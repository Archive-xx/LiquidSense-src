//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u0011\u0010\u000f\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0013\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0012R\u0011\u0010\u0015\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0012¨\u0006\u0017"},
   d2 = {"Lnet/ccbluex/liquidbounce/event/BlockBBEvent;", "Lnet/ccbluex/liquidbounce/event/Event;", "blockPos", "Lnet/minecraft/util/BlockPos;", "block", "Lnet/minecraft/block/Block;", "boundingBox", "Lnet/minecraft/util/AxisAlignedBB;", "(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/Block;Lnet/minecraft/util/AxisAlignedBB;)V", "getBlock", "()Lnet/minecraft/block/Block;", "getBoundingBox", "()Lnet/minecraft/util/AxisAlignedBB;", "setBoundingBox", "(Lnet/minecraft/util/AxisAlignedBB;)V", "x", "", "getX", "()I", "y", "getY", "z", "getZ", "LiquidSense"}
)
public final class BlockBBEvent extends Event {
   // $FF: synthetic field
   private final int y;
   // $FF: synthetic field
   private final int z;
   // $FF: synthetic field
   @NotNull
   private final Block block;
   // $FF: synthetic field
   @Nullable
   private AxisAlignedBB boundingBox;
   // $FF: synthetic field
   private final int x;

   public final int getY() {
      return lllIIIIIIlllllI.y;
   }

   public final int getZ() {
      return lllIIIIIIlllIlI.z;
   }

   @Nullable
   public final AxisAlignedBB getBoundingBox() {
      return lllIIIIIIllIlII.boundingBox;
   }

   public final void setBoundingBox(@Nullable AxisAlignedBB lllIIIIIIllIIII) {
      lllIIIIIIllIIIl.boundingBox = lllIIIIIIllIIII;
   }

   public final int getX() {
      return lllIIIIIlIIIIIl.x;
   }

   public BlockBBEvent(@NotNull BlockPos lllIIIIIIlIIlII, @NotNull Block lllIIIIIIlIIlll, @Nullable AxisAlignedBB lllIIIIIIlIIIlI) {
      Intrinsics.checkParameterIsNotNull(lllIIIIIIlIIlII, "blockPos");
      Intrinsics.checkParameterIsNotNull(lllIIIIIIlIIlll, "block");
      super();
      lllIIIIIIlIIlIl.block = lllIIIIIIlIIlll;
      lllIIIIIIlIIlIl.boundingBox = lllIIIIIIlIIIlI;
      lllIIIIIIlIIlIl.x = lllIIIIIIlIIlII.getX();
      lllIIIIIIlIIlIl.y = lllIIIIIIlIIlII.getY();
      lllIIIIIIlIIlIl.z = lllIIIIIIlIIlII.getZ();
   }

   @NotNull
   public final Block getBlock() {
      return lllIIIIIIlllIII.block;
   }
}
