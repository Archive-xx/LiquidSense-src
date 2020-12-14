//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.misc.FallingPlayer;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@ModuleInfo(
   name = "AntiFall",
   description = "Automatically setbacks you after falling a certain distance.",
   category = ModuleCategory.MOVEMENT
)
@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0016J\u0010\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u0017H\u0007J\u0010\u0010\u0018\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\u001aH\u0007R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001b"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/BugUp;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "detectedLocation", "Lnet/minecraft/util/BlockPos;", "indicator", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "lastFound", "", "maxDistanceWithoutGround", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "maxFallDistance", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "prevX", "", "prevY", "prevZ", "onDisable", "", "onRender3D", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onUpdate", "e", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidSense"}
)
public final class BugUp extends Module {
   // $FF: synthetic field
   private double prevX;
   // $FF: synthetic field
   private double prevZ;
   // $FF: synthetic field
   private final IntegerValue maxFallDistance = new IntegerValue("MaxFallDistance", 10, 2, 255);
   // $FF: synthetic field
   private final BoolValue indicator = new BoolValue("Indicator", true);
   // $FF: synthetic field
   private BlockPos detectedLocation;
   // $FF: synthetic field
   private double prevY;
   // $FF: synthetic field
   private float lastFound;
   // $FF: synthetic field
   private final FloatValue maxDistanceWithoutGround = new FloatValue("MaxDistanceToSetback", 2.5F, 1.0F, 30.0F);
   // $FF: synthetic field
   private final ListValue modeValue = new ListValue("Mode", new String[]{"TeleportBack", "FlyFlag", "OnGroundSpoof", "MotionTeleport-Flag"}, "FlyFlag");

