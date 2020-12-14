//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;

public class AACGround2 extends SpeedMode {
   public AACGround2() {
      super("AACGround2");
   }

   public void onDisable() {
      mc.timer.timerSpeed = 1.0F;
   }

   public void onUpdate() {
      if (MovementUtils.isMoving()) {
         mc.timer.timerSpeed = (Float)((Speed)LiquidBounce.moduleManager.getModule(Speed.class)).aacGroundTimerValue.get();
         MovementUtils.strafe(0.02F);
      }
   }

   public void onMove(MoveEvent llllllllllllllllllIlIIIlIllIlIll) {
   }

   public void onMotion() {
   }
}
