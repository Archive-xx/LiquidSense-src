package jdk.nashorn.internal.objects;

import jdk.nashorn.internal.runtime.PrototypeObject;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.Specialization;

final class NativeDataView$Constructor extends ScriptFunction {
   NativeDataView$Constructor() {
      super("DataView", "constructor", new Specialization[]{new Specialization("constructor", false), new Specialization("constructor", false)});
      NativeDataView$Prototype var10001 = new NativeDataView$Prototype();
      PrototypeObject.setConstructor(var10001, this);
      this.setPrototype(var10001);
      this.setArity(1);
   }
}
