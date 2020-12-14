//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.injection.forge.mixins.entity;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.ClickWindowEvent;
import net.ccbluex.liquidbounce.features.module.modules.exploit.AbortBreaking;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SideOnly(Side.CLIENT)
@Mixin({PlayerControllerMP.class})
public class MixinPlayerControllerMP {
   // $FF: synthetic field
   @Shadow
   @Final
   public NetHandlerPlayClient netClientHandler;

   @Inject(
      method = {"getIsHittingBlock"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void getIsHittingBlock(CallbackInfoReturnable<Boolean> llllllllllllllllllllIllllIIlIIlI) {
      if (LiquidBounce.moduleManager.getModule(AbortBreaking.class).getState()) {
         llllllllllllllllllllIllllIIlIIlI.setReturnValue(false);
      }

   }

   @Inject(
      method = {"attackEntity"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;syncCurrentPlayItem()V"
)}
   )
   private void attackEntity(EntityPlayer llllllllllllllllllllIllllIlIIIll, Entity llllllllllllllllllllIllllIIllllI, CallbackInfo llllllllllllllllllllIllllIlIIIII) {
      LiquidBounce.eventManager.callEvent(new AttackEvent(llllllllllllllllllllIllllIIllllI));
   }

   @Overwrite
   public ItemStack windowClick(int llllllllllllllllllllIlllIlllIIIl, int llllllllllllllllllllIlllIllllIIl, int llllllllllllllllllllIlllIllIllll, int llllllllllllllllllllIlllIllIlllI, EntityPlayer llllllllllllllllllllIlllIlllIllI) {
      char llllllllllllllllllllIlllIllIllII = new ClickWindowEvent(llllllllllllllllllllIlllIlllIIIl, llllllllllllllllllllIlllIllllIIl, llllllllllllllllllllIlllIllIllll, llllllllllllllllllllIlllIllIlllI);
      LiquidBounce.eventManager.callEvent(llllllllllllllllllllIlllIllIllII);
      if (llllllllllllllllllllIlllIllIllII.isCancelled()) {
         return null;
      } else {
         boolean llllllllllllllllllllIlllIllIlIll = llllllllllllllllllllIlllIlllIllI.openContainer.getNextTransactionID(llllllllllllllllllllIlllIlllIllI.inventory);
         byte llllllllllllllllllllIlllIllIlIlI = llllllllllllllllllllIlllIlllIllI.openContainer.slotClick(llllllllllllllllllllIlllIllllIIl, llllllllllllllllllllIlllIllIllll, llllllllllllllllllllIlllIllIlllI, llllllllllllllllllllIlllIlllIllI);
         llllllllllllllllllllIlllIlllIIlI.netClientHandler.addToSendQueue(new C0EPacketClickWindow(llllllllllllllllllllIlllIlllIIIl, llllllllllllllllllllIlllIllllIIl, llllllllllllllllllllIlllIllIllll, llllllllllllllllllllIlllIllIlllI, llllllllllllllllllllIlllIllIlIlI, (short)-1));
         return llllllllllllllllllllIlllIllIlIlI;
      }
   }
}
