//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.ui.client.hud.element.elements;

import java.awt.Color;
import java.io.Closeable;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.UInt;
import kotlin.Unit;
import kotlin.UnsignedKt;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.modules.combat.Aura;
import net.ccbluex.liquidbounce.ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.ui.font.GameFontRenderer;
import net.ccbluex.liquidbounce.utils.GuiPlayerTabOverlay;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.shader.shaders.RainbowShader;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@ElementInfo(
   name = "TargetHud"
)
@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B#\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0016\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dJ\u001e\u0010\u001e\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001f\u001a\u00020\u001d2\u0006\u0010\u001c\u001a\u00020\u001dJ&\u0010 \u001a\u00020\u00192\u0006\u0010!\u001a\u00020\"2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010#\u001a\u00020$J \u0010%\u001a\u00020\u00192\u0006\u0010&\u001a\u00020\u001d2\u0006\u0010'\u001a\u00020\u001d2\u0006\u0010(\u001a\u00020)H\u0002J@\u0010*\u001a\u00020\u00192\u0006\u0010\u0002\u001a\u00020\u001d2\u0006\u0010\u0004\u001a\u00020\u001d2\u0006\u0010+\u001a\u00020\u001d2\u0006\u0010,\u001a\u00020\u001d2\u0006\u0010-\u001a\u00020\u001d2\u0006\u0010.\u001a\u00020/2\u0006\u00100\u001a\u00020/H\u0002J\b\u00101\u001a\u000202H\u0016J\u000e\u00103\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bR\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00064"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/TargetHud;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "x", "", "y", "side", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side;", "(DDLnet/ccbluex/liquidbounce/ui/client/hud/element/Side;)V", "Mode", "Lnet/ccbluex/liquidbounce/value/ListValue;", "backgroundColorAlphaValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "backgroundColorBlueValue", "backgroundColorGreenValue", "backgroundColorModeValue", "backgroundColorRedValue", "brightnessValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "modules", "", "Lnet/ccbluex/liquidbounce/features/module/Module;", "rainbowX", "rainbowY", "saturationValue", "Armor", "", "killAura", "Lnet/ccbluex/liquidbounce/features/module/modules/combat/Aura;", "Y", "", "DefBackground", "width", "DistanceToEntity", "fontRenderer", "Lnet/minecraft/client/gui/FontRenderer;", "Distance", "", "Model", "yaw", "pitch", "entityLivingBase", "Lnet/minecraft/entity/EntityLivingBase;", "drawBox", "x2", "y2", "size", "color", "", "color2", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "head", "LiquidSense"}
)
public final class TargetHud extends Element {
   // $FF: synthetic field
   private final IntegerValue backgroundColorBlueValue;
   // $FF: synthetic field
   private final IntegerValue backgroundColorRedValue;
   // $FF: synthetic field
   private final FloatValue rainbowY;
   // $FF: synthetic field
   private final FloatValue brightnessValue;
   // $FF: synthetic field
   private List<? extends Module> modules;
   // $FF: synthetic field
   private final IntegerValue backgroundColorAlphaValue;
   // $FF: synthetic field
   private final ListValue backgroundColorModeValue;
   // $FF: synthetic field
   private final FloatValue rainbowX;
   // $FF: synthetic field
   private final IntegerValue backgroundColorGreenValue;
   // $FF: synthetic field
   private final FloatValue saturationValue;
   // $FF: synthetic field
   private final ListValue Mode;

