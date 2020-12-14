//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.spectre;

import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.MathHelper;

public class SpectreOnGround extends SpeedMode {
   // $FF: synthetic field
   private int speedUp;

   public void onUpdate() {
   }

   public SpectreOnGround() {
      super("SpectreOnGround");
   }

   public void onMotion() {
   }

   public void onMove(MoveEvent lllllllllllllllllIlllIlIlIIllIIl) {
      if (MovementUtils.isMoving() && !mc.thePlayer.movementInput.jump) {
         if (lllllllllllllllllIlllIlIlIIllIlI.speedUp >= 10) {
            if (mc.thePlayer.onGround) {
               mc.thePlayer.motionX = 0.0D;
               mc.thePlayer.motionZ = 0.0D;
               lllllllllllllllllIlllIlIlIIllIlI.speedUp = 0;
            }

         } else {
            if (mc.thePlayer.onGround && mc.gameSettings.keyBindForward.isKeyDown()) {
               float lllllllllllllllllIlllIlIlIIllIll = mc.thePlayer.rotationYaw * 0.017453292F;
               EntityPlayerSP var10000 = mc.thePlayer;
               var10000.motionX -= (double)(MathHelper.sin(lllllllllllllllllIlllIlIlIIllIll) * 0.145F);
               var10000 = mc.thePlayer;
               var10000.motionZ += (double)(MathHelper.cos(lllllllllllllllllIlllIlIlIIllIll) * 0.145F);
               lllllllllllllllllIlllIlIlIIllIIl.setX(mc.thePlayer.motionX);
               lllllllllllllllllIlllIlIlIIllIIl.setY(0.005D);
               lllllllllllllllllIlllIlIlIIllIIl.setZ(mc.thePlayer.motionZ);
               ++lllllllllllllllllIlllIlIlIIllIlI.speedUp;
            }

         }
      }
   }
}
