package jdk.nashorn.internal.runtime;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import jdk.internal.dynalink.beans.StaticClass;
import jdk.nashorn.api.scripting.AbstractJSObject;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.parser.Lexer;
import jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator;
import jdk.nashorn.internal.runtime.linker.Bootstrap;

public enum JSType {
   UNDEFINED("undefined"),
   NULL("object"),
   BOOLEAN("boolean"),
   NUMBER("number"),
   STRING("string"),
   OBJECT("object"),
   FUNCTION("function");

   private final String typeName;
   public static final long MAX_UINT = 4294967295L;
   private static final Lookup JSTYPE_LOOKUP = MethodHandles.lookup();
   public static final CompilerConstants.Call TO_BOOLEAN = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toBoolean", Boolean.TYPE, Object.class);
   public static final CompilerConstants.Call TO_BOOLEAN_D = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toBoolean", Boolean.TYPE, Double.TYPE);
   public static final CompilerConstants.Call TO_INTEGER = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toInteger", Integer.TYPE, Object.class);
   public static final CompilerConstants.Call TO_LONG = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toLong", Long.TYPE, Object.class);
   public static final CompilerConstants.Call TO_LONG_D = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toLong", Long.TYPE, Double.TYPE);
   public static final CompilerConstants.Call TO_NUMBER = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toNumber", Double.TYPE, Object.class);
   public static final CompilerConstants.Call TO_NUMBER_OPTIMISTIC = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toNumberOptimistic", Double.TYPE, Object.class, Integer.TYPE);
   public static final CompilerConstants.Call TO_STRING = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toString", String.class, Object.class);
   public static final CompilerConstants.Call TO_INT32 = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toInt32", Integer.TYPE, Object.class);
   public static final CompilerConstants.Call TO_INT32_L = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toInt32", Integer.TYPE, Long.TYPE);
   public static final CompilerConstants.Call TO_INT32_OPTIMISTIC = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toInt32Optimistic", Integer.TYPE, Object.class, Integer.TYPE);
   public static final CompilerConstants.Call TO_INT32_D = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toInt32", Integer.TYPE, Double.TYPE);
   public static final CompilerConstants.Call TO_UINT32_OPTIMISTIC = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toUint32Optimistic", Integer.TYPE, Integer.TYPE, Integer.TYPE);
   public static final CompilerConstants.Call TO_UINT32_DOUBLE = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toUint32Double", Double.TYPE, Integer.TYPE);
   public static final CompilerConstants.Call TO_UINT32 = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toUint32", Long.TYPE, Object.class);
   public static final CompilerConstants.Call TO_UINT32_D = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toUint32", Long.TYPE, Double.TYPE);
   public static final CompilerConstants.Call TO_STRING_D = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toString", String.class, Double.TYPE);
   public static final CompilerConstants.Call TO_PRIMITIVE_TO_STRING = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toPrimitiveToString", String.class, Object.class);
   public static final CompilerConstants.Call TO_PRIMITIVE_TO_CHARSEQUENCE = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toPrimitiveToCharSequence", CharSequence.class, Object.class);
   public static final CompilerConstants.Call THROW_UNWARRANTED = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "throwUnwarrantedOptimismException", Object.class, Object.class, Integer.TYPE);
   public static final CompilerConstants.Call ADD_EXACT = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "addExact", Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE);
   public static final CompilerConstants.Call SUB_EXACT = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "subExact", Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE);
   public static final CompilerConstants.Call MUL_EXACT = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "mulExact", Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE);
   public static final CompilerConstants.Call DIV_EXACT = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "divExact", Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE);
   public static final CompilerConstants.Call DIV_ZERO = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "divZero", Integer.TYPE, Integer.TYPE, Integer.TYPE);
   public static final CompilerConstants.Call REM_ZERO = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "remZero", Integer.TYPE, Integer.TYPE, Integer.TYPE);
   public static final CompilerConstants.Call REM_EXACT = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "remExact", Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE);
   public static final CompilerConstants.Call DECREMENT_EXACT = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "decrementExact", Integer.TYPE, Integer.TYPE, Integer.TYPE);
   public static final CompilerConstants.Call INCREMENT_EXACT = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "incrementExact", Integer.TYPE, Integer.TYPE, Integer.TYPE);
   public static final CompilerConstants.Call NEGATE_EXACT = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "negateExact", Integer.TYPE, Integer.TYPE, Integer.TYPE);
   public static final CompilerConstants.Call TO_JAVA_ARRAY = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "toJavaArray", Object.class, Object.class, Class.class);
   public static final CompilerConstants.Call VOID_RETURN = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "voidReturn", Void.TYPE);
   public static final CompilerConstants.Call IS_STRING = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "isString", Boolean.TYPE, Object.class);
   public static final CompilerConstants.Call IS_NUMBER = CompilerConstants.staticCall(JSTYPE_LOOKUP, JSType.class, "isNumber", Boolean.TYPE, Object.class);
   private static final List<Type> ACCESSOR_TYPES = Collections.unmodifiableList(Arrays.asList(Type.INT, Type.NUMBER, Type.OBJECT));
   public static final int TYPE_UNDEFINED_INDEX = -1;
   public static final int TYPE_INT_INDEX = 0;
   public static final int TYPE_DOUBLE_INDEX = 1;
   public static final int TYPE_OBJECT_INDEX = 2;
   public static final List<MethodHandle> CONVERT_OBJECT = toUnmodifiableList(TO_INT32.methodHandle(), TO_NUMBER.methodHandle(), null);
   public static final List<MethodHandle> CONVERT_OBJECT_OPTIMISTIC = toUnmodifiableList(TO_INT32_OPTIMISTIC.methodHandle(), TO_NUMBER_OPTIMISTIC.methodHandle(), null);
   public static final int UNDEFINED_INT = 0;
   public static final long UNDEFINED_LONG = 0L;
   public static final double UNDEFINED_DOUBLE = Double.NaN;
   private static final long MAX_PRECISE_DOUBLE = 9007199254740992L;
   private static final long MIN_PRECISE_DOUBLE = -9007199254740992L;
   public static final List<MethodHandle> GET_UNDEFINED = toUnmodifiableList(jdk.nashorn.internal.lookup.Lookup.MH.constant(Integer.TYPE, 0), jdk.nashorn.internal.lookup.Lookup.MH.constant(Double.TYPE, Double.NaN), jdk.nashorn.internal.lookup.Lookup.MH.constant(Object.class, Undefined.getUndefined()));
   private static final double INT32_LIMIT = 4.294967296E9D;

   private JSType(String typeName) {
      this.typeName = typeName;
   }

   public final String typeName() {
      return this.typeName;
   }

   public static JSType of(Object obj) {
      if (obj == null) {
         return NULL;
      } else if (obj instanceof ScriptObject) {
         return obj instanceof ScriptFunction ? FUNCTION : OBJECT;
      } else if (obj instanceof Boolean) {
         return BOOLEAN;
      } else if (isString(obj)) {
         return STRING;
      } else if (isNumber(obj)) {
         return NUMBER;
      } else if (obj == ScriptRuntime.UNDEFINED) {
         return UNDEFINED;
      } else {
         return Bootstrap.isCallable(obj) ? FUNCTION : OBJECT;
      }
   }

   public static JSType ofNoFunction(Object obj) {
      if (obj == null) {
         return NULL;
      } else if (obj instanceof ScriptObject) {
         return OBJECT;
      } else if (obj instanceof Boolean) {
         return BOOLEAN;
      } else if (isString(obj)) {
         return STRING;
      } else if (isNumber(obj)) {
         return NUMBER;
      } else {
         return obj == ScriptRuntime.UNDEFINED ? UNDEFINED : OBJECT;
      }
   }

   public static void voidReturn() {
   }

   public static boolean isRepresentableAsInt(long number) {
      return (long)((int)number) == number;
   }

   public static boolean isRepresentableAsInt(double number) {
      return (double)((int)number) == number;
   }

   public static boolean isStrictlyRepresentableAsInt(double number) {
      return isRepresentableAsInt(number) && isNotNegativeZero(number);
   }

   public static boolean isRepresentableAsInt(Object obj) {
      return obj instanceof Number ? isRepresentableAsInt(((Number)obj).doubleValue()) : false;
   }

   public static boolean isRepresentableAsLong(double number) {
      return (double)((long)number) == number;
   }

   public static boolean isRepresentableAsDouble(long number) {
      return 9007199254740992L >= number && number >= -9007199254740992L;
   }

   private static boolean isNotNegativeZero(double number) {
      return Double.doubleToRawLongBits(number) != Long.MIN_VALUE;
   }

   public static boolean isPrimitive(Object obj) {
      return obj == null || obj == ScriptRuntime.UNDEFINED || isString(obj) || isNumber(obj) || obj instanceof Boolean;
   }

   public static Object toPrimitive(Object obj) {
      return toPrimitive((Object)obj, (Class)null);
   }

   public static Object toPrimitive(Object obj, Class<?> hint) {
      if (obj instanceof ScriptObject) {
         return toPrimitive((ScriptObject)obj, hint);
      } else if (isPrimitive(obj)) {
         return obj;
      } else if (hint == Number.class && obj instanceof Number) {
         return ((Number)obj).doubleValue();
      } else if (obj instanceof JSObject) {
         return toPrimitive((JSObject)obj, hint);
      } else if (obj instanceof StaticClass) {
         String name = ((StaticClass)obj).getRepresentedClass().getName();
         return (new StringBuilder(12 + name.length())).append("[JavaClass ").append(name).append(']').toString();
      } else {
         return obj.toString();
      }
   }

   private static Object toPrimitive(ScriptObject sobj, Class<?> hint) {
      return requirePrimitive(sobj.getDefaultValue(hint));
   }

   private static Object requirePrimitive(Object result) {
      if (!isPrimitive(result)) {
         throw ECMAErrors.typeError("bad.default.value", result.toString());
      } else {
         return result;
      }
   }

   public static Object toPrimitive(JSObject jsobj, Class<?> hint) {
      try {
         return requirePrimitive(AbstractJSObject.getDefaultValue(jsobj, hint));
      } catch (UnsupportedOperationException var3) {
         throw new ECMAException(Context.getGlobal().newTypeError(var3.getMessage()), var3);
      }
   }

   public static String toPrimitiveToString(Object obj) {
      return toString(toPrimitive(obj));
   }

   public static CharSequence toPrimitiveToCharSequence(Object obj) {
      return toCharSequence(toPrimitive(obj));
   }

   public static boolean toBoolean(double num) {
      return num != 0.0D && !Double.isNaN(num);
   }

   public static boolean toBoolean(Object obj) {
      if (obj instanceof Boolean) {
         return (Boolean)obj;
      } else if (nullOrUndefined(obj)) {
         return false;
      } else if (!(obj instanceof Number)) {
         if (isString(obj)) {
            return ((CharSequence)obj).length() > 0;
         } else {
            return true;
         }
      } else {
         double num = ((Number)obj).doubleValue();
         return num != 0.0D && !Double.isNaN(num);
      }
   }

   public static String toString(Object obj) {
      return toStringImpl(obj, false);
   }

   public static CharSequence toCharSequence(Object obj) {
      return (CharSequence)(obj instanceof ConsString ? (CharSequence)obj : toString(obj));
   }

   public static boolean isString(Object obj) {
      return obj instanceof String || obj instanceof ConsString;
   }

   public static boolean isNumber(Object obj) {
      if (obj == null) {
         return false;
      } else {
         Class<?> c = obj.getClass();
         return c == Integer.class || c == Double.class || c == Float.class || c == Short.class || c == Byte.class;
      }
   }

   public static String toString(int num) {
      return Integer.toString(num);
   }

   public static String toString(double num) {
      if (isRepresentableAsInt(num)) {
         return Integer.toString((int)num);
      } else if (num == Double.POSITIVE_INFINITY) {
         return "Infinity";
      } else if (num == Double.NEGATIVE_INFINITY) {
         return "-Infinity";
      } else {
         return Double.isNaN(num) ? "NaN" : NumberToString.stringFor(num);
      }
   }

   public static String toString(double num, int radix) {
      assert radix >= 2 && radix <= 36 : "invalid radix";

      if (isRepresentableAsInt(num)) {
         return Integer.toString((int)num, radix);
      } else if (num == Double.POSITIVE_INFINITY) {
         return "Infinity";
      } else if (num == Double.NEGATIVE_INFINITY) {
         return "-Infinity";
      } else if (Double.isNaN(num)) {
         return "NaN";
      } else if (num == 0.0D) {
         return "0";
      } else {
         String chars = "0123456789abcdefghijklmnopqrstuvwxyz";
         StringBuilder sb = new StringBuilder();
         boolean negative = num < 0.0D;
         double signedNum = negative ? -num : num;
         double intPart = Math.floor(signedNum);
         double decPart = signedNum - intPart;

         do {
            double remainder = intPart % (double)radix;
            sb.append("0123456789abcdefghijklmnopqrstuvwxyz".charAt((int)remainder));
            intPart -= remainder;
            intPart /= (double)radix;
         } while(intPart >= 1.0D);

         if (negative) {
            sb.append('-');
         }

         sb.reverse();
         if (decPart > 0.0D) {
            int dot = sb.length();
            sb.append('.');

            do {
               decPart *= (double)radix;
               double d = Math.floor(decPart);
               sb.append("0123456789abcdefghijklmnopqrstuvwxyz".charAt((int)d));
               decPart -= d;
            } while(decPart > 0.0D && sb.length() - dot < 1100);
         }

         return sb.toString();
      }
   }

   public static double toNumber(Object obj) {
      if (obj instanceof Double) {
         return (Double)obj;
      } else {
         return obj instanceof Number ? ((Number)obj).doubleValue() : toNumberGeneric(obj);
      }
   }

   public static double toNumberForEq(Object obj) {
      return obj == null ? Double.NaN : toNumber(obj);
   }

   public static double toNumberForStrictEq(Object obj) {
      if (obj instanceof Double) {
         return (Double)obj;
      } else {
         return isNumber(obj) ? ((Number)obj).doubleValue() : Double.NaN;
      }
   }

   public static Number toNarrowestNumber(long l) {
      return isRepresentableAsInt(l) ? (double)Integer.valueOf((int)l) : Double.valueOf((double)l);
   }

   public static double toNumber(Boolean b) {
      return b ? 1.0D : 0.0D;
   }

   public static double toNumber(ScriptObject obj) {
      return toNumber(toPrimitive(obj, Number.class));
   }

   public static double toNumberOptimistic(Object obj, int programPoint) {
      if (obj != null) {
         Class<?> clz = obj.getClass();
         if (clz == Double.class || clz == Integer.class || clz == Long.class) {
            return ((Number)obj).doubleValue();
         }
      }

      throw new UnwarrantedOptimismException(obj, programPoint);
   }

   public static double toNumberMaybeOptimistic(Object obj, int programPoint) {
      return UnwarrantedOptimismException.isValid(programPoint) ? toNumberOptimistic(obj, programPoint) : toNumber(obj);
   }

   public static int digit(char ch, int radix) {
      return digit(ch, radix, false);
   }

   public static int digit(char ch, int radix, boolean onlyIsoLatin1) {
      char maxInRadix = (char)(97 + (radix - 1) - 10);
      char c = Character.toLowerCase(ch);
      if (c >= 'a' && c <= maxInRadix) {
         return Character.digit(ch, radix);
      } else {
         return !Character.isDigit(ch) || onlyIsoLatin1 && (ch < '0' || ch > '9') ? -1 : Character.digit(ch, radix);
      }
   }

   public static double toNumber(String str) {
      int end = str.length();
      if (end == 0) {
         return 0.0D;
      } else {
         int start = 0;

         char f;
         for(f = str.charAt(0); Lexer.isJSWhitespace(f); f = str.charAt(start)) {
            ++start;
            if (start == end) {
               return 0.0D;
            }
         }

         while(Lexer.isJSWhitespace(str.charAt(end - 1))) {
            --end;
         }

         boolean negative;
         if (f == '-') {
            ++start;
            if (start == end) {
               return Double.NaN;
            }

            f = str.charAt(start);
            negative = true;
         } else {
            if (f == '+') {
               ++start;
               if (start == end) {
                  return Double.NaN;
               }

               f = str.charAt(start);
            }

            negative = false;
         }

         double value;
         if (start + 1 < end && f == '0' && Character.toLowerCase(str.charAt(start + 1)) == 'x') {
            value = parseRadix(str.toCharArray(), start + 2, end, 16);
         } else {
            if (f == 'I' && end - start == 8 && str.regionMatches(start, "Infinity", 0, 8)) {
               return negative ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
            }

            for(int i = start; i < end; ++i) {
               f = str.charAt(i);
               if ((f < '0' || f > '9') && f != '.' && f != 'e' && f != 'E' && f != '+' && f != '-') {
                  return Double.NaN;
               }
            }

            try {
               value = Double.parseDouble(str.substring(start, end));
            } catch (NumberFormatException var8) {
               return Double.NaN;
            }
         }

         return negative ? -value : value;
      }
   }

   public static int toInteger(Object obj) {
      return (int)toNumber(obj);
   }

   public static long toLong(Object obj) {
      return obj instanceof Long ? (Long)obj : toLong(toNumber(obj));
   }

   public static long toLong(double num) {
      return (long)num;
   }

   public static int toInt32(Object obj) {
      return toInt32(toNumber(obj));
   }

   public static int toInt32Optimistic(Object obj, int programPoint) {
      if (obj != null && obj.getClass() == Integer.class) {
         return (Integer)obj;
      } else {
         throw new UnwarrantedOptimismException(obj, programPoint);
      }
   }

   public static int toInt32MaybeOptimistic(Object obj, int programPoint) {
      return UnwarrantedOptimismException.isValid(programPoint) ? toInt32Optimistic(obj, programPoint) : toInt32(obj);
   }

   public static int toInt32(long num) {
      return (int)(num >= -9007199254740992L && num <= 9007199254740992L ? num : (long)((double)num % 4.294967296E9D));
   }

   public static int toInt32(double num) {
      return (int)doubleToInt32(num);
   }

   public static long toUint32(Object obj) {
      return toUint32(toNumber(obj));
   }

   public static long toUint32(double num) {
      return doubleToInt32(num) & 4294967295L;
   }

   public static long toUint32(int num) {
      return (long)num & 4294967295L;
   }

   public static int toUint32Optimistic(int num, int pp) {
      if (num >= 0) {
         return num;
      } else {
         throw new UnwarrantedOptimismException(toUint32Double(num), pp, Type.NUMBER);
      }
   }

   public static double toUint32Double(int num) {
      return (double)toUint32(num);
   }

   public static int toUint16(Object obj) {
      return toUint16(toNumber(obj));
   }

   public static int toUint16(int num) {
      return num & '\uffff';
   }

   public static int toUint16(long num) {
      return (int)num & '\uffff';
   }

   public static int toUint16(double num) {
      return (int)doubleToInt32(num) & '\uffff';
   }

   private static long doubleToInt32(double num) {
      int exponent = Math.getExponent(num);
      if (exponent < 31) {
         return (long)num;
      } else if (exponent >= 84) {
         return 0L;
      } else {
         double d = num >= 0.0D ? Math.floor(num) : Math.ceil(num);
         return (long)(d % 4.294967296E9D);
      }
   }

   public static boolean isFinite(double num) {
      return !Double.isInfinite(num) && !Double.isNaN(num);
   }

   public static Double toDouble(double num) {
      return num;
   }

   public static Double toDouble(long num) {
      return (double)num;
   }

   public static Double toDouble(int num) {
      return (double)num;
   }

   public static Object toObject(boolean bool) {
      return bool;
   }

   public static Object toObject(int num) {
      return num;
   }

   public static Object toObject(long num) {
      return num;
   }

   public static Object toObject(double num) {
      return num;
   }

   public static Object toObject(Object obj) {
      return obj;
   }

   public static Object toScriptObject(Object obj) {
      return toScriptObject(Context.getGlobal(), obj);
   }

   public static Object toScriptObject(Global global, Object obj) {
      if (nullOrUndefined(obj)) {
         throw ECMAErrors.typeError(global, "not.an.object", ScriptRuntime.safeToString(obj));
      } else {
         return obj instanceof ScriptObject ? obj : global.wrapAsObject(obj);
      }
   }

   public static Object toJavaArray(Object obj, Class<?> componentType) {
      if (obj instanceof ScriptObject) {
         return ((ScriptObject)obj).getArray().asArrayOfType(componentType);
      } else if (!(obj instanceof JSObject)) {
         if (obj == null) {
            return null;
         } else {
            throw new IllegalArgumentException("not a script object");
         }
      } else {
         ArrayLikeIterator<?> itr = ArrayLikeIterator.arrayLikeIterator(obj);
         int len = (int)itr.getLength();
         Object[] res = new Object[len];

         for(int var5 = 0; itr.hasNext(); res[var5++] = itr.next()) {
         }

         return convertArray(res, componentType);
      }
   }

   public static Object convertArray(Object[] src, Class<?> componentType) {
      int i;
      Object dst;
      if (componentType == Object.class) {
         for(i = 0; i < src.length; ++i) {
            dst = src[i];
            if (dst instanceof ConsString) {
               src[i] = dst.toString();
            }
         }
      }

      i = src.length;
      dst = Array.newInstance(componentType, i);
      MethodHandle converter = Bootstrap.getLinkerServices().getTypeConverter(Object.class, componentType);

      try {
         for(int i = 0; i < src.length; ++i) {
            Array.set(dst, i, invoke(converter, src[i]));
         }

         return dst;
      } catch (Error | RuntimeException var6) {
         throw var6;
      } catch (Throwable var7) {
         throw new RuntimeException(var7);
      }
   }

   public static boolean nullOrUndefined(Object obj) {
      return obj == null || obj == ScriptRuntime.UNDEFINED;
   }

   static String toStringImpl(Object obj, boolean safe) {
      if (obj instanceof String) {
         return (String)obj;
      } else if (obj instanceof ConsString) {
         return obj.toString();
      } else if (isNumber(obj)) {
         return toString(((Number)obj).doubleValue());
      } else if (obj == ScriptRuntime.UNDEFINED) {
         return "undefined";
      } else if (obj == null) {
         return "null";
      } else if (obj instanceof Boolean) {
         return obj.toString();
      } else if (safe && obj instanceof ScriptObject) {
         ScriptObject sobj = (ScriptObject)obj;
         Global gobj = Context.getGlobal();
         return gobj.isError(sobj) ? ECMAException.safeToString(sobj) : sobj.safeToString();
      } else {
         return toString(toPrimitive(obj, String.class));
      }
   }

   static String trimLeft(String str) {
      int start;
      for(start = 0; start < str.length() && Lexer.isJSWhitespace(str.charAt(start)); ++start) {
      }

      return str.substring(start);
   }

   private static Object throwUnwarrantedOptimismException(Object value, int programPoint) {
      throw new UnwarrantedOptimismException(value, programPoint);
   }

   public static int addExact(int x, int y, int programPoint) throws UnwarrantedOptimismException {
      try {
         return Math.addExact(x, y);
      } catch (ArithmeticException var4) {
         throw new UnwarrantedOptimismException((double)x + (double)y, programPoint);
      }
   }

   public static int subExact(int x, int y, int programPoint) throws UnwarrantedOptimismException {
      try {
         return Math.subtractExact(x, y);
      } catch (ArithmeticException var4) {
         throw new UnwarrantedOptimismException((double)x - (double)y, programPoint);
      }
   }

   public static int mulExact(int x, int y, int programPoint) throws UnwarrantedOptimismException {
      try {
         return Math.multiplyExact(x, y);
      } catch (ArithmeticException var4) {
         throw new UnwarrantedOptimismException((double)x * (double)y, programPoint);
      }
   }

   public static int divExact(int x, int y, int programPoint) throws UnwarrantedOptimismException {
      int res;
      try {
         res = x / y;
      } catch (ArithmeticException var5) {
         assert y == 0;

         throw new UnwarrantedOptimismException(x > 0 ? Double.POSITIVE_INFINITY : (x < 0 ? Double.NEGATIVE_INFINITY : Double.NaN), programPoint);
      }

      int rem = x % y;
      if (rem == 0) {
         return res;
      } else {
         throw new UnwarrantedOptimismException((double)x / (double)y, programPoint);
      }
   }

   public static int divZero(int x, int y) {
      return y == 0 ? 0 : x / y;
   }

   public static int remZero(int x, int y) {
      return y == 0 ? 0 : x % y;
   }

   public static int remExact(int x, int y, int programPoint) throws UnwarrantedOptimismException {
      try {
         return x % y;
      } catch (ArithmeticException var4) {
         assert y == 0;

         throw new UnwarrantedOptimismException(Double.NaN, programPoint);
      }
   }

   public static int decrementExact(int x, int programPoint) throws UnwarrantedOptimismException {
      try {
         return Math.decrementExact(x);
      } catch (ArithmeticException var3) {
         throw new UnwarrantedOptimismException((double)x - 1.0D, programPoint);
      }
   }

   public static int incrementExact(int x, int programPoint) throws UnwarrantedOptimismException {
      try {
         return Math.incrementExact(x);
      } catch (ArithmeticException var3) {
         throw new UnwarrantedOptimismException((double)x + 1.0D, programPoint);
      }
   }

   public static int negateExact(int x, int programPoint) throws UnwarrantedOptimismException {
      try {
         if (x == 0) {
            throw new UnwarrantedOptimismException(-0.0D, programPoint);
         } else {
            return Math.negateExact(x);
         }
      } catch (ArithmeticException var3) {
         throw new UnwarrantedOptimismException(-((double)x), programPoint);
      }
   }

   public static int getAccessorTypeIndex(Type type) {
      return getAccessorTypeIndex(type.getTypeClass());
   }

   public static int getAccessorTypeIndex(Class<?> type) {
      if (type == null) {
         return -1;
      } else if (type == Integer.TYPE) {
         return 0;
      } else if (type == Double.TYPE) {
         return 1;
      } else {
         return !type.isPrimitive() ? 2 : -1;
      }
   }

   public static Type getAccessorType(int index) {
      return (Type)ACCESSOR_TYPES.get(index);
   }

   public static int getNumberOfAccessorTypes() {
      return ACCESSOR_TYPES.size();
   }

   private static double parseRadix(char[] chars, int start, int length, int radix) {
      int pos = 0;

      for(int i = start; i < length; ++i) {
         if (digit(chars[i], radix) == -1) {
            return Double.NaN;
         }

         ++pos;
      }

      if (pos == 0) {
         return Double.NaN;
      } else {
         double value = 0.0D;

         for(int i = start; i < start + pos; ++i) {
            value *= (double)radix;
            value += (double)digit(chars[i], radix);
         }

         return value;
      }
   }

   private static double toNumberGeneric(Object obj) {
      if (obj == null) {
         return 0.0D;
      } else if (obj instanceof String) {
         return toNumber((String)obj);
      } else if (obj instanceof ConsString) {
         return toNumber(obj.toString());
      } else if (obj instanceof Boolean) {
         return toNumber((Boolean)obj);
      } else if (obj instanceof ScriptObject) {
         return toNumber((ScriptObject)obj);
      } else {
         return obj instanceof Undefined ? Double.NaN : toNumber(toPrimitive(obj, Number.class));
      }
   }

   private static Object invoke(MethodHandle mh, Object arg) {
      try {
         return mh.invoke(arg);
      } catch (Error | RuntimeException var3) {
         throw var3;
      } catch (Throwable var4) {
         throw new RuntimeException(var4);
      }
   }

   public static MethodHandle unboxConstant(Object o) {
      if (o != null) {
         if (o.getClass() == Integer.class) {
            return jdk.nashorn.internal.lookup.Lookup.MH.constant(Integer.TYPE, (Integer)o);
         }

         if (o.getClass() == Double.class) {
            return jdk.nashorn.internal.lookup.Lookup.MH.constant(Double.TYPE, (Double)o);
         }
      }

      return jdk.nashorn.internal.lookup.Lookup.MH.constant(Object.class, o);
   }

   public static Class<?> unboxedFieldType(Object o) {
      if (o == null) {
         return Object.class;
      } else if (o.getClass() == Integer.class) {
         return Integer.TYPE;
      } else {
         return o.getClass() == Double.class ? Double.TYPE : Object.class;
      }
   }

   private static final List<MethodHandle> toUnmodifiableList(MethodHandle... methodHandles) {
      return Collections.unmodifiableList(Arrays.asList(methodHandles));
   }
}
