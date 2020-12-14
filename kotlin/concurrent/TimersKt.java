package kotlin.concurrent;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.Unit;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 2,
   d1 = {"\u00004\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u001aJ\u0010\u0000\u001a\u00020\u00012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0019\b\u0004\u0010\n\u001a\u0013\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000b¢\u0006\u0002\b\u000eH\u0087\b\u001aL\u0010\u0000\u001a\u00020\u00012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u000f\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\t2\u0019\b\u0004\u0010\n\u001a\u0013\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000b¢\u0006\u0002\b\u000eH\u0087\b\u001a\u001a\u0010\u0010\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0001\u001aJ\u0010\u0010\u001a\u00020\u00012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0019\b\u0004\u0010\n\u001a\u0013\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000b¢\u0006\u0002\b\u000eH\u0087\b\u001aL\u0010\u0010\u001a\u00020\u00012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u000f\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\t2\u0019\b\u0004\u0010\n\u001a\u0013\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000b¢\u0006\u0002\b\u000eH\u0087\b\u001a$\u0010\u0011\u001a\u00020\f2\u0019\b\u0004\u0010\n\u001a\u0013\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000b¢\u0006\u0002\b\u000eH\u0087\b\u001a0\u0010\u0012\u001a\u00020\f*\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00072\u0019\b\u0004\u0010\n\u001a\u0013\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000b¢\u0006\u0002\b\u000eH\u0087\b\u001a8\u0010\u0012\u001a\u00020\f*\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0019\b\u0004\u0010\n\u001a\u0013\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000b¢\u0006\u0002\b\u000eH\u0087\b\u001a0\u0010\u0012\u001a\u00020\f*\u00020\u00012\u0006\u0010\u0014\u001a\u00020\t2\u0019\b\u0004\u0010\n\u001a\u0013\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000b¢\u0006\u0002\b\u000eH\u0087\b\u001a8\u0010\u0012\u001a\u00020\f*\u00020\u00012\u0006\u0010\u0014\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\t2\u0019\b\u0004\u0010\n\u001a\u0013\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000b¢\u0006\u0002\b\u000eH\u0087\b\u001a8\u0010\u0015\u001a\u00020\f*\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0019\b\u0004\u0010\n\u001a\u0013\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000b¢\u0006\u0002\b\u000eH\u0087\b\u001a8\u0010\u0015\u001a\u00020\f*\u00020\u00012\u0006\u0010\u0014\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\t2\u0019\b\u0004\u0010\n\u001a\u0013\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000b¢\u0006\u0002\b\u000eH\u0087\b¨\u0006\u0016"},
   d2 = {"fixedRateTimer", "Ljava/util/Timer;", "name", "", "daemon", "", "startAt", "Ljava/util/Date;", "period", "", "action", "Lkotlin/Function1;", "Ljava/util/TimerTask;", "", "Lkotlin/ExtensionFunctionType;", "initialDelay", "timer", "timerTask", "schedule", "time", "delay", "scheduleAtFixedRate", "kotlin-stdlib"}
)
@JvmName(
   name = "TimersKt"
)
public final class TimersKt {
   @InlineOnly
   private static final TimerTask schedule(@NotNull Timer $this$schedule, long delay, Function1<? super TimerTask, Unit> action) {
      int $i$f$schedule = 0;
      boolean var6 = false;
      TimerTask task = (TimerTask)(new TimerTask(action) {
         // $FF: synthetic field
         final Function1 $action;

         public void run() {
            this.$action.invoke(this);
         }

         public {
            this.$action = $captured_local_variable$0;
         }
      });
      $this$schedule.schedule(task, delay);
      return task;
   }

   @InlineOnly
   private static final TimerTask schedule(@NotNull Timer $this$schedule, Date time, Function1<? super TimerTask, Unit> action) {
      int $i$f$schedule = 0;
      boolean var5 = false;
      TimerTask task = (TimerTask)(new TimerTask(action) {
         // $FF: synthetic field
         final Function1 $action;

         public void run() {
            this.$action.invoke(this);
         }

         public {
            this.$action = $captured_local_variable$0;
         }
      });
      $this$schedule.schedule(task, time);
      return task;
   }

