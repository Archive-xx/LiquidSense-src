package net.ccbluex.liquidbounce.features.module.modules.misc;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0003"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/misc/ComponentOnHover;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "LiquidSense"}
)
@ModuleInfo(
   name = "ComponentOnHover",
   description = "Allows you to see onclick action and value of chat message components when hovered.",
   category = ModuleCategory.MISC
)
public final class ComponentOnHover extends Module {
}
