//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.player;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
   name = "Eagle",
   description = "Makes you eagle (aka. FastBridge).",
   category = ModuleCategory.PLAYER
)
@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\u0010\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0007¨\u0006\b"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/Eagle;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "onDisable", "", "onUpdate", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidSense"}
)
public final class Eagle extends Module {
   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   public void onDisable() {
      if (access$getMc$p$s1046033730().thePlayer != null) {
         if (!GameSettings.isKeyDown(access$getMc$p$s1046033730().gameSettings.keyBindSneak)) {
            access$getMc$p$s1046033730().gameSettings.keyBindSneak.pressed = false;
         }

      }
   }

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent llIIllllIIIIll) {
      Intrinsics.checkParameterIsNotNull(llIIllllIIIIll, "event");
      KeyBinding var10000 = access$getMc$p$s1046033730().gameSettings.keyBindSneak;
      IBlockState var10001 = access$getMc$p$s1046033730().theWorld.getBlockState(new BlockPos(access$getMc$p$s1046033730().thePlayer.posX, access$getMc$p$s1046033730().thePlayer.posY - 1.0D, access$getMc$p$s1046033730().thePlayer.posZ));
      Intrinsics.checkExpressionValueIsNotNull(var10001, "mc.theWorld.getBlockStat… 1.0, mc.thePlayer.posZ))");
      var10000.pressed = Intrinsics.areEqual((Object)var10001.getBlock(), (Object)Blocks.air);
   }
}
