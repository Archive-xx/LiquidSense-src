package jdk.nashorn.internal.runtime.regexp.joni;

import jdk.nashorn.internal.runtime.regexp.joni.ast.CClassNode;
import jdk.nashorn.internal.runtime.regexp.joni.encoding.IntHolder;
import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;

class ByteCodeMachine extends StackMachine {
   private int bestLen;
   private int s = 0;
   private int range;
   private int sprev;
   private int sstart;
   private int sbegin;
   private final int[] code;
   private int ip;

   ByteCodeMachine(Regex regex, char[] chars, int p, int end) {
      super(regex, chars, p, end);
      this.code = regex.code;
   }

   private boolean stringCmpIC(int caseFlodFlag, int s1p, IntHolder ps2, int mbLen, int textEnd) {
      int s1 = s1p;
      int s2 = ps2.value;
      int end1 = s1p + mbLen;

      char c1;
      char c2;
      do {
         if (s1 >= end1) {
            ps2.value = s2;
            return true;
         }

         c1 = EncodingHelper.toLowerCase(this.chars[s1++]);
         c2 = EncodingHelper.toLowerCase(this.chars[s2++]);
      } while(c1 == c2);

      return false;
   }

   private void debugMatchBegin() {
      Config.log.println("match_at: str: " + this.str + ", end: " + this.end + ", start: " + this.sstart + ", sprev: " + this.sprev);
      Config.log.println("size: " + (this.end - this.str) + ", start offset: " + (this.sstart - this.str));
   }

   private void debugMatchLoop() {
   }

   protected final int matchAt(int r, int ss, int sp) {
      this.range = r;
      this.sstart = ss;
      this.sprev = sp;
      this.stk = 0;
      this.ip = 0;
      this.init();
      this.bestLen = -1;
      this.s = ss;
      int[] c = this.code;

      while(true) {
         this.sbegin = this.s;
         switch(c[this.ip++]) {
         case 0:
            return this.finish();
         case 1:
            if (this.opEnd()) {
               return this.finish();
            }
            break;
         case 2:
            this.opExact1();
            break;
         case 3:
            this.opExact2();
            break;
         case 4:
            this.opExact3();
            break;
         case 5:
            this.opExact4();
            break;
         case 6:
            this.opExact5();
            break;
         case 7:
            this.opExactN();
            break;
         case 8:
         case 9:
         case 10:
         case 11:
         case 12:
         case 13:
         case 69:
         default:
            throw new InternalException("undefined bytecode (bug)");
         case 14:
            this.opExact1IC();
            break;
         case 15:
            this.opExactNIC();
            break;
         case 16:
            this.opCClass();
            break;
         case 17:
            this.opCClassMB();
            break;
         case 18:
            this.opCClassMIX();
            break;
         case 19:
            this.opCClassNot();
            break;
         case 20:
            this.opCClassMBNot();
            break;
         case 21:
            this.opCClassMIXNot();
            break;
         case 22:
            this.opCClassNode();
            break;
         case 23:
            this.opAnyChar();
            break;
         case 24:
            this.opAnyCharML();
            break;
         case 25:
            this.opAnyCharStar();
            break;
         case 26:
            this.opAnyCharMLStar();
            break;
         case 27:
            this.opAnyCharStarPeekNext();
            break;
         case 28:
            this.opAnyCharMLStarPeekNext();
            break;
         case 29:
            this.opWord();
            break;
         case 30:
            this.opNotWord();
            break;
         case 31:
            this.opWordBound();
            break;
         case 32:
            this.opNotWordBound();
            break;
         case 33:
            this.opWordBegin();
            break;
         case 34:
            this.opWordEnd();
            break;
         case 35:
            this.opBeginBuf();
            break;
         case 36:
            this.opEndBuf();
            break;
         case 37:
            this.opBeginLine();
            break;
         case 38:
            this.opEndLine();
            break;
         case 39:
            this.opSemiEndBuf();
            break;
         case 40:
            this.opBeginPosition();
            break;
         case 41:
            this.opBackRef1();
            break;
         case 42:
            this.opBackRef2();
            break;
         case 43:
            this.opBackRefN();
            break;
         case 44:
            this.opBackRefNIC();
            break;
         case 45:
            this.opBackRefMulti();
            break;
         case 46:
            this.opBackRefMultiIC();
            break;
         case 47:
            this.opBackRefAtLevel();
            break;
         case 48:
            this.opMemoryStart();
            break;
         case 49:
            this.opMemoryStartPush();
            break;
         case 50:
            this.opMemoryEndPush();
            break;
         case 51:
            this.opMemoryEndPushRec();
            break;
         case 52:
            this.opMemoryEnd();
            break;
         case 53:
            this.opMemoryEndRec();
            break;
         case 54:
            this.opFail();
            break;
         case 55:
            this.opJump();
            break;
         case 56:
            this.opPush();
            break;
         case 57:
            this.opPop();
            break;
         case 58:
            this.opPushOrJumpExact1();
            break;
         case 59:
            this.opPushIfPeekNext();
            break;
         case 60:
            this.opRepeat();
            break;
         case 61:
            this.opRepeatNG();
            break;
         case 62:
            this.opRepeatInc();
            break;
         case 63:
            this.opRepeatIncNG();
            break;
         case 64:
            this.opRepeatIncSG();
            break;
         case 65:
            this.opRepeatIncNGSG();
            break;
         case 66:
            this.opNullCheckStart();
            break;
         case 67:
            this.opNullCheckEnd();
            break;
         case 68:
            this.opNullCheckEndMemST();
            break;
         case 70:
            this.opPushPos();
            break;
         case 71:
            this.opPopPos();
            break;
         case 72:
            this.opPushPosNot();
            break;
         case 73:
            this.opFailPos();
            break;
         case 74:
            this.opPushStopBT();
            break;
         case 75:
            this.opPopStopBT();
            break;
         case 76:
            this.opLookBehind();
            break;
         case 77:
            this.opPushLookBehindNot();
            break;
         case 78:
            this.opFailLookBehindNot();
         }
      }
   }

