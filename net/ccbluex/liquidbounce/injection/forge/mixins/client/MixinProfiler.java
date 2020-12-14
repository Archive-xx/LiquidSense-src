package net.ccbluex.liquidbounce.injection.forge.mixins.client;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.utils.ClassUtils;
import net.minecraft.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Profiler.class})
public class MixinProfiler {
   @Inject(
      method = {"startSection"},
      at = {@At("HEAD")}
   )
   private void startSection(String llllllllllllllllllIllIIlIlIIIlIl, CallbackInfo llllllllllllllllllIllIIlIlIIIIll) {
      if (llllllllllllllllllIllIIlIlIIIlIl.equals("bossHealth") && ClassUtils.hasClass("net.labymod.api.LabyModAPI")) {
         LiquidBounce.eventManager.callEvent(new Render2DEvent(0.0F));
      }

   }
}
