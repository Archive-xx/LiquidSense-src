//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.utils.render;

import java.awt.Color;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Timer;
import net.minecraft.util.Vec3;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Cylinder;
import org.lwjgl.util.glu.GLU;

@SideOnly(Side.CLIENT)
public final class RenderUtils extends MinecraftInstance {
   // $FF: synthetic field
   private static final Map<Integer, Boolean> glCapMap = new HashMap();
   // $FF: synthetic field
   private static IntBuffer viewport = BufferUtils.createIntBuffer(16);
   // $FF: synthetic field
   private static FloatBuffer projection = BufferUtils.createFloatBuffer(16);
   // $FF: synthetic field
   private static FloatBuffer modelView = BufferUtils.createFloatBuffer(16);
   // $FF: synthetic field
   private static final Frustum frustrum = new Frustum();
   // $FF: synthetic field
   public static int deltaTime;

   public static double getEntityRenderX(Entity llllllllllllllllllIllIIIllIIIlII) {
      return llllllllllllllllllIllIIIllIIIlII.lastTickPosX + (llllllllllllllllllIllIIIllIIIlII.posX - llllllllllllllllllIllIIIllIIIlII.lastTickPosX) * (double)Minecraft.getMinecraft().timer.renderPartialTicks - mc.getRenderManager().renderPosX;
   }

   public static void makeScissorBox(float llllllllllllllllllIlIIlllIllIlII, float llllllllllllllllllIlIIlllIllIIll, float llllllllllllllllllIlIIlllIlIllII, float llllllllllllllllllIlIIlllIlIlIll) {
      char llllllllllllllllllIlIIlllIlIlIlI = new ScaledResolution(mc);
      float llllllllllllllllllIlIIlllIlIlIIl = llllllllllllllllllIlIIlllIlIlIlI.getScaleFactor();
      GL11.glScissor((int)(llllllllllllllllllIlIIlllIllIlII * (float)llllllllllllllllllIlIIlllIlIlIIl), (int)(((float)llllllllllllllllllIlIIlllIlIlIlI.getScaledHeight() - llllllllllllllllllIlIIlllIlIlIll) * (float)llllllllllllllllllIlIIlllIlIlIIl), (int)((llllllllllllllllllIlIIlllIlIllII - llllllllllllllllllIlIIlllIllIlII) * (float)llllllllllllllllllIlIIlllIlIlIIl), (int)((llllllllllllllllllIlIIlllIlIlIll - llllllllllllllllllIlIIlllIllIIll) * (float)llllllllllllllllllIlIIlllIlIlIIl));
   }

   public static boolean isInViewFrustrum(Entity llllllllllllllllllIllIIlIlIIllll) {
      return isInViewFrustrum(llllllllllllllllllIllIIlIlIIllll.getEntityBoundingBox()) || llllllllllllllllllIllIIlIlIIllll.ignoreFrustumCheck;
   }

   public static void enableGlCap(int llllllllllllllllllIlIIlllIlIIllI) {
      setGlCap(llllllllllllllllllIlIIlllIlIIllI, true);
   }

   public static void drawAxisAlignedBB(AxisAlignedBB llllllllllllllllllIlIlllIIllIllI, Color llllllllllllllllllIlIlllIIllIIll) {
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(3042);
      GL11.glLineWidth(2.0F);
      GL11.glDisable(3553);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      glColor(llllllllllllllllllIlIlllIIllIIll);
      drawFilledBox(llllllllllllllllllIlIlllIIllIllI);
      GlStateManager.resetColor();
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
   }

   public static void shadow(Entity llllllllllllllllllIlIllIIIIIIlll, double llllllllllllllllllIlIllIIIIIIIII, double llllllllllllllllllIlIllIIIIIIlIl, double llllllllllllllllllIlIllIIIIIIlII, double llllllllllllllllllIlIlIlllllllIl, int llllllllllllllllllIlIllIIIIIIIlI) {
      GL11.glPushMatrix();
      GL11.glDisable(2896);
      GL11.glDisable(3553);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(2929);
      GL11.glEnable(2848);
      GL11.glDepthMask(true);
      GlStateManager.translate(llllllllllllllllllIlIllIIIIIIIII, llllllllllllllllllIlIllIIIIIIlIl, llllllllllllllllllIlIllIIIIIIlII);
      GlStateManager.color(0.1F, 0.1F, 0.1F, 0.75F);
      GlStateManager.rotate(180.0F, 90.0F, 0.0F, 2.0F);
      GlStateManager.rotate(180.0F, 0.0F, 90.0F, 90.0F);
      Cylinder llllllllllllllllllIlIllIIIIIIIIl = new Cylinder();
      llllllllllllllllllIlIllIIIIIIIIl.setDrawStyle(100011);
      llllllllllllllllllIlIllIIIIIIIIl.draw((float)(llllllllllllllllllIlIlIlllllllIl - 0.45D), (float)(llllllllllllllllllIlIlIlllllllIl - 0.5D), 0.0F, llllllllllllllllllIlIllIIIIIIIlI, 0);
      GL11.glDepthMask(true);
      GL11.glDisable(2848);
      GL11.glEnable(2929);
      GL11.glDisable(3042);
      GL11.glEnable(2896);
      GL11.glEnable(3553);
      GL11.glPopMatrix();
   }

   public static void enableGL2D() {
      GL11.glDisable(2929);
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glDepthMask(true);
      GL11.glEnable(2848);
      GL11.glHint(3154, 4354);
      GL11.glHint(3155, 4354);
   }

   public static void drawRect(double llllllllllllllllllIlIllllIIIllII, double llllllllllllllllllIlIllllIIlIIII, double llllllllllllllllllIlIllllIIIlIlI, double llllllllllllllllllIlIllllIIIlllI, int llllllllllllllllllIlIllllIIIlIII) {
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glPushMatrix();
      glColor(llllllllllllllllllIlIllllIIIlIII);
      GL11.glBegin(7);
      GL11.glVertex2d(llllllllllllllllllIlIllllIIIlIlI, llllllllllllllllllIlIllllIIlIIII);
      GL11.glVertex2d(llllllllllllllllllIlIllllIIIllII, llllllllllllllllllIlIllllIIlIIII);
      GL11.glVertex2d(llllllllllllllllllIlIllllIIIllII, llllllllllllllllllIlIllllIIIlllI);
      GL11.glVertex2d(llllllllllllllllllIlIllllIIIlIlI, llllllllllllllllllIlIllllIIIlllI);
      GL11.glEnd();
      GL11.glPopMatrix();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
   }

   public static void disableGlCap(int... llllllllllllllllllIlIIlllIIIllll) {
      String llllllllllllllllllIlIIlllIIIlllI = llllllllllllllllllIlIIlllIIIllll;
      boolean llllllllllllllllllIlIIlllIIIllIl = llllllllllllllllllIlIIlllIIIllll.length;

      for(int llllllllllllllllllIlIIlllIIIllII = 0; llllllllllllllllllIlIIlllIIIllII < llllllllllllllllllIlIIlllIIIllIl; ++llllllllllllllllllIlIIlllIIIllII) {
         int llllllllllllllllllIlIIlllIIlIIIl = llllllllllllllllllIlIIlllIIIlllI[llllllllllllllllllIlIIlllIIIllII];
         setGlCap(llllllllllllllllllIlIIlllIIlIIIl, false);
      }

   }

   public static void drawOutlinedBoundingBox(AxisAlignedBB llllllllllllllllllIllIIlIlIIIllI) {
      Tessellator llllllllllllllllllIllIIlIlIIIlII = Tessellator.getInstance();
      WorldRenderer llllllllllllllllllIllIIlIlIIIIlI = llllllllllllllllllIllIIlIlIIIlII.getWorldRenderer();
      llllllllllllllllllIllIIlIlIIIIlI.begin(3, DefaultVertexFormats.POSITION);
      llllllllllllllllllIllIIlIlIIIIlI.pos(llllllllllllllllllIllIIlIlIIIllI.minX, llllllllllllllllllIllIIlIlIIIllI.minY, llllllllllllllllllIllIIlIlIIIllI.minZ).endVertex();
      llllllllllllllllllIllIIlIlIIIIlI.pos(llllllllllllllllllIllIIlIlIIIllI.maxX, llllllllllllllllllIllIIlIlIIIllI.minY, llllllllllllllllllIllIIlIlIIIllI.minZ).endVertex();
      llllllllllllllllllIllIIlIlIIIIlI.pos(llllllllllllllllllIllIIlIlIIIllI.maxX, llllllllllllllllllIllIIlIlIIIllI.minY, llllllllllllllllllIllIIlIlIIIllI.maxZ).endVertex();
      llllllllllllllllllIllIIlIlIIIIlI.pos(llllllllllllllllllIllIIlIlIIIllI.minX, llllllllllllllllllIllIIlIlIIIllI.minY, llllllllllllllllllIllIIlIlIIIllI.maxZ).endVertex();
      llllllllllllllllllIllIIlIlIIIIlI.pos(llllllllllllllllllIllIIlIlIIIllI.minX, llllllllllllllllllIllIIlIlIIIllI.minY, llllllllllllllllllIllIIlIlIIIllI.minZ).endVertex();
      llllllllllllllllllIllIIlIlIIIlII.draw();
      llllllllllllllllllIllIIlIlIIIIlI.begin(3, DefaultVertexFormats.POSITION);
      llllllllllllllllllIllIIlIlIIIIlI.pos(llllllllllllllllllIllIIlIlIIIllI.minX, llllllllllllllllllIllIIlIlIIIllI.maxY, llllllllllllllllllIllIIlIlIIIllI.minZ).endVertex();
      llllllllllllllllllIllIIlIlIIIIlI.pos(llllllllllllllllllIllIIlIlIIIllI.maxX, llllllllllllllllllIllIIlIlIIIllI.maxY, llllllllllllllllllIllIIlIlIIIllI.minZ).endVertex();
      llllllllllllllllllIllIIlIlIIIIlI.pos(llllllllllllllllllIllIIlIlIIIllI.maxX, llllllllllllllllllIllIIlIlIIIllI.maxY, llllllllllllllllllIllIIlIlIIIllI.maxZ).endVertex();
      llllllllllllllllllIllIIlIlIIIIlI.pos(llllllllllllllllllIllIIlIlIIIllI.minX, llllllllllllllllllIllIIlIlIIIllI.maxY, llllllllllllllllllIllIIlIlIIIllI.maxZ).endVertex();
      llllllllllllllllllIllIIlIlIIIIlI.pos(llllllllllllllllllIllIIlIlIIIllI.minX, llllllllllllllllllIllIIlIlIIIllI.maxY, llllllllllllllllllIllIIlIlIIIllI.minZ).endVertex();
      llllllllllllllllllIllIIlIlIIIlII.draw();
      llllllllllllllllllIllIIlIlIIIIlI.begin(1, DefaultVertexFormats.POSITION);
      llllllllllllllllllIllIIlIlIIIIlI.pos(llllllllllllllllllIllIIlIlIIIllI.minX, llllllllllllllllllIllIIlIlIIIllI.minY, llllllllllllllllllIllIIlIlIIIllI.minZ).endVertex();
      llllllllllllllllllIllIIlIlIIIIlI.pos(llllllllllllllllllIllIIlIlIIIllI.minX, llllllllllllllllllIllIIlIlIIIllI.maxY, llllllllllllllllllIllIIlIlIIIllI.minZ).endVertex();
      llllllllllllllllllIllIIlIlIIIIlI.pos(llllllllllllllllllIllIIlIlIIIllI.maxX, llllllllllllllllllIllIIlIlIIIllI.minY, llllllllllllllllllIllIIlIlIIIllI.minZ).endVertex();
      llllllllllllllllllIllIIlIlIIIIlI.pos(llllllllllllllllllIllIIlIlIIIllI.maxX, llllllllllllllllllIllIIlIlIIIllI.maxY, llllllllllllllllllIllIIlIlIIIllI.minZ).endVertex();
      llllllllllllllllllIllIIlIlIIIIlI.pos(llllllllllllllllllIllIIlIlIIIllI.maxX, llllllllllllllllllIllIIlIlIIIllI.minY, llllllllllllllllllIllIIlIlIIIllI.maxZ).endVertex();
      llllllllllllllllllIllIIlIlIIIIlI.pos(llllllllllllllllllIllIIlIlIIIllI.maxX, llllllllllllllllllIllIIlIlIIIllI.maxY, llllllllllllllllllIllIIlIlIIIllI.maxZ).endVertex();
      llllllllllllllllllIllIIlIlIIIIlI.pos(llllllllllllllllllIllIIlIlIIIllI.minX, llllllllllllllllllIllIIlIlIIIllI.minY, llllllllllllllllllIllIIlIlIIIllI.maxZ).endVertex();
      llllllllllllllllllIllIIlIlIIIIlI.pos(llllllllllllllllllIllIIlIlIIIllI.minX, llllllllllllllllllIllIIlIlIIIllI.maxY, llllllllllllllllllIllIIlIlIIIllI.maxZ).endVertex();
      llllllllllllllllllIllIIlIlIIIlII.draw();
   }

