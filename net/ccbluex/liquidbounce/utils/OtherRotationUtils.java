//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.utils;

import java.util.Random;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.TickEvent;
import net.ccbluex.liquidbounce.features.module.modules.combat.FastBow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

@SideOnly(Side.CLIENT)
public final class OtherRotationUtils extends MinecraftInstance implements Listenable {
   // $FF: synthetic field
   private static double z;
   // $FF: synthetic field
   public static Rotation targetRotation;
   // $FF: synthetic field
   private static double y;
   // $FF: synthetic field
   private static double x;
   // $FF: synthetic field
   private static Random random = new Random();
   // $FF: synthetic field
   public static boolean keepCurrentRotation = false;
   // $FF: synthetic field
   private static int keepLength;
   // $FF: synthetic field
   public static Rotation serverRotation = new Rotation(0.0F, 0.0F);

   public static float[] getVecRotation(Vec3 llIIIIlIIllll, Vec3 llIIIIlIIlllI) {
      long llIIIIlIIllIl = llIIIIlIIlllI.subtract(llIIIIlIIllll);
      float llIIIIlIIllII = llIIIIlIIllIl.lengthVector();
      double llIIIIlIlIIlI = Math.sqrt(llIIIIlIIllIl.xCoord * llIIIIlIIllIl.xCoord + llIIIIlIIllIl.zCoord * llIIIIlIIllIl.zCoord);
      Exception llIIIIlIIlIlI = (float)Math.toDegrees(Math.atan2(llIIIIlIIllIl.zCoord, llIIIIlIIllIl.xCoord)) - 90.0F;
      char llIIIIlIIlIIl = (float)(-(Math.atan2(llIIIIlIIllIl.yCoord, llIIIIlIlIIlI) * 180.0D / 3.141592653589793D));
      return new float[]{llIIIIlIIlIlI, llIIIIlIIlIIl};
   }

   public static boolean isVisible(Vec3 lIllIlIIllIIl) {
      Vec3 lIllIlIIllIlI = new Vec3(mc.thePlayer.posX, mc.thePlayer.getEntityBoundingBox().minY + (double)mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);
      return mc.theWorld.rayTraceBlocks(lIllIlIIllIlI, lIllIlIIllIIl) == null;
   }

   @EventTarget
   public void onPacket(PacketEvent lIllIlIIlIIII) {
      Packet<?> lIllIlIIIllll = lIllIlIIlIIII.getPacket();
      if (lIllIlIIIllll instanceof C03PacketPlayer) {
         boolean lIllIlIIIllII = (C03PacketPlayer)lIllIlIIIllll;
         if (targetRotation != null && !keepCurrentRotation && (targetRotation.getYaw() != serverRotation.getYaw() || targetRotation.getPitch() != serverRotation.getPitch())) {
            lIllIlIIIllII.yaw = targetRotation.getYaw();
            lIllIlIIIllII.pitch = targetRotation.getPitch();
            lIllIlIIIllII.rotating = true;
         }

         if (lIllIlIIIllII.rotating) {
            serverRotation = new Rotation(lIllIlIIIllII.yaw, lIllIlIIIllII.pitch);
         }
      }

   }

   public static float[] getRotations(EntityLivingBase llIIIIllIIlll) {
      String llIIIIllIIIIl = llIIIIllIIlll.posX;
      double llIIIIllIIlIl = llIIIIllIIlll.posZ;
      double llIIIIllIIlII = llIIIIllIIlll.posY;
      float llIIIIlIllllI = new Vec3(llIIIIllIIlll.posX, llIIIIllIIlII, llIIIIllIIlll.posZ);
      return getVecRotation(getEyesPos(), llIIIIlIllllI);
   }

   public static void setTargetRotation(Rotation lIllIlIIIlIlI) {
      setTargetRotation(lIllIlIIIlIlI, 0);
   }

   private static float getAngleDifference(float lIllIllIIIIlI, float lIllIllIIIIIl) {
      return ((lIllIllIIIIlI - lIllIllIIIIIl) % 360.0F + 540.0F) % 360.0F - 180.0F;
   }

