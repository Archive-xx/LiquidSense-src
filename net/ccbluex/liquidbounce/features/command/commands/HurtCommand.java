//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.command.commands;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\b¨\u0006\t"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/HurtCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "LiquidSense"}
)
public final class HurtCommand extends Command {
   public void execute(@NotNull String[] lllllllllllllllllllIIlllllIlIIIl) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllllIIlllllIlIIIl, "args");
      byte lllllllllllllllllllIIlllllIIlllI = 1;
      if (lllllllllllllllllllIIlllllIlIIIl.length > 1) {
         try {
            boolean lllllllllllllllllllIIlllllIIllIl = lllllllllllllllllllIIlllllIlIIIl[1];
            boolean lllllllllllllllllllIIlllllIIllII = false;
            lllllllllllllllllllIIlllllIIlllI = Integer.parseInt(lllllllllllllllllllIIlllllIIllIl);
         } catch (NumberFormatException var11) {
            lllllllllllllllllllIIlllllIlIIII.chatSyntaxError();
            return;
         }
      }

      double lllllllllllllllllllIIlllllIlIlII = access$getMc$p$s1046033730().thePlayer.posX;
      double lllllllllllllllllllIIlllllIlIlIl = access$getMc$p$s1046033730().thePlayer.posY;
      double lllllllllllllllllllIIlllllIlIllI = access$getMc$p$s1046033730().thePlayer.posZ;
      float lllllllllllllllllllIIlllllIIlIIl = 0;

      Minecraft var10000;
      for(int lllllllllllllllllllIIlllllIIlIII = 65 * lllllllllllllllllllIIlllllIIlllI; lllllllllllllllllllIIlllllIIlIIl < lllllllllllllllllllIIlllllIIlIII; ++lllllllllllllllllllIIlllllIIlIIl) {
         var10000 = access$getMc$p$s1046033730();
         Intrinsics.checkExpressionValueIsNotNull(var10000, "mc");
         var10000.getNetHandler().addToSendQueue((Packet)(new C04PacketPlayerPosition(lllllllllllllllllllIIlllllIlIlII, lllllllllllllllllllIIlllllIlIlIl + 0.049D, lllllllllllllllllllIIlllllIlIllI, false)));
         var10000 = access$getMc$p$s1046033730();
         Intrinsics.checkExpressionValueIsNotNull(var10000, "mc");
         var10000.getNetHandler().addToSendQueue((Packet)(new C04PacketPlayerPosition(lllllllllllllllllllIIlllllIlIlII, lllllllllllllllllllIIlllllIlIlIl, lllllllllllllllllllIIlllllIlIllI, false)));
      }

      var10000 = access$getMc$p$s1046033730();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "mc");
      var10000.getNetHandler().addToSendQueue((Packet)(new C04PacketPlayerPosition(lllllllllllllllllllIIlllllIlIlII, lllllllllllllllllllIIlllllIlIlIl, lllllllllllllllllllIIlllllIlIllI, true)));
      lllllllllllllllllllIIlllllIlIIII.chat("You were damaged.");
   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   public HurtCommand() {
      Exception lllllllllllllllllllIIlllllIIIIII = "hurt";
      float lllllllllllllllllllIIllllIllllll = new String[0];
      super(lllllllllllllllllllIIlllllIIIIII, lllllllllllllllllllIIllllIllllll);
   }
}
