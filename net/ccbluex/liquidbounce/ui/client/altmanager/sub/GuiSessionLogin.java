//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.ui.client.altmanager.sub;

import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.concurrent.ThreadsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.ui.client.altmanager.GuiAltManager;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\f\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0006H\u0014J \u0010\u000e\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\fH\u0016J\u0018\u0010\u0015\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0010H\u0014J \u0010\u0019\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u001a\u001a\u00020\u0010H\u0014J\b\u0010\u001b\u001a\u00020\fH\u0016J\b\u0010\u001c\u001a\u00020\fH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001d"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/client/altmanager/sub/GuiSessionLogin;", "Lnet/minecraft/client/gui/GuiScreen;", "prevGui", "Lnet/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager;", "(Lnet/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager;)V", "loginButton", "Lnet/minecraft/client/gui/GuiButton;", "sessionTokenField", "Lnet/minecraft/client/gui/GuiTextField;", "status", "", "actionPerformed", "", "button", "drawScreen", "mouseX", "", "mouseY", "partialTicks", "", "initGui", "keyTyped", "typedChar", "", "keyCode", "mouseClicked", "mouseButton", "onGuiClosed", "updateScreen", "LiquidSense"}
)
public final class GuiSessionLogin extends GuiScreen {
   // $FF: synthetic field
   private String status;
   // $FF: synthetic field
   private GuiButton loginButton;
   // $FF: synthetic field
   private final GuiAltManager prevGui;
   // $FF: synthetic field
   private GuiTextField sessionTokenField;

   public void drawScreen(int lIlIIlIIlIlIIl, int lIlIIlIIlIIlII, float lIlIIlIIlIIlll) {
      lIlIIlIIlIlIlI.drawBackground(0);
      Gui.drawRect(30, 30, lIlIIlIIlIlIlI.width - 30, lIlIIlIIlIlIlI.height - 30, Integer.MIN_VALUE);
      lIlIIlIIlIlIlI.drawCenteredString((FontRenderer)Fonts.font35, "Session Login", lIlIIlIIlIlIlI.width / 2, 36, 16777215);
      lIlIIlIIlIlIlI.drawCenteredString((FontRenderer)Fonts.font35, lIlIIlIIlIlIlI.status, lIlIIlIIlIlIlI.width / 2, lIlIIlIIlIlIlI.height / 4 + 80, 16777215);
      GuiTextField var10000 = lIlIIlIIlIlIlI.sessionTokenField;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("sessionTokenField");
      }

