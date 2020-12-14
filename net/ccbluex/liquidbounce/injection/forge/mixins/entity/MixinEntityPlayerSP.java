//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.injection.forge.mixins.entity;

import java.util.Iterator;
import java.util.List;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PushOutEvent;
import net.ccbluex.liquidbounce.event.SlowDownEvent;
import net.ccbluex.liquidbounce.event.SommtheEvent;
import net.ccbluex.liquidbounce.event.StepConfirmEvent;
import net.ccbluex.liquidbounce.event.StepEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.combat.Aura;
import net.ccbluex.liquidbounce.features.module.modules.fun.Derp;
import net.ccbluex.liquidbounce.features.module.modules.movement.InventoryMove;
import net.ccbluex.liquidbounce.features.module.modules.movement.NoSlow;
import net.ccbluex.liquidbounce.features.module.modules.movement.Sprint;
import net.ccbluex.liquidbounce.features.module.modules.render.NoSwing;
import net.ccbluex.liquidbounce.features.module.modules.render.Rotations;
import net.ccbluex.liquidbounce.features.module.modules.world.Scaffold;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.network.play.client.C03PacketPlayer.C05PacketPlayerLook;
import net.minecraft.network.play.client.C03PacketPlayer.C06PacketPlayerPosLook;
import net.minecraft.network.play.client.C0BPacketEntityAction.Action;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInput;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SideOnly(Side.CLIENT)
@Mixin({EntityPlayerSP.class})
public abstract class MixinEntityPlayerSP extends MixinAbstractClientPlayer {
   // $FF: synthetic field
   @Shadow
   public int sprintingTicksLeft;
   // $FF: synthetic field
   @Shadow
   public boolean serverSprintState;
   // $FF: synthetic field
   @Shadow
   public MovementInput movementInput;
   // $FF: synthetic field
   @Shadow
   protected Minecraft mc;
   // $FF: synthetic field
   @Shadow
   private double lastReportedPosX;
   // $FF: synthetic field
   @Shadow
   private double lastReportedPosY;
   // $FF: synthetic field
   @Shadow
   private int positionUpdateTicks;
   // $FF: synthetic field
   @Shadow
   public float horseJumpPower;
   // $FF: synthetic field
   @Shadow
   private float lastReportedPitch;
   // $FF: synthetic field
   @Shadow
   protected int sprintToggleTimer;
   // $FF: synthetic field
   @Shadow
   public float prevTimeInPortal;
   // $FF: synthetic field
   @Shadow
   private float lastReportedYaw;
   // $FF: synthetic field
   @Shadow
   public int horseJumpPowerCounter;
   // $FF: synthetic field
   @Shadow
   private double lastReportedPosZ;
   // $FF: synthetic field
   @Shadow
   public float timeInPortal;
   // $FF: synthetic field
   @Shadow
   @Final
   public NetHandlerPlayClient sendQueue;
   // $FF: synthetic field
   @Shadow
   private boolean serverSneakState;

   @Shadow
   protected abstract boolean pushOutOfBlocks(double var1, double var3, double var5);

   @Shadow
   protected abstract boolean isCurrentViewEntity();

   @Shadow
   public abstract void sendPlayerAbilities();

   @Shadow
   protected abstract void sendHorseJump();

   @Overwrite
   public void onUpdateWalkingPlayer() {
      Rotations llllllllllllllllllIIlllIIIlIIIll = (Rotations)LiquidBounce.moduleManager.getModule(Rotations.class);

      try {
         LiquidBounce.eventManager.callEvent(new MotionEvent(EventState.PRE));
         String llllllllllllllllllIIlllIIIIllllI = (InventoryMove)LiquidBounce.moduleManager.getModule(InventoryMove.class);
         boolean llllllllllllllllllIIlllIIIlIIlll = llllllllllllllllllIIlllIIIIllllI.getState() && (Boolean)llllllllllllllllllIIlllIIIIllllI.getAacAdditionProValue().get();
         int llllllllllllllllllIIlllIIIIlllII = llllllllllllllllllIIlllIIIlIIlII.isSprinting() && !llllllllllllllllllIIlllIIIlIIlll;
         if (llllllllllllllllllIIlllIIIIlllII != llllllllllllllllllIIlllIIIlIIlII.serverSprintState) {
            if (llllllllllllllllllIIlllIIIIlllII) {
               llllllllllllllllllIIlllIIIlIIlII.sendQueue.addToSendQueue(new C0BPacketEntityAction((EntityPlayerSP)llllllllllllllllllIIlllIIIlIIlII, Action.START_SPRINTING));
            } else {
               llllllllllllllllllIIlllIIIlIIlII.sendQueue.addToSendQueue(new C0BPacketEntityAction((EntityPlayerSP)llllllllllllllllllIIlllIIIlIIlII, Action.STOP_SPRINTING));
            }

            llllllllllllllllllIIlllIIIlIIlII.serverSprintState = llllllllllllllllllIIlllIIIIlllII;
         }

         if (llllllllllllllllllIIlllIIIlIIlII.isCurrentViewEntity()) {
            byte llllllllllllllllllIIlllIIIIllIlI = llllllllllllllllllIIlllIIIlIIlII.rotationYaw;
            Exception llllllllllllllllllIIlllIIIIllIII = llllllllllllllllllIIlllIIIlIIlII.rotationPitch;
            float llllllllllllllllllIIlllIIIllIlII = RotationUtils.serverRotation.getYaw();
            float llllllllllllllllllIIlllIIIllIIlI = RotationUtils.serverRotation.getPitch();
            Derp llllllllllllllllllIIlllIIIllIIIl = (Derp)LiquidBounce.moduleManager.getModule(Derp.class);
            if (llllllllllllllllllIIlllIIIllIIIl.getState()) {
               float[] llllllllllllllllllIIlllIIIllllII = llllllllllllllllllIIlllIIIllIIIl.getRotation();
               llllllllllllllllllIIlllIIIIllIlI = llllllllllllllllllIIlllIIIllllII[0];
               llllllllllllllllllIIlllIIIIllIII = llllllllllllllllllIIlllIIIllllII[1];
            }

            if (RotationUtils.targetRotation != null) {
               llllllllllllllllllIIlllIIIIllIlI = RotationUtils.targetRotation.getYaw();
               llllllllllllllllllIIlllIIIIllIII = RotationUtils.targetRotation.getPitch();
            }

            char llllllllllllllllllIIlllIIIIlIIlI = llllllllllllllllllIIlllIIIlIIlII.posX - llllllllllllllllllIIlllIIIlIIlII.lastReportedPosX;
            float llllllllllllllllllIIlllIIIIlIIII = llllllllllllllllllIIlllIIIlIIlII.getEntityBoundingBox().minY - llllllllllllllllllIIlllIIIlIIlII.lastReportedPosY;
            Exception llllllllllllllllllIIlllIIIIIlllI = llllllllllllllllllIIlllIIIlIIlII.posZ - llllllllllllllllllIIlllIIIlIIlII.lastReportedPosZ;
            double llllllllllllllllllIIlllIIIlIllII = (double)(llllllllllllllllllIIlllIIIIllIlI - llllllllllllllllllIIlllIIIllIlII);
            double llllllllllllllllllIIlllIIIlIlIll = (double)(llllllllllllllllllIIlllIIIIllIII - llllllllllllllllllIIlllIIIllIIlI);
            boolean llllllllllllllllllIIlllIIIlIlIlI = llllllllllllllllllIIlllIIIIlIIlI * llllllllllllllllllIIlllIIIIlIIlI + llllllllllllllllllIIlllIIIIlIIII * llllllllllllllllllIIlllIIIIlIIII + llllllllllllllllllIIlllIIIIIlllI * llllllllllllllllllIIlllIIIIIlllI > 9.0E-4D || llllllllllllllllllIIlllIIIlIIlII.positionUpdateTicks >= 20;
            int llllllllllllllllllIIlllIIIIIIlll = llllllllllllllllllIIlllIIIlIllII != 0.0D || llllllllllllllllllIIlllIIIlIlIll != 0.0D;
            if (llllllllllllllllllIIlllIIIlIIlII.ridingEntity == null) {
               if (llllllllllllllllllIIlllIIIlIlIlI && llllllllllllllllllIIlllIIIIIIlll) {
                  llllllllllllllllllIIlllIIIlIIlII.sendQueue.addToSendQueue(new C06PacketPlayerPosLook(llllllllllllllllllIIlllIIIlIIlII.posX, llllllllllllllllllIIlllIIIlIIlII.getEntityBoundingBox().minY, llllllllllllllllllIIlllIIIlIIlII.posZ, llllllllllllllllllIIlllIIIIllIlI, llllllllllllllllllIIlllIIIIllIII, llllllllllllllllllIIlllIIIlIIlII.onGround));
               } else if (llllllllllllllllllIIlllIIIlIlIlI) {
                  llllllllllllllllllIIlllIIIlIIlII.sendQueue.addToSendQueue(new C04PacketPlayerPosition(llllllllllllllllllIIlllIIIlIIlII.posX, llllllllllllllllllIIlllIIIlIIlII.getEntityBoundingBox().minY, llllllllllllllllllIIlllIIIlIIlII.posZ, llllllllllllllllllIIlllIIIlIIlII.onGround));
               } else if (llllllllllllllllllIIlllIIIIIIlll) {
                  llllllllllllllllllIIlllIIIlIIlII.sendQueue.addToSendQueue(new C05PacketPlayerLook(llllllllllllllllllIIlllIIIIllIlI, llllllllllllllllllIIlllIIIIllIII, llllllllllllllllllIIlllIIIlIIlII.onGround));
               } else {
                  llllllllllllllllllIIlllIIIlIIlII.sendQueue.addToSendQueue(new C03PacketPlayer(llllllllllllllllllIIlllIIIlIIlII.onGround));
               }
            } else {
               llllllllllllllllllIIlllIIIlIIlII.sendQueue.addToSendQueue(new C06PacketPlayerPosLook(llllllllllllllllllIIlllIIIlIIlII.motionX, -999.0D, llllllllllllllllllIIlllIIIlIIlII.motionZ, llllllllllllllllllIIlllIIIIllIlI, llllllllllllllllllIIlllIIIIllIII, llllllllllllllllllIIlllIIIlIIlII.onGround));
               llllllllllllllllllIIlllIIIlIlIlI = false;
            }

            ++llllllllllllllllllIIlllIIIlIIlII.positionUpdateTicks;
            if (llllllllllllllllllIIlllIIIlIlIlI) {
               llllllllllllllllllIIlllIIIlIIlII.lastReportedPosX = llllllllllllllllllIIlllIIIlIIlII.posX;
               llllllllllllllllllIIlllIIIlIIlII.lastReportedPosY = llllllllllllllllllIIlllIIIlIIlII.getEntityBoundingBox().minY;
               llllllllllllllllllIIlllIIIlIIlII.lastReportedPosZ = llllllllllllllllllIIlllIIIlIIlII.posZ;
               llllllllllllllllllIIlllIIIlIIlII.positionUpdateTicks = 0;
            }

            if (llllllllllllllllllIIlllIIIIIIlll) {
               llllllllllllllllllIIlllIIIlIIlII.lastReportedYaw = llllllllllllllllllIIlllIIIlIIlII.rotationYaw;
               llllllllllllllllllIIlllIIIlIIlII.lastReportedPitch = llllllllllllllllllIIlllIIIlIIlII.rotationPitch;
            }

            if (llllllllllllllllllIIlllIIIlIIIll.getState() && llllllllllllllllllIIlllIIIlIIIll.getModeValue().equals("Other")) {
               String llllllllllllllllllIIlllIIIIIIlIl = new SommtheEvent(llllllllllllllllllIIlllIIIlIIlII.posX, llllllllllllllllllIIlllIIIlIIlII.posY, llllllllllllllllllIIlllIIIlIIlII.posZ, llllllllllllllllllIIlllIIIIllIlI, llllllllllllllllllIIlllIIIIllIII, llllllllllllllllllIIlllIIIlIIlII.mc.thePlayer.isSneaking(), llllllllllllllllllIIlllIIIlIIlII.onGround);
               LiquidBounce.eventManager.callEvent(llllllllllllllllllIIlllIIIIIIlIl);
               llllllllllllllllllIIlllIIIIIIlIl.fire();
            }
         }

         LiquidBounce.eventManager.callEvent(new MotionEvent(EventState.POST));
      } catch (Exception var23) {
         var23.printStackTrace();
      }

   }

