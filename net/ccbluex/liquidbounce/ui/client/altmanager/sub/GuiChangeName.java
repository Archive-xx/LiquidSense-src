//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.ui.client.altmanager.sub;

import java.io.IOException;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.SessionEvent;
import net.ccbluex.liquidbounce.ui.client.altmanager.GuiAltManager;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.Session;
import org.lwjgl.input.Keyboard;

public class GuiChangeName extends GuiScreen {
   // $FF: synthetic field
   private String status;
   // $FF: synthetic field
   private final GuiAltManager prevGui;
   // $FF: synthetic field
   private GuiTextField name;

   public void initGui() {
      Keyboard.enableRepeatEvents(true);
      lIIIllIlll.buttonList.add(new GuiButton(1, lIIIllIlll.width / 2 - 100, lIIIllIlll.height / 4 + 96, "Change"));
      boolean var10001 = false;
      lIIIllIlll.buttonList.add(new GuiButton(0, lIIIllIlll.width / 2 - 100, lIIIllIlll.height / 4 + 120, "Back"));
      var10001 = false;
      lIIIllIlll.name = new GuiTextField(2, Fonts.font40, lIIIllIlll.width / 2 - 100, 60, 200, 20);
      lIIIllIlll.name.setFocused(true);
      lIIIllIlll.name.setText(lIIIllIlll.mc.getSession().getUsername());
      lIIIllIlll.name.setMaxStringLength(16);
   }

   public GuiChangeName(GuiAltManager lIIlIIIlll) {
      lIIlIIIllI.prevGui = lIIlIIIlll;
   }

   protected void actionPerformed(GuiButton lIIIIIllll) throws IOException {
      switch(lIIIIIllll.id) {
      case 0:
         lIIIIIlllI.mc.displayGuiScreen(lIIIIIlllI.prevGui);
         break;
      case 1:
         if (lIIIIIlllI.name.getText().isEmpty()) {
            lIIIIIlllI.status = "§cEnter a name!";
            return;
         }

         if (!lIIIIIlllI.name.getText().equalsIgnoreCase(lIIIIIlllI.mc.getSession().getUsername())) {
            lIIIIIlllI.status = "§cJust change the upper and lower case!";
            return;
         }

         lIIIIIlllI.mc.session = new Session(lIIIIIlllI.name.getText(), lIIIIIlllI.mc.getSession().getPlayerID(), lIIIIIlllI.mc.getSession().getToken(), lIIIIIlllI.mc.getSession().getSessionType().name());
         LiquidBounce.eventManager.callEvent(new SessionEvent());
         lIIIIIlllI.status = String.valueOf((new StringBuilder()).append("§aChanged name to §7").append(lIIIIIlllI.name.getText()).append("§c."));
         lIIIIIlllI.prevGui.status = lIIIIIlllI.status;
         lIIIIIlllI.mc.displayGuiScreen(lIIIIIlllI.prevGui);
      }

      super.actionPerformed(lIIIIIllll);
   }

   public void drawScreen(int lIIIlIIIlI, int lIIIlIIIIl, float lIIIlIIIII) {
      lIIIlIIlII.drawBackground(0);
      Gui.drawRect(30, 30, lIIIlIIlII.width - 30, lIIIlIIlII.height - 30, Integer.MIN_VALUE);
      lIIIlIIlII.drawCenteredString(Fonts.font40, "Change Name", lIIIlIIlII.width / 2, 34, 16777215);
      lIIIlIIlII.drawCenteredString(Fonts.font40, lIIIlIIlII.status == null ? "" : lIIIlIIlII.status, lIIIlIIlII.width / 2, lIIIlIIlII.height / 4 + 84, 16777215);
      lIIIlIIlII.name.drawTextBox();
      if (lIIIlIIlII.name.getText().isEmpty() && !lIIIlIIlII.name.isFocused()) {
         lIIIlIIlII.drawCenteredString(Fonts.font40, "§7Username", lIIIlIIlII.width / 2 - 74, 66, 16777215);
      }

      super.drawScreen(lIIIlIIIlI, lIIIlIIIIl, lIIIlIIIII);
   }

   public void updateScreen() {
      llllIIlII.name.updateCursorCounter();
      super.updateScreen();
   }

   public void onGuiClosed() {
      Keyboard.enableRepeatEvents(false);
      super.onGuiClosed();
   }

   protected void keyTyped(char lllllllII, int llllllllI) throws IOException {
      if (1 == llllllllI) {
         lllllllIl.mc.displayGuiScreen(lllllllIl.prevGui);
      } else {
         if (lllllllIl.name.isFocused()) {
            lllllllIl.name.textboxKeyTyped(lllllllII, llllllllI);
            boolean var10001 = false;
         }

         super.keyTyped(lllllllII, llllllllI);
      }
   }

   protected void mouseClicked(int lllllIIIl, int llllIlIll, int llllIlIlI) throws IOException {
      lllllIIlI.name.mouseClicked(lllllIIIl, llllIlIll, llllIlIlI);
      super.mouseClicked(lllllIIIl, llllIlIll, llllIlIlI);
   }
}
