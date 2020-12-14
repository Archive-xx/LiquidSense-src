//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.ui.client;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.ui.font.GameFontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\f\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0014J \u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\r\u001a\u00020\u0004H\u0016J\u0018\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\tH\u0014¨\u0006\u0012"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/client/GuiWelcome;", "Lnet/minecraft/client/gui/GuiScreen;", "()V", "actionPerformed", "", "button", "Lnet/minecraft/client/gui/GuiButton;", "drawScreen", "mouseX", "", "mouseY", "partialTicks", "", "initGui", "keyTyped", "typedChar", "", "keyCode", "LiquidSense"}
)
public final class GuiWelcome extends GuiScreen {
   protected void actionPerformed(@NotNull GuiButton lllIlIIIIIIlI) {
      Intrinsics.checkParameterIsNotNull(lllIlIIIIIIlI, "button");
      if (lllIlIIIIIIlI.id == 1) {
         lllIlIIIIIIIl.mc.displayGuiScreen((GuiScreen)(new GuiMainMenu()));
      }

   }

   public void initGui() {
      lllIlIIllIlIl.buttonList.add(new GuiButton(1, lllIlIIllIlIl.width / 2 - 100, lllIlIIllIlIl.height - 40, "Ok"));
      boolean var10001 = false;
   }

   public void drawScreen(int lllIlIIIlllII, int lllIlIIlIIIlI, float lllIlIIIllIlI) {
      lllIlIIIllllI.drawBackground(0);
      GameFontRenderer lllIlIIlIIlll = Fonts.font35;
      lllIlIIlIIlll.drawCenteredString("LiquidSenseB6", (float)lllIlIIIllllI.width / 2.0F - (float)1, (float)lllIlIIIllllI.height / 8.0F + (float)70, 16777215, true);
      boolean var10001 = false;
      lllIlIIlIIlll.drawCenteredString("By CCBlueX Develop By AquaVit", (float)lllIlIIIllllI.width / 2.0F - (float)6, (float)lllIlIIIllllI.height / 8.0F + 80.0F + (float)(lllIlIIlIIlll.FONT_HEIGHT * 2), 16777215, true);
      var10001 = false;
      lllIlIIlIIlll.drawCenteredString("本客户端是�?费的请勿倒�?�以�?�购买", (float)lllIlIIIllllI.width / 2.0F - (float)13, (float)lllIlIIIllllI.height / 8.0F + 80.0F + (float)(lllIlIIlIIlll.FONT_HEIGHT * 3), 16777215, true);
      var10001 = false;
      lllIlIIlIIlll.drawCenteredString("群785835501�?续更新 仅供学习勿用与商业", (float)lllIlIIIllllI.width / 2.0F - (float)23, (float)(lllIlIIIllllI.height / 8) + 80.0F + (float)(lllIlIIlIIlll.FONT_HEIGHT * 4), 16777215, true);
      var10001 = false;
      lllIlIIlIIlll.drawCenteredString("最终解释�?�归AquaVit所有 已�?�?�循GPLV3�??议", (float)lllIlIIIllllI.width / 2.0F - (float)26, (float)lllIlIIIllllI.height / 8.0F + 80.0F + (float)(lllIlIIlIIlll.FONT_HEIGHT * 5), 16777215, true);
      var10001 = false;
      lllIlIIlIIlll.drawCenteredString("如果使用过程中�?�现BUG 请�??馈QQ2924270322", (float)lllIlIIIllllI.width / 2.0F - (float)26, (float)lllIlIIIllllI.height / 8.0F + 80.0F + (float)(lllIlIIlIIlll.FONT_HEIGHT * 6), 16777215, true);
      var10001 = false;
      lllIlIIlIIlll.drawCenteredString("2020/8/18 UPDATE", (float)lllIlIIIllllI.width / 2.0F - (float)3, (float)lllIlIIIllllI.height / 8.0F + 80.0F + (float)(lllIlIIlIIlll.FONT_HEIGHT * 7), 16777215, true);
      var10001 = false;
      super.drawScreen(lllIlIIIlllII, lllIlIIlIIIlI, lllIlIIIllIlI);
      GL11.glScalef(2.0F, 2.0F, 2.0F);
      Fonts.font40.drawCenteredString("爷懒得写说明", (float)(lllIlIIIllllI.width / 2) / 2.0F, (float)lllIlIIIllllI.height / 8.0F / (float)2 - (float)2, (new Color(0, 140, 255)).getRGB(), true);
      var10001 = false;
   }

   protected void keyTyped(char lllIlIIIlIIII, int lllIlIIIIllII) {
      if (1 != lllIlIIIIllII) {
         super.keyTyped(lllIlIIIlIIII, lllIlIIIIllII);
      }
   }
}
