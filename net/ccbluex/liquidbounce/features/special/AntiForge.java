//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.special;

import io.netty.buffer.Unpooled;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class AntiForge extends MinecraftInstance implements Listenable {
   // $FF: synthetic field
   public static boolean blockProxyPacket = true;
   // $FF: synthetic field
   public static boolean blockPayloadPackets = true;
   // $FF: synthetic field
   public static boolean blockFML = true;
   // $FF: synthetic field
   public static boolean enabled = true;

   public boolean handleEvents() {
      return true;
   }

   @EventTarget
   public void onPacket(PacketEvent lllllllllllllllllllIIllIIIlIIlIl) {
      Packet<?> lllllllllllllllllllIIllIIIlIIIlI = lllllllllllllllllllIIllIIIlIIlIl.getPacket();
      if (enabled && !mc.isIntegratedServerRunning()) {
         try {
            if (blockProxyPacket && lllllllllllllllllllIIllIIIlIIIlI.getClass().getName().equals("net.minecraftforge.fml.common.network.internal.FMLProxyPacket")) {
               lllllllllllllllllllIIllIIIlIIlIl.cancelEvent();
            }

            if (blockPayloadPackets && lllllllllllllllllllIIllIIIlIIIlI instanceof C17PacketCustomPayload) {
               C17PacketCustomPayload lllllllllllllllllllIIllIIIlIlIII = (C17PacketCustomPayload)lllllllllllllllllllIIllIIIlIIIlI;
               if (!lllllllllllllllllllIIllIIIlIlIII.getChannelName().startsWith("MC|")) {
                  lllllllllllllllllllIIllIIIlIIlIl.cancelEvent();
               } else if (lllllllllllllllllllIIllIIIlIlIII.getChannelName().equalsIgnoreCase("MC|Brand")) {
                  lllllllllllllllllllIIllIIIlIlIII.data = (new PacketBuffer(Unpooled.buffer())).writeString("vanilla");
               }
            }
         } catch (Exception var4) {
            var4.printStackTrace();
         }
      }

   }
}
