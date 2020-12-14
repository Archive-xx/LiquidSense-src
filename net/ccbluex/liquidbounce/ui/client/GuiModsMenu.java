//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.ui.client;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.GuiModList;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\f\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0014J \u0010\b\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u000e\u001a\u00020\u0005H\u0016J\u0018\u0010\u000f\u001a\u00020\u00052\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\nH\u0014R\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/client/GuiModsMenu;", "Lnet/minecraft/client/gui/GuiScreen;", "prevGui", "(Lnet/minecraft/client/gui/GuiScreen;)V", "actionPerformed", "", "button", "Lnet/minecraft/client/gui/GuiButton;", "drawScreen", "mouseX", "", "mouseY", "partialTicks", "", "initGui", "keyTyped", "typedChar", "", "keyCode", "LiquidSense"}
)
public final class GuiModsMenu extends GuiScreen {
   // $FF: synthetic field
   private final GuiScreen prevGui;

   protected void actionPerformed(@NotNull GuiButton lIllIlIlIlII) {
      Intrinsics.checkParameterIsNotNull(lIllIlIlIlII, "button");
      switch(lIllIlIlIlII.id) {
      case 0:
         lIllIlIlIlll.mc.displayGuiScreen((GuiScreen)(new GuiModList((GuiScreen)lIllIlIlIlll)));
         break;
      case 1:
         lIllIlIlIlll.mc.displayGuiScreen((GuiScreen)(new GuiScripts((GuiScreen)lIllIlIlIlll)));
         break;
      case 2:
         lIllIlIlIlll.mc.displayGuiScreen(lIllIlIlIlll.prevGui);
      }

   }

   public GuiModsMenu(@NotNull GuiScreen lIllIIlllIIl) {
      Intrinsics.checkParameterIsNotNull(lIllIIlllIIl, "prevGui");
      super();
      lIllIIlllIlI.prevGui = lIllIIlllIIl;
   }

   public void drawScreen(int lIllIlIIlllI, int lIllIlIIlIIl, float lIllIlIIlIII) {
      lIllIlIIllll.drawBackground(0);
      Fonts.fontBold180.drawCenteredString("Mods", (float)lIllIlIIllll.width / 2.0F, (float)lIllIlIIllll.height / 8.0F + 5.0F, 4673984, true);
      boolean var10001 = false;
      super.drawScreen(lIllIlIIlllI, lIllIlIIlIIl, lIllIlIIlIII);
   }

   public void initGui() {
      lIllIlIllIlI.buttonList.add(new GuiButton(0, lIllIlIllIlI.width / 2 - 100, lIllIlIllIlI.height / 4 + 48, "Forge Mods"));
      boolean var10001 = false;
      lIllIlIllIlI.buttonList.add(new GuiButton(1, lIllIlIllIlI.width / 2 - 100, lIllIlIllIlI.height / 4 + 48 + 25, "Scripts"));
      var10001 = false;
      lIllIlIllIlI.buttonList.add(new GuiButton(2, lIllIlIllIlI.width / 2 - 100, lIllIlIllIlI.height / 4 + 48 + 50, "Back"));
      var10001 = false;
   }

   protected void keyTyped(char lIllIlIIIIll, int lIllIlIIIIlI) {
      if (1 == lIllIlIIIIlI) {
         lIllIlIIIIIl.mc.displayGuiScreen(lIllIlIIIIIl.prevGui);
      } else {
         super.keyTyped(lIllIlIIIIll, lIllIlIIIIlI);
      }
   }
}
