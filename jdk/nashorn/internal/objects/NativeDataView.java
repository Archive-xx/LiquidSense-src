package jdk.nashorn.internal.objects;

import java.lang.invoke.MethodHandle;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Collection;
import jdk.nashorn.internal.runtime.AccessorProperty;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;

public class NativeDataView extends ScriptObject {
   private static PropertyMap $nasgenmap$;
   public final Object buffer;
   public final int byteOffset;
   public final int byteLength;
   private final ByteBuffer buf;

   private NativeDataView(NativeArrayBuffer arrBuf) {
      this(arrBuf, arrBuf.getBuffer(), 0);
   }

   private NativeDataView(NativeArrayBuffer arrBuf, int offset) {
      this(arrBuf, bufferFrom(arrBuf, offset), offset);
   }

   private NativeDataView(NativeArrayBuffer arrBuf, int offset, int length) {
      this(arrBuf, bufferFrom(arrBuf, offset, length), offset, length);
   }

   private NativeDataView(NativeArrayBuffer arrBuf, ByteBuffer buf, int offset) {
      this(arrBuf, buf, offset, buf.capacity() - offset);
   }

   private NativeDataView(NativeArrayBuffer arrBuf, ByteBuffer buf, int offset, int length) {
      super(Global.instance().getDataViewPrototype(), $nasgenmap$);
      this.buffer = arrBuf;
      this.byteOffset = offset;
      this.byteLength = length;
      this.buf = buf;
   }

   public static NativeDataView constructor(boolean newObj, Object self, Object... args) {
      if (args.length != 0 && args[0] instanceof NativeArrayBuffer) {
         NativeArrayBuffer arrBuf = (NativeArrayBuffer)args[0];
         switch(args.length) {
         case 1:
            return new NativeDataView(arrBuf);
         case 2:
            return new NativeDataView(arrBuf, JSType.toInt32(args[1]));
         default:
            return new NativeDataView(arrBuf, JSType.toInt32(args[1]), JSType.toInt32(args[2]));
         }
      } else {
         throw ECMAErrors.typeError("not.an.arraybuffer.in.dataview");
      }
   }

   public static NativeDataView constructor(boolean newObj, Object self, Object arrBuf, int offset) {
      if (!(arrBuf instanceof NativeArrayBuffer)) {
         throw ECMAErrors.typeError("not.an.arraybuffer.in.dataview");
      } else {
         return new NativeDataView((NativeArrayBuffer)arrBuf, offset);
      }
   }

   public static NativeDataView constructor(boolean newObj, Object self, Object arrBuf, int offset, int length) {
      if (!(arrBuf instanceof NativeArrayBuffer)) {
         throw ECMAErrors.typeError("not.an.arraybuffer.in.dataview");
      } else {
         return new NativeDataView((NativeArrayBuffer)arrBuf, offset, length);
      }
   }

   public static int getInt8(Object self, Object byteOffset) {
      try {
         return getBuffer(self).get(JSType.toInt32(byteOffset));
      } catch (IllegalArgumentException var3) {
         throw ECMAErrors.rangeError((Throwable)var3, "dataview.offset");
      }
   }

   public static int getInt8(Object self, int byteOffset) {
      try {
         return getBuffer(self).get(byteOffset);
      } catch (IllegalArgumentException var3) {
         throw ECMAErrors.rangeError((Throwable)var3, "dataview.offset");
      }
   }

   public static int getUint8(Object self, Object byteOffset) {
      try {
         return 255 & getBuffer(self).get(JSType.toInt32(byteOffset));
      } catch (IllegalArgumentException var3) {
         throw ECMAErrors.rangeError((Throwable)var3, "dataview.offset");
      }
   }

   public static int getUint8(Object self, int byteOffset) {
      try {
         return 255 & getBuffer(self).get(byteOffset);
      } catch (IllegalArgumentException var3) {
         throw ECMAErrors.rangeError((Throwable)var3, "dataview.offset");
      }
   }

