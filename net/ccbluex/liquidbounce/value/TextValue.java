package net.ccbluex.liquidbounce.value;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0002¢\u0006\u0002\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J\b\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"},
   d2 = {"Lnet/ccbluex/liquidbounce/value/TextValue;", "Lnet/ccbluex/liquidbounce/value/Value;", "", "name", "value", "(Ljava/lang/String;Ljava/lang/String;)V", "fromJson", "", "element", "Lcom/google/gson/JsonElement;", "toJson", "Lcom/google/gson/JsonPrimitive;", "LiquidSense"}
)
public class TextValue extends Value<String> {
   public TextValue(@NotNull String llIIIlllIllll, @NotNull String llIIIllllIIIl) {
      Intrinsics.checkParameterIsNotNull(llIIIlllIllll, "name");
      Intrinsics.checkParameterIsNotNull(llIIIllllIIIl, "value");
      super(llIIIlllIllll, llIIIllllIIIl);
   }

   @NotNull
   public JsonPrimitive toJson() {
      return new JsonPrimitive((String)llIIlIIIlIlIl.getValue());
   }

   public void fromJson(@NotNull JsonElement llIIlIIIIIIIl) {
      Intrinsics.checkParameterIsNotNull(llIIlIIIIIIIl, "element");
      if (llIIlIIIIIIIl.isJsonPrimitive()) {
         String var10001 = llIIlIIIIIIIl.getAsString();
         Intrinsics.checkExpressionValueIsNotNull(var10001, "element.asString");
         llIIIllllllll.setValue(var10001);
      }

   }
}
