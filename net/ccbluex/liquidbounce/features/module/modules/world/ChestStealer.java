//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.world;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.player.InventoryCleaner;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S30PacketWindowItems;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
   name = "ChestStealer",
   description = "Automatically steals all items from a chest.",
   category = ModuleCategory.WORLD
)
@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u001b\u001a\u00020\u00102\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u0018\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u001d2\u0006\u0010!\u001a\u00020\"H\u0002J\u0010\u0010#\u001a\u00020\u001f2\u0006\u0010$\u001a\u00020%H\u0003J\u0012\u0010&\u001a\u00020\u001f2\b\u0010$\u001a\u0004\u0018\u00010'H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\u00020\u00108BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006("},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/world/ChestStealer;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "autoCloseMaxDelayValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "autoCloseMinDelayValue", "autoCloseTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "autoCloseValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "chestTitleValue", "closeOnFullValue", "contentReceived", "", "delayTimer", "fullInventory", "", "getFullInventory", "()Z", "maxDelayValue", "minDelayValue", "nextCloseDelay", "", "nextDelay", "noCompassValue", "onlyItemsValue", "takeRandomizedValue", "isEmpty", "chest", "Lnet/minecraft/client/gui/inventory/GuiChest;", "move", "", "screen", "slot", "Lnet/minecraft/inventory/Slot;", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onRender3D", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "LiquidSense"}
)
public final class ChestStealer extends Module {
   // $FF: synthetic field
   private final BoolValue autoCloseValue;
   // $FF: synthetic field
   private final BoolValue noCompassValue;
   // $FF: synthetic field
   private final IntegerValue autoCloseMinDelayValue;
   // $FF: synthetic field
   private long nextDelay;
   // $FF: synthetic field
   private final IntegerValue minDelayValue;
   // $FF: synthetic field
   private final IntegerValue maxDelayValue;
   // $FF: synthetic field
   private final MSTimer autoCloseTimer;
   // $FF: synthetic field
   private final BoolValue chestTitleValue;
   // $FF: synthetic field
   private int contentReceived;
   // $FF: synthetic field
   private long nextCloseDelay;
   // $FF: synthetic field
   private final IntegerValue autoCloseMaxDelayValue;
   // $FF: synthetic field
   private final BoolValue closeOnFullValue;
   // $FF: synthetic field
   private final BoolValue onlyItemsValue;
   // $FF: synthetic field
   private final BoolValue takeRandomizedValue;
   // $FF: synthetic field
   private final MSTimer delayTimer;

   // $FF: synthetic method
   public static final long access$getNextDelay$p(ChestStealer lllllllllllllllllllllIIlIlIllIll) {
      return lllllllllllllllllllllIIlIlIllIll.nextDelay;
   }

