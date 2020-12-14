//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.utils.render.shader.shaders;

import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.shader.Shader;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL20;

public final class BackgroundShader extends Shader {
   // $FF: synthetic field
   public static final BackgroundShader BACKGROUND_SHADER = new BackgroundShader();
   // $FF: synthetic field
   private float time;

   public BackgroundShader() {
      super("background.frag");
   }

   public void setupUniforms() {
      llIIIIIIlI.setupUniform("iResolution");
      llIIIIIIlI.setupUniform("iTime");
   }

   public void updateUniforms() {
      ScaledResolution lIllllllII = new ScaledResolution(mc);
      int lIlllllIll = lIlllllIIl.getUniform("iResolution");
      if (lIlllllIll > -1) {
         GL20.glUniform2f(lIlllllIll, (float)lIllllllII.getScaledWidth() * 2.0F, (float)lIllllllII.getScaledHeight() * 2.0F);
      }

      int lIlllllIlI = lIlllllIIl.getUniform("iTime");
      if (lIlllllIlI > -1) {
         GL20.glUniform1f(lIlllllIlI, lIlllllIIl.time);
      }

      lIlllllIIl.time += 0.005F * (float)RenderUtils.deltaTime;
   }
}
