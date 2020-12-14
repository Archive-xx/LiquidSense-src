package jdk.nashorn.internal.runtime.linker;

import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Modifier;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.beans.BeansLinker;
import jdk.internal.dynalink.beans.StaticClass;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.GuardingDynamicLinker;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.linker.TypeBasedGuardingDynamicLinker;
import jdk.internal.dynalink.support.Guards;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.ScriptObject;

final class NashornStaticClassLinker implements TypeBasedGuardingDynamicLinker {
   private static final GuardingDynamicLinker staticClassLinker = BeansLinker.getLinkerForClass(StaticClass.class);

   public boolean canLinkType(Class<?> type) {
      return type == StaticClass.class;
   }

   public GuardedInvocation getGuardedInvocation(LinkRequest linkRequest, LinkerServices linkerServices) throws Exception {
      LinkRequest request = linkRequest.withoutRuntimeContext();
      Object self = request.getReceiver();
      if (self.getClass() != StaticClass.class) {
         return null;
      } else {
         Class<?> receiverClass = ((StaticClass)self).getRepresentedClass();
         Bootstrap.checkReflectionAccess(receiverClass, true);
         CallSiteDescriptor desc = request.getCallSiteDescriptor();
         if ("new".equals(desc.getNameToken(1))) {
            if (!Modifier.isPublic(receiverClass.getModifiers())) {
               throw ECMAErrors.typeError("new.on.nonpublic.javatype", receiverClass.getName());
            } else {
               Context.checkPackageAccess(receiverClass);
               if (NashornLinker.isAbstractClass(receiverClass)) {
                  Object[] args = request.getArguments();
                  args[0] = JavaAdapterFactory.getAdapterClassFor(new Class[]{receiverClass}, (ScriptObject)null, (Lookup)linkRequest.getCallSiteDescriptor().getLookup());
                  LinkRequest adapterRequest = request.replaceArguments(request.getCallSiteDescriptor(), args);
                  GuardedInvocation gi = checkNullConstructor(delegate(linkerServices, adapterRequest), receiverClass);
                  return gi.replaceMethods(gi.getInvocation(), Guards.getIdentityGuard(self));
               } else {
                  return checkNullConstructor(delegate(linkerServices, request), receiverClass);
               }
            }
         } else {
            return delegate(linkerServices, request);
         }
      }
   }

   private static GuardedInvocation delegate(LinkerServices linkerServices, LinkRequest request) throws Exception {
      return NashornBeansLinker.getGuardedInvocation(staticClassLinker, request, linkerServices);
   }

   private static GuardedInvocation checkNullConstructor(GuardedInvocation ctorInvocation, Class<?> receiverClass) {
      if (ctorInvocation == null) {
         throw ECMAErrors.typeError("no.constructor.matches.args", receiverClass.getName());
      } else {
         return ctorInvocation;
      }
   }
}
