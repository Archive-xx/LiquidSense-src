package net.ccbluex.liquidbounce.utils.timer;

import java.util.TimerTask;

public class TimerUtil {
   // $FF: synthetic field
   private long time;
   // $FF: synthetic field
   protected static long lastMS;
   // $FF: synthetic field
   private long lastMS1;
   // $FF: synthetic field
   static long prevMS;

   public long time() {
      return System.nanoTime() / 1000000L - llIlIIIIllIl.time;
   }

   public boolean hasTimeElapsed(long llIIlllIlIIl) {
      return llIIlllIlIlI.time() >= llIIlllIlIIl;
   }

   public static long getTime() {
      return System.nanoTime() / 1000000L;
   }

   public long getLastMs() {
      return System.currentTimeMillis() - lastMS;
   }

   public void reset1() {
      llIlIIIIllll.lastMS1 = getCurrentMS();
   }

   public static boolean isDelayComplete(long llIIlllllIll) {
      return System.currentTimeMillis() - lastMS >= llIIlllllIll;
   }

   public boolean hasTimeElapsed(long llIIllllllll, boolean llIlIIIIIIIl) {
      if (llIlIIIIIIll.time() >= llIIllllllll) {
         if (llIlIIIIIIIl) {
            reset();
         }

         return true;
      } else {
         return false;
      }
   }

   public static boolean delay(float llIlIIIllIIl) {
      return (float)(getTime() - prevMS) >= llIlIIIllIIl;
   }

   public static boolean delay(double llIlIIIlIlll) {
      return (double)(getTime() - prevMS) >= llIlIIIlIlll;
   }

   public long getLastMS() {
      return lastMS;
   }

   public void setLastMS(long llIIllllIIIl) {
      lastMS = llIIllllIIIl;
   }

   public long getDifference() {
      return getTime() - prevMS;
   }

   public static void reset() {
      prevMS = getTime();
   }

   public void setDifference(long llIlIIIIIlll) {
      prevMS = getTime() - llIlIIIIIlll;
   }

   public TimerUtil() {
      prevMS = 0L;
      llIlIIIlllII.time = System.nanoTime() / 1000000L;
   }

   public boolean delay1(float llIlIIIlIIll) {
      return (float)(getTime() - prevMS) >= llIlIIIlIIll;
   }

   public boolean hasTicksElapsed(int llIIlllIIIIl) {
      return llIIlllIIIlI.time() >= (long)(1000 / llIIlllIIIIl - 50);
   }

   public static boolean hasReached(long llIIllllIlIl) {
      return getCurrentMS() - lastMS >= llIIllllIlIl;
   }

   public boolean hasReached(double llIIlllIllIl) {
      return (double)(getCurrentMS() - lastMS) >= llIIlllIllIl;
   }

   public boolean check(float llIIllIllIlI) {
      return (float)getTime() >= llIIllIllIlI;
   }

   public static long getCurrentMS() {
      return System.nanoTime() / 1000000L;
   }

   public void setLastMS() {
      lastMS = System.currentTimeMillis();
   }

   public void schedule(TimerTask llIIllIlllll, long llIIllIllllI) {
   }
}
