package jdk.nashorn.internal.runtime;

import jdk.nashorn.internal.objects.annotations.SpecializedFunction;

public interface OptimisticBuiltins {
   SpecializedFunction.LinkLogic getLinkLogic(Class<? extends SpecializedFunction.LinkLogic> var1);

   boolean hasPerInstanceAssumptions();
}
