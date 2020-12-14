//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.ui.client.altmanager.sub;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import com.thealtening.AltService;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.net.Proxy;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.ui.client.altmanager.GuiAltManager;
import net.ccbluex.liquidbounce.ui.elements.GuiPasswordField;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.TabUtils;
import net.ccbluex.liquidbounce.utils.login.MinecraftAccount;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;

public class GuiAdd extends GuiScreen {
   // $FF: synthetic field
   private GuiTextField username;
   // $FF: synthetic field
   private final GuiAltManager prevGui;
   // $FF: synthetic field
   private GuiButton addButton;
   // $FF: synthetic field
   private GuiButton clipboardButton;
   // $FF: synthetic field
   private GuiPasswordField password;
   // $FF: synthetic field
   private String status = "§7Idle...";

   protected void keyTyped(char lllIIllIlllIlll, int lllIIllIlllIllI) throws IOException {
      switch(lllIIllIlllIllI) {
      case 1:
         lllIIllIlllIlIl.mc.displayGuiScreen(lllIIllIlllIlIl.prevGui);
         return;
      case 15:
         TabUtils.tab(lllIIllIlllIlIl.username, lllIIllIlllIlIl.password);
         return;
      case 28:
         lllIIllIlllIlIl.actionPerformed(lllIIllIlllIlIl.addButton);
         return;
      default:
         boolean var10001;
         if (lllIIllIlllIlIl.username.isFocused()) {
            lllIIllIlllIlIl.username.textboxKeyTyped(lllIIllIlllIlll, lllIIllIlllIllI);
            var10001 = false;
         }

         if (lllIIllIlllIlIl.password.isFocused()) {
            lllIIllIlllIlIl.password.textboxKeyTyped(lllIIllIlllIlll, lllIIllIlllIllI);
            var10001 = false;
         }

         super.keyTyped(lllIIllIlllIlll, lllIIllIlllIllI);
      }
   }

   public GuiAdd(GuiAltManager lllIIlllIIllIlI) {
      lllIIlllIIllIll.prevGui = lllIIlllIIllIlI;
   }

   private void addAccount(String lllIIllIlIllIll, String lllIIllIlIllIlI) {
      if (LiquidBounce.fileManager.accountsConfig.altManagerMinecraftAccounts.stream().anyMatch((lllIIllIlIIIlII) -> {
         return lllIIllIlIIIlII.getName().equals(lllIIllIlIllIll);
      })) {
         lllIIllIlIllIII.status = "§cThe account has already been added.";
      } else {
         lllIIllIlIllIII.addButton.enabled = lllIIllIlIllIII.clipboardButton.enabled = false;
         MinecraftAccount lllIIllIlIllIIl = new MinecraftAccount(lllIIllIlIllIll, lllIIllIlIllIlI);
         (new Thread(() -> {
            if (!lllIIllIlIllIIl.isCracked()) {
               lllIIllIlIIlIll.status = "§aChecking...";

               try {
                  byte lllIIllIlIIlIIl = GuiAltManager.altService.getCurrentService();
                  if (lllIIllIlIIlIIl != AltService.EnumAltService.MOJANG) {
                     GuiAltManager.altService.switchService(AltService.EnumAltService.MOJANG);
                  }

                  YggdrasilUserAuthentication lllIIllIlIIllll = (YggdrasilUserAuthentication)(new YggdrasilAuthenticationService(Proxy.NO_PROXY, "")).createUserAuthentication(Agent.MINECRAFT);
                  lllIIllIlIIllll.setUsername(lllIIllIlIllIIl.getName());
                  lllIIllIlIIllll.setPassword(lllIIllIlIllIIl.getPassword());
                  lllIIllIlIIllll.logIn();
                  lllIIllIlIllIIl.setAccountName(lllIIllIlIIllll.getSelectedProfile().getName());
                  if (lllIIllIlIIlIIl == AltService.EnumAltService.THEALTENING) {
                     GuiAltManager.altService.switchService(AltService.EnumAltService.THEALTENING);
                  }
               } catch (AuthenticationException | NoSuchFieldException | IllegalAccessException | NullPointerException var4) {
                  lllIIllIlIIlIll.status = "§cThe account doesn't work.";
                  lllIIllIlIIlIll.addButton.enabled = lllIIllIlIIlIll.clipboardButton.enabled = true;
                  return;
               }
            }

            LiquidBounce.fileManager.accountsConfig.altManagerMinecraftAccounts.add(lllIIllIlIllIIl);
            boolean var10001 = false;
            LiquidBounce.fileManager.saveConfig(LiquidBounce.fileManager.accountsConfig);
            lllIIllIlIIlIll.status = "§aThe account has been added.";
            lllIIllIlIIlIll.prevGui.status = lllIIllIlIIlIll.status;
            lllIIllIlIIlIll.mc.displayGuiScreen(lllIIllIlIIlIll.prevGui);
         })).start();
      }
   }

