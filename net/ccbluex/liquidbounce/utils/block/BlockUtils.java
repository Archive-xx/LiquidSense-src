//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.utils.block;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\u001eB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0007J\u0018\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0007J\u0018\u0010\f\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0007J\u0014\u0010\r\u001a\u0004\u0018\u00010\u000e2\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0007J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0007J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0014\u0010\u0015\u001a\u0004\u0018\u00010\u00162\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0007J\u0012\u0010\u0017\u001a\u00020\u00182\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0007J\u0012\u0010\u0019\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0007J\u0012\u0010\u001a\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0007J\u001c\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u000e0\u001c2\u0006\u0010\u001d\u001a\u00020\u0012H\u0007¨\u0006\u001f"},
   d2 = {"Lnet/ccbluex/liquidbounce/utils/block/BlockUtils;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "canBeClicked", "", "blockPos", "Lnet/minecraft/util/BlockPos;", "collideBlock", "axisAlignedBB", "Lnet/minecraft/util/AxisAlignedBB;", "collide", "Lnet/ccbluex/liquidbounce/utils/block/BlockUtils$Collidable;", "collideBlockIntersects", "getBlock", "Lnet/minecraft/block/Block;", "getBlockName", "", "id", "", "getCenterDistance", "", "getMaterial", "Lnet/minecraft/block/material/Material;", "getState", "Lnet/minecraft/block/state/IBlockState;", "isFullBlock", "isReplaceable", "searchBlocks", "", "radius", "Collidable", "LiquidSense"}
)
public final class BlockUtils extends MinecraftInstance {
   // $FF: synthetic field
   public static final BlockUtils INSTANCE;

   @JvmStatic
   public static final boolean collideBlockIntersects(@NotNull AxisAlignedBB lllIIIIIIlllll, @NotNull BlockUtils.Collidable lllIIIIIIlllII) {
      Intrinsics.checkParameterIsNotNull(lllIIIIIIlllll, "axisAlignedBB");
      Intrinsics.checkParameterIsNotNull(lllIIIIIIlllII, "collide");
      EntityPlayerSP var10000 = access$getMc$p$s1046033730().thePlayer;
      Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
      float lllIIIIIIllIll = MathHelper.floor_double(var10000.getEntityBoundingBox().minX);
      var10000 = access$getMc$p$s1046033730().thePlayer;
      Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");

      for(int lllIIIIIIllIlI = MathHelper.floor_double(var10000.getEntityBoundingBox().maxX) + 1; lllIIIIIIllIll < lllIIIIIIllIlI; ++lllIIIIIIllIll) {
         var10000 = access$getMc$p$s1046033730().thePlayer;
         Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
         byte lllIIIIIIllIIl = MathHelper.floor_double(var10000.getEntityBoundingBox().minZ);
         var10000 = access$getMc$p$s1046033730().thePlayer;
         Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");

         for(int lllIIIIIIllIII = MathHelper.floor_double(var10000.getEntityBoundingBox().maxZ) + 1; lllIIIIIIllIIl < lllIIIIIIllIII; ++lllIIIIIIllIIl) {
            long lllIIIIIIlIlll = new BlockPos((double)lllIIIIIIllIll, lllIIIIIIlllll.minY, (double)lllIIIIIIllIIl);
            Block lllIIIIIlIIIll = getBlock(lllIIIIIIlIlll);
            if (lllIIIIIIlllII.collideBlock(lllIIIIIlIIIll)) {
               if (lllIIIIIlIIIll != null) {
                  AxisAlignedBB var9 = lllIIIIIlIIIll.getCollisionBoundingBox((World)access$getMc$p$s1046033730().theWorld, lllIIIIIIlIlll, getState(lllIIIIIIlIlll));
                  if (var9 != null) {
                     AxisAlignedBB lllIIIIIlIIlII = var9;
                     var10000 = access$getMc$p$s1046033730().thePlayer;
                     Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
                     if (var10000.getEntityBoundingBox().intersectsWith(lllIIIIIlIIlII)) {
                        return true;
                     }
                     continue;
                  }
               }

               boolean var10001 = false;
            }
         }
      }

      return false;
   }

