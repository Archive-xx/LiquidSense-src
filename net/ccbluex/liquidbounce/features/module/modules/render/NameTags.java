//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.FloatCompanionObject;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.text.StringsKt;
import me.AquaVit.liquidSense.API.Particles;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.misc.AntiBot;
import net.ccbluex.liquidbounce.features.module.modules.misc.Teams;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.ui.font.GameFontRenderer;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.FontValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Timer;
import net.minecraft.util.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@ModuleInfo(
   name = "NameTags",
   description = "Changes the scale of the nametags so you can always read them.",
   category = ModuleCategory.RENDER
)
@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J0\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!J\u0016\u0010\"\u001a\u00020\u001f2\u0006\u0010#\u001a\u00020\u001c2\u0006\u0010$\u001a\u00020\u001cJ\u0006\u0010%\u001a\u00020\u001fJ\u0010\u0010&\u001a\u00020\u001f2\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aJ\u0010\u0010'\u001a\u00020\u00182\u0006\u0010(\u001a\u00020)H\u0007J\u0010\u0010*\u001a\u00020\u00182\u0006\u0010(\u001a\u00020+H\u0007J\u0018\u0010,\u001a\u00020\u00182\u0006\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u00020\u001aH\u0002J\u0010\u00100\u001a\u00020\u00182\u0006\u0010-\u001a\u000201H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0013\u001a\u00020\u0014¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016¨\u00062"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/render/NameTags;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "armorValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "borderValue", "clearNamesValue", "distanceValue", "fontValue", "Lnet/ccbluex/liquidbounce/value/FontValue;", "healthValue", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "pingValue", "positions", "", "Lnet/minecraft/util/Vec3;", "scaleValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "teams", "Lnet/ccbluex/liquidbounce/features/module/modules/misc/Teams;", "getTeams", "()Lnet/ccbluex/liquidbounce/features/module/modules/misc/Teams;", "drawString", "", "str", "", "x", "", "y", "colorHex", "", "shadow", "", "getHealthColor", "health", "maxHealth", "getStringHeight", "getStringWidth", "onRender2D", "event", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "onRender3D", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "renderNameTag", "entity", "Lnet/minecraft/entity/EntityLivingBase;", "tag", "updatePositions", "Lnet/minecraft/entity/Entity;", "LiquidSense"}
)
public final class NameTags extends Module {
   // $FF: synthetic field
   private final BoolValue borderValue;
   // $FF: synthetic field
   private final BoolValue armorValue = new BoolValue("Armor", true);
   // $FF: synthetic field
   private final List<Vec3> positions;
   // $FF: synthetic field
   private final ListValue modeValue = new ListValue("Mode", new String[]{"LiquidBounce", "Other"}, "LiquidBounce");
   // $FF: synthetic field
   private final BoolValue distanceValue = new BoolValue("Distance", false);
   // $FF: synthetic field
   private final BoolValue clearNamesValue = new BoolValue("ClearNames", false);
   // $FF: synthetic field
   private final BoolValue pingValue = new BoolValue("Ping", true);
   // $FF: synthetic field
   private final FontValue fontValue;
   // $FF: synthetic field
   @NotNull
   private final Teams teams;
   // $FF: synthetic field
   private final BoolValue healthValue = new BoolValue("Health", true);
   // $FF: synthetic field
   private final FloatValue scaleValue;

