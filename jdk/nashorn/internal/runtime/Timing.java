package jdk.nashorn.internal.runtime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Function;
import java.util.function.Supplier;
import jdk.nashorn.internal.codegen.CompileUnit;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import jdk.nashorn.internal.runtime.logging.Loggable;
import jdk.nashorn.internal.runtime.logging.Logger;

@Logger(
   name = "time"
)
public final class Timing implements Loggable {
   private DebugLogger log;
   private Timing.TimeSupplier timeSupplier;
   private final boolean isEnabled;
   private final long startTime;
   private static final String LOGGER_NAME = ((Logger)Timing.class.getAnnotation(Logger.class)).name();

   public Timing(boolean isEnabled) {
      this.isEnabled = isEnabled;
      this.startTime = System.nanoTime();
   }

   public String getLogInfo() {
      assert this.isEnabled();

      return this.timeSupplier.get();
   }

   public String[] getLogInfoLines() {
      assert this.isEnabled();

      return this.timeSupplier.getStrings();
   }

   boolean isEnabled() {
      return this.isEnabled;
   }

   public void accumulateTime(String module, long durationNano) {
      if (this.isEnabled()) {
         this.ensureInitialized(Context.getContextTrusted());
         this.timeSupplier.accumulateTime(module, durationNano);
      }

   }

   private DebugLogger ensureInitialized(Context context) {
      if (this.isEnabled() && this.log == null) {
         this.log = this.initLogger(context);
         if (this.log.isEnabled()) {
            this.timeSupplier = new Timing.TimeSupplier();
            Runtime.getRuntime().addShutdownHook(new Thread() {
               public void run() {
                  StringBuilder sb = new StringBuilder();
                  String[] var2 = Timing.this.timeSupplier.getStrings();
                  int var3 = var2.length;

                  for(int var4 = 0; var4 < var3; ++var4) {
                     String str = var2[var4];
                     sb.append('[').append(Timing.getLoggerName()).append("] ").append(str).append('\n');
                  }

                  System.err.print(sb);
               }
            });
         }
      }

      return this.log;
   }

   static String getLoggerName() {
      return LOGGER_NAME;
   }

   public DebugLogger initLogger(Context context) {
      return context.getLogger(this.getClass());
   }

   public DebugLogger getLogger() {
      return this.log;
   }

   public static String toMillisPrint(long durationNano) {
      return Long.toString(TimeUnit.NANOSECONDS.toMillis(durationNano));
   }

   final class TimeSupplier implements Supplier<String> {
      private final Map<String, LongAdder> timings = new ConcurrentHashMap();
      private final LinkedBlockingQueue<String> orderedTimingNames = new LinkedBlockingQueue();
      private final Function<String, LongAdder> newTimingCreator = new Function<String, LongAdder>() {
         public LongAdder apply(String s) {
            TimeSupplier.this.orderedTimingNames.add(s);
            return new LongAdder();
         }
      };

      String[] getStrings() {
         List<String> strs = new ArrayList();
         BufferedReader br = new BufferedReader(new StringReader(this.get()));

         String line;
         try {
            while((line = br.readLine()) != null) {
               strs.add(line);
            }
         } catch (IOException var5) {
            throw new RuntimeException(var5);
         }

         return (String[])strs.toArray(new String[strs.size()]);
      }

      public String get() {
         long t = System.nanoTime();
         long knownTime = 0L;
         int maxKeyLength = 0;
         int maxValueLength = 0;

         Entry entry;
         for(Iterator var7 = this.timings.entrySet().iterator(); var7.hasNext(); maxValueLength = Math.max(maxValueLength, Timing.toMillisPrint(((LongAdder)entry.getValue()).longValue()).length())) {
            entry = (Entry)var7.next();
            maxKeyLength = Math.max(maxKeyLength, ((String)entry.getKey()).length());
         }

         ++maxKeyLength;
         StringBuilder sb = new StringBuilder();
         sb.append("Accumulated compilation phase timings:\n\n");

         long duration;
         for(Iterator var16 = this.orderedTimingNames.iterator(); var16.hasNext(); knownTime += duration) {
            String timingName = (String)var16.next();
            int len = sb.length();
            sb.append(timingName);
            len = sb.length() - len;

            while(len++ < maxKeyLength) {
               sb.append(' ');
            }

            duration = ((LongAdder)this.timings.get(timingName)).longValue();
            String strDuration = Timing.toMillisPrint(duration);
            len = strDuration.length();

            for(int i = 0; i < maxValueLength - len; ++i) {
               sb.append(' ');
            }

            sb.append(strDuration).append(" ms\n");
         }

         long total = t - Timing.this.startTime;
         sb.append('\n');
         sb.append("Total runtime: ").append(Timing.toMillisPrint(total)).append(" ms (Non-runtime: ").append(Timing.toMillisPrint(knownTime)).append(" ms [").append((int)((double)knownTime * 100.0D / (double)total)).append("%])");
         sb.append("\n\nEmitted compile units: ").append(CompileUnit.getEmittedUnitCount());
         return sb.toString();
      }

      private void accumulateTime(String module, long duration) {
         ((LongAdder)this.timings.computeIfAbsent(module, this.newTimingCreator)).add(duration);
      }
   }
}
