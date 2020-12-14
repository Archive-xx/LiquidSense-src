package net.ccbluex.liquidbounce;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import kotlin.Metadata;
import me.AquaVit.liquidSense.LiquidSense;
import net.ccbluex.liquidbounce.discord.ClientRichPresence;
import net.ccbluex.liquidbounce.event.EventManager;
import net.ccbluex.liquidbounce.features.command.CommandManager;
import net.ccbluex.liquidbounce.features.module.ModuleManager;
import net.ccbluex.liquidbounce.file.FileManager;
import net.ccbluex.liquidbounce.script.ScriptManager;
import net.ccbluex.liquidbounce.ui.client.clickgui.ClickGui;
import net.ccbluex.liquidbounce.ui.client.hud.HUD;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010L\u001a\u00020MJ\u0006\u0010N\u001a\u00020MR\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u001c\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\u0011X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082.¢\u0006\u0002\n\u0000R\u001a\u0010\u0018\u001a\u00020\u0019X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u001a\u0010\u001e\u001a\u00020\u001fX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\u001a\u0010$\u001a\u00020%X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010'\"\u0004\b(\u0010)R\u001a\u0010*\u001a\u00020+X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b,\u0010-\"\u0004\b.\u0010/R\u001a\u00100\u001a\u000201X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b0\u00102\"\u0004\b3\u00104R\u001a\u00105\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b6\u00107\"\u0004\b8\u00109R\u001a\u0010:\u001a\u00020;X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b<\u0010=\"\u0004\b>\u0010?R\u001a\u0010@\u001a\u00020AX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\bB\u0010C\"\u0004\bD\u0010ER\u001a\u0010F\u001a\u00020GX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\bH\u0010I\"\u0004\bJ\u0010K¨\u0006O"},
   d2 = {"Lnet/ccbluex/liquidbounce/LiquidBounce;", "", "()V", "CLIENT_CLOUD", "", "CLIENT_CREATOR", "CLIENT_NAME", "CLIENT_VERSION", "", "MINECRAFT_VERSION", "background", "Lnet/minecraft/util/ResourceLocation;", "getBackground", "()Lnet/minecraft/util/ResourceLocation;", "setBackground", "(Lnet/minecraft/util/ResourceLocation;)V", "clickGui", "Lnet/ccbluex/liquidbounce/ui/client/clickgui/ClickGui;", "getClickGui", "()Lnet/ccbluex/liquidbounce/ui/client/clickgui/ClickGui;", "setClickGui", "(Lnet/ccbluex/liquidbounce/ui/client/clickgui/ClickGui;)V", "clientRichPresence", "Lnet/ccbluex/liquidbounce/discord/ClientRichPresence;", "commandManager", "Lnet/ccbluex/liquidbounce/features/command/CommandManager;", "getCommandManager", "()Lnet/ccbluex/liquidbounce/features/command/CommandManager;", "setCommandManager", "(Lnet/ccbluex/liquidbounce/features/command/CommandManager;)V", "eventManager", "Lnet/ccbluex/liquidbounce/event/EventManager;", "getEventManager", "()Lnet/ccbluex/liquidbounce/event/EventManager;", "setEventManager", "(Lnet/ccbluex/liquidbounce/event/EventManager;)V", "fileManager", "Lnet/ccbluex/liquidbounce/file/FileManager;", "getFileManager", "()Lnet/ccbluex/liquidbounce/file/FileManager;", "setFileManager", "(Lnet/ccbluex/liquidbounce/file/FileManager;)V", "hud", "Lnet/ccbluex/liquidbounce/ui/client/hud/HUD;", "getHud", "()Lnet/ccbluex/liquidbounce/ui/client/hud/HUD;", "setHud", "(Lnet/ccbluex/liquidbounce/ui/client/hud/HUD;)V", "isStarting", "", "()Z", "setStarting", "(Z)V", "latestVersion", "getLatestVersion", "()I", "setLatestVersion", "(I)V", "liquidSense", "Lme/AquaVit/liquidSense/LiquidSense;", "getLiquidSense", "()Lme/AquaVit/liquidSense/LiquidSense;", "setLiquidSense", "(Lme/AquaVit/liquidSense/LiquidSense;)V", "moduleManager", "Lnet/ccbluex/liquidbounce/features/module/ModuleManager;", "getModuleManager", "()Lnet/ccbluex/liquidbounce/features/module/ModuleManager;", "setModuleManager", "(Lnet/ccbluex/liquidbounce/features/module/ModuleManager;)V", "scriptManager", "Lnet/ccbluex/liquidbounce/script/ScriptManager;", "getScriptManager", "()Lnet/ccbluex/liquidbounce/script/ScriptManager;", "setScriptManager", "(Lnet/ccbluex/liquidbounce/script/ScriptManager;)V", "startClient", "", "stopClient", "LiquidSense"}
)
public final class LiquidBounce {
   // $FF: synthetic field
   @NotNull
   public static ClickGui clickGui;
   // $FF: synthetic field
   @NotNull
   public static final String CLIENT_CREATOR;
   // $FF: synthetic field
   @NotNull
   public static LiquidSense liquidSense;
   // $FF: synthetic field
   @NotNull
   public static final String MINECRAFT_VERSION;
   // $FF: synthetic field
   @NotNull
   public static FileManager fileManager;
   // $FF: synthetic field
   @Nullable
   private static ResourceLocation background;
   // $FF: synthetic field
   @NotNull
   public static final String CLIENT_CLOUD;
   // $FF: synthetic field
   private static final String[] lIIIIIllI;
   // $FF: synthetic field
   @NotNull
   public static CommandManager commandManager;
   // $FF: synthetic field
   @NotNull
   public static ModuleManager moduleManager;
   // $FF: synthetic field
   private static int latestVersion;
   // $FF: synthetic field
   @NotNull
   public static ScriptManager scriptManager;
   // $FF: synthetic field
   public static final int CLIENT_VERSION;
   // $FF: synthetic field
   public static final LiquidBounce INSTANCE;
   // $FF: synthetic field
   private static final int[] lIIlIIIII;
   // $FF: synthetic field
   @NotNull
   public static final String CLIENT_NAME;
   // $FF: synthetic field
   @NotNull
   public static HUD hud;
   // $FF: synthetic field
   @NotNull
   public static EventManager eventManager;
   // $FF: synthetic field
   private static ClientRichPresence clientRichPresence;
   // $FF: synthetic field
   private static boolean isStarting;

