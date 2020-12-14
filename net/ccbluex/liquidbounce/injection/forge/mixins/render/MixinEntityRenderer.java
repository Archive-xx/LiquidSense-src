//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import com.google.common.base.Predicates;
import java.util.List;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.modules.player.Reach;
import net.ccbluex.liquidbounce.features.module.modules.render.CameraClip;
import net.ccbluex.liquidbounce.features.module.modules.render.NoHurtCam;
import net.ccbluex.liquidbounce.features.module.modules.render.Tracers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.EntityViewRenderEvent.CameraSetup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SideOnly(Side.CLIENT)
@Mixin({EntityRenderer.class})
public abstract class MixinEntityRenderer {
   // $FF: synthetic field
   @Shadow
   private float thirdPersonDistance;
   // $FF: synthetic field
   @Shadow
   private Minecraft mc;
   // $FF: synthetic field
   @Shadow
   private boolean cloudFog;
   // $FF: synthetic field
   @Shadow
   private float thirdPersonDistanceTemp;
   // $FF: synthetic field
   @Shadow
   private Entity pointedEntity;

   @Overwrite
   public void getMouseOver(float lllIlllllIllIl) {
      long lllIlllllIllII = lllIllllllIIIl.mc.getRenderViewEntity();
      if (lllIlllllIllII != null && lllIllllllIIIl.mc.theWorld != null) {
         lllIllllllIIIl.mc.mcProfiler.startSection("pick");
         lllIllllllIIIl.mc.pointedEntity = null;
         Reach lllIllllllllII = (Reach)LiquidBounce.moduleManager.getModule(Reach.class);
         long lllIlllllIlIlI = lllIllllllllII.getState() ? (double)lllIllllllllII.getMaxRange() : (double)lllIllllllIIIl.mc.playerController.getBlockReachDistance();
         lllIllllllIIIl.mc.objectMouseOver = lllIlllllIllII.rayTrace(lllIllllllllII.getState() ? (double)(Float)lllIllllllllII.getBuildReachValue().get() : lllIlllllIlIlI, lllIlllllIllIl);
         double lllIlllllllIlI = lllIlllllIlIlI;
         Vec3 lllIlllllllIIl = lllIlllllIllII.getPositionEyes(lllIlllllIllIl);
         char lllIlllllIIlll = false;
         if (lllIllllllIIIl.mc.playerController.extendedReach()) {
            lllIlllllIlIlI = 6.0D;
            lllIlllllllIlI = 6.0D;
         } else if (lllIlllllIlIlI > 3.0D) {
            lllIlllllIIlll = true;
         }

         if (lllIllllllIIIl.mc.objectMouseOver != null) {
            lllIlllllllIlI = lllIllllllIIIl.mc.objectMouseOver.hitVec.distanceTo(lllIlllllllIIl);
         }

         if (lllIllllllllII.getState()) {
            lllIlllllllIlI = (double)(Float)lllIllllllllII.getCombatReachValue().get();
            short lllIlllllIIllI = lllIlllllIllII.rayTrace(lllIlllllllIlI, lllIlllllIllIl);
            if (lllIlllllIIllI != null) {
               lllIlllllllIlI = lllIlllllIIllI.hitVec.distanceTo(lllIlllllllIIl);
            }
         }

         short lllIlllllIIllI = lllIlllllIllII.getLook(lllIlllllIllIl);
         short lllIlllllIIlIl = lllIlllllllIIl.addVector(lllIlllllIIllI.xCoord * lllIlllllIlIlI, lllIlllllIIllI.yCoord * lllIlllllIlIlI, lllIlllllIIllI.zCoord * lllIlllllIlIlI);
         lllIllllllIIIl.pointedEntity = null;
         short lllIlllllIIlII = null;
         short lllIlllllIIIll = 1.0F;
         List<Entity> lllIlllllIIIlI = lllIllllllIIIl.mc.theWorld.getEntitiesInAABBexcluding(lllIlllllIllII, lllIlllllIllII.getEntityBoundingBox().addCoord(lllIlllllIIllI.xCoord * lllIlllllIlIlI, lllIlllllIIllI.yCoord * lllIlllllIlIlI, lllIlllllIIllI.zCoord * lllIlllllIlIlI).expand((double)lllIlllllIIIll, (double)lllIlllllIIIll, (double)lllIlllllIIIll), Predicates.and(EntitySelectors.NOT_SPECTATING, (lllIllllIllIII) -> {
            return lllIllllIllIII.canBeCollidedWith();
         }));
         int lllIlllllIIIIl = lllIlllllllIlI;

         for(int lllIlllllIIIII = 0; lllIlllllIIIII < lllIlllllIIIlI.size(); ++lllIlllllIIIII) {
            Entity llllIIIIIIIIIl = (Entity)lllIlllllIIIlI.get(lllIlllllIIIII);
            short lllIllllIllllI = llllIIIIIIIIIl.getCollisionBorderSize();
            AxisAlignedBB lllIllllllllll = llllIIIIIIIIIl.getEntityBoundingBox().expand((double)lllIllllIllllI, (double)lllIllllIllllI, (double)lllIllllIllllI);
            MovingObjectPosition lllIlllllllllI = lllIllllllllll.calculateIntercept(lllIlllllllIIl, lllIlllllIIlIl);
            if (lllIllllllllll.isVecInside(lllIlllllllIIl)) {
               if (lllIlllllIIIIl >= 0.0D) {
                  lllIllllllIIIl.pointedEntity = llllIIIIIIIIIl;
                  lllIlllllIIlII = lllIlllllllllI == null ? lllIlllllllIIl : lllIlllllllllI.hitVec;
                  lllIlllllIIIIl = 0.0D;
               }
            } else if (lllIlllllllllI != null) {
               float lllIllllIllIll = lllIlllllllIIl.distanceTo(lllIlllllllllI.hitVec);
               if (lllIllllIllIll < lllIlllllIIIIl || lllIlllllIIIIl == 0.0D) {
                  if (llllIIIIIIIIIl == lllIlllllIllII.ridingEntity && !lllIlllllIllII.canRiderInteract()) {
                     if (lllIlllllIIIIl == 0.0D) {
                        lllIllllllIIIl.pointedEntity = llllIIIIIIIIIl;
                        lllIlllllIIlII = lllIlllllllllI.hitVec;
                     }
                  } else {
                     lllIllllllIIIl.pointedEntity = llllIIIIIIIIIl;
                     lllIlllllIIlII = lllIlllllllllI.hitVec;
                     lllIlllllIIIIl = lllIllllIllIll;
                  }
               }
            }
         }

         if (lllIllllllIIIl.pointedEntity != null && lllIlllllIIlll && lllIlllllllIIl.distanceTo(lllIlllllIIlII) > (lllIllllllllII.getState() ? (double)(Float)lllIllllllllII.getCombatReachValue().get() : 3.0D)) {
            lllIllllllIIIl.pointedEntity = null;
            lllIllllllIIIl.mc.objectMouseOver = new MovingObjectPosition(MovingObjectType.MISS, lllIlllllIIlII, (EnumFacing)null, new BlockPos(lllIlllllIIlII));
         }

         if (lllIllllllIIIl.pointedEntity != null && (lllIlllllIIIIl < lllIlllllllIlI || lllIllllllIIIl.mc.objectMouseOver == null)) {
            lllIllllllIIIl.mc.objectMouseOver = new MovingObjectPosition(lllIllllllIIIl.pointedEntity, lllIlllllIIlII);
            if (lllIllllllIIIl.pointedEntity instanceof EntityLivingBase || lllIllllllIIIl.pointedEntity instanceof EntityItemFrame) {
               lllIllllllIIIl.mc.pointedEntity = lllIllllllIIIl.pointedEntity;
            }
         }

         lllIllllllIIIl.mc.mcProfiler.endSection();
      }

   }

