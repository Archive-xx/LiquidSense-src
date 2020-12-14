//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aquavit;

import java.util.Random;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;

public class SlowDown extends SpeedMode {
   // $FF: synthetic field
   private int level = 1;
   // $FF: synthetic field
   static Minecraft mc = Minecraft.getMinecraft();
   // $FF: synthetic field
   private double moveSpeed = 0.2873D;
   // $FF: synthetic field
   private Random random = new Random();
   // $FF: synthetic field
   private double lastDist;
   // $FF: synthetic field
   private double speed = 0.07999999821186066D;

   private boolean canZoom() {
      return (mc.thePlayer.moveForward != 0.0F || mc.thePlayer.moveStrafing != 0.0F) && mc.thePlayer.onGround;
   }

   public void onUpdate() {
      if (!mc.thePlayer.isInWater() && !mc.thePlayer.isSneaking() && !(mc.thePlayer.getHealth() < 0.0F)) {
         if (mc.thePlayer.onGround && llllllllllllllllllllllIIlIIIIlIl.canZoom()) {
            mc.thePlayer.jump();
         }

      }
   }

   public void onEnable() {
      mc.timer.timerSpeed = 1.0F;
      llllllllllllllllllllllIIlIlIllII.level = mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0D, mc.thePlayer.motionY, 0.0D)).size() <= 0 && !mc.thePlayer.isCollidedVertically ? 4 : 1;
   }

   public void onMove(MoveEvent llllllllllllllllllllllIIIlIIllIl) {
      if (!mc.thePlayer.isInWater() && !mc.thePlayer.isSneaking() && !(mc.thePlayer.getHealth() < 0.0F)) {
         switch(llllllllllllllllllllllIIIlIIllll.level) {
         case -1:
            if (llllllllllllllllllllllIIIlIIllll.canZoom()) {
               llllllllllllllllllllllIIIlIIllll.lastDist = 0.0D;
               llllllllllllllllllllllIIIlIIllll.level = 0;
            }
         default:
            int llllllllllllllllllllllIIIlIIllII = 0;

            for(; llllllllllllllllllllllIIIlIIllII < 4; ++llllllllllllllllllllllIIIlIIllII) {
               if (llllllllllllllllllllllIIIlIIllII == 1) {
                  llllllllllllllllllllllIIIlIIllII = 1;
                  if (llllllllllllllllllllllIIIlIIllll.level == llllllllllllllllllllllIIIlIIllII && llllllllllllllllllllllIIIlIIllll.canZoom()) {
                     ++llllllllllllllllllllllIIIlIIllll.level;
                     llllllllllllllllllllllIIIlIIllll.moveSpeed = 1.0353000025D * llllllllllllllllllllllIIIlIIllll.getBaseMoveSpeed() - 0.011D;
                  }
               } else if (llllllllllllllllllllllIIIlIIllII == 2) {
                  llllllllllllllllllllllIIIlIIllII = 2;
                  if (llllllllllllllllllllllIIIlIIllll.level == llllllllllllllllllllllIIIlIIllII && llllllllllllllllllllllIIIlIIllll.canZoom()) {
                     llllllllllllllllllllllIIIlIIllll.moveSpeed *= 1.599991D;
                  } else if (llllllllllllllllllllllIIIlIIllll.level == 3) {
                     for(float llllllllllllllllllllllIIIlIlIlll = 0.6666F; (double)llllllllllllllllllllllIIIlIlIlll > 0.66D; llllllllllllllllllllllIIIlIlIlll = (float)((double)llllllllllllllllllllllIIIlIlIlll - 1.0E-4D)) {
                        double llllllllllllllllllllllIIIlIllIIl = (mc.thePlayer.isCollidedVertically ? 1.0E-4D - Math.random() : 1.0E-5D + Math.random()) + (double)llllllllllllllllllllllIIIlIlIlll * (llllllllllllllllllllllIIIlIIllll.lastDist - llllllllllllllllllllllIIIlIIllll.getBaseMoveSpeed());
                        llllllllllllllllllllllIIIlIIllll.moveSpeed = llllllllllllllllllllllIIIlIIllll.lastDist - llllllllllllllllllllllIIIlIllIIl;
                     }
                  } else {
                     if (mc.thePlayer.isCollidedVertically || llllllllllllllllllllllIIIlIIllll.level > 0) {
                        llllllllllllllllllllllIIIlIIllll.level = mc.thePlayer.moveForward == 0.0F && mc.thePlayer.moveStrafing == 0.0F ? 0 : 1;
                     }

                     llllllllllllllllllllllIIIlIIllll.moveSpeed = llllllllllllllllllllllIIIlIIllll.lastDist - llllllllllllllllllllllIIIlIIllll.lastDist / 159.0D;
                  }
               }
            }

            llllllllllllllllllllllIIIlIIllll.moveSpeed = Math.max(llllllllllllllllllllllIIIlIIllll.moveSpeed, llllllllllllllllllllllIIIlIIllll.getBaseMoveSpeed());
            if (mc.thePlayer.moveForward != 0.0F || mc.thePlayer.moveStrafing != 0.0F) {
               if (llllllllllllllllllllllIIIlIIllll.level > 0) {
                  llllllllllllllllllllllIIIlIIllll.setMotion(llllllllllllllllllllllIIIlIIllIl, llllllllllllllllllllllIIIlIIllll.moveSpeed, (float)RandomUtils.nextInt(44, 45), 90.0F);
               }

               ++llllllllllllllllllllllIIIlIIllll.level;
            }

         }
      }
   }

   private void setMotion(MoveEvent llllllllllllllllllllllIIIIlIIIIl, double llllllllllllllllllllllIIIIIlllll, float llllllllllllllllllllllIIIIIIlllI, float llllllllllllllllllllllIIIIIIllIl) {
      double llllllllllllllllllllllIIIIIllIIl = (double)Minecraft.getMinecraft().thePlayer.movementInput.moveForward;
      long llllllllllllllllllllllIIIIIIlIll = (double)Minecraft.getMinecraft().thePlayer.movementInput.moveStrafe;
      double llllllllllllllllllllllIIIIIIlIlI = Minecraft.getMinecraft().thePlayer.rotationYaw;
      if (llllllllllllllllllllllIIIIIllIIl == 0.0D && llllllllllllllllllllllIIIIIIlIll == 0.0D) {
         llllllllllllllllllllllIIIIlIIIIl.setX(0.0D);
         llllllllllllllllllllllIIIIlIIIIl.setZ(0.0D);
      } else {
         if (llllllllllllllllllllllIIIIIllIIl != 0.0D) {
            if (llllllllllllllllllllllIIIIIIlIll > 0.0D) {
               llllllllllllllllllllllIIIIIIlIlI += llllllllllllllllllllllIIIIIllIIl > 0.0D ? -llllllllllllllllllllllIIIIIIlllI : llllllllllllllllllllllIIIIIIlllI;
            } else if (llllllllllllllllllllllIIIIIIlIll < 0.0D) {
               llllllllllllllllllllllIIIIIIlIlI += llllllllllllllllllllllIIIIIllIIl > 0.0D ? llllllllllllllllllllllIIIIIIlllI : -llllllllllllllllllllllIIIIIIlllI;
            }

            llllllllllllllllllllllIIIIIIlIll = 0.0D;
            if (llllllllllllllllllllllIIIIIllIIl > 0.0D) {
               llllllllllllllllllllllIIIIIllIIl = 1.0D;
            } else if (llllllllllllllllllllllIIIIIllIIl < 0.0D) {
               llllllllllllllllllllllIIIIIllIIl = -1.0D;
            }
         }

         llllllllllllllllllllllIIIIlIIIIl.setX(llllllllllllllllllllllIIIIIllIIl * llllllllllllllllllllllIIIIIlllll * Math.cos(Math.toRadians((double)(llllllllllllllllllllllIIIIIIlIlI + llllllllllllllllllllllIIIIIIllIl))) + llllllllllllllllllllllIIIIIIlIll * llllllllllllllllllllllIIIIIlllll * Math.sin(Math.toRadians((double)(llllllllllllllllllllllIIIIIIlIlI + llllllllllllllllllllllIIIIIIllIl))));
         llllllllllllllllllllllIIIIlIIIIl.setZ(llllllllllllllllllllllIIIIIllIIl * llllllllllllllllllllllIIIIIlllll * Math.sin(Math.toRadians((double)(llllllllllllllllllllllIIIIIIlIlI + llllllllllllllllllllllIIIIIIllIl))) - llllllllllllllllllllllIIIIIIlIll * llllllllllllllllllllllIIIIIlllll * Math.cos(Math.toRadians((double)(llllllllllllllllllllllIIIIIIlIlI + llllllllllllllllllllllIIIIIIllIl))));
      }

   }

   public SlowDown() {
      super("SlowDown");
   }

   public void onDisable() {
      mc.timer.timerSpeed = 1.0F;
      llllllllllllllllllllllIIlIlIIIll.moveSpeed = llllllllllllllllllllllIIlIlIIIll.getBaseMoveSpeed();
      llllllllllllllllllllllIIlIlIIIll.level = 0;
   }

   public void onMotion() {
      float llllllllllllllllllllllIIlIIlIIll = mc.thePlayer.posX - mc.thePlayer.prevPosX;
      long llllllllllllllllllllllIIlIIlIIIl = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;
      llllllllllllllllllllllIIlIIlIlIl.lastDist = Math.sqrt(llllllllllllllllllllllIIlIIlIIll * llllllllllllllllllllllIIlIIlIIll + llllllllllllllllllllllIIlIIlIIIl * llllllllllllllllllllllIIlIIlIIIl);
   }

   private double getBaseMoveSpeed() {
      double llllllllllllllllllllllIIIlIIIlll = 0.2873D;
      if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
         llllllllllllllllllllllIIIlIIIlll *= 1.0D + 0.2D * (double)(mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
      }

      return llllllllllllllllllllllIIIlIIIlll;
   }
}
