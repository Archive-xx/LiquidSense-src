package jdk.internal.dynalink.beans;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import jdk.internal.dynalink.support.Lookup;

abstract class FacetIntrospector {
   private final Class<?> clazz;
   private final boolean instance;
   private final boolean isRestricted;
   protected final AccessibleMembersLookup membersLookup;

   FacetIntrospector(Class<?> clazz, boolean instance) {
      this.clazz = clazz;
      this.instance = instance;
      this.isRestricted = CheckRestrictedPackage.isRestrictedClass(clazz);
      this.membersLookup = new AccessibleMembersLookup(clazz, instance);
   }

   abstract Map<String, MethodHandle> getInnerClassGetters();

   Collection<Field> getFields() {
      if (this.isRestricted) {
         return Collections.emptySet();
      } else {
         Field[] fields = this.clazz.getFields();
         Collection<Field> cfields = new ArrayList(fields.length);
         Field[] var3 = fields;
         int var4 = fields.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Field field = var3[var5];
            boolean isStatic = Modifier.isStatic(field.getModifiers());
            if ((!isStatic || this.clazz == field.getDeclaringClass()) && this.instance != isStatic && this.isAccessible(field)) {
               cfields.add(field);
            }
         }

         return cfields;
      }
   }

   boolean isAccessible(Member m) {
      Class<?> declaring = m.getDeclaringClass();
      return declaring == this.clazz || !CheckRestrictedPackage.isRestrictedClass(declaring);
   }

   Collection<Method> getMethods() {
      return this.membersLookup.getMethods();
   }

   MethodHandle unreflectGetter(Field field) {
      return this.editMethodHandle(Lookup.PUBLIC.unreflectGetter(field));
   }

   MethodHandle unreflectSetter(Field field) {
      return this.editMethodHandle(Lookup.PUBLIC.unreflectSetter(field));
   }

   abstract MethodHandle editMethodHandle(MethodHandle var1);
}