   @InlineOnly
   private static final TimerTask schedule(@NotNull Timer $this$schedule, long delay, long period, Function1<? super TimerTask, Unit> action) {
      int $i$f$schedule = 0;
      boolean var8 = false;
      TimerTask task = (TimerTask)(new TimerTask(action) {
         // $FF: synthetic field
         final Function1 $action;

         public void run() {
            this.$action.invoke(this);
         }

         public {
            this.$action = $captured_local_variable$0;
         }
      });
      $this$schedule.schedule(task, delay, period);
      return task;
   }

   @InlineOnly
   private static final TimerTask schedule(@NotNull Timer $this$schedule, Date time, long period, Function1<? super TimerTask, Unit> action) {
      int $i$f$schedule = 0;
      boolean var7 = false;
      TimerTask task = (TimerTask)(new TimerTask(action) {
         // $FF: synthetic field
         final Function1 $action;

         public void run() {
            this.$action.invoke(this);
         }

         public {
            this.$action = $captured_local_variable$0;
         }
      });
      $this$schedule.schedule(task, time, period);
      return task;
   }

   @InlineOnly
   private static final TimerTask scheduleAtFixedRate(@NotNull Timer $this$scheduleAtFixedRate, long delay, long period, Function1<? super TimerTask, Unit> action) {
      int $i$f$scheduleAtFixedRate = 0;
      boolean var8 = false;
      TimerTask task = (TimerTask)(new TimerTask(action) {
         // $FF: synthetic field
         final Function1 $action;

         public void run() {
            this.$action.invoke(this);
         }

         public {
            this.$action = $captured_local_variable$0;
         }
      });
      $this$scheduleAtFixedRate.scheduleAtFixedRate(task, delay, period);
      return task;
   }

   @InlineOnly
   private static final TimerTask scheduleAtFixedRate(@NotNull Timer $this$scheduleAtFixedRate, Date time, long period, Function1<? super TimerTask, Unit> action) {
      int $i$f$scheduleAtFixedRate = 0;
      boolean var7 = false;
      TimerTask task = (TimerTask)(new TimerTask(action) {
         // $FF: synthetic field
         final Function1 $action;

         public void run() {
            this.$action.invoke(this);
         }

         public {
            this.$action = $captured_local_variable$0;
         }
      });
      $this$scheduleAtFixedRate.scheduleAtFixedRate(task, time, period);
      return task;
   }

   @PublishedApi
   @NotNull
   public static final Timer timer(@Nullable String name, boolean daemon) {
      return name == null ? new Timer(daemon) : new Timer(name, daemon);
   }

   @InlineOnly
   private static final Timer timer(String name, boolean daemon, long initialDelay, long period, Function1<? super TimerTask, Unit> action) {
      int $i$f$timer = 0;
      Timer timer = timer(name, daemon);
      boolean var10 = false;
      boolean var11 = false;
      TimerTask var12 = (TimerTask)(new TimerTask(action) {
         // $FF: synthetic field
         final Function1 $action;

         public void run() {
            this.$action.invoke(this);
         }

         public {
            this.$action = $captured_local_variable$0;
         }
      });
      timer.schedule(var12, initialDelay, period);
      return timer;
   }

   // $FF: synthetic method
   static Timer timer$default(String name, boolean daemon, long initialDelay, long period, Function1 action, int var7, Object var8) {
      if ((var7 & 1) != 0) {
         name = (String)null;
      }

      if ((var7 & 2) != 0) {
         daemon = false;
      }

      if ((var7 & 4) != 0) {
         initialDelay = 0L;
      }

      int $i$f$timer = false;
      Timer timer = timer(name, daemon);
      boolean var10 = false;
      boolean var11 = false;
      TimerTask var12 = (TimerTask)(new TimerTask(action) {
         // $FF: synthetic field
         final Function1 $action;

         public void run() {
            this.$action.invoke(this);
         }

         public {
            this.$action = $captured_local_variable$0;
         }
      });
      timer.schedule(var12, initialDelay, period);
      return timer;
   }

   @InlineOnly
   private static final Timer timer(String name, boolean daemon, Date startAt, long period, Function1<? super TimerTask, Unit> action) {
      int $i$f$timer = 0;
      Timer timer = timer(name, daemon);
      boolean var9 = false;
      boolean var10 = false;
      TimerTask var11 = (TimerTask)(new TimerTask(action) {
         // $FF: synthetic field
         final Function1 $action;

         public void run() {
            this.$action.invoke(this);
         }

         public {
            this.$action = $captured_local_variable$0;
         }
      });
      timer.schedule(var11, startAt, period);
      return timer;
   }

