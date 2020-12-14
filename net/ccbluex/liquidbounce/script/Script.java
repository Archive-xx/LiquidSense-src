package net.ccbluex.liquidbounce.script;

import java.io.File;
import java.nio.charset.Charset;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import jdk.internal.dynalink.beans.StaticClass;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.script.api.Chat;
import net.ccbluex.liquidbounce.script.api.CommandManager;
import net.ccbluex.liquidbounce.script.api.CreativeTab;
import net.ccbluex.liquidbounce.script.api.Item;
import net.ccbluex.liquidbounce.script.api.ModuleManager;
import net.ccbluex.liquidbounce.script.api.Value;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0006\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\bH\u0002J\u000e\u0010\u001f\u001a\u00020\u001d2\u0006\u0010\u0002\u001a\u00020\bJ\u0006\u0010 \u001a\u00020\u001dJ\u0006\u0010!\u001a\u00020\u001dJ\u0006\u0010\"\u001a\u00020\u001dJ\u0006\u0010#\u001a\u00020\u001dR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u00020\bX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\u000eX\u0082.¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\bX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\n\"\u0004\b\u0013\u0010\fR\u001a\u0010\u0014\u001a\u00020\u0015X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006$"},
   d2 = {"Lnet/ccbluex/liquidbounce/script/Script;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "scriptFile", "Ljava/io/File;", "(Ljava/io/File;)V", "invocable", "Ljavax/script/Invocable;", "scriptAuthor", "", "getScriptAuthor", "()Ljava/lang/String;", "setScriptAuthor", "(Ljava/lang/String;)V", "scriptEngine", "Ljavax/script/ScriptEngine;", "getScriptFile", "()Ljava/io/File;", "scriptName", "getScriptName", "setScriptName", "scriptVersion", "", "getScriptVersion", "()D", "setScriptVersion", "(D)V", "state", "", "callFunction", "", "functionName", "import", "loadScript", "onDisable", "onEnable", "onLoad", "LiquidSense"}
)
public final class Script extends MinecraftInstance {
   // $FF: synthetic field
   @NotNull
   public String scriptName;
   // $FF: synthetic field
   @NotNull
   public String scriptAuthor;
   // $FF: synthetic field
   private boolean state;
   // $FF: synthetic field
   private Invocable invocable;
   // $FF: synthetic field
   @NotNull
   private final File scriptFile;
   // $FF: synthetic field
   private double scriptVersion;
   // $FF: synthetic field
   private ScriptEngine scriptEngine;

   public final void setScriptAuthor(@NotNull String lIlIlIIIlIIll) {
      Intrinsics.checkParameterIsNotNull(lIlIlIIIlIIll, "<set-?>");
      lIlIlIIIlIlII.scriptAuthor = lIlIlIIIlIIll;
   }

   public final void onEnable() {
      if (!lIlIlIIIIIlIl.state) {
         lIlIlIIIIIlIl.callFunction("onEnable");
         lIlIlIIIIIlIl.state = true;
      }
   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   @NotNull
   public final String getScriptName() {
      String var10000 = lIlIlIIlIlIlI.scriptName;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("scriptName");
      }

      return var10000;
   }

