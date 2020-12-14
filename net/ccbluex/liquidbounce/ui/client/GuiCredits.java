//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.ui.client;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.concurrent.ThreadsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.ui.font.GameFontRenderer;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.misc.HttpUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\f\n\u0002\b\u0005\u0018\u00002\u00020\u0001:\u0002\u001f B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0002\u0010\u0003J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0014J \u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u001a\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0014H\u0002J\b\u0010\u0018\u001a\u00020\nH\u0016J\b\u0010\u0019\u001a\u00020\nH\u0016J\u0018\u0010\u001a\u001a\u00020\n2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u000fH\u0014J\b\u0010\u001e\u001a\u00020\nH\u0002R\u0018\u0010\u0004\u001a\f\u0012\b\u0012\u00060\u0006R\u00020\u00000\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0007\u001a\u00060\bR\u00020\u0000X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006!"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/client/GuiCredits;", "Lnet/minecraft/client/gui/GuiScreen;", "prevGui", "(Lnet/minecraft/client/gui/GuiScreen;)V", "credits", "Ljava/util/ArrayList;", "Lnet/ccbluex/liquidbounce/ui/client/GuiCredits$Credit;", "list", "Lnet/ccbluex/liquidbounce/ui/client/GuiCredits$GuiList;", "actionPerformed", "", "button", "Lnet/minecraft/client/gui/GuiButton;", "drawScreen", "mouseX", "", "mouseY", "partialTicks", "", "getInfoFromJson", "", "jsonObject", "Lcom/google/gson/JsonObject;", "key", "handleMouseInput", "initGui", "keyTyped", "typedChar", "", "keyCode", "loadCredits", "Credit", "GuiList", "LiquidSense"}
)
public final class GuiCredits extends GuiScreen {
   // $FF: synthetic field
   private final GuiScreen prevGui;
   // $FF: synthetic field
   private GuiCredits.GuiList list;
   // $FF: synthetic field
   private final ArrayList<GuiCredits.Credit> credits;

   private final String getInfoFromJson(JsonObject lllllllllllllllllIlllllIlIlllIII, String lllllllllllllllllIlllllIlIlllIIl) {
      String var3;
      if (lllllllllllllllllIlllllIlIlllIII.has(lllllllllllllllllIlllllIlIlllIIl)) {
         JsonElement var10000 = lllllllllllllllllIlllllIlIlllIII.get(lllllllllllllllllIlllllIlIlllIIl);
         Intrinsics.checkExpressionValueIsNotNull(var10000, "jsonObject.get(key)");
         var3 = var10000.getAsString();
      } else {
         var3 = null;
      }

      return var3;
   }

   protected void keyTyped(char lllllllllllllllllIlllllIlllIIlIl, int lllllllllllllllllIlllllIlllIIlII) {
      if (1 == lllllllllllllllllIlllllIlllIIlII) {
         lllllllllllllllllIlllllIlllIIIll.mc.displayGuiScreen(lllllllllllllllllIlllllIlllIIIll.prevGui);
      } else {
         super.keyTyped(lllllllllllllllllIlllllIlllIIlIl, lllllllllllllllllIlllllIlllIIlII);
      }
   }

   public void handleMouseInput() {
      super.handleMouseInput();
      GuiCredits.GuiList var10000 = lllllllllllllllllIlllllIllIlllll.list;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("list");
      }

