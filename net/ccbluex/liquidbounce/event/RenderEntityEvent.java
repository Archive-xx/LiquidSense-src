package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\f\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\t¢\u0006\u0002\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\n\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0012R\u0011\u0010\u0007\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0012¨\u0006\u0015"},
   d2 = {"Lnet/ccbluex/liquidbounce/event/RenderEntityEvent;", "Lnet/ccbluex/liquidbounce/event/Event;", "entity", "Lnet/minecraft/entity/Entity;", "x", "", "y", "z", "entityYaw", "", "partialTicks", "(Lnet/minecraft/entity/Entity;DDDFF)V", "getEntity", "()Lnet/minecraft/entity/Entity;", "getEntityYaw", "()F", "getPartialTicks", "getX", "()D", "getY", "getZ", "LiquidSense"}
)
public final class RenderEntityEvent extends Event {
   // $FF: synthetic field
   private final double z;
   // $FF: synthetic field
   @NotNull
   private final Entity entity;
   // $FF: synthetic field
   private final float partialTicks;
   // $FF: synthetic field
   private final double x;
   // $FF: synthetic field
   private final float entityYaw;
   // $FF: synthetic field
   private final double y;

   @NotNull
   public final Entity getEntity() {
      return llllllllllllllllllIIlIIllIIIlIll.entity;
   }

   public final double getY() {
      return llllllllllllllllllIIlIIllIIIIlII.y;
   }

   public final float getEntityYaw() {
      return llllllllllllllllllIIlIIlIllllllI.entityYaw;
   }

   public final float getPartialTicks() {
      return llllllllllllllllllIIlIIlIlllllII.partialTicks;
   }

   public RenderEntityEvent(@NotNull Entity llllllllllllllllllIIlIIlIllIlIll, double llllllllllllllllllIIlIIlIlllIIIl, double llllllllllllllllllIIlIIlIlllIIII, double llllllllllllllllllIIlIIlIllIllll, float llllllllllllllllllIIlIIlIllIIlll, float llllllllllllllllllIIlIIlIllIllIl) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIIlIIlIllIlIll, "entity");
      super();
      llllllllllllllllllIIlIIlIlllIIll.entity = llllllllllllllllllIIlIIlIllIlIll;
      llllllllllllllllllIIlIIlIlllIIll.x = llllllllllllllllllIIlIIlIlllIIIl;
      llllllllllllllllllIIlIIlIlllIIll.y = llllllllllllllllllIIlIIlIlllIIII;
      llllllllllllllllllIIlIIlIlllIIll.z = llllllllllllllllllIIlIIlIllIllll;
      llllllllllllllllllIIlIIlIlllIIll.entityYaw = llllllllllllllllllIIlIIlIllIIlll;
      llllllllllllllllllIIlIIlIlllIIll.partialTicks = llllllllllllllllllIIlIIlIllIllIl;
   }

   public final double getZ() {
      return llllllllllllllllllIIlIIllIIIIIIl.z;
   }

   public final double getX() {
      return llllllllllllllllllIIlIIllIIIIlll.x;
   }
}
