package me.AquaVit.liquidSense.modules.render;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import me.AquaVit.liquidSense.API.Particles;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.minecraft.entity.EntityLivingBase;

@ModuleInfo(
   name = "Particle",
   description = "Particle",
   category = ModuleCategory.RENDER
)
public class Particle extends Module {
   // $FF: synthetic field
   private static final String[] lIIIIlllIII;
   // $FF: synthetic field
   private HashMap<EntityLivingBase, Float> healthMap = new HashMap();
   // $FF: synthetic field
   private static final int[] lIIIlIIllII;
   // $FF: synthetic field
   private List<Particles> particles = new ArrayList();

   static native void $jnicClinit();

   private static native int lllIllIllIlI(float var0, float var1);

   private static native boolean lllIllIlllII(Object var0, Object var1);

   private static native boolean lllIllIllIll(Object var0);

   @EventTarget
   public native void onUpdate(UpdateEvent var1);

   @EventTarget
   public native void onRender(Render3DEvent var1);

   private static native boolean lllIlllIIlII(int var0, int var1);

   public static native void enableGL2D();

   private static native String lllIlIIllIlI(String var0, String var1);

   public static native void disableGL2D();

   @EventTarget
   public native void onLivingUpdate(UpdateEvent var1);

   private static native boolean lllIllIlllIl(int var0);

   private static native void lllIlIlIlIll();

   private static native boolean lllIllIllllI(int var0);

   private static native void lllIllIllIII();

   private static native boolean lllIllIlllll(int var0);

   private static native boolean lllIlllIIIll(int var0, int var1);

   private static native int lllIllIllIIl(float var0, float var1);

   public static native double roundToPlace(double var0, int var2);

   private native void lambda$onUpdate$0(Particles var1);

   private static native boolean lllIlllIIIlI(int var0, int var1);

   static {
      boolean var0 = System.getProperty("os.arch").contains("64");
      String var1 = System.getProperty("os.name").toLowerCase();
      String var2 = null;
      if (var1.contains("win") && var0) {
         var2 = "/dev/jnic/lib/2951cb57-9667-4281-9284-a907386e6fdf.dat";
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
            InputStream var5 = Particle.class.getResourceAsStream(var2);
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