   public final void Armor(@NotNull Aura lIlllIIIIllIlll, float lIlllIIIIllIIll) {
      Intrinsics.checkParameterIsNotNull(lIlllIIIIllIlll, "killAura");
      GlStateManager.pushMatrix();
      if (lIlllIIIIllIIll == 26.5F) {
      }

      GlStateManager.translate(70.0F, 0.0F, 0.0F);
      lIlllIIIIllIlIl.drawBox(-0.5F, lIlllIIIIllIIll - 0.5F, 18.5F, lIlllIIIIllIIll + 4.25F, 0.5F, (new Color(24, 27, 30)).getRGB(), (new Color(0, 0, 0)).getRGB());
      EntityLivingBase var10002 = lIlllIIIIllIlll.getTarget();
      Integer var3;
      if (var10002 != null) {
         var3 = var10002.getTotalArmorValue();
      } else {
         boolean var10003 = false;
         var3 = null;
      }

      if (var3 == null) {
         Intrinsics.throwNpe();
      }

      RenderUtils.drawRect(0.0F, lIlllIIIIllIIll, (float)var3 * 0.9F, 30.5F, (new Color(0, 255, 100)).getRGB());
      GlStateManager.popMatrix();
   }

   public TargetHud(double lIllIllllIlIlll, double lIllIllllIllIlI, @NotNull Side lIllIllllIlIlIl) {
      Intrinsics.checkParameterIsNotNull(lIllIllllIlIlIl, "side");
      super(lIllIllllIlIlll, lIllIllllIllIlI, 1.0F, lIllIllllIlIlIl);
      lIllIllllIlllII.Mode = new ListValue("Mode", new String[]{"Head", "Model"}, "Head");
      lIllIllllIlllII.rainbowX = new FloatValue("Rainbow-X", -1000.0F, -2000.0F, 2000.0F);
      lIllIllllIlllII.rainbowY = new FloatValue("Rainbow-Y", -1000.0F, -2000.0F, 2000.0F);
      lIllIllllIlllII.backgroundColorModeValue = new ListValue("Background-Color", new String[]{"Custom", "Random", "Rainbow"}, "Custom");
      lIllIllllIlllII.backgroundColorRedValue = new IntegerValue("Background-R", 0, 0, 255);
      lIllIllllIlllII.backgroundColorGreenValue = new IntegerValue("Background-G", 0, 0, 255);
      lIllIllllIlllII.backgroundColorBlueValue = new IntegerValue("Background-B", 0, 0, 255);
      lIllIllllIlllII.backgroundColorAlphaValue = new IntegerValue("Background-Alpha", 0, 0, 255);
      lIllIllllIlllII.saturationValue = new FloatValue("Random-Saturation", 0.9F, 0.0F, 1.0F);
      lIllIllllIlllII.brightnessValue = new FloatValue("Random-Brightness", 1.0F, 0.0F, 1.0F);
      lIllIllllIlllII.modules = CollectionsKt.emptyList();
   }

   public TargetHud() {
      this(0.0D, 0.0D, (Side)null, 7, (DefaultConstructorMarker)null);
   }

