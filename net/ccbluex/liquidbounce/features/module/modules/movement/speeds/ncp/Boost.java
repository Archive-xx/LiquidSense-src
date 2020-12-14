//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp;

import java.util.Iterator;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.AxisAlignedBB;

public class Boost extends SpeedMode {
   // $FF: synthetic field
   private int motionDelay;
   // $FF: synthetic field
   private float ground;

   private boolean shouldSpeedUp() {
      return !mc.thePlayer.isInWater() && !mc.thePlayer.isOnLadder() && !mc.thePlayer.isSneaking() && MovementUtils.isMoving();
   }

   public Boost() {
      super("Boost");
   }

   public void onMotion() {
      Exception llllllllllllllllllIIIIIllIIlIIII = 3.1981D;
      double llllllllllllllllllIIIIIllIIlIlIl = 4.69D;
      boolean llllllllllllllllllIIIIIllIIlIIll = true;
      Iterator llllllllllllllllllIIIIIllIIIllIl = mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(mc.thePlayer.motionX / llllllllllllllllllIIIIIllIIlIlIl, 0.0D, mc.thePlayer.motionZ / llllllllllllllllllIIIIIllIIlIlIl)).iterator();

      while(llllllllllllllllllIIIIIllIIIllIl.hasNext()) {
         Object llllllllllllllllllIIIIIllIIllIll = llllllllllllllllllIIIIIllIIIllIl.next();
         if (llllllllllllllllllIIIIIllIIllIll instanceof AxisAlignedBB) {
            llllllllllllllllllIIIIIllIIlIIll = false;
            break;
         }
      }

      if (mc.thePlayer.onGround && llllllllllllllllllIIIIIllIIlIIIl.ground < 1.0F) {
         llllllllllllllllllIIIIIllIIlIIIl.ground += 0.2F;
      }

      if (!mc.thePlayer.onGround) {
         llllllllllllllllllIIIIIllIIlIIIl.ground = 0.0F;
      }

      if (llllllllllllllllllIIIIIllIIlIIIl.ground == 1.0F && llllllllllllllllllIIIIIllIIlIIIl.shouldSpeedUp()) {
         if (!mc.thePlayer.isSprinting()) {
            llllllllllllllllllIIIIIllIIlIlIl += 0.8D;
         }

         if (mc.thePlayer.moveStrafing != 0.0F) {
            llllllllllllllllllIIIIIllIIlIIII -= 0.1D;
            llllllllllllllllllIIIIIllIIlIlIl += 0.5D;
         }

         if (mc.thePlayer.isInWater()) {
            llllllllllllllllllIIIIIllIIlIIII -= 0.1D;
         }

         ++llllllllllllllllllIIIIIllIIlIIIl.motionDelay;
         EntityPlayerSP var10000;
         switch(llllllllllllllllllIIIIIllIIlIIIl.motionDelay) {
         case 1:
            var10000 = mc.thePlayer;
            var10000.motionX *= llllllllllllllllllIIIIIllIIlIIII;
            var10000 = mc.thePlayer;
            var10000.motionZ *= llllllllllllllllllIIIIIllIIlIIII;
            break;
         case 2:
            var10000 = mc.thePlayer;
            var10000.motionX /= 1.458D;
            var10000 = mc.thePlayer;
            var10000.motionZ /= 1.458D;
         case 3:
         default:
            break;
         case 4:
            if (llllllllllllllllllIIIIIllIIlIIll) {
               mc.thePlayer.setPosition(mc.thePlayer.posX + mc.thePlayer.motionX / llllllllllllllllllIIIIIllIIlIlIl, mc.thePlayer.posY, mc.thePlayer.posZ + mc.thePlayer.motionZ / llllllllllllllllllIIIIIllIIlIlIl);
            }

            llllllllllllllllllIIIIIllIIlIIIl.motionDelay = 0;
         }
      }

   }

   public void onUpdate() {
   }

   public void onMove(MoveEvent llllllllllllllllllIIIIIllIIIlIIl) {
   }
}
