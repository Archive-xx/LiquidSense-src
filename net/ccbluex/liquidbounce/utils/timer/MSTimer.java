package net.ccbluex.liquidbounce.utils.timer;

public final class MSTimer {
   // $FF: synthetic field
   private long lastMs;
   // $FF: synthetic field
   private long time = -1L;

   public void Reset() {
      lllllllllIIIIII.lastMs = System.currentTimeMillis();
   }

   public boolean hasTimePassed(long llllllllIlllIlI) {
      return System.currentTimeMillis() >= llllllllIlllIll.time + llllllllIlllIlI;
   }

   public boolean hasPassed(double llllllllIIIlllI) {
      return (double)(llllllllIIIllIl.getTime() - llllllllIIIllIl.lastMs) >= llllllllIIIlllI;
   }

   public long getTime() {
      return System.nanoTime() / 1000000L;
   }

   public void reSet() {
      llllllllIIllllI.lastMs = llllllllIIllllI.getTime();
   }

   public void setLastMs(int llllllllIlIlIlI) {
      llllllllIlIlIIl.lastMs = System.currentTimeMillis() + (long)llllllllIlIlIlI;
   }

   public long hasTimeLeft(long llllllllIllIlII) {
      return llllllllIllIlII + llllllllIllIlll.time - System.currentTimeMillis();
   }

   public boolean hasPassed(double llllllllIIllIII, boolean llllllllIIlIIll) {
      float llllllllIIlIIlI = (double)(llllllllIIlIlIl.getTime() - llllllllIIlIlIl.lastMs) >= llllllllIIllIII;
      if (llllllllIIlIIll) {
         llllllllIIlIlIl.reSet();
      }

      return llllllllIIlIIlI;
   }

   public long getLastMs() {
      return llllllllIlIllll.lastMs;
   }

   public void reset() {
      llllllllIllIIIl.time = System.currentTimeMillis();
   }

   public double getPassed() {
      return (double)(llllllllIlIIIlI.getTime() - llllllllIlIIIlI.lastMs);
   }

   public boolean isDelayComplete(long lllllllllIIIlIl) {
      return System.currentTimeMillis() - lllllllllIIIlII.lastMs > lllllllllIIIlIl;
   }

   public void setLastMS() {
      llllllllIlIIllI.lastMs = System.currentTimeMillis();
   }
}
