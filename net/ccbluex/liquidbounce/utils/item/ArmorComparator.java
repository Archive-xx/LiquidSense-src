//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.utils.item;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ArmorComparator implements Comparator<ArmorPiece> {
   // $FF: synthetic field
   private static final Enchantment[] DAMAGE_REDUCTION_ENCHANTMENTS;
   // $FF: synthetic field
   private static final Enchantment[] OTHER_ENCHANTMENTS;
   // $FF: synthetic field
   private static final float[] ENCHANTMENT_DAMAGE_REDUCTION_FACTOR;
   // $FF: synthetic field
   private static final float[] ENCHANTMENT_FACTORS;
   // $FF: synthetic field
   private static final float[] OTHER_ENCHANTMENT_FACTORS;

   private float getThresholdedEnchantmentDamageReduction(ItemStack lIIIlllIIIII) {
      int lIIIllIlllIl = 0.0F;

      for(int lIIIllIlllII = 0; lIIIllIlllII < DAMAGE_REDUCTION_ENCHANTMENTS.length; ++lIIIllIlllII) {
         lIIIllIlllIl += (float)ItemUtils.getEnchantment(lIIIlllIIIII, DAMAGE_REDUCTION_ENCHANTMENTS[lIIIllIlllII]) * ENCHANTMENT_FACTORS[lIIIllIlllII] * ENCHANTMENT_DAMAGE_REDUCTION_FACTOR[lIIIllIlllII];
      }

      return lIIIllIlllIl;
   }

   private float getDamageReduction(int lIIIlllIIlll, int lIIIlllIIllI) {
      return 1.0F - Math.min(20.0F, Math.max((float)lIIIlllIIlll / 5.0F, (float)lIIIlllIIlll - 1.0F / (2.0F + (float)lIIIlllIIllI / 4.0F))) / 25.0F;
   }

   private float getEnchantmentThreshold(ItemStack lIIIllIlIllI) {
      float lIIIllIlIlIl = 0.0F;

      for(int lIIIllIllIII = 0; lIIIllIllIII < OTHER_ENCHANTMENTS.length; ++lIIIllIllIII) {
         lIIIllIlIlIl += (float)ItemUtils.getEnchantment(lIIIllIlIllI, OTHER_ENCHANTMENTS[lIIIllIllIII]) * OTHER_ENCHANTMENT_FACTORS[lIIIllIllIII];
      }

      return lIIIllIlIlIl;
   }

   private float getThresholdedDamageReduction(ItemStack lIIIlllIlllI) {
      byte lIIIlllIllIl = (ItemArmor)lIIIlllIlllI.getItem();
      return lIIIlllIllll.getDamageReduction(lIIIlllIllIl.getArmorMaterial().getDamageReductionAmount(lIIIlllIllIl.armorType), 0) * (1.0F - lIIIlllIllll.getThresholdedEnchantmentDamageReduction(lIIIlllIlllI));
   }

   static {
      DAMAGE_REDUCTION_ENCHANTMENTS = new Enchantment[]{Enchantment.protection, Enchantment.projectileProtection, Enchantment.fireProtection, Enchantment.blastProtection};
      ENCHANTMENT_FACTORS = new float[]{1.5F, 0.4F, 0.39F, 0.38F};
      ENCHANTMENT_DAMAGE_REDUCTION_FACTOR = new float[]{0.04F, 0.08F, 0.15F, 0.08F};
      OTHER_ENCHANTMENTS = new Enchantment[]{Enchantment.featherFalling, Enchantment.thorns, Enchantment.respiration, Enchantment.aquaAffinity, Enchantment.unbreaking};
      OTHER_ENCHANTMENT_FACTORS = new float[]{3.0F, 1.0F, 0.1F, 0.05F, 0.01F};
   }

   public static double round(double lIIlIIIlIIll, int lIIlIIIlIIlI) {
      if (lIIlIIIlIIlI < 0) {
         throw new IllegalArgumentException();
      } else {
         BigDecimal lIIlIIIlIlII = BigDecimal.valueOf(lIIlIIIlIIll);
         lIIlIIIlIlII = lIIlIIIlIlII.setScale(lIIlIIIlIIlI, RoundingMode.HALF_UP);
         return lIIlIIIlIlII.doubleValue();
      }
   }

   public int compare(ArmorPiece lIIIllllllIl, ArmorPiece lIIIllllllII) {
      byte lIIIlllllIll = Double.compare(round((double)lIIIlllllllI.getThresholdedDamageReduction(lIIIllllllII.getItemStack()), 3), round((double)lIIIlllllllI.getThresholdedDamageReduction(lIIIllllllIl.getItemStack()), 3));
      if (lIIIlllllIll == 0) {
         double lIIIlllllIlI = Double.compare(round((double)lIIIlllllllI.getEnchantmentThreshold(lIIIllllllIl.getItemStack()), 3), round((double)lIIIlllllllI.getEnchantmentThreshold(lIIIllllllII.getItemStack()), 3));
         if (lIIIlllllIlI == 0) {
            int lIIIlllllIIl = Integer.compare(ItemUtils.getEnchantmentCount(lIIIllllllIl.getItemStack()), ItemUtils.getEnchantmentCount(lIIIllllllII.getItemStack()));
            if (lIIIlllllIIl != 0) {
               return lIIIlllllIIl;
            } else {
               ItemArmor lIIlIIIIIllI = (ItemArmor)lIIIllllllIl.getItemStack().getItem();
               float lIIIllllIlll = (ItemArmor)lIIIllllllII.getItemStack().getItem();
               short lIIIllllIllI = Integer.compare(lIIlIIIIIllI.getArmorMaterial().getDurability(lIIlIIIIIllI.armorType), lIIIllllIlll.getArmorMaterial().getDurability(lIIIllllIlll.armorType));
               return lIIIllllIllI != 0 ? lIIIllllIllI : Integer.compare(lIIlIIIIIllI.getArmorMaterial().getEnchantability(), lIIIllllIlll.getArmorMaterial().getEnchantability());
            }
         } else {
            return lIIIlllllIlI;
         }
      } else {
         return lIIIlllllIll;
      }
   }
}
