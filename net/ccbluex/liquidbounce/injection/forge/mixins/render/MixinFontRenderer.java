package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.TextEvent;
import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@SideOnly(Side.CLIENT)
@Mixin({FontRenderer.class})
public class MixinFontRenderer {
   @ModifyVariable(
      method = {"getStringWidth"},
      at = @At("HEAD"),
      ordinal = 0
   )
   private String getStringWidth(String llllllllllllllllllIlIIIlIlIIllIl) {
      if (llllllllllllllllllIlIIIlIlIIllIl != null && LiquidBounce.eventManager != null) {
         TextEvent llllllllllllllllllIlIIIlIlIIllII = new TextEvent(llllllllllllllllllIlIIIlIlIIllIl);
         LiquidBounce.eventManager.callEvent(llllllllllllllllllIlIIIlIlIIllII);
         return llllllllllllllllllIlIIIlIlIIllII.getText();
      } else {
         return llllllllllllllllllIlIIIlIlIIllIl;
      }
   }

   @ModifyVariable(
      method = {"renderString"},
      at = @At("HEAD"),
      ordinal = 0
   )
   private String renderString(String llllllllllllllllllIlIIIlIlIlIlII) {
      if (llllllllllllllllllIlIIIlIlIlIlII != null && LiquidBounce.eventManager != null) {
         TextEvent llllllllllllllllllIlIIIlIlIlIIll = new TextEvent(llllllllllllllllllIlIIIlIlIlIlII);
         LiquidBounce.eventManager.callEvent(llllllllllllllllllIlIIIlIlIlIIll);
         return llllllllllllllllllIlIIIlIlIlIIll.getText();
      } else {
         return llllllllllllllllllIlIIIlIlIlIlII;
      }
   }
}
