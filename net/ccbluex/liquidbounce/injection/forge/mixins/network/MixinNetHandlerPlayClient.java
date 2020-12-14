//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.injection.forge.mixins.network;

import io.netty.buffer.Unpooled;
import java.net.URI;
import java.net.URISyntaxException;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EntityMovementEvent;
import net.ccbluex.liquidbounce.event.PacketSenEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.Fly;
import net.ccbluex.liquidbounce.features.special.AntiForge;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketThreadUtil;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.network.play.client.C19PacketResourcePackStatus;
import net.minecraft.network.play.client.C19PacketResourcePackStatus.Action;
import net.minecraft.network.play.server.S01PacketJoinGame;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import net.minecraft.network.play.server.S48PacketResourcePackSend;
import net.minecraft.world.WorldSettings;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({NetHandlerPlayClient.class})
public abstract class MixinNetHandlerPlayClient {
   // $FF: synthetic field
   @Shadow
   private Minecraft gameController;
   // $FF: synthetic field
   @Shadow
   @Final
   private NetworkManager netManager;
   // $FF: synthetic field
   @Shadow
   public int currentServerMaxPlayers;
   // $FF: synthetic field
   @Shadow
   private WorldClient clientWorldController;

   @Inject(
      method = {"handleEntityMovement"},
      at = {@At(
   value = "FIELD",
   target = "Lnet/minecraft/entity/Entity;onGround:Z"
)}
   )
   private void handleEntityMovementEvent(S14PacketEntity llIlIIlIlIlllII, CallbackInfo llIlIIlIlIllIll) {
      short llIlIIlIlIlIlll = llIlIIlIlIlllII.getEntity(llIlIIlIlIlllIl.clientWorldController);
      if (llIlIIlIlIlIlll != null) {
         LiquidBounce.eventManager.callEvent(new EntityMovementEvent(llIlIIlIlIlIlll));
      }

   }

