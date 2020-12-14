package kotlin;

import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function1;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 2,
   d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a-\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00060\u0005H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u0007\u001a\u001f\u0010\b\u001a\u00020\u00012\n\u0010\t\u001a\u00020\u0001\"\u00020\u0006H\u0087\bø\u0001\u0000¢\u0006\u0004\b\n\u0010\u000b\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\f"},
   d2 = {"UIntArray", "Lkotlin/UIntArray;", "size", "", "init", "Lkotlin/Function1;", "Lkotlin/UInt;", "(ILkotlin/jvm/functions/Function1;)[I", "uintArrayOf", "elements", "uintArrayOf--ajY-9A", "([I)[I", "kotlin-stdlib"}
)
public final class UIntArrayKt {
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @InlineOnly
   private static final int[] UIntArray(int size, Function1<? super Integer, UInt> init) {
      int $i$f$UIntArray = 0;
      int[] var3 = new int[size];

      for(int var4 = 0; var4 < size; ++var4) {
         int var6 = false;
         int var7 = ((UInt)init.invoke(var4)).unbox-impl();
         boolean var8 = false;
         var3[var4] = var7;
      }

      return UIntArray.constructor-impl(var3);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @InlineOnly
   private static final int[] uintArrayOf__ajY_9A/* $FF was: uintArrayOf--ajY-9A*/(int... elements) {
      int var1 = 0;
      return elements;
   }
}
