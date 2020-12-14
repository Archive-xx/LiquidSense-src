package net.ccbluex.liquidbounce.value;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B)\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0002\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0002¢\u0006\u0002\u0010\bJ\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0016R\u0011\u0010\u0007\u001a\u00020\u0002¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0002¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\n¨\u0006\u0012"},
   d2 = {"Lnet/ccbluex/liquidbounce/value/IntegerValue;", "Lnet/ccbluex/liquidbounce/value/Value;", "", "name", "", "value", "minimum", "maximum", "(Ljava/lang/String;III)V", "getMaximum", "()I", "getMinimum", "fromJson", "", "element", "Lcom/google/gson/JsonElement;", "toJson", "Lcom/google/gson/JsonPrimitive;", "LiquidSense"}
)
public class IntegerValue extends Value<Integer> {
   // $FF: synthetic field
   private final int maximum;
   // $FF: synthetic field
   private final int minimum;

   public IntegerValue(@NotNull String lIlIIlllIlIIIll, int lIlIIlllIlIIIlI, int lIlIIlllIIlllII, int lIlIIlllIlIIIII) {
      Intrinsics.checkParameterIsNotNull(lIlIIlllIlIIIll, "name");
      super(lIlIIlllIlIIIll, lIlIIlllIlIIIlI);
      lIlIIlllIIlllll.minimum = lIlIIlllIIlllII;
      lIlIIlllIIlllll.maximum = lIlIIlllIlIIIII;
   }

   public final int getMinimum() {
      return lIlIIlllIlllIlI.minimum;
   }

   public final int getMaximum() {
      return lIlIIlllIllIllI.maximum;
   }

   @NotNull
   public JsonPrimitive toJson() {
      return new JsonPrimitive((Number)lIlIIllllIIlIII.getValue());
   }

   public void fromJson(@NotNull JsonElement lIlIIllllIIIIII) {
      Intrinsics.checkParameterIsNotNull(lIlIIllllIIIIII, "element");
      if (lIlIIllllIIIIII.isJsonPrimitive()) {
         lIlIIllllIIIIll.setValue(lIlIIllllIIIIII.getAsInt());
      }

   }

   // $FF: synthetic method
   public IntegerValue(String var1, int var2, int var3, int var4, int lIlIIlllIIIllll, DefaultConstructorMarker var6) {
      if ((lIlIIlllIIIllll & 4) != 0) {
         var3 = 0;
      }

      if ((lIlIIlllIIIllll & 8) != 0) {
         var4 = Integer.MAX_VALUE;
      }

      this(var1, var2, var3, var4);
   }
}
