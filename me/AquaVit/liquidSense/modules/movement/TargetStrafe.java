//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package me.AquaVit.liquidSense.modules.movement;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import kotlin.Metadata;
import kotlin.TypeCastException;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.combat.Aura;
import net.ccbluex.liquidbounce.features.module.modules.movement.Fly;
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
   name = "TargetStrafe",
   description = "He died",
   category = ModuleCategory.MOVEMENT
)
@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010.\u001a\u00020/H\u0002J\b\u00100\u001a\u00020\u000eH\u0002J\u0018\u00101\u001a\u00020\u000e2\u0006\u00102\u001a\u00020\u00142\u0006\u00103\u001a\u00020\u0014H\u0002J\u0010\u00104\u001a\u00020/2\u0006\u00105\u001a\u000206H\u0007J\u000e\u00107\u001a\u00020/2\u0006\u00105\u001a\u000206J.\u00108\u001a\u00020/2\u0006\u00109\u001a\u0002062\u0006\u0010:\u001a\u00020\u00042\u0006\u0010;\u001a\u00020\n2\u0006\u0010<\u001a\u00020\n2\u0006\u0010=\u001a\u00020\u0004R\u0011\u0010\u0003\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\t\u001a\u00020\n8F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u000e8F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\n8F¢\u0006\u0006\u001a\u0004\b\u0012\u0010\fR\u001a\u0010\u0013\u001a\u00020\u0014X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u001d\u001a\u00020\u000e8F¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u0010R\u000e\u0010\u001f\u001a\u00020 X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010!\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u0006\"\u0004\b#\u0010$R\u000e\u0010%\u001a\u00020&X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010'\u001a\u00020\u0014¢\u0006\b\n\u0000\u001a\u0004\b(\u0010\u0016R\u000e\u0010)\u001a\u00020&X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020+X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020-X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006>"},
   d2 = {"Lme/AquaVit/liquidSense/modules/movement/TargetStrafe;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "Enemydistance", "", "getEnemydistance", "()D", "ThirdPersonView", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "algorithm", "", "getAlgorithm", "()F", "canStrafe", "", "getCanStrafe", "()Z", "cansize", "getCansize", "consts", "", "getConsts", "()I", "setConsts", "(I)V", "flight", "Lme/AquaVit/liquidSense/modules/movement/Flight;", "fly", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/Fly;", "keyMode", "getKeyMode", "killAura", "Lnet/ccbluex/liquidbounce/features/module/modules/combat/Aura;", "lastDist", "getLastDist", "setLastDist", "(D)V", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "oldPer", "getOldPer", "radiusMode", "radiusValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "speed", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/Speed;", "check", "", "checkVoid", "isVoid", "X", "Z", "movestrafe", "event", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onMove", "setSpeed", "moveEvent", "moveSpeed", "pseudoYaw", "pseudoStrafe", "pseudoForward", "LiquidSense"}
)
public final class TargetStrafe extends Module {
   // $FF: synthetic field
   private final BoolValue ThirdPersonView;
   // $FF: synthetic field
   private final FloatValue radiusValue;
   // $FF: synthetic field
   private int consts;
   // $FF: synthetic field
   private final ListValue modeValue;
   // $FF: synthetic field
   private static final int[] lllIllIII;
   // $FF: synthetic field
   private final Speed speed;
   // $FF: synthetic field
   private double lastDist;
   // $FF: synthetic field
   private final Flight flight;
   // $FF: synthetic field
   private final Aura killAura;
   // $FF: synthetic field
   private final ListValue radiusMode;
   // $FF: synthetic field
   private final int oldPer;
   // $FF: synthetic field
   private final Fly fly;
   // $FF: synthetic field
   private static final String[] llIllIIlI;

   private static native void lIllIIllIIl();

   private static native String lIlIlllllIl(String var0, String var1);

   public final native void setLastDist(double var1);

   private static native boolean llIIIllIlIl(Object var0);

   private final native boolean isVoid(int var1, int var2);

   public final native int getConsts();

   private static native boolean llIIIlllIll(int var0);

   static native void $jnicClinit();

   public final native void setSpeed(@NotNull MoveEvent var1, double var2, float var4, float var5, double var6);

   private static native boolean llIIIlIllll(int var0);

   private static native boolean llIIIllIIll(int var0, int var1);

   private static native boolean llIIIllllII(int var0);

