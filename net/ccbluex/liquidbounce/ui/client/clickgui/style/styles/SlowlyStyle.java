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
public class SlowlyStyle extends Style {
   // $FF: synthetic field
   private boolean mouseDown;
   // $FF: synthetic field
   private boolean rightMouseDown;

   public void drawButtonElement(int lIllIllIllIlllI, int lIllIllIllIllIl, ButtonElement lIllIllIllIlIlI) {
      Gui.drawRect(lIllIllIllIlIlI.getX() - 1, lIllIllIllIlIlI.getY() - 1, lIllIllIllIlIlI.getX() + lIllIllIllIlIlI.getWidth() + 1, lIllIllIllIlIlI.getY() + lIllIllIllIlIlI.getHeight() + 1, lIllIllIllIllll.hoverColor(lIllIllIllIlIlI.getColor() != Integer.MAX_VALUE ? new Color(7, 152, 252) : new Color(54, 71, 96), lIllIllIllIlIlI.hoverTime).getRGB());
      GlStateManager.resetColor();
      Fonts.font35.drawString(lIllIllIllIlIlI.getDisplayName(), lIllIllIllIlIlI.getX() + 5, lIllIllIllIlIlI.getY() + 5, Color.WHITE.getRGB());
      boolean var10001 = false;
   }

   public void drawPanel(int lIllIlllIIIIlII, int lIllIlllIIIIIll, Panel lIllIlllIIIIIII) {
      RenderUtils.drawBorderedRect((float)lIllIlllIIIIIII.getX(), (float)lIllIlllIIIIIII.getY() - 3.0F, (float)lIllIlllIIIIIII.getX() + (float)lIllIlllIIIIIII.getWidth(), (float)lIllIlllIIIIIII.getY() + 17.0F, 3.0F, (new Color(42, 57, 79)).getRGB(), (new Color(42, 57, 79)).getRGB());
      if (lIllIlllIIIIIII.getFade() > 0) {
         RenderUtils.drawBorderedRect((float)lIllIlllIIIIIII.getX(), (float)lIllIlllIIIIIII.getY() + 17.0F, (float)lIllIlllIIIIIII.getX() + (float)lIllIlllIIIIIII.getWidth(), (float)(lIllIlllIIIIIII.getY() + 19 + lIllIlllIIIIIII.getFade()), 3.0F, (new Color(54, 71, 96)).getRGB(), (new Color(54, 71, 96)).getRGB());
         RenderUtils.drawBorderedRect((float)lIllIlllIIIIIII.getX(), (float)(lIllIlllIIIIIII.getY() + 17 + lIllIlllIIIIIII.getFade()), (float)lIllIlllIIIIIII.getX() + (float)lIllIlllIIIIIII.getWidth(), (float)(lIllIlllIIIIIII.getY() + 19 + lIllIlllIIIIIII.getFade() + 5), 3.0F, (new Color(42, 57, 79)).getRGB(), (new Color(42, 57, 79)).getRGB());
      }

      GlStateManager.resetColor();
      float lIllIlllIIIIIIl = (float)Fonts.font35.getStringWidth(String.valueOf((new StringBuilder()).append("§f").append(StringUtils.stripControlCodes(lIllIlllIIIIIII.getName()))));
      Fonts.font35.drawString(lIllIlllIIIIIII.getName(), (int)((float)lIllIlllIIIIIII.getX() - (lIllIlllIIIIIIl - 100.0F) / 2.0F), lIllIlllIIIIIII.getY() + 7 - 3, Color.WHITE.getRGB());
      boolean var10001 = false;
   }

