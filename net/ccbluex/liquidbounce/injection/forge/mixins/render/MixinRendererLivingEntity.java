//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import co.uk.hexeption.utils.OutlineUtils;
import java.awt.Color;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.SommtheEvent;
import net.ccbluex.liquidbounce.features.module.modules.combat.Aura;
import net.ccbluex.liquidbounce.features.module.modules.render.Chams;
import net.ccbluex.liquidbounce.features.module.modules.render.ESP;
import net.ccbluex.liquidbounce.features.module.modules.render.NameTags;
import net.ccbluex.liquidbounce.features.module.modules.render.Rotations;
import net.ccbluex.liquidbounce.features.module.modules.render.TrueSight;
import net.ccbluex.liquidbounce.features.module.modules.world.Scaffold;
import net.ccbluex.liquidbounce.utils.ChamsColor;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.RenderLivingEvent.Post;
import net.minecraftforge.client.event.RenderLivingEvent.Pre;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SideOnly(Side.CLIENT)
@Mixin({RendererLivingEntity.class})
public abstract class MixinRendererLivingEntity extends MixinRender {
   // $FF: synthetic field
   @Shadow
   private static final Logger logger = LogManager.getLogger();
   // $FF: synthetic field
   @Shadow
   public boolean renderOutlines = false;
   // $FF: synthetic field
   @Shadow
   protected ModelBase mainModel;

   @Shadow
   public abstract float interpolateRotation(float var1, float var2, float var3);

   @Shadow
   public abstract void unsetScoreTeamColor();

   @Shadow
   protected abstract <T extends EntityLivingBase> boolean setScoreTeamColor(T var1);

