package kotlin.ranges;

import kotlin.ExperimentalUnsignedTypes;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.ULong;
import kotlin.UnsignedKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0007\u0018\u0000 \u00172\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002:\u0001\u0017B\u0018\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003ø\u0001\u0000¢\u0006\u0002\u0010\u0006J\u001b\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0003H\u0096\u0002ø\u0001\u0000¢\u0006\u0004\b\r\u0010\u000eJ\u0013\u0010\u000f\u001a\u00020\u000b2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0096\u0002J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u000bH\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0016R\u0017\u0010\u0005\u001a\u00020\u00038VX\u0096\u0004ø\u0001\u0000¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0017\u0010\u0004\u001a\u00020\u00038VX\u0096\u0004ø\u0001\u0000¢\u0006\u0006\u001a\u0004\b\t\u0010\bø\u0001\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0018"},
   d2 = {"Lkotlin/ranges/ULongRange;", "Lkotlin/ranges/ULongProgression;", "Lkotlin/ranges/ClosedRange;", "Lkotlin/ULong;", "start", "endInclusive", "(JJLkotlin/jvm/internal/DefaultConstructorMarker;)V", "getEndInclusive", "()Lkotlin/ULong;", "getStart", "contains", "", "value", "contains-VKZWuLQ", "(J)Z", "equals", "other", "", "hashCode", "", "isEmpty", "toString", "", "Companion", "kotlin-stdlib"}
)
@SinceKotlin(
   version = "1.3"
)
@ExperimentalUnsignedTypes
public final class ULongRange extends ULongProgression implements ClosedRange<ULong> {
   @NotNull
   private static final ULongRange EMPTY = new ULongRange(-1L, 0L, (DefaultConstructorMarker)null);
   public static final ULongRange.Companion Companion = new ULongRange.Companion((DefaultConstructorMarker)null);

   @NotNull
   public ULong getStart() {
      return ULong.box-impl(this.getFirst());
   }

   @NotNull
   public ULong getEndInclusive() {
      return ULong.box-impl(this.getLast());
   }

   public boolean contains_VKZWuLQ/* $FF was: contains-VKZWuLQ*/(long value) {
      long var3 = this.getFirst();
      boolean var5 = false;
      boolean var10000;
      if (UnsignedKt.ulongCompare(var3, value) <= 0) {
         long var8 = this.getLast();
         boolean var7 = false;
         if (UnsignedKt.ulongCompare(value, var8) <= 0) {
            var10000 = true;
            return var10000;
         }
      }

      var10000 = false;
      return var10000;
   }

   public boolean isEmpty() {
      long var1 = this.getFirst();
      long var3 = this.getLast();
      boolean var5 = false;
      return UnsignedKt.ulongCompare(var1, var3) > 0;
   }

   public boolean equals(@Nullable Object other) {
      return other instanceof ULongRange && (this.isEmpty() && ((ULongRange)other).isEmpty() || this.getFirst() == ((ULongRange)other).getFirst() && this.getLast() == ((ULongRange)other).getLast());
   }

   public int hashCode() {
      int var10000;
      if (this.isEmpty()) {
         var10000 = -1;
      } else {
         long var1 = this.getFirst();
         long var3 = this.getFirst();
         byte var5 = 32;
         byte var7 = 31;
         boolean var6 = false;
         long var8 = ULong.constructor-impl(var3 >>> var5);
         boolean var11 = false;
         var8 = ULong.constructor-impl(var1 ^ var8);
         boolean var10 = false;
         int var13 = (int)var8;
         var10000 = var7 * var13;
         var1 = this.getLast();
         var3 = this.getLast();
         var5 = 32;
         int var12 = var10000;
         var6 = false;
         var8 = ULong.constructor-impl(var3 >>> var5);
         var11 = false;
         var8 = ULong.constructor-impl(var1 ^ var8);
         var10 = false;
         var13 = (int)var8;
         var10000 = var12 + var13;
      }

      return var10000;
   }

   @NotNull
   public String toString() {
      return ULong.toString-impl(this.getFirst()) + ".." + ULong.toString-impl(this.getLast());
   }

   private ULongRange(long start, long endInclusive) {
      super(start, endInclusive, 1L, (DefaultConstructorMarker)null);
   }

   // $FF: synthetic method
   public ULongRange(long start, long endInclusive, DefaultConstructorMarker $constructor_marker) {
      this(start, endInclusive);
   }

   @Metadata(
      mv = {1, 1, 15},
      bv = {1, 0, 3},
      k = 1,
      d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"},
      d2 = {"Lkotlin/ranges/ULongRange$Companion;", "", "()V", "EMPTY", "Lkotlin/ranges/ULongRange;", "getEMPTY", "()Lkotlin/ranges/ULongRange;", "kotlin-stdlib"}
   )
   public static final class Companion {
      @NotNull
      public final ULongRange getEMPTY() {
         return ULongRange.EMPTY;
      }

      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker $constructor_marker) {
         this();
      }
   }
}
