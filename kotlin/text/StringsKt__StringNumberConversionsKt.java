package kotlin.text;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000.\n\u0000\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0005\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\n\n\u0002\b\u0003\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0000\u001a\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005*\u00020\u0003H\u0007¢\u0006\u0002\u0010\u0006\u001a\u001b\u0010\u0004\u001a\u0004\u0018\u00010\u0005*\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u0007¢\u0006\u0002\u0010\t\u001a\u0013\u0010\n\u001a\u0004\u0018\u00010\b*\u00020\u0003H\u0007¢\u0006\u0002\u0010\u000b\u001a\u001b\u0010\n\u001a\u0004\u0018\u00010\b*\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u0007¢\u0006\u0002\u0010\f\u001a\u0013\u0010\r\u001a\u0004\u0018\u00010\u000e*\u00020\u0003H\u0007¢\u0006\u0002\u0010\u000f\u001a\u001b\u0010\r\u001a\u0004\u0018\u00010\u000e*\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u0007¢\u0006\u0002\u0010\u0010\u001a\u0013\u0010\u0011\u001a\u0004\u0018\u00010\u0012*\u00020\u0003H\u0007¢\u0006\u0002\u0010\u0013\u001a\u001b\u0010\u0011\u001a\u0004\u0018\u00010\u0012*\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u0007¢\u0006\u0002\u0010\u0014¨\u0006\u0015"},
   d2 = {"numberFormatError", "", "input", "", "toByteOrNull", "", "(Ljava/lang/String;)Ljava/lang/Byte;", "radix", "", "(Ljava/lang/String;I)Ljava/lang/Byte;", "toIntOrNull", "(Ljava/lang/String;)Ljava/lang/Integer;", "(Ljava/lang/String;I)Ljava/lang/Integer;", "toLongOrNull", "", "(Ljava/lang/String;)Ljava/lang/Long;", "(Ljava/lang/String;I)Ljava/lang/Long;", "toShortOrNull", "", "(Ljava/lang/String;)Ljava/lang/Short;", "(Ljava/lang/String;I)Ljava/lang/Short;", "kotlin-stdlib"},
   xs = "kotlin/text/StringsKt"
)
class StringsKt__StringNumberConversionsKt extends StringsKt__StringNumberConversionsJVMKt {
   @SinceKotlin(
      version = "1.1"
   )
   @Nullable
   public static final Byte toByteOrNull(@NotNull String $this$toByteOrNull) {
      Intrinsics.checkParameterIsNotNull($this$toByteOrNull, "$this$toByteOrNull");
      return StringsKt.toByteOrNull($this$toByteOrNull, 10);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @Nullable
   public static final Byte toByteOrNull(@NotNull String $this$toByteOrNull, int radix) {
      Intrinsics.checkParameterIsNotNull($this$toByteOrNull, "$this$toByteOrNull");
      Integer var10000 = StringsKt.toIntOrNull($this$toByteOrNull, radix);
      if (var10000 != null) {
         int var2 = var10000;
         return var2 >= -128 && var2 <= 127 ? (byte)var2 : null;
      } else {
         return null;
      }
   }

   @SinceKotlin(
      version = "1.1"
   )
   @Nullable
   public static final Short toShortOrNull(@NotNull String $this$toShortOrNull) {
      Intrinsics.checkParameterIsNotNull($this$toShortOrNull, "$this$toShortOrNull");
      return StringsKt.toShortOrNull($this$toShortOrNull, 10);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @Nullable
   public static final Short toShortOrNull(@NotNull String $this$toShortOrNull, int radix) {
      Intrinsics.checkParameterIsNotNull($this$toShortOrNull, "$this$toShortOrNull");
      Integer var10000 = StringsKt.toIntOrNull($this$toShortOrNull, radix);
      if (var10000 != null) {
         int var2 = var10000;
         return var2 >= -32768 && var2 <= 32767 ? (short)var2 : null;
      } else {
         return null;
      }
   }

   @SinceKotlin(
      version = "1.1"
   )
   @Nullable
   public static final Integer toIntOrNull(@NotNull String $this$toIntOrNull) {
      Intrinsics.checkParameterIsNotNull($this$toIntOrNull, "$this$toIntOrNull");
      return StringsKt.toIntOrNull($this$toIntOrNull, 10);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @Nullable
   public static final Integer toIntOrNull(@NotNull String $this$toIntOrNull, int radix) {
      Intrinsics.checkParameterIsNotNull($this$toIntOrNull, "$this$toIntOrNull");
      CharsKt.checkRadix(radix);
      int length = $this$toIntOrNull.length();
      if (length == 0) {
         return null;
      } else {
         int start = false;
         boolean isNegative = false;
         int limit = false;
         char firstChar = $this$toIntOrNull.charAt(0);
         byte start;
         int limit;
         if (firstChar < '0') {
            if (length == 1) {
               return null;
            }

            start = 1;
            if (firstChar == '-') {
               isNegative = true;
               limit = Integer.MIN_VALUE;
            } else {
               if (firstChar != '+') {
                  return null;
               }

               isNegative = false;
               limit = -2147483647;
            }
         } else {
            start = 0;
            isNegative = false;
            limit = -2147483647;
         }

         int limitForMaxRadix = -59652323;
         int limitBeforeMul = limitForMaxRadix;
         int result = 0;
         int i = start;

         for(int var11 = length; i < var11; ++i) {
            int digit = CharsKt.digitOf($this$toIntOrNull.charAt(i), radix);
            if (digit < 0) {
               return null;
            }

            if (result < limitBeforeMul) {
               if (limitBeforeMul != limitForMaxRadix) {
                  return null;
               }

               limitBeforeMul = limit / radix;
               if (result < limitBeforeMul) {
                  return null;
               }
            }

            result *= radix;
            if (result < limit + digit) {
               return null;
            }

            result -= digit;
         }

         return isNegative ? result : -result;
      }
   }

   @SinceKotlin(
      version = "1.1"
   )
   @Nullable
   public static final Long toLongOrNull(@NotNull String $this$toLongOrNull) {
      Intrinsics.checkParameterIsNotNull($this$toLongOrNull, "$this$toLongOrNull");
      return StringsKt.toLongOrNull($this$toLongOrNull, 10);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @Nullable
   public static final Long toLongOrNull(@NotNull String $this$toLongOrNull, int radix) {
      Intrinsics.checkParameterIsNotNull($this$toLongOrNull, "$this$toLongOrNull");
      CharsKt.checkRadix(radix);
      int length = $this$toLongOrNull.length();
      if (length == 0) {
         return null;
      } else {
         int start = false;
         boolean isNegative = false;
         long limit = 0L;
         char firstChar = $this$toLongOrNull.charAt(0);
         byte start;
         if (firstChar < '0') {
            if (length == 1) {
               return null;
            }

            start = 1;
            if (firstChar == '-') {
               isNegative = true;
               limit = Long.MIN_VALUE;
            } else {
               if (firstChar != '+') {
                  return null;
               }

               isNegative = false;
               limit = -9223372036854775807L;
            }
         } else {
            start = 0;
            isNegative = false;
            limit = -9223372036854775807L;
         }

         long limitForMaxRadix = -256204778801521550L;
         long limitBeforeMul = limitForMaxRadix;
         long result = 0L;
         int i = start;

         for(int var15 = length; i < var15; ++i) {
            int digit = CharsKt.digitOf($this$toLongOrNull.charAt(i), radix);
            if (digit < 0) {
               return null;
            }

            if (result < limitBeforeMul) {
               if (limitBeforeMul != limitForMaxRadix) {
                  return null;
               }

               limitBeforeMul = limit / (long)radix;
               if (result < limitBeforeMul) {
                  return null;
               }
            }

            result *= (long)radix;
            if (result < limit + (long)digit) {
               return null;
            }

            result -= (long)digit;
         }

         return isNegative ? result : -result;
      }
   }

   @NotNull
   public static final Void numberFormatError(@NotNull String input) {
      Intrinsics.checkParameterIsNotNull(input, "input");
      throw (Throwable)(new NumberFormatException("Invalid number format: '" + input + '\''));
   }

   public StringsKt__StringNumberConversionsKt() {
   }
}
