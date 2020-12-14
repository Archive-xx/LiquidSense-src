//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class YPort extends SpeedMode {
   // $FF: synthetic field
   private int level = 1;
   // $FF: synthetic field
   private double moveSpeed = 0.2873D;
   // $FF: synthetic field
   private double lastDist;
   // $FF: synthetic field
   private boolean safeJump;
   // $FF: synthetic field
   private int timerDelay;

   public void onMotion() {
      if (!lIlIIIlIIIlI.safeJump && !mc.gameSettings.keyBindJump.isKeyDown() && !mc.thePlayer.isOnLadder() && !mc.thePlayer.isInsideOfMaterial(Material.water) && !mc.thePlayer.isInsideOfMaterial(Material.lava) && !mc.thePlayer.isInWater() && (!(lIlIIIlIIIlI.getBlock(-1.1D) instanceof BlockAir) && !(lIlIIIlIIIlI.getBlock(-1.1D) instanceof BlockAir) || !(lIlIIIlIIIlI.getBlock(-0.1D) instanceof BlockAir) && mc.thePlayer.motionX != 0.0D && mc.thePlayer.motionZ != 0.0D && !mc.thePlayer.onGround && mc.thePlayer.fallDistance < 3.0F && (double)mc.thePlayer.fallDistance > 0.05D) && lIlIIIlIIIlI.level == 3) {
         mc.thePlayer.motionY = -0.3994D;
      }

      double lIlIIIlIIllI = mc.thePlayer.posX - mc.thePlayer.prevPosX;
      double lIlIIIlIIlII = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;
      lIlIIIlIIIlI.lastDist = Math.sqrt(lIlIIIlIIllI * lIlIIIlIIllI + lIlIIIlIIlII * lIlIIIlIIlII);
      if (!MovementUtils.isMoving()) {
         lIlIIIlIIIlI.safeJump = true;
      } else if (mc.thePlayer.onGround) {
         lIlIIIlIIIlI.safeJump = false;
      }

   }

   private Block getBlock(AxisAlignedBB lIIllllIIIlI) {
      for(int lIIllllIIlIl = MathHelper.floor_double(lIIllllIIIlI.minX); lIIllllIIlIl < MathHelper.floor_double(lIIllllIIIlI.maxX) + 1; ++lIIllllIIlIl) {
         for(int lIIllllIIIII = MathHelper.floor_double(lIIllllIIIlI.minZ); lIIllllIIIII < MathHelper.floor_double(lIIllllIIIlI.maxZ) + 1; ++lIIllllIIIII) {
            Block lIIllllIIlll = mc.theWorld.getBlockState(new BlockPos(lIIllllIIlIl, (int)lIIllllIIIlI.minY, lIIllllIIIII)).getBlock();
            if (lIIllllIIlll != null) {
               return lIIllllIIlll;
            }
         }
      }

      return null;
   }

   public void onMove(MoveEvent lIIlllllllll) {
      ++lIlIIIIIIIII.timerDelay;
      lIlIIIIIIIII.timerDelay %= 5;
      EntityPlayerSP var10000;
      if (lIlIIIIIIIII.timerDelay != 0) {
         mc.timer.timerSpeed = 1.0F;
      } else {
         if (MovementUtils.hasMotion()) {
            mc.timer.timerSpeed = 32767.0F;
         }

         if (MovementUtils.hasMotion()) {
            mc.timer.timerSpeed = 1.3F;
            var10000 = mc.thePlayer;
            var10000.motionX *= 1.0199999809265137D;
            var10000 = mc.thePlayer;
            var10000.motionZ *= 1.0199999809265137D;
         }
      }

      if (mc.thePlayer.onGround && MovementUtils.hasMotion()) {
         lIlIIIIIIIII.level = 2;
      }

      if (lIlIIIIIIIII.round(mc.thePlayer.posY - (double)((int)mc.thePlayer.posY)) == lIlIIIIIIIII.round(0.138D)) {
         var10000 = mc.thePlayer;
         var10000.motionY -= 0.08D;
         lIIlllllllll.setY(lIIlllllllll.getY() - 0.09316090325960147D);
         var10000 = mc.thePlayer;
         var10000.posY -= 0.09316090325960147D;
      }

      if (lIlIIIIIIIII.level == 1 && (mc.thePlayer.moveForward != 0.0F || mc.thePlayer.moveStrafing != 0.0F)) {
         lIlIIIIIIIII.level = 2;
         lIlIIIIIIIII.moveSpeed = 1.38D * lIlIIIIIIIII.getBaseMoveSpeed() - 0.01D;
      } else if (lIlIIIIIIIII.level == 2) {
         lIlIIIIIIIII.level = 3;
         mc.thePlayer.motionY = 0.399399995803833D;
         lIIlllllllll.setY(0.399399995803833D);
         lIlIIIIIIIII.moveSpeed *= 2.149D;
      } else if (lIlIIIIIIIII.level == 3) {
         lIlIIIIIIIII.level = 4;
         Exception lIIlllllIlll = 0.66D * (lIlIIIIIIIII.lastDist - lIlIIIIIIIII.getBaseMoveSpeed());
         lIlIIIIIIIII.moveSpeed = lIlIIIIIIIII.lastDist - lIIlllllIlll;
      } else {
         if (mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0D, mc.thePlayer.motionY, 0.0D)).size() > 0 || mc.thePlayer.isCollidedVertically) {
            lIlIIIIIIIII.level = 1;
         }

         lIlIIIIIIIII.moveSpeed = lIlIIIIIIIII.lastDist - lIlIIIIIIIII.lastDist / 159.0D;
      }

      lIlIIIIIIIII.moveSpeed = Math.max(lIlIIIIIIIII.moveSpeed, lIlIIIIIIIII.getBaseMoveSpeed());
      float lIIllllllllI = mc.thePlayer.movementInput.moveForward;
      float lIIlllllllIl = mc.thePlayer.movementInput.moveStrafe;
      float lIIlllllllII = mc.thePlayer.rotationYaw;
      if (lIIllllllllI == 0.0F && lIIlllllllIl == 0.0F) {
         lIIlllllllll.setX(0.0D);
         lIIlllllllll.setZ(0.0D);
      } else if (lIIllllllllI != 0.0F) {
         if (lIIlllllllIl >= 1.0F) {
            lIIlllllllII += (float)(lIIllllllllI > 0.0F ? -45 : 45);
            lIIlllllllIl = 0.0F;
         } else if (lIIlllllllIl <= -1.0F) {
            lIIlllllllII += (float)(lIIllllllllI > 0.0F ? 45 : -45);
            lIIlllllllIl = 0.0F;
         }

         if (lIIllllllllI > 0.0F) {
            lIIllllllllI = 1.0F;
         } else if (lIIllllllllI < 0.0F) {
            lIIllllllllI = -1.0F;
         }
      }

      double lIIllllllIll = Math.cos(Math.toRadians((double)(lIIlllllllII + 90.0F)));
      double lIIllllllIlI = Math.sin(Math.toRadians((double)(lIIlllllllII + 90.0F)));
      lIIlllllllll.setX((double)lIIllllllllI * lIlIIIIIIIII.moveSpeed * lIIllllllIll + (double)lIIlllllllIl * lIlIIIIIIIII.moveSpeed * lIIllllllIlI);
      lIIlllllllll.setZ((double)lIIllllllllI * lIlIIIIIIIII.moveSpeed * lIIllllllIlI - (double)lIIlllllllIl * lIlIIIIIIIII.moveSpeed * lIIllllllIll);
      mc.thePlayer.stepHeight = 0.6F;
      if (lIIllllllllI == 0.0F && lIIlllllllIl == 0.0F) {
         lIIlllllllll.setX(0.0D);
         lIIlllllllll.setZ(0.0D);
      }

   }

   private Block getBlock(double lIIlllIllIll) {
      return lIIlllIlllII.getBlock(mc.thePlayer.getEntityBoundingBox().offset(0.0D, lIIlllIllIll, 0.0D));
   }

   private double round(double lIIlllIlIIll) {
      BigDecimal lIIlllIlIlII = new BigDecimal(lIIlllIlIIll);
      lIIlllIlIlII = lIIlllIlIlII.setScale(3, RoundingMode.HALF_UP);
      return lIIlllIlIlII.doubleValue();
   }

   public void onUpdate() {
   }

   private double getBaseMoveSpeed() {
      double lIIllllIlllI = 0.2873D;
      if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
         int lIIlllllIIII = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
         lIIllllIlllI *= 1.0D + 0.2D * (double)(lIIlllllIIII + 1);
      }

      return lIIllllIlllI;
   }

   public YPort() {
      super("YPort");
   }
}
