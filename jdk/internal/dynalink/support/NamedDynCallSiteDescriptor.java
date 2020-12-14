package jdk.internal.dynalink.support;

import java.lang.invoke.MethodType;
import jdk.internal.dynalink.CallSiteDescriptor;

class NamedDynCallSiteDescriptor extends UnnamedDynCallSiteDescriptor {
   private final String name;

   NamedDynCallSiteDescriptor(String op, String name, MethodType methodType) {
      super(op, methodType);
      this.name = name;
   }

   public int getNameTokenCount() {
      return 3;
   }

   public String getNameToken(int i) {
      switch(i) {
      case 0:
         return "dyn";
      case 1:
         return this.getOp();
      case 2:
         return this.name;
      default:
         throw new IndexOutOfBoundsException(String.valueOf(i));
      }
   }

   public CallSiteDescriptor changeMethodType(MethodType newMethodType) {
      return CallSiteDescriptorFactory.getCanonicalPublicDescriptor(new NamedDynCallSiteDescriptor(this.getOp(), this.name, newMethodType));
   }
}
