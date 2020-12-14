package net.ccbluex.liquidbounce.features.module.modules.combat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import kotlin.Metadata;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0013\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010$\u001a\u00020!J\u0010\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(H\u0007J\b\u0010)\u001a\u00020&H\u0016J\u0010\u0010*\u001a\u00020&2\u0006\u0010'\u001a\u00020+H\u0007J\u0010\u0010,\u001a\u00020&2\u0006\u0010'\u001a\u00020-H\u0007R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0016\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\r\"\u0004\b\u0018\u0010\u000fR\u0011\u0010\u0019\u001a\u00020\u001a¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u001a\u0010\u001d\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\r\"\u0004\b\u001f\u0010\u000fR\u0016\u0010 \u001a\u0004\u0018\u00010!8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\"\u0010#¨\u0006."},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/Criticals;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "delayValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "getDelayValue", "()Lnet/ccbluex/liquidbounce/value/IntegerValue;", "setDelayValue", "(Lnet/ccbluex/liquidbounce/value/IntegerValue;)V", "hurtTimeValue", "hypixelOffsets", "", "getHypixelOffsets", "()[D", "setHypixelOffsets", "([D)V", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "getModeValue", "()Lnet/ccbluex/liquidbounce/value/ListValue;", "setModeValue", "(Lnet/ccbluex/liquidbounce/value/ListValue;)V", "motion", "getMotion", "setMotion", "msTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "getMsTimer", "()Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "offsets", "getOffsets", "setOffsets", "tag", "", "getTag", "()Ljava/lang/String;", "getSelectMode", "onAttack", "", "event", "Lnet/ccbluex/liquidbounce/event/AttackEvent;", "onEnable", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidSense"}
)
@ModuleInfo(
   name = "Criticals",
   description = "Automatically deals critical hits.",
   category = ModuleCategory.COMBAT
)
public final class Criticals extends Module {
   // $FF: synthetic field
   @NotNull
   private double[] motion;
   // $FF: synthetic field
   @NotNull
   private ListValue modeValue;
   // $FF: synthetic field
   @NotNull
   private IntegerValue delayValue;
   // $FF: synthetic field
   private final IntegerValue hurtTimeValue;
   // $FF: synthetic field
   @NotNull
   private double[] hypixelOffsets;
   // $FF: synthetic field
   private static final int[] llIlllIlIl;
   // $FF: synthetic field
   @NotNull
   private double[] offsets;
   // $FF: synthetic field
   @NotNull
   private final MSTimer msTimer;
   // $FF: synthetic field
   private static final String[] llIlIIIIlI;

   private static native boolean llIIIIIIlllI(int var0);

   @NotNull
   public final native double[] getHypixelOffsets();

   public final native void setModeValue(@NotNull ListValue var1);

