//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement;

import java.math.BigDecimal;
import java.math.RoundingMode;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.StepConfirmEvent;
import net.ccbluex.liquidbounce.event.StepEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.exploit.Phase;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.stats.StatList;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
   name = "Step",
   description = "Allows you to step up blocks.",
   category = ModuleCategory.MOVEMENT
)
@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u001b\u001a\u00020\bH\u0002J\b\u0010\u001c\u001a\u00020\u001dH\u0002J\b\u0010\u001e\u001a\u00020\u001dH\u0016J\u0010\u0010\u001f\u001a\u00020\u001d2\u0006\u0010 \u001a\u00020!H\u0007J\u0010\u0010\"\u001a\u00020\u001d2\u0006\u0010 \u001a\u00020#H\u0007J\u0010\u0010$\u001a\u00020\u001d2\u0006\u0010 \u001a\u00020%H\u0007J\u0010\u0010&\u001a\u00020\u001d2\u0006\u0010 \u001a\u00020'H\u0007J\u0010\u0010(\u001a\u00020\u001d2\u0006\u0010 \u001a\u00020)H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\u00020\u00158VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u000e\u0010\u0018\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006*"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/Step;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "delayValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "heightValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "isAACStep", "", "isStep", "jumpHeightValue", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "ncpNextStep", "", "spartanSwitch", "stepX", "", "stepY", "stepZ", "tag", "", "getTag", "()Ljava/lang/String;", "timeValue", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "couldStep", "fakeJump", "", "onDisable", "onMove", "event", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onStep", "Lnet/ccbluex/liquidbounce/event/StepEvent;", "onStepConfirm", "Lnet/ccbluex/liquidbounce/event/StepConfirmEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidSense"}
)
public final class Step extends Module {
   // $FF: synthetic field
   private int ncpNextStep;
   // $FF: synthetic field
   private double stepY;
   // $FF: synthetic field
   private final FloatValue heightValue = new FloatValue("Height", 1.0F, 0.6F, 10.0F);
   // $FF: synthetic field
   private boolean isStep;
   // $FF: synthetic field
   private boolean spartanSwitch;
   // $FF: synthetic field
   private boolean isAACStep;
   // $FF: synthetic field
   private double stepX;
   // $FF: synthetic field
   private double stepZ;
   // $FF: synthetic field
   private final FloatValue timeValue = new FloatValue("MotionTime", 0.42F, 0.1F, 1.0F);
   // $FF: synthetic field
   private final FloatValue jumpHeightValue = new FloatValue("JumpHeight", 0.42F, 0.37F, 0.42F);
   // $FF: synthetic field
   private final ListValue modeValue = new ListValue("Mode", new String[]{"Vanilla", "Jump", "NCP", "MotionNCP", "OldNCP", "AAC", "LAAC", "AAC3.3.4", "Spartan", "Rewinside"}, "NCP");
   // $FF: synthetic field
   private final IntegerValue delayValue = new IntegerValue("Delay", 0, 0, 500);
   // $FF: synthetic field
   private final MSTimer timer = new MSTimer();

   @EventTarget
   public final void onMove(@NotNull MoveEvent lIlIlIIIIlllIl) {
      Intrinsics.checkParameterIsNotNull(lIlIlIIIIlllIl, "event");
      Exception lIlIlIIIIllIlI = (String)lIlIlIIIIllllI.modeValue.get();
      EntityPlayerSP var10000 = access$getMc$p$s1046033730().thePlayer;
      if (var10000 == null) {
         boolean var10001 = false;
      } else {
         long lIlIlIIIIllIIl = var10000;
         if (StringsKt.equals(lIlIlIIIIllIlI, "motionncp", true) && lIlIlIIIIllIIl.isCollidedHorizontally) {
            KeyBinding var10 = access$getMc$p$s1046033730().gameSettings.keyBindJump;
            Intrinsics.checkExpressionValueIsNotNull(var10, "mc.gameSettings.keyBindJump");
            if (!var10.isKeyDown()) {
               if (lIlIlIIIIllIIl.onGround && lIlIlIIIIllllI.couldStep()) {
                  lIlIlIIIIllllI.fakeJump();
                  lIlIlIIIIllIIl.motionY = 0.0D;
                  lIlIlIIIIlllIl.setY(0.41999998688698D);
                  lIlIlIIIIllllI.ncpNextStep = 1;
               } else if (lIlIlIIIIllllI.ncpNextStep == 1) {
                  lIlIlIIIIlllIl.setY(0.33319999363422D);
                  lIlIlIIIIllllI.ncpNextStep = 2;
               } else if (lIlIlIIIIllllI.ncpNextStep == 2) {
                  short lIlIlIIIIllIII = MovementUtils.getDirection();
                  lIlIlIIIIlllIl.setY(0.24813599859094704D);
                  Exception lIlIlIIIIlIlll = false;
                  String lIlIlIIIIlIlIl = Math.sin(lIlIlIIIIllIII);
                  lIlIlIIIIlllIl.setX(-lIlIlIIIIlIlIl * 0.7D);
                  lIlIlIIIIlIlll = false;
                  lIlIlIIIIlIlIl = Math.cos(lIlIlIIIIllIII);
                  lIlIlIIIIlllIl.setZ(lIlIlIIIIlIlIl * 0.7D);
                  lIlIlIIIIllllI.ncpNextStep = 0;
               }
            }
         }

      }
   }

