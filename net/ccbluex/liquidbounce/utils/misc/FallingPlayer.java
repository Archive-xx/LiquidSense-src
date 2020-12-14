//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.utils.misc;

import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import org.jetbrains.annotations.Nullable;

public class FallingPlayer extends MinecraftInstance {
   // $FF: synthetic field
   private float strafe;
   // $FF: synthetic field
   private double motionY;
   // $FF: synthetic field
   private double motionZ;
   // $FF: synthetic field
   private double x;
   // $FF: synthetic field
   private double motionX;
   // $FF: synthetic field
   private double y;
   // $FF: synthetic field
   private float forward;
   // $FF: synthetic field
   private double z;
   // $FF: synthetic field
   private final float yaw;

   private void calculateForTick() {
      lllllllllllllllllIllIlIlIlllIlIl.strafe *= 0.98F;
      lllllllllllllllllIllIlIlIlllIlIl.forward *= 0.98F;
      float lllllllllllllllllIllIlIlIlllIIll = lllllllllllllllllIllIlIlIlllIlIl.strafe * lllllllllllllllllIllIlIlIlllIlIl.strafe + lllllllllllllllllIllIlIlIlllIlIl.forward * lllllllllllllllllIllIlIlIlllIlIl.forward;
      if (lllllllllllllllllIllIlIlIlllIIll >= 1.0E-4F) {
         lllllllllllllllllIllIlIlIlllIIll = MathHelper.sqrt_float(lllllllllllllllllIllIlIlIlllIIll);
         if (lllllllllllllllllIllIlIlIlllIIll < 1.0F) {
            lllllllllllllllllIllIlIlIlllIIll = 1.0F;
         }

         lllllllllllllllllIllIlIlIlllIIll = mc.thePlayer.jumpMovementFactor / lllllllllllllllllIllIlIlIlllIIll;
         lllllllllllllllllIllIlIlIlllIlIl.strafe *= lllllllllllllllllIllIlIlIlllIIll;
         lllllllllllllllllIllIlIlIlllIlIl.forward *= lllllllllllllllllIllIlIlIlllIIll;
         float lllllllllllllllllIllIlIlIllllIII = MathHelper.sin(lllllllllllllllllIllIlIlIlllIlIl.yaw * 3.1415927F / 180.0F);
         float lllllllllllllllllIllIlIlIlllIllI = MathHelper.cos(lllllllllllllllllIllIlIlIlllIlIl.yaw * 3.1415927F / 180.0F);
         lllllllllllllllllIllIlIlIlllIlIl.motionX += (double)(lllllllllllllllllIllIlIlIlllIlIl.strafe * lllllllllllllllllIllIlIlIlllIllI - lllllllllllllllllIllIlIlIlllIlIl.forward * lllllllllllllllllIllIlIlIllllIII);
         lllllllllllllllllIllIlIlIlllIlIl.motionZ += (double)(lllllllllllllllllIllIlIlIlllIlIl.forward * lllllllllllllllllIllIlIlIlllIllI + lllllllllllllllllIllIlIlIlllIlIl.strafe * lllllllllllllllllIllIlIlIllllIII);
      }

      lllllllllllllllllIllIlIlIlllIlIl.motionY -= 0.08D;
      lllllllllllllllllIllIlIlIlllIlIl.motionX *= 0.91D;
      lllllllllllllllllIllIlIlIlllIlIl.motionY *= 0.9800000190734863D;
      lllllllllllllllllIllIlIlIlllIlIl.motionY *= 0.91D;
      lllllllllllllllllIllIlIlIlllIlIl.motionZ *= 0.91D;
      lllllllllllllllllIllIlIlIlllIlIl.x += lllllllllllllllllIllIlIlIlllIlIl.motionX;
      lllllllllllllllllIllIlIlIlllIlIl.y += lllllllllllllllllIllIlIlIlllIlIl.motionY;
      lllllllllllllllllIllIlIlIlllIlIl.z += lllllllllllllllllIllIlIlIlllIlIl.motionZ;
   }

