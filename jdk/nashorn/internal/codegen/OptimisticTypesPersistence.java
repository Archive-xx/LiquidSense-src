package jdk.nashorn.internal.codegen;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.security.AccessController;
import java.security.MessageDigest;
import java.security.PrivilegedAction;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.RecompilableScriptFunctionData;
import jdk.nashorn.internal.runtime.Source;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import jdk.nashorn.internal.runtime.options.Options;

public final class OptimisticTypesPersistence {
   private static final int DEFAULT_MAX_FILES = 0;
   private static final int UNLIMITED_FILES = -1;
   private static final int MAX_FILES = getMaxFiles();
   private static final int DEFAULT_CLEANUP_DELAY = 20;
   private static final int CLEANUP_DELAY = Math.max(0, Options.getIntProperty("nashorn.typeInfo.cleanupDelaySeconds", 20));
   private static final String DEFAULT_CACHE_SUBDIR_NAME = "com.oracle.java.NashornTypeInfo";
   private static final File baseCacheDir = createBaseCacheDir();
   private static final File cacheDir;
   private static final Object[] locks;
   private static final long ERROR_REPORT_THRESHOLD = 60000L;
   private static volatile long lastReportedError;
   private static final AtomicBoolean scheduledCleanup;
   private static final Timer cleanupTimer;

