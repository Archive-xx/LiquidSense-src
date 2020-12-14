package jdk.nashorn.internal.runtime.arrays;

import jdk.nashorn.internal.runtime.ScriptObject;

class ScriptArrayIterator extends ArrayLikeIterator<Object> {
   protected final ScriptObject array;
   protected final long length;

   protected ScriptArrayIterator(ScriptObject array, boolean includeUndefined) {
      super(includeUndefined);
      this.array = array;
      this.length = array.getArray().length();
   }

   protected boolean indexInArray() {
      return this.index < this.length;
   }

   public Object next() {
      return this.array.get((double)this.bumpIndex());
   }

   public long getLength() {
      return this.length;
   }

   public boolean hasNext() {
      if (!this.includeUndefined) {
         while(this.indexInArray() && !this.array.has((double)this.index)) {
            this.bumpIndex();
         }
      }

      return this.indexInArray();
   }

   public void remove() {
      this.array.delete((double)this.index, false);
   }
}
