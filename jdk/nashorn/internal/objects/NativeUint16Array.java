package jdk.nashorn.internal.objects;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Collection;
import java.util.Collections;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.arrays.ArrayData;
import jdk.nashorn.internal.runtime.arrays.TypedArrayData;

public final class NativeUint16Array extends ArrayBufferView {
   public static final int BYTES_PER_ELEMENT = 2;
   private static PropertyMap $nasgenmap$;
   private static final ArrayBufferView.Factory FACTORY = new ArrayBufferView.Factory(2) {
      public ArrayBufferView construct(NativeArrayBuffer buffer, int byteOffset, int length) {
         return new NativeUint16Array(buffer, byteOffset, length);
      }

      public NativeUint16Array.Uint16ArrayData createArrayData(ByteBuffer nb, int start, int end) {
         return new NativeUint16Array.Uint16ArrayData(nb.asCharBuffer(), start, end);
      }

      public String getClassName() {
         return "Uint16Array";
      }
   };

   public static NativeUint16Array constructor(boolean newObj, Object self, Object... args) {
      return (NativeUint16Array)constructorImpl(newObj, args, FACTORY);
   }

   NativeUint16Array(NativeArrayBuffer buffer, int byteOffset, int length) {
      super(buffer, byteOffset, length);
   }

   protected ArrayBufferView.Factory factory() {
      return FACTORY;
   }

   protected static Object set(Object self, Object array, Object offset) {
      return ArrayBufferView.setImpl(self, array, offset);
   }

   protected static NativeUint16Array subarray(Object self, Object begin, Object end) {
      return (NativeUint16Array)ArrayBufferView.subarrayImpl(self, begin, end);
   }

   protected ScriptObject getPrototype(Global global) {
      return global.getUint16ArrayPrototype();
   }

   static {
      $clinit$();
   }

   public static void $clinit$() {
      $nasgenmap$ = PropertyMap.newMap((Collection)Collections.EMPTY_LIST);
   }

   private static final class Uint16ArrayData extends TypedArrayData<CharBuffer> {
      private static final MethodHandle GET_ELEM;
      private static final MethodHandle SET_ELEM;

      private Uint16ArrayData(CharBuffer nb, int start, int end) {
         super(((CharBuffer)nb.position(start).limit(end)).slice(), end - start);
      }

      protected MethodHandle getGetElem() {
         return GET_ELEM;
      }

      protected MethodHandle getSetElem() {
         return SET_ELEM;
      }

      private int getElem(int index) {
         try {
            return ((CharBuffer)this.nb).get(index);
         } catch (IndexOutOfBoundsException var3) {
            throw new ClassCastException();
         }
      }

      private void setElem(int index, int elem) {
         try {
            if (index < ((CharBuffer)this.nb).limit()) {
               ((CharBuffer)this.nb).put(index, (char)elem);
            }

         } catch (IndexOutOfBoundsException var4) {
            throw new ClassCastException();
         }
      }

      public boolean isUnsigned() {
         return true;
      }

      public Class<?> getElementType() {
         return Integer.TYPE;
      }

      public Class<?> getBoxedElementType() {
         return Integer.class;
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
      Uint16ArrayData(CharBuffer x0, int x1, int x2, Object x3) {
         this(x0, x1, x2);
      }

      static {
         GET_ELEM = CompilerConstants.specialCall(MethodHandles.lookup(), NativeUint16Array.Uint16ArrayData.class, "getElem", Integer.TYPE, Integer.TYPE).methodHandle();
         SET_ELEM = CompilerConstants.specialCall(MethodHandles.lookup(), NativeUint16Array.Uint16ArrayData.class, "setElem", Void.TYPE, Integer.TYPE, Integer.TYPE).methodHandle();
      }
   }
}
