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
   d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0011"},
   d2 = {"Lnet/ccbluex/liquidbounce/event/EntityMovementEvent;", "Lnet/ccbluex/liquidbounce/event/Event;", "movedEntity", "Lnet/minecraft/entity/Entity;", "(Lnet/minecraft/entity/Entity;)V", "getMovedEntity", "()Lnet/minecraft/entity/Entity;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "LiquidSense"}
)
public final class EntityMovementEvent extends Event {
   // $FF: synthetic field
   @NotNull
   private final Entity movedEntity;

   @NotNull
   public final Entity getMovedEntity() {
      return llllllllllllllllllllIlIlllIlllIl.movedEntity;
   }

   public boolean equals(@Nullable Object var1) {
      if (this != var1) {
         if (var1 instanceof EntityMovementEvent) {
            EntityMovementEvent var2 = (EntityMovementEvent)var1;
            if (Intrinsics.areEqual((Object)this.movedEntity, (Object)var2.movedEntity)) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }

   @NotNull
   public final Entity component1() {
      return llllllllllllllllllllIlIlllIIlllI.movedEntity;
   }

   @NotNull
   public String toString() {
      return String.valueOf((new StringBuilder()).append("EntityMovementEvent(movedEntity=").append(this.movedEntity).append(")"));
   }

   public EntityMovementEvent(@NotNull Entity llllllllllllllllllllIlIlllIlIlII) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllllIlIlllIlIlII, "movedEntity");
      super();
      llllllllllllllllllllIlIlllIlIllI.movedEntity = llllllllllllllllllllIlIlllIlIlII;
   }

   @NotNull
   public final EntityMovementEvent copy(@NotNull Entity llllllllllllllllllllIlIlllIIIlII) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllllIlIlllIIIlII, "movedEntity");
      return new EntityMovementEvent(llllllllllllllllllllIlIlllIIIlII);
   }

   public int hashCode() {
      Entity var10000 = this.movedEntity;
      int var1;
      if (var10000 != null) {
         var1 = var10000.hashCode();
      } else {
         boolean var10001 = false;
         var1 = 0;
      }

      return var1;
   }

   // $FF: synthetic method
   public static EntityMovementEvent copy$default(EntityMovementEvent var0, Entity var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = var0.movedEntity;
      }

      return var0.copy(var1);
   }
}
