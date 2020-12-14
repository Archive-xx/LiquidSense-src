package net.ccbluex.liquidbounce.cape;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\t\u0010\r\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00052\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0004\u0010\u0007\"\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0015"},
   d2 = {"Lnet/ccbluex/liquidbounce/cape/CapeInfo;", "", "resourceLocation", "Lnet/minecraft/util/ResourceLocation;", "isCapeAvailable", "", "(Lnet/minecraft/util/ResourceLocation;Z)V", "()Z", "setCapeAvailable", "(Z)V", "getResourceLocation", "()Lnet/minecraft/util/ResourceLocation;", "component1", "component2", "copy", "equals", "other", "hashCode", "", "toString", "", "LiquidSense"}
)
public final class CapeInfo {
   // $FF: synthetic field
   @NotNull
   private final ResourceLocation resourceLocation;
   // $FF: synthetic field
   private boolean isCapeAvailable;

   public final void setCapeAvailable(boolean lllllllllllllllllllllIllIlIIllll) {
      lllllllllllllllllllllIllIlIlIIII.isCapeAvailable = lllllllllllllllllllllIllIlIIllll;
   }

   public boolean equals(@Nullable Object var1) {
      if (this != var1) {
         if (var1 instanceof CapeInfo) {
            CapeInfo var2 = (CapeInfo)var1;
            if (Intrinsics.areEqual((Object)this.resourceLocation, (Object)var2.resourceLocation) && this.isCapeAvailable == var2.isCapeAvailable) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }

   // $FF: synthetic method
   public static CapeInfo copy$default(CapeInfo lllllllllllllllllllllIllIIlIllII, ResourceLocation var1, boolean var2, int lllllllllllllllllllllIllIIlIlIIl, Object var4) {
      if ((lllllllllllllllllllllIllIIlIlIIl & 1) != 0) {
         var1 = lllllllllllllllllllllIllIIlIllII.resourceLocation;
      }

      if ((lllllllllllllllllllllIllIIlIlIIl & 2) != 0) {
         var2 = lllllllllllllllllllllIllIIlIllII.isCapeAvailable;
      }

      return lllllllllllllllllllllIllIIlIllII.copy(var1, var2);
   }

   public CapeInfo(@NotNull ResourceLocation lllllllllllllllllllllIllIlIIlIlI, boolean lllllllllllllllllllllIllIlIIlIIl) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllllllIllIlIIlIlI, "resourceLocation");
      super();
      lllllllllllllllllllllIllIlIIlIII.resourceLocation = lllllllllllllllllllllIllIlIIlIlI;
      lllllllllllllllllllllIllIlIIlIII.isCapeAvailable = lllllllllllllllllllllIllIlIIlIIl;
   }

   public final boolean isCapeAvailable() {
      return lllllllllllllllllllllIllIlIlIlIl.isCapeAvailable;
   }

   public int hashCode() {
      ResourceLocation var10000 = lllllllllllllllllllllIllIIlIIlll.resourceLocation;
      int var1;
      if (var10000 != null) {
         var1 = var10000.hashCode();
      } else {
         boolean var10001 = false;
         var1 = 0;
      }

      var1 *= 31;
      byte var2 = lllllllllllllllllllllIllIIlIIlll.isCapeAvailable;
      if (var2 != 0) {
         boolean var10002 = false;
         var2 = 1;
      }

      return var1 + var2;
   }

   @NotNull
   public final ResourceLocation getResourceLocation() {
      return lllllllllllllllllllllIllIlIllIIl.resourceLocation;
   }

   @NotNull
   public String toString() {
      return String.valueOf((new StringBuilder()).append("CapeInfo(resourceLocation=").append(this.resourceLocation).append(", isCapeAvailable=").append(this.isCapeAvailable).append(")"));
   }

   @NotNull
   public final ResourceLocation component1() {
      return lllllllllllllllllllllIllIIlllIll.resourceLocation;
   }

   public final boolean component2() {
      return lllllllllllllllllllllIllIIlllIII.isCapeAvailable;
   }

   @NotNull
   public final CapeInfo copy(@NotNull ResourceLocation lllllllllllllllllllllIllIIllIIlI, boolean lllllllllllllllllllllIllIIllIIIl) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllllllIllIIllIIlI, "resourceLocation");
      return new CapeInfo(lllllllllllllllllllllIllIIllIIlI, lllllllllllllllllllllIllIIllIIIl);
   }

   // $FF: synthetic method
   public CapeInfo(ResourceLocation var1, boolean var2, int var3, DefaultConstructorMarker var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      this(var1, var2);
   }
}
