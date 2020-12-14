//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import net.ccbluex.liquidbounce.utils.ServerUtils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GuiIngameMenu.class})
public abstract class MixinGuiIngameMenu extends MixinGuiScreen {
   @Inject(
      method = {"actionPerformed"},
      at = {@At("HEAD")}
   )
   private void actionPerformed(GuiButton lIlIlIlIIlllI, CallbackInfo lIlIlIlIlIIII) {
      if (lIlIlIlIIlllI.id == 1337) {
         lIlIlIlIIllll.mc.theWorld.sendQuittingDisconnectingPacket();
         ServerUtils.connectToLastServer();
      }

   }

   @Inject(
      method = {"initGui"},
      at = {@At("RETURN")}
   )
   private void initGui(CallbackInfo lIlIlIlIlIllI) {
      if (!lIlIlIlIlIlIl.mc.isIntegratedServerRunning()) {
         lIlIlIlIlIlIl.buttonList.add(new GuiButton(1337, lIlIlIlIlIlIl.width / 2 - 100, lIlIlIlIlIlIl.height / 4 + 128, "Reconnect"));
         boolean var10001 = false;
      }

   }
}
