//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@SideOnly(Side.CLIENT)
@Mixin({GuiSlot.class})
public abstract class MixinGuiSlot {
   // $FF: synthetic field
   @Shadow
   public int top;
   // $FF: synthetic field
   @Shadow
   public int right;
   // $FF: synthetic field
   @Shadow
   public int left;
   // $FF: synthetic field
   @Shadow
   protected int mouseX;
   // $FF: synthetic field
   @Shadow
   protected float amountScrolled;
   // $FF: synthetic field
   @Shadow
   protected boolean hasListHeader;
   // $FF: synthetic field
   @Shadow
   public int width;
   // $FF: synthetic field
   @Shadow
   public int bottom;
   // $FF: synthetic field
   @Shadow
   protected boolean field_178041_q;
   // $FF: synthetic field
   @Shadow
   public int height;
   // $FF: synthetic field
   @Shadow
   protected int mouseY;
   // $FF: synthetic field
   @Shadow
   @Final
   protected Minecraft mc;

   @Shadow
   protected abstract void drawBackground();

   @Shadow
   protected abstract void drawListHeader(int var1, int var2, Tessellator var3);

   @Shadow
   protected abstract void bindAmountScrolled();

   @Overwrite
   public void drawScreen(int lllllllllllllllllIlllIlIIllIIlIl, int lllllllllllllllllIlllIlIIllIlIIl, float lllllllllllllllllIlllIlIIllIIlll) {
      if (lllllllllllllllllIlllIlIIllIIllI.field_178041_q) {
         lllllllllllllllllIlllIlIIllIIllI.mouseX = lllllllllllllllllIlllIlIIllIIlIl;
         lllllllllllllllllIlllIlIIllIIllI.mouseY = lllllllllllllllllIlllIlIIllIlIIl;
         lllllllllllllllllIlllIlIIllIIllI.drawBackground();
         char lllllllllllllllllIlllIlIIllIIIll = lllllllllllllllllIlllIlIIllIIllI.getScrollBarX();
         int lllllllllllllllllIlllIlIIlllIlll = lllllllllllllllllIlllIlIIllIIIll + 6;
         lllllllllllllllllIlllIlIIllIIllI.bindAmountScrolled();
         GlStateManager.disableLighting();
         GlStateManager.disableFog();
         Tessellator lllllllllllllllllIlllIlIIlllIllI = Tessellator.getInstance();
         WorldRenderer lllllllllllllllllIlllIlIIlllIlIl = lllllllllllllllllIlllIlIIlllIllI.getWorldRenderer();
         String lllllllllllllllllIlllIlIIlIlllll = lllllllllllllllllIlllIlIIllIIllI.left + lllllllllllllllllIlllIlIIllIIllI.width / 2 - lllllllllllllllllIlllIlIIllIIllI.getListWidth() / 2 + 2;
         int lllllllllllllllllIlllIlIIlllIIll = lllllllllllllllllIlllIlIIllIIllI.top + 4 - (int)lllllllllllllllllIlllIlIIllIIllI.amountScrolled;
         if (lllllllllllllllllIlllIlIIllIIllI.hasListHeader) {
            lllllllllllllllllIlllIlIIllIIllI.drawListHeader(lllllllllllllllllIlllIlIIlIlllll, lllllllllllllllllIlllIlIIlllIIll, lllllllllllllllllIlllIlIIlllIllI);
         }

         lllllllllllllllllIlllIlIIllIIllI.drawSelectionBox(lllllllllllllllllIlllIlIIlIlllll, lllllllllllllllllIlllIlIIlllIIll + 2, lllllllllllllllllIlllIlIIllIIlIl, lllllllllllllllllIlllIlIIllIlIIl + 2);
         GlStateManager.disableDepth();
         double lllllllllllllllllIlllIlIIlIlllIl = 4;
         ScaledResolution lllllllllllllllllIlllIlIIllIllll = new ScaledResolution(lllllllllllllllllIlllIlIIllIIllI.mc);
         Gui.drawRect(0, 0, lllllllllllllllllIlllIlIIllIllll.getScaledWidth(), lllllllllllllllllIlllIlIIllIIllI.top, Integer.MIN_VALUE);
         Gui.drawRect(0, lllllllllllllllllIlllIlIIllIIllI.bottom, lllllllllllllllllIlllIlIIllIllll.getScaledWidth(), lllllllllllllllllIlllIlIIllIIllI.height, Integer.MIN_VALUE);
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
         GlStateManager.disableAlpha();
         GlStateManager.shadeModel(7425);
         GlStateManager.disableTexture2D();
         lllllllllllllllllIlllIlIIlllIlIl.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
         lllllllllllllllllIlllIlIIlllIlIl.pos((double)lllllllllllllllllIlllIlIIllIIllI.left, (double)(lllllllllllllllllIlllIlIIllIIllI.top + lllllllllllllllllIlllIlIIlIlllIl), 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 0).endVertex();
         lllllllllllllllllIlllIlIIlllIlIl.pos((double)lllllllllllllllllIlllIlIIllIIllI.right, (double)(lllllllllllllllllIlllIlIIllIIllI.top + lllllllllllllllllIlllIlIIlIlllIl), 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 0).endVertex();
         lllllllllllllllllIlllIlIIlllIlIl.pos((double)lllllllllllllllllIlllIlIIllIIllI.right, (double)lllllllllllllllllIlllIlIIllIIllI.top, 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 255).endVertex();
         lllllllllllllllllIlllIlIIlllIlIl.pos((double)lllllllllllllllllIlllIlIIllIIllI.left, (double)lllllllllllllllllIlllIlIIllIIllI.top, 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 255).endVertex();
         lllllllllllllllllIlllIlIIlllIllI.draw();
         lllllllllllllllllIlllIlIIlllIlIl.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
         lllllllllllllllllIlllIlIIlllIlIl.pos((double)lllllllllllllllllIlllIlIIllIIllI.left, (double)lllllllllllllllllIlllIlIIllIIllI.bottom, 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 255).endVertex();
         lllllllllllllllllIlllIlIIlllIlIl.pos((double)lllllllllllllllllIlllIlIIllIIllI.right, (double)lllllllllllllllllIlllIlIIllIIllI.bottom, 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
         lllllllllllllllllIlllIlIIlllIlIl.pos((double)lllllllllllllllllIlllIlIIllIIllI.right, (double)(lllllllllllllllllIlllIlIIllIIllI.bottom - lllllllllllllllllIlllIlIIlIlllIl), 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 0).endVertex();
         lllllllllllllllllIlllIlIIlllIlIl.pos((double)lllllllllllllllllIlllIlIIllIIllI.left, (double)(lllllllllllllllllIlllIlIIllIIllI.bottom - lllllllllllllllllIlllIlIIlIlllIl), 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 0).endVertex();
         lllllllllllllllllIlllIlIIlllIllI.draw();
         int lllllllllllllllllIlllIlIIllIllIl = lllllllllllllllllIlllIlIIllIIllI.func_148135_f();
         if (lllllllllllllllllIlllIlIIllIllIl > 0) {
            short lllllllllllllllllIlllIlIIlIllIlI = (lllllllllllllllllIlllIlIIllIIllI.bottom - lllllllllllllllllIlllIlIIllIIllI.top) * (lllllllllllllllllIlllIlIIllIIllI.bottom - lllllllllllllllllIlllIlIIllIIllI.top) / lllllllllllllllllIlllIlIIllIIllI.getContentHeight();
            lllllllllllllllllIlllIlIIlIllIlI = MathHelper.clamp_int(lllllllllllllllllIlllIlIIlIllIlI, 32, lllllllllllllllllIlllIlIIllIIllI.bottom - lllllllllllllllllIlllIlIIllIIllI.top - 8);
            int lllllllllllllllllIlllIlIIllllIIl = (int)lllllllllllllllllIlllIlIIllIIllI.amountScrolled * (lllllllllllllllllIlllIlIIllIIllI.bottom - lllllllllllllllllIlllIlIIllIIllI.top - lllllllllllllllllIlllIlIIlIllIlI) / lllllllllllllllllIlllIlIIllIllIl + lllllllllllllllllIlllIlIIllIIllI.top;
            if (lllllllllllllllllIlllIlIIllllIIl < lllllllllllllllllIlllIlIIllIIllI.top) {
               lllllllllllllllllIlllIlIIllllIIl = lllllllllllllllllIlllIlIIllIIllI.top;
            }

            lllllllllllllllllIlllIlIIlllIlIl.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            lllllllllllllllllIlllIlIIlllIlIl.pos((double)lllllllllllllllllIlllIlIIllIIIll, (double)lllllllllllllllllIlllIlIIllIIllI.bottom, 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 255).endVertex();
            lllllllllllllllllIlllIlIIlllIlIl.pos((double)lllllllllllllllllIlllIlIIlllIlll, (double)lllllllllllllllllIlllIlIIllIIllI.bottom, 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
            lllllllllllllllllIlllIlIIlllIlIl.pos((double)lllllllllllllllllIlllIlIIlllIlll, (double)lllllllllllllllllIlllIlIIllIIllI.top, 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 255).endVertex();
            lllllllllllllllllIlllIlIIlllIlIl.pos((double)lllllllllllllllllIlllIlIIllIIIll, (double)lllllllllllllllllIlllIlIIllIIllI.top, 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 255).endVertex();
            lllllllllllllllllIlllIlIIlllIllI.draw();
            lllllllllllllllllIlllIlIIlllIlIl.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            lllllllllllllllllIlllIlIIlllIlIl.pos((double)lllllllllllllllllIlllIlIIllIIIll, (double)(lllllllllllllllllIlllIlIIllllIIl + lllllllllllllllllIlllIlIIlIllIlI), 0.0D).tex(0.0D, 1.0D).color(128, 128, 128, 255).endVertex();
            lllllllllllllllllIlllIlIIlllIlIl.pos((double)lllllllllllllllllIlllIlIIlllIlll, (double)(lllllllllllllllllIlllIlIIllllIIl + lllllllllllllllllIlllIlIIlIllIlI), 0.0D).tex(1.0D, 1.0D).color(128, 128, 128, 255).endVertex();
            lllllllllllllllllIlllIlIIlllIlIl.pos((double)lllllllllllllllllIlllIlIIlllIlll, (double)lllllllllllllllllIlllIlIIllllIIl, 0.0D).tex(1.0D, 0.0D).color(128, 128, 128, 255).endVertex();
            lllllllllllllllllIlllIlIIlllIlIl.pos((double)lllllllllllllllllIlllIlIIllIIIll, (double)lllllllllllllllllIlllIlIIllllIIl, 0.0D).tex(0.0D, 0.0D).color(128, 128, 128, 255).endVertex();
            lllllllllllllllllIlllIlIIlllIllI.draw();
            lllllllllllllllllIlllIlIIlllIlIl.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            lllllllllllllllllIlllIlIIlllIlIl.pos((double)lllllllllllllllllIlllIlIIllIIIll, (double)(lllllllllllllllllIlllIlIIllllIIl + lllllllllllllllllIlllIlIIlIllIlI - 1), 0.0D).tex(0.0D, 1.0D).color(192, 192, 192, 255).endVertex();
            lllllllllllllllllIlllIlIIlllIlIl.pos((double)(lllllllllllllllllIlllIlIIlllIlll - 1), (double)(lllllllllllllllllIlllIlIIllllIIl + lllllllllllllllllIlllIlIIlIllIlI - 1), 0.0D).tex(1.0D, 1.0D).color(192, 192, 192, 255).endVertex();
            lllllllllllllllllIlllIlIIlllIlIl.pos((double)(lllllllllllllllllIlllIlIIlllIlll - 1), (double)lllllllllllllllllIlllIlIIllllIIl, 0.0D).tex(1.0D, 0.0D).color(192, 192, 192, 255).endVertex();
            lllllllllllllllllIlllIlIIlllIlIl.pos((double)lllllllllllllllllIlllIlIIllIIIll, (double)lllllllllllllllllIlllIlIIllllIIl, 0.0D).tex(0.0D, 0.0D).color(192, 192, 192, 255).endVertex();
            lllllllllllllllllIlllIlIIlllIllI.draw();
         }

         lllllllllllllllllIlllIlIIllIIllI.func_148142_b(lllllllllllllllllIlllIlIIllIIlIl, lllllllllllllllllIlllIlIIllIlIIl);
         GlStateManager.enableTexture2D();
         GlStateManager.shadeModel(7424);
         GlStateManager.enableAlpha();
         GlStateManager.disableBlend();
      }

   }

   @Shadow
   protected abstract void func_148142_b(int var1, int var2);

   @Shadow
   protected abstract int getContentHeight();

   @Overwrite
   protected int getScrollBarX() {
      return lllllllllllllllllIlllIlIIlIlIllI.width - 5;
   }

   @Shadow
   public abstract int func_148135_f();

   @Shadow
   protected abstract void drawSelectionBox(int var1, int var2, int var3, int var4);

   @Shadow
   public abstract int getListWidth();
}
