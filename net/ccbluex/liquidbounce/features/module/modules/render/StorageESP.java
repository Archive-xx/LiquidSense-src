//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.render;

import co.uk.hexeption.utils.OutlineUtils;
import java.awt.Color;
import java.util.Iterator;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.world.ChestAura;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.shader.FramebufferShader;
import net.ccbluex.liquidbounce.utils.render.shader.shaders.GlowShader;
import net.ccbluex.liquidbounce.utils.render.shader.shaders.OutlineShader;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityHopper;
import org.lwjgl.opengl.GL11;

@ModuleInfo(
   name = "StorageESP",
   description = "Allows you to see chests, dispensers, etc. through walls.",
   category = ModuleCategory.RENDER
)
public class StorageESP extends Module {
   // $FF: synthetic field
   private final ListValue modeValue = new ListValue("Mode", new String[]{"Box", "OtherBox", "Outline", "ShaderOutline", "ShaderGlow", "2D", "WireFrame"}, "Outline");
   // $FF: synthetic field
   private final BoolValue dispenserValue = new BoolValue("Dispenser", true);
   // $FF: synthetic field
   private final BoolValue hopperValue = new BoolValue("Hopper", true);
   // $FF: synthetic field
   private final BoolValue furnaceValue = new BoolValue("Furnace", true);
   // $FF: synthetic field
   private final BoolValue enderChestValue = new BoolValue("EnderChest", true);
   // $FF: synthetic field
   private final BoolValue chestValue = new BoolValue("Chest", true);

