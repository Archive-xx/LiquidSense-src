package jdk.nashorn.internal.objects;

import jdk.nashorn.internal.runtime.PrototypeObject;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.Specialization;

final class NativeSyntaxError$Constructor extends ScriptFunction {
   NativeSyntaxError$Constructor() {
      super("SyntaxError", "constructor", (Specialization[])null);
      NativeSyntaxError$Prototype var10001 = new NativeSyntaxError$Prototype();
      PrototypeObject.setConstructor(var10001, this);
      this.setPrototype(var10001);
   }
}
