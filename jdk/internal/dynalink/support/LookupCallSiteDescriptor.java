package jdk.internal.dynalink.support;

import java.lang.invoke.MethodType;
import jdk.internal.dynalink.CallSiteDescriptor;

class LookupCallSiteDescriptor extends DefaultCallSiteDescriptor {
   private final java.lang.invoke.MethodHandles.Lookup lookup;

   LookupCallSiteDescriptor(String[] tokenizedName, MethodType methodType, java.lang.invoke.MethodHandles.Lookup lookup) {
      super(tokenizedName, methodType);
      this.lookup = lookup;
   }

   public java.lang.invoke.MethodHandles.Lookup getLookup() {
      return this.lookup;
   }

   public CallSiteDescriptor changeMethodType(MethodType newMethodType) {
      return new LookupCallSiteDescriptor(this.getTokenizedName(), newMethodType, this.lookup);
   }
}
