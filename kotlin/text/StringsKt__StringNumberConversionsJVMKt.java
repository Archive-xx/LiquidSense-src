package kotlin.text;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000X\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0005\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\t\n\u0000\n\u0002\u0010\n\n\u0002\b\u0002\u001a4\u0010\u0000\u001a\u0004\u0018\u0001H\u0001\"\u0004\b\u0000\u0010\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u0002H\u00010\u0005H\u0082\b¢\u0006\u0004\b\u0006\u0010\u0007\u001a\r\u0010\b\u001a\u00020\t*\u00020\u0003H\u0087\b\u001a\u0015\u0010\b\u001a\u00020\t*\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u0087\b\u001a\u000e\u0010\f\u001a\u0004\u0018\u00010\t*\u00020\u0003H\u0007\u001a\u0016\u0010\f\u001a\u0004\u0018\u00010\t*\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u0007\u001a\r\u0010\r\u001a\u00020\u000e*\u00020\u0003H\u0087\b\u001a\u0015\u0010\r\u001a\u00020\u000e*\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010H\u0087\b\u001a\u000e\u0010\u0011\u001a\u0004\u0018\u00010\u000e*\u00020\u0003H\u0007\u001a\u0016\u0010\u0011\u001a\u0004\u0018\u00010\u000e*\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010H\u0007\u001a\r\u0010\u0012\u001a\u00020\u0013*\u00020\u0003H\u0087\b\u001a\r\u0010\u0014\u001a\u00020\u0015*\u00020\u0003H\u0087\b\u001a\u0015\u0010\u0014\u001a\u00020\u0015*\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010H\u0087\b\u001a\r\u0010\u0016\u001a\u00020\u0017*\u00020\u0003H\u0087\b\u001a\u0013\u0010\u0018\u001a\u0004\u0018\u00010\u0017*\u00020\u0003H\u0007¢\u0006\u0002\u0010\u0019\u001a\r\u0010\u001a\u001a\u00020\u001b*\u00020\u0003H\u0087\b\u001a\u0013\u0010\u001c\u001a\u0004\u0018\u00010\u001b*\u00020\u0003H\u0007¢\u0006\u0002\u0010\u001d\u001a\r\u0010\u001e\u001a\u00020\u0010*\u00020\u0003H\u0087\b\u001a\u0015\u0010\u001e\u001a\u00020\u0010*\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010H\u0087\b\u001a\r\u0010\u001f\u001a\u00020 *\u00020\u0003H\u0087\b\u001a\u0015\u0010\u001f\u001a\u00020 *\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010H\u0087\b\u001a\r\u0010!\u001a\u00020\"*\u00020\u0003H\u0087\b\u001a\u0015\u0010!\u001a\u00020\"*\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010H\u0087\b\u001a\u0015\u0010#\u001a\u00020\u0003*\u00020\u00152\u0006\u0010\u000f\u001a\u00020\u0010H\u0087\b\u001a\u0015\u0010#\u001a\u00020\u0003*\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u0010H\u0087\b\u001a\u0015\u0010#\u001a\u00020\u0003*\u00020 2\u0006\u0010\u000f\u001a\u00020\u0010H\u0087\b\u001a\u0015\u0010#\u001a\u00020\u0003*\u00020\"2\u0006\u0010\u000f\u001a\u00020\u0010H\u0087\b¨\u0006$"},
   d2 = {"screenFloatValue", "T", "str", "", "parse", "Lkotlin/Function1;", "screenFloatValue$StringsKt__StringNumberConversionsJVMKt", "(Ljava/lang/String;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "toBigDecimal", "Ljava/math/BigDecimal;", "mathContext", "Ljava/math/MathContext;", "toBigDecimalOrNull", "toBigInteger", "Ljava/math/BigInteger;", "radix", "", "toBigIntegerOrNull", "toBoolean", "", "toByte", "", "toDouble", "", "toDoubleOrNull", "(Ljava/lang/String;)Ljava/lang/Double;", "toFloat", "", "toFloatOrNull", "(Ljava/lang/String;)Ljava/lang/Float;", "toInt", "toLong", "", "toShort", "", "toString", "kotlin-stdlib"},
   xs = "kotlin/text/StringsKt"
)
class StringsKt__StringNumberConversionsJVMKt extends StringsKt__StringBuilderKt {
   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final String toString(byte $this$toString, int radix) {
      int $i$f$toString = 0;
      int var4 = CharsKt.checkRadix(radix);
      boolean var5 = false;
      String var10000 = Integer.toString($this$toString, CharsKt.checkRadix(var4));
      Intrinsics.checkExpressionValueIsNotNull(var10000, "java.lang.Integer.toStri…(this, checkRadix(radix))");
      return var10000;
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final String toString(short $this$toString, int radix) {
      int $i$f$toString = 0;
      int var4 = CharsKt.checkRadix(radix);
      boolean var5 = false;
      String var10000 = Integer.toString($this$toString, CharsKt.checkRadix(var4));
      Intrinsics.checkExpressionValueIsNotNull(var10000, "java.lang.Integer.toStri…(this, checkRadix(radix))");
      return var10000;
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final String toString(int $this$toString, int radix) {
      int $i$f$toString = 0;
      String var10000 = Integer.toString($this$toString, CharsKt.checkRadix(radix));
      Intrinsics.checkExpressionValueIsNotNull(var10000, "java.lang.Integer.toStri…(this, checkRadix(radix))");
      return var10000;
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final String toString(long $this$toString, int radix) {
      int $i$f$toString = 0;
      String var10000 = Long.toString($this$toString, CharsKt.checkRadix(radix));
      Intrinsics.checkExpressionValueIsNotNull(var10000, "java.lang.Long.toString(this, checkRadix(radix))");
      return var10000;
   }

   @InlineOnly
   private static final boolean toBoolean(@NotNull String $this$toBoolean) {
      int $i$f$toBoolean = 0;
      return Boolean.parseBoolean($this$toBoolean);
   }

   @InlineOnly
   private static final byte toByte(@NotNull String $this$toByte) {
      int $i$f$toByte = 0;
      return Byte.parseByte($this$toByte);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final byte toByte(@NotNull String $this$toByte, int radix) {
      int $i$f$toByte = 0;
      return Byte.parseByte($this$toByte, CharsKt.checkRadix(radix));
   }

   @InlineOnly
   private static final short toShort(@NotNull String $this$toShort) {
      int $i$f$toShort = 0;
      return Short.parseShort($this$toShort);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final short toShort(@NotNull String $this$toShort, int radix) {
      int $i$f$toShort = 0;
      return Short.parseShort($this$toShort, CharsKt.checkRadix(radix));
   }

   @InlineOnly
   private static final int toInt(@NotNull String $this$toInt) {
      int $i$f$toInt = 0;
      return Integer.parseInt($this$toInt);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final int toInt(@NotNull String $this$toInt, int radix) {
      int $i$f$toInt = 0;
      return Integer.parseInt($this$toInt, CharsKt.checkRadix(radix));
   }

   @InlineOnly
   private static final long toLong(@NotNull String $this$toLong) {
      int $i$f$toLong = 0;
      return Long.parseLong($this$toLong);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final long toLong(@NotNull String $this$toLong, int radix) {
      int $i$f$toLong = 0;
      return Long.parseLong($this$toLong, CharsKt.checkRadix(radix));
   }

   @InlineOnly
   private static final float toFloat(@NotNull String $this$toFloat) {
      int $i$f$toFloat = 0;
      return Float.parseFloat($this$toFloat);
   }

   @InlineOnly
   private static final double toDouble(@NotNull String $this$toDouble) {
      int $i$f$toDouble = 0;
      return Double.parseDouble($this$toDouble);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @Nullable
   public static final Float toFloatOrNull(@NotNull String $this$toFloatOrNull) {
      Intrinsics.checkParameterIsNotNull($this$toFloatOrNull, "$this$toFloatOrNull");
      boolean var1 = false;

      Float var2;
      try {
         Float var10000;
         if (ScreenFloatValueRegEx.value.matches((CharSequence)$this$toFloatOrNull)) {
            int var3 = false;
            var10000 = Float.parseFloat($this$toFloatOrNull);
         } else {
            var10000 = null;
         }

         var2 = var10000;
      } catch (NumberFormatException var4) {
         var2 = null;
      }

      return var2;
   }

   @SinceKotlin(
      version = "1.1"
   )
   @Nullable
   public static final Double toDoubleOrNull(@NotNull String $this$toDoubleOrNull) {
      Intrinsics.checkParameterIsNotNull($this$toDoubleOrNull, "$this$toDoubleOrNull");
      boolean var1 = false;

      Double var2;
      try {
         Double var10000;
         if (ScreenFloatValueRegEx.value.matches((CharSequence)$this$toDoubleOrNull)) {
            int var3 = false;
            var10000 = Double.parseDouble($this$toDoubleOrNull);
         } else {
            var10000 = null;
         }

         var2 = var10000;
      } catch (NumberFormatException var4) {
         var2 = null;
      }

      return var2;
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final BigInteger toBigInteger(@NotNull String $this$toBigInteger) {
      int $i$f$toBigInteger = 0;
      return new BigInteger($this$toBigInteger);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final BigInteger toBigInteger(@NotNull String $this$toBigInteger, int radix) {
      int $i$f$toBigInteger = 0;
      return new BigInteger($this$toBigInteger, CharsKt.checkRadix(radix));
   }

   @SinceKotlin(
      version = "1.2"
   )
   @Nullable
   public static final BigInteger toBigIntegerOrNull(@NotNull String $this$toBigIntegerOrNull) {
      Intrinsics.checkParameterIsNotNull($this$toBigIntegerOrNull, "$this$toBigIntegerOrNull");
      return StringsKt.toBigIntegerOrNull($this$toBigIntegerOrNull, 10);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @Nullable
   public static final BigInteger toBigIntegerOrNull(@NotNull String $this$toBigIntegerOrNull, int radix) {
      Intrinsics.checkParameterIsNotNull($this$toBigIntegerOrNull, "$this$toBigIntegerOrNull");
      CharsKt.checkRadix(radix);
      int length = $this$toBigIntegerOrNull.length();
      switch(length) {
      case 0:
         return null;
      case 1:
         if (CharsKt.digitOf($this$toBigIntegerOrNull.charAt(0), radix) < 0) {
            return null;
         }
         break;
      default:
         int start = $this$toBigIntegerOrNull.charAt(0) == '-' ? 1 : 0;
         int index = start;

         for(int var5 = length; index < var5; ++index) {
            if (CharsKt.digitOf($this$toBigIntegerOrNull.charAt(index), radix) < 0) {
               return null;
            }
         }
      }

      boolean var6 = false;
      return new BigInteger($this$toBigIntegerOrNull, CharsKt.checkRadix(radix));
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final BigDecimal toBigDecimal(@NotNull String $this$toBigDecimal) {
      int $i$f$toBigDecimal = 0;
      return new BigDecimal($this$toBigDecimal);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final BigDecimal toBigDecimal(@NotNull String $this$toBigDecimal, MathContext mathContext) {
      int $i$f$toBigDecimal = 0;
      return new BigDecimal($this$toBigDecimal, mathContext);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @Nullable
   public static final BigDecimal toBigDecimalOrNull(@NotNull String $this$toBigDecimalOrNull) {
      Intrinsics.checkParameterIsNotNull($this$toBigDecimalOrNull, "$this$toBigDecimalOrNull");
      boolean var1 = false;

      BigDecimal var2;
      try {
         BigDecimal var10000;
         if (ScreenFloatValueRegEx.value.matches((CharSequence)$this$toBigDecimalOrNull)) {
            int var3 = false;
            boolean var5 = false;
            var10000 = new BigDecimal($this$toBigDecimalOrNull);
         } else {
            var10000 = null;
         }

         var2 = var10000;
      } catch (NumberFormatException var6) {
         var2 = null;
      }

      return var2;
   }

   @SinceKotlin(
      version = "1.2"
   )
   @Nullable
   public static final BigDecimal toBigDecimalOrNull(@NotNull String $this$toBigDecimalOrNull, @NotNull MathContext mathContext) {
      Intrinsics.checkParameterIsNotNull($this$toBigDecimalOrNull, "$this$toBigDecimalOrNull");
      Intrinsics.checkParameterIsNotNull(mathContext, "mathContext");
      boolean var2 = false;

      BigDecimal var8;
      try {
         BigDecimal var10000;
         if (ScreenFloatValueRegEx.value.matches((CharSequence)$this$toBigDecimalOrNull)) {
            int var4 = false;
            boolean var7 = false;
            var10000 = new BigDecimal($this$toBigDecimalOrNull, mathContext);
         } else {
            var10000 = null;
         }

         var8 = var10000;
      } catch (NumberFormatException var9) {
         var8 = null;
      }

      return var8;
   }

   private static final <T> T screenFloatValue$StringsKt__StringNumberConversionsJVMKt(String str, Function1<? super String, ? extends T> parse) {
      byte var2 = 0;

      Object var3;
      try {
         var3 = ScreenFloatValueRegEx.value.matches((CharSequence)str) ? parse.invoke(str) : null;
      } catch (NumberFormatException var5) {
         var3 = null;
      }

      return var3;
   }

   public StringsKt__StringNumberConversionsJVMKt() {
   }
}
