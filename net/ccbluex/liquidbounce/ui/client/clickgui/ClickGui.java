//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.ui.client.clickgui;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.modules.render.ClickGUI;
import net.ccbluex.liquidbounce.ui.client.clickgui.elements.ButtonElement;
import net.ccbluex.liquidbounce.ui.client.clickgui.elements.Element;
import net.ccbluex.liquidbounce.ui.client.clickgui.elements.ModuleElement;
import net.ccbluex.liquidbounce.ui.client.clickgui.style.Style;
import net.ccbluex.liquidbounce.ui.client.clickgui.style.styles.SlowlyStyle;
import net.ccbluex.liquidbounce.ui.client.hud.designer.GuiHudDesigner;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

public class ClickGui extends GuiScreen {
   // $FF: synthetic field
   private final ResourceLocation hudIcon = new ResourceLocation("liquidbounce/custom_hud_icon.png");
   // $FF: synthetic field
   private int mouseX;
   // $FF: synthetic field
   public final List<Panel> panels = new ArrayList();
   // $FF: synthetic field
   public Style style = new SlowlyStyle();
   // $FF: synthetic field
   private int mouseY;
   // $FF: synthetic field
   private Panel clickedPanel;

   public boolean doesGuiPauseGame() {
      return false;
   }

   public void onGuiClosed() {
      LiquidBounce.fileManager.saveConfig(LiquidBounce.fileManager.clickGuiConfig);
   }

   public void updateScreen() {
      Iterator lIlIlIIIlIlIlll = lIlIlIIIlIllIII.panels.iterator();

      while(lIlIlIIIlIlIlll.hasNext()) {
         Panel lIlIlIIIlIllIlI = (Panel)lIlIlIIIlIlIlll.next();
         Iterator lIlIlIIIlIlIlIl = lIlIlIIIlIllIlI.getElements().iterator();

         while(lIlIlIIIlIlIlIl.hasNext()) {
            Element lIlIlIIIlIllIll = (Element)lIlIlIIIlIlIlIl.next();
            if (lIlIlIIIlIllIll instanceof ButtonElement) {
               ButtonElement lIlIlIIIlIlllII = (ButtonElement)lIlIlIIIlIllIll;
               if (lIlIlIIIlIlllII.isHovering(lIlIlIIIlIllIII.mouseX, lIlIlIIIlIllIII.mouseY)) {
                  if (lIlIlIIIlIlllII.hoverTime < 7) {
                     ++lIlIlIIIlIlllII.hoverTime;
                  }
               } else if (lIlIlIIIlIlllII.hoverTime > 0) {
                  --lIlIlIIIlIlllII.hoverTime;
               }
            }

            if (lIlIlIIIlIllIll instanceof ModuleElement) {
               if (((ModuleElement)lIlIlIIIlIllIll).getModule().getState()) {
                  if (((ModuleElement)lIlIlIIIlIllIll).slowlyFade < 255) {
                     ((ModuleElement)lIlIlIIIlIllIll).slowlyFade += 20;
                  }
               } else if (((ModuleElement)lIlIlIIIlIllIll).slowlyFade > 0) {
                  ((ModuleElement)lIlIlIIIlIllIll).slowlyFade -= 20;
               }

               if (((ModuleElement)lIlIlIIIlIllIll).slowlyFade > 255) {
                  ((ModuleElement)lIlIlIIIlIllIll).slowlyFade = 255;
               }

               if (((ModuleElement)lIlIlIIIlIllIll).slowlyFade < 0) {
                  ((ModuleElement)lIlIlIIIlIllIll).slowlyFade = 0;
               }
            }
         }
      }

      super.updateScreen();
   }