   // $FF: synthetic method
   static Timer timer$default(String name, boolean daemon, Date startAt, long period, Function1 action, int var6, Object var7) {
      if ((var6 & 1) != 0) {
         name = (String)null;
      }

      if ((var6 & 2) != 0) {
         daemon = false;
      }

      int $i$f$timer = false;
      Timer timer = timer(name, daemon);
      boolean var9 = false;
      boolean var10 = false;
      TimerTask var11 = (TimerTask)(new TimerTask(action) {
         // $FF: synthetic field
         final Function1 $action;

         public void run() {
            this.$action.invoke(this);
         }

         public {
            this.$action = $captured_local_variable$0;
         }
      });
      timer.schedule(var11, startAt, period);
      return timer;
   }

   @InlineOnly
   private static final Timer fixedRateTimer(String name, boolean daemon, long initialDelay, long period, Function1<? super TimerTask, Unit> action) {
      int $i$f$fixedRateTimer = 0;
      Timer timer = timer(name, daemon);
      boolean var10 = false;
      boolean var11 = false;
      TimerTask var12 = (TimerTask)(new TimerTask(action) {
         // $FF: synthetic field
         final Function1 $action;

         public void run() {
            this.$action.invoke(this);
         }

         public {
            this.$action = $captured_local_variable$0;
         }
      });
      timer.scheduleAtFixedRate(var12, initialDelay, period);
      return timer;
   }

   // $FF: synthetic method
   static Timer fixedRateTimer$default(String name, boolean daemon, long initialDelay, long period, Function1 action, int var7, Object var8) {
      if ((var7 & 1) != 0) {
         name = (String)null;
      }

      if ((var7 & 2) != 0) {
         daemon = false;
      }

      if ((var7 & 4) != 0) {
         initialDelay = 0L;
      }

      int $i$f$fixedRateTimer = false;
      Timer timer = timer(name, daemon);
      boolean var10 = false;
      boolean var11 = false;
      TimerTask var12 = (TimerTask)(new TimerTask(action) {
         // $FF: synthetic field
         final Function1 $action;

         public void run() {
            this.$action.invoke(this);
         }

         public {
            this.$action = $captured_local_variable$0;
         }
      });
      timer.scheduleAtFixedRate(var12, initialDelay, period);
      return timer;
   }

   @InlineOnly
   private static final Timer fixedRateTimer(String name, boolean daemon, Date startAt, long period, Function1<? super TimerTask, Unit> action) {
      int $i$f$fixedRateTimer = 0;
      Timer timer = timer(name, daemon);
      boolean var9 = false;
      boolean var10 = false;
      TimerTask var11 = (TimerTask)(new TimerTask(action) {
         // $FF: synthetic field
         final Function1 $action;

         public void run() {
            this.$action.invoke(this);
         }

         public {
            this.$action = $captured_local_variable$0;
         }
      });
      timer.scheduleAtFixedRate(var11, startAt, period);
      return timer;
   }

   // $FF: synthetic method
   static Timer fixedRateTimer$default(String name, boolean daemon, Date startAt, long period, Function1 action, int var6, Object var7) {
      if ((var6 & 1) != 0) {
         name = (String)null;
      }

      if ((var6 & 2) != 0) {
         daemon = false;
      }

      int $i$f$fixedRateTimer = false;
      Timer timer = timer(name, daemon);
      boolean var9 = false;
      boolean var10 = false;
      TimerTask var11 = (TimerTask)(new TimerTask(action) {
         // $FF: synthetic field
         final Function1 $action;

         public void run() {
            this.$action.invoke(this);
         }

         public {
            this.$action = $captured_local_variable$0;
         }
      });
      timer.scheduleAtFixedRate(var11, startAt, period);
      return timer;
   }

   @InlineOnly
   private static final TimerTask timerTask(Function1<? super TimerTask, Unit> action) {
      int $i$f$timerTask = 0;
      return (TimerTask)(new TimerTask(action) {
         // $FF: synthetic field
         final Function1 $action;

         public void run() {
            this.$action.invoke(this);
         }

         public {
            this.$action = $captured_local_variable$0;
         }
      });
   }
}
