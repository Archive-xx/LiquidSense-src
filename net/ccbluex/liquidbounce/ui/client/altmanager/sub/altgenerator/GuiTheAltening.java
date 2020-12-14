//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.ui.client.altmanager.sub.altgenerator;

import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import com.thealtening.AltService;
import com.thealtening.api.TheAltening;
import com.thealtening.api.data.AccountData;
import java.net.Proxy;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.Event;
import net.ccbluex.liquidbounce.event.SessionEvent;
import net.ccbluex.liquidbounce.ui.client.altmanager.GuiAltManager;
import net.ccbluex.liquidbounce.ui.elements.GuiPasswordField;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.ui.font.GameFontRenderer;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.misc.MiscUtils;
import net.mcleaks.MCLeaks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.Session;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\f\n\u0002\b\u0007\u0018\u0000 \u001f2\u00020\u0001:\u0001\u001fB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\bH\u0014J \u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u000eH\u0016J\u0018\u0010\u0017\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0012H\u0014J \u0010\u001b\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u001c\u001a\u00020\u0012H\u0014J\b\u0010\u001d\u001a\u00020\u000eH\u0016J\b\u0010\u001e\u001a\u00020\u000eH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000¨\u0006 "},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/client/altmanager/sub/altgenerator/GuiTheAltening;", "Lnet/minecraft/client/gui/GuiScreen;", "prevGui", "Lnet/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager;", "(Lnet/ccbluex/liquidbounce/ui/client/altmanager/GuiAltManager;)V", "apiKeyField", "Lnet/minecraft/client/gui/GuiTextField;", "generateButton", "Lnet/minecraft/client/gui/GuiButton;", "loginButton", "status", "", "tokenField", "actionPerformed", "", "button", "drawScreen", "mouseX", "", "mouseY", "partialTicks", "", "initGui", "keyTyped", "typedChar", "", "keyCode", "mouseClicked", "mouseButton", "onGuiClosed", "updateScreen", "Companion", "LiquidSense"}
)
public final class GuiTheAltening extends GuiScreen {
   // $FF: synthetic field
   private String status;
   // $FF: synthetic field
   @NotNull
   private static String apiKey = "";
   // $FF: synthetic field
   private GuiButton loginButton;
   // $FF: synthetic field
   private GuiButton generateButton;
   // $FF: synthetic field
   public static final GuiTheAltening.Companion Companion = new GuiTheAltening.Companion((DefaultConstructorMarker)null);
   // $FF: synthetic field
   private GuiTextField tokenField;
   // $FF: synthetic field
   private final GuiAltManager prevGui;
   // $FF: synthetic field
   private GuiTextField apiKeyField;

   public void drawScreen(int lIIlIlIIIIIlllI, int lIIlIlIIIIIlIIl, float lIIlIlIIIIIllII) {
      lIIlIlIIIIIlIll.drawBackground(0);
      Gui.drawRect(30, 30, lIIlIlIIIIIlIll.width - 30, lIIlIlIIIIIlIll.height - 30, Integer.MIN_VALUE);
      lIIlIlIIIIIlIll.drawCenteredString((FontRenderer)Fonts.font35, "TheAltening", lIIlIlIIIIIlIll.width / 2, 6, 16777215);
      lIIlIlIIIIIlIll.drawCenteredString((FontRenderer)Fonts.font35, lIIlIlIIIIIlIll.status, lIIlIlIIIIIlIll.width / 2, 18, 16777215);
      GuiTextField var10000 = lIIlIlIIIIIlIll.apiKeyField;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("apiKeyField");
      }

