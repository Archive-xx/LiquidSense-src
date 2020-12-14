//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.fun;

import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EnumPlayerModelParts;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u0012H\u0016J\u0010\u0010\u0014\u001a\u00020\u00122\u0006\u0010\u0015\u001a\u00020\u0016H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/fun/SkinDerp;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "delayValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "hatValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "jacketValue", "leftPantsValue", "leftSleeveValue", "prevModelParts", "", "Lnet/minecraft/entity/player/EnumPlayerModelParts;", "rightPantsValue", "rightSleeveValue", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "onDisable", "", "onEnable", "onUpdate", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidSense"}
)
@ModuleInfo(
   name = "SkinDerp",
   description = "Makes your skin blink (Requires multi-layer skin).",
   category = ModuleCategory.FUN
)
public final class SkinDerp extends Module {
   // $FF: synthetic field
   private final MSTimer timer = new MSTimer();
   // $FF: synthetic field
   private Set<? extends EnumPlayerModelParts> prevModelParts = SetsKt.emptySet();
   // $FF: synthetic field
   private final BoolValue rightSleeveValue = new BoolValue("RightSleeve", true);
   // $FF: synthetic field
   private final BoolValue leftSleeveValue = new BoolValue("LeftSleeve", true);
   // $FF: synthetic field
   private final IntegerValue delayValue = new IntegerValue("Delay", 0, 0, 1000);
   // $FF: synthetic field
   private final BoolValue leftPantsValue = new BoolValue("LeftPants", true);
   // $FF: synthetic field
   private final BoolValue jacketValue = new BoolValue("Jacket", true);
   // $FF: synthetic field
   private final BoolValue rightPantsValue = new BoolValue("RightPants", true);
   // $FF: synthetic field
   private final BoolValue hatValue = new BoolValue("Hat", true);

   public void onDisable() {
      GameSettings var10000 = access$getMc$p$s1046033730().gameSettings;
      Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.gameSettings");
      Iterator llIIIlIllIIlIll = var10000.getModelParts().iterator();

      EnumPlayerModelParts llIIIlIllIIllIl;
      while(llIIIlIllIIlIll.hasNext()) {
         llIIIlIllIIllIl = (EnumPlayerModelParts)llIIIlIllIIlIll.next();
         access$getMc$p$s1046033730().gameSettings.setModelPartEnabled(llIIIlIllIIllIl, false);
      }

      llIIIlIllIIlIll = llIIIlIllIIllll.prevModelParts.iterator();

      while(llIIIlIllIIlIll.hasNext()) {
         llIIIlIllIIllIl = (EnumPlayerModelParts)llIIIlIllIIlIll.next();
         access$getMc$p$s1046033730().gameSettings.setModelPartEnabled(llIIIlIllIIllIl, true);
      }

      super.onDisable();
   }

   public void onEnable() {
      GameSettings var10001 = access$getMc$p$s1046033730().gameSettings;
      Intrinsics.checkExpressionValueIsNotNull(var10001, "mc.gameSettings");
      Set var1 = var10001.getModelParts();
      Intrinsics.checkExpressionValueIsNotNull(var1, "mc.gameSettings.modelParts");
      llIIIlIllIllIIl.prevModelParts = var1;
      super.onEnable();
   }

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent llIIIlIllIIIIlI) {
      Intrinsics.checkParameterIsNotNull(llIIIlIllIIIIlI, "event");
      if (llIIIlIllIIIIll.timer.hasTimePassed((long)((Number)llIIIlIllIIIIll.delayValue.get()).intValue())) {
         if ((Boolean)llIIIlIllIIIIll.hatValue.get()) {
            access$getMc$p$s1046033730().gameSettings.setModelPartEnabled(EnumPlayerModelParts.HAT, Random.Default.nextBoolean());
         }

         if ((Boolean)llIIIlIllIIIIll.jacketValue.get()) {
            access$getMc$p$s1046033730().gameSettings.setModelPartEnabled(EnumPlayerModelParts.JACKET, Random.Default.nextBoolean());
         }

         if ((Boolean)llIIIlIllIIIIll.leftPantsValue.get()) {
            access$getMc$p$s1046033730().gameSettings.setModelPartEnabled(EnumPlayerModelParts.LEFT_PANTS_LEG, Random.Default.nextBoolean());
         }

         if ((Boolean)llIIIlIllIIIIll.rightPantsValue.get()) {
            access$getMc$p$s1046033730().gameSettings.setModelPartEnabled(EnumPlayerModelParts.RIGHT_PANTS_LEG, Random.Default.nextBoolean());
         }

         if ((Boolean)llIIIlIllIIIIll.leftSleeveValue.get()) {
            access$getMc$p$s1046033730().gameSettings.setModelPartEnabled(EnumPlayerModelParts.LEFT_SLEEVE, Random.Default.nextBoolean());
         }

         if ((Boolean)llIIIlIllIIIIll.rightSleeveValue.get()) {
            access$getMc$p$s1046033730().gameSettings.setModelPartEnabled(EnumPlayerModelParts.RIGHT_SLEEVE, Random.Default.nextBoolean());
         }

         llIIIlIllIIIIll.timer.reset();
      }

   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }
}
