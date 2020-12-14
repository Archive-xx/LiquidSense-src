//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.utils;

import com.google.common.base.Predicates;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public final class RaycastUtils extends MinecraftInstance {
   public static Entity raycastEntity(double lIIIllIlIlIIlI, RaycastUtils.IEntityFilter lIIIllIlIlIIll) {
      return raycastEntity(lIIIllIlIlIIlI, RotationUtils.serverRotation.getYaw(), RotationUtils.serverRotation.getPitch(), lIIIllIlIlIIll);
   }

   private static Entity raycastEntity(double lIIIllIIIllllI, float lIIIllIIIlllIl, float lIIIllIIIlIIlI, RaycastUtils.IEntityFilter lIIIllIIIlIIII) {
      Entity lIIIllIIIllIII = mc.getRenderViewEntity();
      if (lIIIllIIIllIII != null && mc.theWorld != null) {
         double lIIIllIIlIlIII = lIIIllIIIllllI;
         int lIIIllIIIIlIlI = lIIIllIIIllIII.getPositionEyes(1.0F);
         float lIIIllIIlIIllI = MathHelper.cos(-lIIIllIIIlllIl * 0.017453292F - 3.1415927F);
         float lIIIllIIlIIlIl = MathHelper.sin(-lIIIllIIIlllIl * 0.017453292F - 3.1415927F);
         long lIIIllIIIIIlIl = -MathHelper.cos(-lIIIllIIIlIIlI * 0.017453292F);
         boolean lIIIllIIIIIlII = MathHelper.sin(-lIIIllIIIlIIlI * 0.017453292F);
         long lIIIllIIIIIIlI = new Vec3((double)(lIIIllIIlIIlIl * lIIIllIIIIIlIl), (double)lIIIllIIIIIlII, (double)(lIIIllIIlIIllI * lIIIllIIIIIlIl));
         Vec3 lIIIllIIlIIIIl = lIIIllIIIIlIlI.addVector(lIIIllIIIIIIlI.xCoord * lIIIllIIIllllI, lIIIllIIIIIIlI.yCoord * lIIIllIIIllllI, lIIIllIIIIIIlI.zCoord * lIIIllIIIllllI);
         List<Entity> lIIIllIIlIIIII = mc.theWorld.getEntitiesInAABBexcluding(lIIIllIIIllIII, lIIIllIIIllIII.getEntityBoundingBox().addCoord(lIIIllIIIIIIlI.xCoord * lIIIllIIIllllI, lIIIllIIIIIIlI.yCoord * lIIIllIIIllllI, lIIIllIIIIIIlI.zCoord * lIIIllIIIllllI).expand(1.0D, 1.0D, 1.0D), Predicates.and(EntitySelectors.NOT_SPECTATING, Entity::canBeCollidedWith));
         Exception lIIIlIllllllII = null;
         Iterator lIIIlIlllllIlI = lIIIllIIlIIIII.iterator();

         while(true) {
            while(true) {
               Entity lIIIllIIlIlIIl;
               do {
                  if (!lIIIlIlllllIlI.hasNext()) {
                     return lIIIlIllllllII;
                  }

                  lIIIllIIlIlIIl = (Entity)lIIIlIlllllIlI.next();
               } while(!lIIIllIIIlIIII.canRaycast(lIIIllIIlIlIIl));

               float lIIIllIIlIllII = lIIIllIIlIlIIl.getCollisionBorderSize();
               AxisAlignedBB lIIIllIIlIlIll = lIIIllIIlIlIIl.getEntityBoundingBox().expand((double)lIIIllIIlIllII, (double)lIIIllIIlIllII, (double)lIIIllIIlIllII);
               float lIIIlIllllIIlI = lIIIllIIlIlIll.calculateIntercept(lIIIllIIIIlIlI, lIIIllIIlIIIIl);
               if (lIIIllIIlIlIll.isVecInside(lIIIllIIIIlIlI)) {
                  if (lIIIllIIlIlIII >= 0.0D) {
                     lIIIlIllllllII = lIIIllIIlIlIIl;
                     lIIIllIIlIlIII = 0.0D;
                  }
               } else if (lIIIlIllllIIlI != null) {
                  String lIIIlIllllIIII = lIIIllIIIIlIlI.distanceTo(lIIIlIllllIIlI.hitVec);
                  if (lIIIlIllllIIII < lIIIllIIlIlIII || lIIIllIIlIlIII == 0.0D) {
                     if (lIIIllIIlIlIIl == lIIIllIIIllIII.ridingEntity && !lIIIllIIIllIII.canRiderInteract()) {
                        if (lIIIllIIlIlIII == 0.0D) {
                           lIIIlIllllllII = lIIIllIIlIlIIl;
                        }
                     } else {
                        lIIIlIllllllII = lIIIllIIlIlIIl;
                        lIIIllIIlIlIII = lIIIlIllllIIII;
                     }
                  }
               }
            }
         }
      } else {
         return null;
      }
   }

   public interface IEntityFilter {
      boolean canRaycast(Entity var1);
   }
}
