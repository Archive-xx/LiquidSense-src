//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement.speeds;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class SpeedMode extends MinecraftInstance {
   // $FF: synthetic field
   public final String modeName;

   public abstract void onMotion();

   public abstract void onMove(MoveEvent var1);

   public void onTick() {
   }

   public void onDisable() {
   }

   public void onEnable() {
   }

   public SpeedMode(String lIlllIlIIllIIIl) {
      lIlllIlIIllIIlI.modeName = lIlllIlIIllIIIl;
   }

   public abstract void onUpdate();

   public boolean isActive() {
      Speed lIlllIlIIlIIllI = (Speed)LiquidBounce.moduleManager.getModule(Speed.class);
      return lIlllIlIIlIIllI != null && !mc.thePlayer.isSneaking() && lIlllIlIIlIIllI.getState() && ((String)lIlllIlIIlIIllI.modeValue.get()).equals(lIlllIlIIlIIlll.modeName);
   }
}