   public FallingPlayer(double lllllllllllllllllIllIlIllIIIIlll, double lllllllllllllllllIllIlIllIIlIIII, double lllllllllllllllllIllIlIllIIIIlIl, double lllllllllllllllllIllIlIllIIIIlII, double lllllllllllllllllIllIlIllIIIIIll, double lllllllllllllllllIllIlIllIIIllII, float lllllllllllllllllIllIlIllIIIlIll, float lllllllllllllllllIllIlIllIIIlIlI, float lllllllllllllllllIllIlIllIIIlIIl) {
      lllllllllllllllllIllIlIllIIlIIll.x = lllllllllllllllllIllIlIllIIIIlll;
      lllllllllllllllllIllIlIllIIlIIll.y = lllllllllllllllllIllIlIllIIlIIII;
      lllllllllllllllllIllIlIllIIlIIll.z = lllllllllllllllllIllIlIllIIIIlIl;
      lllllllllllllllllIllIlIllIIlIIll.motionX = lllllllllllllllllIllIlIllIIIIlII;
      lllllllllllllllllIllIlIllIIlIIll.motionY = lllllllllllllllllIllIlIllIIIIIll;
      lllllllllllllllllIllIlIllIIlIIll.motionZ = lllllllllllllllllIllIlIllIIIllII;
      lllllllllllllllllIllIlIllIIlIIll.yaw = lllllllllllllllllIllIlIllIIIlIll;
      lllllllllllllllllIllIlIllIIlIIll.strafe = lllllllllllllllllIllIlIllIIIlIlI;
      lllllllllllllllllIllIlIllIIlIIll.forward = lllllllllllllllllIllIlIllIIIlIIl;
   }

   public FallingPlayer.CollisionResult findCollision(int lllllllllllllllllIllIlIlIlIIIlIl) {
      for(int lllllllllllllllllIllIlIlIlIIlIII = 0; lllllllllllllllllIllIlIlIlIIlIII < lllllllllllllllllIllIlIlIlIIIlIl; ++lllllllllllllllllIllIlIlIlIIlIII) {
         boolean lllllllllllllllllIllIlIlIIllllll = new Vec3(lllllllllllllllllIllIlIlIlIIIllI.x, lllllllllllllllllIllIlIlIlIIIllI.y, lllllllllllllllllIllIlIlIlIIIllI.z);
         lllllllllllllllllIllIlIlIlIIIllI.calculateForTick();
         Vec3 lllllllllllllllllIllIlIlIlIIlllI = new Vec3(lllllllllllllllllIllIlIlIlIIIllI.x, lllllllllllllllllIllIlIlIlIIIllI.y, lllllllllllllllllIllIlIlIlIIIllI.z);
         Exception lllllllllllllllllIllIlIlIIlllIIl = mc.thePlayer.width / 2.0F;
         BlockPos lllllllllllllllllIllIlIlIIlllIll;
         if ((lllllllllllllllllIllIlIlIIlllIll = lllllllllllllllllIllIlIlIlIIIllI.rayTrace(lllllllllllllllllIllIlIlIIllllll, lllllllllllllllllIllIlIlIlIIlllI)) != null) {
            return new FallingPlayer.CollisionResult(lllllllllllllllllIllIlIlIIlllIll, lllllllllllllllllIllIlIlIlIIlIII);
         }

         if ((lllllllllllllllllIllIlIlIIlllIll = lllllllllllllllllIllIlIlIlIIIllI.rayTrace(lllllllllllllllllIllIlIlIIllllll.addVector((double)lllllllllllllllllIllIlIlIIlllIIl, 0.0D, (double)lllllllllllllllllIllIlIlIIlllIIl), lllllllllllllllllIllIlIlIlIIlllI)) != null) {
            return new FallingPlayer.CollisionResult(lllllllllllllllllIllIlIlIIlllIll, lllllllllllllllllIllIlIlIlIIlIII);
         }

         if ((lllllllllllllllllIllIlIlIIlllIll = lllllllllllllllllIllIlIlIlIIIllI.rayTrace(lllllllllllllllllIllIlIlIIllllll.addVector((double)(-lllllllllllllllllIllIlIlIIlllIIl), 0.0D, (double)lllllllllllllllllIllIlIlIIlllIIl), lllllllllllllllllIllIlIlIlIIlllI)) != null) {
            return new FallingPlayer.CollisionResult(lllllllllllllllllIllIlIlIIlllIll, lllllllllllllllllIllIlIlIlIIlIII);
         }

         if ((lllllllllllllllllIllIlIlIIlllIll = lllllllllllllllllIllIlIlIlIIIllI.rayTrace(lllllllllllllllllIllIlIlIIllllll.addVector((double)lllllllllllllllllIllIlIlIIlllIIl, 0.0D, (double)(-lllllllllllllllllIllIlIlIIlllIIl)), lllllllllllllllllIllIlIlIlIIlllI)) != null) {
            return new FallingPlayer.CollisionResult(lllllllllllllllllIllIlIlIIlllIll, lllllllllllllllllIllIlIlIlIIlIII);
         }

         if ((lllllllllllllllllIllIlIlIIlllIll = lllllllllllllllllIllIlIlIlIIIllI.rayTrace(lllllllllllllllllIllIlIlIIllllll.addVector((double)(-lllllllllllllllllIllIlIlIIlllIIl), 0.0D, (double)(-lllllllllllllllllIllIlIlIIlllIIl)), lllllllllllllllllIllIlIlIlIIlllI)) != null) {
            return new FallingPlayer.CollisionResult(lllllllllllllllllIllIlIlIIlllIll, lllllllllllllllllIllIlIlIlIIlIII);
         }

         if ((lllllllllllllllllIllIlIlIIlllIll = lllllllllllllllllIllIlIlIlIIIllI.rayTrace(lllllllllllllllllIllIlIlIIllllll.addVector((double)lllllllllllllllllIllIlIlIIlllIIl, 0.0D, (double)(lllllllllllllllllIllIlIlIIlllIIl / 2.0F)), lllllllllllllllllIllIlIlIlIIlllI)) != null) {
            return new FallingPlayer.CollisionResult(lllllllllllllllllIllIlIlIIlllIll, lllllllllllllllllIllIlIlIlIIlIII);
         }

         if ((lllllllllllllllllIllIlIlIIlllIll = lllllllllllllllllIllIlIlIlIIIllI.rayTrace(lllllllllllllllllIllIlIlIIllllll.addVector((double)(-lllllllllllllllllIllIlIlIIlllIIl), 0.0D, (double)(lllllllllllllllllIllIlIlIIlllIIl / 2.0F)), lllllllllllllllllIllIlIlIlIIlllI)) != null) {
            return new FallingPlayer.CollisionResult(lllllllllllllllllIllIlIlIIlllIll, lllllllllllllllllIllIlIlIlIIlIII);
         }

         if ((lllllllllllllllllIllIlIlIIlllIll = lllllllllllllllllIllIlIlIlIIIllI.rayTrace(lllllllllllllllllIllIlIlIIllllll.addVector((double)(lllllllllllllllllIllIlIlIIlllIIl / 2.0F), 0.0D, (double)lllllllllllllllllIllIlIlIIlllIIl), lllllllllllllllllIllIlIlIlIIlllI)) != null) {
            return new FallingPlayer.CollisionResult(lllllllllllllllllIllIlIlIIlllIll, lllllllllllllllllIllIlIlIlIIlIII);
         }

         if ((lllllllllllllllllIllIlIlIIlllIll = lllllllllllllllllIllIlIlIlIIIllI.rayTrace(lllllllllllllllllIllIlIlIIllllll.addVector((double)(lllllllllllllllllIllIlIlIIlllIIl / 2.0F), 0.0D, (double)(-lllllllllllllllllIllIlIlIIlllIIl)), lllllllllllllllllIllIlIlIlIIlllI)) != null) {
            return new FallingPlayer.CollisionResult(lllllllllllllllllIllIlIlIIlllIll, lllllllllllllllllIllIlIlIlIIlIII);
         }
      }

      return null;
   }

