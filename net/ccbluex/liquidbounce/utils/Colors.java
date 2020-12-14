//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.utils;

import java.awt.Color;
import java.text.NumberFormat;
import net.minecraft.entity.EntityLivingBase;

public class Colors {
   public static int getColor(Color lllIlIIIlIIlll) {
      return getColor(lllIlIIIlIIlll.getRed(), lllIlIIIlIIlll.getGreen(), lllIlIIIlIIlll.getBlue(), lllIlIIIlIIlll.getAlpha());
   }

   public static int getColor(int lllIlIIIIIlIlI, int lllIlIIIIIlIIl, int lllIlIIIIIllIl, int lllIlIIIIIllII) {
      boolean lllIlIIIIIIllI = 0;
      boolean lllIlIIIIIIllI = lllIlIIIIIIllI | lllIlIIIIIllII << 24;
      lllIlIIIIIIllI |= lllIlIIIIIlIlI << 16;
      lllIlIIIIIIllI |= lllIlIIIIIlIIl << 8;
      lllIlIIIIIIllI |= lllIlIIIIIllIl;
      return lllIlIIIIIIllI;
   }

   public static int getColor(int lllIlIIIlIIlIl) {
      return getColor(lllIlIIIlIIlIl, lllIlIIIlIIlIl, lllIlIIIlIIlIl, 255);
   }

   public static Color blend(Color lllIIllIllIlll, Color lllIIllIlIlIll, double lllIIllIlIlIlI) {
      String lllIIllIlIlIIl = (float)lllIIllIlIlIlI;
      float lllIIllIllIIll = 1.0F - lllIIllIlIlIIl;
      float[] lllIIllIllIIlI = new float[3];
      float lllIIllIlIIllI = new float[3];
      lllIIllIllIlll.getColorComponents(lllIIllIllIIlI);
      boolean var10001 = false;
      lllIIllIlIlIll.getColorComponents(lllIIllIlIIllI);
      var10001 = false;
      float lllIIllIllIIII = lllIIllIllIIlI[0] * lllIIllIlIlIIl + lllIIllIlIIllI[0] * lllIIllIllIIll;
      short lllIIllIlIIlII = lllIIllIllIIlI[1] * lllIIllIlIlIIl + lllIIllIlIIllI[1] * lllIIllIllIIll;
      float lllIIllIlIlllI = lllIIllIllIIlI[2] * lllIIllIlIlIIl + lllIIllIlIIllI[2] * lllIIllIllIIll;
      if (lllIIllIllIIII < 0.0F) {
         lllIIllIllIIII = 0.0F;
      } else if (lllIIllIllIIII > 255.0F) {
         lllIIllIllIIII = 255.0F;
      }

      if (lllIIllIlIIlII < 0.0F) {
         lllIIllIlIIlII = 0.0F;
      } else if (lllIIllIlIIlII > 255.0F) {
         lllIIllIlIIlII = 255.0F;
      }

      if (lllIIllIlIlllI < 0.0F) {
         lllIIllIlIlllI = 0.0F;
      } else if (lllIIllIlIlllI > 255.0F) {
         lllIIllIlIlllI = 255.0F;
      }

      Color lllIIllIlIIIlI = null;

      try {
         lllIIllIlIIIlI = new Color(lllIIllIllIIII, lllIIllIlIIlII, lllIIllIlIlllI);
      } catch (IllegalArgumentException var14) {
         NumberFormat lllIIllIlllIIl = NumberFormat.getNumberInstance();
         System.out.println(String.valueOf((new StringBuilder()).append(lllIIllIlllIIl.format((double)lllIIllIllIIII)).append("; ").append(lllIIllIlllIIl.format((double)lllIIllIlIIlII)).append("; ").append(lllIIllIlllIIl.format((double)lllIIllIlIlllI))));
         var14.printStackTrace();
      }

      return lllIIllIlIIIlI;
   }

