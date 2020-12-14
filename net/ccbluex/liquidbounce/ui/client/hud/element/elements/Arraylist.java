package net.ccbluex.liquidbounce.ui.client.hud.element.elements;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.ui.font.GameFontRenderer;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.FontValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B-\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\n\u0010<\u001a\u0004\u0018\u00010=H\u0016J\b\u0010>\u001a\u00020?H\u0016R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\"X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010#\u001a\b\u0012\u0004\u0012\u00020%0$X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010.\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00100\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00101\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00102\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00103\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00104\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00105\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00106\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00107\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00108\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00109\u001a\u00020:X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010;\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006@"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Arraylist;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "x", "", "y", "scale", "", "side", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side;", "(DDFLnet/ccbluex/liquidbounce/ui/client/hud/element/Side;)V", "Breakchange", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "Rianbowb", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "RianbowbValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "Rianbowg", "Rianbowr", "RianbowsValue", "RianbowspeedValue", "TwoRainbow", "animationValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "backgroundColorAlphaValue", "backgroundColorBlueValue", "backgroundColorGreenValue", "backgroundColorModeValue", "backgroundColorRedValue", "brightnessValue", "colorBlueValue", "colorGreenValue", "colorModeValue", "colorRedValue", "fontValue", "Lnet/ccbluex/liquidbounce/value/FontValue;", "modules", "", "Lnet/ccbluex/liquidbounce/features/module/Module;", "rainbowX", "rainbowY", "rectColorBlueAlpha", "rectColorBlueValue", "rectColorGreenValue", "rectColorModeValue", "rectColorRedValue", "rectValue", "rleftall", "rleftright", "rlefttop", "saturationValue", "shadow", "spaceValue", "tags", "tagsArrayColor", "textHeightValue", "textYValue", "upperCaseValue", "x2", "", "y2", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "updateElement", "", "LiquidSense"}
)
@ElementInfo(
   name = "Arraylist",
   single = true
)
public final class Arraylist extends Element {
   // $FF: synthetic field
   private final IntegerValue colorBlueValue;
   // $FF: synthetic field
   private final FloatValue rainbowX;
   // $FF: synthetic field
   private final IntegerValue rectColorRedValue;
   // $FF: synthetic field
   private final BoolValue rleftall;
   // $FF: synthetic field
   private final FloatValue RianbowbValue;
   // $FF: synthetic field
   private int x2;
   // $FF: synthetic field
   private final IntegerValue rectColorBlueValue;
   // $FF: synthetic field
   private final ListValue colorModeValue;
   // $FF: synthetic field
   private final FloatValue rainbowY;
   // $FF: synthetic field
   private final IntegerValue backgroundColorBlueValue;
   // $FF: synthetic field
   private final FloatValue saturationValue;
   // $FF: synthetic field
   private final FloatValue brightnessValue;
   // $FF: synthetic field
   private final IntegerValue Rianbowb;
   // $FF: synthetic field
   private final FloatValue textHeightValue;
   // $FF: synthetic field
   private final FloatValue RianbowsValue;
   // $FF: synthetic field
   private final ListValue animationValue;
   // $FF: synthetic field
   private final BoolValue Breakchange;
   // $FF: synthetic field
   private List<? extends Module> modules;
   // $FF: synthetic field
   private final IntegerValue colorGreenValue;
   // $FF: synthetic field
   private final IntegerValue backgroundColorAlphaValue;
   // $FF: synthetic field
   private final BoolValue rleftright;
   // $FF: synthetic field
   private final FloatValue textYValue;
   // $FF: synthetic field
   private final FontValue fontValue;
   // $FF: synthetic field
   private final IntegerValue rectColorGreenValue;
   // $FF: synthetic field
   private final FloatValue TwoRainbow;
   // $FF: synthetic field
   private final BoolValue tagsArrayColor;
   // $FF: synthetic field
   private final IntegerValue Rianbowr;
   // $FF: synthetic field
   private final IntegerValue colorRedValue;
   // $FF: synthetic field
   private final FloatValue spaceValue;
   // $FF: synthetic field
   private final ListValue rectColorModeValue;
   // $FF: synthetic field
   private float y2;
   // $FF: synthetic field
   private final BoolValue upperCaseValue;
   // $FF: synthetic field
   private final IntegerValue rectColorBlueAlpha;
   // $FF: synthetic field
   private final IntegerValue backgroundColorGreenValue;
   // $FF: synthetic field
   private final IntegerValue RianbowspeedValue;
   // $FF: synthetic field
   private final IntegerValue Rianbowg;
   // $FF: synthetic field
   private final BoolValue tags;
   // $FF: synthetic field
   private final ListValue backgroundColorModeValue;
   // $FF: synthetic field
   private final BoolValue rlefttop;
   // $FF: synthetic field
   private final ListValue rectValue;
   // $FF: synthetic field
   private final BoolValue shadow;
   // $FF: synthetic field
   private final IntegerValue backgroundColorRedValue;

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   public Arraylist() {
      this(0.0D, 0.0D, 0.0F, (Side)null, 15, (DefaultConstructorMarker)null);
   }

