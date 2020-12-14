package jdk.internal.dynalink.beans;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles.Lookup;

class SimpleDynamicMethod extends SingleDynamicMethod {
   private final MethodHandle target;
   private final boolean constructor;

   SimpleDynamicMethod(MethodHandle target, Class<?> clazz, String name) {
      this(target, clazz, name, false);
   }

   SimpleDynamicMethod(MethodHandle target, Class<?> clazz, String name, boolean constructor) {
      super(getName(target, clazz, name, constructor));
      this.target = target;
      this.constructor = constructor;
   }

   private static String getName(MethodHandle target, Class<?> clazz, String name, boolean constructor) {
      return getMethodNameWithSignature(target.type(), constructor ? name : getClassAndMethodName(clazz, name), !constructor);
   }

   boolean isVarArgs() {
      return this.target.isVarargsCollector();
   }

   MethodType getMethodType() {
      return this.target.type();
   }

   MethodHandle getTarget(Lookup lookup) {
      return this.target;
   }

   boolean isConstructor() {
      return this.constructor;
   }
}
