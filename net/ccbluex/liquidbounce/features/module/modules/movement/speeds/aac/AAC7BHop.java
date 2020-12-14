//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac;

import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.client.entity.EntityPlayerSP;

public class AAC7BHop extends SpeedMode {
   public void onMove(MoveEvent llllllllllllllllllIIIlIllllIIlll) {
   }

   public void onUpdate() {
      if (MovementUtils.isMoving() && mc.thePlayer.ridingEntity == null && mc.thePlayer.hurtTime <= 0) {
         if (mc.thePlayer.onGround) {
            mc.thePlayer.jump();
            mc.thePlayer.motionY = 0.405D;
            EntityPlayerSP var10000 = mc.thePlayer;
            var10000.motionX *= 1.004D;
            var10000 = mc.thePlayer;
            var10000.motionZ *= 1.004D;
         } else {
            byte llllllllllllllllllIIIlIllllIlIlI = (double)MovementUtils.getSpeed() * 1.0072D;
            Exception llllllllllllllllllIIIlIllllIlIIl = Math.toRadians((double)mc.thePlayer.rotationYaw);
            mc.thePlayer.motionX = -Math.sin(llllllllllllllllllIIIlIllllIlIIl) * llllllllllllllllllIIIlIllllIlIlI;
            mc.thePlayer.motionZ = Math.cos(llllllllllllllllllIIIlIllllIlIIl) * llllllllllllllllllIIIlIllllIlIlI;
         }
      }
   }

   public void onMotion() {
   }

   public AAC7BHop() {
      super("AAC7BHop");
   }
}
