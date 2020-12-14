package jdk.internal.dynalink.beans;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

class CallerSensitiveDynamicMethod extends SingleDynamicMethod {
   private final AccessibleObject target;
   private final MethodType type;

   CallerSensitiveDynamicMethod(AccessibleObject target) {
      super(getName(target));
      this.target = target;
      this.type = getMethodType(target);
   }

   private static String getName(AccessibleObject target) {
      Member m = (Member)target;
      boolean constructor = m instanceof Constructor;
      return getMethodNameWithSignature(getMethodType(target), constructor ? m.getName() : getClassAndMethodName(m.getDeclaringClass(), m.getName()), !constructor);
   }

   MethodType getMethodType() {
      return this.type;
   }

   private static MethodType getMethodType(AccessibleObject ao) {
      boolean isMethod = ao instanceof Method;
      Class<?> rtype = isMethod ? ((Method)ao).getReturnType() : ((Constructor)ao).getDeclaringClass();
      Class<?>[] ptypes = isMethod ? ((Method)ao).getParameterTypes() : ((Constructor)ao).getParameterTypes();
      MethodType type = MethodType.methodType(rtype, ptypes);
      Member m = (Member)ao;
      return type.insertParameterTypes(0, new Class[]{isMethod ? (Modifier.isStatic(m.getModifiers()) ? Object.class : m.getDeclaringClass()) : StaticClass.class});
   }

   boolean isVarArgs() {
      return this.target instanceof Method ? ((Method)this.target).isVarArgs() : ((Constructor)this.target).isVarArgs();
   }

   MethodHandle getTarget(Lookup lookup) {
      if (this.target instanceof Method) {
         MethodHandle mh = jdk.internal.dynalink.support.Lookup.unreflect(lookup, (Method)this.target);
         return Modifier.isStatic(((Member)this.target).getModifiers()) ? StaticClassIntrospector.editStaticMethodHandle(mh) : mh;
      } else {
         return StaticClassIntrospector.editConstructorMethodHandle(jdk.internal.dynalink.support.Lookup.unreflectConstructor(lookup, (Constructor)this.target));
      }
   }

   boolean isConstructor() {
      return this.target instanceof Constructor;
   }
}
