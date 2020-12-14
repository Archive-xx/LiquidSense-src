//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.ui.client.hud.element.elements;

import java.awt.Color;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.ui.font.AWTFontRenderer;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.ui.font.GameFontRenderer;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.shader.shaders.RainbowShader;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.FontValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\f\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001:\u0002:;B\u0019\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\n\u0010/\u001a\u0004\u0018\u000100H\u0016J\u0018\u00101\u001a\u0002022\u0006\u00103\u001a\u0002042\u0006\u00105\u001a\u00020#H\u0016J\u0010\u00106\u001a\u0002022\u0006\u00107\u001a\u000208H\u0002J\b\u00109\u001a\u000202H\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020#X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020#X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u0018\u0010'\u001a\f\u0012\b\u0012\u00060)R\u00020\u00000(X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010.\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006<"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/TabGUI;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "x", "", "y", "(DD)V", "alphaValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "arrowsValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "backgroundAlphaValue", "backgroundBlueValue", "backgroundGreenValue", "backgroundRedValue", "blueValue", "borderAlphaValue", "borderBlueValue", "borderGreenValue", "borderRainbow", "borderRedValue", "borderStrength", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "borderValue", "categoryMenu", "", "fontValue", "Lnet/ccbluex/liquidbounce/value/FontValue;", "greenValue", "itemY", "", "rainbowX", "rainbowY", "rectangleRainbow", "redValue", "selectedCategory", "", "selectedModule", "tabHeight", "tabY", "tabs", "", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/TabGUI$Tab;", "textFade", "textPositionY", "textShadow", "upperCaseValue", "width", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "handleKey", "", "c", "", "keyCode", "parseAction", "action", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/TabGUI$Action;", "updateAnimation", "Action", "Tab", "LiquidSense"}
)
@ElementInfo(
   name = "TabGUI"
)
public final class TabGUI extends Element {
   // $FF: synthetic field
   private final FontValue fontValue;
   // $FF: synthetic field
   private final IntegerValue borderGreenValue;
   // $FF: synthetic field
   private final IntegerValue blueValue;
   // $FF: synthetic field
   private final IntegerValue borderRedValue;
   // $FF: synthetic field
   private final IntegerValue greenValue;
   // $FF: synthetic field
   private final BoolValue textFade;
   // $FF: synthetic field
   private final List<TabGUI.Tab> tabs;
   // $FF: synthetic field
   private final IntegerValue backgroundBlueValue;
   // $FF: synthetic field
   private final BoolValue borderValue;
   // $FF: synthetic field
   private final FloatValue tabHeight;
   // $FF: synthetic field
   private final IntegerValue borderAlphaValue;
   // $FF: synthetic field
   private float tabY;
   // $FF: synthetic field
   private final FloatValue textPositionY;
   // $FF: synthetic field
   private float itemY;
   // $FF: synthetic field
   private final BoolValue arrowsValue;
   // $FF: synthetic field
   private final IntegerValue backgroundRedValue;
   // $FF: synthetic field
   private final IntegerValue borderBlueValue;
   // $FF: synthetic field
   private final IntegerValue backgroundGreenValue;
   // $FF: synthetic field
   private final FloatValue borderStrength;
   // $FF: synthetic field
   private final FloatValue rainbowX;
   // $FF: synthetic field
   private final FloatValue width;
   // $FF: synthetic field
   private final BoolValue rectangleRainbow;
   // $FF: synthetic field
   private final BoolValue borderRainbow;
   // $FF: synthetic field
   private final IntegerValue alphaValue;
   // $FF: synthetic field
   private boolean categoryMenu;
   // $FF: synthetic field
   private final FloatValue rainbowY;
   // $FF: synthetic field
   private final IntegerValue backgroundAlphaValue;
   // $FF: synthetic field
   private int selectedCategory;
   // $FF: synthetic field
   private final BoolValue upperCaseValue;
   // $FF: synthetic field
   private int selectedModule;
   // $FF: synthetic field
   private final IntegerValue redValue;
   // $FF: synthetic field
   private final BoolValue textShadow;

   // $FF: synthetic method
   public static final void access$setItemY$p(TabGUI lllIIIlIlIlllII, float lllIIIlIlIllIlI) {
      lllIIIlIlIlllII.itemY = lllIIIlIlIllIlI;
   }

   // $FF: synthetic method
   public TabGUI(double var1, double var3, int lllIIIlIlllIlII, DefaultConstructorMarker var6) {
      if ((lllIIIlIlllIlII & 1) != 0) {
         var1 = 5.0D;
      }

      if ((lllIIIlIlllIlII & 2) != 0) {
         var3 = 25.0D;
      }

      this(var1, var3);
   }

   public TabGUI() {
      this(0.0D, 0.0D, 3, (DefaultConstructorMarker)null);
   }

