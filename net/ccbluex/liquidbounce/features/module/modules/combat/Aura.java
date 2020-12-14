//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.combat;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.EntityMovementEvent;
import net.ccbluex.liquidbounce.event.Event;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.StrafeEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.misc.AntiBot;
import net.ccbluex.liquidbounce.features.module.modules.misc.Teams;
import net.ccbluex.liquidbounce.features.module.modules.player.Blink;
import net.ccbluex.liquidbounce.features.module.modules.render.FreeCam;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.OtherRotationUtils;
import net.ccbluex.liquidbounce.utils.RaycastUtils;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.VecRotation;
import net.ccbluex.liquidbounce.utils.extensions.PlayerExtensionKt;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.network.play.client.C16PacketClientStatus.EnumState;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldSettings.GameType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0096\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u0007\n\u0002\b\u000b\n\u0002\u0010!\n\u0002\b\f\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010W\u001a\u00020X2\u0006\u0010Y\u001a\u00020$H\u0002J\u0010\u0010Z\u001a\u0002042\u0006\u0010Y\u001a\u00020[H\u0002J\u0010\u0010\\\u001a\u00020\b2\u0006\u0010Y\u001a\u00020$H\u0002J\u0012\u0010]\u001a\u00020\b2\b\u0010Y\u001a\u0004\u0018\u00010[H\u0002J\b\u0010^\u001a\u00020XH\u0016J\b\u0010_\u001a\u00020XH\u0016J\u0010\u0010`\u001a\u00020X2\u0006\u0010a\u001a\u00020bH\u0007J\u0010\u0010c\u001a\u00020X2\u0006\u0010a\u001a\u00020dH\u0007J\u0010\u0010e\u001a\u00020X2\u0006\u0010a\u001a\u00020fH\u0007J\u0010\u0010g\u001a\u00020X2\u0006\u0010a\u001a\u00020hH\u0007J\u0010\u0010i\u001a\u00020X2\u0006\u0010a\u001a\u00020jH\u0007J\b\u0010k\u001a\u00020XH\u0002J\u0018\u0010l\u001a\u00020X2\u0006\u0010m\u001a\u00020[2\u0006\u0010n\u001a\u00020\bH\u0002J\b\u0010o\u001a\u00020XH\u0002J\u0006\u0010p\u001a\u00020XJ\b\u0010q\u001a\u00020XH\u0002J\u0010\u0010r\u001a\u00020\b2\u0006\u0010Y\u001a\u00020[H\u0002J\b\u0010s\u001a\u00020XH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0019\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\n\"\u0004\b\u001b\u0010\fR\u0014\u0010\u001c\u001a\u00020\b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\nR\u0014\u0010\u001e\u001a\u00020\b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010\nR\u000e\u0010 \u001a\u00020!X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010#\u001a\u0004\u0018\u00010$X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010.\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00100\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00101\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00102\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u00103\u001a\u0002048BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b5\u00106R\u000e\u00107\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00108\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00109\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010:\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010;\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010<\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010=\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010>\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010?\u001a\b\u0012\u0004\u0012\u00020!0@X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010A\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010B\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010C\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010D\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010E\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010F\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010G\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010H\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010I\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010J\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010K\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010L\u001a\u0004\u0018\u00010M8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bN\u0010OR\u001c\u0010P\u001a\u0004\u0018\u00010$X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bQ\u0010R\"\u0004\bS\u0010TR\u000e\u0010U\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010V\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006t"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/Aura;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "AutoBlockValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "BlockRangeValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "Rotationhit", "", "getRotationhit", "()Z", "setRotationhit", "(Z)V", "Timer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "getTimer", "()Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "aacValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "attackDelay", "", "attackTimer", "autoBlockValue", "blockRate", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "blockingStatus", "getBlockingStatus", "setBlockingStatus", "canBlock", "getCanBlock", "cancelRun", "getCancelRun", "clicks", "", "containerOpen", "currentTarget", "Lnet/minecraft/entity/EntityLivingBase;", "delayedBlockValue", "failRateValue", "fakeSharpValue", "fakeSwingValue", "fovValue", "hitable", "hurtTimeValue", "interactAutoBlockValue", "keepSprintValue", "limitedMultiTargetsValue", "livingRaycastValue", "markValue", "maxCPS", "maxPredictSize", "maxRange", "", "getMaxRange", "()F", "maxTurnSpeed", "minCPS", "minPredictSize", "minTurnSpeed", "noInventoryAttackValue", "noInventoryDelayValue", "outborderValue", "predictValue", "prevTargetEntities", "", "priorityValue", "randomCenterValue", "rangeSprintReducementValue", "rangeValue", "raycastIgnoredValue", "raycastValue", "rotationStrafeValue", "rotations", "silentRotationValue", "swingValue", "switchdelayValue", "tag", "", "getTag", "()Ljava/lang/String;", "target", "getTarget", "()Lnet/minecraft/entity/EntityLivingBase;", "setTarget", "(Lnet/minecraft/entity/EntityLivingBase;)V", "targetModeValue", "throughwallValue", "attackEntity", "", "entity", "getRange", "Lnet/minecraft/entity/Entity;", "isAlive", "isEnemy", "onDisable", "onEnable", "onEntityMove", "event", "Lnet/ccbluex/liquidbounce/event/EntityMovementEvent;", "onMotion", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onRender3D", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onStrafe", "Lnet/ccbluex/liquidbounce/event/StrafeEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "runAttack", "startBlocking", "interactEntity", "interact", "stopBlocking", "update", "updateHitable", "updateRotations", "updateTarget", "LiquidSense"}
)
@ModuleInfo(
   name = "Aura",
   description = "Automatically attacks targets around you.",
   category = ModuleCategory.COMBAT,
   keyBind = 19
)
public final class Aura extends Module {
   // $FF: synthetic field
   private final FloatValue failRateValue;
   // $FF: synthetic field
   private final ListValue priorityValue;
   // $FF: synthetic field
   private final MSTimer attackTimer;
   // $FF: synthetic field
   private final FloatValue minPredictSize;
   // $FF: synthetic field
   private long attackDelay;
   // $FF: synthetic field
   private final BoolValue randomCenterValue;
   // $FF: synthetic field
   private int clicks;
   // $FF: synthetic field
   private final BoolValue silentRotationValue;
   // $FF: synthetic field
   private final FloatValue fovValue;
   // $FF: synthetic field
   private final BoolValue markValue;
   // $FF: synthetic field
   private final ListValue rotations;
   // $FF: synthetic field
   private final ListValue AutoBlockValue;
   // $FF: synthetic field
   private final BoolValue interactAutoBlockValue;
   // $FF: synthetic field
   private final BoolValue swingValue;
   // $FF: synthetic field
   private final FloatValue maxPredictSize;
   // $FF: synthetic field
   private final FloatValue throughwallValue;
   // $FF: synthetic field
   private final IntegerValue limitedMultiTargetsValue;
   // $FF: synthetic field
   private final IntegerValue blockRate;
   // $FF: synthetic field
   private final BoolValue livingRaycastValue;
   // $FF: synthetic field
   private boolean Rotationhit;
   // $FF: synthetic field
   private final ListValue targetModeValue;
   // $FF: synthetic field
   private final FloatValue rangeSprintReducementValue;
   // $FF: synthetic field
   private final FloatValue maxTurnSpeed;
   // $FF: synthetic field
   private final BoolValue keepSprintValue;
   // $FF: synthetic field
   private final FloatValue BlockRangeValue;
   // $FF: synthetic field
   private final IntegerValue noInventoryDelayValue;
   // $FF: synthetic field
   private final BoolValue raycastIgnoredValue;
   // $FF: synthetic field
   private final IntegerValue minCPS;
   // $FF: synthetic field
   private EntityLivingBase currentTarget;
   // $FF: synthetic field
   private final BoolValue outborderValue;
   // $FF: synthetic field
   private final BoolValue fakeSwingValue;
   // $FF: synthetic field
   private long containerOpen;
   // $FF: synthetic field
   private final BoolValue aacValue;
   // $FF: synthetic field
   private final BoolValue autoBlockValue;
   // $FF: synthetic field
   private final FloatValue minTurnSpeed;
   // $FF: synthetic field
   private final BoolValue raycastValue;
   // $FF: synthetic field
   @Nullable
   private EntityLivingBase target;
   // $FF: synthetic field
   private final IntegerValue maxCPS;
   // $FF: synthetic field
   private final BoolValue predictValue;
   // $FF: synthetic field
   @NotNull
   private final MSTimer Timer;
   // $FF: synthetic field
   private final BoolValue fakeSharpValue;
   // $FF: synthetic field
   private final IntegerValue switchdelayValue;
   // $FF: synthetic field
   private final List<Integer> prevTargetEntities;
   // $FF: synthetic field
   private final FloatValue rangeValue;
   // $FF: synthetic field
   private final BoolValue delayedBlockValue;
   // $FF: synthetic field
   private final BoolValue noInventoryAttackValue;
   // $FF: synthetic field
   private boolean hitable;
   // $FF: synthetic field
   private final IntegerValue hurtTimeValue;
   // $FF: synthetic field
   private final ListValue rotationStrafeValue;
   // $FF: synthetic field
   private boolean blockingStatus;

   private final boolean updateRotations(Entity lIlIlIl) {
      AxisAlignedBB lIllIIl = lIlIlIl.getEntityBoundingBox();
      Vec3 lIlIIlI;
      Rotation lIlllII;
      VecRotation lIlIIII;
      Rotation lIlIIII;
      VecRotation var12;
      EntityPlayerSP var13;
      boolean var15;
      Rotation var10000;
      boolean var10001;
      boolean var10003;
      EntityPlayerSP var10004;
      if (StringsKt.equals((String)lIlIllI.rotations.get(), "Vanilla", true)) {
         if (((Number)lIlIllI.maxTurnSpeed.get()).floatValue() <= 0.0F) {
            return true;
         } else {
            boolean lIlIIll = lIlIlIl.getEntityBoundingBox();
            if ((Boolean)lIlIllI.predictValue.get()) {
               lIlIIll = lIlIIll.offset((lIlIlIl.posX - lIlIlIl.prevPosX) * (double)RandomUtils.nextFloat(((Number)lIlIllI.minPredictSize.get()).floatValue(), ((Number)lIlIllI.maxPredictSize.get()).floatValue()), (lIlIlIl.posY - lIlIlIl.prevPosY) * (double)RandomUtils.nextFloat(((Number)lIlIllI.minPredictSize.get()).floatValue(), ((Number)lIlIllI.maxPredictSize.get()).floatValue()), (lIlIlIl.posZ - lIlIlIl.prevPosZ) * (double)RandomUtils.nextFloat(((Number)lIlIllI.minPredictSize.get()).floatValue(), ((Number)lIlIllI.maxPredictSize.get()).floatValue()));
            }

            var10001 = (Boolean)lIlIllI.outborderValue.get() && !lIlIllI.attackTimer.hasTimePassed(lIlIllI.attackDelay / (long)2);
            var15 = (Boolean)lIlIllI.randomCenterValue.get();
            var10003 = (Boolean)lIlIllI.predictValue.get();
            var10004 = access$getMc$p$s1046033730().thePlayer;
            Intrinsics.checkExpressionValueIsNotNull(var10004, "mc.thePlayer");
            var12 = RotationUtils.searchCenter(lIlIIll, var10001, var15, var10003, PlayerExtensionKt.getDistanceToEntityBox((Entity)var10004, lIlIlIl) < ((Number)lIlIllI.throughwallValue.get()).doubleValue(), lIlIllI.getMaxRange());
            if (var12 != null) {
               lIlIIII = var12;
               lIlIIlI = lIlIIII.component1();
               lIlllII = lIlIIII.component2();
               var10000 = RotationUtils.limitAngleChange(RotationUtils.serverRotation, lIlllII, (float)(Math.random() * (double)(((Number)lIlIllI.maxTurnSpeed.get()).floatValue() - ((Number)lIlIllI.minTurnSpeed.get()).floatValue()) + ((Number)lIlIllI.minTurnSpeed.get()).doubleValue()));
               Intrinsics.checkExpressionValueIsNotNull(var10000, "RotationUtils.limitAngle…rnSpeed.get()).toFloat())");
               lIlIIII = var10000;
               EntityPlayerSP var14 = access$getMc$p$s1046033730().thePlayer;
               Intrinsics.checkExpressionValueIsNotNull(var14, "mc.thePlayer");
               double llIIIll = PlayerExtensionKt.getDistanceToEntityBox((Entity)var14, lIlIlIl);
               if (llIIIll < ((Number)lIlIllI.rangeValue.get()).doubleValue()) {
                  if ((Boolean)lIlIllI.silentRotationValue.get()) {
                     RotationUtils.setTargetRotation(lIlIIII, (Boolean)lIlIllI.aacValue.get() ? 15 : 0);
                  } else {
                     var13 = access$getMc$p$s1046033730().thePlayer;
                     Intrinsics.checkExpressionValueIsNotNull(var13, "mc.thePlayer");
                     lIlIIII.toPlayer((EntityPlayer)var13);
                  }
               }

               return true;
            } else {
               var10001 = false;
               return false;
            }
         }
      } else {
         if (StringsKt.equals((String)lIlIllI.rotations.get(), "BackTrack", true)) {
            if ((Boolean)lIlIllI.predictValue.get()) {
               lIllIIl = lIllIIl.offset((lIlIlIl.posX - lIlIlIl.prevPosX) * (double)RandomUtils.nextFloat(((Number)lIlIllI.minPredictSize.get()).floatValue(), ((Number)lIlIllI.maxPredictSize.get()).floatValue()), (lIlIlIl.posY - lIlIlIl.prevPosY) * (double)RandomUtils.nextFloat(((Number)lIlIllI.minPredictSize.get()).floatValue(), ((Number)lIlIllI.maxPredictSize.get()).floatValue()), (lIlIlIl.posZ - lIlIlIl.prevPosZ) * (double)RandomUtils.nextFloat(((Number)lIlIllI.minPredictSize.get()).floatValue(), ((Number)lIlIllI.maxPredictSize.get()).floatValue()));
            }

            var10000 = RotationUtils.serverRotation;
            Vec3 var10002 = RotationUtils.getCenter(lIlIlIl.getEntityBoundingBox());
            var10003 = (Boolean)lIlIllI.predictValue.get();
            var10004 = access$getMc$p$s1046033730().thePlayer;
            Intrinsics.checkExpressionValueIsNotNull(var10004, "mc.thePlayer");
            var10000 = RotationUtils.limitAngleChange(var10000, RotationUtils.OtherRotation(lIllIIl, var10002, var10003, PlayerExtensionKt.getDistanceToEntityBox((Entity)var10004, lIlIlIl) < ((Number)lIlIllI.throughwallValue.get()).doubleValue(), lIlIllI.getMaxRange()), (float)(Math.random() * (double)(((Number)lIlIllI.maxTurnSpeed.get()).floatValue() - ((Number)lIlIllI.minTurnSpeed.get()).floatValue()) + ((Number)lIlIllI.minTurnSpeed.get()).doubleValue()));
            Intrinsics.checkExpressionValueIsNotNull(var10000, "RotationUtils.limitAngle…rnSpeed.get()).toFloat())");
            boolean lIlIIll = var10000;
            if (!(Boolean)lIlIllI.silentRotationValue.get()) {
               var13 = access$getMc$p$s1046033730().thePlayer;
               Intrinsics.checkExpressionValueIsNotNull(var13, "mc.thePlayer");
               lIlIIll.toPlayer((EntityPlayer)var13);
               return true;
            }

            RotationUtils.setTargetRotation(lIlIIll, (Boolean)lIlIllI.aacValue.get() ? 15 : 0);
         }

         if (!StringsKt.equals((String)lIlIllI.rotations.get(), "Other", true)) {
            return true;
         } else {
            boolean lIlIIll = access$getMc$p$s1046033730().thePlayer.getDistanceToEntity(lIlIlIl);
            var10001 = (Boolean)lIlIllI.outborderValue.get() && !lIlIllI.attackTimer.hasTimePassed(lIlIllI.attackDelay / (long)2);
            var15 = (Boolean)lIlIllI.randomCenterValue.get();
            var10003 = (Boolean)lIlIllI.predictValue.get();
            var10004 = access$getMc$p$s1046033730().thePlayer;
            Intrinsics.checkExpressionValueIsNotNull(var10004, "mc.thePlayer");
            var12 = OtherRotationUtils.searchCenter(lIllIIl, var10001, var15, var10003, PlayerExtensionKt.getDistanceToEntityBox((Entity)var10004, lIlIlIl) < ((Number)lIlIllI.throughwallValue.get()).doubleValue(), lIlIllI.getMaxRange());
            if (var12 == null) {
               var10001 = false;
               return false;
            } else {
               lIlIIII = var12;
               lIlIIlI = lIlIIII.component1();
               lIlllII = lIlIIII.component2();
               var10000 = RotationUtils.limitAngleChange(RotationUtils.serverRotation, lIlllII, (float)(Math.random() * (double)(((Number)lIlIllI.maxTurnSpeed.get()).floatValue() - ((Number)lIlIllI.minTurnSpeed.get()).floatValue()) + ((Number)lIlIllI.minTurnSpeed.get()).doubleValue()));
               Intrinsics.checkExpressionValueIsNotNull(var10000, "RotationUtils.limitAngle…rnSpeed.get()).toFloat())");
               lIlIIII = var10000;
               if (!(lIlIIII.getYaw() <= (float)5) && !(lIlIIII.getPitch() <= (float)5) && !(lIlIIII.getPitch() >= (float)-5) && !(lIlIIII.getPitch() >= (float)-5)) {
                  lIlIllI.Rotationhit = false;
               } else {
                  lIlIllI.Rotationhit = true;
               }

               if (lIlIIll <= ((Number)lIlIllI.rangeValue.get()).floatValue()) {
                  if ((Boolean)lIlIllI.silentRotationValue.get()) {
                     RotationUtils.setTargetRotation(lIlIIII, (Boolean)lIlIllI.aacValue.get() ? 15 : 0);
                  } else {
                     var13 = access$getMc$p$s1046033730().thePlayer;
                     Intrinsics.checkExpressionValueIsNotNull(var13, "mc.thePlayer");
                     lIlIIII.toPlayer((EntityPlayer)var13);
                  }

                  return true;
               } else {
                  return lIlIllI.Rotationhit;
               }
            }
         }
      }
   }

   public final void setRotationhit(boolean lIIlIIIll) {
      lIIlIIllI.Rotationhit = lIIlIIIll;
   }

   public void onDisable() {
      lIIIlIIIl.target = (EntityLivingBase)null;
      lIIIlIIIl.currentTarget = (EntityLivingBase)null;
      lIIIlIIIl.hitable = false;
      lIIIlIIIl.prevTargetEntities.clear();
      lIIIlIIIl.attackTimer.reset();
      lIIIlIIIl.clicks = 0;
      lIIIlIIIl.stopBlocking();
   }

   public Aura() {
      llllllllllllllllllllllllllllllll.maxCPS = (IntegerValue)(new IntegerValue("MaxCPS", 8, 1, 20) {
         protected void onChanged(int lllllllllllllllllIllIlIIIlllIlIl, int lllllllllllllllllIllIlIIIlllIIll) {
            int lllllllllllllllllIllIlIIIllllIIl = ((Number)llllllllllllllllllllllllllllllll.minCPS.get()).intValue();
            if (lllllllllllllllllIllIlIIIllllIIl > lllllllllllllllllIllIlIIIlllIIll) {
               lllllllllllllllllIllIlIIIlllIIIl.set(lllllllllllllllllIllIlIIIllllIIl);
            }

            llllllllllllllllllllllllllllllll.attackDelay = TimeUtils.randomClickDelay(((Number)llllllllllllllllllllllllllllllll.minCPS.get()).intValue(), ((Number)lllllllllllllllllIllIlIIIlllIIIl.get()).intValue());
         }
      });
      llllllllllllllllllllllllllllllll.minCPS = (IntegerValue)(new IntegerValue("MinCPS", 5, 1, 20) {
         protected void onChanged(int lIIlIlIIIIII, int lIIlIIllllIl) {
            byte lIIlIIllllII = ((Number)llllllllllllllllllllllllllllllll.maxCPS.get()).intValue();
            if (lIIlIIllllII < lIIlIIllllIl) {
               lIIlIIlllllI.set(lIIlIIllllII);
            }

            llllllllllllllllllllllllllllllll.attackDelay = TimeUtils.randomClickDelay(((Number)lIIlIIlllllI.get()).intValue(), ((Number)llllllllllllllllllllllllllllllll.maxCPS.get()).intValue());
         }
      });
      llllllllllllllllllllllllllllllll.hurtTimeValue = new IntegerValue("HurtTime", 10, 0, 10);
      llllllllllllllllllllllllllllllll.rangeValue = new FloatValue("Range", 3.7F, 1.0F, 8.0F);
      llllllllllllllllllllllllllllllll.BlockRangeValue = new FloatValue("BlockRange", 3.0F, 0.0F, 8.0F);
      llllllllllllllllllllllllllllllll.throughwallValue = new FloatValue("ThroughWallRange", 2.0F, 0.0F, 8.0F);
      llllllllllllllllllllllllllllllll.rangeSprintReducementValue = new FloatValue("RangeSprintReducement", 0.0F, 0.0F, 0.4F);
      llllllllllllllllllllllllllllllll.priorityValue = new ListValue("Priority", new String[]{"Health", "Distance", "Direction", "LivingTime"}, "Distance");
      llllllllllllllllllllllllllllllll.targetModeValue = new ListValue("TargetMode", new String[]{"Single", "Switch", "Multi"}, "Switch");
      llllllllllllllllllllllllllllllll.rotations = new ListValue("RotationMode", new String[]{"Vanilla", "Other", "BackTrack"}, "BackTrack");
      llllllllllllllllllllllllllllllll.AutoBlockValue = new ListValue("AutoBlockMode", new String[]{"Vanilla", "Both", "Pos", "A4Test"}, "Vanilla");
      llllllllllllllllllllllllllllllll.swingValue = new BoolValue("Swing", true);
      llllllllllllllllllllllllllllllll.keepSprintValue = new BoolValue("KeepSprint", true);
      llllllllllllllllllllllllllllllll.autoBlockValue = new BoolValue("AutoBlock", false);
      llllllllllllllllllllllllllllllll.interactAutoBlockValue = new BoolValue("InteractAutoBlock", true);
      llllllllllllllllllllllllllllllll.delayedBlockValue = new BoolValue("DelayedBlock", true);
      llllllllllllllllllllllllllllllll.blockRate = new IntegerValue("BlockRate", 100, 1, 100);
      llllllllllllllllllllllllllllllll.raycastValue = new BoolValue("RayCast", true);
      llllllllllllllllllllllllllllllll.raycastIgnoredValue = new BoolValue("RayCastIgnored", false);
      llllllllllllllllllllllllllllllll.livingRaycastValue = new BoolValue("LivingRayCast", true);
      llllllllllllllllllllllllllllllll.aacValue = new BoolValue("AAC", false);
      llllllllllllllllllllllllllllllll.switchdelayValue = new IntegerValue("SwitchDelay", 10, 0, 100);
      llllllllllllllllllllllllllllllll.maxTurnSpeed = (FloatValue)(new FloatValue("MaxTurnSpeed", 180.0F, 0.0F, 180.0F) {
         protected void onChanged(float llllllllllllllllllIlIIlIlIlIllIl, float llllllllllllllllllIlIIlIlIlIllII) {
            int llllllllllllllllllIlIIlIlIlIlIIl = ((Number)llllllllllllllllllllllllllllllll.minTurnSpeed.get()).floatValue();
            if (llllllllllllllllllIlIIlIlIlIlIIl > llllllllllllllllllIlIIlIlIlIllII) {
               llllllllllllllllllIlIIlIlIlIlIll.set(llllllllllllllllllIlIIlIlIlIlIIl);
            }

         }
      });
      llllllllllllllllllllllllllllllll.minTurnSpeed = (FloatValue)(new FloatValue("MinTurnSpeed", 180.0F, 0.0F, 180.0F) {
         protected void onChanged(float llllllllllllllllllIIlIIIlIIIIlIl, float llllllllllllllllllIIlIIIlIIIIlII) {
            float llllllllllllllllllIIlIIIlIIIIlll = ((Number)llllllllllllllllllllllllllllllll.maxTurnSpeed.get()).floatValue();
            if (llllllllllllllllllIIlIIIlIIIIlll < llllllllllllllllllIIlIIIlIIIIlII) {
               llllllllllllllllllIIlIIIlIIIIllI.set(llllllllllllllllllIIlIIIlIIIIlll);
            }

         }
      });
      llllllllllllllllllllllllllllllll.silentRotationValue = new BoolValue("SilentRotation", true);
      llllllllllllllllllllllllllllllll.rotationStrafeValue = new ListValue("Strafe", new String[]{"Off", "OldStrict", "NewStrict", "Silent"}, "Off");
      llllllllllllllllllllllllllllllll.randomCenterValue = new BoolValue("RandomCenter", true);
      llllllllllllllllllllllllllllllll.outborderValue = new BoolValue("Outborder", false);
      llllllllllllllllllllllllllllllll.fovValue = new FloatValue("FOV", 180.0F, 0.0F, 180.0F);
      llllllllllllllllllllllllllllllll.predictValue = new BoolValue("Predict", true);
      llllllllllllllllllllllllllllllll.maxPredictSize = (FloatValue)(new FloatValue("MaxPredictSize", 1.0F, 0.1F, 5.0F) {
         protected void onChanged(float lIllIIIIlIlllll, float lIllIIIIlIllllI) {
            long lIllIIIIlIllIll = ((Number)llllllllllllllllllllllllllllllll.minPredictSize.get()).floatValue();
            if (lIllIIIIlIllIll > lIllIIIIlIllllI) {
               lIllIIIIllIIIII.set(lIllIIIIlIllIll);
            }

         }
      });
      llllllllllllllllllllllllllllllll.minPredictSize = (FloatValue)(new FloatValue("MinPredictSize", 1.0F, 0.1F, 5.0F) {
         protected void onChanged(float llllllllllllllllllIIIIlIlIlIlIlI, float llllllllllllllllllIIIIlIlIlIlIIl) {
            Exception llllllllllllllllllIIIIlIlIlIIllI = ((Number)llllllllllllllllllllllllllllllll.maxPredictSize.get()).floatValue();
            if (llllllllllllllllllIIIIlIlIlIIllI < llllllllllllllllllIIIIlIlIlIlIIl) {
               llllllllllllllllllIIIIlIlIlIlIll.set(llllllllllllllllllIIIIlIlIlIIllI);
            }

         }
      });
      llllllllllllllllllllllllllllllll.failRateValue = new FloatValue("FailRate", 0.0F, 0.0F, 100.0F);
      llllllllllllllllllllllllllllllll.fakeSwingValue = new BoolValue("FakeSwing", true);
      llllllllllllllllllllllllllllllll.noInventoryAttackValue = new BoolValue("NoInvAttack", false);
      llllllllllllllllllllllllllllllll.noInventoryDelayValue = new IntegerValue("NoInvDelay", 200, 0, 500);
      llllllllllllllllllllllllllllllll.limitedMultiTargetsValue = new IntegerValue("LimitedMultiTargets", 0, 0, 50);
      llllllllllllllllllllllllllllllll.markValue = new BoolValue("Mark", true);
      llllllllllllllllllllllllllllllll.fakeSharpValue = new BoolValue("FakeSharp", true);
      float llllllllllllllllllllllllllllllIl = false;
      double lllllllllllllllllllllllllllllIll = (List)(new ArrayList());
      llllllllllllllllllllllllllllllll.prevTargetEntities = lllllllllllllllllllllllllllllIll;
      llllllllllllllllllllllllllllllll.attackTimer = new MSTimer();
      llllllllllllllllllllllllllllllll.Timer = new MSTimer();
      llllllllllllllllllllllllllllllll.containerOpen = -1L;
   }

   private final void attackEntity(EntityLivingBase lllIIlI) {
      Minecraft var8;
      ItemStack var11;
      EntityPlayerSP var10000;
      EntityLivingBase var10001;
      if (StringsKt.equals((String)lllIIll.rotations.get(), "Other", true)) {
         int llIllll = Minecraft.getMinecraft().thePlayer.getDistanceToEntity((Entity)lllIIlI);
         if (llIllll <= ((Number)lllIIll.rangeValue.get()).floatValue() && lllIIll.Rotationhit && lllIIll.hitable) {
            var10000 = access$getMc$p$s1046033730().thePlayer;
            Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
            if (var10000.isBlocking() || lllIIll.blockingStatus) {
               if (StringsKt.equals((String)lllIIll.AutoBlockValue.get(), "A4Test", true)) {
                  KeyBinding.setKeyBindState(access$getMc$p$s1046033730().gameSettings.keyBindUseItem.getKeyCode(), false);
               } else {
                  var8 = access$getMc$p$s1046033730();
                  Intrinsics.checkExpressionValueIsNotNull(var8, "mc");
                  var8.getNetHandler().addToSendQueue((Packet)(new C07PacketPlayerDigging(Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN)));
               }

               lllIIll.blockingStatus = false;
            }

            LiquidBounce.INSTANCE.getEventManager().callEvent((Event)(new AttackEvent((Entity)lllIIlI)));
            if ((Boolean)lllIIll.swingValue.get()) {
               access$getMc$p$s1046033730().thePlayer.swingItem();
            }

            var8 = access$getMc$p$s1046033730();
            Intrinsics.checkExpressionValueIsNotNull(var8, "mc");
            var8.getNetHandler().addToSendQueue((Packet)(new C02PacketUseEntity((Entity)lllIIlI, net.minecraft.network.play.client.C02PacketUseEntity.Action.ATTACK)));
            var10000 = access$getMc$p$s1046033730().thePlayer;
            Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
            var11 = var10000.getHeldItem();
            var10001 = lllIIll.target;
            if (var10001 == null) {
               Intrinsics.throwNpe();
            }

            if (EnchantmentHelper.getModifierForCreature(var11, var10001.getCreatureAttribute()) > 0.0F || (Boolean)lllIIll.fakeSharpValue.get()) {
               access$getMc$p$s1046033730().thePlayer.onEnchantmentCritical((Entity)lllIIll.target);
            }

            var10000 = access$getMc$p$s1046033730().thePlayer;
            Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
            if (var10000.isBlocking() || (Boolean)lllIIll.autoBlockValue.get() && lllIIll.getCanBlock()) {
               if (((Number)lllIIll.blockRate.get()).intValue() <= 0 || (new Random()).nextInt(100) > ((Number)lllIIll.blockRate.get()).intValue()) {
                  return;
               }

               if ((Boolean)lllIIll.delayedBlockValue.get()) {
                  return;
               }

               lllIIll.startBlocking((Entity)lllIIlI, (Boolean)lllIIll.interactAutoBlockValue.get());
            }
         }
      } else {
         var10000 = access$getMc$p$s1046033730().thePlayer;
         Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
         if (var10000.isBlocking() || lllIIll.blockingStatus) {
            if (StringsKt.equals((String)lllIIll.AutoBlockValue.get(), "A4Test", true)) {
               KeyBinding.setKeyBindState(access$getMc$p$s1046033730().gameSettings.keyBindUseItem.getKeyCode(), false);
            } else {
               var8 = access$getMc$p$s1046033730();
               Intrinsics.checkExpressionValueIsNotNull(var8, "mc");
               var8.getNetHandler().addToSendQueue((Packet)(new C07PacketPlayerDigging(Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN)));
            }

            lllIIll.blockingStatus = false;
         }

         var10000 = access$getMc$p$s1046033730().thePlayer;
         Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
         int llIllll = PlayerExtensionKt.getDistanceToEntityBox((Entity)var10000, (Entity)lllIIlI);
         if (lllIIll.hitable && llIllll < ((Number)lllIIll.rangeValue.get()).doubleValue()) {
            LiquidBounce.INSTANCE.getEventManager().callEvent((Event)(new AttackEvent((Entity)lllIIlI)));
            if ((Boolean)lllIIll.swingValue.get()) {
               access$getMc$p$s1046033730().thePlayer.swingItem();
            }

            var8 = access$getMc$p$s1046033730();
            Intrinsics.checkExpressionValueIsNotNull(var8, "mc");
            var8.getNetHandler().addToSendQueue((Packet)(new C02PacketUseEntity((Entity)lllIIlI, net.minecraft.network.play.client.C02PacketUseEntity.Action.ATTACK)));
            if ((Boolean)lllIIll.keepSprintValue.get()) {
               if (access$getMc$p$s1046033730().thePlayer.fallDistance > 0.0F && !access$getMc$p$s1046033730().thePlayer.onGround) {
                  var10000 = access$getMc$p$s1046033730().thePlayer;
                  Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
                  if (!var10000.isOnLadder()) {
                     var10000 = access$getMc$p$s1046033730().thePlayer;
                     Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
                     if (!var10000.isInWater() && !access$getMc$p$s1046033730().thePlayer.isPotionActive(Potion.blindness)) {
                        var10000 = access$getMc$p$s1046033730().thePlayer;
                        Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
                        if (!var10000.isRiding()) {
                           access$getMc$p$s1046033730().thePlayer.onCriticalHit((Entity)lllIIlI);
                        }
                     }
                  }
               }

               var10000 = access$getMc$p$s1046033730().thePlayer;
               Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
               if (EnchantmentHelper.getModifierForCreature(var10000.getHeldItem(), lllIIlI.getCreatureAttribute()) > 0.0F) {
                  access$getMc$p$s1046033730().thePlayer.onEnchantmentCritical((Entity)lllIIlI);
               }
            } else {
               PlayerControllerMP var9 = access$getMc$p$s1046033730().playerController;
               Intrinsics.checkExpressionValueIsNotNull(var9, "mc.playerController");
               if (var9.getCurrentGameType() != GameType.SPECTATOR) {
                  access$getMc$p$s1046033730().thePlayer.attackTargetEntityWithCurrentItem((Entity)lllIIlI);
               }
            }

            Module var10 = LiquidBounce.INSTANCE.getModuleManager().get(Criticals.class);
            if (var10 == null) {
               throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.combat.Criticals");
            }

            String llIlllI = (Criticals)var10;
            float llIllIl = 0;

            for(byte llIllII = 2; llIllIl <= llIllII; ++llIllIl) {
               label166: {
                  label193: {
                     if (access$getMc$p$s1046033730().thePlayer.fallDistance > 0.0F && !access$getMc$p$s1046033730().thePlayer.onGround) {
                        var10000 = access$getMc$p$s1046033730().thePlayer;
                        Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
                        if (!var10000.isOnLadder()) {
                           var10000 = access$getMc$p$s1046033730().thePlayer;
                           Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
                           if (!var10000.isInWater() && !access$getMc$p$s1046033730().thePlayer.isPotionActive(Potion.blindness) && access$getMc$p$s1046033730().thePlayer.ridingEntity == null) {
                              break label193;
                           }
                        }
                     }

                     if (!llIlllI.getState() || !llIlllI.getMsTimer().hasTimePassed((long)((Number)llIlllI.getDelayValue().get()).intValue())) {
                        break label166;
                     }

                     var10000 = access$getMc$p$s1046033730().thePlayer;
                     Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
                     if (var10000.isInWater()) {
                        break label166;
                     }

                     var10000 = access$getMc$p$s1046033730().thePlayer;
                     Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
                     if (var10000.isInLava() || access$getMc$p$s1046033730().thePlayer.isInWeb) {
                        break label166;
                     }
                  }

                  access$getMc$p$s1046033730().thePlayer.onCriticalHit((Entity)lllIIll.target);
               }

               var10000 = access$getMc$p$s1046033730().thePlayer;
               Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
               var11 = var10000.getHeldItem();
               var10001 = lllIIll.target;
               if (var10001 == null) {
                  Intrinsics.throwNpe();
               }

               if (EnchantmentHelper.getModifierForCreature(var11, var10001.getCreatureAttribute()) > 0.0F || (Boolean)lllIIll.fakeSharpValue.get()) {
                  access$getMc$p$s1046033730().thePlayer.onEnchantmentCritical((Entity)lllIIll.target);
               }
            }
         }

         var10000 = access$getMc$p$s1046033730().thePlayer;
         Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
         if (var10000.isBlocking() || (Boolean)lllIIll.autoBlockValue.get() && lllIIll.getCanBlock()) {
            if (((Number)lllIIll.blockRate.get()).intValue() <= 0 || (new Random()).nextInt(100) > ((Number)lllIIll.blockRate.get()).intValue()) {
               return;
            }

            if ((Boolean)lllIIll.delayedBlockValue.get()) {
               return;
            }

            lllIIll.startBlocking((Entity)lllIIlI, (Boolean)lllIIll.interactAutoBlockValue.get());
         }
      }

   }

   private final boolean isAlive(EntityLivingBase lIllll) {
      return lIllll.isEntityAlive() && lIllll.getHealth() > (float)0 || (Boolean)lIlllI.aacValue.get() && lIllll.hurtTime > 5;
   }

   private final float getMaxRange() {
      float lIIlIl = ((Number)lIIlll.rangeValue.get()).floatValue();
      short lIIlII = ((Number)lIIlll.BlockRangeValue.get()).floatValue();
      float lIIIll = false;
      return Math.max(lIIlIl, lIIlII);
   }

   @EventTarget
   public final void onRender3D(@NotNull Render3DEvent llIlIlll) {
      Intrinsics.checkParameterIsNotNull(llIlIlll, "event");
      if (llIllIII.getCancelRun()) {
         llIllIII.target = (EntityLivingBase)null;
         llIllIII.currentTarget = (EntityLivingBase)null;
         llIllIII.hitable = false;
         llIllIII.stopBlocking();
      } else if ((Boolean)llIllIII.noInventoryAttackValue.get() && (access$getMc$p$s1046033730().currentScreen instanceof GuiContainer || System.currentTimeMillis() - llIllIII.containerOpen < ((Number)llIllIII.noInventoryDelayValue.get()).longValue())) {
         llIllIII.target = (EntityLivingBase)null;
         llIllIII.currentTarget = (EntityLivingBase)null;
         llIllIII.hitable = false;
         if (access$getMc$p$s1046033730().currentScreen instanceof GuiContainer) {
            llIllIII.containerOpen = System.currentTimeMillis();
         }

      } else {
         boolean var10001;
         if (llIllIII.target != null) {
            var10001 = false;
            if ((Boolean)llIllIII.markValue.get() && !StringsKt.equals((String)llIllIII.targetModeValue.get(), "Multi", true)) {
               RenderUtils.drawPlatform((Entity)llIllIII.target, llIllIII.hitable ? new Color(37, 126, 255, 70) : new Color(255, 0, 0, 70));
            }

            if (llIllIII.currentTarget != null && llIllIII.attackTimer.hasTimePassed(llIllIII.attackDelay)) {
               EntityLivingBase var10000 = llIllIII.currentTarget;
               if (var10000 == null) {
                  Intrinsics.throwNpe();
               }

               if (var10000.hurtTime <= ((Number)llIllIII.hurtTimeValue.get()).intValue()) {
                  int var3 = llIllIII.clicks++;
                  llIllIII.attackTimer.reset();
                  llIllIII.attackDelay = TimeUtils.randomClickDelay(((Number)llIllIII.minCPS.get()).intValue(), ((Number)llIllIII.maxCPS.get()).intValue());
               }
            }

         } else {
            var10001 = false;
         }
      }
   }

   private final void runAttack() {
      boolean var10001;
      if (lIllIlll.target != null) {
         var10001 = false;
         if (lIllIlll.currentTarget == null) {
            var10001 = false;
         } else {
            var10001 = false;
            float lIlllIIl = ((Number)lIllIlll.failRateValue.get()).floatValue();
            byte lIllIlIl = (Boolean)lIllIlll.swingValue.get();
            boolean lIllIlII = StringsKt.equals((String)lIllIlll.targetModeValue.get(), "Multi", true);
            boolean lIllllII = (Boolean)lIllIlll.aacValue.get() && access$getMc$p$s1046033730().currentScreen instanceof GuiInventory;
            boolean lIllllIl = lIlllIIl > (float)0 && (float)(new Random()).nextInt(100) <= lIlllIIl;
            Minecraft var10000;
            if (lIllllII) {
               var10000 = access$getMc$p$s1046033730();
               Intrinsics.checkExpressionValueIsNotNull(var10000, "mc");
               var10000.getNetHandler().addToSendQueue((Packet)(new C0DPacketCloseWindow()));
            }

            if (lIllIlll.hitable && !lIllllIl) {
               EntityLivingBase var14;
               if (!lIllIlII) {
                  var14 = lIllIlll.currentTarget;
                  if (var14 == null) {
                     Intrinsics.throwNpe();
                  }

                  lIllIlll.attackEntity(var14);
               } else {
                  int lIlllllI = 0;
                  Iterator lIlIllll = access$getMc$p$s1046033730().theWorld.loadedEntityList.iterator();

                  while(lIlIllll.hasNext()) {
                     String lIllIIII = (Entity)lIlIllll.next();
                     EntityPlayerSP var11 = access$getMc$p$s1046033730().thePlayer;
                     Intrinsics.checkExpressionValueIsNotNull(var11, "mc.thePlayer");
                     Entity var12 = (Entity)var11;
                     Intrinsics.checkExpressionValueIsNotNull(lIllIIII, "entity");
                     int lIlIlllI = PlayerExtensionKt.getDistanceToEntityBox(var12, lIllIIII);
                     if (lIllIIII instanceof EntityLivingBase && lIllIlll.isEnemy(lIllIIII) && lIlIlllI <= (double)lIllIlll.getRange(lIllIIII)) {
                        lIllIlll.attackEntity((EntityLivingBase)lIllIIII);
                        ++lIlllllI;
                        if (((Number)lIllIlll.limitedMultiTargetsValue.get()).intValue() != 0 && ((Number)lIllIlll.limitedMultiTargetsValue.get()).intValue() <= lIlllllI) {
                           break;
                        }
                     }
                  }
               }

               if (lIllIlll.Timer.hasTimePassed((long)(((Number)lIllIlll.switchdelayValue.get()).intValue() * 10)) && ((Number)lIllIlll.switchdelayValue.get()).intValue() != 10) {
                  List var13 = lIllIlll.prevTargetEntities;
                  int var15;
                  if ((Boolean)lIllIlll.aacValue.get()) {
                     var14 = lIllIlll.target;
                     if (var14 == null) {
                        Intrinsics.throwNpe();
                     }

                     var15 = var14.getEntityId();
                  } else {
                     var14 = lIllIlll.currentTarget;
                     if (var14 == null) {
                        Intrinsics.throwNpe();
                     }

                     var15 = var14.getEntityId();
                  }

                  var13.add(var15);
                  var10001 = false;
               }

               lIllIlll.Timer.reset();
            } else if (lIllIlIl && ((Boolean)lIllIlll.fakeSwingValue.get() || lIllllIl)) {
               access$getMc$p$s1046033730().thePlayer.swingItem();
            }

            if (lIllllII) {
               var10000 = access$getMc$p$s1046033730();
               Intrinsics.checkExpressionValueIsNotNull(var10000, "mc");
               var10000.getNetHandler().addToSendQueue((Packet)(new C16PacketClientStatus(EnumState.OPEN_INVENTORY_ACHIEVEMENT)));
            }

         }
      } else {
         var10001 = false;
      }
   }

   private final void updateHitable() {
      if (((Number)lIIIllI.maxTurnSpeed.get()).floatValue() <= 0.0F) {
         lIIIllI.hitable = true;
      } else {
         char lIIIlII = (double)lIIIllI.getMaxRange();
         EntityPlayerSP var10000 = access$getMc$p$s1046033730().thePlayer;
         Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
         Entity var10 = (Entity)var10000;
         EntityLivingBase var10001 = lIIIllI.target;
         if (var10001 == null) {
            Intrinsics.throwNpe();
         }

         String lIIIIll = PlayerExtensionKt.getDistanceToEntityBox(var10, (Entity)var10001);
         double lIIIIlI = false;
         short lIIIlIl = Math.min(lIIIlII, lIIIIll) + 1.4D;
         if ((Boolean)lIIIllI.raycastValue.get()) {
            char lIIIlII = RaycastUtils.raycastEntity(lIIIlIl, (RaycastUtils.IEntityFilter)(new RaycastUtils.IEntityFilter() {
               public final boolean canRaycast(Entity llllllllllllllllllIllIlIIlIIIlIl) {
                  boolean var6;
                  label45: {
                     if (!(Boolean)lIIIllI.livingRaycastValue.get() || llllllllllllllllllIllIlIIlIIIlIl instanceof EntityLivingBase && !(llllllllllllllllllIllIlIIlIIIlIl instanceof EntityArmorStand)) {
                        if (lIIIllI.isEnemy(llllllllllllllllllIllIlIIlIIIlIl) || (Boolean)lIIIllI.raycastIgnoredValue.get()) {
                           break label45;
                        }

                        if ((Boolean)lIIIllI.aacValue.get()) {
                           WorldClient var10000 = Aura.access$getMc$p$s1046033730().theWorld;
                           Intrinsics.checkExpressionValueIsNotNull(llllllllllllllllllIllIlIIlIIIlIl, "it");
                           List var5 = var10000.getEntitiesWithinAABBExcludingEntity(llllllllllllllllllIllIlIIlIIIlIl, llllllllllllllllllIllIlIIlIIIlIl.getEntityBoundingBox());
                           Intrinsics.checkExpressionValueIsNotNull(var5, "mc.theWorld.getEntitiesW…it, it.entityBoundingBox)");
                           long llllllllllllllllllIllIlIIlIIIlII = (Collection)var5;
                           double llllllllllllllllllIllIlIIlIIIIll = false;
                           if (!llllllllllllllllllIllIlIIlIIIlII.isEmpty()) {
                              break label45;
                           }
                        }
                     }

                     var6 = false;
                     return var6;
                  }

                  var6 = true;
                  return var6;
               }
            }));
            if ((Boolean)lIIIllI.raycastValue.get() && lIIIlII instanceof EntityLivingBase) {
               Module var11 = LiquidBounce.INSTANCE.getModuleManager().get(NoFriends.class);
               if (var11 == null) {
                  Intrinsics.throwNpe();
               }

               if (var11.getState() || !EntityUtils.isFriend(lIIIlII)) {
                  lIIIllI.currentTarget = (EntityLivingBase)lIIIlII;
               }
            }

            lIIIllI.hitable = ((Number)lIIIllI.maxTurnSpeed.get()).floatValue() > 0.0F ? Intrinsics.areEqual((Object)lIIIllI.currentTarget, (Object)lIIIlII) : true;
         } else {
            lIIIllI.hitable = RotationUtils.isFaced((Entity)lIIIllI.currentTarget, lIIIlIl);
         }

      }
   }

   @EventTarget
   public final void onStrafe(@NotNull StrafeEvent llllIIIl) {
      Intrinsics.checkParameterIsNotNull(llllIIIl, "event");
      if (!StringsKt.equals((String)llllIIlI.rotationStrafeValue.get(), "Off", true)) {
         llllIIlI.update();
         if ((Boolean)llllIIlI.silentRotationValue.get()) {
            int lllIlllI = (String)llllIIlI.rotationStrafeValue.get();
            String lllIllIl = false;
            if (lllIlllI == null) {
               throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            } else {
               String var10000 = lllIlllI.toLowerCase();
               Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).toLowerCase()");
               lllIlllI = var10000;
               Rotation lllIllII;
               float lllIlIll;
               float lllIlIlI;
               float lllIlIIl;
               float llllllll;
               float lIIIIIIII;
               float lllllIlI;
               float lllllIll;
               Rotation var12;
               EntityPlayerSP var13;
               boolean var10001;
               switch(lllIlllI.hashCode()) {
               case -1854958672:
                  if (lllIlllI.equals("oldstrict")) {
                     if (llllIIlI.currentTarget == null) {
                        var10001 = false;
                        return;
                     }

                     var10001 = false;
                     var12 = RotationUtils.targetRotation;
                     if (var12 == null) {
                        var10001 = false;
                        return;
                     }

                     lllIllII = var12;
                     lllllIlI = lllIllII.component1();
                     lllllIll = llllIIIl.getStrafe();
                     lllIlIll = llllIIIl.getForward();
                     lllIlIlI = llllIIIl.getFriction();
                     lllIlIIl = lllllIll * lllllIll + lllIlIll * lllIlIll;
                     if (lllIlIIl >= 1.0E-4F) {
                        lllIlIIl = MathHelper.sqrt_float(lllIlIIl);
                        if (lllIlIIl < 1.0F) {
                           lllIlIIl = 1.0F;
                        }

                        lllIlIIl = lllIlIlI / lllIlIIl;
                        lllllIll *= lllIlIIl;
                        lllIlIll *= lllIlIIl;
                        llllllll = MathHelper.sin((float)((double)lllllIlI * 3.141592653589793D / (double)180.0F));
                        lIIIIIIII = MathHelper.cos((float)((double)lllllIlI * 3.141592653589793D / (double)180.0F));
                        var13 = access$getMc$p$s1046033730().thePlayer;
                        var13.motionX += (double)(lllllIll * lIIIIIIII - lllIlIll * llllllll);
                        var13 = access$getMc$p$s1046033730().thePlayer;
                        var13.motionZ += (double)(lllIlIll * lIIIIIIII + lllllIll * llllllll);
                     }

                     llllIIIl.cancelEvent();
                  }
                  break;
               case -902327211:
                  if (lllIlllI.equals("silent")) {
                     if (llllIIlI.currentTarget == null) {
                        var10001 = false;
                        return;
                     }

                     var10001 = false;
                     llllIIlI.update();
                     RotationUtils.targetRotation.applyStrafeToPlayer(llllIIIl);
                     llllIIIl.cancelEvent();
                  }
                  break;
               case 320426089:
                  if (lllIlllI.equals("newstrict")) {
                     if (llllIIlI.currentTarget == null) {
                        var10001 = false;
                        return;
                     }

                     var10001 = false;
                     var12 = RotationUtils.targetRotation;
                     if (var12 == null) {
                        var10001 = false;
                        return;
                     }

                     lllIllII = var12;
                     lllllIlI = lllIllII.component1();
                     lllllIll = llllIIIl.getStrafe();
                     lllIlIll = llllIIIl.getForward();
                     lllIlIlI = llllIIIl.getFriction();
                     lllIlIIl = lllllIll * lllllIll + lllIlIll * lllIlIll;
                     if (lllIlIIl >= 1.0E-4F) {
                        lllIlIIl = MathHelper.sqrt_float(lllIlIIl);
                        if (lllIlIIl < 1.0F) {
                           lllIlIIl = 1.0F;
                        }

                        lllIlIIl = lllIlIlI / lllIlIIl;
                        lllllIll *= lllIlIIl;
                        lllIlIll *= lllIlIIl;
                        llllllll = MathHelper.sin((float)((double)lllllIlI * 3.141592653589793D / (double)180.0F));
                        lIIIIIIII = MathHelper.cos((float)((double)lllllIlI * 3.141592653589793D / (double)180.0F));
                        var13 = access$getMc$p$s1046033730().thePlayer;
                        var13.motionX += (double)(lllllIll * lIIIIIIII - lllIlIll * llllllll);
                        var13 = access$getMc$p$s1046033730().thePlayer;
                        var13.motionZ += (double)(lllIlIll * lIIIIIIII + lllllIll * llllllll);
                     }

                     llllIIIl.cancelEvent();
                  }
               }

            }
         }
      }
   }

   @Nullable
   public String getTag() {
      return (String)llIII.targetModeValue.get();
   }

   private final float getRange(Entity lllll) {
      EntityPlayerSP var10000 = access$getMc$p$s1046033730().thePlayer;
      Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
      float var2 = PlayerExtensionKt.getDistanceToEntityBox((Entity)var10000, lllll) >= ((Number)llllI.BlockRangeValue.get()).doubleValue() ? ((Number)llllI.rangeValue.get()).floatValue() : ((Number)llllI.rangeValue.get()).floatValue();
      EntityPlayerSP var10001 = access$getMc$p$s1046033730().thePlayer;
      Intrinsics.checkExpressionValueIsNotNull(var10001, "mc.thePlayer");
      return var2 - (var10001.isSprinting() ? ((Number)llllI.rangeSprintReducementValue.get()).floatValue() : 0.0F);
   }

   // $FF: synthetic method
   public static final long access$getAttackDelay$p(Aura llllllllllllllllllllllllllIIIIll) {
      return llllllllllllllllllllllllllIIIIll.attackDelay;
   }

   @Nullable
   public final EntityLivingBase getTarget() {
      return lIIllIIlI.target;
   }

   public final void setTarget(@Nullable EntityLivingBase lIIlIlllI) {
      lIIlIllIl.target = lIIlIlllI;
   }

   private final boolean isEnemy(Entity lIIIIIIl) {
      if (lIIIIIIl instanceof EntityLivingBase && (EntityUtils.targetDead || lIIIIIlI.isAlive((EntityLivingBase)lIIIIIIl)) && Intrinsics.areEqual((Object)lIIIIIIl, (Object)access$getMc$p$s1046033730().thePlayer) ^ true) {
         if (!EntityUtils.targetInvisible && lIIIIIIl.isInvisible()) {
            return false;
         } else if (EntityUtils.targetPlayer && lIIIIIIl instanceof EntityPlayer) {
            if (!((EntityPlayer)lIIIIIIl).isSpectator() && !AntiBot.isBot((EntityLivingBase)lIIIIIIl)) {
               Module var10000;
               if (EntityUtils.isFriend(lIIIIIIl)) {
                  var10000 = LiquidBounce.INSTANCE.getModuleManager().get(NoFriends.class);
                  if (var10000 == null) {
                     Intrinsics.throwNpe();
                  }

                  if (!var10000.getState()) {
                     return false;
                  }
               }

               var10000 = LiquidBounce.INSTANCE.getModuleManager().get(Teams.class);
               if (var10000 == null) {
                  throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.misc.Teams");
               } else {
                  float llllllI = (Teams)var10000;
                  return !llllllI.getState() || !llllllI.isInYourTeam((EntityLivingBase)lIIIIIIl);
               }
            } else {
               return false;
            }
         } else {
            return EntityUtils.targetMobs && EntityUtils.isMob(lIIIIIIl) || EntityUtils.targetAnimals && EntityUtils.isAnimal(lIIIIIIl);
         }
      } else {
         return false;
      }
   }

   private final void startBlocking(Entity lllIlI, boolean lllIIl) {
      Minecraft var10000;
      if (lllIIl) {
         var10000 = access$getMc$p$s1046033730();
         Intrinsics.checkExpressionValueIsNotNull(var10000, "mc");
         var10000.getNetHandler().addToSendQueue((Packet)(new C02PacketUseEntity(lllIlI, lllIlI.getPositionVector())));
         var10000 = access$getMc$p$s1046033730();
         Intrinsics.checkExpressionValueIsNotNull(var10000, "mc");
         var10000.getNetHandler().addToSendQueue((Packet)(new C02PacketUseEntity(lllIlI, net.minecraft.network.play.client.C02PacketUseEntity.Action.INTERACT)));
      }

      if (StringsKt.equals((String)lllllI.AutoBlockValue.get(), "Vanilla", true)) {
         var10000 = access$getMc$p$s1046033730();
         Intrinsics.checkExpressionValueIsNotNull(var10000, "mc");
         var10000.getNetHandler().addToSendQueue((Packet)(new C08PacketPlayerBlockPlacement(access$getMc$p$s1046033730().thePlayer.inventory.getCurrentItem())));
         lllllI.blockingStatus = true;
      }

      if (StringsKt.equals((String)lllllI.AutoBlockValue.get(), "Pos", true)) {
         var10000 = access$getMc$p$s1046033730();
         Intrinsics.checkExpressionValueIsNotNull(var10000, "mc");
         var10000.getNetHandler().addToSendQueue((Packet)(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, access$getMc$p$s1046033730().thePlayer.inventory.getCurrentItem(), 0.0F, 0.0F, 0.0F)));
         lllllI.blockingStatus = true;
      }

      if (StringsKt.equals((String)lllllI.AutoBlockValue.get(), "Both", true)) {
         var10000 = access$getMc$p$s1046033730();
         Intrinsics.checkExpressionValueIsNotNull(var10000, "mc");
         var10000.getNetHandler().addToSendQueue((Packet)(new C08PacketPlayerBlockPlacement(access$getMc$p$s1046033730().thePlayer.inventory.getCurrentItem())));
         var10000 = access$getMc$p$s1046033730();
         Intrinsics.checkExpressionValueIsNotNull(var10000, "mc");
         var10000.getNetHandler().addToSendQueue((Packet)(new C08PacketPlayerBlockPlacement(BlockPos.ORIGIN, 255, access$getMc$p$s1046033730().thePlayer.getHeldItem(), 0.0F, 0.0F, 0.0F)));
         lllllI.blockingStatus = true;
      }

      if (StringsKt.equals((String)lllllI.AutoBlockValue.get(), "A4Test", true)) {
         KeyBinding.setKeyBindState(access$getMc$p$s1046033730().gameSettings.keyBindUseItem.getKeyCode(), true);
         lllllI.blockingStatus = true;
      }

   }

   public final boolean getBlockingStatus() {
      return lIIIllllI.blockingStatus;
   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent llIlllll) {
      Intrinsics.checkParameterIsNotNull(llIlllll, "event");
      if (llIllllI.getCancelRun()) {
         llIllllI.target = (EntityLivingBase)null;
         llIllllI.currentTarget = (EntityLivingBase)null;
         llIllllI.hitable = false;
         llIllllI.stopBlocking();
      } else if (!(Boolean)llIllllI.noInventoryAttackValue.get() || !(access$getMc$p$s1046033730().currentScreen instanceof GuiContainer) && System.currentTimeMillis() - llIllllI.containerOpen >= ((Number)llIllllI.noInventoryDelayValue.get()).longValue()) {
         if (llIllllI.target != null && llIllllI.currentTarget != null) {
            while(llIllllI.clicks > 0) {
               llIllllI.runAttack();
               llIllllI.clicks += -1;
            }
         }

      } else {
         llIllllI.target = (EntityLivingBase)null;
         llIllllI.currentTarget = (EntityLivingBase)null;
         llIllllI.hitable = false;
         if (access$getMc$p$s1046033730().currentScreen instanceof GuiContainer) {
            llIllllI.containerOpen = System.currentTimeMillis();
         }

      }
   }

   public final void setBlockingStatus(boolean lIIIllIIl) {
      lIIIllIlI.blockingStatus = lIIIllIIl;
   }

   private final boolean getCancelRun() {
      EntityPlayerSP var10000 = access$getMc$p$s1046033730().thePlayer;
      Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
      boolean var2;
      if (!var10000.isSpectator()) {
         EntityPlayerSP var10001 = access$getMc$p$s1046033730().thePlayer;
         Intrinsics.checkExpressionValueIsNotNull(var10001, "mc.thePlayer");
         if (llIIll.isAlive((EntityLivingBase)var10001)) {
            Module var1 = LiquidBounce.INSTANCE.getModuleManager().get(Blink.class);
            if (var1 == null) {
               Intrinsics.throwNpe();
            }

            if (!var1.getState()) {
               var1 = LiquidBounce.INSTANCE.getModuleManager().get(FreeCam.class);
               if (var1 == null) {
                  Intrinsics.throwNpe();
               }

               if (!var1.getState()) {
                  var2 = false;
                  return var2;
               }
            }
         }
      }

      var2 = true;
      return var2;
   }

   private final void updateTarget() {
      lIIlIIIl.target = (EntityLivingBase)null;
      int lIIlIIll = ((Number)lIIlIIIl.hurtTimeValue.get()).intValue();
      float lIIlIlII = ((Number)lIIlIIIl.fovValue.get()).floatValue();
      boolean lIIlIlIl = StringsKt.equals((String)lIIlIIIl.targetModeValue.get(), "Switch", true);
      Exception lIIIllII = false;
      List lIIlIllI = (List)(new ArrayList());
      Iterator lIIIlIll = access$getMc$p$s1046033730().theWorld.loadedEntityList.iterator();

      while(true) {
         double lIIIlIlI;
         double lIIIlIII;
         Entity lIIIllII;
         do {
            do {
               do {
                  do {
                     do {
                        if (!lIIIlIll.hasNext()) {
                           Exception lIIIllII = (String)lIIlIIIl.priorityValue.get();
                           double lIIIlIll = false;
                           if (lIIIllII == null) {
                              throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                           }

                           String var18 = lIIIllII.toLowerCase();
                           Intrinsics.checkExpressionValueIsNotNull(var18, "(this as java.lang.String).toLowerCase()");
                           lIIIllII = var18;
                           Comparator lIIIIlll;
                           boolean lIIIlIlI;
                           boolean lIIIlIII;
                           switch(lIIIllII.hashCode()) {
                           case -1221262756:
                              if (lIIIllII.equals("health")) {
                                 lIIIlIlI = false;
                                 if (lIIlIllI.size() > 1) {
                                    lIIIlIII = false;
                                    lIIIIlll = (Comparator)(new Aura$updateTarget$$inlined$sortBy$2());
                                    CollectionsKt.sortWith(lIIlIllI, lIIIIlll);
                                 }
                              }
                              break;
                           case -962590849:
                              if (lIIIllII.equals("direction")) {
                                 lIIIlIlI = false;
                                 if (lIIlIllI.size() > 1) {
                                    lIIIlIII = false;
                                    lIIIIlll = (Comparator)(new Aura$updateTarget$$inlined$sortBy$3());
                                    CollectionsKt.sortWith(lIIlIllI, lIIIIlll);
                                 }
                              }
                              break;
                           case 288459765:
                              if (lIIIllII.equals("distance")) {
                                 lIIIlIlI = false;
                                 if (lIIlIllI.size() > 1) {
                                    lIIIlIII = false;
                                    lIIIIlll = (Comparator)(new Aura$updateTarget$$inlined$sortBy$1());
                                    CollectionsKt.sortWith(lIIlIllI, lIIIIlll);
                                 }
                              }
                              break;
                           case 886905078:
                              if (lIIIllII.equals("livingtime")) {
                                 lIIIlIlI = false;
                                 if (lIIlIllI.size() > 1) {
                                    lIIIlIII = false;
                                    lIIIIlll = (Comparator)(new Aura$updateTarget$$inlined$sortBy$4());
                                    CollectionsKt.sortWith(lIIlIllI, lIIIIlll);
                                 }
                              }
                           }

                           lIIIlIll = lIIlIllI.iterator();

                           EntityLivingBase lIIIllII;
                           do {
                              if (!lIIIlIll.hasNext()) {
                                 Exception lIIIllII = (Collection)lIIlIIIl.prevTargetEntities;
                                 lIIIlIll = false;
                                 if (!lIIIllII.isEmpty()) {
                                    lIIlIIIl.prevTargetEntities.clear();
                                    lIIlIIIl.updateTarget();
                                 }

                                 return;
                              }

                              lIIIllII = (EntityLivingBase)lIIIlIll.next();
                           } while(!lIIlIIIl.updateRotations((Entity)lIIIllII));

                           lIIlIIIl.target = lIIIllII;
                           return;
                        }

                        lIIIllII = (Entity)lIIIlIll.next();
                     } while(!(lIIIllII instanceof EntityLivingBase));
                  } while(!lIIlIIIl.isEnemy(lIIIllII));
               } while(lIIlIlIl && lIIlIIIl.prevTargetEntities.contains(((EntityLivingBase)lIIIllII).getEntityId()));

               EntityPlayerSP var10000 = access$getMc$p$s1046033730().thePlayer;
               Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
               lIIIlIlI = PlayerExtensionKt.getDistanceToEntityBox((Entity)var10000, lIIIllII);
               lIIIlIII = RotationUtils.getRotationDifference(lIIIllII);
            } while(!(lIIIlIlI <= (double)lIIlIIIl.getMaxRange()));
         } while(lIIlIlII != 180.0F && !(lIIIlIII <= (double)lIIlIlII));

         if (((EntityLivingBase)lIIIllII).hurtTime <= lIIlIIll) {
            lIIlIllI.add(lIIIllII);
            boolean var10001 = false;
         }
      }
   }

   @NotNull
   public final MSTimer getTimer() {
      return lIIlIIIII.Timer;
   }

   public final void update() {
      if (!lllIIlIl.getCancelRun() && (!(Boolean)lllIIlIl.noInventoryAttackValue.get() || !(access$getMc$p$s1046033730().currentScreen instanceof GuiContainer) && System.currentTimeMillis() - lllIIlIl.containerOpen >= ((Number)lllIIlIl.noInventoryDelayValue.get()).longValue())) {
         lllIIlIl.updateTarget();
         if (lllIIlIl.target == null) {
            lllIIlIl.stopBlocking();
         } else {
            lllIIlIl.currentTarget = lllIIlIl.target;
            if (!StringsKt.equals((String)lllIIlIl.targetModeValue.get(), "Switch", true) && lllIIlIl.isEnemy((Entity)lllIIlIl.currentTarget)) {
               lllIIlIl.target = lllIIlIl.currentTarget;
            }

         }
      }
   }

   public final boolean getRotationhit() {
      return lIIlIlIIl.Rotationhit;
   }

   @EventTarget
   public final void onMotion(@NotNull MotionEvent lIIIIllIl) {
      Intrinsics.checkParameterIsNotNull(lIIIIllIl, "event");
      if (lIIIIllIl.getEventState() == EventState.POST) {
         boolean var10001;
         if (lIIIIllII.target != null) {
            var10001 = false;
            if (lIIIIllII.currentTarget != null) {
               var10001 = false;
               lIIIIllII.updateHitable();
               if ((Boolean)lIIIIllII.autoBlockValue.get() && (Boolean)lIIIIllII.delayedBlockValue.get() && lIIIIllII.getCanBlock()) {
                  EntityLivingBase var2 = lIIIIllII.currentTarget;
                  if (var2 == null) {
                     Intrinsics.throwNpe();
                  }

                  lIIIIllII.startBlocking((Entity)var2, lIIIIllII.hitable);
               }

            } else {
               var10001 = false;
            }
         } else {
            var10001 = false;
         }
      } else {
         if (StringsKt.equals((String)lIIIIllII.rotationStrafeValue.get(), "Off", true)) {
            lIIIIllII.update();
         }

      }
   }

   private final void stopBlocking() {
      if (llIllI.blockingStatus) {
         if (StringsKt.equals((String)llIllI.AutoBlockValue.get(), "A4Test", true)) {
            KeyBinding.setKeyBindState(access$getMc$p$s1046033730().gameSettings.keyBindUseItem.getKeyCode(), false);
         } else {
            Minecraft var10000 = access$getMc$p$s1046033730();
            Intrinsics.checkExpressionValueIsNotNull(var10000, "mc");
            var10000.getNetHandler().addToSendQueue((Packet)(new C07PacketPlayerDigging(Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN)));
         }

         llIllI.blockingStatus = false;
      }

   }

   @EventTarget
   public final void onEntityMove(@NotNull EntityMovementEvent llIIllII) {
      Intrinsics.checkParameterIsNotNull(llIIllII, "event");
      Exception llIIlIll = llIIllII.getMovedEntity();
      if (llIIllll.target != null && !(Intrinsics.areEqual((Object)llIIlIll, (Object)llIIllll.currentTarget) ^ true)) {
         llIIllll.updateHitable();
      }
   }

   private final boolean getCanBlock() {
      EntityPlayerSP var10000 = access$getMc$p$s1046033730().thePlayer;
      Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
      boolean var2;
      if (var10000.getHeldItem() != null) {
         var10000 = access$getMc$p$s1046033730().thePlayer;
         Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
         ItemStack var1 = var10000.getHeldItem();
         Intrinsics.checkExpressionValueIsNotNull(var1, "mc.thePlayer.heldItem");
         if (var1.getItem() instanceof ItemSword) {
            var2 = true;
            return var2;
         }
      }

      var2 = false;
      return var2;
   }

   public void onEnable() {
      boolean var10001;
      if (access$getMc$p$s1046033730().thePlayer != null) {
         var10001 = false;
         if (access$getMc$p$s1046033730().theWorld != null) {
            var10001 = false;
            lIIIlIlII.updateTarget();
         } else {
            var10001 = false;
         }
      } else {
         var10001 = false;
      }
   }
}
