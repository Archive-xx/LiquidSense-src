//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.utils.item;

import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ArmorPiece {
   // $FF: synthetic field
   private int slot;
   // $FF: synthetic field
   private ItemStack itemStack;

   public ItemStack getItemStack() {
      return lIlllllIIIIlIl.itemStack;
   }

   public ArmorPiece(ItemStack lIlllllIIIllll, int lIlllllIIlIIIl) {
      lIlllllIIlIIll.itemStack = lIlllllIIIllll;
      lIlllllIIlIIll.slot = lIlllllIIlIIIl;
   }

   public int getArmorType() {
      return ((ItemArmor)lIlllllIIIlIll.itemStack.getItem()).armorType;
   }

   public int getSlot() {
      return lIlllllIIIlIII.slot;
   }
}
