//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aquavit;

import java.util.ArrayList;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.timer.TimerUtil;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.client.C13PacketPlayerAbilities;
import net.minecraft.potion.Potion;

public class Hypixel extends SpeedMode {
   // $FF: synthetic field
   private double lastDist;
   // $FF: synthetic field
   TimerUtil lastCheck = new TimerUtil();
   // $FF: synthetic field
   private static int stage;
   // $FF: synthetic field
   public boolean shouldslow = false;
   // $FF: synthetic field
   private double distance;
   // $FF: synthetic field
   boolean lessSlow;
   // $FF: synthetic field
   private TimerUtil timer = new TimerUtil();
   // $FF: synthetic field
   private double movementSpeed;
   // $FF: synthetic field
   double less;
   // $FF: synthetic field
   ArrayList<Packet> packets = new ArrayList();
   // $FF: synthetic field
   boolean collided = false;
   // $FF: synthetic field
   double stair;

   public void onDisable() {
      mc.timer.timerSpeed = 1.0F;
      lllllllllllllllllIllllIIllIIIIIl.packets.clear();
   }

   private double getHypixelSpeed(int lllllllllllllllllIllllIIIlllIllI) {
      double lllllllllllllllllIllllIIIlllIlIl = lllllllllllllllllIllllIIIlllIIIl.defaultSpeed() + 0.028D * (double)MovementUtils.getSpeedEffect() + (double)MovementUtils.getSpeedEffect() / 15.0D;
      double lllllllllllllllllIllllIIIlllIlII = 0.4145D + (double)MovementUtils.getSpeedEffect() / 12.5D;
      double lllllllllllllllllIllllIIIlllIIll = 0.4045D + (double)MovementUtils.getSpeedEffect() / 12.5D;
      long lllllllllllllllllIllllIIIllIllII = (double)lllllllllllllllllIllllIIIlllIllI / 500.0D * 3.0D;
      TimerUtil var10000;
      boolean var10001;
      if (lllllllllllllllllIllllIIIlllIllI == 0) {
         var10000 = lllllllllllllllllIllllIIIlllIIIl.timer;
         var10001 = false;
         if (TimerUtil.isDelayComplete(300L)) {
            var10000 = lllllllllllllllllIllllIIIlllIIIl.timer;
            var10001 = false;
            TimerUtil.reset();
         }

         var10000 = lllllllllllllllllIllllIIIlllIIIl.lastCheck;
         var10001 = false;
         if (!TimerUtil.isDelayComplete(500L)) {
            if (!lllllllllllllllllIllllIIIlllIIIl.shouldslow) {
               lllllllllllllllllIllllIIIlllIIIl.shouldslow = true;
            }
         } else if (lllllllllllllllllIllllIIIlllIIIl.shouldslow) {
            lllllllllllllllllIllllIIIlllIIIl.shouldslow = false;
         }

         lllllllllllllllllIllllIIIlllIlIl = 0.64D + ((double)MovementUtils.getSpeedEffect() + 0.028D * (double)MovementUtils.getSpeedEffect()) * 0.134D;
      } else if (lllllllllllllllllIllllIIIlllIllI == 1) {
         lllllllllllllllllIllllIIIlllIlIl = lllllllllllllllllIllllIIIlllIlII;
      } else if (lllllllllllllllllIllllIIIlllIllI == 2) {
         lllllllllllllllllIllllIIIlllIlIl = lllllllllllllllllIllllIIIlllIIll;
      } else if (lllllllllllllllllIllllIIIlllIllI >= 3) {
         lllllllllllllllllIllllIIIlllIlIl = lllllllllllllllllIllllIIIlllIIll - lllllllllllllllllIllllIIIllIllII;
      }

      if (!lllllllllllllllllIllllIIIlllIIIl.shouldslow) {
         var10000 = lllllllllllllllllIllllIIIlllIIIl.lastCheck;
         var10001 = false;
         if (TimerUtil.isDelayComplete(500L) && !lllllllllllllllllIllllIIIlllIIIl.collided) {
            return Math.max(lllllllllllllllllIllllIIIlllIlIl, lllllllllllllllllIllllIIIlllIIIl.shouldslow ? lllllllllllllllllIllllIIIlllIlIl : lllllllllllllllllIllllIIIlllIIIl.defaultSpeed() + 0.028D * (double)MovementUtils.getSpeedEffect());
         }
      }

      lllllllllllllllllIllllIIIlllIlIl = 0.2D;
      if (lllllllllllllllllIllllIIIlllIllI == 0) {
         lllllllllllllllllIllllIIIlllIlIl = 0.0D;
      }

      return Math.max(lllllllllllllllllIllllIIIlllIlIl, lllllllllllllllllIllllIIIlllIIIl.shouldslow ? lllllllllllllllllIllllIIIlllIlIl : lllllllllllllllllIllllIIIlllIIIl.defaultSpeed() + 0.028D * (double)MovementUtils.getSpeedEffect());
   }

