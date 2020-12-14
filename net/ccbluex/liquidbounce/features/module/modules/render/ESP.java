//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.shader.FramebufferShader;
import net.ccbluex.liquidbounce.utils.render.shader.shaders.GlowShader;
import net.ccbluex.liquidbounce.utils.render.shader.shaders.OutlineShader;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Timer;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

@ModuleInfo(
   name = "ESP",
   description = "Allows you to see targets through walls.",
   category = ModuleCategory.RENDER
)
public class ESP extends Module {
   // $FF: synthetic field
   private final FloatValue shaderOutlineRadius = new FloatValue("ShaderOutline-Radius", 1.35F, 1.0F, 2.0F);
   // $FF: synthetic field
   public final FloatValue outlineWidth = new FloatValue("Outline-Width", 3.0F, 0.5F, 5.0F);
   // $FF: synthetic field
   private final IntegerValue colorBlueValue = new IntegerValue("B", 255, 0, 255);
   // $FF: synthetic field
   private final IntegerValue shadowGreenValue = new IntegerValue("ShadowG", 255, 0, 255);
   // $FF: synthetic field
   private final BoolValue health = new BoolValue("NewHealth", true);
   // $FF: synthetic field
   final NameTags nameTags;
   // $FF: synthetic field
   private final BoolValue rect = new BoolValue("NewRect", true);
   // $FF: synthetic field
   public final FloatValue wireframeWidth = new FloatValue("WireFrame-Width", 2.0F, 0.5F, 5.0F);
   // $FF: synthetic field
   private final IntegerValue shadowBlueValue = new IntegerValue("ShadowB", 255, 0, 255);
   // $FF: synthetic field
   private final BoolValue colorRainbow = new BoolValue("Rainbow", false);
   // $FF: synthetic field
   private final IntegerValue colorRedValue = new IntegerValue("R", 255, 0, 255);
   // $FF: synthetic field
   private final FloatValue width2d = new FloatValue("NewWidth2D", 0.5F, 0.0F, 0.5F);
   // $FF: synthetic field
   private final IntegerValue colorGreenValue = new IntegerValue("G", 255, 0, 255);
   // $FF: synthetic field
   private FloatValue range = new FloatValue("ShadowRange", 1.0F, 0.0F, 5.0F);
   // $FF: synthetic field
   public static boolean renderNameTags = true;
   // $FF: synthetic field
   private final BoolValue colorTeam = new BoolValue("Team", false);
   // $FF: synthetic field
   private final FloatValue shaderGlowRadius = new FloatValue("ShaderGlow-Radius", 2.3F, 2.0F, 3.0F);
   // $FF: synthetic field
   private List<Vec3> positions;
   // $FF: synthetic field
   private final BoolValue armr = new BoolValue("NewArmor", true);
   // $FF: synthetic field
   private final IntegerValue shadowRedValue = new IntegerValue("ShadowR", 255, 0, 255);
   // $FF: synthetic field
   private final BoolValue outline = new BoolValue("NewOutLine", true);
   // $FF: synthetic field
   public final ListValue modeValue = new ListValue("Mode", new String[]{"Box", "New2D", "NewBox", "OtherBox", "WireFrame", "2D", "Outline", "ShaderOutline", "ShaderGlow", "Shadow"}, "Box");

   public Color shadowgetColor(Entity llllllllllllllllllllIllIlllllIlI) {
      if (llllllllllllllllllllIllIlllllIlI instanceof EntityLivingBase) {
         int llllllllllllllllllllIllIllllIlll = (EntityLivingBase)llllllllllllllllllllIllIlllllIlI;
         if (llllllllllllllllllllIllIllllIlll.hurtTime > 0) {
            return Color.RED;
         }

         if (EntityUtils.isFriend(llllllllllllllllllllIllIllllIlll)) {
            return Color.BLUE;
         }

         if ((Boolean)llllllllllllllllllllIllIlllllIIl.colorTeam.get()) {
            char[] llllllllllllllllllllIllIllllllll = llllllllllllllllllllIllIllllIlll.getDisplayName().getFormattedText().toCharArray();
            int llllllllllllllllllllIllIlllllllI = Integer.MAX_VALUE;
            String llllllllllllllllllllIllIllllllIl = "0123456789abcdef";

            for(int llllllllllllllllllllIlllIIIIIIII = 0; llllllllllllllllllllIlllIIIIIIII < llllllllllllllllllllIllIllllllll.length; ++llllllllllllllllllllIlllIIIIIIII) {
               if (llllllllllllllllllllIllIllllllll[llllllllllllllllllllIlllIIIIIIII] == 167 && llllllllllllllllllllIlllIIIIIIII + 1 < llllllllllllllllllllIllIllllllll.length) {
                  float llllllllllllllllllllIllIllllIIlI = "0123456789abcdef".indexOf(llllllllllllllllllllIllIllllllll[llllllllllllllllllllIlllIIIIIIII + 1]);
                  if (llllllllllllllllllllIllIllllIIlI != -1) {
                     llllllllllllllllllllIllIlllllllI = ColorUtils.hexColors[llllllllllllllllllllIllIllllIIlI];
                     break;
                  }
               }
            }

            return new Color(llllllllllllllllllllIllIlllllllI);
         }
      }

      return (Boolean)llllllllllllllllllllIllIlllllIIl.colorRainbow.get() ? ColorUtils.rainbow() : new Color((Integer)llllllllllllllllllllIllIlllllIIl.shadowRedValue.get(), (Integer)llllllllllllllllllllIllIlllllIIl.shadowGreenValue.get(), (Integer)llllllllllllllllllllIllIlllllIIl.shadowBlueValue.get());
   }

   public ESP() {
      lllllllllllllllllllllIIIIIIIIlIl.nameTags = (NameTags)LiquidBounce.moduleManager.getModule(NameTags.class);
      lllllllllllllllllllllIIIIIIIIlIl.positions = new ArrayList();
   }

