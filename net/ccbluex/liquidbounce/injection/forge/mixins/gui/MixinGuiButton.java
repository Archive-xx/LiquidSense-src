//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import java.awt.Color;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@SideOnly(Side.CLIENT)
@Mixin({GuiButton.class})
public abstract class MixinGuiButton extends Gui {
   // $FF: synthetic field
   @Shadow
   public boolean visible;
   // $FF: synthetic field
   @Shadow
   public String displayString;
   // $FF: synthetic field
   @Shadow
   public int xPosition;
   // $FF: synthetic field
   @Shadow
   @Final
   protected static ResourceLocation buttonTextures;
   // $FF: synthetic field
   private float cut;
   // $FF: synthetic field
   @Shadow
   public int height;
   // $FF: synthetic field
   @Shadow
   public boolean enabled;
   // $FF: synthetic field
   @Shadow
   public int width;
   // $FF: synthetic field
   @Shadow
   public int yPosition;
   // $FF: synthetic field
   private float alpha;
   // $FF: synthetic field
   @Shadow
   protected boolean hovered;

   @Shadow
   protected abstract void mouseDragged(Minecraft var1, int var2, int var3);

   @Overwrite
   public void drawButton(Minecraft llllllllllllllllllIIlIIIlIlIllll, int llllllllllllllllllIIlIIIlIllIIlI, int llllllllllllllllllIIlIIIlIllIIIl) {
      if (llllllllllllllllllIIlIIIlIllIlII.visible) {
         FontRenderer llllllllllllllllllIIlIIIlIllIllI = llllllllllllllllllIIlIIIlIlIllll.getLanguageManager().isCurrentLocaleUnicode() ? llllllllllllllllllIIlIIIlIlIllll.fontRendererObj : Fonts.font35;
         llllllllllllllllllIIlIIIlIllIlII.hovered = llllllllllllllllllIIlIIIlIllIIlI >= llllllllllllllllllIIlIIIlIllIlII.xPosition && llllllllllllllllllIIlIIIlIllIIIl >= llllllllllllllllllIIlIIIlIllIlII.yPosition && llllllllllllllllllIIlIIIlIllIIlI < llllllllllllllllllIIlIIIlIllIlII.xPosition + llllllllllllllllllIIlIIIlIllIlII.width && llllllllllllllllllIIlIIIlIllIIIl < llllllllllllllllllIIlIIIlIllIlII.yPosition + llllllllllllllllllIIlIIIlIllIlII.height;
         byte llllllllllllllllllIIlIIIlIlIlIll = RenderUtils.deltaTime;
         if (llllllllllllllllllIIlIIIlIllIlII.enabled && llllllllllllllllllIIlIIIlIllIlII.hovered) {
            llllllllllllllllllIIlIIIlIllIlII.cut += 0.05F * (float)llllllllllllllllllIIlIIIlIlIlIll;
            if (llllllllllllllllllIIlIIIlIllIlII.cut >= 4.0F) {
               llllllllllllllllllIIlIIIlIllIlII.cut = 4.0F;
            }

            llllllllllllllllllIIlIIIlIllIlII.alpha += 0.3F * (float)llllllllllllllllllIIlIIIlIlIlIll;
            if (llllllllllllllllllIIlIIIlIllIlII.alpha >= 210.0F) {
               llllllllllllllllllIIlIIIlIllIlII.alpha = 210.0F;
            }
         } else {
            llllllllllllllllllIIlIIIlIllIlII.cut -= 0.05F * (float)llllllllllllllllllIIlIIIlIlIlIll;
            if (llllllllllllllllllIIlIIIlIllIlII.cut <= 0.0F) {
               llllllllllllllllllIIlIIIlIllIlII.cut = 0.0F;
            }

            llllllllllllllllllIIlIIIlIllIlII.alpha -= 0.3F * (float)llllllllllllllllllIIlIIIlIlIlIll;
            if (llllllllllllllllllIIlIIIlIllIlII.alpha <= 120.0F) {
               llllllllllllllllllIIlIIIlIllIlII.alpha = 120.0F;
            }
         }

         Gui.drawRect(llllllllllllllllllIIlIIIlIllIlII.xPosition + (int)llllllllllllllllllIIlIIIlIllIlII.cut, llllllllllllllllllIIlIIIlIllIlII.yPosition, llllllllllllllllllIIlIIIlIllIlII.xPosition + llllllllllllllllllIIlIIIlIllIlII.width - (int)llllllllllllllllllIIlIIIlIllIlII.cut, llllllllllllllllllIIlIIIlIllIlII.yPosition + llllllllllllllllllIIlIIIlIllIlII.height, llllllllllllllllllIIlIIIlIllIlII.enabled ? (new Color(0.0F, 0.0F, 0.0F, llllllllllllllllllIIlIIIlIllIlII.alpha / 255.0F)).getRGB() : (new Color(0.5F, 0.5F, 0.5F, 0.5F)).getRGB());
         llllllllllllllllllIIlIIIlIlIllll.getTextureManager().bindTexture(buttonTextures);
         llllllllllllllllllIIlIIIlIllIlII.mouseDragged(llllllllllllllllllIIlIIIlIlIllll, llllllllllllllllllIIlIIIlIllIIlI, llllllllllllllllllIIlIIIlIllIIIl);
         ((FontRenderer)llllllllllllllllllIIlIIIlIllIllI).drawStringWithShadow(llllllllllllllllllIIlIIIlIllIlII.displayString, (float)(llllllllllllllllllIIlIIIlIllIlII.xPosition + llllllllllllllllllIIlIIIlIllIlII.width / 2 - ((FontRenderer)llllllllllllllllllIIlIIIlIllIllI).getStringWidth(llllllllllllllllllIIlIIIlIllIlII.displayString) / 2), (float)llllllllllllllllllIIlIIIlIllIlII.yPosition + (float)(llllllllllllllllllIIlIIIlIllIlII.height - 5) / 2.0F, 14737632);
         boolean var10001 = false;
         GlStateManager.resetColor();
      }

   }
}
