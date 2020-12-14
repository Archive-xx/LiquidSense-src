//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.minecraft.block.BlockAir;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class AACPort extends SpeedMode {
   public void onMotion() {
   }

   public AACPort() {
      super("AACPort");
   }

   public void onMove(MoveEvent lllllllllllllllllIllllIlIllllIIl) {
   }

   public void onUpdate() {
      if (MovementUtils.isMoving()) {
         String lllllllllllllllllIllllIlIllllllI = mc.thePlayer.rotationYaw * 0.017453292F;

         for(double lllllllllllllllllIllllIlIlllllIl = 0.2D; lllllllllllllllllIllllIlIlllllIl <= (double)(Float)((Speed)LiquidBounce.moduleManager.getModule(Speed.class)).portMax.get(); lllllllllllllllllIllllIlIlllllIl += 0.2D) {
            boolean lllllllllllllllllIllllIlIlllllII = mc.thePlayer.posX - (double)MathHelper.sin(lllllllllllllllllIllllIlIllllllI) * lllllllllllllllllIllllIlIlllllIl;
            double lllllllllllllllllIllllIllIIIIIlI = mc.thePlayer.posZ + (double)MathHelper.cos(lllllllllllllllllIllllIlIllllllI) * lllllllllllllllllIllllIlIlllllIl;
            if (mc.thePlayer.posY < (double)((int)mc.thePlayer.posY) + 0.5D && !(BlockUtils.getBlock(new BlockPos(lllllllllllllllllIllllIlIlllllII, mc.thePlayer.posY, lllllllllllllllllIllllIllIIIIIlI)) instanceof BlockAir)) {
               break;
            }

            mc.thePlayer.sendQueue.addToSendQueue(new C04PacketPlayerPosition(lllllllllllllllllIllllIlIlllllII, mc.thePlayer.posY, lllllllllllllllllIllllIllIIIIIlI, true));
         }

      }
   }
}
