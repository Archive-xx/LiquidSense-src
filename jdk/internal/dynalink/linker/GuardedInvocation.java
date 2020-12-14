package jdk.internal.dynalink.linker;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.SwitchPoint;
import java.lang.invoke.WrongMethodTypeException;
import java.util.List;
import java.util.Objects;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.support.Guards;
import jdk.nashorn.internal.lookup.Lookup;

public class GuardedInvocation {
   private final MethodHandle invocation;
   private final MethodHandle guard;
   private final Class<? extends Throwable> exception;
   private final SwitchPoint[] switchPoints;

   public GuardedInvocation(MethodHandle invocation) {
      this(invocation, (MethodHandle)null, (SwitchPoint)((SwitchPoint)null), (Class)null);
   }

   public GuardedInvocation(MethodHandle invocation, MethodHandle guard) {
      this(invocation, guard, (SwitchPoint)((SwitchPoint)null), (Class)null);
   }

   public GuardedInvocation(MethodHandle invocation, SwitchPoint switchPoint) {
      this(invocation, (MethodHandle)null, (SwitchPoint)switchPoint, (Class)null);
   }

   public GuardedInvocation(MethodHandle invocation, MethodHandle guard, SwitchPoint switchPoint) {
      this(invocation, guard, (SwitchPoint)switchPoint, (Class)null);
   }

   public GuardedInvocation(MethodHandle invocation, MethodHandle guard, SwitchPoint switchPoint, Class<? extends Throwable> exception) {
      this.invocation = (MethodHandle)Objects.requireNonNull(invocation);
      this.guard = guard;
      this.switchPoints = switchPoint == null ? null : new SwitchPoint[]{switchPoint};
      this.exception = exception;
   }

   public GuardedInvocation(MethodHandle invocation, MethodHandle guard, SwitchPoint[] switchPoints, Class<? extends Throwable> exception) {
      this.invocation = (MethodHandle)Objects.requireNonNull(invocation);
      this.guard = guard;
      this.switchPoints = switchPoints == null ? null : (SwitchPoint[])switchPoints.clone();
      this.exception = exception;
   }

   public MethodHandle getInvocation() {
      return this.invocation;
   }

   public MethodHandle getGuard() {
      return this.guard;
   }

   public SwitchPoint[] getSwitchPoints() {
      return this.switchPoints == null ? null : (SwitchPoint[])this.switchPoints.clone();
   }

   public Class<? extends Throwable> getException() {
      return this.exception;
   }

   public boolean hasBeenInvalidated() {
      if (this.switchPoints == null) {
         return false;
      } else {
         SwitchPoint[] var1 = this.switchPoints;
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            SwitchPoint sp = var1[var3];
            if (sp.hasBeenInvalidated()) {
               return true;
            }
         }

         return false;
      }
   }

   public void assertType(MethodType type) {
      assertType(this.invocation, type);
      if (this.guard != null) {
         assertType(this.guard, type.changeReturnType(Boolean.TYPE));
      }

   }

   public GuardedInvocation replaceMethods(MethodHandle newInvocation, MethodHandle newGuard) {
      return new GuardedInvocation(newInvocation, newGuard, this.switchPoints, this.exception);
   }

   public GuardedInvocation addSwitchPoint(SwitchPoint newSwitchPoint) {
      if (newSwitchPoint == null) {
         return this;
      } else {
         SwitchPoint[] newSwitchPoints;
         if (this.switchPoints != null) {
            newSwitchPoints = new SwitchPoint[this.switchPoints.length + 1];
            System.arraycopy(this.switchPoints, 0, newSwitchPoints, 0, this.switchPoints.length);
            newSwitchPoints[this.switchPoints.length] = newSwitchPoint;
         } else {
            newSwitchPoints = new SwitchPoint[]{newSwitchPoint};
         }

         return new GuardedInvocation(this.invocation, this.guard, newSwitchPoints, this.exception);
      }
   }

   private GuardedInvocation replaceMethodsOrThis(MethodHandle newInvocation, MethodHandle newGuard) {
      return newInvocation == this.invocation && newGuard == this.guard ? this : this.replaceMethods(newInvocation, newGuard);
   }

   public GuardedInvocation asType(MethodType newType) {
      return this.replaceMethodsOrThis(this.invocation.asType(newType), this.guard == null ? null : Guards.asType(this.guard, newType));
   }

   public GuardedInvocation asType(LinkerServices linkerServices, MethodType newType) {
      return this.replaceMethodsOrThis(linkerServices.asType(this.invocation, newType), this.guard == null ? null : Guards.asType(linkerServices, this.guard, newType));
   }

   public GuardedInvocation asTypeSafeReturn(LinkerServices linkerServices, MethodType newType) {
      return this.replaceMethodsOrThis(linkerServices.asTypeLosslessReturn(this.invocation, newType), this.guard == null ? null : Guards.asType(linkerServices, this.guard, newType));
   }

   public GuardedInvocation asType(CallSiteDescriptor desc) {
      return this.asType(desc.getMethodType());
   }

   public GuardedInvocation filterArguments(int pos, MethodHandle... filters) {
      return this.replaceMethods(MethodHandles.filterArguments(this.invocation, pos, filters), this.guard == null ? null : MethodHandles.filterArguments(this.guard, pos, filters));
   }

   public GuardedInvocation dropArguments(int pos, List<Class<?>> valueTypes) {
      return this.replaceMethods(MethodHandles.dropArguments(this.invocation, pos, valueTypes), this.guard == null ? null : MethodHandles.dropArguments(this.guard, pos, valueTypes));
   }

   public GuardedInvocation dropArguments(int pos, Class<?>... valueTypes) {
      return this.replaceMethods(MethodHandles.dropArguments(this.invocation, pos, valueTypes), this.guard == null ? null : MethodHandles.dropArguments(this.guard, pos, valueTypes));
   }

   public MethodHandle compose(MethodHandle fallback) {
      return this.compose(fallback, fallback, fallback);
   }

   public MethodHandle compose(MethodHandle guardFallback, MethodHandle switchpointFallback, MethodHandle catchFallback) {
      MethodHandle guarded = this.guard == null ? this.invocation : MethodHandles.guardWithTest(this.guard, this.invocation, guardFallback);
      MethodHandle catchGuarded = this.exception == null ? guarded : Lookup.MH.catchException(guarded, this.exception, MethodHandles.dropArguments(catchFallback, 0, new Class[]{this.exception}));
      if (this.switchPoints == null) {
         return catchGuarded;
      } else {
         MethodHandle spGuarded = catchGuarded;
         SwitchPoint[] var7 = this.switchPoints;
         int var8 = var7.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            SwitchPoint sp = var7[var9];
            spGuarded = sp.guardWithTest(spGuarded, switchpointFallback);
         }

         return spGuarded;
      }
   }

   private static void assertType(MethodHandle mh, MethodType type) {
      if (!mh.type().equals(type)) {
         throw new WrongMethodTypeException("Expected type: " + type + " actual type: " + mh.type());
      }
   }
}
