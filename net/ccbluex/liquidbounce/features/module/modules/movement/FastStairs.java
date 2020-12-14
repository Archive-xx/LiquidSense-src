//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.block.BlockStairs;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.BlockPos;

@ModuleInfo(
   name = "FastStairs",
   description = "Allows you to climb up stairs faster.",
   category = ModuleCategory.MOVEMENT
)
public class FastStairs extends Module {
   // $FF: synthetic field
   private final ListValue modeValue = new ListValue("Mode", new String[]{"NCP", "AAC", "LAAC"}, "NCP");
   // $FF: synthetic field
   private final BoolValue longJumpValue = new BoolValue("LongJump", false);
   // $FF: synthetic field
   private boolean canJump;

   public String getTag() {
      return (String)lIlIlllIIIIllI.modeValue.get();
   }

   @EventTarget
   public void onUpdate(UpdateEvent lIlIlllIIIlllI) {
      if (mc.thePlayer != null && !LiquidBounce.moduleManager.getModule(Speed.class).getState()) {
         byte lIlIlllIIIlIll = new BlockPos(mc.thePlayer.posX, mc.thePlayer.getEntityBoundingBox().minY, mc.thePlayer.posZ);
         if (mc.thePlayer.onGround && (double)mc.thePlayer.movementInput.moveForward > 0.0D) {
            String lIlIlllIIlIIII = (String)lIlIlllIIIllll.modeValue.get();
            EntityPlayerSP var10000;
            if (BlockUtils.getBlock(lIlIlllIIIlIll) instanceof BlockStairs) {
               mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.5D, mc.thePlayer.posZ);
               double lIlIlllIIlIIIl = lIlIlllIIlIIII.equalsIgnoreCase("NCP") ? 1.4D : (lIlIlllIIlIIII.equalsIgnoreCase("AAC") ? 1.5D : (lIlIlllIIlIIII.equalsIgnoreCase("AAC") ? 1.499D : 1.0D));
               var10000 = mc.thePlayer;
               var10000.motionX *= lIlIlllIIlIIIl;
               var10000 = mc.thePlayer;
               var10000.motionZ *= lIlIlllIIlIIIl;
            }

            if (BlockUtils.getBlock(lIlIlllIIIlIll.down()) instanceof BlockStairs) {
               var10000 = mc.thePlayer;
               var10000.motionX *= 1.3D;
               var10000 = mc.thePlayer;
               var10000.motionZ *= 1.3D;
               if (lIlIlllIIlIIII.equalsIgnoreCase("LAAC")) {
                  var10000 = mc.thePlayer;
                  var10000.motionX *= 1.18D;
                  var10000 = mc.thePlayer;
                  var10000.motionZ *= 1.18D;
               }

               lIlIlllIIIllll.canJump = true;
            } else if ((lIlIlllIIlIIII.equalsIgnoreCase("LAAC") || lIlIlllIIlIIII.equalsIgnoreCase("AAC")) && mc.thePlayer.onGround && lIlIlllIIIllll.canJump) {
               if ((Boolean)lIlIlllIIIllll.longJumpValue.get()) {
                  mc.thePlayer.jump();
                  var10000 = mc.thePlayer;
                  var10000.motionX *= 1.35D;
                  var10000 = mc.thePlayer;
                  var10000.motionZ *= 1.35D;
               }

               lIlIlllIIIllll.canJump = false;
            }
         }

      }
   }
}
