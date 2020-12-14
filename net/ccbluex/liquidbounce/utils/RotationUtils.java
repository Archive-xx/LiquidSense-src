//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.utils;

import java.util.Random;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.TickEvent;
import net.ccbluex.liquidbounce.features.module.modules.combat.FastBow;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

@SideOnly(Side.CLIENT)
public final class RotationUtils extends MinecraftInstance implements Listenable {
   // $FF: synthetic field
   private static double y;
   // $FF: synthetic field
   public static Rotation serverRotation;
   // $FF: synthetic field
   private static double z;
   // $FF: synthetic field
   private static Random random = new Random();
   // $FF: synthetic field
   public static Rotation targetRotation;
   // $FF: synthetic field
   private static double x;
   // $FF: synthetic field
   private static int keepLength;
   // $FF: synthetic field
   public static boolean keepCurrentRotation = false;

   public static void reset() {
      keepLength = 0;
      targetRotation = null;
   }

   public static void setTargetRotation(Rotation llIIIlllllIIIlI, int llIIIlllllIIIll) {
      if (!Double.isNaN((double)llIIIlllllIIIlI.getYaw()) && !Double.isNaN((double)llIIIlllllIIIlI.getPitch()) && !(llIIIlllllIIIlI.getPitch() > 90.0F) && !(llIIIlllllIIIlI.getPitch() < -90.0F)) {
         llIIIlllllIIIlI.fixedSensitivity(mc.gameSettings.mouseSensitivity);
         targetRotation = llIIIlllllIIIlI;
         keepLength = llIIIlllllIIIll;
      }
   }

   public static Rotation toRotation(Vec3 llIIlIIIlllIIll, boolean llIIlIIIlllIIlI) {
      long llIIlIIIllIlIll = new Vec3(mc.thePlayer.posX, mc.thePlayer.getEntityBoundingBox().minY + (double)mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);
      if (llIIlIIIlllIIlI) {
         llIIlIIIllIlIll.addVector(mc.thePlayer.motionX, mc.thePlayer.motionY, mc.thePlayer.motionZ);
         boolean var10001 = false;
      }

      double llIIlIIIllIlIlI = llIIlIIIlllIIll.xCoord - llIIlIIIllIlIll.xCoord;
      double llIIlIIIllIllll = llIIlIIIlllIIll.yCoord - llIIlIIIllIlIll.yCoord;
      char llIIlIIIllIlIII = llIIlIIIlllIIll.zCoord - llIIlIIIllIlIll.zCoord;
      return new Rotation(MathHelper.wrapAngleTo180_float((float)Math.toDegrees(Math.atan2(llIIlIIIllIlIII, llIIlIIIllIlIlI)) - 90.0F), MathHelper.wrapAngleTo180_float((float)(-Math.toDegrees(Math.atan2(llIIlIIIllIllll, Math.sqrt(llIIlIIIllIlIlI * llIIlIIIllIlIlI + llIIlIIIllIlIII * llIIlIIIllIlIII))))));
   }

   static {
      x = random.nextDouble();
      y = random.nextDouble();
      z = random.nextDouble();
   }

   @EventTarget
   public void onPacket(PacketEvent llIIIlllllIlllI) {
      Packet<?> llIIIlllllIlIll = llIIIlllllIlllI.getPacket();
      if (llIIIlllllIlIll instanceof C03PacketPlayer) {
         C03PacketPlayer llIIIllllllIIII = (C03PacketPlayer)llIIIlllllIlIll;
         if (targetRotation != null && !keepCurrentRotation && (targetRotation.getYaw() != serverRotation.getYaw() || targetRotation.getPitch() != serverRotation.getPitch())) {
            llIIIllllllIIII.yaw = targetRotation.getYaw();
            llIIIllllllIIII.pitch = targetRotation.getPitch();
            llIIIllllllIIII.rotating = true;
         }

         if (llIIIllllllIIII.rotating) {
            serverRotation = new Rotation(llIIIllllllIIII.yaw, llIIIllllllIIII.pitch);
         }
      }

   }

   public static double getRotationDifference(Entity llIIlIIIIllIIII) {
      Rotation llIIlIIIIllIIIl = toRotation(getCenter(llIIlIIIIllIIII.getEntityBoundingBox()), true);
      return getRotationDifference(llIIlIIIIllIIIl, new Rotation(mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch));
   }

