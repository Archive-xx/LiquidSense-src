package net.mcleaks;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.net.ssl.HttpsURLConnection;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MCLeaks {
   // $FF: synthetic field
   private static final Gson gson = new Gson();
   // $FF: synthetic field
   private static final ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();
   // $FF: synthetic field
   private static Session session;

   public static void redeem(String lllIlllIlIIllll, Callback<Object> lllIlllIlIIlllI) {
      EXECUTOR_SERVICE.execute(() -> {
         URLConnection lllIlllIIlIlIlI = preparePostRequest(String.valueOf((new StringBuilder()).append("{\"token\":\"").append(lllIlllIlIIllll).append("\"}")));
         if (lllIlllIIlIlIlI == null) {
            lllIlllIlIIlllI.done("An error occured! [R1]");
         } else {
            int lllIlllIIlIIlII = getResult(lllIlllIIlIlIlI);
            if (lllIlllIIlIIlII instanceof String) {
               lllIlllIlIIlllI.done(lllIlllIIlIIlII);
            } else {
               JsonObject lllIlllIIlIlIII = (JsonObject)lllIlllIIlIIlII;
               if (lllIlllIIlIlIII != null) {
                  if (lllIlllIIlIlIII.has("mcname") && lllIlllIIlIlIII.has("session")) {
                     lllIlllIlIIlllI.done(new RedeemResponse(lllIlllIIlIlIII.get("mcname").getAsString(), lllIlllIIlIlIII.get("session").getAsString()));
                  } else {
                     lllIlllIlIIlllI.done("An error occured! [R2]");
                  }
               }
            }
         }
      });
   }

   public static void refresh(Session lllIlllIlIlIIll) {
      session = lllIlllIlIlIIll;
   }

   public static void remove() {
      session = null;
   }

   private static URLConnection preparePostRequest(String lllIlllIlIIIlII) {
      try {
         HttpsURLConnection lllIlllIlIIlIII = (HttpsURLConnection)(new URL("https://auth.mcleaks.net/v1/redeem")).openConnection();
         lllIlllIlIIlIII.setConnectTimeout(10000);
         lllIlllIlIIlIII.setReadTimeout(10000);
         lllIlllIlIIlIII.setRequestMethod("POST");
         lllIlllIlIIlIII.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; rv:2.2) Gecko/20110201");
         lllIlllIlIIlIII.setDoOutput(true);
         DataOutputStream lllIlllIlIIIlll = new DataOutputStream(lllIlllIlIIlIII.getOutputStream());
         lllIlllIlIIIlll.write(lllIlllIlIIIlII.getBytes(StandardCharsets.UTF_8));
         lllIlllIlIIIlll.flush();
         lllIlllIlIIIlll.close();
         return lllIlllIlIIlIII;
      } catch (Exception var3) {
         var3.printStackTrace();
         return null;
      }
   }

   private static Object getResult(URLConnection lllIlllIIllIllI) {
      try {
         double lllIlllIIllIlIl = new BufferedReader(new InputStreamReader(lllIlllIIllIllI.getInputStream()));

         boolean var10001;
         StringBuilder lllIlllIIlllIll;
         String lllIlllIIlllIlI;
         for(lllIlllIIlllIll = new StringBuilder(); (lllIlllIIlllIlI = lllIlllIIllIlIl.readLine()) != null; var10001 = false) {
            lllIlllIIlllIll.append(lllIlllIIlllIlI);
         }

         lllIlllIIllIlIl.close();
         JsonElement lllIlllIIlllIIl = (JsonElement)gson.fromJson(String.valueOf(lllIlllIIlllIll), JsonElement.class);
         if (lllIlllIIlllIIl.isJsonObject() && lllIlllIIlllIIl.getAsJsonObject().has("success")) {
            if (!lllIlllIIlllIIl.getAsJsonObject().get("success").getAsBoolean()) {
               return lllIlllIIlllIIl.getAsJsonObject().has("errorMessage") ? lllIlllIIlllIIl.getAsJsonObject().get("errorMessage").getAsString() : "An error occured! [G4]";
            } else if (!lllIlllIIlllIIl.getAsJsonObject().has("result")) {
               return "An error occured! [G3]";
            } else {
               return lllIlllIIlllIIl.getAsJsonObject().get("result").isJsonObject() ? lllIlllIIlllIIl.getAsJsonObject().get("result").getAsJsonObject() : null;
            }
         } else {
            return "An error occured! [G1]";
         }
      } catch (Exception var5) {
         var5.printStackTrace();
         return "An error occured! [G2]";
      }
   }

   public static boolean isAltActive() {
      return session != null;
   }

   public static Session getSession() {
      return session;
   }
}
