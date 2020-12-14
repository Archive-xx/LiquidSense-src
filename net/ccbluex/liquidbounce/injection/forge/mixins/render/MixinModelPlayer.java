package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.UpdateModelEvent;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ModelPlayer.class})
public class MixinModelPlayer {
   @Inject(
      method = {"setRotationAngles"},
      at = {@At("RETURN")}
   )
   private void revertSwordAnimation(float lllllllllllllllllllIIIlIIlIIlIlI, float lllllllllllllllllllIIIlIIlIIlIIl, float lllllllllllllllllllIIIlIIlIIlIII, float lllllllllllllllllllIIIlIIlIIIlll, float lllllllllllllllllllIIIlIIlIIIllI, float lllllllllllllllllllIIIlIIlIIIlIl, Entity lllllllllllllllllllIIIlIIlIIIIIl, CallbackInfo lllllllllllllllllllIIIlIIlIIIIll) {
      LiquidBounce.eventManager.callEvent(new UpdateModelEvent((EntityPlayer)lllllllllllllllllllIIIlIIlIIIIIl, (ModelPlayer)lllllllllllllllllllIIIlIIlIIIIlI));
   }
}
