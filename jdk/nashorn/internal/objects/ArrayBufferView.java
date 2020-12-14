package jdk.nashorn.internal.objects;

import java.lang.invoke.MethodHandle;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Collection;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.nashorn.internal.runtime.AccessorProperty;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.arrays.ArrayData;
import jdk.nashorn.internal.runtime.arrays.TypedArrayData;

public abstract class ArrayBufferView extends ScriptObject {
   private final NativeArrayBuffer buffer;
   private final int byteOffset;
   private static PropertyMap $nasgenmap$;

   private ArrayBufferView(NativeArrayBuffer buffer, int byteOffset, int elementLength, Global global) {
      super($nasgenmap$);
      int bytesPerElement = this.bytesPerElement();
      checkConstructorArgs(buffer.getByteLength(), bytesPerElement, byteOffset, elementLength);
      this.setProto(this.getPrototype(global));
      this.buffer = buffer;
      this.byteOffset = byteOffset;

      assert byteOffset % bytesPerElement == 0;

      int start = byteOffset / bytesPerElement;
      ByteBuffer newNioBuffer = buffer.getNioBuffer().duplicate().order(ByteOrder.nativeOrder());
      ArrayData data = this.factory().createArrayData(newNioBuffer, start, start + elementLength);
      this.setArray(data);
   }

   protected ArrayBufferView(NativeArrayBuffer buffer, int byteOffset, int elementLength) {
      this(buffer, byteOffset, elementLength, Global.instance());
   }

   private static void checkConstructorArgs(int byteLength, int bytesPerElement, int byteOffset, int elementLength) {
      if (byteOffset >= 0 && elementLength >= 0) {
         if (byteOffset + elementLength * bytesPerElement > byteLength) {
            throw new RuntimeException("byteOffset + byteLength out of range, byteOffset=" + byteOffset + ", elementLength=" + elementLength + ", bytesPerElement=" + bytesPerElement);
         } else if (byteOffset % bytesPerElement != 0) {
            throw new RuntimeException("byteOffset must be a multiple of the element size, byteOffset=" + byteOffset + " bytesPerElement=" + bytesPerElement);
         }
      } else {
         throw new RuntimeException("byteOffset or length must not be negative, byteOffset=" + byteOffset + ", elementLength=" + elementLength + ", bytesPerElement=" + bytesPerElement);
      }
   }

   private int bytesPerElement() {
      return this.factory().bytesPerElement;
   }

   public static Object buffer(Object self) {
      return ((ArrayBufferView)self).buffer;
   }

   public static int byteOffset(Object self) {
      return ((ArrayBufferView)self).byteOffset;
   }

   public static int byteLength(Object self) {
      ArrayBufferView view = (ArrayBufferView)self;
      return ((TypedArrayData)view.getArray()).getElementLength() * view.bytesPerElement();
   }

   public static int length(Object self) {
      return ((ArrayBufferView)self).elementLength();
   }

   public final Object getLength() {
      return this.elementLength();
   }

   private int elementLength() {
      return ((TypedArrayData)this.getArray()).getElementLength();
   }

   protected abstract ArrayBufferView.Factory factory();

   protected abstract ScriptObject getPrototype(Global var1);

   public final String getClassName() {
      return this.factory().getClassName();
   }

   protected boolean isFloatArray() {
      return false;
   }

   protected static ArrayBufferView constructorImpl(boolean newObj, Object[] args, ArrayBufferView.Factory factory) {
      Object arg0 = args.length != 0 ? args[0] : 0;
      if (!newObj) {
         throw ECMAErrors.typeError("constructor.requires.new", factory.getClassName());
      } else {
         int length;
         if (arg0 instanceof NativeArrayBuffer) {
            NativeArrayBuffer buffer = (NativeArrayBuffer)arg0;
            int byteOffset = args.length > 1 ? JSType.toInt32(args[1]) : 0;
            if (args.length > 2) {
               length = JSType.toInt32(args[2]);
            } else {
               if ((buffer.getByteLength() - byteOffset) % factory.bytesPerElement != 0) {
                  throw new RuntimeException("buffer.byteLength - byteOffset must be a multiple of the element size");
               }

               length = (buffer.getByteLength() - byteOffset) / factory.bytesPerElement;
            }

            return factory.construct(buffer, byteOffset, length);
         } else {
            ArrayBufferView dest;
            if (arg0 instanceof ArrayBufferView) {
               length = ((ArrayBufferView)arg0).elementLength();
               dest = factory.construct(length);
            } else {
               if (!(arg0 instanceof NativeArray)) {
                  double dlen = JSType.toNumber(arg0);
                  length = lengthToInt(Double.isInfinite(dlen) ? 0L : JSType.toLong(dlen));
                  return factory.construct(length);
               }

               length = lengthToInt(((NativeArray)arg0).getArray().length());
               dest = factory.construct(length);
            }

            copyElements(dest, length, (ScriptObject)arg0, 0);
            return dest;
         }
      }
   }

