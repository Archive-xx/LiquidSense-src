package net.vitox.particle.util;

import org.lwjgl.opengl.GL11;

public class RenderUtils {
   public static void connectPoints(float llllllllllllllllllIIlIIlIIlIIlIl, float llllllllllllllllllIIlIIlIIlIIlII, float llllllllllllllllllIIlIIlIIlIIIll, float llllllllllllllllllIIlIIlIIlIIllI) {
      GL11.glPushMatrix();
      GL11.glEnable(2848);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.8F);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(3042);
      GL11.glLineWidth(0.5F);
      GL11.glBegin(1);
      GL11.glVertex2f(llllllllllllllllllIIlIIlIIlIIlIl, llllllllllllllllllIIlIIlIIlIIlII);
      GL11.glVertex2f(llllllllllllllllllIIlIIlIIlIIIll, llllllllllllllllllIIlIIlIIlIIllI);
      GL11.glEnd();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glDisable(2848);
      GL11.glEnable(3553);
      GL11.glPopMatrix();
   }

   public static void drawCircle(float llllllllllllllllllIIlIIIlllllIlI, float llllllllllllllllllIIlIIIlllllIIl, float llllllllllllllllllIIlIIIlllllIII, int llllllllllllllllllIIlIIIllllllll) {
      String llllllllllllllllllIIlIIIllllIllI = (float)(llllllllllllllllllIIlIIIllllllll >> 24 & 255) / 255.0F;
      float llllllllllllllllllIIlIIIllllllIl = (float)(llllllllllllllllllIIlIIIllllllll >> 16 & 255) / 255.0F;
      boolean llllllllllllllllllIIlIIIllllIlII = (float)(llllllllllllllllllIIlIIIllllllll >> 8 & 255) / 255.0F;
      float llllllllllllllllllIIlIIIlllllIll = (float)(llllllllllllllllllIIlIIIllllllll & 255) / 255.0F;
      GL11.glColor4f(llllllllllllllllllIIlIIIllllllIl, llllllllllllllllllIIlIIIllllIlII, llllllllllllllllllIIlIIIlllllIll, llllllllllllllllllIIlIIIllllIllI);
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glPushMatrix();
      GL11.glLineWidth(1.0F);
      GL11.glBegin(9);

      for(int llllllllllllllllllIIlIIIllllIIlI = 0; llllllllllllllllllIIlIIIllllIIlI <= 360; ++llllllllllllllllllIIlIIIllllIIlI) {
         GL11.glVertex2d((double)llllllllllllllllllIIlIIIlllllIlI + Math.sin((double)llllllllllllllllllIIlIIIllllIIlI * 3.141592653589793D / 180.0D) * (double)llllllllllllllllllIIlIIIlllllIII, (double)llllllllllllllllllIIlIIIlllllIIl + Math.cos((double)llllllllllllllllllIIlIIIllllIIlI * 3.141592653589793D / 180.0D) * (double)llllllllllllllllllIIlIIIlllllIII);
      }

      GL11.glEnd();
      GL11.glPopMatrix();
      GL11.glEnable(3553);
      GL11.glDisable(2848);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }
}
