//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.injection.forge.mixins.item;

import java.awt.Color;
import me.AquaVit.liquidSense.modules.render.EnchantEffect;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@SideOnly(Side.CLIENT)
@Mixin({RenderItem.class})
public abstract class MixinRenderItem implements IResourceManagerReloadListener {
   // $FF: synthetic field
   @Shadow
   private TextureManager textureManager;
   // $FF: synthetic field
   @Shadow
   private static final ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");

   @Shadow
   public abstract void onResourceManagerReload(IResourceManager var1);

   @Shadow
   protected abstract void renderModel(IBakedModel var1, int var2);

   @Overwrite
   private void renderEffect(IBakedModel lIlIlIIlIlIlII) {
      EnchantEffect lIlIlIIlIllIIl = (EnchantEffect)LiquidBounce.moduleManager.get(EnchantEffect.class);
      Color lIlIlIIlIllIII = (Boolean)lIlIlIIlIllIIl.getRainbow().get() ? ColorUtils.rainbow() : new Color((Integer)lIlIlIIlIllIIl.getRedValue().get(), (Integer)lIlIlIIlIllIIl.getGreenValue().get(), (Integer)lIlIlIIlIllIIl.getBlueValue().get(), (Integer)lIlIlIIlIllIIl.getalphaValue().get());
      GlStateManager.depthMask(false);
      GlStateManager.depthFunc(514);
      GlStateManager.disableLighting();
      GlStateManager.blendFunc(768, 1);
      lIlIlIIlIlIlIl.textureManager.bindTexture(RES_ITEM_GLINT);
      GlStateManager.matrixMode(5890);
      GlStateManager.pushMatrix();
      GlStateManager.scale(8.0F, 8.0F, 8.0F);
      float lIlIlIIlIlIlll = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F / 8.0F;
      GlStateManager.translate(lIlIlIIlIlIlll, 0.0F, 0.0F);
      GlStateManager.rotate(-50.0F, 0.0F, 0.0F, 1.0F);
      if (lIlIlIIlIllIIl.getState()) {
         lIlIlIIlIlIlIl.renderModel(lIlIlIIlIlIlII, lIlIlIIlIllIII.getRGB());
      } else {
         lIlIlIIlIlIlIl.renderModel(lIlIlIIlIlIlII, -8372020);
      }

      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.scale(8.0F, 8.0F, 8.0F);
      long lIlIlIIlIlIIII = (float)(Minecraft.getSystemTime() % 4873L) / 4873.0F / 8.0F;
      GlStateManager.translate(-lIlIlIIlIlIIII, 0.0F, 0.0F);
      GlStateManager.rotate(10.0F, 0.0F, 0.0F, 1.0F);
      if (lIlIlIIlIllIIl.getState()) {
         lIlIlIIlIlIlIl.renderModel(lIlIlIIlIlIlII, lIlIlIIlIllIII.getRGB());
      } else {
         lIlIlIIlIlIlIl.renderModel(lIlIlIIlIlIlII, -8372020);
      }

      GlStateManager.popMatrix();
      GlStateManager.matrixMode(5888);
      GlStateManager.blendFunc(770, 771);
      GlStateManager.enableLighting();
      GlStateManager.depthFunc(515);
      GlStateManager.depthMask(true);
      lIlIlIIlIlIlIl.textureManager.bindTexture(TextureMap.locationBlocksTexture);
   }
}
