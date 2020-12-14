//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac;

import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.MathHelper;

public class AACBHop extends SpeedMode {
   public AACBHop() {
      super("AACBHop");
   }

   public void onUpdate() {
   }

   public void onMove(MoveEvent lIlIIIlllIlII) {
   }

   public void onDisable() {
      mc.timer.timerSpeed = 1.0F;
   }

   public void onMotion() {
      if (!mc.thePlayer.isInWater()) {
         if (MovementUtils.isMoving()) {
            mc.timer.timerSpeed = 1.08F;
            EntityPlayerSP var10000;
            if (mc.thePlayer.onGround) {
               mc.thePlayer.motionY = 0.399D;
               long lIlIIIlllllII = mc.thePlayer.rotationYaw * 0.017453292F;
               var10000 = mc.thePlayer;
               var10000.motionX -= (double)(MathHelper.sin(lIlIIIlllllII) * 0.2F);
               var10000 = mc.thePlayer;
               var10000.motionZ += (double)(MathHelper.cos(lIlIIIlllllII) * 0.2F);
               mc.timer.timerSpeed = 2.0F;
            } else {
               var10000 = mc.thePlayer;
               var10000.motionY *= 0.97D;
               var10000 = mc.thePlayer;
               var10000.motionX *= 1.008D;
               var10000 = mc.thePlayer;
               var10000.motionZ *= 1.008D;
            }
         } else {
            mc.thePlayer.motionX = 0.0D;
            mc.thePlayer.motionZ = 0.0D;
            mc.timer.timerSpeed = 1.0F;
         }

      }
   }
}
