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
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\bJ!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\u000b¨\u0006\f"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/EnchantCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "LiquidSense"}
)
public final class EnchantCommand extends Command {
   public void execute(@NotNull String[] lIIIIIlIllllIIl) {
      Intrinsics.checkParameterIsNotNull(lIIIIIlIllllIIl, "args");
      if (lIIIIIlIllllIIl.length > 2) {
         PlayerControllerMP var10000 = access$getMc$p$s1046033730().playerController;
         Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.playerController");
         if (var10000.isNotCreative()) {
            lIIIIIlIlllllII.chat("§c§lError: §3You need to be in creative mode.");
         } else {
            EntityPlayerSP var14 = access$getMc$p$s1046033730().thePlayer;
            Intrinsics.checkExpressionValueIsNotNull(var14, "mc.thePlayer");
            ItemStack lIIIIIlIlllllIl = var14.getHeldItem();
            if (lIIIIIlIlllllIl != null && lIIIIIlIlllllIl.getItem() != null) {
               int lIIIIIlIlllIllI;
               try {
                  char lIIIIIlIlllIllI = lIIIIIlIllllIIl[1];
                  float lIIIIIlIlllIlIl = false;
                  lIIIIIlIlllIllI = Integer.parseInt(lIIIIIlIlllIllI);
               } catch (NumberFormatException var9) {
                  Enchantment lIIIIIllIIIIIll = Enchantment.getEnchantmentByLocation(lIIIIIlIllllIIl[1]);
                  if (lIIIIIllIIIIIll == null) {
                     lIIIIIlIlllllII.chat(String.valueOf((new StringBuilder()).append("There is no enchantment with the name '").append(lIIIIIlIllllIIl[1]).append('\'')));
                     return;
                  }

                  lIIIIIlIlllIllI = lIIIIIllIIIIIll.effectId;
               }

               int lIIIIIlIllllllI = lIIIIIlIlllIllI;
               char lIIIIIlIlllIllI = Enchantment.getEnchantmentById(lIIIIIlIlllIllI);
               if (lIIIIIlIlllIllI == null) {
                  lIIIIIlIlllllII.chat(String.valueOf((new StringBuilder()).append("There is no enchantment with the ID '").append(lIIIIIlIllllllI).append('\'')));
               } else {
                  int lIIIIIlIlllIlII;
                  try {
                     String lIIIIIlIlllIlII = lIIIIIlIllllIIl[2];
                     String lIIIIIlIlllIIll = false;
                     lIIIIIlIlllIlII = Integer.parseInt(lIIIIIlIlllIlII);
                  } catch (NumberFormatException var8) {
                     lIIIIIlIlllllII.chatSyntaxError();
                     return;
                  }

                  lIIIIIlIlllllIl.addEnchantment(lIIIIIlIlllIllI, lIIIIIlIlllIlII);
                  lIIIIIlIlllllII.chat(String.valueOf((new StringBuilder()).append(lIIIIIlIlllIllI.getTranslatedName(lIIIIIlIlllIlII)).append(" added to ").append(lIIIIIlIlllllIl.getDisplayName()).append('.')));
               }
            } else {
               lIIIIIlIlllllII.chat("§c§lError: §3You need to hold an item.");
            }
         }
      } else {
         lIIIIIlIlllllII.chatSyntax("enchant <type> [level]");
      }
   }

   public EnchantCommand() {
      int lIIIIIlIIllllIl = "enchant";
      String lIIIIIlIIllllII = new String[0];
      super(lIIIIIlIIllllIl, lIIIIIlIIllllII);
   }

   @NotNull
   public List<String> tabComplete(@NotNull String[] lIIIIIlIlIlIIlI) {
      Intrinsics.checkParameterIsNotNull(lIIIIIlIlIlIIlI, "args");
      float lIIIIIlIlIlIIII = false;
      if (lIIIIIlIlIlIIlI.length == 0) {
         return CollectionsKt.emptyList();
      } else {
         switch(lIIIIIlIlIlIIlI.length) {
         case 1:
            Set var10000 = Enchantment.func_181077_c();
            Intrinsics.checkExpressionValueIsNotNull(var10000, "Enchantment.func_181077_c()");
            Exception lIIIIIlIlIlIIIl = (Iterable)var10000;
            lIIIIIlIlIlIIII = false;
            Collection lIIIIIlIlIIlllI = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault(lIIIIIlIlIlIIIl, 10)));
            int lIIIIIlIlIlIlll = false;

            Iterator lIIIIIlIlIIllII;
            Object lIIIIIlIlIllIlI;
            boolean lIIIIIlIlIllIll;
            boolean var10001;
            for(lIIIIIlIlIIllII = lIIIIIlIlIlIIIl.iterator(); lIIIIIlIlIIllII.hasNext(); var10001 = false) {
               lIIIIIlIlIllIlI = lIIIIIlIlIIllII.next();
               long lIIIIIlIlIIlIlI = (ResourceLocation)lIIIIIlIlIllIlI;
               lIIIIIlIlIllIll = false;
               Intrinsics.checkExpressionValueIsNotNull(lIIIIIlIlIIlIlI, "it");
               String var16 = lIIIIIlIlIIlIlI.getResourcePath();
               Intrinsics.checkExpressionValueIsNotNull(var16, "it.resourcePath");
               long lIIIIIlIlIIlIII = var16;
               float lIIIIIlIlIIIlll = false;
               if (lIIIIIlIlIIlIII == null) {
                  throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
               }

               var16 = lIIIIIlIlIIlIII.toLowerCase();
               Intrinsics.checkExpressionValueIsNotNull(var16, "(this as java.lang.String).toLowerCase()");
               Exception lIIIIIlIlIIIlIl = var16;
               lIIIIIlIlIIlllI.add(lIIIIIlIlIIIlIl);
            }

            lIIIIIlIlIlIIIl = (Iterable)((List)lIIIIIlIlIIlllI);
            lIIIIIlIlIlIIII = false;
            lIIIIIlIlIIlllI = (Collection)(new ArrayList());
            lIIIIIlIlIlIlll = false;
            lIIIIIlIlIIllII = lIIIIIlIlIlIIIl.iterator();

            while(lIIIIIlIlIIllII.hasNext()) {
               lIIIIIlIlIllIlI = lIIIIIlIlIIllII.next();
               long lIIIIIlIlIIlIlI = (String)lIIIIIlIlIllIlI;
               lIIIIIlIlIllIll = false;
               if (StringsKt.startsWith(lIIIIIlIlIIlIlI, lIIIIIlIlIlIIlI[0], true)) {
                  lIIIIIlIlIIlllI.add(lIIIIIlIlIllIlI);
                  var10001 = false;
               }
            }

            return (List)lIIIIIlIlIIlllI;
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
