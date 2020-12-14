//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement;

import net.ccbluex.liquidbounce.event.BlockBBEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockVine;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

@ModuleInfo(
   name = "FastClimb",
   description = "Allows you to climb up ladders and vines faster.",
   category = ModuleCategory.MOVEMENT
)
public class FastClimb extends Module {
   // $FF: synthetic field
   private final FloatValue normalSpeedValue = new FloatValue("NormalSpeed", 0.2872F, 0.01F, 5.0F);
   // $FF: synthetic field
   public final ListValue modeValue = new ListValue("Mode", new String[]{"Normal", "InstantTP", "AAC", "AACv3", "OAAC", "LAAC"}, "Normal");

   @EventTarget
   public void onBlockBB(BlockBBEvent llllllllllllllllllllIIlllIllIlIl) {
      if (mc.thePlayer != null && (llllllllllllllllllllIIlllIllIlIl.getBlock() instanceof BlockLadder || llllllllllllllllllllIIlllIllIlIl.getBlock() instanceof BlockVine) && ((String)llllllllllllllllllllIIlllIllIllI.modeValue.get()).equalsIgnoreCase("AACv3") && mc.thePlayer.isOnLadder()) {
         llllllllllllllllllllIIlllIllIlIl.setBoundingBox((AxisAlignedBB)null);
      }

   }

   @EventTarget
   public void onMove(MoveEvent llllllllllllllllllllIIllllIIIIll) {
      byte llllllllllllllllllllIIlllIllllll = (String)llllllllllllllllllllIIllllIIIlII.modeValue.get();
      if (llllllllllllllllllllIIlllIllllll.equalsIgnoreCase("Normal") && mc.thePlayer.isCollidedHorizontally && mc.thePlayer.isOnLadder()) {
         llllllllllllllllllllIIllllIIIIll.setY((double)(Float)llllllllllllllllllllIIllllIIIlII.normalSpeedValue.get());
         mc.thePlayer.motionY = 0.0D;
      } else {
         double llllllllllllllllllllIIllllIIlIII;
         if (llllllllllllllllllllIIlllIllllll.equalsIgnoreCase("AAC") && mc.thePlayer.isCollidedHorizontally) {
            long llllllllllllllllllllIIlllIlllllI = mc.thePlayer.getHorizontalFacing();
            float llllllllllllllllllllIIlllIllllIl = 0.0D;
            llllllllllllllllllllIIllllIIlIII = 0.0D;
            if (llllllllllllllllllllIIlllIlllllI == EnumFacing.NORTH) {
               llllllllllllllllllllIIllllIIlIII = -0.99D;
            }

            if (llllllllllllllllllllIIlllIlllllI == EnumFacing.EAST) {
               llllllllllllllllllllIIlllIllllIl = 0.99D;
            }

            if (llllllllllllllllllllIIlllIlllllI == EnumFacing.SOUTH) {
               llllllllllllllllllllIIllllIIlIII = 0.99D;
            }

            if (llllllllllllllllllllIIlllIlllllI == EnumFacing.WEST) {
               llllllllllllllllllllIIlllIllllIl = -0.99D;
            }

            BlockPos llllllllllllllllllllIIllllIIlIll = new BlockPos(mc.thePlayer.posX + llllllllllllllllllllIIlllIllllIl, mc.thePlayer.posY, mc.thePlayer.posZ + llllllllllllllllllllIIllllIIlIII);
            byte llllllllllllllllllllIIlllIlllIIl = BlockUtils.getBlock(llllllllllllllllllllIIllllIIlIll);
            if (llllllllllllllllllllIIlllIlllIIl instanceof BlockLadder || llllllllllllllllllllIIlllIlllIIl instanceof BlockVine) {
               llllllllllllllllllllIIllllIIIIll.setY(0.5D);
               mc.thePlayer.motionY = 0.0D;
            }
         } else if (llllllllllllllllllllIIlllIllllll.equalsIgnoreCase("AACv3") && BlockUtils.collideBlockIntersects(mc.thePlayer.getEntityBoundingBox(), (llllllllllllllllllllIIlllIlIllIl) -> {
            return llllllllllllllllllllIIlllIlIllIl instanceof BlockLadder || llllllllllllllllllllIIlllIlIllIl instanceof BlockVine;
         }) && mc.gameSettings.keyBindForward.isKeyDown()) {
            llllllllllllllllllllIIllllIIIIll.setY(0.5D);
            llllllllllllllllllllIIllllIIIIll.setX(0.0D);
            llllllllllllllllllllIIllllIIIIll.setZ(0.0D);
            mc.thePlayer.motionY = 0.0D;
            mc.thePlayer.motionX = 0.0D;
            mc.thePlayer.motionZ = 0.0D;
         } else if (llllllllllllllllllllIIlllIllllll.equalsIgnoreCase("OAAC") && mc.thePlayer.isCollidedHorizontally && mc.thePlayer.isOnLadder()) {
            llllllllllllllllllllIIllllIIIIll.setY(0.1649D);
            mc.thePlayer.motionY = 0.0D;
         } else if (llllllllllllllllllllIIlllIllllll.equalsIgnoreCase("LAAC") && mc.thePlayer.isCollidedHorizontally && mc.thePlayer.isOnLadder()) {
            llllllllllllllllllllIIllllIIIIll.setY(0.1699D);
            mc.thePlayer.motionY = 0.0D;
         } else if (llllllllllllllllllllIIlllIllllll.equalsIgnoreCase("InstantTP") && mc.thePlayer.isOnLadder() && mc.gameSettings.keyBindForward.isKeyDown()) {
            for(int llllllllllllllllllllIIlllIlllllI = (int)mc.thePlayer.posY; llllllllllllllllllllIIlllIlllllI < 256; ++llllllllllllllllllllIIlllIlllllI) {
               Block llllllllllllllllllllIIllllIIIllI = BlockUtils.getBlock(new BlockPos(mc.thePlayer.posX, (double)llllllllllllllllllllIIlllIlllllI, mc.thePlayer.posZ));
               if (!(llllllllllllllllllllIIllllIIIllI instanceof BlockLadder)) {
                  EnumFacing llllllllllllllllllllIIllllIIlIIl = mc.thePlayer.getHorizontalFacing();
                  llllllllllllllllllllIIllllIIlIII = 0.0D;
                  double llllllllllllllllllllIIlllIlllIlI = 0.0D;
                  switch(llllllllllllllllllllIIllllIIlIIl) {
                  case DOWN:
                  case UP:
                  default:
                     break;
                  case NORTH:
                     llllllllllllllllllllIIlllIlllIlI = -1.0D;
                     break;
                  case EAST:
                     llllllllllllllllllllIIllllIIlIII = 1.0D;
                     break;
                  case SOUTH:
                     llllllllllllllllllllIIlllIlllIlI = 1.0D;
                     break;
                  case WEST:
                     llllllllllllllllllllIIllllIIlIII = -1.0D;
                  }

                  mc.thePlayer.setPosition(mc.thePlayer.posX + llllllllllllllllllllIIllllIIlIII, (double)llllllllllllllllllllIIlllIlllllI, mc.thePlayer.posZ + llllllllllllllllllllIIlllIlllIlI);
                  break;
               }
            }
         }
      }

   }

   public String getTag() {
      return (String)llllllllllllllllllllIIlllIllIIIl.modeValue.get();
   }
}
