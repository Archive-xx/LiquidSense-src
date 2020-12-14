package jdk.nashorn.internal.runtime.arrays;

import java.lang.reflect.Array;
import jdk.nashorn.internal.runtime.BitVector;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.UnwarrantedOptimismException;

final class UndefinedArrayFilter extends ArrayFilter {
   private final BitVector undefined;

   UndefinedArrayFilter(ArrayData underlying) {
      super(underlying);
      this.undefined = new BitVector(underlying.length());
   }

   public ArrayData copy() {
      UndefinedArrayFilter copy = new UndefinedArrayFilter(this.underlying.copy());
      copy.getUndefined().copy(this.undefined);
      return copy;
   }

   public Object[] asObjectArray() {
      Object[] value = super.asObjectArray();

      for(int i = 0; i < value.length; ++i) {
         if (this.undefined.isSet((long)i)) {
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
         if (this.undefined.isSet((long)i)) {
            Array.set(value, i, undefValue);
         }
      }

      return value;
   }

   public ArrayData shiftLeft(int by) {
      super.shiftLeft(by);
      this.undefined.shiftLeft((long)by, this.length());
      return this;
   }

   public ArrayData shiftRight(int by) {
      super.shiftRight(by);
      this.undefined.shiftRight((long)by, this.length());
      return this;
   }

   public ArrayData ensure(long safeIndex) {
      if (safeIndex >= 131072L && safeIndex >= this.length()) {
         return new SparseArrayData(this, safeIndex + 1L);
      } else {
         super.ensure(safeIndex);
         this.undefined.resize(this.length());
         return this;
      }
   }

   public ArrayData shrink(long newLength) {
      super.shrink(newLength);
      this.undefined.resize(this.length());
      return this;
   }

   public ArrayData set(int index, Object value, boolean strict) {
      this.undefined.clear((long)index);
      if (value == ScriptRuntime.UNDEFINED) {
         this.undefined.set((long)index);
         return this;
      } else {
         return super.set(index, value, strict);
      }
   }

   public ArrayData set(int index, int value, boolean strict) {
      this.undefined.clear((long)index);
      return super.set(index, value, strict);
   }

   public ArrayData set(int index, double value, boolean strict) {
      this.undefined.clear((long)index);
      return super.set(index, value, strict);
   }

   public int getInt(int index) {
      return this.undefined.isSet((long)index) ? 0 : super.getInt(index);
   }

   public int getIntOptimistic(int index, int programPoint) {
      if (this.undefined.isSet((long)index)) {
         throw new UnwarrantedOptimismException(ScriptRuntime.UNDEFINED, programPoint);
      } else {
         return super.getIntOptimistic(index, programPoint);
      }
   }

   public double getDouble(int index) {
      return this.undefined.isSet((long)index) ? Double.NaN : super.getDouble(index);
   }

   public double getDoubleOptimistic(int index, int programPoint) {
      if (this.undefined.isSet((long)index)) {
         throw new UnwarrantedOptimismException(ScriptRuntime.UNDEFINED, programPoint);
      } else {
         return super.getDoubleOptimistic(index, programPoint);
      }
   }

   public Object getObject(int index) {
      return this.undefined.isSet((long)index) ? ScriptRuntime.UNDEFINED : super.getObject(index);
   }

   public ArrayData delete(int index) {
      this.undefined.clear((long)index);
      return super.delete(index);
   }

   public Object pop() {
      long index = this.length() - 1L;
      if (super.has((int)index)) {
         boolean isUndefined = this.undefined.isSet(index);
         Object value = super.pop();
         return isUndefined ? ScriptRuntime.UNDEFINED : value;
      } else {
         return super.pop();
      }
   }

   public ArrayData slice(long from, long to) {
      ArrayData newArray = this.underlying.slice(from, to);
      UndefinedArrayFilter newFilter = new UndefinedArrayFilter(newArray);
      newFilter.getUndefined().copy(this.undefined);
      newFilter.getUndefined().shiftLeft(from, newFilter.length());
      return newFilter;
   }

   private BitVector getUndefined() {
      return this.undefined;
   }
}
