//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import com.mojang.authlib.Agent;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import com.thealtening.AltService;
import com.thealtening.api.TheAltening;
import java.net.Proxy;
import java.util.List;
import java.util.Random;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.SessionEvent;
import net.ccbluex.liquidbounce.features.special.AntiForge;
import net.ccbluex.liquidbounce.ui.client.altmanager.GuiAltManager;
import net.ccbluex.liquidbounce.ui.client.altmanager.sub.altgenerator.GuiTheAltening;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.ServerUtils;
import net.ccbluex.liquidbounce.utils.login.LoginUtils;
import net.ccbluex.liquidbounce.utils.login.MinecraftAccount;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.util.Session;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GuiDisconnected.class})
public abstract class MixinGuiDisconnect extends MixinGuiScreen {
   // $FF: synthetic field
   private GuiButton reconnectButton;
   // $FF: synthetic field
   private GuiButton forgeBypassButton;
   // $FF: synthetic field
   private int reconnectTimer;
   // $FF: synthetic field
   @Shadow
   private int field_175353_i;

   @Inject(
      method = {"actionPerformed"},
      at = {@At("HEAD")}
   )
   private void actionPerformed(GuiButton lllIlllllIIl, CallbackInfo lllIlllllIII) {
      switch(lllIlllllIIl.id) {
      case 1:
         ServerUtils.connectToLastServer();
         break;
      case 2:
         AntiForge.enabled = !AntiForge.enabled;
         lllIlllllIlI.forgeBypassButton.displayString = String.valueOf((new StringBuilder()).append("Bypass AntiForge: ").append(AntiForge.enabled ? "On" : "Off"));
         LiquidBounce.fileManager.saveConfig(LiquidBounce.fileManager.valuesConfig);
         break;
      case 3:
         if (!GuiTheAltening.Companion.getApiKey().isEmpty()) {
            char lllIllllIlIl = GuiTheAltening.Companion.getApiKey();
            TheAltening lllIllllIlII = new TheAltening(lllIllllIlIl);

            try {
               long lllIllllIIll = lllIllllIlII.getAccountData();
               GuiAltManager.altService.switchService(AltService.EnumAltService.THEALTENING);
               byte lllIllllIIlI = new YggdrasilUserAuthentication(new YggdrasilAuthenticationService(Proxy.NO_PROXY, ""), Agent.MINECRAFT);
               lllIllllIIlI.setUsername(lllIllllIIll.getToken());
               lllIllllIIlI.setPassword("LiquidSense");
               lllIllllIIlI.logIn();
               lllIlllllIlI.mc.session = new Session(lllIllllIIlI.getSelectedProfile().getName(), lllIllllIIlI.getSelectedProfile().getId().toString(), lllIllllIIlI.getAuthenticatedToken(), "mojang");
               LiquidBounce.eventManager.callEvent(new SessionEvent());
               ServerUtils.connectToLastServer();
               break;
            } catch (Throwable var7) {
               ClientUtils.getLogger().error("Failed to login into random account from TheAltening.", var7);
            }
         }

         List<MinecraftAccount> lllIllllIlIl = LiquidBounce.fileManager.accountsConfig.altManagerMinecraftAccounts;
         if (!lllIllllIlIl.isEmpty()) {
            long lllIllllIlII = (MinecraftAccount)lllIllllIlIl.get((new Random()).nextInt(lllIllllIlIl.size()));
            GuiAltManager.login(lllIllllIlII);
            boolean var10001 = false;
            ServerUtils.connectToLastServer();
         }
         break;
      case 4:
         LoginUtils.loginCracked(RandomUtils.randomString(RandomUtils.nextInt(5, 16)));
         ServerUtils.connectToLastServer();
      }

   }

   public void updateScreen() {
      ++lllIllllIIII.reconnectTimer;
      if (lllIllllIIII.reconnectTimer > 100) {
         ServerUtils.connectToLastServer();
      }

   }

   @Inject(
      method = {"initGui"},
      at = {@At("RETURN")}
   )
   private void initGui(CallbackInfo llllIIIIlIIl) {
      llllIIIIlIII.reconnectTimer = 0;
      llllIIIIlIII.buttonList.add(llllIIIIlIII.reconnectButton = new GuiButton(1, llllIIIIlIII.width / 2 - 100, llllIIIIlIII.height / 2 + llllIIIIlIII.field_175353_i / 2 + llllIIIIlIII.fontRendererObj.FONT_HEIGHT + 22, "Reconnect"));
      boolean var10001 = false;
      llllIIIIlIII.buttonList.add(new GuiButton(3, llllIIIIlIII.width / 2 - 100, llllIIIIlIII.height / 2 + llllIIIIlIII.field_175353_i / 2 + llllIIIIlIII.fontRendererObj.FONT_HEIGHT + 44, 98, 20, GuiTheAltening.Companion.getApiKey().isEmpty() ? "Random alt" : "New TheAltening alt"));
      var10001 = false;
      llllIIIIlIII.buttonList.add(new GuiButton(4, llllIIIIlIII.width / 2 + 2, llllIIIIlIII.height / 2 + llllIIIIlIII.field_175353_i / 2 + llllIIIIlIII.fontRendererObj.FONT_HEIGHT + 44, 98, 20, "Random username"));
      var10001 = false;
      llllIIIIlIII.buttonList.add(llllIIIIlIII.forgeBypassButton = new GuiButton(2, llllIIIIlIII.width / 2 - 100, llllIIIIlIII.height / 2 + llllIIIIlIII.field_175353_i / 2 + llllIIIIlIII.fontRendererObj.FONT_HEIGHT + 66, String.valueOf((new StringBuilder()).append("Bypass AntiForge: ").append(AntiForge.enabled ? "On" : "Off"))));
      var10001 = false;
   }

   @Inject(
      method = {"drawScreen"},
      at = {@At("RETURN")}
   )
   private void drawScreen(CallbackInfo lllIlllIllII) {
      lllIlllIllIl.reconnectButton.displayString = String.valueOf((new StringBuilder()).append("Reconnect (").append(5 - lllIlllIllIl.reconnectTimer / 20).append(")"));
   }
}
