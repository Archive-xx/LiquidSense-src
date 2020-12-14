package jdk.nashorn.internal.objects;

import java.lang.invoke.MethodHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.internal.objects.annotations.SpecializedFunction;
import jdk.nashorn.internal.runtime.AccessorProperty;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.Debug;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.OptimisticBuiltins;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.Undefined;
import jdk.nashorn.internal.runtime.arrays.ArrayData;
import jdk.nashorn.internal.runtime.arrays.ArrayIndex;
import jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator;
import jdk.nashorn.internal.runtime.arrays.ContinuousArrayData;
import jdk.nashorn.internal.runtime.arrays.IntElements;
import jdk.nashorn.internal.runtime.arrays.IteratorAction;
import jdk.nashorn.internal.runtime.arrays.NumericElements;
import jdk.nashorn.internal.runtime.linker.Bootstrap;
import jdk.nashorn.internal.runtime.linker.InvokeByName;

public final class NativeArray extends ScriptObject implements OptimisticBuiltins {
   private static final Object JOIN = new Object();
   private static final Object EVERY_CALLBACK_INVOKER = new Object();
   private static final Object SOME_CALLBACK_INVOKER = new Object();
   private static final Object FOREACH_CALLBACK_INVOKER = new Object();
   private static final Object MAP_CALLBACK_INVOKER = new Object();
   private static final Object FILTER_CALLBACK_INVOKER = new Object();
   private static final Object REDUCE_CALLBACK_INVOKER = new Object();
   private static final Object CALL_CMP = new Object();
   private static final Object TO_LOCALE_STRING = new Object();
   private static PropertyMap $nasgenmap$;

   NativeArray() {
      this(ArrayData.initialArray());
   }

   NativeArray(long length) {
      this(ArrayData.allocate(length));
   }

   NativeArray(int[] array) {
      this(ArrayData.allocate(array));
   }

   NativeArray(double[] array) {
      this(ArrayData.allocate(array));
   }

   NativeArray(long[] array) {
      this(ArrayData.allocate((long)array.length));
      ArrayData arrayData = this.getArray();
      Class<?> widest = Integer.TYPE;

      for(int index = 0; index < array.length; ++index) {
         long value = array[index];
         if (widest == Integer.TYPE && JSType.isRepresentableAsInt(value)) {
            arrayData = arrayData.set(index, (int)value, false);
         } else if (widest != Object.class && JSType.isRepresentableAsDouble(value)) {
            arrayData = arrayData.set(index, (double)value, false);
            widest = Double.TYPE;
         } else {
            arrayData = arrayData.set(index, value, false);
            widest = Object.class;
         }
      }

      this.setArray(arrayData);
   }

   NativeArray(Object[] array) {
      this(ArrayData.allocate((long)array.length));
      ArrayData arrayData = this.getArray();

      for(int index = 0; index < array.length; ++index) {
         Object value = array[index];
         if (value == ScriptRuntime.EMPTY) {
            arrayData = arrayData.delete(index);
         } else {
            arrayData = arrayData.set(index, value, false);
         }
      }

      this.setArray(arrayData);
   }

   NativeArray(ArrayData arrayData) {
      this(arrayData, Global.instance());
   }

   NativeArray(ArrayData arrayData, Global global) {
      super(global.getArrayPrototype(), $nasgenmap$);
      this.setArray(arrayData);
      this.setIsArray();
   }

   protected GuardedInvocation findGetMethod(CallSiteDescriptor desc, LinkRequest request, String operator) {
      GuardedInvocation inv = this.getArray().findFastGetMethod(this.getArray().getClass(), desc, request, operator);
      return inv != null ? inv : super.findGetMethod(desc, request, operator);
   }

   protected GuardedInvocation findGetIndexMethod(CallSiteDescriptor desc, LinkRequest request) {
      GuardedInvocation inv = this.getArray().findFastGetIndexMethod(this.getArray().getClass(), desc, request);
      return inv != null ? inv : super.findGetIndexMethod(desc, request);
   }

   protected GuardedInvocation findSetIndexMethod(CallSiteDescriptor desc, LinkRequest request) {
      GuardedInvocation inv = this.getArray().findFastSetIndexMethod(this.getArray().getClass(), desc, request);
      return inv != null ? inv : super.findSetIndexMethod(desc, request);
   }

   private static InvokeByName getJOIN() {
      return Global.instance().getInvokeByName(JOIN, new Callable<InvokeByName>() {
         public InvokeByName call() {
            return new InvokeByName("join", ScriptObject.class);
         }
      });
   }

   private static MethodHandle createIteratorCallbackInvoker(Object key, final Class<?> rtype) {
      return Global.instance().getDynamicInvoker(key, new Callable<MethodHandle>() {
         public MethodHandle call() {
            return Bootstrap.createDynamicInvoker("dyn:call", rtype, Object.class, Object.class, Object.class, Double.TYPE, Object.class);
         }
      });
   }

   private static MethodHandle getEVERY_CALLBACK_INVOKER() {
      return createIteratorCallbackInvoker(EVERY_CALLBACK_INVOKER, Boolean.TYPE);
   }

   private static MethodHandle getSOME_CALLBACK_INVOKER() {
      return createIteratorCallbackInvoker(SOME_CALLBACK_INVOKER, Boolean.TYPE);
   }

   private static MethodHandle getFOREACH_CALLBACK_INVOKER() {
      return createIteratorCallbackInvoker(FOREACH_CALLBACK_INVOKER, Void.TYPE);
   }

