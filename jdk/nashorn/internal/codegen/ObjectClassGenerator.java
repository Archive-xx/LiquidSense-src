package jdk.nashorn.internal.codegen;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.AllocationStrategy;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.FunctionScope;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptEnvironment;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.Undefined;
import jdk.nashorn.internal.runtime.UnwarrantedOptimismException;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import jdk.nashorn.internal.runtime.logging.Loggable;
import jdk.nashorn.internal.runtime.logging.Logger;

@Logger(
   name = "fields"
)
public final class ObjectClassGenerator implements Loggable {
   private static final MethodHandle IS_TYPE_GUARD;
   private static final String SCOPE_MARKER = "P";
   static final int FIELD_PADDING = 4;
   private final DebugLogger log;
   private static final Type[] FIELD_TYPES_OBJECT;
   private static final Type[] FIELD_TYPES_DUAL;
   public static final Type PRIMITIVE_FIELD_TYPE;
   private static final MethodHandle GET_DIFFERENT;
   private static final MethodHandle GET_DIFFERENT_UNDEFINED;
   private static boolean initialized;
   private final Context context;
   private final boolean dualFields;
   public static final MethodHandle PACK_DOUBLE;
   public static final MethodHandle UNPACK_DOUBLE;

   public ObjectClassGenerator(Context context, boolean dualFields) {
      this.context = context;
      this.dualFields = dualFields;

      assert context != null;

      this.log = this.initLogger(context);
      if (!initialized) {
         initialized = true;
         if (!dualFields) {
            this.log.warning("Running with object fields only - this is a deprecated configuration.");
         }
      }

   }

   public DebugLogger getLogger() {
      return this.log;
   }

   public DebugLogger initLogger(Context ctxt) {
      return ctxt.getLogger(this.getClass());
   }

   public static long pack(Number n) {
      if (n instanceof Integer) {
         return (long)n.intValue();
      } else if (n instanceof Long) {
         return n.longValue();
      } else if (n instanceof Double) {
         return Double.doubleToRawLongBits(n.doubleValue());
      } else {
         throw new AssertionError("cannot pack" + n);
      }
   }

   private static String getPrefixName(boolean dualFields) {
      return dualFields ? CompilerConstants.JS_OBJECT_DUAL_FIELD_PREFIX.symbolName() : CompilerConstants.JS_OBJECT_SINGLE_FIELD_PREFIX.symbolName();
   }

   private static String getPrefixName(String className) {
      if (className.startsWith(CompilerConstants.JS_OBJECT_DUAL_FIELD_PREFIX.symbolName())) {
         return getPrefixName(true);
      } else if (className.startsWith(CompilerConstants.JS_OBJECT_SINGLE_FIELD_PREFIX.symbolName())) {
         return getPrefixName(false);
      } else {
         throw new AssertionError("Not a structure class: " + className);
      }
   }

   public static String getClassName(int fieldCount, boolean dualFields) {
      String prefix = getPrefixName(dualFields);
      return fieldCount != 0 ? "jdk/nashorn/internal/scripts/" + prefix + fieldCount : "jdk/nashorn/internal/scripts/" + prefix;
   }

   public static String getClassName(int fieldCount, int paramCount, boolean dualFields) {
      return "jdk/nashorn/internal/scripts/" + getPrefixName(dualFields) + fieldCount + "P" + paramCount;
   }

   public static int getFieldCount(Class<?> clazz) {
      String name = clazz.getSimpleName();
      String prefix = getPrefixName(name);
      if (prefix.equals(name)) {
         return 0;
      } else {
         int scopeMarker = name.indexOf("P");
         return Integer.parseInt(scopeMarker == -1 ? name.substring(prefix.length()) : name.substring(prefix.length(), scopeMarker));
      }
   }

   public static String getFieldName(int fieldIndex, Type type) {
      return type.getDescriptor().substring(0, 1) + fieldIndex;
   }

