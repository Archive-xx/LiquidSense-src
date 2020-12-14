//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.utils;

import net.ccbluex.liquidbounce.event.MoveEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public final class MovementUtils extends MinecraftInstance {
   public static boolean isInLiquid() {
      if (Minecraft.getMinecraft().thePlayer.isInWater()) {
         return true;
      } else {
         boolean llllllllllllllllllIlIlIlllllIIII = false;
         char llllllllllllllllllIlIlIllllIlllI = (int)Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().minY;

         for(int llllllllllllllllllIlIlIllllIllIl = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().minX); llllllllllllllllllIlIlIllllIllIl < MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().maxX) + 1; ++llllllllllllllllllIlIlIllllIllIl) {
            for(int llllllllllllllllllIlIlIllllIlIll = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().minZ); llllllllllllllllllIlIlIllllIlIll < MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().maxZ) + 1; ++llllllllllllllllllIlIlIllllIlIll) {
               Block llllllllllllllllllIlIlIlllllIlIl = Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(llllllllllllllllllIlIlIllllIllIl, llllllllllllllllllIlIlIllllIlllI, llllllllllllllllllIlIlIllllIlIll)).getBlock();
               if (llllllllllllllllllIlIlIlllllIlIl != null && llllllllllllllllllIlIlIlllllIlIl.getMaterial() != Material.air) {
                  if (!(llllllllllllllllllIlIlIlllllIlIl instanceof BlockLiquid)) {
                     return false;
                  }

                  llllllllllllllllllIlIlIlllllIIII = true;
               }
            }
         }

         return llllllllllllllllllIlIlIlllllIIII;
      }
   }

   public static void setMoveSpeed(MoveEvent llllllllllllllllllIlIllIllIllllI, double llllllllllllllllllIlIllIllIllIll) {
      double llllllllllllllllllIlIllIlllIIlll = (double)mc.thePlayer.moveForward;
      double llllllllllllllllllIlIllIlllIIlII = (double)mc.thePlayer.moveStrafing;
      float llllllllllllllllllIlIllIlllIIIIl = mc.thePlayer.rotationYaw;
      if (llllllllllllllllllIlIllIlllIIlll == 0.0D && llllllllllllllllllIlIllIlllIIlII == 0.0D) {
         llllllllllllllllllIlIllIllIllllI.setX(0.0D);
         llllllllllllllllllIlIllIllIllllI.setZ(0.0D);
      } else {
         if (llllllllllllllllllIlIllIlllIIlll != 0.0D) {
            if (llllllllllllllllllIlIllIlllIIlII > 0.0D) {
               llllllllllllllllllIlIllIlllIIIIl += (float)(llllllllllllllllllIlIllIlllIIlll > 0.0D ? -45 : 45);
            } else if (llllllllllllllllllIlIllIlllIIlII < 0.0D) {
               llllllllllllllllllIlIllIlllIIIIl += (float)(llllllllllllllllllIlIllIlllIIlll > 0.0D ? 45 : -45);
            }

            llllllllllllllllllIlIllIlllIIlII = 0.0D;
            if (llllllllllllllllllIlIllIlllIIlll > 0.0D) {
               llllllllllllllllllIlIllIlllIIlll = 1.0D;
            } else if (llllllllllllllllllIlIllIlllIIlll < 0.0D) {
               llllllllllllllllllIlIllIlllIIlll = -1.0D;
            }
         }

         llllllllllllllllllIlIllIllIllllI.setX(llllllllllllllllllIlIllIlllIIlll * llllllllllllllllllIlIllIllIllIll * Math.cos(Math.toRadians((double)(llllllllllllllllllIlIllIlllIIIIl + 90.0F))) + llllllllllllllllllIlIllIlllIIlII * llllllllllllllllllIlIllIllIllIll * Math.sin(Math.toRadians((double)(llllllllllllllllllIlIllIlllIIIIl + 90.0F))));
         llllllllllllllllllIlIllIllIllllI.setZ(llllllllllllllllllIlIllIlllIIlll * llllllllllllllllllIlIllIllIllIll * Math.sin(Math.toRadians((double)(llllllllllllllllllIlIllIlllIIIIl + 90.0F))) - llllllllllllllllllIlIllIlllIIlII * llllllllllllllllllIlIllIllIllIll * Math.cos(Math.toRadians((double)(llllllllllllllllllIlIllIlllIIIIl + 90.0F))));
      }

   }

   public static void forward(double llllllllllllllllllIlIlIlIIlIllII) {
      float llllllllllllllllllIlIlIlIIlIlIIl = Math.toRadians((double)mc.thePlayer.rotationYaw);
      mc.thePlayer.setPosition(mc.thePlayer.posX + -Math.sin(llllllllllllllllllIlIlIlIIlIlIIl) * llllllllllllllllllIlIlIlIIlIllII, mc.thePlayer.posY, mc.thePlayer.posZ + Math.cos(llllllllllllllllllIlIlIlIIlIlIIl) * llllllllllllllllllIlIlIlIIlIllII);
   }

   public static void setMotion(MoveEvent llllllllllllllllllIlIlIllIIIIIII, double llllllllllllllllllIlIlIlIlllllIl) {
      double llllllllllllllllllIlIlIlIllllIlI = (double)mc.thePlayer.movementInput.moveForward;
      byte llllllllllllllllllIlIlIlIllIlllI = (double)mc.thePlayer.movementInput.moveStrafe;
      Exception llllllllllllllllllIlIlIlIllIllIl = (double)mc.thePlayer.rotationYaw;
      if (llllllllllllllllllIlIlIlIllllIlI == 0.0D && llllllllllllllllllIlIlIlIllIlllI == 0.0D) {
         mc.thePlayer.motionX = 0.0D;
         mc.thePlayer.motionZ = 0.0D;
      } else {
         if (llllllllllllllllllIlIlIlIllllIlI != 0.0D) {
            if (llllllllllllllllllIlIlIlIllIlllI > 0.0D) {
               llllllllllllllllllIlIlIlIllIllIl += (double)(llllllllllllllllllIlIlIlIllllIlI > 0.0D ? -45 : 45);
            } else if (llllllllllllllllllIlIlIlIllIlllI < 0.0D) {
               llllllllllllllllllIlIlIlIllIllIl += (double)(llllllllllllllllllIlIlIlIllllIlI > 0.0D ? 45 : -45);
            }

            llllllllllllllllllIlIlIlIllIlllI = 0.0D;
            if (llllllllllllllllllIlIlIlIllllIlI > 0.0D) {
               llllllllllllllllllIlIlIlIllllIlI = 1.0D;
            } else if (llllllllllllllllllIlIlIlIllllIlI < 0.0D) {
               llllllllllllllllllIlIlIlIllllIlI = -1.0D;
            }
         }

         llllllllllllllllllIlIlIllIIIIIII.setX(llllllllllllllllllIlIlIlIllllIlI * llllllllllllllllllIlIlIlIlllllIl * Math.cos(Math.toRadians(llllllllllllllllllIlIlIlIllIllIl + 90.0D)) + llllllllllllllllllIlIlIlIllIlllI * llllllllllllllllllIlIlIlIlllllIl * Math.sin(Math.toRadians(llllllllllllllllllIlIlIlIllIllIl + 90.0D)));
         llllllllllllllllllIlIlIllIIIIIII.setZ(llllllllllllllllllIlIlIlIllllIlI * llllllllllllllllllIlIlIlIlllllIl * Math.sin(Math.toRadians(llllllllllllllllllIlIlIlIllIllIl + 90.0D)) - llllllllllllllllllIlIlIlIllIlllI * llllllllllllllllllIlIlIlIlllllIl * Math.cos(Math.toRadians(llllllllllllllllllIlIlIlIllIllIl + 90.0D)));
      }

   }

   public static int LongJumpEffect() {
      return mc.thePlayer.isPotionActive(Potion.jump) ? mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1 : 0;
   }

   public static int getSpeedEffect() {
      return Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.moveSpeed) ? Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1 : 0;
   }

   public static double getBaseMoveSpeed() {
      String llllllllllllllllllIlIllIIIIIlIlI = 0.2873D;
      if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
         llllllllllllllllllIlIllIIIIIlIlI *= 1.0D + 0.2D * (double)(mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
      }

      return llllllllllllllllllIlIllIIIIIlIlI;
   }

   public static boolean isMoving() {
      return mc.thePlayer != null && (mc.thePlayer.movementInput.moveForward != 0.0F || mc.thePlayer.movementInput.moveStrafe != 0.0F);
   }

   public static void setSpeed(MoveEvent llllllllllllllllllIlIllIlIIIllll, double llllllllllllllllllIlIllIlIIIIlII, float llllllllllllllllllIlIllIlIIIllIl, double llllllllllllllllllIlIllIlIIIIIlI, double llllllllllllllllllIlIllIlIIIlIll) {
      double llllllllllllllllllIlIllIlIIIlIlI = llllllllllllllllllIlIllIlIIIlIll;
      double llllllllllllllllllIlIllIlIIIlIIl = llllllllllllllllllIlIllIlIIIIIlI;
      byte llllllllllllllllllIlIllIIllllllI = llllllllllllllllllIlIllIlIIIllIl;
      if (llllllllllllllllllIlIllIlIIIlIll != 0.0D) {
         if (llllllllllllllllllIlIllIlIIIIIlI > 0.0D) {
            llllllllllllllllllIlIllIIllllllI = llllllllllllllllllIlIllIlIIIllIl + (float)(llllllllllllllllllIlIllIlIIIlIll > 0.0D ? -45 : 45);
         } else if (llllllllllllllllllIlIllIlIIIIIlI < 0.0D) {
            llllllllllllllllllIlIllIIllllllI = llllllllllllllllllIlIllIlIIIllIl + (float)(llllllllllllllllllIlIllIlIIIlIll > 0.0D ? 45 : -45);
         }

         llllllllllllllllllIlIllIlIIIlIIl = 0.0D;
         if (llllllllllllllllllIlIllIlIIIlIll > 0.0D) {
            llllllllllllllllllIlIllIlIIIlIlI = 1.0D;
         } else if (llllllllllllllllllIlIllIlIIIlIll < 0.0D) {
            llllllllllllllllllIlIllIlIIIlIlI = -1.0D;
         }
      }

      if (llllllllllllllllllIlIllIlIIIlIIl > 0.0D) {
         llllllllllllllllllIlIllIlIIIlIIl = 1.0D;
      } else if (llllllllllllllllllIlIllIlIIIlIIl < 0.0D) {
         llllllllllllllllllIlIllIlIIIlIIl = -1.0D;
      }

      double llllllllllllllllllIlIllIlIIIIlll = Math.cos(Math.toRadians((double)(llllllllllllllllllIlIllIIllllllI + 90.0F)));
      double llllllllllllllllllIlIllIlIIIIllI = Math.sin(Math.toRadians((double)(llllllllllllllllllIlIllIIllllllI + 90.0F)));
      llllllllllllllllllIlIllIlIIIllll.setX(llllllllllllllllllIlIllIlIIIlIlI * llllllllllllllllllIlIllIlIIIIlII * llllllllllllllllllIlIllIlIIIIlll + llllllllllllllllllIlIllIlIIIlIIl * llllllllllllllllllIlIllIlIIIIlII * llllllllllllllllllIlIllIlIIIIllI);
      llllllllllllllllllIlIllIlIIIllll.setZ(llllllllllllllllllIlIllIlIIIlIlI * llllllllllllllllllIlIllIlIIIIlII * llllllllllllllllllIlIllIlIIIIllI - llllllllllllllllllIlIllIlIIIlIIl * llllllllllllllllllIlIllIlIIIIlII * llllllllllllllllllIlIllIlIIIIlll);
   }

   public static boolean isOnGround(double llllllllllllllllllIlIlIlIlIllllI) {
      return !mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0D, -llllllllllllllllllIlIlIlIlIllllI, 0.0D)).isEmpty();
   }

   public static boolean hasMotion() {
      return mc.thePlayer.motionX != 0.0D && mc.thePlayer.motionZ != 0.0D && mc.thePlayer.motionY != 0.0D;
   }

   public static boolean isBlockUnder() {
      short llllllllllllllllllIlIllIllIIIIlI = mc.thePlayer;
      WorldClient llllllllllllllllllIlIllIllIIIllI = mc.theWorld;
      AxisAlignedBB llllllllllllllllllIlIllIllIIIlIl = llllllllllllllllllIlIllIllIIIIlI.getEntityBoundingBox();
      double llllllllllllllllllIlIllIllIIIlII = llllllllllllllllllIlIllIllIIIIlI.posY + (double)llllllllllllllllllIlIllIllIIIIlI.getEyeHeight();

      for(int llllllllllllllllllIlIllIlIlllllI = 0; (double)llllllllllllllllllIlIllIlIlllllI < llllllllllllllllllIlIllIllIIIlII; llllllllllllllllllIlIllIlIlllllI += 2) {
         if (!llllllllllllllllllIlIllIllIIIllI.getCollidingBoundingBoxes(llllllllllllllllllIlIllIllIIIIlI, llllllllllllllllllIlIllIllIIIlIl.offset(0.0D, (double)(-llllllllllllllllllIlIllIlIlllllI), 0.0D)).isEmpty()) {
            return true;
         }
      }

      return false;
   }

   public static float getSpeed() {
      return (float)Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ);
   }

   public static void strafe() {
      strafe(getSpeed());
   }

   public static float getMaxFallDist() {
      PotionEffect llllllllllllllllllIlIlllIlIlllll = mc.thePlayer.getActivePotionEffect(Potion.jump);
      String llllllllllllllllllIlIlllIlIllIIl = llllllllllllllllllIlIlllIlIlllll != null ? llllllllllllllllllIlIlllIlIlllll.getAmplifier() + 1 : 0;
      return (float)(mc.thePlayer.getMaxFallHeight() + llllllllllllllllllIlIlllIlIllIIl);
   }

   public static float getDistanceToGround(Entity llllllllllllllllllIlIllIIIIllIlI) {
      if (mc.thePlayer.isCollidedVertically && mc.thePlayer.onGround) {
         return 0.0F;
      } else {
         for(float llllllllllllllllllIlIllIIIIllIII = (float)llllllllllllllllllIlIllIIIIllIlI.posY; llllllllllllllllllIlIllIIIIllIII > 0.0F; --llllllllllllllllllIlIllIIIIllIII) {
            int[] llllllllllllllllllIlIllIIIIllllI = new int[]{53, 67, 108, 109, 114, 128, 134, 135, 136, 156, 163, 164, 180};
            Exception llllllllllllllllllIlIllIIIIlIllI = new int[]{6, 27, 28, 30, 31, 32, 37, 38, 39, 40, 50, 51, 55, 59, 63, 65, 66, 68, 69, 70, 72, 75, 76, 77, 83, 92, 93, 94, 104, 105, 106, 115, 119, 131, 132, 143, 147, 148, 149, 150, 157, 171, 175, 176, 177};
            Exception llllllllllllllllllIlIllIIIIlIlIl = mc.theWorld.getBlockState(new BlockPos(llllllllllllllllllIlIllIIIIllIlI.posX, (double)(llllllllllllllllllIlIllIIIIllIII - 1.0F), llllllllllllllllllIlIllIIIIllIlI.posZ)).getBlock();
            if (!(llllllllllllllllllIlIllIIIIlIlIl instanceof BlockAir)) {
               if (Block.getIdFromBlock(llllllllllllllllllIlIllIIIIlIlIl) != 44 && Block.getIdFromBlock(llllllllllllllllllIlIllIIIIlIlIl) != 126) {
                  float llllllllllllllllllIlIllIIIIlIlII = llllllllllllllllllIlIllIIIIllllI;
                  int llllllllllllllllllIlIllIIIIlllll = llllllllllllllllllIlIllIIIIllllI.length;

                  int llllllllllllllllllIlIllIIIIlIIlI;
                  int llllllllllllllllllIlIllIIIlIIIlI;
                  for(llllllllllllllllllIlIllIIIIlIIlI = 0; llllllllllllllllllIlIllIIIIlIIlI < llllllllllllllllllIlIllIIIIlllll; ++llllllllllllllllllIlIllIIIIlIIlI) {
                     llllllllllllllllllIlIllIIIlIIIlI = llllllllllllllllllIlIllIIIIlIlII[llllllllllllllllllIlIllIIIIlIIlI];
                     if (Block.getIdFromBlock(llllllllllllllllllIlIllIIIIlIlIl) == llllllllllllllllllIlIllIIIlIIIlI) {
                        return (float)(llllllllllllllllllIlIllIIIIllIlI.posY - (double)llllllllllllllllllIlIllIIIIllIII - 1.0D) < 0.0F ? 0.0F : (float)(llllllllllllllllllIlIllIIIIllIlI.posY - (double)llllllllllllllllllIlIllIIIIllIII - 1.0D);
                     }
                  }

                  llllllllllllllllllIlIllIIIIlIlII = llllllllllllllllllIlIllIIIIlIllI;
                  llllllllllllllllllIlIllIIIIlllll = llllllllllllllllllIlIllIIIIlIllI.length;

                  for(llllllllllllllllllIlIllIIIIlIIlI = 0; llllllllllllllllllIlIllIIIIlIIlI < llllllllllllllllllIlIllIIIIlllll; ++llllllllllllllllllIlIllIIIIlIIlI) {
                     llllllllllllllllllIlIllIIIlIIIlI = llllllllllllllllllIlIllIIIIlIlII[llllllllllllllllllIlIllIIIIlIIlI];
                     if (Block.getIdFromBlock(llllllllllllllllllIlIllIIIIlIlIl) == llllllllllllllllllIlIllIIIlIIIlI) {
                        return (float)(llllllllllllllllllIlIllIIIIllIlI.posY - (double)llllllllllllllllllIlIllIIIIllIII) < 0.0F ? 0.0F : (float)(llllllllllllllllllIlIllIIIIllIlI.posY - (double)llllllllllllllllllIlIllIIIIllIII);
                     }
                  }

                  return (float)(llllllllllllllllllIlIllIIIIllIlI.posY - (double)llllllllllllllllllIlIllIIIIllIII + llllllllllllllllllIlIllIIIIlIlIl.getBlockBoundsMaxY() - 1.0D);
               }

               return (float)(llllllllllllllllllIlIllIIIIllIlI.posY - (double)llllllllllllllllllIlIllIIIIllIII - 0.5D) < 0.0F ? 0.0F : (float)(llllllllllllllllllIlIllIIIIllIlI.posY - (double)llllllllllllllllllIlIllIIIIllIII - 0.5D);
            }
         }

         return 0.0F;
      }
   }

   public static void setSpeed(MoveEvent llllllllllllllllllIlIllIllIlIIII, double llllllllllllllllllIlIllIllIIllIl) {
      setSpeed(llllllllllllllllllIlIllIllIlIIII, llllllllllllllllllIlIllIllIIllIl, mc.thePlayer.rotationYaw, (double)mc.thePlayer.movementInput.moveStrafe, (double)mc.thePlayer.movementInput.moveForward);
   }

   public static void SpeedSetMotion(double llllllllllllllllllIlIlIlllIIIlIl) {
      double llllllllllllllllllIlIlIlllIIIIll = (double)mc.thePlayer.movementInput.moveForward;
      boolean llllllllllllllllllIlIlIllIllllII = (double)mc.thePlayer.movementInput.moveStrafe;
      int llllllllllllllllllIlIlIllIlllIll = mc.thePlayer.rotationYaw;
      if (llllllllllllllllllIlIlIlllIIIIll == 0.0D && llllllllllllllllllIlIlIllIllllII == 0.0D) {
         mc.thePlayer.motionX = 0.0D;
         mc.thePlayer.motionZ = 0.0D;
      } else {
         if (llllllllllllllllllIlIlIlllIIIIll != 0.0D) {
            if (llllllllllllllllllIlIlIllIllllII > 0.0D) {
               llllllllllllllllllIlIlIllIlllIll += (float)(llllllllllllllllllIlIlIlllIIIIll > 0.0D ? -45 : 45);
            } else if (llllllllllllllllllIlIlIllIllllII < 0.0D) {
               llllllllllllllllllIlIlIllIlllIll += (float)(llllllllllllllllllIlIlIlllIIIIll > 0.0D ? 45 : -45);
            }

            llllllllllllllllllIlIlIllIllllII = 0.0D;
            if (llllllllllllllllllIlIlIlllIIIIll > 0.0D) {
               llllllllllllllllllIlIlIlllIIIIll = 1.0D;
            } else if (llllllllllllllllllIlIlIlllIIIIll < 0.0D) {
               llllllllllllllllllIlIlIlllIIIIll = -1.0D;
            }
         }

         mc.thePlayer.motionX = llllllllllllllllllIlIlIlllIIIIll * llllllllllllllllllIlIlIlllIIIlIl * Math.cos(Math.toRadians((double)(llllllllllllllllllIlIlIllIlllIll + 90.0F))) + llllllllllllllllllIlIlIllIllllII * llllllllllllllllllIlIlIlllIIIlIl * Math.sin(Math.toRadians((double)(llllllllllllllllllIlIlIllIlllIll + 90.0F)));
         mc.thePlayer.motionZ = llllllllllllllllllIlIlIlllIIIIll * llllllllllllllllllIlIlIlllIIIlIl * Math.sin(Math.toRadians((double)(llllllllllllllllllIlIlIllIlllIll + 90.0F))) - llllllllllllllllllIlIlIllIllllII * llllllllllllllllllIlIlIlllIIIlIl * Math.cos(Math.toRadians((double)(llllllllllllllllllIlIlIllIlllIll + 90.0F)));
      }

   }

   public static int getJumpEffect() {
      return Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.jump) ? Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1 : 0;
   }

   public static double getDirection() {
      float llllllllllllllllllIlIlIlIIlIIllI = mc.thePlayer.rotationYaw;
      if (mc.thePlayer.moveForward < 0.0F) {
         llllllllllllllllllIlIlIlIIlIIllI += 180.0F;
      }

      float llllllllllllllllllIlIlIlIIlIIIll = 1.0F;
      if (mc.thePlayer.moveForward < 0.0F) {
         llllllllllllllllllIlIlIlIIlIIIll = -0.5F;
      } else if (mc.thePlayer.moveForward > 0.0F) {
         llllllllllllllllllIlIlIlIIlIIIll = 0.5F;
      }

      if (mc.thePlayer.moveStrafing > 0.0F) {
         llllllllllllllllllIlIlIlIIlIIllI -= 90.0F * llllllllllllllllllIlIlIlIIlIIIll;
      }

      if (mc.thePlayer.moveStrafing < 0.0F) {
         llllllllllllllllllIlIlIlIIlIIllI += 90.0F * llllllllllllllllllIlIlIlIIlIIIll;
      }

      return Math.toRadians((double)llllllllllllllllllIlIlIlIIlIIllI);
   }

   public static void strafe(float llllllllllllllllllIlIlIlIIllllII) {
      if (isMoving()) {
         double llllllllllllllllllIlIlIlIIlllllI = getDirection();
         mc.thePlayer.motionX = -Math.sin(llllllllllllllllllIlIlIlIIlllllI) * (double)llllllllllllllllllIlIlIlIIllllII;
         mc.thePlayer.motionZ = Math.cos(llllllllllllllllllIlIlIlIIlllllI) * (double)llllllllllllllllllIlIlIlIIllllII;
      }
   }

   public static double LongJumpMoveSpeed() {
      long llllllllllllllllllIlIlllIIllllIl = 0.2873D;
      if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
         int llllllllllllllllllIlIlllIlIIIIIl = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
         llllllllllllllllllIlIlllIIllllIl *= 1.0D + 0.2D * (double)(llllllllllllllllllIlIlllIlIIIIIl + 1);
      }

      return llllllllllllllllllIlIlllIIllllIl;
   }
}
