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
import net.ccbluex.liquidbounce.features.module.modules.misc.LiquidChat;
import net.ccbluex.liquidbounce.features.special.AntiForge;
import net.ccbluex.liquidbounce.features.special.BungeeCordSpoof;
import net.ccbluex.liquidbounce.file.FileConfig;
import net.ccbluex.liquidbounce.file.FileManager;
import net.ccbluex.liquidbounce.ui.client.GuiBackground;
import net.ccbluex.liquidbounce.ui.client.altmanager.sub.GuiDonatorCape;
import net.ccbluex.liquidbounce.ui.client.altmanager.sub.altgenerator.GuiTheAltening;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.value.Value;

public class ValuesConfig extends FileConfig {
   public ValuesConfig(File lllllllllllllllllllIllIIIIlIlIII) {
      super(lllllllllllllllllllIllIIIIlIlIII);
   }

   protected void saveConfig() throws IOException {
      JsonObject lllllllllllllllllllIlIlllllllIII = new JsonObject();
      lllllllllllllllllllIlIlllllllIII.addProperty("CommandPrefix", LiquidBounce.commandManager.getPrefix());
      JsonObject lllllllllllllllllllIlIllllllIlll = new JsonObject();
      lllllllllllllllllllIlIllllllIlll.addProperty("TargetPlayer", EntityUtils.targetPlayer);
      lllllllllllllllllllIlIllllllIlll.addProperty("TargetMobs", EntityUtils.targetMobs);
      lllllllllllllllllllIlIllllllIlll.addProperty("TargetAnimals", EntityUtils.targetAnimals);
      lllllllllllllllllllIlIllllllIlll.addProperty("TargetInvisible", EntityUtils.targetInvisible);
      lllllllllllllllllllIlIllllllIlll.addProperty("TargetDead", EntityUtils.targetDead);
      lllllllllllllllllllIlIlllllllIII.add("targets", lllllllllllllllllllIlIllllllIlll);
      byte lllllllllllllllllllIlIlllllIllIl = new JsonObject();
      lllllllllllllllllllIlIlllllIllIl.addProperty("AntiForge", AntiForge.enabled);
      lllllllllllllllllllIlIlllllIllIl.addProperty("AntiForgeFML", AntiForge.blockFML);
      lllllllllllllllllllIlIlllllIllIl.addProperty("AntiForgeProxy", AntiForge.blockProxyPacket);
      lllllllllllllllllllIlIlllllIllIl.addProperty("AntiForgePayloads", AntiForge.blockPayloadPackets);
      lllllllllllllllllllIlIlllllIllIl.addProperty("BungeeSpoof", BungeeCordSpoof.enabled);
      lllllllllllllllllllIlIlllllllIII.add("features", lllllllllllllllllllIlIlllllIllIl);
      float lllllllllllllllllllIlIlllllIllII = new JsonObject();
      lllllllllllllllllllIlIlllllIllII.addProperty("API-Key", GuiTheAltening.Companion.getApiKey());
      lllllllllllllllllllIlIlllllllIII.add("thealtening", lllllllllllllllllllIlIlllllIllII);
      JsonObject lllllllllllllllllllIlIllllllIlII = new JsonObject();
      lllllllllllllllllllIlIllllllIlII.addProperty("token", LiquidChat.Companion.getJwtToken());
      lllllllllllllllllllIlIlllllllIII.add("liquidchat", lllllllllllllllllllIlIllllllIlII);
      byte lllllllllllllllllllIlIlllllIlIlI = new JsonObject();
      lllllllllllllllllllIlIlllllIlIlI.addProperty("TransferCode", GuiDonatorCape.Companion.getTransferCode());
      lllllllllllllllllllIlIlllllIlIlI.addProperty("CapeEnabled", GuiDonatorCape.Companion.getCapeEnabled());
      lllllllllllllllllllIlIlllllllIII.add("DonatorCape", lllllllllllllllllllIlIlllllIlIlI);
      float lllllllllllllllllllIlIlllllIlIIl = new JsonObject();
      lllllllllllllllllllIlIlllllIlIIl.addProperty("Enabled", GuiBackground.Companion.getEnabled());
      lllllllllllllllllllIlIlllllIlIIl.addProperty("Particles", GuiBackground.Companion.getParticles());
      lllllllllllllllllllIlIlllllllIII.add("Background", lllllllllllllllllllIlIlllllIlIIl);
      LiquidBounce.moduleManager.getModules().stream().filter((lllllllllllllllllllIlIllllIlIllI) -> {
         return !lllllllllllllllllllIlIllllIlIllI.getValues().isEmpty();
      }).forEach((lllllllllllllllllllIlIlllllIIIll) -> {
         JsonObject lllllllllllllllllllIlIlllllIIIlI = new JsonObject();
         lllllllllllllllllllIlIlllllIIIll.getValues().forEach((lllllllllllllllllllIlIllllIllIll) -> {
            lllllllllllllllllllIlIlllllIIIlI.add(lllllllllllllllllllIlIllllIllIll.getName(), lllllllllllllllllllIlIllllIllIll.toJson());
         });
         lllllllllllllllllllIlIlllllllIII.add(lllllllllllllllllllIlIlllllIIIll.getName(), lllllllllllllllllllIlIlllllIIIlI);
      });
      int lllllllllllllllllllIlIlllllIlIII = new PrintWriter(new FileWriter(lllllllllllllllllllIlIllllllIIII.getFile()));
      lllllllllllllllllllIlIlllllIlIII.println(FileManager.PRETTY_GSON.toJson(lllllllllllllllllllIlIlllllllIII));
      lllllllllllllllllllIlIlllllIlIII.close();
   }

