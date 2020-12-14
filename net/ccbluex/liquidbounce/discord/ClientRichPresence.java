//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.discord;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.RichPresence;
import com.jagrosh.discordipc.entities.pipe.PipeStatus;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.concurrent.ThreadsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.HttpUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u000f\u001a\u00020\u0010H\u0002J\u0006\u0010\u0011\u001a\u00020\u0010J\u0006\u0010\u0012\u001a\u00020\u0010J\u0006\u0010\u0013\u001a\u00020\u0010R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\f\u001a\n \u000e*\u0004\u0018\u00010\r0\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"},
   d2 = {"Lnet/ccbluex/liquidbounce/discord/ClientRichPresence;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "appID", "", "assets", "", "", "ipcClient", "Lcom/jagrosh/discordipc/IPCClient;", "running", "", "timestamp", "Ljava/time/OffsetDateTime;", "kotlin.jvm.PlatformType", "loadConfiguration", "", "setup", "shutdown", "update", "LiquidSense"}
)
public final class ClientRichPresence extends MinecraftInstance {
   // $FF: synthetic field
   private final Map<String, String> assets;
   // $FF: synthetic field
   private final OffsetDateTime timestamp;
   // $FF: synthetic field
   private long appID;
   // $FF: synthetic field
   private boolean running;
   // $FF: synthetic field
   private IPCClient ipcClient;

   public final void shutdown() {
      try {
         IPCClient var10000 = lllllllllllllllllIllllIlIIlllIII.ipcClient;
         if (var10000 != null) {
            var10000.close();
         } else {
            boolean var10001 = false;
         }
      } catch (Throwable var3) {
         ClientUtils.getLogger().error("Failed to close Discord RPC.", var3);
      }

   }

   public ClientRichPresence() {
      byte lllllllllllllllllIllllIlIIIlllII = false;
      byte lllllllllllllllllIllllIlIIIllIlI = (Map)(new LinkedHashMap());
      lllllllllllllllllIllllIlIIIlllIl.assets = lllllllllllllllllIllllIlIIIllIlI;
      lllllllllllllllllIllllIlIIIlllIl.timestamp = OffsetDateTime.now();
   }