   public static Rotation OtherRotation(AxisAlignedBB llIIlIIIlIIIllI, Vec3 llIIlIIIlIIIlIl, boolean llIIlIIIlIIllll, boolean llIIlIIIlIIIIll, float llIIlIIIlIIllIl) {
      String llIIlIIIlIIIIIl = new Vec3(mc.thePlayer.posX, mc.thePlayer.getEntityBoundingBox().minY + (double)mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);
      long llIIlIIIlIIIIII = mc.thePlayer.getPositionEyes(1.0F);
      byte llIIlIIIIllllll = null;

      double llIIlIIIIlllllI;
      double llIIlIIIlIlIIll;
      double llIIlIIIlIlIlII;
      for(llIIlIIIIlllllI = 0.15D; llIIlIIIIlllllI < 0.85D; llIIlIIIIlllllI += 0.1D) {
         for(llIIlIIIlIlIIll = 0.15D; llIIlIIIlIlIIll < 1.0D; llIIlIIIlIlIIll += 0.1D) {
            for(llIIlIIIlIlIlII = 0.15D; llIIlIIIlIlIlII < 0.85D; llIIlIIIlIlIlII += 0.1D) {
               String llIIlIIIIlllIll = new Vec3(llIIlIIIlIIIllI.minX + (llIIlIIIlIIIllI.maxX - llIIlIIIlIIIllI.minX) * llIIlIIIIlllllI, llIIlIIIlIIIllI.minY + (llIIlIIIlIIIllI.maxY - llIIlIIIlIIIllI.minY) * llIIlIIIlIlIIll, llIIlIIIlIIIllI.minZ + (llIIlIIIlIIIllI.maxZ - llIIlIIIlIIIllI.minZ) * llIIlIIIlIlIlII);
               Rotation llIIlIIIlIlIllI = toRotation(llIIlIIIIlllIll, llIIlIIIlIIllll);
               double llIIlIIIlIlIlIl = llIIlIIIlIIIIII.distanceTo(llIIlIIIIlllIll);
               if (!(llIIlIIIlIlIlIl > (double)llIIlIIIlIIllIl) && (llIIlIIIlIIIIll || isVisible(llIIlIIIIlllIll))) {
                  char llIIlIIIIlllIII = new VecRotation(llIIlIIIIlllIll, llIIlIIIlIlIllI);
                  if (llIIlIIIIllllll == null) {
                     llIIlIIIIllllll = llIIlIIIIlllIII;
                  }
               }
            }
         }
      }

      if (llIIlIIIlIIllll) {
         llIIlIIIlIIIIIl.addVector(mc.thePlayer.motionX, mc.thePlayer.motionY, mc.thePlayer.motionZ);
         boolean var10001 = false;
      }

      llIIlIIIIlllllI = llIIlIIIlIIIlIl.xCoord - llIIlIIIlIIIIIl.xCoord;
      llIIlIIIlIlIIll = llIIlIIIlIIIlIl.yCoord - llIIlIIIlIIIIIl.yCoord;
      llIIlIIIlIlIlII = llIIlIIIlIIIlIl.zCoord - llIIlIIIlIIIIIl.zCoord;
      return new Rotation(MathHelper.wrapAngleTo180_float((float)Math.toDegrees(Math.atan2(llIIlIIIlIlIlII, llIIlIIIIlllllI)) - 90.0F), MathHelper.wrapAngleTo180_float((float)(-Math.toDegrees(Math.atan2(llIIlIIIlIlIIll, Math.sqrt(llIIlIIIIlllllI * llIIlIIIIlllllI + llIIlIIIlIlIlII * llIIlIIIlIlIlII))))));
   }

   public static boolean isVisible(Vec3 llIIIlllllllIIl) {
      Vec3 llIIIlllllllIII = new Vec3(mc.thePlayer.posX, mc.thePlayer.getEntityBoundingBox().minY + (double)mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);
      return mc.theWorld.rayTraceBlocks(llIIIlllllllIII, llIIIlllllllIIl) == null;
   }

   public static boolean isFaced(Entity llIIIllllllllIl, double llIIIlllllllllI) {
      return RaycastUtils.raycastEntity(llIIIlllllllllI, (llIIIllllIllIlI) -> {
         return llIIIllllIllIlI == llIIIllllllllIl;
      }) != null;
   }

