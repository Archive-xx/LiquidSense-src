package jdk.nashorn.internal.objects;

import java.lang.invoke.MethodHandle;
import java.util.ArrayList;
import java.util.Collection;
import jdk.nashorn.internal.runtime.AccessorProperty;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.PrototypeObject;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.Specialization;

final class NativeDate$Constructor extends ScriptFunction {
   private Object parse = ScriptFunction.createBuiltin("parse", "parse");
   private Object UTC;
   private Object now;
   private static final PropertyMap $nasgenmap$;

   public Object G$parse() {
      return this.parse;
   }

   public void S$parse(Object var1) {
      this.parse = var1;
   }

   public Object G$UTC() {
      return this.UTC;
   }

   public void S$UTC(Object var1) {
      this.UTC = var1;
   }

   public Object G$now() {
      return this.now;
   }

   public void S$now(Object var1) {
      this.now = var1;
   }

   static {
      ArrayList var10000 = new ArrayList(3);
      var10000.add(AccessorProperty.create("parse", 2, "G$parse", "S$parse"));
      var10000.add(AccessorProperty.create("UTC", 2, "G$UTC", "S$UTC"));
      var10000.add(AccessorProperty.create("now", 2, "G$now", "S$now"));
      $nasgenmap$ = PropertyMap.newMap((Collection)var10000);
   }

   NativeDate$Constructor() {
      super((String)"Date", (MethodHandle)"construct", (PropertyMap)$nasgenmap$, (Specialization[])(new Specialization[]{new Specialization("construct", false)}));
      ScriptFunction var10001 = ScriptFunction.createBuiltin("UTC", "UTC");
      var10001.setArity(7);
      this.UTC = var10001;
      this.now = ScriptFunction.createBuiltin("now", "now");
      NativeDate$Prototype var1 = new NativeDate$Prototype();
      PrototypeObject.setConstructor(var1, this);
      this.setPrototype(var1);
      this.setArity(7);
   }
}