   public static Vec3 WorldToScreen(Vec3 llllllllllllllllllIllIIIllIIllII) {
      float llllllllllllllllllIllIIIllIIlIll = BufferUtils.createFloatBuffer(3);
      boolean llllllllllllllllllIllIIIllIIllIl = GLU.gluProject((float)llllllllllllllllllIllIIIllIIllII.xCoord, (float)llllllllllllllllllIllIIIllIIllII.yCoord, (float)llllllllllllllllllIllIIIllIIllII.zCoord, modelView, projection, viewport, llllllllllllllllllIllIIIllIIlIll);
      return llllllllllllllllllIllIIIllIIllIl ? new Vec3((double)llllllllllllllllllIllIIIllIIlIll.get(0), (double)((float)Display.getHeight() - llllllllllllllllllIllIIIllIIlIll.get(1)), (double)llllllllllllllllllIllIIIllIIlIll.get(2)) : null;
   }

   public static Vec3 getEntityRenderPosition(Entity llllllllllllllllllIllIIIllIIlIII) {
      return new Vec3(getEntityRenderX(llllllllllllllllllIllIIIllIIlIII), getEntityRenderY(llllllllllllllllllIllIIIllIIlIII), getEntityRenderZ(llllllllllllllllllIllIIIllIIlIII));
   }

   public static void drawtargethudRect(double llllllllllllllllllIlIllllIlIIIIl, double llllllllllllllllllIlIllllIIllIlI, double llllllllllllllllllIlIllllIIlllll, double llllllllllllllllllIlIllllIIllIII, int llllllllllllllllllIlIllllIIlllIl, int llllllllllllllllllIlIllllIIlllII) {
      drawRect(llllllllllllllllllIlIllllIlIIIIl + 1.0D, llllllllllllllllllIlIllllIIllIlI, llllllllllllllllllIlIllllIIlllll - 1.0D, llllllllllllllllllIlIllllIIllIII, llllllllllllllllllIlIllllIIlllIl);
      drawRect(llllllllllllllllllIlIllllIlIIIIl, llllllllllllllllllIlIllllIIllIlI + 1.0D, llllllllllllllllllIlIllllIlIIIIl + 1.0D, llllllllllllllllllIlIllllIIllIII - 1.0D, llllllllllllllllllIlIllllIIlllIl);
      drawRect(llllllllllllllllllIlIllllIlIIIIl + 1.0D, llllllllllllllllllIlIllllIIllIlI + 1.0D, llllllllllllllllllIlIllllIlIIIIl + 0.5D, llllllllllllllllllIlIllllIIllIlI + 0.5D, llllllllllllllllllIlIllllIIlllIl);
      drawRect(llllllllllllllllllIlIllllIlIIIIl + 1.0D, llllllllllllllllllIlIllllIIllIlI + 1.0D, llllllllllllllllllIlIllllIlIIIIl + 0.5D, llllllllllllllllllIlIllllIIllIlI + 0.5D, llllllllllllllllllIlIllllIIlllIl);
      drawRect(llllllllllllllllllIlIllllIIlllll - 1.0D, llllllllllllllllllIlIllllIIllIlI + 1.0D, llllllllllllllllllIlIllllIIlllll - 0.5D, llllllllllllllllllIlIllllIIllIlI + 0.5D, llllllllllllllllllIlIllllIIlllIl);
      drawRect(llllllllllllllllllIlIllllIIlllll - 1.0D, llllllllllllllllllIlIllllIIllIlI + 1.0D, llllllllllllllllllIlIllllIIlllll, llllllllllllllllllIlIllllIIllIII - 1.0D, llllllllllllllllllIlIllllIIlllIl);
      drawRect(llllllllllllllllllIlIllllIlIIIIl + 1.0D, llllllllllllllllllIlIllllIIllIII - 1.0D, llllllllllllllllllIlIllllIlIIIIl + 0.5D, llllllllllllllllllIlIllllIIllIII - 0.5D, llllllllllllllllllIlIllllIIlllIl);
      drawRect(llllllllllllllllllIlIllllIIlllll - 1.0D, llllllllllllllllllIlIllllIIllIII - 1.0D, llllllllllllllllllIlIllllIIlllll - 0.5D, llllllllllllllllllIlIllllIIllIII - 0.5D, llllllllllllllllllIlIllllIIlllIl);
   }

   public static double getEntityRenderY(Entity llllllllllllllllllIllIIIllIIIIlI) {
      return llllllllllllllllllIllIIIllIIIIlI.lastTickPosY + (llllllllllllllllllIllIIIllIIIIlI.posY - llllllllllllllllllIllIIIllIIIIlI.lastTickPosY) * (double)Minecraft.getMinecraft().timer.renderPartialTicks - mc.getRenderManager().renderPosY;
   }

   public static void setGlState(int llllllllllllllllllIlIIlllIIIIIII, boolean llllllllllllllllllIlIIllIlllllll) {
      if (llllllllllllllllllIlIIllIlllllll) {
         GL11.glEnable(llllllllllllllllllIlIIlllIIIIIII);
      } else {
         GL11.glDisable(llllllllllllllllllIlIIlllIIIIIII);
      }

   }

   public static void drawRect(float llllllllllllllllllIlIllIIlIIIIlI, float llllllllllllllllllIlIllIIlIIIllI, float llllllllllllllllllIlIllIIlIIIIII, float llllllllllllllllllIlIllIIlIIIlII, Color llllllllllllllllllIlIllIIlIIIIll) {
      drawRect(llllllllllllllllllIlIllIIlIIIIlI, llllllllllllllllllIlIllIIlIIIllI, llllllllllllllllllIlIllIIlIIIIII, llllllllllllllllllIlIllIIlIIIlII, llllllllllllllllllIlIllIIlIIIIll.getRGB());
   }

   public static void draw2D(EntityLivingBase llllllllllllllllllIlIlIIIIIlIIll, double llllllllllllllllllIlIlIIIIIlIIlI, double llllllllllllllllllIlIlIIIIIllIII, double llllllllllllllllllIlIlIIIIIlIlll, int llllllllllllllllllIlIlIIIIIIllll, int llllllllllllllllllIlIlIIIIIIllIl) {
      GlStateManager.pushMatrix();
      GlStateManager.translate(llllllllllllllllllIlIlIIIIIlIIlI, llllllllllllllllllIlIlIIIIIllIII, llllllllllllllllllIlIlIIIIIlIlll);
      GL11.glNormal3f(0.0F, 0.0F, 0.0F);
      GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
      GlStateManager.scale(-0.1D, -0.1D, 0.1D);
      GL11.glDisable(2929);
      GL11.glBlendFunc(770, 771);
      GlStateManager.enableTexture2D();
      GlStateManager.depthMask(true);
      drawRect(-7.0F, 2.0F, -4.0F, 3.0F, llllllllllllllllllIlIlIIIIIIllll);
      drawRect(4.0F, 2.0F, 7.0F, 3.0F, llllllllllllllllllIlIlIIIIIIllll);
      drawRect(-7.0F, 0.5F, -6.0F, 3.0F, llllllllllllllllllIlIlIIIIIIllll);
      drawRect(6.0F, 0.5F, 7.0F, 3.0F, llllllllllllllllllIlIlIIIIIIllll);
      drawRect(-7.0F, 3.0F, -4.0F, 3.3F, llllllllllllllllllIlIlIIIIIIllIl);
      drawRect(4.0F, 3.0F, 7.0F, 3.3F, llllllllllllllllllIlIlIIIIIIllIl);
      drawRect(-7.3F, 0.5F, -7.0F, 3.3F, llllllllllllllllllIlIlIIIIIIllIl);
      drawRect(7.0F, 0.5F, 7.3F, 3.3F, llllllllllllllllllIlIlIIIIIIllIl);
      GlStateManager.translate(0.0D, 21.0D + -(llllllllllllllllllIlIlIIIIIlIIll.getEntityBoundingBox().maxY - llllllllllllllllllIlIlIIIIIlIIll.getEntityBoundingBox().minY) * 12.0D, 0.0D);
      drawRect(4.0F, -20.0F, 7.0F, -19.0F, llllllllllllllllllIlIlIIIIIIllll);
      drawRect(-7.0F, -20.0F, -4.0F, -19.0F, llllllllllllllllllIlIlIIIIIIllll);
      drawRect(6.0F, -20.0F, 7.0F, -17.5F, llllllllllllllllllIlIlIIIIIIllll);
      drawRect(-7.0F, -20.0F, -6.0F, -17.5F, llllllllllllllllllIlIlIIIIIIllll);
      drawRect(7.0F, -20.0F, 7.3F, -17.5F, llllllllllllllllllIlIlIIIIIIllIl);
      drawRect(-7.3F, -20.0F, -7.0F, -17.5F, llllllllllllllllllIlIlIIIIIIllIl);
      drawRect(4.0F, -20.3F, 7.3F, -20.0F, llllllllllllllllllIlIlIIIIIIllIl);
      drawRect(-7.3F, -20.3F, -4.0F, -20.0F, llllllllllllllllllIlIlIIIIIIllIl);
      GL11.glEnable(2929);
      GlStateManager.popMatrix();
   }

