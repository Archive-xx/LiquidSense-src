//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.combat;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.injection.implementations.IItemStack;
import net.ccbluex.liquidbounce.utils.InventoryUtils;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.item.ArmorComparator;
import net.ccbluex.liquidbounce.utils.item.ArmorPiece;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.network.play.client.C16PacketClientStatus.EnumState;

@ModuleInfo(
   name = "AutoArmor",
   description = "Automatically equips the best armor in your inventory.",
   category = ModuleCategory.COMBAT
)
public class AutoArmor extends Module {
   // $FF: synthetic field
   private long delay;
   // $FF: synthetic field
   private final BoolValue invOpenValue;
   // $FF: synthetic field
   private final BoolValue noMoveValue;
   // $FF: synthetic field
   private final IntegerValue minDelayValue;
   // $FF: synthetic field
   public static final ArmorComparator ARMOR_COMPARATOR = new ArmorComparator();
   // $FF: synthetic field
   private final IntegerValue itemDelayValue;
   // $FF: synthetic field
   private final BoolValue simulateInventory;
   // $FF: synthetic field
   private final BoolValue hotbarValue;
   // $FF: synthetic field
   private final IntegerValue maxDelayValue;

   @EventTarget
   public void onRender3D(Render3DEvent lllllllllllllllllllIIlIlIlIlIIIl) {
      if (InventoryUtils.CLICK_TIMER.hasTimePassed(lllllllllllllllllllIIlIlIlIlIIlI.delay) && (mc.thePlayer.openContainer == null || mc.thePlayer.openContainer.windowId == 0)) {
         Map<Integer, List<ArmorPiece>> lllllllllllllllllllIIlIlIlIlIIII = (Map)IntStream.range(0, 36).filter((lllllllllllllllllllIIlIlIIllIIIl) -> {
            ItemStack lllllllllllllllllllIIlIlIIllIIll = mc.thePlayer.inventory.getStackInSlot(lllllllllllllllllllIIlIlIIllIIIl);
            return lllllllllllllllllllIIlIlIIllIIll != null && lllllllllllllllllllIIlIlIIllIIll.getItem() instanceof ItemArmor && (lllllllllllllllllllIIlIlIIllIIIl < 9 || System.currentTimeMillis() - ((IItemStack)lllllllllllllllllllIIlIlIIllIIll).getItemDelay() >= (long)(Integer)lllllllllllllllllllIIlIlIIllIIlI.itemDelayValue.get());
         }).mapToObj((lllllllllllllllllllIIlIlIIlllIIl) -> {
            return new ArmorPiece(mc.thePlayer.inventory.getStackInSlot(lllllllllllllllllllIIlIlIIlllIIl), lllllllllllllllllllIIlIlIIlllIIl);
         }).collect(Collectors.groupingBy(ArmorPiece::getArmorType));
         ArmorPiece[] lllllllllllllllllllIIlIlIlIIllll = new ArmorPiece[4];

         Entry lllllllllllllllllllIIlIlIlIIlIlI;
         for(Iterator lllllllllllllllllllIIlIlIlIIlIll = lllllllllllllllllllIIlIlIlIlIIII.entrySet().iterator(); lllllllllllllllllllIIlIlIlIIlIll.hasNext(); lllllllllllllllllllIIlIlIlIIllll[(Integer)lllllllllllllllllllIIlIlIlIIlIlI.getKey()] = (ArmorPiece)((List)lllllllllllllllllllIIlIlIlIIlIlI.getValue()).stream().max(ARMOR_COMPARATOR).orElse((Object)null)) {
            lllllllllllllllllllIIlIlIlIIlIlI = (Entry)lllllllllllllllllllIIlIlIlIIlIll.next();
         }

         for(int lllllllllllllllllllIIlIlIlIIlIll = 0; lllllllllllllllllllIIlIlIlIIlIll < 4; ++lllllllllllllllllllIIlIlIlIIlIll) {
            double lllllllllllllllllllIIlIlIlIIlIlI = lllllllllllllllllllIIlIlIlIIllll[lllllllllllllllllllIIlIlIlIIlIll];
            if (lllllllllllllllllllIIlIlIlIIlIlI != null) {
               int lllllllllllllllllllIIlIlIlIlIlIl = 3 - lllllllllllllllllllIIlIlIlIIlIll;
               ArmorPiece lllllllllllllllllllIIlIlIlIlIlII = new ArmorPiece(mc.thePlayer.inventory.armorItemInSlot(lllllllllllllllllllIIlIlIlIlIlIl), -1);
               if (lllllllllllllllllllIIlIlIlIlIlII.getItemStack() == null || !(lllllllllllllllllllIIlIlIlIlIlII.getItemStack().getItem() instanceof ItemArmor) || ARMOR_COMPARATOR.compare(lllllllllllllllllllIIlIlIlIlIlII, lllllllllllllllllllIIlIlIlIIlIlI) < 0) {
                  if (lllllllllllllllllllIIlIlIlIlIlII.getItemStack() != null && lllllllllllllllllllIIlIlIlIlIIlI.move(8 - lllllllllllllllllllIIlIlIlIlIlIl, true)) {
                     return;
                  }

                  if (lllllllllllllllllllIIlIlIlIlIIlI.move(lllllllllllllllllllIIlIlIlIIlIlI.getSlot(), false)) {
                     return;
                  }
               }
            }
         }

      }
   }