   private final void updateAnimation() {
      double lllIIlIIIlIIlll = RenderUtils.deltaTime;
      float lllIIlIIIlIlIll = ((Number)lllIIlIIIlIlIIl.tabHeight.get()).floatValue() * (float)lllIIlIIIlIlIIl.selectedCategory;
      if ((int)lllIIlIIIlIlIIl.tabY != (int)lllIIlIIIlIlIll) {
         if (lllIIlIIIlIlIll > lllIIlIIIlIlIIl.tabY) {
            lllIIlIIIlIlIIl.tabY += 0.1F * (float)lllIIlIIIlIIlll;
         } else {
            lllIIlIIIlIlIIl.tabY -= 0.1F * (float)lllIIlIIIlIIlll;
         }
      } else {
         lllIIlIIIlIlIIl.tabY = lllIIlIIIlIlIll;
      }

      String lllIIlIIIlIIlIl = ((Number)lllIIlIIIlIlIIl.tabHeight.get()).floatValue() * (float)lllIIlIIIlIlIIl.selectedModule;
      if ((int)lllIIlIIIlIlIIl.itemY != (int)lllIIlIIIlIIlIl) {
         if (lllIIlIIIlIIlIl > lllIIlIIIlIlIIl.itemY) {
            lllIIlIIIlIlIIl.itemY += 0.1F * (float)lllIIlIIIlIIlll;
         } else {
            lllIIlIIIlIlIIl.itemY -= 0.1F * (float)lllIIlIIIlIIlll;
         }
      } else {
         lllIIlIIIlIlIIl.itemY = lllIIlIIIlIIlIl;
      }

      if (lllIIlIIIlIlIIl.categoryMenu) {
         lllIIlIIIlIlIIl.itemY = 0.0F;
      }

      if ((Boolean)lllIIlIIIlIlIIl.textFade.get()) {
         Iterable lllIIlIIIlIllll = (Iterable)lllIIlIIIlIlIIl.tabs;
         int lllIIlIIIlIlllI = false;
         int lllIIlIIIllIIII = 0;
         Iterator lllIIlIIIlIIIIl = lllIIlIIIlIllll.iterator();

         while(lllIIlIIIlIIIIl.hasNext()) {
            Object lllIIlIIIllIIIl = lllIIlIIIlIIIIl.next();
            Exception lllIIlIIIIlllll = lllIIlIIIllIIII++;
            boolean lllIIlIIIIllllI = false;
            if (lllIIlIIIIlllll < 0) {
               CollectionsKt.throwIndexOverflow();
            }

            TabGUI.Tab lllIIlIIIllIIll = (TabGUI.Tab)lllIIlIIIllIIIl;
            int lllIIlIIIllIIlI = false;
            if (lllIIlIIIIlllll == lllIIlIIIlIlIIl.selectedCategory) {
               if (lllIIlIIIllIIll.getTextFade() < (float)4) {
                  lllIIlIIIllIIll.setTextFade(lllIIlIIIllIIll.getTextFade() + 0.05F * (float)lllIIlIIIlIIlll);
               }

               if (lllIIlIIIllIIll.getTextFade() > (float)4) {
                  lllIIlIIIllIIll.setTextFade(4.0F);
               }
            } else {
               if (lllIIlIIIllIIll.getTextFade() > (float)0) {
                  lllIIlIIIllIIll.setTextFade(lllIIlIIIllIIll.getTextFade() - 0.05F * (float)lllIIlIIIlIIlll);
               }

               if (lllIIlIIIllIIll.getTextFade() < (float)0) {
                  lllIIlIIIllIIll.setTextFade(0.0F);
               }
            }
         }
      } else {
         Iterator lllIIlIIIlIIIll = lllIIlIIIlIlIIl.tabs.iterator();

         while(lllIIlIIIlIIIll.hasNext()) {
            TabGUI.Tab lllIIlIIIlIllIl = (TabGUI.Tab)lllIIlIIIlIIIll.next();
            if (lllIIlIIIlIllIl.getTextFade() > (float)0) {
               lllIIlIIIlIllIl.setTextFade(lllIIlIIIlIllIl.getTextFade() - 0.05F * (float)lllIIlIIIlIIlll);
            }

            if (lllIIlIIIlIllIl.getTextFade() < (float)0) {
               lllIIlIIIlIllIl.setTextFade(0.0F);
            }
         }
      }

   }

