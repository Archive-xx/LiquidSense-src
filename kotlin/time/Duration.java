package kotlin.time;

import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.DoubleCompanionObject;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b&\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\u0015\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0012\b\u0087@\u0018\u0000 s2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001sB\u0014\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003ø\u0001\u0000¢\u0006\u0004\b\u0004\u0010\u0005J\u001b\u0010%\u001a\u00020\t2\u0006\u0010&\u001a\u00020\u0000H\u0096\u0002ø\u0001\u0000¢\u0006\u0004\b'\u0010(J\u001b\u0010)\u001a\u00020\u00002\u0006\u0010*\u001a\u00020\u0003H\u0086\u0002ø\u0001\u0000¢\u0006\u0004\b+\u0010,J\u001b\u0010)\u001a\u00020\u00002\u0006\u0010*\u001a\u00020\tH\u0086\u0002ø\u0001\u0000¢\u0006\u0004\b+\u0010-J\u001b\u0010)\u001a\u00020\u00032\u0006\u0010&\u001a\u00020\u0000H\u0086\u0002ø\u0001\u0000¢\u0006\u0004\b.\u0010,J\u0013\u0010/\u001a\u0002002\b\u0010&\u001a\u0004\u0018\u000101HÖ\u0003J\t\u00102\u001a\u00020\tHÖ\u0001J\r\u00103\u001a\u000200¢\u0006\u0004\b4\u00105J\r\u00106\u001a\u000200¢\u0006\u0004\b7\u00105J\r\u00108\u001a\u000200¢\u0006\u0004\b9\u00105J\r\u0010:\u001a\u000200¢\u0006\u0004\b;\u00105J\u001b\u0010<\u001a\u00020\u00002\u0006\u0010&\u001a\u00020\u0000H\u0086\u0002ø\u0001\u0000¢\u0006\u0004\b=\u0010,J\u001b\u0010>\u001a\u00020\u00002\u0006\u0010&\u001a\u00020\u0000H\u0086\u0002ø\u0001\u0000¢\u0006\u0004\b?\u0010,J\u0017\u0010@\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002¢\u0006\u0004\bA\u0010(J\u001b\u0010B\u001a\u00020\u00002\u0006\u0010*\u001a\u00020\u0003H\u0086\u0002ø\u0001\u0000¢\u0006\u0004\bC\u0010,J\u001b\u0010B\u001a\u00020\u00002\u0006\u0010*\u001a\u00020\tH\u0086\u0002ø\u0001\u0000¢\u0006\u0004\bC\u0010-J\u008d\u0001\u0010D\u001a\u0002HE\"\u0004\b\u0000\u0010E2u\u0010F\u001aq\u0012\u0013\u0012\u00110\t¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(J\u0012\u0013\u0012\u00110\t¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(K\u0012\u0013\u0012\u00110\t¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(L\u0012\u0013\u0012\u00110\t¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(M\u0012\u0013\u0012\u00110\t¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(N\u0012\u0004\u0012\u0002HE0GH\u0086\b¢\u0006\u0004\bO\u0010PJx\u0010D\u001a\u0002HE\"\u0004\b\u0000\u0010E2`\u0010F\u001a\\\u0012\u0013\u0012\u00110\t¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(K\u0012\u0013\u0012\u00110\t¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(L\u0012\u0013\u0012\u00110\t¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(M\u0012\u0013\u0012\u00110\t¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(N\u0012\u0004\u0012\u0002HE0QH\u0086\b¢\u0006\u0004\bO\u0010RJc\u0010D\u001a\u0002HE\"\u0004\b\u0000\u0010E2K\u0010F\u001aG\u0012\u0013\u0012\u00110\t¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(L\u0012\u0013\u0012\u00110\t¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(M\u0012\u0013\u0012\u00110\t¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(N\u0012\u0004\u0012\u0002HE0SH\u0086\b¢\u0006\u0004\bO\u0010TJN\u0010D\u001a\u0002HE\"\u0004\b\u0000\u0010E26\u0010F\u001a2\u0012\u0013\u0012\u00110V¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(M\u0012\u0013\u0012\u00110\t¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(N\u0012\u0004\u0012\u0002HE0UH\u0086\b¢\u0006\u0004\bO\u0010WJ\u0019\u0010X\u001a\u00020\u00032\n\u0010Y\u001a\u00060Zj\u0002`[¢\u0006\u0004\b\\\u0010]J\u0019\u0010^\u001a\u00020\t2\n\u0010Y\u001a\u00060Zj\u0002`[¢\u0006\u0004\b_\u0010`J\r\u0010a\u001a\u00020b¢\u0006\u0004\bc\u0010dJ\u0019\u0010e\u001a\u00020V2\n\u0010Y\u001a\u00060Zj\u0002`[¢\u0006\u0004\bf\u0010gJ\r\u0010h\u001a\u00020V¢\u0006\u0004\bi\u0010jJ\r\u0010k\u001a\u00020V¢\u0006\u0004\bl\u0010jJ\u000f\u0010m\u001a\u00020bH\u0016¢\u0006\u0004\bn\u0010dJ#\u0010m\u001a\u00020b2\n\u0010Y\u001a\u00060Zj\u0002`[2\b\b\u0002\u0010o\u001a\u00020\t¢\u0006\u0004\bn\u0010pJ\u0013\u0010q\u001a\u00020\u0000H\u0086\u0002ø\u0001\u0000¢\u0006\u0004\br\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u00008Fø\u0001\u0000¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005R\u001a\u0010\b\u001a\u00020\t8@X\u0081\u0004¢\u0006\f\u0012\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\rR\u0011\u0010\u000e\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0005R\u0011\u0010\u0010\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0005R\u0011\u0010\u0012\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0005R\u0011\u0010\u0014\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0005R\u0011\u0010\u0016\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0005R\u0011\u0010\u0018\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u0005R\u0011\u0010\u001a\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u0005R\u001a\u0010\u001c\u001a\u00020\t8@X\u0081\u0004¢\u0006\f\u0012\u0004\b\u001d\u0010\u000b\u001a\u0004\b\u001e\u0010\rR\u001a\u0010\u001f\u001a\u00020\t8@X\u0081\u0004¢\u0006\f\u0012\u0004\b \u0010\u000b\u001a\u0004\b!\u0010\rR\u001a\u0010\"\u001a\u00020\t8@X\u0081\u0004¢\u0006\f\u0012\u0004\b#\u0010\u000b\u001a\u0004\b$\u0010\rR\u000e\u0010\u0002\u001a\u00020\u0003X\u0080\u0004¢\u0006\u0002\n\u0000ø\u0001\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006t"},
   d2 = {"Lkotlin/time/Duration;", "", "value", "", "constructor-impl", "(D)D", "absoluteValue", "getAbsoluteValue-impl", "hoursComponent", "", "hoursComponent$annotations", "()V", "getHoursComponent-impl", "(D)I", "inDays", "getInDays-impl", "inHours", "getInHours-impl", "inMicroseconds", "getInMicroseconds-impl", "inMilliseconds", "getInMilliseconds-impl", "inMinutes", "getInMinutes-impl", "inNanoseconds", "getInNanoseconds-impl", "inSeconds", "getInSeconds-impl", "minutesComponent", "minutesComponent$annotations", "getMinutesComponent-impl", "nanosecondsComponent", "nanosecondsComponent$annotations", "getNanosecondsComponent-impl", "secondsComponent", "secondsComponent$annotations", "getSecondsComponent-impl", "compareTo", "other", "compareTo-LRDsOJo", "(DD)I", "div", "scale", "div-impl", "(DD)D", "(DI)D", "div-LRDsOJo", "equals", "", "", "hashCode", "isFinite", "isFinite-impl", "(D)Z", "isInfinite", "isInfinite-impl", "isNegative", "isNegative-impl", "isPositive", "isPositive-impl", "minus", "minus-LRDsOJo", "plus", "plus-LRDsOJo", "precision", "precision-impl", "times", "times-impl", "toComponents", "T", "action", "Lkotlin/Function5;", "Lkotlin/ParameterName;", "name", "days", "hours", "minutes", "seconds", "nanoseconds", "toComponents-impl", "(DLkotlin/jvm/functions/Function5;)Ljava/lang/Object;", "Lkotlin/Function4;", "(DLkotlin/jvm/functions/Function4;)Ljava/lang/Object;", "Lkotlin/Function3;", "(DLkotlin/jvm/functions/Function3;)Ljava/lang/Object;", "Lkotlin/Function2;", "", "(DLkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "toDouble", "unit", "Ljava/util/concurrent/TimeUnit;", "Lkotlin/time/DurationUnit;", "toDouble-impl", "(DLjava/util/concurrent/TimeUnit;)D", "toInt", "toInt-impl", "(DLjava/util/concurrent/TimeUnit;)I", "toIsoString", "", "toIsoString-impl", "(D)Ljava/lang/String;", "toLong", "toLong-impl", "(DLjava/util/concurrent/TimeUnit;)J", "toLongMilliseconds", "toLongMilliseconds-impl", "(D)J", "toLongNanoseconds", "toLongNanoseconds-impl", "toString", "toString-impl", "decimals", "(DLjava/util/concurrent/TimeUnit;I)Ljava/lang/String;", "unaryMinus", "unaryMinus-impl", "Companion", "kotlin-stdlib"}
)
@SinceKotlin(
   version = "1.3"
)
@ExperimentalTime
public final class Duration implements Comparable<Duration> {
   private final double value;
   private static final double ZERO = constructor-impl(0.0D);
   private static final double INFINITE;
   public static final Duration.Companion Companion = new Duration.Companion((DefaultConstructorMarker)null);

