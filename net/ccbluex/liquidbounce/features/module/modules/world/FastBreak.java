//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.world;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
   name = "FastBreak",
   description = "Allows you to break blocks faster.",
   category = ModuleCategory.WORLD
)
@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/world/FastBreak;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "breakDamage", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidSense"}
)
public final class FastBreak extends Module {
   // $FF: synthetic field
   private final FloatValue breakDamage = new FloatValue("BreakDamage", 0.8F, 0.1F, 1.0F);

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent lIlllllIlllIIIl) {
      Intrinsics.checkParameterIsNotNull(lIlllllIlllIIIl, "event");
      access$getMc$p$s1046033730().playerController.blockHitDelay = 0;
      if (access$getMc$p$s1046033730().playerController.curBlockDamageMP > ((Number)lIlllllIlllIIII.breakDamage.get()).floatValue()) {
         access$getMc$p$s1046033730().playerController.curBlockDamageMP = 1.0F;
      }

      if (Fucker.INSTANCE.getCurrentDamage() > ((Number)lIlllllIlllIIII.breakDamage.get()).floatValue()) {
         Fucker.INSTANCE.setCurrentDamage(1.0F);
      }

   }
}
