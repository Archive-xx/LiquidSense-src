//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.utils.render.shader.shaders;

import net.ccbluex.liquidbounce.utils.render.shader.FramebufferShader;
import org.lwjgl.opengl.GL20;

public final class OutlineShader extends FramebufferShader {
   // $FF: synthetic field
   public static final OutlineShader OUTLINE_SHADER = new OutlineShader();

   public void updateUniforms() {
      GL20.glUniform1i(lIIlIllIlIIlIIl.getUniform("texture"), 0);
      GL20.glUniform2f(lIIlIllIlIIlIIl.getUniform("texelSize"), 1.0F / (float)mc.displayWidth * lIIlIllIlIIlIIl.radius * lIIlIllIlIIlIIl.quality, 1.0F / (float)mc.displayHeight * lIIlIllIlIIlIIl.radius * lIIlIllIlIIlIIl.quality);
      GL20.glUniform4f(lIIlIllIlIIlIIl.getUniform("color"), lIIlIllIlIIlIIl.red, lIIlIllIlIIlIIl.green, lIIlIllIlIIlIIl.blue, lIIlIllIlIIlIIl.alpha);
      GL20.glUniform1f(lIIlIllIlIIlIIl.getUniform("radius"), lIIlIllIlIIlIIl.radius);
   }

   public OutlineShader() {
      super("outline.frag");
   }

   public void setupUniforms() {
      lIIlIllIlIIlIll.setupUniform("texture");
      lIIlIllIlIIlIll.setupUniform("texelSize");
      lIIlIllIlIIlIll.setupUniform("color");
      lIIlIllIlIIlIll.setupUniform("divider");
      lIIlIllIlIIlIll.setupUniform("radius");
      lIIlIllIlIIlIll.setupUniform("maxSample");
   }
}
