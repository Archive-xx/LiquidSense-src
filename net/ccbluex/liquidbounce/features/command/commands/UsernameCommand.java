//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.command.commands;

import java.awt.Toolkit;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\b¨\u0006\t"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/UsernameCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "LiquidSense"}
)
public final class UsernameCommand extends Command {
   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   public UsernameCommand() {
      float llllllllllllllllllIIlllIlIlIIIlI = "username";
      float llllllllllllllllllIIlllIlIlIIIII = new String[0];
      super(llllllllllllllllllIIlllIlIlIIIlI, llllllllllllllllllIIlllIlIlIIIII);
   }

   public void execute(@NotNull String[] llllllllllllllllllIIlllIlIllllII) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIIlllIlIllllII, "args");
      EntityPlayerSP var10000 = access$getMc$p$s1046033730().thePlayer;
      Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
      String llllllllllllllllllIIlllIlIlllllI = var10000.getName();
      llllllllllllllllllIIlllIlIlllIll.chat(String.valueOf((new StringBuilder()).append("Username: ").append(llllllllllllllllllIIlllIlIlllllI)));
      StringSelection llllllllllllllllllIIlllIlIllllll = new StringSelection(llllllllllllllllllIIlllIlIlllllI);
      Toolkit var5 = Toolkit.getDefaultToolkit();
      Intrinsics.checkExpressionValueIsNotNull(var5, "Toolkit.getDefaultToolkit()");
      var5.getSystemClipboard().setContents((Transferable)llllllllllllllllllIIlllIlIllllll, (ClipboardOwner)llllllllllllllllllIIlllIlIllllll);
   }
}
