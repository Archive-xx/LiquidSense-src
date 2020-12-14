//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.misc;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IChatComponent;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/misc/Teams;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "armorColorValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "colorValue", "scoreboardValue", "isInYourTeam", "", "entity", "Lnet/minecraft/entity/EntityLivingBase;", "LiquidSense"}
)
@ModuleInfo(
   name = "Teams",
   description = "Prevents Killaura from attacking team mates.",
   category = ModuleCategory.MISC
)
public final class Teams extends Module {
   // $FF: synthetic field
   private final BoolValue scoreboardValue = new BoolValue("ScoreboardTeam", true);
   // $FF: synthetic field
   private final BoolValue armorColorValue = new BoolValue("ArmorColor", false);
   // $FF: synthetic field
   private final BoolValue colorValue = new BoolValue("Color", true);

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   public final boolean isInYourTeam(@NotNull EntityLivingBase lllllllllllllllllIlllIllIIIlIIll) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllIlllIllIIIlIIll, "entity");
      boolean var10001;
      if (access$getMc$p$s1046033730().thePlayer != null) {
         var10001 = false;
         EntityPlayerSP var10000;
         if ((Boolean)lllllllllllllllllIlllIllIIIlIIlI.scoreboardValue.get()) {
            var10000 = access$getMc$p$s1046033730().thePlayer;
            Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
            if (var10000.getTeam() != null && lllllllllllllllllIlllIllIIIlIIll.getTeam() != null) {
               var10000 = access$getMc$p$s1046033730().thePlayer;
               Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
               if (var10000.getTeam().isSameTeam(lllllllllllllllllIlllIllIIIlIIll.getTeam())) {
                  return true;
               }
            }
         }

         if ((Boolean)lllllllllllllllllIlllIllIIIlIIlI.armorColorValue.get()) {
            int lllllllllllllllllIlllIllIIIlIIII = (EntityPlayer)lllllllllllllllllIlllIllIIIlIIll;
            if (access$getMc$p$s1046033730().thePlayer.inventory.armorInventory[3] != null && lllllllllllllllllIlllIllIIIlIIII.inventory.armorInventory[3] != null) {
               ItemStack lllllllllllllllllIlllIllIIIllIII = access$getMc$p$s1046033730().thePlayer.inventory.armorInventory[3];
               Intrinsics.checkExpressionValueIsNotNull(lllllllllllllllllIlllIllIIIllIII, "myHead");
               Item var9 = lllllllllllllllllIlllIllIIIllIII.getItem();
               if (var9 == null) {
                  throw new TypeCastException("null cannot be cast to non-null type net.minecraft.item.ItemArmor");
               }

               ItemArmor lllllllllllllllllIlllIllIIIllIIl = (ItemArmor)var9;
               ItemStack lllllllllllllllllIlllIllIIIllIlI = lllllllllllllllllIlllIllIIIlIIII.inventory.armorInventory[3];
               var9 = lllllllllllllllllIlllIllIIIllIII.getItem();
               if (var9 == null) {
                  throw new TypeCastException("null cannot be cast to non-null type net.minecraft.item.ItemArmor");
               }

               boolean lllllllllllllllllIlllIllIIIIllII = (ItemArmor)var9;
               if (lllllllllllllllllIlllIllIIIllIIl.getColor(lllllllllllllllllIlllIllIIIllIII) == lllllllllllllllllIlllIllIIIIllII.getColor(lllllllllllllllllIlllIllIIIllIlI)) {
                  return true;
               }
            }
         }

         if ((Boolean)lllllllllllllllllIlllIllIIIlIIlI.colorValue.get()) {
            var10000 = access$getMc$p$s1046033730().thePlayer;
            Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
            if (var10000.getDisplayName() != null && lllllllllllllllllIlllIllIIIlIIll.getDisplayName() != null) {
               IChatComponent var10 = lllllllllllllllllIlllIllIIIlIIll.getDisplayName();
               Intrinsics.checkExpressionValueIsNotNull(var10, "entity.displayName");
               String var11 = var10.getFormattedText();
               Intrinsics.checkExpressionValueIsNotNull(var11, "entity.displayName.formattedText");
               String lllllllllllllllllIlllIllIIIlIlIl = StringsKt.replace$default(var11, "§r", "", false, 4, (Object)null);
               var10000 = access$getMc$p$s1046033730().thePlayer;
               Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
               var10 = var10000.getDisplayName();
               Intrinsics.checkExpressionValueIsNotNull(var10, "mc.thePlayer.displayName");
               var11 = var10.getFormattedText();
               Intrinsics.checkExpressionValueIsNotNull(var11, "mc.thePlayer.displayName.formattedText");
               String lllllllllllllllllIlllIllIIIlIllI = StringsKt.replace$default(var11, "§r", "", false, 4, (Object)null);
               return StringsKt.startsWith$default(lllllllllllllllllIlllIllIIIlIlIl, String.valueOf((new StringBuilder()).append('§').append(lllllllllllllllllIlllIllIIIlIllI.charAt(1))), false, 2, (Object)null);
            }
         }

         return false;
      } else {
         var10001 = false;
         return false;
      }
   }
}
