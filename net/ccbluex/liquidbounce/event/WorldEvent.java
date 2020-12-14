package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;
import net.minecraft.client.multiplayer.WorldClient;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"},
   d2 = {"Lnet/ccbluex/liquidbounce/event/WorldEvent;", "Lnet/ccbluex/liquidbounce/event/Event;", "worldClient", "Lnet/minecraft/client/multiplayer/WorldClient;", "(Lnet/minecraft/client/multiplayer/WorldClient;)V", "getWorldClient", "()Lnet/minecraft/client/multiplayer/WorldClient;", "LiquidSense"}
)
public final class WorldEvent extends Event {
   // $FF: synthetic field
   @Nullable
   private final WorldClient worldClient;

   @Nullable
   public final WorldClient getWorldClient() {
      return lIlllllIIIIIll.worldClient;
   }

   public WorldEvent(@Nullable WorldClient lIllllIllllllI) {
      lIllllIlllllll.worldClient = lIllllIllllllI;
   }
}
