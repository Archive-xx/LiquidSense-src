//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.ui.client.hud.element.elements;

import java.awt.Color;
import java.io.Closeable;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.modules.render.ESP;
import net.ccbluex.liquidbounce.ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.MiniMapRegister;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.SafeVertexBuffer;
import net.ccbluex.liquidbounce.utils.render.shader.shaders.RainbowShader;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

@ElementInfo(
   name = "Radar",
   disableScale = true,
   priority = 1
)
@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u0000 &2\u00020\u0001:\u0001&B\u0019\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\u0010\u0010\"\u001a\u00020\u00152\u0006\u0010#\u001a\u00020\u0018H\u0002J\n\u0010$\u001a\u0004\u0018\u00010%H\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006'"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Radar;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "x", "", "y", "(DD)V", "backgroundAlphaValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "backgroundBlueValue", "backgroundGreenValue", "backgroundRedValue", "borderAlphaValue", "borderBlueValue", "borderGreenValue", "borderRainbowValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "borderRedValue", "borderStrengthValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "fovAngleValue", "fovMarkerVertexBuffer", "Lnet/minecraft/client/renderer/vertex/VertexBuffer;", "fovSizeValue", "lastFov", "", "minimapValue", "playerShapeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "playerSizeValue", "rainbowXValue", "rainbowYValue", "sizeValue", "useESPColorsValue", "viewDistanceValue", "createFovIndicator", "angle", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "Companion", "LiquidSense"}
)
public final class Radar extends Element {
   // $FF: synthetic field
   private final FloatValue playerSizeValue;
   // $FF: synthetic field
   private float lastFov;
   // $FF: synthetic field
   private final IntegerValue backgroundGreenValue;
   // $FF: synthetic field
   private final IntegerValue borderRedValue;
   // $FF: synthetic field
   private static final float SQRT_OF_TWO;
   // $FF: synthetic field
   private final FloatValue rainbowYValue;
   // $FF: synthetic field
   private VertexBuffer fovMarkerVertexBuffer;
   // $FF: synthetic field
   private final IntegerValue borderGreenValue;
   // $FF: synthetic field
   public static final Radar.Companion Companion = new Radar.Companion((DefaultConstructorMarker)null);
   // $FF: synthetic field
   private final FloatValue fovSizeValue;
   // $FF: synthetic field
   private final FloatValue rainbowXValue;
   // $FF: synthetic field
   private final IntegerValue backgroundRedValue;
   // $FF: synthetic field
   private final FloatValue borderStrengthValue;
   // $FF: synthetic field
   private final IntegerValue borderBlueValue;
   // $FF: synthetic field
   private final FloatValue sizeValue;
   // $FF: synthetic field
   private final FloatValue fovAngleValue;
   // $FF: synthetic field
   private final BoolValue borderRainbowValue;
   // $FF: synthetic field
   private final IntegerValue borderAlphaValue;
   // $FF: synthetic field
   private final IntegerValue backgroundBlueValue;
   // $FF: synthetic field
   private final IntegerValue backgroundAlphaValue;
   // $FF: synthetic field
   private final BoolValue minimapValue;
   // $FF: synthetic field
   private final ListValue playerShapeValue;
   // $FF: synthetic field
   private final BoolValue useESPColorsValue;
   // $FF: synthetic field
   private final FloatValue viewDistanceValue;

   static {
      float var0 = 2.0F;
      boolean var1 = false;
      SQRT_OF_TWO = (float)Math.sqrt((double)var0);
   }

