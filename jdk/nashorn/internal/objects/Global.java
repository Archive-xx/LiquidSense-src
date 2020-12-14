package jdk.nashorn.internal.objects;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.SwitchPoint;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.nashorn.api.scripting.ClassFilter;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.AccessorProperty;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.FindProperty;
import jdk.nashorn.internal.runtime.GlobalConstants;
import jdk.nashorn.internal.runtime.GlobalFunctions;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.NativeJavaPackage;
import jdk.nashorn.internal.runtime.Property;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.Scope;
import jdk.nashorn.internal.runtime.ScriptEnvironment;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.ScriptingFunctions;
import jdk.nashorn.internal.runtime.Specialization;
import jdk.nashorn.internal.runtime.arrays.ArrayData;
import jdk.nashorn.internal.runtime.linker.Bootstrap;
import jdk.nashorn.internal.runtime.linker.InvokeByName;
import jdk.nashorn.internal.runtime.linker.NashornCallSiteDescriptor;
import jdk.nashorn.internal.runtime.regexp.RegExpResult;
import jdk.nashorn.internal.scripts.JD;
import jdk.nashorn.internal.scripts.JO;
import jdk.nashorn.tools.ShellFunctions;

public final class Global extends Scope {
   private static final Object LAZY_SENTINEL = new Object();
   private static final Object LOCATION_PLACEHOLDER = new Object();
   private final InvokeByName TO_STRING = new InvokeByName("toString", ScriptObject.class);
   private final InvokeByName VALUE_OF = new InvokeByName("valueOf", ScriptObject.class);
   public Object arguments;
   public Object parseInt;
   public Object parseFloat;
   public Object isNaN;
   public Object isFinite;
   public Object encodeURI;
   public Object encodeURIComponent;
   public Object decodeURI;
   public Object decodeURIComponent;
   public Object escape;
   public Object unescape;
   public Object print;
   public Object load;
   public Object loadWithNewGlobal;
   public Object exit;
   public Object quit;
   public static final double NaN = Double.NaN;
   public static final double Infinity = Double.POSITIVE_INFINITY;
   public static final Object undefined;
   public Object eval;
   public volatile Object object;
   public volatile Object function;
   public volatile Object array;
   public volatile Object string;
   public volatile Object _boolean;
   public volatile Object number;
   private volatile Object date;
   private volatile Object regexp;
   private volatile Object json;
   private volatile Object jsadapter;
   public volatile Object math;
   public volatile Object error;
   private volatile Object evalError;
   private volatile Object rangeError;
   public volatile Object referenceError;
   public volatile Object syntaxError;
   public volatile Object typeError;
   private volatile Object uriError;
   private volatile Object arrayBuffer;
   private volatile Object dataView;
   private volatile Object int8Array;
   private volatile Object uint8Array;
   private volatile Object uint8ClampedArray;
   private volatile Object int16Array;
   private volatile Object uint16Array;
   private volatile Object int32Array;
   private volatile Object uint32Array;
   private volatile Object float32Array;
   private volatile Object float64Array;
   public volatile Object packages;
   public volatile Object com;
   public volatile Object edu;
   public volatile Object java;
   public volatile Object javafx;
   public volatile Object javax;
   public volatile Object org;
   private volatile Object javaImporter;
   private volatile Object javaApi;
   public static final Object __FILE__;
   public static final Object __DIR__;
   public static final Object __LINE__;
   private volatile NativeDate DEFAULT_DATE;
   private volatile NativeRegExp DEFAULT_REGEXP;
   private ScriptFunction builtinFunction;
   private ScriptFunction builtinObject;
   private ScriptFunction builtinArray;
   private ScriptFunction builtinBoolean;
   private ScriptFunction builtinDate;
   private ScriptObject builtinJSON;
   private ScriptFunction builtinJSAdapter;
   private ScriptObject builtinMath;
   private ScriptFunction builtinNumber;
   private ScriptFunction builtinRegExp;
   private ScriptFunction builtinString;
   private ScriptFunction builtinError;
   private ScriptFunction builtinEval;
   private ScriptFunction builtinEvalError;
   private ScriptFunction builtinRangeError;
   private ScriptFunction builtinReferenceError;
   private ScriptFunction builtinSyntaxError;
   private ScriptFunction builtinTypeError;
   private ScriptFunction builtinURIError;
   private ScriptObject builtinPackages;
   private ScriptObject builtinCom;
   private ScriptObject builtinEdu;
   private ScriptObject builtinJava;
   private ScriptObject builtinJavafx;
   private ScriptObject builtinJavax;
   private ScriptObject builtinOrg;
   private ScriptFunction builtinJavaImporter;
   private ScriptObject builtinJavaApi;
   private ScriptFunction builtinArrayBuffer;
   private ScriptFunction builtinDataView;
   private ScriptFunction builtinInt8Array;
   private ScriptFunction builtinUint8Array;
   private ScriptFunction builtinUint8ClampedArray;
   private ScriptFunction builtinInt16Array;
   private ScriptFunction builtinUint16Array;
   private ScriptFunction builtinInt32Array;
   private ScriptFunction builtinUint32Array;
   private ScriptFunction builtinFloat32Array;
   private ScriptFunction builtinFloat64Array;
   private ScriptFunction typeErrorThrower;
   private RegExpResult lastRegExpResult;
   private static final MethodHandle EVAL;
   private static final MethodHandle NO_SUCH_PROPERTY;
   private static final MethodHandle PRINT;
   private static final MethodHandle PRINTLN;
   private static final MethodHandle LOAD;
   private static final MethodHandle LOAD_WITH_NEW_GLOBAL;
   private static final MethodHandle EXIT;
   private static final MethodHandle LEXICAL_SCOPE_FILTER;
   private static PropertyMap $nasgenmap$;
   private final Context context;
   private ThreadLocal<ScriptContext> scontext;
   private ScriptEngine engine;
   private volatile ScriptContext initscontext;
   private final Global.LexicalScope lexicalScope;
   private SwitchPoint lexicalScopeSwitchPoint;
   private final Map<Object, InvokeByName> namedInvokers;
   private final Map<Object, MethodHandle> dynamicInvokers;

   public static Object getDate(Object self) {
      Global global = instanceFrom(self);
      if (global.date == LAZY_SENTINEL) {
         global.date = global.getBuiltinDate();
      }

      return global.date;
   }

   public static void setDate(Object self, Object value) {
      Global global = instanceFrom(self);
      global.date = value;
   }

   public static Object getRegExp(Object self) {
      Global global = instanceFrom(self);
      if (global.regexp == LAZY_SENTINEL) {
         global.regexp = global.getBuiltinRegExp();
      }

      return global.regexp;
   }

   public static void setRegExp(Object self, Object value) {
      Global global = instanceFrom(self);
      global.regexp = value;
   }

   public static Object getJSON(Object self) {
      Global global = instanceFrom(self);
      if (global.json == LAZY_SENTINEL) {
         global.json = global.getBuiltinJSON();
      }

      return global.json;
   }

   public static void setJSON(Object self, Object value) {
      Global global = instanceFrom(self);
      global.json = value;
   }

   public static Object getJSAdapter(Object self) {
      Global global = instanceFrom(self);
      if (global.jsadapter == LAZY_SENTINEL) {
         global.jsadapter = global.getBuiltinJSAdapter();
      }

      return global.jsadapter;
   }

   public static void setJSAdapter(Object self, Object value) {
      Global global = instanceFrom(self);
      global.jsadapter = value;
   }

   public static Object getEvalError(Object self) {
      Global global = instanceFrom(self);
      if (global.evalError == LAZY_SENTINEL) {
         global.evalError = global.getBuiltinEvalError();
      }

      return global.evalError;
   }

   public static void setEvalError(Object self, Object value) {
      Global global = instanceFrom(self);
      global.evalError = value;
   }

   public static Object getRangeError(Object self) {
      Global global = instanceFrom(self);
      if (global.rangeError == LAZY_SENTINEL) {
         global.rangeError = global.getBuiltinRangeError();
      }

      return global.rangeError;
   }

   public static void setRangeError(Object self, Object value) {
      Global global = instanceFrom(self);
      global.rangeError = value;
   }

   public static Object getURIError(Object self) {
      Global global = instanceFrom(self);
      if (global.uriError == LAZY_SENTINEL) {
         global.uriError = global.getBuiltinURIError();
      }

      return global.uriError;
   }

   public static void setURIError(Object self, Object value) {
      Global global = instanceFrom(self);
      global.uriError = value;
   }

   public static Object getArrayBuffer(Object self) {
      Global global = instanceFrom(self);
      if (global.arrayBuffer == LAZY_SENTINEL) {
         global.arrayBuffer = global.getBuiltinArrayBuffer();
      }

      return global.arrayBuffer;
   }

   public static void setArrayBuffer(Object self, Object value) {
      Global global = instanceFrom(self);
      global.arrayBuffer = value;
   }

   public static Object getDataView(Object self) {
      Global global = instanceFrom(self);
      if (global.dataView == LAZY_SENTINEL) {
         global.dataView = global.getBuiltinDataView();
      }

      return global.dataView;
   }

   public static void setDataView(Object self, Object value) {
      Global global = instanceFrom(self);
      global.dataView = value;
   }

   public static Object getInt8Array(Object self) {
      Global global = instanceFrom(self);
      if (global.int8Array == LAZY_SENTINEL) {
         global.int8Array = global.getBuiltinInt8Array();
      }

      return global.int8Array;
   }

