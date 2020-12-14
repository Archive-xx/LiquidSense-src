//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.command.commands;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.ui.client.altmanager.GuiAltManager;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.ServerUtils;
import net.ccbluex.liquidbounce.utils.login.MinecraftAccount;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\b¨\u0006\t"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/LoginCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "LiquidSense"}
)
public final class LoginCommand extends Command {
   public LoginCommand() {
      short llllIIllIlIlll = "login";
      char llllIIllIlIllI = new String[0];
      super(llllIIllIlIlll, llllIIllIlIllI);
   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   public void execute(@NotNull String[] llllIIlllIIIlI) {
      Intrinsics.checkParameterIsNotNull(llllIIlllIIIlI, "args");
      if (llllIIlllIIIlI.length <= 1) {
         llllIIlllIIIll.chatSyntax("login <username/email> [password]");
      } else {
         String var10000;
         if (llllIIlllIIIlI.length > 2) {
            var10000 = GuiAltManager.login(new MinecraftAccount(llllIIlllIIIlI[1], llllIIlllIIIlI[2]));
            Intrinsics.checkExpressionValueIsNotNull(var10000, "GuiAltManager.login(Mine…ccount(args[1], args[2]))");
         } else {
            var10000 = GuiAltManager.login(new MinecraftAccount(llllIIlllIIIlI[1]));
            Intrinsics.checkExpressionValueIsNotNull(var10000, "GuiAltManager.login(MinecraftAccount(args[1]))");
         }

         String llllIIlllIIlII = var10000;
         llllIIlllIIIll.chat(llllIIlllIIlII);
         if (StringsKt.startsWith$default(llllIIlllIIlII, "§cYour name is now", false, 2, (Object)null)) {
            Minecraft var3 = access$getMc$p$s1046033730();
            Intrinsics.checkExpressionValueIsNotNull(var3, "mc");
            if (var3.isIntegratedServerRunning()) {
               return;
            }

            access$getMc$p$s1046033730().theWorld.sendQuittingDisconnectingPacket();
            ServerUtils.connectToLastServer();
         }

      }
   }
}