   @EventTarget
   public void onRender2D(Render2DEvent llllllllllllllllllllIlllIIlIIIlI) {
      int llllllllllllllllllllIlllIIIlllII = ((String)llllllllllllllllllllIlllIIlIIIll.modeValue.get()).toLowerCase();
      if (!llllllllllllllllllllIlllIIIlllII.equalsIgnoreCase("shaderoutline") && !llllllllllllllllllllIlllIIIlllII.equalsIgnoreCase("shaderglow")) {
         Iterator llllllllllllllllllllIlllIIlIIlIl;
         Entity llllllllllllllllllllIlllIIIlIlll;
         int llllllllllllllllllllIlllIIIlIlIl;
         int llllllllllllllllllllIlllIIIlIlII;
         int llllllllllllllllllllIlllIIIlIIlI;
         int llllllllllllllllllllIlllIIlIllII;
         Iterator llllllllllllllllllllIlllIIIlIIII;
         boolean llllllllllllllllllllIlllIIlIlIlI;
         Vec3 llllllllllllllllllllIlllIIllIIlI;
         ScaledResolution llllllllllllllllllllIlllIIlIlIII;
         double llllllllllllllllllllIlllIIlIIllI;
         if (llllllllllllllllllllIlllIIIlllII.equalsIgnoreCase("newbox")) {
            GlStateManager.pushMatrix();
            GL11.glDisable(2929);
            llllllllllllllllllllIlllIIlIlIII = new ScaledResolution(mc);
            llllllllllllllllllllIlllIIlIIllI = (double)llllllllllllllllllllIlllIIlIlIII.getScaleFactor() / Math.pow((double)llllllllllllllllllllIlllIIlIlIII.getScaleFactor(), 2.0D);
            GlStateManager.scale(llllllllllllllllllllIlllIIlIIllI, llllllllllllllllllllIlllIIlIIllI, llllllllllllllllllllIlllIIlIIllI);
            llllllllllllllllllllIlllIIlIIlIl = mc.theWorld.loadedEntityList.iterator();

            while(true) {
               do {
                  do {
                     do {
                        if (!llllllllllllllllllllIlllIIlIIlIl.hasNext()) {
                           GL11.glEnable(2929);
                           GlStateManager.popMatrix();
                           return;
                        }

                        llllllllllllllllllllIlllIIIlIlll = (Entity)llllllllllllllllllllIlllIIlIIlIl.next();
                     } while(llllllllllllllllllllIlllIIIlIlll == null);
                  } while(llllllllllllllllllllIlllIIIlIlll == mc.thePlayer);
               } while(!EntityUtils.isSelected(llllllllllllllllllllIlllIIIlIlll, false));

               llllllllllllllllllllIlllIIlIIIll.updatePositions(llllllllllllllllllllIlllIIIlIlll);
               llllllllllllllllllllIlllIIIlIlIl = Integer.MAX_VALUE;
               llllllllllllllllllllIlllIIIlIlII = Integer.MIN_VALUE;
               llllllllllllllllllllIlllIIIlIIlI = Integer.MIN_VALUE;
               llllllllllllllllllllIlllIIlIllII = Integer.MAX_VALUE;
               llllllllllllllllllllIlllIIIlIIII = llllllllllllllllllllIlllIIlIIIll.positions.iterator();
               llllllllllllllllllllIlllIIlIlIlI = false;

               while(llllllllllllllllllllIlllIIIlIIII.hasNext()) {
                  llllllllllllllllllllIlllIIllIIlI = RenderUtils.WorldToScreen((Vec3)llllllllllllllllllllIlllIIIlIIII.next());
                  if (llllllllllllllllllllIlllIIllIIlI != null && llllllllllllllllllllIlllIIllIIlI.zCoord >= 0.0D && llllllllllllllllllllIlllIIllIIlI.zCoord < 1.0D) {
                     llllllllllllllllllllIlllIIIlIlIl = (int)Math.min(llllllllllllllllllllIlllIIllIIlI.xCoord, (double)llllllllllllllllllllIlllIIIlIlIl);
                     llllllllllllllllllllIlllIIIlIlII = (int)Math.max(llllllllllllllllllllIlllIIllIIlI.xCoord, (double)llllllllllllllllllllIlllIIIlIlII);
                     llllllllllllllllllllIlllIIIlIIlI = (int)Math.max(llllllllllllllllllllIlllIIllIIlI.yCoord, (double)llllllllllllllllllllIlllIIIlIIlI);
                     llllllllllllllllllllIlllIIlIllII = (int)Math.min(llllllllllllllllllllIlllIIllIIlI.yCoord, (double)llllllllllllllllllllIlllIIlIllII);
                     llllllllllllllllllllIlllIIlIlIlI = true;
                  }
               }

               if (llllllllllllllllllllIlllIIlIlIlI) {
                  RenderUtils.drawnewrect(0.0F, 0.0F, 0.0F, 0.0F, 0);
                  if ((Boolean)llllllllllllllllllllIlllIIlIIIll.health.get()) {
                     llllllllllllllllllllIlllIIlIIIll.drawHealth((EntityLivingBase)llllllllllllllllllllIlllIIIlIlll, (float)llllllllllllllllllllIlllIIIlIlIl, (float)llllllllllllllllllllIlllIIlIllII, (float)llllllllllllllllllllIlllIIIlIlII, (float)llllllllllllllllllllIlllIIIlIIlI);
                  }

                  if ((Boolean)llllllllllllllllllllIlllIIlIIIll.armr.get()) {
                     llllllllllllllllllllIlllIIlIIIll.drawArmor((EntityLivingBase)llllllllllllllllllllIlllIIIlIlll, (float)llllllllllllllllllllIlllIIIlIlIl, (float)llllllllllllllllllllIlllIIlIllII, (float)llllllllllllllllllllIlllIIIlIlII, (float)llllllllllllllllllllIlllIIIlIIlI);
                  }

                  if ((Boolean)llllllllllllllllllllIlllIIlIIIll.rect.get()) {
                     llllllllllllllllllllIlllIIlIIIll.drawnew(llllllllllllllllllllIlllIIIlIlll, llllllllllllllllllllIlllIIIlIlIl, llllllllllllllllllllIlllIIlIllII, llllllllllllllllllllIlllIIIlIlII, llllllllllllllllllllIlllIIIlIIlI);
                  }
               }
            }
         } else if (llllllllllllllllllllIlllIIIlllII.equalsIgnoreCase("new2d")) {
            GlStateManager.pushMatrix();
            GL11.glDisable(2929);
            llllllllllllllllllllIlllIIlIlIII = new ScaledResolution(mc);
            llllllllllllllllllllIlllIIlIIllI = (double)llllllllllllllllllllIlllIIlIlIII.getScaleFactor() / Math.pow((double)llllllllllllllllllllIlllIIlIlIII.getScaleFactor(), 2.0D);
            GlStateManager.scale(llllllllllllllllllllIlllIIlIIllI, llllllllllllllllllllIlllIIlIIllI, llllllllllllllllllllIlllIIlIIllI);
            llllllllllllllllllllIlllIIlIIlIl = mc.theWorld.loadedEntityList.iterator();

            while(true) {
               do {
                  do {
                     do {
                        if (!llllllllllllllllllllIlllIIlIIlIl.hasNext()) {
                           GL11.glEnable(2929);
                           GlStateManager.popMatrix();
                           return;
                        }

                        llllllllllllllllllllIlllIIIlIlll = (Entity)llllllllllllllllllllIlllIIlIIlIl.next();
                     } while(llllllllllllllllllllIlllIIIlIlll == null);
                  } while(llllllllllllllllllllIlllIIIlIlll == mc.thePlayer);
               } while(!EntityUtils.isSelected(llllllllllllllllllllIlllIIIlIlll, false));

               llllllllllllllllllllIlllIIlIIIll.updatePositions(llllllllllllllllllllIlllIIIlIlll);
               llllllllllllllllllllIlllIIIlIlIl = Integer.MAX_VALUE;
               llllllllllllllllllllIlllIIIlIlII = Integer.MIN_VALUE;
               llllllllllllllllllllIlllIIIlIIlI = Integer.MIN_VALUE;
               llllllllllllllllllllIlllIIlIllII = Integer.MAX_VALUE;
               llllllllllllllllllllIlllIIIlIIII = llllllllllllllllllllIlllIIlIIIll.positions.iterator();
               llllllllllllllllllllIlllIIlIlIlI = false;

               while(llllllllllllllllllllIlllIIIlIIII.hasNext()) {
                  llllllllllllllllllllIlllIIllIIlI = RenderUtils.WorldToScreen((Vec3)llllllllllllllllllllIlllIIIlIIII.next());
                  if (llllllllllllllllllllIlllIIllIIlI != null && llllllllllllllllllllIlllIIllIIlI.zCoord >= 0.0D && llllllllllllllllllllIlllIIllIIlI.zCoord < 1.0D) {
                     llllllllllllllllllllIlllIIIlIlIl = (int)Math.min(llllllllllllllllllllIlllIIllIIlI.xCoord, (double)llllllllllllllllllllIlllIIIlIlIl);
                     llllllllllllllllllllIlllIIIlIlII = (int)Math.max(llllllllllllllllllllIlllIIllIIlI.xCoord, (double)llllllllllllllllllllIlllIIIlIlII);
                     llllllllllllllllllllIlllIIIlIIlI = (int)Math.max(llllllllllllllllllllIlllIIllIIlI.yCoord, (double)llllllllllllllllllllIlllIIIlIIlI);
                     llllllllllllllllllllIlllIIlIllII = (int)Math.min(llllllllllllllllllllIlllIIllIIlI.yCoord, (double)llllllllllllllllllllIlllIIlIllII);
                     llllllllllllllllllllIlllIIlIlIlI = true;
                  }
               }

               if (llllllllllllllllllllIlllIIlIlIlI) {
                  RenderUtils.drawnewrect(0.0F, 0.0F, 0.0F, 0.0F, 0);
                  if ((Boolean)llllllllllllllllllllIlllIIlIIIll.health.get()) {
                     llllllllllllllllllllIlllIIlIIIll.drawHealth((EntityLivingBase)llllllllllllllllllllIlllIIIlIlll, (float)llllllllllllllllllllIlllIIIlIlIl, (float)llllllllllllllllllllIlllIIlIllII, (float)llllllllllllllllllllIlllIIIlIlII, (float)llllllllllllllllllllIlllIIIlIIlI);
                  }

                  if ((Boolean)llllllllllllllllllllIlllIIlIIIll.armr.get()) {
                     llllllllllllllllllllIlllIIlIIIll.drawArmor((EntityLivingBase)llllllllllllllllllllIlllIIIlIlll, (float)llllllllllllllllllllIlllIIIlIlIl, (float)llllllllllllllllllllIlllIIlIllII, (float)llllllllllllllllllllIlllIIIlIlII, (float)llllllllllllllllllllIlllIIIlIIlI);
                  }

                  if ((Boolean)llllllllllllllllllllIlllIIlIIIll.rect.get()) {
                     llllllllllllllllllllIlllIIlIIIll.drawnew(llllllllllllllllllllIlllIIIlIlll, llllllllllllllllllllIlllIIIlIlIl, llllllllllllllllllllIlllIIlIllII, llllllllllllllllllllIlllIIIlIlII, llllllllllllllllllllIlllIIIlIIlI);
                  }
               }
            }
         }
      } else {
         FramebufferShader llllllllllllllllllllIlllIlIIIIIl = llllllllllllllllllllIlllIIIlllII.equalsIgnoreCase("shaderoutline") ? OutlineShader.OUTLINE_SHADER : (llllllllllllllllllllIlllIIIlllII.equalsIgnoreCase("shaderglow") ? GlowShader.GLOW_SHADER : null);
         if (llllllllllllllllllllIlllIlIIIIIl == null) {
            return;
         }

         ((FramebufferShader)llllllllllllllllllllIlllIlIIIIIl).startDraw(llllllllllllllllllllIlllIIlIIIlI.getPartialTicks());
         renderNameTags = false;

         try {
            Iterator llllllllllllllllllllIlllIIIllIlI = mc.theWorld.loadedEntityList.iterator();

            while(llllllllllllllllllllIlllIIIllIlI.hasNext()) {
               Entity llllllllllllllllllllIlllIlIIIIll = (Entity)llllllllllllllllllllIlllIIIllIlI.next();
               if (EntityUtils.isSelected(llllllllllllllllllllIlllIlIIIIll, false)) {
                  mc.getRenderManager().renderEntityStatic(llllllllllllllllllllIlllIlIIIIll, mc.timer.renderPartialTicks, true);
                  boolean var10001 = false;
               }
            }
         } catch (Exception var16) {
            ClientUtils.getLogger().error("An error occurred while rendering all entities for shader esp", var16);
         }

         renderNameTags = true;
         float llllllllllllllllllllIlllIlIIIIII = llllllllllllllllllllIlllIIIlllII.equalsIgnoreCase("shaderoutline") ? (Float)llllllllllllllllllllIlllIIlIIIll.shaderOutlineRadius.get() : (llllllllllllllllllllIlllIIIlllII.equalsIgnoreCase("shaderglow") ? (Float)llllllllllllllllllllIlllIIlIIIll.shaderGlowRadius.get() : 1.0F);
         ((FramebufferShader)llllllllllllllllllllIlllIlIIIIIl).stopDraw(llllllllllllllllllllIlllIIlIIIll.getColor((Entity)null), llllllllllllllllllllIlllIlIIIIII, 1.0F);
      }

   }

