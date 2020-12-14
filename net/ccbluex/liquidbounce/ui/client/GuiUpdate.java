//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.ui.client;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.misc.MiscUtils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\f\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0014J \u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\r\u001a\u00020\u0004H\u0016J\u0018\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\tH\u0014¨\u0006\u0012"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/client/GuiUpdate;", "Lnet/minecraft/client/gui/GuiScreen;", "()V", "actionPerformed", "", "button", "Lnet/minecraft/client/gui/GuiButton;", "drawScreen", "mouseX", "", "mouseY", "partialTicks", "", "initGui", "keyTyped", "typedChar", "", "keyCode", "LiquidSense"}
)
public final class GuiUpdate extends GuiScreen {
   public void drawScreen(int llIIIIIlllllll, int llIIIIIllllIlI, float llIIIIIlllllIl) {
      llIIIIlIIIIIII.drawBackground(0);
      llIIIIlIIIIIII.drawCenteredString((FontRenderer)Fonts.font35, String.valueOf((new StringBuilder()).append('b').append(LiquidBounce.INSTANCE.getLatestVersion()).append(" got released!")), llIIIIlIIIIIII.width / 2, llIIIIlIIIIIII.height / 8 + 80, 16777215);
      llIIIIlIIIIIII.drawCenteredString((FontRenderer)Fonts.font35, "Press \"Download\" to visit our website or dismiss this message by pressing \"OK\".", llIIIIlIIIIIII.width / 2, llIIIIlIIIIIII.height / 8 + 80 + Fonts.font35.FONT_HEIGHT, 16777215);
      super.drawScreen(llIIIIIlllllll, llIIIIIllllIlI, llIIIIIlllllIl);
      GL11.glScalef(2.0F, 2.0F, 2.0F);
      llIIIIlIIIIIII.drawCenteredString((FontRenderer)Fonts.font35, "New update available!", llIIIIlIIIIIII.width / 2 / 2, llIIIIlIIIIIII.height / 8 / 2 + 20, (new Color(255, 0, 0)).getRGB());
   }

   protected void actionPerformed(@NotNull GuiButton llIIIIIlllIlIl) {
      Intrinsics.checkParameterIsNotNull(llIIIIIlllIlIl, "button");
      switch(llIIIIIlllIlIl.id) {
      case 1:
         llIIIIIlllIllI.mc.displayGuiScreen((GuiScreen)(new GuiMainMenu()));
         break;
      case 2:
         MiscUtils.showURL("https://liquidbounce.net/download");
      }

   }

   protected void keyTyped(char llIIIIIllIlIll, int llIIIIIllIllIl) {
      if (1 != llIIIIIllIllIl) {
         super.keyTyped(llIIIIIllIlIll, llIIIIIllIllIl);
      }
   }

   public void initGui() {
      int llIIIIlIIIlIII = llIIIIlIIIIllI.height / 4 + 48;
      llIIIIlIIIIllI.buttonList.add(new GuiButton(1, llIIIIlIIIIllI.width / 2 + 2, llIIIIlIIIlIII + 48, 98, 20, "OK"));
      boolean var10001 = false;
      llIIIIlIIIIllI.buttonList.add(new GuiButton(2, llIIIIlIIIIllI.width / 2 - 100, llIIIIlIIIlIII + 48, 98, 20, "Download"));
      var10001 = false;
   }
}
