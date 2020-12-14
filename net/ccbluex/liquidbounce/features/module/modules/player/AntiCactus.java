package net.ccbluex.liquidbounce.features.module.modules.player;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.BlockBBEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.minecraft.block.BlockCactus;
import net.minecraft.util.AxisAlignedBB;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/AntiCactus;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "onBlockBB", "", "event", "Lnet/ccbluex/liquidbounce/event/BlockBBEvent;", "LiquidSense"}
)
@ModuleInfo(
   name = "AntiCactus",
   description = "Prevents cactuses from damaging you.",
   category = ModuleCategory.PLAYER
)
public final class AntiCactus extends Module {
   @EventTarget
   public final void onBlockBB(@NotNull BlockBBEvent lllllllllllllllllIllIIlIlIlIIIll) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllIllIIlIlIlIIIll, "event");
      if (lllllllllllllllllIllIIlIlIlIIIll.getBlock() instanceof BlockCactus) {
         lllllllllllllllllIllIIlIlIlIIIll.setBoundingBox(new AxisAlignedBB((double)lllllllllllllllllIllIIlIlIlIIIll.getX(), (double)lllllllllllllllllIllIIlIlIlIIIll.getY(), (double)lllllllllllllllllIllIIlIlIlIIIll.getZ(), (double)lllllllllllllllllIllIIlIlIlIIIll.getX() + 1.0D, (double)lllllllllllllllllIllIIlIlIlIIIll.getY() + 1.0D, (double)lllllllllllllllllIllIIlIlIlIIIll.getZ() + 1.0D));
      }

   }
}
