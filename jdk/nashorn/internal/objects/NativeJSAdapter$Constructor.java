package jdk.nashorn.internal.objects;

import jdk.nashorn.internal.runtime.PrototypeObject;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.Specialization;

final class NativeJSAdapter$Constructor extends ScriptFunction {
   NativeJSAdapter$Constructor() {
      super("JSAdapter", "construct", (Specialization[])null);
      NativeJSAdapter$Prototype var10001 = new NativeJSAdapter$Prototype();
      PrototypeObject.setConstructor(var10001, this);
      this.setPrototype(var10001);
   }
}
