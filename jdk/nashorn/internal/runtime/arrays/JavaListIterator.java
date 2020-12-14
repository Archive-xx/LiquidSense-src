package jdk.nashorn.internal.runtime.arrays;

import java.util.List;

class JavaListIterator extends ArrayLikeIterator<Object> {
   protected final List<?> list;
   protected final long length;

   protected JavaListIterator(List<?> list, boolean includeUndefined) {
      super(includeUndefined);
      this.list = list;
      this.length = (long)list.size();
   }

   protected boolean indexInArray() {
      return this.index < this.length;
   }

   public Object next() {
      return this.list.get((int)this.bumpIndex());
   }

   public long getLength() {
      return this.length;
   }

   public boolean hasNext() {
      return this.indexInArray();
   }

   public void remove() {
      this.list.remove(this.index);
   }
}