   private void drawnew(Entity llllllllllllllllllllIllIllIlIIII, int llllllllllllllllllllIllIllIIIlll, int llllllllllllllllllllIllIllIIIllI, int llllllllllllllllllllIllIllIIIlIl, int llllllllllllllllllllIllIllIIIlII) {
      RenderUtils.drawnewrect(0.0F, 0.0F, 0.0F, 0.0F, 0);
      int llllllllllllllllllllIllIllIIlIll = 1;
      int llllllllllllllllllllIllIllIIlIlI = (new Color(0, 0, 0)).getRGB();
      if (((String)llllllllllllllllllllIllIllIlIIIl.modeValue.get()).equalsIgnoreCase("newbox")) {
         if ((Boolean)llllllllllllllllllllIllIllIlIIIl.outline.get()) {
            RenderUtils.drawnewrect((float)(llllllllllllllllllllIllIllIIIlIl + llllllllllllllllllllIllIllIIlIll), (float)(llllllllllllllllllllIllIllIIIllI + llllllllllllllllllllIllIllIIlIll), (float)(llllllllllllllllllllIllIllIIIlll - llllllllllllllllllllIllIllIIlIll), (float)llllllllllllllllllllIllIllIIIllI - 1.0F - (float)llllllllllllllllllllIllIllIIlIll, llllllllllllllllllllIllIllIIlIlI);
            RenderUtils.drawnewrect((float)(llllllllllllllllllllIllIllIIIlIl + llllllllllllllllllllIllIllIIlIll), (float)(llllllllllllllllllllIllIllIIIlII + llllllllllllllllllllIllIllIIlIll), (float)(llllllllllllllllllllIllIllIIIlll - llllllllllllllllllllIllIllIIlIll), (float)llllllllllllllllllllIllIllIIIlII - 1.0F - (float)llllllllllllllllllllIllIllIIlIll, llllllllllllllllllllIllIllIIlIlI);
            RenderUtils.drawnewrect((float)llllllllllllllllllllIllIllIIIlll + 1.0F + (float)llllllllllllllllllllIllIllIIlIll, (float)llllllllllllllllllllIllIllIIIllI, (float)(llllllllllllllllllllIllIllIIIlll - llllllllllllllllllllIllIllIIlIll), (float)llllllllllllllllllllIllIllIIIlII, llllllllllllllllllllIllIllIIlIlI);
            RenderUtils.drawnewrect((float)(llllllllllllllllllllIllIllIIIlIl + llllllllllllllllllllIllIllIIlIll), (float)llllllllllllllllllllIllIllIIIllI, (float)llllllllllllllllllllIllIllIIIlIl - 1.0F - (float)llllllllllllllllllllIllIllIIlIll, (float)llllllllllllllllllllIllIllIIIlII, llllllllllllllllllllIllIllIIlIlI);
         }

         RenderUtils.drawnewrect((float)llllllllllllllllllllIllIllIIIlIl, (float)llllllllllllllllllllIllIllIIIllI, (float)llllllllllllllllllllIllIllIIIlll, (float)llllllllllllllllllllIllIllIIIllI - 1.0F, llllllllllllllllllllIllIllIlIIIl.getColor(llllllllllllllllllllIllIllIlIIII).getRGB());
         RenderUtils.drawnewrect((float)llllllllllllllllllllIllIllIIIlIl, (float)llllllllllllllllllllIllIllIIIlII, (float)llllllllllllllllllllIllIllIIIlll, (float)llllllllllllllllllllIllIllIIIlII - 1.0F, llllllllllllllllllllIllIllIlIIIl.getColor(llllllllllllllllllllIllIllIlIIII).getRGB());
         RenderUtils.drawnewrect((float)llllllllllllllllllllIllIllIIIlll + 1.0F, (float)llllllllllllllllllllIllIllIIIllI, (float)llllllllllllllllllllIllIllIIIlll, (float)llllllllllllllllllllIllIllIIIlII, llllllllllllllllllllIllIllIlIIIl.getColor(llllllllllllllllllllIllIllIlIIII).getRGB());
         RenderUtils.drawnewrect((float)llllllllllllllllllllIllIllIIIlIl, (float)llllllllllllllllllllIllIllIIIllI, (float)llllllllllllllllllllIllIllIIIlIl - 1.0F, (float)llllllllllllllllllllIllIllIIIlII, llllllllllllllllllllIllIllIlIIIl.getColor(llllllllllllllllllllIllIllIlIIII).getRGB());
      }

      if (((String)llllllllllllllllllllIllIllIlIIIl.modeValue.get()).equalsIgnoreCase("new2d")) {
         if ((Boolean)llllllllllllllllllllIllIllIlIIIl.outline.get()) {
            RenderUtils.drawnewrect((float)llllllllllllllllllllIllIllIIIlll + 1.0F + (float)llllllllllllllllllllIllIllIIlIll, (float)(llllllllllllllllllllIllIllIIIllI - llllllllllllllllllllIllIllIIlIll), (float)(llllllllllllllllllllIllIllIIIlll - llllllllllllllllllllIllIllIIlIll), (float)llllllllllllllllllllIllIllIIIllI + (float)(llllllllllllllllllllIllIllIIIlII - llllllllllllllllllllIllIllIIIllI) / 3.0F + (float)llllllllllllllllllllIllIllIIlIll, llllllllllllllllllllIllIllIIlIlI);
            RenderUtils.drawnewrect((float)llllllllllllllllllllIllIllIIIlll + (float)(llllllllllllllllllllIllIllIIIlIl - llllllllllllllllllllIllIllIIIlll) / 3.0F + (float)llllllllllllllllllllIllIllIIlIll, (float)(llllllllllllllllllllIllIllIIIllI + llllllllllllllllllllIllIllIIlIll), (float)llllllllllllllllllllIllIllIIIlll, (float)llllllllllllllllllllIllIllIIIllI - 1.0F - (float)llllllllllllllllllllIllIllIIlIll, llllllllllllllllllllIllIllIIlIlI);
            RenderUtils.drawnewrect((float)(llllllllllllllllllllIllIllIIIlIl + llllllllllllllllllllIllIllIIlIll), (float)(llllllllllllllllllllIllIllIIIllI - llllllllllllllllllllIllIllIIlIll), (float)llllllllllllllllllllIllIllIIIlIl - 1.0F - (float)llllllllllllllllllllIllIllIIlIll, (float)llllllllllllllllllllIllIllIIIllI + (float)(llllllllllllllllllllIllIllIIIlII - llllllllllllllllllllIllIllIIIllI) / 3.0F + (float)llllllllllllllllllllIllIllIIlIll, llllllllllllllllllllIllIllIIlIlI);
            RenderUtils.drawnewrect((float)llllllllllllllllllllIllIllIIIlIl, (float)(llllllllllllllllllllIllIllIIIllI + llllllllllllllllllllIllIllIIlIll), (float)llllllllllllllllllllIllIllIIIlIl - (float)(llllllllllllllllllllIllIllIIIlIl - llllllllllllllllllllIllIllIIIlll) / 3.0F - (float)llllllllllllllllllllIllIllIIlIll, (float)llllllllllllllllllllIllIllIIIllI - 1.0F - (float)llllllllllllllllllllIllIllIIlIll, llllllllllllllllllllIllIllIIlIlI);
            RenderUtils.drawnewrect((float)llllllllllllllllllllIllIllIIIlll + 1.0F + (float)llllllllllllllllllllIllIllIIlIll, (float)llllllllllllllllllllIllIllIIIlII - 1.0F - (float)llllllllllllllllllllIllIllIIlIll, (float)(llllllllllllllllllllIllIllIIIlll - llllllllllllllllllllIllIllIIlIll), (float)llllllllllllllllllllIllIllIIIlII - 1.0F - (float)(llllllllllllllllllllIllIllIIIlII - llllllllllllllllllllIllIllIIIllI) / 3.0F - (float)llllllllllllllllllllIllIllIIlIll, llllllllllllllllllllIllIllIIlIlI);
            RenderUtils.drawnewrect((float)llllllllllllllllllllIllIllIIIlll + (float)(llllllllllllllllllllIllIllIIIlIl - llllllllllllllllllllIllIllIIIlll) / 3.0F + (float)llllllllllllllllllllIllIllIIlIll, (float)(llllllllllllllllllllIllIllIIIlII + llllllllllllllllllllIllIllIIlIll), (float)(llllllllllllllllllllIllIllIIIlll - llllllllllllllllllllIllIllIIlIll), (float)llllllllllllllllllllIllIllIIIlII - 1.0F - (float)llllllllllllllllllllIllIllIIlIll, llllllllllllllllllllIllIllIIlIlI);
            RenderUtils.drawnewrect((float)(llllllllllllllllllllIllIllIIIlIl + llllllllllllllllllllIllIllIIlIll), (float)llllllllllllllllllllIllIllIIIlII - 1.0F + (float)llllllllllllllllllllIllIllIIlIll, (float)llllllllllllllllllllIllIllIIIlIl - 1.0F - (float)llllllllllllllllllllIllIllIIlIll, (float)llllllllllllllllllllIllIllIIIlII - 1.0F - (float)(llllllllllllllllllllIllIllIIIlII - llllllllllllllllllllIllIllIIIllI) / 3.0F - (float)llllllllllllllllllllIllIllIIlIll, llllllllllllllllllllIllIllIIlIlI);
            RenderUtils.drawnewrect((float)(llllllllllllllllllllIllIllIIIlIl + llllllllllllllllllllIllIllIIlIll), (float)(llllllllllllllllllllIllIllIIIlII + llllllllllllllllllllIllIllIIlIll), (float)llllllllllllllllllllIllIllIIIlIl - (float)(llllllllllllllllllllIllIllIIIlIl - llllllllllllllllllllIllIllIIIlll) / 3.0F - (float)llllllllllllllllllllIllIllIIlIll, (float)llllllllllllllllllllIllIllIIIlII - 1.0F - (float)llllllllllllllllllllIllIllIIlIll, llllllllllllllllllllIllIllIIlIlI);
         }

         RenderUtils.drawnewrect((float)llllllllllllllllllllIllIllIIIlll + 1.0F, (float)llllllllllllllllllllIllIllIIIllI, (float)llllllllllllllllllllIllIllIIIlll, (float)llllllllllllllllllllIllIllIIIllI + (float)(llllllllllllllllllllIllIllIIIlII - llllllllllllllllllllIllIllIIIllI) / 3.0F, llllllllllllllllllllIllIllIlIIIl.getColor(llllllllllllllllllllIllIllIlIIII).getRGB());
         RenderUtils.drawnewrect((float)llllllllllllllllllllIllIllIIIlll + (float)(llllllllllllllllllllIllIllIIIlIl - llllllllllllllllllllIllIllIIIlll) / 3.0F, (float)llllllllllllllllllllIllIllIIIllI, (float)llllllllllllllllllllIllIllIIIlll, (float)llllllllllllllllllllIllIllIIIllI - 1.0F, llllllllllllllllllllIllIllIlIIIl.getColor(llllllllllllllllllllIllIllIlIIII).getRGB());
         RenderUtils.drawnewrect((float)llllllllllllllllllllIllIllIIIlIl, (float)llllllllllllllllllllIllIllIIIllI, (float)llllllllllllllllllllIllIllIIIlIl - 1.0F, (float)llllllllllllllllllllIllIllIIIllI + (float)(llllllllllllllllllllIllIllIIIlII - llllllllllllllllllllIllIllIIIllI) / 3.0F, llllllllllllllllllllIllIllIlIIIl.getColor(llllllllllllllllllllIllIllIlIIII).getRGB());
         RenderUtils.drawnewrect((float)llllllllllllllllllllIllIllIIIlIl, (float)llllllllllllllllllllIllIllIIIllI, (float)llllllllllllllllllllIllIllIIIlIl - (float)(llllllllllllllllllllIllIllIIIlIl - llllllllllllllllllllIllIllIIIlll) / 3.0F, (float)llllllllllllllllllllIllIllIIIllI - 1.0F, llllllllllllllllllllIllIllIlIIIl.getColor(llllllllllllllllllllIllIllIlIIII).getRGB());
         RenderUtils.drawnewrect((float)llllllllllllllllllllIllIllIIIlll + 1.0F, (float)llllllllllllllllllllIllIllIIIlII - 1.0F, (float)llllllllllllllllllllIllIllIIIlll, (float)llllllllllllllllllllIllIllIIIlII - 1.0F - (float)(llllllllllllllllllllIllIllIIIlII - llllllllllllllllllllIllIllIIIllI) / 3.0F, llllllllllllllllllllIllIllIlIIIl.getColor(llllllllllllllllllllIllIllIlIIII).getRGB());
         RenderUtils.drawnewrect((float)llllllllllllllllllllIllIllIIIlll + (float)(llllllllllllllllllllIllIllIIIlIl - llllllllllllllllllllIllIllIIIlll) / 3.0F, (float)llllllllllllllllllllIllIllIIIlII, (float)llllllllllllllllllllIllIllIIIlll, (float)llllllllllllllllllllIllIllIIIlII - 1.0F, llllllllllllllllllllIllIllIlIIIl.getColor(llllllllllllllllllllIllIllIlIIII).getRGB());
         RenderUtils.drawnewrect((float)llllllllllllllllllllIllIllIIIlIl, (float)llllllllllllllllllllIllIllIIIlII - 1.0F, (float)llllllllllllllllllllIllIllIIIlIl - 1.0F, (float)llllllllllllllllllllIllIllIIIlII - 1.0F - (float)(llllllllllllllllllllIllIllIIIlII - llllllllllllllllllllIllIllIIIllI) / 3.0F, llllllllllllllllllllIllIllIlIIIl.getColor(llllllllllllllllllllIllIllIlIIII).getRGB());
         RenderUtils.drawnewrect((float)llllllllllllllllllllIllIllIIIlIl, (float)llllllllllllllllllllIllIllIIIlII, (float)llllllllllllllllllllIllIllIIIlIl - (float)(llllllllllllllllllllIllIllIIIlIl - llllllllllllllllllllIllIllIIIlll) / 3.0F, (float)llllllllllllllllllllIllIllIIIlII - 1.0F, llllllllllllllllllllIllIllIlIIIl.getColor(llllllllllllllllllllIllIllIlIIII).getRGB());
      }

   }

