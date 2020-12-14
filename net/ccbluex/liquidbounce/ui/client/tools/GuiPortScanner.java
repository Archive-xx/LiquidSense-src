//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.ui.client.tools;

import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.TabUtils;
import net.ccbluex.liquidbounce.utils.misc.MiscUtils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;

public class GuiPortScanner extends GuiScreen {
   // $FF: synthetic field
   private GuiButton buttonToggle;
   // $FF: synthetic field
   private int currentPort;
   // $FF: synthetic field
   private int maxPort;
   // $FF: synthetic field
   private GuiTextField maxPortField;
   // $FF: synthetic field
   private final List<Integer> ports = new ArrayList();
   // $FF: synthetic field
   private int minPort;
   // $FF: synthetic field
   private boolean running;
   // $FF: synthetic field
   private GuiTextField minPortField;
   // $FF: synthetic field
   private GuiTextField hostField;
   // $FF: synthetic field
   private String host;
   // $FF: synthetic field
   private GuiTextField threadsField;
   // $FF: synthetic field
   private int checkedPort;
   // $FF: synthetic field
   private final GuiScreen prevGui;
   // $FF: synthetic field
   private String status = "§7Wating...";

   public GuiPortScanner(GuiScreen lllllllIllIIII) {
      lllllllIllIIIl.prevGui = lllllllIllIIII;
   }

   protected void keyTyped(char llllllIlllIIll, int llllllIlllIIlI) throws IOException {
      if (1 == llllllIlllIIlI) {
         llllllIlllIlll.mc.displayGuiScreen(llllllIlllIlll.prevGui);
      } else {
         if (15 == llllllIlllIIlI) {
            TabUtils.tab(llllllIlllIlll.hostField, llllllIlllIlll.minPortField, llllllIlllIlll.maxPortField);
         }

         if (!llllllIlllIlll.running) {
            boolean var10001;
            if (llllllIlllIlll.hostField.isFocused()) {
               llllllIlllIlll.hostField.textboxKeyTyped(llllllIlllIIll, llllllIlllIIlI);
               var10001 = false;
            }

            if (llllllIlllIlll.minPortField.isFocused() && !Character.isLetter(llllllIlllIIll)) {
               llllllIlllIlll.minPortField.textboxKeyTyped(llllllIlllIIll, llllllIlllIIlI);
               var10001 = false;
            }

            if (llllllIlllIlll.maxPortField.isFocused() && !Character.isLetter(llllllIlllIIll)) {
               llllllIlllIlll.maxPortField.textboxKeyTyped(llllllIlllIIll, llllllIlllIIlI);
               var10001 = false;
            }

            if (llllllIlllIlll.threadsField.isFocused() && !Character.isLetter(llllllIlllIIll)) {
               llllllIlllIlll.threadsField.textboxKeyTyped(llllllIlllIIll, llllllIlllIIlI);
               var10001 = false;
            }

            super.keyTyped(llllllIlllIIll, llllllIlllIIlI);
         }
      }
   }

   protected void mouseClicked(int llllllIllIlIII, int llllllIllIIlll, int llllllIllIIllI) throws IOException {
      llllllIllIlIIl.hostField.mouseClicked(llllllIllIlIII, llllllIllIIlll, llllllIllIIllI);
      llllllIllIlIIl.minPortField.mouseClicked(llllllIllIlIII, llllllIllIIlll, llllllIllIIllI);
      llllllIllIlIIl.maxPortField.mouseClicked(llllllIllIlIII, llllllIllIIlll, llllllIllIIllI);
      llllllIllIlIIl.threadsField.mouseClicked(llllllIllIlIII, llllllIllIIlll, llllllIllIIllI);
      super.mouseClicked(llllllIllIlIII, llllllIllIIlll, llllllIllIIllI);
   }

   public void onGuiClosed() {
      Keyboard.enableRepeatEvents(false);
      llllllIllIIIIl.running = false;
      super.onGuiClosed();
   }