   public static int getInt16(Object self, Object byteOffset, Object littleEndian) {
      try {
         return getBuffer(self, littleEndian).getShort(JSType.toInt32(byteOffset));
      } catch (IllegalArgumentException var4) {
         throw ECMAErrors.rangeError((Throwable)var4, "dataview.offset");
      }
   }

   public static int getInt16(Object self, int byteOffset) {
      try {
         return getBuffer(self, false).getShort(byteOffset);
      } catch (IllegalArgumentException var3) {
         throw ECMAErrors.rangeError((Throwable)var3, "dataview.offset");
      }
   }

   public static int getInt16(Object self, int byteOffset, boolean littleEndian) {
      try {
         return getBuffer(self, littleEndian).getShort(byteOffset);
      } catch (IllegalArgumentException var4) {
         throw ECMAErrors.rangeError((Throwable)var4, "dataview.offset");
      }
   }

   public static int getUint16(Object self, Object byteOffset, Object littleEndian) {
      try {
         return '\uffff' & getBuffer(self, littleEndian).getShort(JSType.toInt32(byteOffset));
      } catch (IllegalArgumentException var4) {
         throw ECMAErrors.rangeError((Throwable)var4, "dataview.offset");
      }
   }

   public static int getUint16(Object self, int byteOffset) {
      try {
         return '\uffff' & getBuffer(self, false).getShort(byteOffset);
      } catch (IllegalArgumentException var3) {
         throw ECMAErrors.rangeError((Throwable)var3, "dataview.offset");
      }
   }

   public static int getUint16(Object self, int byteOffset, boolean littleEndian) {
      try {
         return '\uffff' & getBuffer(self, littleEndian).getShort(byteOffset);
      } catch (IllegalArgumentException var4) {
         throw ECMAErrors.rangeError((Throwable)var4, "dataview.offset");
      }
   }

   public static int getInt32(Object self, Object byteOffset, Object littleEndian) {
      try {
         return getBuffer(self, littleEndian).getInt(JSType.toInt32(byteOffset));
      } catch (IllegalArgumentException var4) {
         throw ECMAErrors.rangeError((Throwable)var4, "dataview.offset");
      }
   }

   public static int getInt32(Object self, int byteOffset) {
      try {
         return getBuffer(self, false).getInt(byteOffset);
      } catch (IllegalArgumentException var3) {
         throw ECMAErrors.rangeError((Throwable)var3, "dataview.offset");
      }
   }

   public static int getInt32(Object self, int byteOffset, boolean littleEndian) {
      try {
         return getBuffer(self, littleEndian).getInt(byteOffset);
      } catch (IllegalArgumentException var4) {
         throw ECMAErrors.rangeError((Throwable)var4, "dataview.offset");
      }
   }

   public static double getUint32(Object self, Object byteOffset, Object littleEndian) {
      try {
         return (double)(4294967295L & (long)getBuffer(self, littleEndian).getInt(JSType.toInt32(byteOffset)));
      } catch (IllegalArgumentException var4) {
         throw ECMAErrors.rangeError((Throwable)var4, "dataview.offset");
      }
   }

   public static double getUint32(Object self, int byteOffset) {
      try {
         return (double)JSType.toUint32(getBuffer(self, false).getInt(JSType.toInt32((long)byteOffset)));
      } catch (IllegalArgumentException var3) {
         throw ECMAErrors.rangeError((Throwable)var3, "dataview.offset");
      }
   }

   public static double getUint32(Object self, int byteOffset, boolean littleEndian) {
      try {
         return (double)JSType.toUint32(getBuffer(self, littleEndian).getInt(JSType.toInt32((long)byteOffset)));
      } catch (IllegalArgumentException var4) {
         throw ECMAErrors.rangeError((Throwable)var4, "dataview.offset");
      }
   }

   public static double getFloat32(Object self, Object byteOffset, Object littleEndian) {
      try {
         return (double)getBuffer(self, littleEndian).getFloat(JSType.toInt32(byteOffset));
      } catch (IllegalArgumentException var4) {
         throw ECMAErrors.rangeError((Throwable)var4, "dataview.offset");
      }
   }

