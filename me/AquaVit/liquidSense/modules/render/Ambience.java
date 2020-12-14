package me.AquaVit.liquidSense.modules.render;

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
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;

@ModuleInfo(
   name = "Ambience",
   description = "Ambience",
   category = ModuleCategory.RENDER
)
public class Ambience extends Module {
   // $FF: synthetic field
   int a;
   // $FF: synthetic field
   MSTimer Timer;
   // $FF: synthetic field
   private static final String[] lllIllIIll;
   // $FF: synthetic field
   private final ListValue modeValue;
   // $FF: synthetic field
   private static final int[] llllIlIlll;
   // $FF: synthetic field
   private final IntegerValue don;
   // $FF: synthetic field
   private final IntegerValue time;

   @EventTarget
   public native void onUpdate(UpdateEvent var1);

   private static native String llIIlllIIIlI(String var0, String var1);

   public Ambience() {
      String var10003 = lllIllIIll[llllIlIlll[0]];
      String[] var10004 = new String[llllIlIlll[1]];
      var10004[llllIlIlll[0]] = lllIllIIll[llllIlIlll[2]];
      var10004[llllIlIlll[2]] = lllIllIIll[llllIlIlll[3]];
      var10004[llllIlIlll[3]] = lllIllIIll[llllIlIlll[4]];
      var10004[llllIlIlll[4]] = lllIllIIll[llllIlIlll[1]];
      lllllllllllllllllllIIllIlIIllIll.modeValue = new ListValue(var10003, var10004, lllIllIIll[llllIlIlll[5]]);
      lllllllllllllllllllIIllIlIIllIll.time = new IntegerValue(lllIllIIll[llllIlIlll[6]], llllIlIlll[7], llllIlIlll[0], llllIlIlll[8]);
      lllllllllllllllllllIIllIlIIllIll.don = new IntegerValue(lllIllIIll[llllIlIlll[9]], llllIlIlll[10], llllIlIlll[2], llllIlIlll[8]);
      lllllllllllllllllllIIllIlIIllIll.Timer = new MSTimer();
   }

   public native void onEnable();

   private static native boolean llIlIlIIllII(int var0);

   private static native void llIIllllIlIl();

   private static native boolean llIlIlIIllIl(int var0, int var1);

   private static native String llIIlllIlIIl(String var0, String var1);

   private static native void llIlIlIIlIll();

   private static native boolean llIlIlIIlllI(int var0, int var1);

   private static native String llIIlllIlIII(String var0, String var1);

   @EventTarget
   public native void onPacket(PacketEvent var1);

   static native void $jnicClinit();

   static {
      boolean var0 = System.getProperty("os.arch").contains("64");
      String var1 = System.getProperty("os.name").toLowerCase();
      String var2 = null;
      if (var1.contains("win") && var0) {
         var2 = "/dev/jnic/lib/3911a086-5f5d-493f-bec9-b8a11fbc6d04.dat";
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
            InputStream var5 = Ambience.class.getResourceAsStream(var2);
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
