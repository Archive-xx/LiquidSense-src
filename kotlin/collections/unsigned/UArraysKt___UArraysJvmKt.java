package kotlin.collections.unsigned;

import java.util.List;
import java.util.RandomAccess;
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
import kotlin.UnsignedKt;
import kotlin.collections.AbstractList;
import kotlin.collections.ArraysKt;
import kotlin.internal.InlineOnly;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000>\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0016\u001a\u001c\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0003H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0004\u0010\u0005\u001a\u001c\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00060\u0001*\u00020\u0007H\u0007ø\u0001\u0000¢\u0006\u0004\b\b\u0010\t\u001a\u001c\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\n0\u0001*\u00020\u000bH\u0007ø\u0001\u0000¢\u0006\u0004\b\f\u0010\r\u001a\u001c\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0001*\u00020\u000fH\u0007ø\u0001\u0000¢\u0006\u0004\b\u0010\u0010\u0011\u001a2\u0010\u0012\u001a\u00020\u0013*\u00020\u00032\u0006\u0010\u0014\u001a\u00020\u00022\b\b\u0002\u0010\u0015\u001a\u00020\u00132\b\b\u0002\u0010\u0016\u001a\u00020\u0013H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0017\u0010\u0018\u001a2\u0010\u0012\u001a\u00020\u0013*\u00020\u00072\u0006\u0010\u0014\u001a\u00020\u00062\b\b\u0002\u0010\u0015\u001a\u00020\u00132\b\b\u0002\u0010\u0016\u001a\u00020\u0013H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0019\u0010\u001a\u001a2\u0010\u0012\u001a\u00020\u0013*\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\n2\b\b\u0002\u0010\u0015\u001a\u00020\u00132\b\b\u0002\u0010\u0016\u001a\u00020\u0013H\u0007ø\u0001\u0000¢\u0006\u0004\b\u001b\u0010\u001c\u001a2\u0010\u0012\u001a\u00020\u0013*\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u000e2\b\b\u0002\u0010\u0015\u001a\u00020\u00132\b\b\u0002\u0010\u0016\u001a\u00020\u0013H\u0007ø\u0001\u0000¢\u0006\u0004\b\u001d\u0010\u001e\u001a\u001f\u0010\u001f\u001a\u00020\u0002*\u00020\u00032\u0006\u0010 \u001a\u00020\u0013H\u0087\bø\u0001\u0000¢\u0006\u0004\b!\u0010\"\u001a\u001f\u0010\u001f\u001a\u00020\u0006*\u00020\u00072\u0006\u0010 \u001a\u00020\u0013H\u0087\bø\u0001\u0000¢\u0006\u0004\b#\u0010$\u001a\u001f\u0010\u001f\u001a\u00020\n*\u00020\u000b2\u0006\u0010 \u001a\u00020\u0013H\u0087\bø\u0001\u0000¢\u0006\u0004\b%\u0010&\u001a\u001f\u0010\u001f\u001a\u00020\u000e*\u00020\u000f2\u0006\u0010 \u001a\u00020\u0013H\u0087\bø\u0001\u0000¢\u0006\u0004\b'\u0010(\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006)"},
   d2 = {"asList", "", "Lkotlin/UByte;", "Lkotlin/UByteArray;", "asList-GBYM_sE", "([B)Ljava/util/List;", "Lkotlin/UInt;", "Lkotlin/UIntArray;", "asList--ajY-9A", "([I)Ljava/util/List;", "Lkotlin/ULong;", "Lkotlin/ULongArray;", "asList-QwZRm1k", "([J)Ljava/util/List;", "Lkotlin/UShort;", "Lkotlin/UShortArray;", "asList-rL5Bavg", "([S)Ljava/util/List;", "binarySearch", "", "element", "fromIndex", "toIndex", "binarySearch-WpHrYlw", "([BBII)I", "binarySearch-2fe2U9s", "([IIII)I", "binarySearch-K6DWlUc", "([JJII)I", "binarySearch-EtDCXyQ", "([SSII)I", "elementAt", "index", "elementAt-PpDY95g", "([BI)B", "elementAt-qFRl0hI", "([II)I", "elementAt-r7IrZao", "([JI)J", "elementAt-nggk6HY", "([SI)S", "kotlin-stdlib"},
   xs = "kotlin/collections/unsigned/UArraysKt",
   pn = "kotlin.collections"
)
class UArraysKt___UArraysJvmKt {
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @InlineOnly
   private static final int elementAt_qFRl0hI/* $FF was: elementAt-qFRl0hI*/(@NotNull int[] $this$elementAt, int index) {
      int var2 = 0;
      return UIntArray.get-impl($this$elementAt, index);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @InlineOnly
   private static final long elementAt_r7IrZao/* $FF was: elementAt-r7IrZao*/(@NotNull long[] $this$elementAt, int index) {
      int var2 = 0;
      return ULongArray.get-impl($this$elementAt, index);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @InlineOnly
   private static final byte elementAt_PpDY95g/* $FF was: elementAt-PpDY95g*/(@NotNull byte[] $this$elementAt, int index) {
      int var2 = 0;
      return UByteArray.get-impl($this$elementAt, index);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @InlineOnly
   private static final short elementAt_nggk6HY/* $FF was: elementAt-nggk6HY*/(@NotNull short[] $this$elementAt, int index) {
      int var2 = 0;
      return UShortArray.get-impl($this$elementAt, index);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @NotNull
   public static final List<UInt> asList__ajY_9A/* $FF was: asList--ajY-9A*/(@NotNull final int[] $this$asList) {
      Intrinsics.checkParameterIsNotNull($this$asList, "$this$asList");
      return (List)(new RandomAccess() {
         public int getSize() {
            return UIntArray.getSize-impl($this$asList);
         }

         public boolean isEmpty() {
            return UIntArray.isEmpty-impl($this$asList);
         }

         public boolean contains_WZ4Q5Ns/* $FF was: contains-WZ4Q5Ns*/(int element) {
            return UIntArray.contains-WZ4Q5Ns($this$asList, element);
         }

         @NotNull
         public UInt get(int index) {
            return UInt.box-impl(UIntArray.get-impl($this$asList, index));
         }

         public int indexOf_WZ4Q5Ns/* $FF was: indexOf-WZ4Q5Ns*/(int element) {
            int[] var2 = $this$asList;
            boolean var3 = false;
            boolean var6 = false;
            return ArraysKt.indexOf(var2, element);
         }

         public int lastIndexOf_WZ4Q5Ns/* $FF was: lastIndexOf-WZ4Q5Ns*/(int element) {
            int[] var2 = $this$asList;
            boolean var3 = false;
            boolean var6 = false;
            return ArraysKt.lastIndexOf(var2, element);
         }
      });
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @NotNull
   public static final List<ULong> asList_QwZRm1k/* $FF was: asList-QwZRm1k*/(@NotNull final long[] $this$asList) {
      Intrinsics.checkParameterIsNotNull($this$asList, "$this$asList");
      return (List)(new RandomAccess() {
         public int getSize() {
            return ULongArray.getSize-impl($this$asList);
         }

         public boolean isEmpty() {
            return ULongArray.isEmpty-impl($this$asList);
         }

         public boolean contains_VKZWuLQ/* $FF was: contains-VKZWuLQ*/(long element) {
            return ULongArray.contains-VKZWuLQ($this$asList, element);
         }

         @NotNull
         public ULong get(int index) {
            return ULong.box-impl(ULongArray.get-impl($this$asList, index));
         }

         public int indexOf_VKZWuLQ/* $FF was: indexOf-VKZWuLQ*/(long element) {
            long[] var3 = $this$asList;
            boolean var4 = false;
            boolean var8 = false;
            return ArraysKt.indexOf(var3, element);
         }

         public int lastIndexOf_VKZWuLQ/* $FF was: lastIndexOf-VKZWuLQ*/(long element) {
            long[] var3 = $this$asList;
            boolean var4 = false;
            boolean var8 = false;
            return ArraysKt.lastIndexOf(var3, element);
         }
      });
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @NotNull
   public static final List<UByte> asList_GBYM_sE/* $FF was: asList-GBYM_sE*/(@NotNull final byte[] $this$asList) {
      Intrinsics.checkParameterIsNotNull($this$asList, "$this$asList");
      return (List)(new RandomAccess() {
         public int getSize() {
            return UByteArray.getSize-impl($this$asList);
         }

         public boolean isEmpty() {
            return UByteArray.isEmpty-impl($this$asList);
         }

         public boolean contains_7apg3OU/* $FF was: contains-7apg3OU*/(byte element) {
            return UByteArray.contains-7apg3OU($this$asList, element);
         }

         @NotNull
         public UByte get(int index) {
            return UByte.box-impl(UByteArray.get-impl($this$asList, index));
         }

         public int indexOf_7apg3OU/* $FF was: indexOf-7apg3OU*/(byte element) {
            byte[] var2 = $this$asList;
            boolean var3 = false;
            boolean var6 = false;
            return ArraysKt.indexOf(var2, element);
         }

         public int lastIndexOf_7apg3OU/* $FF was: lastIndexOf-7apg3OU*/(byte element) {
            byte[] var2 = $this$asList;
            boolean var3 = false;
            boolean var6 = false;
            return ArraysKt.lastIndexOf(var2, element);
         }
      });
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @NotNull
   public static final List<UShort> asList_rL5Bavg/* $FF was: asList-rL5Bavg*/(@NotNull final short[] $this$asList) {
      Intrinsics.checkParameterIsNotNull($this$asList, "$this$asList");
      return (List)(new RandomAccess() {
         public int getSize() {
            return UShortArray.getSize-impl($this$asList);
         }

         public boolean isEmpty() {
            return UShortArray.isEmpty-impl($this$asList);
         }

         public boolean contains_xj2QHRw/* $FF was: contains-xj2QHRw*/(short element) {
            return UShortArray.contains-xj2QHRw($this$asList, element);
         }

         @NotNull
         public UShort get(int index) {
            return UShort.box-impl(UShortArray.get-impl($this$asList, index));
         }

         public int indexOf_xj2QHRw/* $FF was: indexOf-xj2QHRw*/(short element) {
            short[] var2 = $this$asList;
            boolean var3 = false;
            boolean var6 = false;
            return ArraysKt.indexOf(var2, element);
         }

         public int lastIndexOf_xj2QHRw/* $FF was: lastIndexOf-xj2QHRw*/(short element) {
            short[] var2 = $this$asList;
            boolean var3 = false;
            boolean var6 = false;
            return ArraysKt.lastIndexOf(var2, element);
         }
      });
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final int binarySearch_2fe2U9s/* $FF was: binarySearch-2fe2U9s*/(@NotNull int[] $this$binarySearch, int element, int fromIndex, int toIndex) {
      Intrinsics.checkParameterIsNotNull($this$binarySearch, "$this$binarySearch");
      AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(fromIndex, toIndex, UIntArray.getSize-impl($this$binarySearch));
      boolean var6 = false;
      int signedElement = element;
      int low = fromIndex;
      int high = toIndex - 1;

      while(low <= high) {
         int mid = low + high >>> 1;
         int midVal = $this$binarySearch[mid];
         int cmp = UnsignedKt.uintCompare(midVal, signedElement);
         if (cmp < 0) {
            low = mid + 1;
         } else {
            if (cmp <= 0) {
               return mid;
            }

            high = mid - 1;
         }
      }

      return -(low + 1);
   }

   // $FF: synthetic method
   public static int binarySearch_2fe2U9s$default/* $FF was: binarySearch-2fe2U9s$default*/(int[] var0, int var1, int var2, int var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = 0;
      }

      if ((var4 & 4) != 0) {
         var3 = UIntArray.getSize-impl(var0);
      }

      return UArraysKt.binarySearch-2fe2U9s(var0, var1, var2, var3);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final int binarySearch_K6DWlUc/* $FF was: binarySearch-K6DWlUc*/(@NotNull long[] $this$binarySearch, long element, int fromIndex, int toIndex) {
      Intrinsics.checkParameterIsNotNull($this$binarySearch, "$this$binarySearch");
      AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(fromIndex, toIndex, ULongArray.getSize-impl($this$binarySearch));
      boolean var9 = false;
      long signedElement = element;
      int low = fromIndex;
      int high = toIndex - 1;

      while(low <= high) {
         int mid = low + high >>> 1;
         long midVal = $this$binarySearch[mid];
         int cmp = UnsignedKt.ulongCompare(midVal, signedElement);
         if (cmp < 0) {
            low = mid + 1;
         } else {
            if (cmp <= 0) {
               return mid;
            }

            high = mid - 1;
         }
      }

      return -(low + 1);
   }

   // $FF: synthetic method
   public static int binarySearch_K6DWlUc$default/* $FF was: binarySearch-K6DWlUc$default*/(long[] var0, long var1, int var3, int var4, int var5, Object var6) {
      if ((var5 & 2) != 0) {
         var3 = 0;
      }

      if ((var5 & 4) != 0) {
         var4 = ULongArray.getSize-impl(var0);
      }

      return UArraysKt.binarySearch-K6DWlUc(var0, var1, var3, var4);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final int binarySearch_WpHrYlw/* $FF was: binarySearch-WpHrYlw*/(@NotNull byte[] $this$binarySearch, byte element, int fromIndex, int toIndex) {
      Intrinsics.checkParameterIsNotNull($this$binarySearch, "$this$binarySearch");
      AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(fromIndex, toIndex, UByteArray.getSize-impl($this$binarySearch));
      boolean var6 = false;
      int signedElement = element & 255;
      int low = fromIndex;
      int high = toIndex - 1;

      while(low <= high) {
         int mid = low + high >>> 1;
         byte midVal = $this$binarySearch[mid];
         int cmp = UnsignedKt.uintCompare(midVal, signedElement);
         if (cmp < 0) {
            low = mid + 1;
         } else {
            if (cmp <= 0) {
               return mid;
            }

            high = mid - 1;
         }
      }

      return -(low + 1);
   }

   // $FF: synthetic method
   public static int binarySearch_WpHrYlw$default/* $FF was: binarySearch-WpHrYlw$default*/(byte[] var0, byte var1, int var2, int var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = 0;
      }

      if ((var4 & 4) != 0) {
         var3 = UByteArray.getSize-impl(var0);
      }

      return UArraysKt.binarySearch-WpHrYlw(var0, var1, var2, var3);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final int binarySearch_EtDCXyQ/* $FF was: binarySearch-EtDCXyQ*/(@NotNull short[] $this$binarySearch, short element, int fromIndex, int toIndex) {
      Intrinsics.checkParameterIsNotNull($this$binarySearch, "$this$binarySearch");
      AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(fromIndex, toIndex, UShortArray.getSize-impl($this$binarySearch));
      boolean var6 = false;
      int signedElement = element & '\uffff';
      int low = fromIndex;
      int high = toIndex - 1;

      while(low <= high) {
         int mid = low + high >>> 1;
         short midVal = $this$binarySearch[mid];
         int cmp = UnsignedKt.uintCompare(midVal, signedElement);
         if (cmp < 0) {
            low = mid + 1;
         } else {
            if (cmp <= 0) {
               return mid;
            }

            high = mid - 1;
         }
      }

      return -(low + 1);
   }

   // $FF: synthetic method
   public static int binarySearch_EtDCXyQ$default/* $FF was: binarySearch-EtDCXyQ$default*/(short[] var0, short var1, int var2, int var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = 0;
      }

      if ((var4 & 4) != 0) {
         var3 = UShortArray.getSize-impl(var0);
      }

      return UArraysKt.binarySearch-EtDCXyQ(var0, var1, var2, var3);
   }

   public UArraysKt___UArraysJvmKt() {
   }
}