   public static double getFloat32(Object self, int byteOffset) {
      try {
         return (double)getBuffer(self, false).getFloat(byteOffset);
      } catch (IllegalArgumentException var3) {
         throw ECMAErrors.rangeError((Throwable)var3, "dataview.offset");
      }
   }

   public static double getFloat32(Object self, int byteOffset, boolean littleEndian) {
      try {
         return (double)getBuffer(self, littleEndian).getFloat(byteOffset);
      } catch (IllegalArgumentException var4) {
         throw ECMAErrors.rangeError((Throwable)var4, "dataview.offset");
      }
   }

   public static double getFloat64(Object self, Object byteOffset, Object littleEndian) {
      try {
         return getBuffer(self, littleEndian).getDouble(JSType.toInt32(byteOffset));
      } catch (IllegalArgumentException var4) {
         throw ECMAErrors.rangeError((Throwable)var4, "dataview.offset");
      }
   }

   public static double getFloat64(Object self, int byteOffset) {
      try {
         return getBuffer(self, false).getDouble(byteOffset);
      } catch (IllegalArgumentException var3) {
         throw ECMAErrors.rangeError((Throwable)var3, "dataview.offset");
      }
   }

   public static double getFloat64(Object self, int byteOffset, boolean littleEndian) {
      try {
         return getBuffer(self, littleEndian).getDouble(byteOffset);
      } catch (IllegalArgumentException var4) {
         throw ECMAErrors.rangeError((Throwable)var4, "dataview.offset");
      }
   }

   public static Object setInt8(Object self, Object byteOffset, Object value) {
      try {
         getBuffer(self).put(JSType.toInt32(byteOffset), (byte)JSType.toInt32(value));
         return ScriptRuntime.UNDEFINED;
      } catch (IllegalArgumentException var4) {
         throw ECMAErrors.rangeError((Throwable)var4, "dataview.offset");
      }
   }

   public static Object setInt8(Object self, int byteOffset, int value) {
      try {
         getBuffer(self).put(byteOffset, (byte)value);
         return ScriptRuntime.UNDEFINED;
      } catch (IllegalArgumentException var4) {
         throw ECMAErrors.rangeError((Throwable)var4, "dataview.offset");
      }
   }

   public static Object setUint8(Object self, Object byteOffset, Object value) {
      try {
         getBuffer(self).put(JSType.toInt32(byteOffset), (byte)JSType.toInt32(value));
         return ScriptRuntime.UNDEFINED;
      } catch (IllegalArgumentException var4) {
         throw ECMAErrors.rangeError((Throwable)var4, "dataview.offset");
      }
   }

   public static Object setUint8(Object self, int byteOffset, int value) {
      try {
         getBuffer(self).put(byteOffset, (byte)value);
         return ScriptRuntime.UNDEFINED;
      } catch (IllegalArgumentException var4) {
         throw ECMAErrors.rangeError((Throwable)var4, "dataview.offset");
      }
   }

   public static Object setInt16(Object self, Object byteOffset, Object value, Object littleEndian) {
      try {
         getBuffer(self, littleEndian).putShort(JSType.toInt32(byteOffset), (short)JSType.toInt32(value));
         return ScriptRuntime.UNDEFINED;
      } catch (IllegalArgumentException var5) {
         throw ECMAErrors.rangeError((Throwable)var5, "dataview.offset");
      }
   }

   public static Object setInt16(Object self, int byteOffset, int value) {
      try {
         getBuffer(self, false).putShort(byteOffset, (short)value);
         return ScriptRuntime.UNDEFINED;
      } catch (IllegalArgumentException var4) {
         throw ECMAErrors.rangeError((Throwable)var4, "dataview.offset");
      }
   }

