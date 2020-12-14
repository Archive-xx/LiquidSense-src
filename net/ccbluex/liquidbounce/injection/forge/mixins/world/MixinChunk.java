package net.ccbluex.liquidbounce.injection.forge.mixins.world;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.render.ProphuntESP;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Chunk.class})
public class MixinChunk {
   @Inject(
      method = {"setBlockState"},
      at = {@At("HEAD")}
   )
   private void setProphuntBlock(BlockPos llllllllllllllllllIIlIIIlIIlIlII, IBlockState llllllllllllllllllIIlIIIlIIlIIll, CallbackInfoReturnable llllllllllllllllllIIlIIIlIIlIIlI) {
      ProphuntESP llllllllllllllllllIIlIIIlIIlIIIl = (ProphuntESP)LiquidBounce.moduleManager.getModule(ProphuntESP.class);
      if (llllllllllllllllllIIlIIIlIIlIIIl.getState()) {
         synchronized(llllllllllllllllllIIlIIIlIIlIIIl.blocks) {
            llllllllllllllllllIIlIIIlIIlIIIl.blocks.put(llllllllllllllllllIIlIIIlIIlIlII, System.currentTimeMillis());
            boolean var10001 = false;
         }
      }

   }
}
