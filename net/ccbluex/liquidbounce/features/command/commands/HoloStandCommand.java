//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.command.commands;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.StringUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\b¨\u0006\t"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/HoloStandCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "LiquidSense"}
)
public final class HoloStandCommand extends Command {
   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   public void execute(@NotNull String[] llllllllllllllllllIIIlIlIIIIIlIl) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIIIlIlIIIIIlIl, "args");
      if (llllllllllllllllllIIIlIlIIIIIlIl.length > 4) {
         PlayerControllerMP var10000 = access$getMc$p$s1046033730().playerController;
         Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.playerController");
         if (var10000.isNotCreative()) {
            llllllllllllllllllIIIlIlIIIIIlII.chat("§c§lError: §3You need to be in creative mode.");
         } else {
            try {
               float llllllllllllllllllIIIlIlIIIIIIIl = llllllllllllllllllIIIlIlIIIIIlIl[1];
               Exception llllllllllllllllllIIIlIlIIIIIIII = false;
               double llllllllllllllllllIIIlIlIIIIlIII = Double.parseDouble(llllllllllllllllllIIIlIlIIIIIIIl);
               Exception llllllllllllllllllIIIlIIllllllll = llllllllllllllllllIIIlIlIIIIIlIl[2];
               Exception llllllllllllllllllIIIlIIlllllllI = false;
               float llllllllllllllllllIIIlIlIIIIIIIl = Double.parseDouble(llllllllllllllllllIIIlIIllllllll);
               short llllllllllllllllllIIIlIIllllllIl = llllllllllllllllllIIIlIlIIIIIlIl[3];
               Exception llllllllllllllllllIIIlIIllllllII = false;
               double llllllllllllllllllIIIlIlIIIIlIlI = Double.parseDouble(llllllllllllllllllIIIlIIllllllIl);
               llllllllllllllllllIIIlIIllllllIl = StringUtils.toCompleteString(llllllllllllllllllIIIlIlIIIIIlIl, 4);
               Exception llllllllllllllllllIIIlIIllllllII = new ItemStack((Item)Items.armor_stand);
               NBTTagCompound llllllllllllllllllIIIlIlIIIIllIl = new NBTTagCompound();
               NBTTagCompound llllllllllllllllllIIIlIlIIIIlllI = new NBTTagCompound();
               llllllllllllllllllIIIlIlIIIIlllI.setInteger("Invisible", 1);
               llllllllllllllllllIIIlIlIIIIlllI.setString("CustomName", llllllllllllllllllIIIlIIllllllIl);
               llllllllllllllllllIIIlIlIIIIlllI.setInteger("CustomNameVisible", 1);
               llllllllllllllllllIIIlIlIIIIlllI.setInteger("NoGravity", 1);
               NBTTagList llllllllllllllllllIIIlIlIIIIllll = new NBTTagList();
               llllllllllllllllllIIIlIlIIIIllll.appendTag((NBTBase)(new NBTTagDouble(llllllllllllllllllIIIlIlIIIIlIII)));
               llllllllllllllllllIIIlIlIIIIllll.appendTag((NBTBase)(new NBTTagDouble(llllllllllllllllllIIIlIlIIIIIIIl)));
               llllllllllllllllllIIIlIlIIIIllll.appendTag((NBTBase)(new NBTTagDouble(llllllllllllllllllIIIlIlIIIIlIlI)));
               llllllllllllllllllIIIlIlIIIIlllI.setTag("Pos", (NBTBase)llllllllllllllllllIIIlIlIIIIllll);
               llllllllllllllllllIIIlIlIIIIllIl.setTag("EntityTag", (NBTBase)llllllllllllllllllIIIlIlIIIIlllI);
               llllllllllllllllllIIIlIIllllllII.setTagCompound(llllllllllllllllllIIIlIlIIIIllIl);
               llllllllllllllllllIIIlIIllllllII.setStackDisplayName("§c§lHolo§eStand");
               boolean var10001 = false;
               Minecraft var17 = access$getMc$p$s1046033730();
               Intrinsics.checkExpressionValueIsNotNull(var17, "mc");
               var17.getNetHandler().addToSendQueue((Packet)(new C10PacketCreativeInventoryAction(36, llllllllllllllllllIIIlIIllllllII)));
               llllllllllllllllllIIIlIlIIIIIlII.chat("The HoloStand was successfully added to your inventory.");
            } catch (NumberFormatException var13) {
               llllllllllllllllllIIIlIlIIIIIlII.chatSyntaxError();
            }

         }
      } else {
         llllllllllllllllllIIIlIlIIIIIlII.chatSyntax("holostand <x> <y> <z> <message...>");
      }
   }

   public HoloStandCommand() {
      boolean llllllllllllllllllIIIlIIllllIIIl = "holostand";
      Exception llllllllllllllllllIIIlIIllllIIII = new String[0];
      super(llllllllllllllllllIIIlIIllllIIIl, llllllllllllllllllIIIlIIllllIIII);
   }
}
