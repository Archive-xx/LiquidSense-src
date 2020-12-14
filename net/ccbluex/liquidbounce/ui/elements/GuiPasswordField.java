//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.ui.elements;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003¢\u0006\u0002\u0010\nJ\b\u0010\u000b\u001a\u00020\fH\u0016¨\u0006\r"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/elements/GuiPasswordField;", "Lnet/minecraft/client/gui/GuiTextField;", "componentId", "", "fontrendererObj", "Lnet/minecraft/client/gui/FontRenderer;", "x", "y", "par5Width", "par6Height", "(ILnet/minecraft/client/gui/FontRenderer;IIII)V", "drawTextBox", "", "LiquidSense"}
)
public final class GuiPasswordField extends GuiTextField {
   public void drawTextBox() {
      short lIlIlIIlllllIII = lIlIlIIlllllIIl.getText();
      char lIlIlIIllllIlll = new StringBuilder();
      long lIlIlIIllllIllI = 0;
      String var10000 = lIlIlIIlllllIIl.getText();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "text");

      for(int lIlIlIIllllIlIl = ((CharSequence)var10000).length(); lIlIlIIllllIllI < lIlIlIIllllIlIl; ++lIlIlIIllllIllI) {
         lIlIlIIllllIlll.append('*');
         boolean var10001 = false;
      }

      lIlIlIIlllllIIl.setText(String.valueOf(lIlIlIIllllIlll));
      super.drawTextBox();
      lIlIlIIlllllIIl.setText(lIlIlIIlllllIII);
   }

   public GuiPasswordField(int lIlIlIIlllIIlIl, @NotNull FontRenderer lIlIlIIlllIlIll, int lIlIlIIlllIIIll, int lIlIlIIlllIlIIl, int lIlIlIIlllIlIII, int lIlIlIIlllIIlll) {
      Intrinsics.checkParameterIsNotNull(lIlIlIIlllIlIll, "fontrendererObj");
      super(lIlIlIIlllIIlIl, lIlIlIIlllIlIll, lIlIlIIlllIIIll, lIlIlIIlllIlIIl, lIlIlIIlllIlIII, lIlIlIIlllIIlll);
   }
}
