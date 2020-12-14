package jdk.nashorn.internal.runtime.options;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

public class LoggingOption extends KeyValueOption {
   private final Map<String, LoggingOption.LoggerInfo> loggers = new HashMap();

   LoggingOption(String value) {
      super(value);
      this.initialize(this.getValues());
   }

   public Map<String, LoggingOption.LoggerInfo> getLoggers() {
      return Collections.unmodifiableMap(this.loggers);
   }

   private void initialize(Map<String, String> logMap) throws IllegalArgumentException {
      try {
         Level level;
         String name;
         boolean isQuiet;
         for(Iterator var2 = logMap.entrySet().iterator(); var2.hasNext(); this.loggers.put(name, new LoggingOption.LoggerInfo(level, isQuiet))) {
            Entry<String, String> entry = (Entry)var2.next();
            name = lastPart((String)entry.getKey());
            String levelString = ((String)entry.getValue()).toUpperCase(Locale.ENGLISH);
            if ("".equals(levelString)) {
               level = Level.INFO;
               isQuiet = false;
            } else if ("QUIET".equals(levelString)) {
               level = Level.INFO;
               isQuiet = true;
            } else {
               level = Level.parse(levelString);
               isQuiet = false;
            }
         }

      } catch (SecurityException | IllegalArgumentException var8) {
         throw var8;
      }
   }

   private static String lastPart(String packageName) {
      String[] parts = packageName.split("\\.");
      return parts.length == 0 ? packageName : parts[parts.length - 1];
   }

   public static class LoggerInfo {
      private final Level level;
      private final boolean isQuiet;

      LoggerInfo(Level level, boolean isQuiet) {
         this.level = level;
         this.isQuiet = isQuiet;
      }

      public Level getLevel() {
         return this.level;
      }

      public boolean isQuiet() {
         return this.isQuiet;
      }
   }
}
