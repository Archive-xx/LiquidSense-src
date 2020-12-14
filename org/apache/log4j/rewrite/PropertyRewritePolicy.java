package org.apache.log4j.rewrite;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Map.Entry;
import org.apache.log4j.Category;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;

public class PropertyRewritePolicy implements RewritePolicy {
   private Map properties;

   public PropertyRewritePolicy() {
      this.properties = Collections.EMPTY_MAP;
   }

   public void setProperties(String props) {
      Map hashTable = new HashMap();
      StringTokenizer pairs = new StringTokenizer(props, ",");

      while(pairs.hasMoreTokens()) {
         StringTokenizer entry = new StringTokenizer(pairs.nextToken(), "=");
         hashTable.put(entry.nextElement().toString().trim(), entry.nextElement().toString().trim());
      }

      synchronized(this) {
         this.properties = hashTable;
      }
   }

   public LoggingEvent rewrite(LoggingEvent source) {
      if (!this.properties.isEmpty()) {
         Map rewriteProps = new HashMap(source.getProperties());
         Iterator iter = this.properties.entrySet().iterator();

         while(iter.hasNext()) {
            Entry entry = (Entry)iter.next();
            if (!rewriteProps.containsKey(entry.getKey())) {
               rewriteProps.put(entry.getKey(), entry.getValue());
            }
         }

         return new LoggingEvent(source.getFQNOfLoggerClass(), (Category)(source.getLogger() != null ? source.getLogger() : Logger.getLogger(source.getLoggerName())), source.getTimeStamp(), source.getLevel(), source.getMessage(), source.getThreadName(), source.getThrowableInformation(), source.getNDC(), source.getLocationInformation(), rewriteProps);
      } else {
         return source;
      }
   }
}
