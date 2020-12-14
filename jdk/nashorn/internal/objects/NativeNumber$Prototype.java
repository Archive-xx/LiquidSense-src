package jdk.nashorn.internal.objects;

import java.util.ArrayList;
import java.util.Collection;
import jdk.nashorn.internal.runtime.AccessorProperty;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.PrototypeObject;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.Specialization;

final class NativeNumber$Prototype extends PrototypeObject {
   private Object toFixed = ScriptFunction.createBuiltin("toFixed", "toFixed", new Specialization[]{new Specialization("toFixed", false)});
   private Object toExponential = ScriptFunction.createBuiltin("toExponential", "toExponential");
   private Object toPrecision = ScriptFunction.createBuiltin("toPrecision", "toPrecision", new Specialization[]{new Specialization("toPrecision", false)});
   private Object toString = ScriptFunction.createBuiltin("toString", "toString");
   private Object toLocaleString = ScriptFunction.createBuiltin("toLocaleString", "toLocaleString");
   private Object valueOf = ScriptFunction.createBuiltin("valueOf", "valueOf");
   private static final PropertyMap $nasgenmap$;

   public Object G$toFixed() {
      return this.toFixed;
   }

   public void S$toFixed(Object var1) {
      this.toFixed = var1;
   }

   public Object G$toExponential() {
      return this.toExponential;
   }

   public void S$toExponential(Object var1) {
      this.toExponential = var1;
   }

   public Object G$toPrecision() {
      return this.toPrecision;
   }

   public void S$toPrecision(Object var1) {
      this.toPrecision = var1;
   }

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

   static {
      ArrayList var10000 = new ArrayList(6);
      var10000.add(AccessorProperty.create("toFixed", 2, "G$toFixed", "S$toFixed"));
      var10000.add(AccessorProperty.create("toExponential", 2, "G$toExponential", "S$toExponential"));
      var10000.add(AccessorProperty.create("toPrecision", 2, "G$toPrecision", "S$toPrecision"));
      var10000.add(AccessorProperty.create("toString", 2, "G$toString", "S$toString"));
      var10000.add(AccessorProperty.create("toLocaleString", 2, "G$toLocaleString", "S$toLocaleString"));
      var10000.add(AccessorProperty.create("valueOf", 2, "G$valueOf", "S$valueOf"));
      $nasgenmap$ = PropertyMap.newMap((Collection)var10000);
   }

   NativeNumber$Prototype() {
      super($nasgenmap$);
   }

   public String getClassName() {
      return "Number";
   }
}
