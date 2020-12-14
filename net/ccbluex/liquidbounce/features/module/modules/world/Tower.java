//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.world;

import java.awt.Color;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.InventoryUtils;
import net.ccbluex.liquidbounce.utils.PlaceRotation;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.block.PlaceInfo;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.timer.TickTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.block.BlockAir;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.potion.Potion;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;

@ModuleInfo(
   name = "Tower",
   description = "Automatically builds a tower beneath you.",
   category = ModuleCategory.WORLD,
   keyBind = 24
)
public class Tower extends Module {
   // $FF: synthetic field
   private final IntegerValue teleportDelayValue = new IntegerValue("TeleportDelay", 0, 0, 20);
   // $FF: synthetic field
   private double jumpGround = 0.0D;
   // $FF: synthetic field
   private final FloatValue constantMotionJumpGroundValue = new FloatValue("ConstantMotionJumpGround", 0.79F, 0.76F, 1.0F);
   // $FF: synthetic field
   private final BoolValue stayAutoBlock = new BoolValue("StayAutoBlock", false);
   // $FF: synthetic field
   private final BoolValue rotationsValue = new BoolValue("Rotations", true);
   // $FF: synthetic field
   private final ListValue modeValue = new ListValue("Mode", new String[]{"Hypixel", "Jump", "Motion", "ConstantMotion", "MotionTP", "Packet", "Teleport", "AAC3.3.9"}, "Motion");
   // $FF: synthetic field
   private final TickTimer timer = new TickTimer();
   // $FF: synthetic field
   private final ListValue placeModeValue = new ListValue("PlaceTiming", new String[]{"Pre", "Post"}, "Post");
   // $FF: synthetic field
   private final BoolValue teleportGroundValue = new BoolValue("TeleportGround", true);
   // $FF: synthetic field
   private int slot;
   // $FF: synthetic field
   private final BoolValue counterDisplayValue = new BoolValue("Counter", true);
   // $FF: synthetic field
   private final FloatValue timerValue = new FloatValue("Timer", 1.0F, 0.0F, 10.0F);
   // $FF: synthetic field
   private final BoolValue autoBlockValue = new BoolValue("AutoBlock", true);
   // $FF: synthetic field
   private final BoolValue stopWhenBlockAbove = new BoolValue("StopWhenBlockAbove", false);
   // $FF: synthetic field
   private PlaceInfo placeInfo;
   // $FF: synthetic field
   private final BoolValue swingValue = new BoolValue("Swing", true);
   // $FF: synthetic field
   private final FloatValue constantMotionValue = new FloatValue("ConstantMotion", 0.42F, 0.1F, 1.0F);
   // $FF: synthetic field
   private final FloatValue jumpMotionValue = new FloatValue("JumpMotion", 0.42F, 0.3681289F, 0.79F);
   // $FF: synthetic field
   private final FloatValue teleportHeightValue = new FloatValue("TeleportHeight", 1.15F, 0.1F, 5.0F);
   // $FF: synthetic field
   private final BoolValue onJumpValue = new BoolValue("OnJump", false);
   // $FF: synthetic field
   private final BoolValue keepRotationValue = new BoolValue("KeepRotation", false);
   // $FF: synthetic field
   private Rotation lockRotation;
   // $FF: synthetic field
   private final BoolValue teleportNoMotionValue = new BoolValue("TeleportNoMotion", false);
   // $FF: synthetic field
   private final IntegerValue jumpDelayValue = new IntegerValue("JumpDelay", 0, 0, 20);

   public void onDisable() {
      if (mc.thePlayer != null) {
         mc.timer.timerSpeed = 1.0F;
         lllllllllllllllllIllIIIlIlIlIlll.lockRotation = null;
         if (lllllllllllllllllIllIIIlIlIlIlll.slot != mc.thePlayer.inventory.currentItem) {
            mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
         }

      }
   }

