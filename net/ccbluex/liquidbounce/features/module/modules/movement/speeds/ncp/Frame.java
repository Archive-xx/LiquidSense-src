//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp;

import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.timer.TickTimer;
import net.minecraft.client.entity.EntityPlayerSP;

public class Frame extends SpeedMode {
   // $FF: synthetic field
   private boolean move;
   // $FF: synthetic field
   private final TickTimer tickTimer = new TickTimer();
   // $FF: synthetic field
   private int motionTicks;

   public void onMove(MoveEvent lIIIIIlIllllII) {
   }

   public void onUpdate() {
   }

   public void onMotion() {
      if (mc.thePlayer.movementInput.moveForward > 0.0F || mc.thePlayer.movementInput.moveStrafe > 0.0F) {
         double lIIIIIllIIIIlI = 4.25D;
         if (mc.thePlayer.onGround) {
            mc.thePlayer.jump();
            if (lIIIIIllIIIIIl.motionTicks == 1) {
               lIIIIIllIIIIIl.tickTimer.reset();
               if (lIIIIIllIIIIIl.move) {
                  mc.thePlayer.motionX = 0.0D;
                  mc.thePlayer.motionZ = 0.0D;
                  lIIIIIllIIIIIl.move = false;
               }

               lIIIIIllIIIIIl.motionTicks = 0;
            } else {
               lIIIIIllIIIIIl.motionTicks = 1;
            }
         } else if (!lIIIIIllIIIIIl.move && lIIIIIllIIIIIl.motionTicks == 1 && lIIIIIllIIIIIl.tickTimer.hasTimePassed(5)) {
            EntityPlayerSP var10000 = mc.thePlayer;
            var10000.motionX *= 4.25D;
            var10000 = mc.thePlayer;
            var10000.motionZ *= 4.25D;
            lIIIIIllIIIIIl.move = true;
         }

         if (!mc.thePlayer.onGround) {
            MovementUtils.strafe();
         }

         lIIIIIllIIIIIl.tickTimer.update();
      }

   }

   public Frame() {
      super("Frame");
   }
}
