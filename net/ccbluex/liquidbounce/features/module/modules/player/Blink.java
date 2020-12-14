//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.player;

import java.awt.Color;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.render.Breadcrumbs;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.network.play.client.C03PacketPlayer.C06PacketPlayerPosLook;
import org.lwjgl.opengl.GL11;

@ModuleInfo(
   name = "Blink",
   description = "Suspends all movement packets.",
   category = ModuleCategory.PLAYER
)
public class Blink extends Module {
   // $FF: synthetic field
   private final LinkedList<double[]> positions = new LinkedList();
   // $FF: synthetic field
   private boolean disableLogger;
   // $FF: synthetic field
   private final BoolValue pulseValue = new BoolValue("Pulse", true);
   // $FF: synthetic field
   private final IntegerValue pulseDelayValue = new IntegerValue("PulseDelay", 1000, 500, 5000);
   // $FF: synthetic field
   private EntityOtherPlayerMP fakePlayer = null;
   // $FF: synthetic field
   private final MSTimer pulseTimer = new MSTimer();
   // $FF: synthetic field
   private final LinkedBlockingQueue<Packet> packets = new LinkedBlockingQueue();

   @EventTarget
   public void onRender3D(Render3DEvent llllllllllllllllllllIIIIIIlllIlI) {
      Breadcrumbs llllllllllllllllllllIIIIIIlllIIl = (Breadcrumbs)LiquidBounce.moduleManager.getModule(Breadcrumbs.class);
      String llllllllllllllllllllIIIIIIllIlIl = (Boolean)llllllllllllllllllllIIIIIIlllIIl.colorRainbow.get() ? ColorUtils.rainbow() : new Color((Integer)llllllllllllllllllllIIIIIIlllIIl.colorRedValue.get(), (Integer)llllllllllllllllllllIIIIIIlllIIl.colorGreenValue.get(), (Integer)llllllllllllllllllllIIIIIIlllIIl.colorBlueValue.get());
      synchronized(llllllllllllllllllllIIIIIIlllIll.positions) {
         GL11.glPushMatrix();
         GL11.glDisable(3553);
         GL11.glBlendFunc(770, 771);
         GL11.glEnable(2848);
         GL11.glEnable(3042);
         GL11.glDisable(2929);
         mc.entityRenderer.disableLightmap();
         GL11.glBegin(3);
         RenderUtils.glColor(llllllllllllllllllllIIIIIIllIlIl);
         int llllllllllllllllllllIIIIIIllIIll = mc.getRenderManager().viewerPosX;
         double llllllllllllllllllllIIIIIIllllIl = mc.getRenderManager().viewerPosY;
         float llllllllllllllllllllIIIIIIllIIIl = mc.getRenderManager().viewerPosZ;
         Iterator llllllllllllllllllllIIIIIIllIIII = llllllllllllllllllllIIIIIIlllIll.positions.iterator();

         while(llllllllllllllllllllIIIIIIllIIII.hasNext()) {
            double[] llllllllllllllllllllIIIIIIllllll = (double[])llllllllllllllllllllIIIIIIllIIII.next();
            GL11.glVertex3d(llllllllllllllllllllIIIIIIllllll[0] - llllllllllllllllllllIIIIIIllIIll, llllllllllllllllllllIIIIIIllllll[1] - llllllllllllllllllllIIIIIIllllIl, llllllllllllllllllllIIIIIIllllll[2] - llllllllllllllllllllIIIIIIllIIIl);
         }

         GL11.glColor4d(1.0D, 1.0D, 1.0D, 1.0D);
         GL11.glEnd();
         GL11.glEnable(2929);
         GL11.glDisable(2848);
         GL11.glDisable(3042);
         GL11.glEnable(3553);
         GL11.glPopMatrix();
      }
   }

   @EventTarget
   public void onUpdate(UpdateEvent llllllllllllllllllllIIIIIlIIllIl) {
      synchronized(llllllllllllllllllllIIIIIlIIllII.positions) {
         llllllllllllllllllllIIIIIlIIllII.positions.add(new double[]{mc.thePlayer.posX, mc.thePlayer.getEntityBoundingBox().minY, mc.thePlayer.posZ});
         boolean var10001 = false;
      }

      if ((Boolean)llllllllllllllllllllIIIIIlIIllII.pulseValue.get() && llllllllllllllllllllIIIIIlIIllII.pulseTimer.hasTimePassed((long)(Integer)llllllllllllllllllllIIIIIlIIllII.pulseDelayValue.get())) {
         llllllllllllllllllllIIIIIlIIllII.blink();
         llllllllllllllllllllIIIIIlIIllII.pulseTimer.reset();
      }

   }

