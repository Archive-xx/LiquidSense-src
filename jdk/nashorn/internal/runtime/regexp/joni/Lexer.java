package jdk.nashorn.internal.runtime.regexp.joni;

import jdk.nashorn.internal.runtime.regexp.joni.ast.QuantifierNode;
import jdk.nashorn.internal.runtime.regexp.joni.constants.TokenType;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

class Lexer extends ScannerSupport {
   protected final ScanEnvironment env;
   protected final Syntax syntax;
   protected final Token token = new Token();

   protected Lexer(ScanEnvironment env, char[] chars, int p, int end) {
      super(chars, p, end);
      this.env = env;
      this.syntax = env.syntax;
   }

   private int fetchRangeQuantifier() {
      this.mark();
      boolean synAllow = this.syntax.allowInvalidInterval();
      if (!this.left()) {
         if (synAllow) {
            return 1;
         } else {
            throw new SyntaxException("end pattern at left brace");
         }
      } else {
         if (!synAllow) {
            this.c = this.peek();
            if (this.c == 41 || this.c == 40 || this.c == 124) {
               throw new SyntaxException("end pattern at left brace");
            }
         }

         int low = this.scanUnsignedNumber();
         if (low < 0) {
            throw new SyntaxException("too big number for repeat range");
         } else if (low > 100000) {
            throw new SyntaxException("too big number for repeat range");
         } else {
            boolean nonLow = false;
            if (this.p == this._p) {
               if (!this.syntax.allowIntervalLowAbbrev()) {
                  return this.invalidRangeQuantifier(synAllow);
               }

               low = 0;
               nonLow = true;
            }

            if (!this.left()) {
               return this.invalidRangeQuantifier(synAllow);
            } else {
               this.fetch();
               int ret = 0;
               int up;
               if (this.c == 44) {
                  int prev = this.p;
                  up = this.scanUnsignedNumber();
                  if (up < 0) {
                     throw new ValueException("too big number for repeat range");
                  }

                  if (up > 100000) {
                     throw new ValueException("too big number for repeat range");
                  }

                  if (this.p == prev) {
                     if (nonLow) {
                        return this.invalidRangeQuantifier(synAllow);
                     }

                     up = -1;
                  }
               } else {
                  if (nonLow) {
                     return this.invalidRangeQuantifier(synAllow);
                  }

                  this.unfetch();
                  up = low;
                  ret = 2;
               }

               if (!this.left()) {
                  return this.invalidRangeQuantifier(synAllow);
               } else {
                  this.fetch();
                  if (this.syntax.opEscBraceInterval()) {
                     if (this.c != this.syntax.metaCharTable.esc) {
                        return this.invalidRangeQuantifier(synAllow);
                     }

                     this.fetch();
                  }

                  if (this.c != 125) {
                     return this.invalidRangeQuantifier(synAllow);
                  } else if (!QuantifierNode.isRepeatInfinite(up) && low > up) {
                     throw new ValueException("upper is smaller than lower in repeat range");
                  } else {
                     this.token.type = TokenType.INTERVAL;
                     this.token.setRepeatLower(low);
                     this.token.setRepeatUpper(up);
                     return ret;
                  }
               }
            }
         }
      }
   }

   private int invalidRangeQuantifier(boolean synAllow) {
      if (synAllow) {
         this.restore();
         return 1;
      } else {
         throw new SyntaxException("invalid repeat range {lower,upper}");
      }
   }