   private static MethodHandle getMAP_CALLBACK_INVOKER() {
      return createIteratorCallbackInvoker(MAP_CALLBACK_INVOKER, Object.class);
   }

   private static MethodHandle getFILTER_CALLBACK_INVOKER() {
      return createIteratorCallbackInvoker(FILTER_CALLBACK_INVOKER, Boolean.TYPE);
   }

   private static MethodHandle getREDUCE_CALLBACK_INVOKER() {
      return Global.instance().getDynamicInvoker(REDUCE_CALLBACK_INVOKER, new Callable<MethodHandle>() {
         public MethodHandle call() {
            return Bootstrap.createDynamicInvoker("dyn:call", Object.class, Object.class, Undefined.class, Object.class, Object.class, Double.TYPE, Object.class);
         }
      });
   }

   private static MethodHandle getCALL_CMP() {
      return Global.instance().getDynamicInvoker(CALL_CMP, new Callable<MethodHandle>() {
         public MethodHandle call() {
            return Bootstrap.createDynamicInvoker("dyn:call", Double.TYPE, Object.class, Object.class, Object.class, Object.class);
         }
      });
   }

   private static InvokeByName getTO_LOCALE_STRING() {
      return Global.instance().getInvokeByName(TO_LOCALE_STRING, new Callable<InvokeByName>() {
         public InvokeByName call() {
            return new InvokeByName("toLocaleString", ScriptObject.class, String.class, new Class[0]);
         }
      });
   }

   public String getClassName() {
      return "Array";
   }

   public Object getLength() {
      long length = this.getArray().length();

      assert length >= 0L;

      return length <= 2147483647L ? (int)length : length;
   }

   private boolean defineLength(long oldLen, PropertyDescriptor oldLenDesc, PropertyDescriptor desc, boolean reject) {
      if (!desc.has("value")) {
         return super.defineOwnProperty("length", desc, reject);
      } else {
         long newLen = validLength(desc.getValue());
         desc.setValue(JSType.toNarrowestNumber(newLen));
         if (newLen >= oldLen) {
            return super.defineOwnProperty("length", desc, reject);
         } else if (!oldLenDesc.isWritable()) {
            if (reject) {
               throw ECMAErrors.typeError("property.not.writable", "length", ScriptRuntime.safeToString(this));
            } else {
               return false;
            }
         } else {
            boolean newWritable = !desc.has("writable") || desc.isWritable();
            if (!newWritable) {
               desc.setWritable(true);
            }

            boolean succeeded = super.defineOwnProperty("length", desc, reject);
            if (!succeeded) {
               return false;
            } else {
               long o = oldLen;

               boolean deleteSucceeded;
               do {
                  if (newLen >= o) {
                     if (!newWritable) {
                        ScriptObject newDesc = Global.newEmptyInstance();
                        newDesc.set("writable", false, 0);
                        return super.defineOwnProperty("length", newDesc, false);
                     }

                     return true;
                  }

                  --o;
                  deleteSucceeded = this.delete((double)o, false);
               } while(deleteSucceeded);

               desc.setValue(o + 1L);
               if (!newWritable) {
                  desc.setWritable(false);
               }

               super.defineOwnProperty("length", desc, false);
               if (reject) {
                  throw ECMAErrors.typeError("property.not.writable", "length", ScriptRuntime.safeToString(this));
               } else {
                  return false;
               }
            }
         }
      }
   }

   public boolean defineOwnProperty(String key, Object propertyDesc, boolean reject) {
      PropertyDescriptor desc = toPropertyDescriptor(Global.instance(), propertyDesc);
      PropertyDescriptor oldLenDesc = (PropertyDescriptor)super.getOwnPropertyDescriptor("length");
      long oldLen = JSType.toUint32(oldLenDesc.getValue());
      if ("length".equals(key)) {
         boolean result = this.defineLength(oldLen, oldLenDesc, desc, reject);
         if (desc.has("writable") && !desc.isWritable()) {
            this.setIsLengthNotWritable();
         }

         return result;
      } else {
         int index = ArrayIndex.getArrayIndex(key);
         if (ArrayIndex.isValidArrayIndex(index)) {
            long longIndex = ArrayIndex.toLongIndex(index);
            if (longIndex >= oldLen && !oldLenDesc.isWritable()) {
               if (reject) {
                  throw ECMAErrors.typeError("property.not.writable", Long.toString(longIndex), ScriptRuntime.safeToString(this));
               } else {
                  return false;
               }
            } else {
               boolean succeeded = super.defineOwnProperty(key, desc, false);
               if (!succeeded) {
                  if (reject) {
                     throw ECMAErrors.typeError("cant.redefine.property", key, ScriptRuntime.safeToString(this));
                  } else {
                     return false;
                  }
               } else {
                  if (longIndex >= oldLen) {
                     oldLenDesc.setValue(longIndex + 1L);
                     super.defineOwnProperty("length", oldLenDesc, false);
                  }

                  return true;
               }
            }
         } else {
            return super.defineOwnProperty(key, desc, reject);
         }
      }
   }

   public final void defineOwnProperty(int index, Object value) {
      assert ArrayIndex.isValidArrayIndex(index) : "invalid array index";

      long longIndex = ArrayIndex.toLongIndex(index);
      if (longIndex >= this.getArray().length()) {
         this.setArray(this.getArray().ensure(longIndex));
      }

      this.setArray(this.getArray().set(index, value, false));
   }

   public Object[] asObjectArray() {
      return this.getArray().asObjectArray();
   }