   @NotNull
   public Border drawElement() {
      float lIlllIIIllIIIll = 3.0F;
      float lIlllIIIllIIlll = 100.0F;
      String lIlllIIIllIlIII = (String)lIlllIIIllIIlIl.backgroundColorModeValue.get();
      boolean lIlllIIIllIlIIl = StringsKt.equals(lIlllIIIllIlIII, "Rainbow", true);
      int lIlllIIIllIlIlI = (new Color(((Number)lIlllIIIllIIlIl.backgroundColorRedValue.get()).intValue(), ((Number)lIlllIIIllIIlIl.backgroundColorGreenValue.get()).intValue(), ((Number)lIlllIIIllIIlIl.backgroundColorBlueValue.get()).intValue(), ((Number)lIlllIIIllIIlIl.backgroundColorAlphaValue.get()).intValue())).getRGB();
      Module var10000 = LiquidBounce.INSTANCE.getModuleManager().getModule(Aura.class);
      if (var10000 == null) {
         throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.combat.Aura");
      } else {
         boolean lIlllIIIlIllllI = (Aura)var10000;
         StringBuilder var34 = (new StringBuilder()).append("#§f");
         EntityLivingBase var10001 = lIlllIIIlIllllI.getTarget();
         boolean var10002;
         String var36;
         if (var10001 != null) {
            var36 = var10001.getName();
         } else {
            var10002 = false;
            var36 = null;
         }

         String lIlllIIIllIllII = String.valueOf(var34.append(var36));
         var34 = (new StringBuilder()).append("§2");
         var10001 = lIlllIIIlIllllI.getTarget();
         StringBuilder lIlllIIIlIIllll;
         int lIlllIIIlIIlllI;
         UInt var39;
         if (var10001 != null) {
            long lIlllIIIlIllIll = var10001.getHealth() * (float)5;
            lIlllIIIlIIllll = var34;
            short lIlllIIIlIllIlI = false;
            lIlllIIIlIIlllI = UnsignedKt.doubleToUInt((double)lIlllIIIlIllIll);
            var34 = lIlllIIIlIIllll;
            var39 = UInt.box-impl(lIlllIIIlIIlllI);
         } else {
            var10002 = false;
            var39 = null;
         }

         String lIlllIIIllIllIl = String.valueOf(var34.append(var39).append("%"));
         var34 = (new StringBuilder()).append("Armor ");
         var10001 = lIlllIIIlIllllI.getTarget();
         if (var10001 != null) {
            short lIlllIIIlIllIlI = var10001.getTotalArmorValue();
            lIlllIIIlIIllll = var34;
            Exception lIlllIIIlIllIIl = false;
            lIlllIIIlIIlllI = UInt.constructor-impl(lIlllIIIlIllIlI);
            var34 = lIlllIIIlIIllll;
            var39 = UInt.box-impl(lIlllIIIlIIlllI);
         } else {
            var10002 = false;
            var39 = null;
         }

         String lIlllIIIllIlllI = String.valueOf(var34.append(var39));
         String lIlllIIIllIllll = "Distance ";
         Exception lIlllIIIlIllIIl = ((Number)lIlllIIIllIIlIl.saturationValue.get()).floatValue();
         float lIlllIIIlllIIIl = ((Number)lIlllIIIllIIlIl.brightnessValue.get()).floatValue();
         EntityLivingBase var40 = lIlllIIIlIllllI.getTarget();
         float var42;
         Float var43;
         Float var47;
         boolean var49;
         if (var40 != null) {
            var42 = var40.getMaxHealth();
            var10001 = lIlllIIIlIllllI.getTarget();
            if (var10001 != null) {
               var47 = var10001.getHealth();
            } else {
               var10002 = false;
               var47 = null;
            }

            if (var47 == null) {
               Intrinsics.throwNpe();
            }

            var43 = (var42 - var47) * (float)5;
         } else {
            var49 = false;
            var43 = null;
         }

         Float lIlllIIIlllIIlI = var43;
         if (lIlllIIIlIllllI.getTarget() != null) {
            RenderUtils.drawBorderedRect(0.0F, 0.0F, lIlllIIIllIIlll, 43.0F, 3.0F, (new Color(0, 0, 0, 150)).getRGB(), 0);
            RenderUtils.drawRect(0.0F, 0.0F, lIlllIIIllIIlll, 43.0F, (new Color(15, 15, 15)).getRGB());
            Fonts.font30.drawString(lIlllIIIllIllII, 37.5F, lIlllIIIllIIIll + (float)2, (new Color(255, 255, 255)).getRGB(), false);
            var49 = false;
            lIlllIIIllIIIll += (float)24;
            Fonts.font30.drawString(lIlllIIIllIlllI, 37.5F, lIlllIIIllIIIll, (new Color(255, 255, 255)).getRGB(), false);
            var49 = false;
            if (lIlllIIIlllIIlI != null) {
               lIlllIIIllIIlIl.Armor(lIlllIIIlIllllI, lIlllIIIllIIIll);
               GameFontRenderer var51 = Fonts.font30;
               Intrinsics.checkExpressionValueIsNotNull(var51, "Fonts.font30");
               lIlllIIIllIIlIl.DistanceToEntity((FontRenderer)var51, lIlllIIIlIllllI, lIlllIIIllIIIll, lIlllIIIllIllll);
               lIlllIIIllIIlIl.DefBackground(lIlllIIIlIllllI, lIlllIIIllIIlll, lIlllIIIllIIIll);
               var40 = lIlllIIIlIllllI.getTarget();
               if (var40 != null) {
                  var43 = var40.getHealth();
               } else {
                  var49 = false;
                  var43 = null;
               }

               if (var43 == null) {
                  Intrinsics.throwNpe();
               }

               if (var43 <= (float)20) {
                  var10001 = lIlllIIIlIllllI.getTarget();
                  if (var10001 != null) {
                     var47 = var10001.getMaxHealth();
                  } else {
                     var10002 = false;
                     var47 = null;
                  }

                  if (var47 == null) {
                     Intrinsics.throwNpe();
                  }

                  var42 = 99.0F * var47 / (float)20 - lIlllIIIlllIIlI;
               } else {
                  var42 = 99.0F;
               }

               float lIlllIIIlllIIll = var42;
               char lIlllIIIlIlIlIl = RainbowShader.Companion;
               char lIlllIIIlIlIlII = ((Number)lIlllIIIllIIlIl.rainbowX.get()).floatValue() == 0.0F ? 0.0F : 1.0F / ((Number)lIlllIIIllIIlIl.rainbowX.get()).floatValue();
               boolean lIlllIIIlIlIIll = ((Number)lIlllIIIllIIlIl.rainbowY.get()).floatValue() == 0.0F ? 0.0F : 1.0F / ((Number)lIlllIIIllIIlIl.rainbowY.get()).floatValue();
               float lIlllIIIlllIlll = (float)(System.currentTimeMillis() % (long)10000) / 100000.0F;
               int lIlllIIIlllIlII = false;
               RainbowShader lIlllIIIllllIll = RainbowShader.INSTANCE;
               if (lIlllIIIllIlIIl) {
                  lIlllIIIllllIll.setStrengthX(lIlllIIIlIlIlII);
                  lIlllIIIllllIll.setStrengthY(lIlllIIIlIlIIll);
                  lIlllIIIllllIll.setOffset(lIlllIIIlllIlll);
                  lIlllIIIllllIll.startShader();
               }

               char lIlllIIIlIlIlIl = (Closeable)lIlllIIIllllIll;
               char lIlllIIIlIlIlII = false;
               Throwable lIlllIIIlIlIIll = (Throwable)null;

               try {
                  RainbowShader lIlllIIIlllIlIl = (RainbowShader)lIlllIIIlIlIlIl;
                  lIlllIIIlllIlII = false;
                  RenderUtils.drawRect(1.0F, 38.0F, lIlllIIIlllIIll, 41.0F, lIlllIIIllIlIIl ? -16777216 : (StringsKt.equals(lIlllIIIllIlIII, "Random", true) ? 0 : lIlllIIIllIlIlI));
                  Unit lIlllIIIlIlIIlI2 = Unit.INSTANCE;
               } catch (Throwable var25) {
                  lIlllIIIlIlIIll = var25;
                  throw var25;
               } finally {
                  CloseableKt.closeFinally(lIlllIIIlIlIlIl, lIlllIIIlIlIIll);
               }
            }

            int lIlllIIIlIlIllI = (String)lIlllIIIllIIlIl.Mode.get();
            char lIlllIIIlIlIlIl = false;
            if (lIlllIIIlIlIllI == null) {
               throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }

            String var50 = lIlllIIIlIlIllI.toLowerCase();
            Intrinsics.checkExpressionValueIsNotNull(var50, "(this as java.lang.String).toLowerCase()");
            lIlllIIIlIlIllI = var50;
            switch(lIlllIIIlIlIllI.hashCode()) {
            case 3198432:
               if (lIlllIIIlIlIllI.equals("head")) {
                  lIlllIIIllIIlIl.head(lIlllIIIlIllllI);
               }
               break;
            case 104069929:
               if (lIlllIIIlIlIllI.equals("model")) {
                  GlStateManager.pushMatrix();
                  GlStateManager.scale(0.38D, 0.38D, 0.38D);
                  GlStateManager.translate(40.0F, 95.0F, 40.0F);
                  var10001 = lIlllIIIlIllllI.getTarget();
                  if (var10001 != null) {
                     var47 = var10001.rotationYaw;
                  } else {
                     var10002 = false;
                     var47 = null;
                  }

                  if (var47 == null) {
                     Intrinsics.throwNpe();
                  }

                  float var52 = var47;
                  EntityLivingBase var45 = lIlllIIIlIllllI.getTarget();
                  Float var46;
                  if (var45 != null) {
                     var46 = var45.rotationPitch;
                  } else {
                     boolean var10003 = false;
                     var46 = null;
                  }

                  if (var46 == null) {
                     Intrinsics.throwNpe();
                  }

                  float var48 = var46;
                  EntityLivingBase var44 = lIlllIIIlIllllI.getTarget();
                  if (var44 == null) {
                     Intrinsics.throwNpe();
                  }

                  lIlllIIIllIIlIl.Model(var52, var48, var44);
                  GlStateManager.popMatrix();
               }
            }
         }

         return new Border(0.0F, 0.0F, lIlllIIIllIIlll, 43.0F);
      }
   }

