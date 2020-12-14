//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.ui.client.hud.designer;

import java.awt.Color;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.render.ClickGUI;
import net.ccbluex.liquidbounce.ui.client.hud.HUD;
import net.ccbluex.liquidbounce.ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.ui.font.GameFontRenderer;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\u0018\u0010\u0010\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u00052\u0006\u0010$\u001a\u00020\u0005H\u0002J\u0018\u0010%\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u00052\u0006\u0010$\u001a\u00020\u0005H\u0002J\u0018\u0010&\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u00052\u0006\u0010$\u001a\u00020\u0005H\u0002J\u001e\u0010'\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u00052\u0006\u0010$\u001a\u00020\u00052\u0006\u0010(\u001a\u00020\u0005J\u0018\u0010)\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u00052\u0006\u0010$\u001a\u00020\u0005H\u0002R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\u0014\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u0005@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\u0018\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u0005@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0016R\u000e\u0010\u001a\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\u001b\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u0005@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0016R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u0016\"\u0004\b\u001e\u0010\u001fR\u001a\u0010\u0006\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u0016\"\u0004\b!\u0010\u001f¨\u0006*"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/designer/EditorPanel;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "hudDesigner", "Lnet/ccbluex/liquidbounce/ui/client/hud/designer/GuiHudDesigner;", "x", "", "y", "(Lnet/ccbluex/liquidbounce/ui/client/hud/designer/GuiHudDesigner;II)V", "create", "", "getCreate", "()Z", "setCreate", "(Z)V", "currentElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "drag", "dragX", "dragY", "<set-?>", "height", "getHeight", "()I", "mouseDown", "realHeight", "getRealHeight", "scroll", "width", "getWidth", "getX", "setX", "(I)V", "getY", "setY", "", "mouseX", "mouseY", "drawCreate", "drawEditor", "drawPanel", "wheel", "drawSelection", "LiquidSense"}
)
public final class EditorPanel extends MinecraftInstance {
   // $FF: synthetic field
   private int height;
   // $FF: synthetic field
   private int scroll;
   // $FF: synthetic field
   private Element currentElement;
   // $FF: synthetic field
   private int x;
   // $FF: synthetic field
   private boolean mouseDown;
   // $FF: synthetic field
   private int dragX;
   // $FF: synthetic field
   private int width;
   // $FF: synthetic field
   private final GuiHudDesigner hudDesigner;
   // $FF: synthetic field
   private boolean create;
   // $FF: synthetic field
   private int dragY;
   // $FF: synthetic field
   private boolean drag;
   // $FF: synthetic field
   private int y;
   // $FF: synthetic field
   private int realHeight;

   public final int getY() {
      return lIllllIIIllIlII.y;
   }

   public final void setX(int lIllllIIIllIllI) {
      lIllllIIIlllIIl.x = lIllllIIIllIllI;
   }

   public final int getWidth() {
      return lIlllllIIIIIlll.width;
   }

   private final void drag(int lIllllIIlIIIIll, int lIllllIIlIIIIlI) {
      if (lIllllIIlIIIIll >= lIllllIIlIIIIIl.x && lIllllIIlIIIIll <= lIllllIIlIIIIIl.x + lIllllIIlIIIIIl.width && lIllllIIlIIIIlI >= lIllllIIlIIIIIl.y && lIllllIIlIIIIlI <= lIllllIIlIIIIIl.y + 12 && Mouse.isButtonDown(0) && !lIllllIIlIIIIIl.mouseDown) {
         lIllllIIlIIIIIl.drag = true;
         lIllllIIlIIIIIl.dragX = lIllllIIlIIIIll - lIllllIIlIIIIIl.x;
         lIllllIIlIIIIIl.dragY = lIllllIIlIIIIlI - lIllllIIlIIIIIl.y;
      }

      if (Mouse.isButtonDown(0) && lIllllIIlIIIIIl.drag) {
         lIllllIIlIIIIIl.x = lIllllIIlIIIIll - lIllllIIlIIIIIl.dragX;
         lIllllIIlIIIIIl.y = lIllllIIlIIIIlI - lIllllIIlIIIIIl.dragY;
      } else {
         lIllllIIlIIIIIl.drag = false;
      }

   }