   private int fetchEscapedValue() {
      if (!this.left()) {
         throw new SyntaxException("end pattern at escape");
      } else {
         this.fetch();
         switch(this.c) {
         case 67:
            if (this.syntax.op2EscCapitalCBarControl()) {
               if (!this.left()) {
                  throw new SyntaxException("end pattern at control");
               }

               this.fetch();
               if (this.c != 45) {
                  throw new SyntaxException("invalid control-code syntax");
               }

               this.fetchEscapedValueControl();
            } else {
               this.fetchEscapedValueBackSlash();
            }
            break;
         case 77:
            if (this.syntax.op2EscCapitalMBarMeta()) {
               if (!this.left()) {
                  throw new SyntaxException("end pattern at meta");
               }

               this.fetch();
               if (this.c != 45) {
                  throw new SyntaxException("invalid meta-code syntax");
               }

               if (!this.left()) {
                  throw new SyntaxException("end pattern at meta");
               }

               this.fetch();
               if (this.c == this.syntax.metaCharTable.esc) {
                  this.c = this.fetchEscapedValue();
               }

               this.c = this.c & 255 | 128;
            } else {
               this.fetchEscapedValueBackSlash();
            }
            break;
         case 99:
            if (this.syntax.opEscCControl()) {
               this.fetchEscapedValueControl();
            }
         default:
            this.fetchEscapedValueBackSlash();
         }

         return this.c;
      }
   }

   private void fetchEscapedValueBackSlash() {
      this.c = this.env.convertBackslashValue(this.c);
   }

   private void fetchEscapedValueControl() {
      if (!this.left()) {
         throw new SyntaxException("end pattern at control");
      } else {
         this.fetch();
         if (this.c == 63) {
            this.c = 127;
         } else {
            if (this.c == this.syntax.metaCharTable.esc) {
               this.c = this.fetchEscapedValue();
            }

            this.c &= 159;
         }

      }
   }

   private void fetchTokenInCCFor_charType(boolean flag, int type) {
      this.token.type = TokenType.CHAR_TYPE;
      this.token.setPropCType(type);
      this.token.setPropNot(flag);
   }

   private void fetchTokenInCCFor_x() {
      if (this.left()) {
         int last = this.p;
         int num;
         if (this.peekIs(123) && this.syntax.opEscXBraceHex8()) {
            this.inc();
            num = this.scanUnsignedHexadecimalNumber(8);
            if (num < 0) {
               throw new ValueException("too big wide-char value");
            }

            if (this.left()) {
               int c2 = this.peek();
               if (EncodingHelper.isXDigit(c2)) {
                  throw new ValueException("too long wide-char value");
               }
            }

            if (this.p > last + 1 && this.left() && this.peekIs(125)) {
               this.inc();
               this.token.type = TokenType.CODE_POINT;
               this.token.setCode(num);
            } else {
               this.p = last;
            }
         } else if (this.syntax.opEscXHex2()) {
            num = this.scanUnsignedHexadecimalNumber(2);
            if (num < 0) {
               throw new ValueException("too big number");
            }

            if (this.p == last) {
               num = 0;
            }

            this.token.type = TokenType.RAW_BYTE;
            this.token.setC(num);
         }

      }
   }

   private void fetchTokenInCCFor_u() {
      if (this.left()) {
         int last = this.p;
         if (this.syntax.op2EscUHex4()) {
            int num = this.scanUnsignedHexadecimalNumber(4);
            if (num < 0) {
               throw new ValueException("too big number");
            }

            if (this.p == last) {
               num = 0;
            }

            this.token.type = TokenType.CODE_POINT;
            this.token.setCode(num);
         }

      }
   }

   private void fetchTokenInCCFor_digit() {
      if (this.syntax.opEscOctal3()) {
         this.unfetch();
         int last = this.p;
         int num = this.scanUnsignedOctalNumber(3);
         if (num < 0) {
            throw new ValueException("too big number");
         }

         if (this.p == last) {
            num = 0;
         }

         this.token.type = TokenType.RAW_BYTE;
         this.token.setC(num);
      }

   }

   private void fetchTokenInCCFor_and() {
      if (this.syntax.op2CClassSetOp() && this.left() && this.peekIs(38)) {
         this.inc();
         this.token.type = TokenType.CC_AND;
      }

   }

