//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.spartan;

import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;

public class SpartanYPort extends SpeedMode {
   // $FF: synthetic field
   private int airMoves;

   public void onMotion() {
      if (mc.gameSettings.keyBindForward.isKeyDown() && !mc.gameSettings.keyBindJump.isKeyDown()) {
         if (mc.thePlayer.onGround) {
            mc.thePlayer.jump();
            lIlIIllIlIllllI.airMoves = 0;
         } else {
            mc.timer.timerSpeed = 1.08F;
            if (lIlIIllIlIllllI.airMoves >= 3) {
               mc.thePlayer.jumpMovementFactor = 0.0275F;
            }

            if (lIlIIllIlIllllI.airMoves >= 4 && (double)(lIlIIllIlIllllI.airMoves % 2) == 0.0D) {
               mc.thePlayer.motionY = -0.3199999928474426D - 0.009D * Math.random();
               mc.thePlayer.jumpMovementFactor = 0.0238F;
            }

            ++lIlIIllIlIllllI.airMoves;
         }
      }

   }

   public void onMove(MoveEvent lIlIIllIlIllIll) {
   }

   public SpartanYPort() {
      super("SpartanYPort");
   }

   public void onUpdate() {
   }
}
