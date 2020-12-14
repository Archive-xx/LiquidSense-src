//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.misc;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.timer.TimerOther;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S0BPacketAnimation;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.WorldSettings.GameType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@ModuleInfo(
   name = "AntiBot",
   description = "Prevents KillAura from attacking AntiCheat bots.",
   category = ModuleCategory.MISC
)
public class AntiBot extends Module {
   // $FF: synthetic field
   private final BoolValue duplicateInWorldValue = new BoolValue("DuplicateInWorld", false);
   // $FF: synthetic field
   private final Map<Integer, Integer> invaildGround = new HashMap();
   // $FF: synthetic field
   private final BoolValue tabValue = new BoolValue("Tab", true);
   // $FF: synthetic field
   private final BoolValue needHitValue = new BoolValue("NeedHit", false);
   // $FF: synthetic field
   private final BoolValue killer = new BoolValue("BotKiller", true);
   // $FF: synthetic field
   private final List<Integer> ground = new ArrayList();
   // $FF: synthetic field
   private final List<Integer> air = new ArrayList();
   // $FF: synthetic field
   private final BoolValue invaildGroundValue = new BoolValue("InvaildGround", true);
   // $FF: synthetic field
   private final IntegerValue livingTime = new IntegerValue("LivingTimeValue", 160, 0, 500);
   // $FF: synthetic field
   private final BoolValue wasInvisibleValue = new BoolValue("WasInvisible", false);
   // $FF: synthetic field
   private static List<EntityPlayer> invalid = new ArrayList();
   // $FF: synthetic field
   public static final List<String> playerName = new ArrayList();
   // $FF: synthetic field
   private final BoolValue duplicateInTabValue = new BoolValue("DuplicateInTab", false);
   // $FF: synthetic field
   private final List<Integer> hitted = new ArrayList();
   // $FF: synthetic field
   private final BoolValue healthValue = new BoolValue("Health", false);
   // $FF: synthetic field
   private final BoolValue armorValue = new BoolValue("Armor", false);
   // $FF: synthetic field
   private final BoolValue entityIDValue = new BoolValue("EntityID", true);
   // $FF: synthetic field
   private final BoolValue airValue = new BoolValue("Air", false);
   // $FF: synthetic field
   TimerOther lastRemoved = new TimerOther();
   // $FF: synthetic field
   private final BoolValue livingTimeValue = new BoolValue("LivingTime", false);
   // $FF: synthetic field
   private final BoolValue derpValue = new BoolValue("Derp", true);
   // $FF: synthetic field
   private TimerOther timer = new TimerOther();
   // $FF: synthetic field
   private final BoolValue groundValue = new BoolValue("Ground", true);
   // $FF: synthetic field
   private final List<Integer> swing = new ArrayList();
   // $FF: synthetic field
   private final BoolValue swingValue = new BoolValue("Swing", false);
   // $FF: synthetic field
   private final BoolValue colorValue = new BoolValue("Color", false);
   // $FF: synthetic field
   private static List<EntityPlayer> removed = new ArrayList();
   // $FF: synthetic field
   private final BoolValue hytBedwarsValue = new BoolValue("HYTBedwars", false);
   // $FF: synthetic field
   private final List<Integer> invisible = new ArrayList();
   // $FF: synthetic field
   private final ListValue tabModeValue = new ListValue("TabMode", new String[]{"Equals", "Contains"}, "Contains");
   // $FF: synthetic field
   private final BoolValue pingValue = new BoolValue("Ping", false);

