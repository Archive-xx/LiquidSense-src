package kotlin.random;

import kotlin.ExperimentalUnsignedTypes;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.UByteArray;
import kotlin.UInt;
import kotlin.ULong;
import kotlin.UnsignedKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.UIntRange;
import kotlin.ranges.ULongRange;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 2,
   d1 = {"\u0000:\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\"\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\u0001ø\u0001\u0000¢\u0006\u0004\b\u0005\u0010\u0006\u001a\"\u0010\u0007\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\bH\u0001ø\u0001\u0000¢\u0006\u0004\b\t\u0010\n\u001a\u001c\u0010\u000b\u001a\u00020\f*\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0010\u001a\u001e\u0010\u000b\u001a\u00020\f*\u00020\r2\u0006\u0010\u0011\u001a\u00020\fH\u0007ø\u0001\u0000¢\u0006\u0004\b\u0012\u0010\u0013\u001a2\u0010\u000b\u001a\u00020\f*\u00020\r2\u0006\u0010\u0011\u001a\u00020\f2\b\b\u0002\u0010\u0014\u001a\u00020\u000f2\b\b\u0002\u0010\u0015\u001a\u00020\u000fH\u0007ø\u0001\u0000¢\u0006\u0004\b\u0016\u0010\u0017\u001a\u0014\u0010\u0018\u001a\u00020\u0003*\u00020\rH\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0019\u001a\u001e\u0010\u0018\u001a\u00020\u0003*\u00020\r2\u0006\u0010\u0004\u001a\u00020\u0003H\u0007ø\u0001\u0000¢\u0006\u0004\b\u001a\u0010\u001b\u001a&\u0010\u0018\u001a\u00020\u0003*\u00020\r2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\u0007ø\u0001\u0000¢\u0006\u0004\b\u001c\u0010\u001d\u001a\u001c\u0010\u0018\u001a\u00020\u0003*\u00020\r2\u0006\u0010\u001e\u001a\u00020\u001fH\u0007ø\u0001\u0000¢\u0006\u0002\u0010 \u001a\u0014\u0010!\u001a\u00020\b*\u00020\rH\u0007ø\u0001\u0000¢\u0006\u0002\u0010\"\u001a\u001e\u0010!\u001a\u00020\b*\u00020\r2\u0006\u0010\u0004\u001a\u00020\bH\u0007ø\u0001\u0000¢\u0006\u0004\b#\u0010$\u001a&\u0010!\u001a\u00020\b*\u00020\r2\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\bH\u0007ø\u0001\u0000¢\u0006\u0004\b%\u0010&\u001a\u001c\u0010!\u001a\u00020\b*\u00020\r2\u0006\u0010\u001e\u001a\u00020'H\u0007ø\u0001\u0000¢\u0006\u0002\u0010(\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006)"},
   d2 = {"checkUIntRangeBounds", "", "from", "Lkotlin/UInt;", "until", "checkUIntRangeBounds-J1ME1BU", "(II)V", "checkULongRangeBounds", "Lkotlin/ULong;", "checkULongRangeBounds-eb3DHEI", "(JJ)V", "nextUBytes", "Lkotlin/UByteArray;", "Lkotlin/random/Random;", "size", "", "(Lkotlin/random/Random;I)[B", "array", "nextUBytes-EVgfTAA", "(Lkotlin/random/Random;[B)[B", "fromIndex", "toIndex", "nextUBytes-Wvrt4B4", "(Lkotlin/random/Random;[BII)[B", "nextUInt", "(Lkotlin/random/Random;)I", "nextUInt-qCasIEU", "(Lkotlin/random/Random;I)I", "nextUInt-a8DCA5k", "(Lkotlin/random/Random;II)I", "range", "Lkotlin/ranges/UIntRange;", "(Lkotlin/random/Random;Lkotlin/ranges/UIntRange;)I", "nextULong", "(Lkotlin/random/Random;)J", "nextULong-V1Xi4fY", "(Lkotlin/random/Random;J)J", "nextULong-jmpaW-c", "(Lkotlin/random/Random;JJ)J", "Lkotlin/ranges/ULongRange;", "(Lkotlin/random/Random;Lkotlin/ranges/ULongRange;)J", "kotlin-stdlib"}
)
public final class URandomKt {
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final int nextUInt(@NotNull Random $this$nextUInt) {
      Intrinsics.checkParameterIsNotNull($this$nextUInt, "$this$nextUInt");
      int var1 = $this$nextUInt.nextInt();
      boolean var2 = false;
      return UInt.constructor-impl(var1);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final int nextUInt_qCasIEU/* $FF was: nextUInt-qCasIEU*/(@NotNull Random $this$nextUInt, int until) {
      Intrinsics.checkParameterIsNotNull($this$nextUInt, "$this$nextUInt");
      return nextUInt-a8DCA5k($this$nextUInt, 0, until);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final int nextUInt_a8DCA5k/* $FF was: nextUInt-a8DCA5k*/(@NotNull Random $this$nextUInt, int from, int until) {
      Intrinsics.checkParameterIsNotNull($this$nextUInt, "$this$nextUInt");
      checkUIntRangeBounds-J1ME1BU(from, until);
      boolean var5 = false;
      int signedFrom = from ^ Integer.MIN_VALUE;
      boolean var6 = false;
      int signedUntil = until ^ Integer.MIN_VALUE;
      int signedResult = $this$nextUInt.nextInt(signedFrom, signedUntil) ^ Integer.MIN_VALUE;
      boolean var7 = false;
      return UInt.constructor-impl(signedResult);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final int nextUInt(@NotNull Random $this$nextUInt, @NotNull UIntRange range) {
      Intrinsics.checkParameterIsNotNull($this$nextUInt, "$this$nextUInt");
      Intrinsics.checkParameterIsNotNull(range, "range");
      if (range.isEmpty()) {
         throw (Throwable)(new IllegalArgumentException("Cannot get random in empty range: " + range));
      } else {
         int var2 = range.getLast();
         byte var3 = -1;
         boolean var4 = false;
         int var10000;
         int var6;
         byte var8;
         if (UnsignedKt.uintCompare(var2, var3) < 0) {
            int var10001 = range.getFirst();
            var2 = range.getLast();
            var8 = 1;
            var6 = var10001;
            var4 = false;
            int var7 = UInt.constructor-impl(var2 + var8);
            var10000 = nextUInt-a8DCA5k($this$nextUInt, var6, var7);
         } else {
            var2 = range.getFirst();
            var8 = 0;
            var4 = false;
            if (UnsignedKt.uintCompare(var2, var8) > 0) {
               var2 = range.getFirst();
               var8 = 1;
               var4 = false;
               var6 = UInt.constructor-impl(var2 - var8);
               var2 = nextUInt-a8DCA5k($this$nextUInt, var6, range.getLast());
               var8 = 1;
               var4 = false;
               var10000 = UInt.constructor-impl(var2 + var8);
            } else {
               var10000 = nextUInt($this$nextUInt);
            }
         }

         return var10000;
      }
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final long nextULong(@NotNull Random $this$nextULong) {
      Intrinsics.checkParameterIsNotNull($this$nextULong, "$this$nextULong");
      long var1 = $this$nextULong.nextLong();
      boolean var3 = false;
      return ULong.constructor-impl(var1);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final long nextULong_V1Xi4fY/* $FF was: nextULong-V1Xi4fY*/(@NotNull Random $this$nextULong, long until) {
      Intrinsics.checkParameterIsNotNull($this$nextULong, "$this$nextULong");
      return nextULong-jmpaW-c($this$nextULong, 0L, until);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final long nextULong_jmpaW_c/* $FF was: nextULong-jmpaW-c*/(@NotNull Random $this$nextULong, long from, long until) {
      Intrinsics.checkParameterIsNotNull($this$nextULong, "$this$nextULong");
      checkULongRangeBounds-eb3DHEI(from, until);
      boolean var9 = false;
      long signedFrom = from ^ Long.MIN_VALUE;
      boolean var11 = false;
      long signedUntil = until ^ Long.MIN_VALUE;
      long signedResult = $this$nextULong.nextLong(signedFrom, signedUntil) ^ Long.MIN_VALUE;
      boolean var13 = false;
      return ULong.constructor-impl(signedResult);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final long nextULong(@NotNull Random $this$nextULong, @NotNull ULongRange range) {
      Intrinsics.checkParameterIsNotNull($this$nextULong, "$this$nextULong");
      Intrinsics.checkParameterIsNotNull(range, "range");
      if (range.isEmpty()) {
         throw (Throwable)(new IllegalArgumentException("Cannot get random in empty range: " + range));
      } else {
         long var2 = range.getLast();
         long var4 = -1L;
         boolean var6 = false;
         boolean var5;
         boolean var9;
         long var10;
         boolean var12;
         long var14;
         long var10000;
         byte var18;
         if (UnsignedKt.ulongCompare(var2, var4) < 0) {
            long var10001 = range.getFirst();
            var2 = range.getLast();
            var18 = 1;
            var14 = var10001;
            var5 = false;
            var9 = false;
            var10 = ULong.constructor-impl((long)var18 & 4294967295L);
            var12 = false;
            long var16 = ULong.constructor-impl(var2 + var10);
            var10000 = nextULong-jmpaW-c($this$nextULong, var14, var16);
         } else {
            var2 = range.getFirst();
            var4 = 0L;
            var6 = false;
            if (UnsignedKt.ulongCompare(var2, var4) > 0) {
               var2 = range.getFirst();
               var18 = 1;
               var5 = false;
               var9 = false;
               var10 = ULong.constructor-impl((long)var18 & 4294967295L);
               var12 = false;
               var14 = ULong.constructor-impl(var2 - var10);
               var2 = nextULong-jmpaW-c($this$nextULong, var14, range.getLast());
               var18 = 1;
               var5 = false;
               var9 = false;
               var10 = ULong.constructor-impl((long)var18 & 4294967295L);
               var12 = false;
               var10000 = ULong.constructor-impl(var2 + var10);
            } else {
               var10000 = nextULong($this$nextULong);
            }
         }

         return var10000;
      }
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @NotNull
   public static final byte[] nextUBytes_EVgfTAA/* $FF was: nextUBytes-EVgfTAA*/(@NotNull Random $this$nextUBytes, @NotNull byte[] array) {
      Intrinsics.checkParameterIsNotNull($this$nextUBytes, "$this$nextUBytes");
      Intrinsics.checkParameterIsNotNull(array, "array");
      boolean var3 = false;
      $this$nextUBytes.nextBytes(array);
      return array;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @NotNull
   public static final byte[] nextUBytes(@NotNull Random $this$nextUBytes, int size) {
      Intrinsics.checkParameterIsNotNull($this$nextUBytes, "$this$nextUBytes");
      byte[] var2 = $this$nextUBytes.nextBytes(size);
      boolean var3 = false;
      return UByteArray.constructor-impl(var2);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @NotNull
   public static final byte[] nextUBytes_Wvrt4B4/* $FF was: nextUBytes-Wvrt4B4*/(@NotNull Random $this$nextUBytes, @NotNull byte[] array, int fromIndex, int toIndex) {
      Intrinsics.checkParameterIsNotNull($this$nextUBytes, "$this$nextUBytes");
      Intrinsics.checkParameterIsNotNull(array, "array");
      boolean var5 = false;
      $this$nextUBytes.nextBytes(array, fromIndex, toIndex);
      return array;
   }

   // $FF: synthetic method
   public static byte[] nextUBytes_Wvrt4B4$default/* $FF was: nextUBytes-Wvrt4B4$default*/(Random var0, byte[] var1, int var2, int var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = 0;
      }

      if ((var4 & 4) != 0) {
         var3 = UByteArray.getSize-impl(var1);
      }

      return nextUBytes-Wvrt4B4(var0, var1, var2, var3);
   }

   @ExperimentalUnsignedTypes
   public static final void checkUIntRangeBounds_J1ME1BU/* $FF was: checkUIntRangeBounds-J1ME1BU*/(int from, int until) {
      boolean var3 = false;
      boolean var2 = UnsignedKt.uintCompare(until, from) > 0;
      var3 = false;
      boolean var4 = false;
      if (!var2) {
         int var5 = false;
         String var6 = RandomKt.boundsErrorMessage(UInt.box-impl(from), UInt.box-impl(until));
         throw (Throwable)(new IllegalArgumentException(var6.toString()));
      }
   }

   @ExperimentalUnsignedTypes
   public static final void checkULongRangeBounds_eb3DHEI/* $FF was: checkULongRangeBounds-eb3DHEI*/(long from, long until) {
      boolean var6 = false;
      boolean var4 = UnsignedKt.ulongCompare(until, from) > 0;
      boolean var5 = false;
      var6 = false;
      if (!var4) {
         int var7 = false;
         String var8 = RandomKt.boundsErrorMessage(ULong.box-impl(from), ULong.box-impl(until));
         throw (Throwable)(new IllegalArgumentException(var8.toString()));
      }
   }
}
