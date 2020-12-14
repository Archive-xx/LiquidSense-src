package me.AquaVit.liquidSense.modules.render;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;

@ModuleInfo(
   name = "EnchantEffect",
   description = "EnchantEffect",
   category = ModuleCategory.RENDER
)
public class EnchantEffect extends Module {
   // $FF: synthetic field
   private final IntegerValue alphaValue;
   // $FF: synthetic field
   private static final int[] lIIlIllllIl;
   // $FF: synthetic field
   private static final String[] lIIIllllllI;
   // $FF: synthetic field
   private final IntegerValue colorRedValue;
   // $FF: synthetic field
   private final IntegerValue colorGreenValue;
   // $FF: synthetic field
   private final IntegerValue colorBlueValue;
   // $FF: synthetic field
   private final BoolValue rainbow;

   public native IntegerValue getGreenValue();

   private static native String lllllIIIllII(String var0, String var1);

   private static native String lllllIIIlIlI(String var0, String var1);

   private static native String lllllIIIlIll(String var0, String var1);

   private static native void lIIIIIlIIllll();

   public native IntegerValue getalphaValue();

   public EnchantEffect() {
      lllllllllllllllllIllllllllIIIIll.colorRedValue = new IntegerValue(lIIIllllllI[lIIlIllllIl[0]], lIIlIllllIl[0], lIIlIllllIl[0], lIIlIllllIl[1]);
      lllllllllllllllllIllllllllIIIIll.colorGreenValue = new IntegerValue(lIIIllllllI[lIIlIllllIl[2]], lIIlIllllIl[3], lIIlIllllIl[0], lIIlIllllIl[1]);
      lllllllllllllllllIllllllllIIIIll.colorBlueValue = new IntegerValue(lIIIllllllI[lIIlIllllIl[4]], lIIlIllllIl[1], lIIlIllllIl[0], lIIlIllllIl[1]);
      lllllllllllllllllIllllllllIIIIll.alphaValue = new IntegerValue(lIIIllllllI[lIIlIllllIl[5]], lIIlIllllIl[1], lIIlIllllIl[0], lIIlIllllIl[1]);
      lllllllllllllllllIllllllllIIIIll.rainbow = new BoolValue(lIIIllllllI[lIIlIllllIl[6]], (boolean)lIIlIllllIl[0]);
   }

   public native IntegerValue getBlueValue();

   public native IntegerValue getRedValue();

   public native BoolValue getRainbow();

   static native void $jnicClinit();

   private static native boolean lIIIIIlIlIIIl(int var0, int var1);

   private static native void lllllIIlIIlI();

   static {
      boolean var0 = System.getProperty("os.arch").contains("64");
      String var1 = System.getProperty("os.name").toLowerCase();
      String var2 = null;
      if (var1.contains("win") && var0) {
         var2 = "/dev/jnic/lib/dc9b828f-91a9-454f-8999-bbddef0165b6.dat";
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
            InputStream var5 = EnchantEffect.class.getResourceAsStream(var2);
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