   // $FF: synthetic method
   public static final BoolValue access$getTagsArrayColor$p(Arraylist lllllllllllllllllIllIlllllIlIlII) {
      return lllllllllllllllllIllIlllllIlIlII.tagsArrayColor;
   }

   // $FF: synthetic method
   public static final BoolValue access$getTags$p(Arraylist lllllllllllllllllIllIlllllIllIlI) {
      return lllllllllllllllllIllIlllllIllIlI.tags;
   }

   // $FF: synthetic method
   public static final BoolValue access$getUpperCaseValue$p(Arraylist lllllllllllllllllIllIllllllIIIlI) {
      return lllllllllllllllllIllIllllllIIIlI.upperCaseValue;
   }

   // $FF: synthetic method
   public Arraylist(double var1, double var3, float var5, Side var6, int lllllllllllllllllIllIllllllIlIll, DefaultConstructorMarker var8) {
      if ((lllllllllllllllllIllIllllllIlIll & 1) != 0) {
         var1 = 1.0D;
      }

      if ((lllllllllllllllllIllIllllllIlIll & 2) != 0) {
         var3 = 2.0D;
      }

      if ((lllllllllllllllllIllIllllllIlIll & 4) != 0) {
         var5 = 1.0F;
      }

      if ((lllllllllllllllllIllIllllllIlIll & 8) != 0) {
         var6 = new Side(Side.Horizontal.RIGHT, Side.Vertical.UP);
      }

      this(var1, var3, var5, var6);
   }

