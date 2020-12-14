//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement;

import net.ccbluex.liquidbounce.event.BlockBBEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

@ModuleInfo(
   name = "LiquidWalk",
   description = "Allows you to walk on water.",
   category = ModuleCategory.MOVEMENT,
   keyBind = 36
)
public class LiquidWalk extends Module {
   // $FF: synthetic field
   public final ListValue modeValue = new ListValue("Mode", new String[]{"Vanilla", "NCP", "AAC", "AAC3.3.11", "AACFly", "Spartan", "Dolphin"}, "NCP");
   // $FF: synthetic field
   private final FloatValue aacFlyValue = new FloatValue("AACFlyMotion", 0.5F, 0.1F, 1.0F);
   // $FF: synthetic field
   private final BoolValue noJumpValue = new BoolValue("NoJump", false);
   // $FF: synthetic field
   private boolean nextTick;

   @EventTarget
   public void onPacket(PacketEvent lIIIIIIllIlIIII) {
      if (mc.thePlayer != null && ((String)lIIIIIIllIlIIIl.modeValue.get()).equalsIgnoreCase("NCP")) {
         if (lIIIIIIllIlIIII.getPacket() instanceof C03PacketPlayer) {
            double lIIIIIIllIIllIl = (C03PacketPlayer)lIIIIIIllIlIIII.getPacket();
            if (BlockUtils.collideBlock(new AxisAlignedBB(mc.thePlayer.getEntityBoundingBox().maxX, mc.thePlayer.getEntityBoundingBox().maxY, mc.thePlayer.getEntityBoundingBox().maxZ, mc.thePlayer.getEntityBoundingBox().minX, mc.thePlayer.getEntityBoundingBox().minY - 0.01D, mc.thePlayer.getEntityBoundingBox().minZ), (lIIIIIIlIllllll) -> {
               return lIIIIIIlIllllll instanceof BlockLiquid;
            })) {
               lIIIIIIllIlIIIl.nextTick = !lIIIIIIllIlIIIl.nextTick;
               if (lIIIIIIllIlIIIl.nextTick) {
                  lIIIIIIllIIllIl.y -= 0.001D;
               }
            }
         }

      }
   }

   @EventTarget
   public void onBlockBB(BlockBBEvent lIIIIIIllIllIlI) {
      if (mc.thePlayer != null && mc.thePlayer.getEntityBoundingBox() != null) {
         if (lIIIIIIllIllIlI.getBlock() instanceof BlockLiquid && !BlockUtils.collideBlock(mc.thePlayer.getEntityBoundingBox(), (lIIIIIIlIllllII) -> {
            return lIIIIIIlIllllII instanceof BlockLiquid;
         }) && !mc.thePlayer.isSneaking()) {
            Exception lIIIIIIllIlIlll = ((String)lIIIIIIllIllIll.modeValue.get()).toLowerCase();
            String lIIIIIIllIlIllI = -1;
            switch(lIIIIIIllIlIlll.hashCode()) {
            case 108891:
               if (lIIIIIIllIlIlll.equals("ncp")) {
                  lIIIIIIllIlIllI = 0;
               }
               break;
            case 233102203:
               if (lIIIIIIllIlIlll.equals("vanilla")) {
                  lIIIIIIllIlIllI = 1;
               }
            }

            switch(lIIIIIIllIlIllI) {
            case 0:
            case 1:
               lIIIIIIllIllIlI.setBoundingBox(AxisAlignedBB.fromBounds((double)lIIIIIIllIllIlI.getX(), (double)lIIIIIIllIllIlI.getY(), (double)lIIIIIIllIllIlI.getZ(), (double)(lIIIIIIllIllIlI.getX() + 1), (double)(lIIIIIIllIllIlI.getY() + 1), (double)(lIIIIIIllIllIlI.getZ() + 1)));
            }
         }

      }
   }

   @EventTarget
   public void onMove(MoveEvent lIIIIIIlllIIIII) {
      if ("aacfly".equals(((String)lIIIIIIlllIIIIl.modeValue.get()).toLowerCase()) && mc.thePlayer.isInWater()) {
         lIIIIIIlllIIIII.setY((double)(Float)lIIIIIIlllIIIIl.aacFlyValue.get());
         mc.thePlayer.motionY = (double)(Float)lIIIIIIlllIIIIl.aacFlyValue.get();
      }

   }