   public static void setInt8Array(Object self, Object value) {
      Global global = instanceFrom(self);
      global.int8Array = value;
   }

   public static Object getUint8Array(Object self) {
      Global global = instanceFrom(self);
      if (global.uint8Array == LAZY_SENTINEL) {
         global.uint8Array = global.getBuiltinUint8Array();
      }

      return global.uint8Array;
   }

   public static void setUint8Array(Object self, Object value) {
      Global global = instanceFrom(self);
      global.uint8Array = value;
   }

   public static Object getUint8ClampedArray(Object self) {
      Global global = instanceFrom(self);
      if (global.uint8ClampedArray == LAZY_SENTINEL) {
         global.uint8ClampedArray = global.getBuiltinUint8ClampedArray();
      }

      return global.uint8ClampedArray;
   }

   public static void setUint8ClampedArray(Object self, Object value) {
      Global global = instanceFrom(self);
      global.uint8ClampedArray = value;
   }

   public static Object getInt16Array(Object self) {
      Global global = instanceFrom(self);
      if (global.int16Array == LAZY_SENTINEL) {
         global.int16Array = global.getBuiltinInt16Array();
      }

      return global.int16Array;
   }

   public static void setInt16Array(Object self, Object value) {
      Global global = instanceFrom(self);
      global.int16Array = value;
   }

   public static Object getUint16Array(Object self) {
      Global global = instanceFrom(self);
      if (global.uint16Array == LAZY_SENTINEL) {
         global.uint16Array = global.getBuiltinUint16Array();
      }

      return global.uint16Array;
   }

   public static void setUint16Array(Object self, Object value) {
      Global global = instanceFrom(self);
      global.uint16Array = value;
   }

   public static Object getInt32Array(Object self) {
      Global global = instanceFrom(self);
      if (global.int32Array == LAZY_SENTINEL) {
         global.int32Array = global.getBuiltinInt32Array();
      }

      return global.int32Array;
   }

   public static void setInt32Array(Object self, Object value) {
      Global global = instanceFrom(self);
      global.int32Array = value;
   }

   public static Object getUint32Array(Object self) {
      Global global = instanceFrom(self);
      if (global.uint32Array == LAZY_SENTINEL) {
         global.uint32Array = global.getBuiltinUint32Array();
      }

      return global.uint32Array;
   }

   public static void setUint32Array(Object self, Object value) {
      Global global = instanceFrom(self);
      global.uint32Array = value;
   }

   public static Object getFloat32Array(Object self) {
      Global global = instanceFrom(self);
      if (global.float32Array == LAZY_SENTINEL) {
         global.float32Array = global.getBuiltinFloat32Array();
      }

      return global.float32Array;
   }

   public static void setFloat32Array(Object self, Object value) {
      Global global = instanceFrom(self);
      global.float32Array = value;
   }

   public static Object getFloat64Array(Object self) {
      Global global = instanceFrom(self);
      if (global.float64Array == LAZY_SENTINEL) {
         global.float64Array = global.getBuiltinFloat64Array();
      }

      return global.float64Array;
   }

   public static void setFloat64Array(Object self, Object value) {
      Global global = instanceFrom(self);
      global.float64Array = value;
   }

   public static Object getJavaImporter(Object self) {
      Global global = instanceFrom(self);
      if (global.javaImporter == LAZY_SENTINEL) {
         global.javaImporter = global.getBuiltinJavaImporter();
      }

      return global.javaImporter;
   }

   public static void setJavaImporter(Object self, Object value) {
      Global global = instanceFrom(self);
      global.javaImporter = value;
   }

   public static Object getJavaApi(Object self) {
      Global global = instanceFrom(self);
      if (global.javaApi == LAZY_SENTINEL) {
         global.javaApi = global.getBuiltinJavaApi();
      }

      return global.javaApi;
   }

   public static void setJavaApi(Object self, Object value) {
      Global global = instanceFrom(self);
      global.javaApi = value;
   }

   NativeDate getDefaultDate() {
      return this.DEFAULT_DATE;
   }

   NativeRegExp getDefaultRegExp() {
      return this.DEFAULT_REGEXP;
   }

   public void setScriptContext(ScriptContext ctxt) {
      assert this.scontext != null;

      this.scontext.set(ctxt);
   }

   public ScriptContext getScriptContext() {
      assert this.scontext != null;

      return (ScriptContext)this.scontext.get();
   }

   public void setInitScriptContext(ScriptContext ctxt) {
      this.initscontext = ctxt;
   }

   private ScriptContext currentContext() {
      ScriptContext sc = this.scontext != null ? (ScriptContext)this.scontext.get() : null;
      if (sc != null) {
         return sc;
      } else if (this.initscontext != null) {
         return this.initscontext;
      } else {
         return this.engine != null ? this.engine.getContext() : null;
      }
   }

   protected Context getContext() {
      return this.context;
   }

   protected boolean useDualFields() {
      return this.context.useDualFields();
   }

   private static PropertyMap checkAndGetMap(Context context) {
      SecurityManager sm = System.getSecurityManager();
      if (sm != null) {
         sm.checkPermission(new RuntimePermission("nashorn.createGlobal"));
      }

      Objects.requireNonNull(context);
      return $nasgenmap$;
   }

   public Global(Context context) {
      super(checkAndGetMap(context));
      this.date = LAZY_SENTINEL;
      this.regexp = LAZY_SENTINEL;
      this.json = LAZY_SENTINEL;
      this.jsadapter = LAZY_SENTINEL;
      this.evalError = LAZY_SENTINEL;
      this.rangeError = LAZY_SENTINEL;
      this.uriError = LAZY_SENTINEL;
      this.namedInvokers = new ConcurrentHashMap();
      this.dynamicInvokers = new ConcurrentHashMap();
      this.context = context;
      this.lexicalScope = context.getEnv()._es6 ? new Global.LexicalScope(this) : null;
   }

   public static Global instance() {
      return (Global)Objects.requireNonNull(Context.getGlobal());
   }

   private static Global instanceFrom(Object self) {
      return self instanceof Global ? (Global)self : instance();
   }

   public static boolean hasInstance() {
      return Context.getGlobal() != null;
   }

   static ScriptEnvironment getEnv() {
      return instance().getContext().getEnv();
   }

   static Context getThisContext() {
      return instance().getContext();
   }

   public ClassFilter getClassFilter() {
      return this.context.getClassFilter();
   }

   public boolean isOfContext(Context ctxt) {
      return this.context == ctxt;
   }

   public boolean isStrictContext() {
      return this.context.getEnv()._strict;
   }

   public void initBuiltinObjects(ScriptEngine eng) {
      if (this.builtinObject == null) {
         this.engine = eng;
         if (this.engine != null) {
            this.scontext = new ThreadLocal();
         }

         this.init(eng);
      }
   }

   public Object wrapAsObject(Object obj) {
      if (obj instanceof Boolean) {
         return new NativeBoolean((Boolean)obj, this);
      } else if (obj instanceof Number) {
         return new NativeNumber(((Number)obj).doubleValue(), this);
      } else if (JSType.isString(obj)) {
         return new NativeString((CharSequence)obj, this);
      } else if (obj instanceof Object[]) {
         return new NativeArray(ArrayData.allocate((Object[])((Object[])obj)), this);
      } else if (obj instanceof double[]) {
         return new NativeArray(ArrayData.allocate((double[])((double[])obj)), this);
      } else if (obj instanceof int[]) {
         return new NativeArray(ArrayData.allocate((int[])((int[])obj)), this);
      } else {
         return obj instanceof ArrayData ? new NativeArray((ArrayData)obj, this) : obj;
      }
   }

   public static GuardedInvocation primitiveLookup(LinkRequest request, Object self) {
      if (JSType.isString(self)) {
         return NativeString.lookupPrimitive(request, self);
      } else if (self instanceof Number) {
         return NativeNumber.lookupPrimitive(request, self);
      } else if (self instanceof Boolean) {
         return NativeBoolean.lookupPrimitive(request, self);
      } else {
         throw new IllegalArgumentException("Unsupported primitive: " + self);
      }
   }

   public static MethodHandle getPrimitiveWrapFilter(Object self) {
      if (JSType.isString(self)) {
         return NativeString.WRAPFILTER;
      } else if (self instanceof Number) {
         return NativeNumber.WRAPFILTER;
      } else if (self instanceof Boolean) {
         return NativeBoolean.WRAPFILTER;
      } else {
         throw new IllegalArgumentException("Unsupported primitive: " + self);
      }
   }

   public ScriptObject newObject() {
      return (ScriptObject)(this.useDualFields() ? new JD(this.getObjectPrototype()) : new JO(this.getObjectPrototype()));
   }

