//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import java.util.Collections;
import java.util.List;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.misc.ComponentOnHover;
import net.ccbluex.liquidbounce.features.module.modules.render.HUD;
import net.ccbluex.liquidbounce.ui.client.GuiBackground;
import net.ccbluex.liquidbounce.utils.render.ParticleUtils;
import net.ccbluex.liquidbounce.utils.render.shader.shaders.BackgroundShader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SideOnly(Side.CLIENT)
@Mixin({GuiScreen.class})
public abstract class MixinGuiScreen {
   // $FF: synthetic field
   @Shadow
   public int height;
   // $FF: synthetic field
   @Shadow
   public int width;
   // $FF: synthetic field
   @Shadow
   protected List<GuiButton> buttonList;
   // $FF: synthetic field
   @Shadow
   public Minecraft mc;
   // $FF: synthetic field
   @Shadow
   protected FontRenderer fontRendererObj;

   @Shadow
   public void updateScreen() {
   }

   @Inject(
      method = {"handleComponentHover"},
      at = {@At("HEAD")}
   )
   private void handleHoverOverComponent(IChatComponent llllllIlIIllIlI, int llllllIlIlIIIIl, int llllllIlIIllIII, CallbackInfo llllllIlIIlllll) {
      if (llllllIlIIllIlI != null && llllllIlIIllIlI.getChatStyle().getChatClickEvent() != null && LiquidBounce.moduleManager.getModule(ComponentOnHover.class).getState()) {
         String llllllIlIIlIlll = llllllIlIIllIlI.getChatStyle();
         ClickEvent llllllIlIIlllIl = llllllIlIIlIlll.getChatClickEvent();
         HoverEvent llllllIlIIlllII = llllllIlIIlIlll.getChatHoverEvent();
         llllllIlIlIIIll.drawHoveringText(Collections.singletonList(String.valueOf((new StringBuilder()).append("§c§l").append(llllllIlIIlllIl.getAction().getCanonicalName().toUpperCase()).append(": §a").append(llllllIlIIlllIl.getValue()))), llllllIlIlIIIIl, llllllIlIIllIII - (llllllIlIIlllII != null ? 17 : 0));
      }
   }

   @Inject(
      method = {"sendChatMessage(Ljava/lang/String;Z)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void messageSend(String llllllIlIllIIIl, boolean llllllIlIlIllII, CallbackInfo llllllIlIlIlIll) {
      if (llllllIlIllIIIl.startsWith(String.valueOf(LiquidBounce.commandManager.getPrefix())) && llllllIlIlIllII) {
         llllllIlIllIIlI.mc.ingameGUI.getChatGUI().addToSentMessages(llllllIlIllIIIl);
         LiquidBounce.commandManager.executeCommands(llllllIlIllIIIl);
         llllllIlIlIlIll.cancel();
      }

   }

   @Inject(
      method = {"drawBackground"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void drawClientBackground(CallbackInfo llllllIllIIIIII) {
      GlStateManager.disableLighting();
      GlStateManager.disableFog();
      if (GuiBackground.Companion.getEnabled()) {
         if (LiquidBounce.INSTANCE.getBackground() == null) {
            BackgroundShader.BACKGROUND_SHADER.startShader();
            Tessellator llllllIllIIIllI = Tessellator.getInstance();
            WorldRenderer llllllIllIIIlIl = llllllIllIIIllI.getWorldRenderer();
            llllllIllIIIlIl.begin(7, DefaultVertexFormats.POSITION);
            llllllIllIIIlIl.pos(0.0D, (double)llllllIllIIIIIl.height, 0.0D).endVertex();
            llllllIllIIIlIl.pos((double)llllllIllIIIIIl.width, (double)llllllIllIIIIIl.height, 0.0D).endVertex();
            llllllIllIIIlIl.pos((double)llllllIllIIIIIl.width, 0.0D, 0.0D).endVertex();
            llllllIllIIIlIl.pos(0.0D, 0.0D, 0.0D).endVertex();
            llllllIllIIIllI.draw();
            BackgroundShader.BACKGROUND_SHADER.stopShader();
         } else {
            boolean llllllIlIllllIl = new ScaledResolution(llllllIllIIIIIl.mc);
            byte llllllIlIllllII = llllllIlIllllIl.getScaledWidth();
            short llllllIlIlllIll = llllllIlIllllIl.getScaledHeight();
            llllllIllIIIIIl.mc.getTextureManager().bindTexture(LiquidBounce.INSTANCE.getBackground());
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            Gui.drawScaledCustomSizeModalRect(0, 0, 0.0F, 0.0F, llllllIlIllllII, llllllIlIlllIll, llllllIlIllllII, llllllIlIlllIll, (float)llllllIlIllllII, (float)llllllIlIlllIll);
         }

         if (GuiBackground.Companion.getParticles()) {
            ParticleUtils.drawParticles(Mouse.getX() * llllllIllIIIIIl.width / llllllIllIIIIIl.mc.displayWidth, llllllIllIIIIIl.height - Mouse.getY() * llllllIllIIIIIl.height / llllllIllIIIIIl.mc.displayHeight - 1);
         }

         llllllIllIIIIII.cancel();
      }

   }

   @Shadow
   public abstract void handleComponentHover(IChatComponent var1, int var2, int var3);

   @Shadow
   protected abstract void drawHoveringText(List<String> var1, int var2, int var3);

   @Inject(
      method = {"drawBackground"},
      at = {@At("RETURN")}
   )
   private void drawParticles(CallbackInfo llllllIlIlllIII) {
      if (GuiBackground.Companion.getParticles()) {
         ParticleUtils.drawParticles(Mouse.getX() * llllllIlIllIlll.width / llllllIlIllIlll.mc.displayWidth, llllllIlIllIlll.height - Mouse.getY() * llllllIlIllIlll.height / llllllIlIllIlll.mc.displayHeight - 1);
      }

   }

   @Inject(
      method = {"drawWorldBackground"},
      at = {@At("HEAD")}
   )
   private void drawWorldBackground(CallbackInfo llllllIllIlIIlI) {
      HUD llllllIllIlIIIl = (HUD)LiquidBounce.moduleManager.getModule(HUD.class);
      if ((Boolean)llllllIllIlIIIl.inventoryParticle.get() && llllllIllIlIIII.mc.thePlayer != null) {
         char llllllIllIIlllI = new ScaledResolution(llllllIllIlIIII.mc);
         float llllllIllIIllIl = llllllIllIIlllI.getScaledWidth();
         int llllllIllIIllII = llllllIllIIlllI.getScaledHeight();
         ParticleUtils.drawParticles(Mouse.getX() * llllllIllIIllIl / llllllIllIlIIII.mc.displayWidth, llllllIllIIllII - Mouse.getY() * llllllIllIIllII / llllllIllIlIIII.mc.displayHeight - 1);
      }

   }
}