   public TabGUI(double lllIIIllIIlIlIl, double lllIIIllIIlIlII) {
      super(lllIIIllIIlIlIl, lllIIIllIIlIlII, 0.0F, (Side)null, 12, (DefaultConstructorMarker)null);
      lllIIIllIIlIllI.rainbowX = new FloatValue("Rainbow-X", -1000.0F, -2000.0F, 2000.0F);
      lllIIIllIIlIllI.rainbowY = new FloatValue("Rainbow-Y", -1000.0F, -2000.0F, 2000.0F);
      lllIIIllIIlIllI.redValue = new IntegerValue("Rectangle Red", 0, 0, 255);
      lllIIIllIIlIllI.greenValue = new IntegerValue("Rectangle Green", 148, 0, 255);
      lllIIIllIIlIllI.blueValue = new IntegerValue("Rectangle Blue", 255, 0, 255);
      lllIIIllIIlIllI.alphaValue = new IntegerValue("Rectangle Alpha", 140, 0, 255);
      lllIIIllIIlIllI.rectangleRainbow = new BoolValue("Rectangle Rainbow", false);
      lllIIIllIIlIllI.backgroundRedValue = new IntegerValue("Background Red", 0, 0, 255);
      lllIIIllIIlIllI.backgroundGreenValue = new IntegerValue("Background Green", 0, 0, 255);
      lllIIIllIIlIllI.backgroundBlueValue = new IntegerValue("Background Blue", 0, 0, 255);
      lllIIIllIIlIllI.backgroundAlphaValue = new IntegerValue("Background Alpha", 150, 0, 255);
      lllIIIllIIlIllI.borderValue = new BoolValue("Border", true);
      lllIIIllIIlIllI.borderStrength = new FloatValue("Border Strength", 2.0F, 1.0F, 5.0F);
      lllIIIllIIlIllI.borderRedValue = new IntegerValue("Border Red", 0, 0, 255);
      lllIIIllIIlIllI.borderGreenValue = new IntegerValue("Border Green", 0, 0, 255);
      lllIIIllIIlIllI.borderBlueValue = new IntegerValue("Border Blue", 0, 0, 255);
      lllIIIllIIlIllI.borderAlphaValue = new IntegerValue("Border Alpha", 150, 0, 255);
      lllIIIllIIlIllI.borderRainbow = new BoolValue("Border Rainbow", false);
      lllIIIllIIlIllI.arrowsValue = new BoolValue("Arrows", true);
      GameFontRenderer var10004 = Fonts.font35;
      Intrinsics.checkExpressionValueIsNotNull(var10004, "Fonts.font35");
      lllIIIllIIlIllI.fontValue = new FontValue("Font", (FontRenderer)var10004);
      lllIIIllIIlIllI.textShadow = new BoolValue("TextShadow", false);
      lllIIIllIIlIllI.textFade = new BoolValue("TextFade", true);
      lllIIIllIIlIllI.textPositionY = new FloatValue("TextPosition-Y", 2.0F, 0.0F, 5.0F);
      lllIIIllIIlIllI.width = new FloatValue("Width", 60.0F, 55.0F, 100.0F);
      lllIIIllIIlIllI.tabHeight = new FloatValue("TabHeight", 12.0F, 10.0F, 15.0F);
      lllIIIllIIlIllI.upperCaseValue = new BoolValue("UpperCase", false);
      short lllIIIllIIlIIII = false;
      long lllIIIlIllllllI = (List)(new ArrayList());
      lllIIIllIIlIllI.tabs = lllIIIlIllllllI;
      lllIIIllIIlIllI.categoryMenu = true;
      Exception lllIIIllIIIlllI = ModuleCategory.values();
      long lllIIIllIIIllIl = lllIIIllIIIlllI.length;

      for(int lllIIIllIIIllll = 0; lllIIIllIIIllll < lllIIIllIIIllIl; ++lllIIIllIIIllll) {
         ModuleCategory lllIIIllIIlIlll = lllIIIllIIIlllI[lllIIIllIIIllll];
         Exception lllIIIllIIIllII = new TabGUI.Tab(lllIIIllIIlIlll.getDisplayName());
         short lllIIIllIIllIlI = (Iterable)LiquidBounce.INSTANCE.getModuleManager().getModules();
         int lllIIIllIIllIIl = false;
         double lllIIIllIIIIlll = (Collection)(new ArrayList());
         int lllIIIllIlIIIII = false;
         Iterator lllIIIllIIIIlIl = lllIIIllIIllIlI.iterator();

         boolean var10001;
         while(lllIIIllIIIIlIl.hasNext()) {
            boolean lllIIIllIIIIlII = lllIIIllIIIIlIl.next();
            boolean lllIIIllIIIIIlI = (Module)lllIIIllIIIIlII;
            int lllIIIllIlIlIII = false;
            if (lllIIIllIIlIlll == lllIIIllIIIIIlI.getCategory()) {
               lllIIIllIIIIlll.add(lllIIIllIIIIlII);
               var10001 = false;
            }
         }

         lllIIIllIIllIlI = (Iterable)((List)lllIIIllIIIIlll);
         lllIIIllIIllIIl = false;

         for(Iterator lllIIIllIIIlIII = lllIIIllIIllIlI.iterator(); lllIIIllIIIlIII.hasNext(); var10001 = false) {
            double lllIIIllIIIIlll = lllIIIllIIIlIII.next();
            long lllIIIllIIIIllI = (Module)lllIIIllIIIIlll;
            String lllIIIllIIIIlIl = false;
            lllIIIllIIIllII.getModules().add(lllIIIllIIIIllI);
         }

         lllIIIllIIlIllI.tabs.add(lllIIIllIIIllII);
         var10001 = false;
      }

   }

   public void handleKey(char lllIIlIIlIlIIll, int lllIIlIIlIlIIIl) {
      switch(lllIIlIIlIlIIIl) {
      case 28:
         lllIIlIIlIlIlII.parseAction(TabGUI.Action.TOGGLE);
         break;
      case 200:
         lllIIlIIlIlIlII.parseAction(TabGUI.Action.UP);
         break;
      case 203:
         lllIIlIIlIlIlII.parseAction(lllIIlIIlIlIlII.getSide().getHorizontal() == Side.Horizontal.RIGHT ? TabGUI.Action.RIGHT : TabGUI.Action.LEFT);
         break;
      case 205:
         lllIIlIIlIlIlII.parseAction(lllIIlIIlIlIlII.getSide().getHorizontal() == Side.Horizontal.RIGHT ? TabGUI.Action.LEFT : TabGUI.Action.RIGHT);
         break;
      case 208:
         lllIIlIIlIlIlII.parseAction(TabGUI.Action.DOWN);
      }

   }

   private final void parseAction(TabGUI.Action lllIIlIIIIIlIII) {
      // $FF: Couldn't be decompiled
   }

