//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.world;

import java.awt.Color;
import java.awt.List;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.StrafeEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.InventoryUtils;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.PlaceRotation;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.block.PlaceInfo;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCarpet;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.BlockSnow;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0BPacketEntityAction.Action;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import org.lwjgl.input.Keyboard;

@ModuleInfo(
   name = "BlockFly",
   description = "Automatically places blocks beneath your feet.",
   category = ModuleCategory.WORLD,
   keyBind = 23
)
public class Scaffold extends Module {
   // $FF: synthetic field
   private final FloatValue minTurnSpeedValue;
   // $FF: synthetic field
   private final FloatValue yRangeValue;
   // $FF: synthetic field
   public final BoolValue rotationStrafeValue;
   // $FF: synthetic field
   private final BoolValue airSafeValue;
   // $FF: synthetic field
   private boolean eagleSneaking;
   // $FF: synthetic field
   public final ListValue modeValue = new ListValue("Mode", new String[]{"Normal", "Rewinside", "Expand"}, "Normal");
   // $FF: synthetic field
   private final IntegerValue maxDelayValue;
   // $FF: synthetic field
   private final FloatValue xzRangeValue;
   // $FF: synthetic field
   private final MSTimer zitterTimer;
   // $FF: synthetic field
   private final BoolValue down;
   // $FF: synthetic field
   private Rotation limitedRotation;
   // $FF: synthetic field
   private boolean facesBlock;
   // $FF: synthetic field
   private final BoolValue keepRotationValue;
   // $FF: synthetic field
   private final BoolValue stayAutoBlock;
   // $FF: synthetic field
   private final ListValue zitterModeValue;
   // $FF: synthetic field
   private final BoolValue safeWalkValue;
   // $FF: synthetic field
   private final FloatValue zitterStrength;
   // $FF: synthetic field
   private final BoolValue eagleValue;
   // $FF: synthetic field
   private int placedBlocksWithoutEagle;
   // $FF: synthetic field
   private final FloatValue speedModifierValue;
   // $FF: synthetic field
   private int launchY;
   // $FF: synthetic field
   private final BoolValue counterDisplayValue;
   // $FF: synthetic field
   private PlaceInfo targetPlace;
   // $FF: synthetic field
   private final FloatValue staticYawOffsetValue;
   // $FF: synthetic field
   private final IntegerValue blocksToEagleValue;
   // $FF: synthetic field
   private final FloatValue staticPitchValue;
   // $FF: synthetic field
   private final IntegerValue expandLengthValue;
   // $FF: synthetic field
   private final BoolValue zitterValue;
   // $FF: synthetic field
   private final MSTimer delayTimer;
   // $FF: synthetic field
   public final ListValue rotationModeValue;
   // $FF: synthetic field
   private final FloatValue zitterSpeed;
   // $FF: synthetic field
   private final BoolValue tower;
   // $FF: synthetic field
   private final ListValue render;
   // $FF: synthetic field
   private final ListValue placeModeValue;
   // $FF: synthetic field
   private final BoolValue autoBlockValue;
   // $FF: synthetic field
   private long delay;
   // $FF: synthetic field
   private int slot;
   // $FF: synthetic field
   private static List blacklistedBlocks;
   // $FF: synthetic field
   private final BoolValue sameYValue;
   // $FF: synthetic field
   private final IntegerValue minDelayValue;
   // $FF: synthetic field
   private final BoolValue hyprotation;
   // $FF: synthetic field
   private boolean zitterDirection;
   // $FF: synthetic field
   private final BoolValue searchValue;
   // $FF: synthetic field
   private final IntegerValue keepLengthValue;
   // $FF: synthetic field
   public BlockData blockdata;
   // $FF: synthetic field
   private final IntegerValue searchAccuracyValue;
   // $FF: synthetic field
   private final FloatValue maxTurnSpeedValue;
   // $FF: synthetic field
   private final BoolValue picker;
   // $FF: synthetic field
   private final BoolValue eagleSilentValue;
   // $FF: synthetic field
   private final FloatValue timerValue;
   // $FF: synthetic field
   private Rotation lockRotation;
   // $FF: synthetic field
   private final BoolValue placeableDelay;
   // $FF: synthetic field
   public final BoolValue sprintValue;
   // $FF: synthetic field
   private final BoolValue swingValue;

   public void onDisable() {
      LiquidBounce.moduleManager.getModule(Tower.class).setState(false);
      if (mc.thePlayer != null) {
         if (!GameSettings.isKeyDown(mc.gameSettings.keyBindSneak)) {
            mc.gameSettings.keyBindSneak.pressed = false;
            if (llIlIlIlIIlII.eagleSneaking) {
               mc.getNetHandler().addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, Action.STOP_SNEAKING));
            }
         }

         if (!GameSettings.isKeyDown(mc.gameSettings.keyBindRight)) {
            mc.gameSettings.keyBindRight.pressed = false;
         }

         if (!GameSettings.isKeyDown(mc.gameSettings.keyBindLeft)) {
            mc.gameSettings.keyBindLeft.pressed = false;
         }

