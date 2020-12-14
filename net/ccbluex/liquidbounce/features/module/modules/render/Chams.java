package net.ccbluex.liquidbounce.features.module.modules.render;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.ChamsColor;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(
   name = "Chams",
   description = "Allows you to see targets through blocks.",
   category = ModuleCategory.RENDER
)
@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0016\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0007R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0010\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0006R\u0011\u0010\u0012\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0006R\u0011\u0010\u0014\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0006¨\u0006\u001b"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/render/Chams;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "chestsValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "getChestsValue", "()Lnet/ccbluex/liquidbounce/value/BoolValue;", "colorA2Value", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "colorAValue", "colorBlue2Value", "colorBlueValue", "colorGreen2Value", "colorGreenValue", "colorRed2Value", "colorRedValue", "itemsValue", "getItemsValue", "rainbow", "getRainbow", "targetsValue", "getTargetsValue", "getModeValue", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidSense"}
)
public final class Chams extends Module {
   // $FF: synthetic field
   private final FloatValue colorBlue2Value = new FloatValue("B2", 200.0F, 0.0F, 255.0F);
   // $FF: synthetic field
   @NotNull
   private final BoolValue itemsValue = new BoolValue("Items", true);
   // $FF: synthetic field
   private final FloatValue colorRed2Value = new FloatValue("R2", 100.0F, 0.0F, 255.0F);
   // $FF: synthetic field
   private final FloatValue colorGreen2Value = new FloatValue("G2", 100.0F, 0.0F, 255.0F);
   // $FF: synthetic field
   private final FloatValue colorRedValue = new FloatValue("R", 200.0F, 0.0F, 255.0F);
   // $FF: synthetic field
   private final FloatValue colorBlueValue = new FloatValue("B", 100.0F, 0.0F, 255.0F);
   // $FF: synthetic field
   private final FloatValue colorAValue = new FloatValue("A", 100.0F, 0.0F, 255.0F);
   // $FF: synthetic field
   private final FloatValue colorA2Value = new FloatValue("A2", 200.0F, 0.0F, 255.0F);
   // $FF: synthetic field
   @NotNull
   private final BoolValue chestsValue = new BoolValue("Chests", true);
   // $FF: synthetic field
   @NotNull
   private final BoolValue targetsValue = new BoolValue("Targets", true);
   // $FF: synthetic field
   @NotNull
   private final BoolValue rainbow = new BoolValue("Rainbow", false);
   // $FF: synthetic field
   private final FloatValue colorGreenValue = new FloatValue("G", 100.0F, 0.0F, 255.0F);

   @NotNull
   public final BoolValue getRainbow() {
      return llllllllllllllllllIIllllIlllllIl.rainbow;
   }

   @NotNull
   public final BoolValue getItemsValue() {
      return llllllllllllllllllIIllllIlllIIll.itemsValue;
   }

   @NotNull
   public final BoolValue getTargetsValue() {
      return llllllllllllllllllIIllllIllllIlI.targetsValue;
   }

   @Nullable
   public final BoolValue getModeValue() {
      return llllllllllllllllllIIllllIlllIIII.rainbow;
   }

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent llllllllllllllllllIIllllIllIlIlI) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIIllllIllIlIlI, "event");
      ChamsColor.red = ((Number)llllllllllllllllllIIllllIllIllIl.colorRedValue.get()).floatValue() / (float)255;
      ChamsColor.green = ((Number)llllllllllllllllllIIllllIllIllIl.colorGreenValue.get()).floatValue() / (float)255;
      ChamsColor.blue = ((Number)llllllllllllllllllIIllllIllIllIl.colorBlueValue.get()).floatValue() / (float)255;
      ChamsColor.Apl = ((Number)llllllllllllllllllIIllllIllIllIl.colorAValue.get()).floatValue() / (float)255;
      ChamsColor.red2 = ((Number)llllllllllllllllllIIllllIllIllIl.colorRed2Value.get()).floatValue() / (float)255;
      ChamsColor.green2 = ((Number)llllllllllllllllllIIllllIllIllIl.colorGreen2Value.get()).floatValue() / (float)255;
      ChamsColor.blue2 = ((Number)llllllllllllllllllIIllllIllIllIl.colorBlue2Value.get()).floatValue() / (float)255;
      ChamsColor.Apl2 = ((Number)llllllllllllllllllIIllllIllIllIl.colorA2Value.get()).floatValue() / (float)255;
   }

   @NotNull
   public final BoolValue getChestsValue() {
      return llllllllllllllllllIIllllIlllIlll.chestsValue;
   }
}
