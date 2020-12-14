package jdk.nashorn.internal.runtime.regexp.joni.ast;

import jdk.nashorn.internal.runtime.regexp.joni.BitSet;
import jdk.nashorn.internal.runtime.regexp.joni.CodeRangeBuffer;
import jdk.nashorn.internal.runtime.regexp.joni.EncodingHelper;
import jdk.nashorn.internal.runtime.regexp.joni.ScanEnvironment;
import jdk.nashorn.internal.runtime.regexp.joni.Syntax;
import jdk.nashorn.internal.runtime.regexp.joni.constants.CCSTATE;
import jdk.nashorn.internal.runtime.regexp.joni.constants.CCVALTYPE;
import jdk.nashorn.internal.runtime.regexp.joni.encoding.IntHolder;
import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

public final class CClassNode extends Node {
   private static final int FLAG_NCCLASS_NOT = 1;
   private static final int FLAG_NCCLASS_SHARE = 2;
   int flags;
   public final BitSet bs = new BitSet();
   public CodeRangeBuffer mbuf;
   private int ctype;
   private static final short[] AsciiCtypeTable = new short[]{16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16908, 16905, 16904, 16904, 16904, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 17028, 16800, 16800, 16800, 16800, 16800, 16800, 16800, 16800, 16800, 16800, 16800, 16800, 16800, 16800, 16800, 30896, 30896, 30896, 30896, 30896, 30896, 30896, 30896, 30896, 30896, 16800, 16800, 16800, 16800, 16800, 16800, 16800, 31906, 31906, 31906, 31906, 31906, 31906, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 16800, 16800, 16800, 16800, 20896, 16800, 30946, 30946, 30946, 30946, 30946, 30946, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 16800, 16800, 16800, 16800, 16392, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

   public void clear() {
      this.bs.clear();
      this.flags = 0;
      this.mbuf = null;
   }

   public int getType() {
      return 1;
   }

   public String getName() {
      return "Character Class";
   }

   public boolean equals(Object other) {
      if (!(other instanceof CClassNode)) {
         return false;
      } else {
         CClassNode cc = (CClassNode)other;
         return this.ctype == cc.ctype && this.isNot() == cc.isNot();
      }
   }

   public int hashCode() {
      return super.hashCode();
   }

   public String toString(int level) {
      StringBuilder value = new StringBuilder();
      value.append("\n  flags: " + this.flagsToString());
      value.append("\n  bs: " + pad(this.bs, level + 1));
      value.append("\n  mbuf: " + pad(this.mbuf, level + 1));
      return value.toString();
   }

   public String flagsToString() {
      StringBuilder f = new StringBuilder();
      if (this.isNot()) {
         f.append("NOT ");
      }

      if (this.isShare()) {
         f.append("SHARE ");
      }

      return f.toString();
   }

   public boolean isEmpty() {
      return this.mbuf == null && this.bs.isEmpty();
   }

   public void addCodeRangeToBuf(int from, int to) {
      this.mbuf = CodeRangeBuffer.addCodeRangeToBuff(this.mbuf, from, to);
   }

   public void addCodeRange(ScanEnvironment env, int from, int to) {
      this.mbuf = CodeRangeBuffer.addCodeRange(this.mbuf, env, from, to);
   }

   public void addAllMultiByteRange() {
      this.mbuf = CodeRangeBuffer.addAllMultiByteRange(this.mbuf);
   }

   public void clearNotFlag() {
      if (this.isNot()) {
         this.bs.invert();
         this.mbuf = CodeRangeBuffer.notCodeRangeBuff(this.mbuf);
         this.clearNot();
      }

   }

   public void and(CClassNode other) {
      boolean not1 = this.isNot();
      BitSet bsr1 = this.bs;
      CodeRangeBuffer buf1 = this.mbuf;
      boolean not2 = other.isNot();
      BitSet bsr2 = other.bs;
      CodeRangeBuffer buf2 = other.mbuf;
      BitSet pbuf;
      if (not1) {
         pbuf = new BitSet();
         bsr1.invertTo(pbuf);
         bsr1 = pbuf;
      }

      if (not2) {
         pbuf = new BitSet();
         bsr2.invertTo(pbuf);
         bsr2 = pbuf;
      }

      bsr1.and(bsr2);
      if (bsr1 != this.bs) {
         this.bs.copy(bsr1);
         bsr1 = this.bs;
      }

      if (not1) {
         this.bs.invert();
      }

      pbuf = null;
      CodeRangeBuffer pbuf;
      if (not1 && not2) {
         pbuf = CodeRangeBuffer.orCodeRangeBuff(buf1, false, buf2, false);
      } else {
         pbuf = CodeRangeBuffer.andCodeRangeBuff(buf1, not1, buf2, not2);
         if (not1) {
            pbuf = CodeRangeBuffer.notCodeRangeBuff(pbuf);
         }
      }

      this.mbuf = pbuf;
   }

   public void or(CClassNode other) {
      boolean not1 = this.isNot();
      BitSet bsr1 = this.bs;
      CodeRangeBuffer buf1 = this.mbuf;
      boolean not2 = other.isNot();
      BitSet bsr2 = other.bs;
      CodeRangeBuffer buf2 = other.mbuf;
      BitSet pbuf;
      if (not1) {
         pbuf = new BitSet();
         bsr1.invertTo(pbuf);
         bsr1 = pbuf;
      }

      if (not2) {
         pbuf = new BitSet();
         bsr2.invertTo(pbuf);
         bsr2 = pbuf;
      }

      bsr1.or(bsr2);
      if (bsr1 != this.bs) {
         this.bs.copy(bsr1);
         bsr1 = this.bs;
      }

      if (not1) {
         this.bs.invert();
      }

      pbuf = null;
      CodeRangeBuffer pbuf;
      if (not1 && not2) {
         pbuf = CodeRangeBuffer.andCodeRangeBuff(buf1, false, buf2, false);
      } else {
         pbuf = CodeRangeBuffer.orCodeRangeBuff(buf1, not1, buf2, not2);
         if (not1) {
            pbuf = CodeRangeBuffer.notCodeRangeBuff(pbuf);
         }
      }

      this.mbuf = pbuf;
   }

   public void addCTypeByRange(int ct, boolean not, int sbOut, int[] mbr) {
      int n = mbr[0];
      int prev;
      int i;
      if (!not) {
         for(prev = 0; prev < n; ++prev) {
            for(i = mbr[prev * 2 + 1]; i <= mbr[prev * 2 + 2]; ++i) {
               if (i >= sbOut) {
                  if (i >= mbr[prev * 2 + 1]) {
                     this.addCodeRangeToBuf(i, mbr[prev * 2 + 2]);
                     ++prev;
                  }

                  while(prev < n) {
                     this.addCodeRangeToBuf(mbr[2 * prev + 1], mbr[2 * prev + 2]);
                     ++prev;
                  }

                  return;
               }

               this.bs.set(i);
            }
         }

         for(prev = 0; prev < n; ++prev) {
            this.addCodeRangeToBuf(mbr[2 * prev + 1], mbr[2 * prev + 2]);
         }
      } else {
         prev = 0;

         for(i = 0; i < n; ++i) {
            for(int j = prev; j < mbr[2 * i + 1]; ++j) {
               if (j >= sbOut) {
                  prev = sbOut;

                  for(i = 0; i < n; ++i) {
                     if (prev < mbr[2 * i + 1]) {
                        this.addCodeRangeToBuf(prev, mbr[i * 2 + 1] - 1);
                     }

                     prev = mbr[i * 2 + 2] + 1;
                  }

                  if (prev < Integer.MAX_VALUE) {
                     this.addCodeRangeToBuf(prev, Integer.MAX_VALUE);
                  }

                  return;
               }

               this.bs.set(j);
            }

            prev = mbr[2 * i + 2] + 1;
         }

         for(i = prev; i < sbOut; ++i) {
            this.bs.set(i);
         }

         prev = sbOut;

         for(i = 0; i < n; ++i) {
            if (prev < mbr[2 * i + 1]) {
               this.addCodeRangeToBuf(prev, mbr[i * 2 + 1] - 1);
            }

            prev = mbr[i * 2 + 2] + 1;
         }

         if (prev < Integer.MAX_VALUE) {
            this.addCodeRangeToBuf(prev, Integer.MAX_VALUE);
         }
      }

   }

   public void addCType(int ctp, boolean not, ScanEnvironment env, IntHolder sbOut) {
      int ct = ctp;
      switch(ctp) {
      case 260:
      case 265:
      case 268:
         ct = ctp ^ 256;
         if (env.syntax != Syntax.JAVASCRIPT || ct != 9) {
            int c;
            if (not) {
               for(c = 0; c < 256; ++c) {
                  if ((AsciiCtypeTable[c] & 1 << ct) == 0) {
                     this.bs.set(c);
                  }
               }

               this.addAllMultiByteRange();
            } else {
               for(c = 0; c < 256; ++c) {
                  if ((AsciiCtypeTable[c] & 1 << ct) != 0) {
                     this.bs.set(c);
                  }
               }
            }

            return;
         }
      }

      int[] ranges = EncodingHelper.ctypeCodeRange(ct, sbOut);
      if (ranges != null) {
         this.addCTypeByRange(ct, not, sbOut.value, ranges);
      } else {
         int c;
         switch(ct) {
         case 1:
         case 2:
         case 3:
         case 4:
         case 6:
         case 8:
         case 9:
         case 10:
         case 11:
         case 13:
         case 14:
            if (not) {
               for(c = 0; c < 256; ++c) {
                  if (!EncodingHelper.isCodeCType(c, ct)) {
                     this.bs.set(c);
                  }
               }

               this.addAllMultiByteRange();
               break;
            } else {
               for(c = 0; c < 256; ++c) {
                  if (EncodingHelper.isCodeCType(c, ct)) {
                     this.bs.set(c);
                  }
               }

               return;
            }
         case 5:
         case 7:
            if (not) {
               for(c = 0; c < 256; ++c) {
                  if (!EncodingHelper.isCodeCType(c, ct)) {
                     this.bs.set(c);
                  }
               }

               return;
            } else {
               for(c = 0; c < 256; ++c) {
                  if (EncodingHelper.isCodeCType(c, ct)) {
                     this.bs.set(c);
                  }
               }

               this.addAllMultiByteRange();
               break;
            }
         case 12:
            if (!not) {
               for(c = 0; c < 256; ++c) {
                  if (EncodingHelper.isWord(c)) {
                     this.bs.set(c);
                  }
               }

               this.addAllMultiByteRange();
               break;
            } else {
               for(c = 0; c < 256; ++c) {
                  if (!EncodingHelper.isWord(c)) {
                     this.bs.set(c);
                  }
               }

               return;
            }
         default:
            throw new InternalException("internal parser error (bug)");
         }

      }
   }

   public void nextStateClass(CClassNode.CCStateArg arg, ScanEnvironment env) {
      if (arg.state == CCSTATE.RANGE) {
         throw new SyntaxException("char-class value at end of range");
      } else {
         if (arg.state == CCSTATE.VALUE && arg.type != CCVALTYPE.CLASS) {
            if (arg.type == CCVALTYPE.SB) {
               this.bs.set(arg.vs);
            } else if (arg.type == CCVALTYPE.CODE_POINT) {
               this.addCodeRange(env, arg.vs, arg.vs);
            }
         }

         arg.state = CCSTATE.VALUE;
         arg.type = CCVALTYPE.CLASS;
      }
   }

   public void nextStateValue(CClassNode.CCStateArg arg, ScanEnvironment env) {
      switch(arg.state) {
      case VALUE:
         if (arg.type == CCVALTYPE.SB) {
            if (arg.vs > 255) {
               throw new ValueException("invalid code point value");
            }

            this.bs.set(arg.vs);
         } else if (arg.type == CCVALTYPE.CODE_POINT) {
            this.addCodeRange(env, arg.vs, arg.vs);
         }
         break;
      case RANGE:
         if (arg.inType == arg.type) {
            if (arg.inType == CCVALTYPE.SB) {
               if (arg.vs > 255 || arg.v > 255) {
                  throw new ValueException("invalid code point value");
               }

               if (arg.vs > arg.v) {
                  if (!env.syntax.allowEmptyRangeInCC()) {
                     throw new ValueException("empty range in char class");
                  }

                  arg.state = CCSTATE.COMPLETE;
                  break;
               }

               this.bs.setRange(arg.vs, arg.v);
            } else {
               this.addCodeRange(env, arg.vs, arg.v);
            }
         } else {
            if (arg.vs > arg.v) {
               if (!env.syntax.allowEmptyRangeInCC()) {
                  throw new ValueException("empty range in char class");
               }

               arg.state = CCSTATE.COMPLETE;
               break;
            }

            this.bs.setRange(arg.vs, arg.v < 255 ? arg.v : 255);
            this.addCodeRange(env, arg.vs, arg.v);
         }

         arg.state = CCSTATE.COMPLETE;
         break;
      case COMPLETE:
      case START:
         arg.state = CCSTATE.VALUE;
      }

      arg.vsIsRaw = arg.vIsRaw;
      arg.vs = arg.v;
      arg.type = arg.inType;
   }

   public boolean isCodeInCCLength(int code) {
      boolean found;
      if (code > 255) {
         found = this.mbuf != null && this.mbuf.isInCodeRange(code);
      } else {
         found = this.bs.at(code);
      }

      if (this.isNot()) {
         return !found;
      } else {
         return found;
      }
   }

   public boolean isCodeInCC(int code) {
      return this.isCodeInCCLength(code);
   }

   public void setNot() {
      this.flags |= 1;
   }

   public void clearNot() {
      this.flags &= -2;
   }

   public boolean isNot() {
      return (this.flags & 1) != 0;
   }

   public void setShare() {
      this.flags |= 2;
   }

   public void clearShare() {
      this.flags &= -3;
   }

   public boolean isShare() {
      return (this.flags & 2) != 0;
   }

   public static final class CCStateArg {
      public int v;
      public int vs;
      public boolean vsIsRaw;
      public boolean vIsRaw;
      public CCVALTYPE inType;
      public CCVALTYPE type;
      public CCSTATE state;
   }
}
