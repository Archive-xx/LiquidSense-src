package net.ccbluex.liquidbounce.utils.render.shader;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.apache.commons.io.IOUtils;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public abstract class Shader extends MinecraftInstance {
   // $FF: synthetic field
   private Map<String, Integer> uniformsMap;
   // $FF: synthetic field
   private int program;

   public void stopShader() {
      GL20.glUseProgram(0);
      GL11.glPopMatrix();
   }

   private String getLogInfo(int llIIIlllIIlI) {
      return ARBShaderObjects.glGetInfoLogARB(llIIIlllIIlI, ARBShaderObjects.glGetObjectParameteriARB(llIIIlllIIlI, 35716));
   }

   public abstract void setupUniforms();

   public int getUniform(String llIIIlIlllIl) {
      return (Integer)llIIIllIIIII.uniformsMap.get(llIIIlIlllIl);
   }

   public Shader(String llIIlIIlIIIl) {
      int llIIlIIlIIII;
      int llIIlIIIlIll;
      try {
         InputStream llIIlIIlIlll = llIIlIIlIIlI.getClass().getResourceAsStream("/assets/minecraft/liquidbounce/shader/vertex.vert");
         llIIlIIlIIII = llIIlIIlIIlI.createShader(IOUtils.toString(llIIlIIlIlll), 35633);
         IOUtils.closeQuietly(llIIlIIlIlll);
         InputStream llIIlIIlIllI = llIIlIIlIIlI.getClass().getResourceAsStream(String.valueOf((new StringBuilder()).append("/assets/minecraft/liquidbounce/shader/fragment/").append(llIIlIIlIIIl)));
         llIIlIIIlIll = llIIlIIlIIlI.createShader(IOUtils.toString(llIIlIIlIllI), 35632);
         IOUtils.closeQuietly(llIIlIIlIllI);
      } catch (Exception var6) {
         var6.printStackTrace();
         return;
      }

      if (llIIlIIlIIII != 0 && llIIlIIIlIll != 0) {
         llIIlIIlIIlI.program = ARBShaderObjects.glCreateProgramObjectARB();
         if (llIIlIIlIIlI.program != 0) {
            ARBShaderObjects.glAttachObjectARB(llIIlIIlIIlI.program, llIIlIIlIIII);
            ARBShaderObjects.glAttachObjectARB(llIIlIIlIIlI.program, llIIlIIIlIll);
            ARBShaderObjects.glLinkProgramARB(llIIlIIlIIlI.program);
            ARBShaderObjects.glValidateProgramARB(llIIlIIlIIlI.program);
            ClientUtils.getLogger().info(String.valueOf((new StringBuilder()).append("[Shader] Successfully loaded: ").append(llIIlIIlIIIl)));
         }
      }
   }

   public abstract void updateUniforms();

   public int getProgramId() {
      return llIIIlIllIlI.program;
   }

   private int createShader(String llIIIlllllIl, int llIIIllllIII) {
      byte llIIIllllIll = 0;

      try {
         int llIIIllllIll = ARBShaderObjects.glCreateShaderObjectARB(llIIIllllIII);
         if (llIIIllllIll == 0) {
            return 0;
         } else {
            ARBShaderObjects.glShaderSourceARB(llIIIllllIll, llIIIlllllIl);
            ARBShaderObjects.glCompileShaderARB(llIIIllllIll);
            if (ARBShaderObjects.glGetObjectParameteriARB(llIIIllllIll, 35713) == 0) {
               throw new RuntimeException(String.valueOf((new StringBuilder()).append("Error creating shader: ").append(llIIIllllIlI.getLogInfo(llIIIllllIll))));
            } else {
               return llIIIllllIll;
            }
         }
      } catch (Exception var5) {
         ARBShaderObjects.glDeleteObjectARB(llIIIllllIll);
         throw var5;
      }
   }

   public void setUniform(String llIIIllIllIl, int llIIIllIllII) {
      llIIIllIlIll.uniformsMap.put(llIIIllIllIl, llIIIllIllII);
      boolean var10001 = false;
   }

   public void startShader() {
      GL11.glPushMatrix();
      GL20.glUseProgram(llIIlIIIIllI.program);
      if (llIIlIIIIllI.uniformsMap == null) {
         llIIlIIIIllI.uniformsMap = new HashMap();
         llIIlIIIIllI.setupUniforms();
      }

      llIIlIIIIllI.updateUniforms();
   }

   public void setupUniform(String llIIIllIIlIl) {
      llIIIllIIlII.setUniform(llIIIllIIlIl, GL20.glGetUniformLocation(llIIIllIIlII.program, llIIIllIIlIl));
   }
}
