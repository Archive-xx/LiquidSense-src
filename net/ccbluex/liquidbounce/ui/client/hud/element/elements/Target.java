//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.ui.client.hud.element.elements;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.modules.combat.Aura;
import net.ccbluex.liquidbounce.ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.ui.font.GameFontRenderer;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.extensions.PlayerExtensionKt;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@ElementInfo(
   name = "Target"
)
@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u000b\u001a\u00020\fH\u0016J \u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0012H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0014"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Target;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "()V", "decimalFormat", "Ljava/text/DecimalFormat;", "easingHealth", "", "fadeSpeed", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "lastTarget", "Lnet/minecraft/entity/Entity;", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "drawHead", "", "skin", "Lnet/minecraft/util/ResourceLocation;", "width", "", "height", "LiquidSense"}
)
public final class Target extends Element {
   // $FF: synthetic field
   private final FloatValue fadeSpeed;
   // $FF: synthetic field
   private final DecimalFormat decimalFormat;
   // $FF: synthetic field
   private float easingHealth;
   // $FF: synthetic field
   private Entity lastTarget;

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   @NotNull
   public Border drawElement() {
      Module var10000 = LiquidBounce.INSTANCE.getModuleManager().get(Aura.class);
      if (var10000 == null) {
         throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.combat.Aura");
      } else {
         char lIIlIIIIIllII = ((Aura)var10000).getTarget();
         if (lIIlIIIIIllII instanceof EntityPlayer) {
            float lIIlIIIIlIIII;
            label30: {
               if (!(Intrinsics.areEqual((Object)lIIlIIIIIllII, (Object)lIIlIIIIIlllI.lastTarget) ^ true) && !(lIIlIIIIIlllI.easingHealth < (float)0) && !(lIIlIIIIIlllI.easingHealth > ((EntityPlayer)lIIlIIIIIllII).getMaxHealth())) {
                  lIIlIIIIlIIII = lIIlIIIIIlllI.easingHealth - ((EntityPlayer)lIIlIIIIIllII).getHealth();
                  double lIIlIIIIIlIlI = false;
                  if (!((double)Math.abs(lIIlIIIIlIIII) < 0.01D)) {
                     break label30;
                  }
               }

               lIIlIIIIIlllI.easingHealth = ((EntityPlayer)lIIlIIIIIllII).getHealth();
            }

            GameFontRenderer var10001 = Fonts.font40;
            String var10002 = ((EntityPlayer)lIIlIIIIIllII).getName();
            Intrinsics.checkExpressionValueIsNotNull(var10002, "target.name");
            lIIlIIIIlIIII = (float)RangesKt.coerceAtLeast(38 + var10001.getStringWidth(var10002), 118);
            Color var10005 = Color.BLACK;
            Intrinsics.checkExpressionValueIsNotNull(var10005, "Color.BLACK");
            int var20 = var10005.getRGB();
            Color var10006 = Color.BLACK;
            Intrinsics.checkExpressionValueIsNotNull(var10006, "Color.BLACK");
            RenderUtils.drawBorderedRect(0.0F, 0.0F, lIIlIIIIlIIII, 36.0F, 3.0F, var20, var10006.getRGB());
            if (lIIlIIIIIlllI.easingHealth > ((EntityPlayer)lIIlIIIIIllII).getHealth()) {
               RenderUtils.drawRect(0.0F, 34.0F, lIIlIIIIIlllI.easingHealth / ((EntityPlayer)lIIlIIIIIllII).getMaxHealth() * lIIlIIIIlIIII, 36.0F, (new Color(252, 185, 65)).getRGB());
            }

            RenderUtils.drawRect(0.0F, 34.0F, ((EntityPlayer)lIIlIIIIIllII).getHealth() / ((EntityPlayer)lIIlIIIIIllII).getMaxHealth() * lIIlIIIIlIIII, 36.0F, (new Color(252, 96, 66)).getRGB());
            if (lIIlIIIIIlllI.easingHealth < ((EntityPlayer)lIIlIIIIIllII).getHealth()) {
               RenderUtils.drawRect(lIIlIIIIIlllI.easingHealth / ((EntityPlayer)lIIlIIIIIllII).getMaxHealth() * lIIlIIIIlIIII, 34.0F, ((EntityPlayer)lIIlIIIIIllII).getHealth() / ((EntityPlayer)lIIlIIIIIllII).getMaxHealth() * lIIlIIIIlIIII, 36.0F, (new Color(44, 201, 144)).getRGB());
            }

            float var15 = lIIlIIIIIlllI.easingHealth;
            float var18 = ((EntityPlayer)lIIlIIIIIllII).getHealth() - lIIlIIIIIlllI.easingHealth;
            double lIIlIIIIIlIlI = 2.0F;
            int lIIlIIIIIlIIl = 10.0F - ((Number)lIIlIIIIIlllI.fadeSpeed.get()).floatValue();
            short lIIlIIIIIIlIl = var18;
            byte lIIlIIIIIIllI = var15;
            String lIIlIIIIIlIII = false;
            char lIIlIIIIIIlII = (float)Math.pow((double)lIIlIIIIIlIlI, (double)lIIlIIIIIlIIl);
            lIIlIIIIIlllI.easingHealth = lIIlIIIIIIllI + lIIlIIIIIIlIl / lIIlIIIIIIlII * (float)RenderUtils.deltaTime;
            Fonts.font40.drawString(((EntityPlayer)lIIlIIIIIllII).getName(), 36, 3, 16777215);
            boolean var16 = false;
            GameFontRenderer var13 = Fonts.font35;
            StringBuilder var17 = (new StringBuilder()).append("Distance: ");
            DecimalFormat var19 = lIIlIIIIIlllI.decimalFormat;
            EntityPlayerSP var10003 = access$getMc$p$s1046033730().thePlayer;
            Intrinsics.checkExpressionValueIsNotNull(var10003, "mc.thePlayer");
            var13.drawString(String.valueOf(var17.append(var19.format(PlayerExtensionKt.getDistanceToEntityBox((Entity)var10003, (Entity)lIIlIIIIIllII)))), 36, 15, 16777215);
            var16 = false;
            Minecraft var14 = access$getMc$p$s1046033730();
            Intrinsics.checkExpressionValueIsNotNull(var14, "mc");
            NetworkPlayerInfo lIIlIIIIlIIIl = var14.getNetHandler().getPlayerInfo(((EntityPlayer)lIIlIIIIIllII).getUniqueID());
            if (lIIlIIIIlIIIl != null) {
               Fonts.font35.drawString(String.valueOf((new StringBuilder()).append("Ping: ").append(RangesKt.coerceAtLeast(lIIlIIIIlIIIl.getResponseTime(), 0))), 36, 24, 16777215);
               var16 = false;
               int lIIlIIIIIlIIl = lIIlIIIIlIIIl.getLocationSkin();
               Intrinsics.checkExpressionValueIsNotNull(lIIlIIIIIlIIl, "locationSkin");
               lIIlIIIIIlllI.drawHead(lIIlIIIIIlIIl, 30, 30);
            }
         }

         lIIlIIIIIlllI.lastTarget = (Entity)lIIlIIIIIllII;
         return new Border(0.0F, 0.0F, 120.0F, 36.0F);
      }
   }

   public Target() {
      super(0.0D, 0.0D, 0.0F, (Side)null, 15, (DefaultConstructorMarker)null);
      lIIIllllllIII.decimalFormat = new DecimalFormat("##0.00", new DecimalFormatSymbols(Locale.ENGLISH));
      lIIIllllllIII.fadeSpeed = new FloatValue("FadeSpeed", 2.0F, 1.0F, 9.0F);
   }

   private final void drawHead(ResourceLocation lIIIlllllllII, int lIIIllllllllI, int lIIIllllllIlI) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      Minecraft var10000 = access$getMc$p$s1046033730();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "mc");
      var10000.getTextureManager().bindTexture(lIIIlllllllII);
      Gui.drawScaledCustomSizeModalRect(2, 2, 8.0F, 8.0F, 8, 8, lIIIllllllllI, lIIIllllllIlI, 64.0F, 64.0F);
   }
}
