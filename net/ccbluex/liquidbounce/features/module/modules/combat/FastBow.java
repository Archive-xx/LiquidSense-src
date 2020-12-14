//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.combat;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C03PacketPlayer.C05PacketPlayerLook;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/FastBow;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "packetsValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidSense"}
)
@ModuleInfo(
   name = "FastBow",
   description = "Turns your bow into a machine gun.",
   category = ModuleCategory.COMBAT
)
public final class FastBow extends Module {
   // $FF: synthetic field
   private final IntegerValue packetsValue = new IntegerValue("Packets", 20, 3, 20);

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent lllIlIlllIllll) {
      Intrinsics.checkParameterIsNotNull(lllIlIlllIllll, "event");
      EntityPlayerSP var10000 = access$getMc$p$s1046033730().thePlayer;
      Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
      if (var10000.isUsingItem()) {
         if (access$getMc$p$s1046033730().thePlayer.inventory.getCurrentItem() != null) {
            ItemStack var6 = access$getMc$p$s1046033730().thePlayer.inventory.getCurrentItem();
            Intrinsics.checkExpressionValueIsNotNull(var6, "mc.thePlayer.inventory.getCurrentItem()");
            if (var6.getItem() instanceof ItemBow) {
               Minecraft var7 = access$getMc$p$s1046033730();
               Intrinsics.checkExpressionValueIsNotNull(var7, "mc");
               NetHandlerPlayClient var8 = var7.getNetHandler();
               BlockPos var10003 = BlockPos.ORIGIN;
               EntityPlayerSP var10005 = access$getMc$p$s1046033730().thePlayer;
               Intrinsics.checkExpressionValueIsNotNull(var10005, "mc.thePlayer");
               var8.addToSendQueue((Packet)(new C08PacketPlayerBlockPlacement(var10003, 255, var10005.getCurrentEquippedItem(), 0.0F, 0.0F, 0.0F)));
               float lllIlIllllIIIl = RotationUtils.targetRotation != null ? RotationUtils.targetRotation.getYaw() : access$getMc$p$s1046033730().thePlayer.rotationYaw;
               float lllIlIllllIIlI = RotationUtils.targetRotation != null ? RotationUtils.targetRotation.getPitch() : access$getMc$p$s1046033730().thePlayer.rotationPitch;
               double lllIlIlllIlIlI = 0;

               for(int lllIlIlllIlIIl = ((Number)lllIlIlllIlllI.packetsValue.get()).intValue(); lllIlIlllIlIlI < lllIlIlllIlIIl; ++lllIlIlllIlIlI) {
                  var7 = access$getMc$p$s1046033730();
                  Intrinsics.checkExpressionValueIsNotNull(var7, "mc");
                  var7.getNetHandler().addToSendQueue((Packet)(new C05PacketPlayerLook(lllIlIllllIIIl, lllIlIllllIIlI, true)));
               }

               var7 = access$getMc$p$s1046033730();
               Intrinsics.checkExpressionValueIsNotNull(var7, "mc");
               var7.getNetHandler().addToSendQueue((Packet)(new C07PacketPlayerDigging(Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN)));
               var10000 = access$getMc$p$s1046033730().thePlayer;
               ItemStack var10001 = access$getMc$p$s1046033730().thePlayer.inventory.getCurrentItem();
               Intrinsics.checkExpressionValueIsNotNull(var10001, "mc.thePlayer.inventory.getCurrentItem()");
               var10000.itemInUseCount = var10001.getMaxItemUseDuration() - 1;
            }
         }

      }
   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }
}