   public Object getDefaultValue(ScriptObject sobj, Class<?> typeHint) {
      Class<?> hint = typeHint;
      if (typeHint == null) {
         hint = Number.class;
      }

      try {
         Object valueOf;
         Object toString;
         Object value;
         if (hint == String.class) {
            valueOf = this.TO_STRING.getGetter().invokeExact(sobj);
            if (Bootstrap.isCallable(valueOf)) {
               toString = this.TO_STRING.getInvoker().invokeExact(valueOf, sobj);
               if (JSType.isPrimitive(toString)) {
                  return toString;
               }
            }

            toString = this.VALUE_OF.getGetter().invokeExact(sobj);
            if (Bootstrap.isCallable(toString)) {
               value = this.VALUE_OF.getInvoker().invokeExact(toString, sobj);
               if (JSType.isPrimitive(value)) {
                  return value;
               }
            }

            throw ECMAErrors.typeError(this, "cannot.get.default.string");
         }

         if (hint == Number.class) {
            valueOf = this.VALUE_OF.getGetter().invokeExact(sobj);
            if (Bootstrap.isCallable(valueOf)) {
               toString = this.VALUE_OF.getInvoker().invokeExact(valueOf, sobj);
               if (JSType.isPrimitive(toString)) {
                  return toString;
               }
            }

            toString = this.TO_STRING.getGetter().invokeExact(sobj);
            if (Bootstrap.isCallable(toString)) {
               value = this.TO_STRING.getInvoker().invokeExact(toString, sobj);
               if (JSType.isPrimitive(value)) {
                  return value;
               }
            }

            throw ECMAErrors.typeError(this, "cannot.get.default.number");
         }
      } catch (Error | RuntimeException var7) {
         throw var7;
      } catch (Throwable var8) {
         throw new RuntimeException(var8);
      }

      return ScriptRuntime.UNDEFINED;
   }

   public boolean isError(ScriptObject sobj) {
      ScriptObject errorProto = this.getErrorPrototype();

      for(ScriptObject proto = sobj.getProto(); proto != null; proto = proto.getProto()) {
         if (proto == errorProto) {
            return true;
         }
      }

      return false;
   }

   public ScriptObject newError(String msg) {
      return new NativeError(msg, this);
   }

   public ScriptObject newEvalError(String msg) {
      return new NativeEvalError(msg, this);
   }

   public ScriptObject newRangeError(String msg) {
      return new NativeRangeError(msg, this);
   }

   public ScriptObject newReferenceError(String msg) {
      return new NativeReferenceError(msg, this);
   }

   public ScriptObject newSyntaxError(String msg) {
      return new NativeSyntaxError(msg, this);
   }

   public ScriptObject newTypeError(String msg) {
      return new NativeTypeError(msg, this);
   }

   public ScriptObject newURIError(String msg) {
      return new NativeURIError(msg, this);
   }

   public PropertyDescriptor newGenericDescriptor(boolean configurable, boolean enumerable) {
      return new GenericPropertyDescriptor(configurable, enumerable, this);
   }

   public PropertyDescriptor newDataDescriptor(Object value, boolean configurable, boolean enumerable, boolean writable) {
      return new DataPropertyDescriptor(configurable, enumerable, writable, value, this);
   }

   public PropertyDescriptor newAccessorDescriptor(Object get, Object set, boolean configurable, boolean enumerable) {
      AccessorPropertyDescriptor desc = new AccessorPropertyDescriptor(configurable, enumerable, get == null ? ScriptRuntime.UNDEFINED : get, set == null ? ScriptRuntime.UNDEFINED : set, this);
      if (get == null) {
         desc.delete("get", false);
      }

      if (set == null) {
         desc.delete("set", false);
      }

      return desc;
   }

   private static <T> T getLazilyCreatedValue(Object key, Callable<T> creator, Map<Object, T> map) {
      T obj = map.get(key);
      if (obj != null) {
         return obj;
      } else {
         try {
            T newObj = creator.call();
            T existingObj = map.putIfAbsent(key, newObj);
            return existingObj != null ? existingObj : newObj;
         } catch (Exception var6) {
            throw new RuntimeException(var6);
         }
      }
   }

   public InvokeByName getInvokeByName(Object key, Callable<InvokeByName> creator) {
      return (InvokeByName)getLazilyCreatedValue(key, creator, this.namedInvokers);
   }

   public MethodHandle getDynamicInvoker(Object key, Callable<MethodHandle> creator) {
      return (MethodHandle)getLazilyCreatedValue(key, creator, this.dynamicInvokers);
   }

   public static Object __noSuchProperty__(Object self, Object name) {
      Global global = instance();
      ScriptContext sctxt = global.currentContext();
      String nameStr = name.toString();
      if (sctxt != null) {
         int scope = sctxt.getAttributesScope(nameStr);
         if (scope != -1) {
            return ScriptObjectMirror.unwrap(sctxt.getAttribute(nameStr, scope), global);
         }
      }

      if ("context".equals(nameStr)) {
         return sctxt;
      } else if ("engine".equals(nameStr) && (System.getSecurityManager() == null || global.getClassFilter() == null)) {
         return global.engine;
      } else if (self == ScriptRuntime.UNDEFINED) {
         throw ECMAErrors.referenceError(global, "not.defined", nameStr);
      } else {
         return ScriptRuntime.UNDEFINED;
      }
   }

   public static Object eval(Object self, Object str) {
      return directEval(self, str, instanceFrom(self), ScriptRuntime.UNDEFINED, false);
   }

   public static Object directEval(Object self, Object str, Object callThis, Object location, boolean strict) {
      if (!JSType.isString(str)) {
         return str;
      } else {
         Global global = instanceFrom(self);
         ScriptObject scope = self instanceof ScriptObject && ((ScriptObject)self).isScope() ? (ScriptObject)self : global;
         return global.getContext().eval((ScriptObject)scope, str.toString(), callThis, location, strict, true);
      }
   }

   public static Object print(Object self, Object... objects) {
      return instanceFrom(self).printImpl(false, objects);
   }

   public static Object println(Object self, Object... objects) {
      return instanceFrom(self).printImpl(true, objects);
   }

   public static Object load(Object self, Object source) throws IOException {
      Global global = instanceFrom(self);
      return global.getContext().load(self, source);
   }

   public static Object loadWithNewGlobal(Object self, Object... args) throws IOException {
      Global global = instanceFrom(self);
      int length = args.length;
      boolean hasArgs = 0 < length;
      Object from = hasArgs ? args[0] : ScriptRuntime.UNDEFINED;
      Object[] arguments = hasArgs ? Arrays.copyOfRange(args, 1, length) : args;
      return global.getContext().loadWithNewGlobal(from, arguments);
   }

   public static Object exit(Object self, Object code) {
      System.exit(JSType.toInt32(code));
      return ScriptRuntime.UNDEFINED;
   }

   public ScriptObject getObjectPrototype() {
      return ScriptFunction.getPrototype(this.builtinObject);
   }

   public ScriptObject getFunctionPrototype() {
      return ScriptFunction.getPrototype(this.builtinFunction);
   }

   ScriptObject getArrayPrototype() {
      return ScriptFunction.getPrototype(this.builtinArray);
   }

   ScriptObject getBooleanPrototype() {
      return ScriptFunction.getPrototype(this.builtinBoolean);
   }

   ScriptObject getNumberPrototype() {
      return ScriptFunction.getPrototype(this.builtinNumber);
   }

   ScriptObject getDatePrototype() {
      return ScriptFunction.getPrototype(this.getBuiltinDate());
   }

   ScriptObject getRegExpPrototype() {
      return ScriptFunction.getPrototype(this.getBuiltinRegExp());
   }

   ScriptObject getStringPrototype() {
      return ScriptFunction.getPrototype(this.builtinString);
   }

   ScriptObject getErrorPrototype() {
      return ScriptFunction.getPrototype(this.builtinError);
   }

   ScriptObject getEvalErrorPrototype() {
      return ScriptFunction.getPrototype(this.getBuiltinEvalError());
   }

   ScriptObject getRangeErrorPrototype() {
      return ScriptFunction.getPrototype(this.getBuiltinRangeError());
   }

   ScriptObject getReferenceErrorPrototype() {
      return ScriptFunction.getPrototype(this.builtinReferenceError);
   }

   ScriptObject getSyntaxErrorPrototype() {
      return ScriptFunction.getPrototype(this.builtinSyntaxError);
   }

   ScriptObject getTypeErrorPrototype() {
      return ScriptFunction.getPrototype(this.builtinTypeError);
   }

   ScriptObject getURIErrorPrototype() {
      return ScriptFunction.getPrototype(this.getBuiltinURIError());
   }

   ScriptObject getJavaImporterPrototype() {
      return ScriptFunction.getPrototype(this.getBuiltinJavaImporter());
   }

   ScriptObject getJSAdapterPrototype() {
      return ScriptFunction.getPrototype(this.getBuiltinJSAdapter());
   }

   private synchronized ScriptFunction getBuiltinArrayBuffer() {
      if (this.builtinArrayBuffer == null) {
         this.builtinArrayBuffer = (ScriptFunction)this.initConstructorAndSwitchPoint("ArrayBuffer", ScriptFunction.class);
      }

      return this.builtinArrayBuffer;
   }

   ScriptObject getArrayBufferPrototype() {
      return ScriptFunction.getPrototype(this.getBuiltinArrayBuffer());
   }

   private synchronized ScriptFunction getBuiltinDataView() {
      if (this.builtinDataView == null) {
         this.builtinDataView = (ScriptFunction)this.initConstructorAndSwitchPoint("DataView", ScriptFunction.class);
      }

      return this.builtinDataView;
   }

   ScriptObject getDataViewPrototype() {
      return ScriptFunction.getPrototype(this.getBuiltinDataView());
   }

   private synchronized ScriptFunction getBuiltinInt8Array() {
      if (this.builtinInt8Array == null) {
         this.builtinInt8Array = (ScriptFunction)this.initConstructorAndSwitchPoint("Int8Array", ScriptFunction.class);
      }

      return this.builtinInt8Array;
   }

