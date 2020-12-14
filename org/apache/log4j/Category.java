package org.apache.log4j;

import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Vector;
import org.apache.log4j.helpers.AppenderAttachableImpl;
import org.apache.log4j.helpers.NullEnumeration;
import org.apache.log4j.spi.AppenderAttachable;
import org.apache.log4j.spi.HierarchyEventListener;
import org.apache.log4j.spi.LoggerRepository;
import org.apache.log4j.spi.LoggingEvent;

public class Category implements AppenderAttachable {
   protected String name;
   protected volatile Level level;
   protected volatile Category parent;
   private static final String FQCN;
   protected ResourceBundle resourceBundle;
   protected LoggerRepository repository;
   AppenderAttachableImpl aai;
   protected boolean additive = true;

   protected Category(String name) {
      this.name = name;
   }

   public synchronized void addAppender(Appender newAppender) {
      if (this.aai == null) {
         this.aai = new AppenderAttachableImpl();
      }

      this.aai.addAppender(newAppender);
      this.repository.fireAddAppenderEvent(this, newAppender);
   }

   public void assertLog(boolean assertion, String msg) {
      if (!assertion) {
         this.error(msg);
      }

   }

   public void callAppenders(LoggingEvent event) {
      int writes = 0;

      for(Category c = this; c != null; c = c.parent) {
         synchronized(c) {
            if (c.aai != null) {
               writes += c.aai.appendLoopOnAppenders(event);
            }

            if (!c.additive) {
               break;
            }
         }
      }

      if (writes == 0) {
         this.repository.emitNoAppenderWarning(this);
      }

   }

   synchronized void closeNestedAppenders() {
      Enumeration enumeration = this.getAllAppenders();
      if (enumeration != null) {
         while(enumeration.hasMoreElements()) {
            Appender a = (Appender)enumeration.nextElement();
            if (a instanceof AppenderAttachable) {
               a.close();
            }
         }
      }

   }

   public void debug(Object message) {
      if (!this.repository.isDisabled(10000)) {
         if (Level.DEBUG.isGreaterOrEqual(this.getEffectiveLevel())) {
            this.forcedLog(FQCN, Level.DEBUG, message, (Throwable)null);
         }

      }
   }

   public void debug(Object message, Throwable t) {
      if (!this.repository.isDisabled(10000)) {
         if (Level.DEBUG.isGreaterOrEqual(this.getEffectiveLevel())) {
            this.forcedLog(FQCN, Level.DEBUG, message, t);
         }

      }
   }

   public void error(Object message) {
      if (!this.repository.isDisabled(40000)) {
         if (Level.ERROR.isGreaterOrEqual(this.getEffectiveLevel())) {
            this.forcedLog(FQCN, Level.ERROR, message, (Throwable)null);
         }

      }
   }

   public void error(Object message, Throwable t) {
      if (!this.repository.isDisabled(40000)) {
         if (Level.ERROR.isGreaterOrEqual(this.getEffectiveLevel())) {
            this.forcedLog(FQCN, Level.ERROR, message, t);
         }

      }
   }

   /** @deprecated */
   public static Logger exists(String name) {
      return LogManager.exists(name);
   }

   public void fatal(Object message) {
      if (!this.repository.isDisabled(50000)) {
         if (Level.FATAL.isGreaterOrEqual(this.getEffectiveLevel())) {
            this.forcedLog(FQCN, Level.FATAL, message, (Throwable)null);
         }

      }
   }

   public void fatal(Object message, Throwable t) {
      if (!this.repository.isDisabled(50000)) {
         if (Level.FATAL.isGreaterOrEqual(this.getEffectiveLevel())) {
            this.forcedLog(FQCN, Level.FATAL, message, t);
         }

      }
   }

   protected void forcedLog(String fqcn, Priority level, Object message, Throwable t) {
      this.callAppenders(new LoggingEvent(fqcn, this, level, message, t));
   }

