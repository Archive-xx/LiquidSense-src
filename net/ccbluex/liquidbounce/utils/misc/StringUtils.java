//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.utils.misc;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Decoder;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sun.misc.BASE64Encoder;

@SideOnly(Side.CLIENT)
public final class StringUtils {
   public static String reverse(String llllllllllllllllllllllIllIIIIIlI) {
      return String.valueOf((new StringBuilder(llllllllllllllllllllllIllIIIIIlI)).reverse());
   }

   public static Long timestampToDate(long llllllllllllllllllllllIllIIllIII) {
      byte llllllllllllllllllllllIllIIlIlll = String.valueOf(llllllllllllllllllllllIllIIllIII).substring(0, 10);
      return Long.valueOf(llllllllllllllllllllllIllIIlIlll);
   }

   public static String encrypt(String llllllllllllllllllllllIllIllIIII, String llllllllllllllllllllllIllIlIlIII) {
      SecretKeySpec llllllllllllllllllllllIllIlIIllI = new SecretKeySpec(llllllllllllllllllllllIllIlIlIII.getBytes(), "AES");

      try {
         short llllllllllllllllllllllIllIlIIlIl = Cipher.getInstance("AES/ECB/PKCS5Padding");
         llllllllllllllllllllllIllIlIIlIl.init(1, llllllllllllllllllllllIllIlIIllI);
         int llllllllllllllllllllllIllIlIIlII = llllllllllllllllllllllIllIlIIlIl.doFinal(llllllllllllllllllllllIllIllIIII.getBytes());
         boolean llllllllllllllllllllllIllIlIIIll = new BASE64Encoder();
         return llllllllllllllllllllllIllIlIIIll.encode(llllllllllllllllllllllIllIlIIlII);
      } catch (Exception var6) {
         var6.printStackTrace();
         Minecraft.getMinecraft().shutdown();
         return null;
      }
   }

   public static String toCompleteString(String[] llllllllllllllllllllllIlIlllllIl, int llllllllllllllllllllllIlIllllllI) {
      return llllllllllllllllllllllIlIlllllIl.length <= llllllllllllllllllllllIlIllllllI ? "" : String.join(" ", (CharSequence[])Arrays.copyOfRange(llllllllllllllllllllllIlIlllllIl, llllllllllllllllllllllIlIllllllI, llllllllllllllllllllllIlIlllllIl.length));
   }

   public static String stringToMD5(String llllllllllllllllllllllIllIIIlIll) {
      byte[] llllllllllllllllllllllIllIIIlIlI;
      try {
         llllllllllllllllllllllIllIIIlIlI = MessageDigest.getInstance("md5").digest(llllllllllllllllllllllIllIIIlIll.getBytes());
      } catch (NoSuchAlgorithmException var4) {
         throw new RuntimeException("NMSLLLLLLLLLLLLLLLLLLLLLLLLLLL");
      }

      byte llllllllllllllllllllllIllIIIIllI = new StringBuilder((new BigInteger(1, llllllllllllllllllllllIllIIIlIlI)).toString(16));

      for(int llllllllllllllllllllllIllIIIIlIl = 0; llllllllllllllllllllllIllIIIIlIl < 32 - llllllllllllllllllllllIllIIIIllI.length(); ++llllllllllllllllllllllIllIIIIlIl) {
         llllllllllllllllllllllIllIIIIllI.insert(0, "0");
         boolean var10001 = false;
      }

      return llllllllllllllllllllllIllIIIIllI.substring(8, 24);
   }

   public static String decrypt(String llllllllllllllllllllllIlllIlIIlI, String llllllllllllllllllllllIlllIIllII) {
      byte[] llllllllllllllllllllllIlllIIlIll = null;

      try {
         Decoder llllllllllllllllllllllIlllIllIlI = Base64.getDecoder();
         long llllllllllllllllllllllIlllIIlIIl = new SecretKeySpec(llllllllllllllllllllllIlllIIllII.getBytes(), "AES");
         short llllllllllllllllllllllIlllIIlIII = Cipher.getInstance("AES/ECB/PKCS5Padding");
         llllllllllllllllllllllIlllIIlIII.init(2, llllllllllllllllllllllIlllIIlIIl);
         llllllllllllllllllllllIlllIIlIll = llllllllllllllllllllllIlllIIlIII.doFinal(llllllllllllllllllllllIlllIllIlI.decode(llllllllllllllllllllllIlllIlIIlI));
      } catch (Exception var6) {
         var6.printStackTrace();
         Minecraft.getMinecraft().shutdown();
      }

      return new String(llllllllllllllllllllllIlllIIlIll);
   }

   public static String replace(String llllllllllllllllllllllIlIllIlIll, String llllllllllllllllllllllIlIlllIIII, String llllllllllllllllllllllIlIllIlIIl) {
      if (!llllllllllllllllllllllIlIllIlIll.isEmpty() && !llllllllllllllllllllllIlIlllIIII.isEmpty() && !llllllllllllllllllllllIlIlllIIII.equals(llllllllllllllllllllllIlIllIlIIl)) {
         if (llllllllllllllllllllllIlIllIlIIl == null) {
            llllllllllllllllllllllIlIllIlIIl = "";
         }

         double llllllllllllllllllllllIlIllIlIII = llllllllllllllllllllllIlIllIlIll.length();
         boolean llllllllllllllllllllllIlIllIIlll = llllllllllllllllllllllIlIlllIIII.length();
         StringBuilder llllllllllllllllllllllIlIllIllII = new StringBuilder(llllllllllllllllllllllIlIllIlIll);

         for(int llllllllllllllllllllllIlIllIIlIl = 0; llllllllllllllllllllllIlIllIIlIl < llllllllllllllllllllllIlIllIlIII; ++llllllllllllllllllllllIlIllIIlIl) {
            double llllllllllllllllllllllIlIllIIlII = llllllllllllllllllllllIlIllIllII.indexOf(llllllllllllllllllllllIlIlllIIII, llllllllllllllllllllllIlIllIIlIl);
            if (llllllllllllllllllllllIlIllIIlII == -1) {
               if (llllllllllllllllllllllIlIllIIlIl == 0) {
                  return llllllllllllllllllllllIlIllIlIll;
               }

               return String.valueOf(llllllllllllllllllllllIlIllIllII);
            }

            llllllllllllllllllllllIlIllIllII.replace(llllllllllllllllllllllIlIllIIlII, llllllllllllllllllllllIlIllIIlII + llllllllllllllllllllllIlIllIIlll, llllllllllllllllllllllIlIllIlIIl);
            boolean var10001 = false;
         }

         return String.valueOf(llllllllllllllllllllllIlIllIllII);
      } else {
         return llllllllllllllllllllllIlIllIlIll;
      }
   }
}