   ScriptObject getInt8ArrayPrototype() {
      return ScriptFunction.getPrototype(this.getBuiltinInt8Array());
   }

   private synchronized ScriptFunction getBuiltinUint8Array() {
      if (this.builtinUint8Array == null) {
         this.builtinUint8Array = (ScriptFunction)this.initConstructorAndSwitchPoint("Uint8Array", ScriptFunction.class);
      }

      return this.builtinUint8Array;
   }

   ScriptObject getUint8ArrayPrototype() {
      return ScriptFunction.getPrototype(this.getBuiltinUint8Array());
   }

   private synchronized ScriptFunction getBuiltinUint8ClampedArray() {
      if (this.builtinUint8ClampedArray == null) {
         this.builtinUint8ClampedArray = (ScriptFunction)this.initConstructorAndSwitchPoint("Uint8ClampedArray", ScriptFunction.class);
      }

      return this.builtinUint8ClampedArray;
   }

   ScriptObject getUint8ClampedArrayPrototype() {
      return ScriptFunction.getPrototype(this.getBuiltinUint8ClampedArray());
   }

   private synchronized ScriptFunction getBuiltinInt16Array() {
      if (this.builtinInt16Array == null) {
         this.builtinInt16Array = (ScriptFunction)this.initConstructorAndSwitchPoint("Int16Array", ScriptFunction.class);
      }

      return this.builtinInt16Array;
   }

   ScriptObject getInt16ArrayPrototype() {
      return ScriptFunction.getPrototype(this.getBuiltinInt16Array());
   }

   private synchronized ScriptFunction getBuiltinUint16Array() {
      if (this.builtinUint16Array == null) {
         this.builtinUint16Array = (ScriptFunction)this.initConstructorAndSwitchPoint("Uint16Array", ScriptFunction.class);
      }

      return this.builtinUint16Array;
   }

   ScriptObject getUint16ArrayPrototype() {
      return ScriptFunction.getPrototype(this.getBuiltinUint16Array());
   }

   private synchronized ScriptFunction getBuiltinInt32Array() {
      if (this.builtinInt32Array == null) {
         this.builtinInt32Array = (ScriptFunction)this.initConstructorAndSwitchPoint("Int32Array", ScriptFunction.class);
      }

      return this.builtinInt32Array;
   }

   ScriptObject getInt32ArrayPrototype() {
      return ScriptFunction.getPrototype(this.getBuiltinInt32Array());
   }

   private synchronized ScriptFunction getBuiltinUint32Array() {
      if (this.builtinUint32Array == null) {
         this.builtinUint32Array = (ScriptFunction)this.initConstructorAndSwitchPoint("Uint32Array", ScriptFunction.class);
      }

      return this.builtinUint32Array;
   }

   ScriptObject getUint32ArrayPrototype() {
      return ScriptFunction.getPrototype(this.getBuiltinUint32Array());
   }

   private synchronized ScriptFunction getBuiltinFloat32Array() {
      if (this.builtinFloat32Array == null) {
         this.builtinFloat32Array = (ScriptFunction)this.initConstructorAndSwitchPoint("Float32Array", ScriptFunction.class);
      }

      return this.builtinFloat32Array;
   }

   ScriptObject getFloat32ArrayPrototype() {
      return ScriptFunction.getPrototype(this.getBuiltinFloat32Array());
   }

   private synchronized ScriptFunction getBuiltinFloat64Array() {
      if (this.builtinFloat64Array == null) {
         this.builtinFloat64Array = (ScriptFunction)this.initConstructorAndSwitchPoint("Float64Array", ScriptFunction.class);
      }

      return this.builtinFloat64Array;
   }

   ScriptObject getFloat64ArrayPrototype() {
      return ScriptFunction.getPrototype(this.getBuiltinFloat64Array());
   }

   public ScriptFunction getTypeErrorThrower() {
      return this.typeErrorThrower;
   }

   private synchronized ScriptFunction getBuiltinDate() {
      if (this.builtinDate == null) {
         this.builtinDate = (ScriptFunction)this.initConstructorAndSwitchPoint("Date", ScriptFunction.class);
         ScriptObject dateProto = ScriptFunction.getPrototype(this.builtinDate);
         this.DEFAULT_DATE = new NativeDate(Double.NaN, dateProto);
      }

      return this.builtinDate;
   }

   private synchronized ScriptFunction getBuiltinEvalError() {
      if (this.builtinEvalError == null) {
         this.builtinEvalError = this.initErrorSubtype("EvalError", this.getErrorPrototype());
      }

      return this.builtinEvalError;
   }

   private ScriptFunction getBuiltinFunction() {
      return this.builtinFunction;
   }

   public static SwitchPoint getBuiltinFunctionApplySwitchPoint() {
      return ScriptFunction.getPrototype(instance().getBuiltinFunction()).getProperty("apply").getBuiltinSwitchPoint();
   }

   private static boolean isBuiltinFunctionProperty(String name) {
      Global instance = instance();
      ScriptFunction builtinFunction = instance.getBuiltinFunction();
      if (builtinFunction == null) {
         return false;
      } else {
         boolean isBuiltinFunction = instance.function == builtinFunction;
         return isBuiltinFunction && ScriptFunction.getPrototype(builtinFunction).getProperty(name).isBuiltin();
      }
   }

   public static boolean isBuiltinFunctionPrototypeApply() {
      return isBuiltinFunctionProperty("apply");
   }

   public static boolean isBuiltinFunctionPrototypeCall() {
      return isBuiltinFunctionProperty("call");
   }

   private synchronized ScriptFunction getBuiltinJSAdapter() {
      if (this.builtinJSAdapter == null) {
         this.builtinJSAdapter = (ScriptFunction)this.initConstructorAndSwitchPoint("JSAdapter", ScriptFunction.class);
      }

      return this.builtinJSAdapter;
   }

   private synchronized ScriptObject getBuiltinJSON() {
      if (this.builtinJSON == null) {
         this.builtinJSON = this.initConstructorAndSwitchPoint("JSON", ScriptObject.class);
      }

      return this.builtinJSON;
   }

   private synchronized ScriptFunction getBuiltinJavaImporter() {
      if (this.getContext().getEnv()._no_java) {
         throw new IllegalStateException();
      } else {
         if (this.builtinJavaImporter == null) {
            this.builtinJavaImporter = (ScriptFunction)this.initConstructor("JavaImporter", ScriptFunction.class);
         }

         return this.builtinJavaImporter;
      }
   }

   private synchronized ScriptObject getBuiltinJavaApi() {
      if (this.getContext().getEnv()._no_java) {
         throw new IllegalStateException();
      } else {
         if (this.builtinJavaApi == null) {
            this.builtinJavaApi = this.initConstructor("Java", ScriptObject.class);
         }

         return this.builtinJavaApi;
      }
   }

   private synchronized ScriptFunction getBuiltinRangeError() {
      if (this.builtinRangeError == null) {
         this.builtinRangeError = this.initErrorSubtype("RangeError", this.getErrorPrototype());
      }

      return this.builtinRangeError;
   }

   private synchronized ScriptFunction getBuiltinRegExp() {
      if (this.builtinRegExp == null) {
         this.builtinRegExp = (ScriptFunction)this.initConstructorAndSwitchPoint("RegExp", ScriptFunction.class);
         ScriptObject regExpProto = ScriptFunction.getPrototype(this.builtinRegExp);
         this.DEFAULT_REGEXP = new NativeRegExp("(?:)", "", this, regExpProto);
         regExpProto.addBoundProperties(this.DEFAULT_REGEXP);
      }

      return this.builtinRegExp;
   }

   private synchronized ScriptFunction getBuiltinURIError() {
      if (this.builtinURIError == null) {
         this.builtinURIError = this.initErrorSubtype("URIError", this.getErrorPrototype());
      }

      return this.builtinURIError;
   }

   public String getClassName() {
      return "global";
   }

   public static Object regExpCopy(Object regexp) {
      return new NativeRegExp((NativeRegExp)regexp);
   }

   public static NativeRegExp toRegExp(Object obj) {
      return obj instanceof NativeRegExp ? (NativeRegExp)obj : new NativeRegExp(JSType.toString(obj));
   }

   public static Object toObject(Object obj) {
      if (obj != null && obj != ScriptRuntime.UNDEFINED) {
         return obj instanceof ScriptObject ? obj : instance().wrapAsObject(obj);
      } else {
         throw ECMAErrors.typeError("not.an.object", ScriptRuntime.safeToString(obj));
      }
   }

   public static NativeArray allocate(Object[] initial) {
      ArrayData arrayData = ArrayData.allocate(initial);

      for(int index = 0; index < initial.length; ++index) {
         Object value = initial[index];
         if (value == ScriptRuntime.EMPTY) {
            arrayData = arrayData.delete(index);
         }
      }

      return new NativeArray(arrayData);
   }

   public static NativeArray allocate(double[] initial) {
      return new NativeArray(ArrayData.allocate(initial));
   }

   public static NativeArray allocate(int[] initial) {
      return new NativeArray(ArrayData.allocate(initial));
   }

   public static ScriptObject allocateArguments(Object[] arguments, Object callee, int numParams) {
      return NativeArguments.allocate(arguments, (ScriptFunction)callee, numParams);
   }

   public static boolean isEval(Object fn) {
      return fn == instance().builtinEval;
   }

