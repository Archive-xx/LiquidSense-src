//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.ui.client.clickgui.style.styles;

import java.awt.Color;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import net.ccbluex.liquidbounce.features.module.modules.render.ClickGUI;
import net.ccbluex.liquidbounce.ui.client.clickgui.Panel;
import net.ccbluex.liquidbounce.ui.client.clickgui.elements.ButtonElement;
import net.ccbluex.liquidbounce.ui.client.clickgui.elements.ModuleElement;
import net.ccbluex.liquidbounce.ui.client.clickgui.style.Style;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.ui.font.GameFontRenderer;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BlockValue;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.FontValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.ccbluex.liquidbounce.value.Value;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;

@SideOnly(Side.CLIENT)
public class NullStyle extends Style {
   // $FF: synthetic field
   private boolean mouseDown;
   // $FF: synthetic field
   private boolean rightMouseDown;

   private BigDecimal round(float lllllllllllllllllllllllIIIIllIll) {
      boolean lllllllllllllllllllllllIIIIllIlI = new BigDecimal(Float.toString(lllllllllllllllllllllllIIIIllIll));
      lllllllllllllllllllllllIIIIllIlI = lllllllllllllllllllllllIIIIllIlI.setScale(2, 4);
      return lllllllllllllllllllllllIIIIllIlI;
   }

   public void drawDescription(int lllllllllllllllllllllllIIlllIllI, int lllllllllllllllllllllllIIlllIlIl, String lllllllllllllllllllllllIIllllIII) {
      int lllllllllllllllllllllllIIlllIlll = Fonts.font35.getStringWidth(lllllllllllllllllllllllIIllllIII);
      RenderUtils.drawRect((float)(lllllllllllllllllllllllIIlllIllI + 9), (float)lllllllllllllllllllllllIIlllIlIl, (float)(lllllllllllllllllllllllIIlllIllI + lllllllllllllllllllllllIIlllIlll + 14), (float)(lllllllllllllllllllllllIIlllIlIl + Fonts.font35.FONT_HEIGHT + 3), ClickGUI.generateColor().getRGB());
      GlStateManager.resetColor();
      Fonts.font35.drawString(lllllllllllllllllllllllIIllllIII, lllllllllllllllllllllllIIlllIllI + 12, lllllllllllllllllllllllIIlllIlIl + Fonts.font35.FONT_HEIGHT / 2, Integer.MAX_VALUE);
      boolean var10001 = false;
   }

   public void drawButtonElement(int lllllllllllllllllllllllIIlllIIII, int lllllllllllllllllllllllIIllIllll, ButtonElement lllllllllllllllllllllllIIllIllIl) {
      GlStateManager.resetColor();
      Fonts.font35.drawString(lllllllllllllllllllllllIIllIllIl.getDisplayName(), (int)((float)lllllllllllllllllllllllIIllIllIl.getX() - ((float)Fonts.font35.getStringWidth(lllllllllllllllllllllllIIllIllIl.getDisplayName()) - 100.0F) / 2.0F), lllllllllllllllllllllllIIllIllIl.getY() + 6, lllllllllllllllllllllllIIllIllIl.getColor());
      boolean var10001 = false;
   }

