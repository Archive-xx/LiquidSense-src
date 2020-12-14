//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp;

import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.MathHelper;

public class NCPYPort extends SpeedMode {
   // $FF: synthetic field
   private int jumps;

   public void onMove(MoveEvent llllllllllllllllllIllIllllIlIIll) {
   }

   public NCPYPort() {
      super("NCPYPort");
   }

   public void onMotion() {
      if (!mc.thePlayer.isOnLadder() && !mc.thePlayer.isInWater() && !mc.thePlayer.isInLava() && !mc.thePlayer.isInWeb && MovementUtils.isMoving() && !mc.thePlayer.isInWater()) {
         if (llllllllllllllllllIllIllllIlIlll.jumps >= 4 && mc.thePlayer.onGround) {
            llllllllllllllllllIllIllllIlIlll.jumps = 0;
         }

         if (mc.thePlayer.onGround) {
            mc.thePlayer.motionY = llllllllllllllllllIllIllllIlIlll.jumps <= 1 ? 0.41999998688697815D : 0.4000000059604645D;
            boolean llllllllllllllllllIllIllllIlIllI = mc.thePlayer.rotationYaw * 0.017453292F;
            EntityPlayerSP var10000 = mc.thePlayer;
            var10000.motionX -= (double)(MathHelper.sin(llllllllllllllllllIllIllllIlIllI) * 0.2F);
            var10000 = mc.thePlayer;
            var10000.motionZ += (double)(MathHelper.cos(llllllllllllllllllIllIllllIlIllI) * 0.2F);
            ++llllllllllllllllllIllIllllIlIlll.jumps;
         } else if (llllllllllllllllllIllIllllIlIlll.jumps <= 1) {
            mc.thePlayer.motionY = -5.0D;
         }

         MovementUtils.strafe();
      }
   }

   public void onUpdate() {
   }
}
