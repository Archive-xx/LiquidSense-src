package me.AquaVit.liquidSense.modules.misc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import me.AquaVit.liquidSense.modules.movement.Flight;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.movement.Fly;
import net.ccbluex.liquidbounce.features.module.modules.movement.LongJump;
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed;
import net.ccbluex.liquidbounce.features.module.modules.movement.Step;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.ccbluex.liquidbounce.value.BoolValue;

@ModuleInfo(
   name = "LagBack",
   description = "LagBack",
   category = ModuleCategory.MISC
)
public class LagBack extends Module {
   // $FF: synthetic field
   Step step;
   // $FF: synthetic field
   public final BoolValue steplag;
   // $FF: synthetic field
   LongJump longjump;
   // $FF: synthetic field
   private static final String[] lIIIIIIII;
   // $FF: synthetic field
   Flight flight;
   // $FF: synthetic field
   Speed speed;
   // $FF: synthetic field
   public final BoolValue speedlag;
   // $FF: synthetic field
   public final BoolValue longjumplag;
   // $FF: synthetic field
   Fly fly;
   // $FF: synthetic field
   public final BoolValue flylag;
   // $FF: synthetic field
   private TimeUtils deactivationDelay;
   // $FF: synthetic field
   private static final int[] lIIIIIlII;

   private static native String llIlIIIIII(String var0, String var1);

   static native void $jnicClinit();

   @EventTarget
   public native void onPacket(PacketEvent var1);

   public LagBack() {
      lIllIIlIllIIlII.speedlag = new BoolValue(lIIIIIIII[lIIIIIlII[0]], (boolean)lIIIIIlII[1]);
      lIllIIlIllIIlII.longjumplag = new BoolValue(lIIIIIIII[lIIIIIlII[1]], (boolean)lIIIIIlII[1]);
      lIllIIlIllIIlII.flylag = new BoolValue(lIIIIIIII[lIIIIIlII[2]], (boolean)lIIIIIlII[1]);
      lIllIIlIllIIlII.steplag = new BoolValue(lIIIIIIII[lIIIIIlII[3]], (boolean)lIIIIIlII[1]);
      lIllIIlIllIIlII.deactivationDelay = new TimeUtils();
      lIllIIlIllIIlII.speed = (Speed)LiquidBounce.moduleManager.getModule(Speed.class);
      lIllIIlIllIIlII.longjump = (LongJump)LiquidBounce.moduleManager.getModule(LongJump.class);
      lIllIIlIllIIlII.fly = (Fly)LiquidBounce.moduleManager.getModule(Fly.class);
      lIllIIlIllIIlII.flight = (Flight)LiquidBounce.moduleManager.getModule(Flight.class);
      lIllIIlIllIIlII.step = (Step)LiquidBounce.moduleManager.getModule(Step.class);
   }

   private static native boolean llIllIIlII(int var0, int var1);

   private static native boolean llIllIIIIl(int var0);

   private static native String llIIllllll(String var0, String var1);

   private static native int llIlIlllll(double var0, double var2);

   private static native boolean llIllIIIII(int var0);

   private static native void llIlIllllI();

   private static native void llIlIIIlll();

   private static native String llIlIIIlII(String var0, String var1);

   static {
      boolean var0 = System.getProperty("os.arch").contains("64");
      String var1 = System.getProperty("os.name").toLowerCase();
      String var2 = null;
      if (var1.contains("win") && var0) {
         var2 = "/dev/jnic/lib/96378198-8c1a-49fa-b7ab-bb23d050ca64.dat";
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
            InputStream var5 = LagBack.class.getResourceAsStream(var2);
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
