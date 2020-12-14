//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.injection.forge.mixins.entity;

import java.util.Objects;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.cape.CapeAPI;
import net.ccbluex.liquidbounce.cape.CapeInfo;
import net.ccbluex.liquidbounce.features.module.modules.misc.NameProtect;
import net.ccbluex.liquidbounce.features.module.modules.render.NoFOV;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SideOnly(Side.CLIENT)
@Mixin({AbstractClientPlayer.class})
public abstract class MixinAbstractClientPlayer extends MixinEntityPlayer {
   // $FF: synthetic field
   private CapeInfo capeInfo;

   @Inject(
      method = {"getFovModifier"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void getFovModifier(CallbackInfoReturnable<Float> llllIlIIlIlIII) {
      byte llllIlIIlIIlll = (NoFOV)LiquidBounce.moduleManager.getModule(NoFOV.class);
      if (llllIlIIlIIlll.getState()) {
         long llllIlIIlIIllI = (Float)llllIlIIlIIlll.getFovValue().get();
         if (!llllIlIIlIllII.isUsingItem()) {
            llllIlIIlIlIII.setReturnValue(llllIlIIlIIllI);
            return;
         }

         if (llllIlIIlIllII.getItemInUse().getItem() != Items.bow) {
            llllIlIIlIlIII.setReturnValue(llllIlIIlIIllI);
            return;
         }

         int llllIlIIlIlllI = llllIlIIlIllII.getItemInUseDuration();
         float llllIlIIlIllIl = (float)llllIlIIlIlllI / 20.0F;
         llllIlIIlIllIl = llllIlIIlIllIl > 1.0F ? 1.0F : llllIlIIlIllIl * llllIlIIlIllIl;
         llllIlIIlIIllI *= 1.0F - llllIlIIlIllIl * 0.15F;
         llllIlIIlIlIII.setReturnValue(llllIlIIlIIllI);
      }

   }

   @Inject(
      method = {"getLocationCape"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void getCape(CallbackInfoReturnable<ResourceLocation> llllIlIIlllIII) {
      if (CapeAPI.INSTANCE.hasCapeService()) {
         if (llllIlIIlllIIl.capeInfo == null) {
            llllIlIIlllIIl.capeInfo = CapeAPI.INSTANCE.loadCape(llllIlIIlllIIl.getUniqueID());
         }

         if (llllIlIIlllIIl.capeInfo != null && llllIlIIlllIIl.capeInfo.isCapeAvailable()) {
            llllIlIIlllIII.setReturnValue(llllIlIIlllIIl.capeInfo.getResourceLocation());
         }

      }
   }

   @Inject(
      method = {"getLocationSkin()Lnet/minecraft/util/ResourceLocation;"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void getSkin(CallbackInfoReturnable<ResourceLocation> llllIlIIIlllII) {
      NameProtect llllIlIIIllllI = (NameProtect)LiquidBounce.moduleManager.getModule(NameProtect.class);
      if (llllIlIIIllllI.getState() && (Boolean)llllIlIIIllllI.skinProtectValue.get()) {
         if (!(Boolean)llllIlIIIllllI.allPlayersValue.get() && !Objects.equals(llllIlIIIlllIl.getGameProfile().getName(), Minecraft.getMinecraft().thePlayer.getGameProfile().getName())) {
            return;
         }

         llllIlIIIlllII.setReturnValue(DefaultPlayerSkin.getDefaultSkin(llllIlIIIlllIl.getUniqueID()));
      }

   }
}
