//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.utils;

import java.util.ArrayList;
import java.util.Comparator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.block.BlockPistonMoving;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.BlockWall;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;

public class AStarCustomPathFinder {
   // $FF: synthetic field
   private final CustomVec3 startCustomVec3;
   // $FF: synthetic field
   private final ArrayList<AStarCustomPathFinder.Hub> hubs = new ArrayList();
   // $FF: synthetic field
   private final CustomVec3 endCustomVec3;
   // $FF: synthetic field
   private static final Minecraft MC = Minecraft.getMinecraft();
   // $FF: synthetic field
   private final ArrayList<AStarCustomPathFinder.Hub> hubsToWork = new ArrayList();
   // $FF: synthetic field
   private ArrayList<CustomVec3> path = new ArrayList();
   // $FF: synthetic field
   private static final CustomVec3[] flatCardinalDirections = new CustomVec3[]{new CustomVec3(1.0D, 0.0D, 0.0D), new CustomVec3(-1.0D, 0.0D, 0.0D), new CustomVec3(0.0D, 0.0D, 1.0D), new CustomVec3(0.0D, 0.0D, -1.0D)};

   public boolean isFullCube() {
      return true;
   }

   public static boolean checkPositionValidity(CustomVec3 lIlllIlIIllIlll) {
      return true;
   }

   public void compute(int lIlllIIlllIlIlI, int lIlllIIlllIlIIl) {
      lIlllIIlllIlIll.path.clear();
      lIlllIIlllIlIll.hubsToWork.clear();
      ArrayList<CustomVec3> lIlllIIlllIlIII = new ArrayList();
      boolean lIlllIIlllIIlll = lIlllIIlllIlIll.startCustomVec3;
      lIlllIIlllIlIII.add(lIlllIIlllIIlll);
      boolean var10001 = false;
      CustomVec3[] lIlllIIlllIllII = flatCardinalDirections;
      lIlllIIlllIlIll.hubsToWork.add(new AStarCustomPathFinder.Hub(lIlllIIlllIIlll, (AStarCustomPathFinder.Hub)null, lIlllIIlllIlIII, lIlllIIlllIIlll.squareDistanceTo(lIlllIIlllIlIll.endCustomVec3), 0.0D, 0.0D));

      label54:
      for(int lIlllIIlllIIlIl = 0; lIlllIIlllIIlIl < lIlllIIlllIlIlI; ++lIlllIIlllIIlIl) {
         ArrayList<AStarCustomPathFinder.Hub> lIlllIIlllIIlII = lIlllIIlllIlIll.hubsToWork;
         int lIlllIIllllIlII = lIlllIIlllIIlII.size();
         lIlllIIlllIIlII.sort(new AStarCustomPathFinder.CompareHub());
         int lIlllIIllllIIll = 0;
         if (lIlllIIllllIlII == 0) {
            break;
         }

         for(int lIlllIIllllIllI = 0; lIlllIIllllIllI < lIlllIIllllIlII; ++lIlllIIllllIllI) {
            int lIlllIIllIlllll = (AStarCustomPathFinder.Hub)lIlllIIlllIIlII.get(lIlllIIllllIllI);
            ++lIlllIIllllIIll;
            if (lIlllIIllllIIll > lIlllIIlllIlIIl) {
               break;
            }

            lIlllIIlllIIlII.remove(lIlllIIllIlllll);
            var10001 = false;
            lIlllIIlllIlIll.hubs.add(lIlllIIllIlllll);
            var10001 = false;
            boolean lIlllIIllIllllI = lIlllIIllIlllll.getLoc();
            double lIlllIIllIlllIl = lIlllIIlllIllII.length;

            for(int lIlllIIllllllII = 0; lIlllIIllllllII < lIlllIIllIlllIl; ++lIlllIIllllllII) {
               CustomVec3 lIlllIIllllllIl = lIlllIIllIllllI.add(lIlllIIlllIllII[lIlllIIllllllII]).floor();
               if (checkPositionValidity(lIlllIIllllllIl) && lIlllIIlllIlIll.addHub(lIlllIIllIlllll, lIlllIIllllllIl, 0.0D)) {
                  break label54;
               }
            }

            CustomVec3 lIlllIIllllIlll = lIlllIIllIllllI.addVector(0.0D, 1.0D, 0.0D).floor();
            CustomVec3 lIlllIIlllllIll;
            if (checkPositionValidity(lIlllIIllllIlll) && lIlllIIlllIlIll.addHub(lIlllIIllIlllll, lIlllIIllllIlll, 0.0D) || checkPositionValidity(lIlllIIlllllIll = lIlllIIllIllllI.addVector(0.0D, -1.0D, 0.0D).floor()) && lIlllIIlllIlIll.addHub(lIlllIIllIlllll, lIlllIIlllllIll, 0.0D)) {
               break label54;
            }
         }
      }

      lIlllIIlllIlIll.hubs.sort(new AStarCustomPathFinder.CompareHub());
      lIlllIIlllIlIll.path = ((AStarCustomPathFinder.Hub)lIlllIIlllIlIll.hubs.get(0)).getPath();
   }

