package net.ccbluex.liquidbounce.file.configs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.file.FileConfig;
import net.ccbluex.liquidbounce.ui.client.hud.Config;
import org.apache.commons.io.FileUtils;

public class HudConfig extends FileConfig {
   public HudConfig(File lllllllllllllllllllllllIlIlIIllI) {
      super(lllllllllllllllllllllllIlIlIIllI);
   }

   protected void loadConfig() throws IOException {
      LiquidBounce.hud.clearElements();
      LiquidBounce.hud = (new Config(FileUtils.readFileToString(lllllllllllllllllllllllIlIlIIIll.getFile()))).toHUD();
   }

   protected void saveConfig() throws IOException {
      PrintWriter lllllllllllllllllllllllIlIIlllll = new PrintWriter(new FileWriter(lllllllllllllllllllllllIlIIllllI.getFile()));
      lllllllllllllllllllllllIlIIlllll.println((new Config(LiquidBounce.hud)).toJson());
      lllllllllllllllllllllllIlIIlllll.close();
   }
}
