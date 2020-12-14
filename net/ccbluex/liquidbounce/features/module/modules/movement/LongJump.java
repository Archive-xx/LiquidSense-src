//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement;

import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.entity.EntityPlayerSP;

@ModuleInfo(
   name = "LongJump",
   description = "Allows you to jump further.",
   category = ModuleCategory.MOVEMENT
)
public class LongJump extends Module {
   // $FF: synthetic field
   private double moveSpeed;
   // $FF: synthetic field
   private final BoolValue autoJumpValue = new BoolValue("AutoJump", false);
   // $FF: synthetic field
   private final ListValue modeValue = new ListValue("Mode", new String[]{"NCP", "AACv1", "AACv2", "AACv3", "Mineplex", "Mineplex2", "Mineplex3", "WatchDog"}, "NCP");
   // $FF: synthetic field
   private boolean teleported;
   // $FF: synthetic field
   private boolean canMineplexBoost;
   // $FF: synthetic field
   private final IntegerValue delay = new IntegerValue("Delay", 200, 10, 1000);
   // $FF: synthetic field
   private final MSTimer delayTimer = new MSTimer();
   // $FF: synthetic field
   private double speed;
   // $FF: synthetic field
   private double lastDif;
   // $FF: synthetic field
   private double lastDist;
   // $FF: synthetic field
   private final IntegerValue dns = new IntegerValue("Distance", 10, 1, 10);
   // $FF: synthetic field
   private final BoolValue autoclose = new BoolValue("AutoClose", false);
   // $FF: synthetic field
   private int downTicks;
   // $FF: synthetic field
   private final FloatValue ncpBoostValue = new FloatValue("NCPBoost", 4.25F, 1.0F, 10.0F);
   // $FF: synthetic field
   private int groundTicks;
   // $FF: synthetic field
   private boolean jumped;
   // $FF: synthetic field
   private boolean canBoost;
   // $FF: synthetic field
   private int stage;

   @EventTarget
   public void onUpdate(UpdateEvent lIIIlIIIlIIIll) {
      if (lIIIlIIIlIIIlI.jumped) {
         byte lIIIlIIIlIIIIl = (String)lIIIlIIIlIIIlI.modeValue.get();
         if (mc.thePlayer.onGround || mc.thePlayer.capabilities.isFlying) {
            lIIIlIIIlIIIlI.jumped = false;
            lIIIlIIIlIIIlI.canMineplexBoost = false;
            if (lIIIlIIIlIIIIl.equalsIgnoreCase("NCP")) {
               mc.thePlayer.motionX = 0.0D;
               mc.thePlayer.motionZ = 0.0D;
            }

            return;
         }

         Exception lIIIlIIIlIIIII = lIIIlIIIlIIIIl.toLowerCase();
         short lIIIlIIIIlllll = -1;
         switch(lIIIlIIIlIIIII.hashCode()) {
         case -1362669950:
            if (lIIIlIIIlIIIII.equals("mineplex")) {
               lIIIlIIIIlllll = 5;
            }
            break;
         case 108891:
            if (lIIIlIIIlIIIII.equals("ncp")) {
               lIIIlIIIIlllll = 0;
            }
            break;
         case 92570110:
            if (lIIIlIIIlIIIII.equals("aacv1")) {
               lIIIlIIIIlllll = 1;
            }
            break;
         case 92570111:
            if (lIIIlIIIlIIIII.equals("aacv2")) {
               lIIIlIIIIlllll = 2;
            }
            break;
         case 92570112:
            if (lIIIlIIIlIIIII.equals("aacv3")) {
               lIIIlIIIIlllll = 4;
            }
            break;
         case 706904560:
            if (lIIIlIIIlIIIII.equals("mineplex2")) {
               lIIIlIIIIlllll = 6;
            }
            break;
         case 706904561:
            if (lIIIlIIIlIIIII.equals("mineplex3")) {
               lIIIlIIIIlllll = 3;
            }
         }

         EntityPlayerSP var10000;
         switch(lIIIlIIIIlllll) {
         case 0:
            MovementUtils.strafe(MovementUtils.getSpeed() * (lIIIlIIIlIIIlI.canBoost ? (Float)lIIIlIIIlIIIlI.ncpBoostValue.get() : 1.0F));
            lIIIlIIIlIIIlI.canBoost = false;
            break;
         case 1:
            var10000 = mc.thePlayer;
            var10000.motionY += 0.05999D;
            MovementUtils.strafe(MovementUtils.getSpeed() * 1.08F);
            break;
         case 2:
         case 3:
            mc.thePlayer.jumpMovementFactor = 0.09F;
            var10000 = mc.thePlayer;
            var10000.motionY += 0.01321D;
            mc.thePlayer.jumpMovementFactor = 0.08F;
            MovementUtils.strafe();
            break;
         case 4:
            double lIIIlIIIIllllI = mc.thePlayer;
            if (lIIIlIIIIllllI.fallDistance > 0.5F && !lIIIlIIIlIIIlI.teleported) {
               double lIIIlIIIIlllIl = 3.0D;
               long lIIIlIIIIlllII = lIIIlIIIIllllI.getHorizontalFacing();
               double lIIIlIIIlIlIII = 0.0D;
               double lIIIlIIIlIIlll = 0.0D;
               switch(lIIIlIIIIlllII) {
               case NORTH:
                  lIIIlIIIlIIlll = -lIIIlIIIIlllIl;
                  break;
               case EAST:
                  lIIIlIIIlIlIII = lIIIlIIIIlllIl;
                  break;
               case SOUTH:
                  lIIIlIIIlIIlll = lIIIlIIIIlllIl;
                  break;
               case WEST:
                  lIIIlIIIlIlIII = -lIIIlIIIIlllIl;
               }

               lIIIlIIIIllllI.setPosition(lIIIlIIIIllllI.posX + lIIIlIIIlIlIII, lIIIlIIIIllllI.posY, lIIIlIIIIllllI.posZ + lIIIlIIIlIIlll);
               lIIIlIIIlIIIlI.teleported = true;
            }
            break;
         case 5:
            var10000 = mc.thePlayer;
            var10000.motionY += 0.01321D;
            mc.thePlayer.jumpMovementFactor = 0.08F;
            MovementUtils.strafe();
            break;
         case 6:
            if (lIIIlIIIlIIIlI.canMineplexBoost) {
               mc.thePlayer.jumpMovementFactor = 0.1F;
               if (mc.thePlayer.fallDistance > 1.5F) {
                  mc.thePlayer.jumpMovementFactor = 0.0F;
                  mc.thePlayer.motionY = -10.0D;
               }

               MovementUtils.strafe();
            }
         }
      }

      if ((Boolean)lIIIlIIIlIIIlI.autoJumpValue.get() && mc.thePlayer.onGround && MovementUtils.isMoving()) {
         lIIIlIIIlIIIlI.jumped = true;
         mc.thePlayer.jump();
      }

   }

