//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.utils;

import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

public class PathfindingUtils {
   // $FF: synthetic field
   private static final Minecraft mc = Minecraft.getMinecraft();

   private static boolean canPassThrow(BlockPos lIIIIlllIllIl) {
      boolean lIIIIlllIlIlI = Minecraft.getMinecraft().theWorld.getBlockState(lIIIIlllIllIl).getBlock();
      return lIIIIlllIlIlI.getMaterial() == Material.air || lIIIIlllIlIlI.getMaterial() == Material.plants || lIIIIlllIlIlI.getMaterial() == Material.vine || lIIIIlllIlIlI == Blocks.ladder || lIIIIlllIlIlI == Blocks.water || lIIIIlllIlIlI == Blocks.flowing_water || lIIIIlllIlIlI == Blocks.wall_sign || lIIIIlllIlIlI == Blocks.standing_sign;
   }

   public static ArrayList<CustomVec3> computePath(CustomVec3 lIIIlIIIIIIll, CustomVec3 lIIIlIIIIIIlI) {
      if (!canPassThrow(new BlockPos(lIIIlIIIIIIll.mc()))) {
         lIIIlIIIIIIll = lIIIlIIIIIIll.addVector(0.0D, 1.0D, 0.0D);
      }

      int lIIIlIIIIIIIl = new AStarCustomPathFinder(lIIIlIIIIIIll, lIIIlIIIIIIlI);
      lIIIlIIIIIIIl.compute();
      int lIIIlIIIIIIII = 0;
      char lIIIIllllllll = null;
      boolean lIIIIlllllllI = null;
      ArrayList<CustomVec3> lIIIlIIIIIlIl = new ArrayList();
      ArrayList<CustomVec3> lIIIIllllllII = lIIIlIIIIIIIl.getPath();

      for(Iterator lIIIIlllllIll = lIIIIllllllII.iterator(); lIIIIlllllIll.hasNext(); ++lIIIlIIIIIIII) {
         CustomVec3 lIIIlIIIIllII = (CustomVec3)lIIIIlllllIll.next();
         boolean var10001;
         if (lIIIlIIIIIIII != 0 && lIIIlIIIIIIII != lIIIIllllllII.size() - 1) {
            boolean lIIIlIIIIllIl = true;
            if (lIIIlIIIIllII.squareDistanceTo(lIIIIlllllllI) > 100.0D) {
               lIIIlIIIIllIl = false;
            } else {
               double lIIIlIIIlIlII = Math.min(lIIIIlllllllI.getX(), lIIIlIIIIllII.getX());
               double lIIIlIIIlIIll = Math.min(lIIIIlllllllI.getY(), lIIIlIIIIllII.getY());
               boolean lIIIIllllIllI = Math.min(lIIIIlllllllI.getZ(), lIIIlIIIIllII.getZ());
               double lIIIlIIIlIIIl = Math.max(lIIIIlllllllI.getX(), lIIIlIIIIllII.getX());
               Exception lIIIIllllIlII = Math.max(lIIIIlllllllI.getY(), lIIIlIIIIllII.getY());
               Exception lIIIIllllIIll = Math.max(lIIIIlllllllI.getZ(), lIIIlIIIIllII.getZ());

               label54:
               for(int lIIIlIIIIlllI = (int)lIIIlIIIlIlII; (double)lIIIlIIIIlllI <= lIIIlIIIlIIIl; ++lIIIlIIIIlllI) {
                  for(int lIIIIllllIIIl = (int)lIIIlIIIlIIll; (double)lIIIIllllIIIl <= lIIIIllllIlII; ++lIIIIllllIIIl) {
                     for(int lIIIlIIIlIllI = (int)lIIIIllllIllI; (double)lIIIlIIIlIllI <= lIIIIllllIIll; ++lIIIlIIIlIllI) {
                        if (!AStarCustomPathFinder.checkPositionValidity(new CustomVec3((double)lIIIlIIIIlllI, (double)lIIIIllllIIIl, (double)lIIIlIIIlIllI))) {
                           lIIIlIIIIllIl = false;
                           break label54;
                        }
                     }
                  }
               }
            }

            if (!lIIIlIIIIllIl) {
               lIIIlIIIIIlIl.add(lIIIIllllllll.addVector(0.5D, 0.0D, 0.5D));
               var10001 = false;
               lIIIIlllllllI = lIIIIllllllll;
            }
         } else {
            if (lIIIIllllllll != null) {
               lIIIlIIIIIlIl.add(lIIIIllllllll.addVector(0.5D, 0.0D, 0.5D));
               var10001 = false;
            }

            lIIIlIIIIIlIl.add(lIIIlIIIIllII.addVector(0.5D, 0.0D, 0.5D));
            var10001 = false;
            lIIIIlllllllI = lIIIlIIIIllII;
         }

         lIIIIllllllll = lIIIlIIIIllII;
      }

      return lIIIlIIIIIlIl;
   }
}
