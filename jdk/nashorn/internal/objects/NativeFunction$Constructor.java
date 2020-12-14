package jdk.nashorn.internal.objects;

import jdk.nashorn.internal.runtime.PrototypeObject;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.Specialization;

final class NativeFunction$Constructor extends ScriptFunction {
   NativeFunction$Constructor() {
      super("Function", "function", (Specialization[])null);
      NativeFunction$Prototype var10001 = new NativeFunction$Prototype();
      PrototypeObject.setConstructor(var10001, this);
      this.setPrototype(var10001);
      this.setArity(1);
   }
}