   private boolean opEnd() {
      int n = this.s - this.sstart;
      Region region;
      if (n > this.bestLen) {
         if (Option.isFindLongest(this.regex.options)) {
            if (n <= this.msaBestLen) {
               return this.endBestLength();
            }

            this.msaBestLen = n;
            this.msaBestS = this.sstart;
         }

         this.bestLen = n;
         region = this.msaRegion;
         if (region != null) {
            region.beg[0] = this.msaBegin = this.sstart - this.str;
            region.end[0] = this.msaEnd = this.s - this.str;

            for(int i = 1; i <= this.regex.numMem; ++i) {
               if (this.repeatStk[this.memEndStk + i] != -1) {
                  region.beg[i] = BitStatus.bsAt(this.regex.btMemStart, i) ? this.stack[this.repeatStk[this.memStartStk + i]].getMemPStr() - this.str : this.repeatStk[this.memStartStk + i] - this.str;
                  region.end[i] = BitStatus.bsAt(this.regex.btMemEnd, i) ? this.stack[this.repeatStk[this.memEndStk + i]].getMemPStr() : this.repeatStk[this.memEndStk + i] - this.str;
               } else {
                  region.beg[i] = region.end[i] = -1;
               }
            }
         } else {
            this.msaBegin = this.sstart - this.str;
            this.msaEnd = this.s - this.str;
         }
      } else {
         region = this.msaRegion;
         if (region != null) {
            region.clear();
         } else {
            this.msaBegin = this.msaEnd = 0;
         }
      }

      return this.endBestLength();
   }

   private boolean endBestLength() {
      if (Option.isFindCondition(this.regex.options)) {
         if (Option.isFindNotEmpty(this.regex.options) && this.s == this.sstart) {
            this.bestLen = -1;
            this.opFail();
            return false;
         }

         if (Option.isFindLongest(this.regex.options) && this.s < this.range) {
            this.opFail();
            return false;
         }
      }

      return true;
   }

   private void opExact1() {
      if (this.s < this.range && this.code[this.ip] == this.chars[this.s++]) {
         ++this.ip;
         this.sprev = this.sbegin;
      } else {
         this.opFail();
      }
   }

   private void opExact2() {
      if (this.s + 2 > this.range) {
         this.opFail();
      } else if (this.code[this.ip] != this.chars[this.s]) {
         this.opFail();
      } else {
         ++this.ip;
         ++this.s;
         if (this.code[this.ip] != this.chars[this.s]) {
            this.opFail();
         } else {
            this.sprev = this.s;
            ++this.ip;
            ++this.s;
         }
      }
   }

