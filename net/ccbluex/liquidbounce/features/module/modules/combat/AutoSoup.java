//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.combat;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.InventoryUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.network.play.client.C16PacketClientStatus.EnumState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
   name = "AutoSoup",
   description = "Makes you automatically eat soup whenever your health is low.",
   category = ModuleCategory.COMBAT
)
@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/AutoSoup;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "delayValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "healthValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "openInventoryValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "simulateInventory", "tag", "", "getTag", "()Ljava/lang/String;", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidSense"}
)
public final class AutoSoup extends Module {
   // $FF: synthetic field
   private final BoolValue simulateInventory = new BoolValue("SimulateInventory", true);
   // $FF: synthetic field
   private final FloatValue healthValue = new FloatValue("Health", 15.0F, 0.0F, 20.0F);
   // $FF: synthetic field
   private final MSTimer timer = new MSTimer();
   // $FF: synthetic field
   private final IntegerValue delayValue = new IntegerValue("Delay", 150, 0, 500);
   // $FF: synthetic field
   private final BoolValue openInventoryValue = new BoolValue("OpenInv", false);

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   @NotNull
   public String getTag() {
      return String.valueOf(((Number)lIIllIllIIIllIl.healthValue.get()).floatValue());
   }

   @EventTarget
   public final void onUpdate(@Nullable UpdateEvent lIIllIllIIIIlII) {
      if (lIIllIllIIIIlIl.timer.hasTimePassed((long)((Number)lIIllIllIIIIlIl.delayValue.get()).intValue())) {
         double lIIllIllIIIIIlI = InventoryUtils.findItem(36, 45, Items.mushroom_stew);
         EntityPlayerSP var10000 = access$getMc$p$s1046033730().thePlayer;
         Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
         Minecraft var6;
         if (var10000.getHealth() <= ((Number)lIIllIllIIIIlIl.healthValue.get()).floatValue() && lIIllIllIIIIIlI != -1) {
            var6 = access$getMc$p$s1046033730();
            Intrinsics.checkExpressionValueIsNotNull(var6, "mc");
            var6.getNetHandler().addToSendQueue((Packet)(new C09PacketHeldItemChange(lIIllIllIIIIIlI - 36)));
            var6 = access$getMc$p$s1046033730();
            Intrinsics.checkExpressionValueIsNotNull(var6, "mc");
            NetHandlerPlayClient var7 = var6.getNetHandler();
            Slot var10003 = access$getMc$p$s1046033730().thePlayer.inventoryContainer.getSlot(lIIllIllIIIIIlI);
            Intrinsics.checkExpressionValueIsNotNull(var10003, "mc.thePlayer.inventoryCo…   .getSlot(soupInHotbar)");
            var7.addToSendQueue((Packet)(new C08PacketPlayerBlockPlacement(var10003.getStack())));
            var6 = access$getMc$p$s1046033730();
            Intrinsics.checkExpressionValueIsNotNull(var6, "mc");
            var6.getNetHandler().addToSendQueue((Packet)(new C07PacketPlayerDigging(Action.DROP_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN)));
            var6 = access$getMc$p$s1046033730();
            Intrinsics.checkExpressionValueIsNotNull(var6, "mc");
            var6.getNetHandler().addToSendQueue((Packet)(new C09PacketHeldItemChange(access$getMc$p$s1046033730().thePlayer.inventory.currentItem)));
            lIIllIllIIIIlIl.timer.reset();
         } else {
            int lIIllIllIIIIIIl = InventoryUtils.findItem(9, 36, Items.mushroom_stew);
            if (lIIllIllIIIIIIl != -1 && InventoryUtils.hasSpaceHotbar()) {
               if ((Boolean)lIIllIllIIIIlIl.openInventoryValue.get() && !(access$getMc$p$s1046033730().currentScreen instanceof GuiInventory)) {
                  return;
               }

               double lIIllIllIIIIIII = !(access$getMc$p$s1046033730().currentScreen instanceof GuiInventory) && (Boolean)lIIllIllIIIIlIl.simulateInventory.get();
               if (lIIllIllIIIIIII) {
                  var6 = access$getMc$p$s1046033730();
                  Intrinsics.checkExpressionValueIsNotNull(var6, "mc");
                  var6.getNetHandler().addToSendQueue((Packet)(new C16PacketClientStatus(EnumState.OPEN_INVENTORY_ACHIEVEMENT)));
               }

               access$getMc$p$s1046033730().playerController.windowClick(0, lIIllIllIIIIIIl, 0, 1, (EntityPlayer)access$getMc$p$s1046033730().thePlayer);
               boolean var10001 = false;
               if (lIIllIllIIIIIII) {
                  var6 = access$getMc$p$s1046033730();
                  Intrinsics.checkExpressionValueIsNotNull(var6, "mc");
                  var6.getNetHandler().addToSendQueue((Packet)(new C0DPacketCloseWindow()));
               }

               lIIllIllIIIIlIl.timer.reset();
            }

         }
      }
   }
}
