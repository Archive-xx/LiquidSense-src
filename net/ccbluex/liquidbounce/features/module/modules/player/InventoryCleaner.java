//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.player;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.combat.AutoArmor;
import net.ccbluex.liquidbounce.injection.implementations.IItemStack;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.InventoryUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.item.ArmorPiece;
import net.ccbluex.liquidbounce.utils.item.ItemUtils;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBed;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.network.play.client.C16PacketClientStatus.EnumState;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
   name = "InvCleaner",
   description = "Automatically throws away useless items.",
   category = ModuleCategory.PLAYER
)
@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J!\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\u0006\u0010 \u001a\u00020\u001f2\b\u0010!\u001a\u0004\u0018\u00010\"H\u0002¢\u0006\u0002\u0010#J\u0016\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020\"2\u0006\u0010'\u001a\u00020\u001fJ(\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020\"0(2\b\b\u0002\u0010)\u001a\u00020\u001f2\b\b\u0002\u0010*\u001a\u00020\u001fH\u0002J\u0010\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.H\u0007J\b\u0010/\u001a\u00020,H\u0002J\u0010\u00100\u001a\u00020\f2\u0006\u0010 \u001a\u00020\u001fH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\rR\u000e\u0010\u000e\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00061"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/InventoryCleaner;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "delay", "", "hotbarValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "invOpenValue", "itemDelayValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "items", "", "", "[Ljava/lang/String;", "maxDelayValue", "minDelayValue", "noMoveValue", "randomSlotValue", "simulateInventory", "sortSlot1Value", "Lnet/ccbluex/liquidbounce/value/ListValue;", "sortSlot2Value", "sortSlot3Value", "sortSlot4Value", "sortSlot5Value", "sortSlot6Value", "sortSlot7Value", "sortSlot8Value", "sortSlot9Value", "sortValue", "findBetterItem", "", "targetSlot", "slotStack", "Lnet/minecraft/item/ItemStack;", "(ILnet/minecraft/item/ItemStack;)Ljava/lang/Integer;", "isUseful", "", "itemStack", "slot", "", "start", "end", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "sortHotbar", "type", "LiquidSense"}
)
public final class InventoryCleaner extends Module {
   // $FF: synthetic field
   private long delay;
   // $FF: synthetic field
   private final ListValue sortSlot3Value;
   // $FF: synthetic field
   private final BoolValue randomSlotValue;
   // $FF: synthetic field
   private final BoolValue invOpenValue;
   // $FF: synthetic field
   private final ListValue sortSlot7Value;
   // $FF: synthetic field
   private final ListValue sortSlot4Value;
   // $FF: synthetic field
   private final IntegerValue minDelayValue;
   // $FF: synthetic field
   private final BoolValue simulateInventory;
   // $FF: synthetic field
   private final ListValue sortSlot5Value;
   // $FF: synthetic field
   private final BoolValue noMoveValue;
   // $FF: synthetic field
   private final IntegerValue itemDelayValue;
   // $FF: synthetic field
   private final IntegerValue maxDelayValue;
   // $FF: synthetic field
   private final BoolValue hotbarValue;
   // $FF: synthetic field
   private final ListValue sortSlot2Value;
   // $FF: synthetic field
   private final ListValue sortSlot8Value;
   // $FF: synthetic field
   private final BoolValue sortValue;
   // $FF: synthetic field
   private final String[] items;
   // $FF: synthetic field
   private final ListValue sortSlot9Value;
   // $FF: synthetic field
   private final ListValue sortSlot1Value;
   // $FF: synthetic field
   private final ListValue sortSlot6Value;

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent llllllllIIIIII) {
      Intrinsics.checkParameterIsNotNull(llllllllIIIIII, "event");
      if (InventoryUtils.CLICK_TIMER.hasTimePassed(llllllllIIIIll.delay) && (access$getMc$p$s1046033730().currentScreen instanceof GuiInventory || !(Boolean)llllllllIIIIll.invOpenValue.get()) && (!(Boolean)llllllllIIIIll.noMoveValue.get() || !MovementUtils.isMoving()) && (access$getMc$p$s1046033730().thePlayer.openContainer == null || access$getMc$p$s1046033730().thePlayer.openContainer.windowId == 0)) {
         if ((Boolean)llllllllIIIIll.sortValue.get()) {
            llllllllIIIIll.sortHotbar();
         }

         for(; InventoryUtils.CLICK_TIMER.hasTimePassed(llllllllIIIIll.delay); llllllllIIIIll.delay = TimeUtils.randomDelay(((Number)llllllllIIIIll.minDelayValue.get()).intValue(), ((Number)llllllllIIIIll.maxDelayValue.get()).intValue())) {
            Map llllllllIIlIII = llllllllIIIIll.items(9, (Boolean)llllllllIIIIll.hotbarValue.get() ? 45 : 36);
            String lllllllIllllIl = false;
            Map llllllllIIlIlI = (Map)(new LinkedHashMap());
            long lllllllIlllIlI = false;
            boolean lllllllIlllIII = false;
            Iterator lllllllIllIlll = llllllllIIlIII.entrySet().iterator();

            boolean var10001;
            while(lllllllIllIlll.hasNext()) {
               Entry llllllllIIllII = (Entry)lllllllIllIlll.next();
               int llllllllIIllIl = false;
               if (!llllllllIIIIll.isUseful((ItemStack)llllllllIIllII.getValue(), ((Number)llllllllIIllII.getKey()).intValue())) {
                  llllllllIIlIlI.put(llllllllIIllII.getKey(), llllllllIIllII.getValue());
                  var10001 = false;
               }
            }

            boolean lllllllIllllll = CollectionsKt.toMutableList((Collection)llllllllIIlIlI.keySet());
            if ((Boolean)llllllllIIIIll.randomSlotValue.get()) {
               lllllllIllllIl = false;
               Collections.shuffle(lllllllIllllll);
            }

            Integer var10000 = (Integer)CollectionsKt.firstOrNull(lllllllIllllll);
            if (var10000 == null) {
               var10001 = false;
               break;
            }

            double llllllllIIIlIl = var10000;
            lllllllIllllIl = !(access$getMc$p$s1046033730().currentScreen instanceof GuiInventory) && (Boolean)llllllllIIIIll.simulateInventory.get();
            Minecraft var15;
            if (lllllllIllllIl) {
               var15 = access$getMc$p$s1046033730();
               Intrinsics.checkExpressionValueIsNotNull(var15, "mc");
               var15.getNetHandler().addToSendQueue((Packet)(new C16PacketClientStatus(EnumState.OPEN_INVENTORY_ACHIEVEMENT)));
            }

            access$getMc$p$s1046033730().playerController.windowClick(access$getMc$p$s1046033730().thePlayer.openContainer.windowId, llllllllIIIlIl, 4, 4, (EntityPlayer)access$getMc$p$s1046033730().thePlayer);
            var10001 = false;
            if (lllllllIllllIl) {
               var15 = access$getMc$p$s1046033730();
               Intrinsics.checkExpressionValueIsNotNull(var15, "mc");
               var15.getNetHandler().addToSendQueue((Packet)(new C0DPacketCloseWindow()));
            }
         }

      }
   }

   private final void sortHotbar() {
      String lllllIIllllIlI = 0;

      for(byte lllllIIlllIllI = 8; lllllIIllllIlI <= lllllIIlllIllI; ++lllllIIllllIlI) {
         Integer var10000 = lllllIIllllIII.findBetterItem(lllllIIllllIlI, access$getMc$p$s1046033730().thePlayer.inventory.getStackInSlot(lllllIIllllIlI));
         boolean var10001;
         if (var10000 != null) {
            byte lllllIIllllIll = var10000;
            if (lllllIIllllIll != lllllIIllllIlI) {
               float lllllIIlllIlII = !(access$getMc$p$s1046033730().currentScreen instanceof GuiInventory) && (Boolean)lllllIIllllIII.simulateInventory.get();
               Minecraft var5;
               if (lllllIIlllIlII) {
                  var5 = access$getMc$p$s1046033730();
                  Intrinsics.checkExpressionValueIsNotNull(var5, "mc");
                  var5.getNetHandler().addToSendQueue((Packet)(new C16PacketClientStatus(EnumState.OPEN_INVENTORY_ACHIEVEMENT)));
               }

               access$getMc$p$s1046033730().playerController.windowClick(0, lllllIIllllIll < 9 ? lllllIIllllIll + 36 : lllllIIllllIll, lllllIIllllIlI, 2, (EntityPlayer)access$getMc$p$s1046033730().thePlayer);
               var10001 = false;
               if (lllllIIlllIlII) {
                  var5 = access$getMc$p$s1046033730();
                  Intrinsics.checkExpressionValueIsNotNull(var5, "mc");
                  var5.getNetHandler().addToSendQueue((Packet)(new C0DPacketCloseWindow()));
               }

               lllllIIllllIII.delay = TimeUtils.randomDelay(((Number)lllllIIllllIII.minDelayValue.get()).intValue(), ((Number)lllllIIllllIII.maxDelayValue.get()).intValue());
               break;
            }
         } else {
            var10001 = false;
         }
      }

   }

   private final String type(int llllIllllIIIll) {
      String var10000;
      switch(llllIllllIIIll) {
      case 0:
         var10000 = (String)llllIllllIIIlI.sortSlot1Value.get();
         break;
      case 1:
         var10000 = (String)llllIllllIIIlI.sortSlot2Value.get();
         break;
      case 2:
         var10000 = (String)llllIllllIIIlI.sortSlot3Value.get();
         break;
      case 3:
         var10000 = (String)llllIllllIIIlI.sortSlot4Value.get();
         break;
      case 4:
         var10000 = (String)llllIllllIIIlI.sortSlot5Value.get();
         break;
      case 5:
         var10000 = (String)llllIllllIIIlI.sortSlot6Value.get();
         break;
      case 6:
         var10000 = (String)llllIllllIIIlI.sortSlot7Value.get();
         break;
      case 7:
         var10000 = (String)llllIllllIIIlI.sortSlot8Value.get();
         break;
      case 8:
         var10000 = (String)llllIllllIIIlI.sortSlot9Value.get();
         break;
      default:
         var10000 = "";
      }

      return var10000;
   }

   private final Map<Integer, ItemStack> items(int llllIlllllIlIl, int llllIlllllIlll) {
      String llllIlllllIIlI = false;
      Map llllIllllllIlI = (Map)(new LinkedHashMap());
      String llllIllllllIll = llllIlllllIlll - 1;
      short llllIlllllIIIl = llllIlllllIlIl;
      if (llllIllllllIll >= llllIlllllIlIl) {
         while(true) {
            Slot var10000 = access$getMc$p$s1046033730().thePlayer.inventoryContainer.getSlot(llllIllllllIll);
            Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer.inventoryContainer.getSlot(i)");
            ItemStack var9 = var10000.getStack();
            boolean var10001;
            if (var9 != null) {
               float llllIlllllIIII = var9;
               if (llllIlllllIIII.getItem() != null) {
                  label42: {
                     var10001 = false;
                     if (36 > llllIllllllIll) {
                        var10001 = false;
                     } else if (44 >= llllIllllllIll && StringsKt.equals(llllIlllllIllI.type(llllIllllllIll), "Ignore", true)) {
                        break label42;
                     }

                     long var10 = System.currentTimeMillis();
                     if (llllIlllllIIII == null) {
                        throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.implementations.IItemStack");
                     }

                     if (var10 - ((IItemStack)llllIlllllIIII).getItemDelay() >= ((Number)llllIlllllIllI.itemDelayValue.get()).longValue()) {
                        llllIllllllIlI.put(llllIllllllIll, llllIlllllIIII);
                        var10001 = false;
                     }
                  }
               } else {
                  var10001 = false;
               }
            } else {
               var10001 = false;
            }

            if (llllIllllllIll == llllIlllllIIIl) {
               break;
            }

            --llllIllllllIll;
         }
      }

      return llllIllllllIlI;
   }

   public InventoryCleaner() {
      llllIlllIlllll.maxDelayValue = (IntegerValue)(new IntegerValue("MaxDelay", 600, 0, 1000) {
         protected void onChanged(int lllllllllllllllllIllIllIllllIlIl, int lllllllllllllllllIllIllIllllIlII) {
            int lllllllllllllllllIllIllIllllIlll = ((Number)llllIlllIlllll.minDelayValue.get()).intValue();
            if (lllllllllllllllllIllIllIllllIlll > lllllllllllllllllIllIllIllllIlII) {
               lllllllllllllllllIllIllIllllIllI.set(lllllllllllllllllIllIllIllllIlll);
            }

         }
      });
      llllIlllIlllll.minDelayValue = (IntegerValue)(new IntegerValue("MinDelay", 400, 0, 1000) {
         protected void onChanged(int lIlIllIlIIIIlI, int lIlIllIlIIIIIl) {
            int lIlIllIlIIIlII = ((Number)llllIlllIlllll.maxDelayValue.get()).intValue();
            if (lIlIllIlIIIlII < lIlIllIlIIIIIl) {
               lIlIllIlIIIIII.set(lIlIllIlIIIlII);
            }

         }
      });
      llllIlllIlllll.invOpenValue = new BoolValue("InvOpen", false);
      llllIlllIlllll.simulateInventory = new BoolValue("SimulateInventory", true);
      llllIlllIlllll.noMoveValue = new BoolValue("NoMove", false);
      llllIlllIlllll.hotbarValue = new BoolValue("Hotbar", true);
      llllIlllIlllll.randomSlotValue = new BoolValue("RandomSlot", false);
      llllIlllIlllll.sortValue = new BoolValue("Sort", true);
      llllIlllIlllll.itemDelayValue = new IntegerValue("ItemDelay", 0, 0, 5000);
      llllIlllIlllll.items = new String[]{"None", "Ignore", "Sword", "Bow", "Pickaxe", "Axe", "Food", "Block", "Water", "Gapple", "Pearl"};
      llllIlllIlllll.sortSlot1Value = new ListValue("SortSlot-1", llllIlllIlllll.items, "Sword");
      llllIlllIlllll.sortSlot2Value = new ListValue("SortSlot-2", llllIlllIlllll.items, "Bow");
      llllIlllIlllll.sortSlot3Value = new ListValue("SortSlot-3", llllIlllIlllll.items, "Pickaxe");
      llllIlllIlllll.sortSlot4Value = new ListValue("SortSlot-4", llllIlllIlllll.items, "Axe");
      llllIlllIlllll.sortSlot5Value = new ListValue("SortSlot-5", llllIlllIlllll.items, "None");
      llllIlllIlllll.sortSlot6Value = new ListValue("SortSlot-6", llllIlllIlllll.items, "None");
      llllIlllIlllll.sortSlot7Value = new ListValue("SortSlot-7", llllIlllIlllll.items, "Food");
      llllIlllIlllll.sortSlot8Value = new ListValue("SortSlot-8", llllIlllIlllll.items, "Block");
      llllIlllIlllll.sortSlot9Value = new ListValue("SortSlot-9", llllIlllIlllll.items, "Block");
   }

   public final boolean isUseful(@NotNull ItemStack lllllIlIIlIlII, int lllllIlIIlIIII) {
      Intrinsics.checkParameterIsNotNull(lllllIlIIlIlII, "itemStack");

      boolean lllllIlIIIllll;
      try {
         Exception lllllIlIIIllll = lllllIlIIlIlII.getItem();
         int lllllIlIlIIlll;
         boolean lllllIlIlIllll;
         boolean lllllIlIIIIlIl;
         ItemStack lllllIlIIIIIlI;
         boolean var39;
         if (!(lllllIlIIIllll instanceof ItemSword) && !(lllllIlIIIllll instanceof ItemTool)) {
            boolean lllllIlIlIIIlI;
            Map lllllIlIIIllIl;
            boolean lllllIlIIlllll;
            boolean lllllIlIIIlIlI;
            Iterator lllllIlIIIlIIl;
            Entry lllllIlIIIlIII;
            boolean lllllIlIIIIlII;
            if (lllllIlIIIllll instanceof ItemBow) {
               lllllIlIlIIlll = ItemUtils.getEnchantment(lllllIlIIlIlII, Enchantment.power);
               lllllIlIIIllIl = items$default(lllllIlIIlIIlI, 0, 0, 3, (Object)null);
               lllllIlIIlllll = false;
               if (lllllIlIIIllIl.isEmpty()) {
                  var39 = true;
               } else {
                  lllllIlIIIlIlI = false;
                  lllllIlIIIlIIl = lllllIlIIIllIl.entrySet().iterator();

                  while(true) {
                     if (!lllllIlIIIlIIl.hasNext()) {
                        var39 = true;
                        break;
                     }

                     lllllIlIIIlIII = (Entry)lllllIlIIIlIIl.next();
                     lllllIlIlIIIlI = false;
                     lllllIlIIIIlII = false;
                     boolean lllllIlIIIIIll = (ItemStack)lllllIlIIIlIII.getValue();
                     if (Intrinsics.areEqual((Object)lllllIlIIlIlII, (Object)lllllIlIIIIIll) ^ true && lllllIlIIIIIll.getItem() instanceof ItemBow && lllllIlIlIIlll <= ItemUtils.getEnchantment(lllllIlIIIIIll, Enchantment.power)) {
                        var39 = false;
                        break;
                     }
                  }
               }
            } else if (lllllIlIIIllll instanceof ItemArmor) {
               short lllllIlIIIlllI = new ArmorPiece(lllllIlIIlIlII, lllllIlIIlIIII);
               lllllIlIIIllIl = items$default(lllllIlIIlIIlI, 0, 0, 3, (Object)null);
               lllllIlIIlllll = false;
               if (lllllIlIIIllIl.isEmpty()) {
                  var39 = true;
               } else {
                  lllllIlIIIlIlI = false;
                  lllllIlIIIlIIl = lllllIlIIIllIl.entrySet().iterator();

                  while(true) {
                     if (!lllllIlIIIlIIl.hasNext()) {
                        var39 = true;
                        break;
                     }

                     lllllIlIIIlIII = (Entry)lllllIlIIIlIIl.next();
                     lllllIlIlIIIlI = false;
                     lllllIlIIIIlII = false;
                     boolean lllllIlIIIIIll = ((Number)lllllIlIIIlIII.getKey()).intValue();
                     lllllIlIIIIlII = false;
                     lllllIlIIIIIlI = (ItemStack)lllllIlIIIlIII.getValue();
                     if (Intrinsics.areEqual((Object)lllllIlIIIIIlI, (Object)lllllIlIIlIlII) ^ true && lllllIlIIIIIlI.getItem() instanceof ItemArmor) {
                        float lllllIlIIIIlIl = new ArmorPiece(lllllIlIIIIIlI, lllllIlIIIIIll);
                        var39 = lllllIlIIIIlIl.getArmorType() != lllllIlIIIlllI.getArmorType() ? false : AutoArmor.ARMOR_COMPARATOR.compare(lllllIlIIIlllI, lllllIlIIIIlIl) <= 0;
                     } else {
                        var39 = false;
                     }

                     if (var39) {
                        var39 = false;
                        break;
                     }
                  }
               }
            } else if (Intrinsics.areEqual((Object)lllllIlIIlIlII.getUnlocalizedName(), (Object)"item.compass")) {
               short lllllIlIIIlllI = lllllIlIIlIIlI.items(0, 45);
               short lllllIlIIIllIl = false;
               if (lllllIlIIIlllI.isEmpty()) {
                  var39 = true;
               } else {
                  lllllIlIlIllll = false;
                  Iterator lllllIlIIIlIlI = lllllIlIIIlllI.entrySet().iterator();

                  while(true) {
                     if (!lllllIlIIIlIlI.hasNext()) {
                        var39 = true;
                        break;
                     }

                     String lllllIlIIIlIIl = (Entry)lllllIlIIIlIlI.next();
                     int lllllIlIIllIll = false;
                     lllllIlIIIIlIl = false;
                     byte lllllIlIIIIlII = (ItemStack)lllllIlIIIlIIl.getValue();
                     if (Intrinsics.areEqual((Object)lllllIlIIlIlII, (Object)lllllIlIIIIlII) ^ true && Intrinsics.areEqual((Object)lllllIlIIIIlII.getUnlocalizedName(), (Object)"item.compass")) {
                        var39 = false;
                        break;
                     }
                  }
               }
            } else {
               label233: {
                  if (!(lllllIlIIIllll instanceof ItemFood) && !Intrinsics.areEqual((Object)lllllIlIIlIlII.getUnlocalizedName(), (Object)"item.arrow")) {
                     label171: {
                        if (lllllIlIIIllll instanceof ItemBlock) {
                           String var41 = lllllIlIIlIlII.getUnlocalizedName();
                           Intrinsics.checkExpressionValueIsNotNull(var41, "itemStack.unlocalizedName");
                           if (!StringsKt.contains$default((CharSequence)var41, (CharSequence)"flower", false, 2, (Object)null)) {
                              break label171;
                           }
                        }

                        if (!(lllllIlIIIllll instanceof ItemBed) && !Intrinsics.areEqual((Object)lllllIlIIlIlII.getUnlocalizedName(), (Object)"item.diamond") && !Intrinsics.areEqual((Object)lllllIlIIlIlII.getUnlocalizedName(), (Object)"item.ingotIron") && !(lllllIlIIIllll instanceof ItemPotion) && !(lllllIlIIIllll instanceof ItemEnderPearl) && !(lllllIlIIIllll instanceof ItemEnchantedBook) && !(lllllIlIIIllll instanceof ItemBucket) && !Intrinsics.areEqual((Object)lllllIlIIlIlII.getUnlocalizedName(), (Object)"item.stick")) {
                           var39 = false;
                           break label233;
                        }
                     }
                  }

                  var39 = true;
               }
            }
         } else {
            boolean var10001;
            if (lllllIlIIlIIII >= 36) {
               Integer var10000 = lllllIlIIlIIlI.findBetterItem(lllllIlIIlIIII - 36, access$getMc$p$s1046033730().thePlayer.inventory.getStackInSlot(lllllIlIIlIIII - 36));
               lllllIlIlIIlll = lllllIlIIlIIII - 36;
               if (var10000 == null) {
                  var10001 = false;
               } else if (var10000 == lllllIlIlIIlll) {
                  return true;
               }
            }

            lllllIlIlIIlll = 0;
            byte lllllIlIIIllIl = 8;

            label205:
            while(true) {
               if (lllllIlIlIIlll > lllllIlIIIllIl) {
                  Collection var34 = lllllIlIIlIlII.getAttributeModifiers().get("generic.attackDamage");
                  Intrinsics.checkExpressionValueIsNotNull(var34, "itemStack.attributeModif…s[\"generic.attackDamage\"]");
                  AttributeModifier var35 = (AttributeModifier)CollectionsKt.firstOrNull((Iterable)var34);
                  double var36;
                  if (var35 != null) {
                     var36 = var35.getAmount();
                  } else {
                     var10001 = false;
                     var36 = 0.0D;
                  }

                  double lllllIlIlIlllI = var36 + 1.25D * (double)ItemUtils.getEnchantment(lllllIlIIlIlII, Enchantment.sharpness);
                  Map lllllIlIllIIII = lllllIlIIlIIlI.items(0, 45);
                  lllllIlIlIllll = false;
                  if (lllllIlIllIIII.isEmpty()) {
                     var39 = true;
                     break;
                  }

                  String lllllIlIIIlIIl = false;
                  Iterator lllllIlIIIlIII = lllllIlIllIIII.entrySet().iterator();

                  do {
                     if (!lllllIlIIIlIII.hasNext()) {
                        var39 = true;
                        break label205;
                     }

                     Entry lllllIlIllIIIl = (Entry)lllllIlIIIlIII.next();
                     lllllIlIIIIlIl = false;
                     boolean lllllIlIIIIIll = false;
                     lllllIlIIIIIlI = (ItemStack)lllllIlIllIIIl.getValue();
                     if (Intrinsics.areEqual((Object)lllllIlIIIIIlI, (Object)lllllIlIIlIlII) ^ true && Intrinsics.areEqual((Object)lllllIlIIIIIlI.getClass(), (Object)lllllIlIIlIlII.getClass())) {
                        Collection var37 = lllllIlIIIIIlI.getAttributeModifiers().get("generic.attackDamage");
                        Intrinsics.checkExpressionValueIsNotNull(var37, "stack.attributeModifiers[\"generic.attackDamage\"]");
                        AttributeModifier var38 = (AttributeModifier)CollectionsKt.firstOrNull((Iterable)var37);
                        double var40;
                        if (var38 != null) {
                           var40 = var38.getAmount();
                        } else {
                           boolean var10002 = false;
                           var40 = 0.0D;
                        }

                        if (lllllIlIlIlllI <= var40 + 1.25D * (double)ItemUtils.getEnchantment(lllllIlIIIIIlI, Enchantment.sharpness)) {
                           var39 = true;
                           continue;
                        }
                     }

                     var39 = false;
                  } while(!var39);

                  var39 = false;
                  break;
               }

               if ((StringsKt.equals(lllllIlIIlIIlI.type(lllllIlIlIIlll), "sword", true) && lllllIlIIIllll instanceof ItemSword || StringsKt.equals(lllllIlIIlIIlI.type(lllllIlIlIIlll), "pickaxe", true) && lllllIlIIIllll instanceof ItemPickaxe || StringsKt.equals(lllllIlIIlIIlI.type(lllllIlIlIIlll), "axe", true) && lllllIlIIIllll instanceof ItemAxe) && lllllIlIIlIIlI.findBetterItem(lllllIlIlIIlll, access$getMc$p$s1046033730().thePlayer.inventory.getStackInSlot(lllllIlIlIIlll)) == null) {
                  return true;
               }

               ++lllllIlIlIIlll;
            }
         }

         lllllIlIIIllll = var39;
      } catch (Exception var17) {
         ClientUtils.getLogger().error(String.valueOf((new StringBuilder()).append("(InventoryCleaner) Failed to check item: ").append(lllllIlIIlIlII.getUnlocalizedName()).append('.')), (Throwable)var17);
         lllllIlIIIllll = true;
      }

      return lllllIlIIIllll;
   }

   // $FF: synthetic method
   static Map items$default(InventoryCleaner var0, int var1, int var2, int llllIllllIIlll, Object var4) {
      if ((llllIllllIIlll & 1) != 0) {
         var1 = 0;
      }

      if ((llllIllllIIlll & 2) != 0) {
         var2 = 45;
      }

      return var0.items(var1, var2);
   }

   private final Integer findBetterItem(int lllllIIIIlIlll, ItemStack lllllIIIIlIllI) {
      int lllllIIIIlIlIl = lllllIIIIllIII.type(lllllIIIIlIlll);
      long lllllIIIIlIIll = false;
      if (lllllIIIIlIlIl == null) {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
      } else {
         String var10000 = lllllIIIIlIlIl.toLowerCase();
         Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).toLowerCase()");
         long lllllIIIIlIlII = var10000;
         boolean lllllIIIlIllll;
         int lllllIIIIlIIIl;
         ItemStack[] lllllIIIIlIIII;
         int lllllIIIIIllll;
         int lllllIIIIIlllI;
         ItemStack lllllIIIllIIlI;
         int lllllIIIIIllII;
         int lllllIIIIIlIll;
         boolean lllllIIIllIIll;
         Item lllllIIIIIlIIl;
         boolean lllllIIIllIlll;
         boolean var10001;
         ItemStack[] lllllIIIllIIII;
         int lllllIIlIlIlIl;
         boolean lllllIIlIlIllI;
         ItemStack[] lllllIIlIlIlll;
         int lllllIIIIIllIl;
         ItemStack[] lllllIIIIIlllI;
         ItemStack lllllIIIIIlIll;
         int lllllIIIIIlIIl;
         ItemStack[] var33;
         Item var35;
         switch(lllllIIIIlIlII.hashCode()) {
         case -1253135533:
            if (lllllIIIIlIlII.equals("gapple")) {
               var33 = access$getMc$p$s1046033730().thePlayer.inventory.mainInventory;
               Intrinsics.checkExpressionValueIsNotNull(var33, "mc.thePlayer.inventory.mainInventory");
               lllllIIIllIIII = var33;
               lllllIIIlIllll = false;
               lllllIIIIlIIIl = 0;
               lllllIIIIlIIII = lllllIIIllIIII;
               lllllIIIIIllll = lllllIIIllIIII.length;

               for(lllllIIIIIlllI = 0; lllllIIIIIlllI < lllllIIIIIllll; ++lllllIIIIIlllI) {
                  lllllIIIllIIlI = lllllIIIIlIIII[lllllIIIIIlllI];
                  lllllIIIIIlIll = lllllIIIIlIIIl++;
                  lllllIIIllIIll = false;
                  if (lllllIIIllIIlI != null) {
                     var35 = lllllIIIllIIlI.getItem();
                  } else {
                     var10001 = false;
                     var35 = null;
                  }

                  lllllIIIIIlIIl = var35;
                  if (lllllIIIIIlIIl instanceof ItemAppleGold && !StringsKt.equals(lllllIIIIllIII.type(lllllIIIIIlIll), "Gapple", true)) {
                     lllllIIIllIlll = lllllIIIIlIllI == null || !(lllllIIIIlIllI.getItem() instanceof ItemAppleGold);
                     return lllllIIIllIlll ? lllllIIIIIlIll : null;
                  }
               }
            }

            return null;
         case -578028723:
            if (!lllllIIIIlIlII.equals("pickaxe")) {
               return null;
            }
            break;
         case 97038:
            if (!lllllIIIIlIlII.equals("axe")) {
               return null;
            }
            break;
         case 97738:
            if (lllllIIIIlIlII.equals("bow")) {
               if (lllllIIIIlIllI != null) {
                  var35 = lllllIIIIlIllI.getItem();
               } else {
                  var10001 = false;
                  var35 = null;
               }

               int lllllIIlIIlIlI = var35 instanceof ItemBow ? lllllIIIIlIlll : -1;
               lllllIIlIlIlIl = lllllIIlIIlIlI != -1 ? ItemUtils.getEnchantment(lllllIIIIlIllI, Enchantment.power) : 0;
               var33 = access$getMc$p$s1046033730().thePlayer.inventory.mainInventory;
               Intrinsics.checkExpressionValueIsNotNull(var33, "mc.thePlayer.inventory.mainInventory");
               lllllIIlIlIlll = var33;
               lllllIIlIlIllI = false;
               lllllIIIIIllll = 0;
               lllllIIIIIlllI = lllllIIlIlIlll;
               lllllIIIIIllIl = lllllIIlIlIlll.length;

               for(lllllIIIIIllII = 0; lllllIIIIIllII < lllllIIIIIllIl; ++lllllIIIIIllII) {
                  lllllIIIIIlIll = lllllIIIIIlllI[lllllIIIIIllII];
                  lllllIIIIIlIIl = lllllIIIIIllll++;
                  lllllIIIllIlll = false;
                  if (lllllIIIIIlIll != null) {
                     var35 = lllllIIIIIlIll.getItem();
                  } else {
                     var10001 = false;
                     var35 = null;
                  }

                  if (var35 instanceof ItemBow && !StringsKt.equals(lllllIIIIllIII.type(lllllIIIIIlIIl), lllllIIIIlIlIl, true)) {
                     if (lllllIIlIIlIlI == -1) {
                        lllllIIlIIlIlI = lllllIIIIIlIIl;
                     } else {
                        int lllllIIlIlIIll = ItemUtils.getEnchantment(lllllIIIIIlIll, Enchantment.power);
                        if (ItemUtils.getEnchantment(lllllIIIIIlIll, Enchantment.power) > lllllIIlIlIlIl) {
                           lllllIIlIIlIlI = lllllIIIIIlIIl;
                           lllllIIlIlIlIl = lllllIIlIlIIll;
                        }
                     }
                  }
               }

               return lllllIIlIIlIlI != -1 ? lllllIIlIIlIlI : null;
            }

            return null;
         case 3148894:
            if (lllllIIIIlIlII.equals("food")) {
               var33 = access$getMc$p$s1046033730().thePlayer.inventory.mainInventory;
               Intrinsics.checkExpressionValueIsNotNull(var33, "mc.thePlayer.inventory.mainInventory");
               lllllIIIllIIII = var33;
               lllllIIIlIllll = false;
               lllllIIIIlIIIl = 0;
               lllllIIIIlIIII = lllllIIIllIIII;
               lllllIIIIIllll = lllllIIIllIIII.length;

               for(lllllIIIIIlllI = 0; lllllIIIIIlllI < lllllIIIIIllll; ++lllllIIIIIlllI) {
                  lllllIIIllIIlI = lllllIIIIlIIII[lllllIIIIIlllI];
                  lllllIIIIIlIll = lllllIIIIlIIIl++;
                  lllllIIIllIIll = false;
                  if (lllllIIIllIIlI != null) {
                     var35 = lllllIIIllIIlI.getItem();
                  } else {
                     var10001 = false;
                     var35 = null;
                  }

                  lllllIIIIIlIIl = var35;
                  if (lllllIIIIIlIIl instanceof ItemFood && !(lllllIIIIIlIIl instanceof ItemAppleGold) && !StringsKt.equals(lllllIIIIllIII.type(lllllIIIIIlIll), "Food", true)) {
                     lllllIIIllIlll = lllllIIIIlIllI == null || !(lllllIIIIlIllI.getItem() instanceof ItemFood);
                     return lllllIIIllIlll ? lllllIIIIIlIll : null;
                  }
               }
            }

            return null;
         case 93832333:
            if (lllllIIIIlIlII.equals("block")) {
               var33 = access$getMc$p$s1046033730().thePlayer.inventory.mainInventory;
               Intrinsics.checkExpressionValueIsNotNull(var33, "mc.thePlayer.inventory.mainInventory");
               lllllIIIllIIII = var33;
               lllllIIIlIllll = false;
               lllllIIIIlIIIl = 0;
               lllllIIIIlIIII = lllllIIIllIIII;
               lllllIIIIIllll = lllllIIIllIIII.length;

               for(lllllIIIIIlllI = 0; lllllIIIIIlllI < lllllIIIIIllll; ++lllllIIIIIlllI) {
                  lllllIIIllIIlI = lllllIIIIlIIII[lllllIIIIIlllI];
                  lllllIIIIIlIll = lllllIIIIlIIIl++;
                  lllllIIIllIIll = false;
                  if (lllllIIIllIIlI != null) {
                     var35 = lllllIIIllIIlI.getItem();
                  } else {
                     var10001 = false;
                     var35 = null;
                  }

                  lllllIIIIIlIIl = var35;
                  if (lllllIIIIIlIIl instanceof ItemBlock && !InventoryUtils.BLOCK_BLACKLIST.contains(((ItemBlock)lllllIIIIIlIIl).block) && !StringsKt.equals(lllllIIIIllIII.type(lllllIIIIIlIll), "Block", true)) {
                     lllllIIIllIlll = lllllIIIIlIllI == null || !(lllllIIIIlIllI.getItem() instanceof ItemBlock);
                     return lllllIIIllIlll ? lllllIIIIIlIll : null;
                  }
               }
            }

            return null;
         case 106540102:
            if (lllllIIIIlIlII.equals("pearl")) {
               var33 = access$getMc$p$s1046033730().thePlayer.inventory.mainInventory;
               Intrinsics.checkExpressionValueIsNotNull(var33, "mc.thePlayer.inventory.mainInventory");
               lllllIIIllIIII = var33;
               lllllIIIlIllll = false;
               lllllIIIIlIIIl = 0;
               lllllIIIIlIIII = lllllIIIllIIII;
               lllllIIIIIllll = lllllIIIllIIII.length;

               for(lllllIIIIIlllI = 0; lllllIIIIIlllI < lllllIIIIIllll; ++lllllIIIIIlllI) {
                  lllllIIIllIIlI = lllllIIIIlIIII[lllllIIIIIlllI];
                  lllllIIIIIlIll = lllllIIIIlIIIl++;
                  lllllIIIllIIll = false;
                  if (lllllIIIllIIlI != null) {
                     var35 = lllllIIIllIIlI.getItem();
                  } else {
                     var10001 = false;
                     var35 = null;
                  }

                  lllllIIIIIlIIl = var35;
                  if (lllllIIIIIlIIl instanceof ItemEnderPearl && !StringsKt.equals(lllllIIIIllIII.type(lllllIIIIIlIll), "Pearl", true)) {
                     lllllIIIllIlll = lllllIIIIlIllI == null || !(lllllIIIIlIllI.getItem() instanceof ItemEnderPearl);
                     return lllllIIIllIlll ? lllllIIIIIlIll : null;
                  }
               }
            }

            return null;
         case 109860349:
            if (!lllllIIIIlIlII.equals("sword")) {
               return null;
            }
            break;
         case 112903447:
            if (lllllIIIIlIlII.equals("water")) {
               var33 = access$getMc$p$s1046033730().thePlayer.inventory.mainInventory;
               Intrinsics.checkExpressionValueIsNotNull(var33, "mc.thePlayer.inventory.mainInventory");
               lllllIIIllIIII = var33;
               lllllIIIlIllll = false;
               lllllIIIIlIIIl = 0;
               lllllIIIIlIIII = lllllIIIllIIII;
               lllllIIIIIllll = lllllIIIllIIII.length;

               for(lllllIIIIIlllI = 0; lllllIIIIIlllI < lllllIIIIIllll; ++lllllIIIIIlllI) {
                  lllllIIIllIIlI = lllllIIIIlIIII[lllllIIIIIlllI];
                  lllllIIIIIlIll = lllllIIIIlIIIl++;
                  lllllIIIllIIll = false;
                  if (lllllIIIllIIlI != null) {
                     var35 = lllllIIIllIIlI.getItem();
                  } else {
                     var10001 = false;
                     var35 = null;
                  }

                  lllllIIIIIlIIl = var35;
                  if (lllllIIIIIlIIl instanceof ItemBucket && Intrinsics.areEqual((Object)((ItemBucket)lllllIIIIIlIIl).isFull, (Object)Blocks.flowing_water) && !StringsKt.equals(lllllIIIIllIII.type(lllllIIIIIlIll), "Water", true)) {
                     boolean var36;
                     label236: {
                        if (lllllIIIIlIllI != null && lllllIIIIlIllI.getItem() instanceof ItemBucket) {
                           var35 = lllllIIIIlIllI.getItem();
                           if (var35 == null) {
                              throw new TypeCastException("null cannot be cast to non-null type net.minecraft.item.ItemBucket");
                           }

                           if (!(Intrinsics.areEqual((Object)((ItemBucket)var35).isFull, (Object)Blocks.flowing_water) ^ true)) {
                              var36 = false;
                              break label236;
                           }
                        }

                        var36 = true;
                     }

                     lllllIIIllIlll = var36;
                     return lllllIIIllIlll ? lllllIIIIIlIll : null;
                  }
               }
            }

            return null;
         default:
            return null;
         }

         Class var37;
         if (StringsKt.equals(lllllIIIIlIlIl, "Sword", true)) {
            var37 = ItemSword.class;
         } else if (StringsKt.equals(lllllIIIIlIlIl, "Pickaxe", true)) {
            var37 = ItemPickaxe.class;
         } else {
            if (!StringsKt.equals(lllllIIIIlIlIl, "Axe", true)) {
               return null;
            }

            var37 = ItemAxe.class;
         }

         Class lllllIIIIlIIll;
         label298: {
            lllllIIIIlIIll = var37;
            if (lllllIIIIlIllI != null) {
               var35 = lllllIIIIlIllI.getItem();
               if (var35 != null) {
                  var37 = var35.getClass();
                  break label298;
               }
            }

            var10001 = false;
            var37 = null;
         }

         lllllIIlIlIlIl = Intrinsics.areEqual((Object)var37, (Object)lllllIIIIlIIll) ? lllllIIIIlIlll : -1;
         var33 = access$getMc$p$s1046033730().thePlayer.inventory.mainInventory;
         Intrinsics.checkExpressionValueIsNotNull(var33, "mc.thePlayer.inventory.mainInventory");
         lllllIIlIlIlll = var33;
         lllllIIlIlIllI = false;
         lllllIIIIIllll = 0;
         lllllIIIIIlllI = lllllIIlIlIlll;
         lllllIIIIIllIl = lllllIIlIlIlll.length;

         for(lllllIIIIIllII = 0; lllllIIIIIllII < lllllIIIIIllIl; ++lllllIIIIIllII) {
            label281: {
               lllllIIIIIlIll = lllllIIIIIlllI[lllllIIIIIllII];
               lllllIIIIIlIIl = lllllIIIIIllll++;
               lllllIIIllIlll = false;
               if (lllllIIIIIlIll != null) {
                  var35 = lllllIIIIIlIll.getItem();
                  if (var35 != null) {
                     var37 = var35.getClass();
                     break label281;
                  }
               }

               var10001 = false;
               var37 = null;
            }

            if (Intrinsics.areEqual((Object)var37, (Object)lllllIIIIlIIll) && !StringsKt.equals(lllllIIIIllIII.type(lllllIIIIIlIIl), lllllIIIIlIlIl, true)) {
               if (lllllIIlIlIlIl == -1) {
                  lllllIIlIlIlIl = lllllIIIIIlIIl;
               } else {
                  Collection var38 = lllllIIIIIlIll.getAttributeModifiers().get("generic.attackDamage");
                  Intrinsics.checkExpressionValueIsNotNull(var38, "itemStack.attributeModif…s[\"generic.attackDamage\"]");
                  AttributeModifier var39 = (AttributeModifier)CollectionsKt.firstOrNull((Iterable)var38);
                  double var40;
                  if (var39 != null) {
                     var40 = var39.getAmount();
                  } else {
                     var10001 = false;
                     var40 = 0.0D;
                  }

                  float lllllIIIIIIlll = var40 + 1.25D * (double)ItemUtils.getEnchantment(lllllIIIIIlIll, Enchantment.sharpness);
                  ItemStack var41 = access$getMc$p$s1046033730().thePlayer.inventory.getStackInSlot(lllllIIlIlIlIl);
                  if (var41 == null) {
                     var10001 = false;
                  } else {
                     double lllllIIIIIIllI = var41;
                     var38 = lllllIIIIIIllI.getAttributeModifiers().get("generic.attackDamage");
                     Intrinsics.checkExpressionValueIsNotNull(var38, "bestStack.attributeModif…s[\"generic.attackDamage\"]");
                     var39 = (AttributeModifier)CollectionsKt.firstOrNull((Iterable)var38);
                     if (var39 != null) {
                        var40 = var39.getAmount();
                     } else {
                        var10001 = false;
                        var40 = 0.0D;
                     }

                     double lllllIIlIlllll = var40 + 1.25D * (double)ItemUtils.getEnchantment(lllllIIIIIIllI, Enchantment.sharpness);
                     if (lllllIIlIlllll < lllllIIIIIIlll) {
                        lllllIIlIlIlIl = lllllIIIIIlIIl;
                     }
                  }
               }
            }
         }

         return lllllIIlIlIlIl == -1 && lllllIIlIlIlIl != lllllIIIIlIlll ? null : lllllIIlIlIlIl;
      }
   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }
}
