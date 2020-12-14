package kotlin.random;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.LongRange;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 2,
   d1 = {"\u0000:\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0007\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0004H\u0007\u001a\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0000\u001a\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\fH\u0000\u001a\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\u0003H\u0000\u001a\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u0004H\u0000\u001a\u0010\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u0003H\u0000\u001a\u0014\u0010\u000f\u001a\u00020\u0003*\u00020\u00012\u0006\u0010\u0010\u001a\u00020\u0011H\u0007\u001a\u0014\u0010\u0012\u001a\u00020\u0004*\u00020\u00012\u0006\u0010\u0010\u001a\u00020\u0013H\u0007\u001a\u0014\u0010\u0014\u001a\u00020\u0003*\u00020\u00032\u0006\u0010\u0015\u001a\u00020\u0003H\u0000¨\u0006\u0016"},
   d2 = {"Random", "Lkotlin/random/Random;", "seed", "", "", "boundsErrorMessage", "", "from", "", "until", "checkRangeBounds", "", "", "fastLog2", "value", "nextInt", "range", "Lkotlin/ranges/IntRange;", "nextLong", "Lkotlin/ranges/LongRange;", "takeUpperBits", "bitCount", "kotlin-stdlib"}
)
public final class RandomKt {
   @SinceKotlin(
      version = "1.3"
   )
   @NotNull
   public static final Random Random(int seed) {
      return (Random)(new XorWowRandom(seed, seed >> 31));
   }

   @SinceKotlin(
      version = "1.3"
   )
   @NotNull
   public static final Random Random(long seed) {
      return (Random)(new XorWowRandom((int)seed, (int)(seed >> 32)));
   }

   @SinceKotlin(
      version = "1.3"
   )
   public static final int nextInt(@NotNull Random $this$nextInt, @NotNull IntRange range) {
      Intrinsics.checkParameterIsNotNull($this$nextInt, "$this$nextInt");
      Intrinsics.checkParameterIsNotNull(range, "range");
      if (range.isEmpty()) {
         throw (Throwable)(new IllegalArgumentException("Cannot get random in empty range: " + range));
      } else {
         return range.getLast() < Integer.MAX_VALUE ? $this$nextInt.nextInt(range.getFirst(), range.getLast() + 1) : (range.getFirst() > Integer.MIN_VALUE ? $this$nextInt.nextInt(range.getFirst() - 1, range.getLast()) + 1 : $this$nextInt.nextInt());
      }
   }

   @SinceKotlin(
      version = "1.3"
   )
   public static final long nextLong(@NotNull Random $this$nextLong, @NotNull LongRange range) {
      Intrinsics.checkParameterIsNotNull($this$nextLong, "$this$nextLong");
      Intrinsics.checkParameterIsNotNull(range, "range");
      if (range.isEmpty()) {
         throw (Throwable)(new IllegalArgumentException("Cannot get random in empty range: " + range));
      } else {
         return range.getLast() < Long.MAX_VALUE ? $this$nextLong.nextLong(range.getFirst(), range.getLast() + 1L) : (range.getFirst() > Long.MIN_VALUE ? $this$nextLong.nextLong(range.getFirst() - 1L, range.getLast()) + 1L : $this$nextLong.nextLong());
      }
   }

   public static final int fastLog2(int value) {
      byte var3 = 31;
      boolean var2 = false;
      int var4 = Integer.numberOfLeadingZeros(value);
      return var3 - var4;
   }

   public static final int takeUpperBits(int $this$takeUpperBits, int bitCount) {
      return $this$takeUpperBits >>> 32 - bitCount & -bitCount >> 31;
   }

   public static final void checkRangeBounds(int from, int until) {
      boolean var2 = until > from;
      boolean var3 = false;
      boolean var4 = false;
      if (!var2) {
         int var5 = false;
         String var6 = boundsErrorMessage(from, until);
         throw (Throwable)(new IllegalArgumentException(var6.toString()));
      }
   }

   public static final void checkRangeBounds(long from, long until) {
      boolean var4 = until > from;
      boolean var5 = false;
      boolean var6 = false;
      if (!var4) {
         int var7 = false;
         String var8 = boundsErrorMessage(from, until);
         throw (Throwable)(new IllegalArgumentException(var8.toString()));
      }
   }

   public static final void checkRangeBounds(double from, double until) {
      boolean var4 = until > from;
      boolean var5 = false;
      boolean var6 = false;
      if (!var4) {
         int var7 = false;
         String var8 = boundsErrorMessage(from, until);
         throw (Throwable)(new IllegalArgumentException(var8.toString()));
      }
   }

   @NotNull
   public static final String boundsErrorMessage(@NotNull Object from, @NotNull Object until) {
      Intrinsics.checkParameterIsNotNull(from, "from");
      Intrinsics.checkParameterIsNotNull(until, "until");
      return "Random range is empty: [" + from + ", " + until + ").";
   }
}