   private double getBaseMoveSpeed() {
      double lllllllllllllllllIllllIIlIlIlIII = 0.2583000063896179D;
      if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
         char lllllllllllllllllIllllIIlIlIIllI = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
         lllllllllllllllllIllllIIlIlIlIII *= 1.0D + 0.1D * (double)(lllllllllllllllllIllllIIlIlIIllI + 1);
      }

      return lllllllllllllllllIllllIIlIlIlIII;
   }

   private double defaultSpeed() {
      double lllllllllllllllllIllllIIlIlIlllI = 0.2873D;
      if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
         int lllllllllllllllllIllllIIlIllIIIl = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
         lllllllllllllllllIllllIIlIlIlllI *= 1.0D + 0.2D * (double)(lllllllllllllllllIllllIIlIllIIIl + 1);
      }

      return lllllllllllllllllIllllIIlIlIlllI;
   }

   private void setMotion(MoveEvent lllllllllllllllllIllllIIIllIIlIl, double lllllllllllllllllIllllIIIllIIlII) {
      long lllllllllllllllllIllllIIIlIllllI = (double)mc.thePlayer.movementInput.moveForward;
      Exception lllllllllllllllllIllllIIIlIlllIl = (double)mc.thePlayer.movementInput.moveStrafe;
      byte lllllllllllllllllIllllIIIlIlllII = mc.thePlayer.rotationYaw;
      if (lllllllllllllllllIllllIIIlIllllI == 0.0D && lllllllllllllllllIllllIIIlIlllIl == 0.0D) {
         lllllllllllllllllIllllIIIllIIlIl.setX(0.0D);
         lllllllllllllllllIllllIIIllIIlIl.setZ(0.0D);
      } else {
         if (lllllllllllllllllIllllIIIlIllllI != 0.0D) {
            if (lllllllllllllllllIllllIIIlIlllIl > 0.0D) {
               lllllllllllllllllIllllIIIlIlllII += (float)(lllllllllllllllllIllllIIIlIllllI > 0.0D ? -45 : 45);
            } else if (lllllllllllllllllIllllIIIlIlllIl < 0.0D) {
               lllllllllllllllllIllllIIIlIlllII += (float)(lllllllllllllllllIllllIIIlIllllI > 0.0D ? 45 : -45);
            }

            lllllllllllllllllIllllIIIlIlllIl = 0.0D;
            if (lllllllllllllllllIllllIIIlIllllI > 0.0D) {
               lllllllllllllllllIllllIIIlIllllI = 1.0D;
            } else if (lllllllllllllllllIllllIIIlIllllI < 0.0D) {
               lllllllllllllllllIllllIIIlIllllI = -1.0D;
            }
         }

         lllllllllllllllllIllllIIIllIIlIl.setX((lllllllllllllllllIllllIIIlIllllI * lllllllllllllllllIllllIIIllIIlII * Math.cos(Math.toRadians((double)(lllllllllllllllllIllllIIIlIlllII + 90.0F))) + lllllllllllllllllIllllIIIlIlllIl * lllllllllllllllllIllllIIIllIIlII * Math.sin(Math.toRadians((double)lllllllllllllllllIllllIIIlIlllII + 90.0D))) * 0.99D);
         lllllllllllllllllIllllIIIllIIlIl.setZ((lllllllllllllllllIllllIIIlIllllI * lllllllllllllllllIllllIIIllIIlII * Math.sin(Math.toRadians((double)(lllllllllllllllllIllllIIIlIlllII + 90.0F))) - lllllllllllllllllIllllIIIlIlllIl * lllllllllllllllllIllllIIIllIIlII * Math.cos(Math.toRadians((double)lllllllllllllllllIllllIIIlIlllII + 90.0D))) * 0.99D);
      }

   }

   public void onUpdate() {
   }

   public void onMotion() {
   }

   @EventTarget
   public void onPacket(PacketEvent lllllllllllllllllIllllIIlIIlIlIl) {
      Packet lllllllllllllllllIllllIIlIIlIlII = lllllllllllllllllIllllIIlIIlIlIl.getPacket();
      if (lllllllllllllllllIllllIIlIIlIlII instanceof C03PacketPlayer) {
         C03PacketPlayer lllllllllllllllllIllllIIlIIlIlll = (C03PacketPlayer)lllllllllllllllllIllllIIlIIlIlII;
         lllllllllllllllllIllllIIlIIlIIll.packets.add(lllllllllllllllllIllllIIlIIlIlII);
         boolean var10001 = false;
      }

   }

   public float angle(double lllllllllllllllllIllllIIlIllIlIl, double lllllllllllllllllIllllIIlIllIllI) {
      return (float)(Math.atan2(lllllllllllllllllIllllIIlIllIllI - mc.thePlayer.posZ, lllllllllllllllllIllllIIlIllIlIl - mc.thePlayer.posX) * 180.0D / 3.141592653589793D) - 90.0F;
   }

   public void onEnable() {
      lllllllllllllllllIllllIIllIIIlll.packets.clear();
      boolean lllllllllllllllllIllllIIllIIIllI = mc.thePlayer == null;
      lllllllllllllllllIllllIIllIIIlll.collided = lllllllllllllllllIllllIIllIIIllI ? false : mc.thePlayer.isCollidedHorizontally;
      lllllllllllllllllIllllIIllIIIlll.lessSlow = false;
      if (mc.thePlayer != null) {
         lllllllllllllllllIllllIIllIIIlll.movementSpeed = lllllllllllllllllIllllIIllIIIlll.getBaseMoveSpeed();
      }

      lllllllllllllllllIllllIIllIIIlll.less = (double)randomNumber(-10000, 0) / 1.0E7D;
      lllllllllllllllllIllllIIllIIIlll.less = 0.0D;
      stage = 2;
      mc.timer.timerSpeed = 1.0F;
   }

   public void doHypixelDisable(boolean lllllllllllllllllIllllIIIlllllll) {
      mc.getNetHandler().getNetworkManager().sendPacket(new C0FPacketConfirmTransaction(0, (short)-2, false));
      PlayerCapabilities lllllllllllllllllIllllIIlIIIIIII = new PlayerCapabilities();
      lllllllllllllllllIllllIIlIIIIIII.isFlying = true;
      lllllllllllllllllIllllIIlIIIIIII.setPlayerWalkSpeed(mc.thePlayer.capabilities.getWalkSpeed());
      lllllllllllllllllIllllIIlIIIIIII.setFlySpeed(mc.thePlayer.capabilities.getFlySpeed());
      if (lllllllllllllllllIllllIIIlllllll) {
         mc.thePlayer.capabilities.isFlying = true;
         mc.thePlayer.capabilities.setPlayerWalkSpeed(mc.thePlayer.capabilities.getWalkSpeed());
         mc.thePlayer.capabilities.setFlySpeed(mc.thePlayer.capabilities.getFlySpeed());
      } else {
         mc.getNetHandler().getNetworkManager().sendPacket(new C13PacketPlayerAbilities(lllllllllllllllllIllllIIlIIIIIII));
      }

   }

   @EventTarget
   public void onUpdate(UpdateEvent lllllllllllllllllIllllIIlIlIIIIl) {
      char lllllllllllllllllIllllIIlIIlllIl = mc.thePlayer.posX - mc.thePlayer.prevPosX;
      double lllllllllllllllllIllllIIlIIlllll = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;
      lllllllllllllllllIllllIIlIIllllI.distance = Math.sqrt(lllllllllllllllllIllllIIlIIlllIl * lllllllllllllllllIllllIIlIIlllIl + lllllllllllllllllIllllIIlIIlllll * lllllllllllllllllIllllIIlIIlllll);
      if (lllllllllllllllllIllllIIlIIllllI.lastDist > 5.0D) {
         lllllllllllllllllIllllIIlIIllllI.lastDist = 0.0D;
      }

      lllllllllllllllllIllllIIlIIllllI.lastDist = Math.sqrt((mc.thePlayer.posX - mc.thePlayer.prevPosX) * (mc.thePlayer.posX - mc.thePlayer.prevPosX) + (mc.thePlayer.posZ - mc.thePlayer.prevPosZ) * (mc.thePlayer.posZ - mc.thePlayer.prevPosZ));
   }

   @EventTarget
   public void onMove(MoveEvent lllllllllllllllllIllllIIlIIIIllI) {
      if (mc.thePlayer.isCollidedHorizontally) {
         lllllllllllllllllIllllIIlIIIlIIl.collided = true;
      }

      if (lllllllllllllllllIllllIIlIIIlIIl.collided) {
         mc.timer.timerSpeed = 1.0F;
         stage = -1;
      }

      if (lllllllllllllllllIllllIIlIIIlIIl.stair > 0.0D) {
         lllllllllllllllllIllllIIlIIIlIIl.stair -= 0.25D;
      }

      lllllllllllllllllIllllIIlIIIlIIl.less -= lllllllllllllllllIllllIIlIIIlIIl.less > 1.0D ? 0.12D : 0.11D;
      if (lllllllllllllllllIllllIIlIIIlIIl.less < 0.0D) {
         lllllllllllllllllIllllIIlIIIlIIl.less = 0.0D;
      }

      if (lllllllllllllllllIllllIIlIIIlIIl.packets.size() > 140) {
         lllllllllllllllllIllllIIlIIIlIIl.doHypixelDisable(false);
         lllllllllllllllllIllllIIlIIIlIIl.packets.clear();
      }

      if (MovementUtils.isOnGround(0.01D) && MovementUtils.isMoving()) {
         lllllllllllllllllIllllIIlIIIlIIl.collided = mc.thePlayer.isCollidedHorizontally;
         if (stage >= 0 || lllllllllllllllllIllllIIlIIIlIIl.collided) {
            stage = 0;
            double lllllllllllllllllIllllIIlIIIlIlI = 0.4048888688697815D + (double)MovementUtils.getJumpEffect() * 0.1D;
            if (lllllllllllllllllIllllIIlIIIlIIl.stair == 0.0D) {
               mc.thePlayer.jump();
               mc.thePlayer.motionY = lllllllllllllllllIllllIIlIIIlIlI;
               lllllllllllllllllIllllIIlIIIIllI.setY(mc.thePlayer.motionY);
            }

            ++lllllllllllllllllIllllIIlIIIlIIl.less;
            if (lllllllllllllllllIllllIIlIIIlIIl.less > 1.52D) {
               lllllllllllllllllIllllIIlIIIlIIl.less = 1.52D;
            }
         }
      }

      lllllllllllllllllIllllIIlIIIlIIl.movementSpeed = lllllllllllllllllIllllIIlIIIlIIl.getHypixelSpeed(stage) + 0.0331D + Math.random() / 1000000.0D;
      lllllllllllllllllIllllIIlIIIlIIl.movementSpeed *= 0.91D;
      if (lllllllllllllllllIllllIIlIIIlIIl.stair > 0.0D) {
         lllllllllllllllllIllllIIlIIIlIIl.movementSpeed *= 0.66D - (double)MovementUtils.getSpeedEffect() * 0.1D;
      }

      if (stage < 0) {
         lllllllllllllllllIllllIIlIIIlIIl.movementSpeed = lllllllllllllllllIllllIIlIIIlIIl.getBaseMoveSpeed();
      }

      if (lllllllllllllllllIllllIIlIIIlIIl.lessSlow) {
         lllllllllllllllllIllllIIlIIIlIIl.movementSpeed *= 0.93D;
      }

      if (MovementUtils.isInLiquid()) {
         lllllllllllllllllIllllIIlIIIlIIl.movementSpeed = 0.12D;
      }

      if (mc.thePlayer.moveForward != 0.0F || mc.thePlayer.moveStrafing != 0.0F) {
         lllllllllllllllllIllllIIlIIIlIIl.setMotion(lllllllllllllllllIllllIIlIIIIllI, lllllllllllllllllIllllIIlIIIlIIl.movementSpeed);
      }

      ++stage;
   }

   public Hypixel() {
      super("Hypixel");
   }

   public static int randomNumber(int lllllllllllllllllIllllIIlIllllII, int lllllllllllllllllIllllIIlIlllIll) {
      return Math.round((float)lllllllllllllllllIllllIIlIlllIll + (float)Math.random() * (float)(lllllllllllllllllIllllIIlIllllII - lllllllllllllllllIllllIIlIlllIll));
   }
}
