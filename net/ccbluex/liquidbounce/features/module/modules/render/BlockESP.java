//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BlockValue;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

@ModuleInfo(
   name = "BlockESP",
   description = "Allows you to see a selected block through walls.",
   category = ModuleCategory.RENDER
)
public class BlockESP extends Module {
   // $FF: synthetic field
   private final IntegerValue colorBlueValue = new IntegerValue("B", 72, 0, 255);
   // $FF: synthetic field
   private Thread thread;
   // $FF: synthetic field
   private final BoolValue colorRainbow = new BoolValue("Rainbow", false);
   // $FF: synthetic field
   private final ListValue modeValue = new ListValue("Mode", new String[]{"Box", "2D"}, "Box");
   // $FF: synthetic field
   private final IntegerValue colorGreenValue = new IntegerValue("G", 179, 0, 255);
   // $FF: synthetic field
   private final IntegerValue colorRedValue = new IntegerValue("R", 255, 0, 255);
   // $FF: synthetic field
   private final MSTimer searchTimer = new MSTimer();
   // $FF: synthetic field
   private final IntegerValue radiusValue = new IntegerValue("Radius", 40, 5, 120);
   // $FF: synthetic field
   private final BlockValue blockValue = new BlockValue("Block", 168);
   // $FF: synthetic field
   private final List<BlockPos> posList = new ArrayList();

   @EventTarget
   public void onUpdate(UpdateEvent llIIIlIllIl) {
      if (llIIIlIllII.searchTimer.hasTimePassed(1000L) && (llIIIlIllII.thread == null || !llIIIlIllII.thread.isAlive())) {
         int llIIIllIIII = (Integer)llIIIlIllII.radiusValue.get();
         Block llIIIlIllll = Block.getBlockById((Integer)llIIIlIllII.blockValue.get());
         if (llIIIlIllll == null || llIIIlIllll == Blocks.air) {
            return;
         }

         llIIIlIllII.thread = new Thread(() -> {
            List<BlockPos> lIllllllIlI = new ArrayList();

            boolean var10001;
            for(int lIlllllIlIl = -llIIIllIIII; lIlllllIlIl < llIIIllIIII; ++lIlllllIlIl) {
               for(int lIlllllllll = llIIIllIIII; lIlllllllll > -llIIIllIIII; --lIlllllllll) {
                  for(int llIIIIIIIII = -llIIIllIIII; llIIIIIIIII < llIIIllIIII; ++llIIIIIIIII) {
                     int llIIIIIIlIl = (int)mc.thePlayer.posX + lIlllllIlIl;
                     int llIIIIIIlII = (int)mc.thePlayer.posY + lIlllllllll;
                     Exception lIlllllIIII = (int)mc.thePlayer.posZ + llIIIIIIIII;
                     BlockPos llIIIIIIIlI = new BlockPos(llIIIIIIlIl, llIIIIIIlII, lIlllllIIII);
                     short lIllllIlllI = BlockUtils.getBlock(llIIIIIIIlI);
                     if (lIllllIlllI == llIIIlIllll) {
                        lIllllllIlI.add(llIIIIIIIlI);
                        var10001 = false;
                     }
                  }
               }
            }

            lIllllllIIl.searchTimer.reset();
            synchronized(lIllllllIIl.posList) {
               lIllllllIIl.posList.clear();
               lIllllllIIl.posList.addAll(lIllllllIlI);
               var10001 = false;
            }
         }, "BlockESP-BlockFinder");
         llIIIlIllII.thread.start();
      }

   }

   @EventTarget
   public void onRender3D(Render3DEvent llIIIIllllI) {
      synchronized(llIIIIlllIl.posList) {
         byte llIIIIllIll = (Boolean)llIIIIlllIl.colorRainbow.get() ? ColorUtils.rainbow() : new Color((Integer)llIIIIlllIl.colorRedValue.get(), (Integer)llIIIIlllIl.colorGreenValue.get(), (Integer)llIIIIlllIl.colorBlueValue.get());
         Iterator llIIIIllIlI = llIIIIlllIl.posList.iterator();

         while(llIIIIllIlI.hasNext()) {
            BlockPos llIIIlIIIIl = (BlockPos)llIIIIllIlI.next();
            double llIIIIllIII = ((String)llIIIIlllIl.modeValue.get()).toLowerCase();
            Exception llIIIIlIlll = -1;
            switch(llIIIIllIII.hashCode()) {
            case 1650:
               if (llIIIIllIII.equals("2d")) {
                  llIIIIlIlll = 1;
               }
               break;
            case 97739:
               if (llIIIIllIII.equals("box")) {
                  llIIIIlIlll = 0;
               }
            }

            switch(llIIIIlIlll) {
            case 0:
               RenderUtils.drawBlockBox(llIIIlIIIIl, llIIIIllIll, true);
               break;
            case 1:
               RenderUtils.draw2D(llIIIlIIIIl, llIIIIllIll.getRGB(), Color.BLACK.getRGB());
            }
         }

      }
   }

   public String getTag() {
      return BlockUtils.getBlockName((Integer)llIIIIlIlII.blockValue.get());
   }
}
