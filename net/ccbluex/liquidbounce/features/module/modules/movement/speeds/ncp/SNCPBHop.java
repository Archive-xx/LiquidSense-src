//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.potion.Potion;
import net.minecraft.util.MovementInput;

public class SNCPBHop extends SpeedMode {
   // $FF: synthetic field
   private double lastDist;
   // $FF: synthetic field
   private double moveSpeed = 0.2873D;
   // $FF: synthetic field
   private int level = 1;
   // $FF: synthetic field
   private int timerDelay;

   public void onDisable() {
      mc.timer.timerSpeed = 1.0F;
      lIIIIlllIlIl.moveSpeed = lIIIIlllIlIl.getBaseMoveSpeed();
      lIIIIlllIlIl.level = 0;
   }

   public void onMove(MoveEvent lIIIIlIlIlll) {
      ++lIIIIlIllIII.timerDelay;
      lIIIIlIllIII.timerDelay %= 5;
      EntityPlayerSP var10000;
      if (lIIIIlIllIII.timerDelay != 0) {
         mc.timer.timerSpeed = 1.0F;
      } else {
         if (MovementUtils.isMoving()) {
            mc.timer.timerSpeed = 32767.0F;
         }

         if (MovementUtils.isMoving()) {
            mc.timer.timerSpeed = 1.3F;
            var10000 = mc.thePlayer;
            var10000.motionX *= 1.0199999809265137D;
            var10000 = mc.thePlayer;
            var10000.motionZ *= 1.0199999809265137D;
         }
      }

      if (mc.thePlayer.onGround && MovementUtils.isMoving()) {
         lIIIIlIllIII.level = 2;
      }

      if (lIIIIlIllIII.round(mc.thePlayer.posY - (double)((int)mc.thePlayer.posY)) == lIIIIlIllIII.round(0.138D)) {
         var10000 = mc.thePlayer;
         var10000.motionY -= 0.08D;
         lIIIIlIlIlll.setY(lIIIIlIlIlll.getY() - 0.09316090325960147D);
         var10000 = mc.thePlayer;
         var10000.posY -= 0.09316090325960147D;
      }

      if (lIIIIlIllIII.level == 1 && (mc.thePlayer.moveForward != 0.0F || mc.thePlayer.moveStrafing != 0.0F)) {
         lIIIIlIllIII.level = 2;
         lIIIIlIllIII.moveSpeed = 1.35D * lIIIIlIllIII.getBaseMoveSpeed() - 0.01D;
      } else if (lIIIIlIllIII.level == 2) {
         lIIIIlIllIII.level = 3;
         mc.thePlayer.motionY = 0.399399995803833D;
         lIIIIlIlIlll.setY(0.399399995803833D);
         lIIIIlIllIII.moveSpeed *= 2.149D;
      } else if (lIIIIlIllIII.level == 3) {
         lIIIIlIllIII.level = 4;
         double lIIIIllIIIIl = 0.66D * (lIIIIlIllIII.lastDist - lIIIIlIllIII.getBaseMoveSpeed());
         lIIIIlIllIII.moveSpeed = lIIIIlIllIII.lastDist - lIIIIllIIIIl;
      } else if (lIIIIlIllIII.level == 88) {
         lIIIIlIllIII.moveSpeed = lIIIIlIllIII.getBaseMoveSpeed();
         lIIIIlIllIII.lastDist = 0.0D;
         lIIIIlIllIII.level = 89;
      } else {
         if (lIIIIlIllIII.level == 89) {
            if (mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0D, mc.thePlayer.motionY, 0.0D)).size() > 0 || mc.thePlayer.isCollidedVertically) {
               lIIIIlIllIII.level = 1;
            }

            lIIIIlIllIII.lastDist = 0.0D;
            lIIIIlIllIII.moveSpeed = lIIIIlIllIII.getBaseMoveSpeed();
            return;
         }