      var10000.handleMouseInput();
   }

   public void drawScreen(int lllllllllllllllllIlllllIllllIllI, int lllllllllllllllllIlllllIllllIlIl, float lllllllllllllllllIlllllIllllIlII) {
      lllllllllllllllllIlllllIllllIlll.drawBackground(0);
      GuiCredits.GuiList var10000 = lllllllllllllllllIlllllIllllIlll.list;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("list");
      }

      var10000.drawScreen(lllllllllllllllllIlllllIllllIllI, lllllllllllllllllIlllllIllllIlIl, lllllllllllllllllIlllllIllllIlII);
      Gui.drawRect(lllllllllllllllllIlllllIllllIlll.width / 4, 40, lllllllllllllllllIlllllIllllIlll.width, lllllllllllllllllIlllllIllllIlll.height - 40, Integer.MIN_VALUE);
      var10000 = lllllllllllllllllIlllllIllllIlll.list;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("list");
      }

      boolean var12;
      if (var10000.getSelectedSlot$LiquidSense() != -1) {
         ArrayList var8 = lllllllllllllllllIlllllIllllIlll.credits;
         GuiCredits.GuiList var10001 = lllllllllllllllllIlllllIllllIlll.list;
         if (var10001 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("list");
         }

         Object var9 = var8.get(var10001.getSelectedSlot$LiquidSense());
         Intrinsics.checkExpressionValueIsNotNull(var9, "credits[list.getSelectedSlot()]");
         long lllllllllllllllllIlllllIllllIIll = (GuiCredits.Credit)var9;
         int lllllllllllllllllIlllllIllllllIl = 45;
         GameFontRenderer var10 = Fonts.font40;
         String var11 = String.valueOf((new StringBuilder()).append("Name: ").append(lllllllllllllllllIlllllIllllIIll.getName()));
         float var10002 = (float)(lllllllllllllllllIlllllIllllIlll.width / 4 + 5);
         float var10003 = (float)lllllllllllllllllIlllllIllllllIl;
         Color var10004 = Color.WHITE;
         Intrinsics.checkExpressionValueIsNotNull(var10004, "Color.WHITE");
         var10.drawString(var11, var10002, var10003, var10004.getRGB(), true);
         var12 = false;
         if (lllllllllllllllllIlllllIllllIIll.getTwitterName() != null) {
            lllllllllllllllllIlllllIllllllIl += Fonts.font40.FONT_HEIGHT;
            var10 = Fonts.font40;
            var11 = String.valueOf((new StringBuilder()).append("Twitter: ").append(lllllllllllllllllIlllllIllllIIll.getTwitterName()));
            var10002 = (float)(lllllllllllllllllIlllllIllllIlll.width / 4 + 5);
            var10003 = (float)lllllllllllllllllIlllllIllllllIl;
            var10004 = Color.WHITE;
            Intrinsics.checkExpressionValueIsNotNull(var10004, "Color.WHITE");
            var10.drawString(var11, var10002, var10003, var10004.getRGB(), true);
            var12 = false;
         }

         if (lllllllllllllllllIlllllIllllIIll.getYoutubeName() != null) {
            lllllllllllllllllIlllllIllllllIl += Fonts.font40.FONT_HEIGHT;
            var10 = Fonts.font40;
            var11 = String.valueOf((new StringBuilder()).append("YouTube: ").append(lllllllllllllllllIlllllIllllIIll.getYoutubeName()));
            var10002 = (float)(lllllllllllllllllIlllllIllllIlll.width / 4 + 5);
            var10003 = (float)lllllllllllllllllIlllllIllllllIl;
            var10004 = Color.WHITE;
            Intrinsics.checkExpressionValueIsNotNull(var10004, "Color.WHITE");
            var10.drawString(var11, var10002, var10003, var10004.getRGB(), true);
            var12 = false;
         }

         lllllllllllllllllIlllllIllllllIl += Fonts.font40.FONT_HEIGHT;

         for(Iterator lllllllllllllllllIlllllIllllIIII = lllllllllllllllllIlllllIllllIIll.getCredits().iterator(); lllllllllllllllllIlllllIllllIIII.hasNext(); var12 = false) {
            double lllllllllllllllllIlllllIllllIIIl = (String)lllllllllllllllllIlllllIllllIIII.next();
            lllllllllllllllllIlllllIllllllIl += Fonts.font40.FONT_HEIGHT;
            var10 = Fonts.font40;
            var10002 = (float)(lllllllllllllllllIlllllIllllIlll.width / 4 + 5);
            var10003 = (float)lllllllllllllllllIlllllIllllllIl;
            var10004 = Color.WHITE;
            Intrinsics.checkExpressionValueIsNotNull(var10004, "Color.WHITE");
            var10.drawString(lllllllllllllllllIlllllIllllIIIl, var10002, var10003, var10004.getRGB(), true);
         }
      }

      Fonts.font40.drawCenteredString("Credits", (float)lllllllllllllllllIlllllIllllIlll.width / 2.0F, 6.0F, 16777215);
      var12 = false;
      if (lllllllllllllllllIlllllIllllIlll.credits.isEmpty()) {
         FontRenderer var13 = (FontRenderer)Fonts.font40;
         int var14 = lllllllllllllllllIlllllIllllIlll.width / 8;
         int var15 = lllllllllllllllllIlllllIllllIlll.height / 2;
         Color var10005 = Color.WHITE;
         Intrinsics.checkExpressionValueIsNotNull(var10005, "Color.WHITE");
         lllllllllllllllllIlllllIllllIlll.drawCenteredString(var13, "Loading...", var14, var15, var10005.getRGB());
         RenderUtils.drawLoadingCircle((float)(lllllllllllllllllIlllllIllllIlll.width / 8), (float)(lllllllllllllllllIlllllIllllIlll.height / 2 - 40));
      }

      super.drawScreen(lllllllllllllllllIlllllIllllIllI, lllllllllllllllllIlllllIllllIlIl, lllllllllllllllllIlllllIllllIlII);
   }

   public void initGui() {
      lllllllllllllllllIllllllIIIIIlll.list = new GuiCredits.GuiList((GuiScreen)lllllllllllllllllIllllllIIIIIlll);
      GuiCredits.GuiList var10000 = lllllllllllllllllIllllllIIIIIlll.list;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("list");
      }

      var10000.registerScrollButtons(7, 8);
      var10000 = lllllllllllllllllIllllllIIIIIlll.list;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("list");
      }

      var10000.elementClicked(-1, false, 0, 0);
      lllllllllllllllllIllllllIIIIIlll.buttonList.add(new GuiButton(1, lllllllllllllllllIllllllIIIIIlll.width / 2 - 100, lllllllllllllllllIllllllIIIIIlll.height - 30, "Back"));
      boolean var10001 = false;
      ThreadsKt.thread$default(false, false, (ClassLoader)null, (String)null, 0, (Function0)(new Function0<Unit>() {
         public final void invoke() {
            lllllllllllllllllIllllllIIIIIlll.loadCredits();
         }
      }), 31, (Object)null);
      var10001 = false;
   }

   private final void loadCredits() {
      lllllllllllllllllIlllllIllIIlIII.credits.clear();

      try {
         JsonElement lllllllllllllllllIlllllIllIIlIll = (new JsonParser()).parse(HttpUtils.get("https://cloud.liquidbounce.net/LiquidBounce/credits.json"));
         if (lllllllllllllllllIlllllIllIIlIll instanceof JsonArray) {
            Iterator lllllllllllllllllIlllllIllIIIlIl = ((JsonArray)lllllllllllllllllIlllllIllIIlIll).iterator();

            while(true) {
               JsonElement lllllllllllllllllIlllllIllIIIllI;
               do {
                  if (!lllllllllllllllllIlllllIllIIIlIl.hasNext()) {
                     return;
                  }

                  lllllllllllllllllIlllllIllIIIllI = (JsonElement)lllllllllllllllllIlllllIllIIIlIl.next();
               } while(!(lllllllllllllllllIlllllIllIIIllI instanceof JsonObject));

               long lllllllllllllllllIlllllIllIIIlII = new ArrayList();
               JsonElement var10000 = ((JsonObject)lllllllllllllllllIlllllIllIIIllI).get("Credits");
               Intrinsics.checkExpressionValueIsNotNull(var10000, "value.get(\"Credits\")");
               Set var13 = var10000.getAsJsonObject().entrySet();
               Intrinsics.checkExpressionValueIsNotNull(var13, "value.get(\"Credits\").asJ…              .entrySet()");
               Iterable lllllllllllllllllIlllllIllIIllll = (Iterable)var13;
               Exception lllllllllllllllllIlllllIllIIIIlI = false;

               boolean var15;
               for(Iterator lllllllllllllllllIlllllIllIIIIIl = lllllllllllllllllIlllllIllIIllll.iterator(); lllllllllllllllllIlllllIllIIIIIl.hasNext(); var15 = false) {
                  long lllllllllllllllllIlllllIllIIIIII = lllllllllllllllllIlllllIllIIIIIl.next();
                  Entry lllllllllllllllllIlllllIllIlIIlI = (Entry)lllllllllllllllllIlllllIllIIIIII;
                  int lllllllllllllllllIlllllIllIlIIIl = false;
                  Object var10001 = lllllllllllllllllIlllllIllIlIIlI.getValue();
                  Intrinsics.checkExpressionValueIsNotNull(var10001, "stringJsonElementEntry.value");
                  lllllllllllllllllIlllllIllIIIlII.add(((JsonElement)var10001).getAsString());
               }

               ArrayList var14 = lllllllllllllllllIlllllIllIIlIII.credits;
               GuiCredits.Credit var16 = new GuiCredits.Credit;
               String var10004 = lllllllllllllllllIlllllIllIIlIII.getInfoFromJson((JsonObject)lllllllllllllllllIlllllIllIIIllI, "Name");
               if (var10004 == null) {
                  Intrinsics.throwNpe();
               }

               var16.<init>(var10004, lllllllllllllllllIlllllIllIIlIII.getInfoFromJson((JsonObject)lllllllllllllllllIlllllIllIIIllI, "TwitterName"), lllllllllllllllllIlllllIllIIlIII.getInfoFromJson((JsonObject)lllllllllllllllllIlllllIllIIIllI, "YouTubeName"), (List)lllllllllllllllllIlllllIllIIIlII);
               var14.add(var16);
               var15 = false;
            }
         }
      } catch (Exception var12) {
         ClientUtils.getLogger().error("Failed to load credits.", (Throwable)var12);
      }
   }

   protected void actionPerformed(@NotNull GuiButton lllllllllllllllllIlllllIlllIlIlI) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllIlllllIlllIlIlI, "button");
      if (lllllllllllllllllIlllllIlllIlIlI.id == 1) {
         lllllllllllllllllIlllllIlllIllIl.mc.displayGuiScreen(lllllllllllllllllIlllllIlllIllIl.prevGui);
      }

   }

   public GuiCredits(@NotNull GuiScreen lllllllllllllllllIlllllIlIllIIIl) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllIlllllIlIllIIIl, "prevGui");
      super();
      lllllllllllllllllIlllllIlIllIIlI.prevGui = lllllllllllllllllIlllllIlIllIIIl;
      lllllllllllllllllIlllllIlIllIIlI.credits = new ArrayList();
   }

   @Metadata(
      mv = {1, 1, 16},
      bv = {1, 0, 3},
      k = 1,
      d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\b\b\u0082\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0014J8\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u0006H\u0014J(\u0010\u0010\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00062\u0006\u0010\u0015\u001a\u00020\u0006H\u0016J\r\u0010\u0016\u001a\u00020\u0006H\u0000¢\u0006\u0002\b\u0017J\b\u0010\u0018\u001a\u00020\u0006H\u0014J\u0010\u0010\u0019\u001a\u00020\u00132\u0006\u0010\u001a\u001a\u00020\u0006H\u0014R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001b"},
      d2 = {"Lnet/ccbluex/liquidbounce/ui/client/GuiCredits$GuiList;", "Lnet/minecraft/client/gui/GuiSlot;", "gui", "Lnet/minecraft/client/gui/GuiScreen;", "(Lnet/ccbluex/liquidbounce/ui/client/GuiCredits;Lnet/minecraft/client/gui/GuiScreen;)V", "selectedSlot", "", "drawBackground", "", "drawSlot", "entryID", "p_180791_2_", "p_180791_3_", "p_180791_4_", "mouseXIn", "mouseYIn", "elementClicked", "index", "doubleClick", "", "var3", "var4", "getSelectedSlot", "getSelectedSlot$LiquidSense", "getSize", "isSelected", "id", "LiquidSense"}
   )
   private final class GuiList extends GuiSlot {
      // $FF: synthetic field
      private int selectedSlot;

      protected boolean isSelected(int lllllllllllllllllIlllIllIlllIIII) {
         return lllllllllllllllllIlllIllIlllIIll.selectedSlot == lllllllllllllllllIlllIllIlllIIII;
      }

      protected void drawSlot(int lllllllllllllllllIlllIllIlIllIlI, int lllllllllllllllllIlllIllIlIllIIl, int lllllllllllllllllIlllIllIlIllIII, int lllllllllllllllllIlllIllIlIlIlll, int lllllllllllllllllIlllIllIlIlIllI, int lllllllllllllllllIlllIllIlIlIlIl) {
         Object var10000 = GuiCredits.this.credits.get(lllllllllllllllllIlllIllIlIllIlI);
         Intrinsics.checkExpressionValueIsNotNull(var10000, "credits[entryID]");
         GuiCredits.Credit lllllllllllllllllIlllIllIlIlllII = (GuiCredits.Credit)var10000;
         GameFontRenderer var9 = Fonts.font40;
         String var10001 = lllllllllllllllllIlllIllIlIlllII.getName();
         float var10002 = (float)lllllllllllllllllIlllIllIlIlIlII.width / 2.0F;
         float var10003 = (float)lllllllllllllllllIlllIllIlIllIII + 2.0F;
         Color var10004 = Color.WHITE;
         Intrinsics.checkExpressionValueIsNotNull(var10004, "Color.WHITE");
         var9.drawCenteredString(var10001, var10002, var10003, var10004.getRGB(), true);
         boolean var10 = false;
      }

      public final int getSelectedSlot$LiquidSense() {
         return lllllllllllllllllIlllIllIllIlIlI.selectedSlot > GuiCredits.this.credits.size() ? -1 : lllllllllllllllllIlllIllIllIlIlI.selectedSlot;
      }

      protected int getSize() {
         return GuiCredits.this.credits.size();
      }

      protected void drawBackground() {
      }

      public GuiList(@NotNull GuiScreen lllllllllllllllllIlllIllIlIIIlll) {
         Intrinsics.checkParameterIsNotNull(lllllllllllllllllIlllIllIlIIIlll, "gui");
         super(GuiCredits.this.mc, lllllllllllllllllIlllIllIlIIIlll.width / 4, lllllllllllllllllIlllIllIlIIIlll.height, 40, lllllllllllllllllIlllIllIlIIIlll.height - 40, 15);
      }

      public void elementClicked(int lllllllllllllllllIlllIllIllIIIIl, boolean lllllllllllllllllIlllIllIllIIlIl, int lllllllllllllllllIlllIllIllIIlII, int lllllllllllllllllIlllIllIllIIIll) {
         lllllllllllllllllIlllIllIllIIlll.selectedSlot = lllllllllllllllllIlllIllIllIIIIl;
      }
   }

   @Metadata(
      mv = {1, 1, 16},
      bv = {1, 0, 3},
      k = 1,
      d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\b\b\u0080\u0004\u0018\u00002\u00020\u0001B/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007¢\u0006\u0002\u0010\bR\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\f¨\u0006\u000f"},
      d2 = {"Lnet/ccbluex/liquidbounce/ui/client/GuiCredits$Credit;", "", "name", "", "twitterName", "youtubeName", "credits", "", "(Lnet/ccbluex/liquidbounce/ui/client/GuiCredits;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;)V", "getCredits", "()Ljava/util/List;", "getName", "()Ljava/lang/String;", "getTwitterName", "getYoutubeName", "LiquidSense"}
   )
   public final class Credit {
      // $FF: synthetic field
      @NotNull
      private final List<String> credits;
      // $FF: synthetic field
      @Nullable
      private final String youtubeName;
      // $FF: synthetic field
      @NotNull
      private final String name;
      // $FF: synthetic field
      @Nullable
      private final String twitterName;

      @NotNull
      public final String getName() {
         return lIlIIIIlIIllIII.name;
      }

      @NotNull
      public final List<String> getCredits() {
         return lIlIIIIlIIIllll.credits;
      }

      @Nullable
      public final String getTwitterName() {
         return lIlIIIIlIIlIlIl.twitterName;
      }

      public Credit(@NotNull String lIlIIIIlIIIIllI, @Nullable String lIlIIIIlIIIIlIl, @Nullable String lIlIIIIIllllllI, @NotNull List<String> lIlIIIIIlllllIl) {
         Intrinsics.checkParameterIsNotNull(lIlIIIIlIIIIllI, "name");
         Intrinsics.checkParameterIsNotNull(lIlIIIIIlllllIl, "credits");
         super();
         lIlIIIIlIIIIIlI.name = lIlIIIIlIIIIllI;
         lIlIIIIlIIIIIlI.twitterName = lIlIIIIlIIIIlIl;
         lIlIIIIlIIIIIlI.youtubeName = lIlIIIIIllllllI;
         lIlIIIIlIIIIIlI.credits = lIlIIIIIlllllIl;
      }

      @Nullable
      public final String getYoutubeName() {
         return lIlIIIIlIIlIIlI.youtubeName;
      }
   }
}