   public static void rectangle(double llllllllllllllllllIlIllllllIIlII, double llllllllllllllllllIlIlllllIllIIl, double llllllllllllllllllIlIlllllIllIII, double llllllllllllllllllIlIllllllIIIIl, int llllllllllllllllllIlIlllllIlIllI) {
      double llllllllllllllllllIlIllllllIIlIl;
      if (llllllllllllllllllIlIllllllIIlII < llllllllllllllllllIlIlllllIllIII) {
         llllllllllllllllllIlIllllllIIlIl = llllllllllllllllllIlIllllllIIlII;
         llllllllllllllllllIlIllllllIIlII = llllllllllllllllllIlIlllllIllIII;
         llllllllllllllllllIlIlllllIllIII = llllllllllllllllllIlIllllllIIlIl;
      }

      if (llllllllllllllllllIlIlllllIllIIl < llllllllllllllllllIlIllllllIIIIl) {
         llllllllllllllllllIlIllllllIIlIl = llllllllllllllllllIlIlllllIllIIl;
         llllllllllllllllllIlIlllllIllIIl = llllllllllllllllllIlIllllllIIIIl;
         llllllllllllllllllIlIllllllIIIIl = llllllllllllllllllIlIllllllIIlIl;
      }

      boolean llllllllllllllllllIlIlllllIlIlIl = (float)(llllllllllllllllllIlIlllllIlIllI >> 24 & 255) / 255.0F;
      float llllllllllllllllllIlIlllllIllllI = (float)(llllllllllllllllllIlIlllllIlIllI >> 16 & 255) / 255.0F;
      float llllllllllllllllllIlIlllllIlllIl = (float)(llllllllllllllllllIlIlllllIlIllI >> 8 & 255) / 255.0F;
      float llllllllllllllllllIlIlllllIlllII = (float)(llllllllllllllllllIlIlllllIlIllI & 255) / 255.0F;
      WorldRenderer llllllllllllllllllIlIlllllIllIll = Tessellator.getInstance().getWorldRenderer();
      GlStateManager.enableBlend();
      GlStateManager.disableTexture2D();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.color(llllllllllllllllllIlIlllllIllllI, llllllllllllllllllIlIlllllIlllIl, llllllllllllllllllIlIlllllIlllII, llllllllllllllllllIlIlllllIlIlIl);
      llllllllllllllllllIlIlllllIllIll.begin(7, DefaultVertexFormats.POSITION);
      llllllllllllllllllIlIlllllIllIll.pos(llllllllllllllllllIlIllllllIIlII, llllllllllllllllllIlIllllllIIIIl, 0.0D).endVertex();
      llllllllllllllllllIlIlllllIllIll.pos(llllllllllllllllllIlIlllllIllIII, llllllllllllllllllIlIllllllIIIIl, 0.0D).endVertex();
      llllllllllllllllllIlIlllllIllIll.pos(llllllllllllllllllIlIlllllIllIII, llllllllllllllllllIlIlllllIllIIl, 0.0D).endVertex();
      llllllllllllllllllIlIlllllIllIll.pos(llllllllllllllllllIlIllllllIIlII, llllllllllllllllllIlIlllllIllIIl, 0.0D).endVertex();
      Tessellator.getInstance().draw();
      GlStateManager.enableTexture2D();
      GlStateManager.disableBlend();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static void drawblock(double llllllllllllllllllIllIIlIIlIIIII, double llllllllllllllllllIllIIlIIIlllll, double llllllllllllllllllIllIIlIIIllllI, int llllllllllllllllllIllIIlIIIlllIl, int llllllllllllllllllIllIIlIIIlllII, float llllllllllllllllllIllIIlIIIIllIl) {
      short llllllllllllllllllIllIIlIIIIllII = (float)(llllllllllllllllllIllIIlIIIlllIl >> 24 & 255) / 255.0F;
      byte llllllllllllllllllIllIIlIIIIlIll = (float)(llllllllllllllllllIllIIlIIIlllIl >> 16 & 255) / 255.0F;
      float llllllllllllllllllIllIIlIIIllIII = (float)(llllllllllllllllllIllIIlIIIlllIl >> 8 & 255) / 255.0F;
      byte llllllllllllllllllIllIIlIIIIlIIl = (float)(llllllllllllllllllIllIIlIIIlllIl & 255) / 255.0F;
      float llllllllllllllllllIllIIlIIIlIllI = (float)(llllllllllllllllllIllIIlIIIlllII >> 24 & 255) / 255.0F;
      long llllllllllllllllllIllIIlIIIIIlll = (float)(llllllllllllllllllIllIIlIIIlllII >> 16 & 255) / 255.0F;
      float llllllllllllllllllIllIIlIIIlIlII = (float)(llllllllllllllllllIllIIlIIIlllII >> 8 & 255) / 255.0F;
      float llllllllllllllllllIllIIlIIIlIIll = (float)(llllllllllllllllllIllIIlIIIlllII & 255) / 255.0F;
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glColor4f(llllllllllllllllllIllIIlIIIIlIll, llllllllllllllllllIllIIlIIIllIII, llllllllllllllllllIllIIlIIIIlIIl, llllllllllllllllllIllIIlIIIIllII);
      drawOutlinedBoundingBox(new AxisAlignedBB(llllllllllllllllllIllIIlIIlIIIII, llllllllllllllllllIllIIlIIIlllll, llllllllllllllllllIllIIlIIIllllI, llllllllllllllllllIllIIlIIlIIIII + 1.0D, llllllllllllllllllIllIIlIIIlllll + 1.0D, llllllllllllllllllIllIIlIIIllllI + 1.0D));
      GL11.glLineWidth(llllllllllllllllllIllIIlIIIIllIl);
      GL11.glColor4f(llllllllllllllllllIllIIlIIIIIlll, llllllllllllllllllIllIIlIIIlIlII, llllllllllllllllllIllIIlIIIlIIll, llllllllllllllllllIllIIlIIIlIllI);
      drawOutlinedBoundingBox(new AxisAlignedBB(llllllllllllllllllIllIIlIIlIIIII, llllllllllllllllllIllIIlIIIlllll, llllllllllllllllllIllIIlIIIllllI, llllllllllllllllllIllIIlIIlIIIII + 1.0D, llllllllllllllllllIllIIlIIIlllll + 1.0D, llllllllllllllllllIllIIlIIIllllI + 1.0D));
      GL11.glDisable(2848);
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
   }

   public static void chamsColor(Color llllllllllllllllllIlIIllIllllIIl) {
      float llllllllllllllllllIlIIllIllllIII = (float)llllllllllllllllllIlIIllIllllIIl.getRed() / 255.0F;
      double llllllllllllllllllIlIIllIlllIIlI = (float)llllllllllllllllllIlIIllIllllIIl.getGreen() / 255.0F;
      float llllllllllllllllllIlIIllIlllIllI = (float)llllllllllllllllllIlIIllIllllIIl.getBlue() / 255.0F;
      float llllllllllllllllllIlIIllIlllIlIl = (float)llllllllllllllllllIlIIllIllllIIl.getAlpha() / 255.0F;
      GL11.glColor4d((double)llllllllllllllllllIlIIllIllllIII, (double)llllllllllllllllllIlIIllIlllIIlI, (double)llllllllllllllllllIlIIllIlllIllI, (double)llllllllllllllllllIlIIllIlllIlIl);
   }

   public static void setGlCap(int llllllllllllllllllIlIIlllIIIlIII, boolean llllllllllllllllllIlIIlllIIIIlIl) {
      glCapMap.put(llllllllllllllllllIlIIlllIIIlIII, GL11.glGetBoolean(llllllllllllllllllIlIIlllIIIlIII));
      boolean var10001 = false;
      setGlState(llllllllllllllllllIlIIlllIIIlIII, llllllllllllllllllIlIIlllIIIIlIl);
   }

   public static void drawBlockBox(BlockPos llllllllllllllllllIllIIIIIllllII, Color llllllllllllllllllIllIIIIlIIIlIl, boolean llllllllllllllllllIllIIIIlIIIlII) {
      RenderManager llllllllllllllllllIllIIIIlIIIIll = mc.getRenderManager();
      Timer llllllllllllllllllIllIIIIlIIIIlI = mc.timer;
      double llllllllllllllllllIllIIIIlIIIIIl = (double)llllllllllllllllllIllIIIIIllllII.getX() - llllllllllllllllllIllIIIIlIIIIll.renderPosX;
      short llllllllllllllllllIllIIIIIllIllI = (double)llllllllllllllllllIllIIIIIllllII.getY() - llllllllllllllllllIllIIIIlIIIIll.renderPosY;
      double llllllllllllllllllIllIIIIIllllll = (double)llllllllllllllllllIllIIIIIllllII.getZ() - llllllllllllllllllIllIIIIlIIIIll.renderPosZ;
      boolean llllllllllllllllllIllIIIIIllIlII = new AxisAlignedBB(llllllllllllllllllIllIIIIlIIIIIl, llllllllllllllllllIllIIIIIllIllI, llllllllllllllllllIllIIIIIllllll, llllllllllllllllllIllIIIIlIIIIIl + 1.0D, llllllllllllllllllIllIIIIIllIllI + 1.0D, llllllllllllllllllIllIIIIIllllll + 1.0D);
      Block llllllllllllllllllIllIIIIIllllIl = BlockUtils.getBlock(llllllllllllllllllIllIIIIIllllII);
      if (llllllllllllllllllIllIIIIIllllIl != null) {
         double llllllllllllllllllIllIIIIIllIIlI = mc.thePlayer;
         char llllllllllllllllllIllIIIIIllIIIl = llllllllllllllllllIllIIIIIllIIlI.lastTickPosX + (llllllllllllllllllIllIIIIIllIIlI.posX - llllllllllllllllllIllIIIIIllIIlI.lastTickPosX) * (double)llllllllllllllllllIllIIIIlIIIIlI.renderPartialTicks;
         double llllllllllllllllllIllIIIIlIIlIII = llllllllllllllllllIllIIIIIllIIlI.lastTickPosY + (llllllllllllllllllIllIIIIIllIIlI.posY - llllllllllllllllllIllIIIIIllIIlI.lastTickPosY) * (double)llllllllllllllllllIllIIIIlIIIIlI.renderPartialTicks;
         boolean llllllllllllllllllIllIIIIIlIllll = llllllllllllllllllIllIIIIIllIIlI.lastTickPosZ + (llllllllllllllllllIllIIIIIllIIlI.posZ - llllllllllllllllllIllIIIIIllIIlI.lastTickPosZ) * (double)llllllllllllllllllIllIIIIlIIIIlI.renderPartialTicks;
         llllllllllllllllllIllIIIIIllIlII = llllllllllllllllllIllIIIIIllllIl.getSelectedBoundingBox(mc.theWorld, llllllllllllllllllIllIIIIIllllII).expand(0.0020000000949949026D, 0.0020000000949949026D, 0.0020000000949949026D).offset(-llllllllllllllllllIllIIIIIllIIIl, -llllllllllllllllllIllIIIIlIIlIII, -llllllllllllllllllIllIIIIIlIllll);
      }

      GL11.glBlendFunc(770, 771);
      enableGlCap(3042);
      disableGlCap(3553, 2929);
      GL11.glDepthMask(false);
      glColor(llllllllllllllllllIllIIIIlIIIlIl.getRed(), llllllllllllllllllIllIIIIlIIIlIl.getGreen(), llllllllllllllllllIllIIIIlIIIlIl.getBlue(), llllllllllllllllllIllIIIIlIIIlIl.getAlpha() != 255 ? llllllllllllllllllIllIIIIlIIIlIl.getAlpha() : (llllllllllllllllllIllIIIIlIIIlII ? 26 : 35));
      drawFilledBox(llllllllllllllllllIllIIIIIllIlII);
      if (llllllllllllllllllIllIIIIlIIIlII) {
         GL11.glLineWidth(1.0F);
         enableGlCap(2848);
         glColor(llllllllllllllllllIllIIIIlIIIlIl);
         RenderGlobal.drawSelectionBoundingBox(llllllllllllllllllIllIIIIIllIlII);
      }

      GlStateManager.resetColor();
      GL11.glDepthMask(true);
      resetCaps();
   }

   public static void drawEntityBox(Entity llllllllllllllllllIlIlllIlIIlIII, Color llllllllllllllllllIlIlllIlIIIlll, boolean llllllllllllllllllIlIlllIlIllIII) {
      RenderManager llllllllllllllllllIlIlllIlIlIlIl = mc.getRenderManager();
      Timer llllllllllllllllllIlIlllIlIlIIll = mc.timer;
      GL11.glBlendFunc(770, 771);
      enableGlCap(3042);
      disableGlCap(3553, 2929);
      GL11.glDepthMask(false);
      double llllllllllllllllllIlIlllIlIlIIIl = llllllllllllllllllIlIlllIlIIlIII.lastTickPosX + (llllllllllllllllllIlIlllIlIIlIII.posX - llllllllllllllllllIlIlllIlIIlIII.lastTickPosX) * (double)llllllllllllllllllIlIlllIlIlIIll.renderPartialTicks - llllllllllllllllllIlIlllIlIlIlIl.renderPosX;
      double llllllllllllllllllIlIlllIlIIllll = llllllllllllllllllIlIlllIlIIlIII.lastTickPosY + (llllllllllllllllllIlIlllIlIIlIII.posY - llllllllllllllllllIlIlllIlIIlIII.lastTickPosY) * (double)llllllllllllllllllIlIlllIlIlIIll.renderPartialTicks - llllllllllllllllllIlIlllIlIlIlIl.renderPosY;
      double llllllllllllllllllIlIlllIlIIllIl = llllllllllllllllllIlIlllIlIIlIII.lastTickPosZ + (llllllllllllllllllIlIlllIlIIlIII.posZ - llllllllllllllllllIlIlllIlIIlIII.lastTickPosZ) * (double)llllllllllllllllllIlIlllIlIlIIll.renderPartialTicks - llllllllllllllllllIlIlllIlIlIlIl.renderPosZ;
      boolean llllllllllllllllllIlIlllIIlllIlI = llllllllllllllllllIlIlllIlIIlIII.getEntityBoundingBox();
      long llllllllllllllllllIlIlllIIlllIIl = new AxisAlignedBB(llllllllllllllllllIlIlllIIlllIlI.minX - llllllllllllllllllIlIlllIlIIlIII.posX + llllllllllllllllllIlIlllIlIlIIIl - 0.05D, llllllllllllllllllIlIlllIIlllIlI.minY - llllllllllllllllllIlIlllIlIIlIII.posY + llllllllllllllllllIlIlllIlIIllll, llllllllllllllllllIlIlllIIlllIlI.minZ - llllllllllllllllllIlIlllIlIIlIII.posZ + llllllllllllllllllIlIlllIlIIllIl - 0.05D, llllllllllllllllllIlIlllIIlllIlI.maxX - llllllllllllllllllIlIlllIlIIlIII.posX + llllllllllllllllllIlIlllIlIlIIIl + 0.05D, llllllllllllllllllIlIlllIIlllIlI.maxY - llllllllllllllllllIlIlllIlIIlIII.posY + llllllllllllllllllIlIlllIlIIllll + 0.15D, llllllllllllllllllIlIlllIIlllIlI.maxZ - llllllllllllllllllIlIlllIlIIlIII.posZ + llllllllllllllllllIlIlllIlIIllIl + 0.05D);
      if (llllllllllllllllllIlIlllIlIllIII) {
         GL11.glLineWidth(1.0F);
         enableGlCap(2848);
         glColor(llllllllllllllllllIlIlllIlIIIlll.getRed(), llllllllllllllllllIlIlllIlIIIlll.getGreen(), llllllllllllllllllIlIlllIlIIIlll.getBlue(), 95);
         RenderGlobal.drawSelectionBoundingBox(llllllllllllllllllIlIlllIIlllIIl);
      }

      glColor(llllllllllllllllllIlIlllIlIIIlll.getRed(), llllllllllllllllllIlIlllIlIIIlll.getGreen(), llllllllllllllllllIlIlllIlIIIlll.getBlue(), llllllllllllllllllIlIlllIlIllIII ? 26 : 35);
      drawFilledBox(llllllllllllllllllIlIlllIIlllIIl);
      GlStateManager.resetColor();
      GL11.glDepthMask(true);
      resetCaps();
   }

   public static void drawPlatform(Entity llllllllllllllllllIlIlllIIIIIIII, Color llllllllllllllllllIlIllIllllllIl) {
      Exception llllllllllllllllllIlIllIlllIIlIl = mc.getRenderManager();
      Timer llllllllllllllllllIlIllIlllllIII = mc.timer;
      String llllllllllllllllllIlIllIllIlllll = llllllllllllllllllIlIlllIIIIIIII.lastTickPosX + (llllllllllllllllllIlIlllIIIIIIII.posX - llllllllllllllllllIlIlllIIIIIIII.lastTickPosX) * (double)llllllllllllllllllIlIllIlllllIII.renderPartialTicks - llllllllllllllllllIlIllIlllIIlIl.renderPosX;
      double llllllllllllllllllIlIllIllllIIll = llllllllllllllllllIlIlllIIIIIIII.lastTickPosY + (llllllllllllllllllIlIlllIIIIIIII.posY - llllllllllllllllllIlIlllIIIIIIII.lastTickPosY) * (double)llllllllllllllllllIlIllIlllllIII.renderPartialTicks - llllllllllllllllllIlIllIlllIIlIl.renderPosY;
      double llllllllllllllllllIlIllIllllIIII = llllllllllllllllllIlIlllIIIIIIII.lastTickPosZ + (llllllllllllllllllIlIlllIIIIIIII.posZ - llllllllllllllllllIlIlllIIIIIIII.lastTickPosZ) * (double)llllllllllllllllllIlIllIlllllIII.renderPartialTicks - llllllllllllllllllIlIllIlllIIlIl.renderPosZ;
      char llllllllllllllllllIlIllIllIllIII = llllllllllllllllllIlIlllIIIIIIII.getEntityBoundingBox().offset(-llllllllllllllllllIlIlllIIIIIIII.posX, -llllllllllllllllllIlIlllIIIIIIII.posY, -llllllllllllllllllIlIlllIIIIIIII.posZ).offset(llllllllllllllllllIlIllIllIlllll, llllllllllllllllllIlIllIllllIIll, llllllllllllllllllIlIllIllllIIII);
      drawAxisAlignedBB(new AxisAlignedBB(llllllllllllllllllIlIllIllIllIII.minX, llllllllllllllllllIlIllIllIllIII.maxY + 0.2D, llllllllllllllllllIlIllIllIllIII.minZ, llllllllllllllllllIlIllIllIllIII.maxX, llllllllllllllllllIlIllIllIllIII.maxY + 0.26D, llllllllllllllllllIlIllIllIllIII.maxZ), llllllllllllllllllIlIllIllllllIl);
   }

   private static void glColor(int llllllllllllllllllIlIlIIIlIIlIll) {
      byte llllllllllllllllllIlIlIIIlIIlIIl = (float)(llllllllllllllllllIlIlIIIlIIlIll >> 24 & 255) / 255.0F;
      float llllllllllllllllllIlIlIIIlIlIIII = (float)(llllllllllllllllllIlIlIIIlIIlIll >> 16 & 255) / 255.0F;
      float llllllllllllllllllIlIlIIIlIIlllI = (float)(llllllllllllllllllIlIlIIIlIIlIll >> 8 & 255) / 255.0F;
      float llllllllllllllllllIlIlIIIlIIIllI = (float)(llllllllllllllllllIlIlIIIlIIlIll & 255) / 255.0F;
      GlStateManager.color(llllllllllllllllllIlIlIIIlIlIIII, llllllllllllllllllIlIlIIIlIIlllI, llllllllllllllllllIlIlIIIlIIIllI, llllllllllllllllllIlIlIIIlIIlIIl);
   }

   public static void NdrawCircle(float llllllllllllllllllIlIlIlllIlIIIl, float llllllllllllllllllIlIlIlllIlllII, float llllllllllllllllllIlIlIlllIIllll, int llllllllllllllllllIlIlIlllIllIlI, float llllllllllllllllllIlIlIlllIllIIl, int llllllllllllllllllIlIlIlllIIlIll) {
      GL11.glPushMatrix();
      llllllllllllllllllIlIlIlllIlIIIl *= 2.0F;
      llllllllllllllllllIlIlIlllIlllII *= 2.0F;
      int llllllllllllllllllIlIlIlllIIlIlI = (float)(6.2831852D / (double)llllllllllllllllllIlIlIlllIllIlI);
      double llllllllllllllllllIlIlIlllIIlIII = (float)Math.cos((double)llllllllllllllllllIlIlIlllIIlIlI);
      float llllllllllllllllllIlIlIlllIlIlIl = (float)Math.sin((double)llllllllllllllllllIlIlIlllIIlIlI);
      float llllllllllllllllllIlIlIlllIlIlII = llllllllllllllllllIlIlIlllIIllll *= 2.0F;
      float llllllllllllllllllIlIlIlllIlIIll = 0.0F;
      enableGL2D();
      GL11.glScalef(0.5F, 0.5F, 0.5F);
      GlStateManager.color(0.0F, 0.0F, 0.0F);
      GL11.glLineWidth(llllllllllllllllllIlIlIlllIllIIl);
      GlStateManager.resetColor();
      glColor(llllllllllllllllllIlIlIlllIIlIll);
      GL11.glBegin(2);

      for(int llllllllllllllllllIlIlIlllIlIIlI = 0; llllllllllllllllllIlIlIlllIlIIlI < llllllllllllllllllIlIlIlllIllIlI; ++llllllllllllllllllIlIlIlllIlIIlI) {
         GL11.glVertex2f(llllllllllllllllllIlIlIlllIlIlII + llllllllllllllllllIlIlIlllIlIIIl, llllllllllllllllllIlIlIlllIlIIll + llllllllllllllllllIlIlIlllIlllII);
         char llllllllllllllllllIlIlIlllIIIIII = llllllllllllllllllIlIlIlllIlIlII;
         llllllllllllllllllIlIlIlllIlIlII = llllllllllllllllllIlIlIlllIIlIII * llllllllllllllllllIlIlIlllIlIlII - llllllllllllllllllIlIlIlllIlIlIl * llllllllllllllllllIlIlIlllIlIIll;
         llllllllllllllllllIlIlIlllIlIIll = llllllllllllllllllIlIlIlllIlIlIl * llllllllllllllllllIlIlIlllIIIIII + llllllllllllllllllIlIlIlllIIlIII * llllllllllllllllllIlIlIlllIlIIll;
      }

      glColor(llllllllllllllllllIlIlIlllIIlIll);
      GL11.glEnd();
      GL11.glScalef(2.0F, 2.0F, 2.0F);
      disableGL2D();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glPopMatrix();
   }

   public static void cylinder(Entity llllllllllllllllllIlIllIIIllIlIl, double llllllllllllllllllIlIllIIIllIIll, double llllllllllllllllllIlIllIIIllIIlI, double llllllllllllllllllIlIllIIIlIlIII, double llllllllllllllllllIlIllIIIlIllll, int llllllllllllllllllIlIllIIIlIIlIl) {
      GL11.glPushMatrix();
      GL11.glDisable(2896);
      GL11.glDisable(3553);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(2929);
      GL11.glEnable(2848);
      GL11.glDepthMask(true);
      GlStateManager.translate(llllllllllllllllllIlIllIIIllIIll, llllllllllllllllllIlIllIIIllIIlI, llllllllllllllllllIlIllIIIlIlIII);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 0.75F);
      GlStateManager.rotate(180.0F, 90.0F, 0.0F, 2.0F);
      GlStateManager.rotate(180.0F, 0.0F, 90.0F, 90.0F);
      Cylinder llllllllllllllllllIlIllIIIlIllIl = new Cylinder();
      llllllllllllllllllIlIllIIIlIllIl.setDrawStyle(100011);
      llllllllllllllllllIlIllIIIlIllIl.draw((float)(llllllllllllllllllIlIllIIIlIllll - 0.5D), (float)(llllllllllllllllllIlIllIIIlIllll - 0.5D), 0.0F, llllllllllllllllllIlIllIIIlIIlIl, 0);
      GL11.glDepthMask(true);
      GL11.glDisable(2848);
      GL11.glEnable(2929);
      GL11.glDisable(3042);
      GL11.glEnable(2896);
      GL11.glEnable(3553);
      GL11.glPopMatrix();
   }

