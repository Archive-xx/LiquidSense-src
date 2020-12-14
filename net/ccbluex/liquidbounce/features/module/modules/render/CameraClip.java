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
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/render/CameraClip;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "LiquidSense"}
)
@ModuleInfo(
   name = "CameraClip",
   description = "Allows you to see through walls in third person view.",
   category = ModuleCategory.RENDER
)
public final class CameraClip extends Module {
}