   public boolean getAdditivity() {
      return this.additive;
   }

   public synchronized Enumeration getAllAppenders() {
      return (Enumeration)(this.aai == null ? NullEnumeration.getInstance() : this.aai.getAllAppenders());
   }

   public synchronized Appender getAppender(String name) {
      return this.aai != null && name != null ? this.aai.getAppender(name) : null;
   }

   public Level getEffectiveLevel() {
      for(Category c = this; c != null; c = c.parent) {
         if (c.level != null) {
            return c.level;
         }
      }

      return null;
   }

   /** @deprecated */
   public Priority getChainedPriority() {
      for(Category c = this; c != null; c = c.parent) {
         if (c.level != null) {
            return c.level;
         }
      }

      return null;
   }

   /** @deprecated */
   public static Enumeration getCurrentCategories() {
      return LogManager.getCurrentLoggers();
   }

   /** @deprecated */
   public static LoggerRepository getDefaultHierarchy() {
      return LogManager.getLoggerRepository();
   }

   /** @deprecated */
   public LoggerRepository getHierarchy() {
      return this.repository;
   }

   public LoggerRepository getLoggerRepository() {
      return this.repository;
   }

   /** @deprecated */
   public static Category getInstance(String name) {
      return LogManager.getLogger(name);
   }

   /** @deprecated */
   public static Category getInstance(Class clazz) {
      return LogManager.getLogger(clazz);
   }

   public final String getName() {
      return this.name;
   }

   public final Category getParent() {
      return this.parent;
   }

   public final Level getLevel() {
      return this.level;
   }

   /** @deprecated */
   public final Level getPriority() {
      return this.level;
   }

   /** @deprecated */
   public static final Category getRoot() {
      return LogManager.getRootLogger();
   }

   public ResourceBundle getResourceBundle() {
      for(Category c = this; c != null; c = c.parent) {
         if (c.resourceBundle != null) {
            return c.resourceBundle;
         }
      }

      return null;
   }

   protected String getResourceBundleString(String key) {
      ResourceBundle rb = this.getResourceBundle();
      if (rb == null) {
         return null;
      } else {
         try {
            return rb.getString(key);
         } catch (MissingResourceException var4) {
            this.error("No resource is associated with key \"" + key + "\".");
            return null;
         }
      }
   }

   public void info(Object message) {
      if (!this.repository.isDisabled(20000)) {
         if (Level.INFO.isGreaterOrEqual(this.getEffectiveLevel())) {
            this.forcedLog(FQCN, Level.INFO, message, (Throwable)null);
         }

      }
   }

   public void info(Object message, Throwable t) {
      if (!this.repository.isDisabled(20000)) {
         if (Level.INFO.isGreaterOrEqual(this.getEffectiveLevel())) {
            this.forcedLog(FQCN, Level.INFO, message, t);
         }

      }
   }

   public boolean isAttached(Appender appender) {
      return appender != null && this.aai != null ? this.aai.isAttached(appender) : false;
   }

   public boolean isDebugEnabled() {
      return this.repository.isDisabled(10000) ? false : Level.DEBUG.isGreaterOrEqual(this.getEffectiveLevel());
   }

   public boolean isEnabledFor(Priority level) {
      return this.repository.isDisabled(level.level) ? false : level.isGreaterOrEqual(this.getEffectiveLevel());
   }

   public boolean isInfoEnabled() {
      return this.repository.isDisabled(20000) ? false : Level.INFO.isGreaterOrEqual(this.getEffectiveLevel());
   }

   public void l7dlog(Priority priority, String key, Throwable t) {
      if (!this.repository.isDisabled(priority.level)) {
         if (priority.isGreaterOrEqual(this.getEffectiveLevel())) {
            String msg = this.getResourceBundleString(key);
            if (msg == null) {
               msg = key;
            }

            this.forcedLog(FQCN, priority, msg, t);
         }

      }
   }

