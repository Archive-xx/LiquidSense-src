//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.other;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

public class MineplexGround extends SpeedMode {
   // $FF: synthetic field
   private float speed = 0.0F;
   // $FF: synthetic field
   private boolean spoofSlot;

   public void onDisable() {
      lIIllIlIlllll.speed = 0.0F;
      mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
   }

   public void onMove(MoveEvent lIIllIllIIIIl) {
   }

   public void onMotion() {
      if (MovementUtils.isMoving() && mc.thePlayer.onGround && mc.thePlayer.inventory.getCurrentItem() != null && !mc.thePlayer.isUsingItem()) {
         lIIllIlllIIIl.spoofSlot = false;

         for(int lIIllIlllIIll = 36; lIIllIlllIIll < 45; ++lIIllIlllIIll) {
            long lIIllIllIllll = mc.thePlayer.inventoryContainer.getSlot(lIIllIlllIIll).getStack();
            if (lIIllIllIllll == null) {
               mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(lIIllIlllIIll - 36));
               lIIllIlllIIIl.spoofSlot = true;
               break;
            }
         }

      }
   }

   public void onUpdate() {
      if (MovementUtils.isMoving() && mc.thePlayer.onGround && !mc.thePlayer.isUsingItem()) {
         if (!lIIllIllIlIlI.spoofSlot && mc.thePlayer.inventory.getCurrentItem() != null) {
            ClientUtils.displayChatMessage("§8[§c§lMineplex§aSpeed§8] §cYou need one empty slot.");
         } else {
            float lIIllIllIIlIl = new BlockPos(mc.thePlayer.posX, mc.thePlayer.getEntityBoundingBox().minY - 1.0D, mc.thePlayer.posZ);
            Vec3 lIIllIllIlIII = (new Vec3(lIIllIllIIlIl)).addVector(0.4000000059604645D, 0.4000000059604645D, 0.4000000059604645D).add(new Vec3(EnumFacing.UP.getDirectionVec()));
            mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, (ItemStack)null, lIIllIllIIlIl, EnumFacing.UP, new Vec3(lIIllIllIlIII.xCoord * 0.4000000059604645D, lIIllIllIlIII.yCoord * 0.4000000059604645D, lIIllIllIlIII.zCoord * 0.4000000059604645D));
            boolean var10001 = false;
            float lIIllIllIIlll = (Float)((Speed)LiquidBounce.moduleManager.getModule(Speed.class)).mineplexGroundSpeedValue.get();
            if (lIIllIllIIlll > lIIllIllIlIlI.speed) {
               lIIllIllIlIlI.speed += lIIllIllIIlll / 8.0F;
            }

            if (lIIllIllIlIlI.speed >= lIIllIllIIlll) {
               lIIllIllIlIlI.speed = lIIllIllIIlll;
            }

            MovementUtils.strafe(lIIllIllIlIlI.speed);
            if (!lIIllIllIlIlI.spoofSlot) {
               mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
            }

         }
      } else {
         lIIllIllIlIlI.speed = 0.0F;
      }
   }

   public MineplexGround() {
      super("MineplexGround");
   }
}
