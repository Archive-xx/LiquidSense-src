//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.command.commands;

import java.util.ArrayList;
import java.util.Iterator;
import me.AquaVit.liquidSense.modules.world.LightningCheck;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.utils.AStarCustomPathFinder;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.CustomVec3;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C13PacketPlayerAbilities;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.network.play.client.C03PacketPlayer.C05PacketPlayerLook;
import net.minecraft.network.play.client.C03PacketPlayer.C06PacketPlayerPosLook;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.BlockPos;
import org.jetbrains.annotations.NotNull;

public class TeleportCommand extends Command {
   // $FF: synthetic field
   public TimeUtils tpreset = new TimeUtils();
   // $FF: synthetic field
   private boolean tryCancelPacket;
   // $FF: synthetic field
   int PlayerY;
   // $FF: synthetic field
   private ArrayList<CustomVec3> path = new ArrayList();
   // $FF: synthetic field
   public boolean tp;
   // $FF: synthetic field
   public TimeUtils timer = new TimeUtils();
   // $FF: synthetic field
   public static BlockPos target;
   // $FF: synthetic field
   public Entity targetEntity;

   public TeleportCommand() {
      super("tp", new String[0]);
   }

   private ArrayList<CustomVec3> computePath(CustomVec3 llIlIlIlIll, CustomVec3 llIlIlIIIIl) {
      if (!llIlIlIllII.canPassThrow(new BlockPos(llIlIlIlIll.mc()))) {
         llIlIlIlIll = llIlIlIlIll.addVector(0.0D, 1.0D, 0.0D);
      }

      AStarCustomPathFinder llIlIlIlIIl = new AStarCustomPathFinder(llIlIlIlIll, llIlIlIIIIl);
      llIlIlIlIIl.compute();
      int llIlIlIlIII = 0;
      CustomVec3 llIlIlIIlll = null;
      short llIlIIlllIl = null;
      ArrayList<CustomVec3> llIlIIlllII = new ArrayList();
      ArrayList<CustomVec3> llIlIlIIlII = llIlIlIlIIl.getPath();

      for(Iterator llIlIIllIlI = llIlIlIIlII.iterator(); llIlIIllIlI.hasNext(); ++llIlIlIlIII) {
         float llIlIIllIIl = (CustomVec3)llIlIIllIlI.next();
         boolean var10001;
         if (llIlIlIlIII != 0 && llIlIlIlIII != llIlIlIIlII.size() - 1) {
            short llIlIIllIII = true;
            if (llIlIIllIIl.squareDistanceTo(llIlIIlllIl) > 25.0D) {
               llIlIIllIII = false;
            } else {
               double llIlIllIlII = Math.min(llIlIIlllIl.getX(), llIlIIllIIl.getX());
               byte llIlIIlIllI = Math.min(llIlIIlllIl.getY(), llIlIIllIIl.getY());
               short llIlIIlIlIl = Math.min(llIlIIlllIl.getZ(), llIlIIllIIl.getZ());
               boolean llIlIIlIlII = Math.max(llIlIIlllIl.getX(), llIlIIllIIl.getX());
               double llIlIllIIII = Math.max(llIlIIlllIl.getY(), llIlIIllIIl.getY());
               double llIlIlIllll = Math.max(llIlIIlllIl.getZ(), llIlIIllIIl.getZ());

               label54:
               for(int llIlIllIlIl = (int)llIlIllIlII; (double)llIlIllIlIl <= llIlIIlIlII; ++llIlIllIlIl) {
                  for(int llIlIllIllI = (int)llIlIIlIllI; (double)llIlIllIllI <= llIlIllIIII; ++llIlIllIllI) {
                     for(int llIlIllIlll = (int)llIlIIlIlIl; (double)llIlIllIlll <= llIlIlIllll; ++llIlIllIlll) {
                        if (!AStarCustomPathFinder.checkPositionValidity(llIlIllIlIl, llIlIllIllI, llIlIllIlll, false)) {
                           llIlIIllIII = false;
                           break label54;
                        }
                     }
                  }
               }
            }

            if (!llIlIIllIII) {
               llIlIIlllII.add(llIlIlIIlll.addVector(0.5D, 0.0D, 0.5D));
               var10001 = false;
               llIlIIlllIl = llIlIlIIlll;
            }
         } else {
            if (llIlIlIIlll != null) {
               llIlIIlllII.add(llIlIlIIlll.addVector(0.5D, 0.0D, 0.5D));
               var10001 = false;
            }

            llIlIIlllII.add(llIlIIllIIl.addVector(0.5D, 0.0D, 0.5D));
            var10001 = false;
            llIlIIlllIl = llIlIIllIIl;
         }

         llIlIlIIlll = llIlIIllIIl;
      }

      return llIlIIlllII;
   }