   public static VecRotation faceBlock(BlockPos lIllllllIIIIl) {
      if (lIllllllIIIIl == null) {
         return null;
      } else {
         byte lIllllllIIIII = null;

         for(double lIllllllIIlII = 0.1D; lIllllllIIlII < 0.9D; lIllllllIIlII += 0.1D) {
            for(double lIllllllIIlIl = 0.1D; lIllllllIIlIl < 0.9D; lIllllllIIlIl += 0.1D) {
               for(double lIllllllIIllI = 0.1D; lIllllllIIllI < 0.9D; lIllllllIIllI += 0.1D) {
                  double lIlllllIllIIl = new Vec3(mc.thePlayer.posX, mc.thePlayer.getEntityBoundingBox().minY + (double)mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);
                  char lIlllllIlIlll = (new Vec3(lIllllllIIIIl)).addVector(lIllllllIIlII, lIllllllIIlIl, lIllllllIIllI);
                  double lIlllllllIIIl = lIlllllIllIIl.distanceTo(lIlllllIlIlll);
                  double lIllllllIllll = lIlllllIlIlll.xCoord - lIlllllIllIIl.xCoord;
                  double lIllllllIllIl = lIlllllIlIlll.yCoord - lIlllllIllIIl.yCoord;
                  double lIllllllIllII = lIlllllIlIlll.zCoord - lIlllllIllIIl.zCoord;
                  boolean lIlllllIlIIlI = (double)MathHelper.sqrt_double(lIllllllIllll * lIllllllIllll + lIllllllIllII * lIllllllIllII);
                  Exception lIlllllIIlllI = new Rotation(MathHelper.wrapAngleTo180_float((float)Math.toDegrees(Math.atan2(lIllllllIllII, lIllllllIllll)) - 90.0F), MathHelper.wrapAngleTo180_float((float)(-Math.toDegrees(Math.atan2(lIllllllIllIl, lIlllllIlIIlI)))));
                  float lIlllllIIllII = getVectorForRotation(lIlllllIIlllI);
                  byte lIlllllIIlIlI = lIlllllIllIIl.addVector(lIlllllIIllII.xCoord * lIlllllllIIIl, lIlllllIIllII.yCoord * lIlllllllIIIl, lIlllllIIllII.zCoord * lIlllllllIIIl);
                  int lIlllllIIlIII = mc.theWorld.rayTraceBlocks(lIlllllIllIIl, lIlllllIIlIlI, false, false, true);
                  if (lIlllllIIlIII.typeOfHit == MovingObjectType.BLOCK) {
                     VecRotation lIlllllllIlIl = new VecRotation(lIlllllIlIlll, lIlllllIIlllI);
                     if (lIllllllIIIII == null || getRotationDifference(lIlllllllIlIl.getRotation()) < getRotationDifference(lIllllllIIIII.getRotation())) {
                        lIllllllIIIII = lIlllllllIlIl;
                     }
                  }
               }
            }
         }

         return lIllllllIIIII;
      }
   }

   public static void faceBow(Entity lIllllIlIIIII, boolean lIllllIIlIIll, boolean lIllllIIlIIlI, float lIllllIIlllIl) {
      EntityPlayerSP lIllllIIlllII = mc.thePlayer;
      boolean lIllllIIIllIl = lIllllIlIIIII.posX + (lIllllIIlIIlI ? (lIllllIlIIIII.posX - lIllllIlIIIII.prevPosX) * (double)lIllllIIlllIl : 0.0D) - (lIllllIIlllII.posX + (lIllllIIlIIlI ? lIllllIIlllII.posX - lIllllIIlllII.prevPosX : 0.0D));
      Exception lIllllIIIllII = lIllllIlIIIII.getEntityBoundingBox().minY + (lIllllIIlIIlI ? (lIllllIlIIIII.getEntityBoundingBox().minY - lIllllIlIIIII.prevPosY) * (double)lIllllIIlllIl : 0.0D) + (double)lIllllIlIIIII.getEyeHeight() - 0.15D - (lIllllIIlllII.getEntityBoundingBox().minY + (lIllllIIlIIlI ? lIllllIIlllII.posY - lIllllIIlllII.prevPosY : 0.0D)) - (double)lIllllIIlllII.getEyeHeight();
      double lIllllIIllIIl = lIllllIlIIIII.posZ + (lIllllIIlIIlI ? (lIllllIlIIIII.posZ - lIllllIlIIIII.prevPosZ) * (double)lIllllIIlllIl : 0.0D) - (lIllllIIlllII.posZ + (lIllllIIlIIlI ? lIllllIIlllII.posZ - lIllllIIlllII.prevPosZ : 0.0D));
      double lIllllIIllIII = Math.sqrt(lIllllIIIllIl * lIllllIIIllIl + lIllllIIllIIl * lIllllIIllIIl);
      double lIllllIIIIlll = LiquidBounce.moduleManager.getModule(FastBow.class).getState() ? 1.0F : (float)lIllllIIlllII.getItemInUseDuration() / 20.0F;
      lIllllIIIIlll = (lIllllIIIIlll * lIllllIIIIlll + lIllllIIIIlll * 2.0F) / 3.0F;
      if (lIllllIIIIlll > 1.0F) {
         lIllllIIIIlll = 1.0F;
      }

      Rotation lIllllIIlIlIl = new Rotation((float)(Math.atan2(lIllllIIllIIl, lIllllIIIllIl) * 180.0D / 3.141592653589793D) - 90.0F, (float)(-Math.toDegrees(Math.atan(((double)(lIllllIIIIlll * lIllllIIIIlll) - Math.sqrt((double)(lIllllIIIIlll * lIllllIIIIlll * lIllllIIIIlll * lIllllIIIIlll) - 0.006000000052154064D * (0.006000000052154064D * lIllllIIllIII * lIllllIIllIII + 2.0D * lIllllIIIllII * (double)(lIllllIIIIlll * lIllllIIIIlll)))) / (0.006000000052154064D * lIllllIIllIII)))));
      if (lIllllIIlIIll) {
         setTargetRotation(lIllllIIlIlIl);
      } else {
         limitAngleChange(new Rotation(lIllllIIlllII.rotationYaw, lIllllIIlllII.rotationPitch), lIllllIIlIlIl, (float)(10 + (new Random()).nextInt(6))).toPlayer(mc.thePlayer);
      }

   }

