package jdk.nashorn.internal.runtime.arrays;

import java.util.Arrays;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Map.Entry;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptRuntime;

class SparseArrayData extends ArrayData {
   static final int MAX_DENSE_LENGTH = 131072;
   private ArrayData underlying;
   private final long maxDenseLength;
   private TreeMap<Long, Object> sparseMap;

   SparseArrayData(ArrayData underlying, long length) {
      this(underlying, length, new TreeMap());
   }

   private SparseArrayData(ArrayData underlying, long length, TreeMap<Long, Object> sparseMap) {
      super(length);

      assert underlying.length() <= length;

      this.underlying = underlying;
      this.maxDenseLength = underlying.length();
      this.sparseMap = sparseMap;
   }

   public ArrayData copy() {
      return new SparseArrayData(this.underlying.copy(), this.length(), new TreeMap(this.sparseMap));
   }

   public Object[] asObjectArray() {
      int len = (int)Math.min(this.length(), 2147483647L);
      int underlyingLength = (int)Math.min((long)len, this.underlying.length());
      Object[] objArray = new Object[len];

      for(int i = 0; i < underlyingLength; ++i) {
         objArray[i] = this.underlying.getObject(i);
      }

      Arrays.fill(objArray, underlyingLength, len, ScriptRuntime.UNDEFINED);

      Entry entry;
      long key;
      for(Iterator var8 = this.sparseMap.entrySet().iterator(); var8.hasNext(); objArray[(int)key] = entry.getValue()) {
         entry = (Entry)var8.next();
         key = (Long)entry.getKey();
         if (key >= 2147483647L) {
            break;
         }
      }

      return objArray;
   }

   public ArrayData shiftLeft(int by) {
      this.underlying = this.underlying.shiftLeft(by);
      TreeMap<Long, Object> newSparseMap = new TreeMap();
      Iterator var3 = this.sparseMap.entrySet().iterator();

      while(var3.hasNext()) {
         Entry<Long, Object> entry = (Entry)var3.next();
         long newIndex = (Long)entry.getKey() - (long)by;
         if (newIndex >= 0L) {
            if (newIndex < this.maxDenseLength) {
               long oldLength = this.underlying.length();
               this.underlying = this.underlying.ensure(newIndex).set((int)newIndex, entry.getValue(), false).safeDelete(oldLength, newIndex - 1L, false);
            } else {
               newSparseMap.put(newIndex, entry.getValue());
            }
         }
      }

      this.sparseMap = newSparseMap;
      this.setLength(Math.max(this.length() - (long)by, 0L));
      return (ArrayData)(this.sparseMap.isEmpty() ? this.underlying : this);
   }

   public ArrayData shiftRight(int by) {
      TreeMap<Long, Object> newSparseMap = new TreeMap();
      long len = this.underlying.length();
      long i;
      if (len + (long)by > this.maxDenseLength) {
         long tempLength = Math.max(0L, this.maxDenseLength - (long)by);

         for(i = tempLength; i < len; ++i) {
            if (this.underlying.has((int)i)) {
               newSparseMap.put(i + (long)by, this.underlying.getObject((int)i));
            }
         }

         this.underlying = this.underlying.shrink((long)((int)tempLength));
         this.underlying.setLength(tempLength);
      }

      this.underlying = this.underlying.shiftRight(by);
      Iterator var9 = this.sparseMap.entrySet().iterator();

      while(var9.hasNext()) {
         Entry<Long, Object> entry = (Entry)var9.next();
         i = (Long)entry.getKey() + (long)by;
         newSparseMap.put(i, entry.getValue());
      }

      this.sparseMap = newSparseMap;
      this.setLength(this.length() + (long)by);
      return this;
   }

   public ArrayData ensure(long safeIndex) {
      if (safeIndex >= this.length()) {
         this.setLength(safeIndex + 1L);
      }

      return this;
   }

   public ArrayData shrink(long newLength) {
      if (newLength < this.underlying.length()) {
         this.underlying = this.underlying.shrink(newLength);
         this.underlying.setLength(newLength);
         this.sparseMap.clear();
         this.setLength(newLength);
      }

      this.sparseMap.subMap(newLength, Long.MAX_VALUE).clear();
      this.setLength(newLength);
      return this;
   }

   public ArrayData set(int index, Object value, boolean strict) {
      if (index >= 0 && (long)index < this.maxDenseLength) {
         long oldLength = this.underlying.length();
         this.underlying = this.underlying.ensure((long)index).set(index, value, strict).safeDelete(oldLength, (long)(index - 1), strict);
         this.setLength(Math.max(this.underlying.length(), this.length()));
      } else {
         Long longIndex = indexToKey(index);
         this.sparseMap.put(longIndex, value);
         this.setLength(Math.max(longIndex + 1L, this.length()));
      }

      return this;
   }

