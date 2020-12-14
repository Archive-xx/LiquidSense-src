package org.apache.log4j.helpers;

import java.io.InputStream;
import java.io.InterruptedIOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Properties;
import org.apache.log4j.Level;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.spi.Configurator;
import org.apache.log4j.spi.LoggerRepository;

public class OptionConverter {
   static String DELIM_START = "${";
   static char DELIM_STOP = '}';
   static int DELIM_START_LEN = 2;
   static int DELIM_STOP_LEN = 1;

   private OptionConverter() {
   }

   public static String[] concatanateArrays(String[] l, String[] r) {
      int len = l.length + r.length;
      String[] a = new String[len];
      System.arraycopy(l, 0, a, 0, l.length);
      System.arraycopy(r, 0, a, l.length, r.length);
      return a;
   }

   public static String convertSpecialChars(String s) {
      int len = s.length();
      StringBuffer sbuf = new StringBuffer(len);

      char c;
      for(int i = 0; i < len; sbuf.append(c)) {
         c = s.charAt(i++);
         if (c == '\\') {
            c = s.charAt(i++);
            if (c == 'n') {
               c = '\n';
            } else if (c == 'r') {
               c = '\r';
            } else if (c == 't') {
               c = '\t';
            } else if (c == 'f') {
               c = '\f';
            } else if (c == '\b') {
               c = '\b';
            } else if (c == '"') {
               c = '"';
            } else if (c == '\'') {
               c = '\'';
            } else if (c == '\\') {
               c = '\\';
            }
         }
      }

      return sbuf.toString();
   }

   public static String getSystemProperty(String key, String def) {
      try {
         return System.getProperty(key, def);
      } catch (Throwable var3) {
         LogLog.debug("Was not allowed to read system property \"" + key + "\".");
         return def;
      }
   }

   public static Object instantiateByKey(Properties props, String key, Class superClass, Object defaultValue) {
      String className = findAndSubst(key, props);
      if (className == null) {
         LogLog.error("Could not find value for key " + key);
         return defaultValue;
      } else {
         return instantiateByClassName(className.trim(), superClass, defaultValue);
      }
   }

   public static boolean toBoolean(String value, boolean dEfault) {
      if (value == null) {
         return dEfault;
      } else {
         String trimmedVal = value.trim();
         if ("true".equalsIgnoreCase(trimmedVal)) {
            return true;
         } else {
            return "false".equalsIgnoreCase(trimmedVal) ? false : dEfault;
         }
      }
   }

   public static int toInt(String value, int dEfault) {
      if (value != null) {
         String s = value.trim();

         try {
            return Integer.valueOf(s);
         } catch (NumberFormatException var4) {
            LogLog.error("[" + s + "] is not in proper int form.");
            var4.printStackTrace();
         }
      }

      return dEfault;
   }

   public static Level toLevel(String value, Level defaultValue) {
      if (value == null) {
         return defaultValue;
      } else {
         value = value.trim();
         int hashIndex = value.indexOf(35);
         if (hashIndex == -1) {
            return "NULL".equalsIgnoreCase(value) ? null : Level.toLevel(value, defaultValue);
         } else {
            Level result = defaultValue;
            String clazz = value.substring(hashIndex + 1);
            String levelName = value.substring(0, hashIndex);
            if ("NULL".equalsIgnoreCase(levelName)) {
               return null;
            } else {
               LogLog.debug("toLevel:class=[" + clazz + "]" + ":pri=[" + levelName + "]");

               try {
                  Class customLevel = Loader.loadClass(clazz);
                  Class[] paramTypes = new Class[]{String.class, Level.class};
                  Method toLevelMethod = customLevel.getMethod("toLevel", paramTypes);
                  Object[] params = new Object[]{levelName, defaultValue};
                  Object o = toLevelMethod.invoke((Object)null, params);
                  result = (Level)o;
               } catch (ClassNotFoundException var11) {
                  LogLog.warn("custom level class [" + clazz + "] not found.");
               } catch (NoSuchMethodException var12) {
                  LogLog.warn("custom level class [" + clazz + "]" + " does not have a class function toLevel(String, Level)", var12);
               } catch (InvocationTargetException var13) {
                  if (var13.getTargetException() instanceof InterruptedException || var13.getTargetException() instanceof InterruptedIOException) {
                     Thread.currentThread().interrupt();
                  }

                  LogLog.warn("custom level class [" + clazz + "]" + " could not be instantiated", var13);
               } catch (ClassCastException var14) {
                  LogLog.warn("class [" + clazz + "] is not a subclass of org.apache.log4j.Level", var14);
               } catch (IllegalAccessException var15) {
                  LogLog.warn("class [" + clazz + "] cannot be instantiated due to access restrictions", var15);
               } catch (RuntimeException var16) {
                  LogLog.warn("class [" + clazz + "], level [" + levelName + "] conversion failed.", var16);
               }

               return result;
            }
         }
      }
   }

