//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac;

import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;

public class AAC4BHop extends SpeedMode {
   // $FF: synthetic field
   private boolean legitHop;

   public void onMove(MoveEvent llllIlIlIllllll) {
   }

   public AAC4BHop() {
      super("AAC4BHop");
   }

   public void onEnable() {
      llllIlIlIllIllI.legitHop = true;
   }

   public void onMotion() {
   }

   public void onDisable() {
      mc.thePlayer.speedInAir = 0.02F;
   }

   public void onUpdate() {
   }

   public void onTick() {
      if (MovementUtils.isMoving()) {
         if (llllIlIlIlIlIll.legitHop) {
            if (mc.thePlayer.onGround) {
               mc.thePlayer.jump();
               mc.thePlayer.onGround = false;
               llllIlIlIlIlIll.legitHop = false;
            }

            return;
         }

         if (mc.thePlayer.onGround) {
            mc.thePlayer.onGround = false;
            MovementUtils.strafe(0.375F);
            mc.thePlayer.jump();
            mc.thePlayer.motionY = 0.41D;
         } else {
            mc.thePlayer.speedInAir = 0.0211F;
         }
      } else {
         mc.thePlayer.motionX = 0.0D;
         mc.thePlayer.motionZ = 0.0D;
         llllIlIlIlIlIll.legitHop = true;
      }

   }
}
