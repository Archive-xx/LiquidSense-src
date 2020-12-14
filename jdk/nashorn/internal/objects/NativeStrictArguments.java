package jdk.nashorn.internal.objects;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.AccessorProperty;
import jdk.nashorn.internal.runtime.Property;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.arrays.ArrayData;

public final class NativeStrictArguments extends ScriptObject {
   private static final MethodHandle G$LENGTH = findOwnMH("G$length", Object.class, Object.class);
   private static final MethodHandle S$LENGTH;
   private static final PropertyMap map$;
   private Object length;
   private final Object[] namedArgs;

   static PropertyMap getInitialMap() {
      return map$;
   }

   NativeStrictArguments(Object[] values, int numParams, ScriptObject proto, PropertyMap map) {
      super(proto, map);
      this.setIsArguments();
      ScriptFunction func = Global.instance().getTypeErrorThrower();
      int flags = true;
      this.initUserAccessors("caller", 6, func, func);
      this.initUserAccessors("callee", 6, func, func);
      this.setArray(ArrayData.allocate(values));
      this.length = values.length;
      this.namedArgs = new Object[numParams];
      if (numParams > values.length) {
         Arrays.fill(this.namedArgs, ScriptRuntime.UNDEFINED);
      }

      System.arraycopy(values, 0, this.namedArgs, 0, Math.min(this.namedArgs.length, values.length));
   }

   public String getClassName() {
      return "Arguments";
   }

   public Object getArgument(int key) {
      return key >= 0 && key < this.namedArgs.length ? this.namedArgs[key] : ScriptRuntime.UNDEFINED;
   }

   public void setArgument(int key, Object value) {
      if (key >= 0 && key < this.namedArgs.length) {
         this.namedArgs[key] = value;
      }

   }

   public static Object G$length(Object self) {
      return self instanceof NativeStrictArguments ? ((NativeStrictArguments)self).getArgumentsLength() : 0;
   }

   public static void S$length(Object self, Object value) {
      if (self instanceof NativeStrictArguments) {
         ((NativeStrictArguments)self).setArgumentsLength(value);
      }

   }

   private Object getArgumentsLength() {
      return this.length;
   }

   private void setArgumentsLength(Object length) {
      this.length = length;
   }

   private static MethodHandle findOwnMH(String name, Class<?> rtype, Class<?>... types) {
      return Lookup.MH.findStatic(MethodHandles.lookup(), NativeStrictArguments.class, name, Lookup.MH.type(rtype, types));
   }

   static {
      S$LENGTH = findOwnMH("S$length", Void.TYPE, Object.class, Object.class);
      ArrayList<Property> properties = new ArrayList(1);
      properties.add(AccessorProperty.create("length", 2, G$LENGTH, S$LENGTH));
      PropertyMap map = PropertyMap.newMap((Collection)properties);
      int flags = true;
      map = map.addPropertyNoHistory(map.newUserAccessors("caller", 6));
      map = map.addPropertyNoHistory(map.newUserAccessors("callee", 6));
      map$ = map;
   }
}