   public static Color getHealthColor(EntityLivingBase lllIIlllllIIlI) {
      short lllIIlllllIIIl = lllIIlllllIIlI.getHealth();
      float[] lllIIlllllIlIl = new float[]{0.0F, 0.15F, 0.55F, 0.7F, 0.9F};
      Color[] lllIIlllllIlII = new Color[]{new Color(133, 0, 0), Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN};
      long lllIIllllIlllI = lllIIlllllIIIl / lllIIlllllIIlI.getMaxHealth();
      return lllIIlllllIIIl >= 0.0F ? blendColors(lllIIlllllIlIl, lllIIlllllIlII, lllIIllllIlllI).brighter() : lllIIlllllIlII[0];
   }

   public static int getColor(int lllIlIIIlIIIIl, int lllIlIIIIllllI) {
      return getColor(lllIlIIIlIIIIl, lllIlIIIlIIIIl, lllIlIIIlIIIIl, lllIlIIIIllllI);
   }

   public static int getRainbow(int lllIlIIIIIIIlI, int lllIIllllllllI) {
      long lllIIlllllllIl = (float)((System.currentTimeMillis() + (long)lllIIllllllllI) % (long)lllIlIIIIIIIlI);
      return Color.getHSBColor(lllIIlllllllIl / (float)lllIlIIIIIIIlI, 0.4F, 1.0F).getRGB();
   }

   public static Color blendColors(float[] lllIIlllIllIll, Color[] lllIIlllIllIlI, float lllIIllllIIIlI) {
      if (lllIIlllIllIll == null) {
         throw new IllegalArgumentException("Fractions can't be null");
      } else if (lllIIlllIllIlI == null) {
         throw new IllegalArgumentException("Colours can't be null");
      } else if (lllIIlllIllIll.length != lllIIlllIllIlI.length) {
         throw new IllegalArgumentException("Fractions and colours must have equal number of elements");
      } else {
         int[] lllIIllllIIIIl = getFractionIndicies(lllIIlllIllIll, lllIIllllIIIlI);
         boolean lllIIlllIlIlll = new float[]{lllIIlllIllIll[lllIIllllIIIIl[0]], lllIIlllIllIll[lllIIllllIIIIl[1]]};
         char lllIIlllIlIllI = new Color[]{lllIIlllIllIlI[lllIIllllIIIIl[0]], lllIIlllIllIlI[lllIIllllIIIIl[1]]};
         float lllIIlllIllllI = lllIIlllIlIlll[1] - lllIIlllIlIlll[0];
         String lllIIlllIlIlII = lllIIllllIIIlI - lllIIlllIlIlll[0];
         Exception lllIIlllIlIIll = lllIIlllIlIlII / lllIIlllIllllI;
         return blend(lllIIlllIlIllI[0], lllIIlllIlIllI[1], (double)(1.0F - lllIIlllIlIIll));
      }
   }

   public static int[] getFractionIndicies(float[] lllIIlllIIlIlI, float lllIIlllIIllIl) {
      double lllIIlllIIIlll = new int[2];

      int lllIIlllIIlIII;
      for(lllIIlllIIlIII = 0; lllIIlllIIlIII < lllIIlllIIlIlI.length && lllIIlllIIlIlI[lllIIlllIIlIII] <= lllIIlllIIllIl; ++lllIIlllIIlIII) {
      }

      if (lllIIlllIIlIII >= lllIIlllIIlIlI.length) {
         lllIIlllIIlIII = lllIIlllIIlIlI.length - 1;
      }

      lllIIlllIIIlll[0] = lllIIlllIIlIII - 1;
      lllIIlllIIIlll[1] = lllIIlllIIlIII;
      return lllIIlllIIIlll;
   }

   public static int getColor(int lllIlIIIIllIlI, int lllIlIIIIlIllI, int lllIlIIIIlIlIl) {
      return getColor(lllIlIIIIllIlI, lllIlIIIIlIllI, lllIlIIIIlIlIl, 255);
   }
}
