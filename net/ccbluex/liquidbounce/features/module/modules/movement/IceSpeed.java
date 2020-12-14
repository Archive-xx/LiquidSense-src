//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement;

import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

@ModuleInfo(
   name = "IceSpeed",
   description = "Allows you to walk faster on ice.",
   category = ModuleCategory.MOVEMENT
)
public class IceSpeed extends Module {
   // $FF: synthetic field
   private final ListValue modeValue = new ListValue("Mode", new String[]{"NCP", "AAC", "Spartan"}, "NCP");

   @EventTarget
   public void onUpdate(UpdateEvent lllllIlIIllll) {
      String lllllIlIIlllI = (String)lllllIlIlIIII.modeValue.get();
      if (lllllIlIIlllI.equalsIgnoreCase("NCP")) {
         Blocks.ice.slipperiness = 0.39F;
         Blocks.packed_ice.slipperiness = 0.39F;
      } else {
         Blocks.ice.slipperiness = 0.98F;
         Blocks.packed_ice.slipperiness = 0.98F;
      }

      if (mc.thePlayer.onGround && !mc.thePlayer.isOnLadder() && !mc.thePlayer.isSneaking() && mc.thePlayer.isSprinting() && (double)mc.thePlayer.movementInput.moveForward > 0.0D) {
         EntityPlayerSP var10000;
         Material lllllIlIIlIll;
         if (lllllIlIIlllI.equalsIgnoreCase("AAC")) {
            lllllIlIIlIll = BlockUtils.getMaterial(mc.thePlayer.getPosition().down());
            if (lllllIlIIlIll == Material.ice || lllllIlIIlIll == Material.packedIce) {
               var10000 = mc.thePlayer;
               var10000.motionX *= 1.342D;
               var10000 = mc.thePlayer;
               var10000.motionZ *= 1.342D;
               Blocks.ice.slipperiness = 0.6F;
               Blocks.packed_ice.slipperiness = 0.6F;
            }
         }

         if (lllllIlIIlllI.equalsIgnoreCase("Spartan")) {
            lllllIlIIlIll = BlockUtils.getMaterial(mc.thePlayer.getPosition().down());
            if (lllllIlIIlIll == Material.ice || lllllIlIIlIll == Material.packedIce) {
               Block lllllIlIlIIlI = BlockUtils.getBlock(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY + 2.0D, mc.thePlayer.posZ));
               if (!(lllllIlIlIIlI instanceof BlockAir)) {
                  var10000 = mc.thePlayer;
                  var10000.motionX *= 1.342D;
                  var10000 = mc.thePlayer;
                  var10000.motionZ *= 1.342D;
               } else {
                  var10000 = mc.thePlayer;
                  var10000.motionX *= 1.18D;
                  var10000 = mc.thePlayer;
                  var10000.motionZ *= 1.18D;
               }

               Blocks.ice.slipperiness = 0.6F;
               Blocks.packed_ice.slipperiness = 0.6F;
            }
         }
      }

   }

   public void onEnable() {
      if (((String)lllllIlIllIIl.modeValue.get()).equalsIgnoreCase("NCP")) {
         Blocks.ice.slipperiness = 0.39F;
         Blocks.packed_ice.slipperiness = 0.39F;
      }

      super.onEnable();
   }

   public void onDisable() {
      Blocks.ice.slipperiness = 0.98F;
      Blocks.packed_ice.slipperiness = 0.98F;
      super.onDisable();
   }
}
