package jdk.internal.dynalink.beans;

import java.lang.invoke.MethodHandle;
import java.util.Collections;
import java.util.Map;

class BeanIntrospector extends FacetIntrospector {
   BeanIntrospector(Class<?> clazz) {
      super(clazz, true);
   }

   Map<String, MethodHandle> getInnerClassGetters() {
      return Collections.emptyMap();
   }

   MethodHandle editMethodHandle(MethodHandle mh) {
      return mh;
   }
}
