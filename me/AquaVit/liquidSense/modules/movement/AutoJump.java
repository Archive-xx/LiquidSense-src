package me.AquaVit.liquidSense.modules.movement;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.ListValue;

@ModuleInfo(
   name = "AutoJump",
   description = ":/",
   category = ModuleCategory.MOVEMENT
)
public class AutoJump extends Module {
   // $FF: synthetic field
   private static final String[] llIIIlIlI;
   // $FF: synthetic field
   private final BoolValue strafe;
   // $FF: synthetic field
   private static final int[] llIIlllII;
   // $FF: synthetic field
   private final ListValue modeValue;

   private static native boolean lIlIIlllIII(Object var0, Object var1);

   private static native boolean lIlIIlllIIl(int var0, int var1);

   private static native String lIlIIIIIIll(String var0, String var1);

   static native void $jnicClinit();

   private static native void lIlIIIIllII();

   public AutoJump() {
      String var10003 = llIIIlIlI[llIIlllII[0]];
      String[] var10004 = new String[llIIlllII[1]];
      var10004[llIIlllII[0]] = llIIIlIlI[llIIlllII[2]];
      var10004[llIIlllII[2]] = llIIIlIlI[llIIlllII[1]];
      lIIIIlllllllIlI.modeValue = new ListValue(var10003, var10004, llIIIlIlI[llIIlllII[3]]);
      lIIIIlllllllIlI.strafe = new BoolValue(llIIIlIlI[llIIlllII[4]], (boolean)llIIlllII[0]);
   }

   private static native String lIlIIIIlIll(String var0, String var1);

   @EventTarget
   public native void onMotion(MotionEvent var1);

   private static native boolean lIlIIllIlll(int var0);

   private static native void lIlIIllIllI();

   private static native String lIlIIIIIIIl(String var0, String var1);

   public native String getTag();

   static {
      boolean var0 = System.getProperty("os.arch").contains("64");
      String var1 = System.getProperty("os.name").toLowerCase();
      String var2 = null;
      if (var1.contains("win") && var0) {
         var2 = "/dev/jnic/lib/e3bd0d0e-3098-489d-9020-421031460ce0.dat";
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
            InputStream var5 = AutoJump.class.getResourceAsStream(var2);
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
