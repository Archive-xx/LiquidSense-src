//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.other;

import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;

public class HiveHop extends SpeedMode {
   public HiveHop() {
      super("HiveHop");
   }

   public void onMove(MoveEvent lIlIlIlIII) {
   }

   public void onUpdate() {
      if (MovementUtils.isMoving()) {
         if (mc.thePlayer.onGround) {
            mc.thePlayer.motionY = 0.3D;
         }

         mc.thePlayer.speedInAir = 0.0425F;
         mc.timer.timerSpeed = 1.04F;
         MovementUtils.strafe();
      } else {
         mc.thePlayer.motionX = mc.thePlayer.motionZ = 0.0D;
         mc.thePlayer.speedInAir = 0.02F;
         mc.timer.timerSpeed = 1.0F;
      }

   }

   public void onEnable() {
      mc.thePlayer.speedInAir = 0.0425F;
      mc.timer.timerSpeed = 1.04F;
   }

   public void onDisable() {
      mc.thePlayer.speedInAir = 0.02F;
      mc.timer.timerSpeed = 1.0F;
   }

   public void onMotion() {
   }
}
