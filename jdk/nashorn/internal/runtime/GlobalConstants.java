package jdk.nashorn.internal.runtime;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.SwitchPoint;
import java.lang.invoke.MethodHandles.Lookup;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.DynamicLinker;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.lookup.MethodHandleFactory;
import jdk.nashorn.internal.runtime.linker.NashornCallSiteDescriptor;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import jdk.nashorn.internal.runtime.logging.Loggable;
import jdk.nashorn.internal.runtime.logging.Logger;

@Logger(
   name = "const"
)
public final class GlobalConstants implements Loggable {
   public static final boolean GLOBAL_ONLY = true;
   private static final Lookup LOOKUP = MethodHandles.lookup();
   private static final MethodHandle INVALIDATE_SP;
   private static final MethodHandle RECEIVER_GUARD;
   private final DebugLogger log;
   private final Map<String, GlobalConstants.Access> map = new HashMap();
   private final AtomicBoolean invalidatedForever = new AtomicBoolean(false);

   public GlobalConstants(DebugLogger log) {
      this.log = log == null ? DebugLogger.DISABLED_LOGGER : log;
   }

   public DebugLogger getLogger() {
      return this.log;
   }

   public DebugLogger initLogger(Context context) {
      return DebugLogger.DISABLED_LOGGER;
   }

   public void invalidateAll() {
      if (!this.invalidatedForever.get()) {
         this.log.info("New global created - invalidating all constant callsites without increasing invocation count.");
         synchronized(this) {
            Iterator var2 = this.map.values().iterator();

            while(var2.hasNext()) {
               GlobalConstants.Access acc = (GlobalConstants.Access)var2.next();
               acc.invalidateUncounted();
            }
         }
      }

   }

   public void invalidateForever() {
      if (this.invalidatedForever.compareAndSet(false, true)) {
         this.log.info("New global created - invalidating all constant callsites.");
         synchronized(this) {
            Iterator var2 = this.map.values().iterator();

            while(var2.hasNext()) {
               GlobalConstants.Access acc = (GlobalConstants.Access)var2.next();
               acc.invalidateForever();
            }

            this.map.clear();
         }
      }

   }

   private synchronized Object invalidateSwitchPoint(Object obj, GlobalConstants.Access acc) {
      if (this.log.isEnabled()) {
         this.log.info("*** Invalidating switchpoint " + acc.getSwitchPoint() + " for receiver=" + obj + " access=" + acc);
      }

      acc.invalidateOnce();
      if (acc.mayRetry()) {
         if (this.log.isEnabled()) {
            this.log.info("Retry is allowed for " + acc + "... Creating a new switchpoint.");
         }

         acc.newSwitchPoint();
      } else if (this.log.isEnabled()) {
         this.log.info("This was the last time I allowed " + DebugLogger.quote(acc.getName()) + " to relink as constant.");
      }

      return obj;
   }

   private GlobalConstants.Access getOrCreateSwitchPoint(String name) {
      GlobalConstants.Access acc = (GlobalConstants.Access)this.map.get(name);
      if (acc != null) {
         return acc;
      } else {
         SwitchPoint sp = new SwitchPoint();
         this.map.put(name, acc = new GlobalConstants.Access(name, sp));
         return acc;
      }
   }

   void delete(String name) {
      if (!this.invalidatedForever.get()) {
         synchronized(this) {
            GlobalConstants.Access acc = (GlobalConstants.Access)this.map.get(name);
            if (acc != null) {
               acc.invalidateForever();
            }
         }
      }

   }

   private static boolean receiverGuard(GlobalConstants.Access acc, Object boundReceiver, Object receiver) {
      boolean id = receiver == boundReceiver;
      if (!id) {
         acc.failGuard();
      }

      return id;
   }

   private static boolean isGlobalSetter(ScriptObject receiver, FindProperty find) {
      return find == null ? receiver.isScope() : find.getOwner().isGlobal();
   }

   GuardedInvocation findSetMethod(FindProperty find, ScriptObject receiver, GuardedInvocation inv, CallSiteDescriptor desc, LinkRequest request) {
      if (!this.invalidatedForever.get() && isGlobalSetter(receiver, find)) {
         String name = desc.getNameToken(2);
         synchronized(this) {
            GlobalConstants.Access acc = this.getOrCreateSwitchPoint(name);
            if (this.log.isEnabled()) {
               this.log.fine("Trying to link constant SETTER ", acc);
            }

            if (acc.mayRetry() && !this.invalidatedForever.get()) {
               if (acc.hasBeenInvalidated()) {
                  this.log.info("New chance for " + acc);
                  acc.newSwitchPoint();
               }

               assert !acc.hasBeenInvalidated();

               MethodHandle target = inv.getInvocation();
               Class<?> receiverType = target.type().parameterType(0);
               MethodHandle boundInvalidator = jdk.nashorn.internal.lookup.Lookup.MH.bindTo(INVALIDATE_SP, this);
               MethodHandle invalidator = jdk.nashorn.internal.lookup.Lookup.MH.asType(boundInvalidator, boundInvalidator.type().changeParameterType(0, receiverType).changeReturnType(receiverType));
               MethodHandle mh = jdk.nashorn.internal.lookup.Lookup.MH.filterArguments(inv.getInvocation(), 0, jdk.nashorn.internal.lookup.Lookup.MH.insertArguments(invalidator, 1, acc));

               assert inv.getSwitchPoints() == null : Arrays.asList(inv.getSwitchPoints());

               this.log.info("Linked setter " + DebugLogger.quote(name) + " " + acc.getSwitchPoint());
               return new GuardedInvocation(mh, inv.getGuard(), acc.getSwitchPoint(), inv.getException());
            } else {
               if (this.log.isEnabled()) {
                  this.log.fine("*** SET: Giving up on " + DebugLogger.quote(name) + " - retry count has exceeded " + DynamicLinker.getLinkedCallSiteLocation());
               }

               return null;
            }
         }
      } else {
         return null;
      }
   }

