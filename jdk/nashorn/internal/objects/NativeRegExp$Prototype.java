package jdk.nashorn.internal.objects;

import java.util.ArrayList;
import java.util.Collection;
import jdk.nashorn.internal.runtime.AccessorProperty;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.PrototypeObject;
import jdk.nashorn.internal.runtime.ScriptFunction;

final class NativeRegExp$Prototype extends PrototypeObject {
   private Object compile = ScriptFunction.createBuiltin("compile", "compile");
   private Object exec = ScriptFunction.createBuiltin("exec", "exec");
   private Object test = ScriptFunction.createBuiltin("test", "test");
   private Object toString = ScriptFunction.createBuiltin("toString", "toString");
   private static final PropertyMap $nasgenmap$;

   public Object G$compile() {
      return this.compile;
   }

   public void S$compile(Object var1) {
      this.compile = var1;
   }

   public Object G$exec() {
      return this.exec;
   }

   public void S$exec(Object var1) {
      this.exec = var1;
   }

   public Object G$test() {
      return this.test;
   }

   public void S$test(Object var1) {
      this.test = var1;
   }

   public Object G$toString() {
      return this.toString;
   }

   public void S$toString(Object var1) {
      this.toString = var1;
   }

   static {
      ArrayList var10000 = new ArrayList(4);
      var10000.add(AccessorProperty.create("compile", 2, "G$compile", "S$compile"));
      var10000.add(AccessorProperty.create("exec", 2, "G$exec", "S$exec"));
      var10000.add(AccessorProperty.create("test", 2, "G$test", "S$test"));
      var10000.add(AccessorProperty.create("toString", 2, "G$toString", "S$toString"));
      $nasgenmap$ = PropertyMap.newMap((Collection)var10000);
   }

   NativeRegExp$Prototype() {
      super($nasgenmap$);
   }

   public String getClassName() {
      return "RegExp";
   }
}
