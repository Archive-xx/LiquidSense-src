//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.injection.forge.mixins.network;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.mcleaks.MCLeaks;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.login.server.S01PacketEncryptionRequest;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.CryptManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SideOnly(Side.CLIENT)
@Mixin({NetHandlerLoginClient.class})
public class MixinNetHandlerLoginClient {
   // $FF: synthetic field
   @Shadow
   @Final
   private NetworkManager networkManager;

   @Inject(
      method = {"handleEncryptionRequest"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void handleEncryptionRequest(S01PacketEncryptionRequest lIlIIIIllllIlI, CallbackInfo lIlIIIIlllllII) {
      if (MCLeaks.isAltActive()) {
         int lIlIIIIllllIII = CryptManager.createNewSharedKey();
         byte lIlIIIIlllIlll = lIlIIIIllllIlI.getServerId();
         double lIlIIIIlllIllI = lIlIIIIllllIlI.getPublicKey();
         char lIlIIIIlllIlIl = (new BigInteger(CryptManager.getServerIdHash(lIlIIIIlllIlll, lIlIIIIlllIllI, lIlIIIIllllIII))).toString(16);
         Exception lIlIIIIlllIlII = MCLeaks.getSession();
         String lIlIIIIlllIIll = String.valueOf((new StringBuilder()).append(((InetSocketAddress)lIlIIIIllllIll.networkManager.getRemoteAddress()).getHostName()).append(":").append(((InetSocketAddress)lIlIIIIllllIll.networkManager.getRemoteAddress()).getPort()));

         try {
            label48: {
               String lIlIIIlIIIllII = String.valueOf((new StringBuilder()).append("{\"session\":\"").append(lIlIIIIlllIlII.getToken()).append("\",\"mcname\":\"").append(lIlIIIIlllIlII.getUsername()).append("\",\"serverhash\":\"").append(lIlIIIIlllIlIl).append("\",\"server\":\"").append(lIlIIIIlllIIll).append("\"}"));
               boolean lIlIIIIlllIIIl = (HttpURLConnection)(new URL("https://auth.mcleaks.net/v1/joinserver")).openConnection();
               lIlIIIIlllIIIl.setConnectTimeout(10000);
               lIlIIIIlllIIIl.setReadTimeout(10000);
               lIlIIIIlllIIIl.setRequestMethod("POST");
               lIlIIIIlllIIIl.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
               lIlIIIIlllIIIl.setDoOutput(true);
               DataOutputStream lIlIIIlIIIlIlI = new DataOutputStream(lIlIIIIlllIIIl.getOutputStream());
               lIlIIIlIIIlIlI.write(lIlIIIlIIIllII.getBytes(StandardCharsets.UTF_8));
               lIlIIIlIIIlIlI.flush();
               lIlIIIlIIIlIlI.close();
               float lIlIIIIllIllll = new BufferedReader(new InputStreamReader(lIlIIIIlllIIIl.getInputStream()));

               StringBuilder lIlIIIlIIIlIII;
               String lIlIIIIllIllIl;
               boolean var10001;
               for(lIlIIIlIIIlIII = new StringBuilder(); (lIlIIIIllIllIl = lIlIIIIllIllll.readLine()) != null; var10001 = false) {
                  lIlIIIlIIIlIII.append(lIlIIIIllIllIl);
               }

               lIlIIIIllIllll.close();
               JsonElement lIlIIIlIIIIllI = (JsonElement)(new Gson()).fromJson(String.valueOf(lIlIIIlIIIlIII), JsonElement.class);
               if (lIlIIIlIIIIllI.isJsonObject() && lIlIIIlIIIIllI.getAsJsonObject().has("success")) {
                  if (lIlIIIlIIIIllI.getAsJsonObject().get("success").getAsBoolean()) {
                     break label48;
                  }

                  int lIlIIIIllIlIll = "Received success=false from MCLeaks API";
                  if (lIlIIIlIIIIllI.getAsJsonObject().has("errorMessage")) {
                     lIlIIIIllIlIll = lIlIIIlIIIIllI.getAsJsonObject().get("errorMessage").getAsString();
                  }

                  lIlIIIIllllIll.networkManager.closeChannel(new ChatComponentText(lIlIIIIllIlIll));
                  lIlIIIIlllllII.cancel();
                  return;
               }

               lIlIIIIllllIll.networkManager.closeChannel(new ChatComponentText("Invalid response from MCLeaks API"));
               lIlIIIIlllllII.cancel();
               return;
            }
         } catch (Exception var17) {
            lIlIIIIllllIll.networkManager.closeChannel(new ChatComponentText(String.valueOf((new StringBuilder()).append("Error whilst contacting MCLeaks API: ").append(var17.toString()))));
            lIlIIIIlllllII.cancel();
            return;
         }

         ClientUtils.sendEncryption(lIlIIIIllllIll.networkManager, lIlIIIIllllIII, lIlIIIIlllIllI, lIlIIIIllllIlI);
         lIlIIIIlllllII.cancel();
      }

   }
}
