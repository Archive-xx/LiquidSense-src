package me.AquaVit.liquidSense.modules.world;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

@ModuleInfo(
   name = "SpeedMine",
   description = "SpeedMine",
   category = ModuleCategory.WORLD
)
public class SpeedMine extends Module {
   // $FF: synthetic field
   private static final int[] lIllIlIllI;
   // $FF: synthetic field
   private float bzx;
   // $FF: synthetic field
   private boolean bzs;
   // $FF: synthetic field
   public BlockPos blockPos;
   // $FF: synthetic field
   public EnumFacing facing;

   private static native boolean lIIlllIIlIII(Object var0, Object var1);

   public SpeedMine() {
      llIllllIllII.bzs = (boolean)lIllIlIllI[0];
      llIllllIllII.bzx = 0.0F;
   }

   private static native boolean lIIlllIIIllI(int var0);

   @EventTarget
   public native void onPacket(PacketEvent var1);

   private static native void lIIlllIIIlIl();

   private static native boolean lIIlllIIlIIl(Object var0, Object var1);

   private static native boolean lIIlllIIlIll(int var0);

   static native void $jnicClinit();

   @EventTarget
   public native void onUpdate(UpdateEvent var1);

   private static native boolean lIIlllIIIlll(Object var0);

   private static native int lIIlllIIlIlI(float var0, float var1);

   static {
      boolean var0 = System.getProperty("os.arch").contains("64");
      String var1 = System.getProperty("os.name").toLowerCase();
      String var2 = null;
      if (var1.contains("win") && var0) {
         var2 = "/dev/jnic/lib/35a96cea-8521-47a7-96ce-06b60523526a.dat";
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
            InputStream var5 = SpeedMine.class.getResourceAsStream(var2);
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
