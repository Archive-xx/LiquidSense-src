//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.file.configs;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.render.XRay;
import net.ccbluex.liquidbounce.file.FileConfig;
import net.ccbluex.liquidbounce.file.FileManager;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.minecraft.block.Block;

public class XRayConfig extends FileConfig {
   public XRayConfig(File lIIlllllIIlllIl) {
      super(lIIlllllIIlllIl);
   }

   protected void loadConfig() throws IOException {
      XRay lIIlllllIIIllII = (XRay)LiquidBounce.moduleManager.getModule(XRay.class);
      if (lIIlllllIIIllII == null) {
         ClientUtils.getLogger().error("[FileManager] Failed to find xray module.");
      } else {
         JsonArray lIIlllllIIIlIlI = (new JsonParser()).parse(new BufferedReader(new FileReader(lIIlllllIIIlIII.getFile()))).getAsJsonArray();
         lIIlllllIIIllII.getXrayBlocks().clear();
         Iterator lIIlllllIIIIlII = lIIlllllIIIlIlI.iterator();

         while(lIIlllllIIIIlII.hasNext()) {
            JsonElement lIIlllllIIIIIlI = (JsonElement)lIIlllllIIIIlII.next();

            try {
               byte lIIlllllIIIIIII = Block.getBlockFromName(lIIlllllIIIIIlI.getAsString());
               if (lIIlllllIIIllII.getXrayBlocks().contains(lIIlllllIIIIIII)) {
                  ClientUtils.getLogger().error(String.valueOf((new StringBuilder()).append("[FileManager] Skipped xray block '").append(lIIlllllIIIIIII.getRegistryName()).append("' because the block is already added.")));
               } else {
                  lIIlllllIIIllII.getXrayBlocks().add(lIIlllllIIIIIII);
                  boolean var10001 = false;
               }
            } catch (Throwable var6) {
               ClientUtils.getLogger().error("[FileManager] Failed to add block to xray.", var6);
            }
         }

      }
   }

   protected void saveConfig() throws IOException {
      float lIIllllIlIllIIl = (XRay)LiquidBounce.moduleManager.getModule(XRay.class);
      if (lIIllllIlIllIIl == null) {
         ClientUtils.getLogger().error("[FileManager] Failed to find xray module.");
      } else {
         boolean lIIllllIlIllIII = new JsonArray();
         Iterator lIIllllIlIlIlll = lIIllllIlIllIIl.getXrayBlocks().iterator();

         while(lIIllllIlIlIlll.hasNext()) {
            Block lIIllllIllIIIIl = (Block)lIIllllIlIlIlll.next();
            lIIllllIlIllIII.add(FileManager.PRETTY_GSON.toJsonTree(Block.getIdFromBlock(lIIllllIllIIIIl)));
         }

         PrintWriter lIIllllIlIllIll = new PrintWriter(new FileWriter(lIIllllIlIlllll.getFile()));
         lIIllllIlIllIll.println(FileManager.PRETTY_GSON.toJson(lIIllllIlIllIII));
         lIIllllIlIllIll.close();
      }
   }
}
