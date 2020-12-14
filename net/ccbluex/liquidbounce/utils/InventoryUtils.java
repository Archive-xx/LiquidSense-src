//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.utils;

import java.util.Arrays;
import java.util.List;
import net.ccbluex.liquidbounce.event.ClickWindowEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;

public final class InventoryUtils extends MinecraftInstance implements Listenable {
   // $FF: synthetic field
   public static final List<Block> BLOCK_BLACKLIST;
   // $FF: synthetic field
   public static final MSTimer CLICK_TIMER = new MSTimer();

   public static boolean hasSpaceHotbar() {
      for(int lllllllllllllllllllllIlIIIIllIII = 36; lllllllllllllllllllllIlIIIIllIII < 45; ++lllllllllllllllllllllIlIIIIllIII) {
         ItemStack lllllllllllllllllllllIlIIIIllIlI = mc.thePlayer.inventoryContainer.getSlot(lllllllllllllllllllllIlIIIIllIII).getStack();
         if (lllllllllllllllllllllIlIIIIllIlI == null) {
            return true;
         }
      }

      return false;
   }

   @EventTarget
   public void onClick(ClickWindowEvent lllllllllllllllllllllIlIIIIIIlIl) {
      CLICK_TIMER.reset();
   }

   @EventTarget
   public void onPacket(PacketEvent lllllllllllllllllllllIlIIIIIIIIl) {
      Packet lllllllllllllllllllllIlIIIIIIIII = lllllllllllllllllllllIlIIIIIIIIl.getPacket();
      if (lllllllllllllllllllllIlIIIIIIIII instanceof C08PacketPlayerBlockPlacement) {
         CLICK_TIMER.reset();
      }

   }

   public static int findItem(int lllllllllllllllllllllIlIIIlIIlII, int lllllllllllllllllllllIlIIIlIIIll, Item lllllllllllllllllllllIlIIIlIIIlI) {
      for(int lllllllllllllllllllllIlIIIlIIlIl = lllllllllllllllllllllIlIIIlIIlII; lllllllllllllllllllllIlIIIlIIlIl < lllllllllllllllllllllIlIIIlIIIll; ++lllllllllllllllllllllIlIIIlIIlIl) {
         ItemStack lllllllllllllllllllllIlIIIlIIllI = mc.thePlayer.inventoryContainer.getSlot(lllllllllllllllllllllIlIIIlIIlIl).getStack();
         if (lllllllllllllllllllllIlIIIlIIllI != null && lllllllllllllllllllllIlIIIlIIllI.getItem() == lllllllllllllllllllllIlIIIlIIIlI) {
            return lllllllllllllllllllllIlIIIlIIlIl;
         }
      }

      return -1;
   }

   static {
      BLOCK_BLACKLIST = Arrays.asList(Blocks.enchanting_table, Blocks.chest, Blocks.ender_chest, Blocks.trapped_chest, Blocks.anvil, Blocks.sand, Blocks.web, Blocks.torch, Blocks.crafting_table, Blocks.furnace, Blocks.waterlily, Blocks.dispenser, Blocks.stone_pressure_plate, Blocks.wooden_pressure_plate, Blocks.noteblock, Blocks.dropper);
   }

   public static int findAutoBlockBlock() {
      int lllllllllllllllllllllIlIIIIIlIll;
      ItemStack lllllllllllllllllllllIlIIIIIlIIl;
      ItemBlock lllllllllllllllllllllIlIIIIIlIII;
      Block lllllllllllllllllllllIlIIIIIIlll;
      for(lllllllllllllllllllllIlIIIIIlIll = 36; lllllllllllllllllllllIlIIIIIlIll < 45; ++lllllllllllllllllllllIlIIIIIlIll) {
         lllllllllllllllllllllIlIIIIIlIIl = mc.thePlayer.inventoryContainer.getSlot(lllllllllllllllllllllIlIIIIIlIll).getStack();
         if (lllllllllllllllllllllIlIIIIIlIIl != null && lllllllllllllllllllllIlIIIIIlIIl.getItem() instanceof ItemBlock) {
            lllllllllllllllllllllIlIIIIIlIII = (ItemBlock)lllllllllllllllllllllIlIIIIIlIIl.getItem();
            lllllllllllllllllllllIlIIIIIIlll = lllllllllllllllllllllIlIIIIIlIII.getBlock();
            if (lllllllllllllllllllllIlIIIIIIlll.isFullCube() && !BLOCK_BLACKLIST.contains(lllllllllllllllllllllIlIIIIIIlll)) {
               return lllllllllllllllllllllIlIIIIIlIll;
            }
         }
      }

      for(lllllllllllllllllllllIlIIIIIlIll = 36; lllllllllllllllllllllIlIIIIIlIll < 45; ++lllllllllllllllllllllIlIIIIIlIll) {
         lllllllllllllllllllllIlIIIIIlIIl = mc.thePlayer.inventoryContainer.getSlot(lllllllllllllllllllllIlIIIIIlIll).getStack();
         if (lllllllllllllllllllllIlIIIIIlIIl != null && lllllllllllllllllllllIlIIIIIlIIl.getItem() instanceof ItemBlock) {
            lllllllllllllllllllllIlIIIIIlIII = (ItemBlock)lllllllllllllllllllllIlIIIIIlIIl.getItem();
            lllllllllllllllllllllIlIIIIIIlll = lllllllllllllllllllllIlIIIIIlIII.getBlock();
            if (!BLOCK_BLACKLIST.contains(lllllllllllllllllllllIlIIIIIIlll)) {
               return lllllllllllllllllllllIlIIIIIlIll;
            }
         }
      }

      return -1;
   }

   public boolean handleEvents() {
      return true;
   }
}
