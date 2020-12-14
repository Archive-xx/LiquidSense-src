package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0007\u001a\u00020\bR\u001e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\t"},
   d2 = {"Lnet/ccbluex/liquidbounce/event/CancellableEvent;", "Lnet/ccbluex/liquidbounce/event/Event;", "()V", "<set-?>", "", "isCancelled", "()Z", "cancelEvent", "", "LiquidSense"}
)
public class CancellableEvent extends Event {
   // $FF: synthetic field
   private boolean isCancelled;

   public final boolean isCancelled() {
      return llllllllllllllllllIIlIIIlllIllII.isCancelled;
   }

   public final void cancelEvent() {
      llllllllllllllllllIIlIIIlllIlIII.isCancelled = true;
   }
}
