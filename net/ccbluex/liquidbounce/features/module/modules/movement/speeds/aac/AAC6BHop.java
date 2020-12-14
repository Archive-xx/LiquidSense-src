//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac;

import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;

public class AAC6BHop extends SpeedMode {
   // $FF: synthetic field
   private boolean legitJump;

   public AAC6BHop() {
      super("AAC6BHop");
   }

   public void onUpdate() {
      mc.timer.timerSpeed = 1.0F;
      if (!mc.thePlayer.isInWater()) {
         if (MovementUtils.isMoving()) {
            if (mc.thePlayer.onGround) {
               if (llIlllIIIllIllI.legitJump) {
                  mc.thePlayer.motionY = 0.4D;
                  MovementUtils.strafe(0.15F);
                  mc.thePlayer.onGround = false;
                  llIlllIIIllIllI.legitJump = false;
                  return;
               }

               mc.thePlayer.motionY = 0.41D;
               MovementUtils.strafe(0.47458485F);
            }

            if (mc.thePlayer.motionY < 0.0D && mc.thePlayer.motionY > -0.2D) {
               mc.timer.timerSpeed = (float)(1.2D + mc.thePlayer.motionY);
            }

            mc.thePlayer.speedInAir = 0.022151F;
         } else {
            llIlllIIIllIllI.legitJump = true;
            mc.thePlayer.motionX = 0.0D;
            mc.thePlayer.motionZ = 0.0D;
         }

      }
   }

   public void onDisable() {
      mc.timer.timerSpeed = 1.0F;
      mc.thePlayer.speedInAir = 0.02F;
   }

   public void onMove(MoveEvent llIlllIIIllIlII) {
   }

   public void onEnable() {
      llIlllIIIllIIlI.legitJump = true;
   }

   public void onMotion() {
   }
}