   public final int getHeight() {
      return lIlllllIIIIIlII.height;
   }

   public final void drawPanel(int lIllllIlllIlIll, int lIllllIlllIlIlI, int lIllllIlllIIlIl) {
      lIllllIlllIlIII.drag(lIllllIlllIlIll, lIllllIlllIlIlI);
      if (Intrinsics.areEqual((Object)lIllllIlllIlIII.currentElement, (Object)lIllllIlllIlIII.hudDesigner.getSelectedElement()) ^ true) {
         lIllllIlllIlIII.scroll = 0;
      }

      lIllllIlllIlIII.currentElement = lIllllIlllIlIII.hudDesigner.getSelectedElement();
      Exception lIllllIlllIIlII = lIllllIlllIlIlI;
      boolean lIllllIlllIlllI = lIllllIlllIlIII.realHeight > 200;
      if (lIllllIlllIlllI) {
         GL11.glPushMatrix();
         RenderUtils.makeScissorBox((float)lIllllIlllIlIII.x, (float)lIllllIlllIlIII.y + 1.0F, (float)lIllllIlllIlIII.x + (float)lIllllIlllIlIII.width, (float)lIllllIlllIlIII.y + 200.0F);
         GL11.glEnable(3089);
         if (lIllllIlllIlIII.y + 200 < lIllllIlllIlIlI) {
            lIllllIlllIIlII = -1;
         }

         if (lIllllIlllIlIll >= lIllllIlllIlIII.x && lIllllIlllIlIll <= lIllllIlllIlIII.x + lIllllIlllIlIII.width && lIllllIlllIIlII >= lIllllIlllIlIII.y && lIllllIlllIIlII <= lIllllIlllIlIII.y + 200 && Mouse.hasWheel()) {
            if (lIllllIlllIIlIl < 0 && -lIllllIlllIlIII.scroll + 205 <= lIllllIlllIlIII.realHeight) {
               lIllllIlllIlIII.scroll -= 12;
            } else if (lIllllIlllIIlIl > 0) {
               lIllllIlllIlIII.scroll += 12;
               if (lIllllIlllIlIII.scroll > 0) {
                  lIllllIlllIlIII.scroll = 0;
               }
            }
         }
      }

      Gui.drawRect(lIllllIlllIlIII.x, lIllllIlllIlIII.y + 12, lIllllIlllIlIII.x + lIllllIlllIlIII.width, lIllllIlllIlIII.y + lIllllIlllIlIII.realHeight, (new Color(27, 34, 40)).getRGB());
      if (lIllllIlllIlIII.create) {
         lIllllIlllIlIII.drawCreate(lIllllIlllIlIll, lIllllIlllIIlII);
      } else if (lIllllIlllIlIII.currentElement != null) {
         lIllllIlllIlIII.drawEditor(lIllllIlllIlIll, lIllllIlllIIlII);
      } else {
         lIllllIlllIlIII.drawSelection(lIllllIlllIlIll, lIllllIlllIIlII);
      }

      if (lIllllIlllIlllI) {
         Gui.drawRect(lIllllIlllIlIII.x + lIllllIlllIlIII.width - 5, lIllllIlllIlIII.y + 15, lIllllIlllIlIII.x + lIllllIlllIlIII.width - 2, lIllllIlllIlIII.y + 197, (new Color(41, 41, 41)).getRGB());
         Exception lIllllIlllIIIlI = (float)197 * ((float)(-lIllllIlllIlIII.scroll) / ((float)lIllllIlllIlIII.realHeight - 170.0F));
         RenderUtils.drawRect((float)(lIllllIlllIlIII.x + lIllllIlllIlIII.width) - 5.0F, (float)(lIllllIlllIlIII.y + 15) + lIllllIlllIIIlI, (float)(lIllllIlllIlIII.x + lIllllIlllIlIII.width) - 2.0F, (float)(lIllllIlllIlIII.y + 20) + lIllllIlllIIIlI, (new Color(37, 126, 255)).getRGB());
         GL11.glDisable(3089);
         GL11.glPopMatrix();
      }

      lIllllIlllIlIII.mouseDown = Mouse.isButtonDown(0);
   }

