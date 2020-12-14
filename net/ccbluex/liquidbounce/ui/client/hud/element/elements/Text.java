//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.ui.client.hud.element.elements;

import java.awt.Color;
import java.io.Closeable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.ui.client.hud.designer.GuiHudDesigner;
import net.ccbluex.liquidbounce.ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.CPSCounter;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.ServerUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.UiUtils;
import net.ccbluex.liquidbounce.utils.render.shader.shaders.RainbowFontShader;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.FontValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.TextValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.Session;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ElementInfo(
   name = "Text"
)
@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\f\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000 72\u00020\u0001:\u00017B-\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\n\u0010(\u001a\u0004\u0018\u00010)H\u0016J\u0012\u0010*\u001a\u0004\u0018\u00010\r2\u0006\u0010+\u001a\u00020\rH\u0002J\u0018\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020/2\u0006\u00100\u001a\u00020\u0016H\u0016J \u00101\u001a\u00020-2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u00102\u001a\u00020\u0016H\u0016J\u0010\u00103\u001a\u00020\r2\u0006\u0010+\u001a\u00020\rH\u0002J\u000e\u00104\u001a\u00020\u00002\u0006\u0010.\u001a\u000205J\b\u00106\u001a\u00020-H\u0016R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\u00020\r8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\"X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\"X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u00068"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Text;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "x", "", "y", "scale", "", "side", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side;", "(DDFLnet/ccbluex/liquidbounce/ui/client/hud/element/Side;)V", "blueValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "display", "", "getDisplay", "()Ljava/lang/String;", "displayString", "Lnet/ccbluex/liquidbounce/value/TextValue;", "displayText", "editMode", "", "editTicks", "", "fontValue", "Lnet/ccbluex/liquidbounce/value/FontValue;", "greenValue", "only", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "op", "outline", "prevClick", "", "rainbow", "rainbowX", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "rainbowY", "rect", "redValue", "shadow", "sk", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "getReplacement", "str", "handleKey", "", "c", "", "keyCode", "handleMouseClick", "mouseButton", "multiReplace", "setColor", "Ljava/awt/Color;", "updateElement", "Companion", "LiquidSense"}
)
public final class Text extends Element {
   // $FF: synthetic field
   private final BoolValue outline;
   // $FF: synthetic field
   private final BoolValue op;
   // $FF: synthetic field
   private final TextValue displayString;
   // $FF: synthetic field
   @NotNull
   private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");
   // $FF: synthetic field
   private final BoolValue rect;
   // $FF: synthetic field
   public static final Text.Companion Companion = new Text.Companion((DefaultConstructorMarker)null);
   // $FF: synthetic field
   private String displayText;
   // $FF: synthetic field
   private final IntegerValue greenValue;
   // $FF: synthetic field
   private final IntegerValue blueValue;
   // $FF: synthetic field
   private final BoolValue only;
   // $FF: synthetic field
   private final BoolValue shadow;
   // $FF: synthetic field
   @NotNull
   private static final SimpleDateFormat HOUR_FORMAT = new SimpleDateFormat("HH:mm");
   // $FF: synthetic field
   private final BoolValue rainbow;
   // $FF: synthetic field
   private final FloatValue rainbowX;
   // $FF: synthetic field
   private FontValue fontValue;
   // $FF: synthetic field
   @NotNull
   private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMddyy");
   // $FF: synthetic field
   private final FloatValue rainbowY;
   // $FF: synthetic field
   private final BoolValue sk;
   // $FF: synthetic field
   private final IntegerValue redValue;
   // $FF: synthetic field
   @NotNull
   private static final DecimalFormat Y_FORMAT = new DecimalFormat("0.000000000");
   // $FF: synthetic field
   private int editTicks;
   // $FF: synthetic field
   private long prevClick;
   // $FF: synthetic field
   private boolean editMode;

