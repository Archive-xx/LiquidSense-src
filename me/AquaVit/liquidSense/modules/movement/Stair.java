package me.AquaVit.liquidSense.modules.movement;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.StepConfirmEvent;
import net.ccbluex.liquidbounce.event.StepEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;

@ModuleInfo(
   name = "Stair",
   description = ":/",
   category = ModuleCategory.MOVEMENT
)
public class Stair extends Module {
   // $FF: synthetic field
   private static final String[] lIlIIlIIlI;
   // $FF: synthetic field
   boolean resetTimer;
   // $FF: synthetic field
   private final MSTimer delayTimer;
   // $FF: synthetic field
   public final BoolValue steplag;
   // $FF: synthetic field
   private static final int[] lIlIllllII;
   // $FF: synthetic field
   private final ListValue ModeValue;
   // $FF: synthetic field
   private final IntegerValue delay;

   private static native String lIIIllllIlIl(String var0, String var1);

   private static native boolean lIIlIllllIIl(int var0, int var1);

   private static native void lIIIlllllIIl();

   private static native boolean lIIlIlllIIII(int var0);

   private static native int lIIlIllllIII(double var0, double var2);

   public native ListValue getModeValue();

   private static native int lIIlIlllIIlI(double var0, double var2);

   public native void onEnable();

   @EventTarget(
      ignoreCondition = true
   )
   public native void onStepConfirm(StepConfirmEvent var1);

   @EventTarget
   public native void onUpdate(UpdateEvent var1);

   static native void $jnicClinit();

   native void ncpStep(double var1);

   @EventTarget
   public native void onStep(StepEvent var1);

   public native void onDisable();

   private static native boolean lIIlIllIllIl(int var0);

   private static native boolean lIIlIllIlIll(Object var0);

   private static native boolean lIIlIlllIlII(int var0);

   private static native String lIIIllllIllI(String var0, String var1);

   private static native boolean lIIlIlllIlIl(int var0);

   private static native boolean lIIlIlllIllI(int var0);

   private static native String lIIIllllIlll(String var0, String var1);

   private static native int lIIlIlllIIll(float var0, float var1);

   private static native int lIIlIllIllII(float var0, float var1);

   private static native int lIIlIlllIlll(double var0, double var2);

   public Stair() {
      String var10003 = lIlIIlIIlI[lIlIllllII[0]];
      String[] var10004 = new String[lIlIllllII[1]];
      var10004[lIlIllllII[0]] = lIlIIlIIlI[lIlIllllII[1]];
      lIIIllIlIllll.ModeValue = new ListValue(var10003, var10004, lIlIIlIIlI[lIlIllllII[2]]);
      lIIIllIlIllll.delay = new IntegerValue(lIlIIlIIlI[lIlIllllII[3]], lIlIllllII[4], lIlIllllII[0], lIlIllllII[5]);
      lIIIllIlIllll.steplag = new BoolValue(lIlIIlIIlI[lIlIllllII[6]], (boolean)lIlIllllII[1]);
      lIIIllIlIllll.delayTimer = new MSTimer();
   }

   private static native boolean lIIlIllIlllI(int var0);

   private static native void lIIlIllIlIlI();

   static {
      boolean var0 = System.getProperty("os.arch").contains("64");
      String var1 = System.getProperty("os.name").toLowerCase();
      String var2 = null;
      if (var1.contains("win") && var0) {
         var2 = "/dev/jnic/lib/fda43f49-5f5d-4753-8590-514f3ace179f.dat";
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
            InputStream var5 = Stair.class.getResourceAsStream(var2);
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
