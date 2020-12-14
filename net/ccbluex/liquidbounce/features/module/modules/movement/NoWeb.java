//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\r"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/NoWeb;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "tag", "", "getTag", "()Ljava/lang/String;", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidSense"}
)
@ModuleInfo(
   name = "NoWeb",
   description = "Prevents you from getting slowed down in webs.",
   category = ModuleCategory.MOVEMENT
)
public final class NoWeb extends Module {
   // $FF: synthetic field
   private final ListValue modeValue = new ListValue("Mode", new String[]{"None", "AAC", "LAAC", "Rewi"}, "None");

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent llIlIIIlIIIIIlI) {
      Intrinsics.checkParameterIsNotNull(llIlIIIlIIIIIlI, "event");
      if (access$getMc$p$s1046033730().thePlayer.isInWeb) {
         long llIlIIIlIIIIIIl = (String)llIlIIIlIIIIIll.modeValue.get();
         char llIlIIIlIIIIIII = false;
         if (llIlIIIlIIIIIIl == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
         } else {
            String var10000 = llIlIIIlIIIIIIl.toLowerCase();
            Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).toLowerCase()");
            llIlIIIlIIIIIIl = var10000;
            KeyBinding var4;
            switch(llIlIIIlIIIIIIl.hashCode()) {
            case 96323:
               if (llIlIIIlIIIIIIl.equals("aac")) {
                  access$getMc$p$s1046033730().thePlayer.jumpMovementFactor = 0.59F;
                  var4 = access$getMc$p$s1046033730().gameSettings.keyBindSneak;
                  Intrinsics.checkExpressionValueIsNotNull(var4, "mc.gameSettings.keyBindSneak");
                  if (!var4.isKeyDown()) {
                     access$getMc$p$s1046033730().thePlayer.motionY = 0.0D;
                  }
               }
               break;
            case 3313751:
               if (llIlIIIlIIIIIIl.equals("laac")) {
                  access$getMc$p$s1046033730().thePlayer.jumpMovementFactor = access$getMc$p$s1046033730().thePlayer.movementInput.moveStrafe != 0.0F ? 1.0F : 1.21F;
                  var4 = access$getMc$p$s1046033730().gameSettings.keyBindSneak;
                  Intrinsics.checkExpressionValueIsNotNull(var4, "mc.gameSettings.keyBindSneak");
                  if (!var4.isKeyDown()) {
                     access$getMc$p$s1046033730().thePlayer.motionY = 0.0D;
                  }

                  if (access$getMc$p$s1046033730().thePlayer.onGround) {
                     access$getMc$p$s1046033730().thePlayer.jump();
                  }
               }
               break;
            case 3387192:
               if (llIlIIIlIIIIIIl.equals("none")) {
                  access$getMc$p$s1046033730().thePlayer.isInWeb = false;
               }
               break;
            case 3497029:
               if (llIlIIIlIIIIIIl.equals("rewi")) {
                  access$getMc$p$s1046033730().thePlayer.jumpMovementFactor = 0.42F;
                  if (access$getMc$p$s1046033730().thePlayer.onGround) {
                     access$getMc$p$s1046033730().thePlayer.jump();
                  }
               }
            }

         }
      }
   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   @Nullable
   public String getTag() {
      return (String)llIlIIIIllllllI.modeValue.get();
   }
}