   private final String multiReplace(String llIlIllllIlIIIl) {
      long llIlIllllIIlllI = -1;
      String llIlIllllIIllIl = new StringBuilder();
      short llIlIllllIIllII = 0;

      String var10000;
      boolean var10001;
      for(int llIlIllllIIlIll = ((CharSequence)llIlIllllIlIIIl).length(); llIlIllllIIllII < llIlIllllIIlIll; ++llIlIllllIIllII) {
         if (llIlIllllIlIIIl.charAt(llIlIllllIIllII) == '%') {
            if (llIlIllllIIlllI != -1) {
               if (llIlIllllIIlllI + 1 != llIlIllllIIllII) {
                  Exception llIlIllllIIlIII = llIlIllllIIlllI + 1;
                  String llIlIllllIIIlll = false;
                  if (llIlIllllIlIIIl == null) {
                     throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                  }

                  var10000 = llIlIllllIlIIIl.substring(llIlIllllIIlIII, llIlIllllIIllII);
                  Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                  String llIlIllllIIIlIl = var10000;
                  String llIlIllllIlIllI = llIlIllllIlIIII.getReplacement(llIlIllllIIIlIl);
                  if (llIlIllllIlIllI != null) {
                     llIlIllllIIllIl.append(llIlIllllIlIllI);
                     var10001 = false;
                     llIlIllllIIlllI = -1;
                     continue;
                  }
               }

               llIlIllllIIllIl.append((CharSequence)llIlIllllIlIIIl, llIlIllllIIlllI, llIlIllllIIllII);
               var10001 = false;
            }

            llIlIllllIIlllI = llIlIllllIIllII;
         } else if (llIlIllllIIlllI == -1) {
            llIlIllllIIllIl.append(llIlIllllIlIIIl.charAt(llIlIllllIIllII));
            var10001 = false;
         }
      }

      if (llIlIllllIIlllI != -1) {
         llIlIllllIIllIl.append((CharSequence)llIlIllllIlIIIl, llIlIllllIIlllI, llIlIllllIlIIIl.length());
         var10001 = false;
      }

      var10000 = String.valueOf(llIlIllllIIllIl);
      Intrinsics.checkExpressionValueIsNotNull(var10000, "result.toString()");
      return var10000;
   }

   public void handleKey(char llIlIlllIIIIllI, int llIlIlllIIIIIlI) {
      if (llIlIlllIIIIlII.editMode && access$getMc$p$s1046033730().currentScreen instanceof GuiHudDesigner) {
         if (llIlIlllIIIIIlI == 14) {
            double llIlIlllIIIIIIl = (CharSequence)llIlIlllIIIIlII.displayString.get();
            long llIlIlllIIIIIII = false;
            if (llIlIlllIIIIIIl.length() > 0) {
               TextValue var10000 = llIlIlllIIIIlII.displayString;
               double llIlIlllIIIIIIl = (String)llIlIlllIIIIlII.displayString.get();
               long llIlIlllIIIIIII = 0;
               byte llIlIllIlllllll = ((String)llIlIlllIIIIlII.displayString.get()).length() - 1;
               int llIlIllIlllllIl = var10000;
               double llIlIllIllllllI = false;
               if (llIlIlllIIIIIIl == null) {
                  throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
               }

               String var11 = llIlIlllIIIIIIl.substring(llIlIlllIIIIIII, llIlIllIlllllll);
               Intrinsics.checkExpressionValueIsNotNull(var11, "(this as java.lang.Strin…ing(startIndex, endIndex)");
               char llIlIllIlllllII = var11;
               llIlIllIlllllIl.set(llIlIllIlllllII);
            }

            llIlIlllIIIIlII.updateElement();
            return;
         }

         if (ChatAllowedCharacters.isAllowedCharacter(llIlIlllIIIIllI) || llIlIlllIIIIllI == 167) {
            llIlIlllIIIIlII.displayString.set(String.valueOf((new StringBuilder()).append((String)llIlIlllIIIIlII.displayString.get()).append(llIlIlllIIIIllI)));
         }

         llIlIlllIIIIlII.updateElement();
      }

   }

   // $FF: synthetic method
   public static final void access$setFontValue$p(Text llIlIllIlIIllll, FontValue llIlIllIlIIlllI) {
      llIlIllIlIIllll.fontValue = llIlIllIlIIlllI;
   }

   private final String getDisplay() {
      double llIlIllllllIIlI = (CharSequence)llIlIllllllIlII.displayString.get();
      long llIlIllllllIIIl = false;
      boolean llIlIllllllIIll = llIlIllllllIIlI.length() == 0 && !llIlIllllllIlII.editMode ? "Text Element" : (String)llIlIllllllIlII.displayString.get();
      return llIlIllllllIlII.multiReplace(llIlIllllllIIll);
   }

