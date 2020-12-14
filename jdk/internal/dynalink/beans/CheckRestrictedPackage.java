package jdk.internal.dynalink.beans;

import java.lang.reflect.Modifier;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.CodeSource;
import java.security.Permissions;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;

class CheckRestrictedPackage {
   private static final AccessControlContext NO_PERMISSIONS_CONTEXT = createNoPermissionsContext();

   static boolean isRestrictedClass(Class<?> clazz) {
      if (!Modifier.isPublic(clazz.getModifiers())) {
         return true;
      } else {
         final SecurityManager sm = System.getSecurityManager();
         if (sm == null) {
            return false;
         } else {
            final String name = clazz.getName();
            final int i = name.lastIndexOf(46);
            if (i == -1) {
               return false;
            } else {
               try {
                  AccessController.doPrivileged(new PrivilegedAction<Void>() {
                     public Void run() {
                        sm.checkPackageAccess(name.substring(0, i));
                        return null;
                     }
                  }, NO_PERMISSIONS_CONTEXT);
                  return false;
               } catch (SecurityException var5) {
                  return true;
               }
            }
         }
      }
   }

   private static AccessControlContext createNoPermissionsContext() {
      return new AccessControlContext(new ProtectionDomain[]{new ProtectionDomain((CodeSource)null, new Permissions())});
   }
}
