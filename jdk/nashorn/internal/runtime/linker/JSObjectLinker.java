package jdk.nashorn.internal.runtime.linker;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Map;
import javax.script.Bindings;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.linker.TypeBasedGuardingDynamicLinker;
import jdk.internal.dynalink.support.CallSiteDescriptorFactory;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.internal.lookup.MethodHandleFactory;
import jdk.nashorn.internal.lookup.MethodHandleFunctionality;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptRuntime;

final class JSObjectLinker implements TypeBasedGuardingDynamicLinker {
   private final NashornBeansLinker nashornBeansLinker;
   private static final MethodHandleFunctionality MH = MethodHandleFactory.getFunctionality();
   private static final MethodHandle IS_JSOBJECT_GUARD;
   private static final MethodHandle JSOBJECTLINKER_GET;
   private static final MethodHandle JSOBJECTLINKER_PUT;
   private static final MethodHandle JSOBJECT_GETMEMBER;
   private static final MethodHandle JSOBJECT_SETMEMBER;
   private static final MethodHandle JSOBJECT_CALL;
   private static final MethodHandle JSOBJECT_SCOPE_CALL;
   private static final MethodHandle JSOBJECT_CALL_TO_APPLY;
   private static final MethodHandle JSOBJECT_NEW;

   JSObjectLinker(NashornBeansLinker nashornBeansLinker) {
      this.nashornBeansLinker = nashornBeansLinker;
   }

   public boolean canLinkType(Class<?> type) {
      return canLinkTypeStatic(type);
   }

   static boolean canLinkTypeStatic(Class<?> type) {
      return Map.class.isAssignableFrom(type) || Bindings.class.isAssignableFrom(type) || JSObject.class.isAssignableFrom(type);
   }

   public GuardedInvocation getGuardedInvocation(LinkRequest request, LinkerServices linkerServices) throws Exception {
      LinkRequest requestWithoutContext = request.withoutRuntimeContext();
      Object self = requestWithoutContext.getReceiver();
      CallSiteDescriptor desc = requestWithoutContext.getCallSiteDescriptor();
      if (desc.getNameTokenCount() >= 2 && "dyn".equals(desc.getNameToken(0))) {
         GuardedInvocation inv;
         if (self instanceof JSObject) {
            inv = this.lookup(desc, request, linkerServices);
            inv = inv.replaceMethods(linkerServices.filterInternalObjects(inv.getInvocation()), inv.getGuard());
         } else {
            if (!(self instanceof Map) && !(self instanceof Bindings)) {
               throw new AssertionError();
            }

            GuardedInvocation beanInv = this.nashornBeansLinker.getGuardedInvocation(request, linkerServices);
            inv = new GuardedInvocation(beanInv.getInvocation(), NashornGuards.combineGuards(beanInv.getGuard(), NashornGuards.getNotJSObjectGuard()));
         }

         return Bootstrap.asTypeSafeReturn(inv, linkerServices, desc);
      } else {
         return null;
      }
   }

   private GuardedInvocation lookup(CallSiteDescriptor desc, LinkRequest request, LinkerServices linkerServices) throws Exception {
      String operator = (String)CallSiteDescriptorFactory.tokenizeOperators(desc).get(0);
      int c = desc.getNameTokenCount();
      byte var7 = -1;
      switch(operator.hashCode()) {
      case -75566075:
         if (operator.equals("getElem")) {
            var7 = 1;
         }
         break;
      case -75232295:
         if (operator.equals("getProp")) {
            var7 = 0;
         }
         break;
      case 108960:
         if (operator.equals("new")) {
            var7 = 6;
         }
         break;
      case 3045982:
         if (operator.equals("call")) {
            var7 = 5;
         }
         break;
      case 618460119:
         if (operator.equals("getMethod")) {
            var7 = 2;
         }
         break;
      case 1984543505:
         if (operator.equals("setElem")) {
            var7 = 4;
         }
         break;
      case 1984877285:
         if (operator.equals("setProp")) {
            var7 = 3;
         }
      }

      switch(var7) {
      case 0:
      case 1:
      case 2:
         if (c > 2) {
            return findGetMethod(desc);
         }

         return findGetIndexMethod(this.nashornBeansLinker.getGuardedInvocation(request, linkerServices));
      case 3:
      case 4:
         return c > 2 ? findSetMethod(desc) : findSetIndexMethod();
      case 5:
         return findCallMethod(desc);
      case 6:
         return findNewMethod(desc);
      default:
         return null;
      }
   }

   private static GuardedInvocation findGetMethod(CallSiteDescriptor desc) {
      String name = desc.getNameToken(2);
      MethodHandle getter = MH.insertArguments(JSOBJECT_GETMEMBER, 1, name);
      return new GuardedInvocation(getter, IS_JSOBJECT_GUARD);
   }

   private static GuardedInvocation findGetIndexMethod(GuardedInvocation inv) {
      MethodHandle getter = MH.insertArguments(JSOBJECTLINKER_GET, 0, inv.getInvocation());
      return inv.replaceMethods(getter, inv.getGuard());
   }

   private static GuardedInvocation findSetMethod(CallSiteDescriptor desc) {
      MethodHandle getter = MH.insertArguments(JSOBJECT_SETMEMBER, 1, desc.getNameToken(2));
      return new GuardedInvocation(getter, IS_JSOBJECT_GUARD);
   }