   @Nullable
   public Border drawElement() {
      lllIIlIlIIIIIIl.updateAnimation();
      AWTFontRenderer.Companion.setAssumeNonVolatile(true);
      FontRenderer lllIIlIlIIIIIll = (FontRenderer)lllIIlIlIIIIIIl.fontValue.get();
      boolean lllIIlIlIIIIlIl = (Boolean)lllIIlIlIIIIIIl.rectangleRainbow.get();
      long lllIIlIIlllllII = new Color(((Number)lllIIlIlIIIIIIl.backgroundRedValue.get()).intValue(), ((Number)lllIIlIlIIIIIIl.backgroundGreenValue.get()).intValue(), ((Number)lllIIlIlIIIIIIl.backgroundBlueValue.get()).intValue(), ((Number)lllIIlIlIIIIIIl.backgroundAlphaValue.get()).intValue());
      Color lllIIlIlIIIlIII = !(Boolean)lllIIlIlIIIIIIl.borderRainbow.get() ? new Color(((Number)lllIIlIlIIIIIIl.borderRedValue.get()).intValue(), ((Number)lllIIlIlIIIIIIl.borderGreenValue.get()).intValue(), ((Number)lllIIlIlIIIIIIl.borderBlueValue.get()).intValue(), ((Number)lllIIlIlIIIIIIl.borderAlphaValue.get()).intValue()) : Color.black;
      float lllIIlIlIIIlIlI = (float)lllIIlIlIIIIIIl.tabs.size() * ((Number)lllIIlIlIIIIIIl.tabHeight.get()).floatValue();
      RenderUtils.drawRect(1.0F, 0.0F, ((Number)lllIIlIlIIIIIIl.width.get()).floatValue(), lllIIlIlIIIlIlI, lllIIlIIlllllII.getRGB());
      float lllIIlIIlllIlII;
      float lllIIlIlIlIIIlI;
      float lllIIlIIlllIIII;
      boolean lllIIlIIllIlllI;
      RainbowShader lllIIlIIllIllIl;
      if ((Boolean)lllIIlIlIIIIIIl.borderValue.get()) {
         Exception lllIIlIIlllIlll = RainbowShader.Companion;
         int lllIIlIIlllIlIl = (Boolean)lllIIlIlIIIIIIl.borderRainbow.get();
         lllIIlIIlllIlII = ((Number)lllIIlIlIIIIIIl.rainbowX.get()).floatValue() == 0.0F ? 0.0F : 1.0F / ((Number)lllIIlIlIIIIIIl.rainbowX.get()).floatValue();
         lllIIlIlIlIIIlI = ((Number)lllIIlIlIIIIIIl.rainbowY.get()).floatValue() == 0.0F ? 0.0F : 1.0F / ((Number)lllIIlIlIIIIIIl.rainbowY.get()).floatValue();
         lllIIlIIlllIIII = (float)(System.currentTimeMillis() % (long)10000) / 10000.0F;
         lllIIlIIllIlllI = false;
         lllIIlIIllIllIl = RainbowShader.INSTANCE;
         if (lllIIlIIlllIlIl) {
            lllIIlIIllIllIl.setStrengthX(lllIIlIIlllIlII);
            lllIIlIIllIllIl.setStrengthY(lllIIlIlIlIIIlI);
            lllIIlIIllIllIl.setOffset(lllIIlIIlllIIII);
            lllIIlIIllIllIl.startShader();
         }

         Exception lllIIlIIlllIlll = (Closeable)lllIIlIIllIllIl;
         lllIIlIIlllIlIl = false;
         Throwable lllIIlIIlllIlII = (Throwable)null;

         try {
            RainbowShader lllIIlIlIlIIlll = (RainbowShader)lllIIlIIlllIlll;
            byte lllIIlIIlllIIII = false;
            float var10002 = ((Number)lllIIlIlIIIIIIl.width.get()).floatValue();
            float var10004 = ((Number)lllIIlIlIIIIIIl.borderStrength.get()).floatValue();
            Intrinsics.checkExpressionValueIsNotNull(lllIIlIlIIIlIII, "borderColor");
            RenderUtils.drawBorder(1.0F, 0.0F, var10002, lllIIlIlIIIlIlI, var10004, lllIIlIlIIIlIII.getRGB());
            Unit lllIIlIIlllIIlI2 = Unit.INSTANCE;
         } catch (Throwable var35) {
            lllIIlIIlllIlII = var35;
            throw var35;
         } finally {
            CloseableKt.closeFinally(lllIIlIIlllIlll, lllIIlIIlllIlII);
         }
      }

      Exception lllIIlIIlllIlll = !lllIIlIlIIIIlIl ? new Color(((Number)lllIIlIlIIIIIIl.redValue.get()).intValue(), ((Number)lllIIlIlIIIIIIl.greenValue.get()).intValue(), ((Number)lllIIlIlIIIIIIl.blueValue.get()).intValue(), ((Number)lllIIlIlIIIIIIl.alphaValue.get()).intValue()) : Color.black;
      int lllIIlIIlllIlIl = RainbowShader.Companion;
      lllIIlIIlllIlII = ((Number)lllIIlIlIIIIIIl.rainbowX.get()).floatValue() == 0.0F ? 0.0F : 1.0F / ((Number)lllIIlIlIIIIIIl.rainbowX.get()).floatValue();
      lllIIlIlIlIIIlI = ((Number)lllIIlIlIIIIIIl.rainbowY.get()).floatValue() == 0.0F ? 0.0F : 1.0F / ((Number)lllIIlIlIIIIIIl.rainbowY.get()).floatValue();
      lllIIlIIlllIIII = (float)(System.currentTimeMillis() % (long)10000) / 10000.0F;
      lllIIlIIllIlllI = false;
      lllIIlIIllIllIl = RainbowShader.INSTANCE;
      if (lllIIlIlIIIIlIl) {
         lllIIlIIllIllIl.setStrengthX(lllIIlIIlllIlII);
         lllIIlIIllIllIl.setStrengthY(lllIIlIlIlIIIlI);
         lllIIlIIllIllIl.setOffset(lllIIlIIlllIIII);
         lllIIlIIllIllIl.startShader();
      }

      int lllIIlIIlllIlIl = (Closeable)lllIIlIIllIllIl;
      Exception lllIIlIIlllIlII = false;
      Throwable lllIIlIIlllIIlI = (Throwable)null;

      try {
         byte lllIIlIIlllIIII = (RainbowShader)lllIIlIIlllIlIl;
         lllIIlIIllIlllI = false;
         RenderUtils.drawRect(1.0F, (float)1 + lllIIlIlIIIIIIl.tabY - (float)1, ((Number)lllIIlIlIIIIIIl.width.get()).floatValue(), lllIIlIlIIIIIIl.tabY + ((Number)lllIIlIlIIIIIIl.tabHeight.get()).floatValue(), lllIIlIIlllIlll);
         Unit lllIIlIIlllIIII3 = Unit.INSTANCE;
      } catch (Throwable var33) {
         lllIIlIIlllIIlI = var33;
         throw var33;
      } finally {
         CloseableKt.closeFinally(lllIIlIIlllIlIl, lllIIlIIlllIIlI);
      }

      GlStateManager.resetColor();
      int lllIIlIIlllIlIl = 1.0F;
      Exception lllIIlIlIIlIIII = (Iterable)lllIIlIlIIIIIIl.tabs;
      String lllIIlIIlllIIlI = false;
      byte lllIIlIIlllIIII = 0;

      for(Iterator lllIIlIIllIlllI = lllIIlIlIIlIIII.iterator(); lllIIlIIllIlllI.hasNext(); lllIIlIIlllIlIl += ((Number)lllIIlIlIIIIIIl.tabHeight.get()).floatValue()) {
         Object lllIIlIlIIlIIll = lllIIlIIllIlllI.next();
         double lllIIlIIllIlIll = lllIIlIIlllIIII++;
         double lllIIlIIllIlIIl = false;
         if (lllIIlIIllIlIll < 0) {
            CollectionsKt.throwIndexOverflow();
         }

         TabGUI.Tab lllIIlIlIIlIllI = (TabGUI.Tab)lllIIlIlIIlIIll;
         int lllIIlIlIIlIlII = false;
         String var55;
         if ((Boolean)lllIIlIlIIIIIIl.upperCaseValue.get()) {
            boolean lllIIlIIlIlllll = lllIIlIlIIlIllI.getTabName();
            String lllIIlIIlIllllI = false;
            if (lllIIlIIlIlllll == null) {
               throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }

            var55 = lllIIlIIlIlllll.toUpperCase();
            Intrinsics.checkExpressionValueIsNotNull(var55, "(this as java.lang.String).toUpperCase()");
         } else {
            var55 = lllIIlIlIIlIllI.getTabName();
         }

         String lllIIlIlIIllIIl = var55;
         float lllIIlIlIIllIlI = lllIIlIlIIIIIIl.getSide().getHorizontal() == Side.Horizontal.RIGHT ? ((Number)lllIIlIlIIIIIIl.width.get()).floatValue() - (float)lllIIlIlIIIIIll.getStringWidth(lllIIlIlIIllIIl) - lllIIlIlIIlIllI.getTextFade() - (float)3 : lllIIlIlIIlIllI.getTextFade() + (float)5;
         String lllIIlIIlIllllI = lllIIlIIlllIlIl + ((Number)lllIIlIlIIIIIIl.textPositionY.get()).floatValue();
         int lllIIlIlIIlllII = lllIIlIlIIIIIIl.selectedCategory == lllIIlIIllIlIll ? 16777215 : (new Color(210, 210, 210)).getRGB();
         lllIIlIlIIIIIll.drawString(lllIIlIlIIllIIl, lllIIlIlIIllIlI, lllIIlIIlIllllI, lllIIlIlIIlllII, (Boolean)lllIIlIlIIIIIIl.textShadow.get());
         boolean var10001 = false;
         if ((Boolean)lllIIlIlIIIIIIl.arrowsValue.get()) {
            if (lllIIlIlIIIIIIl.getSide().getHorizontal() == Side.Horizontal.RIGHT) {
               lllIIlIlIIIIIll.drawString(!lllIIlIlIIIIIIl.categoryMenu && lllIIlIlIIIIIIl.selectedCategory == lllIIlIIllIlIll ? ">" : "<", 3.0F, lllIIlIIlllIlIl + 2.0F, 16777215, (Boolean)lllIIlIlIIIIIIl.textShadow.get());
               var10001 = false;
            } else {
               lllIIlIlIIIIIll.drawString(!lllIIlIlIIIIIIl.categoryMenu && lllIIlIlIIIIIIl.selectedCategory == lllIIlIIllIlIll ? "<" : ">", ((Number)lllIIlIlIIIIIIl.width.get()).floatValue() - 8.0F, lllIIlIIlllIlIl + 2.0F, 16777215, (Boolean)lllIIlIlIIIIIIl.textShadow.get());
               var10001 = false;
            }
         }

         if (lllIIlIIllIlIll == lllIIlIlIIIIIIl.selectedCategory && !lllIIlIlIIIIIIl.categoryMenu) {
            float lllIIlIlIIlllIl = lllIIlIlIIIIIIl.getSide().getHorizontal() == Side.Horizontal.RIGHT ? 1.0F - (float)lllIIlIlIIlIllI.getMenuWidth() : ((Number)lllIIlIlIIIIIIl.width.get()).floatValue() + (float)5;
            Intrinsics.checkExpressionValueIsNotNull(lllIIlIIlllIlll, "rectColor");
            int var10003 = lllIIlIIlllIlll.getRGB();
            int var58 = lllIIlIIlllllII.getRGB();
            Intrinsics.checkExpressionValueIsNotNull(lllIIlIlIIIlIII, "borderColor");
            lllIIlIlIIlIllI.drawTab(lllIIlIlIIlllIl, lllIIlIIlllIlIl, var10003, var58, lllIIlIlIIIlIII.getRGB(), ((Number)lllIIlIlIIIIIIl.borderStrength.get()).floatValue(), (Boolean)lllIIlIlIIIIIIl.upperCaseValue.get(), lllIIlIlIIIIIll, (Boolean)lllIIlIlIIIIIIl.borderRainbow.get(), lllIIlIlIIIIlIl);
         }
      }

      AWTFontRenderer.Companion.setAssumeNonVolatile(false);
      return new Border(1.0F, 0.0F, ((Number)lllIIlIlIIIIIIl.width.get()).floatValue(), lllIIlIlIIIlIlI);
   }

