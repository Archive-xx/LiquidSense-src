package net.ccbluex.liquidbounce.injection.forge.mixins.block;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.render.XRay;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({BlockModelRenderer.class})
public class MixinBlockModelRenderer {
   @Inject(
      method = {"renderModelStandard"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void renderModelStandard(IBlockAccess llllllllllllllllllIIlIIIllIIlIll, IBakedModel llllllllllllllllllIIlIIIllIIlIlI, Block llllllllllllllllllIIlIIIllIIlIIl, BlockPos llllllllllllllllllIIlIIIllIIlIII, WorldRenderer llllllllllllllllllIIlIIIllIIIlll, boolean llllllllllllllllllIIlIIIllIIIllI, CallbackInfoReturnable<Boolean> llllllllllllllllllIIlIIIllIIIIlI) {
      XRay llllllllllllllllllIIlIIIllIIIlII = (XRay)LiquidBounce.moduleManager.getModule(XRay.class);
      if (llllllllllllllllllIIlIIIllIIIlII.getState() && !llllllllllllllllllIIlIIIllIIIlII.getXrayBlocks().contains(llllllllllllllllllIIlIIIllIIlIIl)) {
         llllllllllllllllllIIlIIIllIIIIlI.setReturnValue(false);
      }

   }

   @Inject(
      method = {"renderModelAmbientOcclusion"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void renderModelAmbientOcclusion(IBlockAccess llllllllllllllllllIIlIIIllIlllIl, IBakedModel llllllllllllllllllIIlIIIllIlllII, Block llllllllllllllllllIIlIIIllIlIlIl, BlockPos llllllllllllllllllIIlIIIllIllIlI, WorldRenderer llllllllllllllllllIIlIIIllIllIIl, boolean llllllllllllllllllIIlIIIllIllIII, CallbackInfoReturnable<Boolean> llllllllllllllllllIIlIIIllIlIlII) {
      String llllllllllllllllllIIlIIIllIlIIlI = (XRay)LiquidBounce.moduleManager.getModule(XRay.class);
      if (llllllllllllllllllIIlIIIllIlIIlI.getState() && !llllllllllllllllllIIlIIIllIlIIlI.getXrayBlocks().contains(llllllllllllllllllIIlIIIllIlIlIl)) {
         llllllllllllllllllIIlIIIllIlIlII.setReturnValue(false);
      }

   }
}
