//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.ui.client.clickgui.style.styles;

import java.awt.Color;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import net.ccbluex.liquidbounce.LiquidBounce;
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
public class LiquidBounceStyle extends Style {
   // $FF: synthetic field
   private boolean mouseDown;
   // $FF: synthetic field
   private boolean rightMouseDown;

   public void drawPanel(int lllllllllllllllllIllIIlIlIllIlIl, int lllllllllllllllllIllIIlIlIllIlII, Panel lllllllllllllllllIllIIlIlIllIIIl) {
      RenderUtils.drawBorderedRect((float)lllllllllllllllllIllIIlIlIllIIIl.getX() - (float)(lllllllllllllllllIllIIlIlIllIIIl.getScrollbar() ? 4 : 0), (float)lllllllllllllllllIllIIlIlIllIIIl.getY(), (float)lllllllllllllllllIllIIlIlIllIIIl.getX() + (float)lllllllllllllllllIllIIlIlIllIIIl.getWidth(), (float)lllllllllllllllllIllIIlIlIllIIIl.getY() + 19.0F + (float)lllllllllllllllllIllIIlIlIllIIIl.getFade(), 1.0F, (new Color(255, 255, 255, 90)).getRGB(), Integer.MIN_VALUE);
      float lllllllllllllllllIllIIlIlIllIIlI = (float)Fonts.font35.getStringWidth(String.valueOf((new StringBuilder()).append("§f").append(StringUtils.stripControlCodes(lllllllllllllllllIllIIlIlIllIIIl.getName()))));
      Fonts.font35.drawString(String.valueOf((new StringBuilder()).append("§f").append(lllllllllllllllllIllIIlIlIllIIIl.getName())), (int)((float)lllllllllllllllllIllIIlIlIllIIIl.getX() - (lllllllllllllllllIllIIlIlIllIIlI - 100.0F) / 2.0F), lllllllllllllllllIllIIlIlIllIIIl.getY() + 7, -16777216);
      boolean var10001 = false;
      if (lllllllllllllllllIllIIlIlIllIIIl.getScrollbar() && lllllllllllllllllIllIIlIlIllIIIl.getFade() > 0) {
         RenderUtils.drawRect((float)(lllllllllllllllllIllIIlIlIllIIIl.getX() - 2), (float)(lllllllllllllllllIllIIlIlIllIIIl.getY() + 21), (float)lllllllllllllllllIllIIlIlIllIIIl.getX(), (float)(lllllllllllllllllIllIIlIlIllIIIl.getY() + 16 + lllllllllllllllllIllIIlIlIllIIIl.getFade()), Integer.MAX_VALUE);
         RenderUtils.drawRect((float)(lllllllllllllllllIllIIlIlIllIIIl.getX() - 2), (float)(lllllllllllllllllIllIIlIlIllIIIl.getY() + 30) + ((float)lllllllllllllllllIllIIlIlIllIIIl.getFade() - 24.0F) / (float)(lllllllllllllllllIllIIlIlIllIIIl.getElements().size() - (Integer)((ClickGUI)LiquidBounce.moduleManager.getModule(ClickGUI.class)).maxElementsValue.get()) * (float)lllllllllllllllllIllIIlIlIllIIIl.getDragged() - 10.0F, (float)lllllllllllllllllIllIIlIlIllIIIl.getX(), (float)(lllllllllllllllllIllIIlIlIllIIIl.getY() + 40) + ((float)lllllllllllllllllIllIIlIlIllIIIl.getFade() - 24.0F) / (float)(lllllllllllllllllIllIIlIlIllIIIl.getElements().size() - (Integer)((ClickGUI)LiquidBounce.moduleManager.getModule(ClickGUI.class)).maxElementsValue.get()) * (float)lllllllllllllllllIllIIlIlIllIIIl.getDragged(), Integer.MIN_VALUE);
      }

   }

   private BigDecimal round(float lllllllllllllllllIllIIlIIIllllII) {
      short lllllllllllllllllIllIIlIIIlllIIl = new BigDecimal(Float.toString(lllllllllllllllllIllIIlIIIllllII));
      lllllllllllllllllIllIIlIIIlllIIl = lllllllllllllllllIllIIlIIIlllIIl.setScale(2, 4);
      return lllllllllllllllllIllIIlIIIlllIIl;
   }

