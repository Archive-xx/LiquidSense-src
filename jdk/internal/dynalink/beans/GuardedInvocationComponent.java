package jdk.internal.dynalink.beans;

import java.lang.invoke.MethodHandle;
import jdk.internal.dynalink.linker.GuardedInvocation;

class GuardedInvocationComponent {
   private final GuardedInvocation guardedInvocation;
   private final GuardedInvocationComponent.Validator validator;

   GuardedInvocationComponent(MethodHandle invocation) {
      this((MethodHandle)invocation, (MethodHandle)null, (GuardedInvocationComponent.ValidationType)GuardedInvocationComponent.ValidationType.NONE);
   }

   GuardedInvocationComponent(MethodHandle invocation, MethodHandle guard, GuardedInvocationComponent.ValidationType validationType) {
      this(invocation, guard, (Class)null, validationType);
   }

   GuardedInvocationComponent(MethodHandle invocation, MethodHandle guard, Class<?> validatorClass, GuardedInvocationComponent.ValidationType validationType) {
      this(invocation, guard, new GuardedInvocationComponent.Validator(validatorClass, validationType));
   }

   GuardedInvocationComponent(GuardedInvocation guardedInvocation, Class<?> validatorClass, GuardedInvocationComponent.ValidationType validationType) {
      this(guardedInvocation, new GuardedInvocationComponent.Validator(validatorClass, validationType));
   }

   GuardedInvocationComponent replaceInvocation(MethodHandle newInvocation) {
      return this.replaceInvocation(newInvocation, this.guardedInvocation.getGuard());
   }

   GuardedInvocationComponent replaceInvocation(MethodHandle newInvocation, MethodHandle newGuard) {
      return new GuardedInvocationComponent(this.guardedInvocation.replaceMethods(newInvocation, newGuard), this.validator);
   }

   private GuardedInvocationComponent(MethodHandle invocation, MethodHandle guard, GuardedInvocationComponent.Validator validator) {
      this(new GuardedInvocation(invocation, guard), validator);
   }

   private GuardedInvocationComponent(GuardedInvocation guardedInvocation, GuardedInvocationComponent.Validator validator) {
      this.guardedInvocation = guardedInvocation;
      this.validator = validator;
   }

   GuardedInvocation getGuardedInvocation() {
      return this.guardedInvocation;
   }

   Class<?> getValidatorClass() {
      return this.validator.validatorClass;
   }

   GuardedInvocationComponent.ValidationType getValidationType() {
      return this.validator.validationType;
   }

   GuardedInvocationComponent compose(MethodHandle compositeInvocation, MethodHandle otherGuard, Class<?> otherValidatorClass, GuardedInvocationComponent.ValidationType otherValidationType) {
      GuardedInvocationComponent.Validator compositeValidator = this.validator.compose(new GuardedInvocationComponent.Validator(otherValidatorClass, otherValidationType));
      MethodHandle compositeGuard = compositeValidator == this.validator ? this.guardedInvocation.getGuard() : otherGuard;
      return new GuardedInvocationComponent(compositeInvocation, compositeGuard, compositeValidator);
   }

   private static class Validator {
      final Class<?> validatorClass;
      final GuardedInvocationComponent.ValidationType validationType;

      Validator(Class<?> validatorClass, GuardedInvocationComponent.ValidationType validationType) {
         this.validatorClass = validatorClass;
         this.validationType = validationType;
      }

      GuardedInvocationComponent.Validator compose(GuardedInvocationComponent.Validator other) {
         if (other.validationType == GuardedInvocationComponent.ValidationType.NONE) {
            return this;
         } else {
            switch(this.validationType) {
            case INSTANCE_OF:
               switch(other.validationType) {
               case INSTANCE_OF:
                  if (this.isAssignableFrom(other)) {
                     return other;
                  } else {
                     if (other.isAssignableFrom(this)) {
                        return this;
                     }

                     throw new AssertionError("Incompatible composition " + this + " vs " + other);
                  }
               case EXACT_CLASS:
                  if (this.isAssignableFrom(other)) {
                     return other;
                  }

                  throw new AssertionError("Incompatible composition " + this + " vs " + other);
               case IS_ARRAY:
                  if (this.validatorClass.isArray()) {
                     return this;
                  }

                  throw new AssertionError("Incompatible composition " + this + " vs " + other);
               default:
                  throw new AssertionError();
               }
            case EXACT_CLASS:
               switch(other.validationType) {
               case INSTANCE_OF:
                  if (other.isAssignableFrom(this)) {
                     return this;
                  }

                  throw new AssertionError("Incompatible composition " + this + " vs " + other);
               case EXACT_CLASS:
                  if (this.validatorClass == other.validatorClass) {
                     return this;
                  }

                  throw new AssertionError("Incompatible composition " + this + " vs " + other);
               case IS_ARRAY:
                  if (this.validatorClass.isArray()) {
                     return this;
                  }

                  throw new AssertionError("Incompatible composition " + this + " vs " + other);
               default:
                  throw new AssertionError();
               }
            case IS_ARRAY:
               switch(other.validationType) {
               case INSTANCE_OF:
               case EXACT_CLASS:
                  if (other.validatorClass.isArray()) {
                     return other;
                  }

                  throw new AssertionError("Incompatible composition " + this + " vs " + other);
               case IS_ARRAY:
                  return this;
               default:
                  throw new AssertionError();
               }
            case NONE:
               return other;
            default:
               throw new AssertionError();
            }
         }
      }

      private boolean isAssignableFrom(GuardedInvocationComponent.Validator other) {
         return this.validatorClass.isAssignableFrom(other.validatorClass);
      }

      public String toString() {
         return "Validator[" + this.validationType + (this.validatorClass == null ? "" : " " + this.validatorClass.getName()) + "]";
      }
   }

   static enum ValidationType {
      NONE,
      INSTANCE_OF,
      EXACT_CLASS,
      IS_ARRAY;
   }
}