   public Arraylist(double lllllllllllllllllIlllIIIIIIlIIlI, double lllllllllllllllllIlllIIIIIIlIIIl, float lllllllllllllllllIlllIIIIIIlIIII, @NotNull Side lllllllllllllllllIlllIIIIIIIlIlI) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllIlllIIIIIIIlIlI, "side");
      super(lllllllllllllllllIlllIIIIIIlIIlI, lllllllllllllllllIlllIIIIIIlIIIl, lllllllllllllllllIlllIIIIIIlIIII, lllllllllllllllllIlllIIIIIIIlIlI);
      lllllllllllllllllIlllIIIIIIIlllI.RianbowspeedValue = new IntegerValue("BRainbowSpeed", 90, 1, 90);
      lllllllllllllllllIlllIIIIIIIlllI.RianbowbValue = new FloatValue("BRainbow-Saturation", 1.0F, 0.0F, 1.0F);
      lllllllllllllllllIlllIIIIIIIlllI.RianbowsValue = new FloatValue("BRainbow-Brightness", 1.0F, 0.0F, 1.0F);
      lllllllllllllllllIlllIIIIIIIlllI.Rianbowr = new IntegerValue("BRainbow-R", 0, 0, 255);
      lllllllllllllllllIlllIIIIIIIlllI.Rianbowb = new IntegerValue("BRainbow-B", 50, 0, 64);
      lllllllllllllllllIlllIIIIIIIlllI.Rianbowg = new IntegerValue("BRainbow-G", 50, 0, 64);
      lllllllllllllllllIlllIIIIIIIlllI.rainbowX = new FloatValue("Rainbow-X", -1000.0F, -2000.0F, 2000.0F);
      lllllllllllllllllIlllIIIIIIIlllI.rainbowY = new FloatValue("Rainbow-Y", -1000.0F, -2000.0F, 2000.0F);
      lllllllllllllllllIlllIIIIIIIlllI.colorModeValue = new ListValue("Text-Color", new String[]{"Custom", "Random", "Rainbow", "OtherRainbow", "ALLRainbow", "Bainbow", "TwoRainbow"}, "OtherRainbow");
      lllllllllllllllllIlllIIIIIIIlllI.colorRedValue = new IntegerValue("Text-R", 0, 0, 255);
      lllllllllllllllllIlllIIIIIIIlllI.colorGreenValue = new IntegerValue("Text-G", 111, 0, 255);
      lllllllllllllllllIlllIIIIIIIlllI.colorBlueValue = new IntegerValue("Text-B", 255, 0, 255);
      lllllllllllllllllIlllIIIIIIIlllI.rectColorModeValue = new ListValue("Rect-Color", new String[]{"Custom", "Random", "Rainbow", "OtherRainbow", "ALLRainbow", "Bainbow", "TwoRainbow"}, "OtherRainbow");
      lllllllllllllllllIlllIIIIIIIlllI.rectColorRedValue = new IntegerValue("Rect-R", 255, 0, 255);
      lllllllllllllllllIlllIIIIIIIlllI.rectColorGreenValue = new IntegerValue("Rect-G", 255, 0, 255);
      lllllllllllllllllIlllIIIIIIIlllI.rectColorBlueValue = new IntegerValue("Rect-B", 255, 0, 255);
      lllllllllllllllllIlllIIIIIIIlllI.rectColorBlueAlpha = new IntegerValue("Rect-Alpha", 255, 0, 255);
      lllllllllllllllllIlllIIIIIIIlllI.saturationValue = new FloatValue("Random-Saturation", 0.9F, 0.0F, 1.0F);
      lllllllllllllllllIlllIIIIIIIlllI.brightnessValue = new FloatValue("Random-Brightness", 1.0F, 0.0F, 1.0F);
      lllllllllllllllllIlllIIIIIIIlllI.TwoRainbow = new FloatValue("TwoRainbow", 1.0F, 0.0F, 1.0F);
      lllllllllllllllllIlllIIIIIIIlllI.tags = new BoolValue("Tags", true);
      lllllllllllllllllIlllIIIIIIIlllI.shadow = new BoolValue("ShadowText", true);
      lllllllllllllllllIlllIIIIIIIlllI.backgroundColorModeValue = new ListValue("Background-Color", new String[]{"Custom", "Random", "Rainbow", "OtherRainbow", "ALLRainbow", "Bainbow", "TwoRainbow"}, "Custom");
      lllllllllllllllllIlllIIIIIIIlllI.backgroundColorRedValue = new IntegerValue("Background-R", 0, 0, 255);
      lllllllllllllllllIlllIIIIIIIlllI.backgroundColorGreenValue = new IntegerValue("Background-G", 0, 0, 255);
      lllllllllllllllllIlllIIIIIIIlllI.backgroundColorBlueValue = new IntegerValue("Background-B", 0, 0, 255);
      lllllllllllllllllIlllIIIIIIIlllI.backgroundColorAlphaValue = new IntegerValue("Background-Alpha", 0, 0, 255);
      lllllllllllllllllIlllIIIIIIIlllI.rectValue = new ListValue("Rect", new String[]{"None", "Left", "Right", "RLeft"}, "RLeft");
      lllllllllllllllllIlllIIIIIIIlllI.rlefttop = new BoolValue("RLeftTop", false);
      lllllllllllllllllIlllIIIIIIIlllI.rleftright = new BoolValue("RLeftRight", true);
      lllllllllllllllllIlllIIIIIIIlllI.rleftall = new BoolValue("RLeftALL", false);
      lllllllllllllllllIlllIIIIIIIlllI.upperCaseValue = new BoolValue("UpperCase", false);
      lllllllllllllllllIlllIIIIIIIlllI.spaceValue = new FloatValue("Space", 0.0F, 0.0F, 5.0F);
      lllllllllllllllllIlllIIIIIIIlllI.textHeightValue = new FloatValue("TextHeight", 11.0F, 1.0F, 20.0F);
      lllllllllllllllllIlllIIIIIIIlllI.textYValue = new FloatValue("TextY", 0.0F, 0.0F, 20.0F);
      lllllllllllllllllIlllIIIIIIIlllI.tagsArrayColor = new BoolValue("TagsArrayColor", false);
      lllllllllllllllllIlllIIIIIIIlllI.Breakchange = new BoolValue("NameBreak", false);
      lllllllllllllllllIlllIIIIIIIlllI.animationValue = new ListValue("Animation", new String[]{"Normal", "slide"}, "Normal");
      GameFontRenderer var10004 = Fonts.font40;
      Intrinsics.checkExpressionValueIsNotNull(var10004, "Fonts.font40");
      lllllllllllllllllIlllIIIIIIIlllI.fontValue = new FontValue("Font", (FontRenderer)var10004);
      lllllllllllllllllIlllIIIIIIIlllI.modules = CollectionsKt.emptyList();
   }

   public void updateElement() {
      float lllllllllllllllllIlllIIIIIlIlIIl = (Iterable)LiquidBounce.INSTANCE.getModuleManager().getModules();
      int lllllllllllllllllIlllIIIIIlIIlII = false;
      Collection lllllllllllllllllIlllIIIIIlIllIl = (Collection)(new ArrayList());
      int lllllllllllllllllIlllIIIIIlIllII = false;
      Iterator lllllllllllllllllIlllIIIIIlIIIII = lllllllllllllllllIlllIIIIIlIlIIl.iterator();

      while(lllllllllllllllllIlllIIIIIlIIIII.hasNext()) {
         boolean lllllllllllllllllIlllIIIIIIlllll = lllllllllllllllllIlllIIIIIlIIIII.next();
         Module lllllllllllllllllIlllIIIIIllIIIl = (Module)lllllllllllllllllIlllIIIIIIlllll;
         int lllllllllllllllllIlllIIIIIllIIII = false;
         if (lllllllllllllllllIlllIIIIIllIIIl.getArray() && lllllllllllllllllIlllIIIIIllIIIl.getSlide() > (float)0) {
            lllllllllllllllllIlllIIIIIlIllIl.add(lllllllllllllllllIlllIIIIIIlllll);
            boolean var10001 = false;
         }
      }

      int lllllllllllllllllIlllIIIIIIllIll = (List)lllllllllllllllllIlllIIIIIlIllIl;
      lllllllllllllllllIlllIIIIIlIlIIl = (Iterable)lllllllllllllllllIlllIIIIIIllIll;
      lllllllllllllllllIlllIIIIIlIIlII = false;
      double lllllllllllllllllIlllIIIIIlIIIlI = false;
      short lllllllllllllllllIlllIIIIIlIIIIl = (Comparator)(new Arraylist$updateElement$$inlined$sortedBy$1(lllllllllllllllllIlllIIIIIlIIlll));
      lllllllllllllllllIlllIIIIIIllIll = CollectionsKt.sortedWith(lllllllllllllllllIlllIIIIIlIlIIl, lllllllllllllllllIlllIIIIIlIIIIl);
      lllllllllllllllllIlllIIIIIlIIlll.modules = lllllllllllllllllIlllIIIIIIllIll;
   }

   @Nullable
   public Border drawElement() {
      // $FF: Couldn't be decompiled
   }

   // $FF: synthetic method
   public static final FontValue access$getFontValue$p(Arraylist lllllllllllllllllIllIllllllIlIII) {
      return lllllllllllllllllIllIllllllIlIII.fontValue;
   }
}