   @Metadata(
      mv = {1, 1, 16},
      bv = {1, 0, 3},
      k = 1,
      d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0082\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004JV\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00132\u0006\u0010\u001b\u001a\u00020\u00132\u0006\u0010\u001c\u001a\u00020\u00062\u0006\u0010\u001d\u001a\u00020\u00062\u0006\u0010\u001e\u001a\u00020\u00062\u0006\u0010\u001f\u001a\u00020\u00132\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020!2\u0006\u0010%\u001a\u00020!R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017¨\u0006&"},
      d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/TabGUI$Tab;", "", "tabName", "", "(Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/TabGUI;Ljava/lang/String;)V", "menuWidth", "", "getMenuWidth", "()I", "setMenuWidth", "(I)V", "modules", "", "Lnet/ccbluex/liquidbounce/features/module/Module;", "getModules", "()Ljava/util/List;", "getTabName", "()Ljava/lang/String;", "textFade", "", "getTextFade", "()F", "setTextFade", "(F)V", "drawTab", "", "x", "y", "color", "backgroundColor", "borderColor", "borderStrength", "upperCase", "", "fontRenderer", "Lnet/minecraft/client/gui/FontRenderer;", "borderRainbow", "rectRainbow", "LiquidSense"}
   )
   private final class Tab {
      // $FF: synthetic field
      private int menuWidth;
      // $FF: synthetic field
      @NotNull
      private final List<Module> modules;
      // $FF: synthetic field
      private float textFade;
      // $FF: synthetic field
      @NotNull
      private final String tabName;