   public static Object setInt16(Object self, int byteOffset, int value, boolean littleEndian) {
      try {
         getBuffer(self, littleEndian).putShort(byteOffset, (short)value);
         return ScriptRuntime.UNDEFINED;
      } catch (IllegalArgumentException var5) {
         throw ECMAErrors.rangeError((Throwable)var5, "dataview.offset");
      }
   }

   public static Object setUint16(Object self, Object byteOffset, Object value, Object littleEndian) {
      try {
         getBuffer(self, littleEndian).putShort(JSType.toInt32(byteOffset), (short)JSType.toInt32(value));
         return ScriptRuntime.UNDEFINED;
      } catch (IllegalArgumentException var5) {
         throw ECMAErrors.rangeError((Throwable)var5, "dataview.offset");
      }
   }

   public static Object setUint16(Object self, int byteOffset, int value) {
      try {
         getBuffer(self, false).putShort(byteOffset, (short)value);
         return ScriptRuntime.UNDEFINED;
      } catch (IllegalArgumentException var4) {
         throw ECMAErrors.rangeError((Throwable)var4, "dataview.offset");
      }
   }

   public static Object setUint16(Object self, int byteOffset, int value, boolean littleEndian) {
      try {
         getBuffer(self, littleEndian).putShort(byteOffset, (short)value);
         return ScriptRuntime.UNDEFINED;
      } catch (IllegalArgumentException var5) {
         throw ECMAErrors.rangeError((Throwable)var5, "dataview.offset");
      }
   }

   public static Object setInt32(Object self, Object byteOffset, Object value, Object littleEndian) {
      try {
         getBuffer(self, littleEndian).putInt(JSType.toInt32(byteOffset), JSType.toInt32(value));
         return ScriptRuntime.UNDEFINED;
      } catch (IllegalArgumentException var5) {
         throw ECMAErrors.rangeError((Throwable)var5, "dataview.offset");
      }
   }

   public static Object setInt32(Object self, int byteOffset, int value) {
      try {
         getBuffer(self, false).putInt(byteOffset, value);
         return ScriptRuntime.UNDEFINED;
      } catch (IllegalArgumentException var4) {
         throw ECMAErrors.rangeError((Throwable)var4, "dataview.offset");
      }
   }

   public static Object setInt32(Object self, int byteOffset, int value, boolean littleEndian) {
      try {
         getBuffer(self, littleEndian).putInt(byteOffset, value);
         return ScriptRuntime.UNDEFINED;
      } catch (IllegalArgumentException var5) {
         throw ECMAErrors.rangeError((Throwable)var5, "dataview.offset");
      }
   }

   public static Object setUint32(Object self, Object byteOffset, Object value, Object littleEndian) {
      try {
         getBuffer(self, littleEndian).putInt(JSType.toInt32(byteOffset), (int)JSType.toUint32(value));
         return ScriptRuntime.UNDEFINED;
      } catch (IllegalArgumentException var5) {
         throw ECMAErrors.rangeError((Throwable)var5, "dataview.offset");
      }
   }

   public static Object setUint32(Object self, int byteOffset, double value) {
      try {
         getBuffer(self, false).putInt(byteOffset, (int)JSType.toUint32(value));
         return ScriptRuntime.UNDEFINED;
      } catch (IllegalArgumentException var5) {
         throw ECMAErrors.rangeError((Throwable)var5, "dataview.offset");
      }
   }

   public static Object setUint32(Object self, int byteOffset, double value, boolean littleEndian) {
      try {
         getBuffer(self, littleEndian).putInt(byteOffset, (int)JSType.toUint32(value));
         return ScriptRuntime.UNDEFINED;
      } catch (IllegalArgumentException var6) {
         throw ECMAErrors.rangeError((Throwable)var6, "dataview.offset");
      }
   }

   public static Object setFloat32(Object self, Object byteOffset, Object value, Object littleEndian) {
      try {
         getBuffer(self, littleEndian).putFloat((int)JSType.toUint32(byteOffset), (float)JSType.toNumber(value));
         return ScriptRuntime.UNDEFINED;
      } catch (IllegalArgumentException var5) {
         throw ECMAErrors.rangeError((Throwable)var5, "dataview.offset");
      }
   }