   private final void renderNameTag(EntityLivingBase llllllllllllllllllIIIIIIlIIllllI, String llllllllllllllllllIIIIIIlIlIIIII) {
      double llllllllllllllllllIIIIIIlIIlllII = (FontRenderer)llllllllllllllllllIIIIIIlIlIIIlI.fontValue.get();
      long llllllllllllllllllIIIIIIlIIllIll = AntiBot.isBot(llllllllllllllllllIIIIIIlIIllllI);
      Exception llllllllllllllllllIIIIIIlIIllIlI = llllllllllllllllllIIIIIIlIIllIll ? "§3" : (llllllllllllllllllIIIIIIlIIllllI.isInvisible() ? "§6" : (llllllllllllllllllIIIIIIlIIllllI.isSneaking() ? "§4" : "§7"));
      int llllllllllllllllllIIIIIIlIlIIllI = llllllllllllllllllIIIIIIlIIllllI instanceof EntityPlayer ? EntityUtils.getPing((EntityPlayer)llllllllllllllllllIIIIIIlIIllllI) : 0;
      String llllllllllllllllllIIIIIIlIlIIlll = (Boolean)llllllllllllllllllIIIIIIlIlIIIlI.distanceValue.get() ? String.valueOf((new StringBuilder()).append("§7").append(MathKt.roundToInt(access$getMc$p$s1046033730().thePlayer.getDistanceToEntity((Entity)llllllllllllllllllIIIIIIlIIllllI))).append("m ")) : "";
      short llllllllllllllllllIIIIIIlIIlIlll = (Boolean)llllllllllllllllllIIIIIIlIlIIIlI.pingValue.get() && llllllllllllllllllIIIIIIlIIllllI instanceof EntityPlayer ? String.valueOf((new StringBuilder()).append(llllllllllllllllllIIIIIIlIlIIllI > 200 ? "§c" : (llllllllllllllllllIIIIIIlIlIIllI > 100 ? "§e" : "§a")).append(llllllllllllllllllIIIIIIlIlIIllI).append("ms §7")) : "";
      String llllllllllllllllllIIIIIIlIlIlIIl = (Boolean)llllllllllllllllllIIIIIIlIlIIIlI.healthValue.get() ? String.valueOf((new StringBuilder()).append("§7§c ").append((int)llllllllllllllllllIIIIIIlIIllllI.getHealth()).append(" HP")) : "";
      String llllllllllllllllllIIIIIIlIlIlIlI = llllllllllllllllllIIIIIIlIIllIll ? " §c§lBot" : "";
      String llllllllllllllllllIIIIIIlIlIlIll = String.valueOf((new StringBuilder()).append(llllllllllllllllllIIIIIIlIlIIlll).append(llllllllllllllllllIIIIIIlIIlIlll).append(llllllllllllllllllIIIIIIlIIllIlI).append(llllllllllllllllllIIIIIIlIlIIIII).append(llllllllllllllllllIIIIIIlIlIlIIl).append(llllllllllllllllllIIIIIIlIlIlIlI));
      GL11.glPushMatrix();
      Minecraft var10000 = access$getMc$p$s1046033730();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "mc");
      RenderManager llllllllllllllllllIIIIIIlIlIllII = var10000.getRenderManager();
      Timer llllllllllllllllllIIIIIIlIlIllIl = access$getMc$p$s1046033730().timer;
      GL11.glTranslated(llllllllllllllllllIIIIIIlIIllllI.lastTickPosX + (llllllllllllllllllIIIIIIlIIllllI.posX - llllllllllllllllllIIIIIIlIIllllI.lastTickPosX) * (double)llllllllllllllllllIIIIIIlIlIllIl.renderPartialTicks - llllllllllllllllllIIIIIIlIlIllII.renderPosX, llllllllllllllllllIIIIIIlIIllllI.lastTickPosY + (llllllllllllllllllIIIIIIlIIllllI.posY - llllllllllllllllllIIIIIIlIIllllI.lastTickPosY) * (double)llllllllllllllllllIIIIIIlIlIllIl.renderPartialTicks - llllllllllllllllllIIIIIIlIlIllII.renderPosY + (double)llllllllllllllllllIIIIIIlIIllllI.getEyeHeight() + 0.55D, llllllllllllllllllIIIIIIlIIllllI.lastTickPosZ + (llllllllllllllllllIIIIIIlIIllllI.posZ - llllllllllllllllllIIIIIIlIIllllI.lastTickPosZ) * (double)llllllllllllllllllIIIIIIlIlIllIl.renderPartialTicks - llllllllllllllllllIIIIIIlIlIllII.renderPosZ);
      var10000 = access$getMc$p$s1046033730();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "mc");
      GL11.glRotatef(-var10000.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
      var10000 = access$getMc$p$s1046033730();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "mc");
      GL11.glRotatef(var10000.getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
      String llllllllllllllllllIIIIIIlIIlIIIl = access$getMc$p$s1046033730().thePlayer.getDistanceToEntity((Entity)llllllllllllllllllIIIIIIlIIllllI) / 4.0F;
      if (llllllllllllllllllIIIIIIlIIlIIIl < 1.0F) {
         llllllllllllllllllIIIIIIlIIlIIIl = 1.0F;
      }

      float llllllllllllllllllIIIIIIlIlIllll = llllllllllllllllllIIIIIIlIIlIIIl / 100.0F * ((Number)llllllllllllllllllIIIIIIlIlIIIlI.scaleValue.get()).floatValue();
      GL11.glScalef(-llllllllllllllllllIIIIIIlIlIllll, -llllllllllllllllllIIIIIIlIlIllll, llllllllllllllllllIIIIIIlIlIllll);
      RenderUtils.disableGlCap(2896, 2929);
      RenderUtils.enableGlCap(3042);
      GL11.glBlendFunc(770, 771);
      int llllllllllllllllllIIIIIIlIllIIII = llllllllllllllllllIIIIIIlIIlllII.getStringWidth(llllllllllllllllllIIIIIIlIlIlIll) / 2;
      if ((Boolean)llllllllllllllllllIIIIIIlIlIIIlI.borderValue.get()) {
         RenderUtils.drawBorderedRect((float)(-llllllllllllllllllIIIIIIlIllIIII) - 2.0F, -2.0F, (float)llllllllllllllllllIIIIIIlIllIIII + 4.0F, (float)llllllllllllllllllIIIIIIlIIlllII.FONT_HEIGHT + 2.0F, 2.0F, (new Color(255, 255, 255, 90)).getRGB(), Integer.MIN_VALUE);
      } else {
         RenderUtils.drawRect((float)(-llllllllllllllllllIIIIIIlIllIIII) - 2.0F, -2.0F, (float)llllllllllllllllllIIIIIIlIllIIII + 4.0F, (float)llllllllllllllllllIIIIIIlIIlllII.FONT_HEIGHT + 2.0F, Integer.MIN_VALUE);
      }

      llllllllllllllllllIIIIIIlIIlllII.drawString(llllllllllllllllllIIIIIIlIlIlIll, 1.0F + (float)(-llllllllllllllllllIIIIIIlIllIIII), Intrinsics.areEqual((Object)llllllllllllllllllIIIIIIlIIlllII, (Object)Fonts.minecraftFont) ? 1.0F : 1.5F, 16777215, true);
      boolean var10001 = false;
      if ((Boolean)llllllllllllllllllIIIIIIlIlIIIlI.armorValue.get() && llllllllllllllllllIIIIIIlIIllllI instanceof EntityPlayer) {
         double llllllllllllllllllIIIIIIlIIIlllI = 0;

         for(byte llllllllllllllllllIIIIIIlIIIllIl = 4; llllllllllllllllllIIIIIIlIIIlllI <= llllllllllllllllllIIIIIIlIIIllIl; ++llllllllllllllllllIIIIIIlIIIlllI) {
            if (llllllllllllllllllIIIIIIlIIllllI.getEquipmentInSlot(llllllllllllllllllIIIIIIlIIIlllI) != null) {
               var10000 = access$getMc$p$s1046033730();
               Intrinsics.checkExpressionValueIsNotNull(var10000, "mc");
               var10000.getRenderItem().zLevel = -147.0F;
               var10000 = access$getMc$p$s1046033730();
               Intrinsics.checkExpressionValueIsNotNull(var10000, "mc");
               var10000.getRenderItem().renderItemAndEffectIntoGUI(llllllllllllllllllIIIIIIlIIllllI.getEquipmentInSlot(llllllllllllllllllIIIIIIlIIIlllI), -50 + llllllllllllllllllIIIIIIlIIIlllI * 20, -22);
            }
         }

         GlStateManager.enableAlpha();
         GlStateManager.disableBlend();
         GlStateManager.enableTexture2D();
      }

      RenderUtils.resetCaps();
      GlStateManager.resetColor();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glPopMatrix();
   }

   @EventTarget
   public final void onRender2D(@NotNull Render2DEvent llllllllllllllllllIIIIIlIIIIlIll) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIIIIIlIIIIlIll, "event");
      if (StringsKt.equals((String)llllllllllllllllllIIIIIlIIIIlIlI.modeValue.get(), "Other", true)) {
         GL11.glPushMatrix();
         GL11.glDisable(2929);
         ScaledResolution llllllllllllllllllIIIIIlIIIIllIl = new ScaledResolution(access$getMc$p$s1046033730());
         float llllllllllllllllllIIIIIlIIIIIlll = (double)llllllllllllllllllIIIIIlIIIIllIl.getScaleFactor() / Math.pow((double)llllllllllllllllllIIIIIlIIIIllIl.getScaleFactor(), 2.0D);
         GL11.glScalef((float)llllllllllllllllllIIIIIlIIIIIlll, (float)llllllllllllllllllIIIIIlIIIIIlll, (float)llllllllllllllllllIIIIIlIIIIIlll);
         Iterator llllllllllllllllllIIIIIlIIIIllll = access$getMc$p$s1046033730().theWorld.loadedEntityList.iterator();

         while(true) {
            Entity llllllllllllllllllIIIIIlIIIIIlIl;
            float llllllllllllllllllIIIIIlIIIlIIIl;
            float llllllllllllllllllIIIIIlIIIIIIll;
            float llllllllllllllllllIIIIIlIIIIIIIl;
            boolean llllllllllllllllllIIIIIIllllllll;
            do {
               do {
                  do {
                     do {
                        if (!llllllllllllllllllIIIIIlIIIIllll.hasNext()) {
                           GL11.glEnable(2929);
                           GL11.glPopMatrix();
                           return;
                        }

                        llllllllllllllllllIIIIIlIIIIIlIl = (Entity)llllllllllllllllllIIIIIlIIIIllll.next();
                     } while(llllllllllllllllllIIIIIlIIIIIlIl == null);
                  } while(llllllllllllllllllIIIIIlIIIIIlIl == access$getMc$p$s1046033730().thePlayer);
               } while(!EntityUtils.isSelected(llllllllllllllllllIIIIIlIIIIIlIl, false));

               llllllllllllllllllIIIIIlIIIIlIlI.updatePositions(llllllllllllllllllIIIIIlIIIIIlIl);
               llllllllllllllllllIIIIIlIIIlIIIl = FloatCompanionObject.INSTANCE.getMAX_VALUE();
               llllllllllllllllllIIIIIlIIIIIIll = FloatCompanionObject.INSTANCE.getMIN_VALUE();
               double llllllllllllllllllIIIIIlIIIIIIlI = FloatCompanionObject.INSTANCE.getMIN_VALUE();
               llllllllllllllllllIIIIIlIIIIIIIl = FloatCompanionObject.INSTANCE.getMAX_VALUE();
               Iterator llllllllllllllllllIIIIIlIIIlIlIl = llllllllllllllllllIIIIIlIIIIlIlI.positions.iterator();
               llllllllllllllllllIIIIIIllllllll = false;

               while(llllllllllllllllllIIIIIlIIIlIlIl.hasNext()) {
                  Vec3 var10000 = RenderUtils.WorldToScreen((Vec3)llllllllllllllllllIIIIIlIIIlIlIl.next());
                  Intrinsics.checkExpressionValueIsNotNull(var10000, "RenderUtils.WorldToScreen(iterator2.next())");
                  Vec3 llllllllllllllllllIIIIIlIIllIIll = var10000;
                  if (llllllllllllllllllIIIIIlIIllIIll != null && llllllllllllllllllIIIIIlIIllIIll.zCoord >= 0.0D && llllllllllllllllllIIIIIlIIllIIll.zCoord < 1.0D) {
                     llllllllllllllllllIIIIIlIIIlIIIl = (float)Math.min(llllllllllllllllllIIIIIlIIllIIll.xCoord, (double)llllllllllllllllllIIIIIlIIIlIIIl);
                     llllllllllllllllllIIIIIlIIIIIIll = (float)Math.max(llllllllllllllllllIIIIIlIIllIIll.xCoord, (double)llllllllllllllllllIIIIIlIIIIIIll);
                     llllllllllllllllllIIIIIlIIIIIIlI = (float)Math.max(llllllllllllllllllIIIIIlIIllIIll.yCoord, (double)llllllllllllllllllIIIIIlIIIIIIlI);
                     llllllllllllllllllIIIIIlIIIIIIIl = (float)Math.min(llllllllllllllllllIIIIIlIIllIIll.yCoord, (double)llllllllllllllllllIIIIIlIIIIIIIl);
                     llllllllllllllllllIIIIIIllllllll = true;
                  }
               }
            } while(!llllllllllllllllllIIIIIIllllllll);

            if (!(llllllllllllllllllIIIIIlIIIIIlIl instanceof EntityLivingBase)) {
               return;
            }

            GL11.glPushMatrix();
            long llllllllllllllllllIIIIIIllllllIl = Particles.roundToPlace(((EntityLivingBase)llllllllllllllllllIIIIIlIIIIIlIl).getHealth(), 1);
            String llllllllllllllllllIIIIIlIIIllIIl = "";
            llllllllllllllllllIIIIIlIIIllIIl = AntiBot.isBot((EntityLivingBase)llllllllllllllllllIIIIIlIIIIIlIl) ? "§7" : (llllllllllllllllllIIIIIlIIIIIlIl instanceof EntityPlayer && EntityUtils.isFriend(llllllllllllllllllIIIIIlIIIIIlIl) ? "§9" : (llllllllllllllllllIIIIIlIIIIlIlI.teams.isInYourTeam((EntityLivingBase)llllllllllllllllllIIIIIlIIIIIlIl) ? "§a" : (((EntityLivingBase)llllllllllllllllllIIIIIlIIIIIlIl).isSneaking() ? "§c" : "§r")));
            StringBuilder var35 = (new StringBuilder()).append(llllllllllllllllllIIIIIlIIIllIIl);
            String var10001;
            if ((Boolean)llllllllllllllllllIIIIIlIIIIlIlI.clearNamesValue.get()) {
               var10001 = llllllllllllllllllIIIIIlIIIIIlIl.getName();
            } else {
               IChatComponent var36 = llllllllllllllllllIIIIIlIIIIIlIl.getDisplayName();
               Intrinsics.checkExpressionValueIsNotNull(var36, "entity.getDisplayName()");
               var10001 = var36.getFormattedText();
            }

            String llllllllllllllllllIIIIIlIIIllIlI = String.valueOf(var35.append(var10001));
            String llllllllllllllllllIIIIIlIIIllIll = String.valueOf((new StringBuilder()).append(String.valueOf((int)(llllllllllllllllllIIIIIIllllllIl / (double)((EntityLivingBase)llllllllllllllllllIIIIIlIIIIIlIl).getMaxHealth() * (double)100))).append("%"));
            String llllllllllllllllllIIIIIlIIIlllII = String.valueOf((new StringBuilder()).append("§7").append((int)llllllllllllllllllIIIIIlIIIIIlIl.getDistanceToEntity((Entity)access$getMc$p$s1046033730().thePlayer)).append("m"));
            int llllllllllllllllllIIIIIlIIIlllIl = (new Color(0, 0, 0, 200)).getRGB();
            int llllllllllllllllllIIIIIlIIIllllI = (new Color(35, 35, 35, 180)).getRGB();
            Exception llllllllllllllllllIIIIIIllllIllI = llllllllllllllllllIIIIIlIIIlIIIl + (llllllllllllllllllIIIIIlIIIIIIll - llllllllllllllllllIIIIIlIIIlIIIl);
            float llllllllllllllllllIIIIIlIIlIIIll = 2.0F;
            RenderUtils.drawnewrect(llllllllllllllllllIIIIIIllllIllI - (float)(llllllllllllllllllIIIIIlIIIIlIlI.getStringWidth(llllllllllllllllllIIIIIlIIIllIlI) / 2) - (float)((int)llllllllllllllllllIIIIIlIIlIIIll), llllllllllllllllllIIIIIlIIIIIIIl - (float)llllllllllllllllllIIIIIlIIIIlIlI.getStringHeight() - (float)((int)llllllllllllllllllIIIIIlIIlIIIll), llllllllllllllllllIIIIIIllllIllI + (float)(llllllllllllllllllIIIIIlIIIIlIlI.getStringWidth(llllllllllllllllllIIIIIlIIIllIlI) / 2) + (float)((int)llllllllllllllllllIIIIIlIIlIIIll), llllllllllllllllllIIIIIlIIIIIIIl + (float)((int)llllllllllllllllllIIIIIlIIlIIIll), llllllllllllllllllIIIIIlIIIlllIl);
            llllllllllllllllllIIIIIlIIIIlIlI.drawString(llllllllllllllllllIIIIIlIIIllIlI, llllllllllllllllllIIIIIIllllIllI - (float)(llllllllllllllllllIIIIIlIIIIlIlI.getStringWidth(llllllllllllllllllIIIIIlIIIllIlI) / 2), llllllllllllllllllIIIIIlIIIIIIIl - (float)llllllllllllllllllIIIIIlIIIIlIlI.getStringHeight(), -1, true);
            float llllllllllllllllllIIIIIlIIlIIlII = 4.0F;
            if ((Boolean)llllllllllllllllllIIIIIlIIIIlIlI.healthValue.get()) {
               RenderUtils.drawnewrect(llllllllllllllllllIIIIIIllllIllI + (float)(llllllllllllllllllIIIIIlIIIIlIlI.getStringWidth(llllllllllllllllllIIIIIlIIIllIlI) / 2) + llllllllllllllllllIIIIIlIIlIIlII + llllllllllllllllllIIIIIlIIlIIIll - (float)((int)llllllllllllllllllIIIIIlIIlIIIll), llllllllllllllllllIIIIIlIIIIIIIl - (float)llllllllllllllllllIIIIIlIIIIlIlI.getStringHeight() - (float)((int)llllllllllllllllllIIIIIlIIlIIIll), llllllllllllllllllIIIIIIllllIllI + (float)(llllllllllllllllllIIIIIlIIIIlIlI.getStringWidth(llllllllllllllllllIIIIIlIIIllIlI) / 2) + (float)llllllllllllllllllIIIIIlIIIIlIlI.getStringWidth(llllllllllllllllllIIIIIlIIIllIll) + llllllllllllllllllIIIIIlIIlIIlII + llllllllllllllllllIIIIIlIIlIIIll + (float)((int)llllllllllllllllllIIIIIlIIlIIIll), llllllllllllllllllIIIIIlIIIIIIIl + (float)((int)llllllllllllllllllIIIIIlIIlIIIll), llllllllllllllllllIIIIIlIIIlllIl);
               llllllllllllllllllIIIIIlIIIIlIlI.drawString(llllllllllllllllllIIIIIlIIIllIll, llllllllllllllllllIIIIIIllllIllI + (float)(llllllllllllllllllIIIIIlIIIIlIlI.getStringWidth(llllllllllllllllllIIIIIlIIIllIlI) / 2) + llllllllllllllllllIIIIIlIIlIIlII + llllllllllllllllllIIIIIlIIlIIIll, llllllllllllllllllIIIIIlIIIIIIIl - (float)llllllllllllllllllIIIIIlIIIIlIlI.getStringHeight(), llllllllllllllllllIIIIIlIIIIlIlI.getHealthColor(((EntityLivingBase)llllllllllllllllllIIIIIlIIIIIlIl).getHealth(), ((EntityLivingBase)llllllllllllllllllIIIIIlIIIIIlIl).getMaxHealth()), true);
            }

            if ((Boolean)llllllllllllllllllIIIIIlIIIIlIlI.distanceValue.get()) {
               RenderUtils.drawnewrect(llllllllllllllllllIIIIIIllllIllI - (float)(llllllllllllllllllIIIIIlIIIIlIlI.getStringWidth(llllllllllllllllllIIIIIlIIIllIlI) / 2) - (float)llllllllllllllllllIIIIIlIIIIlIlI.getStringWidth(llllllllllllllllllIIIIIlIIIlllII) - llllllllllllllllllIIIIIlIIlIIlII - llllllllllllllllllIIIIIlIIlIIIll - (float)((int)llllllllllllllllllIIIIIlIIlIIIll), llllllllllllllllllIIIIIlIIIIIIIl - (float)llllllllllllllllllIIIIIlIIIIlIlI.getStringHeight() - (float)((int)llllllllllllllllllIIIIIlIIlIIIll), llllllllllllllllllIIIIIIllllIllI - (float)(llllllllllllllllllIIIIIlIIIIlIlI.getStringWidth(llllllllllllllllllIIIIIlIIIllIlI) / 2) - llllllllllllllllllIIIIIlIIlIIlII - llllllllllllllllllIIIIIlIIlIIIll + (float)((int)llllllllllllllllllIIIIIlIIlIIIll), llllllllllllllllllIIIIIlIIIIIIIl + (float)((int)llllllllllllllllllIIIIIlIIlIIIll), llllllllllllllllllIIIIIlIIIlllIl);
               llllllllllllllllllIIIIIlIIIIlIlI.drawString(llllllllllllllllllIIIIIlIIIlllII, llllllllllllllllllIIIIIIllllIllI - (float)(llllllllllllllllllIIIIIlIIIIlIlI.getStringWidth(llllllllllllllllllIIIIIlIIIllIlI) / 2) - (float)llllllllllllllllllIIIIIlIIIIlIlI.getStringWidth(llllllllllllllllllIIIIIlIIIlllII) - llllllllllllllllllIIIIIlIIlIIlII - llllllllllllllllllIIIIIlIIlIIIll, llllllllllllllllllIIIIIlIIIIIIIl - (float)llllllllllllllllllIIIIIlIIIIlIlI.getStringHeight(), -1, true);
            }

            if ((Boolean)llllllllllllllllllIIIIIlIIIIlIlI.armorValue.get() && llllllllllllllllllIIIIIlIIIIIlIl instanceof EntityPlayer) {
               int llllllllllllllllllIIIIIIllllIIlI = (List)(new ArrayList());
               Exception llllllllllllllllllIIIIIIllllIIIl = 0;

               for(byte llllllllllllllllllIIIIIIllllIIII = 4; llllllllllllllllllIIIIIIllllIIIl <= llllllllllllllllllIIIIIIllllIIII; ++llllllllllllllllllIIIIIIllllIIIl) {
                  if (((EntityLivingBase)llllllllllllllllllIIIIIlIIIIIlIl).getEquipmentInSlot(llllllllllllllllllIIIIIIllllIIIl) == null) {
                  }
               }

               Exception llllllllllllllllllIIIIIIllllIIIl = 4;
               int llllllllllllllllllIIIIIlIIlIlIIl = 0;
               long llllllllllllllllllIIIIIIlllIllll = 2;

               for(Iterator llllllllllllllllllIIIIIIlllIllIl = llllllllllllllllllIIIIIIllllIIlI.iterator(); llllllllllllllllllIIIIIIlllIllIl.hasNext(); llllllllllllllllllIIIIIlIIlIlIIl += 16 * llllllllllllllllllIIIIIIlllIllll + llllllllllllllllllIIIIIIllllIIIl) {
                  ItemStack llllllllllllllllllIIIIIlIIlIllII = (ItemStack)llllllllllllllllllIIIIIIlllIllIl.next();
                  float llllllllllllllllllIIIIIlIIlIlllI = llllllllllllllllllIIIIIIllllIllI - (float)((llllllllllllllllllIIIIIIllllIIlI.size() * 16 * llllllllllllllllllIIIIIIlllIllll + Math.max(llllllllllllllllllIIIIIIllllIIlI.size() - 1, 0) * llllllllllllllllllIIIIIIllllIIIl) / 2);
                  GL11.glPushMatrix();
                  GL11.glScalef((float)llllllllllllllllllIIIIIIlllIllll, (float)llllllllllllllllllIIIIIIlllIllll, (float)llllllllllllllllllIIIIIIlllIllll);
                  RenderUtils.drawnewrect((llllllllllllllllllIIIIIlIIlIlllI + (float)llllllllllllllllllIIIIIlIIlIlIIl) / (float)llllllllllllllllllIIIIIIlllIllll, (llllllllllllllllllIIIIIlIIIIIIIl - (float)llllllllllllllllllIIIIIlIIIIlIlI.getStringHeight() - (float)(16 * llllllllllllllllllIIIIIIlllIllll) - (float)4) / (float)llllllllllllllllllIIIIIIlllIllll, (llllllllllllllllllIIIIIlIIlIlllI + (float)llllllllllllllllllIIIIIlIIlIlIIl) / (float)llllllllllllllllllIIIIIIlllIllll + (float)16, (llllllllllllllllllIIIIIlIIIIIIIl - (float)llllllllllllllllllIIIIIlIIIIlIlI.getStringHeight() - (float)(16 * llllllllllllllllllIIIIIIlllIllll) - (float)4) / (float)llllllllllllllllllIIIIIIlllIllll + (float)16, llllllllllllllllllIIIIIlIIIllllI);
                  RenderUtils.renderItemStack(llllllllllllllllllIIIIIlIIlIllII, (int)((llllllllllllllllllIIIIIlIIlIlllI + (float)llllllllllllllllllIIIIIlIIlIlIIl) / (float)llllllllllllllllllIIIIIIlllIllll), (Integer)(llllllllllllllllllIIIIIlIIIIIIIl - (float)llllllllllllllllllIIIIIlIIIIlIlI.getStringHeight() - (float)(16 * llllllllllllllllllIIIIIIlllIllll) - (float)4) / (float)llllllllllllllllllIIIIIIlllIllll);
                  GL11.glPopMatrix();
                  double llllllllllllllllllIIIIIIlllIlIll = llllllllllllllllllIIIIIlIIlIllII.getMaxDamage() - llllllllllllllllllIIIIIlIIlIllII.getItemDamage();
                  access$getMc$p$s1046033730().fontRendererObj.drawStringWithShadow(String.valueOf(llllllllllllllllllIIIIIIlllIlIll), llllllllllllllllllIIIIIlIIlIlllI + (float)llllllllllllllllllIIIIIlIIlIlIIl + (float)(32 - access$getMc$p$s1046033730().fontRendererObj.getStringWidth(String.valueOf(llllllllllllllllllIIIIIIlllIlIll))), (float)(Integer)llllllllllllllllllIIIIIlIIIIIIIl - (float)llllllllllllllllllIIIIIlIIIIlIlI.getStringHeight() - (float)40, -1);
                  boolean var37 = false;
               }
            }

            GL11.glPopMatrix();
         }
      }
   }

   public final int getHealthColor(float llllllllllllllllllIIIIIIIlllIIlI, float llllllllllllllllllIIIIIIIlllIIIl) {
      float llllllllllllllllllIIIIIIIlllIllI = llllllllllllllllllIIIIIIIlllIIlI / llllllllllllllllllIIIIIIIlllIIIl;
      return llllllllllllllllllIIIIIIIlllIllI >= 0.75F ? (new Color(0, 255, 0)).getRGB() : ((double)llllllllllllllllllIIIIIIIlllIllI < 0.75D && (double)llllllllllllllllllIIIIIIIlllIllI >= 0.25D ? (new Color(255, 255, 0)).getRGB() : (new Color(255, 0, 0)).getRGB());
   }

   private final void updatePositions(Entity llllllllllllllllllIIIIIIllIllIll) {
      llllllllllllllllllIIIIIIllIllIlI.positions.clear();
      String llllllllllllllllllIIIIIIllIllIII = RenderUtils.getEntityRenderPosition(llllllllllllllllllIIIIIIllIllIll);
      double llllllllllllllllllIIIIIIllIllllI = llllllllllllllllllIIIIIIllIllIII.xCoord - llllllllllllllllllIIIIIIllIllIll.posX;
      int llllllllllllllllllIIIIIIllIlIllI = llllllllllllllllllIIIIIIllIllIII.yCoord - llllllllllllllllllIIIIIIllIllIll.posY;
      boolean llllllllllllllllllIIIIIIllIlIlIl = llllllllllllllllllIIIIIIllIllIII.zCoord - llllllllllllllllllIIIIIIllIllIll.posZ;
      Exception llllllllllllllllllIIIIIIllIlIlII = (double)llllllllllllllllllIIIIIIllIllIll.height + 0.3D;
      short llllllllllllllllllIIIIIIllIlIIll = new AxisAlignedBB(llllllllllllllllllIIIIIIllIllIll.posX + llllllllllllllllllIIIIIIllIllllI, llllllllllllllllllIIIIIIllIllIll.posY + llllllllllllllllllIIIIIIllIlIllI, llllllllllllllllllIIIIIIllIllIll.posZ + llllllllllllllllllIIIIIIllIlIlIl, llllllllllllllllllIIIIIIllIllIll.posX + llllllllllllllllllIIIIIIllIllllI, llllllllllllllllllIIIIIIllIllIll.posY + llllllllllllllllllIIIIIIllIlIlII + llllllllllllllllllIIIIIIllIlIllI, llllllllllllllllllIIIIIIllIllIll.posZ + llllllllllllllllllIIIIIIllIlIlIl);
      llllllllllllllllllIIIIIIllIllIlI.positions.add(new Vec3(llllllllllllllllllIIIIIIllIlIIll.minX, llllllllllllllllllIIIIIIllIlIIll.minY, llllllllllllllllllIIIIIIllIlIIll.minZ));
      boolean var10001 = false;
      llllllllllllllllllIIIIIIllIllIlI.positions.add(new Vec3(llllllllllllllllllIIIIIIllIlIIll.minX, llllllllllllllllllIIIIIIllIlIIll.minY, llllllllllllllllllIIIIIIllIlIIll.maxZ));
      var10001 = false;
      llllllllllllllllllIIIIIIllIllIlI.positions.add(new Vec3(llllllllllllllllllIIIIIIllIlIIll.minX, llllllllllllllllllIIIIIIllIlIIll.maxY, llllllllllllllllllIIIIIIllIlIIll.minZ));
      var10001 = false;
      llllllllllllllllllIIIIIIllIllIlI.positions.add(new Vec3(llllllllllllllllllIIIIIIllIlIIll.minX, llllllllllllllllllIIIIIIllIlIIll.maxY, llllllllllllllllllIIIIIIllIlIIll.maxZ));
      var10001 = false;
      llllllllllllllllllIIIIIIllIllIlI.positions.add(new Vec3(llllllllllllllllllIIIIIIllIlIIll.maxX, llllllllllllllllllIIIIIIllIlIIll.minY, llllllllllllllllllIIIIIIllIlIIll.minZ));
      var10001 = false;
      llllllllllllllllllIIIIIIllIllIlI.positions.add(new Vec3(llllllllllllllllllIIIIIIllIlIIll.maxX, llllllllllllllllllIIIIIIllIlIIll.minY, llllllllllllllllllIIIIIIllIlIIll.maxZ));
      var10001 = false;
      llllllllllllllllllIIIIIIllIllIlI.positions.add(new Vec3(llllllllllllllllllIIIIIIllIlIIll.maxX, llllllllllllllllllIIIIIIllIlIIll.maxY, llllllllllllllllllIIIIIIllIlIIll.minZ));
      var10001 = false;
      llllllllllllllllllIIIIIIllIllIlI.positions.add(new Vec3(llllllllllllllllllIIIIIIllIlIIll.maxX, llllllllllllllllllIIIIIIllIlIIll.maxY, llllllllllllllllllIIIIIIllIlIIll.maxZ));
      var10001 = false;
   }

   public final int getStringWidth(@Nullable String llllllllllllllllllIIIIIIIllllIll) {
      return access$getMc$p$s1046033730().fontRendererObj.getStringWidth(llllllllllllllllllIIIIIIIllllIll);
   }

   public NameTags() {
      GameFontRenderer var10004 = Fonts.font40;
      Intrinsics.checkExpressionValueIsNotNull(var10004, "Fonts.font40");
      llllllllllllllllllIIIIIIIllIlllI.fontValue = new FontValue("Font", (FontRenderer)var10004);
      llllllllllllllllllIIIIIIIllIlllI.borderValue = new BoolValue("Border", true);
      llllllllllllllllllIIIIIIIllIlllI.scaleValue = new FloatValue("Scale", 1.0F, 1.0F, 4.0F);
      llllllllllllllllllIIIIIIIllIlllI.positions = (List)(new ArrayList());
      Module var10001 = LiquidBounce.INSTANCE.getModuleManager().get(Teams.class);
      if (var10001 == null) {
         throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.misc.Teams");
      } else {
         llllllllllllllllllIIIIIIIllIlllI.teams = (Teams)var10001;
      }
   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   @EventTarget
   public final void onRender3D(@NotNull Render3DEvent llllllllllllllllllIIIIIlllIIlIIl) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIIIIIlllIIlIIl, "event");
      if (StringsKt.equals((String)llllllllllllllllllIIIIIlllIIlIlI.modeValue.get(), "liquidbounce", true)) {
         Iterator llllllllllllllllllIIIIIlllIIIlII = access$getMc$p$s1046033730().theWorld.loadedEntityList.iterator();

         label51:
         while(true) {
            while(true) {
               Entity llllllllllllllllllIIIIIlllIIllIl;
               do {
                  if (!llllllllllllllllllIIIIIlllIIIlII.hasNext()) {
                     break label51;
                  }

                  llllllllllllllllllIIIIIlllIIllIl = (Entity)llllllllllllllllllIIIIIlllIIIlII.next();
               } while(!EntityUtils.isSelected(llllllllllllllllllIIIIIlllIIllIl, false));

               if (llllllllllllllllllIIIIIlllIIllIl == null) {
                  throw new TypeCastException("null cannot be cast to non-null type net.minecraft.entity.EntityLivingBase");
               }

               EntityLivingBase var10001 = (EntityLivingBase)llllllllllllllllllIIIIIlllIIllIl;
               IChatComponent var10002;
               String var7;
               if ((Boolean)llllllllllllllllllIIIIIlllIIlIlI.clearNamesValue.get()) {
                  var10002 = llllllllllllllllllIIIIIlllIIllIl.getDisplayName();
                  Intrinsics.checkExpressionValueIsNotNull(var10002, "entity.getDisplayName()");
                  var7 = ColorUtils.stripColor(var10002.getUnformattedText());
                  if (var7 == null) {
                     boolean var10003 = false;
                     boolean var8 = false;
                     boolean var6 = false;
                     continue;
                  }
               } else {
                  var10002 = llllllllllllllllllIIIIIlllIIllIl.getDisplayName();
                  Intrinsics.checkExpressionValueIsNotNull(var10002, "entity.getDisplayName()");
                  var7 = var10002.getUnformattedText();
               }

               Intrinsics.checkExpressionValueIsNotNull(var7, "if (clearNamesValue.get(…layName().unformattedText");
               llllllllllllllllllIIIIIlllIIlIlI.renderNameTag(var10001, var7);
            }
         }
      }

      if (StringsKt.equals((String)llllllllllllllllllIIIIIlllIIlIlI.modeValue.get(), "Other", true)) {
         Iterator llllllllllllllllllIIIIIlllIIlIll = access$getMc$p$s1046033730().theWorld.loadedEntityList.iterator();

         while(llllllllllllllllllIIIIIlllIIlIll.hasNext()) {
            boolean llllllllllllllllllIIIIIlllIIIlII = (Entity)llllllllllllllllllIIIIIlllIIlIll.next();
            if (llllllllllllllllllIIIIIlllIIIlII != null && llllllllllllllllllIIIIIlllIIIlII != access$getMc$p$s1046033730().thePlayer && EntityUtils.isSelected(llllllllllllllllllIIIIIlllIIIlII, false)) {
               RenderUtils.updateView();
            }
         }
      }

   }

   public final int getStringHeight() {
      return access$getMc$p$s1046033730().fontRendererObj.FONT_HEIGHT;
   }

   public final void drawString(@Nullable String llllllllllllllllllIIIIIIlIIIIlll, float llllllllllllllllllIIIIIIlIIIIllI, float llllllllllllllllllIIIIIIlIIIIIII, int llllllllllllllllllIIIIIIlIIIIlII, boolean llllllllllllllllllIIIIIIlIIIIIll) {
      access$getMc$p$s1046033730().fontRendererObj.drawString(llllllllllllllllllIIIIIIlIIIIlll, llllllllllllllllllIIIIIIlIIIIllI, llllllllllllllllllIIIIIIlIIIIIII, llllllllllllllllllIIIIIIlIIIIlII, false);
      boolean var10001 = false;
   }

   @NotNull
   public final Teams getTeams() {
      return llllllllllllllllllIIIIIlllIllIlI.teams;
   }
}
