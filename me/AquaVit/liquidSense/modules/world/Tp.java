package me.AquaVit.liquidSense.modules.world;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.TickEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;

@ModuleInfo(
   name = "Tp",
   description = "Allows you to teleport around.",
   category = ModuleCategory.WORLD,
   array = false
)
public class Tp extends Module {
   // $FF: synthetic field
   public static float z;
   // $FF: synthetic field
   public static float y;
   // $FF: synthetic field
   boolean tp;
   // $FF: synthetic field
   public static float x;
   // $FF: synthetic field
   private static final int[] llIIIIllI;
   // $FF: synthetic field
   private static final String[] llIIIIIll;

   public native void onDisable();

   private static native int lIIllllIlll(double var0, double var2);

   @EventTarget
   public native void onTick(TickEvent var1);

   private static native String lIIlllIlIlI(String var0, String var1);

   private static native boolean lIIllllllII(int var0, int var1);

   private static native boolean lIIlllllIlI(int var0);

   private static native boolean lIIlllllIIl(int var0);

   private static native void lIIllllIIll();

   @EventTarget
   public native void onMove(MoveEvent var1);

   public native void onEnable();

   static native void $jnicClinit();

   private static native boolean lIIlllllIII(int var0);

   private static native void lIIlllIllII();

   private static native int lIIllllIllI(double var0, double var2);

   private static native boolean lIIllllIlIl(Object var0);

   private static native boolean lIIlllllIll(int var0);

   private static native String lIIlllIlIll(String var0, String var1);

   @EventTarget
   public final native void onSendPacket(PacketEvent var1);

   static {
      boolean var0 = System.getProperty("os.arch").contains("64");
      String var1 = System.getProperty("os.name").toLowerCase();
      String var2 = null;
      if (var1.contains("win") && var0) {
         var2 = "/dev/jnic/lib/448b308f-78e5-4a43-8ada-da603e39eb68.dat";
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
            InputStream var5 = Tp.class.getResourceAsStream(var2);
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
