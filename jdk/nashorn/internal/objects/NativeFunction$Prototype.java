package jdk.nashorn.internal.objects;

import java.util.ArrayList;
import java.util.Collection;
import jdk.nashorn.internal.runtime.AccessorProperty;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.PrototypeObject;
import jdk.nashorn.internal.runtime.ScriptFunction;

final class NativeFunction$Prototype extends PrototypeObject {
   private Object toString = ScriptFunction.createBuiltin("toString", "toString");
   private Object apply = ScriptFunction.createBuiltin("apply", "apply");
   private Object call;
   private Object bind;
   private Object toSource;
   private static final PropertyMap $nasgenmap$;

   public Object G$toString() {
      return this.toString;
   }

   public void S$toString(Object var1) {
      this.toString = var1;
   }

   public Object G$apply() {
      return this.apply;
   }

   public void S$apply(Object var1) {
      this.apply = var1;
   }

   public Object G$call() {
      return this.call;
   }

   public void S$call(Object var1) {
      this.call = var1;
   }

   public Object G$bind() {
      return this.bind;
   }

   public void S$bind(Object var1) {
      this.bind = var1;
   }

   public Object G$toSource() {
      return this.toSource;
   }

   public void S$toSource(Object var1) {
      this.toSource = var1;
   }

   static {
      ArrayList var10000 = new ArrayList(5);
      var10000.add(AccessorProperty.create("toString", 2, "G$toString", "S$toString"));
      var10000.add(AccessorProperty.create("apply", 2, "G$apply", "S$apply"));
      var10000.add(AccessorProperty.create("call", 2, "G$call", "S$call"));
      var10000.add(AccessorProperty.create("bind", 2, "G$bind", "S$bind"));
      var10000.add(AccessorProperty.create("toSource", 2, "G$toSource", "S$toSource"));
      $nasgenmap$ = PropertyMap.newMap((Collection)var10000);
   }

   NativeFunction$Prototype() {
      super($nasgenmap$);
      ScriptFunction var10001 = ScriptFunction.createBuiltin("call", "call");
      var10001.setArity(1);
      this.call = var10001;
      var10001 = ScriptFunction.createBuiltin("bind", "bind");
      var10001.setArity(1);
      this.bind = var10001;
      this.toSource = ScriptFunction.createBuiltin("toSource", "toSource");
   }

   public String getClassName() {
      return "Function";
   }
}
