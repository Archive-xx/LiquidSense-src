package org.apache.log4j.spi;

import java.util.Enumeration;
import org.apache.log4j.Appender;
import org.apache.log4j.Category;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public interface LoggerRepository {
   void addHierarchyEventListener(HierarchyEventListener var1);

   boolean isDisabled(int var1);

   void setThreshold(Level var1);

   void setThreshold(String var1);

   void emitNoAppenderWarning(Category var1);

   Level getThreshold();

   Logger getLogger(String var1);

   Logger getLogger(String var1, LoggerFactory var2);

   Logger getRootLogger();

   Logger exists(String var1);

   void shutdown();

   Enumeration getCurrentLoggers();

   Enumeration getCurrentCategories();

   void fireAddAppenderEvent(Category var1, Appender var2);

   void resetConfiguration();
}
