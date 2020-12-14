package jdk.internal.dynalink.support;

import java.lang.invoke.MethodType;
import jdk.internal.dynalink.CallSiteDescriptor;

class DefaultCallSiteDescriptor extends AbstractCallSiteDescriptor {
   private final String[] tokenizedName;
   private final MethodType methodType;

   DefaultCallSiteDescriptor(String[] tokenizedName, MethodType methodType) {
      this.tokenizedName = tokenizedName;
      this.methodType = methodType;
   }

   public int getNameTokenCount() {
      return this.tokenizedName.length;
   }

   public String getNameToken(int i) {
      try {
         return this.tokenizedName[i];
      } catch (ArrayIndexOutOfBoundsException var3) {
         throw new IllegalArgumentException(var3.getMessage());
      }
   }

   String[] getTokenizedName() {
      return this.tokenizedName;
   }

   public MethodType getMethodType() {
      return this.methodType;
   }

   public CallSiteDescriptor changeMethodType(MethodType newMethodType) {
      return CallSiteDescriptorFactory.getCanonicalPublicDescriptor(new DefaultCallSiteDescriptor(this.tokenizedName, newMethodType));
   }
}
