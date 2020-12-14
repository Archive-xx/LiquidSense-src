//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.client.entity.EntityPlayerSP;

public class AACHop350 extends SpeedMode implements Listenable {
   public void onMove(MoveEvent lllllllllllllllllIllIIlIllIlllII) {
   }

   @EventTarget
   public void onMotion(MotionEvent lllllllllllllllllIllIIlIllIllIII) {
      if (lllllllllllllllllIllIIlIllIllIII.getEventState() == EventState.POST && MovementUtils.isMoving() && !mc.thePlayer.isInWater() && !mc.thePlayer.isInLava()) {
         EntityPlayerSP var10000 = mc.thePlayer;
         var10000.jumpMovementFactor += 0.00208F;
         if (mc.thePlayer.fallDistance <= 1.0F) {
            if (mc.thePlayer.onGround) {
               mc.thePlayer.jump();
               var10000 = mc.thePlayer;
               var10000.motionX *= 1.0118000507354736D;
               var10000 = mc.thePlayer;
               var10000.motionZ *= 1.0118000507354736D;
            } else {
               var10000 = mc.thePlayer;
               var10000.motionY -= 0.014700000174343586D;
               var10000 = mc.thePlayer;
               var10000.motionX *= 1.0013799667358398D;
               var10000 = mc.thePlayer;
               var10000.motionZ *= 1.0013799667358398D;
            }
         }
      }

   }

   public boolean handleEvents() {
      return lllllllllllllllllIllIIlIllIlIIll.isActive();
   }

   public AACHop350() {
      super("AACHop3.5.0");
      LiquidBounce.eventManager.registerListener(lllllllllllllllllIllIIlIlllIIIII);
   }

   public void onMotion() {
   }

   public void onEnable() {
      if (mc.thePlayer.onGround) {
         mc.thePlayer.motionX = mc.thePlayer.motionZ = 0.0D;
      }

   }

   public void onDisable() {
      mc.thePlayer.jumpMovementFactor = 0.02F;
   }

   public void onUpdate() {
   }
}
