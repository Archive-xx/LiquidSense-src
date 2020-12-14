//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.ui.client.clickgui.style.styles;

import java.awt.Color;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
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
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;

@SideOnly(Side.CLIENT)
public class BlackStyle extends Style {
   // $FF: synthetic field
   private boolean mouseDown;
   // $FF: synthetic field
   private boolean rightMouseDown;

   private Color hoverColor(Color lIIlIlIlIIllIlI, int lIIlIlIlIIllIIl) {
      Exception lIIlIlIlIIllIII = lIIlIlIlIIllIlI.getRed() - lIIlIlIlIIllIIl * 2;
      String lIIlIlIlIIlIlll = lIIlIlIlIIllIlI.getGreen() - lIIlIlIlIIllIIl * 2;
      char lIIlIlIlIIlIllI = lIIlIlIlIIllIlI.getBlue() - lIIlIlIlIIllIIl * 2;
      return new Color(Math.max(lIIlIlIlIIllIII, 0), Math.max(lIIlIlIlIIlIlll, 0), Math.max(lIIlIlIlIIlIllI, 0), lIIlIlIlIIllIlI.getAlpha());
   }

   public void drawDescription(int lIIlIlIlllllIIl, int lIIlIlIlllllIII, String lIIlIlIllllIlll) {
      double lIIlIlIllllIllI = Fonts.font35.getStringWidth(lIIlIlIllllIlll);
      RenderUtils.drawBorderedRect((float)(lIIlIlIlllllIIl + 9), (float)lIIlIlIlllllIII, (float)(lIIlIlIlllllIIl + lIIlIlIllllIllI + 14), (float)(lIIlIlIlllllIII + Fonts.font35.FONT_HEIGHT + 3), 3.0F, (new Color(40, 40, 40)).getRGB(), (new Color(40, 40, 40)).getRGB());
      GlStateManager.resetColor();
      Fonts.font35.drawString(lIIlIlIllllIlll, lIIlIlIlllllIIl + 12, lIIlIlIlllllIII + Fonts.font35.FONT_HEIGHT / 2, Color.WHITE.getRGB());
      boolean var10001 = false;
   }

   private BigDecimal round(float lIIlIlIlIlIIlll) {
      BigDecimal lIIlIlIlIlIlIII = new BigDecimal(Float.toString(lIIlIlIlIlIIlll));
      lIIlIlIlIlIlIII = lIIlIlIlIlIlIII.setScale(2, 4);
      return lIIlIlIlIlIlIII;
   }

   public void drawPanel(int lIIlIllIIIIlIII, int lIIlIllIIIIIlll, Panel lIIlIllIIIIIlII) {
      RenderUtils.drawBorderedRect((float)lIIlIllIIIIIlII.getX(), (float)lIIlIllIIIIIlII.getY() - 3.0F, (float)lIIlIllIIIIIlII.getX() + (float)lIIlIllIIIIIlII.getWidth(), (float)lIIlIllIIIIIlII.getY() + 17.0F, 3.0F, (new Color(20, 20, 20)).getRGB(), (new Color(20, 20, 20)).getRGB());
      if (lIIlIllIIIIIlII.getFade() > 0) {
         RenderUtils.drawBorderedRect((float)lIIlIllIIIIIlII.getX(), (float)lIIlIllIIIIIlII.getY() + 17.0F, (float)lIIlIllIIIIIlII.getX() + (float)lIIlIllIIIIIlII.getWidth(), (float)(lIIlIllIIIIIlII.getY() + 19 + lIIlIllIIIIIlII.getFade()), 3.0F, (new Color(40, 40, 40)).getRGB(), (new Color(40, 40, 40)).getRGB());
         RenderUtils.drawBorderedRect((float)lIIlIllIIIIIlII.getX(), (float)(lIIlIllIIIIIlII.getY() + 17 + lIIlIllIIIIIlII.getFade()), (float)lIIlIllIIIIIlII.getX() + (float)lIIlIllIIIIIlII.getWidth(), (float)(lIIlIllIIIIIlII.getY() + 19 + lIIlIllIIIIIlII.getFade() + 5), 3.0F, (new Color(20, 20, 20)).getRGB(), (new Color(20, 20, 20)).getRGB());
      }

      GlStateManager.resetColor();
      long lIIlIllIIIIIIll = (float)Fonts.font35.getStringWidth(String.valueOf((new StringBuilder()).append("§f").append(StringUtils.stripControlCodes(lIIlIllIIIIIlII.getName()))));
      Fonts.font35.drawString(lIIlIllIIIIIlII.getName(), (int)((float)lIIlIllIIIIIlII.getX() - (lIIlIllIIIIIIll - 100.0F) / 2.0F), lIIlIllIIIIIlII.getY() + 7 - 3, Color.WHITE.getRGB());
      boolean var10001 = false;
   }