   public void drawScreen(int lllIIlllIIIllll, int lllIIlllIIIlIlI, float lllIIlllIIIllIl) {
      lllIIlllIIIllII.drawBackground(0);
      Gui.drawRect(30, 30, lllIIlllIIIllII.width - 30, lllIIlllIIIllII.height - 30, Integer.MIN_VALUE);
      lllIIlllIIIllII.drawCenteredString(Fonts.font40, "Add Account", lllIIlllIIIllII.width / 2, 34, 16777215);
      lllIIlllIIIllII.drawCenteredString(Fonts.font35, lllIIlllIIIllII.status == null ? "" : lllIIlllIIIllII.status, lllIIlllIIIllII.width / 2, lllIIlllIIIllII.height / 4 + 60, 16777215);
      lllIIlllIIIllII.username.drawTextBox();
      lllIIlllIIIllII.password.drawTextBox();
      if (lllIIlllIIIllII.username.getText().isEmpty() && !lllIIlllIIIllII.username.isFocused()) {
         lllIIlllIIIllII.drawCenteredString(Fonts.font40, "§7Username / E-Mail", lllIIlllIIIllII.width / 2 - 55, 66, 16777215);
      }

      if (lllIIlllIIIllII.password.getText().isEmpty() && !lllIIlllIIIllII.password.isFocused()) {
         lllIIlllIIIllII.drawCenteredString(Fonts.font40, "§7Password", lllIIlllIIIllII.width / 2 - 74, 91, 16777215);
      }

      super.drawScreen(lllIIlllIIIllll, lllIIlllIIIlIlI, lllIIlllIIIllIl);
   }

   public void updateScreen() {
      lllIIllIllIIlIl.username.updateCursorCounter();
      lllIIllIllIIlIl.password.updateCursorCounter();
      super.updateScreen();
   }

   public void initGui() {
      Keyboard.enableRepeatEvents(true);
      lllIIlllIIlIlIl.buttonList.add(lllIIlllIIlIlIl.addButton = new GuiButton(1, lllIIlllIIlIlIl.width / 2 - 100, lllIIlllIIlIlIl.height / 4 + 72, "Add"));
      boolean var10001 = false;
      lllIIlllIIlIlIl.buttonList.add(lllIIlllIIlIlIl.clipboardButton = new GuiButton(2, lllIIlllIIlIlIl.width / 2 - 100, lllIIlllIIlIlIl.height / 4 + 96, "Clipboard"));
      var10001 = false;
      lllIIlllIIlIlIl.buttonList.add(new GuiButton(0, lllIIlllIIlIlIl.width / 2 - 100, lllIIlllIIlIlIl.height / 4 + 120, "Back"));
      var10001 = false;
      lllIIlllIIlIlIl.username = new GuiTextField(2, Fonts.font40, lllIIlllIIlIlIl.width / 2 - 100, 60, 200, 20);
      lllIIlllIIlIlIl.username.setFocused(true);
      lllIIlllIIlIlIl.username.setMaxStringLength(Integer.MAX_VALUE);
      lllIIlllIIlIlIl.password = new GuiPasswordField(3, Fonts.font40, lllIIlllIIlIlIl.width / 2 - 100, 85, 200, 20);
      lllIIlllIIlIlIl.password.setMaxStringLength(Integer.MAX_VALUE);
   }

   public void onGuiClosed() {
      Keyboard.enableRepeatEvents(false);
      super.onGuiClosed();
   }

   protected void actionPerformed(GuiButton lllIIlllIIIIIII) throws IOException {
      if (lllIIlllIIIIIII.enabled) {
         switch(lllIIlllIIIIIII.id) {
         case 0:
            lllIIllIlllllll.mc.displayGuiScreen(lllIIllIlllllll.prevGui);
            break;
         case 1:
            if (LiquidBounce.fileManager.accountsConfig.altManagerMinecraftAccounts.stream().anyMatch((lllIIllIIlllllI) -> {
               return lllIIllIIlllllI.getName().equals(lllIIllIIllllIl.username.getText());
            })) {
               lllIIllIlllllll.status = "§cThe account has already been added.";
            } else {
               lllIIllIlllllll.addAccount(lllIIllIlllllll.username.getText(), lllIIllIlllllll.password.getText());
            }
            break;
         case 2:
            try {
               String lllIIlllIIIIlII = (String)Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
               Exception lllIIllIlllllII = lllIIlllIIIIlII.split(":", 2);
               if (!lllIIlllIIIIlII.contains(":") || lllIIllIlllllII.length != 2) {
                  lllIIllIlllllll.status = "§cInvalid clipboard data. (Use: E-Mail:Password)";
                  return;
               }

               lllIIllIlllllll.addAccount(lllIIllIlllllII[0], lllIIllIlllllII[1]);
            } catch (UnsupportedFlavorException var4) {
               lllIIllIlllllll.status = "§cClipboard flavor unsupported!";
               ClientUtils.getLogger().error("Failed to read data from clipboard.", var4);
            }
         }

         super.actionPerformed(lllIIlllIIIIIII);
      }
   }

   protected void mouseClicked(int lllIIllIllIlIIl, int lllIIllIllIlIII, int lllIIllIllIIlll) throws IOException {
      lllIIllIllIlIlI.username.mouseClicked(lllIIllIllIlIIl, lllIIllIllIlIII, lllIIllIllIIlll);
      lllIIllIllIlIlI.password.mouseClicked(lllIIllIllIlIIl, lllIIllIllIlIII, lllIIllIllIIlll);
      super.mouseClicked(lllIIllIllIlIIl, lllIIllIllIlIII, lllIIllIllIIlll);
   }
}
