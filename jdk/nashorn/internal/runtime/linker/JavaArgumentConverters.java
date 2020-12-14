package jdk.nashorn.internal.runtime.linker;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;
import jdk.internal.dynalink.support.TypeUtilities;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.ConsString;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;

final class JavaArgumentConverters {
   private static final MethodHandle TO_BOOLEAN = findOwnMH("toBoolean", Boolean.class, Object.class);
   private static final MethodHandle TO_STRING = findOwnMH("toString", String.class, Object.class);
   private static final MethodHandle TO_DOUBLE = findOwnMH("toDouble", Double.class, Object.class);
   private static final MethodHandle TO_NUMBER = findOwnMH("toNumber", Number.class, Object.class);
   private static final MethodHandle TO_LONG = findOwnMH("toLong", Long.class, Object.class);
   private static final MethodHandle TO_LONG_PRIMITIVE;
   private static final MethodHandle TO_CHAR;
   private static final MethodHandle TO_CHAR_PRIMITIVE;
   private static final Map<Class<?>, MethodHandle> CONVERTERS;

   private JavaArgumentConverters() {
   }

   static MethodHandle getConverter(Class<?> targetType) {
      return (MethodHandle)CONVERTERS.get(targetType);
   }

   private static Boolean toBoolean(Object obj) {
      if (obj instanceof Boolean) {
         return (Boolean)obj;
      } else if (obj == null) {
         return null;
      } else if (obj == ScriptRuntime.UNDEFINED) {
         return null;
      } else if (!(obj instanceof Number)) {
         if (JSType.isString(obj)) {
            return ((CharSequence)obj).length() > 0;
         } else if (obj instanceof ScriptObject) {
            return true;
         } else {
            throw assertUnexpectedType(obj);
         }
      } else {
         double num = ((Number)obj).doubleValue();
         return num != 0.0D && !Double.isNaN(num);
      }
   }

   private static Character toChar(Object o) {
      if (o == null) {
         return null;
      } else if (o instanceof Number) {
         int ival = ((Number)o).intValue();
         if (ival >= 0 && ival <= 65535) {
            return (char)ival;
         } else {
            throw ECMAErrors.typeError("cant.convert.number.to.char");
         }
      } else {
         String s = toString(o);
         if (s == null) {
            return null;
         } else if (s.length() != 1) {
            throw ECMAErrors.typeError("cant.convert.string.to.char");
         } else {
            return s.charAt(0);
         }
      }
   }

   static char toCharPrimitive(Object obj0) {
      Character c = toChar(obj0);
      return c == null ? '\u0000' : c;
   }

   static String toString(Object obj) {
      return obj == null ? null : JSType.toString(obj);
   }

   private static Double toDouble(Object obj0) {
      for(Object obj = obj0; obj != null; obj = JSType.toPrimitive(obj, Number.class)) {
         if (obj instanceof Double) {
            return (Double)obj;
         }

         if (obj instanceof Number) {
            return ((Number)obj).doubleValue();
         }

         if (obj instanceof String) {
            return JSType.toNumber((String)obj);
         }

         if (obj instanceof ConsString) {
            return JSType.toNumber(obj.toString());
         }

         if (obj instanceof Boolean) {
            return (Boolean)obj ? 1.0D : 0.0D;
         }

         if (!(obj instanceof ScriptObject)) {
            if (obj == ScriptRuntime.UNDEFINED) {
               return Double.NaN;
            }

            throw assertUnexpectedType(obj);
         }
      }

      return null;
   }

   private static Number toNumber(Object obj0) {
      for(Object obj = obj0; obj != null; obj = JSType.toPrimitive(obj, Number.class)) {
         if (obj instanceof Number) {
            return (Number)obj;
         }

         if (obj instanceof String) {
            return JSType.toNumber((String)obj);
         }

         if (obj instanceof ConsString) {
            return JSType.toNumber(obj.toString());
         }

         if (obj instanceof Boolean) {
            return (Boolean)obj ? 1.0D : 0.0D;
         }

         if (!(obj instanceof ScriptObject)) {
            if (obj == ScriptRuntime.UNDEFINED) {
               return Double.NaN;
            }

            throw assertUnexpectedType(obj);
         }
      }

      return null;
   }

