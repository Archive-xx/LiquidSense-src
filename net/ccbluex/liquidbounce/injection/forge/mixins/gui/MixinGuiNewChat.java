//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import java.util.Iterator;
import java.util.List;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.render.HUD;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer.EnumChatVisibility;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({GuiNewChat.class})
public abstract class MixinGuiNewChat {
   // $FF: synthetic field
   @Shadow
   @Final
   private Minecraft mc;
   // $FF: synthetic field
   @Shadow
   @Final
   private List<ChatLine> drawnChatLines;
   // $FF: synthetic field
   @Shadow
   @Final
   private List<ChatLine> chatLines;
   // $FF: synthetic field
   @Shadow
   private boolean isScrolled;
   // $FF: synthetic field
   @Shadow
   private int scrollPos;

   @Inject(
      method = {"getChatComponent"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void getChatComponent(int lllIlIIllIIIIlI, int lllIlIIllIIIIIl, CallbackInfoReturnable<IChatComponent> lllIlIIllIIIIII) {
      HUD lllIlIIlIllllll = (HUD)LiquidBounce.moduleManager.getModule(HUD.class);
      if (lllIlIIlIllllll.getState() && (Boolean)lllIlIIlIllllll.fontChatValue.get()) {
         if (!lllIlIIllIIIIll.getChatOpen()) {
            lllIlIIllIIIIII.setReturnValue((Object)null);
         } else {
            int lllIlIIlIlllIIl = new ScaledResolution(lllIlIIllIIIIll.mc);
            int lllIlIIllIIIlll = lllIlIIlIlllIIl.getScaleFactor();
            float lllIlIIllIIIllI = lllIlIIllIIIIll.getChatScale();
            double lllIlIIlIllIllI = lllIlIIllIIIIlI / lllIlIIllIIIlll - 3;
            int lllIlIIllIIIlII = lllIlIIllIIIIIl / lllIlIIllIIIlll - 27;
            lllIlIIlIllIllI = MathHelper.floor_float((float)lllIlIIlIllIllI / lllIlIIllIIIllI);
            lllIlIIllIIIlII = MathHelper.floor_float((float)lllIlIIllIIIlII / lllIlIIllIIIllI);
            if (lllIlIIlIllIllI >= 0 && lllIlIIllIIIlII >= 0) {
               int lllIlIIllIIlIIl = Math.min(lllIlIIllIIIIll.getLineCount(), lllIlIIllIIIIll.drawnChatLines.size());
               if (lllIlIIlIllIllI <= MathHelper.floor_float((float)lllIlIIllIIIIll.getChatWidth() / lllIlIIllIIIIll.getChatScale()) && lllIlIIllIIIlII < Fonts.font40.FONT_HEIGHT * lllIlIIllIIlIIl + lllIlIIllIIlIIl) {
                  int lllIlIIllIIlIlI = lllIlIIllIIIlII / Fonts.font40.FONT_HEIGHT + lllIlIIllIIIIll.scrollPos;
                  if (lllIlIIllIIlIlI >= 0 && lllIlIIllIIlIlI < lllIlIIllIIIIll.drawnChatLines.size()) {
                     byte lllIlIIlIllIIlI = (ChatLine)lllIlIIllIIIIll.drawnChatLines.get(lllIlIIllIIlIlI);
                     int lllIlIIllIIllII = 0;
                     Iterator lllIlIIlIllIIII = lllIlIIlIllIIlI.getChatComponent().iterator();

                     while(lllIlIIlIllIIII.hasNext()) {
                        short lllIlIIlIlIllll = (IChatComponent)lllIlIIlIllIIII.next();
                        if (lllIlIIlIlIllll instanceof ChatComponentText) {
                           lllIlIIllIIllII += Fonts.font40.getStringWidth(GuiUtilRenderComponents.func_178909_a(((ChatComponentText)lllIlIIlIlIllll).getChatComponentText_TextValue(), false));
                           if (lllIlIIllIIllII > lllIlIIlIllIllI) {
                              lllIlIIllIIIIII.setReturnValue(lllIlIIlIlIllll);
                              return;
                           }
                        }
                     }
                  }

                  lllIlIIllIIIIII.setReturnValue((Object)null);
               } else {
                  lllIlIIllIIIIII.setReturnValue((Object)null);
               }
            } else {
               lllIlIIllIIIIII.setReturnValue((Object)null);
            }
         }
      }

   }

   @Shadow
   public abstract int getLineCount();

   @Shadow
   public abstract void deleteChatLine(int var1);

   @Shadow
   public abstract void scroll(int var1);

   @Inject(
      method = {"drawChat"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void drawChat(int lllIlIIllllIIIl, CallbackInfo lllIlIIllllIlII) {
      short lllIlIIlllIllll = (HUD)LiquidBounce.moduleManager.getModule(HUD.class);
      if (lllIlIIlllIllll.getState() && (Boolean)lllIlIIlllIllll.fontChatValue.get()) {
         lllIlIIllllIlII.cancel();
         if (lllIlIIllllIIlI.mc.gameSettings.chatVisibility != EnumChatVisibility.HIDDEN) {
            byte lllIlIIlllIlllI = lllIlIIllllIIlI.getLineCount();
            float lllIlIIlllIllIl = false;
            int lllIlIIlllllIIl = 0;
            double lllIlIIlllIlIll = lllIlIIllllIIlI.drawnChatLines.size();
            Exception lllIlIIlllIlIlI = lllIlIIllllIIlI.mc.gameSettings.chatOpacity * 0.9F + 0.1F;
            if (lllIlIIlllIlIll > 0) {
               if (lllIlIIllllIIlI.getChatOpen()) {
                  lllIlIIlllIllIl = true;
               }

               float lllIlIIlllllllI = lllIlIIllllIIlI.getChatScale();
               int lllIlIIllllllIl = MathHelper.ceiling_float_int((float)lllIlIIllllIIlI.getChatWidth() / lllIlIIlllllllI);
               GlStateManager.pushMatrix();
               GlStateManager.translate(2.0F, 20.0F, 0.0F);
               GlStateManager.scale(lllIlIIlllllllI, lllIlIIlllllllI, 1.0F);

               int lllIlIIlllIIlll;
               int lllIlIIlllIIllI;
               int lllIlIlIIIIIllI;
               for(lllIlIIlllIIlll = 0; lllIlIIlllIIlll + lllIlIIllllIIlI.scrollPos < lllIlIIllllIIlI.drawnChatLines.size() && lllIlIIlllIIlll < lllIlIIlllIlllI; ++lllIlIIlllIIlll) {
                  int lllIlIIlllIIlII = (ChatLine)lllIlIIllllIIlI.drawnChatLines.get(lllIlIIlllIIlll + lllIlIIllllIIlI.scrollPos);
                  if (lllIlIIlllIIlII != null) {
                     lllIlIIlllIIllI = lllIlIIllllIIIl - lllIlIIlllIIlII.getUpdatedCounter();
                     if (lllIlIIlllIIllI < 200 || lllIlIIlllIllIl) {
                        double lllIlIlIIIIlIII = (double)lllIlIIlllIIllI / 200.0D;
                        lllIlIlIIIIlIII = 1.0D - lllIlIlIIIIlIII;
                        lllIlIlIIIIlIII *= 10.0D;
                        lllIlIlIIIIlIII = MathHelper.clamp_double(lllIlIlIIIIlIII, 0.0D, 1.0D);
                        lllIlIlIIIIlIII *= lllIlIlIIIIlIII;
                        lllIlIlIIIIIllI = (int)(255.0D * lllIlIlIIIIlIII);
                        if (lllIlIIlllIllIl) {
                           lllIlIlIIIIIllI = 255;
                        }

                        lllIlIlIIIIIllI = (int)((float)lllIlIlIIIIIllI * lllIlIIlllIlIlI);
                        ++lllIlIIlllllIIl;
                        if (lllIlIlIIIIIllI > 3) {
                           short lllIlIIlllIIIIl = 0;
                           String lllIlIIlllIIIII = -lllIlIIlllIIlll * 9;
                           Gui.drawRect(lllIlIIlllIIIIl, lllIlIIlllIIIII - 9, lllIlIIlllIIIIl + lllIlIIllllllIl + 4, lllIlIIlllIIIII, lllIlIlIIIIIllI / 2 << 24);
                           double lllIlIIllIlllll = lllIlIIlllIIlII.getChatComponent().getFormattedText();
                           Fonts.font40.drawStringWithShadow(lllIlIIllIlllll, (float)lllIlIIlllIIIIl + 2.0F, (float)(lllIlIIlllIIIII - 8), 16777215 + (lllIlIlIIIIIllI << 24));
                           boolean var10001 = false;
                           GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                           GlStateManager.resetColor();
                        }
                     }
                  }
               }

               if (lllIlIIlllIllIl) {
                  lllIlIIlllIIlll = Fonts.font40.FONT_HEIGHT;
                  GlStateManager.translate(-3.0F, 0.0F, 0.0F);
                  int lllIlIIlllIIlII = lllIlIIlllIlIll * lllIlIIlllIIlll + lllIlIIlllIlIll;
                  lllIlIIlllIIllI = lllIlIIlllllIIl * lllIlIIlllIIlll + lllIlIIlllllIIl;
                  boolean lllIlIIlllIIIll = lllIlIIllllIIlI.scrollPos * lllIlIIlllIIllI / lllIlIIlllIlIll;
                  float lllIlIIlllIIIlI = lllIlIIlllIIllI * lllIlIIlllIIllI / lllIlIIlllIIlII;
                  if (lllIlIIlllIIlII != lllIlIIlllIIllI) {
                     lllIlIlIIIIIllI = lllIlIIlllIIIll > 0 ? 170 : 96;
                     short lllIlIIlllIIIIl = lllIlIIllllIIlI.isScrolled ? 13382451 : 3355562;
                     Gui.drawRect(0, -lllIlIIlllIIIll, 2, -lllIlIIlllIIIll - lllIlIIlllIIIlI, lllIlIIlllIIIIl + (lllIlIlIIIIIllI << 24));
                     Gui.drawRect(2, -lllIlIIlllIIIll, 1, -lllIlIIlllIIIll - lllIlIIlllIIIlI, 13421772 + (lllIlIlIIIIIllI << 24));
                  }
               }

               GlStateManager.popMatrix();
            }
         }
      }

   }

   @Shadow
   public abstract boolean getChatOpen();

   @Shadow
   public abstract float getChatScale();

   @Shadow
   public abstract int getChatWidth();
}
