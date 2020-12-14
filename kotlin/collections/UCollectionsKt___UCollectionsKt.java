package kotlin.collections;

import java.util.Collection;
import java.util.Iterator;
import kotlin.ExperimentalUnsignedTypes;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.UByte;
import kotlin.UByteArray;
import kotlin.UInt;
import kotlin.UIntArray;
import kotlin.ULong;
import kotlin.ULongArray;
import kotlin.UShort;
import kotlin.UShortArray;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000F\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001c\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0004\u0010\u0005\u001a\u001c\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00010\u0002H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0006\u0010\u0005\u001a\u001c\u0010\u0000\u001a\u00020\u0007*\b\u0012\u0004\u0012\u00020\u00070\u0002H\u0007ø\u0001\u0000¢\u0006\u0004\b\b\u0010\t\u001a\u001c\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\n0\u0002H\u0007ø\u0001\u0000¢\u0006\u0004\b\u000b\u0010\u0005\u001a\u001a\u0010\f\u001a\u00020\r*\b\u0012\u0004\u0012\u00020\u00030\u000eH\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001a\u001a\u0010\u0010\u001a\u00020\u0011*\b\u0012\u0004\u0012\u00020\u00010\u000eH\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0012\u001a\u001a\u0010\u0013\u001a\u00020\u0014*\b\u0012\u0004\u0012\u00020\u00070\u000eH\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0015\u001a\u001a\u0010\u0016\u001a\u00020\u0017*\b\u0012\u0004\u0012\u00020\n0\u000eH\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0018\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0019"},
   d2 = {"sum", "Lkotlin/UInt;", "", "Lkotlin/UByte;", "sumOfUByte", "(Ljava/lang/Iterable;)I", "sumOfUInt", "Lkotlin/ULong;", "sumOfULong", "(Ljava/lang/Iterable;)J", "Lkotlin/UShort;", "sumOfUShort", "toUByteArray", "Lkotlin/UByteArray;", "", "(Ljava/util/Collection;)[B", "toUIntArray", "Lkotlin/UIntArray;", "(Ljava/util/Collection;)[I", "toULongArray", "Lkotlin/ULongArray;", "(Ljava/util/Collection;)[J", "toUShortArray", "Lkotlin/UShortArray;", "(Ljava/util/Collection;)[S", "kotlin-stdlib"},
   xs = "kotlin/collections/UCollectionsKt"
)
class UCollectionsKt___UCollectionsKt {
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @NotNull
   public static final byte[] toUByteArray(@NotNull Collection<UByte> $this$toUByteArray) {
      Intrinsics.checkParameterIsNotNull($this$toUByteArray, "$this$toUByteArray");
      byte[] result = UByteArray.constructor-impl($this$toUByteArray.size());
      int index = 0;
      Iterator var4 = $this$toUByteArray.iterator();

      while(var4.hasNext()) {
         byte element = ((UByte)var4.next()).unbox-impl();
         UByteArray.set-VurrAj0(result, index++, element);
      }

      return result;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @NotNull
   public static final int[] toUIntArray(@NotNull Collection<UInt> $this$toUIntArray) {
      Intrinsics.checkParameterIsNotNull($this$toUIntArray, "$this$toUIntArray");
      int[] result = UIntArray.constructor-impl($this$toUIntArray.size());
      int index = 0;
      Iterator var4 = $this$toUIntArray.iterator();

      while(var4.hasNext()) {
         int element = ((UInt)var4.next()).unbox-impl();
         UIntArray.set-VXSXFK8(result, index++, element);
      }

      return result;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @NotNull
   public static final long[] toULongArray(@NotNull Collection<ULong> $this$toULongArray) {
      Intrinsics.checkParameterIsNotNull($this$toULongArray, "$this$toULongArray");
      long[] result = ULongArray.constructor-impl($this$toULongArray.size());
      int index = 0;
      Iterator var5 = $this$toULongArray.iterator();

      while(var5.hasNext()) {
         long element = ((ULong)var5.next()).unbox-impl();
         ULongArray.set-k8EXiF4(result, index++, element);
      }

      return result;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @NotNull
   public static final short[] toUShortArray(@NotNull Collection<UShort> $this$toUShortArray) {
      Intrinsics.checkParameterIsNotNull($this$toUShortArray, "$this$toUShortArray");
      short[] result = UShortArray.constructor-impl($this$toUShortArray.size());
      int index = 0;
      Iterator var4 = $this$toUShortArray.iterator();

      while(var4.hasNext()) {
         short element = ((UShort)var4.next()).unbox-impl();
         UShortArray.set-01HTLdE(result, index++, element);
      }

      return result;
   }

   @JvmName(
      name = "sumOfUInt"
   )
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final int sumOfUInt(@NotNull Iterable<UInt> $this$sum) {
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
   public static final long sumOfULong(@NotNull Iterable<ULong> $this$sum) {
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
   public static final int sumOfUByte(@NotNull Iterable<UByte> $this$sum) {
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
   public static final int sumOfUShort(@NotNull Iterable<UShort> $this$sum) {
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

   public UCollectionsKt___UCollectionsKt() {
   }
}
