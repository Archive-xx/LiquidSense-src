//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.util.BlockPos;

@ModuleInfo(
   name = "ProphuntESP",
   description = "Allows you to see disguised players in PropHunt.",
   category = ModuleCategory.RENDER
)
public class ProphuntESP extends Module {
   // $FF: synthetic field
   private final BoolValue colorRainbow = new BoolValue("Rainbow", false);
   // $FF: synthetic field
   private final IntegerValue colorGreenValue = new IntegerValue("G", 90, 0, 255);
   // $FF: synthetic field
   public final Map<BlockPos, Long> blocks = new HashMap();
   // $FF: synthetic field
   private final IntegerValue colorRedValue = new IntegerValue("R", 0, 0, 255);
   // $FF: synthetic field
   private final IntegerValue colorBlueValue = new IntegerValue("B", 255, 0, 255);

   public void onDisable() {
      synchronized(llllllllllllllllllIllIllIlIllllI.blocks) {
         llllllllllllllllllIllIllIlIllllI.blocks.clear();
      }
   }

   @EventTarget
   public void onRender3D(Render3DEvent llllllllllllllllllIllIllIlIlIIII) {
      Color llllllllllllllllllIllIllIlIIllll = (Boolean)llllllllllllllllllIllIllIlIlIIIl.colorRainbow.get() ? ColorUtils.rainbow() : new Color((Integer)llllllllllllllllllIllIllIlIlIIIl.colorRedValue.get(), (Integer)llllllllllllllllllIllIllIlIlIIIl.colorGreenValue.get(), (Integer)llllllllllllllllllIllIllIlIlIIIl.colorBlueValue.get());
      Iterator llllllllllllllllllIllIllIlIIllII = mc.theWorld.loadedEntityList.iterator();

      while(llllllllllllllllllIllIllIlIIllII.hasNext()) {
         double llllllllllllllllllIllIllIlIIlIll = (Entity)llllllllllllllllllIllIllIlIIllII.next();
         if (llllllllllllllllllIllIllIlIIlIll instanceof EntityFallingBlock) {
            RenderUtils.drawEntityBox(llllllllllllllllllIllIllIlIIlIll, llllllllllllllllllIllIllIlIIllll, true);
         }
      }

      synchronized(llllllllllllllllllIllIllIlIlIIIl.blocks) {
         Iterator llllllllllllllllllIllIllIlIlIIlI = llllllllllllllllllIllIllIlIlIIIl.blocks.entrySet().iterator();

         while(llllllllllllllllllIllIllIlIlIIlI.hasNext()) {
            Entry<BlockPos, Long> llllllllllllllllllIllIllIlIIlIlI = (Entry)llllllllllllllllllIllIllIlIlIIlI.next();
            if (System.currentTimeMillis() - (Long)llllllllllllllllllIllIllIlIIlIlI.getValue() > 2000L) {
               llllllllllllllllllIllIllIlIlIIlI.remove();
            } else {
               RenderUtils.drawBlockBox((BlockPos)llllllllllllllllllIllIllIlIIlIlI.getKey(), llllllllllllllllllIllIllIlIIllll, true);
            }
         }

      }
   }
}
