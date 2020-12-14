//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.fun;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0014\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\t\u001a\u00020\n8F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/fun/Derp;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "currentSpin", "", "headlessValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "incrementValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "rotation", "", "getRotation", "()[F", "spinnyValue", "LiquidSense"}
)
@ModuleInfo(
   name = "Derp",
   description = "Makes it look like you were derping around.",
   category = ModuleCategory.FUN
)
public final class Derp extends Module {
   // $FF: synthetic field
   private float currentSpin;
   // $FF: synthetic field
   private final BoolValue headlessValue = new BoolValue("Headless", false);
   // $FF: synthetic field
   private final FloatValue incrementValue = new FloatValue("Increment", 1.0F, 0.0F, 50.0F);
   // $FF: synthetic field
   private final BoolValue spinnyValue = new BoolValue("Spinny", false);

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   @NotNull
   public final float[] getRotation() {
      String lllllllllllllllllIllIIIllIIlIlll = new float[]{access$getMc$p$s1046033730().thePlayer.rotationYaw + (float)(Math.random() * (double)360 - (double)180), (float)(Math.random() * (double)180 - (double)90)};
      if ((Boolean)lllllllllllllllllIllIIIllIIllIIl.headlessValue.get()) {
         lllllllllllllllllIllIIIllIIlIlll[1] = 180.0F;
      }

      if ((Boolean)lllllllllllllllllIllIIIllIIllIIl.spinnyValue.get()) {
         lllllllllllllllllIllIIIllIIlIlll[0] = lllllllllllllllllIllIIIllIIllIIl.currentSpin + ((Number)lllllllllllllllllIllIIIllIIllIIl.incrementValue.get()).floatValue();
         lllllllllllllllllIllIIIllIIllIIl.currentSpin = lllllllllllllllllIllIIIllIIlIlll[0];
      }

      return lllllllllllllllllIllIIIllIIlIlll;
   }
}
