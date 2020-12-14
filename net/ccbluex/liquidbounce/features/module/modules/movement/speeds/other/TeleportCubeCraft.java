//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.other;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;

public class TeleportCubeCraft extends SpeedMode {
   // $FF: synthetic field
   private final MSTimer timer = new MSTimer();

   public void onMove(MoveEvent lllllIIlIIlIIIl) {
      if (MovementUtils.isMoving() && mc.thePlayer.onGround && lllllIIlIIlIlII.timer.hasTimePassed(300L)) {
         Exception lllllIIlIIlIIII = MovementUtils.getDirection();
         float lllllIIlIIlIlIl = (Float)((Speed)LiquidBounce.moduleManager.getModule(Speed.class)).cubecraftPortLengthValue.get();
         lllllIIlIIlIIIl.setX(-Math.sin(lllllIIlIIlIIII) * (double)lllllIIlIIlIlIl);
         lllllIIlIIlIIIl.setZ(Math.cos(lllllIIlIIlIIII) * (double)lllllIIlIIlIlIl);
         lllllIIlIIlIlII.timer.reset();
      }

   }

   public void onMotion() {
   }

   public void onUpdate() {
   }

   public TeleportCubeCraft() {
      super("TeleportCubeCraft");
   }
}
