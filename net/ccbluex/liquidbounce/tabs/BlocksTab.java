//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.tabs;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016J\b\u0010\b\u001a\u00020\tH\u0016J\b\u0010\n\u001a\u00020\u000bH\u0016J\b\u0010\f\u001a\u00020\rH\u0016¨\u0006\u000e"},
   d2 = {"Lnet/ccbluex/liquidbounce/tabs/BlocksTab;", "Lnet/minecraft/creativetab/CreativeTabs;", "()V", "displayAllReleventItems", "", "itemList", "", "Lnet/minecraft/item/ItemStack;", "getTabIconItem", "Lnet/minecraft/item/Item;", "getTranslatedTabLabel", "", "hasSearchBar", "", "LiquidSense"}
)
public final class BlocksTab extends CreativeTabs {
   @NotNull
   public Item getTabIconItem() {
      Item var10000 = (new ItemStack(Blocks.command_block)).getItem();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "ItemStack(Blocks.command_block).item");
      return var10000;
   }

   public BlocksTab() {
      super("Special blocks");
      llIIlIIlIIIIlI.setBackgroundImageName("item_search.png");
      boolean var10001 = false;
   }

   public void displayAllReleventItems(@NotNull List<ItemStack> llIIlIIlIIlIIl) {
      Intrinsics.checkParameterIsNotNull(llIIlIIlIIlIIl, "itemList");
      llIIlIIlIIlIIl.add(new ItemStack(Blocks.command_block));
      boolean var10001 = false;
      llIIlIIlIIlIIl.add(new ItemStack(Items.command_block_minecart));
      var10001 = false;
      llIIlIIlIIlIIl.add(new ItemStack(Blocks.barrier));
      var10001 = false;
      llIIlIIlIIlIIl.add(new ItemStack(Blocks.dragon_egg));
      var10001 = false;
      llIIlIIlIIlIIl.add(new ItemStack(Blocks.brown_mushroom_block));
      var10001 = false;
      llIIlIIlIIlIIl.add(new ItemStack(Blocks.red_mushroom_block));
      var10001 = false;
      llIIlIIlIIlIIl.add(new ItemStack(Blocks.farmland));
      var10001 = false;
      llIIlIIlIIlIIl.add(new ItemStack(Blocks.mob_spawner));
      var10001 = false;
      llIIlIIlIIlIIl.add(new ItemStack(Blocks.lit_furnace));
      var10001 = false;
   }

   @NotNull
   public String getTranslatedTabLabel() {
      return "Special blocks";
   }

   public boolean hasSearchBar() {
      return true;
   }
}
