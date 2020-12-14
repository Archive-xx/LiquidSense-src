//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.ui.client.hud.element.elements;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.ui.font.AWTFontRenderer;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.ui.font.GameFontRenderer;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FontValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B-\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\b\u0010\u000e\u001a\u00020\u000fH\u0016R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Effects;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "x", "", "y", "scale", "", "side", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side;", "(DDFLnet/ccbluex/liquidbounce/ui/client/hud/element/Side;)V", "fontValue", "Lnet/ccbluex/liquidbounce/value/FontValue;", "shadow", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "LiquidSense"}
)
@ElementInfo(
   name = "Effects"
)
public final class Effects extends Element {
   // $FF: synthetic field
   private final BoolValue shadow;
   // $FF: synthetic field
   private final FontValue fontValue;

   @NotNull
   public Border drawElement() {
      Exception lIlIIIlIIIIIIlI = 0.0F;
      boolean lIlIIIlIIIIIIIl = 0.0F;
      FontRenderer lIlIIIlIIIIIlll = (FontRenderer)lIlIIIlIIIIIlII.fontValue.get();
      AWTFontRenderer.Companion.setAssumeNonVolatile(true);
      EntityPlayerSP var10000 = access$getMc$p$s1046033730().thePlayer;
      Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");

      for(Iterator lIlIIIIlllllllI = var10000.getActivePotionEffects().iterator(); lIlIIIIlllllllI.hasNext(); lIlIIIlIIIIIIlI -= (float)lIlIIIlIIIIIlll.FONT_HEIGHT) {
         float lIlIIIIllllllll = (PotionEffect)lIlIIIIlllllllI.next();
         Potion[] var10 = Potion.potionTypes;
         Intrinsics.checkExpressionValueIsNotNull(lIlIIIIllllllll, "effect");
         long lIlIIIIllllllIl = var10[lIlIIIIllllllll.getPotionID()];
         float lIlIIIIllllllII = lIlIIIIllllllll.getAmplifier() == 1 ? "II" : (lIlIIIIllllllll.getAmplifier() == 2 ? "III" : (lIlIIIIllllllll.getAmplifier() == 3 ? "IV" : (lIlIIIIllllllll.getAmplifier() == 4 ? "V" : (lIlIIIIllllllll.getAmplifier() == 5 ? "VI" : (lIlIIIIllllllll.getAmplifier() == 6 ? "VII" : (lIlIIIIllllllll.getAmplifier() == 7 ? "VIII" : (lIlIIIIllllllll.getAmplifier() == 8 ? "IX" : (lIlIIIIllllllll.getAmplifier() == 9 ? "X" : (lIlIIIIllllllll.getAmplifier() > 10 ? "X+" : "I")))))))));
         StringBuilder var11 = new StringBuilder();
         Intrinsics.checkExpressionValueIsNotNull(lIlIIIIllllllIl, "potion");
         String lIlIIIlIIIIlIll = String.valueOf(var11.append(I18n.format(lIlIIIIllllllIl.getName(), new Object[0])).append(' ').append(lIlIIIIllllllII).append("§f: §7").append(Potion.getDurationString(lIlIIIIllllllll)));
         float lIlIIIlIIIIllII = (float)lIlIIIlIIIIIlll.getStringWidth(lIlIIIlIIIIlIll);
         if (lIlIIIlIIIIIIIl < lIlIIIlIIIIllII) {
            lIlIIIlIIIIIIIl = lIlIIIlIIIIllII;
         }

         lIlIIIlIIIIIlll.drawString(lIlIIIlIIIIlIll, -lIlIIIlIIIIllII, lIlIIIlIIIIIIlI, lIlIIIIllllllIl.getLiquidColor(), (Boolean)lIlIIIlIIIIIlII.shadow.get());
         boolean var10001 = false;
      }

      AWTFontRenderer.Companion.setAssumeNonVolatile(false);
      if (lIlIIIlIIIIIIIl == 0.0F) {
         lIlIIIlIIIIIIIl = 40.0F;
      }

      if (lIlIIIlIIIIIIlI == 0.0F) {
         lIlIIIlIIIIIIlI = -10.0F;
      }

      return new Border(2.0F, (float)lIlIIIlIIIIIlll.FONT_HEIGHT, -lIlIIIlIIIIIIIl - 2.0F, lIlIIIlIIIIIIlI + (float)lIlIIIlIIIIIlll.FONT_HEIGHT - 2.0F);
   }

   public Effects(double lIlIIIIllllIIll, double lIlIIIIlllIllIl, float lIlIIIIllllIIIl, @NotNull Side lIlIIIIlllIlIll) {
      Intrinsics.checkParameterIsNotNull(lIlIIIIlllIlIll, "side");
      super(lIlIIIIllllIIll, lIlIIIIlllIllIl, lIlIIIIllllIIIl, lIlIIIIlllIlIll);
      GameFontRenderer var10004 = Fonts.font35;
      Intrinsics.checkExpressionValueIsNotNull(var10004, "Fonts.font35");
      lIlIIIIlllIllll.fontValue = new FontValue("Font", (FontRenderer)var10004);
      lIlIIIIlllIllll.shadow = new BoolValue("Shadow", true);
   }

   // $FF: synthetic method
   public Effects(double var1, double var3, float var5, Side var6, int lIlIIIIllIlllll, DefaultConstructorMarker var8) {
      if ((lIlIIIIllIlllll & 1) != 0) {
         var1 = 2.0D;
      }

      if ((lIlIIIIllIlllll & 2) != 0) {
         var3 = 10.0D;
      }

      if ((lIlIIIIllIlllll & 4) != 0) {
         var5 = 1.0F;
      }

      if ((lIlIIIIllIlllll & 8) != 0) {
         var6 = new Side(Side.Horizontal.RIGHT, Side.Vertical.DOWN);
      }

      this(var1, var3, var5, var6);
   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   public Effects() {
      this(0.0D, 0.0D, 0.0F, (Side)null, 15, (DefaultConstructorMarker)null);
   }
}