   public void drawModuleElement(int lllllllllllllllllIllIIlIIlIlIlIl, int lllllllllllllllllIllIIlIIlIlIlII, ModuleElement lllllllllllllllllIllIIlIIlIlIIll) {
      int lllllllllllllllllIllIIlIIlIIllII = ClickGUI.generateColor().getRGB();
      GlStateManager.resetColor();
      Fonts.font35.drawString(lllllllllllllllllIllIIlIIlIlIIll.getDisplayName(), (int)((float)lllllllllllllllllIllIIlIIlIlIIll.getX() - ((float)Fonts.font35.getStringWidth(lllllllllllllllllIllIIlIIlIlIIll.getDisplayName()) - 100.0F) / 2.0F), lllllllllllllllllIllIIlIIlIlIIll.getY() + 6, lllllllllllllllllIllIIlIIlIlIIll.getModule().getState() ? lllllllllllllllllIllIIlIIlIIllII : Integer.MAX_VALUE);
      boolean var10001 = false;
      List<Value<?>> lllllllllllllllllIllIIlIIlIlIIIl = lllllllllllllllllIllIIlIIlIlIIll.getModule().getValues();
      if (!lllllllllllllllllIllIIlIIlIlIIIl.isEmpty()) {
         Fonts.font35.drawString("+", lllllllllllllllllIllIIlIIlIlIIll.getX() + lllllllllllllllllIllIIlIIlIlIIll.getWidth() - 8, lllllllllllllllllIllIIlIIlIlIIll.getY() + lllllllllllllllllIllIIlIIlIlIIll.getHeight() / 2, Color.WHITE.getRGB());
         var10001 = false;
         if (lllllllllllllllllIllIIlIIlIlIIll.isShowSettings()) {
            int lllllllllllllllllIllIIlIIlIlIlll = lllllllllllllllllIllIIlIIlIlIIll.getY() + 4;
            Iterator lllllllllllllllllIllIIlIIlIIlIIl = lllllllllllllllllIllIIlIIlIlIIIl.iterator();

            while(true) {
               while(lllllllllllllllllIllIIlIIlIIlIIl.hasNext()) {
                  long lllllllllllllllllIllIIlIIlIIlIII = (Value)lllllllllllllllllIllIIlIIlIIlIIl.next();
                  String lllllllllllllllllIllIIlIIlIllIlI;
                  float lllllllllllllllllIllIIlIIlIIIllI;
                  if (lllllllllllllllllIllIIlIIlIIlIII instanceof BoolValue) {
                     lllllllllllllllllIllIIlIIlIllIlI = lllllllllllllllllIllIIlIIlIIlIII.getName();
                     lllllllllllllllllIllIIlIIlIIIllI = (float)Fonts.font35.getStringWidth(lllllllllllllllllIllIIlIIlIllIlI);
                     if (lllllllllllllllllIllIIlIIlIlIIll.getSettingsWidth() < lllllllllllllllllIllIIlIIlIIIllI + 8.0F) {
                        lllllllllllllllllIllIIlIIlIlIIll.setSettingsWidth(lllllllllllllllllIllIIlIIlIIIllI + 8.0F);
                     }

                     RenderUtils.drawRect((float)(lllllllllllllllllIllIIlIIlIlIIll.getX() + lllllllllllllllllIllIIlIIlIlIIll.getWidth() + 4), (float)(lllllllllllllllllIllIIlIIlIlIlll + 2), (float)(lllllllllllllllllIllIIlIIlIlIIll.getX() + lllllllllllllllllIllIIlIIlIlIIll.getWidth()) + lllllllllllllllllIllIIlIIlIlIIll.getSettingsWidth(), (float)(lllllllllllllllllIllIIlIIlIlIlll + 14), Integer.MIN_VALUE);
                     if (lllllllllllllllllIllIIlIIlIlIlIl >= lllllllllllllllllIllIIlIIlIlIIll.getX() + lllllllllllllllllIllIIlIIlIlIIll.getWidth() + 4 && (float)lllllllllllllllllIllIIlIIlIlIlIl <= (float)(lllllllllllllllllIllIIlIIlIlIIll.getX() + lllllllllllllllllIllIIlIIlIlIIll.getWidth()) + lllllllllllllllllIllIIlIIlIlIIll.getSettingsWidth() && lllllllllllllllllIllIIlIIlIlIlII >= lllllllllllllllllIllIIlIIlIlIlll + 2 && lllllllllllllllllIllIIlIIlIlIlII <= lllllllllllllllllIllIIlIIlIlIlll + 14 && Mouse.isButtonDown(0) && lllllllllllllllllIllIIlIIlIlIIll.isntPressed()) {
                        String lllllllllllllllllIllIIlIIlIIIlIl = (BoolValue)lllllllllllllllllIllIIlIIlIIlIII;
                        lllllllllllllllllIllIIlIIlIIIlIl.set(!(Boolean)lllllllllllllllllIllIIlIIlIIIlIl.get());
                        mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
                     }

                     GlStateManager.resetColor();
                     Fonts.font35.drawString(lllllllllllllllllIllIIlIIlIllIlI, lllllllllllllllllIllIIlIIlIlIIll.getX() + lllllllllllllllllIllIIlIIlIlIIll.getWidth() + 6, lllllllllllllllllIllIIlIIlIlIlll + 4, (Boolean)((BoolValue)lllllllllllllllllIllIIlIIlIIlIII).get() ? lllllllllllllllllIllIIlIIlIIllII : Integer.MAX_VALUE);
                     var10001 = false;
                     lllllllllllllllllIllIIlIIlIlIlll += 12;
                  } else {
                     int lllllllllllllllllIllIIlIIlIIIIlI;
                     String lllllllllllllllllIllIIlIIlIIIllI;
                     float lllllllllllllllllIllIIlIIlIIIlIl;
                     if (lllllllllllllllllIllIIlIIlIIlIII instanceof ListValue) {
                        byte lllllllllllllllllIllIIlIIlIIIlll = (ListValue)lllllllllllllllllIllIIlIIlIIlIII;
                        lllllllllllllllllIllIIlIIlIIIllI = lllllllllllllllllIllIIlIIlIIlIII.getName();
                        lllllllllllllllllIllIIlIIlIIIlIl = (float)Fonts.font35.getStringWidth(lllllllllllllllllIllIIlIIlIIIllI);
                        if (lllllllllllllllllIllIIlIIlIlIIll.getSettingsWidth() < lllllllllllllllllIllIIlIIlIIIlIl + 16.0F) {
                           lllllllllllllllllIllIIlIIlIlIIll.setSettingsWidth(lllllllllllllllllIllIIlIIlIIIlIl + 16.0F);
                        }

                        RenderUtils.drawRect((float)(lllllllllllllllllIllIIlIIlIlIIll.getX() + lllllllllllllllllIllIIlIIlIlIIll.getWidth() + 4), (float)(lllllllllllllllllIllIIlIIlIlIlll + 2), (float)(lllllllllllllllllIllIIlIIlIlIIll.getX() + lllllllllllllllllIllIIlIIlIlIIll.getWidth()) + lllllllllllllllllIllIIlIIlIlIIll.getSettingsWidth(), (float)(lllllllllllllllllIllIIlIIlIlIlll + 14), Integer.MIN_VALUE);
                        GlStateManager.resetColor();
                        Fonts.font35.drawString(String.valueOf((new StringBuilder()).append("§c").append(lllllllllllllllllIllIIlIIlIIIllI)), lllllllllllllllllIllIIlIIlIlIIll.getX() + lllllllllllllllllIllIIlIIlIlIIll.getWidth() + 6, lllllllllllllllllIllIIlIIlIlIlll + 4, 16777215);
                        var10001 = false;
                        Fonts.font35.drawString(lllllllllllllllllIllIIlIIlIIIlll.openList ? "-" : "+", (int)((float)(lllllllllllllllllIllIIlIIlIlIIll.getX() + lllllllllllllllllIllIIlIIlIlIIll.getWidth()) + lllllllllllllllllIllIIlIIlIlIIll.getSettingsWidth() - (float)(lllllllllllllllllIllIIlIIlIIIlll.openList ? 5 : 6)), lllllllllllllllllIllIIlIIlIlIlll + 4, 16777215);
                        var10001 = false;
                        if (lllllllllllllllllIllIIlIIlIlIlIl >= lllllllllllllllllIllIIlIIlIlIIll.getX() + lllllllllllllllllIllIIlIIlIlIIll.getWidth() + 4 && (float)lllllllllllllllllIllIIlIIlIlIlIl <= (float)(lllllllllllllllllIllIIlIIlIlIIll.getX() + lllllllllllllllllIllIIlIIlIlIIll.getWidth()) + lllllllllllllllllIllIIlIIlIlIIll.getSettingsWidth() && lllllllllllllllllIllIIlIIlIlIlII >= lllllllllllllllllIllIIlIIlIlIlll + 2 && lllllllllllllllllIllIIlIIlIlIlII <= lllllllllllllllllIllIIlIIlIlIlll + 14 && Mouse.isButtonDown(0) && lllllllllllllllllIllIIlIIlIlIIll.isntPressed()) {
                           lllllllllllllllllIllIIlIIlIIIlll.openList = !lllllllllllllllllIllIIlIIlIIIlll.openList;
                           mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
                        }

                        lllllllllllllllllIllIIlIIlIlIlll += 12;
                        double lllllllllllllllllIllIIlIIlIIIlII = lllllllllllllllllIllIIlIIlIIIlll.getValues();
                        byte lllllllllllllllllIllIIlIIlIIIIll = lllllllllllllllllIllIIlIIlIIIlII.length;

                        for(lllllllllllllllllIllIIlIIlIIIIlI = 0; lllllllllllllllllIllIIlIIlIIIIlI < lllllllllllllllllIllIIlIIlIIIIll; ++lllllllllllllllllIllIIlIIlIIIIlI) {
                           String lllllllllllllllllIllIIlIIlllIIll = lllllllllllllllllIllIIlIIlIIIlII[lllllllllllllllllIllIIlIIlIIIIlI];
                           short lllllllllllllllllIllIIlIIlIIIIII = (float)Fonts.font35.getStringWidth(String.valueOf((new StringBuilder()).append(">").append(lllllllllllllllllIllIIlIIlllIIll)));
                           if (lllllllllllllllllIllIIlIIlIlIIll.getSettingsWidth() < lllllllllllllllllIllIIlIIlIIIIII + 8.0F) {
                              lllllllllllllllllIllIIlIIlIlIIll.setSettingsWidth(lllllllllllllllllIllIIlIIlIIIIII + 8.0F);
                           }

                           if (lllllllllllllllllIllIIlIIlIIIlll.openList) {
                              RenderUtils.drawRect((float)(lllllllllllllllllIllIIlIIlIlIIll.getX() + lllllllllllllllllIllIIlIIlIlIIll.getWidth() + 4), (float)(lllllllllllllllllIllIIlIIlIlIlll + 2), (float)(lllllllllllllllllIllIIlIIlIlIIll.getX() + lllllllllllllllllIllIIlIIlIlIIll.getWidth()) + lllllllllllllllllIllIIlIIlIlIIll.getSettingsWidth(), (float)(lllllllllllllllllIllIIlIIlIlIlll + 14), Integer.MIN_VALUE);
                              if (lllllllllllllllllIllIIlIIlIlIlIl >= lllllllllllllllllIllIIlIIlIlIIll.getX() + lllllllllllllllllIllIIlIIlIlIIll.getWidth() + 4 && (float)lllllllllllllllllIllIIlIIlIlIlIl <= (float)(lllllllllllllllllIllIIlIIlIlIIll.getX() + lllllllllllllllllIllIIlIIlIlIIll.getWidth()) + lllllllllllllllllIllIIlIIlIlIIll.getSettingsWidth() && lllllllllllllllllIllIIlIIlIlIlII >= lllllllllllllllllIllIIlIIlIlIlll + 2 && lllllllllllllllllIllIIlIIlIlIlII <= lllllllllllllllllIllIIlIIlIlIlll + 14 && Mouse.isButtonDown(0) && lllllllllllllllllIllIIlIIlIlIIll.isntPressed()) {
                                 lllllllllllllllllIllIIlIIlIIIlll.set(lllllllllllllllllIllIIlIIlllIIll);
                                 mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
                              }

                              GlStateManager.resetColor();
                              Fonts.font35.drawString(">", lllllllllllllllllIllIIlIIlIlIIll.getX() + lllllllllllllllllIllIIlIIlIlIIll.getWidth() + 6, lllllllllllllllllIllIIlIIlIlIlll + 4, Integer.MAX_VALUE);
                              var10001 = false;
                              Fonts.font35.drawString(lllllllllllllllllIllIIlIIlllIIll, lllllllllllllllllIllIIlIIlIlIIll.getX() + lllllllllllllllllIllIIlIIlIlIIll.getWidth() + 14, lllllllllllllllllIllIIlIIlIlIlll + 4, lllllllllllllllllIllIIlIIlIIIlll.get() != null && ((String)lllllllllllllllllIllIIlIIlIIIlll.get()).equalsIgnoreCase(lllllllllllllllllIllIIlIIlllIIll) ? lllllllllllllllllIllIIlIIlIIllII : Integer.MAX_VALUE);
                              var10001 = false;
                              lllllllllllllllllIllIIlIIlIlIlll += 12;
                           }
                        }
                     } else {
                        float lllllllllllllllllIllIIlIIllIIllI;
                        double lllllllllllllllllIllIIlIIllIlIlI;
                        if (lllllllllllllllllIllIIlIIlIIlIII instanceof FloatValue) {
                           byte lllllllllllllllllIllIIlIIlIIIlll = (FloatValue)lllllllllllllllllIllIIlIIlIIlIII;
                           lllllllllllllllllIllIIlIIlIIIllI = String.valueOf((new StringBuilder()).append(lllllllllllllllllIllIIlIIlIIlIII.getName()).append("§f: §c").append(lllllllllllllllllIllIIlIIlIlIIII.round((Float)lllllllllllllllllIllIIlIIlIIIlll.get())));
                           lllllllllllllllllIllIIlIIlIIIlIl = (float)Fonts.font35.getStringWidth(lllllllllllllllllIllIIlIIlIIIllI);
                           if (lllllllllllllllllIllIIlIIlIlIIll.getSettingsWidth() < lllllllllllllllllIllIIlIIlIIIlIl + 8.0F) {
                              lllllllllllllllllIllIIlIIlIlIIll.setSettingsWidth(lllllllllllllllllIllIIlIIlIIIlIl + 8.0F);
                           }

                           RenderUtils.drawRect((float)(lllllllllllllllllIllIIlIIlIlIIll.getX() + lllllllllllllllllIllIIlIIlIlIIll.getWidth() + 4), (float)(lllllllllllllllllIllIIlIIlIlIlll + 2), (float)(lllllllllllllllllIllIIlIIlIlIIll.getX() + lllllllllllllllllIllIIlIIlIlIIll.getWidth()) + lllllllllllllllllIllIIlIIlIlIIll.getSettingsWidth(), (float)(lllllllllllllllllIllIIlIIlIlIlll + 24), Integer.MIN_VALUE);
                           RenderUtils.drawRect((float)(lllllllllllllllllIllIIlIIlIlIIll.getX() + lllllllllllllllllIllIIlIIlIlIIll.getWidth() + 8), (float)(lllllllllllllllllIllIIlIIlIlIlll + 18), (float)(lllllllllllllllllIllIIlIIlIlIIll.getX() + lllllllllllllllllIllIIlIIlIlIIll.getWidth()) + lllllllllllllllllIllIIlIIlIlIIll.getSettingsWidth() - 4.0F, (float)(lllllllllllllllllIllIIlIIlIlIlll + 19), Integer.MAX_VALUE);
                           lllllllllllllllllIllIIlIIllIIllI = (float)(lllllllllllllllllIllIIlIIlIlIIll.getX() + lllllllllllllllllIllIIlIIlIlIIll.getWidth()) + (lllllllllllllllllIllIIlIIlIlIIll.getSettingsWidth() - 12.0F) * ((Float)lllllllllllllllllIllIIlIIlIIIlll.get() - lllllllllllllllllIllIIlIIlIIIlll.getMinimum()) / (lllllllllllllllllIllIIlIIlIIIlll.getMaximum() - lllllllllllllllllIllIIlIIlIIIlll.getMinimum());
                           RenderUtils.drawRect(8.0F + lllllllllllllllllIllIIlIIllIIllI, (float)(lllllllllllllllllIllIIlIIlIlIlll + 15), lllllllllllllllllIllIIlIIllIIllI + 11.0F, (float)(lllllllllllllllllIllIIlIIlIlIlll + 21), lllllllllllllllllIllIIlIIlIIllII);
                           if (lllllllllllllllllIllIIlIIlIlIlIl >= lllllllllllllllllIllIIlIIlIlIIll.getX() + lllllllllllllllllIllIIlIIlIlIIll.getWidth() + 4 && (float)lllllllllllllllllIllIIlIIlIlIlIl <= (float)(lllllllllllllllllIllIIlIIlIlIIll.getX() + lllllllllllllllllIllIIlIIlIlIIll.getWidth()) + lllllllllllllllllIllIIlIIlIlIIll.getSettingsWidth() - 4.0F && lllllllllllllllllIllIIlIIlIlIlII >= lllllllllllllllllIllIIlIIlIlIlll + 15 && lllllllllllllllllIllIIlIIlIlIlII <= lllllllllllllllllIllIIlIIlIlIlll + 21 && Mouse.isButtonDown(0)) {
                              lllllllllllllllllIllIIlIIllIlIlI = MathHelper.clamp_double((double)((float)(lllllllllllllllllIllIIlIIlIlIlIl - lllllllllllllllllIllIIlIIlIlIIll.getX() - lllllllllllllllllIllIIlIIlIlIIll.getWidth() - 8) / (lllllllllllllllllIllIIlIIlIlIIll.getSettingsWidth() - 12.0F)), 0.0D, 1.0D);
                              lllllllllllllllllIllIIlIIlIIIlll.set(lllllllllllllllllIllIIlIIlIlIIII.round((float)((double)lllllllllllllllllIllIIlIIlIIIlll.getMinimum() + (double)(lllllllllllllllllIllIIlIIlIIIlll.getMaximum() - lllllllllllllllllIllIIlIIlIIIlll.getMinimum()) * lllllllllllllllllIllIIlIIllIlIlI)).floatValue());
                           }

                           GlStateManager.resetColor();
                           Fonts.font35.drawString(lllllllllllllllllIllIIlIIlIIIllI, lllllllllllllllllIllIIlIIlIlIIll.getX() + lllllllllllllllllIllIIlIIlIlIIll.getWidth() + 6, lllllllllllllllllIllIIlIIlIlIlll + 4, 16777215);
                           var10001 = false;
                           lllllllllllllllllIllIIlIIlIlIlll += 22;
                        } else if (lllllllllllllllllIllIIlIIlIIlIII instanceof IntegerValue) {
                           byte lllllllllllllllllIllIIlIIlIIIlll = (IntegerValue)lllllllllllllllllIllIIlIIlIIlIII;
                           lllllllllllllllllIllIIlIIlIIIllI = String.valueOf((new StringBuilder()).append(lllllllllllllllllIllIIlIIlIIlIII.getName()).append("§f: §c").append(lllllllllllllllllIllIIlIIlIIlIII instanceof BlockValue ? String.valueOf((new StringBuilder()).append(BlockUtils.getBlockName((Integer)lllllllllllllllllIllIIlIIlIIIlll.get())).append(" (").append(lllllllllllllllllIllIIlIIlIIIlll.get()).append(")")) : (Serializable)lllllllllllllllllIllIIlIIlIIIlll.get()));
                           lllllllllllllllllIllIIlIIlIIIlIl = (float)Fonts.font35.getStringWidth(lllllllllllllllllIllIIlIIlIIIllI);
                           if (lllllllllllllllllIllIIlIIlIlIIll.getSettingsWidth() < lllllllllllllllllIllIIlIIlIIIlIl + 8.0F) {
                              lllllllllllllllllIllIIlIIlIlIIll.setSettingsWidth(lllllllllllllllllIllIIlIIlIIIlIl + 8.0F);
                           }

                           RenderUtils.drawRect((float)(lllllllllllllllllIllIIlIIlIlIIll.getX() + lllllllllllllllllIllIIlIIlIlIIll.getWidth() + 4), (float)(lllllllllllllllllIllIIlIIlIlIlll + 2), (float)(lllllllllllllllllIllIIlIIlIlIIll.getX() + lllllllllllllllllIllIIlIIlIlIIll.getWidth()) + lllllllllllllllllIllIIlIIlIlIIll.getSettingsWidth(), (float)(lllllllllllllllllIllIIlIIlIlIlll + 24), Integer.MIN_VALUE);
                           RenderUtils.drawRect((float)(lllllllllllllllllIllIIlIIlIlIIll.getX() + lllllllllllllllllIllIIlIIlIlIIll.getWidth() + 8), (float)(lllllllllllllllllIllIIlIIlIlIlll + 18), (float)(lllllllllllllllllIllIIlIIlIlIIll.getX() + lllllllllllllllllIllIIlIIlIlIIll.getWidth()) + lllllllllllllllllIllIIlIIlIlIIll.getSettingsWidth() - 4.0F, (float)(lllllllllllllllllIllIIlIIlIlIlll + 19), Integer.MAX_VALUE);
                           lllllllllllllllllIllIIlIIllIIllI = (float)(lllllllllllllllllIllIIlIIlIlIIll.getX() + lllllllllllllllllIllIIlIIlIlIIll.getWidth()) + (lllllllllllllllllIllIIlIIlIlIIll.getSettingsWidth() - 12.0F) * (float)((Integer)lllllllllllllllllIllIIlIIlIIIlll.get() - lllllllllllllllllIllIIlIIlIIIlll.getMinimum()) / (float)(lllllllllllllllllIllIIlIIlIIIlll.getMaximum() - lllllllllllllllllIllIIlIIlIIIlll.getMinimum());
                           RenderUtils.drawRect(8.0F + lllllllllllllllllIllIIlIIllIIllI, (float)(lllllllllllllllllIllIIlIIlIlIlll + 15), lllllllllllllllllIllIIlIIllIIllI + 11.0F, (float)(lllllllllllllllllIllIIlIIlIlIlll + 21), lllllllllllllllllIllIIlIIlIIllII);
                           if (lllllllllllllllllIllIIlIIlIlIlIl >= lllllllllllllllllIllIIlIIlIlIIll.getX() + lllllllllllllllllIllIIlIIlIlIIll.getWidth() + 4 && (float)lllllllllllllllllIllIIlIIlIlIlIl <= (float)(lllllllllllllllllIllIIlIIlIlIIll.getX() + lllllllllllllllllIllIIlIIlIlIIll.getWidth()) + lllllllllllllllllIllIIlIIlIlIIll.getSettingsWidth() && lllllllllllllllllIllIIlIIlIlIlII >= lllllllllllllllllIllIIlIIlIlIlll + 15 && lllllllllllllllllIllIIlIIlIlIlII <= lllllllllllllllllIllIIlIIlIlIlll + 21 && Mouse.isButtonDown(0)) {
                              lllllllllllllllllIllIIlIIllIlIlI = MathHelper.clamp_double((double)((float)(lllllllllllllllllIllIIlIIlIlIlIl - lllllllllllllllllIllIIlIIlIlIIll.getX() - lllllllllllllllllIllIIlIIlIlIIll.getWidth() - 8) / (lllllllllllllllllIllIIlIIlIlIIll.getSettingsWidth() - 12.0F)), 0.0D, 1.0D);
                              lllllllllllllllllIllIIlIIlIIIlll.set((int)((double)lllllllllllllllllIllIIlIIlIIIlll.getMinimum() + (double)(lllllllllllllllllIllIIlIIlIIIlll.getMaximum() - lllllllllllllllllIllIIlIIlIIIlll.getMinimum()) * lllllllllllllllllIllIIlIIllIlIlI));
                           }

                           GlStateManager.resetColor();
                           Fonts.font35.drawString(lllllllllllllllllIllIIlIIlIIIllI, lllllllllllllllllIllIIlIIlIlIIll.getX() + lllllllllllllllllIllIIlIIlIlIIll.getWidth() + 6, lllllllllllllllllIllIIlIIlIlIlll + 4, 16777215);
                           var10001 = false;
                           lllllllllllllllllIllIIlIIlIlIlll += 22;
                        } else if (lllllllllllllllllIllIIlIIlIIlIII instanceof FontValue) {
                           FontValue lllllllllllllllllIllIIlIIlIllllI = (FontValue)lllllllllllllllllIllIIlIIlIIlIII;
                           long lllllllllllllllllIllIIlIIlIIIllI = (FontRenderer)lllllllllllllllllIllIIlIIlIllllI.get();
                           RenderUtils.drawRect((float)(lllllllllllllllllIllIIlIIlIlIIll.getX() + lllllllllllllllllIllIIlIIlIlIIll.getWidth() + 4), (float)(lllllllllllllllllIllIIlIIlIlIlll + 2), (float)(lllllllllllllllllIllIIlIIlIlIIll.getX() + lllllllllllllllllIllIIlIIlIlIIll.getWidth()) + lllllllllllllllllIllIIlIIlIlIIll.getSettingsWidth(), (float)(lllllllllllllllllIllIIlIIlIlIlll + 14), Integer.MIN_VALUE);
                           String lllllllllllllllllIllIIlIIlIIIlIl = "Font: Unknown";
                           if (lllllllllllllllllIllIIlIIlIIIllI instanceof GameFontRenderer) {
                              double lllllllllllllllllIllIIlIIlIIIlII = (GameFontRenderer)lllllllllllllllllIllIIlIIlIIIllI;
                              lllllllllllllllllIllIIlIIlIIIlIl = String.valueOf((new StringBuilder()).append("Font: ").append(lllllllllllllllllIllIIlIIlIIIlII.getDefaultFont().getFont().getName()).append(" - ").append(lllllllllllllllllIllIIlIIlIIIlII.getDefaultFont().getFont().getSize()));
                           } else if (lllllllllllllllllIllIIlIIlIIIllI == Fonts.minecraftFont) {
                              lllllllllllllllllIllIIlIIlIIIlIl = "Font: Minecraft";
                           } else {
                              double lllllllllllllllllIllIIlIIlIIIlII = Fonts.getFontDetails(lllllllllllllllllIllIIlIIlIIIllI);
                              if (lllllllllllllllllIllIIlIIlIIIlII != null) {
                                 lllllllllllllllllIllIIlIIlIIIlIl = String.valueOf((new StringBuilder()).append(lllllllllllllllllIllIIlIIlIIIlII[0]).append((Integer)lllllllllllllllllIllIIlIIlIIIlII[1] != -1 ? String.valueOf((new StringBuilder()).append(" - ").append(lllllllllllllllllIllIIlIIlIIIlII[1])) : ""));
                              }
                           }

                           Fonts.font35.drawString(lllllllllllllllllIllIIlIIlIIIlIl, lllllllllllllllllIllIIlIIlIlIIll.getX() + lllllllllllllllllIllIIlIIlIlIIll.getWidth() + 6, lllllllllllllllllIllIIlIIlIlIlll + 4, Color.WHITE.getRGB());
                           var10001 = false;
                           double lllllllllllllllllIllIIlIIlIIIlII = Fonts.font35.getStringWidth(lllllllllllllllllIllIIlIIlIIIlIl);
                           if (lllllllllllllllllIllIIlIIlIlIIll.getSettingsWidth() < (float)(lllllllllllllllllIllIIlIIlIIIlII + 8)) {
                              lllllllllllllllllIllIIlIIlIlIIll.setSettingsWidth((float)(lllllllllllllllllIllIIlIIlIIIlII + 8));
                           }

                           if ((Mouse.isButtonDown(0) && !lllllllllllllllllIllIIlIIlIlIIII.mouseDown || Mouse.isButtonDown(1) && !lllllllllllllllllIllIIlIIlIlIIII.rightMouseDown) && lllllllllllllllllIllIIlIIlIlIlIl >= lllllllllllllllllIllIIlIIlIlIIll.getX() + lllllllllllllllllIllIIlIIlIlIIll.getWidth() + 4 && (float)lllllllllllllllllIllIIlIIlIlIlIl <= (float)(lllllllllllllllllIllIIlIIlIlIIll.getX() + lllllllllllllllllIllIIlIIlIlIIll.getWidth()) + lllllllllllllllllIllIIlIIlIlIIll.getSettingsWidth() && lllllllllllllllllIllIIlIIlIlIlII >= lllllllllllllllllIllIIlIIlIlIlll + 4 && lllllllllllllllllIllIIlIIlIlIlII <= lllllllllllllllllIllIIlIIlIlIlll + 12) {
                              List<FontRenderer> lllllllllllllllllIllIIlIIlIlllll = Fonts.getFonts();
                              FontRenderer lllllllllllllllllIllIIlIIlIIIIIl;
                              if (Mouse.isButtonDown(0)) {
                                 for(lllllllllllllllllIllIIlIIlIIIIlI = 0; lllllllllllllllllIllIIlIIlIIIIlI < lllllllllllllllllIllIIlIIlIlllll.size(); ++lllllllllllllllllIllIIlIIlIIIIlI) {
                                    lllllllllllllllllIllIIlIIlIIIIIl = (FontRenderer)lllllllllllllllllIllIIlIIlIlllll.get(lllllllllllllllllIllIIlIIlIIIIlI);
                                    if (lllllllllllllllllIllIIlIIlIIIIIl == lllllllllllllllllIllIIlIIlIIIllI) {
                                       ++lllllllllllllllllIllIIlIIlIIIIlI;
                                       if (lllllllllllllllllIllIIlIIlIIIIlI >= lllllllllllllllllIllIIlIIlIlllll.size()) {
                                          lllllllllllllllllIllIIlIIlIIIIlI = 0;
                                       }

                                       lllllllllllllllllIllIIlIIlIllllI.set(lllllllllllllllllIllIIlIIlIlllll.get(lllllllllllllllllIllIIlIIlIIIIlI));
                                       break;
                                    }
                                 }
                              } else {
                                 for(lllllllllllllllllIllIIlIIlIIIIlI = lllllllllllllllllIllIIlIIlIlllll.size() - 1; lllllllllllllllllIllIIlIIlIIIIlI >= 0; --lllllllllllllllllIllIIlIIlIIIIlI) {
                                    lllllllllllllllllIllIIlIIlIIIIIl = (FontRenderer)lllllllllllllllllIllIIlIIlIlllll.get(lllllllllllllllllIllIIlIIlIIIIlI);
                                    if (lllllllllllllllllIllIIlIIlIIIIIl == lllllllllllllllllIllIIlIIlIIIllI) {
                                       --lllllllllllllllllIllIIlIIlIIIIlI;
                                       if (lllllllllllllllllIllIIlIIlIIIIlI >= lllllllllllllllllIllIIlIIlIlllll.size()) {
                                          lllllllllllllllllIllIIlIIlIIIIlI = 0;
                                       }

                                       if (lllllllllllllllllIllIIlIIlIIIIlI < 0) {
                                          lllllllllllllllllIllIIlIIlIIIIlI = lllllllllllllllllIllIIlIIlIlllll.size() - 1;
                                       }

                                       lllllllllllllllllIllIIlIIlIllllI.set(lllllllllllllllllIllIIlIIlIlllll.get(lllllllllllllllllIllIIlIIlIIIIlI));
                                       break;
                                    }
                                 }
                              }
                           }

                           lllllllllllllllllIllIIlIIlIlIlll += 11;
                        } else {
                           lllllllllllllllllIllIIlIIlIllIlI = String.valueOf((new StringBuilder()).append(lllllllllllllllllIllIIlIIlIIlIII.getName()).append("§f: §c").append(lllllllllllllllllIllIIlIIlIIlIII.get()));
                           lllllllllllllllllIllIIlIIlIIIllI = (float)Fonts.font35.getStringWidth(lllllllllllllllllIllIIlIIlIllIlI);
                           if (lllllllllllllllllIllIIlIIlIlIIll.getSettingsWidth() < lllllllllllllllllIllIIlIIlIIIllI + 8.0F) {
                              lllllllllllllllllIllIIlIIlIlIIll.setSettingsWidth(lllllllllllllllllIllIIlIIlIIIllI + 8.0F);
                           }

                           RenderUtils.drawRect((float)(lllllllllllllllllIllIIlIIlIlIIll.getX() + lllllllllllllllllIllIIlIIlIlIIll.getWidth() + 4), (float)(lllllllllllllllllIllIIlIIlIlIlll + 2), (float)(lllllllllllllllllIllIIlIIlIlIIll.getX() + lllllllllllllllllIllIIlIIlIlIIll.getWidth()) + lllllllllllllllllIllIIlIIlIlIIll.getSettingsWidth(), (float)(lllllllllllllllllIllIIlIIlIlIlll + 14), Integer.MIN_VALUE);
                           GlStateManager.resetColor();
                           Fonts.font35.drawString(lllllllllllllllllIllIIlIIlIllIlI, lllllllllllllllllIllIIlIIlIlIIll.getX() + lllllllllllllllllIllIIlIIlIlIIll.getWidth() + 6, lllllllllllllllllIllIIlIIlIlIlll + 4, 16777215);
                           var10001 = false;
                           lllllllllllllllllIllIIlIIlIlIlll += 12;
                        }
                     }
                  }
               }

               lllllllllllllllllIllIIlIIlIlIIll.updatePressed();
               lllllllllllllllllIllIIlIIlIlIIII.mouseDown = Mouse.isButtonDown(0);
               lllllllllllllllllIllIIlIIlIlIIII.rightMouseDown = Mouse.isButtonDown(1);
               if (lllllllllllllllllIllIIlIIlIlIIll.getSettingsWidth() > 0.0F && lllllllllllllllllIllIIlIIlIlIlll > lllllllllllllllllIllIIlIIlIlIIll.getY() + 4) {
                  RenderUtils.drawBorderedRect((float)(lllllllllllllllllIllIIlIIlIlIIll.getX() + lllllllllllllllllIllIIlIIlIlIIll.getWidth() + 4), (float)(lllllllllllllllllIllIIlIIlIlIIll.getY() + 6), (float)(lllllllllllllllllIllIIlIIlIlIIll.getX() + lllllllllllllllllIllIIlIIlIlIIll.getWidth()) + lllllllllllllllllIllIIlIIlIlIIll.getSettingsWidth(), (float)(lllllllllllllllllIllIIlIIlIlIlll + 2), 1.0F, Integer.MIN_VALUE, 0);
               }
               break;
            }
         }
      }

   }

