package net.ccbluex.liquidbounce.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.util.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"},
   d2 = {"Lnet/ccbluex/liquidbounce/utils/VecRotation;", "", "vec", "Lnet/minecraft/util/Vec3;", "rotation", "Lnet/ccbluex/liquidbounce/utils/Rotation;", "(Lnet/minecraft/util/Vec3;Lnet/ccbluex/liquidbounce/utils/Rotation;)V", "getRotation", "()Lnet/ccbluex/liquidbounce/utils/Rotation;", "getVec", "()Lnet/minecraft/util/Vec3;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "", "LiquidSense"}
)
public final class VecRotation {
   // $FF: synthetic field
   @NotNull
   private final Rotation rotation;
   // $FF: synthetic field
   @NotNull
   private final Vec3 vec;

   @NotNull
   public final Rotation component2() {
      return llIIllIlIIllIll.rotation;
   }

   @NotNull
   public final VecRotation copy(@NotNull Vec3 llIIllIlIIlIlll, @NotNull Rotation llIIllIlIIlIlII) {
      Intrinsics.checkParameterIsNotNull(llIIllIlIIlIlll, "vec");
      Intrinsics.checkParameterIsNotNull(llIIllIlIIlIlII, "rotation");
      return new VecRotation(llIIllIlIIlIlll, llIIllIlIIlIlII);
   }

   @NotNull
   public final Rotation getRotation() {
      return llIIllIlIllIIII.rotation;
   }

   @NotNull
   public final Vec3 getVec() {
      return llIIllIlIllIIlI.vec;
   }

   public int hashCode() {
      Vec3 var10000 = llIIllIIlllIIlI.vec;
      int var1;
      if (var10000 != null) {
         var1 = var10000.hashCode();
      } else {
         boolean var10001 = false;
         var1 = 0;
      }

      var1 *= 31;
      Rotation var2 = llIIllIIlllIIlI.rotation;
      int var3;
      if (var2 != null) {
         var3 = var2.hashCode();
      } else {
         boolean var10002 = false;
         var3 = 0;
      }

      return var1 + var3;
   }

   // $FF: synthetic method
   public static VecRotation copy$default(VecRotation llIIllIlIIIllll, Vec3 var1, Rotation var2, int llIIllIlIIIllII, Object var4) {
      if ((llIIllIlIIIllII & 1) != 0) {
         var1 = llIIllIlIIIllll.vec;
      }

      if ((llIIllIlIIIllII & 2) != 0) {
         var2 = llIIllIlIIIllll.rotation;
      }

      return llIIllIlIIIllll.copy(var1, var2);
   }

   public VecRotation(@NotNull Vec3 llIIllIlIlIIlIl, @NotNull Rotation llIIllIlIlIIlll) {
      Intrinsics.checkParameterIsNotNull(llIIllIlIlIIlIl, "vec");
      Intrinsics.checkParameterIsNotNull(llIIllIlIlIIlll, "rotation");
      super();
      llIIllIlIlIIllI.vec = llIIllIlIlIIlIl;
      llIIllIlIlIIllI.rotation = llIIllIlIlIIlll;
   }

   public boolean equals(@Nullable Object var1) {
      if (this != var1) {
         if (var1 instanceof VecRotation) {
            VecRotation var2 = (VecRotation)var1;
            if (Intrinsics.areEqual((Object)this.vec, (Object)var2.vec) && Intrinsics.areEqual((Object)this.rotation, (Object)var2.rotation)) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }

   @NotNull
   public String toString() {
      return String.valueOf((new StringBuilder()).append("VecRotation(vec=").append(this.vec).append(", rotation=").append(this.rotation).append(")"));
   }

   @NotNull
   public final Vec3 component1() {
      return llIIllIlIIlllll.vec;
   }
}
