//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import java.util.Comparator;
import java.util.List;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiTextField;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SideOnly(Side.CLIENT)
@Mixin({GuiChat.class})
public abstract class MixinGuiChat extends MixinGuiScreen {
   // $FF: synthetic field
   private float yPosOfInputField;
   // $FF: synthetic field
   private float fade = 0.0F;
   // $FF: synthetic field
   @Shadow
   protected GuiTextField inputField;
   // $FF: synthetic field
   @Shadow
   private List<String> foundPlayerNames;
   // $FF: synthetic field
   @Shadow
   private boolean waitingOnAutocomplete;

   @Overwrite
   public void drawScreen(int llIIIIIIIIIlII, int lIlllllllllllI, float llIIIIIIIIIIlI) {
      Gui.drawRect(2, llIIIIIIIIIlIl.height - (int)llIIIIIIIIIlIl.fade, llIIIIIIIIIlIl.width - 2, llIIIIIIIIIlIl.height, Integer.MIN_VALUE);
      llIIIIIIIIIlIl.inputField.drawTextBox();
      Exception lIllllllllllIl = llIIIIIIIIIlIl.mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());
      if (lIllllllllllIl != null) {
         llIIIIIIIIIlIl.handleComponentHover(lIllllllllllIl, llIIIIIIIIIlII, lIlllllllllllI);
      }

   }

   @Inject(
      method = {"keyTyped"},
      at = {@At("RETURN")}
   )
   private void updateLength(CallbackInfo llIIIIIIlIIIll) {
      if (llIIIIIIlIIIlI.inputField.getText().startsWith(String.valueOf(LiquidBounce.commandManager.getPrefix())) && !llIIIIIIlIIIlI.inputField.getText().startsWith(String.valueOf((new StringBuilder()).append(LiquidBounce.commandManager.getPrefix()).append("lc")))) {
         llIIIIIIlIIIlI.inputField.setMaxStringLength(10000);
      } else {
         llIIIIIIlIIIlI.inputField.setMaxStringLength(100);
      }

   }

   @Inject(
      method = {"initGui"},
      at = {@At("RETURN")}
   )
   private void init(CallbackInfo llIIIIIIlIIlll) {
      llIIIIIIlIIllI.inputField.yPosition = llIIIIIIlIIllI.height + 1;
      llIIIIIIlIIllI.yPosOfInputField = (float)llIIIIIIlIIllI.inputField.yPosition;
   }

   @Inject(
      method = {"sendAutocompleteRequest"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void handleClientCommandCompletion(String llIIIIIIIlIIII, String llIIIIIIIIllll, CallbackInfo llIIIIIIIIlllI) {
      if (LiquidBounce.commandManager.autoComplete(llIIIIIIIlIIII)) {
         llIIIIIIIlIIIl.waitingOnAutocomplete = true;
         String[] llIIIIIIIlIIlI = LiquidBounce.commandManager.getLatestAutoComplete();
         if (llIIIIIIIlIIII.toLowerCase().endsWith(llIIIIIIIlIIlI[llIIIIIIIlIIlI.length - 1].toLowerCase())) {
            return;
         }

         llIIIIIIIlIIIl.onAutocompleteResponse(llIIIIIIIlIIlI);
         llIIIIIIIIlllI.cancel();
      }

   }

   @Shadow
   public abstract void onAutocompleteResponse(String[] var1);

   @Inject(
      method = {"autocompletePlayerNames"},
      at = {@At("HEAD")}
   )
   private void prioritizeClientFriends(CallbackInfo llIIIIIIIllIII) {
      llIIIIIIIlIlll.foundPlayerNames.sort(Comparator.comparing((lIlllllllllIll) -> {
         return !LiquidBounce.fileManager.friendsConfig.isFriend(lIlllllllllIll);
      }));
   }

   @Inject(
      method = {"updateScreen"},
      at = {@At("HEAD")}
   )
   private void updateScreen(CallbackInfo llIIIIIIIllllI) {
      int llIIIIIIIlllIl = RenderUtils.deltaTime;
      if (llIIIIIIIlllII.fade < 14.0F) {
         llIIIIIIIlllII.fade += 0.4F * (float)llIIIIIIIlllIl;
      }

      if (llIIIIIIIlllII.fade > 14.0F) {
         llIIIIIIIlllII.fade = 14.0F;
      }

      if (llIIIIIIIlllII.yPosOfInputField > (float)(llIIIIIIIlllII.height - 12)) {
         llIIIIIIIlllII.yPosOfInputField -= 0.4F * (float)llIIIIIIIlllIl;
      }

      if (llIIIIIIIlllII.yPosOfInputField < (float)(llIIIIIIIlllII.height - 12)) {
         llIIIIIIIlllII.yPosOfInputField = (float)(llIIIIIIIlllII.height - 12);
      }

      llIIIIIIIlllII.inputField.yPosition = (int)llIIIIIIIlllII.yPosOfInputField;
   }
}
