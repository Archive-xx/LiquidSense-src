//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.utils.misc;

import java.awt.Component;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;

public final class MiscUtils extends MinecraftInstance {
   public static void showErrorPopup(String lllllllllllllllllIllIIllIllIIIll, String lllllllllllllllllIllIIllIllIIIlI) {
      JOptionPane.showMessageDialog((Component)null, lllllllllllllllllIllIIllIllIIIlI, lllllllllllllllllIllIIllIllIIIll, 0);
   }

   public static File openFileChooser() {
      if (mc.isFullScreen()) {
         mc.toggleFullscreen();
      }

      long lllllllllllllllllIllIIllIlIlIIll = new JFileChooser();
      byte lllllllllllllllllIllIIllIlIlIIlI = new JFrame();
      lllllllllllllllllIllIIllIlIlIIll.setFileSelectionMode(0);
      lllllllllllllllllIllIIllIlIlIIlI.setVisible(true);
      lllllllllllllllllIllIIllIlIlIIlI.toFront();
      lllllllllllllllllIllIIllIlIlIIlI.setVisible(false);
      int lllllllllllllllllIllIIllIlIlIlII = lllllllllllllllllIllIIllIlIlIIll.showOpenDialog(lllllllllllllllllIllIIllIlIlIIlI);
      lllllllllllllllllIllIIllIlIlIIlI.dispose();
      return lllllllllllllllllIllIIllIlIlIlII == 0 ? lllllllllllllllllIllIIllIlIlIIll.getSelectedFile() : null;
   }

   public static void showURL(String lllllllllllllllllIllIIllIlIllIll) {
      try {
         Desktop.getDesktop().browse(new URI(lllllllllllllllllIllIIllIlIllIll));
      } catch (URISyntaxException | IOException var2) {
         var2.printStackTrace();
      }

   }

   public static File saveFileChooser() {
      if (mc.isFullScreen()) {
         mc.toggleFullscreen();
      }

      long lllllllllllllllllIllIIllIlIIlIlI = new JFileChooser();
      JFrame lllllllllllllllllIllIIllIlIIllII = new JFrame();
      lllllllllllllllllIllIIllIlIIlIlI.setFileSelectionMode(0);
      lllllllllllllllllIllIIllIlIIllII.setVisible(true);
      lllllllllllllllllIllIIllIlIIllII.toFront();
      lllllllllllllllllIllIIllIlIIllII.setVisible(false);
      char lllllllllllllllllIllIIllIlIIlIII = lllllllllllllllllIllIIllIlIIlIlI.showSaveDialog(lllllllllllllllllIllIIllIlIIllII);
      lllllllllllllllllIllIIllIlIIllII.dispose();
      return lllllllllllllllllIllIIllIlIIlIII == 0 ? lllllllllllllllllIllIIllIlIIlIlI.getSelectedFile() : null;
   }
}
