package jdk.nashorn.internal.runtime.regexp.joni;

import jdk.nashorn.internal.runtime.regexp.joni.ast.AnchorNode;
import jdk.nashorn.internal.runtime.regexp.joni.ast.AnyCharNode;
import jdk.nashorn.internal.runtime.regexp.joni.ast.BackRefNode;
import jdk.nashorn.internal.runtime.regexp.joni.ast.CClassNode;
import jdk.nashorn.internal.runtime.regexp.joni.ast.ConsAltNode;
import jdk.nashorn.internal.runtime.regexp.joni.ast.EncloseNode;
import jdk.nashorn.internal.runtime.regexp.joni.ast.Node;
import jdk.nashorn.internal.runtime.regexp.joni.ast.QuantifierNode;
import jdk.nashorn.internal.runtime.regexp.joni.ast.StringNode;
import jdk.nashorn.internal.runtime.regexp.joni.constants.CCSTATE;
import jdk.nashorn.internal.runtime.regexp.joni.constants.CCVALTYPE;
import jdk.nashorn.internal.runtime.regexp.joni.constants.TokenType;
import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

class Parser extends Lexer {
   protected final Regex regex;
   protected Node root;
   protected int returnCode;

   protected Parser(ScanEnvironment env, char[] chars, int p, int end) {
      super(env, chars, p, end);
      this.regex = env.reg;
   }

   protected final Node parse() {
      this.root = this.parseRegexp();
      this.regex.numMem = this.env.numMem;
      return this.root;
   }

   private boolean codeExistCheck(int code, boolean ignoreEscaped) {
      this.mark();
      boolean inEsc = false;

      while(true) {
         while(this.left()) {
            if (ignoreEscaped && inEsc) {
               inEsc = false;
            } else {
               this.fetch();
               if (this.c == code) {
                  this.restore();
                  return true;
               }

               if (this.c == this.syntax.metaCharTable.esc) {
                  inEsc = true;
               }
            }
         }

         this.restore();
         return false;
      }
   }