   public void drawModuleElement(int lllllllllllllllllllllllIIIllIIII, int lllllllllllllllllllllllIIIllIlIl, ModuleElement lllllllllllllllllllllllIIIlIlllI) {
      char lllllllllllllllllllllllIIIlIllIl = ClickGUI.generateColor().getRGB();
      GlStateManager.resetColor();
      Fonts.font35.drawString(lllllllllllllllllllllllIIIlIlllI.getDisplayName(), (int)((float)lllllllllllllllllllllllIIIlIlllI.getX() - ((float)Fonts.font35.getStringWidth(lllllllllllllllllllllllIIIlIlllI.getDisplayName()) - 100.0F) / 2.0F), lllllllllllllllllllllllIIIlIlllI.getY() + 6, lllllllllllllllllllllllIIIlIlllI.getModule().getState() ? lllllllllllllllllllllllIIIlIllIl : Integer.MAX_VALUE);
      boolean var10001 = false;
      List<Value<?>> lllllllllllllllllllllllIIIllIIlI = lllllllllllllllllllllllIIIlIlllI.getModule().getValues();
      if (!lllllllllllllllllllllllIIIllIIlI.isEmpty()) {
         Fonts.font35.drawString("+", lllllllllllllllllllllllIIIlIlllI.getX() + lllllllllllllllllllllllIIIlIlllI.getWidth() - 8, lllllllllllllllllllllllIIIlIlllI.getY() + lllllllllllllllllllllllIIIlIlllI.getHeight() / 2, Color.WHITE.getRGB());
         var10001 = false;
         if (lllllllllllllllllllllllIIIlIlllI.isShowSettings()) {
            int lllllllllllllllllllllllIIIlllIII = lllllllllllllllllllllllIIIlIlllI.getY() + 4;
            Iterator lllllllllllllllllllllllIIIlIlIlI = lllllllllllllllllllllllIIIllIIlI.iterator();

            while(true) {
               while(lllllllllllllllllllllllIIIlIlIlI.hasNext()) {
                  Value lllllllllllllllllllllllIIIlllIIl = (Value)lllllllllllllllllllllllIIIlIlIlI.next();
                  String lllllllllllllllllllllllIIIlllIll;
                  float lllllllllllllllllllllllIIIlIIlll;
                  if (lllllllllllllllllllllllIIIlllIIl instanceof BoolValue) {
                     lllllllllllllllllllllllIIIlllIll = lllllllllllllllllllllllIIIlllIIl.getName();
                     lllllllllllllllllllllllIIIlIIlll = (float)Fonts.font35.getStringWidth(lllllllllllllllllllllllIIIlllIll);
                     if (lllllllllllllllllllllllIIIlIlllI.getSettingsWidth() < lllllllllllllllllllllllIIIlIIlll + 8.0F) {
                        lllllllllllllllllllllllIIIlIlllI.setSettingsWidth(lllllllllllllllllllllllIIIlIIlll + 8.0F);
                     }

                     RenderUtils.drawRect((float)(lllllllllllllllllllllllIIIlIlllI.getX() + lllllllllllllllllllllllIIIlIlllI.getWidth() + 4), (float)(lllllllllllllllllllllllIIIlllIII + 2), (float)(lllllllllllllllllllllllIIIlIlllI.getX() + lllllllllllllllllllllllIIIlIlllI.getWidth()) + lllllllllllllllllllllllIIIlIlllI.getSettingsWidth(), (float)(lllllllllllllllllllllllIIIlllIII + 14), Integer.MIN_VALUE);
                     if (lllllllllllllllllllllllIIIllIIII >= lllllllllllllllllllllllIIIlIlllI.getX() + lllllllllllllllllllllllIIIlIlllI.getWidth() + 4 && (float)lllllllllllllllllllllllIIIllIIII <= (float)(lllllllllllllllllllllllIIIlIlllI.getX() + lllllllllllllllllllllllIIIlIlllI.getWidth()) + lllllllllllllllllllllllIIIlIlllI.getSettingsWidth() && lllllllllllllllllllllllIIIllIlIl >= lllllllllllllllllllllllIIIlllIII + 2 && lllllllllllllllllllllllIIIllIlIl <= lllllllllllllllllllllllIIIlllIII + 14 && Mouse.isButtonDown(0) && lllllllllllllllllllllllIIIlIlllI.isntPressed()) {
                        BoolValue lllllllllllllllllllllllIIlIllIll = (BoolValue)lllllllllllllllllllllllIIIlllIIl;
                        lllllllllllllllllllllllIIlIllIll.set(!(Boolean)lllllllllllllllllllllllIIlIllIll.get());
                        mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
                     }

                     GlStateManager.resetColor();
                     Fonts.font35.drawString(lllllllllllllllllllllllIIIlllIll, lllllllllllllllllllllllIIIlIlllI.getX() + lllllllllllllllllllllllIIIlIlllI.getWidth() + 6, lllllllllllllllllllllllIIIlllIII + 4, (Boolean)((BoolValue)lllllllllllllllllllllllIIIlllIIl).get() ? lllllllllllllllllllllllIIIlIllIl : Integer.MAX_VALUE);
                     var10001 = false;
                     lllllllllllllllllllllllIIIlllIII += 12;
                  } else {
                     int lllllllllllllllllllllllIIIlIIIll;
                     String lllllllllllllllllllllllIIIlIIlll;
                     float lllllllllllllllllllllllIIlIIlIII;
                     if (lllllllllllllllllllllllIIIlllIIl instanceof ListValue) {
                        byte lllllllllllllllllllllllIIIlIlIII = (ListValue)lllllllllllllllllllllllIIIlllIIl;
                        lllllllllllllllllllllllIIIlIIlll = lllllllllllllllllllllllIIIlllIIl.getName();
                        lllllllllllllllllllllllIIlIIlIII = (float)Fonts.font35.getStringWidth(lllllllllllllllllllllllIIIlIIlll);
                        if (lllllllllllllllllllllllIIIlIlllI.getSettingsWidth() < lllllllllllllllllllllllIIlIIlIII + 16.0F) {
                           lllllllllllllllllllllllIIIlIlllI.setSettingsWidth(lllllllllllllllllllllllIIlIIlIII + 16.0F);
                        }

                        RenderUtils.drawRect((float)(lllllllllllllllllllllllIIIlIlllI.getX() + lllllllllllllllllllllllIIIlIlllI.getWidth() + 4), (float)(lllllllllllllllllllllllIIIlllIII + 2), (float)(lllllllllllllllllllllllIIIlIlllI.getX() + lllllllllllllllllllllllIIIlIlllI.getWidth()) + lllllllllllllllllllllllIIIlIlllI.getSettingsWidth(), (float)(lllllllllllllllllllllllIIIlllIII + 14), Integer.MIN_VALUE);
                        GlStateManager.resetColor();
                        Fonts.font35.drawString(String.valueOf((new StringBuilder()).append("§c").append(lllllllllllllllllllllllIIIlIIlll)), lllllllllllllllllllllllIIIlIlllI.getX() + lllllllllllllllllllllllIIIlIlllI.getWidth() + 6, lllllllllllllllllllllllIIIlllIII + 4, 16777215);
                        var10001 = false;
                        Fonts.font35.drawString(lllllllllllllllllllllllIIIlIlIII.openList ? "-" : "+", (int)((float)(lllllllllllllllllllllllIIIlIlllI.getX() + lllllllllllllllllllllllIIIlIlllI.getWidth()) + lllllllllllllllllllllllIIIlIlllI.getSettingsWidth() - (float)(lllllllllllllllllllllllIIIlIlIII.openList ? 5 : 6)), lllllllllllllllllllllllIIIlllIII + 4, 16777215);
                        var10001 = false;
                        if (lllllllllllllllllllllllIIIllIIII >= lllllllllllllllllllllllIIIlIlllI.getX() + lllllllllllllllllllllllIIIlIlllI.getWidth() + 4 && (float)lllllllllllllllllllllllIIIllIIII <= (float)(lllllllllllllllllllllllIIIlIlllI.getX() + lllllllllllllllllllllllIIIlIlllI.getWidth()) + lllllllllllllllllllllllIIIlIlllI.getSettingsWidth() && lllllllllllllllllllllllIIIllIlIl >= lllllllllllllllllllllllIIIlllIII + 2 && lllllllllllllllllllllllIIIllIlIl <= lllllllllllllllllllllllIIIlllIII + 14 && Mouse.isButtonDown(0) && lllllllllllllllllllllllIIIlIlllI.isntPressed()) {
                           lllllllllllllllllllllllIIIlIlIII.openList = !lllllllllllllllllllllllIIIlIlIII.openList;
                           mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
                        }

                        lllllllllllllllllllllllIIIlllIII += 12;
                        char lllllllllllllllllllllllIIIlIIlIl = lllllllllllllllllllllllIIIlIlIII.getValues();
                        String lllllllllllllllllllllllIIIlIIlII = lllllllllllllllllllllllIIIlIIlIl.length;

                        for(lllllllllllllllllllllllIIIlIIIll = 0; lllllllllllllllllllllllIIIlIIIll < lllllllllllllllllllllllIIIlIIlII; ++lllllllllllllllllllllllIIIlIIIll) {
                           float lllllllllllllllllllllllIIIlIIIlI = lllllllllllllllllllllllIIIlIIlIl[lllllllllllllllllllllllIIIlIIIll];
                           int lllllllllllllllllllllllIIIlIIIIl = (float)Fonts.font35.getStringWidth(String.valueOf((new StringBuilder()).append(">").append(lllllllllllllllllllllllIIIlIIIlI)));
                           if (lllllllllllllllllllllllIIIlIlllI.getSettingsWidth() < lllllllllllllllllllllllIIIlIIIIl + 12.0F) {
                              lllllllllllllllllllllllIIIlIlllI.setSettingsWidth(lllllllllllllllllllllllIIIlIIIIl + 12.0F);
                           }

                           if (lllllllllllllllllllllllIIIlIlIII.openList) {
                              RenderUtils.drawRect((float)(lllllllllllllllllllllllIIIlIlllI.getX() + lllllllllllllllllllllllIIIlIlllI.getWidth() + 4), (float)(lllllllllllllllllllllllIIIlllIII + 2), (float)(lllllllllllllllllllllllIIIlIlllI.getX() + lllllllllllllllllllllllIIIlIlllI.getWidth()) + lllllllllllllllllllllllIIIlIlllI.getSettingsWidth(), (float)(lllllllllllllllllllllllIIIlllIII + 14), Integer.MIN_VALUE);
                              if (lllllllllllllllllllllllIIIllIIII >= lllllllllllllllllllllllIIIlIlllI.getX() + lllllllllllllllllllllllIIIlIlllI.getWidth() + 4 && (float)lllllllllllllllllllllllIIIllIIII <= (float)(lllllllllllllllllllllllIIIlIlllI.getX() + lllllllllllllllllllllllIIIlIlllI.getWidth()) + lllllllllllllllllllllllIIIlIlllI.getSettingsWidth() && lllllllllllllllllllllllIIIllIlIl >= lllllllllllllllllllllllIIIlllIII + 2 && lllllllllllllllllllllllIIIllIlIl <= lllllllllllllllllllllllIIIlllIII + 14 && Mouse.isButtonDown(0) && lllllllllllllllllllllllIIIlIlllI.isntPressed()) {
                                 lllllllllllllllllllllllIIIlIlIII.set(lllllllllllllllllllllllIIIlIIIlI);
                                 mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
                              }

                              GlStateManager.resetColor();
                              Fonts.font35.drawString(">", lllllllllllllllllllllllIIIlIlllI.getX() + lllllllllllllllllllllllIIIlIlllI.getWidth() + 6, lllllllllllllllllllllllIIIlllIII + 4, Integer.MAX_VALUE);
                              var10001 = false;
                              Fonts.font35.drawString(lllllllllllllllllllllllIIIlIIIlI, lllllllllllllllllllllllIIIlIlllI.getX() + lllllllllllllllllllllllIIIlIlllI.getWidth() + 14, lllllllllllllllllllllllIIIlllIII + 4, lllllllllllllllllllllllIIIlIlIII.get() != null && ((String)lllllllllllllllllllllllIIIlIlIII.get()).equalsIgnoreCase(lllllllllllllllllllllllIIIlIIIlI) ? lllllllllllllllllllllllIIIlIllIl : Integer.MAX_VALUE);
                              var10001 = false;
                              lllllllllllllllllllllllIIIlllIII += 12;
                           }
                        }
                     } else {
                        float lllllllllllllllllllllllIIlIIIlll;
                        double lllllllllllllllllllllllIIlIIlllI;
                        if (lllllllllllllllllllllllIIIlllIIl instanceof FloatValue) {
                           byte lllllllllllllllllllllllIIIlIlIII = (FloatValue)lllllllllllllllllllllllIIIlllIIl;
                           lllllllllllllllllllllllIIIlIIlll = String.valueOf((new StringBuilder()).append(lllllllllllllllllllllllIIIlllIIl.getName()).append("§f: §c").append(lllllllllllllllllllllllIIIllIIIl.round((Float)lllllllllllllllllllllllIIIlIlIII.get())));
                           lllllllllllllllllllllllIIlIIlIII = (float)Fonts.font35.getStringWidth(lllllllllllllllllllllllIIIlIIlll);
                           if (lllllllllllllllllllllllIIIlIlllI.getSettingsWidth() < lllllllllllllllllllllllIIlIIlIII + 8.0F) {
                              lllllllllllllllllllllllIIIlIlllI.setSettingsWidth(lllllllllllllllllllllllIIlIIlIII + 8.0F);
                           }

                           RenderUtils.drawRect((float)(lllllllllllllllllllllllIIIlIlllI.getX() + lllllllllllllllllllllllIIIlIlllI.getWidth() + 4), (float)(lllllllllllllllllllllllIIIlllIII + 2), (float)(lllllllllllllllllllllllIIIlIlllI.getX() + lllllllllllllllllllllllIIIlIlllI.getWidth()) + lllllllllllllllllllllllIIIlIlllI.getSettingsWidth(), (float)(lllllllllllllllllllllllIIIlllIII + 24), Integer.MIN_VALUE);
                           RenderUtils.drawRect((float)(lllllllllllllllllllllllIIIlIlllI.getX() + lllllllllllllllllllllllIIIlIlllI.getWidth() + 8), (float)(lllllllllllllllllllllllIIIlllIII + 18), (float)(lllllllllllllllllllllllIIIlIlllI.getX() + lllllllllllllllllllllllIIIlIlllI.getWidth()) + lllllllllllllllllllllllIIIlIlllI.getSettingsWidth() - 4.0F, (float)(lllllllllllllllllllllllIIIlllIII + 19), Integer.MAX_VALUE);
                           lllllllllllllllllllllllIIlIIIlll = (float)(lllllllllllllllllllllllIIIlIlllI.getX() + lllllllllllllllllllllllIIIlIlllI.getWidth()) + (lllllllllllllllllllllllIIIlIlllI.getSettingsWidth() - 12.0F) * ((Float)lllllllllllllllllllllllIIIlIlIII.get() - lllllllllllllllllllllllIIIlIlIII.getMinimum()) / (lllllllllllllllllllllllIIIlIlIII.getMaximum() - lllllllllllllllllllllllIIIlIlIII.getMinimum());
                           RenderUtils.drawRect(8.0F + lllllllllllllllllllllllIIlIIIlll, (float)(lllllllllllllllllllllllIIIlllIII + 15), lllllllllllllllllllllllIIlIIIlll + 11.0F, (float)(lllllllllllllllllllllllIIIlllIII + 21), lllllllllllllllllllllllIIIlIllIl);
                           if (lllllllllllllllllllllllIIIllIIII >= lllllllllllllllllllllllIIIlIlllI.getX() + lllllllllllllllllllllllIIIlIlllI.getWidth() + 4 && (float)lllllllllllllllllllllllIIIllIIII <= (float)(lllllllllllllllllllllllIIIlIlllI.getX() + lllllllllllllllllllllllIIIlIlllI.getWidth()) + lllllllllllllllllllllllIIIlIlllI.getSettingsWidth() - 4.0F && lllllllllllllllllllllllIIIllIlIl >= lllllllllllllllllllllllIIIlllIII + 15 && lllllllllllllllllllllllIIIllIlIl <= lllllllllllllllllllllllIIIlllIII + 21 && Mouse.isButtonDown(0)) {
                              lllllllllllllllllllllllIIlIIlllI = MathHelper.clamp_double((double)((float)(lllllllllllllllllllllllIIIllIIII - lllllllllllllllllllllllIIIlIlllI.getX() - lllllllllllllllllllllllIIIlIlllI.getWidth() - 8) / (lllllllllllllllllllllllIIIlIlllI.getSettingsWidth() - 12.0F)), 0.0D, 1.0D);
                              lllllllllllllllllllllllIIIlIlIII.set(lllllllllllllllllllllllIIIllIIIl.round((float)((double)lllllllllllllllllllllllIIIlIlIII.getMinimum() + (double)(lllllllllllllllllllllllIIIlIlIII.getMaximum() - lllllllllllllllllllllllIIIlIlIII.getMinimum()) * lllllllllllllllllllllllIIlIIlllI)).floatValue());
                           }

                           GlStateManager.resetColor();
                           Fonts.font35.drawString(lllllllllllllllllllllllIIIlIIlll, lllllllllllllllllllllllIIIlIlllI.getX() + lllllllllllllllllllllllIIIlIlllI.getWidth() + 6, lllllllllllllllllllllllIIIlllIII + 4, 16777215);
                           var10001 = false;
                           lllllllllllllllllllllllIIIlllIII += 22;
                        } else if (lllllllllllllllllllllllIIIlllIIl instanceof IntegerValue) {
                           byte lllllllllllllllllllllllIIIlIlIII = (IntegerValue)lllllllllllllllllllllllIIIlllIIl;
                           lllllllllllllllllllllllIIIlIIlll = String.valueOf((new StringBuilder()).append(lllllllllllllllllllllllIIIlllIIl.getName()).append("§f: §c").append(lllllllllllllllllllllllIIIlllIIl instanceof BlockValue ? String.valueOf((new StringBuilder()).append(BlockUtils.getBlockName((Integer)lllllllllllllllllllllllIIIlIlIII.get())).append(" (").append(lllllllllllllllllllllllIIIlIlIII.get()).append(")")) : (Serializable)lllllllllllllllllllllllIIIlIlIII.get()));
                           lllllllllllllllllllllllIIlIIlIII = (float)Fonts.font35.getStringWidth(lllllllllllllllllllllllIIIlIIlll);
                           if (lllllllllllllllllllllllIIIlIlllI.getSettingsWidth() < lllllllllllllllllllllllIIlIIlIII + 8.0F) {
                              lllllllllllllllllllllllIIIlIlllI.setSettingsWidth(lllllllllllllllllllllllIIlIIlIII + 8.0F);
                           }

                           RenderUtils.drawRect((float)(lllllllllllllllllllllllIIIlIlllI.getX() + lllllllllllllllllllllllIIIlIlllI.getWidth() + 4), (float)(lllllllllllllllllllllllIIIlllIII + 2), (float)(lllllllllllllllllllllllIIIlIlllI.getX() + lllllllllllllllllllllllIIIlIlllI.getWidth()) + lllllllllllllllllllllllIIIlIlllI.getSettingsWidth(), (float)(lllllllllllllllllllllllIIIlllIII + 24), Integer.MIN_VALUE);
                           RenderUtils.drawRect((float)(lllllllllllllllllllllllIIIlIlllI.getX() + lllllllllllllllllllllllIIIlIlllI.getWidth() + 8), (float)(lllllllllllllllllllllllIIIlllIII + 18), (float)(lllllllllllllllllllllllIIIlIlllI.getX() + lllllllllllllllllllllllIIIlIlllI.getWidth()) + lllllllllllllllllllllllIIIlIlllI.getSettingsWidth() - 4.0F, (float)(lllllllllllllllllllllllIIIlllIII + 19), Integer.MAX_VALUE);
                           lllllllllllllllllllllllIIlIIIlll = (float)(lllllllllllllllllllllllIIIlIlllI.getX() + lllllllllllllllllllllllIIIlIlllI.getWidth()) + (lllllllllllllllllllllllIIIlIlllI.getSettingsWidth() - 12.0F) * (float)((Integer)lllllllllllllllllllllllIIIlIlIII.get() - lllllllllllllllllllllllIIIlIlIII.getMinimum()) / (float)(lllllllllllllllllllllllIIIlIlIII.getMaximum() - lllllllllllllllllllllllIIIlIlIII.getMinimum());
                           RenderUtils.drawRect(8.0F + lllllllllllllllllllllllIIlIIIlll, (float)(lllllllllllllllllllllllIIIlllIII + 15), lllllllllllllllllllllllIIlIIIlll + 11.0F, (float)(lllllllllllllllllllllllIIIlllIII + 21), lllllllllllllllllllllllIIIlIllIl);
                           if (lllllllllllllllllllllllIIIllIIII >= lllllllllllllllllllllllIIIlIlllI.getX() + lllllllllllllllllllllllIIIlIlllI.getWidth() + 4 && (float)lllllllllllllllllllllllIIIllIIII <= (float)(lllllllllllllllllllllllIIIlIlllI.getX() + lllllllllllllllllllllllIIIlIlllI.getWidth()) + lllllllllllllllllllllllIIIlIlllI.getSettingsWidth() && lllllllllllllllllllllllIIIllIlIl >= lllllllllllllllllllllllIIIlllIII + 15 && lllllllllllllllllllllllIIIllIlIl <= lllllllllllllllllllllllIIIlllIII + 21 && Mouse.isButtonDown(0)) {
                              lllllllllllllllllllllllIIlIIlllI = MathHelper.clamp_double((double)((float)(lllllllllllllllllllllllIIIllIIII - lllllllllllllllllllllllIIIlIlllI.getX() - lllllllllllllllllllllllIIIlIlllI.getWidth() - 8) / (lllllllllllllllllllllllIIIlIlllI.getSettingsWidth() - 12.0F)), 0.0D, 1.0D);
                              lllllllllllllllllllllllIIIlIlIII.set((int)((double)lllllllllllllllllllllllIIIlIlIII.getMinimum() + (double)(lllllllllllllllllllllllIIIlIlIII.getMaximum() - lllllllllllllllllllllllIIIlIlIII.getMinimum()) * lllllllllllllllllllllllIIlIIlllI));
                           }

                           GlStateManager.resetColor();
                           Fonts.font35.drawString(lllllllllllllllllllllllIIIlIIlll, lllllllllllllllllllllllIIIlIlllI.getX() + lllllllllllllllllllllllIIIlIlllI.getWidth() + 6, lllllllllllllllllllllllIIIlllIII + 4, 16777215);
                           var10001 = false;
                           lllllllllllllllllllllllIIIlllIII += 22;
                        } else if (lllllllllllllllllllllllIIIlllIIl instanceof FontValue) {
                           FontValue lllllllllllllllllllllllIIIllllll = (FontValue)lllllllllllllllllllllllIIIlllIIl;
                           Exception lllllllllllllllllllllllIIIlIIlll = (FontRenderer)lllllllllllllllllllllllIIIllllll.get();
                           RenderUtils.drawRect((float)(lllllllllllllllllllllllIIIlIlllI.getX() + lllllllllllllllllllllllIIIlIlllI.getWidth() + 4), (float)(lllllllllllllllllllllllIIIlllIII + 2), (float)(lllllllllllllllllllllllIIIlIlllI.getX() + lllllllllllllllllllllllIIIlIlllI.getWidth()) + lllllllllllllllllllllllIIIlIlllI.getSettingsWidth(), (float)(lllllllllllllllllllllllIIIlllIII + 14), Integer.MIN_VALUE);
                           String lllllllllllllllllllllllIIIllllIl = "Font: Unknown";
                           if (lllllllllllllllllllllllIIIlIIlll instanceof GameFontRenderer) {
                              GameFontRenderer lllllllllllllllllllllllIIlIIIllI = (GameFontRenderer)lllllllllllllllllllllllIIIlIIlll;
                              lllllllllllllllllllllllIIIllllIl = String.valueOf((new StringBuilder()).append("Font: ").append(lllllllllllllllllllllllIIlIIIllI.getDefaultFont().getFont().getName()).append(" - ").append(lllllllllllllllllllllllIIlIIIllI.getDefaultFont().getFont().getSize()));
                           } else if (lllllllllllllllllllllllIIIlIIlll == Fonts.minecraftFont) {
                              lllllllllllllllllllllllIIIllllIl = "Font: Minecraft";
                           } else {
                              char lllllllllllllllllllllllIIIlIIlIl = Fonts.getFontDetails(lllllllllllllllllllllllIIIlIIlll);
                              if (lllllllllllllllllllllllIIIlIIlIl != null) {
                                 lllllllllllllllllllllllIIIllllIl = String.valueOf((new StringBuilder()).append(lllllllllllllllllllllllIIIlIIlIl[0]).append((Integer)lllllllllllllllllllllllIIIlIIlIl[1] != -1 ? String.valueOf((new StringBuilder()).append(" - ").append(lllllllllllllllllllllllIIIlIIlIl[1])) : ""));
                              }
                           }

                           Fonts.font35.drawString(lllllllllllllllllllllllIIIllllIl, lllllllllllllllllllllllIIIlIlllI.getX() + lllllllllllllllllllllllIIIlIlllI.getWidth() + 6, lllllllllllllllllllllllIIIlllIII + 4, Color.WHITE.getRGB());
                           var10001 = false;
                           char lllllllllllllllllllllllIIIlIIlIl = Fonts.font35.getStringWidth(lllllllllllllllllllllllIIIllllIl);
                           if (lllllllllllllllllllllllIIIlIlllI.getSettingsWidth() < (float)(lllllllllllllllllllllllIIIlIIlIl + 8)) {
                              lllllllllllllllllllllllIIIlIlllI.setSettingsWidth((float)(lllllllllllllllllllllllIIIlIIlIl + 8));
                           }

                           if ((Mouse.isButtonDown(0) && !lllllllllllllllllllllllIIIllIIIl.mouseDown || Mouse.isButtonDown(1) && !lllllllllllllllllllllllIIIllIIIl.rightMouseDown) && lllllllllllllllllllllllIIIllIIII >= lllllllllllllllllllllllIIIlIlllI.getX() + lllllllllllllllllllllllIIIlIlllI.getWidth() + 4 && (float)lllllllllllllllllllllllIIIllIIII <= (float)(lllllllllllllllllllllllIIIlIlllI.getX() + lllllllllllllllllllllllIIIlIlllI.getWidth()) + lllllllllllllllllllllllIIIlIlllI.getSettingsWidth() && lllllllllllllllllllllllIIIllIlIl >= lllllllllllllllllllllllIIIlllIII + 4 && lllllllllllllllllllllllIIIllIlIl <= lllllllllllllllllllllllIIIlllIII + 12) {
                              List<FontRenderer> lllllllllllllllllllllllIIIlIIlII = Fonts.getFonts();
                              FontRenderer lllllllllllllllllllllllIIlIIIlII;
                              if (Mouse.isButtonDown(0)) {
                                 for(lllllllllllllllllllllllIIIlIIIll = 0; lllllllllllllllllllllllIIIlIIIll < lllllllllllllllllllllllIIIlIIlII.size(); ++lllllllllllllllllllllllIIIlIIIll) {
                                    lllllllllllllllllllllllIIlIIIlII = (FontRenderer)lllllllllllllllllllllllIIIlIIlII.get(lllllllllllllllllllllllIIIlIIIll);
                                    if (lllllllllllllllllllllllIIlIIIlII == lllllllllllllllllllllllIIIlIIlll) {
                                       ++lllllllllllllllllllllllIIIlIIIll;
                                       if (lllllllllllllllllllllllIIIlIIIll >= lllllllllllllllllllllllIIIlIIlII.size()) {
                                          lllllllllllllllllllllllIIIlIIIll = 0;
                                       }

                                       lllllllllllllllllllllllIIIllllll.set(lllllllllllllllllllllllIIIlIIlII.get(lllllllllllllllllllllllIIIlIIIll));
                                       break;
                                    }
                                 }
                              } else {
                                 for(lllllllllllllllllllllllIIIlIIIll = lllllllllllllllllllllllIIIlIIlII.size() - 1; lllllllllllllllllllllllIIIlIIIll >= 0; --lllllllllllllllllllllllIIIlIIIll) {
                                    lllllllllllllllllllllllIIlIIIlII = (FontRenderer)lllllllllllllllllllllllIIIlIIlII.get(lllllllllllllllllllllllIIIlIIIll);
                                    if (lllllllllllllllllllllllIIlIIIlII == lllllllllllllllllllllllIIIlIIlll) {
                                       --lllllllllllllllllllllllIIIlIIIll;
                                       if (lllllllllllllllllllllllIIIlIIIll >= lllllllllllllllllllllllIIIlIIlII.size()) {
                                          lllllllllllllllllllllllIIIlIIIll = 0;
                                       }

                                       if (lllllllllllllllllllllllIIIlIIIll < 0) {
                                          lllllllllllllllllllllllIIIlIIIll = lllllllllllllllllllllllIIIlIIlII.size() - 1;
                                       }

                                       lllllllllllllllllllllllIIIllllll.set(lllllllllllllllllllllllIIIlIIlII.get(lllllllllllllllllllllllIIIlIIIll));
                                       break;
                                    }
                                 }
                              }
                           }

                           lllllllllllllllllllllllIIIlllIII += 11;
                        } else {
                           lllllllllllllllllllllllIIIlllIll = String.valueOf((new StringBuilder()).append(lllllllllllllllllllllllIIIlllIIl.getName()).append("§f: §c").append(lllllllllllllllllllllllIIIlllIIl.get()));
                           lllllllllllllllllllllllIIIlIIlll = (float)Fonts.font35.getStringWidth(lllllllllllllllllllllllIIIlllIll);
                           if (lllllllllllllllllllllllIIIlIlllI.getSettingsWidth() < lllllllllllllllllllllllIIIlIIlll + 8.0F) {
                              lllllllllllllllllllllllIIIlIlllI.setSettingsWidth(lllllllllllllllllllllllIIIlIIlll + 8.0F);
                           }

                           RenderUtils.drawRect((float)(lllllllllllllllllllllllIIIlIlllI.getX() + lllllllllllllllllllllllIIIlIlllI.getWidth() + 4), (float)(lllllllllllllllllllllllIIIlllIII + 2), (float)(lllllllllllllllllllllllIIIlIlllI.getX() + lllllllllllllllllllllllIIIlIlllI.getWidth()) + lllllllllllllllllllllllIIIlIlllI.getSettingsWidth(), (float)(lllllllllllllllllllllllIIIlllIII + 14), Integer.MIN_VALUE);
                           GlStateManager.resetColor();
                           Fonts.font35.drawString(lllllllllllllllllllllllIIIlllIll, lllllllllllllllllllllllIIIlIlllI.getX() + lllllllllllllllllllllllIIIlIlllI.getWidth() + 6, lllllllllllllllllllllllIIIlllIII + 4, 16777215);
                           var10001 = false;
                           lllllllllllllllllllllllIIIlllIII += 12;
                        }
                     }
                  }
               }

               lllllllllllllllllllllllIIIlIlllI.updatePressed();
               lllllllllllllllllllllllIIIllIIIl.mouseDown = Mouse.isButtonDown(0);
               lllllllllllllllllllllllIIIllIIIl.rightMouseDown = Mouse.isButtonDown(1);
               if (lllllllllllllllllllllllIIIlIlllI.getSettingsWidth() > 0.0F && lllllllllllllllllllllllIIIlllIII > lllllllllllllllllllllllIIIlIlllI.getY() + 4) {
                  RenderUtils.drawBorderedRect((float)(lllllllllllllllllllllllIIIlIlllI.getX() + lllllllllllllllllllllllIIIlIlllI.getWidth() + 4), (float)(lllllllllllllllllllllllIIIlIlllI.getY() + 6), (float)(lllllllllllllllllllllllIIIlIlllI.getX() + lllllllllllllllllllllllIIIlIlllI.getWidth()) + lllllllllllllllllllllllIIIlIlllI.getSettingsWidth(), (float)(lllllllllllllllllllllllIIIlllIII + 2), 1.0F, Integer.MIN_VALUE, 0);
               }
               break;
            }
         }
      }

   }

