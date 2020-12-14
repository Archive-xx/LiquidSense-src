//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.misc;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer.C05PacketPlayerLook;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/misc/NoRotateSet;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "confirmValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "illegalRotationValue", "noZeroValue", "onPacket", "", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "LiquidSense"}
)
@ModuleInfo(
   name = "NoRotateSet",
   description = "Prevents the server from rotating your head.",
   category = ModuleCategory.MISC
)
public final class NoRotateSet extends Module {
   // $FF: synthetic field
   private final BoolValue confirmValue = new BoolValue("Confirm", true);
   // $FF: synthetic field
   private final BoolValue illegalRotationValue = new BoolValue("ConfirmIllegalRotation", false);
   // $FF: synthetic field
   private final BoolValue noZeroValue = new BoolValue("NoZero", false);

   @EventTarget
   public final void onPacket(@NotNull PacketEvent lllllllllllllllllIlllIlIIIIlIlll) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllIlllIlIIIIlIlll, "event");
      Exception lllllllllllllllllIlllIlIIIIlIlII = lllllllllllllllllIlllIlIIIIlIlll.getPacket();
      boolean var10001;
      if (access$getMc$p$s1046033730().thePlayer == null) {
         var10001 = false;
      } else {
         var10001 = false;
         if (lllllllllllllllllIlllIlIIIIlIlII instanceof S08PacketPlayerPosLook) {
            if ((Boolean)lllllllllllllllllIlllIlIIIIlIllI.noZeroValue.get() && ((S08PacketPlayerPosLook)lllllllllllllllllIlllIlIIIIlIlII).getYaw() == 0.0F && ((S08PacketPlayerPosLook)lllllllllllllllllIlllIlIIIIlIlII).getPitch() == 0.0F) {
               return;
            }

            if (((Boolean)lllllllllllllllllIlllIlIIIIlIllI.illegalRotationValue.get() || ((S08PacketPlayerPosLook)lllllllllllllllllIlllIlIIIIlIlII).getPitch() <= (float)90 && ((S08PacketPlayerPosLook)lllllllllllllllllIlllIlIIIIlIlII).getPitch() >= (float)-90 && RotationUtils.serverRotation != null && ((S08PacketPlayerPosLook)lllllllllllllllllIlllIlIIIIlIlII).getYaw() != RotationUtils.serverRotation.getYaw() && ((S08PacketPlayerPosLook)lllllllllllllllllIlllIlIIIIlIlII).getPitch() != RotationUtils.serverRotation.getPitch()) && (Boolean)lllllllllllllllllIlllIlIIIIlIllI.confirmValue.get()) {
               Minecraft var10000 = access$getMc$p$s1046033730();
               Intrinsics.checkExpressionValueIsNotNull(var10000, "mc");
               var10000.getNetHandler().addToSendQueue((Packet)(new C05PacketPlayerLook(((S08PacketPlayerPosLook)lllllllllllllllllIlllIlIIIIlIlII).getYaw(), ((S08PacketPlayerPosLook)lllllllllllllllllIlllIlIIIIlIlII).getPitch(), access$getMc$p$s1046033730().thePlayer.onGround)));
            }

            ((S08PacketPlayerPosLook)lllllllllllllllllIlllIlIIIIlIlII).yaw = access$getMc$p$s1046033730().thePlayer.rotationYaw;
            ((S08PacketPlayerPosLook)lllllllllllllllllIlllIlIIIIlIlII).pitch = access$getMc$p$s1046033730().thePlayer.rotationPitch;
         }

      }
   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }
}
