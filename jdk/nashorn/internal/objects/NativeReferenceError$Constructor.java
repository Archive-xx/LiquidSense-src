package jdk.nashorn.internal.objects;

import jdk.nashorn.internal.runtime.PrototypeObject;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.Specialization;

final class NativeReferenceError$Constructor extends ScriptFunction {
   NativeReferenceError$Constructor() {
      super("ReferenceError", "constructor", (Specialization[])null);
      NativeReferenceError$Prototype var10001 = new NativeReferenceError$Prototype();
      PrototypeObject.setConstructor(var10001, this);
      this.setPrototype(var10001);
   }
}
