package net.ccbluex.liquidbounce.features.command.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\bJ!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\u000b¨\u0006\f"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/TargetCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "LiquidSense"}
)
public final class TargetCommand extends Command {
   public TargetCommand() {
      float lllllllllllllllllIlIlllllIlIIIIl = "target";
      Exception lllllllllllllllllIlIlllllIlIIIII = new String[0];
      super(lllllllllllllllllIlIlllllIlIIIIl, lllllllllllllllllIlIlllllIlIIIII);
   }

   public void execute(@NotNull String[] lllllllllllllllllIlIllllllIIlIIl) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllIlIllllllIIlIIl, "args");
      if (lllllllllllllllllIlIllllllIIlIIl.length > 1) {
         if (StringsKt.equals(lllllllllllllllllIlIllllllIIlIIl[1], "players", true)) {
            EntityUtils.targetPlayer = !EntityUtils.targetPlayer;
            lllllllllllllllllIlIllllllIIlIlI.chat(String.valueOf((new StringBuilder()).append("§7Target player toggled ").append(EntityUtils.targetPlayer ? "on" : "off").append('.')));
            lllllllllllllllllIlIllllllIIlIlI.playEdit();
            return;
         }

         if (StringsKt.equals(lllllllllllllllllIlIllllllIIlIIl[1], "mobs", true)) {
            EntityUtils.targetMobs = !EntityUtils.targetMobs;
            lllllllllllllllllIlIllllllIIlIlI.chat(String.valueOf((new StringBuilder()).append("§7Target mobs toggled ").append(EntityUtils.targetMobs ? "on" : "off").append('.')));
            lllllllllllllllllIlIllllllIIlIlI.playEdit();
            return;
         }

         if (StringsKt.equals(lllllllllllllllllIlIllllllIIlIIl[1], "animals", true)) {
            EntityUtils.targetAnimals = !EntityUtils.targetAnimals;
            lllllllllllllllllIlIllllllIIlIlI.chat(String.valueOf((new StringBuilder()).append("§7Target animals toggled ").append(EntityUtils.targetAnimals ? "on" : "off").append('.')));
            lllllllllllllllllIlIllllllIIlIlI.playEdit();
            return;
         }

         if (StringsKt.equals(lllllllllllllllllIlIllllllIIlIIl[1], "invisible", true)) {
            EntityUtils.targetInvisible = !EntityUtils.targetInvisible;
            lllllllllllllllllIlIllllllIIlIlI.chat(String.valueOf((new StringBuilder()).append("§7Target Invisible toggled ").append(EntityUtils.targetInvisible ? "on" : "off").append('.')));
            lllllllllllllllllIlIllllllIIlIlI.playEdit();
            return;
         }
      }

      lllllllllllllllllIlIllllllIIlIlI.chatSyntax("target <players/mobs/animals/invisible>");
   }

   @NotNull
   public List<String> tabComplete(@NotNull String[] lllllllllllllllllIlIlllllIllIIll) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllIlIlllllIllIIll, "args");
      double lllllllllllllllllIlIlllllIllIIII = false;
      if (lllllllllllllllllIlIlllllIllIIll.length == 0) {
         return CollectionsKt.emptyList();
      } else {
         List var10000;
         switch(lllllllllllllllllIlIlllllIllIIll.length) {
         case 1:
            double lllllllllllllllllIlIlllllIllIIIl = (Iterable)CollectionsKt.listOf(new String[]{"players", "mobs", "animals", "invisible"});
            lllllllllllllllllIlIlllllIllIIII = false;
            Collection lllllllllllllllllIlIlllllIlllIII = (Collection)(new ArrayList());
            char lllllllllllllllllIlIlllllIlIllIl = false;
            Iterator lllllllllllllllllIlIlllllIlIllII = lllllllllllllllllIlIlllllIllIIIl.iterator();

            while(lllllllllllllllllIlIlllllIlIllII.hasNext()) {
               Object lllllllllllllllllIlIlllllIlllIlI = lllllllllllllllllIlIlllllIlIllII.next();
               String lllllllllllllllllIlIlllllIllllII = (String)lllllllllllllllllIlIlllllIlllIlI;
               Exception lllllllllllllllllIlIlllllIlIlIIl = false;
               if (StringsKt.startsWith(lllllllllllllllllIlIlllllIllllII, lllllllllllllllllIlIlllllIllIIll[0], true)) {
                  lllllllllllllllllIlIlllllIlllIII.add(lllllllllllllllllIlIlllllIlllIlI);
                  boolean var10001 = false;
               }
            }

            var10000 = (List)lllllllllllllllllIlIlllllIlllIII;
            break;
         default:
            var10000 = CollectionsKt.emptyList();
         }

         return var10000;
      }
   }
}
