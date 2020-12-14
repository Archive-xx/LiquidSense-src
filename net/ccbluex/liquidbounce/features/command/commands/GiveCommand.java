//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.command.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.item.ItemUtils;
import net.ccbluex.liquidbounce.utils.misc.StringUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;
import net.minecraft.util.RegistryNamespaced;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\bJ!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\u000b¨\u0006\f"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/GiveCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "LiquidSense"}
)
public final class GiveCommand extends Command {
   public void execute(@NotNull String[] llllllllllllllllllllIIlllIIIIlIl) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllllIIlllIIIIlIl, "args");
      PlayerControllerMP var10000 = access$getMc$p$s1046033730().playerController;
      Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.playerController");
      if (var10000.isNotCreative()) {
         llllllllllllllllllllIIlllIIIIllI.chat("§c§lError: §3You need to be in creative mode.");
      } else if (llllllllllllllllllllIIlllIIIIlIl.length <= 1) {
         llllllllllllllllllllIIlllIIIIllI.chatSyntax("give <item> [amount] [data] [datatag]");
      } else {
         ItemStack llllllllllllllllllllIIlllIIIlIIl = ItemUtils.createItem(StringUtils.toCompleteString(llllllllllllllllllllIIlllIIIIlIl, 1));
         if (llllllllllllllllllllIIlllIIIlIIl == null) {
            llllllllllllllllllllIIlllIIIIllI.chatSyntaxError();
         } else {
            int llllllllllllllllllllIIlllIIIlIlI = -1;
            double llllllllllllllllllllIIlllIIIIIII = 36;

            byte llllllllllllllllllllIIllIllllllI;
            Slot var6;
            for(llllllllllllllllllllIIllIllllllI = 44; llllllllllllllllllllIIlllIIIIIII <= llllllllllllllllllllIIllIllllllI; ++llllllllllllllllllllIIlllIIIIIII) {
               var6 = access$getMc$p$s1046033730().thePlayer.inventoryContainer.getSlot(llllllllllllllllllllIIlllIIIIIII);
               Intrinsics.checkExpressionValueIsNotNull(var6, "mc.thePlayer.inventoryContainer.getSlot(i)");
               if (var6.getStack() == null) {
                  llllllllllllllllllllIIlllIIIlIlI = llllllllllllllllllllIIlllIIIIIII;
                  break;
               }
            }

            if (llllllllllllllllllllIIlllIIIlIlI == -1) {
               llllllllllllllllllllIIlllIIIIIII = 9;

               for(llllllllllllllllllllIIllIllllllI = 44; llllllllllllllllllllIIlllIIIIIII <= llllllllllllllllllllIIllIllllllI; ++llllllllllllllllllllIIlllIIIIIII) {
                  var6 = access$getMc$p$s1046033730().thePlayer.inventoryContainer.getSlot(llllllllllllllllllllIIlllIIIIIII);
                  Intrinsics.checkExpressionValueIsNotNull(var6, "mc.thePlayer.inventoryContainer.getSlot(i)");
                  if (var6.getStack() == null) {
                     llllllllllllllllllllIIlllIIIlIlI = llllllllllllllllllllIIlllIIIIIII;
                     break;
                  }
               }
            }

            if (llllllllllllllllllllIIlllIIIlIlI != -1) {
               Minecraft var7 = access$getMc$p$s1046033730();
               Intrinsics.checkExpressionValueIsNotNull(var7, "mc");
               var7.getNetHandler().addToSendQueue((Packet)(new C10PacketCreativeInventoryAction(llllllllllllllllllllIIlllIIIlIlI, llllllllllllllllllllIIlllIIIlIIl)));
               StringBuilder var10001 = (new StringBuilder()).append("§7Given [§8").append(llllllllllllllllllllIIlllIIIlIIl.getDisplayName()).append("§7] * §8").append(llllllllllllllllllllIIlllIIIlIIl.stackSize).append("§7 to §8");
               Session var10002 = access$getMc$p$s1046033730().getSession();
               Intrinsics.checkExpressionValueIsNotNull(var10002, "mc.getSession()");
               llllllllllllllllllllIIlllIIIIllI.chat(String.valueOf(var10001.append(var10002.getUsername()).append("§7.")));
            } else {
               llllllllllllllllllllIIlllIIIIllI.chat("Your inventory is full.");
            }

         }
      }
   }

   public GiveCommand() {
      super("give", new String[]{"item", "i", "get"});
   }

   @NotNull
   public List<String> tabComplete(@NotNull String[] llllllllllllllllllllIIllIIlllIlI) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllllIIllIIlllIlI, "args");
      boolean llllllllllllllllllllIIllIIllllIl = false;
      if (llllllllllllllllllllIIllIIlllIlI.length == 0) {
         return CollectionsKt.emptyList();
      } else {
         switch(llllllllllllllllllllIIllIIlllIlI.length) {
         case 1:
            RegistryNamespaced var10000 = Item.itemRegistry;
            Intrinsics.checkExpressionValueIsNotNull(var10000, "Item.itemRegistry");
            Set var16 = var10000.getKeys();
            Intrinsics.checkExpressionValueIsNotNull(var16, "Item.itemRegistry.keys");
            Iterable llllllllllllllllllllIIllIIlllIIl = (Iterable)var16;
            llllllllllllllllllllIIllIIllllIl = false;
            float llllllllllllllllllllIIllIIllIllI = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault(llllllllllllllllllllIIllIIlllIIl, 10)));
            int llllllllllllllllllllIIllIIllllll = false;

            Iterator llllllllllllllllllllIIllIIllIlII;
            Object llllllllllllllllllllIIllIlIIIIlI;
            boolean llllllllllllllllllllIIllIIllIIIl;
            boolean var10001;
            for(llllllllllllllllllllIIllIIllIlII = llllllllllllllllllllIIllIIlllIIl.iterator(); llllllllllllllllllllIIllIIllIlII.hasNext(); var10001 = false) {
               llllllllllllllllllllIIllIlIIIIlI = llllllllllllllllllllIIllIIllIlII.next();
               Exception llllllllllllllllllllIIllIIllIIlI = (ResourceLocation)llllllllllllllllllllIIllIlIIIIlI;
               llllllllllllllllllllIIllIIllIIIl = false;
               Intrinsics.checkExpressionValueIsNotNull(llllllllllllllllllllIIllIIllIIlI, "it");
               String var17 = llllllllllllllllllllIIllIIllIIlI.getResourcePath();
               Intrinsics.checkExpressionValueIsNotNull(var17, "it.resourcePath");
               int llllllllllllllllllllIIllIIllIIII = var17;
               float llllllllllllllllllllIIllIIlIllll = false;
               if (llllllllllllllllllllIIllIIllIIII == null) {
                  throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
               }

               var17 = llllllllllllllllllllIIllIIllIIII.toLowerCase();
               Intrinsics.checkExpressionValueIsNotNull(var17, "(this as java.lang.String).toLowerCase()");
               char llllllllllllllllllllIIllIIlIllIl = var17;
               llllllllllllllllllllIIllIIllIllI.add(llllllllllllllllllllIIllIIlIllIl);
            }

            llllllllllllllllllllIIllIIlllIIl = (Iterable)((List)llllllllllllllllllllIIllIIllIllI);
            llllllllllllllllllllIIllIIllllIl = false;
            llllllllllllllllllllIIllIIllIllI = (Collection)(new ArrayList());
            llllllllllllllllllllIIllIIllllll = false;
            llllllllllllllllllllIIllIIllIlII = llllllllllllllllllllIIllIIlllIIl.iterator();

            while(llllllllllllllllllllIIllIIllIlII.hasNext()) {
               llllllllllllllllllllIIllIlIIIIlI = llllllllllllllllllllIIllIIllIlII.next();
               String llllllllllllllllllllIIllIlIIIlII = (String)llllllllllllllllllllIIllIlIIIIlI;
               llllllllllllllllllllIIllIIllIIIl = false;
               if (StringsKt.startsWith(llllllllllllllllllllIIllIlIIIlII, llllllllllllllllllllIIllIIlllIlI[0], true)) {
                  llllllllllllllllllllIIllIIllIllI.add(llllllllllllllllllllIIllIlIIIIlI);
                  var10001 = false;
               }
            }

            return (List)llllllllllllllllllllIIllIIllIllI;
         default:
            return CollectionsKt.emptyList();
         }
      }
   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }
}