         if (mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0D, mc.thePlayer.motionY, 0.0D)).size() > 0 || mc.thePlayer.isCollidedVertically) {
            lIIIIlIllIII.moveSpeed = lIIIIlIllIII.getBaseMoveSpeed();
            lIIIIlIllIII.lastDist = 0.0D;
            lIIIIlIllIII.level = 88;
            return;
         }

         lIIIIlIllIII.moveSpeed = lIIIIlIllIII.lastDist - lIIIIlIllIII.lastDist / 159.0D;
      }

      lIIIIlIllIII.moveSpeed = Math.max(lIIIIlIllIII.moveSpeed, lIIIIlIllIII.getBaseMoveSpeed());
      MovementInput lIIIIlIllllI = mc.thePlayer.movementInput;
      boolean lIIIIlIlIlIl = lIIIIlIllllI.moveForward;
      float lIIIIlIlllII = lIIIIlIllllI.moveStrafe;
      float lIIIIlIllIll = mc.thePlayer.rotationYaw;
      if (lIIIIlIlIlIl == 0.0F && lIIIIlIlllII == 0.0F) {
         lIIIIlIlIlll.setX(0.0D);
         lIIIIlIlIlll.setZ(0.0D);
      } else if (lIIIIlIlIlIl != 0.0F) {
         if (lIIIIlIlllII >= 1.0F) {
            lIIIIlIllIll += (float)(lIIIIlIlIlIl > 0.0F ? -45 : 45);
            lIIIIlIlllII = 0.0F;
         } else if (lIIIIlIlllII <= -1.0F) {
            lIIIIlIllIll += (float)(lIIIIlIlIlIl > 0.0F ? 45 : -45);
            lIIIIlIlllII = 0.0F;
         }

         if (lIIIIlIlIlIl > 0.0F) {
            lIIIIlIlIlIl = 1.0F;
         } else if (lIIIIlIlIlIl < 0.0F) {
            lIIIIlIlIlIl = -1.0F;
         }
      }

      double lIIIIlIllIlI = Math.cos(Math.toRadians((double)(lIIIIlIllIll + 90.0F)));
      boolean lIIIIlIlIIIl = Math.sin(Math.toRadians((double)(lIIIIlIllIll + 90.0F)));
      lIIIIlIlIlll.setX((double)lIIIIlIlIlIl * lIIIIlIllIII.moveSpeed * lIIIIlIllIlI + (double)lIIIIlIlllII * lIIIIlIllIII.moveSpeed * lIIIIlIlIIIl);
      lIIIIlIlIlll.setZ((double)lIIIIlIlIlIl * lIIIIlIllIII.moveSpeed * lIIIIlIlIIIl - (double)lIIIIlIlllII * lIIIIlIllIII.moveSpeed * lIIIIlIllIlI);
      mc.thePlayer.stepHeight = 0.6F;
      if (lIIIIlIlIlIl == 0.0F && lIIIIlIlllII == 0.0F) {
         lIIIIlIlIlll.setX(0.0D);
         lIIIIlIlIlll.setZ(0.0D);
      }

   }

   public void onEnable() {
      mc.timer.timerSpeed = 1.0F;
      lIIIIlllIlll.lastDist = 0.0D;
      lIIIIlllIlll.moveSpeed = 0.0D;
      lIIIIlllIlll.level = 4;
   }

   public void onUpdate() {
   }

   public SNCPBHop() {
      super("SNCPBHop");
   }

   private double round(double lIIIIlIIlIIl) {
      int lIIIIlIIIllI = new BigDecimal(lIIIIlIIlIIl);
      lIIIIlIIIllI = lIIIIlIIIllI.setScale(3, RoundingMode.HALF_UP);
      return lIIIIlIIIllI.doubleValue();
   }

   public void onMotion() {
      double lIIIIllIllll = mc.thePlayer.posX - mc.thePlayer.prevPosX;
      double lIIIIllIlllI = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;
      lIIIIlllIIII.lastDist = Math.sqrt(lIIIIllIllll * lIIIIllIllll + lIIIIllIlllI * lIIIIllIlllI);
   }

   private double getBaseMoveSpeed() {
      String lIIIIlIIllIl = 0.2873D;
      if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
         lIIIIlIIllIl *= 1.0D + 0.2D * (double)(mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
      }

      return lIIIIlIIllIl;
   }
}