   public int compareTo_LRDsOJo/* $FF was: compareTo-LRDsOJo*/(double var1) {
      return compareTo-LRDsOJo(this.value, var1);
   }

   /** @deprecated */
   // $FF: synthetic method
   @PublishedApi
   public static void hoursComponent$annotations() {
   }

   /** @deprecated */
   // $FF: synthetic method
   @PublishedApi
   public static void minutesComponent$annotations() {
   }

   /** @deprecated */
   // $FF: synthetic method
   @PublishedApi
   public static void secondsComponent$annotations() {
   }

   /** @deprecated */
   // $FF: synthetic method
   @PublishedApi
   public static void nanosecondsComponent$annotations() {
   }

   @NotNull
   public String toString() {
      return toString-impl(this.value);
   }

   // $FF: synthetic method
   private Duration(double value) {
      this.value = value;
   }

   public static final double unaryMinus_impl/* $FF was: unaryMinus-impl*/(double $this) {
      return constructor-impl(-$this);
   }

   public static final double plus_LRDsOJo/* $FF was: plus-LRDsOJo*/(double $this, double other) {
      return constructor-impl($this + other);
   }

   public static final double minus_LRDsOJo/* $FF was: minus-LRDsOJo*/(double $this, double other) {
      return constructor-impl($this - other);
   }

