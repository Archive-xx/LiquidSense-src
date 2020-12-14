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
   d2 = {"Lnet/ccbluex/liquidbounce/chat/packet/packets/ClientNewJWTPacket;", "Lnet/ccbluex/liquidbounce/chat/packet/packets/Packet;", "token", "", "(Ljava/lang/String;)V", "getToken", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "LiquidSense"}
)
public final class ClientNewJWTPacket implements Packet {
   // $FF: synthetic field
   @SerializedName("token")
   @NotNull
   private final String token;

   @NotNull
   public final ClientNewJWTPacket copy(@NotNull String llllIlIlIIII) {
      Intrinsics.checkParameterIsNotNull(llllIlIlIIII, "token");
      return new ClientNewJWTPacket(llllIlIlIIII);
   }

   public boolean equals(@Nullable Object var1) {
      if (this != var1) {
         if (var1 instanceof ClientNewJWTPacket) {
            ClientNewJWTPacket var2 = (ClientNewJWTPacket)var1;
            if (Intrinsics.areEqual((Object)this.token, (Object)var2.token)) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }

   @NotNull
   public final String getToken() {
      return llllIllIIllI.token;
   }

   public ClientNewJWTPacket(@NotNull String llllIlIllIIl) {
      Intrinsics.checkParameterIsNotNull(llllIlIllIIl, "token");
      super();
      llllIlIllIlI.token = llllIlIllIIl;
   }

   @NotNull
   public final String component1() {
      return llllIlIlIlIl.token;
   }

   // $FF: synthetic method
   public static ClientNewJWTPacket copy$default(ClientNewJWTPacket var0, String var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = var0.token;
      }

      return var0.copy(var1);
   }

   public int hashCode() {
      String var10000 = this.token;
      int var1;
      if (var10000 != null) {
         var1 = var10000.hashCode();
      } else {
         boolean var10001 = false;
         var1 = 0;
      }

      return var1;
   }

   @NotNull
   public String toString() {
      return String.valueOf((new StringBuilder()).append("ClientNewJWTPacket(token=").append(this.token).append(")"));
   }
}
