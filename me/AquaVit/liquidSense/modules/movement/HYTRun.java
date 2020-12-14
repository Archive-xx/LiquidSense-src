package me.AquaVit.liquidSense.modules.movement;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.combat.Aura;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.minecraft.network.Packet;

@ModuleInfo(
   name = "HYTRun",
   description = ":/",
   category = ModuleCategory.MOVEMENT
)
public class HYTRun extends Module {
   // $FF: synthetic field
   private final FloatValue MotionY;
   // $FF: synthetic field
   private final TimeUtils timer;
   // $FF: synthetic field
   Aura killAura;
   // $FF: synthetic field
   private static final int[] llIlIIIII;
   // $FF: synthetic field
   private boolean disableLogger;
   // $FF: synthetic field
   private final FloatValue MotionF;
   // $FF: synthetic field
   private static final String[] llIIllIll;
   // $FF: synthetic field
   private final List<Packet> packets = new ArrayList();
   // $FF: synthetic field
   private final LinkedList<double[]> positions = new LinkedList();

   public native void onEnable();

   public HYTRun() {
      lIIIIllIIIllIII.killAura = (Aura)LiquidBounce.moduleManager.getModule(Aura.class);
      lIIIIllIIIllIII.timer = new TimeUtils();
      lIIIIllIIIllIII.MotionF = new FloatValue(llIIllIll[llIlIIIII[0]], 5.0F, 0.1F, 30.0F);
      lIIIIllIIIllIII.MotionY = new FloatValue(llIIllIll[llIlIIIII[1]], 5.0F, 0.1F, 30.0F);
   }

   private static native boolean lIlIlIIIlll(int var0);

   private static native String lIlIIllIlII(String var0, String var1);

   private static native void lIlIlIIIIlI();

   @EventTarget
   public native void onPacket(PacketEvent var1);

   private static native boolean lIlIlIIlIII(int var0, int var1);

   public native void onDisable();

   private static native boolean lIlIlIIIlIl(Object var0);

   private static native boolean lIlIlIIIlII(Object var0);

   private static native void lIlIIlllIlI();

   private native void blink();

   private static native boolean lIlIlIIIllI(int var0);

   private static native String lIlIIllIIll(String var0, String var1);

   static native void $jnicClinit();

   @EventTarget
   public native void onUpdate(UpdateEvent var1);

   static {
      boolean var0 = System.getProperty("os.arch").contains("64");
      String var1 = System.getProperty("os.name").toLowerCase();
      String var2 = null;
      if (var1.contains("win") && var0) {
         var2 = "/dev/jnic/lib/68d925d9-92f5-4fbb-ae93-d6650d29836b.dat";
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
            InputStream var5 = HYTRun.class.getResourceAsStream(var2);
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