   public ChestStealer() {
      lllllllllllllllllllllIIlIllIIIIl.maxDelayValue = (IntegerValue)(new IntegerValue("MaxDelay", 200, 0, 400) {
         protected void onChanged(int llllllllllllIII, int lllllllllllIlll) {
            Exception lllllllllllIlII = ((Number)lllllllllllllllllllllIIlIllIIIIl.minDelayValue.get()).intValue();
            if (lllllllllllIlII > lllllllllllIlll) {
               llllllllllllIIl.set(lllllllllllIlII);
            }

            lllllllllllllllllllllIIlIllIIIIl.nextDelay = TimeUtils.randomDelay(((Number)lllllllllllllllllllllIIlIllIIIIl.minDelayValue.get()).intValue(), ((Number)llllllllllllIIl.get()).intValue());
         }
      });
      lllllllllllllllllllllIIlIllIIIIl.minDelayValue = (IntegerValue)(new IntegerValue("MinDelay", 150, 0, 400) {
         protected void onChanged(int lllllllllllllllllllIlIlIIlllIIII, int lllllllllllllllllllIlIlIIllIllIl) {
            byte lllllllllllllllllllIlIlIIllIllII = ((Number)lllllllllllllllllllllIIlIllIIIIl.maxDelayValue.get()).intValue();
            if (lllllllllllllllllllIlIlIIllIllII < lllllllllllllllllllIlIlIIllIllIl) {
               lllllllllllllllllllIlIlIIllIlllI.set(lllllllllllllllllllIlIlIIllIllII);
            }

            lllllllllllllllllllllIIlIllIIIIl.nextDelay = TimeUtils.randomDelay(((Number)lllllllllllllllllllIlIlIIllIlllI.get()).intValue(), ((Number)lllllllllllllllllllllIIlIllIIIIl.maxDelayValue.get()).intValue());
         }
      });
      lllllllllllllllllllllIIlIllIIIIl.takeRandomizedValue = new BoolValue("TakeRandomized", false);
      lllllllllllllllllllllIIlIllIIIIl.onlyItemsValue = new BoolValue("OnlyItems", false);
      lllllllllllllllllllllIIlIllIIIIl.noCompassValue = new BoolValue("NoCompass", false);
      lllllllllllllllllllllIIlIllIIIIl.autoCloseValue = new BoolValue("AutoClose", true);
      lllllllllllllllllllllIIlIllIIIIl.autoCloseMaxDelayValue = (IntegerValue)(new IntegerValue("AutoCloseMaxDelay", 0, 0, 400) {
         protected void onChanged(int lIIlIIIlIIlIlI, int lIIlIIIlIIIlll) {
            float lIIlIIIlIIIllI = ((Number)lllllllllllllllllllllIIlIllIIIIl.autoCloseMinDelayValue.get()).intValue();
            if (lIIlIIIlIIIllI > lIIlIIIlIIIlll) {
               lIIlIIIlIIlIll.set(lIIlIIIlIIIllI);
            }

            lllllllllllllllllllllIIlIllIIIIl.nextCloseDelay = TimeUtils.randomDelay(((Number)lllllllllllllllllllllIIlIllIIIIl.autoCloseMinDelayValue.get()).intValue(), ((Number)lIIlIIIlIIlIll.get()).intValue());
         }
      });
      lllllllllllllllllllllIIlIllIIIIl.autoCloseMinDelayValue = (IntegerValue)(new IntegerValue("AutoCloseMinDelay", 0, 0, 400) {
         protected void onChanged(int llllllllllllllllllIIIIlIlllllIlI, int llllllllllllllllllIIIIlIlllllIIl) {
            int llllllllllllllllllIIIIlIllllllII = ((Number)lllllllllllllllllllllIIlIllIIIIl.autoCloseMaxDelayValue.get()).intValue();
            if (llllllllllllllllllIIIIlIllllllII < llllllllllllllllllIIIIlIlllllIIl) {
               llllllllllllllllllIIIIlIlllllIll.set(llllllllllllllllllIIIIlIllllllII);
            }

            lllllllllllllllllllllIIlIllIIIIl.nextCloseDelay = TimeUtils.randomDelay(((Number)llllllllllllllllllIIIIlIlllllIll.get()).intValue(), ((Number)lllllllllllllllllllllIIlIllIIIIl.autoCloseMaxDelayValue.get()).intValue());
         }
      });
      lllllllllllllllllllllIIlIllIIIIl.closeOnFullValue = new BoolValue("CloseOnFull", true);
      lllllllllllllllllllllIIlIllIIIIl.chestTitleValue = new BoolValue("ChestTitle", false);
      lllllllllllllllllllllIIlIllIIIIl.delayTimer = new MSTimer();
      lllllllllllllllllllllIIlIllIIIIl.nextDelay = TimeUtils.randomDelay(((Number)lllllllllllllllllllllIIlIllIIIIl.minDelayValue.get()).intValue(), ((Number)lllllllllllllllllllllIIlIllIIIIl.maxDelayValue.get()).intValue());
      lllllllllllllllllllllIIlIllIIIIl.autoCloseTimer = new MSTimer();
      lllllllllllllllllllllIIlIllIIIIl.nextCloseDelay = TimeUtils.randomDelay(((Number)lllllllllllllllllllllIIlIllIIIIl.autoCloseMinDelayValue.get()).intValue(), ((Number)lllllllllllllllllllllIIlIllIIIIl.autoCloseMaxDelayValue.get()).intValue());
   }

