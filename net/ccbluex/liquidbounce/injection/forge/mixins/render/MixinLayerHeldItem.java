//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@SideOnly(Side.CLIENT)
@Mixin({LayerHeldItem.class})
public class MixinLayerHeldItem {
   // $FF: synthetic field
   @Shadow
   @Final
   private RendererLivingEntity<?> livingEntityRenderer;

   @Overwrite
   public void doRenderLayer(EntityLivingBase lllllllllllllllllllllIIIlIlIIlIl, float lllllllllllllllllllllIIIlIlIlllI, float lllllllllllllllllllllIIIlIlIllIl, float lllllllllllllllllllllIIIlIlIllII, float lllllllllllllllllllllIIIlIlIlIll, float lllllllllllllllllllllIIIlIlIlIlI, float lllllllllllllllllllllIIIlIlIlIIl, float lllllllllllllllllllllIIIlIlIlIII) {
      ItemStack lllllllllllllllllllllIIIlIlIIlll = lllllllllllllllllllllIIIlIlIIlIl.getHeldItem();
      if (lllllllllllllllllllllIIIlIlIIlll != null) {
         GlStateManager.pushMatrix();
         if (lllllllllllllllllllllIIIlIllIIII.livingEntityRenderer.getMainModel().isChild) {
            Exception lllllllllllllllllllllIIIlIlIIIll = 0.5F;
            GlStateManager.translate(0.0F, 0.625F, 0.0F);
            GlStateManager.rotate(-20.0F, -1.0F, 0.0F, 0.0F);
            GlStateManager.scale(lllllllllllllllllllllIIIlIlIIIll, lllllllllllllllllllllIIIlIlIIIll, lllllllllllllllllllllIIIlIlIIIll);
         }

         Exception lllllllllllllllllllllIIIlIlIIIll = lllllllllllllllllllllIIIlIlIIlIl.getUniqueID();
         EntityPlayer lllllllllllllllllllllIIIlIllIIll = Minecraft.getMinecraft().theWorld.getPlayerEntityByUUID(lllllllllllllllllllllIIIlIlIIIll);
         if (lllllllllllllllllllllIIIlIllIIll != null && lllllllllllllllllllllIIIlIllIIll.isBlocking()) {
            if (lllllllllllllllllllllIIIlIlIIlIl.isSneaking()) {
               ((ModelBiped)lllllllllllllllllllllIIIlIllIIII.livingEntityRenderer.getMainModel()).postRenderArm(0.0325F);
               GlStateManager.translate(-0.58F, 0.3F, -0.2F);
               GlStateManager.rotate(-24390.0F, 137290.0F, -2009900.0F, -2054900.0F);
            } else {
               ((ModelBiped)lllllllllllllllllllllIIIlIllIIII.livingEntityRenderer.getMainModel()).postRenderArm(0.0325F);
               GlStateManager.translate(-0.48F, 0.2F, -0.2F);
               GlStateManager.rotate(-24390.0F, 137290.0F, -2009900.0F, -2054900.0F);
            }
         } else {
            ((ModelBiped)lllllllllllllllllllllIIIlIllIIII.livingEntityRenderer.getMainModel()).postRenderArm(0.0625F);
         }

         GlStateManager.translate(-0.0625F, 0.4375F, 0.0625F);
         if (lllllllllllllllllllllIIIlIlIIlIl instanceof EntityPlayer && ((EntityPlayer)lllllllllllllllllllllIIIlIlIIlIl).fishEntity != null) {
            lllllllllllllllllllllIIIlIlIIlll = new ItemStack(Items.fishing_rod, 0);
         }

         float lllllllllllllllllllllIIIlIlIIIIl = lllllllllllllllllllllIIIlIlIIlll.getItem();
         Minecraft lllllllllllllllllllllIIIlIllIIIl = Minecraft.getMinecraft();
         if (lllllllllllllllllllllIIIlIlIIIIl instanceof ItemBlock && Block.getBlockFromItem(lllllllllllllllllllllIIIlIlIIIIl).getRenderType() == 2) {
            GlStateManager.translate(0.0F, 0.1875F, -0.3125F);
            GlStateManager.rotate(20.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
            float lllllllllllllllllllllIIIlIllIlIl = 0.375F;
            GlStateManager.scale(-lllllllllllllllllllllIIIlIllIlIl, -lllllllllllllllllllllIIIlIllIlIl, lllllllllllllllllllllIIIlIllIlIl);
         }

         if (lllllllllllllllllllllIIIlIlIIlIl.isSneaking()) {
            GlStateManager.translate(0.0F, 0.203125F, 0.0F);
         }

         lllllllllllllllllllllIIIlIllIIIl.getItemRenderer().renderItem(lllllllllllllllllllllIIIlIlIIlIl, lllllllllllllllllllllIIIlIlIIlll, TransformType.THIRD_PERSON);
         GlStateManager.popMatrix();
      }

   }
}
