//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement;

import java.util.ArrayList;
import java.util.List;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.TickEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.AAC2BHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.AAC3BHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.AAC4BHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.AAC5BHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.AAC6BHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.AAC7BHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.AACBHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.AACGround;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.AACGround2;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.AACHop3313;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.AACHop350;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.AACLowHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.AACLowHop2;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.AACLowHop3;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.AACPort;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.AACYPort;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.AACYPort2;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.OldAACBHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aquavit.AAC4Hop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aquavit.Hypixel;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aquavit.NewAAC4Hop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aquavit.SlowDown;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp.Boost;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp.Frame;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp.MiJump;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp.NCPBHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp.NCPFHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp.NCPHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp.NCPYPort;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp.OnGround;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp.SNCPBHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp.YPort;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp.YPort2;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.other.CustomSpeed;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.other.HiveHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.other.HypixelHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.other.MineplexGround;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.other.SlowHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.other.TeleportCubeCraft;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.spartan.SpartanYPort;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.spectre.SpectreBHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.spectre.SpectreLowHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.spectre.SpectreOnGround;
import net.ccbluex.liquidbounce.features.module.modules.world.Scaffold;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

@ModuleInfo(
   name = "Speed",
   description = "Allows you to move faster.",
   category = ModuleCategory.MOVEMENT
)
public class Speed extends Module {
   // $FF: synthetic field
   public float speed = 0.0F;
   // $FF: synthetic field
   public final FloatValue customTimerValue;
   // $FF: synthetic field
   public final ListValue modeValue;
   // $FF: synthetic field
   public final BoolValue resetXZValue;
   // $FF: synthetic field
   public final BoolValue customStrafeValue;
   // $FF: synthetic field
   public final BoolValue resetYValue;
   // $FF: synthetic field
   public final BoolValue stiarcheckValue;
   // $FF: synthetic field
   public final FloatValue customSpeedValue;
   // $FF: synthetic field
   public final FloatValue aacGroundTimerValue;
   // $FF: synthetic field
   public final FloatValue cubecraftPortLengthValue;
   // $FF: synthetic field
   public final FloatValue portMax;
   // $FF: synthetic field
   public final FloatValue mineplexGroundSpeedValue;
   // $FF: synthetic field
   public final FloatValue customYValue;
   // $FF: synthetic field
   public int stoptick;
   // $FF: synthetic field
   private final SpeedMode[] speedModes = new SpeedMode[]{new NCPBHop(), new NCPFHop(), new SNCPBHop(), new NCPHop(), new YPort(), new YPort2(), new NCPYPort(), new Boost(), new Frame(), new MiJump(), new OnGround(), new AACBHop(), new AAC2BHop(), new AAC3BHop(), new AAC4BHop(), new AAC5BHop(), new AAC6BHop(), new AAC7BHop(), new AACHop3313(), new AACHop350(), new AACLowHop(), new AACLowHop2(), new AACLowHop3(), new AACGround(), new AACGround2(), new AACYPort(), new AACYPort2(), new AACPort(), new OldAACBHop(), new SpartanYPort(), new SpectreLowHop(), new SpectreBHop(), new SpectreOnGround(), new TeleportCubeCraft(), new HiveHop(), new HypixelHop(), new MineplexGround(), new SlowHop(), new CustomSpeed(), new AAC4Hop(), new SlowDown(), new NewAAC4Hop(), new Hypixel()};

   public void onDisable() {
      if (mc.thePlayer != null) {
         mc.timer.timerSpeed = 1.0F;
         SpeedMode llllllllllllllllllIIlIlllIIIIlIl = llllllllllllllllllIIlIlllIIIIlII.getMode();
         if (llllllllllllllllllIIlIlllIIIIlIl != null) {
            llllllllllllllllllIIlIlllIIIIlIl.onDisable();
         }

      }
   }

   @EventTarget
   public void onMotion(MotionEvent llllllllllllllllllIIlIlllIlIIIII) {
      if (!mc.thePlayer.isSneaking() && llllllllllllllllllIIlIlllIlIIIII.getEventState() == EventState.PRE) {
         if (MovementUtils.isMoving()) {
            mc.thePlayer.setSprinting(true);
         }

         char llllllllllllllllllIIlIlllIIlllll = llllllllllllllllllIIlIlllIlIIIIl.getMode();
         if (llllllllllllllllllIIlIlllIIlllll != null) {
            llllllllllllllllllIIlIlllIIlllll.onMotion();
         }

      }
   }

