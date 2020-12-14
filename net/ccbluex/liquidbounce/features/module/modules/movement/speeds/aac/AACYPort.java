//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac;

import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.client.entity.EntityPlayerSP;

public class AACYPort extends SpeedMode {
   public void onUpdate() {
   }

   public void onMove(MoveEvent lllIlIlIIllIlII) {
   }

   public void onMotion() {
      if (MovementUtils.isMoving() && !mc.thePlayer.isSneaking()) {
         mc.thePlayer.cameraPitch = 0.0F;
         if (mc.thePlayer.onGround) {
            mc.thePlayer.motionY = 0.3425000011920929D;
            EntityPlayerSP var10000 = mc.thePlayer;
            var10000.motionX *= 1.5893000364303589D;
            var10000 = mc.thePlayer;
            var10000.motionZ *= 1.5893000364303589D;
         } else {
            mc.thePlayer.motionY = -0.19D;
         }
      }

   }

   public AACYPort() {
      super("AACYPort");
   }
}
