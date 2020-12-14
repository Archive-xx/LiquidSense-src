package net.ccbluex.liquidbounce.features.module.modules.player;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import me.AquaVit.liquidSense.modules.movement.Flight;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.movement.Fly;
import net.ccbluex.liquidbounce.utils.VecRotation;
import net.ccbluex.liquidbounce.utils.timer.TickTimer;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.block.Block;
import net.minecraft.network.Packet;
import net.minecraft.util.BlockPos;

@ModuleInfo(
   name = "NoFall",
   description = "Prevents you from taking fall damage.",
   category = ModuleCategory.PLAYER
)
public class NoFall extends Module {
   // $FF: synthetic field
   private int hypixel;
   // $FF: synthetic field
   private float lastFall;
   // $FF: synthetic field
   private final TickTimer mlgTimer;
   // $FF: synthetic field
   Flight flight;
   // $FF: synthetic field
   public final ListValue modeValue;
   // $FF: synthetic field
   private static final int[] lllIlIIlll;
   // $FF: synthetic field
   private boolean fakelag;
   // $FF: synthetic field
   private BlockPos currentMlgBlock;
   // $FF: synthetic field
   private boolean jumped;
   // $FF: synthetic field
   private VecRotation currentMlgRotation;
   // $FF: synthetic field
   private List<Packet<?>> packets;
   // $FF: synthetic field
   Fly fly;
   // $FF: synthetic field
   private final TickTimer spartanTimer;
   // $FF: synthetic field
   private final FloatValue minFallDistance;
   // $FF: synthetic field
   private static final String[] lllIIlllll;
   // $FF: synthetic field
   private int state;
   // $FF: synthetic field
   public double fallDist;
   // $FF: synthetic field
   private int currentMlgItemIndex;
   // $FF: synthetic field
   private boolean packetmodify;

   private static native String llIIlIIIlIII(String var0, String var1);

   private static native boolean llIIlIlIllIl(int var0);

   private static native boolean llIIlIllIlIl(Object var0);

   private static native void llIIlIIlllll();

   private static native boolean llIIlIlIllll(int var0);

   private static native boolean llIIlIllllII(int var0, int var1);

   private static native String llIIlIIIIlIl(String var0, String var1);

   private static native int llIIlIlIIlll(double var0, double var2);

   private static native int llIIlIllIIlI(double var0, double var2);

   static native void $jnicClinit();

   private static native boolean llIIlIllIIII(int var0, int var1);

   public static native boolean isBlockUnder();

   private static native boolean llIIlIllIlII(Object var0, Object var1);

   public static native int getJumpEffect();

   public native String getTag();

   private static native void llIIlIlIIlII();

   @EventTarget(
      ignoreCondition = true
   )
   public native void onJump(JumpEvent var1);

   // $FF: synthetic method
   private static native boolean lambda$onUpdate$1(Block var0);

   // $FF: synthetic method
   private static native boolean lambda$onUpdate$0(Block var0);

   private static native int llIIlIlIIllI(double var0, double var2);

   public NoFall() {
      String var10003 = lllIIlllll[lllIlIIlll[0]];
      String[] var10004 = new String[lllIlIIlll[1]];
      var10004[lllIlIIlll[0]] = lllIIlllll[lllIlIIlll[2]];
      var10004[lllIlIIlll[2]] = lllIIlllll[lllIlIIlll[3]];
      var10004[lllIlIIlll[3]] = lllIIlllll[lllIlIIlll[4]];
      var10004[lllIlIIlll[4]] = lllIIlllll[lllIlIIlll[5]];
      var10004[lllIlIIlll[5]] = lllIIlllll[lllIlIIlll[6]];
      var10004[lllIlIIlll[6]] = lllIIlllll[lllIlIIlll[7]];
      var10004[lllIlIIlll[7]] = lllIIlllll[lllIlIIlll[8]];
      var10004[lllIlIIlll[8]] = lllIIlllll[lllIlIIlll[9]];
      var10004[lllIlIIlll[9]] = lllIIlllll[lllIlIIlll[10]];
      var10004[lllIlIIlll[10]] = lllIIlllll[lllIlIIlll[11]];
      var10004[lllIlIIlll[11]] = lllIIlllll[lllIlIIlll[12]];
      var10004[lllIlIIlll[12]] = lllIIlllll[lllIlIIlll[13]];
      var10004[lllIlIIlll[13]] = lllIIlllll[lllIlIIlll[1]];
      lllllllllllllllllllIllIlIlIllIIl.modeValue = new ListValue(var10003, var10004, lllIIlllll[lllIlIIlll[14]]);
      lllllllllllllllllllIllIlIlIllIIl.spartanTimer = new TickTimer();
      lllllllllllllllllllIllIlIlIllIIl.minFallDistance = new FloatValue(lllIIlllll[lllIlIIlll[15]], 5.0F, 2.0F, 50.0F);
      lllllllllllllllllllIllIlIlIllIIl.mlgTimer = new TickTimer();
      lllllllllllllllllllIllIlIlIllIIl.packets = new ArrayList();
      lllllllllllllllllllIllIlIlIllIIl.fakelag = (boolean)lllIlIIlll[0];
      lllllllllllllllllllIllIlIlIllIIl.fallDist = 0.0D;
      lllllllllllllllllllIllIlIlIllIIl.packetmodify = (boolean)lllIlIIlll[0];
      lllllllllllllllllllIllIlIlIllIIl.flight = (Flight)LiquidBounce.moduleManager.getModule(Flight.class);
      lllllllllllllllllllIllIlIlIllIIl.fly = (Fly)LiquidBounce.moduleManager.getModule(Fly.class);
   }

   private static native int llIIlIlIlIII(float var0, float var1);

   private static native int llIIlIllIIll(float var0, float var1);

   private static native int llIIlIlllllI(double var0, double var2);

   private static native boolean llIIlIlIIlIl(int var0);

   private static native boolean llIIlIllIlll(Object var0);

   private static native int llIIllIIIIII(double var0, double var2);

   @EventTarget
   public native void onMove(MoveEvent var1);

   public native boolean inVoid();

   private static native int llIIlIllllll(double var0, double var2);

   private static native int llIIlIlIlIIl(float var0, float var1);

   private static native int llIIlIlllIll(double var0, double var2);

   private static native boolean llIIlIlllIlI(int var0, int var1);

   public native boolean inAir(double var1, double var3);

   private static native boolean llIIlIllIllI(int var0, int var1);

   private static native boolean llIIlIlIllII(int var0);

   private static native String llIIlIIIllII(String var0, String var1);

   // $FF: synthetic method
   private static native boolean lambda$onMove$3(Block var0);

   private static native boolean llIIlIlllIII(Object var0, Object var1);

   // $FF: synthetic method
   private static native boolean lambda$onMove$2(Block var0);

   @EventTarget(
      ignoreCondition = true
   )
   public native void onUpdate(UpdateEvent var1);

   @EventTarget
   public native void onPacket(PacketEvent var1);

   @EventTarget
   private native void onMotionUpdate(MotionEvent var1);

   public native void onEnable();

   static {
      boolean var0 = System.getProperty("os.arch").contains("64");
      String var1 = System.getProperty("os.name").toLowerCase();
      String var2 = null;
      if (var1.contains("win") && var0) {
         var2 = "/dev/jnic/lib/38afacf6-f083-4008-996b-488746eccd23.dat";
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
            InputStream var5 = NoFall.class.getResourceAsStream(var2);
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
