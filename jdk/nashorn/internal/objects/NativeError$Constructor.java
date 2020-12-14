package jdk.nashorn.internal.objects;

import java.lang.invoke.MethodHandle;
import java.util.ArrayList;
import java.util.Collection;
import jdk.nashorn.internal.runtime.AccessorProperty;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.PrototypeObject;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.Specialization;

final class NativeError$Constructor extends ScriptFunction {
   private Object captureStackTrace = ScriptFunction.createBuiltin("captureStackTrace", "captureStackTrace");
   private Object dumpStack = ScriptFunction.createBuiltin("dumpStack", "dumpStack");
   private static final PropertyMap $nasgenmap$;

   public Object G$captureStackTrace() {
      return this.captureStackTrace;
   }

   public void S$captureStackTrace(Object var1) {
      this.captureStackTrace = var1;
   }

   public Object G$dumpStack() {
      return this.dumpStack;
   }

   public void S$dumpStack(Object var1) {
      this.dumpStack = var1;
   }

   static {
      ArrayList var10000 = new ArrayList(2);
      var10000.add(AccessorProperty.create("captureStackTrace", 2, "G$captureStackTrace", "S$captureStackTrace"));
      var10000.add(AccessorProperty.create("dumpStack", 2, "G$dumpStack", "S$dumpStack"));
      $nasgenmap$ = PropertyMap.newMap((Collection)var10000);
   }

   NativeError$Constructor() {
      super((String)"Error", (MethodHandle)"constructor", (PropertyMap)$nasgenmap$, (Specialization[])null);
      NativeError$Prototype var10001 = new NativeError$Prototype();
      PrototypeObject.setConstructor(var10001, this);
      this.setPrototype(var10001);
   }
}