   private CClassNode parseCharClass() {
      this.fetchTokenInCC();
      boolean neg;
      if (this.token.type == TokenType.CHAR && this.token.getC() == 94 && !this.token.escaped) {
         neg = true;
         this.fetchTokenInCC();
      } else {
         neg = false;
      }

      if (this.token.type == TokenType.CC_CLOSE) {
         if (!this.codeExistCheck(93, true)) {
            throw new SyntaxException("empty char-class");
         }

         this.env.ccEscWarn("]");
         this.token.type = TokenType.CHAR;
      }

      CClassNode cc = new CClassNode();
      CClassNode prevCC = null;
      CClassNode workCC = null;
      CClassNode.CCStateArg arg = new CClassNode.CCStateArg();
      boolean andStart = false;
      arg.state = CCSTATE.START;

      boolean NEW_LINE;
      while(this.token.type != TokenType.CC_CLOSE) {
         NEW_LINE = false;
         switch(this.token.type) {
         case CHAR:
            if (this.token.getC() > 255) {
               arg.inType = CCVALTYPE.CODE_POINT;
            } else {
               arg.inType = CCVALTYPE.SB;
            }

            arg.v = this.token.getC();
            arg.vIsRaw = false;
            this.parseCharClassValEntry2(cc, arg);
            break;
         case RAW_BYTE:
            arg.v = this.token.getC();
            arg.inType = CCVALTYPE.SB;
            arg.vIsRaw = true;
            this.parseCharClassValEntry2(cc, arg);
            break;
         case CODE_POINT:
            arg.v = this.token.getCode();
            arg.vIsRaw = true;
            this.parseCharClassValEntry(cc, arg);
            break;
         case CHAR_TYPE:
            cc.addCType(this.token.getPropCType(), this.token.getPropNot(), this.env, this);
            cc.nextStateClass(arg, this.env);
            break;
         case CC_RANGE:
            if (arg.state == CCSTATE.VALUE) {
               this.fetchTokenInCC();
               NEW_LINE = true;
               if (this.token.type == TokenType.CC_CLOSE) {
                  this.parseCharClassRangeEndVal(cc, arg);
               } else if (this.token.type == TokenType.CC_AND) {
                  this.env.ccEscWarn("-");
                  this.parseCharClassRangeEndVal(cc, arg);
               } else {
                  arg.state = CCSTATE.RANGE;
               }
            } else if (arg.state == CCSTATE.START) {
               arg.v = this.token.getC();
               arg.vIsRaw = false;
               this.fetchTokenInCC();
               NEW_LINE = true;
               if (this.token.type == TokenType.CC_RANGE || andStart) {
                  this.env.ccEscWarn("-");
               }

               this.parseCharClassValEntry(cc, arg);
            } else if (arg.state == CCSTATE.RANGE) {
               this.env.ccEscWarn("-");
               this.parseCharClassSbChar(cc, arg);
            } else {
               this.fetchTokenInCC();
               NEW_LINE = true;
               if (this.token.type == TokenType.CC_CLOSE) {
                  this.parseCharClassRangeEndVal(cc, arg);
               } else if (this.token.type == TokenType.CC_AND) {
                  this.env.ccEscWarn("-");
                  this.parseCharClassRangeEndVal(cc, arg);
               } else {
                  if (!this.syntax.allowDoubleRangeOpInCC()) {
                     throw new SyntaxException("unmatched range specifier in char-class");
                  }

                  this.env.ccEscWarn("-");
                  arg.inType = CCVALTYPE.SB;
                  arg.v = 45;
                  arg.vIsRaw = false;
                  this.parseCharClassValEntry2(cc, arg);
               }
            }
            break;
         case CC_CC_OPEN:
            CClassNode acc = this.parseCharClass();
            cc.or(acc);
            break;
         case CC_AND:
            if (arg.state == CCSTATE.VALUE) {
               arg.v = 0;
               arg.vIsRaw = false;
               cc.nextStateValue(arg, this.env);
            }

            andStart = true;
            arg.state = CCSTATE.START;
            if (prevCC != null) {
               prevCC.and(cc);
            } else {
               prevCC = cc;
               if (workCC == null) {
                  workCC = new CClassNode();
               }

               cc = workCC;
            }

            cc.clear();
            break;
         case EOT:
            throw new SyntaxException("premature end of char-class");
         default:
            throw new InternalException("internal parser error (bug)");
         }

         if (!NEW_LINE) {
            this.fetchTokenInCC();
         }
      }

      if (arg.state == CCSTATE.VALUE) {
         arg.v = 0;
         arg.vIsRaw = false;
         cc.nextStateValue(arg, this.env);
      }

      if (prevCC != null) {
         prevCC.and(cc);
         cc = prevCC;
      }

      if (neg) {
         cc.setNot();
      } else {
         cc.clearNot();
      }

      if (cc.isNot() && this.syntax.notNewlineInNegativeCC() && !cc.isEmpty()) {
         NEW_LINE = true;
         if (EncodingHelper.isNewLine(10)) {
            cc.bs.set(10);
         }
      }

      return cc;
   }

   private void parseCharClassSbChar(CClassNode cc, CClassNode.CCStateArg arg) {
      arg.inType = CCVALTYPE.SB;
      arg.v = this.token.getC();
      arg.vIsRaw = false;
      this.parseCharClassValEntry2(cc, arg);
   }

   private void parseCharClassRangeEndVal(CClassNode cc, CClassNode.CCStateArg arg) {
      arg.v = 45;
      arg.vIsRaw = false;
      this.parseCharClassValEntry(cc, arg);
   }

   private void parseCharClassValEntry(CClassNode cc, CClassNode.CCStateArg arg) {
      arg.inType = arg.v <= 255 ? CCVALTYPE.SB : CCVALTYPE.CODE_POINT;
      this.parseCharClassValEntry2(cc, arg);
   }

   private void parseCharClassValEntry2(CClassNode cc, CClassNode.CCStateArg arg) {
      cc.nextStateValue(arg, this.env);
   }