   public static VecRotation Center(AxisAlignedBB llIIIIIlIlIlI) {
      float llIIIIIlIlIII = null;

      for(double llIIIIIlIIlll = 0.15D; llIIIIIlIIlll < 0.85D; llIIIIIlIIlll += 0.1D) {
         for(double llIIIIIllIIII = 0.15D; llIIIIIllIIII < 1.0D; llIIIIIllIIII += 0.1D) {
            for(double llIIIIIlIIlII = 0.15D; llIIIIIlIIlII < 0.85D; llIIIIIlIIlII += 0.1D) {
               Vec3 llIIIIIllIlII = new Vec3(llIIIIIlIlIlI.minX + (llIIIIIlIlIlI.maxX - llIIIIIlIlIlI.minX) * llIIIIIlIIlll, llIIIIIlIlIlI.minY + (llIIIIIlIlIlI.maxY - llIIIIIlIlIlI.minY) * llIIIIIllIIII * 0.6D, llIIIIIlIlIlI.minZ + (llIIIIIlIlIlI.maxZ - llIIIIIlIlIlI.minZ) * llIIIIIlIIlII);
               char llIIIIIlIIIlI = toRotation(llIIIIIllIlII, false);
               boolean llIIIIIlIIIIl = new VecRotation(llIIIIIllIlII, llIIIIIlIIIlI);
               if (llIIIIIlIlIII == null || getRotationDifference(llIIIIIlIIIIl.getRotation()) < getRotationDifference(llIIIIIlIlIII.getRotation())) {
                  llIIIIIlIlIII = llIIIIIlIIIIl;
               }
            }
         }
      }

      return llIIIIIlIlIII;
   }

   public static Rotation toRotation(Vec3 lIlllIlllIIlI, boolean lIlllIlllIIIl) {
      short lIlllIlllIIII = new Vec3(mc.thePlayer.posX, mc.thePlayer.getEntityBoundingBox().minY + (double)mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);
      if (lIlllIlllIIIl) {
         lIlllIlllIIII.addVector(mc.thePlayer.motionX, mc.thePlayer.motionY, mc.thePlayer.motionZ);
         boolean var10001 = false;
      }

      double lIlllIlllIlIl = lIlllIlllIIlI.xCoord - lIlllIlllIIII.xCoord;
      boolean lIlllIllIllIl = lIlllIlllIIlI.yCoord - lIlllIlllIIII.yCoord;
      byte lIlllIllIlIll = lIlllIlllIIlI.zCoord - lIlllIlllIIII.zCoord;
      return new Rotation(MathHelper.wrapAngleTo180_float((float)Math.toDegrees(Math.atan2(lIlllIllIlIll, lIlllIlllIlIl)) - 90.0F), MathHelper.wrapAngleTo180_float((float)(-Math.toDegrees(Math.atan2(lIlllIllIllIl, Math.sqrt(lIlllIlllIlIl * lIlllIlllIlIl + lIlllIllIlIll * lIlllIllIlIll))))));
   }

