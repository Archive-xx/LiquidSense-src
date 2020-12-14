package net.ccbluex.liquidbounce.features.command.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\b¨\u0006\t"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/BindsCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "LiquidSense"}
)
public final class BindsCommand extends Command {
   public BindsCommand() {
      char llIIIIIIIIIllII = "binds";
      Exception llIIIIIIIIIlIll = new String[0];
      super(llIIIIIIIIIllII, llIIIIIIIIIlIll);
   }

   public void execute(@NotNull String[] llIIIIIIIIlllll) {
      Intrinsics.checkParameterIsNotNull(llIIIIIIIIlllll, "args");
      if (llIIIIIIIIlllll.length > 1 && StringsKt.equals(llIIIIIIIIlllll[1], "clear", true)) {
         Iterator llIIIIIIIIllIll = LiquidBounce.INSTANCE.getModuleManager().getModules().iterator();

         while(llIIIIIIIIllIll.hasNext()) {
            Module llIIIIIIIlIlllI = (Module)llIIIIIIIIllIll.next();
            llIIIIIIIlIlllI.setKeyBind(0);
         }

         llIIIIIIIIllllI.chat("Removed all binds.");
      } else {
         llIIIIIIIIllllI.chat("§c§lBinds");
         Iterable llIIIIIIIlIIIlI = (Iterable)LiquidBounce.INSTANCE.getModuleManager().getModules();
         int llIIIIIIIlIIIIl = false;
         String llIIIIIIIIllIIl = (Collection)(new ArrayList());
         int llIIIIIIIlIlIII = false;
         Iterator llIIIIIIIIlIlll = llIIIIIIIlIIIlI.iterator();

         while(llIIIIIIIIlIlll.hasNext()) {
            char llIIIIIIIIlIllI = llIIIIIIIIlIlll.next();
            String llIIIIIIIIlIlIl = (Module)llIIIIIIIIlIllI;
            Exception llIIIIIIIIlIlII = false;
            if (llIIIIIIIIlIlIl.getKeyBind() != 0) {
               llIIIIIIIIllIIl.add(llIIIIIIIIlIllI);
               boolean var10001 = false;
            }
         }

         llIIIIIIIlIIIlI = (Iterable)((List)llIIIIIIIIllIIl);
         llIIIIIIIlIIIIl = false;
         Iterator llIIIIIIIIllIlI = llIIIIIIIlIIIlI.iterator();

         while(llIIIIIIIIllIlI.hasNext()) {
            String llIIIIIIIIllIIl = llIIIIIIIIllIlI.next();
            byte llIIIIIIIIllIII = (Module)llIIIIIIIIllIIl;
            Exception llIIIIIIIIlIlll = false;
            ClientUtils.displayChatMessage(String.valueOf((new StringBuilder()).append("§6> §c").append(llIIIIIIIIllIII.getName()).append(": §a§l").append(Keyboard.getKeyName(llIIIIIIIIllIII.getKeyBind()))));
         }

         llIIIIIIIIllllI.chatSyntax("binds clear");
      }
   }
}
