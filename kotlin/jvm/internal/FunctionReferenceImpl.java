package kotlin.jvm.internal;

import kotlin.reflect.KDeclarationContainer;

public class FunctionReferenceImpl extends FunctionReference {
   private final KDeclarationContainer owner;
   private final String name;
   private final String signature;

   public FunctionReferenceImpl(int arity, KDeclarationContainer owner, String name, String signature) {
      super(arity);
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
}