   public final void DefBackground(@NotNull Aura lIlllIIIIlIlIIl, float lIlllIIIIlIlIII, float lIlllIIIIlIlIll) {
      Intrinsics.checkParameterIsNotNull(lIlllIIIIlIlIIl, "killAura");
      float var10002 = lIlllIIIIlIlIll + 10.5F;
      float var10003 = lIlllIIIIlIlIII - 1.0F;
      float var10004 = lIlllIIIIlIlIll + 14.5F;
      EntityLivingBase var10006 = lIlllIIIIlIlIIl.getTarget();
      Float var4;
      if (var10006 != null) {
         var4 = var10006.getHealth();
      } else {
         boolean var10007 = false;
         var4 = null;
      }

      if (var4 == null) {
         Intrinsics.throwNpe();
      }

      lIlllIIIIlIlIlI.drawBox(0.5F, var10002, var10003, var10004, 0.5F, var4 <= (float)20 ? (new Color(35, 35, 35)).getRGB() : (new Color(130, 90, 80)).getRGB(), (new Color(50, 50, 50, 150)).getRGB());
   }

   private final void Model(float lIllIlllllIllIl, float lIllIlllllIllII, EntityLivingBase lIllIlllllIlIll) {
      GlStateManager.resetColor();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.enableColorMaterial();
      GlStateManager.pushMatrix();
      GlStateManager.translate(0.0F, 0.0F, 50.0F);
      GlStateManager.scale(-50.0F, 50.0F, 50.0F);
      GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
      float lIllIlllllIllll = lIllIlllllIlIll.renderYawOffset;
      double lIllIlllllIIllI = lIllIlllllIlIll.rotationYaw;
      float lIllIllllllIIIl = lIllIlllllIlIll.rotationPitch;
      float lIllIllllllIIlI = lIllIlllllIlIll.prevRotationYawHead;
      double lIllIlllllIIIll = lIllIlllllIlIll.rotationYawHead;
      GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
      RenderHelper.enableStandardItemLighting();
      GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
      byte lIllIlllllIIIlI = lIllIlllllIllII / 40.0F;
      float lIllIlllllIIIIl = false;
      GlStateManager.rotate(-((float)Math.atan((double)lIllIlllllIIIlI)) * 20.0F, 1.0F, 0.0F, 0.0F);
      lIllIlllllIlIll.renderYawOffset = lIllIlllllIllIl - lIllIlllllIllIl / lIllIlllllIllIl * 0.4F;
      lIllIlllllIlIll.rotationYaw = lIllIlllllIllIl - lIllIlllllIllIl / lIllIlllllIllIl * 0.2F;
      lIllIlllllIlIll.rotationPitch = lIllIlllllIllII;
      lIllIlllllIlIll.rotationYawHead = lIllIlllllIlIll.rotationYaw;
      lIllIlllllIlIll.prevRotationYawHead = lIllIlllllIlIll.rotationYaw;
      GlStateManager.translate(0.0F, 0.0F, 0.0F);
      Minecraft var10000 = access$getMc$p$s1046033730();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "mc");
      RenderManager lIllIllllllIlII = var10000.getRenderManager();
      lIllIllllllIlII.setPlayerViewY(180.0F);
      Intrinsics.checkExpressionValueIsNotNull(lIllIllllllIlII, "renderManager");
      lIllIllllllIlII.setRenderShadow(false);
      lIllIllllllIlII.renderEntityWithPosYaw((Entity)lIllIlllllIlIll, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
      boolean var10001 = false;
      lIllIllllllIlII.setRenderShadow(true);
      lIllIlllllIlIll.renderYawOffset = lIllIlllllIllll;
      lIllIlllllIlIll.rotationYaw = lIllIlllllIIllI;
      lIllIlllllIlIll.rotationPitch = lIllIllllllIIIl;
      lIllIlllllIlIll.prevRotationYawHead = lIllIllllllIIlI;
      lIllIlllllIlIll.rotationYawHead = lIllIlllllIIIll;
      GlStateManager.popMatrix();
      RenderHelper.disableStandardItemLighting();
      GlStateManager.disableRescaleNormal();
      GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
      GlStateManager.disableTexture2D();
      GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
      GlStateManager.resetColor();
   }