   public void onDisable() {
      EntityPlayerSP var10000 = access$getMc$p$s1046033730().thePlayer;
      if (var10000 != null) {
         EntityPlayerSP lIlIlIIllIIlll = var10000;
         lIlIlIIllIIlll.stepHeight = 0.5F;
      } else {
         boolean var10001 = false;
      }
   }

   @EventTarget
   public final void onStep(@NotNull StepEvent lIlIlIIIIIlIII) {
      Intrinsics.checkParameterIsNotNull(lIlIlIIIIIlIII, "event");
      EntityPlayerSP var10000 = access$getMc$p$s1046033730().thePlayer;
      if (var10000 != null) {
         boolean lIlIlIIIIIIlIl = var10000;
         Module var6 = LiquidBounce.INSTANCE.getModuleManager().get(Phase.class);
         if (var6 == null) {
            Intrinsics.throwNpe();
         }

         if (var6.getState()) {
            lIlIlIIIIIlIII.setStepHeight(0.0F);
         } else {
            var6 = LiquidBounce.INSTANCE.getModuleManager().get(Fly.class);
            if (var6 == null) {
               throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.movement.Fly");
            } else {
               int lIlIlIIIIIIlII = (Fly)var6;
               String lIlIlIIIIIllII;
               if (lIlIlIIIIIIlII.getState()) {
                  lIlIlIIIIIllII = (String)lIlIlIIIIIIlII.modeValue.get();
                  if (StringsKt.equals(lIlIlIIIIIllII, "Hypixel", true) || StringsKt.equals(lIlIlIIIIIllII, "OtherHypixel", true) || StringsKt.equals(lIlIlIIIIIllII, "LatestHypixel", true) || StringsKt.equals(lIlIlIIIIIllII, "Rewinside", true) || StringsKt.equals(lIlIlIIIIIllII, "Mineplex", true) && access$getMc$p$s1046033730().thePlayer.inventory.getCurrentItem() == null) {
                     lIlIlIIIIIlIII.setStepHeight(0.0F);
                     return;
                  }
               }

               lIlIlIIIIIllII = (String)lIlIlIIIIIlIIl.modeValue.get();
               if (lIlIlIIIIIIlIl.onGround && lIlIlIIIIIlIIl.timer.hasTimePassed((long)((Number)lIlIlIIIIIlIIl.delayValue.get()).intValue()) && !StringsKt.equals(lIlIlIIIIIllII, "Jump", true) && !StringsKt.equals(lIlIlIIIIIllII, "MotionNCP", true) && !StringsKt.equals(lIlIlIIIIIllII, "LAAC", true) && !StringsKt.equals(lIlIlIIIIIllII, "AAC3.3.4", true)) {
                  float lIlIlIIIIIllIl = ((Number)lIlIlIIIIIlIIl.heightValue.get()).floatValue();
                  lIlIlIIIIIIlIl.stepHeight = lIlIlIIIIIllIl;
                  lIlIlIIIIIlIII.setStepHeight(lIlIlIIIIIllIl);
                  if (lIlIlIIIIIlIII.getStepHeight() > 0.5F) {
                     lIlIlIIIIIlIIl.isStep = true;
                     lIlIlIIIIIlIIl.stepX = lIlIlIIIIIIlIl.posX;
                     lIlIlIIIIIlIIl.stepY = lIlIlIIIIIIlIl.posY;
                     lIlIlIIIIIlIIl.stepZ = lIlIlIIIIIIlIl.posZ;
                  }

               } else {
                  lIlIlIIIIIIlIl.stepHeight = 0.5F;
                  lIlIlIIIIIlIII.setStepHeight(0.5F);
               }
            }
         }
      } else {
         boolean var10001 = false;
      }
   }

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent lIlIlIIlIIIIIl) {
      Intrinsics.checkParameterIsNotNull(lIlIlIIlIIIIIl, "event");
      String lIlIlIIlIIIlII = (String)lIlIlIIlIIIIll.modeValue.get();
      EntityPlayerSP var10000 = access$getMc$p$s1046033730().thePlayer;
      if (var10000 == null) {
         boolean var10001 = false;
      } else {
         label67: {
            EntityPlayerSP lIlIlIIlIIIlIl = var10000;
            if (StringsKt.equals(lIlIlIIlIIIlII, "jump", true) && lIlIlIIlIIIlIl.isCollidedHorizontally && lIlIlIIlIIIlIl.onGround) {
               KeyBinding var11 = access$getMc$p$s1046033730().gameSettings.keyBindJump;
               Intrinsics.checkExpressionValueIsNotNull(var11, "mc.gameSettings.keyBindJump");
               if (!var11.isKeyDown()) {
                  lIlIlIIlIIIIll.fakeJump();
                  lIlIlIIlIIIlIl.motionY = (double)((Number)lIlIlIIlIIIIll.jumpHeightValue.get()).floatValue();
                  break label67;
               }
            }

            if (StringsKt.equals(lIlIlIIlIIIlII, "laac", true)) {
               if (lIlIlIIlIIIlIl.isCollidedHorizontally && !lIlIlIIlIIIlIl.isOnLadder() && !lIlIlIIlIIIlIl.isInWater() && !lIlIlIIlIIIlIl.isInLava() && !lIlIlIIlIIIlIl.isInWeb) {
                  if (lIlIlIIlIIIlIl.onGround && lIlIlIIlIIIIll.timer.hasTimePassed((long)((Number)lIlIlIIlIIIIll.delayValue.get()).intValue())) {
                     lIlIlIIlIIIIll.isStep = true;
                     lIlIlIIlIIIIll.fakeJump();
                     lIlIlIIlIIIlIl.motionY += 0.620000001490116D;
                     boolean lIlIlIIIlllIIl = lIlIlIIlIIIlIl.rotationYaw * 0.017453292F;
                     double lIlIlIIIllIIll = lIlIlIIlIIIlIl.motionX;
                     char lIlIlIIIllIlll = false;
                     double lIlIlIIIllIIIl = (float)Math.sin((double)lIlIlIIIlllIIl);
                     lIlIlIIlIIIlIl.motionX = lIlIlIIIllIIll - (double)lIlIlIIIllIIIl * 0.2D;
                     lIlIlIIIllIIll = lIlIlIIlIIIlIl.motionZ;
                     lIlIlIIIllIlll = false;
                     lIlIlIIIllIIIl = (float)Math.cos((double)lIlIlIIIlllIIl);
                     lIlIlIIlIIIlIl.motionZ = lIlIlIIIllIIll + (double)lIlIlIIIllIIIl * 0.2D;
                     lIlIlIIlIIIIll.timer.reset();
                  }

                  lIlIlIIlIIIlIl.onGround = true;
               } else {
                  lIlIlIIlIIIIll.isStep = false;
               }
            } else if (StringsKt.equals(lIlIlIIlIIIlII, "aac3.3.4", true)) {
               if (lIlIlIIlIIIlIl.isCollidedHorizontally && MovementUtils.isMoving()) {
                  if (lIlIlIIlIIIlIl.onGround && lIlIlIIlIIIIll.couldStep()) {
                     lIlIlIIlIIIlIl.motionX *= 1.26D;
                     lIlIlIIlIIIlIl.motionZ *= 1.26D;
                     lIlIlIIlIIIlIl.jump();
                     lIlIlIIlIIIIll.isAACStep = true;
                  }

                  if (lIlIlIIlIIIIll.isAACStep) {
                     lIlIlIIlIIIlIl.motionY -= 0.015D;
                     if (!lIlIlIIlIIIlIl.isUsingItem() && lIlIlIIlIIIlIl.movementInput.moveStrafe == 0.0F) {
                        lIlIlIIlIIIlIl.jumpMovementFactor = 0.3F;
                     }
                  }
               } else {
                  lIlIlIIlIIIIll.isAACStep = false;
               }
            }
         }

         if (lIlIlIIlIIIIll.stepY == (new BigDecimal(lIlIlIIlIIIIll.stepY)).setScale(3, RoundingMode.HALF_DOWN).doubleValue()) {
            access$getMc$p$s1046033730().timer.timerSpeed = 1.0F;
         }

      }
   }

   @EventTarget(
      ignoreCondition = true
   )
   public final void onStepConfirm(@NotNull StepConfirmEvent lIlIIllllllIlI) {
      Intrinsics.checkParameterIsNotNull(lIlIIllllllIlI, "event");
      EntityPlayerSP lIlIIlllllllII = access$getMc$p$s1046033730().thePlayer;
      if (lIlIIlllllllII != null && lIlIIllllllIIl.isStep) {
         if (lIlIIlllllllII.getEntityBoundingBox().minY - lIlIIllllllIIl.stepY > 0.5D) {
            String lIlIIlllllllIl = (String)lIlIIllllllIIl.modeValue.get();
            Minecraft var10000;
            if (!StringsKt.equals(lIlIIlllllllIl, "NCP", true) && !StringsKt.equals(lIlIIlllllllIl, "AAC", true)) {
               if (StringsKt.equals(lIlIIlllllllIl, "Spartan", true)) {
                  lIlIIllllllIIl.fakeJump();
                  if (lIlIIllllllIIl.spartanSwitch) {
                     var10000 = access$getMc$p$s1046033730();
                     Intrinsics.checkExpressionValueIsNotNull(var10000, "mc");
                     var10000.getNetHandler().addToSendQueue((Packet)(new C04PacketPlayerPosition(lIlIIllllllIIl.stepX, lIlIIllllllIIl.stepY + 0.41999998688698D, lIlIIllllllIIl.stepZ, false)));
                     var10000 = access$getMc$p$s1046033730();
                     Intrinsics.checkExpressionValueIsNotNull(var10000, "mc");
                     var10000.getNetHandler().addToSendQueue((Packet)(new C04PacketPlayerPosition(lIlIIllllllIIl.stepX, lIlIIllllllIIl.stepY + 0.7531999805212D, lIlIIllllllIIl.stepZ, false)));
                     var10000 = access$getMc$p$s1046033730();
                     Intrinsics.checkExpressionValueIsNotNull(var10000, "mc");
                     var10000.getNetHandler().addToSendQueue((Packet)(new C04PacketPlayerPosition(lIlIIllllllIIl.stepX, lIlIIllllllIIl.stepY + 1.001335979112147D, lIlIIllllllIIl.stepZ, false)));
                  } else {
                     var10000 = access$getMc$p$s1046033730();
                     Intrinsics.checkExpressionValueIsNotNull(var10000, "mc");
                     var10000.getNetHandler().addToSendQueue((Packet)(new C04PacketPlayerPosition(lIlIIllllllIIl.stepX, lIlIIllllllIIl.stepY + 0.6D, lIlIIllllllIIl.stepZ, false)));
                  }

                  lIlIIllllllIIl.spartanSwitch = !lIlIIllllllIIl.spartanSwitch;
                  lIlIIllllllIIl.timer.reset();
               } else if (StringsKt.equals(lIlIIlllllllIl, "Rewinside", true)) {
                  lIlIIllllllIIl.fakeJump();
                  var10000 = access$getMc$p$s1046033730();
                  Intrinsics.checkExpressionValueIsNotNull(var10000, "mc");
                  var10000.getNetHandler().addToSendQueue((Packet)(new C04PacketPlayerPosition(lIlIIllllllIIl.stepX, lIlIIllllllIIl.stepY + 0.41999998688698D, lIlIIllllllIIl.stepZ, false)));
                  var10000 = access$getMc$p$s1046033730();
                  Intrinsics.checkExpressionValueIsNotNull(var10000, "mc");
                  var10000.getNetHandler().addToSendQueue((Packet)(new C04PacketPlayerPosition(lIlIIllllllIIl.stepX, lIlIIllllllIIl.stepY + 0.7531999805212D, lIlIIllllllIIl.stepZ, false)));
                  var10000 = access$getMc$p$s1046033730();
                  Intrinsics.checkExpressionValueIsNotNull(var10000, "mc");
                  var10000.getNetHandler().addToSendQueue((Packet)(new C04PacketPlayerPosition(lIlIIllllllIIl.stepX, lIlIIllllllIIl.stepY + 1.001335979112147D, lIlIIllllllIIl.stepZ, false)));
                  lIlIIllllllIIl.timer.reset();
               }
            } else {
               lIlIIllllllIIl.fakeJump();
               access$getMc$p$s1046033730().timer.timerSpeed = ((Number)lIlIIllllllIIl.timeValue.get()).floatValue();
               var10000 = access$getMc$p$s1046033730();
               Intrinsics.checkExpressionValueIsNotNull(var10000, "mc");
               var10000.getNetHandler().addToSendQueue((Packet)(new C04PacketPlayerPosition(lIlIIllllllIIl.stepX, lIlIIllllllIIl.stepY + 0.41999998688698D, lIlIIllllllIIl.stepZ, false)));
               var10000 = access$getMc$p$s1046033730();
               Intrinsics.checkExpressionValueIsNotNull(var10000, "mc");
               var10000.getNetHandler().addToSendQueue((Packet)(new C04PacketPlayerPosition(lIlIIllllllIIl.stepX, lIlIIllllllIIl.stepY + 0.7531999805212D, lIlIIllllllIIl.stepZ, false)));
               lIlIIllllllIIl.timer.reset();
            }
         }

         lIlIIllllllIIl.isStep = false;
         lIlIIllllllIIl.stepX = 0.0D;
         lIlIIllllllIIl.stepY = 0.0D;
         lIlIIllllllIIl.stepZ = 0.0D;
      }
   }

   @EventTarget(
      ignoreCondition = true
   )
   public final void onPacket(@NotNull PacketEvent lIlIIllllIlllI) {
      Intrinsics.checkParameterIsNotNull(lIlIIllllIlllI, "event");
      Packet lIlIIlllllIIlI = lIlIIllllIlllI.getPacket();
      if (lIlIIlllllIIlI instanceof C03PacketPlayer && lIlIIllllIllll.isStep && StringsKt.equals((String)lIlIIllllIllll.modeValue.get(), "OldNCP", true)) {
         ((C03PacketPlayer)lIlIIlllllIIlI).y += 0.07D;
         lIlIIllllIllll.isStep = false;
      }

   }

   private final void fakeJump() {
      EntityPlayerSP var10000 = access$getMc$p$s1046033730().thePlayer;
      if (var10000 != null) {
         int lIlIIllllIlIIl = var10000;
         lIlIIllllIlIIl.isAirBorne = true;
         lIlIIllllIlIIl.triggerAchievement(StatList.jumpStat);
      } else {
         boolean var10001 = false;
      }
   }

   private final boolean couldStep() {
      boolean lIlIIllllIIIII = MovementUtils.getDirection();
      String lIlIIlllIllllI = false;
      double lIlIIllllIIIll = -Math.sin(lIlIIllllIIIII) * 0.4D;
      Exception lIlIIlllIlllIl = false;
      String lIlIIlllIllllI = Math.cos(lIlIIllllIIIII) * 0.4D;
      WorldClient var10000 = access$getMc$p$s1046033730().theWorld;
      if (var10000 == null) {
         Intrinsics.throwNpe();
      }

      EntityPlayerSP var10001 = access$getMc$p$s1046033730().thePlayer;
      if (var10001 == null) {
         Intrinsics.throwNpe();
      }

      return var10000.getCollisionBoxes(var10001.getEntityBoundingBox().offset(lIlIIllllIIIll, 1.001335979112147D, lIlIIlllIllllI)).isEmpty();
   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   @NotNull
   public String getTag() {
      return (String)lIlIIlllIllIlI.modeValue.get();
   }
}
