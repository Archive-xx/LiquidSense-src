//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.gui.ServerSelectionList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin({ServerSelectionList.class})
public abstract class MixinServerSelectionList extends GuiSlot {
   public MixinServerSelectionList(Minecraft llllllllllllllllllllIIlIIllllIII, int llllllllllllllllllllIIlIIlllIlll, int llllllllllllllllllllIIlIIlllIllI, int llllllllllllllllllllIIlIIlllIlIl, int llllllllllllllllllllIIlIIlllIlII, int llllllllllllllllllllIIlIIlllIIll) {
      super(llllllllllllllllllllIIlIIllllIII, llllllllllllllllllllIIlIIlllIlll, llllllllllllllllllllIIlIIlllIllI, llllllllllllllllllllIIlIIlllIlIl, llllllllllllllllllllIIlIIlllIlII, llllllllllllllllllllIIlIIlllIIll);
   }

   @Overwrite
   protected int getScrollBarX() {
      return llllllllllllllllllllIIlIIllIllII.width - 5;
   }
}