   public static void renderItemStack(ItemStack llllllllllllllllllIllIIIllIlIlIl, int llllllllllllllllllIllIIIllIlIlll, int llllllllllllllllllIllIIIllIlIIll) {
      GlStateManager.pushMatrix();
      GlStateManager.depthMask(true);
      GlStateManager.clear(256);
      RenderHelper.enableStandardItemLighting();
      Minecraft.getMinecraft().getRenderItem().zLevel = -150.0F;
      GlStateManager.disableDepth();
      GlStateManager.disableTexture2D();
      GlStateManager.enableBlend();
      GlStateManager.enableAlpha();
      GlStateManager.enableTexture2D();
      GlStateManager.enableLighting();
      GlStateManager.enableDepth();
      Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(llllllllllllllllllIllIIIllIlIlIl, llllllllllllllllllIllIIIllIlIlll, llllllllllllllllllIllIIIllIlIIll);
      Minecraft.getMinecraft().getRenderItem().renderItemOverlays(Minecraft.getMinecraft().fontRendererObj, llllllllllllllllllIllIIIllIlIlIl, llllllllllllllllllIllIIIllIlIlll, llllllllllllllllllIllIIIllIlIIll);
      Minecraft.getMinecraft().getRenderItem().zLevel = 0.0F;
      RenderHelper.disableStandardItemLighting();
      GlStateManager.disableCull();
      GlStateManager.enableAlpha();
      GlStateManager.disableBlend();
      GlStateManager.disableLighting();
      GlStateManager.disableDepth();
      GlStateManager.enableDepth();
      GlStateManager.popMatrix();
   }

   public static void glColor(Color llllllllllllllllllIlIlIIlIIIlIll) {
      long llllllllllllllllllIlIlIIlIIIIlIl = (float)llllllllllllllllllIlIlIIlIIIlIll.getRed() / 255.0F;
      float llllllllllllllllllIlIlIIlIIIlIIl = (float)llllllllllllllllllIlIlIIlIIIlIll.getGreen() / 255.0F;
      float llllllllllllllllllIlIlIIlIIIIIlI = (float)llllllllllllllllllIlIlIIlIIIlIll.getBlue() / 255.0F;
      float llllllllllllllllllIlIlIIlIIIIlll = (float)llllllllllllllllllIlIlIIlIIIlIll.getAlpha() / 255.0F;
      GlStateManager.color(llllllllllllllllllIlIlIIlIIIIlIl, llllllllllllllllllIlIlIIlIIIlIIl, llllllllllllllllllIlIlIIlIIIIIlI, llllllllllllllllllIlIlIIlIIIIlll);
   }

   public static void enableGlCap(int... llllllllllllllllllIlIIlllIIlllll) {
      int llllllllllllllllllIlIIlllIIlllIl = llllllllllllllllllIlIIlllIIlllll;
      double llllllllllllllllllIlIIlllIIlllII = llllllllllllllllllIlIIlllIIlllll.length;

      for(int llllllllllllllllllIlIIlllIIllIll = 0; llllllllllllllllllIlIIlllIIllIll < llllllllllllllllllIlIIlllIIlllII; ++llllllllllllllllllIlIIlllIIllIll) {
         int llllllllllllllllllIlIIlllIlIIIII = llllllllllllllllllIlIIlllIIlllIl[llllllllllllllllllIlIIlllIIllIll];
         setGlCap(llllllllllllllllllIlIIlllIlIIIII, true);
      }

   }

   public static void drawImage(ResourceLocation llllllllllllllllllIlIlIIllIIlIIl, int llllllllllllllllllIlIlIIllIIllll, int llllllllllllllllllIlIlIIllIIlllI, int llllllllllllllllllIlIlIIllIIllIl, int llllllllllllllllllIlIlIIllIIIIll) {
      GL11.glDisable(2929);
      GL11.glEnable(3042);
      GL11.glDepthMask(false);
      OpenGlHelper.glBlendFunc(770, 771, 1, 0);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      mc.getTextureManager().bindTexture(llllllllllllllllllIlIlIIllIIlIIl);
      Gui.drawModalRectWithCustomSizedTexture(llllllllllllllllllIlIlIIllIIllll, llllllllllllllllllIlIlIIllIIlllI, 0.0F, 0.0F, llllllllllllllllllIlIlIIllIIllIl, llllllllllllllllllIlIlIIllIIIIll, (float)llllllllllllllllllIlIlIIllIIllIl, (float)llllllllllllllllllIlIlIIllIIIIll);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glEnable(2929);
   }

