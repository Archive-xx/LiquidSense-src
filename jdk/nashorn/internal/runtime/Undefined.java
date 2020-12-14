package jdk.nashorn.internal.runtime;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.support.CallSiteDescriptorFactory;
import jdk.internal.dynalink.support.Guards;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.linker.NashornCallSiteDescriptor;

public final class Undefined extends DefaultPropertyAccess {
   private static final Undefined UNDEFINED = new Undefined();
   private static final Undefined EMPTY = new Undefined();
   private static final MethodHandle UNDEFINED_GUARD;
   private static final MethodHandle GET_METHOD;
   private static final MethodHandle SET_METHOD;

   private Undefined() {
   }

   public static Undefined getUndefined() {
      return UNDEFINED;
   }

   public static Undefined getEmpty() {
      return EMPTY;
   }

   public String getClassName() {
      return "Undefined";
   }

   public String toString() {
      return "undefined";
   }

   public static GuardedInvocation lookup(CallSiteDescriptor desc) {
      String operator = (String)CallSiteDescriptorFactory.tokenizeOperators(desc).get(0);
      byte var3 = -1;
      switch(operator.hashCode()) {
      case -75566075:
         if (operator.equals("getElem")) {
            var3 = 4;
         }
         break;
      case -75232295:
         if (operator.equals("getProp")) {
            var3 = 3;
         }
         break;
      case 108960:
         if (operator.equals("new")) {
            var3 = 0;
         }
         break;
      case 3045982:
         if (operator.equals("call")) {
            var3 = 1;
         }
         break;
      case 618460119:
         if (operator.equals("getMethod")) {
            var3 = 5;
         }
         break;
      case 1402960095:
         if (operator.equals("callMethod")) {
            var3 = 2;
         }
         break;
      case 1984543505:
         if (operator.equals("setElem")) {
            var3 = 7;
         }
         break;
      case 1984877285:
         if (operator.equals("setProp")) {
            var3 = 6;
         }
      }

      switch(var3) {
      case 0:
      case 1:
         String name = NashornCallSiteDescriptor.getFunctionDescription(desc);
         String msg = name != null ? "not.a.function" : "cant.call.undefined";
         throw ECMAErrors.typeError(msg, name);
      case 2:
         throw lookupTypeError("cant.read.property.of.undefined", desc);
      case 3:
      case 4:
      case 5:
         if (desc.getNameTokenCount() < 3) {
            return findGetIndexMethod(desc);
         }

         return findGetMethod(desc);
      case 6:
      case 7:
         if (desc.getNameTokenCount() < 3) {
            return findSetIndexMethod(desc);
         }

         return findSetMethod(desc);
      default:
         return null;
      }
   }

   private static ECMAException lookupTypeError(String msg, CallSiteDescriptor desc) {
      String name = desc.getNameToken(2);
      return ECMAErrors.typeError(msg, name != null && !name.isEmpty() ? name : null);
   }

   private static GuardedInvocation findGetMethod(CallSiteDescriptor desc) {
      return (new GuardedInvocation(Lookup.MH.insertArguments(GET_METHOD, 1, desc.getNameToken(2)), UNDEFINED_GUARD)).asType(desc);
   }

   private static GuardedInvocation findGetIndexMethod(CallSiteDescriptor desc) {
      return (new GuardedInvocation(GET_METHOD, UNDEFINED_GUARD)).asType(desc);
   }

   private static GuardedInvocation findSetMethod(CallSiteDescriptor desc) {
      return (new GuardedInvocation(Lookup.MH.insertArguments(SET_METHOD, 1, desc.getNameToken(2)), UNDEFINED_GUARD)).asType(desc);
   }

   private static GuardedInvocation findSetIndexMethod(CallSiteDescriptor desc) {
      return (new GuardedInvocation(SET_METHOD, UNDEFINED_GUARD)).asType(desc);
   }

   public Object get(Object key) {
      throw ECMAErrors.typeError("cant.read.property.of.undefined", ScriptRuntime.safeToString(key));
   }

   public void set(Object key, Object value, int flags) {
      throw ECMAErrors.typeError("cant.set.property.of.undefined", ScriptRuntime.safeToString(key));
   }

   public boolean delete(Object key, boolean strict) {
      throw ECMAErrors.typeError("cant.delete.property.of.undefined", ScriptRuntime.safeToString(key));
   }

   public boolean has(Object key) {
      return false;
   }

   public boolean hasOwnProperty(Object key) {
      return false;
   }

   private static MethodHandle findOwnMH(String name, Class<?> rtype, Class<?>... types) {
      return Lookup.MH.findVirtual(MethodHandles.lookup(), Undefined.class, name, Lookup.MH.type(rtype, types));
   }

   static {
      UNDEFINED_GUARD = Guards.getIdentityGuard(UNDEFINED);
      GET_METHOD = findOwnMH("get", Object.class, Object.class);
      SET_METHOD = Lookup.MH.insertArguments(findOwnMH("set", Void.TYPE, Object.class, Object.class, Integer.TYPE), 3, 2);
   }
}
