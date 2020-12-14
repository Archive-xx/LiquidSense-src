package org.apache.log4j.pattern;

import org.apache.log4j.spi.LoggingEvent;

public final class LoggerPatternConverter extends NamePatternConverter {
   private static final LoggerPatternConverter INSTANCE = new LoggerPatternConverter((String[])null);

   private LoggerPatternConverter(String[] options) {
      super("Logger", "logger", options);
   }

   public static LoggerPatternConverter newInstance(String[] options) {
      return options != null && options.length != 0 ? new LoggerPatternConverter(options) : INSTANCE;
   }

   public void format(LoggingEvent event, StringBuffer toAppendTo) {
      int initialLength = toAppendTo.length();
      toAppendTo.append(event.getLoggerName());
      this.abbreviate(initialLength, toAppendTo);
   }
}
