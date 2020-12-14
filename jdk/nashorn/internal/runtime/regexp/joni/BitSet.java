package jdk.nashorn.internal.runtime.regexp.joni;

public final class BitSet {
   static final int BITS_PER_BYTE = 8;
   public static final int SINGLE_BYTE_SIZE = 256;
   private static final int BITS_IN_ROOM = 32;
   static final int BITSET_SIZE = 8;
   static final int ROOM_SHIFT = log2(32);
   final int[] bits = new int[8];
   private static final int BITS_TO_STRING_WRAP = 4;

   public String toString() {
      StringBuilder buffer = new StringBuilder();
      buffer.append("BitSet");

      for(int i = 0; i < 256; ++i) {
         if (i % 64 == 0) {
            buffer.append("\n  ");
         }

         buffer.append(this.at(i) ? "1" : "0");
      }

      return buffer.toString();
   }

   public boolean at(int pos) {
      return (this.bits[pos >>> ROOM_SHIFT] & bit(pos)) != 0;
   }

   public void set(int pos) {
      int[] var10000 = this.bits;
      int var10001 = pos >>> ROOM_SHIFT;
      var10000[var10001] |= bit(pos);
   }

   public void clear(int pos) {
      int[] var10000 = this.bits;
      int var10001 = pos >>> ROOM_SHIFT;
      var10000[var10001] &= ~bit(pos);
   }

   public void clear() {
      for(int i = 0; i < 8; ++i) {
         this.bits[i] = 0;
      }

   }

   public boolean isEmpty() {
      for(int i = 0; i < 8; ++i) {
         if (this.bits[i] != 0) {
            return false;
         }
      }

      return true;
   }

   public void setRange(int from, int to) {
      for(int i = from; i <= to && i < 256; ++i) {
         this.set(i);
      }

   }

   public void invert() {
      for(int i = 0; i < 8; ++i) {
         this.bits[i] = ~this.bits[i];
      }

   }

   public void invertTo(BitSet to) {
      for(int i = 0; i < 8; ++i) {
         to.bits[i] = ~this.bits[i];
      }

   }

   public void and(BitSet other) {
      for(int i = 0; i < 8; ++i) {
         int[] var10000 = this.bits;
         var10000[i] &= other.bits[i];
      }

   }

   public void or(BitSet other) {
      for(int i = 0; i < 8; ++i) {
         int[] var10000 = this.bits;
         var10000[i] |= other.bits[i];
      }

   }

   public void copy(BitSet other) {
      for(int i = 0; i < 8; ++i) {
         this.bits[i] = other.bits[i];
      }

   }

   public int numOn() {
      int num = 0;

      for(int i = 0; i < 256; ++i) {
         if (this.at(i)) {
            ++num;
         }
      }

      return num;
   }

   static int bit(int pos) {
      return 1 << pos % 256;
   }

   private static int log2(int np) {
      int log = 0;

      for(int n = np; (n >>>= 1) != 0; ++log) {
      }

      return log;
   }
}
