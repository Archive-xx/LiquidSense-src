//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.player;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
   name = "AntiAFK",
   description = "Prevents you from getting kicked for being AFK.",
   category = ModuleCategory.PLAYER
)
@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/AntiAFK;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "onDisable", "", "onUpdate", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidSense"}
)
public final class AntiAFK extends Module {
   // $FF: synthetic field
   private final MSTimer timer = new MSTimer();

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent lllllllllllllllllIllIllIIlIIllll) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllIllIllIIlIIllll, "event");
      access$getMc$p$s1046033730().gameSettings.keyBindForward.pressed = true;
      if (lllllllllllllllllIllIllIIlIlIIlI.timer.hasTimePassed(500L)) {
         EntityPlayerSP var10000 = access$getMc$p$s1046033730().thePlayer;
         var10000.rotationYaw += 180.0F;
         lllllllllllllllllIllIllIIlIlIIlI.timer.reset();
      }

   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   public void onDisable() {
      if (!GameSettings.isKeyDown(access$getMc$p$s1046033730().gameSettings.keyBindForward)) {
         access$getMc$p$s1046033730().gameSettings.keyBindForward.pressed = false;
      }

   }
}