   public final void DistanceToEntity(@NotNull FontRenderer lIlllIIIlIIIIII, @NotNull Aura lIlllIIIlIIIlII, float lIlllIIIlIIIIll, @NotNull String lIlllIIIIllllIl) {
      Intrinsics.checkParameterIsNotNull(lIlllIIIlIIIIII, "fontRenderer");
      Intrinsics.checkParameterIsNotNull(lIlllIIIlIIIlII, "killAura");
      Intrinsics.checkParameterIsNotNull(lIlllIIIIllllIl, "Distance");
      EntityLivingBase var10000 = lIlllIIIlIIIlII.getTarget();
      boolean var10001;
      Float var6;
      if (var10000 != null) {
         var6 = var10000.getDistanceToEntity((Entity)access$getMc$p$s1046033730().thePlayer);
      } else {
         var10001 = false;
         var6 = null;
      }

      if (var6 == null) {
         Intrinsics.throwNpe();
      }

      float var7;
      if (var6 <= (float)8) {
         var10000 = lIlllIIIlIIIlII.getTarget();
         if (var10000 != null) {
            var6 = var10000.getDistanceToEntity((Entity)access$getMc$p$s1046033730().thePlayer);
         } else {
            var10001 = false;
            var6 = null;
         }

         if (var6 == null) {
            Intrinsics.throwNpe();
         }

         var7 = var6 * 2.25F;
      } else {
         var7 = 18.0F;
      }

      byte lIlllIIIIllllII = var7;
      GlStateManager.pushMatrix();
      if (lIlllIIIlIIIIll == 26.5F) {
      }

      GlStateManager.translate(70.0F, -10.0F, 0.0F);
      lIlllIIIlIIIIII.drawString(lIlllIIIIllllIl, -32.0F, 27.0F, (new Color(255, 255, 255)).getRGB(), false);
      var10001 = false;
      lIlllIIIlIIIIIl.drawBox(-0.5F, lIlllIIIlIIIIll - 0.5F, 18.5F, lIlllIIIlIIIIll + 4.25F, 0.5F, (new Color(24, 27, 30)).getRGB(), (new Color(0, 0, 0)).getRGB());
      RenderUtils.drawRect(0.0F, lIlllIIIlIIIIll, lIlllIIIIllllII, 30.5F, (new Color(170, 100, 50)).getRGB());
      GlStateManager.popMatrix();
   }

