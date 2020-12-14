package jdk.nashorn.internal.objects;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.Collection;
import java.util.Collections;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.arrays.ArrayData;
import jdk.nashorn.internal.runtime.arrays.TypedArrayData;

public final class NativeFloat32Array extends ArrayBufferView {
   public static final int BYTES_PER_ELEMENT = 4;
   private static PropertyMap $nasgenmap$;
   private static final ArrayBufferView.Factory FACTORY = new ArrayBufferView.Factory(4) {
      public ArrayBufferView construct(NativeArrayBuffer buffer, int byteOffset, int length) {
         return new NativeFloat32Array(buffer, byteOffset, length);
      }

      public NativeFloat32Array.Float32ArrayData createArrayData(ByteBuffer nb, int start, int end) {
         return new NativeFloat32Array.Float32ArrayData(nb.asFloatBuffer(), start, end);
      }

      public String getClassName() {
         return "Float32Array";
      }
   };

   public static NativeFloat32Array constructor(boolean newObj, Object self, Object... args) {
      return (NativeFloat32Array)constructorImpl(newObj, args, FACTORY);
   }

   NativeFloat32Array(NativeArrayBuffer buffer, int byteOffset, int length) {
      super(buffer, byteOffset, length);
   }

   protected ArrayBufferView.Factory factory() {
      return FACTORY;
   }

   protected boolean isFloatArray() {
      return true;
   }

   protected static Object set(Object self, Object array, Object offset) {
      return ArrayBufferView.setImpl(self, array, offset);
   }

   protected static NativeFloat32Array subarray(Object self, Object begin, Object end) {
      return (NativeFloat32Array)ArrayBufferView.subarrayImpl(self, begin, end);
   }

   protected ScriptObject getPrototype(Global global) {
      return global.getFloat32ArrayPrototype();
   }

   static {
      $clinit$();
   }

   public static void $clinit$() {
      $nasgenmap$ = PropertyMap.newMap((Collection)Collections.EMPTY_LIST);
   }

   private static final class Float32ArrayData extends TypedArrayData<FloatBuffer> {
      private static final MethodHandle GET_ELEM;
      private static final MethodHandle SET_ELEM;

      private Float32ArrayData(FloatBuffer nb, int start, int end) {
         super(((FloatBuffer)nb.position(start).limit(end)).slice(), end - start);
      }

      public Class<?> getElementType() {
         return Double.TYPE;
      }

      public Class<?> getBoxedElementType() {
         return Double.class;
      }

      protected MethodHandle getGetElem() {
         return GET_ELEM;
      }

      protected MethodHandle getSetElem() {
         return SET_ELEM;
      }

      private double getElem(int index) {
         try {
            return (double)((FloatBuffer)this.nb).get(index);
         } catch (IndexOutOfBoundsException var3) {
            throw new ClassCastException();
         }
      }

      private void setElem(int index, double elem) {
         try {
            if (index < ((FloatBuffer)this.nb).limit()) {
               ((FloatBuffer)this.nb).put(index, (float)elem);
            }

         } catch (IndexOutOfBoundsException var5) {
            throw new ClassCastException();
         }
      }

      public MethodHandle getElementGetter(Class<?> returnType, int programPoint) {
         return returnType == Integer.TYPE ? null : this.getContinuousElementGetter(this.getClass(), GET_ELEM, returnType, programPoint);
      }

      public int getInt(int index) {
         return (int)this.getDouble(index);
      }

      public double getDouble(int index) {
         return this.getElem(index);
      }

      public double getDoubleOptimistic(int index, int programPoint) {
         return this.getElem(index);
      }

      public Object getObject(int index) {
         return this.getDouble(index);
      }

      public ArrayData set(int index, Object value, boolean strict) {
         return this.set(index, JSType.toNumber(value), strict);
      }

      public ArrayData set(int index, int value, boolean strict) {
         return this.set(index, (double)value, strict);
      }

      public ArrayData set(int index, double value, boolean strict) {
         this.setElem(index, value);
         return this;
      }

      // $FF: synthetic method
      Float32ArrayData(FloatBuffer x0, int x1, int x2, Object x3) {
         this(x0, x1, x2);
      }

      static {
         GET_ELEM = CompilerConstants.specialCall(MethodHandles.lookup(), NativeFloat32Array.Float32ArrayData.class, "getElem", Double.TYPE, Integer.TYPE).methodHandle();
         SET_ELEM = CompilerConstants.specialCall(MethodHandles.lookup(), NativeFloat32Array.Float32ArrayData.class, "setElem", Void.TYPE, Integer.TYPE, Double.TYPE).methodHandle();
      }
   }
}
