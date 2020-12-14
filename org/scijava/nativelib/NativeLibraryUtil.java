package org.scijava.nativelib;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NativeLibraryUtil {
   private static NativeLibraryUtil.Architecture architecture;
   private static final String DELIM = "/";
   private static final String JAVA_TMPDIR = "java.io.tmpdir";
   private static final Logger LOGGER;

   public static NativeLibraryUtil.Architecture getArchitecture() {
      if (NativeLibraryUtil.Architecture.UNKNOWN == architecture) {
         NativeLibraryUtil.Processor processor = getProcessor();
         if (NativeLibraryUtil.Processor.UNKNOWN != processor) {
            String name = System.getProperty("os.name").toLowerCase();
            if (name.indexOf("nix") < 0 && name.indexOf("nux") < 0) {
               if (name.indexOf("win") >= 0) {
                  if (NativeLibraryUtil.Processor.INTEL_32 == processor) {
                     architecture = NativeLibraryUtil.Architecture.WINDOWS_32;
                  } else if (NativeLibraryUtil.Processor.INTEL_64 == processor) {
                     architecture = NativeLibraryUtil.Architecture.WINDOWS_64;
                  }
               } else if (name.indexOf("mac") >= 0) {
                  if (NativeLibraryUtil.Processor.INTEL_32 == processor) {
                     architecture = NativeLibraryUtil.Architecture.OSX_32;
                  } else if (NativeLibraryUtil.Processor.INTEL_64 == processor) {
                     architecture = NativeLibraryUtil.Architecture.OSX_64;
                  } else if (NativeLibraryUtil.Processor.PPC == processor) {
                     architecture = NativeLibraryUtil.Architecture.OSX_PPC;
                  }
               }
            } else if (NativeLibraryUtil.Processor.INTEL_32 == processor) {
               architecture = NativeLibraryUtil.Architecture.LINUX_32;
            } else if (NativeLibraryUtil.Processor.INTEL_64 == processor) {
               architecture = NativeLibraryUtil.Architecture.LINUX_64;
            }
         }
      }

      LOGGER.log(Level.FINE, "architecture is " + architecture + " os.name is " + System.getProperty("os.name").toLowerCase());
      return architecture;
   }

   private static NativeLibraryUtil.Processor getProcessor() {
      NativeLibraryUtil.Processor processor = NativeLibraryUtil.Processor.UNKNOWN;
      String arch = System.getProperty("os.arch").toLowerCase();
      if (arch.indexOf("ppc") >= 0) {
         processor = NativeLibraryUtil.Processor.PPC;
      } else if (arch.indexOf("86") >= 0 || arch.indexOf("amd") >= 0) {
         int bits = 32;
         if (arch.indexOf("64") >= 0) {
            bits = 64;
         }

         processor = 32 == bits ? NativeLibraryUtil.Processor.INTEL_32 : NativeLibraryUtil.Processor.INTEL_64;
      }

      LOGGER.log(Level.FINE, "processor is " + processor + " os.arch is " + System.getProperty("os.arch").toLowerCase());
      return processor;
   }

   public static String getPlatformLibraryPath() {
      String path = "META-INF/lib/";
      path = path + getArchitecture().name().toLowerCase() + "/";
      LOGGER.log(Level.FINE, "platform specific path is " + path);
      return path;
   }

   public static String getPlatformLibraryName(String libName) {
      String name = null;
      switch(getArchitecture()) {
      case LINUX_32:
      case LINUX_64:
         name = libName + ".so";
         break;
      case WINDOWS_32:
      case WINDOWS_64:
         name = libName + ".dll";
         break;
      case OSX_32:
      case OSX_64:
         name = "lib" + libName + ".dylib";
      }

      LOGGER.log(Level.FINE, "native library name " + name);
      return name;
   }

   public static String getVersionedLibraryName(Class libraryJarClass, String libName) {
      String version = libraryJarClass.getPackage().getImplementationVersion();
      if (null != version && version.length() > 0) {
         libName = libName + "-" + version;
      }

      return libName;
   }

   public static boolean loadVersionedNativeLibrary(Class libraryJarClass, String libName) {
      libName = getVersionedLibraryName(libraryJarClass, libName);
      return loadNativeLibrary(libraryJarClass, libName);
   }

   public static boolean loadNativeLibrary(Class libraryJarClass, String libName) {
      boolean success = false;
      if (NativeLibraryUtil.Architecture.UNKNOWN == getArchitecture()) {
         LOGGER.log(Level.WARNING, "No native library available for this platform.");
      } else {
         try {
            String tmpDirectory = System.getProperty("java.io.tmpdir");
            JniExtractor jniExtractor = new DefaultJniExtractor(libraryJarClass, tmpDirectory);
            File extractedFile = jniExtractor.extractJni(getPlatformLibraryPath(), libName);
            System.load(extractedFile.getPath());
            success = true;
         } catch (IOException var6) {
            LOGGER.log(Level.WARNING, "IOException creating DefaultJniExtractor", var6);
         } catch (SecurityException var7) {
            LOGGER.log(Level.WARNING, "Can't load dynamic library", var7);
         } catch (UnsatisfiedLinkError var8) {
            LOGGER.log(Level.WARNING, "Problem with library", var8);
         }
      }

      return success;
   }

   static {
      architecture = NativeLibraryUtil.Architecture.UNKNOWN;
      LOGGER = Logger.getLogger("org.scijava.nativelib.NativeLibraryUtil");
   }

   private static enum Processor {
      UNKNOWN,
      INTEL_32,
      INTEL_64,
      PPC;
   }

   public static enum Architecture {
      UNKNOWN,
      LINUX_32,
      LINUX_64,
      WINDOWS_32,
      WINDOWS_64,
      OSX_32,
      OSX_64,
      OSX_PPC;
   }
}
