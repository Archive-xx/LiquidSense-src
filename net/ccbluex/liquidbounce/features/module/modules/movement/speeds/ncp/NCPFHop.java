//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp;

import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.client.entity.EntityPlayerSP;

public class NCPFHop extends SpeedMode {
   public void onUpdate() {
      if (MovementUtils.isMoving()) {
         EntityPlayerSP var10000;
         if (mc.thePlayer.onGround) {
            mc.thePlayer.jump();
            var10000 = mc.thePlayer;
            var10000.motionX *= 1.01D;
            var10000 = mc.thePlayer;
            var10000.motionZ *= 1.01D;
            mc.thePlayer.speedInAir = 0.0223F;
         }

         var10000 = mc.thePlayer;
         var10000.motionY -= 9.9999E-4D;
         MovementUtils.strafe();
      } else {
         mc.thePlayer.motionX = 0.0D;
         mc.thePlayer.motionZ = 0.0D;
      }

   }

   public void onDisable() {
      mc.thePlayer.speedInAir = 0.02F;
      mc.timer.timerSpeed = 1.0F;
      super.onDisable();
   }

   public void onEnable() {
      mc.timer.timerSpeed = 1.0866F;
      super.onEnable();
   }

   public void onMotion() {
   }

   public void onMove(MoveEvent lllllllllllllllllllllIlIllllIlIl) {
   }

   public NCPFHop() {
      super("NCPFHop");
   }
}
