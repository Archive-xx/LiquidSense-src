package jdk.nashorn.internal.runtime.arrays;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.UnwarrantedOptimismException;

public abstract class ArrayData {
   protected static final int CHUNK_SIZE = 32;
   public static final ArrayData EMPTY_ARRAY = new ArrayData.UntouchedArrayData();
   private long length;
   protected static final CompilerConstants.Call THROW_UNWARRANTED;

   protected ArrayData(long length) {
      this.length = length;
   }

   public static ArrayData initialArray() {
      return new IntArrayData();
   }

   protected static void throwUnwarranted(ArrayData data, int programPoint, int index) {
      throw new UnwarrantedOptimismException(data.getObject(index), programPoint);
   }

   protected static int alignUp(int size) {
      return size + 32 - 1 & -32;
   }

   public static ArrayData allocate(long length) {
      if (length == 0L) {
         return new IntArrayData();
      } else {
         return (ArrayData)(length >= 131072L ? new SparseArrayData(EMPTY_ARRAY, length) : new DeletedRangeArrayFilter(new IntArrayData((int)length), 0L, length - 1L));
      }
   }

   public static ArrayData allocate(Object array) {
      Class<?> clazz = array.getClass();
      if (clazz == int[].class) {
         return new IntArrayData((int[])((int[])array), ((int[])((int[])array)).length);
      } else {
         return (ArrayData)(clazz == double[].class ? new NumberArrayData((double[])((double[])array), ((double[])((double[])array)).length) : new ObjectArrayData((Object[])((Object[])array), ((Object[])((Object[])array)).length));
      }
   }

   public static ArrayData allocate(int[] array) {
      return new IntArrayData(array, array.length);
   }

   public static ArrayData allocate(double[] array) {
      return new NumberArrayData(array, array.length);
   }

   public static ArrayData allocate(Object[] array) {
      return new ObjectArrayData(array, array.length);
   }

   public static ArrayData allocate(ByteBuffer buf) {
      return new ByteBufferArrayData(buf);
   }

   public static ArrayData freeze(ArrayData underlying) {
      return new FrozenArrayFilter(underlying);
   }

   public static ArrayData seal(ArrayData underlying) {
      return new SealedArrayFilter(underlying);
   }

   public static ArrayData preventExtension(ArrayData underlying) {
      return new NonExtensibleArrayFilter(underlying);
   }

   public static ArrayData setIsLengthNotWritable(ArrayData underlying) {
      return new LengthNotWritableFilter(underlying);
   }

   public final long length() {
      return this.length;
   }

   public abstract ArrayData copy();

   public abstract Object[] asObjectArray();

   public Object asArrayOfType(Class<?> componentType) {
      return JSType.convertArray(this.asObjectArray(), componentType);
   }

   public void setLength(long length) {
      this.length = length;
   }

   protected final long increaseLength() {
      return ++this.length;
   }

   protected final long decreaseLength() {
      return --this.length;
   }

   public abstract ArrayData shiftLeft(int var1);

   public abstract ArrayData shiftRight(int var1);

   public abstract ArrayData ensure(long var1);

   public abstract ArrayData shrink(long var1);

   public abstract ArrayData set(int var1, Object var2, boolean var3);

   public abstract ArrayData set(int var1, int var2, boolean var3);

   public abstract ArrayData set(int var1, double var2, boolean var4);

   public ArrayData setEmpty(int index) {
      return this;
   }

   public ArrayData setEmpty(long lo, long hi) {
      return this;
   }

   public abstract int getInt(int var1);

   public Type getOptimisticType() {
      return Type.OBJECT;
   }

   public int getIntOptimistic(int index, int programPoint) {
      throw new UnwarrantedOptimismException(this.getObject(index), programPoint, this.getOptimisticType());
   }

   public abstract double getDouble(int var1);

   public double getDoubleOptimistic(int index, int programPoint) {
      throw new UnwarrantedOptimismException(this.getObject(index), programPoint, this.getOptimisticType());
   }

   public abstract Object getObject(int var1);

   public abstract boolean has(int var1);

   public boolean canDelete(int index, boolean strict) {
      return true;
   }

   public boolean canDelete(long longIndex, boolean strict) {
      return true;
   }

   public final ArrayData safeDelete(long fromIndex, long toIndex, boolean strict) {
      return fromIndex <= toIndex && this.canDelete(fromIndex, strict) ? this.delete(fromIndex, toIndex) : this;
   }

   public PropertyDescriptor getDescriptor(Global global, int index) {
      return global.newDataDescriptor(this.getObject(index), true, true, true);
   }

   public abstract ArrayData delete(int var1);

   public abstract ArrayData delete(long var1, long var3);

   public abstract ArrayData convert(Class<?> var1);