   public void drawButtonElement(int lllllllllllllllllIllIIlIlIIllIll, int lllllllllllllllllIllIIlIlIIllIlI, ButtonElement lllllllllllllllllIllIIlIlIIllIII) {
      GlStateManager.resetColor();
      Fonts.font35.drawString(lllllllllllllllllIllIIlIlIIllIII.getDisplayName(), (int)((float)lllllllllllllllllIllIIlIlIIllIII.getX() - ((float)Fonts.font35.getStringWidth(lllllllllllllllllIllIIlIlIIllIII.getDisplayName()) - 100.0F) / 2.0F), lllllllllllllllllIllIIlIlIIllIII.getY() + 6, lllllllllllllllllIllIIlIlIIllIII.getColor());
      boolean var10001 = false;
   }

   public void drawDescription(int lllllllllllllllllIllIIlIlIlIlIlI, int lllllllllllllllllIllIIlIlIlIlIIl, String lllllllllllllllllIllIIlIlIlIIlll) {
      float lllllllllllllllllIllIIlIlIlIIIlI = Fonts.font35.getStringWidth(lllllllllllllllllIllIIlIlIlIIlll);
      RenderUtils.drawBorderedRect((float)(lllllllllllllllllIllIIlIlIlIlIlI + 9), (float)lllllllllllllllllIllIIlIlIlIlIIl, (float)(lllllllllllllllllIllIIlIlIlIlIlI + lllllllllllllllllIllIIlIlIlIIIlI + 14), (float)(lllllllllllllllllIllIIlIlIlIlIIl + Fonts.font35.FONT_HEIGHT + 3), 1.0F, (new Color(255, 255, 255, 90)).getRGB(), Integer.MIN_VALUE);
      GlStateManager.resetColor();
      Fonts.font35.drawString(lllllllllllllllllIllIIlIlIlIIlll, lllllllllllllllllIllIIlIlIlIlIlI + 12, lllllllllllllllllIllIIlIlIlIlIIl + Fonts.font35.FONT_HEIGHT / 2, Integer.MAX_VALUE);
      boolean var10001 = false;
   }
}
