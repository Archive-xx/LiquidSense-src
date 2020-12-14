package net.ccbluex.liquidbounce.utils.login;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.Closeable;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import javax.net.ssl.HttpsURLConnection;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004J\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0007\u001a\u00020\u0004J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0004J\u000e\u0010\u000b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0004¨\u0006\f"},
   d2 = {"Lnet/ccbluex/liquidbounce/utils/login/UserUtils;", "", "()V", "getUUID", "", "username", "getUsername", "uuid", "isValidToken", "", "token", "isValidTokenOffline", "LiquidSense"}
)
public final class UserUtils {
   // $FF: synthetic field
   public static final UserUtils INSTANCE;

   static {
      UserUtils llllllllllllllllllIIlllIlIIlIIll = new UserUtils();
      INSTANCE = llllllllllllllllllIIlllIlIIlIIll;
   }

   public final boolean isValidToken(@NotNull String llllllllllllllllllIIlllIlllIIIIl) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIIlllIlllIIIIl, "token");
      Exception llllllllllllllllllIIlllIlllIIIII = HttpClients.createDefault();
      BasicHeader[] llllllllllllllllllIIlllIlllIIlIl = new BasicHeader[]{new BasicHeader("Content-Type", "application/json")};
      boolean llllllllllllllllllIIlllIllIllllI = new HttpPost("https://authserver.mojang.com/validate");
      llllllllllllllllllIIlllIllIllllI.setHeaders((Header[])llllllllllllllllllIIlllIlllIIlIl);
      long llllllllllllllllllIIlllIllIlllIl = new JSONObject();
      llllllllllllllllllIIlllIllIlllIl.put("accessToken", (Object)llllllllllllllllllIIlllIlllIIIIl);
      boolean var10001 = false;
      llllllllllllllllllIIlllIllIllllI.setEntity((HttpEntity)(new StringEntity(llllllllllllllllllIIlllIllIlllIl.toString())));
      CloseableHttpResponse llllllllllllllllllIIlllIlllIlIII = llllllllllllllllllIIlllIlllIIIII.execute((HttpUriRequest)llllllllllllllllllIIlllIllIllllI);
      Intrinsics.checkExpressionValueIsNotNull(llllllllllllllllllIIlllIlllIlIII, "response");
      StatusLine var10000 = llllllllllllllllllIIlllIlllIlIII.getStatusLine();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "response.statusLine");
      boolean llllllllllllllllllIIlllIlllIlIIl = var10000.getStatusCode() == 204;
      return llllllllllllllllllIIlllIlllIlIIl;
   }

   public final boolean isValidTokenOffline(@NotNull String llllllllllllllllllIIlllIllllIIIl) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIIlllIllllIIIl, "token");
      return llllllllllllllllllIIlllIllllIIIl.length() >= 32;
   }

   private UserUtils() {
   }

   @Nullable
   public final String getUsername(@NotNull String llllllllllllllllllIIlllIllIIlIll) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIIlllIllIIlIll, "uuid");
      CloseableHttpClient llllllllllllllllllIIlllIllIIlllI = HttpClients.createDefault();
      HttpGet llllllllllllllllllIIlllIllIIllll = new HttpGet(String.valueOf((new StringBuilder()).append("https://api.mojang.com/user/profiles/").append(llllllllllllllllllIIlllIllIIlIll).append("/names")));
      byte llllllllllllllllllIIlllIllIIlIII = llllllllllllllllllIIlllIllIIlllI.execute((HttpUriRequest)llllllllllllllllllIIlllIllIIllll);
      Intrinsics.checkExpressionValueIsNotNull(llllllllllllllllllIIlllIllIIlIII, "response");
      StatusLine var10000 = llllllllllllllllllIIlllIllIIlIII.getStatusLine();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "response.statusLine");
      if (var10000.getStatusCode() != 200) {
         return null;
      } else {
         String llllllllllllllllllIIlllIllIIIllI;
         try {
            JSONArray llllllllllllllllllIIlllIllIlIIll = new JSONArray(EntityUtils.toString(llllllllllllllllllIIlllIllIIlIII.getEntity()));
            llllllllllllllllllIIlllIllIIIllI = (new JSONObject(llllllllllllllllllIIlllIllIlIIll.get(llllllllllllllllllIIlllIllIlIIll.length() - 1).toString())).getString("name");
         } catch (Exception var8) {
            var8.printStackTrace();
            return null;
         }

         return llllllllllllllllllIIlllIllIIIllI;
      }
   }

   @NotNull
   public final String getUUID(@NotNull String llllllllllllllllllIIlllIlIlIIIIl) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIIlllIlIlIIIIl, "username");

      try {
         URLConnection var10000 = (new URL(String.valueOf((new StringBuilder()).append("https://api.mojang.com/users/profiles/minecraft/").append(llllllllllllllllllIIlllIlIlIIIIl)))).openConnection();
         if (var10000 == null) {
            throw new TypeCastException("null cannot be cast to non-null type javax.net.ssl.HttpsURLConnection");
         } else {
            boolean llllllllllllllllllIIlllIlIIlllll = (HttpsURLConnection)var10000;
            llllllllllllllllllIIlllIlIIlllll.setConnectTimeout(2000);
            llllllllllllllllllIIlllIlIIlllll.setReadTimeout(2000);
            llllllllllllllllllIIlllIlIIlllll.setRequestMethod("GET");
            llllllllllllllllllIIlllIlIIlllll.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
            HttpURLConnection.setFollowRedirects(true);
            llllllllllllllllllIIlllIlIIlllll.setDoOutput(true);
            if (llllllllllllllllllIIlllIlIIlllll.getResponseCode() != 200) {
               return "";
            } else {
               Exception llllllllllllllllllIIlllIlIIllllI = (Closeable)(new InputStreamReader(llllllllllllllllllIIlllIlIIlllll.getInputStream()));
               float llllllllllllllllllIIlllIlIIlllIl = false;
               Throwable llllllllllllllllllIIlllIlIIlllII = (Throwable)null;

               String llllllllllllllllllIIlllIlIIllIII;
               try {
                  InputStreamReader llllllllllllllllllIIlllIlIlIllIl = (InputStreamReader)llllllllllllllllllIIlllIlIIllllI;
                  int llllllllllllllllllIIlllIlIlIllII = false;
                  JsonElement llllllllllllllllllIIlllIlIlIllll = (new JsonParser()).parse((Reader)llllllllllllllllllIIlllIlIlIllIl);
                  Intrinsics.checkExpressionValueIsNotNull(llllllllllllllllllIIlllIlIlIllll, "jsonElement");
                  if (!llllllllllllllllllIIlllIlIlIllll.isJsonObject()) {
                     Unit llllllllllllllllllIIlllIlIIllIll1 = Unit.INSTANCE;
                     return "";
                  }

                  JsonElement var18 = llllllllllllllllllIIlllIlIlIllll.getAsJsonObject().get("id");
                  Intrinsics.checkExpressionValueIsNotNull(var18, "jsonElement.asJsonObject.get(\"id\")");
                  String var19 = var18.getAsString();
                  Intrinsics.checkExpressionValueIsNotNull(var19, "jsonElement.asJsonObject.get(\"id\").asString");
                  llllllllllllllllllIIlllIlIIllIII = var19;
               } catch (Throwable var14) {
                  llllllllllllllllllIIlllIlIIlllII = var14;
                  throw var14;
               } finally {
                  CloseableKt.closeFinally(llllllllllllllllllIIlllIlIIllllI, llllllllllllllllllIIlllIlIIlllII);
               }

               return llllllllllllllllllIIlllIlIIllIII;
            }
         }
      } catch (Throwable var16) {
         return "";
      }
   }
}