   public static void updateView() {
      GL11.glGetFloat(2982, modelView);
      GL11.glGetFloat(2983, projection);
      GL11.glGetInteger(2978, viewport);
   }

   public static void drawGradientSideways(double llllllllllllllllllIlIllllIllIlII, double llllllllllllllllllIlIlllllIIIIIl, double llllllllllllllllllIlIllllIllIIlI, double llllllllllllllllllIlIllllIllllll, int llllllllllllllllllIlIllllIllIIII, int llllllllllllllllllIlIllllIllllIl) {
      byte llllllllllllllllllIlIllllIlIlllI = (float)(llllllllllllllllllIlIllllIllIIII >> 24 & 255) / 255.0F;
      String llllllllllllllllllIlIllllIlIllIl = (float)(llllllllllllllllllIlIllllIllIIII >> 16 & 255) / 255.0F;
      byte llllllllllllllllllIlIllllIlIllII = (float)(llllllllllllllllllIlIllllIllIIII >> 8 & 255) / 255.0F;
      long llllllllllllllllllIlIllllIlIlIll = (float)(llllllllllllllllllIlIllllIllIIII & 255) / 255.0F;
      int llllllllllllllllllIlIllllIlIlIlI = (float)(llllllllllllllllllIlIllllIllllIl >> 24 & 255) / 255.0F;
      short llllllllllllllllllIlIllllIlIlIIl = (float)(llllllllllllllllllIlIllllIllllIl >> 16 & 255) / 255.0F;
      float llllllllllllllllllIlIllllIllIllI = (float)(llllllllllllllllllIlIllllIllllIl >> 8 & 255) / 255.0F;
      String llllllllllllllllllIlIllllIlIIlll = (float)(llllllllllllllllllIlIllllIllllIl & 255) / 255.0F;
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glShadeModel(7425);
      GL11.glPushMatrix();
      GL11.glBegin(7);
      GL11.glColor4f(llllllllllllllllllIlIllllIlIllIl, llllllllllllllllllIlIllllIlIllII, llllllllllllllllllIlIllllIlIlIll, llllllllllllllllllIlIllllIlIlllI);
      GL11.glVertex2d(llllllllllllllllllIlIllllIllIlII, llllllllllllllllllIlIlllllIIIIIl);
      GL11.glVertex2d(llllllllllllllllllIlIllllIllIlII, llllllllllllllllllIlIllllIllllll);
      GL11.glColor4f(llllllllllllllllllIlIllllIlIlIIl, llllllllllllllllllIlIllllIllIllI, llllllllllllllllllIlIllllIlIIlll, llllllllllllllllllIlIllllIlIlIlI);
      GL11.glVertex2d(llllllllllllllllllIlIllllIllIIlI, llllllllllllllllllIlIllllIllllll);
      GL11.glVertex2d(llllllllllllllllllIlIllllIllIIlI, llllllllllllllllllIlIlllllIIIIIl);
      GL11.glEnd();
      GL11.glPopMatrix();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
      GL11.glShadeModel(7424);
   }

   public static void drawFace(int llllllllllllllllllIllIIIIIIIlIlI, int llllllllllllllllllIllIIIIIIlIlIl, float llllllllllllllllllIllIIIIIIIIllI, float llllllllllllllllllIllIIIIIIIIlIl, int llllllllllllllllllIllIIIIIIIIIll, int llllllllllllllllllIllIIIIIIlIIIl, int llllllllllllllllllIllIIIIIIlIIII, int llllllllllllllllllIllIIIIIIIllll, float llllllllllllllllllIlIlllllllllII, float llllllllllllllllllIlIllllllllIlI, AbstractClientPlayer llllllllllllllllllIlIllllllllIII) {
      try {
         boolean llllllllllllllllllIlIlllllllIllI = llllllllllllllllllIlIllllllllIII.getLocationSkin();
         Minecraft.getMinecraft().getTextureManager().bindTexture(llllllllllllllllllIlIlllllllIllI);
         GL11.glEnable(3042);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         Gui.drawScaledCustomSizeModalRect(llllllllllllllllllIllIIIIIIIlIlI, llllllllllllllllllIllIIIIIIlIlIl, llllllllllllllllllIllIIIIIIIIllI, llllllllllllllllllIllIIIIIIIIlIl, llllllllllllllllllIllIIIIIIIIIll, llllllllllllllllllIllIIIIIIlIIIl, llllllllllllllllllIllIIIIIIlIIII, llllllllllllllllllIllIIIIIIIllll, llllllllllllllllllIlIlllllllllII, llllllllllllllllllIlIllllllllIlI);
         GL11.glDisable(3042);
      } catch (Exception var12) {
         var12.printStackTrace();
      }

   }

   public static void shadow(Entity llllllllllllllllllIllIIIIllIIlll, double llllllllllllllllllIllIIIIllIIllI, double llllllllllllllllllIllIIIIllIIlIl, double llllllllllllllllllIllIIIIlIlllIl, double llllllllllllllllllIllIIIIlIlllII, int llllllllllllllllllIllIIIIllIIIlI, int llllllllllllllllllIllIIIIllIIIIl) {
      GL11.glPushMatrix();
      GL11.glDisable(2896);
      GL11.glDisable(3553);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(2929);
      GL11.glEnable(2848);
      GL11.glDepthMask(true);
      GlStateManager.translate(llllllllllllllllllIllIIIIllIIllI, llllllllllllllllllIllIIIIllIIlIl, llllllllllllllllllIllIIIIlIlllIl);
      GlStateManager.color(0.1F, 0.1F, 0.1F, 0.75F);
      GlStateManager.rotate(180.0F, 90.0F, 0.0F, 2.0F);
      GlStateManager.rotate(180.0F, 0.0F, 90.0F, 90.0F);
      GlStateManager.resetColor();
      glColor(llllllllllllllllllIllIIIIllIIIIl);
      GL11.glBegin(2);
      double llllllllllllllllllIllIIIIlIllIIl = new Cylinder();
      llllllllllllllllllIllIIIIlIllIIl.setDrawStyle(100011);
      llllllllllllllllllIllIIIIlIllIIl.draw((float)(llllllllllllllllllIllIIIIlIlllII - 0.45D), (float)(llllllllllllllllllIllIIIIlIlllII - 0.5D), 0.0F, llllllllllllllllllIllIIIIllIIIlI, 0);
      GL11.glDepthMask(true);
      GL11.glDisable(2848);
      GL11.glEnable(2929);
      GL11.glDisable(3042);
      GL11.glEnable(2896);
      GL11.glEnable(3553);
      GL11.glPopMatrix();
   }

   public static void draw2D(BlockPos llllllllllllllllllIlIIlllllIIlII, int llllllllllllllllllIlIIlllllIllII, int llllllllllllllllllIlIIlllllIlIlI) {
      RenderManager llllllllllllllllllIlIIlllllIlIIl = mc.getRenderManager();
      double llllllllllllllllllIlIIlllllIlIII = (double)llllllllllllllllllIlIIlllllIIlII.getX() + 0.5D - llllllllllllllllllIlIIlllllIlIIl.renderPosX;
      char llllllllllllllllllIlIIllllIlllIl = (double)llllllllllllllllllIlIIlllllIIlII.getY() - llllllllllllllllllIlIIlllllIlIIl.renderPosY;
      double llllllllllllllllllIlIIlllllIIlIl = (double)llllllllllllllllllIlIIlllllIIlII.getZ() + 0.5D - llllllllllllllllllIlIIlllllIlIIl.renderPosZ;
      GlStateManager.pushMatrix();
      GlStateManager.translate(llllllllllllllllllIlIIlllllIlIII, llllllllllllllllllIlIIllllIlllIl, llllllllllllllllllIlIIlllllIIlIl);
      GL11.glNormal3f(0.0F, 0.0F, 0.0F);
      GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
      GlStateManager.scale(-0.1D, -0.1D, 0.1D);
      setGlCap(2929, false);
      GL11.glBlendFunc(770, 771);
      GlStateManager.enableTexture2D();
      GlStateManager.depthMask(true);
      drawRect(-7.0F, 2.0F, -4.0F, 3.0F, llllllllllllllllllIlIIlllllIllII);
      drawRect(4.0F, 2.0F, 7.0F, 3.0F, llllllllllllllllllIlIIlllllIllII);
      drawRect(-7.0F, 0.5F, -6.0F, 3.0F, llllllllllllllllllIlIIlllllIllII);
      drawRect(6.0F, 0.5F, 7.0F, 3.0F, llllllllllllllllllIlIIlllllIllII);
      drawRect(-7.0F, 3.0F, -4.0F, 3.3F, llllllllllllllllllIlIIlllllIlIlI);
      drawRect(4.0F, 3.0F, 7.0F, 3.3F, llllllllllllllllllIlIIlllllIlIlI);
      drawRect(-7.3F, 0.5F, -7.0F, 3.3F, llllllllllllllllllIlIIlllllIlIlI);
      drawRect(7.0F, 0.5F, 7.3F, 3.3F, llllllllllllllllllIlIIlllllIlIlI);
      GlStateManager.translate(0.0F, 9.0F, 0.0F);
      drawRect(4.0F, -20.0F, 7.0F, -19.0F, llllllllllllllllllIlIIlllllIllII);
      drawRect(-7.0F, -20.0F, -4.0F, -19.0F, llllllllllllllllllIlIIlllllIllII);
      drawRect(6.0F, -20.0F, 7.0F, -17.5F, llllllllllllllllllIlIIlllllIllII);
      drawRect(-7.0F, -20.0F, -6.0F, -17.5F, llllllllllllllllllIlIIlllllIllII);
      drawRect(7.0F, -20.0F, 7.3F, -17.5F, llllllllllllllllllIlIIlllllIlIlI);
      drawRect(-7.3F, -20.0F, -7.0F, -17.5F, llllllllllllllllllIlIIlllllIlIlI);
      drawRect(4.0F, -20.3F, 7.3F, -20.0F, llllllllllllllllllIlIIlllllIlIlI);
      drawRect(-7.3F, -20.3F, -4.0F, -20.0F, llllllllllllllllllIlIIlllllIlIlI);
      resetCaps();
      GlStateManager.popMatrix();
   }

   public static void drawCircle(float llllllllllllllllllIlIlIlIIllIlll, float llllllllllllllllllIlIlIlIIllIllI, float llllllllllllllllllIlIlIlIIllIlII, int llllllllllllllllllIlIlIlIIlllIIl, int llllllllllllllllllIlIlIlIIlllIII) {
      GlStateManager.enableBlend();
      GlStateManager.disableTexture2D();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      glColor(Color.WHITE);
      GL11.glEnable(2848);
      GL11.glLineWidth(2.0F);
      GL11.glBegin(3);

      for(float llllllllllllllllllIlIlIlIIllIIII = (float)llllllllllllllllllIlIlIlIIlllIII; llllllllllllllllllIlIlIlIIllIIII >= (float)llllllllllllllllllIlIlIlIIlllIIl; llllllllllllllllllIlIlIlIIllIIII -= 4.0F) {
         GL11.glVertex2f((float)((double)llllllllllllllllllIlIlIlIIllIlll + Math.cos((double)llllllllllllllllllIlIlIlIIllIIII * 3.141592653589793D / 180.0D) * (double)(llllllllllllllllllIlIlIlIIllIlII * 1.001F)), (float)((double)llllllllllllllllllIlIlIlIIllIllI + Math.sin((double)llllllllllllllllllIlIlIlIIllIIII * 3.141592653589793D / 180.0D) * (double)(llllllllllllllllllIlIlIlIIllIlII * 1.001F)));
      }

      GL11.glEnd();
      GL11.glDisable(2848);
      GlStateManager.enableTexture2D();
      GlStateManager.disableBlend();
   }

