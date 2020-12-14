package net.ccbluex.liquidbounce.features.command.commands;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import me.AquaVit.liquidSense.modules.world.Tp;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.features.module.Module;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\b¨\u0006\t"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/TpCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "LiquidSense"}
)
public final class TpCommand extends Command {
   public void execute(@NotNull String[] lllllllllllllllllllIIIllllIIIIll) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllllIIIllllIIIIll, "args");
      if (lllllllllllllllllllIIIllllIIIIll.length >= 3) {
         try {
            Float var10000 = Float.valueOf(lllllllllllllllllllIIIllllIIIIll[1]);
            Intrinsics.checkExpressionValueIsNotNull(var10000, "Float.valueOf(args[1])");
            Tp.x = var10000;
            var10000 = Float.valueOf(lllllllllllllllllllIIIllllIIIIll[2]);
            Intrinsics.checkExpressionValueIsNotNull(var10000, "Float.valueOf(args[2])");
            Tp.y = var10000;
            var10000 = Float.valueOf(lllllllllllllllllllIIIllllIIIIll[3]);
            Intrinsics.checkExpressionValueIsNotNull(var10000, "Float.valueOf(args[3])");
            Tp.z = var10000;
            Module var4 = LiquidBounce.INSTANCE.getModuleManager().getModule(Tp.class);
            if (var4 == null) {
               Intrinsics.throwNpe();
            }

            var4.setState(true);
            return;
         } catch (ArrayIndexOutOfBoundsException var3) {
            lllllllllllllllllllIIIllllIIIlII.chat("[Teleport] Please type X Y Z");
         }
      }

      lllllllllllllllllllIIIllllIIIlII.chatSyntax("Teleport <x> <y> <z>");
   }

   public TpCommand() {
      super("tp", new String[]{"t"});
   }
}