   private static GuardedInvocation findSetIndexMethod() {
      return new GuardedInvocation(JSOBJECTLINKER_PUT, IS_JSOBJECT_GUARD);
   }

   private static GuardedInvocation findCallMethod(CallSiteDescriptor desc) {
      MethodHandle mh = NashornCallSiteDescriptor.isScope(desc) ? JSOBJECT_SCOPE_CALL : JSOBJECT_CALL;
      if (NashornCallSiteDescriptor.isApplyToCall(desc)) {
         mh = MH.insertArguments(JSOBJECT_CALL_TO_APPLY, 0, mh);
      }

      MethodType type = desc.getMethodType();
      mh = type.parameterType(type.parameterCount() - 1) == Object[].class ? mh : MH.asCollector(mh, Object[].class, type.parameterCount() - 2);
      return new GuardedInvocation(mh, IS_JSOBJECT_GUARD);
   }

   private static GuardedInvocation findNewMethod(CallSiteDescriptor desc) {
      MethodHandle func = MH.asCollector(JSOBJECT_NEW, Object[].class, desc.getMethodType().parameterCount() - 1);
      return new GuardedInvocation(func, IS_JSOBJECT_GUARD);
   }

   private static boolean isJSObject(Object self) {
      return self instanceof JSObject;
   }

   private static Object get(MethodHandle fallback, Object jsobj, Object key) throws Throwable {
      if (key instanceof Integer) {
         return ((JSObject)jsobj).getSlot((Integer)key);
      } else if (key instanceof Number) {
         int index = getIndex((Number)key);
         return index > -1 ? ((JSObject)jsobj).getSlot(index) : ((JSObject)jsobj).getMember(JSType.toString(key));
      } else if (JSType.isString(key)) {
         String name = key.toString();
         return name.indexOf(40) != -1 ? fallback.invokeExact(jsobj, name) : ((JSObject)jsobj).getMember(name);
      } else {
         return null;
      }
   }

   private static void put(Object jsobj, Object key, Object value) {
      if (key instanceof Integer) {
         ((JSObject)jsobj).setSlot((Integer)key, value);
      } else if (key instanceof Number) {
         int index = getIndex((Number)key);
         if (index > -1) {
            ((JSObject)jsobj).setSlot(index, value);
         } else {
            ((JSObject)jsobj).setMember(JSType.toString(key), value);
         }
      } else if (JSType.isString(key)) {
         ((JSObject)jsobj).setMember(key.toString(), value);
      }

   }

   private static int getIndex(Number n) {
      double value = n.doubleValue();
      return JSType.isRepresentableAsInt(value) ? (int)value : -1;
   }

   private static Object callToApply(MethodHandle mh, JSObject obj, Object thiz, Object... args) {
      assert args.length >= 2;

      Object receiver = args[0];
      Object[] arguments = new Object[args.length - 1];
      System.arraycopy(args, 1, arguments, 0, arguments.length);

      try {
         return mh.invokeExact(obj, thiz, new Object[]{receiver, arguments});
      } catch (Error | RuntimeException var7) {
         throw var7;
      } catch (Throwable var8) {
         throw new RuntimeException(var8);
      }
   }

   private static Object jsObjectScopeCall(JSObject jsObj, Object thiz, Object[] args) {
      Object modifiedThiz;
      if (thiz == ScriptRuntime.UNDEFINED && !jsObj.isStrictFunction()) {
         Global global = Context.getGlobal();
         modifiedThiz = ScriptObjectMirror.wrap(global, global);
      } else {
         modifiedThiz = thiz;
      }

      return jsObj.call(modifiedThiz, args);
   }

   private static MethodHandle findJSObjectMH_V(String name, Class<?> rtype, Class<?>... types) {
      return MH.findVirtual(MethodHandles.lookup(), JSObject.class, name, MH.type(rtype, types));
   }

   private static MethodHandle findOwnMH_S(String name, Class<?> rtype, Class<?>... types) {
      return MH.findStatic(MethodHandles.lookup(), JSObjectLinker.class, name, MH.type(rtype, types));
   }

   static {
      IS_JSOBJECT_GUARD = findOwnMH_S("isJSObject", Boolean.TYPE, Object.class);
      JSOBJECTLINKER_GET = findOwnMH_S("get", Object.class, MethodHandle.class, Object.class, Object.class);
      JSOBJECTLINKER_PUT = findOwnMH_S("put", Void.TYPE, Object.class, Object.class, Object.class);
      JSOBJECT_GETMEMBER = findJSObjectMH_V("getMember", Object.class, String.class);
      JSOBJECT_SETMEMBER = findJSObjectMH_V("setMember", Void.TYPE, String.class, Object.class);
      JSOBJECT_CALL = findJSObjectMH_V("call", Object.class, Object.class, Object[].class);
      JSOBJECT_SCOPE_CALL = findOwnMH_S("jsObjectScopeCall", Object.class, JSObject.class, Object.class, Object[].class);
      JSOBJECT_CALL_TO_APPLY = findOwnMH_S("callToApply", Object.class, MethodHandle.class, JSObject.class, Object.class, Object[].class);
      JSOBJECT_NEW = findJSObjectMH_V("newObject", Object.class, Object[].class);
   }
}
