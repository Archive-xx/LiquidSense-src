package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\n\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\r"},
   d2 = {"Lnet/ccbluex/liquidbounce/event/ClickWindowEvent;", "Lnet/ccbluex/liquidbounce/event/CancellableEvent;", "windowId", "", "slotId", "mouseButtonClicked", "mode", "(IIII)V", "getMode", "()I", "getMouseButtonClicked", "getSlotId", "getWindowId", "LiquidSense"}
)
public final class ClickWindowEvent extends CancellableEvent {
   // $FF: synthetic field
   private final int windowId;
   // $FF: synthetic field
   private final int mouseButtonClicked;
   // $FF: synthetic field
   private final int slotId;
   // $FF: synthetic field
   private final int mode;

   public ClickWindowEvent(int lIlIIIIIlIIlIll, int lIlIIIIIlIIlIlI, int lIlIIIIIlIIlllI, int lIlIIIIIlIIllIl) {
      lIlIIIIIlIlIIIl.windowId = lIlIIIIIlIIlIll;
      lIlIIIIIlIlIIIl.slotId = lIlIIIIIlIIlIlI;
      lIlIIIIIlIlIIIl.mouseButtonClicked = lIlIIIIIlIIlllI;
      lIlIIIIIlIlIIIl.mode = lIlIIIIIlIIllIl;
   }

   public final int getSlotId() {
      return lIlIIIIIlIlllIl.slotId;
   }

   public final int getMouseButtonClicked() {
      return lIlIIIIIlIllIll.mouseButtonClicked;
   }

   public final int getMode() {
      return lIlIIIIIlIlIlll.mode;
   }

   public final int getWindowId() {
      return lIlIIIIIllIIIII.windowId;
   }
}
