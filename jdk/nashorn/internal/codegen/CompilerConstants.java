package jdk.nashorn.internal.codegen;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles.Lookup;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.Source;

public enum CompilerConstants {
   __FILE__,
   __DIR__,
   __LINE__,
   INIT("<init>"),
   CLINIT("<clinit>"),
   EVAL("eval"),
   SOURCE("source", Source.class),
   CONSTANTS("constants", Object[].class),
   STRICT_MODE("strictMode", Boolean.TYPE),
   DEFAULT_SCRIPT_NAME("Script"),
   ANON_FUNCTION_PREFIX("L:"),
   NESTED_FUNCTION_SEPARATOR("#"),
   ID_FUNCTION_SEPARATOR("-"),
   PROGRAM(":program"),
   CREATE_PROGRAM_FUNCTION(":createProgramFunction"),
   THIS("this", Object.class),
   THIS_DEBUGGER(":this"),
   SCOPE(":scope", ScriptObject.class, 2),
   RETURN(":return"),
   CALLEE(":callee", ScriptFunction.class),
   VARARGS(":varargs", Object[].class),
   ARGUMENTS_VAR("arguments", Object.class),
   ARGUMENTS(":arguments", ScriptObject.class),
   EXPLODED_ARGUMENT_PREFIX(":xarg"),
   ITERATOR_PREFIX(":i", Iterator.class),
   SWITCH_TAG_PREFIX(":s"),
   EXCEPTION_PREFIX(":e", Throwable.class),
   QUICK_PREFIX(":q"),
   TEMP_PREFIX(":t"),
   LITERAL_PREFIX(":l"),
   REGEX_PREFIX(":r"),
   JAVA_THIS((String)null, 0),
   INIT_MAP((String)null, 1),
   INIT_SCOPE((String)null, 2),
   INIT_ARGUMENTS((String)null, 3),
   JS_OBJECT_DUAL_FIELD_PREFIX("JD"),
   JS_OBJECT_SINGLE_FIELD_PREFIX("JO"),
   ALLOCATE("allocate"),
   SPLIT_PREFIX(":split"),
   SPLIT_ARRAY_ARG(":split_array", 3),
   GET_STRING(":getString"),
   GET_MAP(":getMap"),
   SET_MAP(":setMap"),
   GET_ARRAY_PREFIX(":get"),
   GET_ARRAY_SUFFIX("$array");

   private static Set<String> symbolNames;
   private static final String INTERNAL_METHOD_PREFIX = ":";
   private final String symbolName;
   private final Class<?> type;
   private final int slot;

   private CompilerConstants() {
      this.symbolName = this.name();
      this.type = null;
      this.slot = -1;
   }

   private CompilerConstants(String symbolName) {
      this(symbolName, -1);
   }

   private CompilerConstants(String symbolName, int slot) {
      this(symbolName, (Class)null, slot);
   }

   private CompilerConstants(String symbolName, Class<?> type) {
      this(symbolName, type, -1);
   }

   private CompilerConstants(String symbolName, Class<?> type, int slot) {
      this.symbolName = symbolName;
      this.type = type;
      this.slot = slot;
   }

   public static boolean isCompilerConstant(String name) {
      ensureSymbolNames();
      return symbolNames.contains(name);
   }