      var10000.drawTextBox();
      lIlIIlIIlIlIlI.drawCenteredString((FontRenderer)Fonts.font40, "§7Session Token:", lIlIIlIIlIlIlI.width / 2 - 65, 66, 16777215);
      super.drawScreen(lIlIIlIIlIlIIl, lIlIIlIIlIIlII, lIlIIlIIlIIlll);
   }

   // $FF: synthetic method
   public static final GuiTextField access$getSessionTokenField$p(GuiSessionLogin lIlIIIlllllIIl) {
      GuiTextField var10000 = lIlIIIlllllIIl.sessionTokenField;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("sessionTokenField");
      }

      return var10000;
   }

   public void initGui() {
      Keyboard.enableRepeatEvents(true);
      lIlIIlIIllIIII.loginButton = new GuiButton(1, lIlIIlIIllIIII.width / 2 - 100, lIlIIlIIllIIII.height / 4 + 96, "Login");
      List var10000 = lIlIIlIIllIIII.buttonList;
      GuiButton var10001 = lIlIIlIIllIIII.loginButton;
      if (var10001 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("loginButton");
      }

      var10000.add(var10001);
      boolean var3 = false;
      lIlIIlIIllIIII.buttonList.add(new GuiButton(0, lIlIIlIIllIIII.width / 2 - 100, lIlIIlIIllIIII.height / 4 + 120, "Back"));
      var3 = false;
      lIlIIlIIllIIII.sessionTokenField = new GuiTextField(666, (FontRenderer)Fonts.font40, lIlIIlIIllIIII.width / 2 - 100, 80, 200, 20);
      GuiTextField var2 = lIlIIlIIllIIII.sessionTokenField;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("sessionTokenField");
      }

      var2.setFocused(true);
      var2 = lIlIIlIIllIIII.sessionTokenField;
      if (var2 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("sessionTokenField");
      }

      var2.setMaxStringLength(Integer.MAX_VALUE);
      if (lIlIIlIIllIIII.sessionTokenField == null) {
         Intrinsics.throwUninitializedPropertyAccessException("sessionTokenField");
      }

      var3 = false;
      super.initGui();
   }

   // $FF: synthetic method
   public static final GuiButton access$getLoginButton$p(GuiSessionLogin lIlIIIlllIIlll) {
      GuiButton var10000 = lIlIIIlllIIlll.loginButton;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("loginButton");
      }

      return var10000;
   }

   protected void mouseClicked(int lIlIIlIIIIlIlI, int lIlIIlIIIIllIl, int lIlIIlIIIIlIII) {
      GuiTextField var10000 = lIlIIlIIIIlIll.sessionTokenField;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("sessionTokenField");
      }

      var10000.mouseClicked(lIlIIlIIIIlIlI, lIlIIlIIIIllIl, lIlIIlIIIIlIII);
      super.mouseClicked(lIlIIlIIIIlIlI, lIlIIlIIIIllIl, lIlIIlIIIIlIII);
   }

   // $FF: synthetic method
   public static final void access$setLoginButton$p(GuiSessionLogin lIlIIIlllIIIlI, GuiButton lIlIIIlllIIIIl) {
      lIlIIIlllIIIlI.loginButton = lIlIIIlllIIIIl;
   }

   protected void keyTyped(char lIlIIlIIIlIlIl, int lIlIIlIIIlIlII) {
      if (1 == lIlIIlIIIlIlII) {
         lIlIIlIIIlIllI.mc.displayGuiScreen((GuiScreen)lIlIIlIIIlIllI.prevGui);
      } else {
         GuiTextField var10000 = lIlIIlIIIlIllI.sessionTokenField;
         if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sessionTokenField");
         }

         if (var10000.isFocused()) {
            var10000 = lIlIIlIIIlIllI.sessionTokenField;
            if (var10000 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("sessionTokenField");
            }

            var10000.textboxKeyTyped(lIlIIlIIIlIlIl, lIlIIlIIIlIlII);
            boolean var10001 = false;
         }

         super.keyTyped(lIlIIlIIIlIlIl, lIlIIlIIIlIlII);
      }
   }

   protected void actionPerformed(@NotNull GuiButton lIlIIlIIIlllll) {
      Intrinsics.checkParameterIsNotNull(lIlIIlIIIlllll, "button");
      if (lIlIIlIIIlllll.enabled) {
         switch(lIlIIlIIIlllll.id) {
         case 0:
            lIlIIlIIIllllI.mc.displayGuiScreen((GuiScreen)lIlIIlIIIllllI.prevGui);
            break;
         case 1:
            GuiButton var10000 = lIlIIlIIIllllI.loginButton;
            if (var10000 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("loginButton");
            }

            var10000.enabled = false;
            lIlIIlIIIllllI.status = "§aLogging in...";
            ThreadsKt.thread$default(false, false, (ClassLoader)null, (String)null, 0, (Function0)(new Function0<Unit>() {
               public final void invoke() {
                  // $FF: Couldn't be decompiled
               }
            }), 31, (Object)null);
            boolean var10001 = false;
         }

         super.actionPerformed(lIlIIlIIIlllll);
      }
   }

   public GuiSessionLogin(@NotNull GuiAltManager lIlIIIllllllII) {
      Intrinsics.checkParameterIsNotNull(lIlIIIllllllII, "prevGui");
      super();
      lIlIIIllllllll.prevGui = lIlIIIllllllII;
      lIlIIIllllllll.status = "";
   }

   // $FF: synthetic method
   public static final void access$setSessionTokenField$p(GuiSessionLogin lIlIIIllllIllI, GuiTextField lIlIIIllllIIll) {
      lIlIIIllllIllI.sessionTokenField = lIlIIIllllIIll;
   }

   public void updateScreen() {
      GuiTextField var10000 = lIlIIlIIIIIllI.sessionTokenField;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("sessionTokenField");
      }

      var10000.updateCursorCounter();
      super.updateScreen();
   }

   public void onGuiClosed() {
      Keyboard.enableRepeatEvents(false);
      super.onGuiClosed();
   }

   // $FF: synthetic method
   public static final String access$getStatus$p(GuiSessionLogin lIlIIIllllIIIl) {
      return lIlIIIllllIIIl.status;
   }
}
