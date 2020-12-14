//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement;

import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.block.BlockPane;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.BlockPos;

@ModuleInfo(
   name = "HighJump",
   description = "Allows you to jump higher.",
   category = ModuleCategory.MOVEMENT
)
public class HighJump extends Module {
   // $FF: synthetic field
   private final ListValue modeValue = new ListValue("Mode", new String[]{"Vanilla", "Damage", "AACv3", "DAC", "Mineplex"}, "Vanilla");
   // $FF: synthetic field
   private final FloatValue heightValue = new FloatValue("Height", 2.0F, 1.1F, 5.0F);
   // $FF: synthetic field
   private final BoolValue glassValue = new BoolValue("OnlyGlassPane", false);

   @EventTarget
   public void onJump(JumpEvent lIlIIIIllIllI) {
      if (!(Boolean)lIlIIIIllIlll.glassValue.get() || BlockUtils.getBlock(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ)) instanceof BlockPane) {
         double lIlIIIIllIIll = ((String)lIlIIIIllIlll.modeValue.get()).toLowerCase();
         Exception lIlIIIIllIIlI = -1;
         switch(lIlIIIIllIIll.hashCode()) {
         case -1362669950:
            if (lIlIIIIllIIll.equals("mineplex")) {
               lIlIIIIllIIlI = 1;
            }
            break;
         case 233102203:
            if (lIlIIIIllIIll.equals("vanilla")) {
               lIlIIIIllIIlI = 0;
            }
         }

         switch(lIlIIIIllIIlI) {
         case 0:
            lIlIIIIllIllI.setMotion(lIlIIIIllIllI.getMotion() * (Float)lIlIIIIllIlll.heightValue.get());
            break;
         case 1:
            lIlIIIIllIllI.setMotion(0.47F);
         }

      }
   }

   public String getTag() {
      return (String)lIlIIIIllIIII.modeValue.get();
   }

   @EventTarget
   public void onUpdate(UpdateEvent lIlIIIlIIIIll) {
      if (!(Boolean)lIlIIIlIIIIlI.glassValue.get() || BlockUtils.getBlock(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ)) instanceof BlockPane) {
         double lIlIIIlIIIIIl = ((String)lIlIIIlIIIIlI.modeValue.get()).toLowerCase();
         float lIlIIIlIIIIII = -1;
         switch(lIlIIIlIIIIIl.hashCode()) {
         case -1362669950:
            if (lIlIIIlIIIIIl.equals("mineplex")) {
               lIlIIIlIIIIII = 3;
            }
            break;
         case -1339126929:
            if (lIlIIIlIIIIIl.equals("damage")) {
               lIlIIIlIIIIII = 0;
            }
            break;
         case 99206:
            if (lIlIIIlIIIIIl.equals("dac")) {
               lIlIIIlIIIIII = 2;
            }
            break;
         case 92570112:
            if (lIlIIIlIIIIIl.equals("aacv3")) {
               lIlIIIlIIIIII = 1;
            }
         }

         EntityPlayerSP var10000;
         switch(lIlIIIlIIIIII) {
         case 0:
            if (mc.thePlayer.hurtTime > 0 && mc.thePlayer.onGround) {
               var10000 = mc.thePlayer;
               var10000.motionY += (double)(0.42F * (Float)lIlIIIlIIIIlI.heightValue.get());
            }
            break;
         case 1:
            if (!mc.thePlayer.onGround) {
               var10000 = mc.thePlayer;
               var10000.motionY += 0.059D;
            }
            break;
         case 2:
            if (!mc.thePlayer.onGround) {
               var10000 = mc.thePlayer;
               var10000.motionY += 0.049999D;
            }
            break;
         case 3:
            if (!mc.thePlayer.onGround) {
               MovementUtils.strafe(0.35F);
            }
         }

      }
   }

   @EventTarget
   public void onMove(MoveEvent lIlIIIIllllIl) {
      if (!(Boolean)lIlIIIIllllII.glassValue.get() || BlockUtils.getBlock(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ)) instanceof BlockPane) {
         if (!mc.thePlayer.onGround && "mineplex".equals(((String)lIlIIIIllllII.modeValue.get()).toLowerCase())) {
            EntityPlayerSP var10000 = mc.thePlayer;
            var10000.motionY += mc.thePlayer.fallDistance == 0.0F ? 0.0499D : 0.05D;
         }

      }
   }
}
