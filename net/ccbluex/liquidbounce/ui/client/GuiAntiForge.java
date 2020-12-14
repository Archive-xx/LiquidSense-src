//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.ui.client;

import java.io.IOException;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.special.AntiForge;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiAntiForge extends GuiScreen {
   // $FF: synthetic field
   private GuiButton proxyButton;
   // $FF: synthetic field
   private GuiButton enabledButton;
   // $FF: synthetic field
   private GuiButton fmlButton;
   // $FF: synthetic field
   private final GuiScreen prevGui;
   // $FF: synthetic field
   private GuiButton payloadButton;

   public void drawScreen(int lllllllllllllllllIllIIIIIllIIlII, int lllllllllllllllllIllIIIIIllIIIll, float lllllllllllllllllIllIIIIIllIIllI) {
      lllllllllllllllllIllIIIIIllIIlIl.drawBackground(0);
      Fonts.fontBold180.drawCenteredString("AntiForge", (float)((int)((float)lllllllllllllllllIllIIIIIllIIlIl.width / 2.0F)), (float)((int)((float)lllllllllllllllllIllIIIIIllIIlIl.height / 8.0F + 5.0F)), 4673984, true);
      boolean var10001 = false;
      super.drawScreen(lllllllllllllllllIllIIIIIllIIlII, lllllllllllllllllIllIIIIIllIIIll, lllllllllllllllllIllIIIIIllIIllI);
   }

   public void initGui() {
      lllllllllllllllllIllIIIIIllllIII.buttonList.add(lllllllllllllllllIllIIIIIllllIII.enabledButton = new GuiButton(1, lllllllllllllllllIllIIIIIllllIII.width / 2 - 100, lllllllllllllllllIllIIIIIllllIII.height / 4 + 35, String.valueOf((new StringBuilder()).append("Enabled (").append(AntiForge.enabled ? "On" : "Off").append(")"))));
      boolean var10001 = false;
      lllllllllllllllllIllIIIIIllllIII.buttonList.add(lllllllllllllllllIllIIIIIllllIII.fmlButton = new GuiButton(2, lllllllllllllllllIllIIIIIllllIII.width / 2 - 100, lllllllllllllllllIllIIIIIllllIII.height / 4 + 50 + 25, String.valueOf((new StringBuilder()).append("Block FML (").append(AntiForge.blockFML ? "On" : "Off").append(")"))));
      var10001 = false;
      lllllllllllllllllIllIIIIIllllIII.buttonList.add(lllllllllllllllllIllIIIIIllllIII.proxyButton = new GuiButton(3, lllllllllllllllllIllIIIIIllllIII.width / 2 - 100, lllllllllllllllllIllIIIIIllllIII.height / 4 + 50 + 50, String.valueOf((new StringBuilder()).append("Block FML Proxy Packet (").append(AntiForge.blockProxyPacket ? "On" : "Off").append(")"))));
      var10001 = false;
      lllllllllllllllllIllIIIIIllllIII.buttonList.add(lllllllllllllllllIllIIIIIllllIII.payloadButton = new GuiButton(4, lllllllllllllllllIllIIIIIllllIII.width / 2 - 100, lllllllllllllllllIllIIIIIllllIII.height / 4 + 50 + 75, String.valueOf((new StringBuilder()).append("Block Payload Packets (").append(AntiForge.blockPayloadPackets ? "On" : "Off").append(")"))));
      var10001 = false;
      lllllllllllllllllIllIIIIIllllIII.buttonList.add(new GuiButton(0, lllllllllllllllllIllIIIIIllllIII.width / 2 - 100, lllllllllllllllllIllIIIIIllllIII.height / 4 + 55 + 100 + 5, "Back"));
      var10001 = false;
   }

   public GuiAntiForge(GuiScreen lllllllllllllllllIllIIIIlIIllIII) {
      lllllllllllllllllIllIIIIlIIllIll.prevGui = lllllllllllllllllIllIIIIlIIllIII;
   }

   protected void keyTyped(char lllllllllllllllllIllIIIIIlIIlllI, int lllllllllllllllllIllIIIIIlIIllII) throws IOException {
      if (1 == lllllllllllllllllIllIIIIIlIIllII) {
         lllllllllllllllllIllIIIIIlIlIIII.mc.displayGuiScreen(lllllllllllllllllIllIIIIIlIlIIII.prevGui);
      } else {
         super.keyTyped(lllllllllllllllllIllIIIIIlIIlllI, lllllllllllllllllIllIIIIIlIIllII);
      }
   }

   protected void actionPerformed(GuiButton lllllllllllllllllIllIIIIIllIlllI) {
      switch(lllllllllllllllllIllIIIIIllIlllI.id) {
      case 0:
         lllllllllllllllllIllIIIIIlllIIIl.mc.displayGuiScreen(lllllllllllllllllIllIIIIIlllIIIl.prevGui);
         break;
      case 1:
         AntiForge.enabled = !AntiForge.enabled;
         lllllllllllllllllIllIIIIIlllIIIl.enabledButton.displayString = String.valueOf((new StringBuilder()).append("Enabled (").append(AntiForge.enabled ? "On" : "Off").append(")"));
         LiquidBounce.fileManager.saveConfig(LiquidBounce.fileManager.valuesConfig);
         break;
      case 2:
         AntiForge.blockFML = !AntiForge.blockFML;
         lllllllllllllllllIllIIIIIlllIIIl.fmlButton.displayString = String.valueOf((new StringBuilder()).append("Block FML (").append(AntiForge.blockFML ? "On" : "Off").append(")"));
         LiquidBounce.fileManager.saveConfig(LiquidBounce.fileManager.valuesConfig);
         break;
      case 3:
         AntiForge.blockProxyPacket = !AntiForge.blockProxyPacket;
         lllllllllllllllllIllIIIIIlllIIIl.proxyButton.displayString = String.valueOf((new StringBuilder()).append("Block FML Proxy Packet (").append(AntiForge.blockProxyPacket ? "On" : "Off").append(")"));
         LiquidBounce.fileManager.saveConfig(LiquidBounce.fileManager.valuesConfig);
         break;
      case 4:
         AntiForge.blockPayloadPackets = !AntiForge.blockPayloadPackets;
         lllllllllllllllllIllIIIIIlllIIIl.payloadButton.displayString = String.valueOf((new StringBuilder()).append("Block Payload Packets (").append(AntiForge.blockPayloadPackets ? "On" : "Off").append(")"));
         LiquidBounce.fileManager.saveConfig(LiquidBounce.fileManager.valuesConfig);
      }

   }
}
