package jdk.nashorn.internal.runtime;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.SwitchPoint;
import java.lang.reflect.Array;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import jdk.internal.dynalink.beans.StaticClass;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.internal.codegen.ApplySpecialization;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.ir.debug.JSONWriter;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.objects.NativeObject;
import jdk.nashorn.internal.parser.Lexer;

public final class ScriptRuntime {
   public static final Object[] EMPTY_ARRAY = new Object[0];
   public static final Undefined UNDEFINED = Undefined.getUndefined();
   public static final Undefined EMPTY = Undefined.getEmpty();
   public static final CompilerConstants.Call ADD = CompilerConstants.staticCallNoLookup(ScriptRuntime.class, "ADD", Object.class, Object.class, Object.class);
   public static final CompilerConstants.Call EQ_STRICT;
   public static final CompilerConstants.Call OPEN_WITH;
   public static final CompilerConstants.Call MERGE_SCOPE;
   public static final CompilerConstants.Call TO_PROPERTY_ITERATOR;
   public static final CompilerConstants.Call TO_VALUE_ITERATOR;
   public static final CompilerConstants.Call APPLY;
   public static final CompilerConstants.Call THROW_REFERENCE_ERROR;
   public static final CompilerConstants.Call THROW_CONST_TYPE_ERROR;
   public static final CompilerConstants.Call INVALIDATE_RESERVED_BUILTIN_NAME;

   private ScriptRuntime() {
   }

   public static int switchTagAsInt(Object tag, int deflt) {
      if (tag instanceof Number) {
         double d = ((Number)tag).doubleValue();
         if (JSType.isRepresentableAsInt(d)) {
            return (int)d;
         }
      }

      return deflt;
   }

   public static int switchTagAsInt(boolean tag, int deflt) {
      return deflt;
   }

   public static int switchTagAsInt(long tag, int deflt) {
      return JSType.isRepresentableAsInt(tag) ? (int)tag : deflt;
   }

   public static int switchTagAsInt(double tag, int deflt) {
      return JSType.isRepresentableAsInt(tag) ? (int)tag : deflt;
   }

   public static String builtinObjectToString(Object self) {
      JSType type = JSType.ofNoFunction(self);
      String className;
      switch(type) {
      case BOOLEAN:
         className = "Boolean";
         break;
      case NUMBER:
         className = "Number";
         break;
      case STRING:
         className = "String";
         break;
      case NULL:
         className = "Null";
         break;
      case UNDEFINED:
         className = "Undefined";
         break;
      case OBJECT:
         if (self instanceof ScriptObject) {
            className = ((ScriptObject)self).getClassName();
         } else if (self instanceof JSObject) {
            className = ((JSObject)self).getClassName();
         } else {
            className = self.getClass().getName();
         }
         break;
      default:
         className = self.getClass().getName();
      }

      StringBuilder sb = new StringBuilder();
      sb.append("[object ");
      sb.append(className);
      sb.append(']');
      return sb.toString();
   }

   public static String safeToString(Object obj) {
      return JSType.toStringImpl(obj, true);
   }

   public static Iterator<?> toPropertyIterator(Object obj) {
      if (obj instanceof ScriptObject) {
         return ((ScriptObject)obj).propertyIterator();
      } else if (obj != null && obj.getClass().isArray()) {
         return new ScriptRuntime.RangeIterator(Array.getLength(obj));
      } else if (obj instanceof JSObject) {
         return ((JSObject)obj).keySet().iterator();
      } else if (obj instanceof List) {
         return new ScriptRuntime.RangeIterator(((List)obj).size());
      } else if (obj instanceof Map) {
         return ((Map)obj).keySet().iterator();
      } else {
         Object wrapped = Global.instance().wrapAsObject(obj);
         return wrapped instanceof ScriptObject ? ((ScriptObject)wrapped).propertyIterator() : Collections.emptyIterator();
      }
   }

