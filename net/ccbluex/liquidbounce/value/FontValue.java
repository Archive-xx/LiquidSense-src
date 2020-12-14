package net.ccbluex.liquidbounce.value;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.minecraft.client.gui.FontRenderer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\n\u0010\u000b\u001a\u0004\u0018\u00010\nH\u0016¨\u0006\f"},
   d2 = {"Lnet/ccbluex/liquidbounce/value/FontValue;", "Lnet/ccbluex/liquidbounce/value/Value;", "Lnet/minecraft/client/gui/FontRenderer;", "valueName", "", "value", "(Ljava/lang/String;Lnet/minecraft/client/gui/FontRenderer;)V", "fromJson", "", "element", "Lcom/google/gson/JsonElement;", "toJson", "LiquidSense"}
)
public final class FontValue extends Value<FontRenderer> {
   @Nullable
   public JsonElement toJson() {
      Object[] var10000 = Fonts.getFontDetails((FontRenderer)lIIIlIlIlIIIIII.getValue());
      if (var10000 != null) {
         Object[] lIIIlIlIlIIIIIl = var10000;
         float lIIIlIlIIllllIl = new JsonObject();
         Object var10002 = lIIIlIlIlIIIIIl[0];
         if (lIIIlIlIlIIIIIl[0] == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
         } else {
            lIIIlIlIIllllIl.addProperty("fontName", (String)var10002);
            var10002 = lIIIlIlIlIIIIIl[1];
            if (lIIIlIlIlIIIIIl[1] == null) {
               throw new TypeCastException("null cannot be cast to non-null type kotlin.Int");
            } else {
               lIIIlIlIIllllIl.addProperty("fontSize", (Number)((Integer)var10002));
               return (JsonElement)lIIIlIlIIllllIl;
            }
         }
      } else {
         boolean var10001 = false;
         return null;
      }
   }

   public void fromJson(@NotNull JsonElement lIIIlIlIIllIlll) {
      Intrinsics.checkParameterIsNotNull(lIIIlIlIIllIlll, "element");
      if (lIIIlIlIIllIlll.isJsonObject()) {
         float lIIIlIlIIllIlII = lIIIlIlIIllIlll.getAsJsonObject();
         JsonElement var10001 = lIIIlIlIIllIlII.get("fontName");
         Intrinsics.checkExpressionValueIsNotNull(var10001, "valueObject[\"fontName\"]");
         String var3 = var10001.getAsString();
         JsonElement var10002 = lIIIlIlIIllIlII.get("fontSize");
         Intrinsics.checkExpressionValueIsNotNull(var10002, "valueObject[\"fontSize\"]");
         FontRenderer var4 = Fonts.getFontRenderer(var3, var10002.getAsInt());
         Intrinsics.checkExpressionValueIsNotNull(var4, "Fonts.getFontRenderer(va…Object[\"fontSize\"].asInt)");
         lIIIlIlIIllIllI.setValue(var4);
      }
   }

   public FontValue(@NotNull String lIIIlIlIIlIllll, @NotNull FontRenderer lIIIlIlIIlIlIll) {
      Intrinsics.checkParameterIsNotNull(lIIIlIlIIlIllll, "valueName");
      Intrinsics.checkParameterIsNotNull(lIIIlIlIIlIlIll, "value");
      super(lIIIlIlIIlIllll, lIIIlIlIIlIlIll);
   }
}
