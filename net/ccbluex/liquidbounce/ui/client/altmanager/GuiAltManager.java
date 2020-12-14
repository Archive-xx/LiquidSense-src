//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.ui.client.altmanager;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.thealtening.AltService;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.ui.client.altmanager.sub.GuiAdd;
import net.ccbluex.liquidbounce.ui.client.altmanager.sub.GuiChangeName;
import net.ccbluex.liquidbounce.ui.client.altmanager.sub.GuiDirectLogin;
import net.ccbluex.liquidbounce.ui.client.altmanager.sub.GuiDonatorCape;
import net.ccbluex.liquidbounce.ui.client.altmanager.sub.GuiSessionLogin;
import net.ccbluex.liquidbounce.ui.client.altmanager.sub.altgenerator.GuiMCLeaks;
import net.ccbluex.liquidbounce.ui.client.altmanager.sub.altgenerator.GuiTheAltening;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.login.LoginUtils;
import net.ccbluex.liquidbounce.utils.login.MinecraftAccount;
import net.ccbluex.liquidbounce.utils.login.UserUtils;
import net.ccbluex.liquidbounce.utils.misc.HttpUtils;
import net.ccbluex.liquidbounce.utils.misc.MiscUtils;
import net.mcleaks.MCLeaks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;

public class GuiAltManager extends GuiScreen {
   // $FF: synthetic field
   private final GuiScreen prevGui;
   // $FF: synthetic field
   private GuiAltManager.GuiList altsList;
   // $FF: synthetic field
   public String status = "§7Idle...";
   // $FF: synthetic field
   public static final AltService altService = new AltService();
   // $FF: synthetic field
   private static final Map<String, Boolean> GENERATORS = new HashMap();
   // $FF: synthetic field
   private GuiButton randomButton;
   // $FF: synthetic field
   private GuiButton loginButton;

   public static void loadGenerators() {
      try {
         int lIllIIlIlllIIlI = (new JsonParser()).parse(HttpUtils.get("https://cloud.liquidbounce.net/LiquidBounce/generators.json"));
         if (lIllIIlIlllIIlI.isJsonObject()) {
            JsonObject lIllIIlIlllIlIl = lIllIIlIlllIIlI.getAsJsonObject();
            lIllIIlIlllIlIl.entrySet().forEach((lIllIIIllIIIlIl) -> {
               Boolean var10000 = (Boolean)GENERATORS.put(lIllIIIllIIIlIl.getKey(), ((JsonElement)lIllIIIllIIIlIl.getValue()).getAsBoolean());
               boolean var10001 = false;
            });
         }
      } catch (Throwable var2) {
         ClientUtils.getLogger().error("Failed to load enabled generators.", var2);
      }

   }

