//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import java.util.Iterator;
import java.util.LinkedList;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import org.lwjgl.opengl.GL11;

@ModuleInfo(
   name = "Breadcrumbs",
   description = "Leaves a trail behind you.",
   category = ModuleCategory.RENDER
)
public class Breadcrumbs extends Module {
   // $FF: synthetic field
   private final LinkedList<double[]> positions = new LinkedList();
   // $FF: synthetic field
   public final IntegerValue colorGreenValue = new IntegerValue("G", 179, 0, 255);
   // $FF: synthetic field
   public final IntegerValue colorBlueValue = new IntegerValue("B", 72, 0, 255);
   // $FF: synthetic field
   public final BoolValue colorRainbow = new BoolValue("Rainbow", false);
   // $FF: synthetic field
   public final IntegerValue colorRedValue = new IntegerValue("R", 255, 0, 255);

   public void onEnable() {
      if (mc.thePlayer != null) {
         synchronized(lIIlIIIIlIlIIII.positions) {
            lIIlIIIIlIlIIII.positions.add(new double[]{mc.thePlayer.posX, mc.thePlayer.getEntityBoundingBox().minY + (double)(mc.thePlayer.getEyeHeight() * 0.5F), mc.thePlayer.posZ});
            boolean var10001 = false;
            lIIlIIIIlIlIIII.positions.add(new double[]{mc.thePlayer.posX, mc.thePlayer.getEntityBoundingBox().minY, mc.thePlayer.posZ});
            var10001 = false;
         }

         super.onEnable();
      }
   }

   @EventTarget
   public void onRender3D(Render3DEvent lIIlIIIIllIllll) {
      double lIIlIIIIllIIlII = (Boolean)lIIlIIIIlllIIII.colorRainbow.get() ? ColorUtils.rainbow() : new Color((Integer)lIIlIIIIlllIIII.colorRedValue.get(), (Integer)lIIlIIIIlllIIII.colorGreenValue.get(), (Integer)lIIlIIIIlllIIII.colorBlueValue.get());
      synchronized(lIIlIIIIlllIIII.positions) {
         GL11.glPushMatrix();
         GL11.glDisable(3553);
         GL11.glBlendFunc(770, 771);
         GL11.glEnable(2848);
         GL11.glEnable(3042);
         GL11.glDisable(2929);
         mc.entityRenderer.disableLightmap();
         GL11.glBegin(3);
         RenderUtils.glColor(lIIlIIIIllIIlII);
         byte lIIlIIIIllIIIlI = mc.getRenderManager().viewerPosX;
         int lIIlIIIIllIIIIl = mc.getRenderManager().viewerPosY;
         double lIIlIIIIlllIIIl = mc.getRenderManager().viewerPosZ;
         Iterator lIIlIIIIlIlllll = lIIlIIIIlllIIII.positions.iterator();

         while(lIIlIIIIlIlllll.hasNext()) {
            char lIIlIIIIlIllllI = (double[])lIIlIIIIlIlllll.next();
            GL11.glVertex3d(lIIlIIIIlIllllI[0] - lIIlIIIIllIIIlI, lIIlIIIIlIllllI[1] - lIIlIIIIllIIIIl, lIIlIIIIlIllllI[2] - lIIlIIIIlllIIIl);
         }

         GL11.glColor4d(1.0D, 1.0D, 1.0D, 1.0D);
         GL11.glEnd();
         GL11.glEnable(2929);
         GL11.glDisable(2848);
         GL11.glDisable(3042);
         GL11.glEnable(3553);
         GL11.glPopMatrix();
      }
   }

   public void onDisable() {
      synchronized(lIIlIIIIlIIlIlI.positions) {
         lIIlIIIIlIIlIlI.positions.clear();
      }

      super.onDisable();
   }

   @EventTarget
   public void onUpdate(UpdateEvent lIIlIIIIlIllIII) {
      synchronized(lIIlIIIIlIlIlll.positions) {
         lIIlIIIIlIlIlll.positions.add(new double[]{mc.thePlayer.posX, mc.thePlayer.getEntityBoundingBox().minY, mc.thePlayer.posZ});
         boolean var10001 = false;
      }
   }
}
