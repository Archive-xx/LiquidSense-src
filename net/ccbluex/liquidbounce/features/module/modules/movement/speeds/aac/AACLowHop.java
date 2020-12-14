//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac;

import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;

public class AACLowHop extends SpeedMode {
   // $FF: synthetic field
   private boolean legitJump;

   public void onMove(MoveEvent llllllllllllllllllIIIIlIllIIlllI) {
   }

   public void onMotion() {
      if (MovementUtils.isMoving()) {
         if (mc.thePlayer.onGround) {
            if (llllllllllllllllllIIIIlIllIlIIIl.legitJump) {
               mc.thePlayer.jump();
               llllllllllllllllllIIIIlIllIlIIIl.legitJump = false;
               return;
            }

            mc.thePlayer.motionY = 0.34299999475479126D;
            MovementUtils.strafe(0.534F);
         }
      } else {
         llllllllllllllllllIIIIlIllIlIIIl.legitJump = true;
         mc.thePlayer.motionX = 0.0D;
         mc.thePlayer.motionZ = 0.0D;
      }

   }

   public void onEnable() {
      llllllllllllllllllIIIIlIllIlIlII.legitJump = true;
      super.onEnable();
   }

   public AACLowHop() {
      super("AACLowHop");
   }

   public void onUpdate() {
   }
}
