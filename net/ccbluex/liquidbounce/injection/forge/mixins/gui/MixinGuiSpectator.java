package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.minecraft.client.gui.GuiSpectator;
import net.minecraft.client.gui.ScaledResolution;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GuiSpectator.class})
public class MixinGuiSpectator {
   @Inject(
      method = {"renderTooltip"},
      at = {@At("RETURN")}
   )
   private void renderTooltipPost(ScaledResolution lIIIIIllIlIlIlI, float lIIIIIllIlIlIIl, CallbackInfo lIIIIIllIlIlIII) {
      LiquidBounce.eventManager.callEvent(new Render2DEvent(lIIIIIllIlIlIIl));
   }
}
