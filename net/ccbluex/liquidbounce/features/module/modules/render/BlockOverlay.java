//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.ui.font.GameFontRenderer;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0007J\u0010\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0015H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0013\u0010\t\u001a\u0004\u0018\u00010\n8F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0016"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/render/BlockOverlay;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "colorBlueValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "colorGreenValue", "colorRainbow", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "colorRedValue", "currentBlock", "Lnet/minecraft/util/BlockPos;", "getCurrentBlock", "()Lnet/minecraft/util/BlockPos;", "infoValue", "getInfoValue", "()Lnet/ccbluex/liquidbounce/value/BoolValue;", "onRender2D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "onRender3D", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "LiquidSense"}
)
@ModuleInfo(
   name = "BlockOverlay",
   description = "Allows you to change the design of the block overlay.",
   category = ModuleCategory.RENDER
)
public final class BlockOverlay extends Module {
   // $FF: synthetic field
   @NotNull
   private final BoolValue infoValue = new BoolValue("Info", false);
   // $FF: synthetic field
   private final IntegerValue colorGreenValue = new IntegerValue("G", 117, 0, 255);
   // $FF: synthetic field
   private final IntegerValue colorRedValue = new IntegerValue("R", 68, 0, 255);
   // $FF: synthetic field
   private final IntegerValue colorBlueValue = new IntegerValue("B", 255, 0, 255);
   // $FF: synthetic field
   private final BoolValue colorRainbow = new BoolValue("Rainbow", false);

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   @EventTarget
   public final void onRender2D(@NotNull Render2DEvent lIIIlllIIlIlIlI) {
      Intrinsics.checkParameterIsNotNull(lIIIlllIIlIlIlI, "event");
      if ((Boolean)lIIIlllIIlIlIll.infoValue.get()) {
         BlockPos var10000 = lIIIlllIIlIlIll.getCurrentBlock();
         boolean var10;
         if (var10000 == null) {
            var10 = false;
            return;
         }

         Exception lIIIlllIIlIIlll = var10000;
         Block var7 = BlockUtils.getBlock(lIIIlllIIlIIlll);
         if (var7 == null) {
            var10 = false;
            return;
         }

         Block lIIIlllIIlIllIl = var7;
         String lIIIlllIIlIlllI = String.valueOf((new StringBuilder()).append(lIIIlllIIlIllIl.getLocalizedName()).append(" §7ID: ").append(Block.getIdFromBlock(lIIIlllIIlIllIl)));
         ScaledResolution lIIIlllIIlIllll = new ScaledResolution(access$getMc$p$s1046033730());
         float var8 = (float)(lIIIlllIIlIllll.getScaledWidth() / 2) - 2.0F;
         float var10001 = (float)(lIIIlllIIlIllll.getScaledHeight() / 2) + 5.0F;
         float var10002 = (float)(lIIIlllIIlIllll.getScaledWidth() / 2 + Fonts.font40.getStringWidth(lIIIlllIIlIlllI)) + 2.0F;
         float var10003 = (float)(lIIIlllIIlIllll.getScaledHeight() / 2) + 16.0F;
         Color var10005 = Color.BLACK;
         Intrinsics.checkExpressionValueIsNotNull(var10005, "Color.BLACK");
         int var6 = var10005.getRGB();
         Color var10006 = Color.BLACK;
         Intrinsics.checkExpressionValueIsNotNull(var10006, "Color.BLACK");
         RenderUtils.drawBorderedRect(var8, var10001, var10002, var10003, 3.0F, var6, var10006.getRGB());
         GlStateManager.resetColor();
         GameFontRenderer var9 = Fonts.font40;
         int var11 = lIIIlllIIlIllll.getScaledWidth() / 2;
         int var12 = lIIIlllIIlIllll.getScaledHeight() / 2 + 7;
         Color var10004 = Color.WHITE;
         Intrinsics.checkExpressionValueIsNotNull(var10004, "Color.WHITE");
         var9.drawString(lIIIlllIIlIlllI, var11, var12, var10004.getRGB());
         var10 = false;
      }

   }

   @NotNull
   public final BoolValue getInfoValue() {
      return lIIIlllIlIllIII.infoValue;
   }

