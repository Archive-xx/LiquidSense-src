package net.ccbluex.liquidbounce.features.module.modules.world;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.IntegerValue;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
   name = "FastPlace",
   description = "Allows you to place blocks faster.",
   category = ModuleCategory.WORLD
)
@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/world/FastPlace;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "speedValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "getSpeedValue", "()Lnet/ccbluex/liquidbounce/value/IntegerValue;", "LiquidSense"}
)
public final class FastPlace extends Module {
   // $FF: synthetic field
   @NotNull
   private final IntegerValue speedValue = new IntegerValue("Speed", 0, 0, 4);

   @NotNull
   public final IntegerValue getSpeedValue() {
      return lllllllllllllllllllIIlllllllIIlI.speedValue;
   }
}
