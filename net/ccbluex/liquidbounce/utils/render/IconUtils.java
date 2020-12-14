package net.ccbluex.liquidbounce.utils.render;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;
import me.AquaVit.liquidSense.HwidCheck;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public final class IconUtils {
   public static ByteBuffer[] getFavicon() {
      try {
         String lIllIIIIIlll = HwidCheck.getIconName();
         return new ByteBuffer[]{readImageToBuffer(IconUtils.class.getResourceAsStream(String.valueOf((new StringBuilder()).append("/assets/minecraft/").append(lIllIIIIIlll).append("/icon_16x16.png")))), readImageToBuffer(IconUtils.class.getResourceAsStream(String.valueOf((new StringBuilder()).append("/assets/minecraft/").append(lIllIIIIIlll).append("/icon_32x32.png"))))};
      } catch (IOException var1) {
         var1.printStackTrace();
         return null;
      }
   }

   private static ByteBuffer readImageToBuffer(InputStream lIlIllllIlll) throws IOException {
      if (lIlIllllIlll == null) {
         return null;
      } else {
         boolean lIlIllllIllI = ImageIO.read(lIlIllllIlll);
         int[] lIlIlllllIIl = lIlIllllIllI.getRGB(0, 0, lIlIllllIllI.getWidth(), lIlIllllIllI.getHeight(), (int[])null, 0, lIlIllllIllI.getWidth());
         boolean lIlIllllIlII = ByteBuffer.allocate(4 * lIlIlllllIIl.length);
         double lIlIllllIIll = lIlIlllllIIl;
         long lIlIllllIIlI = lIlIlllllIIl.length;

         boolean var10001;
         for(int lIlIllllIIIl = 0; lIlIllllIIIl < lIlIllllIIlI; ++lIlIllllIIIl) {
            int lIlIllllllII = lIlIllllIIll[lIlIllllIIIl];
            lIlIllllIlII.putInt(lIlIllllllII << 8 | lIlIllllllII >> 24 & 255);
            var10001 = false;
         }

         lIlIllllIlII.flip();
         var10001 = false;
         return lIlIllllIlII;
      }
   }
}