   public ArrayData push(boolean strict, Object... items) {
      if (items.length == 0) {
         return this;
      } else {
         Class<?> widest = widestType(items);
         ArrayData newData = this.convert(widest);
         long pos = newData.length;
         Object[] var7 = items;
         int var8 = items.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            Object item = var7[var9];
            newData = newData.ensure(pos);
            newData.set((int)(pos++), item, strict);
         }

         return newData;
      }
   }

   public ArrayData push(boolean strict, Object item) {
      return this.push(strict, item);
   }

   public abstract Object pop();

   public abstract ArrayData slice(long var1, long var3);

   public ArrayData fastSplice(int start, int removed, int added) throws UnsupportedOperationException {
      throw new UnsupportedOperationException();
   }

   static Class<?> widestType(Object... items) {
      assert items.length > 0;

      Class<?> widest = Integer.class;
      Object[] var2 = items;
      int var3 = items.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Object item = var2[var4];
         if (item == null) {
            return Object.class;
         }

         Class<?> itemClass = item.getClass();
         if (itemClass != Double.class && itemClass != Float.class && itemClass != Long.class) {
            if (itemClass != Integer.class && itemClass != Short.class && itemClass != Byte.class) {
               return Object.class;
            }
         } else if (widest == Integer.class) {
            widest = Double.class;
         }
      }

      return widest;
   }

   protected List<Long> computeIteratorKeys() {
      List<Long> keys = new ArrayList();
      long len = this.length();

      for(long i = 0L; i < len; i = this.nextIndex(i)) {
         if (this.has((int)i)) {
            keys.add(i);
         }
      }

      return keys;
   }

   public Iterator<Long> indexIterator() {
      return this.computeIteratorKeys().iterator();
   }

   public static int nextSize(int size) {
      return alignUp(size + 1) * 2;
   }

   long nextIndex(long index) {
      return index + 1L;
   }

   static Object invoke(MethodHandle mh, Object arg) {
      try {
         return mh.invoke(arg);
      } catch (Error | RuntimeException var3) {
         throw var3;
      } catch (Throwable var4) {
         throw new RuntimeException(var4);
      }
   }

   public GuardedInvocation findFastCallMethod(Class<? extends ArrayData> clazz, CallSiteDescriptor desc, LinkRequest request) {
      return null;
   }

   public GuardedInvocation findFastGetMethod(Class<? extends ArrayData> clazz, CallSiteDescriptor desc, LinkRequest request, String operator) {
      return null;
   }

   public GuardedInvocation findFastGetIndexMethod(Class<? extends ArrayData> clazz, CallSiteDescriptor desc, LinkRequest request) {
      return null;
   }

   public GuardedInvocation findFastSetIndexMethod(Class<? extends ArrayData> clazz, CallSiteDescriptor desc, LinkRequest request) {
      return null;
   }

   static {
      THROW_UNWARRANTED = CompilerConstants.staticCall(MethodHandles.lookup(), ArrayData.class, "throwUnwarranted", Void.TYPE, ArrayData.class, Integer.TYPE, Integer.TYPE);
   }

   private static class UntouchedArrayData extends ContinuousArrayData {
      private UntouchedArrayData() {
         super(0L);
      }

      private ArrayData toRealArrayData() {
         return new IntArrayData(0);
      }

      private ArrayData toRealArrayData(int index) {
         IntArrayData newData = new IntArrayData(index + 1);
         return new DeletedRangeArrayFilter(newData, 0L, (long)index);
      }

      public ContinuousArrayData copy() {
         assert this.length() == 0L;

         return this;
      }

      public Object asArrayOfType(Class<?> componentType) {
         return Array.newInstance(componentType, 0);
      }

      public Object[] asObjectArray() {
         return ScriptRuntime.EMPTY_ARRAY;
      }

      public ArrayData ensure(long safeIndex) {
         assert safeIndex >= 0L;

         return (ArrayData)(safeIndex >= 131072L ? new SparseArrayData(this, safeIndex + 1L) : this.toRealArrayData((int)safeIndex));
      }

      public ArrayData convert(Class<?> type) {
         return this.toRealArrayData().convert(type);
      }

      public ArrayData delete(int index) {
         return new DeletedRangeArrayFilter(this, (long)index, (long)index);
      }

      public ArrayData delete(long fromIndex, long toIndex) {
         return new DeletedRangeArrayFilter(this, fromIndex, toIndex);
      }

      public ArrayData shiftLeft(int by) {
         return this;
      }

      public ArrayData shiftRight(int by) {
         return this;
      }

      public ArrayData shrink(long newLength) {
         return this;
      }

      public ArrayData set(int index, Object value, boolean strict) {
         return this.toRealArrayData(index).set(index, value, strict);
      }

      public ArrayData set(int index, int value, boolean strict) {
         return this.toRealArrayData(index).set(index, value, strict);
      }

      public ArrayData set(int index, double value, boolean strict) {
         return this.toRealArrayData(index).set(index, value, strict);
      }

      public int getInt(int index) {
         throw new ArrayIndexOutOfBoundsException(index);
      }

      public double getDouble(int index) {
         throw new ArrayIndexOutOfBoundsException(index);
      }

      public Object getObject(int index) {
         throw new ArrayIndexOutOfBoundsException(index);
      }

      public boolean has(int index) {
         return false;
      }

      public Object pop() {
         return ScriptRuntime.UNDEFINED;
      }

      public ArrayData push(boolean strict, Object item) {
         return this.toRealArrayData().push(strict, item);
      }

      public ArrayData slice(long from, long to) {
         return this;
      }

      public ContinuousArrayData fastConcat(ContinuousArrayData otherData) {
         return otherData.copy();
      }

      public String toString() {
         return this.getClass().getSimpleName();
      }

      public MethodHandle getElementGetter(Class<?> returnType, int programPoint) {
         return null;
      }

      public MethodHandle getElementSetter(Class<?> elementType) {
         return null;
      }

      public Class<?> getElementType() {
         return Integer.TYPE;
      }

      public Class<?> getBoxedElementType() {
         return Integer.class;
      }

      // $FF: synthetic method
      UntouchedArrayData(Object x0) {
         this();
      }
   }
}
