package jdk.nashorn.internal.runtime;

import java.util.concurrent.atomic.LongAdder;
import jdk.nashorn.internal.codegen.CompilerConstants;

public class Scope extends ScriptObject {
   private int splitState = -1;
   private static final LongAdder count;
   public static final CompilerConstants.Call GET_SPLIT_STATE;
   public static final CompilerConstants.Call SET_SPLIT_STATE;

   public Scope(PropertyMap map) {
      super(map);
      incrementCount();
   }

   public Scope(ScriptObject proto, PropertyMap map) {
      super(proto, map);
      incrementCount();
   }

   public Scope(PropertyMap map, long[] primitiveSpill, Object[] objectSpill) {
      super(map, primitiveSpill, objectSpill);
      incrementCount();
   }

   public boolean isScope() {
      return true;
   }

   boolean hasWithScope() {
      for(Object obj = this; obj != null; obj = ((ScriptObject)obj).getProto()) {
         if (obj instanceof WithObject) {
            return true;
         }
      }

      return false;
   }

   public int getSplitState() {
      return this.splitState;
   }

   public void setSplitState(int state) {
      this.splitState = state;
   }

   public static long getScopeCount() {
      return count != null ? count.sum() : 0L;
   }

   private static void incrementCount() {
      if (Context.DEBUG) {
         count.increment();
      }

   }

   static {
      count = Context.DEBUG ? new LongAdder() : null;
      GET_SPLIT_STATE = CompilerConstants.virtualCallNoLookup(Scope.class, "getSplitState", Integer.TYPE);
      SET_SPLIT_STATE = CompilerConstants.virtualCallNoLookup(Scope.class, "setSplitState", Void.TYPE, Integer.TYPE);
   }
}