   public void initGui() {
      Keyboard.enableRepeatEvents(true);
      lllllllIlIlIll.hostField = new GuiTextField(0, Fonts.font40, lllllllIlIlIll.width / 2 - 100, 60, 200, 20);
      lllllllIlIlIll.hostField.setFocused(true);
      lllllllIlIlIll.hostField.setMaxStringLength(Integer.MAX_VALUE);
      lllllllIlIlIll.hostField.setText("localhost");
      lllllllIlIlIll.minPortField = new GuiTextField(1, Fonts.font40, lllllllIlIlIll.width / 2 - 100, 90, 90, 20);
      lllllllIlIlIll.minPortField.setMaxStringLength(5);
      lllllllIlIlIll.minPortField.setText(String.valueOf(1));
      lllllllIlIlIll.maxPortField = new GuiTextField(2, Fonts.font40, lllllllIlIlIll.width / 2 + 10, 90, 90, 20);
      lllllllIlIlIll.maxPortField.setMaxStringLength(5);
      lllllllIlIlIll.maxPortField.setText(String.valueOf(65535));
      lllllllIlIlIll.threadsField = new GuiTextField(3, Fonts.font40, lllllllIlIlIll.width / 2 - 100, 120, 200, 20);
      lllllllIlIlIll.threadsField.setMaxStringLength(Integer.MAX_VALUE);
      lllllllIlIlIll.threadsField.setText(String.valueOf(500));
      lllllllIlIlIll.buttonList.add(lllllllIlIlIll.buttonToggle = new GuiButton(1, lllllllIlIlIll.width / 2 - 100, lllllllIlIlIll.height / 4 + 95, lllllllIlIlIll.running ? "Stop" : "Start"));
      boolean var10001 = false;
      lllllllIlIlIll.buttonList.add(new GuiButton(0, lllllllIlIlIll.width / 2 - 100, lllllllIlIlIll.height / 4 + 120, "Back"));
      var10001 = false;
      lllllllIlIlIll.buttonList.add(new GuiButton(2, lllllllIlIlIll.width / 2 - 100, lllllllIlIlIll.height / 4 + 155, "Export"));
      var10001 = false;
      super.initGui();
   }

   public void updateScreen() {
      llllllIllIIIll.hostField.updateCursorCounter();
      llllllIllIIIll.minPortField.updateCursorCounter();
      llllllIllIIIll.maxPortField.updateCursorCounter();
      llllllIllIIIll.threadsField.updateCursorCounter();
      super.updateScreen();
   }

   protected void actionPerformed(GuiButton lllllllIIIIIIl) throws IOException {
      switch(lllllllIIIIIIl.id) {
      case 0:
         lllllllIIIIIII.mc.displayGuiScreen(lllllllIIIIIII.prevGui);
         break;
      case 1:
         if (lllllllIIIIIII.running) {
            lllllllIIIIIII.running = false;
         } else {
            lllllllIIIIIII.host = lllllllIIIIIII.hostField.getText();
            if (lllllllIIIIIII.host.isEmpty()) {
               lllllllIIIIIII.status = "§cInvalid host";
               return;
            }

            try {
               lllllllIIIIIII.minPort = Integer.parseInt(lllllllIIIIIII.minPortField.getText());
            } catch (NumberFormatException var8) {
               lllllllIIIIIII.status = "§cInvalid min port";
               return;
            }

            try {
               lllllllIIIIIII.maxPort = Integer.parseInt(lllllllIIIIIII.maxPortField.getText());
            } catch (NumberFormatException var7) {
               lllllllIIIIIII.status = "§cInvalid max port";
               return;
            }

            int lllllllIIIIlll;
            try {
               lllllllIIIIlll = Integer.parseInt(lllllllIIIIIII.threadsField.getText());
            } catch (NumberFormatException var6) {
               lllllllIIIIIII.status = "§cInvalid threads";
               return;
            }

            lllllllIIIIIII.ports.clear();
            lllllllIIIIIII.currentPort = lllllllIIIIIII.minPort - 1;
            lllllllIIIIIII.checkedPort = lllllllIIIIIII.minPort;

            for(int llllllIlllllIl = 0; llllllIlllllIl < lllllllIIIIlll; ++llllllIlllllIl) {
               (new Thread(() -> {
                  while(true) {
                     try {
                        if (llllllIlIlIlll.running && llllllIlIlIlll.currentPort < llllllIlIlIlll.maxPort) {
                           ++llllllIlIlIlll.currentPort;
                           int llllllIlIlIlIl = llllllIlIlIlll.currentPort;

                           try {
                              Socket llllllIlIllIlI = new Socket();
                              llllllIlIllIlI.connect(new InetSocketAddress(llllllIlIlIlll.host, llllllIlIlIlIl), 500);
                              llllllIlIllIlI.close();
                              synchronized(llllllIlIlIlll.ports) {
                                 if (!llllllIlIlIlll.ports.contains(llllllIlIlIlIl)) {
                                    llllllIlIlIlll.ports.add(llllllIlIlIlIl);
                                    boolean var10001 = false;
                                 }
                              }
                           } catch (Exception var6) {
                           }

                           if (llllllIlIlIlll.checkedPort < llllllIlIlIlIl) {
                              llllllIlIlIlll.checkedPort = llllllIlIlIlIl;
                           }
                           continue;
                        }

                        llllllIlIlIlll.running = false;
                        llllllIlIlIlll.buttonToggle.displayString = "Start";
                     } catch (Exception var7) {
                        llllllIlIlIlll.status = String.valueOf((new StringBuilder()).append("§a§l").append(var7.getClass().getSimpleName()).append(": §c").append(var7.getMessage()));
                     }

                     return;
                  }
               })).start();
            }

            lllllllIIIIIII.running = true;
         }

         lllllllIIIIIII.buttonToggle.displayString = lllllllIIIIIII.running ? "Stop" : "Start";
         break;
      case 2:
         File lllllllIIIIIll = MiscUtils.saveFileChooser();
         if (lllllllIIIIIll == null || lllllllIIIIIll.isDirectory()) {
            return;
         }

         try {
            if (!lllllllIIIIIll.exists()) {
               lllllllIIIIIll.createNewFile();
               boolean var10001 = false;
            }

            FileWriter lllllllIIIIlIl = new FileWriter(lllllllIIIIIll);
            lllllllIIIIlIl.write("Portscan\r\n");
            lllllllIIIIlIl.write(String.valueOf((new StringBuilder()).append("Host: ").append(lllllllIIIIIII.host).append("\r\n\r\n")));
            lllllllIIIIlIl.write(String.valueOf((new StringBuilder()).append("Ports (").append(lllllllIIIIIII.minPort).append(" - ").append(lllllllIIIIIII.maxPort).append("):\r\n")));
            Iterator llllllIlllllII = lllllllIIIIIII.ports.iterator();

            while(llllllIlllllII.hasNext()) {
               int llllllIllllIll = (Integer)llllllIlllllII.next();
               lllllllIIIIlIl.write(String.valueOf((new StringBuilder()).append(llllllIllllIll).append("\r\n")));
            }

            lllllllIIIIlIl.flush();
            lllllllIIIIlIl.close();
            JOptionPane.showMessageDialog((Component)null, "Exported successful!", "Port Scanner", 1);
         } catch (Exception var9) {
            var9.printStackTrace();
            MiscUtils.showErrorPopup("Error", String.valueOf((new StringBuilder()).append("Exception class: ").append(var9.getClass().getName()).append("\nMessage: ").append(var9.getMessage())));
         }
      }

      super.actionPerformed(lllllllIIIIIIl);
   }

