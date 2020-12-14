package org.apache.log4j.helpers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.LoggingEvent;

public class PatternParser {
   private static final char ESCAPE_CHAR = '%';
   private static final int LITERAL_STATE = 0;
   private static final int CONVERTER_STATE = 1;
   private static final int DOT_STATE = 3;
   private static final int MIN_STATE = 4;
   private static final int MAX_STATE = 5;
   static final int FULL_LOCATION_CONVERTER = 1000;
   static final int METHOD_LOCATION_CONVERTER = 1001;
   static final int CLASS_LOCATION_CONVERTER = 1002;
   static final int LINE_LOCATION_CONVERTER = 1003;
   static final int FILE_LOCATION_CONVERTER = 1004;
   static final int RELATIVE_TIME_CONVERTER = 2000;
   static final int THREAD_CONVERTER = 2001;
   static final int LEVEL_CONVERTER = 2002;
   static final int NDC_CONVERTER = 2003;
   static final int MESSAGE_CONVERTER = 2004;
   int state;
   protected StringBuffer currentLiteral = new StringBuffer(32);
   protected int patternLength;
   protected int i;
   PatternConverter head;
   PatternConverter tail;
   protected FormattingInfo formattingInfo = new FormattingInfo();
   protected String pattern;

   public PatternParser(String pattern) {
      this.pattern = pattern;
      this.patternLength = pattern.length();
      this.state = 0;
   }

   private void addToList(PatternConverter pc) {
      if (this.head == null) {
         this.head = this.tail = pc;
      } else {
         this.tail.next = pc;
         this.tail = pc;
      }

   }

   protected String extractOption() {
      if (this.i < this.patternLength && this.pattern.charAt(this.i) == '{') {
         int end = this.pattern.indexOf(125, this.i);
         if (end > this.i) {
            String r = this.pattern.substring(this.i + 1, end);
            this.i = end + 1;
            return r;
         }
      }

      return null;
   }

   protected int extractPrecisionOption() {
      String opt = this.extractOption();
      int r = 0;
      if (opt != null) {
         try {
            r = Integer.parseInt(opt);
            if (r <= 0) {
               LogLog.error("Precision option (" + opt + ") isn't a positive integer.");
               r = 0;
            }
         } catch (NumberFormatException var4) {
            LogLog.error("Category option \"" + opt + "\" not a decimal integer.", var4);
         }
      }

      return r;
   }

   public PatternConverter parse() {
      this.i = 0;

      while(true) {
         while(true) {
            while(this.i < this.patternLength) {
               char c = this.pattern.charAt(this.i++);
               switch(this.state) {
               case 0:
                  if (this.i == this.patternLength) {
                     this.currentLiteral.append(c);
                  } else if (c == '%') {
                     switch(this.pattern.charAt(this.i)) {
                     case '%':
                        this.currentLiteral.append(c);
                        ++this.i;
                        break;
                     case 'n':
                        this.currentLiteral.append(Layout.LINE_SEP);
                        ++this.i;
                        break;
                     default:
                        if (this.currentLiteral.length() != 0) {
                           this.addToList(new PatternParser.LiteralPatternConverter(this.currentLiteral.toString()));
                        }

                        this.currentLiteral.setLength(0);
                        this.currentLiteral.append(c);
                        this.state = 1;
                        this.formattingInfo.reset();
                     }
                  } else {
                     this.currentLiteral.append(c);
                  }
                  break;
               case 1:
                  this.currentLiteral.append(c);
                  switch(c) {
                  case '-':
                     this.formattingInfo.leftAlign = true;
                     break;
                  case '.':
                     this.state = 3;
                     break;
                  default:
                     if (c >= '0' && c <= '9') {
                        this.formattingInfo.min = c - 48;
                        this.state = 4;
                     } else {
                        this.finalizeConverter(c);
                     }
                  }
               case 2:
               default:
                  break;
               case 3:
                  this.currentLiteral.append(c);
                  if (c >= '0' && c <= '9') {
                     this.formattingInfo.max = c - 48;
                     this.state = 5;
                     break;
                  }

                  LogLog.error("Error occured in position " + this.i + ".\n Was expecting digit, instead got char \"" + c + "\".");
                  this.state = 0;
                  break;
               case 4:
                  this.currentLiteral.append(c);
                  if (c >= '0' && c <= '9') {
                     this.formattingInfo.min = this.formattingInfo.min * 10 + (c - 48);
                  } else if (c == '.') {
                     this.state = 3;
                  } else {
                     this.finalizeConverter(c);
                  }
                  break;
               case 5:
                  this.currentLiteral.append(c);
                  if (c >= '0' && c <= '9') {
                     this.formattingInfo.max = this.formattingInfo.max * 10 + (c - 48);
                  } else {
                     this.finalizeConverter(c);
                     this.state = 0;
                  }
               }
            }

            if (this.currentLiteral.length() != 0) {
               this.addToList(new PatternParser.LiteralPatternConverter(this.currentLiteral.toString()));
            }

            return this.head;
         }
      }
   }

