package org.apache.log4j.config;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.InterruptedIOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.apache.log4j.Priority;
import org.apache.log4j.helpers.LogLog;

public class PropertyGetter {
   protected static final Object[] NULL_ARG = new Object[0];
   protected Object obj;
   protected PropertyDescriptor[] props;

   public PropertyGetter(Object obj) throws IntrospectionException {
      BeanInfo bi = Introspector.getBeanInfo(obj.getClass());
      this.props = bi.getPropertyDescriptors();
      this.obj = obj;
   }

   public static void getProperties(Object obj, PropertyGetter.PropertyCallback callback, String prefix) {
      try {
         (new PropertyGetter(obj)).getProperties(callback, prefix);
      } catch (IntrospectionException var4) {
         LogLog.error("Failed to introspect object " + obj, var4);
      }

   }

   public void getProperties(PropertyGetter.PropertyCallback callback, String prefix) {
      for(int i = 0; i < this.props.length; ++i) {
         Method getter = this.props[i].getReadMethod();
         if (getter != null && this.isHandledType(getter.getReturnType())) {
            String name = this.props[i].getName();

            try {
               Object result = getter.invoke(this.obj, NULL_ARG);
               if (result != null) {
                  callback.foundProperty(this.obj, prefix, name, result);
               }
            } catch (IllegalAccessException var7) {
               LogLog.warn("Failed to get value of property " + name);
            } catch (InvocationTargetException var8) {
               if (var8.getTargetException() instanceof InterruptedException || var8.getTargetException() instanceof InterruptedIOException) {
                  Thread.currentThread().interrupt();
               }

               LogLog.warn("Failed to get value of property " + name);
            } catch (RuntimeException var9) {
               LogLog.warn("Failed to get value of property " + name);
            }
         }
      }

   }

   protected boolean isHandledType(Class type) {
      return String.class.isAssignableFrom(type) || Integer.TYPE.isAssignableFrom(type) || Long.TYPE.isAssignableFrom(type) || Boolean.TYPE.isAssignableFrom(type) || Priority.class.isAssignableFrom(type);
   }

   public interface PropertyCallback {
      void foundProperty(Object var1, String var2, String var3, Object var4);
   }
}