   public static Iterator<?> toValueIterator(final Object obj) {
      if (obj instanceof ScriptObject) {
         return ((ScriptObject)obj).valueIterator();
      } else if (obj != null && obj.getClass().isArray()) {
         final int length = Array.getLength(obj);
         return new Iterator<Object>() {
            private int index = 0;

            public boolean hasNext() {
               return this.index < length;
            }

            public Object next() {
               if (this.index >= length) {
                  throw new NoSuchElementException();
               } else {
                  return Array.get(obj, this.index++);
               }
            }

            public void remove() {
               throw new UnsupportedOperationException("remove");
            }
         };
      } else if (obj instanceof JSObject) {
         return ((JSObject)obj).values().iterator();
      } else if (obj instanceof Map) {
         return ((Map)obj).values().iterator();
      } else if (obj instanceof Iterable) {
         return ((Iterable)obj).iterator();
      } else {
         Object wrapped = Global.instance().wrapAsObject(obj);
         return wrapped instanceof ScriptObject ? ((ScriptObject)wrapped).valueIterator() : Collections.emptyIterator();
      }
   }

   public static ScriptObject mergeScope(ScriptObject scope) {
      ScriptObject parentScope = scope.getProto();
      parentScope.addBoundProperties(scope);
      return parentScope;
   }

   public static Object apply(ScriptFunction target, Object self, Object... args) {
      try {
         return target.invoke(self, args);
      } catch (Error | RuntimeException var4) {
         throw var4;
      } catch (Throwable var5) {
         throw new RuntimeException(var5);
      }
   }

   public static void throwReferenceError(String name) {
      throw ECMAErrors.referenceError("not.defined", name);
   }

   public static void throwConstTypeError(String name) {
      throw ECMAErrors.typeError("assign.constant", name);
   }

   public static Object construct(ScriptFunction target, Object... args) {
      try {
         return target.construct(args);
      } catch (Error | RuntimeException var3) {
         throw var3;
      } catch (Throwable var4) {
         throw new RuntimeException(var4);
      }
   }

   public static boolean sameValue(Object x, Object y) {
      JSType xType = JSType.ofNoFunction(x);
      JSType yType = JSType.ofNoFunction(y);
      if (xType != yType) {
         return false;
      } else if (xType != JSType.UNDEFINED && xType != JSType.NULL) {
         if (xType == JSType.NUMBER) {
            double xVal = ((Number)x).doubleValue();
            double yVal = ((Number)y).doubleValue();
            if (Double.isNaN(xVal) && Double.isNaN(yVal)) {
               return true;
            } else if (xVal == 0.0D && Double.doubleToLongBits(xVal) != Double.doubleToLongBits(yVal)) {
               return false;
            } else {
               return xVal == yVal;
            }
         } else if (xType != JSType.STRING && yType != JSType.BOOLEAN) {
            return x == y;
         } else {
            return x.equals(y);
         }
      } else {
         return true;
      }
   }

   public static String parse(String code, String name, boolean includeLoc) {
      return JSONWriter.parse(Context.getContextTrusted(), code, name, includeLoc);
   }

   public static boolean isJSWhitespace(char ch) {
      return Lexer.isJSWhitespace(ch);
   }

   public static ScriptObject openWith(ScriptObject scope, Object expression) {
      Global global = Context.getGlobal();
      if (expression == UNDEFINED) {
         throw ECMAErrors.typeError(global, "cant.apply.with.to.undefined");
      } else if (expression == null) {
         throw ECMAErrors.typeError(global, "cant.apply.with.to.null");
      } else {
         Object wrappedExpr;
         if (expression instanceof ScriptObjectMirror) {
            wrappedExpr = ScriptObjectMirror.unwrap(expression, global);
            if (wrappedExpr instanceof ScriptObject) {
               return new WithObject(scope, (ScriptObject)wrappedExpr);
            } else {
               ScriptObject exprObj = global.newObject();
               NativeObject.bindAllProperties(exprObj, (ScriptObjectMirror)expression);
               return new WithObject(scope, exprObj);
            }
         } else {
            wrappedExpr = JSType.toScriptObject(global, expression);
            if (wrappedExpr instanceof ScriptObject) {
               return new WithObject(scope, (ScriptObject)wrappedExpr);
            } else {
               throw ECMAErrors.typeError(global, "cant.apply.with.to.non.scriptobject");
            }
         }
      }
   }

