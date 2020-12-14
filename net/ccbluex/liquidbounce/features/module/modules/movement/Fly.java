//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement;

import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import net.ccbluex.liquidbounce.event.BlockBBEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.StepEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.utils.timer.TickTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.block.BlockAir;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C13PacketPlayerAbilities;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.network.play.client.C03PacketPlayer.C06PacketPlayerPosLook;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Keyboard;

@ModuleInfo(
   name = "Fly",
   description = "Allows you to fly in survival mode.",
   category = ModuleCategory.MOVEMENT,
   keyBind = 33
)
public class Fly extends Module {
   // $FF: synthetic field
   private double lastDistance;
   // $FF: synthetic field
   private final MSTimer flyTimer = new MSTimer();
   // $FF: synthetic field
   private final MSTimer mineSecureVClipTimer = new MSTimer();
   // $FF: synthetic field
   private final BoolValue blinkAttack = new BoolValue("Zoom-Attack", true);
   // $FF: synthetic field
   private final FloatValue blinkAttackPacket = new FloatValue("ZoomAttack-Packet", 4.0F, 2.0F, 20.0F);
   // $FF: synthetic field
   private boolean wasDead;
   // $FF: synthetic field
   private final FloatValue vanillaSpeedValue = new FloatValue("VanillaSpeed", 2.0F, 0.0F, 5.0F);
   // $FF: synthetic field
   private final TickTimer cubecraft2TickTimer = new TickTimer();
   // $FF: synthetic field
   private int aac3glideDelay;
   // $FF: synthetic field
   private ArrayList<Packet> packetList = new ArrayList();
   // $FF: synthetic field
   private final FloatValue ncpMotionValue = new FloatValue("NCPMotion", 0.0F, 0.0F, 1.0F);
   // $FF: synthetic field
   private final FloatValue aacMotion2 = new FloatValue("AAC3.3.13-Motion", 10.0F, 0.1F, 10.0F);
   // $FF: synthetic field
   private final TickTimer cubecraftTeleportTickTimer = new TickTimer();
   // $FF: synthetic field
   private double moveSpeed;
   // $FF: synthetic field
   private final MSTimer mineplexTimer = new MSTimer();
   // $FF: synthetic field
   private final BoolValue aacFast = new BoolValue("AAC3.0.5-Fast", true);
   // $FF: synthetic field
   private final TickTimer spartanTimer = new TickTimer();
   // $FF: synthetic field
   private double startY;
   // $FF: synthetic field
   private double aacJump;
   // $FF: synthetic field
   public final ListValue modeValue = new ListValue("Mode", new String[]{"Zoom", "Vanilla", "SmoothVanilla", "NCP", "OldNCP", "AAC1.9.10", "AAC3.0.5", "AAC3.1.6-Gomme", "AAC3.3.12", "AAC3.3.12-Glide", "AAC3.3.13", "CubeCraft", "Hypixel", "BoostHypixel", "FreeHypixel", "Rewinside", "TeleportRewinside", "Mineplex", "NeruxVace", "Minesucht", "Spartan", "Spartan2", "BugSpartan", "MineSecure", "HawkEye", "HAC", "WatchCat", "Jetpack", "KeepAlive", "Flag"}, "Vanilla");
   // $FF: synthetic field
   private final BoolValue markValue = new BoolValue("Mark", true);
   // $FF: synthetic field
   private final TickTimer hypixelTimer = new TickTimer();
   // $FF: synthetic field
   private float freeHypixelPitch;
   // $FF: synthetic field
   private final MSTimer groundTimer = new MSTimer();
   // $FF: synthetic field
   private final FloatValue hypixelBoostTimer = new FloatValue("Hypixel-BoostTimer", 1.0F, 0.0F, 5.0F);
   // $FF: synthetic field
   private int aac3delay;
   // $FF: synthetic field
   private final FloatValue aacSpeedValue = new FloatValue("AAC1.9.10-Speed", 0.3F, 0.0F, 1.0F);
   // $FF: synthetic field
   private float freeHypixelYaw;
   // $FF: synthetic field
   private boolean noFlag;
   // $FF: synthetic field
   private final BoolValue vanillaKickBypassValue = new BoolValue("VanillaKickBypass", false);
   // $FF: synthetic field
   private boolean failedStart = false;
   // $FF: synthetic field
   private final FloatValue mineplexSpeedValue = new FloatValue("MineplexSpeed", 1.0F, 0.5F, 10.0F);
   // $FF: synthetic field
   private int boostHypixelState = 1;
   // $FF: synthetic field
   private final BoolValue hypixelBoost = new BoolValue("Hypixel-Boost", true);
   // $FF: synthetic field
   private long minesuchtTP;
   // $FF: synthetic field
   private final TickTimer freeHypixelTimer = new TickTimer();
   // $FF: synthetic field
   private final IntegerValue hypixelBoostDelay = new IntegerValue("Hypixel-BoostDelay", 1200, 0, 2000);
   // $FF: synthetic field
   private final FloatValue aacMotion = new FloatValue("AAC3.3.12-Motion", 10.0F, 0.1F, 10.0F);
   // $FF: synthetic field
   private final IntegerValue neruxVaceTicks = new IntegerValue("NeruxVace-Ticks", 6, 0, 20);
   // $FF: synthetic field
   public static boolean noPacketModify;

