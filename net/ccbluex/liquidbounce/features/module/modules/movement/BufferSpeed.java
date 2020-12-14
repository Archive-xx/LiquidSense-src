//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockSlime;
import net.minecraft.block.BlockStairs;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.BlockPos;

@ModuleInfo(
   name = "BufferSpeed",
   description = "Allows you to walk faster on slabs and stairs.",
   category = ModuleCategory.MOVEMENT
)
public class BufferSpeed extends Module {
   // $FF: synthetic field
   private final FloatValue wallBoostValue = new FloatValue("WallBoost", 1.87F, 1.0F, 2.0F);
   // $FF: synthetic field
   private final BoolValue noHurtValue = new BoolValue("NoHurt", true);
   // $FF: synthetic field
   private final FloatValue stairsBoostValue = new FloatValue("StairsBoost", 1.87F, 1.0F, 2.0F);
   // $FF: synthetic field
   private final BoolValue headBlockValue = new BoolValue("HeadBlock", true);
   // $FF: synthetic field
   private final BoolValue bufferValue = new BoolValue("Buffer", true);
   // $FF: synthetic field
   private boolean down;
   // $FF: synthetic field
   private boolean fastHop;
   // $FF: synthetic field
   private final ListValue wallModeValue = new ListValue("WallMode", new String[]{"Old", "New"}, "New");
   // $FF: synthetic field
   private final BoolValue iceValue = new BoolValue("Ice", false);
   // $FF: synthetic field
   private final BoolValue slimeValue = new BoolValue("Slime", true);
   // $FF: synthetic field
   private final BoolValue snowPortValue = new BoolValue("SnowPort", true);
   // $FF: synthetic field
   private final BoolValue speedLimitValue = new BoolValue("SpeedLimit", true);
   // $FF: synthetic field
   private final BoolValue wallValue = new BoolValue("Wall", true);
   // $FF: synthetic field
   private final FloatValue maxSpeedValue = new FloatValue("MaxSpeed", 2.0F, 1.0F, 5.0F);
   // $FF: synthetic field
   private final FloatValue headBlockBoostValue = new FloatValue("HeadBlockBoost", 1.87F, 1.0F, 2.0F);
   // $FF: synthetic field
   private final BoolValue snowValue = new BoolValue("Snow", true);
   // $FF: synthetic field
   private final BoolValue stairsValue = new BoolValue("Stairs", true);
   // $FF: synthetic field
   private final FloatValue iceBoostValue = new FloatValue("IceBoost", 1.342F, 1.0F, 2.0F);
   // $FF: synthetic field
   private final BoolValue slabsValue = new BoolValue("Slabs", true);
   // $FF: synthetic field
   private boolean hadFastHop;
   // $FF: synthetic field
   private double speed = 0.0D;
   // $FF: synthetic field
   private boolean forceDown;
   // $FF: synthetic field
   private final FloatValue snowBoostValue = new FloatValue("SnowBoost", 1.87F, 1.0F, 2.0F);
   // $FF: synthetic field
   private final FloatValue slabsBoostValue = new FloatValue("SlabsBoost", 1.87F, 1.0F, 2.0F);
   // $FF: synthetic field
   private final ListValue slabsModeValue = new ListValue("SlabsMode", new String[]{"Old", "New"}, "New");
   // $FF: synthetic field
   private boolean legitHop;
   // $FF: synthetic field
   private final BoolValue airStrafeValue = new BoolValue("AirStrafe", false);
   // $FF: synthetic field
   private final ListValue stairsModeValue = new ListValue("StairsMode", new String[]{"Old", "New"}, "New");

