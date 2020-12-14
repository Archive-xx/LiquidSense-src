//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp;

import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.client.entity.EntityPlayerSP;

public class OnGround extends SpeedMode {
   public void onUpdate() {
   }

   public void onMotion() {
      if (MovementUtils.isMoving()) {
         if (!((double)mc.thePlayer.fallDistance > 3.994D)) {
            if (!mc.thePlayer.isInWater() && !mc.thePlayer.isOnLadder() && !mc.thePlayer.isCollidedHorizontally) {
               EntityPlayerSP var10000 = mc.thePlayer;
               var10000.posY -= 0.3993000090122223D;
               mc.thePlayer.motionY = -1000.0D;
               mc.thePlayer.cameraPitch = 0.3F;
               mc.thePlayer.distanceWalkedModified = 44.0F;
               mc.timer.timerSpeed = 1.0F;
               if (mc.thePlayer.onGround) {
                  var10000 = mc.thePlayer;
                  var10000.posY += 0.3993000090122223D;
                  mc.thePlayer.motionY = 0.3993000090122223D;
                  mc.thePlayer.distanceWalkedOnStepModified = 44.0F;
                  var10000 = mc.thePlayer;
                  var10000.motionX *= 1.590000033378601D;
                  var10000 = mc.thePlayer;
                  var10000.motionZ *= 1.590000033378601D;
                  mc.thePlayer.cameraPitch = 0.0F;
                  mc.timer.timerSpeed = 1.199F;
               }

            }
         }
      }
   }

   public OnGround() {
      super("OnGround");
   }

   public void onMove(MoveEvent llIIIIIlIlIlIll) {
   }
}
