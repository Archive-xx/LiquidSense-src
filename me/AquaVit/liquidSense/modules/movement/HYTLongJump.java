package me.AquaVit.liquidSense.modules.movement;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.network.Packet;

@ModuleInfo(
   name = "HYTLongJump",
   description = ":/",
   category = ModuleCategory.MOVEMENT
)
public class HYTLongJump extends Module {
   // $FF: synthetic field
   private static final int[] lIIIIllIl;
   // $FF: synthetic field
   private final LinkedList<double[]> positions;
   // $FF: synthetic field
   private final IntegerValue newLagPowerValue;
   // $FF: synthetic field
   private final IntegerValue lagPowerValue;
   // $FF: synthetic field
   private final List<Packet> packets;
   // $FF: synthetic field
   private static final String[] llllIlll;
   // $FF: synthetic field
   private boolean canBoost;
   // $FF: synthetic field
   private boolean jumped;
   // $FF: synthetic field
   private boolean disableLogger;
   // $FF: synthetic field
   private final ListValue modeValue;

   @EventTarget
   public native void onPacket(PacketEvent var1);

   @EventTarget
   public native void onMotion(MotionEvent var1);

   private static native void llIIlllIll();

   private static native boolean lllIIIIlII(int var0);

   @EventTarget
   public native void onMove(MoveEvent var1);

   @EventTarget
   public native void onJump(JumpEvent var1);

   private static native boolean lllIIIIIlI(Object var0);

   private static native void lllIIIIIIl();

   private static native String llIIlIlIIl(String var0, String var1);

   private static native boolean lllIIIIlIl(Object var0);

   public HYTLongJump() {
      String var10003 = llllIlll[lIIIIllIl[0]];
      String[] var10004 = new String[lIIIIllIl[1]];
      var10004[lIIIIllIl[0]] = llllIlll[lIIIIllIl[2]];
      var10004[lIIIIllIl[2]] = llllIlll[lIIIIllIl[1]];
      lIlIlllIIIllIlI.modeValue = new ListValue(var10003, var10004, llllIlll[lIIIIllIl[3]]);
      lIlIlllIIIllIlI.lagPowerValue = new IntegerValue(llllIlll[lIIIIllIl[4]], lIIIIllIl[5], lIIIIllIl[2], lIIIIllIl[5]);
      lIlIlllIIIllIlI.newLagPowerValue = new IntegerValue(llllIlll[lIIIIllIl[6]], lIIIIllIl[7], lIIIIllIl[8], lIIIIllIl[9]);
      lIlIlllIIIllIlI.packets = new ArrayList();
      lIlIlllIIIllIlI.positions = new LinkedList();
   }

   static native void $jnicClinit();

   public native String getTag();

   @EventTarget
   public native void onUpdate(UpdateEvent var1);

   public native void onDisable();

   private static native String llIIlIlIII(String var0, String var1);

   public native void onEnable();

   private native void blink();

   private static native String llIIlIlIlI(String var0, String var1);

   private static native boolean lllIIIlllI(int var0, int var1);

   private static native boolean lllIIIIIll(int var0);

   static {
      boolean var0 = System.getProperty("os.arch").contains("64");
      String var1 = System.getProperty("os.name").toLowerCase();
      String var2 = null;
      if (var1.contains("win") && var0) {
         var2 = "/dev/jnic/lib/d3141c90-6ea7-416d-920e-cae32b811f51.dat";
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
            InputStream var5 = HYTLongJump.class.getResourceAsStream(var2);
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
