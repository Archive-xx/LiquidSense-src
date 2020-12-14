package kotlin.time;

import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.internal.InlineOnly;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 2,
   d1 = {"\u0000.\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u001c\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u001f\u0010%\u001a\u00020\u0007*\u00020\b2\u0006\u0010&\u001a\u00020\u0007H\u0087\nø\u0001\u0000¢\u0006\u0004\b'\u0010(\u001a\u001f\u0010%\u001a\u00020\u0007*\u00020\r2\u0006\u0010&\u001a\u00020\u0007H\u0087\nø\u0001\u0000¢\u0006\u0004\b)\u0010*\u001a \u0010+\u001a\u00020\u0007*\u00020\b2\n\u0010,\u001a\u00060\u0001j\u0002`-H\u0007ø\u0001\u0000¢\u0006\u0002\u0010.\u001a \u0010+\u001a\u00020\u0007*\u00020\r2\n\u0010,\u001a\u00060\u0001j\u0002`-H\u0007ø\u0001\u0000¢\u0006\u0002\u0010/\u001a \u0010+\u001a\u00020\u0007*\u00020\u00102\n\u0010,\u001a\u00060\u0001j\u0002`-H\u0007ø\u0001\u0000¢\u0006\u0002\u00100\"\u001b\u0010\u0000\u001a\u00020\u00018Â\u0002X\u0082\u0004¢\u0006\f\u0012\u0004\b\u0002\u0010\u0003\u001a\u0004\b\u0004\u0010\u0005\"!\u0010\u0006\u001a\u00020\u0007*\u00020\b8FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\f\"!\u0010\u0006\u001a\u00020\u0007*\u00020\r8FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\t\u0010\u000e\u001a\u0004\b\u000b\u0010\u000f\"!\u0010\u0006\u001a\u00020\u0007*\u00020\u00108FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\t\u0010\u0011\u001a\u0004\b\u000b\u0010\u0012\"!\u0010\u0013\u001a\u00020\u0007*\u00020\b8FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u0014\u0010\n\u001a\u0004\b\u0015\u0010\f\"!\u0010\u0013\u001a\u00020\u0007*\u00020\r8FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u0014\u0010\u000e\u001a\u0004\b\u0015\u0010\u000f\"!\u0010\u0013\u001a\u00020\u0007*\u00020\u00108FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u0014\u0010\u0011\u001a\u0004\b\u0015\u0010\u0012\"!\u0010\u0016\u001a\u00020\u0007*\u00020\b8FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u0017\u0010\n\u001a\u0004\b\u0018\u0010\f\"!\u0010\u0016\u001a\u00020\u0007*\u00020\r8FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u0017\u0010\u000e\u001a\u0004\b\u0018\u0010\u000f\"!\u0010\u0016\u001a\u00020\u0007*\u00020\u00108FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u0017\u0010\u0011\u001a\u0004\b\u0018\u0010\u0012\"!\u0010\u0019\u001a\u00020\u0007*\u00020\b8FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u001a\u0010\n\u001a\u0004\b\u001b\u0010\f\"!\u0010\u0019\u001a\u00020\u0007*\u00020\r8FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u001a\u0010\u000e\u001a\u0004\b\u001b\u0010\u000f\"!\u0010\u0019\u001a\u00020\u0007*\u00020\u00108FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u001a\u0010\u0011\u001a\u0004\b\u001b\u0010\u0012\"!\u0010\u001c\u001a\u00020\u0007*\u00020\b8FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u001d\u0010\n\u001a\u0004\b\u001e\u0010\f\"!\u0010\u001c\u001a\u00020\u0007*\u00020\r8FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u001d\u0010\u000e\u001a\u0004\b\u001e\u0010\u000f\"!\u0010\u001c\u001a\u00020\u0007*\u00020\u00108FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u001d\u0010\u0011\u001a\u0004\b\u001e\u0010\u0012\"!\u0010\u001f\u001a\u00020\u0007*\u00020\b8FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b \u0010\n\u001a\u0004\b!\u0010\f\"!\u0010\u001f\u001a\u00020\u0007*\u00020\r8FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b \u0010\u000e\u001a\u0004\b!\u0010\u000f\"!\u0010\u001f\u001a\u00020\u0007*\u00020\u00108FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b \u0010\u0011\u001a\u0004\b!\u0010\u0012\"!\u0010\"\u001a\u00020\u0007*\u00020\b8FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b#\u0010\n\u001a\u0004\b$\u0010\f\"!\u0010\"\u001a\u00020\u0007*\u00020\r8FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b#\u0010\u000e\u001a\u0004\b$\u0010\u000f\"!\u0010\"\u001a\u00020\u0007*\u00020\u00108FX\u0087\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b#\u0010\u0011\u001a\u0004\b$\u0010\u0012\u0082\u0002\u0004\n\u0002\b\u0019¨\u00061"},
   d2 = {"storageUnit", "Ljava/util/concurrent/TimeUnit;", "storageUnit$annotations", "()V", "getStorageUnit", "()Ljava/util/concurrent/TimeUnit;", "days", "Lkotlin/time/Duration;", "", "days$annotations", "(D)V", "getDays", "(D)D", "", "(I)V", "(I)D", "", "(J)V", "(J)D", "hours", "hours$annotations", "getHours", "microseconds", "microseconds$annotations", "getMicroseconds", "milliseconds", "milliseconds$annotations", "getMilliseconds", "minutes", "minutes$annotations", "getMinutes", "nanoseconds", "nanoseconds$annotations", "getNanoseconds", "seconds", "seconds$annotations", "getSeconds", "times", "duration", "times-kIfJnKk", "(DD)D", "times-mvk6XK0", "(ID)D", "toDuration", "unit", "Lkotlin/time/DurationUnit;", "(DLjava/util/concurrent/TimeUnit;)D", "(ILjava/util/concurrent/TimeUnit;)D", "(JLjava/util/concurrent/TimeUnit;)D", "kotlin-stdlib"}
)
public final class DurationKt {
   /** @deprecated */
   // $FF: synthetic method
   private static void storageUnit$annotations() {
   }

