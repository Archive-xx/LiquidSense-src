//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.combat;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0007J\u0010\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0014H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/AutoClicker;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "jitterValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "leftDelay", "", "leftLastSwing", "leftValue", "maxCPSValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "minCPSValue", "rightDelay", "rightLastSwing", "rightValue", "onRender", "", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidSense"}
)
@ModuleInfo(
   name = "AutoClicker",
   description = "Constantly clicks when holding down a mouse button.",
   category = ModuleCategory.COMBAT
)
public final class AutoClicker extends Module {
   // $FF: synthetic field
   private final IntegerValue minCPSValue;
   // $FF: synthetic field
   private long leftDelay;
   // $FF: synthetic field
   private final BoolValue jitterValue;
   // $FF: synthetic field
   private final IntegerValue maxCPSValue;
   // $FF: synthetic field
   private long leftLastSwing;
   // $FF: synthetic field
   private long rightDelay;
   // $FF: synthetic field
   private long rightLastSwing;
   // $FF: synthetic field
   private final BoolValue leftValue;
   // $FF: synthetic field
   private final BoolValue rightValue;

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent lIllllIIllIIII) {
      Intrinsics.checkParameterIsNotNull(lIllllIIllIIII, "event");
      if ((Boolean)lIllllIIllIIIl.jitterValue.get()) {
         EntityPlayerSP var2;
         label47: {
            KeyBinding var10000;
            if ((Boolean)lIllllIIllIIIl.leftValue.get()) {
               var10000 = access$getMc$p$s1046033730().gameSettings.keyBindAttack;
               Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.gameSettings.keyBindAttack");
               if (var10000.isKeyDown()) {
                  break label47;
               }
            }

            if (!(Boolean)lIllllIIllIIIl.rightValue.get()) {
               return;
            }

            var10000 = access$getMc$p$s1046033730().gameSettings.keyBindUseItem;
            Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.gameSettings.keyBindUseItem");
            if (!var10000.isKeyDown()) {
               return;
            }

            var2 = access$getMc$p$s1046033730().thePlayer;
            Intrinsics.checkExpressionValueIsNotNull(var2, "mc.thePlayer");
            if (var2.isUsingItem()) {
               return;
            }
         }

         if (Random.Default.nextBoolean()) {
            var2 = access$getMc$p$s1046033730().thePlayer;
            var2.rotationYaw += Random.Default.nextBoolean() ? -RandomUtils.nextFloat(0.0F, 1.0F) : RandomUtils.nextFloat(0.0F, 1.0F);
         }

         if (Random.Default.nextBoolean()) {
            var2 = access$getMc$p$s1046033730().thePlayer;
            var2.rotationPitch += Random.Default.nextBoolean() ? -RandomUtils.nextFloat(0.0F, 1.0F) : RandomUtils.nextFloat(0.0F, 1.0F);
            if (access$getMc$p$s1046033730().thePlayer.rotationPitch > (float)90) {
               access$getMc$p$s1046033730().thePlayer.rotationPitch = 90.0F;
            } else if (access$getMc$p$s1046033730().thePlayer.rotationPitch < (float)-90) {
               access$getMc$p$s1046033730().thePlayer.rotationPitch = -90.0F;
            }
         }
      }

   }

   public AutoClicker() {
      lIllllIIlIlllI.maxCPSValue = (IntegerValue)(new IntegerValue("MaxCPS", 8, 1, 20) {
         protected void onChanged(int lIllIlIIIll, int lIllIlIIIlI) {
            int lIllIlIIlIl = ((Number)lIllllIIlIlllI.minCPSValue.get()).intValue();
            if (lIllIlIIlIl > lIllIlIIIlI) {
               lIllIlIIIIl.set(lIllIlIIlIl);
            }

         }
      });
      lIllllIIlIlllI.minCPSValue = (IntegerValue)(new IntegerValue("MinCPS", 5, 1, 20) {
         protected void onChanged(int llllllllllllllllllIIllllIIIlIlIl, int llllllllllllllllllIIllllIIIlIIlI) {
            int llllllllllllllllllIIllllIIIlIlll = ((Number)lIllllIIlIlllI.maxCPSValue.get()).intValue();
            if (llllllllllllllllllIIllllIIIlIlll < llllllllllllllllllIIllllIIIlIIlI) {
               llllllllllllllllllIIllllIIIlIIll.set(llllllllllllllllllIIllllIIIlIlll);
            }

         }
      });
      lIllllIIlIlllI.rightValue = new BoolValue("Right", true);
      lIllllIIlIlllI.leftValue = new BoolValue("Left", true);
      lIllllIIlIlllI.jitterValue = new BoolValue("Jitter", false);
      lIllllIIlIlllI.rightDelay = TimeUtils.randomClickDelay(((Number)lIllllIIlIlllI.minCPSValue.get()).intValue(), ((Number)lIllllIIlIlllI.maxCPSValue.get()).intValue());
      lIllllIIlIlllI.leftDelay = TimeUtils.randomClickDelay(((Number)lIllllIIlIlllI.minCPSValue.get()).intValue(), ((Number)lIllllIIlIlllI.maxCPSValue.get()).intValue());
   }

   @EventTarget
   public final void onRender(@NotNull Render3DEvent lIllllIIllIllI) {
      Intrinsics.checkParameterIsNotNull(lIllllIIllIllI, "event");
      KeyBinding var10000 = access$getMc$p$s1046033730().gameSettings.keyBindAttack;
      Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.gameSettings.keyBindAttack");
      if (var10000.isKeyDown() && (Boolean)lIllllIIllIlll.leftValue.get() && System.currentTimeMillis() - lIllllIIllIlll.leftLastSwing >= lIllllIIllIlll.leftDelay) {
         var10000 = access$getMc$p$s1046033730().gameSettings.keyBindAttack;
         Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.gameSettings.keyBindAttack");
         KeyBinding.onTick(var10000.getKeyCode());
         lIllllIIllIlll.leftLastSwing = System.currentTimeMillis();
         lIllllIIllIlll.leftDelay = TimeUtils.randomClickDelay(((Number)lIllllIIllIlll.minCPSValue.get()).intValue(), ((Number)lIllllIIllIlll.maxCPSValue.get()).intValue());
      }

      var10000 = access$getMc$p$s1046033730().gameSettings.keyBindUseItem;
      Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.gameSettings.keyBindUseItem");
      if (var10000.isKeyDown()) {
         EntityPlayerSP var3 = access$getMc$p$s1046033730().thePlayer;
         Intrinsics.checkExpressionValueIsNotNull(var3, "mc.thePlayer");
         if (!var3.isUsingItem() && (Boolean)lIllllIIllIlll.rightValue.get() && System.currentTimeMillis() - lIllllIIllIlll.rightLastSwing >= lIllllIIllIlll.rightDelay) {
            var10000 = access$getMc$p$s1046033730().gameSettings.keyBindUseItem;
            Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.gameSettings.keyBindUseItem");
            KeyBinding.onTick(var10000.getKeyCode());
            lIllllIIllIlll.rightLastSwing = System.currentTimeMillis();
            lIllllIIllIlll.rightDelay = TimeUtils.randomClickDelay(((Number)lIllllIIllIlll.minCPSValue.get()).intValue(), ((Number)lIllllIIllIlll.maxCPSValue.get()).intValue());
         }
      }

   }
}
