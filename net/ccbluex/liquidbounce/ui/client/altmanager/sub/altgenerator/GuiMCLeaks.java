//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.ui.client.altmanager.sub.altgenerator;

import com.thealtening.AltService;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.ui.client.altmanager.GuiAltManager;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.misc.MiscUtils;
import net.mcleaks.Callback;
import net.mcleaks.MCLeaks;
import net.mcleaks.RedeemResponse;
import net.mcleaks.Session;
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
   d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\f\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0014J \u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\nH\u0016J\u0018\u0010\u0014\u001a\u00020\n2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u000fH\u0014J \u0010\u0018\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0019\u001a\u00020\u000fH\u0014J\b\u0010\u001a\u001a\u00020\nH\u0016J\b\u0010\u001b\u001a\u00020\nH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000¨\u0006\u001c"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/client/altmanager/sub/altgenerator/GuiMCLeaks;", "Lnet/minecraft/client/gui/GuiScreen;", "prevGui", "Lnet/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager;", "(Lnet/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager;)V", "status", "", "tokenField", "Lnet/minecraft/client/gui/GuiTextField;", "actionPerformed", "", "button", "Lnet/minecraft/client/gui/GuiButton;", "drawScreen", "mouseX", "", "mouseY", "partialTicks", "", "initGui", "keyTyped", "typedChar", "", "keyCode", "mouseClicked", "mouseButton", "onGuiClosed", "updateScreen", "LiquidSense"}
)
public final class GuiMCLeaks extends GuiScreen {
   // $FF: synthetic field
   private String status;
   // $FF: synthetic field
   private GuiTextField tokenField;
   // $FF: synthetic field
   private final GuiAltManager prevGui;

   public void drawScreen(int lllIIlllIllII, int lllIIlllIlIll, float lllIIlllIlIlI) {
      lllIIlllIllIl.drawBackground(0);
      Gui.drawRect(30, 30, lllIIlllIllIl.width - 30, lllIIlllIllIl.height - 30, Integer.MIN_VALUE);
      lllIIlllIllIl.drawCenteredString((FontRenderer)Fonts.font40, "MCLeaks", lllIIlllIllIl.width / 2, 6, 16777215);
      lllIIlllIllIl.drawString((FontRenderer)Fonts.font40, "Token:", lllIIlllIllIl.width / 2 - 100, lllIIlllIllIl.height / 4 + 30, 10526880);
      if (lllIIlllIllIl.status != null) {
         lllIIlllIllIl.drawCenteredString((FontRenderer)Fonts.font40, lllIIlllIllIl.status, lllIIlllIllIl.width / 2, 18, 16777215);
      }

      GuiTextField var10000 = lllIIlllIllIl.tokenField;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("tokenField");
      }

