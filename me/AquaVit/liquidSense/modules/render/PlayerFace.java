package me.AquaVit.liquidSense.modules.render;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.entity.EntityLivingBase;

@ModuleInfo(
   name = "PlayerFace",
   description = "PlayerFace",
   category = ModuleCategory.RENDER
)
public class PlayerFace extends Module {
   // $FF: synthetic field
   private static final String[] lIIlIIll;
   // $FF: synthetic field
   private static final int[] llIIlllI;
   // $FF: synthetic field
   private final ListValue modeValue;

   private static native void llllIIlII();

   private static native boolean lIllIIlIII(int var0, int var1);

   private static native String llllIIIIl(String var0, String var1);

   static native void $jnicClinit();

   private static native int lIlIlIIIll(float var0, float var1);

   private static native boolean lIlIlIIlIl(int var0);

   private static native void lIlIlIIIlI();

   private static native String lllIlllIl(String var0, String var1);

   private static native boolean lIlIlIlIII(int var0);

   private static native String lllIllllI(String var0, String var1);

   @EventTarget
   public native void onRender(Render3DEvent var1);

   public PlayerFace() {
      String var10003 = lIIlIIll[llIIlllI[0]];
      String[] var10004 = new String[llIIlllI[1]];
      var10004[llIIlllI[0]] = lIIlIIll[llIIlllI[1]];
      llIIIlIIIIlIIll.modeValue = new ListValue(var10003, var10004, lIIlIIll[llIIlllI[2]]);
   }

   private static native boolean lIlIlIIllI(int var0);

   private native boolean isValid(EntityLivingBase var1);

   private static native boolean lIlIlIIlll(Object var0, Object var1);

   static {
      boolean var0 = System.getProperty("os.arch").contains("64");
      String var1 = System.getProperty("os.name").toLowerCase();
      String var2 = null;
      if (var1.contains("win") && var0) {
         var2 = "/dev/jnic/lib/56218d8a-d409-4fc5-8cad-7ecd833ea560.dat";
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
            InputStream var5 = PlayerFace.class.getResourceAsStream(var2);
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
