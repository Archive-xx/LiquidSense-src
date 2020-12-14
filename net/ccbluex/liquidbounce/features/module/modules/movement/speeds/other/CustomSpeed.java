//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.other;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;

public class CustomSpeed extends SpeedMode {
   public void onMove(MoveEvent llIIllIIIIIIlII) {
   }

   public void onEnable() {
      boolean llIIllIIIIlIIlI = (Speed)LiquidBounce.moduleManager.getModule(Speed.class);
      if (llIIllIIIIlIIlI != null) {
         if ((Boolean)llIIllIIIIlIIlI.resetXZValue.get()) {
            mc.thePlayer.motionX = mc.thePlayer.motionZ = 0.0D;
         }

         if ((Boolean)llIIllIIIIlIIlI.resetYValue.get()) {
            mc.thePlayer.motionY = 0.0D;
         }

         super.onEnable();
      }
   }

   public void onUpdate() {
   }

   public void onMotion() {
      if (MovementUtils.isMoving()) {
         char llIIllIIIIlllIl = (Speed)LiquidBounce.moduleManager.getModule(Speed.class);
         if (llIIllIIIIlllIl == null) {
            return;
         }

         mc.timer.timerSpeed = (Float)llIIllIIIIlllIl.customTimerValue.get();
         if (mc.thePlayer.onGround) {
            MovementUtils.strafe((Float)llIIllIIIIlllIl.customSpeedValue.get());
            mc.thePlayer.motionY = (double)(Float)llIIllIIIIlllIl.customYValue.get();
         } else if ((Boolean)llIIllIIIIlllIl.customStrafeValue.get()) {
            MovementUtils.strafe((Float)llIIllIIIIlllIl.customSpeedValue.get());
         } else {
            MovementUtils.strafe();
         }
      } else {
         mc.thePlayer.motionX = mc.thePlayer.motionZ = 0.0D;
      }

   }

   public CustomSpeed() {
      super("Custom");
   }

   public void onDisable() {
      mc.timer.timerSpeed = 1.0F;
      super.onDisable();
   }
}