   private void opExact3() {
      if (this.s + 3 > this.range) {
         this.opFail();
      } else if (this.code[this.ip] != this.chars[this.s]) {
         this.opFail();
      } else {
         ++this.ip;
         ++this.s;
         if (this.code[this.ip] != this.chars[this.s]) {
            this.opFail();
         } else {
            ++this.ip;
            ++this.s;
            if (this.code[this.ip] != this.chars[this.s]) {
               this.opFail();
            } else {
               this.sprev = this.s;
               ++this.ip;
               ++this.s;
            }
         }
      }
   }

   private void opExact4() {
      if (this.s + 4 > this.range) {
         this.opFail();
      } else if (this.code[this.ip] != this.chars[this.s]) {
         this.opFail();
      } else {
         ++this.ip;
         ++this.s;
         if (this.code[this.ip] != this.chars[this.s]) {
            this.opFail();
         } else {
            ++this.ip;
            ++this.s;
            if (this.code[this.ip] != this.chars[this.s]) {
               this.opFail();
            } else {
               ++this.ip;
               ++this.s;
               if (this.code[this.ip] != this.chars[this.s]) {
                  this.opFail();
               } else {
                  this.sprev = this.s;
                  ++this.ip;
                  ++this.s;
               }
            }
         }
      }
   }

   private void opExact5() {
      if (this.s + 5 > this.range) {
         this.opFail();
      } else if (this.code[this.ip] != this.chars[this.s]) {
         this.opFail();
      } else {
         ++this.ip;
         ++this.s;
         if (this.code[this.ip] != this.chars[this.s]) {
            this.opFail();
         } else {
            ++this.ip;
            ++this.s;
            if (this.code[this.ip] != this.chars[this.s]) {
               this.opFail();
            } else {
               ++this.ip;
               ++this.s;
               if (this.code[this.ip] != this.chars[this.s]) {
                  this.opFail();
               } else {
                  ++this.ip;
                  ++this.s;
                  if (this.code[this.ip] != this.chars[this.s]) {
                     this.opFail();
                  } else {
                     this.sprev = this.s;
                     ++this.ip;
                     ++this.s;
                  }
               }
            }
         }
      }
   }

   private void opExactN() {
      int tlen = this.code[this.ip++];
      if (this.s + tlen > this.range) {
         this.opFail();
      } else {
         char[] bs = this.regex.templates[this.code[this.ip++]];
         int var3 = this.code[this.ip++];

         do {
            if (tlen-- <= 0) {
               this.sprev = this.s - 1;
               return;
            }
         } while(bs[var3++] == this.chars[this.s++]);

         this.opFail();
      }
   }

   private void opExact1IC() {
      if (this.s < this.range && this.code[this.ip] == EncodingHelper.toLowerCase(this.chars[this.s++])) {
         ++this.ip;
         this.sprev = this.sbegin;
      } else {
         this.opFail();
      }
   }

   private void opExactNIC() {
      int tlen = this.code[this.ip++];
      if (this.s + tlen > this.range) {
         this.opFail();
      } else {
         char[] bs = this.regex.templates[this.code[this.ip++]];
         int var3 = this.code[this.ip++];

         do {
            if (tlen-- <= 0) {
               this.sprev = this.s - 1;
               return;
            }
         } while(bs[var3++] == EncodingHelper.toLowerCase(this.chars[this.s++]));

         this.opFail();
      }
   }

   private boolean isInBitSet() {
      int c = this.chars[this.s];
      return c <= 255 && (this.code[this.ip + (c >>> BitSet.ROOM_SHIFT)] & 1 << c) != 0;
   }

   private void opCClass() {
      if (this.s < this.range && this.isInBitSet()) {
         this.ip += 8;
         ++this.s;
         this.sprev = this.sbegin;
      } else {
         this.opFail();
      }
   }

   private boolean isInClassMB() {
      int tlen = this.code[this.ip++];
      if (this.s >= this.range) {
         return false;
      } else {
         int ss = this.s++;
         int c = this.chars[ss];
         if (!EncodingHelper.isInCodeRange(this.code, this.ip, c)) {
            return false;
         } else {
            this.ip += tlen;
            return true;
         }
      }
   }

   private void opCClassMB() {
      if (this.s < this.range && this.chars[this.s] > 255) {
         if (!this.isInClassMB()) {
            this.opFail();
         } else {
            this.sprev = this.sbegin;
         }
      } else {
         this.opFail();
      }
   }

