//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.utils;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import java.util.Comparator;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.world.WorldSettings.GameType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiPlayerTabOverlay extends Gui {
   // $FF: synthetic field
   public static Ordering<NetworkPlayerInfo> field_175252_a = Ordering.from(new GuiPlayerTabOverlay.PlayerComparator());

   @SideOnly(Side.CLIENT)
   static class PlayerComparator implements Comparator<NetworkPlayerInfo> {
      private PlayerComparator() {
      }

      // $FF: synthetic method
      PlayerComparator(Object llIIIlIIIllIlll) {
         this();
      }

      public int compare(NetworkPlayerInfo llIIIlIIlIIIlII, NetworkPlayerInfo llIIIlIIlIIIIll) {
         short llIIIlIIlIIIIlI = llIIIlIIlIIIlII.getPlayerTeam();
         float llIIIlIIlIIIIIl = llIIIlIIlIIIIll.getPlayerTeam();
         return ComparisonChain.start().compareTrueFirst(llIIIlIIlIIIlII.getGameType() != GameType.SPECTATOR, llIIIlIIlIIIIll.getGameType() != GameType.SPECTATOR).compare(llIIIlIIlIIIIlI != null ? llIIIlIIlIIIIlI.getRegisteredName() : "", llIIIlIIlIIIIIl != null ? llIIIlIIlIIIIIl.getRegisteredName() : "").compare(llIIIlIIlIIIlII.getGameProfile().getName(), llIIIlIIlIIIIll.getGameProfile().getName()).result();
      }
   }
}
