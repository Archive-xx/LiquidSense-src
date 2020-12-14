//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.render.XRay;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({TileEntityRendererDispatcher.class})
public class MixinTileEntityRendererDispatcher {
   @Inject(
      method = {"renderTileEntity"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void renderTileEntity(TileEntity lllllllllllllllllllIlIlIIlIIIlIl, float lllllllllllllllllllIlIlIIlIIIlII, int lllllllllllllllllllIlIlIIlIIIIll, CallbackInfo lllllllllllllllllllIlIlIIlIIIIlI) {
      XRay lllllllllllllllllllIlIlIIlIIIIIl = (XRay)LiquidBounce.moduleManager.getModule(XRay.class);
      if (lllllllllllllllllllIlIlIIlIIIIIl.getState() && !lllllllllllllllllllIlIlIIlIIIIIl.getXrayBlocks().contains(lllllllllllllllllllIlIlIIlIIIlIl.getBlockType())) {
         lllllllllllllllllllIlIlIIlIIIIlI.cancel();
      }

   }
}
