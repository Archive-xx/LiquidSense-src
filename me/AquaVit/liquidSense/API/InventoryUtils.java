package me.AquaVit.liquidSense.API;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class InventoryUtils {
   // $FF: synthetic field
   public static Minecraft mc;
   // $FF: synthetic field
   private static final int[] lllII;

   private static native int lIIlIlll(float var0, float var1);

   private static native int lIIllIll(float var0, float var1);

   private static native void llllIll();

   public static native void updateInventory();

   public static native int getItemType(ItemStack var0);

   public static native int getSlotID(Item var0);

   public native boolean isBestHelmet(int var1);

   private static native boolean lIIIIllI(Object var0, Object var1);

   static native void $jnicClinit();

   public static native boolean hotbarHas(Item var0);

   private static native boolean lIIIlIll(int var0, int var1);

   public native void dropSlot(int var1);

   public native boolean isBestLeggings(int var1);

   private static native int lIIlIIlI(double var0, double var2);

   private static native boolean lIIlIlII(int var0, int var1);

   public static native int getArmorProt(ItemStack var0);

   private static native int lIIllIlI(float var0, float var1);

   public native boolean hasItemMoreTimes(int var1);

   private static native boolean lIIIIIlI(int var0);

   public static native boolean isBestSword(ItemStack var0, int var1);

   public static native ItemStack getStackInSlot(int var0);

   public native int getBestWeapon();

   public static native int getBestSwordSlotID(ItemStack var0, double var1);

   public native int getArmorProt(int var1);

   private static native boolean lllllll(int var0);

   private static native boolean lIIlIlIl(int var0);

   private static native boolean lIIIIIIl(int var0, int var1);

   private static native double getSwordDamage(ItemStack var0);

   private static native boolean llllllI(Object var0);

   public native boolean isBestBoots(int var1);

   private static native int lIIIlIIl(double var0, double var2);

   public static native boolean isBestArmorOfTypeInInv(ItemStack var0);

   public static native int getFirstItem(Item var0);

   private static native boolean lIIIIIII(int var0, int var1);

   private static native boolean lIIlllII(int var0);

   private static native boolean lllllIl(Object var0);

   public static native ItemStack getItemBySlotID(int var0);

   public native int getBestWeaponInHotbar();

   public native boolean isBestSword(int var1);

   private static native boolean lIIIIIll(Object var0, Object var1);

   private static native boolean lllllII(int var0, int var1);

   private static native boolean lIIIIlIl(int var0, int var1);

   public native boolean isBestChest(int var1);

   public static native boolean hotbarHas(Item var0, int var1);

   public static native float getItemDamage(ItemStack var0);

   static {
      boolean var0 = System.getProperty("os.arch").contains("64");
      String var1 = System.getProperty("os.name").toLowerCase();
      String var2 = null;
      if (var1.contains("win") && var0) {
         var2 = "/dev/jnic/lib/29a6879a-69d2-4f72-b1bc-05598e71d131.dat";
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
            InputStream var5 = InventoryUtils.class.getResourceAsStream(var2);
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
