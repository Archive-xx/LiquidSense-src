//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.vitox;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.vitox.particle.util.RenderUtils;

@SideOnly(Side.CLIENT)
public class ParticleGenerator {
   // $FF: synthetic field
   private int prevWidth;
   // $FF: synthetic field
   private int prevHeight;
   // $FF: synthetic field
   private final int amount;
   // $FF: synthetic field
   private final List<Particle> particles = new ArrayList();

   private void create() {
      int lIIIIlllII = new Random();

      for(int lIIIlIlIIl = 0; lIIIlIlIIl < lIIIIlllIl.amount; ++lIIIlIlIIl) {
         lIIIIlllIl.particles.add(new Particle(lIIIIlllII.nextInt(Minecraft.getMinecraft().displayWidth), lIIIIlllII.nextInt(Minecraft.getMinecraft().displayHeight)));
         boolean var10001 = false;
      }

   }

   public void draw(int lIIIllIllI, int lIIIllIIlI) {
      if (lIIIllIlII.particles.isEmpty() || lIIIllIlII.prevWidth != Minecraft.getMinecraft().displayWidth || lIIIllIlII.prevHeight != Minecraft.getMinecraft().displayHeight) {
         lIIIllIlII.particles.clear();
         lIIIllIlII.create();
      }

      lIIIllIlII.prevWidth = Minecraft.getMinecraft().displayWidth;
      lIIIllIlII.prevHeight = Minecraft.getMinecraft().displayHeight;

      Particle lIIIlllIlI;
      for(Iterator lIIIllIIIl = lIIIllIlII.particles.iterator(); lIIIllIIIl.hasNext(); RenderUtils.drawCircle(lIIIlllIlI.getX(), lIIIlllIlI.getY(), lIIIlllIlI.size, -1)) {
         lIIIlllIlI = (Particle)lIIIllIIIl.next();
         lIIIlllIlI.fall();
         lIIIlllIlI.interpolation();
         String lIIIlIllll = 50;
         boolean lIIIlllIll = (float)lIIIllIllI >= lIIIlllIlI.x - (float)lIIIlIllll && (float)lIIIllIIlI >= lIIIlllIlI.y - (float)lIIIlIllll && (float)lIIIllIllI <= lIIIlllIlI.x + (float)lIIIlIllll && (float)lIIIllIIlI <= lIIIlllIlI.y + (float)lIIIlIllll;
         if (lIIIlllIll) {
            lIIIllIlII.particles.stream().filter((lIIIIIIIII) -> {
               return lIIIIIIIII.getX() > lIIIlllIlI.getX() && lIIIIIIIII.getX() - lIIIlllIlI.getX() < (float)lIIIlIllll && lIIIlllIlI.getX() - lIIIIIIIII.getX() < (float)lIIIlIllll && (lIIIIIIIII.getY() > lIIIlllIlI.getY() && lIIIIIIIII.getY() - lIIIlllIlI.getY() < (float)lIIIlIllll || lIIIlllIlI.getY() > lIIIIIIIII.getY() && lIIIlllIlI.getY() - lIIIIIIIII.getY() < (float)lIIIlIllll);
            }).forEach((lIIIIlIIll) -> {
               lIIIlllIlI.connect(lIIIIlIIll.getX(), lIIIIlIIll.getY());
            });
         }
      }

   }

   public ParticleGenerator(int lIIlIIllIl) {
      lIIlIIllII.amount = lIIlIIllIl;
   }
}
