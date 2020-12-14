package net.ccbluex.liquidbounce.utils.render;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.vitox.ParticleGenerator;

@SideOnly(Side.CLIENT)
public final class ParticleUtils {
   // $FF: synthetic field
   private static final ParticleGenerator particleGenerator = new ParticleGenerator(100);

   public static void drawParticles(int lllllllllllllllllIllllllIIIlIIII, int lllllllllllllllllIllllllIIIIllll) {
      particleGenerator.draw(lllllllllllllllllIllllllIIIlIIII, lllllllllllllllllIllllllIIIIllll);
   }
}
