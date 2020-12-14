//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.utils.render.shader.shaders;

import net.ccbluex.liquidbounce.utils.render.shader.FramebufferShader;
import org.lwjgl.opengl.GL20;

public final class GlowShader extends FramebufferShader {
   // $FF: synthetic field
   public static final GlowShader GLOW_SHADER = new GlowShader();

   public void setupUniforms() {
      lllllIIllIlllIl.setupUniform("texture");
      lllllIIllIlllIl.setupUniform("texelSize");
      lllllIIllIlllIl.setupUniform("color");
      lllllIIllIlllIl.setupUniform("divider");
      lllllIIllIlllIl.setupUniform("radius");
      lllllIIllIlllIl.setupUniform("maxSample");
   }

   public GlowShader() {
      super("glow.frag");
   }

   public void updateUniforms() {
      GL20.glUniform1i(lllllIIllIllIlI.getUniform("texture"), 0);
      GL20.glUniform2f(lllllIIllIllIlI.getUniform("texelSize"), 1.0F / (float)mc.displayWidth * lllllIIllIllIlI.radius * lllllIIllIllIlI.quality, 1.0F / (float)mc.displayHeight * lllllIIllIllIlI.radius * lllllIIllIllIlI.quality);
      GL20.glUniform3f(lllllIIllIllIlI.getUniform("color"), lllllIIllIllIlI.red, lllllIIllIllIlI.green, lllllIIllIllIlI.blue);
      GL20.glUniform1f(lllllIIllIllIlI.getUniform("divider"), 140.0F);
      GL20.glUniform1f(lllllIIllIllIlI.getUniform("radius"), lllllIIllIllIlI.radius);
      GL20.glUniform1f(lllllIIllIllIlI.getUniform("maxSample"), 10.0F);
   }
}