   @Inject(
      method = {"canRenderName"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private <T extends EntityLivingBase> void canRenderName(T lllllIllIllIllI, CallbackInfoReturnable<Boolean> lllllIllIllIIll) {
      if (!ESP.renderNameTags || LiquidBounce.moduleManager.getModule(NameTags.class).getState() && EntityUtils.isSelected(lllllIllIllIllI, false)) {
         lllllIllIllIIll.setReturnValue(false);
      }

   }

   @Shadow
   public abstract <T extends EntityLivingBase> float handleRotationFloat(T var1, float var2);

   @Shadow
   public abstract <T extends EntityLivingBase> float getSwingProgress(T var1, float var2);

   @Shadow
   public abstract <T extends EntityLivingBase> void rotateCorpse(T var1, float var2, float var3, float var4);

   @Shadow
   public abstract <T extends EntityLivingBase> void renderLivingAt(T var1, double var2, double var4, double var6);

   @Shadow
   public abstract <T extends EntityLivingBase> void renderLayers(T var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8);

   @Overwrite
   public <T extends EntityLivingBase> void doRender(T lllllIlIlllllll, double lllllIlIllllllI, double lllllIlIlllIlIl, double lllllIlIlllllII, float lllllIlIllllIll, float lllllIlIllllIlI) {
      long lllllIlIlllIIIl = (Chams)LiquidBounce.moduleManager.getModule(Chams.class);
      if (lllllIlIlllIIIl.getState() && (Boolean)lllllIlIlllIIIl.getTargetsValue().get() && EntityUtils.isSelected(lllllIlIlllllll, false)) {
         GL11.glEnable(32823);
         GL11.glPolygonOffset(0.75F, -1000000.0F);
      }

      if (!MinecraftForge.EVENT_BUS.post(new Pre(lllllIlIlllllll, (RendererLivingEntity)lllllIllIIIIIII, lllllIlIllllllI, lllllIlIlllIlIl, lllllIlIlllllII))) {
         Aura lllllIllIIIIlII = (Aura)LiquidBounce.moduleManager.getModule(Aura.class);
         String lllllIlIllIllll = (Scaffold)LiquidBounce.moduleManager.getModule(Scaffold.class);
         Exception lllllIlIllIlllI = (Rotations)LiquidBounce.moduleManager.getModule(Rotations.class);
         GlStateManager.pushMatrix();
         GlStateManager.disableCull();
         lllllIllIIIIIII.mainModel.swingProgress = lllllIllIIIIIII.getSwingProgress(lllllIlIlllllll, lllllIlIllllIlI);
         int lllllIlIllIllIl = lllllIlIlllllll.isRiding() && lllllIlIlllllll.ridingEntity != null && lllllIlIlllllll.ridingEntity.shouldRiderSit();
         lllllIllIIIIIII.mainModel.isRiding = lllllIlIllIllIl;
         lllllIllIIIIIII.mainModel.isChild = lllllIlIlllllll.isChild();
         if (lllllIlIlllIIIl.getState() && (Boolean)lllllIlIlllIIIl.getTargetsValue().get() && EntityUtils.isSelected(lllllIlIlllllll, false)) {
            GL11.glEnable(32823);
            GL11.glPolygonOffset(1.0F, -1000000.0F);
         }

         try {
            float lllllIllIIIllll = lllllIllIIIIIII.interpolateRotation(lllllIlIlllllll.prevRenderYawOffset, lllllIlIlllllll.renderYawOffset, lllllIlIllllIlI);
            float lllllIllIIIlllI = lllllIllIIIIIII.interpolateRotation(lllllIlIlllllll.prevRotationYawHead, lllllIlIlllllll.rotationYawHead, lllllIlIllllIlI);
            float lllllIllIIIllIl = lllllIllIIIlllI - lllllIllIIIllll;
            float lllllIlIllIlIIl;
            if (lllllIlIllIllIl && lllllIlIlllllll.ridingEntity instanceof EntityLivingBase) {
               float lllllIlIllIlIII = (EntityLivingBase)lllllIlIlllllll.ridingEntity;
               lllllIllIIIllll = lllllIllIIIIIII.interpolateRotation(lllllIlIllIlIII.prevRenderYawOffset, lllllIlIllIlIII.renderYawOffset, lllllIlIllllIlI);
               lllllIllIIIllIl = lllllIllIIIlllI - lllllIllIIIllll;
               lllllIlIllIlIIl = MathHelper.wrapAngleTo180_float(lllllIllIIIllIl);
               if (lllllIlIllIlIIl < -85.0F) {
                  lllllIlIllIlIIl = -85.0F;
               }

               if (lllllIlIllIlIIl >= 85.0F) {
                  lllllIlIllIlIIl = 85.0F;
               }

               lllllIllIIIllll = lllllIllIIIlllI - lllllIlIllIlIIl;
               if (lllllIlIllIlIIl * lllllIlIllIlIIl > 2500.0F) {
                  lllllIllIIIllll += lllllIlIllIlIIl * 0.2F;
               }
            }

            Minecraft lllllIllIIIlIll = Minecraft.getMinecraft();
            long lllllIlIllIIlll = lllllIlIlllllll.prevRotationPitch + (lllllIlIlllllll.rotationPitch - lllllIlIlllllll.prevRotationPitch) * lllllIlIllllIlI;
            lllllIllIIIIIII.renderLivingAt(lllllIlIlllllll, lllllIlIllllllI, lllllIlIlllIlIl, lllllIlIlllllII);
            lllllIlIllIlIIl = lllllIllIIIIIII.handleRotationFloat(lllllIlIlllllll, lllllIlIllllIlI);
            float lllllIllIIlIlIl;
            float lllllIlIllIIlIl;
            float lllllIlIllIIlII;
            if (lllllIlIlllllll instanceof EntityPlayerSP && lllllIlIllIlllI.getState() && lllllIlIllIlllI.getModeValue().equals("Other")) {
               lllllIllIIlIlIl = SommtheEvent.YAW;
               lllllIlIllIIlIl = SommtheEvent.PITCH;
               lllllIlIllIIlII = SommtheEvent.PREVYAW;
               String lllllIlIllIIIll = SommtheEvent.PREVPITCH;
               boolean lllllIllIIlIIIl = SommtheEvent.SNEAKING;
               if (lllllIllIIIIlII.getTarget() != null || lllllIlIllIllll.getState()) {
                  lllllIllIIIllll = lllllIllIIIIIII.interpolateRotation(lllllIlIllIIlII, lllllIllIIlIlIl, lllllIlIllllIlI);
                  float lllllIllIIlIlll = lllllIllIIIIIII.interpolateRotation(lllllIlIllIIlII, lllllIllIIlIlIl, lllllIlIllllIlI) - lllllIllIIIllll;
                  float lllllIllIIlIllI = lllllIllIIIIIII.interpolateRotation(lllllIlIllIIIll, lllllIlIllIIlIl, lllllIlIllllIlI);
                  lllllIllIIIllIl = lllllIllIIlIlll;
                  lllllIlIllIIlll = lllllIllIIlIllI;
               }
            }

            lllllIllIIIIIII.rotateCorpse(lllllIlIlllllll, lllllIlIllIlIIl, lllllIllIIIllll, lllllIlIllllIlI);
            GlStateManager.enableRescaleNormal();
            GlStateManager.scale(-1.0F, -1.0F, 1.0F);
            lllllIllIIIIIII.preRenderCallback(lllllIlIlllllll, lllllIlIllllIlI);
            lllllIllIIlIlIl = 0.0625F;
            GlStateManager.translate(0.0F, -1.5078125F, 0.0F);
            lllllIlIllIIlIl = lllllIlIlllllll.prevLimbSwingAmount + (lllllIlIlllllll.limbSwingAmount - lllllIlIlllllll.prevLimbSwingAmount) * lllllIlIllllIlI;
            lllllIlIllIIlII = lllllIlIlllllll.limbSwing - lllllIlIlllllll.limbSwingAmount * (1.0F - lllllIlIllllIlI);
            if (lllllIlIlllllll.isChild()) {
               lllllIlIllIIlII *= 3.0F;
            }

            if (lllllIlIllIIlIl > 1.0F) {
               lllllIlIllIIlIl = 1.0F;
            }

            GlStateManager.enableAlpha();
            lllllIllIIIIIII.mainModel.setLivingAnimations(lllllIlIlllllll, lllllIlIllIIlII, lllllIlIllIIlIl, lllllIlIllllIlI);
            lllllIllIIIIIII.mainModel.setRotationAngles(lllllIlIllIIlII, lllllIlIllIIlIl, lllllIlIllIlIIl, lllllIllIIIllIl, lllllIlIllIIlll, 0.0625F, lllllIlIlllllll);
            boolean lllllIlIllIIIll;
            if (lllllIllIIIIIII.renderOutlines) {
               lllllIlIllIIIll = lllllIllIIIIIII.setScoreTeamColor(lllllIlIlllllll);
               lllllIllIIIIIII.renderModel(lllllIlIlllllll, lllllIlIllIIlII, lllllIlIllIIlIl, lllllIlIllIlIIl, lllllIllIIIllIl, lllllIlIllIIlll, 0.0625F);
               if (lllllIlIllIIIll) {
                  lllllIllIIIIIII.unsetScoreTeamColor();
               }
            } else {
               lllllIlIllIIIll = lllllIllIIIIIII.setDoRenderBrightness(lllllIlIlllllll, lllllIlIllllIlI);
               lllllIllIIIIIII.renderModel(lllllIlIlllllll, lllllIlIllIIlII, lllllIlIllIIlIl, lllllIlIllIlIIl, lllllIllIIIllIl, lllllIlIllIIlll, 0.0625F);
               if (lllllIlIllIIIll) {
                  lllllIllIIIIIII.unsetBrightness();
               }

               GlStateManager.depthMask(true);
               if (!(lllllIlIlllllll instanceof EntityPlayer) || !((EntityPlayer)lllllIlIlllllll).isSpectator()) {
                  lllllIllIIIIIII.renderLayers(lllllIlIlllllll, lllllIlIllIIlII, lllllIlIllIIlIl, lllllIlIllllIlI, lllllIlIllIlIIl, lllllIllIIIllIl, lllllIlIllIIlll, 0.0625F);
               }
            }

            GlStateManager.disableRescaleNormal();
         } catch (Exception var28) {
            logger.error("Couldn't render entity", var28);
         }

         if (lllllIlIlllIIIl.getState() && (Boolean)lllllIlIlllIIIl.getTargetsValue().get() && EntityUtils.isSelected(lllllIlIlllllll, false)) {
            GL11.glPolygonOffset(1.0F, 1000000.0F);
            GL11.glDisable(32823);
         }

         GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
         GlStateManager.enableTexture2D();
         GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
         GlStateManager.enableCull();
         GlStateManager.popMatrix();
         if (!lllllIllIIIIIII.renderOutlines) {
            super.doRender(lllllIlIlllllll, lllllIlIllllllI, lllllIlIlllIlIl, lllllIlIlllllII, lllllIlIllllIll, lllllIlIllllIlI);
         }

         MinecraftForge.EVENT_BUS.post(new Post(lllllIlIlllllll, (RendererLivingEntity)lllllIllIIIIIII, lllllIlIllllllI, lllllIlIlllIlIl, lllllIlIlllllII));
         boolean var10001 = false;
      }

      if (lllllIlIlllIIIl.getState() && (Boolean)lllllIlIlllIIIl.getTargetsValue().get() && EntityUtils.isSelected(lllllIlIlllllll, false)) {
         GL11.glPolygonOffset(1.0F, 1000000.0F);
         GL11.glDisable(32823);
      }

   }

   @Overwrite
   protected <T extends EntityLivingBase> void renderModel(T lllllIlIIlllIII, float lllllIlIIllIlll, float lllllIlIIllIllI, float lllllIlIlIIIIlI, float lllllIlIIllIlII, float lllllIlIlIIIIII, float lllllIlIIllIIlI) {
      boolean lllllIlIIlllllI = !lllllIlIIlllIII.isInvisible();
      short lllllIlIIllIIII = (TrueSight)LiquidBounce.moduleManager.getModule(TrueSight.class);
      boolean lllllIlIIllllII = !lllllIlIIlllllI && (!lllllIlIIlllIII.isInvisibleToPlayer(Minecraft.getMinecraft().thePlayer) || lllllIlIIllIIII.getState() && (Boolean)lllllIlIIllIIII.getEntitiesValue().get());
      Chams lllllIlIIlllIll = (Chams)LiquidBounce.moduleManager.getModule(Chams.class);
      Color lllllIlIIlllIlI = (Boolean)lllllIlIIlllIll.getRainbow().get() ? ColorUtils.rainbow() : new Color(!Minecraft.getMinecraft().thePlayer.canEntityBeSeen(lllllIlIIlllIII) ? ChamsColor.red2 : ChamsColor.red, !Minecraft.getMinecraft().thePlayer.canEntityBeSeen(lllllIlIIlllIII) ? ChamsColor.green2 : ChamsColor.green, !Minecraft.getMinecraft().thePlayer.canEntityBeSeen(lllllIlIIlllIII) ? ChamsColor.blue2 : ChamsColor.blue, !Minecraft.getMinecraft().thePlayer.canEntityBeSeen(lllllIlIIlllIII) ? ChamsColor.Apl2 : ChamsColor.Apl);
      if (lllllIlIIlllllI || lllllIlIIllllII) {
         if (!lllllIlIlIIIllI.bindEntityTexture(lllllIlIIlllIII)) {
            return;
         }

         if (lllllIlIIllllII) {
            GlStateManager.pushMatrix();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 0.15F);
            GlStateManager.depthMask(false);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.alphaFunc(516, 0.003921569F);
         }

         int lllllIlIIlIllII = (ESP)LiquidBounce.moduleManager.getModule(ESP.class);
         if (lllllIlIIlIllII.getState() && EntityUtils.isSelected(lllllIlIIlllIII, false)) {
            Exception lllllIlIIlIlIll = Minecraft.getMinecraft();
            short lllllIlIIlIlIlI = lllllIlIIlIlIll.gameSettings.fancyGraphics;
            lllllIlIIlIlIll.gameSettings.fancyGraphics = false;
            float lllllIlIlIIlIII = lllllIlIIlIlIll.gameSettings.gammaSetting;
            lllllIlIIlIlIll.gameSettings.gammaSetting = 100000.0F;
            Exception lllllIlIIlIlIII = ((String)lllllIlIIlIllII.modeValue.get()).toLowerCase();
            int lllllIlIIlIIlll = -1;
            switch(lllllIlIIlIlIII.hashCode()) {
            case -1106245566:
               if (lllllIlIIlIlIII.equals("outline")) {
                  lllllIlIIlIIlll = 1;
               }
               break;
            case -941784056:
               if (lllllIlIIlIlIII.equals("wireframe")) {
                  lllllIlIIlIIlll = 0;
               }
            }

            switch(lllllIlIIlIIlll) {
            case 0:
               GL11.glPushMatrix();
               GL11.glPushAttrib(1048575);
               GL11.glPolygonMode(1032, 6913);
               GL11.glDisable(3553);
               GL11.glDisable(2896);
               GL11.glDisable(2929);
               GL11.glEnable(2848);
               GL11.glEnable(3042);
               GL11.glBlendFunc(770, 771);
               RenderUtils.glColor(lllllIlIIlIllII.getColor(lllllIlIIlllIII));
               GL11.glLineWidth((Float)lllllIlIIlIllII.wireframeWidth.get());
               lllllIlIlIIIllI.mainModel.render(lllllIlIIlllIII, lllllIlIIllIlll, lllllIlIIllIllI, lllllIlIlIIIIlI, lllllIlIIllIlII, lllllIlIlIIIIII, lllllIlIIllIIlI);
               GL11.glPopAttrib();
               GL11.glPopMatrix();
               break;
            case 1:
               ClientUtils.disableFastRender();
               GlStateManager.resetColor();
               float lllllIlIIlIIllI = lllllIlIIlIllII.getColor(lllllIlIIlllIII);
               OutlineUtils.setColor(lllllIlIIlIIllI);
               OutlineUtils.renderOne((Float)lllllIlIIlIllII.outlineWidth.get());
               lllllIlIlIIIllI.mainModel.render(lllllIlIIlllIII, lllllIlIIllIlll, lllllIlIIllIllI, lllllIlIlIIIIlI, lllllIlIIllIlII, lllllIlIlIIIIII, lllllIlIIllIIlI);
               OutlineUtils.setColor(lllllIlIIlIIllI);
               OutlineUtils.renderTwo();
               lllllIlIlIIIllI.mainModel.render(lllllIlIIlllIII, lllllIlIIllIlll, lllllIlIIllIllI, lllllIlIlIIIIlI, lllllIlIIllIlII, lllllIlIlIIIIII, lllllIlIIllIIlI);
               OutlineUtils.setColor(lllllIlIIlIIllI);
               OutlineUtils.renderThree();
               lllllIlIlIIIllI.mainModel.render(lllllIlIIlllIII, lllllIlIIllIlll, lllllIlIIllIllI, lllllIlIlIIIIlI, lllllIlIIllIlII, lllllIlIlIIIIII, lllllIlIIllIIlI);
               OutlineUtils.setColor(lllllIlIIlIIllI);
               OutlineUtils.renderFour(lllllIlIIlIIllI);
               lllllIlIlIIIllI.mainModel.render(lllllIlIIlllIII, lllllIlIIllIlll, lllllIlIIllIllI, lllllIlIlIIIIlI, lllllIlIIllIlII, lllllIlIlIIIIII, lllllIlIIllIIlI);
               OutlineUtils.setColor(lllllIlIIlIIllI);
               OutlineUtils.renderFive();
               OutlineUtils.setColor(Color.WHITE);
            }

            lllllIlIIlIlIll.gameSettings.fancyGraphics = lllllIlIIlIlIlI;
            lllllIlIIlIlIll.gameSettings.gammaSetting = lllllIlIlIIlIII;
         }

         if (lllllIlIIlllIll.getState()) {
            if (lllllIlIIlllIll.getState()) {
               GL11.glPushMatrix();
               GL11.glPushAttrib(1048575);
               GL11.glDisable(2929);
               GL11.glDisable(3553);
               GL11.glEnable(3042);
               GL11.glBlendFunc(770, 771);
               GL11.glDisable(2896);
               GL11.glPolygonMode(1032, 6914);
               RenderUtils.chamsColor(lllllIlIIlllIlI);
               lllllIlIlIIIllI.mainModel.render(lllllIlIIlllIII, lllllIlIIllIlll, lllllIlIIllIllI, lllllIlIlIIIIlI, lllllIlIIllIlII, lllllIlIlIIIIII, lllllIlIIllIIlI);
               GL11.glEnable(2896);
               GL11.glDisable(3042);
               GL11.glEnable(3553);
               GL11.glEnable(2929);
               GL11.glColor3d(1.0D, 1.0D, 1.0D);
               GL11.glPopAttrib();
               GL11.glPopMatrix();
            }
         } else {
            lllllIlIlIIIllI.mainModel.render(lllllIlIIlllIII, lllllIlIIllIlll, lllllIlIIllIllI, lllllIlIlIIIIlI, lllllIlIIllIlII, lllllIlIlIIIIII, lllllIlIIllIIlI);
         }

         if (lllllIlIIllllII) {
            GlStateManager.disableBlend();
            GlStateManager.alphaFunc(516, 0.1F);
            GlStateManager.popMatrix();
            GlStateManager.depthMask(true);
         }
      }

   }

   @Shadow
   public abstract <T extends EntityLivingBase> boolean setDoRenderBrightness(T var1, float var2);

   @Shadow
   public abstract <T extends EntityLivingBase> void preRenderCallback(T var1, float var2);

   @Shadow
   public abstract void unsetBrightness();
}