   @EventTarget
   public final void onRender3D(@Nullable Render3DEvent lllllllllllllllllllllIIllIlIIlII) {
      GuiScreen lllllllllllllllllllllIIllIlIIllI = access$getMc$p$s1046033730().currentScreen;
      if (lllllllllllllllllllllIIllIlIIllI instanceof GuiChest && lllllllllllllllllllllIIllIlIIIll.delayTimer.hasTimePassed(lllllllllllllllllllllIIllIlIIIll.nextDelay)) {
         String var15;
         ItemStack var10000;
         boolean var10001;
         if ((Boolean)lllllllllllllllllllllIIllIlIIIll.noCompassValue.get()) {
            label123: {
               var10000 = access$getMc$p$s1046033730().thePlayer.inventory.getCurrentItem();
               if (var10000 != null) {
                  Item var14 = var10000.getItem();
                  if (var14 != null) {
                     var15 = var14.getUnlocalizedName();
                     break label123;
                  }
               }

               var10001 = false;
               var15 = null;
            }

            if (Intrinsics.areEqual((Object)var15, (Object)"item.compass")) {
               return;
            }
         }

         if ((Boolean)lllllllllllllllllllllIIllIlIIIll.chestTitleValue.get()) {
            if (((GuiChest)lllllllllllllllllllllIIllIlIIllI).lowerChestInventory == null) {
               return;
            }

            IInventory var17 = ((GuiChest)lllllllllllllllllllllIIllIlIIllI).lowerChestInventory;
            Intrinsics.checkExpressionValueIsNotNull(var17, "screen.lowerChestInventory");
            var15 = var17.getName();
            Intrinsics.checkExpressionValueIsNotNull(var15, "screen.lowerChestInventory.name");
            CharSequence var19 = (CharSequence)var15;
            String var16 = (new ItemStack((Item)Item.itemRegistry.getObject(new ResourceLocation("minecraft:chest")))).getDisplayName();
            Intrinsics.checkExpressionValueIsNotNull(var16, "ItemStack(Item.itemRegis…aft:chest\"))).displayName");
            if (!StringsKt.contains$default(var19, (CharSequence)var16, false, 2, (Object)null)) {
               return;
            }
         }

         Module var20 = LiquidBounce.INSTANCE.getModuleManager().get(InventoryCleaner.class);
         if (var20 == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.player.InventoryCleaner");
         } else {
            InventoryCleaner lllllllllllllllllllllIIllIlIIlll = (InventoryCleaner)var20;
            if (lllllllllllllllllllllIIllIlIIIll.isEmpty((GuiChest)lllllllllllllllllllllIIllIlIIllI) || (Boolean)lllllllllllllllllllllIIllIlIIIll.closeOnFullValue.get() && lllllllllllllllllllllIIllIlIIIll.getFullInventory()) {
               if ((Boolean)lllllllllllllllllllllIIllIlIIIll.autoCloseValue.get() && ((GuiChest)lllllllllllllllllllllIIllIlIIllI).inventorySlots.windowId == lllllllllllllllllllllIIllIlIIIll.contentReceived && lllllllllllllllllllllIIllIlIIIll.autoCloseTimer.hasTimePassed(lllllllllllllllllllllIIllIlIIIll.nextCloseDelay)) {
                  access$getMc$p$s1046033730().thePlayer.closeScreen();
                  lllllllllllllllllllllIIllIlIIIll.nextCloseDelay = TimeUtils.randomDelay(((Number)lllllllllllllllllllllIIllIlIIIll.autoCloseMinDelayValue.get()).intValue(), ((Number)lllllllllllllllllllllIIllIlIIIll.autoCloseMaxDelayValue.get()).intValue());
               }
            } else {
               lllllllllllllllllllllIIllIlIIIll.autoCloseTimer.reset();
               int lllllllllllllllllllllIIllIIlllll;
               Slot lllllllllllllllllllllIIllIIllllI;
               ItemStack var18;
               if ((Boolean)lllllllllllllllllllllIIllIlIIIll.takeRandomizedValue.get()) {
                  Collection lllllllllllllllllllllIIllIIlllIl;
                  do {
                     double lllllllllllllllllllllIIllIIlllll = false;
                     List lllllllllllllllllllllIIllIlIlIlI = (List)(new ArrayList());
                     lllllllllllllllllllllIIllIIlllll = 0;

                     for(int lllllllllllllllllllllIIllIIllllI = ((GuiChest)lllllllllllllllllllllIIllIlIIllI).inventoryRows * 9; lllllllllllllllllllllIIllIIlllll < lllllllllllllllllllllIIllIIllllI; ++lllllllllllllllllllllIIllIIlllll) {
                        long lllllllllllllllllllllIIllIIlllIl = (Slot)((GuiChest)lllllllllllllllllllllIIllIlIIllI).inventorySlots.inventorySlots.get(lllllllllllllllllllllIIllIIlllll);
                        Intrinsics.checkExpressionValueIsNotNull(lllllllllllllllllllllIIllIIlllIl, "slot");
                        if (lllllllllllllllllllllIIllIIlllIl.getStack() != null) {
                           if ((Boolean)lllllllllllllllllllllIIllIlIIIll.onlyItemsValue.get()) {
                              var10000 = lllllllllllllllllllllIIllIIlllIl.getStack();
                              Intrinsics.checkExpressionValueIsNotNull(var10000, "slot.stack");
                              if (var10000.getItem() instanceof ItemBlock) {
                                 continue;
                              }
                           }

                           if (lllllllllllllllllllllIIllIlIIlll.getState()) {
                              var18 = lllllllllllllllllllllIIllIIlllIl.getStack();
                              Intrinsics.checkExpressionValueIsNotNull(var18, "slot.stack");
                              if (!lllllllllllllllllllllIIllIlIIlll.isUseful(var18, -1)) {
                                 continue;
                              }
                           }

                           lllllllllllllllllllllIIllIlIlIlI.add(lllllllllllllllllllllIIllIIlllIl);
                           var10001 = false;
                        }
                     }

                     lllllllllllllllllllllIIllIIlllll = Random.Default.nextInt(lllllllllllllllllllllIIllIlIlIlI.size());
                     lllllllllllllllllllllIIllIIllllI = (Slot)lllllllllllllllllllllIIllIlIlIlI.get(lllllllllllllllllllllIIllIIlllll);
                     lllllllllllllllllllllIIllIlIIIll.move((GuiChest)lllllllllllllllllllllIIllIlIIllI, lllllllllllllllllllllIIllIIllllI);
                     if (!lllllllllllllllllllllIIllIlIIIll.delayTimer.hasTimePassed(lllllllllllllllllllllIIllIlIIIll.nextDelay)) {
                        break;
                     }

                     lllllllllllllllllllllIIllIIlllIl = (Collection)lllllllllllllllllllllIIllIlIlIlI;
                     double lllllllllllllllllllllIIllIIlllII = false;
                  } while(!lllllllllllllllllllllIIllIIlllIl.isEmpty());

                  return;
               }

               Exception lllllllllllllllllllllIIllIlIlIII = 0;

               for(lllllllllllllllllllllIIllIIlllll = ((GuiChest)lllllllllllllllllllllIIllIlIIllI).inventoryRows * 9; lllllllllllllllllllllIIllIlIlIII < lllllllllllllllllllllIIllIIlllll; ++lllllllllllllllllllllIIllIlIlIII) {
                  lllllllllllllllllllllIIllIIllllI = (Slot)((GuiChest)lllllllllllllllllllllIIllIlIIllI).inventorySlots.inventorySlots.get(lllllllllllllllllllllIIllIlIlIII);
                  if (lllllllllllllllllllllIIllIlIIIll.delayTimer.hasTimePassed(lllllllllllllllllllllIIllIlIIIll.nextDelay)) {
                     Intrinsics.checkExpressionValueIsNotNull(lllllllllllllllllllllIIllIIllllI, "slot");
                     if (lllllllllllllllllllllIIllIIllllI.getStack() != null) {
                        if ((Boolean)lllllllllllllllllllllIIllIlIIIll.onlyItemsValue.get()) {
                           var10000 = lllllllllllllllllllllIIllIIllllI.getStack();
                           Intrinsics.checkExpressionValueIsNotNull(var10000, "slot.stack");
                           if (var10000.getItem() instanceof ItemBlock) {
                              continue;
                           }
                        }

                        if (lllllllllllllllllllllIIllIlIIlll.getState()) {
                           var18 = lllllllllllllllllllllIIllIIllllI.getStack();
                           Intrinsics.checkExpressionValueIsNotNull(var18, "slot.stack");
                           if (!lllllllllllllllllllllIIllIlIIlll.isUseful(var18, -1)) {
                              continue;
                           }
                        }

                        lllllllllllllllllllllIIllIlIIIll.move((GuiChest)lllllllllllllllllllllIIllIlIIllI, lllllllllllllllllllllIIllIIllllI);
                     }
                  }
               }
            }

         }
      } else {
         lllllllllllllllllllllIIllIlIIIll.autoCloseTimer.reset();
      }
   }

