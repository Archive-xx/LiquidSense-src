package net.ccbluex.liquidbounce.features.command.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.features.module.Module;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\bJ!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\u000b¨\u0006\f"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/ToggleCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "LiquidSense"}
)
public final class ToggleCommand extends Command {
   public void execute(@NotNull String[] lIllIlIIlllllll) {
      Intrinsics.checkParameterIsNotNull(lIllIlIIlllllll, "args");
      if (lIllIlIIlllllll.length > 1) {
         double lIllIlIIlllllIl = LiquidBounce.INSTANCE.getModuleManager().getModule(lIllIlIIlllllll[1]);
         if (lIllIlIIlllllIl == null) {
            lIllIlIlIIIIIll.chat(String.valueOf((new StringBuilder()).append("Module '").append(lIllIlIIlllllll[1]).append("' not found.")));
         } else {
            if (lIllIlIIlllllll.length > 2) {
               char lIllIlIIllllIll = lIllIlIIlllllll[2];
               long lIllIlIIllllIlI = false;
               if (lIllIlIIllllIll == null) {
                  throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
               }

               String var10000 = lIllIlIIllllIll.toLowerCase();
               Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).toLowerCase()");
               Exception lIllIlIIlllllII = var10000;
               if (!Intrinsics.areEqual((Object)lIllIlIIlllllII, (Object)"on") && !Intrinsics.areEqual((Object)lIllIlIIlllllII, (Object)"off")) {
                  lIllIlIlIIIIIll.chatSyntax("toggle <module> [on/off]");
                  return;
               }

               lIllIlIIlllllIl.setState(Intrinsics.areEqual((Object)lIllIlIIlllllII, (Object)"on"));
            } else {
               lIllIlIIlllllIl.toggle();
            }

            lIllIlIlIIIIIll.chat(String.valueOf((new StringBuilder()).append(lIllIlIIlllllIl.getState() ? "Enabled" : "Disabled").append(" module §8").append(lIllIlIIlllllIl.getName()).append("§3.")));
         }
      } else {
         lIllIlIlIIIIIll.chatSyntax("toggle <module> [on/off]");
      }
   }

   public ToggleCommand() {
      super("toggle", new String[]{"t"});
   }

   @NotNull
   public List<String> tabComplete(@NotNull String[] lIllIlIIIllIllI) {
      Intrinsics.checkParameterIsNotNull(lIllIlIIIllIllI, "args");
      char lIllIlIIIlIllll = false;
      if (lIllIlIIIllIllI.length == 0) {
         return CollectionsKt.emptyList();
      } else {
         String lIllIlIIIlllIlI = lIllIlIIIllIllI[0];
         List var10000;
         switch(lIllIlIIIllIllI.length) {
         case 1:
            Iterable lIllIlIIIlllllI = (Iterable)LiquidBounce.INSTANCE.getModuleManager().getModules();
            int lIllIlIIIlIlllI = false;
            float lIllIlIIlIIIIll = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault(lIllIlIIIlllllI, 10)));
            int lIllIlIIlIIIIII = false;

            Iterator lIllIlIIIlIlIII;
            Object lIllIlIIIlIIllI;
            boolean lIllIlIIlIIIlll;
            boolean var10001;
            for(lIllIlIIIlIlIII = lIllIlIIIlllllI.iterator(); lIllIlIIIlIlIII.hasNext(); var10001 = false) {
               lIllIlIIIlIIllI = lIllIlIIIlIlIII.next();
               Module lIllIlIIlIlIIll = (Module)lIllIlIIIlIIllI;
               lIllIlIIlIIIlll = false;
               int lIllIlIIIlIIIIl = lIllIlIIlIlIIll.getName();
               lIllIlIIlIIIIll.add(lIllIlIIIlIIIIl);
            }

            lIllIlIIIlllllI = (Iterable)((List)lIllIlIIlIIIIll);
            lIllIlIIIlIlllI = false;
            lIllIlIIlIIIIll = (Collection)(new ArrayList());
            lIllIlIIlIIIIII = false;
            lIllIlIIIlIlIII = lIllIlIIIlllllI.iterator();

            while(lIllIlIIIlIlIII.hasNext()) {
               lIllIlIIIlIIllI = lIllIlIIIlIlIII.next();
               String lIllIlIIlIIlIII = (String)lIllIlIIIlIIllI;
               lIllIlIIlIIIlll = false;
               if (StringsKt.startsWith(lIllIlIIlIIlIII, lIllIlIIIlllIlI, true)) {
                  lIllIlIIlIIIIll.add(lIllIlIIIlIIllI);
                  var10001 = false;
               }
            }

            var10000 = CollectionsKt.toList((Iterable)((List)lIllIlIIlIIIIll));
            break;
         default:
            var10000 = CollectionsKt.emptyList();
         }

         return var10000;
      }
   }
}