   @EventTarget
   public void onRender3D(Render3DEvent lIIIIIIllllIl) {
      try {
         String lIIIIIlIIIIII = (String)lIIIIIIlllllI.modeValue.get();
         if (lIIIIIlIIIIII.equalsIgnoreCase("outline")) {
            ClientUtils.disableFastRender();
            OutlineUtils.checkSetupFBO();
         }

         float lIIIIIIllllll = mc.gameSettings.gammaSetting;
         mc.gameSettings.gammaSetting = 100000.0F;
         Iterator lIIIIIIlllIII = mc.theWorld.loadedTileEntityList.iterator();

         while(lIIIIIIlllIII.hasNext()) {
            Exception lIIIIIIllIlll = (TileEntity)lIIIIIIlllIII.next();
            long lIIIIIIllIllI = null;
            if ((Boolean)lIIIIIIlllllI.chestValue.get() && lIIIIIIllIlll instanceof TileEntityChest && !ChestAura.INSTANCE.getClickedBlocks().contains(lIIIIIIllIlll.getPos())) {
               lIIIIIIllIllI = new Color(0, 66, 255);
            }

            if ((Boolean)lIIIIIIlllllI.enderChestValue.get() && lIIIIIIllIlll instanceof TileEntityEnderChest && !ChestAura.INSTANCE.getClickedBlocks().contains(lIIIIIIllIlll.getPos())) {
               lIIIIIIllIllI = Color.MAGENTA;
            }

            if ((Boolean)lIIIIIIlllllI.furnaceValue.get() && lIIIIIIllIlll instanceof TileEntityFurnace) {
               lIIIIIIllIllI = Color.BLACK;
            }

            if ((Boolean)lIIIIIIlllllI.dispenserValue.get() && lIIIIIIllIlll instanceof TileEntityDispenser) {
               lIIIIIIllIllI = Color.BLACK;
            }

            if ((Boolean)lIIIIIIlllllI.hopperValue.get() && lIIIIIIllIlll instanceof TileEntityHopper) {
               lIIIIIIllIllI = Color.GRAY;
            }

            if (lIIIIIIllIllI != null) {
               if (!(lIIIIIIllIlll instanceof TileEntityChest) && !(lIIIIIIllIlll instanceof TileEntityEnderChest)) {
                  RenderUtils.drawBlockBox(lIIIIIIllIlll.getPos(), lIIIIIIllIllI, !lIIIIIlIIIIII.equalsIgnoreCase("otherbox"));
               } else {
                  String lIIIIIIllIlIl = lIIIIIlIIIIII.toLowerCase();
                  int lIIIIIIllIlII = -1;
                  switch(lIIIIIIllIlIl.hashCode()) {
                  case -1171135301:
                     if (lIIIIIIllIlIl.equals("otherbox")) {
                        lIIIIIIllIlII = 0;
                     }
                     break;
                  case -1106245566:
                     if (lIIIIIIllIlIl.equals("outline")) {
                        lIIIIIIllIlII = 3;
                     }
                     break;
                  case -941784056:
                     if (lIIIIIIllIlIl.equals("wireframe")) {
                        lIIIIIIllIlII = 4;
                     }
                     break;
                  case 1650:
                     if (lIIIIIIllIlIl.equals("2d")) {
                        lIIIIIIllIlII = 2;
                     }
                     break;
                  case 97739:
                     if (lIIIIIIllIlIl.equals("box")) {
                        lIIIIIIllIlII = 1;
                     }
                  }

                  switch(lIIIIIIllIlII) {
                  case 0:
                  case 1:
                     RenderUtils.drawBlockBox(lIIIIIIllIlll.getPos(), lIIIIIIllIllI, !lIIIIIlIIIIII.equalsIgnoreCase("otherbox"));
                     break;
                  case 2:
                     RenderUtils.draw2D(lIIIIIIllIlll.getPos(), lIIIIIIllIllI.getRGB(), Color.BLACK.getRGB());
                     break;
                  case 3:
                     RenderUtils.glColor(lIIIIIIllIllI);
                     OutlineUtils.renderOne(3.0F);
                     TileEntityRendererDispatcher.instance.renderTileEntity(lIIIIIIllIlll, lIIIIIIllllIl.getPartialTicks(), -1);
                     OutlineUtils.renderTwo();
                     TileEntityRendererDispatcher.instance.renderTileEntity(lIIIIIIllIlll, lIIIIIIllllIl.getPartialTicks(), -1);
                     OutlineUtils.renderThree();
                     TileEntityRendererDispatcher.instance.renderTileEntity(lIIIIIIllIlll, lIIIIIIllllIl.getPartialTicks(), -1);
                     OutlineUtils.renderFour(lIIIIIIllIllI);
                     TileEntityRendererDispatcher.instance.renderTileEntity(lIIIIIIllIlll, lIIIIIIllllIl.getPartialTicks(), -1);
                     OutlineUtils.renderFive();
                     OutlineUtils.setColor(Color.WHITE);
                     break;
                  case 4:
                     GL11.glPushMatrix();
                     GL11.glPushAttrib(1048575);
                     GL11.glPolygonMode(1032, 6913);
                     GL11.glDisable(3553);
                     GL11.glDisable(2896);
                     GL11.glDisable(2929);
                     GL11.glEnable(2848);
                     GL11.glEnable(3042);
                     GL11.glBlendFunc(770, 771);
                     TileEntityRendererDispatcher.instance.renderTileEntity(lIIIIIIllIlll, lIIIIIIllllIl.getPartialTicks(), -1);
                     RenderUtils.glColor(lIIIIIIllIllI);
                     GL11.glLineWidth(1.5F);
                     TileEntityRendererDispatcher.instance.renderTileEntity(lIIIIIIllIlll, lIIIIIIllllIl.getPartialTicks(), -1);
                     GL11.glPopAttrib();
                     GL11.glPopMatrix();
                  }
               }
            }
         }

         lIIIIIIlllIII = mc.theWorld.loadedEntityList.iterator();

         while(lIIIIIIlllIII.hasNext()) {
            Exception lIIIIIIllIlll = (Entity)lIIIIIIlllIII.next();
            if (lIIIIIIllIlll instanceof EntityMinecartChest) {
               long lIIIIIIllIllI = lIIIIIlIIIIII.toLowerCase();
               String lIIIIIIllIlIl = -1;
               switch(lIIIIIIllIllI.hashCode()) {
               case -1171135301:
                  if (lIIIIIIllIllI.equals("otherbox")) {
                     lIIIIIIllIlIl = 0;
                  }
                  break;
               case -1106245566:
                  if (lIIIIIIllIllI.equals("outline")) {
                     lIIIIIIllIlIl = 3;
                  }
                  break;
               case -941784056:
                  if (lIIIIIIllIllI.equals("wireframe")) {
                     lIIIIIIllIlIl = 4;
                  }
                  break;
               case 1650:
                  if (lIIIIIIllIllI.equals("2d")) {
                     lIIIIIIllIlIl = 2;
                  }
                  break;
               case 97739:
                  if (lIIIIIIllIllI.equals("box")) {
                     lIIIIIIllIlIl = 1;
                  }
               }

               boolean lIIIIIlIIIIlI;
               boolean var10001;
               switch(lIIIIIIllIlIl) {
               case 0:
               case 1:
                  RenderUtils.drawEntityBox(lIIIIIIllIlll, new Color(0, 66, 255), !lIIIIIlIIIIII.equalsIgnoreCase("otherbox"));
                  break;
               case 2:
                  RenderUtils.draw2D(lIIIIIIllIlll.getPosition(), (new Color(0, 66, 255)).getRGB(), Color.BLACK.getRGB());
                  break;
               case 3:
                  lIIIIIlIIIIlI = mc.gameSettings.entityShadows;
                  mc.gameSettings.entityShadows = false;
                  RenderUtils.glColor(new Color(0, 66, 255));
                  OutlineUtils.renderOne(3.0F);
                  mc.getRenderManager().renderEntityStatic(lIIIIIIllIlll, mc.timer.renderPartialTicks, true);
                  var10001 = false;
                  OutlineUtils.renderTwo();
                  mc.getRenderManager().renderEntityStatic(lIIIIIIllIlll, mc.timer.renderPartialTicks, true);
                  var10001 = false;
                  OutlineUtils.renderThree();
                  mc.getRenderManager().renderEntityStatic(lIIIIIIllIlll, mc.timer.renderPartialTicks, true);
                  var10001 = false;
                  OutlineUtils.renderFour(new Color(0, 66, 255));
                  mc.getRenderManager().renderEntityStatic(lIIIIIIllIlll, mc.timer.renderPartialTicks, true);
                  var10001 = false;
                  OutlineUtils.renderFive();
                  OutlineUtils.setColor(Color.WHITE);
                  mc.gameSettings.entityShadows = lIIIIIlIIIIlI;
                  break;
               case 4:
                  lIIIIIlIIIIlI = mc.gameSettings.entityShadows;
                  mc.gameSettings.entityShadows = false;
                  GL11.glPushMatrix();
                  GL11.glPushAttrib(1048575);
                  GL11.glPolygonMode(1032, 6913);
                  GL11.glDisable(3553);
                  GL11.glDisable(2896);
                  GL11.glDisable(2929);
                  GL11.glEnable(2848);
                  GL11.glEnable(3042);
                  GL11.glBlendFunc(770, 771);
                  RenderUtils.glColor(new Color(0, 66, 255));
                  mc.getRenderManager().renderEntityStatic(lIIIIIIllIlll, mc.timer.renderPartialTicks, true);
                  var10001 = false;
                  RenderUtils.glColor(new Color(0, 66, 255));
                  GL11.glLineWidth(1.5F);
                  mc.getRenderManager().renderEntityStatic(lIIIIIIllIlll, mc.timer.renderPartialTicks, true);
                  var10001 = false;
                  GL11.glPopAttrib();
                  GL11.glPopMatrix();
                  mc.gameSettings.entityShadows = lIIIIIlIIIIlI;
               }
            }
         }

         RenderUtils.glColor(new Color(255, 255, 255, 255));
         mc.gameSettings.gammaSetting = lIIIIIIllllll;
      } catch (Exception var9) {
      }

   }

