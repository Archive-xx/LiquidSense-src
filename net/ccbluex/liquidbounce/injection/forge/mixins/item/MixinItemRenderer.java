//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.injection.forge.mixins.item;

import me.AquaVit.liquidSense.modules.misc.Animations;
import me.AquaVit.liquidSense.modules.render.EveryThingBlock;
import me.AquaVit.liquidSense.modules.render.ItemRotate;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.combat.Aura;
import net.ccbluex.liquidbounce.features.module.modules.render.AntiBlind;
import net.ccbluex.liquidbounce.features.module.modules.render.SwingAnimation;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SideOnly(Side.CLIENT)
@Mixin({ItemRenderer.class})
public abstract class MixinItemRenderer {
   // $FF: synthetic field
   @Shadow
   private float equippedProgress;
   // $FF: synthetic field
   int f3 = 0;
   // $FF: synthetic field
   @Shadow
   @Final
   private Minecraft mc;
   // $FF: synthetic field
   float delay = 0.0F;
   // $FF: synthetic field
   MSTimer rotationTimer = new MSTimer();
   // $FF: synthetic field
   private static int ticks;
   // $FF: synthetic field
   @Shadow
   private ItemStack itemToRender;
   // $FF: synthetic field
   @Shadow
   private float prevEquippedProgress;

   @Shadow
   protected abstract void doItemUsedTransformations(float var1);