   private final void drawEditor(int lIllllIIllIIIll, int lIllllIIllIIIlI) {
      // $FF: Couldn't be decompiled
   }

   public final void setCreate(boolean lIllllIlllllIIl) {
      lIllllIlllllIlI.create = lIllllIlllllIIl;
   }

   private final void drawCreate(int lIllllIllIIIIll, int lIllllIllIIIlIl) {
      lIllllIllIIIlll.height = 15 + lIllllIllIIIlll.scroll;
      lIllllIllIIIlll.realHeight = 15;
      lIllllIllIIIlll.width = 90;
      char lIllllIlIllllll = HUD.Companion.getElements();
      long lIllllIlIlllllI = lIllllIlIllllll.length;

      boolean var10001;
      int var10002;
      int var10003;
      GameFontRenderer var20;
      Color var10004;
      for(int lIllllIllIIIIII = 0; lIllllIllIIIIII < lIllllIlIlllllI; ++lIllllIllIIIIII) {
         byte lIllllIllIIIIIl = lIllllIlIllllll[lIllllIllIIIIII];
         ElementInfo var10000 = (ElementInfo)lIllllIllIIIIIl.getAnnotation(ElementInfo.class);
         if (var10000 == null) {
            var10001 = false;
         } else {
            ElementInfo lIllllIllIIlIIl = var10000;
            if (lIllllIllIIlIIl.single()) {
               Iterable lIllllIllIlIIII = (Iterable)LiquidBounce.INSTANCE.getHud().getElements();
               int lIllllIllIIllll = false;
               boolean var19;
               if (lIllllIllIlIIII instanceof Collection && ((Collection)lIllllIllIlIIII).isEmpty()) {
                  var19 = false;
               } else {
                  Iterator lIllllIlIlllIlI = lIllllIllIlIIII.iterator();

                  while(true) {
                     if (!lIllllIlIlllIlI.hasNext()) {
                        var19 = false;
                        break;
                     }

                     Object lIllllIllIlIIIl = lIllllIlIlllIlI.next();
                     double lIllllIlIlllIII = (Element)lIllllIllIlIIIl;
                     char lIllllIlIllIlll = false;
                     if (Intrinsics.areEqual((Object)lIllllIlIlllIII.getClass(), (Object)lIllllIllIIIIIl)) {
                        var19 = true;
                        break;
                     }
                  }
               }

               if (var19) {
                  continue;
               }
            }

            String lIllllIllIIlIlI = lIllllIllIIlIIl.name();
            var20 = Fonts.font35;
            var10002 = lIllllIllIIIlll.x + 2;
            var10003 = lIllllIllIIIlll.y + lIllllIllIIIlll.height;
            var10004 = Color.WHITE;
            Intrinsics.checkExpressionValueIsNotNull(var10004, "Color.WHITE");
            var20.drawString(lIllllIllIIlIlI, var10002, var10003, var10004.getRGB());
            var10001 = false;
            int lIllllIllIIlIll = Fonts.font35.getStringWidth(lIllllIllIIlIlI);
            if (lIllllIllIIIlll.width < lIllllIllIIlIll + 8) {
               lIllllIllIIIlll.width = lIllllIllIIlIll + 8;
            }

            if (Mouse.isButtonDown(0) && !lIllllIllIIIlll.mouseDown && lIllllIllIIIIll >= lIllllIllIIIlll.x && lIllllIllIIIIll <= lIllllIllIIIlll.x + lIllllIllIIIlll.width && lIllllIllIIIlIl >= lIllllIllIIIlll.y + lIllllIllIIIlll.height && lIllllIllIIIlIl <= lIllllIllIIIlll.y + lIllllIllIIIlll.height + 10) {
               try {
                  Element lIllllIllIIlllI = (Element)lIllllIllIIIIIl.newInstance();
                  if (lIllllIllIIlllI.createElement()) {
                     HUD var22 = LiquidBounce.INSTANCE.getHud();
                     Intrinsics.checkExpressionValueIsNotNull(lIllllIllIIlllI, "newElement");
                     var22.addElement(lIllllIllIIlllI);
                     var10001 = false;
                  }
               } catch (InstantiationException var14) {
                  var14.printStackTrace();
               } catch (IllegalAccessException var15) {
                  var15.printStackTrace();
               }

               lIllllIllIIIlll.create = false;
            }

            lIllllIllIIIlll.height += 10;
            lIllllIllIIIlll.realHeight += 10;
         }
      }

      int var23 = lIllllIllIIIlll.x;
      int var21 = lIllllIllIIIlll.y;
      var10002 = lIllllIllIIIlll.x + lIllllIllIIIlll.width;
      var10003 = lIllllIllIIIlll.y + 12;
      var10004 = ClickGUI.generateColor();
      Intrinsics.checkExpressionValueIsNotNull(var10004, "ClickGUI.generateColor()");
      Gui.drawRect(var23, var21, var10002, var10003, var10004.getRGB());
      var20 = Fonts.font35;
      float var24 = (float)lIllllIllIIIlll.x + 2.0F;
      float var25 = (float)lIllllIllIIIlll.y + 3.5F;
      var10004 = Color.WHITE;
      Intrinsics.checkExpressionValueIsNotNull(var10004, "Color.WHITE");
      var20.drawString("§lCreate element", var24, var25, var10004.getRGB());
      var10001 = false;
   }