   public static void drawOutlinedString(String llllllllllllllllllIllIIIlIIlllII, int llllllllllllllllllIllIIIlIIllIll, int llllllllllllllllllIllIIIlIIllIlI, int llllllllllllllllllIllIIIlIIllIIl, int llllllllllllllllllIllIIIlIIllllI) {
      Minecraft llllllllllllllllllIllIIIlIIlllIl = Minecraft.getMinecraft();
      llllllllllllllllllIllIIIlIIlllIl.fontRendererObj.drawString(llllllllllllllllllIllIIIlIIlllII, (int)((float)llllllllllllllllllIllIIIlIIllIll - 1.0F), llllllllllllllllllIllIIIlIIllIlI, llllllllllllllllllIllIIIlIIllllI);
      boolean var10001 = false;
      llllllllllllllllllIllIIIlIIlllIl.fontRendererObj.drawString(llllllllllllllllllIllIIIlIIlllII, (int)((float)llllllllllllllllllIllIIIlIIllIll + 1.0F), llllllllllllllllllIllIIIlIIllIlI, llllllllllllllllllIllIIIlIIllllI);
      var10001 = false;
      llllllllllllllllllIllIIIlIIlllIl.fontRendererObj.drawString(llllllllllllllllllIllIIIlIIlllII, llllllllllllllllllIllIIIlIIllIll, (int)((float)llllllllllllllllllIllIIIlIIllIlI + 1.0F), llllllllllllllllllIllIIIlIIllllI);
      var10001 = false;
      llllllllllllllllllIllIIIlIIlllIl.fontRendererObj.drawString(llllllllllllllllllIllIIIlIIlllII, llllllllllllllllllIllIIIlIIllIll, (int)((float)llllllllllllllllllIllIIIlIIllIlI - 1.0F), llllllllllllllllllIllIIIlIIllllI);
      var10001 = false;
      llllllllllllllllllIllIIIlIIlllIl.fontRendererObj.drawString(llllllllllllllllllIllIIIlIIlllII, llllllllllllllllllIllIIIlIIllIll, llllllllllllllllllIllIIIlIIllIlI, llllllllllllllllllIllIIIlIIllIIl);
      var10001 = false;
   }

   public static void drawFilledCircle(int llllllllllllllllllIlIlIIllllllll, int llllllllllllllllllIlIlIIllllllIl, float llllllllllllllllllIlIlIIlllllIll, Color llllllllllllllllllIlIlIIlllllIIl) {
      int llllllllllllllllllIlIlIlIIIIIIll = 50;
      byte llllllllllllllllllIlIlIIllllIlIl = 6.283185307179586D / (double)llllllllllllllllllIlIlIlIIIIIIll;
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glBegin(6);

      for(int llllllllllllllllllIlIlIIlllIllll = 0; llllllllllllllllllIlIlIIlllIllll < llllllllllllllllllIlIlIlIIIIIIll; ++llllllllllllllllllIlIlIIlllIllll) {
         long llllllllllllllllllIlIlIIllllIIll = (float)((double)llllllllllllllllllIlIlIIlllllIll * Math.sin((double)llllllllllllllllllIlIlIIlllIllll * llllllllllllllllllIlIlIIllllIlIl));
         Exception llllllllllllllllllIlIlIIllllIIIl = (float)((double)llllllllllllllllllIlIlIIlllllIll * Math.cos((double)llllllllllllllllllIlIlIIlllIllll * llllllllllllllllllIlIlIIllllIlIl));
         GL11.glColor4f((float)llllllllllllllllllIlIlIIlllllIIl.getRed() / 255.0F, (float)llllllllllllllllllIlIlIIlllllIIl.getGreen() / 255.0F, (float)llllllllllllllllllIlIlIIlllllIIl.getBlue() / 255.0F, (float)llllllllllllllllllIlIlIIlllllIIl.getAlpha() / 255.0F);
         GL11.glVertex2f((float)llllllllllllllllllIlIlIIllllllll + llllllllllllllllllIlIlIIllllIIll, (float)llllllllllllllllllIlIlIIllllllIl + llllllllllllllllllIlIlIIllllIIIl);
      }

      GlStateManager.color(0.0F, 0.0F, 0.0F);
      GL11.glEnd();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
      GL11.glPopMatrix();
   }

   public static void drawLine(double llllllllllllllllllIlIIllllIIIlII, double llllllllllllllllllIlIIllllIIIIll, double llllllllllllllllllIlIIllllIIIIlI, double llllllllllllllllllIlIIllllIIIIIl, float llllllllllllllllllIlIIlllIlllIll) {
      GL11.glDisable(3553);
      GL11.glLineWidth(llllllllllllllllllIlIIlllIlllIll);
      GL11.glBegin(1);
      GL11.glVertex2d(llllllllllllllllllIlIIllllIIIlII, llllllllllllllllllIlIIllllIIIIll);
      GL11.glVertex2d(llllllllllllllllllIlIIllllIIIIlI, llllllllllllllllllIlIIllllIIIIIl);
      GL11.glEnd();
      GL11.glEnable(3553);
   }

   public static void disableGL2D() {
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glEnable(2929);
      GL11.glDisable(2848);
      GL11.glHint(3154, 4352);
      GL11.glHint(3155, 4352);
   }

   public static void drawBorderedRect(float llllllllllllllllllIllIIIlIlIllll, float llllllllllllllllllIllIIIlIlIlllI, float llllllllllllllllllIllIIIlIllIlII, float llllllllllllllllllIllIIIlIllIIll, float llllllllllllllllllIllIIIlIlIlIll, int llllllllllllllllllIllIIIlIllIIIl, int llllllllllllllllllIllIIIlIlIlIIl) {
      drawRect(llllllllllllllllllIllIIIlIlIllll, llllllllllllllllllIllIIIlIlIlllI, llllllllllllllllllIllIIIlIllIlII, llllllllllllllllllIllIIIlIllIIll, llllllllllllllllllIllIIIlIlIlIIl);
      drawBorder(llllllllllllllllllIllIIIlIlIllll, llllllllllllllllllIllIIIlIlIlllI, llllllllllllllllllIllIIIlIllIlII, llllllllllllllllllIllIIIlIllIIll, llllllllllllllllllIllIIIlIlIlIll, llllllllllllllllllIllIIIlIllIIIl);
   }

   public static void drawnewrect(float llllllllllllllllllIllIIIlllIIllI, float llllllllllllllllllIllIIIllllIIII, float llllllllllllllllllIllIIIlllIllll, float llllllllllllllllllIllIIIlllIIIll, int llllllllllllllllllIllIIIlllIIIlI) {
      float llllllllllllllllllIllIIIlllIllII;
      if (llllllllllllllllllIllIIIlllIIllI < llllllllllllllllllIllIIIlllIllll) {
         llllllllllllllllllIllIIIlllIllII = llllllllllllllllllIllIIIlllIIllI;
         llllllllllllllllllIllIIIlllIIllI = llllllllllllllllllIllIIIlllIllll;
         llllllllllllllllllIllIIIlllIllll = llllllllllllllllllIllIIIlllIllII;
      }

      if (llllllllllllllllllIllIIIllllIIII < llllllllllllllllllIllIIIlllIIIll) {
         llllllllllllllllllIllIIIlllIllII = llllllllllllllllllIllIIIllllIIII;
         llllllllllllllllllIllIIIllllIIII = llllllllllllllllllIllIIIlllIIIll;
         llllllllllllllllllIllIIIlllIIIll = llllllllllllllllllIllIIIlllIllII;
      }

      llllllllllllllllllIllIIIlllIllII = (float)(llllllllllllllllllIllIIIlllIIIlI >> 24 & 255) / 255.0F;
      float llllllllllllllllllIllIIIlllIlIll = (float)(llllllllllllllllllIllIIIlllIIIlI >> 16 & 255) / 255.0F;
      float llllllllllllllllllIllIIIlllIlIlI = (float)(llllllllllllllllllIllIIIlllIIIlI >> 8 & 255) / 255.0F;
      Exception llllllllllllllllllIllIIIllIllllI = (float)(llllllllllllllllllIllIIIlllIIIlI & 255) / 255.0F;
      int llllllllllllllllllIllIIIllIlllIl = Tessellator.getInstance();
      WorldRenderer llllllllllllllllllIllIIIlllIIlll = llllllllllllllllllIllIIIllIlllIl.getWorldRenderer();
      GlStateManager.enableBlend();
      GlStateManager.disableTexture2D();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.color(llllllllllllllllllIllIIIlllIlIll, llllllllllllllllllIllIIIlllIlIlI, llllllllllllllllllIllIIIllIllllI, llllllllllllllllllIllIIIlllIllII);
      llllllllllllllllllIllIIIlllIIlll.begin(7, DefaultVertexFormats.POSITION);
      llllllllllllllllllIllIIIlllIIlll.pos((double)llllllllllllllllllIllIIIlllIIllI, (double)llllllllllllllllllIllIIIlllIIIll, 0.0D).endVertex();
      llllllllllllllllllIllIIIlllIIlll.pos((double)llllllllllllllllllIllIIIlllIllll, (double)llllllllllllllllllIllIIIlllIIIll, 0.0D).endVertex();
      llllllllllllllllllIllIIIlllIIlll.pos((double)llllllllllllllllllIllIIIlllIllll, (double)llllllllllllllllllIllIIIllllIIII, 0.0D).endVertex();
      llllllllllllllllllIllIIIlllIIlll.pos((double)llllllllllllllllllIllIIIlllIIllI, (double)llllllllllllllllllIllIIIllllIIII, 0.0D).endVertex();
      llllllllllllllllllIllIIIllIlllIl.draw();
      GlStateManager.enableTexture2D();
      GlStateManager.disableBlend();
   }

   public static void glColor(int llllllllllllllllllIlIlIIlIlIIIll, int llllllllllllllllllIlIlIIlIlIIIIl, int llllllllllllllllllIlIlIIlIIllllI, int llllllllllllllllllIlIlIIlIIllIll) {
      GlStateManager.color((float)llllllllllllllllllIlIlIIlIlIIIll / 255.0F, (float)llllllllllllllllllIlIlIIlIlIIIIl / 255.0F, (float)llllllllllllllllllIlIlIIlIIllllI / 255.0F, (float)llllllllllllllllllIlIlIIlIIllIll / 255.0F);
   }

