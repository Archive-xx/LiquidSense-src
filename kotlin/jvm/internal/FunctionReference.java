package kotlin.jvm.internal;

import kotlin.SinceKotlin;
import kotlin.reflect.KCallable;
import kotlin.reflect.KFunction;

public class FunctionReference extends CallableReference implements FunctionBase, KFunction {
   private final int arity;

   public FunctionReference(int arity) {
      this.arity = arity;
   }

   @SinceKotlin(
      version = "1.1"
   )
   public FunctionReference(int arity, Object receiver) {
      super(receiver);
      this.arity = arity;
   }

   public int getArity() {
      return this.arity;
   }

   @SinceKotlin(
      version = "1.1"
   )
   protected KFunction getReflected() {
      return (KFunction)super.getReflected();
   }

   @SinceKotlin(
      version = "1.1"
   )
   protected KCallable computeReflected() {
      return Reflection.function(this);
   }

   @SinceKotlin(
      version = "1.1"
   )
   public boolean isInline() {
      return this.getReflected().isInline();
   }

   @SinceKotlin(
      version = "1.1"
   )
   public boolean isExternal() {
      return this.getReflected().isExternal();
   }

   @SinceKotlin(
      version = "1.1"
   )
   public boolean isOperator() {
      return this.getReflected().isOperator();
   }

   @SinceKotlin(
      version = "1.1"
   )
   public boolean isInfix() {
      return this.getReflected().isInfix();
   }

   @SinceKotlin(
      version = "1.1"
   )
   public boolean isSuspend() {
      return this.getReflected().isSuspend();
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (!(obj instanceof FunctionReference)) {
         return obj instanceof KFunction ? obj.equals(this.compute()) : false;
      } else {
         boolean var10000;
         label31: {
            FunctionReference other = (FunctionReference)obj;
            if (this.getOwner() == null) {
               if (other.getOwner() != null) {
                  break label31;
               }
            } else if (!this.getOwner().equals(other.getOwner())) {
               break label31;
            }

            if (this.getName().equals(other.getName()) && this.getSignature().equals(other.getSignature()) && Intrinsics.areEqual(this.getBoundReceiver(), other.getBoundReceiver())) {
               var10000 = true;
               return var10000;
            }
         }

         var10000 = false;
         return var10000;
      }
   }

   public int hashCode() {
      return ((this.getOwner() == null ? 0 : this.getOwner().hashCode() * 31) + this.getName().hashCode()) * 31 + this.getSignature().hashCode();
   }

   public String toString() {
      KCallable reflected = this.compute();
      if (reflected != this) {
         return reflected.toString();
      } else {
         return "<init>".equals(this.getName()) ? "constructor (Kotlin reflection is not available)" : "function " + this.getName() + " (Kotlin reflection is not available)";
      }
   }
}
