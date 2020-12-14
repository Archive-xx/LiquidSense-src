package jdk.nashorn.internal.runtime;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public final class Version {
   private static final String VERSION_RB_NAME = "jdk.nashorn.internal.runtime.resources.version";
   private static ResourceBundle versionRB;

   private Version() {
   }

   public static String version() {
      return version("release");
   }

   public static String fullVersion() {
      return version("full");
   }

   private static String version(String key) {
      if (versionRB == null) {
         try {
            versionRB = ResourceBundle.getBundle("jdk.nashorn.internal.runtime.resources.version");
         } catch (MissingResourceException var3) {
            return "version not available";
         }
      }

      try {
         return versionRB.getString(key);
      } catch (MissingResourceException var2) {
         return "version not available";
      }
   }
}
