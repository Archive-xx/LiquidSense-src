//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.ui.client.altmanager.sub;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import net.ccbluex.liquidbounce.ui.client.altmanager.GuiAltManager;
import net.ccbluex.liquidbounce.ui.elements.GuiPasswordField;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.TabUtils;
import net.ccbluex.liquidbounce.utils.login.MinecraftAccount;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;

public class GuiDirectLogin extends GuiScreen {
   // $FF: synthetic field
   private GuiTextField username;
   // $FF: synthetic field
   private GuiButton clipboardLoginButton;
   // $FF: synthetic field
   private GuiButton loginButton;
   // $FF: synthetic field
   private GuiPasswordField password;
   // $FF: synthetic field
   private final GuiScreen prevGui;
   // $FF: synthetic field
   private String status = "§7Idle...";

   public void onGuiClosed() {
      Keyboard.enableRepeatEvents(false);
      super.onGuiClosed();
   }

   public void initGui() {
      Keyboard.enableRepeatEvents(true);
      lIllIIlIlllIll.buttonList.add(lIllIIlIlllIll.loginButton = new GuiButton(1, lIllIIlIlllIll.width / 2 - 100, lIllIIlIlllIll.height / 4 + 72, "Login"));
      boolean var10001 = false;
      lIllIIlIlllIll.buttonList.add(lIllIIlIlllIll.clipboardLoginButton = new GuiButton(2, lIllIIlIlllIll.width / 2 - 100, lIllIIlIlllIll.height / 4 + 96, "Clipboard Login"));
      var10001 = false;
      lIllIIlIlllIll.buttonList.add(new GuiButton(0, lIllIIlIlllIll.width / 2 - 100, lIllIIlIlllIll.height / 4 + 120, "Back"));
      var10001 = false;
      lIllIIlIlllIll.username = new GuiTextField(2, Fonts.font40, lIllIIlIlllIll.width / 2 - 100, 60, 200, 20);
      lIllIIlIlllIll.username.setFocused(true);
      lIllIIlIlllIll.username.setMaxStringLength(Integer.MAX_VALUE);
      lIllIIlIlllIll.password = new GuiPasswordField(3, Fonts.font40, lIllIIlIlllIll.width / 2 - 100, 85, 200, 20);
      lIllIIlIlllIll.password.setMaxStringLength(Integer.MAX_VALUE);
   }

   protected void mouseClicked(int lIllIIlIIIllIl, int lIllIIlIIIllII, int lIllIIlIIIlIll) throws IOException {
      lIllIIlIIlIIlI.username.mouseClicked(lIllIIlIIIllIl, lIllIIlIIIllII, lIllIIlIIIlIll);
      lIllIIlIIlIIlI.password.mouseClicked(lIllIIlIIIllIl, lIllIIlIIIllII, lIllIIlIIIlIll);
      super.mouseClicked(lIllIIlIIIllIl, lIllIIlIIIllII, lIllIIlIIIlIll);
   }

   public void drawScreen(int lIllIIlIllIlII, int lIllIIlIllIIll, float lIllIIlIlIlllI) {
      lIllIIlIllIIIl.drawBackground(0);
      Gui.drawRect(30, 30, lIllIIlIllIIIl.width - 30, lIllIIlIllIIIl.height - 30, Integer.MIN_VALUE);
      lIllIIlIllIIIl.drawCenteredString(Fonts.font40, "Direct Login", lIllIIlIllIIIl.width / 2, 34, 16777215);
      lIllIIlIllIIIl.drawCenteredString(Fonts.font35, lIllIIlIllIIIl.status == null ? "" : lIllIIlIllIIIl.status, lIllIIlIllIIIl.width / 2, lIllIIlIllIIIl.height / 4 + 60, 16777215);
      lIllIIlIllIIIl.username.drawTextBox();
      lIllIIlIllIIIl.password.drawTextBox();
      if (lIllIIlIllIIIl.username.getText().isEmpty() && !lIllIIlIllIIIl.username.isFocused()) {
         lIllIIlIllIIIl.drawCenteredString(Fonts.font40, "§7Username / E-Mail", lIllIIlIllIIIl.width / 2 - 55, 66, 16777215);
      }

      if (lIllIIlIllIIIl.password.getText().isEmpty() && !lIllIIlIllIIIl.password.isFocused()) {
         lIllIIlIllIIIl.drawCenteredString(Fonts.font40, "§7Password", lIllIIlIllIIIl.width / 2 - 74, 91, 16777215);
      }

      super.drawScreen(lIllIIlIllIlII, lIllIIlIllIIll, lIllIIlIlIlllI);
   }