   public ArrayData set(int index, int value, boolean strict) {
      if (index >= 0 && (long)index < this.maxDenseLength) {
         long oldLength = this.underlying.length();
         this.underlying = this.underlying.ensure((long)index).set(index, value, strict).safeDelete(oldLength, (long)(index - 1), strict);
         this.setLength(Math.max(this.underlying.length(), this.length()));
      } else {
         Long longIndex = indexToKey(index);
         this.sparseMap.put(longIndex, value);
         this.setLength(Math.max(longIndex + 1L, this.length()));
      }

      return this;
   }

   public ArrayData set(int index, double value, boolean strict) {
      if (index >= 0 && (long)index < this.maxDenseLength) {
         long oldLength = this.underlying.length();
         this.underlying = this.underlying.ensure((long)index).set(index, value, strict).safeDelete(oldLength, (long)(index - 1), strict);
         this.setLength(Math.max(this.underlying.length(), this.length()));
      } else {
         Long longIndex = indexToKey(index);
         this.sparseMap.put(longIndex, value);
         this.setLength(Math.max(longIndex + 1L, this.length()));
      }

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
      return index >= 0 && (long)index < this.maxDenseLength ? this.underlying.getInt(index) : JSType.toInt32(this.sparseMap.get(indexToKey(index)));
   }

   public int getIntOptimistic(int index, int programPoint) {
      return index >= 0 && (long)index < this.maxDenseLength ? this.underlying.getIntOptimistic(index, programPoint) : JSType.toInt32Optimistic(this.sparseMap.get(indexToKey(index)), programPoint);
   }

   public double getDouble(int index) {
      return index >= 0 && (long)index < this.maxDenseLength ? this.underlying.getDouble(index) : JSType.toNumber(this.sparseMap.get(indexToKey(index)));
   }

   public double getDoubleOptimistic(int index, int programPoint) {
      return index >= 0 && (long)index < this.maxDenseLength ? this.underlying.getDouble(index) : JSType.toNumberOptimistic(this.sparseMap.get(indexToKey(index)), programPoint);
   }

   public Object getObject(int index) {
      if (index >= 0 && (long)index < this.maxDenseLength) {
         return this.underlying.getObject(index);
      } else {
         Long key = indexToKey(index);
         return this.sparseMap.containsKey(key) ? this.sparseMap.get(key) : ScriptRuntime.UNDEFINED;
      }
   }

   public boolean has(int index) {
      if (index >= 0 && (long)index < this.maxDenseLength) {
         return (long)index < this.underlying.length() && this.underlying.has(index);
      } else {
         return this.sparseMap.containsKey(indexToKey(index));
      }
   }

   public ArrayData delete(int index) {
      if (index >= 0 && (long)index < this.maxDenseLength) {
         if ((long)index < this.underlying.length()) {
            this.underlying = this.underlying.delete(index);
         }
      } else {
         this.sparseMap.remove(indexToKey(index));
      }

      return this;
   }

   public ArrayData delete(long fromIndex, long toIndex) {
      if (fromIndex < this.maxDenseLength && fromIndex < this.underlying.length()) {
         this.underlying = this.underlying.delete(fromIndex, Math.min(toIndex, this.underlying.length() - 1L));
      }

      if (toIndex >= this.maxDenseLength) {
         this.sparseMap.subMap(fromIndex, true, toIndex, true).clear();
      }

      return this;
   }

   private static Long indexToKey(int index) {
      return ArrayIndex.toLongIndex(index);
   }

   public ArrayData convert(Class<?> type) {
      this.underlying = this.underlying.convert(type);
      return this;
   }

   public Object pop() {
      long len = this.length();
      long underlyingLen = this.underlying.length();
      if (len == 0L) {
         return ScriptRuntime.UNDEFINED;
      } else if (len == underlyingLen) {
         Object result = this.underlying.pop();
         this.setLength(this.underlying.length());
         return result;
      } else {
         this.setLength(len - 1L);
         Long key = len - 1L;
         return this.sparseMap.containsKey(key) ? this.sparseMap.remove(key) : ScriptRuntime.UNDEFINED;
      }
   }

   public ArrayData slice(long from, long to) {
      assert to <= this.length();

      long start = from < 0L ? from + this.length() : from;
      long newLength = to - start;
      long underlyingLength = this.underlying.length();
      if (start >= 0L && to <= this.maxDenseLength) {
         return newLength <= underlyingLength ? this.underlying.slice(from, to) : this.underlying.slice(from, to).ensure(newLength - 1L).delete(underlyingLength, newLength);
      } else {
         ArrayData sliced = EMPTY_ARRAY;
         sliced = sliced.ensure(newLength - 1L);

         for(long i = start; i < to; i = this.nextIndex(i)) {
            if (this.has((int)i)) {
               sliced = sliced.set((int)(i - start), this.getObject((int)i), false);
            }
         }

         assert sliced.length() == newLength;

         return sliced;
      }
   }

   public long nextIndex(long index) {
      if (index < this.underlying.length() - 1L) {
         return this.underlying.nextIndex(index);
      } else {
         Long nextKey = (Long)this.sparseMap.higherKey(index);
         return nextKey != null ? nextKey : this.length();
      }
   }
}