   @EventTarget
   public void onPacket(PacketEvent llllllllllllllllllIIllIIIllIlIII) {
      if (mc.thePlayer != null && mc.theWorld != null) {
         Packet<?> llllllllllllllllllIIllIIIllIlIlI = llllllllllllllllllIIllIIIllIlIII.getPacket();
         boolean var10001;
         if (llllllllllllllllllIIllIIIllIlIlI instanceof S02PacketChat && (Boolean)llllllllllllllllllIIllIIIllIllII.hytBedwarsValue.get()) {
            boolean llllllllllllllllllIIllIIIllIIllI = (S02PacketChat)llllllllllllllllllIIllIIIllIlIlI;
            Matcher llllllllllllllllllIIllIIIlllIIll = Pattern.compile("�?�死了 (.*?)\\(").matcher(llllllllllllllllllIIllIIIllIIllI.getChatComponent().getUnformattedText());
            Matcher llllllllllllllllllIIllIIIlllIIlI = Pattern.compile("> (.*?)\\(").matcher(llllllllllllllllllIIllIIIllIIllI.getChatComponent().getUnformattedText());
            final String llllllllllllllllllIIllIIIllIIIll;
            if (llllllllllllllllllIIllIIIlllIIll.find()) {
               llllllllllllllllllIIllIIIllIIIll = llllllllllllllllllIIllIIIlllIIll.group(1);
               if (!llllllllllllllllllIIllIIIllIIIll.equals("") && !playerName.contains(llllllllllllllllllIIllIIIllIIIll)) {
                  playerName.add(llllllllllllllllllIIllIIIllIIIll);
                  var10001 = false;
                  (new Thread(new Runnable() {
                     public void run() {
                        try {
                           Thread.sleep(6000L);
                           AntiBot.playerName.remove(llllllllllllllllllIIllIIIllIIIll);
                           boolean var10001 = false;
                        } catch (InterruptedException var2) {
                           var2.printStackTrace();
                        }

                     }
                  })).start();
               }
            }

            if (llllllllllllllllllIIllIIIlllIIlI.find()) {
               llllllllllllllllllIIllIIIllIIIll = llllllllllllllllllIIllIIIlllIIlI.group(1);
               if (!llllllllllllllllllIIllIIIllIIIll.equals("") && !playerName.contains(llllllllllllllllllIIllIIIllIIIll)) {
                  playerName.add(llllllllllllllllllIIllIIIllIIIll);
                  var10001 = false;
                  (new Thread(new Runnable() {
                     public void run() {
                        try {
                           Thread.sleep(6000L);
                           AntiBot.playerName.remove(llllllllllllllllllIIllIIIllIIIll);
                           boolean var10001 = false;
                        } catch (InterruptedException var2) {
                           var2.printStackTrace();
                        }

                     }
                  })).start();
               }
            }
         }

         Entity llllllllllllllllllIIllIIIllIllll;
         if (llllllllllllllllllIIllIIIllIlIlI instanceof S14PacketEntity) {
            boolean llllllllllllllllllIIllIIIllIIllI = (S14PacketEntity)llllllllllllllllllIIllIIIllIlIII.getPacket();
            llllllllllllllllllIIllIIIllIllll = llllllllllllllllllIIllIIIllIIllI.getEntity(mc.theWorld);
            if (llllllllllllllllllIIllIIIllIllll instanceof EntityPlayer) {
               if (llllllllllllllllllIIllIIIllIIllI.getOnGround() && !llllllllllllllllllIIllIIIllIllII.ground.contains(llllllllllllllllllIIllIIIllIllll.getEntityId())) {
                  llllllllllllllllllIIllIIIllIllII.ground.add(llllllllllllllllllIIllIIIllIllll.getEntityId());
                  var10001 = false;
               }

               if (!llllllllllllllllllIIllIIIllIIllI.getOnGround() && !llllllllllllllllllIIllIIIllIllII.air.contains(llllllllllllllllllIIllIIIllIllll.getEntityId())) {
                  llllllllllllllllllIIllIIIllIllII.air.add(llllllllllllllllllIIllIIIllIllll.getEntityId());
                  var10001 = false;
               }

               if (llllllllllllllllllIIllIIIllIIllI.getOnGround()) {
                  if (llllllllllllllllllIIllIIIllIllll.prevPosY != llllllllllllllllllIIllIIIllIllll.posY) {
                     llllllllllllllllllIIllIIIllIllII.invaildGround.put(llllllllllllllllllIIllIIIllIllll.getEntityId(), (Integer)llllllllllllllllllIIllIIIllIllII.invaildGround.getOrDefault(llllllllllllllllllIIllIIIllIllll.getEntityId(), 0) + 1);
                     var10001 = false;
                  }
               } else {
                  String llllllllllllllllllIIllIIIllIIlII = (Integer)llllllllllllllllllIIllIIIllIllII.invaildGround.getOrDefault(llllllllllllllllllIIllIIIllIllll.getEntityId(), 0) / 2;
                  if (llllllllllllllllllIIllIIIllIIlII <= 0) {
                     llllllllllllllllllIIllIIIllIllII.invaildGround.remove(llllllllllllllllllIIllIIIllIllll.getEntityId());
                     var10001 = false;
                  } else {
                     llllllllllllllllllIIllIIIllIllII.invaildGround.put(llllllllllllllllllIIllIIIllIllll.getEntityId(), llllllllllllllllllIIllIIIllIIlII);
                     var10001 = false;
                  }
               }

               if (llllllllllllllllllIIllIIIllIllll.isInvisible() && !llllllllllllllllllIIllIIIllIllII.invisible.contains(llllllllllllllllllIIllIIIllIllll.getEntityId())) {
                  llllllllllllllllllIIllIIIllIllII.invisible.add(llllllllllllllllllIIllIIIllIllll.getEntityId());
                  var10001 = false;
               }
            }
         }

         if (llllllllllllllllllIIllIIIllIlIlI instanceof S0BPacketAnimation) {
            boolean llllllllllllllllllIIllIIIllIIllI = (S0BPacketAnimation)llllllllllllllllllIIllIIIllIlIII.getPacket();
            llllllllllllllllllIIllIIIllIllll = mc.theWorld.getEntityByID(llllllllllllllllllIIllIIIllIIllI.getEntityID());
            if (llllllllllllllllllIIllIIIllIllll instanceof EntityLivingBase && llllllllllllllllllIIllIIIllIIllI.getAnimationType() == 0 && !llllllllllllllllllIIllIIIllIllII.swing.contains(llllllllllllllllllIIllIIIllIllll.getEntityId())) {
               llllllllllllllllllIIllIIIllIllII.swing.add(llllllllllllllllllIIllIIIllIllll.getEntityId());
               var10001 = false;
            }
         }

      }
   }

