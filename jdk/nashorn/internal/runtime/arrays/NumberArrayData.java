package jdk.nashorn.internal.runtime.arrays;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import jdk.internal.dynalink.support.TypeUtilities;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptRuntime;

final class NumberArrayData extends ContinuousArrayData implements NumericElements {
   private double[] array;
   private static final MethodHandle HAS_GET_ELEM;
   private static final MethodHandle SET_ELEM;

   NumberArrayData(double[] array, int length) {
      super((long)length);

      assert array.length >= length;

      this.array = array;
   }

   public final Class<?> getElementType() {
      return Double.TYPE;
   }

   public final Class<?> getBoxedElementType() {
      return Double.class;
   }

   public final int getElementWeight() {
      return 3;
   }

   public final ContinuousArrayData widest(ContinuousArrayData otherData) {
      return (ContinuousArrayData)(otherData instanceof IntOrLongElements ? this : otherData);
   }

   public NumberArrayData copy() {
      return new NumberArrayData((double[])this.array.clone(), (int)this.length());
   }

   public Object[] asObjectArray() {
      return this.toObjectArray(true);
   }

   private Object[] toObjectArray(boolean trim) {
      assert this.length() <= (long)this.array.length : "length exceeds internal array size";

      int len = (int)this.length();
      Object[] oarray = new Object[trim ? len : this.array.length];

      for(int index = 0; index < len; ++index) {
         oarray[index] = this.array[index];
      }

      return oarray;
   }

   public Object asArrayOfType(Class<?> componentType) {
      if (componentType == Double.TYPE) {
         int len = (int)this.length();
         return this.array.length == len ? (double[])this.array.clone() : Arrays.copyOf(this.array, len);
      } else {
         return super.asArrayOfType(componentType);
      }
   }

   private static boolean canWiden(Class<?> type) {
      return TypeUtilities.isWrapperType(type) && type != Boolean.class && type != Character.class;
   }

   public ContinuousArrayData convert(Class<?> type) {
      if (!canWiden(type)) {
         int len = (int)this.length();
         return new ObjectArrayData(this.toObjectArray(false), len);
      } else {
         return this;
      }
   }

   public ArrayData shiftLeft(int by) {
      if ((long)by >= this.length()) {
         this.shrink(0L);
      } else {
         System.arraycopy(this.array, by, this.array, 0, this.array.length - by);
      }

      this.setLength(Math.max(0L, this.length() - (long)by));
      return this;
   }

   public ArrayData shiftRight(int by) {
      ArrayData newData = this.ensure((long)by + this.length() - 1L);
      if (newData != this) {
         newData.shiftRight(by);
         return newData;
      } else {
         System.arraycopy(this.array, 0, this.array, by, this.array.length - by);
         return this;
      }
   }

   public ArrayData ensure(long safeIndex) {
      if (safeIndex >= 131072L) {
         return new SparseArrayData(this, safeIndex + 1L);
      } else {
         int alen = this.array.length;
         if (safeIndex >= (long)alen) {
            int newLength = ArrayData.nextSize((int)safeIndex);
            this.array = Arrays.copyOf(this.array, newLength);
         }

         if (safeIndex >= this.length()) {
            this.setLength(safeIndex + 1L);
         }

         return this;
      }
   }

   public ArrayData shrink(long newLength) {
      Arrays.fill(this.array, (int)newLength, this.array.length, 0.0D);
      return this;
   }

   public ArrayData set(int index, Object value, boolean strict) {
      if (value instanceof Double || value != null && canWiden(value.getClass())) {
         return this.set(index, ((Number)value).doubleValue(), strict);
      } else if (value == ScriptRuntime.UNDEFINED) {
         return (new UndefinedArrayFilter(this)).set(index, value, strict);
      } else {
         ArrayData newData = this.convert(value == null ? Object.class : value.getClass());
         return newData.set(index, value, strict);
      }
   }

   public ArrayData set(int index, int value, boolean strict) {
      this.array[index] = (double)value;
      this.setLength(Math.max((long)(index + 1), this.length()));
      return this;
   }

   public ArrayData set(int index, double value, boolean strict) {
      this.array[index] = value;
      this.setLength(Math.max((long)(index + 1), this.length()));
      return this;
   }

   private double getElem(int index) {
      if (this.has(index)) {
         return this.array[index];
      } else {
         throw new ClassCastException();
      }
   }

   private void setElem(int index, double elem) {
      if (this.hasRoomFor(index)) {
         this.array[index] = elem;
      } else {
         throw new ClassCastException();
      }
   }

