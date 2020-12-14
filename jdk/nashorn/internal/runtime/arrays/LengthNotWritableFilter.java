package jdk.nashorn.internal.runtime.arrays;

import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptRuntime;

final class LengthNotWritableFilter extends ArrayFilter {
   private final SortedMap<Long, Object> extraElements;

   LengthNotWritableFilter(ArrayData underlying) {
      this(underlying, new TreeMap());
   }

   private LengthNotWritableFilter(ArrayData underlying, SortedMap<Long, Object> extraElements) {
      super(underlying);
      this.extraElements = extraElements;
   }

   public ArrayData copy() {
      return new LengthNotWritableFilter(this.underlying.copy(), new TreeMap(this.extraElements));
   }

   public boolean has(int index) {
      return super.has(index) || this.extraElements.containsKey((long)index);
   }

   public void setLength(long length) {
   }

   public ArrayData ensure(long index) {
      return this;
   }

   public ArrayData slice(long from, long to) {
      return new LengthNotWritableFilter(this.underlying.slice(from, to), this.extraElements.subMap(from, to));
   }

   private boolean checkAdd(long index, Object value) {
      if (index >= this.length()) {
         this.extraElements.put(index, value);
         return true;
      } else {
         return false;
      }
   }

   private Object get(long index) {
      Object obj = this.extraElements.get(index);
      return obj == null ? ScriptRuntime.UNDEFINED : obj;
   }

   public int getInt(int index) {
      return (long)index >= this.length() ? JSType.toInt32(this.get((long)index)) : this.underlying.getInt(index);
   }

   public int getIntOptimistic(int index, int programPoint) {
      return (long)index >= this.length() ? JSType.toInt32Optimistic(this.get((long)index), programPoint) : this.underlying.getIntOptimistic(index, programPoint);
   }

   public double getDouble(int index) {
      return (long)index >= this.length() ? JSType.toNumber(this.get((long)index)) : this.underlying.getDouble(index);
   }

   public double getDoubleOptimistic(int index, int programPoint) {
      return (long)index >= this.length() ? JSType.toNumberOptimistic(this.get((long)index), programPoint) : this.underlying.getDoubleOptimistic(index, programPoint);
   }

   public Object getObject(int index) {
      return (long)index >= this.length() ? this.get((long)index) : this.underlying.getObject(index);
   }

   public ArrayData set(int index, Object value, boolean strict) {
      if (this.checkAdd((long)index, value)) {
         return this;
      } else {
         this.underlying = this.underlying.set(index, value, strict);
         return this;
      }
   }

   public ArrayData set(int index, int value, boolean strict) {
      if (this.checkAdd((long)index, value)) {
         return this;
      } else {
         this.underlying = this.underlying.set(index, value, strict);
         return this;
      }
   }

   public ArrayData set(int index, double value, boolean strict) {
      if (this.checkAdd((long)index, value)) {
         return this;
      } else {
         this.underlying = this.underlying.set(index, value, strict);
         return this;
      }
   }

   public ArrayData delete(int index) {
      this.extraElements.remove(index);
      this.underlying = this.underlying.delete(index);
      return this;
   }

   public ArrayData delete(long fromIndex, long toIndex) {
      Iterator iter = this.extraElements.keySet().iterator();

      while(iter.hasNext()) {
         long next = (Long)iter.next();
         if (next >= fromIndex && next <= toIndex) {
            iter.remove();
         }

         if (next > toIndex) {
            break;
         }
      }

      this.underlying = this.underlying.delete(fromIndex, toIndex);
      return this;
   }

   public Iterator<Long> indexIterator() {
      List<Long> keys = this.computeIteratorKeys();
      keys.addAll(this.extraElements.keySet());
      return keys.iterator();
   }
}
