//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.injection.forge.mixins.resources;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.misc.NameProtect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.SkinManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({SkinManager.class})
public class MixinSkinManager {
   @Inject(
      method = {"loadSkinFromCache"},
      cancellable = true,
      at = {@At("HEAD")}
   )
   private void injectSkinProtect(GameProfile llllllllllllllllllIlllllIlIlllll, CallbackInfoReturnable<Map<Type, MinecraftProfileTexture>> llllllllllllllllllIlllllIlIllllI) {
      NameProtect llllllllllllllllllIlllllIlIlllIl = (NameProtect)LiquidBounce.moduleManager.getModule(NameProtect.class);
      if (llllllllllllllllllIlllllIlIlllIl.getState() && (Boolean)llllllllllllllllllIlllllIlIlllIl.skinProtectValue.get() && ((Boolean)llllllllllllllllllIlllllIlIlllIl.allPlayersValue.get() || Objects.equals(llllllllllllllllllIlllllIlIlllll.getId(), Minecraft.getMinecraft().getSession().getProfile().getId()))) {
         llllllllllllllllllIlllllIlIllllI.setReturnValue(new HashMap());
         llllllllllllllllllIlllllIlIllllI.cancel();
      }

   }
}