   private void func_178096_A(float lllllllllllllllllllIlIIlIIllIllI, float lllllllllllllllllllIlIIlIIllIIII) {
      GlStateManager.translate((Float)Animations.itemPosX.get(), (Float)Animations.itemPosY.get(), (Float)Animations.itemPosZ.get());
      GlStateManager.translate(0.62F, -0.66F, -0.71999997F);
      GlStateManager.translate(0.0F, lllllllllllllllllllIlIIlIIllIllI * -0.6F, 0.0F);
      GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
      byte lllllllllllllllllllIlIIlIIlIllll = MathHelper.sin(lllllllllllllllllllIlIIlIIllIIII * lllllllllllllllllllIlIIlIIllIIII * 3.1415927F);
      long lllllllllllllllllllIlIIlIIlIllIl = MathHelper.sin(MathHelper.sqrt_float(lllllllllllllllllllIlIIlIIllIIII) * 3.1415927F);
      GlStateManager.rotate(lllllllllllllllllllIlIIlIIlIllll * -20.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(lllllllllllllllllllIlIIlIIlIllIl * -20.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.rotate(lllllllllllllllllllIlIIlIIlIllIl * -80.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.scale(0.4F, 0.4F, 0.4F);
      GlStateManager.scale((Float)Animations.Scale.get(), (Float)Animations.Scale.get(), (Float)Animations.Scale.get());
   }

   @Shadow
   protected abstract void rotateArroundXAndY(float var1, float var2);

   private void transformFirstPersonItem1(float lllllllllllllllllllIlIIlIlllIIII, float lllllllllllllllllllIlIIlIlllIIll) {
      GlStateManager.translate((Float)Animations.itemPosX.get(), (Float)Animations.itemPosY.get(), (Float)Animations.itemPosZ.get());
      GlStateManager.translate(0.56F, -0.52F, -0.71999997F);
      GlStateManager.translate(0.0F, lllllllllllllllllllIlIIlIlllIIII * -0.6F, 0.0F);
      GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
      float lllllllllllllllllllIlIIlIlllIIlI = MathHelper.sin(lllllllllllllllllllIlIIlIlllIIll * lllllllllllllllllllIlIIlIlllIIll * 3.1415927F);
      float lllllllllllllllllllIlIIlIlllIIIl = MathHelper.sin(MathHelper.sqrt_float(lllllllllllllllllllIlIIlIlllIIll) * 3.1415927F);
      GlStateManager.rotate(lllllllllllllllllllIlIIlIlllIIlI * -20.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(lllllllllllllllllllIlIIlIlllIIIl * -20.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.rotate(lllllllllllllllllllIlIIlIlllIIIl * -80.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.scale(0.4F, 0.4F, 0.4F);
      GlStateManager.scale((Float)Animations.Scale.get(), (Float)Animations.Scale.get(), (Float)Animations.Scale.get());
   }

   private void doBlockTransformations2() {
      GlStateManager.translate((Float)Animations.itemPosX.get(), (Float)Animations.itemPosY.get(), (Float)Animations.itemPosZ.get());
      GlStateManager.translate(-0.5F, 0.2F, 0.0F);
      GlStateManager.rotate(30.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(-80.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.rotate(60.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.scale((Float)Animations.Scale.get(), (Float)Animations.Scale.get(), (Float)Animations.Scale.get());
   }

   private void emilio(float lllllllllllllllllllIlIIIlllIIIll, float lllllllllllllllllllIlIIIlllIIIlI) {
      GlStateManager.translate((Float)Animations.itemPosX.get(), (Float)Animations.itemPosY.get(), (Float)Animations.itemPosZ.get());
      long lllllllllllllllllllIlIIIllIlllIl = lllllllllllllllllllIlIIIlllIIIlI * 0.78F - lllllllllllllllllllIlIIIlllIIIlI * lllllllllllllllllllIlIIIlllIIIlI * 0.78F;
      GlStateManager.scale(1.7F, 1.7F, 1.7F);
      GlStateManager.rotate(48.0F, 0.0F, -0.6F, 0.0F);
      GlStateManager.translate(-0.3F, 0.4F, 0.0F);
      GlStateManager.translate(0.0F, 0.08F, 0.0F);
      GlStateManager.translate(0.56F, -0.489F, -0.71999997F);
      GlStateManager.translate(0.0F, 0.0F, 0.0F);
      GlStateManager.rotate(52.0F, 0.0F, 180.0F + lllllllllllllllllllIlIIIllIlllIl * 0.5F, lllllllllllllllllllIlIIIllIlllIl * 20.0F);
      float lllllllllllllllllllIlIIIlllIIIII = MathHelper.sin(lllllllllllllllllllIlIIIlllIIIlI * lllllllllllllllllllIlIIIlllIIIlI * 3.1415927F);
      long lllllllllllllllllllIlIIIllIllIlI = MathHelper.sin(MathHelper.sqrt_float(lllllllllllllllllllIlIIIlllIIIlI) * 3.1415927F);
      GlStateManager.rotate(lllllllllllllllllllIlIIIllIllIlI * -51.3F, 2.0F, 0.0F, 0.0F);
      GlStateManager.translate(0.0F, -0.2F, 0.0F);
      GlStateManager.scale((Float)Animations.Scale.get(), (Float)Animations.Scale.get(), (Float)Animations.Scale.get());
   }

   private void Jigsaw(float lllllllllllllllllllIlIIlIIIIllII, float lllllllllllllllllllIlIIlIIIIlIlI) {
      GlStateManager.translate((Float)Animations.itemPosX.get(), (Float)Animations.itemPosY.get(), (Float)Animations.itemPosZ.get());
      byte lllllllllllllllllllIlIIlIIIIlIIl = lllllllllllllllllllIlIIlIIIIlIlI * 0.8F - lllllllllllllllllllIlIIlIIIIlIlI * lllllllllllllllllllIlIIlIIIIlIlI * 0.8F;
      GlStateManager.translate(0.56F, -0.52F, -0.71999997F);
      GlStateManager.translate(0.0F, lllllllllllllllllllIlIIlIIIIllII * -0.6F, 0.0F);
      GlStateManager.rotate(45.0F, 0.0F, 2.0F + lllllllllllllllllllIlIIlIIIIlIIl * 0.5F, lllllllllllllllllllIlIIlIIIIlIIl * 3.0F);
      long lllllllllllllllllllIlIIlIIIIlIII = MathHelper.sin(lllllllllllllllllllIlIIlIIIIlIlI * lllllllllllllllllllIlIIlIIIIlIlI * 3.1415927F);
      Exception lllllllllllllllllllIlIIlIIIIIlll = MathHelper.sin(MathHelper.sqrt_float(lllllllllllllllllllIlIIlIIIIlIlI) * 3.1415927F);
      GlStateManager.rotate(0.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.scale(0.37F, 0.37F, 0.37F);
      GlStateManager.scale((Float)Animations.Scale.get(), (Float)Animations.Scale.get(), (Float)Animations.Scale.get());
   }

   @Shadow
   protected abstract void rotateWithPlayerRotations(EntityPlayerSP var1, float var2);

   @Shadow
   public abstract void renderItem(EntityLivingBase var1, ItemStack var2, TransformType var3);

   @Overwrite
   public void renderItemInFirstPerson(float lllllllllllllllllllIlIIllIlIlIlI) {
      ItemRotate lllllllllllllllllllIlIIllIlIlIIl = (ItemRotate)LiquidBounce.moduleManager.getModule(ItemRotate.class);
      Animations lllllllllllllllllllIlIIllIlIlIII = (Animations)LiquidBounce.moduleManager.getModule(Animations.class);
      float lllllllllllllllllllIlIIllIlIIlll = 1.0F - (lllllllllllllllllllIlIIllIlIlIll.prevEquippedProgress + (lllllllllllllllllllIlIIllIlIlIll.equippedProgress - lllllllllllllllllllIlIIllIlIlIll.prevEquippedProgress) * lllllllllllllllllllIlIIllIlIlIlI);
      AbstractClientPlayer lllllllllllllllllllIlIIllIlIIllI = lllllllllllllllllllIlIIllIlIlIll.mc.thePlayer;
      char lllllllllllllllllllIlIIllIIlllII = lllllllllllllllllllIlIIllIlIIllI.getSwingProgress(lllllllllllllllllllIlIIllIlIlIlI);
      float lllllllllllllllllllIlIIllIlIIlII = lllllllllllllllllllIlIIllIlIIllI.prevRotationPitch + (lllllllllllllllllllIlIIllIlIIllI.rotationPitch - lllllllllllllllllllIlIIllIlIIllI.prevRotationPitch) * lllllllllllllllllllIlIIllIlIlIlI;
      float lllllllllllllllllllIlIIllIlIIIll = lllllllllllllllllllIlIIllIlIIllI.prevRotationYaw + (lllllllllllllllllllIlIIllIlIIllI.rotationYaw - lllllllllllllllllllIlIIllIlIIllI.prevRotationYaw) * lllllllllllllllllllIlIIllIlIlIlI;
      lllllllllllllllllllIlIIllIlIlIll.rotateArroundXAndY(lllllllllllllllllllIlIIllIlIIlII, lllllllllllllllllllIlIIllIlIIIll);
      lllllllllllllllllllIlIIllIlIlIll.setLightMapFromPlayer(lllllllllllllllllllIlIIllIlIIllI);
      lllllllllllllllllllIlIIllIlIlIll.rotateWithPlayerRotations((EntityPlayerSP)lllllllllllllllllllIlIIllIlIIllI, lllllllllllllllllllIlIIllIlIlIlI);
      GlStateManager.enableRescaleNormal();
      GlStateManager.pushMatrix();
      if (lllllllllllllllllllIlIIllIlIlIll.itemToRender != null) {
         Aura lllllllllllllllllllIlIIllIlIllIl = (Aura)LiquidBounce.moduleManager.getModule(Aura.class);
         EveryThingBlock lllllllllllllllllllIlIIllIlIllII = (EveryThingBlock)LiquidBounce.moduleManager.getModule(EveryThingBlock.class);
         if (lllllllllllllllllllIlIIllIlIlIll.itemToRender.getItem() instanceof ItemMap) {
            lllllllllllllllllllIlIIllIlIlIll.renderItemMap(lllllllllllllllllllIlIIllIlIIllI, lllllllllllllllllllIlIIllIlIIlII, lllllllllllllllllllIlIIllIlIIlll, lllllllllllllllllllIlIIllIIlllII);
         } else if (lllllllllllllllllllIlIIllIlIIllI.getItemInUseCount() <= 0 && (!(lllllllllllllllllllIlIIllIlIlIll.itemToRender.getItem() instanceof ItemSword) || !lllllllllllllllllllIlIIllIlIllIl.getBlockingStatus())) {
            if (lllllllllllllllllllIlIIllIlIlIII.getState()) {
               if (LiquidBounce.moduleManager.getModule(SwingAnimation.class).getState()) {
                  GlStateManager.translate((Float)Animations.itemPosX.get(), (Float)Animations.itemPosY.get(), (Float)Animations.itemPosZ.get());
                  lllllllllllllllllllIlIIllIlIlIll.transformFirstPersonItem(lllllllllllllllllllIlIIllIlIIlll, lllllllllllllllllllIlIIllIIlllII);
                  GlStateManager.scale((Float)Animations.Scale.get(), (Float)Animations.Scale.get(), (Float)Animations.Scale.get());
               } else if (LiquidBounce.moduleManager.getModule(ItemRotate.class).getState()) {
                  GlStateManager.translate((Float)Animations.itemPosX.get(), (Float)Animations.itemPosY.get(), (Float)Animations.itemPosZ.get());
                  lllllllllllllllllllIlIIllIlIlIll.transformFirstPersonItem(lllllllllllllllllllIlIIllIlIIlll, lllllllllllllllllllIlIIllIIlllII);
                  ItemRotate.ItemRenderRotate();
                  GlStateManager.scale((Float)Animations.Scale.get(), (Float)Animations.Scale.get(), (Float)Animations.Scale.get());
               } else if (LiquidBounce.moduleManager.getModule(EveryThingBlock.class).getState()) {
                  GlStateManager.translate((Float)Animations.itemPosX.get(), (Float)Animations.itemPosY.get(), (Float)Animations.itemPosZ.get());
                  lllllllllllllllllllIlIIllIlIlIll.transformFirstPersonItem(lllllllllllllllllllIlIIllIlIIlll, lllllllllllllllllllIlIIllIIlllII);
                  if (lllllllllllllllllllIlIIllIlIlIll.mc.gameSettings.keyBindUseItem.isKeyDown()) {
                     lllllllllllllllllllIlIIllIlIlIll.doBlockTransformations();
                     GL11.glTranslated(((Float)lllllllllllllllllllIlIIllIlIllII.getX().get()).doubleValue(), ((Float)lllllllllllllllllllIlIIllIlIllII.getY().get()).doubleValue(), ((Float)lllllllllllllllllllIlIIllIlIllII.getZ().get()).doubleValue());
                  }

                  GlStateManager.scale((Float)Animations.Scale.get(), (Float)Animations.Scale.get(), (Float)Animations.Scale.get());
               } else {
                  GlStateManager.translate((Float)Animations.itemPosX.get(), (Float)Animations.itemPosY.get(), (Float)Animations.itemPosZ.get());
                  lllllllllllllllllllIlIIllIlIlIll.doItemUsedTransformations(lllllllllllllllllllIlIIllIIlllII);
                  lllllllllllllllllllIlIIllIlIlIll.transformFirstPersonItem(lllllllllllllllllllIlIIllIlIIlll, lllllllllllllllllllIlIIllIIlllII);
                  GlStateManager.scale((Float)Animations.Scale.get(), (Float)Animations.Scale.get(), (Float)Animations.Scale.get());
               }
            } else if (LiquidBounce.moduleManager.getModule(SwingAnimation.class).getState()) {
               lllllllllllllllllllIlIIllIlIlIll.transformFirstPersonItem(lllllllllllllllllllIlIIllIlIIlll, lllllllllllllllllllIlIIllIIlllII);
            } else if (LiquidBounce.moduleManager.getModule(ItemRotate.class).getState()) {
               lllllllllllllllllllIlIIllIlIlIll.transformFirstPersonItem(lllllllllllllllllllIlIIllIlIIlll, lllllllllllllllllllIlIIllIIlllII);
               ItemRotate.ItemRenderRotate();
            } else if (LiquidBounce.moduleManager.getModule(EveryThingBlock.class).getState()) {
               lllllllllllllllllllIlIIllIlIlIll.transformFirstPersonItem(lllllllllllllllllllIlIIllIlIIlll, lllllllllllllllllllIlIIllIIlllII);
               if (lllllllllllllllllllIlIIllIlIlIll.mc.gameSettings.keyBindUseItem.isKeyDown()) {
                  lllllllllllllllllllIlIIllIlIlIll.doBlockTransformations();
                  GL11.glTranslated(((Float)lllllllllllllllllllIlIIllIlIllII.getX().get()).doubleValue(), ((Float)lllllllllllllllllllIlIIllIlIllII.getY().get()).doubleValue(), ((Float)lllllllllllllllllllIlIIllIlIllII.getZ().get()).doubleValue());
               }
            } else {
               lllllllllllllllllllIlIIllIlIlIll.doItemUsedTransformations(lllllllllllllllllllIlIIllIIlllII);
               lllllllllllllllllllIlIIllIlIlIll.transformFirstPersonItem(lllllllllllllllllllIlIIllIlIIlll, lllllllllllllllllllIlIIllIIlllII);
            }
         } else {
            String lllllllllllllllllllIlIIllIIlIlll = lllllllllllllllllllIlIIllIlIllIl.getBlockingStatus() ? EnumAction.BLOCK : lllllllllllllllllllIlIIllIlIlIll.itemToRender.getItemUseAction();
            label226:
            switch(lllllllllllllllllllIlIIllIIlIlll) {
            case NONE:
               lllllllllllllllllllIlIIllIlIlIll.transformFirstPersonItem(lllllllllllllllllllIlIIllIlIIlll, 0.0F);
               break;
            case EAT:
            case DRINK:
               lllllllllllllllllllIlIIllIlIlIll.performDrinking(lllllllllllllllllllIlIIllIlIIllI, lllllllllllllllllllIlIIllIlIlIlI);
               lllllllllllllllllllIlIIllIlIlIll.transformFirstPersonItem(lllllllllllllllllllIlIIllIlIIlll, lllllllllllllllllllIlIIllIIlllII);
               break;
            case BLOCK:
               if (lllllllllllllllllllIlIIllIlIlIII.getState()) {
                  float lllllllllllllllllllIlIIllIllIIll = 1.0F - (lllllllllllllllllllIlIIllIlIlIll.prevEquippedProgress + (lllllllllllllllllllIlIIllIlIlIll.equippedProgress - lllllllllllllllllllIlIIllIlIlIll.prevEquippedProgress) * lllllllllllllllllllIlIIllIlIlIlI);
                  float lllllllllllllllllllIlIIllIllIIlI = lllllllllllllllllllIlIIllIlIlIll.mc.thePlayer.getSwingProgress(lllllllllllllllllllIlIIllIlIlIlI);
                  char lllllllllllllllllllIlIIllIIlIlII = MathHelper.sin(MathHelper.sqrt_float(lllllllllllllllllllIlIIllIllIIlI) * 3.1415927F);
                  float var10000 = lllllllllllllllllllIlIIllIlIlIll.mc.thePlayer.prevRotationPitch + (lllllllllllllllllllIlIIllIlIlIll.mc.thePlayer.rotationPitch - lllllllllllllllllllIlIIllIlIlIll.mc.thePlayer.prevRotationPitch) * lllllllllllllllllllIlIIllIlIlIlI;
                  var10000 = lllllllllllllllllllIlIIllIlIlIll.mc.thePlayer.prevRotationYaw + (lllllllllllllllllllIlIIllIlIlIll.mc.thePlayer.rotationYaw - lllllllllllllllllllIlIIllIlIlIll.mc.thePlayer.prevRotationYaw) * lllllllllllllllllllIlIIllIlIlIlI;
                  boolean lllllllllllllllllllIlIIllIIlIIIl = (String)lllllllllllllllllllIlIIllIlIlIII.getModeValue().get();
                  String lllllllllllllllllllIlIIllIIlIIII = -1;
                  switch(lllllllllllllllllllIlIIllIIlIIIl.hashCode()) {
                  case -1805854619:
                     if (lllllllllllllllllllIlIIllIIlIIIl.equals("Swaing")) {
                        lllllllllllllllllllIlIIllIIlIIII = 14;
                     }
                     break;
                  case -1050807228:
                     if (lllllllllllllllllllIlIIllIIlIIIl.equals("Windmill")) {
                        lllllllllllllllllllIlIIllIIlIIII = 16;
                     }
                     break;
                  case -6873077:
                     if (lllllllllllllllllllIlIIllIIlIIIl.equals("SigmaOther")) {
                        lllllllllllllllllllIlIIllIIlIIII = 13;
                     }
                     break;
                  case 72336:
                     if (lllllllllllllllllllIlIIllIIlIIIl.equals("IDK")) {
                        lllllllllllllllllllIlIIllIIlIIII = 15;
                     }
                     break;
                  case 2393456:
                     if (lllllllllllllllllllIlIIllIIlIIIl.equals("MeMe")) {
                        lllllllllllllllllllIlIIllIIlIIII = 9;
                     }
                     break;
                  case 2499386:
                     if (lllllllllllllllllllIlIIllIIlIIIl.equals("Push")) {
                        lllllllllllllllllllIlIIllIIlIIII = 17;
                     }
                     break;
                  case 71456692:
                     if (lllllllllllllllllllIlIIllIIlIIIl.equals("Jello")) {
                        lllllllllllllllllllIlIIllIIlIIII = 12;
                     }
                     break;
                  case 78845737:
                     if (lllllllllllllllllllIlIIllIIlIIIl.equals("Remix")) {
                        lllllllllllllllllllIlIIllIIlIIII = 5;
                     }
                     break;
                  case 79714356:
                     if (lllllllllllllllllllIlIIllIIlIIIl.equals("Screw")) {
                        lllllllllllllllllllIlIIllIIlIIII = 3;
                     }
                     break;
                  case 79882757:
                     if (lllllllllllllllllllIlIIllIIlIIIl.equals("Sigma")) {
                        lllllllllllllllllllIlIIllIIlIIII = 4;
                     }
                     break;
                  case 79973777:
                     if (lllllllllllllllllllIlIIllIIlIIIl.equals("Slide")) {
                        lllllllllllllllllllIlIIllIIlIIII = 10;
                     }
                     break;
                  case 80294102:
                     if (lllllllllllllllllllIlIIllIIlIIIl.equals("Swang")) {
                        lllllllllllllllllllIlIIllIIlIIII = 6;
                     }
                     break;
                  case 80294106:
                     if (lllllllllllllllllllIlIIllIIlIIIl.equals("Swank")) {
                        lllllllllllllllllllIlIIllIIlIIII = 8;
                     }
                     break;
                  case 80301790:
                     if (lllllllllllllllllllIlIIllIIlIIIl.equals("Swing")) {
                        lllllllllllllllllllIlIIllIIlIIII = 11;
                     }
                     break;
                  case 80307556:
                     if (lllllllllllllllllllIlIIllIIlIIIl.equals("Swong")) {
                        lllllllllllllllllllIlIIllIIlIIII = 7;
                     }
                     break;
                  case 754468562:
                     if (lllllllllllllllllllIlIIllIIlIIIl.equals("Rotate360")) {
                        lllllllllllllllllllIlIIllIIlIIII = 2;
                     }
                     break;
                  case 1220567030:
                     if (lllllllllllllllllllIlIIllIIlIIIl.equals("PushOther")) {
                        lllllllllllllllllllIlIIllIIlIIII = 0;
                     }
                     break;
                  case 1267843374:
                     if (lllllllllllllllllllIlIIllIIlIIIl.equals("SmoothFloat")) {
                        lllllllllllllllllllIlIIllIIlIIII = 1;
                     }
                  }

                  switch(lllllllllllllllllllIlIIllIIlIIII) {
                  case 0:
                     lllllllllllllllllllIlIIllIlIlIll.push(0.1F, lllllllllllllllllllIlIIllIIlllII);
                     if (lllllllllllllllllllIlIIllIlIlIIl.getState()) {
                        ItemRotate.ItemRenderRotate();
                     }

                     lllllllllllllllllllIlIIllIlIlIll.doBlockTransformations2();
                     break label226;
                  case 1:
                     lllllllllllllllllllIlIIllIlIlIll.transformFirstPersonItem(0.0F, 0.95F);
                     Animations.ItemRenderRotation();
                     if (lllllllllllllllllllIlIIllIlIlIIl.getState()) {
                        ItemRotate.ItemRenderRotate();
                     }

                     lllllllllllllllllllIlIIllIlIlIll.doBlockTransformations2();
                     GlStateManager.rotate(lllllllllllllllllllIlIIllIlIlIll.delay, 0.0F, 1.0F, 0.0F);
                     break label226;
                  case 2:
                     lllllllllllllllllllIlIIllIlIlIll.transformFirstPersonItem(0.0F, 0.95F);
                     if (lllllllllllllllllllIlIIllIlIlIIl.getState()) {
                        ItemRotate.ItemRenderRotate();
                     }

                     lllllllllllllllllllIlIIllIlIlIll.doBlockTransformations2();
                     Animations.ItemRenderRotation();
                     break label226;
                  case 3:
                     Animations.renderblock(lllllllllllllllllllIlIIllIlIIlll, lllllllllllllllllllIlIIllIIlllII);
                     if (lllllllllllllllllllIlIIllIlIlIIl.getState()) {
                        ItemRotate.ItemRenderRotate();
                     }
                     break label226;
                  case 4:
                     lllllllllllllllllllIlIIllIlIlIll.genCustom(lllllllllllllllllllIlIIllIllIIll * 0.5F, 0.0F);
                     GlStateManager.rotate(-lllllllllllllllllllIlIIllIIlIlII * 55.0F / 2.0F, -8.0F, -0.0F, 9.0F);
                     GlStateManager.rotate(-lllllllllllllllllllIlIIllIIlIlII * 45.0F, 1.0F, lllllllllllllllllllIlIIllIIlIlII / 2.0F, -0.0F);
                     if (lllllllllllllllllllIlIIllIlIlIIl.getState()) {
                        ItemRotate.ItemRenderRotate();
                     }

                     lllllllllllllllllllIlIIllIlIlIll.doBlockTransformations2();
                     GL11.glTranslated(1.2D, 0.3D, 0.5D);
                     GL11.glTranslatef(-1.0F, lllllllllllllllllllIlIIllIlIlIll.mc.thePlayer.isSneaking() ? -0.1F : -0.2F, 0.2F);
                     GlStateManager.scale(1.2F, 1.2F, 1.2F);
                     break label226;
                  case 5:
                     lllllllllllllllllllIlIIllIlIlIll.genCustom(lllllllllllllllllllIlIIllIlIIlll, 0.83F);
                     if (lllllllllllllllllllIlIIllIlIlIIl.getState()) {
                        ItemRotate.ItemRenderRotate();
                     }

                     lllllllllllllllllllIlIIllIlIlIll.doBlockTransformations2();
                     float lllllllllllllllllllIlIIllIllllII = MathHelper.sin(MathHelper.sqrt_float(lllllllllllllllllllIlIIllIIlllII) * 3.83F);
                     GlStateManager.translate(-0.5F, 0.2F, 0.2F);
                     GlStateManager.rotate(-lllllllllllllllllllIlIIllIllllII * 0.0F, 0.0F, 0.0F, 0.0F);
                     GlStateManager.rotate(-lllllllllllllllllllIlIIllIllllII * 43.0F, 58.0F, 23.0F, 45.0F);
                     break label226;
                  case 6:
                     lllllllllllllllllllIlIIllIlIlIll.transformFirstPersonItem1(lllllllllllllllllllIlIIllIllIIll / 2.0F, lllllllllllllllllllIlIIllIllIIlI);
                     GlStateManager.rotate(lllllllllllllllllllIlIIllIIlIlII * 30.0F / 2.0F, -lllllllllllllllllllIlIIllIIlIlII, 7.0F, 9.0F);
                     GlStateManager.rotate(lllllllllllllllllllIlIIllIIlIlII * 40.0F, 1.0F, -lllllllllllllllllllIlIIllIIlIlII / 2.0F + 7.0F, -0.0F);
                     if (lllllllllllllllllllIlIIllIlIlIIl.getState()) {
                        ItemRotate.ItemRenderRotate();
                     }

                     lllllllllllllllllllIlIIllIlIlIll.doBlockTransformations2();
                     break label226;
                  case 7:
                     lllllllllllllllllllIlIIllIlIlIll.transformFirstPersonItem1(lllllllllllllllllllIlIIllIllIIll / 2.0F, 0.0F);
                     GlStateManager.rotate(-lllllllllllllllllllIlIIllIIlIlII * 40.0F / 2.0F, lllllllllllllllllllIlIIllIIlIlII / 2.0F, -0.0F, 9.0F);
                     GlStateManager.rotate(-lllllllllllllllllllIlIIllIIlIlII * 30.0F, 1.0F, lllllllllllllllllllIlIIllIIlIlII / 2.0F, -0.0F);
                     if (lllllllllllllllllllIlIIllIlIlIIl.getState()) {
                        ItemRotate.ItemRenderRotate();
                     }

                     lllllllllllllllllllIlIIllIlIlIll.doBlockTransformations2();
                     break label226;
                  case 8:
                     GL11.glTranslated(-0.1D, 0.15D, 0.0D);
                     lllllllllllllllllllIlIIllIlIlIll.transformFirstPersonItem(lllllllllllllllllllIlIIllIllIIll / 0.15F, lllllllllllllllllllIlIIllIllIIlI);
                     byte lllllllllllllllllllIlIIllIIIlllI = MathHelper.sin(MathHelper.sqrt_float(lllllllllllllllllllIlIIllIIlllII) * 3.1415927F);
                     GlStateManager.rotate(lllllllllllllllllllIlIIllIIIlllI * 30.0F, 2.0F, -lllllllllllllllllllIlIIllIIIlllI, 9.0F);
                     GlStateManager.rotate(lllllllllllllllllllIlIIllIIIlllI * 35.0F, 1.0F, -lllllllllllllllllllIlIIllIIIlllI, -0.0F);
                     if (lllllllllllllllllllIlIIllIlIlIIl.getState()) {
                        ItemRotate.ItemRenderRotate();
                     }

                     lllllllllllllllllllIlIIllIlIlIll.doBlockTransformations2();
                     break label226;
                  case 9:
                     lllllllllllllllllllIlIIllIlIlIll.genCustom(0.0F, 0.0F);
                     if (lllllllllllllllllllIlIIllIlIlIIl.getState()) {
                        ItemRotate.ItemRenderRotate();
                     }

                     lllllllllllllllllllIlIIllIlIlIll.doBlockTransformations();
                     float lllllllllllllllllllIlIIllIlllIlI = MathHelper.sin(MathHelper.sqrt_float(lllllllllllllllllllIlIIllIIlllII) * 3.1415927F);
                     GlStateManager.translate(-0.5F, 0.4F, 0.0F);
                     GlStateManager.rotate(-lllllllllllllllllllIlIIllIlllIlI * 50.0F, -8.0F, -0.0F, 9.0F);
                     GlStateManager.rotate(-lllllllllllllllllllIlIIllIlllIlI * 70.0F, 1.0F, -0.4F, -0.0F);
                     break label226;
                  case 10:
                     lllllllllllllllllllIlIIllIlIlIll.Jigsaw(lllllllllllllllllllIlIIllIlIIlll, lllllllllllllllllllIlIIllIIlllII);
                     if (lllllllllllllllllllIlIIllIlIlIIl.getState()) {
                        ItemRotate.ItemRenderRotate();
                     }

                     lllllllllllllllllllIlIIllIlIlIll.doBlockTransformations2();
                     break label226;
                  case 11:
                     GL11.glTranslated(-0.10000000149011612D, 0.15000000596046448D, 0.0D);
                     lllllllllllllllllllIlIIllIlIlIll.transformFirstPersonItem(0.5F, lllllllllllllllllllIlIIllIllIIlI);
                     float lllllllllllllllllllIlIIllIIIllII = MathHelper.sin(MathHelper.sqrt_float(lllllllllllllllllllIlIIllIllIIlI) * 3.1415927F);
                     GlStateManager.rotate(lllllllllllllllllllIlIIllIIIllII * 30.0F, -lllllllllllllllllllIlIIllIIIllII, -0.0F, 9.0F);
                     GlStateManager.rotate(lllllllllllllllllllIlIIllIIIllII * 40.0F, 1.0F, -lllllllllllllllllllIlIIllIIIllII, -0.0F);
                     if (lllllllllllllllllllIlIIllIlIlIIl.getState()) {
                        ItemRotate.ItemRenderRotate();
                     }

                     lllllllllllllllllllIlIIllIlIlIll.doBlockTransformations2();
                     break label226;
                  case 12:
                     lllllllllllllllllllIlIIllIlIlIll.transformFirstPersonItem(0.0F, 0.0F);
                     if (lllllllllllllllllllIlIIllIlIlIIl.getState()) {
                        ItemRotate.ItemRenderRotate();
                     }

                     lllllllllllllllllllIlIIllIlIlIll.doBlockTransformations2();
                     int lllllllllllllllllllIlIIllIlllIII = (int)Math.min(255L, (System.currentTimeMillis() % 255L > 127L ? Math.abs(Math.abs(System.currentTimeMillis()) % 255L - 255L) : System.currentTimeMillis() % 255L) * 2L);
                     if ((double)lllllllllllllllllllIlIIllIllIIlI > 0.5D) {
                        var10000 = 1.0F - lllllllllllllllllllIlIIllIllIIlI;
                     }

                     GlStateManager.translate(0.3F, -0.0F, 0.4F);
                     GlStateManager.rotate(0.0F, 0.0F, 0.0F, 1.0F);
                     GlStateManager.translate(0.0F, 0.5F, 0.0F);
                     GlStateManager.rotate(90.0F, 1.0F, 0.0F, -1.0F);
                     GlStateManager.translate(0.6F, 0.5F, 0.0F);
                     GlStateManager.rotate(-90.0F, 1.0F, 0.0F, -1.0F);
                     GlStateManager.rotate(-10.0F, 1.0F, 0.0F, -1.0F);
                     GlStateManager.rotate(lllllllllllllllllllIlIIllIlIlIll.mc.thePlayer.isSwingInProgress ? (float)(-lllllllllllllllllllIlIIllIlllIII) / 5.0F : 1.0F, 1.0F, -0.0F, 1.0F);
                     break label226;
                  case 13:
                     GL11.glTranslated(0.10000000149011612D, 0.019999999552965164D, 0.0D);
                     lllllllllllllllllllIlIIllIlIlIll.func_178096_A(lllllllllllllllllllIlIIllIllIIll, 0.0F);
                     if (lllllllllllllllllllIlIIllIlIlIIl.getState()) {
                        ItemRotate.ItemRenderRotate();
                     }

                     lllllllllllllllllllIlIIllIlIlIll.doBlockTransformations2();
                     lllllllllllllllllllIlIIllIIlIlII = MathHelper.sin(MathHelper.sqrt_float(lllllllllllllllllllIlIIllIllIIlI) * 3.1415927F);
                     GlStateManager.rotate(lllllllllllllllllllIlIIllIIlIlII * 35.0F, -25.0F, -20.0F, 20.0F);
                     break label226;
                  case 14:
                     GL11.glTranslated(-0.1D, 0.15D, 0.0D);
                     if (lllllllllllllllllllIlIIllIlIlIIl.getState()) {
                        ItemRotate.ItemRenderRotate();
                     }

                     lllllllllllllllllllIlIIllIlIlIll.func_178096_A(lllllllllllllllllllIlIIllIlIIlll / 2.0F, -0.2F);
                     lllllllllllllllllllIlIIllIIlIlII = MathHelper.sin(lllllllllllllllllllIlIIllIIlllII * lllllllllllllllllllIlIIllIIlllII * 3.1415927F);
                     GlStateManager.rotate(-lllllllllllllllllllIlIIllIIlIlII * 1.0F / 19.0F, lllllllllllllllllllIlIIllIIlIlII / 20.0F, -0.0F, 9.0F);
                     GlStateManager.rotate(-lllllllllllllllllllIlIIllIIlIlII * 30.0F, 10.0F, lllllllllllllllllllIlIIllIIlIlII / 50.0F, 0.0F);
                     break label226;
                  case 15:
                     lllllllllllllllllllIlIIllIlIlIll.transformFirstPersonItem(lllllllllllllllllllIlIIllIllIIll, 0.0F);
                     if (lllllllllllllllllllIlIIllIlIlIIl.getState()) {
                        ItemRotate.ItemRenderRotate();
                     }

                     lllllllllllllllllllIlIIllIlIlIll.doBlockTransformations2();
                     float lllllllllllllllllllIlIIllIllIllI = MathHelper.sin(lllllllllllllllllllIlIIllIllIIlI * lllllllllllllllllllIlIIllIllIIlI * 3.1415927F);
                     float lllllllllllllllllllIlIIllIllIlIl = MathHelper.sin(MathHelper.sqrt_float(lllllllllllllllllllIlIIllIllIIlI) * 3.1415927F);
                     GlStateManager.translate(-0.0F, -0.4F, 0.4F);
                     GlStateManager.rotate(-lllllllllllllllllllIlIIllIllIlIl * 70.0F / 2.0F, -8.0F, -0.0F, 20.0F);
                     GlStateManager.rotate(-lllllllllllllllllllIlIIllIllIlIl * 30.0F, 1.5F, -0.1F, -0.1F);
                     break label226;
                  case 16:
                     lllllllllllllllllllIlIIllIlIlIll.genCustom(0.0F, 0.0F);
                     if (lllllllllllllllllllIlIIllIlIlIIl.getState()) {
                        ItemRotate.ItemRenderRotate();
                     }

                     lllllllllllllllllllIlIIllIlIlIll.doBlockTransformations();
                     GlStateManager.translate(-0.5F, 0.2F, 0.0F);
                     GlStateManager.rotate(MathHelper.sqrt_float(lllllllllllllllllllIlIIllIIlllII) * 10.0F * 40.0F, 1.0F, -0.0F, 2.0F);
                     break label226;
                  case 17:
                     lllllllllllllllllllIlIIllIlIlIll.transformFirstPersonItem(lllllllllllllllllllIlIIllIlIIlll, 0.0F);
                     if (lllllllllllllllllllIlIIllIlIlIIl.getState()) {
                        ItemRotate.ItemRenderRotate();
                     }

                     lllllllllllllllllllIlIIllIlIlIll.doBlockTransformations2();
                     float lllllllllllllllllllIlIIllIllIlII = MathHelper.sin(MathHelper.sqrt_float(lllllllllllllllllllIlIIllIIlllII) * 3.1415927F);
                     GlStateManager.translate(-0.0F, 0.4F, 0.3F);
                     GlStateManager.rotate(-lllllllllllllllllllIlIIllIllIlII * 35.0F, -8.0F, -0.0F, 9.0F);
                     GlStateManager.rotate(-lllllllllllllllllllIlIIllIllIlII * 10.0F, 1.0F, -0.4F, -0.5F);
                  }
               } else {
                  lllllllllllllllllllIlIIllIlIlIll.transformFirstPersonItem(lllllllllllllllllllIlIIllIlIIlll + 0.1F, lllllllllllllllllllIlIIllIIlllII);
                  lllllllllllllllllllIlIIllIlIlIll.doBlockTransformations();
                  GlStateManager.translate(-0.5F, 0.2F, 0.0F);
               }
               break;
            case BOW:
               lllllllllllllllllllIlIIllIlIlIll.transformFirstPersonItem(lllllllllllllllllllIlIIllIlIIlll, lllllllllllllllllllIlIIllIIlllII);
               lllllllllllllllllllIlIIllIlIlIll.doBowTransformations(lllllllllllllllllllIlIIllIlIlIlI, lllllllllllllllllllIlIIllIlIIllI);
            }
         }

         lllllllllllllllllllIlIIllIlIlIll.renderItem(lllllllllllllllllllIlIIllIlIIllI, lllllllllllllllllllIlIIllIlIlIll.itemToRender, TransformType.FIRST_PERSON);
      } else if (!lllllllllllllllllllIlIIllIlIIllI.isInvisible()) {
         lllllllllllllllllllIlIIllIlIlIll.renderPlayerArm(lllllllllllllllllllIlIIllIlIIllI, lllllllllllllllllllIlIIllIlIIlll, lllllllllllllllllllIlIIllIIlllII);
      }

      GlStateManager.popMatrix();
      GlStateManager.disableRescaleNormal();
      RenderHelper.disableStandardItemLighting();
   }

   @Shadow
   protected abstract void renderPlayerArm(AbstractClientPlayer var1, float var2, float var3);

   @Shadow
   protected abstract void transformFirstPersonItem(float param1, float param2);

   private void avatar(float lllllllllllllllllllIlIIlIIIIIIlI, float lllllllllllllllllllIlIIIllllllIl) {
      GlStateManager.translate((Float)Animations.itemPosX.get(), (Float)Animations.itemPosY.get(), (Float)Animations.itemPosZ.get());
      GlStateManager.translate(0.56F, -0.52F, -0.71999997F);
      GlStateManager.translate(0.0F, 0.0F, 0.0F);
      GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
      short lllllllllllllllllllIlIIIllllllII = MathHelper.sin(lllllllllllllllllllIlIIIllllllIl * lllllllllllllllllllIlIIIllllllIl * 3.1415927F);
      boolean lllllllllllllllllllIlIIIlllllIlI = MathHelper.sin(MathHelper.sqrt_float(lllllllllllllllllllIlIIIllllllIl) * 3.1415927F);
      GlStateManager.rotate(lllllllllllllllllllIlIIIllllllII * -20.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(lllllllllllllllllllIlIIIlllllIlI * -20.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.rotate(lllllllllllllllllllIlIIIlllllIlI * -40.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.scale(0.4F, 0.4F, 0.4F);
      GlStateManager.scale((Float)Animations.Scale.get(), (Float)Animations.Scale.get(), (Float)Animations.Scale.get());
   }

   @Shadow
   protected abstract void performDrinking(AbstractClientPlayer var1, float var2);

   @Shadow
   protected abstract void renderItemMap(AbstractClientPlayer var1, float var2, float var3, float var4);

   private void push(float lllllllllllllllllllIlIIlIlllllIl, float lllllllllllllllllllIlIIllIIIIIII) {
      GlStateManager.translate((Float)Animations.itemPosX.get(), (Float)Animations.itemPosY.get(), (Float)Animations.itemPosZ.get());
      GlStateManager.translate(0.0F, lllllllllllllllllllIlIIlIlllllIl * -0.6F, 0.0F);
      GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
      long lllllllllllllllllllIlIIlIllllIll = MathHelper.sin(lllllllllllllllllllIlIIllIIIIIII * lllllllllllllllllllIlIIllIIIIIII * 3.1415927F);
      float lllllllllllllllllllIlIIlIllllllI = MathHelper.sin(MathHelper.sqrt_float(lllllllllllllllllllIlIIllIIIIIII) * 3.1415927F);
      GlStateManager.rotate(lllllllllllllllllllIlIIlIllllIll * -10.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.rotate(lllllllllllllllllllIlIIlIllllllI * -10.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.rotate(lllllllllllllllllllIlIIlIllllllI * -10.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.scale((Float)Animations.Scale.get(), (Float)Animations.Scale.get(), (Float)Animations.Scale.get());
   }

   private void transformFirstPersonItem(float param1, float param2) {
      // $FF: Couldn't be decompiled
   }

   @Shadow
   protected abstract void doBowTransformations(float var1, AbstractClientPlayer var2);

   @Shadow
   protected abstract void setLightMapFromPlayer(AbstractClientPlayer var1);

   @Inject(
      method = {"renderFireInFirstPerson"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void renderFireInFirstPerson(CallbackInfo lllllllllllllllllllIlIIIlIlllIII) {
      AntiBlind lllllllllllllllllllIlIIIlIlllIIl = (AntiBlind)LiquidBounce.moduleManager.getModule(AntiBlind.class);
      if (lllllllllllllllllllIlIIIlIlllIIl.getState() && (Boolean)lllllllllllllllllllIlIIIlIlllIIl.getFireEffect().get()) {
         lllllllllllllllllllIlIIIlIlllIII.cancel();
      }

   }

   @Shadow
   protected abstract void doBlockTransformations();

   private void tap(float lllllllllllllllllllIlIIlIllIIlII, float lllllllllllllllllllIlIIlIllIIIlI) {
      GlStateManager.translate((Float)Animations.itemPosX.get(), (Float)Animations.itemPosY.get(), (Float)Animations.itemPosZ.get());
      float lllllllllllllllllllIlIIlIllIIIIl = lllllllllllllllllllIlIIlIllIIIlI * 0.8F - lllllllllllllllllllIlIIlIllIIIlI * lllllllllllllllllllIlIIlIllIIIlI * 0.8F;
      GlStateManager.translate(0.56F, -0.52F, -0.71999997F);
      GlStateManager.translate(0.0F, lllllllllllllllllllIlIIlIllIIlII * -0.15F, 0.0F);
      GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
      float lllllllllllllllllllIlIIlIlIllIII = MathHelper.sin(lllllllllllllllllllIlIIlIllIIIlI * lllllllllllllllllllIlIIlIllIIIlI * 3.1415927F);
      char lllllllllllllllllllIlIIlIlIlIllI = MathHelper.sin(MathHelper.sqrt_float(lllllllllllllllllllIlIIlIllIIIlI) * 3.1415927F);
      GlStateManager.rotate(lllllllllllllllllllIlIIlIllIIIIl * -90.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.scale(0.37F, 0.37F, 0.37F);
      GlStateManager.scale((Float)Animations.Scale.get(), (Float)Animations.Scale.get(), (Float)Animations.Scale.get());
   }

   private void genCustom(float lllllllllllllllllllIlIIIllIIIlIl, float lllllllllllllllllllIlIIIllIIIIII) {
      GlStateManager.translate((Float)Animations.itemPosX.get(), (Float)Animations.itemPosY.get(), (Float)Animations.itemPosZ.get());
      GlStateManager.translate(0.56F, -0.52F, -0.71999997F);
      GlStateManager.translate(0.0F, lllllllllllllllllllIlIIIllIIIlIl * -0.6F, 0.0F);
      GlStateManager.rotate(25.0F, 0.0F, 1.0F, 0.0F);
      boolean lllllllllllllllllllIlIIIlIllllll = MathHelper.sin(lllllllllllllllllllIlIIIllIIIIII * lllllllllllllllllllIlIIIllIIIIII * 3.1415927F);
      double lllllllllllllllllllIlIIIlIlllllI = MathHelper.sin(MathHelper.sqrt_float(lllllllllllllllllllIlIIIllIIIIII) * 3.1415927F);
      GlStateManager.rotate(lllllllllllllllllllIlIIIlIllllll * -15.0F, 0.0F, 1.0F, 0.2F);
      GlStateManager.rotate(lllllllllllllllllllIlIIIlIlllllI * -10.0F, 0.2F, 0.1F, 1.0F);
      GlStateManager.rotate(lllllllllllllllllllIlIIIlIlllllI * -30.0F, 1.3F, 0.1F, 0.2F);
      GlStateManager.scale(0.4F, 0.4F, 0.4F);
      GlStateManager.scale((Float)Animations.Scale.get(), (Float)Animations.Scale.get(), (Float)Animations.Scale.get());
   }
}
