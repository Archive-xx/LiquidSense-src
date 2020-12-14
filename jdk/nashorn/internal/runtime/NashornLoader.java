package jdk.nashorn.internal.runtime;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.CodeSource;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Permissions;
import java.security.SecureClassLoader;

abstract class NashornLoader extends SecureClassLoader {
   private static final String OBJECTS_PKG = "jdk.nashorn.internal.objects";
   private static final String RUNTIME_PKG = "jdk.nashorn.internal.runtime";
   private static final String RUNTIME_ARRAYS_PKG = "jdk.nashorn.internal.runtime.arrays";
   private static final String RUNTIME_LINKER_PKG = "jdk.nashorn.internal.runtime.linker";
   private static final String SCRIPTS_PKG = "jdk.nashorn.internal.scripts";
   private static final Permission[] SCRIPT_PERMISSIONS = new Permission[]{new RuntimePermission("accessClassInPackage.jdk.nashorn.internal.runtime"), new RuntimePermission("accessClassInPackage.jdk.nashorn.internal.runtime.linker"), new RuntimePermission("accessClassInPackage.jdk.nashorn.internal.objects"), new RuntimePermission("accessClassInPackage.jdk.nashorn.internal.scripts"), new RuntimePermission("accessClassInPackage.jdk.nashorn.internal.runtime.arrays")};

   NashornLoader(ClassLoader parent) {
      super(parent);
   }

   protected static void checkPackageAccess(String name) {
      int i = name.lastIndexOf(46);
      if (i != -1) {
         SecurityManager sm = System.getSecurityManager();
         if (sm != null) {
            String pkgName = name.substring(0, i);
            byte var5 = -1;
            switch(pkgName.hashCode()) {
            case -1427089809:
               if (pkgName.equals("jdk.nashorn.internal.runtime")) {
                  var5 = 0;
               }
               break;
            case -1051537505:
               if (pkgName.equals("jdk.nashorn.internal.scripts")) {
                  var5 = 4;
               }
               break;
            case -342733909:
               if (pkgName.equals("jdk.nashorn.internal.objects")) {
                  var5 = 3;
               }
               break;
            case 69590425:
               if (pkgName.equals("jdk.nashorn.internal.runtime.arrays")) {
                  var5 = 1;
               }
               break;
            case 376089222:
               if (pkgName.equals("jdk.nashorn.internal.runtime.linker")) {
                  var5 = 2;
               }
            }

            switch(var5) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
               break;
            default:
               sm.checkPackageAccess(pkgName);
            }
         }
      }

   }

   protected PermissionCollection getPermissions(CodeSource codesource) {
      Permissions permCollection = new Permissions();
      Permission[] var3 = SCRIPT_PERMISSIONS;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Permission perm = var3[var5];
         permCollection.add(perm);
      }

      return permCollection;
   }

   static ClassLoader createClassLoader(String classPath, ClassLoader parent) {
      URL[] urls = pathToURLs(classPath);
      return URLClassLoader.newInstance(urls, parent);
   }

   private static URL[] pathToURLs(String path) {
      String[] components = path.split(File.pathSeparator);
      URL[] urls = new URL[components.length];
      int count = 0;

      while(count < components.length) {
         URL url = fileToURL(new File(components[count]));
         if (url != null) {
            urls[count++] = url;
         }
      }

      if (urls.length != count) {
         URL[] tmp = new URL[count];
         System.arraycopy(urls, 0, tmp, 0, count);
         urls = tmp;
      }

      return urls;
   }

   private static URL fileToURL(File file) {
      String name;
      try {
         name = file.getCanonicalPath();
      } catch (IOException var4) {
         name = file.getAbsolutePath();
      }

      name = name.replace(File.separatorChar, '/');
      if (!name.startsWith("/")) {
         name = "/" + name;
      }

      if (!file.isFile()) {
         name = name + "/";
      }

      try {
         return new URL("file", "", name);
      } catch (MalformedURLException var3) {
         throw new IllegalArgumentException("file");
      }
   }
}