   public static Object getLocationDescriptor(Source source, int functionId, Type[] paramTypes) {
      if (cacheDir == null) {
         return null;
      } else {
         StringBuilder b = new StringBuilder(48);
         b.append(source.getDigest()).append('-').append(functionId);
         if (paramTypes != null && paramTypes.length > 0) {
            b.append('-');
            Type[] var4 = paramTypes;
            int var5 = paramTypes.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               Type t = var4[var6];
               b.append(Type.getShortSignatureDescriptor(t));
            }
         }

         return new OptimisticTypesPersistence.LocationDescriptor(new File(cacheDir, b.toString()));
      }
   }

   public static void store(Object locationDescriptor, final Map<Integer, Type> optimisticTypes) {
      if (locationDescriptor != null && !optimisticTypes.isEmpty()) {
         final File file = ((OptimisticTypesPersistence.LocationDescriptor)locationDescriptor).file;
         AccessController.doPrivileged(new PrivilegedAction<Void>() {
            public Void run() {
               synchronized(OptimisticTypesPersistence.getFileLock(file)) {
                  if (!file.exists()) {
                     OptimisticTypesPersistence.scheduleCleanup();
                  }

                  try {
                     FileOutputStream out = new FileOutputStream(file);
                     Throwable var3 = null;

                     try {
                        out.getChannel().lock();
                        DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(out));
                        Type.writeTypeMap(optimisticTypes, dout);
                        dout.flush();
                     } catch (Throwable var15) {
                        var3 = var15;
                        throw var15;
                     } finally {
                        if (out != null) {
                           if (var3 != null) {
                              try {
                                 out.close();
                              } catch (Throwable var14) {
                                 var3.addSuppressed(var14);
                              }
                           } else {
                              out.close();
                           }
                        }

                     }
                  } catch (Exception var17) {
                     OptimisticTypesPersistence.reportError("write", file, var17);
                  }

                  return null;
               }
            }
         });
      }
   }

   public static Map<Integer, Type> load(Object locationDescriptor) {
      if (locationDescriptor == null) {
         return null;
      } else {
         final File file = ((OptimisticTypesPersistence.LocationDescriptor)locationDescriptor).file;
         return (Map)AccessController.doPrivileged(new PrivilegedAction<Map<Integer, Type>>() {
            public Map<Integer, Type> run() {
               try {
                  if (!file.isFile()) {
                     return null;
                  } else {
                     synchronized(OptimisticTypesPersistence.getFileLock(file)) {
                        FileInputStream in = new FileInputStream(file);
                        Throwable var3 = null;

                        Map var5;
                        try {
                           in.getChannel().lock(0L, Long.MAX_VALUE, true);
                           DataInputStream din = new DataInputStream(new BufferedInputStream(in));
                           var5 = Type.readTypeMap(din);
                        } catch (Throwable var17) {
                           var3 = var17;
                           throw var17;
                        } finally {
                           if (in != null) {
                              if (var3 != null) {
                                 try {
                                    in.close();
                                 } catch (Throwable var16) {
                                    var3.addSuppressed(var16);
                                 }
                              } else {
                                 in.close();
                              }
                           }

                        }

                        return var5;
                     }
                  }
               } catch (Exception var20) {
                  OptimisticTypesPersistence.reportError("read", file, var20);
                  return null;
               }
            }
         });
      }
   }

   private static void reportError(String msg, File file, Exception e) {
      long now = System.currentTimeMillis();
      if (now - lastReportedError > 60000L) {
         reportError(String.format("Failed to %s %s", msg, file), e);
         lastReportedError = now;
      }

   }

   private static void reportError(String msg, Exception e) {
      getLogger().warning(msg, "\n", exceptionToString(e));
   }

   private static String exceptionToString(Exception e) {
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw, false);
      e.printStackTrace(pw);
      pw.flush();
      return sw.toString();
   }

   private static File createBaseCacheDir() {
      if (MAX_FILES != 0 && !Options.getBooleanProperty("nashorn.typeInfo.disabled")) {
         try {
            return createBaseCacheDirPrivileged();
         } catch (Exception var1) {
            reportError("Failed to create cache dir", var1);
            return null;
         }
      } else {
         return null;
      }
   }

   private static File createBaseCacheDirPrivileged() {
      return (File)AccessController.doPrivileged(new PrivilegedAction<File>() {
         public File run() {
            String explicitDir = System.getProperty("nashorn.typeInfo.cacheDir");
            File dir;
            if (explicitDir != null) {
               dir = new File(explicitDir);
            } else {
               File systemCacheDir = OptimisticTypesPersistence.getSystemCacheDir();
               dir = new File(systemCacheDir, "com.oracle.java.NashornTypeInfo");
               if (OptimisticTypesPersistence.isSymbolicLink(dir)) {
                  return null;
               }
            }

            return dir;
         }
      });
   }

   private static File createCacheDir(File baseDir) {
      if (baseDir == null) {
         return null;
      } else {
         try {
            return createCacheDirPrivileged(baseDir);
         } catch (Exception var2) {
            reportError("Failed to create cache dir", var2);
            return null;
         }
      }
   }

   private static File createCacheDirPrivileged(final File baseDir) {
      return (File)AccessController.doPrivileged(new PrivilegedAction<File>() {
         public File run() {
            String versionDirName;
            try {
               versionDirName = OptimisticTypesPersistence.getVersionDirName();
            } catch (Exception var3) {
               OptimisticTypesPersistence.reportError("Failed to calculate version dir name", var3);
               return null;
            }

            File versionDir = new File(baseDir, versionDirName);
            if (OptimisticTypesPersistence.isSymbolicLink(versionDir)) {
               return null;
            } else {
               versionDir.mkdirs();
               if (versionDir.isDirectory()) {
                  OptimisticTypesPersistence.getLogger().info("Optimistic type persistence directory is " + versionDir);
                  return versionDir;
               } else {
                  OptimisticTypesPersistence.getLogger().warning("Could not create optimistic type persistence directory " + versionDir);
                  return null;
               }
            }
         }
      });
   }

   private static File getSystemCacheDir() {
      String os = System.getProperty("os.name", "generic");
      if ("Mac OS X".equals(os)) {
         return new File(new File(System.getProperty("user.home"), "Library"), "Caches");
      } else {
         return os.startsWith("Windows") ? new File(System.getProperty("java.io.tmpdir")) : new File(System.getProperty("user.home"), ".cache");
      }
   }

   public static String getVersionDirName() throws Exception {
      URL url = OptimisticTypesPersistence.class.getResource("anchor.properties");
      String protocol = url.getProtocol();
      String fileStr;
      String className;
      if (protocol.equals("jar")) {
         fileStr = url.getFile();
         className = fileStr.substring(0, fileStr.indexOf(33));
         URL file = new URL(className);
         InputStream in = file.openStream();
         Throwable var23 = null;

         try {
            byte[] buf = new byte[131072];
            MessageDigest digest = MessageDigest.getInstance("SHA-1");

            while(true) {
               int l = in.read(buf);
               if (l == -1) {
                  String var10 = Base64.getUrlEncoder().withoutPadding().encodeToString(digest.digest());
                  return var10;
               }

               digest.update(buf, 0, l);
            }
         } catch (Throwable var19) {
            var23 = var19;
            throw var19;
         } finally {
            if (in != null) {
               if (var23 != null) {
                  try {
                     in.close();
                  } catch (Throwable var18) {
                     var23.addSuppressed(var18);
                  }
               } else {
                  in.close();
               }
            }

         }
      } else if (protocol.equals("file")) {
         fileStr = url.getFile();
         className = OptimisticTypesPersistence.class.getName();
         int packageNameLen = className.lastIndexOf(46);
         String dirStr = fileStr.substring(0, fileStr.length() - packageNameLen - 1);
         File dir = new File(dirStr);
         return "dev-" + (new SimpleDateFormat("yyyyMMdd-HHmmss")).format(new Date(getLastModifiedClassFile(dir, 0L)));
      } else {
         throw new AssertionError();
      }
   }

   private static long getLastModifiedClassFile(File dir, long max) {
      long currentMax = max;
      File[] var5 = dir.listFiles();
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         File f = var5[var7];
         long lastModified;
         if (f.getName().endsWith(".class")) {
            lastModified = f.lastModified();
            if (lastModified > currentMax) {
               currentMax = lastModified;
            }
         } else if (f.isDirectory()) {
            lastModified = getLastModifiedClassFile(f, currentMax);
            if (lastModified > currentMax) {
               currentMax = lastModified;
            }
         }
      }

      return currentMax;
   }

   private static boolean isSymbolicLink(File file) {
      if (Files.isSymbolicLink(file.toPath())) {
         getLogger().warning("Directory " + file + " is a symlink");
         return true;
      } else {
         return false;
      }
   }

   private static Object[] createLockArray() {
      Object[] lockArray = new Object[Runtime.getRuntime().availableProcessors() * 2];

      for(int i = 0; i < lockArray.length; ++i) {
         lockArray[i] = new Object();
      }

      return lockArray;
   }

   private static Object getFileLock(File file) {
      return locks[(file.hashCode() & Integer.MAX_VALUE) % locks.length];
   }

   private static DebugLogger getLogger() {
      try {
         return Context.getContext().getLogger(RecompilableScriptFunctionData.class);
      } catch (Exception var1) {
         var1.printStackTrace();
         return DebugLogger.DISABLED_LOGGER;
      }
   }

   private static void scheduleCleanup() {
      if (MAX_FILES != -1 && scheduledCleanup.compareAndSet(false, true)) {
         cleanupTimer.schedule(new TimerTask() {
            public void run() {
               OptimisticTypesPersistence.scheduledCleanup.set(false);

               try {
                  OptimisticTypesPersistence.doCleanup();
               } catch (IOException var2) {
               }

            }
         }, TimeUnit.SECONDS.toMillis((long)CLEANUP_DELAY));
      }

   }

   private static void doCleanup() throws IOException {
      Path[] files = getAllRegularFilesInLastModifiedOrder();
      int nFiles = files.length;
      int filesToDelete = Math.max(0, nFiles - MAX_FILES);
      int filesDeleted = 0;

      for(int i = 0; i < nFiles && filesDeleted < filesToDelete; ++i) {
         try {
            Files.deleteIfExists(files[i]);
            ++filesDeleted;
         } catch (Exception var6) {
         }

         files[i] = null;
      }

   }

   private static Path[] getAllRegularFilesInLastModifiedOrder() throws IOException {
      Stream<Path> filesStream = Files.walk(baseCacheDir.toPath());
      Throwable var1 = null;

      Path[] var2;
      try {
         var2 = (Path[])filesStream.filter(new Predicate<Path>() {
            public boolean test(Path path) {
               return !Files.isDirectory(path, new LinkOption[0]);
            }
         }).map(new Function<Path, OptimisticTypesPersistence.PathAndTime>() {
            public OptimisticTypesPersistence.PathAndTime apply(Path path) {
               return new OptimisticTypesPersistence.PathAndTime(path);
            }
         }).sorted().map(new Function<OptimisticTypesPersistence.PathAndTime, Path>() {
            public Path apply(OptimisticTypesPersistence.PathAndTime pathAndTime) {
               return pathAndTime.path;
            }
         }).toArray(new IntFunction<Path[]>() {
            public Path[] apply(int length) {
               return new Path[length];
            }
         });
      } catch (Throwable var11) {
         var1 = var11;
         throw var11;
      } finally {
         if (filesStream != null) {
            if (var1 != null) {
               try {
                  filesStream.close();
               } catch (Throwable var10) {
                  var1.addSuppressed(var10);
               }
            } else {
               filesStream.close();
            }
         }

      }

      return var2;
   }

   private static int getMaxFiles() {
      String str = Options.getStringProperty("nashorn.typeInfo.maxFiles", (String)null);
      if (str == null) {
         return 0;
      } else {
         return "unlimited".equals(str) ? -1 : Math.max(0, Integer.parseInt(str));
      }
   }

   static {
      cacheDir = createCacheDir(baseCacheDir);
      locks = cacheDir == null ? null : createLockArray();
      if (baseCacheDir != null && MAX_FILES != -1) {
         scheduledCleanup = new AtomicBoolean();
         cleanupTimer = new Timer(true);
      } else {
         scheduledCleanup = null;
         cleanupTimer = null;
      }

   }

   private static class PathAndTime implements Comparable<OptimisticTypesPersistence.PathAndTime> {
      private final Path path;
      private final long time;

      PathAndTime(Path path) {
         this.path = path;
         this.time = getTime(path);
      }

      public int compareTo(OptimisticTypesPersistence.PathAndTime other) {
         return Long.compare(this.time, other.time);
      }

      private static long getTime(Path path) {
         try {
            return Files.getLastModifiedTime(path).toMillis();
         } catch (IOException var2) {
            return -1L;
         }
      }
   }

   private static final class LocationDescriptor {
      private final File file;

      LocationDescriptor(File file) {
         this.file = file;
      }
   }
}
