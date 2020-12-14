//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.world;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0007J\u0010\u0010\n\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u000bH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/world/Timer;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "speedValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "onDisable", "", "onUpdate", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "LiquidSense"}
)
@ModuleInfo(
   name = "Timer",
   description = "Changes the speed of the entire game.",
   category = ModuleCategory.WORLD
)
public final class Timer extends Module {
   // $FF: synthetic field
   private final FloatValue speedValue = new FloatValue("Speed", 2.0F, 0.1F, 10.0F);

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   @EventTarget
   public final void onWorld(@NotNull WorldEvent llllllIIlIlIIII) {
      Intrinsics.checkParameterIsNotNull(llllllIIlIlIIII, "event");
      if (llllllIIlIlIIII.getWorldClient() == null) {
         llllllIIlIlIIIl.setState(false);
      }
   }

   public void onDisable() {
      if (access$getMc$p$s1046033730().thePlayer != null) {
         access$getMc$p$s1046033730().timer.timerSpeed = 1.0F;
      }
   }

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent llllllIIlIlIllI) {
      Intrinsics.checkParameterIsNotNull(llllllIIlIlIllI, "event");
      access$getMc$p$s1046033730().timer.timerSpeed = ((Number)llllllIIlIllIIl.speedValue.get()).floatValue();
   }
}
