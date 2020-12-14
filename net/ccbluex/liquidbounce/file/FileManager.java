//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.file;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import javax.imageio.ImageIO;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.file.configs.AccountsConfig;
import net.ccbluex.liquidbounce.file.configs.ClickGuiConfig;
import net.ccbluex.liquidbounce.file.configs.FriendsConfig;
import net.ccbluex.liquidbounce.file.configs.HudConfig;
import net.ccbluex.liquidbounce.file.configs.ModulesConfig;
import net.ccbluex.liquidbounce.file.configs.ShortcutsConfig;
import net.ccbluex.liquidbounce.file.configs.ValuesConfig;
import net.ccbluex.liquidbounce.file.configs.XRayConfig;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class FileManager extends MinecraftInstance {
   // $FF: synthetic field
   public final File dir;
   // $FF: synthetic field
   public final FileConfig valuesConfig;
   // $FF: synthetic field
   public final FriendsConfig friendsConfig;
   // $FF: synthetic field
   public final FileConfig hudConfig;
   // $FF: synthetic field
   public boolean firstStart;
   // $FF: synthetic field
   public final FileConfig clickGuiConfig;
   // $FF: synthetic field
   public final AccountsConfig accountsConfig;
   // $FF: synthetic field
   public final File fontsDir;
   // $FF: synthetic field
   public final FileConfig shortcutsConfig;
   // $FF: synthetic field
   public final File backgroundFile;
   // $FF: synthetic field
   public final FileConfig modulesConfig;
   // $FF: synthetic field
   public final FileConfig xrayConfig;
   // $FF: synthetic field
   public final File settingsDir;
   // $FF: synthetic field
   public static final Gson PRETTY_GSON = (new GsonBuilder()).setPrettyPrinting().create();

   public void saveConfig(FileConfig llllllllllllllllllIIIlIllllIllII) {
      llllllllllllllllllIIIlIllllIlllI.saveConfig(llllllllllllllllllIIIlIllllIllII, false);
   }

   public void loadConfig(FileConfig llllllllllllllllllIIIllIIIIllIlI) {
      if (!llllllllllllllllllIIIllIIIIllIlI.hasConfig()) {
         ClientUtils.getLogger().info(String.valueOf((new StringBuilder()).append("[FileManager] Skipped loading config: ").append(llllllllllllllllllIIIllIIIIllIlI.getFile().getName()).append(".")));
         llllllllllllllllllIIIllIIIIlllIl.saveConfig(llllllllllllllllllIIIllIIIIllIlI, true);
      } else {
         try {
            llllllllllllllllllIIIllIIIIllIlI.loadConfig();
            ClientUtils.getLogger().info(String.valueOf((new StringBuilder()).append("[FileManager] Loaded config: ").append(llllllllllllllllllIIIllIIIIllIlI.getFile().getName()).append(".")));
         } catch (Throwable var4) {
            ClientUtils.getLogger().error(String.valueOf((new StringBuilder()).append("[FileManager] Failed to load config file: ").append(llllllllllllllllllIIIllIIIIllIlI.getFile().getName()).append(".")), var4);
         }

      }
   }

   private void saveConfig(FileConfig llllllllllllllllllIIIlIlllIlllII, boolean llllllllllllllllllIIIlIlllIllIll) {
      if (llllllllllllllllllIIIlIlllIllIll || !LiquidBounce.INSTANCE.isStarting()) {
         try {
            if (!llllllllllllllllllIIIlIlllIlllII.hasConfig()) {
               llllllllllllllllllIIIlIlllIlllII.createConfig();
            }

            llllllllllllllllllIIIlIlllIlllII.saveConfig();
            ClientUtils.getLogger().info(String.valueOf((new StringBuilder()).append("[FileManager] Saved config: ").append(llllllllllllllllllIIIlIlllIlllII.getFile().getName()).append(".")));
         } catch (Throwable var4) {
            ClientUtils.getLogger().error(String.valueOf((new StringBuilder()).append("[FileManager] Failed to save config file: ").append(llllllllllllllllllIIIlIlllIlllII.getFile().getName()).append(".")), var4);
         }

      }
   }

   public void saveAllConfigs() {
      byte llllllllllllllllllIIIllIIIIIllIl = llllllllllllllllllIIIllIIIIIllll.getClass().getDeclaredFields();
      int llllllllllllllllllIIIllIIIIIllII = llllllllllllllllllIIIllIIIIIllIl.length;

      for(int llllllllllllllllllIIIllIIIIIlIll = 0; llllllllllllllllllIIIllIIIIIlIll < llllllllllllllllllIIIllIIIIIllII; ++llllllllllllllllllIIIllIIIIIlIll) {
         char llllllllllllllllllIIIllIIIIIlIlI = llllllllllllllllllIIIllIIIIIllIl[llllllllllllllllllIIIllIIIIIlIll];
         if (llllllllllllllllllIIIllIIIIIlIlI.getType() == FileConfig.class) {
            try {
               if (!llllllllllllllllllIIIllIIIIIlIlI.isAccessible()) {
                  llllllllllllllllllIIIllIIIIIlIlI.setAccessible(true);
               }

               FileConfig llllllllllllllllllIIIllIIIIlIIlI = (FileConfig)llllllllllllllllllIIIllIIIIIlIlI.get(llllllllllllllllllIIIllIIIIIllll);
               llllllllllllllllllIIIllIIIIIllll.saveConfig(llllllllllllllllllIIIllIIIIlIIlI);
            } catch (IllegalAccessException var6) {
               ClientUtils.getLogger().error(String.valueOf((new StringBuilder()).append("[FileManager] Failed to save config file of field ").append(llllllllllllllllllIIIllIIIIIlIlI.getName()).append(".")), var6);
            }
         }
      }

   }

   public void loadAllConfigs() {
      String llllllllllllllllllIIIllIIIllIlIl = llllllllllllllllllIIIllIIIllIllI.getClass().getDeclaredFields();
      long llllllllllllllllllIIIllIIIllIlII = llllllllllllllllllIIIllIIIllIlIl.length;

      for(int llllllllllllllllllIIIllIIIllIIll = 0; llllllllllllllllllIIIllIIIllIIll < llllllllllllllllllIIIllIIIllIlII; ++llllllllllllllllllIIIllIIIllIIll) {
         char llllllllllllllllllIIIllIIIllIIlI = llllllllllllllllllIIIllIIIllIlIl[llllllllllllllllllIIIllIIIllIIll];
         if (llllllllllllllllllIIIllIIIllIIlI.getType() == FileConfig.class) {
            try {
               if (!llllllllllllllllllIIIllIIIllIIlI.isAccessible()) {
                  llllllllllllllllllIIIllIIIllIIlI.setAccessible(true);
               }

               FileConfig llllllllllllllllllIIIllIIIlllIlI = (FileConfig)llllllllllllllllllIIIllIIIllIIlI.get(llllllllllllllllllIIIllIIIllIllI);
               llllllllllllllllllIIIllIIIllIllI.loadConfig(llllllllllllllllllIIIllIIIlllIlI);
            } catch (IllegalAccessException var6) {
               ClientUtils.getLogger().error(String.valueOf((new StringBuilder()).append("Failed to load config file of field ").append(llllllllllllllllllIIIllIIIllIIlI.getName()).append(".")), var6);
            }
         }
      }

   }

   public void saveConfigs(FileConfig... llllllllllllllllllIIIlIllllllIlI) {
      int llllllllllllllllllIIIlIllllllIIl = llllllllllllllllllIIIlIllllllIlI;
      boolean llllllllllllllllllIIIlIllllllIII = llllllllllllllllllIIIlIllllllIlI.length;

      for(int llllllllllllllllllIIIlIlllllIlll = 0; llllllllllllllllllIIIlIlllllIlll < llllllllllllllllllIIIlIllllllIII; ++llllllllllllllllllIIIlIlllllIlll) {
         FileConfig llllllllllllllllllIIIlIlllllllll = llllllllllllllllllIIIlIllllllIIl[llllllllllllllllllIIIlIlllllIlll];
         llllllllllllllllllIIIlIllllllllI.saveConfig(llllllllllllllllllIIIlIlllllllll);
      }

   }

   public void loadBackground() {
      if (llllllllllllllllllIIIlIlllIIIlll.backgroundFile.exists()) {
         try {
            BufferedImage llllllllllllllllllIIIlIlllIIllII = ImageIO.read(new FileInputStream(llllllllllllllllllIIIlIlllIIIlll.backgroundFile));
            if (llllllllllllllllllIIIlIlllIIllII == null) {
               return;
            }

            LiquidBounce.INSTANCE.setBackground(new ResourceLocation(String.valueOf((new StringBuilder()).append("LiquidSense".toLowerCase()).append("/background.png"))));
            mc.getTextureManager().loadTexture(LiquidBounce.INSTANCE.getBackground(), new DynamicTexture(llllllllllllllllllIIIlIlllIIllII));
            boolean var10001 = false;
            ClientUtils.getLogger().info("[FileManager] Loaded background.");
         } catch (Exception var3) {
            ClientUtils.getLogger().error("[FileManager] Failed to load background.", var3);
         }
      }

   }

   public void loadConfigs(FileConfig... llllllllllllllllllIIIllIIIlIlIII) {
      int llllllllllllllllllIIIllIIIlIIlIl = llllllllllllllllllIIIllIIIlIlIII;
      Exception llllllllllllllllllIIIllIIIlIIlII = llllllllllllllllllIIIllIIIlIlIII.length;

      for(int llllllllllllllllllIIIllIIIlIIIll = 0; llllllllllllllllllIIIllIIIlIIIll < llllllllllllllllllIIIllIIIlIIlII; ++llllllllllllllllllIIIllIIIlIIIll) {
         FileConfig llllllllllllllllllIIIllIIIlIlIlI = llllllllllllllllllIIIllIIIlIIlIl[llllllllllllllllllIIIllIIIlIIIll];
         llllllllllllllllllIIIllIIIlIlIIl.loadConfig(llllllllllllllllllIIIllIIIlIlIlI);
      }

   }

   public FileManager() {
      llllllllllllllllllIIIllIIlIIIlII.dir = new File(mc.mcDataDir, "LiquidSense-Config");
      llllllllllllllllllIIIllIIlIIIlII.fontsDir = new File(llllllllllllllllllIIIllIIlIIIlII.dir, "fonts");
      llllllllllllllllllIIIllIIlIIIlII.settingsDir = new File(llllllllllllllllllIIIllIIlIIIlII.dir, "settings");
      llllllllllllllllllIIIllIIlIIIlII.modulesConfig = new ModulesConfig(new File(llllllllllllllllllIIIllIIlIIIlII.dir, "modules.json"));
      llllllllllllllllllIIIllIIlIIIlII.valuesConfig = new ValuesConfig(new File(llllllllllllllllllIIIllIIlIIIlII.dir, "values.json"));
      llllllllllllllllllIIIllIIlIIIlII.clickGuiConfig = new ClickGuiConfig(new File(llllllllllllllllllIIIllIIlIIIlII.dir, "clickgui.json"));
      llllllllllllllllllIIIllIIlIIIlII.accountsConfig = new AccountsConfig(new File(llllllllllllllllllIIIllIIlIIIlII.dir, "accounts.json"));
      llllllllllllllllllIIIllIIlIIIlII.friendsConfig = new FriendsConfig(new File(llllllllllllllllllIIIllIIlIIIlII.dir, "friends.json"));
      llllllllllllllllllIIIllIIlIIIlII.xrayConfig = new XRayConfig(new File(llllllllllllllllllIIIllIIlIIIlII.dir, "xray-blocks.json"));
      llllllllllllllllllIIIllIIlIIIlII.hudConfig = new HudConfig(new File(llllllllllllllllllIIIllIIlIIIlII.dir, "hud.json"));
      llllllllllllllllllIIIllIIlIIIlII.shortcutsConfig = new ShortcutsConfig(new File(llllllllllllllllllIIIllIIlIIIlII.dir, "shortcuts.json"));
      llllllllllllllllllIIIllIIlIIIlII.backgroundFile = new File(llllllllllllllllllIIIllIIlIIIlII.dir, "userbackground.png");
      llllllllllllllllllIIIllIIlIIIlII.firstStart = false;
      llllllllllllllllllIIIllIIlIIIlII.setupFolder();
      llllllllllllllllllIIIllIIlIIIlII.loadBackground();
   }

   public void setupFolder() {
      boolean var10001;
      if (!llllllllllllllllllIIIllIIlIIIIlI.dir.exists()) {
         llllllllllllllllllIIIllIIlIIIIlI.dir.mkdir();
         var10001 = false;
         llllllllllllllllllIIIllIIlIIIIlI.firstStart = true;
      }

      if (!llllllllllllllllllIIIllIIlIIIIlI.fontsDir.exists()) {
         llllllllllllllllllIIIllIIlIIIIlI.fontsDir.mkdir();
         var10001 = false;
      }

      if (!llllllllllllllllllIIIllIIlIIIIlI.settingsDir.exists()) {
         llllllllllllllllllIIIllIIlIIIIlI.settingsDir.mkdir();
         var10001 = false;
      }

   }
}
