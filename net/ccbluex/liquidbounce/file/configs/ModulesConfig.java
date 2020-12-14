package net.ccbluex.liquidbounce.file.configs;

import com.google.gson.JsonElement;
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
import java.util.Map.Entry;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.file.FileConfig;
import net.ccbluex.liquidbounce.file.FileManager;

public class ModulesConfig extends FileConfig {
   protected void loadConfig() throws IOException {
      JsonElement lllllllllllllllllIlIllllIlIlIIIl = (new JsonParser()).parse(new BufferedReader(new FileReader(lllllllllllllllllIlIllllIlIlIIlI.getFile())));
      if (!(lllllllllllllllllIlIllllIlIlIIIl instanceof JsonNull)) {
         Iterator lllllllllllllllllIlIllllIlIlIIII = lllllllllllllllllIlIllllIlIlIIIl.getAsJsonObject().entrySet().iterator();

         while(lllllllllllllllllIlIllllIlIlIIII.hasNext()) {
            Entry<String, JsonElement> lllllllllllllllllIlIllllIlIIllII = (Entry)lllllllllllllllllIlIllllIlIlIIII.next();
            Module lllllllllllllllllIlIllllIlIlIIll = LiquidBounce.moduleManager.getModule((String)lllllllllllllllllIlIllllIlIIllII.getKey());
            if (lllllllllllllllllIlIllllIlIlIIll != null) {
               JsonObject lllllllllllllllllIlIllllIlIlIlIl = (JsonObject)lllllllllllllllllIlIllllIlIIllII.getValue();
               lllllllllllllllllIlIllllIlIlIIll.setState(lllllllllllllllllIlIllllIlIlIlIl.get("State").getAsBoolean());
               lllllllllllllllllIlIllllIlIlIIll.setKeyBind(lllllllllllllllllIlIllllIlIlIlIl.get("KeyBind").getAsInt());
            }
         }

      }
   }

   protected void saveConfig() throws IOException {
      float lllllllllllllllllIlIllllIIlllllI = new JsonObject();
      Iterator lllllllllllllllllIlIllllIIllllIl = LiquidBounce.moduleManager.getModules().iterator();

      while(lllllllllllllllllIlIllllIIllllIl.hasNext()) {
         Module lllllllllllllllllIlIllllIlIIIIll = (Module)lllllllllllllllllIlIllllIIllllIl.next();
         int lllllllllllllllllIlIllllIIlllIll = new JsonObject();
         lllllllllllllllllIlIllllIIlllIll.addProperty("State", lllllllllllllllllIlIllllIlIIIIll.getState());
         lllllllllllllllllIlIllllIIlllIll.addProperty("KeyBind", lllllllllllllllllIlIllllIlIIIIll.getKeyBind());
         lllllllllllllllllIlIllllIIlllllI.add(lllllllllllllllllIlIllllIlIIIIll.getName(), lllllllllllllllllIlIllllIIlllIll);
      }

      float lllllllllllllllllIlIllllIIllllIl = new PrintWriter(new FileWriter(lllllllllllllllllIlIllllIlIIIIlI.getFile()));
      lllllllllllllllllIlIllllIIllllIl.println(FileManager.PRETTY_GSON.toJson(lllllllllllllllllIlIllllIIlllllI));
      lllllllllllllllllIlIllllIIllllIl.close();
   }

   public ModulesConfig(File lllllllllllllllllIlIllllIlIlllII) {
      super(lllllllllllllllllIlIllllIlIlllII);
   }
}