   public void onDisable() {
      llIllIIlIlllll.prevX = 0.0D;
      llIllIIlIlllll.prevY = 0.0D;
      llIllIIlIlllll.prevZ = 0.0D;
   }

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent llIllIIlIlIlII) {
      Intrinsics.checkParameterIsNotNull(llIllIIlIlIlII, "e");
      llIllIIlIlIlIl.detectedLocation = (BlockPos)null;
      if (access$getMc$p$s1046033730().thePlayer.onGround && !(BlockUtils.getBlock(new BlockPos(access$getMc$p$s1046033730().thePlayer.posX, access$getMc$p$s1046033730().thePlayer.posY - 1.0D, access$getMc$p$s1046033730().thePlayer.posZ)) instanceof BlockAir)) {
         llIllIIlIlIlIl.prevX = access$getMc$p$s1046033730().thePlayer.prevPosX;
         llIllIIlIlIlIl.prevY = access$getMc$p$s1046033730().thePlayer.prevPosY;
         llIllIIlIlIlIl.prevZ = access$getMc$p$s1046033730().thePlayer.prevPosZ;
      }

      if (!access$getMc$p$s1046033730().thePlayer.onGround) {
         EntityPlayerSP var10000 = access$getMc$p$s1046033730().thePlayer;
         Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
         if (!var10000.isOnLadder()) {
            var10000 = access$getMc$p$s1046033730().thePlayer;
            Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
            if (!var10000.isInWater()) {
               Exception llIllIIlIlIIIl = new FallingPlayer(access$getMc$p$s1046033730().thePlayer.posX, access$getMc$p$s1046033730().thePlayer.posY, access$getMc$p$s1046033730().thePlayer.posZ, access$getMc$p$s1046033730().thePlayer.motionX, access$getMc$p$s1046033730().thePlayer.motionY, access$getMc$p$s1046033730().thePlayer.motionZ, access$getMc$p$s1046033730().thePlayer.rotationYaw, access$getMc$p$s1046033730().thePlayer.moveStrafing, access$getMc$p$s1046033730().thePlayer.moveForward);
               FallingPlayer.CollisionResult var10001 = llIllIIlIlIIIl.findCollision(60);
               BlockPos var9;
               if (var10001 != null) {
                  var9 = var10001.getPos();
               } else {
                  boolean var10002 = false;
                  var9 = null;
               }

               llIllIIlIlIlIl.detectedLocation = var9;
               boolean llIllIIlIIlllI;
               if (llIllIIlIlIlIl.detectedLocation != null) {
                  double var7 = access$getMc$p$s1046033730().thePlayer.posY;
                  var9 = llIllIIlIlIlIl.detectedLocation;
                  if (var9 == null) {
                     Intrinsics.throwNpe();
                  }

                  String llIllIIlIlIIII = var7 - (double)var9.getY();
                  llIllIIlIIlllI = false;
                  if (Math.abs(llIllIIlIlIIII) + (double)access$getMc$p$s1046033730().thePlayer.fallDistance <= ((Number)llIllIIlIlIlIl.maxFallDistance.get()).doubleValue()) {
                     llIllIIlIlIlIl.lastFound = access$getMc$p$s1046033730().thePlayer.fallDistance;
                  }
               }

               if (access$getMc$p$s1046033730().thePlayer.fallDistance - llIllIIlIlIlIl.lastFound > ((Number)llIllIIlIlIlIl.maxDistanceWithoutGround.get()).floatValue()) {
                  String llIllIIlIlIIII = (String)llIllIIlIlIlIl.modeValue.get();
                  llIllIIlIIlllI = false;
                  if (llIllIIlIlIIII == null) {
                     throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                  }

                  String var8 = llIllIIlIlIIII.toLowerCase();
                  Intrinsics.checkExpressionValueIsNotNull(var8, "(this as java.lang.String).toLowerCase()");
                  char llIllIIlIIllll = var8;
                  Minecraft var10;
                  switch(llIllIIlIIllll.hashCode()) {
                  case -1718744413:
                     if (llIllIIlIIllll.equals("ongroundspoof")) {
                        var10 = access$getMc$p$s1046033730();
                        Intrinsics.checkExpressionValueIsNotNull(var10, "mc");
                        var10.getNetHandler().addToSendQueue((Packet)(new C03PacketPlayer(true)));
                     }
                     break;
                  case -757065121:
                     if (llIllIIlIIllll.equals("flyflag")) {
                        var10000 = access$getMc$p$s1046033730().thePlayer;
                        var10000.motionY += 0.1D;
                        access$getMc$p$s1046033730().thePlayer.fallDistance = 0.0F;
                     }
                     break;
                  case -198873454:
                     if (llIllIIlIIllll.equals("teleportback")) {
                        access$getMc$p$s1046033730().thePlayer.setPositionAndUpdate(llIllIIlIlIlIl.prevX, llIllIIlIlIlIl.prevY, llIllIIlIlIlIl.prevZ);
                        access$getMc$p$s1046033730().thePlayer.fallDistance = 0.0F;
                        access$getMc$p$s1046033730().thePlayer.motionY = 0.0D;
                     }
                     break;
                  case 1873339608:
                     if (llIllIIlIIllll.equals("motionteleport-flag")) {
                        access$getMc$p$s1046033730().thePlayer.setPositionAndUpdate(access$getMc$p$s1046033730().thePlayer.posX, access$getMc$p$s1046033730().thePlayer.posY + (double)1.0F, access$getMc$p$s1046033730().thePlayer.posZ);
                        var10 = access$getMc$p$s1046033730();
                        Intrinsics.checkExpressionValueIsNotNull(var10, "mc");
                        var10.getNetHandler().addToSendQueue((Packet)(new C04PacketPlayerPosition(access$getMc$p$s1046033730().thePlayer.posX, access$getMc$p$s1046033730().thePlayer.posY, access$getMc$p$s1046033730().thePlayer.posZ, true)));
                        access$getMc$p$s1046033730().thePlayer.motionY = 0.1D;
                        MovementUtils.strafe();
                        access$getMc$p$s1046033730().thePlayer.fallDistance = 0.0F;
                     }
                  }
               }
            }
         }
      }

   }

   @EventTarget
   public final void onRender3D(@NotNull Render3DEvent llIllIIIlllIll) {
      Intrinsics.checkParameterIsNotNull(llIllIIIlllIll, "event");
      if (llIllIIIlllIlI.detectedLocation != null && (Boolean)llIllIIIlllIlI.indicator.get()) {
         double var10000 = (double)access$getMc$p$s1046033730().thePlayer.fallDistance;
         double var10001 = access$getMc$p$s1046033730().thePlayer.posY;
         BlockPos var10002 = llIllIIIlllIlI.detectedLocation;
         if (var10002 == null) {
            Intrinsics.throwNpe();
         }

         if (!(var10000 + (var10001 - (double)(var10002.getY() + 1)) < (double)3)) {
            BlockPos var13 = llIllIIIlllIlI.detectedLocation;
            if (var13 == null) {
               Intrinsics.throwNpe();
            }

            long llIllIIIlllIII = var13.getX();
            var13 = llIllIIIlllIlI.detectedLocation;
            if (var13 == null) {
               Intrinsics.throwNpe();
            }

            int llIllIIIlllllI = var13.getY();
            var13 = llIllIIIlllIlI.detectedLocation;
            if (var13 == null) {
               Intrinsics.throwNpe();
            }

            int llIllIIIllllll = var13.getZ();
            Minecraft var14 = access$getMc$p$s1046033730();
            Intrinsics.checkExpressionValueIsNotNull(var14, "mc");
            short llIllIIIllIlIl = var14.getRenderManager();
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3042);
            GL11.glLineWidth(2.0F);
            GL11.glDisable(3553);
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            RenderUtils.glColor(new Color(255, 0, 0, 90));
            RenderUtils.drawFilledBox(new AxisAlignedBB((double)llIllIIIlllIII - llIllIIIllIlIl.renderPosX, (double)(llIllIIIlllllI + 1) - llIllIIIllIlIl.renderPosY, (double)llIllIIIllllll - llIllIIIllIlIl.renderPosZ, (double)llIllIIIlllIII - llIllIIIllIlIl.renderPosX + 1.0D, (double)llIllIIIlllllI + 1.2D - llIllIIIllIlIl.renderPosY, (double)llIllIIIllllll - llIllIIIllIlIl.renderPosZ + 1.0D));
            GL11.glEnable(3553);
            GL11.glEnable(2929);
            GL11.glDepthMask(true);
            GL11.glDisable(3042);
            short llIllIIIllIIll = (double)access$getMc$p$s1046033730().thePlayer.fallDistance + (access$getMc$p$s1046033730().thePlayer.posY - ((double)llIllIIIlllllI + 0.5D));
            Exception llIllIIIllIIIl = false;
            int llIllIIIllIlII = (int)Math.floor(llIllIIIllIIll);
            StringBuilder var15 = (new StringBuilder()).append(llIllIIIllIlII).append("m (~");
            short llIllIIIllIIll = 0;
            char llIllIIIllIIlI = llIllIIIllIlII - 3;
            String llIllIIIllIIII = var15;
            llIllIIIllIIIl = false;
            boolean llIllIIIlIllll = Math.max(llIllIIIllIIll, llIllIIIllIIlI);
            RenderUtils.renderNameTag(String.valueOf(llIllIIIllIIII.append(llIllIIIlIllll).append(" damage)")), (double)llIllIIIlllIII + 0.5D, (double)llIllIIIlllllI + 1.7D, (double)llIllIIIllllll + 0.5D);
            GlStateManager.resetColor();
            return;
         }
      }

   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }
}
