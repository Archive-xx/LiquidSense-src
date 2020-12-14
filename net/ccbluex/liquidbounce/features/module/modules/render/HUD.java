//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.render;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.KeyEvent;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.ScreenEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.ui.client.hud.designer.GuiHudDesigner;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@ModuleInfo(
   name = "HUD",
   description = "Toggles visibility of the HUD.",
   category = ModuleCategory.RENDER,
   array = false
)
@SideOnly(Side.CLIENT)
public class HUD extends Module {
   // $FF: synthetic field
   public final BoolValue fontChatValue = new BoolValue("FontChat", false);
   // $FF: synthetic field
   public final BoolValue inventoryParticle = new BoolValue("InventoryParticle", false);
   // $FF: synthetic field
   private final BoolValue blurValue = new BoolValue("Blur", false);
   // $FF: synthetic field
   public final BoolValue blackHotbarValue = new BoolValue("BlackHotbar", true);

   @EventTarget
   public void onKey(KeyEvent lllllllllllllllllIlllIIllIIIlIII) {
      LiquidBounce.hud.handleKey('a', lllllllllllllllllIlllIIllIIIlIII.getKey());
   }

   public HUD() {
      lllllllllllllllllIlllIIllIIlIIIl.setState(true);
      lllllllllllllllllIlllIIllIIlIIIl.setArray(false);
   }

   @EventTarget(
      ignoreCondition = true
   )
   public void onScreen(ScreenEvent lllllllllllllllllIlllIIllIIIIlII) {
      if (mc.theWorld != null && mc.thePlayer != null) {
         if (lllllllllllllllllIlllIIllIIIIIll.getState() && (Boolean)lllllllllllllllllIlllIIllIIIIIll.blurValue.get() && !mc.entityRenderer.isShaderActive() && lllllllllllllllllIlllIIllIIIIlII.getGuiScreen() != null && !(lllllllllllllllllIlllIIllIIIIlII.getGuiScreen() instanceof GuiChat) && !(lllllllllllllllllIlllIIllIIIIlII.getGuiScreen() instanceof GuiHudDesigner)) {
            mc.entityRenderer.loadShader(new ResourceLocation(String.valueOf((new StringBuilder()).append("LiquidSense".toLowerCase()).append("/blur.json"))));
         } else if (mc.entityRenderer.getShaderGroup() != null && mc.entityRenderer.getShaderGroup().getShaderGroupName().contains("liquidbounce/blur.json")) {
            mc.entityRenderer.stopUseShader();
         }

      }
   }

   @EventTarget
   public void onUpdate(UpdateEvent lllllllllllllllllIlllIIllIIIllII) {
      LiquidBounce.hud.update();
   }

   @EventTarget
   public void onRender2D(Render2DEvent lllllllllllllllllIlllIIllIIIlllI) {
      if (!(mc.currentScreen instanceof GuiHudDesigner)) {
         LiquidBounce.hud.render(false);
      }
   }
}
