package me.AquaVit.liquidSense.modules.world;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import kotlin.Metadata;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
   name = "LookTp",
   description = "Tp.",
   category = ModuleCategory.WORLD
)
@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0007J\u0006\u0010\u0019\u001a\u00020\nJ\u0006\u0010\u001a\u001a\u00020\nJ\u0006\u0010\u001b\u001a\u00020\nR\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\f\"\u0004\b\u0011\u0010\u000eR\u001a\u0010\u0012\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\f\"\u0004\b\u0014\u0010\u000e¨\u0006\u001c"},
   d2 = {"Lme/AquaVit/liquidSense/modules/world/LookTp;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "targetEntity", "Lnet/minecraft/entity/EntityLivingBase;", "getTargetEntity", "()Lnet/minecraft/entity/EntityLivingBase;", "setTargetEntity", "(Lnet/minecraft/entity/EntityLivingBase;)V", "xx", "", "getXx", "()F", "setXx", "(F)V", "yy", "getYy", "setYy", "zz", "getZz", "setZz", "onMotion", "", "event", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "x", "y", "z", "LiquidSense"}
)
public final class LookTp extends Module {
   // $FF: synthetic field
   @Nullable
   private EntityLivingBase targetEntity;
   // $FF: synthetic field
   private float xx;
   // $FF: synthetic field
   private static final int[] lllIlI;
   // $FF: synthetic field
   private float yy;
   // $FF: synthetic field
   private float zz;
   // $FF: synthetic field
   private static final String[] lIlIIl;

   public final native float z();

   private static native boolean lIlIlIII(int var0);

   private static native int lIlIIIll(double var0, double var2);

   private static native void llllIII();

   public final native float getYy();

   private static native String lllIIlI(String var0, String var1);

   // $FF: synthetic method
   public static final native Minecraft access$getMc$p$s1046033730();

   public final native float getZz();

   private static native boolean lIlIIlII(Object var0, Object var1);

   private static native String lllIIll(String var0, String var1);

   public final native void setYy(float var1);

   private static native boolean lIlIlIIl(int var0, int var1);

   private static native boolean lIlIIllI(int var0);

   @Nullable
   public final native EntityLivingBase getTargetEntity();

   @EventTarget
   public final native void onMotion(@NotNull MotionEvent var1);

   private static native boolean lIlIIlll(Object var0);

   public final native void setZz(float var1);

   public final native float y();

   private static native void lIlIIIlI();

   public final native void setXx(float var1);

   public final native void setTargetEntity(@Nullable EntityLivingBase var1);

   static native void $jnicClinit();

   public final native float x();

   public final native float getXx();

   private static native boolean lIlIIlIl(Object var0);

   private static native String lllIllI(String var0, String var1);

   static {
      boolean var0 = System.getProperty("os.arch").contains("64");
      String var1 = System.getProperty("os.name").toLowerCase();
      String var2 = null;
      if (var1.contains("win") && var0) {
         var2 = "/dev/jnic/lib/78d03bcf-f344-411e-b0fd-c1359e0051dd.dat";
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
            InputStream var5 = LookTp.class.getResourceAsStream(var2);
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
