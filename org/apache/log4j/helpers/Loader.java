package org.apache.log4j.helpers;

import java.io.InterruptedIOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;

public class Loader {
   static final String TSTR = "Caught Exception while in Loader.getResource. This may be innocuous.";
   private static boolean java1 = true;
   private static boolean ignoreTCL = false;

   /** @deprecated */
   public static URL getResource(String resource, Class clazz) {
      return getResource(resource);
   }

   public static URL getResource(String resource) {
      ClassLoader classLoader = null;
      URL url = null;

      try {
         if (!java1 && !ignoreTCL) {
            classLoader = getTCL();
            if (classLoader != null) {
               LogLog.debug("Trying to find [" + resource + "] using context classloader " + classLoader + ".");
               url = classLoader.getResource(resource);
               if (url != null) {
                  return url;
               }
            }
         }

         classLoader = Loader.class.getClassLoader();
         if (classLoader != null) {
            LogLog.debug("Trying to find [" + resource + "] using " + classLoader + " class loader.");
            url = classLoader.getResource(resource);
            if (url != null) {
               return url;
            }
         }
      } catch (IllegalAccessException var4) {
         LogLog.warn("Caught Exception while in Loader.getResource. This may be innocuous.", var4);
      } catch (InvocationTargetException var5) {
         if (var5.getTargetException() instanceof InterruptedException || var5.getTargetException() instanceof InterruptedIOException) {
            Thread.currentThread().interrupt();
         }

         LogLog.warn("Caught Exception while in Loader.getResource. This may be innocuous.", var5);
      } catch (Throwable var6) {
         LogLog.warn("Caught Exception while in Loader.getResource. This may be innocuous.", var6);
      }

      LogLog.debug("Trying to find [" + resource + "] using ClassLoader.getSystemResource().");
      return ClassLoader.getSystemResource(resource);
   }

   public static boolean isJava1() {
      return java1;
   }

   private static ClassLoader getTCL() throws IllegalAccessException, InvocationTargetException {
      Method method = null;

      try {
         method = Thread.class.getMethod("getContextClassLoader", (Class[])null);
      } catch (NoSuchMethodException var2) {
         return null;
      }

      return (ClassLoader)method.invoke(Thread.currentThread(), (Object[])null);
   }

   public static Class loadClass(String clazz) throws ClassNotFoundException {
      if (!java1 && !ignoreTCL) {
         try {
            return getTCL().loadClass(clazz);
         } catch (InvocationTargetException var2) {
            if (var2.getTargetException() instanceof InterruptedException || var2.getTargetException() instanceof InterruptedIOException) {
               Thread.currentThread().interrupt();
            }
         } catch (Throwable var3) {
         }

         return Class.forName(clazz);
      } else {
         return Class.forName(clazz);
      }
   }

   static {
      String prop = OptionConverter.getSystemProperty("java.version", (String)null);
      if (prop != null) {
         int i = prop.indexOf(46);
         if (i != -1 && prop.charAt(i + 1) != '1') {
            java1 = false;
         }
      }

      String ignoreTCLProp = OptionConverter.getSystemProperty("log4j.ignoreTCL", (String)null);
      if (ignoreTCLProp != null) {
         ignoreTCL = OptionConverter.toBoolean(ignoreTCLProp, true);
      }

   }
}