   // $FF: synthetic method
   public Text(double var1, double var3, float var5, Side var6, int llIlIllIlIllIll, DefaultConstructorMarker var8) {
      if ((llIlIllIlIllIll & 1) != 0) {
         var1 = 10.0D;
      }

      if ((llIlIllIlIllIll & 2) != 0) {
         var3 = 10.0D;
      }

      if ((llIlIllIlIllIll & 4) != 0) {
         var5 = 1.0F;
      }

      if ((llIlIllIlIllIll & 8) != 0) {
         var6 = Side.Companion.default();
      }

      this(var1, var3, var5, var6);
   }

   public Text(double llIlIllIllIlIlI, double llIlIllIllIlllI, float llIlIllIllIlIII, @NotNull Side llIlIllIllIIlll) {
      Intrinsics.checkParameterIsNotNull(llIlIllIllIIlll, "side");
      super(llIlIllIllIlIlI, llIlIllIllIlllI, llIlIllIllIlIII, llIlIllIllIIlll);
      llIlIllIllIlIll.displayString = new TextValue("DisplayText", "");
      llIlIllIllIlIll.redValue = new IntegerValue("Red", 255, 0, 255);
      llIlIllIllIlIll.greenValue = new IntegerValue("Green", 255, 0, 255);
      llIlIllIllIlIll.blueValue = new IntegerValue("Blue", 255, 0, 255);
      llIlIllIllIlIll.rainbow = new BoolValue("Rainbow", false);
      llIlIllIllIlIll.rainbowX = new FloatValue("Rainbow-X", -1000.0F, -2000.0F, 2000.0F);
      llIlIllIllIlIll.rainbowY = new FloatValue("Rainbow-Y", -1000.0F, -2000.0F, 2000.0F);
      llIlIllIllIlIll.shadow = new BoolValue("Shadow", false);
      llIlIllIllIlIll.outline = new BoolValue("Outline", false);
      llIlIllIllIlIll.rect = new BoolValue("Rect", false);
      llIlIllIllIlIll.op = new BoolValue("OneTapRect", false);
      llIlIllIllIlIll.sk = new BoolValue("SkeetRect", true);
      llIlIllIllIlIll.only = new BoolValue("OnlyWhtie", false);
      FontRenderer var10004 = Fonts.minecraftFont;
      Intrinsics.checkExpressionValueIsNotNull(var10004, "Fonts.minecraftFont");
      llIlIllIllIlIll.fontValue = new FontValue("Font", var10004);
      llIlIllIllIlIll.displayText = llIlIllIllIlIll.getDisplay();
   }

   public Text() {
      this(0.0D, 0.0D, 0.0F, (Side)null, 15, (DefaultConstructorMarker)null);
   }

   @NotNull
   public final Text setColor(@NotNull Color llIlIllIlllIllI) {
      Intrinsics.checkParameterIsNotNull(llIlIllIlllIllI, "c");
      llIlIllIlllIlll.redValue.set(llIlIllIlllIllI.getRed());
      llIlIllIlllIlll.greenValue.set(llIlIllIlllIllI.getGreen());
      llIlIllIlllIlll.blueValue.set(llIlIllIlllIllI.getBlue());
      return llIlIllIlllIlll;
   }

