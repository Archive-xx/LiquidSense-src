package jdk.internal.dynalink.support;

import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.LinkRequest;

public class RuntimeContextLinkRequestImpl extends LinkRequestImpl {
   private final int runtimeContextArgCount;
   private LinkRequestImpl contextStrippedRequest;

   public RuntimeContextLinkRequestImpl(CallSiteDescriptor callSiteDescriptor, Object callSiteToken, int linkCount, boolean callSiteUnstable, Object[] arguments, int runtimeContextArgCount) {
      super(callSiteDescriptor, callSiteToken, linkCount, callSiteUnstable, arguments);
      if (runtimeContextArgCount < 1) {
         throw new IllegalArgumentException("runtimeContextArgCount < 1");
      } else {
         this.runtimeContextArgCount = runtimeContextArgCount;
      }
   }

   public LinkRequest withoutRuntimeContext() {
      if (this.contextStrippedRequest == null) {
         this.contextStrippedRequest = new LinkRequestImpl(CallSiteDescriptorFactory.dropParameterTypes(this.getCallSiteDescriptor(), 1, this.runtimeContextArgCount + 1), this.getCallSiteToken(), this.getLinkCount(), this.isCallSiteUnstable(), this.getTruncatedArguments());
      }

      return this.contextStrippedRequest;
   }

   public LinkRequest replaceArguments(CallSiteDescriptor callSiteDescriptor, Object[] arguments) {
      return new RuntimeContextLinkRequestImpl(callSiteDescriptor, this.getCallSiteToken(), this.getLinkCount(), this.isCallSiteUnstable(), arguments, this.runtimeContextArgCount);
   }

   private Object[] getTruncatedArguments() {
      Object[] args = this.getArguments();
      Object[] newargs = new Object[args.length - this.runtimeContextArgCount];
      newargs[0] = args[0];
      System.arraycopy(args, this.runtimeContextArgCount + 1, newargs, 1, newargs.length - 1);
      return newargs;
   }
}
