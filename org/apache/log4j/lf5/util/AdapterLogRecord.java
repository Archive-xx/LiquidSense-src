package org.apache.log4j.lf5.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import org.apache.log4j.lf5.LogLevel;
import org.apache.log4j.lf5.LogRecord;

public class AdapterLogRecord extends LogRecord {
   private static LogLevel severeLevel = null;
   private static StringWriter sw = new StringWriter();
   private static PrintWriter pw;

   public void setCategory(String category) {
      super.setCategory(category);
      super.setLocation(this.getLocationInfo(category));
   }

   public boolean isSevereLevel() {
      return severeLevel == null ? false : severeLevel.equals(this.getLevel());
   }

   public static void setSevereLevel(LogLevel level) {
      severeLevel = level;
   }

   public static LogLevel getSevereLevel() {
      return severeLevel;
   }

   protected String getLocationInfo(String category) {
      String stackTrace = this.stackTraceToString(new Throwable());
      String line = this.parseLine(stackTrace, category);
      return line;
   }

   protected String stackTraceToString(Throwable t) {
      String s = null;
      synchronized(sw) {
         t.printStackTrace(pw);
         s = sw.toString();
         sw.getBuffer().setLength(0);
         return s;
      }
   }

   protected String parseLine(String trace, String category) {
      int index = trace.indexOf(category);
      if (index == -1) {
         return null;
      } else {
         trace = trace.substring(index);
         trace = trace.substring(0, trace.indexOf(")") + 1);
         return trace;
      }
   }

   static {
      pw = new PrintWriter(sw);
   }
}