   private final VertexBuffer createFovIndicator(float llIIIllIIlllIl) {
      Tessellator var10000 = Tessellator.getInstance();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "Tessellator.getInstance()");
      float llIIIllIIlllII = var10000.getWorldRenderer();
      llIIIllIIlllII.begin(6, DefaultVertexFormats.POSITION);
      float llIIIllIlIIIIl = (90.0F - llIIIllIIlllIl * 0.5F) / 180.0F * (float)3.141592653589793D;
      float llIIIllIlIIIlI = (90.0F + llIIIllIIlllIl * 0.5F) / 180.0F * (float)3.141592653589793D;
      long llIIIllIIllIIl = llIIIllIlIIIlI;
      long llIIIllIIllIII = 1.0D;
      llIIIllIIlllII.pos(0.0D, 0.0D, 0.0D).endVertex();

      while(llIIIllIIllIIl >= llIIIllIlIIIIl) {
         double llIIIllIIlIlll = false;
         String llIIIllIIlIlIl = (float)Math.cos((double)llIIIllIIllIIl);
         String llIIIllIIlIlIl = (double)llIIIllIIlIlIl * llIIIllIIllIII;
         llIIIllIIlIlll = false;
         long llIIIllIIlIlII = (float)Math.sin((double)llIIIllIIllIIl);
         llIIIllIIlllII.pos(llIIIllIIlIlIl, (double)llIIIllIIlIlII * llIIIllIIllIII, 0.0D).endVertex();
         llIIIllIIllIIl -= 0.15F;
      }

