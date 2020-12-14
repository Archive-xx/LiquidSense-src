//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.utils.render;

import java.awt.Color;
import java.util.Random;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatAllowedCharacters;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0015\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0007J*\u0010\f\u001a\u0004\u0018\u00010\t2\u0006\u0010\r\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0007J\u0018\u0010\u0013\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\u0011H\u0007J\u0010\u0010\u0015\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0007J\b\u0010\u0016\u001a\u00020\tH\u0007J\u0010\u0010\u0016\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\u0011H\u0007J\u0010\u0010\u0016\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\u000fH\u0007J\u0010\u0010\u0016\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0007J\u0018\u0010\u0016\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\u0011H\u0007J\u0018\u0010\u0016\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\u000fH\u0007J\u000e\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0018J\u0014\u0010\u001a\u001a\u0004\u0018\u00010\u00182\b\u0010\u001b\u001a\u0004\u0018\u00010\u0018H\u0007J\u0010\u0010\u001c\u001a\u00020\u00182\u0006\u0010\u001d\u001a\u00020\u0018H\u0007R\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u00020\u00078\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u001e"},
   d2 = {"Lnet/ccbluex/liquidbounce/utils/render/ColorUtils;", "", "()V", "COLOR_PATTERN", "Ljava/util/regex/Pattern;", "kotlin.jvm.PlatformType", "hexColors", "", "ALLColor", "Ljava/awt/Color;", "offset", "", "LiquidSlowly", "time", "count", "", "qd", "", "sq", "TwoRainbow", "alpha", "originalrainbow", "rainbow", "randomMagicText", "", "text", "stripColor", "input", "translateAlternateColorCodes", "textToTranslate", "LiquidSense"}
)
public final class ColorUtils {
   // $FF: synthetic field
   @JvmField
   @NotNull
   public static final int[] hexColors;
   // $FF: synthetic field
   private static final Pattern COLOR_PATTERN;
   // $FF: synthetic field
   public static final ColorUtils INSTANCE;

   private ColorUtils() {
   }

   @JvmStatic
   @Nullable
   public static final Color LiquidSlowly(long lIIIlIllIIIlIl, int lIIIlIllIIIlII, float lIIIlIllIIIIll, float lIIIlIlIlllllI) {
      Color lIIIlIllIIIllI = new Color(Color.HSBtoRGB(((float)lIIIlIllIIIlIl + (float)lIIIlIllIIIlII * -3000000.0F) / (float)2 / 1.0E9F, lIIIlIllIIIIll, lIIIlIlIlllllI));
      return new Color((float)lIIIlIllIIIllI.getRed() / 255.0F * (float)1, (float)lIIIlIllIIIllI.getGreen() / 255.0F * (float)1, (float)lIIIlIllIIIllI.getBlue() / 255.0F * (float)1, (float)lIIIlIllIIIllI.getAlpha() / 255.0F);
   }

   static {
      String lIIIlIlIIIlIlI = new ColorUtils();
      INSTANCE = lIIIlIlIIIlIlI;
      COLOR_PATTERN = Pattern.compile("(?i)§[0-9A-FK-OR]");
      hexColors = new int[16];
      float lIIIlIlIIIlIIl = 16;
      float lIIIlIlIIIlIII = false;
      Exception lIIIlIlIIIIlll = false;
      Exception lIIIlIlIIIIlll = 0;

      for(byte lIIIlIlIIIIllI = lIIIlIlIIIlIIl; lIIIlIlIIIIlll < lIIIlIlIIIIllI; ++lIIIlIlIIIIlll) {
         int lIIIlIlIIIlIll = false;
         int lIIIlIlIIIllIl = (lIIIlIlIIIIlll >> 3 & 1) * 85;
         int lIIIlIlIIIlllI = (lIIIlIlIIIIlll >> 2 & 1) * 170 + lIIIlIlIIIllIl + (lIIIlIlIIIIlll == 6 ? 85 : 0);
         int lIIIlIlIIIllll = (lIIIlIlIIIIlll >> 1 & 1) * 170 + lIIIlIlIIIllIl;
         int lIIIlIlIIlIIII = (lIIIlIlIIIIlll & 1) * 170 + lIIIlIlIIIllIl;
         hexColors[lIIIlIlIIIIlll] = (lIIIlIlIIIlllI & 255) << 16 | (lIIIlIlIIIllll & 255) << 8 | lIIIlIlIIlIIII & 255;
      }

   }

