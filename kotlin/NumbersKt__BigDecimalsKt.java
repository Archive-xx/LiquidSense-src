package kotlin;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import kotlin.internal.InlineOnly;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000$\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\u0010\b\n\u0002\u0010\t\n\u0002\b\u0002\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0001H\u0087\n\u001a\u0015\u0010\u0002\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0087\n\u001a\r\u0010\u0004\u001a\u00020\u0001*\u00020\u0001H\u0087\n\u001a\u0015\u0010\u0005\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0087\n\u001a\u0015\u0010\u0006\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0087\n\u001a\u0015\u0010\u0007\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0087\n\u001a\u0015\u0010\b\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0087\n\u001a\u0015\u0010\t\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0087\n\u001a\r\u0010\n\u001a\u00020\u0001*\u00020\u000bH\u0087\b\u001a\u0015\u0010\n\u001a\u00020\u0001*\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0087\b\u001a\r\u0010\n\u001a\u00020\u0001*\u00020\u000eH\u0087\b\u001a\u0015\u0010\n\u001a\u00020\u0001*\u00020\u000e2\u0006\u0010\f\u001a\u00020\rH\u0087\b\u001a\r\u0010\n\u001a\u00020\u0001*\u00020\u000fH\u0087\b\u001a\u0015\u0010\n\u001a\u00020\u0001*\u00020\u000f2\u0006\u0010\f\u001a\u00020\rH\u0087\b\u001a\r\u0010\n\u001a\u00020\u0001*\u00020\u0010H\u0087\b\u001a\u0015\u0010\n\u001a\u00020\u0001*\u00020\u00102\u0006\u0010\f\u001a\u00020\rH\u0087\b\u001a\r\u0010\u0011\u001a\u00020\u0001*\u00020\u0001H\u0087\n¨\u0006\u0012"},
   d2 = {"dec", "Ljava/math/BigDecimal;", "div", "other", "inc", "minus", "mod", "plus", "rem", "times", "toBigDecimal", "", "mathContext", "Ljava/math/MathContext;", "", "", "", "unaryMinus", "kotlin-stdlib"},
   xs = "kotlin/NumbersKt"
)
class NumbersKt__BigDecimalsKt {
   @InlineOnly
   private static final BigDecimal plus(@NotNull BigDecimal $this$plus, BigDecimal other) {
      int $i$f$plus = 0;
      Intrinsics.checkParameterIsNotNull($this$plus, "$this$plus");
      BigDecimal var10000 = $this$plus.add(other);
      Intrinsics.checkExpressionValueIsNotNull(var10000, "this.add(other)");
      return var10000;
   }

   @InlineOnly
   private static final BigDecimal minus(@NotNull BigDecimal $this$minus, BigDecimal other) {
      int $i$f$minus = 0;
      Intrinsics.checkParameterIsNotNull($this$minus, "$this$minus");
      BigDecimal var10000 = $this$minus.subtract(other);
      Intrinsics.checkExpressionValueIsNotNull(var10000, "this.subtract(other)");
      return var10000;
   }

   @InlineOnly
   private static final BigDecimal times(@NotNull BigDecimal $this$times, BigDecimal other) {
      int $i$f$times = 0;
      Intrinsics.checkParameterIsNotNull($this$times, "$this$times");
      BigDecimal var10000 = $this$times.multiply(other);
      Intrinsics.checkExpressionValueIsNotNull(var10000, "this.multiply(other)");
      return var10000;
   }

   @InlineOnly
   private static final BigDecimal div(@NotNull BigDecimal $this$div, BigDecimal other) {
      int $i$f$div = 0;
      Intrinsics.checkParameterIsNotNull($this$div, "$this$div");
      BigDecimal var10000 = $this$div.divide(other, RoundingMode.HALF_EVEN);
      Intrinsics.checkExpressionValueIsNotNull(var10000, "this.divide(other, RoundingMode.HALF_EVEN)");
      return var10000;
   }

