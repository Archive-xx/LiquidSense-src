package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"},
   d2 = {"Lnet/ccbluex/liquidbounce/event/MotionEvent;", "Lnet/ccbluex/liquidbounce/event/Event;", "eventState", "Lnet/ccbluex/liquidbounce/event/EventState;", "(Lnet/ccbluex/liquidbounce/event/EventState;)V", "getEventState", "()Lnet/ccbluex/liquidbounce/event/EventState;", "LiquidSense"}
)
public final class MotionEvent extends Event {
   // $FF: synthetic field
   @NotNull
   private final EventState eventState;

   public MotionEvent(@NotNull EventState llIIIllIIIIIIll) {
      Intrinsics.checkParameterIsNotNull(llIIIllIIIIIIll, "eventState");
      super();
      llIIIllIIIIIIlI.eventState = llIIIllIIIIIIll;
   }

   @NotNull
   public final EventState getEventState() {
      return llIIIllIIIlIlIl.eventState;
   }
}
