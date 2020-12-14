//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.minecraft.block.BlockCarpet;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.MathHelper;

public class AACHop3313 extends SpeedMode {
   public AACHop3313() {
      super("AACHop3.3.13");
   }

   public void onMove(MoveEvent lllllllllllllllllllllIIIIIlIllII) {
   }

   public void onMotion() {
   }

   public void onUpdate() {
      if (MovementUtils.isMoving() && !mc.thePlayer.isInWater() && !mc.thePlayer.isInLava() && !mc.thePlayer.isOnLadder() && !mc.thePlayer.isRiding() && mc.thePlayer.hurtTime <= 0) {
         EntityPlayerSP var10000;
         if (mc.thePlayer.onGround && mc.thePlayer.isCollidedVertically) {
            float lllllllllllllllllllllIIIIIllIIII = mc.thePlayer.rotationYaw * 0.017453292F;
            var10000 = mc.thePlayer;
            var10000.motionX -= (double)(MathHelper.sin(lllllllllllllllllllllIIIIIllIIII) * 0.202F);
            var10000 = mc.thePlayer;
            var10000.motionZ += (double)(MathHelper.cos(lllllllllllllllllllllIIIIIllIIII) * 0.202F);
            mc.thePlayer.motionY = 0.4050000011920929D;
            LiquidBounce.eventManager.callEvent(new JumpEvent(0.405F));
            MovementUtils.strafe();
         } else if (mc.thePlayer.fallDistance < 0.31F) {
            if (BlockUtils.getBlock(mc.thePlayer.getPosition()) instanceof BlockCarpet) {
               return;
            }

            mc.thePlayer.jumpMovementFactor = mc.thePlayer.moveStrafing == 0.0F ? 0.027F : 0.021F;
            var10000 = mc.thePlayer;
            var10000.motionX *= 1.001D;
            var10000 = mc.thePlayer;
            var10000.motionZ *= 1.001D;
            if (!mc.thePlayer.isCollidedHorizontally) {
               var10000 = mc.thePlayer;
               var10000.motionY -= 0.01499999314546585D;
            }
         } else {
            mc.thePlayer.jumpMovementFactor = 0.02F;
         }

      }
   }

   public void onDisable() {
      mc.thePlayer.jumpMovementFactor = 0.02F;
   }
}
