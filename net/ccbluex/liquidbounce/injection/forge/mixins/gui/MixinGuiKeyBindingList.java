//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiKeyBindingList;
import net.minecraft.client.gui.GuiSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin({GuiKeyBindingList.class})
public abstract class MixinGuiKeyBindingList extends GuiSlot {
   public MixinGuiKeyBindingList(Minecraft lllllllllllllllllIlllIIlIIlIIlII, int lllllllllllllllllIlllIIlIIlIlIlI, int lllllllllllllllllIlllIIlIIlIIIlI, int lllllllllllllllllIlllIIlIIlIIIIl, int lllllllllllllllllIlllIIlIIlIIlll, int lllllllllllllllllIlllIIlIIIlllll) {
      super(lllllllllllllllllIlllIIlIIlIIlII, lllllllllllllllllIlllIIlIIlIlIlI, lllllllllllllllllIlllIIlIIlIIIlI, lllllllllllllllllIlllIIlIIlIIIIl, lllllllllllllllllIlllIIlIIlIIlll, lllllllllllllllllIlllIIlIIIlllll);
   }

   @Overwrite
   protected int getScrollBarX() {
      return lllllllllllllllllIlllIIlIIIlllIl.width - 5;
   }
}
