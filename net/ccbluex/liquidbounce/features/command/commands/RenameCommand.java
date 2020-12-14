//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.command.commands;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.StringUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\b¨\u0006\t"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/RenameCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "LiquidSense"}
)
public final class RenameCommand extends Command {
   public RenameCommand() {
      byte lIIllIllllIIIII = "rename";
      long lIIllIlllIlllll = new String[0];
      super(lIIllIllllIIIII, lIIllIlllIlllll);
   }

   public void execute(@NotNull String[] lIIllIllllIlIIl) {
      Intrinsics.checkParameterIsNotNull(lIIllIllllIlIIl, "args");
      if (lIIllIllllIlIIl.length > 1) {
         PlayerControllerMP var10000 = access$getMc$p$s1046033730().playerController;
         Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.playerController");
         if (var10000.isNotCreative()) {
            lIIllIllllIlIlI.chat("§c§lError: §3You need to be in creative mode.");
         } else {
            EntityPlayerSP var3 = access$getMc$p$s1046033730().thePlayer;
            Intrinsics.checkExpressionValueIsNotNull(var3, "mc.thePlayer");
            ItemStack lIIllIllllIllIl = var3.getHeldItem();
            if (lIIllIllllIllIl != null && lIIllIllllIllIl.getItem() != null) {
               String var10001 = StringUtils.toCompleteString(lIIllIllllIlIIl, 1);
               Intrinsics.checkExpressionValueIsNotNull(var10001, "StringUtils.toCompleteString(args, 1)");
               lIIllIllllIllIl.setStackDisplayName(ColorUtils.translateAlternateColorCodes(var10001));
               boolean var4 = false;
               lIIllIllllIlIlI.chat(String.valueOf((new StringBuilder()).append("§3Item renamed to '").append(lIIllIllllIllIl.getDisplayName()).append("§3'")));
            } else {
               lIIllIllllIlIlI.chat("§c§lError: §3You need to hold a item.");
            }
         }
      } else {
         lIIllIllllIlIlI.chatSyntax("rename <name>");
      }
   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }
}