   public static Object ADD(Object x, Object y) {
      boolean xIsNumber = x instanceof Number;
      boolean yIsNumber = y instanceof Number;
      if (xIsNumber && yIsNumber) {
         return ((Number)x).doubleValue() + ((Number)y).doubleValue();
      } else {
         boolean xIsUndefined = x == UNDEFINED;
         boolean yIsUndefined = y == UNDEFINED;
         if ((!xIsNumber || !yIsUndefined) && (!xIsUndefined || !yIsNumber) && (!xIsUndefined || !yIsUndefined)) {
            Object xPrim = JSType.toPrimitive(x);
            Object yPrim = JSType.toPrimitive(y);
            if (!JSType.isString(xPrim) && !JSType.isString(yPrim)) {
               return JSType.toNumber(xPrim) + JSType.toNumber(yPrim);
            } else {
               try {
                  return new ConsString(JSType.toCharSequence(xPrim), JSType.toCharSequence(yPrim));
               } catch (IllegalArgumentException var9) {
                  throw ECMAErrors.rangeError((Throwable)var9, "concat.string.too.big");
               }
            }
         } else {
            return Double.NaN;
         }
      }
   }

   public static Object DEBUGGER() {
      return UNDEFINED;
   }

   public static Object NEW(Object clazz, Object... args) {
      return UNDEFINED;
   }

   public static Object TYPEOF(Object object, Object property) {
      Object obj = object;
      if (property != null) {
         if (object instanceof ScriptObject) {
            obj = ((ScriptObject)object).get(property);
            if (Global.isLocationPropertyPlaceholder(obj)) {
               if (CompilerConstants.__LINE__.name().equals(property)) {
                  obj = 0;
               } else {
                  obj = "";
               }
            }
         } else if (object instanceof Undefined) {
            obj = ((Undefined)object).get(property);
         } else {
            if (object == null) {
               throw ECMAErrors.typeError("cant.get.property", safeToString(property), "null");
            }

            if (JSType.isPrimitive(object)) {
               obj = ((ScriptObject)JSType.toScriptObject(object)).get(property);
            } else if (object instanceof JSObject) {
               obj = ((JSObject)object).getMember(property.toString());
            } else {
               obj = UNDEFINED;
            }
         }
      }

      return JSType.of(obj).typeName();
   }

   public static Object REFERENCE_ERROR(Object lhs, Object rhs, Object msg) {
      throw ECMAErrors.referenceError("cant.be.used.as.lhs", Objects.toString(msg));
   }

   public static boolean DELETE(Object obj, Object property, Object strict) {
      if (obj instanceof ScriptObject) {
         return ((ScriptObject)obj).delete(property, Boolean.TRUE.equals(strict));
      } else if (obj instanceof Undefined) {
         return ((Undefined)obj).delete(property, false);
      } else if (obj == null) {
         throw ECMAErrors.typeError("cant.delete.property", safeToString(property), "null");
      } else if (obj instanceof ScriptObjectMirror) {
         return ((ScriptObjectMirror)obj).delete(property);
      } else if (JSType.isPrimitive(obj)) {
         return ((ScriptObject)JSType.toScriptObject(obj)).delete(property, Boolean.TRUE.equals(strict));
      } else if (obj instanceof JSObject) {
         ((JSObject)obj).removeMember(Objects.toString(property));
         return true;
      } else {
         return true;
      }
   }

   public static boolean SLOW_DELETE(Object obj, Object property, Object strict) {
      if (obj instanceof ScriptObject) {
         ScriptObject sobj = (ScriptObject)obj;

         for(String key = property.toString(); sobj != null && sobj.isScope(); sobj = sobj.getProto()) {
            FindProperty find = sobj.findProperty(key, false);
            if (find != null) {
               return sobj.delete(key, Boolean.TRUE.equals(strict));
            }
         }
      }

      return DELETE(obj, property, strict);
   }

   public static boolean FAIL_DELETE(Object property, Object strict) {
      if (Boolean.TRUE.equals(strict)) {
         throw ECMAErrors.syntaxError("strict.cant.delete", safeToString(property));
      } else {
         return false;
      }
   }

   public static boolean EQ(Object x, Object y) {
      return equals(x, y);
   }

   public static boolean NE(Object x, Object y) {
      return !EQ(x, y);
   }

