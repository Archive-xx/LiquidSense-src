package jdk.internal.dynalink.beans;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.linker.TypeBasedGuardingDynamicLinker;
import jdk.internal.dynalink.support.CallSiteDescriptorFactory;
import jdk.internal.dynalink.support.Guards;

class DynamicMethodLinker implements TypeBasedGuardingDynamicLinker {
   public boolean canLinkType(Class<?> type) {
      return DynamicMethod.class.isAssignableFrom(type);
   }

   public GuardedInvocation getGuardedInvocation(LinkRequest linkRequest, LinkerServices linkerServices) {
      Object receiver = linkRequest.getReceiver();
      if (!(receiver instanceof DynamicMethod)) {
         return null;
      } else {
         CallSiteDescriptor desc = linkRequest.getCallSiteDescriptor();
         if (desc.getNameTokenCount() != 2 && desc.getNameToken(0) != "dyn") {
            return null;
         } else {
            String operator = desc.getNameToken(1);
            DynamicMethod dynMethod = (DynamicMethod)receiver;
            boolean constructor = dynMethod.isConstructor();
            MethodHandle invocation;
            if (operator == "call" && !constructor) {
               invocation = dynMethod.getInvocation(CallSiteDescriptorFactory.dropParameterTypes(desc, 0, 1), linkerServices);
            } else {
               if (operator != "new" || !constructor) {
                  return null;
               }

               MethodHandle ctorInvocation = dynMethod.getInvocation(desc, linkerServices);
               if (ctorInvocation == null) {
                  return null;
               }

               invocation = MethodHandles.insertArguments(ctorInvocation, 0, new Object[]{null});
            }

            return invocation != null ? new GuardedInvocation(MethodHandles.dropArguments(invocation, 0, new Class[]{desc.getMethodType().parameterType(0)}), Guards.getIdentityGuard(receiver)) : null;
         }
      }
   }
}