   public static void faceBow(Entity llIIlIIllIIIIII, boolean llIIlIIllIIllIl, boolean llIIlIIllIIlIll, float llIIlIIllIIlIIl) {
      EntityPlayerSP llIIlIIllIIlIII = mc.thePlayer;
      double llIIlIIlIlllIll = llIIlIIllIIIIII.posX + (llIIlIIllIIlIll ? (llIIlIIllIIIIII.posX - llIIlIIllIIIIII.prevPosX) * (double)llIIlIIllIIlIIl : 0.0D) - (llIIlIIllIIlIII.posX + (llIIlIIllIIlIll ? llIIlIIllIIlIII.posX - llIIlIIllIIlIII.prevPosX : 0.0D));
      double llIIlIIllIIIllI = llIIlIIllIIIIII.getEntityBoundingBox().minY + (llIIlIIllIIlIll ? (llIIlIIllIIIIII.getEntityBoundingBox().minY - llIIlIIllIIIIII.prevPosY) * (double)llIIlIIllIIlIIl : 0.0D) + (double)llIIlIIllIIIIII.getEyeHeight() - 0.15D - (llIIlIIllIIlIII.getEntityBoundingBox().minY + (llIIlIIllIIlIll ? llIIlIIllIIlIII.posY - llIIlIIllIIlIII.prevPosY : 0.0D)) - (double)llIIlIIllIIlIII.getEyeHeight();
      double llIIlIIllIIIlIl = llIIlIIllIIIIII.posZ + (llIIlIIllIIlIll ? (llIIlIIllIIIIII.posZ - llIIlIIllIIIIII.prevPosZ) * (double)llIIlIIllIIlIIl : 0.0D) - (llIIlIIllIIlIII.posZ + (llIIlIIllIIlIll ? llIIlIIllIIlIII.posZ - llIIlIIllIIlIII.prevPosZ : 0.0D));
      double llIIlIIllIIIlII = Math.sqrt(llIIlIIlIlllIll * llIIlIIlIlllIll + llIIlIIllIIIlIl * llIIlIIllIIIlIl);
      char llIIlIIlIllIllI = LiquidBounce.moduleManager.getModule(FastBow.class).getState() ? 1.0F : (float)llIIlIIllIIlIII.getItemInUseDuration() / 20.0F;
      llIIlIIlIllIllI = (llIIlIIlIllIllI * llIIlIIlIllIllI + llIIlIIlIllIllI * 2.0F) / 3.0F;
      if (llIIlIIlIllIllI > 1.0F) {
         llIIlIIlIllIllI = 1.0F;
      }

      float llIIlIIlIllIlII = new Rotation((float)(Math.atan2(llIIlIIllIIIlIl, llIIlIIlIlllIll) * 180.0D / 3.141592653589793D) - 90.0F, (float)(-Math.toDegrees(Math.atan(((double)(llIIlIIlIllIllI * llIIlIIlIllIllI) - Math.sqrt((double)(llIIlIIlIllIllI * llIIlIIlIllIllI * llIIlIIlIllIllI * llIIlIIlIllIllI) - 0.006000000052154064D * (0.006000000052154064D * llIIlIIllIIIlII * llIIlIIllIIIlII + 2.0D * llIIlIIllIIIllI * (double)(llIIlIIlIllIllI * llIIlIIlIllIllI)))) / (0.006000000052154064D * llIIlIIllIIIlII)))));
      if (llIIlIIllIIllIl) {
         setTargetRotation(llIIlIIlIllIlII);
      } else {
         limitAngleChange(new Rotation(llIIlIIllIIlIII.rotationYaw, llIIlIIllIIlIII.rotationPitch), llIIlIIlIllIlII, (float)(10 + (new Random()).nextInt(6))).toPlayer(mc.thePlayer);
      }

   }

   public static Vec3 getCenter(AxisAlignedBB llIIlIIIIllIlIl) {
      return new Vec3(llIIlIIIIllIlIl.minX + (llIIlIIIIllIlIl.maxX - llIIlIIIIllIlIl.minX) * 0.5D, llIIlIIIIllIlIl.minY + (llIIlIIIIllIlIl.maxY - llIIlIIIIllIlIl.minY) * 0.5D, llIIlIIIIllIlIl.minZ + (llIIlIIIIllIlIl.maxZ - llIIlIIIIllIlIl.minZ) * 0.5D);
   }