   public void l7dlog(Priority priority, String key, Object[] params, Throwable t) {
      if (!this.repository.isDisabled(priority.level)) {
         if (priority.isGreaterOrEqual(this.getEffectiveLevel())) {
            String pattern = this.getResourceBundleString(key);
            String msg;
            if (pattern == null) {
               msg = key;
            } else {
               msg = MessageFormat.format(pattern, params);
            }

            this.forcedLog(FQCN, priority, msg, t);
         }

      }
   }

   public void log(Priority priority, Object message, Throwable t) {
      if (!this.repository.isDisabled(priority.level)) {
         if (priority.isGreaterOrEqual(this.getEffectiveLevel())) {
            this.forcedLog(FQCN, priority, message, t);
         }

      }
   }

   public void log(Priority priority, Object message) {
      if (!this.repository.isDisabled(priority.level)) {
         if (priority.isGreaterOrEqual(this.getEffectiveLevel())) {
            this.forcedLog(FQCN, priority, message, (Throwable)null);
         }

      }
   }

   public void log(String callerFQCN, Priority level, Object message, Throwable t) {
      if (!this.repository.isDisabled(level.level)) {
         if (level.isGreaterOrEqual(this.getEffectiveLevel())) {
            this.forcedLog(callerFQCN, level, message, t);
         }

      }
   }

   private void fireRemoveAppenderEvent(Appender appender) {
      if (appender != null) {
         if (this.repository instanceof Hierarchy) {
            ((Hierarchy)this.repository).fireRemoveAppenderEvent(this, appender);
         } else if (this.repository instanceof HierarchyEventListener) {
            ((HierarchyEventListener)this.repository).removeAppenderEvent(this, appender);
         }
      }

   }

   public synchronized void removeAllAppenders() {
      if (this.aai != null) {
         Vector appenders = new Vector();
         Enumeration iter = this.aai.getAllAppenders();

         while(iter != null && iter.hasMoreElements()) {
            appenders.add(iter.nextElement());
         }

         this.aai.removeAllAppenders();
         iter = appenders.elements();

         while(iter.hasMoreElements()) {
            this.fireRemoveAppenderEvent((Appender)iter.nextElement());
         }

         this.aai = null;
      }

   }

   public synchronized void removeAppender(Appender appender) {
      if (appender != null && this.aai != null) {
         boolean wasAttached = this.aai.isAttached(appender);
         this.aai.removeAppender(appender);
         if (wasAttached) {
            this.fireRemoveAppenderEvent(appender);
         }

      }
   }

   public synchronized void removeAppender(String name) {
      if (name != null && this.aai != null) {
         Appender appender = this.aai.getAppender(name);
         this.aai.removeAppender(name);
         if (appender != null) {
            this.fireRemoveAppenderEvent(appender);
         }

      }
   }

   public void setAdditivity(boolean additive) {
      this.additive = additive;
   }

   final void setHierarchy(LoggerRepository repository) {
      this.repository = repository;
   }

   public void setLevel(Level level) {
      this.level = level;
   }

   /** @deprecated */
   public void setPriority(Priority priority) {
      this.level = (Level)priority;
   }

   public void setResourceBundle(ResourceBundle bundle) {
      this.resourceBundle = bundle;
   }

   /** @deprecated */
   public static void shutdown() {
      LogManager.shutdown();
   }

   public void warn(Object message) {
      if (!this.repository.isDisabled(30000)) {
         if (Level.WARN.isGreaterOrEqual(this.getEffectiveLevel())) {
            this.forcedLog(FQCN, Level.WARN, message, (Throwable)null);
         }

      }
   }

   public void warn(Object message, Throwable t) {
      if (!this.repository.isDisabled(30000)) {
         if (Level.WARN.isGreaterOrEqual(this.getEffectiveLevel())) {
            this.forcedLog(FQCN, Level.WARN, message, t);
         }

      }
   }

   static {
      FQCN = Category.class.getName();
   }
}
