package jdk.nashorn.internal.runtime.linker;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.Handle;
import jdk.internal.org.objectweb.asm.Label;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Type;
import jdk.internal.org.objectweb.asm.commons.InstructionAdapter;
import jdk.nashorn.api.scripting.ScriptUtils;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import sun.reflect.CallerSensitive;

final class JavaAdapterBytecodeGenerator {
   private static final Type SCRIPTUTILS_TYPE = Type.getType(ScriptUtils.class);
   private static final Type OBJECT_TYPE = Type.getType(Object.class);
   private static final Type CLASS_TYPE = Type.getType(Class.class);
   static final String OBJECT_TYPE_NAME;
   static final String SCRIPTUTILS_TYPE_NAME;
   static final String INIT = "<init>";
   static final String GLOBAL_FIELD_NAME = "global";
   static final String GLOBAL_TYPE_DESCRIPTOR;
   static final String SET_GLOBAL_METHOD_DESCRIPTOR;
   static final String VOID_NOARG_METHOD_DESCRIPTOR;
   private static final Type SCRIPT_OBJECT_TYPE;
   private static final Type SCRIPT_FUNCTION_TYPE;
   private static final Type STRING_TYPE;
   private static final Type METHOD_TYPE_TYPE;
   private static final Type METHOD_HANDLE_TYPE;
   private static final String GET_HANDLE_OBJECT_DESCRIPTOR;
   private static final String GET_HANDLE_FUNCTION_DESCRIPTOR;
   private static final String GET_CLASS_INITIALIZER_DESCRIPTOR;
   private static final Type RUNTIME_EXCEPTION_TYPE;
   private static final Type THROWABLE_TYPE;
   private static final Type UNSUPPORTED_OPERATION_TYPE;
   private static final String SERVICES_CLASS_TYPE_NAME;
   private static final String RUNTIME_EXCEPTION_TYPE_NAME;
   private static final String ERROR_TYPE_NAME;
   private static final String THROWABLE_TYPE_NAME;
   private static final String UNSUPPORTED_OPERATION_TYPE_NAME;
   private static final String METHOD_HANDLE_TYPE_DESCRIPTOR;
   private static final String GET_GLOBAL_METHOD_DESCRIPTOR;
   private static final String GET_CLASS_METHOD_DESCRIPTOR;
   private static final String EXPORT_RETURN_VALUE_METHOD_DESCRIPTOR;
   private static final String UNWRAP_METHOD_DESCRIPTOR;
   private static final String GET_CONVERTER_METHOD_DESCRIPTOR;
   private static final String TO_CHAR_PRIMITIVE_METHOD_DESCRIPTOR;
   private static final String TO_STRING_METHOD_DESCRIPTOR;
   private static final String ADAPTER_PACKAGE_PREFIX = "jdk/nashorn/javaadapters/";
   private static final String ADAPTER_CLASS_NAME_SUFFIX = "$$NashornJavaAdapter";
   private static final String JAVA_PACKAGE_PREFIX = "java/";
   private static final int MAX_GENERATED_TYPE_NAME_LENGTH = 255;
   private static final String CLASS_INIT = "<clinit>";
   static final String SUPER_PREFIX = "super$";
   private static final Collection<JavaAdapterBytecodeGenerator.MethodInfo> EXCLUDED;
   private final Class<?> superClass;
   private final List<Class<?>> interfaces;
   private final ClassLoader commonLoader;
   private final boolean classOverride;
   private final String superClassName;
   private final String generatedClassName;
   private final Set<String> usedFieldNames = new HashSet();
   private final Set<String> abstractMethodNames = new HashSet();
   private final String samName;
   private final Set<JavaAdapterBytecodeGenerator.MethodInfo> finalMethods;
   private final Set<JavaAdapterBytecodeGenerator.MethodInfo> methodInfos;
   private boolean autoConvertibleFromFunction;
   private boolean hasExplicitFinalizer;
   private final Map<Class<?>, String> converterFields;
   private final Set<Class<?>> samReturnTypes;
   private final ClassWriter cw;
   private static final AccessControlContext GET_DECLARED_MEMBERS_ACC_CTXT;

   JavaAdapterBytecodeGenerator(Class<?> superClass, List<Class<?>> interfaces, ClassLoader commonLoader, boolean classOverride) throws AdaptationException {
      this.finalMethods = new HashSet(EXCLUDED);
      this.methodInfos = new HashSet();
      this.autoConvertibleFromFunction = false;
      this.hasExplicitFinalizer = false;
      this.converterFields = new LinkedHashMap();
      this.samReturnTypes = new HashSet();

      assert superClass != null && !superClass.isInterface();

      assert interfaces != null;

      this.superClass = superClass;
      this.interfaces = interfaces;
      this.classOverride = classOverride;
      this.commonLoader = commonLoader;
      this.cw = new ClassWriter(3) {
         protected String getCommonSuperClass(String type1, String type2) {
            return JavaAdapterBytecodeGenerator.this.getCommonSuperClass(type1, type2);
         }
      };
      this.superClassName = Type.getInternalName(superClass);
      this.generatedClassName = getGeneratedClassName(superClass, interfaces);
      this.cw.visit(51, 33, this.generatedClassName, (String)null, this.superClassName, getInternalTypeNames(interfaces));
      this.generateGlobalFields();
      this.gatherMethods(superClass);
      this.gatherMethods(interfaces);
      this.samName = this.abstractMethodNames.size() == 1 ? (String)this.abstractMethodNames.iterator().next() : null;
      this.generateHandleFields();
      this.generateConverterFields();
      if (classOverride) {
         this.generateClassInit();
      }

      this.generateConstructors();
      this.generateMethods();
      this.generateSuperMethods();
      if (this.hasExplicitFinalizer) {
         this.generateFinalizerMethods();
      }

      this.cw.visitEnd();
   }

