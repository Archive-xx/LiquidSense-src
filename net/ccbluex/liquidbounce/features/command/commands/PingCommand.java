//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.command.commands;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\b¨\u0006\t"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/PingCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "LiquidSense"}
)
public final class PingCommand extends Command {
   public PingCommand() {
      Exception lIIIllllIIlIIlI = "ping";
      float lIIIllllIIlIIIl = new String[0];
      super(lIIIllllIIlIIlI, lIIIllllIIlIIIl);
   }

   public void execute(@NotNull String[] lIIIllllIIlllII) {
      Intrinsics.checkParameterIsNotNull(lIIIllllIIlllII, "args");
      StringBuilder var10001 = (new StringBuilder()).append("§3Your ping is §a");
      Minecraft var10002 = access$getMc$p$s1046033730();
      Intrinsics.checkExpressionValueIsNotNull(var10002, "mc");
      NetHandlerPlayClient var3 = var10002.getNetHandler();
      EntityPlayerSP var10003 = access$getMc$p$s1046033730().thePlayer;
      Intrinsics.checkExpressionValueIsNotNull(var10003, "mc.thePlayer");
      NetworkPlayerInfo var4 = var3.getPlayerInfo(var10003.getUniqueID());
      Intrinsics.checkExpressionValueIsNotNull(var4, "mc.netHandler.getPlayerInfo(mc.thePlayer.uniqueID)");
      lIIIllllIIllIll.chat(String.valueOf(var10001.append(var4.getResponseTime()).append("ms§3.")));
   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }
}
