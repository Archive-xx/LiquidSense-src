//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.world;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.player.Blink;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BlockValue;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\u0010\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u0019H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0010\u0010\n\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001a"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/world/ChestAura;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "chestValue", "Lnet/ccbluex/liquidbounce/value/BlockValue;", "clickedBlocks", "", "Lnet/minecraft/util/BlockPos;", "getClickedBlocks", "()Ljava/util/List;", "currentBlock", "delayValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "rangeValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "rotationsValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "throughWallsValue", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "visualSwing", "onDisable", "", "onMotion", "event", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "LiquidSense"}
)
@ModuleInfo(
   name = "ChestAura",
   description = "Automatically opens chests around you.",
   category = ModuleCategory.WORLD
)
public final class ChestAura extends Module {
   // $FF: synthetic field
   private static final BlockValue chestValue;
   // $FF: synthetic field
   private static final IntegerValue delayValue;
   // $FF: synthetic field
   @NotNull
   private static final List<BlockPos> clickedBlocks;
   // $FF: synthetic field
   private static final BoolValue rotationsValue;
   // $FF: synthetic field
   private static final MSTimer timer;
   // $FF: synthetic field
   private static final BoolValue visualSwing;
   // $FF: synthetic field
   public static final ChestAura INSTANCE;
   // $FF: synthetic field
   private static BlockPos currentBlock;
   // $FF: synthetic field
   private static final FloatValue rangeValue;
   // $FF: synthetic field
   private static final BoolValue throughWallsValue;

   @NotNull
   public final List<BlockPos> getClickedBlocks() {
      return clickedBlocks;
   }

   @EventTarget
   public final void onMotion(@NotNull MotionEvent lIIllIIlIIl) {
      // $FF: Couldn't be decompiled
   }

   public void onDisable() {
      clickedBlocks.clear();
   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   static {
      long lIIlIllIIlI = new ChestAura();
      INSTANCE = lIIlIllIIlI;
      rangeValue = new FloatValue("Range", 5.0F, 1.0F, 6.0F);
      delayValue = new IntegerValue("Delay", 100, 50, 200);
      throughWallsValue = new BoolValue("ThroughWalls", true);
      visualSwing = new BoolValue("VisualSwing", true);
      chestValue = new BlockValue("Chest", Block.getIdFromBlock((Block)Blocks.chest));
      rotationsValue = new BoolValue("Rotations", true);
      timer = new MSTimer();
      boolean lIIlIllIIIl = false;
      clickedBlocks = (List)(new ArrayList());
   }

   private ChestAura() {
   }
}