   public void drawPanel(int lllllllllllllllllllllllIlIIIIlIl, int lllllllllllllllllllllllIlIIIIlII, Panel lllllllllllllllllllllllIlIIIIIIl) {
      RenderUtils.drawRect((float)lllllllllllllllllllllllIlIIIIIIl.getX() - 3.0F, (float)lllllllllllllllllllllllIlIIIIIIl.getY(), (float)lllllllllllllllllllllllIlIIIIIIl.getX() + (float)lllllllllllllllllllllllIlIIIIIIl.getWidth() + 3.0F, (float)lllllllllllllllllllllllIlIIIIIIl.getY() + 19.0F, ClickGUI.generateColor().getRGB());
      if (lllllllllllllllllllllllIlIIIIIIl.getFade() > 0) {
         RenderUtils.drawBorderedRect((float)lllllllllllllllllllllllIlIIIIIIl.getX(), (float)lllllllllllllllllllllllIlIIIIIIl.getY() + 19.0F, (float)lllllllllllllllllllllllIlIIIIIIl.getX() + (float)lllllllllllllllllllllllIlIIIIIIl.getWidth(), (float)(lllllllllllllllllllllllIlIIIIIIl.getY() + 19 + lllllllllllllllllllllllIlIIIIIIl.getFade()), 1.0F, Integer.MIN_VALUE, Integer.MIN_VALUE);
      }

      GlStateManager.resetColor();
      boolean lllllllllllllllllllllllIlIIIIIII = (float)Fonts.font35.getStringWidth(String.valueOf((new StringBuilder()).append("§f").append(StringUtils.stripControlCodes(lllllllllllllllllllllllIlIIIIIIl.getName()))));
      Fonts.font35.drawString(String.valueOf((new StringBuilder()).append("§f").append(lllllllllllllllllllllllIlIIIIIIl.getName())), (int)((float)lllllllllllllllllllllllIlIIIIIIl.getX() - (lllllllllllllllllllllllIlIIIIIII - 100.0F) / 2.0F), lllllllllllllllllllllllIlIIIIIIl.getY() + 7, Integer.MAX_VALUE);
      boolean var10001 = false;
   }
}
