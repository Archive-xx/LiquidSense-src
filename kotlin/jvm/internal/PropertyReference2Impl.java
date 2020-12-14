package kotlin.jvm.internal;

import kotlin.reflect.KDeclarationContainer;

public class PropertyReference2Impl extends PropertyReference2 {
   private final KDeclarationContainer owner;
   private final String name;
   private final String signature;

   public PropertyReference2Impl(KDeclarationContainer owner, String name, String signature) {
      this.owner = owner;
      this.name = name;
      this.signature = signature;
   }

   public KDeclarationContainer getOwner() {
      return this.owner;
   }

   public String getName() {
      return this.name;
   }

   public String getSignature() {
      return this.signature;
   }

   public Object get(Object receiver1, Object receiver2) {
      return this.getGetter().call(new Object[]{receiver1, receiver2});
   }
}
