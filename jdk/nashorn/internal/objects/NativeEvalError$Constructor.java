package jdk.nashorn.internal.objects;

import jdk.nashorn.internal.runtime.PrototypeObject;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.Specialization;

final class NativeEvalError$Constructor extends ScriptFunction {
   NativeEvalError$Constructor() {
      super("EvalError", "constructor", (Specialization[])null);
      NativeEvalError$Prototype var10001 = new NativeEvalError$Prototype();
      PrototypeObject.setConstructor(var10001, this);
      this.setPrototype(var10001);
   }
}
