package jdk.nashorn.internal.objects;

import java.util.ArrayList;
import java.util.Collection;
import jdk.nashorn.internal.runtime.AccessorProperty;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.PrototypeObject;
import jdk.nashorn.internal.runtime.ScriptFunction;

final class NativeJavaImporter$Prototype extends PrototypeObject {
   private Object __noSuchProperty__ = ScriptFunction.createBuiltin("__noSuchProperty__", "__noSuchProperty__");
   private Object __noSuchMethod__ = ScriptFunction.createBuiltin("__noSuchMethod__", "__noSuchMethod__");
   private static final PropertyMap $nasgenmap$;

   public Object G$__noSuchProperty__() {
      return this.__noSuchProperty__;
   }

   public void S$__noSuchProperty__(Object var1) {
      this.__noSuchProperty__ = var1;
   }

   public Object G$__noSuchMethod__() {
      return this.__noSuchMethod__;
   }

   public void S$__noSuchMethod__(Object var1) {
      this.__noSuchMethod__ = var1;
   }

   static {
      ArrayList var10000 = new ArrayList(2);
      var10000.add(AccessorProperty.create("__noSuchProperty__", 2, "G$__noSuchProperty__", "S$__noSuchProperty__"));
      var10000.add(AccessorProperty.create("__noSuchMethod__", 2, "G$__noSuchMethod__", "S$__noSuchMethod__"));
      $nasgenmap$ = PropertyMap.newMap((Collection)var10000);
   }

   NativeJavaImporter$Prototype() {
      super($nasgenmap$);
   }

   public String getClassName() {
      return "JavaImporter";
   }
}
