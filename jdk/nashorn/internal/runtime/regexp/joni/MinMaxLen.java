package jdk.nashorn.internal.runtime.regexp.joni;

final class MinMaxLen {
   int min;
   int max;
   private static final short[] distValues = new short[]{1000, 500, 333, 250, 200, 167, 143, 125, 111, 100, 91, 83, 77, 71, 67, 63, 59, 56, 53, 50, 48, 45, 43, 42, 40, 38, 37, 36, 34, 33, 32, 31, 30, 29, 29, 28, 27, 26, 26, 25, 24, 24, 23, 23, 22, 22, 21, 21, 20, 20, 20, 19, 19, 19, 18, 18, 18, 17, 17, 17, 16, 16, 16, 16, 15, 15, 15, 15, 14, 14, 14, 14, 14, 14, 13, 13, 13, 13, 13, 13, 12, 12, 12, 12, 12, 12, 11, 11, 11, 11, 11, 11, 11, 11, 11, 10, 10, 10, 10, 10};
   static final int INFINITE_DISTANCE = Integer.MAX_VALUE;

   MinMaxLen() {
   }

   MinMaxLen(int min, int max) {
      this.min = min;
      this.max = max;
   }

   int distanceValue() {
      if (this.max == Integer.MAX_VALUE) {
         return 0;
      } else {
         int d = this.max - this.min;
         return d < distValues.length ? distValues[d] : 1;
      }
   }

   int compareDistanceValue(MinMaxLen other, int v1p, int v2p) {
      if (v2p <= 0) {
         return -1;
      } else if (v1p <= 0) {
         return 1;
      } else {
         int v1 = v1p * this.distanceValue();
         int v2 = v2p * other.distanceValue();
         if (v2 > v1) {
            return 1;
         } else if (v2 < v1) {
            return -1;
         } else if (other.min < this.min) {
            return 1;
         } else {
            return other.min > this.min ? -1 : 0;
         }
      }
   }

   boolean equal(MinMaxLen other) {
      return this.min == other.min && this.max == other.max;
   }

   void set(int min, int max) {
      this.min = min;
      this.max = max;
   }

   void clear() {
      this.min = this.max = 0;
   }

   void copy(MinMaxLen other) {
      this.min = other.min;
      this.max = other.max;
   }

   void add(MinMaxLen other) {
      this.min = distanceAdd(this.min, other.min);
      this.max = distanceAdd(this.max, other.max);
   }

   void addLength(int len) {
      this.min = distanceAdd(this.min, len);
      this.max = distanceAdd(this.max, len);
   }

   void altMerge(MinMaxLen other) {
      if (this.min > other.min) {
         this.min = other.min;
      }

      if (this.max < other.max) {
         this.max = other.max;
      }

   }

   static int distanceAdd(int d1, int d2) {
      if (d1 != Integer.MAX_VALUE && d2 != Integer.MAX_VALUE) {
         return d1 <= Integer.MAX_VALUE - d2 ? d1 + d2 : Integer.MAX_VALUE;
      } else {
         return Integer.MAX_VALUE;
      }
   }

   static int distanceMultiply(int d, int m) {
      if (m == 0) {
         return 0;
      } else {
         return d < Integer.MAX_VALUE / m ? d * m : Integer.MAX_VALUE;
      }
   }

   static String distanceRangeToString(int a, int b) {
      String s = "";
      if (a == Integer.MAX_VALUE) {
         s = s + "inf";
      } else {
         s = s + "(" + a + ")";
      }

      s = s + "-";
      if (b == Integer.MAX_VALUE) {
         s = s + "inf";
      } else {
         s = s + "(" + b + ")";
      }

      return s;
   }
}
