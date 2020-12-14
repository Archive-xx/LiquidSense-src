package org.apache.log4j.jmx;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.InterruptedIOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Vector;
import javax.management.Attribute;
import javax.management.AttributeNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanConstructorInfo;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.management.ReflectionException;
import javax.management.RuntimeOperationsException;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.apache.log4j.helpers.OptionConverter;
import org.apache.log4j.spi.OptionHandler;

public class LayoutDynamicMBean extends AbstractDynamicMBean {
   private MBeanConstructorInfo[] dConstructors = new MBeanConstructorInfo[1];
   private Vector dAttributes = new Vector();
   private String dClassName = this.getClass().getName();
   private Hashtable dynamicProps = new Hashtable(5);
   private MBeanOperationInfo[] dOperations = new MBeanOperationInfo[1];
   private String dDescription = "This MBean acts as a management facade for log4j layouts.";
   private static Logger cat;
   private Layout layout;

   public LayoutDynamicMBean(Layout layout) throws IntrospectionException {
      this.layout = layout;
      this.buildDynamicMBeanInfo();
   }

   private void buildDynamicMBeanInfo() throws IntrospectionException {
      Constructor[] constructors = this.getClass().getConstructors();
      this.dConstructors[0] = new MBeanConstructorInfo("LayoutDynamicMBean(): Constructs a LayoutDynamicMBean instance", constructors[0]);
      BeanInfo bi = Introspector.getBeanInfo(this.layout.getClass());
      PropertyDescriptor[] pd = bi.getPropertyDescriptors();
      int size = pd.length;

      for(int i = 0; i < size; ++i) {
         String name = pd[i].getName();
         Method readMethod = pd[i].getReadMethod();
         Method writeMethod = pd[i].getWriteMethod();
         if (readMethod != null) {
            Class returnClass = readMethod.getReturnType();
            if (this.isSupportedType(returnClass)) {
               String returnClassName;
               if (returnClass.isAssignableFrom(Level.class)) {
                  returnClassName = "java.lang.String";
               } else {
                  returnClassName = returnClass.getName();
               }

               this.dAttributes.add(new MBeanAttributeInfo(name, returnClassName, "Dynamic", true, writeMethod != null, false));
               this.dynamicProps.put(name, new MethodUnion(readMethod, writeMethod));
            }
         }
      }

      MBeanParameterInfo[] params = new MBeanParameterInfo[0];
      this.dOperations[0] = new MBeanOperationInfo("activateOptions", "activateOptions(): add an layout", params, "void", 1);
   }

   private boolean isSupportedType(Class clazz) {
      if (clazz.isPrimitive()) {
         return true;
      } else if (clazz == String.class) {
         return true;
      } else {
         return clazz.isAssignableFrom(Level.class);
      }
   }

   public MBeanInfo getMBeanInfo() {
      cat.debug("getMBeanInfo called.");
      MBeanAttributeInfo[] attribs = new MBeanAttributeInfo[this.dAttributes.size()];
      this.dAttributes.toArray(attribs);
      return new MBeanInfo(this.dClassName, this.dDescription, attribs, this.dConstructors, this.dOperations, new MBeanNotificationInfo[0]);
   }

   public Object invoke(String operationName, Object[] params, String[] signature) throws MBeanException, ReflectionException {
      if (operationName.equals("activateOptions") && this.layout instanceof OptionHandler) {
         OptionHandler oh = this.layout;
         oh.activateOptions();
         return "Options activated.";
      } else {
         return null;
      }
   }

   protected Logger getLogger() {
      return cat;
   }

   public Object getAttribute(String attributeName) throws AttributeNotFoundException, MBeanException, ReflectionException {
      if (attributeName == null) {
         throw new RuntimeOperationsException(new IllegalArgumentException("Attribute name cannot be null"), "Cannot invoke a getter of " + this.dClassName + " with null attribute name");
      } else {
         MethodUnion mu = (MethodUnion)this.dynamicProps.get(attributeName);
         cat.debug("----name=" + attributeName + ", mu=" + mu);
         if (mu != null && mu.readMethod != null) {
            try {
               return mu.readMethod.invoke(this.layout, (Object[])null);
            } catch (InvocationTargetException var4) {
               if (var4.getTargetException() instanceof InterruptedException || var4.getTargetException() instanceof InterruptedIOException) {
                  Thread.currentThread().interrupt();
               }

               return null;
            } catch (IllegalAccessException var5) {
               return null;
            } catch (RuntimeException var6) {
               return null;
            }
         } else {
            throw new AttributeNotFoundException("Cannot find " + attributeName + " attribute in " + this.dClassName);
         }
      }
   }

   public void setAttribute(Attribute attribute) throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {
      if (attribute == null) {
         throw new RuntimeOperationsException(new IllegalArgumentException("Attribute cannot be null"), "Cannot invoke a setter of " + this.dClassName + " with null attribute");
      } else {
         String name = attribute.getName();
         Object value = attribute.getValue();
         if (name == null) {
            throw new RuntimeOperationsException(new IllegalArgumentException("Attribute name cannot be null"), "Cannot invoke the setter of " + this.dClassName + " with null attribute name");
         } else {
            MethodUnion mu = (MethodUnion)this.dynamicProps.get(name);
            if (mu != null && mu.writeMethod != null) {
               Object[] o = new Object[1];
               Class[] params = mu.writeMethod.getParameterTypes();
               if (params[0] == Priority.class) {
                  value = OptionConverter.toLevel((String)value, (Level)this.getAttribute(name));
               }

               o[0] = value;

               try {
                  mu.writeMethod.invoke(this.layout, o);
               } catch (InvocationTargetException var8) {
                  if (var8.getTargetException() instanceof InterruptedException || var8.getTargetException() instanceof InterruptedIOException) {
                     Thread.currentThread().interrupt();
                  }

                  cat.error("FIXME", var8);
               } catch (IllegalAccessException var9) {
                  cat.error("FIXME", var9);
               } catch (RuntimeException var10) {
                  cat.error("FIXME", var10);
               }

            } else {
               throw new AttributeNotFoundException("Attribute " + name + " not found in " + this.getClass().getName());
            }
         }
      }
   }

   static {
      cat = Logger.getLogger(LayoutDynamicMBean.class);
   }
}
