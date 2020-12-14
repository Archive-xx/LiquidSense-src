//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.command.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\bJ!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\u000b¨\u0006\f"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/RemoteViewCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "LiquidSense"}
)
public final class RemoteViewCommand extends Command {
   public RemoteViewCommand() {
      super("remoteview", new String[]{"rv"});
   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   public void execute(@NotNull String[] llllllllllllllllllIlIlllIlIlIIII) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIlIlllIlIlIIII, "args");
      Minecraft var10000;
      if (llllllllllllllllllIlIlllIlIlIIII.length < 2) {
         var10000 = access$getMc$p$s1046033730();
         Intrinsics.checkExpressionValueIsNotNull(var10000, "mc");
         if (Intrinsics.areEqual((Object)var10000.getRenderViewEntity(), (Object)access$getMc$p$s1046033730().thePlayer) ^ true) {
            var10000 = access$getMc$p$s1046033730();
            Intrinsics.checkExpressionValueIsNotNull(var10000, "mc");
            var10000.setRenderViewEntity((Entity)access$getMc$p$s1046033730().thePlayer);
         } else {
            llllllllllllllllllIlIlllIlIlIllI.chatSyntax("remoteview <username>");
         }
      } else {
         String llllllllllllllllllIlIlllIlIlIlll = llllllllllllllllllIlIlllIlIlIIII[1];
         Iterator llllllllllllllllllIlIlllIlIIlIlI = access$getMc$p$s1046033730().theWorld.loadedEntityList.iterator();

         while(llllllllllllllllllIlIlllIlIIlIlI.hasNext()) {
            float llllllllllllllllllIlIlllIlIIllII = (Entity)llllllllllllllllllIlIlllIlIIlIlI.next();
            Intrinsics.checkExpressionValueIsNotNull(llllllllllllllllllIlIlllIlIIllII, "entity");
            if (Intrinsics.areEqual((Object)llllllllllllllllllIlIlllIlIlIlll, (Object)llllllllllllllllllIlIlllIlIIllII.getName())) {
               var10000 = access$getMc$p$s1046033730();
               Intrinsics.checkExpressionValueIsNotNull(var10000, "mc");
               var10000.setRenderViewEntity(llllllllllllllllllIlIlllIlIIllII);
               llllllllllllllllllIlIlllIlIlIllI.chat(String.valueOf((new StringBuilder()).append("Now viewing perspective of §8").append(llllllllllllllllllIlIlllIlIIllII.getName()).append("§3.")));
               llllllllllllllllllIlIlllIlIlIllI.chat(String.valueOf((new StringBuilder()).append("Execute §8").append(LiquidBounce.INSTANCE.getCommandManager().getPrefix()).append("remoteview §3again to go back to yours.")));
               break;
            }
         }

      }
   }

   @NotNull
   public List<String> tabComplete(@NotNull String[] llllllllllllllllllIlIllIllllllII) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIlIllIllllllII, "args");
      byte llllllllllllllllllIlIlllIIIIIIIl = false;
      if (llllllllllllllllllIlIllIllllllII.length == 0) {
         return CollectionsKt.emptyList();
      } else {
         switch(llllllllllllllllllIlIllIllllllII.length) {
         case 1:
            List var10000 = access$getMc$p$s1046033730().theWorld.playerEntities;
            Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.theWorld.playerEntities");
            Iterable llllllllllllllllllIlIlllIIIIIIlI = (Iterable)var10000;
            llllllllllllllllllIlIlllIIIIIIIl = false;
            Collection llllllllllllllllllIlIlllIIIIIllI = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault(llllllllllllllllllIlIlllIIIIIIlI, 10)));
            int llllllllllllllllllIlIllIlllIllII = false;

            Iterator llllllllllllllllllIlIllIlllIlIIl;
            Object llllllllllllllllllIlIllIlllIIllI;
            boolean llllllllllllllllllIlIllIlllIIIII;
            boolean var10001;
            for(llllllllllllllllllIlIllIlllIlIIl = llllllllllllllllllIlIlllIIIIIIlI.iterator(); llllllllllllllllllIlIllIlllIlIIl.hasNext(); var10001 = false) {
               llllllllllllllllllIlIllIlllIIllI = llllllllllllllllllIlIllIlllIlIIl.next();
               EntityPlayer llllllllllllllllllIlIlllIIIlIlll = (EntityPlayer)llllllllllllllllllIlIllIlllIIllI;
               llllllllllllllllllIlIllIlllIIIII = false;
               Intrinsics.checkExpressionValueIsNotNull(llllllllllllllllllIlIlllIIIlIlll, "it");
               double llllllllllllllllllIlIllIllIlllII = llllllllllllllllllIlIlllIIIlIlll.getName();
               llllllllllllllllllIlIlllIIIIIllI.add(llllllllllllllllllIlIllIllIlllII);
            }

            llllllllllllllllllIlIlllIIIIIIlI = (Iterable)((List)llllllllllllllllllIlIlllIIIIIllI);
            llllllllllllllllllIlIlllIIIIIIIl = false;
            llllllllllllllllllIlIlllIIIIIllI = (Collection)(new ArrayList());
            llllllllllllllllllIlIllIlllIllII = false;
            llllllllllllllllllIlIllIlllIlIIl = llllllllllllllllllIlIlllIIIIIIlI.iterator();

            while(llllllllllllllllllIlIllIlllIlIIl.hasNext()) {
               llllllllllllllllllIlIllIlllIIllI = llllllllllllllllllIlIllIlllIlIIl.next();
               String llllllllllllllllllIlIlllIIIIllIl = (String)llllllllllllllllllIlIllIlllIIllI;
               llllllllllllllllllIlIllIlllIIIII = false;
               Intrinsics.checkExpressionValueIsNotNull(llllllllllllllllllIlIlllIIIIllIl, "it");
               if (StringsKt.startsWith(llllllllllllllllllIlIlllIIIIllIl, llllllllllllllllllIlIllIllllllII[0], true)) {
                  llllllllllllllllllIlIlllIIIIIllI.add(llllllllllllllllllIlIllIlllIIllI);
                  var10001 = false;
               }
            }

            return (List)llllllllllllllllllIlIlllIIIIIllI;
         default:
            return CollectionsKt.emptyList();
         }
      }
   }
}
