package jdk.nashorn.internal.objects;

import java.lang.invoke.MethodHandle;
import java.util.ArrayList;
import java.util.Collection;
import jdk.nashorn.internal.runtime.AccessorProperty;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.PrototypeObject;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.Specialization;

final class NativeUint16Array$Constructor extends ScriptFunction {
   private static final PropertyMap $nasgenmap$;

   public int G$BYTES_PER_ELEMENT() {
      return NativeUint16Array.BYTES_PER_ELEMENT;
   }

   static {
      ArrayList var10000 = new ArrayList(1);
      var10000.add(AccessorProperty.create("BYTES_PER_ELEMENT", 7, "G$BYTES_PER_ELEMENT", (MethodHandle)null));
      $nasgenmap$ = PropertyMap.newMap((Collection)var10000);
   }

   NativeUint16Array$Constructor() {
      super((String)"Uint16Array", (MethodHandle)"constructor", (PropertyMap)$nasgenmap$, (Specialization[])null);
      NativeUint16Array$Prototype var10001 = new NativeUint16Array$Prototype();
      PrototypeObject.setConstructor(var10001, this);
      this.setPrototype(var10001);
      this.setArity(1);
   }
}