   private void opCClassMIX() {
      if (this.s >= this.range) {
         this.opFail();
      } else {
         if (this.chars[this.s] > 255) {
            this.ip += 8;
            if (!this.isInClassMB()) {
               this.opFail();
               return;
            }
         } else {
            if (!this.isInBitSet()) {
               this.opFail();
               return;
            }

            this.ip += 8;
            int tlen = this.code[this.ip++];
            this.ip += tlen;
            ++this.s;
         }

         this.sprev = this.sbegin;
      }
   }

   private void opCClassNot() {
      if (this.s < this.range && !this.isInBitSet()) {
         this.ip += 8;
         ++this.s;
         this.sprev = this.sbegin;
      } else {
         this.opFail();
      }
   }

   private boolean isNotInClassMB() {
      int tlen = this.code[this.ip++];
      if (this.s + 1 > this.range) {
         if (this.s >= this.range) {
            return false;
         } else {
            this.s = this.end;
            this.ip += tlen;
            return true;
         }
      } else {
         int ss = this.s++;
         int c = this.chars[ss];
         if (EncodingHelper.isInCodeRange(this.code, this.ip, c)) {
            return false;
         } else {
            this.ip += tlen;
            return true;
         }
      }
   }

   private void opCClassMBNot() {
      if (this.s >= this.range) {
         this.opFail();
      } else if (this.chars[this.s] <= 255) {
         ++this.s;
         int tlen = this.code[this.ip++];
         this.ip += tlen;
         this.sprev = this.sbegin;
      } else if (!this.isNotInClassMB()) {
         this.opFail();
      } else {
         this.sprev = this.sbegin;
      }
   }

   private void opCClassMIXNot() {
      if (this.s >= this.range) {
         this.opFail();
      } else {
         if (this.chars[this.s] > 255) {
            this.ip += 8;
            if (!this.isNotInClassMB()) {
               this.opFail();
               return;
            }
         } else {
            if (this.isInBitSet()) {
               this.opFail();
               return;
            }

            this.ip += 8;
            int tlen = this.code[this.ip++];
            this.ip += tlen;
            ++this.s;
         }

         this.sprev = this.sbegin;
      }
   }

   private void opCClassNode() {
      if (this.s >= this.range) {
         this.opFail();
      } else {
         CClassNode cc = (CClassNode)this.regex.operands[this.code[this.ip++]];
         int ss = this.s++;
         int c = this.chars[ss];
         if (!cc.isCodeInCCLength(c)) {
            this.opFail();
         } else {
            this.sprev = this.sbegin;
         }
      }
   }

   private void opAnyChar() {
      if (this.s >= this.range) {
         this.opFail();
      } else if (EncodingHelper.isNewLine(this.chars[this.s])) {
         this.opFail();
      } else {
         ++this.s;
         this.sprev = this.sbegin;
      }
   }

   private void opAnyCharML() {
      if (this.s >= this.range) {
         this.opFail();
      } else {
         ++this.s;
         this.sprev = this.sbegin;
      }
   }

   private void opAnyCharStar() {
      for(char[] ch = this.chars; this.s < this.range; this.sprev = this.s++) {
         this.pushAlt(this.ip, this.s, this.sprev);
         if (EncodingHelper.isNewLine(ch, this.s, this.end)) {
            this.opFail();
            return;
         }
      }

      this.sprev = this.sbegin;
   }

   private void opAnyCharMLStar() {
      while(this.s < this.range) {
         this.pushAlt(this.ip, this.s, this.sprev);
         this.sprev = this.s++;
      }

      this.sprev = this.sbegin;
   }

   private void opAnyCharStarPeekNext() {
      char c = (char)this.code[this.ip];

      for(char[] ch = this.chars; this.s < this.range; this.sprev = this.s++) {
         char b = ch[this.s];
         if (c == b) {
            this.pushAlt(this.ip + 1, this.s, this.sprev);
         }

         if (EncodingHelper.isNewLine(b)) {
            this.opFail();
            return;
         }
      }

      ++this.ip;
      this.sprev = this.sbegin;
   }

