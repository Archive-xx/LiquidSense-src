package kotlin.random;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.DoubleCompanionObject;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\b'\u0018\u0000 \u00182\u00020\u0001:\u0002\u0017\u0018B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H&J\b\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\tH\u0016J$\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\b\b\u0002\u0010\u000b\u001a\u00020\u00042\b\b\u0002\u0010\f\u001a\u00020\u0004H\u0016J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u0004H\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fH\u0016J\u0018\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fH\u0016J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u0004H\u0016J\u0010\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0004H\u0016J\u0018\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0004H\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0010\u001a\u00020\u0016H\u0016J\u0018\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0011\u001a\u00020\u00162\u0006\u0010\u0010\u001a\u00020\u0016H\u0016¨\u0006\u0019"},
   d2 = {"Lkotlin/random/Random;", "", "()V", "nextBits", "", "bitCount", "nextBoolean", "", "nextBytes", "", "array", "fromIndex", "toIndex", "size", "nextDouble", "", "until", "from", "nextFloat", "", "nextInt", "nextLong", "", "Companion", "Default", "kotlin-stdlib"}
)
@SinceKotlin(
   version = "1.3"
)
public abstract class Random {
   private static final Random defaultRandom;
   /** @deprecated */
   @JvmField
   @NotNull
   public static final Random.Companion Companion;
   public static final Random.Default Default = new Random.Default((DefaultConstructorMarker)null);

   public abstract int nextBits(int var1);

   public int nextInt() {
      return this.nextBits(32);
   }

   public int nextInt(int until) {
      return this.nextInt(0, until);
   }

   public int nextInt(int from, int until) {
      RandomKt.checkRangeBounds(from, until);
      int n = until - from;
      int rnd;
      if (n <= 0 && n != Integer.MIN_VALUE) {
         while(true) {
            do {
               rnd = this.nextInt();
            } while(from > rnd || until <= rnd);

            return rnd;
         }
      } else {
         int var10000;
         int v;
         if ((n & -n) == n) {
            v = RandomKt.fastLog2(n);
            var10000 = this.nextBits(v);
         } else {
            boolean var7 = false;

            int bits;
            do {
               bits = this.nextInt() >>> 1;
               v = bits % n;
            } while(bits - v + (n - 1) < 0);

            var10000 = v;
         }

         rnd = var10000;
         return from + rnd;
      }
   }

   public long nextLong() {
      return ((long)this.nextInt() << 32) + (long)this.nextInt();
   }

   public long nextLong(long until) {
      return this.nextLong(0L, until);
   }

   public long nextLong(long from, long until) {
      RandomKt.checkRangeBounds(from, until);
      long n = until - from;
      long rnd;
      if (n > 0L) {
         rnd = 0L;
         if ((n & -n) == n) {
            int nLow = (int)n;
            int nHigh = (int)(n >>> 32);
            long var10000;
            int bitCount;
            if (nLow != 0) {
               bitCount = RandomKt.fastLog2(nLow);
               var10000 = (long)this.nextBits(bitCount) & 4294967295L;
            } else if (nHigh == 1) {
               var10000 = (long)this.nextInt() & 4294967295L;
            } else {
               bitCount = RandomKt.fastLog2(nHigh);
               var10000 = ((long)this.nextBits(bitCount) << 32) + (long)this.nextInt();
            }

            rnd = var10000;
         } else {
            long v = 0L;

            long bits;
            do {
               bits = this.nextLong() >>> 1;
               v = bits % n;
            } while(bits - v + (n - 1L) < 0L);

            rnd = v;
         }

         return from + rnd;
      } else {
         while(true) {
            do {
               rnd = this.nextLong();
            } while(from > rnd || until <= rnd);

            return rnd;
         }
      }
   }

   public boolean nextBoolean() {
      return this.nextBits(1) != 0;
   }

   public double nextDouble() {
      return PlatformRandomKt.doubleFromParts(this.nextBits(26), this.nextBits(27));
   }

   public double nextDouble(double until) {
      return this.nextDouble(0.0D, until);
   }

