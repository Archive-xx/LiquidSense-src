//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.movement;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.ClickWindowEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.settings.GameSettings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0007J\b\u0010\u0011\u001a\u00020\u000eH\u0016J\u0010\u0010\u0012\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0013H\u0007R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\t\u001a\u0004\u0018\u00010\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f¨\u0006\u0014"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/InventoryMove;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "aacAdditionProValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "getAacAdditionProValue", "()Lnet/ccbluex/liquidbounce/value/BoolValue;", "noDetectableValue", "noMoveClicksValue", "tag", "", "getTag", "()Ljava/lang/String;", "onClick", "", "event", "Lnet/ccbluex/liquidbounce/event/ClickWindowEvent;", "onDisable", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidSense"}
)
@ModuleInfo(
   name = "InvMove",
   description = "Allows you to walk while an inventory is opened.",
   category = ModuleCategory.MOVEMENT
)
public final class InventoryMove extends Module {
   // $FF: synthetic field
   @NotNull
   private final BoolValue aacAdditionProValue = new BoolValue("AACAdditionPro", false);
   // $FF: synthetic field
   private final BoolValue noMoveClicksValue = new BoolValue("NoMoveClicks", false);
   // $FF: synthetic field
   private final BoolValue noDetectableValue = new BoolValue("NoDetectable", false);

   @NotNull
   public final BoolValue getAacAdditionProValue() {
      return lIIllIlIIlllIlI.aacAdditionProValue;
   }

   @EventTarget
   public final void onClick(@NotNull ClickWindowEvent lIIllIlIIlIlllI) {
      Intrinsics.checkParameterIsNotNull(lIIllIlIIlIlllI, "event");
      if ((Boolean)lIIllIlIIllIIIl.noMoveClicksValue.get() && MovementUtils.isMoving()) {
         lIIllIlIIlIlllI.cancelEvent();
      }

   }

   public void onDisable() {
      if (!GameSettings.isKeyDown(access$getMc$p$s1046033730().gameSettings.keyBindForward) || access$getMc$p$s1046033730().currentScreen != null) {
         access$getMc$p$s1046033730().gameSettings.keyBindForward.pressed = false;
      }

      if (!GameSettings.isKeyDown(access$getMc$p$s1046033730().gameSettings.keyBindBack) || access$getMc$p$s1046033730().currentScreen != null) {
         access$getMc$p$s1046033730().gameSettings.keyBindBack.pressed = false;
      }

      if (!GameSettings.isKeyDown(access$getMc$p$s1046033730().gameSettings.keyBindRight) || access$getMc$p$s1046033730().currentScreen != null) {
         access$getMc$p$s1046033730().gameSettings.keyBindRight.pressed = false;
      }

      if (!GameSettings.isKeyDown(access$getMc$p$s1046033730().gameSettings.keyBindLeft) || access$getMc$p$s1046033730().currentScreen != null) {
         access$getMc$p$s1046033730().gameSettings.keyBindLeft.pressed = false;
      }

      if (!GameSettings.isKeyDown(access$getMc$p$s1046033730().gameSettings.keyBindJump) || access$getMc$p$s1046033730().currentScreen != null) {
         access$getMc$p$s1046033730().gameSettings.keyBindJump.pressed = false;
      }

      if (!GameSettings.isKeyDown(access$getMc$p$s1046033730().gameSettings.keyBindSprint) || access$getMc$p$s1046033730().currentScreen != null) {
         access$getMc$p$s1046033730().gameSettings.keyBindSprint.pressed = false;
      }

   }

   @Nullable
   public String getTag() {
      return (Boolean)lIIllIlIIlIlIll.aacAdditionProValue.get() ? "AACAdditionPro" : null;
   }

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent lIIllIlIIllIllI) {
      Intrinsics.checkParameterIsNotNull(lIIllIlIIllIllI, "event");
      if (!(access$getMc$p$s1046033730().currentScreen instanceof GuiChat) && !(access$getMc$p$s1046033730().currentScreen instanceof GuiIngameMenu) && (!(Boolean)lIIllIlIIllIlll.noDetectableValue.get() || !(access$getMc$p$s1046033730().currentScreen instanceof GuiContainer))) {
         access$getMc$p$s1046033730().gameSettings.keyBindForward.pressed = GameSettings.isKeyDown(access$getMc$p$s1046033730().gameSettings.keyBindForward);
         access$getMc$p$s1046033730().gameSettings.keyBindBack.pressed = GameSettings.isKeyDown(access$getMc$p$s1046033730().gameSettings.keyBindBack);
         access$getMc$p$s1046033730().gameSettings.keyBindRight.pressed = GameSettings.isKeyDown(access$getMc$p$s1046033730().gameSettings.keyBindRight);
         access$getMc$p$s1046033730().gameSettings.keyBindLeft.pressed = GameSettings.isKeyDown(access$getMc$p$s1046033730().gameSettings.keyBindLeft);
         access$getMc$p$s1046033730().gameSettings.keyBindJump.pressed = GameSettings.isKeyDown(access$getMc$p$s1046033730().gameSettings.keyBindJump);
         access$getMc$p$s1046033730().gameSettings.keyBindSprint.pressed = GameSettings.isKeyDown(access$getMc$p$s1046033730().gameSettings.keyBindSprint);
      }

   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }
}
