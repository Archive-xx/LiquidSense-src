//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.player;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\bH\u0002J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/Zoot;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "badEffectsValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "fireValue", "noAirValue", "hasBadEffect", "", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidSense"}
)
@ModuleInfo(
   name = "Zoot",
   description = "Removes all bad potion effects/fire.",
   category = ModuleCategory.PLAYER
)
public final class Zoot extends Module {
   // $FF: synthetic field
   private final BoolValue fireValue = new BoolValue("Fire", true);
   // $FF: synthetic field
   private final BoolValue badEffectsValue = new BoolValue("BadEffects", true);
   // $FF: synthetic field
   private final BoolValue noAirValue = new BoolValue("NoAir", false);

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent llllllllllllllllllIllIlllIlIllll) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIllIlllIlIllll, "event");
      if (!(Boolean)llllllllllllllllllIllIlllIlIlllI.noAirValue.get() || access$getMc$p$s1046033730().thePlayer.onGround) {
         EntityPlayerSP var10000;
         Minecraft var8;
         if ((Boolean)llllllllllllllllllIllIlllIlIlllI.badEffectsValue.get()) {
            var10000 = access$getMc$p$s1046033730().thePlayer;
            Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
            Iterator llllllllllllllllllIllIlllIlIlIll = var10000.getActivePotionEffects().iterator();

            label50:
            while(true) {
               PotionEffect llllllllllllllllllIllIlllIllIIlI;
               do {
                  do {
                     if (!llllllllllllllllllIllIlllIlIlIll.hasNext()) {
                        break label50;
                     }

                     llllllllllllllllllIllIlllIllIIlI = (PotionEffect)llllllllllllllllllIllIlllIlIlIll.next();
                  } while(llllllllllllllllllIllIlllIllIIlI == null);
               } while(!llllllllllllllllllIllIlllIlIlllI.hasBadEffect());

               char llllllllllllllllllIllIlllIlIlIlI = 0;

               for(int llllllllllllllllllIllIlllIlIlIIl = llllllllllllllllllIllIlllIllIIlI.getDuration() / 20; llllllllllllllllllIllIlllIlIlIlI < llllllllllllllllllIllIlllIlIlIIl; ++llllllllllllllllllIllIlllIlIlIlI) {
                  var8 = access$getMc$p$s1046033730();
                  Intrinsics.checkExpressionValueIsNotNull(var8, "mc");
                  var8.getNetHandler().addToSendQueue((Packet)(new C03PacketPlayer()));
               }
            }
         }

         if ((Boolean)llllllllllllllllllIllIlllIlIlllI.fireValue.get() && !access$getMc$p$s1046033730().thePlayer.capabilities.isCreativeMode) {
            var10000 = access$getMc$p$s1046033730().thePlayer;
            Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
            if (var10000.isBurning()) {
               long llllllllllllllllllIllIlllIlIllII = 0;

               for(byte llllllllllllllllllIllIlllIlIlIll = 9; llllllllllllllllllIllIlllIlIllII <= llllllllllllllllllIllIlllIlIlIll; ++llllllllllllllllllIllIlllIlIllII) {
                  var8 = access$getMc$p$s1046033730();
                  Intrinsics.checkExpressionValueIsNotNull(var8, "mc");
                  var8.getNetHandler().addToSendQueue((Packet)(new C03PacketPlayer()));
               }
            }
         }

      }
   }

   private final boolean hasBadEffect() {
      return access$getMc$p$s1046033730().thePlayer.isPotionActive(Potion.hunger) || access$getMc$p$s1046033730().thePlayer.isPotionActive(Potion.moveSlowdown) || access$getMc$p$s1046033730().thePlayer.isPotionActive(Potion.digSlowdown) || access$getMc$p$s1046033730().thePlayer.isPotionActive(Potion.harm) || access$getMc$p$s1046033730().thePlayer.isPotionActive(Potion.confusion) || access$getMc$p$s1046033730().thePlayer.isPotionActive(Potion.blindness) || access$getMc$p$s1046033730().thePlayer.isPotionActive(Potion.weakness) || access$getMc$p$s1046033730().thePlayer.isPotionActive(Potion.wither) || access$getMc$p$s1046033730().thePlayer.isPotionActive(Potion.poison);
   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }
}
