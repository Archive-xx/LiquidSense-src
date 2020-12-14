package jdk.nashorn.internal.runtime;

import java.security.CodeSource;
import java.util.Objects;

final class ScriptLoader extends NashornLoader {
   private static final String NASHORN_PKG_PREFIX = "jdk.nashorn.internal.";
   private final Context context;

   Context getContext() {
      return this.context;
   }

   ScriptLoader(Context context) {
      super(context.getStructLoader());
      this.context = context;
   }

   protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
      checkPackageAccess(name);
      return super.loadClass(name, resolve);
   }

   protected Class<?> findClass(String name) throws ClassNotFoundException {
      ClassLoader appLoader = this.context.getAppLoader();
      if (appLoader != null && !name.startsWith("jdk.nashorn.internal.")) {
         return appLoader.loadClass(name);
      } else {
         throw new ClassNotFoundException(name);
      }
   }

   synchronized Class<?> installClass(String name, byte[] data, CodeSource cs) {
      return this.defineClass(name, data, 0, data.length, (CodeSource)Objects.requireNonNull(cs));
   }
}