   @Shadow
   public abstract boolean isSneaking();

   public void moveEntity(double llllllllllllllllllIIllIlIlIlIIII, double llllllllllllllllllIIllIlIlIIllll, double llllllllllllllllllIIllIlIlIIlllI) {
      Exception llllllllllllllllllIIllIlIlIIllIl = new MoveEvent(llllllllllllllllllIIllIlIlIlIIII, llllllllllllllllllIIllIlIlIIllll, llllllllllllllllllIIllIlIlIIlllI);
      LiquidBounce.eventManager.callEvent(llllllllllllllllllIIllIlIlIIllIl);
      if (!llllllllllllllllllIIllIlIlIIllIl.isCancelled()) {
         llllllllllllllllllIIllIlIlIlIIII = llllllllllllllllllIIllIlIlIIllIl.getX();
         llllllllllllllllllIIllIlIlIIllll = llllllllllllllllllIIllIlIlIIllIl.getY();
         llllllllllllllllllIIllIlIlIIlllI = llllllllllllllllllIIllIlIlIIllIl.getZ();
         if (llllllllllllllllllIIllIlIlIlIIIl.noClip) {
            llllllllllllllllllIIllIlIlIlIIIl.setEntityBoundingBox(llllllllllllllllllIIllIlIlIlIIIl.getEntityBoundingBox().offset(llllllllllllllllllIIllIlIlIlIIII, llllllllllllllllllIIllIlIlIIllll, llllllllllllllllllIIllIlIlIIlllI));
            llllllllllllllllllIIllIlIlIlIIIl.posX = (llllllllllllllllllIIllIlIlIlIIIl.getEntityBoundingBox().minX + llllllllllllllllllIIllIlIlIlIIIl.getEntityBoundingBox().maxX) / 2.0D;
            llllllllllllllllllIIllIlIlIlIIIl.posY = llllllllllllllllllIIllIlIlIlIIIl.getEntityBoundingBox().minY;
            llllllllllllllllllIIllIlIlIlIIIl.posZ = (llllllllllllllllllIIllIlIlIlIIIl.getEntityBoundingBox().minZ + llllllllllllllllllIIllIlIlIlIIIl.getEntityBoundingBox().maxZ) / 2.0D;
         } else {
            llllllllllllllllllIIllIlIlIlIIIl.worldObj.theProfiler.startSection("move");
            double llllllllllllllllllIIllIlIllIIllI = llllllllllllllllllIIllIlIlIlIIIl.posX;
            double llllllllllllllllllIIllIlIllIIlIl = llllllllllllllllllIIllIlIlIlIIIl.posY;
            char llllllllllllllllllIIllIlIlIIlIlI = llllllllllllllllllIIllIlIlIlIIIl.posZ;
            if (llllllllllllllllllIIllIlIlIlIIIl.isInWeb) {
               llllllllllllllllllIIllIlIlIlIIIl.isInWeb = false;
               llllllllllllllllllIIllIlIlIlIIII *= 0.25D;
               llllllllllllllllllIIllIlIlIIllll *= 0.05000000074505806D;
               llllllllllllllllllIIllIlIlIIlllI *= 0.25D;
               llllllllllllllllllIIllIlIlIlIIIl.motionX = 0.0D;
               llllllllllllllllllIIllIlIlIlIIIl.motionY = 0.0D;
               llllllllllllllllllIIllIlIlIlIIIl.motionZ = 0.0D;
            }

            float llllllllllllllllllIIllIlIlIIlIIl = llllllllllllllllllIIllIlIlIlIIII;
            double llllllllllllllllllIIllIlIllIIIlI = llllllllllllllllllIIllIlIlIIllll;
            float llllllllllllllllllIIllIlIlIIIlll = llllllllllllllllllIIllIlIlIIlllI;
            boolean llllllllllllllllllIIllIlIllIIIII = llllllllllllllllllIIllIlIlIlIIIl.onGround && llllllllllllllllllIIllIlIlIlIIIl.isSneaking();
            if (llllllllllllllllllIIllIlIllIIIII || llllllllllllllllllIIllIlIlIIllIl.isSafeWalk()) {
               double llllllllllllllllllIIllIlIlIIIlIl;
               for(llllllllllllllllllIIllIlIlIIIlIl = 0.05D; llllllllllllllllllIIllIlIlIlIIII != 0.0D && llllllllllllllllllIIllIlIlIlIIIl.worldObj.getCollidingBoundingBoxes((Entity)llllllllllllllllllIIllIlIlIlIIIl, llllllllllllllllllIIllIlIlIlIIIl.getEntityBoundingBox().offset(llllllllllllllllllIIllIlIlIlIIII, -1.0D, 0.0D)).isEmpty(); llllllllllllllllllIIllIlIlIIlIIl = llllllllllllllllllIIllIlIlIlIIII) {
                  if (llllllllllllllllllIIllIlIlIlIIII < llllllllllllllllllIIllIlIlIIIlIl && llllllllllllllllllIIllIlIlIlIIII >= -llllllllllllllllllIIllIlIlIIIlIl) {
                     llllllllllllllllllIIllIlIlIlIIII = 0.0D;
                  } else if (llllllllllllllllllIIllIlIlIlIIII > 0.0D) {
                     llllllllllllllllllIIllIlIlIlIIII -= llllllllllllllllllIIllIlIlIIIlIl;
                  } else {
                     llllllllllllllllllIIllIlIlIlIIII += llllllllllllllllllIIllIlIlIIIlIl;
                  }
               }

               for(; llllllllllllllllllIIllIlIlIIlllI != 0.0D && llllllllllllllllllIIllIlIlIlIIIl.worldObj.getCollidingBoundingBoxes((Entity)llllllllllllllllllIIllIlIlIlIIIl, llllllllllllllllllIIllIlIlIlIIIl.getEntityBoundingBox().offset(0.0D, -1.0D, llllllllllllllllllIIllIlIlIIlllI)).isEmpty(); llllllllllllllllllIIllIlIlIIIlll = llllllllllllllllllIIllIlIlIIlllI) {
                  if (llllllllllllllllllIIllIlIlIIlllI < llllllllllllllllllIIllIlIlIIIlIl && llllllllllllllllllIIllIlIlIIlllI >= -llllllllllllllllllIIllIlIlIIIlIl) {
                     llllllllllllllllllIIllIlIlIIlllI = 0.0D;
                  } else if (llllllllllllllllllIIllIlIlIIlllI > 0.0D) {
                     llllllllllllllllllIIllIlIlIIlllI -= llllllllllllllllllIIllIlIlIIIlIl;
                  } else {
                     llllllllllllllllllIIllIlIlIIlllI += llllllllllllllllllIIllIlIlIIIlIl;
                  }
               }

               for(; llllllllllllllllllIIllIlIlIlIIII != 0.0D && llllllllllllllllllIIllIlIlIIlllI != 0.0D && llllllllllllllllllIIllIlIlIlIIIl.worldObj.getCollidingBoundingBoxes((Entity)llllllllllllllllllIIllIlIlIlIIIl, llllllllllllllllllIIllIlIlIlIIIl.getEntityBoundingBox().offset(llllllllllllllllllIIllIlIlIlIIII, -1.0D, llllllllllllllllllIIllIlIlIIlllI)).isEmpty(); llllllllllllllllllIIllIlIlIIIlll = llllllllllllllllllIIllIlIlIIlllI) {
                  if (llllllllllllllllllIIllIlIlIlIIII < llllllllllllllllllIIllIlIlIIIlIl && llllllllllllllllllIIllIlIlIlIIII >= -llllllllllllllllllIIllIlIlIIIlIl) {
                     llllllllllllllllllIIllIlIlIlIIII = 0.0D;
                  } else if (llllllllllllllllllIIllIlIlIlIIII > 0.0D) {
                     llllllllllllllllllIIllIlIlIlIIII -= llllllllllllllllllIIllIlIlIIIlIl;
                  } else {
                     llllllllllllllllllIIllIlIlIlIIII += llllllllllllllllllIIllIlIlIIIlIl;
                  }

                  llllllllllllllllllIIllIlIlIIlIIl = llllllllllllllllllIIllIlIlIlIIII;
                  if (llllllllllllllllllIIllIlIlIIlllI < llllllllllllllllllIIllIlIlIIIlIl && llllllllllllllllllIIllIlIlIIlllI >= -llllllllllllllllllIIllIlIlIIIlIl) {
                     llllllllllllllllllIIllIlIlIIlllI = 0.0D;
                  } else if (llllllllllllllllllIIllIlIlIIlllI > 0.0D) {
                     llllllllllllllllllIIllIlIlIIlllI -= llllllllllllllllllIIllIlIlIIIlIl;
                  } else {
                     llllllllllllllllllIIllIlIlIIlllI += llllllllllllllllllIIllIlIlIIIlIl;
                  }
               }
            }

            List<AxisAlignedBB> llllllllllllllllllIIllIlIlIlllll = llllllllllllllllllIIllIlIlIlIIIl.worldObj.getCollidingBoundingBoxes((Entity)llllllllllllllllllIIllIlIlIlIIIl, llllllllllllllllllIIllIlIlIlIIIl.getEntityBoundingBox().addCoord(llllllllllllllllllIIllIlIlIlIIII, llllllllllllllllllIIllIlIlIIllll, llllllllllllllllllIIllIlIlIIlllI));
            AxisAlignedBB llllllllllllllllllIIllIlIlIllllI = llllllllllllllllllIIllIlIlIlIIIl.getEntityBoundingBox();

            AxisAlignedBB llllllllllllllllllIIllIllIIIlIIl;
            for(Iterator llllllllllllllllllIIllIlIlIIIIll = llllllllllllllllllIIllIlIlIlllll.iterator(); llllllllllllllllllIIllIlIlIIIIll.hasNext(); llllllllllllllllllIIllIlIlIIllll = llllllllllllllllllIIllIllIIIlIIl.calculateYOffset(llllllllllllllllllIIllIlIlIlIIIl.getEntityBoundingBox(), llllllllllllllllllIIllIlIlIIllll)) {
               llllllllllllllllllIIllIllIIIlIIl = (AxisAlignedBB)llllllllllllllllllIIllIlIlIIIIll.next();
            }

            llllllllllllllllllIIllIlIlIlIIIl.setEntityBoundingBox(llllllllllllllllllIIllIlIlIlIIIl.getEntityBoundingBox().offset(0.0D, llllllllllllllllllIIllIlIlIIllll, 0.0D));
            boolean llllllllllllllllllIIllIlIlIlllIl = llllllllllllllllllIIllIlIlIlIIIl.onGround || llllllllllllllllllIIllIlIllIIIlI != llllllllllllllllllIIllIlIlIIllll && llllllllllllllllllIIllIlIllIIIlI < 0.0D;

            AxisAlignedBB llllllllllllllllllIIllIlIlIIIIIl;
            Iterator llllllllllllllllllIIllIlIlIIIIlI;
            for(llllllllllllllllllIIllIlIlIIIIlI = llllllllllllllllllIIllIlIlIlllll.iterator(); llllllllllllllllllIIllIlIlIIIIlI.hasNext(); llllllllllllllllllIIllIlIlIlIIII = llllllllllllllllllIIllIlIlIIIIIl.calculateXOffset(llllllllllllllllllIIllIlIlIlIIIl.getEntityBoundingBox(), llllllllllllllllllIIllIlIlIlIIII)) {
               llllllllllllllllllIIllIlIlIIIIIl = (AxisAlignedBB)llllllllllllllllllIIllIlIlIIIIlI.next();
            }

            llllllllllllllllllIIllIlIlIlIIIl.setEntityBoundingBox(llllllllllllllllllIIllIlIlIlIIIl.getEntityBoundingBox().offset(llllllllllllllllllIIllIlIlIlIIII, 0.0D, 0.0D));

            for(llllllllllllllllllIIllIlIlIIIIlI = llllllllllllllllllIIllIlIlIlllll.iterator(); llllllllllllllllllIIllIlIlIIIIlI.hasNext(); llllllllllllllllllIIllIlIlIIlllI = llllllllllllllllllIIllIlIlIIIIIl.calculateZOffset(llllllllllllllllllIIllIlIlIlIIIl.getEntityBoundingBox(), llllllllllllllllllIIllIlIlIIlllI)) {
               llllllllllllllllllIIllIlIlIIIIIl = (AxisAlignedBB)llllllllllllllllllIIllIlIlIIIIlI.next();
            }

            llllllllllllllllllIIllIlIlIlIIIl.setEntityBoundingBox(llllllllllllllllllIIllIlIlIlIIIl.getEntityBoundingBox().offset(0.0D, 0.0D, llllllllllllllllllIIllIlIlIIlllI));
            double llllllllllllllllllIIllIlIIllllIl;
            if (llllllllllllllllllIIllIlIlIlIIIl.stepHeight > 0.0F && llllllllllllllllllIIllIlIlIlllIl && (llllllllllllllllllIIllIlIlIIlIIl != llllllllllllllllllIIllIlIlIlIIII || llllllllllllllllllIIllIlIlIIIlll != llllllllllllllllllIIllIlIlIIlllI)) {
               StepEvent llllllllllllllllllIIllIlIlllllll = new StepEvent(llllllllllllllllllIIllIlIlIlIIIl.stepHeight);
               LiquidBounce.eventManager.callEvent(llllllllllllllllllIIllIlIlllllll);
               double llllllllllllllllllIIllIlIlIIIIIl = llllllllllllllllllIIllIlIlIlIIII;
               double llllllllllllllllllIIllIlIlllllIl = llllllllllllllllllIIllIlIlIIllll;
               llllllllllllllllllIIllIlIIllllIl = llllllllllllllllllIIllIlIlIIlllI;
               float llllllllllllllllllIIllIlIIlllIll = llllllllllllllllllIIllIlIlIlIIIl.getEntityBoundingBox();
               llllllllllllllllllIIllIlIlIlIIIl.setEntityBoundingBox(llllllllllllllllllIIllIlIlIllllI);
               llllllllllllllllllIIllIlIlIIllll = (double)llllllllllllllllllIIllIlIlllllll.getStepHeight();
               List<AxisAlignedBB> llllllllllllllllllIIllIlIIlllIlI = llllllllllllllllllIIllIlIlIlIIIl.worldObj.getCollidingBoundingBoxes((Entity)llllllllllllllllllIIllIlIlIlIIIl, llllllllllllllllllIIllIlIlIlIIIl.getEntityBoundingBox().addCoord(llllllllllllllllllIIllIlIlIIlIIl, llllllllllllllllllIIllIlIlIIllll, llllllllllllllllllIIllIlIlIIIlll));
               String llllllllllllllllllIIllIlIIlllIIl = llllllllllllllllllIIllIlIlIlIIIl.getEntityBoundingBox();
               AxisAlignedBB llllllllllllllllllIIllIlIllllIII = llllllllllllllllllIIllIlIIlllIIl.addCoord(llllllllllllllllllIIllIlIlIIlIIl, 0.0D, llllllllllllllllllIIllIlIlIIIlll);
               double llllllllllllllllllIIllIlIlllIlll = llllllllllllllllllIIllIlIlIIllll;

               AxisAlignedBB llllllllllllllllllIIllIllIIIIllI;
               for(Iterator llllllllllllllllllIIllIlIIllIllI = llllllllllllllllllIIllIlIIlllIlI.iterator(); llllllllllllllllllIIllIlIIllIllI.hasNext(); llllllllllllllllllIIllIlIlllIlll = llllllllllllllllllIIllIllIIIIllI.calculateYOffset(llllllllllllllllllIIllIlIllllIII, llllllllllllllllllIIllIlIlllIlll)) {
                  llllllllllllllllllIIllIllIIIIllI = (AxisAlignedBB)llllllllllllllllllIIllIlIIllIllI.next();
               }

               llllllllllllllllllIIllIlIIlllIIl = llllllllllllllllllIIllIlIIlllIIl.offset(0.0D, llllllllllllllllllIIllIlIlllIlll, 0.0D);
               short llllllllllllllllllIIllIlIIllIllI = llllllllllllllllllIIllIlIlIIlIIl;

               AxisAlignedBB llllllllllllllllllIIllIlIIllIIll;
               for(Iterator llllllllllllllllllIIllIlIIllIlII = llllllllllllllllllIIllIlIIlllIlI.iterator(); llllllllllllllllllIIllIlIIllIlII.hasNext(); llllllllllllllllllIIllIlIIllIllI = llllllllllllllllllIIllIlIIllIIll.calculateXOffset(llllllllllllllllllIIllIlIIlllIIl, llllllllllllllllllIIllIlIIllIllI)) {
                  llllllllllllllllllIIllIlIIllIIll = (AxisAlignedBB)llllllllllllllllllIIllIlIIllIlII.next();
               }

               llllllllllllllllllIIllIlIIlllIIl = llllllllllllllllllIIllIlIIlllIIl.offset(llllllllllllllllllIIllIlIIllIllI, 0.0D, 0.0D);
               double llllllllllllllllllIIllIlIlllIlIl = llllllllllllllllllIIllIlIlIIIlll;

               AxisAlignedBB llllllllllllllllllIIllIlIIllIIIl;
               for(Iterator llllllllllllllllllIIllIlIIllIIlI = llllllllllllllllllIIllIlIIlllIlI.iterator(); llllllllllllllllllIIllIlIIllIIlI.hasNext(); llllllllllllllllllIIllIlIlllIlIl = llllllllllllllllllIIllIlIIllIIIl.calculateZOffset(llllllllllllllllllIIllIlIIlllIIl, llllllllllllllllllIIllIlIlllIlIl)) {
                  llllllllllllllllllIIllIlIIllIIIl = (AxisAlignedBB)llllllllllllllllllIIllIlIIllIIlI.next();
               }

               llllllllllllllllllIIllIlIIlllIIl = llllllllllllllllllIIllIlIIlllIIl.offset(0.0D, 0.0D, llllllllllllllllllIIllIlIlllIlIl);
               byte llllllllllllllllllIIllIlIIllIIlI = llllllllllllllllllIIllIlIlIlIIIl.getEntityBoundingBox();
               double llllllllllllllllllIIllIlIlllIIll = llllllllllllllllllIIllIlIlIIllll;

               AxisAlignedBB llllllllllllllllllIIllIllIIIIIll;
               for(Iterator llllllllllllllllllIIllIlIIllIIII = llllllllllllllllllIIllIlIIlllIlI.iterator(); llllllllllllllllllIIllIlIIllIIII.hasNext(); llllllllllllllllllIIllIlIlllIIll = llllllllllllllllllIIllIllIIIIIll.calculateYOffset(llllllllllllllllllIIllIlIIllIIlI, llllllllllllllllllIIllIlIlllIIll)) {
                  llllllllllllllllllIIllIllIIIIIll = (AxisAlignedBB)llllllllllllllllllIIllIlIIllIIII.next();
               }

               llllllllllllllllllIIllIlIIllIIlI = llllllllllllllllllIIllIlIIllIIlI.offset(0.0D, llllllllllllllllllIIllIlIlllIIll, 0.0D);
               byte llllllllllllllllllIIllIlIIllIIII = llllllllllllllllllIIllIlIlIIlIIl;

               AxisAlignedBB llllllllllllllllllIIllIlIIlIllIl;
               for(Iterator llllllllllllllllllIIllIlIIlIlllI = llllllllllllllllllIIllIlIIlllIlI.iterator(); llllllllllllllllllIIllIlIIlIlllI.hasNext(); llllllllllllllllllIIllIlIIllIIII = llllllllllllllllllIIllIlIIlIllIl.calculateXOffset(llllllllllllllllllIIllIlIIllIIlI, llllllllllllllllllIIllIlIIllIIII)) {
                  llllllllllllllllllIIllIlIIlIllIl = (AxisAlignedBB)llllllllllllllllllIIllIlIIlIlllI.next();
               }

               llllllllllllllllllIIllIlIIllIIlI = llllllllllllllllllIIllIlIIllIIlI.offset(llllllllllllllllllIIllIlIIllIIII, 0.0D, 0.0D);
               double llllllllllllllllllIIllIlIlllIIIl = llllllllllllllllllIIllIlIlIIIlll;

               AxisAlignedBB llllllllllllllllllIIllIlIIlIlIll;
               for(Iterator llllllllllllllllllIIllIlIIlIllII = llllllllllllllllllIIllIlIIlllIlI.iterator(); llllllllllllllllllIIllIlIIlIllII.hasNext(); llllllllllllllllllIIllIlIlllIIIl = llllllllllllllllllIIllIlIIlIlIll.calculateZOffset(llllllllllllllllllIIllIlIIllIIlI, llllllllllllllllllIIllIlIlllIIIl)) {
                  llllllllllllllllllIIllIlIIlIlIll = (AxisAlignedBB)llllllllllllllllllIIllIlIIlIllII.next();
               }

               llllllllllllllllllIIllIlIIllIIlI = llllllllllllllllllIIllIlIIllIIlI.offset(0.0D, 0.0D, llllllllllllllllllIIllIlIlllIIIl);
               float llllllllllllllllllIIllIlIIlIllII = llllllllllllllllllIIllIlIIllIllI * llllllllllllllllllIIllIlIIllIllI + llllllllllllllllllIIllIlIlllIlIl * llllllllllllllllllIIllIlIlllIlIl;
               double llllllllllllllllllIIllIlIIlIlIlI = llllllllllllllllllIIllIlIIllIIII * llllllllllllllllllIIllIlIIllIIII + llllllllllllllllllIIllIlIlllIIIl * llllllllllllllllllIIllIlIlllIIIl;
               if (llllllllllllllllllIIllIlIIlIllII > llllllllllllllllllIIllIlIIlIlIlI) {
                  llllllllllllllllllIIllIlIlIlIIII = llllllllllllllllllIIllIlIIllIllI;
                  llllllllllllllllllIIllIlIlIIlllI = llllllllllllllllllIIllIlIlllIlIl;
                  llllllllllllllllllIIllIlIlIIllll = -llllllllllllllllllIIllIlIlllIlll;
                  llllllllllllllllllIIllIlIlIlIIIl.setEntityBoundingBox(llllllllllllllllllIIllIlIIlllIIl);
               } else {
                  llllllllllllllllllIIllIlIlIlIIII = llllllllllllllllllIIllIlIIllIIII;
                  llllllllllllllllllIIllIlIlIIlllI = llllllllllllllllllIIllIlIlllIIIl;
                  llllllllllllllllllIIllIlIlIIllll = -llllllllllllllllllIIllIlIlllIIll;
                  llllllllllllllllllIIllIlIlIlIIIl.setEntityBoundingBox(llllllllllllllllllIIllIlIIllIIlI);
               }

               AxisAlignedBB llllllllllllllllllIIllIllIIIIIII;
               for(Iterator llllllllllllllllllIIllIlIIlIlIIl = llllllllllllllllllIIllIlIIlllIlI.iterator(); llllllllllllllllllIIllIlIIlIlIIl.hasNext(); llllllllllllllllllIIllIlIlIIllll = llllllllllllllllllIIllIllIIIIIII.calculateYOffset(llllllllllllllllllIIllIlIlIlIIIl.getEntityBoundingBox(), llllllllllllllllllIIllIlIlIIllll)) {
                  llllllllllllllllllIIllIllIIIIIII = (AxisAlignedBB)llllllllllllllllllIIllIlIIlIlIIl.next();
               }

               llllllllllllllllllIIllIlIlIlIIIl.setEntityBoundingBox(llllllllllllllllllIIllIlIlIlIIIl.getEntityBoundingBox().offset(0.0D, llllllllllllllllllIIllIlIlIIllll, 0.0D));
               if (llllllllllllllllllIIllIlIlIIIIIl * llllllllllllllllllIIllIlIlIIIIIl + llllllllllllllllllIIllIlIIllllIl * llllllllllllllllllIIllIlIIllllIl >= llllllllllllllllllIIllIlIlIlIIII * llllllllllllllllllIIllIlIlIlIIII + llllllllllllllllllIIllIlIlIIlllI * llllllllllllllllllIIllIlIlIIlllI) {
                  llllllllllllllllllIIllIlIlIlIIII = llllllllllllllllllIIllIlIlIIIIIl;
                  llllllllllllllllllIIllIlIlIIllll = llllllllllllllllllIIllIlIlllllIl;
                  llllllllllllllllllIIllIlIlIIlllI = llllllllllllllllllIIllIlIIllllIl;
                  llllllllllllllllllIIllIlIlIlIIIl.setEntityBoundingBox(llllllllllllllllllIIllIlIIlllIll);
               } else {
                  LiquidBounce.eventManager.callEvent(new StepConfirmEvent(llllllllllllllllllIIllIlIlIlIIIl.stepHeight));
               }
            }

            llllllllllllllllllIIllIlIlIlIIIl.worldObj.theProfiler.endSection();
            llllllllllllllllllIIllIlIlIlIIIl.worldObj.theProfiler.startSection("rest");
            llllllllllllllllllIIllIlIlIlIIIl.posX = (llllllllllllllllllIIllIlIlIlIIIl.getEntityBoundingBox().minX + llllllllllllllllllIIllIlIlIlIIIl.getEntityBoundingBox().maxX) / 2.0D;
            llllllllllllllllllIIllIlIlIlIIIl.posY = llllllllllllllllllIIllIlIlIlIIIl.getEntityBoundingBox().minY;
            llllllllllllllllllIIllIlIlIlIIIl.posZ = (llllllllllllllllllIIllIlIlIlIIIl.getEntityBoundingBox().minZ + llllllllllllllllllIIllIlIlIlIIIl.getEntityBoundingBox().maxZ) / 2.0D;
            llllllllllllllllllIIllIlIlIlIIIl.isCollidedHorizontally = llllllllllllllllllIIllIlIlIIlIIl != llllllllllllllllllIIllIlIlIlIIII || llllllllllllllllllIIllIlIlIIIlll != llllllllllllllllllIIllIlIlIIlllI;
            llllllllllllllllllIIllIlIlIlIIIl.isCollidedVertically = llllllllllllllllllIIllIlIllIIIlI != llllllllllllllllllIIllIlIlIIllll;
            llllllllllllllllllIIllIlIlIlIIIl.onGround = llllllllllllllllllIIllIlIlIlIIIl.isCollidedVertically && llllllllllllllllllIIllIlIllIIIlI < 0.0D;
            llllllllllllllllllIIllIlIlIlIIIl.isCollided = llllllllllllllllllIIllIlIlIlIIIl.isCollidedHorizontally || llllllllllllllllllIIllIlIlIlIIIl.isCollidedVertically;
            int llllllllllllllllllIIllIlIlIlllII = MathHelper.floor_double(llllllllllllllllllIIllIlIlIlIIIl.posX);
            int llllllllllllllllllIIllIlIlIllIll = MathHelper.floor_double(llllllllllllllllllIIllIlIlIlIIIl.posY - 0.20000000298023224D);
            int llllllllllllllllllIIllIlIlIllIlI = MathHelper.floor_double(llllllllllllllllllIIllIlIlIlIIIl.posZ);
            int llllllllllllllllllIIllIlIIllllll = new BlockPos(llllllllllllllllllIIllIlIlIlllII, llllllllllllllllllIIllIlIlIllIll, llllllllllllllllllIIllIlIlIllIlI);
            Block llllllllllllllllllIIllIlIlIllIII = llllllllllllllllllIIllIlIlIlIIIl.worldObj.getBlockState(llllllllllllllllllIIllIlIIllllll).getBlock();
            if (llllllllllllllllllIIllIlIlIllIII.getMaterial() == Material.air) {
               Block llllllllllllllllllIIllIlIllIlllI = llllllllllllllllllIIllIlIlIlIIIl.worldObj.getBlockState(llllllllllllllllllIIllIlIIllllll.down()).getBlock();
               if (llllllllllllllllllIIllIlIllIlllI instanceof BlockFence || llllllllllllllllllIIllIlIllIlllI instanceof BlockWall || llllllllllllllllllIIllIlIllIlllI instanceof BlockFenceGate) {
                  llllllllllllllllllIIllIlIlIllIII = llllllllllllllllllIIllIlIllIlllI;
                  llllllllllllllllllIIllIlIIllllll = llllllllllllllllllIIllIlIIllllll.down();
               }
            }

            llllllllllllllllllIIllIlIlIlIIIl.updateFallState(llllllllllllllllllIIllIlIlIIllll, llllllllllllllllllIIllIlIlIlIIIl.onGround, llllllllllllllllllIIllIlIlIllIII, llllllllllllllllllIIllIlIIllllll);
            if (llllllllllllllllllIIllIlIlIIlIIl != llllllllllllllllllIIllIlIlIlIIII) {
               llllllllllllllllllIIllIlIlIlIIIl.motionX = 0.0D;
            }

            if (llllllllllllllllllIIllIlIlIIIlll != llllllllllllllllllIIllIlIlIIlllI) {
               llllllllllllllllllIIllIlIlIlIIIl.motionZ = 0.0D;
            }

            if (llllllllllllllllllIIllIlIllIIIlI != llllllllllllllllllIIllIlIlIIllll) {
               llllllllllllllllllIIllIlIlIllIII.onLanded(llllllllllllllllllIIllIlIlIlIIIl.worldObj, (Entity)llllllllllllllllllIIllIlIlIlIIIl);
            }

            if (llllllllllllllllllIIllIlIlIlIIIl.canTriggerWalking() && !llllllllllllllllllIIllIlIllIIIII && llllllllllllllllllIIllIlIlIlIIIl.ridingEntity == null) {
               llllllllllllllllllIIllIlIIllllIl = llllllllllllllllllIIllIlIlIlIIIl.posX - llllllllllllllllllIIllIlIllIIllI;
               float llllllllllllllllllIIllIlIIlllIll = llllllllllllllllllIIllIlIlIlIIIl.posY - llllllllllllllllllIIllIlIllIIlIl;
               String llllllllllllllllllIIllIlIIlllIIl = llllllllllllllllllIIllIlIlIlIIIl.posZ - llllllllllllllllllIIllIlIlIIlIlI;
               if (llllllllllllllllllIIllIlIlIllIII != Blocks.ladder) {
                  llllllllllllllllllIIllIlIIlllIll = 0.0D;
               }

               if (llllllllllllllllllIIllIlIlIllIII != null && llllllllllllllllllIIllIlIlIlIIIl.onGround) {
                  llllllllllllllllllIIllIlIlIllIII.onEntityCollidedWithBlock(llllllllllllllllllIIllIlIlIlIIIl.worldObj, llllllllllllllllllIIllIlIIllllll, (Entity)llllllllllllllllllIIllIlIlIlIIIl);
               }

               llllllllllllllllllIIllIlIlIlIIIl.distanceWalkedModified = (float)((double)llllllllllllllllllIIllIlIlIlIIIl.distanceWalkedModified + (double)MathHelper.sqrt_double(llllllllllllllllllIIllIlIIllllIl * llllllllllllllllllIIllIlIIllllIl + llllllllllllllllllIIllIlIIlllIIl * llllllllllllllllllIIllIlIIlllIIl) * 0.6D);
               llllllllllllllllllIIllIlIlIlIIIl.distanceWalkedOnStepModified = (float)((double)llllllllllllllllllIIllIlIlIlIIIl.distanceWalkedOnStepModified + (double)MathHelper.sqrt_double(llllllllllllllllllIIllIlIIllllIl * llllllllllllllllllIIllIlIIllllIl + llllllllllllllllllIIllIlIIlllIll * llllllllllllllllllIIllIlIIlllIll + llllllllllllllllllIIllIlIIlllIIl * llllllllllllllllllIIllIlIIlllIIl) * 0.6D);
               if (llllllllllllllllllIIllIlIlIlIIIl.distanceWalkedOnStepModified > (float)llllllllllllllllllIIllIlIlIlIIIl.getNextStepDistance() && llllllllllllllllllIIllIlIlIllIII.getMaterial() != Material.air) {
                  llllllllllllllllllIIllIlIlIlIIIl.setNextStepDistance((int)llllllllllllllllllIIllIlIlIlIIIl.distanceWalkedOnStepModified + 1);
                  if (llllllllllllllllllIIllIlIlIlIIIl.isInWater()) {
                     float llllllllllllllllllIIllIlIllIllIl = MathHelper.sqrt_double(llllllllllllllllllIIllIlIlIlIIIl.motionX * llllllllllllllllllIIllIlIlIlIIIl.motionX * 0.20000000298023224D + llllllllllllllllllIIllIlIlIlIIIl.motionY * llllllllllllllllllIIllIlIlIlIIIl.motionY + llllllllllllllllllIIllIlIlIlIIIl.motionZ * llllllllllllllllllIIllIlIlIlIIIl.motionZ * 0.20000000298023224D) * 0.35F;
                     if (llllllllllllllllllIIllIlIllIllIl > 1.0F) {
                        llllllllllllllllllIIllIlIllIllIl = 1.0F;
                     }

                     llllllllllllllllllIIllIlIlIlIIIl.playSound(llllllllllllllllllIIllIlIlIlIIIl.getSwimSound(), llllllllllllllllllIIllIlIllIllIl, 1.0F + (llllllllllllllllllIIllIlIlIlIIIl.rand.nextFloat() - llllllllllllllllllIIllIlIlIlIIIl.rand.nextFloat()) * 0.4F);
                  }

                  llllllllllllllllllIIllIlIlIlIIIl.playStepSound(llllllllllllllllllIIllIlIIllllll, llllllllllllllllllIIllIlIlIllIII);
               }
            }

            try {
               llllllllllllllllllIIllIlIlIlIIIl.doBlockCollisions();
            } catch (Throwable var54) {
               CrashReport llllllllllllllllllIIllIlIllIlIIl = CrashReport.makeCrashReport(var54, "Checking entity block collision");
               float llllllllllllllllllIIllIlIIlllIll = llllllllllllllllllIIllIlIllIlIIl.makeCategory("Entity being checked for collision");
               llllllllllllllllllIIllIlIlIlIIIl.addEntityCrashInfo(llllllllllllllllllIIllIlIIlllIll);
               throw new ReportedException(llllllllllllllllllIIllIlIllIlIIl);
            }

            boolean llllllllllllllllllIIllIlIlIlIlll = llllllllllllllllllIIllIlIlIlIIIl.isWet();
            if (llllllllllllllllllIIllIlIlIlIIIl.worldObj.isFlammableWithin(llllllllllllllllllIIllIlIlIlIIIl.getEntityBoundingBox().contract(0.001D, 0.001D, 0.001D))) {
               llllllllllllllllllIIllIlIlIlIIIl.dealFireDamage(1);
               if (!llllllllllllllllllIIllIlIlIlIlll) {
                  llllllllllllllllllIIllIlIlIlIIIl.setFire(llllllllllllllllllIIllIlIlIlIIIl.getFire() + 1);
                  if (llllllllllllllllllIIllIlIlIlIIIl.getFire() == 0) {
                     llllllllllllllllllIIllIlIlIlIIIl.setFire(8);
                  }
               }
            } else if (llllllllllllllllllIIllIlIlIlIIIl.getFire() <= 0) {
               llllllllllllllllllIIllIlIlIlIIIl.setFire(-llllllllllllllllllIIllIlIlIlIIIl.fireResistance);
            }

            if (llllllllllllllllllIIllIlIlIlIlll && llllllllllllllllllIIllIlIlIlIIIl.getFire() > 0) {
               llllllllllllllllllIIllIlIlIlIIIl.playSound("random.fizz", 0.7F, 1.6F + (llllllllllllllllllIIllIlIlIlIIIl.rand.nextFloat() - llllllllllllllllllIIllIlIlIlIIIl.rand.nextFloat()) * 0.4F);
               llllllllllllllllllIIllIlIlIlIIIl.setFire(-llllllllllllllllllIIllIlIlIlIIIl.fireResistance);
            }

            llllllllllllllllllIIllIlIlIlIIIl.worldObj.theProfiler.endSection();
         }

      }
   }

