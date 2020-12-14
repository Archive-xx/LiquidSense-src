//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.potion.Potion;

public class NCPBHop extends SpeedMode {
   // $FF: synthetic field
   private int level = 1;
   // $FF: synthetic field
   private double lastDist;
   // $FF: synthetic field
   private double moveSpeed = 0.2873D;
   // $FF: synthetic field
   private int timerDelay;

   public void onDisable() {
      mc.timer.timerSpeed = 1.0F;
      llllllllllllllllllIllIlIlIIllIIl.moveSpeed = llllllllllllllllllIllIlIlIIllIIl.getBaseMoveSpeed();
      llllllllllllllllllIllIlIlIIllIIl.level = 0;
   }

   public NCPBHop() {
      super("NCPBHop");
   }

   private double round(double llllllllllllllllllIllIlIIllIlIll) {
      float llllllllllllllllllIllIlIIllIlIlI = new BigDecimal(llllllllllllllllllIllIlIIllIlIll);
      llllllllllllllllllIllIlIIllIlIlI = llllllllllllllllllIllIlIIllIlIlI.setScale(3, RoundingMode.HALF_UP);
      return llllllllllllllllllIllIlIIllIlIlI.doubleValue();
   }

   public void onMotion() {
      double llllllllllllllllllIllIlIlIIlIlII = mc.thePlayer.posX - mc.thePlayer.prevPosX;
      short llllllllllllllllllIllIlIlIIlIIII = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;
      llllllllllllllllllIllIlIlIIlIlIl.lastDist = Math.sqrt(llllllllllllllllllIllIlIlIIlIlII * llllllllllllllllllIllIlIlIIlIlII + llllllllllllllllllIllIlIlIIlIIII * llllllllllllllllllIllIlIlIIlIIII);
   }

