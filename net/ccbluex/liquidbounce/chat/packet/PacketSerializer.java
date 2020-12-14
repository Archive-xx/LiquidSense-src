package net.ccbluex.liquidbounce.chat.packet;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.chat.packet.packets.Packet;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\u001e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00072\u000e\u0010\f\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u0006J \u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016R:\u0010\u0004\u001a.\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u0016\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u0006\u0012\u0004\u0012\u00020\u0007`\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"},
   d2 = {"Lnet/ccbluex/liquidbounce/chat/packet/PacketSerializer;", "Lcom/google/gson/JsonSerializer;", "Lnet/ccbluex/liquidbounce/chat/packet/packets/Packet;", "()V", "packetRegistry", "Ljava/util/HashMap;", "Ljava/lang/Class;", "", "Lkotlin/collections/HashMap;", "registerPacket", "", "packetName", "packetClass", "serialize", "Lcom/google/gson/JsonElement;", "src", "typeOfSrc", "Ljava/lang/reflect/Type;", "context", "Lcom/google/gson/JsonSerializationContext;", "LiquidSense"}
)
public final class PacketSerializer implements JsonSerializer<Packet> {
   // $FF: synthetic field
   private final HashMap<Class<? extends Packet>, String> packetRegistry = new HashMap();

   public final void registerPacket(@NotNull String lIlIIIllIIIIIll, @NotNull Class<? extends Packet> lIlIIIllIIIIIlI) {
      Intrinsics.checkParameterIsNotNull(lIlIIIllIIIIIll, "packetName");
      Intrinsics.checkParameterIsNotNull(lIlIIIllIIIIIlI, "packetClass");
      ((Map)lIlIIIllIIIIlll.packetRegistry).put(lIlIIIllIIIIIlI, lIlIIIllIIIIIll);
      boolean var10001 = false;
   }

   @NotNull
   public JsonElement serialize(@NotNull Packet lIlIIIlIllIIIll, @NotNull Type lIlIIIlIllIIIlI, @NotNull JsonSerializationContext lIlIIIlIllIIIIl) {
      Intrinsics.checkParameterIsNotNull(lIlIIIlIllIIIll, "src");
      Intrinsics.checkParameterIsNotNull(lIlIIIlIllIIIlI, "typeOfSrc");
      Intrinsics.checkParameterIsNotNull(lIlIIIlIllIIIIl, "context");
      Object var10000 = lIlIIIlIllIlIII.packetRegistry.getOrDefault(lIlIIIlIllIIIll.getClass(), "UNKNOWN");
      Intrinsics.checkExpressionValueIsNotNull(var10000, "packetRegistry.getOrDefa…src.javaClass, \"UNKNOWN\")");
      Exception lIlIIIlIllIIIII = (String)var10000;
      Constructor[] var10001 = lIlIIIlIllIIIll.getClass().getConstructors();
      Intrinsics.checkExpressionValueIsNotNull(var10001, "src.javaClass.constructors");
      Object[] lIlIIIlIllIllII = var10001;
      Exception lIlIIIlIlIlllIl = false;
      short lIlIIIlIlIlllII = lIlIIIlIllIllII;
      String lIlIIIlIlIllIll = lIlIIIlIllIllII.length;
      int lIlIIIlIlIllIlI = 0;

      boolean var20;
      while(true) {
         if (lIlIIIlIlIllIlI >= lIlIIIlIlIllIll) {
            var20 = true;
            break;
         }

         Object lIlIIIlIllIllIl = lIlIIIlIlIlllII[lIlIIIlIlIllIlI];
         int lIlIIIlIllIlllI = false;
         Intrinsics.checkExpressionValueIsNotNull(lIlIIIlIllIllIl, "it");
         if (lIlIIIlIllIllIl.getParameterCount() != 0) {
            var20 = false;
            break;
         }

         ++lIlIIIlIlIllIlI;
      }

      double lIlIIIlIlIlIlIl = var20;
      String lIlIIIlIlIlIlII = lIlIIIlIlIlIlIl ? null : lIlIIIlIllIIIll;
      double lIlIIIlIlIlllll = new SerializedPacket(lIlIIIlIllIIIII, lIlIIIlIlIlIlII);
      JsonElement var21 = (new Gson()).toJsonTree(lIlIIIlIlIlllll);
      Intrinsics.checkExpressionValueIsNotNull(var21, "Gson().toJsonTree(serializedPacket)");
      return var21;
   }
}
