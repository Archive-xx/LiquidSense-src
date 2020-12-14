package net.ccbluex.liquidbounce.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.utils.block.PlaceInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"},
   d2 = {"Lnet/ccbluex/liquidbounce/utils/PlaceRotation;", "", "placeInfo", "Lnet/ccbluex/liquidbounce/utils/block/PlaceInfo;", "rotation", "Lnet/ccbluex/liquidbounce/utils/Rotation;", "(Lnet/ccbluex/liquidbounce/utils/block/PlaceInfo;Lnet/ccbluex/liquidbounce/utils/Rotation;)V", "getPlaceInfo", "()Lnet/ccbluex/liquidbounce/utils/block/PlaceInfo;", "getRotation", "()Lnet/ccbluex/liquidbounce/utils/Rotation;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "", "LiquidSense"}
)
public final class PlaceRotation {
   // $FF: synthetic field
   @NotNull
   private final PlaceInfo placeInfo;
   // $FF: synthetic field
   @NotNull
   private final Rotation rotation;

   @NotNull
   public final PlaceRotation copy(@NotNull PlaceInfo lIIlIIlIlIllIII, @NotNull Rotation lIIlIIlIlIlIlIl) {
      Intrinsics.checkParameterIsNotNull(lIIlIIlIlIllIII, "placeInfo");
      Intrinsics.checkParameterIsNotNull(lIIlIIlIlIlIlIl, "rotation");
      return new PlaceRotation(lIIlIIlIlIllIII, lIIlIIlIlIlIlIl);
   }

   // $FF: synthetic method
   public static PlaceRotation copy$default(PlaceRotation lIIlIIlIlIlIIII, PlaceInfo var1, Rotation var2, int lIIlIIlIlIIllIl, Object var4) {
      if ((lIIlIIlIlIIllIl & 1) != 0) {
         var1 = lIIlIIlIlIlIIII.placeInfo;
      }

      if ((lIIlIIlIlIIllIl & 2) != 0) {
         var2 = lIIlIIlIlIlIIII.rotation;
      }

      return lIIlIIlIlIlIIII.copy(var1, var2);
   }

   @NotNull
   public final Rotation getRotation() {
      return lIIlIIlIllIlIll.rotation;
   }

   @NotNull
   public final PlaceInfo getPlaceInfo() {
      return lIIlIIlIllIlllI.placeInfo;
   }

   public boolean equals(@Nullable Object var1) {
      if (this != var1) {
         if (var1 instanceof PlaceRotation) {
            PlaceRotation var2 = (PlaceRotation)var1;
            if (Intrinsics.areEqual((Object)this.placeInfo, (Object)var2.placeInfo) && Intrinsics.areEqual((Object)this.rotation, (Object)var2.rotation)) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }

   public int hashCode() {
      PlaceInfo var10000 = lIIlIIlIlIIlIll.placeInfo;
      int var1;
      if (var10000 != null) {
         var1 = var10000.hashCode();
      } else {
         boolean var10001 = false;
         var1 = 0;
      }

      var1 *= 31;
      Rotation var2 = lIIlIIlIlIIlIll.rotation;
      int var3;
      if (var2 != null) {
         var3 = var2.hashCode();
      } else {
         boolean var10002 = false;
         var3 = 0;
      }

      return var1 + var3;
   }

   @NotNull
   public final Rotation component2() {
      return lIIlIIlIlIlllII.rotation;
   }

   @NotNull
   public String toString() {
      return String.valueOf((new StringBuilder()).append("PlaceRotation(placeInfo=").append(this.placeInfo).append(", rotation=").append(this.rotation).append(")"));
   }

   public PlaceRotation(@NotNull PlaceInfo lIIlIIlIllIIllI, @NotNull Rotation lIIlIIlIllIIIlI) {
      Intrinsics.checkParameterIsNotNull(lIIlIIlIllIIllI, "placeInfo");
      Intrinsics.checkParameterIsNotNull(lIIlIIlIllIIIlI, "rotation");
      super();
      lIIlIIlIllIIlII.placeInfo = lIIlIIlIllIIllI;
      lIIlIIlIllIIlII.rotation = lIIlIIlIllIIIlI;
   }

   @NotNull
   public final PlaceInfo component1() {
      return lIIlIIlIlIlllll.placeInfo;
   }
}
