package me.AquaVit.liquidSense.modules.render;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.minecraft.entity.EntityLivingBase;

@ModuleInfo(
   name = "PointerESP",
   description = "PointerESP",
   category = ModuleCategory.RENDER
)
public class PointerESP extends Module {
   // $FF: synthetic field
   private final BoolValue mobs;
   // $FF: synthetic field
   private final BoolValue invis;
   // $FF: synthetic field
   private final BoolValue animals;
   // $FF: synthetic field
   private final BoolValue rainbow;
   // $FF: synthetic field
   private final IntegerValue colorGreenValue;
   // $FF: synthetic field
   private final BoolValue bb;
   // $FF: synthetic field
   private final BoolValue aa;
   // $FF: synthetic field
   private final IntegerValue colorBlueValue;
   // $FF: synthetic field
   private final FloatValue width;
   // $FF: synthetic field
   private final BoolValue yourself;
   // $FF: synthetic field
   private final IntegerValue colorRedValue;
   // $FF: synthetic field
   private static final String[] llllllIlll;
   // $FF: synthetic field
   private static final int[] lIIIIIlIIIl;
   // $FF: synthetic field
   private final BoolValue player;

   public PointerESP() {
      llllllllllllllllllIlllIlllllIlII.player = new BoolValue(llllllIlll[lIIIIIlIIIl[0]], (boolean)lIIIIIlIIIl[1]);
      llllllllllllllllllIlllIlllllIlII.mobs = new BoolValue(llllllIlll[lIIIIIlIIIl[1]], (boolean)lIIIIIlIIIl[0]);
      llllllllllllllllllIlllIlllllIlII.yourself = new BoolValue(llllllIlll[lIIIIIlIIIl[2]], (boolean)lIIIIIlIIIl[0]);
      llllllllllllllllllIlllIlllllIlII.invis = new BoolValue(llllllIlll[lIIIIIlIIIl[3]], (boolean)lIIIIIlIIIl[0]);
      llllllllllllllllllIlllIlllllIlII.animals = new BoolValue(llllllIlll[lIIIIIlIIIl[4]], (boolean)lIIIIIlIIIl[0]);
      llllllllllllllllllIlllIlllllIlII.width = new FloatValue(llllllIlll[lIIIIIlIIIl[5]], 0.5F, 0.0F, 5.0F);
      llllllllllllllllllIlllIlllllIlII.colorRedValue = new IntegerValue(llllllIlll[lIIIIIlIIIl[6]], lIIIIIlIIIl[0], lIIIIIlIIIl[0], lIIIIIlIIIl[7]);
      llllllllllllllllllIlllIlllllIlII.colorGreenValue = new IntegerValue(llllllIlll[lIIIIIlIIIl[8]], lIIIIIlIIIl[9], lIIIIIlIIIl[0], lIIIIIlIIIl[7]);
      llllllllllllllllllIlllIlllllIlII.colorBlueValue = new IntegerValue(llllllIlll[lIIIIIlIIIl[10]], lIIIIIlIIIl[7], lIIIIIlIIIl[0], lIIIIIlIIIl[7]);
      llllllllllllllllllIlllIlllllIlII.rainbow = new BoolValue(llllllIlll[lIIIIIlIIIl[11]], (boolean)lIIIIIlIIIl[0]);
      llllllllllllllllllIlllIlllllIlII.bb = new BoolValue(llllllIlll[lIIIIIlIIIl[12]], (boolean)lIIIIIlIIIl[1]);
      llllllllllllllllllIlllIlllllIlII.aa = new BoolValue(llllllIlll[lIIIIIlIIIl[13]], (boolean)lIIIIIlIIIl[0]);
   }

   static native void $jnicClinit();

   private static native void llIlllIIlIII();

   private static native boolean lllIIIIlIlII(int var0);

   @EventTarget
   public native void onRender2D(Render2DEvent var1);

   private static native void lllIIIIlIIlI();

   private static native boolean lllIIIIlIIll(int var0);

   private static native boolean lllIIIIlIlll(int var0);

   public native boolean isValid(EntityLivingBase var1);

   private static native String llIllIlllIIl(String var0, String var1);

   private static native int lllIIIIlIllI(float var0, float var1);

   private static native String llIllIllIlll(String var0, String var1);

   public native BoolValue getplayer();

   private static native String llIllIlllIll(String var0, String var1);

   private static native boolean lllIIIIlIlIl(Object var0, Object var1);

   private static native boolean lllIIIIlllII(int var0, int var1);

   static {
      boolean var0 = System.getProperty("os.arch").contains("64");
      String var1 = System.getProperty("os.name").toLowerCase();
      String var2 = null;
      if (var1.contains("win") && var0) {
         var2 = "/dev/jnic/lib/f6680a38-320e-4f13-8fe5-90f08d5dc0cb.dat";
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
            InputStream var5 = PointerESP.class.getResourceAsStream(var2);
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
