package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0017\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\t"},
   d2 = {"Lnet/ccbluex/liquidbounce/event/EventLivingBaseUpdate;", "Lnet/ccbluex/liquidbounce/event/Event;", "Ent", "Lnet/minecraft/entity/Entity;", "entity", "(Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/Entity;)V", "getEnt", "()Lnet/minecraft/entity/Entity;", "getEntity", "LiquidSense"}
)
public final class EventLivingBaseUpdate extends Event {
   // $FF: synthetic field
   @Nullable
   private final Entity Ent;
   // $FF: synthetic field
   @NotNull
   private final Entity entity;

   public EventLivingBaseUpdate(@Nullable Entity lIlllIllIIlIlII, @NotNull Entity lIlllIllIIlIIll) {
      Intrinsics.checkParameterIsNotNull(lIlllIllIIlIIll, "entity");
      super();
      lIlllIllIIlIIlI.Ent = lIlllIllIIlIlII;
      lIlllIllIIlIIlI.entity = lIlllIllIIlIIll;
   }

   @Nullable
   public final Entity getEnt() {
      return lIlllIllIIlllIl.Ent;
   }

   @NotNull
   public final Entity getEntity() {
      return lIlllIllIIllIIl.entity;
   }
}
