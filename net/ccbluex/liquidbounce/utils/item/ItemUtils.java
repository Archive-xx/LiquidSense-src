//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.utils.item;

import java.util.Objects;
import java.util.regex.Pattern;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public final class ItemUtils {
   public static int getEnchantment(ItemStack lllllllllllllllllllIIlIlllIIIIIl, Enchantment lllllllllllllllllllIIlIllIlllllI) {
      if (lllllllllllllllllllIIlIlllIIIIIl != null && lllllllllllllllllllIIlIlllIIIIIl.getEnchantmentTagList() != null && !lllllllllllllllllllIIlIlllIIIIIl.getEnchantmentTagList().hasNoTags()) {
         for(int lllllllllllllllllllIIlIllIllllIl = 0; lllllllllllllllllllIIlIllIllllIl < lllllllllllllllllllIIlIlllIIIIIl.getEnchantmentTagList().tagCount(); ++lllllllllllllllllllIIlIllIllllIl) {
            double lllllllllllllllllllIIlIllIllllII = lllllllllllllllllllIIlIlllIIIIIl.getEnchantmentTagList().getCompoundTagAt(lllllllllllllllllllIIlIllIllllIl);
            if (lllllllllllllllllllIIlIllIllllII.hasKey("ench") && lllllllllllllllllllIIlIllIllllII.getShort("ench") == lllllllllllllllllllIIlIllIlllllI.effectId || lllllllllllllllllllIIlIllIllllII.hasKey("id") && lllllllllllllllllllIIlIllIllllII.getShort("id") == lllllllllllllllllllIIlIllIlllllI.effectId) {
               return lllllllllllllllllllIIlIllIllllII.getShort("lvl");
            }
         }

         return 0;
      } else {
         return 0;
      }
   }

   public static ItemStack createItem(String lllllllllllllllllllIIlIlllIIllll) {
      try {
         lllllllllllllllllllIIlIlllIIllll = lllllllllllllllllllIIlIlllIIllll.replace('&', '§');
         int lllllllllllllllllllIIlIlllIIlllI = new Item();
         Exception lllllllllllllllllllIIlIlllIIllIl = null;
         String lllllllllllllllllllIIlIlllIIllII = 1;
         short lllllllllllllllllllIIlIlllIIlIll = 0;

         for(int lllllllllllllllllllIIlIlllIllIIl = 0; lllllllllllllllllllIIlIlllIllIIl <= Math.min(12, lllllllllllllllllllIIlIlllIIllll.length() - 2); ++lllllllllllllllllllIIlIlllIllIIl) {
            lllllllllllllllllllIIlIlllIIllIl = lllllllllllllllllllIIlIlllIIllll.substring(lllllllllllllllllllIIlIlllIllIIl).split(Pattern.quote(" "));
            double lllllllllllllllllllIIlIlllIIlIIl = new ResourceLocation(lllllllllllllllllllIIlIlllIIllIl[0]);
            lllllllllllllllllllIIlIlllIIlllI = (Item)Item.itemRegistry.getObject(lllllllllllllllllllIIlIlllIIlIIl);
            if (lllllllllllllllllllIIlIlllIIlllI != null) {
               break;
            }
         }

         if (lllllllllllllllllllIIlIlllIIlllI == null) {
            return null;
         } else {
            if (((String[])Objects.requireNonNull(lllllllllllllllllllIIlIlllIIllIl)).length >= 2 && lllllllllllllllllllIIlIlllIIllIl[1].matches("\\d+")) {
               lllllllllllllllllllIIlIlllIIllII = Integer.parseInt(lllllllllllllllllllIIlIlllIIllIl[1]);
            }

            if (lllllllllllllllllllIIlIlllIIllIl.length >= 3 && lllllllllllllllllllIIlIlllIIllIl[2].matches("\\d+")) {
               lllllllllllllllllllIIlIlllIIlIll = Integer.parseInt(lllllllllllllllllllIIlIlllIIllIl[2]);
            }

            byte lllllllllllllllllllIIlIlllIIlIlI = new ItemStack(lllllllllllllllllllIIlIlllIIlllI, lllllllllllllllllllIIlIlllIIllII, lllllllllllllllllllIIlIlllIIlIll);
            if (lllllllllllllllllllIIlIlllIIllIl.length >= 4) {
               double lllllllllllllllllllIIlIlllIIlIIl = new StringBuilder();

               for(int lllllllllllllllllllIIlIlllIIlIII = 3; lllllllllllllllllllIIlIlllIIlIII < lllllllllllllllllllIIlIlllIIllIl.length; ++lllllllllllllllllllIIlIlllIIlIII) {
                  lllllllllllllllllllIIlIlllIIlIIl.append(" ").append(lllllllllllllllllllIIlIlllIIllIl[lllllllllllllllllllIIlIlllIIlIII]);
                  boolean var10001 = false;
               }

               lllllllllllllllllllIIlIlllIIlIlI.setTagCompound(JsonToNBT.getTagFromJson(String.valueOf(lllllllllllllllllllIIlIlllIIlIIl)));
            }

            return lllllllllllllllllllIIlIlllIIlIlI;
         }
      } catch (Exception var8) {
         var8.printStackTrace();
         return null;
      }
   }

   public static int getEnchantmentCount(ItemStack lllllllllllllllllllIIlIllIllIIll) {
      if (lllllllllllllllllllIIlIllIllIIll != null && lllllllllllllllllllIIlIllIllIIll.getEnchantmentTagList() != null && !lllllllllllllllllllIIlIllIllIIll.getEnchantmentTagList().hasNoTags()) {
         short lllllllllllllllllllIIlIllIllIIlI = 0;

         for(int lllllllllllllllllllIIlIllIllIIIl = 0; lllllllllllllllllllIIlIllIllIIIl < lllllllllllllllllllIIlIllIllIIll.getEnchantmentTagList().tagCount(); ++lllllllllllllllllllIIlIllIllIIIl) {
            NBTTagCompound lllllllllllllllllllIIlIllIllIlll = lllllllllllllllllllIIlIllIllIIll.getEnchantmentTagList().getCompoundTagAt(lllllllllllllllllllIIlIllIllIIIl);
            if (lllllllllllllllllllIIlIllIllIlll.hasKey("ench") || lllllllllllllllllllIIlIllIllIlll.hasKey("id")) {
               ++lllllllllllllllllllIIlIllIllIIlI;
            }
         }

         return lllllllllllllllllllIIlIllIllIIlI;
      } else {
         return 0;
      }
   }
}