   protected void loadConfig() throws IOException {
      double lllllllllllllllllllIllIIIIIIlIll = (new JsonParser()).parse(new BufferedReader(new FileReader(lllllllllllllllllllIllIIIIIIllII.getFile())));
      if (!(lllllllllllllllllllIllIIIIIIlIll instanceof JsonNull)) {
         JsonObject lllllllllllllllllllIllIIIIIIlllI = (JsonObject)lllllllllllllllllllIllIIIIIIlIll;
         Iterator lllllllllllllllllllIllIIIIIIlIIl = lllllllllllllllllllIllIIIIIIlllI.entrySet().iterator();

         while(true) {
            while(lllllllllllllllllllIllIIIIIIlIIl.hasNext()) {
               Entry<String, JsonElement> lllllllllllllllllllIllIIIIIIlIII = (Entry)lllllllllllllllllllIllIIIIIIlIIl.next();
               if (((String)lllllllllllllllllllIllIIIIIIlIII.getKey()).equalsIgnoreCase("CommandPrefix")) {
                  LiquidBounce.commandManager.setPrefix(((JsonElement)lllllllllllllllllllIllIIIIIIlIII.getValue()).getAsCharacter());
               } else {
                  JsonObject lllllllllllllllllllIllIIIIIlIllI;
                  if (((String)lllllllllllllllllllIllIIIIIIlIII.getKey()).equalsIgnoreCase("targets")) {
                     lllllllllllllllllllIllIIIIIlIllI = (JsonObject)lllllllllllllllllllIllIIIIIIlIII.getValue();
                     if (lllllllllllllllllllIllIIIIIlIllI.has("TargetPlayer")) {
                        EntityUtils.targetPlayer = lllllllllllllllllllIllIIIIIlIllI.get("TargetPlayer").getAsBoolean();
                     }

                     if (lllllllllllllllllllIllIIIIIlIllI.has("TargetMobs")) {
                        EntityUtils.targetMobs = lllllllllllllllllllIllIIIIIlIllI.get("TargetMobs").getAsBoolean();
                     }

                     if (lllllllllllllllllllIllIIIIIlIllI.has("TargetAnimals")) {
                        EntityUtils.targetAnimals = lllllllllllllllllllIllIIIIIlIllI.get("TargetAnimals").getAsBoolean();
                     }

                     if (lllllllllllllllllllIllIIIIIlIllI.has("TargetInvisible")) {
                        EntityUtils.targetInvisible = lllllllllllllllllllIllIIIIIlIllI.get("TargetInvisible").getAsBoolean();
                     }

                     if (lllllllllllllllllllIllIIIIIlIllI.has("TargetDead")) {
                        EntityUtils.targetDead = lllllllllllllllllllIllIIIIIlIllI.get("TargetDead").getAsBoolean();
                     }
                  } else if (((String)lllllllllllllllllllIllIIIIIIlIII.getKey()).equalsIgnoreCase("features")) {
                     lllllllllllllllllllIllIIIIIlIllI = (JsonObject)lllllllllllllllllllIllIIIIIIlIII.getValue();
                     if (lllllllllllllllllllIllIIIIIlIllI.has("AntiForge")) {
                        AntiForge.enabled = lllllllllllllllllllIllIIIIIlIllI.get("AntiForge").getAsBoolean();
                     }

                     if (lllllllllllllllllllIllIIIIIlIllI.has("AntiForgeFML")) {
                        AntiForge.blockFML = lllllllllllllllllllIllIIIIIlIllI.get("AntiForgeFML").getAsBoolean();
                     }

                     if (lllllllllllllllllllIllIIIIIlIllI.has("AntiForgeProxy")) {
                        AntiForge.blockProxyPacket = lllllllllllllllllllIllIIIIIlIllI.get("AntiForgeProxy").getAsBoolean();
                     }

                     if (lllllllllllllllllllIllIIIIIlIllI.has("AntiForgePayloads")) {
                        AntiForge.blockPayloadPackets = lllllllllllllllllllIllIIIIIlIllI.get("AntiForgePayloads").getAsBoolean();
                     }

                     if (lllllllllllllllllllIllIIIIIlIllI.has("BungeeSpoof")) {
                        BungeeCordSpoof.enabled = lllllllllllllllllllIllIIIIIlIllI.get("BungeeSpoof").getAsBoolean();
                     }
                  } else if (((String)lllllllllllllllllllIllIIIIIIlIII.getKey()).equalsIgnoreCase("thealtening")) {
                     lllllllllllllllllllIllIIIIIlIllI = (JsonObject)lllllllllllllllllllIllIIIIIIlIII.getValue();
                     if (lllllllllllllllllllIllIIIIIlIllI.has("API-Key")) {
                        GuiTheAltening.Companion.setApiKey(lllllllllllllllllllIllIIIIIlIllI.get("API-Key").getAsString());
                     }
                  } else if (((String)lllllllllllllllllllIllIIIIIIlIII.getKey()).equalsIgnoreCase("liquidchat")) {
                     lllllllllllllllllllIllIIIIIlIllI = (JsonObject)lllllllllllllllllllIllIIIIIIlIII.getValue();
                     if (lllllllllllllllllllIllIIIIIlIllI.has("token")) {
                        LiquidChat.Companion.setJwtToken(lllllllllllllllllllIllIIIIIlIllI.get("token").getAsString());
                     }
                  } else if (((String)lllllllllllllllllllIllIIIIIIlIII.getKey()).equalsIgnoreCase("DonatorCape")) {
                     lllllllllllllllllllIllIIIIIlIllI = (JsonObject)lllllllllllllllllllIllIIIIIIlIII.getValue();
                     if (lllllllllllllllllllIllIIIIIlIllI.has("TransferCode")) {
                        GuiDonatorCape.Companion.setTransferCode(lllllllllllllllllllIllIIIIIlIllI.get("TransferCode").getAsString());
                     }

                     if (lllllllllllllllllllIllIIIIIlIllI.has("CapeEnabled")) {
                        GuiDonatorCape.Companion.setCapeEnabled(lllllllllllllllllllIllIIIIIlIllI.get("CapeEnabled").getAsBoolean());
                     }
                  } else if (((String)lllllllllllllllllllIllIIIIIIlIII.getKey()).equalsIgnoreCase("Background")) {
                     lllllllllllllllllllIllIIIIIlIllI = (JsonObject)lllllllllllllllllllIllIIIIIIlIII.getValue();
                     if (lllllllllllllllllllIllIIIIIlIllI.has("Enabled")) {
                        GuiBackground.Companion.setEnabled(lllllllllllllllllllIllIIIIIlIllI.get("Enabled").getAsBoolean());
                     }

                     if (lllllllllllllllllllIllIIIIIlIllI.has("Particles")) {
                        GuiBackground.Companion.setParticles(lllllllllllllllllllIllIIIIIlIllI.get("Particles").getAsBoolean());
                     }
                  } else {
                     Module lllllllllllllllllllIllIIIIIlIIlI = LiquidBounce.moduleManager.getModule((String)lllllllllllllllllllIllIIIIIIlIII.getKey());
                     if (lllllllllllllllllllIllIIIIIlIIlI != null) {
                        short lllllllllllllllllllIllIIIIIIIllI = (JsonObject)lllllllllllllllllllIllIIIIIIlIII.getValue();
                        Iterator lllllllllllllllllllIllIIIIIIIlIl = lllllllllllllllllllIllIIIIIlIIlI.getValues().iterator();

                        while(lllllllllllllllllllIllIIIIIIIlIl.hasNext()) {
                           Value lllllllllllllllllllIllIIIIIlIlII = (Value)lllllllllllllllllllIllIIIIIIIlIl.next();
                           JsonElement lllllllllllllllllllIllIIIIIlIlIl = lllllllllllllllllllIllIIIIIIIllI.get(lllllllllllllllllllIllIIIIIlIlII.getName());
                           if (lllllllllllllllllllIllIIIIIlIlIl != null) {
                              lllllllllllllllllllIllIIIIIlIlII.fromJson(lllllllllllllllllllIllIIIIIlIlIl);
                           }
                        }
                     }
                  }
               }
            }

            return;
         }
      }
   }
}