   @EventTarget
   public void onWorld(WorldEvent llllllllllllllllllIIllIIIlIlIlll) {
      llllllllllllllllllIIllIIIlIlIllI.clearAll();
   }

   @EventTarget
   public void onAttack(AttackEvent llllllllllllllllllIIllIIIlIllllI) {
      boolean llllllllllllllllllIIllIIIlIllIlI = llllllllllllllllllIIllIIIlIllllI.getTargetEntity();
      if (llllllllllllllllllIIllIIIlIllIlI instanceof EntityLivingBase && !llllllllllllllllllIIllIIIlIlllII.hitted.contains(llllllllllllllllllIIllIIIlIllIlI.getEntityId())) {
         llllllllllllllllllIIllIIIlIlllII.hitted.add(llllllllllllllllllIIllIIIlIllIlI.getEntityId());
         boolean var10001 = false;
      }

   }

   public static List<EntityPlayer> getTabPlayerList() {
      Minecraft llllllllllllllllllIIllIIlIIIlIIl = mc;
      NetHandlerPlayClient llllllllllllllllllIIllIIlIIIlIII = llllllllllllllllllIIllIIlIIIlIIl.thePlayer.sendQueue;
      String llllllllllllllllllIIllIIlIIIIIll = new ArrayList();
      List llllllllllllllllllIIllIIlIIIIllI = Ordering.from(new AntiBot.PlayerComparator()).sortedCopy(llllllllllllllllllIIllIIlIIIlIII.getPlayerInfoMap());
      Iterator llllllllllllllllllIIllIIlIIIIIIl = llllllllllllllllllIIllIIlIIIIllI.iterator();

      while(llllllllllllllllllIIllIIlIIIIIIl.hasNext()) {
         Object llllllllllllllllllIIllIIlIIIlIlI = llllllllllllllllllIIllIIlIIIIIIl.next();
         NetworkPlayerInfo llllllllllllllllllIIllIIlIIIllII = (NetworkPlayerInfo)llllllllllllllllllIIllIIlIIIlIlI;
         if (llllllllllllllllllIIllIIlIIIllII != null) {
            llllllllllllllllllIIllIIlIIIIIll.add(llllllllllllllllllIIllIIlIIIlIIl.theWorld.getPlayerEntityByName(llllllllllllllllllIIllIIlIIIllII.getGameProfile().getName()));
            boolean var10001 = false;
         }
      }

      return llllllllllllllllllIIllIIlIIIIIll;
   }

