//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.combat.Criticals;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.minecraft.potion.Potion;

@ModuleInfo(
   name = "Sprint",
   description = "Automatically sprints all the time.",
   category = ModuleCategory.MOVEMENT
)
public class Sprint extends Module {
   // $FF: synthetic field
   public final BoolValue checkServerSide = new BoolValue("CheckServerSide", false);
   // $FF: synthetic field
   public final BoolValue foodValue = new BoolValue("Food", true);
   // $FF: synthetic field
   public final BoolValue blindnessValue = new BoolValue("Blindness", true);
   // $FF: synthetic field
   public final BoolValue allDirectionsValue = new BoolValue("AllDirections", true);
   // $FF: synthetic field
   public final BoolValue checkServerSideGround = new BoolValue("CheckServerSideOnlyGround", false);

   @EventTarget
   public void onUpdate(UpdateEvent llIlIIIIIIIIlll) {
      if (!MovementUtils.isMoving() || mc.thePlayer.isSneaking() || (Boolean)llIlIIIIIIIIllI.blindnessValue.get() && mc.thePlayer.isPotionActive(Potion.blindness) || (Boolean)llIlIIIIIIIIllI.foodValue.get() && !((float)mc.thePlayer.getFoodStats().getFoodLevel() > 6.0F) && !mc.thePlayer.capabilities.allowFlying || (Boolean)llIlIIIIIIIIllI.checkServerSide.get() && (mc.thePlayer.onGround || !(Boolean)llIlIIIIIIIIllI.checkServerSideGround.get()) && !(Boolean)llIlIIIIIIIIllI.allDirectionsValue.get() && RotationUtils.targetRotation != null && RotationUtils.getRotationDifference(new Rotation(mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch)) > 30.0D) {
         mc.thePlayer.setSprinting(false);
      } else {
         if ((Boolean)llIlIIIIIIIIllI.allDirectionsValue.get() || mc.thePlayer.movementInput.moveForward >= 0.8F) {
            Criticals llIlIIIIIIIlIIl = (Criticals)LiquidBounce.moduleManager.getModule(Criticals.class);
            if (!llIlIIIIIIIlIIl.getState() || !((String)llIlIIIIIIIlIIl.getModeValue().get()).equals("HYTJump")) {
               mc.thePlayer.setSprinting(true);
            }
         }

      }
   }
}
