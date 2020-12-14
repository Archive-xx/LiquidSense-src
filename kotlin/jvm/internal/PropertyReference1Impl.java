package kotlin.jvm.internal;

import kotlin.reflect.KDeclarationContainer;

public class PropertyReference1Impl extends PropertyReference1 {
   private final KDeclarationContainer owner;
   private final String name;
   private final String signature;

   public PropertyReference1Impl(KDeclarationContainer owner, String name, String signature) {
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

   public Object get(Object receiver) {
      return this.getGetter().call(new Object[]{receiver});
   }
}