   public final int getRealHeight() {
      return lIlllllIIIIIIII.realHeight;
   }

   public EditorPanel(@NotNull GuiHudDesigner lIllllIIIlIIlll, int lIllllIIIlIIllI, int lIllllIIIlIIlIl) {
      Intrinsics.checkParameterIsNotNull(lIllllIIIlIIlll, "hudDesigner");
      super();
      lIllllIIIlIIlII.hudDesigner = lIllllIIIlIIlll;
      lIllllIIIlIIlII.x = lIllllIIIlIIllI;
      lIllllIIIlIIlII.y = lIllllIIIlIIlIl;
      lIllllIIIlIIlII.width = 80;
      lIllllIIIlIIlII.height = 20;
      lIllllIIIlIIlII.realHeight = 20;
   }

   public final boolean getCreate() {
      return lIllllIllllllIl.create;
   }

   public final void setY(int lIllllIIIlIllll) {
      lIllllIIIlIlllI.y = lIllllIIIlIllll;
   }

   private final void drawSelection(int lIllllIlIlIllIl, int lIllllIlIlIllII) {
      lIllllIlIlIlllI.height = 15 + lIllllIlIlIlllI.scroll;
      lIllllIlIlIlllI.realHeight = 15;
      lIllllIlIlIlllI.width = 120;
      GameFontRenderer var10000 = Fonts.font35;
      int var10002 = lIllllIlIlIlllI.x + 2;
      int var10003 = lIllllIlIlIlllI.y + lIllllIlIlIlllI.height;
      Color var10004 = Color.WHITE;
      Intrinsics.checkExpressionValueIsNotNull(var10004, "Color.WHITE");
      var10000.drawString("§lCreate element", var10002, var10003, var10004.getRGB());
      boolean var10001 = false;
      if (Mouse.isButtonDown(0) && !lIllllIlIlIlllI.mouseDown && lIllllIlIlIllIl >= lIllllIlIlIlllI.x && lIllllIlIlIllIl <= lIllllIlIlIlllI.x + lIllllIlIlIlllI.width && lIllllIlIlIllII >= lIllllIlIlIlllI.y + lIllllIlIlIlllI.height && lIllllIlIlIllII <= lIllllIlIlIlllI.y + lIllllIlIlIlllI.height + 10) {
         lIllllIlIlIlllI.create = true;
      }

      lIllllIlIlIlllI.height += 10;
      lIllllIlIlIlllI.realHeight += 10;
      var10000 = Fonts.font35;
      var10002 = lIllllIlIlIlllI.x + 2;
      var10003 = lIllllIlIlIlllI.y + lIllllIlIlIlllI.height;
      var10004 = Color.WHITE;
      Intrinsics.checkExpressionValueIsNotNull(var10004, "Color.WHITE");
      var10000.drawString("§lReset", var10002, var10003, var10004.getRGB());
      var10001 = false;
      if (Mouse.isButtonDown(0) && !lIllllIlIlIlllI.mouseDown && lIllllIlIlIllIl >= lIllllIlIlIlllI.x && lIllllIlIlIllIl <= lIllllIlIlIlllI.x + lIllllIlIlIlllI.width && lIllllIlIlIllII >= lIllllIlIlIlllI.y + lIllllIlIlIlllI.height && lIllllIlIlIllII <= lIllllIlIlIlllI.y + lIllllIlIlIlllI.height + 10) {
         LiquidBounce.INSTANCE.setHud(HUD.Companion.createDefault());
      }

      lIllllIlIlIlllI.height += 15;
      lIllllIlIlIlllI.realHeight += 15;
      var10000 = Fonts.font35;
      var10002 = lIllllIlIlIlllI.x + 2;
      var10003 = lIllllIlIlIlllI.y + lIllllIlIlIlllI.height;
      var10004 = Color.WHITE;
      Intrinsics.checkExpressionValueIsNotNull(var10004, "Color.WHITE");
      var10000.drawString("§lAvailable Elements", var10002, var10003, var10004.getRGB());
      var10001 = false;
      lIllllIlIlIlllI.height += 10;
      lIllllIlIlIlllI.realHeight += 10;

      for(Iterator lIllllIlIlIIlll = LiquidBounce.INSTANCE.getHud().getElements().iterator(); lIllllIlIlIIlll.hasNext(); lIllllIlIlIlllI.realHeight += 10) {
         Element lIllllIlIlIllll = (Element)lIllllIlIlIIlll.next();
         var10000 = Fonts.font35;
         String var8 = lIllllIlIlIllll.getName();
         var10002 = lIllllIlIlIlllI.x + 2;
         var10003 = lIllllIlIlIlllI.y + lIllllIlIlIlllI.height;
         var10004 = Color.WHITE;
         Intrinsics.checkExpressionValueIsNotNull(var10004, "Color.WHITE");
         var10000.drawString(var8, var10002, var10003, var10004.getRGB());
         var10001 = false;
         int lIllllIlIllIIII = Fonts.font35.getStringWidth(lIllllIlIlIllll.getName());
         if (lIllllIlIlIlllI.width < lIllllIlIllIIII + 8) {
            lIllllIlIlIlllI.width = lIllllIlIllIIII + 8;
         }

         if (Mouse.isButtonDown(0) && !lIllllIlIlIlllI.mouseDown && lIllllIlIlIllIl >= lIllllIlIlIlllI.x && lIllllIlIlIllIl <= lIllllIlIlIlllI.x + lIllllIlIlIlllI.width && lIllllIlIlIllII >= lIllllIlIlIlllI.y + lIllllIlIlIlllI.height && lIllllIlIlIllII <= lIllllIlIlIlllI.y + lIllllIlIlIlllI.height + 10) {
            lIllllIlIlIlllI.hudDesigner.setSelectedElement(lIllllIlIlIllll);
         }

         lIllllIlIlIlllI.height += 10;
      }

      int var7 = lIllllIlIlIlllI.x;
      int var9 = lIllllIlIlIlllI.y;
      var10002 = lIllllIlIlIlllI.x + lIllllIlIlIlllI.width;
      var10003 = lIllllIlIlIlllI.y + 12;
      var10004 = ClickGUI.generateColor();
      Intrinsics.checkExpressionValueIsNotNull(var10004, "ClickGUI.generateColor()");
      Gui.drawRect(var7, var9, var10002, var10003, var10004.getRGB());
      var10000 = Fonts.font35;
      float var10 = (float)lIllllIlIlIlllI.x + 2.0F;
      float var11 = (float)lIllllIlIlIlllI.y + 3.5F;
      var10004 = Color.WHITE;
      Intrinsics.checkExpressionValueIsNotNull(var10004, "Color.WHITE");
      var10000.drawString("§lEditor", var10, var11, var10004.getRGB());
      var10001 = false;
   }

   public final int getX() {
      return lIllllIIIllllII.x;
   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }
}