   private Node parseEnclose(TokenType term) {
      Node node = null;
      if (!this.left()) {
         throw new SyntaxException("end pattern with unmatched parenthesis");
      } else {
         int option = this.env.option;
         EncloseNode en;
         int prev;
         Node node;
         EncloseNode en;
         if (this.peekIs(63) && this.syntax.op2QMarkGroupEffect()) {
            this.inc();
            if (!this.left()) {
               throw new SyntaxException("end pattern in group");
            }

            this.fetch();
            switch(this.c) {
            case 33:
               node = new AnchorNode(2048);
            case 39:
               break;
            case 45:
            case 105:
            case 109:
            case 115:
            case 120:
               boolean neg = false;

               while(true) {
                  switch(this.c) {
                  case 41:
                  case 58:
                     break;
                  case 45:
                     neg = true;
                     break;
                  case 105:
                     option = BitStatus.bsOnOff(option, 1, neg);
                     break;
                  case 109:
                     if (this.syntax.op2OptionPerl()) {
                        option = BitStatus.bsOnOff(option, 8, !neg);
                     } else {
                        if (!this.syntax.op2OptionRuby()) {
                           throw new SyntaxException("undefined group option");
                        }

                        option = BitStatus.bsOnOff(option, 4, neg);
                     }
                     break;
                  case 115:
                     if (!this.syntax.op2OptionPerl()) {
                        throw new SyntaxException("undefined group option");
                     }

                     option = BitStatus.bsOnOff(option, 4, neg);
                     break;
                  case 120:
                     option = BitStatus.bsOnOff(option, 2, neg);
                     break;
                  default:
                     throw new SyntaxException("undefined group option");
                  }

                  if (this.c == 41) {
                     en = new EncloseNode(option, 0);
                     this.returnCode = 2;
                     return en;
                  }

                  if (this.c == 58) {
                     prev = this.env.option;
                     this.env.option = option;
                     this.fetchToken();
                     Node target = this.parseSubExp(term);
                     this.env.option = prev;
                     EncloseNode en = new EncloseNode(option, 0);
                     en.setTarget(target);
                     this.returnCode = 0;
                     return en;
                  }

                  if (!this.left()) {
                     throw new SyntaxException("end pattern in group");
                  }

                  this.fetch();
               }
            case 58:
               this.fetchToken();
               node = this.parseSubExp(term);
               this.returnCode = 1;
               return node;
            case 60:
               this.fetch();
               if (this.c == 61) {
                  node = new AnchorNode(4096);
               } else {
                  if (this.c != 33) {
                     throw new SyntaxException("undefined group option");
                  }

                  node = new AnchorNode(8192);
               }
               break;
            case 61:
               node = new AnchorNode(1024);
               break;
            case 62:
               node = new EncloseNode(4);
               break;
            case 64:
               if (!this.syntax.op2AtMarkCaptureHistory()) {
                  throw new SyntaxException("undefined group option");
               }

               en = new EncloseNode();
               prev = this.env.addMemEntry();
               if (prev >= 32) {
                  throw new ValueException("group number is too big for capture history");
               }

               en.regNum = prev;
               node = en;
               break;
            default:
               throw new SyntaxException("undefined group option");
            }
         } else {
            if (Option.isDontCaptureGroup(this.env.option)) {
               this.fetchToken();
               node = this.parseSubExp(term);
               this.returnCode = 1;
               return node;
            }

            en = new EncloseNode();
            prev = this.env.addMemEntry();
            en.regNum = prev;
            node = en;
         }

         this.fetchToken();
         Node target = this.parseSubExp(term);
         if (((Node)node).getType() == 7) {
            AnchorNode an = (AnchorNode)node;
            an.setTarget(target);
         } else {
            en = (EncloseNode)node;
            en.setTarget(target);
            if (en.type == 1) {
               this.env.setMemNode(en.regNum, (Node)node);
            }
         }

         this.returnCode = 0;
         return (Node)node;
      }
   }