   public static final double times_impl/* $FF was: times-impl*/(double $this, int scale) {
      return constructor-impl($this * (double)scale);
   }

   public static final double times_impl/* $FF was: times-impl*/(double $this, double scale) {
      return constructor-impl($this * scale);
   }

   public static final double div_impl/* $FF was: div-impl*/(double $this, int scale) {
      return constructor-impl($this / (double)scale);
   }

   public static final double div_impl/* $FF was: div-impl*/(double $this, double scale) {
      return constructor-impl($this / scale);
   }

   public static final double div_LRDsOJo/* $FF was: div-LRDsOJo*/(double $this, double other) {
      return $this / other;
   }

   public static final boolean isNegative_impl/* $FF was: isNegative-impl*/(double $this) {
      return $this < (double)0;
   }

   public static final boolean isPositive_impl/* $FF was: isPositive-impl*/(double $this) {
      return $this > (double)0;
   }

   public static final boolean isInfinite_impl/* $FF was: isInfinite-impl*/(double $this) {
      boolean var4 = false;
      return Double.isInfinite($this);
   }

   public static final boolean isFinite_impl/* $FF was: isFinite-impl*/(double $this) {
      boolean var4 = false;
      boolean var7 = false;
      boolean var10000;
      if (!Double.isInfinite($this)) {
         var7 = false;
         if (!Double.isNaN($this)) {
            var10000 = true;
            return var10000;
         }
      }

      var10000 = false;
      return var10000;
   }

   public static final double getAbsoluteValue_impl/* $FF was: getAbsoluteValue-impl*/(double $this) {
      return isNegative-impl($this) ? unaryMinus-impl($this) : $this;
   }

   public static int compareTo_LRDsOJo/* $FF was: compareTo-LRDsOJo*/(double $this, double other) {
      return Double.compare($this, other);
   }

   public static final <T> T toComponents_impl/* $FF was: toComponents-impl*/(double $this, @NotNull Function5<? super Integer, ? super Integer, ? super Integer, ? super Integer, ? super Integer, ? extends T> action) {
      int $i$f$toComponents = 0;
      Intrinsics.checkParameterIsNotNull(action, "action");
      return action.invoke((int)getInDays-impl($this), getHoursComponent-impl($this), getMinutesComponent-impl($this), getSecondsComponent-impl($this), getNanosecondsComponent-impl($this));
   }

