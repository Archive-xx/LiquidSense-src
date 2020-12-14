package net.ccbluex.liquidbounce.features.command.commands;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.concurrent.ThreadsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.Notification;
import net.ccbluex.liquidbounce.utils.SettingsUtils;
import net.ccbluex.liquidbounce.utils.misc.HttpUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u000bH\u0016¢\u0006\u0002\u0010\fJ;\u0010\r\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\u000f2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0018\u0010\u0012\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u0014\u0012\u0004\u0012\u00020\t0\u0013H\u0002¢\u0006\u0002\u0010\u0015J!\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00050\u00142\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u000bH\u0016¢\u0006\u0002\u0010\u0017R\u0016\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/AutoSettingsCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "autoSettingFiles", "", "", "loadingLock", "Ljava/lang/Object;", "execute", "", "args", "", "([Ljava/lang/String;)V", "loadSettings", "useCached", "", "join", "", "callback", "Lkotlin/Function1;", "", "(ZLjava/lang/Long;Lkotlin/jvm/functions/Function1;)V", "tabComplete", "([Ljava/lang/String;)Ljava/util/List;", "LiquidSense"}
)
public final class AutoSettingsCommand extends Command {
   // $FF: synthetic field
   private final Object loadingLock = new Object();
   // $FF: synthetic field
   private List<String> autoSettingFiles;