   @JvmStatic
   public static final double getCenterDistance(@NotNull BlockPos lllIIIIllIlIIl) {
      Intrinsics.checkParameterIsNotNull(lllIIIIllIlIIl, "blockPos");
      return access$getMc$p$s1046033730().thePlayer.getDistance((double)lllIIIIllIlIIl.getX() + 0.5D, (double)lllIIIIllIlIIl.getY() + 0.5D, (double)lllIIIIllIlIIl.getZ() + 0.5D);
   }

   @JvmStatic
   public static final boolean canBeClicked(@Nullable BlockPos lllIIIIlllIlII) {
      Block var10000 = getBlock(lllIIIIlllIlII);
      boolean var1;
      if (var10000 != null) {
         var1 = var10000.canCollideCheck(getState(lllIIIIlllIlII), false);
      } else {
         boolean var10001 = false;
         var1 = false;
      }

      if (var1) {
         WorldClient var2 = access$getMc$p$s1046033730().theWorld;
         Intrinsics.checkExpressionValueIsNotNull(var2, "mc.theWorld");
         if (var2.getWorldBorder().contains(lllIIIIlllIlII)) {
            var1 = true;
            return var1;
         }
      }

      var1 = false;
      return var1;
   }

   @JvmStatic
   public static final boolean isReplaceable(@Nullable BlockPos lllIIIIllllIll) {
      Material var10000 = getMaterial(lllIIIIllllIll);
      boolean var1;
      if (var10000 != null) {
         var1 = var10000.isReplaceable();
      } else {
         boolean var10001 = false;
         var1 = false;
      }

      return var1;
   }

   @JvmStatic
   @NotNull
   public static final Map<BlockPos, Block> searchBlocks(int lllIIIIlIlIlII) {
      Exception lllIIIIlIlIIIl = false;
      Map lllIIIIlIlIlIl = (Map)(new LinkedHashMap());
      Exception lllIIIIlIlIllI = lllIIIIlIlIlII;
      double lllIIIIlIlIIII = -lllIIIIlIlIlII + 1;
      if (lllIIIIlIlIlII >= lllIIIIlIlIIII) {
         while(true) {
            long lllIIIIlIIllll = lllIIIIlIlIlII;
            double lllIIIIlIIlllI = -lllIIIIlIlIlII + 1;
            if (lllIIIIlIlIlII >= lllIIIIlIIlllI) {
               while(true) {
                  char lllIIIIlIllIII = lllIIIIlIlIlII;
                  long lllIIIIlIIllII = -lllIIIIlIlIlII + 1;
                  if (lllIIIIlIlIlII >= lllIIIIlIIllII) {
                     while(true) {
                        BlockPos lllIIIIlIllIIl = new BlockPos((int)access$getMc$p$s1046033730().thePlayer.posX + lllIIIIlIlIllI, (int)access$getMc$p$s1046033730().thePlayer.posY + lllIIIIlIIllll, (int)access$getMc$p$s1046033730().thePlayer.posZ + lllIIIIlIllIII);
                        Block var10000 = getBlock(lllIIIIlIllIIl);
                        boolean var10001;
                        if (var10000 != null) {
                           int lllIIIIlIIlIlI = var10000;
                           lllIIIIlIlIlIl.put(lllIIIIlIllIIl, lllIIIIlIIlIlI);
                           var10001 = false;
                        } else {
                           var10001 = false;
                        }

                        if (lllIIIIlIllIII == lllIIIIlIIllII) {
                           break;
                        }

                        --lllIIIIlIllIII;
                     }
                  }

                  if (lllIIIIlIIllll == lllIIIIlIIlllI) {
                     break;
                  }

                  --lllIIIIlIIllll;
               }
            }

            if (lllIIIIlIlIllI == lllIIIIlIlIIII) {
               break;
            }

            --lllIIIIlIlIllI;
         }
      }

      return lllIIIIlIlIlIl;
   }

