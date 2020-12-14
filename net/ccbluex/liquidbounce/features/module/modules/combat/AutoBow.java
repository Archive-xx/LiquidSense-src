//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.combat;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/AutoBow;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "waitForBowAimbot", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidSense"}
)
@ModuleInfo(
   name = "AutoBow",
   description = "Automatically shoots an arrow whenever your bow is fully loaded.",
   category = ModuleCategory.COMBAT
)
public final class AutoBow extends Module {
   // $FF: synthetic field
   private final BoolValue waitForBowAimbot = new BoolValue("WaitForBowAimbot", true);

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent lIlIIllIIIlIll) {
      Intrinsics.checkParameterIsNotNull(lIlIIllIIIlIll, "event");
      Module var10000 = LiquidBounce.INSTANCE.getModuleManager().get(BowAimbot.class);
      if (var10000 == null) {
         throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.combat.BowAimbot");
      } else {
         Exception lIlIIllIIIlIlI = (BowAimbot)var10000;
         EntityPlayerSP var3 = access$getMc$p$s1046033730().thePlayer;
         Intrinsics.checkExpressionValueIsNotNull(var3, "mc.thePlayer");
         if (var3.isUsingItem()) {
            var3 = access$getMc$p$s1046033730().thePlayer;
            Intrinsics.checkExpressionValueIsNotNull(var3, "mc.thePlayer");
            ItemStack var4 = var3.getHeldItem();
            Item var5;
            if (var4 != null) {
               var5 = var4.getItem();
            } else {
               boolean var10001 = false;
               var5 = null;
            }

            if (Intrinsics.areEqual((Object)var5, (Object)Items.bow)) {
               var3 = access$getMc$p$s1046033730().thePlayer;
               Intrinsics.checkExpressionValueIsNotNull(var3, "mc.thePlayer");
               if (var3.getItemInUseDuration() > 20 && (!(Boolean)lIlIIllIIIlllI.waitForBowAimbot.get() || !lIlIIllIIIlIlI.getState() || lIlIIllIIIlIlI.hasTarget())) {
                  access$getMc$p$s1046033730().thePlayer.stopUsingItem();
                  Minecraft var6 = access$getMc$p$s1046033730();
                  Intrinsics.checkExpressionValueIsNotNull(var6, "mc");
                  var6.getNetHandler().addToSendQueue((Packet)(new C07PacketPlayerDigging(Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN)));
               }
            }
         }

      }
   }
}
