package net.ccbluex.liquidbounce.features.command.commands;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.features.command.CommandManager;
import net.ccbluex.liquidbounce.utils.misc.StringUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\b¨\u0006\t"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/ShortcutCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "LiquidSense"}
)
public final class ShortcutCommand extends Command {
   public ShortcutCommand() {
      super("shortcut", new String[0]);
   }

   public void execute(@NotNull String[] llIllllIIlIllIl) {
      Intrinsics.checkParameterIsNotNull(llIllllIIlIllIl, "args");
      if (llIllllIIlIllIl.length > 3 && StringsKt.equals(llIllllIIlIllIl[1], "add", true)) {
         String var10001;
         try {
            CommandManager var10000 = LiquidBounce.INSTANCE.getCommandManager();
            var10001 = llIllllIIlIllIl[2];
            String var10002 = StringUtils.toCompleteString(llIllllIIlIllIl, 3);
            Intrinsics.checkExpressionValueIsNotNull(var10002, "StringUtils.toCompleteString(args, 3)");
            var10000.registerShortcut(var10001, var10002);
            llIllllIIlIlllI.chat("Successfully added shortcut.");
         } catch (IllegalArgumentException var3) {
            var10001 = var3.getMessage();
            if (var10001 == null) {
               Intrinsics.throwNpe();
            }

            llIllllIIlIlllI.chat(var10001);
         }
      } else if (llIllllIIlIllIl.length >= 3 && StringsKt.equals(llIllllIIlIllIl[1], "remove", true)) {
         if (LiquidBounce.INSTANCE.getCommandManager().unregisterShortcut(llIllllIIlIllIl[2])) {
            llIllllIIlIlllI.chat("Successfully removed shortcut.");
         } else {
            llIllllIIlIlllI.chat("Shortcut does not exist.");
         }
      } else {
         llIllllIIlIlllI.chat("shortcut <add <shortcut_name> <script>/remove <shortcut_name>>");
      }

   }
}