   @EventTarget
   private final void onPacket(PacketEvent lllllllllllllllllllllIIllIIlIlII) {
      Packet lllllllllllllllllllllIIllIIllIII = lllllllllllllllllllllIIllIIlIlII.getPacket();
      if (lllllllllllllllllllllIIllIIllIII instanceof S30PacketWindowItems) {
         lllllllllllllllllllllIIllIIlIlIl.contentReceived = ((S30PacketWindowItems)lllllllllllllllllllllIIllIIllIII).func_148911_c();
      }

   }

   private final boolean getFullInventory() {
      ItemStack[] var10000 = access$getMc$p$s1046033730().thePlayer.inventory.mainInventory;
      Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer.inventory.mainInventory");
      int lllllllllllllllllllllIIlIllIlIlI = var10000;
      double lllllllllllllllllllllIIlIllIlIIl = false;
      boolean lllllllllllllllllllllIIlIllIlIII = lllllllllllllllllllllIIlIllIlIlI;
      byte lllllllllllllllllllllIIlIllIIlll = lllllllllllllllllllllIIlIllIlIlI.length;
      int lllllllllllllllllllllIIlIllIIllI = 0;

      boolean var9;
      while(true) {
         if (lllllllllllllllllllllIIlIllIIllI >= lllllllllllllllllllllIIlIllIIlll) {
            var9 = true;
            break;
         }

         Exception lllllllllllllllllllllIIlIllIIlIl = lllllllllllllllllllllIIlIllIlIII[lllllllllllllllllllllIIlIllIIllI];
         int lllllllllllllllllllllIIlIllIllll = false;
         if (lllllllllllllllllllllIIlIllIIlIl == null) {
            var9 = false;
            break;
         }

         ++lllllllllllllllllllllIIlIllIIllI;
      }

      return var9;
   }

