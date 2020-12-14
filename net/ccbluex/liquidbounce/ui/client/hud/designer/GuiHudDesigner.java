//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.ui.client.hud.designer;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.ui.client.hud.HUD;
import net.ccbluex.liquidbounce.ui.client.hud.element.Element;
import net.minecraft.client.gui.GuiScreen;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\f\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J \u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u000eH\u0016J\u0018\u0010\u0015\u001a\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0010H\u0014J \u0010\u0019\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u001a\u001a\u00020\u0010H\u0014J \u0010\u001b\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u001c\u001a\u00020\u0010H\u0014J\b\u0010\u001d\u001a\u00020\u000eH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\f¨\u0006\u001e"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/designer/GuiHudDesigner;", "Lnet/minecraft/client/gui/GuiScreen;", "()V", "buttonAction", "", "editorPanel", "Lnet/ccbluex/liquidbounce/ui/client/hud/designer/EditorPanel;", "selectedElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "getSelectedElement", "()Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "setSelectedElement", "(Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;)V", "drawScreen", "", "mouseX", "", "mouseY", "partialTicks", "", "initGui", "keyTyped", "typedChar", "", "keyCode", "mouseClicked", "mouseButton", "mouseReleased", "state", "onGuiClosed", "LiquidSense"}
)
public final class GuiHudDesigner extends GuiScreen {
   // $FF: synthetic field
   @Nullable
   private Element selectedElement;
   // $FF: synthetic field
   private EditorPanel editorPanel;
   // $FF: synthetic field
   private boolean buttonAction;

   public void initGui() {
      Keyboard.enableRepeatEvents(true);
      lIlIllIlllIllll.editorPanel = new EditorPanel(lIlIllIlllIllll, lIlIllIlllIllll.width / 2, lIlIllIlllIllll.height / 2);
      super.initGui();
   }

   public void onGuiClosed() {
      Keyboard.enableRepeatEvents(false);
      LiquidBounce.INSTANCE.getFileManager().saveConfig(LiquidBounce.INSTANCE.getFileManager().hudConfig);
      super.onGuiClosed();
   }

   protected void mouseReleased(int lIlIllIIllIlIll, int lIlIllIIllIlIIl, int lIlIllIIllIlIII) {
      super.mouseReleased(lIlIllIIllIlIll, lIlIllIIllIlIIl, lIlIllIIllIlIII);
      LiquidBounce.INSTANCE.getHud().handleMouseReleased();
   }

   @Nullable
   public final Element getSelectedElement() {
      return lIlIllIllllllIl.selectedElement;
   }

   public final void setSelectedElement(@Nullable Element lIlIllIllllIIlI) {
      lIlIllIllllIlll.selectedElement = lIlIllIllllIIlI;
   }

   public void drawScreen(int lIlIllIllIlIIll, int lIlIllIllIlIIlI, float lIlIllIllIlIlIl) {
      LiquidBounce.INSTANCE.getHud().render(true);
      LiquidBounce.INSTANCE.getHud().handleMouseMove(lIlIllIllIlIIll, lIlIllIllIlIIlI);
      if (!CollectionsKt.contains((Iterable)LiquidBounce.INSTANCE.getHud().getElements(), lIlIllIllIllIII.selectedElement)) {
         lIlIllIllIllIII.selectedElement = (Element)null;
      }

      long lIlIllIllIlIIII = Mouse.getDWheel();
      lIlIllIllIllIII.editorPanel.drawPanel(lIlIllIllIlIIll, lIlIllIllIlIIlI, lIlIllIllIlIIII);
      if (lIlIllIllIlIIII != 0) {
         Iterator lIlIllIllIIlllI = LiquidBounce.INSTANCE.getHud().getElements().iterator();

         while(lIlIllIllIIlllI.hasNext()) {
            float lIlIllIllIIllll = (Element)lIlIllIllIIlllI.next();
            if (lIlIllIllIIllll.isInBorder((double)((float)lIlIllIllIlIIll / lIlIllIllIIllll.getScale()) - lIlIllIllIIllll.getRenderX(), (double)((float)lIlIllIllIlIIlI / lIlIllIllIIllll.getScale()) - lIlIllIllIIllll.getRenderY())) {
               lIlIllIllIIllll.setScale(lIlIllIllIIllll.getScale() + (lIlIllIllIlIIII > 0 ? 0.05F : -0.05F));
               break;
            }
         }
      }

      super.drawScreen(lIlIllIllIlIIll, lIlIllIllIlIIlI, lIlIllIllIlIlIl);
   }

