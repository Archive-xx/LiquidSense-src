package org.apache.log4j;

import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.OptionHandler;

public abstract class Layout implements OptionHandler {
   public static final String LINE_SEP = System.getProperty("line.separator");
   public static final int LINE_SEP_LEN;

   public abstract String format(LoggingEvent var1);

   public String getContentType() {
      return "text/plain";
   }

   public String getHeader() {
      return null;
   }

   public String getFooter() {
      return null;
   }

   public abstract boolean ignoresThrowable();

   static {
      LINE_SEP_LEN = LINE_SEP.length();
   }
}