   protected void finalizeConverter(char c) {
      PatternConverter pc = null;
      switch(c) {
      case 'C':
         pc = new PatternParser.ClassNamePatternConverter(this.formattingInfo, this.extractPrecisionOption());
         this.currentLiteral.setLength(0);
         break;
      case 'D':
      case 'E':
      case 'G':
      case 'H':
      case 'I':
      case 'J':
      case 'K':
      case 'N':
      case 'O':
      case 'P':
      case 'Q':
      case 'R':
      case 'S':
      case 'T':
      case 'U':
      case 'V':
      case 'W':
      case 'Y':
      case 'Z':
      case '[':
      case '\\':
      case ']':
      case '^':
      case '_':
      case '`':
      case 'a':
      case 'b':
      case 'e':
      case 'f':
      case 'g':
      case 'h':
      case 'i':
      case 'j':
      case 'k':
      case 'n':
      case 'o':
      case 'q':
      case 's':
      case 'u':
      case 'v':
      case 'w':
      default:
         LogLog.error("Unexpected char [" + c + "] at position " + this.i + " in conversion patterrn.");
         pc = new PatternParser.LiteralPatternConverter(this.currentLiteral.toString());
         this.currentLiteral.setLength(0);
         break;
      case 'F':
         pc = new PatternParser.LocationPatternConverter(this.formattingInfo, 1004);
         this.currentLiteral.setLength(0);
         break;
      case 'L':
         pc = new PatternParser.LocationPatternConverter(this.formattingInfo, 1003);
         this.currentLiteral.setLength(0);
         break;
      case 'M':
         pc = new PatternParser.LocationPatternConverter(this.formattingInfo, 1001);
         this.currentLiteral.setLength(0);
         break;
      case 'X':
         String xOpt = this.extractOption();
         pc = new PatternParser.MDCPatternConverter(this.formattingInfo, xOpt);
         this.currentLiteral.setLength(0);
         break;
      case 'c':
         pc = new PatternParser.CategoryPatternConverter(this.formattingInfo, this.extractPrecisionOption());
         this.currentLiteral.setLength(0);
         break;
      case 'd':
         String dateFormatStr = "ISO8601";
         String dOpt = this.extractOption();
         if (dOpt != null) {
            dateFormatStr = dOpt;
         }

         Object df;
         if (dateFormatStr.equalsIgnoreCase("ISO8601")) {
            df = new ISO8601DateFormat();
         } else if (dateFormatStr.equalsIgnoreCase("ABSOLUTE")) {
            df = new AbsoluteTimeDateFormat();
         } else if (dateFormatStr.equalsIgnoreCase("DATE")) {
            df = new DateTimeDateFormat();
         } else {
            try {
               df = new SimpleDateFormat(dateFormatStr);
            } catch (IllegalArgumentException var7) {
               LogLog.error("Could not instantiate SimpleDateFormat with " + dateFormatStr, var7);
               df = (DateFormat)OptionConverter.instantiateByClassName("org.apache.log4j.helpers.ISO8601DateFormat", DateFormat.class, (Object)null);
            }
         }

         pc = new PatternParser.DatePatternConverter(this.formattingInfo, (DateFormat)df);
         this.currentLiteral.setLength(0);
         break;
      case 'l':
         pc = new PatternParser.LocationPatternConverter(this.formattingInfo, 1000);
         this.currentLiteral.setLength(0);
         break;
      case 'm':
         pc = new PatternParser.BasicPatternConverter(this.formattingInfo, 2004);
         this.currentLiteral.setLength(0);
         break;
      case 'p':
         pc = new PatternParser.BasicPatternConverter(this.formattingInfo, 2002);
         this.currentLiteral.setLength(0);
         break;
      case 'r':
         pc = new PatternParser.BasicPatternConverter(this.formattingInfo, 2000);
         this.currentLiteral.setLength(0);
         break;
      case 't':
         pc = new PatternParser.BasicPatternConverter(this.formattingInfo, 2001);
         this.currentLiteral.setLength(0);
         break;
      case 'x':
         pc = new PatternParser.BasicPatternConverter(this.formattingInfo, 2003);
         this.currentLiteral.setLength(0);
      }

      this.addConverter((PatternConverter)pc);
   }