   @EventTarget
   public void onPacket(PacketEvent llIllIllIII) {
      if (llIllIllIll.tryCancelPacket && (llIllIllIII.getPacket() instanceof C04PacketPlayerPosition || llIllIllIII.getPacket() instanceof C06PacketPlayerPosLook || llIllIllIII.getPacket() instanceof C05PacketPlayerLook)) {
         llIllIllIII.cancelEvent();
      }

      if (llIllIllIII.getPacket() instanceof S08PacketPlayerPosLook) {
         S08PacketPlayerPosLook llIllIlllII = (S08PacketPlayerPosLook)llIllIllIII.getPacket();
         llIllIlllII.pitch = mc.thePlayer.rotationPitch;
         llIllIlllII.yaw = mc.thePlayer.rotationYaw;
         llIllIllIll.tryCancelPacket = false;
         if (llIllIllIII.getPacket() instanceof S08PacketPlayerPosLook && !llIllIllIll.tp) {
            ClientUtils.displayChatMessage("暂时绕过�??作弊!");
            llIllIllIll.tp = true;
            llIllIllIII.cancelEvent();
         }
      }

   }

   private boolean canPassThrow(BlockPos llIlIIIlIll) {
      boolean llIlIIIlIII = mc.theWorld.getBlockState(new BlockPos(llIlIIIlIll.getX(), llIlIIIlIll.getY(), llIlIIIlIll.getZ())).getBlock();
      return llIlIIIlIII.getMaterial() == Material.air || llIlIIIlIII.getMaterial() == Material.plants || llIlIIIlIII.getMaterial() == Material.vine || llIlIIIlIII == Blocks.ladder || llIlIIIlIII == Blocks.water || llIlIIIlIII == Blocks.flowing_water || llIlIIIlIII == Blocks.wall_sign || llIlIIIlIII == Blocks.standing_sign;
   }

