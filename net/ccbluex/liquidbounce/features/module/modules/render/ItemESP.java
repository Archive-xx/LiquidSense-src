//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import java.util.Iterator;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.shader.shaders.OutlineShader;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.projectile.EntityArrow;

@ModuleInfo(
   name = "ItemESP",
   description = "Allows you to see items through walls.",
   category = ModuleCategory.RENDER
)
public class ItemESP extends Module {
   // $FF: synthetic field
   private final IntegerValue colorGreenValue = new IntegerValue("G", 255, 0, 255);
   // $FF: synthetic field
   private final ListValue modeValue = new ListValue("Mode", new String[]{"Box", "ShaderOutline"}, "Box");
   // $FF: synthetic field
   private final BoolValue colorRainbow = new BoolValue("Rainbow", true);
   // $FF: synthetic field
   private final IntegerValue colorBlueValue = new IntegerValue("B", 0, 0, 255);
   // $FF: synthetic field
   private final IntegerValue colorRedValue = new IntegerValue("R", 0, 0, 255);

   @EventTarget
   public void onRender2D(Render2DEvent lllllllllllllllllIllIlIIllllIlIl) {
      if (((String)lllllllllllllllllIllIlIIllllIllI.modeValue.get()).equalsIgnoreCase("ShaderOutline")) {
         OutlineShader.OUTLINE_SHADER.startDraw(lllllllllllllllllIllIlIIllllIlIl.getPartialTicks());

         try {
            Iterator lllllllllllllllllIllIlIIllllIlII = mc.theWorld.loadedEntityList.iterator();

            label32:
            while(true) {
               Entity lllllllllllllllllIllIlIIlllllIlI;
               do {
                  if (!lllllllllllllllllIllIlIIllllIlII.hasNext()) {
                     break label32;
                  }

                  lllllllllllllllllIllIlIIlllllIlI = (Entity)lllllllllllllllllIllIlIIllllIlII.next();
               } while(!(lllllllllllllllllIllIlIIlllllIlI instanceof EntityItem) && !(lllllllllllllllllIllIlIIlllllIlI instanceof EntityArrow));

               mc.getRenderManager().renderEntityStatic(lllllllllllllllllIllIlIIlllllIlI, lllllllllllllllllIllIlIIllllIlIl.getPartialTicks(), true);
               boolean var10001 = false;
            }
         } catch (Exception var4) {
            ClientUtils.getLogger().error("An error occurred while rendering all item entities for shader esp", var4);
         }

         OutlineShader.OUTLINE_SHADER.stopDraw((Boolean)lllllllllllllllllIllIlIIllllIllI.colorRainbow.get() ? ColorUtils.rainbow() : new Color((Integer)lllllllllllllllllIllIlIIllllIllI.colorRedValue.get(), (Integer)lllllllllllllllllIllIlIIllllIllI.colorGreenValue.get(), (Integer)lllllllllllllllllIllIlIIllllIllI.colorBlueValue.get()), 1.0F, 1.0F);
      }

   }

   @EventTarget
   public void onRender3D(Render3DEvent lllllllllllllllllIllIlIlIIIIIIll) {
      if (((String)lllllllllllllllllIllIlIlIIIIIIlI.modeValue.get()).equalsIgnoreCase("Box")) {
         Color lllllllllllllllllIllIlIlIIIIIlIl = (Boolean)lllllllllllllllllIllIlIlIIIIIIlI.colorRainbow.get() ? ColorUtils.rainbow() : new Color((Integer)lllllllllllllllllIllIlIlIIIIIIlI.colorRedValue.get(), (Integer)lllllllllllllllllIllIlIlIIIIIIlI.colorGreenValue.get(), (Integer)lllllllllllllllllIllIlIlIIIIIIlI.colorBlueValue.get());
         Iterator lllllllllllllllllIllIlIlIIIIIIII = mc.theWorld.loadedEntityList.iterator();

         while(true) {
            Entity lllllllllllllllllIllIlIIllllllll;
            do {
               if (!lllllllllllllllllIllIlIlIIIIIIII.hasNext()) {
                  return;
               }

               lllllllllllllllllIllIlIIllllllll = (Entity)lllllllllllllllllIllIlIlIIIIIIII.next();
            } while(!(lllllllllllllllllIllIlIIllllllll instanceof EntityItem) && !(lllllllllllllllllIllIlIIllllllll instanceof EntityArrow));

            RenderUtils.drawEntityBox(lllllllllllllllllIllIlIIllllllll, lllllllllllllllllIllIlIlIIIIIlIl, true);
         }
      }
   }
}
