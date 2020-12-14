package jdk.nashorn.internal.objects;

import java.lang.invoke.MethodHandle;
import java.util.ArrayList;
import java.util.Collection;
import jdk.nashorn.internal.runtime.AccessorProperty;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.PrototypeObject;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.Specialization;

final class NativeNumber$Constructor extends ScriptFunction {
   private static final PropertyMap $nasgenmap$;

   public double G$MAX_VALUE() {
      return NativeNumber.MAX_VALUE;
   }

   public double G$MIN_VALUE() {
      return NativeNumber.MIN_VALUE;
   }

   public double G$NaN() {
      return NativeNumber.NaN;
   }

   public double G$NEGATIVE_INFINITY() {
      return NativeNumber.NEGATIVE_INFINITY;
   }

   public double G$POSITIVE_INFINITY() {
      return NativeNumber.POSITIVE_INFINITY;
   }

   static {
      ArrayList var10000 = new ArrayList(5);
      var10000.add(AccessorProperty.create("MAX_VALUE", 7, "G$MAX_VALUE", (MethodHandle)null));
      var10000.add(AccessorProperty.create("MIN_VALUE", 7, "G$MIN_VALUE", (MethodHandle)null));
      var10000.add(AccessorProperty.create("NaN", 7, "G$NaN", (MethodHandle)null));
      var10000.add(AccessorProperty.create("NEGATIVE_INFINITY", 7, "G$NEGATIVE_INFINITY", (MethodHandle)null));
      var10000.add(AccessorProperty.create("POSITIVE_INFINITY", 7, "G$POSITIVE_INFINITY", (MethodHandle)null));
      $nasgenmap$ = PropertyMap.newMap((Collection)var10000);
   }

   NativeNumber$Constructor() {
      super((String)"Number", (MethodHandle)"constructor", (PropertyMap)$nasgenmap$, (Specialization[])null);
      NativeNumber$Prototype var10001 = new NativeNumber$Prototype();
      PrototypeObject.setConstructor(var10001, this);
      this.setPrototype(var10001);
      this.setArity(1);
   }
}
