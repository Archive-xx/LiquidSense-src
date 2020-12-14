//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.render;

import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;

@ModuleInfo(
   name = "FreeCam",
   description = "Allows you to move out of your body.",
   category = ModuleCategory.RENDER
)
public class FreeCam extends Module {
   // $FF: synthetic field
   private EntityOtherPlayerMP fakePlayer = null;
   // $FF: synthetic field
   private double oldZ;
   // $FF: synthetic field
   private final BoolValue noClipValue = new BoolValue("NoClip", true);
   // $FF: synthetic field
   private final FloatValue speedValue = new FloatValue("Speed", 0.8F, 0.1F, 2.0F);
   // $FF: synthetic field
   private double oldY;
   // $FF: synthetic field
   private double oldX;
   // $FF: synthetic field
   private final BoolValue flyValue = new BoolValue("Fly", true);

   public void onDisable() {
      if (mc.thePlayer != null && lllllllllllllllllIlIlllIlIIIlllI.fakePlayer != null) {
         mc.thePlayer.setPositionAndRotation(lllllllllllllllllIlIlllIlIIIlllI.oldX, lllllllllllllllllIlIlllIlIIIlllI.oldY, lllllllllllllllllIlIlllIlIIIlllI.oldZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch);
         mc.theWorld.removeEntityFromWorld(lllllllllllllllllIlIlllIlIIIlllI.fakePlayer.getEntityId());
         boolean var10001 = false;
         lllllllllllllllllIlIlllIlIIIlllI.fakePlayer = null;
         mc.thePlayer.motionX = 0.0D;
         mc.thePlayer.motionY = 0.0D;
         mc.thePlayer.motionZ = 0.0D;
      }
   }

   @EventTarget
   public void onPacket(PacketEvent lllllllllllllllllIlIlllIlIIIIIlI) {
      Packet<?> lllllllllllllllllIlIlllIlIIIIIIl = lllllllllllllllllIlIlllIlIIIIIlI.getPacket();
      if (lllllllllllllllllIlIlllIlIIIIIIl instanceof C03PacketPlayer || lllllllllllllllllIlIlllIlIIIIIIl instanceof C0BPacketEntityAction) {
         lllllllllllllllllIlIlllIlIIIIIlI.cancelEvent();
      }

   }

   @EventTarget
   public void onUpdate(UpdateEvent lllllllllllllllllIlIlllIlIIIlIII) {
      if ((Boolean)lllllllllllllllllIlIlllIlIIIlIIl.noClipValue.get()) {
         mc.thePlayer.noClip = true;
      }

      mc.thePlayer.fallDistance = 0.0F;
      if ((Boolean)lllllllllllllllllIlIlllIlIIIlIIl.flyValue.get()) {
         short lllllllllllllllllIlIlllIlIIIIllI = (Float)lllllllllllllllllIlIlllIlIIIlIIl.speedValue.get();
         mc.thePlayer.motionY = 0.0D;
         mc.thePlayer.motionX = 0.0D;
         mc.thePlayer.motionZ = 0.0D;
         EntityPlayerSP var10000;
         if (mc.gameSettings.keyBindJump.isKeyDown()) {
            var10000 = mc.thePlayer;
            var10000.motionY += (double)lllllllllllllllllIlIlllIlIIIIllI;
         }

         if (mc.gameSettings.keyBindSneak.isKeyDown()) {
            var10000 = mc.thePlayer;
            var10000.motionY -= (double)lllllllllllllllllIlIlllIlIIIIllI;
         }

         MovementUtils.strafe(lllllllllllllllllIlIlllIlIIIIllI);
      }

   }

   public void onEnable() {
      if (mc.thePlayer != null) {
         lllllllllllllllllIlIlllIlIIlIIIl.oldX = mc.thePlayer.posX;
         lllllllllllllllllIlIlllIlIIlIIIl.oldY = mc.thePlayer.posY;
         lllllllllllllllllIlIlllIlIIlIIIl.oldZ = mc.thePlayer.posZ;
         lllllllllllllllllIlIlllIlIIlIIIl.fakePlayer = new EntityOtherPlayerMP(mc.theWorld, mc.thePlayer.getGameProfile());
         lllllllllllllllllIlIlllIlIIlIIIl.fakePlayer.clonePlayer(mc.thePlayer, true);
         lllllllllllllllllIlIlllIlIIlIIIl.fakePlayer.rotationYawHead = mc.thePlayer.rotationYawHead;
         lllllllllllllllllIlIlllIlIIlIIIl.fakePlayer.copyLocationAndAnglesFrom(mc.thePlayer);
         mc.theWorld.addEntityToWorld(-1000, lllllllllllllllllIlIlllIlIIlIIIl.fakePlayer);
         if ((Boolean)lllllllllllllllllIlIlllIlIIlIIIl.noClipValue.get()) {
            mc.thePlayer.noClip = true;
         }

      }
   }
}
