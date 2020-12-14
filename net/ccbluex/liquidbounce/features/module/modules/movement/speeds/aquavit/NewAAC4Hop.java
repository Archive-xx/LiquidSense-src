//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aquavit;

import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;

public class NewAAC4Hop extends SpeedMode {
   public void onUpdate() {
      if (!mc.thePlayer.isInWater()) {
         if (MovementUtils.isMoving()) {
            if (mc.thePlayer.onGround) {
               mc.gameSettings.keyBindJump.pressed = false;
               mc.thePlayer.jump();
            }

            if (!mc.thePlayer.onGround && (double)mc.thePlayer.fallDistance <= 0.1D) {
               mc.thePlayer.speedInAir = 0.02F;
               mc.timer.timerSpeed = 1.4F;
            }

            if ((double)mc.thePlayer.fallDistance > 0.1D && (double)mc.thePlayer.fallDistance < 1.3D) {
               mc.thePlayer.speedInAir = 0.0205F;
               mc.timer.timerSpeed = 0.65F;
            }

            if ((double)mc.thePlayer.fallDistance >= 1.3D) {
               mc.timer.timerSpeed = 1.0F;
               mc.thePlayer.speedInAir = 0.02F;
            }
         } else {
            mc.thePlayer.motionX = 0.0D;
            mc.thePlayer.motionZ = 0.0D;
         }

      }
   }

   public NewAAC4Hop() {
      super("NewAAC4Hop");
   }

   public void onMove(MoveEvent lllllllllllllllllllIIllIIlIIlIII) {
   }

   public void onMotion() {
   }

   public void onDisable() {
      mc.timer.timerSpeed = 1.0F;
      mc.thePlayer.speedInAir = 0.02F;
   }
}
