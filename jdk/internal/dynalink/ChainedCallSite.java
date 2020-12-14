package jdk.internal.dynalink;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicReference;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.support.AbstractRelinkableCallSite;
import jdk.internal.dynalink.support.Lookup;

public class ChainedCallSite extends AbstractRelinkableCallSite {
   private static final MethodHandle PRUNE_CATCHES;
   private static final MethodHandle PRUNE_SWITCHPOINTS;
   private final AtomicReference<LinkedList<GuardedInvocation>> invocations = new AtomicReference();

   public ChainedCallSite(CallSiteDescriptor descriptor) {
      super(descriptor);
   }

   protected int getMaxChainLength() {
      return 8;
   }

   public void relink(GuardedInvocation guardedInvocation, MethodHandle fallback) {
      this.relinkInternal(guardedInvocation, fallback, false, false);
   }

   public void resetAndRelink(GuardedInvocation guardedInvocation, MethodHandle fallback) {
      this.relinkInternal(guardedInvocation, fallback, true, false);
   }

   private MethodHandle relinkInternal(GuardedInvocation invocation, MethodHandle relink, boolean reset, boolean removeCatches) {
      LinkedList<GuardedInvocation> currentInvocations = (LinkedList)this.invocations.get();
      LinkedList<GuardedInvocation> newInvocations = currentInvocations != null && !reset ? (LinkedList)currentInvocations.clone() : new LinkedList();
      Iterator it = newInvocations.iterator();

      while(true) {
         GuardedInvocation inv;
         do {
            if (!it.hasNext()) {
               if (invocation != null) {
                  if (newInvocations.size() == this.getMaxChainLength()) {
                     newInvocations.removeFirst();
                  }

                  newInvocations.addLast(invocation);
               }

               MethodHandle pruneAndInvokeSwitchPoints = this.makePruneAndInvokeMethod(relink, this.getPruneSwitchpoints());
               MethodHandle pruneAndInvokeCatches = this.makePruneAndInvokeMethod(relink, this.getPruneCatches());
               MethodHandle target = relink;

               GuardedInvocation inv;
               for(Iterator var10 = newInvocations.iterator(); var10.hasNext(); target = inv.compose(target, pruneAndInvokeSwitchPoints, pruneAndInvokeCatches)) {
                  inv = (GuardedInvocation)var10.next();
               }

               if (this.invocations.compareAndSet(currentInvocations, newInvocations)) {
                  this.setTarget(target);
               }

               return target;
            }

            inv = (GuardedInvocation)it.next();
         } while(!inv.hasBeenInvalidated() && (!removeCatches || inv.getException() == null));

         it.remove();
      }
   }

   protected MethodHandle getPruneSwitchpoints() {
      return PRUNE_SWITCHPOINTS;
   }

   protected MethodHandle getPruneCatches() {
      return PRUNE_CATCHES;
   }

   private MethodHandle makePruneAndInvokeMethod(MethodHandle relink, MethodHandle prune) {
      MethodHandle boundPrune = MethodHandles.insertArguments(prune, 0, new Object[]{this, relink});
      MethodHandle ignoreArgsPrune = MethodHandles.dropArguments(boundPrune, 0, this.type().parameterList());
      return MethodHandles.foldArguments(MethodHandles.exactInvoker(this.type()), ignoreArgsPrune);
   }

   private MethodHandle prune(MethodHandle relink, boolean catches) {
      return this.relinkInternal((GuardedInvocation)null, relink, false, catches);
   }

   static {
      PRUNE_CATCHES = MethodHandles.insertArguments(Lookup.findOwnSpecial(MethodHandles.lookup(), "prune", MethodHandle.class, MethodHandle.class, Boolean.TYPE), 2, new Object[]{true});
      PRUNE_SWITCHPOINTS = MethodHandles.insertArguments(Lookup.findOwnSpecial(MethodHandles.lookup(), "prune", MethodHandle.class, MethodHandle.class, Boolean.TYPE), 2, new Object[]{false});
   }
}
