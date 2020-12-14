//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.ui.client.altmanager.sub;

import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.concurrent.ThreadsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.ui.client.altmanager.GuiAltManager;
import net.ccbluex.liquidbounce.ui.elements.GuiPasswordField;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.ui.font.GameFontRenderer;
import net.ccbluex.liquidbounce.utils.misc.MiscUtils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.apache.http.Header;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\f\n\u0002\b\u0007\u0018\u0000 \u001d2\u00020\u0001:\u0001\u001dB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0006H\u0014J \u0010\u000e\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\fH\u0016J\u0018\u0010\u0015\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0010H\u0014J \u0010\u0019\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u001a\u001a\u00020\u0010H\u0014J\b\u0010\u001b\u001a\u00020\fH\u0016J\b\u0010\u001c\u001a\u00020\fH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000¨\u0006\u001e"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/client/altmanager/sub/GuiDonatorCape;", "Lnet/minecraft/client/gui/GuiScreen;", "prevGui", "Lnet/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager;", "(Lnet/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager;)V", "stateButton", "Lnet/minecraft/client/gui/GuiButton;", "status", "", "transferCodeField", "Lnet/minecraft/client/gui/GuiTextField;", "actionPerformed", "", "button", "drawScreen", "mouseX", "", "mouseY", "partialTicks", "", "initGui", "keyTyped", "typedChar", "", "keyCode", "mouseClicked", "mouseButton", "onGuiClosed", "updateScreen", "Companion", "LiquidSense"}
)
public final class GuiDonatorCape extends GuiScreen {
   // $FF: synthetic field
   @NotNull
   private static String transferCode = "";
   // $FF: synthetic field
   private static boolean capeEnabled = true;
   // $FF: synthetic field
   private String status;
   // $FF: synthetic field
   private final GuiAltManager prevGui;
   // $FF: synthetic field
   private GuiTextField transferCodeField;
   // $FF: synthetic field
   public static final GuiDonatorCape.Companion Companion = new GuiDonatorCape.Companion((DefaultConstructorMarker)null);
   // $FF: synthetic field
   private GuiButton stateButton;

   // $FF: synthetic method
   public static final void access$setStateButton$p(GuiDonatorCape lIIIIIIIIlIIlII, GuiButton lIIIIIIIIlIIlIl) {
      lIIIIIIIIlIIlII.stateButton = lIIIIIIIIlIIlIl;
   }

   // $FF: synthetic method
   public static final String access$getStatus$p(GuiDonatorCape lIIIIIIIIllIIlI) {
      return lIIIIIIIIllIIlI.status;
   }

   // $FF: synthetic method
   public static final GuiTextField access$getTransferCodeField$p(GuiDonatorCape lIIIIIIIIllllII) {
      GuiTextField var10000 = lIIIIIIIIllllII.transferCodeField;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("transferCodeField");
      }

