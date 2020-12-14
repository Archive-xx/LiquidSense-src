package jdk.nashorn.internal.runtime.regexp.joni;

final class OptExactInfo {
   static final int OPT_EXACT_MAXLEN = 24;
   final MinMaxLen mmd = new MinMaxLen();
   final OptAnchorInfo anchor = new OptAnchorInfo();
   boolean reachEnd;
   boolean ignoreCase;
   final char[] chars = new char[24];
   int length;
   private static final int COMP_EM_BASE = 20;

   boolean isFull() {
      return this.length >= 24;
   }

   void clear() {
      this.mmd.clear();
      this.anchor.clear();
      this.reachEnd = false;
      this.ignoreCase = false;
      this.length = 0;
   }

   void copy(OptExactInfo other) {
      this.mmd.copy(other.mmd);
      this.anchor.copy(other.anchor);
      this.reachEnd = other.reachEnd;
      this.ignoreCase = other.ignoreCase;
      this.length = other.length;
      System.arraycopy(other.chars, 0, this.chars, 0, 24);
   }

   void concat(OptExactInfo other) {
      if (!this.ignoreCase && other.ignoreCase) {
         if (this.length >= other.length) {
            return;
         }

         this.ignoreCase = true;
      }

      int p = 0;
      int end = p + other.length;

      int i;
      for(i = this.length; p < end && i + 1 <= 24; this.chars[i++] = other.chars[p++]) {
      }

      this.length = i;
      this.reachEnd = p == end ? other.reachEnd : false;
      OptAnchorInfo tmp = new OptAnchorInfo();
      tmp.concat(this.anchor, other.anchor, 1, 1);
      if (!other.reachEnd) {
         tmp.rightAnchor = 0;
      }

      this.anchor.copy(tmp);
   }

   void concatStr(char[] lchars, int pp, int end, boolean raw) {
      int p = pp;

      int i;
      for(i = this.length; p < end && i < 24 && i + 1 <= 24; this.chars[i++] = lchars[p++]) {
      }

      this.length = i;
   }

   void altMerge(OptExactInfo other, OptEnvironment env) {
      if (other.length != 0 && this.length != 0) {
         if (!this.mmd.equal(other.mmd)) {
            this.clear();
         } else {
            int i;
            for(i = 0; i < this.length && i < other.length && this.chars[i] == other.chars[i]; ++i) {
            }

            if (!other.reachEnd || i < other.length || i < this.length) {
               this.reachEnd = false;
            }

            this.length = i;
            this.ignoreCase |= other.ignoreCase;
            this.anchor.altMerge(other.anchor);
            if (!this.reachEnd) {
               this.anchor.rightAnchor = 0;
            }

         }
      } else {
         this.clear();
      }
   }

   void select(OptExactInfo alt) {
      int v1 = this.length;
      int v2 = alt.length;
      if (v2 != 0) {
         if (v1 == 0) {
            this.copy(alt);
         } else {
            if (v1 <= 2 && v2 <= 2) {
               v2 = OptMapInfo.positionValue(this.chars[0] & 255);
               v1 = OptMapInfo.positionValue(alt.chars[0] & 255);
               if (this.length > 1) {
                  v1 += 5;
               }

               if (alt.length > 1) {
                  v2 += 5;
               }
            }

            if (!this.ignoreCase) {
               v1 *= 2;
            }

            if (!alt.ignoreCase) {
               v2 *= 2;
            }

            if (this.mmd.compareDistanceValue(alt.mmd, v1, v2) > 0) {
               this.copy(alt);
            }

         }
      }
   }

   int compare(OptMapInfo m) {
      if (m.value <= 0) {
         return -1;
      } else {
         int ve = 20 * this.length * (this.ignoreCase ? 1 : 2);
         int vm = 200 / m.value;
         return this.mmd.compareDistanceValue(m.mmd, ve, vm);
      }
   }
}
