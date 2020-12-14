package jdk.nashorn.internal.codegen;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.util.TraceClassVisitor;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.ir.FunctionNode;
import jdk.nashorn.internal.ir.debug.NashornClassReader;
import jdk.nashorn.internal.ir.debug.NashornTextifier;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.RewriteException;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.Source;
import jdk.nashorn.internal.scripts.JS;

public class ClassEmitter {
   private static final EnumSet<ClassEmitter.Flag> DEFAULT_METHOD_FLAGS;
   private boolean classStarted;
   private boolean classEnded;
   private final HashSet<MethodEmitter> methodsStarted;
   protected final ClassWriter cw;
   protected final Context context;
   private String unitClassName;
   private Set<Class<?>> constantMethodNeeded;
   private int methodCount;
   private int initCount;
   private int clinitCount;
   private int fieldCount;
   private final Set<String> methodNames;

   private ClassEmitter(Context context, ClassWriter cw) {
      this.context = context;
      this.cw = cw;
      this.methodsStarted = new HashSet();
      this.methodNames = new HashSet();
   }

   public Set<String> getMethodNames() {
      return Collections.unmodifiableSet(this.methodNames);
   }

   ClassEmitter(Context context, String className, String superClassName, String... interfaceNames) {
      this(context, new ClassWriter(3));
      this.cw.visit(51, 33, className, (String)null, superClassName, interfaceNames);
   }

   ClassEmitter(Context context, String sourceName, String unitClassName, boolean strictMode) {
      this(context, new ClassWriter(3) {
         private static final String OBJECT_CLASS = "java/lang/Object";

         protected String getCommonSuperClass(String type1, String type2) {
            try {
               return super.getCommonSuperClass(type1, type2);
            } catch (RuntimeException var4) {
               return ClassEmitter.isScriptObject("jdk/nashorn/internal/scripts", type1) && ClassEmitter.isScriptObject("jdk/nashorn/internal/scripts", type2) ? CompilerConstants.className(ScriptObject.class) : "java/lang/Object";
            }
         }
      });
      this.unitClassName = unitClassName;
      this.constantMethodNeeded = new HashSet();
      this.cw.visit(51, 33, unitClassName, (String)null, pathName(JS.class.getName()), (String[])null);
      this.cw.visitSource(sourceName, (String)null);
      this.defineCommonStatics(strictMode);
   }

   Context getContext() {
      return this.context;
   }

   String getUnitClassName() {
      return this.unitClassName;
   }

   public int getMethodCount() {
      return this.methodCount;
   }

   public int getClinitCount() {
      return this.clinitCount;
   }

   public int getInitCount() {
      return this.initCount;
   }

   public int getFieldCount() {
      return this.fieldCount;
   }

   private static String pathName(String name) {
      return name.replace('.', '/');
   }

   private void defineCommonStatics(boolean strictMode) {
      this.field(EnumSet.of(ClassEmitter.Flag.PRIVATE, ClassEmitter.Flag.STATIC), CompilerConstants.SOURCE.symbolName(), Source.class);
      this.field(EnumSet.of(ClassEmitter.Flag.PRIVATE, ClassEmitter.Flag.STATIC), CompilerConstants.CONSTANTS.symbolName(), Object[].class);
      this.field(EnumSet.of(ClassEmitter.Flag.PUBLIC, ClassEmitter.Flag.STATIC, ClassEmitter.Flag.FINAL), CompilerConstants.STRICT_MODE.symbolName(), Boolean.TYPE, strictMode);
   }

   private void defineCommonUtilities() {
      assert this.unitClassName != null;

      MethodEmitter getMapMethod;
      if (this.constantMethodNeeded.contains(String.class)) {
         getMapMethod = this.method(EnumSet.of(ClassEmitter.Flag.PRIVATE, ClassEmitter.Flag.STATIC), CompilerConstants.GET_STRING.symbolName(), String.class, Integer.TYPE);
         getMapMethod.begin();
         getMapMethod.getStatic(this.unitClassName, CompilerConstants.CONSTANTS.symbolName(), CompilerConstants.CONSTANTS.descriptor()).load(Type.INT, 0).arrayload().checkcast(String.class)._return();
         getMapMethod.end();
      }

      if (this.constantMethodNeeded.contains(PropertyMap.class)) {
         getMapMethod = this.method(EnumSet.of(ClassEmitter.Flag.PUBLIC, ClassEmitter.Flag.STATIC), CompilerConstants.GET_MAP.symbolName(), PropertyMap.class, Integer.TYPE);
         getMapMethod.begin();
         getMapMethod.loadConstants().load(Type.INT, 0).arrayload().checkcast(PropertyMap.class)._return();
         getMapMethod.end();
         MethodEmitter setMapMethod = this.method(EnumSet.of(ClassEmitter.Flag.PUBLIC, ClassEmitter.Flag.STATIC), CompilerConstants.SET_MAP.symbolName(), Void.TYPE, Integer.TYPE, PropertyMap.class);
         setMapMethod.begin();
         setMapMethod.loadConstants().load(Type.INT, 0).load(Type.OBJECT, 1).arraystore();
         setMapMethod.returnVoid();
         setMapMethod.end();
      }

      Iterator var3 = this.constantMethodNeeded.iterator();

      while(var3.hasNext()) {
         Class<?> clazz = (Class)var3.next();
         if (clazz.isArray()) {
            this.defineGetArrayMethod(clazz);
         }
      }

   }

