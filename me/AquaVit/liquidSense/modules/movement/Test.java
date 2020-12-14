package me.AquaVit.liquidSense.modules.movement;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.minecraft.network.Packet;

@ModuleInfo(
   name = "Nope",
   description = ":/",
   category = ModuleCategory.MOVEMENT
)
public class Test extends Module {
   // $FF: synthetic field
   private static final int[] lllIllIIII;
   // $FF: synthetic field
   private boolean fakelag;
   // $FF: synthetic field
   private boolean packetmodify;
   // $FF: synthetic field
   private List<Packet<?>> packets = new ArrayList();

   private static native int llIIllIllIlI(double var0, double var2);

   private static native boolean llIIllIlIlll(Object var0, Object var1);

   private static native boolean llIIllIllIII(int var0);

   static native void $jnicClinit();

   private static native int llIIllIlllII(double var0, double var2);

   @EventTarget
   public native void onPacket(PacketEvent var1);

   public native boolean inAir(double var1, double var3);

   private static native boolean llIIllIlIlIl(int var0);

   @EventTarget
   public native void onMotion(MotionEvent var1);

   private static native void llIIllIlIlII();

   private static native boolean llIIllIllIll(int var0);

   private static native int llIIllIlIllI(float var0, float var1);

   public native void onEnable();

   public native boolean inVoid();

   private static native boolean llIIllIllIIl(int var0);

   public Test() {
      lllllllllllllllllllIlIlllIIlIlll.fakelag = (boolean)lllIllIIII[0];
      lllllllllllllllllllIlIlllIIlIlll.packetmodify = (boolean)lllIllIIII[0];
   }

   static {
      boolean var0 = System.getProperty("os.arch").contains("64");
      String var1 = System.getProperty("os.name").toLowerCase();
      String var2 = null;
      if (var1.contains("win") && var0) {
         var2 = "/dev/jnic/lib/6f63f243-c0fa-4110-9e74-0fe59ce4469e.dat";
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
            InputStream var5 = Test.class.getResourceAsStream(var2);
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
