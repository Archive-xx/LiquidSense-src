//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.ui.client.hud.element.elements;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.awt.Color;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.features.module.modules.render.NoScoreboard;
import net.ccbluex.liquidbounce.ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FontValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.EnumChatFormatting;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B-\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\b\u0010\u001d\u001a\u00020\u001eH\u0002J\n\u0010\u001f\u001a\u0004\u0018\u00010 H\u0016J\b\u0010!\u001a\u00020\u001eH\u0002R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\""},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/ScoreboardElement;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "x", "", "y", "scale", "", "side", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side;", "(DDFLnet/ccbluex/liquidbounce/ui/client/hud/element/Side;)V", "backgroundColorAlphaValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "backgroundColorBlueValue", "backgroundColorGreenValue", "backgroundColorRedValue", "fontValue", "Lnet/ccbluex/liquidbounce/value/FontValue;", "rectColorBlueAlpha", "rectColorBlueValue", "rectColorGreenValue", "rectColorModeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "rectColorRedValue", "rectValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "shadowValue", "textBlueValue", "textGreenValue", "textRedValue", "backgroundColor", "Ljava/awt/Color;", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "textColor", "LiquidSense"}
)
@ElementInfo(
   name = "Scoreboard",
   force = true
)
public final class ScoreboardElement extends Element {
   // $FF: synthetic field
   private final BoolValue rectValue;
   // $FF: synthetic field
   private final BoolValue shadowValue;
   // $FF: synthetic field
   private final IntegerValue textRedValue;
   // $FF: synthetic field
   private final ListValue rectColorModeValue;
   // $FF: synthetic field
   private final IntegerValue textBlueValue;
   // $FF: synthetic field
   private final IntegerValue textGreenValue;
   // $FF: synthetic field
   private final FontValue fontValue;
   // $FF: synthetic field
   private final IntegerValue backgroundColorGreenValue;
   // $FF: synthetic field
   private final IntegerValue rectColorGreenValue;
   // $FF: synthetic field
   private final IntegerValue backgroundColorBlueValue;
   // $FF: synthetic field
   private final IntegerValue rectColorBlueValue;
   // $FF: synthetic field
   private final IntegerValue backgroundColorAlphaValue;
   // $FF: synthetic field
   private final IntegerValue rectColorRedValue;
   // $FF: synthetic field
   private final IntegerValue rectColorBlueAlpha;
   // $FF: synthetic field
   private final IntegerValue backgroundColorRedValue;