   public static Vec3 getVectorForRotation(Rotation llIIlIIIIIIlIll) {
      int llIIlIIIIIIIlIl = MathHelper.cos(-llIIlIIIIIIlIll.getYaw() * 0.017453292F - 3.1415927F);
      byte llIIlIIIIIIIlII = MathHelper.sin(-llIIlIIIIIIlIll.getYaw() * 0.017453292F - 3.1415927F);
      float llIIlIIIIIIlIII = -MathHelper.cos(-llIIlIIIIIIlIll.getPitch() * 0.017453292F);
      float llIIlIIIIIIIlll = MathHelper.sin(-llIIlIIIIIIlIll.getPitch() * 0.017453292F);
      return new Vec3((double)(llIIlIIIIIIIlII * llIIlIIIIIIlIII), (double)llIIlIIIIIIIlll, (double)(llIIlIIIIIIIlIl * llIIlIIIIIIlIII));
   }

   public static void setTargetRotation(Rotation llIIIlllllIlIII) {
      setTargetRotation(llIIIlllllIlIII, 0);
   }

   private static float getAngleDifference(float llIIlIIIIIlIIlI, float llIIlIIIIIlIIll) {
      return ((llIIlIIIIIlIIlI - llIIlIIIIIlIIll) % 360.0F + 540.0F) % 360.0F - 180.0F;
   }

   public static double getRotationDifference(Rotation llIIlIIIIlIIlll, Rotation llIIlIIIIlIIllI) {
      return Math.hypot((double)getAngleDifference(llIIlIIIIlIIlll.getYaw(), llIIlIIIIlIIllI.getYaw()), (double)(llIIlIIIIlIIlll.getPitch() - llIIlIIIIlIIllI.getPitch()));
   }

   @EventTarget
   public void onTick(TickEvent llIIIllllllIlII) {
      if (targetRotation != null) {
         --keepLength;
         if (keepLength <= 0) {
            reset();
         }
      }

      if (random.nextGaussian() > 0.8D) {
         x = Math.random();
      }

      if (random.nextGaussian() > 0.8D) {
         y = Math.random();
      }

      if (random.nextGaussian() > 0.8D) {
         z = Math.random();
      }

   }

   public static float[] getRotations(EntityLivingBase llIIlIllIIIlIII) {
      double llIIlIllIIIllII = llIIlIllIIIlIII.posX;
      double llIIlIllIIIlIll = llIIlIllIIIlIII.posZ;
      long llIIlIllIIIIIlI = llIIlIllIIIlIII.posY + (double)(llIIlIllIIIlIII.getEyeHeight() / 2.0F);
      return getRotationFromPosition(llIIlIllIIIllII, llIIlIllIIIlIll, llIIlIllIIIIIlI);
   }

   public static float[] getRotationFromPosition(double llIIlIlIllIIIll, double llIIlIlIllIIIIl, double llIIlIlIlllIIII) {
      int llIIlIlIlIlllIl = llIIlIlIllIIIll - mc.thePlayer.posX;
      double llIIlIlIllIllIl = llIIlIlIllIIIIl - mc.thePlayer.posZ;
      double llIIlIlIlIllIIl = llIIlIlIlllIIII - mc.thePlayer.posY - 1.2D;
      double llIIlIlIllIlIlI = (double)MathHelper.sqrt_double(llIIlIlIlIlllIl * llIIlIlIlIlllIl + llIIlIlIllIllIl * llIIlIlIllIllIl);
      long llIIlIlIlIlIlIl = (float)(Math.atan2(llIIlIlIllIllIl, llIIlIlIlIlllIl) * 180.0D / 3.141592653589793D) - 90.0F;
      long llIIlIlIlIlIIll = (float)(-(Math.atan2(llIIlIlIlIllIIl, llIIlIlIllIlIlI) * 180.0D / 3.141592653589793D));
      return new float[]{llIIlIlIlIlIlIl, llIIlIlIlIlIIll};
   }

