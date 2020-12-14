package jdk.nashorn.internal.objects;

import java.lang.invoke.MethodHandle;
import java.util.ArrayList;
import java.util.Collection;
import jdk.nashorn.internal.runtime.AccessorProperty;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.PrototypeObject;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.Specialization;

final class NativeString$Constructor extends ScriptFunction {
   private Object fromCharCode;
   private static final PropertyMap $nasgenmap$;

   public Object G$fromCharCode() {
      return this.fromCharCode;
   }

   public void S$fromCharCode(Object var1) {
      this.fromCharCode = var1;
   }

   static {
      ArrayList var10000 = new ArrayList(1);
      var10000.add(AccessorProperty.create("fromCharCode", 2, "G$fromCharCode", "S$fromCharCode"));
      $nasgenmap$ = PropertyMap.newMap((Collection)var10000);
   }

   NativeString$Constructor() {
      super((String)"String", (MethodHandle)"constructor", (PropertyMap)$nasgenmap$, (Specialization[])(new Specialization[]{new Specialization("constructor", false), new Specialization("constructor", false), new Specialization("constructor", false), new Specialization("constructor", false), new Specialization("constructor", false), new Specialization("constructor", false)}));
      ScriptFunction var10001 = ScriptFunction.createBuiltin("fromCharCode", "fromCharCode", new Specialization[]{new Specialization("fromCharCode", false), new Specialization("fromCharCode", false), new Specialization("fromCharCode", false), new Specialization("fromCharCode", false), new Specialization("fromCharCode", false), new Specialization("fromCharCode", false)});
      var10001.setArity(1);
      this.fromCharCode = var10001;
      NativeString$Prototype var1 = new NativeString$Prototype();
      PrototypeObject.setConstructor(var1, this);
      this.setPrototype(var1);
      this.setArity(1);
   }
}
