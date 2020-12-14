package jdk.nashorn.internal.objects;

import java.util.ArrayList;
import java.util.Collection;
import jdk.nashorn.internal.runtime.AccessorProperty;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.PrototypeObject;
import jdk.nashorn.internal.runtime.ScriptFunction;

final class NativeObject$Prototype extends PrototypeObject {
   private Object toString = ScriptFunction.createBuiltin("toString", "toString");
   private Object toLocaleString = ScriptFunction.createBuiltin("toLocaleString", "toLocaleString");
   private Object valueOf = ScriptFunction.createBuiltin("valueOf", "valueOf");
   private Object hasOwnProperty = ScriptFunction.createBuiltin("hasOwnProperty", "hasOwnProperty");
   private Object isPrototypeOf = ScriptFunction.createBuiltin("isPrototypeOf", "isPrototypeOf");
   private Object propertyIsEnumerable = ScriptFunction.createBuiltin("propertyIsEnumerable", "propertyIsEnumerable");
   private static final PropertyMap $nasgenmap$;

   public Object G$toString() {
      return this.toString;
   }

   public void S$toString(Object var1) {
      this.toString = var1;
   }

   public Object G$toLocaleString() {
      return this.toLocaleString;
   }

   public void S$toLocaleString(Object var1) {
      this.toLocaleString = var1;
   }

   public Object G$valueOf() {
      return this.valueOf;
   }

   public void S$valueOf(Object var1) {
      this.valueOf = var1;
   }

   public Object G$hasOwnProperty() {
      return this.hasOwnProperty;
   }

   public void S$hasOwnProperty(Object var1) {
      this.hasOwnProperty = var1;
   }

   public Object G$isPrototypeOf() {
      return this.isPrototypeOf;
   }

   public void S$isPrototypeOf(Object var1) {
      this.isPrototypeOf = var1;
   }

   public Object G$propertyIsEnumerable() {
      return this.propertyIsEnumerable;
   }

   public void S$propertyIsEnumerable(Object var1) {
      this.propertyIsEnumerable = var1;
   }

   static {
      ArrayList var10000 = new ArrayList(6);
      var10000.add(AccessorProperty.create("toString", 2, "G$toString", "S$toString"));
      var10000.add(AccessorProperty.create("toLocaleString", 2, "G$toLocaleString", "S$toLocaleString"));
      var10000.add(AccessorProperty.create("valueOf", 2, "G$valueOf", "S$valueOf"));
      var10000.add(AccessorProperty.create("hasOwnProperty", 2, "G$hasOwnProperty", "S$hasOwnProperty"));
      var10000.add(AccessorProperty.create("isPrototypeOf", 2, "G$isPrototypeOf", "S$isPrototypeOf"));
      var10000.add(AccessorProperty.create("propertyIsEnumerable", 2, "G$propertyIsEnumerable", "S$propertyIsEnumerable"));
      $nasgenmap$ = PropertyMap.newMap((Collection)var10000);
   }

   NativeObject$Prototype() {
      super($nasgenmap$);
   }

   public String getClassName() {
      return "Object";
   }
}