   public MethodHandle getElementGetter(Class<?> returnType, int programPoint) {
      return returnType == Integer.TYPE ? null : this.getContinuousElementGetter(HAS_GET_ELEM, returnType, programPoint);
   }

   public MethodHandle getElementSetter(Class<?> elementType) {
      return elementType.isPrimitive() ? this.getContinuousElementSetter(Lookup.MH.asType(SET_ELEM, SET_ELEM.type().changeParameterType(2, elementType)), elementType) : null;
   }

   public int getInt(int index) {
      return JSType.toInt32(this.array[index]);
   }

   public double getDouble(int index) {
      return this.array[index];
   }

   public double getDoubleOptimistic(int index, int programPoint) {
      return this.array[index];
   }

   public Object getObject(int index) {
      return this.array[index];
   }

   public boolean has(int index) {
      return 0 <= index && (long)index < this.length();
   }

   public ArrayData delete(int index) {
      return new DeletedRangeArrayFilter(this, (long)index, (long)index);
   }

   public ArrayData delete(long fromIndex, long toIndex) {
      return new DeletedRangeArrayFilter(this, fromIndex, toIndex);
   }

   public Object pop() {
      int len = (int)this.length();
      if (len == 0) {
         return ScriptRuntime.UNDEFINED;
      } else {
         int newLength = len - 1;
         double elem = this.array[newLength];
         this.array[newLength] = 0.0D;
         this.setLength((long)newLength);
         return elem;
      }
   }

   public ArrayData slice(long from, long to) {
      long start = from < 0L ? from + this.length() : from;
      long newLength = to - start;
      return new NumberArrayData(Arrays.copyOfRange(this.array, (int)from, (int)to), (int)newLength);
   }

   public ArrayData fastSplice(int start, int removed, int added) throws UnsupportedOperationException {
      long oldLength = this.length();
      long newLength = oldLength - (long)removed + (long)added;
      if (newLength > 131072L && newLength > (long)this.array.length) {
         throw new UnsupportedOperationException();
      } else {
         ArrayData returnValue = removed == 0 ? EMPTY_ARRAY : new NumberArrayData(Arrays.copyOfRange(this.array, start, start + removed), removed);
         if (newLength != oldLength) {
            double[] newArray;
            if (newLength > (long)this.array.length) {
               newArray = new double[ArrayData.nextSize((int)newLength)];
               System.arraycopy(this.array, 0, newArray, 0, start);
            } else {
               newArray = this.array;
            }

            System.arraycopy(this.array, start + removed, newArray, start + added, (int)(oldLength - (long)start - (long)removed));
            this.array = newArray;
            this.setLength(newLength);
         }

         return (ArrayData)returnValue;
      }
   }

   public double fastPush(int arg) {
      return this.fastPush((double)arg);
   }

   public double fastPush(long arg) {
      return this.fastPush((double)arg);
   }

   public double fastPush(double arg) {
      int len = (int)this.length();
      if (len == this.array.length) {
         this.array = Arrays.copyOf(this.array, nextSize(len));
      }

      this.array[len] = arg;
      return (double)this.increaseLength();
   }

   public double fastPopDouble() {
      if (this.length() == 0L) {
         throw new ClassCastException();
      } else {
         int newLength = (int)this.decreaseLength();
         double elem = this.array[newLength];
         this.array[newLength] = 0.0D;
         return elem;
      }
   }

   public Object fastPopObject() {
      return this.fastPopDouble();
   }

   public ContinuousArrayData fastConcat(ContinuousArrayData otherData) {
      int otherLength = (int)otherData.length();
      int thisLength = (int)this.length();

      assert otherLength > 0 && thisLength > 0;

      double[] otherArray = ((NumberArrayData)otherData).array;
      int newLength = otherLength + thisLength;
      double[] newArray = new double[ArrayData.alignUp(newLength)];
      System.arraycopy(this.array, 0, newArray, 0, thisLength);
      System.arraycopy(otherArray, 0, newArray, thisLength, otherLength);
      return new NumberArrayData(newArray, newLength);
   }

   public String toString() {
      assert this.length() <= (long)this.array.length : this.length() + " > " + this.array.length;

      return this.getClass().getSimpleName() + ':' + Arrays.toString(Arrays.copyOf(this.array, (int)this.length()));
   }

   static {
      HAS_GET_ELEM = CompilerConstants.specialCall(MethodHandles.lookup(), NumberArrayData.class, "getElem", Double.TYPE, Integer.TYPE).methodHandle();
      SET_ELEM = CompilerConstants.specialCall(MethodHandles.lookup(), NumberArrayData.class, "setElem", Void.TYPE, Integer.TYPE, Double.TYPE).methodHandle();
   }
}
