//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.injection.forge.mixins.block;

import java.util.List;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.BlockBBEvent;
import net.ccbluex.liquidbounce.features.module.modules.combat.Criticals;
import net.ccbluex.liquidbounce.features.module.modules.exploit.GhostHand;
import net.ccbluex.liquidbounce.features.module.modules.player.NoFall;
import net.ccbluex.liquidbounce.features.module.modules.render.XRay;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SideOnly(Side.CLIENT)
@Mixin({Block.class})
public abstract class MixinBlock {
   // $FF: synthetic field
   @Shadow
   @Final
   protected BlockState blockState;

   @Inject(
      method = {"getAmbientOcclusionLightValue"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void getAmbientOcclusionLightValue(CallbackInfoReturnable<Float> llllIllIll) {
      if (LiquidBounce.moduleManager.getModule(XRay.class).getState()) {
         llllIllIll.setReturnValue(1.0F);
      }

   }

   @Inject(
      method = {"shouldSideBeRendered"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void shouldSideBeRendered(CallbackInfoReturnable<Boolean> lllllIllII) {
      XRay lllllIlIll = (XRay)LiquidBounce.moduleManager.getModule(XRay.class);
      if (lllllIlIll.getState()) {
         lllllIllII.setReturnValue(lllllIlIll.getXrayBlocks().contains(lllllIllIl));
      }

   }

   @Overwrite
   public void addCollisionBoxesToList(World llllllIlll, BlockPos llllllIllI, IBlockState llllllIlIl, AxisAlignedBB llllllllIl, List<AxisAlignedBB> llllllIIll, Entity lllllllIll) {
      AxisAlignedBB lllllllIlI = lllllllIII.getCollisionBoundingBox(llllllIlll, llllllIllI, llllllIlIl);
      BlockBBEvent lllllllIIl = new BlockBBEvent(llllllIllI, lllllllIII.blockState.getBlock(), lllllllIlI);
      LiquidBounce.eventManager.callEvent(lllllllIIl);
      lllllllIlI = lllllllIIl.getBoundingBox();
      if (lllllllIlI != null && llllllllIl.intersectsWith(lllllllIlI)) {
         llllllIIll.add(lllllllIlI);
         boolean var10001 = false;
      }

   }

   @Shadow
   public abstract AxisAlignedBB getCollisionBoundingBox(World var1, BlockPos var2, IBlockState var3);

   @Inject(
      method = {"getPlayerRelativeBlockHardness"},
      at = {@At("RETURN")},
      cancellable = true
   )
   public void modifyBreakSpeed(EntityPlayer llllIlIIlI, World llllIlIIIl, BlockPos llllIlIIII, CallbackInfoReturnable<Float> llllIIllll) {
      float llllIIlllI = (Float)llllIIllll.getReturnValue();
      if (llllIlIIlI.onGround) {
         NoFall llllIlIlIl = (NoFall)LiquidBounce.moduleManager.getModule(NoFall.class);
         float llllIIlIIl = (Criticals)LiquidBounce.moduleManager.getModule(Criticals.class);
         if (llllIlIlIl.getState() && ((String)llllIlIlIl.modeValue.get()).equalsIgnoreCase("NoGround") || llllIIlIIl.getState() && ((String)llllIIlIIl.getModeValue().get()).equalsIgnoreCase("NoGround")) {
            llllIIlllI /= 5.0F;
         }
      }

      llllIIllll.setReturnValue(llllIIlllI);
   }

   @Inject(
      method = {"isCollidable"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void isCollidable(CallbackInfoReturnable<Boolean> lllllIIIII) {
      GhostHand lllllIIIlI = (GhostHand)LiquidBounce.moduleManager.getModule(GhostHand.class);
      if (lllllIIIlI.getState() && (Integer)lllllIIIlI.getBlockValue().get() != Block.getIdFromBlock((Block)lllllIIlII)) {
         lllllIIIII.setReturnValue(false);
      }

   }

   @Shadow
   public abstract void setBlockBounds(float var1, float var2, float var3, float var4, float var5, float var6);
}
