//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityParticleEmitter;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@SideOnly(Side.CLIENT)
@Mixin({EffectRenderer.class})
public abstract class MixinEffectRenderer {
   // $FF: synthetic field
   @Shadow
   private List<EntityParticleEmitter> particleEmitters;

   @Shadow
   protected abstract void updateEffectLayer(int var1);

   @Overwrite
   public void updateEffects() {
      try {
         for(int llIlllllIllI = 0; llIlllllIllI < 4; ++llIlllllIllI) {
            llIllllllIII.updateEffectLayer(llIlllllIllI);
         }

         Iterator llIlllllIllI = llIllllllIII.particleEmitters.iterator();

         while(llIlllllIllI.hasNext()) {
            EntityParticleEmitter llIllllllIlI = (EntityParticleEmitter)llIlllllIllI.next();
            llIllllllIlI.onUpdate();
            if (llIllllllIlI.isDead) {
               llIlllllIllI.remove();
            }
         }
      } catch (ConcurrentModificationException var3) {
      }

   }
}
