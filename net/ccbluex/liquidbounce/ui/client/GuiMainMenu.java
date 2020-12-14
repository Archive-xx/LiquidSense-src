//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.ui.client;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.ui.client.altmanager.GuiAltManager;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.resources.I18n;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\f\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0014J \u0010\b\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u000e\u001a\u00020\u0005H\u0016J\u0018\u0010\u000f\u001a\u00020\u00052\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\nH\u0014¨\u0006\u0013"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/client/GuiMainMenu;", "Lnet/minecraft/client/gui/GuiScreen;", "Lnet/minecraft/client/gui/GuiYesNoCallback;", "()V", "actionPerformed", "", "button", "Lnet/minecraft/client/gui/GuiButton;", "drawScreen", "mouseX", "", "mouseY", "partialTicks", "", "initGui", "keyTyped", "typedChar", "", "keyCode", "LiquidSense"}
)
public final class GuiMainMenu extends GuiScreen implements GuiYesNoCallback {
   protected void keyTyped(char lIIIllIIIIlIIIl, int lIIIllIIIIlIIII) {
   }

   public void initGui() {
      int lIIIllIIIlIlIIl = lIIIllIIIlIIlll.height / 4 + 48;
      int lIIIllIIIlIlIlI = lIIIllIIIlIIlll.height / 4 + 48;
      lIIIllIIIlIIlll.buttonList.add(new GuiButton(1, 0, 0, lIIIllIIIlIIlll.width / 8, 20, I18n.format("menu.singleplayer", new Object[0])));
      boolean var10001 = false;
      lIIIllIIIlIIlll.buttonList.add(new GuiButton(2, (lIIIllIIIlIIlll.width / 2 - lIIIllIIIlIIlll.width / 8 / 2) / 2, 0, lIIIllIIIlIIlll.width / 8, 20, I18n.format("menu.multiplayer", new Object[0])));
      var10001 = false;
      lIIIllIIIlIIlll.buttonList.add(new GuiButton(100, lIIIllIIIlIIlll.width / 2 - lIIIllIIIlIIlll.width / 8 / 2, 0, lIIIllIIIlIIlll.width / 8, 20, "AltManager"));
      var10001 = false;
      lIIIllIIIlIIlll.buttonList.add(new GuiButton(0, lIIIllIIIlIIlll.width / 2 - lIIIllIIIlIIlll.width / 8 / 2 + (lIIIllIIIlIIlll.width / 2 - lIIIllIIIlIIlll.width / 8 / 2) / 2, 0, lIIIllIIIlIIlll.width / 8, 20, I18n.format("menu.options", new Object[0])));
      var10001 = false;
      lIIIllIIIlIIlll.buttonList.add(new GuiButton(4, lIIIllIIIlIIlll.width - lIIIllIIIlIIlll.width / 8, 0, lIIIllIIIlIIlll.width / 8, 20, I18n.format("menu.quit", new Object[0])));
      var10001 = false;
      super.initGui();
   }

   protected void actionPerformed(@NotNull GuiButton lIIIllIIIIlIlIl) {
      Intrinsics.checkParameterIsNotNull(lIIIllIIIIlIlIl, "button");
      switch(lIIIllIIIIlIlIl.id) {
      case 0:
         lIIIllIIIIlIllI.mc.displayGuiScreen((GuiScreen)(new GuiOptions((GuiScreen)lIIIllIIIIlIllI, lIIIllIIIIlIllI.mc.gameSettings)));
         break;
      case 1:
         lIIIllIIIIlIllI.mc.displayGuiScreen((GuiScreen)(new GuiSelectWorld((GuiScreen)lIIIllIIIIlIllI)));
         break;
      case 2:
         lIIIllIIIIlIllI.mc.displayGuiScreen((GuiScreen)(new GuiMultiplayer((GuiScreen)lIIIllIIIIlIllI)));
         break;
      case 4:
         lIIIllIIIIlIllI.mc.shutdown();
         break;
      case 100:
         lIIIllIIIIlIllI.mc.displayGuiScreen((GuiScreen)(new GuiAltManager((GuiScreen)lIIIllIIIIlIllI)));
         break;
      case 101:
         lIIIllIIIIlIllI.mc.displayGuiScreen((GuiScreen)(new GuiServerStatus((GuiScreen)lIIIllIIIIlIllI)));
         break;
      case 102:
         lIIIllIIIIlIllI.mc.displayGuiScreen((GuiScreen)(new GuiBackground((GuiScreen)lIIIllIIIIlIllI)));
         break;
      case 103:
         lIIIllIIIIlIllI.mc.displayGuiScreen((GuiScreen)(new GuiModsMenu((GuiScreen)lIIIllIIIIlIllI)));
         break;
      case 108:
         lIIIllIIIIlIllI.mc.displayGuiScreen((GuiScreen)(new GuiCredits((GuiScreen)lIIIllIIIIlIllI)));
      }

   }

   public void drawScreen(int lIIIllIIIIlllll, int lIIIllIIIIllllI, float lIIIllIIIIlllIl) {
      lIIIllIIIIlllII.drawBackground(0);
      Gui.drawRect(0, 21, lIIIllIIIIlllII.width, 22, (new Color(0, 0, 0)).getRGB());
      Gui.drawRect(0, lIIIllIIIIlllII.height - 16, lIIIllIIIIlllII.width, lIIIllIIIIlllII.height - 15, (new Color(0, 0, 0)).getRGB());
      Gui.drawRect(0, 0, lIIIllIIIIlllII.width, 22, Integer.MIN_VALUE);
      Gui.drawRect(0, lIIIllIIIIlllII.height - 16, lIIIllIIIIlllII.width, lIIIllIIIIlllII.height, Integer.MIN_VALUE);
      Fonts.font35.drawCenteredString("DevelopMent By AquaVit", (float)lIIIllIIIIlllII.width / 2.0F, (float)lIIIllIIIIlllII.height - 20.0F + (float)Fonts.font35.FONT_HEIGHT, 16777215, true);
      boolean var10001 = false;
      Fonts.fontBold180.drawCenteredString("LiquidSense", (float)lIIIllIIIIlllII.width / 2.0F, (float)lIIIllIIIIlllII.height / 2.0F - (float)15, 4673984, true);
      var10001 = false;
      Fonts.font35.drawCenteredString("b6Beta", (float)lIIIllIIIIlllII.width / 2.0F + (float)148, (float)lIIIllIIIIlllII.height / 2.0F - (float)15 + (float)Fonts.font35.FONT_HEIGHT, 16777215, true);
      var10001 = false;
      super.drawScreen(lIIIllIIIIlllll, lIIIllIIIIllllI, lIIIllIIIIlllIl);
   }
}
