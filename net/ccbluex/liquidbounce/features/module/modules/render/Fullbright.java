//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.render;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.ClientShutdownEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

@ModuleInfo(
   name = "Fullbright",
   description = "Brightens up the world around you.",
   category = ModuleCategory.RENDER
)
public class Fullbright extends Module {
   // $FF: synthetic field
   private float prevGamma = -1.0F;
   // $FF: synthetic field
   private final ListValue modeValue = new ListValue("Mode", new String[]{"Gamma", "NightVision"}, "Gamma");

   public void onEnable() {
      llllllllllllllllllIIIlIlIlllllll.prevGamma = mc.gameSettings.gammaSetting;
   }

   @EventTarget(
      ignoreCondition = true
   )
   public void onUpdate(UpdateEvent llllllllllllllllllIIIlIlIlllIllI) {
      if (!llllllllllllllllllIIIlIlIlllIlll.getState() && !LiquidBounce.moduleManager.getModule(XRay.class).getState()) {
         if (llllllllllllllllllIIIlIlIlllIlll.prevGamma != -1.0F) {
            mc.gameSettings.gammaSetting = llllllllllllllllllIIIlIlIlllIlll.prevGamma;
            llllllllllllllllllIIIlIlIlllIlll.prevGamma = -1.0F;
         }
      } else {
         int llllllllllllllllllIIIlIlIlllIlII = ((String)llllllllllllllllllIIIlIlIlllIlll.modeValue.get()).toLowerCase();
         int llllllllllllllllllIIIlIlIlllIIll = -1;
         switch(llllllllllllllllllIIIlIlIlllIlII.hashCode()) {
         case -820818432:
            if (llllllllllllllllllIIIlIlIlllIlII.equals("nightvision")) {
               llllllllllllllllllIIIlIlIlllIIll = 1;
            }
            break;
         case 98120615:
            if (llllllllllllllllllIIIlIlIlllIlII.equals("gamma")) {
               llllllllllllllllllIIIlIlIlllIIll = 0;
            }
         }

         switch(llllllllllllllllllIIIlIlIlllIIll) {
         case 0:
            if (mc.gameSettings.gammaSetting <= 100.0F) {
               ++mc.gameSettings.gammaSetting;
            }
            break;
         case 1:
            mc.thePlayer.addPotionEffect(new PotionEffect(Potion.nightVision.id, 1337, 1));
         }
      }

   }

   @EventTarget(
      ignoreCondition = true
   )
   public void onShutdown(ClientShutdownEvent llllllllllllllllllIIIlIlIlllIIII) {
      llllllllllllllllllIIIlIlIlllIIIl.onDisable();
   }

   public void onDisable() {
      if (llllllllllllllllllIIIlIlIllllIll.prevGamma != -1.0F) {
         mc.gameSettings.gammaSetting = llllllllllllllllllIIIlIlIllllIll.prevGamma;
         llllllllllllllllllIIIlIlIllllIll.prevGamma = -1.0F;
         if (mc.thePlayer != null) {
            mc.thePlayer.removePotionEffectClient(Potion.nightVision.id);
         }

      }
   }
}