   private void opAnyCharMLStarPeekNext() {
      char c = (char)this.code[this.ip];

      for(char[] ch = this.chars; this.s < this.range; this.sprev = this.s++) {
         if (c == ch[this.s]) {
            this.pushAlt(this.ip + 1, this.s, this.sprev);
         }
      }

      ++this.ip;
      this.sprev = this.sbegin;
   }

   private void opWord() {
      if (this.s < this.range && EncodingHelper.isWord(this.chars[this.s])) {
         ++this.s;
         this.sprev = this.sbegin;
      } else {
         this.opFail();
      }
   }

   private void opNotWord() {
      if (this.s < this.range && !EncodingHelper.isWord(this.chars[this.s])) {
         ++this.s;
         this.sprev = this.sbegin;
      } else {
         this.opFail();
      }
   }

   private void opWordBound() {
      if (this.s == this.str) {
         if (this.s >= this.range || !EncodingHelper.isWord(this.chars[this.s])) {
            this.opFail();
            return;
         }
      } else if (this.s == this.end) {
         if (this.sprev >= this.end || !EncodingHelper.isWord(this.chars[this.sprev])) {
            this.opFail();
            return;
         }
      } else if (EncodingHelper.isWord(this.chars[this.s]) == EncodingHelper.isWord(this.chars[this.sprev])) {
         this.opFail();
         return;
      }

   }

   private void opNotWordBound() {
      if (this.s == this.str) {
         if (this.s < this.range && EncodingHelper.isWord(this.chars[this.s])) {
            this.opFail();
            return;
         }
      } else if (this.s == this.end) {
         if (this.sprev < this.end && EncodingHelper.isWord(this.chars[this.sprev])) {
            this.opFail();
            return;
         }
      } else if (EncodingHelper.isWord(this.chars[this.s]) != EncodingHelper.isWord(this.chars[this.sprev])) {
         this.opFail();
         return;
      }

   }

   private void opWordBegin() {
      if (this.s >= this.range || !EncodingHelper.isWord(this.chars[this.s]) || this.s != this.str && EncodingHelper.isWord(this.chars[this.sprev])) {
         this.opFail();
      }
   }

   private void opWordEnd() {
      if (this.s == this.str || !EncodingHelper.isWord(this.chars[this.sprev]) || this.s != this.end && EncodingHelper.isWord(this.chars[this.s])) {
         this.opFail();
      }
   }

   private void opBeginBuf() {
      if (this.s != this.str) {
         this.opFail();
      }

   }

   private void opEndBuf() {
      if (this.s != this.end) {
         this.opFail();
      }

   }

   private void opBeginLine() {
      if (this.s == this.str) {
         if (Option.isNotBol(this.msaOptions)) {
            this.opFail();
         }

      } else if (!EncodingHelper.isNewLine(this.chars, this.sprev, this.end) || this.s == this.end) {
         this.opFail();
      }
   }

   private void opEndLine() {
      if (this.s != this.end) {
         if (!EncodingHelper.isNewLine(this.chars, this.s, this.end)) {
            this.opFail();
         }
      } else {
         if ((this.str == this.end || !EncodingHelper.isNewLine(this.chars, this.sprev, this.end)) && Option.isNotEol(this.msaOptions)) {
            this.opFail();
         }

      }
   }

   private void opSemiEndBuf() {
      if (this.s != this.end) {
         if (!EncodingHelper.isNewLine(this.chars, this.s, this.end) || this.s + 1 != this.end) {
            this.opFail();
         }
      } else {
         if ((this.str == this.end || !EncodingHelper.isNewLine(this.chars, this.sprev, this.end)) && Option.isNotEol(this.msaOptions)) {
            this.opFail();
         }

      }
   }

   private void opBeginPosition() {
      if (this.s != this.msaStart) {
         this.opFail();
      }

   }

   private void opMemoryStartPush() {
      int mem = this.code[this.ip++];
      this.pushMemStart(mem, this.s);
   }

   private void opMemoryStart() {
      int mem = this.code[this.ip++];
      this.repeatStk[this.memStartStk + mem] = this.s;
   }

   private void opMemoryEndPush() {
      int mem = this.code[this.ip++];
      this.pushMemEnd(mem, this.s);
   }

   private void opMemoryEnd() {
      int mem = this.code[this.ip++];
      this.repeatStk[this.memEndStk + mem] = this.s;
   }

   private void opMemoryEndPushRec() {
      int mem = this.code[this.ip++];
      int stkp = this.getMemStart(mem);
      this.pushMemEnd(mem, this.s);
      this.repeatStk[this.memStartStk + mem] = stkp;
   }