   // $FF: synthetic method
   public ScoreboardElement(double var1, double var3, float var5, Side var6, int llIIlIlIllIIII, DefaultConstructorMarker var8) {
      if ((llIIlIlIllIIII & 1) != 0) {
         var1 = 5.0D;
      }

      if ((llIIlIlIllIIII & 2) != 0) {
         var3 = 0.0D;
      }

      if ((llIIlIlIllIIII & 4) != 0) {
         var5 = 1.0F;
      }

      if ((llIIlIlIllIIII & 8) != 0) {
         var6 = new Side(Side.Horizontal.RIGHT, Side.Vertical.MIDDLE);
      }

      this(var1, var3, var5, var6);
   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   @Nullable
   public Border drawElement() {
      if (NoScoreboard.INSTANCE.getState()) {
         return null;
      } else {
         double llIIlIllllIIII = (FontRenderer)llIIlIllllIIlI.fontValue.get();
         int llIIlIllllIlII = llIIlIllllIIlI.textColor().getRGB();
         short llIIlIlllIlllI = llIIlIllllIIlI.backgroundColor().getRGB();
         String llIIlIllllIllI = (String)llIIlIllllIIlI.rectColorModeValue.get();
         int llIIlIllllIlll = (new Color(((Number)llIIlIllllIIlI.rectColorRedValue.get()).intValue(), ((Number)llIIlIllllIIlI.rectColorGreenValue.get()).intValue(), ((Number)llIIlIllllIIlI.rectColorBlueValue.get()).intValue(), ((Number)llIIlIllllIIlI.rectColorBlueAlpha.get()).intValue())).getRGB();
         WorldClient var10000 = access$getMc$p$s1046033730().theWorld;
         Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.theWorld");
         Scoreboard var37 = var10000.getScoreboard();
         Intrinsics.checkExpressionValueIsNotNull(var37, "mc.theWorld.scoreboard");
         Scoreboard llIIlIlllllIII = var37;
         boolean llIIlIlllIlIlI = (ScoreObjective)null;
         EntityPlayerSP var10001 = access$getMc$p$s1046033730().thePlayer;
         Intrinsics.checkExpressionValueIsNotNull(var10001, "mc.thePlayer");
         float llIIlIlllIlIIl = llIIlIlllllIII.getPlayersTeam(var10001.getName());
         if (llIIlIlllIlIIl != null) {
            EnumChatFormatting var39 = llIIlIlllIlIIl.getChatFormat();
            Intrinsics.checkExpressionValueIsNotNull(var39, "playerTeam.chatFormat");
            long llIIlIlllIlIII = var39.getColorIndex();
            if (llIIlIlllIlIII >= 0) {
               llIIlIlllIlIlI = llIIlIlllllIII.getObjectiveInDisplaySlot(3 + llIIlIlllIlIII);
            }
         }

         ScoreObjective var40 = llIIlIlllIlIlI;
         boolean var41;
         if (llIIlIlllIlIlI == null) {
            var41 = false;
            var40 = llIIlIlllllIII.getObjectiveInDisplaySlot(1);
         }

         if (var40 == null) {
            var41 = false;
            return null;
         } else {
            long llIIlIlllIlIII = var40;
            var37 = llIIlIlllIlIII.getScoreboard();
            Intrinsics.checkExpressionValueIsNotNull(var37, "objective.scoreboard");
            Scoreboard llIIlIllllllII = var37;
            byte llIIlIlllIIllI = llIIlIllllllII.getSortedScores(llIIlIlllIlIII);
            double llIIlIlllIIlIl = Lists.newArrayList(Iterables.filter((Iterable)llIIlIlllIIllI, (Predicate)null.INSTANCE));
            llIIlIlllIIllI = llIIlIlllIIlIl.size() > 15 ? (Collection)Lists.newArrayList(Iterables.skip((Iterable)llIIlIlllIIlIl, llIIlIlllIIllI.size() - 15)) : (Collection)llIIlIlllIIlIl;
            int llIIlIllllllll = llIIlIllllIIII.getStringWidth(llIIlIlllIlIII.getDisplayName());

            String llIIlIlllIIIII;
            for(Iterator llIIlIlllIIIlI = llIIlIlllIIllI.iterator(); llIIlIlllIIIlI.hasNext(); llIIlIllllllll = RangesKt.coerceAtLeast(llIIlIllllllll, llIIlIllllIIII.getStringWidth(llIIlIlllIIIII))) {
               Score llIIllIIIlIIII = (Score)llIIlIlllIIIlI.next();
               Intrinsics.checkExpressionValueIsNotNull(llIIllIIIlIIII, "score");
               long llIIlIlllIIIIl = llIIlIllllllII.getPlayersTeam(llIIllIIIlIIII.getPlayerName());
               llIIlIlllIIIII = String.valueOf((new StringBuilder()).append(ScorePlayerTeam.formatPlayerName((Team)llIIlIlllIIIIl, llIIllIIIlIIII.getPlayerName())).append(": ").append(EnumChatFormatting.RED).append(llIIllIIIlIIII.getScorePoints()));
            }

            int llIIllIIIIIIII = llIIlIlllIIllI.size() * llIIlIllllIIII.FONT_HEIGHT;
            short llIIlIlllIIIlI = -llIIlIllllllll - 3 - ((Boolean)llIIlIllllIIlI.rectValue.get() ? 3 : 0);
            Gui.drawRect(llIIlIlllIIIlI - 2, -2, 5, llIIllIIIIIIII + llIIlIllllIIII.FONT_HEIGHT, llIIlIlllIlllI);
            Intrinsics.checkExpressionValueIsNotNull(llIIlIlllIIllI, "scoreCollection");
            long llIIlIlllIIIIl = (Iterable)llIIlIlllIIllI;
            long llIIlIlllIIIII = false;
            short llIIlIllIlllll = 0;
            Iterator llIIlIllIllllI = llIIlIlllIIIIl.iterator();

            while(llIIlIllIllllI.hasNext()) {
               boolean llIIlIllIlllIl = llIIlIllIllllI.next();
               short llIIlIllIlllII = llIIlIllIlllll++;
               double llIIlIllIllIll = false;
               if (llIIlIllIlllII < 0) {
                  CollectionsKt.throwIndexOverflow();
               }

               Score llIIllIIIIIlll = (Score)llIIlIllIlllIl;
               double llIIlIllIlIlll = false;
               Intrinsics.checkExpressionValueIsNotNull(llIIllIIIIIlll, "score");
               char llIIlIllIlIllI = llIIlIllllllII.getPlayersTeam(llIIllIIIIIlll.getPlayerName());
               String llIIllIIIIlIlI = ScorePlayerTeam.formatPlayerName((Team)llIIlIllIlIllI, llIIllIIIIIlll.getPlayerName());
               boolean llIIlIllIlIlII = String.valueOf((new StringBuilder()).append(EnumChatFormatting.RED).append(llIIllIIIIIlll.getScorePoints()));
               char llIIlIllIlIIll = 5 - ((Boolean)llIIlIllllIIlI.rectValue.get() ? 4 : 0);
               int llIIlIllIlIIlI = llIIllIIIIIIII - llIIlIllIlllII * llIIlIllllIIII.FONT_HEIGHT;
               GlStateManager.resetColor();
               llIIlIllllIIII.drawString(llIIllIIIIlIlI, (float)llIIlIlllIIIlI, (float)llIIlIllIlIIlI, llIIlIllllIlII, (Boolean)llIIlIllllIIlI.shadowValue.get());
               var41 = false;
               llIIlIllllIIII.drawString(llIIlIllIlIlII, (float)(llIIlIllIlIIll - llIIlIllllIIII.getStringWidth(llIIlIllIlIlII)), (float)llIIlIllIlIIlI, llIIlIllllIlII, (Boolean)llIIlIllllIIlI.shadowValue.get());
               var41 = false;
               if (llIIlIllIlllII == llIIlIlllIIllI.size() - 1) {
                  String llIIllIIIIllll = llIIlIlllIlIII.getDisplayName();
                  GlStateManager.resetColor();
                  llIIlIllllIIII.drawString(llIIllIIIIllll, (float)(llIIlIlllIIIlI + llIIlIllllllll / 2 - llIIlIllllIIII.getStringWidth(llIIllIIIIllll) / 2), (float)(llIIlIllIlIIlI - llIIlIllllIIII.FONT_HEIGHT), llIIlIllllIlII, (Boolean)llIIlIllllIIlI.shadowValue.get());
                  var41 = false;
               }

               if ((Boolean)llIIlIllllIIlI.rectValue.get()) {
                  int llIIllIIIIlllI = StringsKt.equals(llIIlIllllIllI, "Rainbow", true) ? ColorUtils.rainbow(400000000L * (long)llIIlIllIlllII).getRGB() : llIIlIllllIlll;
                  RenderUtils.drawRect(2.0F, llIIlIllIlllII == llIIlIlllIIllI.size() - 1 ? -2.0F : (float)llIIlIllIlIIlI, 5.0F, llIIlIllIlllII == 0 ? (float)llIIlIllllIIII.FONT_HEIGHT : (float)llIIlIllIlIIlI + (float)llIIlIllllIIII.FONT_HEIGHT * 2.0F, llIIllIIIIlllI);
               }
            }

            return new Border(-((float)llIIlIllllllll) - (float)5 - (float)((Boolean)llIIlIllllIIlI.rectValue.get() ? 3 : 0), -2.0F, 5.0F, (float)llIIllIIIIIIII + (float)llIIlIllllIIII.FONT_HEIGHT);
         }
      }
   }

   public ScoreboardElement() {
      this(0.0D, 0.0D, 0.0F, (Side)null, 15, (DefaultConstructorMarker)null);
   }

   private final Color textColor() {
      return new Color(((Number)llIIlIllIIllII.textRedValue.get()).intValue(), ((Number)llIIlIllIIllII.textGreenValue.get()).intValue(), ((Number)llIIlIllIIllII.textBlueValue.get()).intValue());
   }

   private final Color backgroundColor() {
      return new Color(((Number)llIIlIllIIlllI.backgroundColorRedValue.get()).intValue(), ((Number)llIIlIllIIlllI.backgroundColorGreenValue.get()).intValue(), ((Number)llIIlIllIIlllI.backgroundColorBlueValue.get()).intValue(), ((Number)llIIlIllIIlllI.backgroundColorAlphaValue.get()).intValue());
   }

   public ScoreboardElement(double llIIlIlIllllll, double llIIlIlIlllllI, float llIIlIllIIIIlI, @NotNull Side llIIlIllIIIIIl) {
      Intrinsics.checkParameterIsNotNull(llIIlIllIIIIIl, "side");
      super(llIIlIlIllllll, llIIlIlIlllllI, llIIlIllIIIIlI, llIIlIllIIIIIl);
      llIIlIllIIIIII.textRedValue = new IntegerValue("Text-R", 255, 0, 255);
      llIIlIllIIIIII.textGreenValue = new IntegerValue("Text-G", 255, 0, 255);
      llIIlIllIIIIII.textBlueValue = new IntegerValue("Text-B", 255, 0, 255);
      llIIlIllIIIIII.backgroundColorRedValue = new IntegerValue("Background-R", 0, 0, 255);
      llIIlIllIIIIII.backgroundColorGreenValue = new IntegerValue("Background-G", 0, 0, 255);
      llIIlIllIIIIII.backgroundColorBlueValue = new IntegerValue("Background-B", 0, 0, 255);
      llIIlIllIIIIII.backgroundColorAlphaValue = new IntegerValue("Background-Alpha", 95, 0, 255);
      llIIlIllIIIIII.rectValue = new BoolValue("Rect", false);
      llIIlIllIIIIII.rectColorModeValue = new ListValue("Rect-Color", new String[]{"Custom", "Rainbow"}, "Custom");
      llIIlIllIIIIII.rectColorRedValue = new IntegerValue("Rect-R", 0, 0, 255);
      llIIlIllIIIIII.rectColorGreenValue = new IntegerValue("Rect-G", 111, 0, 255);
      llIIlIllIIIIII.rectColorBlueValue = new IntegerValue("Rect-B", 255, 0, 255);
      llIIlIllIIIIII.rectColorBlueAlpha = new IntegerValue("Rect-Alpha", 255, 0, 255);
      llIIlIllIIIIII.shadowValue = new BoolValue("Shadow", false);
      FontRenderer var10004 = Fonts.minecraftFont;
      Intrinsics.checkExpressionValueIsNotNull(var10004, "Fonts.minecraftFont");
      llIIlIllIIIIII.fontValue = new FontValue("Font", var10004);
   }
}