   public void setIsLengthNotWritable() {
      super.setIsLengthNotWritable();
      this.setArray(ArrayData.setIsLengthNotWritable(this.getArray()));
   }

   public static boolean isArray(Object self, Object arg) {
      return isArray(arg) || arg instanceof JSObject && ((JSObject)arg).isArray();
   }

   public static Object length(Object self) {
      if (isArray(self)) {
         long length = ((ScriptObject)self).getArray().length();

         assert length >= 0L;

         return length <= 2147483647L ? (int)length : (double)length;
      } else {
         return 0;
      }
   }

   public static void length(Object self, Object length) {
      if (isArray(self)) {
         ((ScriptObject)self).setLength(validLength(length));
      }

   }

   public static Object getProtoLength(Object self) {
      return length(self);
   }

   public static void setProtoLength(Object self, Object length) {
      length(self, length);
   }

   static long validLength(Object length) {
      double doubleLength = JSType.toNumber(length);
      if (doubleLength != (double)JSType.toUint32(length)) {
         throw ECMAErrors.rangeError("inappropriate.array.length", ScriptRuntime.safeToString(length));
      } else {
         return (long)doubleLength;
      }
   }

   public static Object toString(Object self) {
      Object obj = Global.toObject(self);
      if (obj instanceof ScriptObject) {
         InvokeByName joinInvoker = getJOIN();
         ScriptObject sobj = (ScriptObject)obj;

         try {
            Object join = joinInvoker.getGetter().invokeExact(sobj);
            if (Bootstrap.isCallable(join)) {
               return joinInvoker.getInvoker().invokeExact(join, sobj);
            }
         } catch (Error | RuntimeException var5) {
            throw var5;
         } catch (Throwable var6) {
            throw new RuntimeException(var6);
         }
      }

      return ScriptRuntime.builtinObjectToString(self);
   }

   public static Object assertNumeric(Object self) {
      if (self instanceof NativeArray && ((NativeArray)self).getArray().getOptimisticType().isNumeric()) {
         return Boolean.TRUE;
      } else {
         throw ECMAErrors.typeError("not.a.numeric.array", ScriptRuntime.safeToString(self));
      }
   }

   public static String toLocaleString(Object self) {
      StringBuilder sb = new StringBuilder();
      ArrayLikeIterator iter = ArrayLikeIterator.arrayLikeIterator(self, true);

      while(iter.hasNext()) {
         Object obj = iter.next();
         if (obj != null && obj != ScriptRuntime.UNDEFINED) {
            Object val = JSType.toScriptObject(obj);

            try {
               if (val instanceof ScriptObject) {
                  InvokeByName localeInvoker = getTO_LOCALE_STRING();
                  ScriptObject sobj = (ScriptObject)val;
                  Object toLocaleString = localeInvoker.getGetter().invokeExact(sobj);
                  if (!Bootstrap.isCallable(toLocaleString)) {
                     throw ECMAErrors.typeError("not.a.function", "toLocaleString");
                  }

                  sb.append(localeInvoker.getInvoker().invokeExact(toLocaleString, sobj));
               }
            } catch (RuntimeException | Error var8) {
               throw var8;
            } catch (Throwable var9) {
               throw new RuntimeException(var9);
            }
         }

         if (iter.hasNext()) {
            sb.append(",");
         }
      }

      return sb.toString();
   }

   public static NativeArray construct(boolean newObj, Object self, Object... args) {
      switch(args.length) {
      case 0:
         return new NativeArray(0L);
      case 1:
         Object len = args[0];
         if (!(len instanceof Number)) {
            return new NativeArray(new Object[]{args[0]});
         } else {
            long length;
            if (len instanceof Integer || len instanceof Long) {
               length = ((Number)len).longValue();
               if (length >= 0L && length < 4294967295L) {
                  return new NativeArray(length);
               }
            }

            length = JSType.toUint32(len);
            double numberLength = ((Number)len).doubleValue();
            if ((double)length != numberLength) {
               throw ECMAErrors.rangeError("inappropriate.array.length", JSType.toString(numberLength));
            }

            return new NativeArray(length);
         }
      default:
         return new NativeArray(args);
      }
   }

   public static NativeArray construct(boolean newObj, Object self) {
      return new NativeArray(0L);
   }

   public static Object construct(boolean newObj, Object self, boolean element) {
      return new NativeArray(new Object[]{element});
   }

   public static NativeArray construct(boolean newObj, Object self, int length) {
      return length >= 0 ? new NativeArray((long)length) : construct(newObj, self, length);
   }

   public static NativeArray construct(boolean newObj, Object self, long length) {
      return length >= 0L && length <= 4294967295L ? new NativeArray(length) : construct(newObj, self, length);
   }

   public static NativeArray construct(boolean newObj, Object self, double length) {
      long uint32length = JSType.toUint32(length);
      return (double)uint32length == length ? new NativeArray(uint32length) : construct(newObj, self, length);
   }

   public static NativeArray concat(Object self, int arg) {
      ContinuousArrayData newData = getContinuousArrayDataCCE(self, Integer.class).copy();
      newData.fastPush(arg);
      return new NativeArray(newData);
   }

   public static NativeArray concat(Object self, long arg) {
      ContinuousArrayData newData = getContinuousArrayDataCCE(self, Long.class).copy();
      newData.fastPush(arg);
      return new NativeArray(newData);
   }

   public static NativeArray concat(Object self, double arg) {
      ContinuousArrayData newData = getContinuousArrayDataCCE(self, Double.class).copy();
      newData.fastPush(arg);
      return new NativeArray(newData);
   }

