package net.ccbluex.liquidbounce.utils.timer;

public final class TimerOther {
   // $FF: synthetic field
   private long time = -1L;
   // $FF: synthetic field
   private long prevMS;

   public void reset() {
      lllllllllllllllllllllIlIIllIIIll.prevMS = lllllllllllllllllllllIlIIllIIIll.getTime();
      lllllllllllllllllllllIlIIllIIIll.time = System.currentTimeMillis();
   }

   public long hasTimeLeft(long lllllllllllllllllllllIlIIllIIlll) {
      return lllllllllllllllllllllIlIIllIIlll + lllllllllllllllllllllIlIIllIlIll.time - System.currentTimeMillis();
   }

   public boolean delay(float lllllllllllllllllllllIlIIlIllIIl) {
      return (float)(lllllllllllllllllllllIlIIlIllIII.getTime() - lllllllllllllllllllllIlIIlIllIII.prevMS) >= lllllllllllllllllllllIlIIlIllIIl;
   }

   public long getDifference() {
      return lllllllllllllllllllllIlIIlIlIlII.getTime() - lllllllllllllllllllllIlIIlIlIlII.prevMS;
   }

   public void setDifference(long lllllllllllllllllllllIlIIlIIllll) {
      lllllllllllllllllllllIlIIlIIlllI.prevMS = lllllllllllllllllllllIlIIlIIlllI.getTime() - lllllllllllllllllllllIlIIlIIllll;
   }

   public boolean hasTimePassed(long lllllllllllllllllllllIlIIlllIIll) {
      return System.currentTimeMillis() >= lllllllllllllllllllllIlIIlllIlII.time + lllllllllllllllllllllIlIIlllIIll;
   }

   public long getTime() {
      return System.nanoTime() / 1000000L;
   }

   public TimerOther() {
      lllllllllllllllllllllIlIIlllIlll.prevMS = lllllllllllllllllllllIlIIlllIlll.getTime();
   }
}
