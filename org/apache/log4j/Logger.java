package org.apache.log4j;

import org.apache.log4j.spi.LoggerFactory;

public class Logger extends Category {
   private static final String FQCN;

   protected Logger(String name) {
      super(name);
   }

   public static Logger getLogger(String name) {
      return LogManager.getLogger(name);
   }

   public static Logger getLogger(Class clazz) {
      return LogManager.getLogger(clazz.getName());
   }

   public static Logger getRootLogger() {
      return LogManager.getRootLogger();
   }

   public static Logger getLogger(String name, LoggerFactory factory) {
      return LogManager.getLogger(name, factory);
   }

   public void trace(Object message) {
      if (!this.repository.isDisabled(5000)) {
         if (Level.TRACE.isGreaterOrEqual(this.getEffectiveLevel())) {
            this.forcedLog(FQCN, Level.TRACE, message, (Throwable)null);
         }

      }
   }

   public void trace(Object message, Throwable t) {
      if (!this.repository.isDisabled(5000)) {
         if (Level.TRACE.isGreaterOrEqual(this.getEffectiveLevel())) {
            this.forcedLog(FQCN, Level.TRACE, message, t);
         }

      }
   }

   public boolean isTraceEnabled() {
      return this.repository.isDisabled(5000) ? false : Level.TRACE.isGreaterOrEqual(this.getEffectiveLevel());
   }

   static {
      FQCN = Logger.class.getName();
   }
}