   private final void drawBox(float lIlllIIIIIIllII, float lIlllIIIIIIIlII, float lIlllIIIIIIIIll, float lIlllIIIIIIlIIl, float lIlllIIIIIIlIII, int lIlllIIIIIIIIII, int lIllIllllllllll) {
      RenderUtils.drawRect(lIlllIIIIIIllII, lIlllIIIIIIIlII, lIlllIIIIIIIIll, lIlllIIIIIIlIIl, lIlllIIIIIIIIII);
      RenderUtils.drawRect(lIlllIIIIIIllII, lIlllIIIIIIIlII, lIlllIIIIIIIIll, lIlllIIIIIIIlII + lIlllIIIIIIlIII, lIllIllllllllll);
      RenderUtils.drawRect(lIlllIIIIIIllII, lIlllIIIIIIlIIl - lIlllIIIIIIlIII, lIlllIIIIIIIIll, lIlllIIIIIIlIIl, lIllIllllllllll);
      RenderUtils.drawRect(lIlllIIIIIIllII, lIlllIIIIIIIlII, lIlllIIIIIIllII + lIlllIIIIIIlIII, lIlllIIIIIIlIIl, lIllIllllllllll);
      RenderUtils.drawRect(lIlllIIIIIIIIll - lIlllIIIIIIlIII, lIlllIIIIIIIlII, lIlllIIIIIIIIll, lIlllIIIIIIlIIl, lIllIllllllllll);
   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   // $FF: synthetic method
   public TargetHud(double var1, double var3, Side var5, int lIllIllllIIlIll, DefaultConstructorMarker var7) {
      if ((lIllIllllIIlIll & 1) != 0) {
         var1 = 40.0D;
      }

      if ((lIllIllllIIlIll & 2) != 0) {
         var3 = 100.0D;
      }

      if ((lIllIllllIIlIll & 4) != 0) {
         var5 = new Side(Side.Horizontal.MIDDLE, Side.Vertical.DOWN);
      }

      this(var1, var3, var5);
   }

   public final void head(@NotNull Aura lIlllIIIIIllIll) {
      Intrinsics.checkParameterIsNotNull(lIlllIIIIIllIll, "killAura");
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      List var10000 = GuiPlayerTabOverlay.field_175252_a.sortedCopy((Iterable)access$getMc$p$s1046033730().thePlayer.sendQueue.getPlayerInfoMap());
      Intrinsics.checkExpressionValueIsNotNull(var10000, "GuiPlayerTabOverlay.fiel…Queue.getPlayerInfoMap())");
      List lIlllIIIIIlllIl = var10000;
      Iterator lIlllIIIIIllllI = lIlllIIIIIlllIl.iterator();
      GL11.glPushMatrix();
      GL11.glTranslated(0.5D, 0.5D, 0.0D);

      while(lIlllIIIIIllllI.hasNext()) {
         Object var6 = lIlllIIIIIllllI.next();
         if (var6 == null) {
            Intrinsics.throwNpe();
         }

         String lIlllIIIIIlIllI = var6;
         if (lIlllIIIIIlIllI == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.client.network.NetworkPlayerInfo");
         }

         String lIlllIIIIIlIlIl = (NetworkPlayerInfo)lIlllIIIIIlIllI;
         if (lIlllIIIIIllIll.getTarget() instanceof EntityPlayer) {
            access$getMc$p$s1046033730().getTextureManager().bindTexture(lIlllIIIIIlIlIl.getLocationSkin());
            Gui.drawScaledCustomSizeModalRect(2, 2, 8.0F, 8.0F, 8, 8, 32, 32, 64.0F, 64.0F);
            EntityLivingBase var7 = lIlllIIIIIllIll.getTarget();
            if (var7 == null) {
               throw new TypeCastException("null cannot be cast to non-null type net.minecraft.entity.player.EntityPlayer");
            }

            if (((EntityPlayer)var7).isInWater()) {
               Gui.drawScaledCustomSizeModalRect(2, 2, 40.0F, 8.0F, 8, 8, 32, 32, 64.0F, 64.0F);
            }

            GlStateManager.bindTexture(0);
         }
      }

      lIlllIIIIIllIlI.drawBox(1.0F, 1.0F, 35.0F, 35.0F, 0.5F, (new Color(0, 0, 0, 0)).getRGB(), (new Color(75, 75, 75)).getRGB());
      GL11.glPopMatrix();
   }
}
