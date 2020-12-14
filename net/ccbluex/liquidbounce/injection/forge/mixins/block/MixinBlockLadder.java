//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.injection.forge.mixins.block;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.movement.FastClimb;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@SideOnly(Side.CLIENT)
@Mixin({BlockLadder.class})
public abstract class MixinBlockLadder extends MixinBlock {
   // $FF: synthetic field
   @Shadow
   @Final
   public static PropertyDirection FACING;

   @Overwrite
   public void setBlockBoundsBasedOnState(IBlockAccess lllllllllllllllllllIlIIIIIIllIll, BlockPos lllllllllllllllllllIlIIIIIIllIlI) {
      IBlockState lllllllllllllllllllIlIIIIIIllIIl = lllllllllllllllllllIlIIIIIIllIll.getBlockState(lllllllllllllllllllIlIIIIIIllIlI);
      if (lllllllllllllllllllIlIIIIIIllIIl.getBlock() instanceof BlockLadder) {
         long lllllllllllllllllllIlIIIIIIlIlII = (FastClimb)LiquidBounce.moduleManager.getModule(FastClimb.class);
         long lllllllllllllllllllIlIIIIIIlIIll = lllllllllllllllllllIlIIIIIIlIlII.getState() && ((String)lllllllllllllllllllIlIIIIIIlIlII.modeValue.get()).equalsIgnoreCase("AAC") ? 0.99F : 0.125F;
         switch((EnumFacing)lllllllllllllllllllIlIIIIIIllIIl.getValue(FACING)) {
         case NORTH:
            lllllllllllllllllllIlIIIIIIlllII.setBlockBounds(0.0F, 0.0F, 1.0F - lllllllllllllllllllIlIIIIIIlIIll, 1.0F, 1.0F, 1.0F);
            break;
         case SOUTH:
            lllllllllllllllllllIlIIIIIIlllII.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, lllllllllllllllllllIlIIIIIIlIIll);
            break;
         case WEST:
            lllllllllllllllllllIlIIIIIIlllII.setBlockBounds(1.0F - lllllllllllllllllllIlIIIIIIlIIll, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            break;
         case EAST:
         default:
            lllllllllllllllllllIlIIIIIIlllII.setBlockBounds(0.0F, 0.0F, 0.0F, lllllllllllllllllllIlIIIIIIlIIll, 1.0F, 1.0F);
         }
      }

   }
}