   public static boolean isBot(EntityLivingBase llllllllllllllllllIIllIIIlIIIlIl) {
      if (!(llllllllllllllllllIIllIIIlIIIlIl instanceof EntityPlayer)) {
         return false;
      } else {
         char llllllllllllllllllIIllIIIlIIIIlI = (AntiBot)LiquidBounce.moduleManager.getModule(AntiBot.class);
         if (llllllllllllllllllIIllIIIlIIIIlI != null && llllllllllllllllllIIllIIIlIIIIlI.getState()) {
            if ((Boolean)llllllllllllllllllIIllIIIlIIIIlI.colorValue.get() && !llllllllllllllllllIIllIIIlIIIlIl.getDisplayName().getFormattedText().replace("§r", "").contains("§")) {
               return true;
            } else if ((Boolean)llllllllllllllllllIIllIIIlIIIIlI.hytBedwarsValue.get() && playerName.contains(llllllllllllllllllIIllIIIlIIIlIl.getName())) {
               return true;
            } else if ((Boolean)llllllllllllllllllIIllIIIlIIIIlI.livingTimeValue.get() && llllllllllllllllllIIllIIIlIIIlIl.ticksExisted < (Integer)llllllllllllllllllIIllIIIlIIIIlI.livingTime.get()) {
               return true;
            } else if ((Boolean)llllllllllllllllllIIllIIIlIIIIlI.groundValue.get() && !llllllllllllllllllIIllIIIlIIIIlI.ground.contains(llllllllllllllllllIIllIIIlIIIlIl.getEntityId())) {
               return true;
            } else if ((Boolean)llllllllllllllllllIIllIIIlIIIIlI.airValue.get() && !llllllllllllllllllIIllIIIlIIIIlI.air.contains(llllllllllllllllllIIllIIIlIIIlIl.getEntityId())) {
               return true;
            } else if ((Boolean)llllllllllllllllllIIllIIIlIIIIlI.swingValue.get() && !llllllllllllllllllIIllIIIlIIIIlI.swing.contains(llllllllllllllllllIIllIIIlIIIlIl.getEntityId())) {
               return true;
            } else if ((Boolean)llllllllllllllllllIIllIIIlIIIIlI.healthValue.get() && llllllllllllllllllIIllIIIlIIIlIl.getHealth() > 20.0F) {
               return true;
            } else if ((Boolean)llllllllllllllllllIIllIIIlIIIIlI.entityIDValue.get() && (llllllllllllllllllIIllIIIlIIIlIl.getEntityId() >= 1000000000 || llllllllllllllllllIIllIIIlIIIlIl.getEntityId() <= -1)) {
               return true;
            } else if (!(Boolean)llllllllllllllllllIIllIIIlIIIIlI.derpValue.get() || !(llllllllllllllllllIIllIIIlIIIlIl.rotationPitch > 90.0F) && !(llllllllllllllllllIIllIIIlIIIlIl.rotationPitch < -90.0F)) {
               if ((Boolean)llllllllllllllllllIIllIIIlIIIIlI.wasInvisibleValue.get() && llllllllllllllllllIIllIIIlIIIIlI.invisible.contains(llllllllllllllllllIIllIIIlIIIlIl.getEntityId())) {
                  return true;
               } else {
                  EntityPlayer llllllllllllllllllIIllIIIlIIIIIl;
                  if ((Boolean)llllllllllllllllllIIllIIIlIIIIlI.armorValue.get()) {
                     llllllllllllllllllIIllIIIlIIIIIl = (EntityPlayer)llllllllllllllllllIIllIIIlIIIlIl;
                     if (llllllllllllllllllIIllIIIlIIIIIl.inventory.armorInventory[0] == null && llllllllllllllllllIIllIIIlIIIIIl.inventory.armorInventory[1] == null && llllllllllllllllllIIllIIIlIIIIIl.inventory.armorInventory[2] == null && llllllllllllllllllIIllIIIlIIIIIl.inventory.armorInventory[3] == null) {
                        return true;
                     }
                  }

                  if ((Boolean)llllllllllllllllllIIllIIIlIIIIlI.pingValue.get()) {
                     llllllllllllllllllIIllIIIlIIIIIl = (EntityPlayer)llllllllllllllllllIIllIIIlIIIlIl;
                     if (mc.getNetHandler().getPlayerInfo(llllllllllllllllllIIllIIIlIIIIIl.getUniqueID()).getResponseTime() == 0) {
                        return true;
                     }
                  }

                  if ((Boolean)llllllllllllllllllIIllIIIlIIIIlI.needHitValue.get() && !llllllllllllllllllIIllIIIlIIIIlI.hitted.contains(llllllllllllllllllIIllIIIlIIIlIl.getEntityId())) {
                     return true;
                  } else if ((Boolean)llllllllllllllllllIIllIIIlIIIIlI.invaildGroundValue.get() && (Integer)llllllllllllllllllIIllIIIlIIIIlI.invaildGround.getOrDefault(llllllllllllllllllIIllIIIlIIIlIl.getEntityId(), 0) >= 10) {
                     return true;
                  } else {
                     if ((Boolean)llllllllllllllllllIIllIIIlIIIIlI.tabValue.get()) {
                        boolean llllllllllllllllllIIllIIIlIIIlll = ((String)llllllllllllllllllIIllIIIlIIIIlI.tabModeValue.get()).equalsIgnoreCase("Equals");
                        String llllllllllllllllllIIllIIIlIIIllI = ColorUtils.stripColor(llllllllllllllllllIIllIIIlIIIlIl.getDisplayName().getFormattedText());
                        if (llllllllllllllllllIIllIIIlIIIllI != null) {
                           Iterator llllllllllllllllllIIllIIIIllllll = mc.getNetHandler().getPlayerInfoMap().iterator();

                           while(true) {
                              String llllllllllllllllllIIllIIIlIIlIIl;
                              do {
                                 if (!llllllllllllllllllIIllIIIIllllll.hasNext()) {
                                    return true;
                                 }

                                 NetworkPlayerInfo llllllllllllllllllIIllIIIlIIlIII = (NetworkPlayerInfo)llllllllllllllllllIIllIIIIllllll.next();
                                 llllllllllllllllllIIllIIIlIIlIIl = ColorUtils.stripColor(EntityUtils.getName(llllllllllllllllllIIllIIIlIIlIII));
                              } while(llllllllllllllllllIIllIIIlIIlIIl == null);

                              if (llllllllllllllllllIIllIIIlIIIlll) {
                                 if (llllllllllllllllllIIllIIIlIIIllI.equals(llllllllllllllllllIIllIIIlIIlIIl)) {
                                    break;
                                 }
                              } else if (llllllllllllllllllIIllIIIlIIIllI.contains(llllllllllllllllllIIllIIIlIIlIIl)) {
                                 break;
                              }
                           }

                           return false;
                        }
                     }

                     if ((Boolean)llllllllllllllllllIIllIIIlIIIIlI.duplicateInWorldValue.get() && mc.theWorld.loadedEntityList.stream().filter((llllllllllllllllllIIllIIIIllIlII) -> {
                        return llllllllllllllllllIIllIIIIllIlII instanceof EntityPlayer && ((EntityPlayer)llllllllllllllllllIIllIIIIllIlII).getDisplayNameString().equals(((EntityPlayer)llllllllllllllllllIIllIIIIllIlII).getDisplayNameString());
                     }).count() > 1L) {
                        return true;
                     } else if ((Boolean)llllllllllllllllllIIllIIIlIIIIlI.duplicateInTabValue.get() && mc.getNetHandler().getPlayerInfoMap().stream().filter((llllllllllllllllllIIllIIIIlllIIl) -> {
                        return llllllllllllllllllIIllIIIlIIIlIl.getName().equals(ColorUtils.stripColor(EntityUtils.getName(llllllllllllllllllIIllIIIIlllIIl)));
                     }).count() > 1L) {
                        return true;
                     } else {
                        return llllllllllllllllllIIllIIIlIIIlIl.getName().isEmpty() || llllllllllllllllllIIllIIIlIIIlIl.getName().equals(mc.thePlayer.getName());
                     }
                  }
               }
            } else {
               return true;
            }
         } else {
            return false;
         }
      }
   }

