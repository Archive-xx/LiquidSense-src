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
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\bJ!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\u000b¨\u0006\f"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/PanicCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "LiquidSense"}
)
public final class PanicCommand extends Command {
   public PanicCommand() {
      byte llllllllllllllllllIIIlllIllllIIl = "panic";
      byte llllllllllllllllllIIIlllIllllIII = new String[0];
      super(llllllllllllllllllIIIlllIllllIIl, llllllllllllllllllIIIlllIllllIII);
   }

   public void execute(@NotNull String[] llllllllllllllllllIIIllllIllIIII) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIIIllllIllIIII, "args");
      Iterable llllllllllllllllllIIIllllIllIIll = (Iterable)LiquidBounce.INSTANCE.getModuleManager().getModules();
      int llllllllllllllllllIIIlllllIIllll = false;
      String llllllllllllllllllIIIllllIlIlIIl = (Collection)(new ArrayList());
      int llllllllllllllllllIIIllllIlIlIII = false;
      Iterator llllllllllllllllllIIIllllIlIIlll = llllllllllllllllllIIIllllIllIIll.iterator();

      boolean llllllllllllllllllIIIllllIlIIlII;
      boolean var10001;
      while(llllllllllllllllllIIIllllIlIIlll.hasNext()) {
         Object llllllllllllllllllIIIlllllIlIlII = llllllllllllllllllIIIllllIlIIlll.next();
         Module llllllllllllllllllIIIlllllIlIllI = (Module)llllllllllllllllllIIIlllllIlIlII;
         llllllllllllllllllIIIllllIlIIlII = false;
         if (llllllllllllllllllIIIlllllIlIllI.getState()) {
            llllllllllllllllllIIIllllIlIlIIl.add(llllllllllllllllllIIIlllllIlIlII);
            var10001 = false;
         }
      }

      int llllllllllllllllllIIIllllIlIllIl = (List)llllllllllllllllllIIIllllIlIlIIl;
      llllllllllllllllllIIIllllIllIIll = null;
      if (llllllllllllllllllIIIllllIllIIII.length > 1) {
         Exception llllllllllllllllllIIIllllIlIlIll = (CharSequence)llllllllllllllllllIIIllllIllIIII[1];
         int llllllllllllllllllIIIllllIlIlIlI = false;
         if (llllllllllllllllllIIIllllIlIlIll.length() > 0) {
            Exception llllllllllllllllllIIIllllIlIlIll = llllllllllllllllllIIIllllIllIIII[1];
            llllllllllllllllllIIIllllIlIlIlI = false;
            if (llllllllllllllllllIIIllllIlIlIll == null) {
               throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }

            String llllllllllllllllllIIIllllIllIIll;
            label107: {
               String var10000 = llllllllllllllllllIIIllllIlIlIll.toLowerCase();
               Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).toLowerCase()");
               llllllllllllllllllIIIllllIlIlIll = var10000;
               switch(llllllllllllllllllIIIllllIlIlIll.hashCode()) {
               case 96673:
                  if (llllllllllllllllllIIIllllIlIlIll.equals("all")) {
                     llllllllllllllllllIIIllllIllIIll = "all";
                     break label107;
                  }
                  break;
               case 1128455843:
                  if (llllllllllllllllllIIIllllIlIlIll.equals("nonrender")) {
                     Iterable llllllllllllllllllIIIlllllIIlIII = (Iterable)llllllllllllllllllIIIllllIlIllIl;
                     String llllllllllllllllllIIIllllIlIlIIl = false;
                     long llllllllllllllllllIIIllllIlIIlll = (Collection)(new ArrayList());
                     int llllllllllllllllllIIIlllllIIlIIl = false;
                     Iterator llllllllllllllllllIIIllllIlIIlIl = llllllllllllllllllIIIlllllIIlIII.iterator();

                     while(llllllllllllllllllIIIllllIlIIlIl.hasNext()) {
                        Object llllllllllllllllllIIIlllllIIllII = llllllllllllllllllIIIllllIlIIlIl.next();
                        double llllllllllllllllllIIIllllIlIIIll = (Module)llllllllllllllllllIIIlllllIIllII;
                        int llllllllllllllllllIIIlllllIIllIl = false;
                        if (llllllllllllllllllIIIllllIlIIIll.getCategory() != ModuleCategory.RENDER) {
                           llllllllllllllllllIIIllllIlIIlll.add(llllllllllllllllllIIIlllllIIllII);
                           var10001 = false;
                        }
                     }

                     llllllllllllllllllIIIllllIlIllIl = (List)llllllllllllllllllIIIllllIlIIlll;
                     llllllllllllllllllIIIllllIllIIll = "all non-render";
                     break label107;
                  }
               }

               String llllllllllllllllllIIIllllIlIlIIl = ModuleCategory.values();
               llllllllllllllllllIIIllllIlIlIII = false;
               byte llllllllllllllllllIIIllllIlIIllI = (Collection)(new ArrayList());
               String llllllllllllllllllIIIllllIlIIlIl = false;
               Exception llllllllllllllllllIIIllllIlIIlII = llllllllllllllllllIIIllllIlIlIIl;
               double llllllllllllllllllIIIllllIlIIIll = llllllllllllllllllIIIllllIlIlIIl.length;

               for(int llllllllllllllllllIIIllllIlIIIlI = 0; llllllllllllllllllIIIllllIlIIIlI < llllllllllllllllllIIIllllIlIIIll; ++llllllllllllllllllIIIllllIlIIIlI) {
                  Object llllllllllllllllllIIIlllllIIIlII = llllllllllllllllllIIIllllIlIIlII[llllllllllllllllllIIIllllIlIIIlI];
                  int llllllllllllllllllIIIllllIIlllll = false;
                  if (StringsKt.equals(llllllllllllllllllIIIlllllIIIlII.getDisplayName(), llllllllllllllllllIIIllllIllIIII[1], true)) {
                     llllllllllllllllllIIIllllIlIIllI.add(llllllllllllllllllIIIlllllIIIlII);
                     var10001 = false;
                  }
               }

               List llllllllllllllllllIIIllllIllIlIl = (List)llllllllllllllllllIIIllllIlIIllI;
               if (llllllllllllllllllIIIllllIllIlIl.isEmpty()) {
                  llllllllllllllllllIIIllllIllIIIl.chat(String.valueOf((new StringBuilder()).append("Category ").append(llllllllllllllllllIIIllllIllIIII[1]).append(" not found")));
                  return;
               }

               String llllllllllllllllllIIIllllIlIlIIl = (ModuleCategory)llllllllllllllllllIIIllllIllIlIl.get(0);
               boolean llllllllllllllllllIIIllllIlIlIII = (Iterable)llllllllllllllllllIIIllllIlIllIl;
               long llllllllllllllllllIIIllllIlIIlll = false;
               Collection llllllllllllllllllIIIllllIlllIlI = (Collection)(new ArrayList());
               llllllllllllllllllIIIllllIlIIlII = false;
               Iterator llllllllllllllllllIIIllllIlIIIll = llllllllllllllllllIIIllllIlIlIII.iterator();

               while(llllllllllllllllllIIIllllIlIIIll.hasNext()) {
                  double llllllllllllllllllIIIllllIlIIIlI = llllllllllllllllllIIIllllIlIIIll.next();
                  Module llllllllllllllllllIIIllllIlllllI = (Module)llllllllllllllllllIIIllllIlIIIlI;
                  double llllllllllllllllllIIIllllIlIIIII = false;
                  if (llllllllllllllllllIIIllllIlllllI.getCategory() == llllllllllllllllllIIIllllIlIlIIl) {
                     llllllllllllllllllIIIllllIlllIlI.add(llllllllllllllllllIIIllllIlIIIlI);
                     var10001 = false;
                  }
               }

               llllllllllllllllllIIIllllIlIllIl = (List)llllllllllllllllllIIIllllIlllIlI;
               llllllllllllllllllIIIllllIllIIll = String.valueOf((new StringBuilder()).append("all ").append(llllllllllllllllllIIIllllIlIlIIl.getDisplayName()));
            }

            Iterator llllllllllllllllllIIIllllIlIlIlI = llllllllllllllllllIIIllllIlIllIl.iterator();

            while(llllllllllllllllllIIIllllIlIlIlI.hasNext()) {
               Module llllllllllllllllllIIIllllIllIlII = (Module)llllllllllllllllllIIIllllIlIlIlI.next();
               llllllllllllllllllIIIllllIllIlII.setState(false);
            }

            llllllllllllllllllIIIllllIllIIIl.chat(String.valueOf((new StringBuilder()).append("Disabled ").append(llllllllllllllllllIIIllllIllIIll).append(" modules.")));
            return;
         }
      }

      llllllllllllllllllIIIllllIllIIIl.chatSyntax("panic <all/nonrender/combat/player/movement/render/world/misc/exploit/fun>");
   }

   @NotNull
   public List<String> tabComplete(@NotNull String[] llllllllllllllllllIIIllllIIIlIlI) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIIIllllIIIlIlI, "args");
      float llllllllllllllllllIIIllllIIIllIl = false;
      if (llllllllllllllllllIIIllllIIIlIlI.length == 0) {
         return CollectionsKt.emptyList();
      } else {
         List var10000;
         switch(llllllllllllllllllIIIllllIIIlIlI.length) {
         case 1:
            Iterable llllllllllllllllllIIIllllIIIlllI = (Iterable)CollectionsKt.listOf(new String[]{"all", "nonrender", "combat", "player", "movement", "render", "world", "misc", "exploit", "fun"});
            llllllllllllllllllIIIllllIIIllIl = false;
            Collection llllllllllllllllllIIIllllIIlIIII = (Collection)(new ArrayList());
            int llllllllllllllllllIIIllllIIIllll = false;
            Iterator llllllllllllllllllIIIllllIIIIlII = llllllllllllllllllIIIllllIIIlllI.iterator();

            while(llllllllllllllllllIIIllllIIIIlII.hasNext()) {
               Object llllllllllllllllllIIIllllIIlIIlI = llllllllllllllllllIIIllllIIIIlII.next();
               boolean llllllllllllllllllIIIllllIIIIIlI = (String)llllllllllllllllllIIIllllIIlIIlI;
               int llllllllllllllllllIIIllllIIlIIll = false;
               if (StringsKt.startsWith(llllllllllllllllllIIIllllIIIIIlI, llllllllllllllllllIIIllllIIIlIlI[0], true)) {
                  llllllllllllllllllIIIllllIIlIIII.add(llllllllllllllllllIIIllllIIlIIlI);
                  boolean var10001 = false;
               }
            }

            var10000 = (List)llllllllllllllllllIIIllllIIlIIII;
            break;
         default:
            var10000 = CollectionsKt.emptyList();
         }

         return var10000;
      }
   }
}
