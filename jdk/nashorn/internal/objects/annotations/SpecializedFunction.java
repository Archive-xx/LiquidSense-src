package jdk.nashorn.internal.objects.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.invoke.MethodHandle;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.LinkRequest;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface SpecializedFunction {
   String name() default "";

   Class<?> linkLogic() default SpecializedFunction.LinkLogic.Empty.class;

   boolean isConstructor() default false;

   boolean isOptimistic() default false;

   public abstract static class LinkLogic {
      public static final SpecializedFunction.LinkLogic EMPTY_INSTANCE = new SpecializedFunction.LinkLogic.Empty();

      public static Class<? extends SpecializedFunction.LinkLogic> getEmptyLinkLogicClass() {
         return SpecializedFunction.LinkLogic.Empty.class;
      }

      public Class<? extends Throwable> getRelinkException() {
         return null;
      }

      public static boolean isEmpty(Class<? extends SpecializedFunction.LinkLogic> clazz) {
         return clazz == SpecializedFunction.LinkLogic.Empty.class;
      }

      public boolean isEmpty() {
         return false;
      }

      public abstract boolean canLink(Object var1, CallSiteDescriptor var2, LinkRequest var3);

      public boolean needsGuard(Object self) {
         return true;
      }

      public boolean needsGuard(Object self, Object... args) {
         return true;
      }

      public MethodHandle getGuard() {
         return null;
      }

      public boolean checkLinkable(Object self, CallSiteDescriptor desc, LinkRequest request) {
         return this.canLink(self, desc, request);
      }

      private static final class Empty extends SpecializedFunction.LinkLogic {
         private Empty() {
         }

         public boolean canLink(Object self, CallSiteDescriptor desc, LinkRequest request) {
            return true;
         }

         public boolean isEmpty() {
            return true;
         }

         // $FF: synthetic method
         Empty(Object x0) {
            this();
         }
      }
   }
}
