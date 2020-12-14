//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.utils;

import java.util.ArrayList;
import java.util.List;
import javax.vecmath.Vector3d;
import net.minecraft.util.MathHelper;

public final class PathUtils extends MinecraftInstance {
   private static double getDistance(double llllllllIIlII, double lllllllIllIIl, double lllllllIllIII, double lllllllIlIlll, double llllllllIIIII, double lllllllIlIlIl) {
      double lllllllIllllI = llllllllIIlII - lllllllIlIlll;
      double lllllllIlllIl = lllllllIllIIl - llllllllIIIII;
      byte lllllllIllIlI = lllllllIllIII - lllllllIlIlIl;
      return (double)MathHelper.sqrt_double(lllllllIllllI * lllllllIllllI + lllllllIlllIl * lllllllIlllIl + lllllllIllIlI * lllllllIllIlI);
   }

   public static List<Vector3d> findPath(double llllllllllllI, double lllllllllllIl, double lllllllllllII, double lllllllllIIll) {
      List<Vector3d> lllllllllIIlI = new ArrayList();
      float lllllllllIIIl = (float)(Math.atan2(lllllllllllII - mc.thePlayer.posZ, llllllllllllI - mc.thePlayer.posX) * 180.0D / 3.141592653589793D - 90.0D);
      Exception lllllllllIIII = getDistance(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, llllllllllllI, lllllllllllIl, lllllllllllII) / lllllllllIIll;
      int llllllllIllll = mc.thePlayer.posY;

      boolean var10001;
      for(double llllllllIlllI = lllllllllIIll; llllllllIlllI < getDistance(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, llllllllllllI, lllllllllllIl, lllllllllllII); llllllllIlllI += lllllllllIIll) {
         llllllllIllll -= (mc.thePlayer.posY - lllllllllllIl) / lllllllllIIII;
         lllllllllIIlI.add(new Vector3d(mc.thePlayer.posX - Math.sin(Math.toRadians((double)lllllllllIIIl)) * llllllllIlllI, llllllllIllll, mc.thePlayer.posZ + Math.cos(Math.toRadians((double)lllllllllIIIl)) * llllllllIlllI));
         var10001 = false;
      }

      lllllllllIIlI.add(new Vector3d(llllllllllllI, lllllllllllIl, lllllllllllII));
      var10001 = false;
      return lllllllllIIlI;
   }

   public static List<Vector3d> findBlinkPath(double lIIIIIIIlIIIII, double lIIIIIIIIlllll, double lIIIIIIIIlIllI) {
      List<Vector3d> lIIIIIIIIlllIl = new ArrayList();
      double lIIIIIIIIlIlII = mc.thePlayer.posX;
      double lIIIIIIIIllIll = mc.thePlayer.posY;
      double lIIIIIIIIllIlI = mc.thePlayer.posZ;
      double lIIIIIIIIllIIl = Math.abs(lIIIIIIIIlIlII - lIIIIIIIlIIIII) + Math.abs(lIIIIIIIIllIll - lIIIIIIIIlllll) + Math.abs(lIIIIIIIIllIlI - lIIIIIIIIlIllI);

      for(int lIIIIIIIIlIIII = 0; lIIIIIIIIllIIl > 0.0D; ++lIIIIIIIIlIIII) {
         lIIIIIIIIllIIl = Math.abs(lIIIIIIIIlIlII - lIIIIIIIlIIIII) + Math.abs(lIIIIIIIIllIll - lIIIIIIIIlllll) + Math.abs(lIIIIIIIIllIlI - lIIIIIIIIlIllI);
         double lIIIIIIIlIlIII = lIIIIIIIIlIlII - lIIIIIIIlIIIII;
         double lIIIIIIIlIIlll = lIIIIIIIIllIll - lIIIIIIIIlllll;
         byte lIIIIIIIIIllIl = lIIIIIIIIllIlI - lIIIIIIIIlIllI;
         byte lIIIIIIIIIllII = (lIIIIIIIIlIIII & 1) == 0 ? 0.4D : 0.1D;
         double lIIIIIIIlIIlII = Math.min(Math.abs(lIIIIIIIlIlIII), lIIIIIIIIIllII);
         if (lIIIIIIIlIlIII < 0.0D) {
            lIIIIIIIIlIlII += lIIIIIIIlIIlII;
         }

         if (lIIIIIIIlIlIII > 0.0D) {
            lIIIIIIIIlIlII -= lIIIIIIIlIIlII;
         }

         double lIIIIIIIlIIIll = Math.min(Math.abs(lIIIIIIIlIIlll), 0.25D);
         if (lIIIIIIIlIIlll < 0.0D) {
            lIIIIIIIIllIll += lIIIIIIIlIIIll;
         }

         if (lIIIIIIIlIIlll > 0.0D) {
            lIIIIIIIIllIll -= lIIIIIIIlIIIll;
         }

         double lIIIIIIIlIIIlI = Math.min(Math.abs(lIIIIIIIIIllIl), lIIIIIIIIIllII);
         if (lIIIIIIIIIllIl < 0.0D) {
            lIIIIIIIIllIlI += lIIIIIIIlIIIlI;
         }

         if (lIIIIIIIIIllIl > 0.0D) {
            lIIIIIIIIllIlI -= lIIIIIIIlIIIlI;
         }

         lIIIIIIIIlllIl.add(new Vector3d(lIIIIIIIIlIlII, lIIIIIIIIllIll, lIIIIIIIIllIlI));
         boolean var10001 = false;
      }

      return lIIIIIIIIlllIl;
   }
}
