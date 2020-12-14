//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.ui.client;

import com.google.gson.Gson;
import java.awt.Color;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.concurrent.ThreadsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.misc.HttpUtils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\f\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0014J \u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\bH\u0016J\u0018\u0010\u0012\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\rH\u0014J\b\u0010\u0016\u001a\u00020\bH\u0002R\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/client/GuiServerStatus;", "Lnet/minecraft/client/gui/GuiScreen;", "prevGui", "(Lnet/minecraft/client/gui/GuiScreen;)V", "status", "Ljava/util/HashMap;", "", "actionPerformed", "", "button", "Lnet/minecraft/client/gui/GuiButton;", "drawScreen", "mouseX", "", "mouseY", "partialTicks", "", "initGui", "keyTyped", "typedChar", "", "keyCode", "loadInformations", "LiquidSense"}
)
public final class GuiServerStatus extends GuiScreen {
   // $FF: synthetic field
   private final GuiScreen prevGui;
   // $FF: synthetic field
   private final HashMap<String, String> status;

   private final void loadInformations() {
      lIllIIIIIIllIIl.status.clear();

      boolean var10001;
      try {
         Object var10000 = (new Gson()).fromJson(HttpUtils.get("https://status.mojang.com/check"), List.class);
         if (var10000 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.List<kotlin.collections.Map<kotlin.String, kotlin.String>>");
         }

         List lIllIIIIIIlllII = (List)var10000;
         Iterator lIllIIIIIIlIllI = lIllIIIIIIlllII.iterator();

         while(lIllIIIIIIlIllI.hasNext()) {
            Map lIllIIIIIIlllIl = (Map)lIllIIIIIIlIllI.next();
            float lIllIIIIIIlIIlI = false;

            for(Iterator lIllIIIIIIlIlII = lIllIIIIIIlllIl.entrySet().iterator(); lIllIIIIIIlIlII.hasNext(); var10001 = false) {
               Entry lIllIIIIIIllllI = (Entry)lIllIIIIIIlIlII.next();
               ((Map)lIllIIIIIIllIIl.status).put(lIllIIIIIIllllI.getKey(), lIllIIIIIIllllI.getValue());
            }
         }
      } catch (IOException var8) {
         ((Map)lIllIIIIIIllIIl.status).put("status.mojang.com/check", "red");
         var10001 = false;
      }

   }

   protected void keyTyped(char lIllIIIIIIIIlII, int lIllIIIIIIIIllI) {
      if (1 == lIllIIIIIIIIllI) {
         lIllIIIIIIIIlIl.mc.displayGuiScreen(lIllIIIIIIIIlIl.prevGui);
      } else {
         super.keyTyped(lIllIIIIIIIIlII, lIllIIIIIIIIllI);
      }
   }

   protected void actionPerformed(@NotNull GuiButton lIllIIIIIIIllII) {
      Intrinsics.checkParameterIsNotNull(lIllIIIIIIIllII, "button");
      if (lIllIIIIIIIllII.id == 1) {
         lIllIIIIIIIllIl.mc.displayGuiScreen(lIllIIIIIIIllIl.prevGui);
      }

   }

   public void drawScreen(int lIllIIIIIlIllIl, int lIllIIIIIlIllII, float lIllIIIIIlIllll) {
      lIllIIIIIlIlllI.drawBackground(0);
      float lIllIIIIIlIlIlI = lIllIIIIIlIlllI.height / 4 + 40;
      Gui.drawRect(lIllIIIIIlIlllI.width / 2 - 115, lIllIIIIIlIlIlI - 5, lIllIIIIIlIlllI.width / 2 + 115, lIllIIIIIlIlllI.height / 4 + 43 + (lIllIIIIIlIlllI.status.keySet().isEmpty() ? 10 : lIllIIIIIlIlllI.status.keySet().size() * Fonts.font40.FONT_HEIGHT), Integer.MIN_VALUE);
      FontRenderer var10001;
      int var10003;
      Color var10005;
      if (lIllIIIIIlIlllI.status.isEmpty()) {
         var10001 = (FontRenderer)Fonts.font40;
         var10003 = lIllIIIIIlIlllI.width / 2;
         int var10004 = lIllIIIIIlIlllI.height / 4 + 40;
         var10005 = Color.WHITE;
         Intrinsics.checkExpressionValueIsNotNull(var10005, "Color.WHITE");
         lIllIIIIIlIlllI.drawCenteredString(var10001, "Loading...", var10003, var10004, var10005.getRGB());
      } else {
         for(Iterator lIllIIIIIlIlIII = lIllIIIIIlIlllI.status.keySet().iterator(); lIllIIIIIlIlIII.hasNext(); lIllIIIIIlIlIlI += Fonts.font40.FONT_HEIGHT) {
            String lIllIIIIIllIlII = (String)lIllIIIIIlIlIII.next();
            long lIllIIIIIlIIlll = (String)lIllIIIIIlIlllI.status.get(lIllIIIIIllIlII);
            var10001 = (FontRenderer)Fonts.font40;
            String var10002 = String.valueOf((new StringBuilder()).append("§c§l").append(lIllIIIIIllIlII).append(": ").append(StringsKt.equals(lIllIIIIIlIIlll, "red", true) ? "§c" : (StringsKt.equals(lIllIIIIIlIIlll, "yellow", true) ? "§e" : "§a")).append(StringsKt.equals(lIllIIIIIlIIlll, "red", true) ? "Offline" : (StringsKt.equals(lIllIIIIIlIIlll, "yellow", true) ? "Slow" : "Online")));
            var10003 = lIllIIIIIlIlllI.width / 2;
            var10005 = Color.WHITE;
            Intrinsics.checkExpressionValueIsNotNull(var10005, "Color.WHITE");
            lIllIIIIIlIlllI.drawCenteredString(var10001, var10002, var10003, lIllIIIIIlIlIlI, var10005.getRGB());
         }
      }

      Fonts.fontBold180.drawCenteredString("Server Status", (float)lIllIIIIIlIlllI.width / 2.0F, (float)lIllIIIIIlIlllI.height / 8.0F + 5.0F, 4673984, true);
      boolean var9 = false;
      super.drawScreen(lIllIIIIIlIllIl, lIllIIIIIlIllII, lIllIIIIIlIllll);
   }

   public GuiServerStatus(@NotNull GuiScreen lIlIlllllllllll) {
      Intrinsics.checkParameterIsNotNull(lIlIlllllllllll, "prevGui");
      super();
      lIllIIIIIIIIIII.prevGui = lIlIlllllllllll;
      lIllIIIIIIIIIII.status = new HashMap();
   }

   public void initGui() {
      lIllIIIIIlllllI.buttonList.add(new GuiButton(1, lIllIIIIIlllllI.width / 2 - 100, lIllIIIIIlllllI.height / 4 + 145, "Back"));
      boolean var10001 = false;
      ThreadsKt.thread$default(false, false, (ClassLoader)null, (String)null, 0, (Function0)(new Function0<Unit>() {
         public final void invoke() {
            lIllIIIIIlllllI.loadInformations();
         }
      }), 31, (Object)null);
      var10001 = false;
   }
}
