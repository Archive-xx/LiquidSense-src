package jdk.internal.dynalink.support;

import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.LinkRequest;

public class LinkRequestImpl implements LinkRequest {
   private final CallSiteDescriptor callSiteDescriptor;
   private final Object callSiteToken;
   private final Object[] arguments;
   private final boolean callSiteUnstable;
   private final int linkCount;

   public LinkRequestImpl(CallSiteDescriptor callSiteDescriptor, Object callSiteToken, int linkCount, boolean callSiteUnstable, Object... arguments) {
      this.callSiteDescriptor = callSiteDescriptor;
      this.callSiteToken = callSiteToken;
      this.linkCount = linkCount;
      this.callSiteUnstable = callSiteUnstable;
      this.arguments = arguments;
   }

   public Object[] getArguments() {
      return this.arguments != null ? (Object[])this.arguments.clone() : null;
   }

   public Object getReceiver() {
      return this.arguments != null && this.arguments.length > 0 ? this.arguments[0] : null;
   }

   public CallSiteDescriptor getCallSiteDescriptor() {
      return this.callSiteDescriptor;
   }

   public Object getCallSiteToken() {
      return this.callSiteToken;
   }

   public boolean isCallSiteUnstable() {
      return this.callSiteUnstable;
   }

   public int getLinkCount() {
      return this.linkCount;
   }

   public LinkRequest withoutRuntimeContext() {
      return this;
   }

   public LinkRequest replaceArguments(CallSiteDescriptor newCallSiteDescriptor, Object[] newArguments) {
      return new LinkRequestImpl(newCallSiteDescriptor, this.callSiteToken, this.linkCount, this.callSiteUnstable, newArguments);
   }
}
