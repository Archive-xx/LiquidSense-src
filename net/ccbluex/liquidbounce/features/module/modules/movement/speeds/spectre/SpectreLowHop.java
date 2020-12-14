//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.spectre;

import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;

public class SpectreLowHop extends SpeedMode {
   public void onUpdate() {
   }

   public SpectreLowHop() {
      super("SpectreLowHop");
   }

   public void onMove(MoveEvent llllllllllllllllllIlIIlIlIIIlIII) {
   }

   public void onMotion() {
      if (MovementUtils.isMoving() && !mc.thePlayer.movementInput.jump) {
         if (mc.thePlayer.onGround) {
            MovementUtils.strafe(1.1F);
            mc.thePlayer.motionY = 0.15D;
         } else {
            MovementUtils.strafe();
         }
      }
   }
}