   private Node parseExp(TokenType term) {
      if (this.token.type == term) {
         return StringNode.EMPTY;
      } else {
         Object node;
         boolean group;
         node = null;
         group = false;
         CClassNode cc;
         label60:
         switch(this.token.type) {
         case RAW_BYTE:
            return this.parseExpTkRawByte(group);
         case CODE_POINT:
            char[] buf = new char[]{(char)this.token.getCode()};
            node = new StringNode(buf, 0, 1);
            break;
         case CHAR_TYPE:
            switch(this.token.getPropCType()) {
            case 4:
            case 9:
            case 11:
               cc = new CClassNode();
               cc.addCType(this.token.getPropCType(), false, this.env, this);
               if (this.token.getPropNot()) {
                  cc.setNot();
               }

               node = cc;
               break label60;
            case 260:
            case 265:
            case 268:
               cc = new CClassNode();
               cc.addCType(this.token.getPropCType(), false, this.env, this);
               if (this.token.getPropNot()) {
                  cc.setNot();
               }

               node = cc;
               break label60;
            default:
               throw new InternalException("internal parser error (bug)");
            }
         case CC_RANGE:
         case CC_AND:
         default:
            throw new InternalException("internal parser error (bug)");
         case CC_CC_OPEN:
            cc = this.parseCharClass();
            node = cc;
            if (Option.isIgnoreCase(this.env.option)) {
               ApplyCaseFoldArg arg = new ApplyCaseFoldArg(this.env, cc);
               EncodingHelper.applyAllCaseFold(this.env.caseFoldFlag, ApplyCaseFold.INSTANCE, arg);
               if (arg.altRoot != null) {
                  node = ConsAltNode.newAltNode(cc, arg.altRoot);
               }
            }
            break;
         case EOT:
         case ALT:
            return StringNode.EMPTY;
         case SUBEXP_OPEN:
            node = this.parseEnclose(TokenType.SUBEXP_CLOSE);
            if (this.returnCode == 1) {
               group = true;
            } else if (this.returnCode == 2) {
               int prev = this.env.option;
               EncloseNode en = (EncloseNode)node;
               this.env.option = en.option;
               this.fetchToken();
               Node target = this.parseSubExp(term);
               this.env.option = prev;
               en.setTarget(target);
               return (Node)node;
            }
            break;
         case SUBEXP_CLOSE:
            if (!this.syntax.allowUnmatchedCloseSubexp()) {
               throw new SyntaxException("unmatched close parenthesis");
            }

            if (this.token.escaped) {
               return this.parseExpTkRawByte(group);
            }

            return this.parseExpTkByte(group);
         case STRING:
            return this.parseExpTkByte(group);
         case ANYCHAR:
            node = new AnyCharNode();
            break;
         case ANYCHAR_ANYTIME:
            Node node = new AnyCharNode();
            QuantifierNode qn = new QuantifierNode(0, -1, false);
            qn.setTarget(node);
            node = qn;
            break;
         case BACKREF:
            int backRef = this.token.getBackrefRef();
            node = new BackRefNode(backRef, this.env);
            break;
         case ANCHOR:
            node = new AnchorNode(this.token.getAnchor());
            break;
         case OP_REPEAT:
         case INTERVAL:
            if (!this.syntax.contextIndepRepeatOps()) {
               return this.parseExpTkByte(group);
            }

            if (this.syntax.contextInvalidRepeatOps()) {
               throw new SyntaxException("target of repeat operator is not specified");
            }

            node = StringNode.EMPTY;
         }

         this.fetchToken();
         return this.parseExpRepeat((Node)node, group);
      }
   }

   private Node parseExpTkByte(boolean group) {
      StringNode node = new StringNode(this.chars, this.token.backP, this.p);

      while(true) {
         this.fetchToken();
         if (this.token.type != TokenType.STRING) {
            return this.parseExpRepeat(node, group);
         }

         if (this.token.backP == node.end) {
            node.end = this.p;
         } else {
            node.cat(this.chars, this.token.backP, this.p);
         }
      }
   }

   private Node parseExpTkRawByte(boolean group) {
      StringNode node = new StringNode((char)this.token.getC());
      node.setRaw();
      this.fetchToken();
      node.clearRaw();
      return this.parseExpRepeat(node, group);
   }

