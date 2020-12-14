//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.world;

import java.awt.Color;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.combat.Aura;
import net.ccbluex.liquidbounce.features.module.modules.player.AutoTool;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.VecRotation;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.extensions.BlockExtensionKt;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BlockValue;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010#\u001a\u0004\u0018\u00010\u00132\u0006\u0010$\u001a\u00020\u0006H\u0002J\u0010\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020\u0013H\u0002J\u0010\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020+H\u0007J\u0010\u0010,\u001a\u00020)2\u0006\u0010*\u001a\u00020-H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001e\u001a\u00020\u001f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b \u0010!R\u000e\u0010\"\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006."},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/world/Fucker;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "actionValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "blockHitDelay", "", "blockValue", "Lnet/ccbluex/liquidbounce/value/BlockValue;", "currentDamage", "", "getCurrentDamage", "()F", "setCurrentDamage", "(F)V", "instantValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "noHitValue", "oldPos", "Lnet/minecraft/util/BlockPos;", "pos", "rangeValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "rotationsValue", "surroundingsValue", "swingValue", "switchTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "switchValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "tag", "", "getTag", "()Ljava/lang/String;", "throughWallsValue", "find", "targetID", "isHitable", "", "blockPos", "onRender3D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidSense"}
)
@ModuleInfo(
   name = "Fucker",
   description = "Destroys selected blocks around you. (aka.  IDNuker)",
   category = ModuleCategory.WORLD
)
public final class Fucker extends Module {
   // $FF: synthetic field
   private static final BoolValue swingValue;
   // $FF: synthetic field
   public static final Fucker INSTANCE;
   // $FF: synthetic field
   private static final ListValue throughWallsValue;
   // $FF: synthetic field
   private static BlockPos oldPos;
   // $FF: synthetic field
   private static final IntegerValue switchValue;
   // $FF: synthetic field
   private static final BlockValue blockValue;
   // $FF: synthetic field
   private static final BoolValue rotationsValue;
   // $FF: synthetic field
   private static float currentDamage;
   // $FF: synthetic field
   private static final MSTimer switchTimer;
   // $FF: synthetic field
   private static BlockPos pos;
   // $FF: synthetic field
   private static final BoolValue surroundingsValue;
   // $FF: synthetic field
   private static final ListValue actionValue;
   // $FF: synthetic field
   private static final BoolValue instantValue;
   // $FF: synthetic field
   private static final FloatValue rangeValue;
   // $FF: synthetic field
   private static final BoolValue noHitValue;
   // $FF: synthetic field
   private static int blockHitDelay;

