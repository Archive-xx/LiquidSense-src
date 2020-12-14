//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import java.awt.Color;
import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.ClickEvent.Action;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GuiEditSign.class})
public class MixinGuiEditSign extends GuiScreen {
   // $FF: synthetic field
   private GuiButton toggleButton;
   // $FF: synthetic field
   private GuiTextField signCommand4;
   // $FF: synthetic field
   @Shadow
   private TileEntitySign tileSign;
   // $FF: synthetic field
   private GuiTextField signCommand1;
   // $FF: synthetic field
   @Shadow
   private GuiButton doneBtn;
   // $FF: synthetic field
   private boolean enabled;
   // $FF: synthetic field
   private GuiTextField signCommand2;
   // $FF: synthetic field
   private GuiTextField signCommand3;
   // $FF: synthetic field
   @Shadow
   private int editLine;

   @Inject(
      method = {"initGui"},
      at = {@At("RETURN")}
   )
   private void initGui(CallbackInfo lllIllIllIlIIII) {
      lllIllIllIIllll.buttonList.add(lllIllIllIIllll.toggleButton = new GuiButton(1, lllIllIllIIllll.width / 2 - 100, lllIllIllIIllll.height / 4 + 145, lllIllIllIIllll.enabled ? "Disable Formatting codes" : "Enable Formatting codes"));
      boolean var10001 = false;
      lllIllIllIIllll.signCommand1 = new GuiTextField(0, lllIllIllIIllll.fontRendererObj, lllIllIllIIllll.width / 2 - 100, lllIllIllIIllll.height - 15, 200, 10);
      lllIllIllIIllll.signCommand2 = new GuiTextField(1, lllIllIllIIllll.fontRendererObj, lllIllIllIIllll.width / 2 - 100, lllIllIllIIllll.height - 30, 200, 10);
      lllIllIllIIllll.signCommand3 = new GuiTextField(2, lllIllIllIIllll.fontRendererObj, lllIllIllIIllll.width / 2 - 100, lllIllIllIIllll.height - 45, 200, 10);
      lllIllIllIIllll.signCommand4 = new GuiTextField(3, lllIllIllIIllll.fontRendererObj, lllIllIllIIllll.width / 2 - 100, lllIllIllIIllll.height - 60, 200, 10);
      lllIllIllIIllll.signCommand1.setText("");
      lllIllIllIIllll.signCommand2.setText("");
      lllIllIllIIllll.signCommand3.setText("");
      lllIllIllIIllll.signCommand4.setText("");
   }

   @Overwrite
   protected void keyTyped(char lllIllIlIllIIlI, int lllIllIlIlIllIl) throws IOException {
      lllIllIlIlIllll.signCommand1.textboxKeyTyped(lllIllIlIllIIlI, lllIllIlIlIllIl);
      boolean var10001 = false;
      lllIllIlIlIllll.signCommand2.textboxKeyTyped(lllIllIlIllIIlI, lllIllIlIlIllIl);
      var10001 = false;
      lllIllIlIlIllll.signCommand3.textboxKeyTyped(lllIllIlIllIIlI, lllIllIlIlIllIl);
      var10001 = false;
      lllIllIlIlIllll.signCommand4.textboxKeyTyped(lllIllIlIllIIlI, lllIllIlIlIllIl);
      var10001 = false;
      if (!lllIllIlIlIllll.signCommand1.isFocused() && !lllIllIlIlIllll.signCommand2.isFocused() && !lllIllIlIlIllll.signCommand3.isFocused() && !lllIllIlIlIllll.signCommand4.isFocused()) {
         if (lllIllIlIlIllIl == 200) {
            lllIllIlIlIllll.editLine = lllIllIlIlIllll.editLine - 1 & 3;
         }

         if (lllIllIlIlIllIl == 208 || lllIllIlIlIllIl == 28 || lllIllIlIlIllIl == 156) {
            lllIllIlIlIllll.editLine = lllIllIlIlIllll.editLine + 1 & 3;
         }

         Exception lllIllIlIlIllII = lllIllIlIlIllll.tileSign.signText[lllIllIlIlIllll.editLine].getUnformattedText();
         if (lllIllIlIlIllIl == 14 && lllIllIlIlIllII.length() > 0) {
            lllIllIlIlIllII = lllIllIlIlIllII.substring(0, lllIllIlIlIllII.length() - 1);
         }

         if ((ChatAllowedCharacters.isAllowedCharacter(lllIllIlIllIIlI) || lllIllIlIlIllll.enabled && lllIllIlIllIIlI == 167) && lllIllIlIlIllll.fontRendererObj.getStringWidth(String.valueOf((new StringBuilder()).append(lllIllIlIlIllII).append(lllIllIlIllIIlI))) <= 90) {
            lllIllIlIlIllII = String.valueOf((new StringBuilder()).append(lllIllIlIlIllII).append(lllIllIlIllIIlI));
         }

         lllIllIlIlIllll.tileSign.signText[lllIllIlIlIllll.editLine] = new ChatComponentText(lllIllIlIlIllII);
         if (lllIllIlIlIllIl == 1) {
            lllIllIlIlIllll.actionPerformed(lllIllIlIlIllll.doneBtn);
         }

      }
   }

