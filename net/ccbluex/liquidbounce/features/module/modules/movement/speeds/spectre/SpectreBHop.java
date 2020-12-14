//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.spectre;

import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;

public class SpectreBHop extends SpeedMode {
   public SpectreBHop() {
      super("SpectreBHop");
   }

   public void onUpdate() {
   }

   public void onMotion() {
      if (MovementUtils.isMoving() && !mc.thePlayer.movementInput.jump) {
         if (mc.thePlayer.onGround) {
            MovementUtils.strafe(1.1F);
            mc.thePlayer.motionY = 0.44D;
         } else {
            MovementUtils.strafe();
         }
      }
   }

   public void onMove(MoveEvent llllllllllllllllllIIllllIIlIlIlI) {
   }
}
