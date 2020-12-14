package jdk.nashorn.internal.runtime.arrays;

import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.Undefined;
import jdk.nashorn.internal.runtime.linker.Bootstrap;

abstract class ArrayFilter extends ArrayData {
   protected ArrayData underlying;

   ArrayFilter(ArrayData underlying) {
      super(underlying.length());
      this.underlying = underlying;
   }

   protected ArrayData getUnderlying() {
      return this.underlying;
   }

   public void setLength(long length) {
      super.setLength(length);
      this.underlying.setLength(length);
   }

   public Object[] asObjectArray() {
      return this.underlying.asObjectArray();
   }

   public Object asArrayOfType(Class<?> componentType) {
      return this.underlying.asArrayOfType(componentType);
   }

   public ArrayData shiftLeft(int by) {
      this.underlying.shiftLeft(by);
      this.setLength(this.underlying.length());
      return this;
   }

   public ArrayData shiftRight(int by) {
      this.underlying = this.underlying.shiftRight(by);
      this.setLength(this.underlying.length());
      return this;
   }

   public ArrayData ensure(long safeIndex) {
      this.underlying = this.underlying.ensure(safeIndex);
      this.setLength(this.underlying.length());
      return this;
   }

   public ArrayData shrink(long newLength) {
      this.underlying = this.underlying.shrink(newLength);
      this.setLength(this.underlying.length());
      return this;
   }

   public ArrayData set(int index, Object value, boolean strict) {
      this.underlying = this.underlying.set(index, value, strict);
      this.setLength(this.underlying.length());
      return this;
   }

   public ArrayData set(int index, int value, boolean strict) {
      this.underlying = this.underlying.set(index, value, strict);
      this.setLength(this.underlying.length());
      return this;
   }

   public ArrayData set(int index, double value, boolean strict) {
      this.underlying = this.underlying.set(index, value, strict);
      this.setLength(this.underlying.length());
      return this;
   }

   public ArrayData setEmpty(int index) {
      this.underlying.setEmpty(index);
      return this;
   }

   public ArrayData setEmpty(long lo, long hi) {
      this.underlying.setEmpty(lo, hi);
      return this;
   }

   public Type getOptimisticType() {
      return this.underlying.getOptimisticType();
   }

   public int getInt(int index) {
      return this.underlying.getInt(index);
   }

   public int getIntOptimistic(int index, int programPoint) {
      return this.underlying.getIntOptimistic(index, programPoint);
   }

   public double getDouble(int index) {
      return this.underlying.getDouble(index);
   }

   public double getDoubleOptimistic(int index, int programPoint) {
      return this.underlying.getDoubleOptimistic(index, programPoint);
   }

   public Object getObject(int index) {
      return this.underlying.getObject(index);
   }

   public boolean has(int index) {
      return this.underlying.has(index);
   }

   public ArrayData delete(int index) {
      this.underlying = this.underlying.delete(index);
      this.setLength(this.underlying.length());
      return this;
   }

   public ArrayData delete(long from, long to) {
      this.underlying = this.underlying.delete(from, to);
      this.setLength(this.underlying.length());
      return this;
   }

   public ArrayData convert(Class<?> type) {
      this.underlying = this.underlying.convert(type);
      this.setLength(this.underlying.length());
      return this;
   }

   public Object pop() {
      Object value = this.underlying.pop();
      this.setLength(this.underlying.length());
      return value;
   }

   public long nextIndex(long index) {
      return this.underlying.nextIndex(index);
   }

   static Object convertUndefinedValue(Class<?> targetType) {
      return invoke(Bootstrap.getLinkerServices().getTypeConverter(Undefined.class, targetType), ScriptRuntime.UNDEFINED);
   }
}