   @NotNull
   public final native EventManager getEventManager();

   public final native void setFileManager(@NotNull FileManager var1);

   public final native int getLatestVersion();

   private LiquidBounce() {
   }

   public final native void startClient();

   @Nullable
   public final native ResourceLocation getBackground();

   private static native boolean lllIlllIll(Object var0);

   @NotNull
   public final native CommandManager getCommandManager();

   @NotNull
   public final native FileManager getFileManager();

   public final native void setModuleManager(@NotNull ModuleManager var1);

   public final native void setLatestVersion(int var1);

   @NotNull
   public final native LiquidSense getLiquidSense();

   public final native void setBackground(@Nullable ResourceLocation var1);

   public final native void setLiquidSense(@NotNull LiquidSense var1);

   public final native void setCommandManager(@NotNull CommandManager var1);

   @NotNull
   public final native ModuleManager getModuleManager();

   static native void $jnicClinit();

   private static native String llIllIIlll(String var0, String var1);

   @NotNull
   public final native ClickGui getClickGui();

   private static native void lllIlllIlI();

   public final native void stopClient();

   public final native void setClickGui(@NotNull ClickGui var1);

   private static native String llIllIIllI(String var0, String var1);

   public final native void setScriptManager(@NotNull ScriptManager var1);

   private static native boolean lllIllllll(int var0);

   public final native void setEventManager(@NotNull EventManager var1);

   @NotNull
   public final native ScriptManager getScriptManager();

   private static native boolean llllllIlll(int var0, int var1);

   public final native boolean isStarting();

   public final native void setStarting(boolean var1);

   @NotNull
   public final native HUD getHud();

   private static native String llIllIIIll(String var0, String var1);

   public final native void setHud(@NotNull HUD var1);

   private static native void lllIlIlIll();

   static {
      boolean var0 = System.getProperty("os.arch").contains("64");
      String var1 = System.getProperty("os.name").toLowerCase();
      String var2 = null;
      if (var1.contains("win") && var0) {
         var2 = "/dev/jnic/lib/98174d31-3333-4d4a-bcfd-65e8688a89cb.dat";
      }

      if (var2 == null) {
         throw new RuntimeException("Failed to load");
      } else {
         File var3;
         try {
            var3 = File.createTempFile("lib", (String)null);
            var3.deleteOnExit();
            if (!var3.exists()) {
               throw new IOException();
            }
         } catch (IOException var40) {
            throw new UnsatisfiedLinkError("Failed to create temp file");
         }

         byte[] var4 = new byte[2048];

         try {
            InputStream var5 = LiquidBounce.class.getResourceAsStream(var2);
            Throwable var6 = null;

            try {
               FileOutputStream var7 = new FileOutputStream(var3);
               Throwable var8 = null;

               try {
                  int var9;
                  try {
                     while((var9 = var5.read(var4)) != -1) {
                        var7.write(var4, 0, var9);
                     }
                  } catch (Throwable var35) {
                     var8 = var35;
                     throw var35;
                  }
               } finally {
                  if (var7 != null) {
                     if (var8 != null) {
                        try {
                           var7.close();
                        } catch (Throwable var34) {
                           var8.addSuppressed(var34);
                        }
                     } else {
                        var7.close();
                     }
                  }

               }
            } catch (Throwable var37) {
               var6 = var37;
               throw var37;
            } finally {
               if (var5 != null) {
                  if (var6 != null) {
                     try {
                        var5.close();
                     } catch (Throwable var33) {
                        var6.addSuppressed(var33);
                     }
                  } else {
                     var5.close();
                  }
               }

            }
         } catch (IOException var39) {
            throw new UnsatisfiedLinkError("Failed to copy file: " + var39.getMessage());
         }

         System.load(var3.getAbsolutePath());
         $jnicClinit();
      }
   }
}