   private static final TimeUnit getStorageUnit() {
      int $i$f$getStorageUnit = 0;
      return TimeUnit.NANOSECONDS;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalTime
   public static final double toDuration(int $this$toDuration, @NotNull TimeUnit unit) {
      Intrinsics.checkParameterIsNotNull(unit, "unit");
      return toDuration((double)$this$toDuration, unit);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalTime
   public static final double toDuration(long $this$toDuration, @NotNull TimeUnit unit) {
      Intrinsics.checkParameterIsNotNull(unit, "unit");
      return toDuration((double)$this$toDuration, unit);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalTime
   public static final double toDuration(double $this$toDuration, @NotNull TimeUnit unit) {
      Intrinsics.checkParameterIsNotNull(unit, "unit");
      int $i$f$getStorageUnit = false;
      TimeUnit var7 = TimeUnit.NANOSECONDS;
      return Duration.constructor-impl(DurationUnitKt.convertDurationUnit($this$toDuration, unit, var7));
   }

   /** @deprecated */
   // $FF: synthetic method
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalTime
   public static void nanoseconds$annotations(int var0) {
   }

   public static final double getNanoseconds(int $this$nanoseconds) {
      return toDuration($this$nanoseconds, TimeUnit.NANOSECONDS);
   }

   /** @deprecated */
   // $FF: synthetic method
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalTime
   public static void nanoseconds$annotations(long var0) {
   }

   public static final double getNanoseconds(long $this$nanoseconds) {
      return toDuration($this$nanoseconds, TimeUnit.NANOSECONDS);
   }

   /** @deprecated */
   // $FF: synthetic method
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalTime
   public static void nanoseconds$annotations(double var0) {
   }

   public static final double getNanoseconds(double $this$nanoseconds) {
      return toDuration($this$nanoseconds, TimeUnit.NANOSECONDS);
   }

   /** @deprecated */
   // $FF: synthetic method
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalTime
   public static void microseconds$annotations(int var0) {
   }

   public static final double getMicroseconds(int $this$microseconds) {
      return toDuration($this$microseconds, TimeUnit.MICROSECONDS);
   }

   /** @deprecated */
   // $FF: synthetic method
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalTime
   public static void microseconds$annotations(long var0) {
   }

   public static final double getMicroseconds(long $this$microseconds) {
      return toDuration($this$microseconds, TimeUnit.MICROSECONDS);
   }

   /** @deprecated */
   // $FF: synthetic method
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalTime
   public static void microseconds$annotations(double var0) {
   }

   public static final double getMicroseconds(double $this$microseconds) {
      return toDuration($this$microseconds, TimeUnit.MICROSECONDS);
   }

   /** @deprecated */
   // $FF: synthetic method
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalTime
   public static void milliseconds$annotations(int var0) {
   }

   public static final double getMilliseconds(int $this$milliseconds) {
      return toDuration($this$milliseconds, TimeUnit.MILLISECONDS);
   }

   /** @deprecated */
   // $FF: synthetic method
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalTime
   public static void milliseconds$annotations(long var0) {
   }

   public static final double getMilliseconds(long $this$milliseconds) {
      return toDuration($this$milliseconds, TimeUnit.MILLISECONDS);
   }

   /** @deprecated */
   // $FF: synthetic method
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalTime
   public static void milliseconds$annotations(double var0) {
   }

   public static final double getMilliseconds(double $this$milliseconds) {
      return toDuration($this$milliseconds, TimeUnit.MILLISECONDS);
   }

   /** @deprecated */
   // $FF: synthetic method
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalTime
   public static void seconds$annotations(int var0) {
   }

   public static final double getSeconds(int $this$seconds) {
      return toDuration($this$seconds, TimeUnit.SECONDS);
   }

   /** @deprecated */
   // $FF: synthetic method
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalTime
   public static void seconds$annotations(long var0) {
   }

   public static final double getSeconds(long $this$seconds) {
      return toDuration($this$seconds, TimeUnit.SECONDS);
   }

   /** @deprecated */
   // $FF: synthetic method
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalTime
   public static void seconds$annotations(double var0) {
   }

   public static final double getSeconds(double $this$seconds) {
      return toDuration($this$seconds, TimeUnit.SECONDS);
   }

   /** @deprecated */
   // $FF: synthetic method
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalTime
   public static void minutes$annotations(int var0) {
   }

   public static final double getMinutes(int $this$minutes) {
      return toDuration($this$minutes, TimeUnit.MINUTES);
   }

   /** @deprecated */
   // $FF: synthetic method
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalTime
   public static void minutes$annotations(long var0) {
   }

   public static final double getMinutes(long $this$minutes) {
      return toDuration($this$minutes, TimeUnit.MINUTES);
   }

   /** @deprecated */
   // $FF: synthetic method
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalTime
   public static void minutes$annotations(double var0) {
   }

   public static final double getMinutes(double $this$minutes) {
      return toDuration($this$minutes, TimeUnit.MINUTES);
   }

   /** @deprecated */
   // $FF: synthetic method
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalTime
   public static void hours$annotations(int var0) {
   }

   public static final double getHours(int $this$hours) {
      return toDuration($this$hours, TimeUnit.HOURS);
   }

   /** @deprecated */
   // $FF: synthetic method
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalTime
   public static void hours$annotations(long var0) {
   }

   public static final double getHours(long $this$hours) {
      return toDuration($this$hours, TimeUnit.HOURS);
   }

   /** @deprecated */
   // $FF: synthetic method
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalTime
   public static void hours$annotations(double var0) {
   }

   public static final double getHours(double $this$hours) {
      return toDuration($this$hours, TimeUnit.HOURS);
   }

   /** @deprecated */
   // $FF: synthetic method
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalTime
   public static void days$annotations(int var0) {
   }

   public static final double getDays(int $this$days) {
      return toDuration($this$days, TimeUnit.DAYS);
   }

   /** @deprecated */
   // $FF: synthetic method
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalTime
   public static void days$annotations(long var0) {
   }

   public static final double getDays(long $this$days) {
      return toDuration($this$days, TimeUnit.DAYS);
   }

   /** @deprecated */
   // $FF: synthetic method
   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalTime
   public static void days$annotations(double var0) {
   }

   public static final double getDays(double $this$days) {
      return toDuration($this$days, TimeUnit.DAYS);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalTime
   @InlineOnly
   private static final double times_mvk6XK0/* $FF was: times-mvk6XK0*/(int $this$times, double duration) {
      int var3 = 0;
      return Duration.times-impl(duration, $this$times);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalTime
   @InlineOnly
   private static final double times_kIfJnKk/* $FF was: times-kIfJnKk*/(double $this$times, double duration) {
      int var4 = 0;
      return Duration.times-impl(duration, $this$times);
   }

   // $FF: synthetic method
   public static final TimeUnit access$getStorageUnit$p() {
      return getStorageUnit();
   }
}
