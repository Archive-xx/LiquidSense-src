package net.ccbluex.liquidbounce.features.command;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.features.command.commands.AutoSettingsCommand;
import net.ccbluex.liquidbounce.features.command.commands.BindCommand;
import net.ccbluex.liquidbounce.features.command.commands.BindsCommand;
import net.ccbluex.liquidbounce.features.command.commands.EnchantCommand;
import net.ccbluex.liquidbounce.features.command.commands.FriendCommand;
import net.ccbluex.liquidbounce.features.command.commands.GiveCommand;
import net.ccbluex.liquidbounce.features.command.commands.HClipCommand;
import net.ccbluex.liquidbounce.features.command.commands.HelpCommand;
import net.ccbluex.liquidbounce.features.command.commands.HideCommand;
import net.ccbluex.liquidbounce.features.command.commands.HoloStandCommand;
import net.ccbluex.liquidbounce.features.command.commands.HurtCommand;
import net.ccbluex.liquidbounce.features.command.commands.LocalAutoSettingsCommand;
import net.ccbluex.liquidbounce.features.command.commands.LoginCommand;
import net.ccbluex.liquidbounce.features.command.commands.PanicCommand;
import net.ccbluex.liquidbounce.features.command.commands.PingCommand;
import net.ccbluex.liquidbounce.features.command.commands.PrefixCommand;
import net.ccbluex.liquidbounce.features.command.commands.ReloadCommand;
import net.ccbluex.liquidbounce.features.command.commands.RemoteViewCommand;
import net.ccbluex.liquidbounce.features.command.commands.RenameCommand;
import net.ccbluex.liquidbounce.features.command.commands.SayCommand;
import net.ccbluex.liquidbounce.features.command.commands.ScriptManagerCommand;
import net.ccbluex.liquidbounce.features.command.commands.ServerInfoCommand;
import net.ccbluex.liquidbounce.features.command.commands.ShortcutCommand;
import net.ccbluex.liquidbounce.features.command.commands.TacoCommand;
import net.ccbluex.liquidbounce.features.command.commands.TargetCommand;
import net.ccbluex.liquidbounce.features.command.commands.ToggleCommand;
import net.ccbluex.liquidbounce.features.command.commands.TpCommand;
import net.ccbluex.liquidbounce.features.command.commands.UsernameCommand;
import net.ccbluex.liquidbounce.features.command.commands.VClipCommand;
import net.ccbluex.liquidbounce.features.command.shortcuts.Shortcut;
import net.ccbluex.liquidbounce.features.command.shortcuts.ShortcutParser;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\f\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\f\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\nJ\u000e\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0018\u001a\u00020\nJ\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u001c\u001a\u00020\nJ\u001d\u0010\u001d\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\t2\u0006\u0010\u0018\u001a\u00020\nH\u0002¢\u0006\u0002\u0010\u001eJ\u000e\u0010\u001f\u001a\u00020\u00172\u0006\u0010 \u001a\u00020\u0005J\u0006\u0010!\u001a\u00020\u001aJ\u0016\u0010\"\u001a\u00020\u001a2\u0006\u0010\u001c\u001a\u00020\n2\u0006\u0010#\u001a\u00020\nJ\u0010\u0010$\u001a\u00020\u00172\b\u0010 \u001a\u0004\u0018\u00010\u0005J\u000e\u0010%\u001a\u00020\u00172\u0006\u0010\u001c\u001a\u00020\nR\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\"\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0086\u000e¢\u0006\u0010\n\u0002\u0010\u000f\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u0010\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015¨\u0006&"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/command/CommandManager;", "", "()V", "commands", "", "Lnet/ccbluex/liquidbounce/features/command/Command;", "getCommands", "()Ljava/util/List;", "latestAutoComplete", "", "", "getLatestAutoComplete", "()[Ljava/lang/String;", "setLatestAutoComplete", "([Ljava/lang/String;)V", "[Ljava/lang/String;", "prefix", "", "getPrefix", "()C", "setPrefix", "(C)V", "autoComplete", "", "input", "executeCommands", "", "getCommand", "name", "getCompletions", "(Ljava/lang/String;)[Ljava/lang/String;", "registerCommand", "command", "registerCommands", "registerShortcut", "script", "unregisterCommand", "unregisterShortcut", "LiquidSense"}
)
@SideOnly(Side.CLIENT)
public final class CommandManager {
   // $FF: synthetic field
   private char prefix;
   // $FF: synthetic field
   @NotNull
   private final List<Command> commands;
   // $FF: synthetic field
   @NotNull
   private String[] latestAutoComplete;

   public final void setPrefix(char llllllllllllllllllIlIIIlIIlIIlIl) {
      llllllllllllllllllIlIIIlIIlIlIII.prefix = llllllllllllllllllIlIIIlIIlIIlIl;
   }

   public final boolean unregisterCommand(@Nullable Command llllllllllllllllllIIllllllIlIIlI) {
      int llllllllllllllllllIIllllllIlIIIl = (Collection)llllllllllllllllllIIllllllIlIIll.commands;
      int llllllllllllllllllIIllllllIlIIII = false;
      if (llllllllllllllllllIIllllllIlIIIl == null) {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableCollection<T>");
      } else {
         return TypeIntrinsics.asMutableCollection(llllllllllllllllllIIllllllIlIIIl).remove(llllllllllllllllllIIllllllIlIIlI);
      }
   }

   private final String[] getCompletions(String llllllllllllllllllIlIIIIIllllIII) {
      char llllllllllllllllllIlIIIIIlllIlIl = (CharSequence)llllllllllllllllllIlIIIIIllllIII;
      float llllllllllllllllllIlIIIIIlllIlII = false;
      if (llllllllllllllllllIlIIIIIlllIlIl.length() > 0) {
         llllllllllllllllllIlIIIIIlllIlII = false;
         if (llllllllllllllllllIlIIIIIllllIII == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
         }

         char[] var10000 = llllllllllllllllllIlIIIIIllllIII.toCharArray();
         Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).toCharArray()");
         if (var10000[0] == llllllllllllllllllIlIIIIIllllIIl.prefix) {
            List llllllllllllllllllIlIIIIIllllIlI = StringsKt.split$default((CharSequence)llllllllllllllllllIlIIIIIllllIII, new String[]{" "}, false, 0, 6, (Object)null);
            byte llllllllllllllllllIlIIIIIlllIIlI;
            boolean llllllllllllllllllIlIIIIIlllIIIl;
            boolean var10001;
            String llllllllllllllllllIlIIIIIllIIIII;
            String var33;
            Object[] var34;
            String[] var36;
            if (llllllllllllllllllIlIIIIIllllIlI.size() > 1) {
               int llllllllllllllllllIlIIIIIlllIIll = (String)llllllllllllllllllIlIIIIIllllIlI.get(0);
               llllllllllllllllllIlIIIIIlllIIlI = 1;
               llllllllllllllllllIlIIIIIlllIIIl = false;
               if (llllllllllllllllllIlIIIIIlllIIll == null) {
                  throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
               }

               var33 = llllllllllllllllllIlIIIIIlllIIll.substring(llllllllllllllllllIlIIIIIlllIIlI);
               Intrinsics.checkExpressionValueIsNotNull(var33, "(this as java.lang.String).substring(startIndex)");
               llllllllllllllllllIlIIIIIllIIIII = var33;
               Command llllllllllllllllllIlIIIIlIIllIlI = llllllllllllllllllIlIIIIIllllIIl.getCommand(llllllllllllllllllIlIIIIIllIIIII);
               Collection llllllllllllllllllIlIIIIIlllIIlI;
               List var35;
               if (llllllllllllllllllIlIIIIlIIllIlI != null) {
                  llllllllllllllllllIlIIIIIlllIIlI = (Collection)CollectionsKt.drop((Iterable)llllllllllllllllllIlIIIIIllllIlI, 1);
                  llllllllllllllllllIlIIIIIlllIIIl = false;
                  var34 = llllllllllllllllllIlIIIIIlllIIlI.toArray(new String[0]);
                  if (var34 == null) {
                     throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
                  }

                  float llllllllllllllllllIlIIIIIllIIIII = var34;
                  var35 = llllllllllllllllllIlIIIIlIIllIlI.tabComplete((String[])llllllllllllllllllIlIIIIIllIIIII);
               } else {
                  var10001 = false;
                  var35 = null;
               }

               List llllllllllllllllllIlIIIIlIIllIll = var35;
               if (llllllllllllllllllIlIIIIlIIllIll != null) {
                  llllllllllllllllllIlIIIIIlllIIlI = (Collection)llllllllllllllllllIlIIIIlIIllIll;
                  llllllllllllllllllIlIIIIIlllIIIl = false;
                  var34 = llllllllllllllllllIlIIIIIlllIIlI.toArray(new String[0]);
                  if (var34 == null) {
                     throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
                  }

                  var36 = (String[])var34;
               } else {
                  var10001 = false;
                  var36 = null;
               }
            } else {
               llllllllllllllllllIlIIIIIlllIIlI = 1;
               llllllllllllllllllIlIIIIIlllIIIl = false;
               if (llllllllllllllllllIlIIIIIllllIII == null) {
                  throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
               }

               var33 = llllllllllllllllllIlIIIIIllllIII.substring(llllllllllllllllllIlIIIIIlllIIlI);
               Intrinsics.checkExpressionValueIsNotNull(var33, "(this as java.lang.String).substring(startIndex)");
               String llllllllllllllllllIlIIIIIllllIll = var33;
               int llllllllllllllllllIlIIIIlIIIIIII = (Iterable)llllllllllllllllllIlIIIIIllllIIl.commands;
               byte llllllllllllllllllIlIIIIIlllIIlI = false;
               char llllllllllllllllllIlIIIIIlllIIII = (Collection)(new ArrayList());
               int llllllllllllllllllIlIIIIlIIIIIIl = false;
               Iterator llllllllllllllllllIlIIIIIllIlllI = llllllllllllllllllIlIIIIlIIIIIII.iterator();

               Object llllllllllllllllllIlIIIIlIIlIIlI;
               Command llllllllllllllllllIlIIIIlIIIIllI;
               boolean llllllllllllllllllIlIIIIIllIlIll;
               String[] llllllllllllllllllIlIIIIIllIlIlI;
               boolean llllllllllllllllllIlIIIIIllIlIIl;
               String[] llllllllllllllllllIlIIIIIllIlIII;
               int llllllllllllllllllIlIIIIIllIIlll;
               int llllllllllllllllllIlIIIIIllIIllI;
               String llllllllllllllllllIlIIIIIllIIlIl;
               boolean llllllllllllllllllIlIIIIIllIIIll;
               while(llllllllllllllllllIlIIIIIllIlllI.hasNext()) {
                  boolean var37;
                  label101: {
                     llllllllllllllllllIlIIIIlIIlIIlI = llllllllllllllllllIlIIIIIllIlllI.next();
                     llllllllllllllllllIlIIIIlIIIIllI = (Command)llllllllllllllllllIlIIIIlIIlIIlI;
                     llllllllllllllllllIlIIIIIllIlIll = false;
                     if (!StringsKt.startsWith(llllllllllllllllllIlIIIIlIIIIllI.getCommand(), llllllllllllllllllIlIIIIIllllIll, true)) {
                        llllllllllllllllllIlIIIIIllIlIlI = llllllllllllllllllIlIIIIlIIIIllI.getAlias();
                        llllllllllllllllllIlIIIIIllIlIIl = false;
                        llllllllllllllllllIlIIIIIllIlIII = llllllllllllllllllIlIIIIIllIlIlI;
                        llllllllllllllllllIlIIIIIllIIlll = llllllllllllllllllIlIIIIIllIlIlI.length;
                        llllllllllllllllllIlIIIIIllIIllI = 0;

                        while(true) {
                           if (llllllllllllllllllIlIIIIIllIIllI >= llllllllllllllllllIlIIIIIllIIlll) {
                              var37 = false;
                              break;
                           }

                           llllllllllllllllllIlIIIIIllIIlIl = llllllllllllllllllIlIIIIIllIlIII[llllllllllllllllllIlIIIIIllIIllI];
                           llllllllllllllllllIlIIIIIllIIIll = false;
                           if (StringsKt.startsWith(llllllllllllllllllIlIIIIIllIIlIl, llllllllllllllllllIlIIIIIllllIll, true)) {
                              var37 = true;
                              break;
                           }

                           ++llllllllllllllllllIlIIIIIllIIllI;
                        }

                        if (!var37) {
                           var37 = false;
                           break label101;
                        }
                     }

                     var37 = true;
                  }

                  if (var37) {
                     llllllllllllllllllIlIIIIIlllIIII.add(llllllllllllllllllIlIIIIlIIlIIlI);
                     var10001 = false;
                  }
               }

               llllllllllllllllllIlIIIIlIIIIIII = (Iterable)((List)llllllllllllllllllIlIIIIIlllIIII);
               llllllllllllllllllIlIIIIIlllIIlI = false;
               llllllllllllllllllIlIIIIIlllIIII = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault(llllllllllllllllllIlIIIIlIIIIIII, 10)));
               llllllllllllllllllIlIIIIlIIIIIIl = false;

               for(llllllllllllllllllIlIIIIIllIlllI = llllllllllllllllllIlIIIIlIIIIIII.iterator(); llllllllllllllllllIlIIIIIllIlllI.hasNext(); var10001 = false) {
                  llllllllllllllllllIlIIIIlIIlIIlI = llllllllllllllllllIlIIIIIllIlllI.next();
                  llllllllllllllllllIlIIIIlIIIIllI = (Command)llllllllllllllllllIlIIIIlIIlIIlI;
                  llllllllllllllllllIlIIIIIllIlIll = false;
                  if (StringsKt.startsWith(llllllllllllllllllIlIIIIlIIIIllI.getCommand(), llllllllllllllllllIlIIIIIllllIll, true)) {
                     var33 = llllllllllllllllllIlIIIIlIIIIllI.getCommand();
                  } else {
                     llllllllllllllllllIlIIIIIllIlIlI = llllllllllllllllllIlIIIIlIIIIllI.getAlias();
                     llllllllllllllllllIlIIIIIllIlIIl = false;
                     llllllllllllllllllIlIIIIIllIlIII = llllllllllllllllllIlIIIIIllIlIlI;
                     llllllllllllllllllIlIIIIIllIIlll = llllllllllllllllllIlIIIIIllIlIlI.length;
                     llllllllllllllllllIlIIIIIllIIllI = 0;

                     while(true) {
                        if (llllllllllllllllllIlIIIIIllIIllI >= llllllllllllllllllIlIIIIIllIIlll) {
                           throw (Throwable)(new NoSuchElementException("Array contains no element matching the predicate."));
                        }

                        llllllllllllllllllIlIIIIIllIIlIl = llllllllllllllllllIlIIIIIllIlIII[llllllllllllllllllIlIIIIIllIIllI];
                        llllllllllllllllllIlIIIIIllIIIll = false;
                        if (StringsKt.startsWith(llllllllllllllllllIlIIIIIllIIlIl, llllllllllllllllllIlIIIIIllllIll, true)) {
                           var33 = llllllllllllllllllIlIIIIIllIIlIl;
                           break;
                        }

                        ++llllllllllllllllllIlIIIIIllIIllI;
                     }
                  }

                  String llllllllllllllllllIlIIIIlIIIIlll = var33;
                  int llllllllllllllllllIlIIIIIllIlIlI = llllllllllllllllllIlIIIIIllllIIl.prefix;
                  llllllllllllllllllIlIIIIIllIlIIl = false;
                  llllllllllllllllllIlIIIIIllIIIII = String.valueOf((new StringBuilder()).append(String.valueOf(llllllllllllllllllIlIIIIIllIlIlI)).append(llllllllllllllllllIlIIIIlIIIIlll));
                  llllllllllllllllllIlIIIIIlllIIII.add(llllllllllllllllllIlIIIIIllIIIII);
               }

               Collection llllllllllllllllllIlIIIIIlllllIl = (Collection)((List)llllllllllllllllllIlIIIIIlllIIII);
               llllllllllllllllllIlIIIIIlllIIlI = false;
               var34 = llllllllllllllllllIlIIIIIlllllIl.toArray(new String[0]);
               if (var34 == null) {
                  throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
               }

               var36 = (String[])var34;
            }

            return var36;
         }
      }

      return null;
   }

   public final boolean registerCommand(@NotNull Command llllllllllllllllllIlIIIIIIlIllIl) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIlIIIIIIlIllIl, "command");
      return llllllllllllllllllIlIIIIIIllIIII.commands.add(llllllllllllllllllIlIIIIIIlIllIl);
   }

   public final boolean unregisterShortcut(@NotNull final String llllllllllllllllllIIllllllIllIlI) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIIllllllIllIlI, "name");
      return llllllllllllllllllIIllllllIlllIl.commands.removeIf((Predicate)(new Predicate<Command>() {
         public final boolean test(@NotNull Command llIlIIllIlIllII) {
            Intrinsics.checkParameterIsNotNull(llIlIIllIlIllII, "it");
            return llIlIIllIlIllII instanceof Shortcut && StringsKt.equals(llIlIIllIlIllII.getCommand(), llllllllllllllllllIIllllllIllIlI, true);
         }
      }));
   }

   public CommandManager() {
      int llllllllllllllllllIIllllllIIlIIl = false;
      char llllllllllllllllllIIllllllIIIlll = (List)(new ArrayList());
      llllllllllllllllllIIllllllIIlIlI.commands = llllllllllllllllllIIllllllIIIlll;
      char llllllllllllllllllIIllllllIIIlll = new String[0];
      llllllllllllllllllIIllllllIIlIlI.latestAutoComplete = llllllllllllllllllIIllllllIIIlll;
      llllllllllllllllllIIllllllIIlIlI.prefix = '.';
   }

   public final char getPrefix() {
      return llllllllllllllllllIlIIIlIIlIllII.prefix;
   }

   @NotNull
   public final String[] getLatestAutoComplete() {
      return llllllllllllllllllIlIIIlIIllIlII.latestAutoComplete;
   }

   public final void setLatestAutoComplete(@NotNull String[] llllllllllllllllllIlIIIlIIllIIII) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIlIIIlIIllIIII, "<set-?>");
      llllllllllllllllllIlIIIlIIllIIIl.latestAutoComplete = llllllllllllllllllIlIIIlIIllIIII;
   }

   public final void executeCommands(@NotNull String llllllllllllllllllIlIIIlIIIIIIlI) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIlIIIlIIIIIIlI, "input");
      Iterator llllllllllllllllllIlIIIlIIIIIIII = llllllllllllllllllIlIIIlIIIIIlIl.commands.iterator();

      while(llllllllllllllllllIlIIIlIIIIIIII.hasNext()) {
         long llllllllllllllllllIlIIIlIIIIIIIl = (Command)llllllllllllllllllIlIIIlIIIIIIII.next();
         float llllllllllllllllllIlIIIIlllllllI = (Collection)StringsKt.split$default((CharSequence)llllllllllllllllllIlIIIlIIIIIIlI, new String[]{" "}, false, 0, 6, (Object)null);
         int llllllllllllllllllIlIIIlIIIIlIIl = false;
         Object[] var10000 = llllllllllllllllllIlIIIIlllllllI.toArray(new String[0]);
         if (var10000 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
         }

         String[] llllllllllllllllllIlIIIlIIIIIlll = (String[])var10000;
         if (StringsKt.equals(llllllllllllllllllIlIIIlIIIIIlll[0], String.valueOf((new StringBuilder()).append(String.valueOf(llllllllllllllllllIlIIIlIIIIIlIl.prefix)).append(llllllllllllllllllIlIIIlIIIIIIIl.getCommand())), true)) {
            llllllllllllllllllIlIIIlIIIIIIIl.execute(llllllllllllllllllIlIIIlIIIIIlll);
            return;
         }

         String llllllllllllllllllIlIIIIllllllII = llllllllllllllllllIlIIIlIIIIIIIl.getAlias();
         float llllllllllllllllllIlIIIIlllllIll = llllllllllllllllllIlIIIIllllllII.length;

         for(int llllllllllllllllllIlIIIIllllllIl = 0; llllllllllllllllllIlIIIIllllllIl < llllllllllllllllllIlIIIIlllllIll; ++llllllllllllllllllIlIIIIllllllIl) {
            float llllllllllllllllllIlIIIIlllllllI = llllllllllllllllllIlIIIIllllllII[llllllllllllllllllIlIIIIllllllIl];
            if (StringsKt.equals(llllllllllllllllllIlIIIlIIIIIlll[0], String.valueOf((new StringBuilder()).append(String.valueOf(llllllllllllllllllIlIIIlIIIIIlIl.prefix)).append(llllllllllllllllllIlIIIIlllllllI)), true)) {
               llllllllllllllllllIlIIIlIIIIIIIl.execute(llllllllllllllllllIlIIIlIIIIIlll);
               return;
            }
         }
      }

      ClientUtils.displayChatMessage(String.valueOf((new StringBuilder()).append("§cCommand not found. Type ").append(llllllllllllllllllIlIIIlIIIIIlIl.prefix).append("help to view all commands.")));
   }

   public final boolean autoComplete(@NotNull String llllllllllllllllllIlIIIIllIlllll) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIlIIIIllIlllll, "input");
      CommandManager var10000 = llllllllllllllllllIlIIIIllIlllIl;
      String[] var10001 = llllllllllllllllllIlIIIIllIlllIl.getCompletions(llllllllllllllllllIlIIIIllIlllll);
      if (var10001 == null) {
         boolean var10002 = false;
         float llllllllllllllllllIlIIIIllIlIIll = new String[0];
         var10000 = llllllllllllllllllIlIIIIllIlllIl;
         var10001 = llllllllllllllllllIlIIIIllIlIIll;
      }

      var10000.latestAutoComplete = var10001;
      boolean var8;
      if (StringsKt.startsWith$default((CharSequence)llllllllllllllllllIlIIIIllIlllll, llllllllllllllllllIlIIIIllIlllIl.prefix, false, 2, (Object)null)) {
         double llllllllllllllllllIlIIIIllIllIIl = llllllllllllllllllIlIIIIllIlllIl.latestAutoComplete;
         short llllllllllllllllllIlIIIIllIlIlll = false;
         int llllllllllllllllllIlIIIIllIlIlIl = false;
         if (llllllllllllllllllIlIIIIllIllIIl.length != 0) {
            var8 = true;
            return var8;
         }
      }

      var8 = false;
      return var8;
   }

   @NotNull
   public final List<Command> getCommands() {
      return llllllllllllllllllIlIIIlIIlllIII.commands;
   }

   public final void registerShortcut(@NotNull String llllllllllllllllllIIlllllllllIII, @NotNull String llllllllllllllllllIIlllllllllIlI) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIIlllllllllIII, "name");
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIIlllllllllIlI, "script");
      if (llllllllllllllllllIIllllllllllIl.getCommand(llllllllllllllllllIIlllllllllIII) == null) {
         Iterable llllllllllllllllllIlIIIIIIIIIIII = (Iterable)ShortcutParser.INSTANCE.parse(llllllllllllllllllIIlllllllllIlI);
         int llllllllllllllllllIIlllllllllllI = false;
         float llllllllllllllllllIIllllllllIIll = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault(llllllllllllllllllIlIIIIIIIIIIII, 10)));
         char llllllllllllllllllIIllllllllIIlI = false;

         boolean var10001;
         for(Iterator llllllllllllllllllIIllllllllIIIl = llllllllllllllllllIlIIIIIIIIIIII.iterator(); llllllllllllllllllIIllllllllIIIl.hasNext(); var10001 = false) {
            boolean llllllllllllllllllIIllllllllIIII = llllllllllllllllllIIllllllllIIIl.next();
            String llllllllllllllllllIIlllllllIllll = (List)llllllllllllllllllIIllllllllIIII;
            Exception llllllllllllllllllIIlllllllIlllI = false;
            Command var10000 = llllllllllllllllllIIllllllllllIl.getCommand((String)llllllllllllllllllIIlllllllIllll.get(0));
            if (var10000 == null) {
               var10001 = false;
               throw (Throwable)(new IllegalArgumentException(String.valueOf((new StringBuilder()).append("Command ").append((String)llllllllllllllllllIIlllllllIllll.get(0)).append(" not found!"))));
            }

            Command llllllllllllllllllIlIIIIIIIIlIIl = var10000;
            String llllllllllllllllllIIlllllllIllII = (Collection)llllllllllllllllllIIlllllllIllll;
            String llllllllllllllllllIIlllllllIlIlI = false;
            Object[] var30 = llllllllllllllllllIIlllllllIllII.toArray(new String[0]);
            if (var30 == null) {
               throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
            }

            double llllllllllllllllllIIlllllllIlIII = var30;
            long llllllllllllllllllIIlllllllIIlII = new Pair(llllllllllllllllllIlIIIIIIIIlIIl, llllllllllllllllllIIlllllllIlIII);
            llllllllllllllllllIIllllllllIIll.add(llllllllllllllllllIIlllllllIIlII);
         }

         String llllllllllllllllllIIlllllllIIlIl = (List)llllllllllllllllllIIllllllllIIll;
         llllllllllllllllllIIllllllllllIl.registerCommand((Command)(new Shortcut(llllllllllllllllllIIlllllllllIII, llllllllllllllllllIIlllllllIIlIl)));
         var10001 = false;
      } else {
         throw (Throwable)(new IllegalArgumentException("Command already exists!"));
      }
   }

   public final void registerCommands() {
      llllllllllllllllllIlIIIlIIlIIIll.registerCommand((Command)(new BindCommand()));
      boolean var10001 = false;
      llllllllllllllllllIlIIIlIIlIIIll.registerCommand((Command)(new VClipCommand()));
      var10001 = false;
      llllllllllllllllllIlIIIlIIlIIIll.registerCommand((Command)(new HClipCommand()));
      var10001 = false;
      llllllllllllllllllIlIIIlIIlIIIll.registerCommand((Command)(new HelpCommand()));
      var10001 = false;
      llllllllllllllllllIlIIIlIIlIIIll.registerCommand((Command)(new SayCommand()));
      var10001 = false;
      llllllllllllllllllIlIIIlIIlIIIll.registerCommand((Command)(new FriendCommand()));
      var10001 = false;
      llllllllllllllllllIlIIIlIIlIIIll.registerCommand((Command)(new AutoSettingsCommand()));
      var10001 = false;
      llllllllllllllllllIlIIIlIIlIIIll.registerCommand((Command)(new LocalAutoSettingsCommand()));
      var10001 = false;
      llllllllllllllllllIlIIIlIIlIIIll.registerCommand((Command)(new ServerInfoCommand()));
      var10001 = false;
      llllllllllllllllllIlIIIlIIlIIIll.registerCommand((Command)(new ToggleCommand()));
      var10001 = false;
      llllllllllllllllllIlIIIlIIlIIIll.registerCommand((Command)(new HurtCommand()));
      var10001 = false;
      llllllllllllllllllIlIIIlIIlIIIll.registerCommand((Command)(new GiveCommand()));
      var10001 = false;
      llllllllllllllllllIlIIIlIIlIIIll.registerCommand((Command)(new UsernameCommand()));
      var10001 = false;
      llllllllllllllllllIlIIIlIIlIIIll.registerCommand((Command)(new TargetCommand()));
      var10001 = false;
      llllllllllllllllllIlIIIlIIlIIIll.registerCommand((Command)(new TacoCommand()));
      var10001 = false;
      llllllllllllllllllIlIIIlIIlIIIll.registerCommand((Command)(new BindsCommand()));
      var10001 = false;
      llllllllllllllllllIlIIIlIIlIIIll.registerCommand((Command)(new HoloStandCommand()));
      var10001 = false;
      llllllllllllllllllIlIIIlIIlIIIll.registerCommand((Command)(new PanicCommand()));
      var10001 = false;
      llllllllllllllllllIlIIIlIIlIIIll.registerCommand((Command)(new PingCommand()));
      var10001 = false;
      llllllllllllllllllIlIIIlIIlIIIll.registerCommand((Command)(new RenameCommand()));
      var10001 = false;
      llllllllllllllllllIlIIIlIIlIIIll.registerCommand((Command)(new EnchantCommand()));
      var10001 = false;
      llllllllllllllllllIlIIIlIIlIIIll.registerCommand((Command)(new ReloadCommand()));
      var10001 = false;
      llllllllllllllllllIlIIIlIIlIIIll.registerCommand((Command)(new LoginCommand()));
      var10001 = false;
      llllllllllllllllllIlIIIlIIlIIIll.registerCommand((Command)(new ScriptManagerCommand()));
      var10001 = false;
      llllllllllllllllllIlIIIlIIlIIIll.registerCommand((Command)(new RemoteViewCommand()));
      var10001 = false;
      llllllllllllllllllIlIIIlIIlIIIll.registerCommand((Command)(new PrefixCommand()));
      var10001 = false;
      llllllllllllllllllIlIIIlIIlIIIll.registerCommand((Command)(new ShortcutCommand()));
      var10001 = false;
      llllllllllllllllllIlIIIlIIlIIIll.registerCommand((Command)(new HideCommand()));
      var10001 = false;
      llllllllllllllllllIlIIIlIIlIIIll.registerCommand((Command)(new TpCommand()));
      var10001 = false;
   }

   @Nullable
   public final Command getCommand(@NotNull String llllllllllllllllllIlIIIIIlIIIIll) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIlIIIIIlIIIIll, "name");
      int llllllllllllllllllIlIIIIIlIIIIlI = (Iterable)llllllllllllllllllIlIIIIIlIIIlII.commands;
      float llllllllllllllllllIlIIIIIlIIIIIl = false;
      int llllllllllllllllllIlIIIIIIllllll = false;
      Iterator llllllllllllllllllIlIIIIIIlllllI = llllllllllllllllllIlIIIIIlIIIIlI.iterator();

      Object var19;
      while(true) {
         if (!llllllllllllllllllIlIIIIIIlllllI.hasNext()) {
            var19 = null;
            break;
         }

         Object llllllllllllllllllIlIIIIIIllllIl;
         boolean var10000;
         label35: {
            llllllllllllllllllIlIIIIIIllllIl = llllllllllllllllllIlIIIIIIlllllI.next();
            Command llllllllllllllllllIlIIIIIlIIlIII = (Command)llllllllllllllllllIlIIIIIIllllIl;
            int llllllllllllllllllIlIIIIIlIIIlll = false;
            if (!StringsKt.equals(llllllllllllllllllIlIIIIIlIIlIII.getCommand(), llllllllllllllllllIlIIIIIlIIIIll, true)) {
               Exception llllllllllllllllllIlIIIIIIlllIlI = llllllllllllllllllIlIIIIIlIIlIII.getAlias();
               long llllllllllllllllllIlIIIIIIlllIIl = false;
               byte llllllllllllllllllIlIIIIIIlllIII = llllllllllllllllllIlIIIIIIlllIlI;
               Exception llllllllllllllllllIlIIIIIIllIlll = llllllllllllllllllIlIIIIIIlllIlI.length;
               int llllllllllllllllllIlIIIIIIllIllI = 0;

               while(true) {
                  if (llllllllllllllllllIlIIIIIIllIllI >= llllllllllllllllllIlIIIIIIllIlll) {
                     var10000 = false;
                     break;
                  }

                  Object llllllllllllllllllIlIIIIIlIIlIll = llllllllllllllllllIlIIIIIIlllIII[llllllllllllllllllIlIIIIIIllIllI];
                  int llllllllllllllllllIlIIIIIlIIllII = false;
                  if (StringsKt.equals(llllllllllllllllllIlIIIIIlIIlIll, llllllllllllllllllIlIIIIIlIIIIll, true)) {
                     var10000 = true;
                     break;
                  }

                  ++llllllllllllllllllIlIIIIIIllIllI;
               }

               if (!var10000) {
                  var10000 = false;
                  break label35;
               }
            }

            var10000 = true;
         }

         if (var10000) {
            var19 = llllllllllllllllllIlIIIIIIllllIl;
            break;
         }
      }

      return (Command)var19;
   }
}
