package jdk.nashorn.internal.runtime.arrays;

import java.util.NoSuchElementException;

final class EmptyArrayLikeIterator extends ArrayLikeIterator<Object> {
   EmptyArrayLikeIterator() {
      super(false);
   }

   public boolean hasNext() {
      return false;
   }

   public Object next() {
      throw new NoSuchElementException();
   }

   public long getLength() {
      return 0L;
   }
}