   public static MethodHandle staticConstantGetter(Object c) {
      return jdk.nashorn.internal.lookup.Lookup.MH.dropArguments(JSType.unboxConstant(c), 0, (Class[])(Object.class));
   }

   private MethodHandle constantGetter(Object c) {
      MethodHandle mh = staticConstantGetter(c);
      return this.log.isEnabled() ? MethodHandleFactory.addDebugPrintout(this.log, Level.FINEST, mh, "getting as constant") : mh;
   }

   GuardedInvocation findGetMethod(FindProperty find, ScriptObject receiver, CallSiteDescriptor desc) {
      if (!this.invalidatedForever.get() && NashornCallSiteDescriptor.isFastScope(desc) && find.getOwner().isGlobal() && !(find.getProperty() instanceof UserAccessorProperty)) {
         boolean isOptimistic = NashornCallSiteDescriptor.isOptimistic(desc);
         int programPoint = isOptimistic ? NashornCallSiteDescriptor.getProgramPoint(desc) : -1;
         Class<?> retType = desc.getMethodType().returnType();
         String name = desc.getNameToken(2);
         synchronized(this) {
            GlobalConstants.Access acc = this.getOrCreateSwitchPoint(name);
            this.log.fine("Starting to look up object value " + name);
            Object c = find.getObjectValue();
            if (this.log.isEnabled()) {
               this.log.fine("Trying to link constant GETTER " + acc + " value = " + c);
            }

            if (!acc.hasBeenInvalidated() && !acc.guardFailed() && !this.invalidatedForever.get()) {
               MethodHandle cmh = this.constantGetter(c);
               MethodHandle mh;
               if (isOptimistic) {
                  if (JSType.getAccessorTypeIndex(cmh.type().returnType()) <= JSType.getAccessorTypeIndex(retType)) {
                     mh = jdk.nashorn.internal.lookup.Lookup.MH.asType(cmh, cmh.type().changeReturnType(retType));
                  } else {
                     mh = jdk.nashorn.internal.lookup.Lookup.MH.dropArguments(jdk.nashorn.internal.lookup.Lookup.MH.insertArguments(JSType.THROW_UNWARRANTED.methodHandle(), 0, c, programPoint), 0, (Class[])(Object.class));
                  }
               } else {
                  mh = jdk.nashorn.internal.lookup.Lookup.filterReturnType(cmh, retType);
               }

               MethodHandle guard;
               if (find.getOwner().isGlobal()) {
                  guard = null;
               } else {
                  guard = jdk.nashorn.internal.lookup.Lookup.MH.insertArguments(RECEIVER_GUARD, 0, acc, receiver);
               }

               if (this.log.isEnabled()) {
                  this.log.info("Linked getter " + DebugLogger.quote(name) + " as MethodHandle.constant() -> " + c + " " + acc.getSwitchPoint());
                  mh = MethodHandleFactory.addDebugPrintout(this.log, Level.FINE, mh, "get const " + acc);
               }

               return new GuardedInvocation(mh, guard, acc.getSwitchPoint(), (Class)null);
            } else {
               if (this.log.isEnabled()) {
                  this.log.info("*** GET: Giving up on " + DebugLogger.quote(name) + " - retry count has exceeded " + DynamicLinker.getLinkedCallSiteLocation());
               }

               return null;
            }
         }
      } else {
         return null;
      }
   }

   static {
      INVALIDATE_SP = CompilerConstants.virtualCall(LOOKUP, GlobalConstants.class, "invalidateSwitchPoint", Object.class, Object.class, GlobalConstants.Access.class).methodHandle();
      RECEIVER_GUARD = CompilerConstants.staticCall(LOOKUP, GlobalConstants.class, "receiverGuard", Boolean.TYPE, GlobalConstants.Access.class, Object.class, Object.class).methodHandle();
   }

   private static class Access {
      private final String name;
      private SwitchPoint sp;
      private int invalidations;
      private boolean guardFailed;
      private static final int MAX_RETRIES = 2;

      private Access(String name, SwitchPoint sp) {
         this.name = name;
         this.sp = sp;
      }

      private boolean hasBeenInvalidated() {
         return this.sp.hasBeenInvalidated();
      }

      private boolean guardFailed() {
         return this.guardFailed;
      }

      private void failGuard() {
         this.invalidateOnce();
         this.guardFailed = true;
      }

      private void newSwitchPoint() {
         assert this.hasBeenInvalidated();

         this.sp = new SwitchPoint();
      }

      private void invalidate(int count) {
         if (!this.sp.hasBeenInvalidated()) {
            SwitchPoint.invalidateAll(new SwitchPoint[]{this.sp});
            this.invalidations += count;
         }

      }

      private void invalidateUncounted() {
         this.invalidate(0);
      }

      private void invalidateOnce() {
         this.invalidate(1);
      }

      private void invalidateForever() {
         this.invalidate(2);
      }

      private boolean mayRetry() {
         return this.invalidations < 2;
      }

      public String toString() {
         return "[" + DebugLogger.quote(this.name) + " <id=" + Debug.id(this) + "> inv#=" + this.invalidations + '/' + 2 + " sp_inv=" + this.sp.hasBeenInvalidated() + ']';
      }

      String getName() {
         return this.name;
      }

      SwitchPoint getSwitchPoint() {
         return this.sp;
      }

      // $FF: synthetic method
      Access(String x0, SwitchPoint x1, Object x2) {
         this(x0, x1);
      }
   }
}
