package jdk.nashorn.internal.objects;

import java.util.ArrayList;
import java.util.Collection;
import jdk.nashorn.internal.runtime.AccessorProperty;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.PrototypeObject;
import jdk.nashorn.internal.runtime.ScriptFunction;

final class NativeBoolean$Prototype extends PrototypeObject {
   private Object toString = ScriptFunction.createBuiltin("toString", "toString");
   private Object valueOf = ScriptFunction.createBuiltin("valueOf", "valueOf");
   private static final PropertyMap $nasgenmap$;

   public Object G$toString() {
      return this.toString;
   }

   public void S$toString(Object var1) {
      this.toString = var1;
   }

   public Object G$valueOf() {
      return this.valueOf;
   }

   public void S$valueOf(Object var1) {
      this.valueOf = var1;
   }

   static {
      ArrayList var10000 = new ArrayList(2);
      var10000.add(AccessorProperty.create("toString", 2, "G$toString", "S$toString"));
      var10000.add(AccessorProperty.create("valueOf", 2, "G$valueOf", "S$valueOf"));
      $nasgenmap$ = PropertyMap.newMap((Collection)var10000);
   }

   NativeBoolean$Prototype() {
      super($nasgenmap$);
   }

   public String getClassName() {
      return "Boolean";
   }
}