   private static Long toLong(Object obj0) {
      for(Object obj = obj0; obj != null; obj = JSType.toPrimitive(obj, Number.class)) {
         if (obj instanceof Long) {
            return (Long)obj;
         }

         if (obj instanceof Integer) {
            return ((Integer)obj).longValue();
         }

         if (obj instanceof Double) {
            Double d = (Double)obj;
            if (Double.isInfinite(d)) {
               return 0L;
            }

            return d.longValue();
         }

         if (obj instanceof Float) {
            Float f = (Float)obj;
            if (Float.isInfinite(f)) {
               return 0L;
            }

            return f.longValue();
         }

         if (obj instanceof Number) {
            return ((Number)obj).longValue();
         }

         if (JSType.isString(obj)) {
            return JSType.toLong(obj);
         }

         if (obj instanceof Boolean) {
            return (Boolean)obj ? 1L : 0L;
         }

         if (!(obj instanceof ScriptObject)) {
            if (obj == ScriptRuntime.UNDEFINED) {
               return null;
            }

            throw assertUnexpectedType(obj);
         }
      }

      return null;
   }

   private static AssertionError assertUnexpectedType(Object obj) {
      return new AssertionError("Unexpected type" + obj.getClass().getName() + ". Guards should have prevented this");
   }

   private static long toLongPrimitive(Object obj0) {
      Long l = toLong(obj0);
      return l == null ? 0L : l;
   }

   private static MethodHandle findOwnMH(String name, Class<?> rtype, Class<?>... types) {
      return Lookup.MH.findStatic(MethodHandles.lookup(), JavaArgumentConverters.class, name, Lookup.MH.type(rtype, types));
   }

   private static void putDoubleConverter(Class<?> targetType) {
      Class<?> primitive = TypeUtilities.getPrimitiveType(targetType);
      CONVERTERS.put(primitive, Lookup.MH.explicitCastArguments(JSType.TO_NUMBER.methodHandle(), JSType.TO_NUMBER.methodHandle().type().changeReturnType(primitive)));
      CONVERTERS.put(targetType, Lookup.MH.filterReturnValue(TO_DOUBLE, findOwnMH(primitive.getName() + "Value", targetType, Double.class)));
   }

   private static void putLongConverter(Class<?> targetType) {
      Class<?> primitive = TypeUtilities.getPrimitiveType(targetType);
      CONVERTERS.put(primitive, Lookup.MH.explicitCastArguments(TO_LONG_PRIMITIVE, TO_LONG_PRIMITIVE.type().changeReturnType(primitive)));
      CONVERTERS.put(targetType, Lookup.MH.filterReturnValue(TO_LONG, findOwnMH(primitive.getName() + "Value", targetType, Long.class)));
   }

   private static Byte byteValue(Long l) {
      return l == null ? null : l.byteValue();
   }

   private static Short shortValue(Long l) {
      return l == null ? null : l.shortValue();
   }

   private static Integer intValue(Long l) {
      return l == null ? null : l.intValue();
   }

   private static Float floatValue(Double d) {
      return d == null ? null : d.floatValue();
   }

   static {
      TO_LONG_PRIMITIVE = findOwnMH("toLongPrimitive", Long.TYPE, Object.class);
      TO_CHAR = findOwnMH("toChar", Character.class, Object.class);
      TO_CHAR_PRIMITIVE = findOwnMH("toCharPrimitive", Character.TYPE, Object.class);
      CONVERTERS = new HashMap();
      CONVERTERS.put(Number.class, TO_NUMBER);
      CONVERTERS.put(String.class, TO_STRING);
      CONVERTERS.put(Boolean.TYPE, JSType.TO_BOOLEAN.methodHandle());
      CONVERTERS.put(Boolean.class, TO_BOOLEAN);
      CONVERTERS.put(Character.TYPE, TO_CHAR_PRIMITIVE);
      CONVERTERS.put(Character.class, TO_CHAR);
      CONVERTERS.put(Double.TYPE, JSType.TO_NUMBER.methodHandle());
      CONVERTERS.put(Double.class, TO_DOUBLE);
      CONVERTERS.put(Long.TYPE, TO_LONG_PRIMITIVE);
      CONVERTERS.put(Long.class, TO_LONG);
      putLongConverter(Byte.class);
      putLongConverter(Short.class);
      putLongConverter(Integer.class);
      putDoubleConverter(Float.class);
   }
}
