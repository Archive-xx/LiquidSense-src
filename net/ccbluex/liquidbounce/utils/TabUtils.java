//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.utils;

import net.minecraft.client.gui.GuiTextField;

public final class TabUtils {
   public static void tab(GuiTextField... lIlIIIIIIlllllI) {
      for(int lIlIIIIIIllllIl = 0; lIlIIIIIIllllIl < lIlIIIIIIlllllI.length; ++lIlIIIIIIllllIl) {
         String lIlIIIIIIllllII = lIlIIIIIIlllllI[lIlIIIIIIllllIl];
         if (lIlIIIIIIllllII.isFocused()) {
            lIlIIIIIIllllII.setFocused(false);
            ++lIlIIIIIIllllIl;
            if (lIlIIIIIIllllIl >= lIlIIIIIIlllllI.length) {
               lIlIIIIIIllllIl = 0;
            }

            lIlIIIIIIlllllI[lIlIIIIIIllllIl].setFocused(true);
            break;
         }
      }

   }
}