   public static final <T> T toComponents_impl/* $FF was: toComponents-impl*/(double $this, @NotNull Function4<? super Integer, ? super Integer, ? super Integer, ? super Integer, ? extends T> action) {
      int $i$f$toComponents = 0;
      Intrinsics.checkParameterIsNotNull(action, "action");
      return action.invoke((int)getInHours-impl($this), getMinutesComponent-impl($this), getSecondsComponent-impl($this), getNanosecondsComponent-impl($this));
   }

   public static final <T> T toComponents_impl/* $FF was: toComponents-impl*/(double $this, @NotNull Function3<? super Integer, ? super Integer, ? super Integer, ? extends T> action) {
      int $i$f$toComponents = 0;
      Intrinsics.checkParameterIsNotNull(action, "action");
      return action.invoke((int)getInMinutes-impl($this), getSecondsComponent-impl($this), getNanosecondsComponent-impl($this));
   }

   public static final <T> T toComponents_impl/* $FF was: toComponents-impl*/(double $this, @NotNull Function2<? super Long, ? super Integer, ? extends T> action) {
      int $i$f$toComponents = 0;
      Intrinsics.checkParameterIsNotNull(action, "action");
      return action.invoke((long)getInSeconds-impl($this), getNanosecondsComponent-impl($this));
   }

   public static final int getHoursComponent_impl/* $FF was: getHoursComponent-impl*/(double $this) {
      return (int)(getInHours-impl($this) % (double)24);
   }

   public static final int getMinutesComponent_impl/* $FF was: getMinutesComponent-impl*/(double $this) {
      return (int)(getInMinutes-impl($this) % (double)60);
   }

   public static final int getSecondsComponent_impl/* $FF was: getSecondsComponent-impl*/(double $this) {
      return (int)(getInSeconds-impl($this) % (double)60);
   }

   public static final int getNanosecondsComponent_impl/* $FF was: getNanosecondsComponent-impl*/(double $this) {
      return (int)(getInNanoseconds-impl($this) % 1.0E9D);
   }

   public static final double toDouble_impl/* $FF was: toDouble-impl*/(double $this, @NotNull TimeUnit unit) {
      Intrinsics.checkParameterIsNotNull(unit, "unit");
      return DurationUnitKt.convertDurationUnit($this, DurationKt.access$getStorageUnit$p(), unit);
   }

   public static final long toLong_impl/* $FF was: toLong-impl*/(double $this, @NotNull TimeUnit unit) {
      Intrinsics.checkParameterIsNotNull(unit, "unit");
      return (long)toDouble-impl($this, unit);
   }

   public static final int toInt_impl/* $FF was: toInt-impl*/(double $this, @NotNull TimeUnit unit) {
      Intrinsics.checkParameterIsNotNull(unit, "unit");
      return (int)toDouble-impl($this, unit);
   }

   public static final double getInDays_impl/* $FF was: getInDays-impl*/(double $this) {
      return toDouble-impl($this, TimeUnit.DAYS);
   }

   public static final double getInHours_impl/* $FF was: getInHours-impl*/(double $this) {
      return toDouble-impl($this, TimeUnit.HOURS);
   }

   public static final double getInMinutes_impl/* $FF was: getInMinutes-impl*/(double $this) {
      return toDouble-impl($this, TimeUnit.MINUTES);
   }

   public static final double getInSeconds_impl/* $FF was: getInSeconds-impl*/(double $this) {
      return toDouble-impl($this, TimeUnit.SECONDS);
   }

   public static final double getInMilliseconds_impl/* $FF was: getInMilliseconds-impl*/(double $this) {
      return toDouble-impl($this, TimeUnit.MILLISECONDS);
   }

   public static final double getInMicroseconds_impl/* $FF was: getInMicroseconds-impl*/(double $this) {
      return toDouble-impl($this, TimeUnit.MICROSECONDS);
   }

   public static final double getInNanoseconds_impl/* $FF was: getInNanoseconds-impl*/(double $this) {
      return toDouble-impl($this, TimeUnit.NANOSECONDS);
   }

   public static final long toLongNanoseconds_impl/* $FF was: toLongNanoseconds-impl*/(double $this) {
      return toLong-impl($this, TimeUnit.NANOSECONDS);
   }

