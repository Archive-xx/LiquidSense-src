package jdk.nashorn.internal.objects;

import java.util.ArrayList;
import java.util.Collection;
import jdk.nashorn.internal.runtime.AccessorProperty;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.PrototypeObject;
import jdk.nashorn.internal.runtime.ScriptFunction;

final class NativeFloat64Array$Prototype extends PrototypeObject {
   private Object set = ScriptFunction.createBuiltin("set", "set");
   private Object subarray = ScriptFunction.createBuiltin("subarray", "subarray");
   private static final PropertyMap $nasgenmap$;

   public Object G$set() {
      return this.set;
   }

   public void S$set(Object var1) {
      this.set = var1;
   }

   public Object G$subarray() {
      return this.subarray;
   }

   public void S$subarray(Object var1) {
      this.subarray = var1;
   }

   static {
      ArrayList var10000 = new ArrayList(2);
      var10000.add(AccessorProperty.create("set", 2, "G$set", "S$set"));
      var10000.add(AccessorProperty.create("subarray", 2, "G$subarray", "S$subarray"));
      $nasgenmap$ = PropertyMap.newMap((Collection)var10000);
   }

   NativeFloat64Array$Prototype() {
      super($nasgenmap$);
   }

   public String getClassName() {
      return "Float64Array";
   }
}
