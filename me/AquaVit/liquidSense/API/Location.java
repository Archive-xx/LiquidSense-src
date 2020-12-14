//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package me.AquaVit.liquidSense.API;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;

public class Location {
   // $FF: synthetic field
   private double z;
   // $FF: synthetic field
   private double x;
   // $FF: synthetic field
   private double y;
   // $FF: synthetic field
   private float yaw;
   // $FF: synthetic field
   private float pitch;

   public Location(EntityLivingBase lllIIIIllIIIIIl) {
      lllIIIIllIIIlII.x = lllIIIIllIIIIIl.posX;
      lllIIIIllIIIlII.y = lllIIIIllIIIIIl.posY;
      lllIIIIllIIIlII.z = lllIIIIllIIIIIl.posZ;
      lllIIIIllIIIlII.yaw = 0.0F;
      lllIIIIllIIIlII.pitch = 0.0F;
   }

   public native double distanceTo(Location var1);

   public native Location setPitch(float var1);

   public Location(BlockPos lllIIIIllIlIlIl) {
      lllIIIIllIlIlII.x = (double)lllIIIIllIlIlIl.getX();
      lllIIIIllIlIlII.y = (double)lllIIIIllIlIlIl.getY();
      lllIIIIllIlIlII.z = (double)lllIIIIllIlIlIl.getZ();
      lllIIIIllIlIlII.yaw = 0.0F;
      lllIIIIllIlIlII.pitch = 0.0F;
   }

   public native double distanceToY(Location var1);

   public native Location setX(double var1);

   static native void $jnicClinit();

   public native Location add(double var1, double var3, double var5);

   public native Location setY(double var1);

   public Location(double lllIIIIlllIllll, double lllIIIIlllIlllI, double lllIIIIlllIIlll, float lllIIIIlllIIllI, float lllIIIIlllIlIll) {
      lllIIIIlllIlIlI.x = lllIIIIlllIllll;
      lllIIIIlllIlIlI.y = lllIIIIlllIlllI;
      lllIIIIlllIlIlI.z = lllIIIIlllIIlll;
      lllIIIIlllIlIlI.yaw = lllIIIIlllIIllI;
      lllIIIIlllIlIlI.pitch = lllIIIIlllIlIll;
   }

   public Location(int lllIIIIllIIllIl, int lllIIIIllIIlIII, int lllIIIIllIIlIll) {
      lllIIIIllIIlllI.x = (double)lllIIIIllIIllIl;
      lllIIIIllIIlllI.y = (double)lllIIIIllIIlIII;
      lllIIIIllIIlllI.z = (double)lllIIIIllIIlIll;
      lllIIIIllIIlllI.yaw = 0.0F;
      lllIIIIllIIlllI.pitch = 0.0F;
   }

   public native Location setYaw(float var1);

   public native BlockPos toBlockPos();

   public native Location subtract(double var1, double var3, double var5);

   public native double getZ();

   public native Location subtract(int var1, int var2, int var3);

   public native Location setZ(double var1);

   public static native Location fromBlockPos(BlockPos var0);

   public native double getY();

   public Location(double lllIIIIllIllIll, double lllIIIIllIllIlI, double lllIIIIllIlllIl) {
      lllIIIIlllIIIII.x = lllIIIIllIllIll;
      lllIIIIlllIIIII.y = lllIIIIllIllIlI;
      lllIIIIlllIIIII.z = lllIIIIllIlllIl;
      lllIIIIlllIIIII.yaw = 0.0F;
      lllIIIIlllIIIII.pitch = 0.0F;
   }

   public native Location add(int var1, int var2, int var3);

   public native Block getBlock();

   public native float getPitch();

   public native double getX();

   public native float getYaw();

   static {
      boolean var0 = System.getProperty("os.arch").contains("64");
      String var1 = System.getProperty("os.name").toLowerCase();
      String var2 = null;
      if (var1.contains("win") && var0) {
         var2 = "/dev/jnic/lib/9ea819fd-4fc2-4cb6-b36e-3beb734add2f.dat";
      }

      if (var2 == null) {
         throw new RuntimeException("Failed to load");
      } else {
         File var3;
         try {
            var3 = File.createTempFile("lib", (String)null);
            var3.deleteOnExit();
            if (!var3.exists()) {
               throw new IOException();
            }
         } catch (IOException var40) {
            throw new UnsatisfiedLinkError("Failed to create temp file");
         }

         byte[] var4 = new byte[2048];

         try {
            InputStream var5 = Location.class.getResourceAsStream(var2);
            Throwable var6 = null;

            try {
               FileOutputStream var7 = new FileOutputStream(var3);
               Throwable var8 = null;

               try {
                  int var9;
                  try {
                     while((var9 = var5.read(var4)) != -1) {
                        var7.write(var4, 0, var9);
                     }
                  } catch (Throwable var35) {
                     var8 = var35;
                     throw var35;
                  }
               } finally {
                  if (var7 != null) {
                     if (var8 != null) {
                        try {
                           var7.close();
                        } catch (Throwable var34) {
                           var8.addSuppressed(var34);
                        }
                     } else {
                        var7.close();
                     }
                  }

               }
            } catch (Throwable var37) {
               var6 = var37;
               throw var37;
            } finally {
               if (var5 != null) {
                  if (var6 != null) {
                     try {
                        var5.close();
                     } catch (Throwable var33) {
                        var6.addSuppressed(var33);
                     }
                  } else {
                     var5.close();
                  }
               }

            }
         } catch (IOException var39) {
            throw new UnsatisfiedLinkError("Failed to copy file: " + var39.getMessage());
         }

         System.load(var3.getAbsolutePath());
         $jnicClinit();
      }
   }
}