   private void defineGetArrayMethod(Class<?> clazz) {
      assert this.unitClassName != null;

      String methodName = getArrayMethodName(clazz);
      MethodEmitter getArrayMethod = this.method(EnumSet.of(ClassEmitter.Flag.PRIVATE, ClassEmitter.Flag.STATIC), methodName, clazz, Integer.TYPE);
      getArrayMethod.begin();
      getArrayMethod.getStatic(this.unitClassName, CompilerConstants.CONSTANTS.symbolName(), CompilerConstants.CONSTANTS.descriptor()).load(Type.INT, 0).arrayload().checkcast(clazz).invoke(CompilerConstants.virtualCallNoLookup(clazz, "clone", Object.class)).checkcast(clazz)._return();
      getArrayMethod.end();
   }

   static String getArrayMethodName(Class<?> clazz) {
      assert clazz.isArray();

      return CompilerConstants.GET_ARRAY_PREFIX.symbolName() + clazz.getComponentType().getSimpleName() + CompilerConstants.GET_ARRAY_SUFFIX.symbolName();
   }

   void needGetConstantMethod(Class<?> clazz) {
      this.constantMethodNeeded.add(clazz);
   }

   private static boolean isScriptObject(String scriptPrefix, String type) {
      if (type.startsWith(scriptPrefix)) {
         return true;
      } else if (type.equals(CompilerConstants.className(ScriptObject.class))) {
         return true;
      } else {
         return type.startsWith("jdk/nashorn/internal/objects");
      }
   }

   public void begin() {
      this.classStarted = true;
   }

   public void end() {
      assert this.classStarted : "class not started for " + this.unitClassName;

      if (this.unitClassName != null) {
         MethodEmitter initMethod = this.init(EnumSet.of(ClassEmitter.Flag.PRIVATE));
         initMethod.begin();
         initMethod.load(Type.OBJECT, 0);
         initMethod.newInstance(JS.class);
         initMethod.returnVoid();
         initMethod.end();
         this.defineCommonUtilities();
      }

      this.cw.visitEnd();
      this.classStarted = false;
      this.classEnded = true;

      assert this.methodsStarted.isEmpty() : "methodsStarted not empty " + this.methodsStarted;

   }

   static String disassemble(byte[] bytecode) {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      PrintWriter pw = new PrintWriter(baos);
      Throwable var3 = null;

      try {
         NashornClassReader cr = new NashornClassReader(bytecode);
         Context ctx = (Context)AccessController.doPrivileged(new PrivilegedAction<Context>() {
            public Context run() {
               return Context.getContext();
            }
         });
         TraceClassVisitor tcv = new TraceClassVisitor((ClassVisitor)null, new NashornTextifier(ctx.getEnv(), cr), pw);
         cr.accept(tcv, 0);
      } catch (Throwable var14) {
         var3 = var14;
         throw var14;
      } finally {
         if (pw != null) {
            if (var3 != null) {
               try {
                  pw.close();
               } catch (Throwable var13) {
                  var3.addSuppressed(var13);
               }
            } else {
               pw.close();
            }
         }

      }

      String str = new String(baos.toByteArray());
      return str;
   }

   void beginMethod(MethodEmitter method) {
      assert !this.methodsStarted.contains(method);

      this.methodsStarted.add(method);
   }

   void endMethod(MethodEmitter method) {
      assert this.methodsStarted.contains(method);

      this.methodsStarted.remove(method);
   }

   MethodEmitter method(String methodName, Class<?> rtype, Class<?>... ptypes) {
      return this.method(DEFAULT_METHOD_FLAGS, methodName, rtype, ptypes);
   }