   private final void callFunction(String lIlIIllllIllI) {
      try {
         Invocable var10000 = lIlIIllllIlll.invocable;
         if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("invocable");
         }

         var10000.invokeFunction(lIlIIllllIllI);
         boolean var10001 = false;
      } catch (NoSuchMethodException var4) {
      } catch (Exception var5) {
         ClientUtils.getLogger().error(String.valueOf((new StringBuilder()).append(lIlIIllllIlll.scriptFile.getName()).append(" caused an error.")), (Throwable)var5);
      }

   }

   public final void onLoad() {
      lIlIlIIIIlIII.callFunction("onLoad");
   }

   public final void loadScript() {
      ScriptEngine var10001 = (new ScriptEngineManager()).getEngineByName("nashorn");
      Intrinsics.checkExpressionValueIsNotNull(var10001, "ScriptEngineManager().getEngineByName(\"nashorn\")");
      lIlIlIIIIllIl.scriptEngine = var10001;
      ScriptEngine var10000 = lIlIlIIIIllIl.scriptEngine;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("scriptEngine");
      }

      var10000.put("mc", access$getMc$p$s1046033730());
      var10000 = lIlIlIIIIllIl.scriptEngine;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("scriptEngine");
      }

      var10000.put("scriptManager", LiquidBounce.INSTANCE.getScriptManager());
      var10000 = lIlIlIIIIllIl.scriptEngine;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("scriptEngine");
      }

      var10000.put("script", lIlIlIIIIllIl);
      var10000 = lIlIlIIIIllIl.scriptEngine;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("scriptEngine");
      }

      var10000.put("commandManager", StaticClass.forClass(CommandManager.INSTANCE.getClass()));
      var10000 = lIlIlIIIIllIl.scriptEngine;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("scriptEngine");
      }

      var10000.put("moduleManager", StaticClass.forClass(ModuleManager.INSTANCE.getClass()));
      var10000 = lIlIlIIIIllIl.scriptEngine;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("scriptEngine");
      }

      var10000.put("creativeTabs", StaticClass.forClass(CreativeTab.INSTANCE.getClass()));
      var10000 = lIlIlIIIIllIl.scriptEngine;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("scriptEngine");
      }

      var10000.put("item", StaticClass.forClass(Item.INSTANCE.getClass()));
      var10000 = lIlIlIIIIllIl.scriptEngine;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("scriptEngine");
      }

      var10000.put("value", StaticClass.forClass(Value.INSTANCE.getClass()));
      var10000 = lIlIlIIIIllIl.scriptEngine;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("scriptEngine");
      }

      var10000.put("chat", StaticClass.forClass(Chat.INSTANCE.getClass()));
      int lIlIlIIIIlIll = FilesKt.readText$default(lIlIlIIIIllIl.scriptFile, (Charset)null, 1, (Object)null);
      var10000 = lIlIlIIIIllIl.scriptEngine;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("scriptEngine");
      }

      var10000.eval(lIlIlIIIIlIll);
      boolean var2 = false;
      var10001 = lIlIlIIIIllIl.scriptEngine;
      if (var10001 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("scriptEngine");
      }

      if (var10001 == null) {
         throw new TypeCastException("null cannot be cast to non-null type javax.script.Invocable");
      } else {
         lIlIlIIIIllIl.invocable = (Invocable)var10001;
         var10001 = lIlIlIIIIllIl.scriptEngine;
         if (var10001 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("scriptEngine");
         }

         Object var3 = var10001.get("scriptName");
         if (var3 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
         } else {
            lIlIlIIIIllIl.scriptName = (String)var3;
            var10001 = lIlIlIIIIllIl.scriptEngine;
            if (var10001 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("scriptEngine");
            }

            var3 = var10001.get("scriptVersion");
            if (var3 == null) {
               throw new TypeCastException("null cannot be cast to non-null type kotlin.Double");
            } else {
               lIlIlIIIIllIl.scriptVersion = (Double)var3;
               var10001 = lIlIlIIIIllIl.scriptEngine;
               if (var10001 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("scriptEngine");
               }

               var3 = var10001.get("scriptAuthor");
               if (var3 == null) {
                  throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
               } else {
                  lIlIlIIIIllIl.scriptAuthor = (String)var3;
                  lIlIlIIIIllIl.onLoad();
               }
            }
         }
      }
   }

   public final void setScriptVersion(double lIlIlIIIlllII) {
      lIlIlIIIlllIl.scriptVersion = lIlIlIIIlllII;
   }

   @NotNull
   public final File getScriptFile() {
      return lIlIIllllIIIl.scriptFile;
   }

   @NotNull
   public final String getScriptAuthor() {
      String var10000 = lIlIlIIIllIII.scriptAuthor;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("scriptAuthor");
      }

      return var10000;
   }

   public final void import(@NotNull String lIlIIlllllllI) {
      Intrinsics.checkParameterIsNotNull(lIlIIlllllllI, "scriptFile");
      ScriptEngine var10000 = lIlIIllllllIl.scriptEngine;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("scriptEngine");
      }

      var10000.eval(FilesKt.readText$default(new File(LiquidBounce.INSTANCE.getScriptManager().getScriptsFolder(), lIlIIlllllllI), (Charset)null, 1, (Object)null));
      boolean var10001 = false;
   }

   public Script(@NotNull File lIlIIlllIllII) {
      Intrinsics.checkParameterIsNotNull(lIlIIlllIllII, "scriptFile");
      super();
      lIlIIlllIlIll.scriptFile = lIlIIlllIllII;
      lIlIIlllIlIll.scriptVersion = 1.0D;
      lIlIIlllIlIll.loadScript();
   }

   public final void setScriptName(@NotNull String lIlIlIIlIIlIl) {
      Intrinsics.checkParameterIsNotNull(lIlIlIIlIIlIl, "<set-?>");
      lIlIlIIlIIlII.scriptName = lIlIlIIlIIlIl;
   }

   public final double getScriptVersion() {
      return lIlIlIIlIIIII.scriptVersion;
   }

   public final void onDisable() {
      if (lIlIlIIIIIIll.state) {
         lIlIlIIIIIIll.callFunction("onDisable");
         lIlIlIIIIIIll.state = false;
      }
   }
}