   public void drawScreen(int lIlIlIIlIIllIll, int lIlIlIIlIIlIlIl, float lIlIlIIlIIlIlII) {
      if (Mouse.isButtonDown(0) && lIlIlIIlIIllIll >= 5 && lIlIlIIlIIllIll <= 50 && lIlIlIIlIIlIlIl <= lIlIlIIlIIlIlll.height - 5 && lIlIlIIlIIlIlIl >= lIlIlIIlIIlIlll.height - 50) {
         lIlIlIIlIIlIlll.mc.displayGuiScreen(new GuiHudDesigner());
      }

      double lIlIlIIlIIllIII = (double)(Float)((ClickGUI)Objects.requireNonNull(LiquidBounce.moduleManager.getModule(ClickGUI.class))).scaleValue.get();
      lIlIlIIlIIllIll = (int)((double)lIlIlIIlIIllIll / lIlIlIIlIIllIII);
      lIlIlIIlIIlIlIl = (int)((double)lIlIlIIlIIlIlIl / lIlIlIIlIIllIII);
      lIlIlIIlIIlIlll.mouseX = lIlIlIIlIIllIll;
      lIlIlIIlIIlIlll.mouseY = lIlIlIIlIIlIlIl;
      lIlIlIIlIIlIlll.drawDefaultBackground();
      RenderUtils.drawFilledCircle(25, lIlIlIIlIIlIlll.height - 25, 16.0F, new Color(37, 126, 255));
      RenderUtils.drawImage(lIlIlIIlIIlIlll.hudIcon, 12, lIlIlIIlIIlIlll.height - 35, 23, 23);
      GlStateManager.scale(lIlIlIIlIIllIII, lIlIlIIlIIllIII, lIlIlIIlIIllIII);
      Iterator lIlIlIIlIIlIIlI = lIlIlIIlIIlIlll.panels.iterator();

      Panel lIlIlIIlIIlllll;
      while(lIlIlIIlIIlIIlI.hasNext()) {
         lIlIlIIlIIlllll = (Panel)lIlIlIIlIIlIIlI.next();
         lIlIlIIlIIlllll.updateFade(RenderUtils.deltaTime);
         lIlIlIIlIIlllll.drawScreen(lIlIlIIlIIllIll, lIlIlIIlIIlIlIl, lIlIlIIlIIlIlII);
      }

      lIlIlIIlIIlIIlI = lIlIlIIlIIlIlll.panels.iterator();

      while(lIlIlIIlIIlIIlI.hasNext()) {
         lIlIlIIlIIlllll = (Panel)lIlIlIIlIIlIIlI.next();
         Iterator lIlIlIIlIIlIIII = lIlIlIIlIIlllll.getElements().iterator();

         while(lIlIlIIlIIlIIII.hasNext()) {
            String lIlIlIIlIIIllll = (Element)lIlIlIIlIIlIIII.next();
            if (lIlIlIIlIIIllll instanceof ModuleElement) {
               ModuleElement lIlIlIIlIlIIIIl = (ModuleElement)lIlIlIIlIIIllll;
               if (lIlIlIIlIIllIll != 0 && lIlIlIIlIIlIlIl != 0 && lIlIlIIlIlIIIIl.isHovering(lIlIlIIlIIllIll, lIlIlIIlIIlIlIl) && lIlIlIIlIlIIIIl.isVisible() && lIlIlIIlIIIllll.getY() <= lIlIlIIlIIlllll.getY() + lIlIlIIlIIlllll.getFade()) {
                  lIlIlIIlIIlIlll.style.drawDescription(lIlIlIIlIIllIll, lIlIlIIlIIlIlIl, lIlIlIIlIlIIIIl.getModule().getDescription());
               }
            }
         }
      }

      if (Mouse.hasWheel()) {
         char lIlIlIIlIIlIIlI = Mouse.getDWheel();

         for(int lIlIlIIlIIllllI = lIlIlIIlIIlIlll.panels.size() - 1; lIlIlIIlIIllllI >= 0 && !((Panel)lIlIlIIlIIlIlll.panels.get(lIlIlIIlIIllllI)).handleScroll(lIlIlIIlIIllIll, lIlIlIIlIIlIlIl, lIlIlIIlIIlIIlI); --lIlIlIIlIIllllI) {
         }
      }

      GlStateManager.disableLighting();
      RenderHelper.disableStandardItemLighting();
      GlStateManager.scale(1.0F, 1.0F, 1.0F);
      super.drawScreen(lIlIlIIlIIllIll, lIlIlIIlIIlIlIl, lIlIlIIlIIlIlII);
   }

