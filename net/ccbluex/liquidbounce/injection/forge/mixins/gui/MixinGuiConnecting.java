//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import com.mojang.authlib.GameProfile;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.ServerUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.mcleaks.MCLeaks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SideOnly(Side.CLIENT)
@Mixin({GuiConnecting.class})
public abstract class MixinGuiConnecting extends GuiScreen {
   // $FF: synthetic field
   @Shadow
   @Final
   private static AtomicInteger CONNECTION_ID;
   // $FF: synthetic field
   @Shadow
   @Final
   private GuiScreen previousGuiScreen;
   // $FF: synthetic field
   @Shadow
   private boolean cancel;
   // $FF: synthetic field
   @Shadow
   private NetworkManager networkManager;
   // $FF: synthetic field
   @Shadow
   @Final
   private static Logger logger;

   @Overwrite
   private void connect(String llIllIlIIIIIIll, int llIllIlIIIIIIlI) {
      logger.info(String.valueOf((new StringBuilder()).append("Connecting to ").append(llIllIlIIIIIIll).append(", ").append(llIllIlIIIIIIlI)));
      (new Thread(() -> {
         InetAddress llIllIIllIlIlll = null;

         try {
            if (llIllIIllIllllI.cancel) {
               return;
            }

            llIllIIllIlIlll = InetAddress.getByName(llIllIlIIIIIIll);
            llIllIIllIllllI.networkManager = NetworkManager.createNetworkManagerAndConnect(llIllIIllIlIlll, llIllIlIIIIIIlI, llIllIIllIllllI.mc.gameSettings.isUsingNativeTransport());
            llIllIIllIllllI.networkManager.setNetHandler(new NetHandlerLoginClient(llIllIIllIllllI.networkManager, llIllIIllIllllI.mc, llIllIIllIllllI.previousGuiScreen));
            llIllIIllIllllI.networkManager.sendPacket(new C00Handshake(47, llIllIlIIIIIIll, llIllIlIIIIIIlI, EnumConnectionState.LOGIN, true));
            llIllIIllIllllI.networkManager.sendPacket(new C00PacketLoginStart(MCLeaks.isAltActive() ? new GameProfile((UUID)null, MCLeaks.getSession().getUsername()) : llIllIIllIllllI.mc.getSession().getProfile()));
         } catch (UnknownHostException var7) {
            if (llIllIIllIllllI.cancel) {
               return;
            }

            logger.error("Couldn't connect to server", var7);
            llIllIIllIllllI.mc.displayGuiScreen(new GuiDisconnected(llIllIIllIllllI.previousGuiScreen, "connect.failed", new ChatComponentTranslation("disconnect.genericReason", new Object[]{"Unknown host"})));
         } catch (Exception var8) {
            if (llIllIIllIllllI.cancel) {
               return;
            }

            logger.error("Couldn't connect to server", var8);
            short llIllIIllIlIlIl = var8.toString();
            if (llIllIIllIlIlll != null) {
               String llIllIIlllIIIIl = String.valueOf((new StringBuilder()).append(llIllIIllIlIlll.toString()).append(":").append(llIllIlIIIIIIlI));
               llIllIIllIlIlIl = llIllIIllIlIlIl.replaceAll(llIllIIlllIIIIl, "");
            }

            llIllIIllIllllI.mc.displayGuiScreen(new GuiDisconnected(llIllIIllIllllI.previousGuiScreen, "connect.failed", new ChatComponentTranslation("disconnect.genericReason", new Object[]{llIllIIllIlIlIl})));
         }

      }, String.valueOf((new StringBuilder()).append("Server Connector #").append(CONNECTION_ID.incrementAndGet())))).start();
   }

   @Inject(
      method = {"connect"},
      at = {@At("HEAD")}
   )
   private void headConnect(String llIllIlIIIIlllI, int llIllIlIIIIllIl, CallbackInfo llIllIlIIIIllII) {
      ServerUtils.serverData = new ServerData("", String.valueOf((new StringBuilder()).append(llIllIlIIIIlllI).append(":").append(llIllIlIIIIllIl)), false);
   }

   @Overwrite
   public void drawScreen(int llIllIIlllIllll, int llIllIIllllIlIl, float llIllIIllllIlII) {
      ScaledResolution llIllIIllllIIll = new ScaledResolution(Minecraft.getMinecraft());
      llIllIIllllIlll.drawDefaultBackground();
      RenderUtils.drawLoadingCircle((float)(llIllIIllllIIll.getScaledWidth() / 2), (float)(llIllIIllllIIll.getScaledHeight() / 4 + 70));
      float llIllIIlllIlIll = "Unknown";
      int llIllIIlllIlIlI = llIllIIllllIlll.mc.getCurrentServerData();
      if (llIllIIlllIlIlI != null) {
         llIllIIlllIlIll = llIllIIlllIlIlI.serverIP;
      }

      Fonts.font40.drawCenteredString("Connecting to", (float)(llIllIIllllIIll.getScaledWidth() / 2), (float)(llIllIIllllIIll.getScaledHeight() / 4 + 110), 16777215, true);
      boolean var10001 = false;
      Fonts.font35.drawCenteredString(llIllIIlllIlIll, (float)(llIllIIllllIIll.getScaledWidth() / 2), (float)(llIllIIllllIIll.getScaledHeight() / 4 + 120), 5407227, true);
      var10001 = false;
      super.drawScreen(llIllIIlllIllll, llIllIIllllIlIl, llIllIIllllIlII);
   }

   @Inject(
      method = {"connect"},
      at = {@At(
   value = "NEW",
   target = "net/minecraft/network/login/client/C00PacketLoginStart"
)},
      cancellable = true
   )
   private void mcLeaks(CallbackInfo llIllIlIIIIlIII) {
   }
}