   @EventTarget
   public void onUpdate(UpdateEvent llIllIlllIlllIl) {
      if (!LiquidBounce.moduleManager.getModule(Speed.class).getState() && (!(Boolean)llIllIlllIllllI.noHurtValue.get() || mc.thePlayer.hurtTime <= 0)) {
         BlockPos llIllIlllIlllII = new BlockPos(mc.thePlayer.posX, mc.thePlayer.getEntityBoundingBox().minY, mc.thePlayer.posZ);
         if (llIllIlllIllllI.forceDown || llIllIlllIllllI.down && mc.thePlayer.motionY == 0.0D) {
            mc.thePlayer.motionY = -1.0D;
            llIllIlllIllllI.down = false;
            llIllIlllIllllI.forceDown = false;
         }

         if (llIllIlllIllllI.fastHop) {
            mc.thePlayer.speedInAir = 0.0211F;
            llIllIlllIllllI.hadFastHop = true;
         } else if (llIllIlllIllllI.hadFastHop) {
            mc.thePlayer.speedInAir = 0.02F;
            llIllIlllIllllI.hadFastHop = false;
         }

         if (MovementUtils.isMoving() && !mc.thePlayer.isSneaking() && !mc.thePlayer.isInWater() && !mc.gameSettings.keyBindJump.isKeyDown()) {
            if (!mc.thePlayer.onGround) {
               llIllIlllIllllI.speed = 0.0D;
               if ((Boolean)llIllIlllIllllI.airStrafeValue.get()) {
                  MovementUtils.strafe();
               }
            } else {
               llIllIlllIllllI.fastHop = false;
               EntityPlayerSP var10000;
               if ((Boolean)llIllIlllIllllI.slimeValue.get() && (BlockUtils.getBlock(llIllIlllIlllII.down()) instanceof BlockSlime || BlockUtils.getBlock(llIllIlllIlllII) instanceof BlockSlime)) {
                  mc.thePlayer.jump();
                  mc.thePlayer.motionY = 0.08D;
                  var10000 = mc.thePlayer;
                  var10000.motionX *= 1.132D;
                  var10000 = mc.thePlayer;
                  var10000.motionZ *= 1.132D;
                  llIllIlllIllllI.down = true;
                  return;
               }

               String llIllIlllIllIIl;
               byte llIllIlllIllIII;
               if ((Boolean)llIllIlllIllllI.slabsValue.get() && BlockUtils.getBlock(llIllIlllIlllII) instanceof BlockSlab) {
                  llIllIlllIllIIl = ((String)llIllIlllIllllI.slabsModeValue.get()).toLowerCase();
                  llIllIlllIllIII = -1;
                  switch(llIllIlllIllIIl.hashCode()) {
                  case 108960:
                     if (llIllIlllIllIIl.equals("new")) {
                        llIllIlllIllIII = 1;
                     }
                     break;
                  case 110119:
                     if (llIllIlllIllIIl.equals("old")) {
                        llIllIlllIllIII = 0;
                     }
                  }

                  switch(llIllIlllIllIII) {
                  case 0:
                     llIllIlllIllllI.boost((Float)llIllIlllIllllI.slabsBoostValue.get());
                     return;
                  case 1:
                     llIllIlllIllllI.fastHop = true;
                     if (llIllIlllIllllI.legitHop) {
                        mc.thePlayer.jump();
                        mc.thePlayer.onGround = false;
                        llIllIlllIllllI.legitHop = false;
                        return;
                     }

                     mc.thePlayer.onGround = false;
                     MovementUtils.strafe(0.375F);
                     mc.thePlayer.jump();
                     mc.thePlayer.motionY = 0.41D;
                     return;
                  }
               }

               if ((Boolean)llIllIlllIllllI.stairsValue.get() && (BlockUtils.getBlock(llIllIlllIlllII.down()) instanceof BlockStairs || BlockUtils.getBlock(llIllIlllIlllII) instanceof BlockStairs)) {
                  llIllIlllIllIIl = ((String)llIllIlllIllllI.stairsModeValue.get()).toLowerCase();
                  llIllIlllIllIII = -1;
                  switch(llIllIlllIllIIl.hashCode()) {
                  case 108960:
                     if (llIllIlllIllIIl.equals("new")) {
                        llIllIlllIllIII = 1;
                     }
                     break;
                  case 110119:
                     if (llIllIlllIllIIl.equals("old")) {
                        llIllIlllIllIII = 0;
                     }
                  }

                  switch(llIllIlllIllIII) {
                  case 0:
                     llIllIlllIllllI.boost((Float)llIllIlllIllllI.stairsBoostValue.get());
                     return;
                  case 1:
                     llIllIlllIllllI.fastHop = true;
                     if (llIllIlllIllllI.legitHop) {
                        mc.thePlayer.jump();
                        mc.thePlayer.onGround = false;
                        llIllIlllIllllI.legitHop = false;
                        return;
                     }

                     mc.thePlayer.onGround = false;
                     MovementUtils.strafe(0.375F);
                     mc.thePlayer.jump();
                     mc.thePlayer.motionY = 0.41D;
                     return;
                  }
               }

               llIllIlllIllllI.legitHop = true;
               if ((Boolean)llIllIlllIllllI.headBlockValue.get() && BlockUtils.getBlock(llIllIlllIlllII.up(2)) != Blocks.air) {
                  llIllIlllIllllI.boost((Float)llIllIlllIllllI.headBlockBoostValue.get());
                  return;
               }

               if ((Boolean)llIllIlllIllllI.iceValue.get() && (BlockUtils.getBlock(llIllIlllIlllII.down()) == Blocks.ice || BlockUtils.getBlock(llIllIlllIlllII.down()) == Blocks.packed_ice)) {
                  llIllIlllIllllI.boost((Float)llIllIlllIllllI.iceBoostValue.get());
                  return;
               }

               if ((Boolean)llIllIlllIllllI.snowValue.get() && BlockUtils.getBlock(llIllIlllIlllII) == Blocks.snow_layer && ((Boolean)llIllIlllIllllI.snowPortValue.get() || mc.thePlayer.posY - (double)((int)mc.thePlayer.posY) >= 0.125D)) {
                  if (mc.thePlayer.posY - (double)((int)mc.thePlayer.posY) >= 0.125D) {
                     llIllIlllIllllI.boost((Float)llIllIlllIllllI.snowBoostValue.get());
                  } else {
                     mc.thePlayer.jump();
                     llIllIlllIllllI.forceDown = true;
                  }

                  return;
               }

               if ((Boolean)llIllIlllIllllI.wallValue.get()) {
                  llIllIlllIllIIl = ((String)llIllIlllIllllI.wallModeValue.get()).toLowerCase();
                  llIllIlllIllIII = -1;
                  switch(llIllIlllIllIIl.hashCode()) {
                  case 108960:
                     if (llIllIlllIllIIl.equals("new")) {
                        llIllIlllIllIII = 1;
                     }
                     break;
                  case 110119:
                     if (llIllIlllIllIIl.equals("old")) {
                        llIllIlllIllIII = 0;
                     }
                  }

                  switch(llIllIlllIllIII) {
                  case 0:
                     if (mc.thePlayer.isCollidedHorizontally && llIllIlllIllllI.isNearBlock() || !(BlockUtils.getBlock(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY + 2.0D, mc.thePlayer.posZ)) instanceof BlockAir)) {
                        llIllIlllIllllI.boost((Float)llIllIlllIllllI.wallBoostValue.get());
                        return;
                     }
                     break;
                  case 1:
                     if (llIllIlllIllllI.isNearBlock() && !mc.thePlayer.movementInput.jump) {
                        mc.thePlayer.jump();
                        mc.thePlayer.motionY = 0.08D;
                        var10000 = mc.thePlayer;
                        var10000.motionX *= 0.99D;
                        var10000 = mc.thePlayer;
                        var10000.motionZ *= 0.99D;
                        llIllIlllIllllI.down = true;
                        return;
                     }
                  }
               }

               float llIllIlllIlllll = MovementUtils.getSpeed();
               if (llIllIlllIllllI.speed < (double)llIllIlllIlllll) {
                  llIllIlllIllllI.speed = (double)llIllIlllIlllll;
               }

               if ((Boolean)llIllIlllIllllI.bufferValue.get() && llIllIlllIllllI.speed > 0.20000000298023224D) {
                  llIllIlllIllllI.speed /= 1.0199999809265137D;
                  MovementUtils.strafe((float)llIllIlllIllllI.speed);
               }
            }

         } else {
            llIllIlllIllllI.reset();
         }
      } else {
         llIllIlllIllllI.reset();
      }
   }

   private boolean isNearBlock() {
      double llIllIllIllIlIl = mc.thePlayer;
      WorldClient llIllIllIllIlll = mc.theWorld;
      List<BlockPos> llIllIllIllIIll = new ArrayList();
      llIllIllIllIIll.add(new BlockPos(llIllIllIllIlIl.posX, llIllIllIllIlIl.posY + 1.0D, llIllIllIllIlIl.posZ - 0.7D));
      boolean var10001 = false;
      llIllIllIllIIll.add(new BlockPos(llIllIllIllIlIl.posX + 0.7D, llIllIllIllIlIl.posY + 1.0D, llIllIllIllIlIl.posZ));
      var10001 = false;
      llIllIllIllIIll.add(new BlockPos(llIllIllIllIlIl.posX, llIllIllIllIlIl.posY + 1.0D, llIllIllIllIlIl.posZ + 0.7D));
      var10001 = false;
      llIllIllIllIIll.add(new BlockPos(llIllIllIllIlIl.posX - 0.7D, llIllIllIllIlIl.posY + 1.0D, llIllIllIllIlIl.posZ));
      var10001 = false;
      Iterator llIllIllIllIIlI = llIllIllIllIIll.iterator();

      BlockPos llIllIllIlllIlI;
      do {
         if (!llIllIllIllIIlI.hasNext()) {
            return false;
         }

         llIllIllIlllIlI = (BlockPos)llIllIllIllIIlI.next();
      } while((llIllIllIllIlll.getBlockState(llIllIllIlllIlI).getBlock().getBlockBoundsMaxY() != llIllIllIllIlll.getBlockState(llIllIllIlllIlI).getBlock().getBlockBoundsMinY() + 1.0D || llIllIllIllIlll.getBlockState(llIllIllIlllIlI).getBlock().isTranslucent() || llIllIllIllIlll.getBlockState(llIllIllIlllIlI).getBlock() == Blocks.water || llIllIllIllIlll.getBlockState(llIllIllIlllIlI).getBlock() instanceof BlockSlab) && llIllIllIllIlll.getBlockState(llIllIllIlllIlI).getBlock() != Blocks.barrier);

      return true;
   }

   private void boost(float llIllIlllIIIIlI) {
      EntityPlayerSP var10000 = mc.thePlayer;
      var10000.motionX *= (double)llIllIlllIIIIlI;
      var10000 = mc.thePlayer;
      var10000.motionZ *= (double)llIllIlllIIIIlI;
      llIllIlllIIIIll.speed = (double)MovementUtils.getSpeed();
      if ((Boolean)llIllIlllIIIIll.speedLimitValue.get() && llIllIlllIIIIll.speed > (double)(Float)llIllIlllIIIIll.maxSpeedValue.get()) {
         llIllIlllIIIIll.speed = (double)(Float)llIllIlllIIIIll.maxSpeedValue.get();
      }

   }

   private void reset() {
      if (mc.thePlayer != null) {
         llIllIlllIIIllI.legitHop = true;
         llIllIlllIIIllI.speed = 0.0D;
         if (llIllIlllIIIllI.hadFastHop) {
            mc.thePlayer.speedInAir = 0.02F;
            llIllIlllIIIllI.hadFastHop = false;
         }

      }
   }

   @EventTarget
   public void onPacket(PacketEvent llIllIlllIlIIII) {
      Packet<?> llIllIlllIIllll = llIllIlllIlIIII.getPacket();
      if (llIllIlllIIllll instanceof S08PacketPlayerPosLook) {
         llIllIlllIlIlII.speed = 0.0D;
      }

   }

   public void onDisable() {
      llIllIlllIIlIIl.reset();
   }

   public void onEnable() {
      llIllIlllIIllII.reset();
   }
}
