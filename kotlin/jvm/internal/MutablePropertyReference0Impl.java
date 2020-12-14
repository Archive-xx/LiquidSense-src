package kotlin.jvm.internal;

import kotlin.reflect.KDeclarationContainer;

public class MutablePropertyReference0Impl extends MutablePropertyReference0 {
   private final KDeclarationContainer owner;
   private final String name;
   private final String signature;

   public MutablePropertyReference0Impl(KDeclarationContainer owner, String name, String signature) {
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

   public Object get() {
      return this.getGetter().call(new Object[0]);
   }

   public void set(Object value) {
      this.getSetter().call(new Object[]{value});
   }
}