      @NotNull
      public final List<Module> getModules() {
         return llllllllllllllllllIIIlIlIIlIllll.modules;
      }

      public Tab(@NotNull String llllllllllllllllllIIIlIIIlIIIllI) {
         Intrinsics.checkParameterIsNotNull(llllllllllllllllllIIIlIIIlIIIllI, "tabName");
         super();
         llllllllllllllllllIIIlIIIlIIIlIl.tabName = llllllllllllllllllIIIlIIIlIIIllI;
         String llllllllllllllllllIIIlIIIlIIIIlI = false;
         byte llllllllllllllllllIIIlIIIlIIIIII = (List)(new ArrayList());
         llllllllllllllllllIIIlIIIlIIIlIl.modules = llllllllllllllllllIIIlIIIlIIIIII;
      }

      public final void drawTab(float llllllllllllllllllIIIlIIIllllIII, float llllllllllllllllllIIIlIIlIIIIlIl, int llllllllllllllllllIIIlIIlIIIIIll, int llllllllllllllllllIIIlIIlIIIIIIl, int llllllllllllllllllIIIlIIIlllIlII, float llllllllllllllllllIIIlIIIlllIIll, boolean llllllllllllllllllIIIlIIIlllllIl, @NotNull FontRenderer llllllllllllllllllIIIlIIIlllIIIl, boolean llllllllllllllllllIIIlIIIllllIll, boolean llllllllllllllllllIIIlIIIllIlllI) {
         Intrinsics.checkParameterIsNotNull(llllllllllllllllllIIIlIIIlllIIIl, "fontRenderer");
         int llllllllllllllllllIIIlIIlIIIlIll = 0;
         Iterator llllllllllllllllllIIIlIIIllIlIIlxx = llllllllllllllllllIIIlIIlIIIlIIl.modules.iterator();

         FontRenderer var10000;
         String var10001;
         String var51;
         while(llllllllllllllllllIIIlIIIllIlIIlxx.hasNext()) {
            Module llllllllllllllllllIIIlIIlIllIllI = (Module)llllllllllllllllllIIIlIIIllIlIIlxx.next();
            var10000 = llllllllllllllllllIIIlIIIlllIIIl;
            String llllllllllllllllllIIIlIIIllIlIIIx;
            boolean llllllllllllllllllIIIlIIIllIIlllxxx;
            String llllllllllllllllllIIIlIIIlIlIIlI;
            if (llllllllllllllllllIIIlIIIlllllIl) {
               llllllllllllllllllIIIlIIIllIlIIIx = llllllllllllllllllIIIlIIlIllIllI.getName();
               llllllllllllllllllIIIlIIIllIIlllxxx = false;
               if (llllllllllllllllllIIIlIIIllIlIIIx == null) {
                  throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
               }

               var51 = llllllllllllllllllIIIlIIIllIlIIIx.toUpperCase();
               Intrinsics.checkExpressionValueIsNotNull(var51, "(this as java.lang.String).toUpperCase()");
               llllllllllllllllllIIIlIIIlIlIIlI = var51;
               var10000 = llllllllllllllllllIIIlIIIlllIIIl;
               var10001 = llllllllllllllllllIIIlIIIlIlIIlI;
            } else {
               var10001 = llllllllllllllllllIIIlIIlIllIllI.getName();
            }

            if (var10000.getStringWidth(var10001) + 4 > llllllllllllllllllIIIlIIlIIIlIll) {
               var10000 = llllllllllllllllllIIIlIIIlllIIIl;
               if (llllllllllllllllllIIIlIIIlllllIl) {
                  llllllllllllllllllIIIlIIIllIlIIIx = llllllllllllllllllIIIlIIlIllIllI.getName();
                  llllllllllllllllllIIIlIIIllIIlllxxx = false;
                  if (llllllllllllllllllIIIlIIIllIlIIIx == null) {
                     throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                  }

                  var51 = llllllllllllllllllIIIlIIIllIlIIIx.toUpperCase();
                  Intrinsics.checkExpressionValueIsNotNull(var51, "(this as java.lang.String).toUpperCase()");
                  llllllllllllllllllIIIlIIIlIlIIlI = var51;
                  var10000 = llllllllllllllllllIIIlIIIlllIIIl;
                  var10001 = llllllllllllllllllIIIlIIIlIlIIlI;
               } else {
                  var10001 = llllllllllllllllllIIIlIIlIllIllI.getName();
               }

               llllllllllllllllllIIIlIIlIIIlIll = (int)((float)var10000.getStringWidth(var10001) + 7.0F);
            }
         }

         llllllllllllllllllIIIlIIlIIIlIIl.menuWidth = llllllllllllllllllIIIlIIlIIIlIll;
         float llllllllllllllllllIIIlIIlIIIllIl = (float)llllllllllllllllllIIIlIIlIIIlIIl.modules.size() * ((Number)TabGUI.this.tabHeight.get()).floatValue();
         float llllllllllllllllllIIIlIIlIIlllll;
         boolean llllllllllllllllllIIIlIIlIIlllII;
         RainbowShader llllllllllllllllllIIIlIIlIlIIIll;
         RainbowShader.Companion llllllllllllllllllIIIlIIIllIlIIl;
         Closeable llllllllllllllllllIIIlIIIllIlIIlx;
         float llllllllllllllllllIIIlIIlIlIIIIl;
         boolean llllllllllllllllllIIIlIIIllIlIII;
         float llllllllllllllllllIIIlIIIllIIlll;
         Throwable llllllllllllllllllIIIlIIIllIIlllx;
         RainbowShader llllllllllllllllllIIIlIIlIIlllIl;
         Unit llllllllllllllllllIIIlIIIllIIllI2;
         if ((Boolean)TabGUI.this.borderValue.get()) {
            llllllllllllllllllIIIlIIIllIlIIl = RainbowShader.Companion;
            llllllllllllllllllIIIlIIlIlIIIIl = ((Number)TabGUI.this.rainbowX.get()).floatValue() == 0.0F ? 0.0F : 1.0F / ((Number)TabGUI.this.rainbowX.get()).floatValue();
            llllllllllllllllllIIIlIIIllIIlll = ((Number)TabGUI.this.rainbowY.get()).floatValue() == 0.0F ? 0.0F : 1.0F / ((Number)TabGUI.this.rainbowY.get()).floatValue();
            llllllllllllllllllIIIlIIlIIlllll = (float)(System.currentTimeMillis() % (long)10000) / 10000.0F;
            llllllllllllllllllIIIlIIlIIlllII = false;
            llllllllllllllllllIIIlIIlIlIIIll = RainbowShader.INSTANCE;
            if (llllllllllllllllllIIIlIIIllllIll) {
               llllllllllllllllllIIIlIIlIlIIIll.setStrengthX(llllllllllllllllllIIIlIIlIlIIIIl);
               llllllllllllllllllIIIlIIlIlIIIll.setStrengthY(llllllllllllllllllIIIlIIIllIIlll);
               llllllllllllllllllIIIlIIlIlIIIll.setOffset(llllllllllllllllllIIIlIIlIIlllll);
               llllllllllllllllllIIIlIIlIlIIIll.startShader();
            }

            llllllllllllllllllIIIlIIIllIlIIlx = (Closeable)llllllllllllllllllIIIlIIlIlIIIll;
            llllllllllllllllllIIIlIIIllIlIII = false;
            llllllllllllllllllIIIlIIIllIIlllx = (Throwable)null;

            try {
               llllllllllllllllllIIIlIIlIIlllIl = (RainbowShader)llllllllllllllllllIIIlIIIllIlIIlx;
               llllllllllllllllllIIIlIIlIIlllII = false;
               RenderUtils.drawBorder(llllllllllllllllllIIIlIIIllllIII - 1.0F, llllllllllllllllllIIIlIIlIIIIlIl - 1.0F, llllllllllllllllllIIIlIIIllllIII + (float)llllllllllllllllllIIIlIIlIIIlIIl.menuWidth - 2.0F, llllllllllllllllllIIIlIIlIIIIlIl + llllllllllllllllllIIIlIIlIIIllIl - 1.0F, llllllllllllllllllIIIlIIIlllIIll, llllllllllllllllllIIIlIIIlllIlII);
               llllllllllllllllllIIIlIIIllIIllI2 = Unit.INSTANCE;
            } catch (Throwable var41) {
               llllllllllllllllllIIIlIIIllIIlllx = var41;
               throw var41;
            } finally {
               CloseableKt.closeFinally(llllllllllllllllllIIIlIIIllIlIIlx, llllllllllllllllllIIIlIIIllIIlllx);
            }
         }

         RenderUtils.drawRect(llllllllllllllllllIIIlIIIllllIII - 1.0F, llllllllllllllllllIIIlIIlIIIIlIl - 1.0F, llllllllllllllllllIIIlIIIllllIII + (float)llllllllllllllllllIIIlIIlIIIlIIl.menuWidth - 2.0F, llllllllllllllllllIIIlIIlIIIIlIl + llllllllllllllllllIIIlIIlIIIllIl - 1.0F, llllllllllllllllllIIIlIIlIIIIIIl);
         llllllllllllllllllIIIlIIIllIlIIl = RainbowShader.Companion;
         llllllllllllllllllIIIlIIlIlIIIIl = ((Number)TabGUI.this.rainbowX.get()).floatValue() == 0.0F ? 0.0F : 1.0F / ((Number)TabGUI.this.rainbowX.get()).floatValue();
         llllllllllllllllllIIIlIIIllIIlll = ((Number)TabGUI.this.rainbowY.get()).floatValue() == 0.0F ? 0.0F : 1.0F / ((Number)TabGUI.this.rainbowY.get()).floatValue();
         llllllllllllllllllIIIlIIlIIlllll = (float)(System.currentTimeMillis() % (long)10000) / 10000.0F;
         llllllllllllllllllIIIlIIlIIlllII = false;
         llllllllllllllllllIIIlIIlIlIIIll = RainbowShader.INSTANCE;
         if (llllllllllllllllllIIIlIIIllIlllI) {
            llllllllllllllllllIIIlIIlIlIIIll.setStrengthX(llllllllllllllllllIIIlIIlIlIIIIl);
            llllllllllllllllllIIIlIIlIlIIIll.setStrengthY(llllllllllllllllllIIIlIIIllIIlll);
            llllllllllllllllllIIIlIIlIlIIIll.setOffset(llllllllllllllllllIIIlIIlIIlllll);
            llllllllllllllllllIIIlIIlIlIIIll.startShader();
         }

         llllllllllllllllllIIIlIIIllIlIIlx = (Closeable)llllllllllllllllllIIIlIIlIlIIIll;
         llllllllllllllllllIIIlIIIllIlIII = false;
         llllllllllllllllllIIIlIIIllIIlllx = (Throwable)null;

         try {
            llllllllllllllllllIIIlIIlIIlllIl = (RainbowShader)llllllllllllllllllIIIlIIIllIlIIlx;
            llllllllllllllllllIIIlIIlIIlllII = false;
            RenderUtils.drawRect(llllllllllllllllllIIIlIIIllllIII - (float)1, llllllllllllllllllIIIlIIlIIIIlIl + TabGUI.this.itemY - (float)1, llllllllllllllllllIIIlIIIllllIII + (float)llllllllllllllllllIIIlIIlIIIlIIl.menuWidth - 2.0F, llllllllllllllllllIIIlIIlIIIIlIl + TabGUI.this.itemY + ((Number)TabGUI.this.tabHeight.get()).floatValue() - (float)1, llllllllllllllllllIIIlIIlIIIIIll);
            llllllllllllllllllIIIlIIIllIIllI2 = Unit.INSTANCE;
         } catch (Throwable var39) {
            llllllllllllllllllIIIlIIIllIIlllx = var39;
            throw var39;
         } finally {
            CloseableKt.closeFinally(llllllllllllllllllIIIlIIIllIlIIlx, llllllllllllllllllIIIlIIIllIIlllx);
         }

         GlStateManager.resetColor();
         char llllllllllllllllllIIIlIIlIIlIIIl = (Iterable)llllllllllllllllllIIIlIIlIIIlIIl.modules;
         llllllllllllllllllIIIlIIIllIlIII = false;
         Exception llllllllllllllllllIIIlIIIllIIlllxx = 0;

         boolean var59;
         for(Iterator llllllllllllllllllIIIlIIIllIIllI = llllllllllllllllllIIIlIIlIIlIIIl.iterator(); llllllllllllllllllIIIlIIIllIIllI.hasNext(); var59 = false) {
            Object llllllllllllllllllIIIlIIlIIlIlII = llllllllllllllllllIIIlIIIllIIllI.next();
            int llllllllllllllllllIIIlIIIllIIIll = llllllllllllllllllIIIlIIIllIIlllxx++;
            long llllllllllllllllllIIIlIIIllIIIlI = false;
            if (llllllllllllllllllIIIlIIIllIIIll < 0) {
               CollectionsKt.throwIndexOverflow();
            }

            String llllllllllllllllllIIIlIIIlIllllI = (Module)llllllllllllllllllIIIlIIlIIlIlII;
            int llllllllllllllllllIIIlIIlIIlIllI = false;
            int llllllllllllllllllIIIlIIlIIllIll = llllllllllllllllllIIIlIIIlIllllI.getState() ? 16777215 : (new Color(205, 205, 205)).getRGB();
            var10000 = llllllllllllllllllIIIlIIIlllIIIl;
            if (llllllllllllllllllIIIlIIIlllllIl) {
               double llllllllllllllllllIIIlIIIlIllIIl = llllllllllllllllllIIIlIIIlIllllI.getName();
               boolean llllllllllllllllllIIIlIIIlIlIlIl = false;
               if (llllllllllllllllllIIIlIIIlIllIIl == null) {
                  throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
               }

               var51 = llllllllllllllllllIIIlIIIlIllIIl.toUpperCase();
               Intrinsics.checkExpressionValueIsNotNull(var51, "(this as java.lang.String).toUpperCase()");
               short llllllllllllllllllIIIlIIIlIlIlII = var51;
               var10000 = llllllllllllllllllIIIlIIIlllIIIl;
               var10001 = llllllllllllllllllIIIlIIIlIlIlII;
            } else {
               var10001 = llllllllllllllllllIIIlIIIlIllllI.getName();
            }

            var10000.drawString(var10001, llllllllllllllllllIIIlIIIllllIII + 2.0F, llllllllllllllllllIIIlIIlIIIIlIl + ((Number)TabGUI.this.tabHeight.get()).floatValue() * (float)llllllllllllllllllIIIlIIIllIIIll + ((Number)TabGUI.this.textPositionY.get()).floatValue(), llllllllllllllllllIIIlIIlIIllIll, (Boolean)TabGUI.this.textShadow.get());
         }

      }

