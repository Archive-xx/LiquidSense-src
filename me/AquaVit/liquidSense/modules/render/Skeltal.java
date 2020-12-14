package me.AquaVit.liquidSense.modules.render;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.WeakHashMap;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateModelEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.minecraft.entity.player.EntityPlayer;

@ModuleInfo(
   name = "Skeltal",
   description = "Skeltal",
   category = ModuleCategory.RENDER
)
public class Skeltal extends Module {
   // $FF: synthetic field
   private final BoolValue smoothLines;
   // $FF: synthetic field
   private static final int[] lllIlIllI;
   // $FF: synthetic field
   private static final String[] lllIlIIlI;
   // $FF: synthetic field
   private final Map<EntityPlayer, float[][]> playerRotationMap = new WeakHashMap();

   private static native boolean llIIIlIlIIl(int var0);

   private native boolean contain(EntityPlayer var1);

   private static native boolean llIIIlIIlIl(int var0, int var1);

   private static native boolean llIIIlIlIII(int var0);

   private static native String llIIIIIIIll(String var0, String var1);

   private static native boolean llIIIlIlIlI(Object var0, Object var1);

   private static native int llIIIlIIIlI(float var0, float var1);

   private native void setupRender(boolean var1);

   public static native double interpolate(double var0, double var2, double var4);

   private static native void llIIIIIIlII();

   private static native boolean llIIIlIIlll(int var0, int var1);

   static native void $jnicClinit();

   private static native void llIIIIllllI();

   @EventTarget
   public final native void onModelUpdate(UpdateModelEvent var1);

   @EventTarget
   public native void onRender(Render3DEvent var1);

   public static native void startSmooth();

   public static native void endSmooth();

   public Skeltal() {
      lllIIIIllIIlII.smoothLines = new BoolValue(lllIlIIlI[lllIlIllI[0]], (boolean)lllIlIllI[1]);
   }

   private static native boolean llIIIlIIllI(Object var0);

   static {
      boolean var0 = System.getProperty("os.arch").contains("64");
      String var1 = System.getProperty("os.name").toLowerCase();
      String var2 = null;
      if (var1.contains("win") && var0) {
         var2 = "/dev/jnic/lib/37a48dbf-a516-441b-87f0-a65ba901a92a.dat";
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
            InputStream var5 = Skeltal.class.getResourceAsStream(var2);
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
