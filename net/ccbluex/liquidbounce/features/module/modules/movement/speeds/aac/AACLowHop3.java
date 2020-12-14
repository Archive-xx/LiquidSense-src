//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac;

import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.client.entity.EntityPlayerSP;

public class AACLowHop3 extends SpeedMode {
   // $FF: synthetic field
   private boolean firstJump;
   // $FF: synthetic field
   private boolean waitForGround;

   public AACLowHop3() {
      super("AACLowHop3");
   }

   public void onMotion() {
      if (MovementUtils.isMoving()) {
         if (mc.thePlayer.hurtTime <= 0) {
            if (mc.thePlayer.onGround) {
               llIlIlIlIIlIIlI.waitForGround = false;
               if (!llIlIlIlIIlIIlI.firstJump) {
                  llIlIlIlIIlIIlI.firstJump = true;
               }

               mc.thePlayer.jump();
               mc.thePlayer.motionY = 0.41D;
            } else {
               if (llIlIlIlIIlIIlI.waitForGround) {
                  return;
               }

               if (mc.thePlayer.isCollidedHorizontally) {
                  return;
               }

               llIlIlIlIIlIIlI.firstJump = false;
               EntityPlayerSP var10000 = mc.thePlayer;
               var10000.motionY -= 0.0149D;
            }

            if (!mc.thePlayer.isCollidedHorizontally) {
               MovementUtils.forward(llIlIlIlIIlIIlI.firstJump ? 0.0016D : 0.001799D);
            }
         } else {
            llIlIlIlIIlIIlI.firstJump = true;
            llIlIlIlIIlIIlI.waitForGround = true;
         }
      } else {
         mc.thePlayer.motionZ = 0.0D;
         mc.thePlayer.motionX = 0.0D;
      }

      long llIlIlIlIIlIIIl = (double)MovementUtils.getSpeed();
      mc.thePlayer.motionX = -(Math.sin(MovementUtils.getDirection()) * llIlIlIlIIlIIIl);
      mc.thePlayer.motionZ = Math.cos(MovementUtils.getDirection()) * llIlIlIlIIlIIIl;
   }

   public void onMove(MoveEvent llIlIlIlIIIllII) {
   }

   public void onEnable() {
      llIlIlIlIlIIlll.firstJump = true;
   }

   public void onUpdate() {
   }
}