      Intrinsics.checkExpressionValueIsNotNull(llIIIllIIlllII, "worldRenderer");
      SafeVertexBuffer llIIIllIlIIlIl = new SafeVertexBuffer(llIIIllIIlllII.getVertexFormat());
      llIIIllIIlllII.finishDrawing();
      llIIIllIIlllII.reset();
      llIIIllIlIIlIl.bufferData(llIIIllIIlllII.getByteBuffer());
      return (VertexBuffer)llIIIllIlIIlIl;
   }

   // $FF: synthetic method
   public Radar(double var1, double var3, int llIIIllIIIIIll, DefaultConstructorMarker var6) {
      if ((llIIIllIIIIIll & 1) != 0) {
         var1 = 5.0D;
      }

      if ((llIIIllIIIIIll & 2) != 0) {
         var3 = 130.0D;
      }

      this(var1, var3);
   }

   @Nullable
   public Border drawElement() {
      MiniMapRegister.INSTANCE.updateChunks();
      short llIIIlllIIlIIl = ((Number)llIIIlllIIlIlI.fovAngleValue.get()).floatValue();
      VertexBuffer var10000;
      boolean var10001;
      if (llIIIlllIIlIlI.lastFov != llIIIlllIIlIIl || llIIIlllIIlIlI.fovMarkerVertexBuffer == null) {
         var10000 = llIIIlllIIlIlI.fovMarkerVertexBuffer;
         if (var10000 != null) {
            var10000.deleteGlBuffers();
            Unit var50 = Unit.INSTANCE;
         } else {
            var10001 = false;
            var10000 = null;
         }

         var10001 = false;
         llIIIlllIIlIlI.fovMarkerVertexBuffer = llIIIlllIIlIlI.createFovIndicator(llIIIlllIIlIIl);
         llIIIlllIIlIlI.lastFov = llIIIlllIIlIIl;
      }

      Minecraft var52 = access$getMc$p$s1046033730();
      Intrinsics.checkExpressionValueIsNotNull(var52, "mc");
      Entity llIIIlllIIllIl = var52.getRenderViewEntity();
      String llIIIlllIIIlll = ((Number)llIIIlllIIlIlI.sizeValue.get()).floatValue();
      if (!(Boolean)llIIIlllIIlIlI.minimapValue.get()) {
         RenderUtils.drawRect(0.0F, 0.0F, llIIIlllIIIlll, llIIIlllIIIlll, (new Color(((Number)llIIIlllIIlIlI.backgroundRedValue.get()).intValue(), ((Number)llIIIlllIIlIlI.backgroundGreenValue.get()).intValue(), ((Number)llIIIlllIIlIlI.backgroundBlueValue.get()).intValue(), ((Number)llIIIlllIIlIlI.backgroundAlphaValue.get()).intValue())).getRGB());
      }

      long llIIIlllIIIllI = ((Number)llIIIlllIIlIlI.viewDistanceValue.get()).floatValue() * 16.0F;
      Exception llIIIlllIIIlIl = ((double)llIIIlllIIIllI + (double)((Number)llIIIlllIIlIlI.fovSizeValue.get()).floatValue()) * ((double)llIIIlllIIIllI + (double)((Number)llIIIlllIIlIlI.fovSizeValue.get()).floatValue());
      float llIIIlllIlIIIl = llIIIlllIIIlll / 2.0F;
      float var54 = (float)llIIIlllIIlIlI.getX();
      float var57 = (float)llIIIlllIIlIlI.getY();
      String llIIIllIllIIlI = (float)llIIIlllIIlIlI.getX();
      String llIIIllIllIIll = var57;
      short llIIIllIllIlII = var54;
      double llIIIlllIlIIlI = false;
      char llIIIllIllIIIl = (float)Math.ceil((double)llIIIlllIIIlll);
      float var10002 = llIIIllIllIIlI + llIIIllIllIIIl;
      llIIIllIllIIIl = (float)llIIIlllIIlIlI.getY();
      llIIIllIllIIlI = var10002;
      llIIIlllIlIIlI = false;
      int llIIIllIllIIII = (float)Math.ceil((double)llIIIlllIIIlll);
      RenderUtils.makeScissorBox(llIIIllIllIlII, llIIIllIllIIll, llIIIllIllIIlI, llIIIllIllIIIl + llIIIllIllIIII);
      GL11.glEnable(3089);
      GL11.glPushMatrix();
      GL11.glTranslatef(llIIIlllIlIIIl, llIIIlllIlIIIl, 0.0F);
      GL11.glRotatef(llIIIlllIIllIl.rotationYaw, 0.0F, 0.0F, -1.0F);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      if ((Boolean)llIIIlllIIlIlI.minimapValue.get()) {
         GL11.glEnable(3553);
         float llIIIllllIIlll = llIIIlllIIIlll / ((Number)llIIIlllIIlIlI.viewDistanceValue.get()).floatValue();
         boolean llIIIlllIIIIIl = 1;
         int llIIIlllIIIIII = SQRT_OF_TWO * ((Number)llIIIlllIIlIlI.viewDistanceValue.get()).floatValue() * 0.5F;
         short llIIIllIllllll = false;
         int llIIIlllIIIIII = (int)((float)Math.ceil((double)llIIIlllIIIIII));
         llIIIllIllllll = false;
         int llIIIllllIlIII = Math.max(llIIIlllIIIIIl, llIIIlllIIIIII);
         double llIIIllllIlIIl = llIIIlllIIllIl.posX / 16.0D;
         short llIIIllIllllll = llIIIlllIIllIl.posZ / 16.0D;
         String llIIIllIllllIl = -llIIIllllIlIII;
         int llIIIllIllllII = llIIIllllIlIII;
         if (llIIIllIllllIl <= llIIIllllIlIII) {
            while(true) {
               long llIIIllllIllII = -llIIIllllIlIII;
               float llIIIllIlllIlI = llIIIllllIlIII;
               if (llIIIllllIllII <= llIIIllllIlIII) {
                  while(true) {
                     short llIIIllIllIlII = MiniMapRegister.INSTANCE;
                     double llIIIllIlllIII = false;
                     String llIIIllIllIIll = Math.floor(llIIIllllIlIIl);
                     String llIIIllIllIIll = (int)llIIIllIllIIll + llIIIllIllllIl;
                     llIIIllIlllIII = false;
                     String llIIIllIllIIlI = Math.floor(llIIIllIllllll);
                     short llIIIllIlllIIl = llIIIllIllIlII.getChunkTextureAt(llIIIllIllIIll, (int)llIIIllIllIIlI + llIIIllllIllII);
                     if (llIIIllIlllIIl != null) {
                        double llIIIllIlllIII = (double)llIIIllllIIlll;
                        short llIIIllIllIllI = false;
                        llIIIllIllIIlI = Math.floor(llIIIllllIlIIl);
                        double llIIIllllIllll = (llIIIllllIlIIl - (double)((long)llIIIllIllIIlI) - (double)1 - (double)llIIIllIllllIl) * llIIIllIlllIII;
                        short llIIIllIllIlIl = false;
                        llIIIllIllIIlI = Math.floor(llIIIllIllllll);
                        short llIIIllIllIllI = (llIIIllIllllll - (double)((long)llIIIllIllIIlI) - (double)1 - (double)llIIIllllIllII) * llIIIllIlllIII;
                        GlStateManager.bindTexture(llIIIllIlllIIl.getTexture().getGlTextureId());
                        GL11.glBegin(7);
                        GL11.glTexCoord2f(0.0F, 0.0F);
                        GL11.glVertex2d(llIIIllllIllll, llIIIllIllIllI);
                        GL11.glTexCoord2f(0.0F, 1.0F);
                        GL11.glVertex2d(llIIIllllIllll, llIIIllIllIllI + (double)llIIIllllIIlll);
                        GL11.glTexCoord2f(1.0F, 1.0F);
                        GL11.glVertex2d(llIIIllllIllll + (double)llIIIllllIIlll, llIIIllIllIllI + (double)llIIIllllIIlll);
                        GL11.glTexCoord2f(1.0F, 0.0F);
                        GL11.glVertex2d(llIIIllllIllll + (double)llIIIllllIIlll, llIIIllIllIllI);
                        GL11.glEnd();
                     }

                     if (llIIIllllIllII == llIIIllIlllIlI) {
                        break;
                     }

                     ++llIIIllllIllII;
                  }
               }

               if (llIIIllIllllIl == llIIIllIllllII) {
                  break;
               }

               ++llIIIllIllllIl;
            }
         }

         GlStateManager.bindTexture(0);
         GL11.glDisable(3553);
      }

      GL11.glDisable(3553);
      GL11.glEnable(2848);
      llIIIlllIlIIlI = StringsKt.equals((String)llIIIlllIIlIlI.playerShapeValue.get(), "triangle", true);
      long llIIIlllIIIIlI = StringsKt.equals((String)llIIIlllIIlIlI.playerShapeValue.get(), "circle", true);
      Tessellator llIIIlllIlIlII = Tessellator.getInstance();
      Intrinsics.checkExpressionValueIsNotNull(llIIIlllIlIlII, "tessellator");
      WorldRenderer llIIIlllIlIlIl = llIIIlllIlIlII.getWorldRenderer();
      if (llIIIlllIIIIlI) {
         GL11.glEnable(2832);
      }

      short llIIIllIllllll = ((Number)llIIIlllIIlIlI.playerSizeValue.get()).floatValue();
      GL11.glEnable(2881);
      if (llIIIlllIlIIlI) {
         llIIIllIllllll *= (float)2;
      } else {
         llIIIlllIlIlIl.begin(0, DefaultVertexFormats.POSITION_COLOR);
         GL11.glPointSize(llIIIllIllllll);
      }

      Iterator llIIIllIllllIl = access$getMc$p$s1046033730().theWorld.loadedEntityList.iterator();

      while(true) {
         Entity llIIIllllIIIII;
         Vector2f llIIIllIllllII;
         float llIIIllllIIlIl;
         do {
            do {
               do {
                  do {
                     if (!llIIIllIllllIl.hasNext()) {
                        if (!llIIIlllIlIIlI) {
                           llIIIlllIlIlII.draw();
                        }

                        if (llIIIlllIIIIlI) {
                           GL11.glDisable(2832);
                        }

                        GL11.glDisable(2881);
                        GL11.glEnable(3553);
                        GL11.glDisable(3042);
                        GL11.glDisable(2848);
                        GL11.glDisable(3089);
                        GL11.glPopMatrix();
                        float llIIIllIlllllI = RainbowShader.Companion;
                        String llIIIllIllllIl = (Boolean)llIIIlllIIlIlI.borderRainbowValue.get();
                        int llIIIllIllllII = ((Number)llIIIlllIIlIlI.rainbowXValue.get()).floatValue() == 0.0F ? 0.0F : 1.0F / ((Number)llIIIlllIIlIlI.rainbowXValue.get()).floatValue();
                        long llIIIlllIllIll = ((Number)llIIIlllIIlIlI.rainbowYValue.get()).floatValue() == 0.0F ? 0.0F : 1.0F / ((Number)llIIIlllIIlIlI.rainbowYValue.get()).floatValue();
                        llIIIllllIIlIl = (float)(System.currentTimeMillis() % (long)10000) / 10000.0F;
                        short llIIIllIlllIIl = false;
                        double llIIIllIlllIII = RainbowShader.INSTANCE;
                        if (llIIIllIllllIl) {
                           llIIIllIlllIII.setStrengthX(llIIIllIllllII);
                           llIIIllIlllIII.setStrengthY(llIIIlllIllIll);
                           llIIIllIlllIII.setOffset(llIIIllllIIlIl);
                           llIIIllIlllIII.startShader();
                        }

                        float llIIIllIlllllI = (Closeable)llIIIllIlllIII;
                        llIIIllIllllIl = false;
                        Throwable llIIIllIllllII = (Throwable)null;

                        try {
                           long llIIIllIlllIll = (RainbowShader)llIIIllIlllllI;
                           int llIIIlllIlIlll = false;
                           RenderUtils.drawBorder(0.0F, 0.0F, llIIIlllIIIlll, llIIIlllIIIlll, ((Number)llIIIlllIIlIlI.borderStrengthValue.get()).floatValue(), (new Color(((Number)llIIIlllIIlIlI.borderRedValue.get()).intValue(), ((Number)llIIIlllIIlIlI.borderGreenValue.get()).intValue(), ((Number)llIIIlllIIlIlI.borderBlueValue.get()).intValue(), ((Number)llIIIlllIIlIlI.borderAlphaValue.get()).intValue())).getRGB());
                           GL11.glEnable(3042);
                           GL11.glDisable(3553);
                           GL11.glBlendFunc(770, 771);
                           GL11.glEnable(2848);
                           RenderUtils.glColor(((Number)llIIIlllIIlIlI.borderRedValue.get()).intValue(), ((Number)llIIIlllIIlIlI.borderGreenValue.get()).intValue(), ((Number)llIIIlllIIlIlI.borderBlueValue.get()).intValue(), ((Number)llIIIlllIIlIlI.borderAlphaValue.get()).intValue());
                           GL11.glLineWidth(((Number)llIIIlllIIlIlI.borderStrengthValue.get()).floatValue());
                           GL11.glBegin(1);
                           GL11.glVertex2f(llIIIlllIlIIIl, 0.0F);
                           GL11.glVertex2f(llIIIlllIlIIIl, llIIIlllIIIlll);
                           GL11.glVertex2f(0.0F, llIIIlllIlIIIl);
                           GL11.glVertex2f(llIIIlllIIIlll, llIIIlllIlIIIl);
                           GL11.glEnd();
                           GL11.glEnable(3553);
                           GL11.glDisable(3042);
                           GL11.glDisable(2848);
                           Unit llIIIllIlllIll4 = Unit.INSTANCE;
                        } catch (Throwable var33) {
                           llIIIllIllllII = var33;
                           throw var33;
                        } finally {
                           CloseableKt.closeFinally(llIIIllIlllllI, llIIIllIllllII);
                        }

                        var10001 = false;
                        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                        return new Border(0.0F, 0.0F, llIIIlllIIIlll, llIIIlllIIIlll);
                     }

                     llIIIllllIIIII = (Entity)llIIIllIllllIl.next();
                  } while(llIIIllllIIIII == null);
               } while(llIIIllllIIIII == access$getMc$p$s1046033730().thePlayer);
            } while(!EntityUtils.isSelected(llIIIllllIIIII, false));

            llIIIllIllllII = new Vector2f((float)(llIIIlllIIllIl.posX - llIIIllllIIIII.posX), (float)(llIIIlllIIllIl.posZ - llIIIllllIIIII.posZ));
         } while(llIIIlllIIIlIl < (double)llIIIllIllllII.lengthSquared());

         long llIIIllIlllIll = llIIIlllIlIIlI || ((Number)llIIIlllIIlIlI.fovSizeValue.get()).floatValue() > 0.0F;
         if (llIIIllIlllIll) {
            GL11.glPushMatrix();
            GL11.glTranslatef(llIIIllIllllII.x / llIIIlllIIIllI * llIIIlllIIIlll, llIIIllIllllII.y / llIIIlllIIIllI * llIIIlllIIIlll, 0.0F);
            GL11.glRotatef(llIIIllllIIIII.rotationYaw, 0.0F, 0.0F, 1.0F);
         }

         if (((Number)llIIIlllIIlIlI.fovSizeValue.get()).floatValue() > 0.0F) {
            GL11.glPushMatrix();
            GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
            llIIIllllIIlIl = ((Number)llIIIlllIIlIlI.fovSizeValue.get()).floatValue() / llIIIlllIIIllI * llIIIlllIIIlll;
            GL11.glScalef(llIIIllllIIlIl, llIIIllllIIlIl, llIIIllllIIlIl);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, (Boolean)llIIIlllIIlIlI.minimapValue.get() ? 0.75F : 0.25F);
            var10000 = llIIIlllIIlIlI.fovMarkerVertexBuffer;
            if (var10000 == null) {
               Intrinsics.throwNpe();
            }

            VertexBuffer llIIIllllIIllI = var10000;
            llIIIllllIIllI.bindBuffer();
            GL11.glEnableClientState(32884);
            GL11.glVertexPointer(3, 5126, 12, 0L);
            llIIIllllIIllI.drawArrays(6);
            llIIIllllIIllI.unbindBuffer();
            GL11.glDisableClientState(32884);
            GL11.glPopMatrix();
         }

         Color llIIIllllIIlII;
         Module var60;
         if (llIIIlllIlIIlI) {
            if ((Boolean)llIIIlllIIlIlI.useESPColorsValue.get()) {
               var60 = LiquidBounce.INSTANCE.getModuleManager().get(ESP.class);
               if (var60 == null) {
                  throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.render.ESP");
               }

               llIIIllllIIlII = ((ESP)var60).getColor(llIIIllllIIIII);
               Intrinsics.checkExpressionValueIsNotNull(llIIIllllIIlII, "color");
               GL11.glColor4f((float)llIIIllllIIlII.getRed() / 255.0F, (float)llIIIllllIIlII.getGreen() / 255.0F, (float)llIIIllllIIlII.getBlue() / 255.0F, 1.0F);
            } else {
               GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            }

            GL11.glBegin(4);
            GL11.glVertex2f(-llIIIllIllllll * 0.25F, llIIIllIllllll * 0.5F);
            GL11.glVertex2f(llIIIllIllllll * 0.25F, llIIIllIllllll * 0.5F);
            GL11.glVertex2f(0.0F, -llIIIllIllllll * 0.5F);
            GL11.glEnd();
         } else {
            var60 = LiquidBounce.INSTANCE.getModuleManager().get(ESP.class);
            if (var60 == null) {
               throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.render.ESP");
            }

            llIIIllllIIlII = ((ESP)var60).getColor(llIIIllllIIIII);
            WorldRenderer var64 = llIIIlllIlIlIl.pos((double)(llIIIllIllllII.x / llIIIlllIIIllI * llIIIlllIIIlll), (double)(llIIIllIllllII.y / llIIIlllIIIllI * llIIIlllIIIlll), 0.0D);
            Intrinsics.checkExpressionValueIsNotNull(llIIIllllIIlII, "color");
            var64.color((float)llIIIllllIIlII.getRed() / 255.0F, (float)llIIIllllIIlII.getGreen() / 255.0F, (float)llIIIllllIIlII.getBlue() / 255.0F, 1.0F).endVertex();
         }

         if (llIIIllIlllIll) {
            GL11.glPopMatrix();
         }
      }
   }

   public Radar(double llIIIllIIIllII, double llIIIllIIIlIll) {
      super(llIIIllIIIllII, llIIIllIIIlIll, 0.0F, (Side)null, 12, (DefaultConstructorMarker)null);
      llIIIllIIIllIl.sizeValue = new FloatValue("Size", 90.0F, 30.0F, 500.0F);
      llIIIllIIIllIl.viewDistanceValue = new FloatValue("View Distance", 4.0F, 0.5F, 32.0F);
      llIIIllIIIllIl.playerShapeValue = new ListValue("Player Shape", new String[]{"Triangle", "Rectangle", "Circle"}, "Triangle");
      llIIIllIIIllIl.playerSizeValue = new FloatValue("Player Size", 2.0F, 0.5F, 20.0F);
      llIIIllIIIllIl.useESPColorsValue = new BoolValue("Use ESP Colors", true);
      llIIIllIIIllIl.fovSizeValue = new FloatValue("FOV Size", 10.0F, 0.0F, 50.0F);
      llIIIllIIIllIl.fovAngleValue = new FloatValue("FOV Angle", 70.0F, 30.0F, 160.0F);
      llIIIllIIIllIl.minimapValue = new BoolValue("Minimap", true);
      llIIIllIIIllIl.rainbowXValue = new FloatValue("Rainbow-X", -1000.0F, -2000.0F, 2000.0F);
      llIIIllIIIllIl.rainbowYValue = new FloatValue("Rainbow-Y", -1000.0F, -2000.0F, 2000.0F);
      llIIIllIIIllIl.backgroundRedValue = new IntegerValue("Background Red", 0, 0, 255);
      llIIIllIIIllIl.backgroundGreenValue = new IntegerValue("Background Green", 0, 0, 255);
      llIIIllIIIllIl.backgroundBlueValue = new IntegerValue("Background Blue", 0, 0, 255);
      llIIIllIIIllIl.backgroundAlphaValue = new IntegerValue("Background Alpha", 50, 0, 255);
      llIIIllIIIllIl.borderStrengthValue = new FloatValue("Border Strength", 2.0F, 1.0F, 5.0F);
      llIIIllIIIllIl.borderRedValue = new IntegerValue("Border Red", 0, 0, 255);
      llIIIllIIIllIl.borderGreenValue = new IntegerValue("Border Green", 0, 0, 255);
      llIIIllIIIllIl.borderBlueValue = new IntegerValue("Border Blue", 0, 0, 255);
      llIIIllIIIllIl.borderAlphaValue = new IntegerValue("Border Alpha", 150, 0, 255);
      llIIIllIIIllIl.borderRainbowValue = new BoolValue("Border Rainbow", false);
   }

   public Radar() {
      this(0.0D, 0.0D, 3, (DefaultConstructorMarker)null);
   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   @Metadata(
      mv = {1, 1, 16},
      bv = {1, 0, 3},
      k = 1,
      d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0005"},
      d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Radar$Companion;", "", "()V", "SQRT_OF_TWO", "", "LiquidSense"}
   )
   public static final class Companion {
      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker llIIIlllIIIllll) {
         this();
      }
   }
}