   public static long toFileSize(String value, long dEfault) {
      if (value == null) {
         return dEfault;
      } else {
         String s = value.trim().toUpperCase();
         long multiplier = 1L;
         int index;
         if ((index = s.indexOf("KB")) != -1) {
            multiplier = 1024L;
            s = s.substring(0, index);
         } else if ((index = s.indexOf("MB")) != -1) {
            multiplier = 1048576L;
            s = s.substring(0, index);
         } else if ((index = s.indexOf("GB")) != -1) {
            multiplier = 1073741824L;
            s = s.substring(0, index);
         }

         if (s != null) {
            try {
               return Long.valueOf(s) * multiplier;
            } catch (NumberFormatException var8) {
               LogLog.error("[" + s + "] is not in proper int form.");
               LogLog.error("[" + value + "] not in expected format.", var8);
            }
         }

         return dEfault;
      }
   }

   public static String findAndSubst(String key, Properties props) {
      String value = props.getProperty(key);
      if (value == null) {
         return null;
      } else {
         try {
            return substVars(value, props);
         } catch (IllegalArgumentException var4) {
            LogLog.error("Bad option value [" + value + "].", var4);
            return value;
         }
      }
   }

   public static Object instantiateByClassName(String className, Class superClass, Object defaultValue) {
      if (className != null) {
         try {
            Class classObj = Loader.loadClass(className);
            if (!superClass.isAssignableFrom(classObj)) {
               LogLog.error("A \"" + className + "\" object is not assignable to a \"" + superClass.getName() + "\" variable.");
               LogLog.error("The class \"" + superClass.getName() + "\" was loaded by ");
               LogLog.error("[" + superClass.getClassLoader() + "] whereas object of type ");
               LogLog.error("\"" + classObj.getName() + "\" was loaded by [" + classObj.getClassLoader() + "].");
               return defaultValue;
            }

            return classObj.newInstance();
         } catch (ClassNotFoundException var4) {
            LogLog.error("Could not instantiate class [" + className + "].", var4);
         } catch (IllegalAccessException var5) {
            LogLog.error("Could not instantiate class [" + className + "].", var5);
         } catch (InstantiationException var6) {
            LogLog.error("Could not instantiate class [" + className + "].", var6);
         } catch (RuntimeException var7) {
            LogLog.error("Could not instantiate class [" + className + "].", var7);
         }
      }

      return defaultValue;
   }

   public static String substVars(String val, Properties props) throws IllegalArgumentException {
      StringBuffer sbuf = new StringBuffer();
      int i = 0;

      while(true) {
         int j = val.indexOf(DELIM_START, i);
         if (j == -1) {
            if (i == 0) {
               return val;
            } else {
               sbuf.append(val.substring(i, val.length()));
               return sbuf.toString();
            }
         }

         sbuf.append(val.substring(i, j));
         int k = val.indexOf(DELIM_STOP, j);
         if (k == -1) {
            throw new IllegalArgumentException('"' + val + "\" has no closing brace. Opening brace at position " + j + '.');
         }

         j += DELIM_START_LEN;
         String key = val.substring(j, k);
         String replacement = getSystemProperty(key, (String)null);
         if (replacement == null && props != null) {
            replacement = props.getProperty(key);
         }

         if (replacement != null) {
            String recursiveReplacement = substVars(replacement, props);
            sbuf.append(recursiveReplacement);
         }

         i = k + DELIM_STOP_LEN;
      }
   }

   public static void selectAndConfigure(InputStream inputStream, String clazz, LoggerRepository hierarchy) {
      Configurator configurator = null;
      if (clazz != null) {
         LogLog.debug("Preferred configurator class: " + clazz);
         configurator = (Configurator)instantiateByClassName(clazz, Configurator.class, (Object)null);
         if (configurator == null) {
            LogLog.error("Could not instantiate configurator [" + clazz + "].");
            return;
         }
      } else {
         configurator = new PropertyConfigurator();
      }

      ((Configurator)configurator).doConfigure(inputStream, hierarchy);
   }

   public static void selectAndConfigure(URL url, String clazz, LoggerRepository hierarchy) {
      Configurator configurator = null;
      String filename = url.getFile();
      if (clazz == null && filename != null && filename.endsWith(".xml")) {
         clazz = "org.apache.log4j.xml.DOMConfigurator";
      }

      if (clazz != null) {
         LogLog.debug("Preferred configurator class: " + clazz);
         configurator = (Configurator)instantiateByClassName(clazz, Configurator.class, (Object)null);
         if (configurator == null) {
            LogLog.error("Could not instantiate configurator [" + clazz + "].");
            return;
         }
      } else {
         configurator = new PropertyConfigurator();
      }

      ((Configurator)configurator).doConfigure(url, hierarchy);
   }
}