   private void drawArmor(EntityLivingBase llllllllllllllllllllIllIlIlIllll, float llllllllllllllllllllIllIlIlIlllI, float llllllllllllllllllllIllIlIlIllIl, float llllllllllllllllllllIllIlIlIllII, float llllllllllllllllllllIllIlIlIlIll) {
      float llllllllllllllllllllIllIlIlIlIlI = llllllllllllllllllllIllIlIlIlIll + 1.0F - llllllllllllllllllllIllIlIlIllIl;
      float llllllllllllllllllllIllIlIlIIIII = (float)llllllllllllllllllllIllIlIlIllll.getTotalArmorValue();
      float llllllllllllllllllllIllIlIlIlIII = llllllllllllllllllllIllIlIlIIIII / 20.0F;
      float llllllllllllllllllllIllIlIIllllI = 2.0F;
      long llllllllllllllllllllIllIlIIlllIl = 1;
      if (!(mc.thePlayer.getDistanceToEntity(llllllllllllllllllllIllIlIlIllll) > 16.0F)) {
         RenderUtils.drawnewrect(llllllllllllllllllllIllIlIlIllII + 2.0F + 1.0F + llllllllllllllllllllIllIlIIllllI, llllllllllllllllllllIllIlIlIllIl - 2.0F, llllllllllllllllllllIllIlIlIllII + 1.0F - 1.0F + llllllllllllllllllllIllIlIIllllI, llllllllllllllllllllIllIlIlIlIll + 1.0F, (new Color(25, 25, 25, 150)).getRGB());
         RenderUtils.drawnewrect(llllllllllllllllllllIllIlIlIllII + 3.0F + llllllllllllllllllllIllIlIIllllI, llllllllllllllllllllIllIlIlIllIl + llllllllllllllllllllIllIlIlIlIlI * (1.0F - llllllllllllllllllllIllIlIlIlIII) - 1.0F, llllllllllllllllllllIllIlIlIllII + 1.0F + llllllllllllllllllllIllIlIIllllI, llllllllllllllllllllIllIlIlIlIll, (new Color(125, 155, 205)).getRGB());
         RenderUtils.drawnewrect(llllllllllllllllllllIllIlIlIllII + 3.0F + llllllllllllllllllllIllIlIIllllI + (float)llllllllllllllllllllIllIlIIlllIl, llllllllllllllllllllIllIlIlIlIll + 1.0F, llllllllllllllllllllIllIlIlIllII + 3.0F + llllllllllllllllllllIllIlIIllllI, llllllllllllllllllllIllIlIlIllIl - 2.0F, (new Color(0, 0, 0, 255)).getRGB());
         RenderUtils.drawnewrect(llllllllllllllllllllIllIlIlIllII + 1.0F + llllllllllllllllllllIllIlIIllllI, llllllllllllllllllllIllIlIlIlIll + 1.0F, llllllllllllllllllllIllIlIlIllII + 1.0F + llllllllllllllllllllIllIlIIllllI - (float)llllllllllllllllllllIllIlIIlllIl, llllllllllllllllllllIllIlIlIllIl - 2.0F, (new Color(0, 0, 0, 255)).getRGB());
         RenderUtils.drawnewrect(llllllllllllllllllllIllIlIlIllII + 1.0F + llllllllllllllllllllIllIlIIllllI, llllllllllllllllllllIllIlIlIllIl - 1.0F, llllllllllllllllllllIllIlIlIllII + 3.0F + llllllllllllllllllllIllIlIIllllI, llllllllllllllllllllIllIlIlIllIl - 2.0F, (new Color(0, 0, 0, 255)).getRGB());
         RenderUtils.drawnewrect(llllllllllllllllllllIllIlIlIllII + 1.0F + llllllllllllllllllllIllIlIIllllI, llllllllllllllllllllIllIlIlIlIll + 1.0F, llllllllllllllllllllIllIlIlIllII + 3.0F + llllllllllllllllllllIllIlIIllllI, llllllllllllllllllllIllIlIlIlIll, (new Color(0, 0, 0, 255)).getRGB());

         for(int llllllllllllllllllllIllIlIIlllII = 0; llllllllllllllllllllIllIlIIlllII < 4; ++llllllllllllllllllllIllIlIIlllII) {
            double llllllllllllllllllllIllIlIllIIll = (double)((llllllllllllllllllllIllIlIlIlIll - llllllllllllllllllllIllIlIlIllIl) / 4.0F);
            Exception llllllllllllllllllllIllIlIIllIlI = llllllllllllllllllllIllIlIlIllll.getEquipmentInSlot(llllllllllllllllllllIllIlIIlllII + 1);
            if (llllllllllllllllllllIllIlIIllIlI != null) {
               RenderUtils.renderItemStack(llllllllllllllllllllIllIlIIllIlI, (int)(llllllllllllllllllllIllIlIlIllII + 6.0F + llllllllllllllllllllIllIlIIllllI), (int)((double)(llllllllllllllllllllIllIlIlIlIll - 2.0F) - (double)(llllllllllllllllllllIllIlIIlllII + 1) * llllllllllllllllllllIllIlIllIIll));
               GlStateManager.pushMatrix();
               long llllllllllllllllllllIllIlIIllIIl = 1.0F;
               GlStateManager.scale(llllllllllllllllllllIllIlIIllIIl, llllllllllllllllllllIllIlIIllIIl, llllllllllllllllllllIllIlIIllIIl);
               mc.fontRendererObj.drawStringWithShadow(String.valueOf(llllllllllllllllllllIllIlIIllIlI.getMaxDamage() - llllllllllllllllllllIllIlIIllIlI.getItemDamage()), (llllllllllllllllllllIllIlIlIllII + 6.0F + llllllllllllllllllllIllIlIIllllI + (16.0F - (float)mc.fontRendererObj.getStringWidth(String.valueOf(llllllllllllllllllllIllIlIIllIlI.getMaxDamage() - llllllllllllllllllllIllIlIIllIlI.getItemDamage())) * llllllllllllllllllllIllIlIIllIIl) / 2.0F) / llllllllllllllllllllIllIlIIllIIl, (float)((int)((double)(llllllllllllllllllllIllIlIlIlIll - 2.0F) - (double)(llllllllllllllllllllIllIlIIlllII + 1) * llllllllllllllllllllIllIlIllIIll) + 16) / llllllllllllllllllllIllIlIIllIIl, -1);
               boolean var10001 = false;
               GlStateManager.popMatrix();
            }
         }

      }
   }