   public static NativeArray concat(Object self, Object arg) {
      ContinuousArrayData selfData = getContinuousArrayDataCCE(self);
      ContinuousArrayData newData;
      if (arg instanceof NativeArray) {
         ContinuousArrayData argData = (ContinuousArrayData)((NativeArray)arg).getArray();
         if (argData.isEmpty()) {
            newData = selfData.copy();
         } else if (selfData.isEmpty()) {
            newData = argData.copy();
         } else {
            Class<?> widestElementType = selfData.widest(argData).getBoxedElementType();
            newData = ((ContinuousArrayData)selfData.convert(widestElementType)).fastConcat((ContinuousArrayData)argData.convert(widestElementType));
         }
      } else {
         newData = getContinuousArrayDataCCE(self, Object.class).copy();
         newData.fastPush(arg);
      }

      return new NativeArray(newData);
   }

   public static NativeArray concat(Object self, Object... args) {
      ArrayList<Object> list = new ArrayList();
      concatToList(list, Global.toObject(self));
      Object[] var3 = args;
      int var4 = args.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Object obj = var3[var5];
         concatToList(list, obj);
      }

      return new NativeArray(list.toArray());
   }

   private static void concatToList(ArrayList<Object> list, Object obj) {
      boolean isScriptArray = isArray(obj);
      boolean isScriptObject = isScriptArray || obj instanceof ScriptObject;
      if (isScriptArray || obj instanceof Iterable || obj != null && obj.getClass().isArray()) {
         Iterator<Object> iter = ArrayLikeIterator.arrayLikeIterator(obj, true);
         if (iter.hasNext()) {
            for(int i = 0; iter.hasNext(); ++i) {
               Object value = iter.next();
               boolean lacksIndex = obj != null && !((ScriptObject)obj).has(i);
               if (value == ScriptRuntime.UNDEFINED && isScriptObject && lacksIndex) {
                  list.add(ScriptRuntime.EMPTY);
               } else {
                  list.add(value);
               }
            }
         } else if (!isScriptArray) {
            list.add(obj);
         }
      } else {
         list.add(obj);
      }

   }

   public static String join(Object self, Object separator) {
      StringBuilder sb = new StringBuilder();
      Iterator<Object> iter = ArrayLikeIterator.arrayLikeIterator(self, true);
      String sep = separator == ScriptRuntime.UNDEFINED ? "," : JSType.toString(separator);

      while(iter.hasNext()) {
         Object obj = iter.next();
         if (obj != null && obj != ScriptRuntime.UNDEFINED) {
            sb.append(JSType.toString(obj));
         }

         if (iter.hasNext()) {
            sb.append(sep);
         }
      }

      return sb.toString();
   }

   public static int popInt(Object self) {
      return getContinuousNonEmptyArrayDataCCE(self, IntElements.class).fastPopInt();
   }

   public static double popDouble(Object self) {
      return getContinuousNonEmptyArrayDataCCE(self, NumericElements.class).fastPopDouble();
   }

   public static Object popObject(Object self) {
      return getContinuousArrayDataCCE(self, (Class)null).fastPopObject();
   }

   public static Object pop(Object self) {
      try {
         ScriptObject sobj = (ScriptObject)self;
         if (bulkable(sobj)) {
            return sobj.getArray().pop();
         } else {
            long len = JSType.toUint32(sobj.getLength());
            if (len == 0L) {
               sobj.set("length", 0, 2);
               return ScriptRuntime.UNDEFINED;
            } else {
               long index = len - 1L;
               Object element = sobj.get((double)index);
               sobj.delete((double)index, true);
               sobj.set("length", (double)index, 2);
               return element;
            }
         }
      } catch (NullPointerException | ClassCastException var7) {
         throw ECMAErrors.typeError("not.an.object", ScriptRuntime.safeToString(self));
      }
   }

   public static double push(Object self, int arg) {
      return getContinuousArrayDataCCE(self, Integer.class).fastPush(arg);
   }

   public static double push(Object self, long arg) {
      return getContinuousArrayDataCCE(self, Long.class).fastPush(arg);
   }

   public static double push(Object self, double arg) {
      return getContinuousArrayDataCCE(self, Double.class).fastPush(arg);
   }

   public static double pushObject(Object self, Object arg) {
      return getContinuousArrayDataCCE(self, Object.class).fastPush(arg);
   }

   public static Object push(Object self, Object... args) {
      try {
         ScriptObject sobj = (ScriptObject)self;
         if (bulkable(sobj) && sobj.getArray().length() + (long)args.length <= 4294967295L) {
            ArrayData newData = sobj.getArray().push(true, args);
            sobj.setArray(newData);
            return JSType.toNarrowestNumber(newData.length());
         } else {
            long len = JSType.toUint32(sobj.getLength());
            Object[] var5 = args;
            int var6 = args.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               Object element = var5[var7];
               sobj.set((double)(len++), element, 2);
            }

            sobj.set("length", (double)len, 2);
            return JSType.toNarrowestNumber(len);
         }
      } catch (NullPointerException | ClassCastException var9) {
         throw ECMAErrors.typeError(Context.getGlobal(), var9, "not.an.object", ScriptRuntime.safeToString(self));
      }
   }

   public static double push(Object self, Object arg) {
      try {
         ScriptObject sobj = (ScriptObject)self;
         ArrayData arrayData = sobj.getArray();
         long length = arrayData.length();
         if (bulkable(sobj) && length < 4294967295L) {
            sobj.setArray(arrayData.push(true, arg));
            return (double)(length + 1L);
         } else {
            long len = JSType.toUint32(sobj.getLength());
            sobj.set((double)(len++), arg, 2);
            sobj.set("length", (double)len, 2);
            return (double)len;
         }
      } catch (NullPointerException | ClassCastException var8) {
         throw ECMAErrors.typeError("not.an.object", ScriptRuntime.safeToString(self));
      }
   }

   public static Object reverse(Object self) {
      try {
         ScriptObject sobj = (ScriptObject)self;
         long len = JSType.toUint32(sobj.getLength());
         long middle = len / 2L;

         for(long lower = 0L; lower != middle; ++lower) {
            long upper = len - lower - 1L;
            Object lowerValue = sobj.get((double)lower);
            Object upperValue = sobj.get((double)upper);
            boolean lowerExists = sobj.has((double)lower);
            boolean upperExists = sobj.has((double)upper);
            if (lowerExists && upperExists) {
               sobj.set((double)lower, upperValue, 2);
               sobj.set((double)upper, lowerValue, 2);
            } else if (!lowerExists && upperExists) {
               sobj.set((double)lower, upperValue, 2);
               sobj.delete((double)upper, true);
            } else if (lowerExists && !upperExists) {
               sobj.delete((double)lower, true);
               sobj.set((double)upper, lowerValue, 2);
            }
         }

         return sobj;
      } catch (NullPointerException | ClassCastException var14) {
         throw ECMAErrors.typeError("not.an.object", ScriptRuntime.safeToString(self));
      }
   }

   public static Object shift(Object self) {
      Object obj = Global.toObject(self);
      Object first = ScriptRuntime.UNDEFINED;
      if (!(obj instanceof ScriptObject)) {
         return first;
      } else {
         ScriptObject sobj = (ScriptObject)obj;
         long len = JSType.toUint32(sobj.getLength());
         if (len > 0L) {
            first = sobj.get(0);
            if (bulkable(sobj)) {
               sobj.getArray().shiftLeft(1);
            } else {
               boolean hasPrevious = true;

               for(long k = 1L; k < len; ++k) {
                  boolean hasCurrent = sobj.has((double)k);
                  if (hasCurrent) {
                     sobj.set((double)(k - 1L), sobj.get((double)k), 2);
                  } else if (hasPrevious) {
                     sobj.delete((double)(k - 1L), true);
                  }

                  hasPrevious = hasCurrent;
               }
            }

            sobj.delete((double)(--len), true);
         } else {
            len = 0L;
         }

         sobj.set("length", (double)len, 2);
         return first;
      }
   }

   public static Object slice(Object self, Object start, Object end) {
      Object obj = Global.toObject(self);
      if (!(obj instanceof ScriptObject)) {
         return ScriptRuntime.UNDEFINED;
      } else {
         ScriptObject sobj = (ScriptObject)obj;
         long len = JSType.toUint32(sobj.getLength());
         long relativeStart = JSType.toLong(start);
         long relativeEnd = end == ScriptRuntime.UNDEFINED ? len : JSType.toLong(end);
         long k = relativeStart < 0L ? Math.max(len + relativeStart, 0L) : Math.min(relativeStart, len);
         long finale = relativeEnd < 0L ? Math.max(len + relativeEnd, 0L) : Math.min(relativeEnd, len);
         if (k >= finale) {
            return new NativeArray(0L);
         } else if (bulkable(sobj)) {
            return new NativeArray(sobj.getArray().slice(k, finale));
         } else {
            NativeArray copy = new NativeArray(finale - k);

            for(long n = 0L; k < finale; ++k) {
               if (sobj.has((double)k)) {
                  copy.defineOwnProperty(ArrayIndex.getArrayIndex(n), sobj.get((double)k));
               }

               ++n;
            }

            return copy;
         }
      }
   }

   private static Object compareFunction(Object comparefn) {
      if (comparefn == ScriptRuntime.UNDEFINED) {
         return null;
      } else if (!Bootstrap.isCallable(comparefn)) {
         throw ECMAErrors.typeError("not.a.function", ScriptRuntime.safeToString(comparefn));
      } else {
         return comparefn;
      }
   }

   private static Object[] sort(Object[] array, Object comparefn) {
      final Object cmp = compareFunction(comparefn);
      List<Object> list = Arrays.asList(array);
      final Object cmpThis = cmp != null && !Bootstrap.isStrictCallable(cmp) ? Global.instance() : ScriptRuntime.UNDEFINED;

      try {
         Collections.sort(list, new Comparator<Object>() {
            private final MethodHandle call_cmp = NativeArray.getCALL_CMP();

            public int compare(Object x, Object y) {
               if (x == ScriptRuntime.UNDEFINED && y == ScriptRuntime.UNDEFINED) {
                  return 0;
               } else if (x == ScriptRuntime.UNDEFINED) {
                  return 1;
               } else if (y == ScriptRuntime.UNDEFINED) {
                  return -1;
               } else if (cmp != null) {
                  try {
                     return (int)Math.signum(this.call_cmp.invokeExact(cmp, cmpThis, x, y));
                  } catch (Error | RuntimeException var4) {
                     throw var4;
                  } catch (Throwable var5) {
                     throw new RuntimeException(var5);
                  }
               } else {
                  return JSType.toString(x).compareTo(JSType.toString(y));
               }
            }
         });
      } catch (IllegalArgumentException var6) {
      }

      return list.toArray(new Object[array.length]);
   }

   public static ScriptObject sort(Object self, Object comparefn) {
      try {
         ScriptObject sobj = (ScriptObject)self;
         long len = JSType.toUint32(sobj.getLength());
         ArrayData array = sobj.getArray();
         if (len > 1L) {
            ArrayList<Object> src = new ArrayList();
            Iterator iter = array.indexIterator();

            while(iter.hasNext()) {
               long index = (Long)iter.next();
               if (index >= len) {
                  break;
               }

               src.add(array.getObject((int)index));
            }

            Object[] sorted = sort(src.toArray(), comparefn);

            for(int i = 0; i < sorted.length; ++i) {
               array = array.set(i, sorted[i], true);
            }

            if ((long)sorted.length != len) {
               array = array.delete((long)sorted.length, len - 1L);
            }

            sobj.setArray(array);
         }

         return sobj;
      } catch (NullPointerException | ClassCastException var10) {
         throw ECMAErrors.typeError("not.an.object", ScriptRuntime.safeToString(self));
      }
   }

   public static Object splice(Object self, Object... args) {
      Object obj = Global.toObject(self);
      if (!(obj instanceof ScriptObject)) {
         return ScriptRuntime.UNDEFINED;
      } else {
         Object start = args.length > 0 ? args[0] : ScriptRuntime.UNDEFINED;
         Object deleteCount = args.length > 1 ? args[1] : ScriptRuntime.UNDEFINED;
         Object[] items;
         if (args.length > 2) {
            items = new Object[args.length - 2];
            System.arraycopy(args, 2, items, 0, items.length);
         } else {
            items = ScriptRuntime.EMPTY_ARRAY;
         }

         ScriptObject sobj = (ScriptObject)obj;
         long len = JSType.toUint32(sobj.getLength());
         long relativeStart = JSType.toLong(start);
         long actualStart = relativeStart < 0L ? Math.max(len + relativeStart, 0L) : Math.min(relativeStart, len);
         long actualDeleteCount = Math.min(Math.max(JSType.toLong(deleteCount), 0L), len - actualStart);
         NativeArray returnValue;
         if (actualStart <= 2147483647L && actualDeleteCount <= 2147483647L && bulkable(sobj)) {
            try {
               returnValue = new NativeArray(sobj.getArray().fastSplice((int)actualStart, (int)actualDeleteCount, items.length));
               int k = (int)actualStart;

               for(int i = 0; i < items.length; ++k) {
                  sobj.defineOwnProperty(k, items[i]);
                  ++i;
               }
            } catch (UnsupportedOperationException var18) {
               returnValue = slowSplice(sobj, actualStart, actualDeleteCount, items, len);
            }
         } else {
            returnValue = slowSplice(sobj, actualStart, actualDeleteCount, items, len);
         }

         return returnValue;
      }
   }

   private static NativeArray slowSplice(ScriptObject sobj, long start, long deleteCount, Object[] items, long len) {
      NativeArray array = new NativeArray(deleteCount);

      long k;
      long from;
      for(k = 0L; k < deleteCount; ++k) {
         from = start + k;
         if (sobj.has((double)from)) {
            array.defineOwnProperty(ArrayIndex.getArrayIndex(k), sobj.get((double)from));
         }
      }

      long to;
      if ((long)items.length < deleteCount) {
         for(k = start; k < len - deleteCount; ++k) {
            from = k + deleteCount;
            to = k + (long)items.length;
            if (sobj.has((double)from)) {
               sobj.set((double)to, sobj.get((double)from), 2);
            } else {
               sobj.delete((double)to, true);
            }
         }

         for(k = len; k > len - deleteCount + (long)items.length; --k) {
            sobj.delete((double)(k - 1L), true);
         }
      } else if ((long)items.length > deleteCount) {
         for(k = len - deleteCount; k > start; --k) {
            from = k + deleteCount - 1L;
            to = k + (long)items.length - 1L;
            if (sobj.has((double)from)) {
               Object fromValue = sobj.get((double)from);
               sobj.set((double)to, fromValue, 2);
            } else {
               sobj.delete((double)to, true);
            }
         }
      }

      k = start;

      for(int i = 0; i < items.length; ++k) {
         sobj.set((double)k, items[i], 2);
         ++i;
      }

      from = len - deleteCount + (long)items.length;
      sobj.set("length", (double)from, 2);
      return array;
   }

   public static Object unshift(Object self, Object... items) {
      Object obj = Global.toObject(self);
      if (!(obj instanceof ScriptObject)) {
         return ScriptRuntime.UNDEFINED;
      } else {
         ScriptObject sobj = (ScriptObject)obj;
         long len = JSType.toUint32(sobj.getLength());
         if (items == null) {
            return ScriptRuntime.UNDEFINED;
         } else {
            int j;
            long k;
            if (bulkable(sobj)) {
               sobj.getArray().shiftRight(items.length);

               for(j = 0; j < items.length; ++j) {
                  sobj.setArray(sobj.getArray().set(j, items[j], true));
               }
            } else {
               for(k = len; k > 0L; --k) {
                  long from = k - 1L;
                  long to = k + (long)items.length - 1L;
                  if (sobj.has((double)from)) {
                     Object fromValue = sobj.get((double)from);
                     sobj.set((double)to, fromValue, 2);
                  } else {
                     sobj.delete((double)to, true);
                  }
               }

               for(j = 0; j < items.length; ++j) {
                  sobj.set(j, items[j], 2);
               }
            }

            k = len + (long)items.length;
            sobj.set("length", (double)k, 2);
            return JSType.toNarrowestNumber(k);
         }
      }
   }

   public static double indexOf(Object self, Object searchElement, Object fromIndex) {
      try {
         ScriptObject sobj = (ScriptObject)Global.toObject(self);
         long len = JSType.toUint32(sobj.getLength());
         if (len == 0L) {
            return -1.0D;
         }

         long n = JSType.toLong(fromIndex);
         if (n >= len) {
            return -1.0D;
         }

         for(long k = Math.max(0L, n < 0L ? len - Math.abs(n) : n); k < len; ++k) {
            if (sobj.has((double)k) && ScriptRuntime.EQ_STRICT(sobj.get((double)k), searchElement)) {
               return (double)k;
            }
         }
      } catch (NullPointerException | ClassCastException var10) {
      }

      return -1.0D;
   }

   public static double lastIndexOf(Object self, Object... args) {
      try {
         ScriptObject sobj = (ScriptObject)Global.toObject(self);
         long len = JSType.toUint32(sobj.getLength());
         if (len == 0L) {
            return -1.0D;
         } else {
            Object searchElement = args.length > 0 ? args[0] : ScriptRuntime.UNDEFINED;
            long n = args.length > 1 ? JSType.toLong(args[1]) : len - 1L;

            for(long k = n < 0L ? len - Math.abs(n) : Math.min(n, len - 1L); k >= 0L; --k) {
               if (sobj.has((double)k) && ScriptRuntime.EQ_STRICT(sobj.get((double)k), searchElement)) {
                  return (double)k;
               }
            }

            return -1.0D;
         }
      } catch (NullPointerException | ClassCastException var10) {
         throw ECMAErrors.typeError("not.an.object", ScriptRuntime.safeToString(self));
      }
   }

   public static boolean every(Object self, Object callbackfn, Object thisArg) {
      return applyEvery(Global.toObject(self), callbackfn, thisArg);
   }

   private static boolean applyEvery(Object self, Object callbackfn, Object thisArg) {
      return (Boolean)(new IteratorAction<Boolean>(Global.toObject(self), callbackfn, thisArg, true) {
         private final MethodHandle everyInvoker = NativeArray.getEVERY_CALLBACK_INVOKER();

         protected boolean forEach(Object val, double i) throws Throwable {
            return (Boolean)(this.result = this.everyInvoker.invokeExact(this.callbackfn, this.thisArg, val, i, this.self));
         }
      }).apply();
   }

   public static boolean some(Object self, Object callbackfn, Object thisArg) {
      return (Boolean)(new IteratorAction<Boolean>(Global.toObject(self), callbackfn, thisArg, false) {
         private final MethodHandle someInvoker = NativeArray.getSOME_CALLBACK_INVOKER();

         protected boolean forEach(Object val, double i) throws Throwable {
            return !this.result = this.someInvoker.invokeExact(this.callbackfn, this.thisArg, val, i, this.self);
         }
      }).apply();
   }

   public static Object forEach(Object self, Object callbackfn, Object thisArg) {
      return (new IteratorAction<Object>(Global.toObject(self), callbackfn, thisArg, ScriptRuntime.UNDEFINED) {
         private final MethodHandle forEachInvoker = NativeArray.getFOREACH_CALLBACK_INVOKER();

         protected boolean forEach(Object val, double i) throws Throwable {
            this.forEachInvoker.invokeExact(this.callbackfn, this.thisArg, val, i, this.self);
            return true;
         }
      }).apply();
   }

   public static NativeArray map(Object self, Object callbackfn, Object thisArg) {
      return (NativeArray)(new IteratorAction<NativeArray>(Global.toObject(self), callbackfn, thisArg, (NativeArray)null) {
         private final MethodHandle mapInvoker = NativeArray.getMAP_CALLBACK_INVOKER();

         protected boolean forEach(Object val, double i) throws Throwable {
            Object r = this.mapInvoker.invokeExact(this.callbackfn, this.thisArg, val, i, this.self);
            ((NativeArray)this.result).defineOwnProperty(ArrayIndex.getArrayIndex(this.index), r);
            return true;
         }

         public void applyLoopBegin(ArrayLikeIterator<Object> iter0) {
            this.result = new NativeArray(iter0.getLength());
         }
      }).apply();
   }

   public static NativeArray filter(Object self, Object callbackfn, Object thisArg) {
      return (NativeArray)(new IteratorAction<NativeArray>(Global.toObject(self), callbackfn, thisArg, new NativeArray()) {
         private long to = 0L;
         private final MethodHandle filterInvoker = NativeArray.getFILTER_CALLBACK_INVOKER();

         protected boolean forEach(Object val, double i) throws Throwable {
            if (this.filterInvoker.invokeExact(this.callbackfn, this.thisArg, val, i, this.self)) {
               ((NativeArray)this.result).defineOwnProperty(ArrayIndex.getArrayIndex((long)(this.to++)), val);
            }

            return true;
         }
      }).apply();
   }

   private static Object reduceInner(ArrayLikeIterator<Object> iter, Object self, Object... args) {
      Object callbackfn = args.length > 0 ? args[0] : ScriptRuntime.UNDEFINED;
      boolean initialValuePresent = args.length > 1;
      Object initialValue = initialValuePresent ? args[1] : ScriptRuntime.UNDEFINED;
      if (callbackfn == ScriptRuntime.UNDEFINED) {
         throw ECMAErrors.typeError("not.a.function", "undefined");
      } else {
         if (!initialValuePresent) {
            if (!iter.hasNext()) {
               throw ECMAErrors.typeError("array.reduce.invalid.init");
            }

            initialValue = iter.next();
         }

         return (new IteratorAction<Object>(Global.toObject(self), callbackfn, ScriptRuntime.UNDEFINED, initialValue, iter) {
            private final MethodHandle reduceInvoker = NativeArray.getREDUCE_CALLBACK_INVOKER();

            protected boolean forEach(Object val, double i) throws Throwable {
               this.result = this.reduceInvoker.invokeExact(this.callbackfn, ScriptRuntime.UNDEFINED, this.result, val, i, this.self);
               return true;
            }
         }).apply();
      }
   }

   public static Object reduce(Object self, Object... args) {
      return reduceInner(ArrayLikeIterator.arrayLikeIterator(self), self, args);
   }

   public static Object reduceRight(Object self, Object... args) {
      return reduceInner(ArrayLikeIterator.reverseArrayLikeIterator(self), self, args);
   }

   private static boolean bulkable(ScriptObject self) {
      return self.isArray() && !hasInheritedArrayEntries(self) && !self.isLengthNotWritable();
   }

   private static boolean hasInheritedArrayEntries(ScriptObject self) {
      for(ScriptObject proto = self.getProto(); proto != null; proto = proto.getProto()) {
         if (proto.hasArrayEntries()) {
            return true;
         }
      }

      return false;
   }

   public String toString() {
      return "NativeArray@" + Debug.id(this) + " [" + this.getArray().getClass().getSimpleName() + ']';
   }

   public SpecializedFunction.LinkLogic getLinkLogic(Class<? extends SpecializedFunction.LinkLogic> clazz) {
      if (clazz == NativeArray.PushLinkLogic.class) {
         return NativeArray.PushLinkLogic.INSTANCE;
      } else if (clazz == NativeArray.PopLinkLogic.class) {
         return NativeArray.PopLinkLogic.INSTANCE;
      } else {
         return clazz == NativeArray.ConcatLinkLogic.class ? NativeArray.ConcatLinkLogic.INSTANCE : null;
      }
   }

   public boolean hasPerInstanceAssumptions() {
      return true;
   }

   private static final <T> ContinuousArrayData getContinuousNonEmptyArrayDataCCE(Object self, Class<T> clazz) {
      try {
         ContinuousArrayData data = (ContinuousArrayData)((NativeArray)self).getArray();
         if (data.length() != 0L) {
            return data;
         }
      } catch (NullPointerException var3) {
      }

      throw new ClassCastException();
   }

   private static final ContinuousArrayData getContinuousArrayDataCCE(Object self) {
      try {
         return (ContinuousArrayData)((NativeArray)self).getArray();
      } catch (NullPointerException var2) {
         throw new ClassCastException();
      }
   }

   private static final ContinuousArrayData getContinuousArrayDataCCE(Object self, Class<?> elementType) {
      try {
         return (ContinuousArrayData)((NativeArray)self).getArray(elementType);
      } catch (NullPointerException var3) {
         throw new ClassCastException();
      }
   }

   static {
      $clinit$();
   }

   public static void $clinit$() {
      ArrayList var10000 = new ArrayList(1);
      var10000.add(AccessorProperty.create("length", 6, "length", "length"));
      $nasgenmap$ = PropertyMap.newMap((Collection)var10000);
   }

   private static final class PopLinkLogic extends NativeArray.ArrayLinkLogic {
      private static final SpecializedFunction.LinkLogic INSTANCE = new NativeArray.PopLinkLogic();

      public boolean canLink(Object self, CallSiteDescriptor desc, LinkRequest request) {
         ContinuousArrayData data = getContinuousNonEmptyArrayData(self);
         if (data != null) {
            Class<?> elementType = data.getElementType();
            Class<?> returnType = desc.getMethodType().returnType();
            boolean typeFits = JSType.getAccessorTypeIndex(returnType) >= JSType.getAccessorTypeIndex(elementType);
            return typeFits;
         } else {
            return false;
         }
      }

      private static ContinuousArrayData getContinuousNonEmptyArrayData(Object self) {
         ContinuousArrayData data = getContinuousArrayData(self);
         if (data != null) {
            return data.length() == 0L ? null : data;
         } else {
            return null;
         }
      }
   }

   private static final class PushLinkLogic extends NativeArray.ArrayLinkLogic {
      private static final SpecializedFunction.LinkLogic INSTANCE = new NativeArray.PushLinkLogic();

      public boolean canLink(Object self, CallSiteDescriptor desc, LinkRequest request) {
         return getContinuousArrayData(self) != null;
      }
   }

   private static final class ConcatLinkLogic extends NativeArray.ArrayLinkLogic {
      private static final SpecializedFunction.LinkLogic INSTANCE = new NativeArray.ConcatLinkLogic();

      public boolean canLink(Object self, CallSiteDescriptor desc, LinkRequest request) {
         Object[] args = request.getArguments();
         if (args.length != 3) {
            return false;
         } else {
            ContinuousArrayData selfData = getContinuousArrayData(self);
            if (selfData == null) {
               return false;
            } else {
               Object arg = args[2];
               if (arg instanceof NativeArray) {
                  ContinuousArrayData argData = getContinuousArrayData(arg);
                  if (argData == null) {
                     return false;
                  }
               }

               return true;
            }
         }
      }
   }

   private abstract static class ArrayLinkLogic extends SpecializedFunction.LinkLogic {
      protected ArrayLinkLogic() {
      }

      protected static ContinuousArrayData getContinuousArrayData(Object self) {
         try {
            return (ContinuousArrayData)((NativeArray)self).getArray();
         } catch (Exception var2) {
            return null;
         }
      }

      public Class<? extends Throwable> getRelinkException() {
         return ClassCastException.class;
      }
   }
}