   private SpeedMode getMode() {
      double llllllllllllllllllIIlIllIllIllII = (String)llllllllllllllllllIIlIllIllIllIl.modeValue.get();
      Exception llllllllllllllllllIIlIllIllIlIll = llllllllllllllllllIIlIllIllIllIl.speedModes;
      short llllllllllllllllllIIlIllIllIlIlI = llllllllllllllllllIIlIllIllIlIll.length;

      for(int llllllllllllllllllIIlIllIllIlIIl = 0; llllllllllllllllllIIlIllIllIlIIl < llllllllllllllllllIIlIllIllIlIlI; ++llllllllllllllllllIIlIllIllIlIIl) {
         Exception llllllllllllllllllIIlIllIllIlIII = llllllllllllllllllIIlIllIllIlIll[llllllllllllllllllIIlIllIllIlIIl];
         if (llllllllllllllllllIIlIllIllIlIII.modeName.equalsIgnoreCase(llllllllllllllllllIIlIllIllIllII)) {
            return llllllllllllllllllIIlIllIllIlIII;
         }
      }

      return null;
   }

   @EventTarget
   public void onMove(MoveEvent llllllllllllllllllIIlIlllIIllIlI) {
      if (llllllllllllllllllIIlIlllIIllIII.stoptick > 0) {
         mc.thePlayer.jumpMovementFactor = 0.0F;
         mc.thePlayer.horseJumpPower = 0.0F;
         mc.thePlayer.onGround = true;
      }

      if (!mc.thePlayer.isSneaking() && llllllllllllllllllIIlIlllIIllIII.stoptick <= 0 && !LiquidBounce.moduleManager.get(Scaffold.class).getState()) {
         short llllllllllllllllllIIlIlllIIlIllI = llllllllllllllllllIIlIlllIIllIII.getMode();
         if (llllllllllllllllllIIlIlllIIlIllI != null) {
            llllllllllllllllllIIlIlllIIlIllI.onMove(llllllllllllllllllIIlIlllIIllIlI);
         }

      }
   }

   public void onEnable() {
      if (mc.thePlayer != null) {
         mc.timer.timerSpeed = 1.0F;
         SpeedMode llllllllllllllllllIIlIlllIIIlIll = llllllllllllllllllIIlIlllIIIlIlI.getMode();
         if (llllllllllllllllllIIlIlllIIIlIll != null) {
            llllllllllllllllllIIlIlllIIIlIll.onEnable();
         }

      }
   }

   public float getspeed() {
      return llllllllllllllllllIIlIlllIlIllll.speed;
   }

   public float setspeed(float llllllllllllllllllIIlIlllIllIlII) {
      return llllllllllllllllllIIlIlllIllIlIl.speed = llllllllllllllllllIIlIlllIllIlII;
   }

   public Speed() {
      llllllllllllllllllIIlIlllIlllIIl.modeValue = new ListValue("Mode", llllllllllllllllllIIlIlllIlllIIl.getModes(), "NCPBHop") {
         protected void onChanged(String lllllllllllllllllllIlIlIlIIIllll, String lllllllllllllllllllIlIlIlIIIlllI) {
            if (llllllllllllllllllIIlIlllIlllIIl.getState()) {
               llllllllllllllllllIIlIlllIlllIIl.onEnable();
            }

         }

         protected void onChange(String lllllllllllllllllllIlIlIlIIllIII, String lllllllllllllllllllIlIlIlIIlIlll) {
            if (llllllllllllllllllIIlIlllIlllIIl.getState()) {
               llllllllllllllllllIIlIlllIlllIIl.onDisable();
            }

         }
      };
      llllllllllllllllllIIlIlllIlllIIl.customSpeedValue = new FloatValue("CustomSpeed", 1.6F, 0.2F, 2.0F);
      llllllllllllllllllIIlIlllIlllIIl.customYValue = new FloatValue("CustomY", 0.0F, 0.0F, 4.0F);
      llllllllllllllllllIIlIlllIlllIIl.customTimerValue = new FloatValue("CustomTimer", 1.0F, 0.1F, 2.0F);
      llllllllllllllllllIIlIlllIlllIIl.customStrafeValue = new BoolValue("CustomStrafe", true);
      llllllllllllllllllIIlIlllIlllIIl.resetXZValue = new BoolValue("CustomResetXZ", false);
      llllllllllllllllllIIlIlllIlllIIl.resetYValue = new BoolValue("CustomResetY", false);
      llllllllllllllllllIIlIlllIlllIIl.portMax = new FloatValue("AAC-PortLength", 1.0F, 1.0F, 20.0F);
      llllllllllllllllllIIlIlllIlllIIl.aacGroundTimerValue = new FloatValue("AACGround-Timer", 3.0F, 1.1F, 10.0F);
      llllllllllllllllllIIlIlllIlllIIl.cubecraftPortLengthValue = new FloatValue("CubeCraft-PortLength", 1.0F, 0.1F, 2.0F);
      llllllllllllllllllIIlIlllIlllIIl.mineplexGroundSpeedValue = new FloatValue("MineplexGround-Speed", 0.5F, 0.1F, 1.0F);
      llllllllllllllllllIIlIlllIlllIIl.stiarcheckValue = new BoolValue("StairCheck", false);
   }

