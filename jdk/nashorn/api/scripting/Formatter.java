package jdk.nashorn.api.scripting;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class Formatter {
   private static final String formatSpecifier = "%(\\d+\\$)?([-#+ 0,(\\<]*)?(\\d+)?(\\.\\d+)?([tT])?([a-zA-Z%])";
   private static final Pattern FS_PATTERN = Pattern.compile("%(\\d+\\$)?([-#+ 0,(\\<]*)?(\\d+)?(\\.\\d+)?([tT])?([a-zA-Z%])");

   private Formatter() {
   }

   static String format(String format, Object[] args) {
      Matcher m = FS_PATTERN.matcher(format);
      int var3 = 1;

      while(m.find()) {
         int index = index(m.group(1));
         boolean previous = isPreviousArgument(m.group(2));
         char conversion = m.group(6).charAt(0);
         if (index >= 0 && !previous && conversion != 'n' && conversion != '%') {
            if (index == 0) {
               index = var3++;
            }

            if (index <= args.length) {
               Object arg = args[index - 1];
               if (m.group(5) != null) {
                  if (arg instanceof Double) {
                     args[index - 1] = ((Double)arg).longValue();
                  }
               } else {
                  switch(conversion) {
                  case 'A':
                  case 'E':
                  case 'G':
                  case 'a':
                  case 'e':
                  case 'f':
                  case 'g':
                     if (arg instanceof Integer) {
                        args[index - 1] = ((Integer)arg).doubleValue();
                     }
                     break;
                  case 'X':
                  case 'd':
                  case 'o':
                  case 'x':
                     if (arg instanceof Double) {
                        args[index - 1] = ((Double)arg).longValue();
                     } else if (arg instanceof String && ((String)arg).length() > 0) {
                        args[index - 1] = Integer.valueOf(((String)arg).charAt(0));
                     }
                     break;
                  case 'c':
                     if (arg instanceof Double) {
                        args[index - 1] = ((Double)arg).intValue();
                     } else if (arg instanceof String && ((String)arg).length() > 0) {
                        args[index - 1] = Integer.valueOf(((String)arg).charAt(0));
                     }
                  }
               }
            }
         }
      }

      return String.format(format, args);
   }

   private static int index(String s) {
      int index = -1;
      if (s != null) {
         try {
            index = Integer.parseInt(s.substring(0, s.length() - 1));
         } catch (NumberFormatException var3) {
         }
      } else {
         index = 0;
      }

      return index;
   }

   private static boolean isPreviousArgument(String s) {
      return s != null && s.indexOf(60) >= 0;
   }
}