   public static Object replaceLocationPropertyPlaceholder(Object placeholder, Object locationProperty) {
      return isLocationPropertyPlaceholder(placeholder) ? locationProperty : placeholder;
   }

   public static boolean isLocationPropertyPlaceholder(Object placeholder) {
      return placeholder == LOCATION_PLACEHOLDER;
   }

   public static Object newRegExp(String expression, String options) {
      return options == null ? new NativeRegExp(expression) : new NativeRegExp(expression, options);
   }

   public static ScriptObject objectPrototype() {
      return instance().getObjectPrototype();
   }

   public static ScriptObject newEmptyInstance() {
      return instance().newObject();
   }

   public static ScriptObject checkObject(Object obj) {
      if (!(obj instanceof ScriptObject)) {
         throw ECMAErrors.typeError("not.an.object", ScriptRuntime.safeToString(obj));
      } else {
         return (ScriptObject)obj;
      }
   }

   public static void checkObjectCoercible(Object obj) {
      if (obj == null || obj == ScriptRuntime.UNDEFINED) {
         throw ECMAErrors.typeError("not.an.object", ScriptRuntime.safeToString(obj));
      }
   }

   public final ScriptObject getLexicalScope() {
      assert this.context.getEnv()._es6;

      return this.lexicalScope;
   }

   public void addBoundProperties(ScriptObject source, Property[] properties) {
      PropertyMap ownMap = this.getMap();
      Global.LexicalScope lexScope = null;
      PropertyMap lexicalMap = null;
      boolean hasLexicalDefinitions = false;
      int var9;
      Property property;
      if (this.context.getEnv()._es6) {
         lexScope = (Global.LexicalScope)this.getLexicalScope();
         lexicalMap = lexScope.getMap();
         Property[] var7 = properties;
         int var8 = properties.length;

         for(var9 = 0; var9 < var8; ++var9) {
            Property property = var7[var9];
            if (property.isLexicalBinding()) {
               hasLexicalDefinitions = true;
            }

            property = ownMap.findProperty(property.getKey());
            if (property != null && !property.isConfigurable() && property.isLexicalBinding()) {
               throw ECMAErrors.syntaxError("redeclare.variable", property.getKey());
            }

            Property lexicalProperty = lexicalMap.findProperty(property.getKey());
            if (lexicalProperty != null && !property.isConfigurable()) {
               throw ECMAErrors.syntaxError("redeclare.variable", property.getKey());
            }
         }
      }

      boolean extensible = this.isExtensible();
      Property[] var14 = properties;
      var9 = properties.length;

      for(int var15 = 0; var15 < var9; ++var15) {
         property = var14[var15];
         if (property.isLexicalBinding()) {
            assert lexScope != null;

            lexicalMap = lexScope.addBoundProperty(lexicalMap, source, property, true);
            if (ownMap.findProperty(property.getKey()) != null) {
               this.invalidateGlobalConstant(property.getKey());
            }
         } else {
            ownMap = this.addBoundProperty(ownMap, source, property, extensible);
         }
      }

      this.setMap(ownMap);
      if (hasLexicalDefinitions) {
         assert lexScope != null;

         lexScope.setMap(lexicalMap);
         this.invalidateLexicalSwitchPoint();
      }

   }

   public GuardedInvocation findGetMethod(CallSiteDescriptor desc, LinkRequest request, String operator) {
      String name = desc.getNameToken(2);
      boolean isScope = NashornCallSiteDescriptor.isScope(desc);
      if (this.lexicalScope != null && isScope && !NashornCallSiteDescriptor.isApplyToCall(desc) && this.lexicalScope.hasOwnProperty(name)) {
         return this.lexicalScope.findGetMethod(desc, request, operator);
      } else {
         GuardedInvocation invocation = super.findGetMethod(desc, request, operator);
         return !isScope || !this.context.getEnv()._es6 || invocation.getSwitchPoints() != null && this.hasOwnProperty(name) ? invocation : invocation.addSwitchPoint(this.getLexicalScopeSwitchPoint());
      }
   }

   protected FindProperty findProperty(String key, boolean deep, ScriptObject start) {
      if (this.lexicalScope != null && start != this && start.isScope()) {
         FindProperty find = this.lexicalScope.findProperty(key, false);
         if (find != null) {
            return find;
         }
      }

      return super.findProperty(key, deep, start);
   }

   public GuardedInvocation findSetMethod(CallSiteDescriptor desc, LinkRequest request) {
      boolean isScope = NashornCallSiteDescriptor.isScope(desc);
      if (this.lexicalScope != null && isScope) {
         String name = desc.getNameToken(2);
         if (this.lexicalScope.hasOwnProperty(name)) {
            return this.lexicalScope.findSetMethod(desc, request);
         }
      }

      GuardedInvocation invocation = super.findSetMethod(desc, request);
      return isScope && this.context.getEnv()._es6 ? invocation.addSwitchPoint(this.getLexicalScopeSwitchPoint()) : invocation;
   }

   public void addShellBuiltins() {
      Object value = ScriptFunction.createBuiltin("input", ShellFunctions.INPUT);
      this.addOwnProperty("input", 2, value);
      value = ScriptFunction.createBuiltin("evalinput", ShellFunctions.EVALINPUT);
      this.addOwnProperty("evalinput", 2, value);
   }

   private synchronized SwitchPoint getLexicalScopeSwitchPoint() {
      SwitchPoint switchPoint = this.lexicalScopeSwitchPoint;
      if (switchPoint == null || switchPoint.hasBeenInvalidated()) {
         switchPoint = this.lexicalScopeSwitchPoint = new SwitchPoint();
      }

      return switchPoint;
   }

   private synchronized void invalidateLexicalSwitchPoint() {
      if (this.lexicalScopeSwitchPoint != null) {
         this.context.getLogger(GlobalConstants.class).info("Invalidating non-constant globals on lexical scope update");
         SwitchPoint.invalidateAll(new SwitchPoint[]{this.lexicalScopeSwitchPoint});
      }

   }

   private static Object lexicalScopeFilter(Object self) {
      return self instanceof Global ? ((Global)self).getLexicalScope() : self;
   }

   private <T extends ScriptObject> T initConstructorAndSwitchPoint(String name, Class<T> clazz) {
      T func = this.initConstructor(name, clazz);
      this.tagBuiltinProperties(name, func);
      return func;
   }

   private void init(ScriptEngine eng) {
      assert Context.getGlobal() == this : "this global is not set as current";

      ScriptEnvironment env = this.getContext().getEnv();
      this.initFunctionAndObject();
      this.setInitialProto(this.getObjectPrototype());
      this.eval = this.builtinEval = ScriptFunction.createBuiltin("eval", EVAL);
      this.parseInt = ScriptFunction.createBuiltin("parseInt", GlobalFunctions.PARSEINT, new Specialization[]{new Specialization(GlobalFunctions.PARSEINT_Z), new Specialization(GlobalFunctions.PARSEINT_I), new Specialization(GlobalFunctions.PARSEINT_OI), new Specialization(GlobalFunctions.PARSEINT_O)});
      this.parseFloat = ScriptFunction.createBuiltin("parseFloat", GlobalFunctions.PARSEFLOAT);
      this.isNaN = ScriptFunction.createBuiltin("isNaN", GlobalFunctions.IS_NAN, new Specialization[]{new Specialization(GlobalFunctions.IS_NAN_I), new Specialization(GlobalFunctions.IS_NAN_J), new Specialization(GlobalFunctions.IS_NAN_D)});
      this.parseFloat = ScriptFunction.createBuiltin("parseFloat", GlobalFunctions.PARSEFLOAT);
      this.isNaN = ScriptFunction.createBuiltin("isNaN", GlobalFunctions.IS_NAN);
      this.isFinite = ScriptFunction.createBuiltin("isFinite", GlobalFunctions.IS_FINITE);
      this.encodeURI = ScriptFunction.createBuiltin("encodeURI", GlobalFunctions.ENCODE_URI);
      this.encodeURIComponent = ScriptFunction.createBuiltin("encodeURIComponent", GlobalFunctions.ENCODE_URICOMPONENT);
      this.decodeURI = ScriptFunction.createBuiltin("decodeURI", GlobalFunctions.DECODE_URI);
      this.decodeURIComponent = ScriptFunction.createBuiltin("decodeURIComponent", GlobalFunctions.DECODE_URICOMPONENT);
      this.escape = ScriptFunction.createBuiltin("escape", GlobalFunctions.ESCAPE);
      this.unescape = ScriptFunction.createBuiltin("unescape", GlobalFunctions.UNESCAPE);
      this.print = ScriptFunction.createBuiltin("print", env._print_no_newline ? PRINT : PRINTLN);
      this.load = ScriptFunction.createBuiltin("load", LOAD);
      this.loadWithNewGlobal = ScriptFunction.createBuiltin("loadWithNewGlobal", LOAD_WITH_NEW_GLOBAL);
      this.exit = ScriptFunction.createBuiltin("exit", EXIT);
      this.quit = ScriptFunction.createBuiltin("quit", EXIT);
      this.builtinArray = (ScriptFunction)this.initConstructorAndSwitchPoint("Array", ScriptFunction.class);
      this.builtinBoolean = (ScriptFunction)this.initConstructorAndSwitchPoint("Boolean", ScriptFunction.class);
      this.builtinNumber = (ScriptFunction)this.initConstructorAndSwitchPoint("Number", ScriptFunction.class);
      this.builtinString = (ScriptFunction)this.initConstructorAndSwitchPoint("String", ScriptFunction.class);
      this.builtinMath = this.initConstructorAndSwitchPoint("Math", ScriptObject.class);
      ScriptObject stringPrototype = this.getStringPrototype();
      stringPrototype.addOwnProperty("length", 7, 0.0D);
      ScriptObject arrayPrototype = this.getArrayPrototype();
      arrayPrototype.setIsArray();
      this.initErrorObjects();
      if (!env._no_java) {
         this.javaApi = LAZY_SENTINEL;
         this.javaImporter = LAZY_SENTINEL;
         this.initJavaAccess();
      } else {
         this.delete("Java", false);
         this.delete("JavaImporter", false);
         this.delete("Packages", false);
         this.delete("com", false);
         this.delete("edu", false);
         this.delete("java", false);
         this.delete("javafx", false);
         this.delete("javax", false);
         this.delete("org", false);
      }

      if (!env._no_typed_arrays) {
         this.arrayBuffer = LAZY_SENTINEL;
         this.dataView = LAZY_SENTINEL;
         this.int8Array = LAZY_SENTINEL;
         this.uint8Array = LAZY_SENTINEL;
         this.uint8ClampedArray = LAZY_SENTINEL;
         this.int16Array = LAZY_SENTINEL;
         this.uint16Array = LAZY_SENTINEL;
         this.int32Array = LAZY_SENTINEL;
         this.uint32Array = LAZY_SENTINEL;
         this.float32Array = LAZY_SENTINEL;
         this.float64Array = LAZY_SENTINEL;
      }

      if (env._scripting) {
         this.initScripting(env);
      }

      if (Context.DEBUG) {
         SecurityManager sm = System.getSecurityManager();
         boolean debugOkay;
         if (sm != null) {
            try {
               sm.checkPermission(new RuntimePermission("nashorn.debugMode"));
               debugOkay = true;
            } catch (SecurityException var8) {
               debugOkay = false;
            }
         } else {
            debugOkay = true;
         }

         if (debugOkay) {
            this.initDebug();
         }
      }

      this.copyBuiltins();
      this.arguments = this.wrapAsObject(env.getArguments().toArray());
      if (env._scripting) {
         this.addOwnProperty("$ARG", 2, this.arguments);
      }

      if (eng != null) {
         this.addOwnProperty("javax.script.filename", 2, (Object)null);
         ScriptFunction noSuchProp = ScriptFunction.createStrictBuiltin("__noSuchProperty__", NO_SUCH_PROPERTY);
         this.addOwnProperty("__noSuchProperty__", 2, noSuchProp);
      }

   }