   private static boolean equals(Object x, Object y) {
      if (x == y) {
         return true;
      } else if (x instanceof ScriptObject && y instanceof ScriptObject) {
         return false;
      } else {
         return !(x instanceof ScriptObjectMirror) && !(y instanceof ScriptObjectMirror) ? equalValues(x, y) : ScriptObjectMirror.identical(x, y);
      }
   }

   private static boolean equalValues(Object x, Object y) {
      JSType xType = JSType.ofNoFunction(x);
      JSType yType = JSType.ofNoFunction(y);
      return xType == yType ? equalSameTypeValues(x, y, xType) : equalDifferentTypeValues(x, y, xType, yType);
   }

   private static boolean equalSameTypeValues(Object x, Object y, JSType type) {
      if (type != JSType.UNDEFINED && type != JSType.NULL) {
         if (type == JSType.NUMBER) {
            return ((Number)x).doubleValue() == ((Number)y).doubleValue();
         } else if (type == JSType.STRING) {
            return x.toString().equals(y.toString());
         } else if (type == JSType.BOOLEAN) {
            return (Boolean)x == (Boolean)y;
         } else {
            return x == y;
         }
      } else {
         return true;
      }
   }

   private static boolean equalDifferentTypeValues(Object x, Object y, JSType xType, JSType yType) {
      if (!isUndefinedAndNull(xType, yType) && !isUndefinedAndNull(yType, xType)) {
         if (isNumberAndString(xType, yType)) {
            return equalNumberToString(x, y);
         } else if (isNumberAndString(yType, xType)) {
            return equalNumberToString(y, x);
         } else if (xType == JSType.BOOLEAN) {
            return equalBooleanToAny(x, y);
         } else if (yType == JSType.BOOLEAN) {
            return equalBooleanToAny(y, x);
         } else if (isNumberOrStringAndObject(xType, yType)) {
            return equalNumberOrStringToObject(x, y);
         } else {
            return isNumberOrStringAndObject(yType, xType) ? equalNumberOrStringToObject(y, x) : false;
         }
      } else {
         return true;
      }
   }

   private static boolean isUndefinedAndNull(JSType xType, JSType yType) {
      return xType == JSType.UNDEFINED && yType == JSType.NULL;
   }

   private static boolean isNumberAndString(JSType xType, JSType yType) {
      return xType == JSType.NUMBER && yType == JSType.STRING;
   }

   private static boolean isNumberOrStringAndObject(JSType xType, JSType yType) {
      return (xType == JSType.NUMBER || xType == JSType.STRING) && yType == JSType.OBJECT;
   }

   private static boolean equalNumberToString(Object num, Object str) {
      return ((Number)num).doubleValue() == JSType.toNumber(str.toString());
   }

   private static boolean equalBooleanToAny(Object bool, Object any) {
      return equals(JSType.toNumber((Boolean)bool), any);
   }

   private static boolean equalNumberOrStringToObject(Object numOrStr, Object any) {
      return equals(numOrStr, JSType.toPrimitive(any));
   }

   public static boolean EQ_STRICT(Object x, Object y) {
      return strictEquals(x, y);
   }

   public static boolean NE_STRICT(Object x, Object y) {
      return !EQ_STRICT(x, y);
   }

   private static boolean strictEquals(Object x, Object y) {
      JSType xType = JSType.ofNoFunction(x);
      JSType yType = JSType.ofNoFunction(y);
      return xType != yType ? false : equalSameTypeValues(x, y, xType);
   }

   public static boolean IN(Object property, Object obj) {
      JSType rvalType = JSType.ofNoFunction(obj);
      if (rvalType == JSType.OBJECT) {
         if (obj instanceof ScriptObject) {
            return ((ScriptObject)obj).has(property);
         } else {
            return obj instanceof JSObject ? ((JSObject)obj).hasMember(Objects.toString(property)) : false;
         }
      } else {
         throw ECMAErrors.typeError("in.with.non.object", rvalType.toString().toLowerCase(Locale.ENGLISH));
      }
   }