   protected void mouseReleased(int lIlIlIIIllIlIll, int lIlIlIIIllIlIlI, int lIlIlIIIllIlIIl) {
      long lIlIlIIIllIlIII = (double)(Float)((ClickGUI)Objects.requireNonNull(LiquidBounce.moduleManager.getModule(ClickGUI.class))).scaleValue.get();
      lIlIlIIIllIlIll = (int)((double)lIlIlIIIllIlIll / lIlIlIIIllIlIII);
      lIlIlIIIllIlIlI = (int)((double)lIlIlIIIllIlIlI / lIlIlIIIllIlIII);
      Iterator lIlIlIIIllIIlll = lIlIlIIIlllIIIl.panels.iterator();

      while(lIlIlIIIllIIlll.hasNext()) {
         Panel lIlIlIIIlllIIlI = (Panel)lIlIlIIIllIIlll.next();
         lIlIlIIIlllIIlI.mouseReleased(lIlIlIIIllIlIll, lIlIlIIIllIlIlI, lIlIlIIIllIlIIl);
      }

      super.mouseReleased(lIlIlIIIllIlIll, lIlIlIIIllIlIlI, lIlIlIIIllIlIIl);
   }

   public ClickGui() {
      int lIlIlIIlIllIlll = true;
      int lIlIlIIlIllIllI = true;
      float lIlIlIIlIllIIIl = 5;
      boolean lIlIlIIlIllIIII = ModuleCategory.values();
      byte lIlIlIIlIlIllll = lIlIlIIlIllIIII.length;

      boolean var10001;
      for(int lIlIlIIlIlIlllI = 0; lIlIlIIlIlIlllI < lIlIlIIlIlIllll; ++lIlIlIIlIlIlllI) {
         final long lIlIlIIlIlIllIl = lIlIlIIlIllIIII[lIlIlIIlIlIlllI];
         lIlIlIIlIlllIII.panels.add(new Panel(lIlIlIIlIlIllIl.getDisplayName(), 100, lIlIlIIlIllIIIl, 100, 18, false) {
            public void setupItems() {
               Iterator llllllllllllllllllIIlIIlIIlllIII = LiquidBounce.moduleManager.getModules().iterator();

               while(llllllllllllllllllIIlIIlIIlllIII.hasNext()) {
                  byte llllllllllllllllllIIlIIlIIllIlll = (Module)llllllllllllllllllIIlIIlIIlllIII.next();
                  if (llllllllllllllllllIIlIIlIIllIlll.getCategory() == lIlIlIIlIlIllIl) {
                     llllllllllllllllllIIlIIlIIlllIlI.getElements().add(new ModuleElement(llllllllllllllllllIIlIIlIIllIlll));
                     boolean var10001 = false;
                  }
               }

            }
         });
         var10001 = false;
         lIlIlIIlIllIIIl += 20;
      }

      lIlIlIIlIllIIIl += 20;
      lIlIlIIlIlllIII.panels.add(new Panel("Targets", 100, lIlIlIIlIllIIIl, 100, 18, false) {
         public void setupItems() {
            lllllllllllllllllIllIIIlllIIIllI.getElements().add(new ButtonElement("Players") {
               public void mouseClicked(int lllIlIlIIlllll, int lllIlIlIIllllI, int lllIlIlIIlllIl) {
                  if (lllIlIlIIlllIl == 0 && lllIlIlIIlllII.isHovering(lllIlIlIIlllll, lllIlIlIIllllI) && lllIlIlIIlllII.isVisible()) {
                     EntityUtils.targetPlayer = !EntityUtils.targetPlayer;
                     lllIlIlIIlllII.displayName = "Players";
                     lllIlIlIIlllII.color = EntityUtils.targetPlayer ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                     mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
                  }

               }

               public void createButton(String lllIlIlIlIlIlI) {
                  lllIlIlIlIlIll.color = EntityUtils.targetPlayer ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                  super.createButton(lllIlIlIlIlIlI);
               }

               public String getDisplayName() {
                  lllIlIlIlIIlIl.displayName = "Players";
                  lllIlIlIlIIlIl.color = EntityUtils.targetPlayer ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                  return super.getDisplayName();
               }
            });
            boolean var10001 = false;
            lllllllllllllllllIllIIIlllIIIllI.getElements().add(new ButtonElement("Mobs") {
               public void createButton(String lIIlIllIIIIIll) {
                  lIIlIllIIIIllI.color = EntityUtils.targetMobs ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                  super.createButton(lIIlIllIIIIIll);
               }

               public String getDisplayName() {
                  lIIlIllIIIIIII.displayName = "Mobs";
                  lIIlIllIIIIIII.color = EntityUtils.targetMobs ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                  return super.getDisplayName();
               }

               public void mouseClicked(int lIIlIlIlllIllI, int lIIlIlIllllIIl, int lIIlIlIllllIII) {
                  if (lIIlIlIllllIII == 0 && lIIlIlIllllIll.isHovering(lIIlIlIlllIllI, lIIlIlIllllIIl) && lIIlIlIllllIll.isVisible()) {
                     EntityUtils.targetMobs = !EntityUtils.targetMobs;
                     lIIlIlIllllIll.displayName = "Mobs";
                     lIIlIlIllllIll.color = EntityUtils.targetMobs ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                     mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
                  }

               }
            });
            var10001 = false;
            lllllllllllllllllIllIIIlllIIIllI.getElements().add(new ButtonElement("Animals") {
               public void createButton(String lIlIIlIIlllll) {
                  lIlIIlIlIIIlI.color = EntityUtils.targetAnimals ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                  super.createButton(lIlIIlIIlllll);
               }

               public String getDisplayName() {
                  lIlIIlIIlllIl.displayName = "Animals";
                  lIlIIlIIlllIl.color = EntityUtils.targetAnimals ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                  return super.getDisplayName();
               }

               public void mouseClicked(int lIlIIlIIlIIlI, int lIlIIlIIlIlIl, int lIlIIlIIlIlII) {
                  if (lIlIIlIIlIlII == 0 && lIlIIlIIlIIll.isHovering(lIlIIlIIlIIlI, lIlIIlIIlIlIl) && lIlIIlIIlIIll.isVisible()) {
                     EntityUtils.targetAnimals = !EntityUtils.targetAnimals;
                     lIlIIlIIlIIll.displayName = "Animals";
                     lIlIIlIIlIIll.color = EntityUtils.targetAnimals ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                     mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
                  }

               }
            });
            var10001 = false;
            lllllllllllllllllIllIIIlllIIIllI.getElements().add(new ButtonElement("Invisible") {
               public String getDisplayName() {
                  llIlllIIIllII.displayName = "Invisible";
                  llIlllIIIllII.color = EntityUtils.targetInvisible ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                  return super.getDisplayName();
               }

               public void createButton(String llIlllIIllIlI) {
                  llIlllIIlllII.color = EntityUtils.targetInvisible ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                  super.createButton(llIlllIIllIlI);
               }

               public void mouseClicked(int llIlllIIIIlIl, int llIlllIIIIIII, int llIlllIIIIIll) {
                  if (llIlllIIIIIll == 0 && llIlllIIIIllI.isHovering(llIlllIIIIlIl, llIlllIIIIIII) && llIlllIIIIllI.isVisible()) {
                     EntityUtils.targetInvisible = !EntityUtils.targetInvisible;
                     llIlllIIIIllI.displayName = "Invisible";
                     llIlllIIIIllI.color = EntityUtils.targetInvisible ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                     mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
                  }

               }
            });
            var10001 = false;
            lllllllllllllllllIllIIIlllIIIllI.getElements().add(new ButtonElement("Dead") {
               public void mouseClicked(int lIlllIlIIIlIIl, int lIlllIlIIIIlII, int lIlllIlIIIIlll) {
                  if (lIlllIlIIIIlll == 0 && lIlllIlIIIlIlI.isHovering(lIlllIlIIIlIIl, lIlllIlIIIIlII) && lIlllIlIIIlIlI.isVisible()) {
                     EntityUtils.targetDead = !EntityUtils.targetDead;
                     lIlllIlIIIlIlI.displayName = "Dead";
                     lIlllIlIIIlIlI.color = EntityUtils.targetDead ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                     mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
                  }

               }

               public String getDisplayName() {
                  lIlllIlIIlIIII.displayName = "Dead";
                  lIlllIlIIlIIII.color = EntityUtils.targetDead ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                  return super.getDisplayName();
               }

               public void createButton(String lIlllIlIIlIlII) {
                  lIlllIlIIlIIll.color = EntityUtils.targetDead ? ClickGUI.generateColor().getRGB() : Integer.MAX_VALUE;
                  super.createButton(lIlllIlIIlIlII);
               }
            });
            var10001 = false;
         }
      });
      var10001 = false;
   }