   private final void loadConfiguration() {
      JsonElement lllllllllllllllllIllllIlIIlIllII = (new JsonParser()).parse(HttpUtils.get("https://cloud.liquidbounce.net/LiquidBounce/discord.json"));
      if (lllllllllllllllllIllllIlIIlIllII instanceof JsonObject) {
         if (((JsonObject)lllllllllllllllllIllllIlIIlIllII).has("appID")) {
            JsonElement var10001 = ((JsonObject)lllllllllllllllllIllllIlIIlIllII).get("appID");
            Intrinsics.checkExpressionValueIsNotNull(var10001, "json.get(\"appID\")");
            lllllllllllllllllIllllIlIIlIlIll.appID = var10001.getAsLong();
         }

         JsonElement var10000 = ((JsonObject)lllllllllllllllllIllllIlIIlIllII).get("assets");
         Intrinsics.checkExpressionValueIsNotNull(var10000, "json.get(\"assets\")");

         boolean var10;
         for(Iterator lllllllllllllllllIllllIlIIlIIlll = var10000.getAsJsonObject().entrySet().iterator(); lllllllllllllllllIllllIlIIlIIlll.hasNext(); var10 = false) {
            byte lllllllllllllllllIllllIlIIlIlIII = (Entry)lllllllllllllllllIllllIlIIlIIlll.next();
            double lllllllllllllllllIllllIlIIlIIIll = false;
            float lllllllllllllllllIllllIlIIlIllIl = (String)lllllllllllllllllIllllIlIIlIlIII.getKey();
            lllllllllllllllllIllllIlIIlIIIll = false;
            short lllllllllllllllllIllllIlIIlIIlIl = (JsonElement)lllllllllllllllllIllllIlIIlIlIII.getValue();
            Map var9 = lllllllllllllllllIllllIlIIlIlIll.assets;
            Intrinsics.checkExpressionValueIsNotNull(lllllllllllllllllIllllIlIIlIllIl, "key");
            Intrinsics.checkExpressionValueIsNotNull(lllllllllllllllllIllllIlIIlIIlIl, "value");
            String var10002 = lllllllllllllllllIllllIlIIlIIlIl.getAsString();
            Intrinsics.checkExpressionValueIsNotNull(var10002, "value.asString");
            var9.put(lllllllllllllllllIllllIlIIlIllIl, var10002);
         }

      }
   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   public final void update() {
      RichPresence.Builder lllllllllllllllllIllllIlIlIIlIll = new RichPresence.Builder();
      lllllllllllllllllIllllIlIlIIlIll.setStartTimestamp(lllllllllllllllllIllllIlIlIIlIlI.timestamp);
      boolean var10001 = false;
      if (lllllllllllllllllIllllIlIlIIlIlI.assets.containsKey("logo")) {
         lllllllllllllllllIllllIlIlIIlIll.setLargeImage((String)lllllllllllllllllIllllIlIlIIlIlI.assets.get("logo"), "MC 1.8.9 - LiquidSense b6");
         var10001 = false;
      }

      if (access$getMc$p$s1046033730().thePlayer != null) {
         Minecraft var10000 = access$getMc$p$s1046033730();
         Intrinsics.checkExpressionValueIsNotNull(var10000, "mc");
         ServerData lllllllllllllllllIllllIlIlIIllII = var10000.getCurrentServerData();
         StringBuilder var15 = (new StringBuilder()).append("Server: ");
         Minecraft var10002 = access$getMc$p$s1046033730();
         Intrinsics.checkExpressionValueIsNotNull(var10002, "mc");
         lllllllllllllllllIllllIlIlIIlIll.setDetails(String.valueOf(var15.append(!var10002.isIntegratedServerRunning() && lllllllllllllllllIllllIlIlIIllII != null ? lllllllllllllllllIllllIlIlIIllII.serverIP : "Singleplayer")));
         var10001 = false;
         var15 = (new StringBuilder()).append("Enabled ");
         short lllllllllllllllllIllllIlIlIIIllI = (Iterable)LiquidBounce.INSTANCE.getModuleManager().getModules();
         String lllllllllllllllllIllllIlIIlllllI = var15;
         int lllllllllllllllllIllllIlIlIIllIl = false;
         int var13;
         if (lllllllllllllllllIllllIlIlIIIllI instanceof Collection && ((Collection)lllllllllllllllllIllllIlIlIIIllI).isEmpty()) {
            var13 = 0;
         } else {
            byte lllllllllllllllllIllllIlIlIIIlII = 0;
            Iterator lllllllllllllllllIllllIlIlIIIIll = lllllllllllllllllIllllIlIlIIIllI.iterator();

            while(lllllllllllllllllIllllIlIlIIIIll.hasNext()) {
               Exception lllllllllllllllllIllllIlIlIIIIlI = lllllllllllllllllIllllIlIlIIIIll.next();
               int lllllllllllllllllIllllIlIlIIIIIl = (Module)lllllllllllllllllIllllIlIlIIIIlI;
               byte lllllllllllllllllIllllIlIlIIIIII = false;
               if (lllllllllllllllllIllllIlIlIIIIIl.getState()) {
                  ++lllllllllllllllllIllllIlIlIIIlII;
                  lllllllllllllllllIllllIlIlIIIIII = false;
                  if (lllllllllllllllllIllllIlIlIIIlII < 0) {
                     CollectionsKt.throwCountOverflow();
                  }
               }
            }

            var13 = lllllllllllllllllIllllIlIlIIIlII;
         }

         char lllllllllllllllllIllllIlIIllllIl = var13;
         lllllllllllllllllIllllIlIlIIlIll.setState(String.valueOf(lllllllllllllllllIllllIlIIlllllI.append(lllllllllllllllllIllllIlIIllllIl).append(" of ").append(LiquidBounce.INSTANCE.getModuleManager().getModules().size()).append(" modules")));
         var10001 = false;
      }

      IPCClient var14 = lllllllllllllllllIllllIlIlIIlIlI.ipcClient;
      PipeStatus var16;
      if (var14 != null) {
         var16 = var14.getStatus();
      } else {
         var10001 = false;
         var16 = null;
      }

      if (var16 == PipeStatus.CONNECTED) {
         var14 = lllllllllllllllllIllllIlIlIIlIlI.ipcClient;
         if (var14 != null) {
            var14.sendRichPresence(lllllllllllllllllIllllIlIlIIlIll.build());
         } else {
            var10001 = false;
         }
      }

   }

   public final void setup() {
      try {
         lllllllllllllllllIllllIlIllIIIlI.running = true;
         lllllllllllllllllIllllIlIllIIIlI.loadConfiguration();
         lllllllllllllllllIllllIlIllIIIlI.ipcClient = new IPCClient(lllllllllllllllllIllllIlIllIIIlI.appID);
         IPCClient var10000 = lllllllllllllllllIllllIlIllIIIlI.ipcClient;
         boolean var10001;
         if (var10000 != null) {
            var10000.setListener((IPCListener)(new IPCListener() {
               public void onReady(@Nullable IPCClient lIIllIllIIlllII) {
                  ThreadsKt.thread$default(false, false, (ClassLoader)null, (String)null, 0, (Function0)(new Function0<Unit>() {
                     public final void invoke() {
                        while(lllllllllllllllllIllllIlIllIIIlI.running) {
                           lllllllllllllllllIllllIlIllIIIlI.update();

                           try {
                              Thread.sleep(1000L);
                           } catch (InterruptedException var2) {
                           }
                        }

                     }
                  }), 31, (Object)null);
                  boolean var10001 = false;
               }

               public void onClose(@Nullable IPCClient lIIllIllIIllIII, @Nullable JSONObject lIIllIllIIlIlll) {
                  lllllllllllllllllIllllIlIllIIIlI.running = false;
               }
            }));
         } else {
            var10001 = false;
         }

         var10000 = lllllllllllllllllIllllIlIllIIIlI.ipcClient;
         if (var10000 != null) {
            var10000.connect();
         } else {
            var10001 = false;
         }
      } catch (Throwable var2) {
         ClientUtils.getLogger().error("Failed to setup Discord RPC.", var2);
      }

   }
}