   private Node parseExpRepeat(Node targetp, boolean group) {
      Object target;
      for(target = targetp; this.token.type == TokenType.OP_REPEAT || this.token.type == TokenType.INTERVAL; this.fetchToken()) {
         if (((Node)target).isInvalidQuantifier()) {
            throw new SyntaxException("target of repeat operator is invalid");
         }

         QuantifierNode qtfr = new QuantifierNode(this.token.getRepeatLower(), this.token.getRepeatUpper(), this.token.type == TokenType.INTERVAL);
         qtfr.greedy = this.token.getRepeatGreedy();
         int ret = qtfr.setQuantifier((Node)target, group, this.env, this.chars, this.getBegin(), this.getEnd());
         Node qn = qtfr;
         if (this.token.getRepeatPossessive()) {
            EncloseNode en = new EncloseNode(4);
            en.setTarget(qtfr);
            qn = en;
         }

         if (ret == 0) {
            target = qn;
         } else if (ret == 2) {
            Node target = ConsAltNode.newListNode((Node)target, (ConsAltNode)null);
            ConsAltNode tmp = ((ConsAltNode)target).setCdr(ConsAltNode.newListNode((Node)qn, (ConsAltNode)null));
            this.fetchToken();
            return this.parseExpRepeatForCar(target, tmp, group);
         }
      }

      return (Node)target;
   }

   private Node parseExpRepeatForCar(Node top, ConsAltNode target, boolean group) {
      for(; this.token.type == TokenType.OP_REPEAT || this.token.type == TokenType.INTERVAL; this.fetchToken()) {
         if (target.car.isInvalidQuantifier()) {
            throw new SyntaxException("target of repeat operator is invalid");
         }

         QuantifierNode qtfr = new QuantifierNode(this.token.getRepeatLower(), this.token.getRepeatUpper(), this.token.type == TokenType.INTERVAL);
         qtfr.greedy = this.token.getRepeatGreedy();
         int ret = qtfr.setQuantifier(target.car, group, this.env, this.chars, this.getBegin(), this.getEnd());
         Node qn = qtfr;
         if (this.token.getRepeatPossessive()) {
            EncloseNode en = new EncloseNode(4);
            en.setTarget(qtfr);
            qn = en;
         }

         if (ret == 0) {
            target.setCar((Node)qn);
         } else {
            assert ret != 2;
         }
      }

      return top;
   }

   private Node parseBranch(TokenType term) {
      Node node = this.parseExp(term);
      if (this.token.type != TokenType.EOT && this.token.type != term && this.token.type != TokenType.ALT) {
         ConsAltNode top = ConsAltNode.newListNode(node, (ConsAltNode)null);
         ConsAltNode t = top;

         while(this.token.type != TokenType.EOT && this.token.type != term && this.token.type != TokenType.ALT) {
            Node node = this.parseExp(term);
            if (((Node)node).getType() != 8) {
               t.setCdr(ConsAltNode.newListNode((Node)node, (ConsAltNode)null));
               t = t.cdr;
            } else {
               t.setCdr((ConsAltNode)node);

               while(((ConsAltNode)node).cdr != null) {
                  node = ((ConsAltNode)node).cdr;
               }

               t = (ConsAltNode)node;
            }
         }

         return top;
      } else {
         return node;
      }
   }

   private Node parseSubExp(TokenType term) {
      Node node = this.parseBranch(term);
      if (this.token.type == term) {
         return node;
      } else if (this.token.type != TokenType.ALT) {
         parseSubExpError(term);
         return null;
      } else {
         ConsAltNode top = ConsAltNode.newAltNode(node, (ConsAltNode)null);

         for(ConsAltNode t = top; this.token.type == TokenType.ALT; t = t.cdr) {
            this.fetchToken();
            node = this.parseBranch(term);
            t.setCdr(ConsAltNode.newAltNode(node, (ConsAltNode)null));
         }

         if (this.token.type != term) {
            parseSubExpError(term);
         }

         return top;
      }
   }

   private static void parseSubExpError(TokenType term) {
      if (term == TokenType.SUBEXP_CLOSE) {
         throw new SyntaxException("end pattern with unmatched parenthesis");
      } else {
         throw new InternalException("internal parser error (bug)");
      }
   }

   private Node parseRegexp() {
      this.fetchToken();
      return this.parseSubExp(TokenType.EOT);
   }
}
