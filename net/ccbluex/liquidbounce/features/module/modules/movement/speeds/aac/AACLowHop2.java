//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac;

import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;

public class AACLowHop2 extends SpeedMode {
   // $FF: synthetic field
   private boolean legitJump;

   public void onMotion() {
      mc.timer.timerSpeed = 1.0F;
      if (!mc.thePlayer.isInWater()) {
         if (MovementUtils.isMoving()) {
            mc.timer.timerSpeed = 1.09F;
            if (mc.thePlayer.onGround) {
               if (llllllllllllllllllllllllIlIIIllI.legitJump) {
                  mc.thePlayer.jump();
                  llllllllllllllllllllllllIlIIIllI.legitJump = false;
                  return;
               }

               mc.thePlayer.motionY = 0.34299999475479126D;
               MovementUtils.strafe(0.534F);
            }
         } else {
            llllllllllllllllllllllllIlIIIllI.legitJump = true;
            mc.thePlayer.motionX = 0.0D;
            mc.thePlayer.motionZ = 0.0D;
         }

      }
   }

   public void onMove(MoveEvent llllllllllllllllllllllllIlIIIIlI) {
   }

   public void onDisable() {
      mc.timer.timerSpeed = 1.0F;
   }

   public void onEnable() {
      llllllllllllllllllllllllIlIIlIIl.legitJump = true;
      mc.timer.timerSpeed = 1.0F;
   }

   public void onUpdate() {
   }

   public AACLowHop2() {
      super("AACLowHop2");
   }
}