   public static VecRotation searchCenter(AxisAlignedBB llIIlIIlIIlIlII, boolean llIIlIIlIIIlIIl, boolean llIIlIIlIIlIIlI, boolean llIIlIIlIIIIlll, boolean llIIlIIlIIlIIII, float llIIlIIlIIIllll) {
      Vec3 llIIlIIlIIIlllI;
      if (llIIlIIlIIIlIIl) {
         llIIlIIlIIIlllI = new Vec3(llIIlIIlIIlIlII.minX + (llIIlIIlIIlIlII.maxX - llIIlIIlIIlIlII.minX) * (x * 0.3D + 1.0D), llIIlIIlIIlIlII.minY + (llIIlIIlIIlIlII.maxY - llIIlIIlIIlIlII.minY) * (y * 0.3D + 1.0D), llIIlIIlIIlIlII.minZ + (llIIlIIlIIlIlII.maxZ - llIIlIIlIIlIlII.minZ) * (z * 0.3D + 1.0D));
         return new VecRotation(llIIlIIlIIIlllI, toRotation(llIIlIIlIIIlllI, llIIlIIlIIIIlll));
      } else {
         llIIlIIlIIIlllI = new Vec3(llIIlIIlIIlIlII.minX + (llIIlIIlIIlIlII.maxX - llIIlIIlIIlIlII.minX) * x * 0.8D, llIIlIIlIIlIlII.minY + (llIIlIIlIIlIlII.maxY - llIIlIIlIIlIlII.minY) * y * 0.8D, llIIlIIlIIlIlII.minZ + (llIIlIIlIIlIlII.maxZ - llIIlIIlIIlIlII.minZ) * z * 0.8D);
         double llIIlIIlIIIIIll = toRotation(llIIlIIlIIIlllI, llIIlIIlIIIIlll);
         char llIIlIIlIIIIIlI = mc.thePlayer.getPositionEyes(1.0F);
         VecRotation llIIlIIlIIIlIll = null;

         for(double llIIlIIlIIIIIII = 0.15D; llIIlIIlIIIIIII < 0.85D; llIIlIIlIIIIIII += 0.1D) {
            for(double llIIlIIIlllllll = 0.15D; llIIlIIIlllllll < 1.0D; llIIlIIIlllllll += 0.1D) {
               for(double llIIlIIIllllllI = 0.15D; llIIlIIIllllllI < 0.85D; llIIlIIIllllllI += 0.1D) {
                  Vec3 llIIlIIlIIllIlI = new Vec3(llIIlIIlIIlIlII.minX + (llIIlIIlIIlIlII.maxX - llIIlIIlIIlIlII.minX) * llIIlIIlIIIIIII, llIIlIIlIIlIlII.minY + (llIIlIIlIIlIlII.maxY - llIIlIIlIIlIlII.minY) * llIIlIIIlllllll, llIIlIIlIIlIlII.minZ + (llIIlIIlIIlIlII.maxZ - llIIlIIlIIlIlII.minZ) * llIIlIIIllllllI);
                  Rotation llIIlIIlIIllIIl = toRotation(llIIlIIlIIllIlI, llIIlIIlIIIIlll);
                  double llIIlIIlIIllIII = llIIlIIlIIIIIlI.distanceTo(llIIlIIlIIllIlI);
                  if (!(llIIlIIlIIllIII > (double)llIIlIIlIIIllll) && (llIIlIIlIIlIIII || isVisible(llIIlIIlIIllIlI))) {
                     float llIIlIIIllllIlI = new VecRotation(llIIlIIlIIllIlI, llIIlIIlIIllIIl);
                     if (llIIlIIlIIIlIll != null) {
                        if (llIIlIIlIIlIIlI) {
                           if (!(getRotationDifference(llIIlIIIllllIlI.getRotation(), llIIlIIlIIIIIll) < getRotationDifference(llIIlIIlIIIlIll.getRotation(), llIIlIIlIIIIIll))) {
                              continue;
                           }
                        } else if (!(getRotationDifference(llIIlIIIllllIlI.getRotation()) < getRotationDifference(llIIlIIlIIIlIll.getRotation()))) {
                           continue;
                        }
                     }

                     llIIlIIlIIIlIll = llIIlIIIllllIlI;
                  }
               }
            }
         }

         return llIIlIIlIIIlIll;
      }
   }

   public static double getRotationDifference(Rotation llIIlIIIIlIllIl) {
      return serverRotation == null ? 0.0D : getRotationDifference(llIIlIIIIlIllIl, serverRotation);
   }

