package jdk.nashorn.internal.runtime.linker;

import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;
import java.security.SecureClassLoader;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import jdk.internal.dynalink.beans.StaticClass;
import jdk.nashorn.internal.codegen.Compiler;
import jdk.nashorn.internal.codegen.DumpBytecode;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;

final class JavaAdapterClassLoader {
   private static final AccessControlContext CREATE_LOADER_ACC_CTXT = ClassAndLoader.createPermAccCtxt("createClassLoader");
   private static final AccessControlContext GET_CONTEXT_ACC_CTXT = ClassAndLoader.createPermAccCtxt("nashorn.getContext");
   private static final Collection<String> VISIBLE_INTERNAL_CLASS_NAMES = Collections.unmodifiableCollection(new HashSet(Arrays.asList(JavaAdapterServices.class.getName(), ScriptObject.class.getName(), ScriptFunction.class.getName(), JSType.class.getName())));
   private final String className;
   private final byte[] classBytes;

   JavaAdapterClassLoader(String className, byte[] classBytes) {
      this.className = className.replace('/', '.');
      this.classBytes = classBytes;
   }

   StaticClass generateClass(final ClassLoader parentLoader, final ProtectionDomain protectionDomain) {
      assert protectionDomain != null;

      return (StaticClass)AccessController.doPrivileged(new PrivilegedAction<StaticClass>() {
         public StaticClass run() {
            try {
               return StaticClass.forClass(Class.forName(JavaAdapterClassLoader.this.className, true, JavaAdapterClassLoader.this.createClassLoader(parentLoader, protectionDomain)));
            } catch (ClassNotFoundException var2) {
               throw new AssertionError(var2);
            }
         }
      }, CREATE_LOADER_ACC_CTXT);
   }

   private ClassLoader createClassLoader(ClassLoader parentLoader, final ProtectionDomain protectionDomain) {
      return new SecureClassLoader(parentLoader) {
         private final ClassLoader myLoader = this.getClass().getClassLoader();

         public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
            try {
               Context.checkPackageAccess(name);
               return super.loadClass(name, resolve);
            } catch (SecurityException var4) {
               if (JavaAdapterClassLoader.VISIBLE_INTERNAL_CLASS_NAMES.contains(name)) {
                  return this.myLoader.loadClass(name);
               } else {
                  throw var4;
               }
            }
         }

         protected Class<?> findClass(String name) throws ClassNotFoundException {
            if (name.equals(JavaAdapterClassLoader.this.className)) {
               assert JavaAdapterClassLoader.this.classBytes != null : "what? already cleared .class bytes!!";

               Context ctx = (Context)AccessController.doPrivileged(new PrivilegedAction<Context>() {
                  public Context run() {
                     return Context.getContext();
                  }
               }, JavaAdapterClassLoader.GET_CONTEXT_ACC_CTXT);
               DumpBytecode.dumpBytecode(ctx.getEnv(), ctx.getLogger(Compiler.class), JavaAdapterClassLoader.this.classBytes, name);
               return this.defineClass(name, JavaAdapterClassLoader.this.classBytes, 0, JavaAdapterClassLoader.this.classBytes.length, protectionDomain);
            } else {
               throw new ClassNotFoundException(name);
            }
         }
      };
   }
}