   private final boolean isHitable(BlockPos llllIIlIlllll) {
      int llllIIlIllllI = (String)throughWallsValue.get();
      double llllIIlIlllIl = false;
      if (llllIIlIllllI == null) {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
      } else {
         String var10000 = llllIIlIllllI.toLowerCase();
         Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).toLowerCase()");
         llllIIlIllllI = var10000;
         boolean var6;
         switch(llllIIlIllllI.hashCode()) {
         case -1409235507:
            if (llllIIlIllllI.equals("around")) {
               var6 = !BlockUtils.isFullBlock(llllIIlIlllll.down()) || !BlockUtils.isFullBlock(llllIIlIlllll.up()) || !BlockUtils.isFullBlock(llllIIlIlllll.north()) || !BlockUtils.isFullBlock(llllIIlIlllll.east()) || !BlockUtils.isFullBlock(llllIIlIlllll.south()) || !BlockUtils.isFullBlock(llllIIlIlllll.west());
               return var6;
            }
            break;
         case 988024425:
            if (llllIIlIllllI.equals("raycast")) {
               double var10002 = access$getMc$p$s1046033730().thePlayer.posX;
               EntityPlayerSP var10003 = access$getMc$p$s1046033730().thePlayer;
               Intrinsics.checkExpressionValueIsNotNull(var10003, "mc.thePlayer");
               double llllIIlIlllIl = new Vec3(var10002, var10003.getEntityBoundingBox().minY + (double)access$getMc$p$s1046033730().thePlayer.getEyeHeight(), access$getMc$p$s1046033730().thePlayer.posZ);
               MovingObjectPosition llllIIllIIIll = access$getMc$p$s1046033730().theWorld.rayTraceBlocks(llllIIlIlllIl, new Vec3((double)llllIIlIlllll.getX() + 0.5D, (double)llllIIlIlllll.getY() + 0.5D, (double)llllIIlIlllll.getZ() + 0.5D), false, true, false);
               var6 = llllIIllIIIll != null && Intrinsics.areEqual((Object)llllIIllIIIll.getBlockPos(), (Object)llllIIlIlllll);
               return var6;
            }
         }

         var6 = true;
         return var6;
      }
   }

   public final float getCurrentDamage() {
      return currentDamage;
   }

   private final BlockPos find(int llllIIlllIlII) {
      Map llllIlIIIIlII = BlockUtils.searchBlocks((int)((Number)rangeValue.get()).floatValue() + 1);
      float llllIIlllIIIl = false;
      int llllIIllIllll = (Map)(new LinkedHashMap());
      boolean llllIIllIlllI = false;
      short llllIIllIllII = false;
      Iterator llllIIllIlIll = llllIlIIIIlII.entrySet().iterator();

      Entry llllIIllIlIlI;
      boolean var10001;
      while(llllIIllIlIll.hasNext()) {
         llllIIllIlIlI = (Entry)llllIIllIlIll.next();
         Exception llllIIllIlIII = false;
         if (Block.getIdFromBlock((Block)llllIIllIlIlI.getValue()) == llllIIlllIlII && BlockUtils.getCenterDistance((BlockPos)llllIIllIlIlI.getKey()) <= ((Number)rangeValue.get()).doubleValue() && (INSTANCE.isHitable((BlockPos)llllIIllIlIlI.getKey()) || (Boolean)surroundingsValue.get())) {
            llllIIllIllll.put(llllIIllIlIlI.getKey(), llllIIllIlIlI.getValue());
            var10001 = false;
         }
      }

      llllIIlllIIIl = false;
      Iterable llllIIllllIIl = (Iterable)llllIIllIllll.entrySet();
      int llllIIllIllll = false;
      Iterator llllIIllllIlI = llllIIllllIIl.iterator();
      Object var10000;
      if (!llllIIllllIlI.hasNext()) {
         var10000 = null;
      } else {
         Object llllIIllllIll = llllIIllllIlI.next();
         if (!llllIIllllIlI.hasNext()) {
            var10000 = llllIIllllIll;
         } else {
            Entry llllIlIIIIIlI = (Entry)llllIIllllIll;
            int llllIlIIIIIIl = false;
            double llllIIlllllII = BlockUtils.getCenterDistance((BlockPos)llllIlIIIIIlI.getKey());

            do {
               byte llllIIllIlIll = llllIIllllIlI.next();
               llllIIllIlIlI = (Entry)llllIIllIlIll;
               int llllIIlllllll = false;
               double llllIIllllllI = BlockUtils.getCenterDistance((BlockPos)llllIIllIlIlI.getKey());
               if (Double.compare(llllIIlllllII, llllIIllllllI) > 0) {
                  llllIIllllIll = llllIIllIlIll;
                  llllIIlllllII = llllIIllllllI;
               }
            } while(llllIIllllIlI.hasNext());

            var10000 = llllIIllllIll;
         }
      }

      Entry var22 = (Entry)var10000;
      BlockPos var23;
      if (var22 != null) {
         var23 = (BlockPos)var22.getKey();
      } else {
         var10001 = false;
         var23 = null;
      }

      return var23;
   }

   @NotNull
   public String getTag() {
      return BlockUtils.getBlockName(((Number)blockValue.get()).intValue());
   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   static {
      float llllIIlIlIllI = new Fucker();
      INSTANCE = llllIIlIlIllI;
      blockValue = new BlockValue("Block", 26);
      throughWallsValue = new ListValue("ThroughWalls", new String[]{"None", "Raycast", "Around"}, "None");
      rangeValue = new FloatValue("Range", 5.0F, 1.0F, 7.0F);
      actionValue = new ListValue("Action", new String[]{"Destroy", "Use", "New"}, "Destroy");
      instantValue = new BoolValue("Instant", false);
      switchValue = new IntegerValue("SwitchDelay", 250, 0, 1000);
      swingValue = new BoolValue("Swing", true);
      rotationsValue = new BoolValue("Rotations", true);
      surroundingsValue = new BoolValue("Surroundings", true);
      noHitValue = new BoolValue("NoHit", false);
      switchTimer = new MSTimer();
   }

   @EventTarget
   public final void onRender3D(@NotNull Render3DEvent llllIlIIllIII) {
      Intrinsics.checkParameterIsNotNull(llllIlIIllIII, "event");
      BlockPos var10000 = pos;
      if (var10000 != null) {
         RenderUtils.drawBlockBox(var10000, Color.RED, true);
      } else {
         boolean var10001 = false;
      }
   }

   public final void setCurrentDamage(float llllIlIllIlll) {
      currentDamage = llllIlIllIlll;
   }

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent llllIlIlIIIll) {
      Intrinsics.checkParameterIsNotNull(llllIlIlIIIll, "event");
      Module var10000;
      if ((Boolean)noHitValue.get()) {
         var10000 = LiquidBounce.INSTANCE.getModuleManager().getModule(Aura.class);
         if (var10000 == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.combat.Aura");
         }

         boolean llllIlIlIIIII = (Aura)var10000;
         if (llllIlIlIIIII.getState() && llllIlIlIIIII.getTarget() != null) {
            return;
         }
      }

      BlockPos var11;
      label158: {
         boolean llllIlIlIIIII = ((Number)blockValue.get()).intValue();
         if (pos != null && Block.getIdFromBlock(BlockUtils.getBlock(pos)) == llllIlIlIIIII) {
            var11 = pos;
            if (var11 == null) {
               Intrinsics.throwNpe();
            }

            if (!(BlockUtils.getCenterDistance(var11) > ((Number)rangeValue.get()).doubleValue())) {
               break label158;
            }
         }

         pos = llllIlIlIIIlI.find(llllIlIlIIIII);
      }

      if (pos == null) {
         currentDamage = 0.0F;
      } else {
         var11 = pos;
         boolean var10001;
         if (var11 != null) {
            float llllIlIIlllll = var11;
            VecRotation var12 = RotationUtils.faceBlock(llllIlIIlllll);
            if (var12 == null) {
               var10001 = false;
            } else {
               VecRotation llllIlIlIIlll = var12;
               int llllIlIIlllIl = false;
               if ((Boolean)surroundingsValue.get()) {
                  Vec3 llllIlIlIlIll = access$getMc$p$s1046033730().thePlayer.getPositionEyes(1.0F);
                  MovingObjectPosition var13 = access$getMc$p$s1046033730().theWorld.rayTraceBlocks(llllIlIlIlIll, llllIlIlIIlll.getVec(), false, false, true);
                  Intrinsics.checkExpressionValueIsNotNull(var13, "mc.theWorld.rayTraceBloc…             false, true)");
                  float llllIlIIllIll = var13.getBlockPos();
                  if (llllIlIIllIll != null && !(BlockExtensionKt.getBlock(llllIlIIllIll) instanceof BlockAir)) {
                     if (llllIlIIlllll.getX() != llllIlIIllIll.getX() || llllIlIIlllll.getY() != llllIlIIllIll.getY() || llllIlIIlllll.getZ() != llllIlIIllIll.getZ()) {
                        llllIlIIlllIl = true;
                     }

                     pos = llllIlIIllIll;
                     var11 = pos;
                     if (var11 == null) {
                        var10001 = false;
                        return;
                     }

                     llllIlIIlllll = var11;
                     var12 = RotationUtils.faceBlock(llllIlIIlllll);
                     if (var12 == null) {
                        var10001 = false;
                        return;
                     }

                     llllIlIlIIlll = var12;
                  }
               }

               if (oldPos != null && Intrinsics.areEqual((Object)oldPos, (Object)llllIlIIlllll) ^ true) {
                  currentDamage = 0.0F;
                  switchTimer.reset();
               }

               oldPos = llllIlIIlllll;
               if (switchTimer.hasTimePassed((long)((Number)switchValue.get()).intValue())) {
                  if (blockHitDelay > 0) {
                     blockHitDelay += -1;
                  } else {
                     if ((Boolean)rotationsValue.get()) {
                        RotationUtils.setTargetRotation(llllIlIlIIlll.getRotation());
                     }

                     EntityPlayerSP var14;
                     if (!StringsKt.equals((String)actionValue.get(), "destroy", true) && !StringsKt.equals((String)actionValue.get(), "new", true) && !llllIlIIlllIl) {
                        if (StringsKt.equals((String)actionValue.get(), "use", true)) {
                           PlayerControllerMP var18 = access$getMc$p$s1046033730().playerController;
                           var14 = access$getMc$p$s1046033730().thePlayer;
                           WorldClient var10002 = access$getMc$p$s1046033730().theWorld;
                           EntityPlayerSP var10003 = access$getMc$p$s1046033730().thePlayer;
                           Intrinsics.checkExpressionValueIsNotNull(var10003, "mc.thePlayer");
                           if (var18.onPlayerRightClick(var14, var10002, var10003.getHeldItem(), pos, EnumFacing.DOWN, new Vec3((double)llllIlIIlllll.getX(), (double)llllIlIIlllll.getY(), (double)llllIlIIlllll.getZ()))) {
                              if ((Boolean)swingValue.get()) {
                                 access$getMc$p$s1046033730().thePlayer.swingItem();
                              }

                              blockHitDelay = 4;
                              currentDamage = 0.0F;
                              pos = (BlockPos)null;
                           }
                        }
                     } else {
                        var10000 = LiquidBounce.INSTANCE.getModuleManager().get(AutoTool.class);
                        if (var10000 == null) {
                           throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.player.AutoTool");
                        }

                        Exception llllIlIIlllII = (AutoTool)var10000;
                        if (llllIlIIlllII.getState()) {
                           llllIlIIlllII.switchSlot(llllIlIIlllll);
                        }

                        Minecraft var16;
                        if ((Boolean)instantValue.get()) {
                           var16 = access$getMc$p$s1046033730();
                           Intrinsics.checkExpressionValueIsNotNull(var16, "mc");
                           var16.getNetHandler().addToSendQueue((Packet)(new C07PacketPlayerDigging(Action.START_DESTROY_BLOCK, llllIlIIlllll, EnumFacing.DOWN)));
                           var16 = access$getMc$p$s1046033730();
                           Intrinsics.checkExpressionValueIsNotNull(var16, "mc");
                           var16.getNetHandler().addToSendQueue((Packet)(new C07PacketPlayerDigging(Action.STOP_DESTROY_BLOCK, llllIlIIlllll, EnumFacing.DOWN)));
                           if (StringsKt.equals((String)actionValue.get(), "new", true)) {
                              var16 = access$getMc$p$s1046033730();
                              Intrinsics.checkExpressionValueIsNotNull(var16, "mc");
                              var16.getNetHandler().addToSendQueue((Packet)(new C07PacketPlayerDigging(Action.START_DESTROY_BLOCK, llllIlIIlllll, EnumFacing.DOWN)));
                           }

                           if ((Boolean)swingValue.get()) {
                              access$getMc$p$s1046033730().thePlayer.swingItem();
                           }

                           currentDamage = 0.0F;
                           return;
                        }

                        Block var15 = BlockExtensionKt.getBlock(llllIlIIlllll);
                        if (var15 == null) {
                           var10001 = false;
                           return;
                        }

                        float llllIlIIllIll = var15;
                        if (currentDamage == 0.0F) {
                           var16 = access$getMc$p$s1046033730();
                           Intrinsics.checkExpressionValueIsNotNull(var16, "mc");
                           var16.getNetHandler().addToSendQueue((Packet)(new C07PacketPlayerDigging(Action.START_DESTROY_BLOCK, llllIlIIlllll, EnumFacing.DOWN)));
                           if (access$getMc$p$s1046033730().thePlayer.capabilities.isCreativeMode || llllIlIIllIll.getPlayerRelativeBlockHardness((EntityPlayer)access$getMc$p$s1046033730().thePlayer, (World)access$getMc$p$s1046033730().theWorld, pos) >= 1.0F) {
                              if ((Boolean)swingValue.get()) {
                                 access$getMc$p$s1046033730().thePlayer.swingItem();
                              }

                              access$getMc$p$s1046033730().playerController.onPlayerDestroyBlock(pos, EnumFacing.DOWN);
                              var10001 = false;
                              currentDamage = 0.0F;
                              pos = (BlockPos)null;
                              return;
                           }
                        }

                        if ((Boolean)swingValue.get()) {
                           access$getMc$p$s1046033730().thePlayer.swingItem();
                        }

                        currentDamage += llllIlIIllIll.getPlayerRelativeBlockHardness((EntityPlayer)access$getMc$p$s1046033730().thePlayer, (World)access$getMc$p$s1046033730().theWorld, llllIlIIlllll);
                        WorldClient var17 = access$getMc$p$s1046033730().theWorld;
                        var14 = access$getMc$p$s1046033730().thePlayer;
                        Intrinsics.checkExpressionValueIsNotNull(var14, "mc.thePlayer");
                        var17.sendBlockBreakProgress(var14.getEntityId(), llllIlIIlllll, (int)(currentDamage * 10.0F) - 1);
                        if (currentDamage >= 1.0F) {
                           var16 = access$getMc$p$s1046033730();
                           Intrinsics.checkExpressionValueIsNotNull(var16, "mc");
                           var16.getNetHandler().addToSendQueue((Packet)(new C07PacketPlayerDigging(Action.STOP_DESTROY_BLOCK, llllIlIIlllll, EnumFacing.DOWN)));
                           access$getMc$p$s1046033730().playerController.onPlayerDestroyBlock(llllIlIIlllll, EnumFacing.DOWN);
                           var10001 = false;
                           blockHitDelay = 4;
                           currentDamage = 0.0F;
                           pos = (BlockPos)null;
                        }
                     }

                  }
               }
            }
         } else {
            var10001 = false;
         }
      }
   }

   private Fucker() {
   }
}
