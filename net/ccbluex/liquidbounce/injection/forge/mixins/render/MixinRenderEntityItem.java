package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.render.Chams;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({RenderEntityItem.class})
public class MixinRenderEntityItem {
   @Inject(
      method = {"doRender"},
      at = {@At("RETURN")}
   )
   private void injectChamsPost(CallbackInfo llllllllllIIllI) {
      Chams llllllllllIIlII = (Chams)LiquidBounce.moduleManager.getModule(Chams.class);
      if (llllllllllIIlII.getState() && (Boolean)llllllllllIIlII.getItemsValue().get()) {
         GL11.glPolygonOffset(1.0F, 1000000.0F);
         GL11.glDisable(32823);
      }

   }

   @Inject(
      method = {"doRender"},
      at = {@At("HEAD")}
   )
   private void injectChamsPre(CallbackInfo llllllllllIllll) {
      Chams llllllllllIlllI = (Chams)LiquidBounce.moduleManager.getModule(Chams.class);
      if (llllllllllIlllI.getState() && (Boolean)llllllllllIlllI.getItemsValue().get()) {
         GL11.glEnable(32823);
         GL11.glPolygonOffset(1.0F, -1000000.0F);
      }

   }
}
