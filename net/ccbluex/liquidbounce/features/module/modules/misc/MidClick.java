//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.misc;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.file.configs.FriendsConfig;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.input.Mouse;

@ModuleInfo(
   name = "MidClick",
   description = "Allows you to add a player as a friend by right clicking him.",
   category = ModuleCategory.MISC
)
public class MidClick extends Module {
   // $FF: synthetic field
   private boolean wasDown;

   @EventTarget
   public void onRender(Render2DEvent llIllIIlIlIllll) {
      if (mc.currentScreen == null) {
         if (!llIllIIlIllIIII.wasDown && Mouse.isButtonDown(2)) {
            byte llIllIIlIlIllII = mc.objectMouseOver.entityHit;
            if (llIllIIlIlIllII instanceof EntityPlayer) {
               String llIllIIlIllIlII = ColorUtils.stripColor(llIllIIlIlIllII.getName());
               FriendsConfig llIllIIlIllIIll = LiquidBounce.fileManager.friendsConfig;
               boolean var10001;
               if (!llIllIIlIllIIll.isFriend(llIllIIlIllIlII)) {
                  llIllIIlIllIIll.addFriend(llIllIIlIllIlII);
                  var10001 = false;
                  LiquidBounce.fileManager.saveConfig(llIllIIlIllIIll);
                  ClientUtils.displayChatMessage(String.valueOf((new StringBuilder()).append("§a§l").append(llIllIIlIllIlII).append("§c was added to your friends.")));
               } else {
                  llIllIIlIllIIll.removeFriend(llIllIIlIllIlII);
                  var10001 = false;
                  LiquidBounce.fileManager.saveConfig(llIllIIlIllIIll);
                  ClientUtils.displayChatMessage(String.valueOf((new StringBuilder()).append("§a§l").append(llIllIIlIllIlII).append("§c was removed from your friends.")));
               }
            } else {
               ClientUtils.displayChatMessage("§c§lError: §aYou need to select a player.");
            }
         }

         llIllIIlIllIIII.wasDown = Mouse.isButtonDown(2);
      }
   }
}