   @EventTarget
   public void onRender2D(Render2DEvent lIIIIIIlIIIll) {
      String lIIIIIIlIIllI = (String)lIIIIIIlIlIII.modeValue.get();
      boolean lIIIIIIlIIIIl = lIIIIIIlIIllI.equalsIgnoreCase("shaderoutline") ? OutlineShader.OUTLINE_SHADER : (lIIIIIIlIIllI.equalsIgnoreCase("shaderglow") ? GlowShader.GLOW_SHADER : null);
      if (lIIIIIIlIIIIl != null) {
         ((FramebufferShader)lIIIIIIlIIIIl).startDraw(lIIIIIIlIIIll.getPartialTicks());

         try {
            byte lIIIIIIlIIIII = mc.getRenderManager();
            Iterator lIIIIIIIlllll = mc.theWorld.loadedTileEntityList.iterator();

            while(lIIIIIIIlllll.hasNext()) {
               TileEntity lIIIIIIlIllII = (TileEntity)lIIIIIIIlllll.next();
               if (lIIIIIIlIllII instanceof TileEntityChest) {
                  TileEntityRendererDispatcher.instance.renderTileEntityAt(lIIIIIIlIllII, (double)lIIIIIIlIllII.getPos().getX() - lIIIIIIlIIIII.renderPosX, (double)lIIIIIIlIllII.getPos().getY() - lIIIIIIlIIIII.renderPosY, (double)lIIIIIIlIllII.getPos().getZ() - lIIIIIIlIIIII.renderPosZ, lIIIIIIlIIIll.getPartialTicks());
               }
            }

            lIIIIIIIlllll = mc.theWorld.loadedEntityList.iterator();

            while(lIIIIIIIlllll.hasNext()) {
               Entity lIIIIIIlIlIll = (Entity)lIIIIIIIlllll.next();
               if (lIIIIIIlIlIll instanceof EntityMinecartChest) {
                  lIIIIIIlIIIII.renderEntityStatic(lIIIIIIlIlIll, lIIIIIIlIIIll.getPartialTicks(), true);
                  boolean var10001 = false;
               }
            }
         } catch (Exception var7) {
            ClientUtils.getLogger().error("An error occurred while rendering all storages for shader esp", var7);
         }

         ((FramebufferShader)lIIIIIIlIIIIl).stopDraw(new Color(0, 66, 255), lIIIIIIlIIllI.equalsIgnoreCase("shaderglow") ? 2.5F : 1.5F, 1.0F);
      }
   }
}