   private void opMemoryEndRec() {
      int mem = this.code[this.ip++];
      this.repeatStk[this.memEndStk + mem] = this.s;
      int stkp = this.getMemStart(mem);
      if (BitStatus.bsAt(this.regex.btMemStart, mem)) {
         this.repeatStk[this.memStartStk + mem] = stkp;
      } else {
         this.repeatStk[this.memStartStk + mem] = this.stack[stkp].getMemPStr();
      }

      this.pushMemEndMark(mem);
   }

   private boolean backrefInvalid(int mem) {
      return this.repeatStk[this.memEndStk + mem] == -1 || this.repeatStk[this.memStartStk + mem] == -1;
   }

   private int backrefStart(int mem) {
      return BitStatus.bsAt(this.regex.btMemStart, mem) ? this.stack[this.repeatStk[this.memStartStk + mem]].getMemPStr() : this.repeatStk[this.memStartStk + mem];
   }

   private int backrefEnd(int mem) {
      return BitStatus.bsAt(this.regex.btMemEnd, mem) ? this.stack[this.repeatStk[this.memEndStk + mem]].getMemPStr() : this.repeatStk[this.memEndStk + mem];
   }

   private void backref(int mem) {
      if (mem <= this.regex.numMem && !this.backrefInvalid(mem)) {
         int pstart = this.backrefStart(mem);
         int pend = this.backrefEnd(mem);
         int n = pend - pstart;
         if (this.s + n > this.range) {
            this.opFail();
         } else {
            this.sprev = this.s;

            while(n-- > 0) {
               if (this.chars[pstart++] != this.chars[this.s++]) {
                  this.opFail();
                  return;
               }
            }

            if (this.sprev < this.range) {
               while(this.sprev + 1 < this.s) {
                  ++this.sprev;
               }
            }

         }
      } else {
         this.opFail();
      }
   }

   private void opBackRef1() {
      this.backref(1);
   }

   private void opBackRef2() {
      this.backref(2);
   }

   private void opBackRefN() {
      this.backref(this.code[this.ip++]);
   }

   private void opBackRefNIC() {
      int mem = this.code[this.ip++];
      if (mem <= this.regex.numMem && !this.backrefInvalid(mem)) {
         int pstart = this.backrefStart(mem);
         int pend = this.backrefEnd(mem);
         int n = pend - pstart;
         if (this.s + n > this.range) {
            this.opFail();
         } else {
            this.sprev = this.s;
            this.value = this.s;
            if (!this.stringCmpIC(this.regex.caseFoldFlag, pstart, this, n, this.end)) {
               this.opFail();
            } else {
               for(this.s = this.value; this.sprev + 1 < this.s; ++this.sprev) {
               }

            }
         }
      } else {
         this.opFail();
      }
   }

   private void opBackRefMulti() {
      int tlen = this.code[this.ip++];

      int i;
      label44:
      for(i = 0; i < tlen; ++i) {
         int mem = this.code[this.ip++];
         if (!this.backrefInvalid(mem)) {
            int pstart = this.backrefStart(mem);
            int pend = this.backrefEnd(mem);
            int n = pend - pstart;
            if (this.s + n > this.range) {
               this.opFail();
               return;
            }

            this.sprev = this.s;
            int swork = this.s;

            do {
               if (n-- <= 0) {
                  this.s = swork;
                  if (this.sprev < this.range) {
                     while(this.sprev + 1 < this.s) {
                        ++this.sprev;
                     }
                  }

                  this.ip += tlen - i - 1;
                  break label44;
               }
            } while(this.chars[pstart++] == this.chars[swork++]);
         }
      }

      if (i == tlen) {
         this.opFail();
      }
   }

   private void opBackRefMultiIC() {
      int tlen = this.code[this.ip++];

      int i;
      for(i = 0; i < tlen; ++i) {
         int mem = this.code[this.ip++];
         if (!this.backrefInvalid(mem)) {
            int pstart = this.backrefStart(mem);
            int pend = this.backrefEnd(mem);
            int n = pend - pstart;
            if (this.s + n > this.range) {
               this.opFail();
               return;
            }

            this.sprev = this.s;
            this.value = this.s;
            if (this.stringCmpIC(this.regex.caseFoldFlag, pstart, this, n, this.end)) {
               for(this.s = this.value; this.sprev + 1 < this.s; ++this.sprev) {
               }

               this.ip += tlen - i - 1;
               break;
            }
         }
      }

      if (i == tlen) {
         this.opFail();
      }
   }

