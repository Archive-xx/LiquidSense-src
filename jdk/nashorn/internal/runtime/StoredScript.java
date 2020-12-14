package jdk.nashorn.internal.runtime;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public final class StoredScript implements Serializable {
   private final int compilationId;
   private final String mainClassName;
   private final Map<String, byte[]> classBytes;
   private final Object[] constants;
   private final Map<Integer, FunctionInitializer> initializers;
   private static final long serialVersionUID = 2958227232195298340L;

   public StoredScript(int compilationId, String mainClassName, Map<String, byte[]> classBytes, Map<Integer, FunctionInitializer> initializers, Object[] constants) {
      this.compilationId = compilationId;
      this.mainClassName = mainClassName;
      this.classBytes = classBytes;
      this.constants = constants;
      this.initializers = initializers;
   }

   public int getCompilationId() {
      return this.compilationId;
   }

   private Map<String, Class<?>> installClasses(Source source, CodeInstaller installer) {
      Map<String, Class<?>> installedClasses = new HashMap();
      byte[] mainClassBytes = (byte[])this.classBytes.get(this.mainClassName);
      Class<?> mainClass = installer.install(this.mainClassName, mainClassBytes);
      installedClasses.put(this.mainClassName, mainClass);
      Iterator var6 = this.classBytes.entrySet().iterator();

      while(var6.hasNext()) {
         Entry<String, byte[]> entry = (Entry)var6.next();
         String className = (String)entry.getKey();
         if (!className.equals(this.mainClassName)) {
            installedClasses.put(className, installer.install(className, (byte[])entry.getValue()));
         }
      }

      installer.initialize(installedClasses.values(), source, this.constants);
      return installedClasses;
   }

   FunctionInitializer installFunction(RecompilableScriptFunctionData data, CodeInstaller installer) {
      Map<String, Class<?>> installedClasses = this.installClasses(data.getSource(), installer);

      assert this.initializers != null;

      assert this.initializers.size() == 1;

      FunctionInitializer initializer = (FunctionInitializer)this.initializers.values().iterator().next();

      for(int i = 0; i < this.constants.length; ++i) {
         if (this.constants[i] instanceof RecompilableScriptFunctionData) {
            RecompilableScriptFunctionData newData = data.getScriptFunctionData(((RecompilableScriptFunctionData)this.constants[i]).getFunctionNodeId());

            assert newData != null;

            newData.initTransients(data.getSource(), installer);
            this.constants[i] = newData;
         }
      }

      initializer.setCode((Class)installedClasses.get(initializer.getClassName()));
      return initializer;
   }

   Class<?> installScript(Source source, CodeInstaller installer) {
      Map<String, Class<?>> installedClasses = this.installClasses(source, installer);
      Object[] var4 = this.constants;
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Object constant = var4[var6];
         if (constant instanceof RecompilableScriptFunctionData) {
            RecompilableScriptFunctionData data = (RecompilableScriptFunctionData)constant;
            data.initTransients(source, installer);
            FunctionInitializer initializer = (FunctionInitializer)this.initializers.get(data.getFunctionNodeId());
            if (initializer != null) {
               initializer.setCode((Class)installedClasses.get(initializer.getClassName()));
               data.initializeCode(initializer);
            }
         }
      }

      return (Class)installedClasses.get(this.mainClassName);
   }

   public int hashCode() {
      int hash = this.mainClassName.hashCode();
      hash = 31 * hash + this.classBytes.hashCode();
      hash = 31 * hash + Arrays.hashCode(this.constants);
      return hash;
   }

   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (!(obj instanceof StoredScript)) {
         return false;
      } else {
         StoredScript cs = (StoredScript)obj;
         return this.mainClassName.equals(cs.mainClassName) && this.classBytes.equals(cs.classBytes) && Arrays.equals(this.constants, cs.constants);
      }
   }
}
