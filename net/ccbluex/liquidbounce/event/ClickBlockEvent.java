package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0019\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"},
   d2 = {"Lnet/ccbluex/liquidbounce/event/ClickBlockEvent;", "Lnet/ccbluex/liquidbounce/event/Event;", "clickedBlock", "Lnet/minecraft/util/BlockPos;", "enumFacing", "Lnet/minecraft/util/EnumFacing;", "(Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;)V", "getClickedBlock", "()Lnet/minecraft/util/BlockPos;", "getEnumFacing", "()Lnet/minecraft/util/EnumFacing;", "LiquidSense"}
)
public final class ClickBlockEvent extends Event {
   // $FF: synthetic field
   @Nullable
   private final BlockPos clickedBlock;
   // $FF: synthetic field
   @Nullable
   private final EnumFacing enumFacing;

   public ClickBlockEvent(@Nullable BlockPos lIIIIIllIllIIIl, @Nullable EnumFacing lIIIIIllIllIIII) {
      lIIIIIllIllIlIl.clickedBlock = lIIIIIllIllIIIl;
      lIIIIIllIllIlIl.enumFacing = lIIIIIllIllIIII;
   }

   @Nullable
   public final EnumFacing getEnumFacing() {
      return lIIIIIllIlllIlI.enumFacing;
   }

   @Nullable
   public final BlockPos getClickedBlock() {
      return lIIIIIllIllllIl.clickedBlock;
   }
}