   @JvmStatic
   @NotNull
   public static final Color rainbow(int lIIIlIlIlllIII) {
      return rainbow(400000L, lIIIlIlIlllIII / 255);
   }

   @JvmStatic
   @NotNull
   public static final Color originalrainbow(long lIIIlIllIIlllI) {
      char lIIIlIllIIllII = new Color(Color.HSBtoRGB((float)(System.nanoTime() + lIIIlIllIIlllI) / 1.0E10F % (float)1, 1.0F, 1.0F));
      return new Color((float)lIIIlIllIIllII.getRed() / 255.0F * 1.0F, (float)lIIIlIllIIllII.getGreen() / 255.0F * 1.0F, (float)lIIIlIllIIllII.getBlue() / 255.0F * 1.0F, (float)lIIIlIllIIllII.getAlpha() / 255.0F);
   }

   @JvmStatic
   @NotNull
   public static final Color TwoRainbow(long lIIIlIlIlIIIll, float lIIIlIlIlIIIII) {
      Color lIIIlIlIlIIlII = new Color(Color.HSBtoRGB((float)(System.nanoTime() + lIIIlIlIlIIIll) / 8.9999999E10F % (float)1, 0.75F, 0.8F));
      return new Color((float)lIIIlIlIlIIlII.getRed() / 255.0F * 1.0F, (float)lIIIlIlIlIIlII.getGreen() / 255.0F * 1.0F, (float)lIIIlIlIlIIlII.getBlue() / 255.0F * 1.0F, lIIIlIlIlIIIII);
   }

   @NotNull
   public final String randomMagicText(@NotNull String lIIIlIlllllIIl) {
      Intrinsics.checkParameterIsNotNull(lIIIlIlllllIIl, "text");
      StringBuilder lIIIlIllllllIl = new StringBuilder();
      double lIIIlIllllIIll = "À�?ÂÈÊË�?ÓÔÕÚßãõğİıŒœŞşŴŵžȇ\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗�?╜╛�?└┴┬├─┼╞╟╚╔╩╦╠�?╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌�?▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√�?�²■\u0000";
      int lIIIlIlllIllII = false;
      char[] var10000 = lIIIlIlllllIIl.toCharArray();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).toCharArray()");
      double lIIIlIlllIlllI = var10000;
      int lIIIlIlllIllIl = lIIIlIlllIlllI.length;