   @JvmStatic
   @Nullable
   public static final Material getMaterial(@Nullable BlockPos lllIIIIllllllI) {
      Block var10000 = getBlock(lllIIIIllllllI);
      Material var2;
      if (var10000 != null) {
         var2 = var10000.getMaterial();
      } else {
         boolean var10001 = false;
         var2 = null;
      }

      return var2;
   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   @JvmStatic
   public static final boolean isFullBlock(@Nullable BlockPos lllIIIIllIllIl) {
      Block var10000 = getBlock(lllIIIIllIllIl);
      if (var10000 != null) {
         AxisAlignedBB var2 = var10000.getCollisionBoundingBox((World)access$getMc$p$s1046033730().theWorld, lllIIIIllIllIl, getState(lllIIIIllIllIl));
         if (var2 != null) {
            AxisAlignedBB lllIIIIllIlllI = var2;
            return lllIIIIllIlllI.maxX - lllIIIIllIlllI.minX == 1.0D && lllIIIIllIlllI.maxY - lllIIIIllIlllI.minY == 1.0D && lllIIIIllIlllI.maxZ - lllIIIIllIlllI.minZ == 1.0D;
         }
      }

      boolean var10001 = false;
      return false;
   }

   static {
      BlockUtils lllIIIIIIlIIII = new BlockUtils();
      INSTANCE = lllIIIIIIlIIII;
   }

   private BlockUtils() {
   }

   @JvmStatic
   @Nullable
   public static final Block getBlock(@Nullable BlockPos lllIIIlIIIIIII) {
      WorldClient var10000 = access$getMc$p$s1046033730().theWorld;
      Block var3;
      if (var10000 != null) {
         IBlockState var2 = var10000.getBlockState(lllIIIlIIIIIII);
         if (var2 != null) {
            var3 = var2.getBlock();
            return var3;
         }
      }

      boolean var10001 = false;
      var3 = null;
      return var3;
   }

   @JvmStatic
   @NotNull
   public static final String getBlockName(int lllIIIIlllIIlI) {
      Block var10000 = Block.getBlockById(lllIIIIlllIIlI);
      Intrinsics.checkExpressionValueIsNotNull(var10000, "Block.getBlockById(id)");
      String var1 = var10000.getLocalizedName();
      Intrinsics.checkExpressionValueIsNotNull(var1, "Block.getBlockById(id).localizedName");
      return var1;
   }

   @JvmStatic
   public static final boolean collideBlock(@NotNull AxisAlignedBB lllIIIIIllIlII, @NotNull BlockUtils.Collidable lllIIIIIllIIll) {
      Intrinsics.checkParameterIsNotNull(lllIIIIIllIlII, "axisAlignedBB");
      Intrinsics.checkParameterIsNotNull(lllIIIIIllIIll, "collide");
      EntityPlayerSP var10000 = access$getMc$p$s1046033730().thePlayer;
      Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
      long lllIIIIIllIlll = MathHelper.floor_double(var10000.getEntityBoundingBox().minX);
      var10000 = access$getMc$p$s1046033730().thePlayer;
      Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");

      for(int lllIIIIIllIIIl = MathHelper.floor_double(var10000.getEntityBoundingBox().maxX) + 1; lllIIIIIllIlll < lllIIIIIllIIIl; ++lllIIIIIllIlll) {
         var10000 = access$getMc$p$s1046033730().thePlayer;
         Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
         String lllIIIIIlllIII = MathHelper.floor_double(var10000.getEntityBoundingBox().minZ);
         var10000 = access$getMc$p$s1046033730().thePlayer;
         Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");

         for(int lllIIIIIlIllll = MathHelper.floor_double(var10000.getEntityBoundingBox().maxZ) + 1; lllIIIIIlllIII < lllIIIIIlIllll; ++lllIIIIIlllIII) {
            char lllIIIIIlIlllI = getBlock(new BlockPos((double)lllIIIIIllIlll, lllIIIIIllIlII.minY, (double)lllIIIIIlllIII));
            if (!lllIIIIIllIIll.collideBlock(lllIIIIIlIlllI)) {
               return false;
            }
         }
      }

      return true;
   }

   @JvmStatic
   @NotNull
   public static final IBlockState getState(@Nullable BlockPos lllIIIIllllIII) {
      IBlockState var10000 = access$getMc$p$s1046033730().theWorld.getBlockState(lllIIIIllllIII);
      Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.theWorld.getBlockState(blockPos)");
      return var10000;
   }

   @Metadata(
      mv = {1, 1, 16},
      bv = {1, 0, 3},
      k = 1,
      d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&¨\u0006\u0006"},
      d2 = {"Lnet/ccbluex/liquidbounce/utils/block/BlockUtils$Collidable;", "", "collideBlock", "", "block", "Lnet/minecraft/block/Block;", "LiquidSense"}
   )
   public interface Collidable {
      boolean collideBlock(@Nullable Block var1);
   }
}
