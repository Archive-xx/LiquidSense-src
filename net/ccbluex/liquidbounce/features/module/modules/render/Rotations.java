//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.render;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.combat.Aura;
import net.ccbluex.liquidbounce.features.module.modules.combat.BowAimbot;
import net.ccbluex.liquidbounce.features.module.modules.fun.Derp;
import net.ccbluex.liquidbounce.features.module.modules.world.ChestAura;
import net.ccbluex.liquidbounce.features.module.modules.world.Fucker;
import net.ccbluex.liquidbounce.features.module.modules.world.Scaffold;
import net.ccbluex.liquidbounce.features.module.modules.world.Tower;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C03PacketPlayer.C05PacketPlayerLook;
import net.minecraft.network.play.client.C03PacketPlayer.C06PacketPlayerPosLook;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\f\u001a\u0004\u0018\u00010\u0004J\u0014\u0010\r\u001a\u00020\u000e2\n\u0010\u000f\u001a\u0006\u0012\u0002\b\u00030\u0010H\u0002J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0007J\u0010\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0016H\u0007J\b\u0010\u0017\u001a\u00020\u000eH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u0007R\u0016\u0010\b\u001a\u0004\u0018\u00010\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b¨\u0006\u0018"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/render/Rotations;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "playerYaw", "", "Ljava/lang/Float;", "tag", "", "getTag", "()Ljava/lang/String;", "getModeValue", "getState", "", "module", "Ljava/lang/Class;", "onPacket", "", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onRender3D", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "shouldRotate", "LiquidSense"}
)
@ModuleInfo(
   name = "Rotations",
   description = "Allows you to see server-sided head and body rotations.",
   category = ModuleCategory.RENDER
)
public final class Rotations extends Module {
   // $FF: synthetic field
   private final ListValue modeValue = new ListValue("Mode", new String[]{"LiquidBounce", "Other"}, "Other");
   // $FF: synthetic field
   private Float playerYaw;

   private final boolean getState(Class<?> llllllllllllllllllIlIlIlIllIIIlI) {
      Module var10000 = LiquidBounce.INSTANCE.getModuleManager().get(llllllllllllllllllIlIlIlIllIIIlI);
      if (var10000 == null) {
         Intrinsics.throwNpe();
      }

      return var10000.getState();
   }

   private final boolean shouldRotate() {
      Module var10000 = LiquidBounce.INSTANCE.getModuleManager().getModule(Aura.class);
      if (var10000 == null) {
         throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.combat.Aura");
      } else {
         Aura llllllllllllllllllIlIlIlIlIlIIII = (Aura)var10000;
         return llllllllllllllllllIlIlIlIlIIllll.getState(Scaffold.class) || llllllllllllllllllIlIlIlIlIIllll.getState(Tower.class) || llllllllllllllllllIlIlIlIlIIllll.getState(Aura.class) && llllllllllllllllllIlIlIlIlIlIIII.getTarget() != null || llllllllllllllllllIlIlIlIlIIllll.getState(Derp.class) || llllllllllllllllllIlIlIlIlIIllll.getState(BowAimbot.class) || llllllllllllllllllIlIlIlIlIIllll.getState(Fucker.class) || llllllllllllllllllIlIlIlIlIIllll.getState(ChestAura.class);
      }
   }

   @Nullable
   public String getTag() {
      return (String)llllllllllllllllllIlIlIlIlIIlIll.modeValue.get();
   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   @EventTarget
   public final void onPacket(@NotNull PacketEvent llllllllllllllllllIlIlIlIlllIlIl) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIlIlIlIlllIlIl, "event");
      if (StringsKt.equals((String)llllllllllllllllllIlIlIlIllllIII.modeValue.get(), "LiquidBounce", true)) {
         if (!llllllllllllllllllIlIlIlIllllIII.shouldRotate() || access$getMc$p$s1046033730().thePlayer == null) {
            return;
         }

         Packet llllllllllllllllllIlIlIllIIIIIIl = llllllllllllllllllIlIlIlIlllIlIl.getPacket();
         if (!(llllllllllllllllllIlIlIllIIIIIIl instanceof C06PacketPlayerPosLook) && !(llllllllllllllllllIlIlIllIIIIIIl instanceof C05PacketPlayerLook)) {
            if (llllllllllllllllllIlIlIlIllllIII.playerYaw != null) {
               EntityPlayerSP var10000 = access$getMc$p$s1046033730().thePlayer;
               Float var10001 = llllllllllllllllllIlIlIlIllllIII.playerYaw;
               if (var10001 == null) {
                  Intrinsics.throwNpe();
               }

               var10000.renderYawOffset = var10001;
            }

            access$getMc$p$s1046033730().thePlayer.rotationYawHead = access$getMc$p$s1046033730().thePlayer.renderYawOffset;
         } else {
            llllllllllllllllllIlIlIlIllllIII.playerYaw = ((C03PacketPlayer)llllllllllllllllllIlIlIllIIIIIIl).yaw;
            access$getMc$p$s1046033730().thePlayer.renderYawOffset = ((C03PacketPlayer)llllllllllllllllllIlIlIllIIIIIIl).getYaw();
            access$getMc$p$s1046033730().thePlayer.rotationYawHead = ((C03PacketPlayer)llllllllllllllllllIlIlIllIIIIIIl).getYaw();
         }
      }

   }

   @Nullable
   public final ListValue getModeValue() {
      return llllllllllllllllllIlIllIIllIIlII.modeValue;
   }

   @EventTarget
   public final void onRender3D(@NotNull Render3DEvent llllllllllllllllllIlIlIllIllIlll) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIlIlIllIllIlll, "event");
      if (RotationUtils.serverRotation != null) {
         access$getMc$p$s1046033730().thePlayer.rotationYawHead = RotationUtils.serverRotation.getYaw();
      }

      if (StringsKt.equals((String)llllllllllllllllllIlIlIllIllIllI.modeValue.get(), "Other", true)) {
         Module var10000 = LiquidBounce.INSTANCE.getModuleManager().getModule(Aura.class);
         if (var10000 == null) {
            Intrinsics.throwNpe();
         }

         if (var10000.getState()) {
            access$getMc$p$s1046033730().thePlayer.rotationYawHead = RotationUtils.serverRotation.getYaw();
            access$getMc$p$s1046033730().thePlayer.renderYawOffset = RotationUtils.serverRotation.getYaw();
         }
      }

   }
}
