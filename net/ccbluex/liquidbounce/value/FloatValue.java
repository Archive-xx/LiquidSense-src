package net.ccbluex.liquidbounce.value;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FloatCompanionObject;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B)\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0002\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0002¢\u0006\u0002\u0010\bJ\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0016R\u0011\u0010\u0007\u001a\u00020\u0002¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0002¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\n¨\u0006\u0012"},
   d2 = {"Lnet/ccbluex/liquidbounce/value/FloatValue;", "Lnet/ccbluex/liquidbounce/value/Value;", "", "name", "", "value", "minimum", "maximum", "(Ljava/lang/String;FFF)V", "getMaximum", "()F", "getMinimum", "fromJson", "", "element", "Lcom/google/gson/JsonElement;", "toJson", "Lcom/google/gson/JsonPrimitive;", "LiquidSense"}
)
public class FloatValue extends Value<Float> {
   // $FF: synthetic field
   private final float maximum;
   // $FF: synthetic field
   private final float minimum;

   public void fromJson(@NotNull JsonElement lIIlllIIIlIIIll) {
      Intrinsics.checkParameterIsNotNull(lIIlllIIIlIIIll, "element");
      if (lIIlllIIIlIIIll.isJsonPrimitive()) {
         lIIlllIIIlIIIlI.setValue(lIIlllIIIlIIIll.getAsFloat());
      }

   }

   public final float getMaximum() {
      return lIIlllIIIIlllII.maximum;
   }

   public final float getMinimum() {
      return lIIlllIIIIlllll.minimum;
   }

   @NotNull
   public JsonPrimitive toJson() {
      return new JsonPrimitive((Number)lIIlllIIIlIlIlI.getValue());
   }

   public FloatValue(@NotNull String lIIlllIIIIlIlII, float lIIlllIIIIIlllI, float lIIlllIIIIlIIlI, float lIIlllIIIIIllII) {
      Intrinsics.checkParameterIsNotNull(lIIlllIIIIlIlII, "name");
      super(lIIlllIIIIlIlII, lIIlllIIIIIlllI);
      lIIlllIIIIlIlIl.minimum = lIIlllIIIIlIIlI;
      lIIlllIIIIlIlIl.maximum = lIIlllIIIIIllII;
   }

   // $FF: synthetic method
   public FloatValue(String var1, float var2, float var3, float var4, int lIIlllIIIIIIIII, DefaultConstructorMarker var6) {
      if ((lIIlllIIIIIIIII & 4) != 0) {
         var3 = 0.0F;
      }

      if ((lIIlllIIIIIIIII & 8) != 0) {
         var4 = FloatCompanionObject.INSTANCE.getMAX_VALUE();
      }

      this(var1, var2, var3, var4);
   }
}