   private void handleVanillaKickBypass() {
      if ((Boolean)lIllIlIIlIIlIl.vanillaKickBypassValue.get() && lIllIlIIlIIlIl.groundTimer.hasTimePassed(1000L)) {
         double lIllIlIIlIIllI = lIllIlIIlIIlIl.calculateGround();

         double lIllIlIIlIIIll;
         for(lIllIlIIlIIIll = mc.thePlayer.posY; lIllIlIIlIIIll > lIllIlIIlIIllI; lIllIlIIlIIIll -= 8.0D) {
            mc.getNetHandler().addToSendQueue(new C04PacketPlayerPosition(mc.thePlayer.posX, lIllIlIIlIIIll, mc.thePlayer.posZ, true));
            if (lIllIlIIlIIIll - 8.0D < lIllIlIIlIIllI) {
               break;
            }
         }

         mc.getNetHandler().addToSendQueue(new C04PacketPlayerPosition(mc.thePlayer.posX, lIllIlIIlIIllI, mc.thePlayer.posZ, true));

         for(lIllIlIIlIIIll = lIllIlIIlIIllI; lIllIlIIlIIIll < mc.thePlayer.posY; lIllIlIIlIIIll += 8.0D) {
            mc.getNetHandler().addToSendQueue(new C04PacketPlayerPosition(mc.thePlayer.posX, lIllIlIIlIIIll, mc.thePlayer.posZ, true));
            if (lIllIlIIlIIIll + 8.0D > mc.thePlayer.posY) {
               break;
            }
         }

         mc.getNetHandler().addToSendQueue(new C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
         lIllIlIIlIIlIl.groundTimer.reset();
      }
   }

   @EventTarget
   public void onStep(StepEvent lIllIlIIllIIIl) {
      String lIllIlIIllIIII = (String)lIllIlIIlIllll.modeValue.get();
      if (lIllIlIIllIIII.equalsIgnoreCase("Hypixel") || lIllIlIIllIIII.equalsIgnoreCase("BoostHypixel") || lIllIlIIllIIII.equalsIgnoreCase("Zoom") || lIllIlIIllIIII.equalsIgnoreCase("Rewinside") || lIllIlIIllIIII.equalsIgnoreCase("Mineplex") && mc.thePlayer.inventory.getCurrentItem() == null) {
         lIllIlIIllIIIl.setStepHeight(0.0F);
      }

   }

   public void onEnable() {
      if (mc.thePlayer != null) {
         lIlllIIlIlllIl.flyTimer.reset();
         noPacketModify = true;
         double lIlllIIlIlllII = mc.thePlayer.posX;
         boolean lIlllIIlIlIllI = mc.thePlayer.posY;
         double lIlllIIlIllIlI = mc.thePlayer.posZ;
         String lIlllIIlIllIIl = (String)lIlllIIlIlllIl.modeValue.get();
         float lIlllIIlIlIIll = lIlllIIlIllIIl.toLowerCase();
         long lIlllIIlIlIIlI = -1;
         switch(lIlllIIlIlIIll.hashCode()) {
         case -1693125473:
            if (lIlllIIlIlIIll.equals("bugspartan")) {
               lIlllIIlIlIIlI = 2;
            }
            break;
         case -1014303276:
            if (lIlllIIlIlIIll.equals("oldncp")) {
               lIlllIIlIlIIlI = 1;
            }
            break;
         case -926713373:
            if (lIlllIIlIlIIll.equals("infinitycubecraft")) {
               lIlllIIlIlIIlI = 3;
            }
            break;
         case 108891:
            if (lIlllIIlIlIIll.equals("ncp")) {
               lIlllIIlIlIIlI = 0;
            }
            break;
         case 3744723:
            if (lIlllIIlIlIIll.equals("zoom")) {
               lIlllIIlIlIIlI = 6;
            }
            break;
         case 502330237:
            if (lIlllIIlIlIIll.equals("infinityvcubecraft")) {
               lIlllIIlIlIIlI = 4;
            }
            break;
         case 1814517522:
            if (lIlllIIlIlIIll.equals("boosthypixel")) {
               lIlllIIlIlIIlI = 5;
            }
         }

         EntityPlayerSP var10000;
         int lIlllIIllIIIIl;
         switch(lIlllIIlIlIIlI) {
         case 0:
            if (mc.thePlayer.onGround) {
               for(lIlllIIllIIIIl = 0; lIlllIIllIIIIl < 65; ++lIlllIIllIIIIl) {
                  mc.getNetHandler().addToSendQueue(new C04PacketPlayerPosition(lIlllIIlIlllII, lIlllIIlIlIllI + 0.049D, lIlllIIlIllIlI, false));
                  mc.getNetHandler().addToSendQueue(new C04PacketPlayerPosition(lIlllIIlIlllII, lIlllIIlIlIllI, lIlllIIlIllIlI, false));
               }

               mc.getNetHandler().addToSendQueue(new C04PacketPlayerPosition(lIlllIIlIlllII, lIlllIIlIlIllI + 0.1D, lIlllIIlIllIlI, true));
               var10000 = mc.thePlayer;
               var10000.motionX *= 0.1D;
               var10000 = mc.thePlayer;
               var10000.motionZ *= 0.1D;
               mc.thePlayer.swingItem();
            }
            break;
         case 1:
            if (mc.thePlayer.onGround) {
               for(lIlllIIllIIIIl = 0; lIlllIIllIIIIl < 4; ++lIlllIIllIIIIl) {
                  mc.getNetHandler().addToSendQueue(new C04PacketPlayerPosition(lIlllIIlIlllII, lIlllIIlIlIllI + 1.01D, lIlllIIlIllIlI, false));
                  mc.getNetHandler().addToSendQueue(new C04PacketPlayerPosition(lIlllIIlIlllII, lIlllIIlIlIllI, lIlllIIlIllIlI, false));
               }

               mc.thePlayer.jump();
               mc.thePlayer.swingItem();
            }
            break;
         case 2:
            for(lIlllIIllIIIIl = 0; lIlllIIllIIIIl < 65; ++lIlllIIllIIIIl) {
               mc.getNetHandler().addToSendQueue(new C04PacketPlayerPosition(lIlllIIlIlllII, lIlllIIlIlIllI + 0.049D, lIlllIIlIllIlI, false));
               mc.getNetHandler().addToSendQueue(new C04PacketPlayerPosition(lIlllIIlIlllII, lIlllIIlIlIllI, lIlllIIlIllIlI, false));
            }

            mc.getNetHandler().addToSendQueue(new C04PacketPlayerPosition(lIlllIIlIlllII, lIlllIIlIlIllI + 0.1D, lIlllIIlIllIlI, true));
            var10000 = mc.thePlayer;
            var10000.motionX *= 0.1D;
            var10000 = mc.thePlayer;
            var10000.motionZ *= 0.1D;
            mc.thePlayer.swingItem();
            break;
         case 3:
            ClientUtils.displayChatMessage("§8[§c§lCubeCraft-§a§lFly§8] §aPlace a block before landing.");
            break;
         case 4:
            ClientUtils.displayChatMessage("§8[§c§lCubeCraft-§a§lFly§8] §aPlace a block before landing.");
            mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 2.0D, mc.thePlayer.posZ);
            break;
         case 5:
            if (mc.thePlayer.onGround) {
               mc.getNetHandler().addToSendQueue(new C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));

               for(double lIlllIIlIlIIIl = 3.0125D; lIlllIIlIlIIIl > 0.0D; lIlllIIlIlIIIl -= 0.0624986421D) {
                  mc.getNetHandler().addToSendQueue(new C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.0624986421D, mc.thePlayer.posZ, false));
                  mc.getNetHandler().addToSendQueue(new C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.0625D, mc.thePlayer.posZ, false));
                  mc.getNetHandler().addToSendQueue(new C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.0624986421D, mc.thePlayer.posZ, false));
                  mc.getNetHandler().addToSendQueue(new C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1.3579E-6D, mc.thePlayer.posZ, false));
               }

               mc.getNetHandler().addToSendQueue(new C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
               mc.thePlayer.jump();
               var10000 = mc.thePlayer;
               var10000.posY += 0.41999998688697815D;
               lIlllIIlIlllIl.boostHypixelState = 1;
               lIlllIIlIlllIl.moveSpeed = 0.1D;
               lIlllIIlIlllIl.lastDistance = 0.0D;
               lIlllIIlIlllIl.failedStart = false;
            }
            break;
         case 6:
            if (mc.thePlayer.onGround) {
               mc.getNetHandler().addToSendQueue(new C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));

               for(double lIlllIIlIllllI = 3.0125D; lIlllIIlIllllI > 0.0D; lIlllIIlIllllI -= 0.0624986421D) {
                  mc.getNetHandler().addToSendQueue(new C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.0624986421D, mc.thePlayer.posZ, false));
                  mc.getNetHandler().addToSendQueue(new C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.0625D, mc.thePlayer.posZ, false));
                  mc.getNetHandler().addToSendQueue(new C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.0624986421D, mc.thePlayer.posZ, false));
                  mc.getNetHandler().addToSendQueue(new C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1.3579E-6D, mc.thePlayer.posZ, false));
               }

               mc.getNetHandler().addToSendQueue(new C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
               mc.thePlayer.jump();
               var10000 = mc.thePlayer;
               var10000.posY += 0.41999998688697815D;
               lIlllIIlIlllIl.boostHypixelState = 1;
               lIlllIIlIlllIl.moveSpeed = 0.1D;
               lIlllIIlIlllIl.lastDistance = 0.0D;
               lIlllIIlIlllIl.failedStart = false;
            }
         }

         lIlllIIlIlllIl.startY = mc.thePlayer.posY;
         lIlllIIlIlllIl.aacJump = -3.8D;
         noPacketModify = false;
         if (lIlllIIlIllIIl.equalsIgnoreCase("freehypixel")) {
            lIlllIIlIlllIl.freeHypixelTimer.reset();
            mc.thePlayer.setPositionAndUpdate(mc.thePlayer.posX, mc.thePlayer.posY + 0.42D, mc.thePlayer.posZ);
            lIlllIIlIlllIl.freeHypixelYaw = mc.thePlayer.rotationYaw;
            lIlllIIlIlllIl.freeHypixelPitch = mc.thePlayer.rotationPitch;
         }

         super.onEnable();
      }
   }

   private double calculateGround() {
      AxisAlignedBB lIllIlIIIllIll = mc.thePlayer.getEntityBoundingBox();
      long lIllIlIIIllIII = 1.0D;

      for(double lIllIlIIIlIlll = mc.thePlayer.posY; lIllIlIIIlIlll > 0.0D; lIllIlIIIlIlll -= lIllIlIIIllIII) {
         Exception lIllIlIIIlIllI = new AxisAlignedBB(lIllIlIIIllIll.maxX, lIllIlIIIlIlll + lIllIlIIIllIII, lIllIlIIIllIll.maxZ, lIllIlIIIllIll.minX, lIllIlIIIlIlll, lIllIlIIIllIll.minZ);
         if (mc.theWorld.checkBlockCollision(lIllIlIIIlIllI)) {
            if (lIllIlIIIllIII <= 0.05D) {
               return lIllIlIIIlIlll + lIllIlIIIllIII;
            }

            lIllIlIIIlIlll += lIllIlIIIllIII;
            lIllIlIIIllIII = 0.05D;
         }
      }

      return 0.0D;
   }

   @EventTarget
   public void onPacket(PacketEvent lIllIlIllIlIIl) {
      if (!noPacketModify) {
         Packet<?> lIllIlIllIlIll = lIllIlIllIlIIl.getPacket();
         if (lIllIlIllIlIll instanceof C03PacketPlayer) {
            String lIllIlIllIIlll = (C03PacketPlayer)lIllIlIllIlIll;
            boolean lIllIlIllIIllI = (String)lIllIlIllIllIl.modeValue.get();
            if (lIllIlIllIIllI.equalsIgnoreCase("NCP") || lIllIlIllIIllI.equalsIgnoreCase("Rewinside") || lIllIlIllIIllI.equalsIgnoreCase("Mineplex") && mc.thePlayer.inventory.getCurrentItem() == null) {
               lIllIlIllIIlll.onGround = true;
            }

            if (lIllIlIllIIllI.equalsIgnoreCase("Hypixel") || lIllIlIllIIllI.equalsIgnoreCase("BoostHypixel") || lIllIlIllIIllI.equalsIgnoreCase("Zoom")) {
               lIllIlIllIIlll.onGround = false;
            }
         }

         if (((String)lIllIlIllIllIl.modeValue.get()).equalsIgnoreCase("Zoom")) {
            if (lIllIlIllIlIll instanceof C03PacketPlayer) {
               lIllIlIllIlIIl.cancelEvent();
               lIllIlIllIllIl.packetList.add(lIllIlIllIlIll);
               boolean var10001 = false;
            }

            if ((Boolean)lIllIlIllIllIl.blinkAttack.get() && lIllIlIllIlIll instanceof C02PacketUseEntity && (float)lIllIlIllIllIl.packetList.size() >= (Float)lIllIlIllIllIl.blinkAttackPacket.get()) {
               noPacketModify = true;
               String lIllIlIllIIlll = new PlayerCapabilities();
               lIllIlIllIIlll.allowFlying = true;
               lIllIlIllIIlll.isFlying = true;
               mc.thePlayer.sendQueue.addToSendQueue(new C13PacketPlayerAbilities(lIllIlIllIIlll));
               Iterator lIllIlIllIIllI = lIllIlIllIllIl.packetList.iterator();

               while(lIllIlIllIIllI.hasNext()) {
                  Packet lIllIlIlllIIII = (Packet)lIllIlIllIIllI.next();
                  mc.getNetHandler().addToSendQueue(lIllIlIlllIIII);
               }

               lIllIlIllIllIl.packetList.clear();
               noPacketModify = false;
            }
         }

         if (lIllIlIllIlIll instanceof S08PacketPlayerPosLook) {
            String lIllIlIllIIlll = (String)lIllIlIllIllIl.modeValue.get();
            if (lIllIlIllIIlll.equalsIgnoreCase("BoostHypixel")) {
               lIllIlIllIllIl.failedStart = true;
               ClientUtils.displayChatMessage("§8[§c§lBoostHypixel-§a§lFly§8] §cSetback detected.");
            }

            if (lIllIlIllIIlll.equalsIgnoreCase("Zoom")) {
               lIllIlIllIllIl.failedStart = true;
               ClientUtils.displayChatMessage("§8[§c§lZoom-§a§lFly§8] §cSetback detected.");
            }
         }

      }
   }

   public String getTag() {
      return (String)lIllIlIIIlIlII.modeValue.get();
   }

   @EventTarget
   public void onUpdate(UpdateEvent lIlllIIIIIllIl) {
      float lIlllIIIIIllII = (Float)lIlllIIIIIlIll.vanillaSpeedValue.get();
      String lIlllIIIIIlIIl = ((String)lIlllIIIIIlIll.modeValue.get()).toLowerCase();
      int lIlllIIIIIlIII = -1;
      switch(lIlllIIIIIlIIl.hashCode()) {
      case -2011701869:
         if (lIlllIIIIIlIIl.equals("spartan")) {
            lIlllIIIIIlIII = 21;
         }
         break;
      case -1848285483:
         if (lIlllIIIIIlIIl.equals("teleportrewinside")) {
            lIlllIIIIIlIII = 13;
         }
         break;
      case -1745954712:
         if (lIlllIIIIIlIIl.equals("keepalive")) {
            lIlllIIIIIlIII = 9;
         }
         break;
      case -1706751950:
         if (lIlllIIIIIlIIl.equals("jetpack")) {
            lIlllIIIIIlIII = 15;
         }
         break;
      case -1693125473:
         if (lIlllIIIIIlIIl.equals("bugspartan")) {
            lIlllIIIIIlIII = 26;
         }
         break;
      case -1362669950:
         if (lIlllIIIIIlIIl.equals("mineplex")) {
            lIlllIIIIIlIII = 16;
         }
         break;
      case -1031473397:
         if (lIlllIIIIIlIIl.equals("cubecraft")) {
            lIlllIIIIIlIII = 2;
         }
         break;
      case -1014303276:
         if (lIlllIIIIIlIIl.equals("oldncp")) {
            lIlllIIIIIlIII = 4;
         }
         break;
      case -385327063:
         if (lIlllIIIIIlIIl.equals("freehypixel")) {
            lIlllIIIIIlIII = 25;
         }
         break;
      case -321358:
         if (lIlllIIIIIlIIl.equals("aac3.3.12-glide")) {
            lIlllIIIIIlIII = 18;
         }
         break;
      case 103050:
         if (lIlllIIIIIlIIl.equals("hac")) {
            lIlllIIIIIlIII = 11;
         }
         break;
      case 108891:
         if (lIlllIIIIIlIIl.equals("ncp")) {
            lIlllIIIIIlIII = 3;
         }
         break;
      case 3145580:
         if (lIlllIIIIIlIIl.equals("flag")) {
            lIlllIIIIIlIII = 8;
         }
         break;
      case 65876907:
         if (lIlllIIIIIlIIl.equals("aac3.1.6-gomme")) {
            lIlllIIIIIlIII = 7;
         }
         break;
      case 233102203:
         if (lIlllIIIIIlIIl.equals("vanilla")) {
            lIlllIIIIIlIII = 0;
         }
         break;
      case 238938827:
         if (lIlllIIIIIlIIl.equals("neruxvace")) {
            lIlllIIIIIlIII = 23;
         }
         break;
      case 325225305:
         if (lIlllIIIIIlIIl.equals("aac3.0.5")) {
            lIlllIIIIIlIII = 6;
         }
         break;
      case 518567306:
         if (lIlllIIIIIlIIl.equals("minesecure")) {
            lIlllIIIIIlIII = 10;
         }
         break;
      case 545150119:
         if (lIlllIIIIIlIIl.equals("watchcat")) {
            lIlllIIIIIlIII = 20;
         }
         break;
      case 701317508:
         if (lIlllIIIIIlIIl.equals("hawkeye")) {
            lIlllIIIIIlIII = 12;
         }
         break;
      case 709940890:
         if (lIlllIIIIIlIIl.equals("minesucht")) {
            lIlllIIIIIlIII = 14;
         }
         break;
      case 1381910549:
         if (lIlllIIIIIlIIl.equals("hypixel")) {
            lIlllIIIIIlIII = 24;
         }
         break;
      case 1435059604:
         if (lIlllIIIIIlIIl.equals("aac1.9.10")) {
            lIlllIIIIIlIII = 5;
         }
         break;
      case 1457669645:
         if (lIlllIIIIIlIIl.equals("smoothvanilla")) {
            lIlllIIIIIlIII = 1;
         }
         break;
      case 1492139162:
         if (lIlllIIIIIlIIl.equals("aac3.3.12")) {
            lIlllIIIIIlIII = 17;
         }
         break;
      case 1492139163:
         if (lIlllIIIIIlIIl.equals("aac3.3.13")) {
            lIlllIIIIIlIII = 19;
         }
         break;
      case 2061751551:
         if (lIlllIIIIIlIIl.equals("spartan2")) {
            lIlllIIIIIlIII = 22;
         }
      }

      EntityPlayerSP var10000;
      boolean var10001;
      Vec3 lIlllIIIIlIIII;
      switch(lIlllIIIIIlIII) {
      case 0:
         mc.thePlayer.capabilities.isFlying = false;
         mc.thePlayer.motionY = 0.0D;
         mc.thePlayer.motionX = 0.0D;
         mc.thePlayer.motionZ = 0.0D;
         if (mc.gameSettings.keyBindJump.isKeyDown()) {
            var10000 = mc.thePlayer;
            var10000.motionY += (double)lIlllIIIIIllII;
         }

         if (mc.gameSettings.keyBindSneak.isKeyDown()) {
            var10000 = mc.thePlayer;
            var10000.motionY -= (double)lIlllIIIIIllII;
         }

         MovementUtils.strafe(lIlllIIIIIllII);
         lIlllIIIIIlIll.handleVanillaKickBypass();
         break;
      case 1:
         mc.thePlayer.capabilities.isFlying = true;
         lIlllIIIIIlIll.handleVanillaKickBypass();
         break;
      case 2:
         mc.timer.timerSpeed = 0.6F;
         lIlllIIIIIlIll.cubecraftTeleportTickTimer.update();
         break;
      case 3:
         mc.thePlayer.motionY = (double)(-(Float)lIlllIIIIIlIll.ncpMotionValue.get());
         if (mc.gameSettings.keyBindSneak.isKeyDown()) {
            mc.thePlayer.motionY = -0.5D;
         }

         MovementUtils.strafe();
         break;
      case 4:
         if (lIlllIIIIIlIll.startY > mc.thePlayer.posY) {
            mc.thePlayer.motionY = -1.0E-33D;
         }

         if (mc.gameSettings.keyBindSneak.isKeyDown()) {
            mc.thePlayer.motionY = -0.2D;
         }

         if (mc.gameSettings.keyBindJump.isKeyDown() && mc.thePlayer.posY < lIlllIIIIIlIll.startY - 0.1D) {
            mc.thePlayer.motionY = 0.2D;
         }

         MovementUtils.strafe();
         break;
      case 5:
         if (mc.gameSettings.keyBindJump.isKeyDown()) {
            lIlllIIIIIlIll.aacJump += 0.2D;
         }

         if (mc.gameSettings.keyBindSneak.isKeyDown()) {
            lIlllIIIIIlIll.aacJump -= 0.2D;
         }

         if (lIlllIIIIIlIll.startY + lIlllIIIIIlIll.aacJump > mc.thePlayer.posY) {
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer(true));
            mc.thePlayer.motionY = 0.8D;
            MovementUtils.strafe((Float)lIlllIIIIIlIll.aacSpeedValue.get());
         }

         MovementUtils.strafe();
         break;
      case 6:
         if (lIlllIIIIIlIll.aac3delay == 2) {
            mc.thePlayer.motionY = 0.1D;
         } else if (lIlllIIIIIlIll.aac3delay > 2) {
            lIlllIIIIIlIll.aac3delay = 0;
         }

         if ((Boolean)lIlllIIIIIlIll.aacFast.get()) {
            if ((double)mc.thePlayer.movementInput.moveStrafe == 0.0D) {
               mc.thePlayer.jumpMovementFactor = 0.08F;
            } else {
               mc.thePlayer.jumpMovementFactor = 0.0F;
            }
         }

         ++lIlllIIIIIlIll.aac3delay;
         break;
      case 7:
         mc.thePlayer.capabilities.isFlying = true;
         if (lIlllIIIIIlIll.aac3delay == 2) {
            var10000 = mc.thePlayer;
            var10000.motionY += 0.05D;
         } else if (lIlllIIIIIlIll.aac3delay > 2) {
            var10000 = mc.thePlayer;
            var10000.motionY -= 0.05D;
            lIlllIIIIIlIll.aac3delay = 0;
         }

         ++lIlllIIIIIlIll.aac3delay;
         if (!lIlllIIIIIlIll.noFlag) {
            mc.getNetHandler().addToSendQueue(new C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.onGround));
         }

         if (mc.thePlayer.posY <= 0.0D) {
            lIlllIIIIIlIll.noFlag = true;
         }
         break;
      case 8:
         mc.getNetHandler().addToSendQueue(new C06PacketPlayerPosLook(mc.thePlayer.posX + mc.thePlayer.motionX * 999.0D, mc.thePlayer.posY + (mc.gameSettings.keyBindJump.isKeyDown() ? 1.5624D : 1.0E-8D) - (mc.gameSettings.keyBindSneak.isKeyDown() ? 0.0624D : 2.0E-8D), mc.thePlayer.posZ + mc.thePlayer.motionZ * 999.0D, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, true));
         mc.getNetHandler().addToSendQueue(new C06PacketPlayerPosLook(mc.thePlayer.posX + mc.thePlayer.motionX * 999.0D, mc.thePlayer.posY - 6969.0D, mc.thePlayer.posZ + mc.thePlayer.motionZ * 999.0D, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, true));
         mc.thePlayer.setPosition(mc.thePlayer.posX + mc.thePlayer.motionX * 11.0D, mc.thePlayer.posY, mc.thePlayer.posZ + mc.thePlayer.motionZ * 11.0D);
         mc.thePlayer.motionY = 0.0D;
         break;
      case 9:
         mc.getNetHandler().addToSendQueue(new C00PacketKeepAlive());
         mc.thePlayer.capabilities.isFlying = false;
         mc.thePlayer.motionY = 0.0D;
         mc.thePlayer.motionX = 0.0D;
         mc.thePlayer.motionZ = 0.0D;
         if (mc.gameSettings.keyBindJump.isKeyDown()) {
            var10000 = mc.thePlayer;
            var10000.motionY += (double)lIlllIIIIIllII;
         }

         if (mc.gameSettings.keyBindSneak.isKeyDown()) {
            var10000 = mc.thePlayer;
            var10000.motionY -= (double)lIlllIIIIIllII;
         }

         MovementUtils.strafe(lIlllIIIIIllII);
         break;
      case 10:
         mc.thePlayer.capabilities.isFlying = false;
         if (!mc.gameSettings.keyBindSneak.isKeyDown()) {
            mc.thePlayer.motionY = -0.009999999776482582D;
         }

         mc.thePlayer.motionX = 0.0D;
         mc.thePlayer.motionZ = 0.0D;
         MovementUtils.strafe(lIlllIIIIIllII);
         if (lIlllIIIIIlIll.mineSecureVClipTimer.hasTimePassed(150L) && mc.gameSettings.keyBindJump.isKeyDown()) {
            mc.getNetHandler().addToSendQueue(new C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 5.0D, mc.thePlayer.posZ, false));
            mc.getNetHandler().addToSendQueue(new C04PacketPlayerPosition(0.5D, -1000.0D, 0.5D, false));
            String lIlllIIIIIIlll = Math.toRadians((double)mc.thePlayer.rotationYaw);
            double lIlllIIIIllllI = -Math.sin(lIlllIIIIIIlll) * 0.4D;
            short lIlllIIIIIIIll = Math.cos(lIlllIIIIIIlll) * 0.4D;
            mc.thePlayer.setPosition(mc.thePlayer.posX + lIlllIIIIllllI, mc.thePlayer.posY, mc.thePlayer.posZ + lIlllIIIIIIIll);
            lIlllIIIIIlIll.mineSecureVClipTimer.reset();
         }
         break;
      case 11:
         var10000 = mc.thePlayer;
         var10000.motionX *= 0.8D;
         var10000 = mc.thePlayer;
         var10000.motionZ *= 0.8D;
      case 12:
         mc.thePlayer.motionY = mc.thePlayer.motionY <= -0.42D ? 0.42D : -0.42D;
         break;
      case 13:
         Vec3 lIlllIIIIlllII = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
         float lIlllIIIIllIll = -mc.thePlayer.rotationYaw;
         float lIlllIIIIllIlI = -mc.thePlayer.rotationPitch;
         double lIlllIIIIllIIl = 9.9D;
         double lIlllIIIIIIIlI = new Vec3(Math.sin(Math.toRadians((double)lIlllIIIIllIll)) * Math.cos(Math.toRadians((double)lIlllIIIIllIlI)) * 9.9D + lIlllIIIIlllII.xCoord, Math.sin(Math.toRadians((double)lIlllIIIIllIlI)) * 9.9D + lIlllIIIIlllII.yCoord, Math.cos(Math.toRadians((double)lIlllIIIIllIll)) * Math.cos(Math.toRadians((double)lIlllIIIIllIlI)) * 9.9D + lIlllIIIIlllII.zCoord);
         mc.getNetHandler().addToSendQueue(new C04PacketPlayerPosition(lIlllIIIIIIIlI.xCoord, mc.thePlayer.posY + 2.0D, lIlllIIIIIIIlI.zCoord, true));
         mc.getNetHandler().addToSendQueue(new C04PacketPlayerPosition(lIlllIIIIlllII.xCoord, mc.thePlayer.posY + 2.0D, lIlllIIIIlllII.zCoord, true));
         mc.thePlayer.motionY = 0.0D;
         break;
      case 14:
         double lIlllIIIIlIlII = mc.thePlayer.posX;
         boolean lIlllIIIIIIIII = mc.thePlayer.posY;
         double lIllIlllllllll = mc.thePlayer.posZ;
         if (mc.gameSettings.keyBindForward.isKeyDown()) {
            if (System.currentTimeMillis() - lIlllIIIIIlIll.minesuchtTP > 99L) {
               char lIllIllllllllI = mc.thePlayer.getPositionEyes(0.0F);
               lIlllIIIIlIIII = mc.thePlayer.getLook(0.0F);
               short lIllIlllllllII = lIllIllllllllI.addVector(lIlllIIIIlIIII.xCoord * 7.0D, lIlllIIIIlIIII.yCoord * 7.0D, lIlllIIIIlIIII.zCoord * 7.0D);
               if ((double)mc.thePlayer.fallDistance > 0.8D) {
                  mc.thePlayer.sendQueue.addToSendQueue(new C04PacketPlayerPosition(lIlllIIIIlIlII, lIlllIIIIIIIII + 50.0D, lIllIlllllllll, false));
                  mc.thePlayer.fall(100.0F, 100.0F);
                  mc.thePlayer.fallDistance = 0.0F;
                  mc.thePlayer.sendQueue.addToSendQueue(new C04PacketPlayerPosition(lIlllIIIIlIlII, lIlllIIIIIIIII + 20.0D, lIllIlllllllll, true));
               }

               mc.thePlayer.sendQueue.addToSendQueue(new C04PacketPlayerPosition(lIllIlllllllII.xCoord, mc.thePlayer.posY + 50.0D, lIllIlllllllII.zCoord, true));
               mc.thePlayer.sendQueue.addToSendQueue(new C04PacketPlayerPosition(lIlllIIIIlIlII, lIlllIIIIIIIII, lIllIlllllllll, false));
               mc.thePlayer.sendQueue.addToSendQueue(new C04PacketPlayerPosition(lIllIlllllllII.xCoord, lIlllIIIIIIIII, lIllIlllllllII.zCoord, true));
               mc.thePlayer.sendQueue.addToSendQueue(new C04PacketPlayerPosition(lIlllIIIIlIlII, lIlllIIIIIIIII, lIllIlllllllll, false));
               lIlllIIIIIlIll.minesuchtTP = System.currentTimeMillis();
            } else {
               mc.thePlayer.sendQueue.addToSendQueue(new C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
               mc.thePlayer.sendQueue.addToSendQueue(new C04PacketPlayerPosition(lIlllIIIIlIlII, lIlllIIIIIIIII, lIllIlllllllll, true));
            }
         }
         break;
      case 15:
         if (mc.gameSettings.keyBindJump.isKeyDown()) {
            mc.effectRenderer.spawnEffectParticle(EnumParticleTypes.FLAME.getParticleID(), mc.thePlayer.posX, mc.thePlayer.posY + 0.2D, mc.thePlayer.posZ, -mc.thePlayer.motionX, -0.5D, -mc.thePlayer.motionZ, new int[0]);
            var10001 = false;
            var10000 = mc.thePlayer;
            var10000.motionY += 0.15D;
            var10000 = mc.thePlayer;
            var10000.motionX *= 1.1D;
            var10000 = mc.thePlayer;
            var10000.motionZ *= 1.1D;
         }
         break;
      case 16:
         if (mc.thePlayer.inventory.getCurrentItem() == null) {
            if (mc.gameSettings.keyBindJump.isKeyDown() && lIlllIIIIIlIll.mineplexTimer.hasTimePassed(100L)) {
               mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.6D, mc.thePlayer.posZ);
               lIlllIIIIIlIll.mineplexTimer.reset();
            }

            if (mc.thePlayer.isSneaking() && lIlllIIIIIlIll.mineplexTimer.hasTimePassed(100L)) {
               mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY - 0.6D, mc.thePlayer.posZ);
               lIlllIIIIIlIll.mineplexTimer.reset();
            }

            char lIllIllllllllI = new BlockPos(mc.thePlayer.posX, mc.thePlayer.getEntityBoundingBox().minY - 1.0D, mc.thePlayer.posZ);
            lIlllIIIIlIIII = (new Vec3(lIllIllllllllI)).addVector(0.4000000059604645D, 0.4000000059604645D, 0.4000000059604645D).add(new Vec3(EnumFacing.UP.getDirectionVec()));
            mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem(), lIllIllllllllI, EnumFacing.UP, new Vec3(lIlllIIIIlIIII.xCoord * 0.4000000059604645D, lIlllIIIIlIIII.yCoord * 0.4000000059604645D, lIlllIIIIlIIII.zCoord * 0.4000000059604645D));
            var10001 = false;
            MovementUtils.strafe(0.27F);
            mc.timer.timerSpeed = 1.0F + (Float)lIlllIIIIIlIll.mineplexSpeedValue.get();
         } else {
            mc.timer.timerSpeed = 1.0F;
            lIlllIIIIIlIll.setState(false);
            ClientUtils.displayChatMessage("§8[§c§lMineplex-§a§lFly§8] §aSelect an empty slot to fly.");
         }
         break;
      case 17:
         if (mc.thePlayer.posY < -70.0D) {
            mc.thePlayer.motionY = (double)(Float)lIlllIIIIIlIll.aacMotion.get();
         }

         mc.timer.timerSpeed = 1.0F;
         if (Keyboard.isKeyDown(29)) {
            mc.timer.timerSpeed = 0.2F;
            mc.rightClickDelayTimer = 0;
         }
         break;
      case 18:
         if (!mc.thePlayer.onGround) {
            ++lIlllIIIIIlIll.aac3glideDelay;
         }

         if (lIlllIIIIIlIll.aac3glideDelay == 2) {
            mc.timer.timerSpeed = 1.0F;
         }

         if (lIlllIIIIIlIll.aac3glideDelay == 12) {
            mc.timer.timerSpeed = 0.1F;
         }

         if (lIlllIIIIIlIll.aac3glideDelay >= 12 && !mc.thePlayer.onGround) {
            lIlllIIIIIlIll.aac3glideDelay = 0;
            mc.thePlayer.motionY = 0.015D;
         }
         break;
      case 19:
         if (mc.thePlayer.isDead) {
            lIlllIIIIIlIll.wasDead = true;
         }

         if (lIlllIIIIIlIll.wasDead || mc.thePlayer.onGround) {
            lIlllIIIIIlIll.wasDead = false;
            mc.thePlayer.motionY = (double)(Float)lIlllIIIIIlIll.aacMotion2.get();
            mc.thePlayer.onGround = false;
         }

         mc.timer.timerSpeed = 1.0F;
         if (Keyboard.isKeyDown(29)) {
            mc.timer.timerSpeed = 0.2F;
            mc.rightClickDelayTimer = 0;
         }
         break;
      case 20:
         MovementUtils.strafe(0.15F);
         mc.thePlayer.setSprinting(true);
         if (mc.thePlayer.posY < lIlllIIIIIlIll.startY + 2.0D) {
            mc.thePlayer.motionY = Math.random() * 0.5D;
         } else if (lIlllIIIIIlIll.startY > mc.thePlayer.posY) {
            MovementUtils.strafe(0.0F);
         }
         break;
      case 21:
         mc.thePlayer.motionY = 0.0D;
         lIlllIIIIIlIll.spartanTimer.update();
         if (lIlllIIIIIlIll.spartanTimer.hasTimePassed(12)) {
            mc.getNetHandler().addToSendQueue(new C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 8.0D, mc.thePlayer.posZ, true));
            mc.getNetHandler().addToSendQueue(new C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 8.0D, mc.thePlayer.posZ, true));
            lIlllIIIIIlIll.spartanTimer.reset();
         }
         break;
      case 22:
         MovementUtils.strafe(0.264F);
         if (mc.thePlayer.ticksExisted % 8 == 0) {
            mc.thePlayer.sendQueue.addToSendQueue(new C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 10.0D, mc.thePlayer.posZ, true));
         }
         break;
      case 23:
         if (!mc.thePlayer.onGround) {
            ++lIlllIIIIIlIll.aac3glideDelay;
         }

         if (lIlllIIIIIlIll.aac3glideDelay >= (Integer)lIlllIIIIIlIll.neruxVaceTicks.get() && !mc.thePlayer.onGround) {
            lIlllIIIIIlIll.aac3glideDelay = 0;
            mc.thePlayer.motionY = 0.015D;
         }
         break;
      case 24:
         char lIllIllllllllI = (Integer)lIlllIIIIIlIll.hypixelBoostDelay.get();
         if ((Boolean)lIlllIIIIIlIll.hypixelBoost.get() && !lIlllIIIIIlIll.flyTimer.hasTimePassed((long)lIllIllllllllI)) {
            mc.timer.timerSpeed = 1.0F + (Float)lIlllIIIIIlIll.hypixelBoostTimer.get() * ((float)lIlllIIIIIlIll.flyTimer.hasTimeLeft((long)lIllIllllllllI) / (float)lIllIllllllllI);
         }

         lIlllIIIIIlIll.hypixelTimer.update();
         if (lIlllIIIIIlIll.hypixelTimer.hasTimePassed(2)) {
            mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1.0E-5D, mc.thePlayer.posZ);
            lIlllIIIIIlIll.hypixelTimer.reset();
         }
         break;
      case 25:
         if (lIlllIIIIIlIll.freeHypixelTimer.hasTimePassed(10)) {
            mc.thePlayer.capabilities.isFlying = true;
         } else {
            mc.thePlayer.rotationYaw = lIlllIIIIIlIll.freeHypixelYaw;
            mc.thePlayer.rotationPitch = lIlllIIIIIlIll.freeHypixelPitch;
            mc.thePlayer.motionX = mc.thePlayer.motionZ = mc.thePlayer.motionY = 0.0D;
            if (lIlllIIIIIlIll.startY == (new BigDecimal(mc.thePlayer.posY)).setScale(3, RoundingMode.HALF_DOWN).doubleValue()) {
               lIlllIIIIIlIll.freeHypixelTimer.update();
            }
         }
         break;
      case 26:
         mc.thePlayer.capabilities.isFlying = false;
         mc.thePlayer.motionY = 0.0D;
         mc.thePlayer.motionX = 0.0D;
         mc.thePlayer.motionZ = 0.0D;
         if (mc.gameSettings.keyBindJump.isKeyDown()) {
            var10000 = mc.thePlayer;
            var10000.motionY += (double)lIlllIIIIIllII;
         }

         if (mc.gameSettings.keyBindSneak.isKeyDown()) {
            var10000 = mc.thePlayer;
            var10000.motionY -= (double)lIlllIIIIIllII;
         }

         MovementUtils.strafe(lIlllIIIIIllII);
      }

   }

   public ListValue getModeValue() {
      return lIlllIIllIllII.modeValue;
   }

   public void onDisable() {
      lIlllIIlIIIllI.wasDead = false;
      if (mc.thePlayer != null) {
         noPacketModify = true;
         lIlllIIlIIIllI.noFlag = false;
         boolean lIlllIIlIIIlIl = (String)lIlllIIlIIIllI.modeValue.get();
         if (!lIlllIIlIIIlIl.toUpperCase().startsWith("AAC") && !lIlllIIlIIIlIl.equalsIgnoreCase("Hypixel") && !lIlllIIlIIIlIl.equalsIgnoreCase("CubeCraft")) {
            mc.thePlayer.motionX = 0.0D;
            mc.thePlayer.motionY = 0.0D;
            mc.thePlayer.motionZ = 0.0D;
         }

         mc.thePlayer.capabilities.isFlying = false;
         mc.timer.timerSpeed = 1.0F;
         mc.thePlayer.speedInAir = 0.02F;
         if (lIlllIIlIIIlIl.equalsIgnoreCase("Zoom")) {
            byte lIlllIIlIIIlII = new PlayerCapabilities();
            lIlllIIlIIIlII.allowFlying = true;
            lIlllIIlIIIlII.isFlying = true;
            mc.thePlayer.sendQueue.addToSendQueue(new C13PacketPlayerAbilities(lIlllIIlIIIlII));
            Iterator lIlllIIlIIIIll = lIlllIIlIIIllI.packetList.iterator();

            while(lIlllIIlIIIIll.hasNext()) {
               Packet lIlllIIlIIlIlI = (Packet)lIlllIIlIIIIll.next();
               mc.getNetHandler().addToSendQueue(lIlllIIlIIlIlI);
            }

            lIlllIIlIIIllI.packetList.clear();
         }

         noPacketModify = false;
      }
   }

   @EventTarget
   public void onMotion(MotionEvent lIllIllIllIIII) {
      double lIllIllIllIIll;
      double lIllIllIlIllII;
      if (((String)lIllIllIlIllll.modeValue.get()).equalsIgnoreCase("zoom")) {
         switch(lIllIllIllIIII.getEventState()) {
         case PRE:
            lIllIllIlIllll.hypixelTimer.update();
            if (lIllIllIlIllll.hypixelTimer.hasTimePassed(2)) {
               mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1.0E-5D, mc.thePlayer.posZ);
               lIllIllIlIllll.hypixelTimer.reset();
            }

            if (!lIllIllIlIllll.failedStart) {
               mc.thePlayer.motionY = 0.0D;
            }
            break;
         case POST:
            lIllIllIllIIll = mc.thePlayer.posX - mc.thePlayer.prevPosX;
            lIllIllIlIllII = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;
            lIllIllIlIllll.lastDistance = Math.sqrt(lIllIllIllIIll * lIllIllIllIIll + lIllIllIlIllII * lIllIllIlIllII);
         }
      }

      if (((String)lIllIllIlIllll.modeValue.get()).equalsIgnoreCase("boosthypixel")) {
         switch(lIllIllIllIIII.getEventState()) {
         case PRE:
            lIllIllIlIllll.hypixelTimer.update();
            if (lIllIllIlIllll.hypixelTimer.hasTimePassed(2)) {
               mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1.0999E-5D, mc.thePlayer.posZ);
               lIllIllIlIllll.hypixelTimer.reset();
            }

            if (!lIllIllIlIllll.failedStart) {
               mc.thePlayer.motionY = 0.0D;
            }
            break;
         case POST:
            lIllIllIllIIll = mc.thePlayer.posX - mc.thePlayer.prevPosX;
            lIllIllIlIllII = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;
            lIllIllIlIllll.lastDistance = Math.sqrt(lIllIllIllIIll * lIllIllIllIIll + lIllIllIlIllII * lIllIllIlIllII);
         }
      }

   }

   @EventTarget
   public void onJump(JumpEvent lIllIlIIlllIlI) {
      String lIllIlIIlllIIl = (String)lIllIlIIlllIll.modeValue.get();
      if (lIllIlIIlllIIl.equalsIgnoreCase("Hypixel") || lIllIlIIlllIIl.equalsIgnoreCase("BoostHypixel") || lIllIlIIlllIIl.equalsIgnoreCase("Zoom") || lIllIlIIlllIIl.equalsIgnoreCase("Rewinside") || lIllIlIIlllIIl.equalsIgnoreCase("Mineplex") && mc.thePlayer.inventory.getCurrentItem() == null) {
         lIllIlIIlllIlI.cancelEvent();
      }

   }

   @EventTarget
   public void onMove(MoveEvent lIllIlIlIlIIII) {
      Exception lIllIlIlIIllll = ((String)lIllIlIlIlIIll.modeValue.get()).toLowerCase();
      byte lIllIlIlIIlllI = -1;
      switch(lIllIlIlIIllll.hashCode()) {
      case -1031473397:
         if (lIllIlIlIIllll.equals("cubecraft")) {
            lIllIlIlIIlllI = 0;
         }
         break;
      case -385327063:
         if (lIllIlIlIIllll.equals("freehypixel")) {
            lIllIlIlIIlllI = 3;
         }
         break;
      case 3744723:
         if (lIllIlIlIIllll.equals("zoom")) {
            lIllIlIlIIlllI = 2;
         }
         break;
      case 1814517522:
         if (lIllIlIlIIllll.equals("boosthypixel")) {
            lIllIlIlIIlllI = 1;
         }
      }

      double lIllIlIlIllIIl;
      switch(lIllIlIlIIlllI) {
      case 0:
         lIllIlIlIllIIl = Math.toRadians((double)mc.thePlayer.rotationYaw);
         if (lIllIlIlIlIIll.cubecraftTeleportTickTimer.hasTimePassed(2)) {
            lIllIlIlIlIIII.setX(-Math.sin(lIllIlIlIllIIl) * 2.4D);
            lIllIlIlIlIIII.setZ(Math.cos(lIllIlIlIllIIl) * 2.4D);
            lIllIlIlIlIIll.cubecraftTeleportTickTimer.reset();
         } else {
            lIllIlIlIlIIII.setX(-Math.sin(lIllIlIlIllIIl) * 0.2D);
            lIllIlIlIlIIII.setZ(Math.cos(lIllIlIlIllIIl) * 0.2D);
         }
         break;
      case 1:
         if (!MovementUtils.isMoving()) {
            lIllIlIlIlIIII.setX(0.0D);
            lIllIlIlIlIIII.setZ(0.0D);
         } else if (!lIllIlIlIlIIll.failedStart) {
            lIllIlIlIllIIl = 1.0D + (mc.thePlayer.isPotionActive(Potion.moveSpeed) ? 0.2D * (double)(mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1) : 0.0D);
            int lIllIlIlIIllII = 0.29D * lIllIlIlIllIIl;
            switch(lIllIlIlIlIIll.boostHypixelState) {
            case 1:
               lIllIlIlIlIIll.moveSpeed = (mc.thePlayer.isPotionActive(Potion.moveSpeed) ? 1.56D : 2.034D) * lIllIlIlIIllII;
               lIllIlIlIlIIll.boostHypixelState = 2;
               break;
            case 2:
               lIllIlIlIlIIll.moveSpeed *= 2.16D;
               lIllIlIlIlIIll.boostHypixelState = 3;
               break;
            case 3:
               lIllIlIlIlIIll.moveSpeed = lIllIlIlIlIIll.lastDistance - (mc.thePlayer.ticksExisted % 2 == 0 ? 0.0103D : 0.0123D) * (lIllIlIlIlIIll.lastDistance - lIllIlIlIIllII);
               lIllIlIlIlIIll.boostHypixelState = 4;
               break;
            default:
               lIllIlIlIlIIll.moveSpeed = lIllIlIlIlIIll.lastDistance - lIllIlIlIlIIll.lastDistance / 159.8D;
            }

            lIllIlIlIlIIll.moveSpeed = Math.max(lIllIlIlIlIIll.moveSpeed, 0.3D);
            long lIllIlIlIIlIll = MovementUtils.getDirection();
            lIllIlIlIlIIII.setX(-Math.sin(lIllIlIlIIlIll) * lIllIlIlIlIIll.moveSpeed);
            lIllIlIlIlIIII.setZ(Math.cos(lIllIlIlIIlIll) * lIllIlIlIlIIll.moveSpeed);
            mc.thePlayer.motionX = lIllIlIlIlIIII.getX();
            mc.thePlayer.motionZ = lIllIlIlIlIIII.getZ();
         }
         break;
      case 2:
         if (!MovementUtils.isMoving()) {
            lIllIlIlIlIIII.setX(0.0D);
            lIllIlIlIlIIII.setZ(0.0D);
         } else if (!lIllIlIlIlIIll.failedStart) {
            int lIllIlIlIIlIlI = 1.0D + (mc.thePlayer.isPotionActive(Potion.moveSpeed) ? 0.2D * (double)(mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1) : 0.0D);
            float lIllIlIlIIlIIl = 0.29D * lIllIlIlIIlIlI;
            switch(lIllIlIlIlIIll.boostHypixelState) {
            case 1:
               lIllIlIlIlIIll.moveSpeed = (mc.thePlayer.isPotionActive(Potion.moveSpeed) ? 1.56D : 2.034D) * lIllIlIlIIlIIl;
               lIllIlIlIlIIll.boostHypixelState = 2;
               break;
            case 2:
               lIllIlIlIlIIll.moveSpeed *= 2.16D;
               lIllIlIlIlIIll.boostHypixelState = 3;
               break;
            case 3:
               lIllIlIlIlIIll.moveSpeed = lIllIlIlIlIIll.lastDistance - (mc.thePlayer.ticksExisted % 2 == 0 ? 0.0103D : 0.0123D) * (lIllIlIlIlIIll.lastDistance - lIllIlIlIIlIIl);
               lIllIlIlIlIIll.boostHypixelState = 4;
               break;
            default:
               lIllIlIlIlIIll.moveSpeed = lIllIlIlIlIIll.lastDistance - lIllIlIlIlIIll.lastDistance / 159.8D;
            }

            lIllIlIlIlIIll.moveSpeed = Math.max(lIllIlIlIlIIll.moveSpeed, 0.3D);
            Exception lIllIlIlIIlIII = MovementUtils.getDirection();
            lIllIlIlIlIIII.setX(-Math.sin(lIllIlIlIIlIII) * lIllIlIlIlIIll.moveSpeed);
            lIllIlIlIlIIII.setZ(Math.cos(lIllIlIlIIlIII) * lIllIlIlIlIIll.moveSpeed);
            mc.thePlayer.motionX = lIllIlIlIlIIII.getX();
            mc.thePlayer.motionZ = lIllIlIlIlIIII.getZ();
         }
         break;
      case 3:
         if (!lIllIlIlIlIIll.freeHypixelTimer.hasTimePassed(10)) {
            lIllIlIlIlIIII.zero();
         }
      }

   }

   @EventTarget
   public void onRender3D(Render3DEvent lIllIllIIIIIII) {
      String lIllIlIlllllII = (String)lIllIllIIIIIIl.modeValue.get();
      if ((Boolean)lIllIllIIIIIIl.markValue.get() && !lIllIlIlllllII.equalsIgnoreCase("Vanilla") && !lIllIlIlllllII.equalsIgnoreCase("SmoothVanilla")) {
         double lIllIlIllllllI = lIllIllIIIIIIl.startY + 2.0D;
         RenderUtils.drawPlatform(lIllIlIllllllI, mc.thePlayer.getEntityBoundingBox().maxY < lIllIlIllllllI ? new Color(0, 255, 0, 90) : new Color(255, 0, 0, 90), 1.0D);
         long lIllIlIllllIlI = lIllIlIlllllII.toLowerCase();
         String lIllIlIllllIIl = -1;
         switch(lIllIlIllllIlI.hashCode()) {
         case 1435059604:
            if (lIllIlIllllIlI.equals("aac1.9.10")) {
               lIllIlIllllIIl = 0;
            }
            break;
         case 1492139162:
            if (lIllIlIllllIlI.equals("aac3.3.12")) {
               lIllIlIllllIIl = 1;
            }
         }

         switch(lIllIlIllllIIl) {
         case 0:
            RenderUtils.drawPlatform(lIllIllIIIIIIl.startY + lIllIllIIIIIIl.aacJump, new Color(0, 0, 255, 90), 1.0D);
            break;
         case 1:
            RenderUtils.drawPlatform(-70.0D, new Color(0, 0, 255, 90), 1.0D);
         }

      }
   }

   @EventTarget
   public void onBB(BlockBBEvent lIllIlIlIIIIII) {
      if (mc.thePlayer != null) {
         String lIllIlIlIIIIlI = (String)lIllIlIlIIIlII.modeValue.get();
         if (lIllIlIlIIIIII.getBlock() instanceof BlockAir && (lIllIlIlIIIIlI.equalsIgnoreCase("Hypixel") || lIllIlIlIIIIlI.equalsIgnoreCase("BoostHypixel") || lIllIlIlIIIIlI.equalsIgnoreCase("Zoom") || lIllIlIlIIIIlI.equalsIgnoreCase("Rewinside") || lIllIlIlIIIIlI.equalsIgnoreCase("Mineplex") && mc.thePlayer.inventory.getCurrentItem() == null) && (double)lIllIlIlIIIIII.getY() < mc.thePlayer.posY) {
            lIllIlIlIIIIII.setBoundingBox(AxisAlignedBB.fromBounds((double)lIllIlIlIIIIII.getX(), (double)lIllIlIlIIIIII.getY(), (double)lIllIlIlIIIIII.getZ(), (double)(lIllIlIlIIIIII.getX() + 1), mc.thePlayer.posY, (double)(lIllIlIlIIIIII.getZ() + 1)));
         }

      }
   }
}
