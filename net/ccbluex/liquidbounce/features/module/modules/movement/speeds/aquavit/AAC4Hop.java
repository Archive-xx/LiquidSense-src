//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aquavit;

import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;

public class AAC4Hop extends SpeedMode {
   public void onMove(MoveEvent lIlIIlIIIlII) {
   }

   public AAC4Hop() {
      super("AAC4Hop");
   }

   public void onMotion() {
   }

   public void onUpdate() {
      if (!mc.thePlayer.isInWater()) {
         if (MovementUtils.isMoving()) {
            if (mc.thePlayer.onGround) {
               mc.thePlayer.jump();
               mc.thePlayer.speedInAir = 0.0201F;
               mc.timer.timerSpeed = 0.94F;
            }

            if ((double)mc.thePlayer.fallDistance > 0.7D && (double)mc.thePlayer.fallDistance < 1.3D) {
               mc.thePlayer.speedInAir = 0.02F;
               mc.timer.timerSpeed = 1.8F;
            }
         } else {
            mc.thePlayer.motionX = 0.0D;
            mc.thePlayer.motionZ = 0.0D;
         }

      }
   }

   public void onDisable() {
      mc.timer.timerSpeed = 1.0F;
      mc.thePlayer.speedInAir = 0.02F;
   }
}