   public static void drawFilledBox(AxisAlignedBB llllllllllllllllllIlIllIIllIlIII) {
      float llllllllllllllllllIlIllIIllIIlll = Tessellator.getInstance();
      WorldRenderer llllllllllllllllllIlIllIIllIlIIl = llllllllllllllllllIlIllIIllIIlll.getWorldRenderer();
      llllllllllllllllllIlIllIIllIlIIl.begin(7, DefaultVertexFormats.POSITION);
      llllllllllllllllllIlIllIIllIlIIl.pos(llllllllllllllllllIlIllIIllIlIII.minX, llllllllllllllllllIlIllIIllIlIII.minY, llllllllllllllllllIlIllIIllIlIII.minZ).endVertex();
      llllllllllllllllllIlIllIIllIlIIl.pos(llllllllllllllllllIlIllIIllIlIII.minX, llllllllllllllllllIlIllIIllIlIII.maxY, llllllllllllllllllIlIllIIllIlIII.minZ).endVertex();
      llllllllllllllllllIlIllIIllIlIIl.pos(llllllllllllllllllIlIllIIllIlIII.maxX, llllllllllllllllllIlIllIIllIlIII.minY, llllllllllllllllllIlIllIIllIlIII.minZ).endVertex();
      llllllllllllllllllIlIllIIllIlIIl.pos(llllllllllllllllllIlIllIIllIlIII.maxX, llllllllllllllllllIlIllIIllIlIII.maxY, llllllllllllllllllIlIllIIllIlIII.minZ).endVertex();
      llllllllllllllllllIlIllIIllIlIIl.pos(llllllllllllllllllIlIllIIllIlIII.maxX, llllllllllllllllllIlIllIIllIlIII.minY, llllllllllllllllllIlIllIIllIlIII.maxZ).endVertex();
      llllllllllllllllllIlIllIIllIlIIl.pos(llllllllllllllllllIlIllIIllIlIII.maxX, llllllllllllllllllIlIllIIllIlIII.maxY, llllllllllllllllllIlIllIIllIlIII.maxZ).endVertex();
      llllllllllllllllllIlIllIIllIlIIl.pos(llllllllllllllllllIlIllIIllIlIII.minX, llllllllllllllllllIlIllIIllIlIII.minY, llllllllllllllllllIlIllIIllIlIII.maxZ).endVertex();
      llllllllllllllllllIlIllIIllIlIIl.pos(llllllllllllllllllIlIllIIllIlIII.minX, llllllllllllllllllIlIllIIllIlIII.maxY, llllllllllllllllllIlIllIIllIlIII.maxZ).endVertex();
      llllllllllllllllllIlIllIIllIIlll.draw();
      llllllllllllllllllIlIllIIllIlIIl.begin(7, DefaultVertexFormats.POSITION);
      llllllllllllllllllIlIllIIllIlIIl.pos(llllllllllllllllllIlIllIIllIlIII.maxX, llllllllllllllllllIlIllIIllIlIII.maxY, llllllllllllllllllIlIllIIllIlIII.minZ).endVertex();
      llllllllllllllllllIlIllIIllIlIIl.pos(llllllllllllllllllIlIllIIllIlIII.maxX, llllllllllllllllllIlIllIIllIlIII.minY, llllllllllllllllllIlIllIIllIlIII.minZ).endVertex();
      llllllllllllllllllIlIllIIllIlIIl.pos(llllllllllllllllllIlIllIIllIlIII.minX, llllllllllllllllllIlIllIIllIlIII.maxY, llllllllllllllllllIlIllIIllIlIII.minZ).endVertex();
      llllllllllllllllllIlIllIIllIlIIl.pos(llllllllllllllllllIlIllIIllIlIII.minX, llllllllllllllllllIlIllIIllIlIII.minY, llllllllllllllllllIlIllIIllIlIII.minZ).endVertex();
      llllllllllllllllllIlIllIIllIlIIl.pos(llllllllllllllllllIlIllIIllIlIII.minX, llllllllllllllllllIlIllIIllIlIII.maxY, llllllllllllllllllIlIllIIllIlIII.maxZ).endVertex();
      llllllllllllllllllIlIllIIllIlIIl.pos(llllllllllllllllllIlIllIIllIlIII.minX, llllllllllllllllllIlIllIIllIlIII.minY, llllllllllllllllllIlIllIIllIlIII.maxZ).endVertex();
      llllllllllllllllllIlIllIIllIlIIl.pos(llllllllllllllllllIlIllIIllIlIII.maxX, llllllllllllllllllIlIllIIllIlIII.maxY, llllllllllllllllllIlIllIIllIlIII.maxZ).endVertex();
      llllllllllllllllllIlIllIIllIlIIl.pos(llllllllllllllllllIlIllIIllIlIII.maxX, llllllllllllllllllIlIllIIllIlIII.minY, llllllllllllllllllIlIllIIllIlIII.maxZ).endVertex();
      llllllllllllllllllIlIllIIllIIlll.draw();
      llllllllllllllllllIlIllIIllIlIIl.begin(7, DefaultVertexFormats.POSITION);
      llllllllllllllllllIlIllIIllIlIIl.pos(llllllllllllllllllIlIllIIllIlIII.minX, llllllllllllllllllIlIllIIllIlIII.maxY, llllllllllllllllllIlIllIIllIlIII.minZ).endVertex();
      llllllllllllllllllIlIllIIllIlIIl.pos(llllllllllllllllllIlIllIIllIlIII.maxX, llllllllllllllllllIlIllIIllIlIII.maxY, llllllllllllllllllIlIllIIllIlIII.minZ).endVertex();
      llllllllllllllllllIlIllIIllIlIIl.pos(llllllllllllllllllIlIllIIllIlIII.maxX, llllllllllllllllllIlIllIIllIlIII.maxY, llllllllllllllllllIlIllIIllIlIII.maxZ).endVertex();
      llllllllllllllllllIlIllIIllIlIIl.pos(llllllllllllllllllIlIllIIllIlIII.minX, llllllllllllllllllIlIllIIllIlIII.maxY, llllllllllllllllllIlIllIIllIlIII.maxZ).endVertex();
      llllllllllllllllllIlIllIIllIlIIl.pos(llllllllllllllllllIlIllIIllIlIII.minX, llllllllllllllllllIlIllIIllIlIII.maxY, llllllllllllllllllIlIllIIllIlIII.minZ).endVertex();
      llllllllllllllllllIlIllIIllIlIIl.pos(llllllllllllllllllIlIllIIllIlIII.minX, llllllllllllllllllIlIllIIllIlIII.maxY, llllllllllllllllllIlIllIIllIlIII.maxZ).endVertex();
      llllllllllllllllllIlIllIIllIlIIl.pos(llllllllllllllllllIlIllIIllIlIII.maxX, llllllllllllllllllIlIllIIllIlIII.maxY, llllllllllllllllllIlIllIIllIlIII.maxZ).endVertex();
      llllllllllllllllllIlIllIIllIlIIl.pos(llllllllllllllllllIlIllIIllIlIII.maxX, llllllllllllllllllIlIllIIllIlIII.maxY, llllllllllllllllllIlIllIIllIlIII.minZ).endVertex();
      llllllllllllllllllIlIllIIllIIlll.draw();
      llllllllllllllllllIlIllIIllIlIIl.begin(7, DefaultVertexFormats.POSITION);
      llllllllllllllllllIlIllIIllIlIIl.pos(llllllllllllllllllIlIllIIllIlIII.minX, llllllllllllllllllIlIllIIllIlIII.minY, llllllllllllllllllIlIllIIllIlIII.minZ).endVertex();
      llllllllllllllllllIlIllIIllIlIIl.pos(llllllllllllllllllIlIllIIllIlIII.maxX, llllllllllllllllllIlIllIIllIlIII.minY, llllllllllllllllllIlIllIIllIlIII.minZ).endVertex();
      llllllllllllllllllIlIllIIllIlIIl.pos(llllllllllllllllllIlIllIIllIlIII.maxX, llllllllllllllllllIlIllIIllIlIII.minY, llllllllllllllllllIlIllIIllIlIII.maxZ).endVertex();
      llllllllllllllllllIlIllIIllIlIIl.pos(llllllllllllllllllIlIllIIllIlIII.minX, llllllllllllllllllIlIllIIllIlIII.minY, llllllllllllllllllIlIllIIllIlIII.maxZ).endVertex();
      llllllllllllllllllIlIllIIllIlIIl.pos(llllllllllllllllllIlIllIIllIlIII.minX, llllllllllllllllllIlIllIIllIlIII.minY, llllllllllllllllllIlIllIIllIlIII.minZ).endVertex();
      llllllllllllllllllIlIllIIllIlIIl.pos(llllllllllllllllllIlIllIIllIlIII.minX, llllllllllllllllllIlIllIIllIlIII.minY, llllllllllllllllllIlIllIIllIlIII.maxZ).endVertex();
      llllllllllllllllllIlIllIIllIlIIl.pos(llllllllllllllllllIlIllIIllIlIII.maxX, llllllllllllllllllIlIllIIllIlIII.minY, llllllllllllllllllIlIllIIllIlIII.maxZ).endVertex();
      llllllllllllllllllIlIllIIllIlIIl.pos(llllllllllllllllllIlIllIIllIlIII.maxX, llllllllllllllllllIlIllIIllIlIII.minY, llllllllllllllllllIlIllIIllIlIII.minZ).endVertex();
      llllllllllllllllllIlIllIIllIIlll.draw();
      llllllllllllllllllIlIllIIllIlIIl.begin(7, DefaultVertexFormats.POSITION);
      llllllllllllllllllIlIllIIllIlIIl.pos(llllllllllllllllllIlIllIIllIlIII.minX, llllllllllllllllllIlIllIIllIlIII.minY, llllllllllllllllllIlIllIIllIlIII.minZ).endVertex();
      llllllllllllllllllIlIllIIllIlIIl.pos(llllllllllllllllllIlIllIIllIlIII.minX, llllllllllllllllllIlIllIIllIlIII.maxY, llllllllllllllllllIlIllIIllIlIII.minZ).endVertex();
      llllllllllllllllllIlIllIIllIlIIl.pos(llllllllllllllllllIlIllIIllIlIII.minX, llllllllllllllllllIlIllIIllIlIII.minY, llllllllllllllllllIlIllIIllIlIII.maxZ).endVertex();
      llllllllllllllllllIlIllIIllIlIIl.pos(llllllllllllllllllIlIllIIllIlIII.minX, llllllllllllllllllIlIllIIllIlIII.maxY, llllllllllllllllllIlIllIIllIlIII.maxZ).endVertex();
      llllllllllllllllllIlIllIIllIlIIl.pos(llllllllllllllllllIlIllIIllIlIII.maxX, llllllllllllllllllIlIllIIllIlIII.minY, llllllllllllllllllIlIllIIllIlIII.maxZ).endVertex();
      llllllllllllllllllIlIllIIllIlIIl.pos(llllllllllllllllllIlIllIIllIlIII.maxX, llllllllllllllllllIlIllIIllIlIII.maxY, llllllllllllllllllIlIllIIllIlIII.maxZ).endVertex();
      llllllllllllllllllIlIllIIllIlIIl.pos(llllllllllllllllllIlIllIIllIlIII.maxX, llllllllllllllllllIlIllIIllIlIII.minY, llllllllllllllllllIlIllIIllIlIII.minZ).endVertex();
      llllllllllllllllllIlIllIIllIlIIl.pos(llllllllllllllllllIlIllIIllIlIII.maxX, llllllllllllllllllIlIllIIllIlIII.maxY, llllllllllllllllllIlIllIIllIlIII.minZ).endVertex();
      llllllllllllllllllIlIllIIllIIlll.draw();
      llllllllllllllllllIlIllIIllIlIIl.begin(7, DefaultVertexFormats.POSITION);
      llllllllllllllllllIlIllIIllIlIIl.pos(llllllllllllllllllIlIllIIllIlIII.minX, llllllllllllllllllIlIllIIllIlIII.maxY, llllllllllllllllllIlIllIIllIlIII.maxZ).endVertex();
      llllllllllllllllllIlIllIIllIlIIl.pos(llllllllllllllllllIlIllIIllIlIII.minX, llllllllllllllllllIlIllIIllIlIII.minY, llllllllllllllllllIlIllIIllIlIII.maxZ).endVertex();
      llllllllllllllllllIlIllIIllIlIIl.pos(llllllllllllllllllIlIllIIllIlIII.minX, llllllllllllllllllIlIllIIllIlIII.maxY, llllllllllllllllllIlIllIIllIlIII.minZ).endVertex();
      llllllllllllllllllIlIllIIllIlIIl.pos(llllllllllllllllllIlIllIIllIlIII.minX, llllllllllllllllllIlIllIIllIlIII.minY, llllllllllllllllllIlIllIIllIlIII.minZ).endVertex();
      llllllllllllllllllIlIllIIllIlIIl.pos(llllllllllllllllllIlIllIIllIlIII.maxX, llllllllllllllllllIlIllIIllIlIII.maxY, llllllllllllllllllIlIllIIllIlIII.minZ).endVertex();
      llllllllllllllllllIlIllIIllIlIIl.pos(llllllllllllllllllIlIllIIllIlIII.maxX, llllllllllllllllllIlIllIIllIlIII.minY, llllllllllllllllllIlIllIIllIlIII.minZ).endVertex();
      llllllllllllllllllIlIllIIllIlIIl.pos(llllllllllllllllllIlIllIIllIlIII.maxX, llllllllllllllllllIlIllIIllIlIII.maxY, llllllllllllllllllIlIllIIllIlIII.maxZ).endVertex();
      llllllllllllllllllIlIllIIllIlIIl.pos(llllllllllllllllllIlIllIIllIlIII.maxX, llllllllllllllllllIlIllIIllIlIII.minY, llllllllllllllllllIlIllIIllIlIII.maxZ).endVertex();
      llllllllllllllllllIlIllIIllIIlll.draw();
   }

