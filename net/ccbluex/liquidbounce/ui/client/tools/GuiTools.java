//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.ui.client.tools;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\f\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0014J \u0010\b\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u000e\u001a\u00020\u0005H\u0016J\u0018\u0010\u000f\u001a\u00020\u00052\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\nH\u0014R\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/client/tools/GuiTools;", "Lnet/minecraft/client/gui/GuiScreen;", "prevGui", "(Lnet/minecraft/client/gui/GuiScreen;)V", "actionPerformed", "", "button", "Lnet/minecraft/client/gui/GuiButton;", "drawScreen", "mouseX", "", "mouseY", "partialTicks", "", "initGui", "keyTyped", "typedChar", "", "keyCode", "LiquidSense"}
)
public final class GuiTools extends GuiScreen {
   // $FF: synthetic field
   private final GuiScreen prevGui;

   protected void actionPerformed(@NotNull GuiButton lIIIlIIIlllIIlI) {
      Intrinsics.checkParameterIsNotNull(lIIIlIIIlllIIlI, "button");
      switch(lIIIlIIIlllIIlI.id) {
      case 0:
         lIIIlIIIlllIlIl.mc.displayGuiScreen(lIIIlIIIlllIlIl.prevGui);
         break;
      case 1:
         lIIIlIIIlllIlIl.mc.displayGuiScreen((GuiScreen)(new GuiPortScanner((GuiScreen)lIIIlIIIlllIlIl)));
      }

   }

   public void initGui() {
      lIIIlIIIllllIll.buttonList.add(new GuiButton(1, lIIIlIIIllllIll.width / 2 - 100, lIIIlIIIllllIll.height / 4 + 48 + 25, "Port Scanner"));
      boolean var10001 = false;
      lIIIlIIIllllIll.buttonList.add(new GuiButton(0, lIIIlIIIllllIll.width / 2 - 100, lIIIlIIIllllIll.height / 4 + 48 + 50 + 5, "Back"));
      var10001 = false;
   }

   public void drawScreen(int lIIIlIIIllIIIlI, int lIIIlIIIlIllIIl, float lIIIlIIIlIlIlll) {
      lIIIlIIIllIIlII.drawBackground(0);
      Fonts.fontBold180.drawCenteredString("Tools", (float)lIIIlIIIllIIlII.width / 2.0F, (float)lIIIlIIIllIIlII.height / 8.0F + 5.0F, 4673984, true);
      boolean var10001 = false;
      super.drawScreen(lIIIlIIIllIIIlI, lIIIlIIIlIllIIl, lIIIlIIIlIlIlll);
   }

   public GuiTools(@NotNull GuiScreen lIIIlIIIlIIIIlI) {
      Intrinsics.checkParameterIsNotNull(lIIIlIIIlIIIIlI, "prevGui");
      super();
      lIIIlIIIlIIIIll.prevGui = lIIIlIIIlIIIIlI;
   }

   protected void keyTyped(char lIIIlIIIlIIlIll, int lIIIlIIIlIIlIlI) {
      if (1 == lIIIlIIIlIIlIlI) {
         lIIIlIIIlIIllII.mc.displayGuiScreen(lIIIlIIIlIIllII.prevGui);
      } else {
         super.keyTyped(lIIIlIIIlIIlIll, lIIIlIIIlIIlIlI);
      }
   }
}