   @EventTarget
   public void onJump(JumpEvent lIIIIIIllIIlIII) {
      if (mc.thePlayer != null) {
         int lIIIIIIllIIIlII = BlockUtils.getBlock(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ));
         if ((Boolean)lIIIIIIllIIIllI.noJumpValue.get() && lIIIIIIllIIIlII instanceof BlockLiquid) {
            lIIIIIIllIIlIII.cancelEvent();
         }

      }
   }

   public String getTag() {
      return (String)lIIIIIIllIIIIlI.modeValue.get();
   }

   @EventTarget
   public void onUpdate(UpdateEvent lIIIIIIlllIllII) {
      if (mc.thePlayer != null && !mc.thePlayer.isSneaking()) {
         boolean lIIIIIIlllIlIlI = ((String)lIIIIIIlllIlIll.modeValue.get()).toLowerCase();
         int lIIIIIIlllIlIIl = -1;
         switch(lIIIIIIlllIlIlI.hashCode()) {
         case -2011701869:
            if (lIIIIIIlllIlIlI.equals("spartan")) {
               lIIIIIIlllIlIIl = 3;
            }
            break;
         case 96323:
            if (lIIIIIIlllIlIlI.equals("aac")) {
               lIIIIIIlllIlIIl = 2;
            }
            break;
         case 108891:
            if (lIIIIIIlllIlIlI.equals("ncp")) {
               lIIIIIIlllIlIIl = 0;
            }
            break;
         case 233102203:
            if (lIIIIIIlllIlIlI.equals("vanilla")) {
               lIIIIIIlllIlIIl = 1;
            }
            break;
         case 1492139161:
            if (lIIIIIIlllIlIlI.equals("aac3.3.11")) {
               lIIIIIIlllIlIIl = 4;
            }
         }

         EntityPlayerSP var10000;
         switch(lIIIIIIlllIlIIl) {
         case 0:
         case 1:
            if (BlockUtils.collideBlock(mc.thePlayer.getEntityBoundingBox(), (lIIIIIIlIlllIII) -> {
               return lIIIIIIlIlllIII instanceof BlockLiquid;
            }) && mc.thePlayer.isInsideOfMaterial(Material.air) && !mc.thePlayer.isSneaking()) {
               mc.thePlayer.motionY = 0.08D;
            }
            break;
         case 2:
            long lIIIIIIlllIlIII = mc.thePlayer.getPosition().down();
            if (!mc.thePlayer.onGround && BlockUtils.getBlock(lIIIIIIlllIlIII) == Blocks.water || mc.thePlayer.isInWater()) {
               if (!mc.thePlayer.isSprinting()) {
                  var10000 = mc.thePlayer;
                  var10000.motionX *= 0.99999D;
                  var10000 = mc.thePlayer;
                  var10000.motionY *= 0.0D;
                  var10000 = mc.thePlayer;
                  var10000.motionZ *= 0.99999D;
                  if (mc.thePlayer.isCollidedHorizontally) {
                     mc.thePlayer.motionY = (double)((float)((int)(mc.thePlayer.posY - (double)((int)(mc.thePlayer.posY - 1.0D)))) / 8.0F);
                  }
               } else {
                  var10000 = mc.thePlayer;
                  var10000.motionX *= 0.99999D;
                  var10000 = mc.thePlayer;
                  var10000.motionY *= 0.0D;
                  var10000 = mc.thePlayer;
                  var10000.motionZ *= 0.99999D;
                  if (mc.thePlayer.isCollidedHorizontally) {
                     mc.thePlayer.motionY = (double)((float)((int)(mc.thePlayer.posY - (double)((int)(mc.thePlayer.posY - 1.0D)))) / 8.0F);
                  }
               }

               if (mc.thePlayer.fallDistance >= 4.0F) {
                  mc.thePlayer.motionY = -0.004D;
               } else if (mc.thePlayer.isInWater()) {
                  mc.thePlayer.motionY = 0.09D;
               }
            }

            if (mc.thePlayer.hurtTime != 0) {
               mc.thePlayer.onGround = false;
            }
            break;
         case 3:
            if (mc.thePlayer.isInWater()) {
               if (mc.thePlayer.isCollidedHorizontally) {
                  var10000 = mc.thePlayer;
                  var10000.motionY += 0.15D;
                  return;
               }

               long lIIIIIIlllIIlll = BlockUtils.getBlock(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY + 1.0D, mc.thePlayer.posZ));
               short lIIIIIIlllIIllI = BlockUtils.getBlock(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY + 1.1D, mc.thePlayer.posZ));
               if (lIIIIIIlllIIllI instanceof BlockLiquid) {
                  mc.thePlayer.motionY = 0.1D;
               } else if (lIIIIIIlllIIlll instanceof BlockLiquid) {
                  mc.thePlayer.motionY = 0.0D;
               }

               mc.thePlayer.onGround = true;
               var10000 = mc.thePlayer;
               var10000.motionX *= 1.085D;
               var10000 = mc.thePlayer;
               var10000.motionZ *= 1.085D;
            }
            break;
         case 4:
            if (mc.thePlayer.isInWater()) {
               var10000 = mc.thePlayer;
               var10000.motionX *= 1.17D;
               var10000 = mc.thePlayer;
               var10000.motionZ *= 1.17D;
               if (mc.thePlayer.isCollidedHorizontally) {
                  mc.thePlayer.motionY = 0.24D;
               } else if (mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY + 1.0D, mc.thePlayer.posZ)).getBlock() != Blocks.air) {
                  var10000 = mc.thePlayer;
                  var10000.motionY += 0.04D;
               }
            }
         }

      }
   }
}