   public double nextDouble(double from, double until) {
      boolean var11;
      double var15;
      label37: {
         RandomKt.checkRangeBounds(from, until);
         double size = until - from;
         var11 = false;
         if (Double.isInfinite(size)) {
            boolean var10000;
            boolean var14;
            label34: {
               var11 = false;
               var14 = false;
               if (!Double.isInfinite(from)) {
                  var14 = false;
                  if (!Double.isNaN(from)) {
                     var10000 = true;
                     break label34;
                  }
               }

               var10000 = false;
            }

            if (var10000) {
               label28: {
                  var11 = false;
                  var14 = false;
                  if (!Double.isInfinite(until)) {
                     var14 = false;
                     if (!Double.isNaN(until)) {
                        var10000 = true;
                        break label28;
                     }
                  }

                  var10000 = false;
               }

               if (var10000) {
                  double r1 = this.nextDouble() * (until / (double)2 - from / (double)2);
                  var15 = from + r1 + r1;
                  break label37;
               }
            }
         }

         var15 = from + this.nextDouble() * size;
      }

      double r = var15;
      if (r >= until) {
         var11 = false;
         var15 = Math.nextAfter(until, DoubleCompanionObject.INSTANCE.getNEGATIVE_INFINITY());
      } else {
         var15 = r;
      }

      return var15;
   }

   public float nextFloat() {
      return (float)this.nextBits(24) / (float)16777216;
   }

   @NotNull
   public byte[] nextBytes(@NotNull byte[] array, int fromIndex, int toIndex) {
      boolean var17;
      label49: {
         Intrinsics.checkParameterIsNotNull(array, "array");
         int var10000 = array.length;
         if (0 <= fromIndex) {
            if (var10000 >= fromIndex) {
               var10000 = array.length;
               if (0 <= toIndex) {
                  if (var10000 >= toIndex) {
                     var17 = true;
                     break label49;
                  }
               }
            }
         }

         var17 = false;
      }

      boolean var4 = var17;
      boolean var5 = false;
      boolean var6 = false;
      boolean var7;
      String var15;
      if (!var4) {
         var7 = false;
         var15 = "fromIndex (" + fromIndex + ") or toIndex (" + toIndex + ") are out of range: 0.." + array.length + '.';
         throw (Throwable)(new IllegalArgumentException(var15.toString()));
      } else {
         var4 = fromIndex <= toIndex;
         var5 = false;
         var6 = false;
         if (!var4) {
            var7 = false;
            var15 = "fromIndex (" + fromIndex + ") must be not greater than toIndex (" + toIndex + ").";
            throw (Throwable)(new IllegalArgumentException(var15.toString()));
         } else {
            int steps = (toIndex - fromIndex) / 4;
            int position = fromIndex;
            var6 = false;
            var7 = false;
            int vr = 0;

            int i;
            for(i = steps; vr < i; ++vr) {
               int var10 = false;
               int v = this.nextInt();
               array[position] = (byte)v;
               array[position + 1] = (byte)(v >>> 8);
               array[position + 2] = (byte)(v >>> 16);
               array[position + 3] = (byte)(v >>> 24);
               position += 4;
            }

            int remainder = toIndex - position;
            vr = this.nextBits(remainder * 8);
            i = 0;

            for(int var9 = remainder; i < var9; ++i) {
               array[position + i] = (byte)(vr >>> i * 8);
            }

            return array;
         }
      }
   }

   // $FF: synthetic method
   public static byte[] nextBytes$default(Random var0, byte[] var1, int var2, int var3, int var4, Object var5) {
      if (var5 != null) {
         throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: nextBytes");
      } else {
         if ((var4 & 2) != 0) {
            var2 = 0;
         }

         if ((var4 & 4) != 0) {
            var3 = var1.length;
         }

         return var0.nextBytes(var1, var2, var3);
      }
   }

   @NotNull
   public byte[] nextBytes(@NotNull byte[] array) {
      Intrinsics.checkParameterIsNotNull(array, "array");
      return this.nextBytes(array, 0, array.length);
   }

   @NotNull
   public byte[] nextBytes(int size) {
      return this.nextBytes(new byte[size]);
   }

   static {
      boolean var0 = false;
      defaultRandom = PlatformImplementationsKt.IMPLEMENTATIONS.defaultPlatformRandom();
      Companion = Random.Companion.INSTANCE;
   }