   public static boolean INSTANCEOF(Object obj, Object clazz) {
      if (clazz instanceof ScriptFunction) {
         return obj instanceof ScriptObject ? ((ScriptObject)clazz).isInstance((ScriptObject)obj) : false;
      } else if (clazz instanceof StaticClass) {
         return ((StaticClass)clazz).getRepresentedClass().isInstance(obj);
      } else if (clazz instanceof JSObject) {
         return ((JSObject)clazz).isInstance(obj);
      } else if (obj instanceof JSObject) {
         return ((JSObject)obj).isInstanceOf(clazz);
      } else {
         throw ECMAErrors.typeError("instanceof.on.non.object");
      }
   }

   public static boolean LT(Object x, Object y) {
      Object px = JSType.toPrimitive(x, Number.class);
      Object py = JSType.toPrimitive(y, Number.class);
      return areBothString(px, py) ? px.toString().compareTo(py.toString()) < 0 : JSType.toNumber(px) < JSType.toNumber(py);
   }

   private static boolean areBothString(Object x, Object y) {
      return JSType.isString(x) && JSType.isString(y);
   }

   public static boolean GT(Object x, Object y) {
      Object px = JSType.toPrimitive(x, Number.class);
      Object py = JSType.toPrimitive(y, Number.class);
      return areBothString(px, py) ? px.toString().compareTo(py.toString()) > 0 : JSType.toNumber(px) > JSType.toNumber(py);
   }

   public static boolean LE(Object x, Object y) {
      Object px = JSType.toPrimitive(x, Number.class);
      Object py = JSType.toPrimitive(y, Number.class);
      return areBothString(px, py) ? px.toString().compareTo(py.toString()) <= 0 : JSType.toNumber(px) <= JSType.toNumber(py);
   }

   public static boolean GE(Object x, Object y) {
      Object px = JSType.toPrimitive(x, Number.class);
      Object py = JSType.toPrimitive(y, Number.class);
      return areBothString(px, py) ? px.toString().compareTo(py.toString()) >= 0 : JSType.toNumber(px) >= JSType.toNumber(py);
   }

   public static void invalidateReservedBuiltinName(String name) {
      Context context = Context.getContextTrusted();
      SwitchPoint sp = context.getBuiltinSwitchPoint(name);

      assert sp != null;

      context.getLogger(ApplySpecialization.class).info("Overwrote special name '" + name + "' - invalidating switchpoint");
      SwitchPoint.invalidateAll(new SwitchPoint[]{sp});
   }

   static {
      EQ_STRICT = CompilerConstants.staticCallNoLookup(ScriptRuntime.class, "EQ_STRICT", Boolean.TYPE, Object.class, Object.class);
      OPEN_WITH = CompilerConstants.staticCallNoLookup(ScriptRuntime.class, "openWith", ScriptObject.class, ScriptObject.class, Object.class);
      MERGE_SCOPE = CompilerConstants.staticCallNoLookup(ScriptRuntime.class, "mergeScope", ScriptObject.class, ScriptObject.class);
      TO_PROPERTY_ITERATOR = CompilerConstants.staticCallNoLookup(ScriptRuntime.class, "toPropertyIterator", Iterator.class, Object.class);
      TO_VALUE_ITERATOR = CompilerConstants.staticCallNoLookup(ScriptRuntime.class, "toValueIterator", Iterator.class, Object.class);
      APPLY = CompilerConstants.staticCall(MethodHandles.lookup(), ScriptRuntime.class, "apply", Object.class, ScriptFunction.class, Object.class, Object[].class);
      THROW_REFERENCE_ERROR = CompilerConstants.staticCall(MethodHandles.lookup(), ScriptRuntime.class, "throwReferenceError", Void.TYPE, String.class);
      THROW_CONST_TYPE_ERROR = CompilerConstants.staticCall(MethodHandles.lookup(), ScriptRuntime.class, "throwConstTypeError", Void.TYPE, String.class);
      INVALIDATE_RESERVED_BUILTIN_NAME = CompilerConstants.staticCallNoLookup(ScriptRuntime.class, "invalidateReservedBuiltinName", Void.TYPE, String.class);
   }

   private static final class RangeIterator implements Iterator<Integer> {
      private final int length;
      private int index;

      RangeIterator(int length) {
         this.length = length;
      }

      public boolean hasNext() {
         return this.index < this.length;
      }

      public Integer next() {
         return this.index++;
      }

      public void remove() {
         throw new UnsupportedOperationException("remove");
      }
   }
}