   @Nullable
   private BlockPos rayTrace(Vec3 lllllllllllllllllIllIlIlIIlIlIIl, Vec3 lllllllllllllllllIllIlIlIIlIIlIl) {
      MovingObjectPosition lllllllllllllllllIllIlIlIIlIIlll = mc.theWorld.rayTraceBlocks(lllllllllllllllllIllIlIlIIlIlIIl, lllllllllllllllllIllIlIlIIlIIlIl, true);
      return lllllllllllllllllIllIlIlIIlIIlll != null && lllllllllllllllllIllIlIlIIlIIlll.typeOfHit == MovingObjectType.BLOCK && lllllllllllllllllIllIlIlIIlIIlll.sideHit == EnumFacing.UP ? lllllllllllllllllIllIlIlIIlIIlll.getBlockPos() : null;
   }

   public static class CollisionResult {
      // $FF: synthetic field
      private final BlockPos pos;
      // $FF: synthetic field
      private final int tick;

      public BlockPos getPos() {
         return llllIIIIlIlIIIl.pos;
      }

      public CollisionResult(BlockPos llllIIIIlIlIlII, int llllIIIIlIlIIll) {
         llllIIIIlIlIlIl.pos = llllIIIIlIlIlII;
         llllIIIIlIlIlIl.tick = llllIIIIlIlIIll;
      }

      public int getTick() {
         return llllIIIIlIIlllI.tick;
      }
   }
}
