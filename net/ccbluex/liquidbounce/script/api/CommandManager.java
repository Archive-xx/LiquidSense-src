package net.ccbluex.liquidbounce.script.api;

import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.internal.runtime.JSType;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.Command;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J#\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00060\tH\u0007¢\u0006\u0002\u0010\nJ\u0010\u0010\u000b\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\rH\u0007J\u0010\u0010\u000e\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\rH\u0007J\u0010\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0007H\u0007¨\u0006\u000f"},
   d2 = {"Lnet/ccbluex/liquidbounce/script/api/CommandManager;", "", "()V", "executeCommand", "", "command", "", "Lnet/ccbluex/liquidbounce/features/command/Command;", "args", "", "(Lnet/ccbluex/liquidbounce/features/command/Command;[Ljava/lang/String;)V", "registerCommand", "scriptObjectMirror", "Ljdk/nashorn/api/scripting/ScriptObjectMirror;", "unregisterCommand", "LiquidSense"}
)
public final class CommandManager {
   // $FF: synthetic field
   public static final CommandManager INSTANCE;

   @JvmStatic
   public static final void executeCommand(@NotNull Command lIIIIIlIIIllII, @NotNull String[] lIIIIIlIIIlIll) {
      Intrinsics.checkParameterIsNotNull(lIIIIIlIIIllII, "command");
      Intrinsics.checkParameterIsNotNull(lIIIIIlIIIlIll, "args");
      lIIIIIlIIIllII.execute(lIIIIIlIIIlIll);
   }

   @JvmStatic
   public static final void unregisterCommand(@NotNull ScriptObjectMirror lIIIIIlIIlIIll) {
      Intrinsics.checkParameterIsNotNull(lIIIIIlIIlIIll, "scriptObjectMirror");
      Object var10000 = lIIIIIlIIlIIll.callMember("getName");
      if (var10000 == null) {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
      } else {
         String lIIIIIlIIlIlIl = (String)var10000;
         Command lIIIIIlIIlIllI = LiquidBounce.INSTANCE.getCommandManager().getCommand(lIIIIIlIIlIlIl);
         LiquidBounce.INSTANCE.getCommandManager().unregisterCommand(lIIIIIlIIlIllI);
         boolean var10001 = false;
      }
   }

   @JvmStatic
   @NotNull
   public static final Command registerCommand(@NotNull ScriptObjectMirror lIIIIIlIIllllI) {
      Intrinsics.checkParameterIsNotNull(lIIIIIlIIllllI, "scriptObjectMirror");
      Command var10000 = new Command() {
         public void execute(@NotNull String[] lIIllIIIlIIlIII) {
            Intrinsics.checkParameterIsNotNull(lIIllIIIlIIlIII, "args");
            CommandManager.this.callMember("execute", (Object)lIIllIIIlIIlIII);
            boolean var10001 = false;
         }
      };
      String var10003 = JSType.toString(lIIIIIlIIllllI.callMember("getName"));
      Intrinsics.checkExpressionValueIsNotNull(var10003, "JSType.toString(scriptOb…or.callMember(\"getName\"))");
      Object var10004 = JSType.toJavaArray(lIIIIIlIIllllI.callMember("getAliases"), String.class);
      if (var10004 == null) {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.String>");
      } else {
         var10000.<init>(var10003, (String[])var10004);
         long lIIIIIlIIlllIl = var10000;
         LiquidBounce.INSTANCE.getCommandManager().registerCommand((Command)lIIIIIlIIlllIl);
         boolean var10001 = false;
         return (Command)lIIIIIlIIlllIl;
      }
   }

   private CommandManager() {
   }

   static {
      CommandManager lIIIIIlIIIIIll = new CommandManager();
      INSTANCE = lIIIIIlIIIIIll;
   }

   @JvmStatic
   public static final void executeCommand(@NotNull String lIIIIIlIIIlIIl) {
      Intrinsics.checkParameterIsNotNull(lIIIIIlIIIlIIl, "command");
      LiquidBounce.INSTANCE.getCommandManager().executeCommands(lIIIIIlIIIlIIl);
   }

   @JvmStatic
   public static final void unregisterCommand(@NotNull Command lIIIIIlIIllIlI) {
      Intrinsics.checkParameterIsNotNull(lIIIIIlIIllIlI, "command");
      LiquidBounce.INSTANCE.getCommandManager().unregisterCommand(lIIIIIlIIllIlI);
      boolean var10001 = false;
   }
}
