package net.ccbluex.liquidbounce.features.module.modules.combat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import kotlin.Metadata;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
   name = "Velocity",
   description = "Allows you to modify the amount of knockback you take.",
   category = ModuleCategory.COMBAT
)
@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010!\u001a\u00020\"H\u0016J\u0010\u0010#\u001a\u00020\"2\u0006\u0010$\u001a\u00020%H\u0007J\u0010\u0010&\u001a\u00020\"2\u0006\u0010$\u001a\u00020'H\u0007J\u0010\u0010(\u001a\u00020\"2\u0006\u0010$\u001a\u00020)H\u0007R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u0006\"\u0004\b\u000f\u0010\bR\u000e\u0010\u0010\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0018\u001a\u00020\u00198VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001bR\u000e\u0010\u001c\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006*"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/Velocity;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "a", "", "getA", "()Z", "setA", "(Z)V", "aacPushXZReducerValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "aacPushYReducerValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "b", "getB", "setB", "horizontalValue", "hurt", "jump", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "reverse2StrengthValue", "reverseHurt", "reverseStrengthValue", "tag", "", "getTag", "()Ljava/lang/String;", "velocityInput", "velocityTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "velocityTimer2", "verticalValue", "onDisable", "", "onJump", "event", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidSense"}
)
public final class Velocity extends Module {
   // $FF: synthetic field
   private final FloatValue aacPushXZReducerValue;
   // $FF: synthetic field
   private static final String[] lllIlIlIII;
   // $FF: synthetic field
   private boolean reverseHurt;
   // $FF: synthetic field
   private static final int[] llllIllIII;
   // $FF: synthetic field
   private boolean jump;
   // $FF: synthetic field
   private boolean velocityInput;
   // $FF: synthetic field
   private final FloatValue reverseStrengthValue;
   // $FF: synthetic field
   private MSTimer velocityTimer;
   // $FF: synthetic field
   private final ListValue modeValue;
   // $FF: synthetic field
   private final FloatValue horizontalValue;
   // $FF: synthetic field
   private final FloatValue reverse2StrengthValue;
   // $FF: synthetic field
   private boolean b;
   // $FF: synthetic field
   private boolean a;
   // $FF: synthetic field
   private final FloatValue verticalValue;
   // $FF: synthetic field
   private boolean hurt;
   // $FF: synthetic field
   private final BoolValue aacPushYReducerValue;
   // $FF: synthetic field
   private MSTimer velocityTimer2;

   // $FF: synthetic method
   public static final native Minecraft access$getMc$p$s1046033730();

   public final native boolean getB();

   private static native void llIlIllIIllI();

   public final native boolean getA();

   private static native boolean llIlIlllIIII(int var0);

   private static native boolean llIlIllIlIIl(Object var0);

   private static native String llIIlIlIIIll(String var0, String var1);

   private static native String llIIlIlIlIlI(String var0, String var1);

   @EventTarget
   public final native void onUpdate(@NotNull UpdateEvent var1);

   @NotNull
   public native String getTag();

   private static native boolean llIlIllIllIl(int var0);

   private static native String llIIlIlIIIII(String var0, String var1);

   private static native boolean llIlIllIlllI(int var0);

   public native void onDisable();

   private static native boolean llIlIllIllll(Object var0);

   public final native void setA(boolean var1);

   @EventTarget
   public final native void onPacket(@NotNull PacketEvent var1);

   private static native int llIlIllIlIlI(double var0, double var2);

   private static native boolean llIllIIIIlII(int var0, int var1);

   public final native void setB(boolean var1);

   @EventTarget
   public final native void onJump(@NotNull JumpEvent var1);

   private static native void llIIlllIIlll();

   private static native boolean llIlIlllIlII(int var0, int var1);

   public Velocity() {
      lllllllllllllllllllIIlIIlIlIIIII.horizontalValue = new FloatValue(lllIlIlIII[llllIllIII[31]], 0.0F, 0.0F, 1.0F);
      lllllllllllllllllllIIlIIlIlIIIII.verticalValue = new FloatValue(lllIlIlIII[llllIllIII[32]], 0.0F, 0.0F, 1.0F);
      String var10003 = lllIlIlIII[llllIllIII[33]];
      String[] var10004 = new String[llllIllIII[9]];
      var10004[llllIllIII[0]] = lllIlIlIII[llllIllIII[34]];
      var10004[llllIllIII[1]] = lllIlIlIII[llllIllIII[35]];
      var10004[llllIllIII[2]] = lllIlIlIII[llllIllIII[36]];
      var10004[llllIllIII[3]] = lllIlIlIII[llllIllIII[37]];
      var10004[llllIllIII[4]] = lllIlIlIII[llllIllIII[38]];
      var10004[llllIllIII[5]] = lllIlIlIII[llllIllIII[39]];
      var10004[llllIllIII[6]] = lllIlIlIII[llllIllIII[40]];
      var10004[llllIllIII[7]] = lllIlIlIII[llllIllIII[41]];
      var10004[llllIllIII[8]] = lllIlIlIII[llllIllIII[42]];
      lllllllllllllllllllIIlIIlIlIIIII.modeValue = new ListValue(var10003, var10004, lllIlIlIII[llllIllIII[43]]);
      lllllllllllllllllllIIlIIlIlIIIII.reverseStrengthValue = new FloatValue(lllIlIlIII[llllIllIII[44]], 1.0F, 0.1F, 1.0F);
      lllllllllllllllllllIIlIIlIlIIIII.reverse2StrengthValue = new FloatValue(lllIlIlIII[llllIllIII[45]], 0.05F, 0.02F, 0.1F);
      lllllllllllllllllllIIlIIlIlIIIII.aacPushXZReducerValue = new FloatValue(lllIlIlIII[llllIllIII[46]], 2.0F, 1.0F, 3.0F);
      lllllllllllllllllllIIlIIlIlIIIII.aacPushYReducerValue = new BoolValue(lllIlIlIII[llllIllIII[47]], (boolean)llllIllIII[1]);
      lllllllllllllllllllIIlIIlIlIIIII.velocityTimer = new MSTimer();
      lllllllllllllllllllIIlIIlIlIIIII.velocityTimer2 = new MSTimer();
   }

   private static native int llIlIllIllII(float var0, float var1);

   private static native int llIlIllllIII(float var0, float var1);

   static native void $jnicClinit();

   private static native boolean llIlIlllIIIl(int var0, int var1);

   static {
      boolean var0 = System.getProperty("os.arch").contains("64");
      String var1 = System.getProperty("os.name").toLowerCase();
      String var2 = null;
      if (var1.contains("win") && var0) {
         var2 = "/dev/jnic/lib/3690b35c-9b62-4906-8d1a-fd0674ddfb71.dat";
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
            InputStream var5 = Velocity.class.getResourceAsStream(var2);
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