   private void generateGlobalFields() {
      this.cw.visitField(18 | (this.classOverride ? 8 : 0), "global", GLOBAL_TYPE_DESCRIPTOR, (String)null, (Object)null).visitEnd();
      this.usedFieldNames.add("global");
   }

   JavaAdapterClassLoader createAdapterClassLoader() {
      return new JavaAdapterClassLoader(this.generatedClassName, this.cw.toByteArray());
   }

   boolean isAutoConvertibleFromFunction() {
      return this.autoConvertibleFromFunction;
   }

   private static String getGeneratedClassName(Class<?> superType, List<Class<?>> interfaces) {
      Class<?> namingType = superType == Object.class ? (interfaces.isEmpty() ? Object.class : (Class)interfaces.get(0)) : superType;
      Package pkg = namingType.getPackage();
      String namingTypeName = Type.getInternalName(namingType);
      StringBuilder buf = new StringBuilder();
      if (!namingTypeName.startsWith("java/") && pkg != null && !pkg.isSealed()) {
         buf.append(namingTypeName).append("$$NashornJavaAdapter");
      } else {
         buf.append("jdk/nashorn/javaadapters/").append(namingTypeName);
      }

      Iterator<Class<?>> it = interfaces.iterator();
      if (superType == Object.class && it.hasNext()) {
         it.next();
      }

      while(it.hasNext()) {
         buf.append("$$").append(((Class)it.next()).getSimpleName());
      }

      return buf.toString().substring(0, Math.min(255, buf.length()));
   }

   private static String[] getInternalTypeNames(List<Class<?>> classes) {
      int interfaceCount = classes.size();
      String[] interfaceNames = new String[interfaceCount];

      for(int i = 0; i < interfaceCount; ++i) {
         interfaceNames[i] = Type.getInternalName((Class)classes.get(i));
      }

      return interfaceNames;
   }

   private void generateHandleFields() {
      int flags = 18 | (this.classOverride ? 8 : 0);
      Iterator var2 = this.methodInfos.iterator();

      while(var2.hasNext()) {
         JavaAdapterBytecodeGenerator.MethodInfo mi = (JavaAdapterBytecodeGenerator.MethodInfo)var2.next();
         this.cw.visitField(flags, mi.methodHandleFieldName, METHOD_HANDLE_TYPE_DESCRIPTOR, (String)null, (Object)null).visitEnd();
      }

   }

   private void generateConverterFields() {
      int flags = 18 | (this.classOverride ? 8 : 0);
      Iterator var2 = this.methodInfos.iterator();

      while(var2.hasNext()) {
         JavaAdapterBytecodeGenerator.MethodInfo mi = (JavaAdapterBytecodeGenerator.MethodInfo)var2.next();
         Class<?> returnType = mi.type.returnType();
         if (!returnType.isPrimitive() && returnType != Object.class && returnType != String.class && !this.converterFields.containsKey(returnType)) {
            String name = this.nextName("convert");
            this.converterFields.put(returnType, name);
            if (mi.getName().equals(this.samName)) {
               this.samReturnTypes.add(returnType);
            }

            this.cw.visitField(flags, name, METHOD_HANDLE_TYPE_DESCRIPTOR, (String)null, (Object)null).visitEnd();
         }
      }

   }

   private void generateClassInit() {
      InstructionAdapter mv = new InstructionAdapter(this.cw.visitMethod(8, "<clinit>", Type.getMethodDescriptor(Type.VOID_TYPE), (String)null, (String[])null));
      mv.invokestatic(SERVICES_CLASS_TYPE_NAME, "getClassOverrides", GET_CLASS_INITIALIZER_DESCRIPTOR, false);
      Label initGlobal;
      if (this.samName != null) {
         Label notAFunction = new Label();
         mv.dup();
         mv.instanceOf(SCRIPT_FUNCTION_TYPE);
         mv.ifeq(notAFunction);
         mv.checkcast(SCRIPT_FUNCTION_TYPE);

         JavaAdapterBytecodeGenerator.MethodInfo mi;
         for(Iterator var4 = this.methodInfos.iterator(); var4.hasNext(); mv.putstatic(this.generatedClassName, mi.methodHandleFieldName, METHOD_HANDLE_TYPE_DESCRIPTOR)) {
            mi = (JavaAdapterBytecodeGenerator.MethodInfo)var4.next();
            if (mi.getName().equals(this.samName)) {
               mv.dup();
               loadMethodTypeAndGetHandle(mv, mi, GET_HANDLE_FUNCTION_DESCRIPTOR);
            } else {
               mv.visitInsn(1);
            }
         }

         initGlobal = new Label();
         mv.goTo(initGlobal);
         mv.visitLabel(notAFunction);
      } else {
         initGlobal = null;
      }

      Iterator var6 = this.methodInfos.iterator();

      while(var6.hasNext()) {
         JavaAdapterBytecodeGenerator.MethodInfo mi = (JavaAdapterBytecodeGenerator.MethodInfo)var6.next();
         mv.dup();
         mv.aconst(mi.getName());
         loadMethodTypeAndGetHandle(mv, mi, GET_HANDLE_OBJECT_DESCRIPTOR);
         mv.putstatic(this.generatedClassName, mi.methodHandleFieldName, METHOD_HANDLE_TYPE_DESCRIPTOR);
      }

      if (initGlobal != null) {
         mv.visitLabel(initGlobal);
      }

      invokeGetGlobalWithNullCheck(mv);
      mv.putstatic(this.generatedClassName, "global", GLOBAL_TYPE_DESCRIPTOR);
      this.generateConverterInit(mv, false);
      endInitMethod(mv);
   }

