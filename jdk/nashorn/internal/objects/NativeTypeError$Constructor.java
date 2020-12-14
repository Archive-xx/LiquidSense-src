package jdk.nashorn.internal.objects;

import jdk.nashorn.internal.runtime.PrototypeObject;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.Specialization;

final class NativeTypeError$Constructor extends ScriptFunction {
   NativeTypeError$Constructor() {
      super("TypeError", "constructor", (Specialization[])null);
      NativeTypeError$Prototype var10001 = new NativeTypeError$Prototype();
      PrototypeObject.setConstructor(var10001, this);
      this.setPrototype(var10001);
   }
}
