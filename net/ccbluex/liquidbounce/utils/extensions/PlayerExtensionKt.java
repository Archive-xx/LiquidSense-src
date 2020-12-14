//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.utils.extensions;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 2,
   d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0016\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u0012\u0010\u0005\u001a\u00020\u0006*\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007¨\u0006\t"},
   d2 = {"getNearestPointBB", "Lnet/minecraft/util/Vec3;", "eye", "box", "Lnet/minecraft/util/AxisAlignedBB;", "getDistanceToEntityBox", "", "Lnet/minecraft/entity/Entity;", "entity", "LiquidSense"}
)
public final class PlayerExtensionKt {
   @NotNull
   public static final Vec3 getNearestPointBB(@NotNull Vec3 lllllllllllllllllllIIIIllIIlllll, @NotNull AxisAlignedBB lllllllllllllllllllIIIIllIlIIIII) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllllIIIIllIIlllll, "eye");
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllllIIIIllIlIIIII, "box");
      double[] lllllllllllllllllllIIIIllIlIIIlI = new double[]{lllllllllllllllllllIIIIllIIlllll.xCoord, lllllllllllllllllllIIIIllIIlllll.yCoord, lllllllllllllllllllIIIIllIIlllll.zCoord};
      boolean lllllllllllllllllllIIIIllIIlllII = new double[]{lllllllllllllllllllIIIIllIlIIIII.minX, lllllllllllllllllllIIIIllIlIIIII.minY, lllllllllllllllllllIIIIllIlIIIII.minZ};
      double[] lllllllllllllllllllIIIIllIlIIlII = new double[]{lllllllllllllllllllIIIIllIlIIIII.maxX, lllllllllllllllllllIIIIllIlIIIII.maxY, lllllllllllllllllllIIIIllIlIIIII.maxZ};
      float lllllllllllllllllllIIIIllIlIIlIl = 0;

      for(byte lllllllllllllllllllIIIIllIIllIIl = 2; lllllllllllllllllllIIIIllIlIIlIl <= lllllllllllllllllllIIIIllIIllIIl; ++lllllllllllllllllllIIIIllIlIIlIl) {
         if (lllllllllllllllllllIIIIllIlIIIlI[lllllllllllllllllllIIIIllIlIIlIl] > lllllllllllllllllllIIIIllIlIIlII[lllllllllllllllllllIIIIllIlIIlIl]) {
            lllllllllllllllllllIIIIllIlIIIlI[lllllllllllllllllllIIIIllIlIIlIl] = lllllllllllllllllllIIIIllIlIIlII[lllllllllllllllllllIIIIllIlIIlIl];
         } else if (lllllllllllllllllllIIIIllIlIIIlI[lllllllllllllllllllIIIIllIlIIlIl] < lllllllllllllllllllIIIIllIIlllII[lllllllllllllllllllIIIIllIlIIlIl]) {
            lllllllllllllllllllIIIIllIlIIIlI[lllllllllllllllllllIIIIllIlIIlIl] = lllllllllllllllllllIIIIllIIlllII[lllllllllllllllllllIIIIllIlIIlIl];
         }
      }

      return new Vec3(lllllllllllllllllllIIIIllIlIIIlI[0], lllllllllllllllllllIIIIllIlIIIlI[1], lllllllllllllllllllIIIIllIlIIIlI[2]);
   }

   public static final double getDistanceToEntityBox(@NotNull Entity lllllllllllllllllllIIIIllIlllIII, @NotNull Entity lllllllllllllllllllIIIIllIllIllI) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllllIIIIllIlllIII, "$this$getDistanceToEntityBox");
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllllIIIIllIllIllI, "entity");
      float lllllllllllllllllllIIIIllIllIlIl = lllllllllllllllllllIIIIllIlllIII.getPositionEyes(0.0F);
      Intrinsics.checkExpressionValueIsNotNull(lllllllllllllllllllIIIIllIllIlIl, "eyes");
      AxisAlignedBB var10001 = lllllllllllllllllllIIIIllIllIllI.getEntityBoundingBox();
      Intrinsics.checkExpressionValueIsNotNull(var10001, "entity.entityBoundingBox");
      Vec3 lllllllllllllllllllIIIIllIllllII = getNearestPointBB(lllllllllllllllllllIIIIllIllIlIl, var10001);
      char lllllllllllllllllllIIIIllIllIIlI = lllllllllllllllllllIIIIllIllllII.xCoord - lllllllllllllllllllIIIIllIllIlIl.xCoord;
      String lllllllllllllllllllIIIIllIllIIIl = false;
      byte lllllllllllllllllllIIIIllIllIIll = Math.abs(lllllllllllllllllllIIIIllIllIIlI);
      String lllllllllllllllllllIIIIllIllllll = lllllllllllllllllllIIIIllIllllII.yCoord - lllllllllllllllllllIIIIllIllIlIl.yCoord;
      byte lllllllllllllllllllIIIIllIllIIII = false;
      lllllllllllllllllllIIIIllIllIIlI = Math.abs(lllllllllllllllllllIIIIllIllllll);
      byte lllllllllllllllllllIIIIllIllIIII = lllllllllllllllllllIIIIllIllllII.zCoord - lllllllllllllllllllIIIIllIllIlIl.zCoord;
      Exception lllllllllllllllllllIIIIllIlIllll = false;
      lllllllllllllllllllIIIIllIllllll = Math.abs(lllllllllllllllllllIIIIllIllIIII);
      Exception lllllllllllllllllllIIIIllIlIllll = 2;
      String lllllllllllllllllllIIIIllIlIlllI = false;
      double var10000 = Math.pow(lllllllllllllllllllIIIIllIllIIll, (double)lllllllllllllllllllIIIIllIlIllll);
      lllllllllllllllllllIIIIllIlIllll = 2;
      int lllllllllllllllllllIIIIllIlIllIl = var10000;
      lllllllllllllllllllIIIIllIlIlllI = false;
      short lllllllllllllllllllIIIIllIllIlll = Math.pow(lllllllllllllllllllIIIIllIllIIlI, (double)lllllllllllllllllllIIIIllIlIllll);
      var10000 = lllllllllllllllllllIIIIllIlIllIl + lllllllllllllllllllIIIIllIllIlll;
      lllllllllllllllllllIIIIllIlIllll = 2;
      lllllllllllllllllllIIIIllIlIllIl = var10000;
      lllllllllllllllllllIIIIllIlIlllI = false;
      lllllllllllllllllllIIIIllIllIlll = Math.pow(lllllllllllllllllllIIIIllIllllll, (double)lllllllllllllllllllIIIIllIlIllll);
      lllllllllllllllllllIIIIllIllIIII = lllllllllllllllllllIIIIllIlIllIl + lllllllllllllllllllIIIIllIllIlll;
      lllllllllllllllllllIIIIllIlIllll = false;
      return Math.sqrt(lllllllllllllllllllIIIIllIllIIII);
   }
}
