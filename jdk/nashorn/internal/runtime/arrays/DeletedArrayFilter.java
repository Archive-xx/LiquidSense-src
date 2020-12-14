package jdk.nashorn.internal.runtime.arrays;

import java.lang.reflect.Array;
import jdk.nashorn.internal.runtime.BitVector;
import jdk.nashorn.internal.runtime.ScriptRuntime;

final class DeletedArrayFilter extends ArrayFilter {
   private final BitVector deleted;

   DeletedArrayFilter(ArrayData underlying) {
      super(underlying);
      this.deleted = new BitVector(underlying.length());
   }

   public ArrayData copy() {
      DeletedArrayFilter copy = new DeletedArrayFilter(this.underlying.copy());
      copy.getDeleted().copy(this.deleted);
      return copy;
   }

   public Object[] asObjectArray() {
      Object[] value = super.asObjectArray();

      for(int i = 0; i < value.length; ++i) {
         if (this.deleted.isSet((long)i)) {
            value[i] = ScriptRuntime.UNDEFINED;
         }
      }

      return value;
   }

   public Object asArrayOfType(Class<?> componentType) {
      Object value = super.asArrayOfType(componentType);
      Object undefValue = convertUndefinedValue(componentType);
      int l = Array.getLength(value);

      for(int i = 0; i < l; ++i) {
         if (this.deleted.isSet((long)i)) {
            Array.set(value, i, undefValue);
         }
      }

      return value;
   }

   public ArrayData shiftLeft(int by) {
      super.shiftLeft(by);
      this.deleted.shiftLeft((long)by, this.length());
      return this;
   }

   public ArrayData shiftRight(int by) {
      super.shiftRight(by);
      this.deleted.shiftRight((long)by, this.length());
      return this;
   }

   public ArrayData ensure(long safeIndex) {
      if (safeIndex >= 131072L && safeIndex >= this.length()) {
         return new SparseArrayData(this, safeIndex + 1L);
      } else {
         super.ensure(safeIndex);
         this.deleted.resize(this.length());
         return this;
      }
   }

   public ArrayData shrink(long newLength) {
      super.shrink(newLength);
      this.deleted.resize(this.length());
      return this;
   }

   public ArrayData set(int index, Object value, boolean strict) {
      this.deleted.clear(ArrayIndex.toLongIndex(index));
      return super.set(index, value, strict);
   }

   public ArrayData set(int index, int value, boolean strict) {
      this.deleted.clear(ArrayIndex.toLongIndex(index));
      return super.set(index, value, strict);
   }

   public ArrayData set(int index, double value, boolean strict) {
      this.deleted.clear(ArrayIndex.toLongIndex(index));
      return super.set(index, value, strict);
   }

   public boolean has(int index) {
      return super.has(index) && this.deleted.isClear(ArrayIndex.toLongIndex(index));
   }

   public ArrayData delete(int index) {
      long longIndex = ArrayIndex.toLongIndex(index);

      assert longIndex >= 0L && longIndex < this.length();

      this.deleted.set(longIndex);
      this.underlying.setEmpty(index);
      return this;
   }

   public ArrayData delete(long fromIndex, long toIndex) {
      assert fromIndex >= 0L && fromIndex <= toIndex && toIndex < this.length();

      this.deleted.setRange(fromIndex, toIndex + 1L);
      this.underlying.setEmpty(fromIndex, toIndex);
      return this;
   }

   public Object pop() {
      long index = this.length() - 1L;
      if (super.has((int)index)) {
         boolean isDeleted = this.deleted.isSet(index);
         Object value = super.pop();
         return isDeleted ? ScriptRuntime.UNDEFINED : value;
      } else {
         return super.pop();
      }
   }

   public ArrayData slice(long from, long to) {
      ArrayData newArray = this.underlying.slice(from, to);
      DeletedArrayFilter newFilter = new DeletedArrayFilter(newArray);
      newFilter.getDeleted().copy(this.deleted);
      newFilter.getDeleted().shiftLeft(from, newFilter.length());
      return newFilter;
   }

   private BitVector getDeleted() {
      return this.deleted;
   }
}
