package kotlin.collections;

import kotlin.ExperimentalUnsignedTypes;
import kotlin.Metadata;
import kotlin.UByteArray;
import kotlin.UIntArray;
import kotlin.ULongArray;
import kotlin.UShortArray;
import kotlin.UnsignedKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 2,
   d1 = {"\u00000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0012\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0006\u0010\u0007\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\t\u0010\n\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\f\u0010\r\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u000f\u0010\u0010\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0013\u0010\u0014\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0015\u0010\u0016\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0017\u0010\u0018\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0019\u0010\u001a\u001a\u001a\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u0003H\u0001ø\u0001\u0000¢\u0006\u0004\b\u001c\u0010\u001d\u001a\u001a\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\bH\u0001ø\u0001\u0000¢\u0006\u0004\b\u001e\u0010\u001f\u001a\u001a\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000bH\u0001ø\u0001\u0000¢\u0006\u0004\b \u0010!\u001a\u001a\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000eH\u0001ø\u0001\u0000¢\u0006\u0004\b\"\u0010#\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006$"},
   d2 = {"partition", "", "array", "Lkotlin/UByteArray;", "left", "right", "partition-4UcCI2c", "([BII)I", "Lkotlin/UIntArray;", "partition-oBK06Vg", "([III)I", "Lkotlin/ULongArray;", "partition--nroSd4", "([JII)I", "Lkotlin/UShortArray;", "partition-Aa5vz7o", "([SII)I", "quickSort", "", "quickSort-4UcCI2c", "([BII)V", "quickSort-oBK06Vg", "([III)V", "quickSort--nroSd4", "([JII)V", "quickSort-Aa5vz7o", "([SII)V", "sortArray", "sortArray-GBYM_sE", "([B)V", "sortArray--ajY-9A", "([I)V", "sortArray-QwZRm1k", "([J)V", "sortArray-rL5Bavg", "([S)V", "kotlin-stdlib"}
)
public final class UArraySortingKt {
   @ExperimentalUnsignedTypes
   private static final int partition_4UcCI2c/* $FF was: partition-4UcCI2c*/(byte[] array, int left, int right) {
      int i = left;
      int j = right;
      byte pivot = UByteArray.get-impl(array, (left + right) / 2);

      while(i <= j) {
         while(true) {
            byte tmp = UByteArray.get-impl(array, i);
            boolean var7 = false;
            boolean var9 = false;
            int var10 = tmp & 255;
            var9 = false;
            int var11 = pivot & 255;
            if (Intrinsics.compare(var10, var11) >= 0) {
               while(true) {
                  tmp = UByteArray.get-impl(array, j);
                  var7 = false;
                  var9 = false;
                  var10 = tmp & 255;
                  var9 = false;
                  var11 = pivot & 255;
                  if (Intrinsics.compare(var10, var11) <= 0) {
                     if (i <= j) {
                        tmp = UByteArray.get-impl(array, i);
                        UByteArray.set-VurrAj0(array, i, UByteArray.get-impl(array, j));
                        UByteArray.set-VurrAj0(array, j, tmp);
                        ++i;
                        --j;
                     }
                     break;
                  }

                  --j;
               }
            } else {
               ++i;
            }
         }
      }

      return i;
   }

   @ExperimentalUnsignedTypes
   private static final void quickSort_4UcCI2c/* $FF was: quickSort-4UcCI2c*/(byte[] array, int left, int right) {
      int index = partition-4UcCI2c(array, left, right);
      if (left < index - 1) {
         quickSort-4UcCI2c(array, left, index - 1);
      }

      if (index < right) {
         quickSort-4UcCI2c(array, index, right);
      }

   }

   @ExperimentalUnsignedTypes
   private static final int partition_Aa5vz7o/* $FF was: partition-Aa5vz7o*/(short[] array, int left, int right) {
      int i = left;
      int j = right;
      short pivot = UShortArray.get-impl(array, (left + right) / 2);

      while(i <= j) {
         while(true) {
            short tmp = UShortArray.get-impl(array, i);
            boolean var7 = false;
            boolean var9 = false;
            int var10 = tmp & '\uffff';
            var9 = false;
            int var11 = pivot & '\uffff';
            if (Intrinsics.compare(var10, var11) >= 0) {
               while(true) {
                  tmp = UShortArray.get-impl(array, j);
                  var7 = false;
                  var9 = false;
                  var10 = tmp & '\uffff';
                  var9 = false;
                  var11 = pivot & '\uffff';
                  if (Intrinsics.compare(var10, var11) <= 0) {
                     if (i <= j) {
                        tmp = UShortArray.get-impl(array, i);
                        UShortArray.set-01HTLdE(array, i, UShortArray.get-impl(array, j));
                        UShortArray.set-01HTLdE(array, j, tmp);
                        ++i;
                        --j;
                     }
                     break;
                  }

                  --j;
               }
            } else {
               ++i;
            }
         }
      }

      return i;
   }