   public static boolean checkPositionValidity(int lIlllIlIIlllllI, int lIlllIlIIllllIl, int lIlllIlIlIIIIll, boolean lIlllIlIlIIIIlI) {
      double lIlllIlIIlllIlI = new BlockPos(lIlllIlIIlllllI, lIlllIlIIllllIl, lIlllIlIlIIIIll);
      BlockPos lIlllIlIlIIIIII = new BlockPos(lIlllIlIIlllllI, lIlllIlIIllllIl + 1, lIlllIlIlIIIIll);
      boolean lIlllIlIIlllIII = new BlockPos(lIlllIlIIlllllI, lIlllIlIIllllIl - 1, lIlllIlIlIIIIll);
      return !isBlockSolid(lIlllIlIIlllIlI) && !isBlockSolid(lIlllIlIlIIIIII) && (isBlockSolid(lIlllIlIIlllIII) || !lIlllIlIlIIIIlI) && isSafeToWalkOn(lIlllIlIIlllIII);
   }

   public AStarCustomPathFinder.Hub isHubExisting(CustomVec3 lIlllIIllIIIlII) {
      ArrayList<AStarCustomPathFinder.Hub> lIlllIIllIIIIll = lIlllIIllIIIlIl.hubs;
      int lIlllIIllIIlIII = lIlllIIllIIIIll.size();

      for(int lIlllIIllIIIIIl = 0; lIlllIIllIIIIIl < lIlllIIllIIlIII; ++lIlllIIllIIIIIl) {
         AStarCustomPathFinder.Hub lIlllIIllIlIIIl = (AStarCustomPathFinder.Hub)lIlllIIllIIIIll.get(lIlllIIllIIIIIl);
         byte lIlllIIlIllllll = lIlllIIllIlIIIl.getLoc();
         if (lIlllIIlIllllll.getX() == lIlllIIllIIIlII.getX() && lIlllIIlIllllll.getY() == lIlllIIllIIIlII.getY() && lIlllIIlIllllll.getZ() == lIlllIIllIIIlII.getZ()) {
            return lIlllIIllIlIIIl;
         }
      }

      ArrayList<AStarCustomPathFinder.Hub> lIlllIIllIIIlll = lIlllIIllIIIlIl.hubsToWork;
      int lIlllIIllIIIIII = lIlllIIllIIIlll.size();

      for(int lIlllIIllIIllII = 0; lIlllIIllIIllII < lIlllIIllIIIIII; ++lIlllIIllIIllII) {
         double lIlllIIlIlllllI = (AStarCustomPathFinder.Hub)lIlllIIllIIIlll.get(lIlllIIllIIllII);
         float lIlllIIlIllllIl = lIlllIIlIlllllI.getLoc();
         if (lIlllIIlIllllIl.getX() == lIlllIIllIIIlII.getX() && lIlllIIlIllllIl.getY() == lIlllIIllIIIlII.getY() && lIlllIIlIllllIl.getZ() == lIlllIIllIIIlII.getZ()) {
            return lIlllIIlIlllllI;
         }
      }

      return null;
   }

   public AStarCustomPathFinder(CustomVec3 lIlllIlIlIlIlII, CustomVec3 lIlllIlIlIlIIll) {
      lIlllIlIlIllIII.startCustomVec3 = lIlllIlIlIlIlII.addVector(0.0D, 0.0D, 0.0D).floor();
      lIlllIlIlIllIII.endCustomVec3 = lIlllIlIlIlIIll.addVector(0.0D, 0.0D, 0.0D).floor();
   }