   private boolean memIsInMemp(int mem, int num, int mempp) {
      int i = 0;

      for(int var5 = mempp; i < num; ++i) {
         int m = this.code[var5++];
         if (mem == m) {
            return true;
         }
      }

      return false;
   }

   private boolean backrefMatchAtNestedLevel(boolean ignoreCase, int caseFoldFlag, int nest, int memNum, int memp) {
      int pend = -1;
      int level = 0;

      for(int k = this.stk - 1; k >= 0; --k) {
         StackEntry e = this.stack[k];
         if (e.type == 2048) {
            --level;
         } else if (e.type == 2304) {
            ++level;
         } else if (level == nest) {
            if (e.type == 256) {
               if (this.memIsInMemp(e.getMemNum(), memNum, memp)) {
                  int pstart = e.getMemPStr();
                  if (pend != -1) {
                     if (pend - pstart > this.end - this.s) {
                        return false;
                     }

                     int p = pstart;
                     this.value = this.s;
                     if (ignoreCase) {
                        if (!this.stringCmpIC(caseFoldFlag, pstart, this, pend - pstart, this.end)) {
                           return false;
                        }
                     } else {
                        while(p < pend) {
                           if (this.chars[p++] != this.chars[this.value++]) {
                              return false;
                           }
                        }
                     }

                     this.s = this.value;
                     return true;
                  }
               }
            } else if (e.type == 33280 && this.memIsInMemp(e.getMemNum(), memNum, memp)) {
               pend = e.getMemPStr();
            }
         }
      }

      return false;
   }

   private void opBackRefAtLevel() {
      int ic = this.code[this.ip++];
      int level = this.code[this.ip++];
      int tlen = this.code[this.ip++];
      this.sprev = this.s;
      if (!this.backrefMatchAtNestedLevel(ic != 0, this.regex.caseFoldFlag, level, tlen, this.ip)) {
         this.opFail();
      } else {
         while(this.sprev + 1 < this.s) {
            ++this.sprev;
         }

         this.ip += tlen;
      }
   }

   private void opNullCheckStart() {
      int mem = this.code[this.ip++];
      this.pushNullCheckStart(mem, this.s);
   }

   private void nullCheckFound() {
      switch(this.code[this.ip++]) {
      case 55:
      case 56:
         ++this.ip;
         break;
      case 57:
      case 58:
      case 59:
      case 60:
      case 61:
      default:
         throw new InternalException("unexpected bytecode (bug)");
      case 62:
      case 63:
      case 64:
      case 65:
         ++this.ip;
      }

   }

   private void opNullCheckEnd() {
      int mem = this.code[this.ip++];
      int isNull = this.nullCheck(mem, this.s);
      if (isNull != 0) {
         this.nullCheckFound();
      }

   }

   private void opNullCheckEndMemST() {
      int mem = this.code[this.ip++];
      int isNull = this.nullCheckMemSt(mem, this.s);
      if (isNull != 0) {
         if (isNull == -1) {
            this.opFail();
            return;
         }

         this.nullCheckFound();
      }

   }

   private void opJump() {
      this.ip += this.code[this.ip] + 1;
   }

   private void opPush() {
      int addr = this.code[this.ip++];
      this.pushAlt(this.ip + addr, this.s, this.sprev);
   }

   private void opPop() {
      this.popOne();
   }

   private void opPushOrJumpExact1() {
      int addr = this.code[this.ip++];
      if (this.s < this.range && this.code[this.ip] == this.chars[this.s]) {
         ++this.ip;
         this.pushAlt(this.ip + addr, this.s, this.sprev);
      } else {
         this.ip += addr + 1;
      }
   }

   private void opPushIfPeekNext() {
      int addr = this.code[this.ip++];
      if (this.s < this.range && this.code[this.ip] == this.chars[this.s]) {
         ++this.ip;
         this.pushAlt(this.ip + addr, this.s, this.sprev);
      } else {
         ++this.ip;
      }
   }

