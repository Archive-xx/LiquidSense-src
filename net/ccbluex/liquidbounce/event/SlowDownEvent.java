package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\t\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0007\"\u0004\b\u000b\u0010\t¨\u0006\f"},
   d2 = {"Lnet/ccbluex/liquidbounce/event/SlowDownEvent;", "Lnet/ccbluex/liquidbounce/event/Event;", "strafe", "", "forward", "(FF)V", "getForward", "()F", "setForward", "(F)V", "getStrafe", "setStrafe", "LiquidSense"}
)
public final class SlowDownEvent extends Event {
   // $FF: synthetic field
   private float strafe;
   // $FF: synthetic field
   private float forward;

   public final void setStrafe(float lIIIIIllIlIIIII) {
      lIIIIIllIIlllll.strafe = lIIIIIllIlIIIII;
   }

   public final float getForward() {
      return lIIIIIllIIlllII.forward;
   }

   public final float getStrafe() {
      return lIIIIIllIlIIlII.strafe;
   }

   public SlowDownEvent(float lIIIIIllIIlIIII, float lIIIIIllIIIllll) {
      lIIIIIllIIlIIIl.strafe = lIIIIIllIIlIIII;
      lIIIIIllIIlIIIl.forward = lIIIIIllIIIllll;
   }

   public final void setForward(float lIIIIIllIIlIlll) {
      lIIIIIllIIlIllI.forward = lIIIIIllIIlIlll;
   }
}