   public static float drawSlider(float lIIlIlIlIIIIllI, float lIIlIlIlIIIIlIl, float lIIlIlIlIIIIlII, int lIIlIlIIllllIII, int lIIlIlIlIIIIIlI, int lIIlIlIIlllIllI, int lIIlIlIlIIIIIII, int lIIlIlIIlllIlII, Color lIIlIlIIlllIIll) {
      double lIIlIlIIlllIIlI = Math.max(lIIlIlIlIIIIlIl, Math.min(lIIlIlIlIIIIllI, lIIlIlIlIIIIlII));
      double lIIlIlIIlllIIIl = (float)lIIlIlIIllllIII + (float)lIIlIlIIlllIllI * (lIIlIlIIlllIIlI - lIIlIlIlIIIIlIl) / (lIIlIlIlIIIIlII - lIIlIlIlIIIIlIl);
      RenderUtils.drawRect((float)lIIlIlIIllllIII, (float)lIIlIlIlIIIIIlI, (float)(lIIlIlIIllllIII + lIIlIlIIlllIllI), (float)(lIIlIlIlIIIIIlI + 2), Integer.MAX_VALUE);
      RenderUtils.drawRect((float)lIIlIlIIllllIII, (float)lIIlIlIlIIIIIlI, lIIlIlIIlllIIIl, (float)(lIIlIlIlIIIIIlI + 2), lIIlIlIIlllIIll);
      RenderUtils.drawFilledCircle((int)lIIlIlIIlllIIIl, lIIlIlIlIIIIIlI + 1, 3.0F, lIIlIlIIlllIIll);
      if (lIIlIlIlIIIIIII >= lIIlIlIIllllIII && lIIlIlIlIIIIIII <= lIIlIlIIllllIII + lIIlIlIIlllIllI && lIIlIlIIlllIlII >= lIIlIlIlIIIIIlI && lIIlIlIIlllIlII <= lIIlIlIlIIIIIlI + 3 && Mouse.isButtonDown(0)) {
         double lIIlIlIlIIIlIII = MathHelper.clamp_double(((double)lIIlIlIlIIIIIII - (double)lIIlIlIIllllIII) / ((double)lIIlIlIIlllIllI - 3.0D), 0.0D, 1.0D);
         Exception lIIlIlIIllIllll = new BigDecimal(Double.toString((double)lIIlIlIlIIIIlIl + (double)(lIIlIlIlIIIIlII - lIIlIlIlIIIIlIl) * lIIlIlIlIIIlIII));
         lIIlIlIIllIllll = lIIlIlIIllIllll.setScale(2, 4);
         return lIIlIlIIllIllll.floatValue();
      } else {
         return lIIlIlIlIIIIllI;
      }
   }

