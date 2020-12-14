package jdk.nashorn.internal.ir;

import jdk.nashorn.internal.codegen.types.Type;

public interface Optimistic {
   int getProgramPoint();

   Optimistic setProgramPoint(int var1);

   boolean canBeOptimistic();

   Type getMostOptimisticType();

   Type getMostPessimisticType();

   Optimistic setType(Type var1);
}
