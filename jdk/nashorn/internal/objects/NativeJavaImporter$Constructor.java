package jdk.nashorn.internal.objects;

import jdk.nashorn.internal.runtime.PrototypeObject;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.Specialization;

final class NativeJavaImporter$Constructor extends ScriptFunction {
   NativeJavaImporter$Constructor() {
      super("JavaImporter", "constructor", (Specialization[])null);
      NativeJavaImporter$Prototype var10001 = new NativeJavaImporter$Prototype();
      PrototypeObject.setConstructor(var10001, this);
      this.setPrototype(var10001);
      this.setArity(1);
   }
}
