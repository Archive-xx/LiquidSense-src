//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.features.module.modules.render.AntiBlind;
import net.ccbluex.liquidbounce.features.module.modules.render.HUD;
import net.ccbluex.liquidbounce.features.module.modules.render.NoScoreboard;
import net.ccbluex.liquidbounce.utils.ClassUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SideOnly(Side.CLIENT)
@Mixin({GuiIngame.class})
public abstract class MixinGuiInGame {
   @Inject(
      method = {"renderTooltip"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void renderTooltip(ScaledResolution llIllIlIlIIllll, float llIllIlIlIIlllI, CallbackInfo llIllIlIlIIlIII) {
      short llIllIlIlIIIlll = (HUD)LiquidBounce.moduleManager.getModule(HUD.class);
      if (Minecraft.getMinecraft().getRenderViewEntity() instanceof EntityPlayer && llIllIlIlIIIlll.getState() && (Boolean)llIllIlIlIIIlll.blackHotbarValue.get()) {
         EntityPlayer llIllIlIlIlIIlI = (EntityPlayer)Minecraft.getMinecraft().getRenderViewEntity();
         float llIllIlIlIIIlIl = llIllIlIlIIllll.getScaledWidth() / 2;
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         GuiIngame.drawRect(llIllIlIlIIIlIl - 91, llIllIlIlIIllll.getScaledHeight() - 24, llIllIlIlIIIlIl + 90, llIllIlIlIIllll.getScaledHeight(), Integer.MIN_VALUE);
         GuiIngame.drawRect(llIllIlIlIIIlIl - 91 - 1 + llIllIlIlIlIIlI.inventory.currentItem * 20 + 1, llIllIlIlIIllll.getScaledHeight() - 24, llIllIlIlIIIlIl - 91 - 1 + llIllIlIlIlIIlI.inventory.currentItem * 20 + 22, llIllIlIlIIllll.getScaledHeight() - 22 - 1 + 24, Integer.MAX_VALUE);
         GlStateManager.enableRescaleNormal();
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         RenderHelper.enableGUIStandardItemLighting();

         for(int llIllIlIlIIIlII = 0; llIllIlIlIIIlII < 9; ++llIllIlIlIIIlII) {
            int llIllIlIlIlIlIl = llIllIlIlIIllll.getScaledWidth() / 2 - 90 + llIllIlIlIIIlII * 20 + 2;
            int llIllIlIlIlIlII = llIllIlIlIIllll.getScaledHeight() - 16 - 3;
            llIllIlIlIIlIll.renderHotbarItem(llIllIlIlIIIlII, llIllIlIlIlIlIl, llIllIlIlIlIlII, llIllIlIlIIlllI, llIllIlIlIlIIlI);
         }

         RenderHelper.disableStandardItemLighting();
         GlStateManager.disableRescaleNormal();
         GlStateManager.disableBlend();
         LiquidBounce.eventManager.callEvent(new Render2DEvent(llIllIlIlIIlllI));
         llIllIlIlIIlIII.cancel();
      }

   }

   @Inject(
      method = {"renderPumpkinOverlay"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void renderPumpkinOverlay(CallbackInfo llIllIlIIllIllI) {
      AntiBlind llIllIlIIllIlll = (AntiBlind)LiquidBounce.moduleManager.getModule(AntiBlind.class);
      if (llIllIlIIllIlll.getState() && (Boolean)llIllIlIIllIlll.getPumpkinEffect().get()) {
         llIllIlIIllIllI.cancel();
      }

   }

   @Inject(
      method = {"renderTooltip"},
      at = {@At("RETURN")}
   )
   private void renderTooltipPost(ScaledResolution llIllIlIIllllll, float llIllIlIIlllllI, CallbackInfo llIllIlIIllllIl) {
      if (!ClassUtils.hasClass("net.labymod.api.LabyModAPI")) {
         LiquidBounce.eventManager.callEvent(new Render2DEvent(llIllIlIIlllllI));
      }

   }

   @Inject(
      method = {"renderScoreboard"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void renderScoreboard(CallbackInfo llIllIlIllIIIII) {
      if (LiquidBounce.moduleManager.getModule(HUD.class).getState() || NoScoreboard.INSTANCE.getState()) {
         llIllIlIllIIIII.cancel();
      }

   }

   @Shadow
   protected abstract void renderHotbarItem(int var1, int var2, int var3, float var4, EntityPlayer var5);
}
