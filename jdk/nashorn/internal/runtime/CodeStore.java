package jdk.nashorn.internal.runtime;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.AccessControlException;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;
import jdk.nashorn.internal.codegen.OptimisticTypesPersistence;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import jdk.nashorn.internal.runtime.logging.Loggable;
import jdk.nashorn.internal.runtime.logging.Logger;
import jdk.nashorn.internal.runtime.options.Options;

@Logger(
   name = "codestore"
)
public abstract class CodeStore implements Loggable {
   public static final String NASHORN_PROVIDE_CODE_STORE = "nashorn.provideCodeStore";
   private DebugLogger log;

   protected CodeStore() {
   }

   public DebugLogger initLogger(Context context) {
      this.log = context.getLogger(this.getClass());
      return this.log;
   }

   public DebugLogger getLogger() {
      return this.log;
   }

   public static CodeStore newCodeStore(Context context) {
      Class baseClass = CodeStore.class;

      try {
         SecurityManager sm = System.getSecurityManager();
         if (sm != null) {
            sm.checkPermission(new RuntimePermission("nashorn.provideCodeStore"));
         }

         ServiceLoader<CodeStore> services = ServiceLoader.load(baseClass);
         Iterator<CodeStore> iterator = services.iterator();
         if (iterator.hasNext()) {
            CodeStore store = (CodeStore)iterator.next();
            store.initLogger(context).info("using code store provider ", store.getClass().getCanonicalName());
            return store;
         }
      } catch (AccessControlException var7) {
         context.getLogger(CodeStore.class).warning("failed to load code store provider ", var7);
      }

      try {
         CodeStore store = new CodeStore.DirectoryCodeStore(context);
         store.initLogger(context);
         return store;
      } catch (IOException var6) {
         context.getLogger(CodeStore.class).warning("failed to create cache directory ", var6);
         return null;
      }
   }

   public StoredScript store(String functionKey, Source source, String mainClassName, Map<String, byte[]> classBytes, Map<Integer, FunctionInitializer> initializers, Object[] constants, int compilationId) {
      return this.store(functionKey, source, this.storedScriptFor(source, mainClassName, classBytes, initializers, constants, compilationId));
   }

   public abstract StoredScript store(String var1, Source var2, StoredScript var3);

   public abstract StoredScript load(Source var1, String var2);

   public StoredScript storedScriptFor(Source source, String mainClassName, Map<String, byte[]> classBytes, Map<Integer, FunctionInitializer> initializers, Object[] constants, int compilationId) {
      Object[] var7 = constants;
      int var8 = constants.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         Object constant = var7[var9];
         if (!(constant instanceof Serializable)) {
            this.getLogger().warning("cannot store ", source, " non serializable constant ", constant);
            return null;
         }
      }