   public static double getRotationDifference(Rotation lIllIllIlIlIl, Rotation lIllIllIlIllI) {
      return Math.hypot((double)getAngleDifference(lIllIllIlIlIl.getYaw(), lIllIllIlIllI.getYaw()), (double)(lIllIllIlIlIl.getPitch() - lIllIllIlIllI.getPitch()));
   }

   public static void setTRotation(Rotation lIllIlIIIIIII, int lIllIIlllllIl) {
      if (!Double.isNaN((double)lIllIlIIIIIII.getYaw()) && !Double.isNaN((double)lIllIlIIIIIII.getPitch()) && !(lIllIlIIIIIII.getPitch() > 90.0F) && !(lIllIlIIIIIII.getPitch() < -90.0F)) {
         lIllIlIIIIIII.fixedSensitivity(mc.gameSettings.mouseSensitivity);
         targetRotation = lIllIlIIIIIII;
         keepLength = lIllIIlllllIl;
      }
   }

   public static double getRotationDifference(Rotation lIllIllIllIll) {
      return serverRotation == null ? 0.0D : getRotationDifference(lIllIllIllIll, serverRotation);
   }

   public static void reset() {
      keepLength = 0;
      targetRotation = null;
   }

   public boolean handleEvents() {
      return true;
   }

   @EventTarget
   public void onTick(TickEvent lIllIlIIlIllI) {
      if (targetRotation != null) {
         --keepLength;
         if (keepLength <= 0) {
            reset();
         }
      }

      if (random.nextGaussian() > 0.6D) {
         x = Math.random();
      }

      if (random.nextGaussian() > 0.6D) {
         y = Math.random();
      }

      if (random.nextGaussian() > 0.6D) {
         z = Math.random();
      }

   }

   public static Vec3 getVectorForRotation(Rotation lIllIlIllIlIl) {
      float lIllIlIllIlII = MathHelper.cos(-lIllIlIllIlIl.getYaw() * 0.017453292F - 3.1415927F);
      short lIllIlIlIlIlI = MathHelper.sin(-lIllIlIllIlIl.getYaw() * 0.017453292F - 3.1415927F);
      float lIllIlIllIIlI = -MathHelper.cos(-lIllIlIllIlIl.getPitch() * 0.017453292F);
      float lIllIlIllIIIl = MathHelper.sin(-lIllIlIllIlIl.getPitch() * 0.017453292F);
      int lIllIlIlIIlll = null;
      lIllIlIlIIlll = new Vec3((double)(lIllIlIlIlIlI * lIllIlIllIIlI), (double)lIllIlIllIIIl, (double)(lIllIlIllIlII * lIllIlIllIIlI));
      byte lIllIlIlIIllI = null;
      String lIllIlIlIIlIl = toRotation(lIllIlIlIIlll, false);
      Exception lIllIlIlIIlII = new VecRotation(lIllIlIlIIlll, lIllIlIllIlIl);
      return new Vec3((double)(lIllIlIlIlIlI * lIllIlIllIIlI), (double)lIllIlIllIIIl, (double)(lIllIlIllIlII * lIllIlIllIIlI));
   }

   public static VecRotation searchCenter(AxisAlignedBB lIllIlllIllll) {
      boolean lIllIllllIlII = false;
      boolean lIllIllllIIll = false;
      VecRotation lIllIllllIIlI = null;
      if (lIllIlllIllll.maxX - lIllIlllIllll.minX < lIllIlllIllll.maxZ - lIllIlllIllll.minZ) {
         lIllIllllIIll = true;
      }

      if (lIllIlllIllll.maxX - lIllIlllIllll.minX > lIllIlllIllll.maxZ - lIllIlllIllll.minZ) {
         lIllIllllIlII = true;
      }

      if (lIllIlllIllll.maxX - lIllIlllIllll.minX == lIllIlllIllll.maxZ - lIllIlllIllll.minZ) {
         lIllIllllIIll = false;
         lIllIllllIlII = false;
      }

      long lIllIlllIlIlI = lIllIlllIllll.minX + (lIllIlllIllll.maxX - lIllIlllIllll.minX) * (lIllIllllIlII ? 0.25D : 0.5D);
      String lIllIlllIlIIl = lIllIlllIllll.minZ + (lIllIlllIllll.maxZ - lIllIlllIllll.minZ) * (lIllIllllIIll ? 0.25D : 0.5D);

      for(double lIllIllllIllI = 0.1D; lIllIllllIllI < 0.9D; lIllIllllIllI += 0.1D) {
         for(double lIllIlllIlllI = 0.2D; lIllIlllIlllI < 0.8D; lIllIlllIlllI += 0.2D) {
            for(double lIllIlllIIlll = 0.1D; lIllIlllIIlll < 0.9D; lIllIlllIIlll += 0.1D) {
               boolean lIllIlllIIllI = lIllIlllIllll.minY + (lIllIlllIllll.maxY - lIllIlllIllll.minY) * lIllIlllIlllI;
               double lIllIlllIIlIl = null;
               lIllIlllIIlIl = new Vec3(lIllIlllIlIlI, lIllIlllIIllI, lIllIlllIlIIl);
               Rotation lIllIlllllIlI = toRotation(lIllIlllIIlIl, false);
               VecRotation lIllIlllllIIl = new VecRotation(lIllIlllIIlIl, lIllIlllllIlI);
               if (lIllIllllIIlI == null || getRotationDifference(lIllIlllllIIl.getRotation()) < getRotationDifference(lIllIllllIIlI.getRotation())) {
                  lIllIllllIIlI = lIllIlllllIIl;
               }
            }
         }
      }

      return lIllIllllIIlI;
   }

