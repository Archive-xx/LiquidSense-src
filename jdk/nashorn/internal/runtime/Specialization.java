package jdk.nashorn.internal.runtime;

import java.lang.invoke.MethodHandle;
import jdk.nashorn.internal.objects.annotations.SpecializedFunction;

public final class Specialization {
   private final MethodHandle mh;
   private final Class<? extends SpecializedFunction.LinkLogic> linkLogicClass;
   private final boolean isOptimistic;

   public Specialization(MethodHandle mh) {
      this(mh, false);
   }

   public Specialization(MethodHandle mh, boolean isOptimistic) {
      this(mh, (Class)null, isOptimistic);
   }

   public Specialization(MethodHandle mh, Class<? extends SpecializedFunction.LinkLogic> linkLogicClass, boolean isOptimistic) {
      this.mh = mh;
      this.isOptimistic = isOptimistic;
      if (linkLogicClass != null) {
         this.linkLogicClass = SpecializedFunction.LinkLogic.isEmpty(linkLogicClass) ? null : linkLogicClass;
      } else {
         this.linkLogicClass = null;
      }

   }

   public MethodHandle getMethodHandle() {
      return this.mh;
   }

   public Class<? extends SpecializedFunction.LinkLogic> getLinkLogicClass() {
      return this.linkLogicClass;
   }

   public boolean isOptimistic() {
      return this.isOptimistic;
   }
}