      public final void setMenuWidth(int llllllllllllllllllIIIlIlIIlIIlIl) {
         llllllllllllllllllIIIlIlIIlIlIII.menuWidth = llllllllllllllllllIIIlIlIIlIIlIl;
      }

      public final float getTextFade() {
         return llllllllllllllllllIIIlIlIIlIIIll.textFade;
      }

      public final void setTextFade(float llllllllllllllllllIIIlIlIIIlllII) {
         llllllllllllllllllIIIlIlIIIlllIl.textFade = llllllllllllllllllIIIlIlIIIlllII;
      }

      @NotNull
      public final String getTabName() {
         return llllllllllllllllllIIIlIIIlIlIIII.tabName;
      }

      public final int getMenuWidth() {
         return llllllllllllllllllIIIlIlIIlIllII.menuWidth;
      }
   }

   @Metadata(
      mv = {1, 1, 16},
      bv = {1, 0, 3},
      k = 1,
      d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007¨\u0006\b"},
      d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/TabGUI$Action;", "", "(Ljava/lang/String;I)V", "UP", "DOWN", "LEFT", "RIGHT", "TOGGLE", "LiquidSense"}
   )
   public static enum Action {
      // $FF: synthetic field
      UP,
      // $FF: synthetic field
      TOGGLE,
      // $FF: synthetic field
      LEFT,
      // $FF: synthetic field
      DOWN,
      // $FF: synthetic field
      RIGHT;
   }
}
