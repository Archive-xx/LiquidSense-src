package jdk.nashorn.internal.runtime.arrays;

import java.util.NoSuchElementException;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptObject;

class ScriptObjectIterator extends ArrayLikeIterator<Object> {
   protected final ScriptObject obj;
   private final long length;

   ScriptObjectIterator(ScriptObject obj, boolean includeUndefined) {
      super(includeUndefined);
      this.obj = obj;
      this.length = JSType.toUint32(obj.getLength());
      this.index = 0L;
   }

   protected boolean indexInArray() {
      return this.index < this.length;
   }

   public long getLength() {
      return this.length;
   }

   public boolean hasNext() {
      if (this.length == 0L) {
         return false;
      } else {
         while(this.indexInArray() && !this.obj.has((double)this.index) && !this.includeUndefined) {
            this.bumpIndex();
         }

         return this.indexInArray();
      }
   }

   public Object next() {
      if (this.indexInArray()) {
         return this.obj.get((double)this.bumpIndex());
      } else {
         throw new NoSuchElementException();
      }
   }
}
