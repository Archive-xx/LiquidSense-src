//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.injection.forge.mixins.entity;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.LiquidWalk;
import net.ccbluex.liquidbounce.features.module.modules.movement.NoJumpDelay;
import net.ccbluex.liquidbounce.features.module.modules.render.AntiBlind;
import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({EntityLivingBase.class})
public abstract class MixinEntityLivingBase extends MixinEntity {
   // $FF: synthetic field
   @Shadow
   private int jumpTicks;
   // $FF: synthetic field
   @Shadow
   protected boolean isJumping;

   @Shadow
   public abstract ItemStack getHeldItem();

   @Shadow
   protected abstract float getJumpUpwardsMotion();

   @Overwrite
   protected void jump() {
      char llIIIIIlIIIIIlI = new JumpEvent(llIIIIIlIIIIIll.getJumpUpwardsMotion());
      LiquidBounce.eventManager.callEvent(llIIIIIlIIIIIlI);
      if (!llIIIIIlIIIIIlI.isCancelled()) {
         llIIIIIlIIIIIll.motionY = (double)llIIIIIlIIIIIlI.getMotion();
         if (llIIIIIlIIIIIll.isPotionActive(Potion.jump)) {
            llIIIIIlIIIIIll.motionY += (double)((float)(llIIIIIlIIIIIll.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F);
         }

         if (llIIIIIlIIIIIll.isSprinting()) {
            float llIIIIIlIIIIllI = llIIIIIlIIIIIll.rotationYaw * 0.017453292F;
            llIIIIIlIIIIIll.motionX -= (double)(MathHelper.sin(llIIIIIlIIIIllI) * 0.2F);
            llIIIIIlIIIIIll.motionZ += (double)(MathHelper.cos(llIIIIIlIIIIllI) * 0.2F);
         }

         llIIIIIlIIIIIll.isAirBorne = true;
      }
   }

   @Shadow
   protected abstract void updateFallState(double var1, boolean var3, Block var4, BlockPos var5);

   @Shadow
   public abstract PotionEffect getActivePotionEffect(Potion var1);

   @Shadow
   public abstract float getHealth();

   @Inject(
      method = {"onLivingUpdate"},
      at = {@At("HEAD")}
   )
   private void headLiving(CallbackInfo llIIIIIIllllllI) {
      if (LiquidBounce.moduleManager.getModule(NoJumpDelay.class).getState()) {
         llIIIIIIlllllll.jumpTicks = 0;
      }

   }

   @Shadow
   public void onLivingUpdate() {
   }

   @Inject(
      method = {"getLook"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void getLook(CallbackInfoReturnable<Vec3> llIIIIIIlllIIII) {
      if ((EntityLivingBase)llIIIIIIlllIIll instanceof EntityPlayerSP) {
         llIIIIIIlllIIII.setReturnValue(llIIIIIIlllIIll.getVectorForRotation(llIIIIIIlllIIll.rotationPitch, llIIIIIIlllIIll.rotationYaw));
      }

   }

   @Inject(
      method = {"isPotionActive(Lnet/minecraft/potion/Potion;)Z"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void isPotionActive(Potion llIIIIIIllIlIII, CallbackInfoReturnable<Boolean> llIIIIIIllIIlll) {
      AntiBlind llIIIIIIllIlIIl = (AntiBlind)LiquidBounce.moduleManager.getModule(AntiBlind.class);
      if ((llIIIIIIllIlIII == Potion.confusion || llIIIIIIllIlIII == Potion.blindness) && llIIIIIIllIlIIl.getState() && (Boolean)llIIIIIIllIlIIl.getConfusionEffect().get()) {
         llIIIIIIllIIlll.setReturnValue(false);
      }

   }

   @Shadow
   public abstract boolean isPotionActive(Potion var1);

   @Shadow
   protected abstract void updateAITick();

   @Inject(
      method = {"onLivingUpdate"},
      at = {@At(
   value = "FIELD",
   target = "Lnet/minecraft/entity/EntityLivingBase;isJumping:Z",
   ordinal = 1
)}
   )
   private void onJumpSection(CallbackInfo llIIIIIIllllIIl) {
      long llIIIIIIlllIllI = (LiquidWalk)LiquidBounce.moduleManager.getModule(LiquidWalk.class);
      if (llIIIIIIlllIllI.getState() && !llIIIIIIllllIlI.isJumping && !llIIIIIIllllIlI.isSneaking() && llIIIIIIllllIlI.isInWater() && ((String)llIIIIIIlllIllI.modeValue.get()).equalsIgnoreCase("Swim")) {
         llIIIIIIllllIlI.updateAITick();
      }

   }
}