   public void compute() {
      lIlllIlIIIlIlIl.compute(1000, 4);
   }

   public ArrayList<CustomVec3> getPath() {
      return lIlllIlIIIllIlI.path;
   }

   private static boolean isSafeToWalkOn(BlockPos lIlllIlIIIllllI) {
      Block lIlllIlIIIlllll = MC.theWorld.getBlockState(lIlllIlIIIllllI).getBlock();
      return !(lIlllIlIIIlllll instanceof BlockFence) && !(lIlllIlIIIlllll instanceof BlockWall);
   }

   public boolean addHub(AStarCustomPathFinder.Hub lIlllIIlIllIIll, CustomVec3 lIlllIIlIlIlIlI, double lIlllIIlIllIIIl) {
      AStarCustomPathFinder.Hub lIlllIIlIllIIII = lIlllIIlIllIlII.isHubExisting(lIlllIIlIlIlIlI);
      byte lIlllIIlIlIIlll = lIlllIIlIllIIIl;
      if (lIlllIIlIllIIll != null) {
         lIlllIIlIlIIlll = lIlllIIlIllIIIl + lIlllIIlIllIIll.getTotalCost();
      }

      CustomVec3 lIlllIIlIlIlllI = lIlllIIlIllIlII.endCustomVec3;
      ArrayList<CustomVec3> lIlllIIlIlIIlIl = lIlllIIlIllIIll.getPath();
      boolean var10001;
      if (lIlllIIlIllIIII == null) {
         if (lIlllIIlIlIlIlI.getX() == lIlllIIlIlIlllI.getX() && lIlllIIlIlIlIlI.getY() == lIlllIIlIlIlllI.getY() && lIlllIIlIlIlIlI.getZ() == lIlllIIlIlIlllI.getZ()) {
            lIlllIIlIllIlII.path.clear();
            lIlllIIlIllIlII.path = lIlllIIlIlIIlIl;
            lIlllIIlIllIlII.path.add(lIlllIIlIlIlIlI);
            var10001 = false;
            return true;
         }

         lIlllIIlIlIIlIl.add(lIlllIIlIlIlIlI);
         var10001 = false;
         lIlllIIlIllIlII.hubsToWork.add(new AStarCustomPathFinder.Hub(lIlllIIlIlIlIlI, lIlllIIlIllIIll, lIlllIIlIlIIlIl, lIlllIIlIlIlIlI.squareDistanceTo(lIlllIIlIlIlllI), lIlllIIlIllIIIl, lIlllIIlIlIIlll));
         var10001 = false;
      } else if (lIlllIIlIllIIII.getCost() > lIlllIIlIllIIIl) {
         lIlllIIlIlIIlIl.add(lIlllIIlIlIlIlI);
         var10001 = false;
         lIlllIIlIllIIII.setLoc(lIlllIIlIlIlIlI);
         lIlllIIlIllIIII.setParent(lIlllIIlIllIIll);
         lIlllIIlIllIIII.setPath(lIlllIIlIlIIlIl);
         lIlllIIlIllIIII.setSquareDistanceToFromTarget(lIlllIIlIlIlIlI.squareDistanceTo(lIlllIIlIlIlllI));
         lIlllIIlIllIIII.setCost(lIlllIIlIllIIIl);
         lIlllIIlIllIIII.setTotalCost(lIlllIIlIlIIlll);
      }

      return false;
   }

   private static boolean isBlockSolid(BlockPos lIlllIlIIlIllII) {
      Block lIlllIlIIlIllIl = MC.theWorld.getBlockState(lIlllIlIIlIllII).getBlock();
      return lIlllIlIIlIllIl instanceof BlockSlab || lIlllIlIIlIllIl instanceof BlockStairs || lIlllIlIIlIllIl instanceof BlockCactus || lIlllIlIIlIllIl instanceof BlockChest || lIlllIlIIlIllIl instanceof BlockEnderChest || lIlllIlIIlIllIl instanceof BlockSkull || lIlllIlIIlIllIl instanceof BlockPane || lIlllIlIIlIllIl instanceof BlockFence || lIlllIlIIlIllIl instanceof BlockWall || lIlllIlIIlIllIl instanceof BlockGlass || lIlllIlIIlIllIl instanceof BlockPistonBase || lIlllIlIIlIllIl instanceof BlockPistonExtension || lIlllIlIIlIllIl instanceof BlockPistonMoving || lIlllIlIIlIllIl instanceof BlockStainedGlass || lIlllIlIIlIllIl instanceof BlockTrapDoor;
   }