   private void drawHealth(EntityLivingBase llllllllllllllllllllIllIIllllllI, float llllllllllllllllllllIllIlIIIlIlI, float llllllllllllllllllllIllIIlllllII, float llllllllllllllllllllIllIlIIIlIII, float llllllllllllllllllllIllIIllllIll) {
      float llllllllllllllllllllIllIlIIIIllI = llllllllllllllllllllIllIIllllIll + 1.0F - llllllllllllllllllllIllIIlllllII;
      double llllllllllllllllllllIllIIllllIIl = llllllllllllllllllllIllIIllllllI.getHealth();
      float llllllllllllllllllllIllIlIIIIlII = llllllllllllllllllllIllIIllllllI.getMaxHealth();
      byte llllllllllllllllllllIllIIlllIlll = llllllllllllllllllllIllIIllllIIl / llllllllllllllllllllIllIlIIIIlII;
      float llllllllllllllllllllIllIlIIIIIlI = 2.0F;
      char llllllllllllllllllllIllIIlllIlIl = 1;
      String llllllllllllllllllllIllIIlllIlII = String.valueOf((new StringBuilder()).append("ยงf").append(llllllllllllllllllllIllIIllllIIl).append("ยงcโ?ค"));
      mc.fontRendererObj.drawStringWithShadow(llllllllllllllllllllIllIIlllIlII, llllllllllllllllllllIllIlIIIlIlI - 3.0F - llllllllllllllllllllIllIlIIIIIlI - (float)mc.fontRendererObj.getStringWidth(llllllllllllllllllllIllIIlllIlII), llllllllllllllllllllIllIIlllllII + llllllllllllllllllllIllIlIIIIllI * (1.0F - llllllllllllllllllllIllIIlllIlll) - 1.0F, -1);
      boolean var10001 = false;
      RenderUtils.drawnewrect(llllllllllllllllllllIllIlIIIlIlI - 3.0F - llllllllllllllllllllIllIlIIIIIlI, llllllllllllllllllllIllIIllllIll, llllllllllllllllllllIllIlIIIlIlI - 1.0F - llllllllllllllllllllIllIlIIIIIlI, llllllllllllllllllllIllIIlllllII - 1.0F, (new Color(25, 25, 25, 150)).getRGB());
      RenderUtils.drawnewrect(llllllllllllllllllllIllIlIIIlIlI - 3.0F - llllllllllllllllllllIllIlIIIIIlI, llllllllllllllllllllIllIIllllIll, llllllllllllllllllllIllIlIIIlIlI - 1.0F - llllllllllllllllllllIllIlIIIIIlI, llllllllllllllllllllIllIIlllllII + llllllllllllllllllllIllIlIIIIllI * (1.0F - llllllllllllllllllllIllIIlllIlll) - 1.0F, getHealthColor(llllllllllllllllllllIllIIllllllI.getHealth(), llllllllllllllllllllIllIIllllllI.getMaxHealth()));
      RenderUtils.drawnewrect(llllllllllllllllllllIllIlIIIlIlI - 3.0F - llllllllllllllllllllIllIlIIIIIlI, llllllllllllllllllllIllIIllllIll + 1.0F, llllllllllllllllllllIllIlIIIlIlI - 3.0F - llllllllllllllllllllIllIlIIIIIlI - (float)llllllllllllllllllllIllIIlllIlIl, llllllllllllllllllllIllIIlllllII - 2.0F, (new Color(0, 0, 0, 255)).getRGB());
      RenderUtils.drawnewrect(llllllllllllllllllllIllIlIIIlIlI - 1.0F - llllllllllllllllllllIllIlIIIIIlI + (float)llllllllllllllllllllIllIIlllIlIl, llllllllllllllllllllIllIIllllIll + 1.0F, llllllllllllllllllllIllIlIIIlIlI - 1.0F - llllllllllllllllllllIllIlIIIIIlI, llllllllllllllllllllIllIIlllllII - 2.0F, (new Color(0, 0, 0, 255)).getRGB());
      RenderUtils.drawnewrect(llllllllllllllllllllIllIlIIIlIlI - 3.0F - llllllllllllllllllllIllIlIIIIIlI, llllllllllllllllllllIllIIlllllII - 1.0F, llllllllllllllllllllIllIlIIIlIlI - 1.0F - llllllllllllllllllllIllIlIIIIIlI, llllllllllllllllllllIllIIlllllII - 2.0F, (new Color(0, 0, 0, 255)).getRGB());
      RenderUtils.drawnewrect(llllllllllllllllllllIllIlIIIlIlI - 3.0F - llllllllllllllllllllIllIlIIIIIlI, llllllllllllllllllllIllIIllllIll + 1.0F, llllllllllllllllllllIllIlIIIlIlI - 1.0F - llllllllllllllllllllIllIlIIIIIlI, llllllllllllllllllllIllIIllllIll, (new Color(0, 0, 0, 255)).getRGB());
   }

