//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;

public class AACGround extends SpeedMode {
   public void onMove(MoveEvent lIIlIlIllllII) {
   }

   public AACGround() {
      super("AACGround");
   }

   public void onMotion() {
   }

   public void onUpdate() {
      if (MovementUtils.isMoving()) {
         mc.timer.timerSpeed = (Float)((Speed)LiquidBounce.moduleManager.getModule(Speed.class)).aacGroundTimerValue.get();
         mc.getNetHandler().addToSendQueue(new C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
      }
   }

   public void onDisable() {
      mc.timer.timerSpeed = 1.0F;
   }
}
