package jdk.internal.dynalink.support;

import java.lang.invoke.MethodType;
import jdk.internal.dynalink.CallSiteDescriptor;

class UnnamedDynCallSiteDescriptor extends AbstractCallSiteDescriptor {
   private final MethodType methodType;
   private final String op;

   UnnamedDynCallSiteDescriptor(String op, MethodType methodType) {
      this.op = op;
      this.methodType = methodType;
   }

   public int getNameTokenCount() {
      return 2;
   }

   String getOp() {
      return this.op;
   }

   public String getNameToken(int i) {
      switch(i) {
      case 0:
         return "dyn";
      case 1:
         return this.op;
      default:
         throw new IndexOutOfBoundsException(String.valueOf(i));
      }
   }

   public MethodType getMethodType() {
      return this.methodType;
   }

   public CallSiteDescriptor changeMethodType(MethodType newMethodType) {
      return CallSiteDescriptorFactory.getCanonicalPublicDescriptor(new UnnamedDynCallSiteDescriptor(this.op, newMethodType));
   }
}