   private void initializeToUndefined(MethodEmitter init, String className, List<String> fieldNames) {
      if (!this.dualFields) {
         if (!fieldNames.isEmpty()) {
            init.load(Type.OBJECT, CompilerConstants.JAVA_THIS.slot());
            init.loadUndefined(Type.OBJECT);

            String fieldName;
            for(Iterator iter = fieldNames.iterator(); iter.hasNext(); init.putField(className, fieldName, Type.OBJECT.getDescriptor())) {
               fieldName = (String)iter.next();
               if (iter.hasNext()) {
                  init.dup2();
               }
            }

         }
      }
   }

   public byte[] generate(String descriptor) {
      String[] counts = descriptor.split("P");
      int fieldCount = Integer.valueOf(counts[0]);
      if (counts.length == 1) {
         return this.generate(fieldCount);
      } else {
         int paramCount = Integer.valueOf(counts[1]);
         return this.generate(fieldCount, paramCount);
      }
   }

   public byte[] generate(int fieldCount) {
      String className = getClassName(fieldCount, this.dualFields);
      String superName = CompilerConstants.className(ScriptObject.class);
      ClassEmitter classEmitter = this.newClassEmitter(className, superName);
      this.addFields(classEmitter, fieldCount);
      MethodEmitter init = newInitMethod(classEmitter);
      init.returnVoid();
      init.end();
      MethodEmitter initWithSpillArrays = newInitWithSpillArraysMethod(classEmitter, ScriptObject.class);
      initWithSpillArrays.returnVoid();
      initWithSpillArrays.end();
      newEmptyInit(className, classEmitter);
      newAllocate(className, classEmitter);
      return this.toByteArray(className, classEmitter);
   }

   public byte[] generate(int fieldCount, int paramCount) {
      String className = getClassName(fieldCount, paramCount, this.dualFields);
      String superName = CompilerConstants.className(FunctionScope.class);
      ClassEmitter classEmitter = this.newClassEmitter(className, superName);
      List<String> initFields = this.addFields(classEmitter, fieldCount);
      MethodEmitter init = newInitScopeMethod(classEmitter);
      this.initializeToUndefined(init, className, initFields);
      init.returnVoid();
      init.end();
      MethodEmitter initWithSpillArrays = newInitWithSpillArraysMethod(classEmitter, FunctionScope.class);
      this.initializeToUndefined(initWithSpillArrays, className, initFields);
      initWithSpillArrays.returnVoid();
      initWithSpillArrays.end();
      MethodEmitter initWithArguments = newInitScopeWithArgumentsMethod(classEmitter);
      this.initializeToUndefined(initWithArguments, className, initFields);
      initWithArguments.returnVoid();
      initWithArguments.end();
      return this.toByteArray(className, classEmitter);
   }

   private List<String> addFields(ClassEmitter classEmitter, int fieldCount) {
      List<String> initFields = new LinkedList();
      Type[] fieldTypes = this.dualFields ? FIELD_TYPES_DUAL : FIELD_TYPES_OBJECT;

      for(int i = 0; i < fieldCount; ++i) {
         Type[] var6 = fieldTypes;
         int var7 = fieldTypes.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            Type type = var6[var8];
            String fieldName = getFieldName(i, type);
            classEmitter.field(fieldName, type.getTypeClass());
            if (type == Type.OBJECT) {
               initFields.add(fieldName);
            }
         }
      }