   @EventTarget
   public void onMove(MoveEvent lIIIlIIIIlIIII) {
      String lIIIlIIIIlIIlI = (String)lIIIlIIIIlIlII.modeValue.get();
      if (lIIIlIIIIlIIlI.equalsIgnoreCase("mineplex3")) {
         if (mc.thePlayer.fallDistance != 0.0F) {
            EntityPlayerSP var10000 = mc.thePlayer;
            var10000.motionY += 0.037D;
         }
      } else if (lIIIlIIIIlIIlI.equalsIgnoreCase("ncp") && !MovementUtils.isMoving() && lIIIlIIIIlIlII.jumped) {
         mc.thePlayer.motionX = 0.0D;
         mc.thePlayer.motionZ = 0.0D;
         lIIIlIIIIlIIII.zeroXZ();
      } else if (lIIIlIIIIlIIlI.equalsIgnoreCase("watchdog")) {
         if (!lIIIlIIIIlIlII.delayTimer.isDelayComplete((long)(Integer)lIIIlIIIIlIlII.delay.get())) {
            lIIIlIIIIlIIII.setX(0.0D);
            lIIIlIIIIlIIII.setZ(0.0D);
         }

         if (lIIIlIIIIlIlII.delayTimer.isDelayComplete((long)(Integer)lIIIlIIIIlIlII.delay.get())) {
            if (lIIIlIIIIlIlII.stage == 1 && MovementUtils.isMoving()) {
               lIIIlIIIIlIlII.stage = 2;
               lIIIlIIIIlIlII.speed = (1.38D * MovementUtils.LongJumpMoveSpeed() - 0.01D) / 1.6D;
            } else if (lIIIlIIIIlIlII.stage == 2 && mc.thePlayer.onGround && mc.thePlayer.isCollidedVertically) {
               lIIIlIIIIlIlII.stage = 3;
               lIIIlIIIIlIIII.setX(0.0D);
               lIIIlIIIIlIIII.setZ(0.0D);
               lIIIlIIIIlIIII.setY(mc.thePlayer.motionY = 0.423D + (double)MovementUtils.LongJumpEffect() * 0.09D);
               lIIIlIIIIlIlII.speed *= 2.149D;
            } else if (lIIIlIIIIlIlII.stage == 3) {
               lIIIlIIIIlIlII.stage = 4;
               String lIIIlIIIIIlllI = 0.64D * (lIIIlIIIIlIlII.lastDist - MovementUtils.LongJumpMoveSpeed());
               lIIIlIIIIlIlII.speed = (lIIIlIIIIlIlII.lastDist - lIIIlIIIIIlllI) * 1.92D;
            } else {
               if (mc.thePlayer.motionY < 0.0D && (double)mc.thePlayer.fallDistance <= 1.2D) {
                  mc.thePlayer.motionY = lIIIlIIIIlIlII.getDownMotion(lIIIlIIIIlIlII.downTicks);
                  ++lIIIlIIIIlIlII.downTicks;
               }

               lIIIlIIIIlIlII.speed = lIIIlIIIIlIlII.lastDist - lIIIlIIIIlIlII.lastDist / 159.0D;
            }

            lIIIlIIIIlIlII.speed = Math.max(lIIIlIIIIlIlII.speed, MovementUtils.LongJumpMoveSpeed());
            if (lIIIlIIIIlIlII.speed > 0.7D) {
               lIIIlIIIIlIlII.speed = 0.7D - Math.random() / 50.0D;
            }

            MovementUtils.setMoveSpeed(lIIIlIIIIlIIII, lIIIlIIIIlIlII.speed * 0.1D * (double)(Integer)lIIIlIIIIlIlII.dns.get());
         }
      }

   }

