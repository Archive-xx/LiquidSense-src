//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.ui.client.clickgui.style.styles.BlackStyle;
import net.ccbluex.liquidbounce.ui.client.clickgui.style.styles.LiquidBounceStyle;
import net.ccbluex.liquidbounce.ui.client.clickgui.style.styles.NullStyle;
import net.ccbluex.liquidbounce.ui.client.clickgui.style.styles.SlowlyStyle;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;

@ModuleInfo(
   name = "ClickGUI",
   description = "Opens the ClickGUI.",
   category = ModuleCategory.RENDER,
   keyBind = 54,
   canEnable = false
)
public class ClickGUI extends Module {
   // $FF: synthetic field
   public final FloatValue scaleValue;
   // $FF: synthetic field
   private static final IntegerValue colorGreenValue = new IntegerValue("G", 160, 0, 255);
   // $FF: synthetic field
   private static final IntegerValue colorBlueValue = new IntegerValue("B", 255, 0, 255);
   // $FF: synthetic field
   private final ListValue styleValue;
   // $FF: synthetic field
   private static final IntegerValue colorRedValue = new IntegerValue("R", 0, 0, 255);
   // $FF: synthetic field
   private static final BoolValue colorRainbow = new BoolValue("Rainbow", false);
   // $FF: synthetic field
   public final IntegerValue maxElementsValue;

   public void onEnable() {
      lllllllllllllllllllIIllllIIIllIl.updateStyle();
      mc.displayGuiScreen(LiquidBounce.clickGui);
   }

   private void updateStyle() {
      Exception lllllllllllllllllllIIllllIIIIllI = ((String)lllllllllllllllllllIIllllIIIIlll.styleValue.get()).toLowerCase();
      short lllllllllllllllllllIIllllIIIIlIl = -1;
      switch(lllllllllllllllllllIIllllIIIIllI.hashCode()) {
      case -899450034:
         if (lllllllllllllllllllIIllllIIIIllI.equals("slowly")) {
            lllllllllllllllllllIIllllIIIIlIl = 2;
         }
         break;
      case -615955772:
         if (lllllllllllllllllllIIllllIIIIllI.equals("liquidbounce")) {
            lllllllllllllllllllIIllllIIIIlIl = 0;
         }
         break;
      case 3392903:
         if (lllllllllllllllllllIIllllIIIIllI.equals("null")) {
            lllllllllllllllllllIIllllIIIIlIl = 1;
         }
         break;
      case 93818879:
         if (lllllllllllllllllllIIllllIIIIllI.equals("black")) {
            lllllllllllllllllllIIllllIIIIlIl = 3;
         }
      }

      switch(lllllllllllllllllllIIllllIIIIlIl) {
      case 0:
         LiquidBounce.clickGui.style = new LiquidBounceStyle();
         break;
      case 1:
         LiquidBounce.clickGui.style = new NullStyle();
         break;
      case 2:
         LiquidBounce.clickGui.style = new SlowlyStyle();
         break;
      case 3:
         LiquidBounce.clickGui.style = new BlackStyle();
      }

   }

   public ClickGUI() {
      lllllllllllllllllllIIllllIIlIIII.styleValue = new ListValue("Style", new String[]{"LiquidBounce", "Null", "Slowly", "Black"}, "Slowly") {
         protected void onChanged(String lIIIlIlIlIIllll, String lIIIlIlIlIIlllI) {
            lllllllllllllllllllIIllllIIlIIII.updateStyle();
         }
      };
      lllllllllllllllllllIIllllIIlIIII.scaleValue = new FloatValue("Scale", 0.78F, 0.5F, 2.0F);
      lllllllllllllllllllIIllllIIlIIII.maxElementsValue = new IntegerValue("MaxElements", 15, 1, 20);
   }

   public static Color generateColor() {
      return (Boolean)colorRainbow.get() ? ColorUtils.rainbow() : new Color((Integer)colorRedValue.get(), (Integer)colorGreenValue.get(), (Integer)colorBlueValue.get());
   }
}