   private static void ensureSymbolNames() {
      if (symbolNames == null) {
         symbolNames = new HashSet();
         CompilerConstants[] var0 = values();
         int var1 = var0.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            CompilerConstants cc = var0[var2];
            symbolNames.add(cc.symbolName);
         }
      }

   }

   public final String symbolName() {
      return this.symbolName;
   }

   public final Class<?> type() {
      return this.type;
   }

   public final int slot() {
      return this.slot;
   }

   public final String descriptor() {
      assert this.type != null : " asking for descriptor of typeless constant";

      return typeDescriptor(this.type);
   }

   public static String className(Class<?> type) {
      return Type.getInternalName(type);
   }

   public static String methodDescriptor(Class<?> rtype, Class<?>... ptypes) {
      return Type.getMethodDescriptor(rtype, ptypes);
   }

   public static String typeDescriptor(Class<?> clazz) {
      return Type.typeFor(clazz).getDescriptor();
   }

   public static CompilerConstants.Call constructorNoLookup(Class<?> clazz) {
      return specialCallNoLookup(clazz, INIT.symbolName(), Void.TYPE);
   }

   public static CompilerConstants.Call constructorNoLookup(String className, Class<?>... ptypes) {
      return specialCallNoLookup(className, INIT.symbolName(), methodDescriptor(Void.TYPE, ptypes));
   }

   public static CompilerConstants.Call constructorNoLookup(Class<?> clazz, Class<?>... ptypes) {
      return specialCallNoLookup(clazz, INIT.symbolName(), Void.TYPE, ptypes);
   }

   public static CompilerConstants.Call specialCallNoLookup(String className, String name, final String desc) {
      return new CompilerConstants.Call((MethodHandle)null, className, name, desc) {
         MethodEmitter invoke(MethodEmitter method) {
            return method.invokespecial(this.className, this.name, this.descriptor);
         }

         public void invoke(MethodVisitor mv) {
            mv.visitMethodInsn(183, this.className, this.name, desc, false);
         }
      };
   }

   public static CompilerConstants.Call specialCallNoLookup(Class<?> clazz, String name, Class<?> rtype, Class<?>... ptypes) {
      return specialCallNoLookup(className(clazz), name, methodDescriptor(rtype, ptypes));
   }

   public static CompilerConstants.Call staticCallNoLookup(String className, String name, final String desc) {
      return new CompilerConstants.Call((MethodHandle)null, className, name, desc) {
         MethodEmitter invoke(MethodEmitter method) {
            return method.invokestatic(this.className, this.name, this.descriptor);
         }

         public void invoke(MethodVisitor mv) {
            mv.visitMethodInsn(184, this.className, this.name, desc, false);
         }
      };
   }

   public static CompilerConstants.Call staticCallNoLookup(Class<?> clazz, String name, Class<?> rtype, Class<?>... ptypes) {
      return staticCallNoLookup(className(clazz), name, methodDescriptor(rtype, ptypes));
   }

   public static CompilerConstants.Call virtualCallNoLookup(Class<?> clazz, String name, Class<?> rtype, Class<?>... ptypes) {
      return new CompilerConstants.Call((MethodHandle)null, className(clazz), name, methodDescriptor(rtype, ptypes)) {
         MethodEmitter invoke(MethodEmitter method) {
            return method.invokevirtual(this.className, this.name, this.descriptor);
         }

         public void invoke(MethodVisitor mv) {
            mv.visitMethodInsn(182, this.className, this.name, this.descriptor, false);
         }
      };
   }

   public static CompilerConstants.Call interfaceCallNoLookup(Class<?> clazz, String name, Class<?> rtype, Class<?>... ptypes) {
      return new CompilerConstants.Call((MethodHandle)null, className(clazz), name, methodDescriptor(rtype, ptypes)) {
         MethodEmitter invoke(MethodEmitter method) {
            return method.invokeinterface(this.className, this.name, this.descriptor);
         }

         public void invoke(MethodVisitor mv) {
            mv.visitMethodInsn(185, this.className, this.name, this.descriptor, true);
         }
      };
   }

   public static CompilerConstants.FieldAccess virtualField(String className, String name, String desc) {
      return new CompilerConstants.FieldAccess(className, name, desc) {
         public MethodEmitter get(MethodEmitter method) {
            return method.getField(this.className, this.name, this.descriptor);
         }

         public void put(MethodEmitter method) {
            method.putField(this.className, this.name, this.descriptor);
         }
      };
   }

   public static CompilerConstants.FieldAccess virtualField(Class<?> clazz, String name, Class<?> type) {
      return virtualField(className(clazz), name, typeDescriptor(type));
   }

   public static CompilerConstants.FieldAccess staticField(String className, String name, String desc) {
      return new CompilerConstants.FieldAccess(className, name, desc) {
         public MethodEmitter get(MethodEmitter method) {
            return method.getStatic(this.className, this.name, this.descriptor);
         }

         public void put(MethodEmitter method) {
            method.putStatic(this.className, this.name, this.descriptor);
         }
      };
   }

   public static CompilerConstants.FieldAccess staticField(Class<?> clazz, String name, Class<?> type) {
      return staticField(className(clazz), name, typeDescriptor(type));
   }

   public static CompilerConstants.Call staticCall(Lookup lookup, Class<?> clazz, String name, Class<?> rtype, Class<?>... ptypes) {
      return new CompilerConstants.Call(jdk.nashorn.internal.lookup.Lookup.MH.findStatic(lookup, clazz, name, jdk.nashorn.internal.lookup.Lookup.MH.type(rtype, ptypes)), className(clazz), name, methodDescriptor(rtype, ptypes)) {
         MethodEmitter invoke(MethodEmitter method) {
            return method.invokestatic(this.className, this.name, this.descriptor);
         }

         public void invoke(MethodVisitor mv) {
            mv.visitMethodInsn(184, this.className, this.name, this.descriptor, false);
         }
      };
   }

   public static CompilerConstants.Call virtualCall(Lookup lookup, Class<?> clazz, String name, Class<?> rtype, Class<?>... ptypes) {
      return new CompilerConstants.Call(jdk.nashorn.internal.lookup.Lookup.MH.findVirtual(lookup, clazz, name, jdk.nashorn.internal.lookup.Lookup.MH.type(rtype, ptypes)), className(clazz), name, methodDescriptor(rtype, ptypes)) {
         MethodEmitter invoke(MethodEmitter method) {
            return method.invokevirtual(this.className, this.name, this.descriptor);
         }

         public void invoke(MethodVisitor mv) {
            mv.visitMethodInsn(182, this.className, this.name, this.descriptor, false);
         }
      };
   }

   public static CompilerConstants.Call specialCall(Lookup lookup, Class<?> clazz, String name, Class<?> rtype, Class<?>... ptypes) {
      return new CompilerConstants.Call(jdk.nashorn.internal.lookup.Lookup.MH.findSpecial(lookup, clazz, name, jdk.nashorn.internal.lookup.Lookup.MH.type(rtype, ptypes), clazz), className(clazz), name, methodDescriptor(rtype, ptypes)) {
         MethodEmitter invoke(MethodEmitter method) {
            return method.invokespecial(this.className, this.name, this.descriptor);
         }

         public void invoke(MethodVisitor mv) {
            mv.visitMethodInsn(183, this.className, this.name, this.descriptor, false);
         }
      };
   }

   public static boolean isInternalMethodName(String methodName) {
      return methodName.startsWith(":") && !methodName.equals(PROGRAM.symbolName);
   }

   static {
      CompilerConstants[] var0 = values();
      int var1 = var0.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         CompilerConstants c = var0[var2];
         String symbolName = c.symbolName();
         if (symbolName != null) {
            symbolName.intern();
         }
      }

   }

   public abstract static class Call extends CompilerConstants.Access {
      protected Call(String className, String name, String descriptor) {
         super((MethodHandle)null, className, name, descriptor);
      }

      protected Call(MethodHandle methodHandle, String className, String name, String descriptor) {
         super(methodHandle, className, name, descriptor);
      }

      abstract MethodEmitter invoke(MethodEmitter var1);

      public abstract void invoke(MethodVisitor var1);
   }

   public abstract static class FieldAccess extends CompilerConstants.Access {
      protected FieldAccess(String className, String name, String descriptor) {
         super((MethodHandle)null, className, name, descriptor);
      }

      protected abstract MethodEmitter get(MethodEmitter var1);

      protected abstract void put(MethodEmitter var1);
   }

   private abstract static class Access {
      protected final MethodHandle methodHandle;
      protected final String className;
      protected final String name;
      protected final String descriptor;

      protected Access(MethodHandle methodHandle, String className, String name, String descriptor) {
         this.methodHandle = methodHandle;
         this.className = className;
         this.name = name;
         this.descriptor = descriptor;
      }

      public MethodHandle methodHandle() {
         return this.methodHandle;
      }

      public String className() {
         return this.className;
      }

      public String name() {
         return this.name;
      }

      public String descriptor() {
         return this.descriptor;
      }
   }
}
