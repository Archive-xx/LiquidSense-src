//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.vitox;

import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.vitox.particle.util.RenderUtils;

@SideOnly(Side.CLIENT)
class Particle {
   // $FF: synthetic field
   public final float size;
   // $FF: synthetic field
   public float y;
   // $FF: synthetic field
   public float x;
   // $FF: synthetic field
   private int height;
   // $FF: synthetic field
   private final float xSpeed = (float)(new Random()).nextInt(5);
   // $FF: synthetic field
   private int width;
   // $FF: synthetic field
   private final float ySpeed = (float)(new Random()).nextInt(5);

   private float lint1(float lllllllllllllllllllllIIIlIIIlIlI) {
      return 1.02F * (1.0F - lllllllllllllllllllllIIIlIIIlIlI) + 1.0F * lllllllllllllllllllllIIIlIIIlIlI;
   }

   private float lint2(float lllllllllllllllllllllIIIlIIIIlll) {
      return 1.02F + lllllllllllllllllllllIIIlIIIIlll * -0.01999998F;
   }

   private float genRandom() {
      return (float)(0.30000001192092896D + Math.random() * 1.2999999523162842D);
   }

   public int getHeight() {
      return lllllllllllllllllllllIIIIllllIll.height;
   }

   Particle(int lllllllllllllllllllllIIIlIIIllll, int lllllllllllllllllllllIIIlIIlIIIl) {
      lllllllllllllllllllllIIIlIIlIIII.x = (float)lllllllllllllllllllllIIIlIIIllll;
      lllllllllllllllllllllIIIlIIlIIII.y = (float)lllllllllllllllllllllIIIlIIlIIIl;
      lllllllllllllllllllllIIIlIIlIIII.size = lllllllllllllllllllllIIIlIIlIIII.genRandom();
   }

   public float getY() {
      return lllllllllllllllllllllIIIIlIlllll.y;
   }

   public int getWidth() {
      return lllllllllllllllllllllIIIIlllIIIl.width;
   }

   public void setX(int lllllllllllllllllllllIIIIllIIIlI) {
      lllllllllllllllllllllIIIIllIIIll.x = (float)lllllllllllllllllllllIIIIllIIIlI;
   }

   void fall() {
      long lllllllllllllllllllllIIIIlIIIIlI = Minecraft.getMinecraft();
      ScaledResolution lllllllllllllllllllllIIIIlIIIlII = new ScaledResolution(lllllllllllllllllllllIIIIlIIIIlI);
      lllllllllllllllllllllIIIIlIIIIll.y += lllllllllllllllllllllIIIIlIIIIll.ySpeed;
      lllllllllllllllllllllIIIIlIIIIll.x += lllllllllllllllllllllIIIIlIIIIll.xSpeed;
      if (lllllllllllllllllllllIIIIlIIIIll.y > (float)lllllllllllllllllllllIIIIlIIIIlI.displayHeight) {
         lllllllllllllllllllllIIIIlIIIIll.y = 1.0F;
      }

      if (lllllllllllllllllllllIIIIlIIIIll.x > (float)lllllllllllllllllllllIIIIlIIIIlI.displayWidth) {
         lllllllllllllllllllllIIIIlIIIIll.x = 1.0F;
      }

      if (lllllllllllllllllllllIIIIlIIIIll.x < 1.0F) {
         lllllllllllllllllllllIIIIlIIIIll.x = (float)lllllllllllllllllllllIIIIlIIIlII.getScaledWidth();
      }

      if (lllllllllllllllllllllIIIIlIIIIll.y < 1.0F) {
         lllllllllllllllllllllIIIIlIIIIll.y = (float)lllllllllllllllllllllIIIIlIIIlII.getScaledHeight();
      }

   }

   public float getX() {
      return lllllllllllllllllllllIIIIllIlIIl.x;
   }

   void interpolation() {
      for(int lllllllllllllllllllllIIIIlIIllIl = 0; lllllllllllllllllllllIIIIlIIllIl <= 64; ++lllllllllllllllllllllIIIIlIIllIl) {
         float lllllllllllllllllllllIIIIlIlIIll = (float)lllllllllllllllllllllIIIIlIIllIl / 64.0F;
         float lllllllllllllllllllllIIIIlIlIIlI = lllllllllllllllllllllIIIIlIIllll.lint1(lllllllllllllllllllllIIIIlIlIIll);
         float lllllllllllllllllllllIIIIlIlIIIl = lllllllllllllllllllllIIIIlIIllll.lint2(lllllllllllllllllllllIIIIlIlIIll);
         if (lllllllllllllllllllllIIIIlIlIIlI != lllllllllllllllllllllIIIIlIlIIIl) {
            lllllllllllllllllllllIIIIlIIllll.y -= lllllllllllllllllllllIIIIlIlIIll;
            lllllllllllllllllllllIIIIlIIllll.x -= lllllllllllllllllllllIIIIlIlIIll;
         }
      }

   }

   public void setHeight(int lllllllllllllllllllllIIIIlllIlII) {
      lllllllllllllllllllllIIIIlllIlll.height = lllllllllllllllllllllIIIIlllIlII;
   }

   public void setY(int lllllllllllllllllllllIIIIlIllIIl) {
      lllllllllllllllllllllIIIIlIlllII.y = (float)lllllllllllllllllllllIIIIlIllIIl;
   }

   void connect(float lllllllllllllllllllllIIIIllllllI, float lllllllllllllllllllllIIIIlllllIl) {
      RenderUtils.connectPoints(lllllllllllllllllllllIIIlIIIIIlI.getX(), lllllllllllllllllllllIIIlIIIIIlI.getY(), lllllllllllllllllllllIIIIllllllI, lllllllllllllllllllllIIIIlllllIl);
   }

   public void setWidth(int lllllllllllllllllllllIIIIllIllIl) {
      lllllllllllllllllllllIIIIllIllII.width = lllllllllllllllllllllIIIIllIllIl;
   }
}
