//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.render;

import com.google.common.base.Predicate;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Cylinder;

@ModuleInfo(
   name = "Projectiles",
   description = "Allows you to see where arrows will land.",
   category = ModuleCategory.RENDER
)
@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/render/Projectiles;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "dynamicBowPower", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "onRender3D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "LiquidSense"}
)
public final class Projectiles extends Module {
   // $FF: synthetic field
   private final BoolValue dynamicBowPower = new BoolValue("DynamicBowPower", true);

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   @EventTarget
   public final void onRender3D(@NotNull Render3DEvent lIlIlIllIllIll) {
      Intrinsics.checkParameterIsNotNull(lIlIlIllIllIll, "event");
      EntityPlayerSP var10000 = access$getMc$p$s1046033730().thePlayer;
      Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
      boolean var10001;
      if (var10000.getHeldItem() == null) {
         var10001 = false;
      } else {
         var10001 = false;
         var10000 = access$getMc$p$s1046033730().thePlayer;
         Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
         ItemStack var39 = var10000.getHeldItem();
         Intrinsics.checkExpressionValueIsNotNull(var39, "mc.thePlayer.heldItem");
         Item lIlIlIllIlllll = var39.getItem();
         Minecraft var40 = access$getMc$p$s1046033730();
         Intrinsics.checkExpressionValueIsNotNull(var40, "mc");
         long lIlIlIllIllIIl = var40.getRenderManager();
         Exception lIlIlIllIllIII = false;
         float lIlIlIlllIIIlI = 1.5F;
         byte lIlIlIllIlIllI = 0.99F;
         float lIlIlIlllIIlII = 0.0F;
         float lIlIlIllIlIlII = 0.0F;
         float lIlIllIIIIIIII;
         if (lIlIlIllIlllll instanceof ItemBow) {
            if ((Boolean)lIlIlIllIlllII.dynamicBowPower.get()) {
               var10000 = access$getMc$p$s1046033730().thePlayer;
               Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
               if (!var10000.isUsingItem()) {
                  return;
               }
            }

            lIlIlIllIllIII = true;
            lIlIlIlllIIlII = 0.05F;
            lIlIlIllIlIlII = 0.3F;
            int var43;
            if ((Boolean)lIlIlIllIlllII.dynamicBowPower.get()) {
               var10000 = access$getMc$p$s1046033730().thePlayer;
               Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
               var43 = var10000.getItemInUseDuration();
            } else {
               var43 = lIlIlIllIlllll.getMaxItemUseDuration(new ItemStack(lIlIlIllIlllll));
            }

            lIlIllIIIIIIII = (float)var43 / 20.0F;
            lIlIllIIIIIIII = (lIlIllIIIIIIII * lIlIllIIIIIIII + lIlIllIIIIIIII * 2.0F) / 3.0F;
            if (lIlIllIIIIIIII < 0.1F) {
               return;
            }

            if (lIlIllIIIIIIII > 1.0F) {
               lIlIllIIIIIIII = 1.0F;
            }

            lIlIlIlllIIIlI = lIlIllIIIIIIII * 3.0F;
         } else if (lIlIlIllIlllll instanceof ItemFishingRod) {
            lIlIlIlllIIlII = 0.04F;
            lIlIlIllIlIlII = 0.25F;
            lIlIlIllIlIllI = 0.92F;
         } else {
            label160: {
               if (lIlIlIllIlllll instanceof ItemPotion) {
                  var10000 = access$getMc$p$s1046033730().thePlayer;
                  Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
                  var39 = var10000.getHeldItem();
                  Intrinsics.checkExpressionValueIsNotNull(var39, "mc.thePlayer.heldItem");
                  if (ItemPotion.isSplash(var39.getItemDamage())) {
                     lIlIlIlllIIlII = 0.05F;
                     lIlIlIllIlIlII = 0.25F;
                     lIlIlIlllIIIlI = 0.5F;
                     break label160;
                  }
               }

               if (!(lIlIlIllIlllll instanceof ItemSnowball) && !(lIlIlIllIlllll instanceof ItemEnderPearl) && !(lIlIlIllIlllll instanceof ItemEgg)) {
                  return;
               }

               lIlIlIlllIIlII = 0.03F;
               lIlIlIllIlIlII = 0.25F;
            }
         }

         float lIlIlIllIlIIlI;
         double lIlIlIlllIlIII;
         double lIlIlIllIlIIII;
         double lIlIlIlllIlIlI;
         double lIlIlIlllIlIll;
         byte var44;
         label147: {
            lIlIllIIIIIIII = RotationUtils.targetRotation != null ? RotationUtils.targetRotation.getYaw() : access$getMc$p$s1046033730().thePlayer.rotationYaw;
            lIlIlIllIlIIlI = RotationUtils.targetRotation != null ? RotationUtils.targetRotation.getPitch() : access$getMc$p$s1046033730().thePlayer.rotationPitch;
            lIlIlIlllIlIII = lIlIlIllIllIIl.renderPosX - (double)(MathHelper.cos(lIlIllIIIIIIII / 180.0F * 3.1415927F) * 0.16F);
            lIlIlIllIlIIII = lIlIlIllIllIIl.renderPosY + (double)access$getMc$p$s1046033730().thePlayer.getEyeHeight() - 0.10000000149011612D;
            lIlIlIlllIlIlI = lIlIlIllIllIIl.renderPosZ - (double)(MathHelper.sin(lIlIllIIIIIIII / 180.0F * 3.1415927F) * 0.16F);
            lIlIlIlllIlIll = (double)(-MathHelper.sin(lIlIllIIIIIIII / 180.0F * 3.1415927F) * MathHelper.cos(lIlIlIllIlIIlI / 180.0F * 3.1415927F)) * (lIlIlIllIllIII ? 1.0D : 0.4D);
            if (lIlIlIllIlllll instanceof ItemPotion) {
               EntityPlayerSP var41 = access$getMc$p$s1046033730().thePlayer;
               Intrinsics.checkExpressionValueIsNotNull(var41, "mc.thePlayer");
               ItemStack var42 = var41.getHeldItem();
               Intrinsics.checkExpressionValueIsNotNull(var42, "mc.thePlayer.heldItem");
               if (ItemPotion.isSplash(var42.getItemDamage())) {
                  var44 = -20;
                  break label147;
               }
            }

            var44 = 0;
         }

         int lIlIlIllIIllIl = (double)(-MathHelper.sin((lIlIlIllIlIIlI + (float)var44) / 180.0F * 3.1415927F)) * (lIlIlIllIllIII ? 1.0D : 0.4D);
         double lIlIlIlllIllIl = (double)(MathHelper.cos(lIlIllIIIIIIII / 180.0F * 3.1415927F) * MathHelper.cos(lIlIlIllIlIIlI / 180.0F * 3.1415927F)) * (lIlIlIllIllIII ? 1.0D : 0.4D);
         boolean lIlIlIllIIlIll = MathHelper.sqrt_double(lIlIlIlllIlIll * lIlIlIlllIlIll + lIlIlIllIIllIl * lIlIlIllIIllIl + lIlIlIlllIllIl * lIlIlIlllIllIl);
         lIlIlIlllIlIll /= (double)lIlIlIllIIlIll;
         lIlIlIllIIllIl /= (double)lIlIlIllIIlIll;
         lIlIlIlllIllIl /= (double)lIlIlIllIIlIll;
         lIlIlIlllIlIll *= (double)lIlIlIlllIIIlI;
         lIlIlIllIIllIl *= (double)lIlIlIlllIIIlI;
         lIlIlIlllIllIl *= (double)lIlIlIlllIIIlI;
         byte lIlIlIllIIlIlI = (MovingObjectPosition)null;
         boolean lIlIlIllllIIII = false;
         boolean lIlIlIllllIIIl = false;
         GL11.glDepthMask(false);
         RenderUtils.enableGlCap(3042, 2848);
         RenderUtils.disableGlCap(2929, 3008, 3553);
         GL11.glBlendFunc(770, 771);
         GL11.glHint(3154, 4354);
         RenderUtils.glColor(new Color(0, 160, 255, 255));
         GL11.glLineWidth(2.0F);
         GL11.glBegin(3);

         while(!lIlIlIllllIIII && lIlIlIllIlIIII > 0.0D) {
            Vec3 lIlIlIllllIIll = new Vec3(lIlIlIlllIlIII, lIlIlIllIlIIII, lIlIlIlllIlIlI);
            short lIlIlIllIIIllI = new Vec3(lIlIlIlllIlIII + lIlIlIlllIlIll, lIlIlIllIlIIII + lIlIlIllIIllIl, lIlIlIlllIlIlI + lIlIlIlllIllIl);
            lIlIlIllIIlIlI = access$getMc$p$s1046033730().theWorld.rayTraceBlocks(lIlIlIllllIIll, lIlIlIllIIIllI, false, true, false);
            lIlIlIllllIIll = new Vec3(lIlIlIlllIlIII, lIlIlIllIlIIII, lIlIlIlllIlIlI);
            lIlIlIllIIIllI = new Vec3(lIlIlIlllIlIII + lIlIlIlllIlIll, lIlIlIllIlIIII + lIlIlIllIIllIl, lIlIlIlllIlIlI + lIlIlIlllIllIl);
            if (lIlIlIllIIlIlI != null) {
               lIlIlIllllIIII = true;
               lIlIlIllIIIllI = new Vec3(lIlIlIllIIlIlI.hitVec.xCoord, lIlIlIllIIlIlI.hitVec.yCoord, lIlIlIllIIlIlI.hitVec.zCoord);
            }

            AxisAlignedBB lIlIlIllllIlIl = (new AxisAlignedBB(lIlIlIlllIlIII - (double)lIlIlIllIlIlII, lIlIlIllIlIIII - (double)lIlIlIllIlIlII, lIlIlIlllIlIlI - (double)lIlIlIllIlIlII, lIlIlIlllIlIII + (double)lIlIlIllIlIlII, lIlIlIllIlIIII + (double)lIlIlIllIlIlII, lIlIlIlllIlIlI + (double)lIlIlIllIlIlII)).addCoord(lIlIlIlllIlIll, lIlIlIllIIllIl, lIlIlIlllIllIl).expand(1.0D, 1.0D, 1.0D);
            int lIlIlIllllIllI = MathHelper.floor_double((lIlIlIllllIlIl.minX - 2.0D) / 16.0D);
            boolean lIlIlIllIIIIll = MathHelper.floor_double((lIlIlIllllIlIl.maxX + 2.0D) / 16.0D);
            String lIlIlIllIIIIlI = MathHelper.floor_double((lIlIlIllllIlIl.minZ - 2.0D) / 16.0D);
            int lIlIlIlllllIIl = MathHelper.floor_double((lIlIlIllllIlIl.maxZ + 2.0D) / 16.0D);
            byte lIlIlIlIllllll = false;
            double lIlIlIllIIIIII = (List)(new ArrayList());
            byte lIlIlIlIllllll = lIlIlIllllIllI;
            String lIlIlIlIlllllI = lIlIlIllIIIIll;
            if (lIlIlIllllIllI <= lIlIlIllIIIIll) {
               while(true) {
                  String lIlIlIllllllll = lIlIlIllIIIIlI;
                  short lIlIlIlIllllII = lIlIlIlllllIIl;
                  if (lIlIlIllIIIIlI <= lIlIlIlllllIIl) {
                     while(true) {
                        access$getMc$p$s1046033730().theWorld.getChunkFromChunkCoords(lIlIlIlIllllll, lIlIlIllllllll).getEntitiesWithinAABBForEntity((Entity)access$getMc$p$s1046033730().thePlayer, lIlIlIllllIlIl, lIlIlIllIIIIII, (Predicate)null);
                        if (lIlIlIllllllll == lIlIlIlIllllII) {
                           break;
                        }

                        ++lIlIlIllllllll;
                     }
                  }

                  if (lIlIlIlIllllll == lIlIlIlIlllllI) {
                     break;
                  }

                  ++lIlIlIlIllllll;
               }
            }

            Iterator lIlIlIlIlllllI = lIlIlIllIIIIII.iterator();

            while(lIlIlIlIlllllI.hasNext()) {
               byte lIlIlIlIllllll = (Entity)lIlIlIlIlllllI.next();
               if (lIlIlIlIllllll.canBeCollidedWith() && lIlIlIlIllllll != access$getMc$p$s1046033730().thePlayer) {
                  String lIlIlIlIllllIl = lIlIlIlIllllll.getEntityBoundingBox().expand((double)lIlIlIllIlIlII, (double)lIlIlIllIlIlII, (double)lIlIlIllIlIlII);
                  MovingObjectPosition var45 = lIlIlIlIllllIl.calculateIntercept(lIlIlIllllIIll, lIlIlIllIIIllI);
                  if (var45 != null) {
                     short lIlIlIlIllllII = var45;
                     lIlIlIllllIIIl = true;
                     lIlIlIllllIIII = true;
                     lIlIlIllIIlIlI = lIlIlIlIllllII;
                  } else {
                     var10001 = false;
                  }
               }
            }

            lIlIlIlllIlIII += lIlIlIlllIlIll;
            lIlIlIllIlIIII += lIlIlIllIIllIl;
            lIlIlIlllIlIlI += lIlIlIlllIllIl;
            IBlockState var46 = access$getMc$p$s1046033730().theWorld.getBlockState(new BlockPos(lIlIlIlllIlIII, lIlIlIllIlIIII, lIlIlIlllIlIlI));
            Intrinsics.checkExpressionValueIsNotNull(var46, "mc.theWorld.getBlockStat…ockPos(posX, posY, posZ))");
            Block var47 = var46.getBlock();
            Intrinsics.checkExpressionValueIsNotNull(var47, "mc.theWorld.getBlockStat…(posX, posY, posZ)).block");
            if (var47.getMaterial() == Material.water) {
               lIlIlIlllIlIll *= 0.6D;
               lIlIlIllIIllIl *= 0.6D;
               lIlIlIlllIllIl *= 0.6D;
            } else {
               lIlIlIlllIlIll *= (double)lIlIlIllIlIllI;
               lIlIlIllIIllIl *= (double)lIlIlIllIlIllI;
               lIlIlIlllIllIl *= (double)lIlIlIllIlIllI;
            }

            lIlIlIllIIllIl -= (double)lIlIlIlllIIlII;
            GL11.glVertex3d(lIlIlIlllIlIII - lIlIlIllIllIIl.renderPosX, lIlIlIllIlIIII - lIlIlIllIllIIl.renderPosY, lIlIlIlllIlIlI - lIlIlIllIllIIl.renderPosZ);
         }

         GL11.glEnd();
         GL11.glPushMatrix();
         GL11.glTranslated(lIlIlIlllIlIII - lIlIlIllIllIIl.renderPosX, lIlIlIllIlIIII - lIlIlIllIllIIl.renderPosY, lIlIlIlllIlIlI - lIlIlIllIllIIl.renderPosZ);
         if (lIlIlIllIIlIlI != null) {
            EnumFacing var48 = lIlIlIllIIlIlI.sideHit;
            Intrinsics.checkExpressionValueIsNotNull(var48, "landingPosition.sideHit");
            switch(var48.getAxis().ordinal()) {
            case 0:
               GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
            case 1:
            default:
               break;
            case 2:
               GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
            }

            if (lIlIlIllllIIIl) {
               RenderUtils.glColor(new Color(255, 0, 0, 150));
            }
         }

         GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
         Cylinder lIlIlIllllIIlI = new Cylinder();
         lIlIlIllllIIlI.setDrawStyle(100011);
         lIlIlIllllIIlI.draw(0.2F, 0.0F, 0.0F, 60, 1);
         GL11.glPopMatrix();
         GL11.glDepthMask(true);
         RenderUtils.resetCaps();
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      }
   }
}
