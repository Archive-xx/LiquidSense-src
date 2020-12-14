package kotlin.ranges;

import kotlin.ExperimentalUnsignedTypes;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.UInt;
import kotlin.UnsignedKt;
import kotlin.collections.UIntIterator;
import kotlin.internal.UProgressionUtilKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0017\u0018\u0000 \u00192\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0019B\"\b\u0000\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0006ø\u0001\u0000¢\u0006\u0002\u0010\u0007J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0096\u0002J\b\u0010\u0013\u001a\u00020\u0006H\u0016J\b\u0010\u0014\u001a\u00020\u0010H\u0016J\t\u0010\u0015\u001a\u00020\u0016H\u0096\u0002J\b\u0010\u0017\u001a\u00020\u0018H\u0016R\u0016\u0010\b\u001a\u00020\u0002ø\u0001\u0000¢\u0006\n\n\u0002\u0010\u000b\u001a\u0004\b\t\u0010\nR\u0016\u0010\f\u001a\u00020\u0002ø\u0001\u0000¢\u0006\n\n\u0002\u0010\u000b\u001a\u0004\b\r\u0010\nR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\nø\u0001\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u001a"},
   d2 = {"Lkotlin/ranges/UIntProgression;", "", "Lkotlin/UInt;", "start", "endInclusive", "step", "", "(IIILkotlin/jvm/internal/DefaultConstructorMarker;)V", "first", "getFirst", "()I", "I", "last", "getLast", "getStep", "equals", "", "other", "", "hashCode", "isEmpty", "iterator", "Lkotlin/collections/UIntIterator;", "toString", "", "Companion", "kotlin-stdlib"}
)
@SinceKotlin(
   version = "1.3"
)
@ExperimentalUnsignedTypes
public class UIntProgression implements Iterable<UInt>, KMappedMarker {
   private final int first;
   private final int last;
   private final int step;
   public static final UIntProgression.Companion Companion = new UIntProgression.Companion((DefaultConstructorMarker)null);

   public final int getFirst() {
      return this.first;
   }

   public final int getLast() {
      return this.last;
   }

   public final int getStep() {
      return this.step;
   }

   @NotNull
   public UIntIterator iterator() {
      return (UIntIterator)(new UIntProgressionIterator(this.first, this.last, this.step, (DefaultConstructorMarker)null));
   }

   public boolean isEmpty() {
      boolean var10000;
      int var1;
      int var2;
      boolean var3;
      if (this.step > 0) {
         var1 = this.first;
         var2 = this.last;
         var3 = false;
         var10000 = UnsignedKt.uintCompare(var1, var2) > 0;
      } else {
         var1 = this.first;
         var2 = this.last;
         var3 = false;
         var10000 = UnsignedKt.uintCompare(var1, var2) < 0;
      }

      return var10000;
   }

   public boolean equals(@Nullable Object other) {
      return other instanceof UIntProgression && (this.isEmpty() && ((UIntProgression)other).isEmpty() || this.first == ((UIntProgression)other).first && this.last == ((UIntProgression)other).last && this.step == ((UIntProgression)other).step);
   }

   public int hashCode() {
      int var10000;
      if (this.isEmpty()) {
         var10000 = -1;
      } else {
         int var1 = this.first;
         byte var4 = 31;
         byte var3 = 31;
         boolean var2 = false;
         int var10001 = var4 * var1;
         var1 = this.last;
         int var6 = var10001;
         var2 = false;
         var10000 = var3 * (var6 + var1) + this.step;
      }

      return var10000;
   }

   @NotNull
   public String toString() {
      return this.step > 0 ? UInt.toString-impl(this.first) + ".." + UInt.toString-impl(this.last) + " step " + this.step : UInt.toString-impl(this.first) + " downTo " + UInt.toString-impl(this.last) + " step " + -this.step;
   }

   private UIntProgression(int start, int endInclusive, int step) {
      if (step == 0) {
         throw (Throwable)(new IllegalArgumentException("Step must be non-zero."));
      } else if (step == Integer.MIN_VALUE) {
         throw (Throwable)(new IllegalArgumentException("Step must be greater than Int.MIN_VALUE to avoid overflow on negation."));
      } else {
         this.first = start;
         this.last = UProgressionUtilKt.getProgressionLastElement-Nkh28Cs(start, endInclusive, step);
         this.step = step;
      }
   }

   // $FF: synthetic method
   public UIntProgression(int start, int endInclusive, int step, DefaultConstructorMarker $constructor_marker) {
      this(start, endInclusive, step);
   }

   @Metadata(
      mv = {1, 1, 15},
      bv = {1, 0, 3},
      k = 1,
      d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J(\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tø\u0001\u0000¢\u0006\u0004\b\n\u0010\u000b\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\f"},
      d2 = {"Lkotlin/ranges/UIntProgression$Companion;", "", "()V", "fromClosedRange", "Lkotlin/ranges/UIntProgression;", "rangeStart", "Lkotlin/UInt;", "rangeEnd", "step", "", "fromClosedRange-Nkh28Cs", "(III)Lkotlin/ranges/UIntProgression;", "kotlin-stdlib"}
   )
   public static final class Companion {
      @NotNull
      public final UIntProgression fromClosedRange_Nkh28Cs/* $FF was: fromClosedRange-Nkh28Cs*/(int rangeStart, int rangeEnd, int step) {
         return new UIntProgression(rangeStart, rangeEnd, step, (DefaultConstructorMarker)null);
      }

      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker $constructor_marker) {
         this();
      }
   }
}
