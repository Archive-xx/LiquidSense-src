package net.ccbluex.liquidbounce.value;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import java.util.Arrays;
import java.util.function.Predicate;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B#\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0002¢\u0006\u0002\u0010\u0007J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0006\u001a\u00020\u0002H\u0016J\u0013\u0010\u000f\u001a\u00020\t2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0002H\u0086\u0002J\u0010\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u0015H\u0016R\u0012\u0010\b\u001a\u00020\t8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0019\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00020\u0005¢\u0006\n\n\u0002\u0010\f\u001a\u0004\b\n\u0010\u000b¨\u0006\u0016"},
   d2 = {"Lnet/ccbluex/liquidbounce/value/ListValue;", "Lnet/ccbluex/liquidbounce/value/Value;", "", "name", "values", "", "value", "(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)V", "openList", "", "getValues", "()[Ljava/lang/String;", "[Ljava/lang/String;", "changeValue", "", "contains", "string", "fromJson", "element", "Lcom/google/gson/JsonElement;", "toJson", "Lcom/google/gson/JsonPrimitive;", "LiquidSense"}
)
public class ListValue extends Value<String> {
   // $FF: synthetic field
   @NotNull
   private final String[] values;
   // $FF: synthetic field
   @JvmField
   public boolean openList;

   public final boolean contains(@Nullable final String lIIIIlIIlIlIIII) {
      return Arrays.stream(lIIIIlIIlIlIlIl.values).anyMatch((Predicate)(new Predicate<String>() {
         public final boolean test(@NotNull String lIIlIlllIIIII) {
            Intrinsics.checkParameterIsNotNull(lIIlIlllIIIII, "s");
            return StringsKt.equals(lIIlIlllIIIII, lIIIIlIIlIlIIII, true);
         }
      }));
   }

   public void changeValue(@NotNull String lIIIIlIIIllIIIl) {
      Intrinsics.checkParameterIsNotNull(lIIIIlIIIllIIIl, "value");
      String lIIIIlIIIlIllII = lIIIIlIIIllIIII.values;
      short lIIIIlIIIlIlIlI = lIIIIlIIIlIllII.length;

      for(int lIIIIlIIIlIllIl = 0; lIIIIlIIIlIllIl < lIIIIlIIIlIlIlI; ++lIIIIlIIIlIllIl) {
         String lIIIIlIIIllIIll = lIIIIlIIIlIllII[lIIIIlIIIlIllIl];
         if (StringsKt.equals(lIIIIlIIIllIIll, lIIIIlIIIllIIIl, true)) {
            lIIIIlIIIllIIII.setValue(lIIIIlIIIllIIll);
            break;
         }
      }

   }

   @NotNull
   public JsonPrimitive toJson() {
      return new JsonPrimitive((String)lIIIIlIIIIllIIl.getValue());
   }

   @NotNull
   public final String[] getValues() {
      return lIIIIlIIIIIlIII.values;
   }

   public ListValue(@NotNull String lIIIIIlllllllIl, @NotNull String[] lIIIIIlllllllII, @NotNull String lIIIIIlllllIlll) {
      Intrinsics.checkParameterIsNotNull(lIIIIIlllllllIl, "name");
      Intrinsics.checkParameterIsNotNull(lIIIIIlllllllII, "values");
      Intrinsics.checkParameterIsNotNull(lIIIIIlllllIlll, "value");
      super(lIIIIIlllllllIl, lIIIIIlllllIlll);
      lIIIIIllllllIlI.values = lIIIIIlllllllII;
      lIIIIIllllllIlI.setValue(lIIIIIlllllIlll);
   }

   public void fromJson(@NotNull JsonElement lIIIIlIIIIIllII) {
      Intrinsics.checkParameterIsNotNull(lIIIIlIIIIIllII, "element");
      if (lIIIIlIIIIIllII.isJsonPrimitive()) {
         String var10001 = lIIIIlIIIIIllII.getAsString();
         Intrinsics.checkExpressionValueIsNotNull(var10001, "element.asString");
         lIIIIlIIIIlIIIl.changeValue(var10001);
      }

   }
}
