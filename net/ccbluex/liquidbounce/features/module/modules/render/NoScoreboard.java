package net.ccbluex.liquidbounce.features.module.modules.render;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/render/NoScoreboard;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "LiquidSense"}
)
@ModuleInfo(
   name = "NoScoreboard",
   description = "Disables the scoreboard.",
   category = ModuleCategory.RENDER
)
public final class NoScoreboard extends Module {
   // $FF: synthetic field
   public static final NoScoreboard INSTANCE;

   static {
      NoScoreboard llIlIIIIIIIllll = new NoScoreboard();
      INSTANCE = llIlIIIIIIIllll;
   }

   private NoScoreboard() {
   }
}