   @EventTarget
   public final native void movestrafe(@NotNull MoveEvent var1);

   public final native void onMove(@NotNull MoveEvent var1);

   public final native double getEnemydistance();

   private static native void llIIIlIlllI();

   private static native boolean llIIIllIIIl(int var0, int var1);

   // $FF: synthetic method
   public static final native Minecraft access$getMc$p$s1046033730();

   public TargetStrafe() {
      llIllIlIllIIlI.ThirdPersonView = new BoolValue(llIllIIlI[lllIllIII[25]], (boolean)lllIllIII[0]);
      llIllIlIllIIlI.radiusValue = new FloatValue(llIllIIlI[lllIllIII[26]], 0.1F, 1.0F, 5.0F);
      String var10003 = llIllIIlI[lllIllIII[27]];
      String[] var10004 = new String[lllIllIII[4]];
      var10004[lllIllIII[0]] = llIllIIlI[lllIllIII[28]];
      var10004[lllIllIII[1]] = llIllIIlI[lllIllIII[29]];
      llIllIlIllIIlI.modeValue = new ListValue(var10003, var10004, llIllIIlI[lllIllIII[30]]);
      var10003 = llIllIIlI[lllIllIII[31]];
      var10004 = new String[lllIllIII[4]];
      var10004[lllIllIII[0]] = llIllIIlI[lllIllIII[32]];
      var10004[lllIllIII[1]] = llIllIIlI[lllIllIII[33]];
      llIllIlIllIIlI.radiusMode = new ListValue(var10003, var10004, llIllIIlI[lllIllIII[34]]);
      Module var10001 = LiquidBounce.INSTANCE.getModuleManager().getModule(Aura.class);
      if (llIIIllIlIl(var10001)) {
         throw new TypeCastException(llIllIIlI[lllIllIII[35]]);
      } else {
         llIllIlIllIIlI.killAura = (Aura)var10001;
         var10001 = LiquidBounce.INSTANCE.getModuleManager().getModule(Speed.class);
         if (llIIIllIlIl(var10001)) {
            throw new TypeCastException(llIllIIlI[lllIllIII[36]]);
         } else {
            llIllIlIllIIlI.speed = (Speed)var10001;
            var10001 = LiquidBounce.INSTANCE.getModuleManager().getModule(Fly.class);
            if (llIIIllIlIl(var10001)) {
               throw new TypeCastException(llIllIIlI[lllIllIII[37]]);
            } else {
               llIllIlIllIIlI.fly = (Fly)var10001;
               var10001 = LiquidBounce.INSTANCE.getModuleManager().getModule(Flight.class);
               if (llIIIllIlIl(var10001)) {
                  throw new TypeCastException(llIllIIlI[lllIllIII[38]]);
               } else {
                  llIllIlIllIIlI.flight = (Flight)var10001;
                  llIllIlIllIIlI.oldPer = access$getMc$p$s1046033730().gameSettings.thirdPersonView;
               }
            }
         }
      }
   }

   private final native boolean checkVoid();

   private static native boolean llIIIllIIlI(int var0, int var1);

   public final native boolean getCanStrafe();

   private static native boolean llIIIllllIl(int var0, int var1);

   private static native String lIllIIIIIII(String var0, String var1);

   private static native String lIllIIIIIll(String var0, String var1);

   public final native boolean getKeyMode();

   public final native int getOldPer();

   public final native double getLastDist();

   private static native boolean llIIIlllllI(int var0);

   public final native float getAlgorithm();

   private static native int llIIIlllIIl(double var0, double var2);

   private static native boolean llIIIllIlll(Object var0);

   private static native boolean llIIIllIIII(int var0);

   private static native int llIIIlllIlI(double var0, double var2);

   public final native void setConsts(int var1);

   public final native float getCansize();

   private static native int llIIIllIlII(float var0, float var1);

   private final native void check();

   private static native int llIIIllllll(double var0, double var2);

   static {
      boolean var0 = System.getProperty("os.arch").contains("64");
      String var1 = System.getProperty("os.name").toLowerCase();
      String var2 = null;
      if (var1.contains("win") && var0) {
         var2 = "/dev/jnic/lib/a21a4437-1f9a-4008-851c-c6a3fad54d53.dat";
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
            InputStream var5 = TargetStrafe.class.getResourceAsStream(var2);
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
