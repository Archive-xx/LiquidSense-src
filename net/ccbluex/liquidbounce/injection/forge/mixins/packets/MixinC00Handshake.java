//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.injection.forge.mixins.packets;

import net.ccbluex.liquidbounce.features.special.AntiForge;
import net.minecraft.client.Minecraft;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@SideOnly(Side.CLIENT)
@Mixin({C00Handshake.class})
public class MixinC00Handshake {
   // $FF: synthetic field
   @Shadow
   public String ip;
   // $FF: synthetic field
   @Shadow
   public int port;
   // $FF: synthetic field
   @Shadow
   private int protocolVersion;
   // $FF: synthetic field
   @Shadow
   private EnumConnectionState requestedState;

   @Overwrite
   public void writePacketData(PacketBuffer lIllIllllIll) {
      lIllIllllIll.writeVarIntToBuffer(lIllIllllllI.protocolVersion);
      lIllIllllIll.writeString(String.valueOf((new StringBuilder()).append(lIllIllllllI.ip).append(AntiForge.enabled && AntiForge.blockFML && !Minecraft.getMinecraft().isIntegratedServerRunning() ? "" : "\u0000FML\u0000")));
      boolean var10001 = false;
      lIllIllllIll.writeShort(lIllIllllllI.port);
      var10001 = false;
      lIllIllllIll.writeVarIntToBuffer(lIllIllllllI.requestedState.getId());
   }
}