   public void drawModuleElement(int lIIlIlIlIlllIlI, int lIIlIlIlIlllIIl, ModuleElement lIIlIlIlIllllIl) {
      Gui.drawRect(lIIlIlIlIllllIl.getX() - 1, lIIlIlIlIllllIl.getY() - 1, lIIlIlIlIllllIl.getX() + lIIlIlIlIllllIl.getWidth() + 1, lIIlIlIlIllllIl.getY() + lIIlIlIlIllllIl.getHeight() + 1, lIIlIlIllIIIIII.hoverColor(new Color(40, 40, 40), lIIlIlIlIllllIl.hoverTime).getRGB());
      Gui.drawRect(lIIlIlIlIllllIl.getX() - 1, lIIlIlIlIllllIl.getY() - 1, lIIlIlIlIllllIl.getX() + lIIlIlIlIllllIl.getWidth() + 1, lIIlIlIlIllllIl.getY() + lIIlIlIlIllllIl.getHeight() + 1, lIIlIlIllIIIIII.hoverColor(new Color(20, 20, 20, lIIlIlIlIllllIl.slowlyFade), lIIlIlIlIllllIl.hoverTime).getRGB());
      GlStateManager.resetColor();
      Fonts.font35.drawString(lIIlIlIlIllllIl.getDisplayName(), lIIlIlIlIllllIl.getX() + 5, lIIlIlIlIllllIl.getY() + 5, Color.WHITE.getRGB());
      boolean var10001 = false;
      List<Value<?>> lIIlIlIlIllIlll = lIIlIlIlIllllIl.getModule().getValues();
      if (!lIIlIlIlIllIlll.isEmpty()) {
         Fonts.font35.drawString(">", lIIlIlIlIllllIl.getX() + lIIlIlIlIllllIl.getWidth() - 8, lIIlIlIlIllllIl.getY() + 5, Color.WHITE.getRGB());
         var10001 = false;
         if (lIIlIlIlIllllIl.isShowSettings()) {
            if (lIIlIlIlIllllIl.getSettingsWidth() > 0.0F && lIIlIlIlIllllIl.slowlySettingsYPos > lIIlIlIlIllllIl.getY() + 6) {
               RenderUtils.drawBorderedRect((float)(lIIlIlIlIllllIl.getX() + lIIlIlIlIllllIl.getWidth() + 4), (float)(lIIlIlIlIllllIl.getY() + 6), (float)(lIIlIlIlIllllIl.getX() + lIIlIlIlIllllIl.getWidth()) + lIIlIlIlIllllIl.getSettingsWidth(), (float)(lIIlIlIlIllllIl.slowlySettingsYPos + 2), 3.0F, (new Color(40, 40, 40)).getRGB(), (new Color(40, 40, 40)).getRGB());
            }

            lIIlIlIlIllllIl.slowlySettingsYPos = lIIlIlIlIllllIl.getY() + 6;
            Iterator lIIlIlIlIllIllI = lIIlIlIlIllIlll.iterator();

            while(true) {
               while(lIIlIlIlIllIllI.hasNext()) {
                  boolean lIIlIlIlIllIlIl = (Value)lIIlIlIlIllIllI.next();
                  String lIIlIlIlIllIlII;
                  float lIIlIlIllIIIIlI;
                  if (lIIlIlIlIllIlIl instanceof BoolValue) {
                     lIIlIlIlIllIlII = lIIlIlIlIllIlIl.getName();
                     lIIlIlIllIIIIlI = (float)Fonts.font35.getStringWidth(lIIlIlIlIllIlII);
                     if (lIIlIlIlIllllIl.getSettingsWidth() < lIIlIlIllIIIIlI + 8.0F) {
                        lIIlIlIlIllllIl.setSettingsWidth(lIIlIlIllIIIIlI + 8.0F);
                     }

                     if (lIIlIlIlIlllIlI >= lIIlIlIlIllllIl.getX() + lIIlIlIlIllllIl.getWidth() + 4 && (float)lIIlIlIlIlllIlI <= (float)(lIIlIlIlIllllIl.getX() + lIIlIlIlIllllIl.getWidth()) + lIIlIlIlIllllIl.getSettingsWidth() && lIIlIlIlIlllIIl >= lIIlIlIlIllllIl.slowlySettingsYPos && lIIlIlIlIlllIIl <= lIIlIlIlIllllIl.slowlySettingsYPos + 12 && Mouse.isButtonDown(0) && lIIlIlIlIllllIl.isntPressed()) {
                        byte lIIlIlIlIllIIlI = (BoolValue)lIIlIlIlIllIlIl;
                        lIIlIlIlIllIIlI.set(!(Boolean)lIIlIlIlIllIIlI.get());
                        mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
                     }

                     Fonts.font35.drawString(lIIlIlIlIllIlII, lIIlIlIlIllllIl.getX() + lIIlIlIlIllllIl.getWidth() + 6, lIIlIlIlIllllIl.slowlySettingsYPos + 2, (Boolean)((BoolValue)lIIlIlIlIllIlIl).get() ? Color.WHITE.getRGB() : Integer.MAX_VALUE);
                     var10001 = false;
                     lIIlIlIlIllllIl.slowlySettingsYPos += 11;
                  } else {
                     int lIIlIlIlIlIllll;
                     String lIIlIlIllIlIIIl;
                     float lIIlIlIllIlIIII;
                     if (lIIlIlIlIllIlIl instanceof ListValue) {
                        Exception lIIlIlIlIllIlII = (ListValue)lIIlIlIlIllIlIl;
                        lIIlIlIllIlIIIl = lIIlIlIlIllIlIl.getName();
                        lIIlIlIllIlIIII = (float)Fonts.font35.getStringWidth(lIIlIlIllIlIIIl);
                        if (lIIlIlIlIllllIl.getSettingsWidth() < lIIlIlIllIlIIII + 16.0F) {
                           lIIlIlIlIllllIl.setSettingsWidth(lIIlIlIllIlIIII + 16.0F);
                        }

                        Fonts.font35.drawString(lIIlIlIllIlIIIl, lIIlIlIlIllllIl.getX() + lIIlIlIlIllllIl.getWidth() + 6, lIIlIlIlIllllIl.slowlySettingsYPos + 2, 16777215);
                        var10001 = false;
                        Fonts.font35.drawString(lIIlIlIlIllIlII.openList ? "-" : "+", (int)((float)(lIIlIlIlIllllIl.getX() + lIIlIlIlIllllIl.getWidth()) + lIIlIlIlIllllIl.getSettingsWidth() - (float)(lIIlIlIlIllIlII.openList ? 5 : 6)), lIIlIlIlIllllIl.slowlySettingsYPos + 2, 16777215);
                        var10001 = false;
                        if (lIIlIlIlIlllIlI >= lIIlIlIlIllllIl.getX() + lIIlIlIlIllllIl.getWidth() + 4 && (float)lIIlIlIlIlllIlI <= (float)(lIIlIlIlIllllIl.getX() + lIIlIlIlIllllIl.getWidth()) + lIIlIlIlIllllIl.getSettingsWidth() && lIIlIlIlIlllIIl >= lIIlIlIlIllllIl.slowlySettingsYPos && lIIlIlIlIlllIIl <= lIIlIlIlIllllIl.slowlySettingsYPos + Fonts.font35.FONT_HEIGHT && Mouse.isButtonDown(0) && lIIlIlIlIllllIl.isntPressed()) {
                           lIIlIlIlIllIlII.openList = !lIIlIlIlIllIlII.openList;
                           mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
                        }

                        lIIlIlIlIllllIl.slowlySettingsYPos += Fonts.font35.FONT_HEIGHT + 1;
                        int lIIlIlIlIllIIIl = lIIlIlIlIllIlII.getValues();
                        Exception lIIlIlIlIllIIII = lIIlIlIlIllIIIl.length;

                        for(lIIlIlIlIlIllll = 0; lIIlIlIlIlIllll < lIIlIlIlIllIIII; ++lIIlIlIlIlIllll) {
                           String lIIlIlIllIllIlI = lIIlIlIlIllIIIl[lIIlIlIlIlIllll];
                           float lIIlIlIllIllIll = (float)Fonts.font35.getStringWidth(String.valueOf((new StringBuilder()).append("> ").append(lIIlIlIllIllIlI)));
                           if (lIIlIlIlIllllIl.getSettingsWidth() < lIIlIlIllIllIll + 12.0F) {
                              lIIlIlIlIllllIl.setSettingsWidth(lIIlIlIllIllIll + 12.0F);
                           }

                           if (lIIlIlIlIllIlII.openList) {
                              if (lIIlIlIlIlllIlI >= lIIlIlIlIllllIl.getX() + lIIlIlIlIllllIl.getWidth() + 4 && (float)lIIlIlIlIlllIlI <= (float)(lIIlIlIlIllllIl.getX() + lIIlIlIlIllllIl.getWidth()) + lIIlIlIlIllllIl.getSettingsWidth() && lIIlIlIlIlllIIl >= lIIlIlIlIllllIl.slowlySettingsYPos + 2 && lIIlIlIlIlllIIl <= lIIlIlIlIllllIl.slowlySettingsYPos + 14 && Mouse.isButtonDown(0) && lIIlIlIlIllllIl.isntPressed()) {
                                 lIIlIlIlIllIlII.set(lIIlIlIllIllIlI);
                                 mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
                              }

                              GlStateManager.resetColor();
                              Fonts.font35.drawString(String.valueOf((new StringBuilder()).append("> ").append(lIIlIlIllIllIlI)), lIIlIlIlIllllIl.getX() + lIIlIlIlIllllIl.getWidth() + 6, lIIlIlIlIllllIl.slowlySettingsYPos + 2, lIIlIlIlIllIlII.get() != null && ((String)lIIlIlIlIllIlII.get()).equalsIgnoreCase(lIIlIlIllIllIlI) ? Color.WHITE.getRGB() : Integer.MAX_VALUE);
                              var10001 = false;
                              lIIlIlIlIllllIl.slowlySettingsYPos += Fonts.font35.FONT_HEIGHT + 1;
                           }
                        }

                        if (!lIIlIlIlIllIlII.openList) {
                           ++lIIlIlIlIllllIl.slowlySettingsYPos;
                        }
                     } else {
                        float lIIlIlIlIllIIIl;
                        if (lIIlIlIlIllIlIl instanceof FloatValue) {
                           Exception lIIlIlIlIllIlII = (FloatValue)lIIlIlIlIllIlIl;
                           lIIlIlIllIlIIIl = String.valueOf((new StringBuilder()).append(lIIlIlIlIllIlIl.getName()).append("§f: ").append(lIIlIlIllIIIIII.round((Float)lIIlIlIlIllIlII.get())));
                           lIIlIlIllIlIIII = (float)Fonts.font35.getStringWidth(lIIlIlIllIlIIIl);
                           if (lIIlIlIlIllllIl.getSettingsWidth() < lIIlIlIllIlIIII + 8.0F) {
                              lIIlIlIlIllllIl.setSettingsWidth(lIIlIlIllIlIIII + 8.0F);
                           }

                           lIIlIlIlIllIIIl = drawSlider((Float)lIIlIlIlIllIlII.get(), lIIlIlIlIllIlII.getMinimum(), lIIlIlIlIllIlII.getMaximum(), lIIlIlIlIllllIl.getX() + lIIlIlIlIllllIl.getWidth() + 8, lIIlIlIlIllllIl.slowlySettingsYPos + 14, (int)lIIlIlIlIllllIl.getSettingsWidth() - 12, lIIlIlIlIlllIlI, lIIlIlIlIlllIIl, new Color(20, 20, 20));
                           if (lIIlIlIlIllIIIl != (Float)lIIlIlIlIllIlII.get()) {
                              lIIlIlIlIllIlII.set(lIIlIlIlIllIIIl);
                           }

                           Fonts.font35.drawString(lIIlIlIllIlIIIl, lIIlIlIlIllllIl.getX() + lIIlIlIlIllllIl.getWidth() + 6, lIIlIlIlIllllIl.slowlySettingsYPos + 3, 16777215);
                           var10001 = false;
                           lIIlIlIlIllllIl.slowlySettingsYPos += 19;
                        } else if (lIIlIlIlIllIlIl instanceof IntegerValue) {
                           Exception lIIlIlIlIllIlII = (IntegerValue)lIIlIlIlIllIlIl;
                           lIIlIlIllIlIIIl = String.valueOf((new StringBuilder()).append(lIIlIlIlIllIlIl.getName()).append("§f: ").append(lIIlIlIlIllIlIl instanceof BlockValue ? String.valueOf((new StringBuilder()).append(BlockUtils.getBlockName((Integer)lIIlIlIlIllIlII.get())).append(" (").append(lIIlIlIlIllIlII.get()).append(")")) : (Serializable)lIIlIlIlIllIlII.get()));
                           lIIlIlIllIlIIII = (float)Fonts.font35.getStringWidth(lIIlIlIllIlIIIl);
                           if (lIIlIlIlIllllIl.getSettingsWidth() < lIIlIlIllIlIIII + 8.0F) {
                              lIIlIlIlIllllIl.setSettingsWidth(lIIlIlIllIlIIII + 8.0F);
                           }

                           lIIlIlIlIllIIIl = drawSlider((float)(Integer)lIIlIlIlIllIlII.get(), (float)lIIlIlIlIllIlII.getMinimum(), (float)lIIlIlIlIllIlII.getMaximum(), lIIlIlIlIllllIl.getX() + lIIlIlIlIllllIl.getWidth() + 8, lIIlIlIlIllllIl.slowlySettingsYPos + 14, (int)lIIlIlIlIllllIl.getSettingsWidth() - 12, lIIlIlIlIlllIlI, lIIlIlIlIlllIIl, new Color(20, 20, 20));
                           if (lIIlIlIlIllIIIl != (float)(Integer)lIIlIlIlIllIlII.get()) {
                              lIIlIlIlIllIlII.set((int)lIIlIlIlIllIIIl);
                           }

                           Fonts.font35.drawString(lIIlIlIllIlIIIl, lIIlIlIlIllllIl.getX() + lIIlIlIlIllllIl.getWidth() + 6, lIIlIlIlIllllIl.slowlySettingsYPos + 3, 16777215);
                           var10001 = false;
                           lIIlIlIlIllllIl.slowlySettingsYPos += 19;
                        } else if (lIIlIlIlIllIlIl instanceof FontValue) {
                           Exception lIIlIlIlIllIlII = (FontValue)lIIlIlIlIllIlIl;
                           FontRenderer lIIlIlIllIIIllI = (FontRenderer)lIIlIlIlIllIlII.get();
                           byte lIIlIlIlIllIIlI = "Font: Unknown";
                           if (lIIlIlIllIIIllI instanceof GameFontRenderer) {
                              int lIIlIlIlIllIIIl = (GameFontRenderer)lIIlIlIllIIIllI;
                              lIIlIlIlIllIIlI = String.valueOf((new StringBuilder()).append("Font: ").append(lIIlIlIlIllIIIl.getDefaultFont().getFont().getName()).append(" - ").append(lIIlIlIlIllIIIl.getDefaultFont().getFont().getSize()));
                           } else if (lIIlIlIllIIIllI == Fonts.minecraftFont) {
                              lIIlIlIlIllIIlI = "Font: Minecraft";
                           } else {
                              int lIIlIlIlIllIIIl = Fonts.getFontDetails(lIIlIlIllIIIllI);
                              if (lIIlIlIlIllIIIl != null) {
                                 lIIlIlIlIllIIlI = String.valueOf((new StringBuilder()).append(lIIlIlIlIllIIIl[0]).append((Integer)lIIlIlIlIllIIIl[1] != -1 ? String.valueOf((new StringBuilder()).append(" - ").append(lIIlIlIlIllIIIl[1])) : ""));
                              }
                           }

                           Fonts.font35.drawString(lIIlIlIlIllIIlI, lIIlIlIlIllllIl.getX() + lIIlIlIlIllllIl.getWidth() + 6, lIIlIlIlIllllIl.slowlySettingsYPos + 2, Color.WHITE.getRGB());
                           var10001 = false;
                           int lIIlIlIlIllIIIl = Fonts.font35.getStringWidth(lIIlIlIlIllIIlI);
                           if (lIIlIlIlIllllIl.getSettingsWidth() < (float)(lIIlIlIlIllIIIl + 8)) {
                              lIIlIlIlIllllIl.setSettingsWidth((float)(lIIlIlIlIllIIIl + 8));
                           }

                           if ((Mouse.isButtonDown(0) && !lIIlIlIllIIIIII.mouseDown || Mouse.isButtonDown(1) && !lIIlIlIllIIIIII.rightMouseDown) && lIIlIlIlIlllIlI >= lIIlIlIlIllllIl.getX() + lIIlIlIlIllllIl.getWidth() + 4 && (float)lIIlIlIlIlllIlI <= (float)(lIIlIlIlIllllIl.getX() + lIIlIlIlIllllIl.getWidth()) + lIIlIlIlIllllIl.getSettingsWidth() && lIIlIlIlIlllIIl >= lIIlIlIlIllllIl.slowlySettingsYPos && lIIlIlIlIlllIIl <= lIIlIlIlIllllIl.slowlySettingsYPos + 12) {
                              List<FontRenderer> lIIlIlIllIIlIII = Fonts.getFonts();
                              FontRenderer lIIlIlIlIlIlllI;
                              if (Mouse.isButtonDown(0)) {
                                 for(lIIlIlIlIlIllll = 0; lIIlIlIlIlIllll < lIIlIlIllIIlIII.size(); ++lIIlIlIlIlIllll) {
                                    lIIlIlIlIlIlllI = (FontRenderer)lIIlIlIllIIlIII.get(lIIlIlIlIlIllll);
                                    if (lIIlIlIlIlIlllI == lIIlIlIllIIIllI) {
                                       ++lIIlIlIlIlIllll;
                                       if (lIIlIlIlIlIllll >= lIIlIlIllIIlIII.size()) {
                                          lIIlIlIlIlIllll = 0;
                                       }

                                       lIIlIlIlIllIlII.set(lIIlIlIllIIlIII.get(lIIlIlIlIlIllll));
                                       break;
                                    }
                                 }
                              } else {
                                 for(lIIlIlIlIlIllll = lIIlIlIllIIlIII.size() - 1; lIIlIlIlIlIllll >= 0; --lIIlIlIlIlIllll) {
                                    lIIlIlIlIlIlllI = (FontRenderer)lIIlIlIllIIlIII.get(lIIlIlIlIlIllll);
                                    if (lIIlIlIlIlIlllI == lIIlIlIllIIIllI) {
                                       --lIIlIlIlIlIllll;
                                       if (lIIlIlIlIlIllll >= lIIlIlIllIIlIII.size()) {
                                          lIIlIlIlIlIllll = 0;
                                       }

                                       if (lIIlIlIlIlIllll < 0) {
                                          lIIlIlIlIlIllll = lIIlIlIllIIlIII.size() - 1;
                                       }

                                       lIIlIlIlIllIlII.set(lIIlIlIllIIlIII.get(lIIlIlIlIlIllll));
                                       break;
                                    }
                                 }
                              }
                           }

                           lIIlIlIlIllllIl.slowlySettingsYPos += 11;
                        } else {
                           lIIlIlIlIllIlII = String.valueOf((new StringBuilder()).append(lIIlIlIlIllIlIl.getName()).append("§f: ").append(lIIlIlIlIllIlIl.get()));
                           lIIlIlIllIIIIlI = (float)Fonts.font35.getStringWidth(lIIlIlIlIllIlII);
                           if (lIIlIlIlIllllIl.getSettingsWidth() < lIIlIlIllIIIIlI + 8.0F) {
                              lIIlIlIlIllllIl.setSettingsWidth(lIIlIlIllIIIIlI + 8.0F);
                           }

                           GlStateManager.resetColor();
                           Fonts.font35.drawString(lIIlIlIlIllIlII, lIIlIlIlIllllIl.getX() + lIIlIlIlIllllIl.getWidth() + 6, lIIlIlIlIllllIl.slowlySettingsYPos + 4, 16777215);
                           var10001 = false;
                           lIIlIlIlIllllIl.slowlySettingsYPos += 12;
                        }
                     }
                  }
               }

               lIIlIlIlIllllIl.updatePressed();
               lIIlIlIllIIIIII.mouseDown = Mouse.isButtonDown(0);
               lIIlIlIllIIIIII.rightMouseDown = Mouse.isButtonDown(1);
               break;
            }
         }
      }

   }

   public void drawButtonElement(int lIIlIlIllllIIlI, int lIIlIlIllllIIIl, ButtonElement lIIlIlIllllIIII) {
      Gui.drawRect(lIIlIlIllllIIII.getX() - 1, lIIlIlIllllIIII.getY() - 1, lIIlIlIllllIIII.getX() + lIIlIlIllllIIII.getWidth() + 1, lIIlIlIllllIIII.getY() + lIIlIlIllllIIII.getHeight() + 1, lIIlIlIlllIllll.hoverColor(lIIlIlIllllIIII.getColor() != Integer.MAX_VALUE ? new Color(20, 20, 20) : new Color(40, 40, 40), lIIlIlIllllIIII.hoverTime).getRGB());
      GlStateManager.resetColor();
      Fonts.font35.drawString(lIIlIlIllllIIII.getDisplayName(), lIIlIlIllllIIII.getX() + 5, lIIlIlIllllIIII.getY() + 5, Color.WHITE.getRGB());
      boolean var10001 = false;
   }
}