   public static void rectangleBordered(double llllllllllllllllllIlIlllIllllIIl, double llllllllllllllllllIlIlllIllllIII, double llllllllllllllllllIlIlllIlllIlll, double llllllllllllllllllIlIlllIlllIllI, double llllllllllllllllllIlIlllIlllIlIl, int llllllllllllllllllIlIlllIlllIlII, int llllllllllllllllllIlIlllIllllIlI) {
      rectangle(llllllllllllllllllIlIlllIllllIIl + llllllllllllllllllIlIlllIlllIlIl, llllllllllllllllllIlIlllIllllIII + llllllllllllllllllIlIlllIlllIlIl, llllllllllllllllllIlIlllIlllIlll - llllllllllllllllllIlIlllIlllIlIl, llllllllllllllllllIlIlllIlllIllI - llllllllllllllllllIlIlllIlllIlIl, llllllllllllllllllIlIlllIlllIlII);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      rectangle(llllllllllllllllllIlIlllIllllIIl + llllllllllllllllllIlIlllIlllIlIl, llllllllllllllllllIlIlllIllllIII, llllllllllllllllllIlIlllIlllIlll - llllllllllllllllllIlIlllIlllIlIl, llllllllllllllllllIlIlllIllllIII + llllllllllllllllllIlIlllIlllIlIl, llllllllllllllllllIlIlllIllllIlI);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      rectangle(llllllllllllllllllIlIlllIllllIIl, llllllllllllllllllIlIlllIllllIII, llllllllllllllllllIlIlllIllllIIl + llllllllllllllllllIlIlllIlllIlIl, llllllllllllllllllIlIlllIlllIllI, llllllllllllllllllIlIlllIllllIlI);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      rectangle(llllllllllllllllllIlIlllIlllIlll - llllllllllllllllllIlIlllIlllIlIl, llllllllllllllllllIlIlllIllllIII, llllllllllllllllllIlIlllIlllIlll, llllllllllllllllllIlIlllIlllIllI, llllllllllllllllllIlIlllIllllIlI);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      rectangle(llllllllllllllllllIlIlllIllllIIl + llllllllllllllllllIlIlllIlllIlIl, llllllllllllllllllIlIlllIlllIllI - llllllllllllllllllIlIlllIlllIlIl, llllllllllllllllllIlIlllIlllIlll - llllllllllllllllllIlIlllIlllIlIl, llllllllllllllllllIlIlllIlllIllI, llllllllllllllllllIlIlllIllllIlI);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static void resetCaps() {
      glCapMap.forEach(RenderUtils::setGlState);
   }

   public static void renderNameTag(String llllllllllllllllllIlIIllllIlIlIl, double llllllllllllllllllIlIIllllIlIlII, double llllllllllllllllllIlIIllllIlIIll, double llllllllllllllllllIlIIllllIIllII) {
      char llllllllllllllllllIlIIllllIIlIll = mc.getRenderManager();
      GL11.glPushMatrix();
      GL11.glTranslated(llllllllllllllllllIlIIllllIlIlII - llllllllllllllllllIlIIllllIIlIll.renderPosX, llllllllllllllllllIlIIllllIlIIll - llllllllllllllllllIlIIllllIIlIll.renderPosY, llllllllllllllllllIlIIllllIIllII - llllllllllllllllllIlIIllllIIlIll.renderPosZ);
      GL11.glNormal3f(0.0F, 1.0F, 0.0F);
      GL11.glRotatef(-mc.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(mc.getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
      GL11.glScalef(-0.05F, -0.05F, 0.05F);
      setGlCap(2896, false);
      setGlCap(2929, false);
      setGlCap(3042, true);
      GL11.glBlendFunc(770, 771);
      float llllllllllllllllllIlIIllllIIlIlI = Fonts.font35.getStringWidth(llllllllllllllllllIlIIllllIlIlIl) / 2;
      Gui.drawRect(-llllllllllllllllllIlIIllllIIlIlI - 1, -1, llllllllllllllllllIlIIllllIIlIlI + 1, Fonts.font35.FONT_HEIGHT, Integer.MIN_VALUE);
      Fonts.font35.drawString(llllllllllllllllllIlIIllllIlIlIl, (float)(-llllllllllllllllllIlIIllllIIlIlI), 1.5F, Color.WHITE.getRGB(), true);
      boolean var10001 = false;
      resetCaps();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glPopMatrix();
   }

   public static void cylinder(Entity llllllllllllllllllIllIIIIlllllIl, double llllllllllllllllllIllIIIIlllIlIl, double llllllllllllllllllIllIIIIlllIlII, double llllllllllllllllllIllIIIIllllIlI, double llllllllllllllllllIllIIIIllllIIl, int llllllllllllllllllIllIIIIllllIII, int llllllllllllllllllIllIIIIlllIlll) {
      GL11.glPushMatrix();
      GL11.glDisable(2896);
      GL11.glDisable(3553);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(2929);
      GL11.glEnable(2848);
      GL11.glDepthMask(true);
      GlStateManager.translate(llllllllllllllllllIllIIIIlllIlIl, llllllllllllllllllIllIIIIlllIlII, llllllllllllllllllIllIIIIllllIlI);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 0.75F);
      GlStateManager.rotate(180.0F, 90.0F, 0.0F, 2.0F);
      GlStateManager.rotate(180.0F, 0.0F, 90.0F, 90.0F);
      GlStateManager.resetColor();
      glColor(llllllllllllllllllIllIIIIlllIlll);
      GL11.glBegin(2);
      float llllllllllllllllllIllIIIIllIllll = new Cylinder();
      llllllllllllllllllIllIIIIllIllll.setDrawStyle(100011);
      llllllllllllllllllIllIIIIllIllll.draw((float)(llllllllllllllllllIllIIIIllllIIl - 0.5D), (float)(llllllllllllllllllIllIIIIllllIIl - 0.5D), 0.0F, llllllllllllllllllIllIIIIllllIII, 0);
      GL11.glDepthMask(true);
      GL11.glDisable(2848);
      GL11.glEnable(2929);
      GL11.glDisable(3042);
      GL11.glEnable(2896);
      GL11.glEnable(3553);
      GL11.glPopMatrix();
   }

   public static void drawESPCircle(float llllllllllllllllllIlIlIllIlIIlll, float llllllllllllllllllIlIlIllIlIIllI, float llllllllllllllllllIlIlIllIIllIlI, int llllllllllllllllllIlIlIllIlIIlII, int llllllllllllllllllIlIlIllIlIIIll) {
      GL11.glPushMatrix();
      llllllllllllllllllIlIlIllIlIIlll *= 2.0F;
      llllllllllllllllllIlIlIllIlIIllI *= 2.0F;
      byte llllllllllllllllllIlIlIllIIlIlll = (float)(6.2831852D / (double)llllllllllllllllllIlIlIllIlIIlII);
      float llllllllllllllllllIlIlIllIlIIIIl = (float)Math.cos((double)llllllllllllllllllIlIlIllIIlIlll);
      float llllllllllllllllllIlIlIllIlIIIII = (float)Math.sin((double)llllllllllllllllllIlIlIllIIlIlll);
      boolean llllllllllllllllllIlIlIllIIlIIll = llllllllllllllllllIlIlIllIIllIlI *= 2.0F;
      float llllllllllllllllllIlIlIllIIllllI = 0.0F;
      enableGL2D();
      GL11.glScalef(0.5F, 0.5F, 0.5F);
      GlStateManager.color(0.0F, 0.0F, 0.0F);
      GlStateManager.resetColor();
      glColor(llllllllllllllllllIlIlIllIlIIIll);
      GL11.glBegin(2);

      for(int llllllllllllllllllIlIlIllIIlllIl = 0; llllllllllllllllllIlIlIllIIlllIl < llllllllllllllllllIlIlIllIlIIlII; ++llllllllllllllllllIlIlIllIIlllIl) {
         GL11.glVertex2f(llllllllllllllllllIlIlIllIIlIIll + llllllllllllllllllIlIlIllIlIIlll, llllllllllllllllllIlIlIllIIllllI + llllllllllllllllllIlIlIllIlIIllI);
         float llllllllllllllllllIlIlIllIlIlIII = llllllllllllllllllIlIlIllIIlIIll;
         llllllllllllllllllIlIlIllIIlIIll = llllllllllllllllllIlIlIllIlIIIIl * llllllllllllllllllIlIlIllIIlIIll - llllllllllllllllllIlIlIllIlIIIII * llllllllllllllllllIlIlIllIIllllI;
         llllllllllllllllllIlIlIllIIllllI = llllllllllllllllllIlIlIllIlIIIII * llllllllllllllllllIlIlIllIlIlIII + llllllllllllllllllIlIlIllIlIIIIl * llllllllllllllllllIlIlIllIIllllI;
      }

      Colors.getColor(-1);
      boolean var10001 = false;
      GL11.glEnd();
      GL11.glScalef(2.0F, 2.0F, 2.0F);
      disableGL2D();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glPopMatrix();
   }

   public static void drawRect(float llllllllllllllllllIlIllIIlIlllIl, float llllllllllllllllllIlIllIIlIlIllI, float llllllllllllllllllIlIllIIlIllIll, float llllllllllllllllllIlIllIIlIllIlI, int llllllllllllllllllIlIllIIlIlIIlI) {
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glPushMatrix();
      glColor(llllllllllllllllllIlIllIIlIlIIlI);
      GL11.glBegin(7);
      GL11.glVertex2d((double)llllllllllllllllllIlIllIIlIllIll, (double)llllllllllllllllllIlIllIIlIlIllI);
      GL11.glVertex2d((double)llllllllllllllllllIlIllIIlIlllIl, (double)llllllllllllllllllIlIllIIlIlIllI);
      GL11.glVertex2d((double)llllllllllllllllllIlIllIIlIlllIl, (double)llllllllllllllllllIlIllIIlIllIlI);
      GL11.glVertex2d((double)llllllllllllllllllIlIllIIlIllIll, (double)llllllllllllllllllIlIllIIlIllIlI);
      GL11.glEnd();
      GL11.glPopMatrix();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
   }

   private static boolean isInViewFrustrum(AxisAlignedBB llllllllllllllllllIllIIlIIIIIIlI) {
      char llllllllllllllllllIllIIIllllllll = mc.getRenderViewEntity();
      frustrum.setPosition(llllllllllllllllllIllIIIllllllll.posX, llllllllllllllllllIllIIIllllllll.posY, llllllllllllllllllIllIIIllllllll.posZ);
      return frustrum.isBoundingBoxInFrustum(llllllllllllllllllIllIIlIIIIIIlI);
   }

   public static void disableGlCap(int llllllllllllllllllIlIIlllIIlIlll) {
      setGlCap(llllllllllllllllllIlIIlllIIlIlll, true);
   }

   public static void drawLoadingCircle(float llllllllllllllllllIlIlIlIlIllIII, float llllllllllllllllllIlIlIlIlIlIlIl) {
      for(int llllllllllllllllllIlIlIlIlIllIIl = 0; llllllllllllllllllIlIlIlIlIllIIl < 4; ++llllllllllllllllllIlIlIlIlIllIIl) {
         int llllllllllllllllllIlIlIlIlIlIIll = (int)(System.nanoTime() / 5000000L * (long)llllllllllllllllllIlIlIlIlIllIIl % 360L);
         drawCircle(llllllllllllllllllIlIlIlIlIllIII, llllllllllllllllllIlIlIlIlIlIlIl, (float)(llllllllllllllllllIlIlIlIlIllIIl * 10), llllllllllllllllllIlIlIlIlIlIIll - 180, llllllllllllllllllIlIlIlIlIlIIll);
      }

   }

   public static double getEntityRenderZ(Entity llllllllllllllllllIllIIIlIlllllI) {
      return llllllllllllllllllIllIIIlIlllllI.lastTickPosZ + (llllllllllllllllllIllIIIlIlllllI.posZ - llllllllllllllllllIllIIIlIlllllI.lastTickPosZ) * (double)Minecraft.getMinecraft().timer.renderPartialTicks - mc.getRenderManager().renderPosZ;
   }

   public static void drawPlatform(double llllllllllllllllllIlIlllIIlIllIl, Color llllllllllllllllllIlIlllIIlIIllI, double llllllllllllllllllIlIlllIIlIlIll) {
      RenderManager llllllllllllllllllIlIlllIIlIlIlI = mc.getRenderManager();
      Exception llllllllllllllllllIlIlllIIlIIIlI = llllllllllllllllllIlIlllIIlIllIl - llllllllllllllllllIlIlllIIlIlIlI.renderPosY;
      drawAxisAlignedBB(new AxisAlignedBB(llllllllllllllllllIlIlllIIlIlIll, llllllllllllllllllIlIlllIIlIIIlI + 0.02D, llllllllllllllllllIlIlllIIlIlIll, -llllllllllllllllllIlIlllIIlIlIll, llllllllllllllllllIlIlllIIlIIIlI, -llllllllllllllllllIlIlllIIlIlIll), llllllllllllllllllIlIlllIIlIIllI);
   }

   public static void drawBorder(float llllllllllllllllllIllIIIlIIlIIII, float llllllllllllllllllIllIIIlIIIlIIl, float llllllllllllllllllIllIIIlIIIlIII, float llllllllllllllllllIllIIIlIIIllIl, float llllllllllllllllllIllIIIlIIIllII, int llllllllllllllllllIllIIIlIIIIlIl) {
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      glColor(llllllllllllllllllIllIIIlIIIIlIl);
      GL11.glLineWidth(llllllllllllllllllIllIIIlIIIllII);
      GL11.glBegin(2);
      GL11.glVertex2d((double)llllllllllllllllllIllIIIlIIIlIII, (double)llllllllllllllllllIllIIIlIIIlIIl);
      GL11.glVertex2d((double)llllllllllllllllllIllIIIlIIlIIII, (double)llllllllllllllllllIllIIIlIIIlIIl);
      GL11.glVertex2d((double)llllllllllllllllllIllIIIlIIlIIII, (double)llllllllllllllllllIllIIIlIIIllIl);
      GL11.glVertex2d((double)llllllllllllllllllIllIIIlIIIlIII, (double)llllllllllllllllllIllIIIlIIIllIl);
      GL11.glEnd();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
   }
}
