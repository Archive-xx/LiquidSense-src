package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.Packet;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0011\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003¢\u0006\u0002\u0010\u0004R\u0015\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"},
   d2 = {"Lnet/ccbluex/liquidbounce/event/PacketSenEvent;", "Lnet/ccbluex/liquidbounce/event/CancellableEvent;", "packet", "Lnet/minecraft/network/Packet;", "(Lnet/minecraft/network/Packet;)V", "getPacket", "()Lnet/minecraft/network/Packet;", "LiquidSense"}
)
public final class PacketSenEvent extends CancellableEvent {
   // $FF: synthetic field
   @NotNull
   private final Packet<?> packet;

   @NotNull
   public final Packet<?> getPacket() {
      return llllllllllllllllllIIlIllllIllllI.packet;
   }

   public PacketSenEvent(@NotNull Packet<?> llllllllllllllllllIIlIllllIlIlll) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIIlIllllIlIlll, "packet");
      super();
      llllllllllllllllllIIlIllllIllIlI.packet = llllllllllllllllllIIlIllllIlIlll;
   }
}