   public Color getColor(Entity llllllllllllllllllllIllIlllIIIlI) {
      if (llllllllllllllllllllIllIlllIIIlI instanceof EntityLivingBase) {
         EntityLivingBase llllllllllllllllllllIllIlllIIlII = (EntityLivingBase)llllllllllllllllllllIllIlllIIIlI;
         if (llllllllllllllllllllIllIlllIIlII.hurtTime > 0) {
            return Color.RED;
         }

         if (EntityUtils.isFriend(llllllllllllllllllllIllIlllIIlII)) {
            return Color.BLUE;
         }

         if ((Boolean)llllllllllllllllllllIllIlllIIIll.colorTeam.get()) {
            char[] llllllllllllllllllllIllIlllIIlll = llllllllllllllllllllIllIlllIIlII.getDisplayName().getFormattedText().toCharArray();
            double llllllllllllllllllllIllIllIlllIl = Integer.MAX_VALUE;
            double llllllllllllllllllllIllIllIlllII = "0123456789abcdef";

            for(int llllllllllllllllllllIllIlllIlIII = 0; llllllllllllllllllllIllIlllIlIII < llllllllllllllllllllIllIlllIIlll.length; ++llllllllllllllllllllIllIlllIlIII) {
               if (llllllllllllllllllllIllIlllIIlll[llllllllllllllllllllIllIlllIlIII] == 167 && llllllllllllllllllllIllIlllIlIII + 1 < llllllllllllllllllllIllIlllIIlll.length) {
                  float llllllllllllllllllllIllIllIllIlI = "0123456789abcdef".indexOf(llllllllllllllllllllIllIlllIIlll[llllllllllllllllllllIllIlllIlIII + 1]);
                  if (llllllllllllllllllllIllIllIllIlI != -1) {
                     llllllllllllllllllllIllIllIlllIl = ColorUtils.hexColors[llllllllllllllllllllIllIllIllIlI];
                     break;
                  }
               }
            }

            return new Color(llllllllllllllllllllIllIllIlllIl);
         }
      }

      return (Boolean)llllllllllllllllllllIllIlllIIIll.colorRainbow.get() ? ColorUtils.rainbow() : new Color((Integer)llllllllllllllllllllIllIlllIIIll.colorRedValue.get(), (Integer)llllllllllllllllllllIllIlllIIIll.colorGreenValue.get(), (Integer)llllllllllllllllllllIllIlllIIIll.colorBlueValue.get());
   }

