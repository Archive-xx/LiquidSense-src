//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.ui.client.hud.element.elements;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0019\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\b\u0010\u0010\u001a\u00020\u0011H\u0016J \u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020\u0017H\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Model;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "x", "", "y", "(DD)V", "customPitch", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "customYaw", "pitchMode", "Lnet/ccbluex/liquidbounce/value/ListValue;", "rotate", "", "rotateDirection", "", "yawMode", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "drawEntityOnScreen", "", "yaw", "pitch", "entityLivingBase", "Lnet/minecraft/entity/EntityLivingBase;", "LiquidSense"}
)
@ElementInfo(
   name = "Model"
)
public final class Model extends Element {
   // $FF: synthetic field
   private final FloatValue customPitch;
   // $FF: synthetic field
   private final ListValue pitchMode;
   // $FF: synthetic field
   private final FloatValue customYaw;
   // $FF: synthetic field
   private boolean rotateDirection;
   // $FF: synthetic field
   private float rotate;
   // $FF: synthetic field
   private final ListValue yawMode;

   public Model() {
      this(0.0D, 0.0D, 3, (DefaultConstructorMarker)null);
   }

   // $FF: synthetic method
   public Model(double var1, double var3, int lIlIlIlllllIIll, DefaultConstructorMarker var6) {
      if ((lIlIlIlllllIIll & 1) != 0) {
         var1 = 40.0D;
      }

      if ((lIlIlIlllllIIll & 2) != 0) {
         var3 = 100.0D;
      }

      this(var1, var3);
   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   @NotNull
   public Border drawElement() {
      float lIlIllIIIlIlIII = (String)lIlIllIIIlIlIll.yawMode.get();
      boolean lIlIllIIIlIIlll = false;
      if (lIlIllIIIlIlIII == null) {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
      } else {
         String var10000;
         float var9;
         label61: {
            var10000 = lIlIllIIIlIlIII.toLowerCase();
            Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).toLowerCase()");
            lIlIllIIIlIlIII = var10000;
            switch(lIlIllIIIlIlIII.hashCode()) {
            case -1349088399:
               if (lIlIllIIIlIlIII.equals("custom")) {
                  var9 = ((Number)lIlIllIIIlIlIll.customYaw.get()).floatValue();
                  break label61;
               }
               break;
            case -985752863:
               if (lIlIllIIIlIlIII.equals("player")) {
                  var9 = access$getMc$p$s1046033730().thePlayer.rotationYaw;
                  break label61;
               }
               break;
            case 1118509956:
               if (lIlIllIIIlIlIII.equals("animation")) {
                  int lIlIllIIIlIlllI = RenderUtils.deltaTime;
                  if (lIlIllIIIlIlIll.rotateDirection) {
                     if (lIlIllIIIlIlIll.rotate <= 70.0F) {
                        lIlIllIIIlIlIll.rotate += 0.12F * (float)lIlIllIIIlIlllI;
                     } else {
                        lIlIllIIIlIlIll.rotateDirection = false;
                        lIlIllIIIlIlIll.rotate = 70.0F;
                     }
                  } else if (lIlIllIIIlIlIll.rotate >= -70.0F) {
                     lIlIllIIIlIlIll.rotate -= 0.12F * (float)lIlIllIIIlIlllI;
                  } else {
                     lIlIllIIIlIlIll.rotateDirection = true;
                     lIlIllIIIlIlIll.rotate = -70.0F;
                  }

                  var9 = lIlIllIIIlIlIll.rotate;
                  break label61;
               }
            }

            var9 = 0.0F;
         }

         float lIlIllIIIlIllII = var9;
         boolean lIlIllIIIlIIlll = (String)lIlIllIIIlIlIll.pitchMode.get();
         double lIlIllIIIlIIllI = false;
         if (lIlIllIIIlIIlll == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
         } else {
            label51: {
               var10000 = lIlIllIIIlIIlll.toLowerCase();
               Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).toLowerCase()");
               lIlIllIIIlIIlll = var10000;
               switch(lIlIllIIIlIIlll.hashCode()) {
               case -1349088399:
                  if (lIlIllIIIlIIlll.equals("custom")) {
                     var9 = ((Number)lIlIllIIIlIlIll.customPitch.get()).floatValue();
                     break label51;
                  }
                  break;
               case -985752863:
                  if (lIlIllIIIlIIlll.equals("player")) {
                     var9 = access$getMc$p$s1046033730().thePlayer.rotationPitch;
                     break label51;
                  }
               }

               var9 = 0.0F;
            }

            float lIlIllIIIlIlIII = var9;
            if (lIlIllIIIlIlIII > (float)0) {
               var9 = -lIlIllIIIlIlIII;
            } else {
               lIlIllIIIlIIlll = false;
               var9 = Math.abs(lIlIllIIIlIlIII);
            }

            lIlIllIIIlIlIII = var9;
            EntityPlayerSP var10003 = access$getMc$p$s1046033730().thePlayer;
            Intrinsics.checkExpressionValueIsNotNull(var10003, "mc.thePlayer");
            lIlIllIIIlIlIll.drawEntityOnScreen(lIlIllIIIlIllII, lIlIllIIIlIlIII, (EntityLivingBase)var10003);
            return new Border(30.0F, 10.0F, -30.0F, -100.0F);
         }
      }
   }

   private final void drawEntityOnScreen(float lIlIllIIIIlIIlI, float lIlIllIIIIlIIIl, EntityLivingBase lIlIllIIIIlIIII) {
      GlStateManager.resetColor();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.enableColorMaterial();
      GlStateManager.pushMatrix();
      GlStateManager.translate(0.0F, 0.0F, 50.0F);
      GlStateManager.scale(-50.0F, 50.0F, 50.0F);
      GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
      String lIlIllIIIIIllII = lIlIllIIIIlIIII.renderYawOffset;
      short lIlIllIIIIIlIll = lIlIllIIIIlIIII.rotationYaw;
      float lIlIllIIIIlIllI = lIlIllIIIIlIIII.rotationPitch;
      double lIlIllIIIIIlIIl = lIlIllIIIIlIIII.prevRotationYawHead;
      int lIlIllIIIIIlIII = lIlIllIIIIlIIII.rotationYawHead;
      GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
      RenderHelper.enableStandardItemLighting();
      GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
      int lIlIllIIIIIIlll = lIlIllIIIIlIIIl / 40.0F;
      char lIlIllIIIIIIllI = false;
      GlStateManager.rotate(-((float)Math.atan((double)lIlIllIIIIIIlll)) * 20.0F, 1.0F, 0.0F, 0.0F);
      lIlIllIIIIIIlll = lIlIllIIIIlIIlI / 40.0F;
      lIlIllIIIIIIllI = false;
      float lIlIllIIIIIIlII = (float)Math.atan((double)lIlIllIIIIIIlll);
      lIlIllIIIIlIIII.renderYawOffset = lIlIllIIIIIIlII * 20.0F;
      lIlIllIIIIIIlll = lIlIllIIIIlIIlI / 40.0F;
      lIlIllIIIIIIllI = false;
      lIlIllIIIIIIlII = (float)Math.atan((double)lIlIllIIIIIIlll);
      lIlIllIIIIlIIII.rotationYaw = lIlIllIIIIIIlII * 40.0F;
      lIlIllIIIIIIlll = lIlIllIIIIlIIIl / 40.0F;
      lIlIllIIIIIIllI = false;
      lIlIllIIIIIIlII = (float)Math.atan((double)lIlIllIIIIIIlll);
      lIlIllIIIIlIIII.rotationPitch = -lIlIllIIIIIIlII * 20.0F;
      lIlIllIIIIlIIII.rotationYawHead = lIlIllIIIIlIIII.rotationYaw;
      lIlIllIIIIlIIII.prevRotationYawHead = lIlIllIIIIlIIII.rotationYaw;
      GlStateManager.translate(0.0F, 0.0F, 0.0F);
      Minecraft var10000 = access$getMc$p$s1046033730();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "mc");
      int lIlIllIIIIIIlll = var10000.getRenderManager();
      lIlIllIIIIIIlll.setPlayerViewY(180.0F);
      Intrinsics.checkExpressionValueIsNotNull(lIlIllIIIIIIlll, "renderManager");
      lIlIllIIIIIIlll.setRenderShadow(false);
      lIlIllIIIIIIlll.renderEntityWithPosYaw((Entity)lIlIllIIIIlIIII, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
      boolean var10001 = false;
      lIlIllIIIIIIlll.setRenderShadow(true);
      lIlIllIIIIlIIII.renderYawOffset = lIlIllIIIIIllII;
      lIlIllIIIIlIIII.rotationYaw = lIlIllIIIIIlIll;
      lIlIllIIIIlIIII.rotationPitch = lIlIllIIIIlIllI;
      lIlIllIIIIlIIII.prevRotationYawHead = lIlIllIIIIIlIIl;
      lIlIllIIIIlIIII.rotationYawHead = lIlIllIIIIIlIII;
      GlStateManager.popMatrix();
      RenderHelper.disableStandardItemLighting();
      GlStateManager.disableRescaleNormal();
      GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
      GlStateManager.disableTexture2D();
      GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
      GlStateManager.resetColor();
   }

   public Model(double lIlIlIlllllllll, double lIlIlIllllllllI) {
      super(lIlIlIlllllllll, lIlIlIllllllllI, 0.0F, (Side)null, 12, (DefaultConstructorMarker)null);
      lIlIllIIIIIIIII.yawMode = new ListValue("Yaw", new String[]{"Player", "Animation", "Custom"}, "Animation");
      lIlIllIIIIIIIII.customYaw = new FloatValue("CustomYaw", 0.0F, -180.0F, 180.0F);
      lIlIllIIIIIIIII.pitchMode = new ListValue("Pitch", new String[]{"Player", "Custom"}, "Player");
      lIlIllIIIIIIIII.customPitch = new FloatValue("CustomPitch", 0.0F, -90.0F, 90.0F);
   }
}