   public static boolean checkPositionValidity(CustomVec3 lIlllIlIlIIlllI, boolean lIlllIlIlIIllll) {
      return checkPositionValidity((int)lIlllIlIlIIlllI.getX(), (int)lIlllIlIlIIlllI.getY(), (int)lIlllIlIlIIlllI.getZ(), lIlllIlIlIIllll);
   }

   public static class CompareHub implements Comparator<AStarCustomPathFinder.Hub> {
      public int compare(AStarCustomPathFinder.Hub lllllllllllllllllllllllIlIIlIlII, AStarCustomPathFinder.Hub lllllllllllllllllllllllIlIIlIlIl) {
         return (int)(lllllllllllllllllllllllIlIIlIlII.getSquareDistanceToFromTarget() + lllllllllllllllllllllllIlIIlIlII.getTotalCost() - (lllllllllllllllllllllllIlIIlIlIl.getSquareDistanceToFromTarget() + lllllllllllllllllllllllIlIIlIlIl.getTotalCost()));
      }
   }

   private static class Hub {
      // $FF: synthetic field
      private double totalCost;
      // $FF: synthetic field
      private AStarCustomPathFinder.Hub parent;
      // $FF: synthetic field
      private double squareDistanceToFromTarget;
      // $FF: synthetic field
      private ArrayList<CustomVec3> path;
      // $FF: synthetic field
      private double cost;
      // $FF: synthetic field
      private CustomVec3 loc;

      public void setPath(ArrayList<CustomVec3> llIIIIlIlIlllll) {
         llIIIIlIlIllllI.path = llIIIIlIlIlllll;
      }

      public double getSquareDistanceToFromTarget() {
         return llIIIIlIlIllIll.squareDistanceToFromTarget;
      }

      public double getCost() {
         return llIIIIlIlIlIIlI.cost;
      }

      public ArrayList<CustomVec3> getPath() {
         return llIIIIlIllIIlII.path;
      }

      public AStarCustomPathFinder.Hub getParent() {
         return llIIIIlIllIllII.parent;
      }

      public Hub(CustomVec3 llIIIIlIlllllIl, AStarCustomPathFinder.Hub llIIIIllIIIIIll, ArrayList<CustomVec3> llIIIIllIIIIIlI, double llIIIIlIllllIlI, double llIIIIllIIIIIII, double llIIIIlIllllIII) {
         llIIIIllIIIIlIl.loc = llIIIIlIlllllIl;
         llIIIIllIIIIlIl.parent = llIIIIllIIIIIll;
         llIIIIllIIIIlIl.path = llIIIIllIIIIIlI;
         llIIIIllIIIIlIl.squareDistanceToFromTarget = llIIIIlIllllIlI;
         llIIIIllIIIIlIl.cost = llIIIIllIIIIIII;
         llIIIIllIIIIlIl.totalCost = llIIIIlIllllIII;
      }

      public void setLoc(CustomVec3 llIIIIlIllIllll) {
         llIIIIlIlllIIII.loc = llIIIIlIllIllll;
      }

      public double getTotalCost() {
         return llIIIIlIlIIlIII.totalCost;
      }

      public void setCost(double llIIIIlIlIIlIll) {
         llIIIIlIlIIlllI.cost = llIIIIlIlIIlIll;
      }

      public void setSquareDistanceToFromTarget(double llIIIIlIlIlIllI) {
         llIIIIlIlIlIlIl.squareDistanceToFromTarget = llIIIIlIlIlIllI;
      }

      public CustomVec3 getLoc() {
         return llIIIIlIlllIllI.loc;
      }

      public void setParent(AStarCustomPathFinder.Hub llIIIIlIllIlIII) {
         llIIIIlIllIlIIl.parent = llIIIIlIllIlIII;
      }

      public void setTotalCost(double llIIIIlIlIIIlII) {
         llIIIIlIlIIIIll.totalCost = llIIIIlIlIIIlII;
      }
   }
}
