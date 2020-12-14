package jdk.internal.dynalink.support;

import jdk.internal.dynalink.GuardedInvocationFilter;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.linker.LinkerServices;

public class DefaultPrelinkFilter implements GuardedInvocationFilter {
   public GuardedInvocation filter(GuardedInvocation inv, LinkRequest request, LinkerServices linkerServices) {
      return inv.asType(linkerServices, request.getCallSiteDescriptor().getMethodType());
   }
}
