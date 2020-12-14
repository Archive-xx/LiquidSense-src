package jdk.nashorn.internal.runtime.linker;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.linker.TypeBasedGuardingDynamicLinker;
import jdk.internal.dynalink.support.CallSiteDescriptorFactory;
import jdk.nashorn.internal.lookup.MethodHandleFactory;
import jdk.nashorn.internal.lookup.MethodHandleFunctionality;
import jdk.nashorn.internal.runtime.JSType;

final class BrowserJSObjectLinker implements TypeBasedGuardingDynamicLinker {
   private static final ClassLoader myLoader = BrowserJSObjectLinker.class.getClassLoader();
   private static final String JSOBJECT_CLASS = "netscape.javascript.JSObject";
   private static volatile Class<?> jsObjectClass;
   private final NashornBeansLinker nashornBeansLinker;
   private static final MethodHandleFunctionality MH = MethodHandleFactory.getFunctionality();
   private static final MethodHandle IS_JSOBJECT_GUARD;
   private static final MethodHandle JSOBJECTLINKER_GET;
   private static final MethodHandle JSOBJECTLINKER_PUT;

   BrowserJSObjectLinker(NashornBeansLinker nashornBeansLinker) {
      this.nashornBeansLinker = nashornBeansLinker;
   }

   public boolean canLinkType(Class<?> type) {
      return canLinkTypeStatic(type);
   }

   static boolean canLinkTypeStatic(Class<?> type) {
      if (jsObjectClass != null && jsObjectClass.isAssignableFrom(type)) {
         return true;
      } else {
         for(Class clazz = type; clazz != null; clazz = clazz.getSuperclass()) {
            if (clazz.getClassLoader() == myLoader && clazz.getName().equals("netscape.javascript.JSObject")) {
               jsObjectClass = clazz;
               return true;
            }
         }

         return false;
      }
   }

   private static void checkJSObjectClass() {
      assert jsObjectClass != null : "netscape.javascript.JSObject not found!";

   }

   public GuardedInvocation getGuardedInvocation(LinkRequest request, LinkerServices linkerServices) throws Exception {
      LinkRequest requestWithoutContext = request.withoutRuntimeContext();
      Object self = requestWithoutContext.getReceiver();
      CallSiteDescriptor desc = requestWithoutContext.getCallSiteDescriptor();
      checkJSObjectClass();
      if (desc.getNameTokenCount() >= 2 && "dyn".equals(desc.getNameToken(0))) {
         if (jsObjectClass.isInstance(self)) {
            GuardedInvocation inv = this.lookup(desc, request, linkerServices);
            inv = inv.replaceMethods(linkerServices.filterInternalObjects(inv.getInvocation()), inv.getGuard());
            return Bootstrap.asTypeSafeReturn(inv, linkerServices, desc);
         } else {
            throw new AssertionError();
         }
      } else {
         return null;
      }
   }

   private GuardedInvocation lookup(CallSiteDescriptor desc, LinkRequest request, LinkerServices linkerServices) throws Exception {
      String operator = (String)CallSiteDescriptorFactory.tokenizeOperators(desc).get(0);
      int c = desc.getNameTokenCount();

      GuardedInvocation inv;
      try {
         inv = this.nashornBeansLinker.getGuardedInvocation(request, linkerServices);
      } catch (Throwable var9) {
         inv = null;
      }

      byte var8 = -1;
      switch(operator.hashCode()) {
      case -75566075:
         if (operator.equals("getElem")) {
            var8 = 1;
         }
         break;
      case -75232295:
         if (operator.equals("getProp")) {
            var8 = 0;
         }
         break;
      case 3045982:
         if (operator.equals("call")) {
            var8 = 5;
         }
         break;
      case 618460119:
         if (operator.equals("getMethod")) {
            var8 = 2;
         }
         break;
      case 1984543505:
         if (operator.equals("setElem")) {
            var8 = 4;
         }
         break;
      case 1984877285:
         if (operator.equals("setProp")) {
            var8 = 3;
         }
      }

      switch(var8) {
      case 0:
      case 1:
      case 2:
         return c > 2 ? findGetMethod(desc, inv) : findGetIndexMethod(inv);
      case 3:
      case 4:
         return c > 2 ? findSetMethod(desc, inv) : findSetIndexMethod();
      case 5:
         return findCallMethod(desc);
      default:
         return null;
      }
   }

   private static GuardedInvocation findGetMethod(CallSiteDescriptor desc, GuardedInvocation inv) {
      if (inv != null) {
         return inv;
      } else {
         String name = desc.getNameToken(2);
         MethodHandle getter = MH.insertArguments(BrowserJSObjectLinker.JSObjectHandles.JSOBJECT_GETMEMBER, 1, name);
         return new GuardedInvocation(getter, IS_JSOBJECT_GUARD);
      }
   }

   private static GuardedInvocation findGetIndexMethod(GuardedInvocation inv) {
      MethodHandle getter = MH.insertArguments(JSOBJECTLINKER_GET, 0, inv.getInvocation());
      return inv.replaceMethods(getter, inv.getGuard());
   }

