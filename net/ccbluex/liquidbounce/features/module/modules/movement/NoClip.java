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
import net.minecraft.client.settings.KeyBinding;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
   name = "NoClip",
   description = "Allows you to freely move through walls (A sandblock has to fall on your head).",
   category = ModuleCategory.MOVEMENT
)
@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\u0010\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0007¨\u0006\b"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/NoClip;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "onDisable", "", "onUpdate", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidSense"}
)
public final class NoClip extends Module {
   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent lIIIlIlIIlIIlIl) {
      Intrinsics.checkParameterIsNotNull(lIIIlIlIIlIIlIl, "event");
      access$getMc$p$s1046033730().thePlayer.noClip = true;
      access$getMc$p$s1046033730().thePlayer.fallDistance = 0.0F;
      access$getMc$p$s1046033730().thePlayer.onGround = false;
      access$getMc$p$s1046033730().thePlayer.capabilities.isFlying = false;
      access$getMc$p$s1046033730().thePlayer.motionX = 0.0D;
      access$getMc$p$s1046033730().thePlayer.motionY = 0.0D;
      access$getMc$p$s1046033730().thePlayer.motionZ = 0.0D;
      float lIIIlIlIIlIIlll = 0.32F;
      access$getMc$p$s1046033730().thePlayer.jumpMovementFactor = lIIIlIlIIlIIlll;
      KeyBinding var10000 = access$getMc$p$s1046033730().gameSettings.keyBindJump;
      Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.gameSettings.keyBindJump");
      EntityPlayerSP var3;
      if (var10000.isKeyDown()) {
         var3 = access$getMc$p$s1046033730().thePlayer;
         var3.motionY += (double)lIIIlIlIIlIIlll;
      }

      var10000 = access$getMc$p$s1046033730().gameSettings.keyBindSneak;
      Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.gameSettings.keyBindSneak");
      if (var10000.isKeyDown()) {
         var3 = access$getMc$p$s1046033730().thePlayer;
         var3.motionY -= (double)lIIIlIlIIlIIlll;
      }

   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   public void onDisable() {
      EntityPlayerSP var10000 = access$getMc$p$s1046033730().thePlayer;
      if (var10000 != null) {
         var10000.noClip = false;
      } else {
         boolean var10001 = false;
      }

   }
}
