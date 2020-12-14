package jdk.internal.dynalink.linker;

import jdk.internal.dynalink.CallSiteDescriptor;

public interface LinkRequest {
   CallSiteDescriptor getCallSiteDescriptor();

   Object getCallSiteToken();

   Object[] getArguments();

   Object getReceiver();

   int getLinkCount();

   boolean isCallSiteUnstable();

   LinkRequest withoutRuntimeContext();

   LinkRequest replaceArguments(CallSiteDescriptor var1, Object[] var2);
}