   private static GuardedInvocation findSetMethod(CallSiteDescriptor desc, GuardedInvocation inv) {
      if (inv != null) {
         return inv;
      } else {
         MethodHandle getter = MH.insertArguments(BrowserJSObjectLinker.JSObjectHandles.JSOBJECT_SETMEMBER, 1, desc.getNameToken(2));
         return new GuardedInvocation(getter, IS_JSOBJECT_GUARD);
      }
   }

   private static GuardedInvocation findSetIndexMethod() {
      return new GuardedInvocation(JSOBJECTLINKER_PUT, IS_JSOBJECT_GUARD);
   }

   private static GuardedInvocation findCallMethod(CallSiteDescriptor desc) {
      MethodHandle call = MH.insertArguments(BrowserJSObjectLinker.JSObjectHandles.JSOBJECT_CALL, 1, "call");
      return new GuardedInvocation(MH.asCollector(call, Object[].class, desc.getMethodType().parameterCount() - 1), IS_JSOBJECT_GUARD);
   }

   private static boolean isJSObject(Object self) {
      return jsObjectClass.isInstance(self);
   }

   private static Object get(MethodHandle fallback, Object jsobj, Object key) throws Throwable {
      if (key instanceof Integer) {
         return BrowserJSObjectLinker.JSObjectHandles.JSOBJECT_GETSLOT.invokeExact(jsobj, (Integer)key);
      } else {
         if (key instanceof Number) {
            int index = getIndex((Number)key);
            if (index > -1) {
               return BrowserJSObjectLinker.JSObjectHandles.JSOBJECT_GETSLOT.invokeExact(jsobj, index);
            }
         } else if (JSType.isString(key)) {
            String name = key.toString();
            if (name.indexOf(40) != -1) {
               return fallback.invokeExact(jsobj, name);
            }

            return BrowserJSObjectLinker.JSObjectHandles.JSOBJECT_GETMEMBER.invokeExact(jsobj, name);
         }

         return null;
      }
   }

   private static void put(Object jsobj, Object key, Object value) throws Throwable {
      if (key instanceof Integer) {
         BrowserJSObjectLinker.JSObjectHandles.JSOBJECT_SETSLOT.invokeExact(jsobj, (Integer)key, value);
      } else if (key instanceof Number) {
         BrowserJSObjectLinker.JSObjectHandles.JSOBJECT_SETSLOT.invokeExact(jsobj, getIndex((Number)key), value);
      } else if (JSType.isString(key)) {
         BrowserJSObjectLinker.JSObjectHandles.JSOBJECT_SETMEMBER.invokeExact(jsobj, key.toString(), value);
      }

   }

   private static int getIndex(Number n) {
      double value = n.doubleValue();
      return JSType.isRepresentableAsInt(value) ? (int)value : -1;
   }

   private static MethodHandle findOwnMH_S(String name, Class<?> rtype, Class<?>... types) {
      return MH.findStatic(MethodHandles.lookup(), BrowserJSObjectLinker.class, name, MH.type(rtype, types));
   }

   static {
      IS_JSOBJECT_GUARD = findOwnMH_S("isJSObject", Boolean.TYPE, Object.class);
      JSOBJECTLINKER_GET = findOwnMH_S("get", Object.class, MethodHandle.class, Object.class, Object.class);
      JSOBJECTLINKER_PUT = findOwnMH_S("put", Void.TYPE, Object.class, Object.class, Object.class);
   }

   static class JSObjectHandles {
      static final MethodHandle JSOBJECT_GETMEMBER;
      static final MethodHandle JSOBJECT_GETSLOT;
      static final MethodHandle JSOBJECT_SETMEMBER;
      static final MethodHandle JSOBJECT_SETSLOT;
      static final MethodHandle JSOBJECT_CALL;

      private static MethodHandle findJSObjectMH_V(String name, Class<?> rtype, Class<?>... types) {
         BrowserJSObjectLinker.checkJSObjectClass();
         return BrowserJSObjectLinker.MH.findVirtual(MethodHandles.publicLookup(), BrowserJSObjectLinker.jsObjectClass, name, BrowserJSObjectLinker.MH.type(rtype, types));
      }

      static {
         JSOBJECT_GETMEMBER = findJSObjectMH_V("getMember", Object.class, String.class).asType(BrowserJSObjectLinker.MH.type(Object.class, Object.class, String.class));
         JSOBJECT_GETSLOT = findJSObjectMH_V("getSlot", Object.class, Integer.TYPE).asType(BrowserJSObjectLinker.MH.type(Object.class, Object.class, Integer.TYPE));
         JSOBJECT_SETMEMBER = findJSObjectMH_V("setMember", Void.TYPE, String.class, Object.class).asType(BrowserJSObjectLinker.MH.type(Void.TYPE, Object.class, String.class, Object.class));
         JSOBJECT_SETSLOT = findJSObjectMH_V("setSlot", Void.TYPE, Integer.TYPE, Object.class).asType(BrowserJSObjectLinker.MH.type(Void.TYPE, Object.class, Integer.TYPE, Object.class));
         JSOBJECT_CALL = findJSObjectMH_V("call", Object.class, String.class, Object[].class).asType(BrowserJSObjectLinker.MH.type(Object.class, Object.class, String.class, Object[].class));
      }
   }
}
