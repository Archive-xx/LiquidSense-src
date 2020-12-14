package jdk.nashorn.internal.objects;

import jdk.nashorn.internal.runtime.PrototypeObject;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.Specialization;

final class NativeRangeError$Constructor extends ScriptFunction {
   NativeRangeError$Constructor() {
      super("RangeError", "constructor", (Specialization[])null);
      NativeRangeError$Prototype var10001 = new NativeRangeError$Prototype();
      PrototypeObject.setConstructor(var10001, this);
      this.setPrototype(var10001);
   }
}
