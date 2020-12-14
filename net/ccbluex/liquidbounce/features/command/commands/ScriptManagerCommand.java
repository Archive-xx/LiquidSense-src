package net.ccbluex.liquidbounce.features.command.commands;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.script.Script;
import net.ccbluex.liquidbounce.script.ScriptManager;
import net.ccbluex.liquidbounce.ui.client.clickgui.ClickGui;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.misc.MiscUtils;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\bJ!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\u000b¨\u0006\f"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/ScriptManagerCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "LiquidSense"}
)
public final class ScriptManagerCommand extends Command {
   public ScriptManagerCommand() {
      super("scriptmanager", new String[]{"scripts"});
   }

   @NotNull
   public List<String> tabComplete(@NotNull String[] llllllllllllllllllIlIIllIIIIllII) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIlIIllIIIIllII, "args");
      Exception llllllllllllllllllIlIIllIIIIlIlI = false;
      if (llllllllllllllllllIlIIllIIIIllII.length == 0) {
         return CollectionsKt.emptyList();
      } else {
         List var10000;
         switch(llllllllllllllllllIlIIllIIIIllII.length) {
         case 1:
            byte llllllllllllllllllIlIIllIIIIlIll = (Iterable)CollectionsKt.listOf(new String[]{"delete", "import", "folder", "reload"});
            llllllllllllllllllIlIIllIIIIlIlI = false;
            Collection llllllllllllllllllIlIIllIIIlIIlI = (Collection)(new ArrayList());
            float llllllllllllllllllIlIIllIIIIIlll = false;
            Iterator llllllllllllllllllIlIIllIIIIIllI = llllllllllllllllllIlIIllIIIIlIll.iterator();

            while(llllllllllllllllllIlIIllIIIIIllI.hasNext()) {
               Object llllllllllllllllllIlIIllIIIlIlII = llllllllllllllllllIlIIllIIIIIllI.next();
               byte llllllllllllllllllIlIIllIIIIIlII = (String)llllllllllllllllllIlIIllIIIlIlII;
               short llllllllllllllllllIlIIllIIIIIIll = false;
               if (StringsKt.startsWith(llllllllllllllllllIlIIllIIIIIlII, llllllllllllllllllIlIIllIIIIllII[0], true)) {
                  llllllllllllllllllIlIIllIIIlIIlI.add(llllllllllllllllllIlIIllIIIlIlII);
                  boolean var10001 = false;
               }
            }

            var10000 = (List)llllllllllllllllllIlIIllIIIlIIlI;
            break;
         default:
            var10000 = CollectionsKt.emptyList();
         }

         return var10000;
      }
   }

   public void execute(@NotNull String[] llllllllllllllllllIlIIllIIlIllll) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIlIIllIIlIllll, "args");
      boolean llllllllllllllllllIlIIllIIlIlIlI;
      if (llllllllllllllllllIlIIllIIlIllll.length > 1) {
         String llllllllllllllllllIlIIllIIlIlIll;
         if (StringsKt.equals(llllllllllllllllllIlIIllIIlIllll[1], "import", true)) {
            try {
               File var38 = MiscUtils.openFileChooser();
               boolean var10001;
               if (var38 == null) {
                  var10001 = false;
                  return;
               }

               File llllllllllllllllllIlIIllIlIIIlIl = var38;
               llllllllllllllllllIlIIllIIlIlIll = llllllllllllllllllIlIIllIlIIIlIl.getName();
               Intrinsics.checkExpressionValueIsNotNull(llllllllllllllllllIlIIllIIlIlIll, "fileName");
               if (StringsKt.endsWith$default(llllllllllllllllllIlIIllIIlIlIll, ".js", false, 2, (Object)null)) {
                  LiquidBounce.INSTANCE.getScriptManager().importScript(llllllllllllllllllIlIIllIlIIIlIl);
                  LiquidBounce.INSTANCE.setClickGui(new ClickGui());
                  LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().clickGuiConfig);
                  llllllllllllllllllIlIIllIIllIIII.chat("Successfully imported script.");
                  return;
               }

               if (StringsKt.endsWith$default(llllllllllllllllllIlIIllIIlIlIll, ".zip", false, 2, (Object)null)) {
                  ZipFile llllllllllllllllllIlIIllIlIIIlll = new ZipFile(llllllllllllllllllIlIIllIlIIIlIl);
                  Enumeration llllllllllllllllllIlIIllIlIIlIII = llllllllllllllllllIlIIllIlIIIlll.entries();
                  ArrayList llllllllllllllllllIlIIllIlIIlIIl = new ArrayList();

                  while(llllllllllllllllllIlIIllIlIIlIII.hasMoreElements()) {
                     char llllllllllllllllllIlIIllIIlIIlll = (ZipEntry)llllllllllllllllllIlIIllIlIIlIII.nextElement();
                     Intrinsics.checkExpressionValueIsNotNull(llllllllllllllllllIlIIllIIlIIlll, "entry");
                     boolean llllllllllllllllllIlIIllIIlIIllI = llllllllllllllllllIlIIllIIlIIlll.getName();
                     boolean llllllllllllllllllIlIIllIIlIIlIl = new File(LiquidBounce.INSTANCE.getScriptManager().getScriptsFolder(), llllllllllllllllllIlIIllIIlIIllI);
                     if (llllllllllllllllllIlIIllIIlIIlll.isDirectory()) {
                        llllllllllllllllllIlIIllIIlIIlIl.mkdir();
                        var10001 = false;
                     } else {
                        InputStream llllllllllllllllllIlIIllIlIlIlIl = llllllllllllllllllIlIIllIlIIIlll.getInputStream(llllllllllllllllllIlIIllIIlIIlll);
                        char llllllllllllllllllIlIIllIIlIIIll = new FileOutputStream(llllllllllllllllllIlIIllIIlIIlIl);
                        IOUtils.copy(llllllllllllllllllIlIIllIlIlIlIl, (OutputStream)llllllllllllllllllIlIIllIIlIIIll);
                        var10001 = false;
                        llllllllllllllllllIlIIllIIlIIIll.close();
                        llllllllllllllllllIlIIllIlIlIlIl.close();
                        Intrinsics.checkExpressionValueIsNotNull(llllllllllllllllllIlIIllIIlIIllI, "entryName");
                        if (!StringsKt.contains$default((CharSequence)llllllllllllllllllIlIIllIIlIIllI, (CharSequence)"/", false, 2, (Object)null)) {
                           llllllllllllllllllIlIIllIlIIlIIl.add(llllllllllllllllllIlIIllIIlIIlIl);
                           var10001 = false;
                        }
                     }
                  }

                  char llllllllllllllllllIlIIllIIlIIlll = (Iterable)llllllllllllllllllIlIIllIlIIlIIl;
                  boolean llllllllllllllllllIlIIllIIlIIllI = false;
                  Iterator llllllllllllllllllIlIIllIIlIIlIl = llllllllllllllllllIlIIllIIlIIlll.iterator();

                  while(llllllllllllllllllIlIIllIIlIIlIl.hasNext()) {
                     Object llllllllllllllllllIlIIllIlIIlllI = llllllllllllllllllIlIIllIIlIIlIl.next();
                     File llllllllllllllllllIlIIllIlIlIIII = (File)llllllllllllllllllIlIIllIlIIlllI;
                     short llllllllllllllllllIlIIllIIlIIIlI = false;
                     LiquidBounce.INSTANCE.getScriptManager().loadScript(llllllllllllllllllIlIIllIlIlIIII);
                  }

                  LiquidBounce.INSTANCE.setClickGui(new ClickGui());
                  LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().clickGuiConfig);
                  LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().hudConfig);
                  llllllllllllllllllIlIIllIIllIIII.chat("Successfully imported script.");
                  return;
               }

               llllllllllllllllllIlIIllIIllIIII.chat("The file extension has to be .js or .zip");
            } catch (Throwable var19) {
               ClientUtils.getLogger().error("Something went wrong while importing a script.", var19);
               llllllllllllllllllIlIIllIIllIIII.chat(String.valueOf((new StringBuilder()).append(var19.getClass().getName()).append(": ").append(var19.getMessage())));
            }
         } else if (StringsKt.equals(llllllllllllllllllIlIIllIIlIllll[1], "delete", true)) {
            try {
               if (llllllllllllllllllIlIIllIIlIllll.length <= 2) {
                  llllllllllllllllllIlIIllIIllIIII.chatSyntax("scriptmanager delete <index>");
                  return;
               }

               llllllllllllllllllIlIIllIIlIlIll = llllllllllllllllllIlIIllIIlIllll[2];
               llllllllllllllllllIlIIllIIlIlIlI = false;
               boolean llllllllllllllllllIlIIllIIlIllII = Integer.parseInt(llllllllllllllllllIlIIllIIlIlIll);
               List llllllllllllllllllIlIIllIIllllll = LiquidBounce.INSTANCE.getScriptManager().getScripts();
               if (llllllllllllllllllIlIIllIIlIllII >= llllllllllllllllllIlIIllIIllllll.size()) {
                  llllllllllllllllllIlIIllIIllIIII.chat(String.valueOf((new StringBuilder()).append("Index ").append(llllllllllllllllllIlIIllIIlIllII).append(" is too high.")));
                  return;
               }

               char llllllllllllllllllIlIIllIIlIlIlI = (Script)llllllllllllllllllIlIIllIIllllll.get(llllllllllllllllllIlIIllIIlIllII);
               LiquidBounce.INSTANCE.getScriptManager().deleteScript(llllllllllllllllllIlIIllIIlIlIlI);
               LiquidBounce.INSTANCE.setClickGui(new ClickGui());
               LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().clickGuiConfig);
               LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().hudConfig);
               llllllllllllllllllIlIIllIIllIIII.chat("Successfully deleted script.");
            } catch (NumberFormatException var17) {
               llllllllllllllllllIlIIllIIllIIII.chatSyntaxError();
            } catch (Throwable var18) {
               ClientUtils.getLogger().error("Something went wrong while deleting a script.", var18);
               llllllllllllllllllIlIIllIIllIIII.chat(String.valueOf((new StringBuilder()).append(var18.getClass().getName()).append(": ").append(var18.getMessage())));
            }
         } else if (StringsKt.equals(llllllllllllllllllIlIIllIIlIllll[1], "reload", true)) {
            try {
               LiquidBounce.INSTANCE.getScriptManager().reloadScripts();
               llllllllllllllllllIlIIllIIllIIII.chat("Successfully reloaded all scripts.");
            } catch (Throwable var16) {
               ClientUtils.getLogger().error("Something went wrong while reloading all scripts.", var16);
               llllllllllllllllllIlIIllIIllIIII.chat(String.valueOf((new StringBuilder()).append(var16.getClass().getName()).append(": ").append(var16.getMessage())));
            }
         } else if (StringsKt.equals(llllllllllllllllllIlIIllIIlIllll[1], "folder", true)) {
            try {
               Desktop.getDesktop().open(LiquidBounce.INSTANCE.getScriptManager().getScriptsFolder());
               llllllllllllllllllIlIIllIIllIIII.chat("Successfully opened scripts folder.");
            } catch (Throwable var15) {
               ClientUtils.getLogger().error("Something went wrong while trying to open your scripts folder.", var15);
               llllllllllllllllllIlIIllIIllIIII.chat(String.valueOf((new StringBuilder()).append(var15.getClass().getName()).append(": ").append(var15.getMessage())));
            }
         }

      } else {
         ScriptManager llllllllllllllllllIlIIllIIllIIIl = LiquidBounce.INSTANCE.getScriptManager();
         byte llllllllllllllllllIlIIllIIlIlIll = (Collection)llllllllllllllllllIlIIllIIllIIIl.getScripts();
         llllllllllllllllllIlIIllIIlIlIlI = false;
         if (!llllllllllllllllllIlIIllIIlIlIll.isEmpty()) {
            llllllllllllllllllIlIIllIIllIIII.chat("§c§lScripts");
            Iterable llllllllllllllllllIlIIllIIllIIll = (Iterable)llllllllllllllllllIlIIllIIllIIIl.getScripts();
            llllllllllllllllllIlIIllIIlIlIlI = false;
            short llllllllllllllllllIlIIllIIlIlIIl = 0;
            Iterator llllllllllllllllllIlIIllIIlIlIII = llllllllllllllllllIlIIllIIllIIll.iterator();

            while(llllllllllllllllllIlIIllIIlIlIII.hasNext()) {
               char llllllllllllllllllIlIIllIIlIIlll = llllllllllllllllllIlIIllIIlIlIII.next();
               boolean llllllllllllllllllIlIIllIIlIIllI = llllllllllllllllllIlIIllIIlIlIIl++;
               boolean llllllllllllllllllIlIIllIIlIIlIl = false;
               if (llllllllllllllllllIlIIllIIlIIllI < 0) {
                  CollectionsKt.throwIndexOverflow();
               }

               char llllllllllllllllllIlIIllIIlIIIll = (Script)llllllllllllllllllIlIIllIIlIIlll;
               double llllllllllllllllllIlIIllIIlIIIIl = false;
               llllllllllllllllllIlIIllIIllIIII.chat(String.valueOf((new StringBuilder()).append(llllllllllllllllllIlIIllIIlIIllI).append(": §a§l").append(llllllllllllllllllIlIIllIIlIIIll.getScriptName()).append(" §a§lv").append(llllllllllllllllllIlIIllIIlIIIll.getScriptVersion()).append(" §3by §a§l").append(llllllllllllllllllIlIIllIIlIIIll.getScriptAuthor())));
            }
         }

         llllllllllllllllllIlIIllIIllIIII.chatSyntax("scriptmanager <import/delete/reload/folder>");
      }
   }
}