   @Inject(
      method = {"pushOutOfBlocks"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onPushOutOfBlocks(CallbackInfoReturnable<Boolean> llllllllllllllllllIIllIllllIllII) {
      PushOutEvent llllllllllllllllllIIllIllllIlIll = new PushOutEvent();
      if (llllllllllllllllllIIllIllllIlIlI.noClip) {
         llllllllllllllllllIIllIllllIlIll.cancelEvent();
      }

      LiquidBounce.eventManager.callEvent(llllllllllllllllllIIllIllllIlIll);
      if (llllllllllllllllllIIllIllllIlIll.isCancelled()) {
         llllllllllllllllllIIllIllllIllII.setReturnValue(false);
      }

   }

   @Shadow
   public abstract void playSound(String var1, float var2, float var3);

   @Overwrite
   public void onLivingUpdate() {
      LiquidBounce.eventManager.callEvent(new UpdateEvent());
      if (llllllllllllllllllIIllIlllIIlIII.sprintingTicksLeft > 0) {
         --llllllllllllllllllIIllIlllIIlIII.sprintingTicksLeft;
         if (llllllllllllllllllIIllIlllIIlIII.sprintingTicksLeft == 0) {
            llllllllllllllllllIIllIlllIIlIII.setSprinting(false);
         }
      }

      if (llllllllllllllllllIIllIlllIIlIII.sprintToggleTimer > 0) {
         --llllllllllllllllllIIllIlllIIlIII.sprintToggleTimer;
      }

      llllllllllllllllllIIllIlllIIlIII.prevTimeInPortal = llllllllllllllllllIIllIlllIIlIII.timeInPortal;
      if (llllllllllllllllllIIllIlllIIlIII.inPortal) {
         if (llllllllllllllllllIIllIlllIIlIII.mc.currentScreen != null && !llllllllllllllllllIIllIlllIIlIII.mc.currentScreen.doesGuiPauseGame()) {
            llllllllllllllllllIIllIlllIIlIII.mc.displayGuiScreen((GuiScreen)null);
         }

         if (llllllllllllllllllIIllIlllIIlIII.timeInPortal == 0.0F) {
            llllllllllllllllllIIllIlllIIlIII.mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("portal.trigger"), llllllllllllllllllIIllIlllIIlIII.rand.nextFloat() * 0.4F + 0.8F));
         }

         llllllllllllllllllIIllIlllIIlIII.timeInPortal += 0.0125F;
         if (llllllllllllllllllIIllIlllIIlIII.timeInPortal >= 1.0F) {
            llllllllllllllllllIIllIlllIIlIII.timeInPortal = 1.0F;
         }

         llllllllllllllllllIIllIlllIIlIII.inPortal = false;
      } else if (llllllllllllllllllIIllIlllIIlIII.isPotionActive(Potion.confusion) && llllllllllllllllllIIllIlllIIlIII.getActivePotionEffect(Potion.confusion).getDuration() > 60) {
         llllllllllllllllllIIllIlllIIlIII.timeInPortal += 0.006666667F;
         if (llllllllllllllllllIIllIlllIIlIII.timeInPortal > 1.0F) {
            llllllllllllllllllIIllIlllIIlIII.timeInPortal = 1.0F;
         }
      } else {
         if (llllllllllllllllllIIllIlllIIlIII.timeInPortal > 0.0F) {
            llllllllllllllllllIIllIlllIIlIII.timeInPortal -= 0.05F;
         }

         if (llllllllllllllllllIIllIlllIIlIII.timeInPortal < 0.0F) {
            llllllllllllllllllIIllIlllIIlIII.timeInPortal = 0.0F;
         }
      }

      if (llllllllllllllllllIIllIlllIIlIII.timeUntilPortal > 0) {
         --llllllllllllllllllIIllIlllIIlIII.timeUntilPortal;
      }

      boolean llllllllllllllllllIIllIlllIIIlll = llllllllllllllllllIIllIlllIIlIII.movementInput.jump;
      boolean llllllllllllllllllIIllIlllIIIllI = llllllllllllllllllIIllIlllIIlIII.movementInput.sneak;
      char llllllllllllllllllIIllIllIlllIll = 0.8F;
      boolean llllllllllllllllllIIllIlllIIIlII = llllllllllllllllllIIllIlllIIlIII.movementInput.moveForward >= llllllllllllllllllIIllIllIlllIll;
      llllllllllllllllllIIllIlllIIlIII.movementInput.updatePlayerMoveState();
      float llllllllllllllllllIIllIllIlllIIl = (NoSlow)LiquidBounce.moduleManager.getModule(NoSlow.class);
      boolean llllllllllllllllllIIllIllIlllIII = (Aura)LiquidBounce.moduleManager.getModule(Aura.class);
      if (llllllllllllllllllIIllIlllIIlIII.getHeldItem() != null && (llllllllllllllllllIIllIlllIIlIII.isUsingItem() || llllllllllllllllllIIllIlllIIlIII.getHeldItem().getItem() instanceof ItemSword && llllllllllllllllllIIllIllIlllIII.getBlockingStatus()) && !llllllllllllllllllIIllIlllIIlIII.isRiding()) {
         int llllllllllllllllllIIllIllIllIlll = new SlowDownEvent(0.2F, 0.2F);
         LiquidBounce.eventManager.callEvent(llllllllllllllllllIIllIllIllIlll);
         MovementInput var10000 = llllllllllllllllllIIllIlllIIlIII.movementInput;
         var10000.moveStrafe *= llllllllllllllllllIIllIllIllIlll.getStrafe();
         var10000 = llllllllllllllllllIIllIlllIIlIII.movementInput;
         var10000.moveForward *= llllllllllllllllllIIllIllIllIlll.getForward();
         llllllllllllllllllIIllIlllIIlIII.sprintToggleTimer = 0;
      }

      llllllllllllllllllIIllIlllIIlIII.pushOutOfBlocks(llllllllllllllllllIIllIlllIIlIII.posX - (double)llllllllllllllllllIIllIlllIIlIII.width * 0.35D, llllllllllllllllllIIllIlllIIlIII.getEntityBoundingBox().minY + 0.5D, llllllllllllllllllIIllIlllIIlIII.posZ + (double)llllllllllllllllllIIllIlllIIlIII.width * 0.35D);
      boolean var10001 = false;
      llllllllllllllllllIIllIlllIIlIII.pushOutOfBlocks(llllllllllllllllllIIllIlllIIlIII.posX - (double)llllllllllllllllllIIllIlllIIlIII.width * 0.35D, llllllllllllllllllIIllIlllIIlIII.getEntityBoundingBox().minY + 0.5D, llllllllllllllllllIIllIlllIIlIII.posZ - (double)llllllllllllllllllIIllIlllIIlIII.width * 0.35D);
      var10001 = false;
      llllllllllllllllllIIllIlllIIlIII.pushOutOfBlocks(llllllllllllllllllIIllIlllIIlIII.posX + (double)llllllllllllllllllIIllIlllIIlIII.width * 0.35D, llllllllllllllllllIIllIlllIIlIII.getEntityBoundingBox().minY + 0.5D, llllllllllllllllllIIllIlllIIlIII.posZ - (double)llllllllllllllllllIIllIlllIIlIII.width * 0.35D);
      var10001 = false;
      llllllllllllllllllIIllIlllIIlIII.pushOutOfBlocks(llllllllllllllllllIIllIlllIIlIII.posX + (double)llllllllllllllllllIIllIlllIIlIII.width * 0.35D, llllllllllllllllllIIllIlllIIlIII.getEntityBoundingBox().minY + 0.5D, llllllllllllllllllIIllIlllIIlIII.posZ + (double)llllllllllllllllllIIllIlllIIlIII.width * 0.35D);
      var10001 = false;
      int llllllllllllllllllIIllIllIllIlll = (Sprint)LiquidBounce.moduleManager.getModule(Sprint.class);
      double llllllllllllllllllIIllIllIllIllI = !(Boolean)llllllllllllllllllIIllIllIllIlll.foodValue.get() || (float)llllllllllllllllllIIllIlllIIlIII.getFoodStats().getFoodLevel() > 6.0F || llllllllllllllllllIIllIlllIIlIII.capabilities.allowFlying;
      if (llllllllllllllllllIIllIlllIIlIII.onGround && !llllllllllllllllllIIllIlllIIIllI && !llllllllllllllllllIIllIlllIIIlII && llllllllllllllllllIIllIlllIIlIII.movementInput.moveForward >= llllllllllllllllllIIllIllIlllIll && !llllllllllllllllllIIllIlllIIlIII.isSprinting() && llllllllllllllllllIIllIllIllIllI && !llllllllllllllllllIIllIlllIIlIII.isUsingItem() && !llllllllllllllllllIIllIlllIIlIII.isPotionActive(Potion.blindness)) {
         if (llllllllllllllllllIIllIlllIIlIII.sprintToggleTimer <= 0 && !llllllllllllllllllIIllIlllIIlIII.mc.gameSettings.keyBindSprint.isKeyDown()) {
            llllllllllllllllllIIllIlllIIlIII.sprintToggleTimer = 7;
         } else {
            llllllllllllllllllIIllIlllIIlIII.setSprinting(true);
         }
      }

      if (!llllllllllllllllllIIllIlllIIlIII.isSprinting() && llllllllllllllllllIIllIlllIIlIII.movementInput.moveForward >= llllllllllllllllllIIllIllIlllIll && llllllllllllllllllIIllIllIllIllI && (llllllllllllllllllIIllIllIlllIIl.getState() || !llllllllllllllllllIIllIlllIIlIII.isUsingItem()) && !llllllllllllllllllIIllIlllIIlIII.isPotionActive(Potion.blindness) && llllllllllllllllllIIllIlllIIlIII.mc.gameSettings.keyBindSprint.isKeyDown()) {
         llllllllllllllllllIIllIlllIIlIII.setSprinting(true);
      }

      String llllllllllllllllllIIllIllIllIlIl = (Scaffold)LiquidBounce.moduleManager.getModule(Scaffold.class);
      if (llllllllllllllllllIIllIllIllIlIl.getState() && !(Boolean)llllllllllllllllllIIllIllIllIlIl.sprintValue.get() || llllllllllllllllllIIllIllIllIlll.getState() && (Boolean)llllllllllllllllllIIllIllIllIlll.checkServerSide.get() && (llllllllllllllllllIIllIlllIIlIII.onGround || !(Boolean)llllllllllllllllllIIllIllIllIlll.checkServerSideGround.get()) && !(Boolean)llllllllllllllllllIIllIllIllIlll.allDirectionsValue.get() && RotationUtils.targetRotation != null && RotationUtils.getRotationDifference(new Rotation(llllllllllllllllllIIllIlllIIlIII.mc.thePlayer.rotationYaw, llllllllllllllllllIIllIlllIIlIII.mc.thePlayer.rotationPitch)) > 30.0D) {
         llllllllllllllllllIIllIlllIIlIII.setSprinting(false);
      }

      if (llllllllllllllllllIIllIlllIIlIII.isSprinting() && ((!llllllllllllllllllIIllIllIllIlll.getState() || !(Boolean)llllllllllllllllllIIllIllIllIlll.allDirectionsValue.get()) && llllllllllllllllllIIllIlllIIlIII.movementInput.moveForward < llllllllllllllllllIIllIllIlllIll || llllllllllllllllllIIllIlllIIlIII.isCollidedHorizontally || !llllllllllllllllllIIllIllIllIllI)) {
         llllllllllllllllllIIllIlllIIlIII.setSprinting(false);
      }

      if (llllllllllllllllllIIllIlllIIlIII.capabilities.allowFlying) {
         if (llllllllllllllllllIIllIlllIIlIII.mc.playerController.isSpectatorMode()) {
            if (!llllllllllllllllllIIllIlllIIlIII.capabilities.isFlying) {
               llllllllllllllllllIIllIlllIIlIII.capabilities.isFlying = true;
               llllllllllllllllllIIllIlllIIlIII.sendPlayerAbilities();
            }
         } else if (!llllllllllllllllllIIllIlllIIIlll && llllllllllllllllllIIllIlllIIlIII.movementInput.jump) {
            if (llllllllllllllllllIIllIlllIIlIII.flyToggleTimer == 0) {
               llllllllllllllllllIIllIlllIIlIII.flyToggleTimer = 7;
            } else {
               llllllllllllllllllIIllIlllIIlIII.capabilities.isFlying = !llllllllllllllllllIIllIlllIIlIII.capabilities.isFlying;
               llllllllllllllllllIIllIlllIIlIII.sendPlayerAbilities();
               llllllllllllllllllIIllIlllIIlIII.flyToggleTimer = 0;
            }
         }
      }

      if (llllllllllllllllllIIllIlllIIlIII.capabilities.isFlying && llllllllllllllllllIIllIlllIIlIII.isCurrentViewEntity()) {
         if (llllllllllllllllllIIllIlllIIlIII.movementInput.sneak) {
            llllllllllllllllllIIllIlllIIlIII.motionY -= (double)(llllllllllllllllllIIllIlllIIlIII.capabilities.getFlySpeed() * 3.0F);
         }

         if (llllllllllllllllllIIllIlllIIlIII.movementInput.jump) {
            llllllllllllllllllIIllIlllIIlIII.motionY += (double)(llllllllllllllllllIIllIlllIIlIII.capabilities.getFlySpeed() * 3.0F);
         }
      }

      if (llllllllllllllllllIIllIlllIIlIII.isRidingHorse()) {
         if (llllllllllllllllllIIllIlllIIlIII.horseJumpPowerCounter < 0) {
            ++llllllllllllllllllIIllIlllIIlIII.horseJumpPowerCounter;
            if (llllllllllllllllllIIllIlllIIlIII.horseJumpPowerCounter == 0) {
               llllllllllllllllllIIllIlllIIlIII.horseJumpPower = 0.0F;
            }
         }

         if (llllllllllllllllllIIllIlllIIIlll && !llllllllllllllllllIIllIlllIIlIII.movementInput.jump) {
            llllllllllllllllllIIllIlllIIlIII.horseJumpPowerCounter = -10;
            llllllllllllllllllIIllIlllIIlIII.sendHorseJump();
         } else if (!llllllllllllllllllIIllIlllIIIlll && llllllllllllllllllIIllIlllIIlIII.movementInput.jump) {
            llllllllllllllllllIIllIlllIIlIII.horseJumpPowerCounter = 0;
            llllllllllllllllllIIllIlllIIlIII.horseJumpPower = 0.0F;
         } else if (llllllllllllllllllIIllIlllIIIlll) {
            ++llllllllllllllllllIIllIlllIIlIII.horseJumpPowerCounter;
            if (llllllllllllllllllIIllIlllIIlIII.horseJumpPowerCounter < 10) {
               llllllllllllllllllIIllIlllIIlIII.horseJumpPower = (float)llllllllllllllllllIIllIlllIIlIII.horseJumpPowerCounter * 0.1F;
            } else {
               llllllllllllllllllIIllIlllIIlIII.horseJumpPower = 0.8F + 2.0F / (float)(llllllllllllllllllIIllIlllIIlIII.horseJumpPowerCounter - 9) * 0.1F;
            }
         }
      } else {
         llllllllllllllllllIIllIlllIIlIII.horseJumpPower = 0.0F;
      }

      super.onLivingUpdate();
      if (llllllllllllllllllIIllIlllIIlIII.onGround && llllllllllllllllllIIllIlllIIlIII.capabilities.isFlying && !llllllllllllllllllIIllIlllIIlIII.mc.playerController.isSpectatorMode()) {
         llllllllllllllllllIIllIlllIIlIII.capabilities.isFlying = false;
         llllllllllllllllllIIllIlllIIlIII.sendPlayerAbilities();
      }

   }

   @Inject(
      method = {"swingItem"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void swingItem(CallbackInfo llllllllllllllllllIIllIlllllIIlI) {
      NoSwing llllllllllllllllllIIllIlllllIlII = (NoSwing)LiquidBounce.moduleManager.getModule(NoSwing.class);
      if (llllllllllllllllllIIllIlllllIlII.getState()) {
         llllllllllllllllllIIllIlllllIIlI.cancel();
         if (!(Boolean)llllllllllllllllllIIllIlllllIlII.getServerSideValue().get()) {
            llllllllllllllllllIIllIlllllIllI.sendQueue.addToSendQueue(new C0APacketAnimation());
         }
      }

   }

   @Shadow
   public abstract boolean isRidingHorse();

   @Shadow
   public abstract void setSprinting(boolean var1);
}
