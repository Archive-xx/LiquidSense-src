package jdk.nashorn.internal.objects;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Collection;
import java.util.Collections;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.linker.PrimitiveLookup;

public final class NativeBoolean extends ScriptObject {
   private final boolean value;
   static final MethodHandle WRAPFILTER;
   private static final MethodHandle PROTOFILTER;
   private static PropertyMap $nasgenmap$;

   private NativeBoolean(boolean value, ScriptObject proto, PropertyMap map) {
      super(proto, map);
      this.value = value;
   }

   NativeBoolean(boolean flag, Global global) {
      this(flag, global.getBooleanPrototype(), $nasgenmap$);
   }

   NativeBoolean(boolean flag) {
      this(flag, Global.instance());
   }

   public String safeToString() {
      return "[Boolean " + this.toString() + "]";
   }

   public String toString() {
      return Boolean.toString(this.getValue());
   }

   public boolean getValue() {
      return this.booleanValue();
   }

   public boolean booleanValue() {
      return this.value;
   }

   public String getClassName() {
      return "Boolean";
   }

   public static String toString(Object self) {
      return getBoolean(self).toString();
   }

   public static boolean valueOf(Object self) {
      return getBoolean(self);
   }

   public static Object constructor(boolean newObj, Object self, Object value) {
      boolean flag = JSType.toBoolean(value);
      return newObj ? new NativeBoolean(flag) : flag;
   }

   private static Boolean getBoolean(Object self) {
      if (self instanceof Boolean) {
         return (Boolean)self;
      } else if (self instanceof NativeBoolean) {
         return ((NativeBoolean)self).getValue();
      } else if (self != null && self == Global.instance().getBooleanPrototype()) {
         return false;
      } else {
         throw ECMAErrors.typeError("not.a.boolean", ScriptRuntime.safeToString(self));
      }
   }

   public static GuardedInvocation lookupPrimitive(LinkRequest request, Object receiver) {
      return PrimitiveLookup.lookupPrimitive(request, (Class)Boolean.class, new NativeBoolean((Boolean)receiver), WRAPFILTER, PROTOFILTER);
   }

   private static NativeBoolean wrapFilter(Object receiver) {
      return new NativeBoolean((Boolean)receiver);
   }

   private static Object protoFilter(Object object) {
      return Global.instance().getBooleanPrototype();
   }

   private static MethodHandle findOwnMH(String name, MethodType type) {
      return Lookup.MH.findStatic(MethodHandles.lookup(), NativeBoolean.class, name, type);
   }

   static {
      WRAPFILTER = findOwnMH("wrapFilter", Lookup.MH.type(NativeBoolean.class, Object.class));
      PROTOFILTER = findOwnMH("protoFilter", Lookup.MH.type(Object.class, Object.class));
      $clinit$();
   }

   public static void $clinit$() {
      $nasgenmap$ = PropertyMap.newMap((Collection)Collections.EMPTY_LIST);
   }
}
