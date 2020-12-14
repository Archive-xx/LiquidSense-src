package jdk.internal.dynalink;

import java.lang.invoke.MethodHandle;
import jdk.internal.dynalink.linker.GuardedInvocation;

public interface RelinkableCallSite {
   void initialize(MethodHandle var1);

   CallSiteDescriptor getDescriptor();

   void relink(GuardedInvocation var1, MethodHandle var2);

   void resetAndRelink(GuardedInvocation var1, MethodHandle var2);
}
