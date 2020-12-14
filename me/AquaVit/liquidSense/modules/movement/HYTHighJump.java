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
import net.ccbluex.liquidbounce.event.KeyEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.network.Packet;

@ModuleInfo(
   name = "HYTHighJump",
   description = ":/",
   category = ModuleCategory.MOVEMENT
)
public class HYTHighJump extends Module {
   // $FF: synthetic field
   private boolean jumped;
   // $FF: synthetic field
   private boolean disableLogger;
   // $FF: synthetic field
   private final List<Packet> packets = new ArrayList();
   // $FF: synthetic field
   private static final int[] lIIIIIIIIlI;
   // $FF: synthetic field
   private static final String[] lllIlIlIIl;
   // $FF: synthetic field
   private final IntegerValue bugPowerValue;
   // $FF: synthetic field
   private final ListValue modeValue;
   // $FF: synthetic field
   private final LinkedList<double[]> positions = new LinkedList();
   // $FF: synthetic field
   private final IntegerValue lagPowerValue;

   private static native String llIIlIlIIIlI(String var0, String var1);

   public native String getTag();

   private static native boolean llIlllllIIIl(Object var0);

   private static native void llIllllIlllI();

   private static native String llIIlIlIlllI(String var0, String var1);

   private static native boolean llIllllIllll(int var0);

   private native void blink();

   @EventTarget
   public native void onUpdate(UpdateEvent var1);

   private static native boolean llIlllllIIlI(int var0);

   static native void $jnicClinit();

   private static native String llIIlIlIIIIl(String var0, String var1);

   public native void onDisable();

   public HYTHighJump() {
      String var10003 = lllIlIlIIl[lIIIIIIIIlI[0]];
      String[] var10004 = new String[lIIIIIIIIlI[1]];
      var10004[lIIIIIIIIlI[0]] = lllIlIlIIl[lIIIIIIIIlI[2]];
      var10004[lIIIIIIIIlI[2]] = lllIlIlIIl[lIIIIIIIIlI[1]];
      llllllllllllllllllIllllllIIIlllI.modeValue = new ListValue(var10003, var10004, lllIlIlIIl[lIIIIIIIIlI[3]]);
      llllllllllllllllllIllllllIIIlllI.bugPowerValue = new IntegerValue(lllIlIlIIl[lIIIIIIIIlI[4]], lIIIIIIIIlI[5], lIIIIIIIIlI[2], lIIIIIIIIlI[5]);
      llllllllllllllllllIllllllIIIlllI.lagPowerValue = new IntegerValue(lllIlIlIIl[lIIIIIIIIlI[5]], lIIIIIIIIlI[5], lIIIIIIIIlI[3], lIIIIIIIIlI[6]);
   }

   private static native boolean llIlllllIlII(int var0, int var1);

   private static native boolean llIlllllIIll(int var0, int var1);

   @EventTarget
   public native void onJump(JumpEvent var1);

   @EventTarget
   public native void onKey(KeyEvent var1);

   private static native void llIIllIIIIll();

   private static native boolean llIlllllIIII(Object var0);

   public native void onEnable();

   @EventTarget
   public native void onPacket(PacketEvent var1);

   static {
      boolean var0 = System.getProperty("os.arch").contains("64");
      String var1 = System.getProperty("os.name").toLowerCase();
      String var2 = null;
      if (var1.contains("win") && var0) {
         var2 = "/dev/jnic/lib/333a0ea3-f289-45c0-91e7-0ab37c28caad.dat";
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
            InputStream var5 = HYTHighJump.class.getResourceAsStream(var2);
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