   /** @deprecated */
   @Deprecated(
      message = "Use Default companion object instead",
      level = DeprecationLevel.HIDDEN
   )
   @Metadata(
      mv = {1, 1, 15},
      bv = {1, 0, 3},
      k = 1,
      d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0016¨\u0006\u0006"},
      d2 = {"Lkotlin/random/Random$Companion;", "Lkotlin/random/Random;", "()V", "nextBits", "", "bitCount", "kotlin-stdlib"}
   )
   public static final class Companion extends Random {
      public static final Random.Companion INSTANCE;

      public int nextBits(int bitCount) {
         return Random.Default.nextBits(bitCount);
      }

      private Companion() {
      }

      static {
         Random.Companion var0 = new Random.Companion();
         INSTANCE = var0;
      }
   }

   @Metadata(
      mv = {1, 1, 15},
      bv = {1, 0, 3},
      k = 1,
      d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0016J\b\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\rH\u0016J \u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\bH\u0016J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\bH\u0016J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0013H\u0016J\u0018\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0013H\u0016J\b\u0010\u0016\u001a\u00020\u0017H\u0016J\b\u0010\u0018\u001a\u00020\bH\u0016J\u0010\u0010\u0018\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\bH\u0016J\u0018\u0010\u0018\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\bH\u0016J\b\u0010\u0019\u001a\u00020\u001aH\u0016J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0014\u001a\u00020\u001aH\u0016J\u0018\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0015\u001a\u00020\u001a2\u0006\u0010\u0014\u001a\u00020\u001aH\u0016R\u0016\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0005\u0010\u0002R\u000e\u0010\u0006\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"},
      d2 = {"Lkotlin/random/Random$Default;", "Lkotlin/random/Random;", "()V", "Companion", "Lkotlin/random/Random$Companion;", "Companion$annotations", "defaultRandom", "nextBits", "", "bitCount", "nextBoolean", "", "nextBytes", "", "array", "fromIndex", "toIndex", "size", "nextDouble", "", "until", "from", "nextFloat", "", "nextInt", "nextLong", "", "kotlin-stdlib"}
   )
   public static final class Default extends Random {
      public int nextBits(int bitCount) {
         return Random.defaultRandom.nextBits(bitCount);
      }

      public int nextInt() {
         return Random.defaultRandom.nextInt();
      }

      public int nextInt(int until) {
         return Random.defaultRandom.nextInt(until);
      }

      public int nextInt(int from, int until) {
         return Random.defaultRandom.nextInt(from, until);
      }

      public long nextLong() {
         return Random.defaultRandom.nextLong();
      }

      public long nextLong(long until) {
         return Random.defaultRandom.nextLong(until);
      }

      public long nextLong(long from, long until) {
         return Random.defaultRandom.nextLong(from, until);
      }

      public boolean nextBoolean() {
         return Random.defaultRandom.nextBoolean();
      }

      public double nextDouble() {
         return Random.defaultRandom.nextDouble();
      }

      public double nextDouble(double until) {
         return Random.defaultRandom.nextDouble(until);
      }

      public double nextDouble(double from, double until) {
         return Random.defaultRandom.nextDouble(from, until);
      }

      public float nextFloat() {
         return Random.defaultRandom.nextFloat();
      }

      @NotNull
      public byte[] nextBytes(@NotNull byte[] array) {
         Intrinsics.checkParameterIsNotNull(array, "array");
         return Random.defaultRandom.nextBytes(array);
      }

      @NotNull
      public byte[] nextBytes(int size) {
         return Random.defaultRandom.nextBytes(size);
      }

      @NotNull
      public byte[] nextBytes(@NotNull byte[] array, int fromIndex, int toIndex) {
         Intrinsics.checkParameterIsNotNull(array, "array");
         return Random.defaultRandom.nextBytes(array, fromIndex, toIndex);
      }

      /** @deprecated */
      // $FF: synthetic method
      @Deprecated(
         message = "Use Default companion object instead",
         level = DeprecationLevel.HIDDEN
      )
      public static void Companion$annotations() {
      }

      private Default() {
      }

      // $FF: synthetic method
      public Default(DefaultConstructorMarker $constructor_marker) {
         this();
      }
   }
}
