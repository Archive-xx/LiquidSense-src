package org.apache.log4j.rewrite;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import org.apache.log4j.Category;
import org.apache.log4j.Logger;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.LoggingEvent;

public class ReflectionRewritePolicy implements RewritePolicy {
   public LoggingEvent rewrite(LoggingEvent source) {
      Object msg = source.getMessage();
      if (!(msg instanceof String)) {
         Object newMsg = msg;
         HashMap rewriteProps = new HashMap(source.getProperties());

         try {
            PropertyDescriptor[] props = Introspector.getBeanInfo(msg.getClass(), Object.class).getPropertyDescriptors();
            if (props.length > 0) {
               for(int i = 0; i < props.length; ++i) {
                  try {
                     Object propertyValue = props[i].getReadMethod().invoke(msg, (Object[])null);
                     if ("message".equalsIgnoreCase(props[i].getName())) {
                        newMsg = propertyValue;
                     } else {
                        rewriteProps.put(props[i].getName(), propertyValue);
                     }
                  } catch (Exception var8) {
                     LogLog.warn("Unable to evaluate property " + props[i].getName(), var8);
                  }
               }

               return new LoggingEvent(source.getFQNOfLoggerClass(), (Category)(source.getLogger() != null ? source.getLogger() : Logger.getLogger(source.getLoggerName())), source.getTimeStamp(), source.getLevel(), newMsg, source.getThreadName(), source.getThrowableInformation(), source.getNDC(), source.getLocationInformation(), rewriteProps);
            }
         } catch (Exception var9) {
            LogLog.warn("Unable to get property descriptors", var9);
         }
      }

      return source;
   }
}
