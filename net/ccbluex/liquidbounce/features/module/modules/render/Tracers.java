//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@ModuleInfo(
   name = "Tracers",
   description = "Draws a line to targets around you.",
   category = ModuleCategory.RENDER
)
@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0002J\u0010\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u000fH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/render/Tracers;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "distanceColorValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "thicknessValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "drawTracer", "", "entity", "Lnet/minecraft/entity/Entity;", "color", "Ljava/awt/Color;", "onRender3D", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "LiquidSense"}
)
public final class Tracers extends Module {
   // $FF: synthetic field
   private final BoolValue distanceColorValue = new BoolValue("DistanceColor", false);
   // $FF: synthetic field
   private final FloatValue thicknessValue = new FloatValue("Thickness", 2.0F, 1.0F, 5.0F);

   @EventTarget
   public final void onRender3D(@NotNull Render3DEvent lIIIIllIIIlll) {
      Intrinsics.checkParameterIsNotNull(lIIIIllIIIlll, "event");
      Iterator lIIIIllIIIlIl = access$getMc$p$s1046033730().theWorld.loadedEntityList.iterator();

      while(lIIIIllIIIlIl.hasNext()) {
         Entity lIIIIllIIlIll = (Entity)lIIIIllIIIlIl.next();
         if (lIIIIllIIlIll != null && Intrinsics.areEqual((Object)lIIIIllIIlIll, (Object)access$getMc$p$s1046033730().thePlayer) ^ true && EntityUtils.isSelected(lIIIIllIIlIll, false)) {
            byte lIIIIllIIIlII = (int)(access$getMc$p$s1046033730().thePlayer.getDistanceToEntity(lIIIIllIIlIll) * (float)2);
            if (lIIIIllIIIlII > 255) {
               lIIIIllIIIlII = 255;
            }

            char lIIIIllIIIIll = EntityUtils.isFriend(lIIIIllIIlIll) ? new Color(0, 0, 255, 150) : ((Boolean)lIIIIllIIlIlI.distanceColorValue.get() ? new Color(255 - lIIIIllIIIlII, lIIIIllIIIlII, 0, 150) : new Color(255, 255, 255, 150));
            lIIIIllIIlIlI.drawTracer(lIIIIllIIlIll, lIIIIllIIIIll);
         }
      }

   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   private final void drawTracer(Entity lIIIIlIllIIll, Color lIIIIlIllIIlI) {
      double var10000 = lIIIIlIllIIll.lastTickPosX + (lIIIIlIllIIll.posX - lIIIIlIllIIll.lastTickPosX) * (double)access$getMc$p$s1046033730().timer.renderPartialTicks;
      Minecraft var10001 = access$getMc$p$s1046033730();
      Intrinsics.checkExpressionValueIsNotNull(var10001, "mc");
      Exception lIIIIlIllIIIl = var10000 - var10001.getRenderManager().renderPosX;
      var10000 = lIIIIlIllIIll.lastTickPosY + (lIIIIlIllIIll.posY - lIIIIlIllIIll.lastTickPosY) * (double)access$getMc$p$s1046033730().timer.renderPartialTicks;
      var10001 = access$getMc$p$s1046033730();
      Intrinsics.checkExpressionValueIsNotNull(var10001, "mc");
      Exception lIIIIlIllIIII = var10000 - var10001.getRenderManager().renderPosY;
      var10000 = lIIIIlIllIIll.lastTickPosZ + (lIIIIlIllIIll.posZ - lIIIIlIllIIll.lastTickPosZ) * (double)access$getMc$p$s1046033730().timer.renderPartialTicks;
      var10001 = access$getMc$p$s1046033730();
      Intrinsics.checkExpressionValueIsNotNull(var10001, "mc");
      long lIIIIlIlIllll = var10000 - var10001.getRenderManager().renderPosZ;
      char lIIIIlIlIlllI = (new Vec3(0.0D, 0.0D, 1.0D)).rotatePitch((float)(-Math.toRadians((double)access$getMc$p$s1046033730().thePlayer.rotationPitch))).rotateYaw((float)(-Math.toRadians((double)access$getMc$p$s1046033730().thePlayer.rotationYaw)));
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(3042);
      GL11.glEnable(2848);
      GL11.glLineWidth(((Number)lIIIIlIllIlII.thicknessValue.get()).floatValue());
      GL11.glDisable(3553);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      RenderUtils.glColor(lIIIIlIllIIlI);
      GL11.glBegin(1);
      GL11.glVertex3d(lIIIIlIlIlllI.xCoord, (double)access$getMc$p$s1046033730().thePlayer.getEyeHeight() + lIIIIlIlIlllI.yCoord, lIIIIlIlIlllI.zCoord);
      GL11.glVertex3d(lIIIIlIllIIIl, lIIIIlIllIIII, lIIIIlIlIllll);
      GL11.glVertex3d(lIIIIlIllIIIl, lIIIIlIllIIII, lIIIIlIlIllll);
      GL11.glVertex3d(lIIIIlIllIIIl, lIIIIlIllIIII + (double)lIIIIlIllIIll.height, lIIIIlIlIllll);
      GL11.glEnd();
      GL11.glEnable(3553);
      GL11.glDisable(2848);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GlStateManager.resetColor();
   }
}
