package net.ccbluex.liquidbounce.utils.timer;

import net.ccbluex.liquidbounce.utils.misc.RandomUtils;

public final class TimeUtils {
   // $FF: synthetic field
   private long lastMS;

   public static long randomClickDelay(int llIIIlllIlllIlI, int llIIIlllIlllIIl) {
      return (long)(Math.random() * (double)(1000 / llIIIlllIlllIlI - 1000 / llIIIlllIlllIIl + 1) + (double)(1000 / llIIIlllIlllIIl));
   }

   public void reset() {
      llIIIlllIlIlIlI.lastMS = llIIIlllIlIlIlI.getCurrentMS();
   }

   public static long getTime() {
      return System.nanoTime() / 1000000L;
   }

   public boolean hasReached(double llIIIlllIllIIlI) {
      return (double)(llIIIlllIllIlIl.getCurrentMS() - llIIIlllIllIlIl.lastMS) >= llIIIlllIllIIlI;
   }

   public boolean sleep(long llIIIlllIIlllIl) {
      if (getTime() >= llIIIlllIIlllIl) {
         llIIIlllIIllllI.reset();
         return true;
      } else {
         return false;
      }
   }

   public boolean sleep(double llIIIlllIIllIIl) {
      if ((double)getTime() >= llIIIlllIIllIIl) {
         llIIIlllIIllIII.reset();
         return true;
      } else {
         return false;
      }
   }

   private long getCurrentMS() {
      return System.nanoTime() / 1000000L;
   }

   public static long randomDelay(int llIIIllllIIIIII, int llIIIllllIIIIIl) {
      return (long)RandomUtils.nextInt(llIIIllllIIIIII, llIIIllllIIIIIl);
   }

   public boolean delay(float llIIIlllIlIIIll) {
      return (float)(getTime() - llIIIlllIlIIllI.lastMS) >= llIIIlllIlIIIll;
   }

   public boolean hasReached(long llIIIlllIlIllII) {
      return (double)(llIIIlllIlIllIl.getCurrentMS() - llIIIlllIlIllIl.lastMS) >= (double)llIIIlllIlIllII;
   }
}
