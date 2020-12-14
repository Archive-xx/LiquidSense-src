package jdk.nashorn.internal.objects;

import java.lang.invoke.MethodHandle;
import java.util.ArrayList;
import java.util.Collection;
import jdk.nashorn.internal.runtime.AccessorProperty;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.PrototypeObject;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.Specialization;

final class NativeArrayBuffer$Constructor extends ScriptFunction {
   private Object isView = ScriptFunction.createBuiltin("isView", "isView");
   private static final PropertyMap $nasgenmap$;

   public Object G$isView() {
      return this.isView;
   }

   public void S$isView(Object var1) {
      this.isView = var1;
   }

   static {
      ArrayList var10000 = new ArrayList(1);
      var10000.add(AccessorProperty.create("isView", 2, "G$isView", "S$isView"));
      $nasgenmap$ = PropertyMap.newMap((Collection)var10000);
   }

   NativeArrayBuffer$Constructor() {
      super((String)"ArrayBuffer", (MethodHandle)"constructor", (PropertyMap)$nasgenmap$, (Specialization[])null);
      NativeArrayBuffer$Prototype var10001 = new NativeArrayBuffer$Prototype();
      PrototypeObject.setConstructor(var10001, this);
      this.setPrototype(var10001);
      this.setArity(1);
   }
}