   public static final long toLongMilliseconds_impl/* $FF was: toLongMilliseconds-impl*/(double $this) {
      return toLong-impl($this, TimeUnit.MILLISECONDS);
   }

   @NotNull
   public static String toString_impl/* $FF was: toString-impl*/(double $this) {
      String var10000;
      if (isInfinite-impl($this)) {
         var10000 = String.valueOf($this);
      } else if ($this == 0.0D) {
         var10000 = "0s";
      } else {
         double absNs = getInNanoseconds-impl(getAbsoluteValue-impl($this));
         boolean scientific = false;
         int maxDecimals = 0;
         TimeUnit var7;
         boolean var8;
         boolean var9;
         boolean var11;
         TimeUnit var20;
         if (absNs < 1.0E-6D) {
            var7 = TimeUnit.SECONDS;
            var8 = false;
            var9 = false;
            var11 = false;
            scientific = true;
            var20 = var7;
         } else if (absNs < (double)1) {
            var7 = TimeUnit.NANOSECONDS;
            var8 = false;
            var9 = false;
            var11 = false;
            maxDecimals = 7;
            var20 = var7;
         } else if (absNs < 1000.0D) {
            var20 = TimeUnit.NANOSECONDS;
         } else if (absNs < 1000000.0D) {
            var20 = TimeUnit.MICROSECONDS;
         } else if (absNs < 1.0E9D) {
            var20 = TimeUnit.MILLISECONDS;
         } else if (absNs < 1.0E12D) {
            var20 = TimeUnit.SECONDS;
         } else if (absNs < 6.0E13D) {
            var20 = TimeUnit.MINUTES;
         } else if (absNs < 3.6E15D) {
            var20 = TimeUnit.HOURS;
         } else if (absNs < 8.64E20D) {
            var20 = TimeUnit.DAYS;
         } else {
            var7 = TimeUnit.DAYS;
            var8 = false;
            var9 = false;
            var11 = false;
            scientific = true;
            var20 = var7;
         }

         TimeUnit unit = var20;
         double value = toDouble-impl($this, unit);
         StringBuilder var21 = new StringBuilder();
         String var10001;
         if (scientific) {
            var10001 = FormatToDecimalsKt.formatScientific(value);
         } else if (maxDecimals > 0) {
            var10001 = FormatToDecimalsKt.formatUpToDecimals(value, maxDecimals);
         } else {
            StringBuilder var12 = var21;
            var9 = false;
            double var17 = Math.abs(value);
            var21 = var12;
            var10001 = FormatToDecimalsKt.formatToExactDecimals(value, precision-impl($this, var17));
         }

         var10000 = var21.append(var10001).append(DurationUnitKt.shortName(unit)).toString();
      }

      return var10000;
   }

   private static final int precision_impl/* $FF was: precision-impl*/(double $this, double value) {
      return value < (double)1 ? 3 : (value < (double)10 ? 2 : (value < (double)100 ? 1 : 0));
   }

   @NotNull
   public static final String toString_impl/* $FF was: toString-impl*/(double $this, @NotNull TimeUnit unit, int decimals) {
      Intrinsics.checkParameterIsNotNull(unit, "unit");
      boolean var4 = decimals >= 0;
      boolean var5 = false;
      boolean var6 = false;
      if (!var4) {
         int var7 = false;
         String var12 = "decimals must be not negative, but was " + decimals;
         throw (Throwable)(new IllegalArgumentException(var12.toString()));
      } else if (isInfinite-impl($this)) {
         return String.valueOf($this);
      } else {
         double number = toDouble-impl($this, unit);
         StringBuilder var8 = new StringBuilder();
         var6 = false;
         double var9 = Math.abs(number);
         return var8.append(var9 < 1.0E14D ? FormatToDecimalsKt.formatToExactDecimals(number, RangesKt.coerceAtMost(decimals, 12)) : FormatToDecimalsKt.formatScientific(number)).append(DurationUnitKt.shortName(unit)).toString();
      }
   }

   // $FF: synthetic method
   public static String toString_impl$default/* $FF was: toString-impl$default*/(double var0, TimeUnit var2, int var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var3 = 0;
      }

