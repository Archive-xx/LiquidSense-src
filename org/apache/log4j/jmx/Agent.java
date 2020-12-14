package org.apache.log4j.jmx;

import java.io.InterruptedIOException;
import java.lang.reflect.InvocationTargetException;
import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import org.apache.log4j.Logger;

/** @deprecated */
public class Agent {
   /** @deprecated */
   static Logger log;

   private static Object createServer() {
      Object newInstance = null;

      try {
         newInstance = Class.forName("com.sun.jdmk.comm.HtmlAdapterServer").newInstance();
         return newInstance;
      } catch (ClassNotFoundException var2) {
         throw new RuntimeException(var2.toString());
      } catch (InstantiationException var3) {
         throw new RuntimeException(var3.toString());
      } catch (IllegalAccessException var4) {
         throw new RuntimeException(var4.toString());
      }
   }

   private static void startServer(Object server) {
      try {
         server.getClass().getMethod("start").invoke(server);
      } catch (InvocationTargetException var3) {
         Throwable cause = var3.getTargetException();
         if (cause instanceof RuntimeException) {
            throw (RuntimeException)cause;
         } else if (cause == null) {
            throw new RuntimeException();
         } else {
            if (cause instanceof InterruptedException || cause instanceof InterruptedIOException) {
               Thread.currentThread().interrupt();
            }

            throw new RuntimeException(cause.toString());
         }
      } catch (NoSuchMethodException var4) {
         throw new RuntimeException(var4.toString());
      } catch (IllegalAccessException var5) {
         throw new RuntimeException(var5.toString());
      }
   }

   /** @deprecated */
   public void start() {
      MBeanServer server = MBeanServerFactory.createMBeanServer();
      Object html = createServer();

      try {
         log.info("Registering HtmlAdaptorServer instance.");
         server.registerMBean(html, new ObjectName("Adaptor:name=html,port=8082"));
         log.info("Registering HierarchyDynamicMBean instance.");
         HierarchyDynamicMBean hdm = new HierarchyDynamicMBean();
         server.registerMBean(hdm, new ObjectName("log4j:hiearchy=default"));
      } catch (JMException var4) {
         log.error("Problem while registering MBeans instances.", var4);
         return;
      } catch (RuntimeException var5) {
         log.error("Problem while registering MBeans instances.", var5);
         return;
      }

      startServer(html);
   }

   static {
      log = Logger.getLogger(Agent.class);
   }
}
