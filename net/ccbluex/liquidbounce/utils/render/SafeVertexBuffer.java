//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.utils.render;

import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.client.renderer.vertex.VertexFormat;

public class SafeVertexBuffer extends VertexBuffer {
   public SafeVertexBuffer(VertexFormat lllllllllllllllllllIIlllIIIlIlIl) {
      super(lllllllllllllllllllIIlllIIIlIlIl);
   }

   protected void finalize() throws Throwable {
      lllllllllllllllllllIIlllIIIlIIII.deleteGlBuffers();
   }
}