      return var10000;
   }

   // $FF: synthetic method
   public static final GuiButton access$getStateButton$p(GuiDonatorCape lIIIIIIIIlIlIIl) {
      GuiButton var10000 = lIIIIIIIIlIlIIl.stateButton;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("stateButton");
      }

      return var10000;
   }

   public void updateScreen() {
      GuiTextField var10000 = lIIIIIIIlIIIlll.transferCodeField;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("transferCodeField");
      }

      var10000.updateCursorCounter();
      super.updateScreen();
   }

   protected void keyTyped(char lIIIIIIIlIlIlll, int lIIIIIIIlIllIIl) {
      if (1 == lIIIIIIIlIllIIl) {
         lIIIIIIIlIllIll.mc.displayGuiScreen((GuiScreen)lIIIIIIIlIllIll.prevGui);
      } else {
         GuiTextField var10000 = lIIIIIIIlIllIll.transferCodeField;
         if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("transferCodeField");
         }

         if (var10000.isFocused()) {
            var10000 = lIIIIIIIlIllIll.transferCodeField;
            if (var10000 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("transferCodeField");
            }

            var10000.textboxKeyTyped(lIIIIIIIlIlIlll, lIIIIIIIlIllIIl);
            boolean var10001 = false;
         }

         super.keyTyped(lIIIIIIIlIlIlll, lIIIIIIIlIllIIl);
      }
   }

   // $FF: synthetic method
   public static final void access$setTransferCodeField$p(GuiDonatorCape lIIIIIIIIlllIII, GuiTextField lIIIIIIIIllIlIl) {
      lIIIIIIIIlllIII.transferCodeField = lIIIIIIIIllIlIl;
   }

   public void onGuiClosed() {
      Keyboard.enableRepeatEvents(false);
      GuiTextField var10000 = lIIIIIIIlIIIlIl.transferCodeField;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("transferCodeField");
      }

      String var2 = var10000.getText();
      Intrinsics.checkExpressionValueIsNotNull(var2, "transferCodeField.text");
      transferCode = var2;
      super.onGuiClosed();
   }

   protected void actionPerformed(@NotNull GuiButton lIIIIIIIllIIIIl) {
      Intrinsics.checkParameterIsNotNull(lIIIIIIIllIIIIl, "button");
      if (lIIIIIIIllIIIIl.enabled) {
         switch(lIIIIIIIllIIIIl.id) {
         case 0:
            lIIIIIIIllIIIlI.mc.displayGuiScreen((GuiScreen)lIIIIIIIllIIIlI.prevGui);
            break;
         case 1:
            GuiButton var10000 = lIIIIIIIllIIIlI.stateButton;
            if (var10000 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("stateButton");
            }

            var10000.enabled = false;
            ThreadsKt.thread$default(false, false, (ClassLoader)null, (String)null, 0, (Function0)(new Function0<Unit>() {
               public final void invoke() {
                  char lllllllllllllllllIllIlllllIlIIll = HttpClients.createDefault();
                  BasicHeader[] lllllllllllllllllIllIlllllIllIIl = new BasicHeader[]{new BasicHeader("Content-Type", "application/json"), new BasicHeader("Authorization", GuiDonatorCape.access$getTransferCodeField$p(lIIIIIIIllIIIlI).getText())};
                  long lllllllllllllllllIllIlllllIlIIII = GuiDonatorCape.Companion.getCapeEnabled() ? (HttpRequestBase)(new HttpDelete("http://capes.liquidbounce.net/api/v1/cape/self")) : (HttpRequestBase)(new HttpPut("http://capes.liquidbounce.net/api/v1/cape/self"));
                  lllllllllllllllllIllIlllllIlIIII.setHeaders((Header[])lllllllllllllllllIllIlllllIllIIl);
                  CloseableHttpResponse lllllllllllllllllIllIlllllIlllIl = lllllllllllllllllIllIlllllIlIIll.execute((HttpUriRequest)lllllllllllllllllIllIlllllIlIIII);
                  Intrinsics.checkExpressionValueIsNotNull(lllllllllllllllllIllIlllllIlllIl, "response");
                  StatusLine var10000 = lllllllllllllllllIllIlllllIlllIl.getStatusLine();
                  Intrinsics.checkExpressionValueIsNotNull(var10000, "response.statusLine");
                  int lllllllllllllllllIllIlllllIlllll = var10000.getStatusCode();
                  GuiDonatorCape var6 = lIIIIIIIllIIIlI;
                  String var10001;
                  if (lllllllllllllllllIllIlllllIlllll == 204) {
                     GuiDonatorCape.Companion.setCapeEnabled(!GuiDonatorCape.Companion.getCapeEnabled());
                     var10001 = GuiDonatorCape.Companion.getCapeEnabled() ? "§aSuccessfully enabled cape" : "§aSuccessfully disabled cape";
                  } else {
                     var10001 = String.valueOf((new StringBuilder()).append("§cFailed to toggle cape (").append(lllllllllllllllllIllIlllllIlllll).append(')'));
                  }

                  var6.status = var10001;
                  GuiDonatorCape.access$getStateButton$p(lIIIIIIIllIIIlI).enabled = true;
               }
            }), 31, (Object)null);
            boolean var10001 = false;
            break;
         case 2:
            MiscUtils.showURL("https://donate.liquidbounce.net");
         }

         super.actionPerformed(lIIIIIIIllIIIIl);
      }
   }

   public void initGui() {
      Keyboard.enableRepeatEvents(true);
      lIIIIIIIlllIIlI.stateButton = new GuiButton(1, lIIIIIIIlllIIlI.width / 2 - 100, 105, "Disable Cape");
      List var10000 = lIIIIIIIlllIIlI.buttonList;
      GuiButton var10001 = lIIIIIIIlllIIlI.stateButton;
      if (var10001 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("stateButton");
      }

      var10000.add(var10001);
      boolean var3 = false;
      lIIIIIIIlllIIlI.buttonList.add(new GuiButton(2, lIIIIIIIlllIIlI.width / 2 - 100, lIIIIIIIlllIIlI.height / 4 + 96, "Donate to get Cape"));
      var3 = false;
      lIIIIIIIlllIIlI.buttonList.add(new GuiButton(0, lIIIIIIIlllIIlI.width / 2 - 100, lIIIIIIIlllIIlI.height / 4 + 120, "Back"));
      var3 = false;
      GameFontRenderer var10004 = Fonts.font40;
      Intrinsics.checkExpressionValueIsNotNull(var10004, "Fonts.font40");
      lIIIIIIIlllIIlI.transferCodeField = (GuiTextField)(new GuiPasswordField(666, (FontRenderer)var10004, lIIIIIIIlllIIlI.width / 2 - 100, 80, 200, 20));
      GuiTextField var2 = lIIIIIIIlllIIlI.transferCodeField;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("transferCodeField");
      }

      var2.setFocused(true);
      var2 = lIIIIIIIlllIIlI.transferCodeField;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("transferCodeField");
      }

      var2.setMaxStringLength(Integer.MAX_VALUE);
      var2 = lIIIIIIIlllIIlI.transferCodeField;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("transferCodeField");
      }

      var2.setText(transferCode);
      super.initGui();
   }

   public void drawScreen(int lIIIIIIIllIlIll, int lIIIIIIIllIIllI, float lIIIIIIIllIlIIl) {
      lIIIIIIIllIllII.drawBackground(0);
      Gui.drawRect(30, 30, lIIIIIIIllIllII.width - 30, lIIIIIIIllIllII.height - 30, Integer.MIN_VALUE);
      lIIIIIIIllIllII.drawCenteredString((FontRenderer)Fonts.font35, "Donator Cape", lIIIIIIIllIllII.width / 2, 36, 16777215);
      lIIIIIIIllIllII.drawCenteredString((FontRenderer)Fonts.font35, lIIIIIIIllIllII.status, lIIIIIIIllIllII.width / 2, lIIIIIIIllIllII.height / 4 + 80, 16777215);
      GuiTextField var10000 = lIIIIIIIllIllII.transferCodeField;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("transferCodeField");
      }

      var10000.drawTextBox();
      lIIIIIIIllIllII.drawCenteredString((FontRenderer)Fonts.font40, "§7Transfer Code:", lIIIIIIIllIllII.width / 2 - 65, 66, 16777215);
      GuiButton var4 = lIIIIIIIllIllII.stateButton;
      if (var4 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("stateButton");
      }

      var4.displayString = capeEnabled ? "Disable Cape" : "Enable Cape";
      super.drawScreen(lIIIIIIIllIlIll, lIIIIIIIllIIllI, lIIIIIIIllIlIIl);
   }

   public GuiDonatorCape(@NotNull GuiAltManager lIIIIIIIIlllllI) {
      Intrinsics.checkParameterIsNotNull(lIIIIIIIIlllllI, "prevGui");
      super();
      lIIIIIIIIllllll.prevGui = lIIIIIIIIlllllI;
      lIIIIIIIIllllll.status = "";
   }

   protected void mouseClicked(int lIIIIIIIlIlIIII, int lIIIIIIIlIIllll, int lIIIIIIIlIIlIlI) {
      GuiTextField var10000 = lIIIIIIIlIIllIl.transferCodeField;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("transferCodeField");
      }

      var10000.mouseClicked(lIIIIIIIlIlIIII, lIIIIIIIlIIllll, lIIIIIIIlIIlIlI);
      super.mouseClicked(lIIIIIIIlIlIIII, lIIIIIIIlIIllll, lIIIIIIIlIIlIlI);
   }

   @Metadata(
      mv = {1, 1, 16},
      bv = {1, 0, 3},
      k = 1,
      d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u000f"},
      d2 = {"Lnet/ccbluex/liquidbounce/ui/client/altmanager/sub/GuiDonatorCape$Companion;", "", "()V", "capeEnabled", "", "getCapeEnabled", "()Z", "setCapeEnabled", "(Z)V", "transferCode", "", "getTransferCode", "()Ljava/lang/String;", "setTransferCode", "(Ljava/lang/String;)V", "LiquidSense"}
   )
   public static final class Companion {
      public final void setTransferCode(@NotNull String llllllllllllllllllIlIlIIllIIlIlI) {
         Intrinsics.checkParameterIsNotNull(llllllllllllllllllIlIlIIllIIlIlI, "<set-?>");
         GuiDonatorCape.transferCode = llllllllllllllllllIlIlIIllIIlIlI;
      }

      public final boolean getCapeEnabled() {
         return GuiDonatorCape.capeEnabled;
      }

      public final void setCapeEnabled(boolean llllllllllllllllllIlIlIIlIllIlll) {
         GuiDonatorCape.capeEnabled = llllllllllllllllllIlIlIIlIllIlll;
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker llllllllllllllllllIlIlIIlIlIIIII) {
         this();
      }

      private Companion() {
      }

      @NotNull
      public final String getTransferCode() {
         return GuiDonatorCape.transferCode;
      }
   }
}
