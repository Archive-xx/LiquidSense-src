//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.command.commands;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.handshake.client.C00Handshake;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u001b\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u000bH\u0016¢\u0006\u0002\u0010\fJ\b\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u0011H\u0007R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0012"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/ServerInfoCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "Lnet/ccbluex/liquidbounce/event/Listenable;", "()V", "ip", "", "port", "", "execute", "", "args", "", "([Ljava/lang/String;)V", "handleEvents", "", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "LiquidSense"}
)
public final class ServerInfoCommand extends Command implements Listenable {
   // $FF: synthetic field
   private String ip;
   // $FF: synthetic field
   private int port;

   public ServerInfoCommand() {
      long llllllllllllllllllIllllIlIlIlIlI = "serverinfo";
      Exception llllllllllllllllllIllllIlIlIlIIl = new String[0];
      super(llllllllllllllllllIllllIlIlIlIlI, llllllllllllllllllIllllIlIlIlIIl);
      LiquidBounce.INSTANCE.getEventManager().registerListener((Listenable)llllllllllllllllllIllllIlIlIllIl);
      llllllllllllllllllIllllIlIlIllIl.ip = "";
   }

   public boolean handleEvents() {
      return true;
   }

   @EventTarget
   public final void onPacket(@NotNull PacketEvent llllllllllllllllllIllllIlIllIllI) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIllllIlIllIllI, "event");
      Packet llllllllllllllllllIllllIlIlllIII = llllllllllllllllllIllllIlIllIllI.getPacket();
      if (llllllllllllllllllIllllIlIlllIII instanceof C00Handshake) {
         String var10001 = ((C00Handshake)llllllllllllllllllIllllIlIlllIII).ip;
         Intrinsics.checkExpressionValueIsNotNull(var10001, "packet.ip");
         llllllllllllllllllIllllIlIllIlIl.ip = var10001;
         llllllllllllllllllIllllIlIllIlIl.port = ((C00Handshake)llllllllllllllllllIllllIlIlllIII).port;
      }

   }

   public void execute(@NotNull String[] llllllllllllllllllIllllIlIllllll) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIllllIlIllllll, "args");
      Minecraft var10000 = access$getMc$p$s1046033730();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "mc");
      if (var10000.getCurrentServerData() == null) {
         llllllllllllllllllIllllIllIIIIII.chat("This command does not work in single player.");
      } else {
         var10000 = access$getMc$p$s1046033730();
         Intrinsics.checkExpressionValueIsNotNull(var10000, "mc");
         char llllllllllllllllllIllllIlIllllII = var10000.getCurrentServerData();
         llllllllllllllllllIllllIllIIIIII.chat("Server infos:");
         llllllllllllllllllIllllIllIIIIII.chat(String.valueOf((new StringBuilder()).append("§7Name: §8").append(llllllllllllllllllIllllIlIllllII.serverName)));
         llllllllllllllllllIllllIllIIIIII.chat(String.valueOf((new StringBuilder()).append("§7IP: §8").append(llllllllllllllllllIllllIllIIIIII.ip).append(':').append(llllllllllllllllllIllllIllIIIIII.port)));
         llllllllllllllllllIllllIllIIIIII.chat(String.valueOf((new StringBuilder()).append("§7Players: §8").append(llllllllllllllllllIllllIlIllllII.populationInfo)));
         llllllllllllllllllIllllIllIIIIII.chat(String.valueOf((new StringBuilder()).append("§7MOTD: §8").append(llllllllllllllllllIllllIlIllllII.serverMOTD)));
         llllllllllllllllllIllllIllIIIIII.chat(String.valueOf((new StringBuilder()).append("§7ServerVersion: §8").append(llllllllllllllllllIllllIlIllllII.gameVersion)));
         llllllllllllllllllIllllIllIIIIII.chat(String.valueOf((new StringBuilder()).append("§7ProtocolVersion: §8").append(llllllllllllllllllIllllIlIllllII.version)));
         llllllllllllllllllIllllIllIIIIII.chat(String.valueOf((new StringBuilder()).append("§7Ping: §8").append(llllllllllllllllllIllllIlIllllII.pingToServer)));
      }
   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }
}