   private void clearAll() {
      llllllllllllllllllIIllIIIlIlIlII.hitted.clear();
      llllllllllllllllllIIllIIIlIlIlII.swing.clear();
      llllllllllllllllllIIllIIIlIlIlII.ground.clear();
      llllllllllllllllllIIllIIIlIlIlII.invaildGround.clear();
      llllllllllllllllllIIllIIIlIlIlII.invisible.clear();
      playerName.clear();
   }

   @EventTarget
   public void onUpdate(UpdateEvent llllllllllllllllllIIllIIlIlIIIIl) {
      if ((Boolean)llllllllllllllllllIIllIIlIlIIIII.killer.get() && !removed.isEmpty() && llllllllllllllllllIIllIIlIlIIIII.lastRemoved.delay(1000.0F)) {
         if (removed.size() == 1) {
            mc.thePlayer.addChatMessage(new ChatComponentText(String.valueOf((new StringBuilder()).append("§b").append(removed.size()).append(" bot has been removed"))));
         } else {
            mc.thePlayer.addChatMessage(new ChatComponentText(String.valueOf((new StringBuilder()).append("§f").append(removed.size()).append(" bots has been removed"))));
         }

         llllllllllllllllllIIllIIlIlIIIII.lastRemoved.reset();
         removed.clear();
      }

      if (!invalid.isEmpty() && llllllllllllllllllIIllIIlIlIIIII.timer.delay(1000.0F)) {
         invalid.clear();
         llllllllllllllllllIIllIIlIlIIIII.timer.reset();
      }

      Iterator llllllllllllllllllIIllIIlIIllllI = mc.theWorld.getLoadedEntityList().iterator();

      while(true) {
         EntityPlayer llllllllllllllllllIIllIIlIlIIlII;
         do {
            do {
               Object llllllllllllllllllIIllIIlIIlllIl;
               do {
                  if (!llllllllllllllllllIIllIIlIIllllI.hasNext()) {
                     return;
                  }

                  llllllllllllllllllIIllIIlIIlllIl = llllllllllllllllllIIllIIlIIllllI.next();
               } while(!(llllllllllllllllllIIllIIlIIlllIl instanceof EntityPlayer));

               llllllllllllllllllIIllIIlIlIIlII = (EntityPlayer)llllllllllllllllllIIllIIlIIlllIl;
            } while(llllllllllllllllllIIllIIlIlIIlII == mc.thePlayer);
         } while(invalid.contains(llllllllllllllllllIIllIIlIlIIlII));

         String llllllllllllllllllIIllIIlIlIIlll = llllllllllllllllllIIllIIlIlIIlII.getDisplayName().getFormattedText();
         String llllllllllllllllllIIllIIlIlIIllI = llllllllllllllllllIIllIIlIlIIlII.getCustomNameTag();
         char llllllllllllllllllIIllIIlIIllIIl = llllllllllllllllllIIllIIlIlIIlII.getName();
         boolean var10001;
         if (llllllllllllllllllIIllIIlIlIIlII.isInvisible() && !llllllllllllllllllIIllIIlIlIIlll.startsWith("§c") && llllllllllllllllllIIllIIlIlIIlll.endsWith("§r") && llllllllllllllllllIIllIIlIlIIllI.equals(llllllllllllllllllIIllIIlIIllIIl)) {
            double llllllllllllllllllIIllIIlIlIlIll = Math.abs(llllllllllllllllllIIllIIlIlIIlII.posX - mc.thePlayer.posX);
            byte llllllllllllllllllIIllIIlIIlIlll = Math.abs(llllllllllllllllllIIllIIlIlIIlII.posY - mc.thePlayer.posY);
            double llllllllllllllllllIIllIIlIlIlIIl = Math.abs(llllllllllllllllllIIllIIlIlIIlII.posZ - mc.thePlayer.posZ);
            double llllllllllllllllllIIllIIlIlIlIII = Math.sqrt(llllllllllllllllllIIllIIlIlIlIll * llllllllllllllllllIIllIIlIlIlIll + llllllllllllllllllIIllIIlIlIlIIl * llllllllllllllllllIIllIIlIlIlIIl);
            if (llllllllllllllllllIIllIIlIIlIlll < 13.0D && llllllllllllllllllIIllIIlIIlIlll > 10.0D && llllllllllllllllllIIllIIlIlIlIII < 3.0D) {
               List<EntityPlayer> llllllllllllllllllIIllIIlIIlllll = getTabPlayerList();
               if (!llllllllllllllllllIIllIIlIIlllll.contains(llllllllllllllllllIIllIIlIlIIlII)) {
                  if (llllllllllllllllllIIllIIlIlIIIII.killer.get() != null) {
                     llllllllllllllllllIIllIIlIlIIIII.lastRemoved.reset();
                     removed.add(llllllllllllllllllIIllIIlIlIIlII);
                     var10001 = false;
                     mc.theWorld.removeEntity(llllllllllllllllllIIllIIlIlIIlII);
                  }

                  invalid.add(llllllllllllllllllIIllIIlIlIIlII);
                  var10001 = false;
               }
            }
         }

         if (!llllllllllllllllllIIllIIlIlIIlll.startsWith("§") && llllllllllllllllllIIllIIlIlIIlll.endsWith("§r")) {
            invalid.add(llllllllllllllllllIIllIIlIlIIlII);
            var10001 = false;
         }

         if (llllllllllllllllllIIllIIlIlIIlII.isInvisible() && !llllllllllllllllllIIllIIlIlIIllI.equalsIgnoreCase("") && llllllllllllllllllIIllIIlIlIIllI.toLowerCase().contains("§c§c") && llllllllllllllllllIIllIIlIIllIIl.contains("§c")) {
            if (llllllllllllllllllIIllIIlIlIIIII.killer.get() != null) {
               llllllllllllllllllIIllIIlIlIIIII.lastRemoved.reset();
               removed.add(llllllllllllllllllIIllIIlIlIIlII);
               var10001 = false;
               mc.theWorld.removeEntity(llllllllllllllllllIIllIIlIlIIlII);
            }

            invalid.add(llllllllllllllllllIIllIIlIlIIlII);
            var10001 = false;
         }

         if (!llllllllllllllllllIIllIIlIlIIllI.equalsIgnoreCase("") && llllllllllllllllllIIllIIlIlIIllI.toLowerCase().contains("§c") && llllllllllllllllllIIllIIlIlIIllI.toLowerCase().contains("§r") || llllllllllllllllllIIllIIlIlIIllI.endsWith("§c")) {
            if (llllllllllllllllllIIllIIlIlIIIII.killer.get() != null) {
               llllllllllllllllllIIllIIlIlIIIII.lastRemoved.reset();
               removed.add(llllllllllllllllllIIllIIlIlIIlII);
               var10001 = false;
               mc.theWorld.removeEntity(llllllllllllllllllIIllIIlIlIIlII);
            }

            invalid.add(llllllllllllllllllIIllIIlIlIIlII);
            var10001 = false;
         }

         if (llllllllllllllllllIIllIIlIlIIlll.contains("§8[NPC]")) {
            invalid.add(llllllllllllllllllIIllIIlIlIIlII);
            var10001 = false;
         }

         if (!llllllllllllllllllIIllIIlIlIIlll.contains("§c") && !llllllllllllllllllIIllIIlIlIIllI.equalsIgnoreCase("")) {
            invalid.add(llllllllllllllllllIIllIIlIlIIlII);
            var10001 = false;
         }
      }
   }