   public void drawScreen(int lllllllIIllllI, int lllllllIIllIIl, float lllllllIIlllII) {
      lllllllIIllIll.drawBackground(0);
      lllllllIIllIll.drawCenteredString(Fonts.font40, "Port Scanner", lllllllIIllIll.width / 2, 34, 16777215);
      lllllllIIllIll.drawCenteredString(Fonts.font35, lllllllIIllIll.running ? String.valueOf((new StringBuilder()).append("§7").append(lllllllIIllIll.checkedPort).append(" §8/ §7").append(lllllllIIllIll.maxPort)) : (lllllllIIllIll.status == null ? "" : lllllllIIllIll.status), lllllllIIllIll.width / 2, lllllllIIllIll.height / 4 + 80, 16777215);
      lllllllIIllIll.buttonToggle.displayString = lllllllIIllIll.running ? "Stop" : "Start";
      lllllllIIllIll.hostField.drawTextBox();
      lllllllIIllIll.minPortField.drawTextBox();
      lllllllIIllIll.maxPortField.drawTextBox();
      lllllllIIllIll.threadsField.drawTextBox();
      lllllllIIllIll.drawString(Fonts.font40, "§c§lPorts:", 2, 2, Color.WHITE.hashCode());
      synchronized(lllllllIIllIll.ports) {
         int lllllllIIlIllI = 12;
         Iterator lllllllIIlIlIl = lllllllIIllIll.ports.iterator();

         while(true) {
            if (!lllllllIIlIlIl.hasNext()) {
               break;
            }

            Integer lllllllIlIIIIl = (Integer)lllllllIIlIlIl.next();
            lllllllIIllIll.drawString(Fonts.font35, String.valueOf(lllllllIlIIIIl), 2, lllllllIIlIllI, Color.WHITE.hashCode());
            lllllllIIlIllI += Fonts.font35.FONT_HEIGHT;
         }
      }

      super.drawScreen(lllllllIIllllI, lllllllIIllIIl, lllllllIIlllII);
   }
}
