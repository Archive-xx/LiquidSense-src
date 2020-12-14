package jdk.internal.dynalink;

import java.lang.invoke.MethodHandle;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.support.AbstractRelinkableCallSite;

public class MonomorphicCallSite extends AbstractRelinkableCallSite {
   public MonomorphicCallSite(CallSiteDescriptor descriptor) {
      super(descriptor);
   }

   public void relink(GuardedInvocation guardedInvocation, MethodHandle relink) {
      this.setTarget(guardedInvocation.compose(relink));
   }

   public void resetAndRelink(GuardedInvocation guardedInvocation, MethodHandle relink) {
      this.relink(guardedInvocation, relink);
   }
}
