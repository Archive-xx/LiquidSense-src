//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.combat;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.item.ItemUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C02PacketUseEntity.Action;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0007J\u0010\u0010\u0010\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0011H\u0007J\u0010\u0010\u0012\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0013H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0014"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/AutoWeapon;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "gotIt", "", "packetUseEntity", "Lnet/minecraft/network/play/client/C02PacketUseEntity;", "silentValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "spoofedSlot", "tick", "", "onAttack", "", "event", "Lnet/ccbluex/liquidbounce/event/AttackEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "LiquidSense"}
)
@ModuleInfo(
   name = "AutoWeapon",
   description = "Automatically selects the best weapon in your hotbar.",
   category = ModuleCategory.COMBAT
)
public final class AutoWeapon extends Module {
   // $FF: synthetic field
   private boolean gotIt;
   // $FF: synthetic field
   private final BoolValue silentValue = new BoolValue("SpoofItem", false);
   // $FF: synthetic field
   private boolean spoofedSlot;
   // $FF: synthetic field
   private C02PacketUseEntity packetUseEntity;
   // $FF: synthetic field
   private int tick;

   @EventTarget
   public final void onAttack(@NotNull AttackEvent lIlIIIlllllllll) {
      Intrinsics.checkParameterIsNotNull(lIlIIIlllllllll, "event");
      lIlIIlIIIIIIIlI.gotIt = true;
   }

   @EventTarget
   public final void onPacket(@NotNull PacketEvent lIlIIIllllIllIl) {
      Intrinsics.checkParameterIsNotNull(lIlIIIllllIllIl, "event");
      if (lIlIIIllllIllIl.getPacket() instanceof C02PacketUseEntity && ((C02PacketUseEntity)lIlIIIllllIllIl.getPacket()).getAction() == Action.ATTACK && lIlIIIllllIlllI.gotIt) {
         lIlIIIllllIlllI.gotIt = false;
         float lIlIIIllllIlIlI = -1;
         double lIlIIIlllllIIII = 0.0D;
         long lIlIIIllllIlIII = 0;

         for(byte lIlIIIllllIIlll = 8; lIlIIIllllIlIII <= lIlIIIllllIIlll; ++lIlIIIllllIlIII) {
            ItemStack lIlIIIlllllIIlI = access$getMc$p$s1046033730().thePlayer.inventory.getStackInSlot(lIlIIIllllIlIII);
            if (lIlIIIlllllIIlI != null && (lIlIIIlllllIIlI.getItem() instanceof ItemSword || lIlIIIlllllIIlI.getItem() instanceof ItemTool)) {
               Iterator lIlIIIllllIIlII = lIlIIIlllllIIlI.getAttributeModifiers().get("generic.attackDamage").iterator();

               while(lIlIIIllllIIlII.hasNext()) {
                  AttributeModifier lIlIIIlllllIIll = (AttributeModifier)lIlIIIllllIIlII.next();
                  Intrinsics.checkExpressionValueIsNotNull(lIlIIIlllllIIll, "attributeModifier");
                  long lIlIIIllllIIIll = lIlIIIlllllIIll.getAmount() + 1.25D * (double)ItemUtils.getEnchantment(lIlIIIlllllIIlI, Enchantment.sharpness);
                  if (lIlIIIllllIIIll > lIlIIIlllllIIII) {
                     lIlIIIlllllIIII = lIlIIIllllIIIll;
                     lIlIIIllllIlIlI = lIlIIIllllIlIII;
                  }
               }
            }
         }

         if (lIlIIIllllIlIlI != -1 && lIlIIIllllIlIlI != access$getMc$p$s1046033730().thePlayer.inventory.currentItem) {
            if ((Boolean)lIlIIIllllIlllI.silentValue.get()) {
               Minecraft var10000 = access$getMc$p$s1046033730();
               Intrinsics.checkExpressionValueIsNotNull(var10000, "mc");
               var10000.getNetHandler().addToSendQueue((Packet)(new C09PacketHeldItemChange(lIlIIIllllIlIlI)));
               lIlIIIllllIlllI.spoofedSlot = true;
            } else {
               access$getMc$p$s1046033730().thePlayer.inventory.currentItem = lIlIIIllllIlIlI;
               access$getMc$p$s1046033730().playerController.updateController();
            }

            lIlIIIllllIllIl.cancelEvent();
            lIlIIIllllIlllI.packetUseEntity = (C02PacketUseEntity)lIlIIIllllIllIl.getPacket();
            lIlIIIllllIlllI.tick = 0;
         }
      }

   }

   @EventTarget(
      ignoreCondition = true
   )
   public final void onUpdate(@NotNull MotionEvent lIlIIIlllIllllI) {
      Intrinsics.checkParameterIsNotNull(lIlIIIlllIllllI, "event");
      if (lIlIIIlllIlllIl.tick < 1) {
         int var10001 = lIlIIIlllIlllIl.tick++;
      } else {
         if (lIlIIIlllIlllIl.packetUseEntity != null) {
            Minecraft var10000 = access$getMc$p$s1046033730();
            Intrinsics.checkExpressionValueIsNotNull(var10000, "mc");
            NetHandlerPlayClient var3 = var10000.getNetHandler();
            Intrinsics.checkExpressionValueIsNotNull(var3, "mc.netHandler");
            var3.getNetworkManager().sendPacket((Packet)lIlIIIlllIlllIl.packetUseEntity);
            if (lIlIIIlllIlllIl.spoofedSlot) {
               var10000 = access$getMc$p$s1046033730();
               Intrinsics.checkExpressionValueIsNotNull(var10000, "mc");
               var10000.getNetHandler().addToSendQueue((Packet)(new C09PacketHeldItemChange(access$getMc$p$s1046033730().thePlayer.inventory.currentItem)));
            }

            lIlIIIlllIlllIl.packetUseEntity = (C02PacketUseEntity)null;
         }

      }
   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }
}