   protected final TokenType fetchTokenInCC() {
      if (!this.left()) {
         this.token.type = TokenType.EOT;
         return this.token.type;
      } else {
         this.fetch();
         this.token.type = TokenType.CHAR;
         this.token.setC(this.c);
         this.token.escaped = false;
         if (this.c == 93) {
            this.token.type = TokenType.CC_CLOSE;
         } else if (this.c == 45) {
            this.token.type = TokenType.CC_RANGE;
         } else if (this.c == this.syntax.metaCharTable.esc) {
            if (!this.syntax.backSlashEscapeInCC()) {
               return this.token.type;
            }

            if (!this.left()) {
               throw new SyntaxException("end pattern at escape");
            }

            this.fetch();
            this.token.escaped = true;
            this.token.setC(this.c);
            switch(this.c) {
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
               this.fetchTokenInCCFor_digit();
               break;
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 69:
            case 70:
            case 71:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
            case 80:
            case 81:
            case 82:
            case 84:
            case 85:
            case 86:
            case 88:
            case 89:
            case 90:
            case 91:
            case 92:
            case 93:
            case 94:
            case 95:
            case 96:
            case 97:
            case 98:
            case 99:
            case 101:
            case 102:
            case 103:
            case 105:
            case 106:
            case 107:
            case 108:
            case 109:
            case 110:
            case 111:
            case 112:
            case 113:
            case 114:
            case 116:
            case 118:
            default:
               this.unfetch();
               int num = this.fetchEscapedValue();
               if (this.token.getC() != num) {
                  this.token.setCode(num);
                  this.token.type = TokenType.CODE_POINT;
               }
               break;
            case 68:
               this.fetchTokenInCCFor_charType(true, 260);
               break;
            case 72:
               if (this.syntax.op2EscHXDigit()) {
                  this.fetchTokenInCCFor_charType(true, 11);
               }
               break;
            case 83:
               this.fetchTokenInCCFor_charType(true, 265);
               break;
            case 87:
               this.fetchTokenInCCFor_charType(true, 268);
               break;
            case 100:
               this.fetchTokenInCCFor_charType(false, 260);
               break;
            case 104:
               if (this.syntax.op2EscHXDigit()) {
                  this.fetchTokenInCCFor_charType(false, 11);
               }
               break;
            case 115:
               this.fetchTokenInCCFor_charType(false, 265);
               break;
            case 117:
               this.fetchTokenInCCFor_u();
               break;
            case 119:
               this.fetchTokenInCCFor_charType(false, 268);
               break;
            case 120:
               this.fetchTokenInCCFor_x();
            }
         } else if (this.c == 38) {
            this.fetchTokenInCCFor_and();
         }

         return this.token.type;
      }
   }

   private void fetchTokenFor_repeat(int lower, int upper) {
      this.token.type = TokenType.OP_REPEAT;
      this.token.setRepeatLower(lower);
      this.token.setRepeatUpper(upper);
      this.greedyCheck();
   }

   private void fetchTokenFor_openBrace() {
      switch(this.fetchRangeQuantifier()) {
      case 0:
         this.greedyCheck();
         break;
      case 2:
         if (this.syntax.fixedIntervalIsGreedyOnly()) {
            this.possessiveCheck();
         } else {
            this.greedyCheck();
         }
      }

   }

   private void fetchTokenFor_anchor(int subType) {
      this.token.type = TokenType.ANCHOR;
      this.token.setAnchor(subType);
   }

   private void fetchTokenFor_xBrace() {
      if (this.left()) {
         int last = this.p;
         int num;
         if (this.peekIs(123) && this.syntax.opEscXBraceHex8()) {
            this.inc();
            num = this.scanUnsignedHexadecimalNumber(8);
            if (num < 0) {
               throw new ValueException("too big wide-char value");
            }

            if (this.left() && EncodingHelper.isXDigit(this.peek())) {
               throw new ValueException("too long wide-char value");
            }

            if (this.p > last + 1 && this.left() && this.peekIs(125)) {
               this.inc();
               this.token.type = TokenType.CODE_POINT;
               this.token.setCode(num);
            } else {
               this.p = last;
            }
         } else if (this.syntax.opEscXHex2()) {
            num = this.scanUnsignedHexadecimalNumber(2);
            if (num < 0) {
               throw new ValueException("too big number");
            }

            if (this.p == last) {
               num = 0;
            }

            this.token.type = TokenType.RAW_BYTE;
            this.token.setC(num);
         }

      }
   }

