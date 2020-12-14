//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.utils;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.combat.NoFriends;
import net.ccbluex.liquidbounce.features.module.modules.misc.AntiBot;
import net.ccbluex.liquidbounce.features.module.modules.misc.Teams;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public final class EntityUtils extends MinecraftInstance {
   // $FF: synthetic field
   public static boolean targetPlayer = true;
   // $FF: synthetic field
   public static boolean targetAnimals = false;
   // $FF: synthetic field
   public static boolean targetMobs = true;
   // $FF: synthetic field
   public static boolean targetInvisible = false;
   // $FF: synthetic field
   public static boolean targetDead = false;

   public static int getPing(EntityPlayer lIllllIlIlllII) {
      if (lIllllIlIlllII == null) {
         return 0;
      } else {
         NetworkPlayerInfo lIllllIlIlllIl = mc.getNetHandler().getPlayerInfo(lIllllIlIlllII.getUniqueID());
         return lIllllIlIlllIl == null ? 0 : lIllllIlIlllIl.getResponseTime();
      }
   }

   public static boolean isSelected(Entity lIllllIlllIIlI, boolean lIllllIllIllll) {
      if (!(lIllllIlllIIlI instanceof EntityLivingBase) || !targetDead && !lIllllIlllIIlI.isEntityAlive() || lIllllIlllIIlI == mc.thePlayer || !targetInvisible && lIllllIlllIIlI.isInvisible()) {
         return false;
      } else if (targetPlayer && lIllllIlllIIlI instanceof EntityPlayer) {
         EntityPlayer lIllllIlllIIll = (EntityPlayer)lIllllIlllIIlI;
         if (lIllllIllIllll) {
            if (AntiBot.isBot(lIllllIlllIIll)) {
               return false;
            } else if (isFriend(lIllllIlllIIll) && !LiquidBounce.moduleManager.getModule(NoFriends.class).getState()) {
               return false;
            } else if (lIllllIlllIIll.isSpectator()) {
               return false;
            } else {
               float lIllllIllIllIl = (Teams)LiquidBounce.moduleManager.getModule(Teams.class);
               return !lIllllIllIllIl.getState() || !lIllllIllIllIl.isInYourTeam(lIllllIlllIIll);
            }
         } else {
            return true;
         }
      } else {
         return targetMobs && isMob(lIllllIlllIIlI) || targetAnimals && isAnimal(lIllllIlllIIlI);
      }
   }

   public static String getName(NetworkPlayerInfo lIllllIllIIIIl) {
      return lIllllIllIIIIl.getDisplayName() != null ? lIllllIllIIIIl.getDisplayName().getFormattedText() : ScorePlayerTeam.formatPlayerName(lIllllIllIIIIl.getPlayerTeam(), lIllllIllIIIIl.getGameProfile().getName());
   }

   public static boolean isFriend(Entity lIllllIllIlIll) {
      return lIllllIllIlIll instanceof EntityPlayer && lIllllIllIlIll.getName() != null && LiquidBounce.fileManager.friendsConfig.isFriend(ColorUtils.stripColor(lIllllIllIlIll.getName()));
   }

   public static boolean isAnimal(Entity lIllllIllIIlll) {
      return lIllllIllIIlll instanceof EntityAnimal || lIllllIllIIlll instanceof EntitySquid || lIllllIllIIlll instanceof EntityGolem || lIllllIllIIlll instanceof EntityBat;
   }

   public static boolean isMob(Entity lIllllIllIIlII) {
      return lIllllIllIIlII instanceof EntityMob || lIllllIllIIlII instanceof EntityVillager || lIllllIllIIlII instanceof EntitySlime || lIllllIllIIlII instanceof EntityGhast || lIllllIllIIlII instanceof EntityDragon;
   }
}
