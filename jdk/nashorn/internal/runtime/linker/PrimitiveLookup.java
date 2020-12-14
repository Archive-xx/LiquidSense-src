package jdk.nashorn.internal.runtime.linker;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.SwitchPoint;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.support.CallSiteDescriptorFactory;
import jdk.internal.dynalink.support.Guards;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.FindProperty;
import jdk.nashorn.internal.runtime.GlobalConstants;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.UserAccessorProperty;

public final class PrimitiveLookup {
   private static final MethodHandle PRIMITIVE_SETTER;

   private PrimitiveLookup() {
   }

   public static GuardedInvocation lookupPrimitive(LinkRequest request, Class<?> receiverClass, ScriptObject wrappedReceiver, MethodHandle wrapFilter, MethodHandle protoFilter) {
      return lookupPrimitive(request, Guards.getInstanceOfGuard(receiverClass), wrappedReceiver, wrapFilter, protoFilter);
   }

   public static GuardedInvocation lookupPrimitive(LinkRequest request, MethodHandle guard, ScriptObject wrappedReceiver, MethodHandle wrapFilter, MethodHandle protoFilter) {
      CallSiteDescriptor desc = request.getCallSiteDescriptor();
      String name;
      FindProperty find;
      if (desc.getNameTokenCount() > 2) {
         name = desc.getNameToken(2);
         find = wrappedReceiver.findProperty(name, true);
      } else {
         name = null;
         find = null;
      }

      String firstOp = (String)CallSiteDescriptorFactory.tokenizeOperators(desc).get(0);
      byte var10 = -1;
      switch(firstOp.hashCode()) {
      case -75566075:
         if (firstOp.equals("getElem")) {
            var10 = 1;
         }
         break;
      case -75232295:
         if (firstOp.equals("getProp")) {
            var10 = 0;
         }
         break;
      case 618460119:
         if (firstOp.equals("getMethod")) {
            var10 = 2;
         }
         break;
      case 1984543505:
         if (firstOp.equals("setElem")) {
            var10 = 4;
         }
         break;
      case 1984877285:
         if (firstOp.equals("setProp")) {
            var10 = 3;
         }
      }

      switch(var10) {
      case 0:
      case 1:
      case 2:
         if (name != null) {
            if (find == null) {
               return null;
            } else {
               SwitchPoint sp = find.getProperty().getBuiltinSwitchPoint();
               if (sp instanceof Context.BuiltinSwitchPoint && !sp.hasBeenInvalidated()) {
                  return new GuardedInvocation(GlobalConstants.staticConstantGetter(find.getObjectValue()), guard, sp, (Class)null);
               } else if (find.isInherited() && !(find.getProperty() instanceof UserAccessorProperty)) {
                  ScriptObject proto = wrappedReceiver.getProto();
                  GuardedInvocation link = proto.lookup(desc, request);
                  if (link != null) {
                     MethodHandle invocation = link.getInvocation();
                     MethodHandle adaptedInvocation = Lookup.MH.asType(invocation, invocation.type().changeParameterType(0, Object.class));
                     MethodHandle method = Lookup.MH.filterArguments(adaptedInvocation, 0, protoFilter);
                     MethodHandle protoGuard = Lookup.MH.filterArguments(link.getGuard(), 0, protoFilter);
                     return new GuardedInvocation(method, NashornGuards.combineGuards(guard, protoGuard));
                  }
               }
            }
         }
      default:
         GuardedInvocation link = wrappedReceiver.lookup(desc, request);
         if (link != null) {
            MethodHandle method = link.getInvocation();
            Class<?> receiverType = method.type().parameterType(0);
            if (receiverType != Object.class) {
               MethodType wrapType = wrapFilter.type();

               assert receiverType.isAssignableFrom(wrapType.returnType());

               method = Lookup.MH.filterArguments(method, 0, Lookup.MH.asType(wrapFilter, wrapType.changeReturnType(receiverType)));
            }

            return new GuardedInvocation(method, guard, link.getSwitchPoints(), (Class)null);
         }

         return null;
      case 3:
      case 4:
         return getPrimitiveSetter(name, guard, wrapFilter, NashornCallSiteDescriptor.isStrict(desc));
      }
   }

   private static GuardedInvocation getPrimitiveSetter(String name, MethodHandle guard, MethodHandle wrapFilter, boolean isStrict) {
      MethodHandle filter = Lookup.MH.asType(wrapFilter, wrapFilter.type().changeReturnType(ScriptObject.class));
      MethodHandle target;
      if (name == null) {
         filter = Lookup.MH.dropArguments(filter, 1, (Class[])(Object.class, Object.class));
         target = Lookup.MH.insertArguments(PRIMITIVE_SETTER, 3, isStrict);
      } else {
         filter = Lookup.MH.dropArguments(filter, 1, (Class[])(Object.class));
         target = Lookup.MH.insertArguments(PRIMITIVE_SETTER, 2, name, isStrict);
      }

      return new GuardedInvocation(Lookup.MH.foldArguments(target, filter), guard);
   }

   private static void primitiveSetter(ScriptObject wrappedSelf, Object self, Object key, boolean strict, Object value) {
      String name = JSType.toString(key);
      FindProperty find = wrappedSelf.findProperty(name, true);
      if (find != null && find.getProperty() instanceof UserAccessorProperty && find.getProperty().isWritable()) {
         find.setValue(value, strict);
      } else if (strict) {
         throw ECMAErrors.typeError("property.not.writable", name, ScriptRuntime.safeToString(self));
      }
   }

   private static MethodHandle findOwnMH(String name, MethodType type) {
      return Lookup.MH.findStatic(MethodHandles.lookup(), PrimitiveLookup.class, name, type);
   }

   static {
      PRIMITIVE_SETTER = findOwnMH("primitiveSetter", Lookup.MH.type(Void.TYPE, ScriptObject.class, Object.class, Object.class, Boolean.TYPE, Object.class));
   }
}