   private void fetchTokenFor_uHex() {
      if (this.left()) {
         int last = this.p;
         if (this.syntax.op2EscUHex4()) {
            int num = this.scanUnsignedHexadecimalNumber(4);
            if (num < 0) {
               throw new ValueException("too big number");
            }

            if (this.p == last) {
               num = 0;
            }

            this.token.type = TokenType.CODE_POINT;
            this.token.setCode(num);
         }

      }
   }

   private void fetchTokenFor_digit() {
      this.unfetch();
      int last = this.p;
      int num = this.scanUnsignedNumber();
      if (num < 0 || num > 1000 || !this.syntax.opDecimalBackref() || num > this.env.numMem && num > 9) {
         if (this.c != 56 && this.c != 57) {
            this.p = last;
            this.fetchTokenFor_zero();
         } else {
            this.p = last;
            this.inc();
         }
      } else if (!this.syntax.strictCheckBackref() || num <= this.env.numMem && this.env.memNodes != null && this.env.memNodes[num] != null) {
         this.token.type = TokenType.BACKREF;
         this.token.setBackrefRef(num);
      } else {
         throw new ValueException("invalid backref number");
      }
   }

   private void fetchTokenFor_zero() {
      if (this.syntax.opEscOctal3()) {
         int last = this.p;
         int num = this.scanUnsignedOctalNumber(this.c == 48 ? 2 : 3);
         if (num < 0) {
            throw new ValueException("too big number");
         }

         if (this.p == last) {
            num = 0;
         }

         this.token.type = TokenType.RAW_BYTE;
         this.token.setC(num);
      } else if (this.c != 48) {
         this.inc();
      }

   }

   private void fetchTokenFor_metaChars() {
      if (this.c == this.syntax.metaCharTable.anyChar) {
         this.token.type = TokenType.ANYCHAR;
      } else if (this.c == this.syntax.metaCharTable.anyTime) {
         this.fetchTokenFor_repeat(0, -1);
      } else if (this.c == this.syntax.metaCharTable.zeroOrOneTime) {
         this.fetchTokenFor_repeat(0, 1);
      } else if (this.c == this.syntax.metaCharTable.oneOrMoreTime) {
         this.fetchTokenFor_repeat(1, -1);
      } else if (this.c == this.syntax.metaCharTable.anyCharAnyTime) {
         this.token.type = TokenType.ANYCHAR_ANYTIME;
      }

   }