      for(int lIIIlIlllIllll = 0; lIIIlIlllIllll < lIIIlIlllIllIl; ++lIIIlIlllIllll) {
         short lIIIlIllllIIIl = lIIIlIlllIlllI[lIIIlIlllIllll];
         if (ChatAllowedCharacters.isAllowedCharacter(lIIIlIllllIIIl)) {
            int lIIIllIIIIIIll = (new Random()).nextInt(lIIIlIllllIIll.length());
            char lIIIlIlllIlIlI = false;
            var10000 = lIIIlIllllIIll.toCharArray();
            Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).toCharArray()");
            short lIIIlIlllIlIII = var10000;
            lIIIlIllllllIl.append(lIIIlIlllIlIII[lIIIllIIIIIIll]);
            boolean var10001 = false;
         }
      }

      String var13 = String.valueOf(lIIIlIllllllIl);
      Intrinsics.checkExpressionValueIsNotNull(var13, "stringBuilder.toString()");
      return var13;
   }

   @JvmStatic
   @NotNull
   public static final Color rainbow(float lIIIlIlIlllIll) {
      return rainbow(400000L, lIIIlIlIlllIll);
   }

   @JvmStatic
   @NotNull
   public static final Color ALLColor(long lIIIlIllIlIIll) {
      Color lIIIlIllIlIlII = new Color(Color.HSBtoRGB((float)((double)Minecraft.getMinecraft().thePlayer.ticksExisted / 50.0D + Math.sin(1.6D) % (double)1), 0.4F, 0.9F));
      return new Color(lIIIlIllIlIlII.getRGB());
   }

   @JvmStatic
   @NotNull
   public static final Color rainbow(long lIIIlIllIllIIl) {
      boolean lIIIlIllIlIlll = new Color(Color.HSBtoRGB((float)(System.nanoTime() + lIIIlIllIllIIl) / 1.0E10F % (float)1, 0.4F, 0.9F));
      return new Color((float)lIIIlIllIlIlll.getRed() / 255.0F * 1.0F, (float)lIIIlIllIlIlll.getGreen() / 255.0F * 1.0F, (float)lIIIlIllIlIlll.getBlue() / 255.0F * 1.0F, (float)lIIIlIllIlIlll.getAlpha() / 255.0F);
   }

   @JvmStatic
   @Nullable
   public static final String stripColor(@Nullable String lIIIllIlIIlllI) {
      Pattern var10000 = COLOR_PATTERN;
      if (lIIIllIlIIlllI != null) {
         return var10000.matcher((CharSequence)lIIIllIlIIlllI).replaceAll("");
      } else {
         boolean var10002 = false;
         return null;
      }
   }

   @JvmStatic
   @NotNull
   public static final Color rainbow(long lIIIlIlIllIlII, int lIIIlIlIllIIll) {
      return rainbow(lIIIlIlIllIlII, (float)lIIIlIlIllIIll / (float)255);
   }

   @JvmStatic
   @NotNull
   public static final Color rainbow(long lIIIlIlIlIllII, float lIIIlIlIlIlIll) {
      Color lIIIlIlIlIllIl = new Color(Color.HSBtoRGB((float)(System.nanoTime() + lIIIlIlIlIllII) / 1.0E10F % (float)1, 0.4F, 0.9F));
      return new Color((float)lIIIlIlIlIllIl.getRed() / 255.0F * 1.0F, (float)lIIIlIlIlIllIl.getGreen() / 255.0F * 1.0F, (float)lIIIlIlIlIllIl.getBlue() / 255.0F * 1.0F, lIIIlIlIlIlIll);
   }

   @JvmStatic
   @NotNull
   public static final Color rainbow() {
      byte lIIIlIlllIIIII = new Color(Color.HSBtoRGB((float)(System.nanoTime() + 400000L) / 1.0E10F % (float)1, 0.4F, 0.9F));
      return new Color((float)lIIIlIlllIIIII.getRed() / 255.0F * 1.0F, (float)lIIIlIlllIIIII.getGreen() / 255.0F * 1.0F, (float)lIIIlIlllIIIII.getBlue() / 255.0F * 1.0F, (float)lIIIlIlllIIIII.getAlpha() / 255.0F);
   }

   @JvmStatic
   @NotNull
   public static final String translateAlternateColorCodes(@NotNull String lIIIllIIlllIll) {
      Intrinsics.checkParameterIsNotNull(lIIIllIIlllIll, "textToTranslate");
      double lIIIllIIllIllI = false;
      char[] var10000 = lIIIllIIlllIll.toCharArray();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).toCharArray()");
      String lIIIllIIlllIlI = var10000;
      float lIIIllIlIIIIIl = 0;

      for(int lIIIllIIllIllI = lIIIllIIlllIlI.length - 1; lIIIllIlIIIIIl < lIIIllIIllIllI; ++lIIIllIlIIIIIl) {
         if (lIIIllIIlllIlI[lIIIllIlIIIIIl] == '&' && StringsKt.contains((CharSequence)"0123456789AaBbCcDdEeFfKkLlMmNnOoRr", lIIIllIIlllIlI[lIIIllIlIIIIIl + 1], true)) {
            lIIIllIIlllIlI[lIIIllIlIIIIIl] = 167;
            lIIIllIIlllIlI[lIIIllIlIIIIIl + 1] = Character.toLowerCase(lIIIllIIlllIlI[lIIIllIlIIIIIl + 1]);
         }
      }

      float lIIIllIIlllIII = false;
      return new String(lIIIllIIlllIlI);
   }
}