   protected void addConverter(PatternConverter pc) {
      this.currentLiteral.setLength(0);
      this.addToList(pc);
      this.state = 0;
      this.formattingInfo.reset();
   }

   private class CategoryPatternConverter extends PatternParser.NamedPatternConverter {
      CategoryPatternConverter(FormattingInfo formattingInfo, int precision) {
         super(formattingInfo, precision);
      }

      String getFullyQualifiedName(LoggingEvent event) {
         return event.getLoggerName();
      }
   }

   private class ClassNamePatternConverter extends PatternParser.NamedPatternConverter {
      ClassNamePatternConverter(FormattingInfo formattingInfo, int precision) {
         super(formattingInfo, precision);
      }

      String getFullyQualifiedName(LoggingEvent event) {
         return event.getLocationInformation().getClassName();
      }
   }

   private abstract static class NamedPatternConverter extends PatternConverter {
      int precision;

      NamedPatternConverter(FormattingInfo formattingInfo, int precision) {
         super(formattingInfo);
         this.precision = precision;
      }

      abstract String getFullyQualifiedName(LoggingEvent var1);

      public String convert(LoggingEvent event) {
         String n = this.getFullyQualifiedName(event);
         if (this.precision <= 0) {
            return n;
         } else {
            int len = n.length();
            int end = len - 1;

            for(int i = this.precision; i > 0; --i) {
               end = n.lastIndexOf(46, end - 1);
               if (end == -1) {
                  return n;
               }
            }

            return n.substring(end + 1, len);
         }
      }
   }

   private class LocationPatternConverter extends PatternConverter {
      int type;

      LocationPatternConverter(FormattingInfo formattingInfo, int type) {
         super(formattingInfo);
         this.type = type;
      }

      public String convert(LoggingEvent event) {
         LocationInfo locationInfo = event.getLocationInformation();
         switch(this.type) {
         case 1000:
            return locationInfo.fullInfo;
         case 1001:
            return locationInfo.getMethodName();
         case 1002:
         default:
            return null;
         case 1003:
            return locationInfo.getLineNumber();
         case 1004:
            return locationInfo.getFileName();
         }
      }
   }

   private static class MDCPatternConverter extends PatternConverter {
      private String key;

      MDCPatternConverter(FormattingInfo formattingInfo, String key) {
         super(formattingInfo);
         this.key = key;
      }

      public String convert(LoggingEvent event) {
         if (this.key != null) {
            Object val = event.getMDC(this.key);
            return val == null ? null : val.toString();
         } else {
            StringBuffer buf = new StringBuffer("{");
            Map properties = event.getProperties();
            if (properties.size() > 0) {
               Object[] keys = properties.keySet().toArray();
               Arrays.sort(keys);

               for(int i = 0; i < keys.length; ++i) {
                  buf.append('{');
                  buf.append(keys[i]);
                  buf.append(',');
                  buf.append(properties.get(keys[i]));
                  buf.append('}');
               }
            }

            buf.append('}');
            return buf.toString();
         }
      }
   }

   private static class DatePatternConverter extends PatternConverter {
      private DateFormat df;
      private Date date = new Date();

      DatePatternConverter(FormattingInfo formattingInfo, DateFormat df) {
         super(formattingInfo);
         this.df = df;
      }

      public String convert(LoggingEvent event) {
         this.date.setTime(event.timeStamp);
         String converted = null;

         try {
            converted = this.df.format(this.date);
         } catch (Exception var4) {
            LogLog.error("Error occured while converting date.", var4);
         }

         return converted;
      }
   }

   private static class LiteralPatternConverter extends PatternConverter {
      private String literal;

      LiteralPatternConverter(String value) {
         this.literal = value;
      }

      public final void format(StringBuffer sbuf, LoggingEvent event) {
         sbuf.append(this.literal);
      }

      public String convert(LoggingEvent event) {
         return this.literal;
      }
   }

   private static class BasicPatternConverter extends PatternConverter {
      int type;

      BasicPatternConverter(FormattingInfo formattingInfo, int type) {
         super(formattingInfo);
         this.type = type;
      }

      public String convert(LoggingEvent event) {
         switch(this.type) {
         case 2000:
            return Long.toString(event.timeStamp - LoggingEvent.getStartTime());
         case 2001:
            return event.getThreadName();
         case 2002:
            return event.getLevel().toString();
         case 2003:
            return event.getNDC();
         case 2004:
            return event.getRenderedMessage();
         default:
            return null;
         }
      }
   }
}
