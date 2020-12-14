package net.ccbluex.liquidbounce.features.command.commands;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\b¨\u0006\t"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/HClipCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "LiquidSense"}
)
public final class HClipCommand extends Command {
   public HClipCommand() {
      byte lIIIllIlIlIIlll = "hclip";
      float lIIIllIlIlIIllI = new String[0];
      super(lIIIllIlIlIIlll, lIIIllIlIlIIllI);
   }

   public void execute(@NotNull String[] lIIIllIlIllIIll) {
      Intrinsics.checkParameterIsNotNull(lIIIllIlIllIIll, "args");
      if (lIIIllIlIllIIll.length > 1) {
         try {
            Exception lIIIllIlIllIIII = lIIIllIlIllIIll[1];
            String lIIIllIlIlIllll = false;
            MovementUtils.forward(Double.parseDouble(lIIIllIlIllIIII));
            lIIIllIlIllIlII.chat("You were teleported.");
         } catch (NumberFormatException var4) {
            lIIIllIlIllIlII.chatSyntaxError();
         }

      } else {
         lIIIllIlIllIlII.chatSyntax("hclip <value>");
      }
   }
}