   @EventTarget
   public void onMotion(MotionEvent lllllllllllllllllIllIIIlIlIIllIl) {
      if (!(Boolean)lllllllllllllllllIllIIIlIlIIlllI.onJumpValue.get() || mc.gameSettings.keyBindJump.isKeyDown()) {
         if ((Boolean)lllllllllllllllllIllIIIlIlIIlllI.rotationsValue.get() && (Boolean)lllllllllllllllllIllIIIlIlIIlllI.keepRotationValue.get() && lllllllllllllllllIllIIIlIlIIlllI.lockRotation != null) {
            RotationUtils.setTargetRotation(lllllllllllllllllIllIIIlIlIIlllI.lockRotation);
         }

         mc.timer.timerSpeed = (Float)lllllllllllllllllIllIIIlIlIIlllI.timerValue.get();
         EventState lllllllllllllllllIllIIIlIlIIllII = lllllllllllllllllIllIIIlIlIIllIl.getEventState();
         if (((String)lllllllllllllllllIllIIIlIlIIlllI.placeModeValue.get()).equalsIgnoreCase(lllllllllllllllllIllIIIlIlIIllII.getStateName())) {
            lllllllllllllllllIllIIIlIlIIlllI.place();
         }

         if (lllllllllllllllllIllIIIlIlIIllII == EventState.PRE) {
            lllllllllllllllllIllIIIlIlIIlllI.placeInfo = null;
            lllllllllllllllllIllIIIlIlIIlllI.timer.update();
            if ((Boolean)lllllllllllllllllIllIIIlIlIIlllI.autoBlockValue.get()) {
               if (InventoryUtils.findAutoBlockBlock() == -1) {
                  return;
               }
            } else if (mc.thePlayer.getHeldItem() == null || !(mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock)) {
               return;
            }

            if (!(Boolean)lllllllllllllllllIllIIIlIlIIlllI.stopWhenBlockAbove.get() || BlockUtils.getBlock(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY + 2.0D, mc.thePlayer.posZ)) instanceof BlockAir) {
               lllllllllllllllllIllIIIlIlIIlllI.move();
            }

            Exception lllllllllllllllllIllIIIlIlIIlIII = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ);
            if (mc.theWorld.getBlockState(lllllllllllllllllIllIIIlIlIIlIII).getBlock() instanceof BlockAir && lllllllllllllllllIllIIIlIlIIlllI.search(lllllllllllllllllIllIIIlIlIIlIII) && (Boolean)lllllllllllllllllIllIIIlIlIIlllI.rotationsValue.get()) {
               char lllllllllllllllllIllIIIlIlIIIlll = RotationUtils.faceBlock(lllllllllllllllllIllIIIlIlIIlIII);
               if (lllllllllllllllllIllIIIlIlIIIlll != null) {
                  RotationUtils.setTargetRotation(lllllllllllllllllIllIIIlIlIIIlll.getRotation());
                  lllllllllllllllllIllIIIlIlIIlllI.placeInfo.setVec3(lllllllllllllllllIllIIIlIlIIIlll.getVec());
               }
            }
         }

      }
   }

   private void fakeJump() {
      mc.thePlayer.isAirBorne = true;
      mc.thePlayer.triggerAchievement(StatList.jumpStat);
   }

   @EventTarget
   public void onRender2D(Render2DEvent lllllllllllllllllIllIIIIlIIIlllI) {
      if ((Boolean)lllllllllllllllllIllIIIIlIIlIIII.counterDisplayValue.get()) {
         GlStateManager.pushMatrix();
         String lllllllllllllllllIllIIIIlIIlIIlI = String.valueOf((new StringBuilder()).append("Blocks: §7").append(lllllllllllllllllIllIIIIlIIlIIII.getBlocksAmount()));
         ScaledResolution lllllllllllllllllIllIIIIlIIlIIIl = new ScaledResolution(mc);
         RenderUtils.drawBorderedRect((float)(lllllllllllllllllIllIIIIlIIlIIIl.getScaledWidth() / 2 - 2), (float)(lllllllllllllllllIllIIIIlIIlIIIl.getScaledHeight() / 2 + 5), (float)(lllllllllllllllllIllIIIIlIIlIIIl.getScaledWidth() / 2 + Fonts.font40.getStringWidth(lllllllllllllllllIllIIIIlIIlIIlI) + 2), (float)(lllllllllllllllllIllIIIIlIIlIIIl.getScaledHeight() / 2 + 16), 3.0F, Color.BLACK.getRGB(), Color.BLACK.getRGB());
         GlStateManager.resetColor();
         Fonts.font40.drawString(lllllllllllllllllIllIIIIlIIlIIlI, lllllllllllllllllIllIIIIlIIlIIIl.getScaledWidth() / 2, lllllllllllllllllIllIIIIlIIlIIIl.getScaledHeight() / 2 + 7, Color.WHITE.getRGB());
         boolean var10001 = false;
         GlStateManager.popMatrix();
      }

   }

   private void move() {
      Exception lllllllllllllllllIllIIIlIIllIllI = ((String)lllllllllllllllllIllIIIlIIlllIII.modeValue.get()).toLowerCase();
      char lllllllllllllllllIllIIIlIIllIlIl = -1;
      switch(lllllllllllllllllIllIIIlIIllIllI.hashCode()) {
      case -1360201941:
         if (lllllllllllllllllIllIIIlIIllIllI.equals("teleport")) {
            lllllllllllllllllIllIIIlIIllIlIl = 5;
         }
         break;
      case -1068318794:
         if (lllllllllllllllllIllIIIlIIllIllI.equals("motion")) {
            lllllllllllllllllIllIIIlIIllIlIl = 2;
         }
         break;
      case -995865464:
         if (lllllllllllllllllIllIIIlIIllIllI.equals("packet")) {
            lllllllllllllllllIllIIIlIIllIlIl = 4;
         }
         break;
      case -157173582:
         if (lllllllllllllllllIllIIIlIIllIllI.equals("motiontp")) {
            lllllllllllllllllIllIIIlIIllIlIl = 3;
         }
         break;
      case 3273774:
         if (lllllllllllllllllIllIIIlIIllIllI.equals("jump")) {
            lllllllllllllllllIllIIIlIIllIlIl = 1;
         }
         break;
      case 325228192:
         if (lllllllllllllllllIllIIIlIIllIllI.equals("aac3.3.9")) {
            lllllllllllllllllIllIIIlIIllIlIl = 7;
         }
         break;
      case 792877146:
         if (lllllllllllllllllIllIIIlIIllIllI.equals("constantmotion")) {
            lllllllllllllllllIllIIIlIIllIlIl = 6;
         }
         break;
      case 1381910549:
         if (lllllllllllllllllIllIIIlIIllIllI.equals("hypixel")) {
            lllllllllllllllllIllIIIlIIllIlIl = 0;
         }
      }

      switch(lllllllllllllllllIllIIIlIIllIlIl) {
      case 0:
         if (lllllllllllllllllIllIIIlIIlllIII.getBlocksAmount() > 0) {
            double lllllllllllllllllIllIIIlIIllIlII;
            double lllllllllllllllllIllIIIlIIllllII;
            if (mc.thePlayer.moveForward == 0.0F && (double)mc.thePlayer.moveStrafing == 0.0D) {
               lllllllllllllllllIllIIIlIIllIlII = 0.4199992157122222D;
               lllllllllllllllllIllIIIlIIllllII = 0.42D;
               if (mc.thePlayer.isPotionActive(Potion.jump)) {
                  lllllllllllllllllIllIIIlIIllIlII += (double)((float)(mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F);
                  lllllllllllllllllIllIIIlIIllllII -= (double)(mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.075D;
               }

               if (mc.thePlayer.motionY < lllllllllllllllllIllIIIlIIllIlII * lllllllllllllllllIllIIIlIIllllII) {
                  mc.thePlayer.motionY = lllllllllllllllllIllIIIlIIllIlII;
               }
            } else {
               lllllllllllllllllIllIIIlIIllIlII = 0.4199992157122222D;
               lllllllllllllllllIllIIIlIIllllII = 0.42D;
               double lllllllllllllllllIllIIIlIIlllIll = 0.2873D;
               if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                  long lllllllllllllllllIllIIIlIIllIIIl = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
                  lllllllllllllllllIllIIIlIIlllIll *= 1.0D + 0.2D * (double)(lllllllllllllllllIllIIIlIIllIIIl + 1);
               }

               if (mc.thePlayer.isPotionActive(Potion.jump)) {
                  lllllllllllllllllIllIIIlIIllIlII += (double)((float)(mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F);
                  lllllllllllllllllIllIIIlIIllllII -= (double)(mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.075D;
                  double var11 = lllllllllllllllllIllIIIlIIlllIll - 0.05D * (double)(mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1);
               }

               if (mc.thePlayer.motionY < lllllllllllllllllIllIIIlIIllIlII * lllllllllllllllllIllIIIlIIllllII) {
                  mc.thePlayer.motionY = lllllllllllllllllIllIIIlIIllIlII;
               }

               if (mc.thePlayer.posY >= (double)Math.round(mc.thePlayer.posY) - 1.0E-4D && mc.thePlayer.posY <= (double)Math.round(mc.thePlayer.posY) + 1.0E-4D) {
                  mc.thePlayer.motionY = 0.0D;
               }
            }
         }
         break;
      case 1:
         if (mc.thePlayer.onGround && lllllllllllllllllIllIIIlIIlllIII.timer.hasTimePassed((Integer)lllllllllllllllllIllIIIlIIlllIII.jumpDelayValue.get())) {
            lllllllllllllllllIllIIIlIIlllIII.fakeJump();
            mc.thePlayer.motionY = (double)(Float)lllllllllllllllllIllIIIlIIlllIII.jumpMotionValue.get();
            lllllllllllllllllIllIIIlIIlllIII.timer.reset();
         }
         break;
      case 2:
         if (mc.thePlayer.onGround) {
            lllllllllllllllllIllIIIlIIlllIII.fakeJump();
            mc.thePlayer.motionY = 0.42D;
         } else if (mc.thePlayer.motionY < 0.1D) {
            mc.thePlayer.motionY = -0.3D;
         }
         break;
      case 3:
         if (mc.thePlayer.onGround) {
            lllllllllllllllllIllIIIlIIlllIII.fakeJump();
            mc.thePlayer.motionY = 0.42D;
         } else if (mc.thePlayer.motionY < 0.23D) {
            mc.thePlayer.setPosition(mc.thePlayer.posX, (double)((int)mc.thePlayer.posY), mc.thePlayer.posZ);
         }
         break;
      case 4:
         if (mc.thePlayer.onGround && lllllllllllllllllIllIIIlIIlllIII.timer.hasTimePassed(2)) {
            lllllllllllllllllIllIIIlIIlllIII.fakeJump();
            mc.getNetHandler().addToSendQueue(new C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.42D, mc.thePlayer.posZ, false));
            mc.getNetHandler().addToSendQueue(new C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.753D, mc.thePlayer.posZ, false));
            mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1.0D, mc.thePlayer.posZ);
            lllllllllllllllllIllIIIlIIlllIII.timer.reset();
         }
         break;
      case 5:
         if ((Boolean)lllllllllllllllllIllIIIlIIlllIII.teleportNoMotionValue.get()) {
            mc.thePlayer.motionY = 0.0D;
         }

         if ((mc.thePlayer.onGround || !(Boolean)lllllllllllllllllIllIIIlIIlllIII.teleportGroundValue.get()) && lllllllllllllllllIllIIIlIIlllIII.timer.hasTimePassed((Integer)lllllllllllllllllIllIIIlIIlllIII.teleportDelayValue.get())) {
            lllllllllllllllllIllIIIlIIlllIII.fakeJump();
            mc.thePlayer.setPositionAndUpdate(mc.thePlayer.posX, mc.thePlayer.posY + (double)(Float)lllllllllllllllllIllIIIlIIlllIII.teleportHeightValue.get(), mc.thePlayer.posZ);
            lllllllllllllllllIllIIIlIIlllIII.timer.reset();
         }
         break;
      case 6:
         if (mc.thePlayer.onGround) {
            lllllllllllllllllIllIIIlIIlllIII.fakeJump();
            lllllllllllllllllIllIIIlIIlllIII.jumpGround = mc.thePlayer.posY;
            mc.thePlayer.motionY = (double)(Float)lllllllllllllllllIllIIIlIIlllIII.constantMotionValue.get();
         }

         if (mc.thePlayer.posY > lllllllllllllllllIllIIIlIIlllIII.jumpGround + (double)(Float)lllllllllllllllllIllIIIlIIlllIII.constantMotionJumpGroundValue.get()) {
            lllllllllllllllllIllIIIlIIlllIII.fakeJump();
            mc.thePlayer.setPosition(mc.thePlayer.posX, (double)((int)mc.thePlayer.posY), mc.thePlayer.posZ);
            mc.thePlayer.motionY = (double)(Float)lllllllllllllllllIllIIIlIIlllIII.constantMotionValue.get();
            lllllllllllllllllIllIIIlIIlllIII.jumpGround = mc.thePlayer.posY;
         }
         break;
      case 7:
         if (mc.thePlayer.onGround) {
            lllllllllllllllllIllIIIlIIlllIII.fakeJump();
            mc.thePlayer.motionY = 0.4001D;
         }

         mc.timer.timerSpeed = 1.0F;
         if (mc.thePlayer.motionY < 0.0D) {
            EntityPlayerSP var10000 = mc.thePlayer;
            var10000.motionY -= 9.45E-6D;
            mc.timer.timerSpeed = 1.6F;
         }
      }

   }

   private int getBlocksAmount() {
      int lllllllllllllllllIllIIIIIllllIll = 0;

      for(int lllllllllllllllllIllIIIIIllllllI = 36; lllllllllllllllllIllIIIIIllllllI < 45; ++lllllllllllllllllIllIIIIIllllllI) {
         ItemStack lllllllllllllllllIllIIIIIlllllll = mc.thePlayer.inventoryContainer.getSlot(lllllllllllllllllIllIIIIIllllllI).getStack();
         if (lllllllllllllllllIllIIIIIlllllll != null && lllllllllllllllllIllIIIIIlllllll.getItem() instanceof ItemBlock) {
            lllllllllllllllllIllIIIIIllllIll += lllllllllllllllllIllIIIIIlllllll.stackSize;
         }
      }

      return lllllllllllllllllIllIIIIIllllIll;
   }

   public String getTag() {
      return (String)lllllllllllllllllIllIIIIIlllIlIl.modeValue.get();
   }

   private boolean search(BlockPos lllllllllllllllllIllIIIIllIlllll) {
      if (!BlockUtils.isReplaceable(lllllllllllllllllIllIIIIllIlllll)) {
         return false;
      } else {
         Vec3 lllllllllllllllllIllIIIIllIlllII = new Vec3(mc.thePlayer.posX, mc.thePlayer.getEntityBoundingBox().minY + (double)mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);
         float lllllllllllllllllIllIIIIllIlIllI = null;
         short lllllllllllllllllIllIIIIllIlIlIl = EnumFacing.values();
         char lllllllllllllllllIllIIIIllIlIlII = lllllllllllllllllIllIIIIllIlIlIl.length;

         for(int lllllllllllllllllIllIIIIllIlIIll = 0; lllllllllllllllllIllIIIIllIlIIll < lllllllllllllllllIllIIIIllIlIlII; ++lllllllllllllllllIllIIIIllIlIIll) {
            float lllllllllllllllllIllIIIIllIlIIlI = lllllllllllllllllIllIIIIllIlIlIl[lllllllllllllllllIllIIIIllIlIIll];
            float lllllllllllllllllIllIIIIllIlIIIl = lllllllllllllllllIllIIIIllIlllll.offset(lllllllllllllllllIllIIIIllIlIIlI);
            if (BlockUtils.canBeClicked(lllllllllllllllllIllIIIIllIlIIIl)) {
               int lllllllllllllllllIllIIIIllIlIIII = new Vec3(lllllllllllllllllIllIIIIllIlIIlI.getDirectionVec());

               for(double lllllllllllllllllIllIIIIlllIlIIl = 0.1D; lllllllllllllllllIllIIIIlllIlIIl < 0.9D; lllllllllllllllllIllIIIIlllIlIIl += 0.1D) {
                  for(double lllllllllllllllllIllIIIIllIIlllI = 0.1D; lllllllllllllllllIllIIIIllIIlllI < 0.9D; lllllllllllllllllIllIIIIllIIlllI += 0.1D) {
                     for(double lllllllllllllllllIllIIIIllIIllIl = 0.1D; lllllllllllllllllIllIIIIllIIllIl < 0.9D; lllllllllllllllllIllIIIIllIIllIl += 0.1D) {
                        byte lllllllllllllllllIllIIIIllIIllII = (new Vec3(lllllllllllllllllIllIIIIllIlllll)).addVector(lllllllllllllllllIllIIIIlllIlIIl, lllllllllllllllllIllIIIIllIIlllI, lllllllllllllllllIllIIIIllIIllIl);
                        double lllllllllllllllllIllIIIIllllIlll = lllllllllllllllllIllIIIIllIlllII.squareDistanceTo(lllllllllllllllllIllIIIIllIIllII);
                        long lllllllllllllllllIllIIIIllIIlIlI = lllllllllllllllllIllIIIIllIIllII.add(new Vec3(lllllllllllllllllIllIIIIllIlIIII.xCoord * 0.5D, lllllllllllllllllIllIIIIllIlIIII.yCoord * 0.5D, lllllllllllllllllIllIIIIllIlIIII.zCoord * 0.5D));
                        if (!(lllllllllllllllllIllIIIIllIlllII.squareDistanceTo(lllllllllllllllllIllIIIIllIIlIlI) > 18.0D) && !(lllllllllllllllllIllIIIIllllIlll > lllllllllllllllllIllIIIIllIlllII.squareDistanceTo(lllllllllllllllllIllIIIIllIIllII.add(lllllllllllllllllIllIIIIllIlIIII))) && mc.theWorld.rayTraceBlocks(lllllllllllllllllIllIIIIllIlllII, lllllllllllllllllIllIIIIllIIlIlI, false, true, false) == null) {
                           String lllllllllllllllllIllIIIIllIIlIIl = lllllllllllllllllIllIIIIllIIlIlI.xCoord - lllllllllllllllllIllIIIIllIlllII.xCoord;
                           String lllllllllllllllllIllIIIIllIIIlll = lllllllllllllllllIllIIIIllIIlIlI.yCoord - lllllllllllllllllIllIIIIllIlllII.yCoord;
                           double lllllllllllllllllIllIIIIllllIIll = lllllllllllllllllIllIIIIllIIlIlI.zCoord - lllllllllllllllllIllIIIIllIlllII.zCoord;
                           double lllllllllllllllllIllIIIIllllIIlI = (double)MathHelper.sqrt_double(lllllllllllllllllIllIIIIllIIlIIl * lllllllllllllllllIllIIIIllIIlIIl + lllllllllllllllllIllIIIIllllIIll * lllllllllllllllllIllIIIIllllIIll);
                           short lllllllllllllllllIllIIIIllIIIIlI = new Rotation(MathHelper.wrapAngleTo180_float((float)Math.toDegrees(Math.atan2(lllllllllllllllllIllIIIIllllIIll, lllllllllllllllllIllIIIIllIIlIIl)) - 90.0F), MathHelper.wrapAngleTo180_float((float)(-Math.toDegrees(Math.atan2(lllllllllllllllllIllIIIIllIIIlll, lllllllllllllllllIllIIIIllllIIlI)))));
                           Vec3 lllllllllllllllllIllIIIIllllIIII = RotationUtils.getVectorForRotation(lllllllllllllllllIllIIIIllIIIIlI);
                           Vec3 lllllllllllllllllIllIIIIlllIllll = lllllllllllllllllIllIIIIllIlllII.addVector(lllllllllllllllllIllIIIIllllIIII.xCoord * 4.0D, lllllllllllllllllIllIIIIllllIIII.yCoord * 4.0D, lllllllllllllllllIllIIIIllllIIII.zCoord * 4.0D);
                           MovingObjectPosition lllllllllllllllllIllIIIIlllIllIl = mc.theWorld.rayTraceBlocks(lllllllllllllllllIllIIIIllIlllII, lllllllllllllllllIllIIIIlllIllll, false, false, true);
                           if (lllllllllllllllllIllIIIIlllIllIl.typeOfHit == MovingObjectType.BLOCK && lllllllllllllllllIllIIIIlllIllIl.getBlockPos().equals(lllllllllllllllllIllIIIIllIlIIIl) && (lllllllllllllllllIllIIIIllIlIllI == null || RotationUtils.getRotationDifference(lllllllllllllllllIllIIIIllIIIIlI) < RotationUtils.getRotationDifference(lllllllllllllllllIllIIIIllIlIllI.getRotation()))) {
                              lllllllllllllllllIllIIIIllIlIllI = new PlaceRotation(new PlaceInfo(lllllllllllllllllIllIIIIllIlIIIl, lllllllllllllllllIllIIIIllIlIIlI.getOpposite(), lllllllllllllllllIllIIIIllIIlIlI), lllllllllllllllllIllIIIIllIIIIlI);
                           }
                        }
                     }
                  }
               }
            }
         }

         if (lllllllllllllllllIllIIIIllIlIllI == null) {
            return false;
         } else {
            if ((Boolean)lllllllllllllllllIllIIIIllIllIlI.rotationsValue.get()) {
               RotationUtils.setTargetRotation(lllllllllllllllllIllIIIIllIlIllI.getRotation(), 0);
               lllllllllllllllllIllIIIIllIllIlI.lockRotation = lllllllllllllllllIllIIIIllIlIllI.getRotation();
            }

            lllllllllllllllllIllIIIIllIllIlI.placeInfo = lllllllllllllllllIllIIIIllIlIllI.getPlaceInfo();
            return true;
         }
      }
   }

   @EventTarget
   public void onPacket(PacketEvent lllllllllllllllllIllIIIIlIlIllIl) {
      if (mc.thePlayer != null) {
         Packet<?> lllllllllllllllllIllIIIIlIlIIlll = lllllllllllllllllIllIIIIlIlIllIl.getPacket();
         if (lllllllllllllllllIllIIIIlIlIIlll instanceof C09PacketHeldItemChange) {
            C09PacketHeldItemChange lllllllllllllllllIllIIIIlIllIIII = (C09PacketHeldItemChange)lllllllllllllllllIllIIIIlIlIIlll;
            lllllllllllllllllIllIIIIlIlIlIlI.slot = lllllllllllllllllIllIIIIlIllIIII.getSlotId();
         }

      }
   }

   private void place() {
      if (lllllllllllllllllIllIIIlIIlIlIlI.placeInfo != null) {
         int lllllllllllllllllIllIIIlIIlIllII = -1;
         double lllllllllllllllllIllIIIlIIlIlIII = mc.thePlayer.getHeldItem();
         if (mc.thePlayer.getHeldItem() == null || !(mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock)) {
            if (!(Boolean)lllllllllllllllllIllIIIlIIlIlIlI.autoBlockValue.get()) {
               return;
            }

            lllllllllllllllllIllIIIlIIlIllII = InventoryUtils.findAutoBlockBlock();
            if (lllllllllllllllllIllIIIlIIlIllII == -1) {
               return;
            }

            mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(lllllllllllllllllIllIIIlIIlIllII - 36));
            lllllllllllllllllIllIIIlIIlIlIII = mc.thePlayer.inventoryContainer.getSlot(lllllllllllllllllIllIIIlIIlIllII).getStack();
         }

         if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, lllllllllllllllllIllIIIlIIlIlIII, lllllllllllllllllIllIIIlIIlIlIlI.placeInfo.getBlockPos(), lllllllllllllllllIllIIIlIIlIlIlI.placeInfo.getEnumFacing(), lllllllllllllllllIllIIIlIIlIlIlI.placeInfo.getVec3())) {
            if ((Boolean)lllllllllllllllllIllIIIlIIlIlIlI.swingValue.get()) {
               mc.thePlayer.swingItem();
            } else {
               mc.getNetHandler().addToSendQueue(new C0APacketAnimation());
            }
         }

         lllllllllllllllllIllIIIlIIlIlIlI.placeInfo = null;
         if (!(Boolean)lllllllllllllllllIllIIIlIIlIlIlI.stayAutoBlock.get() && lllllllllllllllllIllIIIlIIlIllII >= 0) {
            mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
         }

      }
   }

   @EventTarget
   public void onJump(JumpEvent lllllllllllllllllIllIIIIlIIIIIll) {
      if ((Boolean)lllllllllllllllllIllIIIIlIIIIllI.onJumpValue.get()) {
         lllllllllllllllllIllIIIIlIIIIIll.cancelEvent();
      }

   }
}
