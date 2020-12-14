//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.render;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.features.command.CommandManager;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\f"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/render/XRay;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "xrayBlocks", "", "Lnet/minecraft/block/Block;", "getXrayBlocks", "()Ljava/util/List;", "onToggle", "", "state", "", "LiquidSense"}
)
@ModuleInfo(
   name = "XRay",
   description = "Allows you to see ores through walls.",
   category = ModuleCategory.RENDER
)
public final class XRay extends Module {
   // $FF: synthetic field
   @NotNull
   private final List<Block> xrayBlocks;

   @NotNull
   public final List<Block> getXrayBlocks() {
      return lIIlIIIIlIlIII.xrayBlocks;
   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   public XRay() {
      Block[] var10001 = new Block[34];
      Block var10004 = Blocks.coal_ore;
      Intrinsics.checkExpressionValueIsNotNull(var10004, "Blocks.coal_ore");
      var10001[0] = var10004;
      var10004 = Blocks.iron_ore;
      Intrinsics.checkExpressionValueIsNotNull(var10004, "Blocks.iron_ore");
      var10001[1] = var10004;
      var10004 = Blocks.gold_ore;
      Intrinsics.checkExpressionValueIsNotNull(var10004, "Blocks.gold_ore");
      var10001[2] = var10004;
      var10004 = Blocks.redstone_ore;
      Intrinsics.checkExpressionValueIsNotNull(var10004, "Blocks.redstone_ore");
      var10001[3] = var10004;
      var10004 = Blocks.lapis_ore;
      Intrinsics.checkExpressionValueIsNotNull(var10004, "Blocks.lapis_ore");
      var10001[4] = var10004;
      var10004 = Blocks.diamond_ore;
      Intrinsics.checkExpressionValueIsNotNull(var10004, "Blocks.diamond_ore");
      var10001[5] = var10004;
      var10004 = Blocks.emerald_ore;
      Intrinsics.checkExpressionValueIsNotNull(var10004, "Blocks.emerald_ore");
      var10001[6] = var10004;
      var10004 = Blocks.quartz_ore;
      Intrinsics.checkExpressionValueIsNotNull(var10004, "Blocks.quartz_ore");
      var10001[7] = var10004;
      var10004 = Blocks.clay;
      Intrinsics.checkExpressionValueIsNotNull(var10004, "Blocks.clay");
      var10001[8] = var10004;
      var10004 = Blocks.glowstone;
      Intrinsics.checkExpressionValueIsNotNull(var10004, "Blocks.glowstone");
      var10001[9] = var10004;
      var10004 = Blocks.crafting_table;
      Intrinsics.checkExpressionValueIsNotNull(var10004, "Blocks.crafting_table");
      var10001[10] = var10004;
      var10004 = Blocks.torch;
      Intrinsics.checkExpressionValueIsNotNull(var10004, "Blocks.torch");
      var10001[11] = var10004;
      var10004 = Blocks.ladder;
      Intrinsics.checkExpressionValueIsNotNull(var10004, "Blocks.ladder");
      var10001[12] = var10004;
      var10004 = Blocks.tnt;
      Intrinsics.checkExpressionValueIsNotNull(var10004, "Blocks.tnt");
      var10001[13] = var10004;
      var10004 = Blocks.coal_block;
      Intrinsics.checkExpressionValueIsNotNull(var10004, "Blocks.coal_block");
      var10001[14] = var10004;
      var10004 = Blocks.iron_block;
      Intrinsics.checkExpressionValueIsNotNull(var10004, "Blocks.iron_block");
      var10001[15] = var10004;
      var10004 = Blocks.gold_block;
      Intrinsics.checkExpressionValueIsNotNull(var10004, "Blocks.gold_block");
      var10001[16] = var10004;
      var10004 = Blocks.diamond_block;
      Intrinsics.checkExpressionValueIsNotNull(var10004, "Blocks.diamond_block");
      var10001[17] = var10004;
      var10004 = Blocks.emerald_block;
      Intrinsics.checkExpressionValueIsNotNull(var10004, "Blocks.emerald_block");
      var10001[18] = var10004;
      var10004 = Blocks.redstone_block;
      Intrinsics.checkExpressionValueIsNotNull(var10004, "Blocks.redstone_block");
      var10001[19] = var10004;
      var10004 = Blocks.lapis_block;
      Intrinsics.checkExpressionValueIsNotNull(var10004, "Blocks.lapis_block");
      var10001[20] = var10004;
      BlockFire var11 = Blocks.fire;
      Intrinsics.checkExpressionValueIsNotNull(var11, "Blocks.fire");
      var10001[21] = (Block)var11;
      var10004 = Blocks.mossy_cobblestone;
      Intrinsics.checkExpressionValueIsNotNull(var10004, "Blocks.mossy_cobblestone");
      var10001[22] = var10004;
      var10004 = Blocks.mob_spawner;
      Intrinsics.checkExpressionValueIsNotNull(var10004, "Blocks.mob_spawner");
      var10001[23] = var10004;
      var10004 = Blocks.end_portal_frame;
      Intrinsics.checkExpressionValueIsNotNull(var10004, "Blocks.end_portal_frame");
      var10001[24] = var10004;
      var10004 = Blocks.enchanting_table;
      Intrinsics.checkExpressionValueIsNotNull(var10004, "Blocks.enchanting_table");
      var10001[25] = var10004;
      var10004 = Blocks.bookshelf;
      Intrinsics.checkExpressionValueIsNotNull(var10004, "Blocks.bookshelf");
      var10001[26] = var10004;
      var10004 = Blocks.command_block;
      Intrinsics.checkExpressionValueIsNotNull(var10004, "Blocks.command_block");
      var10001[27] = var10004;
      BlockStaticLiquid var12 = Blocks.lava;
      Intrinsics.checkExpressionValueIsNotNull(var12, "Blocks.lava");
      var10001[28] = (Block)var12;
      BlockDynamicLiquid var13 = Blocks.flowing_lava;
      Intrinsics.checkExpressionValueIsNotNull(var13, "Blocks.flowing_lava");
      var10001[29] = (Block)var13;
      var12 = Blocks.water;
      Intrinsics.checkExpressionValueIsNotNull(var12, "Blocks.water");
      var10001[30] = (Block)var12;
      var13 = Blocks.flowing_water;
      Intrinsics.checkExpressionValueIsNotNull(var13, "Blocks.flowing_water");
      var10001[31] = (Block)var13;
      var10004 = Blocks.furnace;
      Intrinsics.checkExpressionValueIsNotNull(var10004, "Blocks.furnace");
      var10001[32] = var10004;
      var10004 = Blocks.lit_furnace;
      Intrinsics.checkExpressionValueIsNotNull(var10004, "Blocks.lit_furnace");
      var10001[33] = var10004;
      lIIIlllllllIlI.xrayBlocks = CollectionsKt.mutableListOf(var10001);
      CommandManager var10000 = LiquidBounce.INSTANCE.getCommandManager();
      double lIIIllllllIlII = "xray";
      double lIIIllllllIllI = var10000;
      String lIIIllllllIIlI = new String[0];
      lIIIllllllIllI.registerCommand((Command)(new Command(lIIIllllllIlII, lIIIllllllIIlI) {
         public void execute(@NotNull String[] llllllllllllllllllIIIIIIIIIIllII) {
            Intrinsics.checkParameterIsNotNull(llllllllllllllllllIIIIIIIIIIllII, "args");
            if (llllllllllllllllllIIIIIIIIIIllII.length > 1) {
               boolean var10001;
               Block llllllllllllllllllIIIIIIIIIlIlII;
               if (StringsKt.equals(llllllllllllllllllIIIIIIIIIIllII[1], "add", true)) {
                  if (llllllllllllllllllIIIIIIIIIIllII.length > 2) {
                     try {
                        llllllllllllllllllIIIIIIIIIlIlII = Block.getBlockById(Integer.parseInt(llllllllllllllllllIIIIIIIIIIllII[2]));
                        if (lIIIlllllllIlI.getXrayBlocks().contains(llllllllllllllllllIIIIIIIIIlIlII)) {
                           llllllllllllllllllIIIIIIIIIIllIl.chat("This block is already on the list.");
                           return;
                        }

                        List var10000 = lIIIlllllllIlI.getXrayBlocks();
                        Intrinsics.checkExpressionValueIsNotNull(llllllllllllllllllIIIIIIIIIlIlII, "block");
                        var10000.add(llllllllllllllllllIIIIIIIIIlIlII);
                        var10001 = false;
                        LiquidBounce.INSTANCE.getFileManager().saveConfig(LiquidBounce.INSTANCE.getFileManager().xrayConfig);
                        llllllllllllllllllIIIIIIIIIIllIl.chat(String.valueOf((new StringBuilder()).append("§7Added block §8").append(llllllllllllllllllIIIIIIIIIlIlII.getLocalizedName()).append("§7.")));
                        llllllllllllllllllIIIIIIIIIIllIl.playEdit();
                     } catch (NumberFormatException var8) {
                        llllllllllllllllllIIIIIIIIIIllIl.chatSyntaxError();
                     }

                     return;
                  }

                  llllllllllllllllllIIIIIIIIIIllIl.chatSyntax("xray add <block_id>");
                  return;
               }

               if (StringsKt.equals(llllllllllllllllllIIIIIIIIIIllII[1], "remove", true)) {
                  if (llllllllllllllllllIIIIIIIIIIllII.length > 2) {
                     try {
                        llllllllllllllllllIIIIIIIIIlIlII = Block.getBlockById(Integer.parseInt(llllllllllllllllllIIIIIIIIIIllII[2]));
                        if (!lIIIlllllllIlI.getXrayBlocks().contains(llllllllllllllllllIIIIIIIIIlIlII)) {
                           llllllllllllllllllIIIIIIIIIIllIl.chat("This block is not on the list.");
                           return;
                        }

                        lIIIlllllllIlI.getXrayBlocks().remove(llllllllllllllllllIIIIIIIIIlIlII);
                        var10001 = false;
                        LiquidBounce.INSTANCE.getFileManager().saveConfig(LiquidBounce.INSTANCE.getFileManager().xrayConfig);
                        StringBuilder var11 = (new StringBuilder()).append("§7Removed block §8");
                        Intrinsics.checkExpressionValueIsNotNull(llllllllllllllllllIIIIIIIIIlIlII, "block");
                        llllllllllllllllllIIIIIIIIIIllIl.chat(String.valueOf(var11.append(llllllllllllllllllIIIIIIIIIlIlII.getLocalizedName()).append("§7.")));
                        llllllllllllllllllIIIIIIIIIIllIl.playEdit();
                     } catch (NumberFormatException var9) {
                        llllllllllllllllllIIIIIIIIIIllIl.chatSyntaxError();
                     }

                     return;
                  }

                  llllllllllllllllllIIIIIIIIIIllIl.chatSyntax("xray remove <block_id>");
                  return;
               }

               if (StringsKt.equals(llllllllllllllllllIIIIIIIIIIllII[1], "list", true)) {
                  llllllllllllllllllIIIIIIIIIIllIl.chat("§8Xray blocks:");
                  Iterable llllllllllllllllllIIIIIIIIIIllll = (Iterable)lIIIlllllllIlI.getXrayBlocks();
                  long llllllllllllllllllIIIIIIIIIIlIII = false;
                  Iterator llllllllllllllllllIIIIIIIIIIIlll = llllllllllllllllllIIIIIIIIIIllll.iterator();

                  while(llllllllllllllllllIIIIIIIIIIIlll.hasNext()) {
                     int llllllllllllllllllIIIIIIIIIIIllI = llllllllllllllllllIIIIIIIIIIIlll.next();
                     Block llllllllllllllllllIIIIIIIIIlIIlI = (Block)llllllllllllllllllIIIIIIIIIIIllI;
                     int llllllllllllllllllIIIIIIIIIlIIIl = false;
                     llllllllllllllllllIIIIIIIIIIllIl.chat(String.valueOf((new StringBuilder()).append("§8").append(llllllllllllllllllIIIIIIIIIlIIlI.getLocalizedName()).append(" §7-§c ").append(Block.getIdFromBlock(llllllllllllllllllIIIIIIIIIlIIlI))));
                  }

                  return;
               }
            }

            llllllllllllllllllIIIIIIIIIIllIl.chatSyntax("xray <add, remove, list>");
         }
      }));
      boolean var10 = false;
   }

   public void onToggle(boolean lIIlIIIIlIIlIl) {
      access$getMc$p$s1046033730().renderGlobal.loadRenderers();
   }
}