   public String getTag() {
      return (String)llllllllllllllllllllIlllIIIIlIll.modeValue.get();
   }

   @EventTarget
   public void onRender3D(Render3DEvent llllllllllllllllllllIllllllIIIIl) {
      String llllllllllllllllllllIllllllIIIII = (String)llllllllllllllllllllIllllllIIIlI.modeValue.get();
      Iterator llllllllllllllllllllIllllllIllll;
      Entity llllllllllllllllllllIllllllIIIll;
      if (llllllllllllllllllllIllllllIIIII.equalsIgnoreCase("newbox")) {
         llllllllllllllllllllIllllllIllll = mc.theWorld.loadedEntityList.iterator();

         label95:
         while(true) {
            do {
               if (!llllllllllllllllllllIllllllIllll.hasNext()) {
                  break label95;
               }

               llllllllllllllllllllIllllllIIIll = (Entity)llllllllllllllllllllIllllllIllll.next();
            } while(!(llllllllllllllllllllIllllllIIIll instanceof EntityItem) && (llllllllllllllllllllIllllllIIIll == null || llllllllllllllllllllIllllllIIIll == mc.thePlayer || !EntityUtils.isSelected(llllllllllllllllllllIllllllIIIll, false)));

            RenderUtils.updateView();
         }
      }

      if (llllllllllllllllllllIllllllIIIII.equalsIgnoreCase("new2d")) {
         llllllllllllllllllllIllllllIllll = mc.theWorld.loadedEntityList.iterator();

         label78:
         while(true) {
            do {
               if (!llllllllllllllllllllIllllllIllll.hasNext()) {
                  break label78;
               }

               llllllllllllllllllllIllllllIIIll = (Entity)llllllllllllllllllllIllllllIllll.next();
            } while(!(llllllllllllllllllllIllllllIIIll instanceof EntityItem) && (llllllllllllllllllllIllllllIIIll == null || llllllllllllllllllllIllllllIIIll == mc.thePlayer || !EntityUtils.isSelected(llllllllllllllllllllIllllllIIIll, false)));

            RenderUtils.updateView();
         }
      }

      llllllllllllllllllllIllllllIllll = mc.theWorld.loadedEntityList.iterator();

      while(llllllllllllllllllllIllllllIllll.hasNext()) {
         llllllllllllllllllllIllllllIIIll = (Entity)llllllllllllllllllllIllllllIllll.next();
         if (llllllllllllllllllllIllllllIIIll != null && llllllllllllllllllllIllllllIIIll != mc.thePlayer && EntityUtils.isSelected(llllllllllllllllllllIllllllIIIll, false)) {
            long llllllllllllllllllllIlllllIllIll = new ScaledResolution(mc);
            double llllllllllllllllllllIllllllIllIl = (double)llllllllllllllllllllIlllllIllIll.getScaleFactor() / Math.pow((double)llllllllllllllllllllIlllllIllIll.getScaleFactor(), 2.0D);
            int llllllllllllllllllllIlllllIllIIl = (EntityLivingBase)llllllllllllllllllllIllllllIIIll;
            RenderManager llllllllllllllllllllIllllllIlIll = mc.getRenderManager();
            Timer llllllllllllllllllllIllllllIlIlI = mc.timer;
            float llllllllllllllllllllIllllllIlIIl = mc.timer.renderPartialTicks;
            byte llllllllllllllllllllIlllllIlIlIl = llllllllllllllllllllIlllllIllIIl.lastTickPosX + (llllllllllllllllllllIlllllIllIIl.posX - llllllllllllllllllllIlllllIllIIl.lastTickPosX) * (double)llllllllllllllllllllIllllllIlIlI.renderPartialTicks - llllllllllllllllllllIllllllIlIll.renderPosX;
            boolean llllllllllllllllllllIlllllIlIlII = llllllllllllllllllllIlllllIllIIl.lastTickPosY + (llllllllllllllllllllIlllllIllIIl.posY - llllllllllllllllllllIlllllIllIIl.lastTickPosY) * (double)llllllllllllllllllllIllllllIlIlI.renderPartialTicks - llllllllllllllllllllIllllllIlIll.renderPosY;
            double llllllllllllllllllllIllllllIIllI = llllllllllllllllllllIlllllIllIIl.lastTickPosZ + (llllllllllllllllllllIlllllIllIIl.posZ - llllllllllllllllllllIlllllIllIIl.lastTickPosZ) * (double)llllllllllllllllllllIllllllIlIlI.renderPartialTicks - llllllllllllllllllllIllllllIlIll.renderPosZ;
            Iterator<Vec3> llllllllllllllllllllIlllllIlIIlI = llllllllllllllllllllIllllllIIIlI.positions.iterator();
            Vec3 llllllllllllllllllllIllllllIIlII = RenderUtils.WorldToScreen((Vec3)llllllllllllllllllllIlllllIlIIlI.next());
            String llllllllllllllllllllIlllllIlIIII = llllllllllllllllllllIllllllIIIII.toLowerCase();
            int llllllllllllllllllllIlllllIIllll = -1;
            switch(llllllllllllllllllllIlllllIlIIII.hashCode()) {
            case -1171135301:
               if (llllllllllllllllllllIlllllIlIIII.equals("otherbox")) {
                  llllllllllllllllllllIlllllIIllll = 1;
               }
               break;
            case -903579360:
               if (llllllllllllllllllllIlllllIlIIII.equals("shadow")) {
                  llllllllllllllllllllIlllllIIllll = 3;
               }
               break;
            case 1650:
               if (llllllllllllllllllllIlllllIlIIII.equals("2d")) {
                  llllllllllllllllllllIlllllIIllll = 2;
               }
               break;
            case 97739:
               if (llllllllllllllllllllIlllllIlIIII.equals("box")) {
                  llllllllllllllllllllIlllllIIllll = 0;
               }
            }

            switch(llllllllllllllllllllIlllllIIllll) {
            case 0:
            case 1:
               RenderUtils.drawEntityBox(llllllllllllllllllllIllllllIIIll, llllllllllllllllllllIllllllIIIlI.getColor(llllllllllllllllllllIlllllIllIIl), !llllllllllllllllllllIllllllIIIII.equalsIgnoreCase("otherbox"));
               break;
            case 2:
               RenderUtils.draw2D(llllllllllllllllllllIlllllIllIIl, llllllllllllllllllllIlllllIlIlIl, llllllllllllllllllllIlllllIlIlII, llllllllllllllllllllIllllllIIllI, llllllllllllllllllllIllllllIIIlI.getColor(llllllllllllllllllllIlllllIllIIl).getRGB(), Color.BLACK.getRGB());
               break;
            case 3:
               RenderUtils.shadow(llllllllllllllllllllIlllllIllIIl, llllllllllllllllllllIlllllIlIlIl, llllllllllllllllllllIlllllIlIlII, llllllllllllllllllllIllllllIIllI, (double)(Float)llllllllllllllllllllIllllllIIIlI.range.get(), 64, llllllllllllllllllllIllllllIIIlI.shadowgetColor(llllllllllllllllllllIlllllIllIIl).getRGB());
               RenderUtils.cylinder(llllllllllllllllllllIlllllIllIIl, llllllllllllllllllllIlllllIlIlIl, llllllllllllllllllllIlllllIlIlII, llllllllllllllllllllIllllllIIllI, (double)(Float)llllllllllllllllllllIllllllIIIlI.range.get(), 64, llllllllllllllllllllIllllllIIIlI.getColor(llllllllllllllllllllIlllllIllIIl).getRGB());
            }
         }
      }

   }