   public Criticals() {
      String var10003 = llIlIIIIlI[llIlllIlIl[49]];
      String[] var10004 = new String[llIlllIlIl[13]];
      var10004[llIlllIlIl[0]] = llIlIIIIlI[llIlllIlIl[50]];
      var10004[llIlllIlIl[1]] = llIlIIIIlI[llIlllIlIl[51]];
      var10004[llIlllIlIl[2]] = llIlIIIIlI[llIlllIlIl[52]];
      var10004[llIlllIlIl[3]] = llIlIIIIlI[llIlllIlIl[53]];
      var10004[llIlllIlIl[4]] = llIlIIIIlI[llIlllIlIl[54]];
      var10004[llIlllIlIl[5]] = llIlIIIIlI[llIlllIlIl[55]];
      var10004[llIlllIlIl[6]] = llIlIIIIlI[llIlllIlIl[56]];
      var10004[llIlllIlIl[7]] = llIlIIIIlI[llIlllIlIl[57]];
      var10004[llIlllIlIl[8]] = llIlIIIIlI[llIlllIlIl[58]];
      var10004[llIlllIlIl[9]] = llIlIIIIlI[llIlllIlIl[59]];
      var10004[llIlllIlIl[10]] = llIlIIIIlI[llIlllIlIl[60]];
      var10004[llIlllIlIl[11]] = llIlIIIIlI[llIlllIlIl[61]];
      var10004[llIlllIlIl[12]] = llIlIIIIlI[llIlllIlIl[62]];
      llllllllllllllllllllIlIlIllllIIl.modeValue = new ListValue(var10003, var10004, llIlIIIIlI[llIlllIlIl[63]]);
      llllllllllllllllllllIlIlIllllIIl.delayValue = new IntegerValue(llIlIIIIlI[llIlllIlIl[64]], llIlllIlIl[0], llIlllIlIl[0], llIlllIlIl[65]);
      llllllllllllllllllllIlIlIllllIIl.hurtTimeValue = new IntegerValue(llIlIIIIlI[llIlllIlIl[66]], llIlllIlIl[10], llIlllIlIl[0], llIlllIlIl[10]);
      llllllllllllllllllllIlIlIllllIIl.msTimer = new MSTimer();
      double[] var10001 = new double[llIlllIlIl[4]];
      var10001[llIlllIlIl[0]] = 0.05000000074505806D;
      var10001[llIlllIlIl[1]] = 0.0015999999595806003D;
      var10001[llIlllIlIl[2]] = 0.029999999329447746D;
      var10001[llIlllIlIl[3]] = 0.0015999999595806D;
      llllllllllllllllllllIlIlIllllIIl.hypixelOffsets = var10001;
      var10001 = new double[llIlllIlIl[4]];
      var10001[llIlllIlIl[0]] = 0.051D;
      var10001[llIlllIlIl[1]] = 0.0D;
      var10001[llIlllIlIl[2]] = 0.0125D;
      var10001[llIlllIlIl[3]] = 0.0D;
      llllllllllllllllllllIlIlIllllIIl.offsets = var10001;
      var10001 = new double[llIlllIlIl[4]];
      var10001[llIlllIlIl[0]] = 0.01D;
      var10001[llIlllIlIl[1]] = 0.001D;
      var10001[llIlllIlIl[2]] = 0.08D;
      var10001[llIlllIlIl[3]] = 0.002D;
      llllllllllllllllllllIlIlIllllIIl.motion = var10001;
   }

   @EventTarget
   public final native void onAttack(@NotNull AttackEvent var1);

   @NotNull
   public final native MSTimer getMsTimer();

   @Nullable
   public native String getTag();

   @NotNull
   public final native String getSelectMode();

   public final native void setDelayValue(@NotNull IntegerValue var1);

   public final native void setHypixelOffsets(@NotNull double[] var1);

   @NotNull
   public final native double[] getOffsets();

   private static native int llIIIIIIllII(double var0, double var2);

   @NotNull
   public final native ListValue getModeValue();

   public native void onEnable();

   private static native boolean llIIIIIIllll(Object var0);

   public final native void setOffsets(@NotNull double[] var1);

   private static native boolean llIIIIIlIIII(int var0, int var1);

   private static native String lIllIlIlIIII(String var0, String var1);

   @NotNull
   public final native double[] getMotion();

   @EventTarget
   public final native void onPacket(@NotNull PacketEvent var1);

   public final native void setMotion(@NotNull double[] var1);

   static native void $jnicClinit();

   @EventTarget
   public final native void onUpdate(@NotNull UpdateEvent var1);

   private static native String lIllIlIIlllI(String var0, String var1);

   @NotNull
   public final native IntegerValue getDelayValue();

   // $FF: synthetic method
   public static final native Minecraft access$getMc$p$s1046033730();

   private static native boolean llIIIIIIlIll(int var0);

   private static native boolean llIIIIIlIIlI(int var0, int var1);

   private static native String lIllIlIIllll(String var0, String var1);

   private static native void lIllllIIlIII();

   private static native void llIIIIIIlIlI();

   static {
      boolean var0 = System.getProperty("os.arch").contains("64");
      String var1 = System.getProperty("os.name").toLowerCase();
      String var2 = null;
      if (var1.contains("win") && var0) {
         var2 = "/dev/jnic/lib/aabe880b-feca-4f10-9302-c49929b8d039.dat";
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
            InputStream var5 = Criticals.class.getResourceAsStream(var2);
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
