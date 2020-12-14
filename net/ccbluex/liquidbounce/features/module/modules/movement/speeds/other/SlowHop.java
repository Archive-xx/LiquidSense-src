//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.other;

import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;

public class SlowHop extends SpeedMode {
   public void onMotion() {
      if (!mc.thePlayer.isInWater()) {
         if (MovementUtils.isMoving()) {
            if (mc.thePlayer.onGround) {
               mc.thePlayer.jump();
            } else {
               MovementUtils.strafe(MovementUtils.getSpeed() * 1.011F);
            }
         } else {
            mc.thePlayer.motionX = 0.0D;
            mc.thePlayer.motionZ = 0.0D;
         }

      }
   }

   public void onUpdate() {
   }

   public SlowHop() {
      super("SlowHop");
   }

   public void onMove(MoveEvent llIlIIIIIllIlll) {
   }
}