   /** @deprecated */
   @Deprecated(
      message = "Use rem(other) instead",
      replaceWith = @ReplaceWith(
   imports = {},
   expression = "rem(other)"
),
      level = DeprecationLevel.ERROR
   )
   @InlineOnly
   private static final BigDecimal mod(@NotNull BigDecimal $this$mod, BigDecimal other) {
      int $i$f$mod = 0;
      Intrinsics.checkParameterIsNotNull($this$mod, "$this$mod");
      BigDecimal var10000 = $this$mod.remainder(other);
      Intrinsics.checkExpressionValueIsNotNull(var10000, "this.remainder(other)");
      return var10000;
   }

   @InlineOnly
   private static final BigDecimal rem(@NotNull BigDecimal $this$rem, BigDecimal other) {
      int $i$f$rem = 0;
      Intrinsics.checkParameterIsNotNull($this$rem, "$this$rem");
      BigDecimal var10000 = $this$rem.remainder(other);
      Intrinsics.checkExpressionValueIsNotNull(var10000, "this.remainder(other)");
      return var10000;
   }

   @InlineOnly
   private static final BigDecimal unaryMinus(@NotNull BigDecimal $this$unaryMinus) {
      int $i$f$unaryMinus = 0;
      Intrinsics.checkParameterIsNotNull($this$unaryMinus, "$this$unaryMinus");
      BigDecimal var10000 = $this$unaryMinus.negate();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "this.negate()");
      return var10000;
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final BigDecimal inc(@NotNull BigDecimal $this$inc) {
      int $i$f$inc = 0;
      Intrinsics.checkParameterIsNotNull($this$inc, "$this$inc");
      BigDecimal var10000 = $this$inc.add(BigDecimal.ONE);
      Intrinsics.checkExpressionValueIsNotNull(var10000, "this.add(BigDecimal.ONE)");
      return var10000;
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final BigDecimal dec(@NotNull BigDecimal $this$dec) {
      int $i$f$dec = 0;
      Intrinsics.checkParameterIsNotNull($this$dec, "$this$dec");
      BigDecimal var10000 = $this$dec.subtract(BigDecimal.ONE);
      Intrinsics.checkExpressionValueIsNotNull(var10000, "this.subtract(BigDecimal.ONE)");
      return var10000;
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final BigDecimal toBigDecimal(int $this$toBigDecimal) {
      int $i$f$toBigDecimal = 0;
      BigDecimal var10000 = BigDecimal.valueOf((long)$this$toBigDecimal);
      Intrinsics.checkExpressionValueIsNotNull(var10000, "BigDecimal.valueOf(this.toLong())");
      return var10000;
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final BigDecimal toBigDecimal(int $this$toBigDecimal, MathContext mathContext) {
      int $i$f$toBigDecimal = 0;
      return new BigDecimal($this$toBigDecimal, mathContext);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final BigDecimal toBigDecimal(long $this$toBigDecimal) {
      int $i$f$toBigDecimal = 0;
      BigDecimal var10000 = BigDecimal.valueOf($this$toBigDecimal);
      Intrinsics.checkExpressionValueIsNotNull(var10000, "BigDecimal.valueOf(this)");
      return var10000;
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final BigDecimal toBigDecimal(long $this$toBigDecimal, MathContext mathContext) {
      int $i$f$toBigDecimal = 0;
      return new BigDecimal($this$toBigDecimal, mathContext);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final BigDecimal toBigDecimal(float $this$toBigDecimal) {
      int $i$f$toBigDecimal = 0;
      return new BigDecimal(String.valueOf($this$toBigDecimal));
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final BigDecimal toBigDecimal(float $this$toBigDecimal, MathContext mathContext) {
      int $i$f$toBigDecimal = 0;
      return new BigDecimal(String.valueOf($this$toBigDecimal), mathContext);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final BigDecimal toBigDecimal(double $this$toBigDecimal) {
      int $i$f$toBigDecimal = 0;
      return new BigDecimal(String.valueOf($this$toBigDecimal));
   }

   @SinceKotlin(
      version = "1.2"
   )
   @InlineOnly
   private static final BigDecimal toBigDecimal(double $this$toBigDecimal, MathContext mathContext) {
      int $i$f$toBigDecimal = 0;
      return new BigDecimal(String.valueOf($this$toBigDecimal), mathContext);
   }

   public NumbersKt__BigDecimalsKt() {
   }
}
