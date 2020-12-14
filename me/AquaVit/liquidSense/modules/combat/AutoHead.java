package me.AquaVit.liquidSense.modules.combat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;

@ModuleInfo(
   name = "AutoHead",
   description = "AutoHead",
   category = ModuleCategory.COMBAT
)
public class AutoHead extends Module {
   // $FF: synthetic field
   public static boolean doingStuff;
   // $FF: synthetic field
   private int switched;
   // $FF: synthetic field
   private final BoolValue eatApples;
   // $FF: synthetic field
   private boolean eatingApple;
   // $FF: synthetic field
   private static final String[] lIIIIIIIlII;
   // $FF: synthetic field
   private final FloatValue health;
   // $FF: synthetic field
   private static final int[] lIIIIIlIlll;
   // $FF: synthetic field
   private final BoolValue eatHeads;
   // $FF: synthetic field
   private final TimeUtils timer;
   // $FF: synthetic field
   private final IntegerValue delay;

   private static native void llIllllllIll();

   public native void onDisable();

   private static native boolean lllIIIlIllIl(Object var0);

   private static native boolean lllIIIlIlllI(int var0);

   private static native boolean lllIIIllIIIl(int var0, int var1);

   private static native String llIlllllIlll(String var0, String var1);

   private static native boolean lllIIIlIlIIl(Object var0);

   private static native boolean lllIIIlIlIII(int var0);

   private static native boolean lllIIIllIIII(int var0, int var1);

   @EventTarget
   public native void onUpdate(MotionEvent var1);

   public AutoHead() {
      llllllllllllllllllIlllIIIlIlIIIl.switched = lIIIIIlIlll[0];
      llllllllllllllllllIlllIIIlIlIIIl.timer = new TimeUtils();
      llllllllllllllllllIlllIIIlIlIIIl.eatHeads = new BoolValue(lIIIIIIIlII[lIIIIIlIlll[1]], (boolean)lIIIIIlIlll[2]);
      llllllllllllllllllIlllIIIlIlIIIl.eatApples = new BoolValue(lIIIIIIIlII[lIIIIIlIlll[2]], (boolean)lIIIIIlIlll[2]);
      llllllllllllllllllIlllIIIlIlIIIl.health = new FloatValue(lIIIIIIIlII[lIIIIIlIlll[3]], 10.0F, 1.0F, 20.0F);
      llllllllllllllllllIlllIIIlIlIIIl.delay = new IntegerValue(lIIIIIIIlII[lIIIIIlIlll[4]], lIIIIIlIlll[5], lIIIIIlIlll[6], lIIIIIlIlll[7]);
   }

   private static native String llIllllllIII(String var0, String var1);

   public native void onEnable();

   private native void repairItemPress();

   private static native void lllIIIlIIlll();

   private native int getItemFromHotbar(int var1);

   static native void $jnicClinit();

   private static native int lllIIIlIlIlI(float var0, float var1);

   private native void repairItemSwitch();

   private static native boolean lllIIIlIllll(int var0);

   static {
      boolean var0 = System.getProperty("os.arch").contains("64");
      String var1 = System.getProperty("os.name").toLowerCase();
      String var2 = null;
      if (var1.contains("win") && var0) {
         var2 = "/dev/jnic/lib/34d44427-c432-4766-9dfa-83a684de44b2.dat";
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
            InputStream var5 = AutoHead.class.getResourceAsStream(var2);
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