   public void onEnable() {
      mc.timer.timerSpeed = 1.0F;
      llllllllllllllllllIllIlIlIIlllIl.level = mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0D, mc.thePlayer.motionY, 0.0D)).size() <= 0 && !mc.thePlayer.isCollidedVertically ? 4 : 1;
   }

   public void onMove(MoveEvent llllllllllllllllllIllIlIlIIIIIll) {
      ++llllllllllllllllllIllIlIIlllllII.timerDelay;
      llllllllllllllllllIllIlIIlllllII.timerDelay %= 5;
      if (llllllllllllllllllIllIlIIlllllII.timerDelay != 0) {
         mc.timer.timerSpeed = 1.0F;
      } else {
         if (MovementUtils.isMoving()) {
            mc.timer.timerSpeed = 32767.0F;
         }

         if (MovementUtils.isMoving()) {
            mc.timer.timerSpeed = 1.3F;
            EntityPlayerSP var10000 = mc.thePlayer;
            var10000.motionX *= 1.0199999809265137D;
            var10000 = mc.thePlayer;
            var10000.motionZ *= 1.0199999809265137D;
         }
      }

      if (mc.thePlayer.onGround && MovementUtils.isMoving()) {
         llllllllllllllllllIllIlIIlllllII.level = 2;
      }

      if (llllllllllllllllllIllIlIIlllllII.round(mc.thePlayer.posY - (double)((int)mc.thePlayer.posY)) == llllllllllllllllllIllIlIIlllllII.round(0.138D)) {
         byte llllllllllllllllllIllIlIIllllIlI = mc.thePlayer;
         llllllllllllllllllIllIlIIllllIlI.motionY -= 0.08D;
         llllllllllllllllllIllIlIlIIIIIll.setY(llllllllllllllllllIllIlIlIIIIIll.getY() - 0.09316090325960147D);
         llllllllllllllllllIllIlIIllllIlI.posY -= 0.09316090325960147D;
      }

      if (llllllllllllllllllIllIlIIlllllII.level == 1 && (mc.thePlayer.moveForward != 0.0F || mc.thePlayer.moveStrafing != 0.0F)) {
         llllllllllllllllllIllIlIIlllllII.level = 2;
         llllllllllllllllllIllIlIIlllllII.moveSpeed = 1.35D * llllllllllllllllllIllIlIIlllllII.getBaseMoveSpeed() - 0.01D;
      } else if (llllllllllllllllllIllIlIIlllllII.level == 2) {
         llllllllllllllllllIllIlIIlllllII.level = 3;
         mc.thePlayer.motionY = 0.399399995803833D;
         llllllllllllllllllIllIlIlIIIIIll.setY(0.399399995803833D);
         llllllllllllllllllIllIlIIlllllII.moveSpeed *= 2.149D;
      } else if (llllllllllllllllllIllIlIIlllllII.level == 3) {
         llllllllllllllllllIllIlIIlllllII.level = 4;
         byte llllllllllllllllllIllIlIIllllIlI = 0.66D * (llllllllllllllllllIllIlIIlllllII.lastDist - llllllllllllllllllIllIlIIlllllII.getBaseMoveSpeed());
         llllllllllllllllllIllIlIIlllllII.moveSpeed = llllllllllllllllllIllIlIIlllllII.lastDist - llllllllllllllllllIllIlIIllllIlI;
      } else {
         if (mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0D, mc.thePlayer.motionY, 0.0D)).size() > 0 || mc.thePlayer.isCollidedVertically) {
            llllllllllllllllllIllIlIIlllllII.level = 1;
         }

         llllllllllllllllllIllIlIIlllllII.moveSpeed = llllllllllllllllllIllIlIIlllllII.lastDist - llllllllllllllllllIllIlIIlllllII.lastDist / 159.0D;
      }

      llllllllllllllllllIllIlIIlllllII.moveSpeed = Math.max(llllllllllllllllllIllIlIIlllllII.moveSpeed, llllllllllllllllllIllIlIIlllllII.getBaseMoveSpeed());
      byte llllllllllllllllllIllIlIIllllIlI = mc.thePlayer.movementInput;
      long llllllllllllllllllIllIlIIllllIIl = llllllllllllllllllIllIlIIllllIlI.moveForward;
      float llllllllllllllllllIllIlIIllllIII = llllllllllllllllllIllIlIIllllIlI.moveStrafe;
      int llllllllllllllllllIllIlIIlllIlll = mc.thePlayer.rotationYaw;
      if (llllllllllllllllllIllIlIIllllIIl == 0.0F && llllllllllllllllllIllIlIIllllIII == 0.0F) {
         llllllllllllllllllIllIlIlIIIIIll.setX(0.0D);
         llllllllllllllllllIllIlIlIIIIIll.setZ(0.0D);
      } else if (llllllllllllllllllIllIlIIllllIIl != 0.0F) {
         if (llllllllllllllllllIllIlIIllllIII >= 1.0F) {
            llllllllllllllllllIllIlIIlllIlll += (float)(llllllllllllllllllIllIlIIllllIIl > 0.0F ? -45 : 45);
            llllllllllllllllllIllIlIIllllIII = 0.0F;
         } else if (llllllllllllllllllIllIlIIllllIII <= -1.0F) {
            llllllllllllllllllIllIlIIlllIlll += (float)(llllllllllllllllllIllIlIIllllIIl > 0.0F ? 45 : -45);
            llllllllllllllllllIllIlIIllllIII = 0.0F;
         }

         if (llllllllllllllllllIllIlIIllllIIl > 0.0F) {
            llllllllllllllllllIllIlIIllllIIl = 1.0F;
         } else if (llllllllllllllllllIllIlIIllllIIl < 0.0F) {
            llllllllllllllllllIllIlIIllllIIl = -1.0F;
         }
      }

      char llllllllllllllllllIllIlIIlllIllI = Math.cos(Math.toRadians((double)(llllllllllllllllllIllIlIIlllIlll + 90.0F)));
      double llllllllllllllllllIllIlIIlllllIl = Math.sin(Math.toRadians((double)(llllllllllllllllllIllIlIIlllIlll + 90.0F)));
      llllllllllllllllllIllIlIlIIIIIll.setX((double)llllllllllllllllllIllIlIIllllIIl * llllllllllllllllllIllIlIIlllllII.moveSpeed * llllllllllllllllllIllIlIIlllIllI + (double)llllllllllllllllllIllIlIIllllIII * llllllllllllllllllIllIlIIlllllII.moveSpeed * llllllllllllllllllIllIlIIlllllIl);
      llllllllllllllllllIllIlIlIIIIIll.setZ((double)llllllllllllllllllIllIlIIllllIIl * llllllllllllllllllIllIlIIlllllII.moveSpeed * llllllllllllllllllIllIlIIlllllIl - (double)llllllllllllllllllIllIlIIllllIII * llllllllllllllllllIllIlIIlllllII.moveSpeed * llllllllllllllllllIllIlIIlllIllI);
      mc.thePlayer.stepHeight = 0.6F;
      if (llllllllllllllllllIllIlIIllllIIl == 0.0F && llllllllllllllllllIllIlIIllllIII == 0.0F) {
         llllllllllllllllllIllIlIlIIIIIll.setX(0.0D);
         llllllllllllllllllIllIlIlIIIIIll.setZ(0.0D);
      }

   }

   private double getBaseMoveSpeed() {
      double llllllllllllllllllIllIlIIlllIIlI = 0.2873D;
      if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
         llllllllllllllllllIllIlIIlllIIlI *= 1.0D + 0.2D * (double)(mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
      }

      return llllllllllllllllllIllIlIIlllIIlI;
   }

   public void onUpdate() {
   }
}
