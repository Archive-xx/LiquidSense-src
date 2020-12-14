//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.ui.client.clickgui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.render.ClickGUI;
import net.ccbluex.liquidbounce.ui.client.clickgui.elements.Element;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class Panel extends MinecraftInstance {
   // $FF: synthetic field
   private final String name;
   // $FF: synthetic field
   public int y2;
   // $FF: synthetic field
   private boolean scrollbar;
   // $FF: synthetic field
   public int y;
   // $FF: synthetic field
   private final int height;
   // $FF: synthetic field
   private float fade;
   // $FF: synthetic field
   private float elementsHeight;
   // $FF: synthetic field
   public int x2;
   // $FF: synthetic field
   private boolean visible;
   // $FF: synthetic field
   public boolean drag;
   // $FF: synthetic field
   private final List<Element> elements;
   // $FF: synthetic field
   private boolean open;
   // $FF: synthetic field
   private final int width;
   // $FF: synthetic field
   private int dragged;
   // $FF: synthetic field
   public int x;
   // $FF: synthetic field
   private int scroll;

   public int getHeight() {
      return llllllllllllllllllllllIIlIIIIlll.height;
   }

   public void mouseReleased(int llllllllllllllllllllllIIllIlIIlI, int llllllllllllllllllllllIIllIlIIIl, int llllllllllllllllllllllIIllIIllII) {
      if (llllllllllllllllllllllIIllIlIIll.visible) {
         llllllllllllllllllllllIIllIlIIll.drag = false;
         if (llllllllllllllllllllllIIllIlIIll.open) {
            Iterator llllllllllllllllllllllIIllIIlIll = llllllllllllllllllllllIIllIlIIll.elements.iterator();

            while(llllllllllllllllllllllIIllIIlIll.hasNext()) {
               Element llllllllllllllllllllllIIllIlIlII = (Element)llllllllllllllllllllllIIllIIlIll.next();
               llllllllllllllllllllllIIllIlIlII.mouseReleased(llllllllllllllllllllllIIllIlIIlI, llllllllllllllllllllllIIllIlIIIl, llllllllllllllllllllllIIllIIllII);
            }

         }
      }
   }

   void updateFade(int llllllllllllllllllllllIIlIllIIlI) {
      if (llllllllllllllllllllllIIlIllIlIl.open) {
         if (llllllllllllllllllllllIIlIllIlIl.fade < llllllllllllllllllllllIIlIllIlIl.elementsHeight) {
            llllllllllllllllllllllIIlIllIlIl.fade += 0.4F * (float)llllllllllllllllllllllIIlIllIIlI;
         }

         if (llllllllllllllllllllllIIlIllIlIl.fade > llllllllllllllllllllllIIlIllIlIl.elementsHeight) {
            llllllllllllllllllllllIIlIllIlIl.fade = (float)((int)llllllllllllllllllllllIIlIllIlIl.elementsHeight);
         }
      } else {
         if (llllllllllllllllllllllIIlIllIlIl.fade > 0.0F) {
            llllllllllllllllllllllIIlIllIlIl.fade -= 0.4F * (float)llllllllllllllllllllllIIlIllIIlI;
         }

         if (llllllllllllllllllllllIIlIllIlIl.fade < 0.0F) {
            llllllllllllllllllllllIIlIllIlIl.fade = 0.0F;
         }
      }

   }

   public void setVisible(boolean llllllllllllllllllllllIIIlllIlIl) {
      llllllllllllllllllllllIIIlllIlII.visible = llllllllllllllllllllllIIIlllIlIl;
   }

   public Panel(String llllllllllllllllllllllIlIIIlIIII, int llllllllllllllllllllllIlIIIIllll, int llllllllllllllllllllllIlIIIlIlIl, int llllllllllllllllllllllIlIIIIllIl, int llllllllllllllllllllllIlIIIlIIll, boolean llllllllllllllllllllllIlIIIIlIll) {
      llllllllllllllllllllllIlIIIlIIIl.name = llllllllllllllllllllllIlIIIlIIII;
      llllllllllllllllllllllIlIIIlIIIl.elements = new ArrayList();
      llllllllllllllllllllllIlIIIlIIIl.scrollbar = false;
      llllllllllllllllllllllIlIIIlIIIl.x = llllllllllllllllllllllIlIIIIllll;
      llllllllllllllllllllllIlIIIlIIIl.y = llllllllllllllllllllllIlIIIlIlIl;
      llllllllllllllllllllllIlIIIlIIIl.width = llllllllllllllllllllllIlIIIIllIl;
      llllllllllllllllllllllIlIIIlIIIl.height = llllllllllllllllllllllIlIIIlIIll;
      llllllllllllllllllllllIlIIIlIIIl.open = llllllllllllllllllllllIlIIIIlIll;
      llllllllllllllllllllllIlIIIlIIIl.visible = true;
      llllllllllllllllllllllIlIIIlIIIl.setupItems();
   }

   public String getName() {
      return llllllllllllllllllllllIIlIlIlllI.name;
   }

   public void setX(int llllllllllllllllllllllIIlIIlllll) {
      llllllllllllllllllllllIIlIlIIIII.x = llllllllllllllllllllllIIlIIlllll;
   }

   public void mouseClicked(int llllllllllllllllllllllIIllIlllll, int llllllllllllllllllllllIIlllIIIlI, int llllllllllllllllllllllIIllIlllIl) {
      if (llllllllllllllllllllllIIlllIIlII.visible) {
         if (llllllllllllllllllllllIIllIlllIl == 1 && llllllllllllllllllllllIIlllIIlII.isHovering(llllllllllllllllllllllIIllIlllll, llllllllllllllllllllllIIlllIIIlI)) {
            llllllllllllllllllllllIIlllIIlII.open = !llllllllllllllllllllllIIlllIIlII.open;
            mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("random.bow"), 1.0F));
         } else {
            Iterator llllllllllllllllllllllIIllIlllII = llllllllllllllllllllllIIlllIIlII.elements.iterator();

            while(llllllllllllllllllllllIIllIlllII.hasNext()) {
               boolean llllllllllllllllllllllIIllIllIll = (Element)llllllllllllllllllllllIIllIlllII.next();
               if ((float)llllllllllllllllllllllIIllIllIll.getY() <= (float)llllllllllllllllllllllIIlllIIlII.getY() + llllllllllllllllllllllIIlllIIlII.fade) {
                  llllllllllllllllllllllIIllIllIll.mouseClicked(llllllllllllllllllllllIIllIlllll, llllllllllllllllllllllIIlllIIIlI, llllllllllllllllllllllIIllIlllIl);
               }
            }

         }
      }
   }

   public void setOpen(boolean llllllllllllllllllllllIIIlllllII) {
      llllllllllllllllllllllIIIlllllIl.open = llllllllllllllllllllllIIIlllllII;
   }

   public List<Element> getElements() {
      return llllllllllllllllllllllIIIllIllIl.elements;
   }

   public abstract void setupItems();

   public int getX() {
      return llllllllllllllllllllllIIlIlIlIIl.x;
   }

   public boolean handleScroll(int llllllllllllllllllllllIIlIlllllI, int llllllllllllllllllllllIIlIllllIl, int llllllllllllllllllllllIIlIllllII) {
      int llllllllllllllllllllllIIllIIIIII = (Integer)((ClickGUI)Objects.requireNonNull(LiquidBounce.moduleManager.getModule(ClickGUI.class))).maxElementsValue.get();
      if (llllllllllllllllllllllIIlIlllllI >= llllllllllllllllllllllIIllIIIlII.getX() && llllllllllllllllllllllIIlIlllllI <= llllllllllllllllllllllIIllIIIlII.getX() + 100 && llllllllllllllllllllllIIlIllllIl >= llllllllllllllllllllllIIllIIIlII.getY() && (float)llllllllllllllllllllllIIlIllllIl <= (float)(llllllllllllllllllllllIIllIIIlII.getY() + 19) + llllllllllllllllllllllIIllIIIlII.elementsHeight) {
         if (llllllllllllllllllllllIIlIllllII < 0 && llllllllllllllllllllllIIllIIIlII.scroll < llllllllllllllllllllllIIllIIIlII.elements.size() - llllllllllllllllllllllIIllIIIIII) {
            ++llllllllllllllllllllllIIllIIIlII.scroll;
            if (llllllllllllllllllllllIIllIIIlII.scroll < 0) {
               llllllllllllllllllllllIIllIIIlII.scroll = 0;
            }
         } else if (llllllllllllllllllllllIIlIllllII > 0) {
            --llllllllllllllllllllllIIllIIIlII.scroll;
            if (llllllllllllllllllllllIIllIIIlII.scroll < 0) {
               llllllllllllllllllllllIIllIIIlII.scroll = 0;
            }
         }

         if (llllllllllllllllllllllIIlIllllII < 0) {
            if (llllllllllllllllllllllIIllIIIlII.dragged < llllllllllllllllllllllIIllIIIlII.elements.size() - llllllllllllllllllllllIIllIIIIII) {
               ++llllllllllllllllllllllIIllIIIlII.dragged;
            }
         } else if (llllllllllllllllllllllIIlIllllII > 0 && llllllllllllllllllllllIIllIIIlII.dragged >= 1) {
            --llllllllllllllllllllllIIllIIIlII.dragged;
         }

         return true;
      } else {
         return false;
      }
   }

   public int getY() {
      return llllllllllllllllllllllIIlIlIIlIl.y;
   }

   public void setY(int llllllllllllllllllllllIIlIIlIIII) {
      llllllllllllllllllllllIIlIIIllll.y = llllllllllllllllllllllIIlIIlIIII;
   }

   public boolean isVisible() {
      return llllllllllllllllllllllIIIlllIIIl.visible;
   }

   public void drawScreen(int llllllllllllllllllllllIIllllllII, int llllllllllllllllllllllIIllllIIll, float llllllllllllllllllllllIIlllllIlI) {
      if (llllllllllllllllllllllIIllllIlIl.visible) {
         String llllllllllllllllllllllIIllllIIIl = (Integer)((ClickGUI)Objects.requireNonNull(LiquidBounce.moduleManager.getModule(ClickGUI.class))).maxElementsValue.get();
         int llllllllllllllllllllllIIllllIlll;
         if (llllllllllllllllllllllIIllllIlIl.drag) {
            int llllllllllllllllllllllIlIIIIIIII = llllllllllllllllllllllIIllllIlIl.x2 + llllllllllllllllllllllIIllllllII;
            llllllllllllllllllllllIIllllIlll = llllllllllllllllllllllIIllllIlIl.y2 + llllllllllllllllllllllIIllllIIll;
            if (llllllllllllllllllllllIlIIIIIIII > -1) {
               llllllllllllllllllllllIIllllIlIl.x = llllllllllllllllllllllIlIIIIIIII;
            }

            if (llllllllllllllllllllllIIllllIlll > -1) {
               llllllllllllllllllllllIIllllIlIl.y = llllllllllllllllllllllIIllllIlll;
            }
         }

         llllllllllllllllllllllIIllllIlIl.elementsHeight = (float)(llllllllllllllllllllllIIllllIlIl.getElementsHeight() - 1);
         boolean llllllllllllllllllllllIIlllllIII = llllllllllllllllllllllIIllllIlIl.elements.size() >= llllllllllllllllllllllIIllllIIIl;
         if (llllllllllllllllllllllIIllllIlIl.scrollbar != llllllllllllllllllllllIIlllllIII) {
            llllllllllllllllllllllIIllllIlIl.scrollbar = llllllllllllllllllllllIIlllllIII;
         }

         LiquidBounce.clickGui.style.drawPanel(llllllllllllllllllllllIIllllllII, llllllllllllllllllllllIIllllIIll, llllllllllllllllllllllIIllllIlIl);
         llllllllllllllllllllllIIllllIlll = llllllllllllllllllllllIIllllIlIl.y + llllllllllllllllllllllIIllllIlIl.height - 2;
         int llllllllllllllllllllllIIllllIllI = 0;
         Iterator llllllllllllllllllllllIIlllIllIl = llllllllllllllllllllllIIllllIlIl.elements.iterator();

         while(true) {
            while(llllllllllllllllllllllIIlllIllIl.hasNext()) {
               char llllllllllllllllllllllIIlllIllII = (Element)llllllllllllllllllllllIIlllIllIl.next();
               ++llllllllllllllllllllllIIllllIllI;
               if (llllllllllllllllllllllIIllllIllI > llllllllllllllllllllllIIllllIlIl.scroll && llllllllllllllllllllllIIllllIllI < llllllllllllllllllllllIIllllIlIl.scroll + llllllllllllllllllllllIIllllIIIl + 1 && llllllllllllllllllllllIIllllIlIl.scroll < llllllllllllllllllllllIIllllIlIl.elements.size()) {
                  llllllllllllllllllllllIIlllIllII.setLocation(llllllllllllllllllllllIIllllIlIl.x, llllllllllllllllllllllIIllllIlll);
                  llllllllllllllllllllllIIlllIllII.setWidth(llllllllllllllllllllllIIllllIlIl.getWidth());
                  if ((float)llllllllllllllllllllllIIllllIlll <= (float)llllllllllllllllllllllIIllllIlIl.getY() + llllllllllllllllllllllIIllllIlIl.fade) {
                     llllllllllllllllllllllIIlllIllII.drawScreen(llllllllllllllllllllllIIllllllII, llllllllllllllllllllllIIllllIIll, llllllllllllllllllllllIIlllllIlI);
                  }

                  llllllllllllllllllllllIIllllIlll += llllllllllllllllllllllIIlllIllII.getHeight() + 1;
                  llllllllllllllllllllllIIlllIllII.setVisible(true);
               } else {
                  llllllllllllllllllllllIIlllIllII.setVisible(false);
               }
            }

            return;
         }
      }
   }

   public boolean getScrollbar() {
      return llllllllllllllllllllllIIlIIIIIlI.scrollbar;
   }

   public boolean getOpen() {
      return llllllllllllllllllllllIIIllllIIl.open;
   }

   boolean isHovering(int llllllllllllllllllllllIIIIlllIIl, int llllllllllllllllllllllIIIIlllIII) {
      byte llllllllllllllllllllllIIIIllIlll = (float)mc.fontRendererObj.getStringWidth(StringUtils.stripControlCodes(llllllllllllllllllllllIIIIlllIlI.name)) - 100.0F;
      return (float)llllllllllllllllllllllIIIIlllIIl >= (float)llllllllllllllllllllllIIIIlllIlI.x - llllllllllllllllllllllIIIIllIlll / 2.0F - 19.0F && (float)llllllllllllllllllllllIIIIlllIIl <= (float)llllllllllllllllllllllIIIIlllIlI.x - llllllllllllllllllllllIIIIllIlll / 2.0F + (float)mc.fontRendererObj.getStringWidth(StringUtils.stripControlCodes(llllllllllllllllllllllIIIIlllIlI.name)) + 19.0F && llllllllllllllllllllllIIIIlllIII >= llllllllllllllllllllllIIIIlllIlI.y && llllllllllllllllllllllIIIIlllIII <= llllllllllllllllllllllIIIIlllIlI.y + llllllllllllllllllllllIIIIlllIlI.height - (llllllllllllllllllllllIIIIlllIlI.open ? 2 : 0);
   }

   private int getElementsHeight() {
      int llllllllllllllllllllllIIIlIlIlII = 0;
      String llllllllllllllllllllllIIIlIlIIlI = 0;
      Iterator llllllllllllllllllllllIIIlIlIIII = llllllllllllllllllllllIIIlIlIllI.elements.iterator();

      while(llllllllllllllllllllllIIIlIlIIII.hasNext()) {
         float llllllllllllllllllllllIIIlIIlllI = (Element)llllllllllllllllllllllIIIlIlIIII.next();
         if (llllllllllllllllllllllIIIlIlIIlI < (Integer)((ClickGUI)Objects.requireNonNull(LiquidBounce.moduleManager.getModule(ClickGUI.class))).maxElementsValue.get()) {
            llllllllllllllllllllllIIIlIlIlII += llllllllllllllllllllllIIIlIIlllI.getHeight() + 1;
            ++llllllllllllllllllllllIIIlIlIIlI;
         }
      }

      return llllllllllllllllllllllIIIlIlIlII;
   }

   public int getWidth() {
      return llllllllllllllllllllllIIlIIIlIll.width;
   }

   public int getFade() {
      return (int)llllllllllllllllllllllIIIllIlIll.fade;
   }

   public int getDragged() {
      return llllllllllllllllllllllIIIllIIlll.dragged;
   }
}
