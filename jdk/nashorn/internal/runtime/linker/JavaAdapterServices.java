package jdk.nashorn.internal.runtime.linker;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.net.URL;
import java.security.AccessController;
import java.security.CodeSigner;
import java.security.CodeSource;
import java.security.Permissions;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;
import java.security.SecureClassLoader;
import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.Type;
import jdk.internal.org.objectweb.asm.commons.InstructionAdapter;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.Undefined;

public final class JavaAdapterServices {
   private static final ThreadLocal<ScriptObject> classOverrides = new ThreadLocal();
   private static final MethodHandle NO_PERMISSIONS_INVOKER = createNoPermissionsInvoker();

   private JavaAdapterServices() {
   }

   public static MethodHandle getHandle(ScriptFunction fn, MethodType type) {
      return bindAndAdaptHandle(fn, fn.isStrict() ? ScriptRuntime.UNDEFINED : Context.getGlobal(), type);
   }

   public static MethodHandle getHandle(Object obj, String name, MethodType type) {
      if (!(obj instanceof ScriptObject)) {
         throw ECMAErrors.typeError("not.an.object", ScriptRuntime.safeToString(obj));
      } else {
         ScriptObject sobj = (ScriptObject)obj;
         if ("toString".equals(name) && !sobj.hasOwnProperty("toString")) {
            return null;
         } else {
            Object fnObj = sobj.get(name);
            if (fnObj instanceof ScriptFunction) {
               return bindAndAdaptHandle((ScriptFunction)fnObj, sobj, type);
            } else if (fnObj != null && !(fnObj instanceof Undefined)) {
               throw ECMAErrors.typeError("not.a.function", name);
            } else {
               return null;
            }
         }
      }
   }

   public static Object getClassOverrides() {
      Object overrides = classOverrides.get();

      assert overrides != null;

      return overrides;
   }

   public static void invokeNoPermissions(MethodHandle method, Object arg) throws Throwable {
      NO_PERMISSIONS_INVOKER.invokeExact(method, arg);
   }

   public static void setGlobal(Object global) {
      Context.setGlobal((ScriptObject)global);
   }

   public static Object getGlobal() {
      return Context.getGlobal();
   }

   static void setClassOverrides(ScriptObject overrides) {
      classOverrides.set(overrides);
   }

   private static MethodHandle bindAndAdaptHandle(ScriptFunction fn, Object self, MethodType type) {
      return Bootstrap.getLinkerServices().asType(ScriptObject.pairArguments(fn.getBoundInvokeHandle(self), type, false), type);
   }

   private static MethodHandle createNoPermissionsInvoker() {
      String className = "NoPermissionsInvoker";
      ClassWriter cw = new ClassWriter(3);
      cw.visit(51, 49, "NoPermissionsInvoker", (String)null, "java/lang/Object", (String[])null);
      Type objectType = Type.getType(Object.class);
      Type methodHandleType = Type.getType(MethodHandle.class);
      InstructionAdapter mv = new InstructionAdapter(cw.visitMethod(9, "invoke", Type.getMethodDescriptor(Type.VOID_TYPE, methodHandleType, objectType), (String)null, (String[])null));
      mv.visitCode();
      mv.visitVarInsn(25, 0);
      mv.visitVarInsn(25, 1);
      mv.invokevirtual(methodHandleType.getInternalName(), "invokeExact", Type.getMethodDescriptor(Type.VOID_TYPE, objectType), false);
      mv.visitInsn(177);
      mv.visitMaxs(0, 0);
      mv.visitEnd();
      cw.visitEnd();
      final byte[] bytes = cw.toByteArray();
      ClassLoader loader = (ClassLoader)AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() {
         public ClassLoader run() {
            return new SecureClassLoader((ClassLoader)null) {
               protected Class<?> findClass(String name) throws ClassNotFoundException {
                  if (name.equals("NoPermissionsInvoker")) {
                     return this.defineClass(name, bytes, 0, bytes.length, new ProtectionDomain(new CodeSource((URL)null, (CodeSigner[])null), new Permissions()));
                  } else {
                     throw new ClassNotFoundException(name);
                  }
               }
            };
         }
      });

      try {
         return MethodHandles.lookup().findStatic(Class.forName("NoPermissionsInvoker", true, loader), "invoke", MethodType.methodType(Void.TYPE, MethodHandle.class, Object.class));
      } catch (ReflectiveOperationException var8) {
         throw new AssertionError(var8.getMessage(), var8);
      }
   }

   public static MethodHandle getObjectConverter(Class<?> returnType) {
      return Bootstrap.getLinkerServices().getTypeConverter(Object.class, returnType);
   }

   public static Object exportReturnValue(Object obj) {
      return NashornBeansLinker.exportArgument(obj, true);
   }

   public static char toCharPrimitive(Object obj) {
      return JavaArgumentConverters.toCharPrimitive(obj);
   }

   public static String toString(Object obj) {
      return JavaArgumentConverters.toString(obj);
   }
}