   private void opRepeat() {
      int mem = this.code[this.ip++];
      int addr = this.code[this.ip++];
      this.repeatStk[mem] = this.stk;
      this.pushRepeat(mem, this.ip);
      if (this.regex.repeatRangeLo[mem] == 0) {
         this.pushAlt(this.ip + addr, this.s, this.sprev);
      }

   }

   private void opRepeatNG() {
      int mem = this.code[this.ip++];
      int addr = this.code[this.ip++];
      this.repeatStk[mem] = this.stk;
      this.pushRepeat(mem, this.ip);
      if (this.regex.repeatRangeLo[mem] == 0) {
         this.pushAlt(this.ip, this.s, this.sprev);
         this.ip += addr;
      }

   }

   private void repeatInc(int mem, int si) {
      StackEntry e = this.stack[si];
      e.increaseRepeatCount();
      if (e.getRepeatCount() < this.regex.repeatRangeHi[mem]) {
         if (e.getRepeatCount() >= this.regex.repeatRangeLo[mem]) {
            this.pushAlt(this.ip, this.s, this.sprev);
            this.ip = e.getRepeatPCode();
         } else {
            this.ip = e.getRepeatPCode();
         }
      }

      this.pushRepeatInc(si);
   }

   private void opRepeatInc() {
      int mem = this.code[this.ip++];
      int si = this.repeatStk[mem];
      this.repeatInc(mem, si);
   }

   private void opRepeatIncSG() {
      int mem = this.code[this.ip++];
      int si = this.getRepeat(mem);
      this.repeatInc(mem, si);
   }

   private void repeatIncNG(int mem, int si) {
      StackEntry e = this.stack[si];
      e.increaseRepeatCount();
      if (e.getRepeatCount() < this.regex.repeatRangeHi[mem]) {
         if (e.getRepeatCount() >= this.regex.repeatRangeLo[mem]) {
            int pcode = e.getRepeatPCode();
            this.pushRepeatInc(si);
            this.pushAlt(pcode, this.s, this.sprev);
         } else {
            this.ip = e.getRepeatPCode();
            this.pushRepeatInc(si);
         }
      } else if (e.getRepeatCount() == this.regex.repeatRangeHi[mem]) {
         this.pushRepeatInc(si);
      }

   }

   private void opRepeatIncNG() {
      int mem = this.code[this.ip++];
      int si = this.repeatStk[mem];
      this.repeatIncNG(mem, si);
   }

   private void opRepeatIncNGSG() {
      int mem = this.code[this.ip++];
      int si = this.getRepeat(mem);
      this.repeatIncNG(mem, si);
   }

   private void opPushPos() {
      this.pushPos(this.s, this.sprev);
   }

   private void opPopPos() {
      StackEntry e = this.stack[this.posEnd()];
      this.s = e.getStatePStr();
      this.sprev = e.getStatePStrPrev();
   }

   private void opPushPosNot() {
      int addr = this.code[this.ip++];
      this.pushPosNot(this.ip + addr, this.s, this.sprev);
   }

   private void opFailPos() {
      this.popTilPosNot();
      this.opFail();
   }

   private void opPushStopBT() {
      this.pushStopBT();
   }

   private void opPopStopBT() {
      this.stopBtEnd();
   }

   private void opLookBehind() {
      int tlen = this.code[this.ip++];
      this.s = EncodingHelper.stepBack(this.str, this.s, tlen);
      if (this.s == -1) {
         this.opFail();
      } else {
         this.sprev = EncodingHelper.prevCharHead(this.str, this.s);
      }
   }

   private void opPushLookBehindNot() {
      int addr = this.code[this.ip++];
      int tlen = this.code[this.ip++];
      int q = EncodingHelper.stepBack(this.str, this.s, tlen);
      if (q == -1) {
         this.ip += addr;
      } else {
         this.pushLookBehindNot(this.ip + addr, this.s, this.sprev);
         this.s = q;
         this.sprev = EncodingHelper.prevCharHead(this.str, this.s);
      }

   }

   private void opFailLookBehindNot() {
      this.popTilLookBehindNot();
      this.opFail();
   }

   private void opFail() {
      if (this.stack == null) {
         this.ip = this.regex.codeLength - 1;
      } else {
         StackEntry e = this.pop();
         this.ip = e.getStatePCode();
         this.s = e.getStatePStr();
         this.sprev = e.getStatePStrPrev();
      }
   }

   private int finish() {
      return this.bestLen;
   }
}
