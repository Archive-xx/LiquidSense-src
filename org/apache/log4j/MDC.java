package org.apache.log4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import org.apache.log4j.helpers.Loader;
import org.apache.log4j.helpers.ThreadLocalMap;

public class MDC {
   static final MDC mdc = new MDC();
   static final int HT_SIZE = 7;
   boolean java1 = Loader.isJava1();
   Object tlm;
   private Method removeMethod;

   private MDC() {
      if (!this.java1) {
         this.tlm = new ThreadLocalMap();
      }

      try {
         this.removeMethod = ThreadLocal.class.getMethod("remove", (Class[])null);
      } catch (NoSuchMethodException var2) {
      }

   }

   public static void put(String key, Object o) {
      if (mdc != null) {
         mdc.put0(key, o);
      }

   }

   public static Object get(String key) {
      return mdc != null ? mdc.get0(key) : null;
   }

   public static void remove(String key) {
      if (mdc != null) {
         mdc.remove0(key);
      }

   }

   public static Hashtable getContext() {
      return mdc != null ? mdc.getContext0() : null;
   }

   public static void clear() {
      if (mdc != null) {
         mdc.clear0();
      }

   }

   private void put0(String key, Object o) {
      if (!this.java1 && this.tlm != null) {
         Hashtable ht = (Hashtable)((ThreadLocalMap)this.tlm).get();
         if (ht == null) {
            ht = new Hashtable(7);
            ((ThreadLocalMap)this.tlm).set(ht);
         }

         ht.put(key, o);
      }
   }

   private Object get0(String key) {
      if (!this.java1 && this.tlm != null) {
         Hashtable ht = (Hashtable)((ThreadLocalMap)this.tlm).get();
         return ht != null && key != null ? ht.get(key) : null;
      } else {
         return null;
      }
   }

   private void remove0(String key) {
      if (!this.java1 && this.tlm != null) {
         Hashtable ht = (Hashtable)((ThreadLocalMap)this.tlm).get();
         if (ht != null) {
            ht.remove(key);
            if (ht.isEmpty()) {
               this.clear0();
            }
         }
      }

   }

   private Hashtable getContext0() {
      return !this.java1 && this.tlm != null ? (Hashtable)((ThreadLocalMap)this.tlm).get() : null;
   }

   private void clear0() {
      if (!this.java1 && this.tlm != null) {
         Hashtable ht = (Hashtable)((ThreadLocalMap)this.tlm).get();
         if (ht != null) {
            ht.clear();
         }

         if (this.removeMethod != null) {
            try {
               this.removeMethod.invoke(this.tlm, (Object[])null);
            } catch (IllegalAccessException var3) {
            } catch (InvocationTargetException var4) {
            }
         }
      }

   }
}
