package net.ccbluex.liquidbounce.script;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u000b\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0007J\u0006\u0010\u0011\u001a\u00020\u000fJ\u0006\u0010\u0012\u001a\u00020\u000fJ\u000e\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u000bJ\u000e\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\u000bJ\u0006\u0010\u0017\u001a\u00020\u000fJ\u0006\u0010\u0018\u001a\u00020\u000fJ\u0006\u0010\u0019\u001a\u00020\u000fR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u001a"},
   d2 = {"Lnet/ccbluex/liquidbounce/script/ScriptManager;", "", "()V", "scriptFileExtension", "", "scripts", "", "Lnet/ccbluex/liquidbounce/script/Script;", "getScripts", "()Ljava/util/List;", "scriptsFolder", "Ljava/io/File;", "getScriptsFolder", "()Ljava/io/File;", "deleteScript", "", "script", "disableScripts", "enableScripts", "importScript", "file", "loadScript", "scriptFile", "loadScripts", "reloadScripts", "unloadScripts", "LiquidSense"}
)
public final class ScriptManager {
   // $FF: synthetic field
   @NotNull
   private final File scriptsFolder;
   // $FF: synthetic field
   @NotNull
   private final List<Script> scripts;
   // $FF: synthetic field
   private final String scriptFileExtension;

   public final void unloadScripts() {
      lIlIllllIlIIl.scripts.clear();
   }

   public final void reloadScripts() {
      lIlIllIlIIllI.disableScripts();
      lIlIllIlIIllI.unloadScripts();
      lIlIllIlIIllI.loadScripts();
      lIlIllIlIIllI.enableScripts();
      ClientUtils.getLogger().info("Successfully reloaded scripts.");
   }

   public final void disableScripts() {
      Iterable lIlIlllIIIIII = (Iterable)lIlIllIllllIl.scripts;
      int lIlIllIllllll = false;
      Iterator lIlIllIlllIlI = lIlIlllIIIIII.iterator();

      while(lIlIllIlllIlI.hasNext()) {
         short lIlIllIlllIIl = lIlIllIlllIlI.next();
         char lIlIllIlllIII = (Script)lIlIllIlllIIl;
         int lIlIlllIIIIlI = false;
         lIlIllIlllIII.onDisable();
      }

   }

   public final void importScript(@NotNull File lIlIllIllIIIl) {
      Intrinsics.checkParameterIsNotNull(lIlIllIllIIIl, "file");
      byte lIlIllIlIlllI = new File(lIlIllIllIIlI.scriptsFolder, lIlIllIllIIIl.getName());
      FilesKt.copyTo$default(lIlIllIllIIIl, lIlIllIlIlllI, false, 0, 6, (Object)null);
      boolean var10001 = false;
      lIlIllIllIIlI.loadScript(lIlIllIlIlllI);
      ClientUtils.getLogger().info(String.valueOf((new StringBuilder()).append("Successfully imported script: ").append(lIlIllIlIlllI.getName())));
   }

   public final void loadScripts() {
      if (!lIlIlllllIIll.scriptsFolder.exists()) {
         lIlIlllllIIll.scriptsFolder.mkdir();
         boolean var10001 = false;
      }

      File[] var10000 = lIlIlllllIIll.scriptsFolder.listFiles((FileFilter)(new FileFilter() {
         public final boolean accept(File llllllllllllllllllllIIlIIlIllIIl) {
            Intrinsics.checkExpressionValueIsNotNull(llllllllllllllllllllIIlIIlIllIIl, "it");
            String var10000 = llllllllllllllllllllIIlIIlIllIIl.getName();
            Intrinsics.checkExpressionValueIsNotNull(var10000, "it.name");
            return StringsKt.endsWith$default(var10000, lIlIlllllIIll.scriptFileExtension, false, 2, (Object)null);
         }
      }));
      Intrinsics.checkExpressionValueIsNotNull(var10000, "scriptsFolder.listFiles(…h(scriptFileExtension) })");
      boolean lIlIlllllIIlI = var10000;
      int lIlIlllllIlIl = false;
      float lIlIlllllIIII = lIlIlllllIIlI;
      char lIlIllllIllll = lIlIlllllIIlI.length;

      for(int lIlIllllIlllI = 0; lIlIllllIlllI < lIlIllllIllll; ++lIlIllllIlllI) {
         Object lIlIlllllIlll = lIlIlllllIIII[lIlIllllIlllI];
         long lIlIllllIlIll = false;
         Intrinsics.checkExpressionValueIsNotNull(lIlIlllllIlll, "it");
         lIlIlllllIIll.loadScript(lIlIlllllIlll);
      }

   }

   public ScriptManager() {
      int lIlIllIIllllI = false;
      double lIlIllIIlllII = (List)(new ArrayList());
      lIlIllIlIIIII.scripts = lIlIllIIlllII;
      lIlIllIlIIIII.scriptsFolder = new File(LiquidBounce.INSTANCE.getFileManager().dir, "scripts");
      lIlIllIlIIIII.scriptFileExtension = ".js";
   }

   public final void loadScript(@NotNull File lIlIllllIIIII) {
      Intrinsics.checkParameterIsNotNull(lIlIllllIIIII, "scriptFile");

      try {
         lIlIllllIIIIl.scripts.add(new Script(lIlIllllIIIII));
         boolean var10001 = false;
         ClientUtils.getLogger().info(String.valueOf((new StringBuilder()).append("Successfully loaded script: ").append(lIlIllllIIIII.getName())));
      } catch (Throwable var3) {
         ClientUtils.getLogger().error(String.valueOf((new StringBuilder()).append("Failed to load script: ").append(lIlIllllIIIII.getName())), var3);
      }

   }

   @NotNull
   public final File getScriptsFolder() {
      return lIllIIIIIIlII.scriptsFolder;
   }

   public final void deleteScript(@NotNull Script lIlIllIlIlIlI) {
      Intrinsics.checkParameterIsNotNull(lIlIllIlIlIlI, "script");
      lIlIllIlIlIlI.onDisable();
      lIlIllIlIlIll.scripts.remove(lIlIllIlIlIlI);
      boolean var10001 = false;
      lIlIllIlIlIlI.getScriptFile().delete();
      var10001 = false;
      ClientUtils.getLogger().info(String.valueOf((new StringBuilder()).append("Successfully deleted script: ").append(lIlIllIlIlIlI.getScriptFile().getName())));
   }

   public final void enableScripts() {
      Iterable lIlIlllIlIlII = (Iterable)lIlIlllIlIIIl.scripts;
      int lIlIlllIlIIll = false;
      Iterator lIlIlllIIlllI = lIlIlllIlIlII.iterator();

      while(lIlIlllIIlllI.hasNext()) {
         float lIlIlllIIllIl = lIlIlllIIlllI.next();
         Script lIlIlllIlIlll = (Script)lIlIlllIIllIl;
         Exception lIlIlllIIlIll = false;
         lIlIlllIlIlll.onEnable();
      }

   }

   @NotNull
   public final List<Script> getScripts() {
      return lIllIIIIIIlll.scripts;
   }
}
