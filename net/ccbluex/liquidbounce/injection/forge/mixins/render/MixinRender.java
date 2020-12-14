//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({Render.class})
public abstract class MixinRender {
   @Shadow
   public void doRender(Entity lllllllllllllllllllIIlIIIlIlIIll, double lllllllllllllllllllIIlIIIlIlIIlI, double lllllllllllllllllllIIlIIIlIlIIIl, double lllllllllllllllllllIIlIIIlIlIIII, float lllllllllllllllllllIIlIIIlIIllll, float lllllllllllllllllllIIlIIIlIIlllI) {
   }

   @Shadow
   protected abstract <T extends Entity> boolean bindEntityTexture(T var1);
}
