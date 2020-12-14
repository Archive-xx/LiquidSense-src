//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp;

import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;

public class YPort2 extends SpeedMode {
   public void onMove(MoveEvent lIIIIlllIIlllII) {
   }

   public void onUpdate() {
   }

   public YPort2() {
      super("YPort2");
   }

   public void onMotion() {
      if (!mc.thePlayer.isOnLadder() && !mc.thePlayer.isInWater() && !mc.thePlayer.isInLava() && !mc.thePlayer.isInWeb && MovementUtils.isMoving()) {
         if (mc.thePlayer.onGround) {
            mc.thePlayer.jump();
         } else {
            mc.thePlayer.motionY = -1.0D;
         }

         MovementUtils.strafe();
      }
   }
}