   private final boolean isEmpty(GuiChest lllllllllllllllllllllIIlIlllllll) {
      Module var10000 = LiquidBounce.INSTANCE.getModuleManager().get(InventoryCleaner.class);
      if (var10000 == null) {
         throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.player.InventoryCleaner");
      } else {
         InventoryCleaner lllllllllllllllllllllIIllIIIIIIl = (InventoryCleaner)var10000;
         int lllllllllllllllllllllIIlIllllIll = 0;
         int lllllllllllllllllllllIIlIllllIlI = lllllllllllllllllllllIIlIlllllll.inventoryRows * 9;

         while(true) {
            if (lllllllllllllllllllllIIlIllllIll >= lllllllllllllllllllllIIlIllllIlI) {
               return true;
            }

            Slot lllllllllllllllllllllIIllIIIIIll = (Slot)lllllllllllllllllllllIIlIlllllll.inventorySlots.inventorySlots.get(lllllllllllllllllllllIIlIllllIll);
            Intrinsics.checkExpressionValueIsNotNull(lllllllllllllllllllllIIllIIIIIll, "slot");
            if (lllllllllllllllllllllIIllIIIIIll.getStack() != null) {
               label35: {
                  if ((Boolean)lllllllllllllllllllllIIllIIIIIII.onlyItemsValue.get()) {
                     ItemStack var6 = lllllllllllllllllllllIIllIIIIIll.getStack();
                     Intrinsics.checkExpressionValueIsNotNull(var6, "slot.stack");
                     if (var6.getItem() instanceof ItemBlock) {
                        break label35;
                     }
                  }

                  if (!lllllllllllllllllllllIIllIIIIIIl.getState()) {
                     break;
                  }

                  ItemStack var10001 = lllllllllllllllllllllIIllIIIIIll.getStack();
                  Intrinsics.checkExpressionValueIsNotNull(var10001, "slot.stack");
                  if (lllllllllllllllllllllIIllIIIIIIl.isUseful(var10001, -1)) {
                     break;
                  }
               }
            }

            ++lllllllllllllllllllllIIlIllllIll;
         }

         return false;
      }
   }

   private final void move(GuiChest lllllllllllllllllllllIIllIIIlllI, Slot lllllllllllllllllllllIIllIIIllIl) {
      lllllllllllllllllllllIIllIIIlllI.handleMouseClick(lllllllllllllllllllllIIllIIIllIl, lllllllllllllllllllllIIllIIIllIl.slotNumber, 0, 1);
      lllllllllllllllllllllIIllIIIllll.delayTimer.reset();
      lllllllllllllllllllllIIllIIIllll.nextDelay = TimeUtils.randomDelay(((Number)lllllllllllllllllllllIIllIIIllll.minDelayValue.get()).intValue(), ((Number)lllllllllllllllllllllIIllIIIllll.maxDelayValue.get()).intValue());
   }

   // $FF: synthetic method
   public static final long access$getNextCloseDelay$p(ChestStealer lllllllllllllllllllllIIlIlIIlIll) {
      return lllllllllllllllllllllIIlIlIIlIll.nextCloseDelay;
   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }
}
