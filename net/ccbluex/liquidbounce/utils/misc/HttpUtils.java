package net.ccbluex.liquidbounce.utils.misc;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0007J\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0004H\u0007J\"\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u00042\b\b\u0002\u0010\u000e\u001a\u00020\u0004H\u0002J \u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u00042\b\b\u0002\u0010\u000e\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0010"},
   d2 = {"Lnet/ccbluex/liquidbounce/utils/misc/HttpUtils;", "", "()V", "DEFAULT_AGENT", "", "download", "", "url", "file", "Ljava/io/File;", "get", "make", "Ljava/net/HttpURLConnection;", "method", "agent", "request", "LiquidSense"}
)
public final class HttpUtils {
   // $FF: synthetic field
   private static final String DEFAULT_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0";
   // $FF: synthetic field
   public static final HttpUtils INSTANCE;

   private HttpUtils() {
   }

   @JvmStatic
   public static final void download(@NotNull String llIlllIlIIllIII, @NotNull File llIlllIlIIlIlll) throws IOException {
      Intrinsics.checkParameterIsNotNull(llIlllIlIIllIII, "url");
      Intrinsics.checkParameterIsNotNull(llIlllIlIIlIlll, "file");
      FileUtils.copyInputStreamToFile(make$default(INSTANCE, llIlllIlIIllIII, "GET", (String)null, 4, (Object)null).getInputStream(), llIlllIlIIlIlll);
   }

   // $FF: synthetic method
   public static String request$default(HttpUtils var0, String var1, String var2, String var3, int var4, Object var5) throws IOException {
      if ((var4 & 4) != 0) {
         var3 = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0";
      }

      return var0.request(var1, var2, var3);
   }

   // $FF: synthetic method
   static HttpURLConnection make$default(HttpUtils var0, String var1, String var2, String var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0";
      }

      return var0.make(var1, var2, var3);
   }

   @NotNull
   public final String request(@NotNull String llIlllIlIlIlllI, @NotNull String llIlllIlIllIIIl, @NotNull String llIlllIlIllIIII) throws IOException {
      Intrinsics.checkParameterIsNotNull(llIlllIlIlIlllI, "url");
      Intrinsics.checkParameterIsNotNull(llIlllIlIllIIIl, "method");
      Intrinsics.checkParameterIsNotNull(llIlllIlIllIIII, "agent");
      HttpURLConnection llIlllIlIllIlII = llIlllIlIllIIll.make(llIlllIlIlIlllI, llIlllIlIllIIIl, llIlllIlIllIIII);
      InputStream var10000 = llIlllIlIllIlII.getInputStream();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "connection.inputStream");
      long llIlllIlIlIlIlI = var10000;
      Exception llIlllIlIlIlIIl = Charsets.UTF_8;
      char llIlllIlIlIlIII = false;
      return TextStreamsKt.readText((Reader)(new InputStreamReader(llIlllIlIlIlIlI, llIlllIlIlIlIIl)));
   }

   @JvmStatic
   @NotNull
   public static final String get(@NotNull String llIlllIlIIllIll) throws IOException {
      Intrinsics.checkParameterIsNotNull(llIlllIlIIllIll, "url");
      return request$default(INSTANCE, llIlllIlIIllIll, "GET", (String)null, 4, (Object)null);
   }

   static {
      short llIlllIlIIlIIII = new HttpUtils();
      INSTANCE = llIlllIlIIlIIII;
      HttpURLConnection.setFollowRedirects(true);
   }

   private final HttpURLConnection make(String llIlllIllIIllIl, String llIlllIllIIllII, String llIlllIllIIlIII) {
      URLConnection var10000 = (new URL(llIlllIllIIllIl)).openConnection();
      if (var10000 == null) {
         throw new TypeCastException("null cannot be cast to non-null type java.net.HttpURLConnection");
      } else {
         HttpURLConnection llIlllIllIIllll = (HttpURLConnection)var10000;
         llIlllIllIIllll.setRequestMethod(llIlllIllIIllII);
         llIlllIllIIllll.setConnectTimeout(2000);
         llIlllIllIIllll.setReadTimeout(10000);
         llIlllIllIIllll.setRequestProperty("User-Agent", llIlllIllIIlIII);
         llIlllIllIIllll.setInstanceFollowRedirects(true);
         llIlllIllIIllll.setDoOutput(true);
         return llIlllIllIIllll;
      }
   }
}