   private void blink() {
      try {
         llllllllllllllllllllIIIIIIlIIlIl.disableLogger = true;

         while(!llllllllllllllllllllIIIIIIlIIlIl.packets.isEmpty()) {
            mc.getNetHandler().getNetworkManager().sendPacket((Packet)llllllllllllllllllllIIIIIIlIIlIl.packets.take());
         }

         llllllllllllllllllllIIIIIIlIIlIl.disableLogger = false;
      } catch (Exception var4) {
         var4.printStackTrace();
         llllllllllllllllllllIIIIIIlIIlIl.disableLogger = false;
      }

      synchronized(llllllllllllllllllllIIIIIIlIIlIl.positions) {
         llllllllllllllllllllIIIIIIlIIlIl.positions.clear();
      }
   }

   public void onDisable() {
      if (mc.thePlayer != null) {
         llllllllllllllllllllIIIIIlIlllII.blink();
         if (llllllllllllllllllllIIIIIlIlllII.fakePlayer != null) {
            mc.theWorld.removeEntityFromWorld(llllllllllllllllllllIIIIIlIlllII.fakePlayer.getEntityId());
            boolean var10001 = false;
            llllllllllllllllllllIIIIIlIlllII.fakePlayer = null;
         }

      }
   }

   public void onEnable() {
      if (mc.thePlayer != null) {
         if (!(Boolean)llllllllllllllllllllIIIIIllIIIIl.pulseValue.get()) {
            llllllllllllllllllllIIIIIllIIIIl.fakePlayer = new EntityOtherPlayerMP(mc.theWorld, mc.thePlayer.getGameProfile());
            llllllllllllllllllllIIIIIllIIIIl.fakePlayer.clonePlayer(mc.thePlayer, true);
            llllllllllllllllllllIIIIIllIIIIl.fakePlayer.copyLocationAndAnglesFrom(mc.thePlayer);
            llllllllllllllllllllIIIIIllIIIIl.fakePlayer.rotationYawHead = mc.thePlayer.rotationYawHead;
            mc.theWorld.addEntityToWorld(-1337, llllllllllllllllllllIIIIIllIIIIl.fakePlayer);
         }

         synchronized(llllllllllllllllllllIIIIIllIIIIl.positions) {
            llllllllllllllllllllIIIIIllIIIIl.positions.add(new double[]{mc.thePlayer.posX, mc.thePlayer.getEntityBoundingBox().minY + (double)(mc.thePlayer.getEyeHeight() / 2.0F), mc.thePlayer.posZ});
            boolean var10001 = false;
            llllllllllllllllllllIIIIIllIIIIl.positions.add(new double[]{mc.thePlayer.posX, mc.thePlayer.getEntityBoundingBox().minY, mc.thePlayer.posZ});
            var10001 = false;
         }

         llllllllllllllllllllIIIIIllIIIIl.pulseTimer.reset();
      }
   }

   @EventTarget
   public void onPacket(PacketEvent llllllllllllllllllllIIIIIlIlIIll) {
      Packet<?> llllllllllllllllllllIIIIIlIlIlIl = llllllllllllllllllllIIIIIlIlIIll.getPacket();
      if (mc.thePlayer != null && !llllllllllllllllllllIIIIIlIlIlll.disableLogger) {
         if (llllllllllllllllllllIIIIIlIlIlIl instanceof C03PacketPlayer) {
            llllllllllllllllllllIIIIIlIlIIll.cancelEvent();
         }

         if (llllllllllllllllllllIIIIIlIlIlIl instanceof C04PacketPlayerPosition || llllllllllllllllllllIIIIIlIlIlIl instanceof C06PacketPlayerPosLook || llllllllllllllllllllIIIIIlIlIlIl instanceof C08PacketPlayerBlockPlacement || llllllllllllllllllllIIIIIlIlIlIl instanceof C0APacketAnimation || llllllllllllllllllllIIIIIlIlIlIl instanceof C0BPacketEntityAction || llllllllllllllllllllIIIIIlIlIlIl instanceof C02PacketUseEntity) {
            llllllllllllllllllllIIIIIlIlIIll.cancelEvent();
            llllllllllllllllllllIIIIIlIlIlll.packets.add(llllllllllllllllllllIIIIIlIlIlIl);
            boolean var10001 = false;
         }

      }
   }

   public IntegerValue getValue() {
      return llllllllllllllllllllIIIIIllIIlIl.pulseDelayValue;
   }

   public String getTag() {
      return String.valueOf(llllllllllllllllllllIIIIIIlIlIll.packets.size());
   }
}
