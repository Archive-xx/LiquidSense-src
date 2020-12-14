//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.injection.forge;

import java.awt.Component;
import java.util.Map;
import javax.swing.JOptionPane;
import me.AquaVit.liquidSense.utils.HttpUtils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

public class MixinLoader implements IFMLLoadingPlugin {
   public String getAccessTransformerClass() {
      return null;
   }

   public MixinLoader() {
      int lIIIIlIlIlllIll;
      try {
         lIIIIlIlIlllIll = Integer.parseInt(HttpUtils.getCurrentVersion());
      } catch (Exception var3) {
         JOptionPane.showMessageDialog((Component)null, "从�?务器拉�?�版本失败 请�?试�?�?�或�?�系明儿", "�?务器抽风了!", 0);
         Minecraft.getMinecraft().shutdown();
         return;
      }

      if (lIIIIlIlIlllIll == 6) {
         MixinBootstrap.init();
         Mixins.addConfiguration("liquidbounce.forge.mixins.json");
         MixinEnvironment.getDefaultEnvironment().setSide(MixinEnvironment.Side.CLIENT);
         boolean var10001 = false;
      } else {
         JOptionPane.showMessageDialog((Component)null, "LiquidSense拥有新版本请更新�?�继续使用", "更新", 0);
         Minecraft.getMinecraft().shutdown();
      }

   }

   public String getSetupClass() {
      return null;
   }

   public String[] getASMTransformerClass() {
      return new String[0];
   }

   public void injectData(Map<String, Object> lIIIIlIlIllIIll) {
   }

   public String getModContainerClass() {
      return null;
   }
}