   public static VecRotation searchCenter(AxisAlignedBB lIlllIIlIlIll, boolean lIlllIIlIlIIl, boolean lIlllIIIllIII, boolean lIlllIIIlIlll, boolean lIlllIIlIIllI, float lIlllIIIlIlIl) {
      Vec3 lIlllIIIlIlII;
      if (lIlllIIlIlIIl) {
         lIlllIIIlIlII = new Vec3(lIlllIIlIlIll.minX + (lIlllIIlIlIll.maxX - lIlllIIlIlIll.minX) * (x * 0.3D + 1.0D), lIlllIIlIlIll.minY + (lIlllIIlIlIll.maxY - lIlllIIlIlIll.minY) * (y * 0.3D + 1.0D), lIlllIIlIlIll.minZ + (lIlllIIlIlIll.maxZ - lIlllIIlIlIll.minZ) * (z * 0.3D + 1.0D));
         return new VecRotation(lIlllIIIlIlII, toRotation(lIlllIIIlIlII, lIlllIIIlIlll));
      } else {
         lIlllIIIlIlII = new Vec3(lIlllIIlIlIll.minX + (lIlllIIlIlIll.maxX - lIlllIIlIlIll.minX) * x * 0.8D, lIlllIIlIlIll.minY + (lIlllIIlIlIll.maxY - lIlllIIlIlIll.minY) * y * 0.8D, lIlllIIlIlIll.minZ + (lIlllIIlIlIll.maxZ - lIlllIIlIlIll.minZ) * z * 0.8D);
         Rotation lIlllIIlIIIIl = toRotation(lIlllIIIlIlII, lIlllIIIlIlll);
         Vec3 lIlllIIlIIIII = mc.thePlayer.getPositionEyes(1.0F);
         VecRotation lIlllIIIlllll = null;

         for(double lIlllIIIlIIII = 0.4D; lIlllIIIlIIII < 0.6D; lIlllIIIlIIII += 0.1D) {
            for(double lIlllIIlIllll = 0.3D; lIlllIIlIllll < 1.0D; lIlllIIlIllll += 0.1D) {
               for(double lIlllIIllIIII = 0.4D; lIlllIIllIIII < 0.6D; lIlllIIllIIII += 0.1D) {
                  Exception lIlllIIIIllIl = new Vec3(lIlllIIlIlIll.minX + (lIlllIIlIlIll.maxX - lIlllIIlIlIll.minX) * lIlllIIIlIIII, lIlllIIlIlIll.minY + (lIlllIIlIlIll.maxY - lIlllIIlIlIll.minY) * lIlllIIlIllll, lIlllIIlIlIll.minZ + (lIlllIIlIlIll.maxZ - lIlllIIlIlIll.minZ) * lIlllIIllIIII);
                  int lIlllIIIIllII = toRotation(lIlllIIIIllIl, lIlllIIIlIlll);
                  long lIlllIIIIlIll = lIlllIIlIIIII.distanceTo(lIlllIIIIllIl);
                  if (!(lIlllIIIIlIll > (double)lIlllIIIlIlIl) && (lIlllIIlIIllI || isVisible(lIlllIIIIllIl))) {
                     VecRotation lIlllIIllIlIl = new VecRotation(lIlllIIIIllIl, lIlllIIIIllII);
                     if (lIlllIIIlllll != null) {
                        if (lIlllIIIllIII) {
                           if (!(getRotationDifference(lIlllIIllIlIl.getRotation(), lIlllIIlIIIIl) < getRotationDifference(lIlllIIIlllll.getRotation(), lIlllIIlIIIIl))) {
                              continue;
                           }
                        } else if (!(getRotationDifference(lIlllIIllIlIl.getRotation()) < getRotationDifference(lIlllIIIlllll.getRotation()))) {
                           continue;
                        }
                     }

                     lIlllIIIlllll = lIlllIIllIlIl;
                  }
               }
            }
         }

         return lIlllIIIlllll;
      }
   }