      return new StoredScript(compilationId, mainClassName, classBytes, initializers, constants);
   }

   public static String getCacheKey(Object functionId, Type[] paramTypes) {
      StringBuilder b = (new StringBuilder()).append(functionId);
      if (paramTypes != null && paramTypes.length > 0) {
         b.append('-');
         Type[] var3 = paramTypes;
         int var4 = paramTypes.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Type t = var3[var5];
            b.append(Type.getShortSignatureDescriptor(t));
         }
      }

      return b.toString();
   }

   public static class DirectoryCodeStore extends CodeStore {
      private static final int DEFAULT_MIN_SIZE = 1000;
      private final File dir;
      private final boolean readOnly;
      private final int minSize;

      public DirectoryCodeStore(Context context) throws IOException {
         this(context, Options.getStringProperty("nashorn.persistent.code.cache", "nashorn_code_cache"), false, 1000);
      }

      public DirectoryCodeStore(Context context, String path, boolean readOnly, int minSize) throws IOException {
         this.dir = checkDirectory(path, context.getEnv(), readOnly);
         this.readOnly = readOnly;
         this.minSize = minSize;
      }

      private static File checkDirectory(final String path, final ScriptEnvironment env, final boolean readOnly) throws IOException {
         try {
            return (File)AccessController.doPrivileged(new PrivilegedExceptionAction<File>() {
               public File run() throws IOException {
                  File dir = (new File(path, CodeStore.DirectoryCodeStore.getVersionDir(env))).getAbsoluteFile();
                  if (readOnly) {
                     if (!dir.exists() || !dir.isDirectory()) {
                        throw new IOException("Not a directory: " + dir.getPath());
                     }

                     if (!dir.canRead()) {
                        throw new IOException("Directory not readable: " + dir.getPath());
                     }
                  } else {
                     if (!dir.exists() && !dir.mkdirs()) {
                        throw new IOException("Could not create directory: " + dir.getPath());
                     }

                     if (!dir.isDirectory()) {
                        throw new IOException("Not a directory: " + dir.getPath());
                     }

                     if (!dir.canRead() || !dir.canWrite()) {
                        throw new IOException("Directory not readable or writable: " + dir.getPath());
                     }
                  }

                  return dir;
               }
            });
         } catch (PrivilegedActionException var4) {
            throw (IOException)var4.getException();
         }
      }

      private static String getVersionDir(ScriptEnvironment env) throws IOException {
         try {
            String versionDir = OptimisticTypesPersistence.getVersionDirName();
            return env._optimistic_types ? versionDir + "_opt" : versionDir;
         } catch (Exception var2) {
            throw new IOException(var2);
         }
      }

      public StoredScript load(final Source source, final String functionKey) {
         if (this.belowThreshold(source)) {
            return null;
         } else {
            final File file = this.getCacheFile(source, functionKey);

            try {
               return (StoredScript)AccessController.doPrivileged(new PrivilegedExceptionAction<StoredScript>() {
                  public StoredScript run() throws IOException, ClassNotFoundException {
                     if (!file.exists()) {
                        return null;
                     } else {
                        ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
                        Throwable var2 = null;

                        StoredScript var4;
                        try {
                           StoredScript storedScript = (StoredScript)in.readObject();
                           DirectoryCodeStore.this.getLogger().info("loaded ", source, "-", functionKey);
                           var4 = storedScript;
                        } catch (Throwable var13) {
                           var2 = var13;
                           throw var13;
                        } finally {
                           if (in != null) {
                              if (var2 != null) {
                                 try {
                                    in.close();
                                 } catch (Throwable var12) {
                                    var2.addSuppressed(var12);
                                 }
                              } else {
                                 in.close();
                              }
                           }

                        }

                        return var4;
                     }
                  }
               });
            } catch (PrivilegedActionException var5) {
               this.getLogger().warning("failed to load ", source, "-", functionKey, ": ", var5.getException());
               return null;
            }
         }
      }

      public StoredScript store(final String functionKey, final Source source, final StoredScript script) {
         if (!this.readOnly && script != null && !this.belowThreshold(source)) {
            final File file = this.getCacheFile(source, functionKey);

            try {
               return (StoredScript)AccessController.doPrivileged(new PrivilegedExceptionAction<StoredScript>() {
                  public StoredScript run() throws IOException {
                     ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
                     Throwable var2 = null;

                     try {
                        out.writeObject(script);
                     } catch (Throwable var11) {
                        var2 = var11;
                        throw var11;
                     } finally {
                        if (out != null) {
                           if (var2 != null) {
                              try {
                                 out.close();
                              } catch (Throwable var10) {
                                 var2.addSuppressed(var10);
                              }
                           } else {
                              out.close();
                           }
                        }

                     }

                     DirectoryCodeStore.this.getLogger().info("stored ", source, "-", functionKey);
                     return script;
                  }
               });
            } catch (PrivilegedActionException var6) {
               this.getLogger().warning("failed to store ", script, "-", functionKey, ": ", var6.getException());
               return null;
            }
         } else {
            return null;
         }
      }

      private File getCacheFile(Source source, String functionKey) {
         return new File(this.dir, source.getDigest() + '-' + functionKey);
      }

      private boolean belowThreshold(Source source) {
         if (source.getLength() < this.minSize) {
            this.getLogger().info("below size threshold ", source);
            return true;
         } else {
            return false;
         }
      }
   }
}
