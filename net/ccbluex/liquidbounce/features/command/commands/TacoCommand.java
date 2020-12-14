//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.command.commands;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u001b\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\tH\u0016¢\u0006\u0002\u0010\u0012J\b\u0010\u0013\u001a\u00020\rH\u0016J\u0010\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u0016H\u0007J\u0010\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u0018H\u0007J!\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00110\u001a2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\tH\u0016¢\u0006\u0002\u0010\u001bR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u000bR\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001c"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/TacoCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "Lnet/ccbluex/liquidbounce/event/Listenable;", "()V", "image", "", "running", "", "tacoTextures", "", "Lnet/minecraft/util/ResourceLocation;", "[Lnet/minecraft/util/ResourceLocation;", "toggle", "", "execute", "", "args", "", "([Ljava/lang/String;)V", "handleEvents", "onRender2D", "event", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "LiquidSense"}
)
public final class TacoCommand extends Command implements Listenable {
   // $FF: synthetic field
   private final ResourceLocation[] tacoTextures;
   // $FF: synthetic field
   private boolean toggle;
   // $FF: synthetic field
   private float running;
   // $FF: synthetic field
   private int image;

   @EventTarget
   public final void onRender2D(@NotNull Render2DEvent lIlIIllIIlllIII) {
      Intrinsics.checkParameterIsNotNull(lIlIIllIIlllIII, "event");
      if (lIlIIllIIlllIIl.toggle) {
         lIlIIllIIlllIIl.running += 0.15F * (float)RenderUtils.deltaTime;
         ScaledResolution lIlIIllIIllllII = new ScaledResolution(access$getMc$p$s1046033730());
         RenderUtils.drawImage(lIlIIllIIlllIIl.tacoTextures[lIlIIllIIlllIIl.image], (int)lIlIIllIIlllIIl.running, lIlIIllIIllllII.getScaledHeight() - 60, 64, 32);
         if ((float)lIlIIllIIllllII.getScaledWidth() <= lIlIIllIIlllIIl.running) {
            lIlIIllIIlllIIl.running = -64.0F;
         }

      }
   }

   public TacoCommand() {
      float lIlIIllIIlIIIlI = "taco";
      boolean lIlIIllIIlIIIIl = new String[0];
      super(lIlIIllIIlIIIlI, lIlIIllIIlIIIIl);
      lIlIIllIIlIIlII.tacoTextures = new ResourceLocation[]{new ResourceLocation("liquidbounce/taco/1.png"), new ResourceLocation("liquidbounce/taco/2.png"), new ResourceLocation("liquidbounce/taco/3.png"), new ResourceLocation("liquidbounce/taco/4.png"), new ResourceLocation("liquidbounce/taco/5.png"), new ResourceLocation("liquidbounce/taco/6.png"), new ResourceLocation("liquidbounce/taco/7.png"), new ResourceLocation("liquidbounce/taco/8.png"), new ResourceLocation("liquidbounce/taco/9.png"), new ResourceLocation("liquidbounce/taco/10.png"), new ResourceLocation("liquidbounce/taco/11.png"), new ResourceLocation("liquidbounce/taco/12.png")};
      LiquidBounce.INSTANCE.getEventManager().registerListener((Listenable)lIlIIllIIlIIlII);
   }

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent lIlIIllIIllIIII) {
      Intrinsics.checkParameterIsNotNull(lIlIIllIIllIIII, "event");
      if (!lIlIIllIIllIIll.toggle) {
         lIlIIllIIllIIll.image = 0;
      } else {
         int var10001 = lIlIIllIIllIIll.image++;
         if (lIlIIllIIllIIll.image >= lIlIIllIIllIIll.tacoTextures.length) {
            lIlIIllIIllIIll.image = 0;
         }

      }
   }

   @NotNull
   public List<String> tabComplete(@NotNull String[] lIlIIllIIlIlIlI) {
      Intrinsics.checkParameterIsNotNull(lIlIIllIIlIlIlI, "args");
      return CollectionsKt.listOf("TACO");
   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   public boolean handleEvents() {
      return true;
   }

   public void execute(@NotNull String[] lIlIIllIlIIIIlI) {
      Intrinsics.checkParameterIsNotNull(lIlIIllIlIIIIlI, "args");
      lIlIIllIlIIIIIl.toggle = !lIlIIllIlIIIIIl.toggle;
      ClientUtils.displayChatMessage(lIlIIllIlIIIIIl.toggle ? "§aTACO TACO TACO. :)" : "§cYou made the little taco sad! :(");
   }
}
