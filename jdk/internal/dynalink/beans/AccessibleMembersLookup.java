package jdk.internal.dynalink.beans;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

class AccessibleMembersLookup {
   private final Map<AccessibleMembersLookup.MethodSignature, Method> methods = new HashMap();
   private final Set<Class<?>> innerClasses = new LinkedHashSet();
   private final boolean instance;

   AccessibleMembersLookup(Class<?> clazz, boolean instance) {
      this.instance = instance;
      this.lookupAccessibleMembers(clazz);
   }

   Method getAccessibleMethod(Method m) {
      return m == null ? null : (Method)this.methods.get(new AccessibleMembersLookup.MethodSignature(m));
   }

   Collection<Method> getMethods() {
      return this.methods.values();
   }

   Class<?>[] getInnerClasses() {
      return (Class[])this.innerClasses.toArray(new Class[this.innerClasses.size()]);
   }

   private void lookupAccessibleMembers(Class<?> clazz) {
      boolean searchSuperTypes;
      int i;
      Class[] interfaces;
      if (!CheckRestrictedPackage.isRestrictedClass(clazz)) {
         searchSuperTypes = false;
         Method[] var3 = clazz.getMethods();
         i = var3.length;

         int var5;
         for(var5 = 0; var5 < i; ++var5) {
            Method method = var3[var5];
            boolean isStatic = Modifier.isStatic(method.getModifiers());
            if (this.instance != isStatic) {
               AccessibleMembersLookup.MethodSignature sig = new AccessibleMembersLookup.MethodSignature(method);
               if (!this.methods.containsKey(sig)) {
                  Class<?> declaringClass = method.getDeclaringClass();
                  if (declaringClass != clazz && CheckRestrictedPackage.isRestrictedClass(declaringClass)) {
                     searchSuperTypes = true;
                  } else if (!isStatic || clazz == declaringClass) {
                     this.methods.put(sig, method);
                  }
               }
            }
         }

         interfaces = clazz.getClasses();
         i = interfaces.length;

         for(var5 = 0; var5 < i; ++var5) {
            Class<?> innerClass = interfaces[var5];
            this.innerClasses.add(innerClass);
         }
      } else {
         searchSuperTypes = true;
      }

      if (this.instance && searchSuperTypes) {
         interfaces = clazz.getInterfaces();

         for(i = 0; i < interfaces.length; ++i) {
            this.lookupAccessibleMembers(interfaces[i]);
         }

         Class<?> superclass = clazz.getSuperclass();
         if (superclass != null) {
            this.lookupAccessibleMembers(superclass);
         }
      }

   }

   static final class MethodSignature {
      private final String name;
      private final Class<?>[] args;

      MethodSignature(String name, Class<?>[] args) {
         this.name = name;
         this.args = args;
      }

      MethodSignature(Method method) {
         this(method.getName(), method.getParameterTypes());
      }

      public boolean equals(Object o) {
         if (!(o instanceof AccessibleMembersLookup.MethodSignature)) {
            return false;
         } else {
            AccessibleMembersLookup.MethodSignature ms = (AccessibleMembersLookup.MethodSignature)o;
            return ms.name.equals(this.name) && Arrays.equals(this.args, ms.args);
         }
      }

      public int hashCode() {
         return this.name.hashCode() ^ Arrays.hashCode(this.args);
      }

      public String toString() {
         StringBuilder b = new StringBuilder();
         b.append("[MethodSignature ").append(this.name).append('(');
         if (this.args.length > 0) {
            b.append(this.args[0].getCanonicalName());

            for(int i = 1; i < this.args.length; ++i) {
               b.append(", ").append(this.args[i].getCanonicalName());
            }
         }

         return b.append(")]").toString();
      }
   }
}