   public void updatePositions(Entity llllllllllllllllllllIlllllIIIlII) {
      llllllllllllllllllllIlllllIIIlIl.positions.clear();
      Vec3 llllllllllllllllllllIlllllIIIIll = RenderUtils.getEntityRenderPosition(llllllllllllllllllllIlllllIIIlII);
      double llllllllllllllllllllIlllllIIIIlI = llllllllllllllllllllIlllllIIIIll.xCoord - llllllllllllllllllllIlllllIIIlII.posX;
      int llllllllllllllllllllIllllIlllIII = llllllllllllllllllllIlllllIIIIll.yCoord - llllllllllllllllllllIlllllIIIlII.posY;
      long llllllllllllllllllllIllllIllIlll = llllllllllllllllllllIlllllIIIIll.zCoord - llllllllllllllllllllIlllllIIIlII.posZ;
      double llllllllllllllllllllIllllIllllll = llllllllllllllllllllIlllllIIIlII instanceof EntityItem ? 0.5D : (double)llllllllllllllllllllIlllllIIIlII.height + 0.1D;
      short llllllllllllllllllllIllllIllIlIl = llllllllllllllllllllIlllllIIIlII instanceof EntityItem ? 0.25D : (double)(Float)llllllllllllllllllllIlllllIIIlIl.width2d.get();
      long llllllllllllllllllllIllllIllIlII = new AxisAlignedBB(llllllllllllllllllllIlllllIIIlII.posX - llllllllllllllllllllIllllIllIlIl + llllllllllllllllllllIlllllIIIIlI, llllllllllllllllllllIlllllIIIlII.posY + llllllllllllllllllllIllllIlllIII, llllllllllllllllllllIlllllIIIlII.posZ - llllllllllllllllllllIllllIllIlIl + llllllllllllllllllllIllllIllIlll, llllllllllllllllllllIlllllIIIlII.posX + llllllllllllllllllllIllllIllIlIl + llllllllllllllllllllIlllllIIIIlI, llllllllllllllllllllIlllllIIIlII.posY + llllllllllllllllllllIllllIllllll + llllllllllllllllllllIllllIlllIII, llllllllllllllllllllIlllllIIIlII.posZ + llllllllllllllllllllIllllIllIlIl + llllllllllllllllllllIllllIllIlll);
      llllllllllllllllllllIlllllIIIlIl.positions.add(new Vec3(llllllllllllllllllllIllllIllIlII.minX, llllllllllllllllllllIllllIllIlII.minY, llllllllllllllllllllIllllIllIlII.minZ));
      boolean var10001 = false;
      llllllllllllllllllllIlllllIIIlIl.positions.add(new Vec3(llllllllllllllllllllIllllIllIlII.minX, llllllllllllllllllllIllllIllIlII.minY, llllllllllllllllllllIllllIllIlII.maxZ));
      var10001 = false;
      llllllllllllllllllllIlllllIIIlIl.positions.add(new Vec3(llllllllllllllllllllIllllIllIlII.minX, llllllllllllllllllllIllllIllIlII.maxY, llllllllllllllllllllIllllIllIlII.minZ));
      var10001 = false;
      llllllllllllllllllllIlllllIIIlIl.positions.add(new Vec3(llllllllllllllllllllIllllIllIlII.minX, llllllllllllllllllllIllllIllIlII.maxY, llllllllllllllllllllIllllIllIlII.maxZ));
      var10001 = false;
      llllllllllllllllllllIlllllIIIlIl.positions.add(new Vec3(llllllllllllllllllllIllllIllIlII.maxX, llllllllllllllllllllIllllIllIlII.minY, llllllllllllllllllllIllllIllIlII.minZ));
      var10001 = false;
      llllllllllllllllllllIlllllIIIlIl.positions.add(new Vec3(llllllllllllllllllllIllllIllIlII.maxX, llllllllllllllllllllIllllIllIlII.minY, llllllllllllllllllllIllllIllIlII.maxZ));
      var10001 = false;
      llllllllllllllllllllIlllllIIIlIl.positions.add(new Vec3(llllllllllllllllllllIllllIllIlII.maxX, llllllllllllllllllllIllllIllIlII.maxY, llllllllllllllllllllIllllIllIlII.minZ));
      var10001 = false;
      llllllllllllllllllllIlllllIIIlIl.positions.add(new Vec3(llllllllllllllllllllIllllIllIlII.maxX, llllllllllllllllllllIllllIllIlII.maxY, llllllllllllllllllllIllllIllIlII.maxZ));
      var10001 = false;
   }

   public static int getHealthColor(float llllllllllllllllllllIllIIlllIIII, float llllllllllllllllllllIllIIllIllll) {
      float llllllllllllllllllllIllIIllIlllI = llllllllllllllllllllIllIIlllIIII / llllllllllllllllllllIllIIllIllll;
      if (llllllllllllllllllllIllIIllIlllI >= 0.75F) {
         return (new Color(0, 255, 0)).getRGB();
      } else {
         return (double)llllllllllllllllllllIllIIllIlllI < 0.75D && (double)llllllllllllllllllllIllIIllIlllI >= 0.25D ? (new Color(255, 255, 0)).getRGB() : (new Color(255, 0, 0)).getRGB();
      }
   }
}
