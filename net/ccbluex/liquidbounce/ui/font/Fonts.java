//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.ui.font;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.misc.HttpUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class Fonts {
   // $FF: synthetic field
   @FontDetails(
      fontName = "Minecraft Font"
   )
   public static final FontRenderer minecraftFont;
   // $FF: synthetic field
   @FontDetails(
      fontName = "Roboto Medium",
      fontSize = 40
   )
   public static GameFontRenderer font40;
   // $FF: synthetic field
   private static final List<GameFontRenderer> CUSTOM_FONT_RENDERERS;
   // $FF: synthetic field
   @FontDetails(
      fontName = "Roboto Medium",
      fontSize = 30
   )
   public static GameFontRenderer font30;
   // $FF: synthetic field
   @FontDetails(
      fontName = "Roboto Bold",
      fontSize = 90
   )
   public static GameFontRenderer fontBold180;
   // $FF: synthetic field
   @FontDetails(
      fontName = "Roboto Medium",
      fontSize = 35
   )
   public static GameFontRenderer font35;

   public static List<FontRenderer> getFonts() {
      List<FontRenderer> llIlllllIlIIllI = new ArrayList();
      float llIlllllIlIIlII = Fonts.class.getDeclaredFields();
      String llIlllllIlIIIll = llIlllllIlIIlII.length;

      boolean var10001;
      for(int llIlllllIlIIIlI = 0; llIlllllIlIIIlI < llIlllllIlIIIll; ++llIlllllIlIIIlI) {
         Field llIlllllIlIIlll = llIlllllIlIIlII[llIlllllIlIIIlI];

         try {
            llIlllllIlIIlll.setAccessible(true);
            Object llIlllllIlIlIIl = llIlllllIlIIlll.get((Object)null);
            if (llIlllllIlIlIIl instanceof FontRenderer) {
               llIlllllIlIIllI.add((FontRenderer)llIlllllIlIlIIl);
               var10001 = false;
            }
         } catch (IllegalAccessException var7) {
            var7.printStackTrace();
         }
      }

      llIlllllIlIIllI.addAll(CUSTOM_FONT_RENDERERS);
      var10001 = false;
      return llIlllllIlIIllI;
   }

   public static void loadFonts() {
      long llIlllllllIIlll = System.currentTimeMillis();
      ClientUtils.getLogger().info("Loading Fonts.");
      downloadFonts();
      font30 = new GameFontRenderer(getFont("Roboto-Medium.ttf", 16));
      font35 = new GameFontRenderer(getFont("Roboto-Medium.ttf", 18));
      font40 = new GameFontRenderer(getFont("Roboto-Medium.ttf", 20));
      fontBold180 = new GameFontRenderer(getFont("Roboto-Bold.ttf", 60));

      try {
         CUSTOM_FONT_RENDERERS.clear();
         File llIlllllllIlIIl = new File(LiquidBounce.fileManager.fontsDir, "fonts.json");
         boolean var10001;
         if (llIlllllllIlIIl.exists()) {
            JsonElement llIlllllllIllII = (new JsonParser()).parse(new BufferedReader(new FileReader(llIlllllllIlIIl)));
            if (llIlllllllIllII instanceof JsonNull) {
               return;
            }

            JsonArray llIlllllllIlIll = (JsonArray)llIlllllllIllII;

            for(Iterator llIlllllllIIIlI = llIlllllllIlIll.iterator(); llIlllllllIIIlI.hasNext(); var10001 = false) {
               byte llIlllllllIIIIl = (JsonElement)llIlllllllIIIlI.next();
               if (llIlllllllIIIIl instanceof JsonNull) {
                  return;
               }

               JsonObject llIlllllllIlllI = (JsonObject)llIlllllllIIIIl;
               CUSTOM_FONT_RENDERERS.add(new GameFontRenderer(getFont(llIlllllllIlllI.get("fontFile").getAsString(), llIlllllllIlllI.get("fontSize").getAsInt())));
            }
         } else {
            llIlllllllIlIIl.createNewFile();
            var10001 = false;
            PrintWriter llIlllllllIlIlI = new PrintWriter(new FileWriter(llIlllllllIlIIl));
            llIlllllllIlIlI.println((new GsonBuilder()).setPrettyPrinting().create().toJson(new JsonArray()));
            llIlllllllIlIlI.close();
         }
      } catch (Exception var8) {
         var8.printStackTrace();
      }

      ClientUtils.getLogger().info(String.valueOf((new StringBuilder()).append("Loaded Fonts. (").append(System.currentTimeMillis() - llIlllllllIIlll).append("ms)")));
   }

   public static Object[] getFontDetails(FontRenderer llIlllllIllIlll) {
      String llIlllllIllIlIl = Fonts.class.getDeclaredFields();
      double llIlllllIllIlII = llIlllllIllIlIl.length;

      for(int llIlllllIllIIll = 0; llIlllllIllIIll < llIlllllIllIlII; ++llIlllllIllIIll) {
         Field llIlllllIlllIIl = llIlllllIllIlIl[llIlllllIllIIll];

         try {
            llIlllllIlllIIl.setAccessible(true);
            byte llIlllllIllIIIl = llIlllllIlllIIl.get((Object)null);
            if (llIlllllIllIIIl.equals(llIlllllIllIlll)) {
               FontDetails llIlllllIllllII = (FontDetails)llIlllllIlllIIl.getAnnotation(FontDetails.class);
               return new Object[]{llIlllllIllllII.fontName(), llIlllllIllllII.fontSize()};
            }
         } catch (IllegalAccessException var7) {
            var7.printStackTrace();
         }
      }

      if (llIlllllIllIlll instanceof GameFontRenderer) {
         Font llIlllllIlllIII = ((GameFontRenderer)llIlllllIllIlll).getDefaultFont().getFont();
         return new Object[]{llIlllllIlllIII.getName(), llIlllllIlllIII.getSize()};
      } else {
         return null;
      }
   }

   public static FontRenderer getFontRenderer(String llIllllllIIllIl, int llIllllllIIlIlI) {
      String llIllllllIIlIIl = Fonts.class.getDeclaredFields();
      char llIllllllIIlIII = llIllllllIIlIIl.length;

      for(int llIllllllIIIlll = 0; llIllllllIIIlll < llIllllllIIlIII; ++llIllllllIIIlll) {
         Field llIllllllIIIllI = llIllllllIIlIIl[llIllllllIIIlll];

         try {
            llIllllllIIIllI.setAccessible(true);
            Object llIllllllIlIIlI = llIllllllIIIllI.get((Object)null);
            if (llIllllllIlIIlI instanceof FontRenderer) {
               float llIllllllIIIlII = (FontDetails)llIllllllIIIllI.getAnnotation(FontDetails.class);
               if (llIllllllIIIlII.fontName().equals(llIllllllIIllIl) && llIllllllIIIlII.fontSize() == llIllllllIIlIlI) {
                  return (FontRenderer)llIllllllIlIIlI;
               }
            }
         } catch (IllegalAccessException var8) {
            var8.printStackTrace();
         }
      }

      Iterator llIllllllIIlIIl = CUSTOM_FONT_RENDERERS.iterator();

      GameFontRenderer llIllllllIIlllI;
      Font llIllllllIIIlll;
      do {
         if (!llIllllllIIlIIl.hasNext()) {
            return minecraftFont;
         }

         llIllllllIIlllI = (GameFontRenderer)llIllllllIIlIIl.next();
         llIllllllIIIlll = llIllllllIIlllI.getDefaultFont().getFont();
      } while(!llIllllllIIIlll.getName().equals(llIllllllIIllIl) || llIllllllIIIlll.getSize() != llIllllllIIlIlI);

      return llIllllllIIlllI;
   }

   static {
      minecraftFont = Minecraft.getMinecraft().fontRendererObj;
      CUSTOM_FONT_RENDERERS = new ArrayList();
   }

   private static void extractZip(String llIllllIlllllll, String llIllllIllllllI) {
      byte[] llIllllIlllllIl = new byte[1024];

      try {
         long llIllllIlllllII = new File(llIllllIllllllI);
         boolean var10001;
         if (!llIllllIlllllII.exists()) {
            llIllllIlllllII.mkdir();
            var10001 = false;
         }

         ZipInputStream llIlllllIIIIlIl = new ZipInputStream(new FileInputStream(llIllllIlllllll));

         for(ZipEntry llIlllllIIIIlII = llIlllllIIIIlIl.getNextEntry(); llIlllllIIIIlII != null; llIlllllIIIIlII = llIlllllIIIIlIl.getNextEntry()) {
            String llIllllIllllIIl = new File(String.valueOf((new StringBuilder()).append(llIllllIllllllI).append(File.separator).append(llIlllllIIIIlII.getName())));
            (new File(llIllllIllllIIl.getParent())).mkdirs();
            var10001 = false;
            FileOutputStream llIllllIllllIII = new FileOutputStream(llIllllIllllIIl);

            int llIlllllIIIIlll;
            while((llIlllllIIIIlll = llIlllllIIIIlIl.read(llIllllIlllllIl)) > 0) {
               llIllllIllllIII.write(llIllllIlllllIl, 0, llIlllllIIIIlll);
            }

            llIllllIllllIII.close();
         }

         llIlllllIIIIlIl.closeEntry();
         llIlllllIIIIlIl.close();
      } catch (IOException var9) {
         var9.printStackTrace();
      }

   }

   private static Font getFont(String llIlllllIIlIllI, int llIlllllIIlIlll) {
      try {
         InputStream llIlllllIIllIll = new FileInputStream(new File(LiquidBounce.fileManager.fontsDir, llIlllllIIlIllI));
         Exception llIlllllIIlIIll = Font.createFont(0, llIlllllIIllIll);
         llIlllllIIlIIll = llIlllllIIlIIll.deriveFont(0, (float)llIlllllIIlIlll);
         llIlllllIIllIll.close();
         return llIlllllIIlIIll;
      } catch (Exception var4) {
         var4.printStackTrace();
         return new Font("default", 0, llIlllllIIlIlll);
      }
   }

   private static void downloadFonts() {
      try {
         File llIllllllIllllI = new File(LiquidBounce.fileManager.fontsDir, "roboto.zip");
         if (!llIllllllIllllI.exists()) {
            ClientUtils.getLogger().info("Downloading fonts...");
            HttpUtils.download("https://cloud.liquidbounce.net/LiquidBounce/fonts/Roboto.zip", llIllllllIllllI);
            ClientUtils.getLogger().info("Extract fonts...");
            extractZip(llIllllllIllllI.getPath(), LiquidBounce.fileManager.fontsDir.getPath());
         }
      } catch (IOException var2) {
         var2.printStackTrace();
      }

   }
}
