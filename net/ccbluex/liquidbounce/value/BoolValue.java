package net.ccbluex.liquidbounce.value;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\b\u0010\u000b\u001a\u00020\fH\u0016¨\u0006\r"},
   d2 = {"Lnet/ccbluex/liquidbounce/value/BoolValue;", "Lnet/ccbluex/liquidbounce/value/Value;", "", "name", "", "value", "(Ljava/lang/String;Z)V", "fromJson", "", "element", "Lcom/google/gson/JsonElement;", "toJson", "Lcom/google/gson/JsonPrimitive;", "LiquidSense"}
)
public class BoolValue extends Value<Boolean> {
   public BoolValue(@NotNull String lIlIlIlIlIlIlII, boolean lIlIlIlIlIlIIll) {
      Intrinsics.checkParameterIsNotNull(lIlIlIlIlIlIlII, "name");
      super(lIlIlIlIlIlIlII, lIlIlIlIlIlIIll);
   }

   @NotNull
   public JsonPrimitive toJson() {
      return new JsonPrimitive((Boolean)lIlIlIlIllIIlIl.getValue());
   }

   public void fromJson(@NotNull JsonElement lIlIlIlIlIllllI) {
      Intrinsics.checkParameterIsNotNull(lIlIlIlIlIllllI, "element");
      if (lIlIlIlIlIllllI.isJsonPrimitive()) {
         lIlIlIlIlIlllIl.setValue(lIlIlIlIlIllllI.getAsBoolean() || StringsKt.equals(lIlIlIlIlIllllI.getAsString(), "true", true));
      }

   }
}
