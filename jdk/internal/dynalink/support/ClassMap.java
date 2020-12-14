package jdk.internal.dynalink.support;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public abstract class ClassMap<T> {
   private final ConcurrentMap<Class<?>, T> map = new ConcurrentHashMap();
   private final Map<Class<?>, Reference<T>> weakMap = new WeakHashMap();
   private final ClassLoader classLoader;

   protected ClassMap(ClassLoader classLoader) {
      this.classLoader = classLoader;
   }

   protected abstract T computeValue(Class<?> var1);

   public T get(final Class<?> clazz) {
      T v = this.map.get(clazz);
      if (v != null) {
         return v;
      } else {
         Reference ref;
         synchronized(this.weakMap) {
            ref = (Reference)this.weakMap.get(clazz);
         }

         Object newV;
         if (ref != null) {
            newV = ref.get();
            if (newV != null) {
               return newV;
            }
         }

         newV = this.computeValue(clazz);

         assert newV != null;

         ClassLoader clazzLoader = (ClassLoader)AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() {
            public ClassLoader run() {
               return clazz.getClassLoader();
            }
         }, ClassLoaderGetterContextProvider.GET_CLASS_LOADER_CONTEXT);
         if (Guards.canReferenceDirectly(this.classLoader, clazzLoader)) {
            T oldV = this.map.putIfAbsent(clazz, newV);
            return oldV != null ? oldV : newV;
         } else {
            synchronized(this.weakMap) {
               ref = (Reference)this.weakMap.get(clazz);
               if (ref != null) {
                  T oldV = ref.get();
                  if (oldV != null) {
                     return oldV;
                  }
               }

               this.weakMap.put(clazz, new SoftReference(newV));
               return newV;
            }
         }
      }
   }
}