   @EventTarget
   public void onPacket(PacketEvent llllllllllllllllllIIlIllIllllIlI) {
      if (llllllllllllllllllIIlIllIllllIlI.getPacket() instanceof S08PacketPlayerPosLook) {
         S08PacketPlayerPosLook llllllllllllllllllIIlIllIlllllII = (S08PacketPlayerPosLook)llllllllllllllllllIIlIllIllllIlI.getPacket();
         llllllllllllllllllIIlIllIlllllII.yaw = mc.thePlayer.rotationYaw;
         llllllllllllllllllIIlIllIlllllII.pitch = mc.thePlayer.rotationPitch;
         llllllllllllllllllIIlIllIllllIll.stoptick = 100;
      } else if (llllllllllllllllllIIlIllIllllIll.stoptick > 0) {
         --llllllllllllllllllIIlIllIllllIll.stoptick;
      }

   }

   private String[] getModes() {
      List<String> llllllllllllllllllIIlIllIlIlllll = new ArrayList();
      float llllllllllllllllllIIlIllIlIlllII = llllllllllllllllllIIlIllIlIllllI.speedModes;
      byte llllllllllllllllllIIlIllIlIllIll = llllllllllllllllllIIlIllIlIlllII.length;

      for(int llllllllllllllllllIIlIllIlIllIlI = 0; llllllllllllllllllIIlIllIlIllIlI < llllllllllllllllllIIlIllIlIllIll; ++llllllllllllllllllIIlIllIlIllIlI) {
         SpeedMode llllllllllllllllllIIlIllIllIIIIl = llllllllllllllllllIIlIllIlIlllII[llllllllllllllllllIIlIllIlIllIlI];
         llllllllllllllllllIIlIllIlIlllll.add(llllllllllllllllllIIlIllIllIIIIl.modeName);
         boolean var10001 = false;
      }

      return (String[])llllllllllllllllllIIlIllIlIlllll.toArray(new String[0]);
   }

   @EventTarget
   public void onUpdate(UpdateEvent llllllllllllllllllIIlIlllIlIlIll) {
      if (!mc.thePlayer.isSneaking()) {
         if (MovementUtils.isMoving()) {
            mc.thePlayer.setSprinting(true);
         }

         SpeedMode llllllllllllllllllIIlIlllIlIlIlI = llllllllllllllllllIIlIlllIlIllII.getMode();
         if (llllllllllllllllllIIlIlllIlIlIlI != null) {
            llllllllllllllllllIIlIlllIlIlIlI.onUpdate();
         }

      }
   }

   public String getTag() {
      return (String)llllllllllllllllllIIlIlllIIIIIIl.modeValue.get();
   }

   @EventTarget
   public void onTick(TickEvent llllllllllllllllllIIlIlllIIlIIlI) {
      if (!mc.thePlayer.isSneaking()) {
         byte llllllllllllllllllIIlIlllIIIllll = llllllllllllllllllIIlIlllIIlIIII.getMode();
         if (llllllllllllllllllIIlIlllIIIllll != null) {
            llllllllllllllllllIIlIlllIIIllll.onTick();
         }

      }
   }
}
