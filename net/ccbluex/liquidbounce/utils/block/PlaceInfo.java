//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.utils.block;

import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u0000 \u00112\u00020\u0001:\u0001\u0011B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010¨\u0006\u0012"},
   d2 = {"Lnet/ccbluex/liquidbounce/utils/block/PlaceInfo;", "", "blockPos", "Lnet/minecraft/util/BlockPos;", "enumFacing", "Lnet/minecraft/util/EnumFacing;", "vec3", "Lnet/minecraft/util/Vec3;", "(Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;Lnet/minecraft/util/Vec3;)V", "getBlockPos", "()Lnet/minecraft/util/BlockPos;", "getEnumFacing", "()Lnet/minecraft/util/EnumFacing;", "getVec3", "()Lnet/minecraft/util/Vec3;", "setVec3", "(Lnet/minecraft/util/Vec3;)V", "Companion", "LiquidSense"}
)
public final class PlaceInfo {
   // $FF: synthetic field
   @NotNull
   private Vec3 vec3;
   // $FF: synthetic field
   @NotNull
   private final EnumFacing enumFacing;
   // $FF: synthetic field
   @NotNull
   private final BlockPos blockPos;
   // $FF: synthetic field
   public static final PlaceInfo.Companion Companion = new PlaceInfo.Companion((DefaultConstructorMarker)null);

   @NotNull
   public final BlockPos getBlockPos() {
      return lIIIIIlIIlll.blockPos;
   }

   @NotNull
   public final Vec3 getVec3() {
      return lIIIIIlIIIII.vec3;
   }

   public final void setVec3(@NotNull Vec3 lIIIIIIllIlI) {
      Intrinsics.checkParameterIsNotNull(lIIIIIIllIlI, "<set-?>");
      lIIIIIIllIll.vec3 = lIIIIIIllIlI;
   }

   @NotNull
   public final EnumFacing getEnumFacing() {
      return lIIIIIlIIlII.enumFacing;
   }

   @JvmStatic
   @Nullable
   public static final PlaceInfo get(@NotNull BlockPos lIIIIIIIIIlI) {
      return Companion.get(lIIIIIIIIIlI);
   }

   // $FF: synthetic method
   public PlaceInfo(BlockPos lIIIIIIIIlll, EnumFacing var2, Vec3 var3, int var4, DefaultConstructorMarker var5) {
      if ((var4 & 4) != 0) {
         var3 = new Vec3((double)lIIIIIIIIlll.getX() + 0.5D, (double)lIIIIIIIIlll.getY() + 0.5D, (double)lIIIIIIIIlll.getZ() + 0.5D);
      }

      this(lIIIIIIIIlll, var2, var3);
   }

   public PlaceInfo(@NotNull BlockPos lIIIIIIlIlII, @NotNull EnumFacing lIIIIIIIllll, @NotNull Vec3 lIIIIIIlIIlI) {
      Intrinsics.checkParameterIsNotNull(lIIIIIIlIlII, "blockPos");
      Intrinsics.checkParameterIsNotNull(lIIIIIIIllll, "enumFacing");
      Intrinsics.checkParameterIsNotNull(lIIIIIIlIIlI, "vec3");
      super();
      lIIIIIIlIIIl.blockPos = lIIIIIIlIlII;
      lIIIIIIlIIIl.enumFacing = lIIIIIIIllll;
      lIIIIIIlIIIl.vec3 = lIIIIIIlIIlI;
   }

   @Metadata(
      mv = {1, 1, 16},
      bv = {1, 0, 3},
      k = 1,
      d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"},
      d2 = {"Lnet/ccbluex/liquidbounce/utils/block/PlaceInfo$Companion;", "", "()V", "get", "Lnet/ccbluex/liquidbounce/utils/block/PlaceInfo;", "blockPos", "Lnet/minecraft/util/BlockPos;", "LiquidSense"}
   )
   public static final class Companion {
      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker lIIllllIllIllI) {
         this();
      }

      @JvmStatic
      @Nullable
      public final PlaceInfo get(@NotNull BlockPos lIIllllIllllII) {
         Intrinsics.checkParameterIsNotNull(lIIllllIllllII, "blockPos");
         BlockPos var10002;
         if (BlockUtils.canBeClicked(lIIllllIllllII.add(0, -1, 0))) {
            var10002 = lIIllllIllllII.add(0, -1, 0);
            Intrinsics.checkExpressionValueIsNotNull(var10002, "blockPos.add(0, -1, 0)");
            return new PlaceInfo(var10002, EnumFacing.UP, (Vec3)null, 4, (DefaultConstructorMarker)null);
         } else if (BlockUtils.canBeClicked(lIIllllIllllII.add(0, 0, 1))) {
            var10002 = lIIllllIllllII.add(0, 0, 1);
            Intrinsics.checkExpressionValueIsNotNull(var10002, "blockPos.add(0, 0, 1)");
            return new PlaceInfo(var10002, EnumFacing.NORTH, (Vec3)null, 4, (DefaultConstructorMarker)null);
         } else if (BlockUtils.canBeClicked(lIIllllIllllII.add(-1, 0, 0))) {
            var10002 = lIIllllIllllII.add(-1, 0, 0);
            Intrinsics.checkExpressionValueIsNotNull(var10002, "blockPos.add(-1, 0, 0)");
            return new PlaceInfo(var10002, EnumFacing.EAST, (Vec3)null, 4, (DefaultConstructorMarker)null);
         } else if (BlockUtils.canBeClicked(lIIllllIllllII.add(0, 0, -1))) {
            var10002 = lIIllllIllllII.add(0, 0, -1);
            Intrinsics.checkExpressionValueIsNotNull(var10002, "blockPos.add(0, 0, -1)");
            return new PlaceInfo(var10002, EnumFacing.SOUTH, (Vec3)null, 4, (DefaultConstructorMarker)null);
         } else {
            PlaceInfo var10000;
            if (BlockUtils.canBeClicked(lIIllllIllllII.add(1, 0, 0))) {
               var10002 = lIIllllIllllII.add(1, 0, 0);
               Intrinsics.checkExpressionValueIsNotNull(var10002, "blockPos.add(1, 0, 0)");
               var10000 = new PlaceInfo(var10002, EnumFacing.WEST, (Vec3)null, 4, (DefaultConstructorMarker)null);
            } else {
               var10000 = null;
            }

            return var10000;
         }
      }
   }
}
