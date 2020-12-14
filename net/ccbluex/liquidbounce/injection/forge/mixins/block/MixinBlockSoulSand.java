package net.ccbluex.liquidbounce.injection.forge.mixins.block;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.movement.NoSlow;
import net.minecraft.block.BlockSoulSand;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SideOnly(Side.CLIENT)
@Mixin({BlockSoulSand.class})
public class MixinBlockSoulSand {
   @Inject(
      method = {"onEntityCollidedWithBlock"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onEntityCollidedWithBlock(CallbackInfo llIIlllIIIIIlll) {
      NoSlow llIIlllIIIIlIII = (NoSlow)LiquidBounce.moduleManager.getModule(NoSlow.class);
      if (llIIlllIIIIlIII.getState() && (Boolean)llIIlllIIIIlIII.getSoulsandValue().get()) {
         llIIlllIIIIIlll.cancel();
      }

   }
}
