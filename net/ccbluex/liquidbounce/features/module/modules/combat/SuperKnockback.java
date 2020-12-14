//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.combat;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0BPacketEntityAction.Action;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
   name = "SuperKnockback",
   description = "Increases knockback dealt to other entities.",
   category = ModuleCategory.COMBAT
)
@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/SuperKnockback;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "hurtTimeValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "onAttack", "", "event", "Lnet/ccbluex/liquidbounce/event/AttackEvent;", "LiquidSense"}
)
public final class SuperKnockback extends Module {
   // $FF: synthetic field
   private final IntegerValue hurtTimeValue = new IntegerValue("HurtTime", 10, 0, 10);

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   @EventTarget
   public final void onAttack(@NotNull AttackEvent llllllllllllllllllIIIlllIIlIlIII) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIIIlllIIlIlIII, "event");
      if (llllllllllllllllllIIIlllIIlIlIII.getTargetEntity() instanceof EntityLivingBase) {
         if (((EntityLivingBase)llllllllllllllllllIIIlllIIlIlIII.getTargetEntity()).hurtTime > ((Number)llllllllllllllllllIIIlllIIlIlIIl.hurtTimeValue.get()).intValue()) {
            return;
         }

         EntityPlayerSP var10000 = access$getMc$p$s1046033730().thePlayer;
         Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
         Minecraft var2;
         if (var10000.isSprinting()) {
            var2 = access$getMc$p$s1046033730();
            Intrinsics.checkExpressionValueIsNotNull(var2, "mc");
            var2.getNetHandler().addToSendQueue((Packet)(new C0BPacketEntityAction((Entity)access$getMc$p$s1046033730().thePlayer, Action.STOP_SPRINTING)));
         }

         var2 = access$getMc$p$s1046033730();
         Intrinsics.checkExpressionValueIsNotNull(var2, "mc");
         var2.getNetHandler().addToSendQueue((Packet)(new C0BPacketEntityAction((Entity)access$getMc$p$s1046033730().thePlayer, Action.START_SPRINTING)));
         var2 = access$getMc$p$s1046033730();
         Intrinsics.checkExpressionValueIsNotNull(var2, "mc");
         var2.getNetHandler().addToSendQueue((Packet)(new C0BPacketEntityAction((Entity)access$getMc$p$s1046033730().thePlayer, Action.STOP_SPRINTING)));
         var2 = access$getMc$p$s1046033730();
         Intrinsics.checkExpressionValueIsNotNull(var2, "mc");
         var2.getNetHandler().addToSendQueue((Packet)(new C0BPacketEntityAction((Entity)access$getMc$p$s1046033730().thePlayer, Action.START_SPRINTING)));
         var10000 = access$getMc$p$s1046033730().thePlayer;
         Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
         var10000.setSprinting(true);
         access$getMc$p$s1046033730().thePlayer.serverSprintState = true;
      }

   }
}
