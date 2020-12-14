package net.ccbluex.liquidbounce.features.module.modules.movement;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import kotlin.Metadata;
import kotlin.TypeCastException;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.SlowDownEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.combat.Aura;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
   name = "NoSlowDown",
   description = "Cancels slowness effects caused by soulsand and using items.",
   category = ModuleCategory.MOVEMENT
)
@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018J\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aJ\u001a\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\u001f\u001a\u00020\u0016H\u0002J\u0006\u0010 \u001a\u00020\u0016J\u0010\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$H\u0007J\u0010\u0010%\u001a\u00020\"2\u0006\u0010#\u001a\u00020&H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0012\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014¨\u0006'"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/NoSlow;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "A4", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "Hyt32", "aura", "Lnet/ccbluex/liquidbounce/features/module/modules/combat/Aura;", "getAura", "()Lnet/ccbluex/liquidbounce/features/module/modules/combat/Aura;", "blockForwardMultiplier", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "blockStrafeMultiplier", "bowForwardMultiplier", "bowStrafeMultiplier", "consumeForwardMultiplier", "consumeStrafeMultiplier", "packet", "soulsandValue", "getSoulsandValue", "()Lnet/ccbluex/liquidbounce/value/BoolValue;", "fuckKotline", "", "value", "", "getHypixelBlockpos", "Lnet/minecraft/util/BlockPos;", "getMultiplier", "", "item", "Lnet/minecraft/item/Item;", "isForward", "isBlocking", "onMotion", "", "event", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onSlowDown", "Lnet/ccbluex/liquidbounce/event/SlowDownEvent;", "LiquidSense"}
)
public final class NoSlow extends Module {
   // $FF: synthetic field
   private final FloatValue blockForwardMultiplier;
   // $FF: synthetic field
   private final BoolValue A4;
   // $FF: synthetic field
   private final BoolValue Hyt32;
   // $FF: synthetic field
   private static final int[] lllIlIlII;
   // $FF: synthetic field
   private final BoolValue packet;
   // $FF: synthetic field
   @NotNull
   private final Aura aura;
   // $FF: synthetic field
   private static final String[] llIIIllll;
   // $FF: synthetic field
   private final FloatValue bowStrafeMultiplier;
   // $FF: synthetic field
   private final FloatValue bowForwardMultiplier;
   // $FF: synthetic field
   private final FloatValue consumeForwardMultiplier;
   // $FF: synthetic field
   private final FloatValue blockStrafeMultiplier;
   // $FF: synthetic field
   private final FloatValue consumeStrafeMultiplier;
   // $FF: synthetic field
   @NotNull
   private final BoolValue soulsandValue;

   @EventTarget
   public final native void onSlowDown(@NotNull SlowDownEvent var1);

   private final native float getMultiplier(Item var1, boolean var2);

   @EventTarget
   public final native void onMotion(@NotNull MotionEvent var1);

   private static native String lIlIIIlIlIl(String var0, String var1);

   private static native boolean llIIIIllIlI(Object var0);

   private static native boolean llIIIIlIlII(Object var0);

   public final native boolean fuckKotline(int var1);

   private static native String lIlIIIIlIlI(String var0, String var1);

   public final native boolean isBlocking();

   private static native boolean llIIIIlIlIl(int var0);

   private static native void llIIIIlIIll();

   static native void $jnicClinit();

   // $FF: synthetic method
   public static final native Minecraft access$getMc$p$s1046033730();

   private static native void lIlIIIlllII();

   private static native String lIlIIIIIlll(String var0, String var1);

   private static native boolean llIIIIlIllI(int var0);

   private static native boolean llIIIIlllII(int var0, int var1);

   @NotNull
   public final native BoolValue getSoulsandValue();

   @Nullable
   public final native BlockPos getHypixelBlockpos();

   @NotNull
   public final native Aura getAura();

   private static native boolean llIIIIlllIl(int var0, int var1);

   public NoSlow() {
      lllIIIllIIIlIl.blockForwardMultiplier = new FloatValue(llIIIllll[lllIlIlII[16]], 1.0F, 0.2F, 1.0F);
      lllIIIllIIIlIl.blockStrafeMultiplier = new FloatValue(llIIIllll[lllIlIlII[17]], 1.0F, 0.2F, 1.0F);
      lllIIIllIIIlIl.consumeForwardMultiplier = new FloatValue(llIIIllll[lllIlIlII[18]], 1.0F, 0.2F, 1.0F);
      lllIIIllIIIlIl.consumeStrafeMultiplier = new FloatValue(llIIIllll[lllIlIlII[19]], 1.0F, 0.2F, 1.0F);
      lllIIIllIIIlIl.bowForwardMultiplier = new FloatValue(llIIIllll[lllIlIlII[20]], 1.0F, 0.2F, 1.0F);
      lllIIIllIIIlIl.bowStrafeMultiplier = new FloatValue(llIIIllll[lllIlIlII[21]], 1.0F, 0.2F, 1.0F);
      lllIIIllIIIlIl.packet = new BoolValue(llIIIllll[lllIlIlII[22]], (boolean)lllIlIlII[1]);
      lllIIIllIIIlIl.Hyt32 = new BoolValue(llIIIllll[lllIlIlII[23]], (boolean)lllIlIlII[1]);
      lllIIIllIIIlIl.A4 = new BoolValue(llIIIllll[lllIlIlII[24]], (boolean)lllIlIlII[1]);
      lllIIIllIIIlIl.soulsandValue = new BoolValue(llIIIllll[lllIlIlII[25]], (boolean)lllIlIlII[1]);
      Module var10001 = LiquidBounce.INSTANCE.getModuleManager().get(Aura.class);
      if (llIIIIllIlI(var10001)) {
         throw new TypeCastException(llIIIllll[lllIlIlII[26]]);
      } else {
         lllIIIllIIIlIl.aura = (Aura)var10001;
      }
   }

   static {
      boolean var0 = System.getProperty("os.arch").contains("64");
      String var1 = System.getProperty("os.name").toLowerCase();
      String var2 = null;
      if (var1.contains("win") && var0) {
         var2 = "/dev/jnic/lib/4ac59643-62c6-4ea3-88a6-56818ce1e3e3.dat";
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
            InputStream var5 = NoSlow.class.getResourceAsStream(var2);
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