   protected void actionPerformed(GuiButton lIllIIIllllIIlI) throws IOException {
      if (lIllIIIllllIIlI.enabled) {
         boolean var10001;
         switch(lIllIIIllllIIlI.id) {
         case 0:
            lIllIIIllllIIll.mc.displayGuiScreen(lIllIIIllllIIll.prevGui);
            break;
         case 1:
            lIllIIIllllIIll.mc.displayGuiScreen(new GuiAdd(lIllIIIllllIIll));
            break;
         case 2:
            if (lIllIIIllllIIll.altsList.getSelectedSlot() != -1 && lIllIIIllllIIll.altsList.getSelectedSlot() < lIllIIIllllIIll.altsList.getSize()) {
               LiquidBounce.fileManager.accountsConfig.altManagerMinecraftAccounts.remove(lIllIIIllllIIll.altsList.getSelectedSlot());
               var10001 = false;
               LiquidBounce.fileManager.saveConfig(LiquidBounce.fileManager.accountsConfig);
               lIllIIIllllIIll.status = "§aThe account has been removed.";
            } else {
               lIllIIIllllIIll.status = "§cSelect an account.";
            }
            break;
         case 3:
            if (lIllIIIllllIIll.altsList.getSelectedSlot() != -1 && lIllIIIllllIIll.altsList.getSelectedSlot() < lIllIIIllllIIll.altsList.getSize()) {
               lIllIIIllllIIll.loginButton.enabled = lIllIIIllllIIll.randomButton.enabled = false;
               double lIllIIIlllIllll = new Thread(() -> {
                  String lIllIIIllIIIlll = (MinecraftAccount)LiquidBounce.fileManager.accountsConfig.altManagerMinecraftAccounts.get(lIllIIIllIIlIlI.altsList.getSelectedSlot());
                  lIllIIIllIIlIlI.status = "§aLogging in...";
                  lIllIIIllIIlIlI.status = login(lIllIIIllIIIlll);
                  lIllIIIllIIlIlI.loginButton.enabled = lIllIIIllIIlIlI.randomButton.enabled = true;
               }, "AltLogin");
               lIllIIIlllIllll.start();
            } else {
               lIllIIIllllIIll.status = "§cSelect an account.";
            }
            break;
         case 4:
            if (LiquidBounce.fileManager.accountsConfig.altManagerMinecraftAccounts.size() <= 0) {
               lIllIIIllllIIll.status = "§cThe list is empty.";
               return;
            }

            int lIllIIIllllllIl = (new Random()).nextInt(LiquidBounce.fileManager.accountsConfig.altManagerMinecraftAccounts.size());
            if (lIllIIIllllllIl < lIllIIIllllIIll.altsList.getSize()) {
               lIllIIIllllIIll.altsList.selectedSlot = lIllIIIllllllIl;
               var10001 = false;
            }

            lIllIIIllllIIll.loginButton.enabled = lIllIIIllllIIll.randomButton.enabled = false;
            byte lIllIIIlllIlllI = new Thread(() -> {
               MinecraftAccount lIllIIIllIlIIII = (MinecraftAccount)LiquidBounce.fileManager.accountsConfig.altManagerMinecraftAccounts.get(lIllIIIllllllIl);
               lIllIIIllIlIIlI.status = "§aLogging in...";
               lIllIIIllIlIIlI.status = login(lIllIIIllIlIIII);
               lIllIIIllIlIIlI.loginButton.enabled = lIllIIIllIlIIlI.randomButton.enabled = true;
            }, "AltLogin");
            lIllIIIlllIlllI.start();
            break;
         case 5:
            lIllIIIllllIIll.mc.displayGuiScreen(new GuiMCLeaks(lIllIIIllllIIll));
            break;
         case 6:
            lIllIIIllllIIll.mc.displayGuiScreen(new GuiDirectLogin(lIllIIIllllIIll));
            break;
         case 7:
            File lIllIIIlllllIII = MiscUtils.openFileChooser();
            if (lIllIIIlllllIII == null) {
               return;
            }

            FileReader lIllIIIllllIlll = new FileReader(lIllIIIlllllIII);
            BufferedReader lIllIIIlllIlIll = new BufferedReader(lIllIIIllllIlll);

            String lIllIIIlllIlIlI;
            while((lIllIIIlllIlIlI = lIllIIIlllIlIll.readLine()) != null) {
               boolean lIllIIIlllIlIIl = lIllIIIlllIlIlI.split(":", 2);
               Exception lIllIIIlllIlIII = false;
               Iterator lIllIIIlllIIlll = LiquidBounce.fileManager.accountsConfig.altManagerMinecraftAccounts.iterator();

               while(lIllIIIlllIIlll.hasNext()) {
                  MinecraftAccount lIllIIIlllllIll = (MinecraftAccount)lIllIIIlllIIlll.next();
                  if (lIllIIIlllllIll.getName().equalsIgnoreCase(lIllIIIlllIlIIl[0])) {
                     lIllIIIlllIlIII = true;
                     break;
                  }
               }

               if (!lIllIIIlllIlIII) {
                  if (lIllIIIlllIlIIl.length > 1) {
                     LiquidBounce.fileManager.accountsConfig.altManagerMinecraftAccounts.add(new MinecraftAccount(lIllIIIlllIlIIl[0], lIllIIIlllIlIIl[1]));
                     var10001 = false;
                  } else {
                     LiquidBounce.fileManager.accountsConfig.altManagerMinecraftAccounts.add(new MinecraftAccount(lIllIIIlllIlIIl[0]));
                     var10001 = false;
                  }
               }
            }

            lIllIIIllllIlll.close();
            lIllIIIlllIlIll.close();
            LiquidBounce.fileManager.saveConfig(LiquidBounce.fileManager.accountsConfig);
            lIllIIIllllIIll.status = "§aThe accounts were imported successfully.";
            break;
         case 8:
            if (lIllIIIllllIIll.altsList.getSelectedSlot() != -1 && lIllIIIllllIIll.altsList.getSelectedSlot() < lIllIIIllllIIll.altsList.getSize()) {
               boolean lIllIIIlllIlIIl = (MinecraftAccount)LiquidBounce.fileManager.accountsConfig.altManagerMinecraftAccounts.get(lIllIIIllllIIll.altsList.getSelectedSlot());
               if (lIllIIIlllIlIIl != null) {
                  Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(String.valueOf((new StringBuilder()).append(lIllIIIlllIlIIl.getName()).append(":").append(lIllIIIlllIlIIl.getPassword()))), (ClipboardOwner)null);
                  lIllIIIllllIIll.status = "§aCopied account into your clipboard.";
               }
            } else {
               lIllIIIllllIIll.status = "§cSelect an account.";
            }
            break;
         case 9:
            lIllIIIllllIIll.mc.displayGuiScreen(new GuiTheAltening(lIllIIIllllIIll));
            break;
         case 10:
            lIllIIIllllIIll.mc.displayGuiScreen(new GuiSessionLogin(lIllIIIllllIIll));
            break;
         case 11:
            lIllIIIllllIIll.mc.displayGuiScreen(new GuiDonatorCape(lIllIIIllllIIll));
            break;
         case 88:
            lIllIIIllllIIll.mc.displayGuiScreen(new GuiChangeName(lIllIIIllllIIll));
         }

      }
   }

   public void handleMouseInput() throws IOException {
      super.handleMouseInput();
      lIllIIIllIlIllI.altsList.handleMouseInput();
   }

   public GuiAltManager(GuiScreen lIllIIlIllllIII) {
      lIllIIlIllllIll.prevGui = lIllIIlIllllIII;
   }

   protected void keyTyped(char lIllIIIllIllllI, int lIllIIIllIlllIl) throws IOException {
      int lIllIIIllIllIIl;
      switch(lIllIIIllIlllIl) {
      case 1:
         lIllIIIllIlllll.mc.displayGuiScreen(lIllIIIllIlllll.prevGui);
         return;
      case 28:
         lIllIIIllIlllll.altsList.elementClicked(lIllIIIllIlllll.altsList.getSelectedSlot(), true, 0, 0);
         break;
      case 200:
         lIllIIIllIllIIl = lIllIIIllIlllll.altsList.getSelectedSlot() - 1;
         if (lIllIIIllIllIIl < 0) {
            lIllIIIllIllIIl = 0;
         }

         lIllIIIllIlllll.altsList.elementClicked(lIllIIIllIllIIl, false, 0, 0);
         break;
      case 201:
         lIllIIIllIlllll.altsList.scrollBy(-lIllIIIllIlllll.height + 100);
         return;
      case 208:
         lIllIIIllIllIIl = lIllIIIllIlllll.altsList.getSelectedSlot() + 1;
         if (lIllIIIllIllIIl >= lIllIIIllIlllll.altsList.getSize()) {
            lIllIIIllIllIIl = lIllIIIllIlllll.altsList.getSize() - 1;
         }

         lIllIIIllIlllll.altsList.elementClicked(lIllIIIllIllIIl, false, 0, 0);
         break;
      case 209:
         lIllIIIllIlllll.altsList.scrollBy(lIllIIIllIlllll.height - 100);
      }

      super.keyTyped(lIllIIIllIllllI, lIllIIIllIlllIl);
   }

   public void initGui() {
      lIllIIlIlIllIlI.altsList = new GuiAltManager.GuiList(lIllIIlIlIllIlI);
      lIllIIlIlIllIlI.altsList.registerScrollButtons(7, 8);
      int lIllIIlIlIlllII = -1;

      for(int lIllIIlIlIllllI = 0; lIllIIlIlIllllI < LiquidBounce.fileManager.accountsConfig.altManagerMinecraftAccounts.size(); ++lIllIIlIlIllllI) {
         Exception lIllIIlIlIlIlll = (MinecraftAccount)LiquidBounce.fileManager.accountsConfig.altManagerMinecraftAccounts.get(lIllIIlIlIllllI);
         if (lIllIIlIlIlIlll != null && ((lIllIIlIlIlIlll.getPassword() == null || lIllIIlIlIlIlll.getPassword().isEmpty()) && lIllIIlIlIlIlll.getName() != null && lIllIIlIlIlIlll.getName().equals(lIllIIlIlIllIlI.mc.session.getUsername()) || lIllIIlIlIlIlll.getAccountName() != null && lIllIIlIlIlIlll.getAccountName().equals(lIllIIlIlIllIlI.mc.session.getUsername()))) {
            lIllIIlIlIlllII = lIllIIlIlIllllI;
            break;
         }
      }

      lIllIIlIlIllIlI.altsList.elementClicked(lIllIIlIlIlllII, false, 0, 0);
      lIllIIlIlIllIlI.altsList.scrollBy(lIllIIlIlIlllII * lIllIIlIlIllIlI.altsList.slotHeight);
      int lIllIIlIlIllIll = 22;
      lIllIIlIlIllIlI.buttonList.add(new GuiButton(1, lIllIIlIlIllIlI.width - 80, lIllIIlIlIllIll + 24, 70, 20, "Add"));
      boolean var10001 = false;
      lIllIIlIlIllIlI.buttonList.add(new GuiButton(2, lIllIIlIlIllIlI.width - 80, lIllIIlIlIllIll + 48, 70, 20, "Remove"));
      var10001 = false;
      lIllIIlIlIllIlI.buttonList.add(new GuiButton(7, lIllIIlIlIllIlI.width - 80, lIllIIlIlIllIll + 72, 70, 20, "Import"));
      var10001 = false;
      lIllIIlIlIllIlI.buttonList.add(new GuiButton(8, lIllIIlIlIllIlI.width - 80, lIllIIlIlIllIll + 96, 70, 20, "Copy"));
      var10001 = false;
      lIllIIlIlIllIlI.buttonList.add(new GuiButton(0, lIllIIlIlIllIlI.width - 80, lIllIIlIlIllIlI.height - 65, 70, 20, "Back"));
      var10001 = false;
      lIllIIlIlIllIlI.buttonList.add(lIllIIlIlIllIlI.loginButton = new GuiButton(3, 5, lIllIIlIlIllIll + 24, 90, 20, "Login"));
      var10001 = false;
      lIllIIlIlIllIlI.buttonList.add(lIllIIlIlIllIlI.randomButton = new GuiButton(4, 5, lIllIIlIlIllIll + 48, 90, 20, "Random"));
      var10001 = false;
      lIllIIlIlIllIlI.buttonList.add(new GuiButton(6, 5, lIllIIlIlIllIll + 72, 90, 20, "Direct Login"));
      var10001 = false;
      lIllIIlIlIllIlI.buttonList.add(new GuiButton(88, 5, lIllIIlIlIllIll + 96, 90, 20, "Change Name"));
      var10001 = false;
      if ((Boolean)GENERATORS.getOrDefault("mcleaks", true)) {
         lIllIIlIlIllIlI.buttonList.add(new GuiButton(5, 5, lIllIIlIlIllIll + 120 + 5, 90, 20, "MCLeaks"));
         var10001 = false;
      }

      if ((Boolean)GENERATORS.getOrDefault("thealtening", true)) {
         lIllIIlIlIllIlI.buttonList.add(new GuiButton(9, 5, lIllIIlIlIllIll + 144 + 5, 90, 20, "TheAltening"));
         var10001 = false;
      }

      lIllIIlIlIllIlI.buttonList.add(new GuiButton(10, 5, lIllIIlIlIllIll + 168 + 5, 90, 20, "Session Login"));
      var10001 = false;
      lIllIIlIlIllIlI.buttonList.add(new GuiButton(11, 5, lIllIIlIlIllIll + 192 + 10, 90, 20, "Cape"));
      var10001 = false;
   }

   public static String login(MinecraftAccount lIllIIlIllIlIIl) {
      if (lIllIIlIllIlIIl == null) {
         return "";
      } else {
         if (altService.getCurrentService() != AltService.EnumAltService.MOJANG) {
            try {
               altService.switchService(AltService.EnumAltService.MOJANG);
            } catch (IllegalAccessException | NoSuchFieldException var3) {
               ClientUtils.getLogger().error("Something went wrong while trying to switch alt service.", var3);
            }
         }

         if (lIllIIlIllIlIIl.isCracked()) {
            LoginUtils.loginCracked(lIllIIlIllIlIIl.getName());
            MCLeaks.remove();
            return String.valueOf((new StringBuilder()).append("§cYour name is now §8").append(lIllIIlIllIlIIl.getName()).append("§c."));
         } else {
            LoginUtils.LoginResult lIllIIlIllIlIlI = LoginUtils.login(lIllIIlIllIlIIl.getName(), lIllIIlIllIlIIl.getPassword());
            if (lIllIIlIllIlIlI == LoginUtils.LoginResult.LOGGED) {
               MCLeaks.remove();
               short lIllIIlIllIIlll = Minecraft.getMinecraft().getSession().getUsername();
               lIllIIlIllIlIIl.setAccountName(lIllIIlIllIIlll);
               LiquidBounce.fileManager.saveConfig(LiquidBounce.fileManager.accountsConfig);
               return String.valueOf((new StringBuilder()).append("§cYour name is now §f§l").append(lIllIIlIllIIlll).append("§c."));
            } else if (lIllIIlIllIlIlI == LoginUtils.LoginResult.WRONG_PASSWORD) {
               return "§cWrong password.";
            } else if (lIllIIlIllIlIlI == LoginUtils.LoginResult.NO_CONTACT) {
               return "§cCannot contact authentication server.";
            } else if (lIllIIlIllIlIlI == LoginUtils.LoginResult.INVALID_ACCOUNT_DATA) {
               return "§cInvaild username or password.";
            } else {
               return lIllIIlIllIlIlI == LoginUtils.LoginResult.MIGRATED ? "§cAccount migrated." : "";
            }
         }
      }
   }

   public void drawScreen(int lIllIIlIlIIIIIl, int lIllIIlIIlllIIl, float lIllIIlIIllllIl) {
      lIllIIlIIlllIll.drawBackground(0);
      lIllIIlIIlllIll.altsList.drawScreen(lIllIIlIlIIIIIl, lIllIIlIIlllIIl, lIllIIlIIllllIl);
      Fonts.font40.drawCenteredString("AltManager", (float)(lIllIIlIIlllIll.width / 2), 6.0F, 16777215);
      boolean var10001 = false;
      Fonts.font35.drawCenteredString(String.valueOf((new StringBuilder()).append(LiquidBounce.fileManager.accountsConfig.altManagerMinecraftAccounts.size()).append(" Alts")), (float)(lIllIIlIIlllIll.width / 2), 18.0F, 16777215);
      var10001 = false;
      Fonts.font35.drawCenteredString(lIllIIlIIlllIll.status, (float)(lIllIIlIIlllIll.width / 2), 32.0F, 16777215);
      var10001 = false;
      Fonts.font35.drawStringWithShadow(String.valueOf((new StringBuilder()).append("§7User: §a").append(MCLeaks.isAltActive() ? MCLeaks.getSession().getUsername() : lIllIIlIIlllIll.mc.getSession().getUsername())), 6.0F, 6.0F, 16777215);
      var10001 = false;
      Fonts.font35.drawStringWithShadow(String.valueOf((new StringBuilder()).append("§7Type: §a").append(altService.getCurrentService() == AltService.EnumAltService.THEALTENING ? "TheAltening" : (MCLeaks.isAltActive() ? "MCLeaks" : (UserUtils.INSTANCE.isValidTokenOffline(lIllIIlIIlllIll.mc.getSession().getToken()) ? "Premium" : "Cracked")))), 6.0F, 15.0F, 16777215);
      var10001 = false;
      super.drawScreen(lIllIIlIlIIIIIl, lIllIIlIIlllIIl, lIllIIlIIllllIl);
   }

   private class GuiList extends GuiSlot {
      // $FF: synthetic field
      private int selectedSlot;

      protected void drawBackground() {
      }

      protected void elementClicked(int lIIlIIIlllIIlIl, boolean lIIlIIIlllIlIIl, int lIIlIIIlllIlIII, int lIIlIIIlllIIlll) {
         lIIlIIIlllIlIll.selectedSlot = lIIlIIIlllIIlIl;
         if (lIIlIIIlllIlIIl) {
            if (GuiAltManager.this.altsList.getSelectedSlot() != -1 && GuiAltManager.this.altsList.getSelectedSlot() < GuiAltManager.this.altsList.getSize() && GuiAltManager.this.loginButton.enabled) {
               GuiAltManager.this.loginButton.enabled = GuiAltManager.this.randomButton.enabled = false;
               (new Thread(() -> {
                  byte lIIlIIIllIIllIl = (MinecraftAccount)LiquidBounce.fileManager.accountsConfig.altManagerMinecraftAccounts.get(GuiAltManager.this.altsList.getSelectedSlot());
                  GuiAltManager.this.status = "§aLogging in...";
                  GuiAltManager.this.status = String.valueOf((new StringBuilder()).append("§c").append(GuiAltManager.login(lIIlIIIllIIllIl)));
                  GuiAltManager.this.loginButton.enabled = GuiAltManager.this.randomButton.enabled = true;
               }, "AltManagerLogin")).start();
            } else {
               GuiAltManager.this.status = "§cSelect an account.";
            }
         }

      }

      public void setSelectedSlot(int lIIlIIIllllIIlI) {
         lIIlIIIllllIIll.selectedSlot = lIIlIIIllllIIlI;
      }

      protected int getSize() {
         return LiquidBounce.fileManager.accountsConfig.altManagerMinecraftAccounts.size();
      }

      GuiList(GuiScreen lIIlIIIllllllll) {
         super(GuiAltManager.this.mc, lIIlIIIllllllll.width, lIIlIIIllllllll.height, 40, lIIlIIIllllllll.height - 40, 30);
      }

      protected void drawSlot(int lIIlIIIllIlIllI, int lIIlIIIllIlllIl, int lIIlIIIllIlllII, int lIIlIIIllIllIll, int lIIlIIIllIllIlI, int lIIlIIIllIllIIl) {
         MinecraftAccount lIIlIIIllIllIII = (MinecraftAccount)LiquidBounce.fileManager.accountsConfig.altManagerMinecraftAccounts.get(lIIlIIIllIlIllI);
         Fonts.font40.drawCenteredString(lIIlIIIllIllIII.getAccountName() == null ? lIIlIIIllIllIII.getName() : lIIlIIIllIllIII.getAccountName(), (float)(lIIlIIIllIlIlll.width / 2), (float)(lIIlIIIllIlllII + 2), Color.WHITE.getRGB(), true);
         boolean var10001 = false;
         Fonts.font40.drawCenteredString(lIIlIIIllIllIII.isCracked() ? "Cracked" : (lIIlIIIllIllIII.getAccountName() == null ? "Premium" : lIIlIIIllIllIII.getName()), (float)(lIIlIIIllIlIlll.width / 2), (float)(lIIlIIIllIlllII + 15), lIIlIIIllIllIII.isCracked() ? Color.GRAY.getRGB() : (lIIlIIIllIllIII.getAccountName() == null ? Color.GREEN.getRGB() : Color.LIGHT_GRAY.getRGB()), true);
         var10001 = false;
      }

      protected boolean isSelected(int lIIlIIIlllllIll) {
         return lIIlIIIllllllII.selectedSlot == lIIlIIIlllllIll;
      }

      int getSelectedSlot() {
         if (lIIlIIIllllIllI.selectedSlot > LiquidBounce.fileManager.accountsConfig.altManagerMinecraftAccounts.size()) {
            lIIlIIIllllIllI.selectedSlot = -1;
         }

         return lIIlIIIllllIllI.selectedSlot;
      }
   }
}
