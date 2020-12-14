package kotlin;

import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 2,
   d1 = {"\u00000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\t\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0001ø\u0001\u0000¢\u0006\u0002\u0010\u0004\u001a\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0002\u001a\u00020\u0003H\u0001ø\u0001\u0000¢\u0006\u0002\u0010\u0007\u001a\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\tH\u0001\u001a\"\u0010\f\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\u0001H\u0001ø\u0001\u0000¢\u0006\u0004\b\r\u0010\u000e\u001a\"\u0010\u000f\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\u0001H\u0001ø\u0001\u0000¢\u0006\u0004\b\u0010\u0010\u000e\u001a\u0010\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0002\u001a\u00020\tH\u0001\u001a\u0018\u0010\u0012\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00132\u0006\u0010\u000b\u001a\u00020\u0013H\u0001\u001a\"\u0010\u0014\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0006H\u0001ø\u0001\u0000¢\u0006\u0004\b\u0015\u0010\u0016\u001a\"\u0010\u0017\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0006H\u0001ø\u0001\u0000¢\u0006\u0004\b\u0018\u0010\u0016\u001a\u0010\u0010\u0019\u001a\u00020\u00032\u0006\u0010\u0002\u001a\u00020\u0013H\u0001\u001a\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0002\u001a\u00020\u0013H\u0000\u001a\u0018\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0002\u001a\u00020\u00132\u0006\u0010\u001c\u001a\u00020\tH\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u001d"},
   d2 = {"doubleToUInt", "Lkotlin/UInt;", "v", "", "(D)I", "doubleToULong", "Lkotlin/ULong;", "(D)J", "uintCompare", "", "v1", "v2", "uintDivide", "uintDivide-J1ME1BU", "(II)I", "uintRemainder", "uintRemainder-J1ME1BU", "uintToDouble", "ulongCompare", "", "ulongDivide", "ulongDivide-eb3DHEI", "(JJ)J", "ulongRemainder", "ulongRemainder-eb3DHEI", "ulongToDouble", "ulongToString", "", "base", "kotlin-stdlib"}
)
@JvmName(
   name = "UnsignedKt"
)
public final class UnsignedKt {
   @PublishedApi
   public static final int uintCompare(int v1, int v2) {
      return Intrinsics.compare(v1 ^ Integer.MIN_VALUE, v2 ^ Integer.MIN_VALUE);
   }

   @PublishedApi
   public static final int ulongCompare(long v1, long v2) {
      long var4;
      return (var4 = (v1 ^ Long.MIN_VALUE) - (v2 ^ Long.MIN_VALUE)) == 0L ? 0 : (var4 < 0L ? -1 : 1);
   }

   @PublishedApi
   public static final int uintDivide_J1ME1BU/* $FF was: uintDivide-J1ME1BU*/(int v1, int v2) {
      boolean var3 = false;
      long var5 = (long)v1 & 4294967295L;
      var3 = false;
      long var7 = (long)v2 & 4294967295L;
      long var2 = var5 / var7;
      boolean var4 = false;
      return UInt.constructor-impl((int)var2);
   }

   @PublishedApi
   public static final int uintRemainder_J1ME1BU/* $FF was: uintRemainder-J1ME1BU*/(int v1, int v2) {
      boolean var3 = false;
      long var5 = (long)v1 & 4294967295L;
      var3 = false;
      long var7 = (long)v2 & 4294967295L;
      long var2 = var5 % var7;
      boolean var4 = false;
      return UInt.constructor-impl((int)var2);
   }

   @PublishedApi
   public static final long ulongDivide_eb3DHEI/* $FF was: ulongDivide-eb3DHEI*/(long v1, long v2) {
      boolean var8 = false;
      boolean var10 = false;
      if (v2 < 0L) {
         var10 = false;
         return ulongCompare(v1, v2) < 0 ? ULong.constructor-impl(0L) : ULong.constructor-impl(1L);
      } else if (v1 >= 0L) {
         return ULong.constructor-impl(v1 / v2);
      } else {
         long quotient = (v1 >>> 1) / v2 << 1;
         long rem = v1 - quotient * v2;
         long var12 = ULong.constructor-impl(rem);
         long var14 = ULong.constructor-impl(v2);
         boolean var16 = false;
         int var19 = ulongCompare(var12, var14);
         return ULong.constructor-impl(quotient + (long)(var19 >= 0 ? 1 : 0));
      }
   }

