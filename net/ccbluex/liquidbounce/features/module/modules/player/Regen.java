//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.player;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.FoodStats;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/Regen;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "foodValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "healthValue", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "noAirValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "resetTimer", "", "speedValue", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidSense"}
)
@ModuleInfo(
   name = "Regen",
   description = "Regenerates your health much faster.",
   category = ModuleCategory.PLAYER
)
public final class Regen extends Module {
   // $FF: synthetic field
   private boolean resetTimer;
   // $FF: synthetic field
   private final IntegerValue healthValue = new IntegerValue("Health", 18, 0, 20);
   // $FF: synthetic field
   private final IntegerValue speedValue = new IntegerValue("Speed", 100, 1, 100);
   // $FF: synthetic field
   private final IntegerValue foodValue = new IntegerValue("Food", 18, 0, 20);
   // $FF: synthetic field
   private final ListValue modeValue = new ListValue("Mode", new String[]{"Vanilla", "Spartan"}, "Vanilla");
   // $FF: synthetic field
   private final BoolValue noAirValue = new BoolValue("NoAir", false);

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent llllIlllIII) {
      Intrinsics.checkParameterIsNotNull(llllIlllIII, "event");
      if (llllIlllIll.resetTimer) {
         access$getMc$p$s1046033730().timer.timerSpeed = 1.0F;
      }

      if ((!(Boolean)llllIlllIll.noAirValue.get() || access$getMc$p$s1046033730().thePlayer.onGround) && !access$getMc$p$s1046033730().thePlayer.capabilities.isCreativeMode) {
         EntityPlayerSP var10000 = access$getMc$p$s1046033730().thePlayer;
         Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
         FoodStats var13 = var10000.getFoodStats();
         Intrinsics.checkExpressionValueIsNotNull(var13, "mc.thePlayer.foodStats");
         if (var13.getFoodLevel() > ((Number)llllIlllIll.foodValue.get()).intValue()) {
            var10000 = access$getMc$p$s1046033730().thePlayer;
            Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
            if (var10000.isEntityAlive()) {
               var10000 = access$getMc$p$s1046033730().thePlayer;
               Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
               if (var10000.getHealth() < ((Number)llllIlllIll.healthValue.get()).floatValue()) {
                  long llllIllIlll = (String)llllIlllIll.modeValue.get();
                  int llllIllIllI = false;
                  if (llllIllIlll == null) {
                     throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                  }

                  String var14 = llllIllIlll.toLowerCase();
                  Intrinsics.checkExpressionValueIsNotNull(var14, "(this as java.lang.String).toLowerCase()");
                  llllIllIlll = var14;
                  boolean llllIllIlIl;
                  boolean llllIllIlII;
                  boolean llllIllllII;
                  int llllIllIlII;
                  Minecraft var15;
                  switch(llllIllIlll.hashCode()) {
                  case -2011701869:
                     if (llllIllIlll.equals("spartan")) {
                        if (MovementUtils.isMoving() || !access$getMc$p$s1046033730().thePlayer.onGround) {
                           return;
                        }

                        int llllIllIllI = 9;
                        llllIllIlIl = false;
                        llllIllIlII = false;
                        llllIllIlII = 0;

                        for(byte llllIllIIll = llllIllIllI; llllIllIlII < llllIllIIll; ++llllIllIlII) {
                           llllIllllII = false;
                           var15 = access$getMc$p$s1046033730();
                           Intrinsics.checkExpressionValueIsNotNull(var15, "mc");
                           var15.getNetHandler().addToSendQueue((Packet)(new C03PacketPlayer()));
                        }

                        access$getMc$p$s1046033730().timer.timerSpeed = 0.45F;
                        llllIlllIll.resetTimer = true;
                     }
                     break;
                  case 233102203:
                     if (llllIllIlll.equals("vanilla")) {
                        int llllIllIllI = ((Number)llllIlllIll.speedValue.get()).intValue();
                        llllIllIlIl = false;
                        llllIllIlII = false;
                        llllIllIlII = 0;

                        for(int llllIllIIll = llllIllIllI; llllIllIlII < llllIllIIll; ++llllIllIlII) {
                           llllIllllII = false;
                           var15 = access$getMc$p$s1046033730();
                           Intrinsics.checkExpressionValueIsNotNull(var15, "mc");
                           var15.getNetHandler().addToSendQueue((Packet)(new C03PacketPlayer()));
                        }
                     }
                  }
               }
            }
         }
      }

   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }
}
