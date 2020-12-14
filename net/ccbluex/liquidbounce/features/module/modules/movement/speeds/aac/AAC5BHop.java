//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac;

import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;

public class AAC5BHop extends SpeedMode {
   // $FF: synthetic field
   private boolean legitJump;

   public void onUpdate() {
   }

   public void onMove(MoveEvent lllllllllllllllllllIllIIlIlIIIlI) {
   }

   public AAC5BHop() {
      super("AAC5BHop");
   }

   public void onMotion() {
   }

   public void onTick() {
      mc.timer.timerSpeed = 1.0F;
      if (!mc.thePlayer.isInWater()) {
         if (MovementUtils.isMoving()) {
            if (mc.thePlayer.onGround) {
               if (lllllllllllllllllllIllIIlIIlIIll.legitJump) {
                  mc.thePlayer.jump();
                  lllllllllllllllllllIllIIlIIlIIll.legitJump = false;
                  return;
               }

               mc.thePlayer.motionY = 0.41D;
               mc.thePlayer.onGround = false;
               MovementUtils.strafe(0.374F);
            } else if (mc.thePlayer.motionY < 0.0D) {
               mc.thePlayer.speedInAir = 0.0201F;
               mc.timer.timerSpeed = 1.02F;
            } else {
               mc.timer.timerSpeed = 1.01F;
            }
         } else {
            lllllllllllllllllllIllIIlIIlIIll.legitJump = true;
            mc.thePlayer.motionX = 0.0D;
            mc.thePlayer.motionZ = 0.0D;
         }

      }
   }
}
