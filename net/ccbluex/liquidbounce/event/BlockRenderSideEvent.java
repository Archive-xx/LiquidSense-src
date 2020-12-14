package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0007\u0018\u00002\u00020\u0001BM\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\t\u0012\u0006\u0010\u000b\u001a\u00020\t\u0012\u0006\u0010\f\u001a\u00020\t\u0012\u0006\u0010\r\u001a\u00020\t\u0012\u0006\u0010\u000e\u001a\u00020\t¢\u0006\u0002\u0010\u000f¨\u0006\u0010"},
   d2 = {"Lnet/ccbluex/liquidbounce/event/BlockRenderSideEvent;", "Lnet/ccbluex/liquidbounce/event/Event;", "world", "Lnet/minecraft/world/IBlockAccess;", "pos", "Lnet/minecraft/util/BlockPos;", "side", "Lnet/minecraft/util/EnumFacing;", "maxX", "", "minX", "maxY", "minY", "maxZ", "minZ", "(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;DDDDDD)V", "LiquidSense"}
)
public final class BlockRenderSideEvent extends Event {
   public BlockRenderSideEvent(@NotNull IBlockAccess llIIllIlIlIIIl, @NotNull BlockPos llIIllIlIllIlI, @NotNull EnumFacing llIIllIlIIllll, double llIIllIlIllIII, double llIIllIlIlIlll, double llIIllIlIlIllI, double llIIllIlIlIlIl, double llIIllIlIlIlII, double llIIllIlIlIIll) {
      Intrinsics.checkParameterIsNotNull(llIIllIlIlIIIl, "world");
      Intrinsics.checkParameterIsNotNull(llIIllIlIllIlI, "pos");
      Intrinsics.checkParameterIsNotNull(llIIllIlIIllll, "side");
      super();
   }
}
