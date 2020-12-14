package net.ccbluex.liquidbounce.ui.client.clickgui.elements;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ButtonElement extends Element {
   // $FF: synthetic field
   public int hoverTime;
   // $FF: synthetic field
   protected String displayName;
   // $FF: synthetic field
   protected int color = 16777215;

   public int getColor() {
      return llIlIIIlIIlIIll.color;
   }

   public void drawScreen(int llIlIIIlIlIlIlI, int llIlIIIlIlIllIl, float llIlIIIlIlIlIII) {
      LiquidBounce.clickGui.style.drawButtonElement(llIlIIIlIlIlIlI, llIlIIIlIlIllIl, llIlIIIlIlIllll);
      super.drawScreen(llIlIIIlIlIlIlI, llIlIIIlIlIllIl, llIlIIIlIlIlIII);
   }

   public int getHeight() {
      return 16;
   }

   public boolean isHovering(int llIlIIIlIIlllll, int llIlIIIlIIllllI) {
      return llIlIIIlIIlllll >= llIlIIIlIlIIIll.getX() && llIlIIIlIIlllll <= llIlIIIlIlIIIll.getX() + llIlIIIlIlIIIll.getWidth() && llIlIIIlIIllllI >= llIlIIIlIlIIIll.getY() && llIlIIIlIIllllI <= llIlIIIlIlIIIll.getY() + 16;
   }

   public void createButton(String llIlIIIlIllIllI) {
      llIlIIIlIllIlll.displayName = llIlIIIlIllIllI;
   }

   public ButtonElement(String llIlIIIlIlllIlI) {
      llIlIIIlIllllIl.createButton(llIlIIIlIlllIlI);
   }

   public void setColor(int llIlIIIlIIlIlIl) {
      llIlIIIlIIlIllI.color = llIlIIIlIIlIlIl;
   }

   public String getDisplayName() {
      return llIlIIIlIIlllII.displayName;
   }
}
