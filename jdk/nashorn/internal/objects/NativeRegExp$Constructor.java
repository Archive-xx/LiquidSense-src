package jdk.nashorn.internal.objects;

import java.lang.invoke.MethodHandle;
import java.util.ArrayList;
import java.util.Collection;
import jdk.nashorn.internal.runtime.AccessorProperty;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.PrototypeObject;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.Specialization;

final class NativeRegExp$Constructor extends ScriptFunction {
   private static final PropertyMap $nasgenmap$;

   static {
      ArrayList var10000 = new ArrayList(15);
      var10000.add(AccessorProperty.create("input", 5, "getLastInput", (MethodHandle)null));
      var10000.add(AccessorProperty.create("multiline", 5, "getLastMultiline", (MethodHandle)null));
      var10000.add(AccessorProperty.create("lastMatch", 5, "getLastMatch", (MethodHandle)null));
      var10000.add(AccessorProperty.create("lastParen", 5, "getLastParen", (MethodHandle)null));
      var10000.add(AccessorProperty.create("leftContext", 5, "getLeftContext", (MethodHandle)null));
      var10000.add(AccessorProperty.create("rightContext", 5, "getRightContext", (MethodHandle)null));
      var10000.add(AccessorProperty.create("$1", 5, "getGroup1", (MethodHandle)null));
      var10000.add(AccessorProperty.create("$2", 5, "getGroup2", (MethodHandle)null));
      var10000.add(AccessorProperty.create("$3", 5, "getGroup3", (MethodHandle)null));
      var10000.add(AccessorProperty.create("$4", 5, "getGroup4", (MethodHandle)null));
      var10000.add(AccessorProperty.create("$5", 5, "getGroup5", (MethodHandle)null));
      var10000.add(AccessorProperty.create("$6", 5, "getGroup6", (MethodHandle)null));
      var10000.add(AccessorProperty.create("$7", 5, "getGroup7", (MethodHandle)null));
      var10000.add(AccessorProperty.create("$8", 5, "getGroup8", (MethodHandle)null));
      var10000.add(AccessorProperty.create("$9", 5, "getGroup9", (MethodHandle)null));
      $nasgenmap$ = PropertyMap.newMap((Collection)var10000);
   }

   NativeRegExp$Constructor() {
      super((String)"RegExp", (MethodHandle)"constructor", (PropertyMap)$nasgenmap$, (Specialization[])(new Specialization[]{new Specialization("constructor", false), new Specialization("constructor", false), new Specialization("constructor", false)}));
      NativeRegExp$Prototype var10001 = new NativeRegExp$Prototype();
      PrototypeObject.setConstructor(var10001, this);
      this.setPrototype(var10001);
      this.setArity(2);
   }
}
