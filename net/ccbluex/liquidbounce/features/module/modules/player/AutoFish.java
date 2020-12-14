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
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
   name = "AutoFish",
   description = "Automatically catches fish when using a rod.",
   category = ModuleCategory.PLAYER
)
@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/AutoFish;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "rodOutTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidSense"}
)
public final class AutoFish extends Module {
   // $FF: synthetic field
   private final MSTimer rodOutTimer = new MSTimer();

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent llIIllIlIIlI) {
      Intrinsics.checkParameterIsNotNull(llIIllIlIIlI, "event");
      EntityPlayerSP var10000 = access$getMc$p$s1046033730().thePlayer;
      Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
      if (var10000.getHeldItem() != null) {
         var10000 = access$getMc$p$s1046033730().thePlayer;
         Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
         ItemStack var2 = var10000.getHeldItem();
         Intrinsics.checkExpressionValueIsNotNull(var2, "mc.thePlayer.heldItem");
         if (var2.getItem() instanceof ItemFishingRod) {
            if (llIIllIlIIIl.rodOutTimer.hasTimePassed(500L) && access$getMc$p$s1046033730().thePlayer.fishEntity == null || access$getMc$p$s1046033730().thePlayer.fishEntity != null && access$getMc$p$s1046033730().thePlayer.fishEntity.motionX == 0.0D && access$getMc$p$s1046033730().thePlayer.fishEntity.motionZ == 0.0D && access$getMc$p$s1046033730().thePlayer.fishEntity.motionY != 0.0D) {
               access$getMc$p$s1046033730().rightClickMouse();
               llIIllIlIIIl.rodOutTimer.reset();
            }

            return;
         }
      }

   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }
}
