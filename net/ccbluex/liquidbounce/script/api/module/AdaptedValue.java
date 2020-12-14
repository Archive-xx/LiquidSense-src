package net.ccbluex.liquidbounce.script.api.module;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.Value;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u0005\u001a\u00020\u0001J\u0006\u0010\u0006\u001a\u00020\u0007J\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0001R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"},
   d2 = {"Lnet/ccbluex/liquidbounce/script/api/module/AdaptedValue;", "", "value", "Lnet/ccbluex/liquidbounce/value/Value;", "(Lnet/ccbluex/liquidbounce/value/Value;)V", "get", "getName", "", "getValue", "set", "", "newValue", "LiquidSense"}
)
public final class AdaptedValue {
   // $FF: synthetic field
   private final Value<Object> value;

   public final void set(@NotNull Object lIlIlIllIIIIlll) {
      Intrinsics.checkParameterIsNotNull(lIlIlIllIIIIlll, "newValue");
      if (lIlIlIllIIIIlll instanceof Number) {
         if (lIlIlIllIIIIllI.value instanceof FloatValue) {
            ((FloatValue)lIlIlIllIIIIllI.value).set(((Number)lIlIlIllIIIIlll).floatValue());
         } else if (lIlIlIllIIIIllI.value instanceof IntegerValue) {
            ((IntegerValue)lIlIlIllIIIIllI.value).set(((Number)lIlIlIllIIIIlll).intValue());
         }

      } else {
         lIlIlIllIIIIllI.value.set(lIlIlIllIIIIlll);
      }
   }

   @NotNull
   public final Object get() {
      return lIlIlIllIIlIIlI.value.get();
   }

   @NotNull
   public final String getName() {
      return lIlIlIllIIIlllI.value.getName();
   }

   public AdaptedValue(@NotNull Value<Object> lIlIlIlIlllllll) {
      Intrinsics.checkParameterIsNotNull(lIlIlIlIlllllll, "value");
      super();
      lIlIlIllIIIIIII.value = lIlIlIlIlllllll;
   }

   @NotNull
   public final Value<Object> getValue() {
      return lIlIlIllIIIllII.value;
   }
}
