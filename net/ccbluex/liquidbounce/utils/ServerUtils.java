//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.utils;

import net.ccbluex.liquidbounce.ui.client.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public final class ServerUtils extends MinecraftInstance {
   // $FF: synthetic field
   public static ServerData serverData;

   public static String getRemoteIp() {
      String llIlllIlIlIIIl = "Singleplayer";
      if (mc.theWorld.isRemote) {
         ServerData llIlllIlIlIIll = mc.getCurrentServerData();
         if (llIlllIlIlIIll != null) {
            llIlllIlIlIIIl = llIlllIlIlIIll.serverIP;
         }
      }

      return llIlllIlIlIIIl;
   }

   public static void connectToLastServer() {
      if (serverData != null) {
         mc.displayGuiScreen(new GuiConnecting(new GuiMultiplayer(new GuiMainMenu()), mc, serverData));
      }
   }
}
