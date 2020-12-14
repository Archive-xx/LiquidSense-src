package jdk.nashorn.internal.runtime;

import java.util.Collection;
import java.util.Map;

public interface CodeInstaller {
   Context getContext();

   Class<?> install(String var1, byte[] var2);

   void initialize(Collection<Class<?>> var1, Source var2, Object[] var3);

   void verify(byte[] var1);

   long getUniqueScriptId();

   void storeScript(String var1, Source var2, String var3, Map<String, byte[]> var4, Map<Integer, FunctionInitializer> var5, Object[] var6, int var7);

   StoredScript loadScript(Source var1, String var2);

   CodeInstaller withNewLoader();

   boolean isCompatibleWith(CodeInstaller var1);
}