   protected void mouseClicked(int lIlIllIlIlIIIIl, int lIlIllIlIlIIIII, int lIlIllIlIIlllll) {
      super.mouseClicked(lIlIllIlIlIIIIl, lIlIllIlIlIIIII, lIlIllIlIIlllll);
      if (lIlIllIlIIllllI.buttonAction) {
         lIlIllIlIIllllI.buttonAction = false;
      } else {
         label32: {
            LiquidBounce.INSTANCE.getHud().handleMouseClick(lIlIllIlIlIIIIl, lIlIllIlIlIIIII, lIlIllIlIIlllll);
            if (lIlIllIlIlIIIIl >= lIlIllIlIIllllI.editorPanel.getX() && lIlIllIlIlIIIIl <= lIlIllIlIIllllI.editorPanel.getX() + lIlIllIlIIllllI.editorPanel.getWidth() && lIlIllIlIlIIIII >= lIlIllIlIIllllI.editorPanel.getY()) {
               int var10001 = lIlIllIlIIllllI.editorPanel.getY();
               String lIlIllIlIIlIlll = lIlIllIlIIllllI.editorPanel.getRealHeight();
               char lIlIllIlIIlIlIl = 200;
               double lIlIllIlIIIlllI = var10001;
               float lIlIllIlIIlIIll = false;
               double lIlIllIlIIIllII = Math.min(lIlIllIlIIlIlll, lIlIllIlIIlIlIl);
               if (lIlIllIlIlIIIII <= lIlIllIlIIIlllI + lIlIllIlIIIllII) {
                  break label32;
               }
            }

            lIlIllIlIIllllI.selectedElement = (Element)null;
            lIlIllIlIIllllI.editorPanel.setCreate(false);
         }

         if (lIlIllIlIIlllll == 0) {
            Iterator lIlIllIlIIlIlIl = LiquidBounce.INSTANCE.getHud().getElements().iterator();

            while(lIlIllIlIIlIlIl.hasNext()) {
               String lIlIllIlIIlIlll = (Element)lIlIllIlIIlIlIl.next();
               if (lIlIllIlIIlIlll.isInBorder((double)((float)lIlIllIlIlIIIIl / lIlIllIlIIlIlll.getScale()) - lIlIllIlIIlIlll.getRenderX(), (double)((float)lIlIllIlIlIIIII / lIlIllIlIIlIlll.getScale()) - lIlIllIlIIlIlll.getRenderY())) {
                  lIlIllIlIIllllI.selectedElement = lIlIllIlIIlIlll;
                  break;
               }
            }
         }

      }
   }

   public GuiHudDesigner() {
      lIlIllIIlIIlllI.editorPanel = new EditorPanel(lIlIllIIlIIlllI, 2, 2);
   }

   protected void keyTyped(char lIlIllIIlIlIIlI, int lIlIllIIlIlIIIl) {
      switch(lIlIllIIlIlIIIl) {
      case 1:
         lIlIllIIlIlIllI.selectedElement = (Element)null;
         lIlIllIIlIlIllI.editorPanel.setCreate(false);
         break;
      case 211:
         if (211 == lIlIllIIlIlIIIl && lIlIllIIlIlIllI.selectedElement != null) {
            HUD var10000 = LiquidBounce.INSTANCE.getHud();
            Element var10001 = lIlIllIIlIlIllI.selectedElement;
            if (var10001 == null) {
               Intrinsics.throwNpe();
            }

            var10000.removeElement(var10001);
            boolean var4 = false;
         }
         break;
      default:
         LiquidBounce.INSTANCE.getHud().handleKey(lIlIllIIlIlIIlI, lIlIllIIlIlIIIl);
      }

      super.keyTyped(lIlIllIIlIlIIlI, lIlIllIIlIlIIIl);
   }
}