   protected void mouseClicked(int lIlIlIIlIIIIlII, int lIlIlIIlIIIIIll, int lIlIlIIIlllllIl) throws IOException {
      char lIlIlIIIlllllII = (double)(Float)((ClickGUI)Objects.requireNonNull(LiquidBounce.moduleManager.getModule(ClickGUI.class))).scaleValue.get();
      lIlIlIIlIIIIlII = (int)((double)lIlIlIIlIIIIlII / lIlIlIIIlllllII);
      lIlIlIIlIIIIIll = (int)((double)lIlIlIIlIIIIIll / lIlIlIIIlllllII);
      Iterator lIlIlIIIllllIll = lIlIlIIlIIIIIII.panels.iterator();

      while(lIlIlIIIllllIll.hasNext()) {
         short lIlIlIIIllllIlI = (Panel)lIlIlIIIllllIll.next();
         lIlIlIIIllllIlI.mouseClicked(lIlIlIIlIIIIlII, lIlIlIIlIIIIIll, lIlIlIIIlllllIl);
         lIlIlIIIllllIlI.drag = false;
         if (lIlIlIIIlllllIl == 0 && lIlIlIIIllllIlI.isHovering(lIlIlIIlIIIIlII, lIlIlIIlIIIIIll)) {
            lIlIlIIlIIIIIII.clickedPanel = lIlIlIIIllllIlI;
         }
      }

      if (lIlIlIIlIIIIIII.clickedPanel != null) {
         lIlIlIIlIIIIIII.clickedPanel.x2 = lIlIlIIlIIIIIII.clickedPanel.x - lIlIlIIlIIIIlII;
         lIlIlIIlIIIIIII.clickedPanel.y2 = lIlIlIIlIIIIIII.clickedPanel.y - lIlIlIIlIIIIIll;
         lIlIlIIlIIIIIII.clickedPanel.drag = true;
         lIlIlIIlIIIIIII.panels.remove(lIlIlIIlIIIIIII.clickedPanel);
         boolean var10001 = false;
         lIlIlIIlIIIIIII.panels.add(lIlIlIIlIIIIIII.clickedPanel);
         var10001 = false;
         lIlIlIIlIIIIIII.clickedPanel = null;
      }

      super.mouseClicked(lIlIlIIlIIIIlII, lIlIlIIlIIIIIll, lIlIlIIIlllllIl);
   }
}