   MethodEmitter method(EnumSet<ClassEmitter.Flag> methodFlags, String methodName, Class<?> rtype, Class<?>... ptypes) {
      ++this.methodCount;
      this.methodNames.add(methodName);
      return new MethodEmitter(this, this.methodVisitor(methodFlags, methodName, rtype, ptypes));
   }

   MethodEmitter method(String methodName, String descriptor) {
      return this.method(DEFAULT_METHOD_FLAGS, methodName, descriptor);
   }

   MethodEmitter method(EnumSet<ClassEmitter.Flag> methodFlags, String methodName, String descriptor) {
      ++this.methodCount;
      this.methodNames.add(methodName);
      return new MethodEmitter(this, this.cw.visitMethod(ClassEmitter.Flag.getValue(methodFlags), methodName, descriptor, (String)null, (String[])null));
   }

   MethodEmitter method(FunctionNode functionNode) {
      ++this.methodCount;
      this.methodNames.add(functionNode.getName());
      FunctionSignature signature = new FunctionSignature(functionNode);
      MethodVisitor mv = this.cw.visitMethod(9 | (functionNode.isVarArg() ? 128 : 0), functionNode.getName(), signature.toString(), (String)null, (String[])null);
      return new MethodEmitter(this, mv, functionNode);
   }

   MethodEmitter restOfMethod(FunctionNode functionNode) {
      ++this.methodCount;
      this.methodNames.add(functionNode.getName());
      MethodVisitor mv = this.cw.visitMethod(9, functionNode.getName(), Type.getMethodDescriptor(functionNode.getReturnType().getTypeClass(), RewriteException.class), (String)null, (String[])null);
      return new MethodEmitter(this, mv, functionNode);
   }

   MethodEmitter clinit() {
      ++this.clinitCount;
      return this.method(EnumSet.of(ClassEmitter.Flag.STATIC), CompilerConstants.CLINIT.symbolName(), Void.TYPE);
   }

   MethodEmitter init() {
      ++this.initCount;
      return this.method(CompilerConstants.INIT.symbolName(), Void.TYPE);
   }

   MethodEmitter init(Class<?>... ptypes) {
      ++this.initCount;
      return this.method(CompilerConstants.INIT.symbolName(), Void.TYPE, ptypes);
   }

   MethodEmitter init(EnumSet<ClassEmitter.Flag> flags, Class<?>... ptypes) {
      ++this.initCount;
      return this.method(flags, CompilerConstants.INIT.symbolName(), Void.TYPE, ptypes);
   }

   final void field(EnumSet<ClassEmitter.Flag> fieldFlags, String fieldName, Class<?> fieldType, Object value) {
      ++this.fieldCount;
      this.cw.visitField(ClassEmitter.Flag.getValue(fieldFlags), fieldName, CompilerConstants.typeDescriptor(fieldType), (String)null, value).visitEnd();
   }

   final void field(EnumSet<ClassEmitter.Flag> fieldFlags, String fieldName, Class<?> fieldType) {
      this.field(fieldFlags, fieldName, fieldType, (Object)null);
   }

   final void field(String fieldName, Class<?> fieldType) {
      this.field(EnumSet.of(ClassEmitter.Flag.PUBLIC), fieldName, fieldType, (Object)null);
   }

   byte[] toByteArray() {
      assert this.classEnded;

      return !this.classEnded ? null : this.cw.toByteArray();
   }

   private MethodVisitor methodVisitor(EnumSet<ClassEmitter.Flag> flags, String methodName, Class<?> rtype, Class<?>... ptypes) {
      return this.cw.visitMethod(ClassEmitter.Flag.getValue(flags), methodName, CompilerConstants.methodDescriptor(rtype, ptypes), (String)null, (String[])null);
   }

   static {
      DEFAULT_METHOD_FLAGS = EnumSet.of(ClassEmitter.Flag.PUBLIC);
   }

   static enum Flag {
      HANDLE_STATIC(6),
      HANDLE_NEWSPECIAL(8),
      HANDLE_SPECIAL(7),
      HANDLE_VIRTUAL(5),
      HANDLE_INTERFACE(9),
      FINAL(16),
      STATIC(8),
      PUBLIC(1),
      PRIVATE(2);

      private int value;

      private Flag(int value) {
         this.value = value;
      }

      int getValue() {
         return this.value;
      }

      static int getValue(EnumSet<ClassEmitter.Flag> flags) {
         int v = 0;

         ClassEmitter.Flag flag;
         for(Iterator var2 = flags.iterator(); var2.hasNext(); v |= flag.getValue()) {
            flag = (ClassEmitter.Flag)var2.next();
         }

         return v;
      }
   }
}
