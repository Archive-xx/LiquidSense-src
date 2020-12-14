package org.apache.log4j.config;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.InterruptedIOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Properties;
import org.apache.log4j.Appender;
import org.apache.log4j.Level;
import org.apache.log4j.Priority;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.helpers.OptionConverter;
import org.apache.log4j.spi.ErrorHandler;
import org.apache.log4j.spi.OptionHandler;

public class PropertySetter {
   protected Object obj;
   protected PropertyDescriptor[] props;

   public PropertySetter(Object obj) {
      this.obj = obj;
   }

   protected void introspect() {
      try {
         BeanInfo bi = Introspector.getBeanInfo(this.obj.getClass());
         this.props = bi.getPropertyDescriptors();
      } catch (IntrospectionException var2) {
         LogLog.error("Failed to introspect " + this.obj + ": " + var2.getMessage());
         this.props = new PropertyDescriptor[0];
      }

   }

   public static void setProperties(Object obj, Properties properties, String prefix) {
      (new PropertySetter(obj)).setProperties(properties, prefix);
   }

   public void setProperties(Properties properties, String prefix) {
      int len = prefix.length();
      Enumeration e = properties.propertyNames();

      while(true) {
         while(true) {
            String key;
            String value;
            do {
               do {
                  do {
                     if (!e.hasMoreElements()) {
                        this.activate();
                        return;
                     }

                     key = (String)e.nextElement();
                  } while(!key.startsWith(prefix));
               } while(key.indexOf(46, len + 1) > 0);

               value = OptionConverter.findAndSubst(key, properties);
               key = key.substring(len);
            } while(("layout".equals(key) || "errorhandler".equals(key)) && this.obj instanceof Appender);

            PropertyDescriptor prop = this.getPropertyDescriptor(Introspector.decapitalize(key));
            if (prop != null && OptionHandler.class.isAssignableFrom(prop.getPropertyType()) && prop.getWriteMethod() != null) {
               OptionHandler opt = (OptionHandler)OptionConverter.instantiateByKey(properties, prefix + key, prop.getPropertyType(), (Object)null);
               PropertySetter setter = new PropertySetter(opt);
               setter.setProperties(properties, prefix + key + ".");

               try {
                  prop.getWriteMethod().invoke(this.obj, opt);
               } catch (IllegalAccessException var11) {
                  LogLog.warn("Failed to set property [" + key + "] to value \"" + value + "\". ", var11);
               } catch (InvocationTargetException var12) {
                  if (var12.getTargetException() instanceof InterruptedException || var12.getTargetException() instanceof InterruptedIOException) {
                     Thread.currentThread().interrupt();
                  }

                  LogLog.warn("Failed to set property [" + key + "] to value \"" + value + "\". ", var12);
               } catch (RuntimeException var13) {
                  LogLog.warn("Failed to set property [" + key + "] to value \"" + value + "\". ", var13);
               }
            } else {
               this.setProperty(key, value);
            }
         }
      }
   }

   public void setProperty(String name, String value) {
      if (value != null) {
         name = Introspector.decapitalize(name);
         PropertyDescriptor prop = this.getPropertyDescriptor(name);
         if (prop == null) {
            LogLog.warn("No such property [" + name + "] in " + this.obj.getClass().getName() + ".");
         } else {
            try {
               this.setProperty(prop, name, value);
            } catch (PropertySetterException var5) {
               LogLog.warn("Failed to set property [" + name + "] to value \"" + value + "\". ", var5.rootCause);
            }
         }

      }
   }

   public void setProperty(PropertyDescriptor prop, String name, String value) throws PropertySetterException {
      Method setter = prop.getWriteMethod();
      if (setter == null) {
         throw new PropertySetterException("No setter for property [" + name + "].");
      } else {
         Class[] paramTypes = setter.getParameterTypes();
         if (paramTypes.length != 1) {
            throw new PropertySetterException("#params for setter != 1");
         } else {
            Object arg;
            try {
               arg = this.convertArg(value, paramTypes[0]);
            } catch (Throwable var8) {
               throw new PropertySetterException("Conversion to type [" + paramTypes[0] + "] failed. Reason: " + var8);
            }

            if (arg == null) {
               throw new PropertySetterException("Conversion to type [" + paramTypes[0] + "] failed.");
            } else {
               LogLog.debug("Setting property [" + name + "] to [" + arg + "].");

               try {
                  setter.invoke(this.obj, arg);
               } catch (IllegalAccessException var9) {
                  throw new PropertySetterException(var9);
               } catch (InvocationTargetException var10) {
                  if (var10.getTargetException() instanceof InterruptedException || var10.getTargetException() instanceof InterruptedIOException) {
                     Thread.currentThread().interrupt();
                  }

                  throw new PropertySetterException(var10);
               } catch (RuntimeException var11) {
                  throw new PropertySetterException(var11);
               }
            }
         }
      }
   }

   protected Object convertArg(String val, Class type) {
      if (val == null) {
         return null;
      } else {
         String v = val.trim();
         if (String.class.isAssignableFrom(type)) {
            return val;
         } else if (Integer.TYPE.isAssignableFrom(type)) {
            return new Integer(v);
         } else if (Long.TYPE.isAssignableFrom(type)) {
            return new Long(v);
         } else {
            if (Boolean.TYPE.isAssignableFrom(type)) {
               if ("true".equalsIgnoreCase(v)) {
                  return Boolean.TRUE;
               }

               if ("false".equalsIgnoreCase(v)) {
                  return Boolean.FALSE;
               }
            } else {
               if (Priority.class.isAssignableFrom(type)) {
                  return OptionConverter.toLevel(v, Level.DEBUG);
               }

               if (ErrorHandler.class.isAssignableFrom(type)) {
                  return OptionConverter.instantiateByClassName(v, ErrorHandler.class, (Object)null);
               }
            }

            return null;
         }
      }
   }

   protected PropertyDescriptor getPropertyDescriptor(String name) {
      if (this.props == null) {
         this.introspect();
      }

      for(int i = 0; i < this.props.length; ++i) {
         if (name.equals(this.props[i].getName())) {
            return this.props[i];
         }
      }

      return null;
   }

   public void activate() {
      if (this.obj instanceof OptionHandler) {
         ((OptionHandler)this.obj).activateOptions();
      }

   }
}
