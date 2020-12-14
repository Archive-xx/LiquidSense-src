//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.BlockBBEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
   name = "BlockWalk",
   description = "Allows you to walk on non-fullblock blocks.",
   category = ModuleCategory.MOVEMENT
)
@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/BlockWalk;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "cobwebValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "snowValue", "onBlockBB", "", "event", "Lnet/ccbluex/liquidbounce/event/BlockBBEvent;", "LiquidSense"}
)
public final class BlockWalk extends Module {
   // $FF: synthetic field
   private final BoolValue cobwebValue = new BoolValue("Cobweb", true);
   // $FF: synthetic field
   private final BoolValue snowValue = new BoolValue("Snow", true);

   @EventTarget
   public final void onBlockBB(@NotNull BlockBBEvent llIlllIIIIllIIl) {
      Intrinsics.checkParameterIsNotNull(llIlllIIIIllIIl, "event");
      if ((Boolean)llIlllIIIIllIlI.cobwebValue.get() && Intrinsics.areEqual((Object)llIlllIIIIllIIl.getBlock(), (Object)Blocks.web) || (Boolean)llIlllIIIIllIlI.snowValue.get() && Intrinsics.areEqual((Object)llIlllIIIIllIIl.getBlock(), (Object)Blocks.snow_layer)) {
         llIlllIIIIllIIl.setBoundingBox(AxisAlignedBB.fromBounds((double)llIlllIIIIllIIl.getX(), (double)llIlllIIIIllIIl.getY(), (double)llIlllIIIIllIIl.getZ(), (double)llIlllIIIIllIIl.getX() + 1.0D, (double)llIlllIIIIllIIl.getY() + 1.0D, (double)llIlllIIIIllIIl.getZ() + 1.0D));
      }

   }
}