   protected void mouseClicked(int lllIllIlIlllIlI, int lllIllIlIllllIl, int lllIllIlIlllIII) throws IOException {
      lllIllIlIlllIll.signCommand1.mouseClicked(lllIllIlIlllIlI, lllIllIlIllllIl, lllIllIlIlllIII);
      lllIllIlIlllIll.signCommand2.mouseClicked(lllIllIlIlllIlI, lllIllIlIllllIl, lllIllIlIlllIII);
      lllIllIlIlllIll.signCommand3.mouseClicked(lllIllIlIlllIlI, lllIllIlIllllIl, lllIllIlIlllIII);
      lllIllIlIlllIll.signCommand4.mouseClicked(lllIllIlIlllIlI, lllIllIlIllllIl, lllIllIlIlllIII);
      super.mouseClicked(lllIllIlIlllIlI, lllIllIlIllllIl, lllIllIlIlllIII);
   }

   @Inject(
      method = {"drawScreen"},
      at = {@At("RETURN")}
   )
   private void drawFields(CallbackInfo lllIllIllIIIlIl) {
      lllIllIllIIIllI.fontRendererObj.drawString("§c§lCommands §7(§f§l1.8§7)", lllIllIllIIIllI.width / 2 - 100, lllIllIllIIIllI.height - 75, Color.WHITE.getRGB());
      boolean var10001 = false;
      lllIllIllIIIllI.signCommand1.drawTextBox();
      lllIllIllIIIllI.signCommand2.drawTextBox();
      lllIllIllIIIllI.signCommand3.drawTextBox();
      lllIllIllIIIllI.signCommand4.drawTextBox();
   }

   @Inject(
      method = {"actionPerformed"},
      at = {@At("HEAD")}
   )
   private void actionPerformed(GuiButton lllIllIllIIlIll, CallbackInfo lllIllIllIIlIlI) {
      switch(lllIllIllIIlIll.id) {
      case 0:
         boolean var10001;
         if (!lllIllIllIIlIIl.signCommand1.getText().isEmpty()) {
            lllIllIllIIlIIl.tileSign.signText[0].setChatStyle((new ChatStyle()).setChatClickEvent(new ClickEvent(Action.RUN_COMMAND, lllIllIllIIlIIl.signCommand1.getText())));
            var10001 = false;
         }

         if (!lllIllIllIIlIIl.signCommand2.getText().isEmpty()) {
            lllIllIllIIlIIl.tileSign.signText[1].setChatStyle((new ChatStyle()).setChatClickEvent(new ClickEvent(Action.RUN_COMMAND, lllIllIllIIlIIl.signCommand2.getText())));
            var10001 = false;
         }

         if (!lllIllIllIIlIIl.signCommand3.getText().isEmpty()) {
            lllIllIllIIlIIl.tileSign.signText[2].setChatStyle((new ChatStyle()).setChatClickEvent(new ClickEvent(Action.RUN_COMMAND, lllIllIllIIlIIl.signCommand3.getText())));
            var10001 = false;
         }

         if (!lllIllIllIIlIIl.signCommand4.getText().isEmpty()) {
            lllIllIllIIlIIl.tileSign.signText[3].setChatStyle((new ChatStyle()).setChatClickEvent(new ClickEvent(Action.RUN_COMMAND, lllIllIllIIlIIl.signCommand4.getText())));
            var10001 = false;
         }
         break;
      case 1:
         lllIllIllIIlIIl.enabled = !lllIllIllIIlIIl.enabled;
         lllIllIllIIlIIl.toggleButton.displayString = lllIllIllIIlIIl.enabled ? "Disable Formatting codes" : "Enable Formatting codes";
      }

   }
}
