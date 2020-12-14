package me.AquaVit.liquidSense.modules.misc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;

@ModuleInfo(
   name = "Animations",
   description = "Animations",
   category = ModuleCategory.MISC
)
public class Animations extends Module {
   // $FF: synthetic field
   private final ListValue modeValue;
   // $FF: synthetic field
   public static final FloatValue Scale;
   // $FF: synthetic field
   private static int f3;
   // $FF: synthetic field
   public static final FloatValue SpeedRotate;
   // $FF: synthetic field
   private static final int[] lIIIllIIIII;
   // $FF: synthetic field
   public static final FloatValue itemPosY;
   // $FF: synthetic field
   private static final String[] llllIllllI;
   // $FF: synthetic field
   public static final FloatValue itemPosX;
   // $FF: synthetic field
   public static final FloatValue itemPosZ;
   // $FF: synthetic field
   private static float shabi;
   // $FF: synthetic field
   private final IntegerValue ScrewSpeedValue;

   public native String getTag();

   public native ListValue getModeValue();

   public Animations() {
      String var10003 = llllIllllI[lIIIllIIIII[0]];
      String[] var10004 = new String[lIIIllIIIII[1]];
      var10004[lIIIllIIIII[0]] = llllIllllI[lIIIllIIIII[2]];
      var10004[lIIIllIIIII[2]] = llllIllllI[lIIIllIIIII[3]];
      var10004[lIIIllIIIII[3]] = llllIllllI[lIIIllIIIII[4]];
      var10004[lIIIllIIIII[4]] = llllIllllI[lIIIllIIIII[5]];
      var10004[lIIIllIIIII[5]] = llllIllllI[lIIIllIIIII[6]];
      var10004[lIIIllIIIII[6]] = llllIllllI[lIIIllIIIII[7]];
      var10004[lIIIllIIIII[7]] = llllIllllI[lIIIllIIIII[8]];
      var10004[lIIIllIIIII[8]] = llllIllllI[lIIIllIIIII[9]];
      var10004[lIIIllIIIII[9]] = llllIllllI[lIIIllIIIII[10]];
      var10004[lIIIllIIIII[10]] = llllIllllI[lIIIllIIIII[11]];
      var10004[lIIIllIIIII[11]] = llllIllllI[lIIIllIIIII[12]];
      var10004[lIIIllIIIII[12]] = llllIllllI[lIIIllIIIII[13]];
      var10004[lIIIllIIIII[13]] = llllIllllI[lIIIllIIIII[14]];
      var10004[lIIIllIIIII[14]] = llllIllllI[lIIIllIIIII[15]];
      var10004[lIIIllIIIII[15]] = llllIllllI[lIIIllIIIII[16]];
      var10004[lIIIllIIIII[16]] = llllIllllI[lIIIllIIIII[17]];
      var10004[lIIIllIIIII[17]] = llllIllllI[lIIIllIIIII[18]];
      var10004[lIIIllIIIII[18]] = llllIllllI[lIIIllIIIII[1]];
      llllllllllllllllllIlIIIlIlIIIlII.modeValue = new ListValue(var10003, var10004, llllIllllI[lIIIllIIIII[19]]);
      llllllllllllllllllIlIIIlIlIIIlII.ScrewSpeedValue = new IntegerValue(llllIllllI[lIIIllIIIII[20]], lIIIllIIIII[11], lIIIllIIIII[2], lIIIllIIIII[20]);
   }

   private static native boolean llllIIIlIlll(Object var0);

   private static native boolean llllIIIlIllI(int var0, int var1);

   private static native boolean llllIIIlIlIl(int var0);

   private static native void llIllIIlIIll();

   private static native int llllIIIlIIll(float var0, float var1);

   public native IntegerValue getValue();

   public static native void ItemRenderRotation();

   public static native void renderblock(float var0, float var1);

   private static native boolean llllIIIlIlII(int var0);

   private static native void llllIIIlIIlI();

   static native void $jnicClinit();

   private static native String llIllIIIIIII(String var0, String var1);

   private static native String llIllIIIIIll(String var0, String var1);

   private static native String llIlIlllllll(String var0, String var1);

   static {
      boolean var0 = System.getProperty("os.arch").contains("64");
      String var1 = System.getProperty("os.name").toLowerCase();
      String var2 = null;
      if (var1.contains("win") && var0) {
         var2 = "/dev/jnic/lib/fbce58e5-a1a2-4719-97c6-aac854e0c519.dat";
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
            InputStream var5 = Animations.class.getResourceAsStream(var2);
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
