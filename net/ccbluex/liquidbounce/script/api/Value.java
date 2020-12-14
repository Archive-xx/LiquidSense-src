package net.ccbluex.liquidbounce.script.api;

import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.script.api.module.AdaptedValue;
import net.ccbluex.liquidbounce.value.BlockValue;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.ccbluex.liquidbounce.value.TextValue;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0018\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\nH\u0007J(\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\fH\u0007J(\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\bH\u0007J+\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00060\u00122\u0006\u0010\u0007\u001a\u00020\u0006H\u0007¢\u0006\u0002\u0010\u0013J\u0018\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0007¨\u0006\u0015"},
   d2 = {"Lnet/ccbluex/liquidbounce/script/api/Value;", "", "()V", "createBlock", "Lnet/ccbluex/liquidbounce/script/api/module/AdaptedValue;", "name", "", "value", "", "createBoolean", "", "createFloat", "", "min", "max", "createInteger", "createList", "values", "", "(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Lnet/ccbluex/liquidbounce/script/api/module/AdaptedValue;", "createText", "LiquidSense"}
)
public final class Value {
   // $FF: synthetic field
   public static final Value INSTANCE;

   @JvmStatic
   @NotNull
   public static final AdaptedValue createBlock(@NotNull String lllIlIIlIlIl, int lllIlIIlIlII) {
      Intrinsics.checkParameterIsNotNull(lllIlIIlIlIl, "name");
      return new AdaptedValue((net.ccbluex.liquidbounce.value.Value)(new BlockValue(lllIlIIlIlIl, lllIlIIlIlII)));
   }

   static {
      Value lllIIllIIIII = new Value();
      INSTANCE = lllIIllIIIII;
   }

   @JvmStatic
   @NotNull
   public static final AdaptedValue createText(@NotNull String lllIIllIlIII, @NotNull String lllIIllIIlll) {
      Intrinsics.checkParameterIsNotNull(lllIIllIlIII, "name");
      Intrinsics.checkParameterIsNotNull(lllIIllIIlll, "value");
      return new AdaptedValue((net.ccbluex.liquidbounce.value.Value)(new TextValue(lllIIllIlIII, lllIIllIIlll)));
   }

   private Value() {
   }

   @JvmStatic
   @NotNull
   public static final AdaptedValue createList(@NotNull String lllIIllIllIl, @NotNull String[] lllIIllIllII, @NotNull String lllIIllIlllI) {
      Intrinsics.checkParameterIsNotNull(lllIIllIllIl, "name");
      Intrinsics.checkParameterIsNotNull(lllIIllIllII, "values");
      Intrinsics.checkParameterIsNotNull(lllIIllIlllI, "value");
      return new AdaptedValue((net.ccbluex.liquidbounce.value.Value)(new ListValue(lllIIllIllIl, lllIIllIllII, lllIIllIlllI)));
   }

   @JvmStatic
   @NotNull
   public static final AdaptedValue createBoolean(@NotNull String lllIlIIIllll, boolean lllIlIIIlllI) {
      Intrinsics.checkParameterIsNotNull(lllIlIIIllll, "name");
      return new AdaptedValue((net.ccbluex.liquidbounce.value.Value)(new BoolValue(lllIlIIIllll, lllIlIIIlllI)));
   }

   @JvmStatic
   @NotNull
   public static final AdaptedValue createFloat(@NotNull String lllIlIIIIlll, float lllIlIIIIllI, float lllIlIIIIlIl, float lllIlIIIIIII) {
      Intrinsics.checkParameterIsNotNull(lllIlIIIIlll, "name");
      return new AdaptedValue((net.ccbluex.liquidbounce.value.Value)(new FloatValue(lllIlIIIIlll, lllIlIIIIllI, lllIlIIIIlIl, lllIlIIIIIII)));
   }

   @JvmStatic
   @NotNull
   public static final AdaptedValue createInteger(@NotNull String lllIIlllIlll, int lllIIllllIlI, int lllIIlllIlIl, int lllIIlllIlII) {
      Intrinsics.checkParameterIsNotNull(lllIIlllIlll, "name");
      return new AdaptedValue((net.ccbluex.liquidbounce.value.Value)(new IntegerValue(lllIIlllIlll, lllIIllllIlI, lllIIlllIlIl, lllIIlllIlII)));
   }
}
