package jdk.nashorn.internal.objects;

import java.lang.invoke.MethodHandle;
import java.util.ArrayList;
import java.util.Collection;
import jdk.nashorn.internal.runtime.AccessorProperty;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.PrototypeObject;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.Specialization;

final class NativeArray$Constructor extends ScriptFunction {
   private Object isArray = ScriptFunction.createBuiltin("isArray", "isArray");
   private static final PropertyMap $nasgenmap$;

   public Object G$isArray() {
      return this.isArray;
   }

   public void S$isArray(Object var1) {
      this.isArray = var1;
   }

   static {
      ArrayList var10000 = new ArrayList(1);
      var10000.add(AccessorProperty.create("isArray", 2, "G$isArray", "S$isArray"));
      $nasgenmap$ = PropertyMap.newMap((Collection)var10000);
   }

   NativeArray$Constructor() {
      super((String)"Array", (MethodHandle)"construct", (PropertyMap)$nasgenmap$, (Specialization[])(new Specialization[]{new Specialization("construct", false), new Specialization("construct", false), new Specialization("construct", false), new Specialization("construct", false), new Specialization("construct", false)}));
      NativeArray$Prototype var10001 = new NativeArray$Prototype();
      PrototypeObject.setConstructor(var10001, this);
      this.setPrototype(var10001);
      this.setArity(1);
   }
}