         llIlIlIlIIlII.lockRotation = null;
         llIlIlIlIIlII.limitedRotation = null;
         llIlIlIlIIlII.facesBlock = false;
         mc.timer.timerSpeed = 1.0F;
         if (llIlIlIlIIlII.slot != mc.thePlayer.inventory.currentItem) {
            mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
         }

      }
   }

   private boolean hotbarContainBlock() {
      int llIIlIlllllIl = 36;

      while(llIIlIlllllIl < 45) {
         try {
            ItemStack llIIllIIIIIII = mc.thePlayer.inventoryContainer.getSlot(llIIlIlllllIl).getStack();
            if (llIIllIIIIIII != null && llIIllIIIIIII.getItem() != null && llIIllIIIIIII.getItem() instanceof ItemBlock) {
               return true;
            }

            ++llIIlIlllllIl;
         } catch (Exception var4) {
         }
      }

      return false;
   }

   private int getBlocksAmount() {
      long llIIllIIIIlIl = 0;

      for(int llIIllIIIlIII = 36; llIIllIIIlIII < 45; ++llIIllIIIlIII) {
         ItemStack llIIllIIIlIIl = mc.thePlayer.inventoryContainer.getSlot(llIIllIIIlIII).getStack();
         if (llIIllIIIlIIl != null && llIIllIIIlIIl.getItem() instanceof ItemBlock) {
            llIIllIIIIlIl += llIIllIIIlIIl.stackSize;
         }
      }

      return llIIllIIIIlIl;
   }

   public Scaffold() {
      llIllIlIIIlII.maxDelayValue = new IntegerValue("MaxDelay", 0, 0, 1000) {
         protected void onChanged(Integer lIIIIIlllIl, Integer lIIIIIlllII) {
            int lIIIIIllIll = (Integer)llIllIlIIIlII.minDelayValue.get();
            if (lIIIIIllIll > lIIIIIlllII) {
               lIIIIIllllI.set(lIIIIIllIll);
            }

         }
      };
      llIllIlIIIlII.minDelayValue = new IntegerValue("MinDelay", 0, 0, 1000) {
         protected void onChanged(Integer lIlllIlIllIllII, Integer lIlllIlIllIlIll) {
            int lIlllIlIllIlIlI = (Integer)llIllIlIIIlII.maxDelayValue.get();
            if (lIlllIlIllIlIlI < lIlllIlIllIlIll) {
               lIlllIlIllIlIIl.set(lIlllIlIllIlIlI);
            }

         }
      };
      llIllIlIIIlII.placeableDelay = new BoolValue("PlaceableDelay", false);
      llIllIlIIIlII.autoBlockValue = new BoolValue("AutoBlock", true);
      llIllIlIIIlII.stayAutoBlock = new BoolValue("StayAutoBlock", false);
      llIllIlIIIlII.sprintValue = new BoolValue("Sprint", true);
      llIllIlIIIlII.swingValue = new BoolValue("Swing", true);
      llIllIlIIIlII.searchValue = new BoolValue("Search", true);
      llIllIlIIIlII.placeModeValue = new ListValue("PlaceTiming", new String[]{"Pre", "Post"}, "Post");
      llIllIlIIIlII.picker = new BoolValue("Picker", false);
      llIllIlIIIlII.eagleValue = new BoolValue("Eagle", false);
      llIllIlIIIlII.eagleSilentValue = new BoolValue("EagleSilent", false);
      llIllIlIIIlII.blocksToEagleValue = new IntegerValue("BlocksToEagle", 0, 0, 10);
      llIllIlIIIlII.expandLengthValue = new IntegerValue("ExpandLength", 5, 1, 6);
      llIllIlIIIlII.rotationModeValue = new ListValue("Rotations", new String[]{"None", "Normal", "AAC4", "StaticPitch", "Hypixel"}, "Normal");
      llIllIlIIIlII.hyprotation = new BoolValue("HypixelRotation", false);
      llIllIlIIIlII.rotationStrafeValue = new BoolValue("RotationStrafe", false) {
         protected void onChanged(Boolean lIlIlIllIlIIIII, Boolean lIlIlIllIIlllll) {
            if (lIlIlIllIIlllll) {
               llIllIlIIIlII.keepRotationValue.set(true);
            }

         }
      };
      llIllIlIIIlII.maxTurnSpeedValue = new FloatValue("MaxTurnSpeed", 180.0F, 1.0F, 180.0F) {
         protected void onChanged(Float lIlIIlIlll, Float lIlIIlIlIl) {
            short lIlIIIlIll = (Float)llIllIlIIIlII.minTurnSpeedValue.get();
            if (lIlIIIlIll > lIlIIlIlIl) {
               lIlIIlIIIl.set(lIlIIIlIll);
            }

            if (lIlIIlIIIl.getMaximum() < lIlIIlIlIl) {
               lIlIIlIIIl.set(lIlIIlIIIl.getMaximum());
            } else if (lIlIIlIIIl.getMinimum() > lIlIIlIlIl) {
               lIlIIlIIIl.set(lIlIIlIIIl.getMinimum());
            }

         }
      };
      llIllIlIIIlII.minTurnSpeedValue = new FloatValue("MinTurnSpeed", 180.0F, 1.0F, 180.0F) {
         protected void onChanged(Float llllIlllllIlllI, Float llllIlllllIlIlI) {
            float llllIlllllIllII = (Float)llIllIlIIIlII.maxTurnSpeedValue.get();
            if (llllIlllllIllII < llllIlllllIlIlI) {
               llllIlllllIllll.set(llllIlllllIllII);
            }

            if (llllIlllllIllll.getMaximum() < llllIlllllIlIlI) {
               llllIlllllIllll.set(llllIlllllIllll.getMaximum());
            } else if (llllIlllllIllll.getMinimum() > llllIlllllIlIlI) {
               llllIlllllIllll.set(llllIlllllIllll.getMinimum());
            }

         }
      };
      llIllIlIIIlII.staticPitchValue = new FloatValue("StaticPitch", 86.0F, 0.0F, 90.0F);
      llIllIlIIIlII.staticYawOffsetValue = new FloatValue("AAC4Offset", 0.0F, 0.0F, 90.0F);
      llIllIlIIIlII.keepLengthValue = new IntegerValue("KeepRotationLength", 0, 0, 20);
      llIllIlIIIlII.keepRotationValue = new BoolValue("KeepRotation", false);
      llIllIlIIIlII.xzRangeValue = new FloatValue("SearchXZ", 0.8F, 0.0F, 1.0F);
      llIllIlIIIlII.yRangeValue = new FloatValue("SearchY", 0.8F, 0.0F, 1.0F);
      llIllIlIIIlII.searchAccuracyValue = new IntegerValue("SearchValue", 8, 0, 16) {
         protected void onChanged(Integer lllIIIIlIlII, Integer lllIIIIlIIll) {
            if (lllIIIIlIlIl.getMaximum() < lllIIIIlIIll) {
               lllIIIIlIlIl.set(lllIIIIlIlIl.getMaximum());
            } else if (lllIIIIlIlIl.getMinimum() > lllIIIIlIIll) {
               lllIIIIlIlIl.set(lllIIIIlIlIl.getMinimum());
            }

         }
      };
      llIllIlIIIlII.zitterValue = new BoolValue("Zitter", false);
      llIllIlIIIlII.zitterModeValue = new ListValue("ZitterMode", new String[]{"Teleport", "Smooth"}, "Teleport");
      llIllIlIIIlII.zitterSpeed = new FloatValue("ZitterSpeed", 0.13F, 0.1F, 0.3F);
      llIllIlIIIlII.zitterStrength = new FloatValue("ZitterStrength", 0.072F, 0.05F, 0.2F);
      llIllIlIIIlII.render = new ListValue("Render", new String[]{"None", "Mark", "DrawCircle"}, "None");
      llIllIlIIIlII.timerValue = new FloatValue("Timer", 1.0F, 0.1F, 10.0F);
      llIllIlIIIlII.speedModifierValue = new FloatValue("SpeedModifier", 1.0F, 0.0F, 2.0F);
      llIllIlIIIlII.sameYValue = new BoolValue("SameY", false);
      llIllIlIIIlII.safeWalkValue = new BoolValue("SafeWalk", true);
      llIllIlIIIlII.airSafeValue = new BoolValue("AirSafe", false);
      llIllIlIIIlII.counterDisplayValue = new BoolValue("Counter", true);
      llIllIlIIIlII.tower = new BoolValue("Tower", true);
      llIllIlIIIlII.down = new BoolValue("Down", true);
      llIllIlIIIlII.facesBlock = false;
      llIllIlIIIlII.delayTimer = new MSTimer();
      llIllIlIIIlII.zitterTimer = new MSTimer();
      llIllIlIIIlII.placedBlocksWithoutEagle = 0;
   }

   @EventTarget
   public void onMotion(MotionEvent llIlIllIlIIll) {
      BlockPos llIlIllIllIII = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ);
      BlockData llIlIllIlIlll = llIlIllIllIlI.getBlockData(llIlIllIllIII);
      if (llIlIllIllIlI.getBlocksAmount() == 0) {
         mc.thePlayer.addChatMessage(new ChatComponentText("§d没方�?�你�?�你妈个逼?傻逼"));
      }

      if ((Boolean)llIlIllIllIlI.hyprotation.get() && (Boolean)llIlIllIllIlI.keepRotationValue.get() && llIlIllIllIlI.lockRotation != null && ((String)llIlIllIllIlI.rotationModeValue.get()).equalsIgnoreCase("Hypixel")) {
         RotationUtils.setTargetRotation(llIlIllIllIlI.lockRotation);
      }

      if (((String)llIlIllIllIlI.rotationModeValue.get()).equalsIgnoreCase("Normal") && (Boolean)llIlIllIllIlI.keepRotationValue.get() && llIlIllIllIlI.lockRotation != null) {
         RotationUtils.setTargetRotation(llIlIllIllIlI.lockRotation);
      }

      if ((((String)llIlIllIllIlI.rotationModeValue.get()).equalsIgnoreCase("AAC4") || ((String)llIlIllIllIlI.rotationModeValue.get()).equalsIgnoreCase("StaticPitch")) && (Boolean)llIlIllIllIlI.keepRotationValue.get() && llIlIllIllIlI.lockRotation != null) {
         llIlIllIllIlI.setRotation(llIlIllIllIlI.lockRotation);
      }

      String llIlIllIlIllI = (String)llIlIllIllIlI.modeValue.get();
      String llIlIllIIllll = llIlIllIlIIll.getEventState();
      if (llIlIllIllIlI.facesBlock || ((String)llIlIllIllIlI.placeModeValue.get()).equalsIgnoreCase(llIlIllIIllll.getStateName())) {
         if ((Boolean)llIlIllIllIlI.down.get()) {
            if (mc.gameSettings.keyBindSprint.isKeyDown()) {
               mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem(), llIlIllIlIlll.position, llIlIllIlIlll.face, getVec3(llIlIllIlIlll.position, llIlIllIlIlll.face));
               boolean var10001 = false;
            } else {
               llIlIllIllIlI.place();
            }
         } else {
            llIlIllIllIlI.place();
         }
      }

      if (llIlIllIIllll == EventState.PRE) {
         if ((Boolean)llIlIllIllIlI.autoBlockValue.get()) {
            if (InventoryUtils.findAutoBlockBlock() == -1) {
               return;
            }
         } else if (mc.thePlayer.getHeldItem() == null || !(mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock)) {
            return;
         }

         llIlIllIllIlI.findBlock(llIlIllIlIllI.equalsIgnoreCase("expand"));
      }

      if (llIlIllIllIlI.targetPlace == null && (Boolean)llIlIllIllIlI.placeableDelay.get()) {
         llIlIllIllIlI.delayTimer.reset();
      }

   }

   private double calcStepSize(double llIIllIIlIIIl) {
      double llIIllIIlIIII = (double)(Integer)llIIllIIIllll.searchAccuracyValue.get();
      llIIllIIlIIII += llIIllIIlIIII % 2.0D;
      return llIIllIIlIIIl / llIIllIIlIIII < 0.01D ? 0.01D : llIIllIIlIIIl / llIIllIIlIIII;
   }

   private void setRotation(Rotation llIlIllIIlIll, int llIlIllIIlIlI) {
      RotationUtils.setTargetRotation(llIlIllIIlIll, llIlIllIIlIlI);
   }

   private void setRotation(Rotation llIlIllIIIlII) {
      llIlIllIIIlIl.setRotation(llIlIllIIIlII, 0);
   }

   public static List getBlacklistedBlocks() {
      return blacklistedBlocks;
   }

   protected void swap(int llIIlIllIIllI, int llIIlIllIIIll) {
      mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, llIIlIllIIllI, llIIlIllIIIll, 2, mc.thePlayer);
      boolean var10001 = false;
   }

   @EventTarget
   public void onRender3D(Render3DEvent llIlIlIIIIlII) {
      RenderManager llIlIlIIIIIll = mc.getRenderManager();
      Exception llIlIIlllllll = mc.timer;
      if (((String)llIlIlIIIIIIl.render.get()).equalsIgnoreCase("Mark")) {
         for(int llIlIIllllllI = 0; llIlIIllllllI < (((String)llIlIlIIIIIIl.modeValue.get()).equalsIgnoreCase("Expand") ? (Integer)llIlIlIIIIIIl.expandLengthValue.get() + 1 : 2); ++llIlIIllllllI) {
            BlockPos llIlIlIIIlIll = new BlockPos(mc.thePlayer.posX + (double)(mc.thePlayer.getHorizontalFacing() == EnumFacing.WEST ? -llIlIIllllllI : (mc.thePlayer.getHorizontalFacing() == EnumFacing.EAST ? llIlIIllllllI : 0)), mc.thePlayer.posY - (mc.thePlayer.posY == (double)((int)mc.thePlayer.posY) + 0.5D ? 0.0D : 1.0D), mc.thePlayer.posZ + (double)(mc.thePlayer.getHorizontalFacing() == EnumFacing.NORTH ? -llIlIIllllllI : (mc.thePlayer.getHorizontalFacing() == EnumFacing.SOUTH ? llIlIIllllllI : 0)));
            PlaceInfo llIlIlIIIlIlI = PlaceInfo.get(llIlIlIIIlIll);
            if (BlockUtils.isReplaceable(llIlIlIIIlIll) && llIlIlIIIlIlI != null) {
               RenderUtils.drawBlockBox(llIlIlIIIlIll, new Color(68, 117, 255, 100), false);
               break;
            }
         }
      }

      if (((String)llIlIlIIIIIIl.render.get()).equalsIgnoreCase("DrawCircle")) {
         float llIlIIllllllI = mc.thePlayer.lastTickPosX + (mc.thePlayer.posX - mc.thePlayer.lastTickPosX) * (double)llIlIIlllllll.renderPartialTicks - llIlIlIIIIIll.renderPosX;
         double llIlIlIIIIlll = mc.thePlayer.lastTickPosY + (mc.thePlayer.posY - mc.thePlayer.lastTickPosY) * (double)llIlIIlllllll.renderPartialTicks - llIlIlIIIIIll.renderPosY;
         float llIlIIllllIll = mc.thePlayer.lastTickPosZ + (mc.thePlayer.posZ - mc.thePlayer.lastTickPosZ) * (double)llIlIIlllllll.renderPartialTicks - llIlIlIIIIIll.renderPosZ;
         RenderUtils.shadow(mc.thePlayer, llIlIIllllllI, llIlIlIIIIlll, llIlIIllllIll, 1.0D, 64);
         RenderUtils.cylinder(mc.thePlayer, llIlIIllllllI, llIlIlIIIIlll, llIlIIllllIll, 1.0D, 64);
      }

   }

   public static double randomNumber(double llIllIIIIIIll, double llIllIIIIIIlI) {
      return Math.random() * (llIllIIIIIIll - llIllIIIIIIlI) + llIllIIIIIIlI;
   }

   public int getBiggestBlockSlotHotbar() {
      long llIIlIllIlllI = -1;
      char llIIlIllIllIl = 0;
      if (llIIlIlllIIlI.getBlocksAmount() == 0) {
         return -1;
      } else {
         for(int llIIlIlllIIll = 36; llIIlIlllIIll < 45; ++llIIlIlllIIll) {
            if (mc.thePlayer.inventoryContainer.getSlot(llIIlIlllIIll).getHasStack()) {
               Item llIIlIlllIlIl = mc.thePlayer.inventoryContainer.getSlot(llIIlIlllIIll).getStack().getItem();
               ItemStack llIIlIlllIlII = mc.thePlayer.inventoryContainer.getSlot(llIIlIlllIIll).getStack();
               if (llIIlIlllIlIl instanceof ItemBlock && llIIlIlllIlII.stackSize > llIIlIllIllIl) {
                  llIIlIllIllIl = llIIlIlllIlII.stackSize;
                  llIIlIllIlllI = llIIlIlllIIll;
               }
            }
         }

         return llIIlIllIlllI;
      }
   }

   private void findBlock(boolean llIlIlIllIlIl) {
      boolean llIlIlIllIlII = mc.thePlayer.posY == (double)((int)mc.thePlayer.posY) + 0.5D ? new BlockPos(mc.thePlayer) : (new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ)).down();
      if (llIlIlIllIlIl || BlockUtils.isReplaceable(llIlIlIllIlII) && !llIlIlIllIllI.search(llIlIlIllIlII, true)) {
         int llIlIlIlllIlI;
         if (llIlIlIllIlIl) {
            for(llIlIlIlllIlI = 0; llIlIlIlllIlI < (Integer)llIlIlIllIllI.expandLengthValue.get(); ++llIlIlIlllIlI) {
               if (llIlIlIllIllI.search(llIlIlIllIlII.add(mc.thePlayer.getHorizontalFacing() == EnumFacing.WEST ? -llIlIlIlllIlI : (mc.thePlayer.getHorizontalFacing() == EnumFacing.EAST ? llIlIlIlllIlI : 0), 0, mc.thePlayer.getHorizontalFacing() == EnumFacing.NORTH ? -llIlIlIlllIlI : (mc.thePlayer.getHorizontalFacing() == EnumFacing.SOUTH ? llIlIlIlllIlI : 0)), false)) {
                  return;
               }
            }
         } else if ((Boolean)llIlIlIllIllI.searchValue.get()) {
            for(llIlIlIlllIlI = -1; llIlIlIlllIlI <= 1; ++llIlIlIlllIlI) {
               for(int llIlIlIlllIll = -1; llIlIlIlllIll <= 1; ++llIlIlIlllIll) {
                  if (llIlIlIllIllI.search(llIlIlIllIlII.add(llIlIlIlllIlI, 0, llIlIlIlllIll), true)) {
                     return;
                  }
               }
            }
         }

      }
   }

   @EventTarget
   public void onUpdate(UpdateEvent llIllIIIlIlIl) {
      llIllIIIlIlII.getBestBlocks();
      mc.timer.timerSpeed = (Float)llIllIIIlIlII.timerValue.get();
      if ((Boolean)llIllIIIlIlII.tower.get() && Keyboard.isKeyDown(57) && !LiquidBounce.moduleManager.getModule(Speed.class).getState()) {
         mc.thePlayer.onGround = false;
         LiquidBounce.moduleManager.getModule(Tower.class).setState(true);
      } else {
         LiquidBounce.moduleManager.getModule(Tower.class).setState(false);
      }

      if (mc.thePlayer.onGround) {
         byte llIllIIIlIIll = (String)llIllIIIlIlII.modeValue.get();
         if (llIllIIIlIIll.equalsIgnoreCase("Rewinside")) {
            MovementUtils.strafe(0.2F);
            mc.thePlayer.motionY = 0.0D;
         }

         if ((Boolean)llIllIIIlIlII.zitterValue.get() && ((String)llIllIIIlIlII.zitterModeValue.get()).equalsIgnoreCase("smooth")) {
            if (!GameSettings.isKeyDown(mc.gameSettings.keyBindRight)) {
               mc.gameSettings.keyBindRight.pressed = false;
            }

            if (!GameSettings.isKeyDown(mc.gameSettings.keyBindLeft)) {
               mc.gameSettings.keyBindLeft.pressed = false;
            }

            if (llIllIIIlIlII.zitterTimer.hasTimePassed(100L)) {
               llIllIIIlIlII.zitterDirection = !llIllIIIlIlII.zitterDirection;
               llIllIIIlIlII.zitterTimer.reset();
            }

            if (llIllIIIlIlII.zitterDirection) {
               mc.gameSettings.keyBindRight.pressed = true;
               mc.gameSettings.keyBindLeft.pressed = false;
            } else {
               mc.gameSettings.keyBindRight.pressed = false;
               mc.gameSettings.keyBindLeft.pressed = true;
            }
         }

         if ((Boolean)llIllIIIlIlII.eagleValue.get()) {
            if (llIllIIIlIlII.placedBlocksWithoutEagle >= (Integer)llIllIIIlIlII.blocksToEagleValue.get()) {
               char llIllIIIlIIlI = mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ)).getBlock() == Blocks.air;
               if ((Boolean)llIllIIIlIlII.eagleSilentValue.get()) {
                  if (llIllIIIlIlII.eagleSneaking != llIllIIIlIIlI) {
                     mc.getNetHandler().addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, llIllIIIlIIlI ? Action.START_SNEAKING : Action.STOP_SNEAKING));
                  }

                  llIllIIIlIlII.eagleSneaking = llIllIIIlIIlI;
               } else {
                  mc.gameSettings.keyBindSneak.pressed = llIllIIIlIIlI;
               }

               llIllIIIlIlII.placedBlocksWithoutEagle = 0;
            } else {
               ++llIllIIIlIlII.placedBlocksWithoutEagle;
            }
         }

         if ((Boolean)llIllIIIlIlII.zitterValue.get() && ((String)llIllIIIlIlII.zitterModeValue.get()).equalsIgnoreCase("teleport")) {
            MovementUtils.strafe((Float)llIllIIIlIlII.zitterSpeed.get());
            char llIllIIIlIIlI = Math.toRadians((double)mc.thePlayer.rotationYaw + (llIllIIIlIlII.zitterDirection ? 90.0D : -90.0D));
            EntityPlayerSP var10000 = mc.thePlayer;
            var10000.motionX -= Math.sin(llIllIIIlIIlI) * (double)(Float)llIllIIIlIlII.zitterStrength.get();
            var10000 = mc.thePlayer;
            var10000.motionZ += Math.cos(llIllIIIlIIlI) * (double)(Float)llIllIIIlIlII.zitterStrength.get();
            llIllIIIlIlII.zitterDirection = !llIllIIIlIlII.zitterDirection;
         }
      }

   }

   private boolean isPosSolid(BlockPos llIlIlllIIlll) {
      Block llIlIlllIIllI = mc.theWorld.getBlockState(llIlIlllIIlll).getBlock();
      return (llIlIlllIIllI.getMaterial().isSolid() || !llIlIlllIIllI.isTranslucent() || llIlIlllIIllI instanceof BlockLadder || llIlIlllIIllI instanceof BlockCarpet || llIlIlllIIllI instanceof BlockSnow || llIlIlllIIllI instanceof BlockSkull) && !llIlIlllIIllI.getMaterial().isLiquid() && !(llIlIlllIIllI instanceof BlockContainer);
   }

   public void getBestBlocks() {
      if (llIIlIIllIlII.getBlocksAmount() != 0) {
         int llIIlIIllIllI;
         int llIIlIIllIlll;
         int llIIlIIlIlllI;
         if ((Boolean)llIIlIIllIlII.picker.get()) {
            new ItemStack(Item.getItemById(261));
            llIIlIIllIllI = llIIlIIllIlII.getBiggestBlockSlotInv();
            int llIIlIIllllII = llIIlIIllIlII.getBiggestBlockSlotHotbar();
            llIIlIIllIlll = llIIlIIllIlII.getBiggestBlockSlotHotbar() > 0 ? llIIlIIllIlII.getBiggestBlockSlotHotbar() : llIIlIIllIlII.getBiggestBlockSlotInv();
            llIIlIIlIlllI = 42;
            if (llIIlIIllllII > 0 && llIIlIIllIllI > 0 && mc.thePlayer.inventoryContainer.getSlot(llIIlIIllIllI).getHasStack() && mc.thePlayer.inventoryContainer.getSlot(llIIlIIllllII).getHasStack() && mc.thePlayer.inventoryContainer.getSlot(llIIlIIllllII).getStack().stackSize < mc.thePlayer.inventoryContainer.getSlot(llIIlIIllIllI).getStack().stackSize) {
               llIIlIIllIlll = llIIlIIllIllI;
            }

            int llIIlIIlIllIl;
            if (llIIlIIllIlII.hotbarContainBlock()) {
               for(llIIlIIlIllIl = 36; llIIlIIlIllIl < 45; ++llIIlIIlIllIl) {
                  if (mc.thePlayer.inventoryContainer.getSlot(llIIlIIlIllIl).getHasStack()) {
                     float llIIlIIlIllII = mc.thePlayer.inventoryContainer.getSlot(llIIlIIlIllIl).getStack().getItem();
                     if (llIIlIIlIllII instanceof ItemBlock) {
                        llIIlIIlIlllI = llIIlIIlIllIl;
                        break;
                     }
                  }
               }
            } else {
               for(llIIlIIlIllIl = 36; llIIlIIlIllIl < 45; ++llIIlIIlIllIl) {
                  if (!mc.thePlayer.inventoryContainer.getSlot(llIIlIIlIllIl).getHasStack()) {
                     llIIlIIlIlllI = llIIlIIlIllIl;
                     break;
                  }
               }
            }

            if (mc.thePlayer.inventoryContainer.getSlot(llIIlIIlIlllI).slotNumber != llIIlIIllIlll) {
               llIIlIIllIlII.swap(llIIlIIllIlll, llIIlIIlIlllI - 36);
               mc.playerController.updateController();
            }
         } else if (llIIlIIllIlII.invCheck()) {
            boolean llIIlIIllIIlI = new ItemStack(Item.getItemById(261));

            for(llIIlIIllIllI = 9; llIIlIIllIllI < 36; ++llIIlIIllIllI) {
               if (mc.thePlayer.inventoryContainer.getSlot(llIIlIIllIllI).getHasStack()) {
                  float llIIlIIllIIII = mc.thePlayer.inventoryContainer.getSlot(llIIlIIllIllI).getStack().getItem();
                  llIIlIIllIlll = 0;
                  if (llIIlIIllIIII instanceof ItemBlock) {
                     for(llIIlIIlIlllI = 36; llIIlIIlIlllI < 45; ++llIIlIIlIlllI) {
                        Container var10000 = mc.thePlayer.inventoryContainer;
                        boolean var10001 = false;
                        if (Container.canAddItemToSlot(mc.thePlayer.inventoryContainer.getSlot(llIIlIIlIlllI), llIIlIIllIIlI, true)) {
                           llIIlIIllIlII.swap(llIIlIIllIllI, llIIlIIlIlllI - 36);
                           ++llIIlIIllIlll;
                           break;
                        }
                     }

                     if (llIIlIIllIlll == 0) {
                        llIIlIIllIlII.swap(llIIlIIllIllI, 7);
                     }
                     break;
                  }
               }
            }
         }

      }
   }

   @EventTarget
   public void onRender2D(Render2DEvent llIlIlIIlIllI) {
      if ((Boolean)llIlIlIIlIlll.counterDisplayValue.get()) {
         GlStateManager.pushMatrix();
         String llIlIlIIllIIl = String.valueOf((new StringBuilder()).append("Blocks: §7").append(llIlIlIIlIlll.getBlocksAmount()));
         float llIlIlIIlIIll = new ScaledResolution(mc);
         RenderUtils.drawBorderedRect((float)(llIlIlIIlIIll.getScaledWidth() / 2 - 2), (float)(llIlIlIIlIIll.getScaledHeight() / 2 + 5), (float)(llIlIlIIlIIll.getScaledWidth() / 2 + Fonts.font40.getStringWidth(llIlIlIIllIIl) + 2), (float)(llIlIlIIlIIll.getScaledHeight() / 2 + 16), 3.0F, Color.BLACK.getRGB(), Color.BLACK.getRGB());
         GlStateManager.resetColor();
         Fonts.font40.drawString(llIlIlIIllIIl, llIlIlIIlIIll.getScaledWidth() / 2, llIlIlIIlIIll.getScaledHeight() / 2 + 7, Color.WHITE.getRGB());
         boolean var10001 = false;
         GlStateManager.popMatrix();
      }

   }

   private BlockData getBlockData(BlockPos llIlIlllIllIl) {
      if (llIlIlllIlllI.isPosSolid(llIlIlllIllIl.add(-1, 0, 0))) {
         return new BlockData(llIlIlllIllIl.add(-1, 0, 0), llIlIlllIlllI.CanDownPut() ? EnumFacing.DOWN : EnumFacing.EAST);
      } else if (llIlIlllIlllI.isPosSolid(llIlIlllIllIl.add(1, 0, 0))) {
         return new BlockData(llIlIlllIllIl.add(1, 0, 0), llIlIlllIlllI.CanDownPut() ? EnumFacing.DOWN : EnumFacing.WEST);
      } else if (llIlIlllIlllI.isPosSolid(llIlIlllIllIl.add(0, 0, 1))) {
         return new BlockData(llIlIlllIllIl.add(0, 0, 1), llIlIlllIlllI.CanDownPut() ? EnumFacing.DOWN : EnumFacing.NORTH);
      } else {
         return llIlIlllIlllI.isPosSolid(llIlIlllIllIl.add(0, 0, -1)) ? new BlockData(llIlIlllIllIl.add(0, 0, -1), llIlIlllIlllI.CanDownPut() ? EnumFacing.DOWN : EnumFacing.SOUTH) : null;
      }
   }

   public String getTag() {
      return (String)llIIlIIlIlIIl.modeValue.get();
   }

   public int getBiggestBlockSlotInv() {
      int llIIlIlIlIIIl = -1;
      int llIIlIlIIllIl = 0;
      if (llIIlIlIlIIlI.getBlocksAmount() == 0) {
         return -1;
      } else {
         for(int llIIlIlIlIIll = 9; llIIlIlIlIIll < 36; ++llIIlIlIlIIll) {
            if (mc.thePlayer.inventoryContainer.getSlot(llIIlIlIlIIll).getHasStack()) {
               Item llIIlIlIlIlIl = mc.thePlayer.inventoryContainer.getSlot(llIIlIlIlIIll).getStack().getItem();
               double llIIlIlIIlIlI = mc.thePlayer.inventoryContainer.getSlot(llIIlIlIlIIll).getStack();
               if (llIIlIlIlIlIl instanceof ItemBlock && llIIlIlIIlIlI.stackSize > llIIlIlIIllIl) {
                  llIIlIlIIllIl = llIIlIlIIlIlI.stackSize;
                  llIIlIlIlIIIl = llIIlIlIlIIll;
               }
            }
         }

         return llIIlIlIlIIIl;
      }
   }

   public void onEnable() {
      if (mc.thePlayer != null) {
         llIllIlIIIIlI.launchY = (int)mc.thePlayer.posY;
      }
   }

   private boolean search(BlockPos llIIlllIlIIIl, boolean llIIlllIlIIII) {
      if (!BlockUtils.isReplaceable(llIIlllIlIIIl)) {
         return false;
      } else {
         int llIIllIllllll = ((String)llIIlllIlIIlI.rotationModeValue.get()).equalsIgnoreCase("StaticPitch");
         boolean llIIllIlllllI = ((String)llIIlllIlIIlI.rotationModeValue.get()).equalsIgnoreCase("AAC4");
         float llIIlllIIllIl = (Float)llIIlllIlIIlI.staticPitchValue.get();
         short llIIllIllllII = (Float)llIIlllIlIIlI.staticYawOffsetValue.get();
         double llIIlllIIlIll = (double)(Float)llIIlllIlIIlI.xzRangeValue.get();
         double llIIlllIIlIlI = llIIlllIlIIlI.calcStepSize(llIIlllIIlIll);
         long llIIllIlllIIl = (double)(Float)llIIlllIlIIlI.yRangeValue.get();
         Exception llIIllIlllIII = llIIlllIlIIlI.calcStepSize(llIIllIlllIIl);
         double llIIlllIIIlll = 0.0D;
         double llIIlllIIIllI = 0.0D;
         double llIIlllIIIlIl = 0.0D;
         Exception llIIllIllIlII = new Vec3(mc.thePlayer.posX, mc.thePlayer.getEntityBoundingBox().minY + (double)mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);
         PlaceRotation llIIlllIIIIll = null;
         String llIIllIllIIlI = EnumFacing.values();
         Exception llIIllIllIIIl = llIIllIllIIlI.length;

         int llIIllIllIIII;
         EnumFacing llIIlllIlllII;
         BlockPos llIIllIlIlllI;
         Vec3 llIIlllIllIlI;
         for(llIIllIllIIII = 0; llIIllIllIIII < llIIllIllIIIl; ++llIIllIllIIII) {
            llIIlllIlllII = llIIllIllIIlI[llIIllIllIIII];
            llIIllIlIlllI = llIIlllIlIIIl.offset(llIIlllIlllII);
            if (BlockUtils.canBeClicked(llIIllIlIlllI)) {
               llIIlllIllIlI = new Vec3(llIIlllIlllII.getDirectionVec());
               double llIIllIlIllII;
               double llIIllllIIIII;
               double llIIllIlIlIII;
               Vec3 llIIllIlIIllI;
               double llIIllIlIIlIl;
               Vec3 llIIllllIIIlI;
               double llIlIIIIIIIlI;
               double llIIllIlIIIIl;
               double llIIllIIlllll;
               double llIIllllllIIl;
               Rotation llIIllIIllIll;
               Vec3 llIIllIIllIlI;
               Vec3 llIIllIIllIIl;
               MovingObjectPosition llIIlllllIlII;
               if (((String)llIIlllIlIIlI.rotationModeValue.get()).equalsIgnoreCase("Hypixel")) {
                  for(llIIllIlIllII = 0.1D; llIIllIlIllII < 0.9D; llIIllIlIllII += 0.1D) {
                     for(llIIllllIIIII = 0.1D; llIIllllIIIII < 0.9D; llIIllllIIIII += 0.1D) {
                        for(llIIllIlIlIII = 0.1D; llIIllIlIlIII < 0.9D; llIIllIlIlIII += 0.1D) {
                           llIIllIlIIllI = (new Vec3(llIIlllIlIIIl)).addVector(llIIllIlIllII, llIIllllIIIII, llIIllIlIlIII);
                           llIIllIlIIlIl = llIIllIllIlII.squareDistanceTo(llIIllIlIIllI);
                           llIIllllIIIlI = llIIllIlIIllI.add(new Vec3(llIIlllIllIlI.xCoord * 0.6D, llIIlllIllIlI.yCoord * 0.6D, llIIlllIllIlI.zCoord * 0.6D));
                           if (!llIIlllIlIIII || !(llIIllIllIlII.squareDistanceTo(llIIllllIIIlI) > 18.0D) && !(llIIllIlIIlIl > llIIllIllIlII.squareDistanceTo(llIIllIlIIllI.add(llIIlllIllIlI))) && mc.theWorld.rayTraceBlocks(llIIllIllIlII, llIIllllIIIlI, false, true, false) == null) {
                              llIlIIIIIIIlI = llIIllllIIIlI.xCoord - llIIllIllIlII.xCoord;
                              llIIllIlIIIIl = llIIllllIIIlI.yCoord - llIIllIllIlII.yCoord;
                              llIIllIIlllll = llIIllllIIIlI.zCoord - llIIllIllIlII.zCoord;
                              llIIllllllIIl = (double)MathHelper.sqrt_double(llIlIIIIIIIlI * llIlIIIIIIIlI + llIIllIIlllll * llIIllIIlllll);
                              llIIllIIllIll = new Rotation(MathHelper.wrapAngleTo180_float((float)Math.toDegrees(Math.atan2(llIIllIIlllll, llIlIIIIIIIlI)) - 90.0F), MathHelper.wrapAngleTo180_float((float)(-Math.toDegrees(Math.atan2(llIIllIlIIIIl, llIIllllllIIl)))));
                              llIIllIIllIlI = RotationUtils.getVectorForRotation(llIIllIIllIll);
                              llIIllIIllIIl = llIIllIllIlII.addVector(llIIllIIllIlI.xCoord * 4.0D, llIIllIIllIlI.yCoord * 4.0D, llIIllIIllIlI.zCoord * 4.0D);
                              llIIlllllIlII = mc.theWorld.rayTraceBlocks(llIIllIllIlII, llIIllIIllIIl, false, false, true);
                              if (llIIlllllIlII.typeOfHit == MovingObjectType.BLOCK && llIIlllllIlII.getBlockPos().equals(llIIllIlIlllI) && (llIIlllIIIIll == null || RotationUtils.getRotationDifference(llIIllIIllIll) < RotationUtils.getRotationDifference(llIIlllIIIIll.getRotation()))) {
                                 llIIlllIIIIll = new PlaceRotation(new PlaceInfo(llIIllIlIlllI, llIIlllIlllII.getOpposite(), llIIllllIIIlI), llIIllIIllIll);
                              }
                           }
                        }
                     }
                  }
               }

               if (((String)llIIlllIlIIlI.rotationModeValue.get()).equalsIgnoreCase("Normal")) {
                  for(llIIllIlIllII = 0.1D; llIIllIlIllII < 0.9D; llIIllIlIllII += 0.1D) {
                     for(llIIllllIIIII = 0.1D; llIIllllIIIII < 0.9D; llIIllllIIIII += 0.1D) {
                        for(llIIllIlIlIII = 0.1D; llIIllIlIlIII < 0.9D; llIIllIlIlIII += 0.1D) {
                           llIIllIlIIllI = (new Vec3(llIIlllIlIIIl)).addVector(llIIllIlIllII, llIIllllIIIII, llIIllIlIlIII);
                           llIIllIlIIlIl = llIIllIllIlII.squareDistanceTo(llIIllIlIIllI);
                           llIIllllIIIlI = llIIllIlIIllI.add(new Vec3(llIIlllIllIlI.xCoord * 0.5D, llIIlllIllIlI.yCoord * 0.5D, llIIlllIllIlI.zCoord * 0.5D));
                           if (!llIIlllIlIIII || !(llIIllIllIlII.squareDistanceTo(llIIllllIIIlI) > 18.0D) && !(llIIllIlIIlIl > llIIllIllIlII.squareDistanceTo(llIIllIlIIllI.add(llIIlllIllIlI))) && mc.theWorld.rayTraceBlocks(llIIllIllIlII, llIIllllIIIlI, false, true, false) == null) {
                              llIlIIIIIIIlI = llIIllllIIIlI.xCoord - llIIllIllIlII.xCoord;
                              llIIllIlIIIIl = llIIllllIIIlI.yCoord - llIIllIllIlII.yCoord;
                              llIIllIIlllll = llIIllllIIIlI.zCoord - llIIllIllIlII.zCoord;
                              llIIllllllIIl = (double)MathHelper.sqrt_double(llIlIIIIIIIlI * llIlIIIIIIIlI + llIIllIIlllll * llIIllIIlllll);
                              llIIllIIllIll = new Rotation(MathHelper.wrapAngleTo180_float((float)Math.toDegrees(Math.atan2(llIIllIIlllll, llIlIIIIIIIlI)) - 90.0F), MathHelper.wrapAngleTo180_float((float)(-Math.toDegrees(Math.atan2(llIIllIlIIIIl, llIIllllllIIl)))));
                              llIIllIIllIlI = RotationUtils.getVectorForRotation(llIIllIIllIll);
                              llIIllIIllIIl = llIIllIllIlII.addVector(llIIllIIllIlI.xCoord * 4.0D, llIIllIIllIlI.yCoord * 4.0D, llIIllIIllIlI.zCoord * 4.0D);
                              llIIlllllIlII = mc.theWorld.rayTraceBlocks(llIIllIllIlII, llIIllIIllIIl, false, false, true);
                              if (llIIlllllIlII.typeOfHit == MovingObjectType.BLOCK && llIIlllllIlII.getBlockPos().equals(llIIllIlIlllI) && (llIIlllIIIIll == null || RotationUtils.getRotationDifference(llIIllIIllIll) < RotationUtils.getRotationDifference(llIIlllIIIIll.getRotation()))) {
                                 llIIlllIIIIll = new PlaceRotation(new PlaceInfo(llIIllIlIlllI, llIIlllIlllII.getOpposite(), llIIllllIIIlI), llIIllIIllIll);
                              }
                           }
                        }
                     }
                  }
               }

               if (((String)llIIlllIlIIlI.rotationModeValue.get()).equalsIgnoreCase("AAC4") || ((String)llIIlllIlIIlI.rotationModeValue.get()).equalsIgnoreCase("StaticPitch")) {
                  for(llIIllIlIllII = 0.5D - llIIlllIIlIll / 2.0D; llIIllIlIllII <= 0.5D + llIIlllIIlIll / 2.0D; llIIllIlIllII += llIIlllIIlIlI) {
                     for(llIIllllIIIII = 0.5D - llIIllIlllIIl / 2.0D; llIIllllIIIII <= 0.5D + llIIllIlllIIl / 2.0D; llIIllllIIIII += llIIllIlllIII) {
                        for(llIIllIlIlIII = 0.5D - llIIlllIIlIll / 2.0D; llIIllIlIlIII <= 0.5D + llIIlllIIlIll / 2.0D; llIIllIlIlIII += llIIlllIIlIlI) {
                           llIIllIlIIllI = (new Vec3(llIIlllIlIIIl)).addVector(llIIllIlIllII, llIIllllIIIII, llIIllIlIlIII);
                           llIIllIlIIlIl = llIIllIllIlII.squareDistanceTo(llIIllIlIIllI);
                           llIIllllIIIlI = llIIllIlIIllI.add(new Vec3(llIIlllIllIlI.xCoord * 0.5D, llIIlllIllIlI.yCoord * 0.5D, llIIlllIllIlI.zCoord * 0.5D));
                           if (!llIIlllIlIIII || !(llIIllIllIlII.squareDistanceTo(llIIllllIIIlI) > 18.0D) && !(llIIllIlIIlIl > llIIllIllIlII.squareDistanceTo(llIIllIlIIllI.add(llIIlllIllIlI))) && mc.theWorld.rayTraceBlocks(llIIllIllIlII, llIIllllIIIlI, false, true, false) == null) {
                              for(int llIIllIlIIIll = 0; llIIllIlIIIll < (llIIllIlllllI ? 2 : 1); ++llIIllIlIIIll) {
                                 byte llIIllIlIIIlI = llIIllIlllllI && llIIllIlIIIll == 0 ? 0.0D : llIIllllIIIlI.xCoord - llIIllIllIlII.xCoord;
                                 byte llIIllIlIIIII = llIIllllIIIlI.yCoord - llIIllIllIlII.yCoord;
                                 String llIIllIIllllI = llIIllIlllllI && llIIllIlIIIll == 1 ? 0.0D : llIIllllIIIlI.zCoord - llIIllIllIlII.zCoord;
                                 boolean llIIllIIlllII = (double)MathHelper.sqrt_double(llIIllIlIIIlI * llIIllIlIIIlI + llIIllIIllllI * llIIllIIllllI);
                                 String llIIllIIllIlI = llIIllIllllll ? llIIlllIIllIl : MathHelper.wrapAngleTo180_float((float)(-Math.toDegrees(Math.atan2(llIIllIlIIIII, llIIllIIlllII))));
                                 Rotation llIIllllIlIll = new Rotation(MathHelper.wrapAngleTo180_float((float)Math.toDegrees(Math.atan2(llIIllIIllllI, llIIllIlIIIlI)) - 90.0F + (llIIllIlllllI ? llIIllIllllII : 0.0F)), llIIllIIllIlI);
                                 Vec3 llIIllllIlIlI = RotationUtils.getVectorForRotation(llIIllllIlIll);
                                 boolean llIIllIIlIlll = llIIllIllIlII.addVector(llIIllllIlIlI.xCoord * 4.0D, llIIllllIlIlI.yCoord * 4.0D, llIIllllIlIlI.zCoord * 4.0D);
                                 Exception llIIllIIlIllI = mc.theWorld.rayTraceBlocks(llIIllIllIlII, llIIllIIlIlll, false, false, true);
                                 if (llIIllIIlIllI.typeOfHit == MovingObjectType.BLOCK && llIIllIIlIllI.getBlockPos().equals(llIIllIlIlllI)) {
                                    if (llIIlllIIIIll == null || RotationUtils.getRotationDifference(llIIllllIlIll) < RotationUtils.getRotationDifference(llIIlllIIIIll.getRotation())) {
                                       llIIlllIIIIll = new PlaceRotation(new PlaceInfo(llIIllIlIlllI, llIIlllIlllII.getOpposite(), llIIllllIIIlI), llIIllllIlIll);
                                    }

                                    llIIlllIIIlll = llIIllIlIllII;
                                    llIIlllIIIllI = llIIllllIIIII;
                                    llIIlllIIIlIl = llIIllIlIlIII;
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

         if (llIIlllIIIIll == null) {
            return false;
         } else {
            if (((String)llIIlllIlIIlI.rotationModeValue.get()).equalsIgnoreCase("Normal")) {
               RotationUtils.setTargetRotation(llIIlllIIIIll.getRotation(), (Integer)llIIlllIlIIlI.keepLengthValue.get());
               llIIlllIlIIlI.lockRotation = llIIlllIIIIll.getRotation();
            }

            if ((Boolean)llIIlllIlIIlI.hyprotation.get() && ((String)llIIlllIlIIlI.rotationModeValue.get()).equalsIgnoreCase("Hypixel")) {
               RotationUtils.setTargetRotation(llIIlllIIIIll.getRotation(), (Integer)llIIlllIlIIlI.keepLengthValue.get());
               llIIlllIlIIlI.lockRotation = llIIlllIIIIll.getRotation();
            }

            if (((String)llIIlllIlIIlI.rotationModeValue.get()).equalsIgnoreCase("AAC4") || ((String)llIIlllIlIIlI.rotationModeValue.get()).equalsIgnoreCase("StaticPitch")) {
               if ((Float)llIIlllIlIIlI.minTurnSpeedValue.get() < 180.0F) {
                  llIIlllIlIIlI.limitedRotation = RotationUtils.limitAngleChange(RotationUtils.serverRotation, llIIlllIIIIll.getRotation(), (float)(Math.random() * (double)((Float)llIIlllIlIIlI.maxTurnSpeedValue.get() - (Float)llIIlllIlIIlI.minTurnSpeedValue.get()) + (double)(Float)llIIlllIlIIlI.minTurnSpeedValue.get()));
                  llIIlllIlIIlI.setRotation(llIIlllIlIIlI.limitedRotation, (Integer)llIIlllIlIIlI.keepLengthValue.get());
                  llIIlllIlIIlI.lockRotation = llIIlllIlIIlI.limitedRotation;
                  llIIlllIlIIlI.facesBlock = false;
                  llIIllIllIIlI = EnumFacing.values();
                  llIIllIllIIIl = llIIllIllIIlI.length;

                  for(llIIllIllIIII = 0; llIIllIllIIII < llIIllIllIIIl; ++llIIllIllIIII) {
                     llIIlllIlllII = llIIllIllIIlI[llIIllIllIIII];
                     llIIllIlIlllI = llIIlllIlIIIl.offset(llIIlllIlllII);
                     if (BlockUtils.canBeClicked(llIIllIlIlllI)) {
                        llIIlllIllIlI = new Vec3(llIIlllIlllII.getDirectionVec());
                        Exception llIIllIlIllII = (new Vec3(llIIlllIlIIIl)).addVector(llIIlllIIIlll, llIIlllIIIllI, llIIlllIIIlIl);
                        double llIIlllIllIII = llIIllIllIlII.squareDistanceTo(llIIllIlIllII);
                        int llIIllIlIlIIl = llIIllIlIllII.add(new Vec3(llIIlllIllIlI.xCoord * 0.5D, llIIlllIllIlI.yCoord * 0.5D, llIIlllIllIlI.zCoord * 0.5D));
                        if (!llIIlllIlIIII || !(llIIllIllIlII.squareDistanceTo(llIIllIlIlIIl) > 18.0D) && !(llIIlllIllIII > llIIllIllIlII.squareDistanceTo(llIIllIlIllII.add(llIIlllIllIlI))) && mc.theWorld.rayTraceBlocks(llIIllIllIlII, llIIllIlIlIIl, false, true, false) == null) {
                           short llIIllIlIlIII = RotationUtils.getVectorForRotation(llIIlllIlIIlI.limitedRotation);
                           Vec3 llIIlllIlIlIl = llIIllIllIlII.addVector(llIIllIlIlIII.xCoord * 4.0D, llIIllIlIlIII.yCoord * 4.0D, llIIllIlIlIII.zCoord * 4.0D);
                           MovingObjectPosition llIIlllIlIlII = mc.theWorld.rayTraceBlocks(llIIllIllIlII, llIIlllIlIlIl, false, false, true);
                           if (llIIlllIlIlII.typeOfHit == MovingObjectType.BLOCK && llIIlllIlIlII.getBlockPos().equals(llIIllIlIlllI)) {
                              llIIlllIlIIlI.facesBlock = true;
                              break;
                           }
                        }
                     }
                  }
               } else {
                  llIIlllIlIIlI.setRotation(llIIlllIIIIll.getRotation(), (Integer)llIIlllIlIIlI.keepLengthValue.get());
                  llIIlllIlIIlI.lockRotation = llIIlllIIIIll.getRotation();
                  llIIlllIlIIlI.facesBlock = true;
               }
            }

            llIIlllIlIIlI.targetPlace = llIIlllIIIIll.getPlaceInfo();
            return true;
         }
      }
   }

   @EventTarget
   public void onPacket(PacketEvent llIllIIIIlIll) {
      if (mc.thePlayer != null) {
         Packet<?> llIllIIIIIlll = llIllIIIIlIll.getPacket();
         if (llIllIIIIIlll instanceof C09PacketHeldItemChange) {
            C09PacketHeldItemChange llIllIIIIllIl = (C09PacketHeldItemChange)llIllIIIIIlll;
            llIllIIIIlIIl.slot = llIllIIIIllIl.getSlotId();
         }

      }
   }

   @EventTarget
   private void onStrafe(StrafeEvent llIllIIlIlIIl) {
      if ((Boolean)llIllIIlIlIlI.rotationStrafeValue.get()) {
         if (llIllIIlIlIlI.lockRotation != null && (Boolean)llIllIIlIlIlI.keepRotationValue.get()) {
            long llIllIIlIIllI = (int)((MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw - ((Float)llIllIIlIlIlI.minTurnSpeedValue.get() < 180.0F ? llIllIIlIlIlI.limitedRotation : llIllIIlIlIlI.lockRotation).getYaw() - 23.5F - 135.0F) + 180.0F) / 45.0F);
            String llIllIIlIIlIl = ((Float)llIllIIlIlIlI.minTurnSpeedValue.get() < 180.0F ? llIllIIlIlIlI.limitedRotation : llIllIIlIlIlI.lockRotation).getYaw();
            Exception llIllIIlIIlII = llIllIIlIlIIl.getStrafe();
            short llIllIIlIIIll = llIllIIlIlIIl.getForward();
            float llIllIIlIlllI = llIllIIlIlIIl.getFriction();
            boolean llIllIIlIIIIl = 0.0F;
            int llIllIIlIIIII = 0.0F;
            switch(llIllIIlIIllI) {
            case 0:
               llIllIIlIIIIl = llIllIIlIIIll;
               llIllIIlIIIII = llIllIIlIIlII;
               break;
            case 1:
               llIllIIlIIIIl += llIllIIlIIIll;
               llIllIIlIIIII -= llIllIIlIIIll;
               llIllIIlIIIIl += llIllIIlIIlII;
               llIllIIlIIIII += llIllIIlIIlII;
               break;
            case 2:
               llIllIIlIIIIl = llIllIIlIIlII;
               llIllIIlIIIII = -llIllIIlIIIll;
               break;
            case 3:
               llIllIIlIIIIl -= llIllIIlIIIll;
               llIllIIlIIIII -= llIllIIlIIIll;
               llIllIIlIIIIl += llIllIIlIIlII;
               llIllIIlIIIII -= llIllIIlIIlII;
               break;
            case 4:
               llIllIIlIIIIl = -llIllIIlIIIll;
               llIllIIlIIIII = -llIllIIlIIlII;
               break;
            case 5:
               llIllIIlIIIIl -= llIllIIlIIIll;
               llIllIIlIIIII += llIllIIlIIIll;
               llIllIIlIIIIl -= llIllIIlIIlII;
               llIllIIlIIIII -= llIllIIlIIlII;
               break;
            case 6:
               llIllIIlIIIIl = -llIllIIlIIlII;
               llIllIIlIIIII = llIllIIlIIIll;
               break;
            case 7:
               llIllIIlIIIIl += llIllIIlIIIll;
               llIllIIlIIIII += llIllIIlIIIll;
               llIllIIlIIIIl -= llIllIIlIIlII;
               llIllIIlIIIII += llIllIIlIIlII;
            }

            if (llIllIIlIIIIl > 1.0F) {
               llIllIIlIIIIl *= 0.5F;
            } else if (llIllIIlIIIIl < 0.9F && llIllIIlIIIIl > 0.3F) {
               llIllIIlIIIIl *= 0.5F;
            }

            if (llIllIIlIIIIl < -1.0F) {
               llIllIIlIIIIl *= 0.5F;
            } else if (llIllIIlIIIIl > -0.9F && llIllIIlIIIIl < -0.3F) {
               llIllIIlIIIIl *= 0.5F;
            }

            if (llIllIIlIIIII > 1.0F) {
               llIllIIlIIIII *= 0.5F;
            } else if (llIllIIlIIIII < 0.9F && llIllIIlIIIII > 0.3F) {
               llIllIIlIIIII *= 0.5F;
            }

            if (llIllIIlIIIII < -1.0F) {
               llIllIIlIIIII *= 0.5F;
            } else if (llIllIIlIIIII > -0.9F && llIllIIlIIIII < -0.3F) {
               llIllIIlIIIII *= 0.5F;
            }

            Exception llIllIIIlllll = llIllIIlIIIII * llIllIIlIIIII + llIllIIlIIIIl * llIllIIlIIIIl;
            if (llIllIIIlllll >= 1.0E-4F) {
               llIllIIIlllll = MathHelper.sqrt_float(llIllIIIlllll);
               if (llIllIIIlllll < 1.0F) {
                  llIllIIIlllll = 1.0F;
               }

               llIllIIIlllll = llIllIIlIlllI / llIllIIIlllll;
               llIllIIlIIIII *= llIllIIIlllll;
               llIllIIlIIIIl *= llIllIIIlllll;
               float llIllIIllIlII = MathHelper.sin((float)((double)llIllIIlIIlIl * 3.141592653589793D / 180.0D));
               boolean llIllIIIlllIl = MathHelper.cos((float)((double)llIllIIlIIlIl * 3.141592653589793D / 180.0D));
               EntityPlayerSP var10000 = mc.thePlayer;
               var10000.motionX += (double)(llIllIIlIIIII * llIllIIIlllIl - llIllIIlIIIIl * llIllIIllIlII);
               var10000 = mc.thePlayer;
               var10000.motionZ += (double)(llIllIIlIIIIl * llIllIIIlllIl + llIllIIlIIIII * llIllIIllIlII);
            }

            llIllIIlIlIIl.cancelEvent();
         }

      }
   }

   private void place() {
      if (llIlIlIlIllII.targetPlace == null) {
         if ((Boolean)llIlIlIlIllII.placeableDelay.get()) {
            llIlIlIlIllII.delayTimer.reset();
         }

      } else if (llIlIlIlIllII.delayTimer.hasTimePassed(llIlIlIlIllII.delay) && (!(Boolean)llIlIlIlIllII.sameYValue.get() || llIlIlIlIllII.launchY == (int)mc.thePlayer.posY)) {
         int llIlIlIlIlIII = -1;
         ItemStack llIlIlIlIlIlI = mc.thePlayer.getHeldItem();
         if (mc.thePlayer.getHeldItem() == null || !(mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock)) {
            if (!(Boolean)llIlIlIlIllII.autoBlockValue.get()) {
               return;
            }

            llIlIlIlIlIII = InventoryUtils.findAutoBlockBlock();
            if (llIlIlIlIlIII == -1) {
               return;
            }

            mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(llIlIlIlIlIII - 36));
            llIlIlIlIlIlI = mc.thePlayer.inventoryContainer.getSlot(llIlIlIlIlIII).getStack();
         }

         if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, llIlIlIlIlIlI, llIlIlIlIllII.targetPlace.getBlockPos(), llIlIlIlIllII.targetPlace.getEnumFacing(), llIlIlIlIllII.targetPlace.getVec3())) {
            llIlIlIlIllII.delayTimer.reset();
            llIlIlIlIllII.delay = TimeUtils.randomDelay((Integer)llIlIlIlIllII.minDelayValue.get(), (Integer)llIlIlIlIllII.maxDelayValue.get());
            if (mc.thePlayer.onGround) {
               Exception llIlIlIlIIllI = (Float)llIlIlIlIllII.speedModifierValue.get();
               EntityPlayerSP var10000 = mc.thePlayer;
               var10000.motionX *= (double)llIlIlIlIIllI;
               var10000 = mc.thePlayer;
               var10000.motionZ *= (double)llIlIlIlIIllI;
            }

            if ((Boolean)llIlIlIlIllII.swingValue.get()) {
               mc.thePlayer.swingItem();
            } else {
               mc.getNetHandler().addToSendQueue(new C0APacketAnimation());
            }
         }

         if (!(Boolean)llIlIlIlIllII.stayAutoBlock.get() && llIlIlIlIlIII >= 0) {
            mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
         }

         llIlIlIlIllII.targetPlace = null;
      }
   }

   private boolean CanDownPut() {
      return (Boolean)llIlIlllIIIIl.down.get() && mc.gameSettings.keyBindSprint.isKeyDown() && mc.thePlayer.onGround;
   }

   public static Vec3 getVec3(BlockPos llIlIllllIlIl, EnumFacing llIlIlllllIIl) {
      byte llIlIllllIIll = (double)llIlIllllIlIl.getX() + 0.5D;
      double llIlIllllIlll = (double)llIlIllllIlIl.getY() + 0.5D;
      double llIlIllllIllI = (double)llIlIllllIlIl.getZ() + 0.5D;
      llIlIllllIIll += (double)llIlIlllllIIl.getFrontOffsetX() / 2.0D;
      llIlIllllIllI += (double)llIlIlllllIIl.getFrontOffsetZ() / 2.0D;
      llIlIllllIlll += (double)llIlIlllllIIl.getFrontOffsetY() / 2.0D;
      if (llIlIlllllIIl != EnumFacing.UP && llIlIlllllIIl != EnumFacing.DOWN) {
         llIlIllllIlll += randomNumber(0.3D, -0.3D);
      } else {
         llIlIllllIIll += randomNumber(0.3D, -0.3D);
         llIlIllllIllI += randomNumber(0.3D, -0.3D);
      }

      if (llIlIlllllIIl == EnumFacing.WEST || llIlIlllllIIl == EnumFacing.EAST) {
         llIlIllllIllI += randomNumber(0.3D, -0.3D);
      }

      if (llIlIlllllIIl == EnumFacing.SOUTH || llIlIlllllIIl == EnumFacing.NORTH) {
         llIlIllllIIll += randomNumber(0.3D, -0.3D);
      }

      return new Vec3(llIlIllllIIll, llIlIllllIlll, llIlIllllIllI);
   }

   @EventTarget
   public void onMove(MoveEvent llIlIlIIlllIl) {
      if ((Boolean)llIlIlIlIIIII.safeWalkValue.get()) {
         if ((Boolean)llIlIlIlIIIII.airSafeValue.get() || mc.thePlayer.onGround) {
            llIlIlIIlllIl.setSafeWalk(true);
         }

      }
   }

   private boolean invCheck() {
      for(int llIIlIlIlllIl = 36; llIIlIlIlllIl < 45; ++llIIlIlIlllIl) {
         if (mc.thePlayer.inventoryContainer.getSlot(llIIlIlIlllIl).getHasStack()) {
            Item llIIlIllIIIII = mc.thePlayer.inventoryContainer.getSlot(llIIlIlIlllIl).getStack().getItem();
            if (llIIlIllIIIII instanceof ItemBlock) {
               return false;
            }
         }
      }

      return true;
   }
}