   @Inject(
      method = {"renderWorldPass"},
      at = {@At(
   value = "FIELD",
   target = "Lnet/minecraft/client/renderer/EntityRenderer;renderHand:Z",
   shift = At.Shift.BEFORE
)}
   )
   private void renderWorldPass(int llllIIIlIIlIll, float llllIIIlIIIlll, long llllIIIlIIlIIl, CallbackInfo llllIIIlIIlIII) {
      LiquidBounce.eventManager.callEvent(new Render3DEvent(llllIIIlIIIlll));
   }

   @Inject(
      method = {"setupCameraTransform"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/renderer/EntityRenderer;setupViewBobbing(F)V",
   shift = At.Shift.BEFORE
)}
   )
   private void setupCameraViewBobbingBefore(CallbackInfo llllIIIIIllIlI) {
      if (LiquidBounce.moduleManager.getModule(Tracers.class).getState()) {
         GL11.glPushMatrix();
      }

   }

   @Shadow
   public abstract void setupCameraTransform(float var1, int var2);

   @Inject(
      method = {"hurtCameraEffect"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void injectHurtCameraEffect(CallbackInfo llllIIIlIIIIll) {
      if (LiquidBounce.moduleManager.getModule(NoHurtCam.class).getState()) {
         llllIIIlIIIIll.cancel();
      }

   }

   @Inject(
      method = {"setupCameraTransform"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/renderer/EntityRenderer;setupViewBobbing(F)V",
   shift = At.Shift.AFTER
)}
   )
   private void setupCameraViewBobbingAfter(CallbackInfo llllIIIIIllIII) {
      if (LiquidBounce.moduleManager.getModule(Tracers.class).getState()) {
         GL11.glPopMatrix();
      }

   }

   @Inject(
      method = {"orientCamera"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/util/Vec3;distanceTo(Lnet/minecraft/util/Vec3;)D"
)},
      cancellable = true
   )
   private void cameraClip(float llllIIIIlIIlII, CallbackInfo llllIIIIlIIIll) {
      if (LiquidBounce.moduleManager.getModule(CameraClip.class).getState()) {
         llllIIIIlIIIll.cancel();
         Entity llllIIIIlIllIl = llllIIIIlIIlIl.mc.getRenderViewEntity();
         double llllIIIIlIIIIl = llllIIIIlIllIl.getEyeHeight();
         double llllIIIIlIIIII;
         float llllIIIIIllllI;
         if (llllIIIIlIllIl instanceof EntityLivingBase && ((EntityLivingBase)llllIIIIlIllIl).isPlayerSleeping()) {
            llllIIIIlIIIIl = (float)((double)llllIIIIlIIIIl + 1.0D);
            GlStateManager.translate(0.0F, 0.3F, 0.0F);
            if (!llllIIIIlIIlIl.mc.gameSettings.debugCamEnable) {
               byte llllIIIIlIIIII = new BlockPos(llllIIIIlIllIl);
               long llllIIIIIlllll = llllIIIIlIIlIl.mc.theWorld.getBlockState(llllIIIIlIIIII);
               ForgeHooksClient.orientBedCamera(llllIIIIlIIlIl.mc.theWorld, llllIIIIlIIIII, llllIIIIIlllll, llllIIIIlIllIl);
               GlStateManager.rotate(llllIIIIlIllIl.prevRotationYaw + (llllIIIIlIllIl.rotationYaw - llllIIIIlIllIl.prevRotationYaw) * llllIIIIlIIlII + 180.0F, 0.0F, -1.0F, 0.0F);
               GlStateManager.rotate(llllIIIIlIllIl.prevRotationPitch + (llllIIIIlIllIl.rotationPitch - llllIIIIlIllIl.prevRotationPitch) * llllIIIIlIIlII, -1.0F, 0.0F, 0.0F);
            }
         } else if (llllIIIIlIIlIl.mc.gameSettings.thirdPersonView > 0) {
            llllIIIIlIIIII = (double)(llllIIIIlIIlIl.thirdPersonDistanceTemp + (llllIIIIlIIlIl.thirdPersonDistance - llllIIIIlIIlIl.thirdPersonDistanceTemp) * llllIIIIlIIlII);
            if (llllIIIIlIIlIl.mc.gameSettings.debugCamEnable) {
               GlStateManager.translate(0.0F, 0.0F, (float)(-llllIIIIlIIIII));
            } else {
               llllIIIIIllllI = llllIIIIlIllIl.rotationYaw;
               float llllIIIIllIlIl = llllIIIIlIllIl.rotationPitch;
               if (llllIIIIlIIlIl.mc.gameSettings.thirdPersonView == 2) {
                  llllIIIIllIlIl += 180.0F;
               }

               if (llllIIIIlIIlIl.mc.gameSettings.thirdPersonView == 2) {
                  GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
               }

               GlStateManager.rotate(llllIIIIlIllIl.rotationPitch - llllIIIIllIlIl, 1.0F, 0.0F, 0.0F);
               GlStateManager.rotate(llllIIIIlIllIl.rotationYaw - llllIIIIIllllI, 0.0F, 1.0F, 0.0F);
               GlStateManager.translate(0.0F, 0.0F, (float)(-llllIIIIlIIIII));
               GlStateManager.rotate(llllIIIIIllllI - llllIIIIlIllIl.rotationYaw, 0.0F, 1.0F, 0.0F);
               GlStateManager.rotate(llllIIIIllIlIl - llllIIIIlIllIl.rotationPitch, 1.0F, 0.0F, 0.0F);
            }
         } else {
            GlStateManager.translate(0.0F, 0.0F, -0.1F);
         }

         if (!llllIIIIlIIlIl.mc.gameSettings.debugCamEnable) {
            byte llllIIIIlIIIII = llllIIIIlIllIl.prevRotationYaw + (llllIIIIlIllIl.rotationYaw - llllIIIIlIllIl.prevRotationYaw) * llllIIIIlIIlII + 180.0F;
            long llllIIIIIlllll = llllIIIIlIllIl.prevRotationPitch + (llllIIIIlIllIl.rotationPitch - llllIIIIlIllIl.prevRotationPitch) * llllIIIIlIIlII;
            llllIIIIIllllI = 0.0F;
            if (llllIIIIlIllIl instanceof EntityAnimal) {
               EntityAnimal llllIIIIllIIll = (EntityAnimal)llllIIIIlIllIl;
               llllIIIIlIIIII = llllIIIIllIIll.prevRotationYawHead + (llllIIIIllIIll.rotationYawHead - llllIIIIllIIll.prevRotationYawHead) * llllIIIIlIIlII + 180.0F;
            }

            double llllIIIIIlllIl = ActiveRenderInfo.getBlockAtEntityViewpoint(llllIIIIlIIlIl.mc.theWorld, llllIIIIlIllIl, llllIIIIlIIlII);
            Exception llllIIIIIlllII = new CameraSetup((EntityRenderer)llllIIIIlIIlIl, llllIIIIlIllIl, llllIIIIIlllIl, (double)llllIIIIlIIlII, llllIIIIlIIIII, llllIIIIIlllll, llllIIIIIllllI);
            MinecraftForge.EVENT_BUS.post(llllIIIIIlllII);
            boolean var10001 = false;
            GlStateManager.rotate(llllIIIIIlllII.roll, 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(llllIIIIIlllII.pitch, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(llllIIIIIlllII.yaw, 0.0F, 1.0F, 0.0F);
         }

         GlStateManager.translate(0.0F, -llllIIIIlIIIIl, 0.0F);
         llllIIIIlIIIII = llllIIIIlIllIl.prevPosX + (llllIIIIlIllIl.posX - llllIIIIlIllIl.prevPosX) * (double)llllIIIIlIIlII;
         String llllIIIIIllllI = llllIIIIlIllIl.prevPosY + (llllIIIIlIllIl.posY - llllIIIIlIllIl.prevPosY) * (double)llllIIIIlIIlII + (double)llllIIIIlIIIIl;
         Exception llllIIIIIlllII = llllIIIIlIllIl.prevPosZ + (llllIIIIlIllIl.posZ - llllIIIIlIllIl.prevPosZ) * (double)llllIIIIlIIlII;
         llllIIIIlIIlIl.cloudFog = llllIIIIlIIlIl.mc.renderGlobal.hasCloudFog(llllIIIIlIIIII, llllIIIIIllllI, llllIIIIIlllII, llllIIIIlIIlII);
      }

   }

   @Shadow
   public abstract void loadShader(ResourceLocation var1);
}