      var10000.drawTextBox();
      var10000 = lIIlIlIIIIIlIll.tokenField;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("tokenField");
      }

      var10000.drawTextBox();
      lIIlIlIIIIIlIll.drawCenteredString((FontRenderer)Fonts.font40, "§7Token:", lIIlIlIIIIIlIll.width / 2 - 84, 40, 16777215);
      lIIlIlIIIIIlIll.drawCenteredString((FontRenderer)Fonts.font40, "§7API-Key:", lIIlIlIIIIIlIll.width / 2 - 78, 105, 16777215);
      lIIlIlIIIIIlIll.drawCenteredString((FontRenderer)Fonts.font40, "§7Use coupon code 'liquidbounce' for 20% off!", lIIlIlIIIIIlIll.width / 2, lIIlIlIIIIIlIll.height - 65, 16777215);
      super.drawScreen(lIIlIlIIIIIlllI, lIIlIlIIIIIlIIl, lIIlIlIIIIIllII);
   }

   protected void keyTyped(char lIIlIIlllllIlll, int lIIlIIlllllIllI) {
      if (1 == lIIlIIlllllIllI) {
         lIIlIIlllllIlIl.mc.displayGuiScreen((GuiScreen)lIIlIIlllllIlIl.prevGui);
      } else {
         GuiTextField var10000 = lIIlIIlllllIlIl.apiKeyField;
         if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("apiKeyField");
         }

         boolean var10001;
         if (var10000.isFocused()) {
            var10000 = lIIlIIlllllIlIl.apiKeyField;
            if (var10000 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("apiKeyField");
            }

            var10000.textboxKeyTyped(lIIlIIlllllIlll, lIIlIIlllllIllI);
            var10001 = false;
         }

         var10000 = lIIlIIlllllIlIl.tokenField;
         if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tokenField");
         }

         if (var10000.isFocused()) {
            var10000 = lIIlIIlllllIlIl.tokenField;
            if (var10000 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("tokenField");
            }

            var10000.textboxKeyTyped(lIIlIIlllllIlll, lIIlIIlllllIllI);
            var10001 = false;
         }

         super.keyTyped(lIIlIIlllllIlll, lIIlIIlllllIllI);
      }
   }

   public GuiTheAltening(@NotNull GuiAltManager lIIlIIlllIllIll) {
      Intrinsics.checkParameterIsNotNull(lIIlIIlllIllIll, "prevGui");
      super();
      lIIlIIlllIllllI.prevGui = lIIlIIlllIllIll;
      lIIlIIlllIllllI.status = "";
   }

   public void onGuiClosed() {
      Keyboard.enableRepeatEvents(false);
      GuiTextField var10000 = lIIlIIllllIIIIl.apiKeyField;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("apiKeyField");
      }

      String var1 = var10000.getText();
      Intrinsics.checkExpressionValueIsNotNull(var1, "apiKeyField.text");
      apiKey = var1;
      super.onGuiClosed();
   }

   public void updateScreen() {
      GuiTextField var10000 = lIIlIIllllIIlIl.apiKeyField;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("apiKeyField");
      }

      var10000.updateCursorCounter();
      var10000 = lIIlIIllllIIlIl.tokenField;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("tokenField");
      }

      var10000.updateCursorCounter();
      super.updateScreen();
   }

   // $FF: synthetic method
   public static final String access$getStatus$p(GuiTheAltening lIIlIIlllIllIIl) {
      return lIIlIIlllIllIIl.status;
   }

   // $FF: synthetic method
   public static final void access$setGenerateButton$p(GuiTheAltening lIIlIIllIlllllI, GuiButton lIIlIIllIllllll) {
      lIIlIIllIlllllI.generateButton = lIIlIIllIllllll;
   }

   protected void actionPerformed(@NotNull GuiButton lIIlIlIIIIIIIII) {
      Intrinsics.checkParameterIsNotNull(lIIlIlIIIIIIIII, "button");
      if (lIIlIlIIIIIIIII.enabled) {
         GuiButton var10000;
         switch(lIIlIlIIIIIIIII.id) {
         case 0:
            lIIlIIlllllllll.mc.displayGuiScreen((GuiScreen)lIIlIIlllllllll.prevGui);
            break;
         case 1:
            var10000 = lIIlIIlllllllll.loginButton;
            if (var10000 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("loginButton");
            }

            var10000.enabled = false;
            var10000 = lIIlIIlllllllll.generateButton;
            if (var10000 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("generateButton");
            }

            var10000.enabled = false;
            GuiTextField var4 = lIIlIIlllllllll.apiKeyField;
            if (var4 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("apiKeyField");
            }

            String var5 = var4.getText();
            Intrinsics.checkExpressionValueIsNotNull(var5, "apiKeyField.text");
            apiKey = var5;
            Exception lIIlIIlllllllIl = new TheAltening(apiKey);
            TheAltening.Asynchronous lIIlIlIIIIIIIll = new TheAltening.Asynchronous(lIIlIIlllllllIl);
            lIIlIIlllllllll.status = "§cGenerating account...";
            lIIlIlIIIIIIIll.getAccountData().thenAccept((Consumer)(new Consumer<AccountData>() {
               public final void accept(AccountData lIIIllIIllIlIlI) {
                  GuiTheAltening var10000 = lIIlIIlllllllll;
                  StringBuilder var10001 = (new StringBuilder()).append("§aGenerated account: §b§l");
                  Intrinsics.checkExpressionValueIsNotNull(lIIIllIIllIlIlI, "account");
                  var10000.status = String.valueOf(var10001.append(lIIIllIIllIlIlI.getUsername()));

                  try {
                     lIIlIIlllllllll.status = "§cSwitching Alt Service...";
                     GuiAltManager.altService.switchService(AltService.EnumAltService.THEALTENING);
                     lIIlIIlllllllll.status = "§cLogging in...";
                     YggdrasilUserAuthentication lIIIllIIllIllIl = new YggdrasilUserAuthentication(new YggdrasilAuthenticationService(Proxy.NO_PROXY, ""), Agent.MINECRAFT);
                     lIIIllIIllIllIl.setUsername(lIIIllIIllIlIlI.getToken());
                     lIIIllIIllIllIl.setPassword("LiquidSense");
                     GuiTheAltening lIIIllIIllIIlII = lIIlIIlllllllll;

                     String lIIIllIIllIIllI;
                     try {
                        var10000 = lIIIllIIllIIlII;
                        lIIIllIIllIllIl.logIn();
                        Minecraft var8 = lIIlIIlllllllll.mc;
                        GameProfile var10004 = lIIIllIIllIllIl.getSelectedProfile();
                        Intrinsics.checkExpressionValueIsNotNull(var10004, "yggdrasilUserAuthentication.selectedProfile");
                        String var10 = var10004.getName();
                        GameProfile var10005 = lIIIllIIllIllIl.getSelectedProfile();
                        Intrinsics.checkExpressionValueIsNotNull(var10005, "yggdrasilUserAuthenticat…         .selectedProfile");
                        var8.session = new Session(var10, var10005.getId().toString(), lIIIllIIllIllIl.getAuthenticatedToken(), "mojang");
                        LiquidBounce.INSTANCE.getEventManager().callEvent((Event)(new SessionEvent()));
                        MCLeaks.remove();
                        GuiAltManager var9 = lIIlIIlllllllll.prevGui;
                        StringBuilder var10002 = (new StringBuilder()).append("§aYour name is now §b§l");
                        GameProfile var10003 = lIIIllIIllIllIl.getSelectedProfile();
                        Intrinsics.checkExpressionValueIsNotNull(var10003, "yggdrasilUserAuthentication.selectedProfile");
                        var9.status = String.valueOf(var10002.append(var10003.getName()).append("§c."));
                        lIIlIIlllllllll.mc.displayGuiScreen((GuiScreen)lIIlIIlllllllll.prevGui);
                        lIIIllIIllIIllI = "";
                     } catch (AuthenticationException var6) {
                        var10000 = lIIIllIIllIIlII;
                        GuiAltManager.altService.switchService(AltService.EnumAltService.MOJANG);
                        ClientUtils.getLogger().error("Failed to login.", (Throwable)var6);
                        lIIIllIIllIIllI = String.valueOf((new StringBuilder()).append("§cFailed to login: ").append(var6.getMessage()));
                     }

                     var10000.status = lIIIllIIllIIllI;
                  } catch (Throwable var7) {
                     lIIlIIlllllllll.status = "§cFailed to login. Unknown error.";
                     ClientUtils.getLogger().error("Failed to login.", var7);
                  }

                  GuiTheAltening.access$getLoginButton$p(lIIlIIlllllllll).enabled = true;
                  GuiTheAltening.access$getGenerateButton$p(lIIlIIlllllllll).enabled = true;
               }
            })).handle((BiFunction)(new BiFunction<T, Throwable, U>() {
               public final void apply(Void lIlIllllllIllll, Throwable lIlIllllllIlIIl) {
                  lIIlIIlllllllll.status = "§cFailed to generate account.";
                  ClientUtils.getLogger().error("Failed to generate account.", lIlIllllllIlIIl);
               }
            })).whenComplete((BiConsumer)(new BiConsumer<Unit, Throwable>() {
               public final void accept(Unit lllllllllllllllllllllIlIIlIlllll, Throwable lllllllllllllllllllllIlIIlIllllI) {
                  GuiTheAltening.access$getLoginButton$p(lIIlIIlllllllll).enabled = true;
                  GuiTheAltening.access$getGenerateButton$p(lIIlIIlllllllll).enabled = true;
               }
            }));
            boolean var10001 = false;
            break;
         case 2:
            var10000 = lIIlIIlllllllll.loginButton;
            if (var10000 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("loginButton");
            }

            var10000.enabled = false;
            var10000 = lIIlIIlllllllll.generateButton;
            if (var10000 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("generateButton");
            }

            var10000.enabled = false;
            (new Thread((Runnable)(new Runnable() {
               public final void run() {
                  try {
                     lIIlIIlllllllll.status = "§cSwitching Alt Service...";
                     GuiAltManager.altService.switchService(AltService.EnumAltService.THEALTENING);
                     lIIlIIlllllllll.status = "§cLogging in...";
                     YggdrasilUserAuthentication lllIlIllIlIlIII = new YggdrasilUserAuthentication(new YggdrasilAuthenticationService(Proxy.NO_PROXY, ""), Agent.MINECRAFT);
                     lllIlIllIlIlIII.setUsername(GuiTheAltening.access$getTokenField$p(lIIlIIlllllllll).getText());
                     lllIlIllIlIlIII.setPassword("LiquidSense");
                     GuiTheAltening lllIlIllIlIIIIl = lIIlIIlllllllll;

                     String lllIlIllIlIIIll;
                     GuiTheAltening var10000;
                     try {
                        var10000 = lllIlIllIlIIIIl;
                        lllIlIllIlIlIII.logIn();
                        Minecraft var10001 = lIIlIIlllllllll.mc;
                        GameProfile var10004 = lllIlIllIlIlIII.getSelectedProfile();
                        Intrinsics.checkExpressionValueIsNotNull(var10004, "yggdrasilUserAuthentication.selectedProfile");
                        String var10 = var10004.getName();
                        GameProfile var10005 = lllIlIllIlIlIII.getSelectedProfile();
                        Intrinsics.checkExpressionValueIsNotNull(var10005, "yggdrasilUserAuthenticat…         .selectedProfile");
                        var10001.session = new Session(var10, var10005.getId().toString(), lllIlIllIlIlIII.getAuthenticatedToken(), "mojang");
                        LiquidBounce.INSTANCE.getEventManager().callEvent((Event)(new SessionEvent()));
                        MCLeaks.remove();
                        GuiAltManager var7 = lIIlIIlllllllll.prevGui;
                        StringBuilder var10002 = (new StringBuilder()).append("§aYour name is now §b§l");
                        GameProfile var10003 = lllIlIllIlIlIII.getSelectedProfile();
                        Intrinsics.checkExpressionValueIsNotNull(var10003, "yggdrasilUserAuthentication.selectedProfile");
                        var7.status = String.valueOf(var10002.append(var10003.getName()).append("§c."));
                        lIIlIIlllllllll.mc.displayGuiScreen((GuiScreen)lIIlIIlllllllll.prevGui);
                        StringBuilder var8 = (new StringBuilder()).append("§aYour name is now §b§l");
                        GameProfile var9 = lllIlIllIlIlIII.getSelectedProfile();
                        Intrinsics.checkExpressionValueIsNotNull(var9, "yggdrasilUserAuthentication.selectedProfile");
                        lllIlIllIlIIIll = String.valueOf(var8.append(var9.getName()).append("§c."));
                     } catch (AuthenticationException var5) {
                        var10000 = lllIlIllIlIIIIl;
                        GuiAltManager.altService.switchService(AltService.EnumAltService.MOJANG);
                        ClientUtils.getLogger().error("Failed to login.", (Throwable)var5);
                        lllIlIllIlIIIll = String.valueOf((new StringBuilder()).append("§cFailed to login: ").append(var5.getMessage()));
                     }

                     var10000.status = lllIlIllIlIIIll;
                  } catch (Throwable var6) {
                     ClientUtils.getLogger().error("Failed to login.", var6);
                     lIIlIIlllllllll.status = "§cFailed to login. Unknown error.";
                  }

                  GuiTheAltening.access$getLoginButton$p(lIIlIIlllllllll).enabled = true;
                  GuiTheAltening.access$getGenerateButton$p(lIIlIIlllllllll).enabled = true;
               }
            }))).start();
            break;
         case 3:
            MiscUtils.showURL("https://thealtening.com/?ref=liquidbounce");
         }

         super.actionPerformed(lIIlIlIIIIIIIII);
      }
   }

   // $FF: synthetic method
   public static final GuiButton access$getGenerateButton$p(GuiTheAltening lIIlIIlllIIIIll) {
      GuiButton var10000 = lIIlIIlllIIIIll.generateButton;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("generateButton");
      }

      return var10000;
   }

   protected void mouseClicked(int lIIlIIllllIllIl, int lIIlIIllllIllII, int lIIlIIllllIIlll) {
      GuiTextField var10000 = lIIlIIllllIlllI.apiKeyField;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("apiKeyField");
      }

      var10000.mouseClicked(lIIlIIllllIllIl, lIIlIIllllIllII, lIIlIIllllIIlll);
      var10000 = lIIlIIllllIlllI.tokenField;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("tokenField");
      }

      var10000.mouseClicked(lIIlIIllllIllIl, lIIlIIllllIllII, lIIlIIllllIIlll);
      super.mouseClicked(lIIlIIllllIllIl, lIIlIIllllIllII, lIIlIIllllIIlll);
   }

   // $FF: synthetic method
   public static final GuiButton access$getLoginButton$p(GuiTheAltening lIIlIIlllIIllIl) {
      GuiButton var10000 = lIIlIIlllIIllIl.loginButton;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("loginButton");
      }

      return var10000;
   }

   public void initGui() {
      Keyboard.enableRepeatEvents(true);
      lIIlIlIIIIlIlIl.loginButton = new GuiButton(2, lIIlIlIIIIlIlIl.width / 2 - 100, 75, "Login");
      List var10000 = lIIlIlIIIIlIlIl.buttonList;
      GuiButton var10001 = lIIlIlIIIIlIlIl.loginButton;
      if (var10001 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("loginButton");
      }

      var10000.add(var10001);
      boolean var2 = false;
      lIIlIlIIIIlIlIl.generateButton = new GuiButton(1, lIIlIlIIIIlIlIl.width / 2 - 100, 140, "Generate");
      var10000 = lIIlIlIIIIlIlIl.buttonList;
      var10001 = lIIlIlIIIIlIlIl.generateButton;
      if (var10001 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("generateButton");
      }

      var10000.add(var10001);
      var2 = false;
      lIIlIlIIIIlIlIl.buttonList.add(new GuiButton(3, lIIlIlIIIIlIlIl.width / 2 - 100, lIIlIlIIIIlIlIl.height - 54, 98, 20, "Buy"));
      var2 = false;
      lIIlIlIIIIlIlIl.buttonList.add(new GuiButton(0, lIIlIlIIIIlIlIl.width / 2 + 2, lIIlIlIIIIlIlIl.height - 54, 98, 20, "Back"));
      var2 = false;
      lIIlIlIIIIlIlIl.tokenField = new GuiTextField(666, (FontRenderer)Fonts.font40, lIIlIlIIIIlIlIl.width / 2 - 100, 50, 200, 20);
      GuiTextField var1 = lIIlIlIIIIlIlIl.tokenField;
      if (var1 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("tokenField");
      }

      var1.setFocused(true);
      var1 = lIIlIlIIIIlIlIl.tokenField;
      if (var1 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("tokenField");
      }

      var1.setMaxStringLength(Integer.MAX_VALUE);
      GameFontRenderer var10004 = Fonts.font40;
      Intrinsics.checkExpressionValueIsNotNull(var10004, "Fonts.font40");
      lIIlIlIIIIlIlIl.apiKeyField = (GuiTextField)(new GuiPasswordField(1337, (FontRenderer)var10004, lIIlIlIIIIlIlIl.width / 2 - 100, 115, 200, 20));
      var1 = lIIlIlIIIIlIlIl.apiKeyField;
      if (var1 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("apiKeyField");
      }

      var1.setMaxStringLength(18);
      var1 = lIIlIlIIIIlIlIl.apiKeyField;
      if (var1 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("apiKeyField");
      }

      var1.setText(apiKey);
      super.initGui();
   }

   // $FF: synthetic method
   public static final GuiTextField access$getTokenField$p(GuiTheAltening lIIlIIllIlllIll) {
      GuiTextField var10000 = lIIlIIllIlllIll.tokenField;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("tokenField");
      }

      return var10000;
   }

   // $FF: synthetic method
   public static final void access$setTokenField$p(GuiTheAltening lIIlIIllIllIlIl, GuiTextField lIIlIIllIllIllI) {
      lIIlIIllIllIlIl.tokenField = lIIlIIllIllIllI;
   }

   // $FF: synthetic method
   public static final void access$setLoginButton$p(GuiTheAltening lIIlIIlllIIlIIl, GuiButton lIIlIIlllIIlIII) {
      lIIlIIlllIIlIIl.loginButton = lIIlIIlllIIlIII;
   }

   @Metadata(
      mv = {1, 1, 16},
      bv = {1, 0, 3},
      k = 1,
      d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\t"},
      d2 = {"Lnet/ccbluex/liquidbounce/ui/client/altmanager/sub/altgenerator/GuiTheAltening$Companion;", "", "()V", "apiKey", "", "getApiKey", "()Ljava/lang/String;", "setApiKey", "(Ljava/lang/String;)V", "LiquidSense"}
   )
   public static final class Companion {
      // $FF: synthetic method
      public Companion(DefaultConstructorMarker lllllllllllllllllIllllIIIIlIllII) {
         this();
      }

      public final void setApiKey(@NotNull String lllllllllllllllllIllllIIIIllIIlI) {
         Intrinsics.checkParameterIsNotNull(lllllllllllllllllIllllIIIIllIIlI, "<set-?>");
         GuiTheAltening.apiKey = lllllllllllllllllIllllIIIIllIIlI;
      }

      private Companion() {
      }

      @NotNull
      public final String getApiKey() {
         return GuiTheAltening.apiKey;
      }
   }
}
