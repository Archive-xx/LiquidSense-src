//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.utils;

import com.google.gson.JsonObject;
import io.netty.util.concurrent.GenericFutureListener;
import java.lang.reflect.Field;
import java.security.PublicKey;
import javax.crypto.SecretKey;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.login.client.C01PacketEncryptionResponse;
import net.minecraft.network.login.server.S01PacketEncryptionRequest;
import net.minecraft.util.IChatComponent.Serializer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SideOnly(Side.CLIENT)
public final class ClientUtils extends MinecraftInstance {
   // $FF: synthetic field
   private static final Logger logger = LogManager.getLogger("LiquidBounce");
   // $FF: synthetic field
   private static Field fastRenderField;

   public static void displayChatMessage(String llllllllllllllllllllIIIIlIllIIll) {
      if (mc.thePlayer == null) {
         getLogger().info(String.valueOf((new StringBuilder()).append("(MCChat)").append(llllllllllllllllllllIIIIlIllIIll)));
      } else {
         JsonObject llllllllllllllllllllIIIIlIllIIlI = new JsonObject();
         llllllllllllllllllllIIIIlIllIIlI.addProperty("text", llllllllllllllllllllIIIIlIllIIll);
         mc.thePlayer.addChatMessage(Serializer.jsonToComponent(llllllllllllllllllllIIIIlIllIIlI.toString()));
      }
   }

   public static void sendEncryption(NetworkManager llllllllllllllllllllIIIIlIlllIIl, SecretKey llllllllllllllllllllIIIIlIlllIII, PublicKey llllllllllllllllllllIIIIlIllIlll, S01PacketEncryptionRequest llllllllllllllllllllIIIIlIlllIlI) {
      llllllllllllllllllllIIIIlIlllIIl.sendPacket(new C01PacketEncryptionResponse(llllllllllllllllllllIIIIlIlllIII, llllllllllllllllllllIIIIlIllIlll, llllllllllllllllllllIIIIlIlllIlI.getVerifyToken()), (llllllllllllllllllllIIIIlIlIlIll) -> {
         llllllllllllllllllllIIIIlIlllIIl.enableEncryption(llllllllllllllllllllIIIIlIlllIII);
      }, new GenericFutureListener[0]);
   }

   public static void disableFastRender() {
      try {
         if (fastRenderField != null) {
            if (!fastRenderField.isAccessible()) {
               fastRenderField.setAccessible(true);
            }

            fastRenderField.setBoolean(mc.gameSettings, false);
         }
      } catch (IllegalAccessException var2) {
      }

   }

   public static Logger getLogger() {
      return logger;
   }

   static {
      try {
         fastRenderField = GameSettings.class.getDeclaredField("ofFastRender");
         if (!fastRenderField.isAccessible()) {
            fastRenderField.setAccessible(true);
         }
      } catch (NoSuchFieldException var2) {
      }

   }
}
