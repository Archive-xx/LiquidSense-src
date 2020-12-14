package jdk.nashorn.internal.runtime.arrays;

import jdk.nashorn.internal.runtime.ScriptObject;

final class ReverseScriptArrayIterator extends ScriptArrayIterator {
   public ReverseScriptArrayIterator(ScriptObject array, boolean includeUndefined) {
      super(array, includeUndefined);
      this.index = array.getArray().length() - 1L;
   }

   public boolean isReverse() {
      return true;
   }

   protected boolean indexInArray() {
      return this.index >= 0L;
   }

   protected long bumpIndex() {
      return (long)(this.index--);
   }
}
