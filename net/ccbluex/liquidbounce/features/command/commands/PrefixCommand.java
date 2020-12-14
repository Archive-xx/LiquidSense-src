package net.ccbluex.liquidbounce.features.command.commands;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.Command;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\b¨\u0006\t"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/PrefixCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "LiquidSense"}
)
public final class PrefixCommand extends Command {
   public void execute(@NotNull String[] llIlIIlIlllIlII) {
      Intrinsics.checkParameterIsNotNull(llIlIIlIlllIlII, "args");
      if (llIlIIlIlllIlII.length <= 1) {
         llIlIIlIlllIlIl.chatSyntax("prefix <character>");
      } else {
         boolean llIlIIlIlllIIll = llIlIIlIlllIlII[1];
         if (llIlIIlIlllIIll.length() > 1) {
            llIlIIlIlllIlIl.chat("§cPrefix can only be one character long!");
         } else {
            LiquidBounce.INSTANCE.getCommandManager().setPrefix(StringsKt.single((CharSequence)llIlIIlIlllIIll));
            LiquidBounce.INSTANCE.getFileManager().saveConfig(LiquidBounce.INSTANCE.getFileManager().valuesConfig);
            llIlIIlIlllIlIl.chat(String.valueOf((new StringBuilder()).append("Successfully changed command prefix to '§8").append(llIlIIlIlllIIll).append("§3'")));
         }
      }
   }

   public PrefixCommand() {
      byte llIlIIlIllIIIlI = "prefix";
      double llIlIIlIllIIIIl = new String[0];
      super(llIlIIlIllIIIlI, llIlIIlIllIIIIl);
   }
}
