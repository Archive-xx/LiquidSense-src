package org.apache.log4j.or;

import java.util.Hashtable;
import org.apache.log4j.helpers.Loader;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.helpers.OptionConverter;
import org.apache.log4j.spi.RendererSupport;

public class RendererMap {
   Hashtable map = new Hashtable();
   static ObjectRenderer defaultRenderer = new DefaultRenderer();

   public static void addRenderer(RendererSupport repository, String renderedClassName, String renderingClassName) {
      LogLog.debug("Rendering class: [" + renderingClassName + "], Rendered class: [" + renderedClassName + "].");
      ObjectRenderer renderer = (ObjectRenderer)OptionConverter.instantiateByClassName(renderingClassName, ObjectRenderer.class, (Object)null);
      if (renderer == null) {
         LogLog.error("Could not instantiate renderer [" + renderingClassName + "].");
      } else {
         try {
            Class renderedClass = Loader.loadClass(renderedClassName);
            repository.setRenderer(renderedClass, renderer);
         } catch (ClassNotFoundException var5) {
            LogLog.error("Could not find class [" + renderedClassName + "].", var5);
         }

      }
   }

   public String findAndRender(Object o) {
      return o == null ? null : this.get(o.getClass()).doRender(o);
   }

   public ObjectRenderer get(Object o) {
      return o == null ? null : this.get(o.getClass());
   }

   public ObjectRenderer get(Class clazz) {
      ObjectRenderer r = null;

      for(Class c = clazz; c != null; c = c.getSuperclass()) {
         r = (ObjectRenderer)this.map.get(c);
         if (r != null) {
            return r;
         }

         r = this.searchInterfaces(c);
         if (r != null) {
            return r;
         }
      }

      return defaultRenderer;
   }

   ObjectRenderer searchInterfaces(Class c) {
      ObjectRenderer r = (ObjectRenderer)this.map.get(c);
      if (r != null) {
         return r;
      } else {
         Class[] ia = c.getInterfaces();

         for(int i = 0; i < ia.length; ++i) {
            r = this.searchInterfaces(ia[i]);
            if (r != null) {
               return r;
            }
         }

         return null;
      }
   }

   public ObjectRenderer getDefaultRenderer() {
      return defaultRenderer;
   }

   public void clear() {
      this.map.clear();
   }

   public void put(Class clazz, ObjectRenderer or) {
      this.map.put(clazz, or);
   }
}
