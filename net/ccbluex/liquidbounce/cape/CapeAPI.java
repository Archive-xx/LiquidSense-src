//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.cape;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.HttpUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0005\u001a\u00020\u0006J\u0010\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\nJ\u0006\u0010\u000b\u001a\u00020\fR\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\r"},
   d2 = {"Lnet/ccbluex/liquidbounce/cape/CapeAPI;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "capeService", "Lnet/ccbluex/liquidbounce/cape/CapeService;", "hasCapeService", "", "loadCape", "Lnet/ccbluex/liquidbounce/cape/CapeInfo;", "uuid", "Ljava/util/UUID;", "registerCapeService", "", "LiquidSense"}
)
public final class CapeAPI extends MinecraftInstance {
   // $FF: synthetic field
   private static CapeService capeService;
   // $FF: synthetic field
   public static final CapeAPI INSTANCE;

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   private CapeAPI() {
   }

   static {
      CapeAPI lllllllllllllllllllIIllIllIllIIl = new CapeAPI();
      INSTANCE = lllllllllllllllllllIIllIllIllIIl;
   }

   public final boolean hasCapeService() {
      return capeService != null;
   }

   @Nullable
   public final CapeInfo loadCape(@NotNull UUID lllllllllllllllllllIIllIlllIIllI) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllllIIllIlllIIllI, "uuid");
      CapeService var10000 = capeService;
      boolean var10001;
      if (var10000 != null) {
         String var14 = var10000.getCape(lllllllllllllllllllIIllIlllIIllI);
         if (var14 != null) {
            String lllllllllllllllllllIIllIlllIlIIl = var14;
            boolean lllllllllllllllllllIIllIlllIIIll = "capes/%s.png";
            long lllllllllllllllllllIIllIlllIIIlI = new Object[]{lllllllllllllllllllIIllIlllIIllI.toString()};
            int lllllllllllllllllllIIllIlllIIIIl = false;
            var14 = String.format(lllllllllllllllllllIIllIlllIIIll, Arrays.copyOf(lllllllllllllllllllIIllIlllIIIlI, lllllllllllllllllllIIllIlllIIIlI.length));
            Intrinsics.checkExpressionValueIsNotNull(var14, "java.lang.String.format(this, *args)");
            char lllllllllllllllllllIIllIlllIIIII = var14;
            ResourceLocation lllllllllllllllllllIIllIlllIlIlI = new ResourceLocation(lllllllllllllllllllIIllIlllIIIII);
            final CapeInfo lllllllllllllllllllIIllIlllIlIll = new CapeInfo(lllllllllllllllllllIIllIlllIlIlI, false, 2, (DefaultConstructorMarker)null);
            ThreadDownloadImageData lllllllllllllllllllIIllIlllIllII = new ThreadDownloadImageData((File)null, lllllllllllllllllllIIllIlllIlIIl, (ResourceLocation)null, (IImageBuffer)(new IImageBuffer() {
               public void skinAvailable() {
                  lllllllllllllllllllIIllIlllIlIll.setCapeAvailable(true);
               }

               @NotNull
               public BufferedImage parseUserSkin(@NotNull BufferedImage llIlIIllllllllI) {
                  Intrinsics.checkParameterIsNotNull(llIlIIllllllllI, "image");
                  return llIlIIllllllllI;
               }
            }));
            Minecraft var15 = access$getMc$p$s1046033730();
            Intrinsics.checkExpressionValueIsNotNull(var15, "mc");
            var15.getTextureManager().loadTexture(lllllllllllllllllllIIllIlllIlIlI, (ITextureObject)lllllllllllllllllllIIllIlllIllII);
            var10001 = false;
            return lllllllllllllllllllIIllIlllIlIll;
         } else {
            var10001 = false;
            return null;
         }
      } else {
         var10001 = false;
         return null;
      }
   }

   public final void registerCapeService() {
      JsonElement var10000 = (new JsonParser()).parse(HttpUtils.get("https://cloud.liquidbounce.net/LiquidBounce/capes.json"));
      Intrinsics.checkExpressionValueIsNotNull(var10000, "JsonParser()\n           …IENT_CLOUD}/capes.json\"))");
      JsonObject lllllllllllllllllllIIlllIIIIIIII = var10000.getAsJsonObject();
      var10000 = lllllllllllllllllllIIlllIIIIIIII.get("serviceType");
      Intrinsics.checkExpressionValueIsNotNull(var10000, "jsonObject.get(\"serviceType\")");
      String lllllllllllllllllllIIlllIIIIIIIl = var10000.getAsString();
      Intrinsics.checkExpressionValueIsNotNull(lllllllllllllllllllIIlllIIIIIIIl, "serviceType");
      char lllllllllllllllllllIIllIlllllIll = false;
      if (lllllllllllllllllllIIlllIIIIIIIl == null) {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
      } else {
         String var13 = lllllllllllllllllllIIlllIIIIIIIl.toLowerCase();
         Intrinsics.checkExpressionValueIsNotNull(var13, "(this as java.lang.String).toLowerCase()");
         boolean lllllllllllllllllllIIllIllllllII = var13;
         switch(lllllllllllllllllllIIllIllllllII.hashCode()) {
         case 96794:
            if (lllllllllllllllllllIIllIllllllII.equals("api")) {
               var10000 = lllllllllllllllllllIIlllIIIIIIII.get("api");
               Intrinsics.checkExpressionValueIsNotNull(var10000, "jsonObject.get(\"api\")");
               var10000 = var10000.getAsJsonObject().get("url");
               Intrinsics.checkExpressionValueIsNotNull(var10000, "jsonObject.get(\"api\").asJsonObject.get(\"url\")");
               char lllllllllllllllllllIIllIlllllIll = var10000.getAsString();
               Intrinsics.checkExpressionValueIsNotNull(lllllllllllllllllllIIllIlllllIll, "url");
               capeService = (CapeService)(new ServiceAPI(lllllllllllllllllllIIllIlllllIll));
               ClientUtils.getLogger().info(String.valueOf((new StringBuilder()).append("Registered ").append(lllllllllllllllllllIIllIlllllIll).append(" as '").append(lllllllllllllllllllIIlllIIIIIIIl).append("' service type.")));
            }
            break;
         case 3322014:
            if (lllllllllllllllllllIIllIllllllII.equals("list")) {
               char lllllllllllllllllllIIllIlllllIll = new HashMap();
               var10000 = lllllllllllllllllllIIlllIIIIIIII.get("users");
               Intrinsics.checkExpressionValueIsNotNull(var10000, "jsonObject.get(\"users\")");
               Iterator lllllllllllllllllllIIllIlllllIIl = var10000.getAsJsonObject().entrySet().iterator();

               while(lllllllllllllllllllIIllIlllllIIl.hasNext()) {
                  double lllllllllllllllllllIIllIlllllIlI = (Entry)lllllllllllllllllllIIllIlllllIIl.next();
                  String lllllllllllllllllllIIllIllllIlIl = false;
                  char lllllllllllllllllllIIlllIIIIIIll = (String)lllllllllllllllllllIIllIlllllIlI.getKey();
                  lllllllllllllllllllIIllIllllIlIl = false;
                  JsonElement lllllllllllllllllllIIlllIIIIIlII = (JsonElement)lllllllllllllllllllIIllIlllllIlI.getValue();
                  Map var14 = (Map)lllllllllllllllllllIIllIlllllIll;
                  Intrinsics.checkExpressionValueIsNotNull(lllllllllllllllllllIIlllIIIIIIll, "key");
                  Intrinsics.checkExpressionValueIsNotNull(lllllllllllllllllllIIlllIIIIIlII, "value");
                  String var10002 = lllllllllllllllllllIIlllIIIIIlII.getAsString();
                  Intrinsics.checkExpressionValueIsNotNull(var10002, "value.asString");
                  var14.put(lllllllllllllllllllIIlllIIIIIIll, var10002);
                  boolean var10001 = false;
                  ClientUtils.getLogger().info(String.valueOf((new StringBuilder()).append("Loaded user cape for '").append(lllllllllllllllllllIIlllIIIIIIll).append("'.")));
               }

               capeService = (CapeService)(new ServiceList((Map)lllllllllllllllllllIIllIlllllIll));
               ClientUtils.getLogger().info(String.valueOf((new StringBuilder()).append("Registered '").append(lllllllllllllllllllIIlllIIIIIIIl).append("' service type.")));
            }
         }

         ClientUtils.getLogger().info("Loaded.");
      }
   }
}
