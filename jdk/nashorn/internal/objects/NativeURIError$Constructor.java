package jdk.nashorn.internal.objects;

import jdk.nashorn.internal.runtime.PrototypeObject;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.Specialization;

final class NativeURIError$Constructor extends ScriptFunction {
   NativeURIError$Constructor() {
      super("URIError", "constructor", (Specialization[])null);
      NativeURIError$Prototype var10001 = new NativeURIError$Prototype();
      PrototypeObject.setConstructor(var10001, this);
      this.setPrototype(var10001);
   }
}
