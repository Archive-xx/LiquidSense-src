package net.ccbluex.liquidbounce.features.command.shortcuts;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.features.command.Command;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u001e\u0010\u0004\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00070\u00060\u0005¢\u0006\u0002\u0010\bJ\u001b\u0010\r\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007H\u0016¢\u0006\u0002\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR)\u0010\u0004\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00070\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0011"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/command/shortcuts/Shortcut;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "name", "", "script", "", "Lkotlin/Pair;", "", "(Ljava/lang/String;Ljava/util/List;)V", "getName", "()Ljava/lang/String;", "getScript", "()Ljava/util/List;", "execute", "", "args", "([Ljava/lang/String;)V", "LiquidSense"}
)
public final class Shortcut extends Command {
   // $FF: synthetic field
   @NotNull
   private final List<Pair<Command, String[]>> script;
   // $FF: synthetic field
   @NotNull
   private final String name;

   public void execute(@NotNull String[] lllllllllllllllllllIIIllIlllIlII) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllllIIIllIlllIlII, "args");
      Iterable lllllllllllllllllllIIIllIlllIlll = (Iterable)lllllllllllllllllllIIIllIlllIIll.script;
      int lllllllllllllllllllIIIllIlllIllI = false;
      Iterator lllllllllllllllllllIIIllIllIllll = lllllllllllllllllllIIIllIlllIlll.iterator();

      while(lllllllllllllllllllIIIllIllIllll.hasNext()) {
         char lllllllllllllllllllIIIllIllIlllI = lllllllllllllllllllIIIllIllIllll.next();
         Pair lllllllllllllllllllIIIllIllllIlI = (Pair)lllllllllllllllllllIIIllIllIlllI;
         int lllllllllllllllllllIIIllIllllIIl = false;
         ((Command)lllllllllllllllllllIIIllIllllIlI.getFirst()).execute((String[])lllllllllllllllllllIIIllIllllIlI.getSecond());
      }

   }

   public Shortcut(@NotNull String lllllllllllllllllllIIIllIllIIIIl, @NotNull List<? extends Pair<? extends Command, String[]>> lllllllllllllllllllIIIllIlIlllIl) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllllIIIllIllIIIIl, "name");
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllllIIIllIlIlllIl, "script");
      super(lllllllllllllllllllIIIllIllIIIIl, new String[0]);
      lllllllllllllllllllIIIllIllIIIlI.name = lllllllllllllllllllIIIllIllIIIIl;
      lllllllllllllllllllIIIllIllIIIlI.script = lllllllllllllllllllIIIllIlIlllIl;
   }

   @NotNull
   public final List<Pair<Command, String[]>> getScript() {
      return lllllllllllllllllllIIIllIllIIlll.script;
   }

   @NotNull
   public final String getName() {
      return lllllllllllllllllllIIIllIllIlIIl.name;
   }
}
