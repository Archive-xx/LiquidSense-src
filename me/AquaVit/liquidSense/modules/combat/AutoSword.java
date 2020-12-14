package me.AquaVit.liquidSense.modules.combat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

@ModuleInfo(
   name = "AutoSword",
   description = "AutoSword",
   category = ModuleCategory.COMBAT
)
public class AutoSword extends Module {
   // $FF: synthetic field
   private boolean shouldSwitch;
   // $FF: synthetic field
   private ItemStack bestSword;
   // $FF: synthetic field
   private final TimeUtils timer;
   // $FF: synthetic field
   private static final int[] lIlllllIIl;
   // $FF: synthetic field
   private ItemStack prevBestSword;

   private native ItemStack getBestItem(Class<? extends Item> var1, Comparator var2);

   private native double getSwordDamage(ItemStack var1);

   private static native void lIlIIIllllII();

   private static native boolean lIlIIIllllll(Object var0);

   private static native boolean lIlIIlIIIIIl(Object var0);

   private static native int lIlIIIllllIl(double var0, double var2);

   private static native boolean lIlIIlIIIIlI(int var0);

   private static native boolean lIlIIlIIIIII(int var0);

   // $FF: synthetic method
   private static native boolean lambda$getBestItem$0(Class var0, ItemStack var1);

   @EventTarget
   private native void onUpdate(MotionEvent var1);

   private static native boolean lIlIIIlllllI(int var0);

   static native void $jnicClinit();

   public AutoSword() {
      lIIllIlIllII.shouldSwitch = (boolean)lIlllllIIl[0];
      lIIllIlIllII.timer = new TimeUtils();
   }

   static {
      boolean var0 = System.getProperty("os.arch").contains("64");
      String var1 = System.getProperty("os.name").toLowerCase();
      String var2 = null;
      if (var1.contains("win") && var0) {
         var2 = "/dev/jnic/lib/b4209f37-a2e7-4f6e-9da4-0261f2294d44.dat";
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
            InputStream var5 = AutoSword.class.getResourceAsStream(var2);
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
