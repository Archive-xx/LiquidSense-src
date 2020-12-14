package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"},
   d2 = {"Lnet/ccbluex/liquidbounce/event/AttackEvent;", "Lnet/ccbluex/liquidbounce/event/Event;", "targetEntity", "Lnet/minecraft/entity/Entity;", "(Lnet/minecraft/entity/Entity;)V", "getTargetEntity", "()Lnet/minecraft/entity/Entity;", "LiquidSense"}
)
public final class AttackEvent extends Event {
   // $FF: synthetic field
   @Nullable
   private final Entity targetEntity;

   public AttackEvent(@Nullable Entity lIIlIlIlIIlIl) {
      lIIlIlIlIIllI.targetEntity = lIIlIlIlIIlIl;
   }

   @Nullable
   public final Entity getTargetEntity() {
      return lIIlIlIlIlIIl.targetEntity;
   }
}
