package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.entity.player.EntityPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"},
   d2 = {"Lnet/ccbluex/liquidbounce/event/UpdateModelEvent;", "Lnet/ccbluex/liquidbounce/event/Event;", "player", "Lnet/minecraft/entity/player/EntityPlayer;", "model", "Lnet/minecraft/client/model/ModelPlayer;", "(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/client/model/ModelPlayer;)V", "getModel", "()Lnet/minecraft/client/model/ModelPlayer;", "getPlayer", "()Lnet/minecraft/entity/player/EntityPlayer;", "LiquidSense"}
)
public final class UpdateModelEvent extends Event {
   // $FF: synthetic field
   @NotNull
   private final EntityPlayer player;
   // $FF: synthetic field
   @NotNull
   private final ModelPlayer model;

   public UpdateModelEvent(@NotNull EntityPlayer lllllllllllllllllllIllIlIllIllIl, @NotNull ModelPlayer lllllllllllllllllllIllIlIllIllII) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllllIllIlIllIllIl, "player");
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllllIllIlIllIllII, "model");
      super();
      lllllllllllllllllllIllIlIllIlIll.player = lllllllllllllllllllIllIlIllIllIl;
      lllllllllllllllllllIllIlIllIlIll.model = lllllllllllllllllllIllIlIllIllII;
   }

   @NotNull
   public final EntityPlayer getPlayer() {
      return lllllllllllllllllllIllIlIlllIllI.player;
   }

   @NotNull
   public final ModelPlayer getModel() {
      return lllllllllllllllllllIllIlIlllIIlI.model;
   }
}
