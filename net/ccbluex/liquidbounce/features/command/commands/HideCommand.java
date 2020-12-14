package net.ccbluex.liquidbounce.features.command.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\bJ!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\u000b¨\u0006\f"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/HideCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "LiquidSense"}
)
public final class HideCommand extends Command {
   @NotNull
   public List<String> tabComplete(@NotNull String[] llllllllllllllllllIIlIlIllIIlIll) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIIlIlIllIIlIll, "args");
      double llllllllllllllllllIIlIlIllIIlIII = false;
      if (llllllllllllllllllIIlIlIllIIlIll.length == 0) {
         return CollectionsKt.emptyList();
      } else {
         short llllllllllllllllllIIlIlIllIIlIIl = llllllllllllllllllIIlIlIllIIlIll[0];
         List var10000;
         switch(llllllllllllllllllIIlIlIllIIlIll.length) {
         case 1:
            Iterable llllllllllllllllllIIlIlIllIIllll = (Iterable)LiquidBounce.INSTANCE.getModuleManager().getModules();
            int llllllllllllllllllIIlIlIllIIlllI = false;
            String llllllllllllllllllIIlIlIllIIIlIl = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault(llllllllllllllllllIIlIlIllIIllll, 10)));
            int llllllllllllllllllIIlIlIllIIIlII = false;

            Iterator llllllllllllllllllIIlIlIllIIIIll;
            Object llllllllllllllllllIIlIlIllIIIIlI;
            boolean llllllllllllllllllIIlIlIllIIIIII;
            boolean var10001;
            for(llllllllllllllllllIIlIlIllIIIIll = llllllllllllllllllIIlIlIllIIllll.iterator(); llllllllllllllllllIIlIlIllIIIIll.hasNext(); var10001 = false) {
               llllllllllllllllllIIlIlIllIIIIlI = llllllllllllllllllIIlIlIllIIIIll.next();
               byte llllllllllllllllllIIlIlIllIIIIIl = (Module)llllllllllllllllllIIlIlIllIIIIlI;
               llllllllllllllllllIIlIlIllIIIIII = false;
               Exception llllllllllllllllllIIlIlIlIlllllI = llllllllllllllllllIIlIlIllIIIIIl.getName();
               llllllllllllllllllIIlIlIllIIIlIl.add(llllllllllllllllllIIlIlIlIlllllI);
            }

            llllllllllllllllllIIlIlIllIIllll = (Iterable)((List)llllllllllllllllllIIlIlIllIIIlIl);
            llllllllllllllllllIIlIlIllIIlllI = false;
            llllllllllllllllllIIlIlIllIIIlIl = (Collection)(new ArrayList());
            llllllllllllllllllIIlIlIllIIIlII = false;
            llllllllllllllllllIIlIlIllIIIIll = llllllllllllllllllIIlIlIllIIllll.iterator();

            while(llllllllllllllllllIIlIlIllIIIIll.hasNext()) {
               llllllllllllllllllIIlIlIllIIIIlI = llllllllllllllllllIIlIlIllIIIIll.next();
               byte llllllllllllllllllIIlIlIllIIIIIl = (String)llllllllllllllllllIIlIlIllIIIIlI;
               llllllllllllllllllIIlIlIllIIIIII = false;
               if (StringsKt.startsWith(llllllllllllllllllIIlIlIllIIIIIl, llllllllllllllllllIIlIlIllIIlIIl, true)) {
                  llllllllllllllllllIIlIlIllIIIlIl.add(llllllllllllllllllIIlIlIllIIIIlI);
                  var10001 = false;
               }
            }

            var10000 = CollectionsKt.toList((Iterable)((List)llllllllllllllllllIIlIlIllIIIlIl));
            break;
         default:
            var10000 = CollectionsKt.emptyList();
         }

         return var10000;
      }
   }

   public HideCommand() {
      long llllllllllllllllllIIlIlIlIllIllI = "hide";
      byte llllllllllllllllllIIlIlIlIllIlIl = new String[0];
      super(llllllllllllllllllIIlIlIlIllIllI, llllllllllllllllllIIlIlIlIllIlIl);
   }

   public void execute(@NotNull String[] llllllllllllllllllIIlIlIllllIllI) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIIlIlIllllIllI, "args");
      if (llllllllllllllllllIIlIlIllllIllI.length <= 1) {
         llllllllllllllllllIIlIlIllllIlll.chatSyntax("hide <module/list/clear/reset>");
      } else if (StringsKt.equals(llllllllllllllllllIIlIlIllllIllI[1], "list", true)) {
         llllllllllllllllllIIlIlIllllIlll.chat("§c§lHidden");
         boolean llllllllllllllllllIIlIlIllllIIll = (Iterable)LiquidBounce.INSTANCE.getModuleManager().getModules();
         int llllllllllllllllllIIlIlIlllllIll = false;
         boolean llllllllllllllllllIIlIlIllllIIII = (Collection)(new ArrayList());
         int llllllllllllllllllIIlIllIIIIIIlI = false;
         Iterator llllllllllllllllllIIlIlIlllIlllI = llllllllllllllllllIIlIlIllllIIll.iterator();

         while(llllllllllllllllllIIlIlIlllIlllI.hasNext()) {
            double llllllllllllllllllIIlIlIlllIllIl = llllllllllllllllllIIlIlIlllIlllI.next();
            float llllllllllllllllllIIlIlIlllIllII = (Module)llllllllllllllllllIIlIlIlllIllIl;
            int llllllllllllllllllIIlIllIIIIIllI = false;
            if (!llllllllllllllllllIIlIlIlllIllII.getArray()) {
               llllllllllllllllllIIlIlIllllIIII.add(llllllllllllllllllIIlIlIlllIllIl);
               boolean var10001 = false;
            }
         }

         llllllllllllllllllIIlIlIllllIIll = (Iterable)((List)llllllllllllllllllIIlIlIllllIIII);
         llllllllllllllllllIIlIlIlllllIll = false;
         Iterator llllllllllllllllllIIlIlIllllIIIl = llllllllllllllllllIIlIlIllllIIll.iterator();

         while(llllllllllllllllllIIlIlIllllIIIl.hasNext()) {
            boolean llllllllllllllllllIIlIlIllllIIII = llllllllllllllllllIIlIlIllllIIIl.next();
            String llllllllllllllllllIIlIlIlllIllll = (Module)llllllllllllllllllIIlIlIllllIIII;
            int llllllllllllllllllIIlIlIlllllllI = false;
            ClientUtils.displayChatMessage(String.valueOf((new StringBuilder()).append("§6> §c").append(llllllllllllllllllIIlIlIlllIllll.getName())));
         }

      } else {
         Module llllllllllllllllllIIlIlIlllllIIl;
         Iterator llllllllllllllllllIIlIlIllllIIlI;
         if (StringsKt.equals(llllllllllllllllllIIlIlIllllIllI[1], "clear", true)) {
            llllllllllllllllllIIlIlIllllIIlI = LiquidBounce.INSTANCE.getModuleManager().getModules().iterator();

            while(llllllllllllllllllIIlIlIllllIIlI.hasNext()) {
               llllllllllllllllllIIlIlIlllllIIl = (Module)llllllllllllllllllIIlIlIllllIIlI.next();
               llllllllllllllllllIIlIlIlllllIIl.setArray(true);
            }

            llllllllllllllllllIIlIlIllllIlll.chat("Cleared hidden modules.");
         } else if (!StringsKt.equals(llllllllllllllllllIIlIlIllllIllI[1], "reset", true)) {
            llllllllllllllllllIIlIlIlllllIIl = LiquidBounce.INSTANCE.getModuleManager().getModule(llllllllllllllllllIIlIlIllllIllI[1]);
            if (llllllllllllllllllIIlIlIlllllIIl == null) {
               llllllllllllllllllIIlIlIllllIlll.chat(String.valueOf((new StringBuilder()).append("Module §a§l").append(llllllllllllllllllIIlIlIllllIllI[1]).append("§3 not found.")));
            } else {
               llllllllllllllllllIIlIlIlllllIIl.setArray(!llllllllllllllllllIIlIlIlllllIIl.getArray());
               llllllllllllllllllIIlIlIllllIlll.chat(String.valueOf((new StringBuilder()).append("Module §a§l").append(llllllllllllllllllIIlIlIlllllIIl.getName()).append("§3 is now §a§l").append(llllllllllllllllllIIlIlIlllllIIl.getArray() ? "visible" : "invisible").append("§3 on the array list.")));
               llllllllllllllllllIIlIlIllllIlll.playEdit();
            }
         } else {
            llllllllllllllllllIIlIlIllllIIlI = LiquidBounce.INSTANCE.getModuleManager().getModules().iterator();

            while(llllllllllllllllllIIlIlIllllIIlI.hasNext()) {
               llllllllllllllllllIIlIlIlllllIIl = (Module)llllllllllllllllllIIlIlIllllIIlI.next();
               llllllllllllllllllIIlIlIlllllIIl.setArray(((ModuleInfo)llllllllllllllllllIIlIlIlllllIIl.getClass().getAnnotation(ModuleInfo.class)).array());
            }

            llllllllllllllllllIIlIlIllllIlll.chat("Reset hidden modules.");
         }
      }
   }
}
