//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.utils.render.shader;

import java.awt.Color;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public abstract class FramebufferShader extends Shader {
   // $FF: synthetic field
   protected float red;
   // $FF: synthetic field
   protected float quality = 1.0F;
   // $FF: synthetic field
   private boolean entityShadows;
   // $FF: synthetic field
   protected float radius = 2.0F;
   // $FF: synthetic field
   protected float blue;
   // $FF: synthetic field
   private static Framebuffer framebuffer;
   // $FF: synthetic field
   protected float alpha = 1.0F;
   // $FF: synthetic field
   protected float green;

   public void stopDraw(Color lllllllllllllllllllIlllIlllllIll, float lllllllllllllllllllIlllIlllllllI, float lllllllllllllllllllIlllIlllllIIl) {
      mc.gameSettings.entityShadows = lllllllllllllllllllIllllIIIIIIII.entityShadows;
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      mc.getFramebuffer().bindFramebuffer(true);
      lllllllllllllllllllIllllIIIIIIII.red = (float)lllllllllllllllllllIlllIlllllIll.getRed() / 255.0F;
      lllllllllllllllllllIllllIIIIIIII.green = (float)lllllllllllllllllllIlllIlllllIll.getGreen() / 255.0F;
      lllllllllllllllllllIllllIIIIIIII.blue = (float)lllllllllllllllllllIlllIlllllIll.getBlue() / 255.0F;
      lllllllllllllllllllIllllIIIIIIII.alpha = (float)lllllllllllllllllllIlllIlllllIll.getAlpha() / 255.0F;
      lllllllllllllllllllIllllIIIIIIII.radius = lllllllllllllllllllIlllIlllllllI;
      lllllllllllllllllllIllllIIIIIIII.quality = lllllllllllllllllllIlllIlllllIIl;
      mc.entityRenderer.disableLightmap();
      RenderHelper.disableStandardItemLighting();
      lllllllllllllllllllIllllIIIIIIII.startShader();
      mc.entityRenderer.setupOverlayRendering();
      lllllllllllllllllllIllllIIIIIIII.drawFramebuffer(framebuffer);
      lllllllllllllllllllIllllIIIIIIII.stopShader();
      mc.entityRenderer.disableLightmap();
      GlStateManager.popMatrix();
      GlStateManager.popAttrib();
   }

   public void startDraw(float lllllllllllllllllllIllllIIIIIlll) {
      GlStateManager.enableAlpha();
      GlStateManager.pushMatrix();
      GlStateManager.pushAttrib();
      framebuffer = lllllllllllllllllllIllllIIIIlIII.setupFrameBuffer(framebuffer);
      framebuffer.framebufferClear();
      framebuffer.bindFramebuffer(true);
      lllllllllllllllllllIllllIIIIlIII.entityShadows = mc.gameSettings.entityShadows;
      mc.gameSettings.entityShadows = false;
      mc.entityRenderer.setupCameraTransform(lllllllllllllllllllIllllIIIIIlll, 0);
   }

   public FramebufferShader(String lllllllllllllllllllIllllIIIIlIll) {
      super(lllllllllllllllllllIllllIIIIlIll);
   }

   public Framebuffer setupFrameBuffer(Framebuffer lllllllllllllllllllIlllIllllIllI) {
      if (lllllllllllllllllllIlllIllllIllI != null) {
         lllllllllllllllllllIlllIllllIllI.deleteFramebuffer();
      }

      lllllllllllllllllllIlllIllllIllI = new Framebuffer(mc.displayWidth, mc.displayHeight, true);
      return lllllllllllllllllllIlllIllllIllI;
   }

   public void drawFramebuffer(Framebuffer lllllllllllllllllllIlllIllllIIIl) {
      int lllllllllllllllllllIlllIlllIlllI = new ScaledResolution(mc);
      GL11.glBindTexture(3553, lllllllllllllllllllIlllIllllIIIl.framebufferTexture);
      GL11.glBegin(7);
      GL11.glTexCoord2d(0.0D, 1.0D);
      GL11.glVertex2d(0.0D, 0.0D);
      GL11.glTexCoord2d(0.0D, 0.0D);
      GL11.glVertex2d(0.0D, (double)lllllllllllllllllllIlllIlllIlllI.getScaledHeight());
      GL11.glTexCoord2d(1.0D, 0.0D);
      GL11.glVertex2d((double)lllllllllllllllllllIlllIlllIlllI.getScaledWidth(), (double)lllllllllllllllllllIlllIlllIlllI.getScaledHeight());
      GL11.glTexCoord2d(1.0D, 1.0D);
      GL11.glVertex2d((double)lllllllllllllllllllIlllIlllIlllI.getScaledWidth(), 0.0D);
      GL11.glEnd();
      GL20.glUseProgram(0);
   }
}