   private boolean move(int lllllllllllllllllllIIlIlIlIIIIIl, boolean lllllllllllllllllllIIlIlIlIIIIII) {
      if (!lllllllllllllllllllIIlIlIlIIIIII && lllllllllllllllllllIIlIlIlIIIIIl < 9 && (Boolean)lllllllllllllllllllIIlIlIIllllll.hotbarValue.get()) {
         mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(lllllllllllllllllllIIlIlIlIIIIIl));
         mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventoryContainer.getSlot(lllllllllllllllllllIIlIlIlIIIIIl).getStack()));
         mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
         lllllllllllllllllllIIlIlIIllllll.delay = TimeUtils.randomDelay((Integer)lllllllllllllllllllIIlIlIIllllll.minDelayValue.get(), (Integer)lllllllllllllllllllIIlIlIIllllll.maxDelayValue.get());
         return true;
      } else if ((!(Boolean)lllllllllllllllllllIIlIlIIllllll.noMoveValue.get() || !MovementUtils.isMoving()) && (!(Boolean)lllllllllllllllllllIIlIlIIllllll.invOpenValue.get() || mc.currentScreen instanceof GuiInventory) && lllllllllllllllllllIIlIlIlIIIIIl != -1) {
         boolean lllllllllllllllllllIIlIlIIllllII = (Boolean)lllllllllllllllllllIIlIlIIllllll.simulateInventory.get() && !(mc.currentScreen instanceof GuiInventory);
         if (lllllllllllllllllllIIlIlIIllllII) {
            mc.getNetHandler().addToSendQueue(new C16PacketClientStatus(EnumState.OPEN_INVENTORY_ACHIEVEMENT));
         }

         mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, lllllllllllllllllllIIlIlIlIIIIII ? lllllllllllllllllllIIlIlIlIIIIIl : (lllllllllllllllllllIIlIlIlIIIIIl < 9 ? lllllllllllllllllllIIlIlIlIIIIIl + 36 : lllllllllllllllllllIIlIlIlIIIIIl), 0, 1, mc.thePlayer);
         boolean var10001 = false;
         lllllllllllllllllllIIlIlIIllllll.delay = TimeUtils.randomDelay((Integer)lllllllllllllllllllIIlIlIIllllll.minDelayValue.get(), (Integer)lllllllllllllllllllIIlIlIIllllll.maxDelayValue.get());
         if (lllllllllllllllllllIIlIlIIllllII) {
            mc.getNetHandler().addToSendQueue(new C0DPacketCloseWindow());
         }

         return true;
      } else {
         return false;
      }
   }

   public AutoArmor() {
      lllllllllllllllllllIIlIlIlIlllll.maxDelayValue = new IntegerValue("MaxDelay", 200, 0, 400) {
         protected void onChanged(Integer llIllIIlllII, Integer llIllIIllIll) {
            int llIllIIllIlI = (Integer)lllllllllllllllllllIIlIlIlIlllll.minDelayValue.get();
            if (llIllIIllIlI > llIllIIllIll) {
               llIllIIlllIl.set(llIllIIllIlI);
            }

         }
      };
      lllllllllllllllllllIIlIlIlIlllll.minDelayValue = new IntegerValue("MinDelay", 100, 0, 400) {
         protected void onChanged(Integer lllIlllIllIlIII, Integer lllIlllIllIIlII) {
            float lllIlllIllIIIll = (Integer)lllllllllllllllllllIIlIlIlIlllll.maxDelayValue.get();
            if (lllIlllIllIIIll < lllIlllIllIIlII) {
               lllIlllIllIlIIl.set(lllIlllIllIIIll);
            }

         }
      };
      lllllllllllllllllllIIlIlIlIlllll.invOpenValue = new BoolValue("InvOpen", false);
      lllllllllllllllllllIIlIlIlIlllll.simulateInventory = new BoolValue("SimulateInventory", true);
      lllllllllllllllllllIIlIlIlIlllll.noMoveValue = new BoolValue("NoMove", false);
      lllllllllllllllllllIIlIlIlIlllll.itemDelayValue = new IntegerValue("ItemDelay", 0, 0, 5000);
      lllllllllllllllllllIIlIlIlIlllll.hotbarValue = new BoolValue("Hotbar", true);
   }
}
