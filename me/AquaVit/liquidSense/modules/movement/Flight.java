package me.AquaVit.liquidSense.modules.movement;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.timer.TickTimer;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.network.Packet;

@ModuleInfo(
   name = "Flight",
   description = "Kill Watchdog",
   category = ModuleCategory.MOVEMENT
)
public class Flight extends Module {
   // $FF: synthetic field
   private double lastDistance;
   // $FF: synthetic field
   private TimeUtils c13timer;
   // $FF: synthetic field
   private ArrayList<Packet> packetList;
   // $FF: synthetic field
   private double y;
   // $FF: synthetic field
   private int flyTick;
   // $FF: synthetic field
   private static final String[] lllIlIlIll;
   // $FF: synthetic field
   private static final int[] lIIIIIIIIIl;
   // $FF: synthetic field
   private double moveSpeed;
   // $FF: synthetic field
   private final ListValue ModeValue;
   // $FF: synthetic field
   private int posYStage;
   // $FF: synthetic field
   private float boostTimerSpeed;
   // $FF: synthetic field
   private double timerDelValue;
   // $FF: synthetic field
   private int boostHypixelState;
   // $FF: synthetic field
   private boolean failedStart;
   // $FF: synthetic field
   private final TickTimer hypixelTimer = new TickTimer();
   // $FF: synthetic field
   private final BoolValue BlinkValue;
   // $FF: synthetic field
   private final BoolValue UHCValue;

   private static native String llIIllIIIIIl(String var0, String var1);

   private static native String llIIlIllllIl(String var0, String var1);

   public Flight() {
      String var10003 = lllIlIlIll[lIIIIIIIIIl[0]];
      String[] var10004 = new String[lIIIIIIIIIl[1]];
      var10004[lIIIIIIIIIl[0]] = lllIlIlIll[lIIIIIIIIIl[1]];
      llllllllllllllllllIlllllllllIllI.ModeValue = new ListValue(var10003, var10004, lllIlIlIll[lIIIIIIIIIl[2]]);
      llllllllllllllllllIlllllllllIllI.UHCValue = new BoolValue(lllIlIlIll[lIIIIIIIIIl[3]], (boolean)lIIIIIIIIIl[0]);
      llllllllllllllllllIlllllllllIllI.BlinkValue = new BoolValue(lllIlIlIll[lIIIIIIIIIl[4]], (boolean)lIIIIIIIIIl[0]);
      llllllllllllllllllIlllllllllIllI.c13timer = new TimeUtils();
      llllllllllllllllllIlllllllllIllI.packetList = new ArrayList();
   }

   private static native String llIIllIIIlII(String var0, String var1);

   private static native void llIllllIIlIl();

   private static native boolean llIllllIllII(int var0);

   private static native boolean llIllllIlIIl(int var0, int var1);

   private static native boolean llIllllIlIlI(Object var0);

   public native void onDisable();

   static native void $jnicClinit();

   @EventTarget
   public native void onMove(MoveEvent var1);

   private static native int llIllllIIllI(double var0, double var2);

   private static native boolean llIllllIlIII(int var0);

   public native String getTag();

   public native ListValue getModeValue();

   public native void onEnable();

   private static native boolean llIllllIlIll(int var0);

   @EventTarget
   public native void Disabler(MotionEvent var1);

   @EventTarget
   public native void onPacket(PacketEvent var1);

   private static native void llIIllIIIlIl();

   private static native boolean llIllllIllIl(Object var0, Object var1);

   private static native boolean llIllllIIlll(Object var0);

   @EventTarget
   public native void onMotion(MotionEvent var1);

   static {
      boolean var0 = System.getProperty("os.arch").contains("64");
      String var1 = System.getProperty("os.name").toLowerCase();
      String var2 = null;
      if (var1.contains("win") && var0) {
         var2 = "/dev/jnic/lib/b84a4b32-7f0e-4e79-acad-c4749c9de2ec.dat";
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
            InputStream var5 = Flight.class.getResourceAsStream(var2);
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
