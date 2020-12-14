//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac;

import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.client.entity.EntityPlayerSP;

public class AAC2BHop extends SpeedMode {
   public void onUpdate() {
   }

   public AAC2BHop() {
      super("AAC2BHop");
   }

   public void onMove(MoveEvent lIIIlllIllIIIl) {
   }

   public void onMotion() {
      if (!mc.thePlayer.isInWater()) {
         if (MovementUtils.isMoving()) {
            EntityPlayerSP var10000;
            if (mc.thePlayer.onGround) {
               mc.thePlayer.jump();
               var10000 = mc.thePlayer;
               var10000.motionX *= 1.02D;
               var10000 = mc.thePlayer;
               var10000.motionZ *= 1.02D;
            } else if (mc.thePlayer.motionY > -0.2D) {
               mc.thePlayer.jumpMovementFactor = 0.08F;
               var10000 = mc.thePlayer;
               var10000.motionY += 0.01431D;
               mc.thePlayer.jumpMovementFactor = 0.07F;
            }
         } else {
            mc.thePlayer.motionX = 0.0D;
            mc.thePlayer.motionZ = 0.0D;
         }

      }
   }
}
