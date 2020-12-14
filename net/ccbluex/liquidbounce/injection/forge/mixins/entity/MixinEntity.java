//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.injection.forge.mixins.entity;

import java.util.Random;
import java.util.UUID;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.StrafeEvent;
import net.ccbluex.liquidbounce.features.module.modules.combat.HitBox;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SideOnly(Side.CLIENT)
@Mixin({Entity.class})
public abstract class MixinEntity {
   // $FF: synthetic field
   @Shadow
   public float distanceWalkedModified;
   // $FF: synthetic field
   @Shadow
   public boolean isInWeb;
   // $FF: synthetic field
   @Shadow
   public float prevRotationPitch;
   // $FF: synthetic field
   @Shadow
   public float prevRotationYaw;
   // $FF: synthetic field
   @Shadow
   public boolean onGround;
   // $FF: synthetic field
   @Shadow
   public double posY;
   // $FF: synthetic field
   @Shadow
   public double motionX;
   // $FF: synthetic field
   @Shadow
   protected boolean inPortal;
   // $FF: synthetic field
   @Shadow
   public double posX;
   // $FF: synthetic field
   @Shadow
   private int fire;
   // $FF: synthetic field
   @Shadow
   public float rotationYaw;
   // $FF: synthetic field
   @Shadow
   public int timeUntilPortal;
   // $FF: synthetic field
   @Shadow
   public World worldObj;
   // $FF: synthetic field
   @Shadow
   public boolean isCollided;
   // $FF: synthetic field
   @Shadow
   public boolean noClip;
   // $FF: synthetic field
   @Shadow
   public boolean isAirBorne;
   // $FF: synthetic field
   @Shadow
   public double posZ;
   // $FF: synthetic field
   @Shadow
   public int fireResistance;
   // $FF: synthetic field
   @Shadow
   private int nextStepDistance;
   // $FF: synthetic field
   @Shadow
   public boolean isCollidedHorizontally;
   // $FF: synthetic field
   @Shadow
   public float distanceWalkedOnStepModified;
   // $FF: synthetic field
   @Shadow
   protected Random rand;
   // $FF: synthetic field
   @Shadow
   public boolean isCollidedVertically;
   // $FF: synthetic field
   @Shadow
   public float width;
   // $FF: synthetic field
   @Shadow
   public double motionY;
   // $FF: synthetic field
   @Shadow
   public float stepHeight;
   // $FF: synthetic field
   @Shadow
   public Entity ridingEntity;
   // $FF: synthetic field
   @Shadow
   public float rotationPitch;
   // $FF: synthetic field
   @Shadow
   public double motionZ;

   @Shadow
   public abstract void setFire(int var1);

   @Shadow
   public void moveEntity(double llllllllllllllllllIllllIIIlllIIl, double llllllllllllllllllIllllIIIlllIII, double llllllllllllllllllIllllIIIllIlll) {
   }

   @Shadow
   public abstract boolean isInWater();

   @Shadow
   protected abstract void doBlockCollisions();

   @Shadow
   protected abstract Vec3 getVectorForRotation(float var1, float var2);

   @Shadow
   public abstract AxisAlignedBB getEntityBoundingBox();

   @Shadow
   public abstract UUID getUniqueID();

   public int getFire() {
      return llllllllllllllllllIllllIIIlIlIll.fire;
   }

   @Inject(
      method = {"getCollisionBorderSize"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void getCollisionBorderSize(CallbackInfoReturnable<Float> llllllllllllllllllIllllIIIlIIlll) {
      HitBox llllllllllllllllllIllllIIIlIIllI = (HitBox)LiquidBounce.moduleManager.getModule(HitBox.class);
      if (llllllllllllllllllIllllIIIlIIllI.getState()) {
         llllllllllllllllllIllllIIIlIIlll.setReturnValue(0.1F + (Float)llllllllllllllllllIllllIIIlIIllI.getSizeValue().get());
      }

   }

   @Shadow
   public abstract boolean isRiding();

   @Shadow
   public abstract void setEntityBoundingBox(AxisAlignedBB var1);

   public int getNextStepDistance() {
      return llllllllllllllllllIllllIIIllIlII.nextStepDistance;
   }

   @Shadow
   public abstract void addEntityCrashInfo(CrashReportCategory var1);

   @Shadow
   protected abstract void playStepSound(BlockPos var1, Block var2);

   @Inject(
      method = {"moveFlying"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void handleRotations(float llllllllllllllllllIllllIIIIlIIlI, float llllllllllllllllllIllllIIIIlIIIl, float llllllllllllllllllIllllIIIIlIllI, CallbackInfo llllllllllllllllllIllllIIIIlIlIl) {
      if ((Entity)llllllllllllllllllIllllIIIIlIIll == Minecraft.getMinecraft().thePlayer) {
         StrafeEvent llllllllllllllllllIllllIIIIlIlII = new StrafeEvent(llllllllllllllllllIllllIIIIlIIlI, llllllllllllllllllIllllIIIIlIIIl, llllllllllllllllllIllllIIIIlIllI);
         LiquidBounce.eventManager.callEvent(llllllllllllllllllIllllIIIIlIlII);
         if (llllllllllllllllllIllllIIIIlIlII.isCancelled()) {
            llllllllllllllllllIllllIIIIlIlIl.cancel();
         }

      }
   }

   @Shadow
   public abstract boolean isInsideOfMaterial(Material var1);

   @Inject(
      method = {"setAngles"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void setAngles(float llllllllllllllllllIllllIIIlIIIlI, float llllllllllllllllllIllllIIIlIIIIl, CallbackInfo llllllllllllllllllIllllIIIlIIIII) {
   }

   @Shadow
   public abstract boolean isWet();

   @Shadow
   protected abstract void dealFireDamage(int var1);

   @Shadow
   public abstract boolean isSprinting();

   public void setNextStepDistance(int llllllllllllllllllIllllIIIlIlllI) {
      llllllllllllllllllIllllIIIlIllll.nextStepDistance = llllllllllllllllllIllllIIIlIlllI;
   }

   @Shadow
   public abstract boolean isSneaking();
}
