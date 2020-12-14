//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.other;

import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.potion.Potion;

public class HypixelHop extends SpeedMode {
   public HypixelHop() {
      super("HypixelHop");
   }

   public void onMotion() {
      if (MovementUtils.isMoving()) {
         if (mc.thePlayer.onGround) {
            mc.thePlayer.jump();
            float lIIlllllIlIllI = MovementUtils.getSpeed() < 0.56F ? MovementUtils.getSpeed() * 1.045F : 0.56F;
            if (mc.thePlayer.onGround && mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
               lIIlllllIlIllI *= 1.0F + 0.13F * (float)(1 + mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier());
            }

            MovementUtils.strafe(lIIlllllIlIllI);
            return;
         }

         if (mc.thePlayer.motionY < 0.2D) {
            EntityPlayerSP var10000 = mc.thePlayer;
            var10000.motionY -= 0.02D;
         }

         MovementUtils.strafe(MovementUtils.getSpeed() * 1.01889F);
      } else {
         mc.thePlayer.motionX = mc.thePlayer.motionZ = 0.0D;
      }

   }

   public void onUpdate() {
   }

   public void onMove(MoveEvent lIIlllllIlIIIl) {
   }
}