   @EventTarget
   public final void onRender3D(@NotNull Render3DEvent lIIIlllIlIIIIII) {
      Intrinsics.checkParameterIsNotNull(lIIIlllIlIIIIII, "event");
      BlockPos var10000 = lIIIlllIIllllll.getCurrentBlock();
      boolean var10001;
      if (var10000 != null) {
         BlockPos lIIIlllIlIIIIlI = var10000;
         IBlockState var13 = access$getMc$p$s1046033730().theWorld.getBlockState(lIIIlllIlIIIIlI);
         Intrinsics.checkExpressionValueIsNotNull(var13, "mc.theWorld.getBlockState(blockPos)");
         Block var14 = var13.getBlock();
         if (var14 != null) {
            Block lIIIlllIlIIIIll = var14;
            float lIIIlllIlIIIlII = lIIIlllIlIIIIII.getPartialTicks();
            Color lIIIlllIlIIIlIl = (Boolean)lIIIlllIIllllll.colorRainbow.get() ? ColorUtils.rainbow(0.4F) : new Color(((Number)lIIIlllIIllllll.colorRedValue.get()).intValue(), ((Number)lIIIlllIIllllll.colorGreenValue.get()).intValue(), ((Number)lIIIlllIIllllll.colorBlueValue.get()).intValue(), (int)102.0F);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            RenderUtils.glColor(lIIIlllIlIIIlIl);
            GL11.glLineWidth(2.0F);
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask(false);
            lIIIlllIlIIIIll.setBlockBoundsBasedOnState((IBlockAccess)access$getMc$p$s1046033730().theWorld, lIIIlllIlIIIIlI);
            double lIIIlllIlIIIllI = access$getMc$p$s1046033730().thePlayer.lastTickPosX + (access$getMc$p$s1046033730().thePlayer.posX - access$getMc$p$s1046033730().thePlayer.lastTickPosX) * (double)lIIIlllIlIIIlII;
            double lIIIlllIlIIIlll = access$getMc$p$s1046033730().thePlayer.lastTickPosY + (access$getMc$p$s1046033730().thePlayer.posY - access$getMc$p$s1046033730().thePlayer.lastTickPosY) * (double)lIIIlllIlIIIlII;
            double lIIIlllIlIIlIII = access$getMc$p$s1046033730().thePlayer.lastTickPosZ + (access$getMc$p$s1046033730().thePlayer.posZ - access$getMc$p$s1046033730().thePlayer.lastTickPosZ) * (double)lIIIlllIlIIIlII;
            byte lIIIlllIIllIllI = lIIIlllIlIIIIll.getSelectedBoundingBox((World)access$getMc$p$s1046033730().theWorld, lIIIlllIlIIIIlI).expand(0.0020000000949949026D, 0.0020000000949949026D, 0.0020000000949949026D).offset(-lIIIlllIlIIIllI, -lIIIlllIlIIIlll, -lIIIlllIlIIlIII);
            RenderGlobal.drawSelectionBoundingBox(lIIIlllIIllIllI);
            RenderUtils.drawFilledBox(lIIIlllIIllIllI);
            GlStateManager.depthMask(true);
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
            GlStateManager.resetColor();
         } else {
            var10001 = false;
         }
      } else {
         var10001 = false;
      }
   }

   @Nullable
   public final BlockPos getCurrentBlock() {
      MovingObjectPosition var10000 = access$getMc$p$s1046033730().objectMouseOver;
      if (var10000 != null) {
         BlockPos var2 = var10000.getBlockPos();
         if (var2 != null) {
            BlockPos lIIIlllIlIlIllI = var2;
            if (BlockUtils.canBeClicked(lIIIlllIlIlIllI)) {
               WorldClient var3 = access$getMc$p$s1046033730().theWorld;
               Intrinsics.checkExpressionValueIsNotNull(var3, "mc.theWorld");
               if (var3.getWorldBorder().contains(lIIIlllIlIlIllI)) {
                  return lIIIlllIlIlIllI;
               }
            }

            return null;
         }
      }

      boolean var10001 = false;
      return null;
   }
}