   private Color hoverColor(Color lIllIllIIIllIll, int lIllIllIIIlIlIl) {
      int lIllIllIIIllIIl = lIllIllIIIllIll.getRed() - lIllIllIIIlIlIl * 2;
      int lIllIllIIIllIII = lIllIllIIIllIll.getGreen() - lIllIllIIIlIlIl * 2;
      long lIllIllIIIlIIlI = lIllIllIIIllIll.getBlue() - lIllIllIIIlIlIl * 2;
      return new Color(Math.max(lIllIllIIIllIIl, 0), Math.max(lIllIllIIIllIII, 0), Math.max(lIllIllIIIlIIlI, 0), lIllIllIIIllIll.getAlpha());
   }

   public static float drawSlider(float lIllIlIllllIlll, float lIllIlIllllIllI, float lIllIllIIIIIIII, int lIllIlIllllIlII, int lIllIlIlllllllI, int lIllIlIllllIIlI, int lIllIlIllllIIIl, int lIllIlIlllllIll, Color lIllIlIlllIllll) {
      short lIllIlIlllIlllI = Math.max(lIllIlIllllIllI, Math.min(lIllIlIllllIlll, lIllIllIIIIIIII));
      String lIllIlIlllIllIl = (float)lIllIlIllllIlII + (float)lIllIlIllllIIlI * (lIllIlIlllIlllI - lIllIlIllllIllI) / (lIllIllIIIIIIII - lIllIlIllllIllI);
      RenderUtils.drawRect((float)lIllIlIllllIlII, (float)lIllIlIlllllllI, (float)(lIllIlIllllIlII + lIllIlIllllIIlI), (float)(lIllIlIlllllllI + 2), Integer.MAX_VALUE);
      RenderUtils.drawRect((float)lIllIlIllllIlII, (float)lIllIlIlllllllI, lIllIlIlllIllIl, (float)(lIllIlIlllllllI + 2), lIllIlIlllIllll);
      RenderUtils.drawFilledCircle((int)lIllIlIlllIllIl, lIllIlIlllllllI + 1, 3.0F, lIllIlIlllIllll);
      if (lIllIlIllllIIIl >= lIllIlIllllIlII && lIllIlIllllIIIl <= lIllIlIllllIlII + lIllIlIllllIIlI && lIllIlIlllllIll >= lIllIlIlllllllI && lIllIlIlllllIll <= lIllIlIlllllllI + 3 && Mouse.isButtonDown(0)) {
         double lIllIllIIIIIlII = MathHelper.clamp_double(((double)lIllIlIllllIIIl - (double)lIllIlIllllIlII) / ((double)lIllIlIllllIIlI - 3.0D), 0.0D, 1.0D);
         long lIllIlIlllIlIll = new BigDecimal(Double.toString((double)lIllIlIllllIllI + (double)(lIllIllIIIIIIII - lIllIlIllllIllI) * lIllIllIIIIIlII));
         lIllIlIlllIlIll = lIllIlIlllIlIll.setScale(2, 4);
         return lIllIlIlllIlIll.floatValue();
      } else {
         return lIllIlIllllIlll;
      }
   }

   private BigDecimal round(float lIllIllIIlIIIll) {
      double lIllIllIIlIIIlI = new BigDecimal(Float.toString(lIllIllIIlIIIll));
      lIllIllIIlIIIlI = lIllIllIIlIIIlI.setScale(2, 4);
      return lIllIllIIlIIIlI;
   }

