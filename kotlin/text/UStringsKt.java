package kotlin.text;

import kotlin.ExperimentalUnsignedTypes;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.UByte;
import kotlin.UInt;
import kotlin.ULong;
import kotlin.UShort;
import kotlin.UnsignedKt;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 2,
   d1 = {"\u0000,\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0013\u001a\u001e\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0005\u0010\u0006\u001a\u001e\u0010\u0000\u001a\u00020\u0001*\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000¢\u0006\u0004\b\b\u0010\t\u001a\u001e\u0010\u0000\u001a\u00020\u0001*\u00020\n2\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000¢\u0006\u0004\b\u000b\u0010\f\u001a\u001e\u0010\u0000\u001a\u00020\u0001*\u00020\r2\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000¢\u0006\u0004\b\u000e\u0010\u000f\u001a\u0014\u0010\u0010\u001a\u00020\u0002*\u00020\u0001H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0011\u001a\u001c\u0010\u0010\u001a\u00020\u0002*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0012\u001a\u0011\u0010\u0013\u001a\u0004\u0018\u00010\u0002*\u00020\u0001H\u0007ø\u0001\u0000\u001a\u0019\u0010\u0013\u001a\u0004\u0018\u00010\u0002*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000\u001a\u0014\u0010\u0014\u001a\u00020\u0007*\u00020\u0001H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0015\u001a\u001c\u0010\u0014\u001a\u00020\u0007*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0016\u001a\u0011\u0010\u0017\u001a\u0004\u0018\u00010\u0007*\u00020\u0001H\u0007ø\u0001\u0000\u001a\u0019\u0010\u0017\u001a\u0004\u0018\u00010\u0007*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000\u001a\u0014\u0010\u0018\u001a\u00020\n*\u00020\u0001H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0019\u001a\u001c\u0010\u0018\u001a\u00020\n*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u001a\u001a\u0011\u0010\u001b\u001a\u0004\u0018\u00010\n*\u00020\u0001H\u0007ø\u0001\u0000\u001a\u0019\u0010\u001b\u001a\u0004\u0018\u00010\n*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000\u001a\u0014\u0010\u001c\u001a\u00020\r*\u00020\u0001H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u001d\u001a\u001c\u0010\u001c\u001a\u00020\r*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u001e\u001a\u0011\u0010\u001f\u001a\u0004\u0018\u00010\r*\u00020\u0001H\u0007ø\u0001\u0000\u001a\u0019\u0010\u001f\u001a\u0004\u0018\u00010\r*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006 "},
   d2 = {"toString", "", "Lkotlin/UByte;", "radix", "", "toString-LxnNnR4", "(BI)Ljava/lang/String;", "Lkotlin/UInt;", "toString-V7xB4Y4", "(II)Ljava/lang/String;", "Lkotlin/ULong;", "toString-JSWoG40", "(JI)Ljava/lang/String;", "Lkotlin/UShort;", "toString-olVBNx4", "(SI)Ljava/lang/String;", "toUByte", "(Ljava/lang/String;)B", "(Ljava/lang/String;I)B", "toUByteOrNull", "toUInt", "(Ljava/lang/String;)I", "(Ljava/lang/String;I)I", "toUIntOrNull", "toULong", "(Ljava/lang/String;)J", "(Ljava/lang/String;I)J", "toULongOrNull", "toUShort", "(Ljava/lang/String;)S", "(Ljava/lang/String;I)S", "toUShortOrNull", "kotlin-stdlib"}
)
@JvmName(
   name = "UStringsKt"
)
public final class UStringsKt {
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @NotNull
   public static final String toString_LxnNnR4/* $FF was: toString-LxnNnR4*/(byte $this$toString, int radix) {
      boolean var3 = false;
      int var2 = $this$toString & 255;
      var3 = false;
      String var10000 = Integer.toString(var2, CharsKt.checkRadix(radix));
      Intrinsics.checkExpressionValueIsNotNull(var10000, "java.lang.Integer.toStri…(this, checkRadix(radix))");
      return var10000;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @NotNull
   public static final String toString_olVBNx4/* $FF was: toString-olVBNx4*/(short $this$toString, int radix) {
      boolean var3 = false;
      int var2 = $this$toString & '\uffff';
      var3 = false;
      String var10000 = Integer.toString(var2, CharsKt.checkRadix(radix));
      Intrinsics.checkExpressionValueIsNotNull(var10000, "java.lang.Integer.toStri…(this, checkRadix(radix))");
      return var10000;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @NotNull
   public static final String toString_V7xB4Y4/* $FF was: toString-V7xB4Y4*/(int $this$toString, int radix) {
      boolean var3 = false;
      long var2 = (long)$this$toString & 4294967295L;
      boolean var4 = false;
      String var10000 = Long.toString(var2, CharsKt.checkRadix(radix));
      Intrinsics.checkExpressionValueIsNotNull(var10000, "java.lang.Long.toString(this, checkRadix(radix))");
      return var10000;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @NotNull
   public static final String toString_JSWoG40/* $FF was: toString-JSWoG40*/(long $this$toString, int radix) {
      boolean var5 = false;
      return UnsignedKt.ulongToString($this$toString, CharsKt.checkRadix(radix));
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final byte toUByte(@NotNull String $this$toUByte) {
      Intrinsics.checkParameterIsNotNull($this$toUByte, "$this$toUByte");
      UByte var10000 = toUByteOrNull($this$toUByte);
      if (var10000 != null) {
         return var10000.unbox-impl();
      } else {
         StringsKt.numberFormatError($this$toUByte);
         throw null;
      }
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final byte toUByte(@NotNull String $this$toUByte, int radix) {
      Intrinsics.checkParameterIsNotNull($this$toUByte, "$this$toUByte");
      UByte var10000 = toUByteOrNull($this$toUByte, radix);
      if (var10000 != null) {
         return var10000.unbox-impl();
      } else {
         StringsKt.numberFormatError($this$toUByte);
         throw null;
      }
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final short toUShort(@NotNull String $this$toUShort) {
      Intrinsics.checkParameterIsNotNull($this$toUShort, "$this$toUShort");
      UShort var10000 = toUShortOrNull($this$toUShort);
      if (var10000 != null) {
         return var10000.unbox-impl();
      } else {
         StringsKt.numberFormatError($this$toUShort);
         throw null;
      }
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final short toUShort(@NotNull String $this$toUShort, int radix) {
      Intrinsics.checkParameterIsNotNull($this$toUShort, "$this$toUShort");
      UShort var10000 = toUShortOrNull($this$toUShort, radix);
      if (var10000 != null) {
         return var10000.unbox-impl();
      } else {
         StringsKt.numberFormatError($this$toUShort);
         throw null;
      }
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final int toUInt(@NotNull String $this$toUInt) {
      Intrinsics.checkParameterIsNotNull($this$toUInt, "$this$toUInt");
      UInt var10000 = toUIntOrNull($this$toUInt);
      if (var10000 != null) {
         return var10000.unbox-impl();
      } else {
         StringsKt.numberFormatError($this$toUInt);
         throw null;
      }
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final int toUInt(@NotNull String $this$toUInt, int radix) {
      Intrinsics.checkParameterIsNotNull($this$toUInt, "$this$toUInt");
      UInt var10000 = toUIntOrNull($this$toUInt, radix);
      if (var10000 != null) {
         return var10000.unbox-impl();
      } else {
         StringsKt.numberFormatError($this$toUInt);
         throw null;
      }
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final long toULong(@NotNull String $this$toULong) {
      Intrinsics.checkParameterIsNotNull($this$toULong, "$this$toULong");
      ULong var10000 = toULongOrNull($this$toULong);
      if (var10000 != null) {
         return var10000.unbox-impl();
      } else {
         StringsKt.numberFormatError($this$toULong);
         throw null;
      }
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   public static final long toULong(@NotNull String $this$toULong, int radix) {
      Intrinsics.checkParameterIsNotNull($this$toULong, "$this$toULong");
      ULong var10000 = toULongOrNull($this$toULong, radix);
      if (var10000 != null) {
         return var10000.unbox-impl();
      } else {
         StringsKt.numberFormatError($this$toULong);
         throw null;
      }
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @Nullable
   public static final UByte toUByteOrNull(@NotNull String $this$toUByteOrNull) {
      Intrinsics.checkParameterIsNotNull($this$toUByteOrNull, "$this$toUByteOrNull");
      return toUByteOrNull($this$toUByteOrNull, 10);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @Nullable
   public static final UByte toUByteOrNull(@NotNull String $this$toUByteOrNull, int radix) {
      Intrinsics.checkParameterIsNotNull($this$toUByteOrNull, "$this$toUByteOrNull");
      UInt var10000 = toUIntOrNull($this$toUByteOrNull, radix);
      if (var10000 != null) {
         int var2 = var10000.unbox-impl();
         byte var4 = -1;
         boolean var5 = false;
         boolean var8 = false;
         int var7 = UInt.constructor-impl(var4 & 255);
         var8 = false;
         if (UnsignedKt.uintCompare(var2, var7) > 0) {
            return null;
         } else {
            boolean var9 = false;
            boolean var6 = false;
            return UByte.box-impl(UByte.constructor-impl((byte)var2));
         }
      } else {
         return null;
      }
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @Nullable
   public static final UShort toUShortOrNull(@NotNull String $this$toUShortOrNull) {
      Intrinsics.checkParameterIsNotNull($this$toUShortOrNull, "$this$toUShortOrNull");
      return toUShortOrNull($this$toUShortOrNull, 10);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @Nullable
   public static final UShort toUShortOrNull(@NotNull String $this$toUShortOrNull, int radix) {
      Intrinsics.checkParameterIsNotNull($this$toUShortOrNull, "$this$toUShortOrNull");
      UInt var10000 = toUIntOrNull($this$toUShortOrNull, radix);
      if (var10000 != null) {
         int var2 = var10000.unbox-impl();
         byte var4 = -1;
         boolean var5 = false;
         boolean var8 = false;
         int var7 = UInt.constructor-impl(var4 & '\uffff');
         var8 = false;
         if (UnsignedKt.uintCompare(var2, var7) > 0) {
            return null;
         } else {
            boolean var9 = false;
            boolean var6 = false;
            return UShort.box-impl(UShort.constructor-impl((short)var2));
         }
      } else {
         return null;
      }
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @Nullable
   public static final UInt toUIntOrNull(@NotNull String $this$toUIntOrNull) {
      Intrinsics.checkParameterIsNotNull($this$toUIntOrNull, "$this$toUIntOrNull");
      return toUIntOrNull($this$toUIntOrNull, 10);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @Nullable
   public static final UInt toUIntOrNull(@NotNull String $this$toUIntOrNull, int radix) {
      Intrinsics.checkParameterIsNotNull($this$toUIntOrNull, "$this$toUIntOrNull");
      CharsKt.checkRadix(radix);
      int length = $this$toUIntOrNull.length();
      if (length == 0) {
         return null;
      } else {
         int limit = -1;
         int start = false;
         char firstChar = $this$toUIntOrNull.charAt(0);
         byte start;
         if (firstChar < '0') {
            if (length == 1 || firstChar != '+') {
               return null;
            }

            start = 1;
         } else {
            start = 0;
         }

         int limitForMaxRadix = 119304647;
         int limitBeforeMul = limitForMaxRadix;
         boolean var10 = false;
         int uradix = UInt.constructor-impl(radix);
         int result = 0;
         int i = start;

         for(int var11 = length; i < var11; ++i) {
            int digit = CharsKt.digitOf($this$toUIntOrNull.charAt(i), radix);
            if (digit < 0) {
               return null;
            }

            boolean var14 = false;
            if (UnsignedKt.uintCompare(result, limitBeforeMul) > 0) {
               if (limitBeforeMul != limitForMaxRadix) {
                  return null;
               }

               var14 = false;
               limitBeforeMul = UnsignedKt.uintDivide-J1ME1BU(limit, uradix);
               var14 = false;
               if (UnsignedKt.uintCompare(result, limitBeforeMul) > 0) {
                  return null;
               }
            }

            var14 = false;
            result = UInt.constructor-impl(result * uradix);
            int beforeAdding = result;
            boolean var16 = false;
            int var15 = UInt.constructor-impl(digit);
            var16 = false;
            result = UInt.constructor-impl(result + var15);
            boolean var19 = false;
            if (UnsignedKt.uintCompare(result, beforeAdding) < 0) {
               return null;
            }
         }

         return UInt.box-impl(result);
      }
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @Nullable
   public static final ULong toULongOrNull(@NotNull String $this$toULongOrNull) {
      Intrinsics.checkParameterIsNotNull($this$toULongOrNull, "$this$toULongOrNull");
      return toULongOrNull($this$toULongOrNull, 10);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalUnsignedTypes
   @Nullable
   public static final ULong toULongOrNull(@NotNull String $this$toULongOrNull, int radix) {
      Intrinsics.checkParameterIsNotNull($this$toULongOrNull, "$this$toULongOrNull");
      CharsKt.checkRadix(radix);
      int length = $this$toULongOrNull.length();
      if (length == 0) {
         return null;
      } else {
         long limit = -1L;
         int start = false;
         char firstChar = $this$toULongOrNull.charAt(0);
         byte start;
         if (firstChar < '0') {
            if (length == 1 || firstChar != '+') {
               return null;
            }

            start = 1;
         } else {
            start = 0;
         }

         long limitForMaxRadix = 512409557603043100L;
         long limitBeforeMul = limitForMaxRadix;
         boolean var14 = false;
         long uradix = ULong.constructor-impl((long)radix);
         long result = 0L;
         int i = start;

         for(int var16 = length; i < var16; ++i) {
            int digit = CharsKt.digitOf($this$toULongOrNull.charAt(i), radix);
            if (digit < 0) {
               return null;
            }

            boolean var20 = false;
            if (UnsignedKt.ulongCompare(result, limitBeforeMul) > 0) {
               if (limitBeforeMul != limitForMaxRadix) {
                  return null;
               }

               var20 = false;
               limitBeforeMul = UnsignedKt.ulongDivide-eb3DHEI(limit, uradix);
               var20 = false;
               if (UnsignedKt.ulongCompare(result, limitBeforeMul) > 0) {
                  return null;
               }
            }

            var20 = false;
            result = ULong.constructor-impl(result * uradix);
            long beforeAdding = result;
            boolean var23 = false;
            int var22 = UInt.constructor-impl(digit);
            var23 = false;
            boolean var27 = false;
            long var28 = ULong.constructor-impl((long)var22 & 4294967295L);
            boolean var30 = false;
            result = ULong.constructor-impl(result + var28);
            boolean var32 = false;
            if (UnsignedKt.ulongCompare(result, beforeAdding) < 0) {
               return null;
            }
         }

         return ULong.box-impl(result);
      }
   }
}
