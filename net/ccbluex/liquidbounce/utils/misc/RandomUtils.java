package net.ccbluex.liquidbounce.utils.misc;

import java.util.Random;

public final class RandomUtils {
   public static int nextInt(int llIIllIllllllII, int llIIllIlllllIll) {
      return llIIllIlllllIll - llIIllIllllllII <= 0 ? llIIllIllllllII : llIIllIllllllII + (new Random()).nextInt(llIIllIlllllIll - llIIllIllllllII);
   }

   public static String random(int llIIllIlllIIllI, String llIIllIlllIIlIl) {
      return random(llIIllIlllIIllI, llIIllIlllIIlIl.toCharArray());
   }

   public static String randomNumber(int llIIllIlllIllIl) {
      return random(llIIllIlllIllIl, "123456789");
   }

   public static double nextDouble(double llIIllIllllIllI, double llIIllIllllIlll) {
      return llIIllIllllIllI != llIIllIllllIlll && !(llIIllIllllIlll - llIIllIllllIllI <= 0.0D) ? llIIllIllllIllI + (llIIllIllllIlll - llIIllIllllIllI) * Math.random() : llIIllIllllIllI;
   }

   public static float nextFloat(float llIIllIllllIIII, float llIIllIlllIllll) {
      return llIIllIllllIIII != llIIllIlllIllll && !(llIIllIlllIllll - llIIllIllllIIII <= 0.0F) ? (float)((double)llIIllIllllIIII + (double)(llIIllIlllIllll - llIIllIllllIIII) * Math.random()) : llIIllIllllIIII;
   }

   public static String randomString(int llIIllIlllIlIIl) {
      return random(llIIllIlllIlIIl, "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
   }

   public static String random(int llIIllIllIllIlI, char[] llIIllIllIllIIl) {
      StringBuilder llIIllIllIllIll = new StringBuilder();

      for(int llIIllIllIlIlll = 0; llIIllIllIlIlll < llIIllIllIllIlI; ++llIIllIllIlIlll) {
         llIIllIllIllIll.append(llIIllIllIllIIl[(new Random()).nextInt(llIIllIllIllIIl.length)]);
         boolean var10001 = false;
      }

      return String.valueOf(llIIllIllIllIll);
   }
}
