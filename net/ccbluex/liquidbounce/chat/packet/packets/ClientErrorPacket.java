package net.ccbluex.liquidbounce.chat.packet.packets;

import com.google.gson.annotations.SerializedName;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0003HÖ\u0001R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"},
   d2 = {"Lnet/ccbluex/liquidbounce/chat/packet/packets/ClientErrorPacket;", "Lnet/ccbluex/liquidbounce/chat/packet/packets/Packet;", "message", "", "(Ljava/lang/String;)V", "getMessage", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "LiquidSense"}
)
public final class ClientErrorPacket implements Packet {
   // $FF: synthetic field
   @SerializedName("message")
   @NotNull
   private final String message;

   public int hashCode() {
      String var10000 = this.message;
      int var1;
      if (var10000 != null) {
         var1 = var10000.hashCode();
      } else {
         boolean var10001 = false;
         var1 = 0;
      }

      return var1;
   }

   public boolean equals(@Nullable Object var1) {
      if (this != var1) {
         if (var1 instanceof ClientErrorPacket) {
            ClientErrorPacket var2 = (ClientErrorPacket)var1;
            if (Intrinsics.areEqual((Object)this.message, (Object)var2.message)) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }

   public ClientErrorPacket(@NotNull String lIlIIlIIlIllIII) {
      Intrinsics.checkParameterIsNotNull(lIlIIlIIlIllIII, "message");
      super();
      lIlIIlIIlIllIll.message = lIlIIlIIlIllIII;
   }

   @NotNull
   public final String component1() {
      return lIlIIlIIlIlIllI.message;
   }

   // $FF: synthetic method
   public static ClientErrorPacket copy$default(ClientErrorPacket var0, String var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = var0.message;
      }

      return var0.copy(var1);
   }

   @NotNull
   public String toString() {
      return String.valueOf((new StringBuilder()).append("ClientErrorPacket(message=").append(this.message).append(")"));
   }

   @NotNull
   public final String getMessage() {
      return lIlIIlIIlIlllll.message;
   }

   @NotNull
   public final ClientErrorPacket copy(@NotNull String lIlIIlIIlIlIIIl) {
      Intrinsics.checkParameterIsNotNull(lIlIIlIIlIlIIIl, "message");
      return new ClientErrorPacket(lIlIIlIIlIlIIIl);
   }
}
