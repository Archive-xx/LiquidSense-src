package net.ccbluex.liquidbounce.features.module.modules.movement;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/Strafe;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "onMotion", "", "event", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "LiquidSense"}
)
@ModuleInfo(
   name = "Strafe",
   description = "Allows you to freely move in mid air.",
   category = ModuleCategory.MOVEMENT
)
public final class Strafe extends Module {
   @EventTarget
   public final void onMotion(@NotNull MotionEvent llllllllllllllllllllIIlIlIIIIllI) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllllIIlIlIIIIllI, "event");
      if (llllllllllllllllllllIIlIlIIIIllI.getEventState() != EventState.POST) {
         MovementUtils.strafe();
      }
   }
}
