//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac;

import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.client.entity.EntityPlayerSP;

public class AACYPort2 extends SpeedMode {
   public void onMove(MoveEvent llllIlIIIIIllI) {
   }

   public AACYPort2() {
      super("AACYPort2");
   }

   public void onMotion() {
      if (MovementUtils.isMoving()) {
         mc.thePlayer.cameraPitch = 0.0F;
         if (mc.thePlayer.onGround) {
            mc.thePlayer.jump();
            mc.thePlayer.motionY = 0.38510000705718994D;
            EntityPlayerSP var10000 = mc.thePlayer;
            var10000.motionX *= 1.01D;
            var10000 = mc.thePlayer;
            var10000.motionZ *= 1.01D;
         } else {
            mc.thePlayer.motionY = -0.21D;
         }
      }

   }

   public void onUpdate() {
   }
}