      return toString-impl(var0, var2, var3);
   }

   @NotNull
   public static final String toIsoString_impl/* $FF was: toIsoString-impl*/(double $this) {
      boolean var2 = false;
      StringBuilder var3 = new StringBuilder();
      boolean var4 = false;
      boolean var5 = false;
      int var7 = false;
      if (isNegative-impl($this)) {
         var3.append('-');
      }

      var3.append("PT");
      double $this$iv = getAbsoluteValue-impl($this);
      int $i$f$toComponents = false;
      int var10000 = (int)getInHours-impl($this$iv);
      int var10001 = getMinutesComponent-impl($this$iv);
      int var10002 = getSecondsComponent-impl($this$iv);
      int nanoseconds = getNanosecondsComponent-impl($this$iv);
      int seconds = var10002;
      int minutes = var10001;
      int hours = var10000;
      int var15 = false;
      boolean hasHours = hours != 0;
      boolean hasSeconds = seconds != 0 || nanoseconds != 0;
      boolean hasMinutes = minutes != 0 || hasSeconds && hasHours;
      if (hasHours) {
         var3.append(hours).append('H');
      }

      if (hasMinutes) {
         var3.append(minutes).append('M');
      }

      if (hasSeconds || !hasHours && !hasMinutes) {
         var3.append(seconds);
         if (nanoseconds != 0) {
            var3.append('.');
            String nss = StringsKt.padStart(String.valueOf(nanoseconds), 9, '0');
            if (nanoseconds % 1000000 == 0) {
               var3.append((CharSequence)nss, 0, 3);
            } else if (nanoseconds % 1000 == 0) {
               var3.append((CharSequence)nss, 0, 6);
            } else {
               var3.append(nss);
            }
         }

         var3.append('S');
      }

      String var20 = var3.toString();
      Intrinsics.checkExpressionValueIsNotNull(var20, "StringBuilder().apply(builderAction).toString()");
      return var20;
   }

   public static double constructor_impl/* $FF was: constructor-impl*/(double value) {
      return value;
   }

   // $FF: synthetic method
   @NotNull
   public static final Duration box_impl/* $FF was: box-impl*/(double v) {
      return new Duration(v);
   }

   public static int hashCode_impl/* $FF was: hashCode-impl*/(double var0) {
      long var10000 = Double.doubleToLongBits(var0);
      return (int)(var10000 ^ var10000 >>> 32);
   }

   public static boolean equals_impl/* $FF was: equals-impl*/(double var0, @Nullable Object var2) {
      if (var2 instanceof Duration) {
         double var3 = ((Duration)var2).unbox-impl();
         if (Double.compare(var0, var3) == 0) {
            return true;
         }
      }

      return false;
   }

   public static final boolean equals_impl0/* $FF was: equals-impl0*/(double p1, double p2) {
      throw null;
   }

   // $FF: synthetic method
   public final double unbox_impl/* $FF was: unbox-impl*/() {
      return this.value;
   }

   static {
      INFINITE = constructor-impl(DoubleCompanionObject.INSTANCE.getPOSITIVE_INFINITY());
   }

   public int hashCode() {
      return hashCode-impl(this.value);
   }

   public boolean equals(Object var1) {
      return equals-impl(this.value, var1);
   }

   @Metadata(
      mv = {1, 1, 15},
      bv = {1, 0, 3},
      k = 1,
      d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J&\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\n\u0010\r\u001a\u00060\u000ej\u0002`\u000f2\n\u0010\u0010\u001a\u00060\u000ej\u0002`\u000fR\u0016\u0010\u0003\u001a\u00020\u0004ø\u0001\u0000¢\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\u0005\u0010\u0006R\u0016\u0010\b\u001a\u00020\u0004ø\u0001\u0000¢\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\t\u0010\u0006\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0011"},
      d2 = {"Lkotlin/time/Duration$Companion;", "", "()V", "INFINITE", "Lkotlin/time/Duration;", "getINFINITE", "()D", "D", "ZERO", "getZERO", "convert", "", "value", "sourceUnit", "Ljava/util/concurrent/TimeUnit;", "Lkotlin/time/DurationUnit;", "targetUnit", "kotlin-stdlib"}
   )
   public static final class Companion {
      public final double getZERO() {
         return Duration.ZERO;
      }

      public final double getINFINITE() {
         return Duration.INFINITE;
      }

      public final double convert(double value, @NotNull TimeUnit sourceUnit, @NotNull TimeUnit targetUnit) {
         Intrinsics.checkParameterIsNotNull(sourceUnit, "sourceUnit");
         Intrinsics.checkParameterIsNotNull(targetUnit, "targetUnit");
         return DurationUnitKt.convertDurationUnit(value, sourceUnit, targetUnit);
      }

      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker $constructor_marker) {
         this();
      }
   }
}
