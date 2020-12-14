//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.command.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.file.FileConfig;
import net.ccbluex.liquidbounce.file.configs.FriendsConfig;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.StringUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\bJ!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\u000b¨\u0006\f"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/FriendCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "LiquidSense"}
)
public final class FriendCommand extends Command {
   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   @NotNull
   public List<String> tabComplete(@NotNull String[] lllllllllllllllllIllIlIIIllIIlII) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllIllIlIIIllIIlII, "args");
      int lllllllllllllllllIllIlIIIllIIIII = false;
      if (lllllllllllllllllIllIlIIIllIIlII.length == 0) {
         return CollectionsKt.emptyList();
      } else {
         boolean var10001;
         List var23;
         switch(lllllllllllllllllIllIlIIIllIIlII.length) {
         case 1:
            Iterable lllllllllllllllllIllIlIIlIIlIIlI = (Iterable)CollectionsKt.listOf(new String[]{"add", "remove", "list", "clear"});
            lllllllllllllllllIllIlIIIllIIIII = false;
            byte lllllllllllllllllIllIlIIIlIlllIl = (Collection)(new ArrayList());
            int lllllllllllllllllIllIlIIlIIlIIll = false;
            Iterator lllllllllllllllllIllIlIIIlIllIlI = lllllllllllllllllIllIlIIlIIlIIlI.iterator();

            while(lllllllllllllllllIllIlIIIlIllIlI.hasNext()) {
               float lllllllllllllllllIllIlIIIlIllIIl = lllllllllllllllllIllIlIIIlIllIlI.next();
               String lllllllllllllllllIllIlIIlIIllIII = (String)lllllllllllllllllIllIlIIIlIllIIl;
               int lllllllllllllllllIllIlIIlIIlIlll = false;
               if (StringsKt.startsWith(lllllllllllllllllIllIlIIlIIllIII, lllllllllllllllllIllIlIIIllIIlII[0], true)) {
                  lllllllllllllllllIllIlIIIlIlllIl.add(lllllllllllllllllIllIlIIIlIllIIl);
                  var10001 = false;
               }
            }

            var23 = (List)lllllllllllllllllIllIlIIIlIlllIl;
            break;
         case 2:
            float lllllllllllllllllIllIlIIIllIIIIl = lllllllllllllllllIllIlIIIllIIlII[0];
            lllllllllllllllllIllIlIIIllIIIII = false;
            if (lllllllllllllllllIllIlIIIllIIIIl == null) {
               throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }

            String var10000 = lllllllllllllllllIllIlIIIllIIIIl.toLowerCase();
            Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).toLowerCase()");
            lllllllllllllllllIllIlIIIllIIIIl = var10000;
            boolean lllllllllllllllllIllIlIIlIIIIIII;
            Collection lllllllllllllllllIllIlIIIlIllIll;
            boolean lllllllllllllllllIllIlIIIlIllIlI;
            Iterator lllllllllllllllllIllIlIIIlIllIIl;
            Object lllllllllllllllllIllIlIIlIIIIllI;
            boolean lllllllllllllllllIllIlIIIlIlIllI;
            String lllllllllllllllllIllIlIIIlIlIlII;
            Iterable lllllllllllllllllIllIlIIlIIIIIIl;
            String lllllllllllllllllIllIlIIIlIlIlll;
            switch(lllllllllllllllllIllIlIIIllIIIIl.hashCode()) {
            case -934610812:
               if (lllllllllllllllllIllIlIIIllIIIIl.equals("remove")) {
                  FriendsConfig var24 = LiquidBounce.INSTANCE.getFileManager().friendsConfig;
                  Intrinsics.checkExpressionValueIsNotNull(var24, "LiquidBounce.fileManager.friendsConfig");
                  var23 = var24.getFriends();
                  Intrinsics.checkExpressionValueIsNotNull(var23, "LiquidBounce.fileManager.friendsConfig.friends");
                  lllllllllllllllllIllIlIIlIIIIIIl = (Iterable)var23;
                  lllllllllllllllllIllIlIIlIIIIIII = false;
                  lllllllllllllllllIllIlIIIlIllIll = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault(lllllllllllllllllIllIlIIlIIIIIIl, 10)));
                  lllllllllllllllllIllIlIIIlIllIlI = false;

                  for(lllllllllllllllllIllIlIIIlIllIIl = lllllllllllllllllIllIlIIlIIIIIIl.iterator(); lllllllllllllllllIllIlIIIlIllIIl.hasNext(); var10001 = false) {
                     lllllllllllllllllIllIlIIlIIIIllI = lllllllllllllllllIllIlIIIlIllIIl.next();
                     float lllllllllllllllllIllIlIIIlIlIlll = (FriendsConfig.Friend)lllllllllllllllllIllIlIIlIIIIllI;
                     lllllllllllllllllIllIlIIIlIlIllI = false;
                     Intrinsics.checkExpressionValueIsNotNull(lllllllllllllllllIllIlIIIlIlIlll, "it");
                     lllllllllllllllllIllIlIIIlIlIlII = lllllllllllllllllIllIlIIIlIlIlll.getPlayerName();
                     lllllllllllllllllIllIlIIIlIllIll.add(lllllllllllllllllIllIlIIIlIlIlII);
                  }

                  lllllllllllllllllIllIlIIlIIIIIIl = (Iterable)((List)lllllllllllllllllIllIlIIIlIllIll);
                  lllllllllllllllllIllIlIIlIIIIIII = false;
                  lllllllllllllllllIllIlIIIlIllIll = (Collection)(new ArrayList());
                  lllllllllllllllllIllIlIIIlIllIlI = false;
                  lllllllllllllllllIllIlIIIlIllIIl = lllllllllllllllllIllIlIIlIIIIIIl.iterator();

                  while(lllllllllllllllllIllIlIIIlIllIIl.hasNext()) {
                     lllllllllllllllllIllIlIIlIIIIllI = lllllllllllllllllIllIlIIIlIllIIl.next();
                     lllllllllllllllllIllIlIIIlIlIlll = (String)lllllllllllllllllIllIlIIlIIIIllI;
                     lllllllllllllllllIllIlIIIlIlIllI = false;
                     Intrinsics.checkExpressionValueIsNotNull(lllllllllllllllllIllIlIIIlIlIlll, "it");
                     if (StringsKt.startsWith(lllllllllllllllllIllIlIIIlIlIlll, lllllllllllllllllIllIlIIIllIIlII[1], true)) {
                        lllllllllllllllllIllIlIIIlIllIll.add(lllllllllllllllllIllIlIIlIIIIllI);
                        var10001 = false;
                     }
                  }

                  return (List)lllllllllllllllllIllIlIIIlIllIll;
               }
               break;
            case 96417:
               if (lllllllllllllllllIllIlIIIllIIIIl.equals("add")) {
                  var23 = access$getMc$p$s1046033730().theWorld.playerEntities;
                  Intrinsics.checkExpressionValueIsNotNull(var23, "mc.theWorld.playerEntities");
                  lllllllllllllllllIllIlIIlIIIIIIl = (Iterable)var23;
                  lllllllllllllllllIllIlIIlIIIIIII = false;
                  lllllllllllllllllIllIlIIIlIllIll = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault(lllllllllllllllllIllIlIIlIIIIIIl, 10)));
                  lllllllllllllllllIllIlIIIlIllIlI = false;

                  for(lllllllllllllllllIllIlIIIlIllIIl = lllllllllllllllllIllIlIIlIIIIIIl.iterator(); lllllllllllllllllIllIlIIIlIllIIl.hasNext(); var10001 = false) {
                     lllllllllllllllllIllIlIIlIIIIllI = lllllllllllllllllIllIlIIIlIllIIl.next();
                     EntityPlayer lllllllllllllllllIllIlIIlIIlIIII = (EntityPlayer)lllllllllllllllllIllIlIIlIIIIllI;
                     lllllllllllllllllIllIlIIIlIlIllI = false;
                     Intrinsics.checkExpressionValueIsNotNull(lllllllllllllllllIllIlIIlIIlIIII, "it");
                     lllllllllllllllllIllIlIIIlIlIlII = lllllllllllllllllIllIlIIlIIlIIII.getName();
                     lllllllllllllllllIllIlIIIlIllIll.add(lllllllllllllllllIllIlIIIlIlIlII);
                  }

                  lllllllllllllllllIllIlIIlIIIIIIl = (Iterable)((List)lllllllllllllllllIllIlIIIlIllIll);
                  lllllllllllllllllIllIlIIlIIIIIII = false;
                  lllllllllllllllllIllIlIIIlIllIll = (Collection)(new ArrayList());
                  lllllllllllllllllIllIlIIIlIllIlI = false;
                  lllllllllllllllllIllIlIIIlIllIIl = lllllllllllllllllIllIlIIlIIIIIIl.iterator();

                  while(lllllllllllllllllIllIlIIIlIllIIl.hasNext()) {
                     lllllllllllllllllIllIlIIlIIIIllI = lllllllllllllllllIllIlIIIlIllIIl.next();
                     lllllllllllllllllIllIlIIIlIlIlll = (String)lllllllllllllllllIllIlIIlIIIIllI;
                     lllllllllllllllllIllIlIIIlIlIllI = false;
                     Intrinsics.checkExpressionValueIsNotNull(lllllllllllllllllIllIlIIIlIlIlll, "it");
                     if (StringsKt.startsWith(lllllllllllllllllIllIlIIIlIlIlll, lllllllllllllllllIllIlIIIllIIlII[1], true)) {
                        lllllllllllllllllIllIlIIIlIllIll.add(lllllllllllllllllIllIlIIlIIIIllI);
                        var10001 = false;
                     }
                  }

                  return (List)lllllllllllllllllIllIlIIIlIllIll;
               }
            }

            return CollectionsKt.emptyList();
         default:
            var23 = CollectionsKt.emptyList();
         }

         return var23;
      }
   }

   public FriendCommand() {
      super("friend", new String[]{"friends"});
   }

   public void execute(@NotNull String[] lllllllllllllllllIllIlIIlIlIlIlI) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllIllIlIIlIlIlIlI, "args");
      if (lllllllllllllllllIllIlIIlIlIlIlI.length > 1) {
         FriendsConfig lllllllllllllllllIllIlIIlIlIlllI = LiquidBounce.INSTANCE.getFileManager().friendsConfig;
         String lllllllllllllllllIllIlIIlIlIlIII;
         if (StringsKt.equals(lllllllllllllllllIllIlIIlIlIlIlI[1], "add", true)) {
            if (lllllllllllllllllIllIlIIlIlIlIlI.length > 2) {
               lllllllllllllllllIllIlIIlIlIlIII = lllllllllllllllllIllIlIIlIlIlIlI[2];
               float lllllllllllllllllIllIlIIlIlIIlll = (CharSequence)lllllllllllllllllIllIlIIlIlIlIII;
               Exception lllllllllllllllllIllIlIIlIlIIllI = false;
               if (lllllllllllllllllIllIlIIlIlIIlll.length() == 0) {
                  lllllllllllllllllIllIlIIlIlIlIll.chat("The name is empty.");
                  return;
               }

               if (lllllllllllllllllIllIlIIlIlIlIlI.length > 3 ? lllllllllllllllllIllIlIIlIlIlllI.addFriend(lllllllllllllllllIllIlIIlIlIlIII, StringUtils.toCompleteString(lllllllllllllllllIllIlIIlIlIlIlI, 3)) : lllllllllllllllllIllIlIIlIlIlllI.addFriend(lllllllllllllllllIllIlIIlIlIlIII)) {
                  LiquidBounce.INSTANCE.getFileManager().saveConfig((FileConfig)lllllllllllllllllIllIlIIlIlIlllI);
                  lllllllllllllllllIllIlIIlIlIlIll.chat(String.valueOf((new StringBuilder()).append("§a§l").append(lllllllllllllllllIllIlIIlIlIlIII).append("§3 was added to your friend list.")));
                  lllllllllllllllllIllIlIIlIlIlIll.playEdit();
               } else {
                  lllllllllllllllllIllIlIIlIlIlIll.chat("The name is already in the list.");
               }

               return;
            }

            lllllllllllllllllIllIlIIlIlIlIll.chatSyntax("friend add <name> [alias]");
            return;
         }

         if (StringsKt.equals(lllllllllllllllllIllIlIIlIlIlIlI[1], "remove", true)) {
            if (lllllllllllllllllIllIlIIlIlIlIlI.length > 2) {
               lllllllllllllllllIllIlIIlIlIlIII = lllllllllllllllllIllIlIIlIlIlIlI[2];
               if (lllllllllllllllllIllIlIIlIlIlllI.removeFriend(lllllllllllllllllIllIlIIlIlIlIII)) {
                  LiquidBounce.INSTANCE.getFileManager().saveConfig((FileConfig)lllllllllllllllllIllIlIIlIlIlllI);
                  lllllllllllllllllIllIlIIlIlIlIll.chat(String.valueOf((new StringBuilder()).append("§a§l").append(lllllllllllllllllIllIlIIlIlIlIII).append("§3 was removed from your friend list.")));
                  lllllllllllllllllIllIlIIlIlIlIll.playEdit();
               } else {
                  lllllllllllllllllIllIlIIlIlIlIll.chat("This name is not in the list.");
               }

               return;
            }

            lllllllllllllllllIllIlIIlIlIlIll.chatSyntax("friend remove <name>");
            return;
         }

         if (StringsKt.equals(lllllllllllllllllIllIlIIlIlIlIlI[1], "clear", true)) {
            Intrinsics.checkExpressionValueIsNotNull(lllllllllllllllllIllIlIIlIlIlllI, "friendsConfig");
            int lllllllllllllllllIllIlIIlIllIIII = lllllllllllllllllIllIlIIlIlIlllI.getFriends().size();
            lllllllllllllllllIllIlIIlIlIlllI.clearFriends();
            LiquidBounce.INSTANCE.getFileManager().saveConfig((FileConfig)lllllllllllllllllIllIlIIlIlIlllI);
            lllllllllllllllllIllIlIIlIlIlIll.chat(String.valueOf((new StringBuilder()).append("Removed ").append(lllllllllllllllllIllIlIIlIllIIII).append(" friend(s).")));
            return;
         }

         if (StringsKt.equals(lllllllllllllllllIllIlIIlIlIlIlI[1], "list", true)) {
            lllllllllllllllllIllIlIIlIlIlIll.chat("Your Friends:");
            Intrinsics.checkExpressionValueIsNotNull(lllllllllllllllllIllIlIIlIlIlllI, "friendsConfig");
            Iterator lllllllllllllllllIllIlIIlIlIIlll = lllllllllllllllllIllIlIIlIlIlllI.getFriends().iterator();

            while(lllllllllllllllllIllIlIIlIlIIlll.hasNext()) {
               FriendsConfig.Friend lllllllllllllllllIllIlIIlIlIllll = (FriendsConfig.Friend)lllllllllllllllllIllIlIIlIlIIlll.next();
               StringBuilder var10001 = (new StringBuilder()).append("§7> §a§l");
               Intrinsics.checkExpressionValueIsNotNull(lllllllllllllllllIllIlIIlIlIllll, "friend");
               lllllllllllllllllIllIlIIlIlIlIll.chat(String.valueOf(var10001.append(lllllllllllllllllIllIlIIlIlIllll.getPlayerName()).append(" §c(§7§l").append(lllllllllllllllllIllIlIIlIlIllll.getAlias()).append("§c)")));
            }

            lllllllllllllllllIllIlIIlIlIlIll.chat(String.valueOf((new StringBuilder()).append("You have §c").append(lllllllllllllllllIllIlIIlIlIlllI.getFriends().size()).append("§3 friends.")));
            return;
         }
      }

      lllllllllllllllllIllIlIIlIlIlIll.chatSyntax("friend <add/remove/list/clear>");
   }
}
