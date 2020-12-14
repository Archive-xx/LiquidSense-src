package net.ccbluex.liquidbounce.features.command.commands;

import java.util.Comparator;
import java.util.List;
import joptsimple.internal.Strings;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\b¨\u0006\t"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/HelpCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "LiquidSense"}
)
public final class HelpCommand extends Command {
   public void execute(@NotNull String[] llllIIIIIllIIlI) {
      Intrinsics.checkParameterIsNotNull(llllIIIIIllIIlI, "args");
      int llllIIIIIllIIIl = 1;
      if (llllIIIIIllIIlI.length > 1) {
         try {
            Exception llllIIIIIllIIII = llllIIIIIllIIlI[1];
            boolean llllIIIIIlIllll = false;
            llllIIIIIllIIIl = Integer.parseInt(llllIIIIIllIIII);
         } catch (NumberFormatException var14) {
            llllIIIIIllIIll.chatSyntaxError();
         }
      }

      if (llllIIIIIllIIIl <= 0) {
         llllIIIIIllIIll.chat("The number you have entered is too low, it must be over 0");
      } else {
         Exception llllIIIIIllIIII = (double)LiquidBounce.INSTANCE.getCommandManager().getCommands().size() / 8.0D;
         byte llllIIIIIlIlllI = llllIIIIIllIIII > (double)((int)llllIIIIIllIIII) ? (int)llllIIIIIllIIII + 1 : (int)llllIIIIIllIIII;
         if (llllIIIIIllIIIl > llllIIIIIlIlllI) {
            llllIIIIIllIIll.chat(String.valueOf((new StringBuilder()).append("The number you have entered is too big, it must be under ").append(llllIIIIIlIlllI).append('.')));
         } else {
            llllIIIIIllIIll.chat("§c§lHelp");
            ClientUtils.displayChatMessage(String.valueOf((new StringBuilder()).append("§7> Page: §8").append(llllIIIIIllIIIl).append(" / ").append(llllIIIIIlIlllI)));
            int llllIIIIIlIllII = (Iterable)LiquidBounce.INSTANCE.getCommandManager().getCommands();
            int llllIIIIIllllII = false;
            String llllIIIIIlIlIIl = false;
            int llllIIIIIlIlIII = (Comparator)(new HelpCommand$execute$$inlined$sortedBy$1());
            List llllIIIIIlllIIl = CollectionsKt.sortedWith(llllIIIIIlIllII, llllIIIIIlIlIII);

            for(int llllIIIIIlIllII = 8 * (llllIIIIIllIIIl - 1); llllIIIIIlIllII < 8 * llllIIIIIllIIIl && llllIIIIIlIllII < llllIIIIIlllIIl.size(); ++llllIIIIIlIllII) {
               byte llllIIIIIlIlIll = (Command)llllIIIIIlllIIl.get(llllIIIIIlIllII);
               StringBuilder var10000 = (new StringBuilder()).append("§6> §7").append(LiquidBounce.INSTANCE.getCommandManager().getPrefix()).append(llllIIIIIlIlIll.getCommand());
               double llllIIIIIlIlIlI = llllIIIIIlIlIll.getAlias();
               double llllIIIIIlIIlll = var10000;
               llllIIIIIlIlIIl = false;
               short llllIIIIIlIIllI = llllIIIIIlIlIlI.length == 0;
               ClientUtils.displayChatMessage(String.valueOf(llllIIIIIlIIlll.append(llllIIIIIlIIllI ? "" : String.valueOf((new StringBuilder()).append(" §7(§8").append(Strings.join(llllIIIIIlIlIll.getAlias(), "§7, §8")).append("§7)")))));
            }

            ClientUtils.displayChatMessage(String.valueOf((new StringBuilder()).append("§a------------\n§7> §c").append(LiquidBounce.INSTANCE.getCommandManager().getPrefix()).append("help §8<§7§lpage§8>")));
         }
      }
   }

   public HelpCommand() {
      byte llllIIIIIIllllI = "help";
      double llllIIIIIIlllIl = new String[0];
      super(llllIIIIIIllllI, llllIIIIIIlllIl);
   }
}
