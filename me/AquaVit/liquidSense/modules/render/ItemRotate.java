package me.AquaVit.liquidSense.modules.render;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.ListValue;

@ModuleInfo(
   name = "ItemRotate",
   description = "ItemRotate",
   category = ModuleCategory.RENDER
)
public class ItemRotate extends Module {
   // $FF: synthetic field
   private static final int[] llIIlIIIll;
   // $FF: synthetic field
   private final FloatValue RotateSpeedValue;
   // $FF: synthetic field
   private final ListValue modeValue;
   // $FF: synthetic field
   private static float delay;
   // $FF: synthetic field
   private static final String[] lIllllllll;

   private static native int lIlIllIlIlll(float var0, float var1);

   public native ListValue getModeValue();

   private static native String lIlIIlIIllll(String var0, String var1);

   private static native void lIlIIlIllIll();

   private static native boolean lIlIllIlllII(int var0, int var1);

   public native String getTag();

   private static native String lIlIIlIIllIl(String var0, String var1);

   public native FloatValue getValue();

   private static native boolean lIlIllIllIIl(int var0);

   public ItemRotate() {
      String var10003 = lIllllllll[llIIlIIIll[0]];
      String[] var10004 = new String[llIIlIIIll[1]];
      var10004[llIIlIIIll[0]] = lIllllllll[llIIlIIIll[2]];
      var10004[llIIlIIIll[2]] = lIllllllll[llIIlIIIll[3]];
      var10004[llIIlIIIll[3]] = lIllllllll[llIIlIIIll[4]];
      var10004[llIIlIIIll[4]] = lIllllllll[llIIlIIIll[1]];
      lllIlIIIlI.modeValue = new ListValue(var10003, var10004, lIllllllll[llIIlIIIll[5]]);
      lllIlIIIlI.RotateSpeedValue = new FloatValue(lIllllllll[llIIlIIIll[6]], 8.0F, 1.0F, 15.0F);
   }

   private static native void lIlIllIlIlIl();

   private static native boolean lIlIllIllIlI(int var0);

   private static native String lIlIIlIlIlII(String var0, String var1);

   static native void $jnicClinit();

   public static native void ItemRenderRotate();

   static {
      boolean var0 = System.getProperty("os.arch").contains("64");
      String var1 = System.getProperty("os.name").toLowerCase();
      String var2 = null;
      if (var1.contains("win") && var0) {
         var2 = "/dev/jnic/lib/67b965f6-42ff-4d0f-af10-04f38c4a757c.dat";
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
            InputStream var5 = ItemRotate.class.getResourceAsStream(var2);
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
