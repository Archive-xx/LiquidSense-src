package jdk.nashorn.internal.objects;

import java.util.ArrayList;
import java.util.Collection;
import jdk.nashorn.internal.runtime.AccessorProperty;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.PrototypeObject;
import jdk.nashorn.internal.runtime.ScriptFunction;

final class NativeError$Prototype extends PrototypeObject {
   private Object name;
   private Object message;
   private Object printStackTrace = ScriptFunction.createBuiltin("printStackTrace", "printStackTrace");
   private Object getStackTrace = ScriptFunction.createBuiltin("getStackTrace", "getStackTrace");
   private Object toString = ScriptFunction.createBuiltin("toString", "toString");
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

   public Object G$printStackTrace() {
      return this.printStackTrace;
   }

   public void S$printStackTrace(Object var1) {
      this.printStackTrace = var1;
   }

   public Object G$getStackTrace() {
      return this.getStackTrace;
   }

   public void S$getStackTrace(Object var1) {
      this.getStackTrace = var1;
   }

   public Object G$toString() {
      return this.toString;
   }

   public void S$toString(Object var1) {
      this.toString = var1;
   }

   static {
      ArrayList var10000 = new ArrayList(5);
      var10000.add(AccessorProperty.create("name", 2, "G$name", "S$name"));
      var10000.add(AccessorProperty.create("message", 2, "G$message", "S$message"));
      var10000.add(AccessorProperty.create("printStackTrace", 2, "G$printStackTrace", "S$printStackTrace"));
      var10000.add(AccessorProperty.create("getStackTrace", 2, "G$getStackTrace", "S$getStackTrace"));
      var10000.add(AccessorProperty.create("toString", 2, "G$toString", "S$toString"));
      $nasgenmap$ = PropertyMap.newMap((Collection)var10000);
   }

   NativeError$Prototype() {
      super($nasgenmap$);
   }

   public String getClassName() {
      return "Error";
   }
}