   public void updateElement() {
      llIlIlllIIllllI.editTicks += 5;
      if (llIlIlllIIllllI.editTicks > 80) {
         llIlIlllIIllllI.editTicks = 0;
      }

      llIlIlllIIllllI.displayText = llIlIlllIIllllI.editMode ? (String)llIlIlllIIllllI.displayString.get() : llIlIlllIIllllI.getDisplay();
   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   private final String getReplacement(String llIlIlllllIlIII) {
      if (access$getMc$p$s1046033730().thePlayer != null) {
         switch(llIlIlllllIlIII.hashCode()) {
         case 48:
            if (llIlIlllllIlIII.equals("0")) {
               return "§0";
            }
            break;
         case 49:
            if (llIlIlllllIlIII.equals("1")) {
               return "§1";
            }
            break;
         case 50:
            if (llIlIlllllIlIII.equals("2")) {
               return "§2";
            }
            break;
         case 51:
            if (llIlIlllllIlIII.equals("3")) {
               return "§3";
            }
            break;
         case 52:
            if (llIlIlllllIlIII.equals("4")) {
               return "§4";
            }
            break;
         case 53:
            if (llIlIlllllIlIII.equals("5")) {
               return "§5";
            }
            break;
         case 54:
            if (llIlIlllllIlIII.equals("6")) {
               return "§6";
            }
            break;
         case 55:
            if (llIlIlllllIlIII.equals("7")) {
               return "§7";
            }
            break;
         case 56:
            if (llIlIlllllIlIII.equals("8")) {
               return "§8";
            }
            break;
         case 57:
            if (llIlIlllllIlIII.equals("9")) {
               return "§9";
            }
            break;
         case 97:
            if (llIlIlllllIlIII.equals("a")) {
               return "§a";
            }
            break;
         case 98:
            if (llIlIlllllIlIII.equals("b")) {
               return "§b";
            }
            break;
         case 99:
            if (llIlIlllllIlIII.equals("c")) {
               return "§c";
            }
            break;
         case 100:
            if (llIlIlllllIlIII.equals("d")) {
               return "§d";
            }
            break;
         case 101:
            if (llIlIlllllIlIII.equals("e")) {
               return "§e";
            }
            break;
         case 102:
            if (llIlIlllllIlIII.equals("f")) {
               return "§f";
            }
            break;
         case 107:
            if (llIlIlllllIlIII.equals("k")) {
               return "§k";
            }
            break;
         case 108:
            if (llIlIlllllIlIII.equals("l")) {
               return "§l";
            }
            break;
         case 109:
            if (llIlIlllllIlIII.equals("m")) {
               return "§m";
            }
            break;
         case 110:
            if (llIlIlllllIlIII.equals("n")) {
               return "§n";
            }
            break;
         case 111:
            if (llIlIlllllIlIII.equals("o")) {
               return "§o";
            }
            break;
         case 114:
            if (llIlIlllllIlIII.equals("r")) {
               return "§r";
            }
            break;
         case 120:
            if (llIlIlllllIlIII.equals("x")) {
               return DECIMAL_FORMAT.format(access$getMc$p$s1046033730().thePlayer.posX);
            }
            break;
         case 121:
            if (llIlIlllllIlIII.equals("y")) {
               return Y_FORMAT.format(access$getMc$p$s1046033730().thePlayer.posY);
            }
            break;
         case 122:
            if (llIlIlllllIlIII.equals("z")) {
               return DECIMAL_FORMAT.format(access$getMc$p$s1046033730().thePlayer.posZ);
            }
            break;
         case 118532:
            if (llIlIlllllIlIII.equals("xdp")) {
               return String.valueOf(access$getMc$p$s1046033730().thePlayer.posX);
            }
            break;
         case 119493:
            if (llIlIlllllIlIII.equals("ydp")) {
               return String.valueOf(access$getMc$p$s1046033730().thePlayer.posY);
            }
            break;
         case 120454:
            if (llIlIlllllIlIII.equals("zdp")) {
               return String.valueOf(access$getMc$p$s1046033730().thePlayer.posZ);
            }
            break;
         case 3441010:
            if (llIlIlllllIlIII.equals("ping")) {
               return String.valueOf(EntityUtils.getPing((EntityPlayer)access$getMc$p$s1046033730().thePlayer));
            }
            break;
         case 2134260957:
            if (llIlIlllllIlIII.equals("velocity")) {
               DecimalFormat var10000 = DECIMAL_FORMAT;
               double llIlIlllllIIllI = access$getMc$p$s1046033730().thePlayer.motionX * access$getMc$p$s1046033730().thePlayer.motionX + access$getMc$p$s1046033730().thePlayer.motionZ * access$getMc$p$s1046033730().thePlayer.motionZ;
               float llIlIlllllIIlII = var10000;
               byte llIlIlllllIIlIl = false;
               char llIlIlllllIIIll = Math.sqrt(llIlIlllllIIllI);
               return llIlIlllllIIlII.format(llIlIlllllIIIll);
            }
         }
      }

      String var9;
      switch(llIlIlllllIlIII.hashCode()) {
      case -892772691:
         if (llIlIlllllIlIII.equals("clientversion")) {
            var9 = "b6";
            return var9;
         }
         break;
      case -265713450:
         if (llIlIlllllIlIII.equals("username")) {
            Session var10 = access$getMc$p$s1046033730().getSession();
            Intrinsics.checkExpressionValueIsNotNull(var10, "mc.getSession()");
            var9 = var10.getUsername();
            return var9;
         }
         break;
      case -215825919:
         if (llIlIlllllIlIII.equals("clientcreator")) {
            var9 = "CCBlueX";
            return var9;
         }
         break;
      case 98726:
         if (llIlIlllllIlIII.equals("cps")) {
            return String.valueOf(CPSCounter.getCPS(CPSCounter.MouseButton.LEFT));
         }
         break;
      case 101609:
         if (llIlIlllllIlIII.equals("fps")) {
            var9 = String.valueOf(Minecraft.getDebugFPS());
            return var9;
         }
         break;
      case 3076014:
         if (llIlIlllllIlIII.equals("date")) {
            var9 = DATE_FORMAT.format(System.currentTimeMillis());
            return var9;
         }
         break;
      case 3316154:
         if (llIlIlllllIlIII.equals("lcps")) {
            return String.valueOf(CPSCounter.getCPS(CPSCounter.MouseButton.LEFT));
         }
         break;
      case 3345945:
         if (llIlIlllllIlIII.equals("mcps")) {
            return String.valueOf(CPSCounter.getCPS(CPSCounter.MouseButton.MIDDLE));
         }
         break;
      case 3494900:
         if (llIlIlllllIlIII.equals("rcps")) {
            return String.valueOf(CPSCounter.getCPS(CPSCounter.MouseButton.RIGHT));
         }
         break;
      case 3560141:
         if (llIlIlllllIlIII.equals("time")) {
            var9 = HOUR_FORMAT.format(System.currentTimeMillis());
            return var9;
         }
         break;
      case 1103204566:
         if (llIlIlllllIlIII.equals("clientname")) {
            var9 = "LiquidSense";
            return var9;
         }
         break;
      case 1379104682:
         if (llIlIlllllIlIII.equals("serverip")) {
            var9 = ServerUtils.getRemoteIp();
            return var9;
         }
      }

      var9 = null;
      return var9;
   }

   public void handleMouseClick(double llIlIlllIIlIIll, double llIlIlllIIlIIlI, int llIlIlllIIlIIIl) {
      if (llIlIlllIIlIlII.isInBorder(llIlIlllIIlIIll, llIlIlllIIlIIlI) && llIlIlllIIlIIIl == 0) {
         if (System.currentTimeMillis() - llIlIlllIIlIlII.prevClick <= 250L) {
            llIlIlllIIlIlII.editMode = true;
         }

         llIlIlllIIlIlII.prevClick = System.currentTimeMillis();
      } else {
         llIlIlllIIlIlII.editMode = false;
      }

   }

   @Nullable
   public Border drawElement() {
      int llIlIlllIlIllII = (new Color(((Number)llIlIlllIlIlIll.redValue.get()).intValue(), ((Number)llIlIlllIlIlIll.greenValue.get()).intValue(), ((Number)llIlIlllIlIlIll.blueValue.get()).intValue())).getRGB();
      String llIlIlllIlIlIII = (new Color(((Number)llIlIlllIlIlIll.redValue.get()).intValue(), ((Number)llIlIlllIlIlIll.greenValue.get()).intValue(), ((Number)llIlIlllIlIlIll.blueValue.get()).intValue())).getRGB() + (new Color(0, 0, 0, 50)).getRGB();
      FontRenderer llIlIlllIlIlllI = (FontRenderer)llIlIlllIlIlIll.fontValue.get();
      if ((Boolean)llIlIlllIlIlIll.rect.get()) {
         RenderUtils.drawRect(-2.0F, -2.0F, (float)(llIlIlllIlIlllI.getStringWidth(llIlIlllIlIlIll.displayText) + 1), (float)llIlIlllIlIlllI.FONT_HEIGHT, (new Color(0, 0, 0, 150)).getRGB());
      }

      llIlIlllIlIlllI.drawString(llIlIlllIlIlIll.displayText, 0.0F, 0.0F, (Boolean)llIlIlllIlIlIll.rainbow.get() ? ColorUtils.rainbow(400000000L).getRGB() : ((Boolean)llIlIlllIlIlIll.only.get() ? -1 : llIlIlllIlIllII), (Boolean)llIlIlllIlIlIll.shadow.get());
      boolean var10001 = false;
      if ((Boolean)llIlIlllIlIlIll.op.get()) {
         RenderUtils.drawRect(-4.0F, -8.0F, (float)(llIlIlllIlIlllI.getStringWidth(llIlIlllIlIlIll.displayText) + 3), (float)llIlIlllIlIlllI.FONT_HEIGHT, (new Color(43, 43, 43)).getRGB());
         RenderUtils.drawGradientSideways(-3.0D, -7.0D, (double)llIlIlllIlIlllI.getStringWidth(llIlIlllIlIlIll.displayText) + 2.0D, -3.0D, (Boolean)llIlIlllIlIlIll.rainbow.get() ? ColorUtils.rainbow(400000000L).getRGB() + (new Color(0, 0, 0, 40)).getRGB() : llIlIlllIlIlIII, (Boolean)llIlIlllIlIlIll.rainbow.get() ? ColorUtils.rainbow(400000000L).getRGB() : llIlIlllIlIllII);
      }

      if ((Boolean)llIlIlllIlIlIll.sk.get()) {
         UiUtils.drawRect(-11.0D, -9.5D, (double)(llIlIlllIlIlllI.getStringWidth(llIlIlllIlIlIll.displayText) + 9), (double)llIlIlllIlIlllI.FONT_HEIGHT + (double)6, (new Color(0, 0, 0)).getRGB());
         UiUtils.outlineRect(-10.0D, -8.5D, (double)(llIlIlllIlIlllI.getStringWidth(llIlIlllIlIlIll.displayText) + 8), (double)llIlIlllIlIlllI.FONT_HEIGHT + (double)5, 8.0D, (new Color(59, 59, 59)).getRGB(), (new Color(59, 59, 59)).getRGB());
         UiUtils.outlineRect(-9.0D, -7.5D, (double)(llIlIlllIlIlllI.getStringWidth(llIlIlllIlIlIll.displayText) + 7), (double)llIlIlllIlIlllI.FONT_HEIGHT + (double)4, 4.0D, (new Color(59, 59, 59)).getRGB(), (new Color(40, 40, 40)).getRGB());
         UiUtils.outlineRect(-4.0D, -3.0D, (double)(llIlIlllIlIlllI.getStringWidth(llIlIlllIlIlIll.displayText) + 2), (double)llIlIlllIlIlllI.FONT_HEIGHT + (double)0, 1.0D, (new Color(18, 18, 18)).getRGB(), (new Color(0, 0, 0)).getRGB());
      }

      if ((Boolean)llIlIlllIlIlIll.outline.get()) {
         int llIlIlllIlllIII = 0;
         char llIlIlllIlIIlIl = llIlIlllIlIlIll.displayText;
         GlStateManager.resetColor();
         int var22 = llIlIlllIlIlllI.getStringWidth(llIlIlllIlIlIll.displayText);
         int var10002 = llIlIlllIlIlllI.getStringWidth(llIlIlllIlIlIll.displayText);
         Color var10004 = Color.BLACK;
         Intrinsics.checkExpressionValueIsNotNull(var10004, "Color.BLACK");
         RenderUtils.drawOutlinedString(llIlIlllIlIIlIl, var22, var10002, llIlIlllIlllIII, var10004.getRGB());
      }

      float llIlIlllIlIIllI = (Boolean)llIlIlllIlIlIll.rainbow.get();
      char llIlIlllIlIIlIl = RainbowFontShader.Companion;
      float llIlIlllIllIlIl = ((Number)llIlIlllIlIlIll.rainbowX.get()).floatValue() == 0.0F ? 0.0F : 1.0F / ((Number)llIlIlllIlIlIll.rainbowX.get()).floatValue();
      int llIlIlllIllIlII = ((Number)llIlIlllIlIlIll.rainbowY.get()).floatValue() == 0.0F ? 0.0F : 1.0F / ((Number)llIlIlllIlIlIll.rainbowY.get()).floatValue();
      short llIlIlllIlIIIlI = (float)(System.currentTimeMillis() % (long)10000) / 10000.0F;
      int llIlIlllIllIIII = false;
      RainbowFontShader llIlIlllIllIlll = RainbowFontShader.INSTANCE;
      if (llIlIlllIlIIllI) {
         llIlIlllIllIlll.setStrengthX(llIlIlllIllIlIl);
         llIlIlllIllIlll.setStrengthY(llIlIlllIllIlII);
         llIlIlllIllIlll.setOffset(llIlIlllIlIIIlI);
         llIlIlllIllIlll.startShader();
      }

      char llIlIlllIlIIlIl = (Closeable)llIlIlllIllIlll;
      float llIlIlllIlIIlII = false;
      Throwable llIlIlllIlIIIll = (Throwable)null;

      try {
         RainbowFontShader llIlIlllIllIIIl = (RainbowFontShader)llIlIlllIlIIlIl;
         llIlIlllIllIIII = false;
         llIlIlllIlIlllI.drawString(llIlIlllIlIlIll.displayText, 0.0F, 0.0F, llIlIlllIlIIllI ? 0 : llIlIlllIlIllII, (Boolean)llIlIlllIlIlIll.shadow.get());
         var10001 = false;
         if (llIlIlllIlIlIll.editMode && access$getMc$p$s1046033730().currentScreen instanceof GuiHudDesigner && llIlIlllIlIlIll.editTicks <= 40) {
            llIlIlllIlIlllI.drawString("_", (float)llIlIlllIlIlllI.getStringWidth(llIlIlllIlIlIll.displayText) + 2.0F, 0.0F, llIlIlllIlIIllI ? ColorUtils.rainbow(400000000L).getRGB() : llIlIlllIlIllII, (Boolean)llIlIlllIlIlIll.shadow.get());
            var10001 = false;
         }

         Unit llIlIlllIlIIIlI2 = Unit.INSTANCE;
      } catch (Throwable var13) {
         llIlIlllIlIIIll = var13;
         throw var13;
      } finally {
         CloseableKt.closeFinally(llIlIlllIlIIlIl, llIlIlllIlIIIll);
      }

      if (llIlIlllIlIlIll.editMode && !(access$getMc$p$s1046033730().currentScreen instanceof GuiHudDesigner)) {
         llIlIlllIlIlIll.editMode = false;
         llIlIlllIlIlIll.updateElement();
      }

      return new Border(-2.0F, -2.0F, (float)llIlIlllIlIlllI.getStringWidth(llIlIlllIlIlIll.displayText) + 2.0F, (float)llIlIlllIlIlllI.FONT_HEIGHT);
   }

   @Metadata(
      mv = {1, 1, 16},
      bv = {1, 0, 3},
      k = 1,
      d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u000f\u001a\u00020\u0010R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0006R\u0011\u0010\r\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\n¨\u0006\u0011"},
      d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Text$Companion;", "", "()V", "DATE_FORMAT", "Ljava/text/SimpleDateFormat;", "getDATE_FORMAT", "()Ljava/text/SimpleDateFormat;", "DECIMAL_FORMAT", "Ljava/text/DecimalFormat;", "getDECIMAL_FORMAT", "()Ljava/text/DecimalFormat;", "HOUR_FORMAT", "getHOUR_FORMAT", "Y_FORMAT", "getY_FORMAT", "defaultClient", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Text;", "LiquidSense"}
   )
   public static final class Companion {
      @NotNull
      public final DecimalFormat getY_FORMAT() {
         return Text.Y_FORMAT;
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker lIIllIIlIIlllIl) {
         this();
      }

      @NotNull
      public final SimpleDateFormat getHOUR_FORMAT() {
         return Text.HOUR_FORMAT;
      }

      private Companion() {
      }

      @NotNull
      public final SimpleDateFormat getDATE_FORMAT() {
         return Text.DATE_FORMAT;
      }

      @NotNull
      public final DecimalFormat getDECIMAL_FORMAT() {
         return Text.DECIMAL_FORMAT;
      }

      @NotNull
      public final Text defaultClient() {
         Text lIIllIIlIlIIlIl = new Text(2.0D, 2.0D, 1.0F, (Side)null, 8, (DefaultConstructorMarker)null);
         lIIllIIlIlIIlIl.displayString.set("%clientname%");
         lIIllIIlIlIIlIl.shadow.set(true);
         FontValue var10000 = lIIllIIlIlIIlIl.fontValue;
         FontRenderer var10001 = Fonts.minecraftFont;
         Intrinsics.checkExpressionValueIsNotNull(var10001, "Fonts.minecraftFont");
         var10000.set(var10001);
         lIIllIIlIlIIlIl.setColor(new Color(200, 50, 50));
         boolean var2 = false;
         return lIIllIIlIlIIlIl;
      }
   }
}
