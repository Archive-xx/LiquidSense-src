package jdk.nashorn.internal.runtime.linker;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Arrays;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.linker.TypeBasedGuardingDynamicLinker;
import jdk.internal.dynalink.support.Guards;

final class BoundCallableLinker implements TypeBasedGuardingDynamicLinker {
   public boolean canLinkType(Class<?> type) {
      return type == BoundCallable.class;
   }

   public GuardedInvocation getGuardedInvocation(LinkRequest linkRequest, LinkerServices linkerServices) throws Exception {
      Object objBoundCallable = linkRequest.getReceiver();
      if (!(objBoundCallable instanceof BoundCallable)) {
         return null;
      } else {
         CallSiteDescriptor descriptor = linkRequest.getCallSiteDescriptor();
         if (descriptor.getNameTokenCount() >= 2 && "dyn".equals(descriptor.getNameToken(0))) {
            String operation = descriptor.getNameToken(1);
            boolean isCall;
            if ("new".equals(operation)) {
               isCall = false;
            } else {
               if (!"call".equals(operation)) {
                  return null;
               }

               isCall = true;
            }

            BoundCallable boundCallable = (BoundCallable)objBoundCallable;
            Object callable = boundCallable.getCallable();
            Object boundThis = boundCallable.getBoundThis();
            Object[] args = linkRequest.getArguments();
            Object[] boundArgs = boundCallable.getBoundArgs();
            int argsLen = args.length;
            int boundArgsLen = boundArgs.length;
            Object[] newArgs = new Object[argsLen + boundArgsLen];
            newArgs[0] = callable;
            byte firstArgIndex;
            if (isCall) {
               newArgs[1] = boundThis;
               firstArgIndex = 2;
            } else {
               firstArgIndex = 1;
            }

            System.arraycopy(boundArgs, 0, newArgs, firstArgIndex, boundArgsLen);
            System.arraycopy(args, firstArgIndex, newArgs, firstArgIndex + boundArgsLen, argsLen - firstArgIndex);
            MethodType type = descriptor.getMethodType();
            MethodType newMethodType = descriptor.getMethodType().changeParameterType(0, callable.getClass());
            if (isCall) {
               newMethodType = newMethodType.changeParameterType(1, boundThis == null ? Object.class : boundThis.getClass());
            }

            for(int i = boundArgs.length; i-- > 0; newMethodType = newMethodType.insertParameterTypes(firstArgIndex, new Class[]{boundArgs[i] == null ? Object.class : boundArgs[i].getClass()})) {
            }

            CallSiteDescriptor newDescriptor = descriptor.changeMethodType(newMethodType);
            GuardedInvocation inv = linkerServices.getGuardedInvocation(linkRequest.replaceArguments(newDescriptor, newArgs));
            if (inv == null) {
               return null;
            } else {
               MethodHandle boundHandle = MethodHandles.insertArguments(inv.getInvocation(), 0, Arrays.copyOf(newArgs, firstArgIndex + boundArgs.length));
               Class<?> p0Type = type.parameterType(0);
               MethodHandle droppingHandle;
               if (isCall) {
                  droppingHandle = MethodHandles.dropArguments(boundHandle, 0, new Class[]{p0Type, type.parameterType(1)});
               } else {
                  droppingHandle = MethodHandles.dropArguments(boundHandle, 0, new Class[]{p0Type});
               }

               MethodHandle newGuard = Guards.getIdentityGuard(boundCallable);
               return inv.replaceMethods(droppingHandle, newGuard.asType(newGuard.type().changeParameterType(0, p0Type)));
            }
         } else {
            return null;
         }
      }
   }
}
