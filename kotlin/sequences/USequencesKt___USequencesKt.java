package kotlin.sequences;

import java.util.Iterator;
import kotlin.ExperimentalUnsignedTypes;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.UByte;
import kotlin.UInt;
import kotlin.ULong;
import kotlin.UShort;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000\"\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001c\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0004\u0010\u0005\u001a\u001c\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00010\u0002H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0006\u0010\u0005\u001a\u001c\u0010\u0000\u001a\u00020\u0007*\b\u0012\u0004\u0012\u00020\u00070\u0002H\u0007ø\u0001\u0000¢\u0006\u0004\b\b\u0010\t\u001a\u001c\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\n0\u0002H\u0007ø\u0001\u0000¢\u0006\u0004\b\u000b\u0010\u0005\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\f"},
   d2 = {"sum", "Lkotlin/UInt;", "Lkotlin/sequences/Sequence;", "Lkotlin/UByte;", "sumOfUByte", "(Lkotlin/sequences/Sequence;)I", "sumOfUInt", "Lkotlin/ULong;", "sumOfULong", "(Lkotlin/sequences/Sequence;)J", "Lkotlin/UShort;", "sumOfUShort", "kotlin-stdlib"},
   xs = "kotlin/sequences/USequencesKt"
)
class USequencesKt___USequencesKt {
   @JvmName(
      name = "sumOfUInt"
   )
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final int sumOfUInt(@NotNull Sequence<UInt> $this$sum) {
      Intrinsics.checkParameterIsNotNull($this$sum, "$this$sum");
      int sum = 0;

      int element;
      for(Iterator var3 = $this$sum.iterator(); var3.hasNext(); sum = UInt.constructor-impl(sum + element)) {
         element = ((UInt)var3.next()).unbox-impl();
         boolean var5 = false;
      }

      return sum;
   }

   @JvmName(
      name = "sumOfULong"
   )
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final long sumOfULong(@NotNull Sequence<ULong> $this$sum) {
      Intrinsics.checkParameterIsNotNull($this$sum, "$this$sum");
      long sum = 0L;

      long element;
      for(Iterator var5 = $this$sum.iterator(); var5.hasNext(); sum = ULong.constructor-impl(sum + element)) {
         element = ((ULong)var5.next()).unbox-impl();
         boolean var8 = false;
      }

      return sum;
   }

   @JvmName(
      name = "sumOfUByte"
   )
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final int sumOfUByte(@NotNull Sequence<UByte> $this$sum) {
      Intrinsics.checkParameterIsNotNull($this$sum, "$this$sum");
      int sum = 0;

      int var7;
      for(Iterator var3 = $this$sum.iterator(); var3.hasNext(); sum = UInt.constructor-impl(sum + var7)) {
         byte element = ((UByte)var3.next()).unbox-impl();
         boolean var5 = false;
         boolean var8 = false;
         var7 = UInt.constructor-impl(element & 255);
         var8 = false;
      }

      return sum;
   }

   @JvmName(
      name = "sumOfUShort"
   )
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final int sumOfUShort(@NotNull Sequence<UShort> $this$sum) {
      Intrinsics.checkParameterIsNotNull($this$sum, "$this$sum");
      int sum = 0;

      int var7;
      for(Iterator var3 = $this$sum.iterator(); var3.hasNext(); sum = UInt.constructor-impl(sum + var7)) {
         short element = ((UShort)var3.next()).unbox-impl();
         boolean var5 = false;
         boolean var8 = false;
         var7 = UInt.constructor-impl(element & '\uffff');
         var8 = false;
      }

      return sum;
   }

   public USequencesKt___USequencesKt() {
   }
}
