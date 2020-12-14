package jdk.nashorn.internal.objects;

import java.util.ArrayList;
import java.util.Collection;
import jdk.nashorn.internal.runtime.AccessorProperty;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.PrototypeObject;

final class NativeTypeError$Prototype extends PrototypeObject {
   private Object name;
   private Object message;
   private static final PropertyMap $nasgenmap$;

   public Object G$name() {
      return this.name;
   }

   public void S$name(Object var1) {
      this.name = var1;
   }

   public Object G$message() {
      return this.message;
   }

   public void S$message(Object var1) {
      this.message = var1;
   }

   static {
      ArrayList var10000 = new ArrayList(2);
      var10000.add(AccessorProperty.create("name", 2, "G$name", "S$name"));
      var10000.add(AccessorProperty.create("message", 2, "G$message", "S$message"));
      $nasgenmap$ = PropertyMap.newMap((Collection)var10000);
   }

   NativeTypeError$Prototype() {
      super($nasgenmap$);
   }

   public String getClassName() {
      return "Error";
   }
}
