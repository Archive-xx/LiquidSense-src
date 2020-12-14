//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.utils.render;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class UiUtils {
   public static int anima(int lIlIIIIIIlIlIll, int lIlIIIIIIlIIlll) {
      int lIlIIIIIIlIlIIl = 0;
      if (lIlIIIIIIlIlIIl < lIlIIIIIIlIlIll) {
         lIlIIIIIIlIlIIl += lIlIIIIIIlIIlll;
      }

      if (lIlIIIIIIlIlIIl > lIlIIIIIIlIlIll) {
         lIlIIIIIIlIlIIl -= lIlIIIIIIlIIlll;
      }

      return lIlIIIIIIlIlIIl;
   }

   public static int width() {
      return (new ScaledResolution(Minecraft.getMinecraft())).getScaledWidth();
   }

   public static void drawImage(ResourceLocation lIlIIIIIIIlllll, int lIlIIIIIIIllIII, int lIlIIIIIIIlllIl, int lIlIIIIIIIlIllI, int lIlIIIIIIIlIlIl) {
      new ScaledResolution(Minecraft.getMinecraft());
      GL11.glDisable(2929);
      GL11.glEnable(3042);
      GL11.glDepthMask(false);
      OpenGlHelper.glBlendFunc(770, 771, 1, 0);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      Minecraft.getMinecraft().getTextureManager().bindTexture(lIlIIIIIIIlllll);
      Gui.drawModalRectWithCustomSizedTexture(lIlIIIIIIIllIII, lIlIIIIIIIlllIl, 0.0F, 0.0F, lIlIIIIIIIlIllI, lIlIIIIIIIlIlIl, (float)lIlIIIIIIIlIllI, (float)lIlIIIIIIIlIlIl);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glEnable(2929);
   }

   public static void stopGlScissor() {
      GL11.glDisable(3089);
      GL11.glPopMatrix();
   }

   public static void drawImage(ResourceLocation lIlIIIIIIIIIlIl, int lIlIIIIIIIIlIll, int lIlIIIIIIIIlIlI, int lIlIIIIIIIIIIlI, int lIlIIIIIIIIlIII, Color lIlIIIIIIIIIIII) {
      new ScaledResolution(Minecraft.getMinecraft());
      GL11.glDisable(2929);
      GL11.glEnable(3042);
      GL11.glDepthMask(false);
      OpenGlHelper.glBlendFunc(770, 771, 1, 0);
      GL11.glColor4f((float)lIlIIIIIIIIIIII.getRed() / 255.0F, (float)lIlIIIIIIIIIIII.getBlue() / 255.0F, (float)lIlIIIIIIIIIIII.getRed() / 255.0F, 1.0F);
      Minecraft.getMinecraft().getTextureManager().bindTexture(lIlIIIIIIIIIlIl);
      Gui.drawModalRectWithCustomSizedTexture(lIlIIIIIIIIlIll, lIlIIIIIIIIlIlI, 0.0F, 0.0F, lIlIIIIIIIIIIlI, lIlIIIIIIIIlIII, (float)lIlIIIIIIIIIIlI, (float)lIlIIIIIIIIlIII);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glEnable(2929);
   }

   public static void drawGradient(double lIIlllllIllllIl, double lIIlllllIlIlllI, double lIIlllllIlIllIl, double lIIlllllIlIllII, int lIIlllllIlllIIl, int lIIlllllIlllIII) {
      float lIIlllllIllIlll = (float)(lIIlllllIlllIIl >> 24 & 255) / 255.0F;
      int lIIlllllIlIlIII = (float)(lIIlllllIlllIIl >> 16 & 255) / 255.0F;
      float lIIlllllIllIlIl = (float)(lIIlllllIlllIIl >> 8 & 255) / 255.0F;
      float lIIlllllIllIlII = (float)(lIIlllllIlllIIl & 255) / 255.0F;
      float lIIlllllIllIIll = (float)(lIIlllllIlllIII >> 24 & 255) / 255.0F;
      float lIIlllllIllIIlI = (float)(lIIlllllIlllIII >> 16 & 255) / 255.0F;
      float lIIlllllIllIIIl = (float)(lIIlllllIlllIII >> 8 & 255) / 255.0F;
      float lIIlllllIllIIII = (float)(lIIlllllIlllIII & 255) / 255.0F;
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glShadeModel(7425);
      GL11.glPushMatrix();
      GL11.glBegin(7);
      GL11.glColor4f(lIIlllllIlIlIII, lIIlllllIllIlIl, lIIlllllIllIlII, lIIlllllIllIlll);
      GL11.glVertex2d(lIIlllllIlIllIl, lIIlllllIlIlllI);
      GL11.glVertex2d(lIIlllllIllllIl, lIIlllllIlIlllI);
      GL11.glColor4f(lIIlllllIllIIlI, lIIlllllIllIIIl, lIIlllllIllIIII, lIIlllllIllIIll);
      GL11.glVertex2d(lIIlllllIllllIl, lIIlllllIlIllII);
      GL11.glVertex2d(lIIlllllIlIllIl, lIIlllllIlIllII);
      GL11.glEnd();
      GL11.glPopMatrix();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
      GL11.glShadeModel(7424);
      GL11.glColor4d(1.0D, 1.0D, 1.0D, 1.0D);
   }

   public static void drawRoundRect(double lIIlllllllllIIl, double lIIlllllllllIII, double lIIllllllllIIlI, double lIIllllllllIIIl, int lIIllllllllIIII) {
      drawRect(lIIlllllllllIIl + 1.0D, lIIlllllllllIII, lIIllllllllIIlI - 1.0D, lIIllllllllIIIl, lIIllllllllIIII);
      drawRect(lIIlllllllllIIl, lIIlllllllllIII + 1.0D, lIIlllllllllIIl + 1.0D, lIIllllllllIIIl - 1.0D, lIIllllllllIIII);
      drawRect(lIIlllllllllIIl + 1.0D, lIIlllllllllIII + 1.0D, lIIlllllllllIIl + 0.5D, lIIlllllllllIII + 0.5D, lIIllllllllIIII);
      drawRect(lIIlllllllllIIl + 1.0D, lIIlllllllllIII + 1.0D, lIIlllllllllIIl + 0.5D, lIIlllllllllIII + 0.5D, lIIllllllllIIII);
      drawRect(lIIllllllllIIlI - 1.0D, lIIlllllllllIII + 1.0D, lIIllllllllIIlI - 0.5D, lIIlllllllllIII + 0.5D, lIIllllllllIIII);
      drawRect(lIIllllllllIIlI - 1.0D, lIIlllllllllIII + 1.0D, lIIllllllllIIlI, lIIllllllllIIIl - 1.0D, lIIllllllllIIII);
      drawRect(lIIlllllllllIIl + 1.0D, lIIllllllllIIIl - 1.0D, lIIlllllllllIIl + 0.5D, lIIllllllllIIIl - 0.5D, lIIllllllllIIII);
      drawRect(lIIllllllllIIlI - 1.0D, lIIllllllllIIIl - 1.0D, lIIllllllllIIlI - 0.5D, lIIllllllllIIIl - 0.5D, lIIllllllllIIII);
   }

   public static void outlineRect(double lIIllllIlIIlllI, double lIIllllIlIIIllI, double lIIllllIlIIIlIl, double lIIllllIlIIlIll, double lIIllllIlIIlIlI, int lIIllllIlIIlIIl, int lIIllllIlIIlIII) {
      drawRect(lIIllllIlIIlllI + lIIllllIlIIlIlI, lIIllllIlIIIllI + lIIllllIlIIlIlI, lIIllllIlIIIlIl - lIIllllIlIIlIlI, lIIllllIlIIlIll - lIIllllIlIIlIlI, lIIllllIlIIlIIl);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      drawRect(lIIllllIlIIlllI + lIIllllIlIIlIlI, lIIllllIlIIIllI, lIIllllIlIIIlIl - lIIllllIlIIlIlI, lIIllllIlIIIllI + lIIllllIlIIlIlI, lIIllllIlIIlIII);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      drawRect(lIIllllIlIIlllI, lIIllllIlIIIllI, lIIllllIlIIlllI + lIIllllIlIIlIlI, lIIllllIlIIlIll, lIIllllIlIIlIII);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      drawRect(lIIllllIlIIIlIl - lIIllllIlIIlIlI, lIIllllIlIIIllI, lIIllllIlIIIlIl, lIIllllIlIIlIll, lIIllllIlIIlIII);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      drawRect(lIIllllIlIIlllI + lIIllllIlIIlIlI, lIIllllIlIIlIll - lIIllllIlIIlIlI, lIIllllIlIIIlIl - lIIllllIlIIlIlI, lIIllllIlIIlIll, lIIllllIlIIlIII);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static void drawGradientSideways(double lIIllllIllllllI, double lIIllllIllIllll, double lIIllllIlllllII, double lIIllllIllllIll, int lIIllllIllllIlI, int lIIllllIllIlIlI) {
      short lIIllllIllIlIII = (float)(lIIllllIllllIlI >> 24 & 255) / 255.0F;
      float lIIllllIlllIlll = (float)(lIIllllIllllIlI >> 16 & 255) / 255.0F;
      float lIIllllIlllIllI = (float)(lIIllllIllllIlI >> 8 & 255) / 255.0F;
      double lIIllllIllIIlII = (float)(lIIllllIllllIlI & 255) / 255.0F;
      int lIIllllIllIIIll = (float)(lIIllllIllIlIlI >> 24 & 255) / 255.0F;
      Exception lIIllllIllIIIlI = (float)(lIIllllIllIlIlI >> 16 & 255) / 255.0F;
      double lIIllllIllIIIII = (float)(lIIllllIllIlIlI >> 8 & 255) / 255.0F;
      float lIIllllIlllIIIl = (float)(lIIllllIllIlIlI & 255) / 255.0F;
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glShadeModel(7425);
      GL11.glPushMatrix();
      GL11.glBegin(7);
      GL11.glColor4f(lIIllllIlllIlll, lIIllllIlllIllI, lIIllllIllIIlII, lIIllllIllIlIII);
      GL11.glVertex2d(lIIllllIllllllI, lIIllllIllIllll);
      GL11.glVertex2d(lIIllllIllllllI, lIIllllIllllIll);
      GL11.glColor4f(lIIllllIllIIIlI, lIIllllIllIIIII, lIIllllIlllIIIl, lIIllllIllIIIll);
      GL11.glVertex2d(lIIllllIlllllII, lIIllllIllllIll);
      GL11.glVertex2d(lIIllllIlllllII, lIIllllIllIllll);
      GL11.glEnd();
      GL11.glPopMatrix();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
      GL11.glShadeModel(7424);
   }

   public static void drawRoundRectWithRect(double lIIlllllllIIlIl, double lIIlllllllIIlII, double lIIlllllllIIIll, double lIIlllllllIIIlI, int lIIlllllllIIIIl) {
      drawRect(lIIlllllllIIlIl + 1.0D, lIIlllllllIIlII, lIIlllllllIIIll - 1.0D, lIIlllllllIIIlI, lIIlllllllIIIIl);
      drawRect(lIIlllllllIIlIl, lIIlllllllIIlII + 1.0D, lIIlllllllIIlIl + 1.0D, lIIlllllllIIIlI - 1.0D, lIIlllllllIIIIl);
      drawRect(lIIlllllllIIlIl + 1.0D, lIIlllllllIIlII + 1.0D, lIIlllllllIIlIl + 0.5D, lIIlllllllIIlII + 0.5D, lIIlllllllIIIIl);
      drawRect(lIIlllllllIIlIl + 1.0D, lIIlllllllIIlII + 1.0D, lIIlllllllIIlIl + 0.5D, lIIlllllllIIlII + 0.5D, lIIlllllllIIIIl);
      drawRect(lIIlllllllIIIll - 1.0D, lIIlllllllIIlII + 1.0D, lIIlllllllIIIll - 0.5D, lIIlllllllIIlII + 0.5D, lIIlllllllIIIIl);
      drawRect(lIIlllllllIIIll - 1.0D, lIIlllllllIIlII + 1.0D, lIIlllllllIIIll, lIIlllllllIIIlI - 1.0D, lIIlllllllIIIIl);
      drawRect(lIIlllllllIIlIl + 1.0D, lIIlllllllIIIlI - 1.0D, lIIlllllllIIlIl + 0.5D, lIIlllllllIIIlI - 0.5D, lIIlllllllIIIIl);
      drawRect(lIIlllllllIIIll - 1.0D, lIIlllllllIIIlI - 1.0D, lIIlllllllIIIll - 0.5D, lIIlllllllIIIlI - 0.5D, lIIlllllllIIIIl);
   }

   public static void startGlScissor(int lIIllllllIlIIlI, int lIIllllllIllIII, int lIIllllllIlIlll, int lIIllllllIIllll) {
      Minecraft lIIllllllIlIlIl = Minecraft.getMinecraft();
      String lIIllllllIIllIl = 1;
      int lIIllllllIlIIll = lIIllllllIlIlIl.gameSettings.guiScale;
      if (lIIllllllIlIIll == 0) {
         lIIllllllIlIIll = 1000;
      }

      while(lIIllllllIIllIl < lIIllllllIlIIll && lIIllllllIlIlIl.displayWidth / (lIIllllllIIllIl + 1) >= 320 && lIIllllllIlIlIl.displayHeight / (lIIllllllIIllIl + 1) >= 240) {
         ++lIIllllllIIllIl;
      }

      GL11.glPushMatrix();
      GL11.glEnable(3089);
      GL11.glScissor(lIIllllllIlIIlI * lIIllllllIIllIl, lIIllllllIlIlIl.displayHeight - (lIIllllllIllIII + lIIllllllIIllll) * lIIllllllIIllIl, lIIllllllIlIlll * lIIllllllIIllIl, lIIllllllIIllll * lIIllllllIIllIl);
   }

   public static void drawRect(double lIIllllIIlIIlIl, double lIIllllIIIllllI, double lIIllllIIlIIIlI, double lIIllllIIlIIIIl, int lIIllllIIIllIll) {
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glPushMatrix();
      RenderUtils.glColor(new Color(lIIllllIIIllIll));
      GL11.glBegin(7);
      GL11.glVertex2d(lIIllllIIlIIIlI, lIIllllIIIllllI);
      GL11.glVertex2d(lIIllllIIlIIlIl, lIIllllIIIllllI);
      GL11.glVertex2d(lIIllllIIlIIlIl, lIIllllIIlIIIIl);
      GL11.glVertex2d(lIIllllIIlIIIlI, lIIllllIIlIIIIl);
      GL11.glEnd();
      GL11.glPopMatrix();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
   }

   public static int height() {
      return (new ScaledResolution(Minecraft.getMinecraft())).getScaledHeight();
   }
}
