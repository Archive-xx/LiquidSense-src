//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.ui.client.clickgui.elements;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.Module;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;

@SideOnly(Side.CLIENT)
public class ModuleElement extends ButtonElement {
   // $FF: synthetic field
   private final Module module;
   // $FF: synthetic field
   private boolean wasPressed;
   // $FF: synthetic field
   private boolean showSettings;
   // $FF: synthetic field
   public int slowlyFade;
   // $FF: synthetic field
   private float settingsWidth = 0.0F;
   // $FF: synthetic field
   public int slowlySettingsYPos;

   public boolean isntPressed() {
      return !lllllllIlIlllII.wasPressed;
   }

   public void drawScreen(int lllllllIllllIll, int lllllllIlllIllI, float lllllllIllllIIl) {
      LiquidBounce.clickGui.style.drawModuleElement(lllllllIllllIll, lllllllIlllIllI, lllllllIllllIII);
   }

   public ModuleElement(Module llllllllIIIIIII) {
      super((String)null);
      llllllllIIIIIIl.displayName = llllllllIIIIIII.getName();
      llllllllIIIIIIl.module = llllllllIIIIIII;
   }

   public boolean isShowSettings() {
      return lllllllIllIIlIl.showSettings;
   }

   public Module getModule() {
      return lllllllIllIlIII.module;
   }

   public void setSettingsWidth(float lllllllIlIIllll) {
      lllllllIlIlIIlI.settingsWidth = lllllllIlIIllll;
   }

   public float getSettingsWidth() {
      return lllllllIlIlIllI.settingsWidth;
   }

   public void updatePressed() {
      lllllllIlIllIIl.wasPressed = Mouse.isButtonDown(0);
   }

   public void setShowSettings(boolean lllllllIllIIIII) {
      lllllllIlIlllll.showSettings = lllllllIllIIIII;
   }

   public void mouseClicked(int lllllllIlllIIII, int lllllllIllIlIll, int lllllllIllIlllI) {
      if (lllllllIllIlllI == 0 && lllllllIllIllIl.isHovering(lllllllIlllIIII, lllllllIllIlIll) && lllllllIllIllIl.isVisible()) {
         lllllllIllIllIl.module.toggle();
         mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
      }

      if (lllllllIllIlllI == 1 && lllllllIllIllIl.isHovering(lllllllIlllIIII, lllllllIllIlIll) && lllllllIllIllIl.isVisible()) {
         lllllllIllIllIl.showSettings = !lllllllIllIllIl.showSettings;
         mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
      }

   }
}
