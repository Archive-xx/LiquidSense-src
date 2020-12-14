package jdk.nashorn.internal.objects;

import java.util.ArrayList;
import java.util.Collection;
import jdk.nashorn.internal.runtime.AccessorProperty;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.PrototypeObject;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.Specialization;

final class NativeArrayBuffer$Prototype extends PrototypeObject {
   private Object slice = ScriptFunction.createBuiltin("slice", "slice", new Specialization[]{new Specialization("slice", false), new Specialization("slice", false)});
   private static final PropertyMap $nasgenmap$;

   public Object G$slice() {
      return this.slice;
   }

   public void S$slice(Object var1) {
      this.slice = var1;
   }

   static {
      ArrayList var10000 = new ArrayList(1);
      var10000.add(AccessorProperty.create("slice", 2, "G$slice", "S$slice"));
      $nasgenmap$ = PropertyMap.newMap((Collection)var10000);
   }

   NativeArrayBuffer$Prototype() {
      super($nasgenmap$);
   }

   public String getClassName() {
      return "ArrayBuffer";
   }
}