   @ExperimentalUnsignedTypes
   private static final void quickSort_Aa5vz7o/* $FF was: quickSort-Aa5vz7o*/(short[] array, int left, int right) {
      int index = partition-Aa5vz7o(array, left, right);
      if (left < index - 1) {
         quickSort-Aa5vz7o(array, left, index - 1);
      }

      if (index < right) {
         quickSort-Aa5vz7o(array, index, right);
      }

   }

   @ExperimentalUnsignedTypes
   private static final int partition_oBK06Vg/* $FF was: partition-oBK06Vg*/(int[] array, int left, int right) {
      int i = left;
      int j = right;
      int pivot = UIntArray.get-impl(array, (left + right) / 2);

      while(i <= j) {
         while(true) {
            int tmp = UIntArray.get-impl(array, i);
            boolean var7 = false;
            if (UnsignedKt.uintCompare(tmp, pivot) >= 0) {
               while(true) {
                  tmp = UIntArray.get-impl(array, j);
                  var7 = false;
                  if (UnsignedKt.uintCompare(tmp, pivot) <= 0) {
                     if (i <= j) {
                        tmp = UIntArray.get-impl(array, i);
                        UIntArray.set-VXSXFK8(array, i, UIntArray.get-impl(array, j));
                        UIntArray.set-VXSXFK8(array, j, tmp);
                        ++i;
                        --j;
                     }
                     break;
                  }

                  --j;
               }
            } else {
               ++i;
            }
         }
      }

      return i;
   }

   @ExperimentalUnsignedTypes
   private static final void quickSort_oBK06Vg/* $FF was: quickSort-oBK06Vg*/(int[] array, int left, int right) {
      int index = partition-oBK06Vg(array, left, right);
      if (left < index - 1) {
         quickSort-oBK06Vg(array, left, index - 1);
      }

      if (index < right) {
         quickSort-oBK06Vg(array, index, right);
      }

   }

   @ExperimentalUnsignedTypes
   private static final int partition__nroSd4/* $FF was: partition--nroSd4*/(long[] array, int left, int right) {
      int i = left;
      int j = right;
      long pivot = ULongArray.get-impl(array, (left + right) / 2);

      while(i <= j) {
         while(true) {
            long tmp = ULongArray.get-impl(array, i);
            boolean var9 = false;
            if (UnsignedKt.ulongCompare(tmp, pivot) >= 0) {
               while(true) {
                  tmp = ULongArray.get-impl(array, j);
                  var9 = false;
                  if (UnsignedKt.ulongCompare(tmp, pivot) <= 0) {
                     if (i <= j) {
                        tmp = ULongArray.get-impl(array, i);
                        ULongArray.set-k8EXiF4(array, i, ULongArray.get-impl(array, j));
                        ULongArray.set-k8EXiF4(array, j, tmp);
                        ++i;
                        --j;
                     }
                     break;
                  }

                  --j;
               }
            } else {
               ++i;
            }
         }
      }

      return i;
   }

   @ExperimentalUnsignedTypes
   private static final void quickSort__nroSd4/* $FF was: quickSort--nroSd4*/(long[] array, int left, int right) {
      int index = partition--nroSd4(array, left, right);
      if (left < index - 1) {
         quickSort--nroSd4(array, left, index - 1);
      }

      if (index < right) {
         quickSort--nroSd4(array, index, right);
      }

   }

   @ExperimentalUnsignedTypes
   public static final void sortArray_GBYM_sE/* $FF was: sortArray-GBYM_sE*/(@NotNull byte[] array) {
      Intrinsics.checkParameterIsNotNull(array, "array");
      quickSort-4UcCI2c(array, 0, UByteArray.getSize-impl(array) - 1);
   }

   @ExperimentalUnsignedTypes
   public static final void sortArray_rL5Bavg/* $FF was: sortArray-rL5Bavg*/(@NotNull short[] array) {
      Intrinsics.checkParameterIsNotNull(array, "array");
      quickSort-Aa5vz7o(array, 0, UShortArray.getSize-impl(array) - 1);
   }

   @ExperimentalUnsignedTypes
   public static final void sortArray__ajY_9A/* $FF was: sortArray--ajY-9A*/(@NotNull int[] array) {
      Intrinsics.checkParameterIsNotNull(array, "array");
      quickSort-oBK06Vg(array, 0, UIntArray.getSize-impl(array) - 1);
   }

   @ExperimentalUnsignedTypes
   public static final void sortArray_QwZRm1k/* $FF was: sortArray-QwZRm1k*/(@NotNull long[] array) {
      Intrinsics.checkParameterIsNotNull(array, "array");
      quickSort--nroSd4(array, 0, ULongArray.getSize-impl(array) - 1);
   }
}