   public static Object setFloat32(Object self, int byteOffset, double value) {
      try {
         getBuffer(self, false).putFloat(byteOffset, (float)value);
         return ScriptRuntime.UNDEFINED;
      } catch (IllegalArgumentException var5) {
         throw ECMAErrors.rangeError((Throwable)var5, "dataview.offset");
      }
   }

   public static Object setFloat32(Object self, int byteOffset, double value, boolean littleEndian) {
      try {
         getBuffer(self, littleEndian).putFloat(byteOffset, (float)value);
         return ScriptRuntime.UNDEFINED;
      } catch (IllegalArgumentException var6) {
         throw ECMAErrors.rangeError((Throwable)var6, "dataview.offset");
      }
   }

   public static Object setFloat64(Object self, Object byteOffset, Object value, Object littleEndian) {
      try {
         getBuffer(self, littleEndian).putDouble((int)JSType.toUint32(byteOffset), JSType.toNumber(value));
         return ScriptRuntime.UNDEFINED;
      } catch (IllegalArgumentException var5) {
         throw ECMAErrors.rangeError((Throwable)var5, "dataview.offset");
      }
   }

   public static Object setFloat64(Object self, int byteOffset, double value) {
      try {
         getBuffer(self, false).putDouble(byteOffset, value);
         return ScriptRuntime.UNDEFINED;
      } catch (IllegalArgumentException var5) {
         throw ECMAErrors.rangeError((Throwable)var5, "dataview.offset");
      }
   }

   public static Object setFloat64(Object self, int byteOffset, double value, boolean littleEndian) {
      try {
         getBuffer(self, littleEndian).putDouble(byteOffset, value);
         return ScriptRuntime.UNDEFINED;
      } catch (IllegalArgumentException var6) {
         throw ECMAErrors.rangeError((Throwable)var6, "dataview.offset");
      }
   }

   private static ByteBuffer bufferFrom(NativeArrayBuffer nab, int offset) {
      try {
         return nab.getBuffer(offset);
      } catch (IllegalArgumentException var3) {
         throw ECMAErrors.rangeError((Throwable)var3, "dataview.constructor.offset");
      }
   }

   private static ByteBuffer bufferFrom(NativeArrayBuffer nab, int offset, int length) {
      try {
         return nab.getBuffer(offset, length);
      } catch (IllegalArgumentException var4) {
         throw ECMAErrors.rangeError((Throwable)var4, "dataview.constructor.offset");
      }
   }

   private static NativeDataView checkSelf(Object self) {
      if (!(self instanceof NativeDataView)) {
         throw ECMAErrors.typeError("not.an.arraybuffer.in.dataview", ScriptRuntime.safeToString(self));
      } else {
         return (NativeDataView)self;
      }
   }

   private static ByteBuffer getBuffer(Object self) {
      return checkSelf(self).buf;
   }

   private static ByteBuffer getBuffer(Object self, Object littleEndian) {
      return getBuffer(self, JSType.toBoolean(littleEndian));
   }

   private static ByteBuffer getBuffer(Object self, boolean littleEndian) {
      return getBuffer(self).order(littleEndian ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN);
   }

   static {
      $clinit$();
   }

   public static void $clinit$() {
      ArrayList var10000 = new ArrayList(3);
      var10000.add(AccessorProperty.create("buffer", 7, "G$buffer", (MethodHandle)null));
      var10000.add(AccessorProperty.create("byteOffset", 7, "G$byteOffset", (MethodHandle)null));
      var10000.add(AccessorProperty.create("byteLength", 7, "G$byteLength", (MethodHandle)null));
      $nasgenmap$ = PropertyMap.newMap((Collection)var10000);
   }

   public Object G$buffer() {
      return this.buffer;
   }

   public int G$byteOffset() {
      return this.byteOffset;
   }

   public int G$byteLength() {
      return this.byteLength;
   }
}
