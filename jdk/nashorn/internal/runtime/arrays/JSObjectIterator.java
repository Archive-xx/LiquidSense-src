package jdk.nashorn.internal.runtime.arrays;

import java.util.NoSuchElementException;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.internal.runtime.JSType;

class JSObjectIterator extends ArrayLikeIterator<Object> {
   protected final JSObject obj;
   private final long length;

   JSObjectIterator(JSObject obj, boolean includeUndefined) {
      super(includeUndefined);
      this.obj = obj;
      this.length = JSType.toUint32(obj.hasMember("length") ? obj.getMember("length") : 0);
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
         while(this.indexInArray() && !this.obj.hasSlot((int)this.index) && !this.includeUndefined) {
            this.bumpIndex();
         }

         return this.indexInArray();
      }
   }

   public Object next() {
      if (this.indexInArray()) {
         return this.obj.getSlot((int)this.bumpIndex());
      } else {
         throw new NoSuchElementException();
      }
   }
}