   protected final TokenType fetchToken() {
      label197:
      while(this.left()) {
         this.token.type = TokenType.STRING;
         this.token.backP = this.p;
         this.fetch();
         if (this.c == this.syntax.metaCharTable.esc && !this.syntax.op2IneffectiveEscape()) {
            if (!this.left()) {
               throw new SyntaxException("end pattern at escape");
            }

            this.token.backP = this.p;
            this.fetch();
            this.token.setC(this.c);
            this.token.escaped = true;
            switch(this.c) {
            case 39:
               if (this.syntax.op2EscGnuBufAnchor()) {
                  this.fetchTokenFor_anchor(8);
               }
               break;
            case 40:
               if (this.syntax.opEscLParenSubexp()) {
                  this.token.type = TokenType.SUBEXP_OPEN;
               }
               break;
            case 41:
               if (this.syntax.opEscLParenSubexp()) {
                  this.token.type = TokenType.SUBEXP_CLOSE;
               }
               break;
            case 42:
               if (this.syntax.opEscAsteriskZeroInf()) {
                  this.fetchTokenFor_repeat(0, -1);
               }
               break;
            case 43:
               if (this.syntax.opEscPlusOneInf()) {
                  this.fetchTokenFor_repeat(1, -1);
               }
               break;
            case 44:
            case 45:
            case 46:
            case 47:
            case 58:
            case 59:
            case 61:
            case 64:
            case 67:
            case 69:
            case 70:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
            case 80:
            case 81:
            case 82:
            case 84:
            case 85:
            case 86:
            case 88:
            case 89:
            case 91:
            case 92:
            case 93:
            case 94:
            case 95:
            case 97:
            case 99:
            case 101:
            case 102:
            case 103:
            case 105:
            case 106:
            case 107:
            case 108:
            case 109:
            case 110:
            case 111:
            case 112:
            case 113:
            case 114:
            case 116:
            case 118:
            case 121:
            default:
               this.unfetch();
               int num = this.fetchEscapedValue();
               if (this.token.getC() != num) {
                  this.token.type = TokenType.CODE_POINT;
                  this.token.setCode(num);
               } else {
                  this.p = this.token.backP + 1;
               }
               break;
            case 48:
               this.fetchTokenFor_zero();
               break;
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
               this.fetchTokenFor_digit();
               break;
            case 60:
               if (this.syntax.opEscLtGtWordBeginEnd()) {
                  this.fetchTokenFor_anchor(256);
               }
               break;
            case 62:
               if (this.syntax.opEscLtGtWordBeginEnd()) {
                  this.fetchTokenFor_anchor(512);
               }
               break;
            case 63:
               if (this.syntax.opEscQMarkZeroOne()) {
                  this.fetchTokenFor_repeat(0, 1);
               }
               break;
            case 65:
               if (this.syntax.opEscAZBufAnchor()) {
                  this.fetchTokenFor_anchor(1);
               }
               break;
            case 66:
               if (this.syntax.opEscBWordBound()) {
                  this.fetchTokenFor_anchor(128);
               }
               break;
            case 68:
               if (this.syntax.opEscDDigit()) {
                  this.fetchTokenInCCFor_charType(true, 260);
               }
               break;
            case 71:
               if (this.syntax.opEscCapitalGBeginAnchor()) {
                  this.fetchTokenFor_anchor(4);
               }
               break;
            case 72:
               if (this.syntax.op2EscHXDigit()) {
                  this.fetchTokenInCCFor_charType(true, 11);
               }
               break;
            case 83:
               if (this.syntax.opEscSWhiteSpace()) {
                  this.fetchTokenInCCFor_charType(true, 265);
               }
               break;
            case 87:
               if (this.syntax.opEscWWord()) {
                  this.fetchTokenInCCFor_charType(true, 268);
               }
               break;
            case 90:
               if (this.syntax.opEscAZBufAnchor()) {
                  this.fetchTokenFor_anchor(16);
               }
               break;
            case 96:
               if (this.syntax.op2EscGnuBufAnchor()) {
                  this.fetchTokenFor_anchor(1);
               }
               break;
            case 98:
               if (this.syntax.opEscBWordBound()) {
                  this.fetchTokenFor_anchor(64);
               }
               break;
            case 100:
               if (this.syntax.opEscDDigit()) {
                  this.fetchTokenInCCFor_charType(false, 260);
               }
               break;
            case 104:
               if (this.syntax.op2EscHXDigit()) {
                  this.fetchTokenInCCFor_charType(false, 11);
               }
               break;
            case 115:
               if (this.syntax.opEscSWhiteSpace()) {
                  this.fetchTokenInCCFor_charType(false, 265);
               }
               break;
            case 117:
               this.fetchTokenFor_uHex();
               break;
            case 119:
               if (this.syntax.opEscWWord()) {
                  this.fetchTokenInCCFor_charType(false, 268);
               }
               break;
            case 120:
               this.fetchTokenFor_xBrace();
               break;
            case 122:
               if (this.syntax.opEscAZBufAnchor()) {
                  this.fetchTokenFor_anchor(8);
               }
               break;
            case 123:
               if (this.syntax.opEscBraceInterval()) {
                  this.fetchTokenFor_openBrace();
               }
               break;
            case 124:
               if (this.syntax.opEscVBarAlt()) {
                  this.token.type = TokenType.ALT;
               }
            }
         } else {
            this.token.setC(this.c);
            this.token.escaped = false;
            if (this.c != 0 && this.syntax.opVariableMetaCharacters()) {
               this.fetchTokenFor_metaChars();
            } else {
               switch(this.c) {
               case 9:
               case 10:
               case 12:
               case 13:
               case 32:
                  if (Option.isExtend(this.env.option)) {
                     continue;
                  }
                  break;
               case 35:
                  if (Option.isExtend(this.env.option)) {
                     while(true) {
                        if (!this.left()) {
                           continue label197;
                        }

                        this.fetch();
                        if (EncodingHelper.isNewLine(this.c)) {
                           continue label197;
                        }
                     }
                  }
                  break;
               case 36:
                  if (this.syntax.opLineAnchor()) {
                     this.fetchTokenFor_anchor(Option.isSingleline(this.env.option) ? 8 : 32);
                  }
                  break;
               case 40:
                  if (this.peekIs(63) && this.syntax.op2QMarkGroupEffect()) {
                     this.inc();
                     if (this.peekIs(35)) {
                        this.fetch();

                        while(this.left()) {
                           this.fetch();
                           if (this.c == this.syntax.metaCharTable.esc) {
                              if (this.left()) {
                                 this.fetch();
                              }
                           } else if (this.c == 41) {
                              continue label197;
                           }
                        }

                        throw new SyntaxException("end pattern in group");
                     }

                     this.unfetch();
                  }

                  if (this.syntax.opLParenSubexp()) {
                     this.token.type = TokenType.SUBEXP_OPEN;
                  }
                  break;
               case 41:
                  if (this.syntax.opLParenSubexp()) {
                     this.token.type = TokenType.SUBEXP_CLOSE;
                  }
                  break;
               case 42:
                  if (this.syntax.opAsteriskZeroInf()) {
                     this.fetchTokenFor_repeat(0, -1);
                  }
                  break;
               case 43:
                  if (this.syntax.opPlusOneInf()) {
                     this.fetchTokenFor_repeat(1, -1);
                  }
                  break;
               case 46:
                  if (this.syntax.opDotAnyChar()) {
                     this.token.type = TokenType.ANYCHAR;
                  }
                  break;
               case 63:
                  if (this.syntax.opQMarkZeroOne()) {
                     this.fetchTokenFor_repeat(0, 1);
                  }
                  break;
               case 91:
                  if (this.syntax.opBracketCC()) {
                     this.token.type = TokenType.CC_CC_OPEN;
                  }
               case 93:
               default:
                  break;
               case 94:
                  if (this.syntax.opLineAnchor()) {
                     this.fetchTokenFor_anchor(Option.isSingleline(this.env.option) ? 1 : 2);
                  }
                  break;
               case 123:
                  if (this.syntax.opBraceInterval()) {
                     this.fetchTokenFor_openBrace();
                  }
                  break;
               case 124:
                  if (this.syntax.opVBarAlt()) {
                     this.token.type = TokenType.ALT;
                  }
               }
            }
         }

         return this.token.type;
      }

      this.token.type = TokenType.EOT;
      return this.token.type;
   }

   private void greedyCheck() {
      if (this.left() && this.peekIs(63) && this.syntax.opQMarkNonGreedy()) {
         this.fetch();
         this.token.setRepeatGreedy(false);
         this.token.setRepeatPossessive(false);
      } else {
         this.possessiveCheck();
      }

   }

   private void possessiveCheck() {
      if (!this.left() || !this.peekIs(43) || (!this.syntax.op2PlusPossessiveRepeat() || this.token.type == TokenType.INTERVAL) && (!this.syntax.op2PlusPossessiveInterval() || this.token.type != TokenType.INTERVAL)) {
         this.token.setRepeatGreedy(true);
         this.token.setRepeatPossessive(false);
      } else {
         this.fetch();
         this.token.setRepeatGreedy(true);
         this.token.setRepeatPossessive(true);
      }

   }

   protected final void syntaxWarn(String message, char ch) {
      this.syntaxWarn(message.replace("<%n>", Character.toString(ch)));
   }

   protected final void syntaxWarn(String message) {
      this.env.reg.warnings.warn(message + ": /" + new String(this.chars, this.getBegin(), this.getEnd()) + "/");
   }
}
