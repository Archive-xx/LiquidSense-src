//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.special.BungeeCordSpoof;
import net.ccbluex.liquidbounce.ui.client.GuiAntiForge;
import net.ccbluex.liquidbounce.ui.client.tools.GuiTools;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GuiMultiplayer.class})
public abstract class MixinGuiMultiplayer extends MixinGuiScreen {
   // $FF: synthetic field
   private GuiButton bungeeCordSpoofButton;

   @Inject(
      method = {"actionPerformed"},
      at = {@At("HEAD")}
   )
   private void actionPerformed(GuiButton llllllllllllllllllllllllIIIIlIIl, CallbackInfo llllllllllllllllllllllllIIIIllII) {
      switch(llllllllllllllllllllllllIIIIlIIl.id) {
      case 997:
         llllllllllllllllllllllllIIIIlIll.mc.displayGuiScreen(new GuiAntiForge((GuiScreen)llllllllllllllllllllllllIIIIlIll));
         break;
      case 998:
         BungeeCordSpoof.enabled = !BungeeCordSpoof.enabled;
         llllllllllllllllllllllllIIIIlIll.bungeeCordSpoofButton.displayString = String.valueOf((new StringBuilder()).append("BungeeCord Spoof: ").append(BungeeCordSpoof.enabled ? "On" : "Off"));
         LiquidBounce.fileManager.saveConfig(LiquidBounce.fileManager.valuesConfig);
         break;
      case 999:
         llllllllllllllllllllllllIIIIlIll.mc.displayGuiScreen(new GuiTools((GuiScreen)llllllllllllllllllllllllIIIIlIll));
      }

   }

   @Inject(
      method = {"initGui"},
      at = {@At("RETURN")}
   )
   private void initGui(CallbackInfo llllllllllllllllllllllllIIlIIlII) {
      llllllllllllllllllllllllIIlIIlIl.buttonList.add(new GuiButton(997, 5, 8, 98, 20, "AntiForge"));
      boolean var10001 = false;
      llllllllllllllllllllllllIIlIIlIl.buttonList.add(llllllllllllllllllllllllIIlIIlIl.bungeeCordSpoofButton = new GuiButton(998, 108, 8, 98, 20, String.valueOf((new StringBuilder()).append("BungeeCord Spoof: ").append(BungeeCordSpoof.enabled ? "On" : "Off"))));
      var10001 = false;
      llllllllllllllllllllllllIIlIIlIl.buttonList.add(new GuiButton(999, llllllllllllllllllllllllIIlIIlIl.width - 104, 8, 98, 20, "Tools"));
      var10001 = false;
   }
}
