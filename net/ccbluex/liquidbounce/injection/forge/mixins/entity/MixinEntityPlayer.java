//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.injection.forge.mixins.entity;

import com.mojang.authlib.GameProfile;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.player.FastUse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.item.ItemStack;
import net.minecraft.util.FoodStats;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({EntityPlayer.class})
public abstract class MixinEntityPlayer extends MixinEntityLivingBase {
   // $FF: synthetic field
   @Shadow
   protected int flyToggleTimer;
   // $FF: synthetic field
   @Shadow
   public PlayerCapabilities capabilities;
   // $FF: synthetic field
   final FastUse fastuse;

   @Shadow
   public abstract ItemStack getItemInUse();

   @Shadow
   protected abstract boolean canTriggerWalking();

   @Shadow
   public abstract FoodStats getFoodStats();

   @Shadow
   public abstract int getItemInUseDuration();

   @Shadow
   public abstract ItemStack getHeldItem();

   @Shadow
   public abstract boolean isUsingItem();

   @Inject(
      method = {"clearItemInUse"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/entity/player/EntityPlayer;setEating(Z)V",
   shift = At.Shift.AFTER
)}
   )
   private void clearItemInUse(CallbackInfo lIIlllIllllIIll) {
      if (LiquidBounce.moduleManager.getModule(FastUse.class).getState()) {
         ((FastUse)LiquidBounce.moduleManager.getModule(FastUse.class)).resetTimer();
      }

   }

   @Shadow
   protected abstract String getSwimSound();

   public MixinEntityPlayer() {
      lIIlllIllllIlIl.fastuse = (FastUse)LiquidBounce.moduleManager.getModule(FastUse.class);
   }

   @Shadow
   public abstract GameProfile getGameProfile();
}