   private void generateConverterInit(InstructionAdapter mv, boolean samOnly) {
      assert !samOnly || !this.classOverride;

      Iterator var3 = this.converterFields.entrySet().iterator();

      while(var3.hasNext()) {
         Entry<Class<?>, String> converterField = (Entry)var3.next();
         Class<?> returnType = (Class)converterField.getKey();
         if (!this.classOverride) {
            mv.visitVarInsn(25, 0);
         }

         if (samOnly && !this.samReturnTypes.contains(returnType)) {
            mv.visitInsn(1);
         } else {
            mv.aconst(Type.getType((Class)converterField.getKey()));
            mv.invokestatic(SERVICES_CLASS_TYPE_NAME, "getObjectConverter", GET_CONVERTER_METHOD_DESCRIPTOR, false);
         }

         if (this.classOverride) {
            mv.putstatic(this.generatedClassName, (String)converterField.getValue(), METHOD_HANDLE_TYPE_DESCRIPTOR);
         } else {
            mv.putfield(this.generatedClassName, (String)converterField.getValue(), METHOD_HANDLE_TYPE_DESCRIPTOR);
         }
      }

   }

   private static void loadMethodTypeAndGetHandle(InstructionAdapter mv, JavaAdapterBytecodeGenerator.MethodInfo mi, String getHandleDescriptor) {
      mv.aconst(Type.getMethodType(mi.type.generic().toMethodDescriptorString()));
      mv.invokestatic(SERVICES_CLASS_TYPE_NAME, "getHandle", getHandleDescriptor, false);
   }

   private static void invokeGetGlobalWithNullCheck(InstructionAdapter mv) {
      invokeGetGlobal(mv);
      mv.dup();
      mv.invokevirtual(OBJECT_TYPE_NAME, "getClass", GET_CLASS_METHOD_DESCRIPTOR, false);
      mv.pop();
   }

   private void generateConstructors() throws AdaptationException {
      boolean gotCtor = false;
      Constructor[] var2 = this.superClass.getDeclaredConstructors();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Constructor<?> ctor = var2[var4];
         int modifier = ctor.getModifiers();
         if ((modifier & 5) != 0 && !isCallerSensitive(ctor)) {
            this.generateConstructors(ctor);
            gotCtor = true;
         }
      }

