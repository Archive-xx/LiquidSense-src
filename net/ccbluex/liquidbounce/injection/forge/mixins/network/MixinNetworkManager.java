package net.ccbluex.liquidbounce.injection.forge.mixins.network;

import io.netty.channel.ChannelHandlerContext;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({NetworkManager.class})
public class MixinNetworkManager {
   @Inject(
      method = {"sendPacket(Lnet/minecraft/network/Packet;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void send(Packet<?> lIIlIllllIlII, CallbackInfo lIIlIllllIIll) {
      long lIIlIlllIllll = new PacketEvent(lIIlIllllIlII);
      LiquidBounce.eventManager.callEvent(lIIlIlllIllll);
      if (lIIlIlllIllll.isCancelled()) {
         lIIlIllllIIll.cancel();
      }

   }

   @Inject(
      method = {"channelRead0"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void read(ChannelHandlerContext lIIlIllllllll, Packet<?> lIIlIlllllIll, CallbackInfo lIIlIlllllIlI) {
      byte lIIlIlllllIIl = new PacketEvent(lIIlIlllllIll);
      LiquidBounce.eventManager.callEvent(lIIlIlllllIIl);
      if (lIIlIlllllIIl.isCancelled()) {
         lIIlIlllllIlI.cancel();
      }

   }
}
