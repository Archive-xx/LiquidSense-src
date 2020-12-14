package net.ccbluex.liquidbounce.file.configs;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.file.FileConfig;
import net.ccbluex.liquidbounce.file.FileManager;
import net.ccbluex.liquidbounce.ui.client.clickgui.Panel;
import net.ccbluex.liquidbounce.ui.client.clickgui.elements.Element;
import net.ccbluex.liquidbounce.ui.client.clickgui.elements.ModuleElement;
import net.ccbluex.liquidbounce.utils.ClientUtils;

public class ClickGuiConfig extends FileConfig {
   public ClickGuiConfig(File llIIlllllIIlIIl) {
      super(llIIlllllIIlIIl);
   }

   protected void loadConfig() throws IOException {
      long llIIllllIllIIIl = (new JsonParser()).parse(new BufferedReader(new FileReader(llIIllllIllIlIl.getFile())));
      if (!(llIIllllIllIIIl instanceof JsonNull)) {
         long llIIllllIllIIII = (JsonObject)llIIllllIllIIIl;
         Iterator llIIllllIlIllll = LiquidBounce.clickGui.panels.iterator();

         while(true) {
            Panel llIIllllIlIlllI;
            do {
               if (!llIIllllIlIllll.hasNext()) {
                  return;
               }

               llIIllllIlIlllI = (Panel)llIIllllIlIllll.next();
            } while(!llIIllllIllIIII.has(llIIllllIlIlllI.getName()));

            try {
               double llIIllllIlIllIl = llIIllllIllIIII.getAsJsonObject(llIIllllIlIlllI.getName());
               llIIllllIlIlllI.setOpen(llIIllllIlIllIl.get("open").getAsBoolean());
               llIIllllIlIlllI.setVisible(llIIllllIlIllIl.get("visible").getAsBoolean());
               llIIllllIlIlllI.setX(llIIllllIlIllIl.get("posX").getAsInt());
               llIIllllIlIlllI.setY(llIIllllIlIllIl.get("posY").getAsInt());
               Iterator llIIllllIlIllII = llIIllllIlIlllI.getElements().iterator();

               while(llIIllllIlIllII.hasNext()) {
                  Element llIIllllIlllIIl = (Element)llIIllllIlIllII.next();
                  if (llIIllllIlllIIl instanceof ModuleElement) {
                     ModuleElement llIIllllIlllIlI = (ModuleElement)llIIllllIlllIIl;
                     if (llIIllllIlIllIl.has(llIIllllIlllIlI.getModule().getName())) {
                        try {
                           int llIIllllIlIlIIl = llIIllllIlIllIl.getAsJsonObject(llIIllllIlllIlI.getModule().getName());
                           llIIllllIlllIlI.setShowSettings(llIIllllIlIlIIl.get("Settings").getAsBoolean());
                        } catch (Exception var10) {
                           ClientUtils.getLogger().error(String.valueOf((new StringBuilder()).append("Error while loading clickgui module element with the name '").append(llIIllllIlllIlI.getModule().getName()).append("' (Panel Name: ").append(llIIllllIlIlllI.getName()).append(").")), var10);
                        }
                     }
                  }
               }
            } catch (Exception var11) {
               ClientUtils.getLogger().error(String.valueOf((new StringBuilder()).append("Error while loading clickgui panel with the name '").append(llIIllllIlIlllI.getName()).append("'.")), var11);
            }
         }
      }
   }

   protected void saveConfig() throws IOException {
      String llIIllllIIlIllI = new JsonObject();
      Iterator llIIllllIIlIlIl = LiquidBounce.clickGui.panels.iterator();

      while(llIIllllIIlIlIl.hasNext()) {
         Panel llIIllllIIllIll = (Panel)llIIllllIIlIlIl.next();
         char llIIllllIIlIIll = new JsonObject();
         llIIllllIIlIIll.addProperty("open", llIIllllIIllIll.getOpen());
         llIIllllIIlIIll.addProperty("visible", llIIllllIIllIll.isVisible());
         llIIllllIIlIIll.addProperty("posX", llIIllllIIllIll.getX());
         llIIllllIIlIIll.addProperty("posY", llIIllllIIllIll.getY());
         Iterator llIIllllIIlIIlI = llIIllllIIllIll.getElements().iterator();

         while(llIIllllIIlIIlI.hasNext()) {
            String llIIllllIIlIIIl = (Element)llIIllllIIlIIlI.next();
            if (llIIllllIIlIIIl instanceof ModuleElement) {
               ModuleElement llIIllllIIlllll = (ModuleElement)llIIllllIIlIIIl;
               byte llIIllllIIIllll = new JsonObject();
               llIIllllIIIllll.addProperty("Settings", llIIllllIIlllll.isShowSettings());
               llIIllllIIlIIll.add(llIIllllIIlllll.getModule().getName(), llIIllllIIIllll);
            }
         }

         llIIllllIIlIllI.add(llIIllllIIllIll.getName(), llIIllllIIlIIll);
      }

      PrintWriter llIIllllIIllIII = new PrintWriter(new FileWriter(llIIllllIIllIlI.getFile()));
      llIIllllIIllIII.println(FileManager.PRETTY_GSON.toJson(llIIllllIIlIllI));
      llIIllllIIllIII.close();
   }
}