   @Overwrite
   public void handleConfirmTransaction(S32PacketConfirmTransaction llIlIIlIIllIlll) {
      double llIlIIlIIllIlIl = (Fly)LiquidBounce.moduleManager.getModule(Fly.class);
      if (llIlIIlIIllIlIl.getState() && ((String)llIlIIlIIllIlIl.getModeValue().get()).equalsIgnoreCase("Zoom")) {
         PacketThreadUtil.checkThreadAndEnqueue(llIlIIlIIllIlll, (NetHandlerPlayClient)llIlIIlIIllllll, llIlIIlIIllllll.gameController);
         Exception llIlIIlIIllIIll = null;
         EntityPlayer llIlIIlIlIIIIlI = llIlIIlIIllllll.gameController.thePlayer;
         if (llIlIIlIIllIlll.getWindowId() == 0) {
            llIlIIlIIllIIll = llIlIIlIlIIIIlI.inventoryContainer;
         } else if (llIlIIlIIllIlll.getWindowId() == llIlIIlIlIIIIlI.openContainer.windowId) {
            llIlIIlIIllIIll = llIlIIlIlIIIIlI.openContainer;
         }

         if (llIlIIlIIllIIll != null && !llIlIIlIIllIlll.func_148888_e()) {
            Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C0FPacketConfirmTransaction(1, (short)-1, false));
         }
      }

   }

   @Inject(
      method = {"handleResourcePack"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void handleResourcePack(S48PacketResourcePackSend llIlIIllIIIIllI, CallbackInfo llIlIIllIIIIIII) {
      long llIlIIlIlllllll = llIlIIllIIIIllI.getURL();
      String llIlIIlIllllllI = llIlIIllIIIIllI.getHash();

      try {
         short llIlIIlIlllllIl = (new URI(llIlIIlIlllllll)).getScheme();
         byte llIlIIlIlllllII = "level".equals(llIlIIlIlllllIl);
         if (!"http".equals(llIlIIlIlllllIl) && !"https".equals(llIlIIlIlllllIl) && !llIlIIlIlllllII) {
            throw new URISyntaxException(llIlIIlIlllllll, "Wrong protocol");
         }

         if (llIlIIlIlllllII && (llIlIIlIlllllll.contains("..") || !llIlIIlIlllllll.endsWith("/resources.zip"))) {
            throw new URISyntaxException(llIlIIlIlllllll, "Invalid levelstorage resourcepack path");
         }
      } catch (URISyntaxException var7) {
         ClientUtils.getLogger().error("Failed to handle resource pack", var7);
         llIlIIllIIIIlll.netManager.sendPacket(new C19PacketResourcePackStatus(llIlIIlIllllllI, Action.FAILED_DOWNLOAD));
         llIlIIllIIIIIII.cancel();
      }

   }

   @Inject(
      method = {"handleJoinGame"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void handleJoinGameWithAntiForge(S01PacketJoinGame llIlIIlIllIIllI, CallbackInfo llIlIIlIllIIlII) {
      if (AntiForge.enabled && AntiForge.blockFML && !Minecraft.getMinecraft().isIntegratedServerRunning()) {
         PacketThreadUtil.checkThreadAndEnqueue(llIlIIlIllIIllI, (NetHandlerPlayClient)llIlIIlIllIllII, llIlIIlIllIllII.gameController);
         llIlIIlIllIllII.gameController.playerController = new PlayerControllerMP(llIlIIlIllIllII.gameController, (NetHandlerPlayClient)llIlIIlIllIllII);
         llIlIIlIllIllII.clientWorldController = new WorldClient((NetHandlerPlayClient)llIlIIlIllIllII, new WorldSettings(0L, llIlIIlIllIIllI.getGameType(), false, llIlIIlIllIIllI.isHardcoreMode(), llIlIIlIllIIllI.getWorldType()), llIlIIlIllIIllI.getDimension(), llIlIIlIllIIllI.getDifficulty(), llIlIIlIllIllII.gameController.mcProfiler);
         llIlIIlIllIllII.gameController.gameSettings.difficulty = llIlIIlIllIIllI.getDifficulty();
         llIlIIlIllIllII.gameController.loadWorld(llIlIIlIllIllII.clientWorldController);
         llIlIIlIllIllII.gameController.thePlayer.dimension = llIlIIlIllIIllI.getDimension();
         llIlIIlIllIllII.gameController.displayGuiScreen(new GuiDownloadTerrain((NetHandlerPlayClient)llIlIIlIllIllII));
         llIlIIlIllIllII.gameController.thePlayer.setEntityId(llIlIIlIllIIllI.getEntityId());
         llIlIIlIllIllII.currentServerMaxPlayers = llIlIIlIllIIllI.getMaxPlayers();
         llIlIIlIllIllII.gameController.thePlayer.setReducedDebug(llIlIIlIllIIllI.isReducedDebugInfo());
         llIlIIlIllIllII.gameController.playerController.setGameType(llIlIIlIllIIllI.getGameType());
         llIlIIlIllIllII.gameController.gameSettings.sendSettingsToServer();
         llIlIIlIllIllII.netManager.sendPacket(new C17PacketCustomPayload("MC|Brand", (new PacketBuffer(Unpooled.buffer())).writeString(ClientBrandRetriever.getClientModName())));
         llIlIIlIllIIlII.cancel();
      }
   }

   @Inject(
      method = {"addToSendQueue"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void senpacket(Packet<?> llIlIIlIIlIlIlI, CallbackInfo llIlIIlIIlIIlII) {
      float llIlIIlIIlIIIlI = new PacketSenEvent(llIlIIlIIlIlIlI);
      LiquidBounce.eventManager.callEvent(llIlIIlIIlIIIlI);
      if (llIlIIlIIlIIIlI.isCancelled()) {
         llIlIIlIIlIIlII.cancel();
      }

   }
}
