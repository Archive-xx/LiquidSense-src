//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.misc;

import java.util.Iterator;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.TextEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.file.configs.FriendsConfig;
import net.ccbluex.liquidbounce.utils.misc.StringUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.TextValue;
import net.minecraft.client.network.NetworkPlayerInfo;

@ModuleInfo(
   name = "NameProtect",
   description = "Changes playernames clientside.",
   category = ModuleCategory.MISC
)
public class NameProtect extends Module {
   // $FF: synthetic field
   private final TextValue fakeNameValue = new TextValue("FakeName", "&cMe");
   // $FF: synthetic field
   public final BoolValue skinProtectValue = new BoolValue("SkinProtect", true);
   // $FF: synthetic field
   public final BoolValue allPlayersValue = new BoolValue("AllPlayers", false);

   @EventTarget(
      ignoreCondition = true
   )
   public void onText(TextEvent llIIIIllIIlIIl) {
      if (mc.thePlayer != null && !llIIIIllIIlIIl.getText().contains("§8[§9§lLiquidSense§8] §3")) {
         Iterator llIIIIllIIlIII = LiquidBounce.fileManager.friendsConfig.getFriends().iterator();

         while(llIIIIllIIlIII.hasNext()) {
            byte llIIIIllIIIlll = (FriendsConfig.Friend)llIIIIllIIlIII.next();
            llIIIIllIIlIIl.setText(StringUtils.replace(llIIIIllIIlIIl.getText(), llIIIIllIIIlll.getPlayerName(), String.valueOf((new StringBuilder()).append(ColorUtils.translateAlternateColorCodes(llIIIIllIIIlll.getAlias())).append("§f"))));
         }

         if (llIIIIllIIlIlI.getState()) {
            llIIIIllIIlIIl.setText(StringUtils.replace(llIIIIllIIlIIl.getText(), mc.thePlayer.getName(), String.valueOf((new StringBuilder()).append(ColorUtils.translateAlternateColorCodes((String)llIIIIllIIlIlI.fakeNameValue.get())).append("§f"))));
            if ((Boolean)llIIIIllIIlIlI.allPlayersValue.get()) {
               llIIIIllIIlIII = mc.getNetHandler().getPlayerInfoMap().iterator();

               while(llIIIIllIIlIII.hasNext()) {
                  NetworkPlayerInfo llIIIIllIIllIl = (NetworkPlayerInfo)llIIIIllIIlIII.next();
                  llIIIIllIIlIIl.setText(StringUtils.replace(llIIIIllIIlIIl.getText(), llIIIIllIIllIl.getGameProfile().getName(), "Protected User"));
               }
            }

         }
      }
   }
}