   protected void keyTyped(char lIllIIlIIllIII, int lIllIIlIIlIlll) throws IOException {
      switch(lIllIIlIIlIlll) {
      case 1:
         lIllIIlIIlllII.mc.displayGuiScreen(lIllIIlIIlllII.prevGui);
         return;
      case 15:
         TabUtils.tab(lIllIIlIIlllII.username, lIllIIlIIlllII.password);
         return;
      case 28:
         lIllIIlIIlllII.actionPerformed(lIllIIlIIlllII.loginButton);
         return;
      default:
         boolean var10001;
         if (lIllIIlIIlllII.username.isFocused()) {
            lIllIIlIIlllII.username.textboxKeyTyped(lIllIIlIIllIII, lIllIIlIIlIlll);
            var10001 = false;
         }

         if (lIllIIlIIlllII.password.isFocused()) {
            lIllIIlIIlllII.password.textboxKeyTyped(lIllIIlIIllIII, lIllIIlIIlIlll);
            var10001 = false;
         }

         super.keyTyped(lIllIIlIIllIII, lIllIIlIIlIlll);
      }
   }

   protected void actionPerformed(GuiButton lIllIIlIlIIIlI) throws IOException {
      if (lIllIIlIlIIIlI.enabled) {
         switch(lIllIIlIlIIIlI.id) {
         case 0:
            lIllIIlIlIIIll.mc.displayGuiScreen(lIllIIlIlIIIll.prevGui);
            break;
         case 1:
            if (lIllIIlIlIIIll.username.getText().isEmpty()) {
               lIllIIlIlIIIll.status = "§cYou have to fill in both fields!";
               return;
            }

            lIllIIlIlIIIll.loginButton.enabled = lIllIIlIlIIIll.clipboardLoginButton.enabled = false;
            (new Thread(() -> {
               lIllIIIlllllII.status = "§aLogging in...";
               if (lIllIIIlllllII.password.getText().isEmpty()) {
                  lIllIIIlllllII.status = GuiAltManager.login(new MinecraftAccount(ColorUtils.translateAlternateColorCodes(lIllIIIlllllII.username.getText())));
               } else {
                  lIllIIIlllllII.status = GuiAltManager.login(new MinecraftAccount(lIllIIIlllllII.username.getText(), lIllIIIlllllII.password.getText()));
               }

               lIllIIIlllllII.loginButton.enabled = lIllIIIlllllII.clipboardLoginButton.enabled = true;
            })).start();
            break;
         case 2:
            try {
               String lIllIIlIlIlIIl = (String)Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
               String[] lIllIIlIlIlIII = lIllIIlIlIlIIl.split(":", 2);
               if (!lIllIIlIlIlIIl.contains(":") || lIllIIlIlIlIII.length != 2) {
                  lIllIIlIlIIIll.status = "§cInvalid clipboard data. (Use: E-Mail:Password)";
                  return;
               }

               lIllIIlIlIIIll.loginButton.enabled = lIllIIlIlIIIll.clipboardLoginButton.enabled = false;
               (new Thread(() -> {
                  lIllIIlIIIIIlI.status = "§aLogging in...";
                  lIllIIlIIIIIlI.status = GuiAltManager.login(new MinecraftAccount(lIllIIlIlIlIII[0], lIllIIlIlIlIII[1]));
                  lIllIIlIIIIIlI.loginButton.enabled = lIllIIlIIIIIlI.clipboardLoginButton.enabled = true;
               })).start();
            } catch (UnsupportedFlavorException var4) {
               lIllIIlIlIIIll.status = "§cClipboard flavor unsupported!";
               ClientUtils.getLogger().error("Failed to read data from clipboard.", var4);
            } catch (IOException var5) {
               lIllIIlIlIIIll.status = "§cUnknown error! (See log)";
               ClientUtils.getLogger().error(var5);
            }
         }

         super.actionPerformed(lIllIIlIlIIIlI);
      }
   }

   public GuiDirectLogin(GuiAltManager lIllIIlIllllll) {
      lIllIIllIIIIII.prevGui = lIllIIlIllllll;
   }

   public void updateScreen() {
      lIllIIlIIIlIII.username.updateCursorCounter();
      lIllIIlIIIlIII.password.updateCursorCounter();
      super.updateScreen();
   }
}
