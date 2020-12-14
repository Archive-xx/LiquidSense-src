package net.ccbluex.liquidbounce.features.module.modules.render;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.BoolValue;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/render/NoSwing;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "serverSideValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "getServerSideValue", "()Lnet/ccbluex/liquidbounce/value/BoolValue;", "LiquidSense"}
)
@ModuleInfo(
   name = "NoSwing",
   description = "Disabled swing effect when hitting an entity/mining a block.",
   category = ModuleCategory.RENDER
)
public final class NoSwing extends Module {
   // $FF: synthetic field
   @NotNull
   private final BoolValue serverSideValue = new BoolValue("ServerSide", true);

   @NotNull
   public final BoolValue getServerSideValue() {
      return lIllllIlIIllII.serverSideValue;
   }
}
