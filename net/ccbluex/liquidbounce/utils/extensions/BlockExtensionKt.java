//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.utils.extensions;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 2,
   d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002\u001a\n\u0010\u0003\u001a\u00020\u0004*\u00020\u0002¨\u0006\u0005"},
   d2 = {"getBlock", "Lnet/minecraft/block/Block;", "Lnet/minecraft/util/BlockPos;", "getVec", "Lnet/minecraft/util/Vec3;", "LiquidSense"}
)
public final class BlockExtensionKt {
   @Nullable
   public static final Block getBlock(@NotNull BlockPos llIIIlllIIIIlIl) {
      Intrinsics.checkParameterIsNotNull(llIIIlllIIIIlIl, "$this$getBlock");
      return BlockUtils.getBlock(llIIIlllIIIIlIl);
   }

   @NotNull
   public static final Vec3 getVec(@NotNull BlockPos llIIIlllIIIIIlI) {
      Intrinsics.checkParameterIsNotNull(llIIIlllIIIIIlI, "$this$getVec");
      return new Vec3((double)llIIIlllIIIIIlI.getX() + 0.5D, (double)llIIIlllIIIIIlI.getY() + 0.5D, (double)llIIIlllIIIIIlI.getZ() + 0.5D);
   }
}
