package net.ccbluex.liquidbounce.features.module.modules.render;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0003"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/render/NoHurtCam;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "LiquidSense"}
)
@ModuleInfo(
   name = "NoHurtCam",
   description = "Disables hurt cam effect when getting hurt.",
   category = ModuleCategory.RENDER
)
public final class NoHurtCam extends Module {
}
