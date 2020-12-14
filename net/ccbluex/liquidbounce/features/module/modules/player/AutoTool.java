//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.player;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.ClickBlockEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
   name = "AutoTool",
   description = "Automatically selects the best tool in your inventory to mine a block.",
   category = ModuleCategory.PLAYER
)
@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u000e\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t¨\u0006\n"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/AutoTool;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "onClick", "", "event", "Lnet/ccbluex/liquidbounce/event/ClickBlockEvent;", "switchSlot", "blockPos", "Lnet/minecraft/util/BlockPos;", "LiquidSense"}
)
public final class AutoTool extends Module {
   public final void switchSlot(@NotNull BlockPos lIllIlIIIIllIlI) {
      Intrinsics.checkParameterIsNotNull(lIllIlIIIIllIlI, "blockPos");
      long lIllIlIIIIllIII = 1.0F;
      String lIllIlIIIIlIlll = -1;
      IBlockState var10000 = access$getMc$p$s1046033730().theWorld.getBlockState(lIllIlIIIIllIlI);
      Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.theWorld.getBlockState(blockPos)");
      Exception lIllIlIIIIlIllI = var10000.getBlock();
      double lIllIlIIIIlllll = 0;

      for(byte lIllIlIIIIlIlII = 8; lIllIlIIIIlllll <= lIllIlIIIIlIlII; ++lIllIlIIIIlllll) {
         ItemStack var10 = access$getMc$p$s1046033730().thePlayer.inventory.getStackInSlot(lIllIlIIIIlllll);
         if (var10 != null) {
            ItemStack lIllIlIIIlIIIII = var10;
            long lIllIlIIIIlIIlI = lIllIlIIIlIIIII.getStrVsBlock(lIllIlIIIIlIllI);
            if (lIllIlIIIIlIIlI > lIllIlIIIIllIII) {
               lIllIlIIIIllIII = lIllIlIIIIlIIlI;
               lIllIlIIIIlIlll = lIllIlIIIIlllll;
            }
         } else {
            boolean var10001 = false;
         }
      }

      if (lIllIlIIIIlIlll != -1) {
         access$getMc$p$s1046033730().thePlayer.inventory.currentItem = lIllIlIIIIlIlll;
      }

   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   @EventTarget
   public final void onClick(@NotNull ClickBlockEvent lIllIlIIllIIIlI) {
      Intrinsics.checkParameterIsNotNull(lIllIlIIllIIIlI, "event");
      BlockPos var10001 = lIllIlIIllIIIlI.getClickedBlock();
      if (var10001 != null) {
         lIllIlIIllIlIII.switchSlot(var10001);
      } else {
         boolean var10002 = false;
      }
   }
}
