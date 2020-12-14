package org.apache.log4j.net;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import org.apache.log4j.helpers.LogLog;

public class ZeroConfSupport {
   private static Object jmDNS = initializeJMDNS();
   Object serviceInfo;
   private static Class jmDNSClass;
   private static Class serviceInfoClass;

   public ZeroConfSupport(String zone, int port, String name, Map properties) {
      boolean isVersion3 = false;

      try {
         jmDNSClass.getMethod("create", (Class[])null);
         isVersion3 = true;
      } catch (NoSuchMethodException var7) {
      }

      if (isVersion3) {
         LogLog.debug("using JmDNS version 3 to construct serviceInfo instance");
         this.serviceInfo = this.buildServiceInfoVersion3(zone, port, name, properties);
      } else {
         LogLog.debug("using JmDNS version 1.0 to construct serviceInfo instance");
         this.serviceInfo = this.buildServiceInfoVersion1(zone, port, name, properties);
      }

   }

   public ZeroConfSupport(String zone, int port, String name) {
      this(zone, port, name, new HashMap());
   }

   private static Object createJmDNSVersion1() {
      try {
         return jmDNSClass.newInstance();
      } catch (InstantiationException var1) {
         LogLog.warn("Unable to instantiate JMDNS", var1);
      } catch (IllegalAccessException var2) {
         LogLog.warn("Unable to instantiate JMDNS", var2);
      }

      return null;
   }

   private static Object createJmDNSVersion3() {
      try {
         Method jmDNSCreateMethod = jmDNSClass.getMethod("create", (Class[])null);
         return jmDNSCreateMethod.invoke((Object)null, (Object[])null);
      } catch (IllegalAccessException var1) {
         LogLog.warn("Unable to instantiate jmdns class", var1);
      } catch (NoSuchMethodException var2) {
         LogLog.warn("Unable to access constructor", var2);
      } catch (InvocationTargetException var3) {
         LogLog.warn("Unable to call constructor", var3);
      }

      return null;
   }

   private Object buildServiceInfoVersion1(String zone, int port, String name, Map properties) {
      Hashtable hashtableProperties = new Hashtable(properties);

      try {
         Class[] args = new Class[]{String.class, String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, Hashtable.class};
         Constructor constructor = serviceInfoClass.getConstructor(args);
         Object[] values = new Object[]{zone, name, new Integer(port), new Integer(0), new Integer(0), hashtableProperties};
         Object result = constructor.newInstance(values);
         LogLog.debug("created serviceinfo: " + result);
         return result;
      } catch (IllegalAccessException var10) {
         LogLog.warn("Unable to construct ServiceInfo instance", var10);
      } catch (NoSuchMethodException var11) {
         LogLog.warn("Unable to get ServiceInfo constructor", var11);
      } catch (InstantiationException var12) {
         LogLog.warn("Unable to construct ServiceInfo instance", var12);
      } catch (InvocationTargetException var13) {
         LogLog.warn("Unable to construct ServiceInfo instance", var13);
      }

      return null;
   }

   private Object buildServiceInfoVersion3(String zone, int port, String name, Map properties) {
      try {
         Class[] args = new Class[]{String.class, String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, Map.class};
         Method serviceInfoCreateMethod = serviceInfoClass.getMethod("create", args);
         Object[] values = new Object[]{zone, name, new Integer(port), new Integer(0), new Integer(0), properties};
         Object result = serviceInfoCreateMethod.invoke((Object)null, values);
         LogLog.debug("created serviceinfo: " + result);
         return result;
      } catch (IllegalAccessException var9) {
         LogLog.warn("Unable to invoke create method", var9);
      } catch (NoSuchMethodException var10) {
         LogLog.warn("Unable to find create method", var10);
      } catch (InvocationTargetException var11) {
         LogLog.warn("Unable to invoke create method", var11);
      }

      return null;
   }

   public void advertise() {
      try {
         Method method = jmDNSClass.getMethod("registerService", serviceInfoClass);
         method.invoke(jmDNS, this.serviceInfo);
         LogLog.debug("registered serviceInfo: " + this.serviceInfo);
      } catch (IllegalAccessException var2) {
         LogLog.warn("Unable to invoke registerService method", var2);
      } catch (NoSuchMethodException var3) {
         LogLog.warn("No registerService method", var3);
      } catch (InvocationTargetException var4) {
         LogLog.warn("Unable to invoke registerService method", var4);
      }

   }

   public void unadvertise() {
      try {
         Method method = jmDNSClass.getMethod("unregisterService", serviceInfoClass);
         method.invoke(jmDNS, this.serviceInfo);
         LogLog.debug("unregistered serviceInfo: " + this.serviceInfo);
      } catch (IllegalAccessException var2) {
         LogLog.warn("Unable to invoke unregisterService method", var2);
      } catch (NoSuchMethodException var3) {
         LogLog.warn("No unregisterService method", var3);
      } catch (InvocationTargetException var4) {
         LogLog.warn("Unable to invoke unregisterService method", var4);
      }

   }

   private static Object initializeJMDNS() {
      try {
         jmDNSClass = Class.forName("javax.jmdns.JmDNS");
         serviceInfoClass = Class.forName("javax.jmdns.ServiceInfo");
      } catch (ClassNotFoundException var3) {
         LogLog.warn("JmDNS or serviceInfo class not found", var3);
      }

      boolean isVersion3 = false;

      try {
         jmDNSClass.getMethod("create", (Class[])null);
         isVersion3 = true;
      } catch (NoSuchMethodException var2) {
      }

      return isVersion3 ? createJmDNSVersion3() : createJmDNSVersion1();
   }

   public static Object getJMDNSInstance() {
      return jmDNS;
   }
}
