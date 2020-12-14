package jdk.internal.dynalink.beans;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.HashMap;
import java.util.Map;

class StaticClassIntrospector extends FacetIntrospector {
   StaticClassIntrospector(Class<?> clazz) {
      super(clazz, false);
   }

   Map<String, MethodHandle> getInnerClassGetters() {
      Map<String, MethodHandle> map = new HashMap();
      Class[] var2 = this.membersLookup.getInnerClasses();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Class<?> innerClass = var2[var4];
         map.put(innerClass.getSimpleName(), this.editMethodHandle(MethodHandles.constant(StaticClass.class, StaticClass.forClass(innerClass))));
      }

      return map;
   }

   MethodHandle editMethodHandle(MethodHandle mh) {
      return editStaticMethodHandle(mh);
   }

   static MethodHandle editStaticMethodHandle(MethodHandle mh) {
      return dropReceiver(mh, Object.class);
   }

   static MethodHandle editConstructorMethodHandle(MethodHandle cmh) {
      return dropReceiver(cmh, StaticClass.class);
   }

   private static MethodHandle dropReceiver(MethodHandle mh, Class<?> receiverClass) {
      MethodHandle newHandle = MethodHandles.dropArguments(mh, 0, new Class[]{receiverClass});
      if (mh.isVarargsCollector() && !newHandle.isVarargsCollector()) {
         MethodType type = mh.type();
         newHandle = newHandle.asVarargsCollector(type.parameterType(type.parameterCount() - 1));
      }

      return newHandle;
   }
}
