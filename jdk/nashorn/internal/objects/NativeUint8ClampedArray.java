package jdk.nashorn.internal.objects;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Collections;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.arrays.ArrayData;
import jdk.nashorn.internal.runtime.arrays.TypedArrayData;

public final class NativeUint8ClampedArray extends ArrayBufferView {
   public static final int BYTES_PER_ELEMENT = 1;
   private static PropertyMap $nasgenmap$;
   private static final ArrayBufferView.Factory FACTORY = new ArrayBufferView.Factory(1) {
      public ArrayBufferView construct(NativeArrayBuffer buffer, int byteOffset, int length) {
         return new NativeUint8ClampedArray(buffer, byteOffset, length);
      }

      public NativeUint8ClampedArray.Uint8ClampedArrayData createArrayData(ByteBuffer nb, int start, int end) {
         return new NativeUint8ClampedArray.Uint8ClampedArrayData(nb, start, end);
      }

      public String getClassName() {
         return "Uint8ClampedArray";
      }
   };

   public static NativeUint8ClampedArray constructor(boolean newObj, Object self, Object... args) {
      return (NativeUint8ClampedArray)constructorImpl(newObj, args, FACTORY);
   }

   NativeUint8ClampedArray(NativeArrayBuffer buffer, int byteOffset, int length) {
      super(buffer, byteOffset, length);
   }

   protected ArrayBufferView.Factory factory() {
      return FACTORY;
   }

   protected static Object set(Object self, Object array, Object offset) {
      return ArrayBufferView.setImpl(self, array, offset);
   }

   protected static NativeUint8ClampedArray subarray(Object self, Object begin, Object end) {
      return (NativeUint8ClampedArray)ArrayBufferView.subarrayImpl(self, begin, end);
   }

   protected ScriptObject getPrototype(Global global) {
      return global.getUint8ClampedArrayPrototype();
   }

   static {
      $clinit$();
   }

   public static void $clinit$() {
      $nasgenmap$ = PropertyMap.newMap((Collection)Collections.EMPTY_LIST);
   }

   private static final class Uint8ClampedArrayData extends TypedArrayData<ByteBuffer> {
      private static final MethodHandle GET_ELEM;
      private static final MethodHandle SET_ELEM;
      private static final MethodHandle RINT_D;
      private static final MethodHandle RINT_O;
      private static final MethodHandle CLAMP_LONG;

      private Uint8ClampedArrayData(ByteBuffer nb, int start, int end) {
         super(((ByteBuffer)nb.position(start).limit(end)).slice(), end - start);
      }

      protected MethodHandle getGetElem() {
         return GET_ELEM;
      }

      protected MethodHandle getSetElem() {
         return SET_ELEM;
      }

      public Class<?> getElementType() {
         return Integer.TYPE;
      }

      public Class<?> getBoxedElementType() {
         return Integer.TYPE;
      }

      private int getElem(int index) {
         try {
            return ((ByteBuffer)this.nb).get(index) & 255;
         } catch (IndexOutOfBoundsException var3) {
            throw new ClassCastException();
         }
      }

      public MethodHandle getElementSetter(Class<?> elementType) {
         MethodHandle setter = super.getElementSetter(elementType);
         if (setter != null) {
            if (elementType == Object.class) {
               return Lookup.MH.filterArguments(setter, 2, RINT_O);
            }

            if (elementType == Double.TYPE) {
               return Lookup.MH.filterArguments(setter, 2, RINT_D);
            }

            if (elementType == Long.TYPE) {
               return Lookup.MH.filterArguments(setter, 2, CLAMP_LONG);
            }
         }

         return setter;
      }

      private void setElem(int index, int elem) {
         try {
            if (index < ((ByteBuffer)this.nb).limit()) {
               int clamped;
               if ((elem & -256) == 0) {
                  clamped = (byte)elem;
               } else {
                  clamped = elem < 0 ? 0 : -1;
               }

               ((ByteBuffer)this.nb).put(index, (byte)clamped);
            }

         } catch (IndexOutOfBoundsException var4) {
            throw new ClassCastException();
         }
      }

      public boolean isClamped() {
         return true;
      }

      public boolean isUnsigned() {
         return true;
      }

      public int getInt(int index) {
         return this.getElem(index);
      }

      public int getIntOptimistic(int index, int programPoint) {
         return this.getElem(index);
      }

      public double getDouble(int index) {
         return (double)this.getInt(index);
      }

      public double getDoubleOptimistic(int index, int programPoint) {
         return (double)this.getElem(index);
      }

      public Object getObject(int index) {
         return this.getInt(index);
      }

      public ArrayData set(int index, Object value, boolean strict) {
         return this.set(index, JSType.toNumber(value), strict);
      }

      public ArrayData set(int index, int value, boolean strict) {
         this.setElem(index, value);
         return this;
      }

      public ArrayData set(int index, double value, boolean strict) {
         return this.set(index, rint(value), strict);
      }

      private static double rint(double rint) {
         return (double)((int)Math.rint(rint));
      }

      private static Object rint(Object rint) {
         return rint(JSType.toNumber(rint));
      }

      private static long clampLong(long l) {
         if (l < 0L) {
            return 0L;
         } else {
            return l > 255L ? 255L : l;
         }
      }

      // $FF: synthetic method
      Uint8ClampedArrayData(ByteBuffer x0, int x1, int x2, Object x3) {
         this(x0, x1, x2);
      }

      static {
         GET_ELEM = CompilerConstants.specialCall(MethodHandles.lookup(), NativeUint8ClampedArray.Uint8ClampedArrayData.class, "getElem", Integer.TYPE, Integer.TYPE).methodHandle();
         SET_ELEM = CompilerConstants.specialCall(MethodHandles.lookup(), NativeUint8ClampedArray.Uint8ClampedArrayData.class, "setElem", Void.TYPE, Integer.TYPE, Integer.TYPE).methodHandle();
         RINT_D = CompilerConstants.staticCall(MethodHandles.lookup(), NativeUint8ClampedArray.Uint8ClampedArrayData.class, "rint", Double.TYPE, Double.TYPE).methodHandle();
         RINT_O = CompilerConstants.staticCall(MethodHandles.lookup(), NativeUint8ClampedArray.Uint8ClampedArrayData.class, "rint", Object.class, Object.class).methodHandle();
         CLAMP_LONG = CompilerConstants.staticCall(MethodHandles.lookup(), NativeUint8ClampedArray.Uint8ClampedArrayData.class, "clampLong", Long.TYPE, Long.TYPE).methodHandle();
      }
   }
}
