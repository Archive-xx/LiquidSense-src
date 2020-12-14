package net.ccbluex.liquidbounce.injection.vanilla;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import net.ccbluex.liquidbounce.script.remapper.injection.transformers.AbstractJavaLinkerTransformer;
import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

public class TweakerMixinLoader implements ITweaker {
   // $FF: synthetic field
   private final List<String> list = new ArrayList();

   public void injectIntoClassLoader(LaunchClassLoader llllllllllllllllllIIIlIIIIIIIllI) {
      System.out.println("[LiquidBounce] Injecting with TweakerMixinLoader.");
      MixinBootstrap.init();
      Mixins.addConfiguration("liquidbounce.forge.mixins.json");
      MixinEnvironment.getDefaultEnvironment().setSide(MixinEnvironment.Side.CLIENT);
      boolean var10001 = false;
      llllllllllllllllllIIIlIIIIIIIllI.registerTransformer(AbstractJavaLinkerTransformer.class.getName());
   }

   public void acceptOptions(List<String> llllllllllllllllllIIIlIIIIIIllll, File llllllllllllllllllIIIlIIIIIlIIll, File llllllllllllllllllIIIlIIIIIIllIl, String llllllllllllllllllIIIlIIIIIIlIll) {
      llllllllllllllllllIIIlIIIIIlIlIl.list.addAll(llllllllllllllllllIIIlIIIIIIllll);
      boolean var10001 = false;
      if (!llllllllllllllllllIIIlIIIIIIllll.contains("--version") && llllllllllllllllllIIIlIIIIIIlIll != null) {
         llllllllllllllllllIIIlIIIIIlIlIl.list.add("--version");
         var10001 = false;
         llllllllllllllllllIIIlIIIIIlIlIl.list.add(llllllllllllllllllIIIlIIIIIIlIll);
         var10001 = false;
      }

      if (!llllllllllllllllllIIIlIIIIIIllll.contains("--assetDir") && llllllllllllllllllIIIlIIIIIIllIl != null) {
         llllllllllllllllllIIIlIIIIIlIlIl.list.add("--assetDir");
         var10001 = false;
         llllllllllllllllllIIIlIIIIIlIlIl.list.add(llllllllllllllllllIIIlIIIIIIllIl.getAbsolutePath());
         var10001 = false;
      }

      if (!llllllllllllllllllIIIlIIIIIIllll.contains("--gameDir") && llllllllllllllllllIIIlIIIIIlIIll != null) {
         llllllllllllllllllIIIlIIIIIlIlIl.list.add("--gameDir");
         var10001 = false;
         llllllllllllllllllIIIlIIIIIlIlIl.list.add(llllllllllllllllllIIIlIIIIIlIIll.getAbsolutePath());
         var10001 = false;
      }

   }

   public String getLaunchTarget() {
      return "net.minecraft.client.main.Main";
   }

   public String[] getLaunchArguments() {
      return (String[])llllllllllllllllllIIIlIIIIIIIIII.list.toArray(new String[0]);
   }
}
