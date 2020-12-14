//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.injection.forge.mixins.network;

import com.mojang.authlib.GameProfile;
import java.util.Objects;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.misc.NameProtect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({NetworkPlayerInfo.class})
public class MixinNetworkPlayerInfo {
   // $FF: synthetic field
   @Shadow
   @Final
   private GameProfile gameProfile;

   @Inject(
      method = {"getLocationSkin"},
      cancellable = true,
      at = {@At("HEAD")}
   )
   private void injectSkinProtect(CallbackInfoReturnable<ResourceLocation> lIlIIlllIlIllll) {
      NameProtect lIlIIlllIllIIIl = (NameProtect)LiquidBounce.moduleManager.getModule(NameProtect.class);
      if (lIlIIlllIllIIIl.getState() && (Boolean)lIlIIlllIllIIIl.skinProtectValue.get() && ((Boolean)lIlIIlllIllIIIl.allPlayersValue.get() || Objects.equals(lIlIIlllIllIIII.gameProfile.getId(), Minecraft.getMinecraft().getSession().getProfile().getId()))) {
         lIlIIlllIlIllll.setReturnValue(DefaultPlayerSkin.getDefaultSkin(lIlIIlllIllIIII.gameProfile.getId()));
         lIlIIlllIlIllll.cancel();
      }

   }
}
