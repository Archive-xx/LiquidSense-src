package net.ccbluex.liquidbounce.script.api;

import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.utils.item.ItemUtils;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"},
   d2 = {"Lnet/ccbluex/liquidbounce/script/api/Item;", "", "()V", "createItem", "Lnet/minecraft/item/ItemStack;", "itemArguments", "", "LiquidSense"}
)
public final class Item {
   // $FF: synthetic field
   public static final Item INSTANCE;

   @JvmStatic
   @NotNull
   public static final ItemStack createItem(@NotNull String lllllllllllllllllIlllIlIllIIIllI) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllIlllIlIllIIIllI, "itemArguments");
      ItemStack var10000 = ItemUtils.createItem(lllllllllllllllllIlllIlIllIIIllI);
      Intrinsics.checkExpressionValueIsNotNull(var10000, "ItemUtils.createItem(itemArguments)");
      return var10000;
   }

   private Item() {
   }

   static {
      Item lllllllllllllllllIlllIlIllIIIIIl = new Item();
      INSTANCE = lllllllllllllllllIlllIlIllIIIIIl;
   }
}
