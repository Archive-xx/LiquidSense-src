package jdk.nashorn.internal.runtime.arrays;

import java.lang.reflect.Array;

class JavaArrayIterator extends ArrayLikeIterator<Object> {
   protected final Object array;
   protected final long length;

   protected JavaArrayIterator(Object array, boolean includeUndefined) {
      super(includeUndefined);

      assert array.getClass().isArray() : "expecting Java array object";

      this.array = array;
      this.length = (long)Array.getLength(array);
   }

   protected boolean indexInArray() {
      return this.index < this.length;
   }

   public Object next() {
      return Array.get(this.array, (int)this.bumpIndex());
   }

   public long getLength() {
      return this.length;
   }

   public boolean hasNext() {
      return this.indexInArray();
   }

   public void remove() {
      throw new UnsupportedOperationException("remove");
   }
}
