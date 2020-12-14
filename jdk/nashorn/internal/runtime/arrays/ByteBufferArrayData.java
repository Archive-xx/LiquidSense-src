package jdk.nashorn.internal.runtime.arrays;

import java.nio.ByteBuffer;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.ScriptRuntime;

final class ByteBufferArrayData extends ArrayData {
   private final ByteBuffer buf;

   ByteBufferArrayData(int length) {
      super((long)length);
      this.buf = ByteBuffer.allocateDirect(length);
   }

   ByteBufferArrayData(ByteBuffer buf) {
      super((long)buf.capacity());
      this.buf = buf;
   }

   public PropertyDescriptor getDescriptor(Global global, int index) {
      return global.newDataDescriptor(this.getObject(index), false, true, true);
   }

   public ArrayData copy() {
      throw unsupported("copy");
   }

   public Object[] asObjectArray() {
      throw unsupported("asObjectArray");
   }

   public void setLength(long length) {
      throw new UnsupportedOperationException("setLength");
   }

   public ArrayData shiftLeft(int by) {
      throw unsupported("shiftLeft");
   }

   public ArrayData shiftRight(int by) {
      throw unsupported("shiftRight");
   }

   public ArrayData ensure(long safeIndex) {
      if (safeIndex < (long)this.buf.capacity()) {
         return this;
      } else {
         throw unsupported("ensure");
      }
   }

   public ArrayData shrink(long newLength) {
      throw unsupported("shrink");
   }

   public ArrayData set(int index, Object value, boolean strict) {
      if (value instanceof Number) {
         this.buf.put(index, ((Number)value).byteValue());
         return this;
      } else {
         throw ECMAErrors.typeError("not.a.number", ScriptRuntime.safeToString(value));
      }
   }

   public ArrayData set(int index, int value, boolean strict) {
      this.buf.put(index, (byte)value);
      return this;
   }

   public ArrayData set(int index, double value, boolean strict) {
      this.buf.put(index, (byte)((int)value));
      return this;
   }

   public int getInt(int index) {
      return 255 & this.buf.get(index);
   }

   public double getDouble(int index) {
      return (double)(255 & this.buf.get(index));
   }

   public Object getObject(int index) {
      return 255 & this.buf.get(index);
   }

   public boolean has(int index) {
      return index > -1 && index < this.buf.capacity();
   }

   public boolean canDelete(int index, boolean strict) {
      return false;
   }

   public boolean canDelete(long longIndex, boolean strict) {
      return false;
   }

   public ArrayData delete(int index) {
      throw unsupported("delete");
   }

   public ArrayData delete(long fromIndex, long toIndex) {
      throw unsupported("delete");
   }

   public ArrayData push(boolean strict, Object... items) {
      throw unsupported("push");
   }

   public Object pop() {
      throw unsupported("pop");
   }

   public ArrayData slice(long from, long to) {
      throw unsupported("slice");
   }

   public ArrayData convert(Class<?> type) {
      throw unsupported("convert");
   }

   private static UnsupportedOperationException unsupported(String method) {
      return new UnsupportedOperationException(method);
   }
}
