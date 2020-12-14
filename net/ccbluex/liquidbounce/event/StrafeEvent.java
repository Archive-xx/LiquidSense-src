package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\b¨\u0006\u000b"},
   d2 = {"Lnet/ccbluex/liquidbounce/event/StrafeEvent;", "Lnet/ccbluex/liquidbounce/event/CancellableEvent;", "strafe", "", "forward", "friction", "(FFF)V", "getForward", "()F", "getFriction", "getStrafe", "LiquidSense"}
)
public final class StrafeEvent extends CancellableEvent {
   // $FF: synthetic field
   private final float strafe;
   // $FF: synthetic field
   private final float friction;
   // $FF: synthetic field
   private final float forward;

   public final float getStrafe() {
      return llllllllllllllllllIllllIIlIlIIIl.strafe;
   }

   public StrafeEvent(float llllllllllllllllllIllllIIlIIIlII, float llllllllllllllllllIllllIIIllllll, float llllllllllllllllllIllllIIIlllllI) {
      llllllllllllllllllIllllIIlIIIIIl.strafe = llllllllllllllllllIllllIIlIIIlII;
      llllllllllllllllllIllllIIlIIIIIl.forward = llllllllllllllllllIllllIIIllllll;
      llllllllllllllllllIllllIIlIIIIIl.friction = llllllllllllllllllIllllIIIlllllI;
   }

   public final float getForward() {
      return llllllllllllllllllIllllIIlIIlllI.forward;
   }

   public final float getFriction() {
      return llllllllllllllllllIllllIIlIIlIlI.friction;
   }
}
