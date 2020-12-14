//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp;

import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.client.entity.EntityPlayerSP;

public class MiJump extends SpeedMode {
   public MiJump() {
      super("MiJump");
   }

   public void onUpdate() {
   }

   public void onMotion() {
      if (MovementUtils.isMoving()) {
         if (mc.thePlayer.onGround && !mc.thePlayer.movementInput.jump) {
            EntityPlayerSP var10000 = mc.thePlayer;
            var10000.motionY += 0.1D;
            String llllllIIlllllIl = 1.8D;
            var10000 = mc.thePlayer;
            var10000.motionX *= 1.8D;
            var10000 = mc.thePlayer;
            var10000.motionZ *= 1.8D;
            String llllllIIlllllII = Math.sqrt(Math.pow(mc.thePlayer.motionX, 2.0D) + Math.pow(mc.thePlayer.motionZ, 2.0D));
            double llllllIIlllllll = 0.66D;
            if (llllllIIlllllII > 0.66D) {
               mc.thePlayer.motionX = mc.thePlayer.motionX / llllllIIlllllII * 0.66D;
               mc.thePlayer.motionZ = mc.thePlayer.motionZ / llllllIIlllllII * 0.66D;
            }
         }

         MovementUtils.strafe();
      }
   }

   public void onMove(MoveEvent llllllIIllllIII) {
   }
}
