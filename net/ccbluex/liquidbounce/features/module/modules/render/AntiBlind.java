package net.ccbluex.liquidbounce.features.module.modules.render;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.BoolValue;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
   name = "AntiBlind",
   description = "Cancels blindness effects.",
   category = ModuleCategory.RENDER
)
@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006¨\u0006\u000b"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/render/AntiBlind;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "confusionEffect", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "getConfusionEffect", "()Lnet/ccbluex/liquidbounce/value/BoolValue;", "fireEffect", "getFireEffect", "pumpkinEffect", "getPumpkinEffect", "LiquidSense"}
)
public final class AntiBlind extends Module {
   // $FF: synthetic field
   @NotNull
   private final BoolValue confusionEffect = new BoolValue("Confusion", true);
   // $FF: synthetic field
   @NotNull
   private final BoolValue fireEffect = new BoolValue("Fire", false);
   // $FF: synthetic field
   @NotNull
   private final BoolValue pumpkinEffect = new BoolValue("Pumpkin", true);

   @NotNull
   public final BoolValue getConfusionEffect() {
      return lllllllllllllllllllllIIIllllIlII.confusionEffect;
   }

   @NotNull
   public final BoolValue getFireEffect() {
      return lllllllllllllllllllllIIIlllIllll.fireEffect;
   }

   @NotNull
   public final BoolValue getPumpkinEffect() {
      return lllllllllllllllllllllIIIllllIIlI.pumpkinEffect;
   }
}