   public static double getRotationDifference(Entity lIllIllIllllI) {
      Rotation lIllIllIlllll = toRotation(getCenter(lIllIllIllllI.getEntityBoundingBox()), true);
      return getRotationDifference(lIllIllIlllll, new Rotation(mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch));
   }

   public static Vec3 getEyesPos() {
      int llIIIIlIIIIlI = 0.0F;
      String llIIIIlIIIIIl = 0.0F;
      float llIIIIlIIIIll = 0.0F;
      llIIIIlIIIIlI = (float)Minecraft.getMinecraft().thePlayer.posX;
      llIIIIlIIIIIl = (float)Minecraft.getMinecraft().thePlayer.posY;
      llIIIIlIIIIll = (float)Minecraft.getMinecraft().thePlayer.posZ;
      if (llIIIIlIIIIIl / 1000.0F < Minecraft.getMinecraft().thePlayer.getEyeHeight()) {
         llIIIIlIIIIIl = (float)((double)llIIIIlIIIIIl - 0.75D * (double)Minecraft.getMinecraft().thePlayer.getEyeHeight());
      }

      return new Vec3((double)llIIIIlIIIIlI, (double)llIIIIlIIIIIl + (double)Minecraft.getMinecraft().thePlayer.getEyeHeight(), (double)llIIIIlIIIIll);
   }

   static {
      x = random.nextDouble();
      y = random.nextDouble();
      z = random.nextDouble();
   }

   public static Vec3 getCenter(AxisAlignedBB lIlllIllIIIII) {
      return new Vec3(lIlllIllIIIII.minX + (lIlllIllIIIII.maxX - lIlllIllIIIII.minX) * 0.5D, lIlllIllIIIII.minY + (lIlllIllIIIII.maxY - lIlllIllIIIII.minY) * 0.5D, lIlllIllIIIII.minZ + (lIlllIllIIIII.maxZ - lIlllIllIIIII.minZ) * 0.5D);
   }

   @NotNull
   public static Rotation limitAngleChange(Rotation lIllIllIIlllI, Rotation lIllIllIIlIII, float lIllIllIIIlll) {
      long lIllIllIIIllI = getAngleDifference(lIllIllIIlIII.getYaw(), lIllIllIIlllI.getYaw());
      float lIllIllIIlIlI = getAngleDifference(lIllIllIIlIII.getPitch(), lIllIllIIlllI.getPitch());
      return new Rotation(lIllIllIIlllI.getYaw() + (lIllIllIIIllI > lIllIllIIIlll ? lIllIllIIIlll : Math.max(lIllIllIIIllI, -lIllIllIIIlll)), lIllIllIIlllI.getPitch() + (lIllIllIIlIlI > lIllIllIIIlll ? lIllIllIIIlll : Math.max(lIllIllIIlIlI, -lIllIllIIIlll)));
   }

   public static boolean isFaced(Entity lIllIlIlIIIIl, double lIllIlIlIIIII) {
      return RaycastUtils.raycastEntity(lIllIlIlIIIII, (lIllIIlllIllI) -> {
         return lIllIIlllIllI == lIllIlIlIIIIl;
      }) != null;
   }

   public static void setTargetRotation(Rotation lIllIlIIIIllI, int lIllIlIIIIIll) {
      if (!Double.isNaN((double)lIllIlIIIIllI.getYaw()) && !Double.isNaN((double)lIllIlIIIIllI.getPitch()) && !(lIllIlIIIIllI.getPitch() > 90.0F) && !(lIllIlIIIIllI.getPitch() < -90.0F)) {
         lIllIlIIIIllI.fixedSensitivity(mc.gameSettings.mouseSensitivity);
         targetRotation = lIllIlIIIIllI;
         keepLength = lIllIlIIIIIll;
      }
   }
}