   @EventTarget
   public void onMotion(MotionEvent lIIIlIIIIIIllI) {
      if (((String)lIIIlIIIIIIlll.modeValue.get()).equalsIgnoreCase("watchdog")) {
         switch(lIIIlIIIIIIllI.getEventState()) {
         case PRE:
            if (mc.thePlayer.isCollidedVertically || mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getCollisionBoundingBox().offset(0.0D, mc.thePlayer.motionY, 0.0D)).size() > 0) {
               if (lIIIlIIIIIIlll.stage == 0) {
                  EntityPlayerSP var10000 = mc.thePlayer;
                  var10000.posY += 1.0E-10D;
                  lIIIlIIIIIIlll.stage = 1;
                  lIIIlIIIIIIlll.downTicks = 0;
               } else if (lIIIlIIIIIIlll.stage == 4 && (Boolean)lIIIlIIIIIIlll.autoclose.get()) {
                  lIIIlIIIIIIlll.setState(false);
               }
            }
            break;
         case POST:
            double lIIIlIIIIIlIIl = mc.thePlayer.posX - mc.thePlayer.prevPosX;
            double lIIIlIIIIIlIII = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;
            lIIIlIIIIIIlll.lastDist = Math.sqrt(lIIIlIIIIIlIIl * lIIIlIIIIIlIIl + lIIIlIIIIIlIII * lIIIlIIIIIlIII);
         }
      }

   }

   @EventTarget(
      ignoreCondition = true
   )
   public void onJump(JumpEvent lIIIIlllllllII) {
      lIIIIllllllIll.jumped = true;
      lIIIIllllllIll.canBoost = true;
      lIIIIllllllIll.teleported = false;
      if (lIIIIllllllIll.getState()) {
         String lIIIIllllllIIl = ((String)lIIIIllllllIll.modeValue.get()).toLowerCase();
         String lIIIIllllllIII = -1;
         switch(lIIIIllllllIIl.hashCode()) {
         case -1362669950:
            if (lIIIIllllllIIl.equals("mineplex")) {
               lIIIIllllllIII = 0;
            }
            break;
         case 706904560:
            if (lIIIIllllllIIl.equals("mineplex2")) {
               lIIIIllllllIII = 1;
            }
         }

         switch(lIIIIllllllIII) {
         case 0:
            lIIIIlllllllII.setMotion(lIIIIlllllllII.getMotion() * 4.08F);
            break;
         case 1:
            if (mc.thePlayer.isCollidedHorizontally) {
               lIIIIlllllllII.setMotion(2.31F);
               lIIIIllllllIll.canMineplexBoost = true;
               mc.thePlayer.onGround = false;
            }
         }
      }

   }

   public void onDisable() {
      mc.timer.timerSpeed = 1.0F;
   }

   public String getTag() {
      return (String)lIIIIlllllIlIl.modeValue.get();
   }

   public double getDownMotion(int lIIIlIIIlllIll) {
      boolean lIIIlIIIlllIII = new double[]{-0.012255154820464102D, -0.029804124002464114D, -0.04384329955726414D, -0.05507464016846417D, -0.0640597127913122D, -0.07124777099670104D, -0.07699821764670045D, -0.08159857503525064D, -0.08527886100093134D, -0.08822308981734832D, -0.09057847290557984D, -0.09246277940424341D, -0.09397022462563696D, -0.09517618082072195D, -0.09614094579116605D, -0.09691275777902224D, -0.0975302073785079D, -0.098024167065457D};
      return lIIIlIIIlllIll <= lIIIlIIIlllIII.length ? lIIIlIIIlllIII[lIIIlIIIlllIll] : mc.thePlayer.motionY;
   }

   public void onEnable() {
      lIIIlIIIllIllI.lastDif = 0.0D;
      lIIIlIIIllIllI.moveSpeed = 0.0D;
      lIIIlIIIllIllI.stage = 0;
      lIIIlIIIllIllI.groundTicks = 1;
      if (((String)lIIIlIIIllIllI.modeValue.get()).equalsIgnoreCase("watchdog")) {
         mc.timer.timerSpeed = 0.6F;
         lIIIlIIIllIllI.delayTimer.reset();
         lIIIlIIIllIllI.speed = MovementUtils.LongJumpMoveSpeed();
         lIIIlIIIllIllI.downTicks = 0;
      }

   }
}