   @PublishedApi
   public static final long ulongRemainder_eb3DHEI/* $FF was: ulongRemainder-eb3DHEI*/(long v1, long v2) {
      boolean var8 = false;
      boolean var10 = false;
      if (v2 < 0L) {
         var10 = false;
         long var10000;
         if (ulongCompare(v1, v2) < 0) {
            var10000 = v1;
         } else {
            var10 = false;
            var10000 = ULong.constructor-impl(v1 - v2);
         }

         return var10000;
      } else if (v1 >= 0L) {
         return ULong.constructor-impl(v1 % v2);
      } else {
         long quotient = (v1 >>> 1) / v2 << 1;
         long rem = v1 - quotient * v2;
         long var12 = ULong.constructor-impl(rem);
         long var14 = ULong.constructor-impl(v2);
         boolean var16 = false;
         int var19 = ulongCompare(var12, var14);
         return ULong.constructor-impl(rem - (var19 >= 0 ? v2 : 0L));
      }
   }

   @PublishedApi
   public static final int doubleToUInt(double v) {
      boolean var4 = false;
      int var10000;
      if (Double.isNaN(v)) {
         var10000 = 0;
      } else {
         byte var2 = 0;
         boolean var3 = false;
         double var7 = uintToDouble(var2);
         if (v <= var7) {
            var10000 = 0;
         } else {
            byte var9 = -1;
            var3 = false;
            var7 = uintToDouble(var9);
            if (v >= var7) {
               var10000 = -1;
            } else {
               int var10;
               if (v <= (double)Integer.MAX_VALUE) {
                  var10 = (int)v;
                  var3 = false;
                  var10000 = UInt.constructor-impl(var10);
               } else {
                  var10 = (int)(v - (double)Integer.MAX_VALUE);
                  var3 = false;
                  var10 = UInt.constructor-impl(var10);
                  int var11 = Integer.MAX_VALUE;
                  var4 = false;
                  var11 = UInt.constructor-impl(var11);
                  var4 = false;
                  var10000 = UInt.constructor-impl(var10 + var11);
               }
            }
         }
      }

      return var10000;
   }

   @PublishedApi
   public static final long doubleToULong(double v) {
      boolean var4 = false;
      long var10000;
      if (Double.isNaN(v)) {
         var10000 = 0L;
      } else {
         long var2 = 0L;
         var4 = false;
         double var9 = ulongToDouble(var2);
         if (v <= var9) {
            var10000 = 0L;
         } else {
            var2 = -1L;
            var4 = false;
            var9 = ulongToDouble(var2);
            if (v >= var9) {
               var10000 = -1L;
            } else if (v < (double)Long.MAX_VALUE) {
               var2 = (long)v;
               var4 = false;
               var10000 = ULong.constructor-impl(var2);
            } else {
               var2 = (long)(v - 9.223372036854776E18D);
               var4 = false;
               var2 = ULong.constructor-impl(var2);
               long var11 = Long.MIN_VALUE;
               boolean var6 = false;
               var10000 = ULong.constructor-impl(var2 + var11);
            }
         }
      }

      return var10000;
   }

   @PublishedApi
   public static final double uintToDouble(int v) {
      return (double)(v & Integer.MAX_VALUE) + (double)(v >>> 31 << 30) * (double)2;
   }

   @PublishedApi
   public static final double ulongToDouble(long v) {
      return (double)(v >>> 11) * (double)2048 + (double)(v & 2047L);
   }

   @NotNull
   public static final String ulongToString(long v) {
      return ulongToString(v, 10);
   }

   @NotNull
   public static final String ulongToString(long v, int base) {
      String var10000;
      if (v >= 0L) {
         boolean var12 = false;
         var10000 = Long.toString(v, CharsKt.checkRadix(base));
         Intrinsics.checkExpressionValueIsNotNull(var10000, "java.lang.Long.toString(this, checkRadix(radix))");
         return var10000;
      } else {
         long quotient = (v >>> 1) / (long)base << 1;
         long rem = v - quotient * (long)base;
         if (rem >= (long)base) {
            rem -= (long)base;
            ++quotient;
         }

         StringBuilder var10 = new StringBuilder();
         boolean var9 = false;
         var10000 = Long.toString(quotient, CharsKt.checkRadix(base));
         Intrinsics.checkExpressionValueIsNotNull(var10000, "java.lang.Long.toString(this, checkRadix(radix))");
         String var11 = var10000;
         var10 = var10.append(var11);
         var9 = false;
         var10000 = Long.toString(rem, CharsKt.checkRadix(base));
         Intrinsics.checkExpressionValueIsNotNull(var10000, "java.lang.Long.toString(this, checkRadix(radix))");
         var11 = var10000;
         return var10.append(var11).toString();
      }
   }
}
