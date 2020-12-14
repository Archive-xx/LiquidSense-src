//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.special;

import java.text.MessageFormat;
import java.util.Random;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.Packet;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BungeeCordSpoof extends MinecraftInstance implements Listenable {
   // $FF: synthetic field
   public static boolean enabled = false;

   private String getRandomIpPart() {
      return String.valueOf((new StringBuilder()).append((new Random()).nextInt(2)).append("").append((new Random()).nextInt(5)).append("").append((new Random()).nextInt(5)));
   }

   public boolean handleEvents() {
      return true;
   }

   @EventTarget
   public void onPacket(PacketEvent llllllllllllllllllIlllIllllllIll) {
      Packet<?> llllllllllllllllllIlllIllllllIlI = llllllllllllllllllIlllIllllllIll.getPacket();
      if (llllllllllllllllllIlllIllllllIlI instanceof C00Handshake && enabled && ((C00Handshake)llllllllllllllllllIlllIllllllIlI).getRequestedState() == EnumConnectionState.LOGIN) {
         C00Handshake llllllllllllllllllIllllIIIIIIIII = (C00Handshake)llllllllllllllllllIlllIllllllIlI;
         llllllllllllllllllIllllIIIIIIIII.ip = String.valueOf((new StringBuilder()).append(llllllllllllllllllIllllIIIIIIIII.ip).append("\u0000").append(MessageFormat.format("{0}.{1}.{2}.{3}", llllllllllllllllllIlllIlllllllll.getRandomIpPart(), llllllllllllllllllIlllIlllllllll.getRandomIpPart(), llllllllllllllllllIlllIlllllllll.getRandomIpPart(), llllllllllllllllllIlllIlllllllll.getRandomIpPart())).append("\u0000").append(mc.getSession().getPlayerID().replace("-", "")));
      }

   }
}
