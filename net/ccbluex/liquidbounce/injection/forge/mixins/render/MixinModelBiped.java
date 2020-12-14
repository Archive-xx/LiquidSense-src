//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.render.Rotations;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SideOnly(Side.CLIENT)
@Mixin({ModelBiped.class})
public class MixinModelBiped {
   // $FF: synthetic field
   @Shadow
   public ModelRenderer bipedRightArm;
   // $FF: synthetic field
   @Shadow
   public int heldItemRight;
   // $FF: synthetic field
   @Shadow
   public ModelRenderer bipedHead;

   @Inject(
      method = {"setRotationAngles"},
      at = {@At(
   value = "FIELD",
   target = "Lnet/minecraft/client/model/ModelBiped;swingProgress:F"
)}
   )
   private void revertSwordAnimation(float llllllllllllllllllIlIIIllIIIlIIl, float llllllllllllllllllIlIIIllIIIIlll, float llllllllllllllllllIlIIIllIIIIlIl, float llllllllllllllllllIlIIIllIIIIlII, float llllllllllllllllllIlIIIllIIIIIll, float llllllllllllllllllIlIIIllIIIIIIl, Entity llllllllllllllllllIlIIIlIlllllll, CallbackInfo llllllllllllllllllIlIIIlIllllllI) {
      if (llllllllllllllllllIlIIIlIlllllIl.heldItemRight == 3) {
         llllllllllllllllllIlIIIlIlllllIl.bipedRightArm.rotateAngleY = 0.0F;
      }

      if (LiquidBounce.moduleManager.getModule(Rotations.class).getState() && RotationUtils.serverRotation != null && llllllllllllllllllIlIIIlIlllllll instanceof EntityPlayer && llllllllllllllllllIlIIIlIlllllll.equals(Minecraft.getMinecraft().thePlayer)) {
         llllllllllllllllllIlIIIlIlllllIl.bipedHead.rotateAngleX = RotationUtils.serverRotation.getPitch() / 57.295776F;
      }

   }
}