   protected static Object setImpl(Object self, Object array, Object offset0) {
      ArrayBufferView dest = (ArrayBufferView)self;
      int length;
      if (array instanceof ArrayBufferView) {
         length = ((ArrayBufferView)array).elementLength();
      } else {
         if (!(array instanceof NativeArray)) {
            throw new RuntimeException("argument is not of array type");
         }

         length = (int)(((NativeArray)array).getArray().length() & 2147483647L);
      }

      ScriptObject source = (ScriptObject)array;
      int offset = JSType.toInt32(offset0);
      if (dest.elementLength() >= length + offset && offset >= 0) {
         copyElements(dest, length, source, offset);
         return ScriptRuntime.UNDEFINED;
      } else {
         throw new RuntimeException("offset or array length out of bounds");
      }
   }

   private static void copyElements(ArrayBufferView dest, int length, ScriptObject source, int offset) {
      int i;
      int j;
      if (!dest.isFloatArray()) {
         i = 0;

         for(j = offset; i < length; ++j) {
            dest.set(j, source.getInt(i, -1), 0);
            ++i;
         }
      } else {
         i = 0;

         for(j = offset; i < length; ++j) {
            dest.set(j, source.getDouble(i, -1), 0);
            ++i;
         }
      }

   }

   private static int lengthToInt(long length) {
      if (length <= 2147483647L && length >= 0L) {
         return (int)(length & 2147483647L);
      } else {
         throw ECMAErrors.rangeError("inappropriate.array.buffer.length", JSType.toString((double)length));
      }
   }

   protected static ScriptObject subarrayImpl(Object self, Object begin0, Object end0) {
      ArrayBufferView arrayView = (ArrayBufferView)self;
      int byteOffset = arrayView.byteOffset;
      int bytesPerElement = arrayView.bytesPerElement();
      int elementLength = arrayView.elementLength();
      int begin = NativeArrayBuffer.adjustIndex(JSType.toInt32(begin0), elementLength);
      int end = NativeArrayBuffer.adjustIndex(end0 != ScriptRuntime.UNDEFINED ? JSType.toInt32(end0) : elementLength, elementLength);
      int length = Math.max(end - begin, 0);

      assert byteOffset % bytesPerElement == 0;

      return arrayView.factory().construct(arrayView.buffer, begin * bytesPerElement + byteOffset, length);
   }

   protected GuardedInvocation findGetIndexMethod(CallSiteDescriptor desc, LinkRequest request) {
      GuardedInvocation inv = this.getArray().findFastGetIndexMethod(this.getArray().getClass(), desc, request);
      return inv != null ? inv : super.findGetIndexMethod(desc, request);
   }

   protected GuardedInvocation findSetIndexMethod(CallSiteDescriptor desc, LinkRequest request) {
      GuardedInvocation inv = this.getArray().findFastSetIndexMethod(this.getArray().getClass(), desc, request);
      return inv != null ? inv : super.findSetIndexMethod(desc, request);
   }

   static {
      $clinit$();
   }

   public static void $clinit$() {
      ArrayList var10000 = new ArrayList(4);
      var10000.add(AccessorProperty.create("buffer", 7, "buffer", (MethodHandle)null));
      var10000.add(AccessorProperty.create("byteOffset", 7, "byteOffset", (MethodHandle)null));
      var10000.add(AccessorProperty.create("byteLength", 7, "byteLength", (MethodHandle)null));
      var10000.add(AccessorProperty.create("length", 7, "length", (MethodHandle)null));
      $nasgenmap$ = PropertyMap.newMap((Collection)var10000);
   }

   protected abstract static class Factory {
      final int bytesPerElement;
      final int maxElementLength;

      public Factory(int bytesPerElement) {
         this.bytesPerElement = bytesPerElement;
         this.maxElementLength = Integer.MAX_VALUE / bytesPerElement;
      }

      public final ArrayBufferView construct(int elementLength) {
         if (elementLength > this.maxElementLength) {
            throw ECMAErrors.rangeError("inappropriate.array.buffer.length", JSType.toString(elementLength));
         } else {
            return this.construct(new NativeArrayBuffer(elementLength * this.bytesPerElement), 0, elementLength);
         }
      }

      public abstract ArrayBufferView construct(NativeArrayBuffer var1, int var2, int var3);

      public abstract TypedArrayData<?> createArrayData(ByteBuffer var1, int var2, int var3);

      public abstract String getClassName();
   }
}
