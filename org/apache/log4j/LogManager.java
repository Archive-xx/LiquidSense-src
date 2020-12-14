package org.apache.log4j;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import org.apache.log4j.helpers.Loader;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.helpers.OptionConverter;
import org.apache.log4j.spi.DefaultRepositorySelector;
import org.apache.log4j.spi.LoggerFactory;
import org.apache.log4j.spi.LoggerRepository;
import org.apache.log4j.spi.NOPLoggerRepository;
import org.apache.log4j.spi.RepositorySelector;
import org.apache.log4j.spi.RootLogger;

public class LogManager {
   /** @deprecated */
   public static final String DEFAULT_CONFIGURATION_FILE = "log4j.properties";
   static final String DEFAULT_XML_CONFIGURATION_FILE = "log4j.xml";
   /** @deprecated */
   public static final String DEFAULT_CONFIGURATION_KEY = "log4j.configuration";
   /** @deprecated */
   public static final String CONFIGURATOR_CLASS_KEY = "log4j.configuratorClass";
   /** @deprecated */
   public static final String DEFAULT_INIT_OVERRIDE_KEY = "log4j.defaultInitOverride";
   private static Object guard = null;
   private static RepositorySelector repositorySelector;

   public static void setRepositorySelector(RepositorySelector selector, Object guard) throws IllegalArgumentException {
      if (LogManager.guard != null && LogManager.guard != guard) {
         throw new IllegalArgumentException("Attempted to reset the LoggerFactory without possessing the guard.");
      } else if (selector == null) {
         throw new IllegalArgumentException("RepositorySelector must be non-null.");
      } else {
         LogManager.guard = guard;
         repositorySelector = selector;
      }
   }

   private static boolean isLikelySafeScenario(Exception ex) {
      StringWriter stringWriter = new StringWriter();
      ex.printStackTrace(new PrintWriter(stringWriter));
      String msg = stringWriter.toString();
      return msg.indexOf("org.apache.catalina.loader.WebappClassLoader.stop") != -1;
   }

   public static LoggerRepository getLoggerRepository() {
      if (repositorySelector == null) {
         repositorySelector = new DefaultRepositorySelector(new NOPLoggerRepository());
         guard = null;
         Exception ex = new IllegalStateException("Class invariant violation");
         String msg = "log4j called after unloading, see http://logging.apache.org/log4j/1.2/faq.html#unload.";
         if (isLikelySafeScenario(ex)) {
            LogLog.debug(msg, ex);
         } else {
            LogLog.error(msg, ex);
         }
      }

      return repositorySelector.getLoggerRepository();
   }

   public static Logger getRootLogger() {
      return getLoggerRepository().getRootLogger();
   }

   public static Logger getLogger(String name) {
      return getLoggerRepository().getLogger(name);
   }

   public static Logger getLogger(Class clazz) {
      return getLoggerRepository().getLogger(clazz.getName());
   }

   public static Logger getLogger(String name, LoggerFactory factory) {
      return getLoggerRepository().getLogger(name, factory);
   }

   public static Logger exists(String name) {
      return getLoggerRepository().exists(name);
   }

   public static Enumeration getCurrentLoggers() {
      return getLoggerRepository().getCurrentLoggers();
   }

   public static void shutdown() {
      getLoggerRepository().shutdown();
   }

   public static void resetConfiguration() {
      getLoggerRepository().resetConfiguration();
   }

   static {
      Hierarchy h = new Hierarchy(new RootLogger(Level.DEBUG));
      repositorySelector = new DefaultRepositorySelector(h);
      String override = OptionConverter.getSystemProperty("log4j.defaultInitOverride", (String)null);
      if (override != null && !"false".equalsIgnoreCase(override)) {
         LogLog.debug("Default initialization of overridden by log4j.defaultInitOverrideproperty.");
      } else {
         String configurationOptionStr = OptionConverter.getSystemProperty("log4j.configuration", (String)null);
         String configuratorClassName = OptionConverter.getSystemProperty("log4j.configuratorClass", (String)null);
         URL url = null;
         if (configurationOptionStr == null) {
            url = Loader.getResource("log4j.xml");
            if (url == null) {
               url = Loader.getResource("log4j.properties");
            }
         } else {
            try {
               url = new URL(configurationOptionStr);
            } catch (MalformedURLException var7) {
               url = Loader.getResource(configurationOptionStr);
            }
         }

         if (url != null) {
            LogLog.debug("Using URL [" + url + "] for automatic log4j configuration.");

            try {
               OptionConverter.selectAndConfigure(url, configuratorClassName, getLoggerRepository());
            } catch (NoClassDefFoundError var6) {
               LogLog.warn("Error during default initialization", var6);
            }
         } else {
            LogLog.debug("Could not find resource: [" + configurationOptionStr + "].");
         }
      }

   }
}
