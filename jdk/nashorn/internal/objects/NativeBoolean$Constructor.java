package jdk.nashorn.internal.objects;

import jdk.nashorn.internal.runtime.PrototypeObject;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.Specialization;

final class NativeBoolean$Constructor extends ScriptFunction {
   NativeBoolean$Constructor() {
      super("Boolean", "constructor", (Specialization[])null);
      NativeBoolean$Prototype var10001 = new NativeBoolean$Prototype();
      PrototypeObject.setConstructor(var10001, this);
      this.setPrototype(var10001);
      this.setArity(1);
   }
}
