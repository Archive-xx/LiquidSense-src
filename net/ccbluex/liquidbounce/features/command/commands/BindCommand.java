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
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.Notification;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\bJ!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\u000b¨\u0006\f"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/BindCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "LiquidSense"}
)
public final class BindCommand extends Command {
   public void execute(@NotNull String[] lIIlllIlllIIlIl) {
      Intrinsics.checkParameterIsNotNull(lIIlllIlllIIlIl, "args");
      if (lIIlllIlllIIlIl.length > 2) {
         long lIIlllIlllIIlII = LiquidBounce.INSTANCE.getModuleManager().getModule(lIIlllIlllIIlIl[1]);
         if (lIIlllIlllIIlII == null) {
            lIIlllIlllIlIII.chat(String.valueOf((new StringBuilder()).append("Module §a§l").append(lIIlllIlllIIlIl[1]).append("§3 not found.")));
         } else {
            short lIIlllIlllIIIlI = lIIlllIlllIIlIl[2];
            boolean lIIlllIlllIIIIl = false;
            if (lIIlllIlllIIIlI == null) {
               throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            } else {
               String var10000 = lIIlllIlllIIIlI.toUpperCase();
               Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).toUpperCase()");
               double lIIlllIlllIIIll = Keyboard.getKeyIndex(var10000);
               lIIlllIlllIIlII.setKeyBind(lIIlllIlllIIIll);
               lIIlllIlllIlIII.chat(String.valueOf((new StringBuilder()).append("Bound module §a§l").append(lIIlllIlllIIlII.getName()).append("§3 to key §a§l").append(Keyboard.getKeyName(lIIlllIlllIIIll)).append("§3.")));
               LiquidBounce.INSTANCE.getHud().addNotification(new Notification(String.valueOf((new StringBuilder()).append("Bound ").append(lIIlllIlllIIlII.getName()).append(" to ").append(Keyboard.getKeyName(lIIlllIlllIIIll)))));
               boolean var10001 = false;
               lIIlllIlllIlIII.playEdit();
            }
         }
      } else {
         lIIlllIlllIlIII.chatSyntax(new String[]{"<module> <key>", "<module> none"});
      }
   }

   @NotNull
   public List<String> tabComplete(@NotNull String[] lIIlllIllIIIIIl) {
      Intrinsics.checkParameterIsNotNull(lIIlllIllIIIIIl, "args");
      Exception lIIlllIlIlllllI = false;
      if (lIIlllIllIIIIIl.length == 0) {
         return CollectionsKt.emptyList();
      } else {
         String lIIlllIllIIIIll = lIIlllIllIIIIIl[0];
         List var10000;
         switch(lIIlllIllIIIIIl.length) {
         case 1:
            Exception lIIlllIlIlllllI = (Iterable)LiquidBounce.INSTANCE.getModuleManager().getModules();
            int lIIlllIllIIIlII = false;
            Exception lIIlllIllIIIlll = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault(lIIlllIlIlllllI, 10)));
            long lIIlllIlIlllIlI = false;

            Iterator lIIlllIlIlllIIl;
            Object lIIlllIllIIlIIl;
            boolean lIIlllIlIllIllI;
            boolean var10001;
            for(lIIlllIlIlllIIl = lIIlllIlIlllllI.iterator(); lIIlllIlIlllIIl.hasNext(); var10001 = false) {
               lIIlllIllIIlIIl = lIIlllIlIlllIIl.next();
               float lIIlllIlIllIlll = (Module)lIIlllIllIIlIIl;
               lIIlllIlIllIllI = false;
               short lIIlllIlIllIlII = lIIlllIlIllIlll.getName();
               lIIlllIllIIIlll.add(lIIlllIlIllIlII);
            }

            lIIlllIlIlllllI = (Iterable)((List)lIIlllIllIIIlll);
            lIIlllIllIIIlII = false;
            lIIlllIllIIIlll = (Collection)(new ArrayList());
            lIIlllIlIlllIlI = false;
            lIIlllIlIlllIIl = lIIlllIlIlllllI.iterator();

            while(lIIlllIlIlllIIl.hasNext()) {
               lIIlllIllIIlIIl = lIIlllIlIlllIIl.next();
               float lIIlllIlIllIlll = (String)lIIlllIllIIlIIl;
               lIIlllIlIllIllI = false;
               if (StringsKt.startsWith(lIIlllIlIllIlll, lIIlllIllIIIIll, true)) {
                  lIIlllIllIIIlll.add(lIIlllIllIIlIIl);
                  var10001 = false;
               }
            }

            var10000 = CollectionsKt.toList((Iterable)((List)lIIlllIllIIIlll));
            break;
         default:
            var10000 = CollectionsKt.emptyList();
         }

         return var10000;
      }
   }

   public BindCommand() {
      float lIIlllIlIlIllII = "bind";
      String lIIlllIlIlIlIll = new String[0];
      super(lIIlllIlIlIllII, lIIlllIlIlIlIll);
   }
}