      var10000.drawTextBox();
      super.drawScreen(lllIIlllIllII, lllIIlllIlIll, lllIIlllIlIlI);
   }

   public GuiMCLeaks(@NotNull GuiAltManager lllIIllIllIlI) {
      Intrinsics.checkParameterIsNotNull(lllIIllIllIlI, "prevGui");
      super();
      lllIIllIllIll.prevGui = lllIIllIllIlI;
   }

   protected void mouseClicked(int lllIIllllIlll, int lllIIlllllIlI, int lllIIlllllIIl) throws IOException {
      super.mouseClicked(lllIIllllIlll, lllIIlllllIlI, lllIIlllllIIl);
      GuiTextField var10000 = lllIIllllllII.tokenField;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("tokenField");
      }

      var10000.mouseClicked(lllIIllllIlll, lllIIlllllIlI, lllIIlllllIIl);
   }

   public void onGuiClosed() {
      Keyboard.enableRepeatEvents(false);
   }

   public void updateScreen() {
      GuiTextField var10000 = lllIlIIllIIlI.tokenField;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("tokenField");
      }

      var10000.updateCursorCounter();
   }

   public void initGui() {
      Keyboard.enableRepeatEvents(true);
      if (MCLeaks.isAltActive()) {
         StringBuilder var10001 = (new StringBuilder()).append("§aToken active. Using §9");
         Session var10002 = MCLeaks.getSession();
         Intrinsics.checkExpressionValueIsNotNull(var10002, "MCLeaks.getSession()");
         lllIlIIllIIII.status = String.valueOf(var10001.append(var10002.getUsername()).append("§a to login!"));
      }

      lllIlIIllIIII.buttonList.add(new GuiButton(1, lllIlIIllIIII.width / 2 - 100, lllIlIIllIIII.height / 4 + 65, 200, 20, "Login"));
      boolean var1 = false;
      lllIlIIllIIII.buttonList.add(new GuiButton(2, lllIlIIllIIII.width / 2 - 100, lllIlIIllIIII.height - 54, 98, 20, "Get Token"));
      var1 = false;
      lllIlIIllIIII.buttonList.add(new GuiButton(3, lllIlIIllIIII.width / 2 + 2, lllIlIIllIIII.height - 54, 98, 20, "Back"));
      var1 = false;
      lllIlIIllIIII.tokenField = new GuiTextField(0, (FontRenderer)Fonts.font40, lllIlIIllIIII.width / 2 - 100, lllIlIIllIIII.height / 4 + 40, 200, 20);
      GuiTextField var10000 = lllIlIIllIIII.tokenField;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("tokenField");
      }

      var10000.setFocused(true);
      var10000 = lllIlIIllIIII.tokenField;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("tokenField");
      }

      var10000.setMaxStringLength(16);
   }

   protected void actionPerformed(@NotNull final GuiButton lllIlIIlIIIIl) {
      Intrinsics.checkParameterIsNotNull(lllIlIIlIIIIl, "button");
      if (lllIlIIlIIIIl.enabled) {
         switch(lllIlIIlIIIIl.id) {
         case 1:
            GuiTextField var10000 = lllIlIIIlllll.tokenField;
            if (var10000 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("tokenField");
            }

            if (var10000.getText().length() != 16) {
               lllIlIIIlllll.status = "§cThe token has to be 16 characters long!";
               return;
            }

            lllIlIIlIIIIl.enabled = false;
            lllIlIIlIIIIl.displayString = "Please wait ...";
            var10000 = lllIlIIIlllll.tokenField;
            if (var10000 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("tokenField");
            }

            MCLeaks.redeem(var10000.getText(), (Callback)(new Callback<Object>() {
               public final void done(Object lIIIlllIIllIII) {
                  if (lIIIlllIIllIII instanceof String) {
                     lllIlIIIlllll.status = String.valueOf((new StringBuilder()).append("§c").append(lIIIlllIIllIII));
                     lllIlIIlIIIIl.enabled = true;
                     lllIlIIlIIIIl.displayString = "Login";
                  } else if (lIIIlllIIllIII == null) {
                     throw new TypeCastException("null cannot be cast to non-null type net.mcleaks.RedeemResponse");
                  } else {
                     RedeemResponse lIIIlllIIllIlI = (RedeemResponse)lIIIlllIIllIII;
                     MCLeaks.refresh(new Session(lIIIlllIIllIlI.getUsername(), lIIIlllIIllIlI.getToken()));

                     try {
                        GuiAltManager.altService.switchService(AltService.EnumAltService.MOJANG);
                     } catch (Exception var4) {
                        ClientUtils.getLogger().error("Failed to change alt service to Mojang.", (Throwable)var4);
                     }

                     lllIlIIIlllll.status = "§aYour token was redeemed successfully!";
                     lllIlIIlIIIIl.enabled = true;
                     lllIlIIlIIIIl.displayString = "Login";
                     lllIlIIIlllll.prevGui.status = lllIlIIIlllll.status;
                     lllIlIIIlllll.mc.displayGuiScreen((GuiScreen)lllIlIIIlllll.prevGui);
                  }
               }
            }));
            break;
         case 2:
            MiscUtils.showURL("https://mcleaks.net/");
            break;
         case 3:
            lllIlIIIlllll.mc.displayGuiScreen((GuiScreen)lllIlIIIlllll.prevGui);
         }

      }
   }

   protected void keyTyped(char lllIlIIIIlIII, int lllIlIIIIlIlI) {
      GuiTextField var10000;
      switch(lllIlIIIIlIlI) {
      case 1:
         lllIlIIIIlIIl.mc.displayGuiScreen((GuiScreen)lllIlIIIIlIIl.prevGui);
         break;
      case 15:
         var10000 = lllIlIIIIlIIl.tokenField;
         if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tokenField");
         }

         GuiTextField var4 = lllIlIIIIlIIl.tokenField;
         if (var4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tokenField");
         }

         var10000.setFocused(!var4.isFocused());
         break;
      case 28:
      case 156:
         Object var10001 = lllIlIIIIlIIl.buttonList.get(1);
         Intrinsics.checkExpressionValueIsNotNull(var10001, "buttonList[1]");
         lllIlIIIIlIIl.actionPerformed((GuiButton)var10001);
         break;
      default:
         var10000 = lllIlIIIIlIIl.tokenField;
         if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tokenField");
         }

         var10000.textboxKeyTyped(lllIlIIIIlIII, lllIlIIIIlIlI);
         boolean var5 = false;
      }

   }
}
