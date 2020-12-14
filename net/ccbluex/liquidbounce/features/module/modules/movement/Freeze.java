//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\u0010\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0007¨\u0006\b"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/Freeze;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "onDisable", "", "onUpdate", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidSense"}
)
@ModuleInfo(
   name = "Freeze",
   description = "Allows you to stay stuck in mid air.",
   category = ModuleCategory.MOVEMENT
)
public final class Freeze extends Module {
   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent llllllllllllllllllIllIIIIIlIllII) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIllIIIIIlIllII, "event");
      access$getMc$p$s1046033730().thePlayer.isDead = true;
      access$getMc$p$s1046033730().thePlayer.rotationYaw = access$getMc$p$s1046033730().thePlayer.cameraYaw;
      access$getMc$p$s1046033730().thePlayer.rotationPitch = access$getMc$p$s1046033730().thePlayer.cameraPitch;
   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   public void onDisable() {
      EntityPlayerSP var10000 = access$getMc$p$s1046033730().thePlayer;
      if (var10000 != null) {
         var10000.isDead = false;
      } else {
         boolean var10001 = false;
      }

   }
}
