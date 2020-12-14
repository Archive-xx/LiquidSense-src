package jdk.nashorn.internal.objects;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.Collection;
import java.util.Collections;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.arrays.ArrayData;
import jdk.nashorn.internal.runtime.arrays.TypedArrayData;

public final class NativeUint32Array extends ArrayBufferView {
   public static final int BYTES_PER_ELEMENT = 4;
   private static PropertyMap $nasgenmap$;
   private static final ArrayBufferView.Factory FACTORY = new ArrayBufferView.Factory(4) {
      public ArrayBufferView construct(NativeArrayBuffer buffer, int byteBegin, int length) {
         return new NativeUint32Array(buffer, byteBegin, length);
      }

      public NativeUint32Array.Uint32ArrayData createArrayData(ByteBuffer nb, int start, int end) {
         return new NativeUint32Array.Uint32ArrayData(nb.order(ByteOrder.nativeOrder()).asIntBuffer(), start, end);
      }

      public String getClassName() {
         return "Uint32Array";
      }
   };

   public static NativeUint32Array constructor(boolean newObj, Object self, Object... args) {
      return (NativeUint32Array)constructorImpl(newObj, args, FACTORY);
   }

   NativeUint32Array(NativeArrayBuffer buffer, int byteOffset, int length) {
      super(buffer, byteOffset, length);
   }

   protected ArrayBufferView.Factory factory() {
      return FACTORY;
   }

   protected static Object set(Object self, Object array, Object offset) {
      return ArrayBufferView.setImpl(self, array, offset);
   }

   protected static NativeUint32Array subarray(Object self, Object begin, Object end) {
      return (NativeUint32Array)ArrayBufferView.subarrayImpl(self, begin, end);
   }

   protected ScriptObject getPrototype(Global global) {
      return global.getUint32ArrayPrototype();
   }

   static {
      $clinit$();
   }

   public static void $clinit$() {
      $nasgenmap$ = PropertyMap.newMap((Collection)Collections.EMPTY_LIST);
   }

   private static final class Uint32ArrayData extends TypedArrayData<IntBuffer> {
      private static final MethodHandle GET_ELEM;
      private static final MethodHandle SET_ELEM;

      private Uint32ArrayData(IntBuffer nb, int start, int end) {
         super(((IntBuffer)nb.position(start).limit(end)).slice(), end - start);
      }

      protected MethodHandle getGetElem() {
         return GET_ELEM;
      }

      protected MethodHandle getSetElem() {
         return SET_ELEM;
      }

      public MethodHandle getElementGetter(Class<?> returnType, int programPoint) {
         return returnType == Integer.TYPE ? null : this.getContinuousElementGetter(this.getClass(), GET_ELEM, returnType, programPoint);
      }

      private int getRawElem(int index) {
         try {
            return ((IntBuffer)this.nb).get(index);
         } catch (IndexOutOfBoundsException var3) {
            throw new ClassCastException();
         }
      }

      private double getElem(int index) {
         return (double)JSType.toUint32(this.getRawElem(index));
      }

      private void setElem(int index, int elem) {
         try {
            if (index < ((IntBuffer)this.nb).limit()) {
               ((IntBuffer)this.nb).put(index, elem);
            }

         } catch (IndexOutOfBoundsException var4) {
            throw new ClassCastException();
         }
      }

      public boolean isUnsigned() {
         return true;
      }

      public Class<?> getElementType() {
         return Double.TYPE;
      }

      public Class<?> getBoxedElementType() {
         return Double.class;
      }

      public int getInt(int index) {
         return this.getRawElem(index);
      }

      public int getIntOptimistic(int index, int programPoint) {
         return JSType.toUint32Optimistic(this.getRawElem(index), programPoint);
      }

      public double getDouble(int index) {
         return this.getElem(index);
      }

      public double getDoubleOptimistic(int index, int programPoint) {
         return this.getElem(index);
      }

      public Object getObject(int index) {
         return this.getElem(index);
      }

      public ArrayData set(int index, Object value, boolean strict) {
         return this.set(index, JSType.toInt32(value), strict);
      }

      public ArrayData set(int index, int value, boolean strict) {
         this.setElem(index, value);
         return this;
      }

      public ArrayData set(int index, double value, boolean strict) {
         return this.set(index, (int)value, strict);
      }

      // $FF: synthetic method
      Uint32ArrayData(IntBuffer x0, int x1, int x2, Object x3) {
         this(x0, x1, x2);
      }

      static {
         GET_ELEM = CompilerConstants.specialCall(MethodHandles.lookup(), NativeUint32Array.Uint32ArrayData.class, "getElem", Double.TYPE, Integer.TYPE).methodHandle();
         SET_ELEM = CompilerConstants.specialCall(MethodHandles.lookup(), NativeUint32Array.Uint32ArrayData.class, "setElem", Void.TYPE, Integer.TYPE, Integer.TYPE).methodHandle();
      }
   }
}
