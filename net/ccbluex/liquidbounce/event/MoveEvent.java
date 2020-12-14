package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003¢\u0006\u0002\u0010\u0006J\u0006\u0010\u0014\u001a\u00020\u0015J\u0006\u0010\u0016\u001a\u00020\u0015R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\t\"\u0004\b\n\u0010\u000bR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\r\"\u0004\b\u0011\u0010\u000fR\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\r\"\u0004\b\u0013\u0010\u000f¨\u0006\u0017"},
   d2 = {"Lnet/ccbluex/liquidbounce/event/MoveEvent;", "Lnet/ccbluex/liquidbounce/event/CancellableEvent;", "x", "", "y", "z", "(DDD)V", "isSafeWalk", "", "()Z", "setSafeWalk", "(Z)V", "getX", "()D", "setX", "(D)V", "getY", "setY", "getZ", "setZ", "zero", "", "zeroXZ", "LiquidSense"}
)
public final class MoveEvent extends CancellableEvent {
   // $FF: synthetic field
   private boolean isSafeWalk;
   // $FF: synthetic field
   private double x;
   // $FF: synthetic field
   private double z;
   // $FF: synthetic field
   private double y;

   public final double getZ() {
      return lllllllllllllllllllllIIlllIIlIIl.z;
   }

   public final void setZ(double lllllllllllllllllllllIIlllIIIIll) {
      lllllllllllllllllllllIIlllIIIlII.z = lllllllllllllllllllllIIlllIIIIll;
   }

   public final double getX() {
      return lllllllllllllllllllllIIlllIlllII.x;
   }

   public final void setSafeWalk(boolean lllllllllllllllllllllIIllllIIlII) {
      lllllllllllllllllllllIIllllIIlIl.isSafeWalk = lllllllllllllllllllllIIllllIIlII;
   }

   public final void zeroXZ() {
      lllllllllllllllllllllIIlllIlllll.x = 0.0D;
      lllllllllllllllllllllIIlllIlllll.z = 0.0D;
   }

   public final void zero() {
      lllllllllllllllllllllIIllllIIIIl.x = 0.0D;
      lllllllllllllllllllllIIllllIIIIl.y = 0.0D;
      lllllllllllllllllllllIIllllIIIIl.z = 0.0D;
   }

   public final void setY(double lllllllllllllllllllllIIlllIIllII) {
      lllllllllllllllllllllIIlllIIllIl.y = lllllllllllllllllllllIIlllIIllII;
   }

   public final boolean isSafeWalk() {
      return lllllllllllllllllllllIIllllIlIlI.isSafeWalk;
   }

   public final void setX(double lllllllllllllllllllllIIlllIlIlIl) {
      lllllllllllllllllllllIIlllIllIII.x = lllllllllllllllllllllIIlllIlIlIl;
   }

   public final double getY() {
      return lllllllllllllllllllllIIlllIlIIll.y;
   }

   public MoveEvent(double lllllllllllllllllllllIIllIllllIl, double lllllllllllllllllllllIIllIlllIII, double lllllllllllllllllllllIIllIllIlll) {
      lllllllllllllllllllllIIllIlllllI.x = lllllllllllllllllllllIIllIllllIl;
      lllllllllllllllllllllIIllIlllllI.y = lllllllllllllllllllllIIllIlllIII;
      lllllllllllllllllllllIIllIlllllI.z = lllllllllllllllllllllIIllIllIlll;
   }
}
