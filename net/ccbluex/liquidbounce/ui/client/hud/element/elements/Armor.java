//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.ui.client.hud.element.elements;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@ElementInfo(
   name = "Armor"
)
@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B-\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\b\u0010\f\u001a\u00020\rH\u0016R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Armor;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "x", "", "y", "scale", "", "side", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side;", "(DDFLnet/ccbluex/liquidbounce/ui/client/hud/element/Side;)V", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "LiquidSense"}
)
public final class Armor extends Element {
   // $FF: synthetic field
   private final ListValue modeValue;

   public Armor() {
      this(0.0D, 0.0D, 0.0F, (Side)null, 15, (DefaultConstructorMarker)null);
   }

   @NotNull
   public Border drawElement() {
      PlayerControllerMP var10000 = access$getMc$p$s1046033730().playerController;
      Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.playerController");
      if (var10000.isNotCreative()) {
         GL11.glPushMatrix();
         Minecraft var10 = access$getMc$p$s1046033730();
         Intrinsics.checkExpressionValueIsNotNull(var10, "mc");
         String llIIIllIlIIIllI = var10.getRenderItem();
         boolean llIIIllIlIIlIlI = access$getMc$p$s1046033730().thePlayer.isInsideOfMaterial(Material.water);
         long llIIIllIlIIIlII = 1;
         int llIIIllIlIIllII = llIIIllIlIIlIlI ? -10 : 0;
         boolean llIIIllIlIIIIlI = (String)llIIIllIlIIlIII.modeValue.get();
         int llIIIllIlIIlllI = 3;

         for(boolean llIIIllIlIIIIII = false; llIIIllIlIIlllI >= 0; --llIIIllIlIIlllI) {
            ItemStack var11 = access$getMc$p$s1046033730().thePlayer.inventory.armorInventory[llIIIllIlIIlllI];
            if (var11 != null) {
               long llIIIllIIllllll = var11;
               llIIIllIlIIIllI.renderItemIntoGUI(llIIIllIIllllll, llIIIllIlIIIlII, llIIIllIlIIllII);
               llIIIllIlIIIllI.renderItemOverlays(access$getMc$p$s1046033730().fontRendererObj, llIIIllIIllllll, llIIIllIlIIIlII, llIIIllIlIIllII);
               if (StringsKt.equals(llIIIllIlIIIIlI, "Horizontal", true)) {
                  llIIIllIlIIIlII += 18;
               } else if (StringsKt.equals(llIIIllIlIIIIlI, "Vertical", true)) {
                  llIIIllIlIIllII += 18;
               }
            } else {
               boolean var10001 = false;
            }
         }

         GlStateManager.enableAlpha();
         GlStateManager.disableBlend();
         GlStateManager.disableLighting();
         GlStateManager.disableCull();
         GL11.glPopMatrix();
      }

      return StringsKt.equals((String)llIIIllIlIIlIII.modeValue.get(), "Horizontal", true) ? new Border(0.0F, 0.0F, 72.0F, 17.0F) : new Border(0.0F, 0.0F, 18.0F, 72.0F);
   }

   public Armor(double llIIIllIIlllIII, double llIIIllIIllIlll, float llIIIllIIllIllI, @NotNull Side llIIIllIIllIlIl) {
      Intrinsics.checkParameterIsNotNull(llIIIllIIllIlIl, "side");
      super(llIIIllIIlllIII, llIIIllIIllIlll, llIIIllIIllIllI, llIIIllIIllIlIl);
      llIIIllIIllIlII.modeValue = new ListValue("Alignment", new String[]{"Horizontal", "Vertical"}, "Horizontal");
   }

   // $FF: synthetic method
   public Armor(double var1, double var3, float var5, Side var6, int llIIIllIIlIIlII, DefaultConstructorMarker var8) {
      if ((llIIIllIIlIIlII & 1) != 0) {
         var1 = -8.0D;
      }

      if ((llIIIllIIlIIlII & 2) != 0) {
         var3 = 57.0D;
      }

      if ((llIIIllIIlIIlII & 4) != 0) {
         var5 = 1.0F;
      }

      if ((llIIIllIIlIIlII & 8) != 0) {
         var6 = new Side(Side.Horizontal.MIDDLE, Side.Vertical.DOWN);
      }

      this(var1, var3, var5, var6);
   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }
}