   public void execute(@NotNull String[] llIlllIlIII) {
      if (llIlllIlIII.length == 0) {
         ClientUtils.displayChatMessage("[Teleport] Please type X Y Z");
      }

      try {
         llIlllIlIll.timer.reset();
         llIlllIlIll.tp = false;
         llIlllIlIll.targetEntity = null;
         target = null;
         if (llIlllIlIII.length == 3) {
            target = new BlockPos(Integer.parseInt(llIlllIlIII[0]), Integer.parseInt(llIlllIlIII[1]), Integer.parseInt(llIlllIlIII[2]));
         } else {
            Iterator llIlllIIlll;
            Entity llIlllIIllI;
            if (llIlllIlIII.length == 2) {
               if (!llIlllIlIII[0].contains("LN")) {
                  llIlllIIlll = mc.theWorld.loadedEntityList.iterator();

                  while(llIlllIIlll.hasNext()) {
                     llIlllIIllI = (Entity)llIlllIIlll.next();
                     if (llIlllIIllI instanceof EntityPlayer && mc.thePlayer != llIlllIIllI && llIlllIIllI.getName().equalsIgnoreCase(llIlllIlIII[0])) {
                        llIlllIlIll.targetEntity = llIlllIIllI;
                        llIlllIlIll.PlayerY = Integer.parseInt(llIlllIlIII[1]);
                        break;
                     }
                  }

                  if (llIlllIlIll.targetEntity == null) {
                     ClientUtils.displayChatMessage(String.valueOf((new StringBuilder()).append("Can not locate ").append(llIlllIlIII[0])));
                     return;
                  }

                  ClientUtils.displayChatMessage(String.valueOf((new StringBuilder()).append("Located Player ").append(llIlllIlIll.targetEntity.getName()).append("And Pos Y Added ").append(llIlllIlIll.PlayerY).append(" !")));
               }
            } else if (llIlllIlIII.length == 1) {
               if (llIlllIlIII[0].contains("FN")) {
                  target = new BlockPos(LightningCheck.x, LightningCheck.y, LightningCheck.z);
                  ClientUtils.displayChatMessage("Located Lighting !");
               }

               if (!llIlllIlIII[0].contains("LN") && !llIlllIlIII[0].contains("TCK")) {
                  llIlllIIlll = mc.theWorld.loadedEntityList.iterator();

                  while(llIlllIIlll.hasNext()) {
                     llIlllIIllI = (Entity)llIlllIIlll.next();
                     if (llIlllIIllI instanceof EntityPlayer && mc.thePlayer != llIlllIIllI && llIlllIIllI.getName().equalsIgnoreCase(llIlllIlIII[0])) {
                        llIlllIlIll.targetEntity = llIlllIIllI;
                        llIlllIlIll.PlayerY = 0;
                        break;
                     }
                  }

                  if (llIlllIlIll.targetEntity == null) {
                     ClientUtils.displayChatMessage(String.valueOf((new StringBuilder()).append("Can not locate ").append(llIlllIlIII[0])));
                     return;
                  }

                  ClientUtils.displayChatMessage(String.valueOf((new StringBuilder()).append("Located Player ").append(llIlllIlIll.targetEntity.getName()).append("And Pos Y Added ").append(llIlllIlIll.PlayerY).append(" !")));
               }
            }
         }

         mc.getNetHandler().addToSendQueue(new C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY + 0.41999998688697815D, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, false));
         mc.getNetHandler().addToSendQueue(new C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY + 0.6753199986886979D, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, false));
         llIlllIlIll.tryCancelPacket = true;
      } catch (Exception var4) {
      }

   }

   @EventTarget
   private void onUpdate(MotionEvent llIllIlIIII) {
      if (llIllIlIIIl.timer.hasReached(8500L)) {
         ClientUtils.displayChatMessage("Failed to teleport.");
      }

      if (llIllIlIIIl.tp) {
         llIllIlIIIl.tp = false;
         CustomVec3 llIllIlIIll = new CustomVec3(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
         if (target == null) {
            target = new BlockPos(llIllIlIIIl.targetEntity.posX, llIllIlIIIl.targetEntity.posY + (double)llIllIlIIIl.PlayerY, llIllIlIIIl.targetEntity.posZ);
         }

         CustomVec3 llIllIlIIlI = new CustomVec3((double)target.getX(), (double)target.getY(), (double)target.getZ());
         llIllIlIIIl.path = llIllIlIIIl.computePath(llIllIlIIll, llIllIlIIlI);
         llIllIlIIIl.tpreset.reset();
         ClientUtils.displayChatMessage(String.valueOf((new StringBuilder()).append("Teleporting to X:").append(target.getX()).append(" Y:").append(target.getY()).append(" Z:").append(target.getZ())));
         (new Thread() {
            public void run() {
               try {
                  sleep(1000L);
               } catch (InterruptedException var4) {
                  var4.printStackTrace();
               }

               Iterator llllllllllllllllllIlllIIIllIIlII = llIllIlIIIl.path.iterator();

               while(llllllllllllllllllIlllIIIllIIlII.hasNext()) {
                  CustomVec3 llllllllllllllllllIlllIllIIllllI = (CustomVec3)llllllllllllllllllIlllIIIllIIlII.next();
                  TeleportCommand.mc.thePlayer.sendQueue.getNetworkManager().sendPacket(new C04PacketPlayerPosition(llllllllllllllllllIlllIllIIllllI.getX(), llllllllllllllllllIlllIllIIllllI.getY(), llllllllllllllllllIlllIllIIllllI.getZ(), true));
                  C13PacketPlayerAbilities llllllllllllllllllIlllIllIlIIIII = new C13PacketPlayerAbilities();
                  llllllllllllllllllIlllIllIlIIIII.setInvulnerable(false);
                  llllllllllllllllllIlllIllIlIIIII.setCreativeMode(false);
                  llllllllllllllllllIlllIllIlIIIII.setFlying(false);
                  llllllllllllllllllIlllIllIlIIIII.setAllowFlying(false);
                  llllllllllllllllllIlllIllIlIIIII.setFlySpeed(1.0E8F);
                  llllllllllllllllllIlllIllIlIIIII.setWalkSpeed(1.0E8F);
                  TeleportCommand.mc.getNetHandler().getNetworkManager().sendPacket(llllllllllllllllllIlllIllIlIIIII);
               }

               TeleportCommand.mc.thePlayer.sendQueue.getNetworkManager().sendPacket(new C04PacketPlayerPosition((double)TeleportCommand.target.getX(), (double)TeleportCommand.target.getY(), (double)TeleportCommand.target.getZ(), TeleportCommand.mc.thePlayer.onGround));
               TeleportCommand.mc.thePlayer.setPosition((double)TeleportCommand.target.getX(), (double)TeleportCommand.target.getY(), (double)TeleportCommand.target.getZ());
               TeleportCommand.mc.thePlayer.sendQueue.getNetworkManager().sendPacket(new C04PacketPlayerPosition((double)TeleportCommand.target.getX(), (double)((float)TeleportCommand.target.getY() - MovementUtils.getDistanceToGround(TeleportCommand.mc.thePlayer)), (double)TeleportCommand.target.getZ(), TeleportCommand.mc.thePlayer.onGround));
               TeleportCommand.mc.thePlayer.setPosition((double)TeleportCommand.target.getX(), (double)((float)TeleportCommand.target.getY() - MovementUtils.getDistanceToGround(TeleportCommand.mc.thePlayer)), (double)TeleportCommand.target.getZ());
               ClientUtils.displayChatMessage("Teleported!");
            }
         }).start();
      }

   }

   @EventTarget
   public void onMove(MoveEvent llIlllIIIlI) {
      if (llIlllIIIll.tryCancelPacket) {
         llIlllIIIlI.cancelEvent();
      }

   }
}
