package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.render.Chams;
import net.minecraft.client.renderer.tileentity.TileEntityChestRenderer;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({TileEntityChestRenderer.class})
public class MixinTileEntityChestRenderer {
   @Inject(
      method = {"renderTileEntityAt"},
      at = {@At("RETURN")}
   )
   private void injectChamsPost(CallbackInfo lIIIIIIlIIIIII) {
      double lIIIIIIIlllllI = (Chams)LiquidBounce.moduleManager.getModule(Chams.class);
      if (lIIIIIIIlllllI.getState() && (Boolean)lIIIIIIIlllllI.getChestsValue().get()) {
         GL11.glPolygonOffset(1.0F, 1000000.0F);
         GL11.glDisable(32823);
      }

   }

   @Inject(
      method = {"renderTileEntityAt"},
      at = {@At("HEAD")}
   )
   private void injectChamsPre(CallbackInfo lIIIIIIlIIIlIl) {
      Chams lIIIIIIlIIIlII = (Chams)LiquidBounce.moduleManager.getModule(Chams.class);
      if (lIIIIIIlIIIlII.getState() && (Boolean)lIIIIIIlIIIlII.getChestsValue().get()) {
         GL11.glEnable(32823);
         GL11.glPolygonOffset(1.0F, -1000000.0F);
      }

   }
}
