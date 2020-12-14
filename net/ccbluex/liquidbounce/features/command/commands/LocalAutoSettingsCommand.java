package net.ccbluex.liquidbounce.features.command.commands;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.Notification;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.SettingsUtils;
import net.ccbluex.liquidbounce.utils.misc.StringUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\bJ\u0015\u0010\t\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\u0006H\u0002¢\u0006\u0002\u0010\u000bJ!\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00070\r2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\u000e¨\u0006\u000f"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/LocalAutoSettingsCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "getLocalSettings", "Ljava/io/File;", "()[Ljava/io/File;", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "LiquidSense"}
)
public final class LocalAutoSettingsCommand extends Command {
   public LocalAutoSettingsCommand() {
      super("localautosettings", new String[]{"localsetting", "localsettings", "localconfig"});
   }

   public void execute(@NotNull String[] llllllllllllllllllIIIllIllIlIIII) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIIIllIllIlIIII, "args");
      if (llllllllllllllllllIIIllIllIlIIII.length > 1) {
         File llllllllllllllllllIIIllIllIIllll;
         String llllllllllllllllllIIIllIllIIlllI;
         boolean var10001;
         if (StringsKt.equals(llllllllllllllllllIIIllIllIlIIII[1], "load", true)) {
            if (llllllllllllllllllIIIllIllIlIIII.length > 2) {
               llllllllllllllllllIIIllIllIIllll = new File(LiquidBounce.INSTANCE.getFileManager().settingsDir, llllllllllllllllllIIIllIllIlIIII[2]);
               if (llllllllllllllllllIIIllIllIIllll.exists()) {
                  try {
                     llllllllllllllllllIIIllIllIlIIll.chat("§9Loading settings...");
                     llllllllllllllllllIIIllIllIIlllI = FilesKt.readText$default(llllllllllllllllllIIIllIllIIllll, (Charset)null, 1, (Object)null);
                     llllllllllllllllllIIIllIllIlIIll.chat("§9Set settings...");
                     SettingsUtils.INSTANCE.executeScript(llllllllllllllllllIIIllIllIIlllI);
                     llllllllllllllllllIIIllIllIlIIll.chat("§6Settings applied successfully.");
                     LiquidBounce.INSTANCE.getHud().addNotification(new Notification("Updated Settings"));
                     var10001 = false;
                     llllllllllllllllllIIIllIllIlIIll.playEdit();
                  } catch (IOException var8) {
                     var8.printStackTrace();
                  }

                  return;
               }

               llllllllllllllllllIIIllIllIlIIll.chat("§cSettings file does not exist!");
               return;
            }

            llllllllllllllllllIIIllIllIlIIll.chatSyntax("localautosettings load <name>");
            return;
         }

         if (StringsKt.equals(llllllllllllllllllIIIllIllIlIIII[1], "save", true)) {
            if (llllllllllllllllllIIIllIllIlIIII.length > 2) {
               llllllllllllllllllIIIllIllIIllll = new File(LiquidBounce.INSTANCE.getFileManager().settingsDir, llllllllllllllllllIIIllIllIlIIII[2]);

               try {
                  if (llllllllllllllllllIIIllIllIIllll.exists()) {
                     llllllllllllllllllIIIllIllIIllll.delete();
                     var10001 = false;
                  }

                  llllllllllllllllllIIIllIllIIllll.createNewFile();
                  var10001 = false;
                  boolean llllllllllllllllllIIIllIllIllIll;
                  String var16;
                  if (llllllllllllllllllIIIllIllIlIIII.length > 3) {
                     var16 = StringUtils.toCompleteString(llllllllllllllllllIIIllIllIlIIII, 3);
                     Intrinsics.checkExpressionValueIsNotNull(var16, "StringUtils.toCompleteString(args, 3)");
                     double llllllllllllllllllIIIllIllIIllIl = var16;
                     llllllllllllllllllIIIllIllIllIll = false;
                     if (llllllllllllllllllIIIllIllIIllIl == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                     }

                     var16 = llllllllllllllllllIIIllIllIIllIl.toLowerCase();
                     Intrinsics.checkExpressionValueIsNotNull(var16, "(this as java.lang.String).toLowerCase()");
                  } else {
                     var16 = "values";
                  }

                  llllllllllllllllllIIIllIllIIlllI = var16;
                  double llllllllllllllllllIIIllIllIIllIl = StringsKt.contains$default((CharSequence)llllllllllllllllllIIIllIllIIlllI, (CharSequence)"all", false, 2, (Object)null) || StringsKt.contains$default((CharSequence)llllllllllllllllllIIIllIllIIlllI, (CharSequence)"values", false, 2, (Object)null);
                  llllllllllllllllllIIIllIllIllIll = StringsKt.contains$default((CharSequence)llllllllllllllllllIIIllIllIIlllI, (CharSequence)"all", false, 2, (Object)null) || StringsKt.contains$default((CharSequence)llllllllllllllllllIIIllIllIIlllI, (CharSequence)"binds", false, 2, (Object)null);
                  boolean llllllllllllllllllIIIllIllIlllII = StringsKt.contains$default((CharSequence)llllllllllllllllllIIIllIllIIlllI, (CharSequence)"all", false, 2, (Object)null) || StringsKt.contains$default((CharSequence)llllllllllllllllllIIIllIllIIlllI, (CharSequence)"states", false, 2, (Object)null);
                  if (!llllllllllllllllllIIIllIllIIllIl && !llllllllllllllllllIIIllIllIllIll && !llllllllllllllllllIIIllIllIlllII) {
                     llllllllllllllllllIIIllIllIlIIll.chatSyntaxError();
                     return;
                  }

                  llllllllllllllllllIIIllIllIlIIll.chat("§9Creating settings...");
                  short llllllllllllllllllIIIllIllIIlIlI = SettingsUtils.INSTANCE.generateScript(llllllllllllllllllIIIllIllIIllIl, llllllllllllllllllIIIllIllIllIll, llllllllllllllllllIIIllIllIlllII);
                  llllllllllllllllllIIIllIllIlIIll.chat("§9Saving settings...");
                  FilesKt.writeText$default(llllllllllllllllllIIIllIllIIllll, llllllllllllllllllIIIllIllIIlIlI, (Charset)null, 2, (Object)null);
                  llllllllllllllllllIIIllIllIlIIll.chat("§6Settings saved successfully.");
               } catch (Throwable var9) {
                  llllllllllllllllllIIIllIllIlIIll.chat(String.valueOf((new StringBuilder()).append("§cFailed to create local config: §3").append(var9.getMessage())));
                  ClientUtils.getLogger().error("Failed to create local config.", var9);
               }

               return;
            }

            llllllllllllllllllIIIllIllIlIIll.chatSyntax("localsettings save <name> [all/values/binds/states]...");
            return;
         }

         if (StringsKt.equals(llllllllllllllllllIIIllIllIlIIII[1], "delete", true)) {
            if (llllllllllllllllllIIIllIllIlIIII.length > 2) {
               llllllllllllllllllIIIllIllIIllll = new File(LiquidBounce.INSTANCE.getFileManager().settingsDir, llllllllllllllllllIIIllIllIlIIII[2]);
               if (llllllllllllllllllIIIllIllIIllll.exists()) {
                  llllllllllllllllllIIIllIllIIllll.delete();
                  var10001 = false;
                  llllllllllllllllllIIIllIllIlIIll.chat("§6Settings file deleted successfully.");
                  return;
               }

               llllllllllllllllllIIIllIllIlIIll.chat("§cSettings file does not exist!");
               return;
            }

            llllllllllllllllllIIIllIllIlIIll.chatSyntax("localsettings delete <name>");
            return;
         }

         if (StringsKt.equals(llllllllllllllllllIIIllIllIlIIII[1], "list", true)) {
            llllllllllllllllllIIIllIllIlIIll.chat("§cSettings:");
            File[] var10000 = llllllllllllllllllIIIllIllIlIIll.getLocalSettings();
            if (var10000 == null) {
               var10001 = false;
               return;
            }

            Exception llllllllllllllllllIIIllIllIIllll = var10000;
            short llllllllllllllllllIIIllIllIIllII = llllllllllllllllllIIIllIllIIllll;
            float llllllllllllllllllIIIllIllIIlIll = llllllllllllllllllIIIllIllIIllll.length;

            for(int llllllllllllllllllIIIllIllIIllIl = 0; llllllllllllllllllIIIllIllIIllIl < llllllllllllllllllIIIllIllIIlIll; ++llllllllllllllllllIIIllIllIIllIl) {
               boolean llllllllllllllllllIIIllIllIIlllI = llllllllllllllllllIIIllIllIIllII[llllllllllllllllllIIIllIllIIllIl];
               llllllllllllllllllIIIllIllIlIIll.chat(String.valueOf((new StringBuilder()).append("> ").append(llllllllllllllllllIIIllIllIIlllI.getName())));
            }

            return;
         }
      }

      llllllllllllllllllIIIllIllIlIIll.chatSyntax("localsettings <load/save/list/delete>");
   }

   @NotNull
   public List<String> tabComplete(@NotNull String[] llllllllllllllllllIIIllIlIIllllI) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIIIllIlIIllllI, "args");
      long llllllllllllllllllIIIllIlIIllIlI = false;
      if (llllllllllllllllllIIIllIlIIllllI.length == 0) {
         return CollectionsKt.emptyList();
      } else {
         boolean var10001;
         List var29;
         switch(llllllllllllllllllIIIllIlIIllllI.length) {
         case 1:
            Exception llllllllllllllllllIIIllIlIIllIll = (Iterable)CollectionsKt.listOf(new String[]{"delete", "list", "load", "save"});
            llllllllllllllllllIIIllIlIIllIlI = false;
            String llllllllllllllllllIIIllIlIIllIII = (Collection)(new ArrayList());
            long llllllllllllllllllIIIllIlIIlIlll = false;
            Iterator llllllllllllllllllIIIllIlIIlIllI = llllllllllllllllllIIIllIlIIllIll.iterator();

            while(llllllllllllllllllIIIllIlIIlIllI.hasNext()) {
               int llllllllllllllllllIIIllIlIIlIlIl = llllllllllllllllllIIIllIlIIlIllI.next();
               byte llllllllllllllllllIIIllIlIIlIlII = (String)llllllllllllllllllIIIllIlIIlIlIl;
               int llllllllllllllllllIIIllIlIllIlll = false;
               if (StringsKt.startsWith(llllllllllllllllllIIIllIlIIlIlII, llllllllllllllllllIIIllIlIIllllI[0], true)) {
                  llllllllllllllllllIIIllIlIIllIII.add(llllllllllllllllllIIIllIlIIlIlIl);
                  var10001 = false;
               }
            }

            var29 = (List)llllllllllllllllllIIIllIlIIllIII;
            break;
         case 2:
            Exception llllllllllllllllllIIIllIlIIllIll = llllllllllllllllllIIIllIlIIllllI[0];
            llllllllllllllllllIIIllIlIIllIlI = false;
            if (llllllllllllllllllIIIllIlIIllIll == null) {
               throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }

            label60: {
               String var10000 = llllllllllllllllllIIIllIlIIllIll.toLowerCase();
               Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).toLowerCase()");
               llllllllllllllllllIIIllIlIIllIll = var10000;
               switch(llllllllllllllllllIIIllIlIIllIll.hashCode()) {
               case -1335458389:
                  if (llllllllllllllllllIIIllIlIIllIll.equals("delete")) {
                     break label60;
                  }
                  break;
               case 3327206:
                  if (llllllllllllllllllIIIllIlIIllIll.equals("load")) {
                     break label60;
                  }
               }

               return CollectionsKt.emptyList();
            }

            File[] var28 = llllllllllllllllllIIIllIlIIlllIl.getLocalSettings();
            if (var28 == null) {
               var10001 = false;
               return CollectionsKt.emptyList();
            }

            File[] llllllllllllllllllIIIllIlIlIIIII = var28;
            String llllllllllllllllllIIIllIlIIllIII = false;
            double llllllllllllllllllIIIllIlIIlIllI = (Collection)(new ArrayList(llllllllllllllllllIIIllIlIlIIIII.length));
            int llllllllllllllllllIIIllIlIIlIlIl = false;
            byte llllllllllllllllllIIIllIlIIlIlII = llllllllllllllllllIIIllIlIlIIIII;
            byte llllllllllllllllllIIIllIlIIlIIll = llllllllllllllllllIIIllIlIlIIIII.length;

            for(int llllllllllllllllllIIIllIlIIlIIlI = 0; llllllllllllllllllIIIllIlIIlIIlI < llllllllllllllllllIIIllIlIIlIIll; ++llllllllllllllllllIIIllIlIIlIIlI) {
               char llllllllllllllllllIIIllIlIIlIIIl = llllllllllllllllllIIIllIlIIlIlII[llllllllllllllllllIIIllIlIIlIIlI];
               int llllllllllllllllllIIIllIlIlIllll = false;
               Exception llllllllllllllllllIIIllIlIIIllIl = llllllllllllllllllIIIllIlIIlIIIl.getName();
               llllllllllllllllllIIIllIlIIlIllI.add(llllllllllllllllllIIIllIlIIIllIl);
               var10001 = false;
            }

            int llllllllllllllllllIIIllIlIIllIIl = (Iterable)((List)llllllllllllllllllIIIllIlIIlIllI);
            llllllllllllllllllIIIllIlIIllIII = false;
            llllllllllllllllllIIIllIlIIlIllI = (Collection)(new ArrayList());
            llllllllllllllllllIIIllIlIIlIlIl = false;
            Iterator llllllllllllllllllIIIllIlIIlIlII = llllllllllllllllllIIIllIlIIllIIl.iterator();

            while(llllllllllllllllllIIIllIlIIlIlII.hasNext()) {
               Object llllllllllllllllllIIIllIlIlIIllI = llllllllllllllllllIIIllIlIIlIlII.next();
               short llllllllllllllllllIIIllIlIIlIIlI = (String)llllllllllllllllllIIIllIlIlIIllI;
               char llllllllllllllllllIIIllIlIIlIIIl = false;
               Intrinsics.checkExpressionValueIsNotNull(llllllllllllllllllIIIllIlIIlIIlI, "it");
               if (StringsKt.startsWith(llllllllllllllllllIIIllIlIIlIIlI, llllllllllllllllllIIIllIlIIllllI[1], true)) {
                  llllllllllllllllllIIIllIlIIlIllI.add(llllllllllllllllllIIIllIlIlIIllI);
                  var10001 = false;
               }
            }

            return (List)llllllllllllllllllIIIllIlIIlIllI;
         default:
            var29 = CollectionsKt.emptyList();
         }

         return var29;
      }
   }

   private final File[] getLocalSettings() {
      return LiquidBounce.INSTANCE.getFileManager().settingsDir.listFiles();
   }
}
