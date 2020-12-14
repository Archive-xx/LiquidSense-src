package jdk.nashorn.internal.objects;

import java.lang.invoke.MethodHandle;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import jdk.nashorn.internal.runtime.AccessorProperty;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;

public final class NativeArrayBuffer extends ScriptObject {
   private final ByteBuffer nb;
   private static PropertyMap $nasgenmap$;

   protected NativeArrayBuffer(ByteBuffer nb, Global global) {
      super(global.getArrayBufferPrototype(), $nasgenmap$);
      this.nb = nb;
   }

   protected NativeArrayBuffer(ByteBuffer nb) {
      this(nb, Global.instance());
   }

   protected NativeArrayBuffer(int byteLength) {
      this(ByteBuffer.allocateDirect(byteLength));
   }

   protected NativeArrayBuffer(NativeArrayBuffer other, int begin, int end) {
      this(cloneBuffer(other.getNioBuffer(), begin, end));
   }

   public static NativeArrayBuffer constructor(boolean newObj, Object self, Object... args) {
      if (!newObj) {
         throw ECMAErrors.typeError("constructor.requires.new", "ArrayBuffer");
      } else {
         return args.length == 0 ? new NativeArrayBuffer(0) : new NativeArrayBuffer(JSType.toInt32(args[0]));
      }
   }

   private static ByteBuffer cloneBuffer(ByteBuffer original, int begin, int end) {
      ByteBuffer clone = ByteBuffer.allocateDirect(original.capacity());
      original.rewind();
      clone.put(original);
      original.rewind();
      clone.flip();
      clone.position(begin);
      clone.limit(end);
      return clone.slice();
   }

   ByteBuffer getNioBuffer() {
      return this.nb;
   }

   public String getClassName() {
      return "ArrayBuffer";
   }

   public static int byteLength(Object self) {
      return ((NativeArrayBuffer)self).getByteLength();
   }

   public static boolean isView(Object self, Object obj) {
      return obj instanceof ArrayBufferView;
   }

   public static NativeArrayBuffer slice(Object self, Object begin0, Object end0) {
      NativeArrayBuffer arrayBuffer = (NativeArrayBuffer)self;
      int byteLength = arrayBuffer.getByteLength();
      int begin = adjustIndex(JSType.toInt32(begin0), byteLength);
      int end = adjustIndex(end0 != ScriptRuntime.UNDEFINED ? JSType.toInt32(end0) : byteLength, byteLength);
      return new NativeArrayBuffer(arrayBuffer, begin, Math.max(end, begin));
   }

   public static Object slice(Object self, int begin, int end) {
      NativeArrayBuffer arrayBuffer = (NativeArrayBuffer)self;
      int byteLength = arrayBuffer.getByteLength();
      return new NativeArrayBuffer(arrayBuffer, adjustIndex(begin, byteLength), Math.max(adjustIndex(end, byteLength), begin));
   }

   public static Object slice(Object self, int begin) {
      return slice(self, begin, ((NativeArrayBuffer)self).getByteLength());
   }

   static int adjustIndex(int index, int length) {
      return index < 0 ? clamp(index + length, length) : clamp(index, length);
   }

   private static int clamp(int index, int length) {
      if (index < 0) {
         return 0;
      } else {
         return index > length ? length : index;
      }
   }

   int getByteLength() {
      return this.nb.limit();
   }

   ByteBuffer getBuffer() {
      return this.nb;
   }

   ByteBuffer getBuffer(int offset) {
      return (ByteBuffer)this.nb.duplicate().position(offset);
   }

   ByteBuffer getBuffer(int offset, int length) {
      return (ByteBuffer)this.getBuffer(offset).limit(length);
   }

   static {
      $clinit$();
   }

   public static void $clinit$() {
      ArrayList var10000 = new ArrayList(1);
      var10000.add(AccessorProperty.create("byteLength", 7, "byteLength", (MethodHandle)null));
      $nasgenmap$ = PropertyMap.newMap((Collection)var10000);
   }
}
