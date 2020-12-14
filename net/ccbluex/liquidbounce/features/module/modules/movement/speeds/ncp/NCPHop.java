//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp;

import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;

public class NCPHop extends SpeedMode {
   public void onMotion() {
   }

   public void onUpdate() {
      if (MovementUtils.isMoving()) {
         if (mc.thePlayer.onGround) {
            mc.thePlayer.jump();
            mc.thePlayer.speedInAir = 0.0223F;
         }

         MovementUtils.strafe();
      } else {
         mc.thePlayer.motionX = 0.0D;
         mc.thePlayer.motionZ = 0.0D;
      }

   }

   public void onMove(MoveEvent llllllllllllllllllIllllIIllIIIlI) {
   }

   public void onEnable() {
      mc.timer.timerSpeed = 1.0865F;
      super.onEnable();
   }

   public void onDisable() {
      mc.thePlayer.speedInAir = 0.02F;
      mc.timer.timerSpeed = 1.0F;
      super.onDisable();
   }

   public NCPHop() {
      super("NCPHop");
   }
}