      if (!gotCtor) {
         throw new AdaptationException(AdaptationResult.Outcome.ERROR_NO_ACCESSIBLE_CONSTRUCTOR, this.superClass.getCanonicalName());
      }
   }

   private void generateConstructors(Constructor<?> ctor) {
      if (this.classOverride) {
         this.generateDelegatingConstructor(ctor);
      } else {
         this.generateOverridingConstructor(ctor, false);
         if (this.samName != null) {
            if (!this.autoConvertibleFromFunction && ctor.getParameterTypes().length == 0) {
               this.autoConvertibleFromFunction = true;
            }

            this.generateOverridingConstructor(ctor, true);
         }
      }

   }

   private void generateDelegatingConstructor(Constructor<?> ctor) {
      Type originalCtorType = Type.getType(ctor);
      Type[] argTypes = originalCtorType.getArgumentTypes();
      InstructionAdapter mv = new InstructionAdapter(this.cw.visitMethod(1 | (ctor.isVarArgs() ? 128 : 0), "<init>", Type.getMethodDescriptor(originalCtorType.getReturnType(), argTypes), (String)null, (String[])null));
      mv.visitCode();
      mv.visitVarInsn(25, 0);
      int offset = 1;
      Type[] var6 = argTypes;
      int var7 = argTypes.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         Type argType = var6[var8];
         mv.load(offset, argType);
         offset += argType.getSize();
      }

      mv.invokespecial(this.superClassName, "<init>", originalCtorType.getDescriptor(), false);
      endInitMethod(mv);
   }

   private void generateOverridingConstructor(Constructor<?> ctor, boolean fromFunction) {
      Type originalCtorType = Type.getType(ctor);
      Type[] originalArgTypes = originalCtorType.getArgumentTypes();
      int argLen = originalArgTypes.length;
      Type[] newArgTypes = new Type[argLen + 1];
      Type extraArgumentType = fromFunction ? SCRIPT_FUNCTION_TYPE : SCRIPT_OBJECT_TYPE;
      newArgTypes[argLen] = extraArgumentType;
      System.arraycopy(originalArgTypes, 0, newArgTypes, 0, argLen);
      InstructionAdapter mv = new InstructionAdapter(this.cw.visitMethod(1, "<init>", Type.getMethodDescriptor(originalCtorType.getReturnType(), newArgTypes), (String)null, (String[])null));
      mv.visitCode();
      mv.visitVarInsn(25, 0);
      Class<?>[] argTypes = ctor.getParameterTypes();
      int offset = 1;

      for(int i = 0; i < argLen; ++i) {
         Type argType = Type.getType(argTypes[i]);
         mv.load(offset, argType);
         offset += argType.getSize();
      }

      mv.invokespecial(this.superClassName, "<init>", originalCtorType.getDescriptor(), false);
      String getHandleDescriptor = fromFunction ? GET_HANDLE_FUNCTION_DESCRIPTOR : GET_HANDLE_OBJECT_DESCRIPTOR;

      JavaAdapterBytecodeGenerator.MethodInfo mi;
      for(Iterator var15 = this.methodInfos.iterator(); var15.hasNext(); mv.putfield(this.generatedClassName, mi.methodHandleFieldName, METHOD_HANDLE_TYPE_DESCRIPTOR)) {
         mi = (JavaAdapterBytecodeGenerator.MethodInfo)var15.next();
         mv.visitVarInsn(25, 0);
         if (fromFunction && !mi.getName().equals(this.samName)) {
            mv.visitInsn(1);
         } else {
            mv.visitVarInsn(25, offset);
            if (!fromFunction) {
               mv.aconst(mi.getName());
            }

            loadMethodTypeAndGetHandle(mv, mi, getHandleDescriptor);
         }
      }

      mv.visitVarInsn(25, 0);
      invokeGetGlobalWithNullCheck(mv);
      mv.putfield(this.generatedClassName, "global", GLOBAL_TYPE_DESCRIPTOR);
      this.generateConverterInit(mv, fromFunction);
      endInitMethod(mv);
      if (!fromFunction) {
         newArgTypes[argLen] = OBJECT_TYPE;
         InstructionAdapter mv2 = new InstructionAdapter(this.cw.visitMethod(1, "<init>", Type.getMethodDescriptor(originalCtorType.getReturnType(), newArgTypes), (String)null, (String[])null));
         this.generateOverridingConstructorWithObjectParam(mv2, ctor, originalCtorType.getDescriptor());
      }

   }

   private void generateOverridingConstructorWithObjectParam(InstructionAdapter mv, Constructor<?> ctor, String ctorDescriptor) {
      mv.visitCode();
      mv.visitVarInsn(25, 0);
      Class<?>[] argTypes = ctor.getParameterTypes();
      int offset = 1;

      for(int i = 0; i < argTypes.length; ++i) {
         Type argType = Type.getType(argTypes[i]);
         mv.load(offset, argType);
         offset += argType.getSize();
      }

      mv.invokespecial(this.superClassName, "<init>", ctorDescriptor, false);
      mv.visitVarInsn(25, offset);
      mv.visitInsn(1);
      mv.visitInsn(1);
      mv.invokestatic(SERVICES_CLASS_TYPE_NAME, "getHandle", GET_HANDLE_OBJECT_DESCRIPTOR, false);
      endInitMethod(mv);
   }

   private static void endInitMethod(InstructionAdapter mv) {
      mv.visitInsn(177);
      endMethod(mv);
   }

   private static void endMethod(InstructionAdapter mv) {
      mv.visitMaxs(0, 0);
      mv.visitEnd();
   }

   private static void invokeGetGlobal(InstructionAdapter mv) {
      mv.invokestatic(SERVICES_CLASS_TYPE_NAME, "getGlobal", GET_GLOBAL_METHOD_DESCRIPTOR, false);
   }

   private static void invokeSetGlobal(InstructionAdapter mv) {
      mv.invokestatic(SERVICES_CLASS_TYPE_NAME, "setGlobal", SET_GLOBAL_METHOD_DESCRIPTOR, false);
   }

   private String nextName(String name) {
      int i = 0;

      String nextName;
      String ordinal;
      int maxNameLen;
      for(nextName = name; !this.usedFieldNames.add(nextName); nextName = (name.length() <= maxNameLen ? name : name.substring(0, maxNameLen)).concat(ordinal)) {
         ordinal = String.valueOf(i++);
         maxNameLen = 255 - ordinal.length();
      }

      return nextName;
   }

   private void generateMethods() {
      Iterator var1 = this.methodInfos.iterator();

      while(var1.hasNext()) {
         JavaAdapterBytecodeGenerator.MethodInfo mi = (JavaAdapterBytecodeGenerator.MethodInfo)var1.next();
         this.generateMethod(mi);
      }

   }

   private void generateMethod(JavaAdapterBytecodeGenerator.MethodInfo mi) {
      Method method = mi.method;
      Class<?>[] exceptions = method.getExceptionTypes();
      String[] exceptionNames = getExceptionNames(exceptions);
      MethodType type = mi.type;
      String methodDesc = type.toMethodDescriptorString();
      String name = mi.getName();
      Type asmType = Type.getMethodType(methodDesc);
      Type[] asmArgTypes = asmType.getArgumentTypes();
      InstructionAdapter mv = new InstructionAdapter(this.cw.visitMethod(getAccessModifiers(method), name, methodDesc, (String)null, exceptionNames));
      mv.visitCode();
      Label handleDefined = new Label();
      Class<?> returnType = type.returnType();
      Type asmReturnType = Type.getType(returnType);
      if (this.classOverride) {
         mv.getstatic(this.generatedClassName, mi.methodHandleFieldName, METHOD_HANDLE_TYPE_DESCRIPTOR);
      } else {
         mv.visitVarInsn(25, 0);
         mv.getfield(this.generatedClassName, mi.methodHandleFieldName, METHOD_HANDLE_TYPE_DESCRIPTOR);
      }

      mv.visitInsn(89);
      mv.visitJumpInsn(199, handleDefined);
      if (Modifier.isAbstract(method.getModifiers())) {
         mv.anew(UNSUPPORTED_OPERATION_TYPE);
         mv.dup();
         mv.invokespecial(UNSUPPORTED_OPERATION_TYPE_NAME, "<init>", VOID_NOARG_METHOD_DESCRIPTOR, false);
         mv.athrow();
      } else {
         mv.visitInsn(87);
         this.emitSuperCall(mv, method.getDeclaringClass(), name, methodDesc);
      }

      mv.visitLabel(handleDefined);
      if (this.classOverride) {
         mv.getstatic(this.generatedClassName, "global", GLOBAL_TYPE_DESCRIPTOR);
      } else {
         mv.visitVarInsn(25, 0);
         mv.getfield(this.generatedClassName, "global", GLOBAL_TYPE_DESCRIPTOR);
      }

      Label setupGlobal = new Label();
      mv.visitLabel(setupGlobal);
      int nextLocalVar = 1;
      Type[] var16 = asmArgTypes;
      int globalsDifferVar = asmArgTypes.length;

      for(int var18 = 0; var18 < globalsDifferVar; ++var18) {
         Type t = var16[var18];
         nextLocalVar += t.getSize();
      }

      int currentGlobalVar = nextLocalVar++;
      globalsDifferVar = nextLocalVar++;
      mv.dup();
      invokeGetGlobal(mv);
      mv.dup();
      mv.visitVarInsn(58, currentGlobalVar);
      Label globalsDiffer = new Label();
      mv.ifacmpne(globalsDiffer);
      mv.pop();
      mv.iconst(0);
      Label invokeHandle = new Label();
      mv.goTo(invokeHandle);
      mv.visitLabel(globalsDiffer);
      invokeSetGlobal(mv);
      mv.iconst(1);
      mv.visitLabel(invokeHandle);
      mv.visitVarInsn(54, globalsDifferVar);
      int varOffset = 1;
      Type[] var21 = asmArgTypes;
      int var22 = asmArgTypes.length;

      for(int var23 = 0; var23 < var22; ++var23) {
         Type t = var21[var23];
         mv.load(varOffset, t);
         boxStackTop(mv, t);
         varOffset += t.getSize();
      }

      Label tryBlockStart = new Label();
      mv.visitLabel(tryBlockStart);
      emitInvokeExact(mv, type.generic());
      this.convertReturnValue(mv, returnType, asmReturnType);
      Label tryBlockEnd = new Label();
      mv.visitLabel(tryBlockEnd);
      emitFinally(mv, currentGlobalVar, globalsDifferVar);
      mv.areturn(asmReturnType);
      boolean throwableDeclared = isThrowableDeclared(exceptions);
      Label throwableHandler;
      if (!throwableDeclared) {
         throwableHandler = new Label();
         mv.visitLabel(throwableHandler);
         mv.anew(RUNTIME_EXCEPTION_TYPE);
         mv.dupX1();
         mv.swap();
         mv.invokespecial(RUNTIME_EXCEPTION_TYPE_NAME, "<init>", Type.getMethodDescriptor(Type.VOID_TYPE, THROWABLE_TYPE), false);
      } else {
         throwableHandler = null;
      }

      Label rethrowHandler = new Label();
      mv.visitLabel(rethrowHandler);
      emitFinally(mv, currentGlobalVar, globalsDifferVar);
      mv.athrow();
      Label methodEnd = new Label();
      mv.visitLabel(methodEnd);
      mv.visitLocalVariable("currentGlobal", GLOBAL_TYPE_DESCRIPTOR, (String)null, setupGlobal, methodEnd, currentGlobalVar);
      mv.visitLocalVariable("globalsDiffer", Type.BOOLEAN_TYPE.getDescriptor(), (String)null, setupGlobal, methodEnd, globalsDifferVar);
      if (throwableDeclared) {
         mv.visitTryCatchBlock(tryBlockStart, tryBlockEnd, rethrowHandler, THROWABLE_TYPE_NAME);

         assert throwableHandler == null;
      } else {
         mv.visitTryCatchBlock(tryBlockStart, tryBlockEnd, rethrowHandler, RUNTIME_EXCEPTION_TYPE_NAME);
         mv.visitTryCatchBlock(tryBlockStart, tryBlockEnd, rethrowHandler, ERROR_TYPE_NAME);
         String[] var27 = exceptionNames;
         int var28 = exceptionNames.length;

         for(int var29 = 0; var29 < var28; ++var29) {
            String excName = var27[var29];
            mv.visitTryCatchBlock(tryBlockStart, tryBlockEnd, rethrowHandler, excName);
         }

         mv.visitTryCatchBlock(tryBlockStart, tryBlockEnd, throwableHandler, THROWABLE_TYPE_NAME);
      }

      endMethod(mv);
   }

   private void convertReturnValue(InstructionAdapter mv, Class<?> returnType, Type asmReturnType) {
      switch(asmReturnType.getSort()) {
      case 0:
         mv.pop();
         break;
      case 1:
         JSType.TO_BOOLEAN.invoke((MethodVisitor)mv);
         break;
      case 2:
         mv.invokestatic(SERVICES_CLASS_TYPE_NAME, "toCharPrimitive", TO_CHAR_PRIMITIVE_METHOD_DESCRIPTOR, false);
         break;
      case 3:
         JSType.TO_INT32.invoke((MethodVisitor)mv);
         mv.visitInsn(145);
         break;
      case 4:
         JSType.TO_INT32.invoke((MethodVisitor)mv);
         mv.visitInsn(147);
         break;
      case 5:
         JSType.TO_INT32.invoke((MethodVisitor)mv);
         break;
      case 6:
         JSType.TO_NUMBER.invoke((MethodVisitor)mv);
         mv.visitInsn(144);
         break;
      case 7:
         JSType.TO_LONG.invoke((MethodVisitor)mv);
         break;
      case 8:
         JSType.TO_NUMBER.invoke((MethodVisitor)mv);
         break;
      default:
         if (asmReturnType.equals(OBJECT_TYPE)) {
            mv.invokestatic(SERVICES_CLASS_TYPE_NAME, "exportReturnValue", EXPORT_RETURN_VALUE_METHOD_DESCRIPTOR, false);
         } else if (asmReturnType.equals(STRING_TYPE)) {
            mv.invokestatic(SERVICES_CLASS_TYPE_NAME, "toString", TO_STRING_METHOD_DESCRIPTOR, false);
         } else {
            if (this.classOverride) {
               mv.getstatic(this.generatedClassName, (String)this.converterFields.get(returnType), METHOD_HANDLE_TYPE_DESCRIPTOR);
            } else {
               mv.visitVarInsn(25, 0);
               mv.getfield(this.generatedClassName, (String)this.converterFields.get(returnType), METHOD_HANDLE_TYPE_DESCRIPTOR);
            }

            mv.swap();
            emitInvokeExact(mv, MethodType.methodType(returnType, Object.class));
         }
      }

   }

   private static void emitInvokeExact(InstructionAdapter mv, MethodType type) {
      mv.invokevirtual(METHOD_HANDLE_TYPE.getInternalName(), "invokeExact", type.toMethodDescriptorString(), false);
   }

   private static void boxStackTop(InstructionAdapter mv, Type t) {
      switch(t.getSort()) {
      case 1:
         invokeValueOf(mv, "Boolean", 'Z');
         break;
      case 2:
         invokeValueOf(mv, "Character", 'C');
         break;
      case 3:
      case 4:
      case 5:
         invokeValueOf(mv, "Integer", 'I');
         break;
      case 6:
         mv.visitInsn(141);
         invokeValueOf(mv, "Double", 'D');
         break;
      case 7:
         invokeValueOf(mv, "Long", 'J');
         break;
      case 8:
         invokeValueOf(mv, "Double", 'D');
      case 9:
      case 11:
         break;
      case 10:
         if (t.equals(OBJECT_TYPE)) {
            mv.invokestatic(SCRIPTUTILS_TYPE_NAME, "unwrap", UNWRAP_METHOD_DESCRIPTOR, false);
         }
         break;
      default:
         assert false;
      }

   }

   private static void invokeValueOf(InstructionAdapter mv, String boxedType, char unboxedType) {
      mv.invokestatic("java/lang/" + boxedType, "valueOf", "(" + unboxedType + ")Ljava/lang/" + boxedType + ";", false);
   }

   private static void emitFinally(InstructionAdapter mv, int currentGlobalVar, int globalsDifferVar) {
      mv.visitVarInsn(21, globalsDifferVar);
      Label skip = new Label();
      mv.ifeq(skip);
      mv.visitVarInsn(25, currentGlobalVar);
      invokeSetGlobal(mv);
      mv.visitLabel(skip);
   }

   private static boolean isThrowableDeclared(Class<?>[] exceptions) {
      Class[] var1 = exceptions;
      int var2 = exceptions.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Class<?> exception = var1[var3];
         if (exception == Throwable.class) {
            return true;
         }
      }

      return false;
   }

   private void generateSuperMethods() {
      Iterator var1 = this.methodInfos.iterator();

      while(var1.hasNext()) {
         JavaAdapterBytecodeGenerator.MethodInfo mi = (JavaAdapterBytecodeGenerator.MethodInfo)var1.next();
         if (!Modifier.isAbstract(mi.method.getModifiers())) {
            this.generateSuperMethod(mi);
         }
      }

   }

   private void generateSuperMethod(JavaAdapterBytecodeGenerator.MethodInfo mi) {
      Method method = mi.method;
      String methodDesc = mi.type.toMethodDescriptorString();
      String name = mi.getName();
      InstructionAdapter mv = new InstructionAdapter(this.cw.visitMethod(getAccessModifiers(method), "super$" + name, methodDesc, (String)null, getExceptionNames(method.getExceptionTypes())));
      mv.visitCode();
      this.emitSuperCall(mv, method.getDeclaringClass(), name, methodDesc);
      endMethod(mv);
   }

   private Class<?> findInvokespecialOwnerFor(Class<?> cl) {
      assert Modifier.isInterface(cl.getModifiers()) : cl + " is not an interface";

      if (cl.isAssignableFrom(this.superClass)) {
         return this.superClass;
      } else {
         Iterator var2 = this.interfaces.iterator();

         Class iface;
         do {
            if (!var2.hasNext()) {
               throw new AssertionError("can't find the class/interface that extends " + cl);
            }

            iface = (Class)var2.next();
         } while(!cl.isAssignableFrom(iface));

         return iface;
      }
   }

   private void emitSuperCall(InstructionAdapter mv, Class<?> owner, String name, String methodDesc) {
      mv.visitVarInsn(25, 0);
      int nextParam = 1;
      Type methodType = Type.getMethodType(methodDesc);
      Type[] var7 = methodType.getArgumentTypes();
      int var8 = var7.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         Type t = var7[var9];
         mv.load(nextParam, t);
         nextParam += t.getSize();
      }

      if (Modifier.isInterface(owner.getModifiers())) {
         mv.invokespecial(Type.getInternalName(this.findInvokespecialOwnerFor(owner)), name, methodDesc, false);
      } else {
         mv.invokespecial(this.superClassName, name, methodDesc, false);
      }

      mv.areturn(methodType.getReturnType());
   }

   private void generateFinalizerMethods() {
      String finalizerDelegateName = this.nextName("access$");
      this.generateFinalizerDelegate(finalizerDelegateName);
      this.generateFinalizerOverride(finalizerDelegateName);
   }

   private void generateFinalizerDelegate(String finalizerDelegateName) {
      InstructionAdapter mv = new InstructionAdapter(this.cw.visitMethod(10, finalizerDelegateName, Type.getMethodDescriptor(Type.VOID_TYPE, OBJECT_TYPE), (String)null, (String[])null));
      mv.visitVarInsn(25, 0);
      mv.checkcast(Type.getType(this.generatedClassName));
      mv.invokespecial(this.superClassName, "finalize", Type.getMethodDescriptor(Type.VOID_TYPE), false);
      mv.visitInsn(177);
      endMethod(mv);
   }

   private void generateFinalizerOverride(String finalizerDelegateName) {
      InstructionAdapter mv = new InstructionAdapter(this.cw.visitMethod(1, "finalize", VOID_NOARG_METHOD_DESCRIPTOR, (String)null, (String[])null));
      mv.aconst(new Handle(6, this.generatedClassName, finalizerDelegateName, Type.getMethodDescriptor(Type.VOID_TYPE, OBJECT_TYPE)));
      mv.visitVarInsn(25, 0);
      mv.invokestatic(SERVICES_CLASS_TYPE_NAME, "invokeNoPermissions", Type.getMethodDescriptor(METHOD_HANDLE_TYPE, OBJECT_TYPE), false);
      mv.visitInsn(177);
      endMethod(mv);
   }

   private static String[] getExceptionNames(Class<?>[] exceptions) {
      String[] exceptionNames = new String[exceptions.length];

      for(int i = 0; i < exceptions.length; ++i) {
         exceptionNames[i] = Type.getInternalName(exceptions[i]);
      }

      return exceptionNames;
   }

   private static int getAccessModifiers(Method method) {
      return 1 | (method.isVarArgs() ? 128 : 0);
   }

   private void gatherMethods(Class<?> type) throws AdaptationException {
      int var4;
      int var5;
      if (Modifier.isPublic(type.getModifiers())) {
         Method[] typeMethods = type.isInterface() ? type.getMethods() : type.getDeclaredMethods();
         Method[] var3 = typeMethods;
         var4 = typeMethods.length;

         for(var5 = 0; var5 < var4; ++var5) {
            Method typeMethod = var3[var5];
            String name = typeMethod.getName();
            if (!name.startsWith("super$")) {
               int m = typeMethod.getModifiers();
               if (!Modifier.isStatic(m) && (Modifier.isPublic(m) || Modifier.isProtected(m))) {
                  if (name.equals("finalize") && typeMethod.getParameterCount() == 0) {
                     if (type != Object.class) {
                        this.hasExplicitFinalizer = true;
                        if (Modifier.isFinal(m)) {
                           throw new AdaptationException(AdaptationResult.Outcome.ERROR_FINAL_FINALIZER, type.getCanonicalName());
                        }
                     }
                  } else {
                     JavaAdapterBytecodeGenerator.MethodInfo mi = new JavaAdapterBytecodeGenerator.MethodInfo(typeMethod);
                     if (!Modifier.isFinal(m) && !isCallerSensitive(typeMethod)) {
                        if (!this.finalMethods.contains(mi) && this.methodInfos.add(mi)) {
                           if (Modifier.isAbstract(m)) {
                              this.abstractMethodNames.add(mi.getName());
                           }

                           mi.setIsCanonical(this);
                        }
                     } else {
                        this.finalMethods.add(mi);
                     }
                  }
               }
            }
         }
      }

      if (!type.isInterface()) {
         Class<?> superType = type.getSuperclass();
         if (superType != null) {
            this.gatherMethods(superType);
         }

         Class[] var11 = type.getInterfaces();
         var4 = var11.length;

         for(var5 = 0; var5 < var4; ++var5) {
            Class<?> itf = var11[var5];
            this.gatherMethods(itf);
         }
      }

   }

   private void gatherMethods(List<Class<?>> classes) throws AdaptationException {
      Iterator var2 = classes.iterator();

      while(var2.hasNext()) {
         Class<?> c = (Class)var2.next();
         this.gatherMethods(c);
      }

   }

   private static Collection<JavaAdapterBytecodeGenerator.MethodInfo> getExcludedMethods() {
      return (Collection)AccessController.doPrivileged(new PrivilegedAction<Collection<JavaAdapterBytecodeGenerator.MethodInfo>>() {
         public Collection<JavaAdapterBytecodeGenerator.MethodInfo> run() {
            try {
               return Arrays.asList(new JavaAdapterBytecodeGenerator.MethodInfo(Object.class, "finalize", new Class[0]), new JavaAdapterBytecodeGenerator.MethodInfo(Object.class, "clone", new Class[0]));
            } catch (NoSuchMethodException var2) {
               throw new AssertionError(var2);
            }
         }
      }, GET_DECLARED_MEMBERS_ACC_CTXT);
   }

   private String getCommonSuperClass(String type1, String type2) {
      try {
         Class<?> c1 = Class.forName(type1.replace('/', '.'), false, this.commonLoader);
         Class<?> c2 = Class.forName(type2.replace('/', '.'), false, this.commonLoader);
         if (c1.isAssignableFrom(c2)) {
            return type1;
         } else if (c2.isAssignableFrom(c1)) {
            return type2;
         } else {
            return !c1.isInterface() && !c2.isInterface() ? assignableSuperClass(c1, c2).getName().replace('.', '/') : OBJECT_TYPE_NAME;
         }
      } catch (ClassNotFoundException var5) {
         throw new RuntimeException(var5);
      }
   }

   private static Class<?> assignableSuperClass(Class<?> c1, Class<?> c2) {
      Class<?> superClass = c1.getSuperclass();
      return superClass.isAssignableFrom(c2) ? superClass : assignableSuperClass(superClass, c2);
   }

   private static boolean isCallerSensitive(AccessibleObject e) {
      return e.isAnnotationPresent(CallerSensitive.class);
   }

   static {
      OBJECT_TYPE_NAME = OBJECT_TYPE.getInternalName();
      SCRIPTUTILS_TYPE_NAME = SCRIPTUTILS_TYPE.getInternalName();
      GLOBAL_TYPE_DESCRIPTOR = OBJECT_TYPE.getDescriptor();
      SET_GLOBAL_METHOD_DESCRIPTOR = Type.getMethodDescriptor(Type.VOID_TYPE, OBJECT_TYPE);
      VOID_NOARG_METHOD_DESCRIPTOR = Type.getMethodDescriptor(Type.VOID_TYPE);
      SCRIPT_OBJECT_TYPE = Type.getType(ScriptObject.class);
      SCRIPT_FUNCTION_TYPE = Type.getType(ScriptFunction.class);
      STRING_TYPE = Type.getType(String.class);
      METHOD_TYPE_TYPE = Type.getType(MethodType.class);
      METHOD_HANDLE_TYPE = Type.getType(MethodHandle.class);
      GET_HANDLE_OBJECT_DESCRIPTOR = Type.getMethodDescriptor(METHOD_HANDLE_TYPE, OBJECT_TYPE, STRING_TYPE, METHOD_TYPE_TYPE);
      GET_HANDLE_FUNCTION_DESCRIPTOR = Type.getMethodDescriptor(METHOD_HANDLE_TYPE, SCRIPT_FUNCTION_TYPE, METHOD_TYPE_TYPE);
      GET_CLASS_INITIALIZER_DESCRIPTOR = Type.getMethodDescriptor(OBJECT_TYPE);
      RUNTIME_EXCEPTION_TYPE = Type.getType(RuntimeException.class);
      THROWABLE_TYPE = Type.getType(Throwable.class);
      UNSUPPORTED_OPERATION_TYPE = Type.getType(UnsupportedOperationException.class);
      SERVICES_CLASS_TYPE_NAME = Type.getInternalName(JavaAdapterServices.class);
      RUNTIME_EXCEPTION_TYPE_NAME = RUNTIME_EXCEPTION_TYPE.getInternalName();
      ERROR_TYPE_NAME = Type.getInternalName(Error.class);
      THROWABLE_TYPE_NAME = THROWABLE_TYPE.getInternalName();
      UNSUPPORTED_OPERATION_TYPE_NAME = UNSUPPORTED_OPERATION_TYPE.getInternalName();
      METHOD_HANDLE_TYPE_DESCRIPTOR = METHOD_HANDLE_TYPE.getDescriptor();
      GET_GLOBAL_METHOD_DESCRIPTOR = Type.getMethodDescriptor(OBJECT_TYPE);
      GET_CLASS_METHOD_DESCRIPTOR = Type.getMethodDescriptor(CLASS_TYPE);
      EXPORT_RETURN_VALUE_METHOD_DESCRIPTOR = Type.getMethodDescriptor(OBJECT_TYPE, OBJECT_TYPE);
      UNWRAP_METHOD_DESCRIPTOR = Type.getMethodDescriptor(OBJECT_TYPE, OBJECT_TYPE);
      GET_CONVERTER_METHOD_DESCRIPTOR = Type.getMethodDescriptor(METHOD_HANDLE_TYPE, CLASS_TYPE);
      TO_CHAR_PRIMITIVE_METHOD_DESCRIPTOR = Type.getMethodDescriptor(Type.CHAR_TYPE, OBJECT_TYPE);
      TO_STRING_METHOD_DESCRIPTOR = Type.getMethodDescriptor(STRING_TYPE, OBJECT_TYPE);
      EXCLUDED = getExcludedMethods();
      GET_DECLARED_MEMBERS_ACC_CTXT = ClassAndLoader.createPermAccCtxt("accessDeclaredMembers");
   }

   private static class MethodInfo {
      private final Method method;
      private final MethodType type;
      private String methodHandleFieldName;

      private MethodInfo(Class<?> clazz, String name, Class<?>... argTypes) throws NoSuchMethodException {
         this(clazz.getDeclaredMethod(name, argTypes));
      }

      private MethodInfo(Method method) {
         this.method = method;
         this.type = Lookup.MH.type(method.getReturnType(), method.getParameterTypes());
      }

      public boolean equals(Object obj) {
         return obj instanceof JavaAdapterBytecodeGenerator.MethodInfo && this.equals((JavaAdapterBytecodeGenerator.MethodInfo)obj);
      }

      private boolean equals(JavaAdapterBytecodeGenerator.MethodInfo other) {
         return this.getName().equals(other.getName()) && this.type.equals(other.type);
      }

      String getName() {
         return this.method.getName();
      }

      public int hashCode() {
         return this.getName().hashCode() ^ this.type.hashCode();
      }

      void setIsCanonical(JavaAdapterBytecodeGenerator self) {
         this.methodHandleFieldName = self.nextName(this.getName());
      }

      // $FF: synthetic method
      MethodInfo(Method x0, Object x1) {
         this(x0);
      }

      // $FF: synthetic method
      MethodInfo(Class x0, String x1, Class[] x2, Object x3) throws NoSuchMethodException {
         this(x0, x1, x2);
      }
   }
}
