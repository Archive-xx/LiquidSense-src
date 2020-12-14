//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.player;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.network.play.client.C03PacketPlayer.C05PacketPlayerLook;
import net.minecraft.network.play.client.C03PacketPlayer.C06PacketPlayerPosLook;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
   name = "PotionSaver",
   description = "Freezes all potion effects while you are standing still.",
   category = ModuleCategory.PLAYER
)
@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/PotionSaver;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "onPacket", "", "e", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "LiquidSense"}
)
public final class PotionSaver extends Module {
   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   @EventTarget
   public final void onPacket(@NotNull PacketEvent lIIlllIlll) {
      Intrinsics.checkParameterIsNotNull(lIIlllIlll, "e");
      boolean lIIlllIllI = lIIlllIlll.getPacket();
      if (lIIlllIllI instanceof C03PacketPlayer && !(lIIlllIllI instanceof C04PacketPlayerPosition) && !(lIIlllIllI instanceof C06PacketPlayerPosLook) && !(lIIlllIllI instanceof C05PacketPlayerLook) && access$getMc$p$s1046033730().thePlayer != null) {
         EntityPlayerSP var10000 = access$getMc$p$s1046033730().thePlayer;
         Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
         if (!var10000.isUsingItem()) {
            lIIlllIlll.cancelEvent();
         }
      }

   }
}