   private void initErrorObjects() {
      this.builtinError = (ScriptFunction)this.initConstructor("Error", ScriptFunction.class);
      ScriptObject errorProto = this.getErrorPrototype();
      ScriptFunction getStack = ScriptFunction.createBuiltin("getStack", NativeError.GET_STACK);
      ScriptFunction setStack = ScriptFunction.createBuiltin("setStack", NativeError.SET_STACK);
      errorProto.addOwnProperty("stack", 2, getStack, setStack);
      ScriptFunction getLineNumber = ScriptFunction.createBuiltin("getLineNumber", NativeError.GET_LINENUMBER);
      ScriptFunction setLineNumber = ScriptFunction.createBuiltin("setLineNumber", NativeError.SET_LINENUMBER);
      errorProto.addOwnProperty("lineNumber", 2, getLineNumber, setLineNumber);
      ScriptFunction getColumnNumber = ScriptFunction.createBuiltin("getColumnNumber", NativeError.GET_COLUMNNUMBER);
      ScriptFunction setColumnNumber = ScriptFunction.createBuiltin("setColumnNumber", NativeError.SET_COLUMNNUMBER);
      errorProto.addOwnProperty("columnNumber", 2, getColumnNumber, setColumnNumber);
      ScriptFunction getFileName = ScriptFunction.createBuiltin("getFileName", NativeError.GET_FILENAME);
      ScriptFunction setFileName = ScriptFunction.createBuiltin("setFileName", NativeError.SET_FILENAME);
      errorProto.addOwnProperty("fileName", 2, getFileName, setFileName);
      errorProto.set("name", "Error", 0);
      errorProto.set("message", "", 0);
      this.tagBuiltinProperties("Error", this.builtinError);
      this.builtinReferenceError = this.initErrorSubtype("ReferenceError", errorProto);
      this.builtinSyntaxError = this.initErrorSubtype("SyntaxError", errorProto);
      this.builtinTypeError = this.initErrorSubtype("TypeError", errorProto);
   }

   private ScriptFunction initErrorSubtype(String name, ScriptObject errorProto) {
      ScriptFunction cons = (ScriptFunction)this.initConstructor(name, ScriptFunction.class);
      ScriptObject prototype = ScriptFunction.getPrototype(cons);
      prototype.set("name", name, 0);
      prototype.set("message", "", 0);
      prototype.setInitialProto(errorProto);
      this.tagBuiltinProperties(name, cons);
      return cons;
   }

   private void initJavaAccess() {
      ScriptObject objectProto = this.getObjectPrototype();
      this.builtinPackages = new NativeJavaPackage("", objectProto);
      this.builtinCom = new NativeJavaPackage("com", objectProto);
      this.builtinEdu = new NativeJavaPackage("edu", objectProto);
      this.builtinJava = new NativeJavaPackage("java", objectProto);
      this.builtinJavafx = new NativeJavaPackage("javafx", objectProto);
      this.builtinJavax = new NativeJavaPackage("javax", objectProto);
      this.builtinOrg = new NativeJavaPackage("org", objectProto);
   }

   private void initScripting(ScriptEnvironment scriptEnv) {
      ScriptObject value = ScriptFunction.createBuiltin("readLine", ScriptingFunctions.READLINE);
      this.addOwnProperty("readLine", 2, value);
      value = ScriptFunction.createBuiltin("readFully", ScriptingFunctions.READFULLY);
      this.addOwnProperty("readFully", 2, value);
      String execName = "$EXEC";
      value = ScriptFunction.createBuiltin("$EXEC", ScriptingFunctions.EXEC);
      value.addOwnProperty("throwOnError", 2, false);
      this.addOwnProperty("$EXEC", 2, value);
      ScriptObject value = (ScriptObject)this.get("print");
      this.addOwnProperty("echo", 2, value);
      ScriptObject options = this.newObject();
      copyOptions(options, scriptEnv);
      this.addOwnProperty("$OPTIONS", 2, options);
      if (System.getSecurityManager() == null) {
         ScriptObject env = this.newObject();
         env.putAll(System.getenv(), scriptEnv._strict);
         if (!env.containsKey("PWD")) {
            env.put("PWD", System.getProperty("user.dir"), scriptEnv._strict);
         }

         this.addOwnProperty("$ENV", 2, env);
      } else {
         this.addOwnProperty("$ENV", 2, ScriptRuntime.UNDEFINED);
      }

      this.addOwnProperty("$OUT", 2, ScriptRuntime.UNDEFINED);
      this.addOwnProperty("$ERR", 2, ScriptRuntime.UNDEFINED);
      this.addOwnProperty("$EXIT", 2, ScriptRuntime.UNDEFINED);
   }

