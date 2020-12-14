package org.apache.log4j.pattern;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.log4j.spi.LoggingEvent;

public final class BridgePatternConverter extends org.apache.log4j.helpers.PatternConverter {
   private LoggingEventPatternConverter[] patternConverters;
   private FormattingInfo[] patternFields;
   private boolean handlesExceptions;

   public BridgePatternConverter(String pattern) {
      this.next = null;
      this.handlesExceptions = false;
      List converters = new ArrayList();
      List fields = new ArrayList();
      Map converterRegistry = null;
      PatternParser.parse(pattern, converters, fields, (Map)converterRegistry, PatternParser.getPatternLayoutRules());
      this.patternConverters = new LoggingEventPatternConverter[converters.size()];
      this.patternFields = new FormattingInfo[converters.size()];
      int i = 0;
      Iterator converterIter = converters.iterator();

      for(Iterator fieldIter = fields.iterator(); converterIter.hasNext(); ++i) {
         Object converter = converterIter.next();
         if (converter instanceof LoggingEventPatternConverter) {
            this.patternConverters[i] = (LoggingEventPatternConverter)converter;
            this.handlesExceptions |= this.patternConverters[i].handlesThrowable();
         } else {
            this.patternConverters[i] = new LiteralPatternConverter("");
         }

         if (fieldIter.hasNext()) {
            this.patternFields[i] = (FormattingInfo)fieldIter.next();
         } else {
            this.patternFields[i] = FormattingInfo.getDefault();
         }
      }

   }

   protected String convert(LoggingEvent event) {
      StringBuffer sbuf = new StringBuffer();
      this.format(sbuf, event);
      return sbuf.toString();
   }

   public void format(StringBuffer sbuf, LoggingEvent e) {
      for(int i = 0; i < this.patternConverters.length; ++i) {
         int startField = sbuf.length();
         this.patternConverters[i].format(e, sbuf);
         this.patternFields[i].format(startField, sbuf);
      }

   }

   public boolean ignoresThrowable() {
      return !this.handlesExceptions;
   }
}