   public void drawModuleElement(int lIllIllIIlllIll, int lIllIllIIllIlIl, ModuleElement lIllIllIIllIlII) {
      Gui.drawRect(lIllIllIIllIlII.getX() - 1, lIllIllIIllIlII.getY() - 1, lIllIllIIllIlII.getX() + lIllIllIIllIlII.getWidth() + 1, lIllIllIIllIlII.getY() + lIllIllIIllIlII.getHeight() + 1, lIllIllIIllllII.hoverColor(new Color(54, 71, 96), lIllIllIIllIlII.hoverTime).getRGB());
      Gui.drawRect(lIllIllIIllIlII.getX() - 1, lIllIllIIllIlII.getY() - 1, lIllIllIIllIlII.getX() + lIllIllIIllIlII.getWidth() + 1, lIllIllIIllIlII.getY() + lIllIllIIllIlII.getHeight() + 1, lIllIllIIllllII.hoverColor(new Color(7, 152, 252, lIllIllIIllIlII.slowlyFade), lIllIllIIllIlII.hoverTime).getRGB());
      GlStateManager.resetColor();
      Fonts.font35.drawString(lIllIllIIllIlII.getDisplayName(), lIllIllIIllIlII.getX() + 5, lIllIllIIllIlII.getY() + 5, Color.WHITE.getRGB());
      boolean var10001 = false;
      List<Value<?>> lIllIllIIllIIll = lIllIllIIllIlII.getModule().getValues();
      if (!lIllIllIIllIIll.isEmpty()) {
         Fonts.font35.drawString(">", lIllIllIIllIlII.getX() + lIllIllIIllIlII.getWidth() - 8, lIllIllIIllIlII.getY() + 5, Color.WHITE.getRGB());
         var10001 = false;
         if (lIllIllIIllIlII.isShowSettings()) {
            if (lIllIllIIllIlII.getSettingsWidth() > 0.0F && lIllIllIIllIlII.slowlySettingsYPos > lIllIllIIllIlII.getY() + 6) {
               RenderUtils.drawBorderedRect((float)(lIllIllIIllIlII.getX() + lIllIllIIllIlII.getWidth() + 4), (float)(lIllIllIIllIlII.getY() + 6), (float)(lIllIllIIllIlII.getX() + lIllIllIIllIlII.getWidth()) + lIllIllIIllIlII.getSettingsWidth(), (float)(lIllIllIIllIlII.slowlySettingsYPos + 2), 3.0F, (new Color(54, 71, 96)).getRGB(), (new Color(54, 71, 96)).getRGB());
            }

            lIllIllIIllIlII.slowlySettingsYPos = lIllIllIIllIlII.getY() + 6;
            Iterator lIllIllIIllIIlI = lIllIllIIllIIll.iterator();

            while(true) {
               while(lIllIllIIllIIlI.hasNext()) {
                  Value lIllIllIIllllIl = (Value)lIllIllIIllIIlI.next();
                  String lIllIllIIllIIII;
                  float lIllIllIIlIllll;
                  if (lIllIllIIllllIl instanceof BoolValue) {
                     lIllIllIIllIIII = lIllIllIIllllIl.getName();
                     lIllIllIIlIllll = (float)Fonts.font35.getStringWidth(lIllIllIIllIIII);
                     if (lIllIllIIllIlII.getSettingsWidth() < lIllIllIIlIllll + 8.0F) {
                        lIllIllIIllIlII.setSettingsWidth(lIllIllIIlIllll + 8.0F);
                     }

                     if (lIllIllIIlllIll >= lIllIllIIllIlII.getX() + lIllIllIIllIlII.getWidth() + 4 && (float)lIllIllIIlllIll <= (float)(lIllIllIIllIlII.getX() + lIllIllIIllIlII.getWidth()) + lIllIllIIllIlII.getSettingsWidth() && lIllIllIIllIlIl >= lIllIllIIllIlII.slowlySettingsYPos && lIllIllIIllIlIl <= lIllIllIIllIlII.slowlySettingsYPos + 12 && Mouse.isButtonDown(0) && lIllIllIIllIlII.isntPressed()) {
                        BoolValue lIllIllIlIllIlI = (BoolValue)lIllIllIIllllIl;
                        lIllIllIlIllIlI.set(!(Boolean)lIllIllIlIllIlI.get());
                        mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
                     }

                     Fonts.font35.drawString(lIllIllIIllIIII, lIllIllIIllIlII.getX() + lIllIllIIllIlII.getWidth() + 6, lIllIllIIllIlII.slowlySettingsYPos + 2, (Boolean)((BoolValue)lIllIllIIllllIl).get() ? Color.WHITE.getRGB() : Integer.MAX_VALUE);
                     var10001 = false;
                     lIllIllIIllIlII.slowlySettingsYPos += 11;
                  } else {
                     int lIllIllIlIIIlIl;
                     String lIllIllIlIIllIl;
                     float lIllIllIlIIllII;
                     if (lIllIllIIllllIl instanceof ListValue) {
                        double lIllIllIIllIIII = (ListValue)lIllIllIIllllIl;
                        lIllIllIlIIllIl = lIllIllIIllllIl.getName();
                        lIllIllIlIIllII = (float)Fonts.font35.getStringWidth(lIllIllIlIIllIl);
                        if (lIllIllIIllIlII.getSettingsWidth() < lIllIllIlIIllII + 16.0F) {
                           lIllIllIIllIlII.setSettingsWidth(lIllIllIlIIllII + 16.0F);
                        }

                        Fonts.font35.drawString(lIllIllIlIIllIl, lIllIllIIllIlII.getX() + lIllIllIIllIlII.getWidth() + 6, lIllIllIIllIlII.slowlySettingsYPos + 2, 16777215);
                        var10001 = false;
                        Fonts.font35.drawString(lIllIllIIllIIII.openList ? "-" : "+", (int)((float)(lIllIllIIllIlII.getX() + lIllIllIIllIlII.getWidth()) + lIllIllIIllIlII.getSettingsWidth() - (float)(lIllIllIIllIIII.openList ? 5 : 6)), lIllIllIIllIlII.slowlySettingsYPos + 2, 16777215);
                        var10001 = false;
                        if (lIllIllIIlllIll >= lIllIllIIllIlII.getX() + lIllIllIIllIlII.getWidth() + 4 && (float)lIllIllIIlllIll <= (float)(lIllIllIIllIlII.getX() + lIllIllIIllIlII.getWidth()) + lIllIllIIllIlII.getSettingsWidth() && lIllIllIIllIlIl >= lIllIllIIllIlII.slowlySettingsYPos && lIllIllIIllIlIl <= lIllIllIIllIlII.slowlySettingsYPos + Fonts.font35.FONT_HEIGHT && Mouse.isButtonDown(0) && lIllIllIIllIlII.isntPressed()) {
                           lIllIllIIllIIII.openList = !lIllIllIIllIIII.openList;
                           mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
                        }

                        lIllIllIIllIlII.slowlySettingsYPos += Fonts.font35.FONT_HEIGHT + 1;
                        Exception lIllIllIIlIllIl = lIllIllIIllIIII.getValues();
                        long lIllIllIIlIllII = lIllIllIIlIllIl.length;

                        for(lIllIllIlIIIlIl = 0; lIllIllIlIIIlIl < lIllIllIIlIllII; ++lIllIllIlIIIlIl) {
                           String lIllIllIlIlIllI = lIllIllIIlIllIl[lIllIllIlIIIlIl];
                           Exception lIllIllIIlIlIIl = (float)Fonts.font35.getStringWidth(String.valueOf((new StringBuilder()).append("> ").append(lIllIllIlIlIllI)));
                           if (lIllIllIIllIlII.getSettingsWidth() < lIllIllIIlIlIIl + 12.0F) {
                              lIllIllIIllIlII.setSettingsWidth(lIllIllIIlIlIIl + 12.0F);
                           }

                           if (lIllIllIIllIIII.openList) {
                              if (lIllIllIIlllIll >= lIllIllIIllIlII.getX() + lIllIllIIllIlII.getWidth() + 4 && (float)lIllIllIIlllIll <= (float)(lIllIllIIllIlII.getX() + lIllIllIIllIlII.getWidth()) + lIllIllIIllIlII.getSettingsWidth() && lIllIllIIllIlIl >= lIllIllIIllIlII.slowlySettingsYPos + 2 && lIllIllIIllIlIl <= lIllIllIIllIlII.slowlySettingsYPos + 14 && Mouse.isButtonDown(0) && lIllIllIIllIlII.isntPressed()) {
                                 lIllIllIIllIIII.set(lIllIllIlIlIllI);
                                 mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
                              }

                              GlStateManager.resetColor();
                              Fonts.font35.drawString(String.valueOf((new StringBuilder()).append("> ").append(lIllIllIlIlIllI)), lIllIllIIllIlII.getX() + lIllIllIIllIlII.getWidth() + 6, lIllIllIIllIlII.slowlySettingsYPos + 2, lIllIllIIllIIII.get() != null && ((String)lIllIllIIllIIII.get()).equalsIgnoreCase(lIllIllIlIlIllI) ? Color.WHITE.getRGB() : Integer.MAX_VALUE);
                              var10001 = false;
                              lIllIllIIllIlII.slowlySettingsYPos += Fonts.font35.FONT_HEIGHT + 1;
                           }
                        }

                        if (!lIllIllIIllIIII.openList) {
                           ++lIllIllIIllIlII.slowlySettingsYPos;
                        }
                     } else {
                        float lIllIllIIlIllIl;
                        if (lIllIllIIllllIl instanceof FloatValue) {
                           double lIllIllIIllIIII = (FloatValue)lIllIllIIllllIl;
                           lIllIllIlIIllIl = String.valueOf((new StringBuilder()).append(lIllIllIIllllIl.getName()).append("§f: ").append(lIllIllIIllllII.round((Float)lIllIllIIllIIII.get())));
                           lIllIllIlIIllII = (float)Fonts.font35.getStringWidth(lIllIllIlIIllIl);
                           if (lIllIllIIllIlII.getSettingsWidth() < lIllIllIlIIllII + 8.0F) {
                              lIllIllIIllIlII.setSettingsWidth(lIllIllIlIIllII + 8.0F);
                           }

                           lIllIllIIlIllIl = drawSlider((Float)lIllIllIIllIIII.get(), lIllIllIIllIIII.getMinimum(), lIllIllIIllIIII.getMaximum(), lIllIllIIllIlII.getX() + lIllIllIIllIlII.getWidth() + 8, lIllIllIIllIlII.slowlySettingsYPos + 14, (int)lIllIllIIllIlII.getSettingsWidth() - 12, lIllIllIIlllIll, lIllIllIIllIlIl, new Color(7, 152, 252));
                           if (lIllIllIIlIllIl != (Float)lIllIllIIllIIII.get()) {
                              lIllIllIIllIIII.set(lIllIllIIlIllIl);
                           }

                           Fonts.font35.drawString(lIllIllIlIIllIl, lIllIllIIllIlII.getX() + lIllIllIIllIlII.getWidth() + 6, lIllIllIIllIlII.slowlySettingsYPos + 3, 16777215);
                           var10001 = false;
                           lIllIllIIllIlII.slowlySettingsYPos += 19;
                        } else if (lIllIllIIllllIl instanceof IntegerValue) {
                           double lIllIllIIllIIII = (IntegerValue)lIllIllIIllllIl;
                           lIllIllIlIIllIl = String.valueOf((new StringBuilder()).append(lIllIllIIllllIl.getName()).append("§f: ").append(lIllIllIIllllIl instanceof BlockValue ? String.valueOf((new StringBuilder()).append(BlockUtils.getBlockName((Integer)lIllIllIIllIIII.get())).append(" (").append(lIllIllIIllIIII.get()).append(")")) : (Serializable)lIllIllIIllIIII.get()));
                           lIllIllIlIIllII = (float)Fonts.font35.getStringWidth(lIllIllIlIIllIl);
                           if (lIllIllIIllIlII.getSettingsWidth() < lIllIllIlIIllII + 8.0F) {
                              lIllIllIIllIlII.setSettingsWidth(lIllIllIlIIllII + 8.0F);
                           }

                           lIllIllIIlIllIl = drawSlider((float)(Integer)lIllIllIIllIIII.get(), (float)lIllIllIIllIIII.getMinimum(), (float)lIllIllIIllIIII.getMaximum(), lIllIllIIllIlII.getX() + lIllIllIIllIlII.getWidth() + 8, lIllIllIIllIlII.slowlySettingsYPos + 14, (int)lIllIllIIllIlII.getSettingsWidth() - 12, lIllIllIIlllIll, lIllIllIIllIlIl, new Color(7, 152, 252));
                           if (lIllIllIIlIllIl != (float)(Integer)lIllIllIIllIIII.get()) {
                              lIllIllIIllIIII.set((int)lIllIllIIlIllIl);
                           }

                           Fonts.font35.drawString(lIllIllIlIIllIl, lIllIllIIllIlII.getX() + lIllIllIIllIlII.getWidth() + 6, lIllIllIIllIlII.slowlySettingsYPos + 3, 16777215);
                           var10001 = false;
                           lIllIllIIllIlII.slowlySettingsYPos += 19;
                        } else if (lIllIllIIllllIl instanceof FontValue) {
                           double lIllIllIIllIIII = (FontValue)lIllIllIIllllIl;
                           float lIllIllIIlIllll = (FontRenderer)lIllIllIIllIIII.get();
                           String lIllIllIlIIIIIl = "Font: Unknown";
                           if (lIllIllIIlIllll instanceof GameFontRenderer) {
                              Exception lIllIllIIlIllIl = (GameFontRenderer)lIllIllIIlIllll;
                              lIllIllIlIIIIIl = String.valueOf((new StringBuilder()).append("Font: ").append(lIllIllIIlIllIl.getDefaultFont().getFont().getName()).append(" - ").append(lIllIllIIlIllIl.getDefaultFont().getFont().getSize()));
                           } else if (lIllIllIIlIllll == Fonts.minecraftFont) {
                              lIllIllIlIIIIIl = "Font: Minecraft";
                           } else {
                              Object[] lIllIllIlIIlIIl = Fonts.getFontDetails(lIllIllIIlIllll);
                              if (lIllIllIlIIlIIl != null) {
                                 lIllIllIlIIIIIl = String.valueOf((new StringBuilder()).append(lIllIllIlIIlIIl[0]).append((Integer)lIllIllIlIIlIIl[1] != -1 ? String.valueOf((new StringBuilder()).append(" - ").append(lIllIllIlIIlIIl[1])) : ""));
                              }
                           }

                           Fonts.font35.drawString(lIllIllIlIIIIIl, lIllIllIIllIlII.getX() + lIllIllIIllIlII.getWidth() + 6, lIllIllIIllIlII.slowlySettingsYPos + 2, Color.WHITE.getRGB());
                           var10001 = false;
                           int lIllIllIlIIIIII = Fonts.font35.getStringWidth(lIllIllIlIIIIIl);
                           if (lIllIllIIllIlII.getSettingsWidth() < (float)(lIllIllIlIIIIII + 8)) {
                              lIllIllIIllIlII.setSettingsWidth((float)(lIllIllIlIIIIII + 8));
                           }

                           if ((Mouse.isButtonDown(0) && !lIllIllIIllllII.mouseDown || Mouse.isButtonDown(1) && !lIllIllIIllllII.rightMouseDown) && lIllIllIIlllIll >= lIllIllIIllIlII.getX() + lIllIllIIllIlII.getWidth() + 4 && (float)lIllIllIIlllIll <= (float)(lIllIllIIllIlII.getX() + lIllIllIIllIlII.getWidth()) + lIllIllIIllIlII.getSettingsWidth() && lIllIllIIllIlIl >= lIllIllIIllIlII.slowlySettingsYPos && lIllIllIIllIlIl <= lIllIllIIllIlII.slowlySettingsYPos + 12) {
                              List<FontRenderer> lIllIllIIlIllII = Fonts.getFonts();
                              FontRenderer lIllIllIlIIlIII;
                              if (Mouse.isButtonDown(0)) {
                                 for(lIllIllIlIIIlIl = 0; lIllIllIlIIIlIl < lIllIllIIlIllII.size(); ++lIllIllIlIIIlIl) {
                                    lIllIllIlIIlIII = (FontRenderer)lIllIllIIlIllII.get(lIllIllIlIIIlIl);
                                    if (lIllIllIlIIlIII == lIllIllIIlIllll) {
                                       ++lIllIllIlIIIlIl;
                                       if (lIllIllIlIIIlIl >= lIllIllIIlIllII.size()) {
                                          lIllIllIlIIIlIl = 0;
                                       }

                                       lIllIllIIllIIII.set(lIllIllIIlIllII.get(lIllIllIlIIIlIl));
                                       break;
                                    }
                                 }
                              } else {
                                 for(lIllIllIlIIIlIl = lIllIllIIlIllII.size() - 1; lIllIllIlIIIlIl >= 0; --lIllIllIlIIIlIl) {
                                    lIllIllIlIIlIII = (FontRenderer)lIllIllIIlIllII.get(lIllIllIlIIIlIl);
                                    if (lIllIllIlIIlIII == lIllIllIIlIllll) {
                                       --lIllIllIlIIIlIl;
                                       if (lIllIllIlIIIlIl >= lIllIllIIlIllII.size()) {
                                          lIllIllIlIIIlIl = 0;
                                       }

                                       if (lIllIllIlIIIlIl < 0) {
                                          lIllIllIlIIIlIl = lIllIllIIlIllII.size() - 1;
                                       }

                                       lIllIllIIllIIII.set(lIllIllIIlIllII.get(lIllIllIlIIIlIl));
                                       break;
                                    }
                                 }
                              }
                           }

                           lIllIllIIllIlII.slowlySettingsYPos += 11;
                        } else {
                           lIllIllIIllIIII = String.valueOf((new StringBuilder()).append(lIllIllIIllllIl.getName()).append("§f: ").append(lIllIllIIllllIl.get()));
                           lIllIllIIlIllll = (float)Fonts.font35.getStringWidth(lIllIllIIllIIII);
                           if (lIllIllIIllIlII.getSettingsWidth() < lIllIllIIlIllll + 8.0F) {
                              lIllIllIIllIlII.setSettingsWidth(lIllIllIIlIllll + 8.0F);
                           }

                           GlStateManager.resetColor();
                           Fonts.font35.drawString(lIllIllIIllIIII, lIllIllIIllIlII.getX() + lIllIllIIllIlII.getWidth() + 6, lIllIllIIllIlII.slowlySettingsYPos + 4, 16777215);
                           var10001 = false;
                           lIllIllIIllIlII.slowlySettingsYPos += 12;
                        }
                     }
                  }
               }

               lIllIllIIllIlII.updatePressed();
               lIllIllIIllllII.mouseDown = Mouse.isButtonDown(0);
               lIllIllIIllllII.rightMouseDown = Mouse.isButtonDown(1);
               break;
            }
         }
      }

   }

   public void drawDescription(int lIllIllIllllIIl, int lIllIllIllllIII, String lIllIllIlllIIll) {
      short lIllIllIlllIIlI = Fonts.font35.getStringWidth(lIllIllIlllIIll);
      RenderUtils.drawBorderedRect((float)(lIllIllIllllIIl + 9), (float)lIllIllIllllIII, (float)(lIllIllIllllIIl + lIllIllIlllIIlI + 14), (float)(lIllIllIllllIII + Fonts.font35.FONT_HEIGHT + 3), 3.0F, (new Color(42, 57, 79)).getRGB(), (new Color(42, 57, 79)).getRGB());
      GlStateManager.resetColor();
      Fonts.font35.drawString(lIllIllIlllIIll, lIllIllIllllIIl + 12, lIllIllIllllIII + Fonts.font35.FONT_HEIGHT / 2, Color.WHITE.getRGB());
      boolean var10001 = false;
   }
}