   private static void copyOptions(ScriptObject options, ScriptEnvironment scriptEnv) {
      Field[] var2 = scriptEnv.getClass().getFields();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Field f = var2[var4];

         try {
            options.set(f.getName(), f.get(scriptEnv), 0);
         } catch (IllegalAccessException | IllegalArgumentException var7) {
            throw new RuntimeException(var7);
         }
      }

   }

   private void copyBuiltins() {
      this.array = this.builtinArray;
      this._boolean = this.builtinBoolean;
      this.error = this.builtinError;
      this.function = this.builtinFunction;
      this.com = this.builtinCom;
      this.edu = this.builtinEdu;
      this.java = this.builtinJava;
      this.javafx = this.builtinJavafx;
      this.javax = this.builtinJavax;
      this.org = this.builtinOrg;
      this.math = this.builtinMath;
      this.number = this.builtinNumber;
      this.object = this.builtinObject;
      this.packages = this.builtinPackages;
      this.referenceError = this.builtinReferenceError;
      this.string = this.builtinString;
      this.syntaxError = this.builtinSyntaxError;
      this.typeError = this.builtinTypeError;
   }

   private void initDebug() {
      this.addOwnProperty("Debug", 2, this.initConstructor("Debug", ScriptObject.class));
   }

   private Object printImpl(boolean newLine, Object... objects) {
      ScriptContext sc = this.currentContext();
      PrintWriter out = sc != null ? new PrintWriter(sc.getWriter()) : this.getContext().getEnv().getOut();
      StringBuilder sb = new StringBuilder();
      Object[] var6 = objects;
      int var7 = objects.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         Object obj = var6[var8];
         if (sb.length() != 0) {
            sb.append(' ');
         }

         sb.append(JSType.toString(obj));
      }

      if (newLine) {
         out.println(sb.toString());
      } else {
         out.print(sb.toString());
      }

      out.flush();
      return ScriptRuntime.UNDEFINED;
   }

   private <T extends ScriptObject> T initConstructor(String name, Class<T> clazz) {
      try {
         StringBuilder sb = new StringBuilder("jdk.nashorn.internal.objects.");
         sb.append("Native");
         sb.append(name);
         sb.append("$Constructor");
         Class<?> funcClass = Class.forName(sb.toString());
         T res = (ScriptObject)clazz.cast(funcClass.newInstance());
         if (res instanceof ScriptFunction) {
            ScriptFunction func = (ScriptFunction)res;
            func.modifyOwnProperty(func.getProperty("prototype"), 7);
         }

         if (res.getProto() == null) {
            res.setInitialProto(this.getObjectPrototype());
         }

         res.setIsBuiltin();
         return res;
      } catch (InstantiationException | IllegalAccessException | ClassNotFoundException var7) {
         throw new RuntimeException(var7);
      }
   }

   private List<Property> extractBuiltinProperties(String name, ScriptObject func) {
      List<Property> list = new ArrayList();
      list.addAll(Arrays.asList(func.getMap().getProperties()));
      if (func instanceof ScriptFunction) {
         ScriptObject proto = ScriptFunction.getPrototype((ScriptFunction)func);
         if (proto != null) {
            list.addAll(Arrays.asList(proto.getMap().getProperties()));
         }
      }

      Property prop = this.getProperty(name);
      if (prop != null) {
         list.add(prop);
      }

      return list;
   }

   private void tagBuiltinProperties(String name, ScriptObject func) {
      SwitchPoint sp = this.context.getBuiltinSwitchPoint(name);
      if (sp == null) {
         sp = this.context.newBuiltinSwitchPoint(name);
      }

      Iterator var4 = this.extractBuiltinProperties(name, func).iterator();

      while(var4.hasNext()) {
         Property prop = (Property)var4.next();
         prop.setBuiltinSwitchPoint(sp);
      }

   }

   private void initFunctionAndObject() {
      this.builtinFunction = (ScriptFunction)this.initConstructor("Function", ScriptFunction.class);
      ScriptFunction anon = ScriptFunction.createAnonymous();
      anon.addBoundProperties(this.getFunctionPrototype());
      this.builtinFunction.setInitialProto(anon);
      this.builtinFunction.setPrototype(anon);
      anon.set("constructor", this.builtinFunction, 0);
      anon.deleteOwnProperty(anon.getMap().findProperty("prototype"));
      this.typeErrorThrower = ScriptFunction.createBuiltin("TypeErrorThrower", Lookup.TYPE_ERROR_THROWER_GETTER);
      this.typeErrorThrower.preventExtensions();
      this.builtinObject = (ScriptFunction)this.initConstructor("Object", ScriptFunction.class);
      ScriptObject ObjectPrototype = this.getObjectPrototype();
      anon.setInitialProto(ObjectPrototype);
      ScriptFunction getProto = ScriptFunction.createBuiltin("getProto", NativeObject.GET__PROTO__);
      ScriptFunction setProto = ScriptFunction.createBuiltin("setProto", NativeObject.SET__PROTO__);
      ObjectPrototype.addOwnProperty("__proto__", 2, getProto, setProto);
      Property[] properties = this.getFunctionPrototype().getMap().getProperties();
      Property[] var6 = properties;
      int var7 = properties.length;

      int var8;
      Property property;
      String key;
      Object value;
      ScriptFunction func;
      ScriptObject prototype;
      for(var8 = 0; var8 < var7; ++var8) {
         property = var6[var8];
         key = property.getKey();
         value = this.builtinFunction.get(key);
         if (value instanceof ScriptFunction && value != anon) {
            func = (ScriptFunction)value;
            func.setInitialProto(this.getFunctionPrototype());
            prototype = ScriptFunction.getPrototype(func);
            if (prototype != null) {
               prototype.setInitialProto(ObjectPrototype);
            }
         }
      }

      var6 = this.builtinObject.getMap().getProperties();
      var7 = var6.length;

      for(var8 = 0; var8 < var7; ++var8) {
         property = var6[var8];
         key = property.getKey();
         value = this.builtinObject.get(key);
         if (value instanceof ScriptFunction) {
            func = (ScriptFunction)value;
            prototype = ScriptFunction.getPrototype(func);
            if (prototype != null) {
               prototype.setInitialProto(ObjectPrototype);
            }
         }
      }

      properties = this.getObjectPrototype().getMap().getProperties();
      var6 = properties;
      var7 = properties.length;

      for(var8 = 0; var8 < var7; ++var8) {
         property = var6[var8];
         key = property.getKey();
         if (!key.equals("constructor")) {
            value = ObjectPrototype.get(key);
            if (value instanceof ScriptFunction) {
               func = (ScriptFunction)value;
               prototype = ScriptFunction.getPrototype(func);
               if (prototype != null) {
                  prototype.setInitialProto(ObjectPrototype);
               }
            }
         }
      }

      this.tagBuiltinProperties("Object", this.builtinObject);
      this.tagBuiltinProperties("Function", this.builtinFunction);
      this.tagBuiltinProperties("Function", anon);
   }

   private static MethodHandle findOwnMH_S(String name, Class<?> rtype, Class<?>... types) {
      return Lookup.MH.findStatic(MethodHandles.lookup(), Global.class, name, Lookup.MH.type(rtype, types));
   }

   RegExpResult getLastRegExpResult() {
      return this.lastRegExpResult;
   }

   void setLastRegExpResult(RegExpResult regExpResult) {
      this.lastRegExpResult = regExpResult;
   }

   protected boolean isGlobal() {
      return true;
   }

   static {
      undefined = ScriptRuntime.UNDEFINED;
      __FILE__ = LOCATION_PLACEHOLDER;
      __DIR__ = LOCATION_PLACEHOLDER;
      __LINE__ = LOCATION_PLACEHOLDER;
      EVAL = findOwnMH_S("eval", Object.class, Object.class, Object.class);
      NO_SUCH_PROPERTY = findOwnMH_S("__noSuchProperty__", Object.class, Object.class, Object.class);
      PRINT = findOwnMH_S("print", Object.class, Object.class, Object[].class);
      PRINTLN = findOwnMH_S("println", Object.class, Object.class, Object[].class);
      LOAD = findOwnMH_S("load", Object.class, Object.class, Object.class);
      LOAD_WITH_NEW_GLOBAL = findOwnMH_S("loadWithNewGlobal", Object.class, Object.class, Object[].class);
      EXIT = findOwnMH_S("exit", Object.class, Object.class, Object.class);
      LEXICAL_SCOPE_FILTER = findOwnMH_S("lexicalScopeFilter", Object.class, Object.class);
      $clinit$();
   }

   public static void $clinit$() {
      ArrayList var10000 = new ArrayList(61);
      var10000.add(AccessorProperty.create("arguments", 6, "G$arguments", "S$arguments"));
      var10000.add(AccessorProperty.create("parseInt", 2, "G$parseInt", "S$parseInt"));
      var10000.add(AccessorProperty.create("parseFloat", 2, "G$parseFloat", "S$parseFloat"));
      var10000.add(AccessorProperty.create("isNaN", 2, "G$isNaN", "S$isNaN"));
      var10000.add(AccessorProperty.create("isFinite", 2, "G$isFinite", "S$isFinite"));
      var10000.add(AccessorProperty.create("encodeURI", 2, "G$encodeURI", "S$encodeURI"));
      var10000.add(AccessorProperty.create("encodeURIComponent", 2, "G$encodeURIComponent", "S$encodeURIComponent"));
      var10000.add(AccessorProperty.create("decodeURI", 2, "G$decodeURI", "S$decodeURI"));
      var10000.add(AccessorProperty.create("decodeURIComponent", 2, "G$decodeURIComponent", "S$decodeURIComponent"));
      var10000.add(AccessorProperty.create("escape", 2, "G$escape", "S$escape"));
      var10000.add(AccessorProperty.create("unescape", 2, "G$unescape", "S$unescape"));
      var10000.add(AccessorProperty.create("print", 2, "G$print", "S$print"));
      var10000.add(AccessorProperty.create("load", 2, "G$load", "S$load"));
      var10000.add(AccessorProperty.create("loadWithNewGlobal", 2, "G$loadWithNewGlobal", "S$loadWithNewGlobal"));
      var10000.add(AccessorProperty.create("exit", 2, "G$exit", "S$exit"));
      var10000.add(AccessorProperty.create("quit", 2, "G$quit", "S$quit"));
      var10000.add(AccessorProperty.create("NaN", 7, "G$NaN", (MethodHandle)null));
      var10000.add(AccessorProperty.create("Infinity", 7, "G$Infinity", (MethodHandle)null));
      var10000.add(AccessorProperty.create("undefined", 7, "G$undefined", (MethodHandle)null));
      var10000.add(AccessorProperty.create("eval", 2, "G$eval", "S$eval"));
      var10000.add(AccessorProperty.create("Object", 2, "G$object", "S$object"));
      var10000.add(AccessorProperty.create("Function", 2, "G$function", "S$function"));
      var10000.add(AccessorProperty.create("Array", 2, "G$array", "S$array"));
      var10000.add(AccessorProperty.create("String", 2, "G$string", "S$string"));
      var10000.add(AccessorProperty.create("Boolean", 2, "G$_boolean", "S$_boolean"));
      var10000.add(AccessorProperty.create("Number", 2, "G$number", "S$number"));
      var10000.add(AccessorProperty.create("Math", 2, "G$math", "S$math"));
      var10000.add(AccessorProperty.create("Error", 2, "G$error", "S$error"));
      var10000.add(AccessorProperty.create("ReferenceError", 2, "G$referenceError", "S$referenceError"));
      var10000.add(AccessorProperty.create("SyntaxError", 2, "G$syntaxError", "S$syntaxError"));
      var10000.add(AccessorProperty.create("TypeError", 2, "G$typeError", "S$typeError"));
      var10000.add(AccessorProperty.create("Packages", 2, "G$packages", "S$packages"));
      var10000.add(AccessorProperty.create("com", 2, "G$com", "S$com"));
      var10000.add(AccessorProperty.create("edu", 2, "G$edu", "S$edu"));
      var10000.add(AccessorProperty.create("java", 2, "G$java", "S$java"));
      var10000.add(AccessorProperty.create("javafx", 2, "G$javafx", "S$javafx"));
      var10000.add(AccessorProperty.create("javax", 2, "G$javax", "S$javax"));
      var10000.add(AccessorProperty.create("org", 2, "G$org", "S$org"));
      var10000.add(AccessorProperty.create("__FILE__", 7, "G$__FILE__", (MethodHandle)null));
      var10000.add(AccessorProperty.create("__DIR__", 7, "G$__DIR__", (MethodHandle)null));
      var10000.add(AccessorProperty.create("__LINE__", 7, "G$__LINE__", (MethodHandle)null));
      var10000.add(AccessorProperty.create("Date", 2, "getDate", "setDate"));
      var10000.add(AccessorProperty.create("RegExp", 2, "getRegExp", "setRegExp"));
      var10000.add(AccessorProperty.create("JSON", 2, "getJSON", "setJSON"));
      var10000.add(AccessorProperty.create("JSAdapter", 2, "getJSAdapter", "setJSAdapter"));
      var10000.add(AccessorProperty.create("EvalError", 2, "getEvalError", "setEvalError"));
      var10000.add(AccessorProperty.create("RangeError", 2, "getRangeError", "setRangeError"));
      var10000.add(AccessorProperty.create("URIError", 2, "getURIError", "setURIError"));
      var10000.add(AccessorProperty.create("ArrayBuffer", 2, "getArrayBuffer", "setArrayBuffer"));
      var10000.add(AccessorProperty.create("DataView", 2, "getDataView", "setDataView"));
      var10000.add(AccessorProperty.create("Int8Array", 2, "getInt8Array", "setInt8Array"));
      var10000.add(AccessorProperty.create("Uint8Array", 2, "getUint8Array", "setUint8Array"));
      var10000.add(AccessorProperty.create("Uint8ClampedArray", 2, "getUint8ClampedArray", "setUint8ClampedArray"));
      var10000.add(AccessorProperty.create("Int16Array", 2, "getInt16Array", "setInt16Array"));
      var10000.add(AccessorProperty.create("Uint16Array", 2, "getUint16Array", "setUint16Array"));
      var10000.add(AccessorProperty.create("Int32Array", 2, "getInt32Array", "setInt32Array"));
      var10000.add(AccessorProperty.create("Uint32Array", 2, "getUint32Array", "setUint32Array"));
      var10000.add(AccessorProperty.create("Float32Array", 2, "getFloat32Array", "setFloat32Array"));
      var10000.add(AccessorProperty.create("Float64Array", 2, "getFloat64Array", "setFloat64Array"));
      var10000.add(AccessorProperty.create("JavaImporter", 2, "getJavaImporter", "setJavaImporter"));
      var10000.add(AccessorProperty.create("Java", 2, "getJavaApi", "setJavaApi"));
      $nasgenmap$ = PropertyMap.newMap((Collection)var10000);
   }

   public Object G$arguments() {
      return this.arguments;
   }

   public void S$arguments(Object var1) {
      this.arguments = var1;
   }

   public Object G$parseInt() {
      return this.parseInt;
   }

   public void S$parseInt(Object var1) {
      this.parseInt = var1;
   }

   public Object G$parseFloat() {
      return this.parseFloat;
   }

   public void S$parseFloat(Object var1) {
      this.parseFloat = var1;
   }

   public Object G$isNaN() {
      return this.isNaN;
   }

   public void S$isNaN(Object var1) {
      this.isNaN = var1;
   }

   public Object G$isFinite() {
      return this.isFinite;
   }

   public void S$isFinite(Object var1) {
      this.isFinite = var1;
   }

   public Object G$encodeURI() {
      return this.encodeURI;
   }

   public void S$encodeURI(Object var1) {
      this.encodeURI = var1;
   }

   public Object G$encodeURIComponent() {
      return this.encodeURIComponent;
   }

   public void S$encodeURIComponent(Object var1) {
      this.encodeURIComponent = var1;
   }

   public Object G$decodeURI() {
      return this.decodeURI;
   }

   public void S$decodeURI(Object var1) {
      this.decodeURI = var1;
   }

   public Object G$decodeURIComponent() {
      return this.decodeURIComponent;
   }

   public void S$decodeURIComponent(Object var1) {
      this.decodeURIComponent = var1;
   }

   public Object G$escape() {
      return this.escape;
   }

   public void S$escape(Object var1) {
      this.escape = var1;
   }

   public Object G$unescape() {
      return this.unescape;
   }

   public void S$unescape(Object var1) {
      this.unescape = var1;
   }

   public Object G$print() {
      return this.print;
   }

   public void S$print(Object var1) {
      this.print = var1;
   }

   public Object G$load() {
      return this.load;
   }

   public void S$load(Object var1) {
      this.load = var1;
   }

   public Object G$loadWithNewGlobal() {
      return this.loadWithNewGlobal;
   }

   public void S$loadWithNewGlobal(Object var1) {
      this.loadWithNewGlobal = var1;
   }

   public Object G$exit() {
      return this.exit;
   }

   public void S$exit(Object var1) {
      this.exit = var1;
   }

   public Object G$quit() {
      return this.quit;
   }

   public void S$quit(Object var1) {
      this.quit = var1;
   }

   public double G$NaN() {
      return NaN;
   }

   public double G$Infinity() {
      return Infinity;
   }

   public Object G$undefined() {
      return undefined;
   }

   public Object G$eval() {
      return this.eval;
   }

   public void S$eval(Object var1) {
      this.eval = var1;
   }

   public Object G$object() {
      return this.object;
   }

   public void S$object(Object var1) {
      this.object = var1;
   }

   public Object G$function() {
      return this.function;
   }

   public void S$function(Object var1) {
      this.function = var1;
   }

   public Object G$array() {
      return this.array;
   }

   public void S$array(Object var1) {
      this.array = var1;
   }

   public Object G$string() {
      return this.string;
   }

   public void S$string(Object var1) {
      this.string = var1;
   }

   public Object G$_boolean() {
      return this._boolean;
   }

   public void S$_boolean(Object var1) {
      this._boolean = var1;
   }

   public Object G$number() {
      return this.number;
   }

   public void S$number(Object var1) {
      this.number = var1;
   }

   public Object G$math() {
      return this.math;
   }

   public void S$math(Object var1) {
      this.math = var1;
   }

   public Object G$error() {
      return this.error;
   }

   public void S$error(Object var1) {
      this.error = var1;
   }

   public Object G$referenceError() {
      return this.referenceError;
   }

   public void S$referenceError(Object var1) {
      this.referenceError = var1;
   }

   public Object G$syntaxError() {
      return this.syntaxError;
   }

   public void S$syntaxError(Object var1) {
      this.syntaxError = var1;
   }

   public Object G$typeError() {
      return this.typeError;
   }

   public void S$typeError(Object var1) {
      this.typeError = var1;
   }

   public Object G$packages() {
      return this.packages;
   }

   public void S$packages(Object var1) {
      this.packages = var1;
   }

   public Object G$com() {
      return this.com;
   }

   public void S$com(Object var1) {
      this.com = var1;
   }

   public Object G$edu() {
      return this.edu;
   }

   public void S$edu(Object var1) {
      this.edu = var1;
   }

   public Object G$java() {
      return this.java;
   }

   public void S$java(Object var1) {
      this.java = var1;
   }

   public Object G$javafx() {
      return this.javafx;
   }

   public void S$javafx(Object var1) {
      this.javafx = var1;
   }

   public Object G$javax() {
      return this.javax;
   }

   public void S$javax(Object var1) {
      this.javax = var1;
   }

   public Object G$org() {
      return this.org;
   }

   public void S$org(Object var1) {
      this.org = var1;
   }

   public Object G$__FILE__() {
      return __FILE__;
   }

   public Object G$__DIR__() {
      return __DIR__;
   }

   public Object G$__LINE__() {
      return __LINE__;
   }

   private static class LexicalScope extends ScriptObject {
      LexicalScope(Global global) {
         super(global, PropertyMap.newMap());
      }

      protected GuardedInvocation findGetMethod(CallSiteDescriptor desc, LinkRequest request, String operator) {
         return filterInvocation(super.findGetMethod(desc, request, operator));
      }

      protected GuardedInvocation findSetMethod(CallSiteDescriptor desc, LinkRequest request) {
         return filterInvocation(super.findSetMethod(desc, request));
      }

      protected PropertyMap addBoundProperty(PropertyMap propMap, ScriptObject source, Property property, boolean extensible) {
         return super.addBoundProperty(propMap, source, property, extensible);
      }

      private static GuardedInvocation filterInvocation(GuardedInvocation invocation) {
         MethodType type = invocation.getInvocation().type();
         return invocation.asType(type.changeParameterType(0, Object.class)).filterArguments(0, Global.LEXICAL_SCOPE_FILTER);
      }
   }
}