   public void execute(@NotNull String[] lllllllllllllllllllIlllIlllIIIlI) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllllIlllIlllIIIlI, "args");
      if (lllllllllllllllllllIlllIlllIIIlI.length <= 1) {
         lllllllllllllllllllIlllIlllIIIll.chatSyntax("settings <load/list>");
      } else {
         if (StringsKt.equals(lllllllllllllllllllIlllIlllIIIlI[1], "load", true)) {
            if (lllllllllllllllllllIlllIlllIIIlI.length < 3) {
               lllllllllllllllllllIlllIlllIIIll.chatSyntax("settings load <name/url>");
               return;
            }

            String var10000;
            if (StringsKt.startsWith$default(lllllllllllllllllllIlllIlllIIIlI[2], "http", false, 2, (Object)null)) {
               var10000 = lllllllllllllllllllIlllIlllIIIlI[2];
            } else {
               StringBuilder var7 = (new StringBuilder()).append("https://cloud.liquidbounce.net/LiquidBounce/settings/");
               int lllllllllllllllllllIlllIllIllllI = lllllllllllllllllllIlllIlllIIIlI[2];
               byte lllllllllllllllllllIlllIllIlllII = var7;
               String lllllllllllllllllllIlllIllIlllIl = false;
               if (lllllllllllllllllllIlllIllIllllI == null) {
                  throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
               }

               var10000 = lllllllllllllllllllIlllIllIllllI.toLowerCase();
               Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).toLowerCase()");
               String lllllllllllllllllllIlllIllIllIll = var10000;
               var10000 = String.valueOf(lllllllllllllllllllIlllIllIlllII.append(lllllllllllllllllllIlllIllIllIll));
            }

            final char lllllllllllllllllllIlllIllIlllll = var10000;
            lllllllllllllllllllIlllIlllIIIll.chat("Loading settings...");
            ThreadsKt.thread$default(false, false, (ClassLoader)null, (String)null, 0, (Function0)(new Function0<Unit>() {
               public final void invoke() {
                  try {
                     boolean llllllllllllllllllIllIIllIllIllI = HttpUtils.get(lllllllllllllllllllIlllIllIlllll);
                     lllllllllllllllllllIlllIlllIIIll.chat("Applying settings...");
                     SettingsUtils.INSTANCE.executeScript(llllllllllllllllllIllIIllIllIllI);
                     lllllllllllllllllllIlllIlllIIIll.chat("§6Settings applied successfully");
                     LiquidBounce.INSTANCE.getHud().addNotification(new Notification("Updated Settings"));
                     boolean var10001 = false;
                     lllllllllllllllllllIlllIlllIIIll.playEdit();
                  } catch (Exception var2) {
                     var2.printStackTrace();
                     lllllllllllllllllllIlllIlllIIIll.chat("Failed to fetch auto settings.");
                  }

               }
            }), 31, (Object)null);
            boolean var10001 = false;
         } else if (StringsKt.equals(lllllllllllllllllllIlllIlllIIIlI[1], "list", true)) {
            lllllllllllllllllllIlllIlllIIIll.chat("Loading settings...");
            loadSettings$default(lllllllllllllllllllIlllIlllIIIll, false, (Long)null, (Function1)(new Function1<List<? extends String>, Unit>() {
               public final void invoke(@NotNull List<String> llllIIlIIlI) {
                  Intrinsics.checkParameterIsNotNull(llllIIlIIlI, "it");
                  Iterator llllIIIllIl = llllIIlIIlI.iterator();

                  while(llllIIIllIl.hasNext()) {
                     double llllIIIlllI = (String)llllIIIllIl.next();
                     lllllllllllllllllllIlllIlllIIIll.chat(String.valueOf((new StringBuilder()).append("> ").append(llllIIIlllI)));
                  }

               }
            }), 2, (Object)null);
         }

      }
   }

   public AutoSettingsCommand() {
      super("autosettings", new String[]{"setting", "settings", "config", "autosetting"});
   }

   // $FF: synthetic method
   static void loadSettings$default(AutoSettingsCommand var0, boolean var1, Long var2, Function1 var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = (Long)null;
      }

      var0.loadSettings(var1, var2, var3);
   }

   private final void loadSettings(final boolean lllllllllllllllllllIlllIllIlIIll, Long lllllllllllllllllllIlllIllIIlllI, final Function1<? super List<String>, Unit> lllllllllllllllllllIlllIllIIllIl) {
      double lllllllllllllllllllIlllIllIIllII = ThreadsKt.thread$default(false, false, (ClassLoader)null, (String)null, 0, (Function0)(new Function0<Unit>() {
         public final void invoke() {
            int lllllllllllllllllllllIlIlIllllIl = lllllllllllllllllllIlllIllIlIIII.loadingLock;
            char lllllllllllllllllllllIlIlIllllII = false;
            int lllllllllllllllllllllIlIlIlllIll = false;
            synchronized(lllllllllllllllllllllIlIlIllllIl){}

            try {
               double lllllllllllllllllllllIlIlIlllIlI = false;
               boolean var15;
               if (!lllllllllllllllllllIlllIllIlIIll || lllllllllllllllllllIlllIllIlIIII.autoSettingFiles == null) {
                  try {
                     short lllllllllllllllllllllIlIlIlllIIl = (new JsonParser()).parse(HttpUtils.get("https://api.github.com/repos/CCBlueX/LiquidCloud/contents/LiquidBounce/settings"));
                     double lllllllllllllllllllllIlIlIlllIII = false;
                     List lllllllllllllllllllllIlIllIIIlII = (List)(new ArrayList());
                     if (lllllllllllllllllllllIlIlIlllIIl instanceof JsonArray) {
                        for(Iterator lllllllllllllllllllllIlIlIllIllI = ((JsonArray)lllllllllllllllllllllIlIlIlllIIl).iterator(); lllllllllllllllllllllIlIlIllIllI.hasNext(); var15 = false) {
                           JsonElement lllllllllllllllllllllIlIllIIIlIl = (JsonElement)lllllllllllllllllllllIlIlIllIllI.next();
                           Intrinsics.checkExpressionValueIsNotNull(lllllllllllllllllllllIlIllIIIlIl, "setting");
                           JsonElement var16 = lllllllllllllllllllllIlIllIIIlIl.getAsJsonObject().get("name");
                           Intrinsics.checkExpressionValueIsNotNull(var16, "setting.asJsonObject[\"name\"]");
                           String var17 = var16.getAsString();
                           Intrinsics.checkExpressionValueIsNotNull(var17, "setting.asJsonObject[\"name\"].asString");
                           lllllllllllllllllllllIlIllIIIlII.add(var17);
                        }
                     }

                     lllllllllllllllllllIlllIllIIllIl.invoke(lllllllllllllllllllllIlIllIIIlII);
                     var15 = false;
                     lllllllllllllllllllIlllIllIlIIII.autoSettingFiles = lllllllllllllllllllllIlIllIIIlII;
                  } catch (Exception var11) {
                     lllllllllllllllllllIlllIllIlIIII.chat("Failed to fetch auto settings list.");
                  }

                  Unit lllllllllllllllllllllIlIlIlllIll1 = Unit.INSTANCE;
                  return;
               }

               Function1 var10000 = lllllllllllllllllllIlllIllIIllIl;
               List var10001 = lllllllllllllllllllIlllIllIlIIII.autoSettingFiles;
               if (var10001 == null) {
                  Intrinsics.throwNpe();
               }

               var10000.invoke(var10001);
               var15 = false;
            } finally {
               ;
            }

         }
      }), 31, (Object)null);
      if (lllllllllllllllllllIlllIllIIlllI != null) {
         lllllllllllllllllllIlllIllIIllII.join(lllllllllllllllllllIlllIllIIlllI);
      }

   }

   @NotNull
   public List<String> tabComplete(@NotNull String[] lllllllllllllllllllIlllIlIIIlIIl) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllllIlllIlIIIlIIl, "args");
      Exception lllllllllllllllllllIlllIlIIIIllI = false;
      if (lllllllllllllllllllIlllIlIIIlIIl.length == 0) {
         return CollectionsKt.emptyList();
      } else {
         List var10000;
         boolean var10001;
         Iterable lllllllllllllllllllIlllIlIIIIlll;
         Collection lllllllllllllllllllIlllIlIIlIlIl;
         boolean lllllllllllllllllllIlllIlIIIIIIl;
         Iterator lllllllllllllllllllIlllIIlllllll;
         Object lllllllllllllllllllIlllIIllllllI;
         String lllllllllllllllllllIlllIlIIlllII;
         boolean lllllllllllllllllllIlllIlIIllIll;
         switch(lllllllllllllllllllIlllIlIIIlIIl.length) {
         case 1:
            lllllllllllllllllllIlllIlIIIIlll = (Iterable)CollectionsKt.listOf(new String[]{"list", "load"});
            lllllllllllllllllllIlllIlIIIIllI = false;
            lllllllllllllllllllIlllIlIIlIlIl = (Collection)(new ArrayList());
            lllllllllllllllllllIlllIlIIIIIIl = false;
            lllllllllllllllllllIlllIIlllllll = lllllllllllllllllllIlllIlIIIIlll.iterator();

            while(lllllllllllllllllllIlllIIlllllll.hasNext()) {
               lllllllllllllllllllIlllIIllllllI = lllllllllllllllllllIlllIIlllllll.next();
               lllllllllllllllllllIlllIlIIlllII = (String)lllllllllllllllllllIlllIIllllllI;
               lllllllllllllllllllIlllIlIIllIll = false;
               if (StringsKt.startsWith(lllllllllllllllllllIlllIlIIlllII, lllllllllllllllllllIlllIlIIIlIIl[0], true)) {
                  lllllllllllllllllllIlllIlIIlIlIl.add(lllllllllllllllllllIlllIIllllllI);
                  var10001 = false;
               }
            }

            var10000 = (List)lllllllllllllllllllIlllIlIIlIlIl;
            break;
         case 2:
            if (StringsKt.equals(lllllllllllllllllllIlllIlIIIlIIl[0], "load", true)) {
               if (lllllllllllllllllllIlllIlIIIlIll.autoSettingFiles == null) {
                  lllllllllllllllllllIlllIlIIIlIll.loadSettings(true, 500L, (Function1)null.INSTANCE);
               }

               if (lllllllllllllllllllIlllIlIIIlIll.autoSettingFiles != null) {
                  var10000 = lllllllllllllllllllIlllIlIIIlIll.autoSettingFiles;
                  if (var10000 == null) {
                     Intrinsics.throwNpe();
                  }

                  lllllllllllllllllllIlllIlIIIIlll = (Iterable)var10000;
                  lllllllllllllllllllIlllIlIIIIllI = false;
                  lllllllllllllllllllIlllIlIIlIlIl = (Collection)(new ArrayList());
                  lllllllllllllllllllIlllIlIIIIIIl = false;
                  lllllllllllllllllllIlllIIlllllll = lllllllllllllllllllIlllIlIIIIlll.iterator();

                  while(lllllllllllllllllllIlllIIlllllll.hasNext()) {
                     lllllllllllllllllllIlllIIllllllI = lllllllllllllllllllIlllIIlllllll.next();
                     lllllllllllllllllllIlllIlIIlllII = (String)lllllllllllllllllllIlllIIllllllI;
                     lllllllllllllllllllIlllIlIIllIll = false;
                     if (StringsKt.startsWith(lllllllllllllllllllIlllIlIIlllII, lllllllllllllllllllIlllIlIIIlIIl[1], true)) {
                        lllllllllllllllllllIlllIlIIlIlIl.add(lllllllllllllllllllIlllIIllllllI);
                        var10001 = false;
                     }
                  }

                  return (List)lllllllllllllllllllIlllIlIIlIlIl;
               }
            }

            return CollectionsKt.emptyList();
         default:
            var10000 = CollectionsKt.emptyList();
         }

         return var10000;
      }
   }
}
