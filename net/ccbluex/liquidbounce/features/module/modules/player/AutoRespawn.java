//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.player;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.exploit.Ghost;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiScreen;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/AutoRespawn;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "instantValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidSense"}
)
@ModuleInfo(
   name = "AutoRespawn",
   description = "Automatically respawns you after dying.",
   category = ModuleCategory.PLAYER
)
public final class AutoRespawn extends Module {
   // $FF: synthetic field
   private final BoolValue instantValue = new BoolValue("Instant", true);

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent llllllllllllllllllllIllllIlIlllI) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllllIllllIlIlllI, "event");
      Module var10000 = LiquidBounce.INSTANCE.getModuleManager().get(Ghost.class);
      if (var10000 == null) {
         Intrinsics.throwNpe();
      }

      if (!var10000.getState()) {
         boolean var3;
         if ((Boolean)llllllllllllllllllllIllllIllIIIl.instantValue.get()) {
            EntityPlayerSP var2 = access$getMc$p$s1046033730().thePlayer;
            Intrinsics.checkExpressionValueIsNotNull(var2, "mc.thePlayer");
            var3 = var2.getHealth() == 0.0F || access$getMc$p$s1046033730().thePlayer.isDead;
         } else {
            label30: {
               if (access$getMc$p$s1046033730().currentScreen instanceof GuiGameOver) {
                  GuiScreen var4 = access$getMc$p$s1046033730().currentScreen;
                  if (var4 == null) {
                     throw new TypeCastException("null cannot be cast to non-null type net.minecraft.client.gui.GuiGameOver");
                  }

                  if (((GuiGameOver)var4).enableButtonsTimer >= 20) {
                     var3 = true;
                     break label30;
                  }
               }

               var3 = false;
            }
         }

         if (var3) {
            access$getMc$p$s1046033730().thePlayer.respawnPlayer();
            access$getMc$p$s1046033730().displayGuiScreen((GuiScreen)null);
         }

      }
   }
}