   public void onDisable() {
      mc.ingameGUI.getChatGUI().addToSentMessages("test");
      llllllllllllllllllIIllIIlIlllIIl.clearAll();
      super.onDisable();
   }

   @SideOnly(Side.CLIENT)
   public static class PlayerComparator implements Comparator<NetworkPlayerInfo> {
      private PlayerComparator() {
      }

      public int compare(NetworkPlayerInfo lIIlIIlIlIlII, NetworkPlayerInfo lIIlIIlIlIlll) {
         ScorePlayerTeam lIIlIIlIlIllI = lIIlIIlIlIlII.getPlayerTeam();
         ScorePlayerTeam lIIlIIlIlIlIl = lIIlIIlIlIlll.getPlayerTeam();
         return ComparisonChain.start().compareTrueFirst(lIIlIIlIlIlII.getGameType() != GameType.SPECTATOR, lIIlIIlIlIlll.getGameType() != GameType.SPECTATOR).compare(lIIlIIlIlIllI != null ? lIIlIIlIlIllI.getRegisteredName() : "", lIIlIIlIlIlIl != null ? lIIlIIlIlIlIl.getRegisteredName() : "").compare(lIIlIIlIlIlII.getGameProfile().getName(), lIIlIIlIlIlll.getGameProfile().getName()).result();
      }

      // $FF: synthetic method
      PlayerComparator(Object lIIlIIlIIIlll) {
         this();
      }
   }
}
