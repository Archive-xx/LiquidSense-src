package jdk.nashorn.internal.runtime.linker;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.beans.BeansLinker;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.linker.TypeBasedGuardingDynamicLinker;
import jdk.internal.dynalink.support.CallSiteDescriptorFactory;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.ScriptRuntime;

final class JavaSuperAdapterLinker implements TypeBasedGuardingDynamicLinker {
   private static final String GET_METHOD = "getMethod";
   private static final String DYN_GET_METHOD = "dyn:getMethod";
   private static final String DYN_GET_METHOD_FIXED = "dyn:getMethod:super$";
   private static final MethodHandle ADD_PREFIX_TO_METHOD_NAME;
   private static final MethodHandle BIND_DYNAMIC_METHOD;
   private static final MethodHandle GET_ADAPTER;
   private static final MethodHandle IS_ADAPTER_OF_CLASS;

   public boolean canLinkType(Class<?> type) {
      return type == JavaSuperAdapter.class;
   }

   public GuardedInvocation getGuardedInvocation(LinkRequest linkRequest, LinkerServices linkerServices) throws Exception {
      Object objSuperAdapter = linkRequest.getReceiver();
      if (!(objSuperAdapter instanceof JavaSuperAdapter)) {
         return null;
      } else {
         CallSiteDescriptor descriptor = linkRequest.getCallSiteDescriptor();
         if (!CallSiteDescriptorFactory.tokenizeOperators(descriptor).contains("getMethod")) {
            return null;
         } else {
            Object adapter = ((JavaSuperAdapter)objSuperAdapter).getAdapter();
            Object[] args = linkRequest.getArguments();
            args[0] = adapter;
            MethodType type = descriptor.getMethodType();
            Class<?> adapterClass = adapter.getClass();
            boolean hasFixedName = descriptor.getNameTokenCount() > 2;
            String opName = hasFixedName ? "dyn:getMethod:super$" + descriptor.getNameToken(2) : "dyn:getMethod";
            CallSiteDescriptor newDescriptor = NashornCallSiteDescriptor.get(descriptor.getLookup(), opName, type.changeParameterType(0, adapterClass), 0);
            GuardedInvocation guardedInv = NashornBeansLinker.getGuardedInvocation(BeansLinker.getLinkerForClass(adapterClass), linkRequest.replaceArguments(newDescriptor, args), linkerServices);
            MethodHandle guard = IS_ADAPTER_OF_CLASS.bindTo(adapterClass);
            if (guardedInv == null) {
               return (new GuardedInvocation(MethodHandles.dropArguments(Lookup.EMPTY_GETTER, 1, type.parameterList().subList(1, type.parameterCount())), guard)).asType(descriptor);
            } else {
               MethodHandle invocation = guardedInv.getInvocation();
               MethodType invType = invocation.type();
               MethodHandle typedBinder = BIND_DYNAMIC_METHOD.asType(MethodType.methodType(Object.class, invType.returnType(), invType.parameterType(0)));
               MethodHandle droppingBinder = MethodHandles.dropArguments(typedBinder, 2, invType.parameterList().subList(1, invType.parameterCount()));
               MethodHandle bindingInvocation = MethodHandles.foldArguments(droppingBinder, invocation);
               MethodHandle typedGetAdapter = asFilterType(GET_ADAPTER, 0, invType, type);
               MethodHandle adaptedInvocation;
               if (hasFixedName) {
                  adaptedInvocation = MethodHandles.filterArguments(bindingInvocation, 0, new MethodHandle[]{typedGetAdapter});
               } else {
                  MethodHandle typedAddPrefix = asFilterType(ADD_PREFIX_TO_METHOD_NAME, 1, invType, type);
                  adaptedInvocation = MethodHandles.filterArguments(bindingInvocation, 0, new MethodHandle[]{typedGetAdapter, typedAddPrefix});
               }

               return guardedInv.replaceMethods(adaptedInvocation, guard).asType(descriptor);
            }
         }
      }
   }

   private static MethodHandle asFilterType(MethodHandle filter, int pos, MethodType targetType, MethodType sourceType) {
      return filter.asType(MethodType.methodType(targetType.parameterType(pos), sourceType.parameterType(pos)));
   }

   private static Object addPrefixToMethodName(Object name) {
      return "super$".concat(String.valueOf(name));
   }

   private static Object bindDynamicMethod(Object dynamicMethod, Object boundThis) {
      return dynamicMethod == null ? ScriptRuntime.UNDEFINED : Bootstrap.bindCallable(dynamicMethod, boundThis, (Object[])null);
   }

   private static boolean isAdapterOfClass(Class<?> clazz, Object obj) {
      return obj instanceof JavaSuperAdapter && clazz == ((JavaSuperAdapter)obj).getAdapter().getClass();
   }

   static {
      jdk.internal.dynalink.support.Lookup lookup = new jdk.internal.dynalink.support.Lookup(MethodHandles.lookup());
      ADD_PREFIX_TO_METHOD_NAME = lookup.findOwnStatic("addPrefixToMethodName", Object.class, Object.class);
      BIND_DYNAMIC_METHOD = lookup.findOwnStatic("bindDynamicMethod", Object.class, Object.class, Object.class);
      GET_ADAPTER = lookup.findVirtual(JavaSuperAdapter.class, "getAdapter", MethodType.methodType(Object.class));
      IS_ADAPTER_OF_CLASS = lookup.findOwnStatic("isAdapterOfClass", Boolean.TYPE, Class.class, Object.class);
   }
}