   public static VecRotation faceBlock(BlockPos llIIlIlIIIlIIIl) {
      if (llIIlIlIIIlIIIl == null) {
         return null;
      } else {
         boolean llIIlIlIIIlIIII = null;

         for(double llIIlIlIIIllIII = 0.1D; llIIlIlIIIllIII < 0.9D; llIIlIlIIIllIII += 0.1D) {
            for(double llIIlIlIIIllIlI = 0.1D; llIIlIlIIIllIlI < 0.9D; llIIlIlIIIllIlI += 0.1D) {
               for(double llIIlIlIIIIllIl = 0.1D; llIIlIlIIIIllIl < 0.9D; llIIlIlIIIIllIl += 0.1D) {
                  short llIIlIlIIIIllII = new Vec3(mc.thePlayer.posX, mc.thePlayer.getEntityBoundingBox().minY + (double)mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);
                  long llIIlIlIIIIlIll = (new Vec3(llIIlIlIIIlIIIl)).addVector(llIIlIlIIIllIII, llIIlIlIIIllIlI, llIIlIlIIIIllIl);
                  double llIIlIlIIlIlIIl = llIIlIlIIIIllII.distanceTo(llIIlIlIIIIlIll);
                  float llIIlIlIIIIlIIl = llIIlIlIIIIlIll.xCoord - llIIlIlIIIIllII.xCoord;
                  double llIIlIlIIlIIlll = llIIlIlIIIIlIll.yCoord - llIIlIlIIIIllII.yCoord;
                  int llIIlIlIIIIIlll = llIIlIlIIIIlIll.zCoord - llIIlIlIIIIllII.zCoord;
                  float llIIlIlIIIIIllI = (double)MathHelper.sqrt_double(llIIlIlIIIIlIIl * llIIlIlIIIIlIIl + llIIlIlIIIIIlll * llIIlIlIIIIIlll);
                  String llIIlIlIIIIIlIl = new Rotation(MathHelper.wrapAngleTo180_float((float)Math.toDegrees(Math.atan2(llIIlIlIIIIIlll, llIIlIlIIIIlIIl)) - 90.0F), MathHelper.wrapAngleTo180_float((float)(-Math.toDegrees(Math.atan2(llIIlIlIIlIIlll, llIIlIlIIIIIllI)))));
                  Vec3 llIIlIlIIlIIIlI = getVectorForRotation(llIIlIlIIIIIlIl);
                  Exception llIIlIlIIIIIIll = llIIlIlIIIIllII.addVector(llIIlIlIIlIIIlI.xCoord * llIIlIlIIlIlIIl, llIIlIlIIlIIIlI.yCoord * llIIlIlIIlIlIIl, llIIlIlIIlIIIlI.zCoord * llIIlIlIIlIlIIl);
                  MovingObjectPosition llIIlIlIIIllllI = mc.theWorld.rayTraceBlocks(llIIlIlIIIIllII, llIIlIlIIIIIIll, false, false, true);
                  if (llIIlIlIIIllllI.typeOfHit == MovingObjectType.BLOCK) {
                     long llIIlIlIIIIIIIl = new VecRotation(llIIlIlIIIIlIll, llIIlIlIIIIIlIl);
                     if (llIIlIlIIIlIIII == null || getRotationDifference(llIIlIlIIIIIIIl.getRotation()) < getRotationDifference(llIIlIlIIIlIIII.getRotation())) {
                        llIIlIlIIIlIIII = llIIlIlIIIIIIIl;
                     }
                  }
               }
            }
         }

         return llIIlIlIIIlIIII;
      }
   }

   public boolean handleEvents() {
      return true;
   }

   @NotNull
   public static Rotation limitAngleChange(Rotation llIIlIIIIIllIll, Rotation llIIlIIIIIllIlI, float llIIlIIIIIllIIl) {
      boolean llIIlIIIIIllIII = getAngleDifference(llIIlIIIIIllIlI.getYaw(), llIIlIIIIIllIll.getYaw());
      short llIIlIIIIIlIlll = getAngleDifference(llIIlIIIIIllIlI.getPitch(), llIIlIIIIIllIll.getPitch());
      return new Rotation(llIIlIIIIIllIll.getYaw() + (llIIlIIIIIllIII > llIIlIIIIIllIIl ? llIIlIIIIIllIIl : Math.max(llIIlIIIIIllIII, -llIIlIIIIIllIIl)), llIIlIIIIIllIll.getPitch() + (llIIlIIIIIlIlll > llIIlIIIIIllIIl ? llIIlIIIIIllIIl : Math.max(llIIlIIIIIlIlll, -llIIlIIIIIllIIl)));
   }
}