      return initFields;
   }

   private ClassEmitter newClassEmitter(String className, String superName) {
      ClassEmitter classEmitter = new ClassEmitter(this.context, className, superName, new String[0]);
      classEmitter.begin();
      return classEmitter;
   }

   private static MethodEmitter newInitMethod(ClassEmitter classEmitter) {
      MethodEmitter init = classEmitter.init(PropertyMap.class);
      init.begin();
      init.load(Type.OBJECT, CompilerConstants.JAVA_THIS.slot());
      init.load(Type.OBJECT, CompilerConstants.INIT_MAP.slot());
      init.invoke(CompilerConstants.constructorNoLookup(ScriptObject.class, PropertyMap.class));
      return init;
   }

   private static MethodEmitter newInitWithSpillArraysMethod(ClassEmitter classEmitter, Class<?> superClass) {
      MethodEmitter init = classEmitter.init(PropertyMap.class, long[].class, Object[].class);
      init.begin();
      init.load(Type.OBJECT, CompilerConstants.JAVA_THIS.slot());
      init.load(Type.OBJECT, CompilerConstants.INIT_MAP.slot());
      init.load(Type.LONG_ARRAY, 2);
      init.load(Type.OBJECT_ARRAY, 3);
      init.invoke(CompilerConstants.constructorNoLookup(superClass, PropertyMap.class, long[].class, Object[].class));
      return init;
   }

   private static MethodEmitter newInitScopeMethod(ClassEmitter classEmitter) {
      MethodEmitter init = classEmitter.init(PropertyMap.class, ScriptObject.class);
      init.begin();
      init.load(Type.OBJECT, CompilerConstants.JAVA_THIS.slot());
      init.load(Type.OBJECT, CompilerConstants.INIT_MAP.slot());
      init.load(Type.OBJECT, CompilerConstants.INIT_SCOPE.slot());
      init.invoke(CompilerConstants.constructorNoLookup(FunctionScope.class, PropertyMap.class, ScriptObject.class));
      return init;
   }

   private static MethodEmitter newInitScopeWithArgumentsMethod(ClassEmitter classEmitter) {
      MethodEmitter init = classEmitter.init(PropertyMap.class, ScriptObject.class, ScriptObject.class);
      init.begin();
      init.load(Type.OBJECT, CompilerConstants.JAVA_THIS.slot());
      init.load(Type.OBJECT, CompilerConstants.INIT_MAP.slot());
      init.load(Type.OBJECT, CompilerConstants.INIT_SCOPE.slot());
      init.load(Type.OBJECT, CompilerConstants.INIT_ARGUMENTS.slot());
      init.invoke(CompilerConstants.constructorNoLookup(FunctionScope.class, PropertyMap.class, ScriptObject.class, ScriptObject.class));
      return init;
   }

   private static void newEmptyInit(String className, ClassEmitter classEmitter) {
      MethodEmitter emptyInit = classEmitter.init();
      emptyInit.begin();
      emptyInit.load(Type.OBJECT, CompilerConstants.JAVA_THIS.slot());
      emptyInit.loadNull();
      emptyInit.invoke(CompilerConstants.constructorNoLookup(className, PropertyMap.class));
      emptyInit.returnVoid();
      emptyInit.end();
   }

   private static void newAllocate(String className, ClassEmitter classEmitter) {
      MethodEmitter allocate = classEmitter.method(EnumSet.of(ClassEmitter.Flag.PUBLIC, ClassEmitter.Flag.STATIC), CompilerConstants.ALLOCATE.symbolName(), ScriptObject.class, PropertyMap.class);
      allocate.begin();
      allocate._new(className, Type.typeFor(ScriptObject.class));
      allocate.dup();
      allocate.load(Type.typeFor(PropertyMap.class), 0);
      allocate.invoke(CompilerConstants.constructorNoLookup(className, PropertyMap.class));
      allocate._return();
      allocate.end();
   }

   private byte[] toByteArray(String className, ClassEmitter classEmitter) {
      classEmitter.end();
      byte[] code = classEmitter.toByteArray();
      ScriptEnvironment env = this.context.getEnv();
      DumpBytecode.dumpBytecode(env, this.log, code, className);
      if (env._verify_code) {
         this.context.verify(code);
      }

      return code;
   }

   private static Object getDifferent(Object receiver, Class<?> forType, MethodHandle primitiveGetter, MethodHandle objectGetter, int programPoint) {
      MethodHandle sameTypeGetter = getterForType(forType, primitiveGetter, objectGetter);
      MethodHandle mh = Lookup.MH.asType(sameTypeGetter, sameTypeGetter.type().changeReturnType(Object.class));

      try {
         Object value = mh.invokeExact(receiver);
         throw new UnwarrantedOptimismException(value, programPoint);
      } catch (RuntimeException | Error var8) {
         throw var8;
      } catch (Throwable var9) {
         throw new RuntimeException(var9);
      }
   }

   private static Object getDifferentUndefined(int programPoint) {
      throw new UnwarrantedOptimismException(Undefined.getUndefined(), programPoint);
   }

   private static MethodHandle getterForType(Class<?> forType, MethodHandle primitiveGetter, MethodHandle objectGetter) {
      switch(JSType.getAccessorTypeIndex(forType)) {
      case 0:
         return Lookup.MH.explicitCastArguments(primitiveGetter, primitiveGetter.type().changeReturnType(Integer.TYPE));
      case 1:
         return Lookup.MH.filterReturnValue(primitiveGetter, UNPACK_DOUBLE);
      case 2:
         return objectGetter;
      default:
         throw new AssertionError(forType);
      }
   }

   private static MethodHandle createGetterInner(Class<?> forType, Class<?> type, MethodHandle primitiveGetter, MethodHandle objectGetter, List<MethodHandle> converters, int programPoint) {
      int fti = forType == null ? -1 : JSType.getAccessorTypeIndex(forType);
      int ti = JSType.getAccessorTypeIndex(type);
      boolean isOptimistic = converters == JSType.CONVERT_OBJECT_OPTIMISTIC;
      boolean isPrimitiveStorage = forType != null && forType.isPrimitive();
      MethodHandle getter = primitiveGetter == null ? objectGetter : (isPrimitiveStorage ? primitiveGetter : objectGetter);
      if (forType == null) {
         if (isOptimistic) {
            return ti == 2 ? Lookup.MH.dropArguments((MethodHandle)JSType.GET_UNDEFINED.get(2), 0, (Class[])(Object.class)) : Lookup.MH.asType(Lookup.MH.dropArguments(Lookup.MH.insertArguments(GET_DIFFERENT_UNDEFINED, 0, programPoint), 0, (Class[])(Object.class)), getter.type().changeReturnType(type));
         } else {
            return Lookup.MH.dropArguments((MethodHandle)JSType.GET_UNDEFINED.get(ti), 0, (Class[])(Object.class));
         }
      } else {
         assert primitiveGetter != null || forType == Object.class : forType;

         MethodHandle tgetter;
         if (isOptimistic) {
            if (fti < ti) {
               assert fti != -1;

               tgetter = getterForType(forType, primitiveGetter, objectGetter);
               return Lookup.MH.asType(tgetter, tgetter.type().changeReturnType(type));
            } else if (fti == ti) {
               return getterForType(forType, primitiveGetter, objectGetter);
            } else {
               assert fti > ti;

               return fti == 2 ? Lookup.MH.filterReturnValue(objectGetter, Lookup.MH.insertArguments((MethodHandle)converters.get(ti), 1, programPoint)) : Lookup.MH.asType(Lookup.MH.filterArguments(objectGetter, 0, Lookup.MH.insertArguments(GET_DIFFERENT, 1, forType, primitiveGetter, objectGetter, programPoint)), objectGetter.type().changeReturnType(type));
            }
         } else {
            assert !isOptimistic;

            tgetter = getterForType(forType, primitiveGetter, objectGetter);
            if (fti == 2) {
               return fti != ti ? Lookup.MH.filterReturnValue(tgetter, (MethodHandle)JSType.CONVERT_OBJECT.get(ti)) : tgetter;
            } else {
               assert primitiveGetter != null;

               MethodType tgetterType = tgetter.type();
               switch(fti) {
               case 0:
                  return Lookup.MH.asType(tgetter, tgetterType.changeReturnType(type));
               case 1:
                  switch(ti) {
                  case 0:
                     return Lookup.MH.filterReturnValue(tgetter, JSType.TO_INT32_D.methodHandle);
                  case 1:
                     assert tgetterType.returnType() == Double.TYPE;

                     return tgetter;
                  default:
                     return Lookup.MH.asType(tgetter, tgetterType.changeReturnType(Object.class));
                  }
               default:
                  throw new UnsupportedOperationException(forType + "=>" + type);
               }
            }
         }
      }
   }

   public static MethodHandle createGetter(Class<?> forType, Class<?> type, MethodHandle primitiveGetter, MethodHandle objectGetter, int programPoint) {
      return createGetterInner(forType, type, primitiveGetter, objectGetter, UnwarrantedOptimismException.isValid(programPoint) ? JSType.CONVERT_OBJECT_OPTIMISTIC : JSType.CONVERT_OBJECT, programPoint);
   }

   public static MethodHandle createSetter(Class<?> forType, Class<?> type, MethodHandle primitiveSetter, MethodHandle objectSetter) {
      assert forType != null;

      int fti = JSType.getAccessorTypeIndex(forType);
      int ti = JSType.getAccessorTypeIndex(type);
      if (fti != 2 && primitiveSetter != null) {
         MethodType pmt = primitiveSetter.type();
         switch(fti) {
         case 0:
            switch(ti) {
            case 0:
               return Lookup.MH.asType(primitiveSetter, pmt.changeParameterType(1, Integer.TYPE));
            case 1:
               return Lookup.MH.filterArguments(primitiveSetter, 1, PACK_DOUBLE);
            default:
               return objectSetter;
            }
         case 1:
            if (ti == 2) {
               return objectSetter;
            }

            return Lookup.MH.asType(Lookup.MH.filterArguments(primitiveSetter, 1, PACK_DOUBLE), pmt.changeParameterType(1, type));
         default:
            throw new UnsupportedOperationException(forType + "=>" + type);
         }
      } else {
         return ti == 2 ? objectSetter : Lookup.MH.asType(objectSetter, objectSetter.type().changeParameterType(1, type));
      }
   }

   private static boolean isType(Class<?> boxedForType, Object x) {
      return x != null && x.getClass() == boxedForType;
   }

   private static Class<? extends Number> getBoxedType(Class<?> forType) {
      if (forType == Integer.TYPE) {
         return Integer.class;
      } else if (forType == Long.TYPE) {
         return Long.class;
      } else if (forType == Double.TYPE) {
         return Double.class;
      } else {
         assert false;

         return null;
      }
   }

   public static MethodHandle createGuardBoxedPrimitiveSetter(Class<?> forType, MethodHandle primitiveSetter, MethodHandle objectSetter) {
      Class<? extends Number> boxedForType = getBoxedType(forType);
      return Lookup.MH.guardWithTest(Lookup.MH.insertArguments(Lookup.MH.dropArguments(IS_TYPE_GUARD, 1, (Class[])(Object.class)), 0, boxedForType), Lookup.MH.asType(primitiveSetter, objectSetter.type()), objectSetter);
   }

   static int getPaddedFieldCount(int count) {
      return count / 4 * 4 + 4;
   }

   private static MethodHandle findOwnMH(String name, Class<?> rtype, Class<?>... types) {
      return Lookup.MH.findStatic(MethodHandles.lookup(), ObjectClassGenerator.class, name, Lookup.MH.type(rtype, types));
   }

   static AllocationStrategy createAllocationStrategy(int thisProperties, boolean dualFields) {
      int paddedFieldCount = getPaddedFieldCount(thisProperties);
      return new AllocationStrategy(paddedFieldCount, dualFields);
   }

   static {
      IS_TYPE_GUARD = findOwnMH("isType", Boolean.TYPE, Class.class, Object.class);
      FIELD_TYPES_OBJECT = new Type[]{Type.OBJECT};
      FIELD_TYPES_DUAL = new Type[]{Type.LONG, Type.OBJECT};
      PRIMITIVE_FIELD_TYPE = Type.LONG;
      GET_DIFFERENT = findOwnMH("getDifferent", Object.class, Object.class, Class.class, MethodHandle.class, MethodHandle.class, Integer.TYPE);
      GET_DIFFERENT_UNDEFINED = findOwnMH("getDifferentUndefined", Object.class, Integer.TYPE);
      initialized = false;
      PACK_DOUBLE = Lookup.MH.explicitCastArguments(Lookup.MH.findStatic(MethodHandles.publicLookup(), Double.class, "doubleToRawLongBits", Lookup.MH.type(Long.TYPE, Double.TYPE)), Lookup.MH.type(Long.TYPE, Double.TYPE));
      UNPACK_DOUBLE = Lookup.MH.findStatic(MethodHandles.publicLookup(), Double.class, "longBitsToDouble", Lookup.MH.type(Double.TYPE, Long.TYPE));
   }
}
